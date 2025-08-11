package pss.www.platform.actions;

public class JWebListExpandAreaResponsiveAction  extends JWebViewAreaAction {
	String ajaxContainer;

	JWebListExpandAreaResponsiveAction(String zAjaxContainer) {
		super("do-ListExpandAreaAction", false);
		ajaxContainer=zAjaxContainer;
  }

  @Override
  public String getAjaxContainer() throws Exception {
  	return ajaxContainer;
  }

}
