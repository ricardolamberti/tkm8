package pss.common.version.protocol;

import pss.core.connectivity.messageManager.common.message.MessageContent;
import pss.core.connectivity.messageManager.common.message.MessageEnvelope;

public class MsgOk extends MessageContent {



	/**
	 * 
	 */
	private static final long serialVersionUID = 1464540905815254361L;

	public char getMessageType() {
		return MessageEnvelope.MSG_TYPE_RESPONSE;
	}

} 