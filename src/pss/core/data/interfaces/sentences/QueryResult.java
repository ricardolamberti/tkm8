package pss.core.data.interfaces.sentences;

public class QueryResult {
        public final boolean blocked;
        public final String rawPlanText;
        public final int rowCount;
        public final boolean truncated;

        public QueryResult(boolean blocked, String rawPlanText, int rowCount, boolean truncated) {
                this.blocked = blocked;
                this.rawPlanText = rawPlanText;
                this.rowCount = rowCount;
                this.truncated = truncated;
        }

        public static QueryResult blocked(String msg, String plan) {
                return new QueryResult(true, plan, 0, false);
        }
}
