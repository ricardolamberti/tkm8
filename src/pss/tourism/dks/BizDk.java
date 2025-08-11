package pss.tourism.dks;


import pss.bsp.config.airportGroups.detalle.BizAirportGroupDetail;
import pss.common.components.JSetupParameters;
import pss.common.customList.config.relation.JRelation;
import pss.common.customList.config.relation.JRelations;
import pss.common.regions.divitions.BizPaisLista;
import pss.core.data.interfaces.sentences.JBaseRegistro;
import pss.core.services.JExec;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.GeoPosition;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;
import pss.tourism.pnr.PNRCache;

public class BizDk extends JRecord {

	// -------------------------------------------------------------------------//
	// Propiedades de la Clase
	// -------------------------------------------------------------------------//
	private JLong pId=new JLong();
	private JString pCompany=new JString();
	private JString pAgEmision=new JString();
	private JString pOfficeId=new JString();
	private JString pGDS=new JString();
	private JString pDK=new JString();
	private JString pDKSinonimo=new JString();

	public long getId() throws Exception {
		return pId.getValue();
	}

	public String getOfficeId() throws Exception {
		return pOfficeId.getValue();
	}
	public String getEmisionId() throws Exception {
		return pAgEmision.getValue();
	}
	public String getCompany() throws Exception {
		return pCompany.getValue();
	}
	public void setGDS(String value) throws Exception {
		pGDS.setValue(value);
	}

	public void setDk(String value) throws Exception {
		pDK.setValue(value);
	}
	public void setDkSinonimo(String value) throws Exception {
		pDKSinonimo.setValue(value);
	}
	public void setOfficeId(String value) throws Exception {
		pOfficeId.setValue(value);
	}
	public void setAgEmision(String value) throws Exception {
		pAgEmision.setValue(value);
	}
	public void setCompany(String zValor) {
		this.pCompany.setValue(zValor);
	}

	public String getGDS() throws Exception {
		return this.pGDS.getValue();
	}

	public String getDk() throws Exception {
		return pDK.getValue();
	}
	public String getDkSinonimo() throws Exception {
		return pDKSinonimo.getValue();
	}
	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public BizDk() throws Exception {
	}

	@Override
	public void createProperties() throws Exception {
		addItem("id", pId);
		addItem("company", pCompany);
		addItem("office_id", pOfficeId);
		addItem("ag_emision", pAgEmision);
		addItem("gds", pGDS);
		addItem("dk", pDK);
		addItem("dk_sinonimo", pDKSinonimo);
		
	}

	@Override
	public void createFixedProperties() throws Exception {

		addFixedItem(KEY, "id", "id", false, false, 64);
		addFixedItem(FIELD, "company", "Company", true, false, 100);
		addFixedItem(FIELD, "office_id", "office", true, false, 100);
		addFixedItem(FIELD, "ag_emision", "Ag.emisión", true, false, 100);
		addFixedItem(FIELD, "gds", "gds", true, false, 100);
		addFixedItem(FIELD, "dk", "dk", true, false, 100);
		addFixedItem(FIELD, "dk_sinonimo", "dk sinónimo", true, false, 100);
		
	}


	@Override
	public String GetTable() {
		return "TUR_DKS";
	}

	public static String find(String company,String gds,String officeId,String agEmision,String sDk) throws Exception {
		BizDk dk = new BizDk();
		dk.dontThrowException(true);
		dk.addFilter("company", company);
		  dk.addFixedFilter(" WHERE"+
	  		" (gds IS NULL OR gds = '' OR gds = '" + gds + "') " +
	  		" AND (office_id IS NULL OR office_id = '' OR office_id = '" + officeId + "') " +
        " AND (ag_emision IS NULL OR ag_emision = '' OR ag_emision = '" + agEmision + "') "+
    		" AND (dk_sinonimo IS NULL OR dk_sinonimo = '' OR dk_sinonimo = '" + sDk + "') ");
		if (!dk.read()) return sDk;
		return dk.getDk();
	}
	public static String find2(String company,String gds,String officeId,String agEmision,String sDk) throws Exception {
		BizDk dk = new BizDk();
		dk.dontThrowException(true);
		dk.addFilter("company", company);
		  dk.addFixedFilter(" WHERE"+
		  		" (dk_sinonimo IS NULL) AND " +
	  		" (gds IS NULL OR gds = '' OR gds = '" + gds + "') " +
	  		" AND (office_id IS NULL OR office_id = '' OR office_id = '" + officeId + "') " +
        " AND (ag_emision IS NULL OR ag_emision = '' OR ag_emision = '" + agEmision + "') ");
		if (!dk.read()) return sDk;
		return dk.getDk();
	}
	@Override
	public void processUpdate() throws Exception {
		super.processUpdate();
	}
	
	@Override
	public void processInsert() throws Exception {
		super.processInsert();
	}
	public boolean read(long id) throws Exception {
		addFilter("id", id);
		return super.read();
	}

	public String execChangeAll() throws Exception {
		JExec oExec = new JExec(this, "changeAll") {

			@Override
			public void Do() throws Exception {
				setOutput(changeAll());
			}
		};
		oExec.execute();
		return (String)oExec.getOutput();
	}
	public String changeAll() throws Exception {
		String sql = "UPDATE tur_pnr_boleto AS tpb "
				+ " SET customer_id_reducido = '"+getDk()+"' "
				+ " WHERE company = '"+getCompany()+"' "
				+ (getGDS().isEmpty()?"":" AND gds = '"+getGDS()+"' ")
			  + (getOfficeId().isEmpty()?"":" AND office_id = '"+getOfficeId()+"' ")
				+ (getEmisionId().isEmpty()?"":" AND agente_emision = '"+getEmisionId()+"' ")
				+ (getDkSinonimo().isEmpty()?"":" AND  customer_id_reducido = '"+getDkSinonimo()+"' ")
				  + " AND customer_id_reducido <> '"+getDk()+"' ";
		JBaseRegistro reg;
		reg = JBaseRegistro.recordsetFactory();
		reg.Execute(sql);
		reg.close();
		return "Se modificaron "+reg.getResult()+" boletos";
	}
}
