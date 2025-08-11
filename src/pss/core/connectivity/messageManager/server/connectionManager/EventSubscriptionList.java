package pss.core.connectivity.messageManager.server.connectionManager;

import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.Queue;

import pss.core.connectivity.messageManager.common.connection.BaseConnectionInstance;

public class EventSubscriptionList {
	private Queue<WeakReference<BaseConnectionInstance>> pConnectionsSubscripted = new LinkedList<WeakReference<BaseConnectionInstance>>();

	public void subscriptToEventList(BaseConnectionInstance zConnInst) throws Exception {
		this.pConnectionsSubscripted.add(new WeakReference<BaseConnectionInstance>(zConnInst));
	}
	
	public void removeFromEventList(BaseConnectionInstance zConnInst) throws Exception {
		this.pConnectionsSubscripted.remove( new WeakReference<BaseConnectionInstance>(zConnInst) );
	}
	
	public Queue<WeakReference<BaseConnectionInstance>> getSubscriptionList() throws Exception {
		Queue<WeakReference<BaseConnectionInstance>> subscriptionList = new LinkedList<WeakReference<BaseConnectionInstance>>();
		subscriptionList.addAll(this.pConnectionsSubscripted);
		return subscriptionList;
	}
}
