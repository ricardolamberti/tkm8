package pss.core.connectivity.messageManager.client.session;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SessionManager {
	private static SessionManager sessionManager = null;

	public static SessionManager getSessionManager() {
		if (sessionManager!=null) return sessionManager;
		return sessionManager=new SessionManager();
	}

	public static ISessionObject retrieveSessionObject(String sessionId, String idObject) {
		return SessionManager.getSessionManager().getSession(sessionId).getSessionObjects().get(idObject);
	}
	
	public static void storeSessionObject(String sessionId, String idObject,ISessionObject obj) {
		SessionManager.getSessionManager().getSession(sessionId).getSessionObjects().put(idObject,obj);
	}
	
	Map<String,Session> sessions;

	private Map<String,Session> getSessions() {
		if (sessions!=null)	return sessions;
		Map<String,Session> s = Collections.synchronizedMap(new HashMap<String,Session>());
		return sessions =s ;
	}

	
	private Session getSession(String sessionId) {
		Session s = getSessions().get(sessionId);
		if (s!=null) return s;
		s = new Session();
		getSessions().put(sessionId, s);
		return s;
	}
	
}
