package pss.www.ui;

import java.awt.Dimension;

import pss.core.winUI.responsiveControls.JFormCheckResponsive;
import pss.www.platform.content.generators.JXMLContent;

public class JWebCollapseResponsive extends JWebViewEditComponent implements JWebLabelHolder {


	public JWebCollapseResponsive() {
  }
	
	private String sLabel = "";
	private String sDataTarget = "";
	private boolean bShow=true;
  private String image;
	
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}


  @Override
	public void destroy() {
    this.sLabel = null;
    super.destroy();
  }
	
  public JWebCollapseResponsive(String zLabel) {
    this.sLabel = zLabel;
  }

	@Override
	public String getComponentType() {
		return "collapsable_responsive";
	}

	@Override
	protected void widgetToXML(JXMLContent zContent) throws Exception {
      zContent.setAttribute("image", image);
      zContent.setAttribute("data-toggle", bShow?"collapse":"collapse");
      zContent.setAttribute("data-target", "#"+sDataTarget);
 	}
	
  public void setLabel(String zLabel) {
    this.sLabel = zLabel;
  }

	public String getLabel() {
    return this.sLabel;
	}
 
	public String getsDataTarget() {
		return sDataTarget;
	}

	public void setsDataTarget(String sDataTarget) {
		this.sDataTarget = sDataTarget;
	}
  //
  //  value providing
  //

  @Override
	public String getValueAsUIString() throws Exception {
    return  getValue().toString();
  }

  @Override
	public void setValueFromUIString(String zValue) {
    this.bShow = zValue != null && zValue.trim().equalsIgnoreCase("S");
  }
  
  @Override
	public String getValueAsDBString() throws Exception {
    if (this.bShow) {
      return "S";
    }  else {
      return "N";
    }
  }

  @Override
	public void setValueFromDBString(String zDBString) throws Exception {
    this.bShow = zDBString != null && (zDBString.trim().equalsIgnoreCase("S") || zDBString.trim().equalsIgnoreCase("Y"));
  }
  
  @Override
	public Object getValue() {
    if (this.bShow) {
      return Boolean.TRUE;
    }  else {
      return Boolean.FALSE;
    }
  }
  
  @Override
	public void setValue(Object zValue) throws Exception {
    this.bShow = zValue != null && ((Boolean)zValue).booleanValue();
  }

  public static JWebCheckResponsive create(JWebViewComposite parent, JFormCheckResponsive control) throws Exception {
  	JWebCheckResponsive webCheck = new JWebCheckResponsive();
	  webCheck.takeAttributesFormControlResponsive(control);
	  webCheck.setResponsive(control.isResponsive());
	 
	  if (parent!=null)
		  parent.addChild(control.getName(), webCheck);
	  return webCheck;
  }

	@Override
	protected Dimension getDefaultSize() throws Exception {
		return null;
	}
}
