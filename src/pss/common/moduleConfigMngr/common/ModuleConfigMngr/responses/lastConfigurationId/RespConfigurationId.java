package  pss.common.moduleConfigMngr.common.ModuleConfigMngr.responses.lastConfigurationId;

import java.util.Date;

public class RespConfigurationId {
	private String moduleId = null;
	private String configType = null;
	private long lastConfigId = 0;
	private Date lastConfigIdDate = null;

	public RespConfigurationId(String moduleId, String configType, long lastConfigId, Date lastConfigIdDate) {
	  this.moduleId = moduleId;
	  this.configType = configType;
	  this.lastConfigId = lastConfigId;
	  this.lastConfigIdDate = lastConfigIdDate;
  }

	public String getModuleId() {
  	return moduleId;
  }

	public String getConfigType() {
  	return configType;
  }

	public long getLastConfigId() {
  	return lastConfigId;
  }

	public Date getLastConfigIdDate() {
  	return lastConfigIdDate;
  }	
	
}
