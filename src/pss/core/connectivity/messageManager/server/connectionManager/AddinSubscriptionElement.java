package pss.core.connectivity.messageManager.server.connectionManager;

import java.lang.ref.WeakReference;

import pss.core.connectivity.messageManager.common.message.IMessageContent;
import pss.core.connectivity.messageManager.modules.addIns.IBaseServerAddInModule;

public class AddinSubscriptionElement {
	String addinName;
	WeakReference<IBaseServerAddInModule> addinInstance = null;
	IMessageContent filterMsg = null;

	public AddinSubscriptionElement(IBaseServerAddInModule zAddin, IMessageContent zFilterMsg) {
	  this.addinInstance = new WeakReference<IBaseServerAddInModule>(zAddin);
	  this.filterMsg = zFilterMsg;
	  addinName = zAddin.getAddinName();
  }

	public IBaseServerAddInModule getAddinInstance() {
		return this.addinInstance.get();
  }

	public IMessageContent getFilterMsg() {
  	return filterMsg;
  }
	
	public String getAddinName() {
  	return addinName;
  }
}
