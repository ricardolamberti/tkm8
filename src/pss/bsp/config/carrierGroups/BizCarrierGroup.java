package  pss.bsp.config.carrierGroups;

import pss.bsp.config.carrierGroups.detalle.BizCarrierGroupDetail;
import pss.common.customList.config.relation.JRelation;
import pss.common.customList.config.relation.JRelations;
import pss.core.services.JExec;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.collections.JIterator;
import pss.tourism.carrier.BizCarrier;

public class BizCarrierGroup extends JRecord {

  private JLong  pIdGroup = new JLong();
  private JString pCompany = new JString();
  private JString pDescripcion = new JString();


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  public void setIdGroup(long zValue) throws Exception {    pIdGroup.setValue(zValue);  }
  public long getIdGroup() throws Exception {     return pIdGroup.getValue();  }
  public boolean isNullIdGroup() throws Exception { return  pIdGroup.isNull(); } 
  public void setNullToIdGroup() throws Exception {  pIdGroup.setNull(); } 
  public void setCompany(String zValue) throws Exception {    pCompany.setValue(zValue);  }
  public String getCompany() throws Exception {     return pCompany.getValue();  }
  public boolean isNullCompany() throws Exception { return  pCompany.isNull(); } 
  public void setNullToCompany() throws Exception {  pCompany.setNull(); } 
  public void setDescripcion(String zValue) throws Exception {    pDescripcion.setValue(zValue);  }
  public String getDescripcion() throws Exception {     return pDescripcion.getValue();  }

	public String getKeyField() throws Exception {
		return "id_group";
	}

  /**
   * Constructor de la Clase
   */
  public BizCarrierGroup() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "id_group", pIdGroup );
    this.addItem( "company", pCompany );
    this.addItem( "descripcion", pDescripcion );
  }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "id_group", "Grupo Líneas Aereas", true, false, 18 );
    this.addFixedItem( FIELD, "company", "Compañia", true, true, 15 );
    this.addFixedItem( FIELD, "descripcion", "Descr.Grupo Aerolineas", true, true, 200 );
  }
  /**
   * Returns the table name
   */
  public String GetTable() { return "BSP_CARRIER_GROUP"; }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  /**
   * Default read() method
   */
  public boolean read( String company, long zId ) throws Exception { 
  	addFilter( "company",  company); 
  	addFilter( "id_group",  zId ); 
    return read(); 
  } 
  
  public boolean read(String company, String descripcion ) throws Exception { 
    addFilter( "company",  company ); 
    addFilter( "descripcion",  descripcion ); 
    return read(); 
  } 


  public void execProcessLineas(final JRecords recs) throws Exception {
		JExec oExec = new JExec(null,null) {
			public void Do() throws Exception {
				processLineas(recs);
			}
		};
		oExec.execute();
	}

  public void processLineas(JRecords recs) throws Exception {
		JIterator<BizCarrier> iter = recs.getStaticIterator();
		while (iter.hasMoreElements()) {
			BizCarrier c = (BizCarrier) iter.nextElement();
			this.processLinea(c);
		}
	}
  
  public void processLinea(BizCarrier carrier) throws Exception {
  	BizCarrierGroupDetail d = new BizCarrierGroupDetail();
  	d.addFilter("company", this.getCompany());
  	d.addFilter("id_group", this.getIdGroup());
  	d.addFilter("carrier", carrier.getCarrier());
  	d.dontThrowException(true);
  	if (d.read())	return;
  	d.setCompany(this.getCompany());
  	d.setIdGroup(this.getIdGroup());
  	d.setCarrier(carrier.getCarrier());
  	d.processInsert();
  }
  

	@Override
	public void processDelete() throws Exception {

		this.getDetails().processDeleteAll();

		super.processDelete();
	}
	
	@Override
	public void processInsert() throws Exception {
		if (this.pIdGroup.isNull()) {
			BizCarrierGroup max =  new BizCarrierGroup();
			this.pIdGroup.setValue(max.SelectMaxLong("id_group")+1);
		}
			
		super.processInsert();
	}

	public JRecords<BizCarrierGroupDetail> getDetails() throws Exception {
		JRecords<BizCarrierGroupDetail> recs=new JRecords<BizCarrierGroupDetail>(BizCarrierGroupDetail.class);
		recs.addFilter("id_group", getIdGroup());
		recs.readAll();
		return recs;
	}

	@Override
	public void attachRelationMap(JRelations rels) throws Exception {
  	rels.setSourceWinsClass("pss.bsp.config.carrierGroups.GuiCarrierGroups");
  	
  	JRelation r;
  	r = rels.addRelationForce(10, "restriccion usuario");
   	r.addFilter(" (bsp_carrier_group.company in (COMPANY_CUSTOMLIST)) ");

//		JRelation r=rels.addRelationChild(50, "Detalles", BizCarrierGroupDetail.class);
//  	r.addJoin("BSP_CARRIER_GROUP.id_group", "carrier_groups.id_group");
//  	r.setAlias("carrier_groups");

  	//rels.hideField("company");
	}

	public String getDescripField() throws Exception {
		return "descripcion";
	}

	public BizCarrierGroup processClon(BizCarrierGroup newDoc) throws Exception {
		newDoc.setNullToIdGroup();
		newDoc.processInsert();
		JRecords<BizCarrierGroupDetail> ampls = new JRecords<BizCarrierGroupDetail>(BizCarrierGroupDetail.class);
		ampls.addFilter("company", getCompany());
		ampls.addFilter("id_group", getIdGroup());
		ampls.toStatic();
		while (ampls.nextRecord()) {
			BizCarrierGroupDetail ampl = ampls.getRecord();
			BizCarrierGroupDetail na = new BizCarrierGroupDetail();
			na.copyProperties(ampl);
			na.setCompany(newDoc.getCompany());
			na.setIdGroup(newDoc.getIdGroup());
			
			na.processInsert();
		}

		return newDoc;
	}
 
	public static void processIgualarParent(String parentCompany,String company) throws Exception {
		JRecords<BizCarrierGroup> ampls = new JRecords<BizCarrierGroup>(BizCarrierGroup.class);
		ampls.addFilter("company", parentCompany);
		ampls.toStatic();
		String updated = ""; 
		while (ampls.nextRecord()) {
			BizCarrierGroup ampl= ampls.getRecord();
			BizCarrierGroup na = new BizCarrierGroup();
			na.dontThrowException(true);
			boolean find=na.read(company,ampl.getDescripcion());
			na.copyNotKeysProperties(ampl);
			na.setCompany(company);
			if (find) na.update();
			else 	na.processInsert();
			
			updated += (updated.equals("")?"":", ")+na.getIdGroup();
			
			JRecords<BizCarrierGroupDetail> amplsD = new JRecords<BizCarrierGroupDetail>(BizCarrierGroupDetail.class);
			amplsD.addFilter("company", ampl.getCompany());
			amplsD.addFilter("id_group", ampl.getIdGroup());
			amplsD.toStatic();
			String updatedD = ""; 
			while (amplsD.nextRecord()) {
				BizCarrierGroupDetail amplD= amplsD.getRecord();
				BizCarrierGroupDetail naD = new BizCarrierGroupDetail();
				naD.dontThrowException(true);
				boolean findD=naD.read(na.getCompany(),na.getIdGroup(),amplD.getCarrier());
				naD.copyNotKeysProperties(amplD);
				naD.setCompany(na.getCompany());
				naD.setIdGroup(na.getIdGroup());
				naD.setCarrier(amplD.getCarrier());
				if (findD) naD.update();
				else 	naD.processInsert();
				updatedD += (updatedD.equals("")?"":", ")+"'"+naD.getCarrier()+"'";
			}
			if (!updatedD.equals("")) {
				JRecords<BizCarrierGroupDetail> amplsDD = new JRecords<BizCarrierGroupDetail>(BizCarrierGroupDetail.class);
				amplsDD.addFilter("company", na.getCompany());
				amplsDD.addFilter("id_group", na.getIdGroup());
				amplsDD.addFilter("carrier", "("+updatedD+")","not in");
				amplsDD.processDeleteAll();
			}
		}
		if (!updated.equals("")) {
			JRecords<BizCarrierGroup> amplsD = new JRecords<BizCarrierGroup>(BizCarrierGroup.class);
			amplsD.addFilter("company", company);
			amplsD.addFilter("id_group", "("+updated+")","not in");
			amplsD.processDeleteAll();
		}
	}
}
