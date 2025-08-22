package pss.bsp.contrato.rutas.ms;

import pss.core.services.fields.JFloat;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizMS extends JRecord {
	public static final String SOLO_CUMPLE = "1";
	public static final String SOLO_NO_CUMPLE = "2";
	public static final String FALTA_CANTIDAD = "3";
	public static final String LOS_POSIBLES = "4";
	
  public JString pCompany = new JString();
  public JLong pLinea = new JLong();
  public JString pAerolinea = new JString();
  public JString pRuta = new JString();
  public JLong pCantidad = new JLong();
  public JFloat pPorcentaje = new JFloat();
  public JLong pFactor = new JLong();
  


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  /**
   * Constructor de la Clase
   */
  public BizMS() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "company", pCompany );
    this.addItem( "linea", pLinea );
    this.addItem( "ruta", pRuta );
    this.addItem( "aerolinea", pAerolinea);
    this.addItem( "cantidad", pCantidad );
    this.addItem( "porcentaje", pPorcentaje );
    this.addItem( "factor", pFactor );
   }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "company", "Company", true, true, 15 );
    this.addFixedItem( KEY, "linea", "Linea", false, false, 32 );
    this.addFixedItem( FIELD, "ruta", "Ruta", true, true, 250 );
    this.addFixedItem( FIELD, "aerolinea", "Aerolinea", true, true, 250 );
    this.addFixedItem( FIELD, "cantidad", "Cantidad tkt", true, true, 18 );
    this.addFixedItem( FIELD, "porcentaje", "Porcentaje", true, true, 18,2 );
    this.addFixedItem( FIELD, "factor", "factor", true, true, 18 );
  }
  /**
   * Returns the table name
   */
  public String GetTable() { return null; }



//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


}
