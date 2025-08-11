package pss.core.connectivity.messageManager.client.session;

import java.util.HashMap;
import java.util.Map;

public class Session {
	Map<String,ISessionObject> sessionObjects;

	public Map<String,ISessionObject> getSessionObjects() {
		if (sessionObjects!=null)	return sessionObjects;
		HashMap<String,ISessionObject> s = new HashMap<String,ISessionObject> ();
		return sessionObjects =s ;
	}

	public void store(String idObject, ISessionObject obj) {
		if (getSessionObjects().get(idObject)==null) {
			getSessionObjects().remove(idObject);
		}
		getSessionObjects().put(idObject, obj);
	}
	
	public ISessionObject retrieve(String idObject) {
		return getSessionObjects().get(idObject);
	}
	
}
