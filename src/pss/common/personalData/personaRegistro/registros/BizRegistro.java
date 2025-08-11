package  pss.common.personalData.personaRegistro.registros;

import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.collections.JMap;

public class BizRegistro extends JRecord {
 
  private JString pId = new JString();
  private JString pDescripcion = new JString();
  private JString pIdTipoSociedad = new JString();

  
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  public void setId(String zValue) throws Exception {    pId.setValue(zValue);  }
  public String getId() throws Exception {     return pId.getValue();  }
  public void setDescripcion(String zValue) throws Exception {    pDescripcion.setValue(zValue);  }
  public String getDescripcion() throws Exception {     return pDescripcion.getValue();  }


  /**
   * Constructor de la Clase
   */
  public BizRegistro() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "id", pId);
    this.addItem( "descripcion", pDescripcion );
    this.addItem( "id_tipo_sociedad", pIdTipoSociedad );
    }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "id", "Id", true, true, 5 );
    this.addFixedItem( FIELD, "descripcion", "Descripcion", true, true, 250 );
    this.addFixedItem( FIELD, "id_tipo_sociedad", "id_tipo_sociedad", true, false, 18 );
  }
  /**
   * Returns the table name
   */
  public String GetTable() { return "PER_REGISTROS"; }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  /**
   * Default Read() method
   */
  public boolean read( String id ) throws Exception { 
    this.addFilter( "id",  id); 
    return this.read(); 
  } 
  public boolean readByIdTipoSociedad( String id ) throws Exception { 
    this.addFilter( "id_tipo_sociedad",  id); 
    return this.read(); 
  }  
  
  @Override
  public void processInsert() throws Exception {
  	if (this.pId.isNull()) {
  		BizRegistro max = new BizRegistro();
  		pId.setValue(max.SelectMaxIntFromString("id")+1);
  	}
  	this.insert();
  }
  
	static JMap<String, BizRegistro> hCache = null; 
  public static JMap<String, BizRegistro> getRegistros() throws Exception {
  	if (hCache==null) {
  		JRecords<BizRegistro> recs = new JRecords<BizRegistro>(BizRegistro.class);
  		recs.readAll();
  		hCache=recs.convertToHash("id");
  	}
  	return hCache;
  }
  
  public static String getDescrRegistro(String reg) throws Exception {
  	try {
  		return BizRegistro.getRegistros().getElement(reg).getDescripcion();
  	} catch (Exception e) {
  		return "Desconocido";
  	}
  }
  
  

}
