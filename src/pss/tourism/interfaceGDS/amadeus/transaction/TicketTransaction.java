package pss.tourism.interfaceGDS.amadeus.transaction;

import pss.bsp.clase.BizClase;
import pss.bsp.clase.detalle.BizClaseDetail;
import pss.core.services.records.JRecords;
import pss.core.tools.JTools;
import pss.core.tools.PssLogger;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;
import pss.tourism.airports.BizAirport;
import pss.tourism.carrier.BizCarrier;
import pss.tourism.interfaceGDS.BaseTransaction;
import pss.tourism.interfaceGDS.amadeus.record.AIRRecord;
import pss.tourism.interfaceGDS.amadeus.record.AmadeusRecord;
import pss.tourism.interfaceGDS.amadeus.record.BLKRecord;
import pss.tourism.interfaceGDS.amadeus.record.BRecord;
import pss.tourism.interfaceGDS.amadeus.record.CRecord;
import pss.tourism.interfaceGDS.amadeus.record.DRecord;
import pss.tourism.interfaceGDS.amadeus.record.EMDRecord;
import pss.tourism.interfaceGDS.amadeus.record.GRecord;
import pss.tourism.interfaceGDS.amadeus.record.HRecord;
import pss.tourism.interfaceGDS.amadeus.record.IRecord;
import pss.tourism.interfaceGDS.amadeus.record.KFTFRecord;
import pss.tourism.interfaceGDS.amadeus.record.KFTRecord;
import pss.tourism.interfaceGDS.amadeus.record.KNRecord;
import pss.tourism.interfaceGDS.amadeus.record.KNTIRecord;
import pss.tourism.interfaceGDS.amadeus.record.KNTYRecord;
import pss.tourism.interfaceGDS.amadeus.record.KRecord;
import pss.tourism.interfaceGDS.amadeus.record.KSRecord;
import pss.tourism.interfaceGDS.amadeus.record.MRecord;
import pss.tourism.interfaceGDS.amadeus.record.MUCRecord;
import pss.tourism.interfaceGDS.amadeus.record.QRecord;
import pss.tourism.pnr.BizPNRSegmentoAereo;
import pss.tourism.pnr.BizPNRTax;
import pss.tourism.pnr.BizPNRTicket;
import pss.tourism.pnr.PNRCache;

public class TicketTransaction extends BaseTransaction {

	protected MUCRecord muc;

	private int segments = 0;

	private boolean isInternational = false;

	


	public void save(JMap<String, Object> mRecords) throws Exception {
		this.mRecords = mRecords;
		
		muc = ((MUCRecord) mRecords.getElement(AmadeusRecord.MUC));
		this.setPnrLocator(muc.getPNRLocator());
		PssLogger.logDebug("checking if already exists, AMADEUS PNR: "
				+ this.getPnrLocator());
		AIRRecord air = ((AIRRecord) mRecords.getElement(AmadeusRecord.AIR));
		if (air!=null) {
			if (air.getTipoAIR().equals("IM"))
				return;
		}
		
	
		
		BRecord b = ((BRecord)mRecords.getElement(AmadeusRecord.B));
		// Si es manual no lo proceso`
		if (b != null) {
			if (b.isBT())
				return;
		}

		mComis = JCollectionFactory.createMap();
		mFares = JCollectionFactory.createMap();
		mTaxes = JCollectionFactory.createMap();
		mPays = JCollectionFactory.createMap();
		

		// savePNR();
		// saveTaxesFrom();
		
		// saveFareFrom();
		// savePaysFrom();
		// saveCommissionFrom();
		// saveTicketFrom();
		// // saveServiceFeeFrom();
		// // saveRemarkFrom();
		// saveAirSegmentFrom();
		// // saveConjunctionTicketsFrom();
		// savePassengersFrom();
		// analyzeAirSegments(null);
		preSavePNR();
		saveTicketFrom();
	}

	public String getIATA() throws Exception {
		PssLogger.logDebug("IATA=>" + muc.getIATA());
		return muc.getIATA();
	}

	protected void preSavePNR() throws Exception {

		setIATA(muc.getIATA());
		setOfficeId(muc.getOffice());
		setAgReserva(muc.getAgReserva());
		GRecord g = (GRecord) mRecords.getElement(AmadeusRecord.G);
		if (g != null) {
		    isInternational = g.isInternational();
			setSaleIndicator(g.getIndicadorVenta());
		}
		DRecord d = (DRecord) mRecords.getElement(AmadeusRecord.D);
		if (d != null) {
			
			setCreationDate(d.getCreationDateAIR());
			setCreationDateAIR(d.getCreationDateAIR());
		}
		setCustId("");
		IRecord ir = (IRecord) mRecords.getElement(AmadeusRecord.I);
		setCountTickets(ir.getNumberOfTickets() + "");
		AIRRecord air = (AIRRecord) mRecords.getElement(AmadeusRecord.AIR);
		BLKRecord blk = (BLKRecord) mRecords.getElement(AmadeusRecord.BLK);
		if (ir!=null&&ir.getAitan()!=null) {
			setCustId(ir.getAitan());
			setCustIdReduced(ir.getAitan());
		}
		if (ir!=null&&ir.getDepartamento()!=null) {
			setDepartamento(ir.getDepartamento());
		}		
		if (ir!=null&&ir.getSucursal()!=null) {
			setSucursal(ir.getSucursal());
		}		
		if (air!=null)
			setVersion(air.getVersion());
		if (blk!=null)
			setVersion(blk.getVersion());
//		setRoute(this.travelItineraryRoute());
		HRecord m =getHRecord(1);
		if (m != null) {
			setDeparture(m.getDepartureDate());
		}
		setTxType("1");
		setDuplicated("0");

	}



	public void setSegments(int segments) {
		this.segments = segments;
	}



	protected void saveAirSegmentFrom(BizPNRTicket t) throws Exception {
		try {
			String firstCarrier = null;
			//t.getSegments().delete();
			

			JRecords<BizPNRSegmentoAereo> segs = new JRecords<BizPNRSegmentoAereo>(
					BizPNRSegmentoAereo.class);
			segs.setStatic(true);
			int seg=0;//RJL no entiendo como funciona el M, pero hay 4 segmentos algunos voy en el medio, y 4 familias no parece coincidir con ningun codigo
			int im3 = segments;
			for (int i = 0; i < im3; i++) {
				HRecord m = getHRecord(i);
				if (m==null)
					continue;
				boolean segmentoInternacional = false;

				if (m.isVoid())
				  continue;

				if (!m.hasSegment()) //RJL revisar, explotaba porque venia null lo salteo
				  continue;
				BizPNRSegmentoAereo sa = new BizPNRSegmentoAereo();

				sa.setCompany(company);
				sa.setId(t.getId());
				sa.setCodigoPNR(this.getPnrLocator());
				sa.setCodigoSegmento(m.getSegment());
				
				String ind = m.getConnectionIndicator();
				sa.setConnectionIndicator(ind.equals(" ") || ind.equals("") ? BizPNRSegmentoAereo.SEGMENTO_START : (ind.equals("O") ? BizPNRSegmentoAereo.SEGMENTO_STOP : BizPNRSegmentoAereo.SEGMENTO_CONNECTION));


				sa.setDespegue(m.getDepartureCityCode());
				sa.setGeoDespegue(this.findGeoAirport(sa.getDespegue()));

				sa.setArrivo(m.getArrivalCityCode());
				sa.setGeoArrivo(this.findGeoAirport(sa.getArrivo()));

				sa.setRuta(m.getDepartureCityCode() + "/"
						+ m.getArrivalCityCode());
				sa.setCarrier(m.getCarrierCode());
				sa.setCarrierPlaca(t.getCarrier());
				// Carrier operator ? de donde sacarlo ?
				// sa.setCarrierOp(m.getCarrierCodeOp());
				BizAirport airportDespegue = sa.getObjAeropuertoDespegue();
				BizAirport airportArribo = sa.getObjAeropuertoArrivo();

				if (airportArribo.getCountry().equals(
						t.getObjCompany().getCountry()))
					sa.setPaisDestino(airportDespegue.getCountry());
				else
					sa.setPaisDestino(airportArribo.getCountry());

				segmentoInternacional = !airportDespegue.getCountry().equals(t.getObjCompany().getCountry()) || !airportDespegue.getObjCountry().GetPais().equals(airportArribo.getObjCountry().GetPais());
				if (firstCarrier == null && segmentoInternacional) {
					firstCarrier = m.getCarrierCode();
				}

				sa.setNumeroVuelo(m.getFlightNumber());
				sa.setClase(m.getClassOfService());
				BizClaseDetail cd = PNRCache.getTipoClaseCache(getCompany(), t.getCarrier(), m.getClassOfService(),segmentoInternacional);
				if (cd == null) {
					sa.setTipoClase(BizClase.getTipoClaseDefault(m.getClassOfService()));
					sa.setPrioridad(BizClase.getPrioridadClaseDefault(m.getClassOfService()));
				} else {
					sa.setTipoClase(cd.getObjClase().getDescripcion());
					sa.setPrioridad(cd.getPrioridad());
				}

				sa.setFechaDespegue(m.getDepartureDate());
				sa.setFechaArrivo(m.getArrivalDate());

				sa.setHoraDespegue(m.getDepartureTime());
				sa.setHoraArrivo(m.getArrivalTime());

				sa.setEstado(m.getStatus());
				sa.setCodigoComida("");
				sa.setTipoEquipo(m.getPlaneType());
				sa.setCodigoEntreten("");
				sa.setDuracion(m.getFlightLapse());
				
				seg++;
				setFareFamily(seg, sa);


				segs.addItem(sa);
			}
			finalyzeSegments(t, segs);

		} catch (Exception ignored) {
			PssLogger.logError(ignored);
			PssLogger.logError(ignored, "ERROR IGNORADO EN LOS SEGMENTOS !!!!");
		}
	}

	protected void setFareFamily(int seg, BizPNRSegmentoAereo sa) throws Exception {
		MRecord mrec=getMRecord();
		if (mrec!=null && mrec.getFareBasic(seg)!=null) {
			sa.setFareBasic(mrec.getFareBasic(seg).trim());
			sa.setFareBasicExpanded(mrec.getFareBasic(seg).trim());
			if (sa.getObjCarrier()!=null && sa.getObjCarrier().getObjLogica()!=null) {
				sa.getObjCarrier().getObjLogica().doCarrierLogica(mrec.getFareBasic(seg), sa);
			} else {
				sa.setFamiliaTarifaria(sa.getFareBasic() == null || sa.getFareBasic().length() < 6 ? "" : sa.getFareBasic().substring(3, 5));
			}
		} else {
			sa.setFareBasic("");
			sa.setFareBasicExpanded("");
			sa.setFamiliaTarifaria("");
			
		}
	}

	private HRecord getHRecord(int i) {
		HRecord m =  (HRecord) mRecords.getElement(AmadeusRecord.H+ (i + 1));
		
		if (m==null) {
			 m = (HRecord) mRecords.getElement(AmadeusRecord.U	+ (i + 1));
		};
		
		return m;
	}
	private MRecord getMRecord() {
		MRecord m =  (MRecord) mRecords.getElement(AmadeusRecord.M);
		
		return m;
	}

	private void saveTicketFrom() throws Exception {

		saveCommissionValues();
		saveTaxesFrom();
		saveFareFrom();
		savePaysFrom();
		
		
		IRecord ir = (IRecord) mRecords.getElement(AmadeusRecord.I);

		int it = ir.getNumberOfTickets();

		for (int t = 0; t < it; t++) {

			String ticket = ir.getTicketNumber(t + 1);
			String emdticket = null;
			
			String tickettype = ir.getTicketType(t + 1);
			
			this.endorsement = ir.getEndorsement(t+1);
			
			Passenger pass = new Passenger();
			pass.passengerName = ir.getPaxName(t + 1);
			pass.passengerIdentFiscal = ir.getPaxIdentFiscal();
			pass.passengerType = ir.getPassengerTypeIn(t + 1);
			mPass.addElement(ticket, pass);
			
			if (IRecord.isEMD()) {
				tickettype="D";
				isEMD = true;
			}

			emdid = null;
			if (isEMD) {
				Object[] ids = IRecord.getIds();
				for ( int idx=0 ; idx < IRecord.countEMDs(); idx ++ ) {
				   emdid=ids[idx].toString();
				   emdticket= IRecord.getEMDTicket(emdid);
				   String type = IRecord.getEMDCardType(emdid);
				   String name = IRecord.getEMDName(emdid);
				   String passtype = IRecord.getEMDType(emdid);
				   if (name!=null) {
				  	  pass = new Passenger();
							pass.passengerName = name;
							pass.passengerType = passtype;
							mPass.addElement(emdticket, pass);
				   }
				   
				   if (type!=null)
				     saveEMDPaysFrom(emdticket, IRecord.getEMDAmount(emdid), IRecord.getEMDCardnumber(emdid), type, IRecord.getEMDAuth(emdid));
				   String aircode = ir.getAirlineCode(t + 1);
				   
				   if (aircode==null) {
					   String emdairline = IRecord.getEMDAirline(emdid);
					   BizCarrier carr = new BizCarrier();
					   carr.dontThrowException(true);
					   carr.addFilter("cod_iata", emdairline);
					   if (carr.read()) {
						   aircode=carr.getCarrier();
					   }
				   }
				   
				   this.saveTicket(emdticket, isInternational, aircode,
						   	(t + 1) + "", tickettype, "0",
							   ir.getOriginalTicketNumber(t + 1));
				   if (ticket!=null) {
					   if ( emdticket.equalsIgnoreCase(ticket)==false ) {
						   isEMD=false;
						   this.saveTicket(ticket, isInternational, ir.getAirlineCode(t + 1),
								   	(t + 1) + "", tickettype, "0",
									   ir.getOriginalTicketNumber(t + 1));
						   isEMD=true;
					   }
				   }
			   
				}
			} else {
			
				
			   this.saveTicket(ticket, isInternational, ir.getAirlineCode(t + 1),
				   	(t + 1) + "", tickettype, "0",
					   ir.getOriginalTicketNumber(t + 1), ir.getTourCode(t+1));
			}
		}

	}
	
	String emdid;
	
	protected String getVendor() {
		CRecord c = (CRecord) mRecords.getElement(AmadeusRecord.C);
		if (c==null)
			return null;
		return c.getVendor();
	}

	
	protected void saveFareSegment(BizPNRTicket t) throws Exception {
		QRecord m = (QRecord) mRecords.getElement(AmadeusRecord.Q);
		if (m==null) return;
		saveFareSegment( t,  m) ;
	}


	
	protected EMD getEMDbyID() {
		int i=0;
		EMD e = null; 
		EMDRecord emd = (EMDRecord) mRecords.getElement("EMD" + (i+1));

		while (emd!=null) {
			
			if (emdid.equals(emd.getEMDID())) {
				e = new EMD();
				e.baseFare = emd.getNetFare();
				e.fare = emd.getFare();
				e.netCurrency = emd.getNetCurrency();
				e.totalCurrency = emd.getTotalCurrency();
				e.fareWithTax = emd.getTotalFare();
				e.taxAmount = emd.getTaxAmount();
				e.taxCode = emd.getTaxCode();
				e.taxCurrency = emd.getTaxCurrency();
				e.carrier = emd.getAirlane();
				break;
			}
			 i++;
			 emd = (EMDRecord) mRecords.getElement("EMD" + (i+1));
			 if (emd==null)
				 break;
			
		}
		
		return e;
	}


	private void saveCommissionValues() throws Exception {

		KRecord k = (KRecord) mRecords.getElement(AmadeusRecord.K);

		if (k != null) {
			if (k.isEmpty())
				k = null;
		}

		if (k == null) {
			k = (KNRecord) mRecords.getElement(AmadeusRecord.KN);
		}
		if (k == null)
			return;

		IRecord ir = (IRecord) mRecords.getElement(AmadeusRecord.I);

		int it = ir.getNumberOfTickets();

		for (int t = 0; t < it; t++) {

			String ticket = ir.getTicketNumber(t + 1);

			double fareamount=Double.parseDouble(k.getTotalFare());
			if (k.isReIssued()) {
				isReissued = true;
				KFTRecord kft = (KFTRecord) mRecords
						.getElement(AmadeusRecord.KFT);
				if (kft == null) {
					kft = (KNTYRecord) mRecords.getElement(AmadeusRecord.KNTY);
				}
				
				if (kft!=null )
					fareamount = Double.parseDouble(k.getTotalFare())
						- kft.getTotalTax();
				else {
					fareamount = Double.parseDouble(k.getFare());
					if (fareamount == 0.0f)
						fareamount = Double.parseDouble(k.getBaseFare());

				}
			} else {
				fareamount = Double.parseDouble(k.getFare());
				if (fareamount == 0.0f)
					fareamount = Double.parseDouble(k.getBaseFare());

			}

			double commission = Double.parseDouble(ir
					.getCommissionAmount(t + 1));
			double percentaje = Double.parseDouble(ir
					.getCommissionPercentaje(t + 1));

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

	private void saveFareFrom() throws Exception {

		
		KRecord k = (KRecord) mRecords.getElement(AmadeusRecord.K);

		if (k != null) {
			if (k.isEmpty())
				k = null;
		}

		if (k == null) {
			k = (KNRecord) mRecords.getElement(AmadeusRecord.KN);
		}

		if (k == null)
			return;

		IRecord ir = (IRecord) mRecords.getElement(AmadeusRecord.I);

		int it = ir.getNumberOfTickets();

		for (int t = 0; t < it; t++) {

			String ticket = ir.getTicketNumber(t + 1);

			double netamount=0;
			double fareamount=0;
			
			String fee = ir.getAdditionalFee(t+1);
			String costofee = ir.getCostoFee(t+1);
			String ordenfee = ir.getOrdenFee(t+1);
			String formapago= ir.getFormaPagoFee(t+1);
			String centrocosto = ir.getCentroCosto(t+1);
			String solicitante = ir.getSolicitante(t+1);
			String cliente = ir.getCliente(t+1);
			String consumo = ir.getConsumo(t+1);
			String email = ir.getEmail(0);
			
			String er = k.getCurrencyExchange();
			double totalfare = !JTools.isNumber(k.getTotalFare(),true)? 0: Double.parseDouble(k.getTotalFare());
			double der = Double.parseDouble(er);		
			double imp=0;
			double total = Double.parseDouble(k.getTotalFare());
			if (k.isReIssued()) {
				PssLogger.logDebug("REEMISION AMADEUS");
				KFTRecord kft = (KFTRecord) mRecords.getElement(AmadeusRecord.KFT);
				if (kft == null) {
					kft = (KNTYRecord) mRecords.getElement(AmadeusRecord.KNTY);
				}  
				imp= (kft==null?0:kft.getTotalTax());
				KSRecord k2 = (KSRecord) mRecords.getElement("KS-");
				if (k2!=null) {
				   k=k2;
				   total = Double.parseDouble(k.getTotalFare());
				   netamount = total- imp;
				   if (netamount>0)
					   fareamount = total ;
				} else {
					netamount = !JTools.isNumber(k.getFare(),true)?0:Double.parseDouble(k.getFare());//RJL algunos casos viene ARSARS
					fareamount = JTools.forceRd( total- imp, 2);
			
				}
				
				//if (this.getCompany().equals("BIBAM") && kft!=null) {
					if (totalfare<=netamount ) {
						netamount = JTools.forceRd( totalfare - imp, 2);
					}
				//}
			} else {
				netamount = Double.parseDouble(k.getFare());
				fareamount = Double.parseDouble(k.getBaseFare());
			}

			if (netamount == 0.0f)
				netamount = fareamount;
			
			
		//	if (this.getCompany().equals("BIBAM")) {
			
				if (er.equals("1")==false ) {
					if ( netamount/der != fareamount ) {
				  	fareamount=fareamount/der;
					}
				}
			
		//	}
			
			if (totalfare==0) {
				fareamount = 0;
				netamount = 0;
			}

			Fare fare = new Fare();
			fare.baseCurrency = k.getCurrencyBaseFare();
			fare.baseFare = fareamount;
			fare.totalCurrency = k.getCurrencyTotalFare();
			fare.exchangeRate = Double.parseDouble(er);
			fare.baseFareWithTax = Double.parseDouble(k.getTotalFare());
			fare.fare = netamount;
			fare.isEMD = ir.isEMD(t+1);
			fare.fee = fee;
			fare.costoFee = costofee;
			fare.ordenFee = ordenfee;
			fare.pagoFee = formapago;
			fare.centroCosto=centrocosto;
			fare.solicitante=solicitante;
			fare.cliente=cliente;
			fare.consumo=consumo;
			fare.mail=email;

			mFares.addElement(ticket, fare);

			boolean exist = priceExists(ticket);
			this.savePrice(exist, k.getCurrencyBaseFare(),
					k.getCurrencyTotalFare(), ticket,
					Double.parseDouble(k.getTotalFare()), fareamount,
					netamount, er);
			// } else {
			// updatePrice(k.getCurrencyBaseFare(), k.getCurrencyTotalFare(),
			// ticket,
			// Double.parseDouble(k.getTotalFare()), fareamount, netamount, er);
			// }
		}

	}

	private void saveTaxesFrom() throws Exception {

		KFTRecord kft = (KFTRecord) mRecords.getElement(AmadeusRecord.KFT);

		if (kft == null)
			kft = (KNTIRecord) mRecords.getElement(AmadeusRecord.KNTI);
		if (kft == null)
			kft = (KNTYRecord) mRecords.getElement(AmadeusRecord.KNTY);
		if (kft == null)
			kft = (KFTFRecord) mRecords.getElement(AmadeusRecord.KFTF);

		IRecord ir = (IRecord) mRecords.getElement(AmadeusRecord.I);
		if (kft == null)
			return;

		int im = kft.getTaxCount();
		int it = ir.getNumberOfTickets();

		for (int ticket = 0; ticket < it; ticket++) {

			String ticketNumber = ir.getTicketNumber(ticket + 1);
			JMap<String, TaxCode> taxcodes = JCollectionFactory.createMap();

			for (int i = 0; i < im; i++) {

				String taxid1 = kft.getTaxCode(i + 1);
				String taxamount = kft.getTaxAmount(i + 1);
				String currency = kft.getTaxCurrency(i + 1);

				// saveTaxTicketInformation(ticketNumber, taxid1, taxamount,
				// currency);

				TaxCode tax = new TaxCode();
				tax.amount = Double.parseDouble(taxamount);
				tax.currency = currency;
				
				PssLogger.logDebug("Tax Code: " + taxid1);
				PssLogger.logDebug("Tax Amount: " + tax.amount);
				PssLogger.logDebug("Tax Currency: " + tax.currency);

				taxcodes.addElement(taxid1, tax);

			}
			mTaxes.addElement(ticketNumber, taxcodes);

		}

	}

	/**
	 * @param ticketNumber
	 * @param taxid1
	 * @param taxamount
	 * @param currency
	 * @throws Exception
	 */
	private void saveTaxTicketInformation(String ticketNumber, String taxid1,
			String taxamount, String currency) throws Exception {

		if (taxamount == null)
			taxamount = "0";
		if (JTools.isNumber(taxamount) == false)
			taxamount = "0";

		BizPNRTax tax = new BizPNRTax();
		tax.dontThrowException(true);
		if (tax.read(this.getCompany(), this.getPnrLocator(), ticketNumber,
				taxid1)) {
			tax.setCompany(company);
			tax.setId(interfaceId);
			tax.setImporte(Double.parseDouble(taxamount));
			tax.update();
		} else {
			if (taxid1 == null)
				return;
			tax.setCompany(company);
			tax.setId(interfaceId);
			tax.setCodigopnr(this.getPnrLocator());
			tax.setSecuencia(1);
			tax.setNumeroBoleto(ticketNumber);
			if (currency == null)
				currency = "ARS";
			if (currency.equals(""))
				currency = "ARS";
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

		IRecord ir = (IRecord) mRecords.getElement(AmadeusRecord.I);
		KRecord k = (KRecord) mRecords.getElement(AmadeusRecord.K);

		if (k == null)
			 k = (KRecord) mRecords.getElement(AmadeusRecord.KN);

		if (k == null)
			return;

		int it = ir.getNumberOfTickets();

		for (int t = 0; t < it; t++) {

			String ticket = ir.getTicketNumber(t + 1);

			String cc = ir.getCreditCardNumber(t + 1);
			if (cc == null)
				continue;
			String auth = ir.getCreditCardAuth(t + 1);
			
			String amount = k.getTotalFare();
			String curr = k.getCurrencyTotalFare();
			String type = ir.getFormOfPaymentDesc(t + 1);
			String amountpay = ir.getAmountPaid(t + 1);

			 if (Double.parseDouble(amount)>=Double.parseDouble(amountpay) && amountpay.equals("0") == false)
				amount = amountpay;
			

			Pay pay = new Pay();
			pay.amount = Double.parseDouble(amount);
			pay.card = cc;
			pay.cardName= type;
			pay.auth = auth;
			mPays.addElement(ticket, pay);

		}
		
		

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
