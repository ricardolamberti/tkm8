package pss.common.customDashboard2;

import pss.core.services.fields.JBoolean;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizDashBoardConfigBase extends JRecord {

	protected JString pDashName = new JString();
	protected JLong pId = new JLong();
	protected JString pCompany = new JString();
	protected JString pUserid = new JString();
	protected JLong pDashOrder = new JLong();
	protected JLong pDashDescription = new JLong();
	protected  JBoolean  pExcluded = new  JBoolean ();
	protected  JBoolean  pOnlyAdmin = new  JBoolean ();

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


	public void setDashName(String zValue) throws Exception {    pDashName.setValue(zValue);  }
	public String getDashName()	throws Exception {     return pDashName.getValue();  }
	public boolean isNullDashName() throws Exception { return  pDashName.isNull(); } 
	public void setNullToDashName() throws Exception {  pDashName.setNull(); } 
	public void setId(long zValue) throws Exception {    pId.setValue(zValue);  }
	public long getId()	throws Exception {     return pId.getValue();  }
	public boolean isNullId() throws Exception { return  pId.isNull(); } 
	public void setNullToId() throws Exception {  pId.setNull(); } 
	public void setCompany(String zValue) throws Exception {    pCompany.setValue(zValue);  }
	public String getCompany()	throws Exception {     return pCompany.getValue();  }
	public boolean isNullCompany() throws Exception { return  pCompany.isNull(); } 
	public void setNullToCompany() throws Exception {  pCompany.setNull(); } 
	public void setUserid(String zValue) throws Exception {    pUserid.setValue(zValue);  }
	public String getUserid()	throws Exception {     return pUserid.getValue();  }
	public boolean isNullUserid() throws Exception { return  pUserid.isNull(); } 
	public void setNullToUserid() throws Exception {  pUserid.setNull(); } 
	public void setDashOrder(long zValue) throws Exception {    pDashOrder.setValue(zValue);  }
	public long getDashOrder()	throws Exception {     return pDashOrder.getValue();  }
	public boolean isNullDashOrder() throws Exception { return  pDashOrder.isNull(); } 
	public void setNullToDashOrder() throws Exception {  pDashOrder.setNull(); } 
	public void setDashDescription(long zValue) throws Exception {    pDashDescription.setValue(zValue);  }
	public long getDashDescription()	throws Exception {     return pDashDescription.getValue();  }
	public boolean isNullDashDescription() throws Exception { return  pDashDescription.isNull(); } 
	public void setNullToDashDescription() throws Exception {  pDashDescription.setNull(); } 
	public void setExcluded( boolean  zValue) throws Exception {    pExcluded.setValue(zValue);  }
	public  boolean  getExcluded()	throws Exception {     return pExcluded.getValue();  }
	public boolean isNullExcluded() throws Exception { return  pExcluded.isNull(); } 
	public void setNullToExcluded() throws Exception {  pExcluded.setNull(); } 
	
	public void setOnlyAdmin( boolean  zValue) throws Exception {    pOnlyAdmin.setValue(zValue);  }
	public  boolean  isOnlyAdmin()	throws Exception {     return pOnlyAdmin.getValue();  }
	public boolean isNullOnlyAdmin() throws Exception { return  pOnlyAdmin.isNull(); } 
	public void setNullToOnlyAdmin() throws Exception {  pOnlyAdmin.setNull(); } 

  /**
   * Constructor de la Clase
   */
  public BizDashBoardConfigBase() throws Exception {
  }


	public void createProperties() throws Exception {
		addItem( "DASH_NAME", pDashName );
		addItem( "ID", pId );
		addItem( "COMPANY", pCompany );
		addItem( "USERID", pUserid );
		addItem( "DASH_ORDER", pDashOrder );
		addItem( "DASH_DESCRIPTION", pDashDescription );
		addItem( "EXCLUDED", pExcluded );
		addItem( "ONLY_ADMIN", pOnlyAdmin );
  }
  /**
   * Adds the fixed object properties
   */
	public void createFixedProperties() throws Exception {
		addFixedItem( FIELD, "DASH_NAME", "DASH_NAME", true,false, 100 );
		addFixedItem( KEY, "ID", "Id", false,false, 0 );
		addFixedItem( FIELD, "COMPANY", "Empresa", true, true, 250 );
		addFixedItem( FIELD, "USERID", "Usuario", true,false, 50 );
		addFixedItem( FIELD, "DASH_ORDER", "Orden", true, true, 3 );
		addFixedItem( FIELD, "DASH_DESCRIPTION", "Descripcion", true, true, 3 );
		addFixedItem( FIELD, "EXCLUDED", "EXCLUDED", true,false, 1 );
		addFixedItem( FIELD, "ONLY_ADMIN", "Solo Admin", true,false, 1 );
  }
  /**
   * Returns the table name
   */
	public String GetTable() { return "UI_DASHBOARD_ORDER"; }


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
