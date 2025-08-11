/*
 * Created on 17-jun-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.platform.actions;

public class JWebBackToQueryAction extends JWebServerAction {

  JWebBackToQueryAction() {
  	super("do-BackToQueryAction", false);
  }
  @Override
  public String getAjaxContainer() throws Exception {return  "view_area_and_title";}

  @Override
	public String getDescription() {return "Atrás";}
  
}
