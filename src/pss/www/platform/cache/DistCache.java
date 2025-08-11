package pss.www.platform.cache;

public interface DistCache {
    byte[] getBytes(String key) throws Exception;
    void putBytes(String key, byte[] value, long ttlSeconds) throws Exception;
    void delete(String key) throws Exception;
    boolean isHealthy();
}
