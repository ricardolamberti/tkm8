package  pss.common.backup.core.service;

import pss.common.backup.procesos.JBackUpProcedures;
import pss.common.backup.settings.BizMainSetting;


public class BackUpThread extends Thread implements Runnable{

	public final static String BACKUP="BACKUP";
	public final static String EXPORT="EXPORT";
	
	private BizMainSetting oBackUpSetting =null;
	private String pOper="";
	
	BackUpThread(BizMainSetting BackUpSetting){	
		this.oBackUpSetting = BackUpSetting;		
	}
	
	public void setOperation(String zValor) { this.pOper=zValor; }
	
	
	public void run() {
		if (this.pOper.equals(BackUpThread.BACKUP)) {
			try {
				JBackUpProcedures oBP = new JBackUpProcedures(oBackUpSetting);
				oBP.BackUp();
			}catch  (Exception e) {
			}
		}
	}


}
