package  pss.common.regions.company;

import pss.common.regions.currency.BizMoneda;
import pss.common.regions.currency.BizMonedaPais;
import pss.common.regions.divitions.BizPais;
import pss.common.regions.nodes.BizNodo;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.JExcepcion;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;

public class BizCompanyCountry extends JRecord {

	JString pCompany=new JString();
	JString pCountry=new JString();
	JString pLocalCurrency=new JString();
	JString pDescrCountry=new JString() {
		public void preset() throws Exception {
			pDescrCountry.setValue(getObjCountry().GetDescrip());
		}
	};

	BizNodo modelNode=null;

	public void setCompany(String zValue) throws Exception {
		pCompany.setValue(zValue);
	}

	public void setCountry(String zValue) throws Exception {
		pCountry.setValue(zValue);
	}

	public String getCompany() throws Exception {
		return pCompany.getValue();
	}

	public String getCountry() throws Exception {
		return pCountry.getValue();
	}

	public String getLocalCurrency() throws Exception {
		return pLocalCurrency.getValue();
	}

//	private BizNodo modelShop=null;

	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public BizCompanyCountry() throws Exception {
	}

	@Override
	public void createProperties() throws Exception {
		addItem("company", pCompany);
		addItem("pais", pCountry);
		addItem("local_currency", pLocalCurrency);
		addItem("descr_country", pDescrCountry);
	}

	@Override
	public void createFixedProperties() throws Exception {
		addFixedItem(KEY, "company", "Empresa", true, true, 15);
		addFixedItem(KEY, "pais", "Pais", true, true, 15);
		addFixedItem(FIELD, "local_currency", "Moneda Principal", true, false, 15);
		addFixedItem(VIRTUAL, "descr_country", "Pais", true, true, 100);
	}

	@Override
	public String GetTable() {
		return "nod_company_country";
	}

	public boolean read(String zCompany, String zCountry) throws Exception {
		this.addFilter("pais", zCountry);
		this.addFilter("company", zCompany);
		return this.read();
	}

//	public boolean read(String zCompany) throws Exception {
//		this.addFilter("company", zCompany);
//		return this.read();
//	}

	@Override
	public void processInsert() throws Exception {
		this.takeDataFromDefault();
		super.processInsert();
	}

	public boolean iAmDefault() throws Exception {
		return pCompany.getValue().equals(BizCompany.DEFAULT_COMPANY);
	}

	private void takeDataFromDefault() throws Exception {
		if (this.iAmDefault()) return;
	}

	@Override
	public void processDelete() throws Exception {
		if (this.getNodos().nextRecord()) 
			JExcepcion.SendError("La empresa tiene sucursales definidas");

		super.processDelete();
		
	}

	private BizCompany company=null;
	public BizCompany getObjCompany() throws Exception {
		if (this.company!=null) return this.company;
		return (this.company=BizCompany.getCompany(this.pCompany.getValue()));
	}

	private BizPais country=null;
	public BizPais getObjCountry() throws Exception {
		if (this.country!=null) return this.country;
		return (this.country=BizPais.findPais(this.pCountry.getValue()));
	}

	private JRecords<BizNodo> nodos=null;
	public JRecords<BizNodo> getNodos() throws Exception {
		if (this.nodos!=null) return this.nodos;
		JRecords<BizNodo> recs=new JRecords<BizNodo>(BizNodo.class);
		recs.addFilter("company", getCompany());
		recs.addFilter("pais", getCountry());
		recs.readAll();
		return (this.nodos=recs);
	}

	public JRecords<BizMonedaPais> getCurrencies() throws Exception {
		JRecords<BizMonedaPais> recs=new JRecords<BizMonedaPais>(BizMonedaPais.class);
		recs.addFilter("company", getCompany());
		recs.addFilter("pais", getCountry());
		recs.readAll();
		return recs;
	}

	public String findLocalCurrency() throws Exception {
		if (!this.pLocalCurrency.isEmpty()) return this.getLocalCurrency();
		return this.getObjCountry().GetMonedaLocal();
	}

	public String getDescrLocalCurrency() throws Exception {
		return BizMoneda.getMoneda(this.findLocalCurrency()).GetDescrip();
	}

	public String findAbsoluteLocalCurrency() throws Exception {
		return this.getObjCountry().GetMonedaLocal();
	}

	public String getDescrAbsoluteLocalCurrency() throws Exception {
		return BizMoneda.getMoneda(this.findAbsoluteLocalCurrency()).GetDescrip();
	}

	public boolean isBimonetario() throws Exception {
		return !this.findLocalCurrency().equals(this.findAbsoluteLocalCurrency());
	}

	public JMap<String, String> getMonedasMap() throws Exception {
		JMap<String, String> map = JCollectionFactory.createMap();
		map.addElement(this.findLocalCurrency(), this.getDescrLocalCurrency());
		map.addElement(this.findAbsoluteLocalCurrency(), this.getDescrAbsoluteLocalCurrency());
		return map;
	}
	
}
