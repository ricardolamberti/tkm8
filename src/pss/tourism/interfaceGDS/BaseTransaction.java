package pss.tourism.interfaceGDS;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pss.bsp.bspBusiness.BizBSPCompany;
import pss.bsp.reembolsos.BizReembolso;
import pss.common.regions.company.BizCompany;
import pss.common.regions.currency.history.BizMonedaCotizacion;
import pss.common.regions.divitions.BizPaisLista;
import pss.core.services.records.JRecords;
import pss.core.tools.GeoPosition;
import pss.core.tools.JDateTools;
import pss.core.tools.JTools;
import pss.core.tools.PssLogger;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JList;
import pss.core.tools.collections.JMap;
import pss.tourism.airports.BizAirport;
import pss.tourism.carrier.BizCarrier;
import pss.tourism.interfaceGDS.FareRecord.ConnectionRecord;
import pss.tourism.pnr.BizPNRFare;
import pss.tourism.pnr.BizPNRFilename;
import pss.tourism.pnr.BizPNROtro;
import pss.tourism.pnr.BizPNRSegmentoAereo;
import pss.tourism.pnr.BizPNRTax;
import pss.tourism.pnr.BizPNRTicket;
import pss.tourism.pnr.BizTicketsFaltantes;
import pss.tourism.voidManual.BizVoidManual;

public class BaseTransaction {

	protected String IATA = "";
	protected int segments = 0;

	protected JMap<String, Object> mRecords = null;

	private String pnrLocator;
	protected String pnrOrigen;
	protected String pnrFile = null;
	protected long year;

	protected boolean isEMD = false;
	protected boolean isReissued = false;
	protected String endorsement=null;

	protected BizPNRTicket pnrTicket;

	protected JMap<String, Commission> mComis = JCollectionFactory.createMap();
	protected JMap<String, Fare> mFares = JCollectionFactory.createMap();
	protected JMap<String, Passenger> mPass = JCollectionFactory.createMap();
	protected JMap<String, Pay> mPays = JCollectionFactory.createMap();
	protected JMap<String, EMD> mEmds = JCollectionFactory.createMap();
	
	protected JMap<String, Fare> mOFares = JCollectionFactory.createMap();
	protected boolean reprocess;
	

	public boolean isReprocess() {
		return reprocess;
	}

	public void setReprocess(boolean reprocess) {
		this.reprocess = reprocess;
	}

	protected JMap<String, JMap<String, TaxCode>> mTaxes = JCollectionFactory.createMap();

	protected class EMD {
		public EMD() {
		}

		public String totalCurrency;
		public String netCurrency;
		public double exchangeRate=0;
		public double baseFare=0;
		public double fareWithTax=0;
		public double fare=0;
		public double netFare=0;
		public double taxAmount=0;
		public String taxCurrency;
		public String taxCode;
		public double taxAmount2=0;
		public String taxCode2;
		public double taxAmount3=0;
		public String taxCode3;
		public String carrier;
	
		
	};

	// t.setCodigoBaseMoneda(emd.getNetCurrency());
	// t.setCodigoMoneda(emd.getTotalCurrency());
	// if (emd.getNetFare()!=0)
	// t.setTipoCambio(emd.getFare()/emd.getNetFare());
	// t.setTarifaBase(emd.getNetFare());
	// t.setTarifaBaseFactura(emd.getNetFare());
	// t.setTarifaBaseConImpuestos(emd.getTotalFare());
	// t.setTarifa(emd.getFare());

	protected class Commission {
		public Commission() {
		}

		public double fare;
		public double amount;
		public double percentaje;
	};

	
	protected class Pay {
		public Pay() {
		}

		public String card;
		public String auth;
		public String cardName;
		public double amount;
	};

	protected class TaxCode {
		public TaxCode() {
		}

		public String currency;
		public double amount;
	};

	protected class Fare {
		public Fare() {
		}

		public String totalCurrency;
		public String baseCurrency;
		public double exchangeRate;
		public double baseFare;
		public double baseFareWithTax;
		public double fare;
		public double YQFare;
		public boolean isEMD;

		public String fee;
		public String costoFee;
		public String ordenFee;
		public String pagoFee;
		public String centroCosto;
		public String solicitante;
		public String cliente;
		public String consumo;
		public String mail;
	}

	protected class Passenger {
		public Passenger() {
		}

		public String passengerName;
		public String passengerIdentFiscal;
		public String passengerType;
	}

	public long getYear() {
		return year;
	}

	public void setYear(long year) {
		this.year = year;
	}

	/**
	 * @return the pnrFile
	 */
	public String getPnrFile() {
		return pnrFile;
	}

	public void setPnrLocator(String value) {
		this.pnrLocator = value;
	}

	public String getPnrLocator() {
		return this.pnrLocator;
	}

	/**
	 * @param pnrFile
	 *          the pnrFile to set
	 */
	public void setPnrFile(String pnrFile) {
		this.pnrFile = pnrFile;
	}

	protected Long interfaceId;

	protected String company = null;
	protected String origen = null;

	public String getOrigen() {
		return origen;
	}

	public void setOrigen(String origen) {
		this.origen = origen;
	}

	protected String gds = null;

	public String getGds() {
		return gds;
	}

	public void setGds(String gds) {
		this.gds = gds;
	}

	// protected BizTourismConfig cfg;

	// protected BizPNR pnr;

	public Long getInterfaceId() {
		return interfaceId;
	}

	public void setInterfaceId(Long interfaceId) {
		this.interfaceId = interfaceId;
	}

	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}

	public String getAgReserva() {
		return agreserva;
	}

	public void setAgReserva(String officeId) {
		this.agreserva = officeId;
	}

	public Date getPNRDate() {
		return pnrDate;
	}

	public void setPNRDate(Date creationDate) {
		this.pnrDate = creationDate;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getCreationDateAIR() {
		return creationDateAIR;
	}

	public void setCreationDateAIR(Date creationDateAIR) {
		this.creationDateAIR = creationDateAIR;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getCountTickets() {
		return countTickets;
	}

	public void setCountTickets(String countTickets) {
		this.countTickets = countTickets;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getRoute() {
		return route;
	}

	public void setRoute(String route) {
		this.route = route;
	}

	public Date getDeparture() {
		return departure;
	}

	public void setDeparture(Date departure) {
		this.departure = departure;
	}

	public String getTxType() {
		return txType;
	}

	public void setTxType(String txType) {
		this.txType = txType;
	}

	public String getDuplicated() {
		return duplicated;
	}

	public void setDuplicated(String duplicated) {
		this.duplicated = duplicated;
	}

	public void setIATA(String iATA) {
		IATA = iATA;
	}

	protected String officeId;
	protected String agreserva;
	protected String saleIndicator;
	protected Date pnrDate;
	protected Date creationDate;
	protected Date creationDateAIR;
	protected String custId;
	protected String countTickets;
	protected String version;
	protected String route;
	protected Date departure;
	protected String txType;
	protected String duplicated;
	protected String tipoPrestacion;
	private String custIdReduced;
	private String departamento;
	private String sucursal;

	public String getTipoPrestacion() {
		return tipoPrestacion;
	}

	public void setTipoPrestacion(String tipoPrestacion) {
		this.tipoPrestacion = tipoPrestacion;
	}
	protected void checkAerolinea(String aerolinea) throws Exception {
		if (aerolinea==null) return;
		if (aerolinea.equals("")) return;
		if (aerolinea.length()>10) 
			return;
		BizCarrier carr = new BizCarrier();
		carr.dontThrowException(true);
		if (!carr.Read(aerolinea)) {
			BizCarrier carrNew = new BizCarrier();
			carrNew.setCarrier(aerolinea);
			carrNew.setDescription("Codigo aerolinea "+aerolinea);
			carrNew.setCodAnalisis(BizCarrier.SIN_LOGICA);
						carrNew.processInsert();
		}
		return;
	}
	protected void setTarifasEnDolares(BizPNRTicket tk) throws Exception {
		double cot = BizMonedaCotizacion.readCotizacionCorrienteAt(tk.getCompany(), tk.getObjCompany().getCountry(), "USD", tk.getCodigoMoneda(), tk.getCreationDate());
		if (cot == 0 && tk.getCodigoMoneda().equals("USD"))
			cot = 1;
	  double YQUsa=0;
	  
		if (tk.getCodigoMoneda().equals("USD")) {
			tk.setTarifaUsa(tk.getTarifa());
			if (cot != 0) {
				tk.setTarifaFacturaDolares(JTools.rd(tk.getTarifaFactura() / cot, 2));
				tk.setNetoFacturaDolares(JTools.rd(tk.getNetoFactura() / cot, 2));
				tk.setTarifaTotalFacturaDolares(JTools.rd(tk.getTarifaTotalFactura() / cot, 2));
				tk.setTarifaNetoUsa(JTools.rd(tk.getNeto() / cot, 2));
				tk.setQUsd(JTools.rd(tk.getQ() / cot, 2));
				YQUsa=(tk.getYQ() / cot);
				tk.setTarifaNetoYQUsa(JTools.rd(tk.getTarifaNetoUsa() + YQUsa, 2));
				tk.setTarifaBaseYQUsa(JTools.rd(tk.getTarifaUsa() + YQUsa, 2));
				tk.setTarifaFacturadaYQUsa(JTools.rd(tk.getTarifaFacturaDolares() + YQUsa, 2));
	//			tk.setTarifaFacturadaYQ(JTools.rd(tk.getTarifaFactura() + (tk.getYQ() / cot), 2));
			}
		} else if (tk.getCodigoBaseMoneda().equals("USD")) {
			if (cot != 0) {
				tk.setTarifaUsa(JTools.rd(tk.getTarifa() / cot, 2));// tk.getTarifaBase
				tk.setTarifaFacturaDolares(JTools.rd(tk.getTarifaFactura() / cot, 2));
				tk.setNetoFacturaDolares(JTools.rd(tk.getNetoFactura() / cot, 2));
				tk.setTarifaTotalFacturaDolares(JTools.rd(tk.getTarifaTotalFactura() / cot, 2));
				tk.setTarifaNetoUsa(JTools.rd(tk.getNeto() / cot, 2));
				tk.setQUsd(JTools.rd(tk.getQ() / cot, 2));
				YQUsa=(tk.getYQ() / cot);
				tk.setTarifaNetoYQUsa(JTools.rd(tk.getTarifaNetoUsa() +YQUsa, 2));
				tk.setTarifaBaseYQUsa(JTools.rd(tk.getTarifaUsa() + YQUsa, 2));
				tk.setTarifaFacturadaYQUsa(JTools.rd(tk.getTarifaFacturaDolares() +YQUsa, 2));
	//			tk.setTarifaFacturadaYQ(JTools.rd(tk.getTarifaFactura() + (tk.getYQ() / cot), 2));
			}
		} else {
			if (cot==0) {
				String curr = tk.getCodigoMoneda();
				String currLocal = tk.getCodigoMonedaLocal();
				double cotBase = curr.equals("USD")?1:BizMonedaCotizacion.readCotizacionCorrienteAt(tk.getCompany(), tk.getObjCompany().getCountry(), curr,currLocal, tk.getCreationDate());
//				double cotLocal = currLocal.equals("USD")?1:BizMonedaCotizacion.readCotizacionCorrienteAt(tk.getCompany(), tk.getObjCompany().getCountry(), "USD",currLocal, tk.getCreationDate());
				
				if (cotBase==0) {
					cot= BizMonedaCotizacion.readCotizacionCorrienteAt(tk.getCompany(), tk.getObjCompany().getCountry(), curr, currLocal, tk.getCreationDate());
				} else {
//					cotBase = cotBase==0?1:cotBase;
//					cotLocal = cotLocal==0?1:cotLocal;
					cot = cotBase;
					
				}

			}
			if (cot != 0) {
				tk.setTarifaFacturaDolares(JTools.rd(tk.getTarifaFactura() / cot, 2));
				tk.setNetoFacturaDolares(JTools.rd(tk.getNetoFactura() / cot, 2));
				tk.setTarifaTotalFacturaDolares(JTools.rd(tk.getTarifaTotalFactura() / cot, 2));
				tk.setTarifaUsa(JTools.rd(tk.getTarifa() / cot, 2));
				tk.setTarifaNetoUsa(JTools.rd(tk.getNeto() / cot, 2));
				tk.setQUsd(JTools.rd(tk.getQ() / cot, 2));
				YQUsa=(tk.getYQ() / cot);
				tk.setTarifaNetoYQUsa(JTools.rd(tk.getTarifaNetoUsa() + YQUsa, 2));
				tk.setTarifaBaseYQUsa(JTools.rd(tk.getTarifaUsa() + YQUsa, 2));
				tk.setTarifaFacturadaYQUsa(JTools.rd(tk.getTarifaFacturaDolares() + YQUsa, 2));
	//			tk.setTarifaFacturadaYQ(JTools.rd(tk.getTarifaFactura() + (tk.getYQ() / cot), 2));
			} 
			

		}

		if (tk.getCodigoMonedaLocal()!=null && !tk.getCodigoMonedaLocal().equals("") && !tk.getCodigoMonedaLocal().equals(tk.getCodigoMoneda())) {
			double cotLocal = BizMonedaCotizacion.readCotizacionCorrienteAt(tk.getCompany(), tk.getObjCompany().getCountry(), "USD",tk.getCodigoMonedaLocal(), tk.getCreationDate());
			if (cotLocal == 0)
				cotLocal = 1;
			else 
				cotLocal = 1f/cotLocal;

			tk.setTarifaLocal(JTools.rd(tk.getTarifaUsa() / cotLocal, 2));
			tk.setTarifaFacturaLocal(JTools.rd(tk.getTarifaFacturaDolares() / cotLocal, 2));
			tk.setNetoFacturaLocal(JTools.rd(tk.getNetoFacturaDolares() / cotLocal, 2));
			tk.setTarifaTotalFacturaLocal(JTools.rd(tk.getTarifaTotalFacturaDolares() / cotLocal, 2));
			tk.setTarifaNetoLocal(JTools.rd(tk.getTarifaNetoUsa() / cotLocal, 2));
			tk.setTarifaNetoYQLocal(JTools.rd(tk.getTarifaNetoLocal() + (YQUsa / cotLocal), 2));
			tk.setTarifaBaseYQLocal(JTools.rd(tk.getTarifaLocal() + (YQUsa / cotLocal), 2));
			tk.setTarifaFacturadaYQLocal(JTools.rd(tk.getTarifaFacturaLocal() + (YQUsa / cotLocal), 2));
			tk.setQLocal(JTools.rd(tk.getQ() / cotLocal, 2));
		} else {
			tk.setTarifaLocal(tk.getTarifa() );
			tk.setTarifaFacturaLocal(tk.getTarifaFactura());
			tk.setNetoFacturaLocal(tk.getNetoFactura());
			tk.setTarifaTotalFacturaLocal(tk.getTarifaTotalFactura());
			tk.setTarifaNetoLocal(tk.getNeto());
			tk.setTarifaNetoYQLocal(tk.getNetoFactura() + tk.getYQ());
			tk.setTarifaBaseYQLocal(tk.getTarifaBaseFactura() + tk.getYQ());
			tk.setTarifaFacturadaYQLocal(tk.getTarifaFactura() + tk.getYQ());
			tk.setQLocal(tk.getQ());
		}
		
		

	}

	protected void setTarifasEnDolares(BizPNROtro tk) throws Exception {
		double cot = BizMonedaCotizacion.readCotizacionCorrienteAt(tk.getCompany(), tk.getObjCompany().getCountry(), "USD", tk.getMonedaImporte(), tk.getFechaReserva());
		if (cot == 0 && tk.getMonedaImporte().equals("USD"))
			cot = 1;

		if (tk.getMonedaImporte().equals("USD")) {
			tk.setImporteUsd(tk.getImporte());
			tk.setImporteBaseUsd(tk.getImporte());
			tk.setPrecioTotalUsd(tk.getPrecioTotal() );
		} else if (tk.getMonedaBase().equals("USD")) {
			if (cot != 0) {
				tk.setImporteUsd(JTools.rd(tk.getImporteBase() / cot, 2));
				tk.setImporteBaseUsd(JTools.rd(tk.getImporte() / cot, 2));
				tk.setPrecioTotalUsd(JTools.rd(tk.getPrecioTotal() / cot, 2));
			}
		} else {
			if (cot != 0) {
				tk.setImporteUsd(JTools.rd(tk.getImporte() / cot, 2));
				tk.setImporteBaseUsd(JTools.rd(tk.getImporte() / cot, 2));
				tk.setPrecioTotalUsd(JTools.rd(tk.getPrecioTotal() / cot, 2));
			}

		}

		if (tk.getMonedaTasas().equals("USD")) {
			tk.setTasasUsd(tk.getTasas() );
		} else {
			cot = BizMonedaCotizacion.readCotizacionCorrienteAt(tk.getCompany(), tk.getObjCompany().getCountry(), "USD", tk.getMonedaTasas(), tk.getFechaReserva());
			if (cot == 0 && tk.getMonedaImporte().equals("USD"))
				cot = 1;
			if (cot != 0) {
				tk.setTasasUsd(JTools.rd(tk.getTasas() / cot, 2));
			}

		}
		if (tk.getMonedaFee().equals("USD")) {
			tk.setFeeUsd(tk.getFee() );
		} else {
			cot = BizMonedaCotizacion.readCotizacionCorrienteAt(tk.getCompany(), tk.getObjCompany().getCountry(), "USD", tk.getMonedaFee(), tk.getFechaReserva());
			if (cot == 0 && tk.getMonedaFee().equals("USD"))
				cot = 1;
			if (cot != 0) {
				tk.setFeeUsd(JTools.rd(tk.getFee() / cot, 2));
			}

		}

		if (tk.getMonedaLocal()!=null && !tk.getMonedaLocal().equals("") && !tk.getMonedaLocal().equals(tk.getMonedaImporte())) {
			double cotLocal = BizMonedaCotizacion.readCotizacionCorrienteAt(tk.getCompany(), tk.getObjCompany().getCountry(), "USD",tk.getMonedaLocal(), tk.getFechaReserva());
			if (cotLocal == 0)
				cotLocal = 1;
			else 
				cotLocal = 1f/cotLocal;
			tk.setImporteLocal(JTools.rd(tk.getImporteUsd() / cotLocal, 2));
			tk.setImporteBaseLocal(JTools.rd(tk.getImporteUsd() / cotLocal, 2));
			tk.setTasasLocal(JTools.rd(tk.getTasasUsd() / cotLocal, 2));
			tk.setFeeLocal(JTools.rd(tk.getFeeUsd() / cotLocal, 2));
			tk.setPrecioTotalLocal(JTools.rd(tk.getPrecioTotalUsd() / cotLocal, 2));
		} else {
			tk.setImporteLocal(tk.getImporte());
			tk.setImporteBaseLocal(tk.getImporte());
			tk.setTasasLocal(tk.getTasas() );
			tk.setFeeLocal(tk.getFee() );
			tk.setPrecioTotalLocal(tk.getPrecioTotal() );
			
		}

	}
	/**
	 * @param update
	 * @return
	 * @throws Exception
	 */
	protected boolean checkPNR(boolean update) throws Exception {
		// if (pnr == null) {
		// pnr = new BizPNR();
		// update = false;
		// } else {
		// interfaceId = pnr.getId();
		// }
		// return update;
		return false;
	}

	// public BizPNR isAlreadyProcessed() throws Exception {
	// pnr = new BizPNR();
	// pnr.dontThrowException(true);
	// pnr.addFilter("company", company);
	// pnr.addFilter("codigopnr", pnrLocator);
	// if (pnr.read())
	// return pnr;
	// return null;
	// }
	public String getFechaEmision() throws Exception {
		return null;
	}

	public String getIATA() throws Exception {
		return null;
	}

	/**
	 * @return the company
	 */
	public String getCompany() {
		return company;
	}

	/**
	 * @param company
	 *          the company to set
	 */
	public void setCompany(String company) {
		this.company = company;
	}

	public String getDirectory() {

		return getCompany() + "/" + getGds() + "/" + getPnrLocator().substring(0, 2);
	}

	protected boolean paymentExists(String ticket, String paymode) throws Exception {
		// BizPNRPayment pp = new BizPNRPayment();
		// pp.dontThrowException(true);
		// return pp.Read(company, pnrLocator, ticket, paymode);
		return false;
	}

	/**
	 * @param ticket
	 * @throws Exception
	 */
	protected boolean priceExists(String ticket) throws Exception {
		// BizPNRPrice pp = new BizPNRPrice();
		// pp.dontThrowException(true);
		// return pp.Read(company, pnrLocator, ticket);
		return false;
	}

	// protected void updatePrice(BizPNRPrice pp, String curr, String totalcurr,
	// String ticket, double netamount, double base, double tarifa,
	// String exchangerate) throws Exception {
	// pp.setCompany(company);
	// pp.setId(interfaceId);
	// pp.setCodigoPNR(pnrLocator);
	// pp.setSecuencia(1);
	// pp.setNumeroBoleto(ticket);
	// pp.setCodigoMoneda(totalcurr);
	// pp.setCodigoBaseMoneda(curr);
	// pp.setTipoDeCambio(exchangerate);
	// pp.setTarifaBase(base + "");
	// pp.setTarifaConImpuestos(netamount + "");
	// pp.setTarifa(tarifa + "");
	// pp.update();
	// }

	protected void savePrice(boolean update, String curr, String totalcurr, String ticket, double netamount, double base, double tarifa, String exchangerate) throws Exception {

		// BizPNRPrice pp = new BizPNRPrice();
		// pp.setCompany(company);
		// pp.setId(interfaceId);
		// pp.setCodigoPNR(pnrLocator);
		// pp.setSecuencia(1);
		// pp.setNumeroBoleto(ticket);
		//
		// if ( curr == null )
		// curr = totalcurr;
		//
		//
		// pp.setCodigoMoneda(totalcurr);
		// pp.setCodigoBaseMoneda(curr);
		// pp.setTipoDeCambio(exchangerate);
		// pp.setTarifaBase(base + "");
		// pp.setTarifaConImpuestos(netamount + "");
		// pp.setTarifa(tarifa + "");
		// if (update)
		// pp.update();
		// else
		// pp.insert();
	}

	/**
	 * @param m5
	 * @param curr
	 * @param ticket
	 * @return
	 * @throws Exception
	 */
	protected void savePayment(boolean update, String amount, String type, String creditcard, String curr, String ticket) throws Exception {
		// BizPNRPayment pp = new BizPNRPayment();
		// pp.setCompany(company);
		// pp.setId(interfaceId);
		// pp.setCodigopnr(pnrLocator);
		// pp.setSecuencia(1);
		// pp.setNumeroboleto(ticket);
		// pp.setFormapago(type);
		// pp.setNumerotarjeta(creditcard);
		// pp.setCodigomoneda(curr);
		// String am1 = amount;
		// pp.setImporte(am1 + "");
		// if (update)
		// pp.update();
		// else
		// pp.insert();
	}

	/**
	 * @param i
	 * @param ticket
	 * @param commission
	 * @return
	 * @throws Exception
	 */
	protected void saveCommision(boolean exist, String ticket, double commission, double percentaje) throws Exception {
		// BizPNRComision pp = new BizPNRComision();
		// pp.setCompany(company);
		// pp.setId(interfaceId);
		// pp.setCodigopnr(pnrLocator);
		// pp.setSecuencia(1);
		// pp.setNumeroboleto(ticket);
		// // pp.setSegmento((i + 1) + "");
		// pp.setImporte(commission + "");
		// pp.setPorcentaje(percentaje + "");
		// if (exist)
		// pp.update();
		// else
		// pp.insert();
	}

	protected boolean commisionExists(String ticket) throws Exception {
		// BizPNRComision pp = new BizPNRComision();
		// pp.dontThrowException(true);
		// return pp.Read(company, pnrLocator, ticket);
		return false;
	}

	protected EMD getEMDbyID() {

		return null;
	}

	protected void assignSumTaxes(BizPNRTicket t) throws Exception {
		String imp = (BizBSPCompany.getConfigView(t.getCompany()).getTipoImpuesto());
		double sumTax = 0;
		double sumIva = 0;
		t.setImpuestos(0);
		t.setIva(0);

		if (isEMD) {
			EMD emd = (EMD) getEMDbyID();
			if (emd == null)
				return;

//			JMap<String, JMap<String, TaxCode>> mTaxes = JCollectionFactory.createMap();
//
//			JMap<String, TaxCode> taxes = JCollectionFactory.createMap();
			JMap<String, TaxCode> taxes = mTaxes.getElement(t.getNumeroboleto());
			if (taxes==null)
				taxes = JCollectionFactory.createMap();
		
			TaxCode tx ;
			if (emd.taxAmount>0) {
				tx = new TaxCode();
				tx.amount = emd.taxAmount;
				tx.currency = emd.taxCurrency;
				taxes.addElement(emd.taxCode, tx);
				if ((","+imp+",").indexOf(","+emd.taxCode+",")!=-1)
					sumIva += tx.amount;
				else
					sumTax += tx.amount;

			} 
	
			mTaxes.addElement(t.getNumeroboleto(), taxes);
			t.setIvaFactura(sumIva);
			t.setIva(sumIva);
			t.setImpuestoFactura(JTools.forceRd(sumTax,2));
			t.setImpuestosTotalFactura(JTools.forceRd(sumTax+sumIva,2));
			t.setImpuestos(JTools.forceRd(sumTax,2));
			t.setImpuestosTotal(JTools.forceRd(sumTax+sumIva,2));

			return;
		}


		JMap<String, TaxCode> taxes = mTaxes.getElement(t.getNumeroboleto());

		if (taxes == null)
			return;

		JIterator<String> kit = taxes.getKeyIterator();
		JIterator<TaxCode> vit = taxes.getValueIterator();

		t.setYQ(0);
		while (kit.hasMoreElements()) {
			String code = kit.nextElement();
			TaxCode value = vit.nextElement();

			if (code == null)
				continue;

			if ((","+imp+",").indexOf(","+code+",")!=-1)
				sumIva += value.amount;
			else
				sumTax += value.amount;

			if (code.equals("YQ"))
				t.setYQ(JTools.forceRd(value.amount,2));
		}
		t.setImpuestoFactura(JTools.forceRd(sumTax,2));
		t.setImpuestosTotalFactura(JTools.forceRd(sumTax + sumIva,2));
		t.setIvaFactura(JTools.forceRd(sumIva,2));
		t.setImpuestos(JTools.forceRd(sumTax,2));
		t.setIva(JTools.forceRd(sumIva,2));
		t.setImpuestosTotal(JTools.forceRd(sumTax + sumIva,2));

	}

	protected void saveTicket(String ticket, boolean isinternational, String carriercode, String paxid, String tickettype, String connecteditems, String originalTicket) throws Exception {
		this.saveTicket(ticket, isinternational, carriercode, paxid, tickettype, connecteditems, originalTicket, null);
	}

	protected void saveTicket(String ticket, boolean isinternational, String carriercode, String paxid, String tickettype, String connecteditems, String originalTicket, String tourCode) throws Exception {

		BizPNRTicket t = new BizPNRTicket();
		t.setNumeroboleto(ticket);
		t.setCompany(company);
			
				// t.setTourCode(tourcode); agregar tour code!!!!!
		t.setCodigoaerolinea(carriercode);
		t.setNumeropasajero(paxid);
		t.setTipoboleto(tickettype);
		t.setCantidadconectados(connecteditems);
		t.setDirectory(getDirectory());
		t.setFechaProcesamiento(new Date());
		t.setInternacional(isinternational);
		t.setTourCode(tourCode);
		t.setInternacionalDescr(t.isInternacional() ? "International" : "Domestic");
		if (endorsement!=null)
			t.setEndoso(endorsement);

		t.setComisionFactura(0);
		t.setComisionAmount(0);
		t.setComisionPerc(0);
		if (mComis.getElement(ticket) != null && isEMD == false) {
			t.setComisionFactura(JTools.forceRd(mComis.getElement(ticket).amount,2));
			t.setComisionAmount(JTools.forceRd(mComis.getElement(ticket).amount,2));
			t.setComisionPerc(JTools.forceRd(mComis.getElement(ticket).percentaje,2));
		}
		assignFareFrom(t);
		assignCommon(t);
		assignPays(t);
		assignSumTaxes(t);

		t.setNeto(JTools.forceRd(t.getTarifa() - t.getComisionAmount(),2));
		t.setNetoFactura(JTools.forceRd(t.getNeto(),2));
		t.setTarifaFactura(JTools.forceRd(t.getTarifa(),2));
		t.setTarifaTotalFactura(JTools.forceRd(t.getTarifaBaseConImpuestos(),2));
		t.setImpuestoFactura(JTools.forceRd(t.getImpuestos(),2));
		t.setComisionFactura(JTools.forceRd(t.getComisionAmount(),2));

		// tk.setIvaFactura(tk.getIva());
		// tk.setNetoFactura(tk.getNeto());
		// tk.setTarifaBaseFactura(tk.getTarifaBase());
		// tk.setTarifaFactura(tk.getTarifa());
		// tk.setTarifaTotalFactura(tk.getTarifaBaseConImpuestos());
		// tk.setImpuestoFactura(tk.getImpuestos());
		// tk.setComisionFactura(tk.getComisionAmount());
		// tk.setTipoOperacion(m5.getTipoOperacion());

		t.setReemitted(false);
		t.setEmision(true);
		t.setCodigoMonedaLocal(BizCompany.getObjCompany(t.getCompany()).getCurrency());
		

		setTarifasEnDolares(t);

		if (isReissued && originalTicket != null) { // esto deberia cambiar como
																								// esta en SABRE
			BizPNRTicket pnrOriginal = findTicketFromBoleto(getCompany(), originalTicket);
			if (pnrOriginal != null) {
				double cotiz=1;
				if (!t.getCodigoMoneda().equals("") &&!pnrOriginal.getCodigoMoneda().equals(t.getCodigoMoneda())) {
					String curr = pnrOriginal.getCodigoMoneda();
					String currLocal = t.getCodigoMoneda();
					double cotBase = curr.equals("USD")?1:BizMonedaCotizacion.readCotizacionCorrienteAt(t.getCompany(), t.getObjCompany().getCountry(), curr,currLocal, t.getCreationDate());
					
					if (cotBase==0) {
						cotBase = curr.equals("USD")?1:BizMonedaCotizacion.readCotizacionCorrienteAt(t.getCompany(), t.getObjCompany().getCountry(), "USD",curr, t.getCreationDate());
						double cotLocal = currLocal.equals("USD")?1:BizMonedaCotizacion.readCotizacionCorrienteAt(t.getCompany(), t.getObjCompany().getCountry(), "USD",currLocal, t.getCreationDate());
						cotBase = cotBase==0?1:cotBase;
						cotLocal = cotLocal==0?1:cotLocal;
						cotiz = cotLocal/cotBase;
					} else {		
						cotiz = cotBase;
					}	
				}
//				t.setCreationDate(pnrOriginal.getCreationDate());
				pnrOriginal.processReemitir(true);
				t.setCodigoMoneda(pnrOriginal.getCodigoMoneda());
				t.setCodigoBaseMoneda(pnrOriginal.getCodigoBaseMoneda());
				t.setRefOriginal(pnrOriginal.getId());
				t.setNetoFactura(JTools.forceRd(t.getNeto(),2));
				t.setNeto(JTools.forceRd(t.getNeto() + pnrOriginal.getNeto(),2));
				t.setTarifaFactura(JTools.forceRd(t.getTarifa(),2));
				t.setTarifa(JTools.forceRd(t.getTarifa() + (pnrOriginal.getTarifa()*cotiz),2));
				t.setTarifaYQ(JTools.forceRd(t.getTarifa() + t.getYQ(),2));
				t.setTarifaFacturadaYQ(JTools.forceRd(t.getTarifaFactura() + t.getYQ(),2));
				t.setIva(JTools.forceRd(t.getIva() + (pnrOriginal.getIva()*cotiz),2));
				t.setTarifaBaseConImpuestos(JTools.forceRd(t.getTarifaBaseConImpuestos() + (pnrOriginal.getTarifaBaseConImpuestos()*cotiz),2));
				t.setImpuestos(JTools.forceRd(t.getImpuestos() + (pnrOriginal.getImpuestos()*cotiz),2));
				t.setComisionAmount(JTools.forceRd(t.getComisionAmount() + (pnrOriginal.getComisionAmount()*cotiz),2));
				if (t.getTarifaBase() > 0) {
					if (t.getTipoCambio()<0)
						t.setTipoCambio(JTools.forceRd(t.getNeto() / t.getTarifaBase(),2));
				}
			} else {
				addFaltante(getCompany(), originalTicket);
			}
			
			t.setReemitted(true);
			t.setOriginalNumeroboleto(originalTicket);
		}

		t.setVendedor(getVendor());
		

		PssLogger.logDebug("tipo cambio: " + t.getTipoCambio());
		
		if (t.acceptTicket()) {
			save(t);

			this.saveTaxes(t);
			if (isEMD == false) {
				this.saveAirSegmentFrom(t);
				this.saveFareSegment(t);
			}
			BizReembolso reem = new BizReembolso();
			reem.dontThrowException(true);
			if (reem.readByBoletoRfnd(t.getCompany(), t.getNumeroboleto()))
				t.setRefund(true);
			t.saveCotizacion();
			//t.calculeOver();
			t.setCalculed(false);
			Date newVoid = null;
			if ((newVoid = BizVoidManual.isManualVoid(t.getCompany(), t.getNumeroboleto()))!=null) {
				t.setVoid(true);
				t.setVoidDate(newVoid);
			}
			String newDK = null;
			if ((newDK = BizVoidManual.isManualDK(t.getCompany(), t.getNumeroboleto()))!=null) {
				t.setCustomerIdReducido(newDK);
			}	
			t.SetVision("UPDATE");
			t.update();
			
			}

	}

	protected String getVendor() {
		return null;
	}

	protected FareRecord findViaje(BizPNRTicket tk, String codes, String from, String to, JList<ConnectionRecord> escalas, FareParser m) throws Exception {

		JIterator<FareRecord> it = m.getFareFields().getValueIterator();
		while (it.hasMoreElements()) {
			FareRecord fare = it.nextElement();
			if (fare.airport_from == null)
				continue;
			if (fare.airport_to == null)
				continue;
			if ((fare.airport_from.equals(from) || fare.inEscala(from)) && (fare.airport_to.equals(to) || fare.inEscala(to)))
				return fare;
		}
		if (escalas == null)
			return null;

		JIterator<ConnectionRecord> ite = escalas.getIterator();
		while (ite.hasMoreElements()) {
			ConnectionRecord conn = ite.nextElement();
			JIterator<FareRecord> itf = m.getFareFields().getValueIterator();
			while (itf.hasMoreElements()) {
				FareRecord fare = itf.nextElement();
				if (fare.airport_from == null)
					continue;
				if (fare.airport_to == null)
					continue;
				if (fare.airport_from.equals(from) && (fare.airport_to.equals(conn.airport_to)))
					return fare;
				if (fare.airport_from.equals(conn.airport_from) && (fare.airport_to.equals(to)))
					return fare;
			}
		}
		return null;
	}

	protected void saveAirSegmentFrom(BizPNRTicket t) throws Exception {

	}

	protected void saveFareSegment(BizPNRTicket t) throws Exception {

	}

	protected void saveFareSegment(BizPNRTicket t, FareParser m) throws Exception {
		t.getSegmentsFare().delete();

		JIterator<FareRecord> it = m.getFareFields().getValueIterator();
		while (it.hasMoreElements()) {
			FareRecord fare = it.nextElement();
			String ruta = "";
			String codes = "";
			if (fare.escalas == null)
				continue;
			if (fare.airport_to == null)
				continue;
			if (fare.airport_from == null)
				continue;

			JIterator<ConnectionRecord> iter = fare.escalas.getIterator();
			while (iter.hasMoreElements()) {
				ConnectionRecord conn = iter.nextElement();
				ruta += (ruta.equals("") ? "" : "/") + conn.airport_from + "/" + conn.airport_to;
			}
			double importe = fare.importe == null ? 0 : fare.importe.trim().equals("") ? 0 : Double.parseDouble(fare.importe);
			double impuestoiv = fare.impuesto;
			double importeiv = importe;
			double totaliv = importeiv + impuestoiv;
			double impuestob = fare.impuesto;
			double importeb = importe;
			double totalb = importeb + impuestob;

			FareRecord m6 = findViaje(t, codes, fare.airport_to, fare.airport_from, fare.escalas, m);
			if (m6 != null && m6.importe != null) {
				importeiv += (m6.importe.trim().equals("") ? 0 : Double.parseDouble(m6.importe));
				impuestoiv += m6.impuesto;
				totaliv = importeiv + impuestoiv;
			}

			if (m.getMoneda() != null && !m.getMoneda().equals(t.getCodigoMoneda())) {
				if (m.getMoneda().equals("NUC")) {
					importe = importe * m.getDoubleRoe();
					importeiv = importeiv * m.getDoubleRoe();
					impuestoiv = impuestoiv * m.getDoubleRoe();
					totaliv = totaliv * m.getDoubleRoe();
				}
			}
			if (t.getTipoCambio() != 0) {
				importe = importe * t.getTipoCambio();
				importeiv = importeiv * t.getTipoCambio();
				impuestoiv = impuestoiv * t.getTipoCambio();
				totaliv = totaliv * t.getTipoCambio();
			}
			double neto = importe - (importe * (t.getComisionPerc() / 100.0));
			double netob = importeb - (importeb * (t.getComisionPerc() / 100.0));
			double netoiv = importeiv - (importeiv * (t.getComisionPerc() / 100.0));

			saveFareTicketInformation(t.getId(), t.getCompany(), t.getCodigopnr(), m.getPassengerType(), fare.id, fare.airport_from, fare.airport_to, impuestoiv, importe, neto, ruta, fare.escalas.size(), fare.clase, fare.tipoClase, fare.emitido, t.getCodigoBaseMoneda(), t.getCodigoMoneda(), totalb, importeb, netob, totaliv, importeiv, netoiv, t.getObjCompany().getCountry(), m6 != null);

		}
	}

	protected void saveTaxes(BizPNRTicket t) throws Exception {
		int i = 0;

		t.getTaxes().delete();
		JMap<String, TaxCode> taxes = mTaxes.getElement(t.getNumeroboleto());

		if (taxes == null)
			return;

		JIterator<String> kit = taxes.getKeyIterator();
		JIterator<TaxCode> vit = taxes.getValueIterator();

		while (kit.hasMoreElements()) {
			String code = kit.nextElement();
			TaxCode value = vit.nextElement();
			BizPNRTax tax = new BizPNRTax();
			tax.setId(t.getId());
			tax.setSecuencia(++i);
			tax.setCodigoimpuesto(code);
			tax.setImporte(value.amount);
			tax.setCodigomoneda(value.currency);
			tax.setCodigomonedaLocal(t.getCodigoMonedaLocal());
			
			
			
			tax.setCompany(this.getCompany());
			tax.setCodigopnr(this.getPnrLocator());
			tax.setNumeroBoleto(t.getNumeroboleto());
			tax.setTarifasEnDolares(t);
			tax.insert();
		}
	}

	protected void assignCommon(BizPNRTicket t) throws Exception {

		t.setCompany(company);
		t.setOrigen(this.getOrigen());
		t.setGDS(this.getGds());
		t.setArchivo(this.getPnrFile());

		t.setCodigopnr(this.getPnrLocator());
		t.setNroIata(this.getIATA());
		t.setOfficeId(this.getOfficeId());
		t.setAgenteEmision(this.getOfficeId());
		t.setAgenteReserva(this.getAgReserva());
		t.setPNRDate(this.getPNRDate());
		t.setCreationDate(this.getCreationDate());
		t.setCreationDateAir(this.getCreationDateAIR());
		t.setTipoPrestacion(tipoPrestacion);
		t.setCustomerId(this.getCustId());
		t.setCustomerIdReducido(this.getCustIdReduced());
		t.setDepartamento(this.getDepartamento());
		//t.setOfficeId(this.getSucursal());

		if (isEMD)
			t.setTipoOperacion("EMD");
		else
			t.setTipoOperacion("ETR"); // HACER!

		// t.setRoute(this.travelItineraryRoute());

		if (mPass.getElement(t.getNumeroboleto()) != null) {
			t.setNombrePasajero(mPass.getElement(t.getNumeroboleto()).passengerName);
			t.setIdentFiscal(mPass.getElement(t.getNumeroboleto()).passengerIdentFiscal);
			t.setTipoPasajero(mPass.getElement(t.getNumeroboleto()).passengerType);
		}

		// -- FALTA ESTO
		// t.setTransactionType(value);

		t.setVoid(false);
		t.setNullVoidDate();

	}

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	public String getSucursal() {
		return sucursal;
	}

	public void setSucursal(String sucursal) {
		this.sucursal = sucursal;
	}

	public void setCustIdReduced(String cust) {
		this.custIdReduced = cust;
	}

	public String getCustIdReduced() {
		// TODO Auto-generated method stub
		return this.custIdReduced;
	}

	protected void assignPays(BizPNRTicket t) throws Exception {
		Pay pay = mPays.getElement(t.getNumeroboleto());
		if (pay != null) {

			if (pay.card == null)
				pay.amount = 0.0;
			else if (pay.amount==0.0) {
				pay.amount = t.getTarifaBaseConImpuestos();
			}
			t.setMontoTarjeta(JTools.forceRd(pay.amount,2));
			t.setNumeroTarjeta(pay.card);
			t.setNombreTarjeta(pay.cardName);
			t.setAutorizationCreditCard(pay.auth);

		}
	}

	protected void assignFareFrom(BizPNRTicket t) throws Exception {

		if (isEMD) {

			EMD emd = (EMD) getEMDbyID();
			if (emd == null)
				return;

			t.setCodigoBaseMoneda(emd.netCurrency);
			t.setCodigoMoneda(emd.totalCurrency);
			t.setCodigoMonedaLocal(BizCompany.getObjCompany(t.getCompany()).getCurrency());
			if (emd.carrier!=null && t.getCarrier()!=null)
				t.setCodigoaerolinea(emd.carrier.length()==5?emd.carrier.substring(3,5):emd.carrier);
			if (emd.netFare != 0)
				t.setTipoCambio(emd.fare / emd.netFare);
			t.setTarifaBase(emd.netFare);
			t.setTarifaBaseFactura(emd.netFare);
			t.setTarifaBaseConImpuestos(emd.fareWithTax);
			t.setTarifa(emd.fare);

			return;
		}

		if (mFares.getElement(t.getNumeroboleto()) != null) {

			t.setCodigoMoneda(mFares.getElement(t.getNumeroboleto()).totalCurrency);
			t.setCodigoBaseMoneda(mFares.getElement(t.getNumeroboleto()).baseCurrency);
			t.setTipoCambio(JTools.forceRd( mFares.getElement(t.getNumeroboleto()).exchangeRate, 2));
			t.setTarifaBaseConImpuestos(JTools.forceRd(mFares.getElement(t.getNumeroboleto()).baseFareWithTax,2));
			t.setTarifaBase(JTools.forceRd(mFares.getElement(t.getNumeroboleto()).baseFare,2));
			t.setTarifaBaseFactura(JTools.forceRd(mFares.getElement(t.getNumeroboleto()).baseFare,2));
			t.setTarifa(JTools.forceRd(mFares.getElement(t.getNumeroboleto()).fare,2));
			if (mFares.getElement(t.getNumeroboleto()).fee != null) {
				if (JTools.isNumber(mFares.getElement(t.getNumeroboleto()).fee, false))
					t.setCostoFee(JTools.getNumberEmbeddedWithDecSigned(mFares.getElement(t.getNumeroboleto()).fee));
			}
			t.setCentroCostos(mFares.getElement(t.getNumeroboleto()).centroCosto);
			t.setOrdenFee(mFares.getElement(t.getNumeroboleto()).ordenFee);
			t.setPagoFee(mFares.getElement(t.getNumeroboleto()).pagoFee);
			if (mFares.getElement(t.getNumeroboleto()).costoFee != null)
				t.setAdicionalFee(JTools.getNumberEmbeddedWithDecSigned(mFares.getElement(t.getNumeroboleto()).costoFee));
			t.setSolicitante(mFares.getElement(t.getNumeroboleto()).solicitante);
			if (mFares.getElement(t.getNumeroboleto()).cliente != null)
				t.setCodigoCliente(mFares.getElement(t.getNumeroboleto()).cliente);
			if (mFares.getElement(t.getNumeroboleto()).consumo != null) {
				t.setConsumo(mFares.getElement(t.getNumeroboleto()).consumo);
				t.setConsumoNum(JTools.getLongFirstNumberEmbedded(t.getConsumo()));
			}
			if (mFares.getElement(t.getNumeroboleto()).mail != null) {
				t.setEmailID(mFares.getElement(t.getNumeroboleto()).mail);
			}

		}
		t.setTarifaYQ(JTools.forceRd(t.getTarifa(),2));
		t.setTarifaFacturadaYQ(JTools.forceRd(t.getTarifaFactura(),2));
		JMap<String, TaxCode> taxes = mTaxes.getElement(t.getNumeroboleto());
		if (taxes != null) {
			TaxCode tx = taxes.getElement("YQ");
			if (tx != null) {
				double yq = tx.amount;
				t.setTarifaYQ(JTools.forceRd(t.getTarifa() + yq,2));
				t.setTarifaFacturadaYQ(JTools.forceRd(t.getTarifaFactura() + yq,2));
			}
		}
	}

	protected void saveTicket(boolean exist, String ticket, boolean isinternational, String carriercode, String paxid, String tickettype, String connecteditems) throws Exception {

		BizPNRTicket t = new BizPNRTicket();
		t.setNumeroboleto(ticket);
		t.setCodigoaerolinea(carriercode);
		t.setNumeropasajero(paxid);
		t.setCantidadconectados(connecteditems);
		t.setCompany(company);
		if (interfaceId != null)
			t.setId(interfaceId);
		t.setCodigopnr(pnrLocator);
		t.setInternacional(isinternational);
		t.setInternacionalDescr(t.isInternacional() ? "International" : "Domestic");
		t.setDirectory(getCompany() + "/" + getGds() + "/" + getPnrLocator().substring(0, 2));

		t.setTipoboleto(tickettype);
		t.setVoid(false);
		t.setNullVoidDate();
		t.setTipoPrestacion(tipoPrestacion);

		BizAirport airport = t.getObjAeropuertoOrigen();
		BizPaisLista pais = airport != null ? airport.getObjCountry() : null;
		if (airport != null)
			t.setPais(airport.getCountry());
		if (pais != null)
			t.setPaisOrigen(pais.GetPais());
		// if (pais!=null) t.setContinenteOrigen(pais.GetContinente());
		// if (pais!=null) t.setRegionOrigen(pais.GetRegion());
		// if (airport!=null) t.setCiudadOrigen(airport.getCity());
		// if (airport!=null) t.setGeoOrigen(airport.getGeoPosicion());
		airport = t.getObjAeropuertoDestino();
		pais = airport != null ? airport.getObjCountry() : null;
		if (pais != null)
			t.setPaisDestino(pais.GetPais());
		// if (pais!=null) t.setContinenteDestino(pais.GetContinente());
		// if (pais!=null) t.setRegionDestino(pais.GetRegion());
		// if (airport!=null) t.setCiudadDestino(airport.getCity());
		// if (airport!=null) t.setGeoDestino(airport.getGeoPosicion());
		if (t.isNullCustomerId())
			t.setCustomerId(t.getCodigoCliente());
		if (t.isNullCustomerIdReducido())
			t.setCustomerIdReducido(t.getCustomerId());

		if (t.acceptTicket()) {
			save(t);
		//	t.calculeOver();
			t.setCalculed(false);
			t.SetVision("UPDATE");
			t.update();
		}
		// t.extractPersonas();
	}

	protected GeoPosition findGeoAirport(String code) throws Exception {
		BizAirport a = new BizAirport();
		a.dontThrowException(true);
		if (!a.Read(code))
			return null;
		return a.getGeoPosicion();
	}

	protected BizPNRTicket findExists(BizPNRTicket tk) throws Exception {
		BizPNRTicket pt = new BizPNRTicket();
		pt.dontThrowException(true);
		if (!pt.Read(tk.getCompany(), tk.getCodigopnr(), tk.getNumeroboleto()))
			return null;
		return pt;
	}

	protected void save(BizPNRTicket tk) throws Exception {
		if (tk.getNumeroboleto()=="")
			PssLogger.logInfo("log point");
		if (!tk.hasTourCode())
			tk.setTourCode("-");

		BizPNRTicket old = this.findExists(tk);
		if (old != null) {
			tk.setId(old.getId());
			tk.update();
		} else {
			if (tk.isNullReemitted())
				tk.setReemitted(false);
			tk.setInsertDate(new Date());
			tk.setCalculed(false);
			tk.SetVision("UPDATE");
			
			tk.insert();
			tk.setId(tk.getIdentity("interface_id"));
		}
		tk.extractPersonas();
		marcarParaReprocesar(tk.getCompany(),tk.getNumeroboleto());
	}

	protected BizPNRTicket findTicketFromBoleto(String company, String boleto) throws Exception {
		BizPNRTicket pnr = new BizPNRTicket();
		pnr.dontThrowException(true);
		if (!pnr.ReadByBoleto(company, boleto))
			return null;
		return pnr;
	}

	/**
	 * @param m
	 * @param i
	 */
	protected void saveRemark(String m, int i) throws Exception {
		// BizPNRRemark r = new BizPNRRemark();
		// r.setCompany(company);
		// r.setId(interfaceId);
		// r.setCodigoPNR(pnrLocator);
		// r.setNumero((i + 1) + "");
		// r.setDetalle(m);
		// r.insert();
	}

	/**
	 * @throws Exception
	 */
	protected boolean isRemarkExists() throws Exception {
		// BizPNRRemark r2 = new BizPNRRemark();
		// r2.dontThrowException(true);
		// r2.addFilter("company", company);
		// r2.addFilter("codigopnr", pnrLocator);
		// if (r2.read()) {
		// return true;
		// }
		return false;
	}

	protected boolean savePNR() throws Exception {

		// preSavePNR();
		//
		// boolean update = true;
		// pnr = isAlreadyProcessed();
		// update = checkPNR(update);
		// if (pnr.isConsumed()) {
		// PssLogger.logDebug("pnr consumed, interface id:" + pnr.getId());
		// return false;
		// }
		//
		// pnr.setCompany(company);
		// pnr.setOrigen(origen);
		// pnr.setGDS(gds);
		// pnr.setCodigoPNR(pnrLocator);
		// pnr.setConsumed("N");
		// pnr.setComandoCRS("X");
		// pnr.setOfficeID(officeId);
		// pnr.setIATA(IATA);
		// pnr.setAirType("X");
		// pnr.setIndicadorVenta(";" + saleIndicator);
		// pnr.setLocalizador(pnrLocator);
		// if ( pnrFile == null )
		// pnrFile = pnrLocator;
		// pnr.setFile(pnrFile);
		// pnr.setFechaCreacion(creationDate);
		// pnr.setFechaModificacion(creationDate);
		// pnr.setFechaCreacionAIR(creationDateAIR);
		// pnr.setCodigoCliente(custId);
		// pnr.setCantidadBoletos(countTickets);
		// pnr.setVersion(version);
		// pnr.setRoute(route);
		// pnr.setFechaSalida(departure);
		// pnr.setTransactionType(txType);
		// pnr.setDuplicated(duplicated);
		// pnr.setTipoPrestacion(tipoPrestacion);
		// if (update)
		// pnr.update();
		// else
		// pnr.insertRecord();
		//
		// interfaceId = pnr.getId();
		// PssLogger.logDebug("interface id:" + pnr.getId());
		//
		return true;
	}

	protected void preSavePNR() throws Exception {
	}

	public String getSaleIndicator() {
		return saleIndicator;
	}

	public void setSaleIndicator(String saleIndicator) {
		this.saleIndicator = saleIndicator;
	}

	public void setSegments(int segments) {
		this.segments = segments;
	}

	public void save(JMap<String, Object> mRecords2) throws Exception {
	}

	// public void checkTaxes() throws Exception {
	//
	// JRecords<BizPNRPrice> prcs = new
	// JRecords<BizPNRPrice>(BizPNRPrice.class);
	// prcs.addFilter("company", company);
	// prcs.addFilter("codigopnr", pnrLocator );
	// prcs.toStatic();
	// while ( prcs.nextRecord() ) {
	// BizPNRPrice p = prcs.getRecord();
	//
	// JRecords<BizPNRTax> taxes = new JRecords<BizPNRTax>(BizPNRTax.class);
	// taxes.addFilter("interface_id", p.getId() );
	// taxes.addFilter("numeroboleto", p.getTicket());
	// long count = taxes.selectCount();
	// if ( count == 0 ) continue;
	//
	// double total = taxes.selectSum( "importe");
	//
	// double taxteoric = JTools.rd( p.getTarifaConImpuestos()-p.getTarifa(),
	// 2);
	//
	// if ( taxteoric > total ) {
	// BizPNRTax tax = new BizPNRTax();
	// tax.setCompany(company);
	// tax.setId(p.getId());
	// tax.setCodigopnr(pnrLocator);
	// tax.setSecuencia(1);
	// tax.setNumeroBoleto(p.getTicket());
	// tax.setCodigomoneda(p.getCodigoMoneda());
	// tax.setCodigoimpuesto("XT");
	// tax.setImporte((taxteoric-total)+"");
	// tax.setVirtual(true);
	// tax.insert();
	// }
	// }
	// }

	protected void finalyzeSegments(BizPNRTicket tk, JRecords<BizPNRSegmentoAereo> segments) throws Exception {

		tk.processAnalizeSegments(segments);
		tk.update();
	}

	protected void saveFareTicketInformation(long id, String company, String pnr, String tipo, long idFare, String desde, String hasta, double impuesto, double importe, double neto, String ruta, long escala, String clase, String tipoClase, boolean emitido, String monedabase, String moneda, double totalb, double importeb, double netob, double totaliv, double importeiv, double netoiv, String countryOrigen, boolean conVuelta) throws Exception {

		BizPNRFare fare = new BizPNRFare();
		fare.dontThrowException(true);
		if (fare.readFare(company, id, tipo, ruta)) {
			fare.setCompany(company);
			fare.setId(id);
			fare.setSecuencia(idFare);
			fare.setTipoPasajero(tipo);
			fare.setAirportFrom(desde);
			fare.setAirportTo(hasta);
			fare.setRuta(ruta);
			fare.setClase(clase);
			fare.setTipoClase(tipoClase);

			fare.setImporte(importe);
			fare.setImpuesto(impuesto);
			fare.setTotalIdaYVuelta(totaliv);
			fare.setImporteIdaYVuelta(importeiv);
			fare.setNetoIdaYVuelta(netoiv);
			fare.setMonedaBase(monedabase);
			fare.setMoneda(moneda);
			fare.setTotalBase(totalb);
			fare.setImporteBase(importeb);
			fare.setNetoBase(netob);
			fare.setNeto(neto);
			if (fare.getObjAeropuertoArrivo() != null && fare.getObjAeropuertoArrivo().getCountry().equals(countryOrigen) && !conVuelta) {
				fare.setPaisDestino(fare.getObjAeropuertoDespegue().getCountry());
				fare.setPaisOrigen(fare.getObjAeropuertoArrivo() == null ? null : fare.getObjAeropuertoArrivo().getCountry());
			} else {
				fare.setPaisDestino(fare.getObjAeropuertoArrivo() == null ? null : fare.getObjAeropuertoArrivo().getCountry());
				fare.setPaisOrigen(fare.getObjAeropuertoDespegue().getCountry());
			}
			fare.setNroEscala(escala);
			fare.setEmitido(emitido);
			fare.update();
		} else {
			boolean exists = fare.read(company, id, idFare);

			fare.setCompany(company);
			fare.setId(id);
			fare.setCodigopnr(pnr);
			fare.setSecuencia(idFare);
			fare.setAirportFrom(desde);
			fare.setAirportTo(hasta);
			fare.setTipoPasajero(tipo);
			fare.setRuta(ruta);
			fare.setClase(clase);
			fare.setTipoClase(tipoClase);
			fare.setImporte(importe);
			fare.setImpuesto(impuesto);
			fare.setNeto(neto);
			fare.setMonedaBase(monedabase);
			fare.setMoneda(moneda);
			fare.setTotalBase(totalb);
			fare.setImporteBase(importeb);
			fare.setNetoBase(netob);
			fare.setTotalIdaYVuelta(totaliv);
			fare.setImporteIdaYVuelta(importeiv);
			fare.setNetoIdaYVuelta(netoiv);
			if (fare.getObjAeropuertoArrivo() != null && fare.getObjAeropuertoArrivo().getCountry().equals(countryOrigen) && !conVuelta) {
				fare.setPaisDestino(fare.getObjAeropuertoDespegue().getCountry());
				fare.setPaisOrigen(fare.getObjAeropuertoArrivo() == null ? null : fare.getObjAeropuertoArrivo().getCountry());
			} else {
				fare.setPaisDestino(fare.getObjAeropuertoArrivo() == null ? null : fare.getObjAeropuertoArrivo().getCountry());
				fare.setPaisOrigen(fare.getObjAeropuertoDespegue() == null ? null : fare.getObjAeropuertoDespegue().getCountry());
			}
			fare.setNroEscala(escala);
			fare.setEmitido(emitido);
			if (exists)
				fare.update();
			else
				fare.insert();
		}
	}

	public void postProcessTransaction(Date date) throws Exception {
		try {
			if (this.getPnrLocator() == null)
				return;
			Long orderProcesamiento = generateOrdenProc(this.getPnrFile());
			if (orderProcesamiento==null)
				orderProcesamiento = Long.parseLong(JDateTools.DateToString(date==null?new Date():date, "yyyyMMddHHmmss")+"0"); 
			BizPNRFilename file = new BizPNRFilename();
			file.dontThrowException(true);
			file.addFilter("company", this.getCompany());
			file.addFilter("pnr", this.getPnrLocator());
			file.addFilter("filename", this.getPnrFile());
			file.addFilter("directory", this.getDirectory());
			if (file.read()) {
				return;
			}
			file.setCompany(this.getCompany());
			file.setDirectory(this.getDirectory());
			file.setPNR(this.getPnrLocator());
			file.setArchivo(this.getPnrFile());
			file.setGDS(this.getGds());
			file.setOrderProcesamiento(orderProcesamiento);
			file.setDate(new Date());
			file.insert();
		} catch (Exception eee) {
			PssLogger.logError(eee);
		}
	}
	
	public static Long generateOrdenProc(String filename) {
    // Regex para extraer año, mes, día y últimos tres dígitos
    String regex = ".*\\.(\\d{4})\\.(\\d{2})\\.(\\w{3})\\.(\\d{2})\\.(\\d{3}).*";
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(filename);

    if (matcher.matches()) {
        // Extraer partes del nombre del archivo
        String year = matcher.group(1);          // 2024
        String month = matcher.group(2);         // 08
        String monthName = matcher.group(3);     // JUL
        String day = matcher.group(4);           // 24
        String lastThreeDigits = matcher.group(5); // 007

        // Convertir el nombre del mes a un número (e.g., JUL -> 07)
        int monthNumber = monthToNumber(monthName);
        if (monthNumber == -1) {
            throw new IllegalArgumentException("Mes no válido: " + monthName);
        }

        // Construir la fecha y hora
        String dateTimeString = String.format("%s%02d%02d0000", year, monthNumber, Integer.parseInt(day));

        // Concatenar los últimos tres dígitos como milisegundos
        String ordenProcString = dateTimeString + lastThreeDigits;

        // Convertir a Long y devolver
        return Long.parseLong(ordenProcString);
    }

    return null;
}

// Método para convertir nombres de meses en números
private static int monthToNumber(String monthName) {
    switch (monthName.toUpperCase()) {
        case "JAN": return 1;
        case "FEB": return 2;
        case "MAR": return 3;
        case "APR": return 4;
        case "MAY": return 5;
        case "JUN": return 6;
        case "JUL": return 7;
        case "AUG": return 8;
        case "SEP": return 9;
        case "OCT": return 10;
        case "NOV": return 11;
        case "DEC": return 12;
        default: return -1;
    }
}


	protected void setFareFamily(int seg, BizPNRSegmentoAereo sa) throws Exception {
		sa.setFareBasic("");
		sa.setFareBasicExpanded("");
		sa.setFamiliaTarifaria("");
	}
	public void addFaltante(String company, String ticket) throws Exception {
		BizTicketsFaltantes t = new BizTicketsFaltantes();
		t.dontThrowException(true);
		if (t.Read(company,ticket)) {
			t.setReprocesar(false);
			t.processUpdate();
			return;
		}
	  t = new BizTicketsFaltantes();
		t.setNroBoleto(ticket);
		t.setCompany(company);
		t.setReprocesar(false);
		t.processInsert();
	}
	public void marcarParaReprocesar(String company, String ticket) throws Exception {
		if (reprocess) return;
		BizTicketsFaltantes t = new BizTicketsFaltantes();
		t.dontThrowException(true);
		if (!t.Read(company,ticket)) return;
		t.setReprocesar(true);
		t.processUpdate();
	}
}
