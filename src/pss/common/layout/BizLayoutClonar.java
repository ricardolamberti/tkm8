package pss.common.layout;

import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;

public class BizLayoutClonar extends JRecord {

	public BizLayoutClonar() throws Exception {
	}

	private JString pCompany=new JString();
	private JString pPais=new JString();
	private JString pLayout=new JString();
	private JString pCompanyClon=new JString();
	private JString pLayoutClon=new JString();

	private BizLayout oLayout;

	public void setPais(String value) {
		pPais.setValue(value);
	}

	public void setLayout(String value) {
		pLayout.setValue(value);
	}

	public void setObjLayout(BizLayout oLayout) {
		this.oLayout=oLayout;
	}
	
	public String getCompany() throws Exception {
		return this.pCompany.getValue();
	}

	@Override
	public void createProperties() throws Exception {
		addItem("pais", pPais);
		addItem("company", pCompany);
		addItem("layout", pLayout);
		addItem("company_clon", pCompanyClon);
		addItem("layout_clon", pLayoutClon);
	}

	@Override
	public void createFixedProperties() throws Exception {
		addFixedItem(FIELD, "company", "Empresa", true, true, 15);
		addFixedItem(FIELD, "pais", "País", true, true, 15);
		addFixedItem(FIELD, "layout", "Layout", true, true, 15);
		addFixedItem(FIELD, "company_clon", "Empresa Clon", true, true, 15);
		addFixedItem(FIELD, "layout_clon", "Layout Clon", true, true, 15);
	}

	@Override
	public String GetTable() {
		return "";
	}

	@Override
	public void processInsert() throws Exception {

		BizLayout oLayoutCopia=(BizLayout) getObjLayout().getPreInstance();
		JRecords<BizLayoutCampo> oLayoutCampos=oLayoutCopia.getCampos();

		if (this.pCompanyClon.isNull()) this.pCompanyClon.setValue(pCompany.getValue());
		oLayoutCopia.pCompany.setValue(this.pCompanyClon.getValue());
		oLayoutCopia.pLayout.setValue(this.pLayoutClon.getValue());
		oLayoutCopia.processInsert();

		oLayoutCampos.firstRecord();
		while (oLayoutCampos.nextRecord()) {
			BizLayoutCampo oLayoutCampo=oLayoutCampos.getRecord();
			oLayoutCampo.pCompany.setValue(this.pCompanyClon.getValue());
			oLayoutCampo.pLayout.setValue(this.pLayoutClon.getValue());
			oLayoutCampo.oLayout=null;
			oLayoutCampo.processInsert();
		}

	}

	public BizLayout getObjLayout() throws Exception {
		if (this.oLayout!=null) return this.oLayout;
		BizLayout record=new BizLayout();
		record.Read(pCompany.getValue(), pPais.getValue(), pLayout.getValue());
		return (this.oLayout=record);
	}

}
