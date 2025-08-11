package pss.common.customList.config.customlist;

import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizCustomListFav extends JRecord {
	
//	public static final String SQL_BASED="SQL";

  private JString pCompany = new JString();
  private JLong pListId = new JLong();
  private JString pUsuario = new JString();
  

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  public void setCompany(String zValue) throws Exception {    pCompany.setValue(zValue);  }
  public String getCompany() throws Exception {     return pCompany.getValue();  }
  public void setUsuario(String zValue) throws Exception {    pUsuario.setValue(zValue);  }
  public String getUsuario() throws Exception {     return pUsuario.getValue();  }
  public void setListId(long zValue) throws Exception {    pListId.setValue(zValue);  }
  public long getListId() throws Exception {     return pListId.getValue();  }
  /**
   * Constructor de la Clase
   */
  public BizCustomListFav() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "company", pCompany );
    this.addItem( "list_id", pListId );
    this.addItem( "usuario", pUsuario);
  }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "company", "Company", true, true, 15 );
    this.addFixedItem( KEY, "list_id", "List id", true, true, 5 );
    this.addFixedItem( KEY, "usuario", "Usuario", true, true, 18 );
  }
  
  /**
   * Returns the table name
   */
  public String GetTable() { return "LST_CUSTOM_LISTV2_FAV"; }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  /**
   * Default read() method
   */
  public boolean read( String zCompany, long zListId ,String usuario) throws Exception { 
    addFilter( "company",  zCompany ); 
    addFilter( "list_id",  zListId ); 
    addFilter( "usuario",  usuario ); 
   return read(); 
  } 
  
 


  


}
