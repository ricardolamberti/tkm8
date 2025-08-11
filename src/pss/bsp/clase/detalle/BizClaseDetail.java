package pss.bsp.clase.detalle;

import pss.bsp.clase.BizClase;
import pss.common.customList.config.relation.JRelations;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;
import pss.core.win.JWins;
import pss.tourism.carrier.BizCarrier;
import pss.tourism.carrier.GuiCarriers;

public class BizClaseDetail extends JRecord {

  private JLong pId = new JLong();
  private JLong pIdClase = new JLong();
  private JString pCompany = new JString();
  private JString pCarrier = new JString();
  private JString pInternacional = new JString();
  private JString pLetra = new JString();
  private JLong pPrioridad = new JLong();
  private JString pDescripcion = new JString() {
  	public void preset() throws Exception {
  		pDescripcion.setValue(getDescripcion());
  	};
  };

  public static final String DOMESTICO = "D";
  public static final String INTERNACIONAL = "I";
  
  static JMap<String,String> estados;
  public static JMap<String,String> getEstados() throws Exception {
  	if (estados!=null) return estados;
  	JMap<String,String> maps = JCollectionFactory.createMap();
  	maps.addElement(DOMESTICO, "Domsstico");
  	maps.addElement(INTERNACIONAL, "Internacional");
  	return estados=maps;
  }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  public void setId(long zValue) throws Exception {    pId.setValue(zValue);  }
  public long getId() throws Exception {     return pId.getValue();  }
  public boolean isNullId() throws Exception { return  pId.isNull(); } 
  public void setNullToId() throws Exception {  pId.setNull(); } 
  public void setIdClase(long zValue) throws Exception {    pIdClase.setValue(zValue);  }
  public long getIdClase() throws Exception {     return pIdClase.getValue();  }
  public boolean isNullIdClase() throws Exception { return  pIdClase.isNull(); } 
  public void setNullToIdClase() throws Exception {  pIdClase.setNull(); } 
  public void setCarrier(String zValue) throws Exception {    pCarrier.setValue(zValue);  }
  public String getCarrier() throws Exception {     return pCarrier.getValue();  }
  public boolean isNullCarrier() throws Exception { return  pCarrier.isNull(); } 
  public void setNullToCarrier() throws Exception {  pCarrier.setNull(); } 
  public void setCompany(String zValue) throws Exception {    pCompany.setValue(zValue);  }
  public String getCompany() throws Exception {     return pCompany.getValue();  }
//  public boolean isNullCompany() throws Exception { return  pCompany.isNull(); } 
//  public void setNullToCompany() throws Exception {  pCompany.setNull(); } 
  public void setLetra(String zValue) throws Exception {    pLetra.setValue(zValue);  }
  public String getLetra() throws Exception {     return pLetra.getValue();  }
  public void setPrioridad(long zValue) throws Exception {    pPrioridad.setValue(zValue);  }
  public long getPrioridad() throws Exception {     return pPrioridad.getValue();  }
  public void setInternacional(String zValue) throws Exception {    pInternacional.setValue(zValue);  }
  public boolean isInternacional() throws Exception {     return pInternacional.getValue().equalsIgnoreCase(INTERNACIONAL);  }
  public boolean isDomestico() throws Exception {     return pInternacional.getValue().equalsIgnoreCase(DOMESTICO);  }
  public boolean isNeutro() throws Exception {     return pInternacional.isNull();  }
  public String getDomesticoInternacional() throws Exception {     return pInternacional.getValue();  }
  
  public String getDescripcion() throws Exception {     return getObjAerolinea().getDescription();  }

  /**
   * Constructor de la Clase
   */
  public BizClaseDetail() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "id", pId );
    this.addItem( "id_clase", pIdClase );
    this.addItem( "company", pCompany );
    this.addItem( "aerolinea", pCarrier );
    this.addItem( "letra", pLetra );
    this.addItem( "prioridad", pPrioridad );
    this.addItem( "internacional", pInternacional );
    this.addItem( "descripcion", pDescripcion );
  }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "id", "id", false, false, 18 );
    this.addFixedItem( KEY, "id_clase", "id_clase", true, true, 18 );
    this.addFixedItem( KEY, "company", "Compañia", true, true, 50 );
    this.addFixedItem( FIELD, "aerolinea", "Id Aerolinea", true, true, 50 ).setControl(createControlWin(GuiCarriers.class,"carrier",null));
   
    this.addFixedItem( FIELD, "letra", "Letra", true, true, 50 );
    this.addFixedItem( FIELD, "prioridad", "Prioridad", true, true, 18 );
    this.addFixedItem( FIELD, "internacional", "Domestico/Internacional", true, false, 1 );
    this.addFixedItem( VIRTUAL, "descripcion", "Aerolinea", true, true, 200 );
  }
  
  @Override
  public void createControlProperties() throws Exception {
  	this.addControlsProperty("internacional", createControlCombo(JWins.createVirtualWinsFromMap(getEstados()),null, null) );
  	super.createControlProperties();
  }
  /**
   * Returns the table name
   */
  public String GetTable() { return "BSP_CLASE_DETAIL"; }



//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public void attachRelationMap(JRelations rels) throws Exception {
  	rels.setSourceWinsClass("pss.bsp.clase.detalle.GuiClaseDetails");
  
//  	JRelation r=rels.addRelationParent(49, "Region", BizRegion.class, "id_region", "id");
//  	r.addJoin("BSP_REGION_DETAIL.id_region", "BSP_REGION.id");
//  	r.addJoin("BSP_REGION_DETAIL.company", "BSP_REGION.company");
//  	r.setTypeJoin(JRelations.JOIN_LEFT);
		super.attachRelationMap(rels);
	}
  /**
   * Default read() method
   */
  public boolean read( long idClase,long id ) throws Exception { 
    addFilter( "id",  id ); 
    addFilter( "id_clase",  idClase ); 
    return read(); 
  } 
  public boolean read( String company, long idClase,long id ) throws Exception { 
    addFilter( "company",  company ); 
    addFilter( "id",  id ); 
    addFilter( "id_clase",  idClase ); 
    return read(); 
  } 
  public boolean read( String company, long idClase,String carrier,String letra, String internacional ) throws Exception { 
    addFilter( "company",  company ); 
    addFilter( "letra",  letra ); 
    addFilter( "aerolinea",  carrier ); 
    addFilter( "id_clase",  idClase ); 
    addFilter( "internacional",  internacional ); 
    return read(); 
  } 
  public boolean read( String country ) throws Exception { 
    addFilter( "aerolinea",  country ); 
    return read(); 
  }   
  BizCarrier objCarrier;
  
  public BizCarrier getObjAerolinea() throws Exception {
  	BizCarrier p = new BizCarrier();
  	p.Read(pCarrier.getValue());
  	return objCarrier=p;
  }
  BizClase objClase;
  public void setObjClase(BizClase zClase) throws Exception {
  	objClase=zClase;
  }
  public BizClase getObjClase() throws Exception {
  	if (objClase!=null) return objClase;
  	BizClase p = new BizClase();
  	p.read(pIdClase.getValue());
  	return objClase=p;
  }
 }
