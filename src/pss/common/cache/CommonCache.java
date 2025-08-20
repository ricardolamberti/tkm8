package pss.common.cache;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

import pss.core.tools.JTools;
import pss.core.tools.PssLogger;
import pss.www.platform.cache.CacheProvider;
import pss.www.platform.cache.DistCache;

/**
 * Thread-safe helpers for scoped cache access.
 *
 * <pre>
 * // Global por 30 minutos
 * CommonCache.putString("ANNOUNCE:home_banner", "Bienvenido!", CacheScope.GLOBAL, Duration.ofMinutes(30));
 *
 * // Preferencias por usuario (4h)
 * CommonCache.putObject("prefs", userPrefsDTO, CacheScope.USER, Duration.ofHours(4));
 *
 * // Estado temporal por sesión/página (20m)
 * CommonCache.putBytes("wizard_state", serializedBytes, CacheScope.SESSION, Duration.ofMinutes(20));
 *
 * // Lecturas
 * String banner = CommonCache.getString("ANNOUNCE:home_banner", CacheScope.GLOBAL);
 * UserPrefs prefs = CommonCache.getObject("prefs", UserPrefs.class, CacheScope.USER);
 *
 * // Invalidaciones
 * CommonCache.remove("prefs", CacheScope.USER);
 * CommonCache.clearUser();
 * CommonCache.clearSession();
 * CommonCache.clearGlobalPrefix("ANNOUNCE:");
 * </pre>
 */
public final class CommonCache {

	private static final String NS = "PSS:COMMONCACHE:";
	private static final Duration INDEX_TTL = Duration.ofHours(24);

	private CommonCache() {
	}

	/**
	 * Store a UTF-8 string value.
	 * 
	 * @throws Exception
	 */
	public static void putString(String key, String value, CacheScope scope, Duration ttl) throws Exception {
		Objects.requireNonNull(value, "value");
		putBytes(key, value.getBytes(StandardCharsets.UTF_8), scope, ttl);
	}

	/**
	 * Retrieve a UTF-8 string value.
	 * 
	 * @throws Exception
	 */
	public static String getString(String key, CacheScope scope) throws Exception {
		byte[] data = getBytes(key, scope);
		return data == null ? null : new String(data, StandardCharsets.UTF_8);
	}

	/**
	 * Store raw bytes.
	 * 
	 * @throws Exception
	 */
	public static void putBytes(String key, byte[] bytes, CacheScope scope, Duration ttl) throws Exception {
		Objects.requireNonNull(bytes, "bytes");
		String fullKey = buildKey(key, scope);
		DistCache cache = CacheProvider.get();
		cache.putBytes(fullKey, bytes, ttlSeconds(ttl));
		addToIndex(scope, contextId(scope), fullKey);
	}

	/**
	 * Retrieve raw bytes.
	 * 
	 * @throws Exception
	 */
	public static byte[] getBytes(String key, CacheScope scope) throws Exception {
		String fullKey = buildKey(key, scope);
		DistCache cache = CacheProvider.get();
		return cache.getBytes(fullKey);
	}

	/** Serialize and store an object. */
	public static void putObject(String key, Object value, CacheScope scope, Duration ttl) throws Exception {
		if (value == null) {
			remove(key, scope);
			return;
		}
		String json = toJson(value);
		putString(key, json, scope, ttl);
	}

	/** Retrieve and deserialize an object. */
	public static <T> T getObject(String key, Class<T> type, CacheScope scope) throws Exception {
		String json = getString(key, scope);
		return json == null ? null : fromJson(json, type);
	}

	/** Remove a cached value. */
	public static void remove(String key, CacheScope scope) throws Exception {
		String fullKey = buildKey(key, scope);
		DistCache cache = CacheProvider.get();
		cache.delete(fullKey);
		removeFromIndex(scope, contextId(scope), fullKey);
	}

	/** Invalidate all keys for the current user. */
	public static void clearUser() throws Exception {
		String userId = UserContext.currentUserId();
		String prefix = NS + "USER:" + userId + ":";
		DistCache cache = CacheProvider.get();
		Iterable<String> keys = tryKeysByPrefix(cache, prefix);
		if (keys != null) {
			for (String k : keys) {
				cache.delete(k);
			}
			PssLogger.logInfo("CommonCache: cleared user " + userId);
		} else {
			PssLogger.logError("CommonCache: keysByPrefix unavailable, using index for user " + userId);
			String idxKey = indexKey(CacheScope.USER, userId);
			Set<String> all = readIndex(idxKey);
			for (String k : all) {
				cache.delete(k);
			}
			cache.delete(idxKey);
			PssLogger.logInfo("CommonCache: cleared user " + userId);
		}
	}

	/** Invalidate all keys for the current session. */
	public static void clearSession() throws Exception {
		String sessionId = UserContext.currentSessionId();
		String prefix = NS + "SESSION:" + sessionId + ":";
		DistCache cache = CacheProvider.get();
		Iterable<String> keys = tryKeysByPrefix(cache, prefix);
		if (keys != null) {
			for (String k : keys) {
				cache.delete(k);
			}
			PssLogger.logInfo("CommonCache: cleared session " + sessionId);
		} else {
			PssLogger.logError("CommonCache: keysByPrefix unavailable, using index for session " + sessionId);
			String idxKey = indexKey(CacheScope.SESSION, sessionId);
			Set<String> all = readIndex(idxKey);
			for (String k : all) {
				cache.delete(k);
			}
			cache.delete(idxKey);
			PssLogger.logInfo("CommonCache: cleared session " + sessionId);
		}
	}

	/** Invalidate global keys by prefix. */
	public static void clearGlobalPrefix(String prefix) throws Exception {
		Objects.requireNonNull(prefix, "prefix");
		String fullPrefix = NS + "GLOBAL:" + prefix;
		DistCache cache = CacheProvider.get();
		Iterable<String> keys = tryKeysByPrefix(cache, fullPrefix);
		if (keys != null) {
			for (String k : keys) {
				cache.delete(k);
			}
			PssLogger.logInfo("CommonCache: cleared global prefix " + prefix);
		} else {
			PssLogger.logError("CommonCache: keysByPrefix unavailable, using index for global prefix " + prefix);
			String idxKey = indexKey(CacheScope.GLOBAL, null);
			Set<String> all = readIndex(idxKey);
			Iterator<String> it = all.iterator();
			while (it.hasNext()) {
				String k = it.next();
				if (k.startsWith(fullPrefix)) {
					cache.delete(k);
					it.remove();
				}
			}
			if (all.isEmpty()) {
				cache.delete(idxKey);
			} else {
				writeIndex(idxKey, all);
			}
			PssLogger.logInfo("CommonCache: cleared global prefix " + prefix);
		}
	}

	// ---------------------- helpers ----------------------

	private static int ttlSeconds(Duration ttl) throws Exception {
		if (ttl == null) {
			return 0;
		}
		long sec = ttl.getSeconds();
		if (sec <= 0) {
			return 0;
		}
		return (int) Math.min(Integer.MAX_VALUE, sec);
	}

	private static String buildKey(String key, CacheScope scope) throws Exception {
		Objects.requireNonNull(key, "key");
		if (key.isEmpty()) {
			throw new IllegalArgumentException("key is empty");
		}
		StringBuilder sb = new StringBuilder(NS);
		switch (scope) {
		case GLOBAL:
			sb.append("GLOBAL:").append(key);
			break;
		case USER:
			sb.append("USER:").append(UserContext.currentUserId()).append(':').append(key);
			break;
		case SESSION:
			sb.append("SESSION:").append(UserContext.currentSessionId()).append(':').append(key);
			break;
		default:
			throw new IllegalArgumentException("unknown scope");
		}
		return sb.toString();
	}

	private static String contextId(CacheScope scope) throws Exception {
		switch (scope) {
		case USER:
			return UserContext.currentUserId();
		case SESSION:
			return UserContext.currentSessionId();
		default:
			return null;
		}
	}

	private static Iterable<String> tryKeysByPrefix(DistCache cache, String prefix) throws Exception {
		try {
			java.lang.reflect.Method m = cache.getClass().getMethod("keysByPrefix", String.class);
			Object res = m.invoke(cache, prefix);
			if (res instanceof Iterable) {
				return (Iterable<String>) res;
			}
		} catch (Exception e) {
			// ignore
		}
		return null;
	}

	private static void addToIndex(CacheScope scope, String ctxId, String fullKey) throws Exception {
		String idxKey = indexKey(scope, ctxId);
		if (idxKey == null) {
			return;
		}
		Set<String> set = readIndex(idxKey);
		if (set.add(fullKey)) {
			writeIndex(idxKey, set);
		}
	}

	private static void removeFromIndex(CacheScope scope, String ctxId, String fullKey) throws Exception {
		String idxKey = indexKey(scope, ctxId);
		if (idxKey == null) {
			return;
		}
		Set<String> set = readIndex(idxKey);
		if (set.remove(fullKey)) {
			if (set.isEmpty()) {
				CacheProvider.get().delete(idxKey);
			} else {
				writeIndex(idxKey, set);
			}
		}
	}

	private static String indexKey(CacheScope scope, String ctxId) throws Exception {
		switch (scope) {
		case USER:
			return NS + "IDX:USER:" + ctxId;
		case SESSION:
			return NS + "IDX:SESSION:" + ctxId;
		case GLOBAL:
			return NS + "IDX:GLOBAL";
		default:
			return null;
		}
	}

	private static Set<String> readIndex(String idxKey) throws Exception {
		DistCache cache = CacheProvider.get();
		byte[] data = cache.getBytes(idxKey);
		if (data == null) {
			return new HashSet<>();
		}
		try {
			String json = new String(data, StandardCharsets.UTF_8);
			String[] arr = fromJson(json, String[].class);
			if (arr == null) {
				return new HashSet<>();
			}
			return new HashSet<>(Arrays.asList(arr));
		} catch (Exception e) {
			return new HashSet<>();
		}
	}

	private static void writeIndex(String idxKey, Set<String> set) {
		try {
			String json = toJson(set.toArray(new String[0]));
			CacheProvider.get().putBytes(idxKey, json.getBytes(StandardCharsets.UTF_8), ttlSeconds(INDEX_TTL));
		} catch (Exception e) {
			// ignore
		}
	}

	private static String toJson(Object obj) throws Exception {
		java.lang.reflect.Method m = JTools.class.getMethod("toJSONString", Object.class);
		return (String) m.invoke(null, obj);
	}

	@SuppressWarnings("unchecked")
	private static <T> T fromJson(String json, Class<T> type) throws Exception {
		java.lang.reflect.Method m = JTools.class.getMethod("fromJSONString", String.class, Class.class);
		return (T) m.invoke(null, json, type);
	}
}
