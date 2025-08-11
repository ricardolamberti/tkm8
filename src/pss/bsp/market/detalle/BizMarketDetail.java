package  pss.bsp.market.detalle;

import pss.bsp.market.BizMarket;
import pss.common.customList.config.relation.JRelations;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.tourism.carrier.BizCarrier;

public class BizMarketDetail extends JRecord {

  private JLong pId = new JLong();
  private JLong pIdMarket = new JLong();
  private JString pCompany = new JString();
  private JString pRuta = new JString();
  private JLong pPrioridad = new JLong();



//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  public void setId(long zValue) throws Exception {    pId.setValue(zValue);  }
  public long getId() throws Exception {     return pId.getValue();  }
  public boolean isNullId() throws Exception { return  pId.isNull(); } 
  public void setNullToId() throws Exception {  pId.setNull(); } 
  public void setIdMarket(long zValue) throws Exception {    pIdMarket.setValue(zValue);  }
  public long getIdMarket() throws Exception {     return pIdMarket.getValue();  }
  public boolean isNullIdMarket() throws Exception { return  pIdMarket.isNull(); } 
  public void setNullToIdMarket() throws Exception {  pIdMarket.setNull(); } 
  public void setRuta(String zValue) throws Exception {    pRuta.setValue(zValue);  }
  public String getRuta() throws Exception {     return pRuta.getValue();  }
  public boolean isNullRuta() throws Exception { return  pRuta.isNull(); } 
  public void setNullToRuta() throws Exception {  pRuta.setNull(); } 
  public void setCompany(String zValue) throws Exception {    pCompany.setValue(zValue);  }
  public String getCompany() throws Exception {     return pCompany.getValue();  }
//  public boolean isNullCompany() throws Exception { return  pCompany.isNull(); } 
//  public void setNullToCompany() throws Exception {  pCompany.setNull(); } 
  public void setPrioridad(long zValue) throws Exception {    pPrioridad.setValue(zValue);  }
  public long getPrioridad() throws Exception {     return pPrioridad.getValue();  }


  /**
   * Constructor de la Clase
   */
  public BizMarketDetail() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "id", pId );
    this.addItem( "id_market", pIdMarket );
    this.addItem( "company", pCompany );
    this.addItem( "ruta", pRuta );
    this.addItem( "prioridad", pPrioridad );
  }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "id", "id", false, false, 18 );
    this.addFixedItem( KEY, "id_market", "id_market", true, true, 18 );
    this.addFixedItem( KEY, "company", "Compañia", true, true, 50 );
    this.addFixedItem( FIELD, "ruta", "ruta", true, true, 30000 );
    this.addFixedItem( FIELD, "prioridad", "Prioridad", true, true, 18 );
  }
  /**
   * Returns the table name
   */
  public String GetTable() { return "BSP_MARKET_DETAIL"; }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public void attachRelationMap(JRelations rels) throws Exception {
  	rels.setSourceWinsClass("pss.bsp.market.detalle.GuiMarketDetails");
  
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
    addFilter( "id_market",  idClase ); 
    return read(); 
  } 
  public boolean read( String company, long idClase,long id ) throws Exception { 
    addFilter( "company",  company ); 
    addFilter( "id",  id ); 
    addFilter( "id_market",  idClase ); 
    return read(); 
  } 

//  public boolean read( String ruta ) throws Exception { 
//    addFilter( "ruta",  ruta ); 
//    return read(); 
//  }   

  BizMarket objMarket;
  public void setObjMarket(BizMarket zClase) throws Exception {
  	objMarket=zClase;
  }
  public BizMarket getObjMarket() throws Exception {
  	if (objMarket!=null) return objMarket;
  	BizMarket p = new BizMarket();
  	p.read(pIdMarket.getValue());
  	return objMarket=p;
  }
 }
