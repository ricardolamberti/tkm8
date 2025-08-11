package pss.core.winUI.responsiveControls;

import pss.core.services.fields.JObject;

public class JFormLabelDataResponsive extends JFormControlResponsive  {

  private boolean outstanding = false; //destacado

  String oDato;
	private boolean estatico;
	private int iTextHAlignment = ALIGN_DEFAULT;
	private int iTextVAlignment = ALIGN_DEFAULT;

  public int getTextHAlignment() {
		return iTextHAlignment;
	}

	public JFormLabelDataResponsive setTextHAlignment(int iTextHAlignment) {
		this.iTextHAlignment = iTextHAlignment;
		return this;
	}

	public int getTextVAlignment() {
		return iTextVAlignment;
	}

	public JFormLabelDataResponsive setTextVAlignment(int iTextVAlignment) {
		this.iTextVAlignment = iTextVAlignment;
		return this;
	}

	public boolean isEstatico() {
		return estatico;
	}

	public void setEstatico(boolean estatico) {
		this.estatico = estatico;
	}
  //-------------------------------------------------------------------------//
  // Constructor
  //-------------------------------------------------------------------------//
  public JFormLabelDataResponsive() {
  }
  
	public String getTipoDatoConvert() { // ver de unificar para no hacer estas negradas
		if (GetTipoDato().equals("CHAR")) return JObject.JSTRING;
		return this.GetTipoDato();
	}





//  @Override
//	public void BaseToControl(String zModo, boolean userRequest) throws Exception {
//    if (this.getProp() == null) return;
//   	setValue( this.getProp().toFormattedString() );
//  }


  //-------------------------------------------------------------------------//
  // Obtengo el valor del campo
  //-------------------------------------------------------------------------//
  @Override
	public String getSpecificValue() throws Exception {
  	if (isEstatico()) return getFieldProp();
    return  getProp().toFormattedString();
  }

  @Override
  public void clear() throws Exception {
  	if (isEstatico()) {
  		oDato=getFieldProp();
  	} else {
  		getProp().setNull();
  	}
  }
  //-------------------------------------------------------------------------//
  // Obtengo el valor del campo
  //-------------------------------------------------------------------------//
  @Override
	public boolean hasValue() throws Exception {
  	if (isEstatico()) return false;
  	String oDato = getProp().toFormattedString();
    if ( oDato == null       ) return false;
    if ( oDato.trim().equals( "" )  ) return false;
    return true;
  }

  @Override
	public void setValue( String zVal ) throws Exception {
  	if (isEstatico()) {
  		oDato=getFieldProp();
  		return;
  	}
  	super.setValue(zVal);
  	oDato= zVal;
  }


  public boolean isOutstanding() {
		return outstanding;
	}
	public JFormLabelDataResponsive setOutstanding(boolean outstanding) {
		this.outstanding = outstanding;
		return this;
	}
}
