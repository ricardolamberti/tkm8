package  pss.common.security.license.license.detail;

import java.util.Date;

import pss.core.services.fields.JDate;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizLicenseDetail extends JRecord {

  private JLong pIdlicense = new JLong();
  private JString pField = new JString();
  private JString pValue = new JString();
  private JLong pCount = new JLong();
	private JDate pLastChange=new JDate();


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  public void setIdlicense(long zValue) throws Exception {    pIdlicense.setValue(zValue);  }
  public long getIdlicense() throws Exception {     return pIdlicense.getValue();  }
  public boolean isNullIdlicense() throws Exception { return  pIdlicense.isNull(); } 
  public void setNullToIdlicense() throws Exception {  pIdlicense.setNull(); } 
  public void setField(String zValue) throws Exception {    pField.setValue(zValue);  }
  public String getField() throws Exception {     return pField.getValue();  }
  public boolean isNullField() throws Exception { return  pField.isNull(); } 
  public void setNullToField() throws Exception {  pField.setNull(); } 
  public void setValue(String zValue) throws Exception {    pValue.setValue(zValue);  }
  public String getValue() throws Exception {     return pValue.getValue();  }
  public boolean isNullValue() throws Exception { return  pValue.isNull(); } 
  public void setNullToValue() throws Exception {  pValue.setNull(); } 

  public void setCount(long zValue) throws Exception {    pCount.setValue(zValue);  }
  public long getCount() throws Exception {     return pCount.getValue();  }
 
	public void setLastChange(Date zValue) throws Exception {
		pLastChange.setValue(zValue);
	}

	public Date getLastChange() throws Exception {
		return pLastChange.getValue();
	}
  /**
   * Constructor de la Clase
   */
  public BizLicenseDetail() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "id_license", pIdlicense );
    this.addItem( "field", pField );
    this.addItem( "value", pValue );
    this.addItem( "count", pCount );
		this.addItem( "lastChange", pLastChange);
  }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "id_license", "Id license", true, true, 64 );
    this.addFixedItem( KEY, "field", "Field", true, true, 50 );
    this.addFixedItem( FIELD, "value", "value", true, false, 100 );
    this.addFixedItem( FIELD, "count", "count", true, false, 15 );
		this.addFixedItem( FIELD, "lastChange", "Last Change", true, true, 50);
  }
  /**
   * Returns the table name
   */
  public String GetTable() { return "SEG_LICENSE_DETAIL"; }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public void processInsert() throws Exception {
		setLastChange(new Date());
		super.processInsert();
	}

  /**
   * Default read() method
   */
  public boolean read( long zIdlicense, String zField ) throws Exception { 
    addFilter( "id_license",  zIdlicense ); 
    addFilter( "field",  zField ); 
    return read(); 
  } 
}
