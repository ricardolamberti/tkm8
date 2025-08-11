package pss.common.paymentManagement;

import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizPenalizeDebtBase extends JRecord {

	private JLong pId = new JLong();
	private JString pCompany = new JString();
	private JLong pPenalizeTime = new JLong();


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


	public void setId(long zValue) throws Exception {    pId.setValue(zValue);  }
	public long getId()	throws Exception {     return pId.getValue();  }
	public boolean isNullId() throws Exception { return  pId.isNull(); } 
	public void setNullToId() throws Exception {  pId.setNull(); } 
	public void setCompany(String zValue) throws Exception {    pCompany.setValue(zValue);  }
	public String getCompany()	throws Exception {     return pCompany.getValue();  }
	public boolean isNullCompany() throws Exception { return  pCompany.isNull(); } 
	public void setNullToCompany() throws Exception {  pCompany.setNull(); } 
	public void setPenalizeTime(long zValue) throws Exception {    pPenalizeTime.setValue(zValue);  }
	public long getPenalizeTime()	throws Exception {     return pPenalizeTime.getValue();  }
	public boolean isNullPenalizeTime() throws Exception { return  pPenalizeTime.isNull(); } 
	public void setNullToPenalizeTime() throws Exception {  pPenalizeTime.setNull(); } 


  /**
   * Constructor de la Clase
   */
  public BizPenalizeDebtBase() throws Exception {
  }


	public void createProperties() throws Exception {
		addItem( "ID", pId );
		addItem( "COMPANY", pCompany );
		addItem( "PENALIZE_TIME", pPenalizeTime );
  }
  /**
   * Adds the fixed object properties
   */
	public void createFixedProperties() throws Exception {
		addFixedItem( FIELD, "ID", "Id", false,false, 0 );
		addFixedItem( KEY, "COMPANY", "Empresa", true,false, 50 );
		addFixedItem( FIELD, "PENALIZE_TIME", "PENALIZE_TIME", true,false, 6 );
  }
  /**
   * Returns the table name
   */
	public String GetTable() { return "PAY_PENALIZE_DEBT"; }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  /**
   * Default read() method
   */
	public boolean read( String zCompany ) throws Exception { 
		addFilter( "COMPANY",  zCompany ); 
		return read(); 
  } 
}
