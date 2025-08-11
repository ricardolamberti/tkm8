package pss.core.connectivity.messageManager.common.message.messageProcessor;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import pss.core.connectivity.messageManager.common.message.IMessageContent;
import pss.core.connectivity.messageManager.common.message.MsgResponse;
import pss.core.tools.JExcepcion;
import pss.core.tools.PssLogger;

public class ResponseListener implements IResponseListener {
	protected Object pRespListenerObj = null;
	protected String pResponseListenerMethodName = null;
	protected String pErrorRespListenerMethodName = null;
	protected Method pResponseMethod = null;
	protected Method pErrorRespMethod = null;
	protected IMessageContent pResponseMsg;

	public ResponseListener(Object zListenerObj, String zResponseListenerMethod, String zRespErrorListenerMethod) throws Exception {
		if (this.setResponseListenerMethod(zListenerObj, zResponseListenerMethod, zRespErrorListenerMethod) == false) {
			JExcepcion.SendError("No puede ser creado el listener " + this.getClass().getSimpleName() + " con los metodos metodos " + zResponseListenerMethod + " , " + zRespErrorListenerMethod);
		}
	}

	@Override
	public Method getErrorRespListenerMethod() {
		return this.pErrorRespMethod;
	}

	@Override
	public String getErrorRespListenerMethodName() {
		return this.pErrorRespListenerMethodName;
	}

	@Override
	public Method getResponseListenerMethod() {
		return this.pResponseMethod;
	}

	@Override
	public String getResponseListenerMethodName() {
		return this.pResponseListenerMethodName;
	}

	@Override
	public Object getResponseListenerObj() {
		return this.pRespListenerObj;
	}

	@SuppressWarnings("unchecked")
  @Override
	public boolean setResponseListenerMethod(Object zListenerObj, String zResponseListenerMethod, String zRespErrorListenerMethod) throws Exception {
		Class[] parameters = { IMessageContent.class };
		try {
//			this.pResponseMethod = zListenerObj.getClass().getDeclaredMethod(zResponseListenerMethod, parameters);
			this.pResponseMethod = zListenerObj.getClass().getMethod(zResponseListenerMethod, parameters);
			if (Modifier.isPublic(pResponseMethod.getModifiers()) == false) {
				PssLogger.logError("Solo se pueden declarar \"Response Listener Methods\" que sean publicos");
				return false;
			}

			if (zRespErrorListenerMethod != null && !zRespErrorListenerMethod.isEmpty()) {
//				this.pErrorRespMethod = zListenerObj.getClass().getDeclaredMethod(zRespErrorListenerMethod, parameters);
				this.pErrorRespMethod = zListenerObj.getClass().getMethod(zRespErrorListenerMethod, parameters);
				if (Modifier.isPublic(pErrorRespMethod.getModifiers()) == false) {
					PssLogger.logError("Solo se pueden declarar \"Error Response Listener Methods\" que sean publicos");
					return false;
				}
			}
		} catch (NoSuchMethodException eNSM) {
			PssLogger.logError("Error obteniendo metodo listener: " + eNSM.getMessage());
			return false;
		} catch (SecurityException eNSM) {
			PssLogger.logError("Error obteniendo metodo listener: " + eNSM.getMessage());
			return false;
		}

		this.pRespListenerObj = zListenerObj;
		this.pResponseListenerMethodName = zResponseListenerMethod;
		this.pErrorRespListenerMethodName = zRespErrorListenerMethod;
		return true;
	}

	@Override
	public IMessageContent getMessageToSubscribe() {
		MsgResponse oMR = new MsgResponse(this.pResponseMsg);
		return oMR;
	}
	
	@Override
	public IMessageContent getMessageToSend() {
	  return this.pResponseMsg;
	}

	@Override
	public String getMsgId() {
		return this.pResponseMsg.getMessageId();
	}

	@Override
	public char getMsgType() {
		return this.pResponseMsg.getMessageType();
	}

	@Override
	public void setMessageToSubscribe(IMessageContent zMsg) {
		this.pResponseMsg = zMsg;
	}

}
