package pss.core.connectivity.messageManager.server;

import pss.core.connectivity.messageManager.common.core.loader.IConfigurationLoader;
import pss.core.connectivity.messageManager.common.core.loader.ModuleLoader;


public class ConfigMgr  implements IConfigurationLoader {
	
	int portMsgMgr;
	
	public int getPortMsgMgr() {
		return portMsgMgr;
	}

	public ConfigMgr setPortMsgMgr(int portMsgMgr) {
		this.portMsgMgr = portMsgMgr;
		return this;
	}

	@Override
	public void loadDirectoriesPath(ModuleLoader loader) throws Exception {
	}
	


}
