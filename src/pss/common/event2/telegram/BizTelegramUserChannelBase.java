package pss.common.event2.telegram;

import java.util.Date;

import pss.core.services.fields.JBoolean;
import pss.core.services.fields.JDateTime;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;


public class BizTelegramUserChannelBase extends JRecord {



	public static final String TABLE_TELEGRAM_USER_CHANNEL = "TELEGRAM_USER_CHANNEL";


	public static final String ID = "ID";
	public static final String COMPANY = "COMPANY";
	public static final String USERID = "USERID";
	public static final String CHANNEL_ID = "CHANNEL_ID";
	public static final String REGISTERED = "REGISTERED";
	public static final String DATE_REGISTERED = "DATE_REGISTERED";
	public static final String DATE_CONFIRMED = "DATE_CONFIRMED";
	public static final String CONFIRMED = "CONFIRMED";
	public static final String CHANNEL_DESCRIPTION = "CHANNEL_DESCRIPTION";
	public static final String PIN = "PIN";
	public static final String ADMIN_USER = "ADMIN_USER";
	public static final String RESET_USER = "RESET_USER";


	public static final String D_ID = "Id";
	public static final String D_COMPANY = "Empresa";
	public static final String D_USERID = "Usuario";
	public static final String D_CHANNEL_ID = "CHANNEL_ID";
	public static final String D_REGISTERED = "Registrado";
	public static final String D_DATE_REGISTERED = "DATE_REGISTERED";
	public static final String D_DATE_CONFIRMED = "DATE_CONFIRMED";
	public static final String D_CONFIRMED = "CONFIRMED";
	public static final String D_CHANNEL_DESCRIPTION = "CHANNEL_DESCRIPTION";
	public static final String D_PIN = "Pin";
	public static final String D_ADMIN_USER = "ADMIN_USER";
	public static final String D_RESET_USER = "RESET_USER";


	protected JLong pId = new JLong();
	protected JString pCompany = new JString();
	protected JString pUserid = new JString();
	protected JString pChannelId = new JString();
	protected JBoolean pRegistered = new JBoolean();
	protected JDateTime pDateRegistered = new JDateTime();
	protected JDateTime pDateConfirmed = new JDateTime();
	protected JBoolean pConfirmed = new JBoolean();
	protected JString pChannelDescription = new JString();
	protected JLong pPin = new JLong();
	protected JBoolean pAdminUser = new JBoolean();
	protected JBoolean pResetUser = new JBoolean();

	

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
	public void setUserid(String zValue) throws Exception {    pUserid.setValue(zValue);  }
	public String getUserid()	throws Exception {     return pUserid.getValue();  }
	public boolean isNullUserid() throws Exception { return  pUserid.isNull(); } 
	public void setNullToUserid() throws Exception {  pUserid.setNull(); } 
	public void setChannelId(String zValue) throws Exception {    pChannelId.setValue(zValue);  }
	public String getChannelId()	throws Exception {     return pChannelId.getValue();  }
	public boolean isNullChannelId() throws Exception { return  pChannelId.isNull(); } 
	public void setNullToChannelId() throws Exception {  pChannelId.setNull(); } 
	public void setRegistered( boolean  zValue) throws Exception {    pRegistered.setValue(zValue);  }
	public  boolean  isRegistered()	throws Exception {     return pRegistered.getValue();  }
	public boolean isNullRegistered() throws Exception { return  pRegistered.isNull(); } 
	public void setNullToRegistered() throws Exception {  pRegistered.setNull(); } 
	public void setDateRegistered(Date zValue) throws Exception {    pDateRegistered.setValue(zValue);  }
	public Date getDateRegistered()	throws Exception {     return pDateRegistered.getValue();  }
	public boolean isNullDateRegistered() throws Exception { return  pDateRegistered.isNull(); } 
	public void setNullToDateRegistered() throws Exception {  pDateRegistered.setNull(); } 
	public void setDateConfirmed(Date zValue) throws Exception {    pDateConfirmed.setValue(zValue);  }
	public Date getDateConfirmed()	throws Exception {     return pDateConfirmed.getValue();  }
	public boolean isNullDateConfirmed() throws Exception { return  pDateConfirmed.isNull(); } 
	public void setNullToDateConfirmed() throws Exception {  pDateConfirmed.setNull(); } 
	public void setConfirmed( boolean  zValue) throws Exception {    pConfirmed.setValue(zValue);  }
	public  boolean  isConfirmed()	throws Exception {     return pConfirmed.getValue();  }
	public boolean isNullConfirmed() throws Exception { return  pConfirmed.isNull(); } 
	public void setNullToConfirmed() throws Exception {  pConfirmed.setNull(); } 
	public void setChannelDescription(String zValue) throws Exception {    pChannelDescription.setValue(zValue);  }
	public String getChannelDescription()	throws Exception {     return pChannelDescription.getValue();  }
	public boolean isNullChannelDescription() throws Exception { return  pChannelDescription.isNull(); } 
	public void setNullToChannelDescription() throws Exception {  pChannelDescription.setNull(); } 
	public void setPin(long zValue) throws Exception {    pPin.setValue(zValue);  }
	public long getPin()	throws Exception {     return pPin.getValue();  }
	public boolean isNullPin() throws Exception { return  pPin.isNull(); } 
	public void setNullToPin() throws Exception {  pPin.setNull(); } 
	public void setAdminUser( boolean  zValue) throws Exception {    pAdminUser.setValue(zValue);  }
	public  boolean  isAdminUser()	throws Exception {     return pAdminUser.getValue();  }
	public boolean isNullAdminUser() throws Exception { return  pAdminUser.isNull(); } 
	public void setNullToAdminUser() throws Exception {  pAdminUser.setNull(); } 
	public void setResetUser( boolean  zValue) throws Exception {    pResetUser.setValue(zValue);  }
	public  boolean  isResetUser()	throws Exception {     return pResetUser.getValue();  }
	public boolean isNullResetUser() throws Exception { return  pResetUser.isNull(); } 
	public void setNullToResetUser() throws Exception {  pResetUser.setNull(); } 

	protected BizTelegramUserChannel getThis() { return (BizTelegramUserChannel) this; } 
 


	/*
	* Constructor de la Clase
	*/
	public BizTelegramUserChannelBase() throws Exception {
	}


	public void createProperties() throws Exception {
		addItem( ID, pId );
		addItem( COMPANY, pCompany );
		addItem( USERID, pUserid );
		addItem( CHANNEL_ID, pChannelId );
		addItem( REGISTERED, pRegistered );
		addItem( DATE_REGISTERED, pDateRegistered );
		addItem( DATE_CONFIRMED, pDateConfirmed );
		addItem( CONFIRMED, pConfirmed );
		addItem( CHANNEL_DESCRIPTION, pChannelDescription );
		addItem( PIN, pPin );
		addItem( ADMIN_USER, pAdminUser );
		addItem( RESET_USER, pResetUser );
	}


	/*
	* Adds the fixed object properties
	*/
	public void createFixedProperties() throws Exception {
		addFixedItem( KEY, ID, BizTelegramUserChannelBase.D_ID, false,false, 18 );
		addFixedItem( FIELD, COMPANY, BizTelegramUserChannelBase.D_COMPANY, true,false, 50 );
		addFixedItem( FIELD, USERID, BizTelegramUserChannelBase.D_USERID, true,false, 50 );
		addFixedItem( FIELD, CHANNEL_ID, BizTelegramUserChannelBase.D_CHANNEL_ID, true,false, 100 );
		addFixedItem( FIELD, REGISTERED, BizTelegramUserChannelBase.D_REGISTERED, true,false, 1 );
		addFixedItem( FIELD, DATE_REGISTERED, BizTelegramUserChannelBase.D_DATE_REGISTERED, true,false, 10 );
		addFixedItem( FIELD, DATE_CONFIRMED, BizTelegramUserChannelBase.D_DATE_CONFIRMED, true,false, 10 );
		addFixedItem( FIELD, CONFIRMED, BizTelegramUserChannelBase.D_CONFIRMED, true,false, 1 );
		addFixedItem( FIELD, CHANNEL_DESCRIPTION, BizTelegramUserChannelBase.D_CHANNEL_DESCRIPTION, true,false, 500 );
		addFixedItem( FIELD, PIN, BizTelegramUserChannelBase.D_PIN, true,false, 6 );
		addFixedItem( FIELD, ADMIN_USER, BizTelegramUserChannelBase.D_ADMIN_USER, true,false, 1 );
		addFixedItem( FIELD, RESET_USER, BizTelegramUserChannelBase.D_RESET_USER, true,false, 1 );
	}


	/*
	* Returns the table name
	*/
	@Override
	public String GetTable() { return TABLE_TELEGRAM_USER_CHANNEL; }


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
