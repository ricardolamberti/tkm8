package pss.core.connectivity.messageManager.common.message.messageProcessor;

import pss.core.connectivity.messageManager.common.message.IMessageContent;

public interface IProcessMsg {
	public IMessageContent processMessageContent(IMessageContent zMessage) throws Exception;
}
