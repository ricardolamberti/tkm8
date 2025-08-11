/*
 * Created on 16-oct-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.ui;

import java.awt.Dimension;

import pss.core.ui.graphics.JDrawing;
import pss.www.platform.content.generators.JXMLContent;


public class JWebDrawingHolder extends JWebViewComponent {

  private JDrawing oDrawing;
  
  
  public JWebDrawingHolder(JDrawing zDrawing) {
    this.oDrawing = zDrawing;
  }


  @Override
	public void destroy() {
    this.oDrawing = null;
    super.destroy();
  }

  @Override
	public String getComponentType() {
    return "drawing";
  }

  @Override
	protected void componentToXML(JXMLContent zContent) throws Exception {
  }

  @Override
	protected Dimension getDefaultSize() throws Exception {
    return new Dimension(300,200);
  }

  public JDrawing getDrawing() {
    return this.oDrawing;
  }

  public void setDrawing(JDrawing drawing) {
    this.oDrawing = drawing;
  }

}
