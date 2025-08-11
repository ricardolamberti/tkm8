package pss.www.platform.actions;

public class JWebDropAction extends JWebServerAction {

	JWebDropAction(boolean zIsSubmit) {
  	super("do-DropAction", zIsSubmit);
  }

  @Override
  public String getAjaxContainer() {
  	return "view_area_and_title";
  }

}