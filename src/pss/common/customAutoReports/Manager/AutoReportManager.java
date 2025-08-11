package pss.common.customAutoReports.Manager;

import pss.common.customAutoReports.scheduler.BizAutoReportSchedule;
import pss.core.JAplicacion;
import pss.core.data.interfaces.connections.JBDatos;
import pss.core.services.records.JRecords;
import pss.core.tools.PssLogger;

public class AutoReportManager {

	public static void main(String[] args) {
		AutoReportManager.process();
	}

	/**
	 * Start Auto Report Manager Process
	 */
	public static void process() {
		while (Thread.currentThread().isInterrupted() == false) {
			try {
				AutoReportManager mgr = new AutoReportManager();
				mgr.start();
				Thread.sleep(180000);
			} catch (InterruptedException e) {
				PssLogger.logDebug("Interruption received");
			} catch (Throwable e) {
				PssLogger.logError(e);
			}
		}
		return;
	}

	public void start() throws Exception {

		try {
			JAplicacion.openSession();
			JAplicacion.GetApp().openApp("autoreportmanager",
					JAplicacion.AppTipoThread(), true);
			PssLogger.logInfo("checking auto reports schedule ..."); //$NON-NLS-1$
			JRecords<BizAutoReportSchedule> schedulers = new JRecords<BizAutoReportSchedule>(
					BizAutoReportSchedule.class);
			schedulers.toStatic();
			schedulers.firstRecord();
			while (schedulers.nextRecord()) {
				BizAutoReportSchedule scheduler = schedulers.getRecord();
				processReport(scheduler);
			}
			PssLogger.logInfo("checking auto reports finished ..."); //$NON-NLS-1$
		} catch (Throwable e) {
			PssLogger.logError(e);			
		} finally {
			JAplicacion.GetApp().closeApp();
			JAplicacion.closeSession();
		}

	}

	private void processReport(BizAutoReportSchedule scheduler)
			throws Exception {
		JBDatos.GetBases().beginTransaction();
		try {
			PssLogger.logInfo("processing report: " + scheduler.getIdReport() + "-" + scheduler.getObjReportList().getReportName()); //$NON-NLS-1$
			scheduler.processReport(false, true);
			JBDatos.GetBases().commit();
			PssLogger.logInfo("report processed"); //$NON-NLS-1$
		} catch (Exception eee) {
			PssLogger.logError(eee, "report error"); //$NON-NLS-1$
			JBDatos.GetBases().rollback();
		}
	}

//	private void processAction(BizEvent evt) throws Exception {
//		if (evt.getEventAction() == BizEvent.EV_ACTION_MAIL)
//			sendMail(evt);
//	}
//
//	private void sendMail(BizEvent evt) throws Exception {
//		PssLogger.logInfo("sending mail for event: " + evt.getEventCode() + "-" + evt.getEventAction() + ", " + evt.getEventActionId()); //$NON-NLS-1$
//		BizMailCasilla ms = new BizMailCasilla();
//		ms.dontThrowException(true);
//		if (ms.read((int) evt.getEventActionId())) {
//			ms.send(evt.getTitle(), evt.getInfo());
//		}
//	}

}
