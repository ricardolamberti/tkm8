package  pss.common.security.mail;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Header;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.AndTerm;
import javax.mail.search.ComparisonTerm;
import javax.mail.search.ReceivedDateTerm;
import javax.mail.search.SearchTerm;
import javax.mail.search.SubjectTerm;

import com.sun.mail.util.MailSSLSocketFactory;

import pss.common.security.BizUsuario;
import pss.core.JAplicacion;
import pss.core.services.records.JRecords;
import pss.core.tools.PssLogger;
import pss.core.tools.collections.JIterator;


public class JPop3 implements IUserMail{

	public static void main(String[] args) {
		try {
		//	while (true )
		//		new JPop3().search("pop3","pop.uolsinectis.com.ar", 110, "ricardolamberti@uol.com.ar", "XXXXXX",false,"(EXP:",null,null);
	//			new JPop3().search("pop3","pop.gmail.com", 995, "ricardo.lamberti@gmail.com", "XXXXX", true,"(EXP:",null,null);
				new JPop3().search("imap","imap.gmail.com", 993, "ricardo.lamberti@gmail.com", "XXXXX", true,"(EXP:",null,null,null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
 
	public Date onMessage(Message m,BizUsrMailSender mailer) throws Exception {
		Enumeration headers = m.getAllHeaders();
		
		while (headers.hasMoreElements()) {
		  Header h = (Header) headers.nextElement();
		  System.out.println(h.getName() + ": " + h.getValue());
		}

		// Get some headers
		Date date = m.getSentDate();
		String subj = m.getSubject();
		String mimeType = m.getContentType();
		System.out.println(date + "\t"  + subj + "\t" + mimeType);
		readContent(m);
		return date;
	}
	String sContent = null;
	public Date search(String zService,String host, int port, String user, String password, boolean ssl, String zContent, Date date,IUserMail messageManager,BizUsrMailSender mailer) throws Exception {
		IUserMail mgMess = messageManager;
		if (mgMess==null) mgMess=this;
		sContent=zContent;
		String service = zService;
		boolean exchange = false;
	   if (service.equals("eimap")) {
	  	 service="imaps";
	  	 ssl=true;
	  	 exchange=true;
	   }
     Properties pop3Props = new Properties();
     if (service.equals("pop3")) {
	     pop3Props.put("mail."+service+".disabletop", true);
	     pop3Props.put("mail."+service+".forgettopheaders", true);
     }
     Session session;
     Store store;
		if (ssl) {
			pop3Props.put("mail."+service+".ssl.enable", true);
			if (!exchange) {
				pop3Props.put("mail."+service+".socketFactory", port);
				pop3Props.put("mail."+service+".socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			} else { 
				MailSSLSocketFactory sf = new MailSSLSocketFactory();
				sf.setTrustAllHosts(true);
				pop3Props.put("mail.imaps.starttls.enable", "true");
				pop3Props.put("mail.imap.ssl.trust", "*");
				pop3Props.put("mail.imaps.ssl.socketFactory", sf);
			}
			
		}
		session = Session.getInstance(pop3Props);
		store = session.getStore(service);
		store.connect(host, port, user, password);
		// Get a Store object
		
		Folder fldr = store.getDefaultFolder();
    
		fldr = fldr.getFolder("INBOX");
    
		fldr.open(Folder.READ_ONLY);

		int count = fldr.getMessageCount();
		System.out.println(count + " total messages");

    // creates a search criterion
    SearchTerm searchCondition;
    SearchTerm searchSubject = null;
    SearchTerm searchDate = null;
    if (sContent!=null) searchSubject =new SubjectTerm(sContent);
    Date lastmail=date;
    Message msgs[] = null;
		if (date!=null) searchDate = new ReceivedDateTerm(ComparisonTerm.GT,date);
		
		
		if (searchDate==null && searchSubject==null) 
			searchCondition =null;
		else if (searchDate!=null && searchSubject!=null)
			searchCondition=new AndTerm(searchDate,searchSubject);
		else if (searchDate!=null) searchCondition = searchDate;
		else searchCondition = searchSubject;
	
		if (searchCondition==null) msgs = fldr.getMessages();
		else msgs = fldr.search(searchCondition);
		
//		FetchProfile fetchProfile = new FetchProfile();
//		fetchProfile.add(UIDFolder.FetchProfileItem.UID);
//		fldr.fetch(msgs, fetchProfile);
		
 		for (Message m:msgs) {
 			try {
				Date newLast =mgMess.onMessage(m,mailer);
				if (lastmail==null || newLast.after(lastmail)) lastmail=newLast;
			} catch (javax.mail.MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
 		}
  	fldr.close(true);
		store.close();
		return lastmail;
	}

	
	public static void readContent(Message m) throws IOException, MessagingException {
		Object o = m.getContent();
		if (o instanceof String) {
			System.out.println("**This is a String Message**");
			System.out.println((String) o);
			
		} else if (o instanceof Multipart) {
			System.out.print("**This is a Multipart Message.  ");
			Multipart mp = (Multipart) o;
			int count3 = mp.getCount();
			System.out.println("It has " + count3 + " BodyParts in it**");
			for (int j = 0; j < count3; j++) {
				// Part are numbered starting at 0
				BodyPart b = mp.getBodyPart(j);
				String mimeType2 = b.getContentType();
				System.out.println("BodyPart " + (j + 1) + " is of MimeType " + m.getContentType());

				Object o2 = b.getContent();
				if (o2 instanceof String) {
					System.out.println("**This is a String BodyPart**");
					System.out.println((String) o2);
				} else if (o2 instanceof Multipart) {
					System.out.print("**This BodyPart is a nested Multipart.");
					Multipart mp2 = (Multipart) o2;
					int count2 = mp2.getCount();
					System.out.println("It has " + count2 + "further BodyParts in it**");
				} else if (o2 instanceof InputStream) {
					System.out.println("**This is an InputStream BodyPart**");
				}
			} // End of for
		} else if (o instanceof InputStream) {
			System.out.println("**This is an InputStream message**");
			InputStream is = (InputStream) o;
			// Assumes character content (not binary images)
			int c;
			while ((c = is.read()) != -1) {
				System.out.write(c);
			}
		}
	}

  public synchronized void execute(String content,IUserMail messageManager) throws Exception {
//		Thread oThread = Thread.currentThread();
//		while (!oThread.isInterrupted()) {

			boolean open = false;
			JAplicacion.openSession();
			try {
				JAplicacion.GetApp().openApp("Search email", JAplicacion.AppService(), true);
				open = true;
				if (BizUsuario.getUsr().GetUsuario().equals("")) {
					BizUsuario usuario = new BizUsuario();
					usuario.Read(BizUsuario.C_ADMIN_USER);
					BizUsuario.SetGlobal(usuario);
			}

				JRecords<BizUsrMailSender> oMails = new JRecords<BizUsrMailSender>(BizUsrMailSender.class);
				JIterator<BizUsrMailSender> it = oMails.getStaticIterator();
				while (it.hasMoreElements()) {
					BizUsrMailSender mail = it.nextElement();
					try {
						if (!mail.isReceiver()) continue;
						if (!mail.isOK()) continue;
						mail.findMails(content, messageManager);
					} catch (Exception e) {
						PssLogger.logError(e);
					}
				}

			} catch (Exception eee) {
				PssLogger.logError(eee);
				System.out.println("Error :" + eee.getMessage());
			} finally {
				if (open)
					JAplicacion.GetApp().closeApp();
				JAplicacion.closeSession();
				Thread.sleep(100000);
			}

		}

//	}
}