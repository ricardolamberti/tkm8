package  pss.common.backup.settings;

import pss.core.services.fields.JBoolean;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizBackUpGroupDetail extends JRecord {
	
	public final static String TYPE_INCREMENTAL="INC";
	public final static String TYPE_ALL="ALL";
	

  private JString pCompany = new JString();
  private JString pGrupo = new JString();
  private JString pTabla = new JString();
  private JString pType = new JString();
  private JString pHeader_Table = new JString();
	private JBoolean pIsDetail = new JBoolean();


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
  public void setTabla(String zValue) throws Exception {    pTabla.setValue(zValue);  }
  public String getTabla() throws Exception {     return pTabla.getValue();  }
  public boolean isNullTabla() throws Exception { return  pTabla.isNull(); } 
  public void setNullToTabla() throws Exception {  pTabla.setNull(); } 
  public void setType(String zValue) throws Exception {    pType.setValue(zValue);  }
  public String getType() throws Exception {     return pType.getValue();  }
  public boolean isNullType() throws Exception { return  pType.isNull(); } 
  public void setNullToType() throws Exception {  pType.setNull(); } 
  public String getHeaderTable() throws Exception {     return pHeader_Table.getValue();  }
  public Boolean isDetail() throws Exception { return pIsDetail.getValue();  }


  /**
   * Constructor de la Clase
   */
  public BizBackUpGroupDetail() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "company", pCompany );
    this.addItem( "grupo", pGrupo );
    this.addItem( "tabla", pTabla );
    this.addItem( "type", pType );
    this.addItem( "header_table", pHeader_Table );
    this.addItem( "is_detail", pIsDetail );
  }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "company", "Company", true, true, 15 );
    this.addFixedItem( KEY, "grupo", "Grupo", true, true, 50 );
    this.addFixedItem( KEY, "tabla", "Tabla", true, true, 50 );
    this.addFixedItem( FIELD, "type", "Tipo", true, true, 3 );
    this.addFixedItem( FIELD, "header_table", "Tabla Header", true, false, 50 );
    this.addFixedItem( FIELD, "is_detail", "Es Tabla Detalle", true, true, 1 );
  }
  /**
   * Returns the table name
   */
  public String GetTable() { return "BCK_GRUPO_DETALLE"; }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  /**
   * Default read() method
   */
  public boolean read( String zCompany, String zGrupo, String zTabla ) throws Exception { 
    addFilter( "company",  zCompany ); 
    addFilter( "grupo",  zGrupo ); 
    addFilter( "tabla",  zTabla ); 
    return read(); 
  } 
}
