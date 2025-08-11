package pss.www.platform.actions;

public class JWebPartialRefreshPreviewFormAction extends JWebServerAction {

	JWebPartialRefreshPreviewFormAction(String zURI, boolean zIsSubmit) {
    super(zURI, zIsSubmit);
  }

	JWebPartialRefreshPreviewFormAction() {
  	super("do-PartialRefreshPreviewForm", true);
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
