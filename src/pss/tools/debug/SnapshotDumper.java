package pss.tools.debug;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;
import java.nio.charset.StandardCharsets;

/**
 * Persists snapshots to disk for later inspection. The format is a very simple
 * JSON structure produced without external libraries.
 */
public final class SnapshotDumper {
    private SnapshotDumper() {
    }

    public static void dump(GraphSnapshot.Snapshot snap, String requestId) {
        if (snap == null) {
            return;
        }
        File dir = new File(GraphSnapshotConfig.dumpDir());
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File f = new File(dir, requestId + "-" + snap.label + ".json");
        try (Writer w = new OutputStreamWriter(new FileOutputStream(f), StandardCharsets.UTF_8)) {
            w.write("{\n");
            w.write("  \"graphHash\": \"" + escape(snap.graphHash) + "\",\n");
            w.write("  \"nodes\": [\n");
            boolean firstNode = true;
            for (GraphSnapshot.NodeSig n : snap.nodes.values()) {
                if (!firstNode) {
                    w.write(",\n");
                }
                firstNode = false;
                w.write("    {\n");
                w.write("      \"id\": \"" + escape(n.id) + "\",\n");
                w.write("      \"type\": \"" + escape(n.type) + "\",\n");
                w.write("      \"props\": { ");
                boolean first = true;
                for (Map.Entry<String, String> e : n.props.entrySet()) {
                    if (!first) {
                        w.write(", ");
                    }
                    first = false;
                    w.write("\"" + escape(e.getKey()) + "\": \"" + escape(e.getValue()) + "\"");
                }
                w.write(" },\n");
                w.write("      \"refs\": [");
                first = true;
                for (String r : n.refs) {
                    if (!first) {
                        w.write(", ");
                    }
                    first = false;
                    w.write("\"" + escape(r) + "\"");
                }
                w.write("]\n    }");
            }
            w.write("\n  ]\n}");
        } catch (IOException e) {
            // Ignore errors on debug output
        }
    }

    private static String escape(String s) {
        if (s == null) {
            return "";
        }
        return s.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}

