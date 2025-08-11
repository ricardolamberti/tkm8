package pss.common.mail;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import pss.common.mail.mailing.BizMail;
import pss.common.mail.message.BizMessage;
import pss.common.security.BizUsuario;
import pss.core.services.records.JRecords;
import pss.core.tools.JDateTools;


public class JMail {
	
	
	private Map<String,JMailData>  cacheMails;
	private Map<String,JMailData> getCacheMap() throws Exception {
		if (cacheMails!=null) return cacheMails;
		cacheMails = new HashMap<String,JMailData>(); 
		return cacheMails;
	}
		
	private static JMail mailServer;
	public static JMail getMailServer() throws Exception {
		if (mailServer!=null) return mailServer;
		JMail m = new JMail();
		return (mailServer = m);
	}
	
	public static JMailData getMyMail() throws Exception {
		return JMail.getMailServer().findMyMail();
	}
	
	public void clearCache(String usuario) throws Exception {
//		String key = this.getKey(company,usuario);
		this.getCacheMap().remove(usuario);
	}
	
//	public boolean hasMail() throws Exception {
//		return this.findMyMail().hasMail();
//	}
//	
//	public boolean hasUrgentMail() throws Exception {
//		return this.findMyMail().hasUrgentMail();
//	}	
//
//	public long getCantMail() throws Exception {
//		return this.findMyMail().getCantMail();
//	}	
//
//	public String getUrgentMessage() throws Exception {
//		return this.findMyMail().getUrgentMail();
//	}
//	
//	public String getUrgentTitle() throws Exception {
//		return this.findMyMail().getUrgentTitle();
//	}	
	

	
	public void	markUrgentReaded() throws Exception {
		JMailData data = this.findMyMail();
		BizMail urg = data.getUrgentMail();
		if (urg==null) return;
		urg.processMarkReaded();
		Thread.sleep(urg.getSleepTime());

		this.clearCache(BizUsuario.getUsr().GetUsuario());
	}	
	
	private JMailData findMyMailCache() throws Exception {
		if (!BizUsuario.hasUsr()) return this.getVirtualMail();
		return this.getCacheMap().get(BizUsuario.getUsr().GetUsuario());
	}
	
	private JMailData getVirtualMail() throws Exception {
		if (BizUsuario.getUsr()==null) return new JMailData(); 
		JMailData mail = new JMailData();
		mail.company =  BizUsuario.getUsr().getCompany();
		mail.user = BizUsuario.getUsr().GetUsuario();
		return mail;
	}
	
	private JMailData findMyMail() throws Exception {
		JMailData data = this.findMyMailCache();
		if (data!=null && data.isReciente())
			return data;
		
		this.checkMails();
		return this.findMyMailCache();
	}
	
//	private String getKey(String c,String u) {
//		return c+"__"+u;
//	}
	
	private void checkMails() throws Exception {
//		this.purgeExpiredMessages();
		JMailData mail = this.getVirtualMail();
		mail.verifyCantidad();
		mail.verifyUrgente();
		this.pushMail(mail);
	}
	
	private void pushMail(JMailData mail) throws Exception {
		mail.lastCheck = new Date();
//		String key = this.getKey(mail.company, mail.user);
		this.getCacheMap().remove(mail.user);
		this.getCacheMap().put(mail.user, mail);		
	}

	public void purgeExpiredMessages() throws Exception {
		JRecords<BizMail> mails= new JRecords<BizMail>(BizMail.class);
		mails.addFilter("company", BizUsuario.getUsr().getCompany());
		mails.addFixedFilter("where valid_time is not null and valid_time < '" + JDateTools.CurrentDateTime() + "'");
		mails.readAll();
		while (mails.nextRecord()) {
			BizMail m = mails.getRecord();
			m.delete();
			BizMessage ms = new BizMessage();
			ms.dontThrowException(true);
			ms.addFilter("id_message", m.getIdmessage());
		  ms.deleteMultiple();
		}
	}
}
