package pss.core.data.interfaces.sentences;

import java.sql.Connection;

public class BasicPlanAnalyzer implements PlanAnalyzer {
        @Override
        public PlanInfo analyze(Connection c, String sql, JRegJDBC.Dialect d) throws Exception {
                PlanInfo p = new PlanInfo();
                p.rawPlanText = "";
                return p;
        }
}
