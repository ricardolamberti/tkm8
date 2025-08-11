package pss.core.connectivity.messageManager.common.message.messageProcessor;

import pss.core.connectivity.messageManager.common.message.MessageEnvelope;

public class SystemEventListener extends EventListener {

	public SystemEventListener(Object zListenerObj, String zListenerMethod) throws Exception {
		super(zListenerObj, zListenerMethod);
	}

	@Override
	public String getMsgId() {
		return this.pEventMsg.getMessageId();
	}

	@Override
	public char getMsgType() {
		return MessageEnvelope.MSG_TYPE_SYSTEM_EVENT;
	}

}
