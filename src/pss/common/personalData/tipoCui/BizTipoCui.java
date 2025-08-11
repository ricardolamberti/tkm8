package pss.common.personalData.tipoCui;

import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizTipoCui extends JRecord {


  //-------------------------------------------------------------------------//
  // Propiedades de la Clase
  //-------------------------------------------------------------------------//
  JString pPais = new JString();
  JString pTipoPersona = new JString();
  JString pTipoCui = new JString();
  JString pDescripcion = new JString();

  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public String getTipoCui() throws Exception { return pTipoCui.getValue();}
  public String getDescripcion() throws Exception { return pDescripcion.getValue();}
  public void setTipoCui(String value) throws Exception { pTipoCui.setValue(value);}
  public void setDescripcion(String value) throws Exception { pDescripcion.setValue(value);}
  public void setPais(String value) throws Exception { pPais.setValue(value);}
  public void setTipoPersona(String value) throws Exception { pTipoPersona.setValue(value);}
  
  public BizTipoCui() throws Exception {
  }

  @Override
	public void createProperties() throws Exception {
    addItem( "pais", pPais);
    addItem( "tipo_cui", pTipoCui);
    addItem( "tipo_persona", pTipoPersona);
    addItem( "descripcion", pDescripcion);
  }

  @Override
	public void createFixedProperties() throws Exception {
    addFixedItem( KEY, "pais", "País", true, true, 15 );
    addFixedItem( KEY, "tipo_cui", "Tipo Cui", true, true, 15 );
    addFixedItem( FIELD, "tipo_persona", "Tipo Persona", true, true, 1 );
    addFixedItem( FIELD, "descripcion", "Descripcion", true, true, 20 );
  }

  public String getPais() throws Exception { return pPais.getValue(); }
  @Override
	public String GetTable() { return "PER_TIPO_CUI";}


  public boolean Read( String zCountry, String zTipoDoc ) throws Exception {
  	addFilter("pais", zCountry);
    addFilter("tipo_cui", zTipoDoc);
    return this.read();
  }

}
