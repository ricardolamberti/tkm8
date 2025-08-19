package pss.history;

import pss.core.tools.PssLogger;
import pss.www.platform.applications.JHistoryProvider;
import pss.www.platform.actions.resolvers.JDoPssActionResolver;

final class LazyJHistoryProvider extends JHistoryProvider {
  private final HistoryStore store;
  private final String pKey;
  private final JDoPssActionResolver resolver;
  private volatile boolean loaded;
  private volatile JHistoryProvider real;

  LazyJHistoryProvider(HistoryStore s, String hId, String providerKey, JDoPssActionResolver r) {
    this.store=s; this.pKey="P:"+hId+":"+providerKey; this.resolver=r;
  }

  JHistoryProvider load() {
    if (loaded) return real;
    synchronized (this) {
      if (loaded) return real;
      byte[] base = getOrThrow(store, pKey);
      try {
        JHistoryProvider p = new JHistoryProvider();
        p.unSerializeShallow(new String(base, java.nio.charset.StandardCharsets.UTF_8));
        p.setAction(new LazyBizAction(store, "A:"+pKey, resolver));
        this.real = p; this.loaded=true; return p;
      } catch (Exception e) { throw new RuntimeException(e); }
    }
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
