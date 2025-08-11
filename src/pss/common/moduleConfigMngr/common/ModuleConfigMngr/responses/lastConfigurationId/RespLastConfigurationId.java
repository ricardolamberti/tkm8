package pss.common.moduleConfigMngr.common.ModuleConfigMngr.responses.lastConfigurationId;

import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;

import pss.core.connectivity.messageManager.common.message.MessageComponent;

public class RespLastConfigurationId extends MessageComponent {
  private static final long serialVersionUID = -5026590964982120263L;
	protected Queue<RespConfigurationId> respConfigIdList = null;

	public Queue<RespConfigurationId> getRespConfigIdList() {
		if (this.respConfigIdList == null) {
			this.respConfigIdList = new LinkedList<RespConfigurationId>();
		}
		return this.respConfigIdList;
	}

	public void addLastConfigurationId(RespConfigurationId zRespConfId) throws Exception {
		this.getRespConfigIdList().add(zRespConfId);
	}

	public void addLastConfigurationId(String moduleId, String configType, long lastConfigId, Date lastConfigIdDate) throws Exception {
		this.getRespConfigIdList().add(new RespConfigurationId(moduleId, configType, lastConfigId, lastConfigIdDate));
	}

	public RespConfigurationId getRespConfigurationId(String moduleId, String configType) {
		for (RespConfigurationId rCI : this.getRespConfigIdList()) {
			if (rCI.getModuleId().equals(moduleId) == false) {
				continue;
			}
			if (rCI.getConfigType().equals(configType) == false) {
				continue;
			}
			return rCI;
		}
		return null;
	}
	
}
