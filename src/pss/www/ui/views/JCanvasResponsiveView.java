package pss.www.ui.views;

import pss.www.ui.JWebDivResponsive;
import pss.www.ui.JWebView;
import pss.www.ui.JWebViewInternalComposite;
import pss.www.ui.processing.JWebCanvas;

public class JCanvasResponsiveView extends JWebView {


  private JWebViewInternalComposite oCanvas;

  
  public JCanvasResponsiveView(JWebCanvas zCanvas) {
    this.oCanvas = (JWebViewInternalComposite) zCanvas;
  }
  
  @Override
	protected void build() throws Exception {
	JWebDivResponsive workArea = new JWebDivResponsive();
	workArea.setClassResponsive("workarea");
	workArea.add(oCanvas.getName(), this.oCanvas);
	this.add("workarea", workArea);
    
  }



}
