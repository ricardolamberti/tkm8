package pss.core.connectivity.messageManager.server.confMngr;

import java.util.Collection;
import java.util.HashMap;

import pss.core.tools.PssLogger;

public class ConfigurationActions {
	private String configActionName = null;
	private HashMap<String, ConfigurationUnit> confUnitMap = null;

	public ConfigurationActions(String zConfActionName) {
		this.configActionName = zConfActionName;
	}

	public void addConfigurationUnit(ConfigurationUnit zConfUnit) throws Exception {
		if (getConfUnitMap().containsKey(zConfUnit.getConfigUnitName())) {
			PssLogger.logDebug("Warning: La unidad de configuracion [" + zConfUnit.getConfigUnitName() + "] ya esta subscipta para [" + this.configActionName + "]");
			return;
		}
		
		this.getConfUnitMap().put(zConfUnit.getConfigUnitName(), zConfUnit);
	}
	
	public String getConfActionsName() {
		if (this.configActionName == null) {
			this.configActionName = new String("");
		}
		return configActionName;
	}
	
	public HashMap<String, ConfigurationUnit> getConfUnitMap() {
		if (this.confUnitMap == null) {
			this.confUnitMap = new HashMap<String, ConfigurationUnit>();
		}
		
		return this.confUnitMap;
	}

	public Collection<ConfigurationUnit> getSubscriptedConfUnitsCollection() {
		return this.getConfUnitMap().values();
	}
	
}
