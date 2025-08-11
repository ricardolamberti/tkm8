package  pss.common.customList.config.field.stadistic;

import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizStadistic extends JRecord  {


	private JString pCompany = new JString();
	private JLong pListId = new JLong();
	private JLong pSecuencia = new JLong();
	private JLong pOrden = new JLong();
	private JLong pId = new JLong();
	private JString pCampo = new JString();
	private JLong pCantidad = new JLong();


	public void setCompany(String zValue) throws Exception {
		pCompany.setValue(zValue);
	}

	public String getCompany() throws Exception {
		return pCompany.getValue();
	}
	public void setListId(long zValue) throws Exception {
		pListId.setValue(zValue);
	}

	public long getListId() throws Exception {
		return pListId.getValue();
	}
	public long getOrden() throws Exception {
		return pOrden.getValue();
	}
	public long getId() throws Exception {
		return pId.getValue();
	}
	public void setId(long zValue) throws Exception {
		pId.setValue(zValue);
	}

	public void setSecuencia(long zValue) throws Exception {
		pSecuencia.setValue(zValue);
	}

	public long getSecuencia() throws Exception {
		return pSecuencia.getValue();
	}
	public void setNullToSecuencia() throws Exception { pSecuencia.setNull();}

	public void setCantidad(long zValue) throws Exception {
		pCantidad.setValue(zValue);
	}

	public long getCantidad() throws Exception {
		return pCantidad.getValue();
	}
	public void setCampo(String zValue) throws Exception {
		pCampo.setValue(zValue);
	}

	public String getCampo() throws Exception {
		return pCampo.getValue();
	}
	/**
	 * Constructor de la Clase
	 */
	public BizStadistic() throws Exception {
	}

	public void createProperties() throws Exception {
		this.addItem( "company", pCompany);
		this.addItem( "list_id", pListId);
		this.addItem( "secuencia", pSecuencia);
		this.addItem( "id", pId);
		this.addItem( "campo", pCampo);
    this.addItem( "cantidad", pCantidad );
	}

	/**
	 * Adds the fixed object properties
	 */
	public void createFixedProperties() throws Exception {
		this.addFixedItem( KEY, "company", "Company", true, true, 15);
		this.addFixedItem( KEY, "list_id", "List id", true, true, 64);
		this.addFixedItem( KEY, "secuencia", "Secuencia", true, true, 64);
		this.addFixedItem( KEY, "id", "Id", false, false, 64);
		this.addFixedItem( FIELD, "campo", "Campo", true, false, 100);
		this.addFixedItem( FIELD, "cantidad", "Cantidad", true, false, 18 );
    
	}

	/**
	 * Returns the table name
	 */
	public String GetTable() {
		return "LST_CAMPO_STADISTIC";
	}

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Functionality methods
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public boolean read( long zCampo) throws Exception {
		addFilter("id", zCampo);
		return read();
	}


}
