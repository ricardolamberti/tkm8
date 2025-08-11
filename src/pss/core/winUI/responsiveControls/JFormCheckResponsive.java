package pss.core.winUI.responsiveControls;

import pss.core.services.fields.JBoolean;

public class JFormCheckResponsive extends JFormControlResponsive  {
	public static final String LABEL_RIGHT = "RIGHT";
	public static final String LABEL_LEFT = "LEFT";
	public static final String LABEL_TOP = "TOP";
	public static final String LABEL_BOTTOM = "BOTTOM";
	public static final String MODE_TOGGLE = "toggle";
	public static final String MODE_SINGLE = "single";
	public static final String MODE_SQUARE= "square";
	public static final String MODE_BIGSQUARE = "bigsquare";
	public static final String MODE_CIRCLE = "circle";
	public static final String MODE_BIGCIRCLE = "bigcircle";
	public static final String MODE_ANIMATED = "animated";

	public static final String STYLE_IOS = "ios";
	public static final String STYLE_ANDROID = "android";

	public static final String SIZE_LARGE = "large";
	public static final String SIZE_NORMAL = "normal";
	public static final String SIZE_SMALL= "small";
	public static final String SIZE_MINI = "mini";

  //-------------------------------------------------------------------------//
  // Propiedades de la Clase
  //-------------------------------------------------------------------------//
  String  sValorCheck="S";
  String  sDescripValorCheck="Si";
  String  sValorUnCheck="N";
  String  sDescripValorUnCheck="No";
 // Boolean value;
  String align;


	String  sMode;
  String  styleOn;
  String  styleOff;
  String  style;


	//-------------------------------------------------------------------------//
  // Metodos de acceso a las Propiedades de la Clase
  //-------------------------------------------------------------------------//
  public String    GetValorCheck()             { return sValorCheck ; }
  public String    getDescripcionValorCheck()             { return sDescripValorCheck ; }
  public JFormCheckResponsive SetValorCheck( String zValue )   { sValorCheck = zValue; return this; }
  public JFormCheckResponsive setDescripcionValorCheck( String zValue )   { sDescripValorCheck = zValue; return this; }
  public String    GetValorUnCheck()           { return sValorUnCheck ; }
  public String    getDescripcionValorUnCheck()           { return sDescripValorUnCheck ; }
  public JFormCheckResponsive SetValorUnCheck( String zValue ) { sValorUnCheck = zValue; return this; }
  public JFormCheckResponsive setDescripcionValorUnCheck( String zValue ) { sDescripValorUnCheck = zValue; return this; }
  public void SetChecked()                     { getProp().setValue(true); }
  public void SetUnChecked()                   {  getProp().setValue(false); }
  public boolean isSelected() throws Exception {return getSpecificValue().equals(sValorCheck);}
	
  @Override
  public void initialize() throws Exception {
	  sValorCheck="S";
	  sDescripValorCheck="Si";
	  sValorUnCheck="N";
	  sDescripValorUnCheck="No";
	  super.initialize();
  }
  public String getStyleOn() {
		return styleOn;
	}
	public JFormCheckResponsive setStyleOn(String styleOn) {
		this.styleOn = styleOn;
		return this;
	}
	public String getStyleOff() {
		return styleOff;
	}
	public JFormCheckResponsive setStyleOff(String styleOff) {
		this.styleOff = styleOff;
		return this;
	}
	public JFormCheckResponsive setStyle(String style) {
		this.style = style;
		return this;
	}
	public String getStyle() {
		return style;
	}
	 public String getAlign() throws Exception {
	  	if (align==null) {
	   		if (getFormatFields()==null) return align;

	  		if (getFormatFields().equals(JFormPanelResponsive.FIELD_LABEL_VERTICAL))
	  				return JFormCheckResponsive.LABEL_TOP;
	  		if (getFormatFields().equals(JFormPanelResponsive.FIELD_LABEL_HORIZONTAL_LEFT))
					return JFormCheckResponsive.LABEL_LEFT;
	  		if (getFormatFields().equals(JFormPanelResponsive.FIELD_LABEL_HORIZONTAL_RIGHT))
					 return JFormCheckResponsive.LABEL_LEFT;
	  		
	  	}
			return align;
		}
	public JFormCheckResponsive setAlign(String zalign) {
		this.align = zalign;
		return this;
	}
  //-------------------------------------------------------------------------//
  // Constructor
  //-------------------------------------------------------------------------//
  public JFormCheckResponsive() {
	  sValorCheck   = "S";
    sValorUnCheck = "N";
  }


  //-------------------------------------------------------------------------//
  // Obtengo el valor del campo
  //-------------------------------------------------------------------------//
  @Override
	public String getSpecificValue() throws Exception {
  	if (getProp().isNull()) 
  		return sValorUnCheck;
  	if ( !getProp().isBoolean()) return getProp().toString();
    if ( getProp().isBoolean() && ((JBoolean)getProp()).getValue() )
      return sValorCheck;
    else
      return sValorUnCheck;
  }


  @Override
  public void clear() throws Exception {
  	getProp().setNull();
  }
  //-------------------------------------------------------------------------//
  // Obtengo el valor del campo
  //-------------------------------------------------------------------------//
  @Override
	public boolean hasValue() throws Exception {
		if (getProp()==null) return false;
		return getProp().isNotNull();
  }
	public void setValue(String zVal) throws Exception {
	 	if ( !getProp().isBoolean()) {
	 		getProp().setValue(zVal);
	 		return;
	 	}
 		getProp().setValue(zVal!=null && zVal.equalsIgnoreCase(GetValorCheck()));
	}
	

	@Override
	public void setForcedValue(String forcedValue) {
		if (forcedValue==null) return;
		if (forcedValue.equals("true"))
			super.setForcedValue("S");
		else if (forcedValue.equals("false"))
			super.setForcedValue("N");
		else
			super.setForcedValue(forcedValue);
	}
	
	public void hide() throws Exception {
		this.setVisible(false);
	}
  public String getMode() {
		return sMode;
	}
	public JFormCheckResponsive setMode(String sMode) {
		this.sMode = sMode;
		return this;
	}

	public boolean hasMode() {
		return this.sMode!=null;
	}
	

}
