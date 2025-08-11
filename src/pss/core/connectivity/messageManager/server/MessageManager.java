package pss.core.connectivity.messageManager.server;

import java.util.HashMap;

import pss.core.connectivity.messageManager.client.connectionChannel.LocalClientConnectionChannel;
import pss.core.connectivity.messageManager.common.core.loader.IConfigurationLoader;
import pss.core.connectivity.messageManager.modules.addIns.IBaseServerAddInModule;
import pss.core.connectivity.messageManager.server.confMngr.ConfigurationManager;
import pss.core.connectivity.messageManager.server.connection.ServerConnectionInstance;
import pss.core.connectivity.messageManager.server.connection.TcpMessageServer;
import pss.core.connectivity.messageManager.server.connectionChannel.LocalServerConnectionChannel;
import pss.core.connectivity.messageManager.server.connectionManager.ConnectionsManager;

public class MessageManager  {
	private static MessageManager gPssMsgMngr = null;
	private static boolean gWasLaunched = false;
	private HashMap<String, IBaseServerAddInModule> addinInstanceMap = null;
	private TcpMessageServer pTcpMsgSrv = null;
	private ConnectionsManager pConnMngr = null;
	private ConfigurationManager pConfMngr = null;
	public static IConfigurationLoader config;
	

	public static IConfigurationLoader getConfig() {
		return config;
	}

	public static void setConfig(IConfigurationLoader config) {
		MessageManager.config = config;
	}

	public MessageManager() {
	}

	public void launchMessageManager() throws Exception {
		if (gWasLaunched == true)
			return;
		
		// Genera un nuevo log para que todo el manejo de mensajes quede bajo un
		// mismo log.
		// JAplicacion.openSession();
//		 JAplicacion.GetApp().openApp("MESSAGE_MANAGER", JAplicacion.AppTipoThread(), false);
		// JAplicacion.GetApp().openApp("MESSAGE_MANAGER", JAplicacion.AppService(), true);

		this.launchTcpMsgMngr();
		gWasLaunched = true;
	}

	protected HashMap<String, IBaseServerAddInModule> getAddinInstanceMap() {
		if (this.addinInstanceMap == null) {
			this.addinInstanceMap = new HashMap<String, IBaseServerAddInModule>();
		}
		return this.addinInstanceMap;
	}

	protected ConnectionsManager getConnectionMngr() throws Exception {
		if (this.pConnMngr == null) {
			this.pConnMngr = new ConnectionsManager();
			this.pConnMngr.launchSessionDepuratorThread();
		}
		return this.pConnMngr;
	}

	protected ConfigurationManager getConfigurationMngr() throws Exception {
		if (this.pConfMngr == null) {
			this.pConfMngr = ConfigurationManager.getConfigurationManagerInstance();
		}

		return this.pConfMngr;
	}


	public static synchronized MessageManager getMessageManagerInstance() throws Exception {
		if (gPssMsgMngr == null) {
			gPssMsgMngr = new MessageManager();
		}
		return gPssMsgMngr;
	}

	private void launchTcpMsgMngr() throws Exception {
		if (pTcpMsgSrv != null) {
			return;
		}

		this.pTcpMsgSrv = new TcpMessageServer();
		this.pTcpMsgSrv.setConnectionsMngr(this.getConnectionMngr());
		// Por ahora no hace falta, escucho en el puerto default, pero esta
		// arquitectura permite
		// levantar un server en diferentes puertos.
    this.pTcpMsgSrv.setTcpPort(((ConfigMgr)config).getPortMsgMgr());
		this.pTcpMsgSrv.launchTcpMessageServer();
	}

	public void connectLocalClient(LocalClientConnectionChannel zLocalClientCnn) throws Exception {
		LocalServerConnectionChannel oServerConnChnl = new LocalServerConnectionChannel();
		oServerConnChnl.setRemoteConnection(zLocalClientCnn);
		zLocalClientCnn.setRemoteConnection(oServerConnChnl);

		ServerConnectionInstance oConnection = new ServerConnectionInstance();
		oConnection.setConnectionChannel(oServerConnChnl);
		oConnection.setConnectionsMngr(this.getConnectionMngr());
		oConnection.launchConnectionInstanceShadow();
	}

	public static boolean bDebugMode = false;



}
