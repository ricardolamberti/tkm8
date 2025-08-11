package pss.common.terminals.config.auxPrinter;

import java.util.Date;

import pss.core.services.fields.JBoolean;
import pss.core.services.fields.JDate;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizPrinter extends JRecord {

  private JString pComany = new JString();
  private JString pNodo = new JString();
//  private JLong pTerminalPool = new JLong();
  private JLong pTerminalId = new JLong();
//  private JString pTieneRollo = new JString();
  private JString pNroRollo = new JString();
  private JBoolean pCierreInformeparticular = new JBoolean();
  private JDate pFechaIniciofiscal = new JDate();
  private JString pHoraIniciofiscal = new JString();


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  public void setComany(String zValue) throws Exception {    pComany.setValue(zValue);  }
  public String getComany() throws Exception {     return pComany.getValue();  }
  public void setNodo(String zValue) throws Exception {    pNodo.setValue(zValue);  }
  public String getNodo() throws Exception {     return pNodo.getValue();  }
//  public void setTerminalPool(long zValue) throws Exception {    pTerminalPool.setValue(zValue);  }
//  public long getTerminalPool() throws Exception {     return pTerminalPool.getValue();  }
  public void setTerminalId(long zValue) throws Exception {    pTerminalId.setValue(zValue);  }
  public long getTerminalId() throws Exception {     return pTerminalId.getValue();  }
//  public void setTieneRollo(String zValue) throws Exception {    pTieneRollo.setValue(zValue);  }
//  public String getTieneRollo() throws Exception {     return pTieneRollo.getValue();  }
  public void setNroRollo(String zValue) throws Exception {    pNroRollo.setValue(zValue);  }
  public String getNroRollo() throws Exception {     return pNroRollo.getValue();  }
  public void setCierreInformeparticular(boolean zValue) throws Exception {    pCierreInformeparticular.setValue(zValue);  }
  public boolean isCierreInformeparticular() throws Exception { return pCierreInformeparticular.getValue();  }
  public void setFechaIniciofiscal(Date zValue) throws Exception {    pFechaIniciofiscal.setValue(zValue);  }
  public Date getFechaIniciofiscal() throws Exception {     return pFechaIniciofiscal.getValue();  }
  public void setHoraIniciofiscal(String zValue) throws Exception {    pHoraIniciofiscal.setValue(zValue);  }
  public String getHoraIniciofiscal() throws Exception {     return pHoraIniciofiscal.getValue();  }


  /**
   * Constructor de la Clase
   */
  public BizPrinter() throws Exception {
  }
  @Override
	public void createProperties() throws Exception {
    this.addItem( "comany", pComany );
    this.addItem( "nodo", pNodo );
//    this.addItem( "terminal_pool", pTerminalPool );
    this.addItem( "terminal_id", pTerminalId );
//    this.addItem( "tiene_rollo", pTieneRollo );
    this.addItem( "nro_rollo", pNroRollo );
    this.addItem( "cierre_informe_particular", pCierreInformeparticular );
    this.addItem( "fecha_inicio_fiscal", pFechaIniciofiscal );
    this.addItem( "hora_inicio_fiscal", pHoraIniciofiscal );
  }
  /**
   * Adds the fixed object properties
   */
  @Override
	public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "comany", "Comany", true, true, 15 );
//    this.addFixedItem( KEY, "terminal_pool", "Terminal pool", true, true, 8 );
    this.addFixedItem( KEY, "terminal_id", "Terminal id", true, true, 5 );
    this.addFixedItem( FIELD, "nodo", "Nodo", true, true, 15 );
//    this.addFixedItem( FIELD, "tiene_rollo", "Tiene rollo", true, true, 1 );
    this.addFixedItem( FIELD, "nro_rollo", "Nro rollo", true, true, 20 );
    this.addFixedItem( FIELD, "cierre_informe_particular", "Cierre informe particular", true, true, 1 );
    this.addFixedItem( FIELD, "fecha_inicio_fiscal", "Fecha inicio fiscal", true, false, 10 );
    this.addFixedItem( FIELD, "hora_inicio_fiscal", "Hora inicio fiscal", true, false, 6 );
  }
  /**
   * Returns the table name
   */
  @Override
	public String GetTable() { return "TER_PRINTER"; }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  /**
   * Default Read() method
   */
  public boolean Read( String zComany,  long zTerminalId ) throws Exception { 
    addFilter( "comany",  zComany ); 
//    addFilter( "nodo",  zNodo ); 
//    addFilter( "terminal_pool",  zTerminalPool ); 
    addFilter( "terminal_id",  zTerminalId ); 
    return Read(); 
  }
  public boolean Read( String zComany, String zNodo, long zTerminalId ) throws Exception { 
    addFilter( "comany",  zComany ); 
//    addFilter( "nodo",  zNodo ); 
//    addFilter( "terminal_pool",  zTerminalPool ); 
    addFilter( "terminal_id",  zTerminalId ); 
    return Read(); 
  }
  public boolean hasNroRollo() throws Exception {     
  	return pNroRollo.isNotNull();  
  }
  
}
