/*
 * Created on 17-jun-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.ui;

import javax.swing.JLabel;

import pss.core.winUI.controls.JFormControl;
import pss.www.platform.content.generators.JXMLContent;
import pss.www.ui.controller.JWebFormControl;


/**
 * 
 * 
 * Created on 17-jun-2003
 * @author PSS
 */

public class JWebLabel extends JWebViewFixedComponent implements JWebCompoundLabelHolder, JWebControlInterface {

  private String sLabel;
//  private int iTextHAlignment = JFormControl.ALIGN_DEFAULT;
//  private int iTextVAlignment = JFormControl.ALIGN_DEFAULT;

  private int iTextPosition = TEXT_POSITION_DEFAULT;
  private int iTextIconGap = TEXT_ICON_GAP_DEFAULT;
  private String styleImage;


	private JWebIcon oIcon;


  public JWebLabel() {
  }
  public JWebLabel(String zLabel) {
    this.sLabel = zLabel;
  }
  public JWebLabel(String zLabel, JWebIcon zIcon) {
    this.sLabel = zLabel;
    this.oIcon = zIcon;
  }
  public String getStyleImage() {
		return styleImage;
	}
	public void setStyleImage(String imageStyle) {
		this.styleImage = imageStyle;
	}

	@Override
	public void destroy() {
    this.oIcon = null;
    this.sLabel = null;
    super.destroy();
  }

  public String getDisplayValue() throws Exception {
		return this.getSpecificValue();
	}
	
  @Override
	public String getComponentType() {
    return "label";
  }

  @Override
	protected void componentToXML(JXMLContent zContent) throws Exception {
  }
  

  public String getLabel() {
    return this.sLabel;
  }

  public void setLabel(String string) {
    this.sLabel = string;
  }


//  public int getHAlignment() {
//    return this.iTextHAlignment;
//  }
//
//  public int getVAlignment() {
//    return this.iTextVAlignment;
//  }
//
//  public void setHAlignment(int i) {
//    this.iTextHAlignment = i;
//  }
//
//  public void setVAlignment(int i) {
//    this.iTextVAlignment = i;
//  }
//
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

  public JWebIcon getIcon() {
    return this.oIcon;
  }
  public JWebIcon getMaximizeIcon() {
		return null;
	}
  public void setIcon(JWebIcon icon) {
    this.oIcon = icon;
  }
  
  public static JWebLabel create(JWebViewComposite parent, JLabel zComp,JFormControl c) throws Exception {
    JWebLabel webLabel = new JWebLabel();
    webLabel.setLabel(zComp.getText());
    webLabel.setResponsive(c.isResponsive());
    webLabel.takeAttributesForm(parent,zComp);
    if (c!=null)
    	webLabel.setSkinStereotype(c.getSkinStereotype());

    if (parent!=null)
    	parent.addChild(webLabel.getObjectName(), webLabel);
    return webLabel;
  }
  

	public void setEditable(boolean editable) throws Exception {}
  public void clear() throws Exception {}
  public String getSpecificValue() throws Exception {return sLabel;}
  public void setValue(String zVal) throws Exception {this.sLabel=zVal;}
	public void setValueFromUIString(String val) throws Exception {
		this.setValue(val);
	}
  public void setController(JWebFormControl control) {}
	  
	public void onShow(String mode) throws Exception {
	}

    
}
