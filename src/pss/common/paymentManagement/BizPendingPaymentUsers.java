package  pss.common.paymentManagement;

import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizPendingPaymentUsers extends JRecord {
	private JString pCompany = new JString();
	private JString pUserId = new JString();

	public void setCompany(String zValue) throws Exception {
		pCompany.setValue(zValue);
	}

	public String getCompany() throws Exception {
		return pCompany.getValue();
	}

	public void setUserId(String zValue) throws Exception {
		pUserId.setValue(zValue);
	}

	public String getUserId() throws Exception {
		return pUserId.getValue();
	}

	/**
	 * Constructor de la Clase
	 */
	public BizPendingPaymentUsers() throws Exception {

	}

	public void createProperties() throws Exception {
		this.addItem("company", pCompany);
		this.addItem("userid", pUserId);
	}

	/**
	 * Adds the fixed object properties
	 */
	@Override
	public void createFixedProperties() throws Exception {
		this.addFixedItem(KEY, "company", "Empresa", true, true, 50);
		this.addFixedItem(KEY, "userid", "UserId", true, true, 250);
	}

	/**
	 * Returns the table name
	 */
	@Override
	public String GetTable() {
		return "PAY_PENDING_USER";
	}

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Functionality methods
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Default Read() method
	 */
	public boolean read(String company, String userid) throws Exception {
		clearFilters();
		addFilter("company", company);
		addFilter("userid", userid);
		return read();
	}
}
