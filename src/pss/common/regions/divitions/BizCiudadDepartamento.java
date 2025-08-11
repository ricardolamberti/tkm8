package  pss.common.regions.divitions;

import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizCiudadDepartamento extends JRecord {

  private JString pCiudadId = new JString();
  private JString pProvincia = new JString();
  private JString pPais = new JString();
  private JLong pDepartamentoId = new JLong();
  private JLong pId = new JLong();


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  public void setCiudadId(String zValue) throws Exception {    pCiudadId.setValue(zValue);  }
  public String getCiudadId() throws Exception {     return pCiudadId.getValue();  }
  public boolean isNullCiudadId() throws Exception { return  pCiudadId.isNull(); } 
  public void setNullToCiudadId() throws Exception {  pCiudadId.setNull(); } 
  public void setProvincia(String zValue) throws Exception {    pProvincia.setValue(zValue);  }
  public String getProvincia() throws Exception {     return pProvincia.getValue();  }
  public boolean isNullProvincia() throws Exception { return  pProvincia.isNull(); } 
  public void setNullToProvincia() throws Exception {  pProvincia.setNull(); } 
  public void setPais(String zValue) throws Exception {    pPais.setValue(zValue);  }
  public String getPais() throws Exception {     return pPais.getValue();  }
  public boolean isNullPais() throws Exception { return  pPais.isNull(); } 
  public void setNullToPais() throws Exception {  pPais.setNull(); } 
  public void setDepartamentoId(long zValue) throws Exception {    pDepartamentoId.setValue(zValue);  }
  public long getDepartamentoId() throws Exception {     return pDepartamentoId.getValue();  }
  public boolean isNullDepartamentoId() throws Exception { return  pDepartamentoId.isNull(); } 
  public void setNullToDepartamentoId() throws Exception {  pDepartamentoId.setNull(); } 
  public void setId(long zValue) throws Exception {    pId.setValue(zValue);  }
  public long getId() throws Exception {     return pId.getValue();  }
  public boolean isNullId() throws Exception { return  pId.isNull(); } 
  public void setNullToId() throws Exception {  pId.setNull(); } 


  /**
   * Constructor de la Clase
   */
  public BizCiudadDepartamento() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "ciudad_id", pCiudadId );
    this.addItem( "provincia", pProvincia );
    this.addItem( "pais", pPais );
    this.addItem( "departamento_id", pDepartamentoId );
    this.addItem( "id", pId );
  }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( FIELD, "ciudad_id", "Ciudad id", true, true, 15 );
    this.addFixedItem( FIELD, "provincia", "Provincia", true, true, 15 );
    this.addFixedItem( FIELD, "pais", "Pais", true, true, 15 );
    this.addFixedItem( FIELD, "departamento_id", "Departamento id", true, true, 3 );
    this.addFixedItem( KEY, "id", "Id", true, true, 18 );
  }
  /**
   * Returns the table name
   */
  public String GetTable() { return "reg_ciudad_departamento"; }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  @Override
  public void processInsert() throws Exception {
  	if (pId.isNull()){
  		BizCiudadDepartamento c = new BizCiudadDepartamento();
  		pId.setValue(c.SelectMaxLong("id")+1);
  	}
  	super.processInsert();
  }

  /**
   * Default read() method
   */
  public boolean read( long zId ) throws Exception { 
    addFilter( "id",  zId ); 
    return read(); 
  } 

  public boolean read(String pais, String prov, String ciudad ,long dep) throws Exception { 
    addFilter( "pais",  pais ); 
    addFilter( "provincia",  prov ); 
    addFilter( "ciudad_id",  ciudad ); 
    addFilter( "departamento_id",  dep ); 
    return read(); 
  } 
  public boolean readByCiudad(String pais, String prov, String ciudad ) throws Exception { 
    addFilter( "pais",  pais ); 
    addFilter( "provincia",  prov ); 
    addFilter( "ciudad_id",  ciudad ); 
    return read(); 
  }
  
	BizDepartamento objDepartamento;
	public BizDepartamento getObjDepartamento() throws Exception {
		BizDepartamento d = new BizDepartamento();
		d.dontThrowException(true);
		if (!d.read(getDepartamentoId())) return null;
		return objDepartamento=d;
	}
}
