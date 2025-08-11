package pss.common.event.mail;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Enumeration;

import javax.mail.AuthenticationFailedException;
import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Header;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Store;
import javax.mail.search.AndTerm;
import javax.mail.search.ComparisonTerm;
import javax.mail.search.ReceivedDateTerm;
import javax.mail.search.SearchTerm;
import javax.mail.search.SubjectTerm;

import pss.common.security.BizUsuario;
import pss.core.services.records.JRecords;
import pss.core.tools.PssLogger;

public class JMailRecive implements IUserMail {

//	private BizMailConf conf;
	private int idServer;
	private String pattern;
	private IUserMail listener;

	public void setPattern(String v) { 
		this.pattern=v;
	}
	public void setListener(IUserMail v) { 
		this.listener=v;
	}
	
	public JMailRecive() {
	}

	public void setIdServer(int v) {
		idServer=v;
	}
//	public void setObjServer(BizMailConf v) {
//		this.mailConf=v;
//	}
	private BizMailCasilla mailCasilla;
	public void setObjMailCasilla(BizMailCasilla v) {
		this.mailCasilla=v;
	}
	public BizMailCasilla getObjMailCasilla() throws Exception {
		return this.mailCasilla;
	}

//	private BizMailConf mailConf;
//	public BizMailConf getObjMailConf() throws Exception {
//		if (this.mailConf!=null) return this.mailConf;
//		BizMailConf conf = new BizMailConf();
//		conf.dontThrowException(true);
//		if (!conf.read(idServer))
//			JExcepcion.SendError("No existe servidor");
//		return (this.mailConf=conf);
//	}


	public void reciveMails() throws Exception {
		BizMailCasilla casilla=this.getObjMailCasilla();
		if (!casilla.isOK()) return;
		try {
			casilla.execSearchAndRecive(this);
		} catch (AuthenticationFailedException e) {
			casilla.execProcessSuspend(true, e.getMessage());
			throw e;
		} catch (Exception e) {
			casilla.execProcessSuspend(false, e.getMessage());
			throw e;
		}
	
	}
	
//	public static void main(String[] args) {
//		JMailRecive mc = new JMailRecive();
//		mc
//		JMailRecive.exe
//	}

//  public synchronized void execute(String content, IUserMail listener) throws Exception {
//		boolean open = false;
//		JAplicacion.openSession();
//		try {
//			JAplicacion.GetApp().openApp("Search email", JAplicacion.AppService(), true);
//			open = true;
//			BizUsuario usuario = new BizUsuario();
//			usuario.Read(BizUsuario.C_ADMIN_USER);
//			BizUsuario.SetGlobal(usuario);
//
//			JIterator<BizMailCasilla> iter = this.getCasillas().getStaticIterator();
//			while (iter.hasMoreElements()) {
//				BizMailCasilla mail = iter.nextElement();
//				try {
//					if (!mail.isOK()) continue;
//					this.setContent(content);
//					this.setListener(listener);
//					this.reciveMails();
//				} catch (Exception e) {
//					PssLogger.logError(e);
//				}
//			}
//
//		} catch (Exception e) {
//			PssLogger.logError(e);
//		} finally {
//			if (open) JAplicacion.GetApp().closeApp();
//			JAplicacion.closeSession();
//			Thread.sleep(100000);
//		}
//
//	}

  public JRecords<BizMailCasilla> getCasillas() throws Exception { 
  	JRecords<BizMailCasilla> recs = new JRecords<BizMailCasilla>(BizMailCasilla.class);
  	recs.readAll();
  	return recs;
  }


	public Date recive() throws Exception {
		if (this.listener==null) listener=this;
		
		final BizMailCasilla casilla = this.getObjMailCasilla();

		Store store = casilla.connectReceive();
		
		Folder fldr = store.getDefaultFolder();
		fldr = fldr.getFolder("INBOX");
		fldr.open(Folder.READ_ONLY);

		int count = fldr.getMessageCount();
		PssLogger.logDebug(count + " total messages");

		SearchTerm search = this.createSearchTerm();
		
    Message msgs[] = search==null?fldr.getMessages():fldr.search(search);
		
		Date last=casilla.getLastMail();
 		for (Message m:msgs) {
			listener.onMessage(this, m);
			Date newLast = m.getSentDate();
			if (last==null || newLast.after(last)) last=newLast;
 		}
  	fldr.close(true);
		store.close();
		return last;
	}

	private SearchTerm createSearchTerm() throws Exception { 
		BizMailCasilla casilla=this.getObjMailCasilla();
	  Date lastMail=casilla.hasLastMail()? casilla.getLastMail():BizUsuario.getUsr().todayGMT(false);
	  SearchTerm search = new ReceivedDateTerm(ComparisonTerm.GE, lastMail); // protocolo imap solo sirve por fecha, no usa hora
		if (this.pattern!=null) 
	  	return new AndTerm(search, new SubjectTerm(this.pattern));

		return search;
	}	
	
	public static void dumpContent(Message m) throws IOException, MessagingException {
		Object o = m.getContent();
		if (o instanceof String) {
			PssLogger.logDebug("**This is a String Message**");
			PssLogger.logDebug((String) o);
			
		} else if (o instanceof Multipart) {
			PssLogger.logDebug("**This is a Multipart Message.  ");
			Multipart mp = (Multipart) o;
			int count3 = mp.getCount();
			PssLogger.logDebug("It has " + count3 + " BodyParts in it**");
			for (int j = 0; j < count3; j++) {
				// Part are numbered starting at 0
				BodyPart b = mp.getBodyPart(j);
//				String mimeType2 = b.getContentType();
				PssLogger.logDebug("BodyPart " + (j + 1) + " is of MimeType " + m.getContentType());

				Object o2 = b.getContent();
				if (o2 instanceof String) {
					PssLogger.logDebug("**This is a String BodyPart**");
					PssLogger.logDebug((String) o2);
				} else if (o2 instanceof Multipart) {
					PssLogger.logDebug("**This BodyPart is a nested Multipart.");
					Multipart mp2 = (Multipart) o2;
					int count2 = mp2.getCount();
					PssLogger.logDebug("It has " + count2 + "further BodyParts in it**");
				} else if (o2 instanceof InputStream) {
					PssLogger.logDebug("**This is an InputStream BodyPart**");
				}
			} // End of for
		} else if (o instanceof InputStream) {
			PssLogger.logDebug("**This is an InputStream message**");
			InputStream is = (InputStream) o;
			// Assumes character content (not binary images)
			int c;
			while ((c = is.read()) != -1) {
				System.out.write(c);
			}
		}
	}

	public void printMsg(Message m) throws Exception {
		Enumeration headers = m.getAllHeaders();
		while (headers.hasMoreElements()) {
		  Header h = (Header) headers.nextElement();
		  PssLogger.logDebug(h.getName() + ": " + h.getValue());
		}
		Date date = m.getSentDate();
		String subj = m.getSubject();
		String mimeType = m.getContentType();
		PssLogger.logDebug(date + "\t"  + subj + "\t" + mimeType);
		JMailRecive.dumpContent(m);
	}

	public void onMessage(JMailRecive reciver, Message m) throws Exception {
		this.printMsg(m);
	}
	

	
}
