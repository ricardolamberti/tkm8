package pss.tools.debug;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import pss.core.tools.PssLogger;

/**
 * Registry that stores DebugSnap instances per request id. Allows multiple
 * JWebRequest objects belonging to the same logical request to contribute to a
 * single snapshot.
 */
public final class DebugSnapRegistry {

    private DebugSnapRegistry() {}

    /** Snapshot of pre/post registered objects. */
    public static final class DebugSnap {
        public static final String STORAGE_POINTER = "POINTER";
        public static final String STORAGE_CACHE = "CACHE";

        static final class Entry {
            final String id;
            final String type;
            final String storage;
            final String hash;

            Entry(String id, String type, String storage, String hash) {
                this.id = id;
                this.type = type;
                this.storage = storage;
                this.hash = hash;
            }
        }

        final java.util.Map<String, Entry> pre = new java.util.LinkedHashMap<>();
        final java.util.Map<String, Entry> post = new java.util.LinkedHashMap<>();

        static String sha256(byte[] data) {
            try {
                java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA-256");
                byte[] d = md.digest(data);
                StringBuilder sb = new StringBuilder();
                for (byte b : d)
                    sb.append(String.format("%02x", b));
                return sb.toString();
            } catch (Exception e) {
                return "sha256-error:" + e.getMessage();
            }
        }

        static String sha256(String s) {
            return sha256(s.getBytes(java.nio.charset.StandardCharsets.UTF_8));
        }

        public void addPrePointer(String id, String type, String payloadStr) {
            pre.put(id, new Entry(id, type, STORAGE_POINTER, sha256(payloadStr)));
        }

        public void addPreCache(String id, String type, byte[] payloadBytes) {
            pre.put(id, new Entry(id, type, STORAGE_CACHE, sha256(payloadBytes)));
        }

        public void addPostPointer(String id, String type, String payloadStr) {
            post.put(id, new Entry(id, type, STORAGE_POINTER, sha256(payloadStr)));
        }

        public void addPostCache(String id, String type, byte[] payloadBytes) {
            post.put(id, new Entry(id, type, STORAGE_CACHE, sha256(payloadBytes)));
        }

        public String diff(int maxLines) {
            StringBuilder sb = new StringBuilder();
            int emitted = 0;

            for (String id : pre.keySet()) {
                if (!post.containsKey(id)) {
                    if (emitted++ < maxLines)
                        sb.append("- Falta en POST: ").append(id).append("\n");
                }
            }
            for (String id : post.keySet()) {
                if (!pre.containsKey(id)) {
                    if (emitted++ < maxLines)
                        sb.append("+ Extra en POST: ").append(id).append("\n");
                }
            }

            for (String id : pre.keySet()) {
                Entry a = pre.get(id);
                Entry b = post.get(id);
                if (b == null)
                    continue;

                if (!a.type.equals(b.type)) {
                    if (emitted++ < maxLines)
                        sb.append("~ Tipo difiere ").append(id).append(": ").append(a.type).append(" != ").append(b.type).append("\n");
                }
                if (!a.storage.equals(b.storage)) {
                    if (emitted++ < maxLines)
                        sb.append("~ Storage difiere ").append(id).append(": ").append(a.storage).append(" != ").append(b.storage).append("\n");
                }
                if (!a.hash.equals(b.hash)) {
                    if (emitted++ < maxLines)
                        sb.append("~ PayloadHash difiere ").append(id).append(": ").append(a.hash).append(" != ").append(b.hash).append("\n");
                }
                if (emitted >= maxLines) {
                    sb.append("... (más)\n");
                    break;
                }
            }
            if (emitted == 0)
                sb.append("SNAP OK: mapas e hashes coinciden.\n");
            return sb.toString();
        }
    }

    private static final class State {
        final DebugSnap snap = new DebugSnap();
        int refs = 0;
        long lastTouch = System.currentTimeMillis();
    }

    private static final ConcurrentHashMap<String, State> REGISTRY = new ConcurrentHashMap<>();
    private static final long TTL_MILLIS = Long.getLong("pss.debug.snap.ttlMillis", 5 * 60_000L);
    private static final int MAX_ENTRIES = Integer.getInteger("pss.debug.snap.maxEntries", 1000);

    public static void begin(String reqId) {
        if (reqId == null)
            return;
        State st = REGISTRY.compute(reqId, (k, v) -> {
            if (v == null)
                v = new State();
            v.refs++;
            v.lastTouch = System.currentTimeMillis();
            return v;
        });
        PssLogger.logInfo("[SNAP] begin req=" + reqId + " refs=" + st.refs);
        cleanup();
    }

    public static DebugSnap get(String reqId) {
        State st = REGISTRY.get(reqId);
        return st != null ? st.snap : null;
    }

    public static void touch(String reqId) {
        State st = REGISTRY.get(reqId);
        if (st != null) {
            st.lastTouch = System.currentTimeMillis();
            PssLogger.logDebug("[SNAP] touch req=" + reqId);
        }
        cleanup();
    }

    public static String finish(String reqId, int maxLines) {
        if (reqId == null)
            return null;
        State st = REGISTRY.get(reqId);
        if (st == null)
            return null;
        int refs = --st.refs;
        if (refs <= 0) {
            REGISTRY.remove(reqId);
            String diff = st.snap.diff(maxLines);
            PssLogger.logInfo("[SNAP] finish req=" + reqId + " refs=0");
            cleanup();
            return diff;
        } else {
            st.lastTouch = System.currentTimeMillis();
            PssLogger.logInfo("[SNAP] finish req=" + reqId + " refs=" + refs);
            return null;
        }
    }

    private static void cleanup() {
        long now = System.currentTimeMillis();
        for (Map.Entry<String, State> e : REGISTRY.entrySet()) {
            if (now - e.getValue().lastTouch > TTL_MILLIS) {
                REGISTRY.remove(e.getKey(), e.getValue());
            }
        }
        int size = REGISTRY.size();
        if (size > MAX_ENTRIES) {
            List<Map.Entry<String, State>> list = new ArrayList<>(REGISTRY.entrySet());
            list.sort(Comparator.comparingLong(a -> a.getValue().lastTouch));
            for (int i = 0; i < size - MAX_ENTRIES; i++) {
                Map.Entry<String, State> en = list.get(i);
                REGISTRY.remove(en.getKey(), en.getValue());
            }
        }
    }
}

