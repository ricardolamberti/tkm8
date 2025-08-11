package pss.common.mail;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import pss.common.mail.mailing.BizMail;
import pss.common.security.BizUsuario;
import pss.core.services.records.JRecords;
import pss.core.tools.JDateTools;

public class JMailData {

	public String company;
	public String user;
	public long cantMails;
	public long cantUrgentMails;
	public boolean showUrgentMessage;
	public BizMail urgentMail;
	public String urgentMessage;
	public String urgentTitle;
	public Date lastCheck;
	

	long urgentId;
	
	Date urgentDate;
	

	public JMailData() {
	}
		
	public void verifyCantidad() throws Exception {
		JRecords<BizMail> mails= new JRecords<BizMail>(BizMail.class);
		mails.addFilter("company", this.company);
		mails.addFilter("usuario", this.user);
		mails.addFilter("readed", false);
		mails.addFixedFilter("(valid_time is null or valid_time >= '" + JDateTools.CurrentDateTime() + "')");
		this.cantMails = mails.selectCount();
	}
	
	public void verifyUrgente() throws Exception {
		JRecords<BizMail> urgs= new JRecords<BizMail>(BizMail.class);
		urgs.addFilter("company", BizUsuario.getUsr().getCompany());
		urgs.addFilter("usuario", BizUsuario.getUsr().GetUsuario());
		urgs.addFilter("readed", false);
		urgs.addJoin("msg_message");
		urgs.addFixedFilter("where msg_message.id_message=msg_mail.id_message ");
		urgs.addFixedFilter(" and msg_message.urgent='S'");
		urgs.readAll();
		urgs.toStatic();
		if (!urgs.ifRecordFound()) return;
		BizMail mail = urgs.getFirstRecord();
		this.cantUrgentMails =  urgs.sizeStaticElements();
		this.urgentMail = mail;
//		this.urgentMessage = mail.getUrgentMessage();
//		this.urgentTitle = mail.getUrgentTitle();
//		this.showUrgentMessage = false;
	}

	
	public boolean isReciente() throws Exception {
		if (this.lastCheck==null) return true;
		Calendar c = new GregorianCalendar();
		c.setTime(new Date());
		c.add(Calendar.SECOND, -60);
		return c.getTime().before(this.lastCheck); 
	}

	
	public boolean hasMails() throws Exception {
		return this.cantMails!=0;
	}

	public boolean hasUrgentMail() throws Exception {
//		if (this.urgentMail) return false;
		return this.cantUrgentMails!=0;
	}

	public long getCantMail() throws Exception {
		return this.cantMails;
	}
	
	public BizMail getUrgentMail() throws Exception {
		return this.urgentMail;
	}


}
