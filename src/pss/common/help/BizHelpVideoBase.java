package pss.common.help;

import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizHelpVideoBase extends JRecord {

	private JLong pId = new JLong();
	private JString pBusiness = new JString();
	private JString pDescription = new JString();
	private JString pLink = new JString();


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


	public void setId(long zValue) throws Exception {    pId.setValue(zValue);  }
	public long getId()	throws Exception {     return pId.getValue();  }
	public boolean isNullId() throws Exception { return  pId.isNull(); } 
	public void setNullToId() throws Exception {  pId.setNull(); } 
	public void setBusiness(String zValue) throws Exception {    pBusiness.setValue(zValue);  }
	public String getBusiness()	throws Exception {     return pBusiness.getValue();  }
	public boolean isNullBusiness() throws Exception { return  pBusiness.isNull(); } 
	public void setNullToBusiness() throws Exception {  pBusiness.setNull(); } 
	public void setDescription(String zValue) throws Exception {    pDescription.setValue(zValue);  }
	public String getDescription()	throws Exception {     return pDescription.getValue();  }
	public boolean isNullDescription() throws Exception { return  pDescription.isNull(); } 
	public void setNullToDescription() throws Exception {  pDescription.setNull(); } 
	public void setLink(String zValue) throws Exception {    pLink.setValue(zValue);  }
	public String getLink()	throws Exception {     return pLink.getValue();  }
	public boolean isNullLink() throws Exception { return  pLink.isNull(); } 
	public void setNullToLink() throws Exception {  pLink.setNull(); } 


  /**
   * Constructor de la Clase
   */
  public BizHelpVideoBase() throws Exception {
  }


	public void createProperties() throws Exception {
		addItem( "ID", pId );
		addItem( "BUSINESS", pBusiness );
		addItem( "DESCRIPTION", pDescription );
		addItem( "LINK", pLink );
  }
  /**
   * Adds the fixed object properties
   */
	public void createFixedProperties() throws Exception {
		addFixedItem( KEY, "ID", "Id", false,false, 0 );
		addFixedItem( FIELD, "BUSINESS", "BUSINESS", true,false, 200 );
		addFixedItem( FIELD, "DESCRIPTION", "Descripcion", true,false, 200 );
		addFixedItem( FIELD, "LINK", "LINK", true,false, 100 );
  }
  /**
   * Returns the table name
   */
	public String GetTable() { return "HELP_VIDEO"; }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  /**
   * Default read() method
   */
	public boolean read( long zId ) throws Exception { 
		addFilter( "ID",  zId ); 
		return read(); 
  } 
}
