package pss.www.platform.actions;

public class JWebModalExceptionAction extends JWebServerAction {

	JWebModalExceptionAction(boolean zIsSubmit) {
  	super("modaldo-exceptionaction", zIsSubmit);
  }

  @Override
  public String getAjaxContainer() throws Exception {
  	return "view_area";//luego agrega modal_
  }

  
}
