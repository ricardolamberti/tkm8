package pss.common.security.mail;

import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.AuthenticationFailedException;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import pss.common.event.mail.BizMailConf;
import pss.core.data.interfaces.connections.JBDatos;
import pss.core.services.JExec;
import pss.core.services.fields.JDateTime;
import pss.core.services.fields.JInteger;
import pss.core.services.fields.JPassword;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.tools.JExcepcion;
import pss.core.tools.JTools;
import pss.core.tools.PssLogger;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;

public class BizUsrMailSender extends JRecord {

	public final static String OK = "OK";
	public final static String SUSPENDIDO = "SUSP";
	public final static String SUSPUSUARIO = "SUSPUSU";
	public final static String SUSPMAX = "SUSPMAX";
	public final static long MAX_INTENTOS = 5;

	private String smtpServer = null;

	private JInteger pID = new JInteger();
	private JString pUsuario = new JString();
	private JString pDescripcion = new JString();
	private JString pEmail = new JString();
	private JString pUsuAuth = new JString();
	private JInteger pIDMailConf = new JInteger();
	private JPassword pPassword = new JPassword();
	private JString pServicename = new JString() {
		public void preset() throws Exception {
			pServicename.setValue(getObjMailConf().getDescription());
		};
	};

	private JDateTime pLastMail = new JDateTime();
	private JString pEstado = new JString();
	private JInteger pIntentos = new JInteger();
	private JString pDescrEstado = new JString() {
		public void preset() throws Exception {
			pDescrEstado.setValue(getEstadosMap().getElement(pEstado.getValue()));
		};
	};

	public void setMailPassword(String zVal) throws Exception {
		pPassword.setValue(zVal);
	}

	public String getMailPassword() throws Exception {
		return pPassword.getValue();
	}
	public String getEmail() throws Exception {
		return pEmail.getValue();
	}

	public void setEstado(String newEstado) throws Exception {
		pEstado.setValue(newEstado);
	}

	public boolean isOK() throws Exception {
		return pEstado.getValue().equals(OK);
	}

	public boolean isSuspendido() throws Exception {
		return pEstado.getValue().equals(SUSPENDIDO);
	}

	public void setInternalUser(String zVal) throws Exception {
		pUsuario.setValue(zVal);
	}

	public String getInternalUser() throws Exception {
		return pUsuario.getValue();
	}

	public long getIntentos() throws Exception {
		return pIntentos.getValue();
	}

	public void setIntentos(long nv) throws Exception {
		pIntentos.setValue(nv);
	}

	@Override
	public void createProperties() throws Exception {
		addItem("ID", pID);
		addItem("usuario", pUsuario);
		addItem("descripcion", pDescripcion);
		addItem("email", pEmail);
		addItem("usuario_auth", pUsuAuth);
		addItem("password", pPassword);
		addItem("last_mail", pLastMail);
		addItem("id_mail_conf", pIDMailConf);
		addItem("servicename", pServicename);
		addItem("estado", pEstado);
		addItem("intentos", pIntentos);
		addItem("descr_estado", pDescrEstado);
	}

	@Override
	public void createFixedProperties() throws Exception {
		addFixedItem(KEY, "ID", "ID", false, false, 18);
		addFixedItem(KEY, "usuario", "Usuario", true, false, 50);
		addFixedItem(FIELD, "usuario_auth", "Usuario auth", true, false, 4000);
		addFixedItem(FIELD, "descripcion", "Descripcion", true, false, 250);
		addFixedItem(FIELD, "email", "Direccion correo", true, true, 50);
		addFixedItem(FIELD, "password", "Password ", true, true, 250);
		addFixedItem(FIELD, "id_mail_conf", "Servidor Mail", true, true, 18);
		addFixedItem(FIELD, "last_mail", "Ultimo mail ", true, false, 18);
		addFixedItem(FIELD, "estado", "id estado", true, false, 18);
		addFixedItem(FIELD, "intentos", "Intentos", true, false, 18);
		addFixedItem(VIRTUAL, "servicename", "Servicio", true, false, 200);
		addFixedItem(VIRTUAL, "descr_estado", "Estado", true, false, 200);
	}

	public int getID() throws Exception {
		return pID.getValue();
	}

	public String getUsuario() throws Exception {
		return pUsuario.getValue();
	}

	public String getUsuarioAuth() throws Exception {
		return pUsuAuth.getValue();
	}

	public String getPassword() throws Exception {
		return pPassword.getValue();
	}

	public int getIdMailConf() throws Exception {
		return pIDMailConf.getValue();
	}

	public void setID(int iValue) throws Exception {
		pID.setValue(iValue);
	}

	public void setIDMailConf(int iValue) throws Exception {
		pIDMailConf.setValue(iValue);
	}

	public String getDescripcion() throws Exception {
		return pDescripcion.getValue();
	}

	public void setDescripcion(String aMailSubject) {
		pDescripcion.setValue(aMailSubject);
	}

	@Override
	public String GetTable() throws Exception {
		return "SEG_MAIL_SENDER";
	}

	public boolean read(int iID) throws Exception {
		addFilter("ID", iID);
		return read();
	}
	public boolean readByUsuario(String usuario) throws Exception {
		addFilter("usuario", usuario);
		return read();
	}

	// public void processInsert() throws Exception {
	// this.InsertarRegistro();
	// }

	public BizUsrMailSender() throws Exception {
	}

	public void setSMTPServer(String aSMTPServer) {
		smtpServer = aSMTPServer;
	}

	public void setUsuarioAuth(String aPass) {
		pUsuAuth.setValue(aPass);
	}

	/*************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************
	 * Enviar un mail
	 * 
	 * @throws Exception
	 ************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************/
	public void send(String to, String title, String body) throws Exception {
		this.send(to, title, body, null);
	}

	BizMailConf objMailConf;
	public BizMailConf getObjMailConf() throws Exception {
		if (objMailConf != null) return objMailConf;
		BizMailConf oConf = new BizMailConf();
		oConf.dontThrowException(true);
		if (!oConf.read(pIDMailConf.getValue()))
			JExcepcion.SendError("No existe servidor smtp");
		return objMailConf = oConf;
	}

	public boolean isSender() throws Exception {
//		return getObjMailConf().isSender();
		return true;
	}

	public boolean isReceiver() throws Exception {
		return true;
//		return getObjMailConf().isReceiver();
	}

	public void testSend() throws Exception {
		if (!isSender())
			throw new Exception("No se puede enviar mensajes con este servidor");
		if (!isOK())
			throw new Exception("Servicio de envio suspendido");
		BizMailConf oConf = getObjMailConf();
		smtpServer = oConf.getSmtpServer();

		PssLogger.logDebug("mailTitle: " + "TEST");
		PssLogger.logDebug("mailFrom: " + pEmail.getValue());
		PssLogger.logDebug("mailto: " + pEmail.getValue());

		Session session = this.getSession(oConf);
		// final Session session = getSession();
		final MimeMessage msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress(pEmail.getValue()));

		final Address[] recipientAddresses = InternetAddress.parse(pEmail.getValue());
		msg.setRecipients(Message.RecipientType.TO, recipientAddresses);
		msg.setSentDate(new Date());
		msg.setSubject("TEST");

		// create and fill the first message part
		MimeBodyPart mbp1 = new MimeBodyPart();
		Multipart mp = new MimeMultipart("related");

		mbp1.setContent("TEST SMTP CONFIG!", "text/html");
		mp.addBodyPart(mbp1);
		msg.setContent(mp);

		//	

		if (!oConf.getSmtpTransport().equals("esmtp")) {
			/*Properties properties = new Properties();
			properties.setProperty("mail.smtp.starttls.enable", "true");
			session = Session.getInstance(properties, null);*/				
			final Transport transport = session.getTransport(oConf.getSmtpTransport());
			
			transport.connect(smtpServer, (int) oConf.getSmtpPort(), pUsuAuth.getValue(), pPassword.getValue());
			transport.sendMessage(msg, recipientAddresses);
			transport.close();
		} else
			Transport.send(msg);

	}

	public void send(String to, String title, String body, String filename) throws Exception {
		if (!this.isSender())
			throw new Exception("No se puede enviar mensajes con este servidor");
		if (!this.isOK())
			throw new Exception("Servicio de envio suspendido");
		
		BizMailConf oConf = this.getObjMailConf();
		smtpServer = oConf.getSmtpServer();

		PssLogger.logDebug("mailTitle: " + title);
		PssLogger.logDebug("mailFrom: " + pEmail.getValue());
		PssLogger.logDebug("mailto: " + to);
		PssLogger.logDebug("attach: " + filename);

		Session session = this.getSession(oConf);
		// final Session session = getSession();
		final MimeMessage msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress(pEmail.getValue()));
		to=JTools.replace(to, ";", ",");
		to=JTools.replace(to, " ", "");
    to=JTools.replace(to, "mailto:", "");
		final Address[] recipientAddresses = InternetAddress.parse(to);
		msg.setRecipients(Message.RecipientType.TO, recipientAddresses);
		msg.setSentDate(new Date());
		msg.setSubject(title);

		Map<String,String> map = new TreeMap<String,String>();
		Multipart mp = new MimeMultipart("related");
	 	int posInicial = 0;
	 	String source = body;
  	while (true) {
  		int posI = source.indexOf("<img",posInicial);
  		if (posI==-1) break;
  		int l = "<img".length();
  		int posIS = source.indexOf("src=\"data:image/jpg;base64,",posI);
  		if (posIS!=-1) {
  			int posFS = source.indexOf("\"",posIS+("src=\"data:image/jpg;base64,".length())+1);
    		String contenidoS = source.substring(posIS+("src=\"data:image/jpg;base64,".length()),posFS);
    		map.put("img_" + posIS, contenidoS.replace(" ", "+"));
    		source = source.replace("data:image/jpg;base64,"+contenidoS, "cid:img_" + posIS );
  		}
  	  		
  		posInicial= posI+1;
  		
  	}
  	body=source;
		// create and fill the first message part
		// buscar todos los src="base64 

		MimeBodyPart mbp1 = new MimeBodyPart();
		
		mbp1.setContent(body, "text/html");

		// create the Multipart and add its parts to it
		mp.addBodyPart(mbp1);

		Iterator<String> it = map.keySet().iterator();
		while (it.hasNext()) {
			String key=it.next();
			String content = map.get(key);
			Decoder decoder = Base64.getDecoder();
			byte[] imageByte = decoder.decode(content);

  		MimeBodyPart imagePart = new MimeBodyPart();
  	  DataSource ds = new ByteArrayDataSource(imageByte, "image/*");
  		imagePart.setDataHandler(new DataHandler(ds));
  		imagePart.setDisposition(MimeBodyPart.INLINE);
  		imagePart.setContentID("<" + key + ">");
  		mp.addBodyPart(imagePart);
			
		}
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

		//	

		if (!oConf.getSmtpTransport().equals("esmtp")) {
			final Transport transport = session.getTransport(oConf.getSmtpTransport());
			transport.connect(smtpServer, (int) oConf.getSmtpPort(), pUsuAuth.getValue(), pPassword.getValue());
			transport.sendMessage(msg, recipientAddresses);
			transport.close();
		} else
			Transport.send(msg);
	}

	private Session getSession(BizMailConf conf) throws Exception {
		return Session.getInstance(conf.makeProperties(pUsuAuth.getValue(), true), conf.makeAuth(pUsuAuth.getValue(), pPassword.getValue()));
	}


	public void testReceive() throws Exception {
//		findMails("TEST", null);
	}
 
	public void findMails(String zContent, IUserMail messageManager) throws Exception {
		if (!isOK())
			throw new Exception("Servicio de recepcion suspendido");
		try {
			JPop3 pop = new JPop3();
			Date lastmail = pop.search(getObjMailConf().getSmtpTransport(), getObjMailConf().getSmtpServer(), (int) getObjMailConf().getSmtpPort(), getUsuarioAuth(), getPassword(), getObjMailConf().getSmtpTransport().endsWith("s"), zContent, pLastMail.getValue(), messageManager,this);
			if (messageManager != null) {
				pLastMail.setValue(lastmail);
				setIntentos(0);
				boolean inTransaction = JBDatos.GetBases().getPrivateCurrentDatabase().isTransactionInProgress();
				if (inTransaction)
					this.update();
				else
					this.execProcessUpdate();
			}
		} catch (AuthenticationFailedException e) {
			setEstado(SUSPENDIDO);
			setIntentos(getIntentos() + 1);
			boolean inTransaction = JBDatos.GetBases().getPrivateCurrentDatabase().isTransactionInProgress();
			if (inTransaction)
				this.update();
			else
				this.execProcessUpdate();
			throw e;
		} catch (Exception e) {
			setIntentos(getIntentos() + 1);
			if (getIntentos() > MAX_INTENTOS) setEstado(SUSPENDIDO);
			boolean inTransaction = JBDatos.GetBases().getPrivateCurrentDatabase().isTransactionInProgress();
			if (inTransaction)
				this.update();
			else
				this.execProcessUpdate();

			throw e;
		}
	}

	public void execProcesarChangeEstado(final String newEstado) throws Exception {
		JExec exec = new JExec(null, null) {
			public void Do() throws Exception {
				procesarChangeEstado(newEstado);
			}
		};
		exec.execute();
	}

	public void procesarChangeEstado(String newEstado) throws Exception {
		setEstado(newEstado);
		update();
	}

	public void execProcesarResetearFecha() throws Exception {
		JExec exec = new JExec(null, null) {
			public void Do() throws Exception {
				procesarResetearFecha();
			}
		};
		exec.execute();
	}

	public void procesarResetearFecha() throws Exception {
		pLastMail.setNull();
		update();
	}

	static JMap<String, String> estados;

	public static JMap<String, String> getEstadosMap() throws Exception {
		if (estados != null)
			return estados;
		JMap<String, String> map = JCollectionFactory.createMap();
		map.addElement(BizUsrMailSender.OK, "En ejecucion");
		map.addElement(BizUsrMailSender.SUSPENDIDO, "Suspendido por error autentificacion");
		map.addElement(BizUsrMailSender.SUSPUSUARIO, "Suspendido por usuario");
		map.addElement(BizUsrMailSender.SUSPMAX, "Suspendido por maximo reintentos");
		return estados = map;
	}

}
