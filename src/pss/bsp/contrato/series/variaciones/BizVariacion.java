package pss.bsp.contrato.series.variaciones;

import java.util.Calendar;
import java.util.Date;

import pss.common.security.BizUsuario;
import pss.core.services.fields.JDate;
import pss.core.services.fields.JFloat;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizVariacion extends JRecord {

  private JString pCompany = new JString();
  private JString pDescripcion = new JString();
  private JFloat pVariacion = new JFloat();
  private JDate pFechaHasta = new JDate();
  private JDate pFechaDesde = new JDate();
  private JLong pId = new JLong();
  private JLong pLinea = new JLong();


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  public void setDescripcion(String zValue) throws Exception {    pDescripcion.setValue(zValue);  }
  public String getDescripcion() throws Exception {     return pDescripcion.getValue();  }
  public boolean isNullDescripcion() throws Exception { return  pDescripcion.isNull(); } 
  public void setNullToDescripcion() throws Exception {  pDescripcion.setNull(); } 
  public void setVariacion(double zValue) throws Exception {    pVariacion.setValue(zValue);  }
  public double getVariacion() throws Exception {     return pVariacion.getValue();  }
  public boolean isNullVariacion() throws Exception { return  pVariacion.isNull(); } 
  public void setNullToVariacion() throws Exception {  pVariacion.setNull(); } 
  public void setFechaHasta(Date zValue) throws Exception {    pFechaHasta.setValue(zValue);  }
  public Date getFechaHasta() throws Exception {     return pFechaHasta.getValue();  }
  public boolean isNullFechaHasta() throws Exception { return  pFechaHasta.isNull(); } 
  public void setNullToFechaHasta() throws Exception {  pFechaHasta.setNull(); } 
  public void setFechaDesde(Date zValue) throws Exception {    pFechaDesde.setValue(zValue);  }
  public Date getFechaDesde() throws Exception {     return pFechaDesde.getValue();  }
  public boolean isNullFechaDesde() throws Exception { return  pFechaDesde.isNull(); } 
  public void setNullToFechaDesde() throws Exception {  pFechaDesde.setNull(); } 
  public void setId(long zValue) throws Exception {    pId.setValue(zValue);  }
  public long getId() throws Exception {     return pId.getValue();  }
  public boolean isNullId() throws Exception { return  pId.isNull(); } 
  public void setNullToId() throws Exception {  pId.setNull(); } 
  public void setLinea(long zValue) throws Exception {    pLinea.setValue(zValue);  }
  public long getLinea() throws Exception {     return pLinea.getValue();  }


  /**
   * Constructor de la Clase
   */
  public BizVariacion() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "descripcion", pDescripcion );
    this.addItem( "variacion", pVariacion );
    this.addItem( "fecha_hasta", pFechaHasta );
    this.addItem( "fecha_desde", pFechaDesde );
    this.addItem( "id", pId );
    this.addItem( "linea", pLinea );
    this.addItem( "company", pCompany );
  }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( FIELD, "descripcion", "Descripcion", true, true, 250 );
    this.addFixedItem( FIELD, "variacion", "Variacion", true, true, 18,2 );
    this.addFixedItem( FIELD, "fecha_hasta", "Fecha hasta", true, true, 10 );
    this.addFixedItem( FIELD, "fecha_desde", "Fecha desde", true, true, 10 );
    this.addFixedItem( FIELD, "company", "company", true, false, 20 );
    this.addFixedItem( KEY, "id", "Id", true, true, 32 );
    this.addFixedItem( KEY, "linea", "Linea", false, false, 32 );
  }
  /**
   * Returns the table name
   */
  public String GetTable() { return "bsp_series_variaciones"; }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  /**
   * Default read() method
   */
  public boolean read( long zId ) throws Exception { 
    addFilter( "id",  zId ); 
    return read(); 
  } 
  public boolean read( Calendar fecha) throws Exception { 
    addFilter( "fecha_desde",  fecha.getTime(),">=" ); 
    addFilter( "fecha_hasta",  fecha.getTime(),"<=" ); 
    
    return read(); 
  } 
  
  @Override
  public void processInsert() throws Exception {
  	if (!BizUsuario.IsAdminUser()) {
  		pCompany.setValue(BizUsuario.getUsr().getCompany());
  	}
  	setId(1);//todavia no se de donde se va a enganchar
  	super.processInsert();
  }
}
