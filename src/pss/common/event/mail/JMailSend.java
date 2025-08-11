package pss.common.event.mail;

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
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import pss.core.tools.JExcepcion;
import pss.core.tools.PssLogger;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JMap;

public class JMailSend {

	private int idServer;
	private String title="";
	private String mailTo;
	private String mailFrom;
	private String mailFromName;
	private String password;
	private String signature="";
	private String authUser;
	private String body="";
	private boolean bFirmar=false;
	private String mailToBCC;
	private String mailId;
	
	private JMap<String, String> fileMap;
	
	public JMailSend() {
	}
	
//	public void setCompany(String v) {
//		company=v;
//	}
//	public void setUsuario(String v) {
//		usuario=v;
//	}
	public void setAuthUser(String v) {
		authUser=v;
	}
	public void setSignature(String v) {
		if (v==null) v="";
		this.signature=v;
	}
	public void setTitle(String v) {
		title=v;
	}
	public void setMailTo(String v) {
		mailTo=v;
	}
	public void setMailFrom(String v) {
		mailFrom=v;
	}
	public void setMailFromName(String v) {
		mailFromName=v;
	}
	public void setPassword(String v) {
		password=v;
	}
	public void setBody(String v) {
		body=v;
	}
	public void setIdServer(int v) {
		idServer=v;
	}
	public void setMailId(String v) {
		this.mailId=v;
	}
	public String getMailId() {
		return this.mailId;
	}
	public void setObjServer(BizMailConf v) {
		this.mailConf=v;
	}
	
	public void setFilesAttach(JMap<String, String> map) {
		this.fileMap=map;
	}
	public void addFileAttach(String name, String file) {
		if (this.fileMap==null) fileMap=JCollectionFactory.createMap();
		this.fileMap.addElement(name, file);
	}
	public void setMailToBCC(String v) {
		mailToBCC=v;
	}

//	public String findSignature() throws Exception {
//		if (!this.bFirmar) return null;
//		BizMailSignature sig = new BizMailSignature();
//		sig.dontThrowException(true);
//		if ( !sig.read(company,usuario) ) 
//			return null;
//		return URLDecoder.decode(new String(sig.getSignature().getBytes("ISO-8859-1") ), "ISO-8859-1") ;
//	}

	/*************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************
	 * Enviar un mail
	 * 
	 * @throws Exception
	 ************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************/
//	public void send(String title, String body) throws Exception {
//		this.send(title, body, (String)null);
//	}
//	
//	public void send(String title, String body, String filename) throws Exception {
//		JList<String> list = JCollectionFactory.createList();
//		if (filename!=null) list.addElement(filename);
//		this.send(title, body, list);
//	}
//	
//	public void send(String title, String body, JList<String> filenames) throws Exception {
//		this.send(title, body, filenames, false);
//	}
	
//	public void send(String title, String body, JList<String> filenames, boolean hasSignature) throws Exception {
	private BizMailConf mailConf;
	public BizMailConf getObjMailConf() throws Exception {
		if (this.mailConf!=null) return this.mailConf;
		BizMailConf conf = new BizMailConf();
		conf.dontThrowException(true);
		if (!conf.read(idServer))
			JExcepcion.SendError("No existe servidor smtp");
		return (this.mailConf=conf);
	}
	
//	public String getBodyAsIso() throws Exception {
//		if (this.body==null) return "";
//		return (new URLCodec()).decode(this.body,"ISO-8859-1");
////		return URLDecoder.decode(new String(body.getBytes("ISO-8859-1")), "ISO-8859-1");
//	}

//	private String appendSignature(String body) throws Exception {
//		String signature = this.findSignature();
//		if (signature==null) return body;
//		return body+signature;
//	}

	
	public void send() throws Exception {
		
		BizMailConf conf = this.getObjMailConf();
	
		PssLogger.logDebug("mailTitle: " + title);
		PssLogger.logDebug("mailFrom: " + mailFrom);
		PssLogger.logDebug("mailFromName: " + mailFromName);
		PssLogger.logDebug("mailto: " + mailTo);
		PssLogger.logDebug("mailto CCO: " + mailToBCC);
		PssLogger.logDebug("attach: " + (this.fileMap!=null ? "S":"N"));
	
		Session session = this.findSession();
		
		MimeMessage msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress(this.mailFrom, this.mailFromName));
	
		Address[] recipientAddresses = InternetAddress.parse(this.mailTo.replaceFirst(";", ","));
		msg.setRecipients(Message.RecipientType.TO, recipientAddresses);
		
		
	/*//////////////////////////////////////////////////////////////////////////////////
		// Queda Preparado para Enviar mails con BCC, para cuando se pueda, porque con el método
		// Transport.sendmessage no sepuede.
	 
		 MRO 03/11/2017
		
		
		if (!this.mailToBCC.isEmpty()) {
			final Address[] recipientAddressesBCC = InternetAddress.parse(this.mailToBCC.replaceFirst(";", ","));
			msg.addRecipients(Message.RecipientType.BCC, recipientAddressesBCC);
		}*/	
		
		msg.setSentDate(new Date());
		
	
		msg.setSubject(this.title, "utf-8");
		
		
		// create the Multipart and add its parts to it

		// create and fill the first message part

		
		String myBody=this.body;
		myBody=this.createTreeMap();
		myBody=myBody+signature;

		Multipart mp = new MimeMultipart("related");
		MimeBodyPart mbp1 = new MimeBodyPart();		
		mbp1.setContent(myBody, "text/html");
		mp.addBodyPart(mbp1);
	
		this.attachTreeMap(mp);
		this.attachFiles(mp);
	
		// add the Multipart to the message
		msg.setContent(mp);
//		if (conf.isSalienteExchange()) {
//			Transport.send(msg);
//		} else {
			Transport transport = session.getTransport(conf.getSalienteTransport());
			transport.connect(conf.getSmtpServer(), conf.getSmtpPort(), this.mailFrom, this.password);
			transport.sendMessage(msg, recipientAddresses);
			transport.close();
//		}
		this.setMailId(msg.getMessageID());
	}

	private Map<String,String> imageMap;
	private String createTreeMap() throws Exception {
		Map<String,String> map = new TreeMap<String,String>();
		int cursor=0;
		String source = this.body;
		String stype="src=\"data:image/";
		while (true) {
			int posI = source.indexOf("<img", cursor);
			if (posI==-1) break;
			// remplaza la imagen por un puntero a los adjuntos
			int posIS = source.indexOf(stype, cursor);
			if (posIS==-1) return source;
			String ftype=source.substring(posIS+stype.length(), posIS+stype.length()+3);
			int posContenido = posIS+27;
			int posFS = source.indexOf("\"", posContenido);
			String contenidoS = source.substring(posContenido, posFS);
			String fileName = "img_" + posIS + "."+ftype;
			map.put(fileName, contenidoS.replace(" ", "+"));
			source = source.replace(source.substring(posIS, posFS+1), "src=\"cid:"+fileName+ "\"");
			cursor=posFS;
		}
		this.imageMap=map;
		return source;
	}
	
	private void attachFiles(Multipart mp) throws Exception { 
		if (this.fileMap == null) return;
		// create the second message part
		// attach the file to the message
		JIterator<String> iter = this.fileMap.getKeyIterator();
		while (iter.hasMoreElements()) {
			String name = iter.nextElement();
			if (name.equals("")) continue;
			String fileName = this.fileMap.getElement(name);
			MimeBodyPart mbp2 = new MimeBodyPart();
			FileDataSource fds = new FileDataSource(fileName);
			mbp2.setDataHandler(new DataHandler(fds));
			mbp2.setFileName(name);
			mbp2.setContentID(name);
			mp.addBodyPart(mbp2);
		}
	}
	
	private void attachTreeMap(Multipart mp) throws Exception {
		if (this.imageMap==null) return;
		Iterator<String> it = imageMap.keySet().iterator();
		while (it.hasNext()) {
			String key=it.next();
			String content = imageMap.get(key);
			Decoder decoder = Base64.getDecoder();
			byte[] imageByte = decoder.decode(content);
	
			MimeBodyPart imagePart = new MimeBodyPart();
		  DataSource ds = new ByteArrayDataSource(imageByte, "image/*");
		  imagePart.setFileName(key);
			imagePart.setDataHandler(new DataHandler(ds));
			imagePart.setDisposition(MimeBodyPart.INLINE);
			imagePart.setContentID("<" + key + ">");
			mp.addBodyPart(imagePart);
		}			
	}



//	private Session findSession() throws Exception {
//		if (this.getObjMailConf().isMicrosoft())
//			return this.getAuthSession();
//		else 
//			return this.getNormalSession();
//	}
//	
//	private Session getNormalSession() throws Exception {
//		Properties properties = new Properties();
//		if ( this.getObjMailConf().isSsl() ) {
//		  properties = System.getProperties();
//		  properties.put("mail.smtp.starttls.enable", "true");
//		}
//		return Session.getInstance(properties, null);
//	}
	
	private Session findSession() throws Exception {
		BizMailConf conf = this.getObjMailConf();
		return Session.getInstance(conf.makeProperties(authUser, true), conf.makeAuth(authUser, password));
	}
	

}
