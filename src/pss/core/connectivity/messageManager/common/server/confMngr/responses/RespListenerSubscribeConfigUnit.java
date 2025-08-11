package pss.core.connectivity.messageManager.common.server.confMngr.responses;

import pss.core.connectivity.messageManager.common.message.messageProcessor.ResponseListener;
import pss.core.connectivity.messageManager.common.server.confMngr.requests.subscribeConfigUnit.ReqSubscribeConfigUnit;

public class RespListenerSubscribeConfigUnit extends ResponseListener {

	public RespListenerSubscribeConfigUnit(ReqSubscribeConfigUnit zMsg, Object zListenerObj, String zResponseListenerMethod, String zRespErrorListenerMethod) throws Exception {
		super(zListenerObj, zResponseListenerMethod, zRespErrorListenerMethod);
		this.setMessageToSubscribe(zMsg);
	}
	
	public RespListenerSubscribeConfigUnit(Object zListenerObj, String zResponseListenerMethod, String zRespErrorListenerMethod) throws Exception {
		this(new ReqSubscribeConfigUnit(), zListenerObj, zResponseListenerMethod, zRespErrorListenerMethod);
	}

}
