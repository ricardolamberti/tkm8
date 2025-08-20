package common.cache;

import pss.www.platform.actions.JWebActionFactory;
import pss.www.platform.actions.JWebRequest;

/** Resolves user and session identifiers from the current request. */
public final class UserContext {
    private UserContext() {
    }

    /**
     * Current user identifier.
     *
     * @return resolved user id or {@code "anon"} if not available
     */
    public static String currentUserId() {
        JWebRequest req = JWebActionFactory.getCurrentRequest();
        if (req != null) {
            Object id = req.getAttribute("userId");
            if (id != null) {
                return String.valueOf(id);
            }
            // TODO: use JWT token when available
        }
        return "anon";
    }

    /**
     * Current session identifier.
     *
     * @return resolved session id or {@code "nopage"} if not available
     */
    public static String currentSessionId() {
        JWebRequest req = JWebActionFactory.getCurrentRequest();
        if (req != null) {
            Object id = req.getAttribute("pageId");
            if (id != null) {
                return String.valueOf(id);
            }
            id = req.getAttribute("requestId");
            if (id != null) {
                return String.valueOf(id);
            }
        }
        return "nopage";
    }
}
