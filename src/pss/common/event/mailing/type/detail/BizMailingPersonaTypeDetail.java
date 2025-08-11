package  pss.common.event.mailing.type.detail;

import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizMailingPersonaTypeDetail extends JRecord {

	private JLong pIdtipo = new JLong();
	private JLong pIdDetail = new JLong();
  private JString pCompany = new JString();
  private JString pDK = new JString();
  
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  public void setIdtipo(long zValue) throws Exception {    pIdtipo.setValue(zValue);  }
  public long getIdtipo() throws Exception {     return pIdtipo.getValue();  }
  public void setIdDetail(long zValue) throws Exception {    pIdDetail.setValue(zValue);  }
  public long getIdDetail() throws Exception {     return pIdDetail.getValue();  }
  public void setCompany(String zValue) throws Exception {    pCompany.setValue(zValue);  }
  public String getCompany() throws Exception {     return pCompany.getValue();  }
  public void setDK(String zValue) throws Exception {    pDK.setValue(zValue);  }
  public String getDK() throws Exception {     return pDK.getValue();  }
 

  public BizMailingPersonaTypeDetail() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "company", pCompany);
    this.addItem( "id_tipo", pIdtipo);
    this.addItem( "id_detail", pIdDetail );
    this.addItem( "dk", pDK );

  }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "id_detail", "Id", false, false, 64 );
    this.addFixedItem( FIELD, "company", "Company", true, true, 50 );
    this.addFixedItem( FIELD, "id_tipo", "id_tipo", true, false, 64 );
    this.addFixedItem( FIELD, "dk", "dk", true, false, 250 );
 }
  /**
   * Returns the table name
   */
  public String GetTable() { return "BSP_PERSONA_TYPES_DETAIL"; }

  public void processInsert() throws Exception {
   	super.processInsert();
  }
  public void processUpdate() throws Exception {
   	super.processUpdate();
  };

  /**
   * Default Read() method
   */
  public boolean Read( long zIdtipoDetail) throws Exception { 
    this.addFilter( "id_detail",  zIdtipoDetail ); 
    return this.read(); 
  } 
 }
