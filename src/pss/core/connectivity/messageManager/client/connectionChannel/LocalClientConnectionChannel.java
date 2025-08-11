package pss.core.connectivity.messageManager.client.connectionChannel;

import pss.core.connectivity.messageManager.common.connectionChannel.BaseLocalConnectionChannel;
import pss.core.connectivity.messageManager.server.MessageManager;

public class LocalClientConnectionChannel extends BaseLocalConnectionChannel {

	@Override
public boolean connect(String connectionString) throws Exception {
	MessageManager.getMessageManagerInstance().connectLocalClient(this);
	return true;
}

}
