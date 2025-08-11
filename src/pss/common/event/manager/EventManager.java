package pss.common.event.manager;

import pss.core.JAplicacion;
import pss.core.data.interfaces.connections.JBDatos;
import pss.core.services.records.JRecords;
import pss.core.tools.PssLogger;
import pss.core.tools.WinServiceTools;

public class EventManager {

	public static void main(String[] args) {
		try {
			if (args.length >= 1) {
				WinServiceTools w = new WinServiceTools();
				w.setClassName("pss.common.event.manager.EventManager");
				w.setServiceName("Lex Event Manager " + args[1]);
				w.install();
				System.exit(0);
			}
		} catch (Exception eee) {
		}
		EventManager.startEvent();
	}

	/**
	 * Start the Event Manager Process
	 */
	public static void startEvent() {
		while (Thread.currentThread().isInterrupted() == false) {
			try {
				EventManager mgr = new EventManager();
				mgr.start();
				Thread.sleep(60000);
			} catch (InterruptedException e) {
				PssLogger.logDebug("Interruption received");
				break;
			} catch (Exception e) {
				PssLogger.logError(e);
			}
		}

	}

	public void start() throws Exception {

		try {
			JAplicacion.openSession();
			JAplicacion.GetApp().openApp("eventmanager",JAplicacion.AppTipoThread(), true);

			PssLogger.logInfo("checking events to manage ..."); //$NON-NLS-1$
			JRecords<BizEvent> evts = new JRecords<BizEvent>(BizEvent.class);
			evts.addFilter("processed", false);
			evts.setTop(100);
			evts.toStatic();
			evts.firstRecord();
			while (evts.nextRecord()) {
				BizEvent evt = evts.getRecord();
				processEvent(evt);
			}
			PssLogger.logInfo("checking events finished ..."); //$NON-NLS-1$

		} finally {
			JAplicacion.GetApp().closeApp();
			JAplicacion.closeSession();
		}

	}

	private void processEvent(BizEvent evt) throws Exception {
		JBDatos.GetBases().beginTransaction();
		try {
			PssLogger.logInfo("processing event: " + evt.getEventCode() ); //$NON-NLS-1$
			boolean processed = processAction(evt);
			evt.setProcessed(processed);
			evt.updateRecord();
			JBDatos.GetBases().commit();
			PssLogger.logInfo("event processed"); //$NON-NLS-1$
		} catch (Exception eee) {
			PssLogger.logError(eee, "event error", true); //$NON-NLS-1$
			JBDatos.GetBases().rollback();
		}
	}

	private boolean processAction(BizEvent evt) throws Exception {
//		if (evt.getEventAction() == BizEvent.EV_ACTION_MAIL)
//			return this.sendMail(evt);
		return false;
	}

	
	private boolean sendMail(BizEvent evt) throws Exception {
//		PssLogger.logInfo("sending mail for event: " + evt.getEventCode() + "-" + evt.getEventAction() + ", " + evt.getEventActionId()); //$NON-NLS-1$
//		BizMailCasilla ms = new BizMailCasilla();
//		ms.dontThrowException(true);
//		if (ms.read((int) evt.getEventActionId())) {
//			try {
//				ms.send(evt.getTitle(), evt.getInfo());
//				PssLogger.logInfo("mail sent"); //$NON-NLS-1$
//			} catch (Exception eee) {
//				PssLogger.logError(eee, "mail error", true); //$NON-NLS-1$
//				return false;
//			}
//		}
//		
		return true;
	}

}
