package pss.www.platform.actions;

public class JWebViewAllPanelsAction extends JWebServerAction {

	JWebViewAllPanelsAction(boolean zIsSubmit) {
  	super("do-allPanelsAction", zIsSubmit);
  }

  @Override
  public String getAjaxContainer() {
  	return "view_area_and_title";
  }

}
