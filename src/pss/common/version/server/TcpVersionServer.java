package pss.common.version.server;

import java.net.Socket;

import pss.core.connectivity.messageManager.server.connection.BaseTcpMessageServer;

public class TcpVersionServer extends BaseTcpMessageServer {

	IVersionServerProcess process;
	
	TcpVersionServer(IVersionServerProcess p) {
		process = p;
	} 
	
	@Override
	protected void doConnectionDetect(Socket oSocket) throws Exception {
		VersionConnectionInstance connection = new VersionConnectionInstance(process);
		connection.launchConnectionInstance(oSocket);

	}

}
