package pss.core.connectivity.messageManager.server.connection;

import java.util.Calendar;
import java.util.Iterator;
import java.util.Queue;

import pss.core.connectivity.messageManager.common.connection.BaseConnectionInstance;
import pss.core.connectivity.messageManager.common.message.EvtError;
import pss.core.connectivity.messageManager.common.message.EvtError.EvtErrorCode;
import pss.core.connectivity.messageManager.common.message.EvtMessage;
import pss.core.connectivity.messageManager.common.message.IMessageContent;
import pss.core.connectivity.messageManager.common.message.MessageEnvelope;
import pss.core.connectivity.messageManager.common.message.MsgResponse;
import pss.core.connectivity.messageManager.common.message.ReqMessage;
import pss.core.connectivity.messageManager.common.message.events.SysEvtOnDisconnect;
import pss.core.connectivity.messageManager.modules.addIns.IBaseServerAddInModule;
import pss.core.connectivity.messageManager.modules.login.BasicLoginMsgModule;
import pss.core.connectivity.messageManager.modules.login.ReqLogin;
import pss.core.connectivity.messageManager.modules.login.Response_Login;
import pss.core.connectivity.messageManager.server.MessageManager;
import pss.core.connectivity.messageManager.server.connectionManager.ConnectionsManager;
import pss.core.tools.JExcepcion;
import pss.core.tools.PssLogger;

public class ServerConnectionInstance extends BaseConnectionInstance {
	private ConnectionInstanceEchoCtrl echoCtrl = new ConnectionInstanceEchoCtrl();
	private BasicLoginMsgModule loginModule = BasicLoginMsgModule.getLoginModuleInstance();
	private ConnectionsManager connectionsMngr = null;
	protected String sessionId = null;
	protected String connectionName = null;

	public ServerConnectionInstance() throws Exception {
	}

	public String getSessionId() {
		return sessionId;
	}

	public String getConnectionName() {
		return connectionName;
	}

	@Override
	protected void checkEchoWaitingTime() throws Exception {
		if (this.pIsValidatedConnetion == false)
			return;

		if (echoCtrl.checkIfTimedOut() == false) {
			return;
		}

		switch (echoCtrl.getEchoWaitingStatus()) {
		case ConnectionInstanceEchoCtrl.ECHO_STATUS_TIME_WAITING:
			try {
				this.sendEchoMsg();
			} catch (Exception e) {
				PssLogger.logError(e, "Error intentando enviar mensaje de eco");
			}
			break;
		case ConnectionInstanceEchoCtrl.ECHO_STATUS_ANSWER_WAITING:
			if (MessageManager.bDebugMode == false) {
				PssLogger.logError("Timeout esperando respuesta de echo");
				//			} else {
				// JEchoCtrlExcepcion.SendError("Timeout esperando respuesta de echo");
			}
			echoCtrl.resetTimeWaiting(false);
			break;
		default:
			PssLogger.logError("Error chequeando echo time estado [" + echoCtrl.getEchoWaitingStatus() + "], reseteando valor a TIME_WAITING");
			echoCtrl.setToTimeWaitingStatus();
		}
	}

	@Override
	protected void onProcessed(MessageEnvelope msg) throws Exception {
		if (msg.getMsgType() == MessageEnvelope.MSG_TYPE_ECHO) {
			echoCtrl.setToAnswerWaitingStatus();
		}
	}

	@Override
	protected void onStart() throws Exception {
		echoCtrl.setToTimeWaitingStatus();
	}

	@Override
	protected void processEchoMsgType(MessageEnvelope msg) throws Exception {
		// PssLogger.logDebug("Se recibio respuesta de Echo");

	}

	@Override
	protected void processErrorMsgType(MessageEnvelope msg) throws Exception {
		BaseConnectionInstance oBCI = connectionsMngr.getConnectionInstanceByName(msg.getMsgTarget());
		if (oBCI == null) {
			return;
		}
		oBCI.addOutgoingMsg(msg);
	}

	@Override
	protected void processErrorMsgType(IMessageContent zMsgContent) throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	protected void processEventMsgType(MessageEnvelope msg) throws Exception {
		Queue<BaseConnectionInstance> eventsQueue;
		eventsQueue = connectionsMngr.getEventMsgConnInstance(msg.getMessageContent());

		if (eventsQueue == null) {
			return;
		}

		Iterator<BaseConnectionInstance> it = eventsQueue.iterator();
		BaseConnectionInstance oBCI;

		while (it.hasNext()) {
			oBCI = it.next();
			if (oBCI == null) {
				continue;
			}

			if (oBCI.getConnectionInstanceId().equals(msg.getMsgSource())) {
				continue;
			}

			oBCI.addOutgoingMsg(msg);
		}
	}

	@Override
	public void setConnectionInstanceName(String zConnectionInstanceName) {
		this.pConnectionInstanceName = zConnectionInstanceName;
	}

	@Override
	protected void processLoginErrorMsgType(MessageEnvelope zMsg) throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	protected void processLoginMsgType(MessageEnvelope msg) throws Exception {
		ReqLogin oReqLogin = (ReqLogin) msg.getMessageContent();

		if (oReqLogin.ifHasClientName() == false) {
			JExcepcion.SendError("Login sin Client Name");
		}

		if (oReqLogin.getPassword().isEmpty()) {
			JExcepcion.SendError("Error password vacio");
		}

		this.loginModule.validateConnection(oReqLogin.getPassword());

		pIsValidatedConnetion = true;
		Response_Login oResp = new Response_Login();
		oResp.setMsgId(msg.getMsgId());
		oResp.setResult("OK");
		this.connectionName = oReqLogin.getClientName();
		Thread.currentThread().setName("Server of " + oReqLogin.getClientName());

		if (oReqLogin.ifHasSessionId()) { // Es una reconexion
			this.sessionId = oReqLogin.getSessionId();
			PssLogger.logDebug("Tratando de reconectarse con Session ID [" + this.sessionId + "]");
			this.connectionsMngr.reconnect(this);
			oResp.setResponseMsg("Reconnection Login OK");
		} else { // Then is a new connection
			this.sessionId = getNewSessionId(oReqLogin.getClientName());
			PssLogger.logDebug("Generating Session ID [" + this.sessionId + "]");
			this.connectionsMngr.add(this);
			this.setConnectionInstanceName(oReqLogin.getClientName());
			oResp.setResponseMsg("New Login OK");
			oResp.setSessionId(this.sessionId);
		}
		this.addOutgoingMsg(oResp, msg.getMsgSource());
	}

	private String getNewSessionId(String zClientId) throws Exception {
		String sessionId = zClientId;
		sessionId += String.valueOf(Calendar.getInstance().getTimeInMillis());
		return sessionId;
	}

	@Override
	protected void processProcessMsg(MessageEnvelope msg) throws Exception {
		BaseConnectionInstance oBCI;

		if (msg.getMsgType() == MessageEnvelope.MSG_TYPE_REQUEST) {
			oBCI = connectionsMngr.getProcessMsgConnInstance(msg.getMessageContent());
		} else {
			oBCI = connectionsMngr.getConnectionInstanceByName(msg.getMsgTarget());
		}
		if (oBCI != null) {
			oBCI.addOutgoingMsg(msg);
			return;
		}
		if (msg.getMsgType() == MessageEnvelope.MSG_TYPE_REQUEST) {
			EvtError oEE = new EvtError(EvtErrorCode.EC10001, "Nadie procesa el mensaje [" + msg.getMsgId() + "] con el filtro especificado", msg);
			this.addOutgoingMsg(oEE, msg.getMsgSource());
		}
	}

	@Override
	protected void processRequestSubscribeMsgType(MessageEnvelope msg) throws Exception {
		connectionsMngr.subscribeToProcessMsg(this, msg.getMessageContent());
	}

	@Override
	protected void processEventSubscribeMsgType(MessageEnvelope msg) throws Exception {
		connectionsMngr.subscribeToEventMsg(this, msg.getMessageContent());
	}

	@Override
	protected void processSystemEventSubscribeMsgType(MessageEnvelope msg) throws Exception {
		connectionsMngr.subscribeToSystemEventMsg(this, msg.getMessageContent());
	}

	@Override
	protected void processSystemEventMsgType(MessageEnvelope msg) throws Exception {
		Queue<BaseConnectionInstance> eventsQueue;
		eventsQueue = connectionsMngr.getSystemEventMsgConnInstance(msg.getMessageContent());

		if (eventsQueue == null) {
			return;
		}

		Iterator<BaseConnectionInstance> oIt = eventsQueue.iterator();
		BaseConnectionInstance oBCI;

		while (oIt.hasNext()) {
			oBCI = oIt.next();
			if (oBCI == null) {
				continue;
			}
			if (oBCI.getConnectionInstanceId().equals(msg.getMsgSource())) {
				continue;
			}
			oBCI.addOutgoingMsg(msg);
		}
	}

	@Override
	protected void resetEchoWaitingTime() throws Exception {
		this.echoCtrl.resetTimeWaiting();
	}

	/**
	 * @return the pConnectionsMngr
	 */
	public ConnectionsManager getConnectionsMngr() {
		return connectionsMngr;
	}

	/**
	 * @param zConnectionsMngr
	 *          the pConnectionsMngr to set
	 */
	public void setConnectionsMngr(ConnectionsManager zConnectionsMngr) {
		connectionsMngr = zConnectionsMngr;
	}

	@Override
	protected boolean onPreProcessMsg(MessageEnvelope zMsg) throws Exception {
		IMessageContent mc = zMsg.getMessageContent();

		// Obviamente el mensaje debe tener contenido para poder ser preprosable
		if (mc == null) {
			return true;
		}

		// Solo los eventos, requests y respuestas pueden ser preprocesados.
		if ((mc instanceof EvtMessage) == false &&
				(mc instanceof MsgResponse) == false &&
				(mc instanceof ReqMessage) == false) {
			return true;
		}
		
		Queue<IBaseServerAddInModule> addinQueue;

		addinQueue = this.connectionsMngr.getAddInSubscriptionList(mc);

		if (addinQueue == null) {
			return true;
		}

		Iterator<IBaseServerAddInModule> it = addinQueue.iterator();
		IBaseServerAddInModule oAddIn;

		while (it.hasNext()) {
			oAddIn = it.next();
			if (oAddIn == null) {
				continue;
			}

			if (!oAddIn.processMsg(zMsg)) {
				PssLogger.logDebug("Por pedido del Addin [" + oAddIn.getAddinName() + "] Se va a descartar el mensaje :" + zMsg.dumpMessage());
				return false;
			}
		}

		return true;
	}

	@Override
	protected void onFinishShadow() {
		try {
			SysEvtOnDisconnect oEvt = new SysEvtOnDisconnect();
			oEvt.setModuleName(getConnectionInstanceId());
			MessageEnvelope oME = new MessageEnvelope();
			oME.setMsgContent(oEvt);
			oME.setMsgSource(getConnectionInstanceId());
			oME.setMsgType(oEvt.getMessageType());
			oME.setMsgId(oEvt.getMessageId());
			processSystemEventMsgType(oME);
			// this.connectionsMngr.remove(this);
			super.onFinishShadow();
		} catch (Exception e) {
			PssLogger.logError(e, "[" + Thread.currentThread().getName() + "]");
		}
	}
}
