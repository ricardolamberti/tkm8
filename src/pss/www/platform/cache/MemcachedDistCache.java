package pss.www.platform.cache;

import java.net.InetSocketAddress;

import net.spy.memcached.MemcachedClient;
import pss.core.data.BizPssConfig;

public class MemcachedDistCache implements DistCache, AutoCloseable {

    private final MemcachedClient client;
    private final int opTimeoutMs;

    public MemcachedDistCache(String host, int port, int opTimeoutMs) throws Exception {
        this.client = new MemcachedClient(new InetSocketAddress(host, port));
        this.opTimeoutMs = opTimeoutMs;
    }

    @Override
    public byte[] getBytes(String key) {
        Object v = client.get(key);
        return (v instanceof byte[]) ? (byte[]) v : null;
    }

    @Override
    public void putBytes(String key, byte[] value, long ttlSeconds) {
        int ttl = (int) (ttlSeconds > 0 ? ttlSeconds : BizPssConfig.getMemcachedDefaultTtlSeconds());
        client.set(key, ttl, value);
    }

    @Override
    public void delete(String key) {
        client.delete(key);
    }

    @Override
    public boolean isHealthy() {
        return !client.getAvailableServers().isEmpty();
    }

    @Override
    public void close() {
        try {
            client.shutdown();
        } catch (Exception ignore) {
        }
    }
}
