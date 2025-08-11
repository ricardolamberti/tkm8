package pss.common.version.protocol;

import pss.common.version.JGenerateVersion.VersionStatus;
import pss.core.connectivity.messageManager.common.message.MessageContent;
import pss.core.connectivity.messageManager.common.message.MessageEnvelope;

public class ReqChangeStatusPackage  extends MessageContent {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1584199782437499625L;
	
	public String licence;
	public long version;
	public VersionStatus status;
	public Exception error;
	
	public char getMessageType() {
		return MessageEnvelope.MSG_TYPE_REQUEST;
	}
 
}