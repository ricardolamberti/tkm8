package pss.core.connectivity.messageManager.common.message.messageProcessor;

import java.lang.reflect.Method;

public interface IRequestListener extends ISubscriber {
	public boolean setRequestProcessorMethod(Object zProcessorObj, String zProcessorMethod) throws Exception;
	public Object getProcessorObj();
	public String getProcessorMethodName();
	public Method getProcessorMethod();

}
