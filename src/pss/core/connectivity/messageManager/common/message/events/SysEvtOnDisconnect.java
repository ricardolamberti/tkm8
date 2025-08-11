package pss.core.connectivity.messageManager.common.message.events;

import pss.core.connectivity.messageManager.common.message.IMessageContent;
import pss.core.connectivity.messageManager.common.message.MessageContent;
import pss.core.connectivity.messageManager.common.message.MessageEnvelope;

public class SysEvtOnDisconnect extends MessageContent {
	/**
	 * 
	 */
	private static final long serialVersionUID = -464944676213588467L;
	String pModuleName = null;

	@Override
	public char getMessageType() {
		return MessageEnvelope.MSG_TYPE_SYSTEM_EVENT;
	}

	@Override
	public boolean isEmpty() {
		if (this.pModuleName == null)
			return true;
		if (this.pModuleName.isEmpty())
			return true;
		return false;
	}

	/**
	 * @return the pModuleName
	 */
	public String getModuleName() {
		return pModuleName;
	}

	/**
	 * @param moduleName the pModuleName to set
	 */
	public SysEvtOnDisconnect setModuleName(String moduleName) {
		pModuleName = moduleName;
		return this;
	}

	@Override
	public boolean isEqual(IMessageContent zMsg) {
	  if (!super.isEqual(zMsg)) {
	  	return false;
	  }
	  if (this.pModuleName.equalsIgnoreCase(((SysEvtOnDisconnect)zMsg).pModuleName) == false)
	  	return false;
	  return true;
	}
}
