package pss.common.moduleConfigMngr.common.ModuleConfigMngr.requests.lastConfigurationId;

public class ReqConfigurationId {
	private String moduleId = null;
	private String configType = null;

	public ReqConfigurationId(String zModuleId, String zConfigType) {
	  this.moduleId = zModuleId;
	  this.configType = zConfigType;
  }

	public String getModuleId() {
  	return moduleId;
  }

	public String getConfigType() {
  	return configType;
  }

}
