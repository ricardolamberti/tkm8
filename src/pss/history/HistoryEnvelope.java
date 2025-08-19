package pss.history;

public final class HistoryEnvelope implements java.io.Serializable {
  public String version = "v1";
  public String managerId;
  public String homeHistoryId;
  public java.util.List<String> historyIds;
  public long createdAt;
}
