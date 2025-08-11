package  pss.common.event.mail;

import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import pss.common.regions.company.BizCompany;
import pss.core.services.fields.JDate;
import pss.core.services.fields.JHour;
import pss.core.services.fields.JInteger;
import pss.core.services.fields.JPassword;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.JDateTools;
import pss.core.tools.JExcepcion;
import pss.core.tools.PssLogger;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JList;
import pss.core.tools.collections.JStringTokenizer;

public class BizMailSender extends JRecord {

	public static final String MAIL_SENT = "S";
	public static final String MAIL_PENDING = "P";

	public static final String MAIL_SEPARADOR = ";";

	private String smtpServer = null;
//	private String mailFormat = "text/plain";

	private JInteger pID = new JInteger();
	private JString pMailTo = new JString();
	private JString pUser = new JString();
	private JString pMailSubject = new JString();
	private JString pMailBody = new JString();
	private JString pMailFrom = new JString();
	private JInteger pIDMailConf = new JInteger();
	private JPassword pMailPassword = new JPassword();
	private JDate pFechaEnvio = new JDate();
	private JHour pHoraEnvio = new JHour();
	private JString pEstado = new JString();
	private JString pCompany = new JString();

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

	public void setInternalUser(String zVal) throws Exception {
		pUser.setValue(zVal);
	}

	public String getInternalUser() throws Exception {
		return pUser.getValue();
	}

	@Override
	public void createProperties() throws Exception {
		addItem("ID", pID);
		addItem("company", pCompany);
		addItem("internal_user", pUser);
		addItem("MAIL_TO", pMailTo);
		addItem("MAIL_SUBJECT", pMailSubject);
		addItem("MAIL_BODY", pMailBody);
		addItem("MAIL_FROM", pMailFrom);
		addItem("MAIL_PASSWORD", pMailPassword);
		addItem("ID_MAIL_CONF", pIDMailConf);
		addItem("FECHA_ENVIO", pFechaEnvio);
		addItem("ESTADO", pEstado);
		addItem("HORA_ENVIO", pHoraEnvio);
	}

	@Override
	public void createFixedProperties() throws Exception {
		addFixedItem(KEY, "ID", "ID", false, false, 18, 0);
		addFixedItem(FIELD, "company", "Empresa", true, false, 50);
		addFixedItem(FIELD, "internal_user", "Usuario", true, false, 50);
		addFixedItem(FIELD, "MAIL_TO", "Destinatario", true, false, 4000);
		addFixedItem(FIELD, "MAIL_SUBJECT", "Asunto", true, false, 250);
		addFixedItem(FIELD, "MAIL_BODY", "Mensaje", true, false, 4000);
		addFixedItem(FIELD, "MAIL_FROM", "Remitente", true, true, 50);
		addFixedItem(FIELD, "MAIL_PASSWORD", "MAIL Password ", true, true, 250);
		addFixedItem(FIELD, "ID_MAIL_CONF", "Servidor Mail", true, true, 18);
		addFixedItem(FIELD, "FECHA_ENVIO", "FECHA ENVIO", true, false, 8);
		addFixedItem(FIELD, "HORA_ENVIO", "HORA ENVIO", true, false, 6);
		addFixedItem(FIELD, "ESTADO", "ESTADO", true, false, 1);
	}

	public int getID() throws Exception {
		return pID.getValue();
	}

	public String getMailTo() throws Exception {
		return pMailTo.getValue();
	}

	public String getMailFrom() throws Exception {
		return pMailFrom.getValue();
	}

	public String getMailBody() throws Exception {
		return pMailBody.getValue();
	}

	public int getIdMailConf() throws Exception {
		return pIDMailConf.getValue();
	}

	public Date getFechaEnvio() throws Exception {
		return pFechaEnvio.getValue();
	}

	public String getEstado() throws Exception {
		return pEstado.getValue();
	}

	public void setID(int iValue) throws Exception {
		pID.setValue(iValue);
	}

	public void setIDMailConf(int iValue) throws Exception {
		pIDMailConf.setValue(iValue);
	}

	public String getMailSubject() throws Exception { return pMailSubject.getValue();}
	public void setMailSubject(String aMailSubject) {
		pMailSubject.setValue(aMailSubject);
	}

	public void setMailBody(String aMailBody) {
		pMailBody.setValue(aMailBody);
	}

	public void setEstado(String zValue) throws Exception {
		pEstado.setValue(zValue);
	}

	public void setFechaEnvio(Date zValue) throws Exception {
		pFechaEnvio.setValue(zValue);
	}

	public void setHoraEnvio(String zValue) throws Exception {
		pHoraEnvio.setValue(zValue);
	}

	@Override
	public String GetTable() throws Exception {
		return "MAIL_SENDER";
	}

	public boolean read(int iID) throws Exception {
		addFilter("ID", iID);
		return this.read();
//		) {
//			BizMailConf oConf = new BizMailConf();
//			oConf.dontThrowException(true);
//			if (oConf.read(this.pIDMailConf.getValue())) {
//				if (oConf.getMailFormat().equals("") == false)
//					mailFormat = oConf.getMailFormat();
//			}
//			return true;
//		}
//		return false;
	}

	// public void processInsert() throws Exception {
	// this.InsertarRegistro();
	// }

	public BizMailSender() throws Exception {
	}

	public void setSMTPServer(String aSMTPServer) {
		smtpServer = aSMTPServer;
	}

//	public void setMailFormat(String aMailFormat) {
//		mailFormat = aMailFormat;
//	}

	public void setMailFrom(String aMailFrom) {
		pMailFrom.setValue(aMailFrom);
	}

	public void clearMailTo() { pMailTo.setValue(""); }
	
	public void setMailTo(String aMailTo) throws Exception {
		if (pMailTo.isNull() || pMailTo.getValue().equalsIgnoreCase("")) {
			pMailTo.setValue(aMailTo);
		} else {
			pMailTo.setValue(pMailTo.getValue() + "," + aMailTo);
		}
	}

	/*************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************
	 * Enviar un mail
	 * 
	 * @throws Exception
	 ************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************/
	public void send(String title, String body) throws Exception {
		send(title, body, null);
	}

	public void send(String title, String body, String filename) throws Exception {
//		BizMailConf oConf = new BizMailConf();
//		oConf.dontThrowException(true);
//		if (!oConf.read(pIDMailConf.getValue()))
//			JExcepcion.SendError("No existe servidor smtp");
//		smtpServer = oConf.getSmtpServer();
//
//		PssLogger.logDebug("mailTitle: " + title);
//		PssLogger.logDebug("mailFrom: " + pMailFrom.getValue());
//		PssLogger.logDebug("mailto: " + pMailTo.getValue());
//		PssLogger.logDebug("attach: " + filename);
//
//		final Session session = Session.getInstance(System.getProperties(), null);
//		final MimeMessage msg = new MimeMessage(session);
//		msg.setFrom(new InternetAddress(pMailFrom.getValue()));
//
//		final Address[] recipientAddresses = InternetAddress.parse(pMailTo.getValue());
//		msg.setRecipients(Message.RecipientType.TO, recipientAddresses);
//		msg.setSentDate(new Date());
//		if (title != null)
//			msg.setSubject(title);
//		else
//			msg.setSubject(pMailSubject.getValue());
//		if (body == null) {
//			body = pMailBody.getValue();
//		}
//		
////		   BodyPart messageBodyPart = new MimeBodyPart();
////		   messageBodyPart.setText("Here's the file");
////		   Multipart multipart = new MimeMultipart();
////		   multipart.addBodyPart(messageBodyPart);
////		   messageBodyPart = new MimeBodyPart();
////		   DataSource source = new FileDataSource(filename);
////		   messageBodyPart.setDataHandler(new DataHandler(source));
////		   messageBodyPart.setFileName(filename);
////		   multipart.addBodyPart(messageBodyPart);
////		   message.setContent(multipart);
//		
//		
//		// create and fill the first message part
//		MimeBodyPart mbp1 = new MimeBodyPart();
//				
//		mbp1.setContent(body, "text/html");
//
//		// create the Multipart and add its parts to it
//		Multipart mp = new MimeMultipart("related");
//		mp.addBodyPart(mbp1);
//
//		if (filename != null) {
//			// create the second message part
//			// attach the file to the message
//			MimeBodyPart mbp2 = new MimeBodyPart();
//			FileDataSource fds = new FileDataSource(filename);
//			mbp2.setDataHandler(new DataHandler(fds));
//			mbp2.setFileName(fds.getName());
//			mbp2.setContentID(fds.getName());
//			mp.addBodyPart(mbp2);
//		}
//
//
//		// add the Multipart to the message
//		msg.setContent(mp);
//
//		final Transport transport = session.getTransport(oConf.getTransport());
//		transport.connect(smtpServer, (int) oConf.getSmtpPort(), pMailFrom.getValue(), pMailPassword.getValue());
//		transport.sendMessage(msg, recipientAddresses);
//		transport.close();
//		
		BizMailConf oConf = new BizMailConf();
		oConf.dontThrowException(true);
		if (!oConf.read(pIDMailConf.getValue()))
			JExcepcion.SendError("No existe servidor smtp");
		smtpServer = oConf.getSmtpServer();

		PssLogger.logDebug("mailTitle: " + title);
		PssLogger.logDebug("mailFrom: " + pMailFrom.getValue());
		PssLogger.logDebug("mailto: " + pMailTo.getValue());
		PssLogger.logDebug("attach: " + filename);

		Session session = null;
		if (!oConf.getSmtpTransport().equals("esmtp"))
			session = Session.getInstance(System.getProperties(), null);
		else
			session = getSession(oConf);
		
		final MimeMessage msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress(pMailFrom.getValue()));

		final Address[] recipientAddresses = InternetAddress.parse(pMailTo.getValue());
		msg.setRecipients(Message.RecipientType.TO, recipientAddresses);
		msg.setSentDate(new Date());
		if (title != null)
			msg.setSubject(title);
		else
			msg.setSubject(pMailSubject.getValue());
		if (body == null) {
			body = pMailBody.getValue();
		}
		
//		   BodyPart messageBodyPart = new MimeBodyPart();
//		   messageBodyPart.setText("Here's the file");
//		   Multipart multipart = new MimeMultipart();
//		   multipart.addBodyPart(messageBodyPart);
//		   messageBodyPart = new MimeBodyPart();
//		   DataSource source = new FileDataSource(filename);
//		   messageBodyPart.setDataHandler(new DataHandler(source));
//		   messageBodyPart.setFileName(filename);
//		   multipart.addBodyPart(messageBodyPart);
//		   message.setContent(multipart);
		
		// create and fill the first message part
		MimeBodyPart mbp1 = new MimeBodyPart();
				
		mbp1.setContent(body, "text/html");

		// create the Multipart and add its parts to it
		Multipart mp = new MimeMultipart("related");
		mp.addBodyPart(mbp1);

		if (filename != null) {
			// create the second message part
			// attach the file to the message
			MimeBodyPart mbp2 = new MimeBodyPart();
			FileDataSource fds = new FileDataSource(filename);
			mbp2.setDataHandler(new DataHandler(fds));
			mbp2.setFileName(fds.getName());
			mbp2.setContentID(fds.getName());
			mp.addBodyPart(mbp2);
		}


		// add the Multipart to the message
		msg.setContent(mp);

		if (!oConf.getSmtpTransport().equals("esmtp")) {
			final Transport transport = session.getTransport(oConf.getSmtpTransport());
			transport.connect(smtpServer, (int) oConf.getSmtpPort(), pMailFrom.getValue(), pMailPassword.getValue());
			transport.sendMessage(msg, recipientAddresses);
			transport.close();
		}
		else {
			Transport.send(msg);
		}
	}


	private Session getSession( BizMailConf conf) throws Exception {
		Authenticator authenticator = new Authenticator(pUser.getValue(),pMailPassword.getValue());

		Properties properties = new Properties();
		properties.setProperty("mail.smtp.submitter", authenticator.getPasswordAuthentication().getUserName());
		properties.setProperty("mail.smtp.auth", "true");

		properties.setProperty("mail.smtp.host", conf.getSmtpServer());
		properties.setProperty("mail.smtp.port", ""+conf.getSmtpPort());

		return Session.getInstance(properties, authenticator);
	}

	private class Authenticator extends javax.mail.Authenticator {
		private PasswordAuthentication authentication;

		public Authenticator(String username,String password) {
			authentication = new PasswordAuthentication(username, password);
		}

		protected PasswordAuthentication getPasswordAuthentication() {
			return authentication;
		}
	}
	
/*
	public class AttachExample {

		 public static void main (String args[]) throws Exception {
		   String host = "smtp.kar.com";
		   String from = "FromAddre@kar.com";
		   String[] to =  {"ABC@kar.com","XYZ@kar.com"};
		   String filename = "AttachFile.txt";
		   // Get system properties
		   Properties props = System.getProperties();
		   props.put("mail.smtp.host", host);

		   Session session = Session.getInstance(props, null);
		   System.out.println(session.getProperties());

		   Message message = new MimeMessage(session);
		   message.setFrom(new InternetAddress(from));

		   InternetAddress[] toAddress = new InternetAddress[to.length];
		   for (int i = 0; i < to.length; i++)
		     toAddress[i] = new InternetAddress(to[i]);
		   message.setRecipients(Message.RecipientType.TO, toAddress);    
		   message.setSubject("Hello JavaMail Attachment");
		   
		   BodyPart messageBodyPart = new MimeBodyPart();
		   messageBodyPart.setText("Here's the file");
		   Multipart multipart = new MimeMultipart();
		   multipart.addBodyPart(messageBodyPart);
		   messageBodyPart = new MimeBodyPart();
		   DataSource source = new FileDataSource(filename);
		   messageBodyPart.setDataHandler(new DataHandler(source));
		   messageBodyPart.setFileName(filename);
		   multipart.addBodyPart(messageBodyPart);
		   message.setContent(multipart);
		   
		  try{
		       Transport.send(message);
		  }
		  catch(SendFailedException sfe)
		   {
		 	 message.setRecipients(Message.RecipientType.TO,  sfe.getValidUnsentAddresses());
		 	 Transport.send(message);
		   
		   }
		 }

		} 
	*/
	
	/*************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************
	 * @return Una lista con las direcciones donde se debe enviar el mail
	 * @throws Exception
	 ************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************/
	private JList<String> getList() throws Exception {
		JList<String> oList = JCollectionFactory.createList();
		PssLogger.logDebug("mailto: " + pMailTo.getValue());
		JStringTokenizer st = JCollectionFactory.createStringTokenizer(pMailTo.getValue(), ';');
		// int iCantToks = st.countTokens();
		for (int i = 0; st.hasMoreTokens(); i++) {
			String param = st.nextToken();
			PssLogger.logDebug(param);
			oList.addElement(param);
		}
		return oList;
	}

	public static void mailProcess() throws Exception {
		JRecords<BizMailSender> oMails = new JRecords<BizMailSender>(BizMailSender.class);
		oMails.addFilter("ESTADO", BizMailSender.MAIL_PENDING);
		oMails.readAll();
		oMails.firstRecord();
		while (oMails.nextRecord()) {
			BizMailSender oMail = oMails.getRecord();
			oMail.send(null, null);
			oMail.marcarEnviado();
		}
	}

	public void validateConstraints() throws Exception {
		if (this.pCompany.isNull()) {
			this.pCompany.setValue(BizCompany.DEFAULT_COMPANY);
		}
	}

	private void marcarEnviado() throws Exception {
		setEstado(BizMailSender.MAIL_SENT);
		setFechaEnvio(new Date());
		setHoraEnvio(JDateTools.DateToString(new JDate(new Date()).getValue(), "HHmmss"));
		processUpdate();
	}

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

}

