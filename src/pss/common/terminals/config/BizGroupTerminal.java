package pss.common.terminals.config;

import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizGroupTerminal extends JRecord {

  private JString pCompany = new JString();
  private JString pNodo = new JString();
  private JLong pTerminalGroup = new JLong();
//  private JLong pTerminalPool = new JLong();
  private JLong pTerminalId = new JLong();
//  private JString pDescrTerminalPool = new JString() {public void preset() throws Exception {pDescrTerminalPool.setValue(getDescrTerminalPool());}};
  private JString pDescrTerminal = new JString() {@Override
	public void preset() throws Exception {pDescrTerminal.setValue(getDescrTerminal());}};
  
//  private BizTerminalPool terminalPool=null;
  private BizTerminal terminal=null;

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  public void setCompany(String zValue) throws Exception {    pCompany.setValue(zValue);  }
  public String getCompany() throws Exception {     return pCompany.getValue();  }
  public void setNodo(String zValue) throws Exception {    pNodo.setValue(zValue);  }
  public String getNodo() throws Exception {     return pNodo.getValue();  }
  public void setTerminalGroup(long zValue) throws Exception {    pTerminalGroup.setValue(zValue);  }
  public long getTerminalGroup() throws Exception {     return pTerminalGroup.getValue();  }
//  public void setTerminalPool(long zValue) throws Exception {    pTerminalPool.setValue(zValue);  }
//  public long getTerminalPool() throws Exception {     return pTerminalPool.getValue();  }
  public void setTerminalId(long zValue) throws Exception {    pTerminalId.setValue(zValue);  }
  public long getTerminalId() throws Exception {     return pTerminalId.getValue();  }


  /**
   * Constructor de la Clase
   */
  public BizGroupTerminal() throws Exception {
  }


  @Override
	public void createProperties() throws Exception {
    this.addItem( "company", pCompany );
    this.addItem( "nodo", pNodo );
    this.addItem( "terminal_group", pTerminalGroup );
//    this.addItem( "terminal_pool", pTerminalPool );
    this.addItem( "terminal_id", pTerminalId );
//    this.addItem( "descr_terminal_pool", pDescrTerminalPool );
    this.addItem( "descr_terminal", pDescrTerminal );
  }
  /**
   * Adds the fixed object properties
   */
  @Override
	public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "company", "Company", true, true, 15 );
    this.addFixedItem( KEY, "nodo", "Nodo", true, true, 15 );
    this.addFixedItem( KEY, "terminal_group", "Terminal group", true, true, 8 );
//    this.addFixedItem( KEY, "terminal_pool", "Terminal pool", true, true, 8 );
    this.addFixedItem( KEY, "terminal_id", "Terminal id", true, true, 5 );
//    this.addFixedItem( VIRTUAL, "descr_terminal_pool", "Terminal Pool", true, true, 100 );
    this.addFixedItem( VIRTUAL, "descr_terminal", "Terminal", true, true, 100 );
  }
  /**
   * Returns the table name
   */
  @Override
	public String GetTable() { return "TER_GROUP_TERMINAL"; }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  /**
   * Default Read() method
   */
  public boolean Read( String zCompany, String zNodo, long zTerminalGroup, long zTerminalPool, long zTerminalId ) throws Exception { 
    addFilter( "company",  zCompany ); 
    addFilter( "nodo",  zNodo ); 
    addFilter( "terminal_group",  zTerminalGroup ); 
    addFilter( "terminal_pool",  zTerminalPool ); 
    addFilter( "terminal_id",  zTerminalId ); 
    return Read(); 
  }
  
//  public BizTerminalPool getObjTerminalPool() throws Exception {
//  	if (this.terminalPool!=null) return this.terminalPool;
//  	BizTerminalPool record = new BizTerminalPool();
//  	record.Read(pCompany.getValue(), pNodo.getValue(), pTerminalPool.getValue());
//  	return (this.terminalPool=record);
//  }
  
  public BizTerminal getObjTerminal() throws Exception {
  	if (this.terminal!=null) return this.terminal;
  	BizTerminal record = new BizTerminal();
  	record.Read(pCompany.getValue(), pTerminalId.getValue());
  	return (this.terminal=record);
  }
  
//  public String getDescrTerminalPool() throws Exception {
//  	return this.getObjTerminalPool().getDescription();
//  }

  public String getDescrTerminal() throws Exception {
  	return this.getObjTerminal().getDescrTerminal();
  }
  
}
