package pss.core.connectivity.messageManager.modules.addIns;

import pss.core.connectivity.messageManager.common.message.MessageEnvelope;
import pss.core.connectivity.messageManager.server.connectionManager.ConnectionsManager;

public interface IBaseServerAddInModule {
	public boolean processMsg(MessageEnvelope zMsg) throws Exception;
	public void subscribeToControlMessages(ConnectionsManager pConnectionsMngr) throws Exception;
	public String getAddinName();
	public void launchAddinClientConnection() throws Exception;
	public boolean isEqual(IBaseServerAddInModule zAddin);
}
