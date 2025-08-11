/*
 * Created on 17-jun-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.platform.actions;

public class JWebExcludeColumnsAction extends JWebServerAction {

  JWebExcludeColumnsAction() {
  	super("do-excludecolumns", false);
  }
  @Override
	public String getAjaxContainer() {return "view_area_and_title";}

  @Override
	public String getDescription() {return "Excluir Columnas";}
  
}
