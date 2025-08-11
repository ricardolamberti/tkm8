package  pss.common.moduleConfigMngr.common.ModuleConfigMngr.responses.subscribeMdlConfigMngr;

import pss.common.moduleConfigMngr.common.ModuleConfigMngr.requests.subscribeMdlConfigMngr.ReqSubscribeMdlConfigMngr;
import pss.core.connectivity.messageManager.common.message.messageProcessor.ResponseListener;

public class RespListenerSubscribeMdlConfigMngr extends ResponseListener {

	public RespListenerSubscribeMdlConfigMngr(ReqSubscribeMdlConfigMngr zMsg, Object zListenerObj, String zResponseListenerMethod, String zRespErrorListenerMethod) throws Exception {
		super(zListenerObj, zResponseListenerMethod, zRespErrorListenerMethod);
		this.setMessageToSubscribe(zMsg);
	}
	
	public RespListenerSubscribeMdlConfigMngr(Object zListenerObj, String zResponseListenerMethod, String zRespErrorListenerMethod) throws Exception {
		this(new ReqSubscribeMdlConfigMngr(), zListenerObj, zResponseListenerMethod, zRespErrorListenerMethod);
	}

}
