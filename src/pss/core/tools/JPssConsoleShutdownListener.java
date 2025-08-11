package pss.core.tools;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class JPssConsoleShutdownListener {

  private static JPssConsoleShutdownListener INSTANCE = new JPssConsoleShutdownListener();

  private static final int COMMAND_SHUTDOWN_LENGTH = 8;
  private static final int PORT = 9666;
  private static final String COMMAND_SHUTDOWN = "shutdown";
  private Thread oSocketTread;
  private ServerSocket oServerSocket;


  public static JPssConsoleShutdownListener getInstance() {
    return INSTANCE;
  }

  public void start() {
    this.oSocketTread = new Thread() { @Override
		public void run() {
      startUpgradeSocket();
    }};
    this.oSocketTread.setDaemon(true);
    this.oSocketTread.setName("Pss Upgrade thread");
    this.oSocketTread.start();
  }

  private void startUpgradeSocket() {
    try {
      while (!Thread.currentThread().isInterrupted()) {
        this.oServerSocket = new ServerSocket(PORT, 5);
        Socket oClientSocket = this.oServerSocket.accept();
        this.performListening(oClientSocket);
        Thread.sleep(1000);
      }
    } catch (Exception e) {
      PssLogger.logDebug("Error en la ejecución del upgrade socket");
//      JDebugPrint.logDebug(e);
    }
    this.oServerSocket = null;
    this.oSocketTread = null;
  }
  /**
   * Listen the command
   */
  private void performListening(Socket zSocket) throws Exception {
    while (true) {
      InputStream oInput = zSocket.getInputStream();
      BufferedInputStream oBuffered = new BufferedInputStream(oInput);

      String sResponse = "";
      for (int i = 0; i < COMMAND_SHUTDOWN_LENGTH; i++) {
        sResponse += "" + (char) oBuffered.read();
      }
      if (sResponse.equals(COMMAND_SHUTDOWN)) {
        this.shutdown();
      } else {
        PssLogger.logDebug("Upgrade thread: comando no contemplado --> " + sResponse);
      }
    }
  }

  private void shutdown() throws Exception {
//    UITools.contexts().performInAWTEventDispatchContext(new JAct() {@Override
//		public void Do() {
//      doShutdown();
//    }});
  }
  private void doShutdown() {
//    try {
//      this.oSocketTread.interrupt();
//      if (this.oServerSocket != null && !this.oServerSocket.isClosed()) {
//        this.oServerSocket.close();
//      }
//    } catch (Exception e) {
//      PssLogger.logDebug("Error al intentar matar el upgrade socket");
//      PssLogger.logDebug(e);
//    }
//    // close the application and desktop
//    UITools.desktop().shutdown();
  }



}
