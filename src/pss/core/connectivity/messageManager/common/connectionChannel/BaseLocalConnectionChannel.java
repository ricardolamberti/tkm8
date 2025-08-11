package pss.core.connectivity.messageManager.common.connectionChannel;

import java.util.LinkedList;
import java.util.Queue;

import pss.core.connectivity.messageManager.client.connectionChannel.IClientConnectionChannel;
import pss.core.connectivity.messageManager.common.message.MessageEnvelope;

public abstract class BaseLocalConnectionChannel implements IClientConnectionChannel {

	private Queue<MessageEnvelope> pIncommingMsgQueue = new LinkedList<MessageEnvelope>();
	private BaseLocalConnectionChannel pRemoteConnection = null;
	
	public synchronized void setRemoteConnection(BaseLocalConnectionChannel zRemoteConnection) {
		pRemoteConnection = zRemoteConnection;
	}
	
	public synchronized void addNewMsg(MessageEnvelope zMsg) throws Exception {
		if (this.isConnected() == false)
			return;
		
		pIncommingMsgQueue.add(zMsg);
	}
	
	public synchronized void closeRemoteConnectionChannel() {
		if (isConnected()) {
			pRemoteConnection = null;
			this.pIncommingMsgQueue.clear();
		}
	}
	
	@Override
	public synchronized void closeConnectionChannel() throws Exception {
		if (isConnected()) {
			pRemoteConnection.closeRemoteConnectionChannel();
			this.closeRemoteConnectionChannel();
		}
	}

	@Override
	public synchronized String getConnectionId() {
		return "LOCAL";
	}

	@Override
	public synchronized boolean isConnected() {
		return (pRemoteConnection != null);
	}

	@Override
	public synchronized boolean isIncommingMsgWaiting() throws Exception {
		return this.pIncommingMsgQueue.isEmpty() == false;
	}

	@Override
	public synchronized MessageEnvelope readMsg() throws Exception {
		MessageEnvelope oMsg;
		oMsg = this.pIncommingMsgQueue.poll();
		return oMsg;
	}

	@Override
	public synchronized void sendMsg(MessageEnvelope msg) throws Exception {
		if (this.isConnected()) {
			this.pRemoteConnection.addNewMsg(msg);
		}
	}

}
