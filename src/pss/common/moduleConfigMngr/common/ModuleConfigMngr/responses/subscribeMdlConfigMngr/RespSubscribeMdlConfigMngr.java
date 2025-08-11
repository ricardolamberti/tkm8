package  pss.common.moduleConfigMngr.common.ModuleConfigMngr.responses.subscribeMdlConfigMngr;

import pss.common.moduleConfigMngr.common.ModuleConfigMngr.responses.lastConfigurationId.RespConfigurationId;
import pss.core.connectivity.messageManager.common.message.MessageComponent;

public class RespSubscribeMdlConfigMngr extends MessageComponent {
  private static final long serialVersionUID = -5026590964982120263L;
	protected RespConfigurationId respConfigId = null;
	
	public RespConfigurationId getRespConfigId() {
  	return respConfigId;
  }
	public void setRespConfigId(RespConfigurationId respConfigId) {
  	this.respConfigId = respConfigId;
  }

}
