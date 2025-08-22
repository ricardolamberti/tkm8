package pss.core.data.interfaces.sentences;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BasicPlanAnalyzer implements PlanAnalyzer {

    // Umbral para considerar "tabla grande" en PG (ajustá a tu entorno)
    private static final long PG_BIG_TABLE_BYTES = 500L * 1024 * 1024;

    @Override
    public PlanInfo analyze(Connection c, String sql, JRegJDBC.Dialect d) throws Exception {
        switch (d) {
            case POSTGRES:
                return analyzePg(c, sql);
            case ORACLE:
                return analyzeOracle(c, sql);
            case SQLSERVER:
                return analyzeSqlServer(c, sql);
            case HIBERNATE:
            default:
                PlanInfo p = new PlanInfo();
                p.rawPlanText = "Plan analyzer not implemented for dialect: " + d;
                return p;
        }
    }

    /* ======================== PostgreSQL ======================== */

    private PlanInfo analyzePg(Connection conn, String sql) throws SQLException {
        PlanInfo info = new PlanInfo();
        StringBuilder plan = new StringBuilder();

        try (Statement st = conn.createStatement()) {
            setTimeoutSafe(st, 5);
            try (ResultSet rs = st.executeQuery("EXPLAIN " + sql)) {
                while (rs.next()) plan.append(rs.getString(1)).append('\n');
            }
        }
        String planText = plan.toString();
        info.rawPlanText = planText;

        // Parseo de costo total (upper bound) y filas estimadas de la raíz
        // Ejemplo: "Nested Loop  (cost=0.85..12345.67 rows=890 width=...)"
        Matcher mc = Pattern.compile("cost=([0-9.]+)\\.\\.([0-9.]+)\\s+rows=([0-9]+)").matcher(planText);
        if (mc.find()) {
            info.totalCost = safeDouble(mc.group(2));
            info.estRows   = safeLong(mc.group(3));
        }

        // Detectar Seq Scan y chequear si es sobre "tabla grande"
        Set<String> seqScanTables = new HashSet<String>();
        Matcher ms = Pattern.compile("Seq Scan on\\s+([A-Za-z0-9_\\.]+)").matcher(planText);
        while (ms.find()) seqScanTables.add(ms.group(1));

        info.hasSeqScanOnBigTable = false;
        for (String rel : seqScanTables) {
            if (isPgBigTable(conn, rel)) { info.hasSeqScanOnBigTable = true; break; }
        }

        // Señal de join cartesiano (poco común en PG, pero por las dudas)
        info.hasCartesianJoin = planText.toLowerCase().contains("cartesian");
        return info;
    }

    private boolean isPgBigTable(Connection conn, String relNameMaybeSchema) {
        // Intenta por nombre calificado y por relname suelto
        String sqlQualified =
            "SELECT pg_total_relation_size((quote_ident(n.nspname)||'.'||quote_ident(c.relname))::regclass) AS sz " +
            "FROM pg_class c JOIN pg_namespace n ON n.oid=c.relnamespace " +
            "WHERE (n.nspname || '.' || c.relname) = ? ORDER BY sz DESC LIMIT 1";
        String sqlByRelname =
            "SELECT pg_total_relation_size(c.oid) AS sz " +
            "FROM pg_class c WHERE c.relname = ? ORDER BY sz DESC LIMIT 1";

        // 1) Si viene schema.tabla
        if (relNameMaybeSchema.indexOf('.') >= 0) {
            try (PreparedStatement ps = conn.prepareStatement(sqlQualified)) {
                ps.setString(1, relNameMaybeSchema);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) return rs.getLong("sz") >= PG_BIG_TABLE_BYTES;
                }
            } catch (Exception ignore) { /* sin permisos o no existe: continua */ }
        }

        // 2) Por relname
        String rel = relNameMaybeSchema.contains(".")
                ? relNameMaybeSchema.substring(relNameMaybeSchema.indexOf('.') + 1)
                : relNameMaybeSchema;
        try (PreparedStatement ps = conn.prepareStatement(sqlByRelname)) {
            ps.setString(1, rel);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getLong("sz") >= PG_BIG_TABLE_BYTES;
            }
        } catch (Exception ignore) {}
        return false;
    }

    /* ========================= ORACLE ========================== */

    private PlanInfo analyzeOracle(Connection conn, String sql) throws SQLException {
        PlanInfo info = new PlanInfo();
        StringBuilder sb = new StringBuilder();

        // 1) Generar el plan
        try (Statement st = conn.createStatement()) {
            setTimeoutSafe(st, 5);
            st.execute("EXPLAIN PLAN FOR " + sql);
        }

        // 2) Obtenerlo vía DBMS_XPLAN
        final String xplan =
            "SELECT plan_table_output " +
            "FROM TABLE(DBMS_XPLAN.DISPLAY(NULL, NULL, 'BASIC +COST +BYTES +CARDINALITY +PREDICATE'))";
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(xplan)) {
            while (rs.next()) sb.append(rs.getString(1)).append('\n');
        }
        String plan = sb.toString();
        info.rawPlanText = plan;

        // Parseo simple de COST y CARDINALITY (pueden aparecer en varias líneas)
        Matcher mc = Pattern.compile("(?i)cost\\s*=?\\s*([0-9]+)").matcher(plan);
        if (mc.find()) info.totalCost = safeDouble(mc.group(1));

        Matcher mr = Pattern.compile("(?i)cardinality\\s*=?\\s*([0-9]+)").matcher(plan);
        if (mr.find()) info.estRows = safeLong(mr.group(1));

        // Detección de join cartesiano
        info.hasCartesianJoin = plan.toLowerCase().contains("cartesian");

        // (Opcional) podrías detectar "FULL TABLE SCAN" y consultar DBA_* para tamaños
        info.hasSeqScanOnBigTable = false;
        return info;
    }

    /* ======================== SQL SERVER ======================= */

    private PlanInfo analyzeSqlServer(Connection conn, String sql) throws SQLException {
        PlanInfo info = new PlanInfo();
        String xml = null;

        try (Statement st = conn.createStatement()) {
            setTimeoutSafe(st, 5);
            st.execute("SET SHOWPLAN_XML ON");
            try (ResultSet rs = st.executeQuery(sql)) {
                if (rs.next()) xml = rs.getString(1);
            } finally {
                try { st.execute("SET SHOWPLAN_XML OFF"); } catch (Exception ignore) {}
            }
        }
        if (xml == null) xml = "<empty/>";
        info.rawPlanText = xml;

        // Costo estimado del subárbol
        Matcher mc = Pattern.compile("EstimatedTotalSubtreeCost=\"([0-9.]+)\"").matcher(xml);
        if (mc.find()) info.totalCost = safeDouble(mc.group(1));

        // EstimateRows: tomamos el máximo como aproximación
        double maxRows = 0d;
        Matcher mr = Pattern.compile("EstimateRows=\"([0-9.]+)\"").matcher(xml);
        while (mr.find()) {
            double v = safeDouble(mr.group(1));
            if (v > maxRows) maxRows = v;
        }
        info.estRows = (long) maxRows;

        // Heurística de cartesiano: presencia de "Cross" en el XML
        String lower = xml.toLowerCase();
        info.hasCartesianJoin = (lower.contains("crossjoin") || lower.contains("cross join") || lower.contains("crossapply"));

        // (Opcional) detectar Table Scan: PhysicalOp="Table Scan"
        info.hasSeqScanOnBigTable = lower.contains("physicalop=\"table scan\"");

        return info;
    }

    /* ======================== Utilitarios ====================== */

    private void setTimeoutSafe(Statement st, int seconds) {
        try {
            st.setQueryTimeout(seconds);
        } catch (SQLFeatureNotSupportedException | UnsupportedOperationException ignored) {
            // Algunos drivers viejos (p.ej. org.postgresql.jdbc4) no implementan setQueryTimeout.
            // En PG podés usar SET LOCAL statement_timeout desde afuera si querés (en tu flujo principal).
        } catch (SQLException ignored) {
        }
    }

    private static double safeDouble(String s) {
        try { return Double.parseDouble(s); } catch (Exception e) { return 0d; }
    }

    private static long safeLong(String s) {
        try { return Long.parseLong(s); } catch (Exception e) { return 0L; }
    }
}
