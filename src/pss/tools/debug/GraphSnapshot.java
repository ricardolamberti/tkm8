package pss.tools.debug;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.Map;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Utility helpers to take deterministic snapshots of an object graph and
 * produce diffs between two snapshots.
 */
public class GraphSnapshot {

    /** Extracts the identity, properties and references of objects that
     * participate in the graph. Implementations must ensure that the returned
     * data is stable (no random or volatile data).
     */
    public interface Extractor {
        String getId(Object o);
        Map<String, String> getProps(Object o);
        Collection<?> getRefs(Object o);
    }

    /** Signature for a single node in the graph. */
    public static final class NodeSig {
        public final String id;
        public final String type;
        public final SortedMap<String, String> props;
        public final SortedSet<String> refs;

        public NodeSig(String id, String type, Map<String, String> props, Collection<String> refs) {
            this.id = id;
            this.type = type;
            this.props = new TreeMap<>(props != null ? props : Collections.emptyMap());
            this.refs = new TreeSet<>(refs != null ? refs : Collections.emptySet());
        }

        /**
         * Deterministic textual representation of this node.  Includes id,
         * type, properties and references sorted alphabetically.
         */
        public String canonicalString() {
            StringBuilder sb = new StringBuilder();
            sb.append(id).append('\n');
            sb.append(type).append('\n');
            for (Map.Entry<String, String> e : props.entrySet()) {
                sb.append(e.getKey()).append('=').append(e.getValue()).append('\n');
            }
            sb.append("#refs\n");
            for (String r : refs) {
                sb.append(r).append('\n');
            }
            return sb.toString();
        }

        /** SHA-256 hash of the {@link #canonicalString()}. */
        public String hash() {
            return sha256(canonicalString());
        }
    }

    /** Snapshot of a full graph. */
    public static final class Snapshot {
        public final String label;
        public final SortedMap<String, NodeSig> nodes;
        public final String graphHash;

        public Snapshot(String label, SortedMap<String, NodeSig> nodes, String graphHash) {
            this.label = label;
            this.nodes = nodes;
            this.graphHash = graphHash;
        }
    }

    /**
     * Takes a snapshot of the graph reachable from the provided roots using
     * breadth first search.  Cycles are avoided using the object's id provided
     * by the {@link Extractor}.
     */
    public static Snapshot take(String label, Collection<?> roots, Extractor extractor) {
        if (roots == null) {
            roots = Collections.emptyList();
        }
        SortedMap<String, NodeSig> nodes = new TreeMap<>();
        Deque<Object> dq = new ArrayDeque<>(roots);
        java.util.Set<String> seen = new java.util.HashSet<>();
        while (!dq.isEmpty()) {
            Object o = dq.poll();
            if (o == null) {
                continue;
            }
            String id = extractor.getId(o);
            if (id == null || seen.contains(id)) {
                continue;
            }
            seen.add(id);
            Map<String, String> props = extractor.getProps(o);
            if (props == null) {
                props = Collections.emptyMap();
            }
            Collection<?> rawRefs = extractor.getRefs(o);
            java.util.List<String> refIds = new ArrayList<>();
            if (rawRefs != null) {
                for (Object r : rawRefs) {
                    if (r == null) {
                        continue;
                    }
                    String rid = extractor.getId(r);
                    if (rid != null) {
                        refIds.add(rid);
                        if (!seen.contains(rid)) {
                            dq.add(r);
                        }
                    }
                }
            }
            NodeSig ns = new NodeSig(id, o.getClass().getName(), props, refIds);
            nodes.put(id, ns);
        }
        // Build global hash deterministically by iterating over nodes ordered by id
        StringBuilder concat = new StringBuilder();
        for (NodeSig n : nodes.values()) {
            concat.append(n.hash());
        }
        String graphHash = sha256(concat.toString());
        return new Snapshot(label, nodes, graphHash);
    }

    /**
     * Produces a human readable diff between two snapshots.  At most
     * {@code maxLines} are returned.
     */
    public static String diff(Snapshot a, Snapshot b, int maxLines) {
        if (a == null || b == null) {
            return "One snapshot is null";
        }
        StringBuilder sb = new StringBuilder();
        if (!a.graphHash.equals(b.graphHash)) {
            sb.append("Hash A: ").append(a.graphHash).append(" Hash B: ").append(b.graphHash).append('\n');
        } else {
            sb.append("Hash: ").append(a.graphHash).append('\n');
        }
        int lines = 1;
        SortedSet<String> ids = new TreeSet<>();
        ids.addAll(a.nodes.keySet());
        ids.addAll(b.nodes.keySet());
        for (String id : ids) {
            if (lines >= maxLines) {
                sb.append("...");
                break;
            }
            NodeSig na = a.nodes.get(id);
            NodeSig nb = b.nodes.get(id);
            if (na == null) {
                sb.append("Only in B: ").append(id).append('\n');
                lines++;
                continue;
            }
            if (nb == null) {
                sb.append("Only in A: ").append(id).append('\n');
                lines++;
                continue;
            }
            if (!na.type.equals(nb.type)) {
                sb.append("Type differs for ").append(id).append(':').append(' ')
                  .append(na.type).append(" vs ").append(nb.type).append('\n');
                lines++;
            }
            if (lines >= maxLines) {
                sb.append("...");
                break;
            }
            if (!na.props.equals(nb.props)) {
                sb.append("Props differ for ").append(id).append(':').append(' ')
                  .append(na.props).append(" vs ").append(nb.props).append('\n');
                lines++;
            }
            if (lines >= maxLines) {
                sb.append("...");
                break;
            }
            if (!na.refs.equals(nb.refs)) {
                sb.append("Refs differ for ").append(id).append(':').append(' ')
                  .append(na.refs).append(" vs ").append(nb.refs).append('\n');
                lines++;
            }
        }
        return sb.toString();
    }

    /** Helper to compute SHA-256 in hex form. */
    private static String sha256(String in) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(in.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
    }
}

