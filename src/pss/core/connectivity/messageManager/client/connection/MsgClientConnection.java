package pss.core.connectivity.messageManager.client.connection;

import pss.core.connectivity.messageManager.client.connectionChannel.IClientConnectionChannel;
import pss.core.connectivity.messageManager.client.connectionChannel.LocalClientConnectionChannel;
import pss.core.connectivity.messageManager.client.connectionChannel.TcpClientConnectionChannel;
import pss.core.connectivity.messageManager.common.message.EvtError;
import pss.core.connectivity.messageManager.common.message.IMessageContent;
import pss.core.connectivity.messageManager.common.message.MessageEnvelope;
import pss.core.connectivity.messageManager.common.message.MsgResponse;
import pss.core.connectivity.messageManager.common.message.ReqSubscribe;
import pss.core.connectivity.messageManager.common.message.events.SysEvtOnConnect;
import pss.core.connectivity.messageManager.common.message.events.SysEvtOnDisconnect;
import pss.core.connectivity.messageManager.common.message.messageProcessor.ErrorEventListener;
import pss.core.connectivity.messageManager.common.message.messageProcessor.IEventListener;
import pss.core.connectivity.messageManager.common.message.messageProcessor.IProcessMsg;
import pss.core.connectivity.messageManager.common.message.messageProcessor.IRequestListener;
import pss.core.connectivity.messageManager.common.message.messageProcessor.IResponseListener;
import pss.core.tools.JExcepcion;
import pss.core.tools.PssLogger;

// Aca falta agregar metodos para enviar el mensaje a la vez que me subscribo a sus respuestas
// 

public class MsgClientConnection {
	private MessageProcessMap processMsgMap = null;
	private MessageProcessMap answerMsgMap = null;
	private MessageProcessMap listenEventsMap = null;
	private MessageProcessMap listenSystemEventsMap = null;
	private MessageProcessMap errorEventsMap = null;
	private String clientConnectionName = new String("");
	private String connectionString = null;

	private IClientConnectionChannel connectionChannel = null;
	private Thread clientThread = null;
	private ClientConnectionInstance clientConnectionInstance = null;
	boolean openDatabaseConnection = false;

	protected MsgClientConnection() {
	}

	public MsgClientConnection(String zClientName) {
		clientConnectionName = zClientName;
	}

	public void setClientConnectionName(String clientConnectionName) {
		this.clientConnectionName = clientConnectionName;
	}

	public void setConnectionString(String connectionString) {
		this.connectionString = connectionString;
	}

	/**
	 * Mantiene el thread vivo hasta que es interrumpido.Si se desconecta de server, intenta reconectarse automaticamente.
	 * Solo se interrumpe si
	 */
	private void runMsgClientThread() {
		while (true) {
			try {
				if (Thread.currentThread().isInterrupted()) {
					PssLogger.logDebug("The current thread has been interrupted");
					break;
				}

				if (this.connectionChannel == null) {
					this.connectionChannel = getClientConnectionChannel(this.connectionString);
				}

				if (connectionChannel.isConnected() == false) {
					if (!connectionChannel.connect(this.connectionString)) {
						Thread.sleep(100);
						continue;
					}
				}

				// Creo y seteo la instancia de conexion del cliente con el servidor
				this.clientConnectionInstance = new ClientConnectionInstance(this);
				this.clientConnectionInstance.setConnectionChannel(connectionChannel);
				this.clientConnectionInstance.setConnectionInstanceName(clientConnectionName);
				this.clientConnectionInstance.launchConnectionInstanceShadow();

				// Espera a que la instancia de conexion del cliente este realmente
				// conectado
				while (true) {
					if (Thread.currentThread().isInterrupted())
						break;
					if (this.clientConnectionInstance.ifConnected())
						break;
					Thread.sleep(50);
				} // end while

				// Dispara el trigger que ahora si estoy realmente conectado con el
				// servidor
				this.onLocalConnect();

				// Controla que la instancia de conexion este conectada y que no se haya
				// solicitado la
				// interrupcion del thread actual.
				while (true) {
					if (clientConnectionInstance.ifConnected() == false)
						break;

					if (Thread.currentThread().isInterrupted())
						break;

					Thread.sleep(100);
				}

				this.connectionChannel = null;

			} catch (InterruptedException i) {
				PssLogger.logDebug("Se interrumpio el procesamiento del cliente [" + Thread.currentThread().getName() + "]");
				break;
			} catch (Exception e) {
				PssLogger.logError(e, "Error en cliente [" + Thread.currentThread().getName() + "]");
			} // end try/catch
		} // end while true
		// Si se desconecto del sevidor, disparo el trigger que ahora estoy
		// desconectado

		try {
			if (clientConnectionInstance.ifConnected() == false) {
				this.onLocalDisconnect();
			} else {
				this.clientConnectionInstance.stopConnectionInstanceShadow();
			}
		} catch (Exception e) {
			PssLogger.logError(e, "Error closing this client [" + Thread.currentThread().getName() + "]");
		}

	} // end runMsgClientThread

	public void connect(String zConnectionString) throws Exception {
		// this.connectionChannel = getClientConnectionChannel(zConnectionString);
		if (zConnectionString != null)
			this.connectionString = zConnectionString;

		Runnable runMethod = new Runnable() {
			public void run() {
				runMsgClientThread();
			}
		};

		clientThread = new Thread(runMethod);
		if (this.clientConnectionName != null && this.clientConnectionName.isEmpty() == false) {
			clientThread.setName("MsgClientConnection of [" + this.clientConnectionName + "]");
		} else {
			clientThread.setName("MsgClientConnection of [ Unknown ]");
		}

		clientThread.start();

		PssLogger.logDebug("MsgClient has been launched");
	}

	private static boolean checkIpAddressFormat(String zIpAddress) {
		try {
			String strAux = zIpAddress;
			int iPos;
			int iValue;

			// Check de first three octects
			for (long lCount = 1; lCount <= 3; ++lCount) {
				iPos = strAux.indexOf('.');
				if (iPos == -1)
					return false;
				iValue = Integer.valueOf(strAux.substring(0, iPos));
				if (iValue < 0 || iValue > 255)
					return false;

				strAux = strAux.substring(iPos + 1);
			} // end for

			// Check the fourth octect
			iPos = strAux.indexOf(':');
			if (iPos == -1)
				return false;
			iValue = Integer.valueOf(strAux.substring(0, iPos));
			if (iValue <= 0 || iValue >= 255)
				return false;
			strAux = strAux.substring(iPos + 1);

			// Check the socket port
			iValue = Integer.valueOf(strAux.substring(0, iPos));
			if (iValue <= 0 || iValue >= 65535)
				return false;

			return true;

		} catch (Exception e) {
			PssLogger.logError(e, "Se produjo el siguiente error chequeando la direccion IP [" + zIpAddress + "]");
			return false;
		}
	}

	public static IClientConnectionChannel getClientConnectionChannel(String zConnectionString) throws Exception {
		if (zConnectionString.compareToIgnoreCase("LOCAL") == 0) {
			return new LocalClientConnectionChannel();
		}

		if (checkIpAddressFormat(zConnectionString)) {
			return new TcpClientConnectionChannel();
		}

		JExcepcion.SendError("El siguiente canal de conexion [" + zConnectionString + "] no puede ser utilizado en ningun canal de comunicaion");
		return null;
	}

	// TODO : Ver si puedo hacer un metodo solo que se llame subscribe que el
	// resuelva solo la subscripcion en lugar de tener 20 APIs diferentes

	public void subscribeToEvent(IEventListener zEventListener) throws Exception {
		ClientProcessMsg oProc = new ClientProcessMsg();
		oProc.pObj = zEventListener.getListenerObj();
		oProc.pMet = zEventListener.getListenerMethod();
		if (ErrorEventListener.class.isInstance(zEventListener)) {
			subscribeToErrorMsg(zEventListener);
			return;
		}
		subscribeToMsg(zEventListener.getMessageToSubscribe(), oProc);
	}

	// public void subscribeToMsg(char zMsgType, String zMsgId, IProcessMsg zProcessMsgClass) throws Exception {
	public void subscribeToMsg(IMessageContent zMsg, IProcessMsg zProcessMsgClass) throws Exception {
		char zMsgType = zMsg.getMessageType();

		switch (zMsgType) {
		// Este mensaje de subscripcion es para inscribir el punto de procesamiento
		// de las respuestas,
		// no hay que avisar al server.
		// case MessageEnvelope.MSG_TYPE_RESPONSE:
		// this.pAnswerMsgMap.subscribeToProcessList(zMsg, zProcessMsgClass);
		// return; // Sale directamente, no es necesario continuar se resuelve a
		// // nivel local.
		// case MessageEnvelope.MSG_TYPE_ERROR:
		// this.pErrorEventsMap.subscribeToProcessList(zMsg, zProcessMsgClass);
		// return; // Sale directamente, no es necesario continuar se resuelve a
		// // nivel local.
		case MessageEnvelope.MSG_TYPE_REQUEST:
			zMsgType = MessageEnvelope.MSG_TYPE_REQUEST_SUBSCRIBE;
		case MessageEnvelope.MSG_TYPE_REQUEST_SUBSCRIBE:
			this.getProcessMsgMap().subscribeToProcessList(zMsg, zProcessMsgClass);
			break;
		case MessageEnvelope.MSG_TYPE_EVENT:
			zMsgType = MessageEnvelope.MSG_TYPE_EVENT_SUBSCRIBE;
		case MessageEnvelope.MSG_TYPE_EVENT_SUBSCRIBE:
			this.getListenEventsMap().subscribeToProcessList(zMsg, zProcessMsgClass);
			break;
		case MessageEnvelope.MSG_TYPE_SYSTEM_EVENT:
			zMsgType = MessageEnvelope.MSG_TYPE_SYSTEM_EVENT_SUBSCRIBE;
		case MessageEnvelope.MSG_TYPE_SYSTEM_EVENT_SUBSCRIBE:
			this.getListenSystemEventsMap().subscribeToProcessList(zMsg, zProcessMsgClass);
			break;
		default:
			JExcepcion.SendError("El tipo de mensaje [" + zMsgType + "][" + MessageEnvelope.getMessageTypeDescription(zMsgType) + "]");
		}

		if (this.clientConnectionInstance == null) {
			return;
		}

		if (this.clientConnectionInstance.ifConnected() == false) {
			return;
		}

		ReqSubscribe oMsgContent = new ReqSubscribe();
		// oMsgContent.setSubscribeMsgType(zMsgType);
		// oMsgContent.setSubscribeMsgId(zMsgId);
		oMsgContent.setMsgContent(zMsg);

		this.sendMessage(oMsgContent);
	}

	public void subscribeToEventMsg(IMessageContent zMsg, IProcessMsg zProcessMsgClass) throws Exception {
		subscribeToMsg(zMsg, zProcessMsgClass);
	}

	public void subscribeToRequestMsg(IRequestListener zRequestListener) throws Exception {
		ClientProcessMsg oProc = new ClientProcessMsg();
		oProc.pObj = zRequestListener.getProcessorObj();
		oProc.pMet = zRequestListener.getProcessorMethod();
		subscribeToMsg(zRequestListener.getMessageToSubscribe(), oProc);
	}

	public void subscribeToRequestMsg(IMessageContent zMsg, IProcessMsg zProcessMsgClass) throws Exception {
		subscribeToMsg(zMsg, zProcessMsgClass);
	}

	public void subscribeToErrorMsg(IMessageContent zMsg, IProcessMsg zProcessErrorMsg) throws Exception {
		this.getErrorEventsMap().subscribeToProcessList(zMsg, zProcessErrorMsg);
	}

	public void subscribeToErrorMsg(IEventListener zEventListener) throws Exception {
		ClientProcessMsg oProc = new ClientProcessMsg();
		oProc.pObj = zEventListener.getListenerObj();
		oProc.pMet = zEventListener.getListenerMethod();
		this.getErrorEventsMap().subscribeToProcessList(zEventListener.getMessageToSubscribe(), oProc);
	}

	public void subscribeToErrorMsg(IProcessMsg zProcessErrorMsg) throws Exception {
		EvtError oEvtError = new EvtError();
		// subscribeToMsg(oEvtError, zProcessErrorMsg);
		this.getErrorEventsMap().subscribeToProcessList(oEvtError, zProcessErrorMsg);
	}

	// public void subscribeToOnConnect(IProcessMsg zProcessMsgClass) throws
	// Exception {
	// subscribeToThis(SysEvtOnConnect.class, zProcessMsgClass);
	// }

	public void subscribeToAnswer(IMessageContent zMsg, IProcessMsg zProcessMsgClass) throws Exception {
		MsgResponse oMR = new MsgResponse(zMsg);
		this.getAnswerMsgMap().subscribeToProcessList(oMR, zProcessMsgClass);
	}

	public void subscribeToAnswer(IResponseListener zResponseListener) throws Exception {
		ClientProcessMsg oProc = new ClientProcessMsg();
		oProc.pObj = zResponseListener.getResponseListenerObj();
		oProc.pMet = zResponseListener.getResponseListenerMethod();
		// subscribeToMsg(zResponseListener.getMessageToSubscribe(), oProc);
		this.getAnswerMsgMap().subscribeToProcessList(zResponseListener.getMessageToSubscribe(), oProc);

		if (zResponseListener.getErrorRespListenerMethod() != null) {
			ClientProcessMsg oErrorProc = new ClientProcessMsg();
			oErrorProc.pObj = zResponseListener.getResponseListenerObj();
			oErrorProc.pMet = zResponseListener.getErrorRespListenerMethod();
			// subscribeToMsg(zResponseListener.getMessageToSubscribe(), oErrorProc);
			this.getErrorEventsMap().subscribeToProcessList(zResponseListener.getMessageToSubscribe(), oErrorProc);
		}
	}

	// public void subscribeToThis(Class<? extends IMessageContent> zMsgContent, IProcessMsg zProcessMsgClass) throws
	// Exception {
	// IMessageContent oMsgContent = zMsgContent.newInstance();
	// subscribeToMsg(oMsgContent.getMessageType(), oMsgContent.getMessageId(), zProcessMsgClass);
	// }

	// public void subscribeToDisconnect(IProcessMsg zProcessMsgClass) throws
	// Exception {
	// subscribeToThis(SysEvtOnDisconnect.class, zProcessMsgClass);
	// }

	public void sendMessage(IMessageContent zMsgContent) throws Exception {
		this.sendMessage(zMsgContent, null, null, null);
	}

	public void sendMessage(IMessageContent zMsgContent, String zDestination) throws Exception {
		this.sendMessage(zMsgContent, zDestination, null, null);
	}

	public void sendMessage(IResponseListener zResponseListener) throws Exception {
		this.subscribeToAnswer(zResponseListener);
		this.sendMessage(zResponseListener.getMessageToSend());
	}

	public void sendMessage(IMessageContent zMsgContent, String zDestination, IProcessMsg zSubscribeToAnswer, IProcessMsg zSubscribeToError) throws Exception {
		// try {
		if (zSubscribeToError != null) {
			subscribeToErrorMsg(zMsgContent, zSubscribeToError);
		}
		if (zSubscribeToAnswer != null) {
			subscribeToAnswer(zMsgContent, zSubscribeToAnswer);
		}
		if (clientConnectionInstance == null) {
			JExcepcion.SendError("La instancia de conexion todavia no esta lista");
		}
		zMsgContent.setSourceSessionId(this.sessionId);
		this.clientConnectionInstance.addOutgoingMsg(zMsgContent, zDestination);
		// } catch (Exception e) {
		// String strLog = "MsgType [" + zMsgContent.getMessageType() + "] ";
		// strLog += "MsgId [" + zMsgContent.getMessageId() + "]";
		// PssLogger.logError(e, "Error sending " + strLog);
		// throw e;
		// }
	}

	public void sendResponse(IMessageContent zMsgContent) throws Exception {
		if (this.clientConnectionInstance.getLastMessageProcessed() == null) {
			JExcepcion.SendError("No hay un Mensaje en proceso valido");
		}

		this.sendMessage(zMsgContent, this.clientConnectionInstance.getLastMessageProcessed().getMsgSource());
	}

	public void sendMessage(IMessageContent zMsgContent, String zDestination, IProcessMsg zSubscribeToAnswer) throws Exception {
		this.sendMessage(zMsgContent, zDestination, zSubscribeToAnswer, null);
	}

	public void sendMessage(IMessageContent zMsgContent, IProcessMsg zSubscribeToAnswer, IProcessMsg zSubscribeToError) throws Exception {
		this.sendMessage(zMsgContent, "", zSubscribeToAnswer, zSubscribeToError);
	}

	public void sendMessage(IMessageContent zMsgContent, IProcessMsg zSubscribeToAnswer) throws Exception {
		this.sendMessage(zMsgContent, zSubscribeToAnswer, null);
	}

	public boolean ifConnected() {
		if (this.clientThread == null || this.clientConnectionInstance == null) {
			return false;
		}

		if (this.clientThread.isAlive() == false) {
			return false;
		}

		if (this.clientConnectionInstance.ifConnected() == false) {
			return false;
		}

		return true;
	}

	public void disconnect() throws Exception {
		while (true) {
			this.clientThread.interrupt();
			this.clientThread.join(100);

			if (this.clientThread.isAlive() == false) {
				break;
			}
		} // end while
	}

	protected void onLocalConnect() throws Exception {
		SysEvtOnConnect oMsg = new SysEvtOnConnect();
		oMsg.setModuleName(this.clientConnectionName);
		this.sendMessage(oMsg);
		IProcessMsg zProcMsgClass = this.getListenSystemEventsMap().getProcEntryByMsg(oMsg);
		if (zProcMsgClass == null) {
			return;
		}
		zProcMsgClass.processMessageContent(oMsg);
	}

	protected void onLocalDisconnect() throws Exception {
		SysEvtOnDisconnect oMsg = new SysEvtOnDisconnect();
		oMsg.setModuleName(this.clientConnectionName);
		IProcessMsg zProcMsgClass = this.getListenSystemEventsMap().getProcEntryByMsg(oMsg);
		if (zProcMsgClass == null) {
			return;
		}
		zProcMsgClass.processMessageContent(oMsg);
	}

	public String getClientConnectionName() {
		return clientConnectionName;
	}

	// public String getSessionId() {
	// if (this.pClientConnectionInstance == null)
	// return null;
	// return this.pClientConnectionInstance.getSessionId();
	// }

	private String sessionId = null;

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String zSessionId) {
		this.sessionId = zSessionId;
	}

	public boolean ifHasSessionId() {
		if (this.sessionId == null || this.sessionId.isEmpty()) {
			return false;
		}
		return true;
	}

	public MessageProcessMap getProcessMsgMap() {
		if (this.processMsgMap == null) {
			this.processMsgMap = new MessageProcessMap();
		}
		return processMsgMap;
	}

	public MessageProcessMap getAnswerMsgMap() {
		if (this.answerMsgMap == null) {
			this.answerMsgMap = new MessageProcessMap();
		}

		return answerMsgMap;
	}

	public MessageProcessMap getListenEventsMap() {
		if (this.listenEventsMap == null) {
			this.listenEventsMap = new MessageProcessMap();
		}
		return listenEventsMap;
	}

	public MessageProcessMap getListenSystemEventsMap() {
		if (this.listenSystemEventsMap == null) {
			this.listenSystemEventsMap = new MessageProcessMap();
		}
		return listenSystemEventsMap;
	}

	public MessageProcessMap getErrorEventsMap() {
		if (this.errorEventsMap == null) {
			this.errorEventsMap = new MessageProcessMap();
		}
		return errorEventsMap;
	}

	public boolean isOpenDatabaseConnection() {
		return openDatabaseConnection;
	}

	public void setOpenDatabaseConnection(boolean openDatabaseConnection) {
		this.openDatabaseConnection = openDatabaseConnection;
	}

	// public void boletearLaConnexionParaProbarLaReconexion() {
	// try {
	// this.connectionChannel.closeConnectionChannel();
	// } catch (Exception e) {
	// PssLogger.logError(e);
	// }
	// }
}
