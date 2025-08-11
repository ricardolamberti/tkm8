package pss.common.terminals.config;

import pss.common.regions.company.BizCompany;
import pss.common.terminals.core.JTerminalGroup;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.collections.JIterator;

public class BizTerminalGroup extends JRecord {

  private JString pCompany = new JString();
  private JString pNodo = new JString();
  private JString pDescrNodo = new JString() {
  	public void preset() throws Exception {
  		pDescrNodo.setValue(getDescrNodo());
  	}
  };
  private JLong pTerminalGroup = new JLong();
  private JString pDescription = new JString();
  
  private JTerminalGroup terminalGroup=null;
  private JRecords<BizGroupTerminal> terminals=null;

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  public void setCompany(String zValue) throws Exception {    pCompany.setValue(zValue);  }
  public String getCompany() throws Exception {     return pCompany.getValue();  }
  public void setNodo(String zValue) throws Exception {    pNodo.setValue(zValue);  }
  public String getNodo() throws Exception {     return pNodo.getValue();  }
  public void setTerminalGroup(long zValue) throws Exception {    pTerminalGroup.setValue(zValue);  }
  public long getTerminalGroup() throws Exception { return pTerminalGroup.getValue();  }
  public String getDescription() throws Exception { return pDescription.getValue();  }


  /**
   * Constructor de la Clase
   */
  public BizTerminalGroup() throws Exception {
  }
  @Override
	public void createProperties() throws Exception {
    this.addItem( "company", pCompany );
    this.addItem( "nodo", pNodo );
    this.addItem( "descr_nodo", pDescrNodo );
    this.addItem( "terminal_group", pTerminalGroup );
    this.addItem( "description", pDescription );
  }
  /**
   * Adds the fixed object properties
   */
  @Override
	public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "company", "Company", true, true, 15 );
    this.addFixedItem( KEY, "terminal_group", "Grupo de Terminales", true, true, 8 );
    this.addFixedItem( FIELD, "nodo", "Nodo", true, true, 15 );
    this.addFixedItem( VIRTUAL, "descr_nodo", "Sucursal", true, true, 100 );
    this.addFixedItem( FIELD, "description", "Descripción", true, true, 100 );
  }
  /**
   * Returns the table name
   */
  @Override
	public String GetTable() { return "TER_TERMINAL_GROUP"; }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  /**
   * Default Read() method
   */
  public boolean Read( String zCompany,  long zTerminalGroup ) throws Exception { 
    addFilter( "company",  zCompany ); 
   // addFilter( "nodo",  zNodo ); 
    addFilter( "terminal_group",  zTerminalGroup ); 
    return Read(); 
  } 
  public boolean Read( String zCompany, String zNodo, long zTerminalGroup ) throws Exception { 
    addFilter( "company",  zCompany ); 
   // addFilter( "nodo",  zNodo ); 
    addFilter( "terminal_group",  zTerminalGroup ); 
    return Read(); 
  } 
  
  public JTerminalGroup createTerminalGroup() throws Exception {
  	if (this.terminalGroup!=null) return this.terminalGroup;
  	JTerminalGroup group = new JTerminalGroup();
  	group.setCompany(this.getCompany());
  	group.setNodo(this.getNodo());
  	group.setGroupId(this.getTerminalGroup());
  	JIterator<BizGroupTerminal> iter = this.getTerminals().getStaticIterator();
  	while (iter.hasMoreElements()) {
  		BizGroupTerminal terminal = iter.nextElement();
  		group.addTerminal(terminal.getObjTerminal().getTerminalPointer());
  	}
  	return (this.terminalGroup=group);
  }

  
  public JRecords<BizGroupTerminal> getTerminals() throws Exception {
  	if (this.terminals!=null) return this.terminals;
  	JRecords<BizGroupTerminal> records = new JRecords<BizGroupTerminal>(BizGroupTerminal.class);
  	records.addFilter("company", this.pCompany.getValue());
  	records.addFilter("nodo", this.pNodo.getValue());
  	records.addFilter("terminal_group", this.pTerminalGroup.getValue());
  	records.readAll();
  	records.toStatic();
  	return (this.terminals=records);
  }
  
//  public JRecords getTerminalPoolsWithDriver(int driver) throws Exception {
//  	JMap map=null;
//  	JRecords records = new JRecords(BizTerminalPool.class);
//  	records.setStatic(true);
//  	JIterator iter = this.getTerminals().getStaticIterator();
//  	while (iter.hasMoreElements()) {
//  		BizGroupTerminal terminal = (BizGroupTerminal)iter.nextElement();
//  		if (!terminal.getObjTerminal().hasDriverConfig(driver)) continue;
//  		if (map==null) map = JCollectionFactory.createMap();
//  		if (map.containsKey(String.valueOf(terminal.getTerminalPool()))) continue;
//  		records.addItem(terminal.getObjTerminalPool());
//  		map.addElement(String.valueOf(terminal.getTerminalPool()), null);
//  	}
//  	return records;
//  }

  public JRecords<BizTerminal> getTerminalsWithDriver(int driver) throws Exception {
  	JRecords<BizTerminal> records = new JRecords<BizTerminal>(BizTerminal.class);
  	records.setStatic(true);
  	JIterator<BizGroupTerminal> iter = this.getTerminals().getStaticIterator();
  	while (iter.hasMoreElements()) {
  		BizGroupTerminal terminal = iter.nextElement();
//  		if (poolId!=0 && poolId!=terminal.getTerminalPool()) continue;
  		if (!terminal.getObjTerminal().hasDriverConfig(driver)) continue;
  		records.addItem(terminal.getObjTerminal());
  	}
  	return records;
  }
  
	public BizTerminal getTerminalById(long terminalId) throws Exception {
		JIterator<BizGroupTerminal> iter = this.getTerminals().getStaticIterator();
		while (iter.hasMoreElements()) {
			BizGroupTerminal terminal = iter.nextElement();
			if (terminal.getTerminalId()!=terminalId) continue;
			return terminal.getObjTerminal();
		}
		return null;
	}
  
  
  @Override
	public void processInsert() throws Exception {
  	if (pTerminalGroup.isNull()) {
  		BizTerminalGroup max = new BizTerminalGroup();
  		max.addFilter("company", pCompany.getValue());
  		max.addFilter("nodo", pNodo.getValue());
  		pTerminalGroup.setValue(max.SelectMaxLong("terminal_group")+1);
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

