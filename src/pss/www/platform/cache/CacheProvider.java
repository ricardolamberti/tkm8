package pss.www.platform.cache;

import pss.core.data.BizPssConfig;
import pss.core.tools.PssLogger;

public final class CacheProvider {
    private static DistCache INSTANCE;

    private CacheProvider() {}

    public static synchronized DistCache get() {
        if (INSTANCE != null)
            return INSTANCE;
        DistCache local = buildLocalCaffeine();
        if (BizPssConfig.isCacheReplicationEnabled()) {
            try {
                DistCache mem = new MemcachedDistCache(
                        BizPssConfig.getMemcachedHost(),
                        BizPssConfig.getMemcachedPort(),
                        BizPssConfig.getMemcachedTimeoutMs());
                INSTANCE = new ReplicatingCache(local, mem, true);
                return INSTANCE;
            } catch (Exception e) {
                PssLogger.logError("Memcached disabled (boot error). Falling back to local cache");
                PssLogger.logError(e);
            }
        }
        INSTANCE = local;
        return INSTANCE;
    }

    public static synchronized DistCache maybeGet() {
        return INSTANCE;
    }

    private static DistCache buildLocalCaffeine() {
        return new CaffeineDistCache();
    }
}
