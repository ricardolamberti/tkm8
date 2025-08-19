package pss.history;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import pss.core.tools.JTools;
import pss.core.win.actions.BizAction;
import pss.www.platform.applications.JHistory;
import pss.www.platform.applications.JHistoryProvider;
import pss.www.platform.applications.JWebHistoryManager;

public final class HistoryChunker {
  public static HistoryEnvelope saveChunked(JWebHistoryManager hm, HistoryStore store, int ttlSec) throws Exception {
    String hmId = "HM:" + UUID.randomUUID();
    List<String> hIds = new ArrayList<>();
    for (JHistory h : hm.getActionHistory()) {
      String hId = "H:" + UUID.randomUUID();
      store.put(hId, serializeHistoryShallow(h), ttlSec);
      Map<String,JHistoryProvider> providers = h.getProviders();
      if (providers != null) {
        for (Map.Entry<String,JHistoryProvider> e : providers.entrySet()) {
          String pKey = "P:" + hId + ":" + e.getKey();
          store.put(pKey, serializeProviderShallow(e.getValue()), ttlSec);
          String aKey = "A:" + pKey;
          store.put(aKey, encodeActionRef(e.getValue().getAction()), ttlSec);
        }
      }
      hIds.add(hId);
    }
    HistoryEnvelope env = new HistoryEnvelope();
    env.managerId = hmId;
    env.homeHistoryId = hIds.isEmpty()? null : hIds.get(0);
    env.historyIds = hIds;
    env.createdAt = System.currentTimeMillis();
    store.put(hmId, HistoryJson.toJson(env).getBytes(java.nio.charset.StandardCharsets.UTF_8), ttlSec);
    return env;
  }

  private static byte[] serializeHistoryShallow(JHistory h) throws Exception {
    return JTools.stringToByteArray(h.serializeShallow());
  }
  private static byte[] serializeProviderShallow(JHistoryProvider p) throws Exception {
    return JTools.stringToByteArray(p.serializeShallow());
  }
  public static byte[] encodeActionRef(BizAction a) {
    String ref = a == null ? "" : (a.getDomain() + ":" + a.getName() + "|" + a.getParamsAsQueryString());
    return ref.getBytes(java.nio.charset.StandardCharsets.UTF_8);
  }
}
