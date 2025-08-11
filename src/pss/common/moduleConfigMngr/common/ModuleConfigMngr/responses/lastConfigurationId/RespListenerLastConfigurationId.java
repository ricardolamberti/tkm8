package  pss.common.moduleConfigMngr.common.ModuleConfigMngr.responses.lastConfigurationId;

import pss.common.moduleConfigMngr.common.ModuleConfigMngr.requests.lastConfigurationId.ReqLastConfigurationId;
import pss.core.connectivity.messageManager.common.message.messageProcessor.ResponseListener;

public class RespListenerLastConfigurationId extends ResponseListener {

	public RespListenerLastConfigurationId(ReqLastConfigurationId zMsg, Object zListenerObj, String zResponseListenerMethod, String zRespErrorListenerMethod) throws Exception {
		super(zListenerObj, zResponseListenerMethod, zRespErrorListenerMethod);
		this.setMessageToSubscribe(zMsg);
	}
	
	public RespListenerLastConfigurationId(Object zListenerObj, String zResponseListenerMethod, String zRespErrorListenerMethod) throws Exception {
		this(new ReqLastConfigurationId(), zListenerObj, zResponseListenerMethod, zRespErrorListenerMethod);
	}

}