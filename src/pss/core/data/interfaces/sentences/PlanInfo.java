package pss.core.data.interfaces.sentences;

public final class PlanInfo {
        public long estRows;
        public double totalCost;
        public boolean hasSeqScanOnBigTable;
        public boolean hasCartesianJoin;
        public String rawPlanText;
}
