package pss.common.version.protocol;

import pss.common.version.JGenerateVersion.VersionStatus;
import pss.core.connectivity.messageManager.common.message.MessageContent;
import pss.core.connectivity.messageManager.common.message.MessageEnvelope;

public class MsgCurrentStatusPackage  extends MessageContent {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4966891975625968951L;
	public String licence;
	public long version;
	public VersionStatus status;
	
	public char getMessageType() {
		return MessageEnvelope.MSG_TYPE_RESPONSE;
	}

}
 