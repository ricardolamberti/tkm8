package pss.core.connectivity.messageManager.common.message.messageProcessor;

import pss.core.connectivity.messageManager.common.message.EvtError;

public class ErrorEventListener extends EventListener {

	public ErrorEventListener(Object zListenerObj, String zListenerMethod) throws Exception {
	  super(zListenerObj, zListenerMethod);
	  this.setMessageToSubscribe(new EvtError());
  }

//	@Override
//	public String getMsgId() {
//	  return "*";
//	}
//	
//	@Override
//	public char getMsgType() {
//	  return MessageEnvelope.MSG_TYPE_ERROR;
//	}
}
