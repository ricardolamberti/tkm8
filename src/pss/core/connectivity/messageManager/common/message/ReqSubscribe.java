package pss.core.connectivity.messageManager.common.message;


public class ReqSubscribe extends MessageContent {
	private static final long serialVersionUID = 1224249762719934616L;
//	 private String subscribeMsgId = null;
//	 private char subscribeMsgType = 0;
	private IMessageContent msgContent = null;

	@Override
	public String getMessageId() {
		// if (this.isEmpty())
		// return "*";f
		// return subscribeMsgId;
		if (this.msgContent == null) {
			return "*";
		}
		return msgContent.getMessageId();
	}

	@Override
	public char getMessageType() {
//		return this.subscribeMsgType;

		if (this.msgContent == null) {
			return ' ';
		}

		char cMsgType;

		switch (msgContent.getMessageType()) {
		case MessageEnvelope.MSG_TYPE_EVENT:
			cMsgType = MessageEnvelope.MSG_TYPE_EVENT_SUBSCRIBE;
			break;
		case MessageEnvelope.MSG_TYPE_REQUEST:
			cMsgType = MessageEnvelope.MSG_TYPE_REQUEST_SUBSCRIBE;
			break;
		case MessageEnvelope.MSG_TYPE_SYSTEM_EVENT:
			cMsgType = MessageEnvelope.MSG_TYPE_SYSTEM_EVENT_SUBSCRIBE;
			break;
		default:
			cMsgType = msgContent.getMessageType();
		}

		return cMsgType;
	}

	@Override
	public boolean isEmpty() {
		return (msgContent == null);
	}

	// /**
	// * @return the pSubscribeMsgId
	// */
	// public String getSubscribeMsgId() {
	// return subscribeMsgId;
	// }

	// /**
	// * @param zSubscribeMsgId
	// * the pSubscribeMsgId to set
	// */
	// public void setSubscribeMsgId(String zSubscribeMsgId) {
	// subscribeMsgId = zSubscribeMsgId;
	// }
	
//	 /**
//	 * @return the subscribeMsgType
//	 */
//	 public char getSubscribeMsgType() {
//	 return subscribeMsgType;
//	 }
//	
//	 /**
//	 * @param subscribeMsgType
//	 * the pSubscribeMsgType to set
//	 */
//	 public void setSubscribeMsgType(char zSubscribeMsgType) {
//	 subscribeMsgType = zSubscribeMsgType;
//	 }

	public IMessageContent getMsgContent() {
		return msgContent;
	}

	public void setMsgContent(IMessageContent msgContent) {
		this.msgContent = msgContent;
	}
}
