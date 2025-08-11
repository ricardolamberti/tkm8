package pss.common.regions.entidad;

import pss.common.regions.entidad.nodos.BizEntidadNodo;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;


public class BizEntidad extends JRecord {
	
  private JLong pId = new JLong();
  private JString pCompany = new JString();
  private JString pDescripcion = new JString();
  private JString pUrl = new JString();
  

  public void setDescripcion(String zValue) throws Exception {    pDescripcion.setValue(zValue);  }
  public String getDescripcion() throws Exception {     return pDescripcion.getValue();  }
  public String getCompany() throws Exception {     return pCompany.getValue();  }
  public long getId() throws Exception { return pId.getValue();  }
  public String getUrl() throws Exception { return pUrl.getValue();  }

  /**
   * Constructor de la Clase
   */
  public BizEntidad() throws Exception {
  }
  
  @Override
  public void createProperties() throws Exception {
    this.addItem( "id", pId );
    this.addItem( "company", pCompany );
    this.addItem( "descripcion", pDescripcion );
    this.addItem( "url", pUrl );
  }
  
  @Override
	public void createFixedProperties() throws Exception {
  	this.addFixedItem( KEY, "id", "Id", false, false, 18 );
    this.addFixedItem( FIELD, "company", "Company", true, true, 15 );
    this.addFixedItem( FIELD, "descripcion", "Descripción", true, true, 100 );
    this.addFixedItem( FIELD, "url", "URL", true, false, 1000 );
  }
  /**
   * Returns the table name
   */
  @Override
	public String GetTable() { return "NOD_ENTIDAD"; }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  /**
   * Default Read() method
   */
  public boolean read(long id) throws Exception { 
    addFilter( "id",  id ); 
    return this.read(); 
  } 
  
  @Override
  public void processInsert() throws Exception {
  	super.processInsert();
  	this.pId.setValue(this.getIdentity("id"));
  }
  
  public JRecords<BizEntidadNodo> getNodos() throws Exception {
  	JRecords<BizEntidadNodo> recs = new JRecords<BizEntidadNodo>(BizEntidadNodo.class);
  	recs.addFilter("company", this.getCompany());
  	recs.addFilter("id_entidad", this.getId());
  	return recs;
  }
  
  public String makeUrl(String method) throws Exception {
  	String url = this.getUrl().replace("{{METHOD}}", method);
  	return url;
  }
  	
}
