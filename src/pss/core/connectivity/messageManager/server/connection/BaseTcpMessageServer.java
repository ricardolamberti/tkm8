package pss.core.connectivity.messageManager.server.connection;

import java.net.Socket;

import pss.core.connectivity.messageManager.server.connectionManager.ConnectionsManager;
import pss.core.connectivity.tcp.TcpServer;
import pss.core.tools.PssLogger;

public abstract class BaseTcpMessageServer {
	public static int DEFAULT_PORT = 9876;
	public static int CHECK_THREAD_ALIVE_TIMEOUT = 1000; // 1 Seg.

	protected  TcpServer pSocketServer = null;
	protected Thread pSocketServerThread = null;

	protected ConnectionsManager pConnectionsMngr = null;
	protected int pTcpPort = 0;
	
	private void runMsgMngrSocketServer() {
		while (true) {
			if (Thread.currentThread().isInterrupted()) {
				break;
			}
			try {
				Socket oSocket = pSocketServer.listen(0);
				
				doConnectionDetect(oSocket);
				
//				System.out.println(oConnection.getStreamAddress());
				// pConnectionsMngr.add(oConnection. getStreamAddress(), oConnection);
				
			}
			catch (Exception e) {
				PssLogger.logError(e, "["+Thread.currentThread().getName()+"]");
			}
		}
		
	}
	
	protected abstract void doConnectionDetect(Socket oSocket) throws Exception ;

	
	public void launchTcpMessageServer() throws Exception {
//		if (this.pConnectionsMngr == null) {
//			JExcepcion.SendError("Debe configurarse un Connection Manager antes de lazar en TCP Message Server");
//		}
//		
		if (pSocketServerThread!=null) {
			if (pSocketServerThread.isAlive() && !pSocketServerThread.isInterrupted()) {
				PssLogger.logError("ERROR! Se esta tratando de levantar un socket que ya esta levantado!!!!!!!");
				return;
			}
		}
		
		if (this.pTcpPort <= 0) {
			pTcpPort = DEFAULT_PORT;
		}
		
    PssLogger.logDebug("Launching TcpMessageServer on port [" + pTcpPort + "]");

    // TODO: Falta control sobre los atributos de la clase, x ejemplo si 
    // ya se lanzo todo y el socket server esta conectado.
    
			this.pSocketServer = new TcpServer();
			pSocketServer.start(this.pTcpPort);
			Runnable runMethod = new Runnable() {
					public void run() {
						runMsgMngrSocketServer();
					}
			};
		  pSocketServerThread = new Thread(runMethod);
			PssLogger.logDebug("Launching Socket server");
			pSocketServerThread.start();
	
		
//		while (true) {
//			if (Thread.currentThread().isInterrupted())
//				break;
//
//			Thread.sleep(CHECK_THREAD_ALIVE_TIMEOUT);
//		} // end while
		
//		PssLogger.logDebug("Interrupting Thread");
//		pSocketServerThread.interrupt();
//		PssLogger.logDebug("Stopping SocketServer");
//		this.pSocketServer.stop();
//		
//		PssLogger.logDebug("Waiting for socket server thread finish");
//		while (pSocketServerThread.isInterrupted() == false) {
//			Thread.sleep(100);
//		}
//		PssLogger.logDebug("Socket server thread is really finished");
	}
	
	public void stopTcpMessageServer() throws Exception {
		PssLogger.logDebug("Interrupting Thread");
		pSocketServerThread.interrupt();
		PssLogger.logDebug("Stopping SocketServer");
		this.pSocketServer.stop();
		
		PssLogger.logDebug("Waiting for socket server thread finish");
		while (pSocketServerThread.isInterrupted() == false) {
			Thread.sleep(100);
		}
		pSocketServerThread=null;
		PssLogger.logDebug("Socket server thread is really finished");
	}
	
//	/**
//	 * Launch TCP Message Manager
//	 * 
//	 * @param port
//	 * @return Tcp Message Manager Instance
//	 * @throws Exception
//	 */
//	public static TcpMessageServer launchTcpMessageServer(int zPort) throws Exception {
//		
//		TcpMessageServer oMsgMngr = new TcpMessageServer();
//		pS
//		oMsgMngr.launch();
//		return oMsgMngr;
//	}
//
//	public static TcpMessageServer launchTcpMessageServer() throws Exception {
//		return launchTcpMessageServer(DEFAULT_PORT);
//	}
//	
//	public static void main(String[] args) {
//		try {
//			TcpMessageServer.launchTcpMessageServer(TcpMessageServer.DEFAULT_PORT);
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	/**
	 * @return the pConnectionsMngr
	 */
	public ConnectionsManager getConnectionsMngr() {
		return pConnectionsMngr;
	}

	/**
	 * @param zConnectionsMngr 
	 * @param connectionsMngr the pConnectionsMngr to set
	 * @throws Exception 
	 */
	public void setConnectionsMngr(ConnectionsManager zConnectionsMngr) throws Exception {
//		if (zConnectionsMngr == null) {
//			JExcepcion.SendError("No se puede configurar un Connection Manager en nulo");
//		}
			
		pConnectionsMngr = zConnectionsMngr;
	}

	/**
	 * @return the pTcpPort
	 */
	public int getTcpPort() {
		return pTcpPort;
	}

	/**
	 * @param zTcpPort 
	 * @param tcpPort the pTcpPort to set
	 */
	public void setTcpPort(int zTcpPort) {
		pTcpPort = zTcpPort;
	}

}
