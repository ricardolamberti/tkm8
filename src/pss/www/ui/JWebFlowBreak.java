/*
 * Created on 21-jul-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.ui;

import java.awt.Dimension;

import pss.core.winUI.responsiveControls.JFormLineResponsive;
import pss.www.platform.content.generators.JXMLContent;

public class JWebFlowBreak extends JWebViewFixedComponent implements JWebControlInterface {

  private boolean bAppliedByLayout;
  private boolean withLine;

  
  public static JWebFlowBreak create(JWebViewComposite parent, JFormLineResponsive c) throws Exception {
  	JWebFlowBreak webLabel = new JWebFlowBreak();
    webLabel.takeAttributesFormControlResponsive(c);
    webLabel.setWithLine(c.isWithLine());
     if (parent!=null)
    	parent.addChild(webLabel.getObjectName(), webLabel);
    return webLabel;
  }
  
  public boolean isWithLine() {
		return withLine;
	}

	public JWebFlowBreak setWithLine(boolean withLine) {
		this.withLine = withLine;
		return this;
	}

	@Override
	public String getComponentType() {
    return "break";
  }
  
  @Override
	public boolean isBreak() {
    return true;
  }

  @Override
	public Dimension getDefaultSize() {
    return new Dimension(0, 0); 
  }

  @Override
	public void setX(int zValue) {
    super.setX(zValue);
    this.bAppliedByLayout = true;
  }
  @Override
	public void setY(int zValue) {
    super.setY(zValue);
    this.bAppliedByLayout = true;
  }

  @Override
	protected void componentToXML(JXMLContent zContent) throws Exception {
    if (this.bAppliedByLayout) {
      zContent.setAttribute("appled_by_layout", true);
    }
    if (this.isWithLine()) {
      zContent.setAttribute("with_line", true);
    }
    
  }

	@Override
	public void onShow(String modo) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
