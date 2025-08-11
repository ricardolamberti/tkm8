package pss.tourism.interfaceGDS.links;

import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizRetailWholeSaleLink extends JRecord {

	/********************************************
	 * Objeto de las acciones que se ejecutaran
	 ********************************************/
	private JString pWholeSaleCompanyId = new JString();
	private JString pRetailCompanyId = new JString();
	private JString pRetailBranchCode = new JString();

	
	public String getWholeSaleCompanyId() throws Exception {
		return pWholeSaleCompanyId.getValue();
	}

	public void setWholeSaleCompanyId(String pWholeSaleCompanyId) {
		this.pWholeSaleCompanyId.setValue( pWholeSaleCompanyId );
	}

	public String getRetailCompanyId() throws Exception {
		return pRetailCompanyId.getValue();
	}

	public void setRetailCompanyId(String pRetailCompanyId) {
		this.pRetailCompanyId.setValue( pRetailCompanyId );
	}

	public String getRetailBranchCode() throws Exception {
		return pRetailBranchCode.getValue();
	}

	public void setRetailBranchCode(String pRetailBranchCode) {
		this.pRetailBranchCode.setValue( pRetailBranchCode );
	}



	@Override
	public void createProperties() throws Exception {
		addItem("whole_sale_id", pWholeSaleCompanyId);
		addItem("retail_id", pRetailCompanyId);
		addItem("retail_branch_id", pRetailBranchCode);
	}

	@Override
	public void createFixedProperties() throws Exception {
		addFixedItem(KEY, "whole_sale_id", "ID Mayorista", true, true, 50);
		addFixedItem(KEY, "retail_id", "ID Minorista", false, false, 50);
		addFixedItem(KEY, "retail_branch_id", "Sucursal Minorista", false, false, 50);
	}

	public BizRetailWholeSaleLink() throws Exception {
	}

	@Override
	public String GetTable() {
		return "tur_wholesale_retail_link";
	}


}// end class
