package pss.common.terminals.config;

import pss.common.terminals.core.JTerminal;
import pss.core.services.fields.JInteger;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizTerminalDriver extends JRecord {

  private JString pCompany = new JString();
  private JString pNodo = new JString();
//  private JLong pTerminalPool = new JLong();
  private JLong pTerminalId = new JLong();
  private JInteger pDriverType = new JInteger();
  private JString pDescrDriverType = new JString() {@Override
	public void preset() throws Exception {pDescrDriverType.setValue(getDescrDriver());}};

  private BizTerminal terminal=null;
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  public void setCompany(String zValue) throws Exception {    pCompany.setValue(zValue);  }
  public String getCompany() throws Exception {     return pCompany.getValue();  }
  public void setNodo(String zValue) throws Exception {    pNodo.setValue(zValue);  }
  public String getNodo() throws Exception {     return pNodo.getValue();  }
//  public void setTerminalPool(long zValue) throws Exception {    pTerminalPool.setValue(zValue);  }
//  public long getTerminalPool() throws Exception {     return pTerminalPool.getValue();  }
  public void setTerminalId(long zValue) throws Exception {    pTerminalId.setValue(zValue);  }
  public long getTerminalId() throws Exception {     return pTerminalId.getValue();  }
  public void setDriverType(int zValue) throws Exception {    pDriverType.setValue(zValue);  }
  public int getDriverType() throws Exception {     return pDriverType.getValue();  }


  /**
   * Constructor de la Clase
   */
  public BizTerminalDriver() throws Exception {
  }
  @Override
	public void createProperties() throws Exception {
    this.addItem( "company", pCompany );
    this.addItem( "nodo", pNodo );
//    this.addItem( "terminal_pool", pTerminalPool );
    this.addItem( "terminal_id", pTerminalId );
    this.addItem( "driver_type", pDriverType );
    this.addItem( "descr_driver_type", pDescrDriverType );
  }
  /**
   * Adds the fixed object properties
   */
  @Override
	public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "company", "Company", true, true, 15 );
    this.addFixedItem( KEY, "nodo", "Nodo", true, true, 15 );
//    this.addFixedItem( KEY, "terminal_pool", "Terminal pool", true, true, 8 );
    this.addFixedItem( KEY, "terminal_id", "Terminal id", true, true, 5 );
    this.addFixedItem( KEY, "driver_type", "Driver type", true, true, 15 );
    this.addFixedItem( VIRTUAL, "descr_driver_type", "Driver type", true, true, 15 );
  }
  /**
   * Returns the table name
   */
  @Override
	public String GetTable() { return "TER_TERMINAL_DRIVER"; }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  /**
   * Default Read() method
   */
  public boolean Read( String zCompany, String zNodo, long zTerminalId, int driverType ) throws Exception { 
    addFilter( "company",  zCompany ); 
    addFilter( "nodo",  zNodo ); 
//    addFilter( "terminal_pool",  zTerminalPool ); 
    addFilter( "terminal_id",  zTerminalId ); 
    addFilter( "driver_type",  driverType );
    return Read(); 
  } 
  
  public String getDescrDriver() throws Exception {
  	return (String)JTerminal.getDriversTypes().getElement(String.valueOf(pDriverType.getValue()));
  }
  
  public BizTerminal getObjTerminal() throws Exception {
  	if (this.terminal!=null) return this.terminal;
  	BizTerminal record = new BizTerminal();
  	record.Read(pCompany.getValue(),  pTerminalId.getValue());
  	return (this.terminal=record);
  }
  
}
