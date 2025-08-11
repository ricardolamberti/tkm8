package pss.www.platform.actions;

public class JWebPartialRefreshFormAction extends JWebServerAction {

	JWebPartialRefreshFormAction(String zURI, boolean zIsSubmit) {
    super(zURI, zIsSubmit);
  }

	JWebPartialRefreshFormAction() {
  	super("do-PartialRefreshForm", true);
  }
	
	String ajaxContainer; 

  public void setAjaxContainer(String ajaxContainer) {
		this.ajaxContainer = ajaxContainer;
	}

	@Override
  public String getAjaxContainer() {
  	return ajaxContainer==null?"view_area_and_title":ajaxContainer;
  	
  }

  
}
