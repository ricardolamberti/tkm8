package pss.core.connectivity.messageManager.common.message.messageProcessor;

import pss.core.connectivity.messageManager.common.message.IMessageContent;

public interface ISubscriber {
	public char getMsgType();
	public String getMsgId();
	public IMessageContent getMessageToSubscribe();
	public void setMessageToSubscribe(IMessageContent zMsg);
}
