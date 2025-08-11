package pss.common.version.protocol;

import pss.core.connectivity.messageManager.common.message.MessageContent;
import pss.core.connectivity.messageManager.common.message.MessageEnvelope;

public class ReqCurrentVersion extends MessageContent {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4509207903236059873L;

	public String licence;
	
	public char getMessageType() {
		return MessageEnvelope.MSG_TYPE_REQUEST;
	}

}
 