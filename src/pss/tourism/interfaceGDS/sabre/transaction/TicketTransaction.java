package pss.tourism.interfaceGDS.sabre.transaction;

import java.util.Date;

import kotlin.coroutines.AbstractCoroutineContextKey;
import pss.bsp.bspBusiness.BizBSPCompany;
import pss.bsp.clase.BizClase;
import pss.bsp.clase.detalle.BizClaseDetail;
import pss.bsp.reembolsos.BizReembolso;
import pss.common.regions.company.BizCompany;
import pss.common.regions.currency.history.BizMonedaCotizacion;
import pss.core.services.records.JRecords;
import pss.core.tools.JDateTools;
import pss.core.tools.JPair;
import pss.core.tools.JTools;
import pss.core.tools.PssLogger;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JList;
import pss.core.tools.collections.JMap;
import pss.tourism.airports.BizAirport;
import pss.tourism.carrier.BizCarrier;
import pss.tourism.interfaceGDS.BaseTransaction;
import pss.tourism.interfaceGDS.FareRecord;
import pss.tourism.interfaceGDS.FareRecord.ConnectionRecord;
import pss.tourism.interfaceGDS.Tools;
import pss.tourism.interfaceGDS.sabre.record.AARecord;
import pss.tourism.interfaceGDS.sabre.record.M0Record;
import pss.tourism.interfaceGDS.sabre.record.M1Record;
import pss.tourism.interfaceGDS.sabre.record.M2Record;
import pss.tourism.interfaceGDS.sabre.record.M3Record;
import pss.tourism.interfaceGDS.sabre.record.M4Record;
import pss.tourism.interfaceGDS.sabre.record.M5Record;
import pss.tourism.interfaceGDS.sabre.record.M6Record;
import pss.tourism.interfaceGDS.sabre.record.M7Record;
import pss.tourism.interfaceGDS.sabre.record.M8Record;
import pss.tourism.interfaceGDS.sabre.record.MGRecord;
import pss.tourism.interfaceGDS.sabre.record.MXRecord;
import pss.tourism.interfaceGDS.sabre.record.SabreRecord;
import pss.tourism.pnr.BizPNRConnectedTicket;
import pss.tourism.pnr.BizPNROtro;
import pss.tourism.pnr.BizPNRReserva;
import pss.tourism.pnr.BizPNRSegmentoAereo;
import pss.tourism.pnr.BizPNRTax;
import pss.tourism.pnr.BizPNRTicket;
import pss.tourism.pnr.PNRCache;
import pss.tourism.voidManual.BizVoidManual;

public class TicketTransaction extends BaseTransaction {

	protected M0Record m0;

	protected AARecord AA;

	BizPNRTicket tkOrig;
	
	BizPNRTicket getObjTicketOriginal(String ticketNumber) throws Exception {
		//if (tkOrig!=null) return tkOrig;
		tkOrig = new BizPNRTicket();
		tkOrig.dontThrowException(true);
		if (!tkOrig.ReadByBoleto(getCompany(), ticketNumber))
			return null;

		return tkOrig;
		
	}

	public void save(JMap<String, Object> mRecords) throws Exception {
		this.mRecords = mRecords;
		m0 = ((M0Record) mRecords.getElement(SabreRecord.M0));
		AA = ((AARecord) mRecords.getElement(SabreRecord.AA));
		this.setPnrLocator(m0.getPNRLocator());
		findYear();
		//if (hasAIRTicket()) {
			this.saveTicket();
//		}
		
		saveOtherTickets();
		saveReserva();
			
	}

	private boolean hasAIRTicket() {
		try {
			int im = m0.getNumberOfM3();
			for (int i = 0; i < im; i++) {
				M3Record m3 = (M3Record) (mRecords.getElement(SabreRecord.M3 + (i + 1)));
				if (m3.isAirItinerary())
					return true;
			}
		} catch (Exception ee) {

		}
		return false;
	}

	private void saveOtherTickets() throws Exception {
		int im = m0.getNumberOfM3();
		for (int i = 0; i < im; i++) {
			M3Record m3 = (M3Record) (mRecords.getElement(SabreRecord.M3 + (i + 1)));
			if (m3==null||m3.isAirItinerary()) continue;
			saveOtherTicketFromM3(m3);
		}
	}

	private void saveReserva() throws Exception {
		if (!m0.isReserva()) return;
		int im = m0.getNumberOfM1();

		for (int i = 0; i < im; i++) {
			M1Record m1 = (M1Record) (mRecords.getElement(SabreRecord.M1 + (i + 1)));
			if (m1==null) continue;
				saveOneReserva(m1);
		}

	}

	private void assignPassenger(BizPNRTicket tk) throws Exception {
		M1Record m1 = this.findM1ByPax(tk.getNumeropasajero());
		if (m1 != null) {
			tk.setNombrePasajero(m1.getPaxName());
			tk.setIdentFiscal(m1.getPaxFiscalId());
		}
		M2Record m2 = this.findM2ByPax(m1.getPaxId());
		if (m2 != null)
			tk.setTipoPasajero(m2.getPassengerType());
	}

	private void assignPassengerM5(BizPNRTicket tk) throws Exception {
		M1Record m1 = this.findM1ByPax(tk.getNumeropasajero());
		if (m1 != null) {
			tk.setNombrePasajero(m1.getPaxName());
			tk.setIdentFiscal(m1.getPaxFiscalId());
		}
		// M5Record m2 = this.findM5ByTicket(m1.getPaxId(),false);
		// if (m2!=null) tk.setTipoPasajero(m2.getPassengerType());
	}

	private boolean assignExtraInfoEMD(BizPNRTicket tk,String paxId,String ticketnumber) throws Exception {
		MGRecord mg = this.findMGByPax(paxId,ticketnumber);
		if (mg != null) {
			tk.setCreationDate(mg.getFieldValueAsDateLong(MGRecord.CREATION_LOCAL_DATE));
			return true;
		}
		return false;
	}
	private boolean hasExtraInfoEMD(String ticketnumber) throws Exception {
		MGRecord mg = this.findMGByTicketnumber(ticketnumber);
		if (mg != null) {
			return true;
		}
		return false;
	}
	private void saveConjunctionTicketsFrom() throws Exception {

		int im = m0.getNumberOfM2();
		for (int i = 0; i < im; i++) {
			M2Record m2 = (M2Record) (mRecords.getElement(SabreRecord.M2 + (i + 1)));
			int ctc = m2.getConjuctionTicketCount() - 1;
			if (ctc > 0) {
				for (int j = 1; j <= ctc; j++) {
					BizPNRConnectedTicket ct = new BizPNRConnectedTicket();
					ct.dontThrowException(true);
					if (ct.read(company, m2.getTicketNumber(), (ctc + 1 + j) + ""))
						continue;
					ct.setCompany(company);
					ct.setTicket(m2.getTicketNumber());
					ct.setConnectedTicket((ctc + 1 + j) + "");
					ct.insert();
				}
			}
		}

	}

	private boolean inCodes(String codes, String id) throws Exception {
		if (codes == null)
			return true;
		for (int i = 0; i < codes.length(); i += 2) {
			String code = codes.substring(i, i + 2);
			if (code.equals(JTools.LPad(id, 2, "0")))
				return true;
		}
		return false;
	}

	private void saveAirSegmentFromM4(BizPNRTicket tk, M2Record m2, String m4codes) throws Exception {
		String firstCarrier = null;
		int im4 = m0.getNumberOfM4();
//		if (im4>0)
//			tk.getSegments().delete();
		JRecords<BizPNRSegmentoAereo> segments = new JRecords<BizPNRSegmentoAereo>(BizPNRSegmentoAereo.class);
		segments.setStatic(true);
		for (int j = 0; j < im4; j++) {
			M4Record m4 = (M4Record) (mRecords.getElement(SabreRecord.M4 + (j + 1)));
			if (!inCodes(m4codes, "" + (j + 1)))
				continue;
			if (m4==null) continue;

			int im3 = m0.getNumberOfM3();
			for (int k = 0; k < im3; k++) {
				M3Record m = (M3Record) (mRecords.getElement(SabreRecord.M3 + (k+1)));
				
				if (m==null) continue;
				if (m4.getFieldValue(M4Record.M3_RECORD_ITEM_NUMBER).equals("OP")) continue;
				if (Integer.parseInt(m.getItemNumber())!=Integer.parseInt((m4.getFieldValue(M4Record.M3_RECORD_ITEM_NUMBER)))) continue;
				boolean segmentoInternacional = false;
				if (m==null || ( !m.isAirItinerary() && !m.isBusItinerary()))
					continue;

				BizPNRSegmentoAereo sa = new BizPNRSegmentoAereo();
				sa.setId(tk.getId());
				sa.setCompany(tk.getCompany());
				sa.setCodigoPNR(tk.getCodigopnr());
				sa.setCodigoSegmento(m.getItemNumber());

				if (m.isAirItinerary()) {
					sa.setDespegue(m.getDepartureCityCode());
					sa.setGeoDespegue(this.findGeoAirport(sa.getDespegue()));
					sa.setArrivo(m.getArrivalCityCode());
					sa.setGeoArrivo(this.findGeoAirport(sa.getArrivo()));
					checkAerolinea(m.getCarrierCode());
					checkAerolinea(m.getCarrierCodeOp());
					sa.setCarrier(m.getCarrierCode());
					sa.setCarrierPlaca(tk.getCarrier());
					sa.setCarrierOp(m.getCarrierCodeOp());
					sa.setRuta(m.getDepartureCityCode() + "/" + m.getArrivalCityCode());

					BizAirport airportDespegue = sa.getObjAeropuertoDespegue();
					BizAirport airportArribo = sa.getObjAeropuertoArrivo();

					if (airportArribo.getCountry().equals(tk.getObjCompany().getCountry()))
						sa.setPaisDestino(airportDespegue.getCountry());
					else
						sa.setPaisDestino(airportArribo.getCountry());

					segmentoInternacional = !airportDespegue.getCountry().equals(tk.getObjCompany().getCountry()) || !airportDespegue.getObjCountry().GetPais().equals(airportArribo.getObjCountry().GetPais());
					if (firstCarrier == null && segmentoInternacional) {
						firstCarrier = m.getCarrierCode();
					}

					sa.setNumeroVuelo(m.getFlightNumber());
					sa.setClase(m.getClassOfService());
					BizClaseDetail cd =  PNRCache.getTipoClaseCache(getCompany(), tk.getCarrier(), m.getClassOfService(),segmentoInternacional);
					if (cd == null) {
						sa.setTipoClase(BizClase.getTipoClaseDefault(m.getClassOfService()));
						sa.setPrioridad(BizClase.getPrioridadClaseDefault(m.getClassOfService()));
					} else {
						sa.setTipoClase(cd.getObjClase().getDescripcion());
						sa.setPrioridad(cd.getPrioridad());
					}
					sa.setFechaDespegue(JDateTools.StringToDate(m.getDepartureDate(year, tk.getCreationDate()), "yyyyMMdd"));
					sa.setFechaArrivo(JDateTools.StringToDate(m.getArrivalDate(year, tk.getCreationDate()), "yyyyMMdd"));
					sa.setHoraDespegue(m.getDepartureTime());
					sa.setHoraArrivo(m.getArrivalTime());
				//	if (m.isAirItinerary()) {
						sa.setEstado(m.isHoldingConfirmed() ? "OK" : m.getSegmentStatusCode());
						sa.setCodigoComida(m.getMealServiceCode());
						sa.setTipoEquipo(m.getEquipmentTypeCode());
						sa.setCodigoEntreten(m.getMealServiceCode());
						sa.setDuracion(m.getElapsedTimeCode());
				//	}


					String ind = m4.getFieldValue(M4Record.CONNECTION_INDICATOR);
					sa.setConnectionIndicator(ind.equals(" ") || ind.equals("") ? BizPNRSegmentoAereo.SEGMENTO_START : (ind.equals("O") ? BizPNRSegmentoAereo.SEGMENTO_STOP : BizPNRSegmentoAereo.SEGMENTO_CONNECTION));
					String monto = m4.getFieldValue(M4Record.FARE_BY_LEG_DOLLAR_AMOUNT);
					double dmonto = monto.trim().equals("") || !JTools.isNumber(monto) ? 0 : Double.parseDouble(monto);
					if (monto.indexOf(".")==-1 && dmonto>Double.parseDouble(m2.getFareAmount())) dmonto/=100; //a veces viene sin el punto
					double dmontoOriginal = dmonto;

					JPair m6fare = getM6FromM3(m, m2);
					String moneda = m4.getFieldValue(M4Record.CURRENCY_CODE);
					if (moneda == null || moneda.trim().equals("") || moneda.trim().equals("@@") || moneda.trim().equals("@@@"))
						moneda = tkOrig!=null&&tkOrig.getCodigoMoneda()!=null?tkOrig.getCodigoMoneda():(!monto.equals("") && monto.indexOf(".")==-1)?tk.getCodigoBaseMoneda():"USD";
					if (!moneda.equals(tk.getCodigoBaseMoneda())) {
						if (moneda.equals("NUC") && m6fare != null && ((M6Record) m6fare.firstObject()) != null) {
							dmonto = dmonto * ((M6Record) m6fare.firstObject()).getDoubleRoe();
							moneda="USD";
						} else if (moneda.equals("NUC") ){
							dmonto = dmonto * 1;
							moneda="USD";						
						}
					} 
					String monBase=tk.getCodigoBaseMoneda();
					if (!moneda.equals("USD")) {
						double cot = BizMonedaCotizacion.readCotizacionCorrienteAt(tk.getCompany(), tk.getObjCompany().getCountry(), "USD",moneda, tk.getCreationDate());

						if (cot!=0){
							monBase="USD";
							dmontoOriginal = dmonto / cot;
							if (tk.getTipoCambio() != 0 && tk.getTipoCambio() != 1) {
								dmonto = dmontoOriginal * tk.getTipoCambio();
							}
						} else {
							double cotADolares = BizMonedaCotizacion.readCotizacionCorrienteAt(tk.getCompany(), tk.getObjCompany().getCountry(), "USD",tk.getCodigoMoneda(), tk.getCreationDate());
							double cotAMonNacional = tk.getTipoCambio();
							if (cotADolares!=0 && cotAMonNacional!=0){
								monBase="USD";
								dmonto =  (dmonto*cotAMonNacional);
							//	dmontoOriginal = dmonto;//(dmonto*cotAMonNacional)/cotADolares;
								
							}
						}
					}
					else {
						if (tk.getCodigoBaseMoneda().equals("USD") && tk.getTipoCambio()!=0) {
							monBase="USD";
							dmontoOriginal=dmonto;
							dmonto = dmontoOriginal * tk.getTipoCambio();
						
						} else {
							double cotADolares = BizMonedaCotizacion.readCotizacionCorrienteAt(tk.getCompany(), tk.getObjCompany().getCountry(), "USD",tk.getCodigoMoneda(), tk.getCreationDate());
							if (cotADolares!=0) {
								monBase="USD";
								dmontoOriginal=dmonto;
								dmonto = dmontoOriginal * cotADolares;
							}						
						}

					}
//					String monBaseTicket = tk.getCodigoBaseMoneda();
//					String monBase = m4.getFieldValue(M4Record.CURRENCY_CODE);
//					String moneda = tk.getCodigoMoneda();
//			  	if (monBase == null || monBase.trim().equals("") || monBase.trim().equals("@@") || monBase.trim().equals("@@@"))
//			  		monBase = "USD";
//						if (!monBase.equals("USD")) {
//							double cot = BizMonedaCotizacion.readCotizacionCorrienteAt(tk.getCompany(), tk.getObjCompany().getCountry(), "USD",monBase, tk.getCreationDate());
//	
//							if (cot!=0){
//								monBase="USD";
//								dmontoOriginal = dmonto / cot;
//							}
//						}	else {
//							if (monBaseTicket.equals(monBase)) {
//								if (tk.getTipoCambio() != 0 && tk.getTipoCambio() != 1) {
//									dmonto = dmonto * tk.getTipoCambio();
//								}							
//							} else {
//								double cot = BizMonedaCotizacion.readCotizacionCorrienteAt(tk.getCompany(), tk.getObjCompany().getCountry(), monBase,moneda, tk.getCreationDate());
//								
//								if (cot!=0){
//									dmonto = dmonto * cot;
//								}
//								
//							}
//
//						}
					if (m6fare != null && m6fare.secondObject() != null) {
						((FareRecord) m6fare.secondObject()).emitido = true;
						sa.setIdFare(((FareRecord) m6fare.secondObject()).id);
						if (((FareRecord) m6fare.secondObject()).prioridad < sa.getPrioridad()) {
							((FareRecord) m6fare.secondObject()).clase = sa.getClase();
							((FareRecord) m6fare.secondObject()).tipoClase = sa.getTipoClase();
							((FareRecord) m6fare.secondObject()).prioridad = sa.getPrioridad();
						}
					}
					sa.setFareBasic(m4.getFieldValue(M4Record.FARE_BASIS_CODE));
					sa.setFareBasicExpanded(m4.getFieldValue(M4Record.FARE_BASIS_CODE_EXPANDED)+m4.getFieldValue(M4Record.TICKET_DESIGNATOR_EXPANDED));
					if (sa.getObjCarrier()!=null && sa.getObjCarrier().getObjLogica()!=null) {
						sa.getObjCarrier().getObjLogica().doCarrierLogica(sa.getFareBasic(), sa);
					} else {
						sa.setFamiliaTarifaria(sa.getFareBasic() == null || sa.getFareBasic().length() < 6 ? "" : sa.getFareBasic().substring(3, 5));
					}
					sa.setLimitePeso(m4.getFieldValue(M4Record.BAGGAGE_ALLOWANCE_WEIGHT_LIMIT));
					sa.setMonedaBase(monBase);
					sa.setMoneda(tk.getCodigoMoneda());
					sa.setMontoOriginal(dmontoOriginal);
					sa.setMonto(dmonto);
					sa.setEmitido(true);
					segments.addItem(sa);
				}
			}

		}

	
		finalyzeSegments(tk,segments);

	}


	private void saveAirSegmentFromM2(BizPNRTicket tk, M2Record m2, String codes) throws Exception {
		String firstCarrier = null;
		tk.getSegments().delete();
		boolean international = false;
		Date fechaSalida = null;
		JRecords<BizPNRSegmentoAereo> segments = new JRecords<BizPNRSegmentoAereo>(BizPNRSegmentoAereo.class);
		segments.setStatic(true);
		String clase = null;
		String tipoclase = null;
		long clasePrioridad = 0;

		int im3 = m0.getNumberOfM3();
		for (int i = 0; i < im3; i++) {
			M3Record m = (M3Record) (mRecords.getElement(SabreRecord.M3 + (i + 1)));
			boolean segmentoInternacional = false;
			if (!m.isAirItinerary() && !m.isBusItinerary())
				continue;

			if (!inCodes(codes, m.getFieldValue(M3Record.ITINERARY_ITEM_NUMBER)))
				continue;

			if (fechaSalida == null) {
				fechaSalida = JDateTools.StringToDate(m.getDepartureDate(getYear(), getCreationDate()), "yyyyMMdd");
			}
			BizPNRSegmentoAereo sa = new BizPNRSegmentoAereo();
			sa.setId(tk.getId());
			sa.setCompany(tk.getCompany());
			sa.setCodigoPNR(tk.getCodigopnr());
			sa.setCodigoSegmento(m.getItemNumber());

			if (m.isAirItinerary()) {
				sa.setDespegue(m.getDepartureCityCode());
				sa.setGeoDespegue(this.findGeoAirport(sa.getDespegue()));
				sa.setArrivo(m.getArrivalCityCode());
				sa.setGeoArrivo(this.findGeoAirport(sa.getArrivo()));
				checkAerolinea(m.getCarrierCode());
				checkAerolinea(m.getCarrierCodeOp());
				sa.setCarrier(m.getCarrierCode());
				sa.setCarrierPlaca(tk.getCarrier());
				sa.setCarrierOp(m.getCarrierCodeOp());
				sa.setRuta(m.getDepartureCityCode() + "/" + m.getArrivalCityCode());

				BizAirport airportDespegue = sa.getObjAeropuertoDespegue();
				BizAirport airportArribo = sa.getObjAeropuertoArrivo();

				if (airportArribo.getCountry().equals(tk.getObjCompany().getCountry()))
					sa.setPaisDestino(airportDespegue.getCountry());
				else
					sa.setPaisDestino(airportArribo.getCountry());

				segmentoInternacional = !airportDespegue.getCountry().equals(tk.getObjCompany().getCountry()) || !airportDespegue.getObjCountry().GetPais().equals(airportArribo.getObjCountry().GetPais());
				if (firstCarrier == null && segmentoInternacional) {
					firstCarrier = m.getCarrierCode();
				}
			}

			sa.setNumeroVuelo(m.getFlightNumber());
			sa.setClase(m.getClassOfService());
			BizClaseDetail cd =  PNRCache.getTipoClaseCache(getCompany(), tk.getCarrier(), m.getClassOfService(),segmentoInternacional);
			if (cd == null) {
				sa.setTipoClase(BizClase.getTipoClaseDefault(m.getClassOfService()));
				sa.setPrioridad(BizClase.getPrioridadClaseDefault(m.getClassOfService()));
			} else {
				sa.setTipoClase(cd.getObjClase().getDescripcion());
				sa.setPrioridad(cd.getPrioridad());
			}
			sa.setFechaDespegue(JDateTools.StringToDate(m.getDepartureDate(year, tk.getCreationDate()), "yyyyMMdd"));
			sa.setFechaArrivo(JDateTools.StringToDate(m.getArrivalDate(year, tk.getCreationDate()), "yyyyMMdd"));
			sa.setHoraDespegue(m.getDepartureTime());
			sa.setHoraArrivo(m.getArrivalTime());
			if (m.isAirItinerary()) {
				sa.setEstado(m.isHoldingConfirmed() ? "OK" : m.getSegmentStatusCode());
				sa.setCodigoComida(m.getMealServiceCode());
				sa.setTipoEquipo(m.getEquipmentTypeCode());
				sa.setCodigoEntreten(m.getMealServiceCode());
				sa.setDuracion(m.getElapsedTimeCode());
			}

			if (clase != null) {
				if (clasePrioridad < sa.getPrioridad()) {
					clase = sa.getClase();
					tipoclase = sa.getTipoClase();
					clasePrioridad = sa.getPrioridad();
				}
			} else {
				clase = sa.getClase();
				tipoclase = sa.getTipoClase();
				clasePrioridad = sa.getPrioridad();
			}
			if (m2 != null && !m2.getFieldValue(M2Record.ENTITLEMENT_ITEM_NUMBERS).equals("")) {
				boolean find = false;
				int im4 = m0.getNumberOfM4();
				for (int j = 0; j < im4; j++) {
					M4Record m4 = (M4Record) (mRecords.getElement(SabreRecord.M4 + (j + 1)));
					if (!m4.getFieldValue(M4Record.M3_RECORD_ITEM_NUMBER).equals(JTools.lpad(m.getFieldValue(M3Record.ITINERARY_ITEM_NUMBER), "0", 2)))
						continue;
					if (!inCodes(m2.getFieldValue(M2Record.ENTITLEMENT_ITEM_NUMBERS), "" + (j + 1)))
						continue;

					String ind = m4.getFieldValue(M4Record.CONNECTION_INDICATOR);
					sa.setConnectionIndicator(ind.equals(" ") || ind.equals("") ? BizPNRSegmentoAereo.SEGMENTO_START : (ind.equals("O") ? BizPNRSegmentoAereo.SEGMENTO_STOP : BizPNRSegmentoAereo.SEGMENTO_CONNECTION));
					String monto = m4.getFieldValue(M4Record.FARE_BY_LEG_DOLLAR_AMOUNT);
					double dmonto = monto.trim().equals("") || !JTools.isNumber(monto) ? 0 : Double.parseDouble(monto);
					double dmontoOriginal = dmonto;

					JPair m6fare = getM6FromM3(m, m2);
					String moneda = m4.getFieldValue(M4Record.CURRENCY_CODE);
					if (moneda == null || moneda.trim().equals("") || moneda.trim().equals("@@") || moneda.trim().equals("@@@"))
						moneda = tk.getCodigoBaseMoneda();
					if (!moneda.equals(tk.getCodigoBaseMoneda())) {
						if (moneda.equals("NUC") && m6fare != null && ((M6Record) m6fare.firstObject()) != null) {
							dmonto = dmonto * ((M6Record) m6fare.firstObject()).getDoubleRoe();
							moneda="USD";
						} else if (moneda.equals("NUC")) {
							dmonto = dmonto * 1;
							moneda="USD";
						}
					}
					String monBase=tk.getCodigoBaseMoneda();
					if (!moneda.equals("USD")) {
						double cot = BizMonedaCotizacion.readCotizacionCorrienteAt(tk.getCompany(), tk.getObjCompany().getCountry(), "USD",moneda, tk.getCreationDate());

						if (cot!=0){
							monBase="USD";
							dmontoOriginal = dmonto / cot;
						}
					}
					else {
						if (tk.getTipoCambio() != 0 && tk.getTipoCambio() != 1) {
							dmonto = dmonto * tk.getTipoCambio();
						}
					}
					if (m6fare != null && m6fare.secondObject() != null) {
						((FareRecord) m6fare.secondObject()).emitido = true;
						sa.setIdFare(((FareRecord) m6fare.secondObject()).id);
						if (((FareRecord) m6fare.secondObject()).prioridad < sa.getPrioridad()) {
							((FareRecord) m6fare.secondObject()).clase = sa.getClase();
							((FareRecord) m6fare.secondObject()).tipoClase = sa.getTipoClase();
							((FareRecord) m6fare.secondObject()).prioridad = sa.getPrioridad();
						}
					}
					sa.setFareBasic(m4.getFieldValue(M4Record.FARE_BASIS_CODE));
					sa.setFareBasicExpanded(m4.getFieldValue(M4Record.FARE_BASIS_CODE_EXPANDED)+m4.getFieldValue(M4Record.TICKET_DESIGNATOR_EXPANDED));
					if (sa.getObjCarrier()!=null && sa.getObjCarrier().getObjLogica()!=null) {
						sa.getObjCarrier().getObjLogica().doCarrierLogica(sa.getFareBasic(), sa);
					} else {
						sa.setFamiliaTarifaria(sa.getFareBasic() == null || sa.getFareBasic().length() < 6 ? "" : sa.getFareBasic().substring(3, 5));
					}

					sa.setLimitePeso(m4.getFieldValue(M4Record.BAGGAGE_ALLOWANCE_WEIGHT_LIMIT));
					sa.setMonedaBase(monBase);
					sa.setMoneda(tk.getCodigoMoneda());
					sa.setMontoOriginal(dmontoOriginal);
					sa.setMonto(dmonto);
					sa.setEmitido(true);
					find = true;

				}
				if (!find) {
					sa.setEmitido(false);
				}
				international |= segmentoInternacional;
				segments.addItem(sa);

			}

		}

		finalyzeSegments(tk,  segments);

	}
	private boolean saveAirSegmentFromTkOriginal(BizPNRTicket tk,  BizPNRTicket oldTicket) throws Exception {
		String firstCarrier = null;
		Date fechaSalida = null;
		boolean find =false;
		//tk.getSegments().delete();
		JRecords<BizPNRSegmentoAereo> segments = new JRecords<BizPNRSegmentoAereo>(BizPNRSegmentoAereo.class);
		segments.setStatic(true);
		JRecords<BizPNRSegmentoAereo> segmentsOrig = oldTicket.getSegments();
		JIterator<BizPNRSegmentoAereo> it = segmentsOrig.getStaticIterator();
		while (it.hasMoreElements()) {
			BizPNRSegmentoAereo seg = it.nextElement();
			BizPNRSegmentoAereo newseg = new BizPNRSegmentoAereo();
			newseg.copyProperties(seg);
			newseg.setId(tk.getId());
			find =true;

			int im3 = m0.getNumberOfM3();
			for (int i = 0; i < im3; i++) {
				M3Record m = (M3Record) (mRecords.getElement(SabreRecord.M3 + (i + 1)));
				boolean segmentoInternacional = false;
				if (!m.isAirItinerary() && !m.isBusItinerary())
					continue;

				if (Integer.parseInt(m.getItemNumber())!=newseg.getCodigoSegmento()) continue;
				if (fechaSalida == null) {
					fechaSalida = JDateTools.StringToDate(m.getDepartureDate(getYear(), getCreationDate()), "yyyyMMdd");
				}
				
				if (m.isAirItinerary()) {
					newseg.setDespegue(m.getDepartureCityCode());
					newseg.setGeoDespegue(this.findGeoAirport(newseg.getDespegue()));
					newseg.setArrivo(m.getArrivalCityCode());
					newseg.setGeoArrivo(this.findGeoAirport(newseg.getArrivo()));
					checkAerolinea(m.getCarrierCode());
					checkAerolinea(m.getCarrierCodeOp());
					newseg.setCarrier(m.getCarrierCode());
					newseg.setCarrierPlaca(tk.getCarrier());

					newseg.setCarrierOp(m.getCarrierCodeOp());
					newseg.setRuta(m.getDepartureCityCode() + "/" + m.getArrivalCityCode());

					BizAirport airportDespegue = newseg.getObjAeropuertoDespegue();
					BizAirport airportArribo = newseg.getObjAeropuertoArrivo();

					if (airportArribo.getCountry().equals(tk.getObjCompany().getCountry()))
						newseg.setPaisDestino(airportDespegue.getCountry());
					else
						newseg.setPaisDestino(airportArribo.getCountry());

					segmentoInternacional = !airportDespegue.getCountry().equals(tk.getObjCompany().getCountry()) || !airportDespegue.getObjCountry().GetPais().equals(airportArribo.getObjCountry().GetPais());
					if (firstCarrier == null && segmentoInternacional) {
						firstCarrier = m.getCarrierCode();
					}
				}

				newseg.setNumeroVuelo(m.getFlightNumber());
				newseg.setClase(m.getClassOfService());
				BizClaseDetail cd =  PNRCache.getTipoClaseCache(getCompany(), tk.getCarrier(), m.getClassOfService(),segmentoInternacional);
				if (cd == null) {
					newseg.setTipoClase(BizClase.getTipoClaseDefault(m.getClassOfService()));
					newseg.setPrioridad(BizClase.getPrioridadClaseDefault(m.getClassOfService()));
				} else {
					newseg.setTipoClase(cd.getObjClase().getDescripcion());
					newseg.setPrioridad(cd.getPrioridad());
				}
				PssLogger.logDebug(m.getDepartureDate(year, tk.getCreationDate()));
				newseg.setFechaDespegue(JDateTools.StringToDate(m.getDepartureDate(year, tk.getCreationDate()), "yyyyMMdd"));
				newseg.setFechaArrivo(JDateTools.StringToDate(m.getArrivalDate(year, tk.getCreationDate()), "yyyyMMdd"));
				newseg.setHoraDespegue(m.getDepartureTime());
				newseg.setHoraArrivo(m.getArrivalTime());
				if (m.isAirItinerary()) {
					newseg.setEstado(m.isHoldingConfirmed() ? "OK" : m.getSegmentStatusCode());
					newseg.setCodigoComida(m.getMealServiceCode());
					newseg.setTipoEquipo(m.getEquipmentTypeCode());
					newseg.setCodigoEntreten(m.getMealServiceCode());
					newseg.setDuracion(m.getElapsedTimeCode());
				}

				newseg.setEmitido(true);
				segments.addItem(newseg);
				break;
			}

			
		}
		if (find)
			finalyzeSegments(tk,segments);
		return find;
	}
	private void saveAirSegmentFromM5(BizPNRTicket tk, M5Record m5) throws Exception {
		String firstCarrier = null;
		tk.getSegments().delete();
		Date fechaSalida = null;
		JRecords<BizPNRSegmentoAereo> segments = new JRecords<BizPNRSegmentoAereo>(BizPNRSegmentoAereo.class);
		segments.setStatic(true);

		int im3 = m0.getNumberOfM3();
		for (int i = 0; i < im3; i++) {
			M3Record m = (M3Record) (mRecords.getElement(SabreRecord.M3 + (i + 1)));
			boolean segmentoInternacional = false;
			if (!m.isAirItinerary() )//&& !m.isBusItinerary())
				continue;

			
			if (fechaSalida == null) {
				fechaSalida = JDateTools.StringToDate(m.getDepartureDate(getYear(), getCreationDate()), "yyyyMMdd");
			}
			BizPNRSegmentoAereo sa = new BizPNRSegmentoAereo();
			sa.setId(tk.getId());
			sa.setCompany(tk.getCompany());
			sa.setCodigoPNR(tk.getCodigopnr());
			sa.setCodigoSegmento(m.getItemNumber());

			if (m.isAirItinerary()) {
				sa.setDespegue(m.getDepartureCityCode());
				sa.setGeoDespegue(this.findGeoAirport(sa.getDespegue()));
				sa.setArrivo(m.getArrivalCityCode());
				sa.setGeoArrivo(this.findGeoAirport(sa.getArrivo()));
				checkAerolinea(m.getCarrierCode());
				checkAerolinea(m.getCarrierCodeOp());
				sa.setCarrier(m.getCarrierCode());
				sa.setCarrierPlaca(tk.getCarrier());

				sa.setCarrierOp(m.getCarrierCodeOp());
				sa.setRuta(m.getDepartureCityCode() + "/" + m.getArrivalCityCode());

				BizAirport airportDespegue = sa.getObjAeropuertoDespegue();
				BizAirport airportArribo = sa.getObjAeropuertoArrivo();

				if (airportArribo.getCountry().equals(tk.getObjCompany().getCountry()))
					sa.setPaisDestino(airportDespegue.getCountry());
				else
					sa.setPaisDestino(airportArribo.getCountry());

				segmentoInternacional = !airportDespegue.getCountry().equals(tk.getObjCompany().getCountry()) || !airportDespegue.getObjCountry().GetPais().equals(airportArribo.getObjCountry().GetPais());
				if (firstCarrier == null && segmentoInternacional) {
					firstCarrier = m.getCarrierCode();
				}
			}

			sa.setNumeroVuelo(m.getFlightNumber());
			sa.setClase(m.getClassOfService());
			BizClaseDetail cd =  PNRCache.getTipoClaseCache(getCompany(), tk.getCarrier(), m.getClassOfService(),segmentoInternacional);
			if (cd == null) {
				sa.setTipoClase(BizClase.getTipoClaseDefault(m.getClassOfService()));
				sa.setPrioridad(BizClase.getPrioridadClaseDefault(m.getClassOfService()));
			} else {
				sa.setTipoClase(cd.getObjClase().getDescripcion());
				sa.setPrioridad(cd.getPrioridad());
			}
			PssLogger.logDebug(m.getDepartureDate(year, tk.getCreationDate()));
			sa.setFechaDespegue(JDateTools.StringToDate(m.getDepartureDate(year, tk.getCreationDate()), "yyyyMMdd"));
			sa.setFechaArrivo(JDateTools.StringToDate(m.getArrivalDate(year, tk.getCreationDate()), "yyyyMMdd"));
			sa.setHoraDespegue(m.getDepartureTime());
			sa.setHoraArrivo(m.getArrivalTime());
			if (m.isAirItinerary()) {
				sa.setEstado(m.isHoldingConfirmed() ? "OK" : m.getSegmentStatusCode());
				sa.setCodigoComida(m.getMealServiceCode());
				sa.setTipoEquipo(m.getEquipmentTypeCode());
				sa.setCodigoEntreten(m.getMealServiceCode());
				sa.setDuracion(m.getElapsedTimeCode());
			}

			sa.setEmitido(false);
			segments.addItem(sa);

		}

		finalyzeSegments(tk,segments);

	}

	private JPair getM6FromM3(M3Record m3, M2Record m2) throws Exception {
		int im6 = m0.getNumberOfM6();
		String ads = m3.getFieldValue(M3Record.DEPARTURE_CITY_CODE);
		String aar = m3.getFieldValue(M3Record.ARRIVAL_CITY_CODE);

		// busco por codigo de aeropuerto
		for (int i = 0; i < im6; i++) {
			M6Record m = (M6Record) (mRecords.getElement(SabreRecord.M6 + (i + 1)));
			if (m==null) continue;
			if (!inCodes(m2.getFieldValue(M2Record.M6_FARE_CALCULATION_ITEM_NUMBERS), "" + (i + 1)))
				continue;
			if (!m.getPassengerType().equals(m2.getFieldValue(M2Record.PASSENGER_TYPE)))
				continue;
			JIterator<FareRecord> it = m.getFareFields().getValueIterator();
			while (it.hasMoreElements()) {
				FareRecord fare = it.nextElement();
				if (fare.escalas == null)
					continue;
				JIterator<ConnectionRecord> itc = fare.escalas.getIterator();
				while (itc.hasMoreElements()) {
					ConnectionRecord conn = itc.nextElement();
					if (conn.airport_from==null) continue;
					if (conn.airport_to==null) continue;
					if (conn.airport_from.equals(ads) && conn.airport_to.equals(aar))
						return new JPair(m, fare);
				}
			}
		}

		String cds = BizAirport.getCiudad(ads);
		String car = BizAirport.getCiudad(aar);
		// si falla, busco por codigo de ciudad
		for (int i = 0; i < im6; i++) {
			M6Record m = (M6Record) (mRecords.getElement(SabreRecord.M6 + (i + 1)));
			if (m==null) continue;
			if (!inCodes(m2.getFieldValue(M2Record.M6_FARE_CALCULATION_ITEM_NUMBERS), "" + (i + 1)))
				continue;
			if (!m.getPassengerType().equals(m2.getFieldValue(M2Record.PASSENGER_TYPE)))
				continue;
			JIterator<FareRecord> it = m.getFareFields().getValueIterator();
			while (it.hasMoreElements()) {
				FareRecord fare = it.nextElement();
				if (fare.escalas == null)
					continue;
				JIterator<ConnectionRecord> itc = fare.escalas.getIterator();
				while (itc.hasMoreElements()) {
					ConnectionRecord conn = itc.nextElement();
					if (conn.airport_from==null) continue;
					if (conn.airport_to==null) continue;
					String m6cds = BizAirport.getCiudad(conn.airport_from);
					String m6car = BizAirport.getCiudad(conn.airport_to);
					if (m6car != null && m6cds != null)
						if (m6cds.equals(cds) && m6car.equals(car))
							return new JPair(m, fare);
				}
			}
		}

		return null;
	}

	protected FareRecord findViaje(BizPNRTicket tk, String codes, String from, String to, JList<ConnectionRecord> escalas) throws Exception {
		int im6 = m0.getNumberOfM6();
		for (int i = 0; i < im6; i++) {
			M6Record m = (M6Record) (mRecords.getElement(SabreRecord.M6 + (i + 1)));
			if (!m.getPassengerType().equals(tk.getTipoPasajero()))
				continue;
			if (!inCodes(codes, "" + (i + 1)))
				continue;
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
				continue;
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
		}
		return null;
	}

	private FareRecord findViajeM5(BizPNRTicket tk, String from, String to, JList<ConnectionRecord> escalas) throws Exception {
		int im6 = m0.getNumberOfM6();
		for (int i = 0; i < im6; i++) {
			M6Record m = (M6Record) (mRecords.getElement(SabreRecord.M6 + (i + 1)));
			if (!m.getPassengerType().equals(tk.getTipoPasajero()))
				continue;
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
				continue;
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
		}
		return null;
	}

	private void saveFareSegmentFrom(BizPNRTicket tk, String codes) throws Exception {
		tk.getSegmentsFare().delete();

		int im6 = m0.getNumberOfM6();
		for (int i = 0; i < im6; i++) {
			M6Record m = (M6Record) (mRecords.getElement(SabreRecord.M6 + (i + 1)));
			if(m==null) continue;
			if (!m.getPassengerType().equals(tk.getTipoPasajero()))
				continue;
			if (!inCodes(codes, "" + (i + 1)))
				continue;
			

			
			JIterator<FareRecord> it = m.getFareFields().getValueIterator();
			while (it.hasMoreElements()) {
				FareRecord fare = it.nextElement();
				String ruta = "";
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
				double importe = fare.importe == null ? 0 : fare.importe.trim().equals("") ? 0 : Double.parseDouble(JTools.getNumberEmbeddedWithDecSigned(fare.importe));
				double impuestoiv = fare.impuesto;
				double importeiv = importe;
				double totaliv = importeiv + impuestoiv;
				double impuestob = fare.impuesto;
				double importeb = importe;
				double totalb = importeb + impuestob;

				FareRecord m6 = findViaje(tk, codes, fare.airport_to, fare.airport_from, fare.escalas);
				if (m6 != null && m6.importe != null) {
					importeiv += (m6.importe.trim().equals("") ? 0 : Double.parseDouble(m6.importe));
					impuestoiv += m6.impuesto;
					totaliv = importeiv + impuestoiv;
				}

				if (m.getMoneda() != null && !m.getMoneda().equals(tk.getCodigoMoneda())) {
					if (m.getMoneda().equals("NUC")) {
						importe = importe * m.getDoubleRoe();
						importeiv = importeiv * m.getDoubleRoe();
						impuestoiv = impuestoiv * m.getDoubleRoe();
						totaliv = totaliv * m.getDoubleRoe();
					}
				}
				if (tk.getTipoCambio() != 0) {
					importe = importe * tk.getTipoCambio();
					importeiv = importeiv * tk.getTipoCambio();
					impuestoiv = impuestoiv * tk.getTipoCambio();
					totaliv = totaliv * tk.getTipoCambio();
				}
				double neto = importe - (importe * (tk.getComisionPerc() / 100.0));
				double netob = importeb - (importeb * (tk.getComisionPerc() / 100.0));
				double netoiv = importeiv - (importeiv * (tk.getComisionPerc() / 100.0));

				try {
					saveFareTicketInformation(tk.getId(), tk.getCompany(), tk.getCodigopnr(), m.getPassengerType(), fare.id, fare.airport_from, fare.airport_to, impuestoiv, importe, neto, ruta, fare.escalas.size(), fare.clase, fare.tipoClase, fare.emitido, tk.getCodigoBaseMoneda(), tk.getCodigoMoneda(), totalb, importeb, netob, totaliv, importeiv, netoiv, tk.getObjCompany().getCountry(), m6 != null);
				} catch (Exception e) {
					PssLogger.logError(e);// no descarto ticket por errores en el m6
				}

			}
		}

	}

	private void saveFareSegmentFromM5(BizPNRTicket tk) throws Exception {
		int im6 = m0.getNumberOfM6();

		if (im6>0)
			tk.getSegmentsFare().delete();

		for (int i = 0; i < im6; i++) {
			M6Record m = (M6Record) (mRecords.getElement(SabreRecord.M6 + (i + 1)));
			if (m==null) continue;
			JIterator<FareRecord> it = m.getFareFields().getValueIterator();
			while (it.hasMoreElements()) {
				FareRecord fare = it.nextElement();
				String ruta = "";
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

				FareRecord m6 = findViajeM5(tk, fare.airport_to, fare.airport_from, fare.escalas);
				if (m6 != null && m6.importe != null) {
					importeiv += (m6.importe.trim().equals("") ? 0 : Double.parseDouble(m6.importe));
					impuestoiv += m6.impuesto;
					totaliv = importeiv + impuestoiv;
				}

				if (m.getMoneda() != null && !m.getMoneda().equals(tk.getCodigoMoneda())) {
					if (m.getMoneda().equals("NUC")) {
						importe = importe * m.getDoubleRoe();
						importeiv = importeiv * m.getDoubleRoe();
						impuestoiv = impuestoiv * m.getDoubleRoe();
						totaliv = totaliv * m.getDoubleRoe();
					}
				}
				if (tk.getTipoCambio() != 0) {
					importe = importe * tk.getTipoCambio();
					importeiv = importeiv * tk.getTipoCambio();
					impuestoiv = impuestoiv * tk.getTipoCambio();
					totaliv = totaliv * tk.getTipoCambio();
				}
				double neto = importe - (importe * (tk.getComisionPerc() / 100.0));
				double netob = importeb - (importeb * (tk.getComisionPerc() / 100.0));
				double netoiv = importeiv - (importeiv * (tk.getComisionPerc() / 100.0));

				try {
					saveFareTicketInformation(tk.getId(), tk.getCompany(), tk.getCodigopnr(), m.getPassengerType(), fare.id, fare.airport_from, fare.airport_to, impuestoiv, importe, neto, ruta, fare.escalas.size(), fare.clase, fare.tipoClase, fare.emitido, tk.getCodigoBaseMoneda(), tk.getCodigoMoneda(), totalb, importeb, netob, totaliv, importeiv, netoiv, tk.getObjCompany().getCountry(), m6 != null);
				} catch (Exception e) {
					PssLogger.logError(e); //no descarto por errores en m6
				}

			}
		}

	}

	private void assignPnrDataFormM7(BizPNRTicket tk) throws Exception {
		int cant = m0.getNumberOfM7();
		M7Record m = (M7Record) (mRecords.getElement(SabreRecord.M7));
		if (m == null)
			return;
		String r = "";
		for (int i = 0; i < cant; i++) {
			r += m.getRemarkItem(i) + "\n";
		}
		tk.setRemarks(r);
	}

		
	private void saveTicket() throws Exception {
		JMap<String, String> procesados = JCollectionFactory.createMap();
		JIterator<M2Record> iterm2 = this.getM2Records(true).getIterator();
		while (iterm2.hasMoreElements()) {
			M2Record m2 = iterm2.nextElement();
			if (getM5(m2.getTicketNumber())!=null) continue;
			saveOneTicketFromM2(m2);

		}

		JIterator<M5Record> iter = this.getM5Records(true).getIterator();
		while (iter.hasMoreElements()) {
			M5Record m5 = iter.nextElement();

			M2Record m2 = getM2(m5.getTicketNumber());
			if (m2!=null) 
				saveOneTicketFromM2(m2);
			else {
				if (!JTools.isNumber(m5.getTicketNumber())) continue;
				if (procesados.getElement(m5.getTicketNumber()) != null)
					continue;
				procesados.addElement(m5.getTicketNumber(), m5.getTicketNumber());
				saveOneTicketFromM5(m5);
			}
		}
		
		
	};

	private void saveOneTicketFromM5(M5Record m5) throws Exception {
			boolean posteriorALaRemision = false;
			BizPNRTicket tk = new BizPNRTicket();
			

			BizPNRTicket tkOrig = getObjTicketOriginal(m5.getTicketNumber());
			if (tkOrig != null) {
				if (!this.getPnrFile().equals(tkOrig.getArchivo()) && tkOrig.isComplete()) {
					tk.dontThrowException(true);
					if (tk.ReadByBoleto(getCompany(), m5.getTicketNumber())) {
						if (!tk.isVoided()) {
							this.assignCommonM5(tk, null,m5, tkOrig);
							this.save(tk);
						}
					}

					return; // no es el archivo original, no reprocesar
				}
			}

			String aer = m5.getCarrierCode().length() > 2 ? m5.getCarrierCode().substring(0, 2) : m5.getCarrierCode();
			checkAerolinea(aer);
			tk.setCodigoaerolinea(aer);
			tk.setNumeroboleto(m5.getTicketNumber());
			tk.setComisionPerc(1d);
			tk.setNumeropasajero(m5.getPaxIdInt());
			tk.setInternacional(!m5.getMercado().equals("D"));
			tk.setInternacionalDescr(tk.isInternacional() ? "International" : "Domestic");
			tk.setDirectory(getDirectory());
			tk.setArchivo(this.getPnrFile());
			tk.setComplete(detectIsEMD(m5,tk.getNumeroboleto())); //;os emd, alcanza con el m5, si es ticket, es completo solo si viene de m2
			tk.setFechaProcesamiento(new Date());

			this.assignCommonM5(tk, null, m5, null);
			
			this.setearTarifasFromM5(tk);
			this.setearTarifasHistoricoFromM5(tk);
			this.assignPays(m5, null, tk);
				

			 JRecords<BizPNRTax> taxes = this.generateTaxesFormM5(m5, tk);
			if (taxes.getStaticItems().size() > 0)
				this.assignSumTaxes(tk, taxes,null);
//			int version = Integer.parseInt(JTools.getNumberEmbedded(getPnrFile().substring(getPnrFile().length()-6)));

			// RJL revisar
//			if (version==0 && JDateTools.getDaysBetween( tk.getPNRDate(),m0.getCreationDate(getYear()))>2) { //se perdio el original?
//				if (!posteriorALaRemision && tk.getCreationDate().after(tk.getPNRDate())){
//					tk.setCreationDate(tk.getPNRDate()); // BUG por perdida de pnr supongo que si hay mucha diferencia entre el la fecha de creacion y el pnr, y no es una remision debe ser viejo
//				} else {
//					tk.setCreationDate(m0.getCreationDate(getYear())); // BUG por perdida de pnr supongo que si hay mucha diferencia entre el la fecha de creacion y el pnr, y no es una remision debe ser viejo
//				}
//			}

			if (detectIsEMD(m5,tk.getNumeroboleto())) {
				assignExtraInfoEMD(tk,m5.getPaxId(),m5.getTicketNumber());
			}

			if (m5.isRemision()) {
				if (m5.getFieldValue(M5Record.E_DATA).length() >= 16) {
					String ticketOriginal = Tools.getOnlyNumbers(m5.getFieldValue(M5Record.E_DATA).substring(6, 16));
					tk.setEmision(false);
				 	tk.setOriginalNumeroboleto(ticketOriginal);
					BizPNRTicket pnrOriginal = findTicketFromBoleto(getCompany(), ticketOriginal);
					if (pnrOriginal != null) {
			//			tk.setCreationDate(pnrOriginal.getCreationDate());
						pnrOriginal.processReemitir(true);
						tk.setRefOriginal(pnrOriginal.getId());
					} else {
						addFaltante(getCompany(), ticketOriginal);
					}
				} 
			}
			
			// SG: La mande a basetransaction para usarla en Amadeus
			
			if (tk.acceptTicket()) {
				setTarifasEnDolares(tk);
				tk.setCalculed(false);
				tk.SetVision("UPDATE");
				this.save(tk);

				this.saveTaxesFormM5(m5, tk, taxes);
				//tk.calculeOver();
				
				tk.update();
				if (!detectIsEMD(m5,tk.getNumeroboleto())) {
					String numeroTicket = m5.getTicketNumber();
					boolean find=false;
					while (!find) {
						M5Record m5Reemisor = findReemisor(numeroTicket);
						if (m5Reemisor ==null) break;
						M2Record m2 = getM2(m5Reemisor.getTicketNumber());
						if (m2!=null) {
							this.saveAirSegmentFromM4(tk, m2, m2.getFieldValue(M2Record.ENTITLEMENT_ITEM_NUMBERS));
							this.saveFareSegmentFrom(tk, m2.getFieldValue(M2Record.M6_FARE_CALCULATION_ITEM_NUMBERS));
							find = true;
							break;
						} else {
							if (numeroTicket.equals(m5Reemisor.getTicketNumber())) 
								break;
							numeroTicket=m5Reemisor.getTicketNumber();
							continue;
						}
						
						
					} 
					if (!find) {
						find=tkOrig==null?false:(this.saveAirSegmentFromTkOriginal(tk, tkOrig));
						if (!find) {
							if (m0.getNumberOfM4()==0)
								saveAirSegmentFromM5(tk, m5);
							else { // si hay un m4, uso ese al parecer es mejor que tomar todo
								M2Record m2 = (M2Record) (mRecords.getElement(SabreRecord.M2 + (0 + 1)));
								if (m2!=null)
								saveAirSegmentFromM4(tk, m2, m2.getFieldValue(M2Record.ENTITLEMENT_ITEM_NUMBERS));
	
							}
							this.saveFareSegmentFromM5(tk);
						}
					}
				}
			}
		}

	public boolean detectIsEMD(M5Record rec, String ticketnumber) throws Exception {
		if (rec.getTipoOperacion().equals("EMD")) return true;
		if (hasExtraInfoEMD(ticketnumber)) return true;
		return false;
	}

	public void setearTarifasFromM5(BizPNRTicket tk) throws Exception {

		String nroTkOriginal = tk.getNumeroboleto();
		
		M5Record m5 = findM5ByTicket(nroTkOriginal, false);
		if (m5==null) return;

		String currLocal= getMonedaDefault(getCompany());
		String curr;
		double comision = 0;
		double comisionPerc =0;
		double saleamount =0;
		double xtamount = 0;
		double neto = 0;
		double base=0;
		double tax=0;
		double othertax=0;
		double netom5=0;
		tk.setCodigoMonedaLocal(BizCompany.getObjCompany(tk.getCompany()).getCurrency());

		
		int im5 = m0.getNumberOfM5();
		for (int i = 0; i < im5; i++) {
			M5Record m5a = (M5Record) (mRecords.getElement(SabreRecord.M5 + (i + 1)));
			if (m5a == null)
				continue;
			if (!m5.hasTicketNumber())
				continue;
			if (m5a.getPaxId()==null)
				continue;
			if (m5a.getPaxId().equals("99"))
				continue;
			if (m5a.getTicketNumber()==null) continue;
			if (!m5a.getTicketNumber().equals(nroTkOriginal)) continue;
			
			if (m5a.getCurrency()!=null)
				curr=m5a.getCurrency();
			else
				curr=currLocal;//tk.getCodigoMoneda()
			double cotiz = 1;
//			if (!curr.equals(currLocal)) {
//				double cotBase = curr.equals("USD")?1:BizMonedaCotizacion.readCotizacionCorrienteAt(tk.getCompany(), tk.getObjCompany().getCountry(), "USD",curr, tk.getCreationDate());
//				double cotLocal = currLocal.equals("USD")?1:BizMonedaCotizacion.readCotizacionCorrienteAt(tk.getCompany(), tk.getObjCompany().getCountry(), "USD",currLocal, tk.getCreationDate());
//
//				cotBase = cotBase==0?1:cotBase;
//				cotLocal = cotLocal==0?1:cotLocal;
//				cotiz = cotLocal/cotBase;
//				cotiz = cotiz==0?1:cotiz;
//				if (cotiz<1000 && cotiz!=1)
//					PssLogger.logInfo("debug point");
//
//			}		
			tk.setCodigoBaseMoneda(curr);
			tk.setCodigoMoneda(curr);

			comision = m5a.getComisionAmount();
			netom5 = m5a.getNetAmount();
			comisionPerc = m5a.getComisionPerc();
			saleamount = m5a.getSaleAmount();
			xtamount = ( m5a.getTaxLocal()+ m5a.getTaxAmount() + m5a.getOtherTaxAmount());
			neto = 0;
			if (comisionPerc != 0) {
				neto += ((netom5 / ((1 + (comisionPerc / 100.0)))));
				comision = (neto * comisionPerc) / 100.0;
			} else {
				neto += (netom5 - comision);
				comisionPerc = neto == 0 ? 0 : (comision * 100) / neto;
			}
			base += netom5;// m5a.getSaleAmount() - ( m5a.getTaxLocal()+m5a.getTaxAmount() + m5a.getOtherTaxAmount());
			tax += m5a.getTaxLocal();
			othertax +=m5a.getOtherTaxAmount()+m5a.getTaxAmount();
		
		}
		
	

		tk.setTarifaFactura(base);
		tk.setIvaFactura(tax);
		tk.setImpuestoFactura(othertax);
		tk.setComisionPerc(comisionPerc);
		tk.setComisionFactura(comision);
		tk.setNetoFactura(neto);
		tk.setTarifaBaseFactura(base);
		tk.setTarifaTotalFactura(saleamount);
		tk.setTarifaFacturadaYQ(saleamount);
		tk.setTipoOperacion(detectIsEMD(m5,tk.getNumeroboleto())?"EMD":"ETR");
		tk.setImpuestosTotalFactura(xtamount);

		tk.setTarifa(base);
		tk.setIva(tax);
		tk.setImpuestos(othertax);
		tk.setNeto(neto);
		tk.setTarifaBase(base);
		tk.setTarifaBaseConImpuestos(base+tax+othertax);
		tk.setTarifaYQ(base + tk.getYQ());

		double exchangeRate = tk.getTarifaBase() == 0 ? 1 : tk.getTarifa() / tk.getTarifaBase();
		if (exchangeRate > 0)
				tk.setTipoCambio(exchangeRate);
	}		
		
	public void setearTarifasHistoricoFromM5(BizPNRTicket tk) throws Exception {
		String nroTkOriginal = tk.getNumeroboleto();
		double tarifaAcum = 0;
		double baseAcum = 0;
		double ivaAcum = 0;
		double impuestoAcum = 0;
		double comisionAcum = 0;
		M5Record m5 = findM5ByTicket(nroTkOriginal, false);
		if (m5 == null)
			return;
		double comision = 0;
		double comisionPerc = 0;
		double saleamount = 0;
		double xtamount = 0;
		double neto = 0;
		String currLocal = getMonedaDefault(getCompany());
		String currBase = tk.getCodigoBaseMoneda();
		String curr;
		double cotiz = 1;
		double cotizABase = 1;
		double base = 0;
		double tax;
		double othertax;
		String reemision;
		if (!currBase.equals(currLocal)) {
			double cotBase = currBase.equals("USD") ? 1 : BizMonedaCotizacion.readCotizacionCorrienteAt(tk.getCompany(), tk.getObjCompany().getCountry(), "USD", currBase, tk.getCreationDate());
			double cotLocal = currLocal.equals("USD") ? 1 : BizMonedaCotizacion.readCotizacionCorrienteAt(tk.getCompany(), tk.getObjCompany().getCountry(), "USD", currLocal, tk.getCreationDate());

			if (cotBase==0) {
				cotiz= BizMonedaCotizacion.readCotizacionCorrienteAt(tk.getCompany(), tk.getObjCompany().getCountry(), currBase, currLocal, tk.getCreationDate());
			} else {
				cotBase = cotBase == 0 ? 1 : cotBase;
				cotLocal = cotLocal == 0 ? 1 : cotLocal;
				cotizABase = cotLocal / cotBase;
				cotizABase = cotizABase == 0 ? 1 : cotizABase;
				if (cotiz<1000 && cotiz!=1)
					PssLogger.logInfo("debug point");
				}
		}

		while (true) {
			if (nroTkOriginal == tk.getNumeroboleto()) {
				tarifaAcum += tk.getTarifa();
				baseAcum += tk.getTarifaBase();
				ivaAcum += tk.getIva();
				impuestoAcum += tk.getImpuestos();
				comisionAcum += tk.getComisionAmount() + tk.getImporteover();
			
			} else {
				m5 = findM5ByTicket(nroTkOriginal, false);
				if (m5 != null) {
					base = 0;
					comision = 0;
					saleamount = 0;
					xtamount = 0;
					tax = 0;
					othertax = 0;
					neto = 0;
					int im5 = m0.getNumberOfM5();
					for (int i = 0; i < im5; i++) {
						M5Record m5a = (M5Record) (mRecords.getElement(SabreRecord.M5 + (i + 1)));
						if (m5a == null)
							continue;
						if (!m5.hasTicketNumber())
							continue;
					if (m5a.getPaxId()==null)
							continue;
						if (m5a.getPaxId().equals("99"))
							continue;
						if (m5a.getTicketNumber()==null) continue;
						if (!m5a.getTicketNumber().equals(nroTkOriginal)) continue;
						cotiz = 1;
						if (m5a.getCurrency() != null)
							curr = m5a.getCurrency();
						else
							curr = currLocal;
						
						if (!curr.equals(currLocal)) {
							double cotBase = curr.equals("USD") ? 1 : BizMonedaCotizacion.readCotizacionCorrienteAt(tk.getCompany(), tk.getObjCompany().getCountry(), "USD", curr, tk.getCreationDate());
							double cotLocal = currLocal.equals("USD") ? 1 : BizMonedaCotizacion.readCotizacionCorrienteAt(tk.getCompany(), tk.getObjCompany().getCountry(), "USD", currLocal, tk.getCreationDate());
	
							if (cotBase==0) {
								cotiz= BizMonedaCotizacion.readCotizacionCorrienteAt(tk.getCompany(), tk.getObjCompany().getCountry(), currBase, currLocal, tk.getCreationDate());
							} else {
							cotBase = cotBase == 0 ? 1 : cotBase;
							cotLocal = cotLocal == 0 ? 1 : cotLocal;
							cotiz = cotLocal / cotBase;
							cotiz = cotiz == 0 ? 1 : cotiz;
							if (cotiz<1000 && cotiz!=1)
								PssLogger.logInfo("debug point");
							}
						}
						comisionPerc = m5a.getComisionPerc();
						saleamount = m5a.getSaleAmount();
						xtamount = m5a.getTaxLocal() + m5a.getTaxAmount() + m5a.getOtherTaxAmount();
						if (comisionPerc != 0) {
							neto += ((m5a.getNetAmount() / ((1 + (comisionPerc / 100.0)))));
							comision += (neto * comisionPerc) / 100.0;
						} else {
							comision += m5a.getComisionAmount();
							neto += m5a.getNetAmount() - comision;
							comisionPerc = (comision * 100) / neto;
						}
						tax += m5a.getTaxLocal();
						othertax += m5a.getOtherTaxAmount()+m5a.getTaxAmount();
						base += saleamount - xtamount;
					}
					tarifaAcum += base / cotiz;
					baseAcum += base / cotizABase;
					ivaAcum += tax / cotiz;
					impuestoAcum += othertax / cotiz;
					comisionAcum += comision / cotiz;
	
				} else {
					BizPNRTicket tkOrig = getObjTicketOriginal(nroTkOriginal);
					if (tkOrig != null) {
						cotiz=1;
						if (!tkOrig.getCodigoMoneda().equals(tk.getCodigoMoneda())) {
							curr=tkOrig.getCodigoMoneda();
							currLocal=tk.getCodigoMoneda();
							double cotBase = curr.equals("USD") ? 1 : BizMonedaCotizacion.readCotizacionCorrienteAt(tk.getCompany(), tk.getObjCompany().getCountry(), "USD", curr, tk.getCreationDate());
							double cotLocal = currLocal.equals("USD") ? 1 : BizMonedaCotizacion.readCotizacionCorrienteAt(tk.getCompany(), tk.getObjCompany().getCountry(), "USD", currLocal, tk.getCreationDate());
	
							if (cotBase==0) {
								cotiz= BizMonedaCotizacion.readCotizacionCorrienteAt(tk.getCompany(), tk.getObjCompany().getCountry(), currBase, currLocal, tk.getCreationDate());
							} else {
							cotBase = cotBase == 0 ? 1 : cotBase;
							cotLocal = cotLocal == 0 ? 1 : cotLocal;
							cotiz = cotLocal / cotBase;
							cotiz = cotiz == 0 ? 1 : cotiz;
							if (cotiz<1000 && cotiz!=1)
								PssLogger.logInfo("debug point");
							}
						}
						tarifaAcum += tkOrig.getTarifa()*cotiz;
//						baseAcum +=  tkOrig.getTarifaBase();
						ivaAcum += tkOrig.getIva()*cotiz;
						impuestoAcum += tkOrig.getImpuestos()*cotiz;
						comisionAcum += tkOrig.getComisionAmount()*cotiz + tk.getImporteover();
						break; // el ticket ya calculo el acumulado, lo tomo
					} else
						break; // no hay info del ticket, se perdio la cadena
				}
			}
			if (m5.getFieldValue(M5Record.E_DATA) != null && m5.getFieldValue(M5Record.E_DATA).length() >= 16) {
				String newOriginal = Tools.getOnlyNumbers(m5.getFieldValue(M5Record.E_DATA).substring(6, 16));
				if (newOriginal.equals(nroTkOriginal))
					break;
				nroTkOriginal = newOriginal;
			} else
				break; // no hay mas reemisiones
		}

		tk.setTarifa(tarifaAcum);
		tk.setTarifaBase(baseAcum);
		tk.setTarifaYQ(tarifaAcum + tk.getYQ());
		tk.setTarifaBaseConImpuestos(tarifaAcum + impuestoAcum+ivaAcum);
		tk.setImpuestos(impuestoAcum);
		tk.setIva(ivaAcum);
		tk.setImpuestosTotal(tk.getImpuestos()+tk.getIva());
		tk.setComisionAmount(comisionAcum);
		tk.setNeto(tarifaAcum - comisionAcum);

	}	
		
	
	@Override
	public String getIATA() throws Exception {
		PssLogger.logDebug("IATA=>" + m0.getIATA());
		return m0.getIATA();
	}
	private void findYear() throws Exception {
//		int im = m0.getNumberOfM3();
//		for (int i = 0; i < im; i++) {
//			M3Record m3 = (M3Record) (mRecords.getElement(SabreRecord.M3 + (i + 1)));
//			if (m3==null) continue;
//			String year =  m3.getFieldValue(M3Record.FLIGHT_DEPARTURE_YEAR);
//			if (year==null) continue;
//			this.setYear(Long.parseLong( year));
//			break;
//		}

	}

	private void assignPnrDataFormM0(BizPNRTicket tk, String codes,BizPNRTicket tkOrig) throws Exception {
		tk.setCompany(this.getCompany());
		tk.setOrigen(this.getOrigen());
		tk.setGDS(this.getGds());
		tk.setArchivo(this.getPnrFile());

		tk.setCodigopnr(m0.getPNRLocator());
		tk.setNroIata(m0.getIATA());
		tk.setOfficeId(m0.getOfficeLocation());
		tk.setAgenteEmision(m0.getAgenteEmision());
		tk.setAgenteReserva(m0.getAgenteReserva());
//		tk.setVendedor(m0.getVendedor());
		tk.setCityCode(m0.getOriginCityCode());
//		if (tk.isNullCreationDate() || tk.getCreationDate().after(m0.getCreationDate(getYear()))) {
//			tk.setCreationDate(m0.getCreationDate(year)e(getYear()));
//		}
		if (AA.getCreationDate(getYear()).after(new Date())) {
			setYear(getYear()-1);// parche si la fecha queda en el futuro le resto uno
		}
		
		if (tk.isNullCreationDate()) {
			tk.setCreationDate(AA.getCreationDate(getYear()));
		}
		tk.setPNRDate(m0.getPNRDate(getYear()));
		if (tk.getPNRDate().after(tk.getCreationDate()))
			tk.setPNRDate(m0.getPNRDate(getYear()-1)); // si la fecha de creacion es mayor que la de arreglo, supongo que el anio est mal
		if (tkOrig==null)
			tk.setCreationDateAir(AA.getCreationDate(getYear()));
		if (this.getCompany().equals("SOL" )) {
			tk.setCustomerId(m0.getCustomer());
		} else {
			tk.setCustomerId(m0.getCustomerNumber());
		}
		setCustId(tk.getCustomerId());
		tk.setCustomerIdReducidoFromCustomer(m0.getCustomer());
//		tk.setRoute(this.travelItineraryRoute(codes));
		// tk.setDepartureDate(m0.getDepartureDate(getYear(),tk.getCreationDate()));
		tk.setTransactionType(m0.getTransactionType());
		tk.setTipoPrestacion(m0.getTipoPrestacion());
		tk.setVersion(m0.getInterfaceVersionNumber());
		tk.setVoid(false);
		tk.setNullVoidDate();
		// tk.setIndicadorVenta(";" + saleIndicator);

	}

	private void assignCommon(BizPNRTicket tk, String codes) throws Exception {
		this.assignPnrDataFormM0(tk, codes, null);
//		tk.setVendedor(m0.getVendedor());
		if (company.equals("CIR")) //ciruclar lo toma de otro lado
			tk.setVendedor(m0.getVendedor2());
		else
			tk.setVendedor(m0.getVendedor());
		// this.assignPnrDataFormM7(tk);
		this.assignPnrDataFormM8(tk);
		this.assignPassenger(tk);
	}

	private void assignCommonM5(BizPNRTicket tk, String codes, M5Record m5,BizPNRTicket tkOrig) throws Exception {
		this.assignPnrDataFormM0(tk, codes,tkOrig);
		String ticketOriginal = tk.getNumeroboleto();
		if (m5.isRemision()) {
			if (m5.getFieldValue(M5Record.E_DATA).length() >= 16) {
				ticketOriginal = Tools.getOnlyNumbers(m5.getFieldValue(M5Record.E_DATA).substring(6, 16));
			}
		}
		String fecha = extractCreationDateOriginalTicket(ticketOriginal);
		if (fecha!=null) {
			try {
				tk.setCreationDate(JDateTools.StringToDate(fecha, "ddMMMyy"));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			BizPNRTicket pnrOriginal = findTicketFromBoleto(getCompany(), ticketOriginal);
			if (pnrOriginal != null) {
				tk.setCreationDate(pnrOriginal.getCreationDate());
			} 
		}
		if (company.equals("CIR")) //ciruclar lo toma de otro lado
			tk.setVendedor(m0.getVendedor2());
		else
			tk.setVendedor(m0.getVendedor());
		// this.assignPnrDataFormM7(tk);
		this.assignPnrDataFormM8(tk);
		this.assignPassengerM5(tk);
	}

	private void saveOtherTicketFromM3(M3Record m3) throws Exception {
		M1Record m1 = this.findM1ByPax(m3.getItemNumber());
		if (m1==null) return;
		
		BizPNROtro tk = new BizPNROtro();
		tk.dontThrowException(true);
		boolean exist = tk.readByPNR(getCompany(),m0.getIATA(),m0.getPNRLocator(),m3.getFieldValueNumber(M3Record.ITINERARY_ITEM_NUMBER));
		tk.setCompany(this.getCompany());
		tk.setOrigen(this.getOrigen());
		tk.setGDS(this.getGds());
		tk.setArchivo(this.getPnrFile());
		tk.setCodigoPNR(m0.getPNRLocator());
		tk.setNroIata(m0.getIATA());
		tk.setOfficeId(m0.getOfficeLocation());
		tk.setVendedor(m0.getVendedor());
		tk.setCreationDate(m0.getCreationDate(getYear()));
		tk.setCityCode(m0.getOriginCityCode());
		tk.setPNRDate(m0.getPNRDate(getYear()));
		tk.setCreationDateAir(AA.getCreationDate(getYear()));
		if (this.getCompany().equals("SOL" ))
			tk.setCustomerId(m0.getCustomer());
		else
			tk.setCustomerId(m0.getCustomerNumber());
		tk.setCustomerIdReducidoFromCustomer(m0.getCustomer());
		tk.setTransactionType(m0.getTransactionType());
		tk.setTipoPrestacion(m0.getTipoPrestacion());
		tk.setVersion(m0.getInterfaceVersionNumber());
		tk.setNombrePasajero(m1.getPaxName());
		tk.setVoid(false);
		tk.setMonedaLocal(BizCompany.getObjCompany(tk.getCompany()).getCurrency());
		
		tk.setCodigoSegmento(m3.getFieldValueNumber(M3Record.ITINERARY_ITEM_NUMBER));
		tk.setControlData(m3.getFieldValue(M3Record.ACCOUNTING_LINK_CODE));
		tk.setProductCode(m3.getFieldValue(M3Record.PRODUCT_CODE));
		tk.setControlData(m3.getFieldValue(M3Record.CONTROL_DATA));
		tk.setAction(m3.getFieldValue(M3Record.ACTION_ADVICE_SEGMENT_STATUS_CODES));
		tk.setProductoSecundario(m3.getFieldValue(M3Record.SECONDARY_PRODUCT_CODE));
    tk.setFechaReserva(m3.getFieldValueAsDate(M3Record.DEPARTURE_DATE_OR_RESERVATION_COMMENCEMENT_IN_DATE,getYear()));
    tk.setFechaProcesamiento(new Date());
		tk.setConfirmacion(m3.getFieldValue(M3Record.CONFIRMATION_NUMBER));
		tk.setProductDescr(tk.generateProductDescripcion());
		tk.setImporte(0);
		tk.setImporteBase(0);
		tk.setTasas(0);
		tk.setFee(0);
    if (tk.isHotel()) {
  		tk.setPasajeros(m3.getFieldValueNumber(M3Record.NUMBER_IN_PARTY));
  		tk.setAeropuerto(m3.getFieldValue(M3Record.CITY_AIRPORT_CODE));
      tk.setHotelCodigo(m3.getFieldValue(M3Record.HOTEL_COMPANY_CHAIN_CODE));
  		Date fechaSalida = JDateTools.StringToDate(m3.getFechaSalida(getYear(), getCreationDate()), "yyyyMMdd");
  		tk.setFechaSalida(fechaSalida);
      tk.setNoches(m3.getNoches());
      tk.setHotelName(m3.getFieldValue(M3Record.HOTEL_HOTEL_NAME));
      tk.setHabitaciones(m3.getHabitaciones());
      tk.setProductType(m3.getProductCodeDet());
	    
      tk.setImporteBase(m3.getFieldValueDouble(M3Record.HOTEL_AUTOMATED_HOTEL_PRECIO1));
	    tk.setMonedaBase(m3.getFieldValue(M3Record.HOTEL_AUTOMATED_HOTEL_PRECIOM1));
	    
	    tk.setImporte(m3.getFieldValueDouble(M3Record.HOTEL_AUTOMATED_HOTEL_PRECIO2));
	    tk.setMonedaImporte(m3.getFieldValue(M3Record.HOTEL_AUTOMATED_HOTEL_PRECIOM2));
	    
	    tk.setHotelFName(m3.getFieldValue(M3Record.HOTEL_AUTOMATED_HFN));
	    
	    tk.setTasas(m3.getFieldValueDouble(M3Record.HOTEL_AUTOMATED_TTX));
	    tk.setMonedaTasas(m3.getFieldValueMoneda(M3Record.HOTEL_AUTOMATED_HTP));
	    
	    tk.setFee(m3.getFieldValueDouble(M3Record.HOTEL_AUTOMATED_TSC));
	    tk.setMonedaFee(m3.getFieldValueMoneda(M3Record.HOTEL_AUTOMATED_HTP));
	    
	    tk.setPrecioTotal(m3.getFieldValueDouble(M3Record.HOTEL_AUTOMATED_HTP));
	    tk.setMonedaPrecioTotal(m3.getFieldValueMoneda(M3Record.HOTEL_AUTOMATED_HTP));
	    
	    tk.setComisionMonto(m3.getFieldValueDouble(M3Record.HOTEL_AUTOMATED_TAC));
	    tk.setComisionCodigo(m3.getFieldValue(M3Record.HOTEL_AUTOMATED_CMN));
	    tk.setComisionTexto(m3.getFieldValue(M3Record.HOTEL_AUTOMATED_CMT));
	    
    } else if (tk.isLimo()) {
  		tk.setPasajeros(m3.getFieldValueNumber(M3Record.NUMBER_IN_PARTY));
	    tk.setCityCode(m3.getFieldValue(M3Record.LIMO_AUTOMATED_CITYCODE));
	    tk.setServiceCode(m3.getFieldValue(M3Record.OTHER_SERVICE_CODE));
	    tk.setServiceType(m3.getFieldValue(M3Record.LIMO_AUTOMATED_SERVICETYPE));
	    tk.setInfo(m3.getFieldValue(M3Record.LIMO_OPERATOR_CARRIER)+"/"+m3.getFieldValue(M3Record.LIMO_OPERATOR_POINTSALE));
	    tk.setCodigoConfirmacion(m3.getFieldValue(M3Record.LIMO_OPERATOR_SI));
	  } else if (tk.isCar()) {
	    tk.setNroAutos(m3.getFieldValueNumber(M3Record.NUMBER_OF_CARS));
	    tk.setPickupPoint(m3.getFieldValue(M3Record.CAR_AUTOMATED_PICKUP));
	    tk.setDropOffdate(m3.getFieldValueAsDate(M3Record.CAR_AUTOMATED_DROPOFF,getYear()));
  		tk.setProductType(m3.getFieldValue(M3Record.NUMBER_IN_PARTY));
	    tk.setCarType(m3.getFieldValue(M3Record.CAR_AUTOMATED_CAR_TYPE));
	    tk.setUpgrade(m3.getFieldValue(M3Record.CAR_AUTOMATED_UPG));
	    tk.setArriboHora(m3.getFieldValue(M3Record.CAR_AUTOMATED_ARR));
	    tk.setRetorno(m3.getFieldValue(M3Record.CAR_AUTOMATED_RET));
	    tk.setDopOfflocation(m3.getFieldValue(M3Record.CAR_AUTOMATED_DO));
	    tk.setDropOffcharge(m3.getFieldValue(M3Record.CAR_AUTOMATED_DOC));
	    tk.setGarantia(m3.getFieldValue(M3Record.CAR_AUTOMATED_G));
	    tk.setCorporateDiscount(m3.getFieldValue(M3Record.CAR_AUTOMATED_CD));
	    tk.setTourcode(m3.getFieldValue(M3Record.CAR_AUTOMATED_CD));
	    tk.setEquipamiento(m3.getFieldValue(M3Record.CAR_AUTOMATED_IT));

//	    tk.setImporteBase(m3.getFieldValueFirstDouble(M3Record.CAR_AUTOMATED_AP));
//	    tk.setMonedaBase(m3.getFieldValueFirstMoneda(M3Record.CAR_AUTOMATED_AP));
//	    tk.setImporte(m3.getFieldValueFirstDouble(M3Record.CAR_AUTOMATED_AP));
//	    tk.setMonedaImporte(m3.getFieldValueFirstMoneda(M3Record.CAR_AUTOMATED_AP));
//
//	    tk.setFee(m3.getFieldValueFirstDouble(M3Record.CAR_AUTOMATED_RG));
//	    tk.setMonedaFee(m3.getFieldValueFirstMoneda(M3Record.CAR_AUTOMATED_RG));

	    tk.setPrecioTotal(m3.getFieldValueFirstDouble(M3Record.CAR_AUTOMATED_AP));
	    tk.setMonedaPrecioTotal(m3.getFieldValueFirstMoneda(M3Record.CAR_AUTOMATED_AP));
	    tk.setRemarks(m3.getFieldValue(M3Record.CAR_AUTOMATED_IR));
	    tk.setNombreCliente(m3.getFieldValue(M3Record.CAR_AUTOMATED_NM));
	    tk.setCupon(m3.getFieldValue(M3Record.CAR_AUTOMATED_PC));
	    tk.setTasaGarantia(m3.getFieldValue(M3Record.CAR_AUTOMATED_RG));
	    tk.setInfo(m3.getFieldValue(M3Record.CAR_AUTOMATED_AP));
	    tk.setBookingSource(m3.getFieldValue(M3Record.CAR_AUTOMATED_BS));
	    tk.setRateCode(m3.getFieldValue(M3Record.CAR_AUTOMATED_RC));
	    tk.setProveedor(m3.getFieldValue(M3Record.CAR_AUTOMATED_VP));
	    tk.setVoucherTipo(m3.getFieldValue(M3Record.CAR_AUTOMATED_VV));
	    tk.setVoucherNumero(m3.getFieldValue(M3Record.CAR_AUTOMATED_VN));
	    tk.setVoucherBiling(m3.getFieldValue(M3Record.CAR_AUTOMATED_VB));
	    tk.setVoucherFormat(m3.getFieldValue(M3Record.CAR_AUTOMATED_VF));
	    tk.setEquipamientoConfirmado(m3.getFieldValue(M3Record.CAR_AUTOMATED_SQC));
	    tk.setGarantiaInfo(m3.getFieldValue(M3Record.CAR_AUTOMATED_PGP));
	    tk.setComisionMonto(m3.getFieldValueDouble(M3Record.CAR_AUTOMATED_RG));
	    tk.setComisionTexto(m3.getFieldValue(M3Record.CAR_AUTOMATED_RG));
    } else if (tk.isElvaTor()) {
	    tk.setProductType(m3.getFieldValue(M3Record.ETO_AUTOMATED_R));
//	    tk.setMealPlan(m3.getFieldValue(M3Record.ETO_AUTOMATED_M));
	    tk.setCarType(m3.getFieldValue(M3Record.ETO_AUTOMATED_C));
	    tk.setAeropuerto(m3.getFieldValue(M3Record.ETO_AUTOMATED_L));
	    tk.setInfo(m3.getFieldValue(M3Record.ETO_AUTOMATED_F)+"/"+m3.getFieldValue(M3Record.ETO_AUTOMATED_M));
	    tk.setPrecioTotal(m3.getFieldValueDouble(M3Record.ETO_AUTOMATED_P));
	    tk.setMonedaPrecioTotal(m3.getFieldValueMoneda(M3Record.ETO_AUTOMATED_P));
	    tk.setImporteBase(m3.getFieldValueDouble(M3Record.ETO_AUTOMATED_P));
	    tk.setMonedaBase(m3.getFieldValue(M3Record.ETO_AUTOMATED_P));
	    tk.setImporte(m3.getFieldValueDouble(M3Record.ETO_AUTOMATED_P));
	    tk.setMonedaImporte(m3.getFieldValue(M3Record.ETO_AUTOMATED_P));
	  } else if (tk.isElvaSea()) {
	    tk.setImporteBase(m3.getFieldValueDouble(M3Record.ESE_AUTOMATED_V));
	    tk.setImporte(m3.getFieldValueDouble(M3Record.ESE_AUTOMATED_T));
	    tk.setInfo(m3.getFieldValue(M3Record.ESE_AUTOMATED_C)+"/"+m3.getFieldValue(M3Record.ESE_AUTOMATED_B)+"/"+m3.getFieldValue(M3Record.ESE_AUTOMATED_S));
	    tk.setNombreCliente(m3.getFieldValue(M3Record.ESE_AUTOMATED_P));
	    tk.setPrecioTotal(m3.getFieldValueDouble(M3Record.ESE_AUTOMATED_F));
	    tk.setMonedaPrecioTotal(m3.getFieldValueMoneda(M3Record.ESE_AUTOMATED_F));
	    tk.setImporteBase(m3.getFieldValueDouble(M3Record.ESE_AUTOMATED_F));
	    tk.setMonedaBase(m3.getFieldValue(M3Record.ESE_AUTOMATED_F));
	    tk.setImporte(m3.getFieldValueDouble(M3Record.ESE_AUTOMATED_F));
	    tk.setMonedaImporte(m3.getFieldValue(M3Record.ESE_AUTOMATED_F));
	    tk.setCodigoConfirmacion(m3.getFieldValue(M3Record.ETO_AUTOMATED_CF));
	  } else if (tk.isAddSegment()) {
	    tk.setServiceCode(m3.getFieldValue(M3Record.ADD_AUTOMATED_ST));
//	  	tk.setBookingId(m3.getFieldValue(M3Record.ADD_AUTOMATED_BI));
	  	tk.setBookingSource(m3.getFieldValue(M3Record.ADD_AUTOMATED_BS));
	    tk.setVendedor(m3.getFieldValue(M3Record.ADD_AUTOMATED_VR));
	    tk.setCodigoConfirmacion(m3.getFieldValue(M3Record.ADD_AUTOMATED_CF));
	    tk.setInfo(m3.getFieldValue(M3Record.ADD_AUTOMATED_SI)+"/"+m3.getFieldValue(M3Record.ADD_AUTOMATED_VI)+"/"+m3.getFieldValue(M3Record.ADD_AUTOMATED_TN));
	    tk.setDropOffcharge(m3.getFieldValue(M3Record.ADD_AUTOMATED_DO));
	    tk.setNombreCliente(m3.getFieldValue(M3Record.ADD_AUTOMATED_NM));
	    tk.setPickupPoint(m3.getFieldValue(M3Record.ADD_AUTOMATED_PU));
	  } else if (tk.isInsurance()) {
	    tk.setBookingSource(m3.getFieldValue(M3Record.INS_AUTOMATED_BS));
	    tk.setHotelName(m3.getFieldValue(M3Record.INS_AUTOMATED_NM));
	    tk.setInfo(m3.getFieldValue(M3Record.INS_AUTOMATED_PT));
	    tk.setCodigoConfirmacion(m3.getFieldValue(M3Record.INS_AUTOMATED_CF));
	  } else if (tk.isRail()) {
	    tk.setServiceCode(m3.getFieldValue(M3Record.RAL_AUTOMATED_CL));
	    tk.setProveedor(m3.getFieldValue(M3Record.RAL_AUTOMATED_AN));
	    tk.setNombreCliente(m3.getFieldValue(M3Record.RAL_AUTOMATED_PG));
	    tk.setPrecioTotal(m3.getFieldValueDouble(M3Record.RAL_AUTOMATED_FR));
	    tk.setMonedaPrecioTotal(m3.getFieldValueMoneda(M3Record.RAL_AUTOMATED_FR));
	    tk.setImporteBase(m3.getFieldValueDouble(M3Record.RAL_AUTOMATED_FR));
	    tk.setMonedaBase(m3.getFieldValue(M3Record.RAL_AUTOMATED_FR));
	    tk.setImporte(m3.getFieldValueDouble(M3Record.RAL_AUTOMATED_FR));
	    tk.setMonedaImporte(m3.getFieldValue(M3Record.RAL_AUTOMATED_FR));
	    tk.setComisionMonto(m3.getFieldValueDouble(M3Record.RAL_AUTOMATED_CA));
	    tk.setComisionCodigo(m3.getFieldValue(M3Record.RAL_AUTOMATED_CO));
	    tk.setComisionTexto(m3.getFieldValue(M3Record.RAL_AUTOMATED_CT));
	    tk.setPaisOrigen(m3.getFieldValue(M3Record.RAL_AUTOMATED_OC));
	    tk.setPaisDestino(m3.getFieldValue(M3Record.RAL_AUTOMATED_DC));
	    tk.setProductType(m3.getFieldValue(M3Record.RAL_AUTOMATED_PT));
	  } else if (tk.isCruiser()) {
	    tk.setServiceCode(m3.getFieldValue(M3Record.CRU_AUTOMATED_CS));
	    tk.setHotelName(m3.getFieldValue(M3Record.CRU_AUTOMATED_NA));
	    tk.setInfo(m3.getFieldValue(M3Record.CRU_AUTOMATED_SI));
	    tk.setFechaSalida(m3.getFieldValueAsDate(M3Record.CRU_AUTOMATED_DT, getYear()));
	    tk.setArriboHora(m3.getFieldValue(M3Record.CRU_AUTOMATED_AR));
	    tk.setCodigoConfirmacion(m3.getFieldValue(M3Record.CRU_AUTOMATED_CF));
	  } else if (tk.isTor()) {
	    tk.setServiceCode(m3.getFieldValue(M3Record.CRU_AUTOMATED_CS));
	    tk.setHotelName(m3.getFieldValue(M3Record.CRU_AUTOMATED_NA));
	    tk.setInfo(m3.getFieldValue(M3Record.CRU_AUTOMATED_SI));
	    tk.setFechaSalida(m3.getFieldValueAsDate(M3Record.CRU_AUTOMATED_DT, getYear()));
	    tk.setArriboHora(m3.getFieldValue(M3Record.CRU_AUTOMATED_AR));
	    tk.setCodigoConfirmacion(m3.getFieldValue(M3Record.CRU_AUTOMATED_CF));
	  } else {
	    tk.setServiceType(m3.getFieldValue(M3Record.OTHER_SERVICE_CODE));
	    tk.setCityCode(m3.getFieldValue(M3Record.OTHER_VARIABLE_CITY_SERVICE));
			JIterator<M5Record> iter = this.getAllM5Records().getIterator();
			double importe=0;
			double tasa=0;
			double fee=0;
			String moneda="";
			while (iter.hasMoreElements()) {
				M5Record m5 = iter.nextElement();

					importe+=m5.getFieldValueDouble(M5Record.NET_AMOUNT);
					moneda=tk.getMonedaLocal();
					tasa+=m5.getFieldValueDouble(M5Record.TAX_AMOUNT)+m5.getOtherTaxAmount();
					fee+=m5.getFieldValueDouble(M5Record.COMISION_AMOUNT);;

			}
			tk.setImporteBase(importe);
			tk.setMonedaBase(moneda);
			tk.setImporte(importe);
			tk.setMonedaImporte(moneda);
			tk.setTasas(tasa);
			tk.setMonedaTasas(moneda);
			tk.setFee(fee);
			tk.setMonedaFee(moneda);
			tk.setComisionMonto(fee);
    
	  }
		JIterator<M5Record> iter = this.getAllM5Records().getIterator();
		while (iter.hasMoreElements()) {
			M5Record m5 = iter.nextElement();
			if (!m5.hasVoucher())
				continue;
			if (tk.getImporteBase()==0) {
				tk.setImporteBase(m5.getFieldValueDouble(M5Record.NET_AMOUNT));
				tk.setMonedaBase(tk.getMonedaLocal());
				tk.setImporte(m5.getFieldValueDouble(M5Record.NET_AMOUNT));
				tk.setMonedaImporte(tk.getMonedaLocal());
			}
			if (tk.getTasas()==0) {
				tk.setTasas(m5.getFieldValueDouble(M5Record.TAX_AMOUNT));
				tk.setMonedaTasas(tk.getMonedaLocal());
			}
			if (tk.getFee()==0) {
				tk.setFee(m5.getOtherTaxAmount());
				tk.setMonedaFee(tk.getMonedaLocal());
				tk.setComisionMonto(tk.getFee());
				tk.setComisionCodigo(""+m5.getFieldValueDouble(M5Record.COMISION_AMOUNT));
			}
			break;
		}
	
    
  	setTarifasEnDolares(tk);
  	tk.postProcesar();
  	
    if (exist)
    	tk.processUpdate();
    else
    	tk.processInsert();
	}
	
	private M5Record getM5(String tn) throws Exception {
		int im5 = m0.getNumberOfM5();
		for (int i = 0; i < im5; i++) {
			M5Record m5 = (M5Record) (mRecords.getElement(SabreRecord.M5 + (i + 1)));
			if (m5!=null&&m5.getTicketNumber()==null)
				continue;
			if (m5!=null&&m5.getTicketNumber().equals(tn))
				return m5;
		}
		return null;
	}
	
	private M2Record getM2(String tn) throws Exception {
		int im2 = m0.getNumberOfM2();
		for (int i = 0; i < im2; i++) {
			M2Record m2 = (M2Record) (mRecords.getElement(SabreRecord.M2 + (i + 1)));
			if (m2!=null &&m2.getTicketNumber().equals(tn))
				return m2;
		}
		return null;
	}
	

	private void saveFromM6(BizPNRTicket tk, String codes) throws Exception {
		int im6 = m0.getNumberOfM6();
		for (int i = 0; i < im6; i++) {
			M6Record m = (M6Record) (mRecords.getElement(SabreRecord.M6 + (i + 1)));
			if(m==null) continue;
			if (!m.getPassengerType().equals(tk.getTipoPasajero()))
				continue;
			if (!inCodes(codes, "" + (i + 1)))
				continue;
			
			tk.setQ(m.getQ());
			if (m.getQ()!=0 && m.getMonedaQ()!=null && !m.getMonedaQ().equals(tk.getCodigoMoneda())) {
				double cotiz =BizMonedaCotizacion.readCotizacionCorrienteAt(tk.getCompany(), tk.getObjCompany().getCountry(), tk.getCodigoBaseMoneda(),tk.getCodigoMoneda(), tk.getCreationDate());
//				double cotLocal = tk.getCodigoMoneda().equals("USD")?1:BizMonedaCotizacion.readCotizacionCorrienteAt(tk.getCompany(), tk.getObjCompany().getCountry(), "USD",tk.getCodigoMoneda(), tk.getCreationDate());

//				cotBase = cotBase==0?1:cotBase;
//				cotLocal = cotLocal==0?1:cotLocal;
//				double cotiz = cotLocal/cotBase;
//				cotiz = cotiz==0?1:cotiz;

				tk.setQ(JTools.rd(tk.getQ()*m.getDoubleRoe()*cotiz,2));
			}
			
		}
	}
	private String extractOriginalTicket(M2Record m2) throws Exception {
		 return m2.getFieldValue(M2Record.ORIGINAL_ISSUE).isEmpty()|| m2.getFieldValue(M2Record.ORIGINAL_ISSUE).length()<13?
				(!m2.getFieldValue(M2Record.ISSUED_IN_EXCHANGE).isEmpty()&& m2.getFieldValue(M2Record.ISSUED_IN_EXCHANGE).indexOf("/")!=-1?m2.getFieldValue(M2Record.ISSUED_IN_EXCHANGE).substring(3,m2.getFieldValue(M2Record.ISSUED_IN_EXCHANGE).indexOf("/")) : null):
					m2.getFieldValue(M2Record.ORIGINAL_ISSUE).substring(3, 13);
	}
	private String extractCreationDateOriginalTicket(String ticket) throws Exception {
		JIterator<M2Record> iterm2 = this.getM2Records(true).getIterator();
		while (iterm2.hasMoreElements()) {
			M2Record m2 = iterm2.nextElement();
			String original = m2.getFieldValue(M2Record.ORIGINAL_ISSUE)+m2.getFieldValue(M2Record.ISSUED_IN_EXCHANGE);
			if (original==null)continue;
			int pos = original.indexOf(ticket);
			if (pos==-1 || original.length()<pos+ticket.length()+10) continue;
			String fecha = original.substring(pos+ticket.length()+3,pos+ticket.length()+10);
			return fecha;
		}
		return null;
	}
	private void saveOneTicketFromM2(M2Record m2) throws Exception {
			M1Record m1 = this.findM1ByPax(m2.getPaxId());
	    boolean segmentosDefinidos = false;

			BizPNRTicket tk = new BizPNRTicket();
			String ticketOriginalOld = extractOriginalTicket(m2);
			BizPNRTicket tkOrig =null;
			if (ticketOriginalOld!=null && !ticketOriginalOld.equals("")) {
				tkOrig = getObjTicketOriginal(ticketOriginalOld);
				if (tkOrig!=null) {
			//		Date d =m0.getCreationDate(getYear());
			//		if (tkOrig.getCreationDate().before(d)||tkOrig.getCreationDate().equals(d))
					tk.setCreationDate(tkOrig.getCreationDate());
//					else
//						tk.setCreationDate(d);
					segmentosDefinidos=tkOrig.getBookings()>0;
				}			
			} 

			tk.setNumeroboleto(m2.getTicketNumber());
			tk.setTourCode(m2.getTourCode()==null?"-":m2.getTourCode());
			checkAerolinea(m2.getCarrierCode());
			tk.setCodigoaerolinea(m2.getCarrierCode());
			tk.setNumeropasajero(m2.getPaxId());
			tk.setTipoboleto(m2.isElectronicTicket() ? "K" : "T");
			tk.setCantidadconectados(m2.getConjuctionTicketCount() + "");
			tk.setDirectory(getCompany() + "/" + getGds() + "/" + getPnrLocator().substring(0, 2));
			tk.setComisionAmount(m2.getCommision());
			tk.setComisionPerc(m2.getCommisionPercentaje());
			this.assignCommon(tk, m1.getFieldValue(M1Record.M3_ITEM_NUMBERS_FOR_THIS));
			this.assignFareFromM2(m2, tk);
			this.assignAdditionalFee(tk);
			this.assignPays(null, m2, tk);
			JRecords<BizPNRTax> taxes = this.generateTaxesFromM2(m2, tk);
			this.assignSumTaxes(tk, taxes,m2);
			tk.setNeto(tk.getTarifa() - tk.getComisionAmount() - tk.getImporteover());
			tk.setReemitted(false);
			tk.setEmision(true);
			tk.setTarifaYQ(tk.getTarifa() + tk.getYQ());
			tk.setComplete(true);
			tk.setIvaFactura(tk.getIva());
			tk.setNetoFactura(tk.getNeto());
			tk.setTarifaBaseFactura(tk.getTarifaBase());
			tk.setTarifaFactura(tk.getTarifa());
			tk.setTarifaTotalFactura(tk.getTarifaBaseConImpuestos());
			tk.setTarifaFacturadaYQ(tk.getTarifaFactura() + tk.getYQ());
			tk.setImpuestoFactura(tk.getImpuestos());
			tk.setFechaProcesamiento(new Date());
			tk.setComisionFactura(tk.getComisionAmount());
			tk.setTipoOperacion("ETR");
			
			this.setearTarifasHistoricoFromM5(tk);

			M5Record m5L = findM5ByTicket(tk.getNumeroboleto(), false);
			if (m5L!=null) {
				tk.setInternacional(!m5L.getMercado().equals("D"));
				tk.setInternacionalDescr(tk.isInternacional() ? "International" : "Domestic");
				checkAerolinea(m5L.getCarrierCode());
				tk.setCodigoaerolinea(m5L.getCarrierCode());
			}
			String firstTicketOriginal = "";
			if (m2.getFieldValue(M2Record.ISSUED_IN_EXCHANGE).length() > 13) { // reemision
				String ticketOriginal = m2.getFieldValue(M2Record.ISSUED_IN_EXCHANGE).substring(3, 13);
				firstTicketOriginal = ticketOriginal;
				BizPNRTicket pnrOriginal = findTicketFromBoleto(getCompany(), ticketOriginal);
				if (pnrOriginal != null) {
					pnrOriginal.setReemitted(true);
					pnrOriginal.update();
					tk.setRefOriginal(pnrOriginal.getId());
				} else {
					addFaltante(getCompany(), ticketOriginal);
				}

				tk.setEmision(false);
				tk.setOriginalNumeroboleto(ticketOriginal);
			}	else if (m2.getFieldValue(M2Record.ORIGINAL_ISSUE).length() > 13) {
				String ticketOriginal = m2.getFieldValue(M2Record.ORIGINAL_ISSUE).substring(3, 13);
				if (!ticketOriginal.equals(firstTicketOriginal)) { // reemision
					BizPNRTicket pnrOriginal = findTicketFromBoleto(getCompany(), ticketOriginal);
					if (pnrOriginal != null) {
						pnrOriginal.setReemitted(true);
						pnrOriginal.update();
						tk.setRefOriginal(pnrOriginal.getId());
	//					tk.setIva(tk.getIva() + pnrOriginal.getIva());
					} else {
						addFaltante(getCompany(), ticketOriginal);
					}
					tk.setEmision(false);
					tk.setOriginalNumeroboleto(ticketOriginal);
				}
			}
			this.saveFromM6(tk, m2.getFieldValue(M2Record.M6_FARE_CALCULATION_ITEM_NUMBERS));

			setTarifasEnDolares(tk);

			tk.setImpuestosTotalFactura(tk.getImpuestoFactura()+tk.getIvaFactura());
			tk.setImpuestosTotal(tk.getImpuestos()+tk.getIva());
			if (tk.acceptTicket()) {
				this.save(tk);

				this.saveTaxesFromM2(m2, tk, taxes);

				// this.saveAirSegmentFrom(tk, m2,
				// m1.getFieldValue(M1Record.M3_ITEM_NUMBERS_FOR_THIS));
				int im4 = m0.getNumberOfM4();
				if (im4!=0) {
					this.saveAirSegmentFromM4(tk, m2, m2.getFieldValue(M2Record.ENTITLEMENT_ITEM_NUMBERS));
					this.saveFareSegmentFrom(tk, m2.getFieldValue(M2Record.M6_FARE_CALCULATION_ITEM_NUMBERS));
				} else {
					if (segmentosDefinidos){
						this.saveAirSegmentFromTkOriginal(tk, tkOrig);
						this.saveFareSegmentFrom(tk, m2.getFieldValue(M2Record.M6_FARE_CALCULATION_ITEM_NUMBERS));
						
					}
				}
				BizReembolso reem = new BizReembolso();
				reem.dontThrowException(true);
				if (reem.readByBoletoRfnd(tk.getCompany(), tk.getNumeroboleto()))
					tk.setRefund(true);
				tk.saveCotizacion();
				//tk.calculeOver();
				tk.setCalculed(false);
				Date newVoid = null;
				if ((newVoid = BizVoidManual.isManualVoid(tk.getCompany(), tk.getNumeroboleto()))!=null) {
					tk.setVoid(true);
					tk.setVoidDate(newVoid);
				}
				String newDK = null;
				if ((newDK = BizVoidManual.isManualDK(tk.getCompany(), tk.getNumeroboleto()))!=null) {
					tk.setCustomerIdReducido(newDK);
				}	
				tk.SetVision("UPDATE");
				
				tk.update();
			} else {
				PssLogger.logInfo("Ignore ticket!!!!");
			}
	}

	
	private void assignFareFromMX(BizPNRTicket tk) throws Exception {

		int im = m0.getNumberOfM2();
		MXRecord mx = ((MXRecord) mRecords.getElement(SabreRecord.MX));
		tk.setCodigoMonedaLocal(BizCompany.getObjCompany(tk.getCompany()).getCurrency());

		for (int i = 0; i < im; i++) {
			M2Record m = (M2Record) (mRecords.getElement(SabreRecord.M2 + (i + 1)));
			String ticket = m.getTicketNumber();
			if (!ticket.equals(tk.getNumeroboleto()))
				continue;
			double fareAmountWithTaxes = Double.parseDouble(mx.getFareAmount(ticket, "T")); // tarifa
																																											// con
																																											// impuestos

			double fareAmountInOriginalCurrency = Double.parseDouble(mx.getFareAmount(ticket, "F")); // tarifa
																																																// en
																																																// moneda
																																																// original

			String amount = mx.getFareAmount(ticket, "Q").trim();
			double realamount = Double.parseDouble(amount.equals("USD")||amount.equals(getMonedaDefault(getCompany())) ? "0" : amount); // neto
																																										// sin
																																										// impuestos

			String fareCurr = getMonedaDefault(getCompany());
			double fareAmount;
			if (amount.equals(getMonedaDefault(getCompany())) || fareAmountWithTaxes < realamount) {
				realamount = fareAmountWithTaxes - this.getMxAllTaxForTicket(ticket);
				fareAmount = realamount;
			} else {
				fareAmount = fareAmountWithTaxes - this.getMxAllTaxForTicket(ticket);
			}
			if (fareAmountWithTaxes > 0.0)
				fareCurr = mx.getFareCurrency(ticket, "T");

			// if (realamount == 0) {
			// fareAmount = fareAmountInOriginalCurrency;
			// realamount = fareAmount;
			// }

			double exchangeRate = fareAmountInOriginalCurrency == 0 || realamount==0 ? 1 : JTools.rd(realamount / fareAmountInOriginalCurrency, 2);

			// boolean exist = priceExists(ticket);

			tk.setCodigoBaseMoneda(mx.getFareCurrency(ticket, "F"));
			tk.setCodigoMoneda(fareCurr);
			tk.setTipoCambio(exchangeRate);
			tk.setTarifaBaseConImpuestos(fareAmountWithTaxes);
			tk.setTarifaBase(fareAmountInOriginalCurrency);
			tk.setTarifa(fareAmount);
			tk.setTarifaYQ(tk.getTarifa() + tk.getYQ());
			tk.setTarifaFacturadaYQ(tk.getTarifaFactura() + tk.getYQ());

		}

	}
	String monDefa;
	String monDefaCompany;
	private String getMonedaDefault(String company) throws Exception {
		if (monDefaCompany!=null && monDefaCompany!=company) monDefa=null;
		if (monDefa!=null) return monDefa;
		monDefaCompany=company;
		return monDefa=BizBSPCompany.getObjBSPCompany(company).getMonedaDefa();
	}

//	private void assignFareFromM5(M5Record m5, BizPNRTicket tk) throws Exception {
//
//		String curr= getMonedaDefault(getCompany());
//
//		double comision = m5.getComisionAmount();
//		double comisionPerc = m5.getComisionPerc();
//		double saleamount = m5.getSaleAmount();
//		double xtamount = m5.getTaxAmount()+m5.getOtherTaxAmount();
//		double neto = 0;
//		if (comisionPerc!=0) {
//			neto = ((m5.getNetAmount()/((1+(comisionPerc/100.0)))));
//			comision = (neto*comisionPerc)/100.0;
//		} else {
//			neto = m5.getNetAmount() - comision;
//		}
//		double base = saleamount - xtamount;
//
//		if (xtamount == 0.0f && saleamount == 0.0f)
//			return;
//
//		tk.setCodigoMoneda(curr);
//		tk.setCodigoBaseMoneda(curr);
//		tk.setTipoCambio(1d);
//		tk.setTarifaBaseConImpuestos(tk.getTarifaBaseConImpuestos() + saleamount);
//		tk.setTarifaBase(tk.getTarifaBase() + base);
//		tk.setTarifa(tk.getTarifa() + base);
//		tk.setNeto(tk.getNeto() + neto);
//		tk.setImpuestos(tk.getImpuestos() + xtamount);
//		tk.setComisionAmount(tk.getComisionAmount() + comision);
//		tk.setTarifaYQ(tk.getTarifa()+ tk.getYQ());
//	}
	
	private boolean hasMX(String ticketNumber) throws Exception {
		 	MXRecord mx = ((MXRecord) mRecords.getElement(SabreRecord.MX));
		 	if (mx==null) return false;
		 	return mx.getTaxes(ticketNumber)!=null && !mx.getTaxes(ticketNumber).isEmpty();

	}

	private void assignFareFromM2(M2Record m2, BizPNRTicket tk) throws Exception {
		String tax1 ,tax2 ,tax3 ;
		double t1 , t2 ,t3 ;
		String imp = (BizBSPCompany.getConfigView(tk.getCompany()).getTipoImpuesto());
		double iva = 0;
		double noiva=0;
		
		if (m0.hasToUseMxRecord()) {
			this.assignFareFromMX(tk);
			return;
		} else if (hasMX(tk.getNumeroboleto())) {
			MXRecord mx = ((MXRecord) mRecords.getElement(SabreRecord.MX));
			JMap<String,String> taxes= mx.getTaxes(tk.getNumeroboleto());
			JIterator<String> keys = taxes.getKeyIterator();
			
			while (keys.hasMoreElements()) {
				String key = keys.nextElement();
				String value = taxes.getElement(key);
				if (!JTools.isNumber(value))
					continue; // aveces viene E
				if ((","+imp+",").indexOf(","+key+",")!=-1) iva += Double.parseDouble(value);
				else noiva += Double.parseDouble(value);
			}
			tax1=imp.indexOf(",")==-1?imp:imp.substring(0,imp.indexOf(","));
			tax2="--";
			tax3="";
			 t1 = iva;
			 t2 =noiva;
			 t3=0;
	
		} else {
			 tax1 = m2.getTax1Id();
			 tax2 = m2.getTax2Id();
			 tax3 = m2.getTax3Id();

			 t1 = Double.parseDouble(m2.getTax1Amount());
			 t2 = Double.parseDouble(m2.getTax2Amount());
			 t3 = Double.parseDouble(m2.getTax3Amount());
				if ((","+imp+",").indexOf(","+tax1+",")!=-1) iva += t1;
				else noiva += t1;
				if ((","+imp+",").indexOf(","+tax2+",")!=-1) iva += t2;
				else noiva += t2;				
				if ((","+imp+",").indexOf(","+tax3+",")!=-1) iva += t3;
				else noiva += t3;
		}


		double fareAmountWithTaxes = Double.parseDouble(m2.getTotalFareAmount());
		double fareAmountInOriginalCurrency = Double.parseDouble(m2.getFareAmount());
		double realamount = Double.parseDouble(m2.getRealNetAmount());

		double fareAmount = fareAmountWithTaxes - t1 - t2 - t3;
		tk.setCodigoMonedaLocal(BizCompany.getObjCompany(tk.getCompany()).getCurrency());

		double taxtot = 0.0f;
		if (!tax1.equals("XT") || tk.getGDS().equals("NDC")) 
			taxtot += t1;
		if (!tax2.equals("XT") || tk.getGDS().equals("NDC"))
			taxtot += t2;
		if (!tax3.equals("XT") || tk.getGDS().equals("NDC"))
			taxtot += t3;

		if (t1 + t2 + t3 + realamount > (fareAmountWithTaxes + 1)) {
			fareAmount = fareAmountWithTaxes - taxtot;
		}
		String currLocal= getMonedaDefault(getCompany());
		String curr=m2.getFareCurrencyCode().equals("")?m2.getTotalFareCurrencyCode():m2.getFareCurrencyCode();
		double exchangeRate=1;
		if (fareAmount==0 && fareAmountInOriginalCurrency!=0 ) {
			double cotiz ;
			double cotBase = curr.equals("USD")?1:BizMonedaCotizacion.readCotizacionCorrienteAt(tk.getCompany(), tk.getObjCompany().getCountry(), "USD",curr, tk.getCreationDate());
			double cotLocal = currLocal.equals("USD")?1:BizMonedaCotizacion.readCotizacionCorrienteAt(tk.getCompany(), tk.getObjCompany().getCountry(), "USD",currLocal, tk.getCreationDate());
			
			if (cotBase==0) {
				cotiz= BizMonedaCotizacion.readCotizacionCorrienteAt(tk.getCompany(), tk.getObjCompany().getCountry(), curr, currLocal, tk.getCreationDate());
			} else {
				cotBase = cotBase==0?1:cotBase;
				cotLocal = cotLocal==0?1:cotLocal;
				cotiz = cotLocal/cotBase;
				
			}
			exchangeRate = cotiz==0?1:cotiz;
			if (cotiz<1000 && cotiz!=1)
				PssLogger.logInfo("debug point");

			if (cotiz == 0) {
				fareAmount = 0;
				fareAmountWithTaxes = fareAmount + taxtot;
				exchangeRate = fareAmountInOriginalCurrency == 0 ? 1 : realamount / fareAmountInOriginalCurrency;
			}
	//		tk.setCodigoMoneda(m2.getTotalFareCurrencyCode());
			tk.setCodigoMoneda(currLocal);
			tk.setCodigoBaseMoneda(curr);
	
		} else {
//			double cotLocal = currLocal.equals("USD")?1:BizMonedaCotizacion.readCotizacionCorrienteAt(tk.getCompany(), tk.getObjCompany().getCountry(), "USD",currLocal, tk.getCreationDate());
			 exchangeRate = fareAmountInOriginalCurrency == 0 ? 1 : realamount / fareAmountInOriginalCurrency;
//			 if (m2.getFareCurrencyCode().equals("USD") && (exchangeRate<cotLocal-(cotLocal*.2)) || exchangeRate>(cotLocal+(cotLocal*.2))) 
//				 exchangeRate=cotLocal;
//			 else 
				 if (m2.getTotalFareCurrencyCode().equals(m2.getFareCurrencyCode()))
					exchangeRate = 1;
			
				tk.setCodigoMoneda(m2.getTotalFareCurrencyCode());
				tk.setCodigoBaseMoneda(curr);
		}
		if (exchangeRate > 0)
			tk.setTipoCambio(exchangeRate);
		tk.setTarifaBaseConImpuestos(fareAmountWithTaxes);
		tk.setTarifaBase(fareAmountInOriginalCurrency);
		tk.setTarifa(fareAmount);
		tk.setPrivateFareIndicator(m2.getPrivateFareIndicator());
		tk.setTarifaYQ(tk.getTarifa() + tk.getYQ());
		tk.setTarifaFacturadaYQ(tk.getTarifaFactura() + tk.getYQ());

	}

	private double getMxAllTaxForTicket(String ticket) throws Exception {

		double tax = 0.0f;
		MXRecord mx = ((MXRecord) mRecords.getElement(SabreRecord.MX));

		JMap<String, String> taxes = mx.getTaxes(ticket);

		if (taxes == null)
			return 0;

		JIterator<String> values = taxes.getValueIterator();

		while (values.hasMoreElements()) {
			String value = values.nextElement();
			if (!JTools.isNumber(value))
				continue; // aveces viene E
			tax += Double.parseDouble(value);
		}

		return JTools.rd(tax, 2);

	}

	private JRecords<BizPNRTax> saveMxTaxesFrom(BizPNRTicket tk) throws Exception {
		JRecords<BizPNRTax> recs = new JRecords<BizPNRTax>(BizPNRTax.class);
		recs.setStatic(true);

		int im = m0.getNumberOfM2();
		MXRecord mx = ((MXRecord) mRecords.getElement(SabreRecord.MX));

		for (int i = 0; i < im; i++) {
			M2Record m = (M2Record) (mRecords.getElement(SabreRecord.M2 + (i + 1)));
			if (m==null) continue;
			String ticket = m.getTicketNumber();

			JMap<String, String> taxes = mx.getTaxes(ticket);

			if (taxes == null)
				continue;

			JIterator<String> keys = taxes.getKeyIterator();
			while (keys.hasMoreElements()) {
				String key = keys.nextElement();
				String value = taxes.getElement(key);
				if (!JTools.isNumber(value))
					continue; // aveces viene E
				if (!this.isNormalTax(tk,key, Double.parseDouble(value)))
					continue;
				recs.addItem(this.createTax(m, key, Double.parseDouble(taxes.getElement(key)), tk));
			}

		}
		return recs;
	}

	private void saveTaxes(JRecords<BizPNRTax> recs, BizPNRTicket tk) throws Exception {
		int i = 0;
		JIterator<BizPNRTax> iter = recs.getStaticIterator();
		while (iter.hasMoreElements()) {
			BizPNRTax t = iter.nextElement();
			if (!t.getNumeroBoleto().equals(tk.getNumeroboleto()))
				continue;

			t.setId(tk.getId());
			t.setSecuencia(++i);
			t.insert();
		}
	}

	private void saveTaxesFromM2(M2Record m2, BizPNRTicket tk, JRecords<BizPNRTax> taxes) throws Exception {
		tk.getTaxes().delete();
		this.saveTaxes(taxes, tk);
	}

	private JRecords<BizPNRTax> generateTaxesFromM2(M2Record m2, BizPNRTicket tk) throws Exception {
		if (m0.hasToUseMxRecord() || hasMX(tk.getNumeroboleto())) {
			return this.saveMxTaxesFrom(tk);
		}

		JRecords<BizPNRTax> recs = new JRecords<BizPNRTax>(BizPNRTax.class);
		recs.setStatic(true);

		String tax[] = new String[3];
		double total[] = new double[3];

		tax[0] = m2.getTax1Id();
		tax[1] = m2.getTax2Id();
		tax[2] = m2.getTax3Id();

		total[0] = Double.parseDouble(m2.getTax1Amount());
		total[1] = Double.parseDouble(m2.getTax2Amount());
		total[2] = Double.parseDouble(m2.getTax3Amount());

		double realamount = Double.parseDouble(m2.getRealNetAmount());
//		if (m0.isPreviouslyInvoicedIndicator() && realamount <= 0.0f)
//			return recs;

		int xtTax = -1;

		double amountnotxt = 0.0f;
		boolean haszk = false;

		for (int i = 0; i < 3; i++) {
			if (!this.isNormalTax(tk,tax[i], total[i]))
				xtTax = i;
			else {
				recs.addItem(this.createTax(m2, tax[i], total[i], tk));
				if (tax[i].equals(SabreRecord.TAX_ZK))
					haszk = true;
				else
					amountnotxt += total[i];
			}
		}

		if (xtTax < 0)
			return recs;

		if (total[xtTax] < 0.0f)
			return recs;

		double totalfare = Double.parseDouble(m2.getTotalFareAmount());

		if (total[0] + total[1] + total[2] + realamount >=(totalfare ))
			return recs;

		M6Record m6 = this.getM6Record(m2);
		if (m6 == null && tk.getOriginalNumeroboleto().equals("")) {
			recs.addItem(this.createTax(m2, SabreRecord.TAX_XT, total[xtTax], tk));

			if (totalfare - 100 > total[0] + total[1] + total[2] + realamount) {
				double zk = totalfare - amountnotxt - realamount;
				recs.addItem(this.createTax(m2, SabreRecord.TAX_ZK, JTools.rd(zk, 2), tk));
			}
			return recs;
		}

		double sumtax = 0.0f;

		JMap<String, String> map = m6.getTaxCollection();
		JIterator<String> iter = map.getKeyIterator();
		while (iter.hasMoreElements()) {
			String key = iter.nextElement();
			double monto = Double.parseDouble(map.getElement(key));
			if (m2.getPassengerType().equals(m6.getPassengerType())) {
				if (key.equals(SabreRecord.TAX_ZK))
					haszk = true;
				else {
					sumtax += monto;
					recs.addItem(this.createTax(m2, key, monto, tk));
				}
			}
		}

		if (haszk) {
			if (totalfare - 100 > sumtax + amountnotxt + realamount) {
				double zk = totalfare - sumtax - amountnotxt - realamount;
				recs.addItem(this.createTax(m2, SabreRecord.TAX_ZK, JTools.rd(zk, 2), tk));
			}
		}
		return recs;
	}

	private M6Record getM6Record(M2Record rec) throws Exception {
		return getM6Record(rec, false);
	}

	private M6Record getM6Record(M2Record rec, boolean ignoreXT) throws Exception {
		int im = m0.getNumberOfM6();
		for (int i = 0; i < im; i++) {
			M6Record m = (M6Record) (mRecords.getElement(SabreRecord.M6 + (i + 1)));
			if (m == null)
				break;
			if (ignoreXT)
				return m;
			if (rec.getPassengerType().equals(m.getPassengerType())) {
				if (rec.getTaxXTAmount() == m.getXTAmount() || m.getXTAmount() == 0.0)
					return m;
			}
		}

		return null;
	}

	/**
	 * @param m
	 * @param taxid1
	 * @throws Exception
	 */
	private boolean isNormalTax(BizPNRTicket tk,String taxid, double taxamount) throws Exception {
		if (taxid.equals(""))
			return false;
//		if (taxid.equals(SabreRecord.TAX_XT) && !tk.getGDS().equals("NDC"))
//			return false;
		if (taxamount <= 0.0)
			return false;
		return true;
	}

	// private boolean insertOrUpdateTaxNoXT(M2Record m, String taxid1, String
	// taxamount) throws Exception {
	// if (taxid1.equals("") == false && taxid1.equals(SabreRecord.TAX_XT) ==
	// false) {
	// double tax = Double.parseDouble(taxamount);
	// if (tax <= 0.0)
	// return false;
	// this.insertOrUpdateTax(m, taxid1, taxamount);
	// return true;
	// }
	// return false;
	// }

	private BizPNRTax createTax(M5Record m5, String taxid, double taxamount, BizPNRTicket tk) throws Exception {
		M8Record m8 = (M8Record) mRecords.getElement(SabreRecord.M8);
		String defa= getMonedaDefault(getCompany());
		String curr = defa;
		
		BizPNRTax tax = new BizPNRTax();
		tax.setCompany(this.getCompany());
		tax.setCodigopnr(tk.getCodigopnr());
		// tax.setId(this.getInterfaceId());
		tax.setCodigopnr(this.getPnrLocator());
		tax.setNumeroBoleto(m5.getTicketNumber());
		tax.setCodigomoneda(curr);
		tax.setCodigomonedaLocal(tk.getCodigoMonedaLocal());

		tax.setCodigoimpuesto(taxid);
		tax.setImporte(taxamount);
		tax.setTarifasEnDolares(tk);

		return tax;
	}

	private BizPNRTax createTax(M2Record m2, String taxid, double taxamount, BizPNRTicket tk) throws Exception {
		BizPNRTax tax = new BizPNRTax();
		tax.setCompany(tk.getCompany());
		tax.setId(tk.getId());
		tax.setCodigopnr(tk.getCodigopnr());
		tax.setNumeroBoleto(m2.getTicketNumber());
		tax.setCodigomoneda(m2.getTotalFareCurrencyCode());
		tax.setCodigomonedaLocal(tk.getCodigoMonedaLocal());

		tax.setCodigoimpuesto(taxid);
		tax.setImporte(taxamount);
		tax.setTarifasEnDolares(tk);

		return tax;
	}

	private void assignPnrDataFormM8(BizPNRTicket tk) throws Exception {
		M8Record m8 = (M8Record) mRecords.getElement(SabreRecord.M8);
		if (m8 == null)
			return;

		if (!m8.getCustomerId().equals("")) {
			tk.setCodigoCliente(m8.getCustomerId());
		}
		if (!m8.getCustomer().equals("")) {
			tk.setCliente(m8.getCustomer());
		}
		if (!m8.getEmployee().equals("")) {
			tk.setEmpleadoID(m8.getEmployee());
		}
		if (!m8.getEmailID().equals("")) {
			tk.setEmailID(m8.getEmailID());
		}
		if (!m8.getAV().equals("")) {
			tk.setAvRemark(m8.getAV());
		}
		if (!m8.getBusinessGroup().equals("")) {
			tk.setBusinessGroupRemark(m8.getBusinessGroup());
		}
		if (!m8.getFechaAuth().equals("")) {
			tk.setFechaAuthRemark(m8.getFechaAuth());
		}
		if (!m8.getFechaCreate().equals("")) {
			tk.setFechaCreationRemark(m8.getFechaCreate());
		}
		if (!m8.getReasonCode().equals("")) {
			tk.setReasonCodeRemark(m8.getReasonCode());
		}
		if (!m8.getResonCodeHotel().equals("")) {
			tk.setReasonCodeHotelRemark(m8.getResonCodeHotel());
		}
		if (!m8.getCostCener().equals("")) {
			tk.setCentroCostos(m8.getCostCener());
		}
		if (!m8.getObservation().equals("")) {
			tk.setObservacion(m8.getObservation());
		}
		if (!m8.getRegion().equals("")) {
			tk.setRegion(m8.getRegion());
		}
		if (!m8.getClase().equals("")) {
			tk.setTipoClase(m8.getClase());
		}
		if (!m8.getDepartamento().equals("")) {
			tk.setDepartamento(m8.getDepartamento());
		}
		if (!m8.getAutorizationCreditCard().equals("")) {
			tk.setAutorizationCreditCard(m8.getAutorizationCreditCard());
		}
		if (!m8.getCodSegCreditCard().equals("")) {
			tk.setCodSegCreditCard(m8.getCodSegCreditCard());
		}
		if (!m8.getDepartamento().equals("")) {
			tk.setDepartamento(m8.getDepartamento());
		}
		if (!m8.getCodigoNegocio().equals("")) {
			tk.setCodigoNegocio(m8.getCodigoNegocio());
		}
		if (!m8.getVendor().equals("")&&!company.equals("CHA")) { //hardcode los vendedores de chasma se toman del m0 el resto de su m8, hay que agregar una opcion de configuracion!
			tk.setVendedor(m8.getVendor());
		}
		if (!m8.getSucursal().equals("")) {
			tk.setOfficeId(m8.getSucursal());
		}
		if (m8.getCorporativo()!=null) {
			tk.setCorporativo(m8.getCorporativo());
		}
		if (!m8.getCuenta().equals("")) {
			tk.setCuenta(m8.getCuenta());
		}
		if (!m8.getProposito().equals("")) {
			tk.setProposito(m8.getProposito());
		}
		if (m8.getFechaReserva(getYear())!=null) {
			tk.setReserva(m8.getFechaReserva(getYear()));
		}
		if (!m8.getFareSavings().equals("")) {
			tk.setFareSavings(m8.getFareSavings());
		}
		if (!m8.getSolicitante().equals("")) {
			tk.setSolicitante(m8.getSolicitante());
		}
		if (m8.getHighFare()!=0) {
			tk.setFareHigh(m8.getHighFare());
		}
		if (m8.getLowFare()!=0) {
			tk.setFareLow(m8.getLowFare());
		}
		if (tk.getTourCode().equals("") && !m8.getTourCode().equals("")) {
			tk.setTourCode(m8.getTourCode());
		}
		if (m8.getCostoFee().equals("")) {
			tk.setCostoFee(m8.getCostoFee());
		}
		
		this.assignAdditionalFee(tk);

	}
	private M5Record findM5Hotel() throws Exception {
		JIterator<M5Record> iter = this.getM5Records(true).getIterator();
		while (iter.hasMoreElements()) {
			M5Record m5 = iter.nextElement();
			if (!m5.hasTicketNumber())
				continue;

			return m5;
		}
		return null;
	}
	private M5Record findM5ByTicket(String tkt, boolean filtrar) throws Exception {
		JIterator<M5Record> iter = this.getM5Records(true).getIterator();
		while (iter.hasMoreElements()) {
			M5Record m5 = iter.nextElement();
			if (!m5.hasTicketNumber())
				continue;
			if (!m5.getTicketNumber().equals(tkt))
				continue;
			if (filtrar && !m5.isOne())
				continue;
//			if (filtrar && m5.isEMD())
//				continue;

			return m5;
		}
		return null;
	}

	
	private void assignAdditionalFee(BizPNRTicket tk) throws Exception {
		M8Record m8 = (M8Record) mRecords.getElement(SabreRecord.M8);
		if (m8 == null)	return;
		tk.setTarifaReal(m8.getTarifaReal1(tk.getNumeroboleto()));
		tk.setImporteoverRmk(m8.getOverGain1(tk.getNumeroboleto()));
		tk.setAdicionalFee(JTools.getNumberEmbeddedWithDecSigned(m8.getServiceFee().replace(",", ".")));
		tk.setCostoFee(JTools.getNumberEmbeddedWithDecSigned(m8.getCostoFee().replace(",", ".")));
		tk.setPagoFee(m8.getPagoFee());
		tk.setOrdenFee(m8.getOrdenFee());
		tk.setConsumo(m8.getConsumo());
	  tk.setConsumoNum(JTools.getLongFirstNumberEmbedded(tk.getConsumo()));

	}

	private M1Record findM1ByPax(String paxId) throws Exception {
		int im = m0.getNumberOfM1();

		for (int i = 0; i < im; i++) {
			M1Record m1 = (M1Record) (mRecords.getElement(SabreRecord.M1 + (i + 1)));
			if (m1==null) continue;
			if (m1.getPaxId()==null) continue;
			if (m1.getPaxId().equals(paxId))
				return m1;
		}
		return null;
	}
	private MGRecord findMGByPax(String paxId,String ticketNumber) throws Exception {
		int im = m0.getNumberOfM5();

		for (int i = 0; i < im; i++) {
			MGRecord mg = (MGRecord) (mRecords.getElement(SabreRecord.MG + (i + 1)));
			if (mg==null) continue;
			if (mg.getPaxId()==null) continue;
			if (!mg.getEMDId().equals(ticketNumber)) continue;
			if (mg.getPaxId().equals(paxId))
				return mg;
		}
		return null;
	}
	private MGRecord findMGByTicketnumber(String ticketNumber) throws Exception {
		int im = m0.getNumberOfM5();

		for (int i = 0; i < im; i++) {
			MGRecord mg = (MGRecord) (mRecords.getElement(SabreRecord.MG + (i + 1)));
			if (mg==null) continue;
			if (mg.getPaxId()==null) continue;
			if (!mg.getEMDId().equals(ticketNumber)) continue;
				return mg;
		}
		return null;
	}
	private M2Record findM2ByPax(String paxId) {
		int im2 = m0.getNumberOfM2();

		for (int i = 0; i < im2; i++) {
			M2Record m2record = (M2Record) (mRecords.getElement(SabreRecord.M2 + (i + 1)));
			if (m2record.getPaxId().equals(paxId))
				return m2record;
		}
		return null;
	}

	private M2Record findM2ByTicket(String ticket) throws Exception {
		int im = m0.getNumberOfM2();
		for (int i = 0; i < im; i++) {
			M2Record m = (M2Record) (mRecords.getElement(SabreRecord.M2 + (i + 1)));
			if (m!=null&&m.getTicketNumber().equals(ticket))
				return m;
		}
		return null;
	}


	private void assignPays(M5Record m5, M2Record m2, BizPNRTicket tk) throws Exception {
		if (m2 == null)
			m2 = this.findM2ByTicket(tk.getNumeroboleto());
		if (m5 == null) {
			JIterator<M5Record> iter = this.getM5Records(false).getIterator();
			while (iter.hasMoreElements()) {
				M5Record m5c = iter.nextElement();
				if (!m5c.hasTicketNumber())
					continue;
				if (!m5c.getTicketNumber().equals(tk.getNumeroboleto()))
					continue;
				if (!m5c.isOne())
					continue;
				if (m5c != null && m5c.isCash())
					continue;
				double amount = m5c != null ? m5c.getAmount() : 0;
				if (m2 != null) {
					tk.setNetRemit(m2.getNetRemit());
					double m2Card = m2.getCreditCardAmount();
					if (m2Card != 0d)
						amount = m2Card;
				}

				if (m2!=null)tk.setAutorizationCreditCard(m2.getCreditCardAutorization());
				tk.setMontoTarjeta(tk.getMontoTarjeta() + amount);
				tk.setNombreTarjeta(m5c.getFormOfPaymentDesc());
				tk.setNumeroTarjeta(m5c != null ? m5c.getCreditCardNumber() : null);

			}
			return;
		}
		
		if (m5 != null && m5.isCash())
			return;

		double amount = m5 != null ? m5.getAmount() : 0;
		if (m2 != null) {
			tk.setNetRemit(m2.getNetRemit());
			double m2Card = m2.getCreditCardAmount();
			if (m2Card != 0d)
				amount = m2Card;
		}

		if (m2!=null)tk.setAutorizationCreditCard(m2.getCreditCardAutorization());
		tk.setMontoTarjeta(tk.getMontoTarjeta() + amount);
		tk.setNombreTarjeta(m5.getFormOfPaymentDesc());
		tk.setNumeroTarjeta(m5 != null ? m5.getCreditCardNumber() : null);
	}

	private void assignSumTaxes(BizPNRTicket tk, JRecords<BizPNRTax> taxes, M2Record m2) throws Exception {
		double sumTax = 0;
		double sumIva = 0;
		String imp = (BizBSPCompany.getConfigView(tk.getCompany()).getTipoImpuesto());
		tk.setImpuestos(0);
		tk.setIva(0);
		tk.setYQ(0);
		JIterator<BizPNRTax> it = taxes.getStaticIterator();
		while (it.hasMoreElements()) {
			BizPNRTax tax = it.nextElement();
			if (!tax.getNumeroBoleto().equals(tk.getNumeroboleto()))
				continue;
			String currLocal = getMonedaDefault(getCompany());
			String currBase = tax.getCodigomoneda();
			double cotiz = 1;
//			if (!currBase.equals(currLocal)) {
//				double cotBase = currBase.equals("USD") ? 1 : BizMonedaCotizacion.readCotizacionCorrienteAt(tk.getCompany(), tk.getObjCompany().getCountry(), "USD", currBase, tk.getCreationDate());
//				double cotLocal = currLocal.equals("USD") ? 1 : BizMonedaCotizacion.readCotizacionCorrienteAt(tk.getCompany(), tk.getObjCompany().getCountry(), "USD", currLocal, tk.getCreationDate());
//				cotBase = cotBase == 0 ? 1 : cotBase;
//				cotLocal = cotLocal == 0 ? 1 : cotLocal;
//				cotiz = cotLocal / cotBase;
//				cotiz = cotiz == 0 ? 1 : cotiz;
//				if (cotiz<1000 && cotiz!=1)
//					PssLogger.logInfo("debug point");
//
//			}
			if ((","+imp+",").indexOf(","+tax.getCodigoimpuesto()+",")!=-1)
				sumIva += tax.getImporte()*cotiz;
			else
				sumTax += tax.getImporte()*cotiz;
			if (tax.getCodigoimpuesto().equals("YQ"))
				tk.setYQ(tax.getImporte()*cotiz);
		}
		
		if (sumTax != 0 && sumIva==0 && m2!=null) {
			int im = m0.getNumberOfM6();
			for (int i = 0; i < im; i++) {
				M6Record m6 = (M6Record) (mRecords.getElement(SabreRecord.M6 + (i + 1)));
				if (m6 == null)
					continue;
				JMap<String, String> map = m6.getTaxCollection();
				JIterator<String> iter = map.getKeyIterator();
				while (iter.hasMoreElements()) {
					String key = iter.nextElement();
					double monto = Double.parseDouble(map.getElement(key));
					if (m2.getPassengerType().equals(m6.getPassengerType())) {
						if ((","+imp+",").indexOf(","+key+",")!=-1) {
							
							sumIva=monto;
							sumTax-=monto;
							break;
						}
					}
				}
				
			}
			
		}
		tk.setImpuestos(sumTax);
		tk.setIva(sumIva);

	}


	private void saveTaxesFormM5(M5Record m5, BizPNRTicket tk, JRecords<BizPNRTax> taxes) throws Exception {
		tk.getTaxes().delete();
		this.saveTaxes(taxes, tk);
	}

	private JRecords<BizPNRTax> generateTaxesFormM5(M5Record m5, BizPNRTicket tk) throws Exception {

		JRecords<BizPNRTax> recs = new JRecords<BizPNRTax>(BizPNRTax.class);
		recs.setStatic(true);

		recs.addItem(this.createTax(m5, SabreRecord.TAX_XT, m5.getTaxAmount(), tk));
		int tax=1;
		Double iva = m5.getTaxLocal();
		if (iva!=null) {
			String imp = (BizBSPCompany.getConfigView(tk.getCompany()).getTipoImpuesto());
			if (imp.indexOf(",")!=-1) imp = imp.substring(0,imp.indexOf(","));
			recs.addItem(this.createTax(m5,imp, iva, tk));
		}
		while (true) {
			Double tasa = m5.getOtherTax(tax++);
			if (tasa==null) break;
			recs.addItem(this.createTax(m5,"TAX_"+tax, tasa.doubleValue(), tk));
		}
		return recs;
	}

	private JList<M2Record> getM2Records(boolean onlyTickets) throws Exception {
		JList<M2Record> list = JCollectionFactory.createList();
		int im2 = m0.getNumberOfM2();
		for (int i = 0; i < im2; i++) {
			M2Record m2 = (M2Record) (mRecords.getElement(SabreRecord.M2 + (i + 1)));
			if (m2 == null)
				continue;
			list.addElement(m2);
		}
		return list;
	}

	private JList<M5Record> getM5Records(boolean onlyTickets) throws Exception {
		JList<M5Record> list = JCollectionFactory.createList();
		int im5 = m0.getNumberOfM5();
		for (int i = 0; i < im5; i++) {
			M5Record m5 = (M5Record) (mRecords.getElement(SabreRecord.M5 + (i + 1)));
			if (m5 == null)
				continue;
			if (!m5.hasTicketNumber())
				continue;
			if (m5.getPaxId()==null)
				continue;
			if (m5.getPaxId().equals("99"))
				continue;
			if (onlyTickets && m5.hasMFlag())
				continue;
			list.addElement(m5);
		}
		return list;
	}
	private JList<M5Record> getAllM5Records() throws Exception {
		JList<M5Record> list = JCollectionFactory.createList();
		int im5 = m0.getNumberOfM5();
		for (int i = 0; i < im5; i++) {
			M5Record m5 = (M5Record) (mRecords.getElement(SabreRecord.M5 + (i + 1)));
			if (m5 == null)
				continue;
			list.addElement(m5);
		}
		return list;
	}

	private M5Record findReemisor(String reemitido) throws Exception {
		int im5 = m0.getNumberOfM5();
		for (int i = 0; i < im5; i++) {
			M5Record m5 = (M5Record) (mRecords.getElement(SabreRecord.M5 + (i + 1)));
			if (m5 == null)
				continue;
			if (m5.getFieldValue(M5Record.E_DATA)==null || m5.getFieldValue(M5Record.E_DATA).length() < 16) 
				continue;
			String ticketOriginal = Tools.getOnlyNumbers(m5.getFieldValue(M5Record.E_DATA).substring(6, 16));
			if (ticketOriginal.equals(reemitido)) 
				return m5;
		}
		return null;
	}


	public boolean isType2() throws Exception {
		return m0.getTransactionType().equals("2");
	}

	
	private void saveOneReserva(M1Record m1) throws Exception {

		BizPNRReserva tk = new BizPNRReserva();
		tk.dontThrowException(true);
		long idPax=Long.parseLong(m1.getPaxId());
		boolean exist = tk.readByPNR(getCompany(),m0.getIATA(),m0.getPNRLocator(),idPax);
		tk.setCompany(this.getCompany());
		tk.setOrigen(this.getOrigen());
		tk.setGDS(this.getGds());
		tk.setArchivo(this.getPnrFile());
		tk.setCodigoPNR(m0.getPNRLocator());
		tk.setIdPax(idPax);
		tk.setNroIata(m0.getIATA());
		tk.setOfficeId(m0.getOfficeLocation());
		tk.setVendedor(m0.getVendedor());
		tk.setCreationDate(m0.getCreationDate(getYear()));
		tk.setCityCode(m0.getOriginCityCode());
		tk.setPNRDate(m0.getPNRDate(getYear()));
		tk.setCreationDateAir(AA.getCreationDate(getYear()));
		if (this.getCompany().equals("SOL" ))
			tk.setCustomerId(m0.getCustomer());
		else
			tk.setCustomerId(m0.getCustomerNumber());
		tk.setCustomerIdReducidoFromCustomer(m0.getCustomer());
		tk.setTransactionType(m0.getTransactionType());
		tk.setTipoPrestacion(m0.getTipoPrestacion());
		tk.setTipoOperacion("RES");
		tk.setVersion(m0.getInterfaceVersionNumber());
		tk.setNombrePasajero(m1.getPaxName());
		tk.setVoid(false);
    tk.setFechaProcesamiento(new Date());



    if (exist)
   	tk.processUpdate();
   else
   	tk.processInsert();
		
		String firstCarrier = null;
		Date fechaSalida = null;
		JRecords<BizPNRSegmentoAereo> segments = new JRecords<BizPNRSegmentoAereo>(BizPNRSegmentoAereo.class);
		segments.setStatic(true);
		String aerolinea=null;
		int im3 = m0.getNumberOfM3();
		for (int i = 0; i < im3; i++) {
			M3Record m = (M3Record) (mRecords.getElement(SabreRecord.M3 + (i + 1)));
			boolean segmentoInternacional = false;
			if (m==null || !m.isAirItinerary() )//&& !m.isBusItinerary())
				continue;

			
			if (fechaSalida == null) {
				fechaSalida = JDateTools.StringToDate(m.getDepartureDate(getYear(), getCreationDate()), "yyyyMMdd");
			}
			BizPNRSegmentoAereo sa = new BizPNRSegmentoAereo();
			sa.setId(tk.getId());
			sa.setCompany(tk.getCompany());
			sa.setCodigoPNR(tk.getCodigopnr());
			sa.setCodigoSegmento(m.getItemNumber());

			if (m.isAirItinerary()) {
				sa.setDespegue(m.getDepartureCityCode());
				sa.setGeoDespegue(this.findGeoAirport(sa.getDespegue()));
				sa.setArrivo(m.getArrivalCityCode());
				sa.setGeoArrivo(this.findGeoAirport(sa.getArrivo()));
				aerolinea=m.getCarrierCode();
				checkAerolinea(m.getCarrierCode());
				checkAerolinea(m.getCarrierCodeOp());
				sa.setCarrier(m.getCarrierCode());
				sa.setCarrierOp(m.getCarrierCodeOp());
				sa.setCarrierPlaca(tk.getCarrier());

				sa.setRuta(m.getDepartureCityCode() + "/" + m.getArrivalCityCode());

				BizAirport airportDespegue = sa.getObjAeropuertoDespegue();
				BizAirport airportArribo = sa.getObjAeropuertoArrivo();

				if (airportArribo.getCountry().equals(tk.getObjCompany().getCountry()))
					sa.setPaisDestino(airportDespegue.getCountry());
				else
					sa.setPaisDestino(airportArribo.getCountry());

				segmentoInternacional = !airportDespegue.getCountry().equals(tk.getObjCompany().getCountry()) || !airportDespegue.getObjCountry().GetPais().equals(airportArribo.getObjCountry().GetPais());
				if (firstCarrier == null && segmentoInternacional) {
					firstCarrier = m.getCarrierCode();
				}
			}

			sa.setNumeroVuelo(m.getFlightNumber());
			sa.setClase(m.getClassOfService());
			BizClaseDetail cd =  PNRCache.getTipoClaseCache(getCompany(), tk.getCarrier(), m.getClassOfService(),segmentoInternacional);
			if (cd == null) {
				sa.setTipoClase(BizClase.getTipoClaseDefault(m.getClassOfService()));
				sa.setPrioridad(BizClase.getPrioridadClaseDefault(m.getClassOfService()));
			} else {
				sa.setTipoClase(cd.getObjClase().getDescripcion());
				sa.setPrioridad(cd.getPrioridad());
			}
			PssLogger.logDebug(m.getDepartureDate(year, tk.getCreationDate()));
			sa.setFechaDespegue(JDateTools.StringToDate(m.getDepartureDate(year, tk.getCreationDate()), "yyyyMMdd"));
			sa.setFechaArrivo(JDateTools.StringToDate(m.getArrivalDate(year, tk.getCreationDate()), "yyyyMMdd"));
			sa.setHoraDespegue(m.getDepartureTime());
			sa.setHoraArrivo(m.getArrivalTime());
			if (m.isAirItinerary()) {
				sa.setEstado(m.isHoldingConfirmed() ? "OK" : m.getSegmentStatusCode());
				sa.setCodigoComida(m.getMealServiceCode());
				sa.setTipoEquipo(m.getEquipmentTypeCode());
				sa.setCodigoEntreten(m.getMealServiceCode());
				sa.setDuracion(m.getElapsedTimeCode());
			}

			sa.setEmitido(false);
			segments.addItem(sa);

		}
		checkAerolinea(aerolinea);
		tk.setCodigoaerolinea(aerolinea);

		tk.processAnalizeSegments(segments);
		tk.update();

	}

}
