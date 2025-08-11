package pss.core.connectivity.messageManager.server;

import pss.core.connectivity.messageManager.common.core.loader.IConfigurationLoader;
import pss.core.connectivity.messageManager.common.core.loader.IModuleLoader;
import pss.core.connectivity.messageManager.common.core.loader.ModuleLoader;
import pss.core.connectivity.messageManager.modules.addIns.IBaseServerAddInModule;
import pss.core.tools.PssLogger;

public class MessageManagerLoader implements IModuleLoader {
	private boolean pLoadAddinsDinamically = true;
	

	@Override
	public void launchModule(String classFullName,IConfigurationLoader config) throws Exception {
		this.installAddinModule(classFullName,config);
	}

	@Override
	public boolean isLoaderClass(String classFullName) throws ClassNotFoundException {
		return IBaseServerAddInModule.class.isAssignableFrom(Class.forName(classFullName));
	}


	public void setLoadAddinsDinamically(boolean zLoadAddinsDinamically) {
		this.pLoadAddinsDinamically = zLoadAddinsDinamically;
	}

	protected void installAddinModule(String zAddinModuleName,IConfigurationLoader config) throws Exception {
		IBaseServerAddInModule oAddin;

		try {
			PssLogger.logDebug("Addin [" + zAddinModuleName + "] will be processed");
			oAddin = (IBaseServerAddInModule) Class.forName(zAddinModuleName).newInstance();
		} catch (InstantiationException e) {
			PssLogger.logDebug("Se detecto la siguiente excepcion " + e.getMessage() + " tratando de instanciar la clase " + zAddinModuleName);
			return;
		} catch (Exception e) {
			PssLogger.logDebug("Se detecto la siguiente excepcion tratando de instanciar la clase " + zAddinModuleName);
			e.printStackTrace();
			return;
		} catch (Throwable t) {
			PssLogger.logDebug("Se detecto la siguiente error CRITICO tratando de instanciar la clase " + zAddinModuleName);
			t.printStackTrace();
			return;
		}
		
		oAddin.subscribeToControlMessages(MessageManager.getMessageManagerInstance().getConnectionMngr());
		oAddin.launchAddinClientConnection();
		MessageManager.getMessageManagerInstance().getAddinInstanceMap().put(oAddin.getAddinName(), oAddin);
	}

	@Override
	public boolean isLoadModuleDinamically() {
		return pLoadAddinsDinamically;
	}

	@Override
	public void loadClass(ModuleLoader loader) throws Exception {

	}

}
