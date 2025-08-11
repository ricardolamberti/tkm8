package pss.core.connectivity.messageManager.server.connectionManager;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Queue;

import pss.core.connectivity.messageManager.common.connection.BaseConnectionInstance;
import pss.core.connectivity.messageManager.common.message.EvtMessage;
import pss.core.connectivity.messageManager.common.message.IMessageContent;
import pss.core.connectivity.messageManager.common.message.MsgResponse;
import pss.core.connectivity.messageManager.common.message.ReqMessage;
import pss.core.connectivity.messageManager.common.message.ReqSubscribe;
import pss.core.connectivity.messageManager.modules.addIns.IBaseServerAddInModule;
import pss.core.connectivity.messageManager.server.connection.ServerConnectionInstance;
import pss.core.tools.JExcepcion;
import pss.core.tools.PssLogger;

public class ConnectionsManager {
	private HashMap<String, SessionInstance> sessionElementsMap = null;
	private HashMap<String, WeakReference<SessionInstance>> connectionsMap = null;
	private HashMap<String, MessageSubscriptionList> processMap = null;
	private HashMap<String, MessageSubscriptionList> eventMap = null;
	private HashMap<String, MessageSubscriptionList> systemEventMap = null;
	private HashMap<String, AddinSubscriptionList> addInMap = null;
	private Thread currentThread = null;
	private Thread parentThread = null;

	private boolean ifSessionDepuratorThreadHasBeenInterrupted() {
		if (this.parentThread != null && this.currentThread != null) {
			if (this.currentThread.isInterrupted())
				return true;

			if (this.parentThread.isInterrupted())
				return true;

		}
		return false;
	}

	protected void runSessionDepuratorThread() {
		try {
			// this.onStart();
			while (true) {
				PssLogger.logDebug("Waiting to process Session Depurator");
				for (long a = 0; a < 1200 && ifSessionDepuratorThreadHasBeenInterrupted() == false; ++a) {
					Thread.sleep(500);
					// Thread.sleep(50);
				} // end for
				if (ifSessionDepuratorThreadHasBeenInterrupted()) {
					PssLogger.logDebug("Current thread has been interrupted");
					break;
				}
				PssLogger.logDebug("Processing Session Depurator");

				synchronized (sessionElementsMap) {
					Iterator<SessionInstance> sessionIT;
					SessionInstance sessionInstance;
					boolean deleteAnySession;

					while (true) {
						sessionIT = this.getSessionElementsMap().values().iterator();
						deleteAnySession = false;
						while (sessionIT.hasNext()) {
							sessionInstance = sessionIT.next();
							if (sessionInstance.isAlive() == false) {
								PssLogger.logDebug("This session will be deleted [" + sessionInstance.getSessionId() + "]");
								sessionIT.remove();
								deleteAnySession = true;
								break;
							}
						} // end while
						if (deleteAnySession == false) {
							break;
						}
					} // end while
				} // end synchronized
			} // end while
		} catch (Exception e) {
			PssLogger.logError(e, "[" + Thread.currentThread().getName() + "]");
		} // End try/catch
	}

	public void launchSessionDepuratorThread() throws Exception {
		if (currentThread != null) {
			if (currentThread.isAlive())
				return;
		}
		parentThread = Thread.currentThread();
		currentThread = new Thread(new Runnable() {
			public void run() {
				runSessionDepuratorThread();
			}
		});
		currentThread.setName(this.getClass().getSimpleName());
		currentThread.start();
	}

	public void stopSessionDepuratorThread() throws Exception {
		if (currentThread == null) {
			return;
		}

		if (currentThread.isAlive() == false) {
			return;
		}

		PssLogger.logDebug("Interrupting Connection Instance Thread");
		currentThread.interrupt();

		try {
			while (true) {
				this.currentThread.join(500);

				if (this.currentThread.isAlive()) {
					PssLogger.logError("The current thread has not been finished yet");
				} else {
					PssLogger.logDebug("The current thread has been finished ok");
					break;
				}
			} // end while
		} catch (InterruptedException i) {
			// Do nothing, exit normally
		} catch (Exception e) {
			throw e;
		}
		this.parentThread = null;
		this.currentThread = null;
		PssLogger.logDebug("Connection Instance Thread really finished");
	}

	public synchronized HashMap<String, SessionInstance> getSessionElementsMap() {
		if (this.sessionElementsMap == null) {
			this.sessionElementsMap = new HashMap<String, SessionInstance>();
		}
		return sessionElementsMap;
	}

	public synchronized HashMap<String, WeakReference<SessionInstance>> getConnectionsMap() {
		if (this.connectionsMap == null) {
			this.connectionsMap = new HashMap<String, WeakReference<SessionInstance>>();
		}
		return connectionsMap;
	}

	public synchronized HashMap<String, MessageSubscriptionList> getProcessMap() {
		if (this.processMap == null) {
			this.processMap = new HashMap<String, MessageSubscriptionList>();
		}
		return processMap;
	}

	public synchronized HashMap<String, MessageSubscriptionList> getEventMap() {
		if (this.eventMap == null) {
			this.eventMap = new HashMap<String, MessageSubscriptionList>();
		}
		return eventMap;
	}

	public synchronized HashMap<String, MessageSubscriptionList> getSystemEventMap() {
		if (this.systemEventMap == null) {
			this.systemEventMap = new HashMap<String, MessageSubscriptionList>();
		}
		return systemEventMap;
	}

	public synchronized HashMap<String, AddinSubscriptionList> getAddInMap() {
		if (this.addInMap == null) {
			this.addInMap = new HashMap<String, AddinSubscriptionList>();
		}
		return addInMap;
	}

	// Funcionalidad de conexion, tiene que ver con las instancias de clientes que
	// se conectan con el servidor.
	public synchronized void add(ServerConnectionInstance zConn) throws Exception {
		if (this.getSessionElementsMap().containsKey(zConn.getSessionId())) {
			JExcepcion.SendError("Se esta intentando agregar una nueva sesion con la misma clave [" + zConn.getSessionId() + "]");
		}

		SessionInstance oSE = new SessionInstance(zConn.getSessionId());
		oSE.setConnectionInstance(zConn);
		this.getSessionElementsMap().put(zConn.getSessionId(), oSE);
		getConnectionsMap().put(zConn.getConnectionName(), new WeakReference<SessionInstance>(oSE));
	}

	public synchronized void reconnect(ServerConnectionInstance zConn) throws Exception {
		if (this.getSessionElementsMap().containsKey(zConn.getSessionId()) == false) {
			JExcepcion.SendError("No existe el id de session [" + zConn.getSessionId() + "]");
		}
		getSessionElementsMap().get(zConn.getSessionId()).setConnectionInstance(zConn);
	}

	public synchronized BaseConnectionInstance getConnectionInstanceBySession(String zSessionId) {
		if (this.getSessionElementsMap().containsKey(zSessionId) == false) {
			return null;
		}

		return this.getSessionElementsMap().get(zSessionId).getConnectionInstance();
	}

	// public synchronized void removeConnectionInstanceBySession(String zSessionId) {
	// if (this.getSessionElementsMap().containsKey(zSessionId)) {
	// this.getSessionElementsMap().remove(zSessionId);
	// }
	// }
	//
	// public synchronized void remove(String zConnAddress) throws Exception {
	// if (this.getConnectionsMap().containsKey(zConnAddress))
	// this.getConnectionsMap().remove(zConnAddress);
	// }

	public void remove(ServerConnectionInstance zConn) throws Exception {
		this.getSessionElementsMap().remove(zConn.getSessionId());
	}

	public synchronized BaseConnectionInstance getConnectionInstanceByName(String zConnAddress) throws Exception {
		if (getConnectionsMap().containsKey(zConnAddress) == false)
			return null;
		WeakReference<SessionInstance> wr = getConnectionsMap().get(zConnAddress);
		if (wr.get() == null) {
			this.getConnectionsMap().remove(zConnAddress);
			return null;
		}
		if (wr.get().getConnectionInstance().ifConnected() == false) {
			JExcepcion.SendError("La instancia [" + wr.get().getConnectionInstance().getConnectionInstanceId() + "] no esta disponible");
		}

		return (BaseConnectionInstance) wr.get().getConnectionInstance();
	}

	// Funcionalidad relacionada solo con el procesamiento de mensajes
	public synchronized void subscribeToProcessMsg(ServerConnectionInstance zConnectionInstance, IMessageContent zReqSubscribe) throws Exception {
		MessageSubscriptionList oMSL;
		ReqSubscribe oReqSub = (ReqSubscribe) zReqSubscribe;
		IMessageContent oFilterMsg = oReqSub.getMsgContent();

		if (oFilterMsg == null) {
			JExcepcion.SendError("Se recibio un ReqSubscribe que no tiene filtro");
		}

		if (this.getProcessMap().containsKey(oFilterMsg.getMessageId()) == false) {
			oMSL = new MessageSubscriptionList();
			this.getProcessMap().put(oFilterMsg.getMessageId(), oMSL);
		} else {
			Queue<BaseConnectionInstance> qBCI;

			oMSL = this.getProcessMap().get(oFilterMsg.getMessageId());
			qBCI = oMSL.getConnectionsByFilter(oFilterMsg);

			if (qBCI.isEmpty() == false) {
				JExcepcion.SendError("Ya existe un Cliente que procesa el mensaje [" + oFilterMsg.getMessageId() + "] con el mismo filtro");
			} // end if
		} // end if

		if (this.getSessionElementsMap().containsKey(zConnectionInstance.getSessionId()) == false) {
			JExcepcion.SendError("No existe la sesion [" + zConnectionInstance.getSessionId() + "]");
		}

		oMSL.addToSubscriptionList(this.getSessionElementsMap().get(zConnectionInstance.getSessionId()), oFilterMsg);
		PssLogger.logDebug("El cliente [" + zConnectionInstance.getConnectionInstanceId() + "] se subscribio para procesar [" + oFilterMsg.getMessageId() + "]");
	}

	public synchronized void removeFromProcessMsg(BaseConnectionInstance zConnectionInstance) throws Exception {
		Iterator<MessageSubscriptionList> oIT;
		oIT = this.getProcessMap().values().iterator();
		while (oIT.hasNext()) {
			oIT.next().removeFromSubscriptionList(zConnectionInstance);
		} // end while
	} // end removeFromProcessMsg

	public synchronized BaseConnectionInstance getProcessMsgConnInstance(IMessageContent zFilterMsg) throws Exception {
		if (this.getProcessMap().containsKey(zFilterMsg.getMessageId()) == false) {
			return null;
		}
		Queue<BaseConnectionInstance> qBCI;
		qBCI = this.getProcessMap().get(zFilterMsg.getMessageId()).getConnectionsByFilter(zFilterMsg);
		if (qBCI.size() < 1) {
			return null;
		}
		if (qBCI.size() > 1) {
			JExcepcion.SendError("Existe mas de un procesador para el mensaje [" + zFilterMsg.getMessageId() + "] con el filtro [" + zFilterMsg.dumpMessage() + "]");
		}
		return qBCI.element();
	}

	// Funcionalidad relacionada solo con el procesamiento de Eventos
	public synchronized void subscribeToEventMsg(ServerConnectionInstance zConnectionInstance, IMessageContent zReqSubscribe) throws Exception {
		MessageSubscriptionList oMSL;
		ReqSubscribe oReqSub = (ReqSubscribe) zReqSubscribe;
		IMessageContent oFilterMsg = oReqSub.getMsgContent();

		if (oFilterMsg == null) {
			JExcepcion.SendError("Se recibio un ReqSubscribe que no tiene filtro");
		}

		if (this.getEventMap().containsKey(oFilterMsg.getMessageId()) == false) {
			oMSL = new MessageSubscriptionList();
			this.getEventMap().put(oFilterMsg.getMessageId(), oMSL);
		} else {
			Queue<BaseConnectionInstance> qBCI;

			oMSL = this.getEventMap().get(oFilterMsg.getMessageId());
			qBCI = oMSL.getConnectionsByFilter(oFilterMsg);

			if (qBCI.contains(zConnectionInstance)) {
				PssLogger.logDebug("El Cliente [" + zConnectionInstance.getConnectionInstanceId() + "] ya esta subscripto al evento [" + oFilterMsg.getMessageId() + "] con el mismo filtro");
				return;
			}
		} // end if

		if (this.getSessionElementsMap().containsKey(zConnectionInstance.getSessionId()) == false) {
			JExcepcion.SendError("No existe la sesion [" + zConnectionInstance.getSessionId() + "]");
		}

		oMSL.addToSubscriptionList(this.getSessionElementsMap().get(zConnectionInstance.getSessionId()), oFilterMsg);
		PssLogger.logDebug("El cliente [" + zConnectionInstance.getConnectionInstanceId() + "] se subscribio al evento [" + oFilterMsg.getMessageId() + "]");
	}

	public synchronized void removeFromEventMsg(BaseConnectionInstance zConnectionInstance) throws Exception {
		Iterator<MessageSubscriptionList> oIT;
		oIT = this.getEventMap().values().iterator();
		while (oIT.hasNext()) {
			oIT.next().removeFromSubscriptionList(zConnectionInstance);
		} // end while
	} // end removeFromEventMsg

	public synchronized Queue<BaseConnectionInstance> getEventMsgConnInstance(IMessageContent zFilterMsg) throws Exception {
		if (this.getEventMap().containsKey(zFilterMsg.getMessageId()) == false) {
			return null;
		}
		return this.getEventMap().get(zFilterMsg.getMessageId()).getConnectionsByFilter(zFilterMsg);
	}

	// Funcionalidad relacionada solo con el procesamiento de Eventos
	public synchronized void subscribeToSystemEventMsg(ServerConnectionInstance zConnectionInstance, IMessageContent zReqSubscribe) throws Exception {
		MessageSubscriptionList oMSL;
		ReqSubscribe oReqSub = (ReqSubscribe) zReqSubscribe;
		IMessageContent oFilterMsg = oReqSub.getMsgContent();

		if (oFilterMsg == null) {
			JExcepcion.SendError("Se recibio un ReqSubscribe que no tiene filtro");
		}

		if (this.getSystemEventMap().containsKey(oFilterMsg.getMessageId()) == false) {
			oMSL = new MessageSubscriptionList();
			this.getSystemEventMap().put(oFilterMsg.getMessageId(), oMSL);
		} else {
			Queue<BaseConnectionInstance> qBCI;

			oMSL = this.getSystemEventMap().get(oFilterMsg.getMessageId());
			qBCI = oMSL.getConnectionsByFilter(oFilterMsg);

			if (qBCI.contains(zConnectionInstance)) {
				PssLogger.logDebug("El Cliente [" + zConnectionInstance.getConnectionInstanceId() + "] ya esta subscripto al evento de sistema [" + oFilterMsg.getMessageId() + "] con el mismo filtro");
				return;
			}
		} // end if

		if (this.getSessionElementsMap().containsKey(zConnectionInstance.getSessionId()) == false) {
			JExcepcion.SendError("No existe la sesion [" + zConnectionInstance.getSessionId() + "]");
		}

		oMSL.addToSubscriptionList(this.getSessionElementsMap().get(zConnectionInstance.getSessionId()), oFilterMsg);
		// oMSL.addToSubscriptionList(zConnectionInstance, oFilterMsg);
		PssLogger.logDebug("El cliente [" + zConnectionInstance.getConnectionInstanceId() + "] se subscribio al evento de sistema [" + oFilterMsg.getMessageId() + "]");
	}

	public synchronized void removeFromSystemEventMsg(BaseConnectionInstance zConnectionInstance) throws Exception {
		Iterator<MessageSubscriptionList> oIT;
		oIT = this.getSystemEventMap().values().iterator();
		while (oIT.hasNext()) {
			oIT.next().removeFromSubscriptionList(zConnectionInstance);
		} // end while
	} // end removeFromSystemEventMsg

	public synchronized Queue<BaseConnectionInstance> getSystemEventMsgConnInstance(IMessageContent zFilterMsg) throws Exception {
		if (this.getSystemEventMap().containsKey(zFilterMsg.getMessageId()) == false) {
			return null;
		}
		return this.getSystemEventMap().get(zFilterMsg.getMessageId()).getConnectionsByFilter(zFilterMsg);
	}

	public synchronized void subscribeToAddInSubscriptionList(IBaseServerAddInModule zAddIn, IMessageContent zFilterMsg) throws Exception {
		AddinSubscriptionList oASL;

		if ((zFilterMsg instanceof EvtMessage) == false &&
				(zFilterMsg instanceof MsgResponse) == false &&
				(zFilterMsg instanceof ReqMessage) == false) {
			JExcepcion.SendError("El Addin [" + zAddIn.getAddinName() + "] no puede subscribirse a preprocesar el mensaje [" + zFilterMsg.getMessageId() + "] porque no pertence a los tipos permitidos");
		}

		
		if (this.getAddInMap().containsKey(zFilterMsg.getMessageId()) == false) {
			oASL = new AddinSubscriptionList();
			this.getAddInMap().put(zFilterMsg.getMessageId(), oASL);
		} else {
			oASL = this.getAddInMap().get(zFilterMsg.getMessageId());
		}

		oASL.addToSubscriptionList(zAddIn, zFilterMsg);
	}

	public synchronized void removeFromAddInSubscriptionList(IBaseServerAddInModule zAddIn) throws Exception {
		Iterator<AddinSubscriptionList> oIT;
		oIT = this.getAddInMap().values().iterator();
		while (oIT.hasNext()) {
			oIT.next().removeFromSubscriptionList(zAddIn);
		} // end while
	}

	public synchronized Queue<IBaseServerAddInModule> getAddInSubscriptionList(IMessageContent zMsg) throws Exception {
		AddinSubscriptionList oASL;

		if (getAddInMap().containsKey(zMsg.getMessageId()) == false) {
			return null;
		}

		oASL = this.getAddInMap().get(zMsg.getMessageId());
		if (oASL != null)
			return oASL.getAddinsByFilter(zMsg);
		return null;
	}

}
