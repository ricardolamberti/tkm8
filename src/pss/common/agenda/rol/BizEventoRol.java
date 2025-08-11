package  pss.common.agenda.rol;

import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizEventoRol extends JRecord {

  private JLong pIdrol = new JLong();
  private JString pDescripcion = new JString();
  private JString pCompany = new JString();


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  public void setIdrol(long zValue) throws Exception {    pIdrol.setValue(zValue);  }
  public long getIdrol() throws Exception {     return pIdrol.getValue();  }
  public boolean isNullIdrol() throws Exception { return  pIdrol.isNull(); } 
  public void setNullToIdrol() throws Exception {  pIdrol.setNull(); } 
  public void setDescripcion(String zValue) throws Exception {    pDescripcion.setValue(zValue);  }
  public String getDescripcion() throws Exception {     return pDescripcion.getValue();  }
  public boolean isNullDescripcion() throws Exception { return  pDescripcion.isNull(); } 
  public void setNullToDescripcion() throws Exception {  pDescripcion.setNull(); } 
  public void setCompany(String zValue) throws Exception {    pCompany.setValue(zValue);  }
  public String getCompany() throws Exception {     return pCompany.getValue();  }
  public boolean isNullCompany() throws Exception { return  pCompany.isNull(); } 
  public void setNullToCompany() throws Exception {  pCompany.setNull(); } 


  /**
   * Constructor de la Clase
   */
  public BizEventoRol() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "id_rol", pIdrol );
    this.addItem( "descripcion", pDescripcion );
    this.addItem( "company", pCompany );
  }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "id_rol", "Id rol", false, false, 64 );
    this.addFixedItem( FIELD, "descripcion", "Descripcion", true, true, 100 );
    this.addFixedItem( KEY, "company", "Company", true, true, 15 );
  }
  /**
   * Returns the table name
   */
  public String GetTable() { return "agd_evento_rol"; }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
@Override
public void processInsert() throws Exception {
	// TODO Auto-generated method stub
	super.processInsert();
	setIdrol(getIdentity("id_rol"));
}

  /**
   * Default read() method
   */
  public boolean read( long zIdrol, String zCompany ) throws Exception { 
    addFilter( "id_rol",  zIdrol ); 
    addFilter( "company",  zCompany ); 
    return read(); 
  } 
}
