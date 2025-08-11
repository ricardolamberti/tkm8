package  pss.common.regions.company;

import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizCompanyBusinessModule extends JRecord {


	private JString pCompany=new JString();
	private JString pBusiness=new JString();
	private JString pModule=new JString();
	
	/**
	 * @return the pTaxId
	 */
	public String getModule() throws Exception {
		return pModule.getValue();
	}
	/**
	 * @param taxId the pTaxId to set
	 */
	public void setModule(String Module) {
		pModule.setValue(Module);
	}

	
	private JCompanyBusiness oBusiness=null;

	public void setCompany(String value) throws Exception {
		pCompany.setValue(value);
	}
	
	public void setBusiness(String value) throws Exception {
		pBusiness.setValue(value);
	}

	public String getBusiness() throws Exception {
		return pBusiness.getValue();
	}

	public String getCompany() throws Exception {
		return pCompany.getValue();
	}

	public BizCompanyBusinessModule() throws Exception {
	}

	@Override
	public void createProperties() throws Exception {
		this.addItem("company", pCompany);
		this.addItem("business", pBusiness);
		this.addItem("module", pModule);
	}

	@Override
	public void createFixedProperties() throws Exception {
		this.addFixedItem(KEY, "company", "Empresa", true, true, 15);
		this.addFixedItem(KEY, "business", "Negocio", true, true, 15);
		this.addFixedItem(KEY, "module", "Módulo", true, true, 30);
	}

	@Override
	public String GetTable() {
		return "NOD_COMPANY_MODULE";
	}

	public boolean Read(String zCompany, String zBusiness, String zModule) throws Exception {
		this.addFilter("company", zCompany);
		this.addFilter("business", zBusiness);
		this.addFilter("module", zModule);
		return this.read();
	}
  
}
