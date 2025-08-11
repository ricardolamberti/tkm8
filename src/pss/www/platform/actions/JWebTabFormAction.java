package pss.www.platform.actions;


public class JWebTabFormAction extends JWebServerAction {

	String forcedProviderName="";
	
	JWebTabFormAction(boolean zIsSubmit) {
  	super("do-tabFormAction", zIsSubmit);
  }

  @Override
  public String getAjaxContainer() {return "nb_page_area";}

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
