package pss.common.version.protocol;

import pss.core.connectivity.messageManager.common.message.MessageContent;
import pss.core.connectivity.messageManager.common.message.MessageEnvelope;

public class ReqVersion extends MessageContent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6769016279118224470L;
	public String licence;
	public long idVersion;
	
	public char getMessageType() {
		return MessageEnvelope.MSG_TYPE_REQUEST;
	}

}
 