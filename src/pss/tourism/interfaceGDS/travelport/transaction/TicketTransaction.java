package pss.tourism.interfaceGDS.travelport.transaction;

import pss.bsp.clase.BizClase;
import pss.bsp.clase.detalle.BizClaseDetail;
import pss.core.services.records.JRecords;
import pss.core.tools.JTools;
import pss.core.tools.PssLogger;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;
import pss.tourism.airports.BizAirport;
import pss.tourism.interfaceGDS.BaseTransaction;
import pss.tourism.interfaceGDS.travelport.record.Record01;
import pss.tourism.interfaceGDS.travelport.record.Record1;
import pss.tourism.interfaceGDS.travelport.record.Record2;
import pss.tourism.interfaceGDS.travelport.record.Record3;
import pss.tourism.interfaceGDS.travelport.record.Record7;
import pss.tourism.interfaceGDS.travelport.record.Record8;
import pss.tourism.interfaceGDS.travelport.record.Record9;
import pss.tourism.interfaceGDS.travelport.record.RecordA;
import pss.tourism.interfaceGDS.travelport.record.RecordB;
import pss.tourism.interfaceGDS.travelport.record.RecordC;
import pss.tourism.interfaceGDS.travelport.record.RecordG;
import pss.tourism.interfaceGDS.travelport.record.RecordMINUSE;
import pss.tourism.interfaceGDS.travelport.record.RecordN;
import pss.tourism.interfaceGDS.travelport.record.TravelPortRecord;
import pss.tourism.pnr.BizPNRSegmentoAereo;
import pss.tourism.pnr.BizPNRTax;
import pss.tourism.pnr.BizPNRTicket;
import pss.tourism.pnr.PNRCache;

public class TicketTransaction extends BaseTransaction {

	protected Record1 r1;

	//OS int segments = 0;

	private boolean isInternational = false;

	public void save(JMap<String, Object> mRecords) throws Exception {
		this.mRecords = mRecords;
		r1 = ((Record1) mRecords.getElement(TravelPortRecord.ONE));
		if (r1==null)return;
		if (r1.getPNRLocator().equals("******"))
			return;
		this.setPnrLocator(r1.getPNRLocator());
		PssLogger.logDebug("checking if already exists, WORSPAN PNR: " + this.getPnrLocator());

		preSavePNR();
		saveTicketFrom();

	
	}

	private void saveRemarkFrom() throws Exception {
		RecordN m = (RecordN) (mRecords.getElement(TravelPortRecord.NRECORD));
		if (m == null)
			return;
		if (isRemarkExists())
			return;
		int im = m.getRemarkCount();
		for (int i = 0; i < im; i++) {
			saveRemark(m.getRemarkItem(i + 1), i);
		}
	}
	
	public String getIATA() throws Exception {
		Record3 r3 = (Record3) mRecords.getElement(TravelPortRecord.THREE);
		if (r3 == null) return null;
		return r3.getIATA();
	}


	protected void preSavePNR() throws Exception {

		Record3 r3 = (Record3) mRecords.getElement(TravelPortRecord.THREE);
		Record2 r2 = (Record2) mRecords.getElement(TravelPortRecord.TWO);
		Record9 r9 = (Record9) mRecords.getElement(TravelPortRecord.NINE);
		RecordA ra = (RecordA) mRecords.getElement(TravelPortRecord.ARECORD);
		Record01 r01 = (Record01) mRecords.getElement(TravelPortRecord.ZEROONE);
		// r3.getIATA();

		setIATA(r3.getIATA());

		setOfficeId("");
		if (r9 != null) {
			isInternational = r9.isInternational();
			setSaleIndicator(r9.getSalesIndicator());
		}

		// BOOK AGENTS
		setOfficeId(r3.getBook2());
		setAgReserva(r3.getBook1());

		// BOOK AGENTS

		setCreationDate(r3.getCreationDate());
		setCreationDateAIR(r3.getCreationDateAIR());
		setCustId(r2.getCustomerId());
		setCustIdReduced(r2.getCustomerId());

		if (ra!=null)
		setCountTickets(ra.getNumberOfTickets() + "");
		setVersion(r1.getVersion());
		// setRoute(this.travelItineraryRoute());
		if (r01 != null)
			setDeparture(r01.getDepartureDate(1));
		setTxType("1");
		setDuplicated(r1.isDuplicated() ? "1" : "0");

		RecordMINUSE re = (RecordMINUSE) mRecords.getElement(TravelPortRecord.MINUSERECORD);
		if (re != null) {
			this.isEMD = true;
		}

	}

	// private String travelItineraryRoute() throws Exception {
	// String route = "";
	//
	// Record01 r01 = (Record01) mRecords.getElement(TravelPortRecord.ZEROONE);
	// Record01 m = (Record01) mRecords.getElement(TravelPortRecord.ZEROONE);
	//
	// int im3 = r01.getNumberOfSegments();
	// segments = im3;
	// Record01 dm = null;
	// for (int i = 0; i < im3; i++) {
	// if (dm == null) {
	// if (m != null && m.getDepartureCityCode(i + 1) != null
	// && m.getArrivalCityCode(i + 1) != null) {
	// if (route.equals("") == false)
	// route += "/";
	// route += m.getDepartureCityCode(i + 1) + "/"
	// + m.getArrivalCityCode(i + 1);
	// }
	// } else if (dm.getArrivalCityCode(i + 1).equals(
	// dm.getDepartureCityCode(i + 1))) {
	// route += m.getArrivalCityCode(i + 1);
	// } else {
	// route += "/ANRK/" + m.getDepartureCityCode(i + 1) + "/"
	// + m.getArrivalCityCode(i + 1);
	// }
	// }
	// return route;
	// }

	// private String getPassengerTypeIn(M1Record m1record) {
	// int im2 = m0.getNumberOfM2();
	//
	// for (int i = 0; i < im2; i++) {
	// M2Record m2record = (M2Record) (mRecords.getElement(SabreRecord.M2 + (i +
	// 1)));
	// if (m2record.getPaxId().equals(m1record.getPaxId()))
	// return m2record.getPassengerType();
	// }
	// return "";
	// }
	//

	private void savePassengersFrom() throws Exception {

		RecordA ir = (RecordA) mRecords.getElement(TravelPortRecord.ARECORD);

		int it = ir.getNumberOfTickets();

		for (int t = 0; t < it; t++) {

			boolean upd = false;
			// BizPNRPasajero p = new BizPNRPasajero();
			// p.dontThrowException(true);
			// if (p.Read(company, this.getPnrLocator(), ir.getPaxId(t + 1)))
			// upd = true;
			// p.setCompany(company);
			// p.setId(interfaceId);
			// p.setCodigopnr(this.getPnrLocator());
			// p.setNumeropasajero(ir.getPaxId(t + 1));
			// p.setNombrepasajero(ir.getPaxName(t + 1));
			// p.setTipopasajero(ir.getPassengerTypeIn(t + 1));
			// if (upd)
			// p.update();
			// else
			// p.insert();
		}

	}

	//
	// private void saveConjunctionTicketsFrom() throws Exception {
	//
	// int im = m0.getNumberOfM2();
	// for (int i = 0; i < im; i++) {
	// M2Record m2 = (M2Record) (mRecords.getElement(SabreRecord.M2 + (i + 1)));
	// int ctc = m2.getConjuctionTicketCount() - 1;
	// if (ctc > 0) {
	// for (int j = 1; j <= ctc; j++) {
	// BizPNRConnectedTicket ct = new BizPNRConnectedTicket();
	// ct.dontThrowException(true);
	// if (ct.read(company, m2.getTicketNumber(), (ctc + 1 + j) + ""))
	// continue;
	// ct.setCompany(company);
	// ct.setTicket(m2.getTicketNumber());
	// ct.setConnectedTicket((ctc + 1 + j) + "");
	// ct.insert();
	// }
	// }
	// }
	//
	// }
	//
	protected void saveAirSegmentFrom(BizPNRTicket t) throws Exception {
		String firstCarrier = null;

		Record01 m = (Record01) mRecords.getElement(TravelPortRecord.ZEROONE);
		if (m==null) return;
		int im3 = m.getNumberOfSegments();

		JRecords<BizPNRSegmentoAereo> segs = new JRecords<BizPNRSegmentoAereo>(BizPNRSegmentoAereo.class);
		segs.setStatic(true);

		for (int i = 0; i < im3; i++) {

			boolean segmentoInternacional = false;

			BizPNRSegmentoAereo sa = new BizPNRSegmentoAereo();

			sa.setCompany(company);
			sa.setEmitido(true);
			sa.setId(t.getId());
			sa.setCodigoPNR(this.getPnrLocator());
			sa.setCodigoSegmento(m.getSegment(i + 1));
			sa.setDespegue(m.getDepartureCityCode(i + 1));
			sa.setArrivo(m.getArrivalCityCode(i + 1));
			sa.setCarrier(m.getCarrierCode(i + 1));
			sa.setCarrierPlaca(t.getCarrier());

			sa.setNumeroVuelo(m.getFlightNumber(i + 1));
			sa.setClase(m.getClassOfService(i + 1));
			sa.setRuta(m.getDepartureCityCode(i + 1) + "/" + m.getArrivalCityCode(i + 1));
			sa.setFareBasic(m.getFareFamily(i+1));
			sa.setFareBasicExpanded(m.getFareFamily(i+1));
			if (sa.getObjCarrier()!=null && sa.getObjCarrier().getObjLogica()!=null) {
				sa.getObjCarrier().getObjLogica().doCarrierLogica(sa.getFareBasic(), sa);
			} else {
				sa.setFamiliaTarifaria(sa.getFareBasic() == null || sa.getFareBasic().length() < 6 ? "" : sa.getFareBasic().substring(3, 5));
			}
			sa.setFechaDespegue(m.getDepartureDate(i + 1));
			sa.setFechaArrivo(m.getArrivalDate(i + 1));
			sa.setHoraDespegue(m.getDepartureTime(i + 1));
			sa.setHoraArrivo(m.getArrivalTime(i + 1));
			sa.setEstado(m.getStatus(i + 1));
			sa.setCodigoComida("");
			sa.setTipoEquipo("");
			sa.setCodigoEntreten("");
			sa.setDuracion("");
			sa.setDuracion(m.getFlightTime(i+1));
		
			String ind = m.getConnectedCode(i+1);
			sa.setConnectionIndicator(ind.equals(" ") || ind.equals("") ? BizPNRSegmentoAereo.SEGMENTO_START : (ind.equals("O") ? BizPNRSegmentoAereo.SEGMENTO_STOP : BizPNRSegmentoAereo.SEGMENTO_CONNECTION));


			BizAirport airportDespegue = sa.getObjAeropuertoDespegue();
			BizAirport airportArribo = sa.getObjAeropuertoArrivo();

			if (airportArribo.getCountry().equals(t.getObjCompany().getCountry()))
				sa.setPaisDestino(airportDespegue.getCountry());
			else
				sa.setPaisDestino(airportArribo.getCountry());

			segmentoInternacional = !airportDespegue.getCountry().equals(t.getObjCompany().getCountry()) || !airportDespegue.getObjCountry().GetPais().equals(airportArribo.getObjCountry().GetPais());
			if (firstCarrier == null && segmentoInternacional) {
				firstCarrier = sa.getCarrier();
			}
			BizClaseDetail cd =  PNRCache.getTipoClaseCache(getCompany(), t.getCarrier(), sa.getClase(),segmentoInternacional);
			if (cd == null) {
				sa.setTipoClase(BizClase.getTipoClaseDefault(sa.getClase()));
				sa.setPrioridad(BizClase.getPrioridadClaseDefault(sa.getClase()));
			} else {
				sa.setTipoClase(cd.getObjClase().getDescripcion());
				sa.setPrioridad(cd.getPrioridad());
			}

			segs.addItem(sa);
		}
		finalyzeSegments(t, segs);

	}

	protected EMD getEMDbyID() {
		int i = 0;

		RecordMINUSE re = (RecordMINUSE) mRecords.getElement(TravelPortRecord.MINUSERECORD);
		if (re == null)
			return null;

		EMD e = new EMD();
		e.baseFare = Double.parseDouble(re.getAmount());
		e.fare = Double.parseDouble(re.getAmount());
		e.netCurrency = re.getCurrency();
		e.totalCurrency = re.getTotalCurrency();
		e.fareWithTax = Double.parseDouble(re.getTotalAmount());
		e.taxAmount = Double.parseDouble(re.getTaxAmount());
		e.taxCode = "XT";
		e.taxCurrency = re.getTaxCurrency();

		return e;
	}

	private void saveEMDPaysFrom(String ticket, String amount, String cc, String type, String auth) throws Exception {
		
		Pay pay = new Pay();
		if (amount!=null)
		  pay.amount = Double.parseDouble(amount);
		else 
			pay.amount=0;
		pay.card = cc;
		pay.cardName= type;
		pay.auth = auth;
		mPays.addElement(ticket, pay);
	}

	private void saveTicketFrom() throws Exception {
		String originalTicket = null;

		if (isEMD) {
			String tickettype = "D";

			RecordMINUSE re = (RecordMINUSE) mRecords.getElement(TravelPortRecord.MINUSERECORD);
			saveEMDPaysFrom(re.getEMDID() ,  re.getAmount(), re.getCardNumber(), re.getCardType(), "000");
			this.saveTicket(re.getEMDID(), isInternational, re.getCarrier(), 1 + "", tickettype, "0", null);

			// Object[] ids = IRecord.getIds();
			// for ( int idx=0 ; idx < IRecord.countEMDs(); idx ++ ) {
			// emdid=ids[idx].toString();
			// emdticket= IRecord.getEMDTicket(emdid);
			// String aircode = ir.getAirlineCode(t + 1);
			// this.saveTicket(emdticket, isInternational, aircode,
			// (t + 1) + "", tickettype, "0",
			// ir.getOriginalTicketNumber(t + 1));
			// if (ticket!=null) {
			// if ( emdticket.equalsIgnoreCase(ticket)==false ) {
			// isEMD=false;
			// this.saveTicket(ticket, isInternational, ir.getAirlineCode(t
			// + 1),
			// (t + 1) + "", tickettype, "0",
			// ir.getOriginalTicketNumber(t + 1));
			// }
			// }

			return;

		}
		saveCommissionValues();
		saveTaxesFrom();
		saveFareFrom();
		savePaysFrom();

		RecordA ir = (RecordA) mRecords.getElement(TravelPortRecord.ARECORD);
		if (ir==null) return;
		
		int it = ir.getNumberOfTickets();

		for (int t = 0; t < it; t++) {

			String ticket = ir.getTicketNumber(t + 1);

			if (JTools.isNumber(ticket) == false)
				continue;

			String tickettype = "K";

			Passenger pass = new Passenger();
			pass.passengerName = ir.getPaxName(t + 1);
			pass.passengerIdentFiscal = "";//aun no se como viene este dato
			pass.passengerType = ir.getPassengerTypeIn(t + 1);
			mPass.addElement(ticket, pass);

			// boolean exist = this.findExists(null)!=null;
			Record9 r9 = (Record9) mRecords.getElement(TravelPortRecord.NINE);
			RecordC c = (RecordC) mRecords.getElement(TravelPortRecord.CRECORD);
			if (c != null) {
				originalTicket = c.getTicketNumber();
				this.isReissued = true;
			}
			// this.saveTicket(true, ticket, isInternational,
			// r9.getAirlineCode(), ir.getPaxId(t + 1), tickettype, "0");
			String aircode = "";
			if (r9 != null)
				aircode = r9.getAirlineCode();

			saveTicket(ticket, isInternational, aircode, ir.getPaxId(t + 1), tickettype, "0", originalTicket);

		}

	}

	private void saveCommissionValues() throws Exception {

		Record8 k = (Record8) mRecords.getElement(TravelPortRecord.EIGHT);

		RecordA ir = (RecordA) mRecords.getElement(TravelPortRecord.ARECORD);
		RecordB b = (RecordB) mRecords.getElement(TravelPortRecord.BRECORD);
		RecordG g = (RecordG) mRecords.getElement(TravelPortRecord.GRECORD);
		if (ir==null) return;
		if (g != null) {
			if (g.getNumberOfFares() == 0) {
				g = (RecordG) mRecords.getElement(TravelPortRecord.XRECORD);
				if (g.isEven())
					return;
			}
		}
		// RecordX x = (RecordX) mRecords.getElement(TravelPortRecord.XRECORD);

		int it = ir.getNumberOfTickets();

		for (int t = 0; t < it; t++) {

			String ticket = ir.getTicketNumber(t + 1);

			String paxtype = ir.getPaxType(t + 1);

			// String paxfareid = b.getFareIdFromPaxType(paxtype);
			if (g == null)
				continue;

			double fareamount = Double.parseDouble(g.getFare(t+1));

			if (k == null)
				continue;

			double commission = Double.parseDouble(k.getCommissionAmount(paxtype));
			double percentaje = Double.parseDouble(k.getCommissionPercentaje(paxtype));

			if (commission == 0.0f && percentaje == 0.0f)
				continue;

			if (commission == 0.0f)
				commission = JTools.forceRd(fareamount * percentaje / 100, 2);

			PssLogger.logDebug("Fare             : " + fareamount);
			PssLogger.logDebug("Comm. Percentaje : " + percentaje);
			PssLogger.logDebug("Comm. Amount     : " + commission);

			Commission com = new Commission();
			com.amount = commission;
			com.percentaje = percentaje;
			com.fare = fareamount;

			mComis.addElement(ticket, com);

		}
	}

	protected void saveFareSegment(BizPNRTicket t) throws Exception {
		RecordB b = (RecordB) mRecords.getElement(TravelPortRecord.BRECORD);
		if (b == null)
			return;
		saveFareSegment(t, b);
	}

	protected void removeM4Marks() throws Exception {

	}

	private void saveFareFrom() throws Exception {

		Record8 k = (Record8) mRecords.getElement(TravelPortRecord.EIGHT);

		RecordA ir = (RecordA) mRecords.getElement(TravelPortRecord.ARECORD);
		RecordB b = (RecordB) mRecords.getElement(TravelPortRecord.BRECORD);
		RecordG g = (RecordG) mRecords.getElement(TravelPortRecord.GRECORD);
		if (g==null) return;
		if (g.getNumberOfFares() == 0) {
			g = (RecordG) mRecords.getElement(TravelPortRecord.XRECORD);
			if (g.isEven())
				return;

		}

		if (k != null) {
			if (k.isEmpty())
				k = null;
		}
		int it = ir.getNumberOfTickets();

		for (int t = 0; t < it; t++) {

			String ticket = ir.getTicketNumber(t + 1);

			String paxtype = ir.getPaxType(t + 1);

			int paxfareid = 0;
			int ronda = ir.getPaxRonda(t+1);
			if (g.getNumberOfFares()> 1) {
				paxfareid=1;
				for (int cg=0;cg<g.getNumberOfFares() ;cg++) {
					if (g.getFareRonda(cg+1)>=ronda) {
						paxfareid=cg;
						break;
					}
						
				}
				// paxfareid = g.getFareId(1);
			}

			double netamount = Double.parseDouble(g.getFare(paxfareid+1));
			double fareamount = Double.parseDouble(g.getBaseFare(paxfareid+1));
			// if (netamount == 0.0f)
			// netamount = fareamount;
			String er = g.getCurrencyExchange(paxfareid+1);

			boolean exist = priceExists(ticket);
			String basecurr = g.getBaseFareCurreny(paxfareid+1);
			if (basecurr == null)
				basecurr = "";
			if (basecurr.equals("")) {
				if(b!=null)
					basecurr = b.getCurrencyBaseFare(paxtype);
			}
			String totalcurr = g.getCurrencyTotalFare(paxfareid+1);

			Fare fare = new Fare();
			fare.baseCurrency = basecurr;
			fare.baseFare = fareamount;
			fare.totalCurrency = totalcurr;
			fare.exchangeRate = Double.parseDouble(er);
			fare.baseFareWithTax = Double.parseDouble(g.getTotalFare(paxfareid+1));
			fare.fare = netamount;
			fare.isEMD = false;
			
			// fare.fee = fee;
			// fare.costoFee = costofee;
			// fare.ordenFee = ordenfee;
			// fare.pagoFee = formapago;
			// fare.centroCosto=centrocosto;
			// fare.solicitante=solicitante;
			// fare.cliente=cliente;
			// fare.consumo=consumo;

			mFares.addElement(ticket, fare);

			// this.savePrice(exist, basecurr,
			// g.getCurrencyTotalFare(paxfareid), ticket,
			// Double.parseDouble(g.getTotalFare(paxfareid)),
			// fareamount, netamount, er);
			// } else {
			// updatePrice(p, basecurr, g.getCurrencyTotalFare(paxfareid),
			// ticket,
			// Double.parseDouble(g.getTotalFare(paxfareid)),
			// fareamount, netamount, er);
			// }
		}

	}

	private void saveTaxesFrom() throws Exception {

		RecordB kft = (RecordB) mRecords.getElement(TravelPortRecord.BRECORD);
		RecordG g = (RecordG) mRecords.getElement(TravelPortRecord.GRECORD);
		if (g==null) return;
		if (g != null) {
			if (g.getNumberOfFares() == 0) {
				g = (RecordG) mRecords.getElement(TravelPortRecord.XRECORD);
				if (g.isEven())
					return;
			}
		}

		RecordA ra = (RecordA) mRecords.getElement(TravelPortRecord.ARECORD);

		int it = ra.getNumberOfTickets();

		

		for (int ticket = 0; ticket < it; ticket++) {

			JMap<String, TaxCode> taxcodes = JCollectionFactory.createMap();
			
			String ticketNumber = ra.getTicketNumber(ticket + 1);
			String passtype = ra.getPaxType(ticket + 1);
			int ronda = 0;//ra.getPaxRonda(ticket+1);
			
			// JMap<String, String> taxes = kft.getTaxCollection(passtype);
			// if (taxes == null)
			// continue;
			// JIterator<String> key = taxes.getKeyIterator();
			// JIterator<String> value = taxes.getValueIterator();

			String currency = g.getCurrencyTotalFare(ronda+1);
			String taxid1;
			String taxamount;

			// while (key.hasMoreElements()) {
			//
			// taxid1 = key.nextElement();
			// taxamount = value.nextElement();
			// //String currency = "ARS";
			//
			// TaxCode tax = new TaxCode();
			// tax.amount = Double.parseDouble(taxamount);
			// tax.currency = currency;
			//
			// PssLogger.logDebug("Tax Code: " + taxid1);
			// PssLogger.logDebug("Tax Amount: " + tax.amount);
			// PssLogger.logDebug("Tax Currency: " + tax.currency);
			//
			// taxcodes.addElement(taxid1, tax);
			//
			// }
			TaxCode tax = new TaxCode();
			taxid1 = g.getTaxXTCode(ronda+1);
			if (taxid1 != null) {
				tax.amount = Double.parseDouble(g.getTaxXT(ronda+1));
				tax.currency = currency;

				PssLogger.logDebug("Tax Code: " + taxid1);
				PssLogger.logDebug("Tax Amount: " + tax.amount);
				PssLogger.logDebug("Tax Currency: " + tax.currency);
				taxcodes.addElement(taxid1, tax);
			}
			taxid1 = g.getTax2Code(ronda+1);
			if (taxid1 != null) {

				tax = new TaxCode();
				tax.amount = Double.parseDouble(g.getTax2(ronda+1));
				tax.currency = currency;

				PssLogger.logDebug("Tax Code: " + taxid1);
				PssLogger.logDebug("Tax Amount: " + tax.amount);
				PssLogger.logDebug("Tax Currency: " + tax.currency);
				taxcodes.addElement(taxid1, tax);
			}

			if (taxid1 != null) {
				taxid1 = g.getTax3Code(ronda+1);

				tax = new TaxCode();
				tax.amount = Double.parseDouble(g.getTax3(ronda+1));
				tax.currency = currency;

				PssLogger.logDebug("Tax Code: " + taxid1);
				PssLogger.logDebug("Tax Amount: " + tax.amount);
				PssLogger.logDebug("Tax Currency: " + tax.currency);
				taxcodes.addElement(taxid1, tax);

				mTaxes.addElement(ticketNumber, taxcodes);
			}

		}

	}

	/**
	 * @param ticketNumber
	 * @param taxid1
	 * @param taxamount
	 * @param currency
	 * @throws Exception
	 */
	private void saveTaxTicketInformation(String ticketNumber, String taxid1, String taxamount, String currency) throws Exception {
		BizPNRTax tax = new BizPNRTax();
		if (taxid1 == null)
			return;
		if (taxid1.equals(""))
			return;
		tax.dontThrowException(true);
		if (tax.read(company, this.getPnrLocator(), ticketNumber, taxid1)) {
			tax.setCompany(company);
			tax.setId(interfaceId);
			tax.setImporte(Double.parseDouble(taxamount));
			tax.update();
		} else {
			tax.setCompany(company);
			tax.setId(interfaceId);
			tax.setCodigopnr(this.getPnrLocator());
			tax.setSecuencia(1);
			tax.setNumeroBoleto(ticketNumber);
			tax.setCodigomoneda(currency);
			tax.setCodigoimpuesto(taxid1);
			tax.setImporte(Double.parseDouble(taxamount));
			tax.insert();
		}
	}

	// private void saveServiceFee(boolean type2) throws Exception {
	// M8Record m8 = (M8Record) mRecords.getElement(SabreRecord.M8);
	//
	// if (m8 == null)
	// return;
	//
	// checkSaleData(m8);
	//
	// int im5 = m0.getNumberOfM5();
	// for (int i = 0; i < im5; i++) {
	// M5Record m5 = (M5Record) (mRecords.getElement(SabreRecord.M5 + (i + 1)));
	//
	// if (m5 == null)
	// continue;
	// String paxid = m5.getPaxId();
	// if (paxid.equals("99"))
	// continue;
	//
	// if (m5.getTicketNumber().equals("") == false) {
	// BizPNRTicket tk = new BizPNRTicket();
	// tk.dontThrowException(true);
	// if (tk.Read(company, pnrLocator, m5.getTicketNumber())) {
	// if (type2)
	// updateAdditionalFeeDataType2(m8, paxid, tk);
	// else
	// updateAdditionalFeeDataType1(m8, paxid, tk);
	// }
	// }
	//
	// }
	//
	// }
	//
	// /**
	// * @param m8
	// * @param paxid
	// * @param tk
	// * @throws Exception
	// */
	// private void updateAdditionalFeeDataType2(M8Record m8, String paxid,
	// BizPNRTicket tk) throws Exception {
	// tk.setImporteover(m8.getOverGain(paxid));
	// tk.setImporteCedido(m8.getOverCedido(paxid));
	// tk.setAdicionalFee(m8.getServiceFee(paxid));
	// tk.setExpense(m8.getExpenses(paxid));
	// tk.update();
	// }
	//
	// private void updateAdditionalFeeDataType1(M8Record m8, String paxid,
	// BizPNRTicket tk) throws Exception {
	// tk.setImporteover(m8.getOverGain1(tk.getNumeroboleto()));
	// tk.setImporteCedido(m8.getOverCedido1(tk.getNumeroboleto()));
	// tk.update();
	// }
	//
	// /**
	// * @param m8
	// * @throws Exception
	// */
	// private void checkSaleData(M8Record m8) throws Exception {
	// BizPNR pnr = new BizPNR();
	// pnr.dontThrowException(true);
	// if (pnr.Read(company, pnrLocator)) {
	// if (m8.getCustomerId().equals("") == false) {
	// pnr.setCodigoCliente(m8.getCustomerId());
	// pnr.setCentroCosto(m8.getCostCener());
	// pnr.setObservation(m8.getObservation());
	// pnr.update();
	// }
	// if (m8.getVendor().equals("") == false) {
	// pnr.setVendedor(m8.getVendor());
	// pnr.update();
	// }
	// }
	// }
	//

	private void savePaysFrom() throws Exception {

		Record7 r7 = (Record7) mRecords.getElement(TravelPortRecord.SEVEN);

		RecordA ir = (RecordA) mRecords.getElement(TravelPortRecord.ARECORD);
		RecordB b = (RecordB) mRecords.getElement(TravelPortRecord.BRECORD);
		RecordG g = (RecordG) mRecords.getElement(TravelPortRecord.GRECORD);
		if (g==null) return;
		if (g.getNumberOfFares() == 0) {
			g = (RecordG) mRecords.getElement(TravelPortRecord.XRECORD);
			if (g.isEven())
				return;
		}

		int it = ir.getNumberOfTickets();

		for (int t = 0; t < it; t++) {

			String ticket = ir.getTicketNumber(t + 1);

			String paxtype = ir.getPaxType(t + 1);
			// String paxfareid = b.getFareIdFromPaxType(paxtype);

			String amount = g.getTotalFare(t+1);
			String curr = g.getCurrencyTotalFare(t+1);
			String type = r7.getFormOfPaymentDesc();
			String cc = r7.getCreditCardNumber();
			String amountpay = "0";

			if (amountpay.equals("0") == false)
				amount = amountpay;
			
			if (type.equals("CASH"))
				type = null;

			Pay pay = new Pay();
			pay.amount = Double.parseDouble(amount);
			pay.card = cc;
			pay.cardName = type;
			// pay.auth = auth;
			mPays.addElement(ticket, pay);

			// boolean exist = paymentExists(ticket, type);
			// if (pay != null)
			// continue;

			// this.savePayment(exist, amount, type, cc, curr, ticket);

		}

	}

	//
	//
	// private String travelItineraryRoute() throws Exception {
	// String route = "";
	// int im3 = m0.getNumberOfM3();
	// M3Record dm = null;
	// for (int i = 0; i < im3; i++) {
	// M3Record m = (M3Record) (mRecords.getElement(SabreRecord.M3 + (i + 1)));
	// if (dm == null) {
	// if (m != null && m.getDepartureCityCode() != null &&
	// m.getArrivalCityCode()
	// != null) {
	// if (route.equals("") == false)
	// route += "/";
	// route += m.getDepartureCityCode() + "/" + m.getArrivalCityCode();
	// }
	// } else if (dm.getArrivalCityCode().equals(dm.getDepartureCityCode())) {
	// route += m.getArrivalCityCode();
	// } else {
	// route += "/ANRK/" + m.getDepartureCityCode() + "/" +
	// m.getArrivalCityCode();
	// }
	// }
	// return route;
	// }

}
