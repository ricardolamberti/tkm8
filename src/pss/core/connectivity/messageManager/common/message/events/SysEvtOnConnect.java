package pss.core.connectivity.messageManager.common.message.events;

import pss.core.connectivity.messageManager.common.message.IMessageContent;
import pss.core.connectivity.messageManager.common.message.MessageContent;
import pss.core.connectivity.messageManager.common.message.MessageEnvelope;

public class SysEvtOnConnect extends MessageContent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3491845061047123865L;
	String pModuleName = null;
	
	/**
	 * @return the pModuleName
	 */
	public String getModuleName() {
		return pModuleName;
	}

	/**
	 * @param moduleName the pModuleName to set
	 */
	public SysEvtOnConnect setModuleName(String moduleName) {
		pModuleName = moduleName;
		return this;
	}
	
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

	@Override
	public boolean isEqual(IMessageContent zMsg) {
	  if (super.isEqual(zMsg) == false)
	  	return false;
	  
	  if (this.pModuleName.equals(((SysEvtOnConnect)zMsg).pModuleName) == false)
	  	return false;
	  
	  return true;
	}
}
