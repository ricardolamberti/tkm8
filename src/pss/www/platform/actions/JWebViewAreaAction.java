/*
 * Created on 17-jun-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.platform.actions;

public class JWebViewAreaAction extends JWebServerAction {


  JWebViewAreaAction(String url, boolean zIsSubmit) {
  	super(url, zIsSubmit);
  }
  
  JWebViewAreaAction(boolean zIsSubmit) {
  	super("do-ViewAreaAction", zIsSubmit);
  }

  @Override
  public String getAjaxContainer() throws Exception {
  	return "view_area";
  }

}
