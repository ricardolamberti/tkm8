package pss.bsp.consolid.model.liquidacion.acumulado.invoice;

import java.util.Date;

import com.ibm.icu.util.Calendar;

import pss.bsp.consolid.model.cotizacionDK.BizCotizacionDK;
import pss.bsp.consolid.model.liquidacion.acumulado.BizLiqAcum;
import pss.bsp.consolid.model.liquidacion.acumulado.invoice.detail.BizInvoiceDetail;
import pss.bsp.consolid.model.liquidacion.acumulado.invoice.pdf.GuiInvoicePdf;
import pss.bsp.consolid.model.liquidacion.detail.BizLiqDetail;
import pss.bsp.consolid.model.trxOra.BizTrxOra;
import pss.bsp.dk.BizClienteDK;
import pss.bsp.organization.BizOrganization;
import pss.bsp.pdf.BizPDF;
import pss.bsp.pdf.mex.detalle.BizMexDetalle;
import pss.common.regions.multilanguage.JLanguage;
import pss.common.security.BizUsuario;
import pss.core.services.fields.JDate;
import pss.core.services.fields.JFloat;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JObjBDs;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.JDateTools;
import pss.core.tools.JTools;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JMap;
import pss.core.win.JWins;

public class BizInvoice extends JRecord {

	private JString pCompany = new JString();
	private JLong pLiquidacionId = new JLong();
	private JLong pLiqAcumId = new JLong();
	private JLong pId = new JLong();
	private JLong pHoja = new JLong();
	private JDate pFechaDesde = new JDate();
	private JDate pFechaLiq = new JDate();
	private JDate pFechaInvoice = new JDate();
	private JDate pFechaHasta = new JDate();
	private JString pOrganization = new JString();
	private JString pDk = new JString();
	private JString pDkName = new JString();
	private JString pHojaStr = new JString() {
		public void preset() throws Exception {
			if (pHoja.getRawValue()==1) pHojaStr.setValue( "Liquidacion (AIR917)" );
			if (pHoja.getRawValue()==2) pHojaStr.setValue( "Servicios BSP (AIR917)" );
			if (pHoja.getRawValue()==3) pHojaStr.setValue( "Servicios Aereo (AIR917)" );
			if (pHoja.getRawValue()==4) pHojaStr.setValue( "Liquidacion (ETV917)" );
			if (pHoja.getRawValue()==5) pHojaStr.setValue( "Liquidacion (KIW917)" );
			if (pHoja.getRawValue()==6) pHojaStr.setValue( "Liquidacion (HMI917)" );
			if (pHoja.getRawValue()==7) pHojaStr.setValue( "Liquidacion (FLR917)" );
		};
	};

	private JString pFormato = new JString();
	private JLong pNumero = new JLong();
	private JString pDireccion = new JString();
	private JString pCurrency = new JString();
	private JString pVat = new JString();
	private JString pBankName = new JString();
	private JString pAccountName = new JString();
	private JString pAccountNumber = new JString();
	private JString pClabe = new JString();
	private JString pSwift = new JString();
	private JString pAccountCurrency = new JString();
	private JString pBankAddress = new JString();
	private JString pDireccionFrom = new JString();


	private JFloat pTotal = new JFloat();
	private JFloat pTax = new JFloat();
	private JFloat pPay = new JFloat();
	private JFloat pComision = new JFloat();
	private JObjBDs<BizInvoiceDetail> pDetalle = new JObjBDs<BizInvoiceDetail>();

	private JFloat pTotalAndTax = new JFloat() {
		public void preset() throws Exception {
			pTotalAndTax.setValue(JTools.rd(pTotal.getValue()+pTax.getValue(),2));
		};
	};
	
	
	private JFloat pTotalAmount = new JFloat() {
		public void preset() throws Exception {
			pTotalAmount.setValue(JTools.rd(pTotalAndTax.getValue()-pPay.getValue(),2));
		};
	};
		
	private JFloat pBilling = new JFloat() {
		public void preset() throws Exception {
			pBilling.setValue(JTools.rd(pTotalAmount.getValue()-pComision.getValue(),2));
		};
	};
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Getter & Setters methods
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Getter & Setters methods
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  public JRecords<BizInvoiceDetail> getDetalles() throws Exception {
  	if (pDetalle.isRawNull()) {
  		JRecords<BizInvoiceDetail> convs = new JRecords<BizInvoiceDetail>(BizInvoiceDetail.class);
  		convs.addFilter("company", getCompany());
    	convs.addFilter("invoice_id", getId());
    	convs.toStatic();
  		pDetalle.setValue(convs);
  	}
  	return pDetalle.getValue();
  }
     
  
	public long getLiqAcumId() throws Exception {
		return pLiqAcumId.getValue();
	}

	public void setLiqAcumId(long val) throws Exception {
		pLiqAcumId.setValue(val);
	}

	public long getLiquidacionId() throws Exception {
		return pLiquidacionId.getValue();
	}

	public void setLiquidacionId(long val) throws Exception {
		pLiquidacionId.setValue(val);
	}
	public long getHoja() throws Exception {
		return pHoja.getValue();
	}

	public void setHoja(long val) throws Exception {
		pHoja.setValue(val);
	}
	public long getId() throws Exception {
		return pId.getValue();
	}

	public void setId(long val) throws Exception {
		pId.setValue(val);
	}

	public java.util.Date getFechaDesde() throws Exception {
		return pFechaDesde.getValue();
	}

	public void setFechaDesde(java.util.Date val) throws Exception {
		pFechaDesde.setValue(val);
	}
	public java.util.Date getFechaLiquidacion() throws Exception {
		return pFechaLiq.getValue();
	}

	public void setFechaLiquidacion(java.util.Date val) throws Exception {
		pFechaLiq.setValue(val);
	}
	public java.util.Date getFechaInvoice() throws Exception {
		return pFechaInvoice.getValue();
	}

	public void setFechaInvoice(java.util.Date val) throws Exception {
		pFechaInvoice.setValue(val);
	}
	public java.util.Date getFechaHasta() throws Exception {
		return pFechaHasta.getValue();
	}

	public void setFechaHasta(java.util.Date val) throws Exception {
		pFechaHasta.setValue(val);
	}

	public String getOrganization() throws Exception {
		return pOrganization.getValue();
	}

	public void setOrganization(String val) throws Exception {
		pOrganization.setValue(val);
	}
	
	public String getCompany() throws Exception {
		return pCompany.getValue();
	}

	public void setCompany(String val) throws Exception {
		pCompany.setValue(val);
	}

	public String getDk() throws Exception {
		return pDk.getValue();
	}

	public void setDk(String val) throws Exception {
		pDk.setValue(val);
	}
	public String getDkName() throws Exception {
		return pDkName.getValue();
	}

	public void setDkName(String val) throws Exception {
		pDkName.setValue(val);
	}

	public String getFormato() throws Exception {
		return pFormato.getValue();
	}

	public void setFormato(String val) throws Exception {
		pFormato.setValue(val);
	}

	public long getNumero() throws Exception {
		return pNumero.getValue();
	}

	public void setNumero(long val) throws Exception {
		pNumero.setValue(val);
	}

	public String getDireccion() throws Exception {
		return pDireccion.getValue();
	}

	public void setDireccion(String val) throws Exception {
		pDireccion.setValue(val);
	}

	public String getCurrency() throws Exception {
		return pCurrency.getValue();
	}

	public void setCurrency(String val) throws Exception {
		pCurrency.setValue(val);
	}

	public String getVat() throws Exception {
		return pVat.getValue();
	}

	public void setVat(String val) throws Exception {
		pVat.setValue(val);
	}

	public String getDireccionFrom() throws Exception {
		return pDireccionFrom.getValue();
	}

	public void setDireccionFrom(String val) throws Exception {
		pDireccionFrom.setValue(val);
	}

	public double getTotal() throws Exception {
		return pTotal.getValue();
	}

	public void setTotal(double val) throws Exception {
		pTotal.setValue(val);
	}

	public double getTax() throws Exception {
		return pTax.getValue();
	}
	public double getComision() throws Exception {
		return pComision.getValue();
	}

	public void setTax(double val) throws Exception {
		pTax.setValue(val);
	}

	public double getPay() throws Exception {
		return pPay.getValue();
	}

	public void setPay(double val) throws Exception {
		pPay.setValue(val);
	}

	public double getTotalAndTax() throws Exception {
		return pTotalAndTax.getValue();
	}
	public double getTotalAmount() throws Exception {
		return pTotalAmount.getValue();
	}
	public double getBilling() throws Exception {
		return pBilling.getValue();
	}
	public void setBilling(double val) throws Exception {
		pBilling.setValue(val);
	}
	public void setComision(double val) throws Exception {
		pComision.setValue(val);
	}

	public String getBankName() throws Exception {
		return pBankName.getValue();
	}
	public void setBankName(String val) throws Exception {
		pBankName.setValue(val);
	}
	public String getAccountName() throws Exception {
		return pAccountName.getValue();
	}
	public void setAccountName(String val) throws Exception {
		pAccountName.setValue(val);
	}
	public String getAccountNumber() throws Exception {
		return pAccountNumber.getValue();
	}
	public void setAccountNumber(String val) throws Exception {
		pAccountNumber.setValue(val);
	}
	public String getAccountCurrency() throws Exception {
		return pAccountCurrency.getValue();
	}
	public void setAccountCurrency(String val) throws Exception {
		pAccountCurrency.setValue(val);
	}
	public String getClabe() throws Exception {
		return pClabe.getValue();
	}
	public void setClabe(String val) throws Exception {
		pClabe.setValue(val);
	}
	public void setSwift(String val) throws Exception {
		pSwift.setValue(val);
	}
	public String getSwift() throws Exception {
		return pSwift.getValue();
	}

	public String getBankAddress() throws Exception {
		return pBankAddress.getValue();
	}
	public void setBankAddress(String val) throws Exception {
		pBankAddress.setValue(val);
	}
	public BizInvoice() throws Exception {
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// createProperties
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public void createProperties() throws Exception {
		this.addItem("company", pCompany);
		this.addItem("liquidacion_id", pLiquidacionId);
		this.addItem("liq_acum_id", pLiqAcumId);
    this.addItem("organization", pOrganization);
    this.addItem("id", pId);
    this.addItem("dk", pDk);
    this.addItem("dk_name", pDkName);
    this.addItem("hoja", pHoja);
		
    this.addItem("fecha_invoice", pFechaInvoice);
    this.addItem("fecha_liq", pFechaLiq);
		this.addItem("fecha_desde", pFechaDesde);
		this.addItem("fecha_hasta", pFechaHasta);
		this.addItem("formato", pFormato);
		this.addItem("numero", pNumero);
		this.addItem("direccion", pDireccion);
		this.addItem("currency", pCurrency);
		this.addItem("vat", pVat);
		this.addItem("direccion_from", pDireccionFrom);
		this.addItem("total", pTotal);
		this.addItem("tax", pTax);
		this.addItem("pay", pPay);
		this.addItem("comision", pComision);
		this.addItem("total_and_tax", pTotalAndTax);	
		this.addItem("tot_amount", pTotalAmount);	
		this.addItem("billing", pBilling);	
		this.addItem("detalle", pDetalle);	
		this.addItem("bank_name", pBankName);	
		this.addItem("account_name", pAccountName);	
		this.addItem("account_number", pAccountNumber);	
		this.addItem("clabe_account_number", pClabe);	
		this.addItem("swift", pSwift);	
		this.addItem("acccount_currency", pAccountCurrency);	
		this.addItem("bank_address", pBankAddress);	
		this.addItem("hoja_str", pHojaStr);	
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// createFixedProperties
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public void createFixedProperties() throws Exception {
		this.addFixedItem(KEY, "id", "id ID", false, false, 64);
		this.addFixedItem(FIELD, "liquidacion_id", "Liquidacion ID", true, true, 64);
		this.addFixedItem(FIELD, "company", "company", true, true, 64);
		this.addFixedItem(FIELD, "liq_acum_id", "Liquidacion Acum ID", true, true, 64);
	  this.addFixedItem(FIELD, "organization", "Organización", true, false, 50);
	  this.addFixedItem(FIELD, "dk", "DK", true, true, 20);
	  this.addFixedItem(FIELD, "dk_name", "DK Name", true, true, 200);
	  this.addFixedItem(FIELD, "hoja", "Hoja", true, true, 18);
	  this.addFixedItem(FIELD, "fecha_invoice", "Fecha Invoice", true, false, 18);
	  this.addFixedItem(FIELD, "fecha_liq", "Fecha liquidacion", true, false, 18);
		this.addFixedItem(FIELD, "fecha_desde", "Fecha desde", true, false, 18);
		this.addFixedItem(FIELD, "fecha_hasta", "Fecha hasta", true, false, 18);
		this.addFixedItem(FIELD, "formato", "Formato", true, false, 50);
		this.addFixedItem(FIELD, "numero", "Invoice #", true, false, 18);
		this.addFixedItem(FIELD, "direccion", "Dirección", true, false, 1000);
		this.addFixedItem(FIELD, "currency", "Moneda", true, false, 1000);
		this.addFixedItem(FIELD, "vat", "Vat number", true, false, 1000);
		this.addFixedItem(FIELD, "bank_name", "Bank name", true, false, 1000);
		this.addFixedItem(FIELD, "account_name", "Account name", true, false, 1000);
		this.addFixedItem(FIELD, "account_number", "Account number", true, false, 1000);
		this.addFixedItem(FIELD, "clabe_account_number", "Clabe", true, false, 1000);
		this.addFixedItem(FIELD, "swift", "Swift", true, false, 1000);
		this.addFixedItem(FIELD, "acccount_currency", "Account currency", true, false, 1000);
		this.addFixedItem(FIELD, "bank_address", "Bank address", true, false, 1000);
		this.addFixedItem(FIELD, "direccion_from", "Dirección cliente", true, false, 1000);
		this.addFixedItem(FIELD, "total", "Revenue of issued Tickets – total", true, false, 18,2);
		this.addFixedItem(FIELD, "tax", "Amount of Tax - total", true, false, 18,2);
		this.addFixedItem(FIELD, "pay", "Total payment BSP-CC", true, false, 18,2);
		this.addFixedItem(FIELD, "comision", "Commission and refund commission", true, false, 18,2);
		this.addFixedItem(VIRTUAL, "total_and_tax", "Total amount of ticket revenue and tax", true, false, 18,2);
		this.addFixedItem(VIRTUAL, "tot_amount", "Total Amount", true, false, 18,2);
		this.addFixedItem(VIRTUAL, "billing", "Billing amount", true, false, 18,2);
		this.addFixedItem(VIRTUAL, "hoja_str", "Tipo documento", true, false, 100);
	    this.addFixedItem( VIRTUAL, "detalle", "detalle", true, false, 4000 ).setClase(BizInvoiceDetail.class);

		}

  @Override
  public void createControlProperties() throws Exception {
  	this.addControlsProperty("formato", createControlCombo(JWins.createVirtualWinsFromMap(BizInvoice.getTiposFormato()),null, null) );
  	super.createControlProperties();
  }
  public final static String FM_INVOICE_AER917 = "AER917";
  public final static String FM_INVOICE_ETV917 = "ETV917";
  public final static String FM_INVOICE_KIW917 = "KIW917";
  public final static String FM_INVOICE_HMI917 = "HMI917";
  public final static String FM_INVOICE_FLR917 = "FLR917";
  
	public static JMap<String,String> getTiposFormato() throws Exception {
		JMap<String, String> map = JCollectionFactory.createOrderedMap();
		map.addElement(BizInvoice.FM_INVOICE_AER917, JLanguage.translate("Modelo AER917"));
		map.addElement(BizInvoice.FM_INVOICE_ETV917, JLanguage.translate("Modelo ETV917"));
		map.addElement(BizInvoice.FM_INVOICE_KIW917, JLanguage.translate("Modelo KIW917"));
		map.addElement(BizInvoice.FM_INVOICE_HMI917, JLanguage.translate("Modelo HMI917"));
		map.addElement(BizInvoice.FM_INVOICE_FLR917, JLanguage.translate("Modelo FLR917"));
		return map;
	}
	/**
	 * Returns the table name
	 */
	public String GetTable() {
		return "BSP_INVOICE";
	}

	public void processInsert() throws Exception {
		super.processInsert();
  	JRecords<BizInvoiceDetail> detalles = this.pDetalle.getRawValue();
   	if (detalles!=null) {
   		JIterator<BizInvoiceDetail> it = detalles.getStaticIterator();
   		while (it.hasMoreElements()) {
   			BizInvoiceDetail det = it.nextElement();
   			det.setLiqInvoiceId(getId());
   			det.setCompany(getCompany());
   			det.processInsert();
   		}
   	}
	}
	
	@Override
	public void processDelete() throws Exception {
		JRecords<BizInvoiceDetail> detalles = this.getDetalles();
		detalles.deleteAll();
   	super.processDelete();
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

  public GuiInvoicePdf createDataSourceInvoice() throws Exception{
  	GuiInvoicePdf a = new GuiInvoicePdf();
  	a.GetcDato().setCompany(this.getCompany());
  	a.GetcDato().setDK(this.getDk());
  	a.GetcDato().setId(this.getId());
  	a.GetcDato().setHoja(this.getHoja());
  	a.GetcDato().setLiquidationId(this.getLiquidacionId());
  	a.GetcDato().getHeader();
    a.GetcDato().getRecords();
  	return a;
 }
	BizOrganization objOrg;
  
  public BizOrganization getObjOrganization() throws Exception {
  	if (objOrg!=null) return objOrg;
  	BizOrganization biz = new BizOrganization();
  	biz.dontThrowException(true);
  	biz.addFilter("company", BizUsuario.getUsr().getCompany());
  	biz.addFilter("code", this.getOrganization());
  	if (biz.read())
  		return objOrg=biz;
  	return  null;
	}
  public void fillFormato(long hoja,String company,String zdk, long liqAcumId) throws Exception {
  	if (hoja==1) fillFormatoH1(company,zdk,liqAcumId);
  	if (hoja==2) fillFormatoH2(company,zdk,liqAcumId);
  	if (hoja==3) fillFormatoH3(company,zdk,liqAcumId);
  	if (hoja==4) fillFormatoH4(company,zdk,liqAcumId);
  	if (hoja==5) fillFormatoH5(company,zdk,liqAcumId);
   	if (hoja==6) fillFormatoH6(company,zdk,liqAcumId);
   	if (hoja==7) fillFormatoH7(company,zdk,liqAcumId);
    
  }
  //AIR917
  public void fillFormatoH1(String company,String zdk, long liqAcumId) throws Exception {
		BizClienteDK dk = new BizClienteDK();
		dk.dontThrowException(true);
		dk.ReadByDK(company, zdk);
		BizLiqAcum acum = new BizLiqAcum();
		acum.dontThrowException(true);
		acum.readById(liqAcumId);
		BizInvoice lastInvoice = null;
		if (dk.getFormato() != null) {
			JRecords<BizInvoice> lastInvoices = null;
			lastInvoices = new JRecords<BizInvoice>(BizInvoice.class);
			lastInvoices.addFilter("company", "company");
			lastInvoices.addFilter("dk", dk.getCode());
			lastInvoices.addFilter("formato", dk.getFormato());
			lastInvoices.addOrderBy("fecha_desde", "DESC");
			lastInvoices.setTop(1);
			lastInvoices.dontThrowException(true);
			JIterator<BizInvoice> it = lastInvoices.getStaticIterator();
			if (it.hasMoreElements())
				lastInvoice = it.nextElement();
		}
		setHoja(1);
		setDk(acum.getDK());
		setDkName(dk.getDescripcion().isEmpty()?acum.getDK():dk.getDescripcion());
		setCurrency(acum.getMoneda());
		setComision(acum.getComision());
		setTotal(acum.getTotalFacturado());
		setTax(acum.getTotalTax());
		setPay(acum.getTotalTarjeta());

		setNumero(lastInvoice == null ? 1 : lastInvoice.getNumero() + 1);
		setDireccion(lastInvoice == null ? "TRAVEL-FAN SA DE CV \n" + "JAIME BALNES, 11 EDIFICIO C,PISO 10, 11510, LOS MORALES POLANCO, MIGUEL HIDALGO, CIUDAD DE MÉXICO, MÉXICO.\n" + "R.F.C. TRA100420CN1" : lastInvoice.getDireccion());
		setDireccionFrom(lastInvoice == null ? "Global Ticket Factory GmbH\n" + "Boppstr. 10\n" + "10967 Berlin" : lastInvoice.getDireccionFrom());
		setVat(lastInvoice == null ?"DE36113030":lastInvoice.getVat());
		
	}
  public void fillFormatoH2(String company,String zdk, long liqAcumId) throws Exception {
		BizClienteDK dk = new BizClienteDK();
		dk.dontThrowException(true);
		dk.ReadByDK(company, zdk);
		BizLiqAcum acum = new BizLiqAcum();
		acum.dontThrowException(true);
		acum.readById(liqAcumId);
		BizInvoice lastInvoice = null;
		if (dk.getFormato() != null) {
			JRecords<BizInvoice> lastInvoices = null;
			lastInvoices = new JRecords<BizInvoice>(BizInvoice.class);
			lastInvoices.addFilter("company", "company");
			lastInvoices.addFilter("dk", dk.getCode());
			lastInvoices.addFilter("formato", dk.getFormato());
			lastInvoices.addOrderBy("fecha_desde", "DESC");
			lastInvoices.setTop(1);
			lastInvoices.dontThrowException(true);
			JIterator<BizInvoice> it = lastInvoices.getStaticIterator();
			if (it.hasMoreElements())
				lastInvoice = it.nextElement();
		}
		setHoja(2);
		setDk(acum.getDK());
		setDkName(dk.getDescripcion().isEmpty()?acum.getDK():dk.getDescripcion());
		setCurrency(acum.getMoneda());
	
			setNumero(lastInvoice == null ? 1 : lastInvoice.getNumero() + 1);
		setDireccion(lastInvoice == null ? "TRAVEL-FAN SA DE CV \n"
				+ "JAIME BALNES, 11 EDIFICIO C,PISO 10, 11510, LOS MORALES POLANCO, MIGUEL HIDALGO, CIUDAD DE MÉXICO, MÉXICO.\n"
				+ "R.F.C. TRA100420CN1": lastInvoice.getDireccion());
		setDireccionFrom(lastInvoice == null ? "Global Ticket Factory GmbH\n"
				+ "Boppstr. 10\n"
				+ "10967 Berlin": lastInvoice.getDireccionFrom());
		setVat(lastInvoice == null ?"DE36113030":lastInvoice.getVat());
		
	
			JRecords<BizTrxOra> oras = new  JRecords<BizTrxOra>(BizTrxOra.class);
			oras.addFilter("tipo_servicio", "BSP");
			oras.addFilter("tipo_documento", "FCE");
			oras.addFilter("dk", acum.getDK());
			oras.addFilter("fecha_emision", acum.getFechaDesde(),">=");
			oras.addFilter("fecha_emision", acum.getFechaHasta(),"<=");
			double bill=0;
			JIterator<BizTrxOra> it = oras.getStaticIterator();
			while (it.hasMoreElements()) {
				BizTrxOra ora = it.nextElement();
				BizInvoiceDetail det = new BizInvoiceDetail();
				det.setCompany(getCompany());
				det.setNombre("");
				det.setRuta(ora.getRuta());
				det.setServ("E48");
				det.setTarifa(ora.getTarifa());
				det.setIVA(ora.getIva());
				det.setTUA(ora.getTua());
				det.setOtros(0);
				det.setTotal(ora.getTotal());
				bill+=ora.getTotal();
				getDetalles().addItem(det);
				
			}
			setComision(0);
			setTax(0);
			setPay(0);
			setTotal(bill);
		
	}
  public void fillFormatoH3(String company,String zdk, long liqAcumId) throws Exception {
		BizClienteDK dk = new BizClienteDK();
		dk.dontThrowException(true);
		dk.ReadByDK(company, zdk);
		BizLiqAcum acum = new BizLiqAcum();
		acum.dontThrowException(true);
		acum.readById(liqAcumId);
		BizInvoice lastInvoice = null;
		if (dk.getFormato() != null) {
			JRecords<BizInvoice> lastInvoices = null;
			lastInvoices = new JRecords<BizInvoice>(BizInvoice.class);
			lastInvoices.addFilter("company", "company");
			lastInvoices.addFilter("dk", dk.getCode());
			lastInvoices.addFilter("formato", dk.getFormato());
			lastInvoices.addOrderBy("fecha_desde", "DESC");
			lastInvoices.setTop(1);
			lastInvoices.dontThrowException(true);
			JIterator<BizInvoice> it = lastInvoices.getStaticIterator();
			if (it.hasMoreElements())
				lastInvoice = it.nextElement();
		}
		setHoja(3);
		setDk(acum.getDK());
		setDkName(dk.getDescripcion().isEmpty()?acum.getDK():dk.getDescripcion());
		setCurrency(acum.getMoneda());
	
			setNumero(lastInvoice == null ? 1 : lastInvoice.getNumero() + 1);
		setDireccion(lastInvoice == null ? "TRAVEL-FAN SA DE CV \n"
				+ "JAIME BALNES, 11 EDIFICIO C,PISO 10, 11510, LOS MORALES POLANCO, MIGUEL HIDALGO, CIUDAD DE MÉXICO, MÉXICO.\n"
				+ "R.F.C. TRA100420CN1": lastInvoice.getDireccion());
		setDireccionFrom(lastInvoice == null ? "Global Ticket Factory GmbH\n"
				+ "Boppstr. 10\n"
				+ "10967 Berlin": lastInvoice.getDireccionFrom());
		setVat(lastInvoice == null ?"DE36113030":lastInvoice.getVat());
		
	
			JRecords<BizTrxOra> oras = new  JRecords<BizTrxOra>(BizTrxOra.class);
			oras.addFilter("tipo_servicio", "CSA");
			oras.addFilter("tipo_documento", "FCE");
			oras.addFilter("dk", acum.getDK());
			oras.addFilter("fecha_emision", acum.getFechaDesde(),">=");
			oras.addFilter("fecha_emision", acum.getFechaHasta(),"<=");
			double bill=0;
			JIterator<BizTrxOra> it = oras.getStaticIterator();
			while (it.hasMoreElements()) {
				BizTrxOra ora = it.nextElement();
				BizInvoiceDetail det = new BizInvoiceDetail();
				det.setCompany(getCompany());
				det.setNombre("");
				det.setRuta(ora.getRuta());
				det.setServ("E48");
				det.setTarifa(ora.getTarifa());
				det.setIVA(ora.getIva());
				det.setTUA(ora.getTua());
				det.setOtros(0);
				det.setTotal(ora.getTotal());
				bill+=ora.getTotal();
				getDetalles().addItem(det);
				
			}
			setComision(0);
			setTax(0);
			setPay(0);
			setTotal(bill);
		
  }
  // EVT917
  public void fillFormatoH4(String company,String zdk, long liqAcumId) throws Exception {
//  		new BizMexDetalle().completeDK();
//  		new BizPDF().procPendientes(getFechaDesde(),getFechaHasta());

  		BizClienteDK dk = new BizClienteDK();
  		dk.dontThrowException(true);
  		dk.ReadByDK(company, zdk);
  		BizLiqAcum acum = new BizLiqAcum();
  		acum.dontThrowException(true);
  		acum.readById(liqAcumId);
  		
  		JRecords<BizLiqDetail> dets = new JRecords<BizLiqDetail>(BizLiqDetail.class);
  		dets.addFilter("company", acum.getCompany());
  		dets.addFilter("liquidacion_id",acum.getLiquidacionId());
  		dets.addFilter("dk",acum.getDK(),"=");
  		dets.readAll();
  		long tkttCount=0;
  		double tkttImporte=0;
  		long emdCount=0;
  		double emdImporte=0;
  		long admCount=0;
  		double admImporte=0;
   		long rfndCount=0;
  		double rfndImporte=0;
   		long feeCount=0;
  		double feeImporte=0;
   		double cred=0;
   	 	while (dets.nextRecord()) {
				BizLiqDetail det = dets.getRecord();

				if (det.getTipoDoc().equals("FCE") && det.getTipoServ().equals("CSA")) {
					feeCount++;
					feeImporte += det.getImporte();
				} else if (det.getTipoServ().equals("M1")) {
					admCount++;
					admImporte += det.getImporte();
				} else if (det.getTipoDoc().equals("RFN")) {
					rfndCount++;
					rfndImporte += det.getImporte();
				} else if (det.getTipo().equals("ETR")) {
					tkttCount++;
					tkttImporte += det.getImporte();
				} else if (det.getTipo().equals("EMD")) {
					emdCount++;
					emdImporte += det.getImporte();
				}
				cred += det.getTarjeta();

			}
 
  		
  		BizInvoice lastInvoice = null;
  		if (dk.getFormato() != null) {
  			JRecords<BizInvoice> lastInvoices = null;
  			lastInvoices = new JRecords<BizInvoice>(BizInvoice.class);
  			lastInvoices.addFilter("company", acum.getCompany());
  			lastInvoices.addFilter("dk", acum.getDK());
  			lastInvoices.addFilter("formato", dk.getFormato());
  			lastInvoices.addOrderBy("fecha_desde", "DESC");
  			lastInvoices.setTop(1);
  			lastInvoices.dontThrowException(true);
  			JIterator<BizInvoice> it = lastInvoices.getStaticIterator();
  			if (it.hasMoreElements())
  				lastInvoice = it.nextElement();
  		}
  		setHoja(4);
  		setDk(acum.getDK());
  		setDkName(dk.getDescripcion().isEmpty()?acum.getDK():dk.getDescripcion());

  		setCurrency(acum.getMoneda());
  		setFechaInvoice(new Date());
  		setFechaLiquidacion(acum.getFechaLiquidacion());
  		setNumero(lastInvoice == null ? 1 : lastInvoice.getNumero() + 1);
  		setDireccionFrom(lastInvoice == null ? 
  				  "Gotogate International AB<br>"
  				+ "Registration ID: SE556538126501<br>"
  				+ "Dragarbrunnsgatan 46, 6th Floor<br>"
  				+ "Post box 1340<br>"
  				+ "Uppsala, Sweden." : lastInvoice.getDireccionFrom());
  	
  		double bill = 0;
  		BizInvoiceDetail det = new BizInvoiceDetail();
  		det.setCompany(getCompany());
  		det.setNombre("");
  		det.setRuta("Issued Tickets during the Sales Period");
  		det.setServ("MXN");
  		det.setTarifa(tkttCount);
  		det.setOtros(tkttCount==0?0:JTools.rd(tkttImporte/tkttCount,2));
  		det.setTotal(JTools.rd(tkttImporte,2));
  		bill += det.getTotal();
  		getDetalles().addItem(det);
  		
   		det = new BizInvoiceDetail();
  		det.setCompany(getCompany());
  		det.setNombre("");
  		det.setRuta("Issued EMD during the Sales Period");
  		det.setServ("MXN");
  		det.setTarifa(JTools.rd(emdCount,2));
  		det.setOtros(emdCount==0?0:JTools.rd(emdImporte/emdCount,2));
  		det.setTotal(JTools.rd(emdImporte,2));
  		bill += det.getTotal();
  		getDetalles().addItem(det);
  		
   		det = new BizInvoiceDetail();
  		det.setCompany(getCompany());
  		det.setNombre("");
  		det.setRuta("Airline Debit Memo/ Airline Credit Memo");
  		det.setServ("MXN");
   		det.setTarifa(admCount);
  		det.setOtros(admCount==0?0:JTools.rd(admImporte/admCount,2));
  		det.setTotal(JTools.rd(admImporte,2));
   		bill += det.getTotal();
  		getDetalles().addItem(det);
  	
   		det = new BizInvoiceDetail();
  		det.setCompany(getCompany());
  		det.setNombre("");
  		det.setRuta("Refund during the sales period");
  		det.setServ("MXN");
  		det.setTarifa(rfndCount);
  		det.setOtros(rfndCount==0?0:JTools.rd(rfndImporte/rfndCount,2));
  		det.setTotal(JTools.rd(rfndImporte,2));
   		bill += det.getTotal();
  		getDetalles().addItem(det);
    	
   		det = new BizInvoiceDetail();
  		det.setCompany(getCompany());
  		det.setNombre("");
  		det.setRuta("Consolidator fee");
  		det.setServ("MXN");
  		det.setTarifa(feeCount);
  		det.setOtros(feeCount==0?0:JTools.rd(feeImporte/feeCount,2));
  		det.setTotal(JTools.rd(feeImporte,2));
   		bill += det.getTotal();
  		getDetalles().addItem(det);
  		
			JRecords<BizTrxOra> oras = new  JRecords<BizTrxOra>(BizTrxOra.class);
			oras.addFilter("tipo_servicio", "BSP");
			oras.addFilter("tipo_documento", "FCE");
			oras.addFilter("dk", acum.getDK());
			oras.addFilter("fecha_emision", acum.getFechaDesde(),">=");
			oras.addFilter("fecha_emision", acum.getFechaHasta(),"<=");
			double bsp=0;
			int cantBsp=0;
			JIterator<BizTrxOra> it = oras.getStaticIterator();
			while (it.hasMoreElements()) {
				BizTrxOra ora = it.nextElement();
				bsp+=ora.getTotal();
				cantBsp++;
				
			}
  		det = new BizInvoiceDetail();
  		det.setCompany(getCompany());
  		det.setNombre("");
  		det.setRuta("BSP Mensual Invoice");
  		det.setServ("MXN");
  		det.setTarifa(cantBsp);
  		det.setOtros(cantBsp==0?0:JTools.rd(bsp/cantBsp,2));
  		det.setTotal(JTools.rd(bsp,2));
   	
  		bill += det.getTotal();
  		getDetalles().addItem(det);
  		
  		det = new BizInvoiceDetail();
  		det.setCompany(getCompany());
  		det.setNombre("");
  		det.setRuta("Value added Tax (VAT)");
  		det.setServ("MXN");
  		det.setTarifa(0);
  		det.setOtros(0);
  		det.setTotal(0);
  		bill += det.getTotal();
  		getDetalles().addItem(det);
  		
  		setComision(0);
  		setTotal(JTools.rd(bill,2));
  		setPay(JTools.rd(cred,2));
  		setBilling(JTools.rd(bill-getPay(),2));
  		setFormato(dk.getFormato());
  		
  		setBankName(lastInvoice == null ? "BANCO SANTANDER (MEXICO) S.A." : lastInvoice.getBankName() );
  		setBankAddress(lastInvoice == null ? "EDIFICIO CORPORATIVO SANTA FE<br> MODULO 112 PROL. PASEO DE LA REFORMA 500 COL. LOMAS DE SANTA FE<br>01219 MÉXICO " : lastInvoice.getBankAddress() );
  		setAccountName(lastInvoice == null ? "TRAVEL FAN SA DE CV" : lastInvoice.getAccountName() );
  		setAccountCurrency(lastInvoice == null ? "USD" : lastInvoice.getAccountCurrency() );
  		setAccountNumber(lastInvoice == null ? "82500529101" : lastInvoice.getAccountNumber() );
  		setSwift(lastInvoice == null ? "BMSXMXMMXXX" : lastInvoice.getSwift() );
  		setClabe(lastInvoice == null ? "014180825005291015" : lastInvoice.getClabe() );
  				
  	
  	
	}
  public void fillFormatoH5(String company,String zdk, long liqAcumId) throws Exception {
//		new BizMexDetalle().completeDK();
//		new BizPDF().procPendientes(getFechaDesde(),getFechaHasta());

		BizClienteDK dk = new BizClienteDK();
		dk.dontThrowException(true);
		dk.ReadByDK(company, zdk);
		BizLiqAcum acum = new BizLiqAcum();
		acum.dontThrowException(true);
		acum.readById(liqAcumId);
		
		
		
		
		BizInvoice lastInvoice = null;
		if (dk.getFormato() != null) {
			JRecords<BizInvoice> lastInvoices = null;
			lastInvoices = new JRecords<BizInvoice>(BizInvoice.class);
			lastInvoices.addFilter("company", acum.getCompany());
			lastInvoices.addFilter("dk", acum.getDK());
			lastInvoices.addFilter("formato", dk.getFormato());
			lastInvoices.addOrderBy("fecha_desde", "DESC");
			lastInvoices.setTop(1);
			lastInvoices.dontThrowException(true);
			JIterator<BizInvoice> it = lastInvoices.getStaticIterator();
			if (it.hasMoreElements())
				lastInvoice = it.nextElement();
		}
		setHoja(5);
		Calendar cal = Calendar.getInstance();
		cal.setTime(acum.getFechaDesde());
		String invoice=""+cal.get(Calendar.YEAR)+JTools.LPad(""+cal.get(Calendar.MONTH),2,"0")+JTools.LPad(""+cal.get(Calendar.WEEK_OF_MONTH),2,"0");
		setDk(acum.getDK());
		setDkName(dk.getDescripcion().isEmpty()?acum.getDK():dk.getDescripcion());
		setCurrency(acum.getMoneda());
		setFechaInvoice(new Date());
		setFechaLiquidacion(acum.getFechaLiquidacion());
		setDireccion(invoice);
		setDireccionFrom(lastInvoice == null ? 
				 	"  <strong>Kiwi.com s.r.o.</strong><br>"
				  + "  Rohansk&eacute; n&aacute;b&#345;e&#382;&iacute; 678/25<br>"
				  + "  186 00, Prague - Karl&iacute;n<br>"
				  + "  Czech Republic<br>"
				  + "  ID: 29352886<br>"
				  + "  VAT ID: CZ29352886" : lastInvoice.getDireccionFrom());
	
		double bill = 0;
		double cash=0;
 	 	
		BizCotizacionDK cot = getObjCotizacionDK();
		if (cot==null) throw new Exception("No hay cotizacion para el DK "+getDk()+" en la fecha "+JDateTools.dateToStr(getFechaHasta(), "dd/MM/yyyy"));
	
		JRecords<BizLiqDetail> dets = new JRecords<BizLiqDetail>(BizLiqDetail.class);
		dets.addFilter("company", acum.getCompany());
		dets.addFilter("liquidacion_id",acum.getLiquidacionId());
		dets.addFilter("dk",acum.getDK(),"=");
		dets.readAll();
		while (dets.nextRecord()) {
 	 		BizLiqDetail detLiq = dets.getRecord();
	 	 	BizInvoiceDetail det = new BizInvoiceDetail();
			det.setCompany(getCompany());
			det.setNombre(invoice);
			det.setRuta(detLiq.getPrestador());
			det.setCupon(detLiq.getNroBoleto());
			det.setFecha(detLiq.getFechaDoc());
			det.setTarifa(JTools.rd(detLiq.getTarifa()/cot.getCotizacion(),2));
			det.setIVA(JTools.rd(detLiq.getIva()/cot.getCotizacion(),2));
			det.setTUA(JTools.rd(detLiq.getTua()/cot.getCotizacion(),2));
			det.setTotal(JTools.rd(detLiq.getImporte()/cot.getCotizacion(),2));
			det.setServ(detLiq.getFormaPago());
			bill += det.getTotal();
			cash += JTools.rd(detLiq.getContado()/cot.getCotizacion(),2);
			getDetalles().addItem(det);
		}	
		
		
		setComision(0);
		setTotal(JTools.rd(bill,2));
		setPay(JTools.rd(0,2));
		setBilling(JTools.rd(cash,2));
		setFormato(dk.getFormato());
		
					
		setBankName(lastInvoice == null ? "BANCO SANTANDER S.A." : lastInvoice.getBankName() );
		setAccountName(lastInvoice == null ? "TRAVEL FAN SA DE CV" : lastInvoice.getAccountName() );
		setSwift(lastInvoice == null ? "BMSXMXMMXXX" : lastInvoice.getSwift() );
		setClabe(lastInvoice == null ? "0141 8065 5027 09222 55" : lastInvoice.getClabe() );
				
	
	
}
  public void fillFormatoH6(String company,String zdk, long liqAcumId) throws Exception {
		new BizMexDetalle().completeDK();
		new BizPDF().procPendientes(getFechaDesde(),getFechaHasta());

		BizClienteDK dk = new BizClienteDK();
		dk.dontThrowException(true);
		dk.ReadByDK(company, zdk);
		BizLiqAcum acum = new BizLiqAcum();
		acum.dontThrowException(true);
		acum.readById(liqAcumId);
		
		
		
		
		BizInvoice lastInvoice = null;
		if (dk.getFormato() != null) {
			JRecords<BizInvoice> lastInvoices = null;
			lastInvoices = new JRecords<BizInvoice>(BizInvoice.class);
			lastInvoices.addFilter("company", acum.getCompany());
			lastInvoices.addFilter("dk", acum.getDK());
			lastInvoices.addFilter("formato", dk.getFormato());
			lastInvoices.addOrderBy("fecha_desde", "DESC");
			lastInvoices.setTop(1);
			lastInvoices.dontThrowException(true);
			JIterator<BizInvoice> it = lastInvoices.getStaticIterator();
			if (it.hasMoreElements())
				lastInvoice = it.nextElement();
		}
		setHoja(6);
		Calendar cal = Calendar.getInstance();
		cal.setTime(acum.getFechaDesde());
		String invoice=""+cal.get(Calendar.YEAR)+JTools.LPad(""+cal.get(Calendar.MONTH),2,"0")+JTools.LPad(""+cal.get(Calendar.WEEK_OF_MONTH),2,"0");
		setDk(acum.getDK());
		setDkName(dk.getDescripcion().isEmpty()?acum.getDK():dk.getDescripcion());
		setCurrency(acum.getMoneda());
		setFechaInvoice(new Date());
		setFechaDesde(getFechaDesde());
		setFechaHasta(getFechaHasta());
		setFechaLiquidacion(getFechaLiquidacion());
		setFechaInvoice(getFechaInvoice());
		setFechaLiquidacion(acum.getFechaLiquidacion());
		setDireccion(invoice);
		setDireccionFrom(lastInvoice == null ? 
				  "BEIJING YIQUFEI BUSINESS TOURISM<br>"
				  + "MANAGEMENT CO LTD.<br>"
				  + "5TH FLOOR, BUILDING 19, KECHUANG 13TH<br>"
				  + "STREET, BEIJING ECONOMIC-TECHNOLOGICAL<br>"
				  + "DEVELOPMENT AREA, DAXING DISTRICT, BEIJING." : lastInvoice.getDireccionFrom());
	
		double bill = acum.getTotalFacturado();
		double cash=acum.getTotalContado();
 	 	
		BizCotizacionDK cot = getObjCotizacionDK();
		if (cot==null) throw new Exception("No hay cotizacion para el DK "+getDk()+" en la fecha "+JDateTools.dateToStr(getFechaHasta(), "dd/MM/yyyy"));
		setComision(0);
		setTotal(JTools.rd(bill/cot.getCotizacion(),2));
		setPay(JTools.rd(0,2));
		setBilling(JTools.rd(cash/cot.getCotizacion(),2));
		setFormato(dk.getFormato());
		
						
		setBankName(lastInvoice == null ? "BANCO SANTANDER (MEXICO) S.A." : lastInvoice.getBankName() );
		setBankAddress(lastInvoice == null ? "EDIFICIO CORPORATIVO SANTA FE<br> MODULO 112 PROL. PASEO DE LA REFORMA 500 COL. LOMAS DE SANTA FE<br>01219 MÉXICO " : lastInvoice.getBankAddress() );
		setAccountName(lastInvoice == null ? "TRAVEL FAN SA DE CV" : lastInvoice.getAccountName() );
		setAccountCurrency(lastInvoice == null ? "USD" : lastInvoice.getAccountCurrency() );
		setAccountNumber(lastInvoice == null ? "82500529101" : lastInvoice.getAccountNumber() );
		setSwift(lastInvoice == null ? "BMSXMXMMXXX" : lastInvoice.getSwift() );
		setClabe(lastInvoice == null ? "0141808250029115" : lastInvoice.getClabe() );
		
	
}
  public void fillFormatoH7(String company,String zdk, long liqAcumId) throws Exception {
		new BizMexDetalle().completeDK();
		new BizPDF().procPendientes(getFechaDesde(),getFechaHasta());

		BizClienteDK dk = new BizClienteDK();
		dk.dontThrowException(true);
		dk.ReadByDK(company, zdk);
		BizLiqAcum acum = new BizLiqAcum();
		acum.dontThrowException(true);
		acum.readById(liqAcumId);
		
		
		
		
		BizInvoice lastInvoice = null;
		if (dk.getFormato() != null) {
			JRecords<BizInvoice> lastInvoices = null;
			lastInvoices = new JRecords<BizInvoice>(BizInvoice.class);
			lastInvoices.addFilter("company", acum.getCompany());
			lastInvoices.addFilter("dk", acum.getDK());
			lastInvoices.addFilter("formato", dk.getFormato());
			lastInvoices.addOrderBy("fecha_desde", "DESC");
			lastInvoices.setTop(1);
			lastInvoices.dontThrowException(true);
			JIterator<BizInvoice> it = lastInvoices.getStaticIterator();
			if (it.hasMoreElements())
				lastInvoice = it.nextElement();
		}
		setHoja(7);
		Calendar cal = Calendar.getInstance();
		cal.setTime(acum.getFechaDesde());
		String invoice=""+cal.get(Calendar.YEAR)+JTools.LPad(""+cal.get(Calendar.MONTH),2,"0")+JTools.LPad(""+cal.get(Calendar.WEEK_OF_MONTH),2,"0");
		setDk(acum.getDK());
		setDkName(dk.getDescripcion().isEmpty()?acum.getDK():dk.getDescripcion());
		setCurrency(acum.getMoneda());
		setFechaInvoice(new Date());
		setFechaDesde(getFechaDesde());
		setFechaHasta(getFechaHasta());
		setFechaLiquidacion(getFechaLiquidacion());
		setFechaInvoice(getFechaInvoice());
		setFechaLiquidacion(acum.getFechaLiquidacion());
		setDireccion(invoice);
		setDireccionFrom(lastInvoice == null ? 
				  "SHENZHEN HANG LU TRAVEL<BR>"
				  + "TECHNOLOGY CO LTD<BR>"
				  + "ROOM 525 No 9 KEJI ROAD HIGH TECH<BR>"
				  + "PARK" : lastInvoice.getDireccionFrom());
	
		double bill = acum.getTotalFacturado();
		double cash=acum.getTotalContado();
 		 	
		BizCotizacionDK cot = getObjCotizacionDK();
		if (cot==null) throw new Exception("No hay cotizacion para el DK "+getDk()+" en la fecha "+JDateTools.dateToStr(getFechaHasta(), "dd/MM/yyyy"));
		setComision(0);
		setTotal(JTools.rd(bill/cot.getCotizacion(),2));
		setPay(JTools.rd(0,2));
		setBilling(JTools.rd(cash/cot.getCotizacion(),2));
		setFormato(dk.getFormato());
		
			
		
	
}
  BizCotizacionDK objCotiz;
  public BizCotizacionDK getObjCotizacionDK() throws Exception {
  	if (objCotiz!=null) return objCotiz;
  	BizCotizacionDK obj = new BizCotizacionDK();
  	obj.dontThrowException(true);
  	if (!obj.ReadByDk(getCompany(),getDk(),getFechaHasta()))
  		return null;
  	return objCotiz=obj;
  }
}
