package pss.tools.debug;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import pss.core.services.records.JBaseRecord;
import pss.core.services.records.JRecord;
import pss.core.win.JBaseWin;
import pss.core.win.submits.JAct;

/**
 * Generic extractor for the core UI model. It retrieves stable properties and
 * references from windows, records and actions without triggering lazy
 * behaviour.
 */
public class CoreExtractor implements GraphSnapshot.Extractor {

    @Override
    public String getId(Object o) {
        try {
            if (o instanceof JBaseWin) {
                return ((JBaseWin) o).getUniqueId();
            }
            if (o instanceof JAct) {
                return ((JAct) o).getActionUniqueId();
            }
            if (o instanceof JRecord) {
                return ((JRecord) o).getUniqueId();
            }
            if (o instanceof JBaseRecord) {
                return ((JBaseRecord) o).getUniqueId();
            }
        } catch (Exception ignore) {
        }
        return null;
    }

    @Override
    public Map<String, String> getProps(Object o) {
        Map<String, String> props = new TreeMap<>();
        try {
            if (o instanceof JBaseWin) {
                JBaseWin win = (JBaseWin) o;
                try {
                    props.put("title", String.valueOf(win.GetTitle()));
                } catch (Exception ignore) {
                }
                props.put("embedded", String.valueOf(win.isEmbedded()));
                Boolean vis = invokeBoolean(win, "isVisible");
                if (vis != null) {
                    props.put("visible", String.valueOf(vis));
                }
                Boolean en = invokeBoolean(win, "isEnabled");
                if (en != null) {
                    props.put("enabled", String.valueOf(en));
                }
                props.put("childrenCount", String.valueOf(getChildrenCount(win)));
            } else if (o instanceof JAct) {
                JAct act = (JAct) o;
                props.put("name", String.valueOf(act.getName()));
                Boolean en = invokeBoolean(act, "isEnabled");
                if (en != null) {
                    props.put("enabled", String.valueOf(en));
                }
            } else if (o instanceof JRecord || o instanceof JBaseRecord) {
                JBaseRecord rec = (JBaseRecord) o;
                try {
                    props.put("table", String.valueOf(rec.GetTable()));
                } catch (Exception ignore) {
                }
                try {
                    Method m = rec.getClass().getMethod("getState");
                    Object st = m.invoke(rec);
                    props.put("state", String.valueOf(st));
                } catch (Exception ignore) {
                }
                try {
                    Method m = rec.getClass().getMethod("getPK");
                    Object pk = m.invoke(rec);
                    props.put("pk", String.valueOf(pk));
                } catch (Exception ignore) {
                }
            }
        } catch (Exception ignore) {
        }
        return props;
    }

    @Override
    public Collection<?> getRefs(Object o) {
        List<Object> refs = new ArrayList<>();
        try {
            if (o instanceof JBaseWin) {
                JBaseWin win = (JBaseWin) o;
                // children
                try {
                    Method m = win.getClass().getMethod("getChildren");
                    Object res = m.invoke(win);
                    addAll(refs, res);
                } catch (Exception ignore) {
                }
                // parent
                try {
                    JBaseWin p = win.getParent();
                    if (p != null) {
                        refs.add(p);
                    }
                } catch (Exception ignore) {
                }
            } else if (o instanceof JAct) {
                try {
                    Method m = o.getClass().getMethod("getWinResult");
                    Object res = m.invoke(o);
                    if (res instanceof JBaseWin) {
                        refs.add(res);
                    }
                } catch (Exception ignore) {
                }
            } else if (o instanceof JRecord) {
                // Related records already loaded
                try {
                    Method m = o.getClass().getMethod("getRelatedRecords");
                    Object res = m.invoke(o);
                    addAll(refs, res);
                } catch (Exception ignore) {
                }
            }
        } catch (Exception ignore) {
        }
        return refs;
    }

    private static Boolean invokeBoolean(Object o, String name) {
        try {
            Method m = o.getClass().getMethod(name);
            Object r = m.invoke(o);
            if (r instanceof Boolean) {
                return (Boolean) r;
            }
        } catch (Exception ignore) {
        }
        return null;
    }

    private static int getChildrenCount(JBaseWin win) {
        try {
            Method m = win.getClass().getMethod("getChildren");
            Object res = m.invoke(win);
            if (res instanceof Collection) {
                return ((Collection<?>) res).size();
            }
            if (res != null && res.getClass().isArray()) {
                return Array.getLength(res);
            }
        } catch (Exception ignore) {
        }
        return 0;
    }

    private static void addAll(List<Object> out, Object data) {
        if (data == null) {
            return;
        }
        if (data instanceof Collection) {
            out.addAll((Collection<?>) data);
            return;
        }
        if (data.getClass().isArray()) {
            int len = Array.getLength(data);
            for (int i = 0; i < len; i++) {
                out.add(Array.get(data, i));
            }
        } else {
            out.add(data);
        }
    }
}

