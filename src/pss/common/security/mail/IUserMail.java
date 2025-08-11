package  pss.common.security.mail;

import java.util.Date;

import javax.mail.Message;

public interface IUserMail {
	public Date onMessage(Message m,BizUsrMailSender mailer) throws Exception;
}
