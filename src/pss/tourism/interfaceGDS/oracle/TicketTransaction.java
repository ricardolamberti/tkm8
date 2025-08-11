package pss.tourism.interfaceGDS.oracle;

import java.util.Date;
import java.util.regex.Pattern;

import pss.bsp.clase.BizClase;
import pss.bsp.clase.detalle.BizClaseDetail;
import pss.bsp.consolid.model.trxOra.BizTrxOra;
import pss.bsp.consolid.model.trxseg.BizArTrxDKSeg;
import pss.common.regions.company.BizCompany;
import pss.common.security.BizUsuario;
import pss.core.services.records.JRecords;
import pss.core.tools.JDateTools;
import pss.core.tools.JTools;
import pss.core.tools.PssLogger;
import pss.core.tools.collections.JIterator;
import pss.tourism.airports.BizAirport;
import pss.tourism.interfaceGDS.BaseTransaction;
import pss.tourism.pnr.BizPNRSegmentoAereo;
import pss.tourism.pnr.BizPNRTax;
import pss.tourism.pnr.BizPNRTicket;
import pss.tourism.pnr.PNRCache;

public class TicketTransaction extends BaseTransaction {
	private BizPNRTax createTax(String moneda, String taxid, double taxamount, BizPNRTicket tk) throws Exception {
		BizPNRTax tax = new BizPNRTax();
		tax.setCompany(tk.getCompany());
		tax.setId(tk.getId());
		tax.setCodigopnr(tk.getCodigopnr());
		tax.setNumeroBoleto(tk.getNumeroboleto());
		tax.setCodigomoneda(moneda);
		tax.setCodigomonedaLocal(tk.getCodigoMonedaLocal());

		tax.setCodigoimpuesto(taxid);
		tax.setImporte(taxamount);
		tax.setTarifasEnDolares(tk);

		return tax;
	}

	public boolean isContado(String doc) {
		if (doc.equals("CH"))
			return true;
		if (doc.equals("NI"))
			return true;
		if (doc.equals("TR"))
			return true;
		if (doc.equals("CA"))
			return true;
		return false;
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
			t.keysToFilters();
			if (t.exists())
				t.update();
			else
				t.insert();
		}
	}

	private void linkSegmentos(JRecords<BizPNRSegmentoAereo> recs, BizPNRTicket tk) throws Exception {
		int i = 0;
		JIterator<BizPNRSegmentoAereo> iter = recs.getStaticIterator();
		while (iter.hasMoreElements()) {
			BizPNRSegmentoAereo t = iter.nextElement();

			t.setId(tk.getId());

		}
	}

	public static String identifyRutaType(String servicio, String ruta) {
		if (ruta == null || ruta.trim().isEmpty()) {
			return "EMD";
//			throw new IllegalArgumentException("La ruta no puede ser nula o vacía");
		}

		if (servicio.indexOf("AEREAS") == -1)
			return "EMD";

		// Patrón mejorado para ETR: rutas con segmentos separados por "/",
		// opcionalmente terminadas por "-".
		Pattern etrPattern = Pattern.compile("^(\\w{3})(/\\w{3})+(-/\\w{3})*$");

		if (etrPattern.matcher(ruta).matches()) {
			return "ETR";
		} else {
			return "EMD";
		}
	}


	public void buildTrx(String company, String nroBoleto, BizTrxOra tktFnd) throws Exception {
		boolean reproceso = tktFnd==null;
		if (nroBoleto.equals("3474605537"))
			PssLogger.logInfo("log point");
		if (tktFnd ==null) {
			tktFnd = new BizTrxOra();
			tktFnd.addFilter("boleto", nroBoleto+"R");
			tktFnd.addFilter("tipo_documento", "STE");
			tktFnd.dontThrowException(true);
			if (!tktFnd.read()) {
				tktFnd = new BizTrxOra();
				tktFnd.addFilter("boleto", nroBoleto);
				tktFnd.addFilter("tipo_documento", "STE");
				tktFnd.dontThrowException(true);
				if (!tktFnd.read()) {
					return;
				}
			}
		

		}
		if (tktFnd.getPnr().length()>10) return;
		boolean isVoid=	tktFnd.getRuta().indexOf("BOLETO CANCELADO") != -1;
		if (nroBoleto.isEmpty())
			return;
		if (tktFnd.getLineaAerea().length() != 2) {
			return ; //hotel
		}
		BizPNRTicket tkt = new BizPNRTicket();
		boolean segmentoInternacional = false;
		String firstCarrier = null;
		tkt.dontThrowException(true);
		if (tkt.ReadByBoleto(company, nroBoleto)) {
			if (tkt.isVoided()) {
				return;
			}
			if (isVoid && !tkt.isVoided() && tkt.getGDS().equals("ORACLE")) {
				PssLogger.logInfo("Se detecto Anulacion [" + nroBoleto + "]. Se intenta voidear.");
				tkt.setVoid(true);
				tkt.setVoidDate(tktFnd.getFechaFactura());
				tkt.update();
			}
			if (tkt.getGDS().equals("ORACLE") && 
					(tktFnd.getTotalFactura()!=tkt.getTarifaTotalFactura() || 
					(tktFnd.getTipoServicio().equals("LI") && !tkt.isInternacional()) ||
					(tktFnd.getTipoServicio().equals("LD") && tkt.isInternacional())
					)) {
				PssLogger.logInfo("Se detecto Diferencia [" + nroBoleto + "]. Se intenta actualizar.");
			//	tkt.deletePNR(tktOra.getPnr());
			} else {
				//if (!tkt.getGDS().equals("ORACLE"))
				if (!reproceso) //forzar
					return;				
			}

		} else {
			PssLogger.logInfo("Se detecto faltante [" + nroBoleto + "]. Se intenta cargar.");
			BizUsuario.eventInterfaz(this.getClass().getCanonicalName(), "Agregando tickets boleto "+nroBoleto, 1, 100, false, null);

		}
		BizTrxOra tktOra = null; 
		if (tktFnd.getTipoDocumento().equals("STE")) {
			tktOra = tktFnd;
		} else {
			tktOra = new BizTrxOra();
			tktOra.addFilter("boleto", tktFnd.getBoleto()+"R");
			tktOra.addFilter("tipo_documento", "STE");
			tktOra.dontThrowException(true);
			if (!tktOra.read()) {
				tktOra = new BizTrxOra();
				tktOra.addFilter("boleto", tktFnd.getBoleto());
				tktOra.addFilter("tipo_documento", "STE");
				tktOra.dontThrowException(true);
				if (!tktOra.read()) {
					return;
				}			}
		}
	//	tkt.deletePNR(tktOra.getPnr());
		BizTrxOra tktRFN = new BizTrxOra();
		tktRFN.addFilter("boleto", nroBoleto);
		tktRFN.addFilter("tipo_documento", "RFN");
		tktRFN.dontThrowException(true);
		if (tktRFN.read()) 
			isVoid=true;
		
			BizPNRTicket pnrTicket = new BizPNRTicket();
		pnrTicket.setCompany(company);
		pnrTicket.setNumeroboleto(nroBoleto);
		pnrTicket.setCodigoaerolinea(tktOra.getLineaAerea());
		checkAerolinea(tktOra.getLineaAerea());

		pnrTicket.setCustomerId(tktOra.getDk());
		pnrTicket.setCustomerIdReducido(tktOra.getDk());
		pnrTicket.setGDS("ORACLE");
		pnrTicket.setCodigopnr(tktOra.getPnr());
		pnrTicket.setVoid(isVoid);
		pnrTicket.setReemitted(false);
		pnrTicket.setTipoPasajero("ADT");
//		if (!tktOra.isNullFechaCreacionPnr())
//			pnrTicket.setCreationDateAir(JDateTools.StringToDate(JDateTools.ensureTimePresent(JDateTools.convertMonthLiteralToNumber(tktOra.getFechaCreacionPnr()), "dd-MM-yy"), "dd-MM-yy"));
//		else
			pnrTicket.setCreationDateAir(tktOra.getFechaEmision());
		pnrTicket.setCreationDate(tktOra.getFechaEmision());
		pnrTicket.setNombrePasajero(tktOra.getPasajero());

		JRecords<BizPNRTax> taxes = new JRecords<BizPNRTax>(BizPNRTax.class);
		taxes.setStatic(true);
		boolean isEMD = identifyRutaType(tktOra.getServicio(), tktOra.getRuta()).equals("EMD");
		taxes.getStaticItems().addElement(createTax(tktOra.getMoneda(), "MX", tktOra.getIva(), pnrTicket));
		taxes.getStaticItems().addElement(createTax(tktOra.getMoneda(), "XT", tktOra.getTua(), pnrTicket));
		this.assignSumTaxes(pnrTicket);
		pnrTicket.setNeto(tktOra.getTarifa());
		pnrTicket.setReemitted(false);
		pnrTicket.setEmision(true);
		pnrTicket.setComplete(true);
		pnrTicket.setCodigoBaseMoneda(BizCompany.getObjCompany(pnrTicket.getCompany()).getCurrency());
		pnrTicket.setCodigoMonedaLocal(BizCompany.getObjCompany(pnrTicket.getCompany()).getCurrency());
		pnrTicket.setCodigoMoneda(tktOra.getMoneda()==null?BizCompany.getObjCompany(pnrTicket.getCompany()).getCurrency():tktOra.getMoneda());
		pnrTicket.setIvaFactura(Math.abs(tktOra.getIva()));
		pnrTicket.setNetoFactura(Math.abs(tktOra.getTarifa()));
		pnrTicket.setNetoFacturaLocal(Math.abs(tktOra.getTarifa()));
		pnrTicket.setTarifaBaseFactura(Math.abs(tktOra.getTarifa()));
		pnrTicket.setTarifaFactura(Math.abs(tktOra.getTarifa()));
		pnrTicket.setTarifaFacturaLocal(Math.abs(tktOra.getTarifa()));
		pnrTicket.setTarifaTotalFactura(Math.abs(tktOra.getTotalFactura()));
		pnrTicket.setTarifaFacturadaYQ(Math.abs(tktOra.getTarifa()));
		pnrTicket.setImpuestosTotalFactura(Math.abs(tktOra.getIva()+tktOra.getTua()));
		pnrTicket.setImpuestoFactura(Math.abs( tktOra.getTua()));
		pnrTicket.setFechaProcesamiento(new Date());
		pnrTicket.setComisionFactura(0);
		pnrTicket.setTipoOperacion(isEMD ? "EMD" : "ETR");
		pnrTicket.setTarifa(Math.abs(tktOra.getTarifa()));
		pnrTicket.setNeto(Math.abs(tktOra.getTarifa()));
		pnrTicket.setImpuestosTotal(Math.abs(tktOra.getIva()+tktOra.getTua()));
		pnrTicket.setImpuestos(Math.abs(tktOra.getTua()));
		pnrTicket.setIva(Math.abs(tktOra.getIva()));
		pnrTicket.setTarifaBase(Math.abs(tktOra.getTarifa()));
		pnrTicket.setTarifaBaseConImpuestos(Math.abs(tktOra.getTarifa() + tktOra.getIva() + tktOra.getTua()));
		pnrTicket.setTarifaYQ(Math.abs(tktOra.getTarifa() + 0));
		pnrTicket.setTarifaYQ(Math.abs(pnrTicket.getTarifa() + pnrTicket.getYQ()));
		pnrTicket.setCodigoMonedaLocal(BizCompany.getObjCompany(pnrTicket.getCompany()).getCurrency());
		pnrTicket.setNroIata(JTools.getNumberEmbedded(tktOra.getIata().replace(" ", "")));
		
		Pay pay = new Pay();
		pay.amount = tktOra.getTotalFactura();
		if (isContado(tktOra.getFormaPago())) {
			pay.card = null;
		} else {
			pay.card = "0000XXXXXXXXXX0000";
			pay.cardName = "UNKNOWN";
			pay.auth = "Y";
		}
		mPays.addElement(nroBoleto, pay);
		assignPays(pnrTicket);

		boolean internacional = false;
		JRecords<BizPNRSegmentoAereo> listSeg = new JRecords<BizPNRSegmentoAereo>(BizPNRSegmentoAereo.class);
		listSeg.setStatic(true);

		JRecords<BizArTrxDKSeg> trxSegs = new JRecords<BizArTrxDKSeg>(BizArTrxDKSeg.class);
		trxSegs.addFilter("boleto", nroBoleto);
		trxSegs.addOrderBy("id_segment", "ASC");
		int seg = 0;
		Long lastIdSeg=null;
		if (!isEMD) {
			JIterator<BizArTrxDKSeg> it = trxSegs.getStaticIterator();
			while (it.hasMoreElements()) {
				BizArTrxDKSeg trxSeg = it.nextElement();
				BizPNRSegmentoAereo segmento = new BizPNRSegmentoAereo();
				if (trxSeg.getOriginCityCode() == null || trxSeg.getOriginCityCode().length() < 3)
					continue;// poca info
				if (trxSeg.getArrivalCityCode() == null || trxSeg.getArrivalCityCode().length() < 3)
					continue;// poca info
				if (lastIdSeg!=null && lastIdSeg.equals(trxSeg.getIdSegment()))
					continue;
				if (trxSeg.getOriginCityCode().equals("YYX"))
					PssLogger.logInfo("log point");
				lastIdSeg = trxSeg.getIdSegment();
				segmento.setCompany(company);
				segmento.setCarrier(trxSeg.getLineaAerea());
				checkAerolinea(segmento.getCarrier());
				segmento.setCarrierOp(trxSeg.getLineaAerea());
				segmento.setCarrierPlaca(trxSeg.getLineaAerea());
				segmento.setCodigoPNR(trxSeg.getPnr());
				pnrTicket.setCodigopnr(trxSeg.getPnr());
				//pnrTicket.setNroIata(trxSeg.getIata().replace(" ", ""));
				pnrTicket.setAgenteEmision(trxSeg.getAgenteEmite());
//				pnrTicket.setCreationDate(JDateTools.StringToDate(JDateTools.ensureTimePresent(JDateTools.convertMonthLiteralToNumber(trxSeg.getFechaCreacionPnr()), "dd-MM-yy"), "dd-MM-yy"));
				segmento.setCodigoSegmento("" + (++seg));
				segmento.setDespegue(trxSeg.getOriginCityCode());
				segmento.setGeoDespegue(this.findGeoAirport(segmento.getDespegue()));
				segmento.setArrivo(trxSeg.getArrivalCityCode());
				segmento.setGeoArrivo(this.findGeoAirport(segmento.getArrivo()));
				BizAirport airportDespegue = segmento.getObjAeropuertoDespegue();
				BizAirport airportArribo = segmento.getObjAeropuertoArrivo();

				if (airportArribo.getCountry().equals(pnrTicket.getObjCompany().getCountry()))
					segmento.setPaisDestino(airportDespegue.getCountry());
				else
					segmento.setPaisDestino(airportArribo.getCountry());

				segmentoInternacional = !airportDespegue.getCountry().equals(pnrTicket.getObjCompany().getCountry()) || !airportDespegue.getObjCountry().GetPais().equals(airportArribo.getObjCountry().GetPais());
				if (firstCarrier == null && segmentoInternacional) {
					firstCarrier = trxSeg.getLineaAerea();
				}

				segmento.setNumeroVuelo(trxSeg.getFlightNumber());
				segmento.setClase(trxSeg.getClase());
				BizClaseDetail cd = PNRCache.getTipoClaseCache(getCompany(), pnrTicket.getCarrier(), trxSeg.getClase(), segmentoInternacional);
				if (cd == null) {
					segmento.setTipoClase(BizClase.getTipoClaseDefault(trxSeg.getClase()));
					segmento.setPrioridad(BizClase.getPrioridadClaseDefault(trxSeg.getClase()));
				} else {
					segmento.setTipoClase(cd.getObjClase().getDescripcion());
					segmento.setPrioridad(cd.getPrioridad());
				}
				Date dsalida = JDateTools.StringToDate(JDateTools.ensureTimePresent(JDateTools.convertMonthLiteralToNumber(trxSeg.getFechaSalida()), "dd-MM-yy HH:mm"), "dd-MM-yy HH:mm");
				Date dregreso = trxSeg.getFechaRegreso().isEmpty() ? null : JDateTools.StringToDate(JDateTools.ensureTimePresent(JDateTools.convertMonthLiteralToNumber(trxSeg.getFechaRegreso()), "dd-MM-yy HH:mm"), "dd-MM-yy HH:mm");
				segmento.setFechaDespegue(dsalida);
				segmento.setFechaArrivo(dregreso);
				segmento.setHoraDespegue(JDateTools.DateToString(dsalida, "HH:mm"));
				if (dregreso != null)
					segmento.setHoraArrivo(JDateTools.DateToString(dregreso, "HH:mm"));
				segmento.setEstado("OK");
				segmento.setCodigoComida("");
				segmento.setTipoEquipo("");
				segmento.setCodigoEntreten("");
				segmento.setDuracion(trxSeg.getElapsedFlyingTime());
				segmento.setConnectionIndicator(BizPNRSegmentoAereo.SEGMENTO_CONNECTION);
				segmento.setFareBasic(trxSeg.getFareBasisCodeExp());
				segmento.setFareBasicExpanded(trxSeg.getFareBasisCodeExp());
				if (segmento.getObjCarrier() != null && segmento.getObjCarrier().getObjLogica() != null) {
					segmento.getObjCarrier().getObjLogica().doCarrierLogica(segmento.getFareBasic(), segmento);
				} else {
					segmento.setFamiliaTarifaria(segmento.getFareBasic() == null || segmento.getFareBasic().length() < 6 ? "" : segmento.getFareBasic().substring(3, 5));
				}
				segmento.setMonedaBase(trxSeg.getMoneda());
				segmento.setMoneda(trxSeg.getMoneda());
				segmento.setMontoOriginal(trxSeg.getImporte() / trxSegs.sizeStaticElements());
				segmento.setMonto(trxSeg.getImporte() / trxSegs.sizeStaticElements());
				segmento.setEmitido(true);
				internacional &= segmentoInternacional;
				listSeg.getStaticItems().addElement(segmento);
			}
			
			if (seg==0) {
				processRuta(tktOra.getRuta(),listSeg,pnrTicket);
			} 

		
	
		}
		setTarifasEnDolares(pnrTicket);
		if (tktOra.getTipoServicio().equals("LI") || tktOra.getTipoServicio().equals("LD")) {
			internacional = tktOra.getTipoServicio().equals("LI");
			pnrTicket.setInternacionalDescr(internacional ? "International" : "Domestic");
			pnrTicket.setInternacional(internacional);

		}
		save(pnrTicket);
		if (!isEMD)
			this.linkSegmentos(listSeg, pnrTicket);
		this.saveTaxes(taxes, pnrTicket);
		if (!isEMD)
			this.finalyzeSegments(pnrTicket, listSeg);
		// this.saveFareSegment(pnrTicket);
		if (!isEMD) {
			pnrTicket.calculeOver();
			pnrTicket.update();
		}
	}
	
	public void processRuta(String ruta, JRecords<BizPNRSegmentoAereo>listSeg,BizPNRTicket pnrTicket) throws Exception {
    if (ruta == null || ruta.trim().isEmpty()) {
        return ;
    }

    // Dividir la ruta en segmentos basados en "/"
    String[] segmentos = ruta.split("/");
    if (segmentos.length < 2) {
      return ;
   }
    int seg=0;
    boolean segmentoInternacional = false;
    boolean internacional=false;
    for (int i = 0; i < segmentos.length - 1; i++) {
        BizPNRSegmentoAereo segmento = new BizPNRSegmentoAereo();
        segmento.setCompany(company);

        // Establecer origen y destino
        String originCity = segmentos[i];
        String arrivalCity = segmentos[i + 1];
        segmento.setCodigoSegmento("" + (++seg));
				if (originCity.equals("YYX"))
					PssLogger.logInfo("log point");
        segmento.setDespegue(originCity);
        segmento.setArrivo(arrivalCity);
        segmento.setGeoDespegue(this.findGeoAirport(segmento.getDespegue()));
        segmento.setGeoArrivo(this.findGeoAirport(segmento.getArrivo()));
      BizAirport airportDespegue = segmento.getObjAeropuertoDespegue();
        BizAirport airportArrivo = segmento.getObjAeropuertoArrivo();

        segmento.setCodigoPNR(pnrTicket.getCodigopnr());
        // Buscar la información geográfica de los aeropuertos
 

        // Identificar si es internacional
        if (airportArrivo != null && airportDespegue != null) {
            segmentoInternacional = !airportDespegue.getCountry().equals(airportArrivo.getCountry());
            segmento.setPaisDestino(airportArrivo.getCountry());
        } else {
            segmento.setPaisDestino("UNKNOWN");
        }

       

        // Configurar datos predeterminados
        segmento.setCarrier(pnrTicket.getLineaAerea());
        segmento.setFechaDespegue(pnrTicket.getDepartureDate()); // Configura la fecha como el momento actual
        segmento.setFechaArrivo(pnrTicket.getArriveDate());
        segmento.setEstado("OK");
        segmento.setConnectionIndicator(BizPNRSegmentoAereo.SEGMENTO_CONNECTION);
				segmento.setEmitido(true);
				internacional &= segmentoInternacional;
        // Agregar segmento a la lista
        listSeg.getStaticItems().addElement(segmento);
    }
		pnrTicket.setInternacionalDescr(internacional ? "International" : "Domestic");
		pnrTicket.setInternacional(internacional);

    return ;
}

}
