package  pss.common.help;

import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizUserManual extends JRecord {

	private JString pId=new JString();
	private JString pFile=new JString();

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Getter & Setters methods
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////



	public void setId(String zValue) throws Exception {
		pId.setValue(zValue);
	}

	public String getId() throws Exception {
		return pId.getValue();
	}

	public void setFile(String zValue) throws Exception {
		pFile.setValue(zValue);
	}

	public String getFile() throws Exception {
		return pFile.getValue();
	}

	/**
	 * Constructor de la Clase
	 */
	public BizUserManual() throws Exception {
	}

	@Override
	public void createProperties() throws Exception {
		this.addItem("id", pId);
		this.addItem("file", pFile);
	}

	/**
	 * Adds the fixed object properties
	 */
	@Override
	public void createFixedProperties() throws Exception {
		this.addFixedItem(VIRTUAL, "id", "Manual", true, true, 50);
		this.addFixedItem(VIRTUAL, "file", "Archivo", true, true, 200);
	}

	/**
	 * Returns the table name
	 */
	@Override
	public String GetTable() {
		return "";
	}

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Functionality methods
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	
	/**
	 * Default Read() method
	 */
	public boolean Read(String zid) throws Exception {
		addFilter("id", zid);
		return read();
	}



}
