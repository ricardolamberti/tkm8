package  pss.common.moduleConfigMngr.common.ModuleConfigMngr.requests.lastConfigurationId;

import pss.core.connectivity.messageManager.common.message.messageProcessor.RequestListener;

public class ReqListenerLastConfigurationId extends RequestListener {

	public ReqListenerLastConfigurationId(Object zProcessorObj, String zProcessorMethod) throws Exception {
		this(new ReqLastConfigurationId(), zProcessorObj, zProcessorMethod);
  }
	
	public ReqListenerLastConfigurationId(ReqLastConfigurationId zMsg, Object zProcessorObj, String zProcessorMethod) throws Exception {
	  super(zProcessorObj, zProcessorMethod);
	  this.setMessageToSubscribe(zMsg);
	}
}
