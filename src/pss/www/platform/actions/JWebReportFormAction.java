package pss.www.platform.actions;

public class JWebReportFormAction extends JWebServerAction {



	JWebReportFormAction(boolean zIsSubmit) {
  	super("modaldo-ReportFormAction", zIsSubmit);
  }

  @Override
  public String getAjaxContainer() throws Exception {
  	return "modal_view_area";//luego agrega modal_
  }

}
