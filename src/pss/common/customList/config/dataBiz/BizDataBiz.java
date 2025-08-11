package  pss.common.customList.config.dataBiz;


import pss.core.services.fields.JHtml;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizDataBiz extends JRecord   {



	
	private JString pCampo = new JString();
	private JHtml pValor = new JHtml();
	private JString pDescripcion = new JString();
	
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Getter & Setters methods
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void setCampo(String zValue) throws Exception {
		pCampo.setValue(zValue);
	}

	public String getCampo() throws Exception {
		return pCampo.getValue();
	}
	public void setValor(String zValue) throws Exception {
		pValor.setValue(zValue);
	}

	public String getValor() throws Exception {
		return pValor.getValue();
	}	
	public void setDescripcion(String zValue) throws Exception {
		pDescripcion.setValue(zValue);
	}

	public String getDescripcion() throws Exception {
		return pDescripcion.getValue();
	}
	/**
	 * Constructor de la Clase
	 */
	public BizDataBiz() throws Exception {
	}

	public void createProperties() throws Exception {
		this.addItem("campo", pCampo);
		this.addItem("valor", pValor);
		this.addItem("descripcion", pDescripcion);
	}

	/**
	 * Adds the fixed object properties
	 */
	public void createFixedProperties() throws Exception {
		this.addFixedItem(KEY, "campo", "Campo", true, true, 300);
		this.addFixedItem(FIELD, "descripcion", "Descripcion", true, true, 500);
		this.addFixedItem(FIELD, "valor", "Valor", true, true, 4000);
	}

	/**
	 * Returns the table name
	 */
	public String GetTable() {
		return "";
	}

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Functionality methods
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////



	
}
