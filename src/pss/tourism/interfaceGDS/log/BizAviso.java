package pss.tourism.interfaceGDS.log;

import java.util.Date;

import pss.core.services.fields.JDate;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizAviso extends JRecord {

	private JString pCompany = new JString();
	private JDate pLastAviso = new JDate();

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Getter & Setters methods
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


	public void setCompany(String zValue) throws Exception {
		pCompany.setValue(zValue);
	}

	public String getCompany() throws Exception {
		return pCompany.getValue();
	}


	public void setLastAviso(Date zValue) throws Exception {
		pLastAviso.setValue(zValue);
	}

	public Date getLastAviso() throws Exception {
		return (pLastAviso.getValue());
	}

	

	/**
	 * Constructor de la Clase
	 */
	public BizAviso() throws Exception {
		this.addItem("company", pCompany);
		this.addItem("fecha_aviso", pLastAviso);

	}

	/**
	 * Adds the fixed object properties
	 */
	@Override
	public void createFixedProperties() throws Exception {
		this.addFixedItem(KEY, "company", "Empresa", true, true, 50);
		this.addFixedItem(FIELD, "fecha_aviso", "Ultima Conexion", true, true, 50);
		}

	/**
	 * Returns the table name
	 */
	@Override
	public String GetTable() {
		return "TUR_PNR_AVISO";
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Functionality methods
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Default Read() method
	 */
	public boolean read(String company) throws Exception {
		clearFilters();
		addFilter("company", company);
		return read();
	}
}
