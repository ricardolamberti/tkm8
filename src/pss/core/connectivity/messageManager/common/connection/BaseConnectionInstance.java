package pss.core.connectivity.messageManager.common.connection;

import java.util.LinkedList;
import java.util.Queue;

import pss.core.connectivity.messageManager.common.connectionChannel.IConnectionChannel;
import pss.core.connectivity.messageManager.common.message.EvtError;
import pss.core.connectivity.messageManager.common.message.EvtError.EvtErrorCode;
import pss.core.connectivity.messageManager.common.message.IMessageContent;
import pss.core.connectivity.messageManager.common.message.MessageEnvelope;
import pss.core.connectivity.messageManager.server.connection.JEchoCtrlExcepcion;
import pss.core.tools.JExcepcion;
import pss.core.tools.PssLogger;

/**
 * Implementa una instancia de conexion independientemente de su canal (Ej. TCP, local, etc.) y del origen de esta
 * (Cliente o Servidor). Esta implementacion permite procesar mensajes reprensentados por la clase
 * 
 */
public abstract class BaseConnectionInstance {
	/**
	 * Tiempo de espera entre consultas para saber si hay mensajes entrantes o salientes
	 */
	static final long SHADOW_DEFAULT_SLEEP_TIME = 100;

	/**
	 * Instancia del thread actual
	 */
	private Thread currentThread = null;
	private Thread parentThread = null;
	private boolean killThisConnection = false;

	/**
	 * Canal de conexion entre dos instancias de conexion
	 */
	protected IConnectionChannel pConnectionChannel = null;
	protected String pConnectionInstanceName = null;

	private Queue<MessageEnvelope> outGoingMsgQueue = new LinkedList<MessageEnvelope>();
	protected boolean pIsValidatedConnetion = false;
	protected MessageEnvelope lastMessageProcessed = null;

	protected abstract void resetEchoWaitingTime() throws Exception;

	protected abstract void checkEchoWaitingTime() throws Exception;

	protected abstract void processEchoMsgType(MessageEnvelope zMsg) throws Exception;

	protected abstract void processErrorMsgType(MessageEnvelope zMsg) throws Exception;

	protected abstract void processErrorMsgType(IMessageContent zMsgContent) throws Exception;

	protected abstract void processEventMsgType(MessageEnvelope zMsg) throws Exception;

	protected abstract void processSystemEventMsgType(MessageEnvelope zMsg) throws Exception;

	protected abstract void processLoginMsgType(MessageEnvelope zMsg) throws Exception;

	protected abstract void processLoginErrorMsgType(MessageEnvelope zMsg) throws Exception;

	protected abstract void processRequestSubscribeMsgType(MessageEnvelope zMsg) throws Exception;

	protected abstract void processProcessMsg(MessageEnvelope zMsg) throws Exception;

	protected abstract void processEventSubscribeMsgType(MessageEnvelope zMsg) throws Exception;

	protected abstract void processSystemEventSubscribeMsgType(MessageEnvelope zMsg) throws Exception;

	protected abstract boolean onPreProcessMsg(MessageEnvelope zMsg) throws Exception;

	protected abstract void onProcessed(MessageEnvelope zMsg) throws Exception;

	protected abstract void onStart() throws Exception;

	// protected Thread getThread() {
	// return this.currentThread;
	// }

	protected void runConnectionInstanceShadowThread() {
		boolean bMsgProccessed;
		PssLogger.logDebug("Running Connection Instance Thread for Connection Id [" + this.getConnectionInstanceId() + "]");
		try {
			this.onStart();
			while (true) {
				if (ifShadowThreadHasBeenInterrupted()) {
					PssLogger.logDebug("Current thread has been interrupted");
					break;
				}

				if (isConnected() == false) {
					PssLogger.logDebug("The connection instance has been broken or closed");
					break;
				}

				bMsgProccessed = false;
				if (isIncommingMsgWaiting()) {
					resetEchoWaitingTime();
					bMsgProccessed = true;
					processIncommingMsg();
				}

				synchronized (outGoingMsgQueue) {
					if (ifOutgoingMsgWaiting()) {
						bMsgProccessed = true;
						processOutgoingMsg();
					}
				} // end synchronized block

				if (bMsgProccessed) {
					Thread.sleep(10);
					continue;
				}

				Thread.sleep(SHADOW_DEFAULT_SLEEP_TIME);
				checkEchoWaitingTime();

				Thread.sleep(50);
			} // end while
		} catch (InterruptedException i) {
			PssLogger.logDebug("Se interrumpio la conexion");
		} catch (JEchoCtrlExcepcion j) {
			PssLogger.logError(j);
			this.killThisConnection = true;
		} catch (Exception e) {
			// Si la excepcion no fue de comunicacion trato de procesarla con el ONERROR
			if (isCommunicationError(e) == false) {
				PssLogger.logError(e, "[" + Thread.currentThread().getName() + "]");
				try {
					this.onError(e, null);
				} catch (Exception z) {
					PssLogger.logError(z, "[" + Thread.currentThread().getName() + "]");
				} // End try/catch
			} else {
				PssLogger.logError(e, "Communication Error en Connection Instance: [" + this.getConnectionInstanceId() + "]");
			}
		} // End try/catch
		onFinishShadow();
	}

	public void launchConnectionInstanceShadow() throws Exception {
		parentThread = Thread.currentThread();
		currentThread = new Thread(new Runnable() {
			public void run() {
				runConnectionInstanceShadowThread();
			}
		});
		currentThread.setName(getConnectionInstanceId());
		currentThread.start();
		PssLogger.logDebug("Thread launched for ConnectionId [" + getConnectionInstanceId() + "]");
	}

	public void stopConnectionInstanceShadow() throws Exception {
		if (currentThread == null) {
			return;
		}

		if (currentThread.isInterrupted()) {
			return;
		}	
		
		if (currentThread.isAlive() == false) {
			return;
		}
			
		try {
			PssLogger.logDebug("Interrupting Connection Instance Thread");
			currentThread.interrupt();
			while (true) {
				synchronized (this.currentThread) {
					if (this.currentThread == null) {
						break;
					}
					currentThread.interrupt();
					this.currentThread.join(500);
					if (this.currentThread == null) {
						break;
					}
					if (this.currentThread.isAlive()) {
						PssLogger.logError("The current thread has not been finished yet");
					} else {
						PssLogger.logDebug("The current thread has been finished ok");
						break;
					}
				}
			} // end while
		} catch (InterruptedException i) {
			// Do nothing, exit normally
		} catch (Exception e) {
			PssLogger.logError(e, "Error esperando a que se termine el ConnectionInstanceShadow");
			throw e;
		}
		PssLogger.logDebug("Connection Instance Thread really finished");
	}

	public void addOutgoingMsg(MessageEnvelope zMsg) throws Exception {
		if (pIsValidatedConnetion == false) {
			if (zMsg.getMsgType() != MessageEnvelope.MSG_TYPE_LOGIN && zMsg.getMsgType() != MessageEnvelope.MSG_TYPE_LOGIN_ERROR) {
				String strLog = "MsgType [" + zMsg.getMessageTypeDescription() + "] ";
				strLog += "MsgId [" + zMsg.getMsgId() + "]";
				PssLogger.logError("Descartando el siguiente mensaje porque la conexion aun no esta validada: " + strLog);
				EvtError oEE = new EvtError(EvtErrorCode.EC10000, "Conexion aun no ha sido validada por el servidor", zMsg);
				processErrorMsgType(oEE);
				return;
			}
		}
		synchronized (outGoingMsgQueue) {
			outGoingMsgQueue.add(zMsg);
		}
	}

	public void addOutgoingMsg(IMessageContent zMsgContent, String zDestination) throws Exception {
		MessageEnvelope oMsg = new MessageEnvelope();

		oMsg.setMsgType(zMsgContent.getMessageType());
		oMsg.setMsgId(zMsgContent.getMessageId());
		oMsg.setMsgSource(this.getConnectionInstanceId());
		if (zDestination != null && zDestination.isEmpty() == false) {
			oMsg.setMsgTarget(zDestination);
		}
		oMsg.setMsgContent(zMsgContent);
		addOutgoingMsg(oMsg);
	}

	public void addOutgoingMsg(IMessageContent zMsgContent) throws Exception {
		addOutgoingMsg(zMsgContent, null);
	}

	private void processIncommingMsg() throws Exception {
		MessageEnvelope oMsg = null;

		try {
			oMsg = readMsg();
			this.lastMessageProcessed = oMsg;
			this.onProcess(oMsg);
		} catch (Exception e) {
			this.onError(e, oMsg);
		}
		this.lastMessageProcessed = null;
	}

	protected boolean ifOutgoingMsgWaiting() {
		synchronized (outGoingMsgQueue) {
			return (outGoingMsgQueue.isEmpty() == false);
		}
	}

	protected void processOutgoingMsg() throws Exception {
		MessageEnvelope oPssMsg;
		synchronized (outGoingMsgQueue) {
			oPssMsg = outGoingMsgQueue.poll();
		} // end synchronized block
		// if (!onPreProcessMsg(oPssMsg)) {
		// PssLogger.logDebug("Descartando mensaje de salida [" + oPssMsg.getMsgId() + "]");
		// return;
		// }
		this.sendMsg(oPssMsg);
		this.onProcessed(oPssMsg);
	}

	private boolean ifShadowThreadHasBeenInterrupted() {
		if (this.parentThread != null && this.currentThread != null) {
			if (this.currentThread.isInterrupted())
				return true;
			if (this.parentThread.isInterrupted())
				return true;
			if (Thread.currentThread().isInterrupted())
				return true;

		}
		return false;
	}

	protected void onProcess(MessageEnvelope zMsg) throws Exception {

		try {
			if (pIsValidatedConnetion == false) {
				if (zMsg.getMsgType() != MessageEnvelope.MSG_TYPE_ECHO && zMsg.getMsgType() != MessageEnvelope.MSG_TYPE_LOGIN && zMsg.getMsgType() != MessageEnvelope.MSG_TYPE_LOGIN_ERROR)
					JExcepcion.SendError("The conneccion has not validated by the server");
			}

			if (zMsg.getMsgType() != MessageEnvelope.MSG_TYPE_ECHO) {
				String strLog = "[" + Thread.currentThread().getName() + "] Receiving: ";
				strLog += " MsgType [" + zMsg.getMessageTypeDescription() + "]";
				strLog += " MsgId [" + zMsg.getMsgId() + "]";
				strLog += " from [" + zMsg.getMsgSource() + "]";
				if (zMsg.getMsgTarget().isEmpty() == false) {
					strLog += " to [" + zMsg.getMsgTarget() + "]";
				}
				PssLogger.logDebug(strLog);
			}

			if (!onPreProcessMsg(zMsg)) {
				PssLogger.logDebug("Descartando mensaje [" + zMsg.getMsgId() + "]");
				return;
			}

			switch (zMsg.getMsgType()) {
			case MessageEnvelope.MSG_TYPE_ECHO:
				processEchoMsgType(zMsg);
				break;
			case MessageEnvelope.MSG_TYPE_ERROR:
				processErrorMsgType(zMsg);
				break;
			case MessageEnvelope.MSG_TYPE_EVENT:
				processEventMsgType(zMsg);
				break;
			case MessageEnvelope.MSG_TYPE_SYSTEM_EVENT:
				processSystemEventMsgType(zMsg);
				break;
			case MessageEnvelope.MSG_TYPE_LOGIN:
				processLoginMsgType(zMsg);
				break;
			case MessageEnvelope.MSG_TYPE_LOGIN_ERROR:
				processLoginErrorMsgType(zMsg);
				break;
			// case PssMessageEnvelope.MSG_TYPE_PREPROCESS:
			// processPreprocessMsgType(oMsg);
			// break;
			case MessageEnvelope.MSG_TYPE_REQUEST_SUBSCRIBE:
				processRequestSubscribeMsgType(zMsg);
				break;
			case MessageEnvelope.MSG_TYPE_REQUEST:
			case MessageEnvelope.MSG_TYPE_RESPONSE:
				processProcessMsg(zMsg);
				break;
			case MessageEnvelope.MSG_TYPE_EVENT_SUBSCRIBE:
				processEventSubscribeMsgType(zMsg);
				break;
			case MessageEnvelope.MSG_TYPE_SYSTEM_EVENT_SUBSCRIBE:
				processSystemEventSubscribeMsgType(zMsg);
				break;
			default:
				JExcepcion.SendError("El tipo de mensaje [" + zMsg.getMsgType() + "] es invalido");
			} // end switch

		} catch (InterruptedException i) {
			// Si el thread corriente fue interrumpido no tengo que enviar ningun mensaje porque ya no tengo conexion
			return;
		} catch (Exception e) {
			onError(e, zMsg);
		}

		return;
	}

	protected void sendEchoMsg() throws Exception {
		MessageEnvelope oMsg = new MessageEnvelope();
		oMsg.setMsgType(MessageEnvelope.MSG_TYPE_ECHO);
		this.addOutgoingMsg(oMsg);
	}

	protected void onError(Exception zException, MessageEnvelope zMsg) throws Exception {
		if (zMsg != null) {
			PssLogger.logError("[" + Thread.currentThread().getName() + "] Se produjo el siguiente error procesando el mensaje:");
			PssLogger.logError(zMsg.dumpMessage());
		}
		PssLogger.logError(zException);
		// Solo envio el onError si el error no fue de comunicacion, sino no tiene
		// sentido
		if (isCommunicationError(zException)) {
			throw zException;
		} // End if "Error de comunicación"
		PssLogger.logError(zException, "[" + Thread.currentThread().getName() + "]");
//		zException.printStackTrace();
		EvtError oErrorMsg = new EvtError(EvtErrorCode.EC99998, zException, zMsg);
		this.addOutgoingMsg(oErrorMsg, zMsg.getMsgSource());
	}

	protected boolean isCommunicationError(Exception zException) {
		if (zException.getMessage() == null)
			return false;
		if (zException.getMessage().equalsIgnoreCase("Error de comunicación"))
			return true;
		if (zException.getClass() == java.net.SocketException.class)
			return true;
		return false;
	}

	public void setConnectionInstanceName(String zConnectionInstanceName) {
		zConnectionInstanceName.replace('<', ' ');
		zConnectionInstanceName.replace('>', ' ');

		this.pConnectionInstanceName = zConnectionInstanceName;
	}

	public String getConnectionInstanceId() {
		String strName;
		if (this.pConnectionInstanceName == null) {
			strName = "Unnamed";
		} else {
			strName = pConnectionInstanceName;
		}

		if (strName.indexOf('<') < 0) {
			strName += " < " + this.pConnectionChannel.getConnectionId() + " > ";
		}
		return strName;
	}

	public void setConnectionChannel(IConnectionChannel zConnectionChannel) {
		pConnectionChannel = zConnectionChannel;
	}

	protected boolean isIncommingMsgWaiting() throws Exception {
		return pConnectionChannel.isIncommingMsgWaiting();
	}

	protected MessageEnvelope readMsg() throws Exception {
		return pConnectionChannel.readMsg();
	}

	private void sendMsg(MessageEnvelope zMsg) throws Exception {
		if (zMsg.getMsgType() != MessageEnvelope.MSG_TYPE_ECHO) {
			String strLog = "Sending MsgType [" + zMsg.getMessageTypeDescription() + "]";
			strLog += "  MsgId [" + zMsg.getMsgId() + "]";
			strLog += " from [" + Thread.currentThread().getName() + "]";
			if (zMsg.getMsgTarget().isEmpty() == false) {
				strLog += " to [" + zMsg.getMsgTarget() + "]";
			}
			PssLogger.logDebug(strLog);
		}
		pConnectionChannel.sendMsg(zMsg);
	}

	protected boolean isConnected() {
		return pConnectionChannel.isConnected();
	}

	protected void onFinishShadow() {
		try {
			if (pConnectionChannel.isConnected())
				pConnectionChannel.closeConnectionChannel();
		} catch (Exception e) {
			PssLogger.logError(e, "[" + Thread.currentThread().getName() + "]");
		}
		synchronized (this.currentThread) {
			this.currentThread = null;
			this.parentThread = null;
		}
	}

	public boolean ifConnectionThreadIsRunning() {
		if (this.parentThread == null || this.currentThread == null)
			return false;

		if (this.parentThread.isAlive() == false)
			return false;

		return this.currentThread.isAlive();
	}

	public boolean ifConnected() {
		if (this.ifConnectionThreadIsRunning() == false)
			return false;

		if (this.pConnectionChannel.isConnected() == false) {
			return false;
		}

		return (this.pIsValidatedConnetion == true);
	}

	public boolean isEqual(BaseConnectionInstance zBCI) {
		return this.pConnectionInstanceName.equalsIgnoreCase(zBCI.getConnectionInstanceId());
	}

	public boolean ifKillThisConnectionInstance() {
		return this.killThisConnection;
	}

	public MessageEnvelope getLastMessageProcessed() {
  	return lastMessageProcessed;
  }

}
