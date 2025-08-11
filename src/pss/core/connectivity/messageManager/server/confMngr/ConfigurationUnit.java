package pss.core.connectivity.messageManager.server.confMngr;

import java.util.LinkedList;
import java.util.Queue;

import pss.core.tools.JExcepcion;
import pss.core.tools.PssLogger;

public class ConfigurationUnit {
	private String configUnitName = null;
	private Class<IMMConfigurationSubscriptor> configUnitClass = null;
  private Queue<String> classesToSubscribeQueue = null;

	public ConfigurationUnit() {
	}

  public Queue<String> getClassesToSubscribeMap() {
		if (this.classesToSubscribeQueue == null) {
			this.classesToSubscribeQueue = new LinkedList<String>();
		}
		return this.classesToSubscribeQueue;
	}

  public void addClassToSubscribe(String zClassToSubscribe) throws Exception {
		getClassesToSubscribeMap().add(zClassToSubscribe);
	}
	
	public IMMConfigurationSubscriptor getConfigUnitClass() {
		if (this.configUnitClass == null)
			return null;
		try {
			return (IMMConfigurationSubscriptor) this.configUnitClass.newInstance();
		} catch (Exception e) {
			PssLogger.logError(e, "Error obteniendo objecto a configurar [" + configUnitClass.getName() + "]");
			return null;
		}
	}

	public String getConfigurationSubscriptorName() {
		if (this.configUnitClass == null) {
			return new String("");
		}
		return this.configUnitClass.getName();
	}
	
	@SuppressWarnings("unchecked")
  public void setConfigUnitClass(String zConfigUnitClassName) throws Exception {

		if (IMMConfigurationSubscriptor.class.isAssignableFrom(Class.forName(zConfigUnitClassName)) == false) {
			JExcepcion.SendError("La clase ["+ zConfigUnitClassName +"] debe implementar la interfaz ["+IMMConfigurationSubscriptor.class.getSimpleName()+"] para poder representar una unidad de configuracion");
		}

		this.configUnitClass = (Class<IMMConfigurationSubscriptor>) Class.forName(zConfigUnitClassName);
	}

	public String getConfigUnitName() {
		return configUnitName;
	}

	public void setConfigUnitName(String configUnitName) {
		this.configUnitName = configUnitName;
	}


}
