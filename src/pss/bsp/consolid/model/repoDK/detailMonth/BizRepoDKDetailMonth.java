package pss.bsp.consolid.model.repoDK.detailMonth;

import pss.core.services.fields.JFloat;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizRepoDKDetailMonth extends JRecord {

	// -------------------------------------------------------------------------//
	// Propiedades de la Clase
	// -------------------------------------------------------------------------//
	private JLong pId = new JLong();
	private JLong pRepoDKId = new JLong();
	private JLong pRepoDKDetailId = new JLong();
	private JString pPeriodDesde = new JString();
	private JString pPeriodHasta = new JString();
	private JString pMes = new JString();
	private JString pCompany = new JString();
	private JFloat pVentaBspNeto = new JFloat();
	private JFloat pVentaBspTotal = new JFloat();
	private JFloat pBajoCostoNeto = new JFloat();
	private JFloat pBajoCostoTotal = new JFloat();
	private JFloat pBoletosEmds = new JFloat();
	private JFloat pBoletosTkts = new JFloat();
	private JFloat pBoletosBajoCosto = new JFloat();
	private JFloat pBoletosTotal = new JFloat();



	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public BizRepoDKDetailMonth() throws Exception {
	}

	@Override
	public void createProperties() throws Exception {
		addItem("id", pId);
		addItem("repo_dk_id", pRepoDKId);
		addItem("repo_dk_detail_id", pRepoDKDetailId);
		addItem("period_desde", pPeriodDesde);
		addItem("period_hasta", pPeriodHasta);
		addItem("mes", pMes);
		addItem("company", pCompany);
		addItem("venta_bsp_neto", pVentaBspNeto);
		addItem("venta_bsp_total", pVentaBspTotal);
		addItem("bajo_costo_neto", pBajoCostoNeto);
		addItem("bajo_costo_total", pBajoCostoTotal);
		addItem("boletos_emds", pBoletosEmds);
		addItem("boletos_tkts", pBoletosTkts);
		addItem("boletos_bajo_costo", pBoletosBajoCosto);
		addItem("boletos_total", pBoletosTotal);
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
		addFixedItem(FIELD, "bajo_costo_neto", "Bajo Costo Neto", true, false, 18, 2);
		addFixedItem(FIELD, "bajo_costo_total", "Bajo Costo Total", true, false, 18, 2);
		addFixedItem(FIELD, "boletos_emds", "EMDs", true, false, 18, 2);
		addFixedItem(FIELD, "boletos_tkts", "TKT´s", true, false, 18, 2);
		addFixedItem(FIELD, "boletos_bajo_costo", "Bajo Costo", true, false, 18, 2);
		addFixedItem(FIELD, "boletos_total", "Boletos Total", true, false, 18, 2);
	}



	@Override
	public String GetTable() {
		return "TUR_REPO_DK_DETAIL_MONTH";
	}

	// -------------------------------------------------------------------------//
	// Getters y Setters
	// -------------------------------------------------------------------------//
	public void setId(long v) throws Exception { pId.setValue(v); }
	public long getId() throws Exception { return pId.getValue(); }

	public void setRepoDKId(long v) throws Exception { pRepoDKId.setValue(v); }
	public long getRepoDKId() throws Exception { return pRepoDKId.getValue(); }

	public void setRepoDKDetailId(long v) throws Exception { pRepoDKDetailId.setValue(v); }
	public long getRepoDKDetailId() throws Exception { return pRepoDKDetailId.getValue(); }

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

	public void setBajoCostoNeto(double v) throws Exception { pBajoCostoNeto.setValue(v); }
	public double getBajoCostoNeto() throws Exception { return pBajoCostoNeto.getValue(); }
	public JFloat getBajoCostoNetoProp() throws Exception { return pBajoCostoNeto; }

	public void setBajoCostoTotal(double v) throws Exception { pBajoCostoTotal.setValue(v); }
	public double getBajoCostoTotal() throws Exception { return pBajoCostoTotal.getValue(); }
	public JFloat getBajoCostoTotalProp() throws Exception { return pBajoCostoTotal; }

	public void setBoletosEmds(double v) throws Exception { pBoletosEmds.setValue(v); }
	public double getBoletosEmds() throws Exception { return pBoletosEmds.getValue(); }
	public JFloat getBoletosEmdsProp() throws Exception { return pBoletosEmds; }

	public void setBoletosTkts(double v) throws Exception { pBoletosTkts.setValue(v); }
	public double getBoletosTkts() throws Exception { return pBoletosTkts.getValue(); }
	public JFloat getBoletosTktsProp() throws Exception { return pBoletosTkts; }

	public void setBoletosBajoCosto(double v) throws Exception { pBoletosBajoCosto.setValue(v); }
	public double getBoletosBajoCosto() throws Exception { return pBoletosBajoCosto.getValue(); }
	public JFloat getBoletosBajoCostoProp() throws Exception { return pBoletosBajoCosto; }

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
