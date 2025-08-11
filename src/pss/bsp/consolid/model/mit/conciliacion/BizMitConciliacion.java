package pss.bsp.consolid.model.mit.conciliacion;

import pss.bsp.consolid.model.mit.BizMit;
import pss.core.services.fields.JDate;
import pss.core.services.fields.JFloat;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.win.JWins;

public class BizMitConciliacion extends JRecord {

	// -------------------------------------------------------------------------//
	// Propiedades
	// -------------------------------------------------------------------------//
	private JLong pId = new JLong();
	private JLong pIdMit = new JLong();
	private JString pCompany = new JString();
	private JString pNorOp = new JString();
	private JString pTipoOp = new JString();
	private JString pPnr = new JString();
	private JDate pFecha = new JDate();
	private JFloat pImporteMit = new JFloat();
	private JFloat pImportePnr = new JFloat();
	private JFloat pDiferencia = new JFloat();
	private JString pEstadoConciliacion = new JString();
	private JString pDetalle = new JString();
	private JDate pFechaConciliacion = new JDate();

	// -------------------------------------------------------------------------//
	// Constructor
	// -------------------------------------------------------------------------//
	public BizMitConciliacion() throws Exception {
	}

	@Override
	public void createProperties() throws Exception {
		addItem("id_conciliacion", pId);
		addItem("id_mit", pIdMit);
		addItem("company", pCompany);
		addItem("nor_op", pNorOp);
		addItem("tipo_op", pTipoOp);
		addItem("pnr", pPnr);
		addItem("fecha", pFecha);
		addItem("importe_mit", pImporteMit);
		addItem("importe_pnr", pImportePnr);
		addItem("diferencia", pDiferencia);
		addItem("estado_conciliacion", pEstadoConciliacion);
		addItem("detalle", pDetalle);
		addItem("fecha_conciliacion", pFechaConciliacion);
	}

	@Override
	public void createFixedProperties() throws Exception {
		addFixedItem(KEY, "id_conciliacion", "ID", false, false, 64);
		addFixedItem(FIELD, "company", "Compañía", true, false, 15);
		addFixedItem(FIELD, "id_mit", "Id mit", true, false, 64);
		addFixedItem(FIELD, "nor_op", "Nro Op.", true, false, 50);
		addFixedItem(FIELD, "tipo_op", "Tipo Op.", true, false, 50);
		addFixedItem(FIELD, "pnr", "PNR", true, false, 50);
		addFixedItem(FIELD, "fecha", "Fecha", true, false, 20);
		addFixedItem(FIELD, "importe_mit", "Importe MIT", true, false, 18, 2);
		addFixedItem(FIELD, "importe_pnr", "Importe PNR", true, false, 18, 2);
		addFixedItem(FIELD, "diferencia", "Diferencia", true, false, 18, 2);
		addFixedItem(FIELD, "estado_conciliacion", "Estado", true, false, 30);
		addFixedItem(FIELD, "detalle", "Detalle", true, false, 4000);
		addFixedItem(FIELD, "fecha_conciliacion", "F. Conciliación", true, false, 20);
	}

	@Override
	public void createControlProperties() throws Exception {
		this.addControlsProperty("estado_conciliacion", createControlCombo(JWins.createVirtualWinsFromMap(BizMit.getEstados()),null, null) );
		super.createControlProperties();
	}
	@Override
	public String GetTable() {
		return "bsp_pnr_mit_conciliacion";
	}

	// -------------------------------------------------------------------------//
	// Getters y Setters
	// -------------------------------------------------------------------------//

	public void setId(long id) throws Exception { pId.setValue(id); }
	public long getId() throws Exception { return pId.getValue(); }

	public void setIdMit(long id) throws Exception { pIdMit.setValue(id); }
	public long getIdMit() throws Exception { return pIdMit.getValue(); }

	public void setCompany(String v) throws Exception { pCompany.setValue(v); }
	public String getCompany() throws Exception { return pCompany.getValue(); }

	public void setNorOp(String v) throws Exception { pNorOp.setValue(v); }
	public String getNorOp() throws Exception { return pNorOp.getValue(); }

	public void setTipoOp(String v) throws Exception { pTipoOp.setValue(v); }
	public String getTipoOp() throws Exception { return pTipoOp.getValue(); }

	public void setPnr(String v) throws Exception { pPnr.setValue(v); }
	public String getPnr() throws Exception { return pPnr.getValue(); }

	public void setFecha(java.util.Date v) throws Exception { pFecha.setValue(v); }
	public java.util.Date getFecha() throws Exception { return pFecha.getValue(); }

	public void setImporteMit(double v) throws Exception { pImporteMit.setValue(v); }
	public double getImporteMit() throws Exception { return pImporteMit.getValue(); }

	public void setImportePnr(double v) throws Exception { pImportePnr.setValue(v); }
	public double getImportePnr() throws Exception { return pImportePnr.getValue(); }

	public void setDiferencia(double v) throws Exception { pDiferencia.setValue(v); }
	public double getDiferencia() throws Exception { return pDiferencia.getValue(); }

	public void setEstadoConciliacion(String v) throws Exception { pEstadoConciliacion.setValue(v); }
	public String getEstadoConciliacion() throws Exception { return pEstadoConciliacion.getValue(); }

	public void setDetalle(String v) throws Exception { pDetalle.setValue(v); }
	public String getDetalle() throws Exception { return pDetalle.getValue(); }

	public void setFechaConciliacion(java.util.Date v) throws Exception { pFechaConciliacion.setValue(v); }
	public java.util.Date getFechaConciliacion() throws Exception { return pFechaConciliacion.getValue(); }
}