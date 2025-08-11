package  pss.common.moduleConfigMngr.common.ModuleConfigMngr.responses.newConfigurationApplied;

import pss.common.moduleConfigMngr.common.ModuleConfigMngr.requests.newConfigurationApplied.ReqNewConfigurationApplied;
import pss.core.connectivity.messageManager.common.message.messageProcessor.ResponseListener;

public class RespListenerNewConfigurationApplied extends ResponseListener {

	public RespListenerNewConfigurationApplied(ReqNewConfigurationApplied zMsg, Object zListenerObj, String zResponseListenerMethod, String zRespErrorListenerMethod) throws Exception {
		super(zListenerObj, zResponseListenerMethod, zRespErrorListenerMethod);
		this.setMessageToSubscribe(zMsg);
	}
	
	public RespListenerNewConfigurationApplied(Object zListenerObj, String zResponseListenerMethod, String zRespErrorListenerMethod) throws Exception {
		this(new ReqNewConfigurationApplied(), zListenerObj, zResponseListenerMethod, zRespErrorListenerMethod);
	}

}