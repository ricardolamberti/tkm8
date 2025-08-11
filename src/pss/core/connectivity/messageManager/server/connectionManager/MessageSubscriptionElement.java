package pss.core.connectivity.messageManager.server.connectionManager;

import java.lang.ref.WeakReference;

import pss.core.connectivity.messageManager.common.connection.BaseConnectionInstance;
import pss.core.connectivity.messageManager.common.message.IMessageContent;

public class MessageSubscriptionElement {
	String connectionName = null;
	WeakReference<SessionInstance> sessionInstance = null;
	IMessageContent filterMsg = null;

	public MessageSubscriptionElement(SessionInstance zSessionInstance, IMessageContent zFilterMsg) {
	  super();
	  this.sessionInstance = new WeakReference<SessionInstance>(zSessionInstance);
	  this.filterMsg = zFilterMsg;
	  this.connectionName = zSessionInstance.getConnectionInstance().getConnectionInstanceId(); 
  }

	public BaseConnectionInstance getConnectionInstance() {
		if (this.sessionInstance.get() == null) {
			return null;
		}
		return this.sessionInstance.get().getConnectionInstance();
  }

	public IMessageContent getFilterMsg() {
  	return filterMsg;
  }

	public String getConnectionName() {
  	return connectionName;
  }
	
}
