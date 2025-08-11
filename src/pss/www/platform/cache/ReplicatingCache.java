package pss.www.platform.cache;

import pss.core.data.BizPssConfig;

public class ReplicatingCache implements DistCache {

    private final DistCache local;
    private final DistCache remote;
    private final boolean replicationEnabled;

    public ReplicatingCache(DistCache local, DistCache remote, boolean enabled) {
        this.local = local;
        this.remote = remote;
        this.replicationEnabled = enabled && (remote != null);
    }

    @Override
    public byte[] getBytes(String key) throws Exception {
        byte[] v = local.getBytes(key);
        if (v != null)
            return v;
        if (replicationEnabled) {
            v = remote.getBytes(key);
            if (v != null) {
                local.putBytes(key, v, BizPssConfig.getMemcachedDefaultTtlSeconds());
            }
        }
        return v;
    }

    @Override
    public void putBytes(String key, byte[] value, long ttlSeconds) throws Exception {
        local.putBytes(key, value, ttlSeconds);
        if (replicationEnabled) {
            remote.putBytes(key, value, ttlSeconds);
        }
    }

    @Override
    public void delete(String key) throws Exception {
        local.delete(key);
        if (replicationEnabled) {
            remote.delete(key);
        }
    }

    @Override
    public boolean isHealthy() {
        return replicationEnabled ? (local.isHealthy() && remote.isHealthy()) : local.isHealthy();
    }
}
