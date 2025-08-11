package  pss.bsp.clase;

import pss.bsp.bspBusiness.BizBSPCompany;
import pss.bsp.clase.detalle.BizClaseDetail;
import pss.bsp.familia.BizFamilia;
import pss.common.customList.config.customlist.BizCustomList;
import pss.common.customList.config.field.campo.BizCampo;
import pss.common.customList.config.relation.JRelations;
import pss.common.regions.company.BizCompany;
import pss.core.services.JExec;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.JDateTools;
import pss.core.tools.JTools;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;
import pss.tourism.pnr.BizPNRSegmentoAereo;
import pss.tourism.pnr.BizPNRTicket;

public class BizClase extends JRecord {

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
  public BizClase() throws Exception {
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
    this.addFixedItem( FIELD, "descripcion", "Descripción clase", true, true, 200 );
  }
  /**
   * Returns the table name
   */
  public String GetTable() { return "BSP_CLASE"; }


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

	
	public JRecords<BizClaseDetail> getDetails() throws Exception {
		JRecords<BizClaseDetail> r=new JRecords<BizClaseDetail>(BizClaseDetail.class);
		r.addFilter("id_clase", this.getId());
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
  	rels.setSourceWinsClass("pss.bsp.clase.GuiClases");
//  	
  	//rels.hideField("company");
  	rels.hideField("id");
  	rels.hideField("descripcion");

//		JRelation r=rels.addRelationChild(50, "Miembros", BizRegionDetail.class);
//  	r.addJoin("BSP_REGION.id", "BSP_REGION_DETAIL.id_region");
//  	r.setAlias("members");

		super.attachRelationMap(rels);
	}

	static public BizClaseDetail getTipoClase(String company,String carrier,String tipo, boolean internacional) throws Exception {
		BizClaseDetail cl = new BizClaseDetail();
		cl.dontThrowException(true);
		cl.addFilter("company", company);
		cl.addFilter("aerolinea", carrier);
		cl.addFixedFilter(" position('"+tipo+"' in letra)<>0  and "+(internacional?" internacional = 'I' ": " internacional = 'D' " ));
		if (!cl.read()) {
			BizClaseDetail cl2 = new BizClaseDetail();
			cl2.dontThrowException(true);
			cl2.addFilter("company", company);
			cl2.addFilter("aerolinea", carrier);
			cl2.addFixedFilter(" position('"+tipo+"' in letra) <>0  and internacional is null ");
			if (!cl2.read()) {
				return null;
			}
			cl=cl2;
		}
		BizClase c = new BizClase();
		c.dontThrowException(true);
		c.addFilter("company", company);
		c.addFilter("id", cl.getIdClase());
		if (!c.read()) 
			return null;
	  cl.setObjClase(c);
		return cl;
		
	}
	static public String getTipoClaseDefault(String tipo) throws Exception {
		if (tipo.toUpperCase().indexOf("FPAR")!=-1) return "First class";
		if (tipo.toUpperCase().indexOf("CJDIZ")!=-1) return "Business class";
		if (tipo.toUpperCase().indexOf("GKLQRSTUVWXMHNYB")!=-1) return "Economy class";
		return "Economy class";
		
	}

static public long getPrioridadClaseDefault(String tipo) throws Exception {
	if (tipo.toUpperCase().indexOf("FPAR")!=-1) return 10;
	if (tipo.toUpperCase().indexOf("CJDIZ")!=-1) return 5;
	if (tipo.toUpperCase().indexOf("GIKLQRSTUVWXMHNYB")!=-1) return 1;
	return 1;
	
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

	
	public BizClase processClon(BizClase newDoc) throws Exception {
		newDoc.insert();
		newDoc.setId(newDoc.getIdentity("id"));
		JRecords<BizClaseDetail> ampls = new JRecords<BizClaseDetail>(BizClaseDetail.class);
		ampls.addFilter("company", getCompany());
		ampls.addFilter("id_clase", getId());
		ampls.toStatic();
		while (ampls.nextRecord()) {
			BizClaseDetail ampl = ampls.getRecord();
			BizClaseDetail na = new BizClaseDetail();
			na.copyProperties(ampl);
			na.setCompany(newDoc.getCompany());
			na.setIdClase(newDoc.getId());
			
			na.processInsert();
		}

		return newDoc;
	}
 
	public static void processIgualarParent(String parentCompany,String company) throws Exception {
		JRecords<BizClase> ampls = new JRecords<BizClase>(BizClase.class);
		ampls.addFilter("company", parentCompany);
		ampls.toStatic();
		String updated = ""; 
		while (ampls.nextRecord()) {
			BizClase ampl= ampls.getRecord();
			BizClase na = new BizClase();
			na.dontThrowException(true);
			boolean find=na.read(company,ampl.getDescripcion());
			na.copyNotKeysProperties(ampl);
			na.setCompany(company);
			if (find) na.update();
			else 	na.processInsert();
			updated += (updated.equals("")?"":", ")+na.getId();
			
			JRecords<BizClaseDetail> amplsD = new JRecords<BizClaseDetail>(BizClaseDetail.class);
			amplsD.addFilter("company", ampl.getCompany());
			amplsD.addFilter("id_clase", ampl.getId());
			amplsD.toStatic();
			String updatedD = ""; 
			while (amplsD.nextRecord()) {
				BizClaseDetail amplD= amplsD.getRecord();
				BizClaseDetail naD = new BizClaseDetail();
				naD.dontThrowException(true);
				boolean findD=naD.read(na.getCompany(),na.getId(),amplD.getCarrier(),amplD.getLetra(), amplD.getDomesticoInternacional());
				naD.copyNotKeysProperties(amplD);
				naD.setCompany(na.getCompany());
				naD.setIdClase(na.getId());
				if (findD) naD.update();
				else 	naD.processInsert();
				updatedD += (updatedD.equals("")?"":", ")+naD.getId();
			}
			if (!updatedD.equals("")) {
				JRecords<BizClaseDetail> amplsDD = new JRecords<BizClaseDetail>(BizClaseDetail.class);
				amplsDD.addFilter("company", na.getCompany());
				amplsDD.addFilter("id_clase", na.getId());
				amplsDD.addFilter("id", "("+updatedD+")","not in");
				amplsDD.processDeleteAll();
			}
		}
		if (!updated.equals("")) {
			JRecords<BizClase> amplsD = new JRecords<BizClase>(BizClase.class);
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
	public BizClase processCopiarOtraEmpresa( BizCompany compania) throws Exception {
	
		BizClase na = new BizClase();
			
		na.copyProperties(this);
		na.pId.setNull();
		na.pCompany.setValue(compania.getCompany());
		this.processClon(na);
		return na;
	}
	
}
