package pss.core.connectivity.messageManager.client.connection;

import pss.core.connectivity.messageManager.common.message.IMessageContent;
import pss.core.connectivity.messageManager.common.message.messageProcessor.IProcessMsg;

public class MessageProcessElement {
	IProcessMsg procMsg = null;
	IMessageContent filterMsg = null;
	
	public MessageProcessElement() {
	}

	public MessageProcessElement(IMessageContent zFilterMsg, IProcessMsg zProcMsg) {
	  this.procMsg = zProcMsg;
	  this.filterMsg = zFilterMsg;
  }

	public IProcessMsg getProcMsg() {
  	return procMsg;
  }

	public void setProcMsg(IProcessMsg procMsg) {
  	this.procMsg = procMsg;
  }

	public IMessageContent getFilterMsg() {
  	return filterMsg;
  }

	public void setFilterMsg(IMessageContent filterMsg) {
  	this.filterMsg = filterMsg;
  }
	
}
