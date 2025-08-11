package pss.common.event.mailing;

import pss.common.customList.config.relation.JRelations;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;

public class BizBSPPersona extends JRecord {

  private JString pCompany = new JString();
  private JLong pId = new JLong();
  private JString pTipo = new JString();
  private JString pCodigo = new JString();
  private JString pDescripcion = new JString();
  private JString pMail = new JString();
  private JString pDescrip = new JString() {
  	public void preset() throws Exception {
  		pDescrip.setValue(getDescripOperativa());
  	};
  };
  

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  public void setId(long zValue) throws Exception {    pId.setValue(zValue);  }
  public long getId() throws Exception {     return pId.getValue();  }
  public void setCompany(String zValue) throws Exception {    pCompany.setValue(zValue);  }
  public String getCompany() throws Exception {     return pCompany.getValue();  }
  
  public void setDescipcion(String zValue) throws Exception {    pDescripcion.setValue(zValue);  }
  public String getDescripcion() throws Exception {     return pDescripcion.getValue();  }
  public boolean isNullDescripcion() throws Exception { return  pDescripcion.isNull(); } 
  public void setNullToDescripcion() throws Exception {  pDescripcion.setNull(); } 

  public void setCodigo(String zValue) throws Exception {    pCodigo.setValue(zValue);  }
  public String getCodigo() throws Exception {     return pCodigo.getValue();  }
  public boolean isNullCodigo() throws Exception { return  pCodigo.isNull(); } 
  public void setNullToCodigo() throws Exception {  pCodigo.setNull(); } 

  public void setMail(String zValue) throws Exception {    pMail.setValue(zValue);  }
  public String getMail() throws Exception {     return pMail.getValue();  }
  public boolean isNullMail() throws Exception { return  pMail.isNull(); } 
  public void setNullToMail() throws Exception {  pMail.setNull(); } 

  public void setTipo(String zValue) throws Exception {    pTipo.setValue(zValue);  }
  public String getTipo() throws Exception {     return pTipo.getValue();  }
  public boolean isNullTipo() throws Exception { return  pTipo.isNull(); } 
  public void setNullToTipo() throws Exception {  pTipo.setNull(); } 

  public String getDescripOperativa() throws Exception {     
  	return pCodigo.getValue()+"-"+pDescripcion.getValue();  
  }
  

  /**
   * Constructor de la Clase
   */
  public BizBSPPersona() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "id", pId );
    this.addItem( "company", pCompany );
    this.addItem( "codigo", pCodigo );
    this.addItem( "descripcion", pDescripcion );
    this.addItem( "descripcion_op", pDescrip);
    this.addItem( "mail", pMail );
    this.addItem( "tipo", pTipo );
  }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "id", "Id", false, false, 64 );
    this.addFixedItem( KEY, "company", "Compania", true, true, 15 );
    this.addFixedItem( FIELD, "codigo", "Codigo", true, true, 50 );
    this.addFixedItem( FIELD, "descripcion", "Descripcion mailing", true, false, 250 );
    this.addFixedItem( FIELD, "mail", "Mail", true, false, 250 );
    this.addFixedItem( FIELD, "tipo", "Tipo", true, true, 50 );
    this.addFixedItem( VIRTUAL, "descripcion_op", "Descripcion mailing", true, false, 250 );
  }
  /**
   * Returns the table name
   */
  public String GetTable() { return "BSP_PERSONA"; }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public final static String CLIENTE = "CLIENTE";
	public final static String VENDEDOR = "VENDEDOR";
	public final static String CCOSTO = "CCOSTO";
	public final static String SUCURSAL = "Sucursal";
	public final static String IATA = "Iata";

  
  static JMap<String,String> gTipos;
  public static JMap<String,String> getTipos() throws Exception {
  	if (gTipos!=null) return gTipos;
  	gTipos=JCollectionFactory.createMap();
  	gTipos.addElement(CLIENTE, "Clientes");
  	gTipos.addElement(VENDEDOR, "Vendedor");
  	gTipos.addElement(CCOSTO, "Centro de Costo");
  	gTipos.addElement(SUCURSAL, "Sucursal");
  	gTipos.addElement(IATA, "IATA");
  	return gTipos;
  }
 
  
  public static void addPersona(String type,String company,String codigo) throws Exception {
  	BizBSPPersona p = new BizBSPPersona();
  	p.dontThrowException(true);
  	if (p.read(type,company,codigo)) return;
  	p.setTipo(type);
  	p.setCompany(company);
  	p.setCodigo(codigo);
  	p.processInsert();
  }

  /**
   * Default read() method
   */
  public boolean read( long id ) throws Exception { 
    addFilter( "id",  id ); 
    return read(); 
  } 
  
  public boolean read( String type,String company, String codigo ) throws Exception { 
  	addFilter( "tipo",  type ); 
    addFilter( "company",  company ); 
    addFilter( "codigo",  codigo ); 
    return read(); 
  } 
  
  public void attachRelationMap(JRelations rels) throws Exception {
  	rels.setSourceWinsClass(GuiBSPPersonas.class.getName());
  	rels.hideField("company");
  	rels.hideField("id");
		super.attachRelationMap(rels);

  }
}
