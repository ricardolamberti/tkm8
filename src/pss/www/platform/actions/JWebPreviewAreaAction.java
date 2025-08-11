/*
 * Created on 17-jun-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.platform.actions;

public class JWebPreviewAreaAction extends JWebViewAreaAction {

  JWebPreviewAreaAction() {
  	super("do-PreviewAreaAction", false);
  }

  @Override
  public String getAjaxContainer() throws Exception {
  	return "preview_panel_"+this.getProviderName();
  }

}
