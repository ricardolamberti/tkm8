package  pss.common.components;

import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizDirectorio extends JRecord {

  //-------------------------------------------------------------------------//
  // Propiedades de la Clase
  //-------------------------------------------------------------------------//
  JString pDirectorio   = new JString();

  public void setDirectorio(String zValue) { pDirectorio.setValue(zValue);}
	public JString getDirectorio() {return pDirectorio;}
	
  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public BizDirectorio() throws Exception {
  }

  @Override
	public void createProperties() throws Exception {
    addItem( "directorio", pDirectorio);
    addItem( "descripcion"  , pDirectorio);
  }

  @Override
	public void createFixedProperties() throws Exception {
    addFixedItem( KEY  , "directorio"  , "Directorio", true, true, 100 );
    addFixedItem( VIRTUAL  , "descripcion"  , "Directorio", true, true, 100 );
  }
  
  @Override
	public String GetTable() {return "";}
  
}
