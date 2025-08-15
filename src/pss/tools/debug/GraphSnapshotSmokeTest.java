package pss.tools.debug;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/** Simple manual smoke test for the GraphSnapshot utility. */
public class GraphSnapshotSmokeTest {

    /** Simple stub node used only for this smoke test. */
    static class Stub {
        final String id;
        final String name;
        final List<Stub> refs = new ArrayList<>();
        Stub(String id, String name) { this.id = id; this.name = name; }
    }

    public static void main(String[] args) {
        GraphSnapshot.Extractor ex = new GraphSnapshot.Extractor() {
            @Override
            public String getId(Object o) {
                return ((Stub) o).id;
            }
            @Override
            public Map<String, String> getProps(Object o) {
                Map<String,String> m = new TreeMap<>();
                m.put("name", ((Stub)o).name);
                return m;
            }
            @Override
            public Collection<?> getRefs(Object o) {
                return ((Stub) o).refs;
            }
        };

        // Case 1: identical graphs
        Stub a1 = new Stub("1", "A");
        Stub a2 = new Stub("2", "B");
        a1.refs.add(a2);
        Stub b1 = new Stub("1", "A");
        Stub b2 = new Stub("2", "B");
        b1.refs.add(b2);
        GraphSnapshot.Snapshot s1 = GraphSnapshot.take("a", Arrays.asList(a1), ex);
        GraphSnapshot.Snapshot s2 = GraphSnapshot.take("b", Arrays.asList(b1), ex);
        System.out.println("Case1 hashes: " + s1.graphHash + " == " + s2.graphHash);
        System.out.println(GraphSnapshot.diff(s1, s2, 10));

        // Case 2: modify one reference
        Stub c1 = new Stub("1", "A");
        Stub c2 = new Stub("2", "B");
        // no reference added -> graph differs
        GraphSnapshot.Snapshot s3 = GraphSnapshot.take("c", Arrays.asList(c1), ex);
        System.out.println("Case2 hashes: " + s1.graphHash + " vs " + s3.graphHash);
        System.out.println(GraphSnapshot.diff(s1, s3, 10));
    }
}

