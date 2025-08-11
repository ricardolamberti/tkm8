package pss.core.connectivity.messageManager.modules.login;

import pss.core.connectivity.messageManager.common.message.MessageContent;
import pss.core.connectivity.messageManager.common.message.MessageEnvelope;

public class ReqLogin extends MessageContent {
	private static final long serialVersionUID = 3246742140506302974L;
	String clientName = null;
	String sessionId = null;
	
	public void setClientName(String zClientName) {
		clientName = zClientName;
	}
	
	public String getClientName() {
		return clientName;
	}
	
	public String getSessionId() {
  	return sessionId;
  }

	public void setSessionId(String sessionId) {
  	this.sessionId = sessionId;
  }

	public String getPassword() {
		return "GP151HGK218MR159RL158SG154";
	}
	
	@Override
	public char getMessageType() {
		return MessageEnvelope.MSG_TYPE_LOGIN;
	}

	public boolean ifHasClientName() {
		return (this.clientName != null && this.clientName.isEmpty()==false);
	}
	
	public boolean ifHasSessionId() {
		return (this.sessionId != null && this.sessionId.isEmpty()==false);
	}
	
}
