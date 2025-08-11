package pss.common.documentos.tipos;

import pss.common.documentos.BizDocumento;
import pss.common.security.BizUsuario;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;
import pss.core.win.JWins;

public class BizDocFisicoTipo extends JRecord {
 
	static final public String LOCAL = "LOCAL";
	static final public String CORREO = "CORREO";
	static final public String ELECT = "ELECT";
//	static final public String CORREO_EXT = "CORREO_EXT";
	
  private JString pIdtipo_doc = new JString();
  private JString pDescripcion = new JString();
  private JString pTransitorio = new JString();
  private JString pClase = new JString();

  
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  public void setIdtipo_doc(String zValue) throws Exception {    pIdtipo_doc.setValue(zValue);  }
  public String getIdtipo_doc() throws Exception {     return pIdtipo_doc.getValue();  }
  public void setDescripcion(String zValue) throws Exception {    pDescripcion.setValue(zValue);  }
  public String getDescripcion() throws Exception {     return pDescripcion.getValue();  }
  public void setTransitorio(String zValue) throws Exception {    pTransitorio.setValue(zValue);  }
  public String getTransitorio() throws Exception {     return pTransitorio.getValue();  }
  public void setClase(String zValue) throws Exception {    pClase.setValue(zValue);  }
  public String getClase() throws Exception {     return pClase.getValue();  }


  /**
   * Constructor de la Clase 
   */
  public BizDocFisicoTipo() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "id_tipo_doc", pIdtipo_doc );
    this.addItem( "descripcion", pDescripcion );
    this.addItem( "transitorio", pTransitorio );
    this.addItem( "clase", pClase );
  }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "id_tipo_doc", "Id tipo doc", true, true, 50 );
    this.addFixedItem( FIELD, "descripcion", "Descripcion", true, true, 250 );
    this.addFixedItem( FIELD, "transitorio", "Transitorio", true, true, 1 );
    this.addFixedItem( FIELD, "clase", "Clase", true, true, 250 );
  }
  /**
   * Returns the table name
   */
  public String GetTable() { return "JUD_DOC_FISICO_TIPO"; }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  /**
   * Default Read() method
   */
  public boolean Read( String zIdtipo_doc ) throws Exception { 
    this.addFilter( "id_tipo_doc",  zIdtipo_doc ); 
    return this.read(); 
  } 
  
  
  static JMap<String,String> clasesConocidas = null;
  static JMap<String,String> getClasesConocidas() throws Exception {
  	if( clasesConocidas!=null) return clasesConocidas;
  	JMap<String,String> cc = JCollectionFactory.createMap();
//  	cc.addElement(CORREO, "pss.erp.documentos.docEmail.BizDocEmail");
//  	cc.addElement(ELECT, "pss.erp.documentos.docElectronico.BizDocElectronico");
//  	cc.addElement(LOCAL, "pss.erp.documentos.docLocal.BizDocLocal");
  	return (clasesConocidas=BizUsuario.getUsr().getObjBusiness().documentClasesConocidas(cc));
  }
  
  public static JWins  getClases() throws Exception {
    return  JWins.createVirtualWinsFromMap(getClasesConocidas());
  }
  
  public String findClase() throws Exception {
  	return BizDocFisicoTipo.getClasesConocidas().getElement(this.pClase.getValue());
  }
  
  public BizDocumento getTipoDocInstance() throws Exception {
  	return (BizDocumento)Class.forName(this.findClase()).newInstance();
  }
  
	static JMap<String, BizDocFisicoTipo> hCache = null; 
  public static JMap<String, BizDocFisicoTipo> getTipos() throws Exception {
  	if (hCache==null) {
  		JRecords<BizDocFisicoTipo> recs = new JRecords<BizDocFisicoTipo>(BizDocFisicoTipo.class);
  		recs.readAll();
  		hCache=recs.convertToHash("id_tipo_doc");
  	}
  	return hCache;
  }
  
  public static String getDescrTipo(String tipo) throws Exception {
  	try {
  		return BizDocFisicoTipo.getTipos().getElement(tipo).getDescripcion();
  	} catch (Exception e) {
  		return "Desconocido";
  	}
  }

//  public static String getDescrSubTipo(String tipo, String subtipo) throws Exception {
//  	try {
//  		BizDocFisicoTipo st = BizDocFisicoTipo.getTipos().getElement(tipo);
//  		JMap<String, String> subtipos= st.getTipoDocInstance().getSubtipos();
//  		if (subtipos==null) return subtipo;
//  		return subtipos.getElement(subtipo);
//  	} catch (Exception e) {
//  		return "Desconocido";
//  	}
//  }

}
