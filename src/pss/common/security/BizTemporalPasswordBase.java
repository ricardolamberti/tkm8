package pss.common.security;

import java.util.Date;

import pss.core.services.fields.JDateTime;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;


public class BizTemporalPasswordBase extends JRecord {



	protected JLong pId = new JLong();
	protected JString pPassword = new JString();
	protected JString pMail = new JString();
	protected JDateTime pStartDate = new JDateTime();
	protected JDateTime pEndDate = new JDateTime();


	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Getter & Setters methods
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


	public void setId(long zValue) throws Exception {    pId.setValue(zValue);  }
	public long getId()	throws Exception {     return pId.getValue();  }
	public boolean isNullId() throws Exception { return  pId.isNull(); } 
	public void setNullToId() throws Exception {  pId.setNull(); } 
	public void setPassword(String zValue) throws Exception {    pPassword.setValue(zValue);  }
	public String getPassword()	throws Exception {     return pPassword.getValue();  }
	public boolean isNullPassword() throws Exception { return  pPassword.isNull(); } 
	public void setNullToPassword() throws Exception {  pPassword.setNull(); } 
	public void setMail(String zValue) throws Exception {    pMail.setValue(zValue);  }
	public String getMail()	throws Exception {     return pMail.getValue();  }
	public boolean isNullMail() throws Exception { return  pMail.isNull(); } 
	public void setNullToMail() throws Exception {  pMail.setNull(); } 
	public void setStartDate(Date zValue) throws Exception {    pStartDate.setValue(zValue);  }
	public Date getStartDate()	throws Exception {     return pStartDate.getValue();  }
	public boolean isNullStartDate() throws Exception { return  pStartDate.isNull(); } 
	public void setNullToStartDate() throws Exception {  pStartDate.setNull(); } 
	public void setEndDate(Date zValue) throws Exception {    pEndDate.setValue(zValue);  }
	public Date getEndDate()	throws Exception {     return pEndDate.getValue();  }
	public boolean isNullEndDate() throws Exception { return  pEndDate.isNull(); } 
	public void setNullToEndDate() throws Exception {  pEndDate.setNull(); } 


	/*
	* Constructor de la Clase
	*/
	public BizTemporalPasswordBase() throws Exception {
	}


	public void createProperties() throws Exception {
		addItem( "ID", pId );
		addItem( "PASSWORD", pPassword );
		addItem( "MAIL", pMail );
		addItem( "START_DATE", pStartDate );
		addItem( "END_DATE", pEndDate );
	}


	/*
	* Adds the fixed object properties
	*/
	public void createFixedProperties() throws Exception {
		addFixedItem( FIELD, "ID", "Id", false,false, 18 );
		addFixedItem( KEY, "PASSWORD", "PASSWORD", true,false, 200 );
		addFixedItem( FIELD, "MAIL", "MAIL", true,false, 200 );
		addFixedItem( FIELD, "START_DATE", "START_DATE", true,false, 10 );
		addFixedItem( FIELD, "END_DATE", "END_DATE", true,false, 10 );
	}


	/*
	* Returns the table name
	*/
	public String GetTable() { return "SEG_TEMPORAL_PASSWORD"; }


	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Functionality methods
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


	/*
	* Default read() method
	*/
	public boolean read( String zPassword ) throws Exception { 
		addFilter( "PASSWORD",  zPassword ); 
		return read(); 
	} 


	public boolean read( long zId ) throws Exception { 
		addFilter( "ID",  zId ); 
		return read(); 
	} 


}
