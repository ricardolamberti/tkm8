package pss.bsp.consolid.model.repoCarrier.detailMonth;

import pss.core.services.fields.JFloat;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizRepoCarrierDetailMonth extends JRecord {

	// -------------------------------------------------------------------------//
	// Propiedades de la Clase
	// -------------------------------------------------------------------------//
	private JLong pId = new JLong();
	private JLong prepoCarrierId = new JLong();
	private JLong prepoCarrierDetailId = new JLong();
	private JString pPeriodDesde = new JString();
	private JString pPeriodHasta = new JString();
	private JString pMes = new JString();
	private JString pCompany = new JString();
	private JFloat pVentaBspNeto = new JFloat();
	private JFloat pVentaBspTotal = new JFloat();
	private JFloat pVentaBspAdma = new JFloat();
	private JFloat pVentaBspRfnd = new JFloat();
	private JFloat pBoletosEmds = new JFloat();
	private JFloat pBoletosTkts = new JFloat();
	private JFloat pBoletosTotal = new JFloat();
	private JFloat pComision = new JFloat();



	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public BizRepoCarrierDetailMonth() throws Exception {
	}

	@Override
	public void createProperties() throws Exception {
		addItem("id", pId);
		addItem("repo_dk_id", prepoCarrierId);
		addItem("repo_dk_detail_id", prepoCarrierDetailId);
		addItem("period_desde", pPeriodDesde);
		addItem("period_hasta", pPeriodHasta);
		addItem("mes", pMes);
		addItem("company", pCompany);
		addItem("venta_bsp_neto", pVentaBspNeto);
		addItem("venta_bsp_total", pVentaBspTotal);
		addItem("venta_bsp_rfnd", pVentaBspRfnd);
		addItem("venta_bsp_adma", pVentaBspAdma);
		addItem("boletos_emds", pBoletosEmds);
		addItem("boletos_tkts", pBoletosTkts);
		addItem("boletos_total", pBoletosTotal);
		addItem("comision", pComision);
		}



	@Override
	public void createFixedProperties() throws Exception {
		addFixedItem(KEY, "id", "ID", false, false, 64);
		addFixedItem(FIELD, "repo_dk_id", "Repo DK", true, false, 64);
		addFixedItem(FIELD, "repo_dk_detail_id", "Repo Detail DK", true, false, 64);
		addFixedItem(FIELD, "period_desde", "Período Desde", true, false, 20);
		addFixedItem(FIELD, "period_hasta", "Período Hasta", true, false, 20);
		addFixedItem(FIELD, "company", "Compañía", true, false, 15);
		addFixedItem(FIELD, "mes", "Compañía", true, false, 15);
		addFixedItem(FIELD, "venta_bsp_neto", "Venta BSP Neto", true, false, 18, 2);
		addFixedItem(FIELD, "venta_bsp_total", "Venta BSP Total", true, false, 18, 2);
		addFixedItem(FIELD, "venta_bsp_rfnd", "Venta BSP RFND", true, false, 18, 2);
		addFixedItem(FIELD, "venta_bsp_adma", "Venta BSP ADMA", true, false, 18, 2);
		addFixedItem(FIELD, "boletos_emds", "EMDs", true, false, 18, 2);
		addFixedItem(FIELD, "boletos_tkts", "TKT´s", true, false, 18, 2);
		addFixedItem(FIELD, "boletos_total", "Boletos Total", true, false, 18, 2);
		addFixedItem(FIELD, "comision", "Comision", true, false, 18, 2);
	}



	@Override
	public String GetTable() {
		return "TUR_REPO_CARRIER_DETAIL_MONTH";
	}

	// -------------------------------------------------------------------------//
	// Getters y Setters
	// -------------------------------------------------------------------------//
	public void setId(long v) throws Exception { pId.setValue(v); }
	public long getId() throws Exception { return pId.getValue(); }

	public void setRepoCarrierId(long v) throws Exception { prepoCarrierId.setValue(v); }
	public long getRepoCarrierId() throws Exception { return prepoCarrierId.getValue(); }

	public void setRepoCarrierDetailId(long v) throws Exception { prepoCarrierDetailId.setValue(v); }
	public long getRepoCarrierDetailId() throws Exception { return prepoCarrierDetailId.getValue(); }

	public void setPeriodDesde(String v) throws Exception { pPeriodDesde.setValue(v); }
	public String getPeriodDesde() throws Exception { return pPeriodDesde.getValue(); }

	public void setPeriodHasta(String v) throws Exception { pPeriodHasta.setValue(v); }
	public String getPeriodHasta() throws Exception { return pPeriodHasta.getValue(); }

	public void setCompany(String v) throws Exception { pCompany.setValue(v); }
	public String getCompany() throws Exception { return pCompany.getValue(); }

	public void setMes(String v) throws Exception { pMes.setValue(v); }
	public String getMes() throws Exception { return pMes.getValue(); }

	public void setVentaBspNeto(double v) throws Exception { pVentaBspNeto.setValue(v); }
	public double getVentaBspNeto() throws Exception { return pVentaBspNeto.getValue(); }
	public JFloat getVentaBspNetoProp() throws Exception { return pVentaBspNeto; }

	public void setVentaBspTotal(double v) throws Exception { pVentaBspTotal.setValue(v); }
	public double getVentaBspTotal() throws Exception { return pVentaBspTotal.getValue(); }
	public JFloat getVentaBspTotalProp() throws Exception { return pVentaBspTotal; }

	public void setVentaBspRfnd(double v) throws Exception { pVentaBspRfnd.setValue(v); }
	public double getVentaBspRfnd() throws Exception { return pVentaBspRfnd.getValue(); }
	public JFloat getVentaBspRfndProp() throws Exception { return pVentaBspRfnd; }

	public void setVentaBspAdma(double v) throws Exception { pVentaBspAdma.setValue(v); }
	public double getVentaBspAdma() throws Exception { return pVentaBspAdma.getValue(); }
	public JFloat getVentaBspAdmaProp() throws Exception { return pVentaBspAdma; }


	public void setBoletosEmds(double v) throws Exception { pBoletosEmds.setValue(v); }
	public double getBoletosEmds() throws Exception { return pBoletosEmds.getValue(); }
	public JFloat getBoletosEmdsProp() throws Exception { return pBoletosEmds; }

	public void setBoletosTkts(double v) throws Exception { pBoletosTkts.setValue(v); }
	public double getBoletosTkts() throws Exception { return pBoletosTkts.getValue(); }
	public JFloat getBoletosTktsProp() throws Exception { return pBoletosTkts; }
	
	public void setComision(double v) throws Exception { pComision.setValue(v); }
	public double getComision() throws Exception { return pComision.getValue(); }
	public JFloat getComisionProp() throws Exception { return pComision; }


	public void setBoletosTotal(double v) throws Exception { pBoletosTotal.setValue(v); }
	public double getBoletosTotal() throws Exception { return pBoletosTotal.getValue(); }
	public JFloat getBoletosTotalProp() throws Exception { return pBoletosTotal; }

	public boolean readByMes(String company,long repoId,long id,String mes) throws Exception {
		addFilter("company", company);
		addFilter("repo_dk_id", repoId);
		addFilter("repo_dk_detail_id", id);
		addFilter("mes", mes);
		return read();
	}
}
