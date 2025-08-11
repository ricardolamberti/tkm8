package pss.core.connectivity.messageManager.client.connectionChannel;

import pss.core.connectivity.messageManager.common.connectionChannel.IConnectionChannel;

public interface IClientConnectionChannel extends IConnectionChannel {
	public boolean connect(String zConnectionString) throws Exception;

}
