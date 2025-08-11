package pss.core.connectivity.messageManager.client.connection;

import pss.core.connectivity.messageManager.common.message.IMessageContent;
import pss.core.connectivity.messageManager.common.message.events.SysEvtListenerOnConnect;
import pss.core.connectivity.messageManager.common.message.events.SysEvtListenerOnDisconnect;
import pss.core.connectivity.messageManager.common.message.events.SysEvtOnConnect;
import pss.core.connectivity.messageManager.common.message.events.SysEvtOnDisconnect;
import pss.core.connectivity.messageManager.common.message.messageProcessor.IEventListener;
import pss.core.connectivity.messageManager.common.message.messageProcessor.IProcessMsg;
import pss.core.connectivity.messageManager.common.message.messageProcessor.IRequestListener;
import pss.core.connectivity.messageManager.common.message.messageProcessor.IResponseListener;
import pss.core.tools.PssLogger;

public abstract class MessageClient implements IModuleClient {
	/**
	 * Inicializa , crea la instancia de conexion cliente y configura los eventos default del cliente
	 * 
	 * @throws Exception
	 */
	protected MsgConnectionInterface msgConnInterface = null;

	@Override
	public boolean ifOpenDBConnection() {
	  return false;
	}

	@Override
	public boolean initializeModuleClient(MsgConnectionInterface zClient) throws Exception {
		msgConnInterface = zClient;
		msgConnInterface.setOpenDatabaseConnection(this.ifOpenDBConnection());
		msgConnInterface.subscribeToEvent(new SysEvtListenerOnConnect(new SysEvtOnConnect().setModuleName(this.getClass().getSimpleName()), this, "onConnect"));
		msgConnInterface.subscribeToEvent(new SysEvtListenerOnDisconnect(new SysEvtOnDisconnect().setModuleName(this.getClass().getSimpleName()), this, "onDisconnect"));
		return true;
	}

	/**
	 * Configura la conexion con el servidor y lanza el proceso de conexion. Cuando la conexion esta establecida y
	 * validada con el servidor se ejecuta automaticamente el metodo OnConnect. Una vez que la comunicacion esta
	 * establecida, si se pierde la comunicacion, el cliente llama al metodo OnDisconnect.
	 * 
	 * Las subscripciones realizadas antes que la conexion este establecida se almacenan y se ejecutan automaticamente al
	 * momento de la conexion no es necesario espera el onConnect para efectuarlas.
	 * 
	 * @param zConnectionString
	 *          IpAddress:port, si la conexion es via TCP (Ej. "127.0.0.1:9876") local si la conexion es local (Ej.
	 *          "local")
	 * 
	 * @throws Exception
	 */
	public void connect(String zConnectionString) throws Exception {
		this.msgConnInterface.connect(zConnectionString);
	}

	/**
	 * Desconecta el cliente del servidor en forma ordenada
	 * 
	 * @throws Exception
	 */
	public void disconnect() throws Exception {
		this.msgConnInterface.disconnect();
	}

	protected void launchMessageClient() throws Exception {
		MsgConnectionInterface client = new MsgConnectionInterface();
		client.launch(this);
	}

	public boolean ifConnected() throws Exception {
		return this.msgConnInterface.ifConnected();
	}

	// Subscripciones al las respuestas.

	public void subscribeToAnswer(IResponseListener zResponseListener) throws Exception {
		this.msgConnInterface.subscribeToAnswer(zResponseListener);
	}

	public void subscribeToAnswer(IMessageContent zMsg, IProcessMsg zProcessMsgClass) throws Exception {
		this.msgConnInterface.subscribeToAnswer(zMsg, zProcessMsgClass);
	}

	public void subscribeToErrorMsg(IProcessMsg zProcessErrorMsg) throws Exception {
		this.msgConnInterface.subscribeToErrorMsg(zProcessErrorMsg);
	}

	public void subscribeToErrorMsg(IMessageContent zMsg, IProcessMsg zProcessErrorMsg) throws Exception {
		this.msgConnInterface.subscribeToErrorMsg(zMsg, zProcessErrorMsg);
	}

	public void subscribeToEvent(IEventListener zEventListener) throws Exception {
		this.msgConnInterface.subscribeToEvent(zEventListener);
	}

	public void subscribeToEventMsg(IMessageContent zMsg, IProcessMsg zProcessMsgClass) throws Exception {
		this.msgConnInterface.subscribeToEventMsg(zMsg, zProcessMsgClass);
	}

	public void subscribeToRequestMsg(IRequestListener zRequestListener) throws Exception {
		this.msgConnInterface.subscribeToRequestMsg(zRequestListener);
	}

	public void subscribeToRequestMsg(IMessageContent zMsg, IProcessMsg zProcessMsgClass) throws Exception {
		this.msgConnInterface.subscribeToRequestMsg(zMsg, zProcessMsgClass);
	}

	public void sendMessage(IMessageContent zMsgContent) throws Exception {
		this.msgConnInterface.sendMessage(zMsgContent);
	}

	public void sendMessage(IMessageContent zMsgContent, String zDestination) throws Exception {
		this.msgConnInterface.sendMessage(zMsgContent, zDestination);
	}

	public void sendMessage(IResponseListener zResponseListener) throws Exception {
		this.msgConnInterface.sendMessage(zResponseListener);
	}

	public void sendMessage(IMessageContent zMsgContent, String zDestination, IProcessMsg zSubscribeToAnswer, IProcessMsg zSubscribeToError) throws Exception {
		this.msgConnInterface.sendMessage(zMsgContent, zDestination, zSubscribeToAnswer, zSubscribeToError);
	}

	public void sendMessage(IMessageContent zMsgContent, String zDestination, IProcessMsg zSubscribeToAnswer) throws Exception {
		this.msgConnInterface.sendMessage(zMsgContent, zDestination, zSubscribeToAnswer);
	}

	public void sendMessage(IMessageContent zMsgContent, IProcessMsg zSubscribeToAnswer, IProcessMsg zSubscribeToError) throws Exception {
		this.msgConnInterface.sendMessage(zMsgContent, zSubscribeToAnswer, zSubscribeToError);
	}

	public void sendMessage(IMessageContent zMsgContent, IProcessMsg zSubscribeToAnswer) throws Exception {
		this.msgConnInterface.sendMessage(zMsgContent, zSubscribeToAnswer);
	}

	public void sendResponse(IMessageContent zMsgContent) throws Exception {
		this.msgConnInterface.sendResponse(zMsgContent);
	}
	
	public String getClientConnectionName() {
		try {
			return this.msgConnInterface.getClientConnectionName();
		} catch (Exception e) {
			PssLogger.logError(e);
			return null;
		}
	}

	@Override
	public IModuleClient getNewInstance() throws Exception {
		return this;
	}

	@Override
	public String getModuleName() throws Exception {
		return this.getClass().getSimpleName();
	}
	
	
	// Metodos Abstractos
	/**
	 * Se debe implementar este metodo que es invocado cuando se establece la conexion y esta es validada por el servidor
	 * 
	 * @param zMsg
	 * @return
	 * @throws Exception
	 */
	public abstract IMessageContent onConnect(IMessageContent zMsg) throws Exception;

	/**
	 * Se debe implementar este metodo que es invocado cuando se pierde la comunicacion con el servidor
	 * 
	 * @param zMsg
	 * @return
	 * @throws Exception
	 */
	public abstract IMessageContent onDisconnect(IMessageContent zMsg) throws Exception;

	/**
	 * Se debe implementar este metodo para retornar el connection String al server.
	 * 
	 * @return String - String de conexion con el server (Ej. "local" o "127.0.0.1:9999")
	 */
	public abstract String getConnectionString();

}
