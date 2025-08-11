package  pss.bsp.config.airportGroups;

import pss.bsp.bspBusiness.BizBSPCompany;
import pss.bsp.clase.BizClase;
import pss.bsp.clase.detalle.BizClaseDetail;
import pss.bsp.config.airportGroups.detalle.BizAirportGroupDetail;
import pss.bsp.config.carrierGroups.BizCarrierGroup;
import pss.bsp.config.carrierGroups.detalle.BizCarrierGroupDetail;
import pss.common.customList.config.relation.JRelation;
import pss.common.customList.config.relation.JRelations;
import pss.common.security.BizUsuario;
import pss.core.services.JExec;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JObjBDs;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.collections.JIterator;
import pss.tourism.airports.BizAirport;
import pss.tourism.carrier.BizCarrier;

public class BizAirportGroup extends JRecord {

  private JLong  pIdGroup = new JLong();
  private JString pCompany = new JString();
  private JString pDescripcion = new JString();
  private JObjBDs pDetails = new JObjBDs()  {
  	public void preset() throws Exception {
  		pDetails.setValue(getDetails());
  	};
  };

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
  public BizAirportGroup() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "id_group", pIdGroup );
    this.addItem( "company", pCompany );
    this.addItem( "descripcion", pDescripcion );
    this.addItem( "detalle", pDetails );
  }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "id_group", "Grupo Aeropuertos", true, false, 18 );
    this.addFixedItem( FIELD, "company", "Compañia", true, true, 15 );
    this.addFixedItem( FIELD, "descripcion", "Descr.Grupo Aeropuertos", true, true, 200 );
    this.addFixedItem( RECORDS, "detalle", "Detalle", true, true, 200 ).setClase(BizAirportGroupDetail.class);
  }
  /**
   * Returns the table name
   */
  public String GetTable() { return "BSP_AIRPORT_GROUP"; }


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


  public void execProcessAirports(final JRecords recs) throws Exception {
		JExec oExec = new JExec(null,null) {
			public void Do() throws Exception {
				processAirports(recs);
			}
		};
		oExec.execute();
	}

  public void processAirports(JRecords recs) throws Exception {
		JIterator<BizAirport> iter = recs.getStaticIterator();
		while (iter.hasMoreElements()) {
			BizAirport a = (BizAirport) iter.nextElement();
			this.processAirport(a);
		}
	}
  
  public void processAirport(BizAirport airport) throws Exception {
  	BizAirportGroupDetail d = new BizAirportGroupDetail();
  	d.addFilter("company", this.getCompany());
  	d.addFilter("id_group", this.getIdGroup());
  	d.addFilter("airport", airport.getCode());
  	d.dontThrowException(true);
  	if (d.read())	return;
  	d.setCompany(this.getCompany());
  	d.setIdGroup(this.getIdGroup());
  	d.setAirport(airport.getCode());
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
			BizAirportGroup max =  new BizAirportGroup();
			this.pIdGroup.setValue(max.SelectMaxLong("id_group")+1);
		}
			
		super.processInsert();
	}

	public JRecords<BizAirportGroupDetail> getDetails() throws Exception {
		JRecords<BizAirportGroupDetail> recs=new JRecords<BizAirportGroupDetail>(BizAirportGroupDetail.class);
		recs.addFilter("id_group", getIdGroup());
		recs.readAll();
		return recs;
	}

	@Override
	public void attachRelationMap(JRelations rels) throws Exception {
  	rels.setSourceWinsClass("pss.bsp.config.airportGroups.GuiAirportGroups");
  	
  	JRelation r;
  	r = rels.addRelationForce(10, "restriccion usuario");
   	r.addFilter(" (BSP_AIRPORT_GROUP.company in (COMPANY_CUSTOMLIST)) ");

//		JRelation r=rels.addRelationChild(50, "Detalles", BizCarrierGroupDetail.class);
//  	r.addJoin("BSP_CARRIER_GROUP.id_group", "carrier_groups.id_group");
//  	r.setAlias("carrier_groups");

  	//rels.hideField("company");
	}

	public String getDescripField() throws Exception {
		return "descripcion";
	}

	public BizAirportGroup processClon(BizAirportGroup newDoc) throws Exception {
		newDoc.setNullToIdGroup();
		newDoc.processInsert();
		JRecords<BizAirportGroupDetail> ampls = new JRecords<BizAirportGroupDetail>(BizAirportGroupDetail.class);
		ampls.addFilter("company", getCompany());
		ampls.addFilter("id_group", getIdGroup());
		ampls.toStatic();
		while (ampls.nextRecord()) {
			BizAirportGroupDetail ampl = ampls.getRecord();
			BizAirportGroupDetail na = new BizAirportGroupDetail();
			na.copyProperties(ampl);
			na.setCompany(newDoc.getCompany());
			na.setIdGroup(newDoc.getIdGroup());
			
			na.processInsert();
		}

		return newDoc;
	}
 
	public static void processIgualarParent(String parentCompany,String company) throws Exception {
		JRecords<BizAirportGroup> ampls = new JRecords<BizAirportGroup>(BizAirportGroup.class);
		ampls.addFilter("company", parentCompany);
		ampls.toStatic();
		String updated = ""; 
		while (ampls.nextRecord()) {
			BizAirportGroup ampl= ampls.getRecord();
			BizAirportGroup na = new BizAirportGroup();
			na.dontThrowException(true);
			boolean find=na.read(company,ampl.getDescripcion());
			na.copyNotKeysProperties(ampl);
			na.setCompany(company);
			if (find) na.update();
			else 	na.processInsert();
			
			updated += (updated.equals("")?"":", ")+na.getIdGroup();
			
			JRecords<BizAirportGroupDetail> amplsD = new JRecords<BizAirportGroupDetail>(BizAirportGroupDetail.class);
			amplsD.addFilter("company", ampl.getCompany());
			amplsD.addFilter("id_group", ampl.getIdGroup());
			amplsD.toStatic();
			String updatedD = ""; 
			while (amplsD.nextRecord()) {
				BizAirportGroupDetail amplD= amplsD.getRecord();
				BizAirportGroupDetail naD = new BizAirportGroupDetail();
				naD.dontThrowException(true);
				boolean findD=naD.read(na.getCompany(),na.getIdGroup(),amplD.getAirport());
				naD.copyNotKeysProperties(amplD);
				naD.setCompany(na.getCompany());
				naD.setIdGroup(na.getIdGroup());
				naD.setAirport(amplD.getAirport());
				if (findD) naD.update();
				else 	naD.processInsert();
				updatedD += (updatedD.equals("")?"":", ")+"'"+naD.getAirport()+"'";
			}
			if (!updatedD.equals("")) {
				JRecords<BizAirportGroupDetail> amplsDD = new JRecords<BizAirportGroupDetail>(BizAirportGroupDetail.class);
				amplsDD.addFilter("company", na.getCompany());
				amplsDD.addFilter("id_group", na.getIdGroup());
				amplsDD.addFilter("airport", "("+updatedD+")","not in");
				amplsDD.processDeleteAll();
			}
		}
		if (!updated.equals("")) {
			JRecords<BizAirportGroup> amplsD = new JRecords<BizAirportGroup>(BizAirportGroup.class);
			amplsD.addFilter("company", company);
			amplsD.addFilter("id_group", "("+updated+")","not in");
			amplsD.processDeleteAll();
		}
	}
}
