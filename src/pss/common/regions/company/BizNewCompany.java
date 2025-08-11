package  pss.common.regions.company;

import java.util.Date;

import pss.core.services.fields.JObject;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizNewCompany extends JRecord {

	protected JString pCompany=new JString();
	protected JString pDescription=new JString();
	protected JString pNegocio=new JString();
	protected  JString pParentCompany=new JString();

	public void setParentCompany(String value) throws Exception {
		pParentCompany.setValue(value);
	}

	public String getParentCompany() throws Exception {
		return pParentCompany.getValue();
	}

	public void setCompany(String value) throws Exception {
		pCompany.setValue(value);
	}

	public void setBusiness(String value) throws Exception {
		pNegocio.setValue(value);
	}
	public void setDesciption(String value) throws Exception {
		pDescription.setValue(value);
	}


	// BizShop modelShop;

	public BizNewCompany() throws Exception {
	}

	@Override
	public void createProperties() throws Exception {
		this.addItem("company", pCompany);
		this.addItem("description", pDescription);
		this.addItem("business", pNegocio);
		this.addItem("parent_company", pParentCompany);
	}

	@Override
	public void createFixedProperties() throws Exception {
		this.addFixedItem(FIELD, "company", "Empresa", true, true, 15, 0, JObject.JUPPERCASE);
		this.addFixedItem(FIELD, "description", "Descripción", true, true, 150);
		this.addFixedItem(FIELD, "business", "Negocio", true, true, 15);
		this.addFixedItem(FIELD, "parent_company", "Empresa generadora", true, false, 15, 0, JObject.JUPPERCASE);
	}

	@Override
	public String GetTable() {
		return "";
	}

	@Override
	public void processInsert() throws Exception {
		pCompany.setValue(pCompany.getValue().toUpperCase());
		createCompany();
	}

	private void createCompany() throws Exception {
		BizCompany oEmpresa=new BizCompany();
		oEmpresa.setCompany(pCompany.getValue());
		oEmpresa.setDescription(pDescription.getValue());
		oEmpresa.setBusiness(this.getBusiness());
		oEmpresa.setParentCompany(this.getParentCompany());
		oEmpresa.setStartDate(new Date());
		oEmpresa.processInsert();
	}

	protected String getCompany() throws Exception {
		return pCompany.getValue();
	}
	protected String getCompanyParent() throws Exception {
		return pParentCompany.getValue();
	}

	protected String getBusiness() throws Exception {
		return JCompanyBusinessModules.DEFAULT;
	}
	
}
