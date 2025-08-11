package  pss.bsp.market;

import pss.bsp.market.detalle.BizMarketDetail;
import pss.common.customList.config.relation.JRelations;
import pss.common.personalData.personaRegistro.BizPersonaRegistro;
import pss.core.services.JExec;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.JDateTools;
import pss.core.tools.collections.JIterator;
import pss.tourism.pnr.BizPNRSegmentoAereo;
import pss.tourism.pnr.BizPNRTicket;

public class BizMarket extends JRecord {

  private JLong  pId = new JLong();
  private JString pCompany = new JString();
  private JString pDescripcion = new JString();


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
  public void setDescripcion(String zValue) throws Exception {    pDescripcion.setValue(zValue);  }
  public String getDescripcion() throws Exception {     return pDescripcion.getValue();  }
  public boolean isNullDescripcion() throws Exception { return  pDescripcion.isNull(); } 
  public void setNullDescripcion() throws Exception {  pDescripcion.setNull(); } 


  /**
   * Constructor de la Clase
   */
  public BizMarket() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "company", pCompany );
    this.addItem( "id", pId );
    this.addItem( "descripcion", pDescripcion );
  }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "id", "ID", false, false, 18 );
    this.addFixedItem( KEY, "company", "Compañia", true, true, 50 );
    this.addFixedItem( FIELD, "descripcion", "Descripción mercado", true, true, 200 );
  }
  /**
   * Returns the table name
   */
  public String GetTable() { return "BSP_MARKET"; }


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



	@Override
	public void processDelete() throws Exception {
		this.getDetails().processDeleteAll();

		super.processDelete();
	}

	
	public JRecords<BizMarketDetail> getDetails() throws Exception {
		JRecords<BizMarketDetail> r=new JRecords<BizMarketDetail>(BizMarketDetail.class);
		r.addFilter("id_market", this.getId());
		r.addOrderBy("prioridad","ASC");

		r.readAll();
		return r;
	}
		

	@Override
	public void attachRelationMap(JRelations rels) throws Exception {
  	rels.setSourceWinsClass("pss.bsp.market.GuiMarkets");
//  	
  	//rels.hideField("company");
  	rels.hideField("id");
  	rels.hideField("descripcion");

		super.attachRelationMap(rels);
	}

	static public BizMarketDetail getTipoMercado(String company,String carrier,String tipo) throws Exception {
		BizMarketDetail cl = new BizMarketDetail();
		cl.dontThrowException(true);
		cl.addFilter("company", company);
		cl.addFilter("aerolinea", carrier);
		cl.addFixedFilter(" position('"+tipo+"' in letra) <>0 ");
		if (!cl.read()) {
			return null;
		}
		BizMarket c = new BizMarket();
		c.dontThrowException(true);
		c.addFilter("company", company);
		c.addFilter("id", cl.getIdMarket());
		if (!c.read()) 
			return null;
	  cl.setObjMarket(c);
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


	
	public BizMarket processClon(BizMarket newDoc) throws Exception {
		newDoc.insert();
		newDoc.setId(newDoc.getIdentity("id"));
		JRecords<BizMarketDetail> ampls = new JRecords<BizMarketDetail>(BizMarketDetail.class);
		ampls.addFilter("company", getCompany());
		ampls.addFilter("id_market", getId());
		ampls.toStatic();
		while (ampls.nextRecord()) {
			BizMarketDetail ampl = ampls.getRecord();
			BizMarketDetail na = new BizMarketDetail();
			na.copyProperties(ampl);
			na.setCompany(newDoc.getCompany());
			na.setIdMarket(newDoc.getId());
			
			na.processInsert();
		}

		return newDoc;
	}
 
	public static void processIgualarParent(String parentCompany,String company) throws Exception {
		JRecords<BizMarket> ampls = new JRecords<BizMarket>(BizMarket.class);
		ampls.addFilter("company", parentCompany);
		ampls.toStatic();
		String updated = ""; 
		while (ampls.nextRecord()) {
			BizMarket ampl= ampls.getRecord();
			BizMarket na = new BizMarket();
			na.dontThrowException(true);
			boolean find=na.read(company,ampl.getDescripcion());
			na.copyNotKeysProperties(ampl);
			na.setCompany(company);
			if (find) na.update();
			else 	na.processInsert();
			updated += (updated.equals("")?"":", ")+na.getId();
			
			JRecords<BizMarketDetail> amplsD = new JRecords<BizMarketDetail>(BizMarketDetail.class);
			amplsD.addFilter("company", ampl.getCompany());
			amplsD.addFilter("id_market", ampl.getId());

			JRecords<BizMarketDetail> amplsDD = new JRecords<BizMarketDetail>(BizMarketDetail.class);
			amplsDD.addFilter("company", na.getCompany());
			amplsDD.addFilter("id_market", na.getId());
			amplsDD.processDeleteAll();
			
			amplsD.toStatic();
			String updatedD = ""; 
			while (amplsD.nextRecord()) {
				BizMarketDetail amplD= amplsD.getRecord();
				BizMarketDetail naD = new BizMarketDetail();
				naD.dontThrowException(true);
				naD.copyNotKeysProperties(amplD);
				naD.setCompany(na.getCompany());
				naD.setIdMarket(na.getId());
				naD.processInsert();
				updatedD += (updatedD.equals("")?"":", ")+naD.getId();
			}
		}
		if (!updated.equals("")) {
			JRecords<BizMarket> amplsD = new JRecords<BizMarket>(BizMarket.class);
			amplsD.addFilter("company", company);
			amplsD.addFilter("id", "("+updated+")","not in");
			amplsD.processDeleteAll();
		}
	}
}
