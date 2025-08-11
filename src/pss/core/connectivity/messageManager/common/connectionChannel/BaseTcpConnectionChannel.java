package pss.core.connectivity.messageManager.common.connectionChannel;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import pss.core.connectivity.messageManager.common.message.MessageEnvelope;
import pss.core.connectivity.tcp.JTcpStream;
import pss.core.tools.PssLogger;

public class BaseTcpConnectionChannel implements IConnectionChannel {
	// private TcpClient pSocketClient = new TcpClient();
	protected String hostId = null;
	protected int port = 0;
	protected JTcpStream tcpStream = null;

	public void connect(Socket zSocket) throws Exception {
		this.tcpStream = new JTcpStream(zSocket, 1000);
	}
	
	@Override
	public void closeConnectionChannel() throws Exception {
		if (this.isConnected() == false)
			return;
		try {
		this.tcpStream.finish();
		} catch (Exception e) {
			PssLogger.logError(e);
		}
	}

	@Override
	public String getConnectionId() {
		String strConnId;
		
		strConnId = this.tcpStream.getLocalAddress();
		
		if (strConnId != null && strConnId.isEmpty() == false)
			return strConnId;
	
		if (hostId != null) {
			strConnId = new String(this.hostId + ":" + this.port);
		} else {
			strConnId = "unnamed";
		}
	
		return strConnId;
	}

	@Override
	public boolean isConnected() {
		if (this.tcpStream == null)
			return false;

		return this.tcpStream.isConnected();
	}

	@Override
	public boolean isIncommingMsgWaiting() throws Exception {
		if (this.isConnected() == false)
			return false;

		return this.tcpStream.isDataAvailable();
	}

	@Override
	public MessageEnvelope readMsg() throws Exception { 
		byte MsgReceived[] = this.tcpStream.receiveByteArray();
		ByteArrayInputStream bs= new ByteArrayInputStream(MsgReceived); 
		ObjectInputStream is = new ObjectInputStream(bs);
		MessageEnvelope oMsg = (MessageEnvelope)is.readObject();
		is.close();		
		return oMsg;
	}

	@Override
	public void sendMsg(MessageEnvelope msg) throws Exception {
		ByteArrayOutputStream bs= new ByteArrayOutputStream();
		ObjectOutputStream os = new ObjectOutputStream (bs);
		os.writeObject(msg); 
		os.close();
		byte[] bytes =  bs.toByteArray(); // devuelve byte[]
		this.tcpStream.sendBytes(bytes);
		// this.pTcpStream.sendBytes(bs.toString());
	}
}
