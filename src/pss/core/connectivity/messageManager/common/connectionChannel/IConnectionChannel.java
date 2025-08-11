package pss.core.connectivity.messageManager.common.connectionChannel;

import pss.core.connectivity.messageManager.common.message.MessageEnvelope;

public interface IConnectionChannel {
	public boolean isIncommingMsgWaiting() throws Exception;
	public MessageEnvelope readMsg() throws Exception;
	public void sendMsg(MessageEnvelope zMsg) throws Exception;
	public boolean isConnected();
	public void closeConnectionChannel() throws Exception;
	public String getConnectionId();
}
