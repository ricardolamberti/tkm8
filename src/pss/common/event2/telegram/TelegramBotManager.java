package pss.common.event2.telegram;

import java.io.File;
import java.util.Date;
import java.util.Iterator;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendDocument;
import com.pengrad.telegrambot.request.SendMessage;

import pss.JPath;
import pss.common.event2.mail.BizMailSender;
import pss.common.security.BizTemporalPassword;
import pss.common.security.BizUsuario;
import pss.core.JAplicacion;
import pss.core.data.BizPssConfig;
import pss.core.data.interfaces.connections.JBDatos;
import pss.core.tools.JDateTools;
import pss.core.tools.JOSCommand;
import pss.core.tools.JTools;
import pss.core.tools.PssLogger;
import pss.core.tools.WinServiceTools;

public class TelegramBotManager {

	private static String TELEGRAM_BOT_DEV = "xxxx";

	public TelegramBotManager() throws Exception {
	}

	public TelegramBot bot;
	protected String idMail = "1";
	protected String mailSuffix = "PUMP-CONTROL.COM";

	public void setBot(TelegramBot val) {
		bot = val;
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
					w.setClassName("pss.common.event2.telegram.TelegramBotManager");
					w.setServiceName("PWG Telegram Bot");
					w.install();
					System.exit(0);
				}


//				if (args[0].equals("1"))
//					processTelegramMessages();
//				else
					processTelegramDevMessages();

			} finally {

			}

		} catch (Exception eee) {
			PssLogger.logError(eee);

		}
	}

	public Thread startBotManager() {
		// Transit SSL Transaction Server
		Thread oth1 = new Thread() {
			public void run() {
				try {
					TelegramBotManager mng = new TelegramBotManager();
					mailSuffix = BizPssConfig.getPssConfig().getCachedStrictValue("GENERAL", "TELEGRAM_MAIL_SUFFIX", mailSuffix);
					bot = new TelegramBot(BizPssConfig.getPssConfig().getCachedStrictValue("GENERAL", "TELEGRAM_BOT_ID", "xxx"));
					mng.processTelegramDevMessages();
				} catch (Exception eee) {

				}
			}
		};
		oth1.start();
		return oth1;
	}

	public Thread startDevBotManager() {
		// Transit SSL Transaction Server
		Thread oth1 = new Thread() {
			public void run() {
				try {
					TelegramBotManager mng = new TelegramBotManager();
					mailSuffix = BizPssConfig.getPssConfig().getCachedStrictValue("GENERAL", "TELEGRAM_MAIL_SUFFIX", mailSuffix);
					bot = new TelegramBot(BizPssConfig.getPssConfig().getCachedStrictValue("GENERAL", "TELEGRAM_BOT_DEV_ID", TELEGRAM_BOT_DEV));
					mng.processDevMessages();
				} catch (Exception eee) {

				}
			}
		};
		oth1.start();
		return oth1;
	}

	public static void processTelegramMessages() throws Exception {
		System.out.println("Telegrams messages");
		TelegramBotManager mng = new TelegramBotManager();
		mng.setDefaultConfigFromIni();
		mng.setBot(new TelegramBot(BizPssConfig.getPssConfig().getCachedStrictValue("GENERAL", "TELEGRAM_BOT_ID", "xxxx")));
		mng.processBotMessages();
	}

	protected void setDefaultConfigFromIni( ) throws Exception {
		mailSuffix = BizPssConfig.getPssConfig().getCachedStrictValue("GENERAL", "TELEGRAM_MAIL_SUFFIX", mailSuffix);
		idMail = BizPssConfig.getPssConfig().getCachedStrictValue("GENERAL", "TELEGRAM_MAIL_ID", idMail);
	}

	public static void processTelegramDevMessages() throws Exception {
		System.out.println("Telegrams Dev messages");
		processTelegramDevMessages(null, null, null);
	}

	public static void processTelegramDevMessages(String token, String idmail, String mailsuffix) throws Exception {
		TelegramBotManager mng = new TelegramBotManager();
		if (token != null)
			mng.setBot(new TelegramBot(token));
		else
			mng.setBot(new TelegramBot(BizPssConfig.getPssConfig().getCachedStrictValue("GENERAL", "TELEGRAM_BOT_DEV_ID", TELEGRAM_BOT_DEV)));
		if (idmail != null)
			mng.idMail = idmail;
		if (mailsuffix != null)
			mng.mailSuffix = mailsuffix;
		mng.setDefaultConfigFromIni();
		mng.processDevMessages();
	}

	public void sendTelegramMessage(String chatId, String msg) throws Exception {
		bot = new TelegramBot(BizPssConfig.getPssConfig().getCachedStrictValue("GENERAL", "TELEGRAM_BOT_ID", "xxxx"));
		bot.execute(new SendMessage(chatId, msg));
	}

	boolean first = true;

	private void processDevMessages() throws Exception, InterruptedException {

		JAplicacion.openSession();
		JAplicacion.GetApp().openApp("telegram.dev.bot", JAplicacion.AppTipoThread(), true);

		devMode = true;

		bot.setUpdatesListener(updates -> {
			// ... process updates
			// return id of last processed update or confirm them all
			try {
				JAplicacion.openSession();
				JAplicacion.GetApp().openApp("telegram.dev.updates", JAplicacion.AppTipoThread(), true);

				try {
					Iterator<Update> iupd = updates.iterator();
					while (iupd.hasNext()) {
						Update upd = iupd.next();

						Message mmsg = upd.message();

						String msg = mmsg.text();

						// System.out.println(msg);

						String name = upd.message().chat().firstName();
						String last = upd.message().chat().lastName();

						long chatId = upd.message().chat().id();

						PssLogger.logDebug("Mensaje recibido de " + name + " " + last + ", ChatId = " + chatId + ", Mensaje = " + msg);

						JBDatos.GetBases().beginTransaction();
						processChat(chatId, msg, name + " " + last);
						JBDatos.GetBases().commit();

					}

				} finally {
					JAplicacion.GetApp().closeApp();
					JAplicacion.closeSession();
				}
			} catch (Exception ee) {
				PssLogger.logError(ee);
			}

			return UpdatesListener.CONFIRMED_UPDATES_ALL;
		});

		while (Thread.currentThread().isInterrupted() == false) {
			PssLogger.logDebug("Esperando mensajes ...");
			Thread.sleep(60000);
			if (bot == null)
				break;
		}

	}

	protected void processBotMessages() throws Exception, InterruptedException {

		JAplicacion.openSession();
		JAplicacion.GetApp().openApp("telegram.bot", JAplicacion.AppTipoThread(), true);

		bot.setUpdatesListener(updates -> {
			// ... process updates
			// return id of last processed update or confirm them all
			try {
				JAplicacion.openSession();
				JAplicacion.GetApp().openApp("telegram.updates", JAplicacion.AppTipoThread(), true);

				try {
					Iterator<Update> iupd = updates.iterator();
					while (iupd.hasNext()) {
						Update upd = iupd.next();

						Message mmsg = upd.message();

						String msg = mmsg.text();

						// System.out.println(msg);

						String name = upd.message().chat().firstName();
						String last = upd.message().chat().lastName();
						if (name == null)
							name = "";
						if (last == null)
							last = "";

						long chatId = upd.message().chat().id();

						PssLogger.logDebug("Mensaje recibido de " + name + " " + last + ", ChatId = " + chatId + ", Mensaje = " + msg);

						JBDatos.GetBases().beginTransaction();
						processChat(chatId, msg, (name + " " + last).trim());
						JBDatos.GetBases().commit();

					}

				} finally {
					JAplicacion.GetApp().closeApp();
					JAplicacion.closeSession();
				}
			} catch (Exception ee) {
				PssLogger.logError(ee);
			}

			return UpdatesListener.CONFIRMED_UPDATES_ALL;
		});

		while (Thread.currentThread().isInterrupted() == false) {
			CleanAllPendingRegistrations();
			PssLogger.logDebug("Esperando mensajes ...");
			Thread.sleep(60000);
			if (bot == null)
				break;
		}

	}

	private void processReset(long chatId, String msg, BizTelegramUserChannel currUser) throws Exception {

		String cmd = msg.substring(CMD_RESET.length()).trim().toLowerCase();

		checkResetUser(chatId, currUser);

		String line = "";

		line = "service " + cmd + " restart";

		JOSCommand os = new JOSCommand(line);
		os.executeWaiting();

		sendMessage(chatId, "Reset " + msg + " terminado.");

	}

	private void processGetSuper(long chatId, String msg, BizTelegramUserChannel currUser) throws Exception {

		String user = msg.substring(CMD_GETSUPER.length()).trim().toLowerCase();

		checkAdminUser(chatId, currUser);

		BizTemporalPassword pwd = new BizTemporalPassword();
		pwd.dontThrowException(true);
		pwd.addFilter("mail", currUser.getUserid());
		if (pwd.read()) {
			Date date = new Date();
			if (date.before(pwd.getEndDate())) {
				sendMessage(chatId, JTools.PasswordToString(pwd.getPassword()));
				return;
			}
			pwd.delete();
		}

		long pin = 0;

		while (true) {
			pin = (long) (Math.random() * ((9999999999L - 1000000000) + 1)) + 1000000000;
			BizTemporalPassword test = new BizTemporalPassword();
			test.dontThrowException(true);
			if (test.read(JTools.StringToPassword(pin + "")) == false)
				break;
		}

		pwd = new BizTemporalPassword();
		pwd.setPassword(JTools.StringToPassword(pin + ""));
		pwd.setMail(currUser.getUserid());
		pwd.setStartDate(new Date());
		pwd.setEndDate(JDateTools.addHours(new Date(), 4));
		pwd.insert();

		sendMessage(chatId, "" + pin);

	}

	private void checkAdminUser(long chatId, BizTelegramUserChannel currUser) throws Exception {
		if (!currUser.isAdminUser()) {
		  throw new TelegramException("Usuario invalido para la operacion");
		}
	}
	
	private void checkResetUser(long chatId, BizTelegramUserChannel currUser) throws Exception {
		if (!currUser.isResetUser()) {
		  throw new TelegramException("Usuario invalido para la operacion");
		}
	}

	private void processGetLog(long chatId, String msg) throws Exception {

		String log = msg.substring(CMD_GETLOG.length()).trim();

		if (log.equalsIgnoreCase(""))
			sendMessage(chatId, "Debe seleccionar un archivo.");

		File file = new File(JPath.PssPathLog() + "/" + log);
		if (file.exists() == false) {
			sendMessage(chatId, "No existe el archivo : " + log);
			return;
		}
		SendDocument doc = new SendDocument(chatId, file);
		bot.execute(doc);

	}

	private void CleanAllPendingRegistrations() throws Exception {
		// TODO Auto-generated method stub
		long count = 0;
		BizTelegramUserChannel tc = new BizTelegramUserChannel();
		tc.addFilter("date_registered", JDateTools.DateToString(JDateTools.SubMinutes(new Date(), 10), "dd-MM-yyyy HH:mm:ss"), "<");
		tc.addFixedFilter(" userid not like '%" + mailSuffix + "' ");
		tc.addFilter("confirmed", false);
		count = tc.selectCount();
		if (count > 0) {
			PssLogger.logDebug("Borrando registraciones sin confirmar ...");
			JBDatos.GetBases().beginTransaction();
			tc = new BizTelegramUserChannel();
			tc.addFilter("date_registered", JDateTools.DateToString(JDateTools.SubMinutes(new Date(), 10), "dd-MM-yyyy HH:mm:ss"), "<");
			tc.addFilter("confirmed", false);
			tc.deleteMultiple();
			PssLogger.logDebug("Registraciones sin confirmar borradas: " + count);
			JBDatos.GetBases().commit();
		}

	}

	public static String CMD_REGISTER = "/REGISTER";
	public static String CMD_CONFIRM = "/CONFIRM";
	public static String CMD_UNREGISTER = "/UNREGISTER";
	public static String CMD_GETLOG = "/GETLOG";
	public static String CMD_GETSUPER = "/GETSUPER";
	public static String CMD_RESET = "/RESET";

	private void processChat(long chatId, String msg, String descrip) {
		try {
			if (msg == null)
				return;

			msg = msg.trim();

			cleanIncompleteRegisters(chatId);

			// Registro el chat
			if (msg.toUpperCase().startsWith(CMD_REGISTER)) {
				processRegister(chatId, msg.toUpperCase(), descrip);
				return;
			}
			if (msg.toUpperCase().startsWith(CMD_UNREGISTER)) {
				processUnRegister(chatId, msg.toUpperCase());
				return;
			}

			// Confirmo registracion
			if (msg.toUpperCase().startsWith(CMD_CONFIRM)) {
				processConfirmRegister(chatId, msg.toUpperCase());
				return;
			}

			BizTelegramUserChannel user = isRegistered(chatId);
			if (user == null) {
				return;
			}

			if (devMode) {
				if (msg.toUpperCase().startsWith(CMD_GETLOG)) {
					processGetLog(chatId, msg);
					return;
				}

				if (msg.toUpperCase().startsWith(CMD_GETSUPER)) {
					processGetSuper(chatId, msg, user);
					return;
				}
				if (msg.toUpperCase().startsWith(CMD_RESET)) {
					processReset(chatId, msg.toUpperCase(), user);
					return;
				}
			}

			if (processCMD(chatId, msg) == false) {
				if (devMode) {
					sendMessage(chatId,
							"Comandos v�lidos:\n\n" + "/register 'mail' - Registra el mail para soporte por telegram\n" + "/confirm 'pin recibido por mail' - Confirma la registraci�n del mail\n"
									+ "/unregister 'mail' - Desregistra el mail\n" + "/getlog 'nombre del log a bajar' - Permite bajar un log del headoffice\n"
									+ "/getsuper - Clave d�namica para el headoffice\n"
									+ "/reset 'servicio' - Reinicia un servicio\n");
				} else
					sendMessage(chatId, "Comandos v�lidos:\n\n" + "/register 'usuario headoffice' - Registra un usuario del headoffice para recibir alertas y mensajes por telegram\n"
							+ "/confirm 'usuario headoffice' - Confirma la registraci�n de un usuario del headoffice\n" + "/unregister 'usuario headoffice' - Desregistra un usuario del headoffice\n");
			}

		} catch (Exception eee) {
			if (eee instanceof com.google.gson.JsonSyntaxException) {
				try {
					JBDatos.GetBases().commit();
					JBDatos.GetBases().beginTransaction();
				} catch (Exception e2) {
					
				}
				return;
			}
			if (eee instanceof TelegramException) {
			
				sendMessage(chatId, eee.getMessage());
			} else
				PssLogger.logError(eee);
			try {
				JBDatos.GetBases().rollback();
				JBDatos.GetBases().beginTransaction();
			} catch (Exception e2) {
				PssLogger.logError(e2, "DB Error");
			}

		}
		// TODO Auto-generated method stub

	}

	private void cleanIncompleteRegisters(long chatId) throws Exception {

		BizTelegramUserChannel tc = new BizTelegramUserChannel();
		tc.dontThrowException(true);
		tc.addFilter("channel_id", chatId);
		tc.addFilter("confirmed", false);
		tc.addFixedFilter(" userid not like '%" + mailSuffix + "' ");
		tc.addFilter("date_registered", JDateTools.DateToString(JDateTools.SubMinutes(new Date(), 10), "dd-MM-yyyy HH:mm:ss"), "<");
		if (tc.read()) {
			tc.delete();
		}

	}

	private void processUnRegister(long chatId, String msg) throws Exception {
		// TODO Auto-generated method stub
		String user = msg.substring(CMD_UNREGISTER.length()).trim().toLowerCase();

		if (devMode==false)
			user = user.toUpperCase();
		
		BizTelegramUserChannel tc = new BizTelegramUserChannel();
		tc.dontThrowException(true);
		tc.addFilter("userid", user);
		tc.addFilter("channel_id", chatId);
		if (tc.read()) {
			tc.delete();
			sendMessage(chatId, "Usuario " + user + " desregistrado.");
			return;
		}

		sendMessage(chatId, "Usuario o Chat invalido.");
	}

	private void sendMessage(long chatId, String msg) {
		bot.execute(new SendMessage(chatId, msg));
	}

	private void processConfirmRegister(long chatId, String msg) throws Exception {

		String user = msg.substring(CMD_CONFIRM.length()).trim().toLowerCase();

		BizTelegramUserChannel tc = new BizTelegramUserChannel();
		tc.dontThrowException(true);
		if (devMode)
			tc.addFilter("pin", user);
		else {
			user= user.toUpperCase();
			tc.addFilter("userid", user);
		}
		tc.addFilter("channel_id", chatId);
		tc.read();

		if (tc.isRegistered()) {
			sendMessage(chatId, "Usuario " + user + " ya registrado.");
			return;
		}
		tc.setRegistered(true);
		if (devMode)
			tc.setConfirmed(true);
		tc.update();

		if (devMode) {
			sendMessage(chatId, "El usuario " + tc.getUserid() + " confirmo su registraci�n.");
		} else
			sendMessage(chatId, "El usuario " + user
					+ " confirmo su registraci�n. Para terminar el proceso debe loguearse al headoffice dentro de los pr�ximos 10 minutos y confirmar la vinculaci�n de su usuario con Telegram.");
	}

	private boolean devMode = false;

	private void processRegister(long chatId, String msg, String descrip) throws Exception {

		String user = msg.substring(CMD_REGISTER.length()).trim().toLowerCase();
		BizUsuario usr = null;
		if (devMode == false) {
			usr = new BizUsuario();
			user = user.toUpperCase();
			usr.dontThrowException(true);
			if (usr.Read(user) == false) 
				throw new TelegramException( "No es un usuario valido del headoffice");
		} else {
			if (user.toUpperCase().indexOf(mailSuffix.toUpperCase()) <= 0) 
				throw new TelegramException( user + " no es un mail valido de " + mailSuffix.toLowerCase() );
		}

		BizTelegramUserChannel tc = new BizTelegramUserChannel();
		tc.dontThrowException(true);
		tc.addFilter("userid", user);
		if (tc.read()) {
			if (tc.isRegistered()) {
				sendMessage(chatId, "Usuario " + user + " ya registrado.");
				return;
			}
			tc.delete();
		}

		String confMsg = "Confirme la registraci�n con el comando: /confirm 'usuario headoffice'";

		tc.setChannelId(chatId + "");
		tc.setUserid(user);
		long pin = (long) (Math.random() * ((999999 - 100000) + 1)) + 100000;
		if (devMode) {
			sendMail(user, pin);
			confMsg = "Confirme la registraci�n con el comando: /confirm 'pin recibido por mail'";
			tc.setCompany("DEFAULT");
		} else {
			tc.setCompany(usr.getCompany());
		}
		tc.setRegistered(false);
		tc.setConfirmed(false);
		tc.setDateRegistered(new Date());
		tc.setChannelDescription(descrip);
		tc.setPin((long) pin);
		tc.insert();

		sendMessage(chatId, confMsg);

	}

	private void sendMail(String mail, long pin) {
		try {
			BizMailSender ms = new BizMailSender();
			ms.dontThrowException(true);
			int id = Integer.parseInt(idMail);
			if (ms.read((int) id)) {
				ms.clearMailTo();
				// ms.clearMailBCC();

				// ms.setMailTo("diego.guerreiro@ypf.com");
				// ms.setMailTo("pablo.meloni@ypf.com");
				// ms.setMailTo("maria.alvarez@ypf.com");

				ms.setMailTo(mail);
				// ms.setMailBCC(cfg.getMailBcc());

				ms.send("Telegram Registraci�n", "Su pin de registraci�n es : " + pin);
			}
		} catch (Exception eee) {

		}

	}

	private boolean processCMD(long chatId, String msg) {

		return false;

	}

	private BizTelegramUserChannel isRegistered(long chatId) throws Exception {
		BizTelegramUserChannel tc = new BizTelegramUserChannel();
		tc.dontThrowException(true);
		tc.addFilter("channel_id", chatId);
		if (tc.read()) {
			return tc;
		}
		String msg = "Ud no se encuentra registrado. Comando registraci�n: /register 'usuario headoffice'";
		if (devMode)
			msg = "Ud no se encuentra registrado. Comando registraci�n: /register 'mail'";
			
		bot.execute(new SendMessage(chatId, msg));
		return null;
	}

}// end class
