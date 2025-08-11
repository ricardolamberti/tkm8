package pss.www.ui;

import java.awt.Dimension;

import pss.core.winUI.responsiveControls.JFormCheckResponsive;
import pss.www.platform.actions.JWebAction;
import pss.www.platform.content.generators.JXMLContent;

public class JWebCheckResponsive extends JWebViewEditComponent implements JWebLabelHolder,JWebActionable {

//	public static final String MODE_TOGGLE = "toggle";
//	public static final String MODE_SINGLE = "single";
//
//	public static final String STYLE_IOS = "ios";
//	public static final String STYLE_ANDROID = "android";
//
//	public static final String SIZE_LARGE = "large";
//	public static final String SIZE_NORMAL = "normal";
//	public static final String SIZE_SMALL= "small";
//	public static final String SIZE_MINI = "mini";


	private String sLabel = "";
	private boolean bCheched;
  private String mode;
  private boolean noForm;
  private String align;




	private String dataon;
	private String dataoff;
	private String datasize;
	private String datawidth;
	private String dataheight;
	private String dataoffstyle;
	private String dataonstyle;
	private String datastyle;

	

	

	private JWebAction webAction;

	
	public JWebCheckResponsive() {
  }

  @Override
	public void destroy() {
    this.sLabel = null;
    super.destroy();
  }

  public boolean getNoForm() {
		return noForm;
	}

	public void setNoForm(boolean noForm) {
		this.noForm = noForm;
	}
	
  public JWebCheckResponsive(String zLabel) {
    this.sLabel = zLabel;
  }

	@Override
	public String getComponentType() {
		if (noForm)
			return "check_box_responsive_noform";
		return "check_box_responsive";
	}


	@Override
	protected void widgetToXML(JXMLContent zContent) throws Exception {
    if (mode!=null) zContent.setAttribute("mode", mode);
    if (dataon!=null) zContent.setAttribute("dataon", dataon);
    if (dataoff!=null) zContent.setAttribute("dataoff", dataoff);
    if (dataoffstyle!=null) zContent.setAttribute("dataoffstyle", dataoffstyle);
    if (dataonstyle!=null) zContent.setAttribute("dataonstyle", dataonstyle);
    if (datawidth!=null) zContent.setAttribute("datawidth", datawidth);
    if (dataheight!=null) zContent.setAttribute("dataheight", dataheight);
    if (datastyle!=null) zContent.setAttribute("datastyle", datastyle);
    if (datasize!=null) zContent.setAttribute("datasize", datasize);
    zContent.setAttribute("align", align==null?"LEFT":align);
    zContent.setAttribute("checked", this.bCheched?"S":"N");
	}
	
  public void setLabel(String zLabel) {
    this.sLabel = zLabel;
  }

	public String getLabel() {
    return this.sLabel;
	}
  
  public boolean isChecked() {
    return this.bCheched;
  }

  public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getDataon() {
		return dataon;
	}
	public String getAlign() {
		return align;
	}

	public void setAlign(String horizontal) {
		this.align = horizontal;
	}
	public void setDataon(String dataon) {
		this.dataon = dataon;
	}

	public String getDataoff() {
		return dataoff;
	}

	public void setDataoff(String dataoff) {
		this.dataoff = dataoff;
	}

	public void setWebAction(JWebAction webAction) {
		this.webAction = webAction;
	}

	public String getDatasize() {
		return datasize;
	}

	public void setDatasize(String datasize) {
		this.datasize = datasize;
	}

	public String getDatawidth() {
		return datawidth;
	}

	public void setDatawidth(String datawidth) {
		this.datawidth = datawidth;
	}

	public String getDataheight() {
		return dataheight;
	}

	public void setDataheight(String dataheight) {
		this.dataheight = dataheight;
	}

	public String getDataoffstyle() {
		return dataoffstyle;
	}

	public void setDataoffstyle(String dataoffstyle) {
		this.dataoffstyle = dataoffstyle;
	}

	public String getDataonstyle() {
		return dataonstyle;
	}

	public void setDataonstyle(String dataonstyle) {
		this.dataonstyle = dataonstyle;
	}

	public String getDatastyle() {
		return datastyle;
	}

	public void setDatastyle(String datastyle) {
		this.datastyle = datastyle;
	}

  //
  //  value providing
  //

  @Override
	public String getValueAsUIString() throws Exception {
    return this.getValue().toString();
  }

  @Override
	public void setValueFromUIString(String zValue) {
    this.bCheched = zValue != null && zValue.trim().equalsIgnoreCase("S");
  }
  
  @Override
	public String getValueAsDBString() throws Exception {
    if (this.bCheched) {
      return "S";
    }  else {
      return "N";
    }
  }

  @Override
	public void setValueFromDBString(String zDBString) throws Exception {
    this.bCheched = zDBString != null && (zDBString.trim().equalsIgnoreCase("S") || zDBString.trim().equalsIgnoreCase("Y"));
  }
  
  @Override
	public Object getValue() {
    if (this.bCheched) {
      return Boolean.TRUE;
    }  else {
      return Boolean.FALSE;
    }
  }
  
  @Override
	public void setValue(Object zValue) throws Exception {
    this.bCheched = zValue != null && ((Boolean)zValue).booleanValue();
  }

  public static JWebCheckResponsive create(JWebViewComposite parent, JFormCheckResponsive control) throws Exception {
  	JWebCheckResponsive webCheck = new JWebCheckResponsive();
  	
  
	  webCheck.takeAttributesFormControlResponsive(control);
	  webCheck.setAlign(control.getAlign());
	  webCheck.setResponsive(control.isResponsive());
    webCheck.setMode(webCheck.findMode(control));
	  
	  if (webCheck.isToogle()) {
	    webCheck.setDataon(control.getDescripcionValorCheck());
		  webCheck.setDataoff(control.getDescripcionValorUnCheck());
		  webCheck.setLabel(control.getLabel());
			webCheck.setDatasize(JFormCheckResponsive.SIZE_SMALL);
			webCheck.setClassResponsive("label_checkbox");
			webCheck.setDataonstyle(control.getStyleOn());
			webCheck.setDataoffstyle(control.getStyleOff());
			webCheck.setDatastyle(control.getStyle());
			//webCheck.setNoForm(false);
	  } 
	  
	  if (parent!=null)
		  parent.addChild(control.getName(), webCheck);
	  return webCheck;
  }
  public JWebAction getWebAction() throws Exception {
  	if (webAction!=null) return webAction;
  	if (!this.hasToRefreshForm()) return null;
  	return this.getObjectProvider().getWebSourceAction();
  }

	@Override
	protected Dimension getDefaultSize() throws Exception {
		return null;
	}
	
	public boolean isToogle() throws Exception {
		return this.getMode().equals(JFormCheckResponsive.MODE_TOGGLE);
	}

	public String findMode(JFormCheckResponsive control) throws Exception {
		if (control.hasMode()) return control.getMode();
		return this.skin().findCheckModeDefault();
	}

	
}
