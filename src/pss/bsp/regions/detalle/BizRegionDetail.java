package  pss.bsp.regions.detalle;

import pss.bsp.regions.BizRegion;
import pss.common.customList.config.relation.JRelation;
import pss.common.customList.config.relation.JRelations;
import pss.common.regions.divitions.BizPaisLista;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizRegionDetail extends JRecord {

  private JLong pId = new JLong();
  private JLong pIdRegion = new JLong();
  private JString pCompany = new JString();
  private JString pPais = new JString();
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
  public void setIdRegion(long zValue) throws Exception {    pIdRegion.setValue(zValue);  }
  public long getIdRegion() throws Exception {     return pIdRegion.getValue();  }
  public boolean isNullIdRegion() throws Exception { return  pIdRegion.isNull(); } 
  public void setNullToIdRegion() throws Exception {  pIdRegion.setNull(); } 
  public void setPais(String zValue) throws Exception {    pPais.setValue(zValue);  }
  public String getPais() throws Exception {     return pPais.getValue();  }
  public boolean isNullPais() throws Exception { return  pPais.isNull(); } 
  public void setNullToPais() throws Exception {  pPais.setNull(); } 
  public void setCompany(String zValue) throws Exception {    pCompany.setValue(zValue);  }
  public String getCompany() throws Exception {     return pCompany.getValue();  }
  public boolean isNullCompany() throws Exception { return  pCompany.isNull(); } 
  public void setNullToCompany() throws Exception {  pCompany.setNull(); } 

  public String getDescripcion() throws Exception {     return getObjPais().GetDescrip();  }

  /**
   * Constructor de la Clase
   */
  public BizRegionDetail() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "id", pId );
    this.addItem( "id_region", pIdRegion );
    this.addItem( "company", pCompany );
    this.addItem( "pais", pPais );
    this.addItem( "descripcion_pais", pDescripcion );
  }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "id", "id", false, false, 18 );
    this.addFixedItem( KEY, "id_region", "id_region", true, true, 18 );
    this.addFixedItem( KEY, "company", "Compañia", true, true, 50 );
    this.addFixedItem( FIELD, "pais", "Id pais", true, true, 50 );
    this.addFixedItem( VIRTUAL, "descripcion_pais", "País", true, true, 200 );
  }
  /**
   * Returns the table name
   */
  public String GetTable() { return "BSP_REGION_DETAIL"; }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public void attachRelationMap(JRelations rels) throws Exception {
  	rels.setSourceWinsClass("pss.bsp.regions.detalle.GuiRegionDetails");
  
  	JRelation r=rels.addRelationParent(49, "Region", BizRegion.class, "id_region", "id");
  	r.addJoin("BSP_REGION_DETAIL.id_region", "BSP_REGION.id");
  	r.addJoin("BSP_REGION_DETAIL.company", "BSP_REGION.company");
//  	r.setTypeJoin(JRelations.JOIN_LEFT);
		super.attachRelationMap(rels);
	}
  /**
   * Default read() method
   */
  public boolean read( long idRegion,long id ) throws Exception { 
    addFilter( "id",  id ); 
    addFilter( "id_region",  idRegion ); 
    return read(); 
  } 
  public boolean read( String company,long idRegion,long id ) throws Exception { 
    addFilter( "company",  company ); 
    addFilter( "id",  id ); 
    addFilter( "id_region",  idRegion ); 
    return read(); 
  } 
  public boolean read( String company,long idRegion,String pais ) throws Exception { 
    addFilter( "company",  company ); 
    addFilter( "pais",  pais ); 
    addFilter( "id_region",  idRegion ); 
    return read(); 
  } 
  public boolean read( String country ) throws Exception { 
    addFilter( "pais",  country ); 
    return read(); 
  }   
  BizPaisLista objPais;
  
  public BizPaisLista getObjPais() throws Exception {
  	BizPaisLista p = new BizPaisLista();
  	p.Read(getPais());
  	return objPais=p;
  }
}
