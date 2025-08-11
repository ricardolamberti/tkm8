package  pss.common.moduleConfigMngr.common.ModuleConfigMngr.requests.newConfigurationApplied;

import java.util.LinkedList;
import java.util.Queue;

import pss.common.moduleConfigMngr.actions.ModuleConfAction;
import pss.common.moduleConfigMngr.transactions.ModuleConfTransaction;
import pss.core.connectivity.messageManager.common.message.ReqMessage;

/**
 * @author Pentaware S.A.
 */
public class ReqNewConfigurationApplied extends ReqMessage {
	/**
   * 
   */
  private static final long serialVersionUID = -4061910463414372319L;
  
	private String company = null;
	private String country = null;
	private String node = null;	
	private String moduleId = null;
	private String configType = null;
	private Queue<ModuleConfTransaction> transactionsList = null;
	private Queue<ModuleConfAction> actionList = null;

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

	public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	public String getConfigType() {
		return configType;
	}

	public void setConfigType(String configType) {
		this.configType = configType;
	}

	public Queue<ModuleConfTransaction> getTransactionList() {
		if (this.transactionsList == null) {
			this.transactionsList = new LinkedList<ModuleConfTransaction>();
		}
		return this.transactionsList;
	}
	
	public boolean ifHaveTransactions() {
		if (this.transactionsList == null) 
			return false;
		
		return (this.getTransactionList().isEmpty() == false);
	}

	public void addNewTransaction(ModuleConfTransaction zMCT) {
		this.getTransactionList().add(zMCT);
	}

	public void addNewTransaction(String trxDescription, String transaction) {
		this.getTransactionList().add(new ModuleConfTransaction(trxDescription, transaction));
	}
	
  public Queue<ModuleConfAction> getActionList() {
  	if (this.actionList == null) {
  		this.actionList = new LinkedList<ModuleConfAction>();
  	}
  	return this.actionList;
  }
  
  public boolean ifHaveActions() {
  	if (this.actionList == null)
  		return false;
  	return (this.actionList.isEmpty() == false);
  }
	
  public void addNewAction(ModuleConfAction zMCA) {
  	this.getActionList().add(zMCA);
  }
  
  public void addNewAction(String actionId, String actionData) {
  	this.addNewAction(new ModuleConfAction(actionId, actionData));
  }
  
}
