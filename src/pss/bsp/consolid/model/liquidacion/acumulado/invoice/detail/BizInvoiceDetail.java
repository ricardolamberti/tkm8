package pss.bsp.consolid.model.liquidacion.acumulado.invoice.detail;

import java.util.Date;

import pss.bsp.consolid.model.liquidacion.acumulado.BizLiqAcum;
import pss.bsp.consolid.model.liquidacion.acumulado.invoice.pdf.GuiInvoicePdf;
import pss.bsp.dk.BizClienteDK;
import pss.bsp.organization.BizOrganization;
import pss.common.regions.multilanguage.JLanguage;
import pss.common.security.BizUsuario;
import pss.core.services.fields.JDate;
import pss.core.services.fields.JFloat;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JMap;
import pss.core.win.JWins;

public class BizInvoiceDetail extends JRecord {

	private JString pCompany = new JString();
	private JLong pLiqInvoice = new JLong();
	private JLong pId = new JLong();

	private JString pNombre = new JString();
	private JString pRuta = new JString();
	private JString pServ = new JString();
	private JString pCupon = new JString();
	private JString pClave = new JString();
	private JDate pFecha = new JDate();

	private JFloat pTotal = new JFloat();
	private JFloat pIVA = new JFloat();
	private JFloat pTUA = new JFloat();
	private JFloat pOtros = new JFloat();
	private JFloat pTarifa = new JFloat();

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Getter & Setters methods
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Getter & Setters methods
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public long getLiqInvoiceId() throws Exception {
		return pLiqInvoice.getValue();
	}

	public void setLiqInvoiceId(long val) throws Exception {
		pLiqInvoice.setValue(val);
	}
	public long getId() throws Exception {
		return pId.getValue();
	}

	public void setId(long val) throws Exception {
		pId.setValue(val);
	}


	public String getCompany() throws Exception {
		return pCompany.getValue();
	}

	public void setCompany(String val) throws Exception {
		pCompany.setValue(val);
	}


	public long getLiqInvoice() throws Exception {
		return pLiqInvoice.getValue();
	}

	public void setLiqInvoice(long val) throws Exception {
		pLiqInvoice.setValue(val);
	}

	public Date getFecha() throws Exception {
		return pFecha.getValue();
	}

	public void setFecha(Date val) throws Exception {
		pFecha.setValue(val);
	}


	public String getNombre() throws Exception {
		return pNombre.getValue();
	}

	public void setNombre(String val) throws Exception {
		pNombre.setValue(val);
	}

	public String getRuta() throws Exception {
		return pRuta.getValue();
	}

	public void setRuta(String val) throws Exception {
		pRuta.setValue(val);
	}

	public String getServ() throws Exception {
		return pServ.getValue();
	}

	public void setServ(String val) throws Exception {
		pServ.setValue(val);
	}

	public String getCupon() throws Exception {
		return pCupon.getValue();
	}

	public void setCupon(String val) throws Exception {
		pCupon.setValue(val);
	}

	public String getClave() throws Exception {
		return pClave.getValue();
	}

	public void setClave(String val) throws Exception {
		pClave.setValue(val);
	}

	public double getTotal() throws Exception {
		return pTotal.getValue();
	}

	public void setTotal(double val) throws Exception {
		pTotal.setValue(val);
	}

	public double getIVA() throws Exception {
		return pIVA.getValue();
	}

	public void setIVA(double val) throws Exception {
		pIVA.setValue(val);
	}

	public double getTUA() throws Exception {
		return pTUA.getValue();
	}

	public void setTUA(double val) throws Exception {
		pTUA.setValue(val);
	}

	public double getOtros() throws Exception {
		return pOtros.getValue();
	}

	public void setOtros(double val) throws Exception {
		pOtros.setValue(val);
	}

	public double getTarifa() throws Exception {
		return pTarifa.getValue();
	}

	public void setTarifa(double val) throws Exception {
		pTarifa.setValue(val);
	}


	
	public BizInvoiceDetail() throws Exception {
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// createProperties
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public void createProperties() throws Exception {
		this.addItem("company", pCompany);
		this.addItem("invoice_id", pLiqInvoice);
		this.addItem("id", pId);
		this.addItem("fecha", pFecha);
		
		this.addItem("nombre", pNombre);
		this.addItem("ruta", pRuta);
		this.addItem("serv", pServ);
		this.addItem("cupon", pCupon);
		this.addItem("clave", pClave);
		this.addItem("tarifa", pTarifa);
		this.addItem("iva", pIVA);
		this.addItem("tua", pTUA);
		this.addItem("otros", pOtros);
		this.addItem("total", pTotal);
		}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// createFixedProperties
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 *
	 */
	@Override
	public void createFixedProperties() throws Exception {
		this.addFixedItem(KEY, "id", "id ID", false, false, 64);
		this.addFixedItem(FIELD, "invoice_id", "Invoice ID", true, true, 64);
		this.addFixedItem(FIELD, "company", "company", true, true, 64);
		this.addFixedItem(FIELD, "fecha", "Fecha", true, false, 16);
		
		this.addFixedItem(FIELD, "nombre", "Nombre", true, false, 200);
		this.addFixedItem(FIELD, "ruta", "Ruta/Concepto/Servicio", true, false, 1000);
		this.addFixedItem(FIELD, "serv", "Prov.serv.", true, false, 50);
		this.addFixedItem(FIELD, "cupon", "Boletos cupon", true, false, 50);
		this.addFixedItem(FIELD, "clave", "Clave Unidad", true, false, 50);
		this.addFixedItem(FIELD, "tarifa", "Tarifa", true, false, 18,2);
		this.addFixedItem(FIELD, "iva", "Iva", true, false, 18,2);
		this.addFixedItem(FIELD, "tua", "TUA", true, false, 18,2);
		this.addFixedItem(FIELD, "otros", "Otros", true, false, 18,2);
		this.addFixedItem(FIELD, "total", "Total", true, false, 18,2);
			}

  @Override
  public void createControlProperties() throws Exception {
//  	this.addControlsProperty("formato", createControlCombo(JWins.createVirtualWinsFromMap(BizInvoiceDetail.getTiposFormato()),null, null) );
  	super.createControlProperties();
  }

	/**
	 * Returns the table name
	 */
	public String GetTable() {
		return "BSP_INVOICE_DETAIL";
	}

	public void processInsert() throws Exception {
		super.processInsert();
	}

	public void processUpdate() throws Exception {
		super.processUpdate();
	};

	/**
	 * Default Read() method
	 */
	public boolean Read(long id) throws Exception {
		this.addFilter("id", id);
		return this.read();
	}


}
