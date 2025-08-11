package  pss.common.moduleConfigMngr.common.ModuleConfigMngr.requests.lastConfigurationId;

import java.util.LinkedList;
import java.util.Queue;

import pss.core.connectivity.messageManager.common.message.ReqMessage;

/**
 * @author Pentaware S.A.
 */
public class ReqLastConfigurationId extends ReqMessage {
  private static final long serialVersionUID = -4285381912324555663L;
  
	private String company = null;
	private String country = null;
	private String node = null;	
	private Queue<ReqConfigurationId> reqConfIdList = null;

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Queue<ReqConfigurationId> getReqConfigIdList() {
		if (this.reqConfIdList == null) {
			this.reqConfIdList = new LinkedList<ReqConfigurationId>();
		}
		return this.reqConfIdList;
	}
	
	public boolean ifHaveReqConfigId() {
		if (this.reqConfIdList == null) 
			return false;
		
		return (this.getReqConfigIdList().isEmpty() == false);
	}

	public void addReqConfigId(ReqConfigurationId zReqConfId) {
		this.getReqConfigIdList().add(zReqConfId);
	}

	public void addReqConfigId(String zModuleId, String zConfigType) {
		this.addReqConfigId(new ReqConfigurationId(zModuleId, zConfigType));
	}
  
}
