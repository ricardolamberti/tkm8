package pss.tourism.telegram.event;

import java.util.Date;

import pss.core.services.fields.JDateTime;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;


public class BizTelegramEventBase extends JRecord {



	public static final String TABLE_TELEGRAM_EVENT = "TELEGRAM_EVENT";


	public static final String ID = "ID";
	public static final String COMPANY = "COMPANY";
	public static final String EVENT_CODE_ID = "EVENT_CODE_ID";
	public static final String EVENT_CODE = "EVENT_CODE";
	public static final String EVENT_DATE = "EVENT_DATE";


	public static final String D_ID = "Id";
	public static final String D_COMPANY = "Empresa";
	public static final String D_EVENT_CODE_ID = "EVENT_CODE_ID";
	public static final String D_EVENT_CODE = "EVENT_CODE";
	public static final String D_EVENT_DATE = "EVENT_DATE";


	protected JLong pId = new JLong();
	protected JString pCompany = new JString();
	protected JString pEventCodeid = new JString();
	protected JLong pEventCode = new JLong();
	protected JDateTime pEventDate = new JDateTime();


	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Getter & Setters methods
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////




	public String getDescripField() { 
		return "ID";
	}


	public void setId(long zValue) throws Exception {    pId.setValue(zValue);  }
	public long getId()	throws Exception {     return pId.getValue();  }
	public boolean isNullId() throws Exception { return  pId.isNull(); } 
	public void setNullToId() throws Exception {  pId.setNull(); } 
	public void setCompany(String zValue) throws Exception {    pCompany.setValue(zValue);  }
	public String getCompany()	throws Exception {     return pCompany.getValue();  }
	public boolean isNullCompany() throws Exception { return  pCompany.isNull(); } 
	public void setNullToCompany() throws Exception {  pCompany.setNull(); } 
	public void setEventCodeid(String zValue) throws Exception {    pEventCodeid.setValue(zValue);  }
	public String getEventCodeid()	throws Exception {     return pEventCodeid.getValue();  }
	public boolean isNullEventCodeid() throws Exception { return  pEventCodeid.isNull(); } 
	public void setNullToEventCodeid() throws Exception {  pEventCodeid.setNull(); } 
	public void setEventCode(long zValue) throws Exception {    pEventCode.setValue(zValue);  }
	public long getEventCode()	throws Exception {     return pEventCode.getValue();  }
	public boolean isNullEventCode() throws Exception { return  pEventCode.isNull(); } 
	public void setNullToEventCode() throws Exception {  pEventCode.setNull(); } 
	public void setEventDate(Date zValue) throws Exception {    pEventDate.setValue(zValue);  }
	public Date getEventDate()	throws Exception {     return pEventDate.getValue();  }
	public boolean isNullEventDate() throws Exception { return  pEventDate.isNull(); } 
	public void setNullToEventDate() throws Exception {  pEventDate.setNull(); } 

	protected BizTelegramEvent getThis() { return (BizTelegramEvent) this; } 
 


	/*
	* Constructor de la Clase
	*/
	public BizTelegramEventBase() throws Exception {
	}


	public void createProperties() throws Exception {
		addItem( ID, pId );
		addItem( COMPANY, pCompany );
		addItem( EVENT_CODE_ID, pEventCodeid );
		addItem( EVENT_CODE, pEventCode );
		addItem( EVENT_DATE, pEventDate );
	}


	/*
	* Adds the fixed object properties
	*/
	public void createFixedProperties() throws Exception {
		addFixedItem( KEY, ID, BizTelegramEventBase.D_ID, false,false, 18 );
		addFixedItem( FIELD, COMPANY, BizTelegramEventBase.D_COMPANY, true,false, 50 );
		addFixedItem( FIELD, EVENT_CODE_ID, BizTelegramEventBase.D_EVENT_CODE_ID, true,false, 50 );
		addFixedItem( FIELD, EVENT_CODE, BizTelegramEventBase.D_EVENT_CODE, true,false, 10 );
		addFixedItem( FIELD, EVENT_DATE, BizTelegramEventBase.D_EVENT_DATE, true,false, 10 );
	}


	/*
	* Returns the table name
	*/
	@Override
	public String GetTable() { return TABLE_TELEGRAM_EVENT; }


	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Functionality methods
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


	/*
	* Default read() method
	*/
	public boolean read( long zId ) throws Exception { 
		addFilter( ID,  zId ); 
		return read(); 
	} 


	@Override
	public void processInsert() throws Exception {
		super.processInsert();
		pId.setValue(this.getIdentity(ID));
	}

}
