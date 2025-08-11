package pss.core.connectivity.messageManager.modules.login;

import pss.core.tools.JExcepcion;

public class BasicLoginMsgModule {

	public void validateConnection(String zPassword) throws Exception {
		if (zPassword.compareTo("GP151HGK218MR159RL158SG154") != 0)
			JExcepcion.SendError("Password Incorrecta") ;
		return;
	}
	
	public static BasicLoginMsgModule getLoginModuleInstance() throws Exception {
		return new BasicLoginMsgModule();
	}
}
