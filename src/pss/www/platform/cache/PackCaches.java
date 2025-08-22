package pss.www.platform.cache;

import java.util.concurrent.TimeUnit;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Weigher;

public final class PackCaches {
  private PackCaches() {}

  // Cachea el pack (b64url(deflate(JSON))) por uniqueId y "sello" (ver abajo).
  public static final Cache<String, String> WIN_PACK = Caffeine.newBuilder()
      .maximumWeight(64L * 1024 * 1024) // 64MB de packs
      .weigher(new Weigher<String,String>() {
        @Override public int weigh(String k, String v) { return v != null ? v.length() : 0; }
      })
      .expireAfterAccess(10, TimeUnit.MINUTES)
      .recordStats()
      .build();

  public static final Cache<String, String> REC_PACK = Caffeine.newBuilder()
      .maximumWeight(64L * 1024 * 1024)
      .weigher(new Weigher<String,String>() {
        @Override public int weigh(String k, String v) { return v != null ? v.length() : 0; }
      })
      .expireAfterAccess(10, TimeUnit.MINUTES)
      .recordStats()
      .build();

  public static void invalidateWinKey(String key) { WIN_PACK.invalidate(key); }
  public static void invalidateRecKey(String key) { REC_PACK.invalidate(key); }
}
