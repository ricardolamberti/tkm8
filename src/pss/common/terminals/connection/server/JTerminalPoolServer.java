package pss.common.terminals.connection.server;

import java.net.BindException;
import java.net.Socket;

import pss.core.connectivity.tcp.JTcpShadow;
import pss.core.connectivity.tcp.JTcpStream;
import pss.core.connectivity.tcp.TcpServer;
import pss.core.tools.JExcepcion;
import pss.core.tools.PssLogger;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;

public class JTerminalPoolServer {

	private static JMap<String, JTerminalPoolShadow> remoteTerminalPools;
	private TcpServer tcpServer;
	private static Thread serverThread;
	
	public JTerminalPoolServer() {
	}
	
	public static synchronized void addTerminalShadow(JTerminalPoolShadow shadow) throws Exception {
		if (remoteTerminalPools==null) remoteTerminalPools = JCollectionFactory.createMap();
		remoteTerminalPools.addElement(shadow.getTerminalPool().getMacAddress(), shadow);
	}
	
	public static synchronized void removeTerminalShadow(JTerminalPoolShadow shadow) throws Exception {
		if (remoteTerminalPools==null) return;
		remoteTerminalPools.removeElement(shadow.getTerminalPool().getMacAddress());
	}

	public static JMap<String, JTerminalPoolShadow> getRemoteTerminals() throws Exception {
		return remoteTerminalPools;
	}
//	private static String getUniqueKey(JTerminalPool pool) throws Exception {
//		return String.valueOf(pool.getCompany()+pool.getNodo()+String.valueOf(pool.getPoolId()));
//	}
//	
//	public static JTerminalPoolShadow getTerminalPoolShadow(String macAddress) throws Exception {
//		return JTerminalPoolServer.getTerminalPoolShadow(BizUsuario.getUsr().getCompany(), BizUsuario.getUsr().getNodo(), id);
//	}
	public static JTerminalPoolShadow getTerminalPoolShadow(String macAddress) throws Exception {
		if (remoteTerminalPools==null) {
			JTerminalPoolServer.listen();
			Thread.sleep(5000);
		}
		return remoteTerminalPools.getElement(macAddress);
	}
	
	public static Thread getServerThread() {
		return serverThread;
	}
	
	public static void main(String[] argvs) throws Exception {
		JTerminalPoolServer.listen();
	}

	public static void listen() throws Exception {
		JTerminalPoolServer server = new JTerminalPoolServer();
		server.listen(9100);
	}

	public void listen(int port) throws Exception {
		TcpServer server = new TcpServer();
//		server.setHost(JTools.GetHostName());
//		server.setPort(port);
//		server.setTimeoutEspera(0);
    try { 
    	server.start(port); 
    } catch ( BindException e) {
      JExcepcion.SendError("Port en uso: "+port);
    }
    this.tcpServer = server;
		remoteTerminalPools = JCollectionFactory.createMap();

    serverThread = new Thread(new Runnable () {public void run() { terminalPoolServer(); }});
    serverThread.setName("TerminalPool-Server-"+port);    
    serverThread.start();

    PssLogger.logDebug("Port open: "+port);
	}
	
  private void terminalPoolServer() {
    try {
      while (true) {
      	if (Thread.currentThread().isInterrupted()) break;
      	Socket s = tcpServer.listen(0);
        JTcpStream stream = new  JTcpStream(s,5000);
        JTcpShadow oShadow = new JTerminalPoolShadow(stream);
        oShadow.executeShadow();
      }
      remoteTerminalPools=null;
      PssLogger.logDebug("Terminal Pool Server Shutdown");
    } catch ( Exception e) {
    	PssLogger.logDebug("Terminal Pool Server ERROR");
    	PssLogger.logError(e);
    }}
	
  public static void stop() {
  	JTerminalPoolServer.getServerThread().interrupt();
  }

}
