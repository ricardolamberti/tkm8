package  pss.common.moduleConfigMngr.common.ModuleConfigMngr.requests.subscribeMdlConfigMngr;

import pss.core.connectivity.messageManager.common.message.messageProcessor.RequestListener;

public class ReqListenerSubscribeMdlConfigMngr extends RequestListener {

	public ReqListenerSubscribeMdlConfigMngr(Object zProcessorObj, String zProcessorMethod) throws Exception {
		this(new ReqSubscribeMdlConfigMngr(), zProcessorObj, zProcessorMethod);
  }
	
	public ReqListenerSubscribeMdlConfigMngr(ReqSubscribeMdlConfigMngr zMsg, Object zProcessorObj, String zProcessorMethod) throws Exception {
	  super(zProcessorObj, zProcessorMethod);
	  this.setMessageToSubscribe(zMsg);
	}
}
