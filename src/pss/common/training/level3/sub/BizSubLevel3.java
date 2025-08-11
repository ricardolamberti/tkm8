package pss.common.training.level3.sub;

import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizSubLevel3 extends JRecord {

  private JLong pId = new JLong();
  private JLong pId2 = new JLong();
  private JString pDescripcion = new JString();

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
  public BizSubLevel3() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "id", pId );
    this.addItem( "subid", pId2 );
    this.addItem( "descripcion", pDescripcion );
  }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "id", "Id", true, true, 16 );
    this.addFixedItem( KEY, "subid", "sId", false, false, 64 );
    this.addFixedItem( FIELD, "descripcion", "Detalle", true, true, 250 );
  }
  /**
   * Returns the table name
   */
  public String GetTable() { return "TRA_SUBLEVEL3"; }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  /**
   * Default Read() method
   */
  public boolean Read( long zIdgrupo , long zIdgrupo2) throws Exception { 
    addFilter( "id",  zIdgrupo ); 
    addFilter( "subid",  zIdgrupo2 ); 
    return read(); 
  } 
  

}
