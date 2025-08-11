package pss.core.connectivity.messageManager.server.confMngr;

import pss.common.moduleConfigMngr.JMMModuleConfigMngr;
import pss.core.connectivity.messageManager.client.connection.IModuleClient;
import pss.core.connectivity.messageManager.client.connection.MsgConnectionInterface;
import pss.core.connectivity.messageManager.common.core.loader.IConfigurationLoader;
import pss.core.connectivity.messageManager.common.core.loader.IModuleLoadeable;
import pss.core.connectivity.messageManager.common.core.loader.IModuleLoader;
import pss.core.connectivity.messageManager.common.core.loader.ModuleLoader;

public class LoaderConfMngr implements IModuleLoader {
	public boolean isLoaderClass(String classFullName) throws ClassNotFoundException {
		return (IModuleLoadeableConfMgr.class.isAssignableFrom(Class.forName(classFullName)) && IModuleClient.class.isAssignableFrom(Class.forName(classFullName)));
	}

	@Override
	public void launchModule(String classFullName, IConfigurationLoader config) throws Exception {
		MsgConnectionInterface client = new MsgConnectionInterface();
		IModuleClient moduleLoadeable = ((IModuleClient) Class.forName(classFullName).newInstance());
		((IModuleLoadeable)moduleLoadeable).setConfiguration(config);
		client.launch(moduleLoadeable.getNewInstance());
	}


	public boolean isLoadModuleDinamically() {
		return false;
	}

	@Override
	public void loadClass(ModuleLoader loader) throws Exception {
		loader.addClass(ConfigurationManager.class.getName());
		loader.addClass(JMMModuleConfigMngr.class.getName());
	}

}
