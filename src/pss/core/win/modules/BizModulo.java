package pss.core.win.modules;

import pss.core.services.fields.JInteger;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizModulo extends JRecord {
  //-------------------------------------------------------------------------//
  // Propiedades de la Clase
  //-------------------------------------------------------------------------//
  public JInteger pModulo = new JInteger();
  public JString  pDescrip  = new JString ();
  public JInteger pOrden    = new JInteger();

  //-------------------------------------------------------------------------//
  // Constructor de la Clase
  //-------------------------------------------------------------------------//
  public BizModulo() throws Exception {
    addItem( "modulo" , pModulo  ) ;
    addItem( "descripcion" , pDescrip ) ;
    addItem( "orden" , pOrden   ) ;
  }

  @Override
	public void createFixedProperties() throws Exception {
    addFixedItem( KEY, "modulo"     , "Modulo", true, true, 100 ) ;
    addFixedItem( FIELD, "descripcion", "Descripción", true, true, 100 ) ;
    addFixedItem( FIELD, "orden"      , "Orden", true, true, 5 ) ;
  }

  @Override
	public String GetTable() {return "";}


}


