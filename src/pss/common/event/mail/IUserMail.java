package pss.common.event.mail;

import javax.mail.Message;

public interface IUserMail {
	
	public void onMessage(JMailRecive reciver, Message m) throws Exception;
	
}
