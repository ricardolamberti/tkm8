package pss.common.version.pack.detail;

import pss.core.services.fields.JBoolean;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizPackageDetail extends JRecord {

  private JLong pIdpackage = new JLong();
  private JLong pIdsub_package = new JLong();
  private JString pKeyPack = new JString();
  private JString pPackage  = new JString();
  private JBoolean pDelete = new JBoolean();


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
 

  public void setIdpackage(long zValue) throws Exception {    pIdpackage.setValue(zValue);  }
  public long getIdpackage() throws Exception {     return pIdpackage.getValue();  }
  public boolean isNullIdpackage() throws Exception { return  pIdpackage.isNull(); } 
  public void setNullToIdpackage() throws Exception {  pIdpackage.setNull(); } 
  public void setIdsub_package(long zValue) throws Exception {    pIdsub_package.setValue(zValue);  }
  public long getIdsub_package() throws Exception {     return pIdsub_package.getValue();  }
  public boolean isNullIdsub_package() throws Exception { return  pIdsub_package.isNull(); } 
  public void setNullToIdsub_package() throws Exception {  pIdsub_package.setNull(); } 
  public void setKeyPack(String zValue) throws Exception {    pKeyPack.setValue(zValue);  }
  public String getKeyPack() throws Exception {     return pKeyPack.getValue();  }
  public boolean isNullKeyPack() throws Exception { return  pKeyPack.isNull(); } 
  public void setNullToKeyPack() throws Exception {  pKeyPack.setNull(); } 
  public void setPackage(String zValue) throws Exception {    pPackage.setValue(zValue);  }
  public String getPackage() throws Exception {     return pPackage.getValue();  }
  public boolean isNullPackage() throws Exception { return  pPackage.isNull(); } 
  public void setNullToPackage() throws Exception {  pPackage.setNull(); } 
  public boolean isDelete() throws Exception { return  pDelete.getValue(); } 
  public void setDelete(boolean zValue) throws Exception {  pDelete.setValue(zValue); } 


  /**
   * Constructor de la Clase
   */
  public BizPackageDetail() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "id_package", pIdpackage );
    this.addItem( "id_sub_package", pIdsub_package );
    this.addItem( "key_pack", pKeyPack );
    this.addItem( "pack", pPackage  );
    this.addItem( "is_delete", pDelete );
  }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "id_package", "Id package", true, true, 16 );
    this.addFixedItem( KEY, "id_sub_package", "Id sub package", true, true, 16 );
    this.addFixedItem( FIELD, "key_pack", "Key pack", true, true, 400 );
    this.addFixedItem( FIELD, "pack", "Package", true, true, 1000 );
    this.addFixedItem( FIELD, "is_delete", "Package", true, true, 1 );
  }
  /**
   * Returns the table name
   */
  public String GetTable() { return "vrs_package_detail"; }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  /**
   * Default read() method
   */
  public boolean read( long zIdpackage, long zIdsub_package ) throws Exception { 
    addFilter( "id_package",  zIdpackage ); 
    addFilter( "id_sub_package",  zIdsub_package ); 
    return read(); 
  } 
}
