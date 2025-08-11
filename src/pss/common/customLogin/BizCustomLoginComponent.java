package pss.common.customLogin;

import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizCustomLoginComponent extends JRecord {

	private JLong pId=new JLong();
	private JLong pSecuencia=new JLong();
	private JLong pOrden=new JLong();
	private JString pTexto=new JString();

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Getter & Setters methods
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void setId(long zValue) throws Exception {
		pId.setValue(zValue);
	}

	public long getId() throws Exception {
		return pId.getValue();
	}


	public void setTexto(String zValue) throws Exception {
		pTexto.setValue(zValue);
	}

	public String getTexto() throws Exception {
		return pTexto.getValue();
	}

	/**
	 * Constructor de la Clase
	 */
	public BizCustomLoginComponent() throws Exception {
	}

	@Override
	public void createProperties() throws Exception {
		this.addItem("id", pId);
		this.addItem("secuencia", pSecuencia);
		this.addItem("orden", pOrden);
		this.addItem("texto", pTexto);
	}

	/**
	 * Adds the fixed object properties
	 */
	@Override
	public void createFixedProperties() throws Exception {
		this.addFixedItem(KEY, "id", "Id", true, true, 18);
		this.addFixedItem(KEY, "secuencia", "Secuencia", false, false, 64);
		this.addFixedItem(FIELD, "orden", "Orden", true, false, 18);
		this.addFixedItem(FIELD, "texto", "Texto", true, true, 1000);
	}

	/**
	 * Returns the table name
	 */
	@Override
	public String GetTable() {
		return "SEG_CUSTOMLOGIN_COMPONENT";
	}

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Functionality methods
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Default Read() method
	 */
	public boolean Read(long zId, long sec) throws Exception {
		addFilter("id", zId);
		addFilter("secuencia", sec);
		return read();
	}

	@Override
	public void processInsert() throws Exception {
  	if (pOrden.isNull()) {
			BizCustomLoginComponent max = new BizCustomLoginComponent();
			pOrden.setValue(max.SelectMaxLong("orden") + 1);
  	}
  	super.processInsert();
	}
	
	


}
