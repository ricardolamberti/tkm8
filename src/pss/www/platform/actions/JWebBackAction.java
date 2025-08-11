/*
 * Created on 17-jun-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.platform.actions;

public class JWebBackAction extends JWebServerAction {

  JWebBackAction() {
  	super("do-BackAction", false);
  }
  @Override
  public String getAjaxContainer() throws Exception {return hasAjaxContainer()?super.getAjaxContainer(): "view_area_and_title";}

  @Override
	public String getDescription() {return "Atrás";}
  
}
