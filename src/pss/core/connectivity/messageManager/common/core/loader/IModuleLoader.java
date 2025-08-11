package pss.core.connectivity.messageManager.common.core.loader;
/**
 * Especializacion de cargadores de modulos
 * @author Pentaware
 *
 */
public interface IModuleLoader {
	public boolean isLoaderClass(String classFullName) throws ClassNotFoundException;
	public void launchModule(String classFullName,IConfigurationLoader config) throws Exception;
	public void loadClass(ModuleLoader loader) throws Exception ;
	public boolean isLoadModuleDinamically();
}
