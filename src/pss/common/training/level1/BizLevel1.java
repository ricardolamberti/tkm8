package pss.common.training.level1;

import pss.core.services.fields.JDate;
import pss.core.services.fields.JDateTime;
import pss.core.services.fields.JFloat;
import pss.core.services.fields.JHour;
import pss.core.services.fields.JLOB;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizLevel1 extends JRecord {

  private JLong pId = new JLong();
  private JString pDescripcion = new JString();
  private JDate pFecha = new JDate();
  private JDateTime pFechaHora = new JDateTime();
  private JHour pHora = new JHour();
  private JString pCombo = new JString();
  private JString pMulti = new JString();
  private JLOB pTexto = new JLOB();
  private JFloat pFloat = new JFloat();
  private JLong pLong = new JLong();

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
  public BizLevel1() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "id", pId );
    this.addItem( "descripcion", pDescripcion );
    this.addItem( "fecha", pFecha );
    this.addItem( "fechahora", pFechaHora );
    this.addItem( "hora", pHora );
    this.addItem( "combo", pCombo );
    this.addItem( "multi", pMulti );
    this.addItem( "texto", pTexto );
    this.addItem( "decimal", pFloat );
    this.addItem( "numero", pLong );
  }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "id", "Id", false, false, 16 );
    this.addFixedItem( FIELD, "descripcion", "Usuario", true, true, 250 );
    this.addFixedItem( FIELD, "fecha", "Fecha", true, false, 16 );
    this.addFixedItem( FIELD, "fechahora", "Fecha y hora", true, false, 16 );
    this.addFixedItem( FIELD, "hora", "Hora", true, false, 12 );
    this.addFixedItem( FIELD, "combo", "Combo", true, false, 10 );
    this.addFixedItem( FIELD, "multi", "Multi", true, false, 10 );
    this.addFixedItem( FIELD, "texto", "Texto", true, false, 250 );
    this.addFixedItem( FIELD, "decimal", "Decimal", true, false, 10,2 );
    this.addFixedItem( FIELD, "numero", "Numero", true, false, 10 );
  }
  /**
   * Returns the table name
   */
  public String GetTable() { return "TRA_LEVEL1"; }


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
