package pss.history;

import pss.www.platform.cache.CacheProvider;
import pss.www.platform.cache.DistCache;

public final class DistCacheHistoryStore implements HistoryStore {
  private final DistCache cache = CacheProvider.get();
  @Override public void put(String k, byte[] d, int ttl) throws Exception { cache.putBytes(k, d, ttl); }
  @Override public byte[] get(String k) throws Exception { return cache.getBytes(k); }
  @Override public void remove(String k) throws Exception { try { cache.delete(k); } catch (Throwable ignore) {} }
}
