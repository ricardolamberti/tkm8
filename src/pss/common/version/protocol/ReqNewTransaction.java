package pss.common.version.protocol;

import pss.core.connectivity.messageManager.common.message.MessageContent;
import pss.core.connectivity.messageManager.common.message.MessageEnvelope;

public class ReqNewTransaction extends MessageContent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3397990369267955090L;

	@Override
	public char getMessageType() {
		return MessageEnvelope.MSG_TYPE_REQUEST;
	}

	public long id;
	public String keyMessage;
	public String message;
	
} 
