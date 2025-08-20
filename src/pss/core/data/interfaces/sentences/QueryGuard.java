package pss.core.data.interfaces.sentences;

public final class QueryGuard {
        public enum Decision { OK, WARN, BLOCK }

        public Decision decide(PlanInfo p) {
                if (p == null)
                        return Decision.OK;
                if (p.hasCartesianJoin)
                        return Decision.BLOCK;
                if (p.hasSeqScanOnBigTable && p.estRows > 1_000_000)
                        return Decision.BLOCK;
                if (p.totalCost > 50_000_000)
                        return Decision.BLOCK;
                if (p.estRows > 1_000_000)
                        return Decision.WARN;
                return Decision.OK;
        }
}
