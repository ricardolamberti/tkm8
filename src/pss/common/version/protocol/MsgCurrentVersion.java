package pss.common.version.protocol;

import pss.core.connectivity.messageManager.common.message.MessageContent;
import pss.core.connectivity.messageManager.common.message.MessageEnvelope;

public class MsgCurrentVersion extends MessageContent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 563156281332417675L;

	public String licence;
	public long version;
	
	public char getMessageType() {
		return MessageEnvelope.MSG_TYPE_RESPONSE;
	}

}
 