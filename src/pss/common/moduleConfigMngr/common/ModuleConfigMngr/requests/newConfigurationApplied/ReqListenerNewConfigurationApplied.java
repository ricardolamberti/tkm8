package  pss.common.moduleConfigMngr.common.ModuleConfigMngr.requests.newConfigurationApplied;

import pss.core.connectivity.messageManager.common.message.messageProcessor.RequestListener;

public class ReqListenerNewConfigurationApplied extends RequestListener {

	public ReqListenerNewConfigurationApplied(Object zProcessorObj, String zProcessorMethod) throws Exception {
		this(new ReqNewConfigurationApplied(), zProcessorObj, zProcessorMethod);
  }
	
	public ReqListenerNewConfigurationApplied(ReqNewConfigurationApplied zMsg, Object zProcessorObj, String zProcessorMethod) throws Exception {
	  super(zProcessorObj, zProcessorMethod);
	  this.setMessageToSubscribe(zMsg);
	}
}
