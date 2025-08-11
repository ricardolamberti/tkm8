package  pss.bsp.hoteles.detalle;

import pss.bsp.hoteles.BizHotel;
import pss.common.customList.config.relation.JRelations;
import pss.core.services.fields.JBoolean;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizHotelDetail extends JRecord {

  private JLong pId = new JLong();
  private JLong pIdHotel = new JLong();
  private JString pCompany = new JString();
  private JString pDescripcion = new JString();
  private JBoolean  pExacto = new JBoolean();


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  public void setId(long zValue) throws Exception {    pId.setValue(zValue);  }
  public long getId() throws Exception {     return pId.getValue();  }
  public boolean isNullId() throws Exception { return  pId.isNull(); } 
  public void setNullToId() throws Exception {  pId.setNull(); } 
  public void setIdHotel(long zValue) throws Exception {    pIdHotel.setValue(zValue);  }
  public long getIdHotel() throws Exception {     return pIdHotel.getValue();  }
  public boolean isNullIdHotel() throws Exception { return  pIdHotel.isNull(); } 
  public void setNullToIdHotel() throws Exception {  pIdHotel.setNull(); } 
  public void setCompany(String zValue) throws Exception {    pCompany.setValue(zValue);  }
  public String getCompany() throws Exception {     return pCompany.getValue();  }
  public void setDescription(String zValue) throws Exception {    pDescripcion.setValue(zValue);  }
  public String getDescription() throws Exception {     return pDescripcion.getValue();  }

  public void setExacto(boolean zValue) throws Exception {    pExacto.setValue(zValue);  }
  public boolean getExacto() throws Exception {     return pExacto.getValue();  }


  /**
   * Constructor de la Clase
   */
  public BizHotelDetail() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "id", pId );
    this.addItem( "id_hotel", pIdHotel );
    this.addItem( "company", pCompany );
    this.addItem( "descripcion", pDescripcion );
    this.addItem( "exacto", pExacto );
  }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "id", "id", false, false, 18 );
    this.addFixedItem( KEY, "id_hotel", "id_hotel", true, true, 18 );
    this.addFixedItem( KEY, "company", "Compañia", true, true, 50 );
    this.addFixedItem( FIELD, "descripcion", "descripcion", true, true, 200 );
    this.addFixedItem( FIELD, "exacto", "exacto", true, false, 1 );
  }
  /**
   * Returns the table name
   */
  public String GetTable() { return "BSP_HOTEL_DETAIL"; }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public void attachRelationMap(JRelations rels) throws Exception {
  	rels.setSourceWinsClass("pss.bsp.hoteles.detalle.GuiHotelDetails");
  
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
    addFilter( "id_hotel",  idClase ); 
    return read(); 
  } 
  public boolean read( String company, long idClase,long id ) throws Exception { 
    addFilter( "company",  company ); 
    addFilter( "id",  id ); 
    addFilter( "id_hotel",  idClase ); 
    return read(); 
  } 

  
  BizHotel objHotel;
  public void setObjHotel(BizHotel zClase) throws Exception {
  	objHotel=zClase;
  }
  public BizHotel getObjHotel() throws Exception {
  	if (objHotel!=null) return objHotel;
  	BizHotel p = new BizHotel();
  	p.read(pIdHotel.getValue());
  	return objHotel=p;
  }
 }
