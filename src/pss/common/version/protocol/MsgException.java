package pss.common.version.protocol;

import pss.core.connectivity.messageManager.common.message.MessageContent;
import pss.core.connectivity.messageManager.common.message.MessageEnvelope;

public class MsgException extends MessageContent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8393844019842908082L;

	public String errorId;
	public String error;
	
	public char getMessageType() {
		return MessageEnvelope.MSG_TYPE_ERROR;
	}
	
	public MsgException() {
	}
	
	public MsgException(Exception e) {
		error   = e.getMessage();
		errorId = e.getClass().getName();
	}

} 
