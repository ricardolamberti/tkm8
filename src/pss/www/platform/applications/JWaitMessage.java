package pss.www.platform.applications;

import pss.www.platform.actions.JWebActionFactory;

public class JWaitMessage {
	
	public JWaitMessage(String icon,boolean progress, boolean acceptCancel, long porcentaje, long total,String mensaje) {
		if (JWebActionFactory.getCurrentRequest()==null) return;
  	wait_idrequest =  JWebActionFactory.getCurrentRequest().getRequestid();
  	wait_icon = icon;
  	wait_progress = progress;
  	wait_percent=porcentaje;
  	wait_total=total;
  	wait_acceptCancel = acceptCancel;
  	wait_message = mensaje;
  	
	}
  String wait_idrequest;
 	boolean wait_progress;
  boolean wait_acceptCancel;
  long wait_percent;
  long wait_total;
  
  boolean cancel=false;
  

	public boolean isCancel() {
		return cancel;
	}

	public void setCancel(boolean cancel) {
		this.cancel = cancel;
	}
	String wait_message;
  String wait_icon;
  public String getWait_idrequest() {
		return wait_idrequest;
	}

	public boolean isWait_progress() {
		return wait_progress;
	}
  public long getWait_total() {
		return wait_total;
	}

	public boolean isWait_acceptCancel() {
		return wait_acceptCancel;
	}

	public long getWait_percent() {
		return wait_percent;
	}
	public String getWait_icon() {
		return wait_icon;
	}
	public String getWait_message() {
		return wait_message;
	}
}
