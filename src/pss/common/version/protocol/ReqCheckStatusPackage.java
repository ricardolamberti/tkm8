package pss.common.version.protocol;

import pss.common.version.JGenerateVersion.VersionStatus;
import pss.core.connectivity.messageManager.common.message.MessageContent;
import pss.core.connectivity.messageManager.common.message.MessageEnvelope;

public class ReqCheckStatusPackage  extends MessageContent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2547185426696413956L;
	
	public String licence;
	public long version;
	public VersionStatus status;
	
	public char getMessageType() {
		return MessageEnvelope.MSG_TYPE_REQUEST;
	}

} 