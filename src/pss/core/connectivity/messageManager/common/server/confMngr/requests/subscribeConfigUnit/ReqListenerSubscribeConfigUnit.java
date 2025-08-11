package pss.core.connectivity.messageManager.common.server.confMngr.requests.subscribeConfigUnit;

import pss.core.connectivity.messageManager.common.message.messageProcessor.RequestListener;

public class ReqListenerSubscribeConfigUnit extends RequestListener {

	public ReqListenerSubscribeConfigUnit(Object zProcessorObj, String zProcessorMethod) throws Exception {
		this(new ReqSubscribeConfigUnit(), zProcessorObj, zProcessorMethod);
  }
	
	public ReqListenerSubscribeConfigUnit(ReqSubscribeConfigUnit zMsg, Object zProcessorObj, String zProcessorMethod) throws Exception {
	  super(zProcessorObj, zProcessorMethod);
	  this.setMessageToSubscribe(zMsg);
	}
}
