package pss.www.platform.actions;

public class JWebWinListEditAction extends JWebServerAction {

  JWebWinListEditAction() {
  	super("do-WinListEditAction", false);
  }

  @Override
  public String getAjaxContainer() { 	return "view_area_and_title";}

  
}
