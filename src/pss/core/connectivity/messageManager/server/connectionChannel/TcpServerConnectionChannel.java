package pss.core.connectivity.messageManager.server.connectionChannel;

import java.net.Socket;

import pss.core.connectivity.messageManager.common.connectionChannel.BaseTcpConnectionChannel;
import pss.core.connectivity.tcp.JTcpStream;

public class TcpServerConnectionChannel extends BaseTcpConnectionChannel {
	
	public void connect(Socket zSocket) throws Exception {
		this.tcpStream = new JTcpStream(zSocket, 1000);
	}

}
