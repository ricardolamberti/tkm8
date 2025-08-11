package  pss.common.moduleConfigMngr.actions;

import java.io.Serializable;

public class ModuleConfAction implements Serializable {
	/**
   * 
   */
  private static final long serialVersionUID = 1602775937180934507L;
  public static final String NONE_ACTION_ID = "NONE";
	public static final String DOWNLOAD_ACTION_ID = "DOWNLOAD";
	public static final String APPLY_ACTION_ID = "APPLY";
	
	public static String getActionDescription(String zAction) {
		if (zAction.equalsIgnoreCase(DOWNLOAD_ACTION_ID)) return "Download Configuration";
		if (zAction.equalsIgnoreCase(APPLY_ACTION_ID)) return "Aplicar Configuracion";
		return new String("Unknown action ["+zAction+"]");
	}
	
	protected String actionId = null;
	protected String actionData = null;
	
	public ModuleConfAction() {
		
	}

	public ModuleConfAction(String actionId, String actionData) {
	  this.actionId = actionId;
	  this.actionData = actionData;
  }

	public String getActionId() {
  	return actionId;
  }

	public void setActionId(String actionId) {
  	this.actionId = actionId;
  }

	public String getActionData() {
  	return actionData;
  }

	public void setActionData(String actionData) {
  	this.actionData = actionData;
  }
	
}
