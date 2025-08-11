package pss.common.version.storeAndForward.detail;

import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizStoreForward extends JRecord {

  private JLong pIdPack = new JLong();
  private JLong pId = new JLong();
  private JString pStore = new JString();
  private JString pKeyMessage = new JString();
  private JString pMessage = new JString();


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  public void setId(long zValue) throws Exception {    pId.setValue(zValue);  }
  public long getId() throws Exception {     return pId.getValue();  }
  public void setIdPack(long zValue) throws Exception {    pIdPack.setValue(zValue);  }
  public long getIdPack() throws Exception {     return pIdPack.getValue();  }
  public void setStore(String zValue) throws Exception {    pStore.setValue(zValue);  }
  public String getStore() throws Exception {     return pStore.getValue();  }
  public void setKeyMessage(String zValue) throws Exception {    pKeyMessage.setValue(zValue);  }
  public String getKeyMessage() throws Exception {     return pKeyMessage.getValue();  }
  public void setMessage(String zValue) throws Exception {    pMessage.setValue(zValue);  }
  public String getMessage() throws Exception {     return pMessage.getValue();  }
 

  /**
   * Constructor de la Clase
   */
  public BizStoreForward() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "id", pId );
    this.addItem( "id_pack", pIdPack );
    this.addItem( "store", pStore );
    this.addItem( "key_message", pKeyMessage );
    this.addItem( "message", pMessage );
   }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "id_pack", "IdPack", true, true, 18 );
    this.addFixedItem( KEY, "id", "Id", true, true, 18 );
    this.addFixedItem( KEY, "store", "Store", true, true, 250 );
    this.addFixedItem( FIELD, "key_message", "key", true, true,250 );
    this.addFixedItem( FIELD, "message", "Message", true, true, 1000 );
  }
  /**
   * Returns the table name
   */
  public String GetTable() { return "vrs_store_forward"; }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  /**
   * Default read() method
   */
  public boolean read(long idPack, long zId ) throws Exception { 
    addFilter( "idPack",  idPack ); 
    addFilter( "id",  zId ); 
    return read(); 
  } 
}
