package pss.bsp.reembolsos;

import pss.core.services.fields.JDate;
import pss.core.services.fields.JFloat;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.tourism.pnr.BizPNRTicket;

public class BizReembolso extends JRecord {

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//Declaración de variables
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private JLong pId = new JLong();
	private JString pCompany = new JString();
	private JString pCarrier= new JString();
	private JString pBoletoRfnd = new JString();
	private JString pBoleto = new JString();
	private JDate pFecha = new JDate();
	private JString pPeriodo = new JString();
	private JString pTipo = new JString();
	private JString pFolio = new JString();
	private JString pFPag = new JString();
	private JFloat pTarifa = new JFloat();
	private JFloat pTax = new JFloat();
	private JFloat pTotal = new JFloat();
	private JString pOrigen = new JString();
	

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//Constructor de la clase
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public BizReembolso() throws Exception {
	}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//Métodos para crear propiedades
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public void createProperties() throws Exception {
		addItem("id", pId);
		addItem("company", pCompany);
		addItem("carrier", pCarrier);
		addItem("boleto_rfnd", pBoletoRfnd);
		addItem("boleto", pBoleto);
		addItem("fecha", pFecha);
		addItem("periodo", pPeriodo);
		addItem("tipo", pTipo);
		addItem("folio", pFolio);
		addItem("tarifa", pTarifa);
		addItem("tax", pTax);
		addItem("total", pTotal);
		addItem("forma_pago", pFPag);
		addItem("origen", pOrigen);
	}

	@Override
	public void createFixedProperties() throws Exception {
		addFixedItem(KEY, "id", "ID", false, false, 64);
		addFixedItem(FIELD, "company", "Compañía", true, true, 15);
		addFixedItem(FIELD, "carrier", "Carrier", true, false, 10);
		addFixedItem(FIELD, "boleto_rfnd", "Boleto RFND", true, true, 30);
		addFixedItem(FIELD, "boleto", "Boleto", true, true, 30);
		addFixedItem(FIELD, "fecha", "Fecha", true, true, 20);
		addFixedItem(FIELD, "periodo", "Período", true, false, 10);
		addFixedItem(FIELD, "tipo", "Tipo", true, false, 10);
		addFixedItem(FIELD, "folio", "Folio", true, false, 50);
		addFixedItem(FIELD, "forma_pago", "Forma pago", true, false, 50);
		addFixedItem(FIELD, "tarifa", "Tarifa", true, false, 18, 2);
		addFixedItem(FIELD, "tax", "Impuestos", true, false, 18, 2);
		addFixedItem(FIELD, "total", "Total", true, false, 18, 2);
		addFixedItem(FIELD, "origen", "Origen", true, false, 64);
	}

	/**
	 * Returns the table name
	 */
	public String GetTable() {
		return "BSP_REEMBOLSO";
	}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Default read() method
	 */
	public boolean read(long zId) throws Exception {
		addFilter("id", zId);
		return read();
	}
	public boolean readByBoletoRfnd(String zCompany,String zBoleto) throws Exception {
		addFilter("company", zCompany);
		addFilter("boleto_rfnd", zBoleto);
		return read();
	}
	// -------------------------------------------------------------------------//
	// Getters y Setters
	// -------------------------------------------------------------------------//
	public void setId(long v) throws Exception { pId.setValue(v); }
	public long getId() throws Exception { return pId.getValue(); }

	public void setCompany(String v) throws Exception { pCompany.setValue(v); }
	public String getCompany() throws Exception { return pCompany.getValue(); }

	public void setCarrier(String v) throws Exception { pCarrier.setValue(v); }
	public String getCarrier() throws Exception { return pCarrier.getValue(); }

	public void setBoleto(String v) throws Exception { pBoleto.setValue(v); }
	public String getBoleto() throws Exception { return pBoleto.getValue(); }

	public void setBoletoRfnd(String v) throws Exception { pBoletoRfnd.setValue(v); }
	public String getBoletooRfnd() throws Exception { return pBoletoRfnd.getValue(); }

	public void setFecha(java.util.Date v) throws Exception { pFecha.setValue(v); }
	public java.util.Date getFecha() throws Exception { return pFecha.getValue(); }

	public void setPeriodo(String v) throws Exception { pPeriodo.setValue(v); }
	public String getPeriodo() throws Exception { return pPeriodo.getValue(); }
	
	public void setFormaPago(String v) throws Exception { pFPag.setValue(v); }
	public String getFormaPago() throws Exception { return pFPag.getValue(); }
	
	public void setFolio(String v) throws Exception { pFolio.setValue(v); }
	public String getFolio() throws Exception { return pFolio.getValue(); }

	public void setTipo(String v) throws Exception { pTipo.setValue(v); }
	public String getTipo() throws Exception { return pTipo.getValue(); }

	public void setTarifa(double v) throws Exception { pTarifa.setValue(v); }
	public double getTarifa() throws Exception { return pTarifa.getValue(); }
	

	public void setTax(double v) throws Exception { pTax.setValue(v); }
	public double getTax() throws Exception { return pTax.getValue(); }
	
	public void setTotal(double v) throws Exception { pTotal.setValue(v); }
	public double getTotal() throws Exception { return pTotal.getValue(); }

	public void setOrigen(String v) throws Exception { pOrigen.setValue(v); }
	public String getOrigen() throws Exception { return pOrigen.getValue(); }
	
	BizPNRTicket objBoleto;
	public BizPNRTicket getObjBoleto() throws Exception {
		if (this.objBoleto != null)
			return this.objBoleto;
		BizPNRTicket a = new BizPNRTicket();
		a.dontThrowException(true);

		if (!a.ReadByBoleto(getCompany(), getBoletooRfnd()))
			return null;
		return (this.objBoleto = a);
	}
}
