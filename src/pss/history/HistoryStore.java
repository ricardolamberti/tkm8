package pss.history;

public interface HistoryStore {
  void put(String key, byte[] data, int ttlSec) throws Exception;
  byte[] get(String key) throws Exception;
  void remove(String key) throws Exception;
}
