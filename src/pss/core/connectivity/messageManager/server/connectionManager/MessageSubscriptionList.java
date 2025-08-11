package pss.core.connectivity.messageManager.server.connectionManager;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import pss.core.connectivity.messageManager.common.connection.BaseConnectionInstance;
import pss.core.connectivity.messageManager.common.message.IMessageContent;
import pss.core.tools.PssLogger;

public class MessageSubscriptionList {
	private String listName = "unammed";
	private Queue<MessageSubscriptionElement> pConnectionsSubscripted = new LinkedList<MessageSubscriptionElement>();

	public synchronized void addToSubscriptionList(SessionInstance zSessionInstance, IMessageContent zFilterMsg) throws Exception {
		MessageSubscriptionElement oMSE = new MessageSubscriptionElement(zSessionInstance, zFilterMsg);
		this.pConnectionsSubscripted.add(oMSE);
	}

	public synchronized void removeFromSubscriptionList(BaseConnectionInstance zConnInst) throws Exception {
		Iterator<MessageSubscriptionElement> oIT;
		MessageSubscriptionElement oMSE;
		BaseConnectionInstance oBCI;
		boolean bObjectDeleted;

		while (true) {
			oIT = this.pConnectionsSubscripted.iterator();
			bObjectDeleted = false;
			while (oIT.hasNext()) {
				oMSE = oIT.next();
				oBCI = oMSE.getConnectionInstance();
				if (oBCI == null) {
					PssLogger.logDebug("Deleting [" + oMSE.getConnectionName() + "] from [" + this.listName + "] due the communication channel is null");
					oIT.remove();
					bObjectDeleted = true;
					break;
				}
				if (oBCI.isEqual(zConnInst)) {
					PssLogger.logDebug("Deleting [" + oMSE.getConnectionName() + "] from [" + this.listName + "]");
					oIT.remove();
					bObjectDeleted = true;
					break;
				}
			} // end while (oIT.hasNext())
			if (bObjectDeleted == false) {
				break;
			}
		} // end while
	}

	public Queue<BaseConnectionInstance> getConnectionsByFilter(IMessageContent zFilter) {
		Queue<BaseConnectionInstance> qBCI = new LinkedList<BaseConnectionInstance>();
		Iterator<MessageSubscriptionElement> oIT;
		MessageSubscriptionElement oMSE;
		BaseConnectionInstance oBCI;
		IMessageContent oFilter;
		boolean bObjectDeleted;

		while (true) {
			oIT = this.pConnectionsSubscripted.iterator();
			bObjectDeleted = false;
			while (oIT.hasNext()) {
				oMSE = oIT.next();
				oBCI = oMSE.getConnectionInstance();
				if (oBCI == null) {
					PssLogger.logDebug("Deleting [" + oMSE.getConnectionName() + "] from [" + this.listName + "] due the communication channel is null");
					oIT.remove();
					bObjectDeleted = true;
					break;
				}
				oFilter = oMSE.getFilterMsg();
				if (oFilter.isEqual(zFilter)) {
					PssLogger.logDebug("Adding [" + oMSE.getConnectionName() + "] to queue list");
					qBCI.add(oBCI);
				}
			} // end while (oIT.hasNext())
			if (bObjectDeleted == false) {
				break;
			}
		} // end while
		
		return qBCI;
	}

	public Queue<MessageSubscriptionElement> getSubscriptionList() throws Exception {
		Queue<MessageSubscriptionElement> subscriptionList = new LinkedList<MessageSubscriptionElement>();
		subscriptionList.addAll(this.pConnectionsSubscripted);
		return subscriptionList;
	}

	public String getListName() {
		return listName;
	}

	public void setListName(String listName) {
		this.listName = listName;
	}
}
