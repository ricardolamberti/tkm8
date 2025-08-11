package pss.common.restJason.apiReqHistory;

import pss.common.terminals.connection.server.JTerminalPoolServer;
import pss.core.JAplicacion;
import pss.core.services.records.JRecords;
import pss.core.tools.PssLogger;

public class JApiRobot {

	public JApiRobot() {
	}

  private static Thread robot;
  public static void startRobot() throws Exception {
  	if (robot!=null) return;
  	Thread t = new Thread(new Runnable () {
  		public void run() { 
  			sendLoop(); 
  		}});
    t.setName("HTTP-Client-Sender");    
    t.start();
    PssLogger.logDebug("Start HTTP-Client-Server");
    robot=t;
  }
  
	private static void sendLoop() {
	  try {
	  	JAplicacion.openSession();
	    JAplicacion.GetApp().openApp("HTTP-Client", JAplicacion.AppTipoThread(), true );
	    while (true) {
	    	if (Thread.currentThread().isInterrupted()) break;
	    	sendPendings();
	    	Thread.sleep(2000);
	    }
	    JAplicacion.GetApp().closeApp();
	    PssLogger.logDebug("HTTP-Client Shutdown");
	    robot=null;
	  } catch ( Exception e) {
	  	PssLogger.logDebug("HTTP-Client ERROR");
    	PssLogger.logError(e);
    	robot=null;
	  }
	}
		
  public static void stopRobot() {
  	if (robot==null) return;
  	robot.interrupt();
  }

  public static boolean isRunning() {
  	return robot!=null;
  }
  public static void sendPendings() throws Exception {
  	JRecords<BizApiRequestHistory> recs = new JRecords<BizApiRequestHistory>(BizApiRequestHistory.class);
  	recs.addFilter("status", BizApiRequestHistory.PENDING);
  	recs.addFilter("oper", BizApiRequestHistory.SALIENTE);
  	recs.addOrderBy("creation_date", "asc");
  	recs.readAll();
  	while (recs.nextRecord()) {
  		BizApiRequestHistory h = recs.getRecord();
  		try {
  			h.execProcessEnviar();
  		} catch (Exception e) {
  			PssLogger.logDebug("Error " + h.getId() + "- " + e.getMessage());
  		}
  	}
  }

}
