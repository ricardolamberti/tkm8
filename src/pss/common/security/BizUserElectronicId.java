package  pss.common.security;

import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.tools.JExcepcion;

public class BizUserElectronicId extends JRecord {
	private JString pUsuario = new JString();
	private JString pElectronicId = new JString();

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Getter & Setters methods
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void setUsuario(String zValue) throws Exception {
		pUsuario.setValue(zValue);
	}
	public String getUsuario() throws Exception {
		return pUsuario.getValue();
	}
	public void setElectronicId(String zValue) throws Exception {
		pElectronicId.setValue(zValue);
	}
	public String getElectronicId() throws Exception {
		return pElectronicId.getValue();
	}

	/**
	 * Constructor de la Clase
	 */
	public BizUserElectronicId() throws Exception {
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Properties methods
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Adds the object properties
	 */
	@Override
	public void createProperties() throws Exception {
		this.addItem("usuario", pUsuario);
		this.addItem("electronic_id", pElectronicId);
	}
	/**
	 * Adds the fixed object properties
	 */
	@Override
	public void createFixedProperties() throws Exception {
		this.addFixedItem( KEY, "usuario", "Usuario", true, true, 15);
		this.addFixedItem( KEY, "electronic_id", "Electronic id", true, true, 255);
	}
	/**
	 * Returns the table name
	 */
	@Override
	public String GetTable() {
		return "SEG_USER_ELECTID";
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Functionality methods
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Default Read() method
	 */
	public boolean Read(String zUsuario, String zElectronicId) throws Exception {
		addFilter("usuario", zUsuario);
		addFilter("electronic_id", zElectronicId);
		return Read();
	}

  /**
   * Default Read() method
   */
  public boolean Read(String zElectronicId) throws Exception {
    addFilter("electronic_id", zElectronicId);
    return Read();
  }

  @Override
	public void processInsert() throws Exception {
    BizUserElectronicId oTemp = new BizUserElectronicId();
    oTemp.dontThrowException(true);
    if ( oTemp.Read(pElectronicId.getValue()) ) {
       JExcepcion.SendError("Identificacion ya utilizada para el usuario: ^" + oTemp.getUsuario());
    }
    this.insertRecord();
  }


}
