package pss.history;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import pss.core.tools.collections.JList;
import pss.www.platform.applications.JHistory;
import pss.www.platform.applications.JWebHistoryManager;
import pss.www.platform.actions.resolvers.JDoPssActionResolver;

public final class LazyJWebHistoryManager extends JWebHistoryManager {
  private final HistoryStore store;
  private final HistoryEnvelope env;
  private final JDoPssActionResolver resolver;
  private final Map<String, LazyJHistory> cache = new ConcurrentHashMap<>();

  public static LazyJWebHistoryManager load(HistoryStore store, HistoryEnvelope env, JDoPssActionResolver r) {
    return new LazyJWebHistoryManager(store, env, r);
  }
  private LazyJWebHistoryManager(HistoryStore s, HistoryEnvelope e, JDoPssActionResolver r) {
    super(null);
    this.store=s; this.env=e; this.resolver=r;
  }

  @Override public JList<JHistory> getActionHistory() { return new LazyHistoryList(); }
  @Override public JHistory getHomePage() { return loadHistory(env.homeHistoryId); }

  private JHistory loadHistory(String hId) {
    if (hId == null) return null;
    return cache.computeIfAbsent(hId, id -> new LazyJHistory(store, id, resolver));
  }

  private final class LazyHistoryList extends java.util.AbstractList<JHistory> implements JList<JHistory> {
    @Override public JHistory get(int index) { return loadHistory(env.historyIds.get(index)); }
    @Override public int size() { return env.historyIds.size(); }
  }
}
