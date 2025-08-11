package pss.bsp.consolid.model.mit.detail;

import pss.core.services.fields.JDate;
import pss.core.services.fields.JFloat;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizMitDetail extends JRecord {

	// -------------------------------------------------------------------------//
	// Propiedades de la Clase
	// -------------------------------------------------------------------------//
	private JLong pId = new JLong();
	private JLong pReportId = new JLong();
	private JDate pFecha = new JDate();
	private JString pCompany = new JString();
	private JString pNorOp = new JString();
	private JString pTiopoOp = new JString();
	private JString pPnr = new JString();
	private JString pNroTarjeta = new JString();
	private JString pAgencia = new JString();
	private JString pNombreAgencia = new JString();
	private JString pAerolinea = new JString();
	private JString pStatus = new JString();
	private JString pAutorizacion = new JString();
	private JString pGlobalizador = new JString();
	private JFloat pImporte = new JFloat();
	private JString pBoleto1 = new JString();
	private JString pBoleto2 = new JString();
	private JString pBoleto3 = new JString();
	private JString pBoleto4 = new JString();
	private JString pBoleto5 = new JString();
	private JString pBoleto6 = new JString();
	private JString pBoleto7 = new JString();
	private JString pBoleto8 = new JString();
	private JString pBoleto9 = new JString();
	private JString pBoleto10 = new JString();

	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public BizMitDetail() throws Exception {
	}

	@Override
	public void createProperties() throws Exception {
		addItem("id", pId);
		addItem("id_report", pReportId);
		addItem("fecha", pFecha);
		addItem("company", pCompany);
		addItem("nor_op", pNorOp);
		addItem("tipo_op", pTiopoOp);
		addItem("pnr", pPnr);
		addItem("nro_tarjeta", pNroTarjeta);
		addItem("agencia", pAgencia);
		addItem("nombre_agencia", pNombreAgencia);
		addItem("aerolinea", pAerolinea);
		addItem("status", pStatus);
		addItem("autorizacion", pAutorizacion);
		addItem("globalizador", pGlobalizador);
		addItem("importe", pImporte);
		addItem("boleto1", pBoleto1);
		addItem("boleto2", pBoleto2);
		addItem("boleto3", pBoleto3);
		addItem("boleto4", pBoleto4);
		addItem("boleto5", pBoleto5);
		addItem("boleto6", pBoleto6);
		addItem("boleto7", pBoleto7);
		addItem("boleto8", pBoleto8);
		addItem("boleto9", pBoleto9);
		addItem("boleto10", pBoleto10);
	}

	@Override
	public void createFixedProperties() throws Exception {
		addFixedItem(KEY, "id", "ID", false, false, 64);
		addFixedItem(FIELD, "company", "Company", true, false, 100);
		addFixedItem(FIELD, "id_report", "Id Report", true, false, 64);
		addFixedItem(FIELD, "fecha", "Fecha", true, false, 20);
		addFixedItem(FIELD, "nor_op", "Nro Operación", true, false, 50);
		addFixedItem(FIELD, "tipo_op", "Tipo Operación", true, false, 50);
		addFixedItem(FIELD, "pnr", "PNR", true, false, 20);
		addFixedItem(FIELD, "nro_tarjeta", "Nro Tarjeta", true, false, 30);
		addFixedItem(FIELD, "agencia", "Agencia", true, false, 50);
		addFixedItem(FIELD, "nombre_agencia", "Nombre Agencia", true, false, 100);
		addFixedItem(FIELD, "aerolinea", "Aerolínea", true, false, 20);
		addFixedItem(FIELD, "status", "Estado", true, false, 20);
		addFixedItem(FIELD, "autorizacion", "Autorización", true, false, 30);
		addFixedItem(FIELD, "globalizador", "Globalizador", true, false, 30);
		addFixedItem(FIELD, "importe", "Importe", true, false, 18, 2);
		addFixedItem(FIELD, "boleto1", "Boleto 1", true, false, 30);
		addFixedItem(FIELD, "boleto2", "Boleto 2", true, false, 30);
		addFixedItem(FIELD, "boleto3", "Boleto 3", true, false, 30);
		addFixedItem(FIELD, "boleto4", "Boleto 4", true, false, 30);
		addFixedItem(FIELD, "boleto5", "Boleto 5", true, false, 30);
		addFixedItem(FIELD, "boleto6", "Boleto 6", true, false, 30);
		addFixedItem(FIELD, "boleto7", "Boleto 7", true, false, 30);
		addFixedItem(FIELD, "boleto8", "Boleto 8", true, false, 30);
		addFixedItem(FIELD, "boleto9", "Boleto 9", true, false, 30);
		addFixedItem(FIELD, "boleto10", "Boleto 10", true, false, 30);
	}

	@Override
	public String GetTable() {
		return "TUR_MIT_DETAIL";
	}

	// -------------------------------------------------------------------------//
	// Getters y Setters
	// -------------------------------------------------------------------------//

	public void setId(long id) throws Exception { pId.setValue(id); }
	public long getId() throws Exception { return pId.getValue(); }

	public void setReportId(long id) throws Exception { pReportId.setValue(id); }
	public long getReportId() throws Exception { return pReportId.getValue(); }

	public void setFecha(java.util.Date f) throws Exception { pFecha.setValue(f); }
	public java.util.Date getFecha() throws Exception { return pFecha.getValue(); }

	public void setCompany(String v) throws Exception { pCompany.setValue(v); }
	public String getCompany() throws Exception { return pCompany.getValue(); }

	public void setNorOp(String v) throws Exception { pNorOp.setValue(v); }
	public String getNorOp() throws Exception { return pNorOp.getValue(); }

	public void setTipoOp(String v) throws Exception { pTiopoOp.setValue(v); }
	public String getTipoOp() throws Exception { return pTiopoOp.getValue(); }

	public void setPnr(String v) throws Exception { pPnr.setValue(v); }
	public String getPnr() throws Exception { return pPnr.getValue(); }

	public void setNroTarjeta(String v) throws Exception { pNroTarjeta.setValue(v); }
	public String getNroTarjeta() throws Exception { return pNroTarjeta.getValue(); }

	public void setAgencia(String v) throws Exception { pAgencia.setValue(v); }
	public String getAgencia() throws Exception { return pAgencia.getValue(); }

	public void setNombreAgencia(String v) throws Exception { pNombreAgencia.setValue(v); }
	public String getNombreAgencia() throws Exception { return pNombreAgencia.getValue(); }

	public void setAerolinea(String v) throws Exception { pAerolinea.setValue(v); }
	public String getAerolinea() throws Exception { return pAerolinea.getValue(); }

	public void setStatus(String v) throws Exception { pStatus.setValue(v); }
	public String getStatus() throws Exception { return pStatus.getValue(); }

	public void setAutorizacion(String v) throws Exception { pAutorizacion.setValue(v); }
	public String getAutorizacion() throws Exception { return pAutorizacion.getValue(); }

	public void setGlobalizador(String v) throws Exception { pGlobalizador.setValue(v); }
	public String getGlobalizador() throws Exception { return pGlobalizador.getValue(); }

	public void setImporte(double v) throws Exception { pImporte.setValue(v); }
	public double getImporte() throws Exception { return pImporte.getValue(); }

	public void setBoleto(int i, String v) throws Exception {
		switch (i) {
			case 1: pBoleto1.setValue(v); break;
			case 2: pBoleto2.setValue(v); break;
			case 3: pBoleto3.setValue(v); break;
			case 4: pBoleto4.setValue(v); break;
			case 5: pBoleto5.setValue(v); break;
			case 6: pBoleto6.setValue(v); break;
			case 7: pBoleto7.setValue(v); break;
			case 8: pBoleto8.setValue(v); break;
			case 9: pBoleto9.setValue(v); break;
			case 10: pBoleto10.setValue(v); break;
		}
	}

	public String getBoleto(int i) throws Exception {
		switch (i) {
			case 1: return pBoleto1.getValue();
			case 2: return pBoleto2.getValue();
			case 3: return pBoleto3.getValue();
			case 4: return pBoleto4.getValue();
			case 5: return pBoleto5.getValue();
			case 6: return pBoleto6.getValue();
			case 7: return pBoleto7.getValue();
			case 8: return pBoleto8.getValue();
			case 9: return pBoleto9.getValue();
			case 10: return pBoleto10.getValue();
			default: return null;
		}
	}
	public boolean hasBoleto(int i) throws Exception {
		switch (i) {
			case 1: return pBoleto1.isNotNull();
			case 2: return pBoleto2.isNotNull();
			case 3: return pBoleto3.isNotNull();
			case 4: return pBoleto4.isNotNull();
			case 5: return pBoleto5.isNotNull();
			case 6: return pBoleto6.isNotNull();
			case 7: return pBoleto7.isNotNull();
			case 8: return pBoleto8.isNotNull();
			case 9: return pBoleto9.isNotNull();
			case 10: return pBoleto10.isNotNull();
			default: return false;
		}
		}
}
