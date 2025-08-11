/*
 * Created on 17-jun-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.platform.actions;

public class JWebSecurityAction extends JWebServerAction {

  JWebSecurityAction() {
  	super("do-security", false);
  }
  @Override
	public String getAjaxContainer() {return "view_area_and_title";}

  @Override
	public String getDescription() {return "Seguridad";}
  
}
