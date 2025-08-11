package pss.core.connectivity.messageManager.client.connection;

import pss.core.connectivity.messageManager.common.message.IMessageContent;

public interface IModuleClient {
	public String getModuleName()  throws Exception;
	public IModuleClient getNewInstance() throws Exception;
	public String getConnectionString() throws Exception;
	public IMessageContent onConnect(IMessageContent zMsg) throws Exception;
	public IMessageContent onDisconnect(IMessageContent zMsg) throws Exception;
//	public void setModule(ModuleClient client) throws Exception;
	public boolean initializeModuleClient(MsgConnectionInterface zClient) throws Exception;
	public boolean ifOpenDBConnection();
}
