package  pss.bsp.regions;

import pss.bsp.regions.detalle.BizRegionDetail;
import pss.common.customList.config.relation.JRelation;
import pss.common.customList.config.relation.JRelations;
import pss.common.regions.divitions.BizPaisLista;
import pss.core.services.JExec;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;

public class BizRegion extends JRecord {

  private JLong  pId = new JLong();
  private JString pCompany = new JString();
  private JString pRegion = new JString();


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
  public void setRegion(String zValue) throws Exception {    pRegion.setValue(zValue);  }
  public String getRegion() throws Exception {     return pRegion.getValue();  }
  public boolean isNullRegion() throws Exception { return  pRegion.isNull(); } 
  public void setNullRegion() throws Exception {  pRegion.setNull(); } 


  /**
   * Constructor de la Clase
   */
  public BizRegion() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "id", pId );
    this.addItem( "company", pCompany );
    this.addItem( "descripcion", pRegion );
  }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "id", "ID", false, false, 18 );
    this.addFixedItem( KEY, "company", "Compañia", true, true, 50 );
    this.addFixedItem( FIELD, "descripcion", "Descripción region", true, true, 200 );
  }
  /**
   * Returns the table name
   */
  public String GetTable() { return "BSP_REGION"; }


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
    addFilter( "company", company ); 
    addFilter( "id",  zId ); 
    return read(); 
  } 
  public boolean read( String company,String  zRegion ) throws Exception { 
    addFilter( "company", company ); 
    addFilter( "descripcion",  zRegion ); 
    return read(); 
  } 
	public void execProcessAddMiembro(final BizPaisLista pais) throws Exception {
		JExec oExec = new JExec(this, "processAddMiembro") {

			@Override
			public void Do() throws Exception {
				processAddMiembro(pais);
			}
		};
		oExec.execute();
	}
  
  public void processAddMiembro(BizPaisLista pais) throws Exception {
  	BizRegionDetail d = new BizRegionDetail();
  	d.dontThrowException(true);
  	if (d.read(pais.GetPais()))	return;
  	d.setCompany(getCompany());
  	d.setIdRegion(getId());
  	d.setPais(pais.GetPais());
  	d.processInsert();
  }
  

	@Override
	public void processDelete() throws Exception {

		JRecords<BizRegionDetail> oRegions=new JRecords<BizRegionDetail>(BizRegionDetail.class);
		oRegions.addFilter("id_region", getId());
		oRegions.processDeleteAll();

		super.processDelete();
	}

	public BizRegion processClon(BizRegion newDoc) throws Exception {
		newDoc.insert();
		newDoc.setId(newDoc.getIdentity("id"));
		JRecords<BizRegionDetail> ampls = new JRecords<BizRegionDetail>(BizRegionDetail.class);
		ampls.addFilter("company", getCompany());
		ampls.addFilter("id_region", getId());
		ampls.toStatic();
		while (ampls.nextRecord()) {
			BizRegionDetail ampl = ampls.getRecord();
			BizRegionDetail na = new BizRegionDetail();
			na.copyProperties(ampl);
			na.setCompany(newDoc.getCompany());
			na.setIdRegion(newDoc.getId());
			na.processInsert();
		}

		return newDoc;
	}
	
	public static void processIgualarParent(String parentCompany,String company) throws Exception {
		JRecords<BizRegion> ampls = new JRecords<BizRegion>(BizRegion.class);
		ampls.addFilter("company", parentCompany);
		ampls.toStatic();
		String updated = ""; 
		while (ampls.nextRecord()) {
			BizRegion ampl= ampls.getRecord();
			BizRegion na = new BizRegion();
			na.dontThrowException(true);
			boolean find=na.read(company,ampl.getRegion());
			na.copyNotKeysProperties(ampl);
			na.setCompany(company);
			if (find) na.update();
			else 	na.processInsert();
			updated += (updated.equals("")?"":", ")+na.getId();
			
			JRecords<BizRegionDetail> amplsD = new JRecords<BizRegionDetail>(BizRegionDetail.class);
			amplsD.addFilter("company", ampl.getCompany());
			amplsD.addFilter("id_region", ampl.getId());
			amplsD.toStatic();
			String updatedD = ""; 
			while (amplsD.nextRecord()) {
				BizRegionDetail amplD= amplsD.getRecord();
				BizRegionDetail naD = new BizRegionDetail();
				naD.dontThrowException(true);
				boolean findD=naD.read(na.getCompany(),na.getId(),amplD.getPais());
				naD.copyNotKeysProperties(amplD);
				naD.setCompany(na.getCompany());
				naD.setIdRegion(na.getId());
				if (findD) naD.update();
				else 	naD.processInsert();
				updatedD += (updatedD.equals("")?"":", ")+naD.getId();
			}
			if (!updatedD.equals("")) {
				JRecords<BizRegionDetail> amplsDD = new JRecords<BizRegionDetail>(BizRegionDetail.class);
				amplsDD.addFilter("company", na.getCompany());
				amplsDD.addFilter("id_region", na.getId());
				amplsDD.addFilter("id", "("+updatedD+")","not in");
				amplsDD.processDeleteAll();
			}
		}
		if (!updated.equals("")) {
			JRecords<BizRegion> amplsD = new JRecords<BizRegion>(BizRegion.class);
			amplsD.addFilter("company", company);
			amplsD.addFilter("id", "("+updated+")","not in");
			amplsD.processDeleteAll();
		}
	}
	@Override
	public void attachRelationMap(JRelations rels) throws Exception {
  	rels.setSourceWinsClass("pss.bsp.regions.GuiRegion");
  	
		JRelation r=rels.addRelationChild(50, "Miembros", BizRegionDetail.class);
  	r.addJoin("BSP_REGION.id", "BSP_REGION_DETAIL.id_region");
  	r.setAlias("members");

		super.attachRelationMap(rels);
	}

 
}
