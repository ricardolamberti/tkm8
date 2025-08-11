package  pss.bsp.familia;

import pss.bsp.contrato.BizContrato;
import pss.bsp.contrato.detalle.BizDetalle;
import pss.bsp.contrato.logica.ILogicaContrato;
import pss.bsp.familia.detalle.BizFamiliaDetail;
import pss.common.customList.config.relation.JRelations;
import pss.common.regions.company.BizCompany;
import pss.core.services.JExec;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.JDateTools;
import pss.core.tools.collections.JIterator;
import pss.tourism.pnr.BizPNRSegmentoAereo;
import pss.tourism.pnr.BizPNRTicket;

public class BizFamilia extends JRecord {

  private JLong  pId = new JLong();
  private JString pCompany = new JString();
  private JString pClase = new JString();


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


  /**
   * Constructor de la Clase
   */
  public BizFamilia() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "company", pCompany );
    this.addItem( "id", pId );
    this.addItem( "descripcion", pClase );
  }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "id", "ID", false, false, 18 );
    this.addFixedItem( KEY, "company", "Compañia", true, true, 50 );
    this.addFixedItem( FIELD, "descripcion", "Descripción familia", true, true, 200 );
  }
  /**
   * Returns the table name
   */
  public String GetTable() { return "BSP_FAMILIA"; }


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

	
	public JRecords<BizFamiliaDetail> getDetails() throws Exception {
		JRecords<BizFamiliaDetail> r=new JRecords<BizFamiliaDetail>(BizFamiliaDetail.class);
		r.addFilter("id_familia", this.getId());
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
  	rels.setSourceWinsClass("pss.bsp.familia.GuiFamilias");
//  	
  	//rels.hideField("company");
  	rels.hideField("id");
  	rels.hideField("descripcion");

//		JRelation r=rels.addRelationChild(50, "Miembros", BizRegionDetail.class);
//  	r.addJoin("BSP_REGION.id", "BSP_REGION_DETAIL.id_region");
//  	r.setAlias("members");

		super.attachRelationMap(rels);
	}

	static public BizFamiliaDetail getTipoFamilia(String company,String carrier,String tipo) throws Exception {
		BizFamiliaDetail cl = new BizFamiliaDetail();
		cl.dontThrowException(true);
		cl.addFilter("company", company);
		cl.addFilter("aerolinea", carrier);
		cl.addFixedFilter(" position('"+tipo+"' in letra) <>0 ");
		if (!cl.read()) {
			return null;
		}
		BizFamilia c = new BizFamilia();
		c.dontThrowException(true);
		c.addFilter("company", company);
		c.addFilter("id", cl.getIdFamilia());
		if (!c.read()) 
			return null;
	  cl.setObjFamilia(c);
		return cl;
		
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
				BizPNRTicket.processReprocessAllTicket(getCompany());
			}
		};
		oExec.execute();
	}


	
	public BizFamilia processClon(BizFamilia newDoc) throws Exception {
		newDoc.insert();
		newDoc.setId(newDoc.getIdentity("id"));
		JRecords<BizFamiliaDetail> ampls = new JRecords<BizFamiliaDetail>(BizFamiliaDetail.class);
		ampls.addFilter("company", getCompany());
		ampls.addFilter("id_familia", getId());
		ampls.toStatic();
		while (ampls.nextRecord()) {
			BizFamiliaDetail ampl = ampls.getRecord();
			BizFamiliaDetail na = new BizFamiliaDetail();
			na.copyProperties(ampl);
			na.setCompany(newDoc.getCompany());
			na.setIdFamilia(newDoc.getId());
			na.setNullToId();
			
			na.processInsert();
		}

		return newDoc;
	}
 
	public static void processIgualarParent(String parentCompany,String company) throws Exception {
		JRecords<BizFamilia> ampls = new JRecords<BizFamilia>(BizFamilia.class);
		ampls.addFilter("company", parentCompany);
		ampls.toStatic();
		String updated = ""; 
		while (ampls.nextRecord()) {
			BizFamilia ampl= ampls.getRecord();
			BizFamilia na = new BizFamilia();
			na.dontThrowException(true);
			boolean find=na.read(company,ampl.getDescripcion());
			na.copyNotKeysProperties(ampl);
			na.setCompany(company);
			if (find) na.update();
			else 	na.processInsert();
			updated += (updated.equals("")?"":", ")+na.getId();
			
			JRecords<BizFamiliaDetail> amplsD = new JRecords<BizFamiliaDetail>(BizFamiliaDetail.class);
			amplsD.addFilter("company", ampl.getCompany());
			amplsD.addFilter("id_familia", ampl.getId());
			amplsD.toStatic();
			String updatedD = ""; 
			while (amplsD.nextRecord()) {
				BizFamiliaDetail amplD= amplsD.getRecord();
				BizFamiliaDetail naD = new BizFamiliaDetail();
				naD.dontThrowException(true);
				boolean findD=naD.read(na.getCompany(),na.getId(),amplD.getCarrier(),amplD.getLetra());
				naD.copyNotKeysProperties(amplD);
				naD.setCompany(na.getCompany());
				naD.setIdFamilia(na.getId());
				if (findD) naD.update();
				else 	naD.processInsert();
				updatedD += (updatedD.equals("")?"":", ")+naD.getId();
			}
			if (!updatedD.equals("")) {
				JRecords<BizFamiliaDetail> amplsDD = new JRecords<BizFamiliaDetail>(BizFamiliaDetail.class);
				amplsDD.addFilter("company", na.getCompany());
				amplsDD.addFilter("id_familia", na.getId());
				amplsDD.addFilter("id", "("+updatedD+")","not in");
				amplsDD.processDeleteAll();
			}
		}
		if (!updated.equals("")) {
			JRecords<BizFamilia> amplsD = new JRecords<BizFamilia>(BizFamilia.class);
			amplsD.addFilter("company", company);
			amplsD.addFilter("id", "("+updated+")","not in");
			amplsD.processDeleteAll();
		}
	}
	

	public void execProcessCopiarOtraEmpresa(final BizCompany compania) throws Exception {
		JExec oExec = new JExec(this, "processCopiarOtraEmpresa") {

			@Override
			public void Do() throws Exception {
				processCopiarOtraEmpresa(compania);
			}
		};
		oExec.execute();
	}
	public BizFamilia processCopiarOtraEmpresa( BizCompany compania) throws Exception {
	
		BizFamilia na = new BizFamilia();
			
		na.copyProperties(this);
		na.pId.setNull();
		na.pCompany.setValue(compania.getCompany());
		this.processClon(na);
		return na;
	}
	


}
