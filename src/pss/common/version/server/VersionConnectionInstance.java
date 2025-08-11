package pss.common.version.server;

import java.net.Socket;

import pss.common.version.protocol.MsgException;
import pss.core.JAplicacion;
import pss.core.connectivity.messageManager.common.connectionChannel.BaseTcpConnectionChannel;
import pss.core.connectivity.messageManager.common.message.MessageEnvelope;
import pss.core.tools.PssLogger;

public class VersionConnectionInstance extends BaseTcpConnectionChannel {
	Thread thread;
	IVersionServerProcess process;
	
 public boolean isLaunched() {
		return thread!=null && thread.isAlive() && !thread.isInterrupted();
 } 
 
public VersionConnectionInstance(IVersionServerProcess p) throws Exception {
	 process = p;
	}
 
	public void launchConnectionInstance(Socket s ) throws Exception {
		connect(s);
	}
	
	public void connect(Socket zSocket) throws Exception {
			super.connect(zSocket);
			thread=new Thread() {
				@Override
				public void run() {
					runProccessRequest();
					super.run();
				}
			};
			thread.start();
	}

	void runProccessRequest() {
//			while  (!Thread.currentThread().isInterrupted()) {
				try {
			    JAplicacion.openSession(); 
			    JAplicacion.GetApp().openApp("Server Version Admin", JAplicacion.AppTipoThread(), true );
			    runServerWithDatabase();
					if (!isConnected()) return;
				} catch (InterruptedException e) {
					return;
				} catch (Exception e) {
					PssLogger.logError(e);
				}
	//		}
	    JAplicacion.closeSession();
	}
		
	void runServerWithDatabase() throws Exception {
//		while (!Thread.currentThread().isInterrupted()) {
			while (!Thread.currentThread().isInterrupted() && !this.isIncommingMsgWaiting()) {
				if (!isConnected()) return;
				Thread.sleep(100);
			}
			if (!isConnected()) return;

			try {
				processRequest();
			} catch (InterruptedException e) {
				return;
			} catch (Exception e) {
				PssLogger.logError(e);
				sendError(e);
			}
//		}
	}
	
	void sendError(Exception e) throws Exception {
		MessageEnvelope resp = new MessageEnvelope();
		MsgException error = new MsgException(e);
		resp.setMsgContent(error);
		sendMsg(resp);
	}
	
	void processRequest()  throws Exception {
		MessageEnvelope msg = this.readMsg();
		MessageEnvelope resp = process.onProcess(msg);
		sendMsg(resp);
	}	
	

}
