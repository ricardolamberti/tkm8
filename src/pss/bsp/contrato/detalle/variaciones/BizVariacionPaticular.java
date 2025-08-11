package pss.bsp.contrato.detalle.variaciones;

import java.util.Calendar;
import java.util.Date;
import pss.core.services.records.JRecord;
import pss.core.services.fields.*;

public class BizVariacionPaticular extends JRecord {

  private JString pDescripcion = new JString();
  private JFloat pVariacion = new JFloat();
  private JDate pFechaHasta = new JDate();
  private JDate pFechaDesde = new JDate();
  private JLong pId = new JLong();
  private JLong pLineaContrato = new JLong();
  private JString pCompany = new JString();
  private JLong pIdContrato = new JLong();
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
  public void setIdContrato(long zValue) throws Exception {    pIdContrato.setValue(zValue);  }
  public long getIdContrato() throws Exception {     return pIdContrato.getValue();  }
  public boolean isNullIdContrato() throws Exception { return  pIdContrato.isNull(); } 
  public void setNullToIdContrato() throws Exception {  pIdContrato.setNull(); } 
  public void setLineaContrato(long zValue) throws Exception {    pLineaContrato.setValue(zValue);  }
  public long getLineaContrato() throws Exception {     return pLineaContrato.getValue();  }
  public boolean isNullLineaContrato() throws Exception { return  pLineaContrato.isNull(); } 
  public void setNullToLineaContrato() throws Exception {  pLineaContrato.setNull(); } 
  public void setCompany(String zValue) throws Exception {    pCompany.setValue(zValue);  }
  public String getCompany() throws Exception {     return pCompany.getValue();  }
  public boolean isNullCompany() throws Exception { return  pCompany.isNull(); } 
  public void setNullToCompany() throws Exception {  pCompany.setNull(); } 


  /**
   * Constructor de la Clase
   */
  public BizVariacionPaticular() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "company", pCompany );
    this.addItem( "id_contrato", pIdContrato );
    this.addItem( "linea_contrato", pLineaContrato );
    this.addItem( "id", pId );
    this.addItem( "linea", pLinea );
    this.addItem( "descripcion", pDescripcion );
    this.addItem( "variacion", pVariacion );
    this.addItem( "fecha_hasta", pFechaHasta );
    this.addItem( "fecha_desde", pFechaDesde );
  }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( FIELD, "company", "Company", true, true, 15 );
    this.addFixedItem( KEY, "id_contrato", "Id", true, true, 64 );
    this.addFixedItem( KEY, "linea_contrato", "Linea", true, true, 32 );
    this.addFixedItem( KEY, "id", "id", false, false, 32 );
    this.addFixedItem( FIELD, "linea", "Linea", true, false, 32 );
    this.addFixedItem( FIELD, "descripcion", "Descripcion", true, true, 250 );
    this.addFixedItem( FIELD, "variacion", "Variacion", true, true, 18,2 );
    this.addFixedItem( FIELD, "fecha_hasta", "Fecha hasta", true, true, 10 );
    this.addFixedItem( FIELD, "fecha_desde", "Fecha desde", true, true, 10 );
  }
  /**
   * Returns the table name
   */
  public String GetTable() { return "bsp_series_variaciones_particular"; }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  /**
   * Default read() method
   */
  public boolean read( long id_contrato, long linea_contrato, long linea ) throws Exception { 
    addFilter( "id_contrato",  id_contrato ); 
    addFilter( "linea_contrato",  linea_contrato ); 
    addFilter( "linea",  linea ); 
    return read(); 
  } 
  public boolean read( long id ) throws Exception { 
    addFilter( "id",  id ); 
    return read(); 
  } 
  public boolean read( Calendar fecha) throws Exception { 
    addFilter( "fecha_desde",  fecha.getTime(),">=" ); 
    addFilter( "fecha_hasta",  fecha.getTime(),"<=" ); 
    return read(); 
  } 
  
}
