package pss.www.platform.actions;

public class JWebModalViewAreaAction extends JWebServerAction {


	JWebModalViewAreaAction(String url, boolean zIsSubmit) {
  	super(url, zIsSubmit);
  }
  
	JWebModalViewAreaAction(boolean zIsSubmit) {
  	super("modaldo-ViewAreaAction", zIsSubmit);
  }

  @Override
  public String getAjaxContainer() throws Exception {
  	return "view_area";//luego agrega modal_
  }

}
