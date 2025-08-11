package pss.tourism.telegram;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Date;

import com.pengrad.telegrambot.TelegramBot;

import pss.common.event2.telegram.BizTelegramUserChannel;
import pss.common.event2.telegram.TelegramBotManager;
import pss.core.JAplicacion;
import pss.core.data.BizPssConfig;
import pss.core.services.records.JRecords;
import pss.core.tools.JDateTools;
import pss.core.tools.PssLogger;
import pss.core.tools.WinServiceTools;
import pss.core.tools.collections.JIterator;
import pss.tourism.interfaceGDS.log.BizInterfaceLog;
import pss.tourism.pnr.BizPNRTicket;
import pss.tourism.telegram.event.BizTelegramEvent;

public class TelegramUserBot extends TelegramBotManager {

      public static final int EVT_TICKET_ROBOT_OFFLINE = 3;
        public static final int EVT_TICKET_NO_TICKETS = 4;

        /**
         * Telegram bot token for user notifications. The value must be supplied via the
         * {@code TELEGRAM_USER_BOT_TOKEN} environment variable.
         */
        public static final String TOKEN = System.getenv("TELEGRAM_USER_BOT_TOKEN");

	public TelegramUserBot() throws Exception {
	}

	public static void main(String[] args) {
		try {

			// BufferedReader br = new BufferedReader(new
			// FileReader(JPath.PssPathDataFiles()+
			// "/mesas_totales_agrp_politica.txt"));
			try {
				// TelegramBotManager mng = new TelegramBotManager();
				// mng.startDevBotManager();
				//
				// while (Thread.currentThread().isInterrupted() == false) {
				// //CleanAllPendingRegistrations();
				// //PssLogger.logDebug("Esperando mensajes ...");
				// Thread.sleep(60000);
				//
				// }

				if (args.length >= 1) {
					WinServiceTools w = new WinServiceTools();
					w.setClassName("pss.tourism.telegramBot.TelegramUserBot");
					w.setServiceName("PWG Telegram User Bot");
					w.install();
					System.exit(0);
				}

				startTelegramSendMessages();
				processTelegramMessages();

			} finally {

			}

		} catch (Exception eee) {
			PssLogger.logError(eee);

		}
	}

	public static void processTelegramMessages() throws Exception {
		System.out.println("Telegrams messages");
		TelegramUserBot mng = new TelegramUserBot();
		mng.setDefaultConfigFromIni();
		mng.setBot(new TelegramBot(TOKEN));
		mng.processBotMessages();
	}

	public static Thread startTelegramSendMessages() {
		Thread oth1 = new Thread() {
			public void run() {
				try {
					TelegramUserBot mgr = new TelegramUserBot();
					mgr.sendTelegramMessages();
				} catch (Exception ee) {
				}
			}
		};
		oth1.start();
		return oth1;
	}

	public void sendTelegramMessages() throws Exception {
		try {
			Thread.sleep(5000);

			JAplicacion.openSession();
			JAplicacion.GetApp().openApp("send.telegram.messages.", JAplicacion.AppService(), true);

			while (Thread.currentThread().isInterrupted() == false) {
				try {

					JRecords<BizTelegramUserChannel> ch = new JRecords<BizTelegramUserChannel>(BizTelegramUserChannel.class);
					ch.addFilter("registered", true);
					ch.addFilter("confirmed", true);
					ch.toStatic();

					JIterator<BizTelegramUserChannel> it = ch.getStaticIterator();
					while (it.hasMoreElements()) {
						BizTelegramUserChannel u = it.nextElement();

						JRecords<BizInterfaceLog> logs = new JRecords<BizInterfaceLog>(BizInterfaceLog.class);
						logs.addFilter("company", u.getCompany());
						logs.toStatic();

						JIterator<BizInterfaceLog> ilogs = logs.getStaticIterator();

						while (ilogs.hasMoreElements()) {
							BizInterfaceLog ilog = ilogs.nextElement();

							long code;
							
							String id = ilog.getId();


							// Date lastDate = tkt.getLastPnr();
							if (ilog.getLastTransfer() != null) {
								code = EVT_TICKET_NO_TICKETS;
								
							
								Date date = JDateTools.SubDays(new Date(), 3);
								Date last = JDateTools.SubDays(new Date(), 20);
								if (ilog.getLastTransfer().before(date) && ilog.getLastEcho().after(last)) {

									if (!hasEvent(u.getCompany(), id, code)) {
										try {

											String message = "No se reciben boletos desde el " + ilog.getLastTransfer() + " del robot " + id;
											URL msg = new URL("https://api.telegram.org/bot" + TOKEN + "/sendMessage?chat_id=" + u.getChannelId() + "&text=" + message + "");
											BufferedReader in = new BufferedReader(new InputStreamReader(msg.openStream()));
											in.close();

											saveTelegramEvent(u.getCompany(), id, code);

										} catch (Exception ee) {

										}
									}
								} else {
									removeTelegramEvent(u.getCompany(), id, code);
								}

							}

							if (ilog.getLastEcho() != null) {
								code = EVT_TICKET_ROBOT_OFFLINE;

								Date date = JDateTools.SubHours(new Date(), 1);
								Date last = JDateTools.SubDays(new Date(), 20);
								if (ilog.getLastEcho().before(date) && ilog.getLastEcho().after(last) ) {
									if (!hasEvent(u.getCompany(), id, code)) {
										try {

											String message = "No hay conexión con TKM desde hace mas de 1 hora desde el robot " + id;
											URL msg = new URL("https://api.telegram.org/bot" + TOKEN + "/sendMessage?chat_id=" + u.getChannelId() + "&text=" + message + "");
											BufferedReader in = new BufferedReader(new InputStreamReader(msg.openStream()));
											in.close();

											saveTelegramEvent(u.getCompany(), id, code);

										} catch (Exception ee) {

										}
									} 
								} else {
									removeTelegramEvent(u.getCompany(), id, code);
								}
							}

						}

						Thread.sleep(60000);

					}
					

				} catch (InterruptedException e) {
					try {

					} catch (Exception ignored) {
					}
					PssLogger.logDebug("Interruption received");
					break;
				}
				Thread.sleep(60000);
			}

		} catch (Exception ee) {
			System.out.println(ee);

		} finally {
			JAplicacion.GetApp().closeApp();
			JAplicacion.closeSession();
		}

	}
	
	private void removeTelegramEvent(String company, String id, long code) throws Exception {
		BizTelegramEvent evt = new BizTelegramEvent();
		evt.dontThrowException(true);
		evt.addFilter("company", company);
		evt.addFilter("event_code_id", id);
		evt.addFilter("event_code", code);
		if (evt.read()) {
			evt = new BizTelegramEvent();
			evt.dontThrowException(true);
			evt.addFilter("company", company);
			evt.addFilter("event_code_id", id);
			evt.addFilter("event_code", code);
			evt.execDeleteAll();
		}
	}
	

	private boolean hasEvent(String company, String id, long code) throws Exception {
		BizTelegramEvent evt = new BizTelegramEvent();
		evt.dontThrowException(true);
		evt.addFilter("company", company);
		evt.addFilter("event_code_id", id);
		evt.addFilter("event_code", code);
		return evt.read();
	}

	private void saveTelegramEvent(String company, String id, long code) throws Exception {
		BizTelegramEvent evt = new BizTelegramEvent();
		evt.setCompany(company);
		evt.setEventDate(new Date());
		evt.setEventCode(code);
		evt.setEventCodeid(id);
		evt.execProcessInsert();
	}

}
