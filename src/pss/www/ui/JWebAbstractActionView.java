/*
 * Created on 12-jun-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.ui;

import java.awt.Dimension;

import pss.www.platform.actions.JWebAction;
import pss.www.platform.content.generators.JXMLContent;

/**
 * 
 * 
 * Created on 12-jun-2003
 * @author PSS
 */

public abstract class JWebAbstractActionView extends JWebViewComponent implements JWebActionable, JWebActionHolder, JWebCompoundLabelHolder {


  private JWebAction oAction;
  private String sLabel;

//  private int iTextHAlignment = JFormControl.ALIGN_DEFAULT;
//  private int iTextVAlignment = JFormControl.ALIGN_DEFAULT;

  private int iTextPosition = TEXT_POSITION_DEFAULT;
  private int iTextIconGap = TEXT_ICON_GAP_DEFAULT;
  
  private boolean bVisibleWhenDisabled;
  
  private JWebIcon oIcon;

  @Override
	protected void componentToXML(JXMLContent zContent) throws Exception {

  }

  @Override
	public void destroy() {
    this.oAction = null;
    this.sLabel = null;
    super.destroy();
  }

  @Override
	public Dimension getDefaultSize() {
    return new Dimension(155, 32); 
  }
  
  public JWebIcon getIcon() {
    return this.oIcon;
  }

  public void setIcon(JWebIcon icon) {
    this.oIcon = icon;
  }
  
  public JWebIcon getMaximizeIcon() {
		return null;
	}
  public String getLabel() {
    return this.sLabel;
  }

  public void setLabel(String string) {
    this.sLabel = string;
  }

  public JWebAction getWebAction() throws Exception {
    return this.oAction;
  }
  
  @Override
  public String getStyleImage() {
  	return null;
  }

  public void setWebAction(JWebAction action) {
    this.oAction = action;
    if (this.getToolTip()==null && this.oAction!=null) {
      this.setToolTip(this.oAction.getDescription());
    }
  }


  public int getTextPosition() {
    return this.iTextPosition;
  }

  public int getTextIconGap() {
    return this.iTextIconGap;
  }

  public void setTextPosition(int i) {
    this.iTextPosition = i;
  }

  public void setTextIconGap(int i) {
    this.iTextIconGap = i;
  }


  public boolean isVisibleWhenDisabled() {
    return this.bVisibleWhenDisabled;
  }
  public void setVisibleWhenDisabled(boolean zVisibleWhenDisabled) {
    this.bVisibleWhenDisabled = zVisibleWhenDisabled;
  }

  @Override
	public boolean isVisible()  {
    try {
			boolean bResult = super.isVisible();
			if (!bResult) {
			  return false;
			}
			JWebAction oTheAction = this.getWebAction();
			if (oTheAction!=null) {
			  return this.isVisibleWhenDisabled() || oTheAction.isEnabled();
			}
			return bResult;
		} catch (Exception e) {
			return false;
		}
  }





}
