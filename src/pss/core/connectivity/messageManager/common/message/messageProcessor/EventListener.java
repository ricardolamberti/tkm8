package pss.core.connectivity.messageManager.common.message.messageProcessor;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import pss.core.connectivity.messageManager.common.message.IMessageContent;
import pss.core.tools.JExcepcion;
import pss.core.tools.PssLogger;

public class EventListener implements IEventListener {
	protected Object pListenerObj = null;
	protected String pListenerMethodName = null;
	protected Method pMethod = null;
	protected IMessageContent pEventMsg = null;
	
	public EventListener(Object zListenerObj, String zListenerMethod) throws Exception {
		if (this.setEventListenerMethod(zListenerObj, zListenerMethod) == false) {
			JExcepcion.SendError("No puede ser creado el listener " + this.getClass().getSimpleName() + " con el metodo " + zListenerMethod);
		}
  }
	
	@Override
	public Method getListenerMethod() {
		return this.pMethod;
	}

	@Override
	public String getListenerMethodName() {
		return pListenerMethodName;
	}

	@Override
	public Object getListenerObj() {
		return pListenerObj;
	}

	@SuppressWarnings("unchecked")
  @Override
	public boolean setEventListenerMethod(Object zListenerObj, String zListenerMethod) throws Exception {
		Class[] parameters = {IMessageContent.class};
		try {
//		pMethod = zListenerObj.getClass().getDeclaredMethod(zListenerMethod, parameters);
		pMethod = zListenerObj.getClass().getMethod(zListenerMethod, parameters);
		if (Modifier.isPublic(pMethod.getModifiers()) == false) {
			PssLogger.logError("Solo se pueden declarar \"Event Listener Methods\" que sean publicos");
			return false;
		}
		} catch (NoSuchMethodException eNSM) {
			PssLogger.logError("Error obteniendo metodo listener: " + eNSM.getMessage());
			return false;
		} catch (SecurityException eNSM) {
			PssLogger.logError("Error obteniendo metodo listener: " + eNSM.getMessage());
			return false;
		}
		
		this.pListenerObj = zListenerObj;
		this.pListenerMethodName = zListenerMethod;
		return true;
	}

	@Override
  public IMessageContent getMessageToSubscribe() {
	  return this.pEventMsg;
  }

	@Override
  public void setMessageToSubscribe(IMessageContent zMsg) {
	  this.pEventMsg = zMsg;
  }

	@Override
  public String getMsgId() {
	  return this.pEventMsg.getMessageId();
  }

	@Override
  public char getMsgType() {
	  return this.pEventMsg.getMessageType();
  }

}
