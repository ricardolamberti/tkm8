package pss.bsp.consolid.model.cuf.detail;

import pss.core.services.fields.JBoolean;
import pss.core.services.fields.JFloat;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizCufDetail extends JRecord {

	// -------------------------------------------------------------------------//
	// Propiedades de la Clase
	// -------------------------------------------------------------------------//
	private JLong pId = new JLong();
	private JLong pCufId = new JLong();
	private JString pCompany = new JString();
	private JString pTACN = new JString();
	private JFloat pEFCO = new JFloat();
	private JString pAgente = new JString();
	private JString pDK = new JString();
	private JString pAL = new JString();
	private JString pRuta = new JString();
	private JString pCorreo = new JString();
	private JString pUsoCFDI = new JString();
	private JString pFormaPago = new JString();
	private JString pOrganizacion = new JString();


	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public BizCufDetail() throws Exception {
	}

	@Override
	public void createProperties() throws Exception {
		addItem("id", pId);
		addItem("id_cuf", pCufId);
		addItem("company", pCompany);
		addItem("tacn", pTACN);
		addItem("efco", pEFCO);
		addItem("agente", pAgente);
		addItem("dk", pDK);
		addItem("al", pAL);
		addItem("ruta", pRuta);
		addItem("correo", pCorreo);
		addItem("uso_cfdi", pUsoCFDI);
		addItem("forma_pago", pFormaPago);
		addItem("organizacion", pOrganizacion);
	}


	@Override
	public void createFixedProperties() throws Exception {
		addFixedItem(KEY, "id", "ID", false, false, 64);
		addFixedItem(FIELD, "company", "Company", true, false, 100);
		addFixedItem(FIELD, "id_cuf", "Id Cuf", true, false, 64);
		addFixedItem(FIELD, "tacn", "TACN", true, false, 20);
		addFixedItem(FIELD, "efco", "EFCO", true, false, 20);
		addFixedItem(FIELD, "agente", "Agente", true, false, 100);
		addFixedItem(FIELD, "dk", "DK", true, false, 20);
		addFixedItem(FIELD, "al", "AL", true, false, 10);
		addFixedItem(FIELD, "ruta", "Ruta", true, false, 100);
		addFixedItem(FIELD, "correo", "Correo", true, false, 200);
		addFixedItem(FIELD, "uso_cfdi", "Uso CFDI", true, false, 10);
		addFixedItem(FIELD, "forma_pago", "Forma Pago", true, false, 10);
		addFixedItem(FIELD, "organizacion", "Organizacion", true, false, 10);
	}


	@Override
	public String GetTable() {
		return "TUR_CUF_DETAIL";
	}

	// -------------------------------------------------------------------------//
	// Getters y Setters
	// -------------------------------------------------------------------------//
	public void setId(long id) throws Exception {
		pId.setValue(id);
	}

	public long getId() throws Exception {
		return pId.getValue();
	}
	
	public void setCufId(long val) throws Exception {
		pCufId.setValue(val);
	}

	public long getCufId() throws Exception {
		return pCufId.getValue();
	}

	public String getCompany() throws Exception {
		return pCompany.getValue();
	}

	public void setCompany(String company) throws Exception {
		pCompany.setValue(company);
	}
	public String getTACN() throws Exception {
		return pTACN.getValue();
	}

	public void setTACN(String val) throws Exception {
		pTACN.setValue(val);
	}

	public double getEFCO() throws Exception {
		return pEFCO.getValue();
	}

	public void setEFCO(double zVal) throws Exception {
		pEFCO.setValue(zVal);
	}

	public String getAgente() throws Exception {
		return pAgente.getValue();
	}

	public void setAgente(String val) throws Exception {
		pAgente.setValue(val);
	}

	public String getDK() throws Exception {
		return pDK.getValue();
	}

	public void setDK(String val) throws Exception {
		pDK.setValue(val);
	}
	public String getOrganizacion() throws Exception {
		return pOrganizacion.getValue();
	}

	public void setOrganizacion(String val) throws Exception {
		pOrganizacion.setValue(val);
	}
	public String getAL() throws Exception {
		return pAL.getValue();
	}

	public void setAL(String val) throws Exception {
		pAL.setValue(val);
	}

	public String getRuta() throws Exception {
		return pRuta.getValue();
	}

	public void setRuta(String val) throws Exception {
		pRuta.setValue(val);
	}

	public String getCorreo() throws Exception {
		return pCorreo.getValue();
	}

	public void setCorreo(String val) throws Exception {
		pCorreo.setValue(val);
	}

	public String getUsoCFDI() throws Exception {
		return pUsoCFDI.getValue();
	}

	public void setUsoCFDI(String val) throws Exception {
		pUsoCFDI.setValue(val);
	}

	public String getFormaPago() throws Exception {
		return pFormaPago.getValue();
	}

	public void setFormaPago(String val) throws Exception {
		pFormaPago.setValue(val);
	}

}
