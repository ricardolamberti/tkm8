package  pss.common.security.license.typeLicense.detail;

import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizTypeLicenseDetail extends JRecord {

	private JString pCompany=new JString();
	private JString pIdtypelicense=new JString();
	private JString pField=new JString();
	private JString pValue=new JString();

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

	public void setIdtypelicense(String zValue) throws Exception {
		pIdtypelicense.setValue(zValue);
	}

	public String getIdtypelicense() throws Exception {
		return pIdtypelicense.getValue();
	}

	public boolean isNullIdtypelicense() throws Exception {
		return pIdtypelicense.isNull();
	}

	public void setNullToIdtypelicense() throws Exception {
		pIdtypelicense.setNull();
	}

	public void setField(String zValue) throws Exception {
		pField.setValue(zValue);
	}

	public String getField() throws Exception {
		return pField.getValue();
	}

	public boolean isNullField() throws Exception {
		return pField.isNull();
	}

	public void setNullToField() throws Exception {
		pField.setNull();
	}

	public void setValue(String zValue) throws Exception {
		pValue.setValue(zValue);
	}

	public String getValue() throws Exception {
		return pValue.getValue();
	}

	public boolean isNullValue() throws Exception {
		return pValue.isNull();
	}

	public void setNullToValue() throws Exception {
		pValue.setNull();
	}

	/**
	 * Constructor de la Clase
	 */
	public BizTypeLicenseDetail() throws Exception {
	}

	public void createProperties() throws Exception {
		this.addItem("company", pCompany);
		this.addItem("id_typeLicense", pIdtypelicense);
		this.addItem("field", pField);
		this.addItem("value", pValue);
	}

	/**
	 * Adds the fixed object properties
	 */
	public void createFixedProperties() throws Exception {
		this.addFixedItem(KEY, "company", "Company", true, true, 15);
		this.addFixedItem(KEY, "id_typeLicense", "Id typelicense", true, true, 50);
		this.addFixedItem(KEY, "field", "Field", true, true, 150);
		this.addFixedItem(FIELD, "value", "Valor por defecto", true, false, 150);
	}

	/**
	 * Returns the table name
	 */
	public String GetTable() {
		return "SEG_DETAIL_LICENSE";
	}

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Functionality methods
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public void processInsert() throws Exception {
		super.processInsert();
	}

	/**
	 * Default read() method
	 */
	public boolean read(String zCompany, String zIdtypelicense, String zField) throws Exception {
		addFilter("company", zCompany);
		addFilter("id_typeLicense", zIdtypelicense);
		addFilter("field", zField);
		return read();
	}
}
