package pss.core.connectivity.messageManager.client.connection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import pss.core.connectivity.messageManager.common.message.EvtError;
import pss.core.connectivity.messageManager.common.message.EvtError.EvtErrorCode;
import pss.core.connectivity.messageManager.common.message.IMessageContent;
import pss.core.connectivity.messageManager.common.message.MessageEnvelope;
import pss.core.connectivity.messageManager.common.message.MsgMngrExcepcion;
import pss.core.connectivity.messageManager.common.message.messageProcessor.IProcessMsg;
import pss.core.tools.PssLogger;

public class ClientProcessMsg implements IProcessMsg {
	public Object pObj;
	public Method pMet;

	@Override
	public IMessageContent processMessageContent(IMessageContent zMessage) throws Exception {
		Object[] parameters = { zMessage };
		try {
			return (IMessageContent) this.pMet.invoke(pObj, parameters);
		} catch (Exception e) {

			if ((e instanceof InvocationTargetException) && (((InvocationTargetException) e).getTargetException() instanceof InterruptedException)) {
				// PssLogger.logError(e);
				throw (InterruptedException) ((InvocationTargetException) e).getTargetException();
			}

			PssLogger.logError(e);
			EvtError oErrorMsg;
			MessageEnvelope oEnv = new MessageEnvelope();
			oEnv.setMsgContent(zMessage);
			oEnv.setMsgId(zMessage.getMessageId());
			oEnv.setMsgType(zMessage.getMessageType());
			if (e instanceof MsgMngrExcepcion) {
				MsgMngrExcepcion jexcep = (MsgMngrExcepcion) e;
				oErrorMsg = new EvtError(jexcep.getEvtErrorCode(), jexcep.getMessage(), oEnv);
			} else {
				oErrorMsg = new EvtError(EvtErrorCode.EC99998, e, oEnv);
			}
			return oErrorMsg;
		}
	}

}
