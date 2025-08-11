package pss.common.version.storeAndForward;

import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizStoreForwardHeader extends JRecord {

  private JLong pId = new JLong();
  private JString pStore = new JString();
  private JString pDescription = new JString();
 

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  public void setId(long zValue) throws Exception {    pId.setValue(zValue);  }
  public long getId() throws Exception {     return pId.getValue();  }
  public void setStore(String zValue) throws Exception {    pStore.setValue(zValue);  }
  public String getStore() throws Exception {     return pStore.getValue();  }
  public void setDescription(String zValue) throws Exception {    pDescription.setValue(zValue);  }
  public String getDescription() throws Exception {     return pDescription.getValue();  }
  

  /**
   * Constructor de la Clase
   */
  public BizStoreForwardHeader() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "id_pack", pId );
    this.addItem( "store", pStore );
    this.addItem( "description", pDescription );
   }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "id_pack", "Id", true, true, 18 );
    this.addFixedItem( KEY, "store", "Store", true, true, 250 );
    this.addFixedItem( FIELD, "description", "Descripcion", true, true, 250 );
  }
  /**
   * Returns the table name
   */
  public String GetTable() { return "vrs_store_forward_header"; }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  /**
   * Default read() method
   */
  public boolean read( String store, long zId ) throws Exception { 
    addFilter( "id_pack",  zId ); 
    addFilter( "store",  store ); 
    return read(); 
  } 
}
