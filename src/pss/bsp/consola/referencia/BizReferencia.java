package  pss.bsp.consola.referencia;

import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizReferencia extends JRecord {

  private JLong pId = new JLong();
  private JLong pIcono = new JLong();
  private JString pDescripcion = new JString();


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  

  public void setId(long zValue) throws Exception {    pId.setValue(zValue);  }
  public long getId() throws Exception {     return pId.getValue();  }
  public boolean isNullId() throws Exception { return  pId.isNull(); } 
  public void setNullToId() throws Exception {  pId.setNull(); } 
  public void setIcono(long zValue) throws Exception {    pIcono.setValue(zValue);  }
  public long getIcono() throws Exception {     return pIcono.getValue();  }
  public boolean isNullIcono() throws Exception { return  pIcono.isNull(); } 
  public void setNullToIcono() throws Exception {  pIcono.setNull(); } 
  public void setDescripcion(String zValue) throws Exception {    pDescripcion.setValue(zValue);  }
  public String getDescripcion() throws Exception {     return pDescripcion.getValue();  }
  public boolean isNullDescripcion() throws Exception { return  pDescripcion.isNull(); } 
  public void setNullToDescripcion() throws Exception {  pDescripcion.setNull(); } 


  /**
   * Constructor de la Clase
   */
  public BizReferencia() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "id", pId );
    this.addItem( "icono", pIcono );
    this.addItem( "descripcion", pDescripcion );
  }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "id", "Id", true, true, 18 );
    this.addFixedItem( FIELD, "icono", "Icono", true, true, 18 );
    this.addFixedItem( FIELD, "descripcion", "Descripcion", true, true, 250 );
  }
  /**
   * Returns the table name
   */
  public String GetTable() { return null; }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


}
