package pss.www.ui;

import pss.core.winUI.responsiveControls.JFormLabelResponsive;
import pss.www.platform.content.generators.JXMLContent;
import pss.www.ui.controller.JWebFormControl;

public class JWebLabelResponsive extends JWebViewFixedComponent implements JWebCompoundLabelHolder, JWebControlInterface {

  private String sLabel;
//  private int iTextHAlignment = JFormControl.ALIGN_DEFAULT;
//  private int iTextVAlignment = JFormControl.ALIGN_DEFAULT;

  private int iTextPosition = TEXT_POSITION_DEFAULT;
  private int iTextIconGap = TEXT_ICON_GAP_DEFAULT;
  private String styleImage;
  public String getStyleImage() {
		return styleImage;
	}
	public void setStyleImage(String style) {
		this.styleImage = style;
	}

	private JWebIcon oIcon;


  public JWebLabelResponsive() {
  }
  public JWebLabelResponsive(String zLabel) {
    this.sLabel = zLabel;
  }
  public JWebLabelResponsive(String zLabel, JWebIcon zIcon) {
    this.sLabel = zLabel;
    this.oIcon = zIcon;
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
    return "label_responsive";
  }

  @Override
	protected void componentToXML(JXMLContent zContent) throws Exception {
  }
  

  public String getLabel() {
    return this.sLabel;
  }

  public void setLabel(String string){
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

  public void setIcon(JWebIcon icon) {
    this.oIcon = icon;
  }
  public JWebIcon getMaximizeIcon() {
		return null;
	}
  public static JWebLabelResponsive create(JWebViewComposite parent, JFormLabelResponsive c) throws Exception {
  	JWebLabelResponsive webLabel = new JWebLabelResponsive();
    webLabel.takeAttributesFormControlResponsive(c);
    webLabel.setLabel(c.getLabel());
//    webLabel.setHAlignment(c.getHAlignment());
//    webLabel.setVAlignment(c.getVAlignment());
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
