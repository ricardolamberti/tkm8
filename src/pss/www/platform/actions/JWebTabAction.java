
package pss.www.platform.actions;

public class JWebTabAction extends JWebServerAction {

	String forcedProviderName="";
	
  JWebTabAction() {
  	super("do-tabRefresh", false);
  }

  @Override
  public String getAjaxContainer() throws Exception {
  	return getForcedProviderName();
  }

	public String getForcedProviderName() {
		return forcedProviderName;
	}

	public void setForcedProviderName(String forcedProviderName) {
		this.forcedProviderName = forcedProviderName;
	}
	
  @Override
	protected String getProviderName() throws Exception {
  	if (!getForcedProviderName().equals(""))
  		return getForcedProviderName();

  	return super.getProviderName();
  }

	

}
