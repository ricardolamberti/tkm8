package pss.history;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class HistoryJson {
  public static String toJson(HistoryEnvelope e) {
    StringBuilder sb = new StringBuilder(128);
    sb.append("{\"v\":\"").append(e.version).append("\",");
    sb.append("\"m\":\"").append(e.managerId).append("\",");
    sb.append("\"h\":\"").append(e.homeHistoryId==null?"":e.homeHistoryId).append("\",");
    sb.append("\"l\":[");
    for (int i=0;i<e.historyIds.size();i++) {
      if (i>0) sb.append(',');
      sb.append('"').append(e.historyIds.get(i)).append('"');
    }
    sb.append("],\"t\":").append(e.createdAt).append('}');
    return sb.toString();
  }
  public static HistoryEnvelope fromJson(String s) {
    HistoryEnvelope e = new HistoryEnvelope();
    Map<String,String> map = parseFlat(s);
    e.version = map.get("v");
    e.managerId = map.get("m");
    String home = map.get("h");
    e.homeHistoryId = (home!=null && !home.isEmpty()) ? home : null;
    e.historyIds = parseList(map.get("l"));
    e.createdAt = Long.parseLong(map.get("t"));
    return e;
  }
  private static Map<String,String> parseFlat(String s) {
    Map<String,String> map = new HashMap<>();
    map.put("v", extract(s, "v"));
    map.put("m", extract(s, "m"));
    map.put("h", extract(s, "h"));
    map.put("l", extract(s, "l"));
    map.put("t", extract(s, "t"));
    return map;
  }
  private static String extract(String s, String key) {
    String pattern = "\""+key+"\":";
    int start = s.indexOf(pattern);
    if (start==-1) return null;
    start += pattern.length();
    char c = s.charAt(start);
    if (c=='\"') {
      int end = s.indexOf('"', start+1);
      return s.substring(start+1, end);
    } else if (c=='[') {
      int end = s.indexOf(']', start);
      return s.substring(start, end+1);
    } else {
      int end = s.indexOf(',', start);
      if (end==-1) end = s.indexOf('}', start);
      return s.substring(start, end);
    }
  }
  private static List<String> parseList(String s) {
    List<String> list = new ArrayList<>();
    if (s==null) return list;
    s = s.trim();
    if (s.startsWith("[")) s = s.substring(1);
    if (s.endsWith("]")) s = s.substring(0, s.length()-1);
    if (s.isEmpty()) return list;
    String[] parts = s.split("\\\"\\s*,\\s*\\\"");
    for (String p : parts) {
      String v = p.replace("\"", "");
      if (!v.isEmpty()) list.add(v);
    }
    return list;
  }
}
