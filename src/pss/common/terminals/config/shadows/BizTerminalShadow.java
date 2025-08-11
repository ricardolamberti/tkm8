package pss.common.terminals.config.shadows;

import pss.common.terminals.connection.server.JTerminalPoolShadow;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizTerminalShadow extends JRecord {

  private JString pMacAddress = new JString();
  private JTerminalPoolShadow shadow=null;

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  public void setMacAddress(String zValue) throws Exception {    pMacAddress.setValue(zValue);  }
  public String getMacAddress() throws Exception {     return pMacAddress.getValue();  }
  public void setTerminalShadow(JTerminalPoolShadow value) throws Exception {    this.shadow=value;  }

  /**
   * Constructor de la Clase
   */
  public BizTerminalShadow() throws Exception {
  }
  @Override
	public void createProperties() throws Exception {
    this.addItem( "mac_address", pMacAddress );
  }
  /**
   * Adds the fixed object properties
   */
  @Override
	public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "mac_address", "Mac Address", true, true, 15 );
  }
  /**
   * Returns the table name
   */
  @Override
	public String GetTable() { return ""; }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  
}
