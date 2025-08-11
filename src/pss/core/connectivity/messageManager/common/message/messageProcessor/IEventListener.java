package pss.core.connectivity.messageManager.common.message.messageProcessor;

import java.lang.reflect.Method;

public interface IEventListener extends ISubscriber {
	public boolean setEventListenerMethod(Object zListenerObj, String zListenerMethod) throws Exception;
	public Object getListenerObj();
	public String getListenerMethodName();
	public Method getListenerMethod();
}
