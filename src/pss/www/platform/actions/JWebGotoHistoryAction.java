package pss.www.platform.actions;

public class JWebGotoHistoryAction  extends JWebServerAction {

	JWebGotoHistoryAction() {
  	super("do-GotoHistoryAction", false);
  }
  @Override
  public String getAjaxContainer() {return "view_area_and_title";}

}
