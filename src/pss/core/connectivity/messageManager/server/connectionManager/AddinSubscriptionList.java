package pss.core.connectivity.messageManager.server.connectionManager;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import pss.core.connectivity.messageManager.common.message.IMessageContent;
import pss.core.connectivity.messageManager.modules.addIns.IBaseServerAddInModule;
import pss.core.tools.PssLogger;

public class AddinSubscriptionList {
	private Queue<AddinSubscriptionElement> addinSubscriptionList = new LinkedList<AddinSubscriptionElement>();

	public synchronized void addToSubscriptionList(IBaseServerAddInModule zAddin, IMessageContent zFilterMsg) throws Exception {
		AddinSubscriptionElement oASE = new AddinSubscriptionElement(zAddin, zFilterMsg);
		this.addinSubscriptionList.add(oASE);
	}

	public synchronized void removeFromSubscriptionList(IBaseServerAddInModule zAddin) throws Exception {
		Iterator<AddinSubscriptionElement> oIT;
		AddinSubscriptionElement oASE;
		IBaseServerAddInModule oBSAIM;
		boolean bObjectDeleted;

		while (true) {
			oIT = this.addinSubscriptionList.iterator();
			bObjectDeleted = false;
			while (oIT.hasNext()) {
				oASE = oIT.next();
				oBSAIM = oASE.getAddinInstance();
				if (oBSAIM == null) {
					PssLogger.logDebug("Deleting [" + oASE.getAddinName() + "] due the communication channel is null");
					oIT.remove();
					bObjectDeleted = true;
					break;
				}
				if (oBSAIM.isEqual(zAddin)) {
					PssLogger.logDebug("Deleting [" + oBSAIM.getAddinName() + "] by request");
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

	public Queue<IBaseServerAddInModule> getAddinsByFilter(IMessageContent zFilter) {
		Queue<IBaseServerAddInModule> qBSAIM = new LinkedList<IBaseServerAddInModule>();
		Iterator<AddinSubscriptionElement> oIT;
		AddinSubscriptionElement oASE;
		IBaseServerAddInModule oBSAIM;
		IMessageContent oFilter;
		boolean bObjectDeleted;

		while (true) {
			oIT = this.addinSubscriptionList.iterator();
			bObjectDeleted = false;
			while (oIT.hasNext()) {
				oASE = oIT.next();
				oBSAIM = oASE.getAddinInstance();
				if (oBSAIM == null) {
					PssLogger.logDebug("Deleting [" + oASE.getAddinName() + "] due the communication channel is null");
					oIT.remove();
					bObjectDeleted = true;
					break;
				}
				oFilter = oASE.getFilterMsg();
				if (oFilter.isEqual(zFilter)) {
					PssLogger.logDebug("Adding [" + oBSAIM.getAddinName() + "] to queue list");
					qBSAIM.add(oBSAIM);
				}
			} // end while (oIT.hasNext())
			if (bObjectDeleted == false) {
				break;
			}
		} // end while
		
		return qBSAIM;
	}

	public Queue<AddinSubscriptionElement> getSubscriptionList() throws Exception {
		Queue<AddinSubscriptionElement> subscriptionList = new LinkedList<AddinSubscriptionElement>();
		subscriptionList.addAll(this.addinSubscriptionList);
		return subscriptionList;
	}
}
