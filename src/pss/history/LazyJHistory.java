package pss.history;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import pss.core.tools.PssLogger;
import pss.www.platform.applications.JHistory;
import pss.www.platform.applications.JHistoryProvider;
import pss.www.platform.actions.resolvers.JDoPssActionResolver;

final class LazyJHistory extends JHistory {
  private final HistoryStore store;
  private final String hId;
  private final JDoPssActionResolver resolver;
  private volatile boolean baseLoaded;
  private Map<String, LazyJHistoryProvider> providersLazy;

  LazyJHistory(HistoryStore s, String id, JDoPssActionResolver r) { this.store=s; this.hId=id; this.resolver=r; }

  private void ensureBaseLoaded() {
    if (baseLoaded) return;
    synchronized (this) {
      if (baseLoaded) return;
      byte[] data = getOrThrow(store, hId);
      try {
        this.unSerializeShallow(new String(data, java.nio.charset.StandardCharsets.UTF_8));
        Set<String> keys = this.getProviderKeysFromShallow();
        providersLazy = new LinkedHashMap<>();
        for (String k : keys) providersLazy.put(k, new LazyJHistoryProvider(store, hId, k, resolver));
        baseLoaded = true;
      } catch (Exception e) { throw new RuntimeException(e); }
    }
  }

  @Override public Map<String,JHistoryProvider> getProviders() {
    ensureBaseLoaded();
    return new java.util.AbstractMap<String,JHistoryProvider>() {
      @Override public Set<Entry<String,JHistoryProvider>> entrySet() {
        ensureBaseLoaded();
        Map<String,JHistoryProvider> real = new LinkedHashMap<>();
        providersLazy.forEach((k,v) -> real.put(k, v.load()));
        return real.entrySet();
      }
      @Override public JHistoryProvider get(Object key) {
        ensureBaseLoaded();
        LazyJHistoryProvider p = providersLazy.get(key);
        return p == null ? null : p.load();
      }
      @Override public int size() { ensureBaseLoaded(); return providersLazy.size(); }
    };
  }

  private static byte[] getOrThrow(HistoryStore s, String key) {
    boolean metrics = Boolean.getBoolean("pss.history.lazy.metrics");
    try {
      byte[] b = s.get(key);
      if (metrics) {
        if (b == null) PssLogger.logWarn("MISS " + key);
        else PssLogger.logDebug("HIT " + key);
      }
      if (b == null) throw new IllegalStateException("Missing history chunk: " + key);
      return b;
    } catch (Exception ex) { throw new RuntimeException(ex); }
  }
}
