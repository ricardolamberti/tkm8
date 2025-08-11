package pss.tourism.telegram;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;

import pss.bsp.bspBusiness.BizBSPCompany;
import pss.bsp.consola.config.BizBSPConfig;
import pss.bsp.gds.BizInterfazNew;
import pss.common.regions.company.BizCompany;
import pss.common.security.mail.BizUsrMailSender;
import pss.core.JAplicacion;
import pss.core.data.interfaces.connections.JBDatos;
import pss.core.services.records.JRecords;
import pss.core.tools.JDateTools;
import pss.core.tools.PssLogger;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JMap;
import pss.tourism.interfaceGDS.log.BizAviso;
import pss.tourism.interfaceGDS.log.BizInterfaceLog;
import pss.tourism.pnr.BizPNRTicket;

public class Manager {
	public static final String TOKEN = "356653274:AAHb4WLaZRJJcp0RGPw-5a1lQmV0Yb-LwjM";

	//
	public static void main(String[] args) {
		Manager.process(null);
	}

	public static void process(String args) {
		try {
			// String bip = "/Users/sgalli/emerg.wav";

			// bot.as
			// bot.sendMessage("text");
			String idsender = null;

			if (args!=null) {
				idsender = args;
			}

			String curday = JDateTools.CurrentDate();
			boolean avisado = false;

			// URL url = new URL("http://pscode.org/media/leftright.wav");
			while (Thread.currentThread().isInterrupted() == false) {

				JAplicacion.openSession();
				JAplicacion.GetApp().openApp("telegramBot", "process", false);
				long time = Long.parseLong(JDateTools.CurrentTime().replaceAll(
						":", ""));

				if (time < 70000 || time > 230000) {
					JAplicacion.GetApp().closeApp();
					JAplicacion.closeSession();

					Thread.sleep(60000);
					continue;
				}
				Calendar startDate = Calendar.getInstance();
				int dayofweek = startDate.get(Calendar.DAY_OF_WEEK);

				if (dayofweek == Calendar.SUNDAY
						|| dayofweek == Calendar.SATURDAY) {
					Thread.sleep(60000);
					continue;
				}
				
				JAplicacion.GetApp().closeApp();
				JAplicacion.closeSession();

				JAplicacion.openSession();
				JAplicacion.GetApp().openApp("telegramBot", "process", true);
				JBDatos.getDefaultDatabase().beginTransaction();

				JRecords<BizInterfaceLog> logs = new JRecords<BizInterfaceLog>(
						BizInterfaceLog.class);
				logs.addOrderBy("company");

				JMap<String, BizInterfaceLog> mOffline = JCollectionFactory
						.createMap();

				logs.toStatic();
				while (logs.nextRecord()) {

					BizInterfaceLog log = logs.getRecord();

					if (log.getCompany().equalsIgnoreCase("test"))
						continue;
					BizBSPCompany bspCompany = BizBSPCompany.getObjBSPCompany(log.getCompany());
					if (bspCompany==null) continue;
					
					if (bspCompany.getObjExtraData()!=null) {
						if (bspCompany.getObjExtraData().getInactiva()) continue; // agencia desactivada
						if (bspCompany.getObjExtraData().getVersion()==4) continue; // agencia de prueba
					}
					log.setHasTickets(true);
					if (JDateTools.addDays(log.getLastEcho(), (long) 2).before(
							new Date())) {
						if (dayofweek == Calendar.MONDAY) {
							if (JDateTools.addDays(log.getLastEcho(), (long) 4)
									.before(new Date()) == false)
								continue;
						}
						mOffline.addElement(log.getCompany(), log);
					}

				}

				JRecords<BizCompany> cpys = new JRecords<BizCompany>(
						BizCompany.class);
				cpys.addFixedFilter("parent_company<>'DEFAULT' and parent_company is not null" );
				cpys.toStatic();
				while (cpys.nextRecord()) {

					BizCompany cpy = cpys.getRecord();
					
					if (cpy.getCompany().equalsIgnoreCase("test"))
						continue;
					if (cpy.getCompany().equalsIgnoreCase("training"))
						continue;
					
					System.out.println(cpy.getCompany());
					
					BizBSPCompany bspCompany = BizBSPCompany.getObjBSPCompany(cpy.getCompany());
					if (bspCompany==null) continue;
					
					if (bspCompany.getObjExtraData()!=null) {
						if (bspCompany.getObjExtraData().getInactiva()) continue; // agencia desactivada
						if (bspCompany.getObjExtraData().getVersion()==4) continue; // agencia de prueba
					}
					
					
					BizPNRTicket tkt = new BizPNRTicket();
					tkt.addFilter("company", cpy.getCompany());
					tkt.addFilter("fecha_proc",  JDateTools.SubDays(new Date(), (long) 20), ">=");
					tkt.dontThrowException(true);
					//long count= tkt.selectCount();
					Date lastDate= tkt.SelectMaxDate("fecha_proc");
			
					//Date lastDate = tkt.getLastPnr();
					if (lastDate != null) {
						Date futuredate = JDateTools.addDays(lastDate, (long) 5);
						Date currdate = new Date();
						if ( futuredate.before(currdate) ) {
							BizInterfaceLog log = new BizInterfaceLog();
							log.setCompany(cpy.getCompany());
							log.setHasTickets(false);
							log.setLastEcho(lastDate);
							mOffline.addElement(log.getCompany(), log);
						}
					}

				}

				if (mOffline.isEmpty() == false ) {

					
					JIterator<String> it = mOffline.getKeyIterator();
					while (it.hasMoreElements()) {
						String key = it.nextElement();

						String chatid = "-1001051592879";
						
						if (avisado(key)) 
							continue;
						
						// String chatid = "-204874842";
						String message = "Agencia: " + key ;
						
						if (mOffline.getElement(key).hasTickets()==false) {
							message+=" sin recibir boletos desde el ";
						} else {
							message += " con ID: "
									+ mOffline.getElement(key).getId();
							message += " en servidor: "
									+ mOffline.getElement(key).getLastServer();
							message +=" sin conexion desde el ";
						}
						message += 	mOffline.getElement(key).getLastEcho();
						
						try {
							URL msg = new URL("https://api.telegram.org/bot"
									+ TOKEN + "/sendMessage?chat_id=" + chatid
									+ "&text=\"" + message + "\"");
							BufferedReader in = new BufferedReader(
									new InputStreamReader(msg.openStream()));
							in.close();

							sendMail(idsender, key, mOffline.getElement(key)
									.getId(), mOffline.getElement(key)
									.getLastServer(), JDateTools.dateToStr(
									mOffline.getElement(key).getLastEcho(),
									"dd-MM-yyyy HH:mm:ss"), mOffline.getElement(key).hasTickets() );
						} catch (Exception eee) {
							PssLogger.logError(eee, "En URL ");
						}

						PssLogger.logDebug(" Agencia: " + key);
					}

					// while ((inputLine = in.readLine()) != null) {
					// System.out.println(inputLine);
					// }

					// Clip clip = AudioSystem.getClip();
					// // getAudioInputStream() also accepts a File or
					// InputStream
					// AudioInputStream ais = AudioSystem
					// .getAudioInputStream(new File(bip));
					// clip.open(ais);
					// clip.loop(Clip.LOOP_CONTINUOUSLY);
					// SwingUtilities.invokeLater(new Runnable() {
					// public void run() {
					// // A GUI element to prevent the Clip's daemon Thread
					// // from terminating at the end of the main()
					// JOptionPane.showMessageDialog(null,
					// "Close to exit!");
					// }
					// });
				} else {
					PssLogger.logDebug(" Nada por aqui ...");
				}

				JAplicacion.GetApp().closeApp();
				JAplicacion.closeSession();

				Thread.sleep(60000);

			}

			//
			// String bip = "/Users/sgalli/alarm.mp3";
			// new javafx.embed.swing.JFXPanel();
			// String uriString = new File(bip).toURI().toString();
			// new MediaPlayer(new Media(uriString)).play();

		} catch (Exception eee) {
			PssLogger.logError(eee.getMessage());
		}

	}

	private static boolean avisado(String company) throws Exception {
		BizAviso avi = new BizAviso();
		avi.dontThrowException(true);
		if ( avi.read(company)==false ) {
			avi.setCompany(company);
			avi.setLastAviso(new Date());
			avi.insert();
		}
		
		if (JDateTools.DateToString( avi.getLastAviso(), JDateTools.DEFAULT_DATE_FORMAT).equals(JDateTools.CurrentDate())) 
			return true;
		
		avi.setLastAviso(new Date());
		avi.update();
		JBDatos.GetBases().commit();
		JBDatos.GetBases().beginTransaction();

		return false;
	}

	private static void sendMail(String idsender, String id, String idgds,
			String lastServer, String lastEcho, boolean hasTickets) {
		try {
			BizUsrMailSender ms = new BizUsrMailSender();
			ms.dontThrowException(true);
			if (ms.read(Integer.parseInt(idsender))) {
				BizBSPConfig cfg = new BizBSPConfig();
				cfg.dontThrowException(true);
				if (cfg.read(id, "*")) {
					BizCompany cpy = new BizCompany();
					cpy.Read(id);

					String mail = "<br></br>"
							+ "<br></br>"
							+ "*** Este es un mail autom�tico, NO RESPONDER ***"
							+ "<br></br>" + "<br></br>";
					mail += "";

					mail += "<table width=100% style=\"border: 1px solid black;border-collapse: collapse;\" >";
					mail += "<tr><td style=\"padding: 5px;\"><b>Agencia</b></td>";
					mail += "<td style=\"padding: 5px;\">"
							+ cpy.getDescription() + "</td>";

					mail += "<table><tr></tr></table>";

					mail += "</table>";

					String body = "Instalaci�n para ID: <b>"
							+ idgds.toUpperCase()
							+ "</b> y desde su PC/servidor: <b>" + lastServer
							+ "</b>";
					
					if (hasTickets==false)
						body = "No se estan recibiendo boletos de su Agencia";

					mail += "<table  width=100% style=\"border: 1px solid black;border-collapse: collapse;\">";
					mail += " <tr style=\"border: 1px solid black;border-collapse: collapse;\"><td style=\"border: 1px solid black;border-collapse: collapse;padding: 5px;\"><b>Evento</b></td><td style=\"padding: 5px;\"> ";
					if (hasTickets)		
						mail += "Alerta de Desconexión" + "</td></tr>";
					else
						mail += "Alerta de No Recepci�n de Boletos" + "</td></tr>";
						
					mail += "<tr style=\"border: 1px solid black;border-collapse: collapse;\"><td style=\"border: 1px solid black;border-collapse: collapse;padding: 5px;\"><b>Fecha Desde</b></td><td style=\"padding: 5px;\"> "
							+ lastEcho + "</td></tr>";
				
					mail += "<tr style=\"border: 1px solid black;border-collapse: collapse;\"><td style=\"border: 1px solid black;border-collapse: collapse;padding: 5px;\"><b>Descripción</b></td><td style=\"padding: 5px;\"> "
							+ body + "</td></tr>";
					
					mail += "</table>" + "<br></br>" + "<br></br>";

					mail += "Por favor verificar que: "
							+ "<tr></tr>"
							+ "- La computadora que recibe los archivos de interfaz tenga Internet"
							+ "<tr></tr>"
							+ "- Que no se hayan realizado cambios en esa computadora "
							+ "<tr></tr>"
							+ "-  Su BackOffice está recibiendo los archivos normalmente?"
							+ "<tr></tr>"
							+ "-  Que no hayan cambiado el destino de los PNRs en su impresora del GDS"
							+ "<tr></tr>"
							+ " "
							+ "<tr></tr>"
							+ "<br></br>"
							+ "- Si nada ha sufrido cambios, por favor reiniciar la computadora y no apagarla durante 24 horas."
							+ "<tr></tr>" + " " + "<tr></tr>" + "<tr></tr>"
							+ "- Si tiene servicio de soporte tecnico pedir el reincio el servicio de windows \"pss ftp client\". Si el problema subsiste, seguir� recibiendo estos mails"
							+ "<tr></tr>" + " " + "<tr></tr>" + "<tr></tr>"
							+ "<br></br>" + "<br></br>"
							+ "<b>Ticketmining Team</b>" + "<br></br>"
							+ "<br></br>";

					// String body = "No se reciben Tickets desde el "
					// + lastEcho + " para el GDS: " + idgds
					// + " desde el servidor: " + lastServer;
					String subject = "ALERTA TICKETMINING: No hay conexión con su Agencia";
					if (hasTickets==false) {
						subject = "ALERTA TICKETMINING: No se reciben Boletos de su Agencia";
					}
					
					ms.send(cfg.getEmail(),
							subject,
							mail, null);
				}

			}

		} catch (Exception eee) {
			PssLogger.logDebug(eee);
		}

	}

}
