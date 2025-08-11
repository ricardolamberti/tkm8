package pss.www.platform.actions;

public class JWebFormLovRefreshAction extends JWebServerAction {

	JWebFormLovRefreshAction(String zURI, boolean zIsSubmit) {
    super(zURI, zIsSubmit);
  }

	JWebFormLovRefreshAction() {
  	super("do-FormLovRefreshAction", true);
  	this.setAjaxContainer("win_list_complete");
  }
}