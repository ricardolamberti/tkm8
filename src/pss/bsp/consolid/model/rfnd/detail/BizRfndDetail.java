package pss.bsp.consolid.model.rfnd.detail;

import pss.core.services.fields.JFloat;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizRfndDetail extends JRecord {

	// -------------------------------------------------------------------------//
	// Propiedades de la Clase
	// -------------------------------------------------------------------------//
	private JLong pId = new JLong();
	private JLong pRfndId = new JLong();
	private JString pCompany = new JString();
	private JString pTipo = new JString();
	private JString pIata = new JString();
	private JString pDK = new JString();
	private JString pSolicita = new JString();
	private JString pNumero = new JString();
	private JString pFCE = new JString();
	private JString pPaxNameHot = new JString();
	private JString pConcepto = new JString();
	private JString pServicio = new JString();
	private JString pLA = new JString();
	private JString pFolioBsp = new JString();
	private JString pBoleto = new JString();
	private JString pFPag = new JString();
	private JFloat pTarifa = new JFloat();
	private JFloat pIva = new JFloat();
	private JFloat pTua = new JFloat();
	private JFloat pTotal = new JFloat();
	private JString pObservacion = new JString();
	private JString pPNR = new JString();
	private JString pRuta = new JString();
	private JString pFechaHot = new JString();
	private JFloat pPctCom = new JFloat();
	private JFloat pComision = new JFloat();
	private JString pFechaPeriodo = new JString();


	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public BizRfndDetail() throws Exception {
	}

	@Override
	public void createProperties() throws Exception {
		addItem("id", pId);
		addItem("id_rfnd", pRfndId);
		addItem("company", pCompany);
		addItem("tipo", pTipo);
		addItem("iata", pIata);
		addItem("dk", pDK);
		addItem("solicita", pSolicita);
		addItem("numero", pNumero);
		addItem("fce", pFCE);
		addItem("pax_name_hot", pPaxNameHot);
		addItem("concepto", pConcepto);
		addItem("servicio", pServicio);
		addItem("la", pLA);
		addItem("folio_bsp", pFolioBsp);
		addItem("boleto", pBoleto);
		addItem("fpag", pFPag);
		addItem("tarifa", pTarifa);
		addItem("iva", pIva);
		addItem("tua", pTua);
		addItem("total", pTotal);
		addItem("observacion", pObservacion);
		addItem("pnr", pPNR);
		addItem("ruta", pRuta);
		addItem("fecha_hot", pFechaHot);
		addItem("pct_com", pPctCom);
		addItem("comision", pComision);
		addItem("fecha_periodo", pFechaPeriodo);
}


	@Override
	public void createFixedProperties() throws Exception {
		addFixedItem(KEY, "id", "ID", false, false, 64);
		addFixedItem(FIELD, "company", "Company", true, false, 100);
		addFixedItem(FIELD, "id_rfnd", "Id Rfnd", true, false, 64);
		addFixedItem(FIELD, "tipo", "Tipo", true, false, 30);
		addFixedItem(FIELD, "iata", "IATA", true, false, 40);
		addFixedItem(FIELD, "dk", "DK", true, false, 20);
		addFixedItem(FIELD, "solicita", "Solicita", true, false, 250);
		addFixedItem(FIELD, "numero", "Número", true, false, 20);
		addFixedItem(FIELD, "fce", "FCE", true, false, 200);
		addFixedItem(FIELD, "pax_name_hot", "Pax/Hotel", true, false, 250);
		addFixedItem(FIELD, "concepto", "Concepto", true, false, 250);
		addFixedItem(FIELD, "servicio", "Servicio", true, false, 250);
		addFixedItem(FIELD, "la", "LA", true, false, 250);
		addFixedItem(FIELD, "folio_bsp", "Folio BSP", true, false, 250);
		addFixedItem(FIELD, "boleto", "Boleto", true, false, 30);
		addFixedItem(FIELD, "fpag", "Forma de Pago", true, false, 30);
		addFixedItem(FIELD, "tarifa", "Tarifa", true, false, 18, 2);
		addFixedItem(FIELD, "iva", "IVA", true, false, 18, 2);
		addFixedItem(FIELD, "tua", "TUA", true, false, 18, 2);
		addFixedItem(FIELD, "total", "Total", true, false, 18, 2);
		addFixedItem(FIELD, "observacion", "Observación", true, false, 400);
		addFixedItem(FIELD, "pnr", "PNR", true, false, 20);
		addFixedItem(FIELD, "ruta", "Ruta", true, false, 250);
		addFixedItem(FIELD, "fecha_hot", "Fecha Hot", true, false, 20);
		addFixedItem(FIELD, "pct_com", "% Comisión", true, false, 18, 2);
		addFixedItem(FIELD, "comision", "Comisión", true, false, 18, 2);
		addFixedItem(FIELD, "fecha_periodo", "Fecha Periodo", true, false, 20);
	}


	@Override
	public String GetTable() {
		return "TUR_REPO_RFND_DETAIL";
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
	
	public void setRfndId(long val) throws Exception {
		pRfndId.setValue(val);
	}

	public long getRfndId() throws Exception {
		return pRfndId.getValue();
	}

	public String getCompany() throws Exception {
		return pCompany.getValue();
	}

	public void setCompany(String company) throws Exception {
		pCompany.setValue(company);
	}
	
	public void setTipo(String v) throws Exception { pTipo.setValue(v); }
	public String getTipo() throws Exception { return pTipo.getValue(); }

	public void setIata(String v) throws Exception { pIata.setValue(v); }
	public String getIata() throws Exception { return pIata.getValue(); }

	public void setDK(String v) throws Exception { pDK.setValue(v); }
	public String getDK() throws Exception { return pDK.getValue(); }

	public void setSolicita(String v) throws Exception { pSolicita.setValue(v); }
	public String getSolicita() throws Exception { return pSolicita.getValue(); }

	public void setNumero(String v) throws Exception { pNumero.setValue(v); }
	public String getNumero() throws Exception { return pNumero.getValue(); }

	public void setFCE(String v) throws Exception { pFCE.setValue(v); }
	public String getFCE() throws Exception { return pFCE.getValue(); }

	public void setPaxNameHot(String v) throws Exception { pPaxNameHot.setValue(v); }
	public String getPaxNameHot() throws Exception { return pPaxNameHot.getValue(); }

	public void setConcepto(String v) throws Exception { pConcepto.setValue(v); }
	public String getConcepto() throws Exception { return pConcepto.getValue(); }

	public void setServicio(String v) throws Exception { pServicio.setValue(v); }
	public String getServicio() throws Exception { return pServicio.getValue(); }

	public void setLA(String v) throws Exception { pLA.setValue(v); }
	public String getLA() throws Exception { return pLA.getValue(); }

	public void setFolioBsp(String v) throws Exception { pFolioBsp.setValue(v); }
	public String getFolioBsp() throws Exception { return pFolioBsp.getValue(); }

	public void setBoleto(String v) throws Exception { pBoleto.setValue(v); }
	public String getBoleto() throws Exception { return pBoleto.getValue(); }

	public void setFPag(String v) throws Exception { pFPag.setValue(v); }
	public String getFPag() throws Exception { return pFPag.getValue(); }

	public void setTarifa(double v) throws Exception { pTarifa.setValue(v); }
	public double getTarifa() throws Exception { return pTarifa.getValue(); }

	public void setIva(double v) throws Exception { pIva.setValue(v); }
	public double getIva() throws Exception { return pIva.getValue(); }

	public void setTua(double v) throws Exception { pTua.setValue(v); }
	public double getTua() throws Exception { return pTua.getValue(); }

	public void setTotal(double v) throws Exception { pTotal.setValue(v); }
	public double getTotal() throws Exception { return pTotal.getValue(); }

	public void setObservacion(String v) throws Exception { pObservacion.setValue(v); }
	public String getObservacion() throws Exception { return pObservacion.getValue(); }

	public void setPNR(String v) throws Exception { pPNR.setValue(v); }
	public String getPNR() throws Exception { return pPNR.getValue(); }

	public void setRuta(String v) throws Exception { pRuta.setValue(v); }
	public String getRuta() throws Exception { return pRuta.getValue(); }

	public void setFechaHot(String v) throws Exception { pFechaHot.setValue(v); }
	public String getFechaHot() throws Exception { return pFechaHot.getValue(); }

	public void setPctCom(double v) throws Exception { pPctCom.setValue(v); }
	public double getPctCom() throws Exception { return pPctCom.getValue(); }

	public void setComision(double v) throws Exception { pComision.setValue(v); }
	public double getComision() throws Exception { return pComision.getValue(); }

	public void setFechaPeriodo(String v) throws Exception { pFechaPeriodo.setValue(v); }
	public String getFechaPeriodo() throws Exception { return pFechaPeriodo.getValue(); }

	
}
