package pss.core.connectivity.messageManager.client.connection;

import java.util.Iterator;
import java.util.Queue;

import pss.core.JAplicacion;
import pss.core.connectivity.messageManager.common.connection.BaseConnectionInstance;
import pss.core.connectivity.messageManager.common.message.EvtError;
import pss.core.connectivity.messageManager.common.message.IMessageContent;
import pss.core.connectivity.messageManager.common.message.MessageEnvelope;
import pss.core.connectivity.messageManager.common.message.ReqSubscribe;
import pss.core.connectivity.messageManager.common.message.messageProcessor.IProcessMsg;
import pss.core.connectivity.messageManager.modules.login.ReqLogin;
import pss.core.connectivity.messageManager.modules.login.Response_Login;
import pss.core.tools.PssLogger;

public class ClientConnectionInstance extends BaseConnectionInstance {
	// private MessageProcessMap pProcessMsgMap = null;
	// private MessageProcessMap pAnswerMsgMap = null;
	// private MessageProcessMap pListenEventsMap = null;
	// private MessageProcessMap pListenSystemEventsMap = null;
	// private MessageProcessMap pErrorEventsMap = null;
	// private String sessionId = null;
	MsgClientConnection msgClientConnection = null;

	// este metodo tiene que estar protected, lo pongo asi solo para probar
	public ClientConnectionInstance(MsgClientConnection msgClientConnection) {
		this.msgClientConnection = msgClientConnection;
	}

	/**
	 * Resetea el cliente
	 * 
	 * @see pss.core.connectivity.messageManager.common.connection.BaseConnectionInstance#resetEchoWaitingTime()
	 */
	protected final void resetEchoWaitingTime() throws Exception {

	}

	protected final void checkEchoWaitingTime() throws Exception {

	}

	@Override
	protected void onProcessed(MessageEnvelope msg) throws Exception {
		// TODO Auto-generated method stub

	}

	protected void sendLoginMsg() throws Exception {
		ReqLogin oMsg = new ReqLogin();
		oMsg.setClientName(this.getConnectionInstanceId());
		if (this.msgClientConnection.ifHasSessionId()) {
			oMsg.setSessionId(this.msgClientConnection.getSessionId());
		}
		addOutgoingMsg(oMsg);
	}

	@Override
	protected void onStart() throws Exception {
		if (this.msgClientConnection.isOpenDatabaseConnection()) {
			JAplicacion.openSession();
			JAplicacion.GetApp().openApp("Thread" + Thread.currentThread().getId(), JAplicacion.AppTipoThread(), true);
		}
		this.sendLoginMsg();
	}

	@Override
	protected void onFinishShadow() {
		if (this.msgClientConnection.isOpenDatabaseConnection()) {
			JAplicacion.closeSession();
			try {
				JAplicacion.GetApp().closeApp();
			} catch (Exception e) {
				PssLogger.logError(e, "Error closing app");
			}
		}
		super.onFinishShadow();
	}

	@Override
	protected void processEchoMsgType(MessageEnvelope msg) throws Exception {
		sendEchoMsg();
	}

	@Override
	protected void processErrorMsgType(MessageEnvelope msg) throws Exception {
		if (msg.getMessageContent() != null)
			processErrorMsgType(msg.getMessageContent());
	}

	@Override
	protected void processErrorMsgType(IMessageContent zMsgContent) throws Exception {
		PssLogger.logError("Mensaje de error:" + zMsgContent.dumpMessage());

		// if (pErrorEventsMap == null) {
		if (this.msgClientConnection == null) {
			PssLogger.logError("No esta configurado el mapa de procesamiento de error");
		}

		IProcessMsg oProcMsg;

		// Busco el procesador especifico para un mensaje determinado
		if (zMsgContent instanceof EvtError) {
			oProcMsg = this.msgClientConnection.getErrorEventsMap().getProcEntryByMsg(((EvtError) zMsgContent).getMsgEnvWithError().getMessageContent());
		} else {
			oProcMsg = this.msgClientConnection.getErrorEventsMap().getProcEntryByMsg(zMsgContent);
		}
		// if (oProcMsg == null) {
		// // Si no existe un procesador especific, busco un procesador generico
		// oProcMsg = this.pErrorEventsMap.getProcEntryByMsg();

		if (oProcMsg == null) {
			PssLogger.logDebug("WARNING: No hay ningun proceso configurado para procesar mensajes de error");
			return;
		}
		// }

		oProcMsg.processMessageContent(zMsgContent);
	}

	@Override
	protected void processEventMsgType(MessageEnvelope msg) throws Exception {
		IProcessMsg oProc;
		oProc = this.msgClientConnection.getListenEventsMap().getProcEntryByMsg(msg.getMessageContent());
		if (oProc == null) {
			PssLogger.logError("El message id [" + msg.getMsgId() + "] no puede ser procesado por este cliente.");
			return;
		}
		oProc.processMessageContent(msg.getMessageContent());
	}

	protected void sendPendingSubscriptions() throws Exception {
		this.sendPendingSubscriptions(MessageEnvelope.MSG_TYPE_REQUEST_SUBSCRIBE, this.msgClientConnection.getProcessMsgMap());
		this.sendPendingSubscriptions(MessageEnvelope.MSG_TYPE_EVENT_SUBSCRIBE, this.msgClientConnection.getListenEventsMap());
		this.sendPendingSubscriptions(MessageEnvelope.MSG_TYPE_SYSTEM_EVENT_SUBSCRIBE, this.msgClientConnection.getListenSystemEventsMap());
		this.sendPendingSubscriptions(MessageEnvelope.MSG_TYPE_ERROR, this.msgClientConnection.getErrorEventsMap());
	}

	@Override
	protected void processLoginErrorMsgType(MessageEnvelope zMsg) throws Exception {
		if (this.msgClientConnection.ifHasSessionId()) {
			this.msgClientConnection.setSessionId("");
		} else {
			Thread.sleep(5000); // Wait 5 seg to retry
		}
		this.sendLoginMsg();
	}

	@Override
	protected void processLoginMsgType(MessageEnvelope msg) throws Exception {
		pIsValidatedConnetion = true;

		if (this.msgClientConnection.ifHasSessionId() == false) {
			this.sendPendingSubscriptions();
		}

		Response_Login oRespLogin = (Response_Login) msg.getMessageContent();
		if (oRespLogin.getSessionId() != null) {
			this.msgClientConnection.setSessionId(oRespLogin.getSessionId());
		}
	}

	protected void sendPendingSubscriptions(char zMsgType, MessageProcessMap zProcMap) throws Exception {
		Queue<IMessageContent> qMC;
		Iterator<IMessageContent> oIt;
		IMessageContent msg;

		if (zProcMap != null) {
			qMC = zProcMap.getSubscriptedMsgList();
			oIt = qMC.iterator();

			while (oIt.hasNext()) {
				msg = oIt.next();

				ReqSubscribe oMsgContent = new ReqSubscribe();
				// oMsgContent.setSubscribeMsgType(zMsgType);
				// oMsgContent.setSubscribeMsgId(strMsgId);
				oMsgContent.setMsgContent(msg);
				this.addOutgoingMsg(oMsgContent);
			} // end while
		} // end if
	}

	@Override
	protected void processProcessMsg(MessageEnvelope msg) throws Exception {
		MessageProcessMap oMsgProcMap;
		IMessageContent oMC;

		if (msg.getMsgType() == MessageEnvelope.MSG_TYPE_REQUEST) {
			oMsgProcMap = this.msgClientConnection.getProcessMsgMap();
		} else {
			oMsgProcMap = this.msgClientConnection.getAnswerMsgMap();
		}

		oMC = msg.getMessageContent();

		IProcessMsg oProc;
		oProc = oMsgProcMap.getProcEntryByMsg(oMC);
		if (oProc == null) {
			PssLogger.logError("El message id [" + msg.getMsgId() + "] no puede ser procesado por este cliente.");
			return;
		}
		IMessageContent rtaContent = oProc.processMessageContent(msg.getMessageContent());
		if (rtaContent != null) {
			this.addOutgoingMsg(rtaContent, msg.getMsgSource());
		}
	}

	@Override
	protected void processRequestSubscribeMsgType(MessageEnvelope msg) throws Exception {
		// TODO : Aca hay que recibir los OK

	}

	@Override
	protected void processEventSubscribeMsgType(MessageEnvelope msg) throws Exception {
		// TODO : Aca hay que recibir los OK
	}

	@Override
	protected void processSystemEventSubscribeMsgType(MessageEnvelope msg) throws Exception {
		// TODO : Aca hay que recibir los OK

	}

	@Override
	protected void processSystemEventMsgType(MessageEnvelope msg) throws Exception {
		IProcessMsg oProc;
		oProc = this.msgClientConnection.getListenSystemEventsMap().getProcEntryByMsg(msg.getMessageContent());
		if (oProc == null) {
			PssLogger.logError("El message id [" + msg.getMsgId() + "] no puede ser procesado por este cliente.");
			return;
		}
		oProc.processMessageContent(msg.getMessageContent());
	}

	// /**
	// * @return the pProcessMsgMap
	// */
	// public MessageProcessMap getProcessMsgMap() {
	// return pProcessMsgMap;
	// }
	//
	// /**
	// * @param processMsgMap
	// * the pProcessMsgMap to set
	// */
	// public void setProcessMsgMap(MessageProcessMap processMsgMap) {
	// pProcessMsgMap = processMsgMap;
	// }
	//
	// /**
	// * @return the pListenEventsMap
	// */
	// public MessageProcessMap getListenEventsMap() {
	// return pListenEventsMap;
	// }
	//
	// /**
	// * @param listenEventsMap
	// * the pListenEventsMap to set
	// */
	// public void setListenEventsMap(MessageProcessMap listenEventsMap) {
	// pListenEventsMap = listenEventsMap;
	// }
	//
	// /**
	// * @return the pListenSystemEventsMap
	// */
	// public MessageProcessMap getListenSystemEventsMap() {
	// return pListenSystemEventsMap;
	// }
	//
	// /**
	// * @param listenSystemEventsMap
	// * the pListenSystemEventsMap to set
	// */
	// public void setListenSystemEventsMap(MessageProcessMap listenSystemEventsMap) {
	// pListenSystemEventsMap = listenSystemEventsMap;
	// }
	//
	// /**
	// * @return the pErrorEventsMap
	// */
	// public MessageProcessMap getErrorEventsMap() {
	// return pErrorEventsMap;
	// }
	//
	// /**
	// * @param errorEventsMap
	// * the pErrorEventsMap to set
	// */
	// public void setErrorEventsMap(MessageProcessMap errorEventsMap) {
	// pErrorEventsMap = errorEventsMap;
	// }
	//
	// /**
	// * @return the pAnswerMsgMap
	// */
	// public MessageProcessMap getAnswerMsgMap() {
	// return pAnswerMsgMap;
	// }
	//
	// /**
	// * @param answerMsgMap
	// * the pAnswerMsgMap to set
	// */
	// public void setAnswerMsgMap(MessageProcessMap answerMsgMap) {
	// pAnswerMsgMap = answerMsgMap;
	// }

	@Override
	protected boolean onPreProcessMsg(MessageEnvelope zMsg) throws Exception {
		return true;
	}

}
