package pss.core.connectivity.messageManager.common.message.events;

import pss.core.connectivity.messageManager.common.message.messageProcessor.SystemEventListener;

public class SysEvtListenerOnDisconnect extends SystemEventListener {

	public SysEvtListenerOnDisconnect(Object zListenerObj, String zListenerMethod) throws Exception {
		this(new SysEvtOnDisconnect(), zListenerObj, zListenerMethod);
	}

	public SysEvtListenerOnDisconnect(SysEvtOnDisconnect zMsg, Object zListenerObj, String zListenerMethod) throws Exception {
		super(zListenerObj, zListenerMethod);
		this.setMessageToSubscribe(zMsg);
	}

}
