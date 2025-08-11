package  pss.common.pki.signs;

import pss.common.security.BizUsuario;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

/**
 * Manejo de una firma privada desde un archivo 
 * @author RJL
 *
 */
public class BizSign extends JRecord {

  private JString pCompany = new JString();
  private JString pSignId = new JString();
  private JString pSignDescription = new JString();
  private JString pSignFile = new JString();
  private JString pSignPassword = new JString();
  private JString pSignFormat = new JString();


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  public void setCompany(String zValue) throws Exception {    pCompany.setValue(zValue);  }
  public String getCompany() throws Exception {     return pCompany.getValue();  }
  public void setSignId(String zValue) throws Exception {    pSignId.setValue(zValue);  }
  public String getSignId() throws Exception {     return pSignId.getValue();  }
  public void setSignDescription(String zValue) throws Exception {    pSignDescription.setValue(zValue);  }
  public String getSignDescription() throws Exception {     return pSignDescription.getValue();  }
  public void setSignFile(String zValue) throws Exception {    pSignFile.setValue(zValue);  }
  public String getSignFile() throws Exception {     return pSignFile.getValue();  }
  public void setSignPassword(String zValue) throws Exception {    pSignPassword.setValue(zValue);  }
  public String getSignPassword() throws Exception {     return pSignPassword.getValue();  }
  public void setSignFormat(String zValue) throws Exception {    pSignFormat.setValue(zValue);  }
  public String getSignFormat() throws Exception {     return pSignFormat.getValue();  }


  /**
   * Constructor de la Clase
   */
  public BizSign() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "company", pCompany );
    this.addItem( "sign_id", pSignId );
    this.addItem( "sign_description", pSignDescription );
    this.addItem( "sign_file", pSignFile );
    this.addItem( "sign_password", pSignPassword );
    this.addItem( "sign_format", pSignFormat );
  }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "company", "Compania", true, true, 15 );
    this.addFixedItem( KEY, "sign_id", "Sign id", true, true, 30 );
    this.addFixedItem( FIELD, "sign_description", "Sign description", true, true, 100 );
    this.addFixedItem( FIELD, "sign_file", "Sign file", true, true, 150 );
    this.addFixedItem( FIELD, "sign_password", "Sign password", true, true, 20 );
    this.addFixedItem( FIELD, "sign_format", "Sign format", true, true, 30 );
  }
  /**
   * Returns the table name
   */
  public String GetTable() { return "pki_signs"; }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public boolean read( String zSignId ) throws Exception { 
    addFilter( "company",  BizUsuario.getUsr().getCompany() ); 
    addFilter( "sign_id",  zSignId ); 
    return read(); 
  } 
  /**
   * Default read() method
   */
  public boolean read( String zCompany, String zSignId ) throws Exception { 
    addFilter( "company",  zCompany ); 
    addFilter( "sign_id",  zSignId ); 
    return read(); 
  } 
}
