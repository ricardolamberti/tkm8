/*
 * Created on 27-jun-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.ui.views;

import pss.www.ui.JWebView;
import pss.www.ui.JWebViewInternalComposite;
import pss.www.ui.processing.JWebCanvas;


public class JCanvasWrapperView extends JWebView {


  private JWebViewInternalComposite oCanvas;

  public JCanvasWrapperView(JWebCanvas zCanvas) {
    this.oCanvas = (JWebViewInternalComposite) zCanvas;
  }
  
  @Override
	protected void build() throws Exception {
    this.add(oCanvas.getName(), this.oCanvas);
    
  }


}
