package pss.common.version.client;

import pss.core.connectivity.messageManager.common.core.loader.IConfigurationLoader;
import pss.core.connectivity.messageManager.common.core.loader.ModuleLoader;

public class ConfigurationVersionManager implements IConfigurationLoader {
	
	String connection;
	String host;
	int port;
	
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getConnection() {
		return host+":"+port;
	}


	

	@Override
	public void loadDirectoriesPath(ModuleLoader loader) throws Exception {
		loader.addDirectory("/pss/forecourt/company");
		loader.addDirectory("/pss/forecourt/pfc");
	} 
}
