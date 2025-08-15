package pss.tools.debug;

/**
 * Configuration helpers for graph snapshot debugging. Values are read from
 * system properties so they can be toggled at runtime without code changes.
 */
public final class GraphSnapshotConfig {
    private GraphSnapshotConfig() {
    }

    /** Whether the snapshot feature is enabled. */
    public static boolean enabled() {
        return true;// Boolean.getBoolean("pss.debug.graphsnapshot.enabled");
    }

    /** Maximum number of diff lines to log. */
    public static int maxDiffLines() {
        return Integer.getInteger("pss.debug.graphsnapshot.maxdifflines", 200);
    }

    /** If true an exception will be thrown when the graphs differ. */
    public static boolean failOnDiff() {
        return true;/ Boolean.getBoolean("pss.debug.graphsnapshot.failOnDiff");
    }

    /** Directory where snapshots will be dumped as JSON files. */
    public static String dumpDir() {
        return System.getProperty("pss.debug.graphsnapshot.dir", "/dev/logs/snapshots");
    }
}

