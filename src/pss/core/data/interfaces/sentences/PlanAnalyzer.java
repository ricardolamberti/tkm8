package pss.core.data.interfaces.sentences;

import java.sql.Connection;

import pss.core.data.interfaces.sentences.JRegJDBC.Dialect;

public interface PlanAnalyzer {
        PlanInfo analyze(Connection c, String sql, Dialect d) throws Exception;
}
