
package pss.www.platform.actions;

public class JWebWinListDeleteAction extends JWebServerAction {

  JWebWinListDeleteAction() {
  	super("do-WinListDeleteAction", false);
  }

  @Override
  public String getAjaxContainer() { 	return "view_area_and_title";}

  
}
