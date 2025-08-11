package  pss.common.event2.mail;

import java.util.Date;
import pss.core.services.records.JRecord;
import pss.core.services.fields.*;

public class BizMailSignature extends JRecord {

	private JString pCompany = new JString();
	private JString pUsername = new JString();
	private JString pSignature = new JString();
	private JString pUserDesc = new JString();

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Getter & Setters methods
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void setCompany(String zValue) throws Exception {
		pCompany.setValue(zValue);
	}

	public String getCompany() throws Exception {
		return pCompany.getValue();
	}

	public boolean isNullCompany() throws Exception {
		return pCompany.isNull();
	}

	public void setNullToCompany() throws Exception {
		pCompany.setNull();
	}

	public void setUsername(String zValue) throws Exception {
		pUsername.setValue(zValue);
	}

	public String getUsername() throws Exception {
		return pUsername.getValue();
	}

	public boolean isNullUsername() throws Exception {
		return pUsername.isNull();
	}

	public void setNullToUsername() throws Exception {
		pUsername.setNull();
	}

	public void setSignature(String zValue) throws Exception {
		pSignature.setValue(zValue);
	}

	public String getSignature() throws Exception {
		return pSignature.getValue();
	}

	public boolean isNullSignature() throws Exception {
		return pSignature.isNull();
	}

	public void setNullToSignature() throws Exception {
		pSignature.setNull();
	}

	public void setUserDesc(String desc) {
		this.pUserDesc.setValue(desc);
	}

	/**
	 * Constructor de la Clase
	 */
	public BizMailSignature() throws Exception {
	}

	public void createProperties() throws Exception {
		this.addItem("COMPANY", pCompany);
		this.addItem("USERNAME", pUsername);
		this.addItem("user_desc", pUserDesc);
		this.addItem("SIGNATURE", pSignature);
	}

	/**
	 * Adds the fixed object properties
	 */
	public void createFixedProperties() throws Exception {
		this.addFixedItem(KEY, "COMPANY", "Company", true, true, 50);
		this.addFixedItem(KEY, "USERNAME", "Username", true, true, 150);
		this.addFixedItem(FIELD, "SIGNATURE", "Signature", true, false, 32000);
		this.addFixedItem(VIRTUAL, "user_desc", "Usuario", false, false, 4000);
	}

	/**
	 * Returns the table name
	 */
	public String GetTable() {
		return "MAIL_SIGNATURE";
	}

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Functionality methods
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Default read() method
	 */
	public boolean read(String zCompany, String zUsername) throws Exception {
		addFilter("COMPANY", zCompany);
		addFilter("USERNAME", zUsername);
		return read();
	}
}
