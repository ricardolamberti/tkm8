package pss.core.connectivity.messageManager.common.message;

import pss.core.tools.PssLogger;

public class MsgResponse extends MessageContent {
	public enum TypeResponseCode {
		OK, ERROR, ERROR_RETRY
	};

	/**
   * 
   */
	private static final long serialVersionUID = -6270939567322051104L;

	protected TypeResponseCode pResponseCode;
	protected String pResponseDescription = null;
	// protected String pResponseMsgId;
	protected IMessageContent msgResponse = null;

	@Override
	public String getMessageId() {
		if (msgResponse == null) {
			PssLogger.logError("Error: No hay respuesta configurada");
			return "*";
		}
		return msgResponse.getMessageId();
	}

	// public void setMsgId(String pResponseMsgId) {
	// this.pResponseMsgId = pResponseMsgId;
	// }

	// public void setMsgResponse(IMessageContent msgResponse) {
	// this.msgResponse = msgResponse;
	// }

	public MsgResponse(IMessageContent msgResponse) {
		this.msgResponse = msgResponse;
	}

	public TypeResponseCode getResponseCode() {
		return pResponseCode;
	}

	public void setResponseCode(TypeResponseCode responseCode) {
		pResponseCode = responseCode;
	}

	public String getResponseDescription() {
		return pResponseDescription;
	}

	public void setResponseDescription(String responseDescription) {
		pResponseDescription = responseDescription;
	}

	public String getResponseCodeDescr() {
		switch (this.pResponseCode) {
		case OK:
			return "OK";
		case ERROR:
			return "ERROR";
		case ERROR_RETRY:
			return "ERROR RECUPERABLE";
		}
		return "";
	}

	public char getMessageType() {
		return MessageEnvelope.MSG_TYPE_RESPONSE;
	}

	@Override
	public boolean isEqual(IMessageContent zMsg) {
		if (!(zMsg instanceof MsgResponse)) {
			return false;
//				return this.msgResponse.isEqual(zMsg);
		} else {
			if (zMsg.getClass() != this.getClass())
				return false;
			MsgResponse oMR = (MsgResponse) zMsg;
			if (this.msgResponse != null) {
				return this.msgResponse.isEqual(oMR.msgResponse);
			}	
		}
		return true;
	}
}
