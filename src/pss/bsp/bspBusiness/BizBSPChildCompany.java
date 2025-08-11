package pss.bsp.bspBusiness;

import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizBSPChildCompany  extends JRecord {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2323808744795823915L;
	private JLong pId = new JLong();
	private JString pCompany = new JString();
	private JString pChildCompany = new JString();
  

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void setId(long zValue) throws Exception {    pId.setValue(zValue);  }
  public long getId() throws Exception {     return pId.getValue();  }
  public boolean isNullId() throws Exception { return  pId.isNull(); } 
  public void setNullToId() throws Exception {  pId.setNull(); } 
  
  public void setCompany(String zValue) throws Exception {    pCompany.setValue(zValue);  }
  public String getCompany() throws Exception {     return pCompany.getValue();  }
  public boolean isNullCompany() throws Exception { return  pCompany.isNull(); } 
  public void setNullToCompany() throws Exception {  pCompany.setNull(); } 
  
  public void setChildCompany(String zValue) throws Exception {    pChildCompany.setValue(zValue);  }
  public String getChildCompany() throws Exception {     return pChildCompany.getValue();  }
  public boolean isNullChildCompany() throws Exception { return  pChildCompany.isNull(); } 
  public void setNullToChildCompany() throws Exception {  pChildCompany.setNull(); } 


  /**
   * Constructor de la Clase
   */
  public BizBSPChildCompany() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "company", pCompany );
    this.addItem( "child_company", pChildCompany );
    this.addItem( "id", pId );
  }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "id", "id", false, false, 64 );
    this.addFixedItem( FIELD, "company", "Company", true, true, 15 );
    this.addFixedItem( FIELD, "child_company", "Company hija", true, true, 15 );
  }
  /**
   * Returns the table name
   */
  public String GetTable() { return "bsp_child_company"; }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  /**
   * Default read() method
   */
  public boolean read( long id ) throws Exception { 
    addFilter( "id",  id ); 
    return read(); 
  } 
  
  

}
