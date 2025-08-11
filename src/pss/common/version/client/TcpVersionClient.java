package pss.common.version.client;

import pss.core.JAplicacion;
import pss.core.tools.PssLogger;

public class TcpVersionClient {

	Thread thread;
	String connection;
	IVersionClientProcess process;

	TcpVersionClient(IVersionClientProcess p) {
		process = p;
	}

	public void start(String zConnection) throws Exception {
		connection = zConnection;
		thread = new Thread() {
			@Override
			public void run() {
				runClient();
				super.run();
			}
		};
		thread.run();
	}
 
	void runClient() {
		while (!Thread.currentThread().isInterrupted()) {
			try {
				JAplicacion.openSession();
				JAplicacion.GetApp().openApp("Client Version Admin", JAplicacion.AppTipoThread(), true);
				runClientWithDatabase();
			} catch (Exception e) {

			}
			JAplicacion.closeSession();
		}
	}

	void runClientWithDatabase() {
		while (!Thread.currentThread().isInterrupted()) {
			try {
				try {
					process.onProcess(connection);
				} catch (Exception e) {

				}
				Thread.sleep(60000);
			} catch (InterruptedException e) {
				break;
			} catch (Exception e) {
				PssLogger.logError(e);
			}
		}
	}

	public void stop() throws Exception {
		while (thread.isAlive()) {
			thread.interrupt();
			Thread.sleep(100);
		}
	}

}
