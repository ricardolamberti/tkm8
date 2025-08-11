package  pss.common.backup.settings;

import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizBackUpGroup extends JRecord {

  private JString pCompany = new JString();
  private JString pGrupo = new JString();


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  public void setCompany(String zValue) throws Exception {    pCompany.setValue(zValue);  }
  public String getCompany() throws Exception {     return pCompany.getValue();  }
  public boolean isNullCompany() throws Exception { return  pCompany.isNull(); } 
  public void setNullToCompany() throws Exception {  pCompany.setNull(); } 
  public void setGrupo(String zValue) throws Exception {    pGrupo.setValue(zValue);  }
  public String getGrupo() throws Exception {     return pGrupo.getValue();  }
  public boolean isNullGrupo() throws Exception { return  pGrupo.isNull(); } 
  public void setNullToGrupo() throws Exception {  pGrupo.setNull(); } 


  /**
   * Constructor de la Clase
   */
  public BizBackUpGroup() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "company", pCompany );
    this.addItem( "grupo", pGrupo );
  }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "company", "Company", true, true, 15 );
    this.addFixedItem( KEY, "grupo", "Grupo", true, true, 50 );
  }
  /**
   * Returns the table name
   */
  public String GetTable() { return "BCK_GRUPO"; }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  /**
   * Default read() method
   */
  public boolean read( String zCompany, String zGrupo ) throws Exception { 
    addFilter( "company",  zCompany ); 
    addFilter( "grupo",  zGrupo ); 
    return read(); 
  } 
}
