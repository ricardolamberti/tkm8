package pss.core.connectivity.messageManager.server.connectionManager;

import java.lang.ref.WeakReference;
import java.util.Calendar;
import java.util.HashMap;

import pss.core.connectivity.messageManager.common.connection.BaseConnectionInstance;
import pss.core.tools.PssLogger;

public class SessionInstance {
	private String sessionId = null;
	private BaseConnectionInstance connectionInstance = null;
	private HashMap<String, WeakReference<Object>> objectSessionMap = null;
	private long lastAliveCheckTime = 0;
	private static final long ECHO_MAX_DEAD_WAITING_TIME = 3600000; // 1 hour
	private long maxDeadTime = ECHO_MAX_DEAD_WAITING_TIME;

	public void setMaxDeadTime(long maxDeadTime) {
		this.maxDeadTime = maxDeadTime;
	}

	public boolean isAlive() throws Exception {
		if (this.connectionInstance == null) {
			if (this.lastAliveCheckTime != 0) {
				if (Calendar.getInstance().getTimeInMillis() - lastAliveCheckTime > 10000) {
					PssLogger.logDebug("Deleting Session [" + this.sessionId + "] due has not a connection Instance during 10 sec.");
					return false;
				}
			}
			return true;
		}

		if (this.connectionInstance.ifConnectionThreadIsRunning()) {
			this.lastAliveCheckTime = Calendar.getInstance().getTimeInMillis();
			return true;
		}

		if (Calendar.getInstance().getTimeInMillis() - lastAliveCheckTime > this.maxDeadTime) {
			PssLogger.logDebug("Deleting Session [" + this.sessionId + "] due has activity by [" + (this.maxDeadTime / 60000) + "] min.");
			return false;
		}

		return true;
	}

	public String getSessionId() {
		return sessionId;
	}

	public SessionInstance(String sessionId) {
		this.sessionId = sessionId;
	}

	public synchronized BaseConnectionInstance getConnectionInstance() {
		return connectionInstance;
	}

	public synchronized void setConnectionInstance(BaseConnectionInstance connectionInstance) {
		this.connectionInstance = connectionInstance;
	}

	public synchronized HashMap<String, WeakReference<Object>> getObjectSessionMap() {
		if (this.objectSessionMap == null) {
			this.objectSessionMap = new HashMap<String, WeakReference<Object>>();
		}
		return objectSessionMap;
	}

	public synchronized void addObjectSessionMap(String zObjectId, Object zObject) {
		this.getObjectSessionMap().put(zObjectId, new WeakReference<Object>(zObject));
	}

	public synchronized Object getObjectFromSessionMap(String zObjectId) {
		WeakReference<Object> oWR = this.getObjectSessionMap().get(zObjectId);
		return oWR.get();
	}

	public synchronized void removeObjectFromSessionMap(String zObjectId) {
		this.getObjectSessionMap().remove(zObjectId);
	}

}
