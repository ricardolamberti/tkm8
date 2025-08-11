package pss.core.winUI.responsiveControls;

import pss.core.services.fields.JObject;
import pss.core.winUI.controls.JFormControl;

//========================================================================== //
// Clase para el manejo standard de EditBoxes
//========================================================================== //
public class JFormEditResponsive extends JFormControlResponsive  {
//  public static final int ALIGN_DEFAULT = 0;
  
//  public static final int HALIGN_LEFT = 1;
//  public static final int HALIGN_CENTER = 2;
//  public static final int HALIGN_RIGHT = 3;
//
//  public static final int VALIGN_TOP = 4;
//  public static final int VALIGN_MIDDLE = 5;
//  public static final int VALIGN_BOTTOM = 6;
 
  private boolean outstanding = false; //destacado
//	private int iTextHAlignment = ALIGN_DEFAULT;
//	private int iTextVAlignment = ALIGN_DEFAULT;

  String oDato;
	boolean autocomplete=true;
	
	
	//-------------------------------------------------------------------------//
  // Constructor
  //-------------------------------------------------------------------------//
  public JFormEditResponsive() {
  }
  
	public String getTipoDatoConvert() { // ver de unificar para no hacer estas negradas
		if (GetTipoDato().equals("CHAR")) return JObject.JSTRING;
		return this.GetTipoDato();
	}

//  public int getTextHAlignment() {
//		return iTextHAlignment;
//	}
//
//	public JFormEditResponsive setTextHAlignment(int iTextHAlignment) {
//		this.iTextHAlignment = iTextHAlignment;
//		return this;
//	}
//
//	public int getTextVAlignment() {
//		return iTextVAlignment;
//	}
//
//	public JFormEditResponsive setTextVAlignment(int iTextVAlignment) {
//		this.iTextVAlignment = iTextVAlignment;
//		return this;
//	}
	
	public boolean isAutocomplete() {
		return autocomplete;
	}
	public JFormEditResponsive setAutocomplete(boolean autocomplete) {
		this.autocomplete = autocomplete;
		return this;
	}
//	@Override
//	public void BaseToControl(String zModo, boolean userRequest) throws Exception {
////    if (this.getProp() == null) return;
////    if (zModo.equals(JBaseForm.MODO_CONSULTA)/* || this.ifReadOnly() */)
////    	setValue( this.getProp().toFormattedString() );
////    else 
////      setValue( this.getProp().toInputString() );
//  }


  //-------------------------------------------------------------------------//
  // Obtengo el valor del campo
  //-------------------------------------------------------------------------//
  @Override
	public String getSpecificValue() throws Exception {
  	updateFromProp();
    return oDato;
  }

  @Override
  public void clear() throws Exception {
  	if (this.getProp()==null) return;
  	getProp().setNull();
  	updateFromProp();
  }
  //-------------------------------------------------------------------------//
  // Obtengo el valor del campo
  //-------------------------------------------------------------------------//
  @Override
	public boolean hasValue() throws Exception {
  	oDato=getSpecificValue();
    if ( oDato == null       ) return false;
    if ( oDato.trim().equals( "" )  ) return false;
    return true;
  }

  @Override
	public void setValue( String zVal ) throws Exception {
  	super.setValue(zVal);
  	updateFromProp();
  }


  public boolean isOutstanding() {
		return outstanding;
	}
	public JFormEditResponsive setOutstanding(boolean outstanding) {
		this.outstanding = outstanding;
		return this;
	}
	
	private void updateFromProp() throws Exception {
		if (getProp()==null || getProp().isNull()) {
			this.oDato = "";
			return;
		}
    if (showFormatted())
    	this.oDato =( this.getProp().toFormattedString() );
    else 
    	this.oDato =( this.getProp().toInputString() );

	}

	public int findHAlignDefault() {
		if (this.isNumerico())
			return JFormControl.HALIGN_RIGHT;
		return JFormControl.HALIGN_LEFT;
	}

}
