package pss.core.connectivity.messageManager.server.connectionChannel;

import pss.core.connectivity.messageManager.common.connectionChannel.BaseLocalConnectionChannel;

public class LocalServerConnectionChannel extends BaseLocalConnectionChannel {
	@Override
	public boolean connect(String connectionString) throws Exception {
		return false;
	}

}
