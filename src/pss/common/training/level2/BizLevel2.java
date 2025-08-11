package pss.common.training.level2;

import pss.core.services.fields.JColour;
import pss.core.services.fields.JGeoPosition;
import pss.core.services.fields.JLOB;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizLevel2 extends JRecord {

  private JLong pId = new JLong();
  private JString pDescripcion = new JString();
  private JLOB pTexto = new JLOB();
  private JString pFormLov = new JString();
  private JString pWinLov1 = new JString();
  private JString pWinLov2 = new JString();
  private JString pFile = new JString();
  private JGeoPosition pMapa = new JGeoPosition();
  private JColour pColor = new JColour();

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  public void setId(long zValue) throws Exception {    pId.setValue(zValue);  }
  public long getId() throws Exception {     return pId.getValue();  }
  public void setDescripcion(String zValue) throws Exception {    pDescripcion.setValue(zValue);  }
  public String getDescripcion() throws Exception {     return pDescripcion.getValue();  }


  /**
   * Constructor de la Clase
   */
  public BizLevel2() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "id", pId );
    this.addItem( "descripcion", pDescripcion );
    this.addItem( "texto", pTexto );
    this.addItem( "formlov", pFormLov );
    this.addItem( "winlov1", pWinLov1 );
    this.addItem( "winlov2", pWinLov2 );
    this.addItem( "file", pFile );
    this.addItem( "mapa", pMapa );
    this.addItem( "color", pColor );
  }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "id", "Id", false, false, 16 );
    this.addFixedItem( FIELD, "descripcion", "Usuario", true, true, 250 );
    this.addFixedItem( FIELD, "texto", "html", true, false, 250 );
    this.addFixedItem( FIELD, "formlov", "formlov", true, false, 50 );
    this.addFixedItem( FIELD, "winlov1", "winlov single", true, false, 50 );
    this.addFixedItem( FIELD, "winlov2", "winlov multiple", true, false, 500 );
    this.addFixedItem( FIELD, "file", "file", true, false, 1000 );
    this.addFixedItem( FIELD, "mapa", "mapa", true, false, 100 );
    this.addFixedItem( FIELD, "color", "color", true, false, 10 );
  }
  /**
   * Returns the table name
   */
  public String GetTable() { return "TRA_LEVEL2"; }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  /**
   * Default Read() method
   */
  public boolean Read( String zIdgrupo ) throws Exception { 
    addFilter( "id",  zIdgrupo ); 
    return read(); 
  } 
  
}
