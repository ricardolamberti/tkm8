package  pss.common.security;

import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizPermiso extends JRecord {


  //-------------------------------------------------------------------------//
  // Propiedades de la Clase
  //-------------------------------------------------------------------------//
  JString pCod_permiso = new JString();
  JString pTipo_permiso = new JString();




  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public BizPermiso() throws Exception {
  }

  @Override
	public void createProperties() throws Exception {
    addItem( "COD_PERMISO", pCod_permiso );
    addItem( "TIPO_PERMISO", pTipo_permiso );
  }

  @Override
	public void createFixedProperties() throws Exception {
    addFixedItem( KEY, "COD_PERMISO", "Cod_permiso", true, true, 1 );
    addFixedItem( FIELD, "TIPO_PERMISO", "Tipo_permiso", true, true, 50 );
  }


  @Override
	public String GetTable() {return ""; }
}
