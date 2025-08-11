/*
 * Created on 22-jul-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.platform.tasks;

import pss.core.JAplicacion;
import pss.core.tools.PssLogger;

public abstract class JWebCleanUpTask {


  private Thread oThread;
  private long iDelay;



  public JWebCleanUpTask(long zDelay) {
    this.iDelay = zDelay;
  }


  public void start() {
    this.oThread = new Thread(this.getName()) {
      @Override
			public void run() {
        while (!Thread.currentThread().isInterrupted() && shouldContinue()) {
          try {
      			Thread.sleep(iDelay);
      			JAplicacion.openSession();
      			JAplicacion.GetApp().openApp("CleanUpTask_"+getName(),JAplicacion.AppService(), false);
            
            doCleanUp();
            

            if (JAplicacion.GetApp()!=null)
            	JAplicacion.GetApp().closeApp();
      			JAplicacion.closeSession();
          } catch (InterruptedException e) {
            break;
	        } catch (Exception e) {
	        	PssLogger.logError(e);
	        }
        }
        taskFinished();
      }
    };
    this.oThread.setDaemon(true);
    this.oThread.start();
  }
  
  
  //
  //  METHODS TO OVERRIDE IN SUBCLASSES
  //
  public abstract String getName();
  protected boolean shouldContinue() {
    return true;
  }
  protected void taskFinished() {
    oThread = null;
  }
  protected abstract void doCleanUp();

}
