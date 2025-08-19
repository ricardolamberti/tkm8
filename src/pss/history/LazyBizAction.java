package pss.history;

import pss.core.tools.PssLogger;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.www.platform.actions.resolvers.JDoPssActionResolver;

final class LazyBizAction extends BizAction {
  private final HistoryStore store;
  private final String aKey;
  private final JDoPssActionResolver resolver;
  private volatile BizAction real;

  LazyBizAction(HistoryStore s, String k, JDoPssActionResolver r) { this.store=s; this.aKey=k; this.resolver=r; }

  private BizAction ensure() {
    if (real != null) return real;
    synchronized (this) {
      if (real != null) return real;
      String ref = new String(getOrThrow(store, aKey), java.nio.charset.StandardCharsets.UTF_8);
      real = (ref==null || ref.isEmpty()) ? null : resolver.resolve(ref);
      return real;
    }
  }

  @Override public String getName() { BizAction a = ensure(); return a==null?null:a.getName(); }
  @Override public String getDomain() { BizAction a = ensure(); return a==null?null:a.getDomain(); }
  @Override public String getProviderName() { BizAction a = ensure(); return a==null?null:a.getProviderName(); }
  @Override public JAct getObjSubmit() { BizAction a = ensure(); return a==null?null:a.getObjSubmit(); }
  @Override public void setObjSubmit(JAct s) { BizAction a = ensure(); if (a!=null) a.setObjSubmit(s); }
  @Override public boolean hasSubmit() { BizAction a = ensure(); return a!=null && a.hasSubmit(); }
  @Override public boolean isSameAction(BizAction other) { BizAction a = ensure(); return a!=null && a.isSameAction(other); }
  @Override public String getParamsAsQueryString() { BizAction a = ensure(); return a==null?"":a.getParamsAsQueryString(); }

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
