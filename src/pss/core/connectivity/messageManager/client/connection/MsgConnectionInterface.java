package pss.core.connectivity.messageManager.client.connection;


public class MsgConnectionInterface extends MsgClientConnection {
	public void launch(IModuleClient zClient) throws Exception {
		IModuleClient moduleClient = zClient;
		setClientConnectionName(moduleClient.getClass().getSimpleName());
		if (moduleClient.initializeModuleClient(this))
			this.connect(moduleClient.getConnectionString());
	}
}
