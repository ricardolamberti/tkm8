package pss.core.connectivity.messageManager.server.connection;

import java.net.Socket;

import pss.core.connectivity.messageManager.server.connectionChannel.TcpServerConnectionChannel;

public class TcpMessageServer extends BaseTcpMessageServer{

	
	protected void doConnectionDetect(Socket oSocket) throws Exception {
		TcpServerConnectionChannel oServerConnChnl = new TcpServerConnectionChannel();
		oServerConnChnl.connect(oSocket);

		ServerConnectionInstance oConnection = new ServerConnectionInstance();
		oConnection.setConnectionChannel(oServerConnChnl);
		oConnection.setConnectionsMngr(this.pConnectionsMngr);
		oConnection.launchConnectionInstanceShadow();

	}

	
}
