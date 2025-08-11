package pss.core.connectivity.messageManager.common.message;

public class EvtMessage extends MessageContent implements ISubscribible {
	public static final int ALL = -1;
	/**
   * 
   */
	private static final long serialVersionUID = -3406269140110899273L;
	char pMessageType = MessageEnvelope.MSG_TYPE_EVENT;

	@Override
	public MessageContent asSubscribe() {
		pMessageType = MessageEnvelope.MSG_TYPE_EVENT_SUBSCRIBE;
		return this;
	}

	public char getMessageType() {
		// return MessageEnvelope.MSG_TYPE_EVENT;
		return this.pMessageType;
	}
	


}
