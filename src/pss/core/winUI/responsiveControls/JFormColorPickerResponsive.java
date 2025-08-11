package pss.core.winUI.responsiveControls;

import pss.core.services.fields.JObject;
import pss.core.winUI.forms.JBaseForm;

public class JFormColorPickerResponsive extends JFormControlResponsive  {


  //-------------------------------------------------------------------------//
  // Constructor
  //-------------------------------------------------------------------------//
	String oDato;
	
  public JFormColorPickerResponsive() {
  }
  
	public String getTipoDatoConvert() { // ver de unificar para no hacer estas negradas
		if (GetTipoDato().equals("CHAR")) return JObject.JSTRING;
		return this.GetTipoDato();
	}



//  @Override
//	public void BaseToControl(String zModo, boolean userRequest) throws Exception {
//    if (this.getProp() == null) return;
//    if (zModo.equals(JBaseForm.MODO_CONSULTA)/* || this.ifReadOnly() */)
//    	setValue( this.getProp().toFormattedString() );
//    else 
//      setValue( this.getProp().toInputString() );
//  }

  @Override
	public String getSpecificValue() throws Exception {
		updateFromProp();
    return oDato;
  }

  @Override
  public void clear() throws Exception {
		if (getProp()==null) return;
  	getProp().setNull();
  	updateFromProp();
  }
  //-------------------------------------------------------------------------//
  // Obtengo el valor del campo
  //-------------------------------------------------------------------------//
  @Override
	public boolean hasValue()  throws Exception {
		if (getProp()==null) return false;
		oDato = getSpecificValue();
    if ( oDato == null       ) return false;
    if ( oDato.trim().equals( "" )  ) return false;
    return true;
  }

  @Override
	public void setValue( String zVal ) throws Exception {
  	super.setValue(zVal);
  	updateFromProp();
  }
  
	private void updateFromProp() throws Exception {
		if (getProp().isNull()) {
			this.oDato = null;
			return;
		}
    if (getModo().equals(JBaseForm.MODO_CONSULTA)/* || this.ifReadOnly() */)
    	this.oDato =( this.getProp().toFormattedString() );
    else 
    	this.oDato =( this.getProp().toInputString() );

	}

}
