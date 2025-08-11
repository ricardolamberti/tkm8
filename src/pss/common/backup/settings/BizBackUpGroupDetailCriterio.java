package  pss.common.backup.settings;

import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizBackUpGroupDetailCriterio extends JRecord {

	public final static String TYPE_COMPANY="COMP";
	public final static String TYPE_FDESDE="FDES";
	public final static String TYPE_FHASTA="FHAS";
	public final static String TYPE_MANUAL="MANU";
	public final static String TYPE_RELATION="RELA";
	
  private JString pCompany = new JString();
  private JString pGrupo = new JString();
  private JString pTabla = new JString();
  private JString pFieldName1 = new JString();
  private JString pOperator1 = new JString();
  private JString pValue1 = new JString();
  private JString pTypeOffield = new JString();


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
  public void setFieldName1(String zValue) throws Exception {    pFieldName1.setValue(zValue);  }
  public String getFieldName1() throws Exception {     return pFieldName1.getValue();  }
  public boolean isNullFieldName1() throws Exception { return  pFieldName1.isNull(); } 
  public void setNullToFieldName1() throws Exception {  pFieldName1.setNull(); } 
  public void setOperator1(String zValue) throws Exception {    pOperator1.setValue(zValue);  }
  public String getOperator1() throws Exception {     return pOperator1.getValue();  }
  public boolean isNullOperator1() throws Exception { return  pOperator1.isNull(); } 
  public void setNullToOperator1() throws Exception {  pOperator1.setNull(); } 
  public void setValue1(String zValue) throws Exception {    pValue1.setValue(zValue);  }
  public String getValue1() throws Exception {     return pValue1.getValue();  }
  public boolean isNullValue1() throws Exception { return  pValue1.isNull(); } 
  public void setNullToValue1() throws Exception {  pValue1.setNull(); } 
  public void setTypeOffield(String zValue) throws Exception {    pTypeOffield.setValue(zValue);  }
  public String getTypeOffield() throws Exception {     return pTypeOffield.getValue();  }
  public boolean isNullTypeOffield() throws Exception { return  pTypeOffield.isNull(); } 
  public void setNullToTypeOffield() throws Exception {  pTypeOffield.setNull(); } 


  /**
   * Constructor de la Clase
   */
  public BizBackUpGroupDetailCriterio() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "company", pCompany );
    this.addItem( "grupo", pGrupo );
    this.addItem( "tabla", pTabla );
    this.addItem( "field_name", pFieldName1 );
    this.addItem( "operator", pOperator1 );
    this.addItem( "value", pValue1 );
    this.addItem( "type_of_field", pTypeOffield );
  }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "company", "Company", true, true, 15 );
    this.addFixedItem( KEY, "grupo", "Grupo", true, true, 50 );
    this.addFixedItem( KEY, "tabla", "Tabla", true, true, 50 );
    this.addFixedItem( KEY, "field_name", "Campo", true, true, 50 );
    this.addFixedItem( KEY, "operator", "Operador", true, true, 10 );
    this.addFixedItem( FIELD, "value", "Valor", true, false, 50 );
    this.addFixedItem( FIELD, "type_of_field", "Tipo de Campo", true, true, 5 );
  }
  /**
   * Returns the table name
   */
  public String GetTable() { return "BCK_GRUPO_DET_CRITERIO"; }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  /**
   * Default read() method
   */
  public boolean read( String zCompany, String zGrupo, String zTabla, String zFieldName1, String zOperator1 ) throws Exception { 
    addFilter( "company",  zCompany ); 
    addFilter( "grupo",  zGrupo ); 
    addFilter( "tabla",  zTabla ); 
    addFilter( "field_name1",  zFieldName1 ); 
    addFilter( "operator1",  zOperator1 ); 
    return read(); 
  } 
}
