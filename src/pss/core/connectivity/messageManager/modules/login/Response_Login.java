package pss.core.connectivity.messageManager.modules.login;

import pss.core.connectivity.messageManager.common.message.MessageEnvelope;
import pss.core.connectivity.messageManager.common.message.RespGenericResponse;

public class Response_Login extends RespGenericResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6576444802027190273L;
	private String sessionId = null;
	
	@Override
	public char getMessageType() {
		return MessageEnvelope.MSG_TYPE_LOGIN;
	}

	public String getSessionId() {
  	return sessionId;
  }

	public void setSessionId(String sessionId) {
  	this.sessionId = sessionId;
  }
	
}
