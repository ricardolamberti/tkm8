package pss.common.version.protocol;

import java.util.List;

import pss.core.connectivity.messageManager.common.message.MessageContent;
import pss.core.connectivity.messageManager.common.message.MessageEnvelope;

public class MsgVersion extends MessageContent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3471806192775773832L;
	public String licence;
	public long idVersion;
	public boolean apply;
	public boolean obsolete;
	public boolean fullVersion;
	public List<MsgSubPack> packages;
	
	public char getMessageType() {
		return MessageEnvelope.MSG_TYPE_RESPONSE;
	}
} 
