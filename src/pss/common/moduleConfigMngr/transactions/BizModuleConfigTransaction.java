package  pss.common.moduleConfigMngr.transactions;

import java.util.Date;

import pss.core.services.fields.JDate;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizModuleConfigTransaction extends JRecord {
	
  private JString pCompany = new JString();
  private JString pCountry = new JString();
  private JString pNode = new JString();
  private JString pModuleId = new JString();
  private JString pConfigType = new JString();
  private JLong pConfId = new JLong();
  private JLong pTransId = new JLong();
  private JDate pCreationDatetime = new JDate();
  private JString pDescription = new JString();
  private JString pTransactionMsg = new JString();


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  public void setCompany(String zValue) throws Exception {    pCompany.setValue(zValue);  }
  public String getCompany() throws Exception {     return pCompany.getValue();  }
  public boolean isNullCompany() throws Exception { return  pCompany.isNull(); } 
  public void setNullToCompany() throws Exception {  pCompany.setNull(); } 
  public void setCountry(String zValue) throws Exception {    pCountry.setValue(zValue);  }
  public String getCountry() throws Exception {     return pCountry.getValue();  }
  public boolean isNullCountry() throws Exception { return  pCountry.isNull(); } 
  public void setNullToCountry() throws Exception {  pCountry.setNull(); } 
  public void setNode(String zValue) throws Exception {    pNode.setValue(zValue);  }
  public String getNode() throws Exception {     return pNode.getValue();  }
  public boolean isNullNode() throws Exception { return  pNode.isNull(); } 
  public void setNullToNode() throws Exception {  pNode.setNull(); } 
  public void setModuleId(String zValue) throws Exception {    pModuleId.setValue(zValue);  }
  public String getModuleId() throws Exception {     return pModuleId.getValue();  }
  public boolean isNullModuleId() throws Exception { return  pModuleId.isNull(); } 
  public void setNullToModuleId() throws Exception {  pModuleId.setNull(); } 
  public void setConfigType(String zValue) throws Exception {    pConfigType.setValue(zValue);  }
  public String getConfigType() throws Exception {     return pConfigType.getValue();  }
  public boolean isNullConfigType() throws Exception { return  pConfigType.isNull(); } 
  public void setNullToConfigType() throws Exception {  pConfigType.setNull(); } 
  public void setConfId(long zValue) throws Exception {    pConfId.setValue(zValue);  }
  public long getConfId() throws Exception {     return pConfId.getValue();  }
  public boolean isNullConfId() throws Exception { return  pConfId.isNull(); } 
  public void setNullToConfId() throws Exception {  pConfId.setNull(); } 
  public void setCreationDatetime(Date zValue) throws Exception {    pCreationDatetime.setValue(zValue);  }
  public Date getCreationDatetime() throws Exception {     return pCreationDatetime.getValue();  }
  public boolean isNullCreationDatetime() throws Exception { return  pCreationDatetime.isNull(); } 
  public void setNullToCreationDatetime() throws Exception {  pCreationDatetime.setNull(); } 
  public void setDescription(String zValue) throws Exception {    pDescription.setValue(zValue);  }
  public String getDescription() throws Exception {     return pDescription.getValue();  }
  public boolean isNullDescription() throws Exception { return  pDescription.isNull(); } 
  public void setNullToDescription() throws Exception {  pDescription.setNull(); } 
  public void setTransactionMsg(String zValue) throws Exception {    pTransactionMsg.setValue(zValue);  }
  public String getTransactionMsg() throws Exception {     return pTransactionMsg.getValue();  }
  public boolean isNullTransactionMsg() throws Exception { return  pTransactionMsg.isNull(); } 
  public void setNullToTransactionMsg() throws Exception {  pTransactionMsg.setNull(); } 
  public void setTransId(long zValue) throws Exception {    pTransId.setValue(zValue);  }
  public long getTransId() throws Exception {     return pTransId.getValue();  }
  public boolean isNullTransId() throws Exception { return  pTransId.isNull(); } 
  public void setNullToTransId() throws Exception {  pTransId.setNull(); } 


  /**
   * Constructor de la Clase
   */
  public BizModuleConfigTransaction() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "company", pCompany );
    this.addItem( "country", pCountry );
    this.addItem( "node", pNode );
    this.addItem( "module_id", pModuleId );
    this.addItem( "config_type", pConfigType );
    this.addItem( "conf_id", pConfId );
    this.addItem( "trans_id", pTransId );
    this.addItem( "creation_datetime", pCreationDatetime );
    this.addItem( "description", pDescription );
    this.addItem( "transaction_msg", pTransactionMsg );
  }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "company", "Company", true, true, 15 );
    this.addFixedItem( KEY, "country", "Country", true, true, 50 );
    this.addFixedItem( KEY, "node", "Nodo", true, true, 50 );
    this.addFixedItem( KEY, "module_id", "Module id", true, true, 50 );
    this.addFixedItem( KEY, "config_type", "Config type", true, true, 10 );
    this.addFixedItem( KEY, "conf_id", "Conf id", true, true, 10 );
    this.addFixedItem( KEY, "trans_id", "Trans id", true, true, 10 );
    this.addFixedItem( FIELD, "creation_datetime", "Creation datetime", true, true, 10 );
    this.addFixedItem( FIELD, "description", "Description", true, true, 50 );
    this.addFixedItem( FIELD, "transaction_msg", "Transaction msg", true, true,800 );
  }
  /**
   * Returns the table name
   */
  public String GetTable() { return "mdl_config_transactions"; }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  /**
   * Default read() method
   */
  public boolean read( String zCompany, String zCountry, String zNode, String zModuleId, String zConfigType, long zConfId , long zTransId) throws Exception { 
    addFilter( "company",  zCompany ); 
    addFilter( "country",  zCountry ); 
    addFilter( "node",  zNode ); 
    addFilter( "module_id",  zModuleId ); 
    addFilter( "config_type",  zConfigType ); 
    addFilter( "conf_id",  zConfId ); 
    addFilter( "trans_id",  zTransId ); 
    return read(); 
  }
  
  @Override
  public void processInsert() throws Exception {
  	BizModuleConfigTransaction MCT = new BizModuleConfigTransaction();
  	MCT.addFilter( "company",  this.getCompany() ); 
  	MCT.addFilter( "country",  this.getCountry() ); 
  	MCT.addFilter( "node",  this.getNode() ); 
  	MCT.addFilter( "module_id",  this.getModuleId() ); 
  	MCT.addFilter( "config_type",  this.getConfigType() ); 
  	MCT.addFilter( "conf_id",  this.getConfId()); 
    MCT.dontThrowException(true);

    long lNextTransId = MCT.SelectMaxLong("trans_id");
//    long lNextTransId = (Integer)MCT.selectObjectMax("trans_id", true);
    this.setTransId(lNextTransId+1);
    
    super.processInsert();
  }
}
