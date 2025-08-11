package pss.common.event.mail;

import java.util.Date;

import javax.mail.Session;
import javax.mail.Store;

import pss.common.regions.company.BizCompany;
import pss.core.JAplicacion;
import pss.core.data.interfaces.connections.JBDatos;
import pss.core.services.JExec;
import pss.core.services.fields.JBoolean;
import pss.core.services.fields.JDateTime;
import pss.core.services.fields.JInteger;
import pss.core.services.fields.JPassword;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.tools.JExcepcion;
import pss.core.tools.PssLogger;

public class BizMailCasilla extends JRecord {

	public static final String STATUS_OK = "K";
	public static final String STATUS_SUSPENDIDO = "S";

	public static final String FILTER_ALL = "A";
	public static final String FILTER_ONLYRESP = "R";
	//
	// public static final String MAIL_SEPARADOR = ";";

	// private String smtpServer = null;
	// private String mailFormat = "text/plain";

	private JInteger pID = new JInteger();
	private JString pCompany = new JString();
	private JBoolean pActivo = new JBoolean() {
		public void preset() throws Exception {
			setValue(isOK());
		};
	};
	// private JString pDescripcion = new JString();
	// private JString pMailTo = new JString();
	private JString pInternalUser = new JString();
	// private JString pMailSubject = new JString();
	// private JString pMailBody = new JString();
	private JString pMailFrom = new JString();
	private JInteger pIDMailConf = new JInteger();
	private JPassword pMailPassword = new JPassword();
	// private JString pUsuarioAuth = new JString();
	private JString pEstado = new JString();
	private JString pMensaje = new JString();
	private JInteger pIntentos = new JInteger();
	private JDateTime pLastMail = new JDateTime();
	private JString pNombre = new JString();
	private JString pFilterEntrantes = new JString();

	// private JDate pFechaEnvio = new JDate();
	// private JHour pHoraEnvio = new JHour();
	// private JString pEstado = new JString();
	public void setFilterEntrantes(String zVal) throws Exception {
		pFilterEntrantes.setValue(zVal);
	}
	public void setNombre(String zVal) throws Exception {
		pNombre.setValue(zVal);
	}

	public void setCompany(String zVal) throws Exception {
		pCompany.setValue(zVal);
	}

	public String getCompany() throws Exception {
		return pCompany.getValue();
	}

	public void setMailPassword(String zVal) throws Exception {
		pMailPassword.setValue(zVal);
	}

	public String getMailPassword() throws Exception {
		return pMailPassword.getValue();
	}

	public Date getLastMail() throws Exception {
		return pLastMail.getValue();
	}

	public boolean hasLastMail() throws Exception {
		return pLastMail.isNotNull();
	}

	public void setLastMail(Date v) throws Exception {
		this.pLastMail.setValue(v);
	}

	// public String getUsuarioAuth() throws Exception {
	// return pUsuarioAuth.getValue();
	// }

	public void setInternalUser(String zVal) throws Exception {
		pInternalUser.setValue(zVal);
	}

	public String getInternalUser() throws Exception {
		return pInternalUser.getValue();
	}

	@Override
	public void createProperties() throws Exception {
		addItem("ID", pID);
		addItem("company", pCompany);
		// addItem("descripcion", pDescripcion);
		addItem("MAIL_FROM", pMailFrom);
		addItem("MAIL_PASSWORD", pMailPassword);
		addItem("id_mail_conf", pIDMailConf);
		addItem("internal_user", pInternalUser);
		addItem("estado", pEstado);
		addItem("mensaje", pMensaje);
		addItem("intentos", pIntentos);
		addItem("last_mail", pLastMail);
		addItem("nombre", pNombre);
		addItem("activo", pActivo);
		addItem("filter_entrantes", pFilterEntrantes);
		// addItem("MAIL_TO", pMailTo);
		// addItem("MAIL_SUBJECT", pMailSubject);
		// addItem("MAIL_BODY", pMailBody);
		// addItem("FECHA_ENVIO", pFechaEnvio);
		// addItem("ESTADO", pEstado);
		// addItem("HORA_ENVIO", pHoraEnvio);
	}

	@Override
	public void createFixedProperties() throws Exception {
		addFixedItem(KEY, "ID", "ID", false, false, 18, 0);
		addFixedItem(FIELD, "company", "Empresa", true, false, 50);
		addFixedItem(FIELD, "MAIL_FROM", "Remitente", true, false, 50);
		addFixedItem(FIELD, "MAIL_PASSWORD", "MAIL Password ", true, false, 250);
		addFixedItem(FIELD, "ID_MAIL_CONF", "Servidor Mail", true, true, 18);
		addFixedItem(FIELD, "internal_user", "Usuario Interno", true, false, 50);
		addFixedItem(FIELD, "estado", "Estado", true, false, 1);
		addFixedItem(FIELD, "mensaje", "Mensaje", true, false, 1000);
		addFixedItem(FIELD, "intentos", "Intentos", true, false, 5);
		addFixedItem(FIELD, "filter_entrantes", "Filter Entrantes", true, false, 1);
		addFixedItem(FIELD, "last_mail", "Last Mail", true, false, 10);
		addFixedItem(FIELD, "nombre", "Nombre", true, false, 100);
		addFixedItem(VIRTUAL, "activo", "Ok", true, false, 1);
	}

	public int getID() throws Exception {
		return pID.getValue();
	}

	// public String getMailTo() throws Exception {
	// return pMailTo.getValue();
	// }

	public String getMailFrom() throws Exception {
		return pMailFrom.getValue();
	}

	public String getNombre() throws Exception {
		return pNombre.getValue();
	}

	public String findNombreEmail() throws Exception {
		if (this.pNombre.isEmpty())
			return this.getMailFrom();
		return pNombre.getValue() + "<" + this.getMailFrom() + ">";
	}

	// public String getMailBody() throws Exception {
	// return pMailBody.getValue();
	// }

	public int getIdMailConf() throws Exception {
		return pIDMailConf.getValue();
	}

	// public Date getFechaEnvio() throws Exception {
	// return pFechaEnvio.getValue();
	// }
	//
	// public String getEstado() throws Exception {
	// return pEstado.getValue();
	// }

	public void setID(int iValue) throws Exception {
		pID.setValue(iValue);
	}

	public void setIDMailConf(long iValue) throws Exception {
		pIDMailConf.setValue(iValue);
	}

	// public String getMailSubject() throws Exception { return
	// pMailSubject.getValue();}
	// public void setMailSubject(String aMailSubject) {
	// pMailSubject.setValue(aMailSubject);
	// }
	//
	// public void setMailBody(String aMailBody) {
	// pMailBody.setValue(aMailBody);
	// }
	//
	// public void setEstado(String zValue) throws Exception {
	// pEstado.setValue(zValue);
	// }
	//
	// public void setFechaEnvio(Date zValue) throws Exception {
	// pFechaEnvio.setValue(zValue);
	// }
	//
	// public void setHoraEnvio(String zValue) throws Exception {
	// pHoraEnvio.setValue(zValue);
	// }

	@Override
	public String GetTable() throws Exception {
		return "MAIL_CASILLA";
	}

	public boolean read(int id) throws Exception {
		addFilter("id", id);
		// if (read()) {
		// BizMailConf oConf = new BizMailConf();
		// oConf.dontThrowException(true);
		// if (oConf.read(this.pIDMailConf.getValue())) {
		// if (oConf.getMailFormat().equals("") == false)
		// mailFormat = oConf.getMailFormat();
		// }
		// return true;
		// }
		return super.read();
	}

	// public void processInsert() throws Exception {
	// this.InsertarRegistro();
	// }

	public BizMailCasilla() throws Exception {
	}

	// public void setSMTPServer(String aSMTPServer) {
	// smtpServer = aSMTPServer;
	// }
	//
	// public void setMailFormat(String aMailFormat) {
	// mailFormat = aMailFormat;
	// }

	public void setMailFrom(String aMailFrom) {
		pMailFrom.setValue(aMailFrom);
	}

	// public void clearMailTo() { pMailTo.setValue(""); }

	// public void setMailTo(String aMailTo) throws Exception {
	// if (pMailTo.isNull() || pMailTo.getValue().equalsIgnoreCase("")) {
	// pMailTo.setValue(aMailTo);
	// } else {
	// pMailTo.setValue(pMailTo.getValue() + "," + aMailTo);
	// }
	// }

	/*************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************
	 * Enviar un mail
	 * 
	 * @throws Exception
	 ************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************/
	// public void send(String title, String body) throws Exception {
	// this.send(title, body, (String)null);
	// }
	//
	// public void send(String title, String body, String filename) throws
	// Exception {
	// JList<String> list = JCollectionFactory.createList();
	// if (filename!=null) list.addElement(filename);
	// this.send(title, body, list);
	// }
	//
	// public void send(String title, String body, JList<String> filenames)
	// throws Exception {
	// this.send(title, body, filenames, false);
	// }
	//
	// public void send(String title, String body, JList<String> filenames,
	// boolean hasSignature) throws Exception {
	// BizMailConf oConf = new BizMailConf();
	// oConf.dontThrowException(true);
	// if (!oConf.read(pIDMailConf.getValue()))
	// JExcepcion.SendError("No existe servidor smtp");
	//
	// String signature = null;
	// if (hasSignature) {
	// BizMailSignature sig = new BizMailSignature();
	// sig.dontThrowException(true);
	// if (
	// sig.read(BizUsuario.getUsr().getCompany(),BizUsuario.getUsr().GetUsuario())
	// ) {
	// signature = URLDecoder.decode(new
	// String(sig.getSignature().getBytes("ISO-8859-1") ), "ISO-8859-1") ;
	// }
	// }
	//
	// smtpServer = oConf.getSmtpServer();
	//
	// PssLogger.logDebug("mailTitle: " + title);
	// PssLogger.logDebug("mailFrom: " + pMailFrom.getValue());
	// PssLogger.logDebug("mailto: " + pMailTo.getValue());
	// PssLogger.logDebug("attach: " + filenames.toString());
	//
	// Session session = null;
	// if (!oConf.getTransport().equals("esmtp"))
	// session = Session.getInstance(System.getProperties(), null);
	// else
	// session = getSession(oConf);
	//
	// final MimeMessage msg = new MimeMessage(session);
	// msg.setFrom(new InternetAddress(pMailFrom.getValue()));
	//
	// final Address[] recipientAddresses =
	// InternetAddress.parse(pMailTo.getValue());
	// msg.setRecipients(Message.RecipientType.TO, recipientAddresses);
	// msg.setSentDate(new Date());
	// if (title != null)
	// msg.setSubject(title);
	// else
	// msg.setSubject(pMailSubject.getValue());
	// if (body == null) {
	// body = pMailBody.getValue();
	// }
	// if (signature!=null) {
	// body += signature;
	// }
	//
	//// BodyPart messageBodyPart = new MimeBodyPart();
	//// messageBodyPart.setText("Here's the file");
	//// Multipart multipart = new MimeMultipart();
	//// multipart.addBodyPart(messageBodyPart);
	//// messageBodyPart = new MimeBodyPart();
	//// DataSource source = new FileDataSource(filename);
	//// messageBodyPart.setDataHandler(new DataHandler(source));
	//// messageBodyPart.setFileName(filename);
	//// multipart.addBodyPart(messageBodyPart);
	//// message.setContent(multipart);
	//
	// // create and fill the first message part
	// MimeBodyPart mbp1 = new MimeBodyPart();
	//
	// mbp1.setContent(body, "text/html");
	//
	// // create the Multipart and add its parts to it
	// Multipart mp = new MimeMultipart("related");
	// mp.addBodyPart(mbp1);
	//
	// if (filenames != null) {
	// // create the second message part
	// // attach the file to the message
	// JIterator<String> iter = filenames.getIterator();
	// while (iter.hasMoreElements()) {
	// String filename = iter.nextElement();
	// if (filename.equals("")) continue;
	// MimeBodyPart mbp2 = new MimeBodyPart();
	// FileDataSource fds = new FileDataSource(filename);
	// mbp2.setDataHandler(new DataHandler(fds));
	// mbp2.setFileName(fds.getName());
	// mbp2.setContentID(fds.getName());
	// mp.addBodyPart(mbp2);
	// }
	// }
	//
	//
	// // add the Multipart to the message
	// msg.setContent(mp);
	//
	// if (!oConf.getTransport().equals("esmtp")) {
	// final Transport transport = session.getTransport(oConf.getTransport());
	// transport.connect(smtpServer, (int) oConf.getSmtpPort(),
	// pMailFrom.getValue(), pMailPassword.getValue());
	// transport.sendMessage(msg, recipientAddresses);
	// transport.close();
	// }
	// else {
	// Transport.send(msg);
	// }
	// }
	//
	//
	// private Session getSession( BizMailConf conf) throws Exception {
	// Authenticator authenticator = new
	// Authenticator(pUser.getValue(),pMailPassword.getValue());
	//
	// Properties properties = new Properties();
	// properties.setProperty("mail.smtp.submitter",
	// authenticator.getPasswordAuthentication().getUserName());
	// properties.setProperty("mail.smtp.auth", "true");
	//
	// properties.setProperty("mail.smtp.host", conf.getSmtpServer());
	// properties.setProperty("mail.smtp.port", ""+conf.getSmtpPort());
	//
	// return Session.getInstance(properties, authenticator);
	// }
	//
	// private class Authenticator extends javax.mail.Authenticator {
	// private PasswordAuthentication authentication;
	//
	// public Authenticator(String username,String password) {
	// authentication = new PasswordAuthentication(username, password);
	// }
	//
	// protected PasswordAuthentication getPasswordAuthentication() {
	// return authentication;
	// }
	// }

	/*
	 * public class AttachExample {
	 */

	public static void main(String args[]) throws Exception {

		try {
			
			JAplicacion.openSession();
			JAplicacion.GetApp().openApp("mailtest" , JAplicacion.AppService(), true);
			JBDatos.GetBases().beginTransaction();
			
			
			BizMailCasilla c = new BizMailCasilla();
			c.read(2396);
			
			JMailSend s = new JMailSend();
			s.setObjServer(c.getObjMailConf());
			s.setMailFrom(c.getMailFrom());
			s.setMailFromName(c.getNombre());
			s.setMailTo("santiago.galli@gmail.com");
			s.setAuthUser(c.getInternalUser());
			s.setPassword(c.getMailPassword());
			s.setTitle("Test Mail");
			s.setBody("Prueba Envio Mail");
			s.send();

			JBDatos.GetBases().commit();
			
		} catch (Exception ee) {
			System.out.println(ee.getMessage());
		}

	}

	/*************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************
	 * @return Una lista con las direcciones donde se debe enviar el mail
	 * @throws Exception
	 ************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************/
	// private JList<String> getList() throws Exception {
	// JList<String> oList = JCollectionFactory.createList();
	// PssLogger.logDebug("mailto: " + pMailTo.getValue());
	// JStringTokenizer st =
	// JCollectionFactory.createStringTokenizer(pMailTo.getValue(), ';');
	// // int iCantToks = st.countTokens();
	// for (int i = 0; st.hasMoreTokens(); i++) {
	// String param = st.nextToken();
	// PssLogger.logDebug(param);
	// oList.addElement(param);
	// }
	// return oList;
	// }

	// public static void mailProcess() throws Exception {
	// JRecords<BizMailSender> oMails = new
	// JRecords<BizMailSender>(BizMailSender.class);
	// oMails.addFilter("ESTADO", BizMailSender.MAIL_PENDING);
	// oMails.readAll();
	// oMails.firstRecord();
	// while (oMails.nextRecord()) {
	// BizMailSender oMail = oMails.getRecord();
	// oMail.send(null, null);
	// oMail.marcarEnviado();
	// }
	// }

	private BizMailConf mailConf = null;

	public BizMailConf getObjMailConf() throws Exception {
		if (this.mailConf != null)
			return this.mailConf;
		BizMailConf c = new BizMailConf();
		c.read(this.pIDMailConf.getValue());
		return (this.mailConf = c);
	}

	public void validateConstraints() throws Exception {
		if (this.pCompany.isNull()) {
			this.pCompany.setValue(BizCompany.DEFAULT_COMPANY);
		}
	}

	public void processInsert() throws Exception {
		if (pEstado.isNull())
			this.pEstado.setValue(BizMailCasilla.STATUS_OK);
		super.processInsert();
		this.pID.setValue(this.getIdentity("id"));
	}

	public void processEnvioTest() throws Exception {
		JMailSend s = new JMailSend();
		s.setObjServer(this.getObjMailConf());
		s.setMailFromName(this.getNombre());
		s.setMailFrom(this.getMailFrom());
		s.setMailTo(this.getMailFrom());
		s.setAuthUser(this.getInternalUser());
		s.setPassword(this.getMailPassword());
		s.setTitle("[SITI] Test Mail");
		s.setBody("Prueba Envio Mail");
		s.send();
	}

	public void processHabilitar() throws Exception {
		BizMailCasilla c = (BizMailCasilla) this.getPreInstance();
		c.setEstado(BizMailCasilla.STATUS_OK);
		c.setMensaje(null);
		c.update();
		this.setEstado(c.getEstado());
		this.setMensaje(null);
	}

	public void processDesHabilitar() throws Exception {
		BizMailCasilla c = (BizMailCasilla) this.getPreInstance();
		c.setEstado(BizMailCasilla.STATUS_SUSPENDIDO);
		c.setMensaje("Deshabilitación Manual");
		c.update();
		this.setEstado(c.getEstado());
		this.setMensaje(c.getMensaje());
	}
	// private void marcarEnviado() throws Exception {
	// setEstado(BizMailCasilla.MAIL_SENT);
	// setFechaEnvio(new Date());
	// setHoraEnvio(JDateTools.DateToString(new JDate(new Date()).getValue(),
	// "HHmmss"));
	// processUpdate();
	// }

	// public static void main ( String args[] ) throws Exception {
	// JAplicacion.AbrirSesion();
	// JAplicacion.GetApp().SetAppId(JAplicacion.AppSetup());
	// BizLoginTrace oLogin = new BizLoginTrace();
	// oLogin.SetLogin("ADMIN");
	// oLogin.SetPassword("1234");
	// oLogin.LogIn();
	// JBDatos.GetBases().Commit();
	// BizMailSender.mailProcess();
	// }
	public boolean isOK() throws Exception {
		return this.pEstado.getValue().equals(BizMailCasilla.STATUS_OK);
	}

	public void setEstado(String v) throws Exception {
		this.pEstado.setValue(v);
	}

	public void setMensaje(String v) throws Exception {
		this.pMensaje.setValue(v);
	}

	public String getMensaje() throws Exception {
		return this.pMensaje.getValue();
	}

	public String getEstado() throws Exception {
		return this.pEstado.getValue();
	}

	public int getIntentos() throws Exception {
		return this.pIntentos.getValue();
	}

	public void setIntentos(int v) throws Exception {
		this.pIntentos.setValue(v);
	}

	public void execProcessSuspend(boolean force, String msg) throws Exception {
		JExec exec = new JExec(null, null) {
			public void Do() throws Exception {
				processSuspend(force, msg);
			};
		};
		exec.execute();
	}

	public void processSuspend(boolean force, String msg) throws Exception {
		BizMailCasilla c = (BizMailCasilla) this.getPreInstance();
		if (force) {
			c.setEstado(BizMailCasilla.STATUS_SUSPENDIDO);
			c.setMensaje(msg);
		} else {
			c.setIntentos(c.getIntentos() + 1);
			if (c.getIntentos() > 3) {
				c.setEstado(BizMailCasilla.STATUS_SUSPENDIDO);
				c.setMensaje(msg);
			}
		}
		c.update();
		this.setEstado(c.getEstado());
	}

	public boolean isOnlyRspuestas() throws Exception {
		return this.pFilterEntrantes.getValue().equals(BizMailCasilla.FILTER_ONLYRESP);
	}

	public boolean isAll() throws Exception {
		return this.pFilterEntrantes.getValue().equals(BizMailCasilla.FILTER_ALL);
	}
	
//	private String findUser() throws Exception {
//		String user = this.getMailFrom();
////		if (this.getObjMailConf().isEntranteExchange())
////			 return user.substring(0,user.indexOf("@"));
//		return user;
//	}
	

	public Store connectReceive() throws Exception {
		BizMailConf conf = this.getObjMailConf();
		
		final String user = this.findUser();
		final String pass = this.getMailPassword();
	
		Session session = Session.getInstance(conf.makeProperties(user, false), conf.makeAuth(user, pass));
		
		Store store = session.getStore(conf.getEntranteTransport());
		PssLogger.logDebug("server: "+ conf.getPop3Server() + " port: "+conf.getPop3Port() + " casilla: "+ this.getMailFrom());
		store.connect(conf.getPop3Server(), conf.getPop3Port(), user, pass);
		return store;
	}

	public void execSearchAndRecive(JMailRecive recive) throws Exception {
		JExec exec = new JExec(null,null) {
			public void Do() throws Exception {
				searchAndReceive(recive);
			};
		};
		exec.execute();
	} 

	public void searchAndReceive(JMailRecive recive) throws Exception {
		Date lastmail =recive.recive();
		this.setLastMail(lastmail);
		this.setIntentos(0);
		this.update();
	}

	public void testReceive() throws Exception {
		JMailRecive mr = new JMailRecive();
		mr.setObjMailCasilla(this);
		mr.reciveMails();
		JExcepcion.SendError("Recepcion del email OK");
	}


	public String findUser() throws Exception {
		if (this.pInternalUser.hasValue()) 
			return pInternalUser.getValue();
		return this.pMailFrom.getValue();
	}
}
