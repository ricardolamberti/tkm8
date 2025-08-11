package pss.core.connectivity.messageManager.common.message;


public class RespGenericResponse extends MessageContent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7721870765269431227L;
	private String pMsgId = null;
	private String pResult = new String ("OK");
	private String pResponseMsg = null;
	
//	@Override
//	public String dumpMessage() {
//		String strDump;
//		strDump = "MSG_TYPE:" + MessageEnvelope.getMessageTypeDescription(this.getMessageType());
//		strDump += "[" + this.getMessageType() +"]";
//		strDump += String.valueOf('\n');
//		
//		strDump += "MSG_ID:" + this.getMessageId();
//		strDump += String.valueOf('\n');
//		
//		strDump = "Message Id Response: ";
//		if (this.pMsgId == null || this.pMsgId.isEmpty()) {
//			strDump += "<empty>";
//		} else {
//			strDump += pMsgId;
//		}
//		strDump += String.valueOf('\n');
//
//		strDump = "Result: ";
//		if (this.pResult == null || this.pResult.isEmpty()) {
//			strDump += "<empty>";
//		} else {
//			strDump += pResult;
//		}
//		strDump += String.valueOf('\n');
//		
//		strDump = "Message Response: ";
//		if (this.pResponseMsg == null || this.pResponseMsg.isEmpty()) {
//			strDump += "<empty>";
//		} else {
//			strDump += pResponseMsg;
//		}
//		strDump += String.valueOf('\n');
//	
//		return strDump;
//	}

	@Override
	public String getMessageId() {
		return this.pMsgId;
	}

	@Override
	public char getMessageType() {
		return MessageEnvelope.MSG_TYPE_RESPONSE;
	}

	@Override
	public boolean isEmpty() {
		if (pMsgId == null)
			return true;
		return pMsgId.isEmpty();
	}

	/**
	 * @return the pMsgId
	 */
	public String getMsgId() {
		return pMsgId;
	}

	/**
	 * @param msgId the pMsgId to set
	 */
	public void setMsgId(String msgId) {
		pMsgId = msgId;
	}

	/**
	 * @return the pResult
	 */
	public String getResult() {
		return pResult;
	}

	/**
	 * @param result the pResult to set
	 */
	public void setResult(String result) {
		pResult = result;
	}

	/**
	 * @return the pResponseMsg
	 */
	public String getResponseMsg() {
		return pResponseMsg;
	}

	/**
	 * @param responseMsg the pResponseMsg to set
	 */
	public void setResponseMsg(String responseMsg) {
		pResponseMsg = responseMsg;
	}

}
