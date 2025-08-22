package  pss.bsp.hoteles;

import pss.bsp.hoteles.detalle.BizHotelDetail;
import pss.common.customList.config.relation.JRelations;
import pss.core.services.JExec;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.tourism.pnr.BizPNROtro;
public class BizHotel extends JRecord {

  private JLong  pId = new JLong();
  private JString pCompany = new JString();
  private JString pClase = new JString();
  private JLong  pOrden = new JLong();
  

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  public void setId(long zValue) throws Exception {    pId.setValue(zValue);  }
  public long getId() throws Exception {     return pId.getValue();  }
  public boolean isNullId() throws Exception { return  pId.isNull(); } 
  public void setNullToId() throws Exception {  pId.setNull(); } 
  public void setCompany(String zValue) throws Exception {    pCompany.setValue(zValue);  }
  public String getCompany() throws Exception {     return pCompany.getValue();  }
  public boolean isNullCompany() throws Exception { return  pCompany.isNull(); } 
  public void setNullToCompany() throws Exception {  pCompany.setNull(); } 
  public void setDescripcion(String zValue) throws Exception {    pClase.setValue(zValue);  }
  public String getDescripcion() throws Exception {     return pClase.getValue();  }
  public boolean isNullDescripcion() throws Exception { return  pClase.isNull(); } 
  public void setNullDescripcion() throws Exception {  pClase.setNull(); } 
  public void setOrden(long zValue) throws Exception {    pOrden.setValue(zValue);  }
  public long getOrden() throws Exception {     return pOrden.getValue();  }


  /**
   * Constructor de la Clase
   */
  public BizHotel() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "company", pCompany );
    this.addItem( "id", pId );
    this.addItem( "descripcion", pClase );
    this.addItem( "orden", pOrden );
  }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "id", "ID", false, false, 18 );
    this.addFixedItem( KEY, "company", "Compañia", true, true, 50 );
    this.addFixedItem( FIELD, "descripcion", "Descr.grupo hotel", true, true, 200 );
    this.addFixedItem( FIELD, "orden", "orden", true, false, 18 );
  }
  /**
   * Returns the table name
   */
  public String GetTable() { return "BSP_HOTEL"; }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  /**
   * Default read() method
   */
  public boolean read( long zId ) throws Exception { 
    addFilter( "id",  zId ); 
    return read(); 
  } 
  public boolean read( String company,long zId ) throws Exception { 
    addFilter( "company",  company ); 
    addFilter( "id",  zId ); 
    return read(); 
  } 
  
  public boolean read(String company, String descripcion ) throws Exception { 
    addFilter( "company",  company ); 
    addFilter( "descripcion",  descripcion ); 
    return read(); 
  } 
//	public void execProcessAddMiembro(final BizPaisLista pais) throws Exception {
//		JExec oExec = new JExec(this, "processAddMiembro") {
//
//			@Override
//			public void Do() throws Exception {
//				processAddMiembro(pais);
//			}
//		};
//		oExec.execute();
//	}
//  
//  public void processAddMiembro(BizPaisLista pais) throws Exception {
//  	BizRegionDetail d = new BizRegionDetail();
//  	d.dontThrowException(true);
//  	if (d.read(pais.GetPais()))	return;
//  	d.setCompany(getCompany());
//  	d.setIdRegion(getId());
//  	d.setPais(pais.GetPais());
//  	d.processInsert();
//  }
//  


	@Override
	public void processDelete() throws Exception {
		this.getDetails().processDeleteAll();

		super.processDelete();
	}

	
	public JRecords<BizHotelDetail> getDetails() throws Exception {
		JRecords<BizHotelDetail> r=new JRecords<BizHotelDetail>(BizHotelDetail.class);
		r.addFilter("id_hotel", this.getId());
		r.readAll();
		return r;
	}
		
	//
//	public BizClase processClon(BizClase newDoc) throws Exception {
//		newDoc.insert();
//		newDoc.setId(newDoc.getIdentity("id"));
//		JRecords<BizRegionDetail> ampls = new JRecords<BizRegionDetail>(BizRegionDetail.class);
//		ampls.addFilter("company", getCompany());
//		ampls.addFilter("id_region", getId());
//		ampls.toStatic();
//		while (ampls.nextRecord()) {
//			BizRegionDetail ampl = ampls.getRecord();
//			BizRegionDetail na = new BizRegionDetail();
//			na.copyProperties(ampl);
//			na.setCompany(newDoc.getCompany());
//			na.setIdRegion(newDoc.getId());
//			na.processInsert();
//		}
//
//		return newDoc;
//	}
	@Override
	public void attachRelationMap(JRelations rels) throws Exception {
  	rels.setSourceWinsClass("pss.bsp.hoteles.GuiHotels");
//  	
  	//rels.hideField("company");
  	rels.hideField("id");
//  	rels.hideField("descripcion");

//		JRelation r=rels.addRelationChild(50, "Miembros", BizRegionDetail.class);
//  	r.addJoin("BSP_REGION.id", "BSP_REGION_DETAIL.id_region");
//  	r.setAlias("members");

		super.attachRelationMap(rels);
	}

	


	@Override
	public void processInsert() throws Exception {
		super.processInsert();
		setId(getIdentity("id"));
	}

	public void execReprocessAllTicket() throws Exception {
		JExec oExec = new JExec(this, "processReprocessAllTicket") {

			@Override
			public void Do() throws Exception {
				BizPNROtro.processReprocessAllTicket(getCompany());
			}
		};
		oExec.execute();
	}


	
	public BizHotel processClon(BizHotel newDoc) throws Exception {
		newDoc.insert();
		newDoc.setId(newDoc.getIdentity("id"));
		JRecords<BizHotelDetail> ampls = new JRecords<BizHotelDetail>(BizHotelDetail.class);
		ampls.addFilter("company", getCompany());
		ampls.addFilter("id_hotel", getId());
		ampls.toStatic();
		while (ampls.nextRecord()) {
			BizHotelDetail ampl = ampls.getRecord();
			BizHotelDetail na = new BizHotelDetail();
			na.copyProperties(ampl);
			na.setCompany(newDoc.getCompany());
			na.setIdHotel(newDoc.getId());
			
			na.processInsert();
		}

		return newDoc;
	}
 

}
