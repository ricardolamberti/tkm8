package pss.core.connectivity.messageManager.common.message.messageProcessor;

import java.lang.reflect.Method;

import pss.core.connectivity.messageManager.common.message.IMessageContent;

public interface IResponseListener extends ISubscriber {
	public boolean setResponseListenerMethod(Object zListenerObj, String zResponseListenerMethod, String zRespErrorListenerMethod) throws Exception;
	public Object getResponseListenerObj();
	public String getResponseListenerMethodName();
	public String getErrorRespListenerMethodName();
	public Method getResponseListenerMethod();
	public Method getErrorRespListenerMethod();
	public IMessageContent getMessageToSend();	
}
