package  pss.bsp.familia.detalle;

import pss.bsp.familia.BizFamilia;
import pss.common.customList.config.relation.JRelations;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.tourism.carrier.BizCarrier;

public class BizFamiliaDetail extends JRecord {

  private JLong pId = new JLong();
  private JLong pIdFamilia = new JLong();
  private JString pCompany = new JString();
  private JString pCarrier = new JString();
  private JString pLetra = new JString();
  private JLong pPrioridad = new JLong();
  private JString pDescripcion = new JString() {
  	public void preset() throws Exception {
  		pDescripcion.setValue(getDescripcion());
  	};
  };


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  public void setId(long zValue) throws Exception {    pId.setValue(zValue);  }
  public long getId() throws Exception {     return pId.getValue();  }
  public boolean isNullId() throws Exception { return  pId.isNull(); } 
  public void setNullToId() throws Exception {  pId.setNull(); } 
  public void setIdFamilia(long zValue) throws Exception {    pIdFamilia.setValue(zValue);  }
  public long getIdFamilia() throws Exception {     return pIdFamilia.getValue();  }
  public boolean isNullIdFamilia() throws Exception { return  pIdFamilia.isNull(); } 
  public void setNullToIdFamilia() throws Exception {  pIdFamilia.setNull(); } 
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

  public String getDescripcion() throws Exception {     return getObjAerolinea().getDescription();  }

  /**
   * Constructor de la Clase
   */
  public BizFamiliaDetail() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "id", pId );
    this.addItem( "id_familia", pIdFamilia );
    this.addItem( "company", pCompany );
    this.addItem( "aerolinea", pCarrier );
    this.addItem( "letra", pLetra );
    this.addItem( "prioridad", pPrioridad );
    this.addItem( "descripcion", pDescripcion );
  }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "id", "id", false, false, 18 );
    this.addFixedItem( KEY, "id_familia", "id_familia", true, true, 18 );
    this.addFixedItem( KEY, "company", "Compañia", true, true, 50 );
    this.addFixedItem( FIELD, "aerolinea", "Id Aerolinea", true, true, 50 );
    this.addFixedItem( FIELD, "letra", "Letra", true, true, 250 );
    this.addFixedItem( FIELD, "prioridad", "Prioridad", true, true, 18 );
    this.addFixedItem( VIRTUAL, "descripcion", "Aerolinea", true, true, 200 );
  }
  /**
   * Returns the table name
   */
  public String GetTable() { return "BSP_FAMILIA_DETAIL"; }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public void attachRelationMap(JRelations rels) throws Exception {
  	rels.setSourceWinsClass("pss.bsp.familia.detalle.GuiFamiliaDetails");
  
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
    addFilter( "id_familia",  idClase ); 
    return read(); 
  } 
  public boolean read( String company, long idClase,long id ) throws Exception { 
    addFilter( "company",  company ); 
    addFilter( "id",  id ); 
    addFilter( "id_familia",  idClase ); 
    return read(); 
  } 
  public boolean read( String company, long idClase, String carrier,String letra ) throws Exception { 
    addFilter( "company",  company ); 
    addFilter( "letra",  letra ); 
    addFilter( "id_familia",  idClase ); 
    addFilter( "aerolinea",  carrier ); 
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
  BizFamilia objFamilia;
  public void setObjFamilia(BizFamilia zClase) throws Exception {
  	objFamilia=zClase;
  }
  public BizFamilia getObjFamilia() throws Exception {
  	if (objFamilia!=null) return objFamilia;
  	BizFamilia p = new BizFamilia();
  	p.read(pIdFamilia.getValue());
  	return objFamilia=p;
  }
 }
