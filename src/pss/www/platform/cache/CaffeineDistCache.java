package pss.www.platform.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

public class CaffeineDistCache implements DistCache {
    private final Cache<String, byte[]> cache;

    public CaffeineDistCache() {
        this.cache = Caffeine.newBuilder().build();
    }

    @Override
    public byte[] getBytes(String key) {
        return cache.getIfPresent(key);
    }

    @Override
    public void putBytes(String key, byte[] value, long ttlSeconds) {
        cache.put(key, value);
    }

    @Override
    public void delete(String key) {
        cache.invalidate(key);
    }

    @Override
    public boolean isHealthy() {
        return true;
    }
}
