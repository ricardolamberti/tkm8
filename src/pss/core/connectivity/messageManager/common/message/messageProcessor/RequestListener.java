package pss.core.connectivity.messageManager.common.message.messageProcessor;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import pss.core.connectivity.messageManager.common.message.IMessageContent;
import pss.core.tools.JExcepcion;
import pss.core.tools.PssLogger;

public class RequestListener implements IRequestListener {
	protected Object pProcessorObj = null;
	protected String pProcessorMethodName = null;
	protected Method pMethod = null;
	protected IMessageContent pRequestMsg;
	
	public RequestListener(Object zProcessorObj, String zProcessorMethod) throws Exception {
		if (this.setRequestProcessorMethod(zProcessorObj, zProcessorMethod) == false) {
			JExcepcion.SendError("No puede ser creado el RequestProcessor " + this.getClass().getSimpleName() + " con el metodo " + zProcessorMethod);
		}
  }
	
	public RequestListener(IMessageContent zMsg, Object zProcessorObj, String zProcessorMethod) throws Exception {
		this(zProcessorObj,zProcessorMethod);
		this.setMessageToSubscribe(zMsg);
  }
	
	@Override
	public Method getProcessorMethod() {
		return this.pMethod;
	}

	@Override
	public String getProcessorMethodName() {
		return pProcessorMethodName;
	}

	@Override
	public Object getProcessorObj() {
		return pProcessorObj;
	}

	@SuppressWarnings("unchecked")
  @Override
	public boolean setRequestProcessorMethod(Object zProcessorObj, String zProcessorMethod) throws Exception {
		Class[] parameters = {IMessageContent.class};
		try {
//		pMethod = zProcessorObj.getClass().getDeclaredMethod(zProcessorMethod, parameters);
		pMethod = zProcessorObj.getClass().getMethod(zProcessorMethod, parameters);
		if (Modifier.isPublic(pMethod.getModifiers()) == false) {
			PssLogger.logError("Solo se pueden declarar \"Request Processor Methods\" que sean publicos");
			return false;
		}
		} catch (NoSuchMethodException eNSM) {
			PssLogger.logError("Error obteniendo metodo : " + eNSM.getMessage());
			return false;
		} catch (SecurityException eNSM) {
			PssLogger.logError("Error obteniendo metodo : " + eNSM.getMessage());
			return false;
		}
		
		this.pProcessorObj = zProcessorObj;
		this.pProcessorMethodName = zProcessorMethod;
		return true;
	}

	@Override
	public IMessageContent getMessageToSubscribe() {
		return this.pRequestMsg;
	}

	@Override
	public String getMsgId() {
		return this.pRequestMsg.getMessageId();
	}

	@Override
	public char getMsgType() {
		return this.pRequestMsg.getMessageType();
	}

	@Override
	public void setMessageToSubscribe(IMessageContent zMsg) {
		this.pRequestMsg = zMsg;
	}

}
