package  pss.common.moduleConfigMngr.transactions;

import java.io.Serializable;

public class ModuleConfTransaction implements Serializable{

  private static final long serialVersionUID = 7050172900921989759L;
  protected String trxDescription;
  protected String transaction;
	
  public ModuleConfTransaction(String trxDescription, String transaction) {
	  this.trxDescription = trxDescription;
	  this.transaction = transaction;
  }
  
  public ModuleConfTransaction() {
  }

	public String getTrxDescription() {
  	return trxDescription;
  }

	public void setTrxDescription(String trxDescription) {
  	this.trxDescription = trxDescription;
  }

	public String getTransaction() {
  	return transaction;
  }

	public void setTransaction(String transaction) {
  	this.transaction = transaction;
  }
  

}
