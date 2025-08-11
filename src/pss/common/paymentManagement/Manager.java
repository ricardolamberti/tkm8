/**
 * 
 */
package pss.common.paymentManagement;

import java.util.Date;

import pss.common.mail.mailing.BizMail;
import pss.common.mail.message.BizMessage;
import pss.common.security.BizUsuario;
import pss.core.JAplicacion;
import pss.core.data.interfaces.connections.JBDatos;
import pss.core.services.records.JRecords;
import pss.core.tools.JDateTools;
import pss.core.tools.PssLogger;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;

/**
 * @author sgalli
 * 
 */
public class Manager {
	

	static JMap<String, Date> mMsgs = JCollectionFactory.createMap();

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Manager.process();
	}

	public static void process() {
		while (Thread.currentThread().isInterrupted() == false) {
			try {
				JAplicacion.openSession();
				JAplicacion.GetApp().openApp("paymentManagement", "process", true);
				JBDatos.GetBases().beginTransaction();
				Manager mgr = new Manager();
				BizUsuario usr = new BizUsuario();
				usr.Read("ADMIN.PWG");
				BizUsuario.SetGlobal(usr);
				mgr.checkPendingPayments();
				JBDatos.GetBases().commit();
				JAplicacion.GetApp().closeApp();
				JAplicacion.closeSession();
				Thread.sleep(60000);
			} catch (InterruptedException e) {
				PssLogger.logDebug("Interruption received");
				break;
			} catch (Exception e) {
				PssLogger.logError(e);
			}
		}
	}

	private void checkPendingPayments() throws Exception {
		JRecords<BizPendingPaymentConfig> docs = new JRecords<BizPendingPaymentConfig>(BizPendingPaymentConfig.class);
		docs.addOrderBy("company");
		//docs.addFilter("company", "BCV");
		docs.toStatic();
		try {
			while (docs.nextRecord()) {
				BizPendingPaymentConfig doc = docs.getRecord();
//
//				BizCuentaMovs movs;
//				movs = getPendingInvoices(doc);
//
//				if (movs.nextRecord() == false) {
//					removePenalize(doc.getCompany());
//					continue;
//				}
//				int numberOfInvoicesPending = 1;
//				int ignore = (int) doc.getIgnoreInvoices();
//				double amount = 0.0f;
//				BizCuentaMov mov = movs.getRecord();
//				while (movs.nextRecord()) {
//					BizCuentaMov m = movs.getRecord();
//					amount += m.getObjTransaction().GetMontoTotal();
//					if (ignore-- > 0)
//						mov = movs.getRecord();
//					else
//						numberOfInvoicesPending++;
//				}
//				int unpaidTotal = numberOfInvoicesPending + ignore;
//			
//				String samount = JRegionalFormatterFactory.getBusinessFormatter().formatCurrency(amount);
//				Date docdate = mov.getFechaContab();
//				Date checkdate = JDateTools.SubDays(new Date(), doc.getDaysToMessage());
//				PssLogger.logDebug("Company       			:" + doc.getCompany());
//				PssLogger.logDebug("Invoice Ignore 			:" + ignore);
//				PssLogger.logDebug("Invoice Unpaid Total	:" + unpaidTotal );
//				PssLogger.logDebug("Invoice Unpaid Ignore	:" + numberOfInvoicesPending);
//				PssLogger.logDebug("Total Unpaid  			:" + samount );
//				PssLogger.logDebug("First Invoice 			:" + JDateTools.DateToString(docdate));
//				PssLogger.logDebug("Last ok Date  			:" + JDateTools.DateToString(checkdate));
//				PssLogger.logDebug("Going to check if have to send messages ...");
//				if (checkdate.after(docdate)) {
//					sendMessagesToAllUsers(doc, numberOfInvoicesPending, samount);
//				} else {
//					removeAllMessage(doc.getCompany());
//				}
//
//				if (numberOfInvoicesPending < 4)
//					removePenalize(doc.getCompany());

			}
		} catch (Exception eee) {
			JBDatos.GetBases().rollback();
			JBDatos.GetBases().beginTransaction();
			PssLogger.logError(eee);
		} finally {
			docs.closeRecord();
		}
	}

	private void removeAllMessage(String company) throws Exception {

		BizMail mail = new BizMail();
		mail.addFilter("company", company);
		mail.deleteMultiple();

		BizMessage msg = new BizMessage();
		msg.addFilter("company", company);
		msg.deleteMultiple();

	}

	private void sendMessagesToAllUsers(BizPendingPaymentConfig doc, int numberOfInvoicesPending, String samount) throws Exception, InterruptedException {
		long lapse = doc.getMessageLapse();
		if (numberOfInvoicesPending == 3)
			lapse = lapse / 4;
		if (numberOfInvoicesPending == 4)
			lapse = lapse / 8;
		if (numberOfInvoicesPending >= 5)
			lapse = lapse / 16;
		PssLogger.logDebug("Timeout, have to send alert message , lapse: " + lapse);

		if (numberOfInvoicesPending >= 4 ) {
			int seconds = 1 * (numberOfInvoicesPending - 3);
			if (seconds>5) seconds = 5;
			addPenalize(doc.getCompany(), seconds);
		}

		PssLogger.logDebug("Check if has to send msg ... " ); 
		if (this.hasToSendMessage(doc.getCompany(), lapse)) {
			PssLogger.logDebug("Going to send msg ... " ); 
			removeAllMessage(doc.getCompany());
			mMsgs.removeElement(doc.getCompany());
			mMsgs.addElement(doc.getCompany(), new Date());
			JRecords<BizUsuario> users = new JRecords<BizUsuario>(BizUsuario.class);
			users.addFilter("company", doc.getCompany());
			users.addFilter("activo", true);
			users.addFixedFilter(" usuario not like 'ADMIN." + doc.getCompany() + "'");
			users.toStatic();
			while (users.nextRecord()) {
				BizUsuario user = users.getRecord();
				this.sendMessage(user.GetUsuario(), doc, numberOfInvoicesPending >= 4, samount);
				Thread.sleep(100);
			}
		}
	}

//	private BizCuentaMovs getPendingInvoices(BizPendingPaymentConfig doc) throws Exception {
//		BizCuentaMovs movs;
//		movs = new BizCuentaMovs();
//		movs.setJoinPersona(true);
//		movs.addFixedFilter(" rtl_cliente.cliente = " + doc.getClient() + " ");
//		movs.addFixedFilter(" rtl_cuenta_mov.client_group='C' ");
//		movs.addFixedFilter(" RTL_CUENTA_MOV.pagado='P' ");
//		movs.addFixedFilter(" RTL_CUENTA_MOV.operacion in ('D','C') ");
//		movs.addFixedFilter(" RTL_CUENTA_MOV.autorizado='S' ");
//		movs.addFixedFilter(" rtl_cuenta_mov.reversado='N' ");
//		movs.addFixedFilter(" rtl_cuenta_mov.anulacion='N' ");
//		movs.addFixedFilter(" rtl_cuenta_mov.company= 'PWG' ");
//		// movs.addFixedFilter(" rtl_cuenta_mov.fecha <= '"+ new Date()+ "'");
//		movs.addOrderBy("rtl_cuenta_mov.fecha_contab");
//		movs.readAll();
//		return movs;
//	}

	private void removePenalize(String company) throws Exception {
		BizPenalizeDebt debt = getPenalizeDebt(company);
		if (debt.read()) {
			debt.delete();
		}
	}

	private BizPenalizeDebt getPenalizeDebt(String company) throws Exception {
		BizPenalizeDebt debt = new BizPenalizeDebt();
		debt.addFilter("company", company);
		debt.dontThrowException(true);
		return debt;
	}

	private void addPenalize(String company, int seconds) throws Exception {
		BizPenalizeDebt debt = getPenalizeDebt(company);
		if (debt.read()) {
			if (seconds>7) seconds = 7;
			if (debt.getPenalizeTime() != seconds) {
				debt.setPenalizeTime(seconds);
				debt.update();
			}
		} else {
			debt = new BizPenalizeDebt();
			debt.setCompany(company);
			debt.setPenalizeTime(seconds);
			debt.insert();
		}
	}

	private boolean hasToSendMessage(String company, long messageLapse) throws Exception {
		Date lastmsg = mMsgs.getElement(company);
		if (lastmsg == null)
			return true;
		long last = lastmsg.getTime();
		long now = new Date().getTime();
		if (last + (messageLapse * 60 * 1000) < now)
			return true;
		return false;
	}

	private void sendMessage(String userId, BizPendingPaymentConfig cfg, boolean penalyze, String samount ) throws Exception {

		BizMail mail = new BizMail();

		mail.setCompany(cfg.getCompany());
		// mail.setFolder("INBOX");
		mail.setUrgente(true);
		mail.setUsuario("ADMIN");
		mail.addDestinatario(userId);
		String title = cfg.getTitle();
		
		mail.setTitle(title);
		String text = cfg.getTextMessage(); 
		if (penalyze)
			text += ". Se asignan menos recursos a su empresa y esto ocasiona una baja de performance.";
		mail.setMensaje(text);
		mail.setSleepTime(15000L);
		// mail.setDateCreation(new Date());
		mail.setValidTime(JDateTools.addMinutes(new Date(), cfg.getMessageLapse() + 1));
		mail.processInsert();
	}

}
