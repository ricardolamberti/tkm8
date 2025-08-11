package pss.core.connectivity.messageManager.common.message.events;

import pss.core.connectivity.messageManager.common.message.messageProcessor.SystemEventListener;

public class SysEvtListenerOnConnect extends SystemEventListener {

	public SysEvtListenerOnConnect(Object zListenerObj, String zListenerMethod) throws Exception {
		this(new SysEvtOnConnect(), zListenerObj, zListenerMethod);
	}

	public SysEvtListenerOnConnect(SysEvtOnConnect zMsg, Object zListenerObj, String zListenerMethod) throws Exception {
		super(zListenerObj, zListenerMethod);
		this.setMessageToSubscribe(zMsg);
	}

}
