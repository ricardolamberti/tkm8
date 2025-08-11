package  pss.common.diccionario;

import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizDiccionario extends JRecord {

  private JString pCompany = new JString();
  private JString pGrupo = new JString();
	private JLong pIdDiccionario = new JLong();
  private JString pDescripcion = new JString();


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  public void setCompany(String zValue) throws Exception {    pCompany.setValue(zValue);  }
  public String getCompany() throws Exception {     return pCompany.getValue();  }
  public boolean isNullCompany() throws Exception { return  pCompany.isNull(); } 
  public void setNullToCompany() throws Exception {  pCompany.setNull(); } 
  public void setDescripcion(String zValue) throws Exception {    pDescripcion.setValue(zValue);  }
  public String getDescripcion() throws Exception {     return pDescripcion.getValue();  }
  public boolean isNullDescripcion() throws Exception { return  pDescripcion.isNull(); } 
  public void setNullToDescripcion() throws Exception {  pDescripcion.setNull(); } 
  public void setIdDiccionario(long zValue) throws Exception {    pIdDiccionario.setValue(zValue);  }
  public long getIdDiccionario() throws Exception {     return pIdDiccionario.getValue();  }
  public boolean isNullIdDiccionario() throws Exception { return  pIdDiccionario.isNull(); } 
  public void setNullToIdDiccionario() throws Exception {  pIdDiccionario.setNull(); } 
  public void setGrupo(String zValue) throws Exception {    pGrupo.setValue(zValue);  }
  public String getGrupo() throws Exception {     return pGrupo.getValue();  }
  public boolean isNullGrupo() throws Exception { return  pGrupo.isNull(); } 
  public void setNullToGrupo() throws Exception {  pGrupo.setNull(); } 


  /**
   * Constructor de la Clase
   */
  public BizDiccionario() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "company", pCompany );
    this.addItem( "id_dicc", pIdDiccionario );
    this.addItem( "descripcion", pDescripcion );
    this.addItem( "grupo", pGrupo );
  }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "id_dicc", "Id diccionario", false, false, 64 );
    this.addFixedItem( FIELD, "company", "Company", true, true, 15 );
    this.addFixedItem( FIELD, "descripcion", "Descripcion", true, true, 250 );
    this.addFixedItem( FIELD, "grupo", "grupo", true, false, 50 );
  
  }
  /**
   * Returns the table name
   */
  public String GetTable() { return "tool_diccionario"; }

  /**
   * Default read() method
   */
  public boolean read(long zIdDiccionario ) throws Exception { 
    addFilter( "id_dicc",  zIdDiccionario ); 
    return read(); 
  } 
}
