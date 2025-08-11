/*
 * Created on 17-jun-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.platform.actions;

public class JWebViewAreaAndTitleAction extends JWebServerAction {

  JWebViewAreaAndTitleAction(boolean zIsSubmit) {
  	super("do-ViewAreaAndTitleAction", zIsSubmit);
  }

  @Override
  public String getAjaxContainer() {
  	return "view_area_and_title";
  }

}
