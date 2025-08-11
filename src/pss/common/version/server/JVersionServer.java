package pss.common.version.server;

import pss.core.connectivity.messageManager.client.connection.MessageClient;
import pss.core.connectivity.messageManager.client.connection.MsgConnectionInterface;
import pss.core.connectivity.messageManager.common.message.IMessageContent;
import pss.core.connectivity.messageManager.common.message.messageProcessor.IProcessMsg;

public class JVersionServer extends MessageClient implements IProcessMsg {

	private TcpVersionServer tcpMsgSrv = null;
	private IVersionServerProcess process = null;
	private int port;
 
	public int getPort() {
		return port;
	} 

	public void setPort(int port) {
		this.port = port;
	}

	public JVersionServer(IVersionServerProcess p) throws Exception {
		process=p;
	}

	private TcpVersionServer getTcpVersionServer() throws Exception {
		if (tcpMsgSrv!=null) return tcpMsgSrv;
		TcpVersionServer t = new TcpVersionServer(process);
		t.setConnectionsMngr(null);
	  t.setTcpPort(getPort() );
	  return tcpMsgSrv=t;
	}
	

	// @Override
	public IMessageContent onConnect(IMessageContent zMsg) throws Exception {
		getTcpVersionServer().launchTcpMessageServer();
		return null;
	}

	// @Override
	public IMessageContent onDisconnect(IMessageContent zMsg) throws Exception {
		getTcpVersionServer().stopTcpMessageServer();
		return null;
	}

	@Override
	public String getConnectionString() {
		return "local";
	}


	@Override
	public String getModuleName() throws Exception {
		return this.getClass().getSimpleName();
	}

	

	@Override
	public boolean initializeModuleClient(MsgConnectionInterface zClient) throws Exception {
		super.initializeModuleClient(zClient);
	//	subscribeToRequestMsg(new ReqPosConfig(), this);
		return true;
	}

	
	@Override
	public IMessageContent processMessageContent(IMessageContent zMessage) throws Exception {
	//	if (zMessage instanceof ReqPosConfig) return process((ReqPosConfig) zMessage);
		
		return null;
	}

}
