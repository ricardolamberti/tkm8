package pss.common.terminals.config;

import pss.common.regions.company.BizCompany;
import pss.common.terminals.core.JTerminalPool;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.JTools;
import pss.core.tools.collections.JIterator;

public class BizTerminalPool extends JRecord {

  private JString pCompany = new JString();
  private JString pNodo = new JString();
  private JString pDescrNodo = new JString() {
  	public void preset() throws Exception {
  		pDescrNodo.setValue(getDescrNodo());
  	}
  };
  private JLong pTerminalPool = new JLong();
  private JString pDescription = new JString();
  private JString pMacAddress = new JString();
  
  private JRecords<BizTerminal> terminales=null;
  private JTerminalPool pool=null;

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  public void setCompany(String zValue) throws Exception {    pCompany.setValue(zValue);  }
  public String getCompany() throws Exception {     return pCompany.getValue();  }
  public void setNodo(String zValue) throws Exception {    pNodo.setValue(zValue);  }
  public String getNodo() throws Exception {     return pNodo.getValue();  }
  public void setTerminalPool(long zValue) throws Exception {    pTerminalPool.setValue(zValue);  }
  public long getTerminalPool() throws Exception { return pTerminalPool.getValue();  }
  public String getDescription() throws Exception { return pDescription.getValue();  }
  public void setMacAddress(boolean zValue) throws Exception {    pMacAddress.setValue(zValue);  }
  public String getMacAddress() throws Exception { return pMacAddress.getValue();  }


  /**
   * Constructor de la Clase
   */
  public BizTerminalPool() throws Exception {
  }
  @Override
	public void createProperties() throws Exception {
    this.addItem( "company", pCompany );
    this.addItem( "nodo", pNodo );
    this.addItem( "descr_nodo", pDescrNodo );
    this.addItem( "terminal_pool", pTerminalPool );
    this.addItem( "description", pDescription );
    this.addItem( "mac_address", pMacAddress );
  }
  /**
   * Adds the fixed object properties
   */
  @Override
	public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "company", "Company", true, true, 15 );
    this.addFixedItem( KEY, "nodo", "Nodo", true, true, 15 );
    this.addFixedItem( KEY, "terminal_pool", "Terminal pool", true, true, 8 );
    this.addFixedItem( VIRTUAL, "descr_nodo", "Sucursal", true, true, 100 );
    this.addFixedItem( FIELD, "description", "Descripción", true, true, 100 );
    this.addFixedItem( FIELD, "mac_address", "MAC Address", true, true, 100 );
  }
  /**
   * Returns the table name
   */
  @Override
	public String GetTable() { return "TER_TERMINAL_POOL"; }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  /**
   * Default Read() method
   */
  public boolean Read( String zCompany, String zNodo, long zTerminalPool ) throws Exception { 
    addFilter( "company",  zCompany ); 
    addFilter( "nodo",  zNodo ); 
    addFilter( "terminal_pool",  zTerminalPool ); 
    return this.read(); 
  } 

  public boolean readByMacAddress( String macAddress) throws Exception { 
    this.addFilter( "mac_address",  macAddress ); 
    return this.read(); 
  } 
  
//  public JTerminalPool createTerminalPool() throws Exception {
//  	if (this.isRemote())
//  		return JTerminalPoolServer.getTerminalPool(pCompany.getValue(), pNodo.getValue(), pTerminalPool.getValue());
//  	else
//  		return this.createTerminalPoolByConfig();
//  }
  
  public boolean isRemote() throws Exception {     
  	return !this.pMacAddress.getValue().equalsIgnoreCase(JTools.getMacAddress());  
  }

  
  public JTerminalPool createTerminalPoolByConfig() throws Exception {
  	if (this.pool!=null) return this.pool;
  	JTerminalPool pool = new JTerminalPool();
  	pool.setCompany(this.getCompany());
  	pool.setNodo(this.getNodo());
  	pool.setPoolId(this.getTerminalPool());
  	pool.setMacAddress(this.getMacAddress());
  	JIterator<BizTerminal> iter = this.getTerminals().getStaticIterator();
  	while (iter.hasMoreElements()) {
  		BizTerminal terminal = iter.nextElement();
  		pool.addTerminal(terminal.getTerminalPointer());
  	}
  	return (this.pool=pool);
  }
  
  public JRecords<BizTerminal> getTerminals() throws Exception {
  	if (this.terminales!=null) return this.terminales;
  	JRecords<BizTerminal> records = new JRecords<BizTerminal>(BizTerminal.class);
  	records.addFilter("company", this.pCompany.getValue());
  	records.addFilter("nodo", this.pNodo.getValue());
  	records.addFilter("terminal_pool", this.pTerminalPool.getValue());
  	records.readAll();
  	records.toStatic();
  	return (this.terminales=records);
  }
  
  @Override
	public void processInsert() throws Exception {
  	if (pTerminalPool.isNull()) {
  		BizTerminalPool max = new BizTerminalPool();
  		max.addFilter("company", pCompany.getValue());
  		max.addFilter("nodo", pNodo.getValue());
  		pTerminalPool.setValue(max.SelectMaxLong("terminal_pool")+1);
  	}
  	super.processInsert();
  }
  
  @Override
	public void processDelete() throws Exception {
  	this.getTerminals().processDeleteAll();
  	super.processDelete();
  }
  
  public String getDescrNodo() throws Exception {
  	return BizCompany.getCompany(this.pCompany.getValue()).findNodo(this.getNodo()).GetDescrip();
  }
  
  
}
