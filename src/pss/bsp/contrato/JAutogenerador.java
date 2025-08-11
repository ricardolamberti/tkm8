package pss.bsp.contrato;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

import pss.bsp.contrato.detalle.BizDetalle;
import pss.core.services.records.JRecords;
import pss.core.tools.JDateTools;
import pss.core.tools.JTools;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JList;
import pss.tourism.airports.BizAirport;
import pss.tourism.pnr.GuiBookings;
import pss.tourism.pnr.GuiPNRTickets;

public class JAutogenerador {
	public static final int META= 1;
	public static final int BASE= 2;
	public static final int AUX = 3;
	
	BizDetalle detalle;
	long tipo;
	boolean generado;

	String outputSql;
	String outputSqlHistorico;
	String outputSqlDetalle;
	String outputSqlReserva;
	String outputField;
	String outputClass;
	String outputTitle;
	String outputSqlAux1;
	String outputSqlAux2;
	String outputDescrSqlAux1;
	String outputDescrSqlAux2;
	
	String fieldAerolinea;
	String fieldRuta;
	String fieldClase;
	String fieldHistorico2;
	String fieldHistorico1;
	String fieldDiario;
	String fieldVendedor;
	String fieldCliente;
	String fieldRutaDestino;
	String fieldRutaOrigen;
	String fieldRutaDestinoGeo;
	String fieldRutaOrigenGeo;
	
	String calculedField1Descripcion;
	String calculedField2Descripcion;
	String calculedField3Descripcion;
	String calculedField4Descripcion;
	String calculedField5Descripcion;
	String calculedField6Descripcion;
	
	Date fechaEmisionDesde;
	Date fechaEmisionHasta;
	Date fechaVueloDesde;
	Date fechaVueloHasta;
	Date fechaBlockoutDesde;
	Date fechaBlockoutHasta;
  boolean fechaFija;
  boolean fechaFijaEmision;
  boolean fechaFijaVuelo;
	
	String calculedFieldMSAerolinea;
	String calculedField2MSAerolinea;
	String calculedFromMSAerolinea;
	String calculedWhereMSAerolinea;
	String calculedGroupMSAerolinea;

	String calculedFromReservas;
	String calculedWhereReservas;

	String calculedFieldMSRuta;
	String calculedField2MSRuta;
	String calculedFromMSRuta;
	String calculedWhereMSRuta;
	String calculedGroupMSRuta;

	String calculedFieldMSClase;
	String calculedField2MSClase;
	String calculedFromMSClase;
	String calculedWhereMSClase;
	String calculedGroupMSClase;

	String calculedFieldRutas;
	String calculedField2Rutas;
	String calculedFromRutas;
	String calculedWhereRutas;
	String calculedGroupRutas;
	
	String calculedFieldDiario;
	String calculedField2Diario;
	String calculedFromDiario;
	String calculedWhereDiario;
	String calculedGroupDiario;
	
	String calculedFieldHistorico;
	String calculedField2Historico;
	String calculedFromHistorico;
	String calculedWhereHistorico;
	String calculedGroupHistorico;
	
	String calculedFieldVendedor;
	String calculedField2Vendedor;
	String calculedFromVendedor;
	String calculedWhereVendedor;
	String calculedGroupVendedor;
	
	String calculedFieldCliente;
	String calculedField2Cliente;
	String calculedField3Cliente;
	String calculedFromCliente;
	String calculedWhereCliente;
	String calculedGroupCliente;
	
	String calculedFromDetalle;
  boolean upfront;
	
	public JAutogenerador(BizDetalle zDetalle,long zTipo) {
		detalle = zDetalle;
		tipo = zTipo;
		generado=false;
	}
	public String getOutputSqlHistorico() throws Exception {
		autogenerar();
		return outputSqlHistorico;
	}
	public String getOutputSql() throws Exception {
		autogenerar();
		return outputSql;
	}
	public String getOutputSqlDetalle()  throws Exception {
		autogenerar();
		return outputSqlDetalle;
	}
	public String getOutputConsultaAux1() throws Exception {
		autogenerar();
		return outputSqlAux1;
	}
	public String getOutputConsultaAux2() throws Exception {
		autogenerar();
		return outputSqlAux2;
	}
	public String getOutputDescrConsultaAux1() throws Exception {
		autogenerar();
		return outputDescrSqlAux1;
	}
	public String getOutputDescrConsultaAux2() throws Exception {
		autogenerar();
		return outputDescrSqlAux2;
	}
	public String getOutputClass()  throws Exception {
		autogenerar();
		return outputClass;
	}
	public String getOutputSqlReserva()  throws Exception {
		autogenerar();
		return outputSqlReserva;
	}
	public String getOutputField()  throws Exception {
		autogenerar();
		return outputField;
	}
	public String getOutputField1Description()  throws Exception {
		autogenerar();
		return calculedField1Descripcion;
	}
	public String getOutputField2Description()  throws Exception {
		autogenerar();
		return calculedField2Descripcion;
	}
	public String getOutputField3Description()  throws Exception {
		autogenerar();
		return calculedField3Descripcion;
	}
	public String getOutputField4Description()  throws Exception {
		autogenerar();
		return calculedField4Descripcion;
	}
	public String getOutputField5Description()  throws Exception {
		autogenerar();
		return calculedField5Descripcion;
	}
	public String getOutputField6Description()  throws Exception {
		autogenerar();
		return calculedField6Descripcion;
	}
	public String getOutputTitle()  throws Exception {
		autogenerar();
		return outputTitle;
	}
	
	boolean isBooking;
	boolean isVolado;
	boolean isVoladoEmitido;
	boolean isTarifaBruta;
	boolean isMSCantidad;
	boolean isTarifaNetaSoloIda;
	boolean isPax;
	boolean isTarifa;
	boolean isPorcentajeSobreElTotal;
	boolean isPorcentajeSoloIdaSobreElTotal;
	boolean isPorcentajeSobreExcluidos;
	boolean isPorcentajeSobreGDSIncluidos;
	boolean hasOrigenContinente;
	boolean hasOrigenZona;
	boolean hasDestinoContinente;
	boolean hasDestinoZona;
	boolean isViceversa;
	boolean hasPrimera;
	boolean isPrimera;
	boolean isNoPrimera;
	boolean hasAndOr;
	boolean isAnd;
	boolean isOr;
	boolean hasTipoViajes;
	boolean isOpenJaw;
	boolean isRoundTrip;
	boolean hasMultidestino;
	boolean isMore4;
	boolean isLess4;
	boolean hasCambio;
	boolean isPosteriorDespegue;
	boolean isAntesDespegue;	
	boolean hasStopover;
	boolean isStopover;
	boolean isNoStopover;
	boolean hasVueltaMundo;
	boolean isVueltaMundo;
	boolean isNoVueltaMundo;
	boolean hasInterlineal;
	boolean isSoloUna;
	boolean isInterlineal;
	boolean isOmitirExchange;
	boolean isMSTarifa;
	boolean hasTourCodeExcluded;
	long getSepFecha;
	String getTourcodeExcludes;
	boolean hasTourCodeIncluded;
	String getTourcodeIncluded;
	boolean hasTipoPasajeroExcluded;
	String getTipoPasajeroExcludes;
	boolean hasTipoPasajeroIncluded;
	String getTipoPasajeroIncluded;
	boolean hasFareBasicExcluded;
	String getFareBasicExcludes;
	boolean hasFareBasicIncluded;
	String getFareBasicIncluded;
	boolean hasClasesExcluded;
	String getClasesExcludes;
	boolean hasClasesIncluded;
	String getClasesIncluded;
	boolean hasGDSExcluded;
	String getGDSExcludes;
	boolean hasGDSIncluded;
	String getGDSIncluded;
	boolean hasMercado;
	String getMercado;
	boolean hasNoMercado;
	String getNoMercado;
	boolean hasRutas;
	String getRutas;
	boolean hasNoRutas;
	String getNoRutas;
	boolean hasVuelos;
	String getVuelos;
	boolean hasNoVuelos;
	String getNoVuelos;
	boolean hasDestinoPais;
	boolean hasOrigenPais;
	boolean hasDestinoAeropuerto;
	boolean hasDomesticInternacional;
	boolean isDomestic;
	boolean isInternational;
	boolean hasOrigenAeropuerto;
	boolean isOrigenNegado;
	boolean isDestinoNegado;
	JList<String> getOrigenAeropuerto;
	JList<String> getOrigenContinente;
	JList<String> getOrigenZona;
	JList<String> getOrigenPais;
	JList<String> getDestinoAeropuerto;
	JList<String> getDestinoContinente;
	JList<String> getDestinoZona;
	JList<String> getDestinoPais;
	JList<String> getMeses;
	
	public void calcule() throws Exception {
		getMeses = detalle.getMeses();
		upfront=detalle.isUpfront();
		fechaEmisionDesde = detalle.getFechaEmisionDesde();
		fechaEmisionHasta = detalle.getFechaEmisionHasta();
		fechaVueloDesde = detalle.getFechaVueloDesde();
		fechaVueloHasta = detalle.getFechaVueloHasta();
		fechaBlockoutDesde = detalle.getFechaBlockoutDesde();
		fechaBlockoutHasta = detalle.getFechaBlockoutHasta();
		getSepFecha = detalle.getSepFecha();
		fechaFija = fechaEmisionDesde!=null||fechaEmisionHasta!=null||fechaVueloDesde!=null||fechaVueloHasta!=null||!getMeses.isEmpty();
		fechaFijaEmision = fechaEmisionDesde!=null||fechaEmisionHasta!=null||getMeses!=null;
		fechaFijaVuelo = fechaFija&&!fechaFijaEmision;
		isBooking = tipo==META?detalle.getPax():(tipo==BASE?detalle.getPaxBase():detalle.getPaxAux());
		isVolado = upfront?false:( tipo==META?detalle.isVolado():(tipo==BASE?detalle.isVoladoBase():detalle.isVoladoEmitidoAux()));
		isVoladoEmitido = upfront?false:(tipo==META?detalle.isVoladoEmitido():(tipo==BASE?detalle.isVoladoEmitidoBase():detalle.isVoladoEmitidoAux()));
		isTarifaBruta = upfront?false:(tipo==META?detalle.isTarifaBruta():(tipo==BASE?detalle.isTarifaBrutaBase():detalle.isTarifaBrutaAux()));
		isMSCantidad = upfront?false:(tipo==META?detalle.isMSCantidad():(tipo==BASE?detalle.isMSCantidadBase():detalle.isMSCantidadAux()));
		isTarifaNetaSoloIda = upfront?false:(tipo==META?false:(tipo==BASE?detalle.isTarifaNetaSoloIda():false));
		isPax = upfront?false:(tipo==META?detalle.isPax():(tipo==BASE?detalle.isPaxBase():detalle.isPaxAux()));
		isTarifa = upfront?true:(tipo==META?detalle.isTarifa():(tipo==BASE?detalle.isTarifaBase():detalle.isTarifaAux()));
		isPorcentajeSobreElTotal = upfront?false:(tipo==META?detalle.isPorcentajeSobreElTotal():(tipo==BASE?detalle.isPorcentajeSobreElTotalBase():detalle.isPorcentajeSobreElTotalAux()));
		isPorcentajeSoloIdaSobreElTotal = upfront?false:(tipo==META?detalle.isPorcentajeSoloIdaSobreElTotal():(tipo==BASE?detalle.isPorcentajeSoloIdaSobreElTotalBase():detalle.isPorcentajeSoloIdaSobreElTotalAux()));
		isPorcentajeSobreExcluidos = upfront?false:(tipo==META?detalle.isPorcentajeSobreExcluidos():(tipo==BASE?detalle.isPorcentajeSobreExcluidosBase():detalle.isPorcentajeSobreExcluidosAux()));
		isPorcentajeSobreGDSIncluidos = upfront?false:(tipo==META?detalle.isPorcentajeSobreGDSIncluidos():(tipo==BASE?detalle.isPorcentajeSobreGDSIncluidosBase():detalle.isPorcentajeSobreGDSIncluidosAux()));
		hasOrigenContinente = tipo==META?detalle.hasOrigenContinente():(tipo==BASE?detalle.hasOrigenContinenteBase():detalle.hasOrigenContinenteAux());
		hasOrigenZona = tipo==META?detalle.hasOrigenZona():(tipo==BASE?detalle.hasOrigenZonaBase():detalle.hasOrigenZonaAux());
		hasDestinoContinente = tipo==META?detalle.hasDestinoContinente():(tipo==BASE?detalle.hasDestinoContinenteBase():detalle.hasDestinoContinenteAux());
		hasDestinoZona = tipo==META?detalle.hasDestinoZona():(tipo==BASE?detalle.hasDestinoZonaBase():detalle.hasDestinoZonaAux());
		hasDestinoPais = tipo==META?detalle.hasDestinoPais():(tipo==BASE?detalle.hasDestinoPaisBase():detalle.hasDestinoPaisAux());
		hasOrigenPais = tipo==META?detalle.hasOrigenPais():(tipo==BASE?detalle.hasOrigenPaisBase():detalle.hasOrigenPaisAux());
		isViceversa = tipo==META?detalle.isViceversa():(tipo==BASE?detalle.isViceversaBase():detalle.isViceversaAux());
		hasPrimera = tipo==META?detalle.hasPrimera():(tipo==BASE?detalle.hasPrimeraBase():detalle.hasPrimeraAux());
		isPrimera = tipo==META?detalle.isPrimera():(tipo==BASE?detalle.isPrimeraBase():detalle.isPrimeraAux());
		isNoPrimera = tipo==META?detalle.isNoPrimera():(tipo==BASE?detalle.isNoPrimeraBase():detalle.isNoPrimeraAux());
		hasAndOr = tipo==META?detalle.hasAndOr():(tipo==BASE?detalle.hasAndOrBase():detalle.hasAndOrAux());
		isAnd = tipo==META?detalle.isAnd():(tipo==BASE?detalle.isAndBase():detalle.isAndAux());
		isOr = tipo==META?detalle.isOr():(tipo==BASE?detalle.isOrBase():detalle.isOrAux());
		hasTipoViajes = tipo==META?detalle.hasTipoViaje():false;
		isOpenJaw = tipo==META?detalle.isOpenJaws():false;
		isRoundTrip = tipo==META?detalle.isNoPrimera():false;
		hasMultidestino = tipo==META?detalle.hasMultidestino():false;
		isMore4 = tipo==META?detalle.isMore4Destinos():false;
		isLess4 = tipo==META?detalle.isLess4Destinos():false;
		hasCambio = tipo==META?detalle.hasCambio():false;
		isPosteriorDespegue = tipo==META?detalle.isPostDespegue():false;
		isAntesDespegue = tipo==META?detalle.isPreDespegue():false;
		hasStopover = tipo==META?detalle.isStopOver():false;
		isStopover = tipo==META?detalle.isNoStopOver():false;
		isNoStopover = tipo==META?detalle.isPreDespegue():false;
		hasVueltaMundo = tipo==META?detalle.hasVueltaMundo():false;
		isVueltaMundo = tipo==META?detalle.isVueltaMundo():false;
		isNoVueltaMundo = tipo==META?detalle.isNoVueltaMundo():false;
		hasInterlineal = tipo==META?detalle.hasInterlineal():(tipo==BASE?detalle.hasInterlinealBase():detalle.hasInterlinealAux());
		isSoloUna = tipo==META?detalle.isSoloUna():(tipo==BASE?detalle.isSoloUnaBase():detalle.isSoloUnaAux());
		isInterlineal = tipo==META?detalle.isInterlineal():(tipo==BASE?detalle.isInterlinealBase():detalle.isInterlinealAux());
		isOmitirExchange = tipo==META?detalle.isExchange():(tipo==BASE?detalle.isExchangeBase():detalle.isExchangeAux());
		isMSTarifa = tipo==META?detalle.isMSTarifa():(tipo==BASE?detalle.isMSTarifaBase():detalle.isMSTarifaAux());
		hasTourCodeExcluded = tipo==META?detalle.hasTourCodeExcluded():(tipo==BASE?detalle.hasTourCodeExcludedBase():detalle.hasTourCodeExcludedAux());
		getTourcodeExcludes = tipo==META?detalle.getTourcodeExcludes():(tipo==BASE?detalle.getTourcodeExcludesBase():detalle.getTourcodeExcludesAux());
		hasTourCodeIncluded = tipo==META?detalle.hasTourCodeIncluded():(tipo==BASE?detalle.hasTourCodeIncludedBase():detalle.hasTourCodeIncludedAux());
		getTourcodeIncluded = tipo==META?detalle.getTourcodeIncludes():(tipo==BASE?detalle.getTourcodeIncludedBase():detalle.getTourcodeIncludedAux());
		hasTipoPasajeroExcluded = tipo==META?detalle.hasTipoPasajeroExcluded():(tipo==BASE?detalle.hasTipoPasajeroExcludedBase():detalle.hasTipoPasajeroExcludedAux());
		getTipoPasajeroExcludes = tipo==META?detalle.getTipoPasajeroExcludes():(tipo==BASE?detalle.getTipoPasajeroExcludesBase():detalle.getTipoPasajeroExcludesAux());
		hasTipoPasajeroIncluded = tipo==META?detalle.hasTipoPasajeroIncluded():(tipo==BASE?detalle.hasTipoPasajeroIncludedBase():detalle.hasTipoPasajeroIncludedAux());
		getTipoPasajeroIncluded = tipo==META?detalle.getTipoPasajeroIncludes():(tipo==BASE?detalle.getTipoPasajeroIncludedBase():detalle.getTipoPasajeroIncludedAux());
		hasFareBasicExcluded = tipo==META?detalle.hasFareBasicExcluded():(tipo==BASE?detalle.hasFareBasicExcludedBase():detalle.hasFareBasicExcludedAux());
		getFareBasicExcludes = tipo==META?detalle.getFareBasicExcludes():(tipo==BASE?detalle.getFareBasicExcludesBase():detalle.getFareBasicExcludesAux());
		hasFareBasicIncluded = tipo==META?detalle.hasFareBasicIncluded():(tipo==BASE?detalle.hasFareBasicIncludedBase():detalle.hasFareBasicIncludedAux());
		getFareBasicIncluded = tipo==META?detalle.getFareBasicIncludes():(tipo==BASE?detalle.getFareBasicIncludedBase():detalle.getFareBasicIncludedAux());
		hasGDSIncluded = tipo==META?detalle.hasGDS():(tipo==BASE?detalle.hasGDSBase():detalle.hasGDSAux());
		hasGDSExcluded = tipo==META?detalle.hasNoGDS():(tipo==BASE?detalle.hasNoGDSBase():detalle.hasNoGDSAux());
		getGDSIncluded = tipo==META?detalle.getGDS():(tipo==BASE?detalle.getGDSBase():detalle.getGDSAux());
		getGDSExcludes = tipo==META?detalle.getNoGDS():(tipo==BASE?detalle.getNoGDSBase():detalle.getNoGDSAux());

		hasClasesExcluded = tipo==META?detalle.hasClasesExcluded():(tipo==BASE?detalle.hasClasesExcludedBase():detalle.hasClasesExcludedAux());
		getClasesExcludes = tipo==META?detalle.getClasesExcludes():(tipo==BASE?detalle.getClasesExcludesBase():detalle.getClasesExcludesAux());
		hasClasesIncluded = tipo==META?detalle.hasClasesIncluded():(tipo==BASE?detalle.hasClasesIncludedBase():detalle.hasClasesIncludedAux());
		getClasesIncluded = tipo==META?detalle.getClasesIncluded():(tipo==BASE?detalle.getClasesIncludedBase():detalle.getClasesIncludedAux());
		hasMercado = tipo==META?detalle.hasMercado():(tipo==BASE?detalle.hasMercadoBase():detalle.hasMercadoAux());
		getMercado = tipo==META?detalle.getMercados():(tipo==BASE?detalle.getMercadosBase():detalle.getMercadosAux());
		hasNoMercado = tipo==META?detalle.hasNoMercado():(tipo==BASE?detalle.hasNoMercadoBase():detalle.hasNoMercadoAux());
		getNoMercado = tipo==META?detalle.getNoMercados():(tipo==BASE?detalle.getNoMercadosBase():detalle.getNoMercadosAux());
		hasRutas= tipo==META?detalle.hasRutas():(tipo==BASE?detalle.hasRutasBase():detalle.hasRutasAux());
		hasNoRutas= tipo==META?detalle.hasNoRutas():false;
		getRutas = tipo==META?detalle.getRutas():(tipo==BASE?detalle.getRutasBase():detalle.getRutasAux());
		getNoRutas = tipo==META?detalle.getNoRutas():"";
		hasVuelos= tipo==META?detalle.hasVuelos():(tipo==BASE?detalle.hasVuelosBase():detalle.hasVuelosAux());
		getVuelos = tipo==META?detalle.getVuelos():(tipo==BASE?detalle.getVuelosBase():detalle.getVuelosAux());
		hasNoVuelos= tipo==META?detalle.hasNoVuelos():(tipo==BASE?detalle.hasNoVuelosBase():detalle.hasNoVuelosAux());
		getNoVuelos = tipo==META?detalle.getNoVuelos():(tipo==BASE?detalle.getNoVuelosBase():detalle.getNoVuelosAux());
		hasDomesticInternacional = tipo==META?detalle.hasDomesticInternacional():(tipo==BASE?detalle.hasDomesticInternacionalBase():detalle.hasDomesticInternacionalAux());
		isDomestic = tipo==META?detalle.isDomestic():(tipo==BASE?detalle.isDomesticBase():detalle.isDomesticAux());
		isInternational = tipo==META?detalle.isInternational():(tipo==BASE?detalle.isInternationalBase():detalle.isInternationalAux());
		hasOrigenAeropuerto = tipo==META?detalle.hasOrigenAeropuerto():(tipo==BASE?detalle.hasOrigenAeropuertoBase():detalle.hasOrigenAeropuertoAux());
		hasDestinoAeropuerto = tipo==META?detalle.hasDestinoAeropuerto():(tipo==BASE?detalle.hasDestinoAeropuertoBase():detalle.hasDestinoAeropuertoAux());
		getOrigenAeropuerto = tipo==META?detalle.getOrigenAeropuerto():(tipo==BASE?detalle.getOrigenAeropuertoBase():detalle.getOrigenAeropuertoAux());
		getOrigenContinente = tipo==META?detalle.getOrigenContinente():(tipo==BASE?detalle.getOrigenContinenteBase():detalle.getOrigenContinenteAux());
		getOrigenZona = tipo==META?detalle.getOrigenZona():(tipo==BASE?detalle.getOrigenZonaBase():detalle.getOrigenZonaAux());
		getOrigenPais = tipo==META?detalle.getOrigenPais():(tipo==BASE?detalle.getOrigenPaisBase():detalle.getOrigenPaisAux());
		getDestinoAeropuerto = tipo==META?detalle.getDestinoAeropuerto():(tipo==BASE?detalle.getDestinoAeropuertoBase():detalle.getDestinoAeropuertoAux());
		getDestinoContinente = tipo==META?detalle.getDestinoContinente():(tipo==BASE?detalle.getDestinoContinenteBase():detalle.getDestinoContinenteAux());
		getDestinoZona = tipo==META?detalle.getDestinoZona():(tipo==BASE?detalle.getDestinoZonaBase():detalle.getDestinoZonaAux());
		getDestinoPais = tipo==META?detalle.getDestinoPais():(tipo==BASE?detalle.getDestinoPaisBase():detalle.getDestinoPaisAux());
		isOrigenNegado = tipo==META?detalle.isOrigenNegado():(tipo==BASE?detalle.isOrigenNegadoBase():detalle.isOrigenNegadoAux());
		isDestinoNegado = tipo==META?detalle.isDestinoNegado():(tipo==BASE?detalle.isDestinoNegadoBase():detalle.isDestinoNegadoAux());
		getOrigenAeropuerto=completeRutas(getOrigenAeropuerto);
		getDestinoAeropuerto=completeRutas(getDestinoAeropuerto);
		
	}	
	
	public JList<String> completeRutas(JList<String> rutas) throws Exception {
		JList<String> newList = JCollectionFactory.createList();
		JIterator<String> it = rutas.getIterator();
		while (it.hasMoreElements()) {
			String s = it.nextElement();
			newList.addElement(s);
			JRecords<BizAirport> checkAirports = new JRecords<BizAirport>(BizAirport.class);
			checkAirports.addFilter("iata_area", s);
			JIterator<BizAirport> ita = checkAirports.getStaticIterator();
			while(ita.hasMoreElements()) {
				BizAirport air = ita.nextElement();
				if (!newList.containsElement(air.getCode()))
					newList.addElement(air.getCode());
			}
		}
		return newList;
	}
	public String getAreaMetro(String airport) throws Exception {
		BizAirport air = new BizAirport();
		air.dontThrowException(true);
		if (!air.Read(airport)) return airport;
		if (air.getIataArea().equals("")) return airport;
		return air.getIataArea();
	}
	public boolean isAreaMetro(String metro) throws Exception {
		BizAirport air = new BizAirport();
		air.dontThrowException(true);
		air.addFilter("iata_area", metro);
		return (air.read());
	}
	public void autogenerar() throws Exception {
		if (generado) return;
		generado=true;
		calcule();
		autogenerarReservas();

		if (isBooking) {
			autogenerarBooking();
		} else {
			autogenerarTickets();
		}
	
	}
	public boolean hasRestriccionesDeRuta() throws Exception {
		return hasMercado || (
				(hasDestinoContinente||hasDestinoZona||hasDestinoAeropuerto||hasDestinoPais||
				hasOrigenContinente||hasOrigenZona||hasOrigenAeropuerto||hasOrigenPais)&&!isViceversa
				);
	}
	

	private boolean isRequiredAnd() throws Exception {
		boolean hasOrigen =  hasOrigenAeropuerto || hasOrigenContinente || hasOrigenPais || hasOrigenZona;
		boolean hasDestino =  hasDestinoAeropuerto || hasDestinoContinente || hasDestinoPais || hasDestinoZona;
		if (hasOrigen && hasDestino) return isOrigenNegado && isDestinoNegado;
		if (hasOrigen) return isOrigenNegado;
		if (hasDestino) return isDestinoNegado;
		return false;
	}
	
	public String getGroupByFecha() throws Exception {
    // Determinar campo de fecha base como en getFilterFecha
    String fieldDiario;

    if (isBooking) {
        fieldDiario = fechaFijaEmision ? "TUR_PNR_BOOKING.fecha_emision" : "TUR_PNR_BOOKING.FechaDespegue";
    } else {
        fieldDiario = fechaFijaEmision ? "TUR_PNR_BOLETO.creation_date" : "TUR_PNR_BOLETO.departure_date";
    }

    // Obtener fechas
    Date fDesde = detalle.getFDesde();
    Date fHasta = detalle.getFHasta();

    // Si hay fechas, calculamos la diferencia para elegir agrupación adecuada
    if (fDesde != null && fHasta != null) {
        long diffMillis = fHasta.getTime() - fDesde.getTime();
        long dias = diffMillis / (1000 * 60 * 60 * 24);

        if (dias < 32) {
          // Agrupación mensual
          return "DATE_TRUNC('month', " + fieldDiario + ")";
      } else if (dias < 63) {
          // Agrupación bimestral
          return "MAKE_DATE(EXTRACT(YEAR FROM " + fieldDiario + ")::int, ((FLOOR((EXTRACT(MONTH FROM " + fieldDiario + ") - 1) / 2) * 2 + 1))::int, 1)";
      } else if (dias < 94) {
          // Agrupación trimestral
          return "DATE_TRUNC('quarter', " + fieldDiario + ")";
      } else if (dias < 187) {
          // Agrupación semestral
          return "MAKE_DATE(EXTRACT(YEAR FROM " + fieldDiario + ")::int, ((FLOOR((EXTRACT(MONTH FROM " + fieldDiario + ") - 1) / 6) * 6 + 1))::int, 1)";
      } else {
          // Agrupación anual
          return "DATE_TRUNC('year', " + fieldDiario + ")";
      }


    }

    // Si no hay fechas, agrupamos por año como predeterminado
    return "DATE_TRUNC('year', " + fieldDiario + ")";
}
	public String getFilterFechaFijo() throws Exception {
		Calendar calDesde = Calendar.getInstance();
		calDesde.setTime(detalle.getFDesde());
		Calendar calHasta = Calendar.getInstance();
		calHasta.setTime(detalle.getFHasta());

		Calendar dif = Calendar.getInstance();
		dif.setTimeInMillis(calHasta.getTime().getTime() - calDesde.getTime().getTime());
		int dias = dif.get(Calendar.DAY_OF_YEAR);
		String strFechasB="";
		String strFechasH="";
		String sqlMeses="";
		if (getMeses !=null && !getMeses.isEmpty()) {
			if (isBooking) {
				if (fechaFijaEmision) {
					fieldDiario = "TUR_PNR_BOOKING.fecha_emision ";
					} else {
					fieldDiario = "TUR_PNR_BOOKING.FechaDespegue ";
				}

				// Generar SQL a partir de getMeses en formato uuuuMM
				if (getMeses != null && !getMeses.isEmpty()) {
					JIterator<String> itm = getMeses.getIterator();
					while (itm.hasMoreElements()) {
						String mesAnio = itm.nextElement(); // Ejemplo: "202401"
						String anio = mesAnio.substring(0, 4); // Extrae el año "2024"
						String mesNumerico = mesAnio.substring(4, 6); // Extrae el mes "01"

						sqlMeses += (sqlMeses.equals("") ? "" : " OR ") + " (to_char(" + fieldDiario + ", 'YYYY') = '" + anio + "' " + " AND to_char(" + fieldDiario + ", 'MM') = '" + mesNumerico + "') ";
					}
					strFechasB +=  (strFechasB.equals("")?"":" AND")+" (" + sqlMeses + ")";
				}
			} else {
				if (fechaFijaEmision) {
					fieldDiario = "TUR_PNR_BOLETO.creation_date ";
					} else {
					fieldDiario = "TUR_PNR_BOLETO.departure_date ";
				}

				// Generar SQL a partir de getMeses en formato uuuuMM
				if (getMeses != null && !getMeses.isEmpty()) {
					JIterator<String> itm = getMeses.getIterator();
					while (itm.hasMoreElements()) {
						String mesAnio = itm.nextElement(); // Ejemplo: "202401"
						String anio = mesAnio.substring(0, 4); // Extrae el año "2024"
						String mesNumerico = mesAnio.substring(4, 6); // Extrae el mes "01"

						sqlMeses += (sqlMeses.equals("") ? "" : " OR ") + " (to_char(" + fieldDiario + ", 'YYYY') = '" + anio + "' " + " AND to_char(" + fieldDiario + ", 'MM') = '" + mesNumerico + "') ";
					}
					strFechasB +=  (strFechasB.equals("")?"":" AND")+" (" + sqlMeses + ")";
				}
			}

		} else if (fechaFija && (upfront || tipo!=META)) {
				if (isBooking) {
					if (fechaFijaEmision) {
						fieldDiario = "TUR_PNR_BOOKING.fecha_emision ";
						} else {
						fieldDiario = "TUR_PNR_BOOKING.FechaDespegue ";
					}
					if (fechaEmisionDesde != null)
						strFechasB +=  (strFechasB.equals("")?"":" AND")+" TUR_PNR_BOOKING.fecha_emision>= '" + JDateTools.DateToString(fechaEmisionDesde) + "' ";
					if (fechaEmisionHasta != null)
						strFechasB +=  (strFechasB.equals("")?"":" AND")+" TUR_PNR_BOOKING.fecha_emision<= '" + JDateTools.DateToString(fechaEmisionHasta) + "' ";
					if (fechaVueloDesde != null)
						strFechasB +=  (strFechasB.equals("")?"":" AND")+" TUR_PNR_BOOKING.FechaDespegue>= '" + JDateTools.DateToString(fechaVueloDesde) + "' ";
					if (fechaVueloHasta != null)
						strFechasB +=  (strFechasB.equals("")?"":" AND")+" TUR_PNR_BOOKING.FechaDespegue<= '" + JDateTools.DateToString(fechaVueloHasta) + "' ";
					if (fechaBlockoutDesde != null && fechaBlockoutHasta != null) {
						strFechasB +=  (strFechasB.equals("")?"":" AND")+" ( TUR_PNR_BOOKING.FechaDespegue< '" + JDateTools.DateToString(fechaBlockoutDesde) + "' ";
						strFechasB += " OR TUR_PNR_BOOKING.FechaDespegue> '" + JDateTools.DateToString(fechaBlockoutHasta) + "') ";
					} else if (fechaBlockoutDesde != null)
						strFechasB +=  (strFechasB.equals("")?"":" AND")+" TUR_PNR_BOOKING.FechaDespegue< '" + JDateTools.DateToString(fechaBlockoutDesde) + "' ";
					else if (fechaBlockoutHasta != null)
						strFechasB +=  (strFechasB.equals("")?"":" AND")+" TUR_PNR_BOOKING.FechaDespegue> '" + JDateTools.DateToString(fechaBlockoutHasta) + "' ";

					if (getSepFecha>0) {
						strFechasB +=  (strFechasB.equals("")?"":" AND")+" extract(day from TUR_PNR_BOOKING.FechaDespegue-TUR_PNR_BOOKING.fecha_emision) > " + getSepFecha + " ";

					}
				} else {
					if (fechaFijaEmision) {
						fieldDiario = "TUR_PNR_BOLETO.creation_date ";
						} else {
						fieldDiario = "TUR_PNR_BOLETO.departure_date ";
					}

					if (fechaEmisionDesde != null)
						strFechasB += (strFechasB.equals("")?"":" AND")+" TUR_PNR_BOLETO.creation_date>= '" + JDateTools.DateToString(fechaEmisionDesde) + "' ";
					if (fechaEmisionHasta != null)
						strFechasB +=  (strFechasB.equals("")?"":" AND")+" TUR_PNR_BOLETO.creation_date<= '" + JDateTools.DateToString(fechaEmisionHasta) + "' ";
					if (fechaVueloDesde != null)
						strFechasB +=  (strFechasB.equals("")?"":" AND")+" TUR_PNR_BOLETO.departure_date>= '" + JDateTools.DateToString(fechaVueloDesde) + "' ";
					if (fechaVueloHasta != null)
						strFechasB +=  (strFechasB.equals("")?"":" AND")+" TUR_PNR_BOLETO.departure_date<= '" + JDateTools.DateToString(fechaVueloHasta) + "' ";
					if (fechaBlockoutDesde != null && fechaBlockoutHasta != null) {
						strFechasB +=  (strFechasB.equals("")?"":" AND")+" (TUR_PNR_BOLETO.departure_date< '" + JDateTools.DateToString(fechaBlockoutDesde) + "' ";
						strFechasB += " OR TUR_PNR_BOLETO.departure_date> '" + JDateTools.DateToString(fechaBlockoutHasta) + "') ";
					} else if (fechaBlockoutDesde != null)
						strFechasB +=  (strFechasB.equals("")?"":" AND")+" TUR_PNR_BOLETO.departure_date< '" + JDateTools.DateToString(fechaBlockoutDesde) + "' ";
					else if (fechaBlockoutHasta != null)
						strFechasB +=  (strFechasB.equals("")?"":" AND")+" TUR_PNR_BOLETO.departure_date> '" + JDateTools.DateToString(fechaBlockoutHasta) + "' ";
					if (getSepFecha>0) {
						strFechasB +=  (strFechasB.equals("")?"":" AND")+" extract(day from TUR_PNR_BOLETO.departure_date-TUR_PNR_BOLETO.creation_date) > " + getSepFecha + " ";
					}
		
				}
	
		}
		
		String comment = (isVolado?"ES_VOLADO":"")+" "+(isBooking?"ES_BOOKING":"");
		String strFecha=strFechasB;
		if (strFecha.trim().isEmpty())
			return " TRUE ";
		
		return " (true and /*7*/"+strFecha+ "/*7*/) ::FECHA:: ::DK:: "+"/* "+comment+" */";
	}

	public String getFilterFecha(boolean historico) throws Exception {
		Calendar calDesde = Calendar.getInstance();
		calDesde.setTime(detalle.getFDesde());
		Calendar calHasta = Calendar.getInstance();
		calHasta.setTime(detalle.getFHasta());

		Calendar dif = Calendar.getInstance();
		dif.setTimeInMillis(calHasta.getTime().getTime() - calDesde.getTime().getTime());
		int dias = dif.get(Calendar.DAY_OF_YEAR);
		String strFechasB="";
		String strFechasH="";
		String sqlMeses="";
		if (getMeses !=null && !getMeses.isEmpty()) {
			if (isBooking) {
				if (fechaFijaEmision) {
					fieldDiario = "TUR_PNR_BOOKING.fecha_emision ";
					fieldHistorico1 = "date_part('year', TUR_PNR_BOOKING.fecha_emision)";
					fieldHistorico2 = "to_char(TUR_PNR_BOOKING.fecha_emision, 'MM-DD')";
				} else {
					fieldDiario = "TUR_PNR_BOOKING.FechaDespegue ";
					fieldHistorico1 = "date_part('year', TUR_PNR_BOOKING.FechaDespegue)";
					fieldHistorico2 = "to_char(TUR_PNR_BOOKING.FechaDespegue, 'MM-DD')";
				}

				// Generar SQL a partir de getMeses en formato uuuuMM
				if (getMeses != null && !getMeses.isEmpty()) {
					JIterator<String> itm = getMeses.getIterator();
					while (itm.hasMoreElements()) {
						String mesAnio = itm.nextElement(); // Ejemplo: "202401"
						String anio = mesAnio.substring(0, 4); // Extrae el año "2024"
						String mesNumerico = mesAnio.substring(4, 6); // Extrae el mes "01"

						sqlMeses += (sqlMeses.equals("") ? "" : " OR ") + " (to_char(" + fieldDiario + ", 'YYYY') = '" + anio + "' " + " AND to_char(" + fieldDiario + ", 'MM') = '" + mesNumerico + "') ";
					}
					strFechasB +=  (strFechasB.equals("")?"":" AND")+" (" + sqlMeses + ")";
				}
			} else {
				if (fechaFijaEmision) {
					fieldDiario = "TUR_PNR_BOLETO.creation_date ";
					fieldHistorico1 = "date_part('year', TUR_PNR_BOLETO.creation_date)";
					fieldHistorico2 = "to_char(TUR_PNR_BOLETO.creation_date, 'MM-DD')";
				} else {
					fieldDiario = "TUR_PNR_BOLETO.departure_date ";
					fieldHistorico1 = "date_part('year', TUR_PNR_BOLETO.departure_date)";
					fieldHistorico2 = "to_char(TUR_PNR_BOLETO.departure_date, 'MM-DD')";
				}

				// Generar SQL a partir de getMeses en formato uuuuMM
				if (getMeses != null && !getMeses.isEmpty()) {
					JIterator<String> itm = getMeses.getIterator();
					while (itm.hasMoreElements()) {
						String mesAnio = itm.nextElement(); // Ejemplo: "202401"
						String anio = mesAnio.substring(0, 4); // Extrae el año "2024"
						String mesNumerico = mesAnio.substring(4, 6); // Extrae el mes "01"

						sqlMeses += (sqlMeses.equals("") ? "" : " OR ") + " (to_char(" + fieldDiario + ", 'YYYY') = '" + anio + "' " + " AND to_char(" + fieldDiario + ", 'MM') = '" + mesNumerico + "') ";
					}
					strFechasB +=  (strFechasB.equals("")?"":" AND")+" (" + sqlMeses + ")";
				}
			}

		} else if (fechaFija && (upfront || tipo!=META)) {
				if (isBooking) {
					if (fechaFijaEmision) {
						fieldDiario = "TUR_PNR_BOOKING.fecha_emision ";
						fieldHistorico1 = "date_part('year',TUR_PNR_BOOKING.fecha_emision)";
						fieldHistorico2 = "to_char(TUR_PNR_BOOKING.fecha_emision, 'MM-DD')";
					} else {
						fieldDiario = "TUR_PNR_BOOKING.FechaDespegue ";
						fieldHistorico1 = "date_part('year',TUR_PNR_BOOKING.FechaDespegue)";
						fieldHistorico2 = "to_char(TUR_PNR_BOOKING.FechaDespegue, 'MM-DD')";
					}
					if (fechaEmisionDesde != null)
						strFechasB +=  (strFechasB.equals("")?"":" AND")+" TUR_PNR_BOOKING.fecha_emision>= '" + JDateTools.DateToString(fechaEmisionDesde) + "' ";
					if (fechaEmisionHasta != null)
						strFechasB +=  (strFechasB.equals("")?"":" AND")+" TUR_PNR_BOOKING.fecha_emision<= '" + JDateTools.DateToString(fechaEmisionHasta) + "' ";
					if (fechaVueloDesde != null)
						strFechasB +=  (strFechasB.equals("")?"":" AND")+" TUR_PNR_BOOKING.FechaDespegue>= '" + JDateTools.DateToString(fechaVueloDesde) + "' ";
					if (fechaVueloHasta != null)
						strFechasB +=  (strFechasB.equals("")?"":" AND")+" TUR_PNR_BOOKING.FechaDespegue<= '" + JDateTools.DateToString(fechaVueloHasta) + "' ";
					if (fechaBlockoutDesde != null && fechaBlockoutHasta != null) {
						strFechasB +=  (strFechasB.equals("")?"":" AND")+" ( TUR_PNR_BOOKING.FechaDespegue< '" + JDateTools.DateToString(fechaBlockoutDesde) + "' ";
						strFechasB += " OR TUR_PNR_BOOKING.FechaDespegue> '" + JDateTools.DateToString(fechaBlockoutHasta) + "') ";
					} else if (fechaBlockoutDesde != null)
						strFechasB +=  (strFechasB.equals("")?"":" AND")+" TUR_PNR_BOOKING.FechaDespegue< '" + JDateTools.DateToString(fechaBlockoutDesde) + "' ";
					else if (fechaBlockoutHasta != null)
						strFechasB +=  (strFechasB.equals("")?"":" AND")+" TUR_PNR_BOOKING.FechaDespegue> '" + JDateTools.DateToString(fechaBlockoutHasta) + "' ";

					if (getSepFecha>0) {
						strFechasB +=  (strFechasB.equals("")?"":" AND")+" extract(day from TUR_PNR_BOOKING.FechaDespegue-TUR_PNR_BOOKING.fecha_emision) > " + getSepFecha + " ";

					}
				} else {
					if (fechaFijaEmision) {
						fieldDiario = "TUR_PNR_BOLETO.creation_date ";
						fieldHistorico1 = "date_part('year',TUR_PNR_BOLETO.creation_date)";
						fieldHistorico2 = "to_char(TUR_PNR_BOLETO.creation_date, 'MM-DD')";
					} else {
						fieldDiario = "TUR_PNR_BOLETO.departure_date ";
						fieldHistorico1 = "date_part('year',TUR_PNR_BOLETO.departure_date)";
						fieldHistorico2 = "to_char(TUR_PNR_BOLETO.departure_date, 'MM-DD')";
					}

					if (fechaEmisionDesde != null)
						strFechasB += (strFechasB.equals("")?"":" AND")+" TUR_PNR_BOLETO.creation_date>= '" + JDateTools.DateToString(fechaEmisionDesde) + "' ";
					if (fechaEmisionHasta != null)
						strFechasB +=  (strFechasB.equals("")?"":" AND")+" TUR_PNR_BOLETO.creation_date<= '" + JDateTools.DateToString(fechaEmisionHasta) + "' ";
					if (fechaVueloDesde != null)
						strFechasB +=  (strFechasB.equals("")?"":" AND")+" TUR_PNR_BOLETO.departure_date>= '" + JDateTools.DateToString(fechaVueloDesde) + "' ";
					if (fechaVueloHasta != null)
						strFechasB +=  (strFechasB.equals("")?"":" AND")+" TUR_PNR_BOLETO.departure_date<= '" + JDateTools.DateToString(fechaVueloHasta) + "' ";
					if (fechaBlockoutDesde != null && fechaBlockoutHasta != null) {
						strFechasB +=  (strFechasB.equals("")?"":" AND")+" (TUR_PNR_BOLETO.departure_date< '" + JDateTools.DateToString(fechaBlockoutDesde) + "' ";
						strFechasB += " OR TUR_PNR_BOLETO.departure_date> '" + JDateTools.DateToString(fechaBlockoutHasta) + "') ";
					} else if (fechaBlockoutDesde != null)
						strFechasB +=  (strFechasB.equals("")?"":" AND")+" TUR_PNR_BOLETO.departure_date< '" + JDateTools.DateToString(fechaBlockoutDesde) + "' ";
					else if (fechaBlockoutHasta != null)
						strFechasB +=  (strFechasB.equals("")?"":" AND")+" TUR_PNR_BOLETO.departure_date> '" + JDateTools.DateToString(fechaBlockoutHasta) + "' ";
					if (getSepFecha>0) {
						strFechasB +=  (strFechasB.equals("")?"":" AND")+" extract(day from TUR_PNR_BOLETO.departure_date-TUR_PNR_BOLETO.creation_date) > " + getSepFecha + " ";
					}
		
				}
	
		}
		{
			if (isBooking) {
				if (isVolado||isVoladoEmitido) {
					if (fechaVueloDesde==null && fechaVueloHasta==null) {
						fieldDiario = "TUR_PNR_BOOKING.FechaDespegue ";
						fieldHistorico1 = "date_part('year',TUR_PNR_BOOKING.FechaDespegue)";
						fieldHistorico2 = "to_char(TUR_PNR_BOOKING.FechaDespegue, 'MM-DD')";
						strFechasH =  (strFechasH.equals("")?"":" AND")+" date_part('year',TUR_PNR_BOOKING.FechaDespegue) = date_part('year',now())";
						if (dias < 32) {// mensual
							strFechasB += (strFechasB.equals("")?"":" AND")+" date_part('month'::text,now())=date_part('month'::text,TUR_PNR_BOOKING.FechaDespegue)";
						} else if (dias < 63) {// bimestral
							strFechasB += (strFechasB.equals("")?"":" AND")+" (floor(((extract (month from TUR_PNR_BOOKING.FechaDespegue))-1 ) / 2) +1 = floor(((extract (month from now()))-1 ) / 2) +1)";
						} else if (dias < 94) {// trimestral
							strFechasB += (strFechasB.equals("")?"":" AND")+"  (floor(((extract (month from TUR_PNR_BOOKING.FechaDespegue))-1 ) / 3) +1 = floor(((extract (month from now()))-1 ) / 3) +1) ";
						} else if (dias < 125) {// cuatrimestral
							strFechasB += (strFechasB.equals("")?"":" AND")+"  (extract(QUARTER  from  TUR_PNR_BOOKING.FechaDespegue)  = extract(QUARTER  from now())) ";
						} else if (dias < 187) {// semestral
							strFechasB += (strFechasB.equals("")?"":" AND")+"  (floor(((extract (month from  TUR_PNR_BOOKING.FechaDespegue))-1 ) / 6) +1 = floor(((extract (month from now()))-1 ) / 6) +1) ";
						}
						
						if (isVoladoEmitido) {
							strFechasH +=  (strFechasH.equals("")?"":" AND")+" date_part('year',TUR_PNR_BOOKING.fecha_emision) = date_part('year',now())";
							if (dias < 32) {// mensual
								strFechasB +=  (strFechasB.equals("")?"":" AND")+" date_part('month'::text,now())=date_part('month'::text,TUR_PNR_BOOKING.fecha_emision)";
							} else if (dias < 63) {// bimestral
								strFechasB +=  (strFechasB.equals("")?"":" AND")+" (floor(((extract (month from TUR_PNR_BOOKING.fecha_emision))-1 ) / 2) +1 = floor(((extract (month from now()))-1 ) / 2) +1)";
							} else if (dias < 94) {// trimestral
								strFechasB +=  (strFechasB.equals("")?"":" AND")+"  (floor(((extract (month from TUR_PNR_BOOKING.fecha_emision))-1 ) / 3) +1 = floor(((extract (month from now()))-1 ) / 3) +1) ";
							} else if (dias < 125) {// cuatrimestral
								strFechasB +=  (strFechasB.equals("")?"":" AND")+"  (extract(QUARTER  from  TUR_PNR_BOOKING.fecha_emision)  = extract(QUARTER  from now())) ";
							} else if (dias < 187) {// semestral
								strFechasB +=  (strFechasB.equals("")?"":" AND")+"  (floor(((extract (month from  TUR_PNR_BOOKING.fecha_emision))-1 ) / 6) +1 = floor(((extract (month from now()))-1 ) / 6) +1) ";
							}
						} 					
					}
	

				} else { //emitido
					if ((upfront && fechaEmisionDesde==null && fechaEmisionHasta==null) ||
							(tipo!=META && fechaEmisionDesde==null && fechaEmisionHasta==null ) ||
							(!upfront && tipo==META)) {
						fieldDiario = "TUR_PNR_BOOKING.fecha_emision ";
						fieldHistorico1 = "date_part('year',TUR_PNR_BOOKING.fecha_emision)";
						fieldHistorico2 = "to_char(TUR_PNR_BOOKING.fecha_emision, 'MM-DD')";
						strFechasH =  (strFechasH.equals("")?"":" AND")+" date_part('year',TUR_PNR_BOOKING.fecha_emision) = date_part('year',now())";
						if (dias < 32) {// mensual
							strFechasB +=  (strFechasB.equals("")?"":" AND")+" date_part('month'::text,now())=date_part('month'::text,TUR_PNR_BOOKING.fecha_emision)";
						} else if (dias < 63) {// bimestral
							strFechasB +=  (strFechasB.equals("")?"":" AND")+" (floor(((extract (month from TUR_PNR_BOOKING.fecha_emision))-1 ) / 2) +1 = floor(((extract (month from now()))-1 ) / 2) +1)";
						} else if (dias < 94) {// trimestral
							strFechasB +=  (strFechasB.equals("")?"":" AND")+"  (floor(((extract (month from TUR_PNR_BOOKING.fecha_emision))-1 ) / 3) +1 = floor(((extract (month from now()))-1 ) / 3) +1) ";
						} else if (dias < 125) {// cuatrimestral
							strFechasB +=  (strFechasB.equals("")?"":" AND")+"  (extract(QUARTER  from  TUR_PNR_BOOKING.fecha_emision)  = extract(QUARTER  from now())) ";
						} else if (dias < 187) {// semestral
							strFechasB +=  (strFechasB.equals("")?"":" AND")+"  (floor(((extract (month from  TUR_PNR_BOOKING.fecha_emision))-1 ) / 6) +1 = floor(((extract (month from now()))-1 ) / 6) +1) ";
						}

					}					
				}

				if (getSepFecha>0) {
					strFechasB +=  (strFechasB.equals("")?"":" AND")+" extract(day from TUR_PNR_BOOKING.FechaDespegue-TUR_PNR_BOOKING.fecha_emision) > " + getSepFecha + " ";
				}
			} else {
				if (isVolado||isVoladoEmitido) {
					if (fechaVueloDesde==null && fechaVueloHasta==null) {
						
						fieldDiario = "TUR_PNR_BOLETO.departure_date ";
						fieldHistorico1 = "date_part('year',TUR_PNR_BOLETO.departure_date)";
						fieldHistorico2 = "to_char(TUR_PNR_BOLETO.departure_date, 'MM-DD')";
						strFechasH =  (strFechasH.equals("")?"":" AND")+" date_part('year',TUR_PNR_BOLETO.departure_date) = date_part('year',now())";
						if (dias < 32) {// mensual
							strFechasB +=  (strFechasB.equals("")?"":" AND")+" date_part('month'::text,now())=date_part('month'::text,TUR_PNR_BOLETO.departure_date)";
						} else if (dias < 63) {// bimestral
							strFechasB +=  (strFechasB.equals("")?"":" AND")+" (floor(((extract (month from TUR_PNR_BOLETO.departure_date))-1 ) / 2) +1 = floor(((extract (month from now()))-1 ) / 2) +1)";
						} else if (dias < 94) {// trimestral
							strFechasB +=  (strFechasB.equals("")?"":" AND")+"  (floor(((extract (month from TUR_PNR_BOLETO.departure_date))-1 ) / 3) +1 = floor(((extract (month from now()))-1 ) / 3) +1) ";
						} else if (dias < 125) {// cuatrimestral
							strFechasB +=  (strFechasB.equals("")?"":" AND")+"  (extract(QUARTER  from  TUR_PNR_BOLETO.departure_date)  = extract(QUARTER  from now())) ";
						} else if (dias < 187) {// semestral
							strFechasB +=  (strFechasB.equals("")?"":" AND")+"  (floor(((extract (month from  TUR_PNR_BOLETO.departure_date))-1 ) / 6) +1 = floor(((extract (month from now()))-1 ) / 6) +1) ";
						}
					
						if (isVoladoEmitido) {
							strFechasH +=  (strFechasH.equals("")?"":" AND")+" date_part('year',TUR_PNR_BOLETO.creation_date) = date_part('year',now())";
							if (dias < 32) {// mensual
								strFechasB +=  (strFechasB.equals("")?"":" AND")+" date_part('month'::text,now())=date_part('month'::text,TUR_PNR_BOLETO.creation_date)";
							} else if (dias < 63) {// bimestral
								strFechasB +=  (strFechasB.equals("")?"":" AND")+" (floor(((extract (month from TUR_PNR_BOLETO.creation_date))-1 ) / 2) +1 = floor(((extract (month from now()))-1 ) / 2) +1)";
							} else if (dias < 94) {// trimestral
								strFechasB +=  (strFechasB.equals("")?"":" AND")+" (floor(((extract (month from TUR_PNR_BOLETO.creation_date))-1 ) / 3) +1 = floor(((extract (month from now()))-1 ) / 3) +1) ";
							} else if (dias < 125) {// cuatrimestral
								strFechasB +=  (strFechasB.equals("")?"":" AND")+" (extract(QUARTER  from  TUR_PNR_BOLETO.creation_date)  = extract(QUARTER  from now())) ";
							} else if (dias < 187) {// semestral
								strFechasB +=  (strFechasB.equals("")?"":" AND")+" (floor(((extract (month from  TUR_PNR_BOLETO.creation_date))-1 ) / 6) +1 = floor(((extract (month from now()))-1 ) / 6) +1) ";
							}
						}
					}
				} else { //emitido
					if (fechaEmisionDesde==null && fechaEmisionHasta==null) {
						fieldDiario = "TUR_PNR_BOLETO.creation_date ";
						fieldHistorico1 = "date_part('year',TUR_PNR_BOLETO.creation_date)";
						fieldHistorico2 = "to_char(TUR_PNR_BOLETO.creation_date, 'MM-DD')";
						strFechasH =  (strFechasH.equals("")?"":" AND")+" date_part('year',TUR_PNR_BOLETO.creation_date) = date_part('year',now())";
						if (dias < 32) {// mensual
							strFechasB +=  (strFechasB.equals("")?"":" AND")+" date_part('month'::text,now())=date_part('month'::text,TUR_PNR_BOLETO.creation_date)";
						} else if (dias < 63) {// bimestral
							strFechasB +=  (strFechasB.equals("")?"":" AND")+" (floor(((extract (month from TUR_PNR_BOLETO.creation_date))-1 ) / 2) +1 = floor(((extract (month from now()))-1 ) / 2) +1)";
						} else if (dias < 94) {// trimestral
							strFechasB +=  (strFechasB.equals("")?"":" AND")+"  (floor(((extract (month from TUR_PNR_BOLETO.creation_date))-1 ) / 3) +1 = floor(((extract (month from now()))-1 ) / 3) +1) ";
						} else if (dias < 125) {// cuatrimestral
							strFechasB +=  (strFechasB.equals("")?"":" AND")+"  (extract(QUARTER  from  TUR_PNR_BOLETO.creation_date)  = extract(QUARTER  from now())) ";
						} else if (dias < 187) {// semestral
							strFechasB +=  (strFechasB.equals("")?"":" AND")+"  (floor(((extract (month from  TUR_PNR_BOLETO.creation_date))-1 ) / 6) +1 = floor(((extract (month from now()))-1 ) / 6) +1) ";
						}
					}
				}
				if (getSepFecha>0) {
					strFechasB +=  (strFechasB.equals("")?"":" AND")+" extract(day from TUR_PNR_BOLETO.departure_date-TUR_PNR_BOLETO.creation_date) > " + getSepFecha + " ";
				}
			}

		}
		String comment = (isVolado?"ES_VOLADO":"")+" "+(isBooking?"ES_BOOKING":"");
		String strFecha=(historico?strFechasB:strFechasH+(strFechasB.equals("")?"":(strFechasH.equals("")?"":" AND ")+strFechasB));
		if (strFecha.trim().isEmpty())
			return " TRUE ";
		
		return " (true and /*7*/"+strFecha+ "/*7*/) ::FECHA:: ::DK:: "+"/* "+comment+" */";
	}
	
	public String getFilterFechaReserva(boolean historico) throws Exception {
		Calendar calDesde = Calendar.getInstance();
		calDesde.setTime(detalle.getFDesde());
		Calendar calHasta = Calendar.getInstance();
		calHasta.setTime(detalle.getFHasta());

		Calendar dif = Calendar.getInstance();
		dif.setTimeInMillis(calHasta.getTime().getTime() - calDesde.getTime().getTime());
		int dias = dif.get(Calendar.DAY_OF_YEAR);
		String strFechasB="";
		String strFechasH="";


		if (isVolado||isVoladoEmitido) {
			strFechasH = (strFechasH.equals("")?"":" AND")+" date_part('year',TUR_PNR_RESERVA.departure_date) = date_part('year',now())";
			if (dias < 32) {// mensual
				strFechasB += (strFechasB.equals("")?"":" AND")+" date_part('month'::text,now())=date_part('month'::text,TUR_PNR_RESERVA.departure_date)";
			} else if (dias < 63) {// bimestral
				strFechasB += (strFechasB.equals("")?"":" AND")+" (floor(((extract (month from TUR_PNR_RESERVA.departure_date))-1 ) / 2) +1 = floor(((extract (month from now()))-1 ) / 2) +1)";
			} else if (dias < 94) {// trimestral
				strFechasB += (strFechasB.equals("")?"":" AND")+"  (floor(((extract (month from TUR_PNR_RESERVA.departure_date))-1 ) / 3) +1 = floor(((extract (month from now()))-1 ) / 3) +1) ";
			} else if (dias < 125) {// cuatrimestral
				strFechasB += (strFechasB.equals("")?"":" AND")+"  (extract(QUARTER  from  TUR_PNR_RESERVA.departure_date)  = extract(QUARTER  from now())) ";
			} else if (dias < 187) {// semestral
				strFechasB += (strFechasB.equals("")?"":" AND")+"  (floor(((extract (month from  TUR_PNR_RESERVA.departure_date))-1 ) / 6) +1 = floor(((extract (month from now()))-1 ) / 6) +1) ";
			}
		
			if (isVoladoEmitido) {
				strFechasH += (strFechasH.equals("")?"":" AND")+" date_part('year',TUR_PNR_RESERVA.creation_date) = date_part('year',now())";
				if (dias < 32) {// mensual
					strFechasB += (strFechasB.equals("")?"":" AND")+" date_part('month'::text,now())=date_part('month'::text,TUR_PNR_RESERVA.creation_date)";
				} else if (dias < 63) {// bimestral
					strFechasB += (strFechasB.equals("")?"":" AND")+" (floor(((extract (month from TUR_PNR_RESERVA.creation_date))-1 ) / 2) +1 = floor(((extract (month from now()))-1 ) / 2) +1)";
				} else if (dias < 94) {// trimestral
					strFechasB += (strFechasB.equals("")?"":" AND")+" (floor(((extract (month from TUR_PNR_RESERVA.creation_date))-1 ) / 3) +1 = floor(((extract (month from now()))-1 ) / 3) +1) ";
				} else if (dias < 125) {// cuatrimestral
					strFechasB += (strFechasB.equals("")?"":" AND")+" (extract(QUARTER  from  TUR_PNR_RESERVA.creation_date)  = extract(QUARTER  from now())) ";
				} else if (dias < 187) {// semestral
					strFechasB += (strFechasB.equals("")?"":" AND")+" (floor(((extract (month from  TUR_PNR_RESERVA.creation_date))-1 ) / 6) +1 = floor(((extract (month from now()))-1 ) / 6) +1) ";
				}
			}
		} else { //emitido
			strFechasH = (strFechasH.equals("")?"":" AND")+" date_part('year',TUR_PNR_RESERVA.creation_date) = date_part('year',now())";
			if (dias < 32) {// mensual
				strFechasB += (strFechasB.equals("")?"":" AND")+" date_part('month'::text,now())=date_part('month'::text,TUR_PNR_RESERVA.creation_date)";
			} else if (dias < 63) {// bimestral
				strFechasB += (strFechasB.equals("")?"":" AND")+" (floor(((extract (month from TUR_PNR_RESERVA.creation_date))-1 ) / 2) +1 = floor(((extract (month from now()))-1 ) / 2) +1)";
			} else if (dias < 94) {// trimestral
				strFechasB += (strFechasB.equals("")?"":" AND")+" (floor(((extract (month from TUR_PNR_RESERVA.creation_date))-1 ) / 3) +1 = floor(((extract (month from now()))-1 ) / 3) +1) ";
			} else if (dias < 125) {// cuatrimestral
				strFechasB += (strFechasB.equals("")?"":" AND")+" (extract(QUARTER  from  TUR_PNR_RESERVA.creation_date)  = extract(QUARTER  from now())) ";
			} else if (dias < 187) {// semestral
				strFechasB += (strFechasB.equals("")?"":" AND")+" (floor(((extract (month from  TUR_PNR_RESERVA.creation_date))-1 ) / 6) +1 = floor(((extract (month from now()))-1 ) / 6) +1) ";
			}
		}		
		
                         
		return "AND "+(historico?strFechasB:strFechasH+(strFechasB.equals("")?"":(strFechasH.equals("")?"":" AND ")+strFechasB))+" ::DK:: ";
	}
//Método auxiliar para formatear las clases como lista SQL
	private String formatClases(String clases) {
	    StringBuilder formatted = new StringBuilder();
	    for (char c : clases.toCharArray()) {
	        if (formatted.length() > 0) {
	            formatted.append(", ");
	        }
	        formatted.append("'").append(c).append("'");
	    }
	    return formatted.toString();
	}

	public void autogenerarTickets() throws Exception {
		String sqlSelect = "select     ";
		String rutaDef = "";
		String sqlOp = "";
		String sqlOp2 = "";
		String sqlOp3 = "";
		String sqlField = "";
	
		String field= "";
		String field2= "";
		
		if (upfront) {
			 field =(detalle.isYQ()?(!detalle.isDolares()?"tarifa_facturada_yq_local" :"tarifa_facturada_yq_usa"):(!detalle.isDolares()?"tarifa_factura_local" :"tarifa_factura_usa"));
			 sqlOp +=    " sum( TUR_PNR_BOLETO."+field+") ";
			 calculedField1Descripcion=(detalle.isYQ()?(!detalle.isDolares()?"Tarifa con YQ" :"U$s tarifa con YQ"):(!detalle.isDolares()?"Tarifa" :"U$s Tarifa"));
			 field2 = "*";
			 calculedField2Descripcion="Cantidad tkt";
			 calculedField6Descripcion="Cantidad tkt";
			 sqlOp2 = " count( " +field2+")";
			 sqlOp3 = " count( " +field2+")";
			 sqlField = sqlOp+" as SUM_"+field+" ";
		} else if (isVolado || isVoladoEmitido) {
			field=  (detalle.isYQ()?(!detalle.isDolares()?"netoyq_local" :"netoyq_usa"):(!detalle.isDolares()?"neto_local" :"neto_usa"));
			calculedField1Descripcion=(detalle.isYQ()?(!detalle.isDolares()?"Neto con YQ" :"U$s Neto con YQ"):(!detalle.isDolares()?"Neto" :"U$s Neto"));;
			field2 = "*";
			calculedField2Descripcion="Cantidad tkt";
			 calculedField6Descripcion="Cantidad tkt";
			 sqlOp2 = " count( " +field2+")";
			 sqlOp3 = " count( " +field2+")";
			 sqlOp = " sum( " +field+")";
			
			
			if (isTarifaBruta) {
				field=    (detalle.isYQ()?(!detalle.isDolares()?"baseyq_local" :"baseyq_usa"):(!detalle.isDolares()?"tarifa_local" :"tarifa_usa"));
				sqlOp = " sum( " +field+")";
				calculedField1Descripcion=(detalle.isYQ()?(!detalle.isDolares()?"Tarifa con YQ" :"U$s tarifa con YQ"):(!detalle.isDolares()?"Tarifa" :"U$s Tarifa"));
				field2 = "*";
				calculedField2Descripcion="Cantidad tkt";
				 calculedField6Descripcion="Cantidad tkt";
					sqlOp2 = " count( " +field2+")";
					sqlOp3 = " count( " +field2+")";
			} else if (isMSCantidad) {
				field = "cantidad";
				sqlOp = " COUNT( * )";
				calculedField1Descripcion="Cantidad tkt";
				field2= (detalle.isYQ()?(!detalle.isDolares()?"netoyq_local" :"netoyq_usa"):(!detalle.isDolares()?"neto_local" :"neto_usa"));
				calculedField2Descripcion=(detalle.isYQ()?(!detalle.isDolares()?"Tarifa con YQ" :"U$s tarifa con YQ"):(!detalle.isDolares()?"Tarifa" :"U$s Tarifa"));
				 calculedField6Descripcion="Cantidad tkt";
					sqlOp2 = " sum( " +field2+")";
					sqlOp3 = " count( *)";
			} else if (isPax /*&&!hasRestriccionesDeRuta()*/) {
				field = "cantidad";
				sqlOp = " COUNT(*)";
				calculedField1Descripcion="Cantidad tkt";
				field2= (detalle.isYQ()?(!detalle.isDolares()?"netoyq_local" :"netoyq_usa"):(!detalle.isDolares()?"neto_local" :"neto_usa"));
				calculedField2Descripcion=(detalle.isYQ()?(!detalle.isDolares()?"Tarifa con YQ" :"U$s tarifa con YQ"):(!detalle.isDolares()?"Tarifa" :"U$s Tarifa"));
				 calculedField6Descripcion="Cantidad tkt";
				sqlOp2 = " sum( " +field2+")";
				sqlOp3 = " count( *)";
		}/*else if (isPax) {
				field = "cant_roundtrip";		
				sqlOp = " sum( " +field+")";
				 calculedField6Descripcion="Cantidad tkt";
				calculedField1Descripcion="Cantidad (ida y vuelta) tkt";
				field2= (detalle.isYQ()?(!detalle.isDolares()?"netoyq_local" :"netoyq_usa"):(!detalle.isDolares()?"neto_local" :"neto_usa"));
				calculedField2Descripcion=(detalle.isYQ()?(!detalle.isDolares()?"Tarifa con YQ" :"U$s tarifa con YQ"):(!detalle.isDolares()?"Tarifa" :"U$s Tarifa"));
				sqlOp2 = " sum( " +field2+")";
				sqlOp3 = " count( *)";
		} */
			sqlField = sqlOp+" as SUM_"+field+" ";
		} else {
			field=(detalle.isYQ()?(!detalle.isDolares()?"tarifa_facturada_yq_local" :"tarifa_facturada_yq_usa"):(!detalle.isDolares()?"neto_factura_local" :"neto_factura_usa"));
			calculedField1Descripcion=(detalle.isYQ()?(!detalle.isDolares()?"Neto con YQ" :"U$s Neto con YQ"):(!detalle.isDolares()?"Neto" :"U$s Neto"));;
			sqlOp = " sum( " +field+")";
			field2 = "cant_roundtrip";
			sqlOp2 = " sum( " +field2+")";
			sqlOp3 = " count( *)";
			calculedField6Descripcion="Cantidad tkt";
			calculedField2Descripcion="Cantidad (ida y vuelta) tkt";
			if (isTarifaBruta) {
				field=    (detalle.isYQ()?(!detalle.isDolares()?"tarifa_facturada_yq_local" :"tarifa_facturada_yq_usa"):(!detalle.isDolares()?"tarifa_factura_local" :"tarifa_factura_usa"));
				sqlOp = " sum( " +field+")";
				calculedField1Descripcion=(detalle.isYQ()?(!detalle.isDolares()?"Tarifa con YQ" :"U$s tarifa con YQ"):(!detalle.isDolares()?"Tarifa" :"U$s Tarifa"));
				field2 = "cant_roundtrip";
				sqlOp2 = " sum( " +field2+")";
				sqlOp3 = " count( *)";
				calculedField6Descripcion="Cantidad tkt";
				calculedField2Descripcion="Cantidad (ida y vuelta) tkt";
			} else if (isMSCantidad) {
				field = "cant_roundtrip";
				sqlOp = " sum( " +field+")";
				calculedField6Descripcion="Cantidad tkt";
				calculedField1Descripcion="Cantidad (ida y vuelta) tkt";
				field2=    (detalle.isYQ()?(!detalle.isDolares()?"tarifa_facturada_yq_local" :"tarifa_facturada_yq_usa"):(!detalle.isDolares()?"tarifa_factura_local" :"tarifa_factura_usa"));
				sqlOp2 = " sum( " +field2+")";
				sqlOp3 = " count( *)";
			  calculedField6Descripcion="Cantidad tkt";
				calculedField2Descripcion=(detalle.isYQ()?(!detalle.isDolares()?"Tarifa con YQ" :"U$s tarifa con YQ"):(!detalle.isDolares()?"Tarifa" :"U$s Tarifa"));
			} /*else if (isPax && hasRestriccionesDeRuta()){
				field = "cant_roundtrip";
				sqlOp = " sum( " +field+")";
				calculedField1Descripcion="Cantidad (ida y vuelta) tkt";
				field2=    (detalle.isYQ()?(!detalle.isDolares()?"tarifa_facturada_yq_local" :"tarifa_facturada_yq_usa"):(!detalle.isDolares()?"tarifa_factura_local" :"tarifa_factura_usa"));
				sqlOp2 = " sum( " +field2+")";
				sqlOp3 = " count( *)";
				calculedField2Descripcion=(detalle.isYQ()?(!detalle.isDolares()?"Tarifa con YQ" :"U$s tarifa con YQ"):(!detalle.isDolares()?"Tarifa" :"U$s Tarifa"));
				 calculedField6Descripcion="Cantidad tkt";
			}*/else if (isPax){
				field = "cantidad";
				sqlOp = " count( *)";
				calculedField1Descripcion="Cantidad tkt";
				field2=    (detalle.isYQ()?(!detalle.isDolares()?"tarifa_facturada_yq_local" :"tarifa_facturada_yq_usa"):(!detalle.isDolares()?"tarifa_factura_local" :"tarifa_factura_usa"));
				sqlOp2 = " sum( " +field2+")";
				sqlOp3 = " count( *)";
				calculedField2Descripcion=(detalle.isYQ()?(!detalle.isDolares()?"Tarifa con YQ" :"U$s tarifa con YQ"):(!detalle.isDolares()?"Tarifa" :"U$s Tarifa"));
				 calculedField6Descripcion="Cantidad tkt";
			} else {
				sqlOp = " sum( " +field+")";
				sqlOp3 = " count( *)";
			}
			sqlField = sqlOp+" as SUM_"+field+" ";
			if (isPorcentajeSobreElTotal)
				sqlField = sqlOp+" as SUM_"+field+" ";			
			if (isPorcentajeSoloIdaSobreElTotal)
				sqlField = sqlOp+" as SUM_"+field+" ";			
			if (isPorcentajeSobreExcluidos)
				sqlField = sqlOp+" as SUM_"+field+" ";			
			if (isPorcentajeSobreGDSIncluidos)
				sqlField = sqlOp+" as SUM_"+field+" ";			
			
		}
		calculedFieldMSAerolinea=sqlOp;
		calculedFieldMSRuta=sqlOp;
		calculedFieldMSClase=sqlOp;
		calculedFieldRutas=sqlOp;
		calculedFieldVendedor=sqlOp;
		calculedFieldCliente=sqlOp;
		calculedFieldDiario=sqlOp;
		calculedFieldHistorico=sqlOp;
		calculedField2MSAerolinea=sqlOp2;
		calculedField2MSRuta=sqlOp2;
		calculedField2Vendedor=sqlOp2;
		calculedField2Cliente=sqlOp2;
		calculedField3Cliente=sqlOp3;
		calculedField2MSClase=sqlOp2;
		calculedField2Rutas=sqlOp2;
		calculedField2Diario=sqlOp2;
		calculedField2Historico=sqlOp2;

		String sqlFrom = " from  TUR_PNR_BOLETO   ";
		String sqlWhere="";
		String sqlWhere1="";
		String sqlWhereFecha="";
		String sqlWhereAerolineas="";
		String sqlWhereAerolineasInv="";
		String sqlWhereClases="";
		String sqlWhereTarifas="";
		String sqlWhereFareExcluidas="";
		String sqlWhereClasesExcluidas="";
		String sqlWhereClasesExcluidasInv="";
		String sqlWhereGDS="";
		
		
		if (hasOrigenContinente||hasOrigenZona||(isViceversa&&(hasDestinoContinente||hasDestinoZona))) {
			sqlFrom+=" JOIN TUR_AIRPORT ae_origen ON ae_origen.code=TUR_PNR_BOLETO.aeropuerto_origen  ";
			if (hasOrigenContinente||hasOrigenZona||(isViceversa&&(hasDestinoContinente||hasDestinoZona))) {
				sqlFrom+= " JOIN reg_pais airorpais ON ae_origen.country=airorpais.pais ";
			}
		}
		if (hasDestinoContinente||hasDestinoZona||(isViceversa&&(hasOrigenContinente||hasOrigenZona))) {
			sqlFrom+=" JOIN TUR_AIRPORT ae_destino ON ae_destino.code=TUR_PNR_BOLETO.aeropuerto_destino  ";
			if (hasDestinoContinente||hasDestinoZona||(isViceversa&&(hasOrigenContinente||hasOrigenZona))) {
				sqlFrom+= " JOIN reg_pais airdestpais ON ae_destino.country=airdestpais.pais ";
			}
		}
		sqlWhere1 += " WHERE (TUR_PNR_BOLETO.company= '" + detalle.getCompany() + "')   ";
		sqlWhere1 += " AND  TUR_PNR_BOLETO.void = 'N' AND  TUR_PNR_BOLETO.is_ticket = 'S'  ";
		sqlWhere1 += isOmitirExchange?" AND TUR_PNR_BOLETO.is_emision = 'S' " : isVolado?" AND TUR_PNR_BOLETO.reemitted = 'N' ":"";
		
		if (hasPrimera) {
			if (isPrimera)
				sqlWhere1 += " AND  /*1*/ TUR_PNR_BOLETO.boleto_original is null /*1*/ ";
			else if (isNoPrimera)
				sqlWhere1 += "  AND /*1*/ TUR_PNR_BOLETO.boleto_original is not null /*1*/ ";	
		}
		
		
		if (hasTipoViajes) {
			if (isOpenJaw)
				sqlWhere1 += " AND /*2*/  TUR_PNR_BOLETO.cant_roundtrip = 1 /*2*/ ";
			else if (isRoundTrip)
				sqlWhere1 += " AND /*2*/ TUR_PNR_BOLETO.cant_roundtrip = 2 /*2*/ ";	
		}
		if (hasCambio) {
			if (isPosteriorDespegue)
				sqlWhere1 += " AND /*3*/ TUR_PNR_BOLETO.creation_date > TUR_PNR_BOLETO.departure_date /*3*/ ";
			else if (isAntesDespegue)
				sqlWhere1 += " AND /*3*/ TUR_PNR_BOLETO.creation_date < TUR_PNR_BOLETO.departure_date /*3*/ ";	
		}
		if (hasMultidestino) {
			if (isMore4)
				sqlWhere1 += " AND  /*4*/ TUR_PNR_BOLETO.cant_segmentos >=4 /*4*/ ";
			else if (isLess4)
				sqlWhere1 += " AND  /*4*/ TUR_PNR_BOLETO.cant_segmentos < 4 /*4*/ ";	
		}
		if (hasStopover) {
			if (isStopover)
				sqlWhere1 += "  and /*5*/ EXISTS (select carrier from TUR_PNR_SEGMENTOAEREO where TUR_PNR_SEGMENTOAEREO.interface_id=TUR_PNR_BOLETO.interface_id and TUR_PNR_SEGMENTOAEREO.segmento_ini = 'O') /*5*/ ";
			else if (isNoStopover)
				sqlWhere1 += " and  /*5*/ NOT EXISTS (select carrier from TUR_PNR_SEGMENTOAEREO where TUR_PNR_SEGMENTOAEREO.interface_id=TUR_PNR_BOLETO.interface_id and TUR_PNR_SEGMENTOAEREO.segmento_ini = 'O') /*5*/ ";
		}
		if (hasVueltaMundo) {
			if (isVueltaMundo)
				sqlWhere1 += "  AND  /*6*/ TUR_PNR_BOLETO.vuelta_mundo = 'S' /*6*/ ";
			else if (isNoVueltaMundo)
				sqlWhere1 += "  AND /*6*/ TUR_PNR_BOLETO.vuelta_mundo = 'N' /*6*/ ";	
		}
		sqlWhereFecha +=  "AND "+ getFilterFecha(false);

		if (upfront) {
			sqlWhere1 += " AND is_ticket = 'S' ";	
		}
		
		if (detalle.hasMultiAerolineasPlaca()) {
			sqlWhereAerolineas = " AND /*8*/  TUR_PNR_BOLETO.codigoAEROLINEA in ( " + detalle.getAerolineasPlaca() + ") /*8*/ ";
			sqlWhereAerolineasInv = "  AND /*8*/ TUR_PNR_BOLETO.codigoAEROLINEA not in ( " + detalle.getAerolineasPlaca() + ") /*8*/ ";
		} else if(!detalle.getIdAerolinea().isEmpty()) {
			sqlWhereAerolineas = " AND /*8*/ TUR_PNR_BOLETO.codigoAEROLINEA = '" + detalle.getIdAerolinea() + "' /*8*/ ";
			sqlWhereAerolineasInv = " AND /*8*/ TUR_PNR_BOLETO.codigoAEROLINEA <> '" + detalle.getIdAerolinea() + "' /*8*/ ";
		}			



	
		if (detalle.getNoAerolineasPlaca() != null && !detalle.getNoAerolineasPlaca().trim().equals("")) {
			sqlWhere1 += "  AND /*8*/  TUR_PNR_BOLETO.codigoAEROLINEA not in ( " + detalle.getNoAerolineasPlaca() + ") /*8*/ ";
		}
		
		
	
		if (detalle.hasMultiAerolineas()) {
			if (detalle.getRawAerolineas().indexOf(":")!=-1) {
				String sqlTC = "";
				String rawAerolineas = detalle.getRawAerolineas(); // Ejemplo: "AR:ABC,DL:A,HL"
				if (rawAerolineas != null && !rawAerolineas.isEmpty()) {
				    String[] aerolineasArray = rawAerolineas.split(","); // Separar cada bloque de aerolínea y clases

				    for (String aerolineaClase : aerolineasArray) {
				        String[] parts = aerolineaClase.split(":"); // Separar la aerolínea de las clases
				        String carrier = parts[0].trim(); // Aerolínea
				        String clases = (parts.length > 1) ? parts[1].trim() : ""; // Clases (si existen)

				        if (!clases.isEmpty()) {
				            // Condición para aerolínea con clases
				            sqlTC += (sqlTC.isEmpty() ? "" : " AND ") +
				                     "EXISTS (SELECT 1 FROM TUR_PNR_SEGMENTOAEREO " +
				                     "WHERE TUR_PNR_SEGMENTOAEREO.interface_id = TUR_PNR_BOLETO.interface_id " +
				                     "AND TUR_PNR_SEGMENTOAEREO.carrier = '" + carrier + "' " +
				                     "AND TUR_PNR_SEGMENTOAEREO.clase IN (" + formatClases(clases) + "))";
				        } else {
				            // Condición para aerolínea sin clases
				            sqlTC += (sqlTC.isEmpty() ? "" : " AND ") +
				                     "EXISTS (SELECT 1 FROM TUR_PNR_SEGMENTOAEREO " +
				                     "WHERE TUR_PNR_SEGMENTOAEREO.interface_id = TUR_PNR_BOLETO.interface_id " +
				                     "AND TUR_PNR_SEGMENTOAEREO.carrier = '" + carrier + "')";
				        }
				    }

				    if (!sqlTC.isEmpty()) {
				        sqlWhereAerolineas +=  (sqlWhereAerolineas.isEmpty() ? "" : " AND ") +" /*8*/ " + sqlTC + " /*8*/ " ;
				    }
				}

			} else {
				String sqlTC = "";
				StringTokenizer toksTC = new StringTokenizer(detalle.getRawAerolineas(), ",;");
				while (toksTC.hasMoreTokens()) {
					String s = toksTC.nextToken();
					sqlTC += (sqlTC.equals("") ? "" : "|") +  s.trim();
				}
				if (!sqlTC.equals(""))
					sqlWhereAerolineas += (sqlWhere1.equals("") ? "" : " AND ")+  " /*8*/ " +"( carrier_intinerary ~* '^("+sqlTC+")(-("+sqlTC+"))*$')"+  " /*8*/ " ;
			
			}
		}
		
		if (detalle.hasNoMultiAerolineas()) {
			if (detalle.getRawNoAerolineas().indexOf(":")!=-1) {
				String sqlTC = "";
				String rawAerolineas = detalle.getRawNoAerolineas(); // Ejemplo: "AR:ABC,DL:A,HL"
				if (rawAerolineas != null && !rawAerolineas.isEmpty()) {
				    String[] aerolineasArray = rawAerolineas.split(","); // Separar cada bloque de aerolínea y clases

				    for (String aerolineaClase : aerolineasArray) {
				        String[] parts = aerolineaClase.split(":"); // Separar la aerolínea de las clases
				        String carrier = parts[0].trim(); // Aerolínea
				        String clases = (parts.length > 1) ? parts[1].trim() : ""; // Clases (si existen)

				        if (!clases.isEmpty()) {
				            // Condición para aerolínea con clases
				            sqlTC += (sqlTC.isEmpty() ? "" : " AND ") +
				                     "NOT EXISTS (SELECT 1 FROM TUR_PNR_SEGMENTOAEREO " +
				                     "WHERE TUR_PNR_SEGMENTOAEREO.interface_id = TUR_PNR_BOLETO.interface_id " +
				                     "AND TUR_PNR_SEGMENTOAEREO.carrier = '" + carrier + "' " +
				                     "AND TUR_PNR_SEGMENTOAEREO.clase IN (" + formatClases(clases) + "))";
				        } else {
				            // Condición para aerolínea sin clases
				            sqlTC += (sqlTC.isEmpty() ? "" : " AND ") +
				                     "NOT EXISTS (SELECT 1 FROM TUR_PNR_SEGMENTOAEREO " +
				                     "WHERE TUR_PNR_SEGMENTOAEREO.interface_id = TUR_PNR_BOLETO.interface_id " +
				                     "AND TUR_PNR_SEGMENTOAEREO.carrier = '" + carrier + "')";
				        }
				    }

				    if (!sqlTC.isEmpty()) {
				        sqlWhereAerolineas +=   (sqlWhereAerolineas.isEmpty() ? "" : " AND ") + " /*8*/ " +sqlTC+ " /*8*/ " ;
				    }
				}

			} else {
        String sqlNoTC = "";
        StringTokenizer toksTC = new StringTokenizer(detalle.getRawNoAerolineas(), ",;");
        while (toksTC.hasMoreTokens()) {
            String s = toksTC.nextToken().trim();
       
            sqlNoTC += (sqlNoTC.isEmpty() ? "" : " AND ") +
                       "NOT EXISTS (SELECT 1 FROM TUR_PNR_SEGMENTOAEREO " +
                       "WHERE TUR_PNR_SEGMENTOAEREO.interface_id = TUR_PNR_BOLETO.interface_id " +
                       "AND TUR_PNR_SEGMENTOAEREO.carrier = '" + s + "')";
        }
        
        if (!sqlNoTC.isEmpty()) {
        	sqlWhereAerolineas += (sqlWhereAerolineas.isEmpty() ? "" : " AND ") + " /*8*/ " + sqlNoTC +  " /*8*/ " ;
        }
			}
		}
		
		if (hasInterlineal) {
			if (isInterlineal) {
				sqlWhereAerolineas +=  " AND  /*9*/  TUR_PNR_BOLETO.is_interlineal ='S' "+ " /*9*/ " ;
			}	else if (isSoloUna) {
				sqlWhereAerolineas +=  " AND /*9*/  TUR_PNR_BOLETO.is_interlineal ='N' "+ " /*9*/ " ;
			}
		}


		fieldAerolinea= "TUR_PNR_BOLETO.codigoAEROLINEA";
		fieldRuta = "TUR_PNR_BOLETO.mini_route";
		fieldVendedor = "TUR_PNR_BOLETO.vendedor";
		fieldCliente = "TUR_PNR_BOLETO.customer_id_reducido";
		fieldClase = "TUR_PNR_BOLETO.clase";
		fieldRutaOrigen = "TUR_PNR_BOLETO.aeropuerto_origen";
		fieldRutaDestino = "TUR_PNR_BOLETO.aeropuerto_destino";
		fieldRutaOrigenGeo = "ae_origen.geo_position";
		fieldRutaDestinoGeo = "ae_destino.geo_position";
		calculedFromDetalle = "TUR_PNR_BOLETO.*";
	
		if (detalle.hasIatas()) {
			sqlWhere1 += " AND /*10*/ TUR_PNR_BOLETO.nro_iata in ( " + detalle.getIatas() + ") "+" /*10*/ " ;
		} if (detalle.hasNoIatas()) {
			sqlWhere1 += " AND /*10*/ NOT(TUR_PNR_BOLETO.nro_iata in ( " + detalle.getNoIatas() + ")) "+" /*10*/ " ;
		}


		

//		if (detalle.hasMultiAerolineas()&&!isMSCantidad&&!isMSTarifa) {
//			sqlWhere1 += " and not EXISTS (select carrier from TUR_PNR_SEGMENTOAEREO where TUR_PNR_SEGMENTOAEREO.interface_id=TUR_PNR_BOLETO.interface_id and TUR_PNR_SEGMENTOAEREO.carrier not in ( "+detalle.getAerolineas()+"))";
//		} else {
//			sqlWhere1 += " AND TUR_PNR_BOLETO.is_interlineal ='N' ";
//		}

	

		String sqlAND = "";
		String sqlOROrigen = "";
		String sqlORDestino = "";
		String sqlANDViceversa = "";
		String sqlOROrigenViceversa = "";
		String sqlORDestinoViceversa = "";
		if (hasTourCodeExcluded) {
			String sqlTC = "";
			StringTokenizer toksTC = new StringTokenizer(getTourcodeExcludes, ",;");
			while (toksTC.hasMoreTokens()) {
				String s = toksTC.nextToken();
				if (s.trim().indexOf("%")!=-1)
					sqlTC += (sqlTC.equals("") ? "" : " OR ") + " (TUR_PNR_BOLETO.tour_code not like '" + s.trim() + "') ";
				else
					sqlTC += (sqlTC.equals("") ? "" : " OR ") + " (TUR_PNR_BOLETO.tour_code <> '" + s.trim() + "') ";
				rutaDef+=(rutaDef.equals("")?"":",")+s.trim();
			}
			if (!sqlTC.equals(""))
				sqlAND +=  (sqlAND.equals("") ? "" : " AND ")+ " /*11*/ " +"("+sqlTC+")"+" /*11*/ ";
		}
		if (hasTourCodeIncluded) {
			String sqlTC = "";
			StringTokenizer toksTC = new StringTokenizer(getTourcodeIncluded, ",;");
			while (toksTC.hasMoreTokens()) {
				String s = toksTC.nextToken();
				if (s.trim().indexOf("%")!=-1)
					sqlTC += (sqlTC.equals("") ? "" : " OR ") + " (TUR_PNR_BOLETO.tour_code  like '" + s.trim() + "') ";
				else
					sqlTC += (sqlTC.equals("") ? "" : " OR ") + " (TUR_PNR_BOLETO.tour_code = '" + s.trim() + "') ";
				rutaDef+=(rutaDef.equals("")?"":",")+s.trim();
			}
			if (!sqlTC.equals(""))
				sqlAND += (sqlAND.equals("") ? "" : " AND ") +" /*12*/ " + "("+sqlTC+")"+" /*12*/ " ;
		}
		if (hasTipoPasajeroExcluded) {
			String sqlTC = "";
			StringTokenizer toksTC = new StringTokenizer(getTipoPasajeroExcludes, ",;");
			while (toksTC.hasMoreTokens()) {
				String s = toksTC.nextToken();
				if (s.trim().indexOf("%")!=-1)
					sqlTC += (sqlTC.equals("") ? "" : " OR ") + " (TUR_PNR_BOLETO.tipo_pasajero not like '" + s.trim() + "') ";
				else
					sqlTC += (sqlTC.equals("") ? "" : " OR ") + " (TUR_PNR_BOLETO.tipo_pasajero <> '" + s.trim() + "') ";
				rutaDef+=(rutaDef.equals("")?"":",")+s.trim();
			}
			if (!sqlTC.equals(""))
				sqlAND += (sqlAND.equals("") ? "" : " AND ")+" /*13*/ " + "("+sqlTC+")"+" /*13*/ ";
		}
		if (hasTipoPasajeroIncluded) {
			String sqlTC = "";
			StringTokenizer toksTC = new StringTokenizer(getTipoPasajeroIncluded, ",;");
			while (toksTC.hasMoreTokens()) {
				String s = toksTC.nextToken();
				if (s.trim().indexOf("%")!=-1)
					sqlTC += (sqlTC.equals("") ? "" : " OR ") + " (TUR_PNR_BOLETO.tipo_pasajero  like '" + s.trim() + "') ";
				else
					sqlTC += (sqlTC.equals("") ? "" : " OR ") + " (TUR_PNR_BOLETO.tipo_pasajero = '" + s.trim() + "') ";
				rutaDef+=(rutaDef.equals("")?"":",")+s.trim();
			}
			if (!sqlTC.equals(""))
				sqlAND += (sqlAND.equals("") ? "" : " AND ") +" /*14*/ " + "("+sqlTC+")"+" /*14*/ " ;
		}
		if (hasFareBasicExcluded) { 
	    String sqlTC = "";
	    String select = " not EXISTS (select TUR_PNR_SEGMENTOAEREO.interface_id from TUR_PNR_SEGMENTOAEREO where TUR_PNR_SEGMENTOAEREO.interface_id=TUR_PNR_BOLETO.interface_id and ";
	    StringTokenizer toksTC = new StringTokenizer(getFareBasicExcludes, ",;");
	    while (toksTC.hasMoreTokens()) {
	        String s = toksTC.nextToken();
	        if (s.matches("\\d+:'\\w+'")) { // Detectar el formato 7:'AB'
	            String[] parts = s.split(":'");
	            int position = Integer.parseInt(parts[0]);
	            String substring = parts[1].replace("'", ""); // Eliminar comillas simples
	            sqlTC += (sqlTC.isEmpty() ? "" : " OR ") +
	                     " (SUBSTRING(TUR_PNR_SEGMENTOAEREO.fare_basic_ext FROM " + position + " FOR " + substring.length() + ") = '" + substring + "') ";
	        } else if (s.matches("\\d+:\\w+")) { // Detectar el formato 7:XAB
	            String[] parts = s.split(":");
	            int position = Integer.parseInt(parts[0]);
	            String characters = parts[1];
	            String charList = characters.chars()
	                    .mapToObj(c -> "'" + (char) c + "'")
	                    .collect(Collectors.joining(","));
	            sqlTC += (sqlTC.isEmpty() ? "" : " OR ") +
	                     " (SUBSTRING(TUR_PNR_SEGMENTOAEREO.fare_basic_ext FROM " + position + " FOR 1) IN (" + charList + ")) ";
	        } else if (s.matches("\\d+:\\w+:[A-Z]{2}")) { // Detectar el formato 7:XAB:AA (con aerolínea)
	            String[] parts = s.split(":");
	            int position = Integer.parseInt(parts[0]);
	            String characters = parts[1];
	            String airline = parts[2];
	            String charList = characters.chars()
	                    .mapToObj(c -> "'" + (char) c + "'")
	                    .collect(Collectors.joining(","));
	            sqlTC += (sqlTC.isEmpty() ? "" : " OR ") +
	                     " ((SUBSTRING(TUR_PNR_SEGMENTOAEREO.fare_basic_ext FROM " + position + " FOR 1) IN (" + charList + ")) " +
	                     " AND TUR_PNR_SEGMENTOAEREO.carrier = '" + airline + "') ";
	        } else if (s.trim().indexOf("%") != -1) { // Detectar LIKE
	            sqlTC += (sqlTC.isEmpty() ? "" : " OR ") +
	                     " (TUR_PNR_SEGMENTOAEREO.fare_basic_ext LIKE '" + s.trim() + "') ";
	        } else { // Detectar igualdad exacta
	            sqlTC += (sqlTC.isEmpty() ? "" : " OR ") +
	                     " (TUR_PNR_SEGMENTOAEREO.fare_basic_ext = '" + s.trim() + "') ";
	        }
	        rutaDef += (rutaDef.isEmpty() ? "" : ",") + s.trim().replace("'", "");
	    }
	    if (!sqlTC.isEmpty())
	        sqlWhereFareExcluidas += " AND /*15*/ (" + select + "(" + sqlTC + ")" + "))"+" /*15*/ " ;
	}
		if (hasFareBasicIncluded) { 
	    String sqlTC = "";
	    String select = " EXISTS (select TUR_PNR_SEGMENTOAEREO.interface_id from TUR_PNR_SEGMENTOAEREO where TUR_PNR_SEGMENTOAEREO.interface_id=TUR_PNR_BOLETO.interface_id and ";
	    StringTokenizer toksTC = new StringTokenizer(getFareBasicIncluded, ",;");
	    while (toksTC.hasMoreTokens()) {
	        String s = toksTC.nextToken();
	        if (s.matches("\\d+:'\\w+'")) { // Detectar el formato 7:'AB'
	            String[] parts = s.split(":'");
	            int position = Integer.parseInt(parts[0]);
	            String substring = parts[1].replace("'", ""); // Eliminar comillas simples
	            sqlTC += (sqlTC.isEmpty() ? "" : " OR ") +
	                     " (SUBSTRING(TUR_PNR_SEGMENTOAEREO.fare_basic_ext FROM " + position + " FOR " + substring.length() + ") = '" + substring + "') ";
	        } else if (s.matches("\\d+:\\w+")) { // Detectar el formato 7:XAB
	            String[] parts = s.split(":");
	            int position = Integer.parseInt(parts[0]);
	            String characters = parts[1];
	            String charList = characters.chars()
	                    .mapToObj(c -> "'" + (char) c + "'")
	                    .collect(Collectors.joining(","));
	            sqlTC += (sqlTC.isEmpty() ? "" : " OR ") +
	                     " (SUBSTRING(TUR_PNR_SEGMENTOAEREO.fare_basic_ext FROM " + position + " FOR 1) IN (" + charList + ")) ";
	        } else if (s.matches("\\d+:\\w+:[A-Z]{2}")) { // Detectar el formato 7:XAB:AA (con aerolínea)
	            String[] parts = s.split(":");
	            int position = Integer.parseInt(parts[0]);
	            String characters = parts[1];
	            String airline = parts[2];
	            String charList = characters.chars()
	                    .mapToObj(c -> "'" + (char) c + "'")
	                    .collect(Collectors.joining(","));
	            sqlTC += (sqlTC.isEmpty() ? "" : " OR ") +
	                     " ((SUBSTRING(TUR_PNR_SEGMENTOAEREO.fare_basic_ext FROM " + position + " FOR 1) IN (" + charList + ")) " +
	                     " AND TUR_PNR_SEGMENTOAEREO.carrier = '" + airline + "') ";
	        } else if (s.trim().indexOf("%") != -1) { // Detectar LIKE
	            sqlTC += (sqlTC.isEmpty() ? "" : " OR ") +
	                     " (TUR_PNR_SEGMENTOAEREO.fare_basic_ext LIKE '" + s.trim() + "') ";
	        } else { // Detectar igualdad exacta
	            sqlTC += (sqlTC.isEmpty() ? "" : " OR ") +
	                     " (TUR_PNR_SEGMENTOAEREO.fare_basic_ext = '" + s.trim() + "') ";
	        }
	        rutaDef += (rutaDef.isEmpty() ? "" : ",") + s.trim().replace("'", "");
	    }
	    if (!sqlTC.isEmpty())
	        sqlWhereTarifas += " AND  /*16*/(" + select + "(" + sqlTC + ")" + "))"+" /*16*/ " ;
	}

		if (hasGDSIncluded) {
			sqlWhereGDS +=" AND /*17*/ TUR_PNR_BOLETO.gds in ( " + getGDSIncluded + ") "+" /*17*/ " ;
		} 
		if (hasGDSExcluded) {
			sqlWhereGDS += " AND /*17*/  NOT(TUR_PNR_BOLETO.gds in ( " + getGDSExcludes + ")) "+" /*17*/ " ;
		}
		if (hasClasesExcluded) {
	    // --- Agrupación para condiciones sobre TUR_PNR_SEGMENTOAEREO (para tokens con clase ≤ "z") ---
	    // Se usarán para formar un único NOT EXISTS
	    // Para tokens con dos partes:
	    Map<String, List<String>> segPaisExcl = new HashMap<>();    // key: país (sin '@'); value: lista de clases (a.toUpperCase())
	    Map<String, List<String>> segAirlineExcl = new HashMap<>(); // key: aerolínea; value: lista de clases (a.toUpperCase())
	    // Para tokens sin ":":
	    List<String> segClasesOnlyExcl = new ArrayList<>();
	    
	    // --- Agrupación para condiciones directas sobre TUR_PNR_BOLETO (para tokens con clase > "z") ---
	    Map<String, List<String>> bookingPaisDirect = new HashMap<>();    // key: país (sin '@'); value: lista de clases
	    Map<String, List<String>> bookingAirlineDirect = new HashMap<>(); // key: aerolínea; value: lista de clases
	    List<String> bookingClasesDirect = new ArrayList<>();             // tokens sin ":" para exclusión directa
	    
	    StringTokenizer toksTC = new StringTokenizer(getClasesExcludes, ",;");
	    while (toksTC.hasMoreTokens()) {
	        String token = toksTC.nextToken().trim();
	        if (token.isEmpty()) {
	            continue;
	        }
	        int pos = token.indexOf(":");
	        if (pos != -1) {
	            // Token con separador ":"
	            String a = token.substring(0, pos).trim();
	            String b = token.substring(pos + 1).trim();
	            // Por defecto: "a" es la primera parte y "b" es la segunda; si b tiene 2 o más caracteres se invierten.
	            String aerolinea = a;
	            String clase = b;
	            if (b.length() >= 2) {
	                aerolinea = b;
	                clase = a;
	            }
	            if (clase.compareTo("z") <= 0) {
	                // Para segmentos se usa a.toUpperCase() en la comparación de clase.
	                String claseValue = a.toUpperCase();
	                if (aerolinea.startsWith("@")) {
	                    String pais = aerolinea.substring(1);
	                    segPaisExcl.computeIfAbsent(pais, k -> new ArrayList<>()).add(claseValue);
	                } else {
	                    segAirlineExcl.computeIfAbsent(aerolinea, k -> new ArrayList<>()).add(claseValue);
	                }
	            } else {
	                // Para exclusión directa sobre TUR_PNR_BOLETO (clase > "z")
	                if (aerolinea.startsWith("@")) {
	                    String pais = aerolinea.substring(1);
	                    bookingPaisDirect.computeIfAbsent(pais, k -> new ArrayList<>()).add(clase);
	                } else {
	                    bookingAirlineDirect.computeIfAbsent(aerolinea, k -> new ArrayList<>()).add(clase);
	                }
	            }
	        } else {
	            // Token sin separador ":"
	            if (token.compareTo("z") <= 0) {
	                segClasesOnlyExcl.add(token.toUpperCase());
	            } else {
	                bookingClasesDirect.add(token);
	            }
	        }
	        // Se acumulan los tokens en rutaDef (opcional)
	        rutaDef += (rutaDef.isEmpty() ? "" : ",") + token ;
	    }
	    
	    // --- Construcción de la condición unificada para la parte de segmentos ---
	    List<String> segExclConds = new ArrayList<>();
	    // a) Por país:
	    for (Map.Entry<String, List<String>> entry : segPaisExcl.entrySet()) {
	        String pais = entry.getKey();
	        String classes = toQuotedList(entry.getValue());
	        segExclConds.add("(seg.Origen_pais = '" + pais + "' AND seg.clase IN (" + classes + "))");
	    }
	    // b) Por aerolínea:
	    for (Map.Entry<String, List<String>> entry : segAirlineExcl.entrySet()) {
	        String airline = entry.getKey();
	        String classes = toQuotedList(entry.getValue());
	        segExclConds.add("(seg.carrier = '" + airline + "' AND seg.clase IN (" + classes + "))");
	    }
	    // c) Para tokens sin ":":
	    if (!segClasesOnlyExcl.isEmpty()) {
	        String classes = toQuotedList(segClasesOnlyExcl);
	        segExclConds.add("seg.clase IN (" + classes + ")");
	    }
	    String segCondition = "";
	    if (!segExclConds.isEmpty()) {
	        segCondition = "NOT EXISTS (SELECT 1 FROM TUR_PNR_SEGMENTOAEREO seg " +
	                       "WHERE seg.interface_id = TUR_PNR_BOLETO.interface_id " +
	                       "AND (" + String.join(" OR ", segExclConds) + "))";
	    }
	    
	    // --- Construcción de las condiciones directas para TUR_PNR_BOLETO ---
	    List<String> bookingCondsList = new ArrayList<>();
	    // a) Por país:
	    for (Map.Entry<String, List<String>> entry : bookingPaisDirect.entrySet()) {
	        String pais = entry.getKey();
	        String classes = toQuotedList(entry.getValue());
	        bookingCondsList.add("NOT (TUR_PNR_BOLETO.pais_origen = '" + pais + "' " +
	                              "AND TUR_PNR_BOLETO.clase IN (" + classes + "))");
	    }
	    // b) Por aerolínea:
	    for (Map.Entry<String, List<String>> entry : bookingAirlineDirect.entrySet()) {
	        String airline = entry.getKey();
	        String classes = toQuotedList(entry.getValue());
	        bookingCondsList.add("NOT (TUR_PNR_BOLETO.codigoAEROLINEA = '" + airline + "' " +
	                              "AND TUR_PNR_BOLETO.clase IN (" + classes + "))");
	    }
	    // c) Para tokens sin ":":
	    if (!bookingClasesDirect.isEmpty()) {
	        String classes = toQuotedList(bookingClasesDirect);
	        bookingCondsList.add("TUR_PNR_BOLETO.clase NOT IN (" + classes + ")");
	    }
	    String bookingCondition = "";
	    if (!bookingCondsList.isEmpty()) {
	        bookingCondition = String.join(" AND ", bookingCondsList);
	    }
	    
	    // --- Combinación final de condiciones ---
	    List<String> finalConds = new ArrayList<>();
	    if (!segCondition.isEmpty()) {
	        finalConds.add(segCondition);
	    }
	    if (!bookingCondition.isEmpty()) {
	        finalConds.add(bookingCondition);
	    }
	    if (!finalConds.isEmpty()) {
	        sqlWhereClasesExcluidas +=  " AND  /*18*/ (" + String.join(" AND ", finalConds) + ")"+" /*18*/ ";
	    }
	}


		if (hasClasesExcluded) {
	    // Grupo A: Para tokens con dos partes y clase.compareTo("z") <= 0 (EXISTS sobre segmentos)
	    // Se agrupa según país (cuando el identificador inicia con '@') o aerolínea.
	    Map<String, List<String>> segExistsPais = new HashMap<>();    // key: pais (sin '@'); value: lista de clases (a.toUpperCase())
	    Map<String, List<String>> segExistsAirline = new HashMap<>(); // key: aerolínea; value: lista de clases (a.toUpperCase())
	    
	    // Grupo B: Para tokens con dos partes y clase.compareTo("z") > 0 (condiciones directas sobre TUR_PNR_BOLETO)
	    Map<String, List<String>> bookingInvPais = new HashMap<>();    // key: pais (sin '@'); value: lista de clases
	    Map<String, List<String>> bookingInvAirline = new HashMap<>(); // key: aerolínea; value: lista de clases
	    
	    // Grupo C: Tokens sin ":" y con valor ≤ "z" (EXISTS sobre segmentos, comparando la clase en mayúsculas)
	    List<String> segExistsOnly = new ArrayList<>();
	    
	    // Grupo D: Tokens sin ":" y con valor > "z" (condición directa sobre TUR_PNR_BOLETO)
	    List<String> bookingInvOnly = new ArrayList<>();
	    
	    StringTokenizer toksTC = new StringTokenizer(getClasesExcludes, ",;");
	    while (toksTC.hasMoreTokens()) {
	        String token = toksTC.nextToken().trim();
	        if (token.isEmpty()) {
	            continue;
	        }
	        int pos = token.indexOf(":");
	        if (pos != -1) {
	            // Separamos en dos partes
	            String a = token.substring(0, pos).trim();
	            String b = token.substring(pos + 1).trim();
	            String aerolinea = a;
	            String clase = b;
	            // Si la segunda parte tiene 2 o más caracteres, se invierten los valores
	            if (b.length() >= 2) {
	                aerolinea = b;
	                clase = a;
	            }
	            // Si la parte 'clase' es ≤ "z" se aplica EXISTS sobre segmentos (Grupo A)
	            if (clase.compareTo("z") <= 0) {
	                String claseValue = a.toUpperCase();
	                if (aerolinea.startsWith("@")) {
	                    String pais = aerolinea.substring(1);
	                    segExistsPais.computeIfAbsent(pais, k -> new ArrayList<>()).add(claseValue);
	                } else {
	                    segExistsAirline.computeIfAbsent(aerolinea, k -> new ArrayList<>()).add(claseValue);
	                }
	            } else {
	                // Para clase > "z", se aplican condiciones directas sobre TUR_PNR_BOLETO (Grupo B)
	                if (aerolinea.startsWith("@")) {
	                    String pais = aerolinea.substring(1);
	                    bookingInvPais.computeIfAbsent(pais, k -> new ArrayList<>()).add(clase);
	                } else {
	                    bookingInvAirline.computeIfAbsent(aerolinea, k -> new ArrayList<>()).add(clase);
	                }
	            }
	        } else {
	            // Token sin ":" se evalúa directamente
	            String tokenTrim = token.trim();
	            if (tokenTrim.compareTo("z") <= 0) {
	                segExistsOnly.add(tokenTrim.toUpperCase());
	            } else {
	                bookingInvOnly.add(tokenTrim);
	            }
	        }
	        // Se acumula el token en rutaDef
	        rutaDef += (rutaDef.isEmpty() ? "" : ",") + token;
	    }
	    
	    // Construcción de las condiciones finales (se unirán con OR)
	    List<String> conditions = new ArrayList<>();
	    
	    // Grupo A: Condiciones EXISTS sobre TUR_PNR_SEGMENTOAEREO
	    for (Map.Entry<String, List<String>> entry : segExistsPais.entrySet()) {
	        String pais = entry.getKey();
	        String classesList = toQuotedList(entry.getValue());
	        conditions.add("EXISTS (SELECT 1 FROM TUR_PNR_SEGMENTOAEREO seg " +
	                       "WHERE seg.interface_id = TUR_PNR_BOLETO.interface_id " +
	                       "AND seg.Origen_pais = '" + pais + "' " +
	                       "AND seg.clase IN (" + classesList + "))");
	    }
	    for (Map.Entry<String, List<String>> entry : segExistsAirline.entrySet()) {
	        String airline = entry.getKey();
	        String classesList = toQuotedList(entry.getValue());
	        conditions.add("EXISTS (SELECT 1 FROM TUR_PNR_SEGMENTOAEREO seg " +
	                       "WHERE seg.interface_id = TUR_PNR_BOLETO.interface_id " +
	                       "AND seg.carrier = '" + airline + "' " +
	                       "AND seg.clase IN (" + classesList + "))");
	    }
	    
	    // Grupo B: Condiciones directas sobre TUR_PNR_BOLETO
	    for (Map.Entry<String, List<String>> entry : bookingInvPais.entrySet()) {
	        String pais = entry.getKey();
	        String classesList = toQuotedList(entry.getValue());
	        conditions.add("((TUR_PNR_BOLETO.pais_origen = '" + pais + "' " +
	                       "AND TUR_PNR_BOLETO.clase IN (" + classesList + ")))");
	    }
	    for (Map.Entry<String, List<String>> entry : bookingInvAirline.entrySet()) {
	        String airline = entry.getKey();
	        String classesList = toQuotedList(entry.getValue());
	        conditions.add("((TUR_PNR_BOLETO.codigoAEROLINEA = '" + airline + "' " +
	                       "AND TUR_PNR_BOLETO.clase IN (" + classesList + ")))");
	    }
	    
	    // Grupo C: EXISTS para tokens sin ":" con valor ≤ "z"
	    if (!segExistsOnly.isEmpty()) {
	        String classesList = toQuotedList(segExistsOnly);
	        conditions.add("EXISTS (SELECT 1 FROM TUR_PNR_SEGMENTOAEREO seg " +
	                       "WHERE seg.interface_id = TUR_PNR_BOLETO.interface_id " +
	                       "AND seg.clase IN (" + classesList + "))");
	    }
	    
	    // Grupo D: Condición directa para tokens sin ":" con valor > "z"
	    if (!bookingInvOnly.isEmpty()) {
	        String classesList = toQuotedList(bookingInvOnly);
	        conditions.add("(TUR_PNR_BOLETO.clase IN (" + classesList + "))");
	    }
	    
	    // Se unen todas las condiciones con OR y se agregan a la cláusula final
	    if (!conditions.isEmpty()) {
	        sqlWhereClasesExcluidasInv +=" AND  /*18*/ (" + String.join(" OR ", conditions) + ")"+" /*18*/ " ;
	    }
	}
		if (hasClasesIncluded) {
	    // --- Agrupación para condiciones que se evaluarán en el único EXISTS (para segmentos) ---
	    Map<String, List<String>> existsByPais = new HashMap<>();    // key: país (sin '@'); value: lista de clases (en mayúsculas)
	    Map<String, List<String>> existsByCarrier = new HashMap<>(); // key: aerolínea; value: lista de clases (en mayúsculas)
	    List<String> existsOnly = new ArrayList<>();                 // Tokens sin ":" cuyo valor ≤ "z"

	    // --- Agrupación para condiciones directas sobre TUR_PNR_BOLETO ---
	    Map<String, List<String>> bookingByPais = new HashMap<>();    // key: país (sin '@'); value: lista de clases
	    Map<String, List<String>> bookingByCarrier = new HashMap<>(); // key: aerolínea; value: lista de clases
	    List<String> bookingOnly = new ArrayList<>();                 // Tokens sin ":" cuyo valor > "z"

	    StringTokenizer toksTC = new StringTokenizer(getClasesIncluded, ",;");
	    while (toksTC.hasMoreTokens()) {
	        String token = toksTC.nextToken().trim();
	        if (token.isEmpty()) {
	            continue;
	        }
	        int pos = token.indexOf(":");
	        if (pos != -1) {
	            // Token con separador ":"
	            String a = token.substring(0, pos).trim();
	            String b = token.substring(pos + 1).trim();
	            // Por defecto: "a" es aerolínea y "b" es clase; si b tiene 2 o más caracteres se intercambian
	            String aerolinea = a;
	            String clase = b;
	            if (b.length() >= 2) {
	                aerolinea = b;
	                clase = a;
	            }
	            if (clase.compareTo("z") <= 0) {
	                // Condición para segmentos: se usa a.toUpperCase() para comparar la clase
	                String claseValue = a.toUpperCase();
	                if (aerolinea.startsWith("@")) {
	                    String pais = aerolinea.substring(1);
	                    existsByPais.computeIfAbsent(pais, k -> new ArrayList<>()).add(claseValue);
	                } else {
	                    existsByCarrier.computeIfAbsent(aerolinea, k -> new ArrayList<>()).add(claseValue);
	                }
	            } else {
	                // Condición directa sobre TUR_PNR_BOLETO
	                if (aerolinea.startsWith("@")) {
	                    String pais = aerolinea.substring(1);
	                    bookingByPais.computeIfAbsent(pais, k -> new ArrayList<>()).add(clase);
	                } else {
	                    bookingByCarrier.computeIfAbsent(aerolinea, k -> new ArrayList<>()).add(clase);
	                }
	            }
	        } else {
	            // Token sin separador ":"
	            if (token.compareTo("z") <= 0) {
	                existsOnly.add(token.toUpperCase());
	            } else {
	                bookingOnly.add(token);
	            }
	        }
	        // Actualiza rutaDef (opcional)
	        rutaDef += (rutaDef.isEmpty() ? "" : ",") + token.toUpperCase();
	    }

	    // --- Construcción de la condición EXISTS (única) para segmentos ---
	    List<String> existsConds = new ArrayList<>();
	    for (Map.Entry<String, List<String>> entry : existsByPais.entrySet()) {
	        String pais = entry.getKey();
	        String classesList = toQuotedList(entry.getValue());
	        existsConds.add("(seg.Origen_pais = '" + pais + "' AND seg.clase IN (" + classesList + "))");
	    }
	    for (Map.Entry<String, List<String>> entry : existsByCarrier.entrySet()) {
	        String airline = entry.getKey();
	        String classesList = toQuotedList(entry.getValue());
	        existsConds.add("(seg.carrier = '" + airline + "' AND seg.clase IN (" + classesList + "))");
	    }
	    if (!existsOnly.isEmpty()) {
	        String classesList = toQuotedList(existsOnly);
	        existsConds.add("seg.clase IN (" + classesList + ")");
	    }
	    String existsClause = "";
	    if (!existsConds.isEmpty()) {
	        existsClause = "EXISTS (SELECT 1 FROM TUR_PNR_SEGMENTOAEREO seg " +
	                       "WHERE seg.interface_id = TUR_PNR_BOLETO.interface_id " +
	                       "AND (" + String.join(" OR ", existsConds) + "))";
	    }

	    // --- Construcción de las condiciones directas sobre TUR_PNR_BOLETO ---
	    List<String> bookingConds = new ArrayList<>();
	    for (Map.Entry<String, List<String>> entry : bookingByPais.entrySet()) {
	        String pais = entry.getKey();
	        String classesList = toQuotedList(entry.getValue());
	        bookingConds.add("((TUR_PNR_BOLETO.pais_origen = '" + pais + "' AND TUR_PNR_BOLETO.clase IN (" + classesList + ")))");
	    }
	    for (Map.Entry<String, List<String>> entry : bookingByCarrier.entrySet()) {
	        String airline = entry.getKey();
	        String classesList = toQuotedList(entry.getValue());
	        bookingConds.add("((TUR_PNR_BOLETO.codigoAEROLINEA = '" + airline + "' AND TUR_PNR_BOLETO.clase IN (" + classesList + ")))");
	    }
	    if (!bookingOnly.isEmpty()) {
	        String classesList = toQuotedList(bookingOnly);
	        bookingConds.add("(TUR_PNR_BOLETO.clase IN (" + classesList + "))");
	    }
	    String bookingClause = "";
	    if (!bookingConds.isEmpty()) {
	        bookingClause = String.join(" OR ", bookingConds);
	    }

	    // --- Combinación final de condiciones ---
	    List<String> finalConds = new ArrayList<>();
	    if (!existsClause.isEmpty()) {
	        finalConds.add(existsClause);
	    }
	    if (!bookingClause.isEmpty()) {
	        finalConds.add(bookingClause);
	    }
	    String finalCondition = "";
	    if (!finalConds.isEmpty()) {
	        finalCondition = String.join(" OR ", finalConds);
	    }
	    if (!finalCondition.isEmpty()) {
	        sqlWhereClases += " AND /*19*/  (" + finalCondition + ")"+" /*19*/ " ;
	    }
	}

		if (hasMercado) {
			String sqlOR = "";
			StringTokenizer toksTC = new StringTokenizer(getMercado, ",;");
			while (toksTC.hasMoreTokens()) {
				String s = toksTC.nextToken();
				if (s.trim().indexOf("%")!=-1) {
					sqlOR += (sqlOR.equals("") ? "" : " OR ") + " (TUR_PNR_BOLETO.market  like '" + s.trim() + "') ";
					continue;
				} 
				String rutaSQL = s.trim();
				if (rutaSQL.length() == 6)
					rutaSQL = "BOK-" + rutaSQL;
				else if (rutaSQL != null && rutaSQL.startsWith("BOK"))
					rutaSQL = rutaSQL.substring(1);

				sqlOR += (sqlOR.equals("") ? "" : " OR ") + " (TUR_PNR_BOLETO.market like '%" + rutaSQL + "%') ";
				rutaDef+=(rutaDef.equals("")?"":",")+s.trim();
			}
			if (!sqlOR.equals(""))
				sqlAND += (sqlAND.equals("") ? "" : " AND ") +" /*20*/ " + "("+sqlOR+")"+" /*20*/ " ;
		}
		if (hasNoMercado) {
			String sqlOR = "";
			StringTokenizer toksTC = new StringTokenizer(getNoMercado, ",;");
			while (toksTC.hasMoreTokens()) {
				String s = toksTC.nextToken();
				if (s.trim().indexOf("%")!=-1) {
					sqlOR += (sqlOR.equals("") ? "" : " OR ") + " (TUR_PNR_BOLETO.market  like '" + s.trim() + "') ";
					continue;
				} 
				String rutaSQL = s.trim();
				if (rutaSQL.length() == 6)
					rutaSQL = "BOK-" + rutaSQL;
				else if (rutaSQL != null && rutaSQL.startsWith("BOK"))
					rutaSQL = rutaSQL.substring(1);

				sqlOR += (sqlOR.equals("") ? "" : " OR ") + " (TUR_PNR_BOLETO.market like '%" + rutaSQL + "%') ";
				rutaDef+=(rutaDef.equals("")?"":",")+s.trim();
			}
			if (!sqlOR.equals(""))
				sqlAND +=(sqlAND.equals("") ? "" : " AND ") + " /*21*/ " + " NOT("+sqlOR+")"+" /*21*/ " ;
		}
		if (hasVuelos) {
			String sqlTC = "";
			String select = " EXISTS (select TUR_PNR_SEGMENTOAEREO.interface_id from TUR_PNR_SEGMENTOAEREO where TUR_PNR_SEGMENTOAEREO.interface_id=TUR_PNR_BOLETO.interface_id and ";
			StringTokenizer toksTC = new StringTokenizer(getVuelos, ",;");
			while (toksTC.hasMoreTokens()) {
				String s = toksTC.nextToken();
				if (s.trim().indexOf("-")!=-1) {
					long menor= JTools.getLongFirstNumberEmbedded(s.trim().substring(0,s.trim().indexOf("-")));
					long mayor= JTools.getLongFirstNumberEmbedded(s.trim().substring(s.trim().indexOf("-")+1));
					sqlTC += (sqlTC.equals("") ? "" : " OR ") + " (cast(substring(TUR_PNR_SEGMENTOAEREO.NumeroVuelo FROM '[0-9]+') AS INTEGER) between " + menor + " and "+mayor+") ";
				} else
					sqlTC += (sqlTC.equals("") ? "" : " OR ") + " (TUR_PNR_SEGMENTOAEREO.NumeroVuelo = '" + s.trim() + "') ";
				}
			if (!sqlTC.equals(""))
				sqlAND += (sqlAND.equals("") ? "" : " AND ") +" /*22*/ " + "("+ select+"("+ sqlTC+")"+"))"+" /*22*/ ";
		}
		if (hasNoVuelos) {
			String sqlTC = "";
			String select = " NOT EXISTS (select TUR_PNR_SEGMENTOAEREO.interface_id from TUR_PNR_SEGMENTOAEREO where TUR_PNR_SEGMENTOAEREO.interface_id=TUR_PNR_BOLETO.interface_id and ";
			StringTokenizer toksTC = new StringTokenizer(getNoVuelos, ",;");
			while (toksTC.hasMoreTokens()) {
				String s = toksTC.nextToken();
				if (s.trim().indexOf("-")!=-1) {
					long menor= JTools.getLongFirstNumberEmbedded(s.trim().substring(0,s.trim().indexOf("-")));
					long mayor= JTools.getLongFirstNumberEmbedded(s.trim().substring(s.trim().indexOf("-")+1));
					sqlTC += (sqlTC.equals("") ? "" : " OR ") + " (cast(substring(TUR_PNR_SEGMENTOAEREO.NumeroVuelo FROM '[0-9]+') AS INTEGER) between " + menor + " and "+mayor+") ";
				} else
					sqlTC += (sqlTC.equals("") ? "" : " OR ") + " (TUR_PNR_SEGMENTOAEREO.NumeroVuelo = '" + s.trim() + "') ";
				}
			if (!sqlTC.equals(""))
				sqlAND += (sqlAND.equals("") ? "" : " AND ") +" /*23*/ " + "("+ select+" "+ sqlTC+"))"+" /*23*/ " ;
		}
		if (hasRutas) {
			String sqlOR = "";
			StringTokenizer toksTC = new StringTokenizer(getRutas, ",;");
			while (toksTC.hasMoreTokens()) {
				String s = toksTC.nextToken();
				if (s.trim().indexOf("(")!=-1) {
					sqlOR += (sqlOR.equals("") ? "" : " OR ") + " (UPPER(TUR_PNR_BOLETO.route) like '%" + s.trim().toUpperCase() + "%') ";
				} else if (s.trim().indexOf("%")!=-1) {
					sqlOR += (sqlOR.equals("") ? "" : " OR ") + " (TUR_PNR_BOLETO.air_intinerary  like '" + s.trim() + "') ";
				} else {
					String sqlAND2="";
					String searchPais="";
					String searchAero="";
					String searchMetro="";
					boolean anyPais=false;
					boolean anyAero=false;
					boolean anyMetro=false;
					StringTokenizer toksTCI = new StringTokenizer(s, "-");
					while (toksTCI.hasMoreTokens()) {
						String nodo = toksTCI.nextToken();
						if (nodo.length()==2) { //pais
							searchPais += (searchPais.equals("")?"":"-")+nodo.toUpperCase();
							searchAero += (searchAero.equals("")?"":"-")+"___";
							searchMetro+= (searchMetro.equals("")?"":"-")+"___";
							anyPais=true;
						}
						else if (nodo.length()==3) { // aeropuerto
							boolean isMetro = isAreaMetro(nodo.toUpperCase());
							searchPais += (searchPais.equals("")?"":"-")+"__";
							if (isMetro) {
								anyMetro=true;
								searchMetro += (searchMetro.equals("")?"":"-")+nodo.toUpperCase();
								searchAero += (searchAero.equals("")?"":"-")+"___";
							} else {
								anyAero=true;
								searchAero += (searchAero.equals("")?"":"-")+nodo.toUpperCase();
								searchMetro += (searchMetro.equals("")?"":"-")+"___";
							}
						}	else
							continue;
					}
					if (anyAero && anyPais && anyMetro) {
						sqlAND2 += (sqlAND2.equals("") ? "" : " AND ") + " (TUR_PNR_BOLETO.air_pais_intinerary  like '" + searchPais + "') ";
						sqlAND2 += (sqlAND2.equals("") ? "" : " AND ") + " (TUR_PNR_BOLETO.air_intinerary  like '" + searchAero + "') ";
						sqlAND2 += (sqlAND2.equals("") ? "" : " AND ") + " (TUR_PNR_BOLETO.air_gen_intinerary  like '" + searchMetro + "') ";
					}	else if (anyAero && anyPais && !anyMetro) {
						sqlAND2 += (sqlAND2.equals("") ? "" : " AND ") + " (TUR_PNR_BOLETO.air_pais_intinerary  like '" + searchPais + "') ";
						sqlAND2 += (sqlAND2.equals("") ? "" : " AND ") + " (TUR_PNR_BOLETO.air_intinerary  like '" + searchAero + "') ";
					}	else if (anyAero && !anyPais && anyMetro) {
						sqlAND2 += (sqlAND2.equals("") ? "" : " AND ") + " (TUR_PNR_BOLETO.air_intinerary  like '" + searchAero + "') ";
						sqlAND2 += (sqlAND2.equals("") ? "" : " AND ") + " (TUR_PNR_BOLETO.air_gen_intinerary  like '" + searchMetro + "') ";
					}	else if (!anyAero && anyPais && anyMetro) {
						sqlAND2 += (sqlAND2.equals("") ? "" : " AND ") + " (TUR_PNR_BOLETO.air_pais_intinerary  like '" + searchPais + "') ";
						sqlAND2 += (sqlAND2.equals("") ? "" : " AND ") + " (TUR_PNR_BOLETO.air_gen_intinerary  like '" + searchMetro + "') ";
					} else if (anyAero) {
						sqlAND2 += (sqlAND2.equals("") ? "" : " AND ") + " (TUR_PNR_BOLETO.air_intinerary  = '" + s.trim() + "') ";
					}	else if (anyPais) {
						sqlAND2 += (sqlAND2.equals("") ? "" : " AND ") + " (TUR_PNR_BOLETO.air_pais_intinerary  = '" + s.trim() + "') ";
					}	else if (anyMetro) {
						sqlAND2 += (sqlAND2.equals("") ? "" : " AND ") + " (TUR_PNR_BOLETO.air_gen_intinerary  = '" + s.trim() + "') ";
					}
					if (!sqlAND2.equals(""))
						sqlOR+= (sqlOR.equals("") ? "" : " OR ") + sqlAND2;
				
				
				}
//				rutaDef+=(rutaDef.equals("")?"":",")+s.trim();
			}
			if (!sqlOR.equals(""))
				sqlAND += (sqlAND.equals("") ? "" : " AND ") + " /*24*/ " +"("+sqlOR+")"+" /*24*/ ";
		}
		if (hasNoRutas) {
			String sqlOR = "";
			StringTokenizer toksTC = new StringTokenizer(getNoRutas, ",;");
			while (toksTC.hasMoreTokens()) {
				String s = toksTC.nextToken();
				if (s.trim().indexOf("(")!=-1) {
					sqlOR += (sqlOR.equals("") ? "" : " AND ") + " (UPPER(TUR_PNR_BOLETO.route) not like '%" + s.trim().toUpperCase() + "%') ";
				} else if (s.trim().indexOf("%") != -1) {
					sqlOR += (sqlOR.equals("") ? "" : " AND ") + " (TUR_PNR_BOLETO.air_intinerary NOT LIKE '" + s.trim() + "') ";
				} else {
					String sqlAND2 = "";
					String searchPais = "";
					String searchAero = "";
					String searchMetro = "";
					boolean anyPais = false;
					boolean anyAero = false;
					boolean anyMetro = false;
					StringTokenizer toksTCI = new StringTokenizer(s, "-");
					while (toksTCI.hasMoreTokens()) {
						String nodo = toksTCI.nextToken();
						if (nodo.length() == 2) { // País
							searchPais += (searchPais.equals("") ? "" : "-") + nodo.toUpperCase();
							searchAero += (searchAero.equals("") ? "" : "-") + "___";
							searchMetro += (searchMetro.equals("") ? "" : "-") + "___";
							anyPais = true;
						} else if (nodo.length() == 3) { // aeropuerto
							boolean isMetro = isAreaMetro(nodo.toUpperCase());
							searchPais += (searchPais.equals("") ? "" : "-") + "__";
							if (isMetro) {
								anyMetro = true;
								searchMetro += (searchMetro.equals("") ? "" : "-") + nodo.toUpperCase();
								searchAero += (searchAero.equals("") ? "" : "-") + "___";
							} else {
								anyAero = true;
								searchAero += (searchAero.equals("") ? "" : "-") + nodo.toUpperCase();
								searchMetro += (searchMetro.equals("") ? "" : "-") + "___";
							}
						} else
							continue;
					}

					if (anyAero && anyPais && anyMetro) {
						sqlAND2 += (sqlAND2.equals("") ? "" : " OR ") + " (TUR_PNR_BOLETO.air_pais_intinerary NOT LIKE '" + searchPais + "') ";
						sqlAND2 += " AND (TUR_PNR_BOLETO.air_intinerary NOT LIKE '" + searchAero + "') ";
						sqlAND2 += " AND (TUR_PNR_BOLETO.air_gen_intinerary NOT LIKE '" + searchMetro + "') ";
					} else if (anyAero && anyPais && !anyMetro) {
						sqlAND2 += (sqlAND2.equals("") ? "" : " OR ") + " (TUR_PNR_BOLETO.air_pais_intinerary NOT LIKE '" + searchPais + "') ";
						sqlAND2 += " AND (TUR_PNR_BOLETO.air_intinerary NOT LIKE '" + searchAero + "') ";
					} else if (anyAero && !anyPais && anyMetro) {
						sqlAND2 += (sqlAND2.equals("") ? "" : " OR ") + " (TUR_PNR_BOLETO.air_intinerary NOT LIKE '" + searchAero + "') ";
						sqlAND2 += " AND (TUR_PNR_BOLETO.air_gen_intinerary NOT LIKE '" + searchMetro + "') ";
					} else if (!anyAero && anyPais && anyMetro) {
						sqlAND2 += (sqlAND2.equals("") ? "" : " OR ") + " (TUR_PNR_BOLETO.air_pais_intinerary NOT LIKE '" + searchPais + "') ";
						sqlAND2 += " AND (TUR_PNR_BOLETO.air_gen_intinerary NOT LIKE '" + searchMetro + "') ";
					} else if (anyAero) {
						sqlAND2 += (sqlAND2.equals("") ? "" : " OR ") + " (TUR_PNR_BOLETO.air_intinerary != '" + s.trim() + "') ";
					} else if (anyPais) {
						sqlAND2 += (sqlAND2.equals("") ? "" : " OR ") + " (TUR_PNR_BOLETO.air_pais_intinerary != '" + s.trim() + "') ";
					} else if (anyMetro) {
						sqlAND2 += (sqlAND2.equals("") ? "" : " OR ") + " (TUR_PNR_BOLETO.air_gen_intinerary != '" + s.trim() + "') ";
					}

					if (!sqlAND2.equals("")) {
						sqlOR += (sqlOR.equals("") ? "" : " AND ") + "(" + sqlAND2 + ")";
					}
				}
			}
			if (!sqlOR.equals("")) {
				sqlAND +=  (sqlAND.equals("") ? "" : " AND ") +" /*25*/ " + "(" + sqlOR + ")"+" /*25*/ ";
			}
		}


		if (hasDomesticInternacional) {
			if (isDomestic) {
				sqlAND+= (sqlAND.equals("") ? "" : " AND ") + " /*26*/ " +"(internacional_descr='Domestic')"+" /*26*/ " ;
			}
			if (isInternational) {
				sqlAND+= (sqlAND.equals("") ? "" : " AND ") +" /*26*/ " + "(internacional_descr='International')"+" /*26*/ " ;
			}
			if (isViceversa) {
				if (isDomestic) {
					sqlANDViceversa+= " /*26*/ " +(sqlANDViceversa.equals("") ? "" : " AND ") + "(internacional_descr='Domestic')"+" /*26*/ " ;
				}
				if (isInternational) {
					sqlANDViceversa+= " /*26*/ " +(sqlANDViceversa.equals("") ? "" : " AND ") + "(internacional_descr='International')"+" /*26*/ " ;	
				}
			}
		}	
		if (hasOrigenAeropuerto) {
			String sqlOR = "";
			String sqlORVic = "";
			JIterator<String> it = getOrigenAeropuerto.getIterator();
			while (it.hasMoreElements()) {
				String s = it.nextElement();
				sqlOR += (sqlOR.equals("") ? "" : " OR ") + " (TUR_PNR_BOLETO.aeropuerto_origen = '" + s.trim() + "') ";
				if (isViceversa) {
					sqlORVic += (sqlORVic.equals("") ? "" : " OR ") + " (TUR_PNR_BOLETO.aeropuerto_destino = '" + s.trim() + "') ";
				}
				rutaDef+=(rutaDef.equals("")?"":",")+s.trim();

			}
			if (!sqlOR.equals(""))
				sqlOROrigen += (sqlOROrigen.equals("") ? "" : " OR ")  + "("+sqlOR+")";
			if (!sqlORVic.equals(""))
				sqlOROrigenViceversa += (sqlOROrigenViceversa.equals("") ? "" : " OR ")  + "("+sqlORVic+")";
		}
		if (hasOrigenContinente) {
			String sqlOR = "";
			String sqlORVic = "";
			JIterator<String> it = getOrigenContinente.getIterator();
			while (it.hasMoreElements()) {
				String s = it.nextElement();
				sqlOR += (sqlOR.equals("") ? "" : " OR ") + " (airorpais.continente = '" + s.trim() + "') ";
				if (isViceversa) {
					sqlORVic += (sqlORVic.equals("") ? "" : " OR ") + " (airdestpais.continente = '" + s.trim() + "') ";
				}
				rutaDef+=(rutaDef.equals("")?"":",")+s.trim();
			}
			if (!sqlOR.equals(""))
				sqlOROrigen += (sqlOROrigen.equals("") ? "" : " OR ")  + "("+sqlOR+")";
			if (!sqlORVic.equals(""))
				sqlOROrigenViceversa += (sqlOROrigenViceversa.equals("") ? "" : " OR ")  + "("+sqlORVic+")";
		}
		if (hasOrigenZona) {
			String sqlOR = "";
			String sqlORVic = "";
			JIterator<String> it = getOrigenZona.getIterator();
			while (it.hasMoreElements()) {
				String s = it.nextElement();
				sqlOR += (sqlOR.equals("") ? "" : " OR ") + " (airorpais.region = '" + s.trim() + "') ";
				if (isViceversa) {
					sqlORVic += (sqlORVic.equals("") ? "" : " OR ") + " (airdestpais.region = '" + s.trim() + "') ";
				}				
				rutaDef+=(rutaDef.equals("")?"":",")+s.trim();
			}
			if (!sqlOR.equals(""))
				sqlOROrigen += (sqlOROrigen.equals("") ? "" : " OR ")  + "("+sqlOR+")";
			if (!sqlORVic.equals(""))
				sqlOROrigenViceversa += (sqlOROrigenViceversa.equals("") ? "" : " OR ")  + "("+sqlORVic+")";
		}
		if (hasOrigenPais) {
			String sqlOR = "";
			String sqlORVic = "";
			JIterator<String> it = getOrigenPais.getIterator();
			while (it.hasMoreElements()) {
				String s = it.nextElement();
				sqlOR += (sqlOR.equals("") ? "" : " OR ") + " ( TUR_PNR_BOLETO.pais_origen = '" + s.trim() + "') ";
				if (isViceversa) {
					sqlORVic += (sqlORVic.equals("") ? "" : " OR ") + " (TUR_PNR_BOLETO.pais_destino = '" + s.trim() + "') ";
				}
				rutaDef+=(rutaDef.equals("")?"":",")+s.trim();
			}
			if (!sqlOR.equals(""))
				sqlOROrigen +=(sqlOROrigen.equals("") ? "" : " OR ") + "("+sqlOR+")" ;
			if (!sqlORVic.equals(""))
				sqlOROrigenViceversa += (sqlOROrigenViceversa.equals("") ? "" : " OR ")  + "("+sqlORVic+")" ;
		}
		
		
		if (hasDestinoAeropuerto) {
			String sqlOR = "";
			String sqlORVic = "";
			JIterator<String> it = getDestinoAeropuerto.getIterator();
			while (it.hasMoreElements()) {
				String s = it.nextElement();
				sqlOR += (sqlOR.equals("") ? "" : " OR ") + " (TUR_PNR_BOLETO.aeropuerto_destino = '" + s.trim() + "') ";
				if (isViceversa) {
					sqlORVic += (sqlORVic.equals("") ? "" : " OR ") + " (TUR_PNR_BOLETO.aeropuerto_origen = '" + s.trim() + "') ";
				}
				rutaDef+=(rutaDef.equals("")?"":",")+s.trim();
			}
			if (!sqlOR.equals(""))
				sqlORDestino += (sqlORDestino.equals("") ? "" : " OR ") + "("+sqlOR+")";
			if (!sqlORVic.equals(""))
				sqlORDestinoViceversa += (sqlORDestinoViceversa.equals("") ? "" : " OR ")  + "("+sqlORVic+")";
		}
		if (hasDestinoContinente) {
			String sqlOR = "";
			String sqlORVic = "";
			JIterator<String> it = getDestinoContinente.getIterator();
			while (it.hasMoreElements()) {
				String s = it.nextElement();
				sqlOR += (sqlOR.equals("") ? "" : " OR ") + " (airdestpais.continente = '" + s.trim() + "') ";
				if (isViceversa) {
					sqlORVic += (sqlORVic.equals("") ? "" : " OR ") + " (airorpais.continente = '" + s.trim() + "') ";
				}
				rutaDef+=(rutaDef.equals("")?"":",")+s.trim();
			}
			if (!sqlOR.equals(""))
				sqlORDestino += (sqlORDestino.equals("") ? "" : " OR ")  + "("+sqlOR+")";
			if (!sqlORVic.equals(""))
				sqlORDestinoViceversa += (sqlORDestinoViceversa.equals("") ? "" : " OR ")  + "("+sqlORVic+")";
		}
		if (hasDestinoZona) {
			String sqlOR = "";
			String sqlORVic = "";
			JIterator<String> it = getDestinoZona.getIterator();
			while (it.hasMoreElements()) {
				String s = it.nextElement();
				sqlOR += (sqlOR.equals("") ? "" : " OR ") + " (airdestpais.region = '" + s.trim() + "') ";
				if (isViceversa) {
					sqlORVic += (sqlORVic.equals("") ? "" : " OR ") + " (airorpais.region = '" + s.trim() + "') ";
				}
				rutaDef+=(rutaDef.equals("")?"":",")+s.trim();
			}
			if (!sqlOR.equals(""))
				sqlORDestino += (sqlORDestino.equals("") ? "" : " OR ")  + "("+sqlOR+")";
			if (!sqlORVic.equals(""))
				sqlORDestinoViceversa += (sqlORDestinoViceversa.equals("") ? "" : " OR ")  + "("+sqlORVic+")";
		}
		if (hasDestinoPais) {
			String sqlOR = "";
			String sqlORVic = "";
			JIterator<String> it = getDestinoPais.getIterator();
			while (it.hasMoreElements()) {
				String s = it.nextElement();
				sqlOR += (sqlOR.equals("") ? "" : " OR ") + " ( TUR_PNR_BOLETO.pais_destino = '" + s.trim() + "') ";
				if (isViceversa) {
					sqlORVic += (sqlORVic.equals("") ? "" : " OR ") + " (TUR_PNR_BOLETO.pais_origen = '" + s.trim() + "') ";
					
				}
				rutaDef+=(rutaDef.equals("")?"":",")+s.trim();
			}
			if (!sqlOR.equals(""))
				sqlORDestino +=(sqlORDestino.equals("") ? "" : " OR ") + "("+sqlOR+")" ;
			if (!sqlORVic.equals(""))
				sqlORDestinoViceversa += (sqlORDestinoViceversa.equals("") ? "" : " OR ")  + "("+sqlORVic+")" ;
		}
		if (!hasAndOr) {
			if (isDestinoNegado && isOrigenNegado) {
				String grupo="";
				String grupoInv="";
				if (!sqlOROrigen.equals(""))
					grupo+= (grupo.equals("") ? "" : " AND ") + (isOrigenNegado?" NOT ":"")+"("+sqlOROrigen+")";
				if (!sqlOROrigenViceversa.equals(""))
					grupoInv+= (grupoInv.equals("") ? "" : " AND ") + (isOrigenNegado?" NOT ":"")+"("+sqlOROrigenViceversa+")";
		
				if (!sqlORDestino.equals(""))
					grupo+= (grupo.equals("") ? "" : " AND ") + (isDestinoNegado?" NOT ":"")+"("+sqlORDestino+")";
				if (!sqlORDestinoViceversa.equals(""))
					grupoInv+= (grupoInv.equals("") ? "" : " AND ") + (isDestinoNegado?" NOT ":"")+"("+sqlORDestinoViceversa+")";
				if (!sqlOROrigen.equals(""))
						sqlAND+= " /*27*/ " +(sqlAND.equals("") ? "" : " AND ") + "("+grupo+")"+" /*27*/ " ;
				if (!sqlORDestinoViceversa.equals(""))
						sqlANDViceversa+= " /*27*/ " +(sqlANDViceversa.equals("") ? "" : " AND ") + "("+grupoInv+")"+" /*27*/ " ;
				
			} else {
				if (!sqlOROrigen.equals(""))
					sqlAND+=  (sqlAND.equals("") ? "" : " AND ") +" /*27*/ " + (isOrigenNegado?" NOT ":"")+"("+sqlOROrigen+")"+ " /*27*/ " ;
				if (!sqlOROrigenViceversa.equals(""))
					sqlANDViceversa+=  (sqlANDViceversa.equals("") ? "" : " AND ") +" /*27*/ " + (isOrigenNegado?" NOT ":"")+"("+sqlOROrigenViceversa+")"+" /*27*/ " ;
		
				if (!sqlORDestino.equals(""))
					sqlAND+=  (sqlAND.equals("") ? "" : " AND ") +" /*27*/ " + (isDestinoNegado?" NOT ":"")+"("+sqlORDestino+")"+ " /*27*/ " ;
				if (!sqlORDestinoViceversa.equals(""))
					sqlANDViceversa+= (sqlANDViceversa.equals("") ? "" : " AND ") + (isDestinoNegado?" NOT ":"")+"("+sqlORDestinoViceversa+")"+ " /*27*/ " ;
			}

		} else if (isAnd) {
			if (!sqlOROrigen.equals(""))
				sqlAND+=  (sqlAND.equals("") ? "" : " AND ") +" /*27*/ " + (isOrigenNegado?" NOT ":"")+"("+sqlOROrigen+")"+ " /*27*/ " ;
			if (!sqlOROrigenViceversa.equals(""))
				sqlANDViceversa+=   (sqlANDViceversa.equals("") ? "" : " AND ") + " /*27*/ " +(isOrigenNegado?" NOT ":"")+"("+sqlOROrigenViceversa+")"+ " /*27*/ " ;
	
			if (!sqlORDestino.equals(""))
				sqlAND+=   (sqlAND.equals("") ? "" : " AND ") +" /*27*/ " + (isDestinoNegado?" NOT ":"")+"("+sqlORDestino+")"+ " /*27*/ " ;
			if (!sqlORDestinoViceversa.equals(""))
				sqlANDViceversa+=   (sqlANDViceversa.equals("") ? "" : " AND ") +" /*27*/ " + (isDestinoNegado?" NOT ":"")+"("+sqlORDestinoViceversa+")"+ " /*27*/ " ;
		
		} else if (isOr) {
			String grupo="";
			String grupoInv="";
			if (!sqlOROrigen.equals(""))
				grupo+= (grupo.equals("") ? "" : " OR ") + (isOrigenNegado?" NOT ":"")+"("+sqlOROrigen+")";
			if (!sqlOROrigenViceversa.equals(""))
				grupoInv+= (grupoInv.equals("") ? "" : " OR ") + (isOrigenNegado?" NOT ":"")+"("+sqlOROrigenViceversa+")";
	
			if (!sqlORDestino.equals(""))
				grupo+= (grupo.equals("") ? "" : " OR ") + (isDestinoNegado?" NOT ":"")+"("+sqlORDestino+")";
			if (!sqlORDestinoViceversa.equals(""))
				grupoInv+= (grupoInv.equals("") ? "" : " OR ") + (isDestinoNegado?" NOT ":"")+"("+sqlORDestinoViceversa+")";
			if (!sqlOROrigen.equals(""))
					sqlAND+=  (sqlAND.equals("") ? "" : " OR ") + " /*27*/ " +"("+grupo+")"+ " /*27*/ " ;
			if (!sqlORDestinoViceversa.equals(""))
					sqlANDViceversa+=  (sqlANDViceversa.equals("") ? "" : " OR ") + " /*27*/ " +"("+grupoInv+")"+ " /*27*/ " ;
			
		}

		if (!sqlANDViceversa.equals("") && isRequiredAnd() ) {
			sqlWhere +=  " AND ((" + sqlAND + ") AND (" + sqlANDViceversa + "))" ;
		} else if (!sqlANDViceversa.equals("")) {
			sqlWhere +=  " AND ((" + sqlAND + ") OR (" + sqlANDViceversa + "))" ;
		} else if (!sqlAND.equals("")) {
			sqlWhere +=  " AND (" + sqlAND + ")" ;
			
		}
		String sqlGroup = " GROUP BY TUR_PNR_BOLETO.company ";

		String zTotalize = sqlSelect;//sin where2
		zTotalize+= sqlField;
		zTotalize+= sqlFrom;
		zTotalize+= sqlWhere1;
		zTotalize+= sqlWhereFecha;
		zTotalize+= sqlWhere;
		zTotalize+= sqlWhereClasesExcluidas;
		zTotalize+= sqlWhereClases;
		zTotalize+= sqlWhereGDS;
		zTotalize+= sqlWhereTarifas;
		zTotalize+= sqlWhereFareExcluidas;
		zTotalize+= sqlGroup;
		
		String zTotalize2 = sqlSelect;//sin where3
		zTotalize2+= sqlField;
		zTotalize2+= sqlFrom;
		zTotalize2+= sqlWhere1;
		zTotalize2+= sqlWhereFecha;
		zTotalize2+= sqlWhere;
		zTotalize2+= sqlWhereAerolineas;
		zTotalize2+= sqlWhereFareExcluidas;
		zTotalize2+= sqlWhereClasesExcluidas;
		zTotalize2+= sqlGroup;
		
		
		String zTotalize3 = sqlSelect;//sin where3
		zTotalize3+= sqlField;
		zTotalize3+= sqlFrom;
		zTotalize3+= sqlWhere1;
		zTotalize3+= sqlWhereFecha;
		zTotalize3+= sqlWhere;
		zTotalize3+= sqlWhereFareExcluidas;
		zTotalize3+= sqlWhereClasesExcluidasInv;
		zTotalize3+= sqlWhereGDS;
		zTotalize3+= sqlWhereAerolineas;
		zTotalize3+= sqlGroup;

		String zTotalize4 = sqlSelect;//sin where3
		zTotalize4+= sqlField;
		zTotalize4+= sqlFrom;
		zTotalize4+= sqlWhere1;
		zTotalize4+= sqlWhereFecha;
		zTotalize4+= sqlWhere;
		zTotalize4+= sqlWhereClasesExcluidas;
		zTotalize4+= sqlWhereFareExcluidas;
		zTotalize4+= sqlWhereClases;
		zTotalize4+= sqlWhereTarifas;
		zTotalize4+= sqlGroup;
		
		
	
		String sqlFieldProm = "case ("+zTotalize+") when 0 then 0 else ((100.0* "+sqlOp+" ) / ("+zTotalize+") ) end as SUM_"+field+" ";
		String sqlFieldPromTotal = "case ("+zTotalize2+") when 0 then 0 else ((100.0* "+sqlOp+" ) / ("+zTotalize2+") ) end as SUM_"+field+" ";
		String sqlFieldPromExcluidos = "case ("+zTotalize3+") when 0 then 0 else ((100.0* "+sqlOp+" ) / ("+zTotalize3+") ) end as SUM_"+field+" ";
		String sqlFieldPromGDSIncluidos = "case ("+zTotalize4+") when 0 then 0 else ((100.0* "+sqlOp+" ) / ("+zTotalize4+") ) end as SUM_"+field+" ";

		String sql = "";
		String sqlH = "";
		sql += sqlSelect;
		if (isPorcentajeSobreElTotal)
			sql += sqlFieldPromTotal;
		else if (isPorcentajeSobreExcluidos)
			sql += sqlFieldPromExcluidos;
		else if (isPorcentajeSobreGDSIncluidos)
			sql += sqlFieldPromGDSIncluidos;
		else if (isMSTarifa || isMSCantidad)
			sql += sqlFieldProm;
		else
			sql += sqlField;
		sql += sqlFrom;
		sql += sqlWhere1;
		sql += sqlWhereFecha;
		sql += sqlWhereAerolineas;
		if (!isPorcentajeSobreExcluidos)
			sql += sqlWhereClasesExcluidas;
		if (!isPorcentajeSobreGDSIncluidos)
			sql += sqlWhereGDS;
		sql += sqlWhereClases;
		sql += sqlWhereFareExcluidas;
		sql += sqlWhereTarifas;
		sql += sqlWhere;
		sql += sqlGroup;

		String sqlP = "";
		sqlP += sqlSelect;
	
		sqlP += sqlField;
		sqlP += sqlFrom;
		sqlP += sqlWhere1;
		sqlP += sqlWhereFecha;
		sqlP += sqlWhereAerolineas;
		if (!isPorcentajeSobreExcluidos)
			sqlP += sqlWhereClasesExcluidas;
		if (!isPorcentajeSobreGDSIncluidos)
			sqlP += sqlWhereGDS;
		sqlP += sqlWhereClases;
		sqlP += sqlWhereFareExcluidas;
		sqlP += sqlWhereTarifas;
		sqlP += sqlWhere;
		sqlP += sqlGroup;
		calculedFromMSAerolinea = sqlFrom;
		calculedWhereMSAerolinea = sqlWhere1 + sqlWhereFecha + " AND (( TRUE "+sqlWhereAerolineasInv+") or ( TRUE " +sqlWhereClases + sqlWhereTarifas +" ))"+ sqlWhereClasesExcluidas + sqlWhere +sqlWhereGDS+sqlWhereFareExcluidas;
		calculedGroupMSAerolinea = sqlGroup+", "+fieldAerolinea;

		calculedFromMSRuta = sqlFrom;
		calculedWhereMSRuta = sqlWhere1 + sqlWhereFecha + sqlWhereAerolineas + sqlWhereClases+ sqlWhereTarifas + sqlWhereClasesExcluidas + sqlWhere +sqlWhereGDS+sqlWhereFareExcluidas;
		calculedGroupMSRuta = sqlGroup+", "+fieldRuta;

		calculedFromVendedor = sqlFrom;
		calculedWhereVendedor = sqlWhere1 + sqlWhereFecha +  sqlWhereAerolineas + sqlWhereClases +sqlWhereTarifas + sqlWhereClasesExcluidas + sqlWhere +sqlWhereGDS+sqlWhereFareExcluidas;
		calculedGroupVendedor = sqlGroup+", "+fieldVendedor;

		calculedFromCliente = sqlFrom;
		calculedWhereCliente = sqlWhere1 + sqlWhereFecha + sqlWhereAerolineas + sqlWhereClases + sqlWhereTarifas + sqlWhereClasesExcluidas + sqlWhere +sqlWhereGDS+sqlWhereFareExcluidas;
		calculedGroupCliente = sqlGroup+", "+fieldCliente;

		calculedFromMSClase = sqlFrom;
		calculedWhereMSClase = sqlWhere1 + sqlWhereFecha + sqlWhereAerolineas + sqlWhereClases + sqlWhereTarifas + sqlWhereClasesExcluidas + sqlWhere +sqlWhereGDS+sqlWhereFareExcluidas;
		calculedGroupMSClase = sqlGroup+", "+fieldClase;

		calculedFromDiario = sqlFrom;
		calculedWhereDiario = sqlWhere1 + sqlWhereFecha + sqlWhereAerolineas + sqlWhereClases + sqlWhereTarifas + sqlWhereClasesExcluidas + sqlWhere +sqlWhereGDS+sqlWhereFareExcluidas;
		calculedGroupDiario = sqlGroup+", "+fieldDiario;

		calculedFromHistorico = sqlFrom;
		calculedWhereHistorico = sqlWhere1 + " and "+ getFilterFecha(true) + sqlWhereAerolineas + sqlWhereClases + sqlWhereTarifas + sqlWhereClasesExcluidas + sqlWhere +sqlWhereGDS+sqlWhereFareExcluidas;
		calculedGroupHistorico = sqlGroup+", "+fieldHistorico1+","+fieldHistorico2;

		calculedFromRutas = sqlFrom;
		calculedFromRutas +=(sqlFrom.indexOf("ae_origen")!=-1?"":" JOIN TUR_AIRPORT ae_origen ON ae_origen.code=TUR_PNR_BOLETO.aeropuerto_origen  ");
		calculedFromRutas +=(sqlFrom.indexOf("ae_destino")!=-1?"":" JOIN TUR_AIRPORT ae_destino ON ae_destino.code=TUR_PNR_BOLETO.aeropuerto_destino  ");
		calculedWhereRutas = sqlWhere1 + sqlWhereFecha +  sqlWhereAerolineas + sqlWhereClases + sqlWhereTarifas + sqlWhereClasesExcluidas +sqlWhereFareExcluidas+ sqlWhere +sqlWhereGDS;
		calculedGroupRutas = sqlGroup+", TUR_PNR_BOLETO.aeropuerto_origen,TUR_PNR_BOLETO.aeropuerto_destino";


		String sqlg = "select     ";
		sqlg += " * ";
		sqlg += sqlFrom;
		sqlg += sqlWhere1;
		sqlg += sqlWhereFecha;
		sqlg += sqlWhereAerolineas;
		sqlg += sqlWhereClases;
		sqlg += sqlWhereTarifas;
		if (!isPorcentajeSobreExcluidos)
			sqlg += sqlWhereClasesExcluidas;
		if (!isPorcentajeSobreGDSIncluidos)
			sqlg += sqlWhereGDS;
		sqlg += sqlWhereFareExcluidas;
		sqlg += sqlWhere;
		String agrupFecha = getGroupByFecha();
		String localFieldDiario;
    if (isBooking) {
    	localFieldDiario = fechaFijaEmision ? "TUR_PNR_BOOKING.fecha_emision" : "TUR_PNR_BOOKING.FechaDespegue";
    } else {
	  	localFieldDiario = fechaFijaEmision ? "TUR_PNR_BOLETO.creation_date" : "TUR_PNR_BOLETO.departure_date";
	  }
		String joinTotal=sqlFrom;
		String whereTotal="";
		String fecha = getFilterFechaFijo();
		String joinFiltrado=sqlFrom;
		String whereFiltrado="";
		String comment = (isVolado?"ES_VOLADO":"")+" "+(isBooking?"ES_BOOKING":"");

		if (isPorcentajeSobreElTotal) {
	
			whereTotal+= sqlWhere1;
			whereTotal+= fecha.equals("")?"": " AND "+fecha;
			
			whereTotal+= sqlWhere;
			whereTotal+= sqlWhereAerolineas;
			whereTotal+= sqlWhereFareExcluidas;
			whereTotal+= sqlWhereClasesExcluidas;
			whereFiltrado += sqlWhere1;
			whereFiltrado += fecha;
			whereFiltrado += sqlWhereAerolineas;
			whereFiltrado += sqlWhereClasesExcluidas;
			whereFiltrado += sqlWhereGDS;
			whereFiltrado += sqlWhereClases;
			whereFiltrado += sqlWhereFareExcluidas;
			whereFiltrado += sqlWhereTarifas;
			whereFiltrado += sqlWhere;
	
			
		}
		else if (isPorcentajeSobreExcluidos) {
			whereTotal+= sqlWhere1;
			whereTotal+= fecha.equals("")?"": " AND "+fecha;
			
			whereTotal+= sqlWhere;
			whereTotal+= sqlWhereFareExcluidas;
			whereTotal+= sqlWhereClasesExcluidasInv;
			whereTotal+= sqlWhereGDS;
			whereTotal+= sqlWhereAerolineas;
			whereFiltrado += sqlWhere1;
			whereFiltrado +=   fecha.equals("")?"": " AND "+fecha;
			whereFiltrado += sqlWhereAerolineas;
			whereFiltrado += sqlWhereGDS;
			whereFiltrado += sqlWhereClases;
			whereFiltrado += sqlWhereFareExcluidas;
			whereFiltrado += sqlWhereTarifas;
			whereFiltrado += sqlWhere;
		}else if (isPorcentajeSobreGDSIncluidos) {
			whereTotal+= sqlWhere1;
			whereTotal+=   fecha.equals("")?"": " AND "+fecha;
			whereTotal+= sqlWhere;
			whereTotal+= sqlWhereAerolineas;
			whereTotal+= sqlWhereClasesExcluidas;
			whereTotal+= sqlWhereFareExcluidas;
			whereTotal+= sqlWhereClases;
			whereTotal+= sqlWhereTarifas;

			whereFiltrado += sqlWhere1;
			whereFiltrado +=   fecha.equals("")?"": " AND "+fecha;
			whereFiltrado += sqlWhereAerolineas;
			whereFiltrado += sqlWhereClasesExcluidas;
			whereFiltrado += sqlWhereClases;
			whereFiltrado += sqlWhereFareExcluidas;
			whereFiltrado += sqlWhereTarifas;
			whereFiltrado += sqlWhere;
		}else if (isMSTarifa || isMSCantidad){
			whereTotal+= sqlWhere1;
			whereTotal+=   fecha.equals("")?"": " AND "+fecha;
			whereTotal+= sqlWhere;
			whereTotal+= sqlWhereClasesExcluidas;
			whereTotal+= sqlWhereClases;
			whereTotal+= sqlWhereGDS;
			whereTotal+= sqlWhereTarifas;
			whereTotal+= sqlWhereFareExcluidas;
			whereFiltrado += sqlWhere1;
			whereFiltrado +=   fecha.equals("")?"": " AND "+fecha;
			whereFiltrado += sqlWhereAerolineas;
			whereFiltrado += sqlWhereClasesExcluidas;
			whereFiltrado += sqlWhereGDS;
			whereFiltrado += sqlWhereClases;
			whereFiltrado += sqlWhereFareExcluidas;
			whereFiltrado += sqlWhereTarifas;
			whereFiltrado += sqlWhere;
		}else{
			whereTotal+= sqlWhere1;
			whereTotal+=  fecha.equals("")?"": " AND "+fecha;
			whereTotal+= sqlWhere;
			whereTotal+= sqlWhereAerolineas;
			whereTotal+= sqlWhereClasesExcluidas;
			whereTotal+= sqlWhereClases;
			whereTotal+= sqlWhereGDS;
			whereTotal+= sqlWhereTarifas;
			whereTotal+= sqlWhereFareExcluidas;

		}
		if (isPorcentajeSobreElTotal || isPorcentajeSobreExcluidos||isPorcentajeSobreGDSIncluidos||isMSTarifa || isMSCantidad) {

				sqlH+="WITH total_diario AS (\r\n"
					+ "		  SELECT\r\n"
					+ "		    DATE("+localFieldDiario+") AS fecha,\r\n"
					+ "		    "+agrupFecha+" AS periodo,\r\n"
		      + "		    "+sqlOp+" AS total_dia\r\n"
					+ "		  "+joinTotal+"\r\n"
					+ whereTotal 
					+ "		    AND "+localFieldDiario+" >= now() - INTERVAL '2 years'\r\n"
					+ "		  GROUP BY DATE("+localFieldDiario+"), "+agrupFecha+"\r\n"
					+ "		),\r\n"
					+ "		filtrados_diario AS (\r\n"
					+ "		  SELECT\r\n"
					+ "		    "+agrupFecha+" AS periodo,\r\n"
		      + "		    DATE("+localFieldDiario+") AS fecha,\r\n"
					+ "		    "+sqlOp+" AS filtro_dia\r\n"
					+ "		  "+joinFiltrado+"\r\n"
					+ whereFiltrado
					+ "		    AND "+localFieldDiario+" >= now() - INTERVAL '2 years'\r\n"
					+ "		  GROUP BY DATE("+localFieldDiario+"), "+agrupFecha+"\r\n"
						+ "		),\r\n"
					+ "	  acumulados AS (\r\n"
					+ "		  SELECT\r\n"
					+ "		    COALESCE(t.fecha, f.fecha) AS fecha,t.periodo as periodo,\r\n"
						+ "		    SUM(t.total_dia) OVER (PARTITION BY t.periodo ORDER BY COALESCE(t.fecha, f.fecha) ROWS UNBOUNDED PRECEDING) AS total_acum,\r\n"
					+ "		    SUM(f.filtro_dia) OVER (PARTITION BY t.periodo ORDER BY COALESCE(t.fecha, f.fecha) ROWS UNBOUNDED PRECEDING) AS filtro_acum\r\n"
					+ "		  FROM total_diario t\r\n"
					+ "		  FULL OUTER JOIN filtrados_diario f ON t.fecha = f.fecha\r\n"
					+ "		),\r\n"
					+ "	  base AS (\r\n"
					+ "  SELECT\r\n"
					+ "    fecha,\r\n"
					+ "    periodo,\r\n"
					+ "		  ROUND(100.0 * filtro_acum / NULLIF(total_acum, 0), 2) AS valor\r\n"
					+ "  FROM acumulados\r\n"
					+ ")";		
		} else {
			
			sqlH+="WITH total_diario AS (\r\n"
					+ "		  SELECT\r\n"
					+ "		    DATE("+localFieldDiario+") AS fecha,\r\n"
					+ "		    "+agrupFecha+" AS periodo,\r\n"
	        +" " +sqlOp+" AS total_dia\r\n"
					+ "		  "+joinTotal+"\r\n"
					+ whereTotal 
					+ "		    AND "+localFieldDiario+" >= now() - INTERVAL '2 years'\r\n"
					+ "		  GROUP BY DATE("+localFieldDiario+"), "+agrupFecha+"\r\n"
					+ "		),\r\n"
					+ "	  acumulados AS (\r\n"
					+ "		  SELECT\r\n"
					+ "		    t.fecha AS fecha, t.periodo as periodo, \r\n"
					+ "		    SUM(t.total_dia) OVER (PARTITION BY t.periodo ORDER BY t.fecha ROWS UNBOUNDED PRECEDING) AS total_acum\r\n"
					+ "		  FROM total_diario t\r\n"
					+ "		),\r\n"
					+ "	  base AS (\r\n"
					+ "  SELECT\r\n"
					+ "    fecha,\r\n"
					+ "    periodo,\r\n"
					+ "    ROUND(NULLIF(total_acum, 0), 2) AS valor\r\n"
					+ "  FROM acumulados\r\n"
					+ ")";
		}



		

		outputSql=sql;
		outputSqlHistorico=sqlH;
		outputSqlDetalle=sqlg;
		outputField="SUM_"+field;
		outputClass=GuiPNRTickets.class.getName();
		outputTitle="TICKETS "+rutaDef;
		if (isMSTarifa||isMSCantidad||isPorcentajeSobreElTotal || isPorcentajeSobreExcluidos || 
				isPorcentajeSobreGDSIncluidos || isPorcentajeSoloIdaSobreElTotal) {
			if (isPorcentajeSobreElTotal)
				outputSqlAux1 = zTotalize2;
			else if (isPorcentajeSobreExcluidos)
				outputSqlAux1 = zTotalize3;
			else if (isPorcentajeSobreGDSIncluidos)
				outputSqlAux1 = zTotalize;
			else if (isMSTarifa || isMSCantidad)
				outputSqlAux1 = zTotalize;

			outputSqlAux2 =sqlP;
			outputDescrSqlAux1 =(tipo==META?"Meta":"Base")+" total ventas";
			outputDescrSqlAux2 =(tipo==META?"Meta":"Base")+" total condición";
			
		}
			
		if (outputTitle.length()>400) 
			outputTitle = outputTitle.substring(0,390)+"...";
	}
	private String toQuotedList(List<String> list) {
    return list.stream()
               .map(s -> "'" + s + "'")
               .collect(Collectors.joining(", "));
}


	public void autogenerarBooking() throws Exception {

		String sqlSelect = "select     ";
		String rutaDefO = "";
		String rutaDefD = "";
		String rutaDef = "";
		String sqlOp;
		String sqlOp2;
		String sqlField;
		boolean requiredBoletos=false;
		if (upfront) {
			String field="baseyq_usa";
			String bigfield="boletos.baseyq_usa";
			requiredBoletos=true;

			if (detalle.isYQ()) {
				if (detalle.isDolares()) {
					if (detalle.isViceversa()) {
						field="baseyq_usa";
						bigfield="boletos."+field;
						calculedField1Descripcion="U$s Neto YQ boleto";
						requiredBoletos=true;
					}else{
						field="baseyq_usa";
						bigfield="boletos."+field;
						calculedField1Descripcion="U$s Neto YQ boleto";
						requiredBoletos=true;
					}
				} else {
					if (detalle.isViceversa()){
						field="netoyq_local";
						bigfield="boletos."+field;
						calculedField1Descripcion="Neto YQ boleto";
						requiredBoletos=true;
					} else {
						field="netoyq_local";
						bigfield="boletos."+field;
						calculedField1Descripcion="Neto YQ boleto";
						requiredBoletos=true;
					}
				}
			}else {
				if (detalle.isDolares()) {
					if (detalle.isViceversa()){
						field="monto_orig_factura";
						bigfield="TUR_PNR_BOOKING."+field;
						calculedField1Descripcion="U$s Neto";
						requiredBoletos=false;
					} else {
						field="monto_round_usa_factura";
						bigfield="TUR_PNR_BOOKING."+field;
						requiredBoletos=false;
						calculedField1Descripcion="U$s Neto ida y vuelta";
					}
				} else {
					if (detalle.isViceversa()) {
						field="monto_factura";
						bigfield="TUR_PNR_BOOKING."+field;
						requiredBoletos=false;
						calculedField1Descripcion="U$s Neto";
					} else {
						field="monto_round_factura";
						bigfield="TUR_PNR_BOOKING."+field;
						requiredBoletos=false;
						calculedField1Descripcion="U$s Neto ida y vuelta";
					}
				} 
			
			}
			sqlOp =" sum("+bigfield+") ";
			sqlField = sqlOp + " as SUM_TUR_PNR_BOLETO_TARIFA ";
			sqlOp2 = " sum(  TUR_PNR_BOOKING.roundtrip) ";
			calculedField2Descripcion="Cantidad (ida y vuelta) tkt";
	} else if (isVolado || isVoladoEmitido) {
			sqlOp = " sum(" + (detalle.isYQ() ? (!detalle.isDolares() ? "TUR_PNR_BOOKING.monto_round" : "TUR_PNR_BOOKING.monto_round_usa") : (!detalle.isDolares() ? "TUR_PNR_BOOKING.monto_round" : "TUR_PNR_BOOKING.monto_round_usa")) + ") ";
			calculedField1Descripcion=(detalle.isYQ()?(!detalle.isDolares()?"Neto con YQ" :"U$s Neto con YQ"):(!detalle.isDolares()?"Neto" :"U$s Neto"));;
			sqlOp2 = " sum(  TUR_PNR_BOOKING.roundtrip) ";
			requiredBoletos=false;
			calculedField2Descripcion="Cantidad (ida y vuelta) tkt";
			if (isTarifaBruta) {
				sqlOp = " sum(" + (detalle.isYQ() ? (!detalle.isDolares() ? "boletos.netoyq_local" : "boletos.baseyq_usa") : (!detalle.isDolares() ? "boletos.tarifa" : "boletos.tarifa_usa")) + ") ";
				calculedField1Descripcion=(detalle.isYQ()?(!detalle.isDolares()?"Tarifa con YQ" :"U$s tarifa con YQ"):(!detalle.isDolares()?"Tarifa" :"U$s Tarifa"));
				sqlOp2 = " sum(  TUR_PNR_BOOKING.roundtrip) ";
				requiredBoletos=false;
				calculedField2Descripcion="Cantidad (ida y vuelta) tkt";
			} else if (isPax /*&& !hasRestriccionesDeRuta()*/) {
				sqlOp = " count(  *) ";
				calculedField1Descripcion="Cantidad tkt";
				sqlOp2 = " sum(" + (detalle.isYQ() ? (!detalle.isDolares() ? "TUR_PNR_BOOKING.monto" : "TUR_PNR_BOOKING.monto_orig") : (!detalle.isDolares() ? "TUR_PNR_BOOKING.monto" : "TUR_PNR_BOOKING.monto_orig")) + ") ";
				requiredBoletos=false;
				calculedField2Descripcion=(detalle.isYQ()?(!detalle.isDolares()?"Neto con YQ" :"U$s Neto con YQ"):(!detalle.isDolares()?"Neto" :"U$s Neto"));;
			} /*else if (isPax) {
				sqlOp = " sum(  TUR_PNR_BOOKING.roundtrip) ";
				calculedField1Descripcion="Cantidad (ida y vuelta) tkt";
				sqlOp2 = " sum(" + (detalle.isYQ() ? (!detalle.isDolares() ? "TUR_PNR_BOOKING.monto" : "TUR_PNR_BOOKING.monto_orig") : (!detalle.isDolares() ? "TUR_PNR_BOOKING.monto" : "TUR_PNR_BOOKING.monto_orig")) + ") ";
				requiredBoletos=false;
				calculedField2Descripcion=(detalle.isYQ()?(!detalle.isDolares()?"Neto con YQ" :"U$s Neto con YQ"):(!detalle.isDolares()?"Neto" :"U$s Neto"));;
			}*/ else if (isTarifa && !hasRestriccionesDeRuta()) {
				sqlOp = " sum(" + (detalle.isYQ() ? (!detalle.isDolares() ? "TUR_PNR_BOOKING.monto" : "TUR_PNR_BOOKING.monto_orig") : (!detalle.isDolares() ? "TUR_PNR_BOOKING.monto" : "TUR_PNR_BOOKING.monto_orig")) + ") ";
				calculedField1Descripcion=(detalle.isYQ()?(!detalle.isDolares()?"Neto con YQ" :"U$s Neto con YQ"):(!detalle.isDolares()?"Neto" :"U$s Neto"));;
				sqlOp2 = " sum(  TUR_PNR_BOOKING.roundtrip) ";
				requiredBoletos=false;
				calculedField2Descripcion="Cantidad (ida y vuelta) tkt";
			} else if (isTarifaNetaSoloIda) {
				sqlOp = " sum(" + (detalle.isYQ() ? (!detalle.isDolares() ? "TUR_PNR_BOOKING.monto" : "TUR_PNR_BOOKING.monto_orig") : (!detalle.isDolares() ? "TUR_PNR_BOOKING.monto" : "TUR_PNR_BOOKING.monto_orig")) + ") ";
				calculedField1Descripcion=(detalle.isYQ()?(!detalle.isDolares()?"Neto con YQ" :"U$s Neto con YQ"):(!detalle.isDolares()?"Neto" :"U$s Neto"));;
				sqlOp2 = " sum(  TUR_PNR_BOOKING.roundtrip) ";
				requiredBoletos=false;
				calculedField2Descripcion="Importe tkt";
			}
			sqlField = sqlOp + " as SUM_TUR_PNR_BOLETO_TARIFA ";

		} else {
			requiredBoletos=false;

			sqlOp = " sum(" + (detalle.isYQ() ? (!detalle.isDolares() ? "TUR_PNR_BOOKING.monto_factura" : "TUR_PNR_BOOKING.monto_orig_factura") : (!detalle.isDolares() ? "TUR_PNR_BOOKING.monto_factura" : "TUR_PNR_BOOKING.monto_orig_factura")) + ") ";
			calculedField1Descripcion=(detalle.isYQ()?(!detalle.isDolares()?"Neto con YQ" :"U$s Neto con YQ"):(!detalle.isDolares()?"Neto" :"U$s Neto"));;
			sqlOp2 = "sum(  TUR_PNR_BOOKING.roundtrip) ";
			calculedField2Descripcion="Cantidad (ida y vuelta) bks";
			if (isTarifaBruta) {
				sqlOp = " sum(" + (detalle.isYQ() ? (!detalle.isDolares() ? "boletos.netoyq_local" : "boletos.baseyq_usa") : (!detalle.isDolares() ? "boletos.tarifa_local" : "boletos.tarifa_usa")) + ") ";
				calculedField1Descripcion=(detalle.isYQ()?(!detalle.isDolares()?"Tarifa con YQ" :"U$s tarifa con YQ"):(!detalle.isDolares()?"Tarifa" :"U$s Tarifa"));
				sqlOp2 = "sum(  TUR_PNR_BOOKING.roundtrip) ";
				calculedField2Descripcion="Cantidad (ida y vuelta) bks";
			} else if (isMSCantidad && hasRestriccionesDeRuta()) {
				sqlOp = "sum(  TUR_PNR_BOOKING.roundtrip) ";
				calculedField1Descripcion="Cantidad (ida y vuelta) bks";
				sqlOp2 = " sum(" + (detalle.isYQ() ? (!detalle.isDolares() ? "TUR_PNR_BOOKING.monto_factura" : "TUR_PNR_BOOKING.monto_orig_factura") : (!detalle.isDolares() ? "TUR_PNR_BOOKING.monto_factura" : "TUR_PNR_BOOKING.monto_orig_factura")) + ") ";
				calculedField2Descripcion=(detalle.isYQ()?(!detalle.isDolares()?"Neto con YQ" :"U$s Neto con YQ"):(!detalle.isDolares()?"Neto" :"U$s Neto"));
			} else if (isMSCantidad && !hasRestriccionesDeRuta()) {
				sqlOp = " count(  TUR_PNR_BOOKING.*) ";
				calculedField1Descripcion="Cantidad bks";
				sqlOp2 = " sum(" + (detalle.isYQ() ? (!detalle.isDolares() ? "TUR_PNR_BOOKING.monto_factura" : "TUR_PNR_BOOKING.monto_orig_factura") : (!detalle.isDolares() ? "TUR_PNR_BOOKING.monto_factura" : "TUR_PNR_BOOKING.monto_orig_factura")) + ") ";
				calculedField2Descripcion=(detalle.isYQ()?(!detalle.isDolares()?"Neto con YQ" :"U$s Neto con YQ"):(!detalle.isDolares()?"Neto" :"U$s Neto"));
			} else if (isPax /*&& !hasRestriccionesDeRuta()*/) {
				sqlOp = " count(  TUR_PNR_BOOKING.*) ";
				calculedField1Descripcion="Cantidad bks";
				sqlOp2 = " sum(" + (detalle.isYQ() ? (!detalle.isDolares() ? "TUR_PNR_BOOKING.monto_factura" : "TUR_PNR_BOOKING.monto_orig_factura") : (!detalle.isDolares() ? "TUR_PNR_BOOKING.monto_factura" : "TUR_PNR_BOOKING.monto_orig_factura")) + ") ";
				calculedField2Descripcion=(detalle.isYQ()?(!detalle.isDolares()?"Neto con YQ" :"U$s Neto con YQ"):(!detalle.isDolares()?"Neto" :"U$s Neto"));
			} /*else if (isPax) {
				sqlOp = " sum(  TUR_PNR_BOOKING.roundtrip) ";
				calculedField1Descripcion="Cantidad (ida y vuelta) bks";
				sqlOp2 = " sum(" + (detalle.isYQ() ? (!detalle.isDolares() ? "TUR_PNR_BOOKING.monto_factura" : "TUR_PNR_BOOKING.monto_orig_factura") : (!detalle.isDolares() ? "TUR_PNR_BOOKING.monto_factura" : "TUR_PNR_BOOKING.monto_orig_factura")) + ") ";
				calculedField2Descripcion=(detalle.isYQ()?(!detalle.isDolares()?"Neto con YQ" :"U$s Neto con YQ"):(!detalle.isDolares()?"Neto" :"U$s Neto"));
			}*/ else if (isTarifa && !hasRestriccionesDeRuta()) {
				sqlOp = " sum(" + (detalle.isYQ() ? (!detalle.isDolares() ? "TUR_PNR_BOOKING.monto_factura" : "TUR_PNR_BOOKING.monto_orig_factura") : (!detalle.isDolares() ? "TUR_PNR_BOOKING.monto_factura" : "TUR_PNR_BOOKING.monto_orig_factura")) + ") ";
				calculedField1Descripcion=(detalle.isYQ()?(!detalle.isDolares()?"Neto con YQ" :"U$s Neto con YQ"):(!detalle.isDolares()?"Neto" :"U$s Neto"));
				sqlOp2 = "sum(  TUR_PNR_BOOKING.roundtrip) ";
				calculedField2Descripcion="Cantidad (ida y vuelta) bks";
			}
			sqlField = sqlOp + " as SUM_TUR_PNR_BOLETO_TARIFA ";
		}
		
		calculedFieldMSAerolinea=sqlOp;
		calculedFieldMSRuta=sqlOp;
		calculedFieldVendedor=sqlOp;
		calculedFieldCliente=sqlOp;
		calculedFieldMSClase=sqlOp;
		calculedFieldRutas=sqlOp;
		calculedFieldHistorico=sqlOp;
		calculedFieldDiario=sqlOp;
		calculedField2MSAerolinea=sqlOp2;
		calculedField2MSRuta=sqlOp2;
		calculedField2Vendedor=sqlOp2;
		calculedField2Cliente=sqlOp2;
		calculedField2MSClase=sqlOp2;
		calculedField2Rutas=sqlOp2;
		calculedField2Historico=sqlOp2;
		calculedField2Diario=sqlOp2;
		String sqlFrom = " from  TUR_PNR_BOOKING   ";
		String sqlWhere1="";
		String sqlWhereFecha="";
		String sqlWhereAerolineas="";
		String sqlWhereAerolineasInv="";
		String sqlWhereClases="";
		String sqlWhereGDS="";
		String sqlWhereTarifas="";
		String sqlWhereClasesExcluidas="";
		String sqlWhereFareExcluidas="";
		String sqlWhereClasesExcluidasInv="";
		if (requiredBoletos || hasMercado || hasNoMercado || hasPrimera || hasTipoViajes || hasCambio || hasMultidestino || hasStopover || hasVueltaMundo || hasTipoPasajeroExcluded || hasTipoPasajeroIncluded || hasTourCodeExcluded || hasGDSIncluded || hasGDSExcluded || hasTourCodeIncluded || detalle.hasIatas()||detalle.hasNoIatas() || hasInterlineal ||detalle.isTarifaBruta()) {
			sqlFrom+=" JOIN TUR_PNR_BOLETO boletos ON TUR_PNR_BOOKING.interface_id=boletos.interface_id AND TUR_PNR_BOOKING.company=boletos.company ";
		}

		if (hasOrigenContinente||hasOrigenZona||(isViceversa&&(hasDestinoContinente||hasDestinoZona))) {
			sqlFrom+= " JOIN reg_pais airorpais ON TUR_PNR_BOOKING.origen_pais=airorpais.pais ";
		}
		if (hasDestinoContinente||hasDestinoZona||(isViceversa&&(hasOrigenContinente||hasOrigenZona))) {
			sqlFrom+= " JOIN reg_pais airdestpais ON TUR_PNR_BOOKING.destino_pais=airdestpais.pais ";
		}
		sqlWhere1 += " WHERE (TUR_PNR_BOOKING.company= '" + detalle.getCompany() + "')   ";
		sqlWhere1 += isVolado?" AND TUR_PNR_BOOKING.reemitido = 'N' ":"";
		
		if (hasPrimera) {
			if (isPrimera)
				sqlWhere1 +=  " AND  /*1*/ boletos.boleto_original is null "+" /*1*/ " ;
			else if (isNoPrimera)
				sqlWhere1 += " AND /*1*/  boletos.boleto_original is not null "+" /*1*/ " ;	
		}
		
		if (hasVueltaMundo) {
			if (isVueltaMundo)
				sqlWhere1 += " AND /*6*/  boletos.vuelta_mundo = 'S' "+" /*6*/ " ;
			else if (isNoVueltaMundo)
				sqlWhere1 +=  " AND /*6*/  boletos.vuelta_mundo = 'N' "+" /*6*/ " ;	
		}
		
		if (hasTipoViajes) {
			if (isOpenJaw)
				sqlWhere1 +=" " +" AND /*2*/  boletos.cant_roundtrip = 1 "+" /*2*/ ";
			else if (isRoundTrip)
				sqlWhere1 += " AND /*2*/  boletos.cant_roundtrip = 2 "+" /*2*/ ";	
		}
		if (hasCambio) {
			if (isPosteriorDespegue)
				sqlWhere1 += " AND /*3*/ boletos.creation_date_date > boletos.departure_date "+" /*3*/ " ;
			else if (isAntesDespegue)
				sqlWhere1 += " AND /*3*/ boletos.creation_date_date < boletos.departure_date"+" /*3*/ " ;	
		}
		if (hasMultidestino) {
			if (isMore4)
				sqlWhere1 += " AND /*4*/ boletos.cant_segmentos >=4 "+" /*4*/ " ;
			else if (isLess4)
				sqlWhere1 += " AND /*4*/ boletos.cant_segmentos < 4"+" /*4*/ " ;	
		}
		if (hasStopover) {
			if (isStopover)
				sqlWhere1 += " and /*5*/  EXISTS (select carrier from TUR_PNR_SEGMENTOAEREO where TUR_PNR_SEGMENTOAEREO.interface_id=boletos.interface_id and TUR_PNR_SEGMENTOAEREO.segmento_ini = 'O')"+" /*5*/ " ;
			else if (isNoStopover)
				sqlWhere1 += " and  /*5*/  NOT EXISTS (select carrier from TUR_PNR_SEGMENTOAEREO where TUR_PNR_SEGMENTOAEREO.interface_id=boletos.interface_id and TUR_PNR_SEGMENTOAEREO.segmento_ini = 'O')"+" /*5*/ ";
		}
		sqlWhereFecha += " AND " +getFilterFecha(false);


			if (detalle.hasMultiAerolineasPlaca()) {
				sqlWhereAerolineas += " AND  /*8*/ TUR_PNR_BOOKING.carrier_placa in ( " + detalle.getAerolineasPlaca() + ") "+" /*8*/ ";
				sqlWhereAerolineasInv += " AND /*8*/ TUR_PNR_BOOKING.carrier_placa not in ( " + detalle.getAerolineasPlaca() + ") "+" /*8*/ ";
			} 
			if (detalle.hasNoMultiAerolineasPlaca()) {
				sqlWhereAerolineas += " AND /*8*/ TUR_PNR_BOOKING.carrier_placa not in ( " + detalle.getNoAerolineasPlaca() + ") "+" /*8*/ ";
				sqlWhereAerolineasInv +=  " AND /*8*/ TUR_PNR_BOOKING.carrier_placa in ( " + detalle.getNoAerolineasPlaca() + ") "+" /*8*/ ";
			} 
			if (detalle.hasMultiAerolineas()) {
				sqlWhereAerolineas += " AND /*8*/ TUR_PNR_BOOKING.carrier in ( " + detalle.getAerolineas() + ") "+" /*8*/ ";
				sqlWhereAerolineasInv += " AND /*8*/ TUR_PNR_BOOKING.carrier not in ( " + detalle.getAerolineas() + ") "+" /*8*/ ";
			} else {
				sqlWhereAerolineas += " AND /*8*/ TUR_PNR_BOOKING.carrier = '" + detalle.getIdAerolinea() + "' "+" /*8*/ ";
				sqlWhereAerolineasInv += " AND /*8*/ TUR_PNR_BOOKING.carrier <> '" + detalle.getIdAerolinea() + "' "+" /*8*/ ";
			}
	

		if (detalle.getNoAerolineas() != null && !detalle.getNoAerolineas().trim().equals("")) {
			sqlWhereAerolineas += " AND /*8*/ TUR_PNR_BOOKING.carrier not in ( " + detalle.getNoAerolineas() + ") "+" /*8*/ ";
			sqlWhereAerolineasInv += " AND /*8*/ TUR_PNR_BOOKING.carrier  in ( " + detalle.getNoAerolineas() + ") "+" /*8*/ ";
		}
		if (hasInterlineal) {
			if (isInterlineal) { 
				sqlWhereAerolineas += " AND  /*9*/ boletos.is_interlineal ='S' "+" /*9*/ ";
			}	else if (isSoloUna) {
				sqlWhereAerolineas += " AND  /*9*/ boletos.is_interlineal ='N' "+" /*9*/ ";
			}
		}
		
		
		fieldAerolinea= "TUR_PNR_BOOKING.carrier";
		fieldRuta = "TUR_PNR_BOOKING.origen_destino";
		fieldVendedor = "boletos.vendedor";
		fieldCliente = "boletos.customer_id_reducido";
		fieldClase = "TUR_PNR_BOOKING.clase";
		fieldRutaOrigen = "TUR_PNR_BOOKING.despegue";
		fieldRutaDestino = "TUR_PNR_BOOKING.arrivo";
		fieldRutaOrigenGeo = "ae_origen.geo_position";
		fieldRutaDestinoGeo = "ae_destino.geo_position";
		calculedFromDetalle = "TUR_PNR_BOOKING.*";

		if (detalle.hasIatas())
			sqlWhere1 += " AND /*10*/ boletos.nro_iata in ( " + detalle.getIatas() + ") "+" /*10*/ ";
		if (detalle.hasNoIatas())
			sqlWhere1 += " AND /*10*/ NOT(boletos.nro_iata in ( " + detalle.getNoIatas() + ")) "+" /*10*/ ";


		
		if (hasGDSIncluded) {
			sqlWhereGDS +=  " AND /*17*/ boletos.gds in ( " + getGDSIncluded + ") "+" /*17*/ ";
		} if (hasGDSExcluded) {
			sqlWhereGDS += " AND /*17*/ NOT(boletos.gds in ( " + getGDSExcludes + ")) "+" /*17*/ ";
		}

		String sqlAND = "";
		String sqlOROrigen = "";
		String sqlORDestino = "";
		String sqlANDViceversa = "";
		String sqlOROrigenViceversa = "";
		String sqlORDestinoViceversa = "";
		if (hasTourCodeExcluded) {
			String sqlOR = "";
			String sqlTC = "";
			StringTokenizer toksTC = new StringTokenizer(getTourcodeExcludes, ",;");
			while (toksTC.hasMoreTokens()) {
				String s = toksTC.nextToken();
				if (s.trim().indexOf("%")!=-1)
					sqlTC += (sqlTC.equals("") ? "" : " OR ") + " (boletos.tour_code not like '" + s.trim() + "') ";
				else
					sqlTC += (sqlTC.equals("") ? "" : " OR ") + " (boletos.tour_code <> '" + s.trim() + "') ";
				rutaDef+=(rutaDef.equals("")?"":",")+s.trim();

			}
			if (!sqlTC.equals(""))
				sqlAND += (sqlAND.equals("") ? "" : " AND ") +" /*11*/ "+ " (" + sqlTC+")"+" /*11*/ ";
		}
		if (hasTourCodeIncluded) {
			String sqlOR = "";
			String sqlTC = "";
			StringTokenizer toksTC = new StringTokenizer(getTourcodeIncluded, ",;");
			while (toksTC.hasMoreTokens()) {
				String s = toksTC.nextToken();
				if (s.trim().indexOf("%")!=-1)
					sqlTC += (sqlTC.equals("") ? "" : " OR ") + " (boletos.tour_code  like '" + s.trim() + "') ";
				else
					sqlTC += (sqlTC.equals("") ? "" : " OR ") + " (boletos.tour_code = '" + s.trim() + "') ";
				rutaDef+=(rutaDef.equals("")?"":",")+s.trim();

			}
			if (!sqlTC.equals(""))
				sqlAND += (sqlAND.equals("") ? "" : " AND ") + " /*12*/ "+" (" + sqlTC+")"+" /*11*/ ";
		}
		if (hasFareBasicExcluded) {
	    String sqlOR = "";
	    String sqlTC = "";
	    StringTokenizer toksTC = new StringTokenizer(getFareBasicExcludes, ",;");
	    while (toksTC.hasMoreTokens()) {
	        String s = toksTC.nextToken();
	        if (s.matches("\\d+:'\\w+'")) { // Detectar el formato 7:'AB'
	            String[] parts = s.split(":'");
	            int position = Integer.parseInt(parts[0]);
	            String substring = parts[1].replace("'", ""); // Eliminar comillas simples
	            // Construir la condición que verifica que la subcadena no esté presente
	            sqlTC += (sqlTC.isEmpty() ? "" : " AND ") +
	                     " (SUBSTRING(fare_basic_ext FROM " + position + " FOR " + substring.length() + ") <> '" + substring + "') ";
	        } else if (s.matches("\\d+:\\w+")) { // Detectar el formato 7:XAB
	            String[] parts = s.split(":");
	            int position = Integer.parseInt(parts[0]);
	            String characters = parts[1];
	            // Construir la cláusula NOT IN para los caracteres especificados
	            String charList = characters.chars()
	                    .mapToObj(c -> "'" + (char) c + "'")
	                    .collect(Collectors.joining(","));
	            sqlTC += (sqlTC.isEmpty() ? "" : " AND ") +
	                     " (SUBSTRING(fare_basic_ext FROM " + position + " FOR 1) NOT IN (" + charList + ")) ";
	        } else if (s.matches("\\d+:\\w+:[A-Z]{2}")) { // Detectar el formato 7:XAB:AA (con aerolínea)
	            String[] parts = s.split(":");
	            int position = Integer.parseInt(parts[0]);
	            String characters = parts[1];
	            String airline = parts[2];
	            // Construir la condición que incluye tanto NOT IN como el filtro de aerolínea
	            String charList = characters.chars()
	                    .mapToObj(c -> "'" + (char) c + "'")
	                    .collect(Collectors.joining(","));
	            sqlTC += (sqlTC.isEmpty() ? "" : " AND ") +
	                     " ((SUBSTRING(fare_basic_ext FROM " + position + " FOR 1) NOT IN (" + charList + ")) " +
	                     " OR carrier <> '" + airline + "') ";
	        } else if (s.trim().indexOf("%") != -1) { // Detectar LIKE
	            sqlTC += (sqlTC.isEmpty() ? "" : " AND ") + " (fare_basic_ext NOT LIKE '" + s.trim() + "') ";
	        } else { // Detectar igualdad exacta
	            sqlTC += (sqlTC.isEmpty() ? "" : " AND ") + " (fare_basic_ext <> '" + s.trim() + "') ";
	        }
	        rutaDef += (rutaDef.isEmpty() ? "" : ",") + s.trim().replace("'", "");
	    }
	    if (!sqlTC.isEmpty())
	        sqlWhereFareExcluidas += " AND /*15*/ (" + sqlTC + ")"+" /*15*/ ";
	}

		if (hasFareBasicIncluded) {
	    String sqlOR = "";
	    String sqlTC = "";
	    StringTokenizer toksTC = new StringTokenizer(getFareBasicIncluded, ",;");
	    while (toksTC.hasMoreTokens()) {
	        String s = toksTC.nextToken();
	        if (s.matches("\\d+:'\\w+'")) { // Detectar el formato 7:'AB'
	            String[] parts = s.split(":'");
	            int position = Integer.parseInt(parts[0]);
	            String substring = parts[1].replace("'", ""); // Eliminar comillas simples
	            // Construir la condición que verifica que la subcadena esté presente
	            sqlTC += (sqlTC.isEmpty() ? "" : " OR ") +
	                     " (SUBSTRING(fare_basic_ext FROM " + position + " FOR " + substring.length() + ") = '" + substring + "') ";
	        } else if (s.matches("\\d+:\\w+")) { // Detectar el formato 7:XAB
	            String[] parts = s.split(":");
	            int position = Integer.parseInt(parts[0]);
	            String characters = parts[1];
	            // Construir la cláusula IN para los caracteres especificados
	            String charList = characters.chars()
	                    .mapToObj(c -> "'" + (char) c + "'")
	                    .collect(Collectors.joining(","));
	            sqlTC += (sqlTC.isEmpty() ? "" : " OR ") +
	                     " (SUBSTRING(fare_basic_ext FROM " + position + " FOR 1) IN (" + charList + ")) ";
	        } else if (s.matches("\\d+:\\w")) { // Detectar el formato 7:X
	            String[] parts = s.split(":");
	            int position = Integer.parseInt(parts[0]);
	            char character = parts[1].charAt(0);
	            // Construir la cláusula LIKE para el formato 7:X usando SUBSTRING
	            sqlTC += (sqlTC.isEmpty() ? "" : " OR ") +
	                     " (SUBSTRING(fare_basic_ext FROM " + position + " FOR 1) = '" + character + "') ";
	        } else if (s.trim().indexOf("%") != -1) {
	            sqlTC += (sqlTC.isEmpty() ? "" : " OR ") +
	                     " (fare_basic_ext LIKE '" + s.trim() + "') ";
	        } else {
	            sqlTC += (sqlTC.isEmpty() ? "" : " OR ") +
	                     " (fare_basic_ext = '" + s.trim() + "') ";
	        }
	        rutaDef += (rutaDef.isEmpty() ? "" : ",") + s.trim().replace("'", "");
	    }
	    if (!sqlTC.isEmpty())
	        sqlWhereTarifas +=" AND  /*16*/ (" + sqlTC + ")"+" /*16*/ ";
	}
		if (hasTipoPasajeroExcluded) {
			String sqlOR = "";
			String sqlTC = "";
			StringTokenizer toksTC = new StringTokenizer(getTipoPasajeroExcludes, ",;");
			while (toksTC.hasMoreTokens()) {
				String s = toksTC.nextToken();
				if (s.trim().indexOf("%")!=-1)
					sqlTC += (sqlTC.equals("") ? "" : " OR ") + " (boletos.tipo_pasajero not like '" + s.trim() + "') ";
				else
					sqlTC += (sqlTC.equals("") ? "" : " OR ") + " (boletos.tipo_pasajero <> '" + s.trim() + "') ";
				rutaDef+=(rutaDef.equals("")?"":",")+s.trim();

			}
			if (!sqlTC.equals(""))
				sqlAND +=(sqlAND.equals("") ? "" : " AND ") + " /*13*/ "+ " (" + sqlTC+")"+" /*13*/ ";
		}
		if (hasTipoPasajeroIncluded) {
			String sqlOR = "";
			String sqlTC = "";
			StringTokenizer toksTC = new StringTokenizer(getTipoPasajeroIncluded, ",;");
			while (toksTC.hasMoreTokens()) {
				String s = toksTC.nextToken();
				if (s.trim().indexOf("%")!=-1)
					sqlTC += (sqlTC.equals("") ? "" : " OR ") + " (boletos.tipo_pasajero  like '" + s.trim() + "') ";
				else
					sqlTC += (sqlTC.equals("") ? "" : " OR ") + " (boletos.tipo_pasajero = '" + s.trim() + "') ";
				rutaDef+=(rutaDef.equals("")?"":",")+s.trim();

			}
			if (!sqlTC.equals(""))
				sqlAND += (sqlAND.equals("") ? "" : " AND ") +" /*14*/ "+ " (" + sqlTC+")"+" /*14*/ ";
		}
		if (hasClasesExcluded) {
			String sqlOR = "";
			String sqlTC = "";
			StringTokenizer toksTC = new StringTokenizer(getClasesExcludes, ",;");
			while (toksTC.hasMoreTokens()) {
				String s = toksTC.nextToken();
				int pos = s.indexOf(":");
				if (pos!=-1) {
					String a = s.substring(0,pos);
					String b = s.substring(pos+1);
					String aerolinea =a.trim();
					String clase=b.trim();
					if (b.length()>=2) {
						aerolinea=b;
						clase=a;
					}
			
					if (aerolinea.startsWith("@")) {
						String pais = aerolinea.substring(1);
						sqlTC += (sqlTC.equals("") ? "" : " AND ") + " (TUR_PNR_BOOKING.Origen_pais <> '" + pais + "'  OR ( TUR_PNR_BOOKING.Origen_pais = '" + pais + "' AND TUR_PNR_BOOKING.clase <> '" + clase + "')) ";

					} else {
						sqlTC += (sqlTC.equals("") ? "" : " AND ") + " (TUR_PNR_BOOKING.carrier <> '" + aerolinea + "'  OR ( TUR_PNR_BOOKING.carrier = '" + aerolinea + "' AND TUR_PNR_BOOKING.clase <> '" + clase + "')) ";
					}
				} else {
					sqlTC += (sqlTC.equals("") ? "" : " AND ") + " (TUR_PNR_BOOKING.clase <> '" + s.trim() + "') ";
				}
				rutaDef+=(rutaDef.equals("")?"":",")+s.trim();
			}
			if (!sqlTC.equals(""))
				sqlWhereClasesExcluidas += (" AND ") +  " /*18*/ "+sqlTC+" /*18*/ ";
		}

		if (hasClasesExcluded) {
			String sqlOR = "";
			StringTokenizer toksTC = new StringTokenizer(getClasesExcludes, ",;");
			while (toksTC.hasMoreTokens()) {
				String s = toksTC.nextToken();
				int pos = s.indexOf(":");
				if (pos!=-1) {
					String a = s.substring(0,pos);
					String b = s.substring(pos+1);
					String aerolinea =a.trim();
					String clase=b.trim();
					if (b.length()>=2) {
						aerolinea=b;
						clase=a;
					}
					if (aerolinea.startsWith("@")) {
						String pais = aerolinea.substring(1);
						sqlOR += (sqlOR.equals("") ? "" : " OR ") + " ( (TUR_PNR_BOOKING.Origen_pais  = '" + pais + "'  AND TUR_PNR_BOOKING.clase ='" + clase + "')) ";

					} else {
							sqlOR += (sqlOR.equals("") ? "" : " OR ") + " ( (TUR_PNR_BOOKING.carrier  = '" + aerolinea + "'  AND TUR_PNR_BOOKING.clase ='" + clase + "')) ";
					}
				} else {
					sqlOR += (sqlOR.equals("") ? "" : " OR ") + " (TUR_PNR_BOOKING.clase ='" + s.trim() + "') ";
				}
				rutaDef+=(rutaDef.equals("")?"":",")+s.trim();
	
			}
			if (!sqlOR.equals(""))
				sqlWhereClasesExcluidasInv += (" AND ")  + " /*18*/ "+ "("+sqlOR+")"+" /*18*/ ";

		}
		if (hasClasesIncluded) {
			String sqlOR = "";
			StringTokenizer toksTC = new StringTokenizer(getClasesIncluded, ",;");
			while (toksTC.hasMoreTokens()) {
				String s = toksTC.nextToken();
				int pos = s.indexOf(":");
				if (pos!=-1) {
					String a = s.substring(0,pos);
					String b = s.substring(pos+1);
					String aerolinea =a.trim();
					String clase=b.trim();
					if (b.length()>=2) {
						aerolinea=b;
						clase=a;
					}
					if (aerolinea.startsWith("@")) {
						String pais = aerolinea.substring(1);
						sqlOR += (sqlOR.equals("") ? "" : " OR ") + " ( (TUR_PNR_BOOKING.Origen_pais  = '" + pais + "'  AND TUR_PNR_BOOKING.clase ='" + clase + "')) ";
					} else {
						sqlOR += (sqlOR.equals("") ? "" : " OR ") + " ( (TUR_PNR_BOOKING.carrier  = '" + aerolinea + "'  AND TUR_PNR_BOOKING.clase ='" + clase + "')) ";
					}
				} else {
					sqlOR += (sqlOR.equals("") ? "" : " OR ") + " (TUR_PNR_BOOKING.clase ='" + s.trim() + "') ";
				}
				rutaDef+=(rutaDef.equals("")?"":",")+s.trim();
	
			}
			if (!sqlOR.equals(""))
				sqlWhereClases += (" AND ")  +" /*19*/ "+ "("+sqlOR+")"+" /*19*/ ";

		}
		if (hasMercado) {
			String sqlOR = "";
			StringTokenizer toksTC = new StringTokenizer(getMercado, ",;");
			while (toksTC.hasMoreTokens()) {
				String s = toksTC.nextToken();
				String rutaSQL = s.trim();
				if (s.trim().indexOf("%")!=-1) {
					sqlOR += (sqlOR.equals("") ? "" : " OR ") + " (TUR_PNR_BOOKING.mercado  like '" + s.trim() + "') ";
					continue;
				} 
				if (rutaSQL.length() == 6)
					rutaSQL = "BOK-" + rutaSQL;
				else if (rutaSQL != null && rutaSQL.startsWith("BOK"))
					rutaSQL = rutaSQL.substring(1);

				sqlOR += (sqlOR.equals("") ? "" : " OR ") + " (TUR_PNR_BOOKING.mercado like '%" + rutaSQL + "%') ";
				rutaDef+=(rutaDef.equals("")?"":",")+s.trim();
			}
			if (!sqlOR.equals(""))
				sqlAND += (sqlAND.equals("") ? "" : " OR ")  + " /*20*/ "+"("+sqlOR+")"+" /*20*/ ";
		}
		if (hasNoMercado) {
			String sqlOR = "";
			StringTokenizer toksTC = new StringTokenizer(getNoMercado, ",;");
			while (toksTC.hasMoreTokens()) {
				String s = toksTC.nextToken();
				String rutaSQL = s.trim();
				if (s.trim().indexOf("%")!=-1) {
					sqlOR += (sqlOR.equals("") ? "" : " OR ") + " (TUR_PNR_BOOKING.mercado  like '" + s.trim() + "') ";
					continue;
				} 
				if (rutaSQL.length() == 6)
					rutaSQL = "BOK-" + rutaSQL;
				else if (rutaSQL != null && rutaSQL.startsWith("BOK"))
					rutaSQL = rutaSQL.substring(1);

				sqlOR += (sqlOR.equals("") ? "" : " OR ") + " (TUR_PNR_BOOKING.mercado like '%" + rutaSQL + "%') ";
				rutaDef+=(rutaDef.equals("")?"":",")+s.trim();
			}
			if (!sqlOR.equals(""))
				sqlAND += (sqlAND.equals("") ? "" : " AND ") +" /*21*/ "+ " NOT("+sqlOR+")"+" /*21*/ ";
		}
		if (hasVuelos) {
			String sqlTC = "";
			StringTokenizer toksTC = new StringTokenizer(getVuelos, ",;");
			while (toksTC.hasMoreTokens()) {
				String s = toksTC.nextToken();
				if (s.trim().indexOf("-")!=-1) {
					long menor= JTools.getLongFirstNumberEmbedded(s.trim().substring(0,s.trim().indexOf("-")));
					long mayor= JTools.getLongFirstNumberEmbedded(s.trim().substring(s.trim().indexOf("-")+1));
					sqlTC += (sqlTC.equals("") ? "" : " OR ") + " (cast(substring(NumeroVuelo FROM '[0-9]+') AS INTEGER) between " + menor + " and "+mayor+") ";
				} else
					sqlTC += (sqlTC.equals("") ? "" : " OR ") + " (NumeroVuelo = '" + s.trim() + "') ";
				rutaDef+=(rutaDef.equals("")?"":",")+s.trim();
			}
			if (!sqlTC.equals(""))
				sqlAND += (sqlAND.equals("") ? "" : " AND ") + " /*22*/ "+ "("+ sqlTC+")"+ " /*22*/ ";
		}
		if (hasNoVuelos) {
			String sqlTC = "";
			StringTokenizer toksTC = new StringTokenizer(getNoVuelos, ",;");
			while (toksTC.hasMoreTokens()) {
				String s = toksTC.nextToken();
				if (s.trim().indexOf("-")!=-1) {
					long menor= JTools.getLongFirstNumberEmbedded(s.trim().substring(0,s.trim().indexOf("-")));
					long mayor= JTools.getLongFirstNumberEmbedded(s.trim().substring(s.trim().indexOf("-")+1));
					sqlTC += (sqlTC.equals("") ? "" : " AND ") + " not (cast(substring(NumeroVuelo FROM '[0-9]+') AS INTEGER) between " + menor + " and "+mayor+") ";
				} else
					sqlTC += (sqlTC.equals("") ? "" : " AND ") + " (NumeroVuelo <> '" + s.trim() + "') ";
				rutaDef+=(rutaDef.equals("")?"":",")+s.trim();
			}
			if (!sqlTC.equals(""))
				sqlAND +=  (sqlAND.equals("") ? "" : " AND ") +" /*23*/ "+ "("+ sqlTC+")"+ " /*23*/ ";
		}

		if (hasDomesticInternacional) {
			if (isDomestic)
				sqlAND+=  " /*26*/ "+(sqlAND.equals("") ? "" : " AND ") + "(internationaldomestic='Domestic')"+" /*26*/ ";
			if (isInternational)
				sqlAND+= " /*26*/ "+(sqlAND.equals("") ? "" : " AND ") + "(internationaldomestic='International')"+" /*26*/ ";
			if (isViceversa) {
				if (isDomestic)
					sqlANDViceversa+=(sqlANDViceversa.equals("") ? "" : " AND ") + " /*26*/ "+ "(internationaldomestic='Domestic')"+" /*26*/ ";
				if (isInternational)
					sqlANDViceversa+= " /*26*/ "+(sqlANDViceversa.equals("") ? "" : " AND ") + "(internationaldomestic='International')"+" /*26*/ ";	
			}
		}	
		if (hasOrigenAeropuerto) {
			String sqlOR = "";
			String sqlORVic = "";
			JIterator<String> it = getOrigenAeropuerto.getIterator();
			while (it.hasMoreElements()) {
				String s = it.nextElement();
				sqlOR += (sqlOR.equals("") ? "" : " OR ") + " (TUR_PNR_BOOKING.despegue = '" + s.trim() + "') ";
				if (isViceversa)
					sqlORVic += (sqlORVic.equals("") ? "" : " OR ") + " (TUR_PNR_BOOKING.arrivo = '" + s.trim() + "') ";
				rutaDefO+=(rutaDefO.equals("")?"":",")+s.trim();
			}
			if (!sqlOR.equals(""))
				sqlOROrigen += (sqlOROrigen.equals("") ? "" : " OR ")  + "("+sqlOR+")";
			if (!sqlORVic.equals(""))
				sqlOROrigenViceversa += (sqlOROrigenViceversa.equals("") ? "" : " OR ")  + "("+sqlORVic+")";
		}
		if (hasOrigenContinente) {
			String sqlOR = "";
			String sqlORVic = "";
			JIterator<String> it = getOrigenContinente.getIterator();
			while (it.hasMoreElements()) {
				String s = it.nextElement();
				sqlOR += (sqlOR.equals("") ? "" : " OR ") + " (airorpais.continente = '" + s.trim() + "') ";
				if (isViceversa)
					sqlORVic += (sqlORVic.equals("") ? "" : " OR ") + " (airdestpais.continente = '" + s.trim() + "') ";
				rutaDefO+=(rutaDefO.equals("")?"":",")+s.trim();
			}
			if (!sqlOR.equals(""))
				sqlOROrigen += (sqlOROrigen.equals("") ? "" : " OR ")  + "("+sqlOR+")";
			if (!sqlORVic.equals(""))
				sqlOROrigenViceversa += (sqlOROrigenViceversa.equals("") ? "" : " OR ")  + "("+sqlORVic+")";
		}
		if (hasOrigenZona) {
			String sqlOR = "";
			String sqlORVic = "";
			JIterator<String> it = getOrigenZona.getIterator();
			while (it.hasMoreElements()) {
				String s = it.nextElement();
				sqlOR += (sqlOR.equals("") ? "" : " OR ") + " (airorpais.region = '" + s.trim() + "') ";
				if (isViceversa)
					sqlORVic += (sqlORVic.equals("") ? "" : " OR ") + " (airdestpais.region = '" + s.trim() + "') ";
				rutaDefO+=(rutaDefO.equals("")?"":",")+s.trim();
			}
			if (!sqlOR.equals(""))
				sqlOROrigen += (sqlOROrigen.equals("") ? "" : " OR ")  + "("+sqlOR+")";
			if (!sqlORVic.equals(""))
				sqlOROrigenViceversa += (sqlOROrigenViceversa.equals("") ? "" : " OR ")  + "("+sqlORVic+")";
		}
		if (hasOrigenPais) {
			String sqlOR = "";
			String sqlORVic = "";
			JIterator<String> it = getOrigenPais.getIterator();
			while (it.hasMoreElements()) {
				String s = it.nextElement();
				sqlOR += (sqlOR.equals("") ? "" : " OR ") + " ( TUR_PNR_BOOKING.Origen_pais = '" + s.trim() + "') ";
				if (isViceversa)
					sqlORVic += (sqlORVic.equals("") ? "" : " OR ") + " (TUR_PNR_BOOKING.Destino_pais = '" + s.trim() + "') ";
				rutaDefO+=(rutaDefO.equals("")?"":",")+s.trim();
			}
			if (!sqlOR.equals(""))
				sqlOROrigen += (sqlOROrigen.equals("") ? "" : " OR ")  + "("+sqlOR+")";
			if (!sqlORVic.equals(""))
				sqlOROrigenViceversa += (sqlOROrigenViceversa.equals("") ? "" : " OR ")  + "("+sqlORVic+")";
		}
		if (!sqlOROrigen.equals(""))
			sqlAND+= (sqlAND.equals("") ? "" : " AND ") +(isOrigenNegado?" NOT ":"")+"("+sqlOROrigen+")";
		if (!sqlOROrigenViceversa.equals(""))
			sqlANDViceversa+= (sqlANDViceversa.equals("") ? "" : " AND ") +(isOrigenNegado?" NOT ":"")+"("+sqlOROrigenViceversa+")";
		if (isOrigenNegado) rutaDef += " NO "+rutaDefO;
		else rutaDef+=rutaDefO;
		if (hasDestinoAeropuerto) {
			String sqlOR = "";
			String sqlORVic = "";
			JIterator<String> it = getDestinoAeropuerto.getIterator();
			while (it.hasMoreElements()) {
				String s = it.nextElement();
				sqlOR += (sqlOR.equals("") ? "" : " OR ") + " (TUR_PNR_BOOKING.arrivo = '" + s.trim() + "') ";
				if (isViceversa)
					sqlORVic += (sqlORVic.equals("") ? "" : " OR ") + " (TUR_PNR_BOOKING.despegue = '" + s.trim() + "') ";
				rutaDefD+=(rutaDefD.equals("")?"":",")+s.trim();
			}
			if (!sqlOR.equals(""))
				sqlORDestino += (sqlORDestino.equals("") ? "" : " OR ")  + "("+sqlOR+")";
			if (!sqlORVic.equals(""))
				sqlORDestinoViceversa += (sqlORDestinoViceversa.equals("") ? "" : " OR ")  + "("+sqlORVic+")";
		}
		if (hasDestinoContinente) {
			String sqlOR = "";
			String sqlORVic = "";
			JIterator<String> it = getDestinoContinente.getIterator();
			while (it.hasMoreElements()) {
				String s = it.nextElement();
				sqlOR += (sqlOR.equals("") ? "" : " OR ") + " (airdestpais.continente = '" + s.trim() + "') ";
				if (isViceversa)
					sqlORVic += (sqlORVic.equals("") ? "" : " OR ") + " (airorpais.continente= '" + s.trim() + "') ";
				rutaDefD+=(rutaDefD.equals("")?"":",")+s.trim();
			}
			if (!sqlOR.equals(""))
				sqlORDestino += (sqlORDestino.equals("") ? "" : " OR ")  + "("+sqlOR+")";
			if (!sqlORVic.equals(""))
				sqlORDestinoViceversa += (sqlORDestinoViceversa.equals("") ? "" : " OR ")  + "("+sqlORVic+")";
		}
		if (hasDestinoZona) {
			String sqlOR = "";
			String sqlORVic = "";
  		JIterator<String> it = getDestinoZona.getIterator();
			while (it.hasMoreElements()) {
				String s = it.nextElement();
				sqlOR += (sqlOR.equals("") ? "" : " OR ") + " (airdestpais.region = '" + s.trim() + "') ";
				if (isViceversa)
					sqlORVic += (sqlORVic.equals("") ? "" : " OR ") + " (airorpais.region= '" + s.trim() + "') ";
				rutaDefD+=(rutaDefD.equals("")?"":",")+s.trim();
			}
			if (!sqlOR.equals(""))
				sqlORDestino += (sqlORDestino.equals("") ? "" : " OR ")  + "("+sqlOR+")";
			if (!sqlORVic.equals(""))
				sqlORDestinoViceversa += (sqlORDestinoViceversa.equals("") ? "" : " OR ")  + "("+sqlORVic+")";
		}
		if (hasDestinoPais) {
			String sqlOR = "";
			String sqlORVic = "";
			JIterator<String> it = getDestinoPais.getIterator();
			while (it.hasMoreElements()) {
				String s = it.nextElement();
				sqlOR += (sqlOR.equals("") ? "" : " OR ") + " ( TUR_PNR_BOOKING.Destino_pais = '" + s.trim() + "') ";
				if (isViceversa)
					sqlORVic += (sqlORVic.equals("") ? "" : " OR ") + " (TUR_PNR_BOOKING.Origen_pais = '" + s.trim() + "') ";
				rutaDefD+=(rutaDefD.equals("")?"":",")+s.trim();
			}
			if (!sqlOR.equals(""))
				sqlORDestino += (sqlORDestino.equals("") ? "" : " OR ")  + "("+sqlOR+")";
			if (!sqlORVic.equals(""))
				sqlORDestinoViceversa += (sqlORDestinoViceversa.equals("") ? "" : " OR ")  + "("+sqlORVic+")";
		}

		if (!sqlORDestino.equals(""))
			sqlAND+= (sqlAND.equals("") ? "" : " AND ") +(isDestinoNegado?" NOT ":"")+"("+sqlORDestino+")";
		if (!sqlORDestinoViceversa.equals(""))
			sqlANDViceversa+= (sqlANDViceversa.equals("") ? "" : " AND ") +(isDestinoNegado?" NOT ":"")+"("+sqlORDestinoViceversa+")";
		
		if (isDestinoNegado) rutaDef += " NO "+rutaDefD;
		else rutaDef+=rutaDefD;

		
		if (!sqlANDViceversa.equals("") && isRequiredAnd())
			sqlWhere1 += " AND /*27*/((" + sqlAND + ") AND (" + sqlANDViceversa + "))"+" /*27*/ ";
		else if (!sqlANDViceversa.equals(""))
			sqlWhere1 += " AND /*27*/((" + sqlAND + ") OR (" + sqlANDViceversa + "))"+" /*27*/ ";
		else if (!sqlAND.equals(""))
			sqlWhere1 += " AND /*27*/(" + sqlAND + ")"+" /*27*/ ";

		String sqlGroup = " GROUP BY TUR_PNR_BOOKING.company ";
		
		String zTotalize = sqlSelect;
		zTotalize+= sqlField;
		zTotalize+= sqlFrom;
		zTotalize+= sqlWhere1;
		zTotalize+= sqlWhereFecha;
		zTotalize+= sqlWhereClases;
		zTotalize+= sqlWhereTarifas;
		zTotalize+= sqlWhereFareExcluidas;
		zTotalize+= sqlWhereClasesExcluidas;
		zTotalize+= sqlGroup;
		
		String sqlFieldProm = "case ("+zTotalize+") when 0 then 0 else ((100.0* "+sqlOp+" ) / ("+zTotalize+") ) end as SUM_TUR_PNR_BOLETO_TARIFA";

		String zTotalize2 = sqlSelect;
		zTotalize2+= sqlField;
		zTotalize2+= sqlFrom;
		zTotalize2+= sqlWhere1;
		zTotalize2+= sqlWhereFecha;
		zTotalize2+= sqlWhereAerolineas;
		zTotalize2+= sqlWhereFareExcluidas;
		zTotalize2+= sqlWhereClasesExcluidas;
		zTotalize2+= sqlGroup;
	
		String zTotalize3 = sqlSelect;
		zTotalize3+= sqlField;
		zTotalize3+= sqlFrom;
		zTotalize3+= sqlWhere1;
		zTotalize3+= sqlWhereFecha;
		zTotalize3+= sqlWhereAerolineas;
		zTotalize3+= sqlWhereFareExcluidas;
		zTotalize3+= sqlWhereClasesExcluidasInv;
		zTotalize3+= sqlGroup;

		String zTotalize4 = sqlSelect;
		zTotalize4+= sqlField;
		zTotalize4+= sqlFrom;
		zTotalize4+= sqlWhere1;
		zTotalize4+= sqlWhereFecha;
		zTotalize4+= sqlWhereClases;
		zTotalize4+= sqlWhereTarifas;
		zTotalize4+= sqlWhereFareExcluidas;
		zTotalize4+= sqlWhereClasesExcluidas;
		zTotalize4+= sqlWhereGDS;
		zTotalize4+= sqlGroup;
		
		String sqlFieldPromTotal = "case ("+zTotalize2+") when 0 then 0 else ((100.0* "+sqlOp+" ) / ("+zTotalize2+") ) end as SUM_TUR_PNR_BOLETO_TARIFA";
	  String sqlFieldPromExcluidos = "case ("+zTotalize3+") when 0 then 0 else ((100.0* "+sqlOp+" ) / ("+zTotalize3+") ) end as SUM_TUR_PNR_BOLETO_TARIFA";
	  String sqlFieldPromGDSIncluidos = "case ("+zTotalize4+") when 0 then 0 else ((100.0* "+sqlOp+" ) / ("+zTotalize4+") ) end as SUM_TUR_PNR_BOLETO_TARIFA";

	  if (isPorcentajeSoloIdaSobreElTotal)
	  	 sqlFieldPromTotal = "case ("+zTotalize2+") when 0 then 0 else ((100.0* "+sqlOp+" ) / (0.8* ("+zTotalize2+")) ) end as SUM_TUR_PNR_BOLETO_TARIFA";//truchada, le resto un poco para que de cts, al parecer mezclan booking con boletos pero a mi se me complica ese mix
	  
		String sql = "";
		sql += sqlSelect;
		if (isPorcentajeSobreElTotal)
			sql += sqlFieldPromTotal;
		else if (isPorcentajeSoloIdaSobreElTotal)
			sql += sqlFieldPromTotal;
		else if (isPorcentajeSobreExcluidos)
			sql += sqlFieldPromExcluidos;
		else if (isPorcentajeSobreGDSIncluidos)
			sql += sqlFieldPromGDSIncluidos;
		else if (isMSTarifa || isMSCantidad)
			sql += sqlFieldProm;
		else
			sql += sqlField;

		sql += sqlFrom;
		sql += sqlWhere1;
		sql += sqlWhereFecha;
		sql += sqlWhereAerolineas;
		sql += sqlWhereClases;
		sql += sqlWhereTarifas;
		if (!isPorcentajeSobreExcluidos)
			sql += sqlWhereClasesExcluidas;
		if (isPorcentajeSobreGDSIncluidos)
			sql += sqlWhereGDS;
		sql += sqlWhereFareExcluidas;
		
		sql += sqlGroup;
	
		String sqlp = "";
		sqlp += sqlSelect;

			sqlp += sqlField;

		sqlp += sqlFrom;
		sqlp += sqlWhere1;
		sqlp += sqlWhereFecha;
		sqlp += sqlWhereAerolineas;
		sqlp += sqlWhereClases;
		sqlp += sqlWhereTarifas;
		if (!isPorcentajeSobreExcluidos)
			sqlp += sqlWhereClasesExcluidas;
		if (isPorcentajeSobreGDSIncluidos)
			sqlp += sqlWhereGDS;
		sqlp += sqlWhereFareExcluidas;
		
		sqlp += sqlGroup;
		calculedFromMSAerolinea = sqlFrom;
//		calculedWhereMSAerolinea = sqlWhere1 + sqlWhereFecha +  " AND (( TRUE "+sqlWhereAerolineasInv+") or ( TRUE " +sqlWhereClases + sqlWhereTarifas +" ))" + sqlWhereClasesExcluidas +sqlWhereGDS+ sqlWhereFareExcluidas;
		calculedWhereMSAerolinea = sqlWhere1 + sqlWhereFecha  + sqlWhereClasesExcluidas +sqlWhereGDS+ sqlWhereFareExcluidas;
			calculedGroupMSAerolinea = sqlGroup+", "+fieldAerolinea;

		calculedFromMSRuta = sqlFrom;
		calculedWhereMSRuta = sqlWhere1 + sqlWhereFecha+ sqlWhereAerolineas + sqlWhereClases + sqlWhereTarifas + sqlWhereClasesExcluidas+sqlWhereGDS+ sqlWhereFareExcluidas;
		calculedGroupMSRuta = sqlGroup+", "+fieldRuta;;

		calculedFromVendedor = sqlFrom + (sqlFrom.indexOf("TUR_PNR_BOLETO")!=-1?"":" JOIN TUR_PNR_BOLETO boletos ON TUR_PNR_BOOKING.interface_id=boletos.interface_id AND TUR_PNR_BOOKING.company=boletos.company ");
		calculedWhereVendedor = sqlWhere1 + sqlWhereFecha + sqlWhereAerolineas + sqlWhereClases + sqlWhereTarifas + sqlWhereClasesExcluidas+sqlWhereGDS+ sqlWhereFareExcluidas;
		calculedGroupVendedor = sqlGroup+", "+fieldVendedor;

		calculedFromCliente = sqlFrom + (sqlFrom.indexOf("TUR_PNR_BOLETO")!=-1?"":" JOIN TUR_PNR_BOLETO boletos ON TUR_PNR_BOOKING.interface_id=boletos.interface_id AND TUR_PNR_BOOKING.company=boletos.company ");
		calculedWhereCliente = sqlWhere1 + sqlWhereFecha + sqlWhereAerolineas + sqlWhereClases +sqlWhereTarifas+ sqlWhereClasesExcluidas+sqlWhereGDS+ sqlWhereFareExcluidas;
		calculedGroupCliente = sqlGroup+", "+fieldCliente;

		calculedFromMSClase = sqlFrom;
		calculedWhereMSClase = sqlWhere1 + sqlWhereFecha + sqlWhereAerolineas + sqlWhereClases + sqlWhereTarifas + sqlWhereClasesExcluidas+sqlWhereGDS+ sqlWhereFareExcluidas;
		calculedGroupMSClase = sqlGroup+", "+fieldClase;

		calculedFromDiario = sqlFrom;
		calculedWhereDiario = sqlWhere1 + sqlWhereFecha + sqlWhereAerolineas + sqlWhereClases + sqlWhereTarifas + sqlWhereClasesExcluidas+sqlWhereGDS+ sqlWhereFareExcluidas;
		calculedGroupDiario = sqlGroup+", "+fieldDiario;

		calculedFromHistorico = sqlFrom;
		calculedWhereHistorico = sqlWhere1 + " AND "+getFilterFecha(true) + sqlWhereAerolineas + sqlWhereClases + sqlWhereTarifas + sqlWhereClasesExcluidas+sqlWhereGDS+ sqlWhereFareExcluidas;
		calculedGroupHistorico = sqlGroup+", "+fieldHistorico1+","+fieldHistorico2;

		calculedFromRutas = sqlFrom;
		calculedFromRutas +=(sqlFrom.indexOf("ae_origen")!=-1?"":" JOIN TUR_AIRPORT ae_origen ON ae_origen.code=TUR_PNR_BOOKING.despegue  ");
		calculedFromRutas +=(sqlFrom.indexOf("ae_destino")!=-1?"":" JOIN TUR_AIRPORT ae_destino ON ae_destino.code=TUR_PNR_BOOKING.arrivo  ");
		calculedWhereRutas = sqlWhere1 + sqlWhereFecha + sqlWhereAerolineas + sqlWhereClases + sqlWhereTarifas + sqlWhereClasesExcluidas+sqlWhereGDS+ sqlWhereFareExcluidas;
		calculedGroupRutas = sqlGroup+", TUR_PNR_BOOKING.despegue, TUR_PNR_BOOKING.arrivo";

		String sqlg = "select     ";
		sqlg += " * ";
		sqlg += sqlFrom;
		sqlg += sqlWhere1;
		sqlg += sqlWhereFecha;
		sqlg += sqlWhereAerolineas;
		sqlg += sqlWhereClases;
		sqlg += sqlWhereTarifas;
		if (!isPorcentajeSobreExcluidos)
			sqlg += sqlWhereClasesExcluidas;
		if (!isPorcentajeSobreGDSIncluidos)
			sqlg += sqlWhereGDS;
		sqlg += sqlWhereFareExcluidas;
		
		String sqlH="";
		String agrupFecha = getGroupByFecha();
		String localFieldDiario;
    if (isBooking) {
    	localFieldDiario = fechaFijaEmision ? "TUR_PNR_BOOKING.fecha_emision" : "TUR_PNR_BOOKING.FechaDespegue";
    } else {
	  	localFieldDiario = fechaFijaEmision ? "TUR_PNR_BOLETO.creation_date" : "TUR_PNR_BOLETO.departure_date";
	  }
		String joinTotal=sqlFrom;
		String whereTotal="";
		String joinFiltrado=sqlFrom;
		String whereFiltrado="";
		String comment = (isVolado?"ES_VOLADO":"")+" "+(isBooking?"ES_BOOKING":"");

		if (isPorcentajeSobreElTotal) {
	
			whereTotal+= sqlWhere1;
			//whereTotal+= sqlWhereFecha;
			whereTotal+= "  ::FECHA:: ::DK:: "+"/* "+comment+" */";
			whereTotal += sqlWhereAerolineas;
			whereTotal += sqlWhereClases;
			whereTotal += sqlWhereTarifas;
			whereTotal += sqlWhereClasesExcluidas;
			whereTotal += sqlWhereFareExcluidas;
			whereFiltrado += sqlWhere1;
			whereFiltrado+= sqlWhereAerolineas;
			whereFiltrado+= sqlWhereFareExcluidas;
			whereFiltrado+= sqlWhereClasesExcluidas;

	
			
		}
		else if (isPorcentajeSobreExcluidos) {
			whereTotal+= sqlWhere1;
			//whereTotal+= sqlWhereFecha;
			whereTotal+= "  ::FECHA:: ::DK:: "+"/* "+comment+" */";
			whereTotal += sqlWhereAerolineas;
			whereTotal += sqlWhereClases;
			whereTotal += sqlWhereTarifas;
			whereTotal += sqlWhereFareExcluidas;

			whereFiltrado += sqlWhere1;
			whereFiltrado+= sqlWhereAerolineas;
			whereFiltrado+= sqlWhereFareExcluidas;
			whereFiltrado+= sqlWhereClasesExcluidasInv;
		}else if (isPorcentajeSobreGDSIncluidos) {
			whereTotal+= sqlWhere1;
			//whereTotal+= sqlWhereFecha;
			whereTotal+= "  ::FECHA:: ::DK:: "+"/* "+comment+" */";
			whereTotal += sqlWhereAerolineas;
			whereTotal += sqlWhereClases;
			whereTotal += sqlWhereTarifas;
			whereTotal += sqlWhereClasesExcluidas;
			whereTotal += sqlWhereGDS;
			whereTotal += sqlWhereFareExcluidas;


			whereFiltrado += sqlWhere1;
			whereFiltrado+= sqlWhereClases;
			whereFiltrado+= sqlWhereTarifas;
			whereFiltrado+= sqlWhereFareExcluidas;
			whereFiltrado+= sqlWhereClasesExcluidas;
			whereFiltrado+= sqlWhereGDS;
		}else if (isMSTarifa || isMSCantidad){
			whereTotal+= sqlWhere1;
			whereTotal+= "  ::FECHA:: ::DK:: "+"/* "+comment+" */";
			whereTotal+= sqlWhereFecha;
			whereTotal+= sqlWhereClases;
			whereTotal+= sqlWhereTarifas;
			whereTotal+= sqlWhereFareExcluidas;
			whereTotal+= sqlWhereClasesExcluidas;

			whereFiltrado += sqlWhere1;
			//whereFiltrado += sqlWhereFecha;
			whereFiltrado+= sqlWhereClases;
			whereFiltrado+= sqlWhereTarifas;
			whereFiltrado+= sqlWhereFareExcluidas;
			whereFiltrado+= sqlWhereClasesExcluidas;
		}else{
			whereTotal+= sqlWhere1;
			whereTotal+= "  ::FECHA:: ::DK:: "+"/* "+comment+" */";
			whereTotal += sqlWhereAerolineas;
			whereTotal += sqlWhereClases;
			whereTotal += sqlWhereTarifas;
			whereTotal += sqlWhereClasesExcluidas;
			whereTotal += sqlWhereFareExcluidas;

		}
		if (isPorcentajeSobreElTotal || isPorcentajeSobreExcluidos||isPorcentajeSobreGDSIncluidos||isMSTarifa || isMSCantidad) {

				sqlH+="WITH total_diario AS (\r\n"
					+ "		  SELECT\r\n"
					+ "		    DATE("+localFieldDiario+") AS fecha,\r\n"
					+ "		    "+agrupFecha+" AS periodo,\r\n"
		      + "		    "+sqlOp+" AS total_dia\r\n"
					+ "		  "+joinTotal+"\r\n"
					+ whereTotal 
					+ "		    AND "+localFieldDiario+" >= now() - INTERVAL '2 years'\r\n"
					+ "		  GROUP BY DATE("+localFieldDiario+"), "+agrupFecha+"\r\n"
					+ "		),\r\n"
					+ "		filtrados_diario AS (\r\n"
					+ "		  SELECT\r\n"
					+ "		    "+agrupFecha+" AS periodo,\r\n"
		      + "		    DATE("+localFieldDiario+") AS fecha,\r\n"
					+ "		    "+sqlOp+" AS filtro_dia\r\n"
					+ "		  "+joinFiltrado+"\r\n"
					+ whereFiltrado
					+ "		    AND "+localFieldDiario+" >= now() - INTERVAL '2 years'\r\n"
					+ "		  GROUP BY DATE("+localFieldDiario+"), "+agrupFecha+"\r\n"
						+ "		),\r\n"
					+ "	  acumulados AS (\r\n"
					+ "		  SELECT\r\n"
					+ "		    COALESCE(t.fecha, f.fecha) AS fecha,t.periodo as periodo,\r\n"
						+ "		    SUM(t.total_dia) OVER (PARTITION BY t.periodo ORDER BY COALESCE(t.fecha, f.fecha) ROWS UNBOUNDED PRECEDING) AS total_acum,\r\n"
					+ "		    SUM(f.filtro_dia) OVER (PARTITION BY t.periodo ORDER BY COALESCE(t.fecha, f.fecha) ROWS UNBOUNDED PRECEDING) AS filtro_acum\r\n"
					+ "		  FROM total_diario t\r\n"
					+ "		  FULL OUTER JOIN filtrados_diario f ON t.fecha = f.fecha\r\n"
					+ "		)\r\n"
					+ "	 ,\r\n"
					+ "base AS (\r\n"
					+ "  SELECT\r\n"
					+ "    fecha,\r\n"
					+ "    periodo,\r\n"
					+ "		  ROUND(100.0 * filtro_acum / NULLIF(total_acum, 0), 2) AS valor\r\n"
					+ "  FROM acumulados\r\n"
					+ ")";		
		} else {
			
			sqlH+="WITH total_diario AS (\r\n"
					+ "		  SELECT\r\n"
					+ "		    DATE("+localFieldDiario+") AS fecha,\r\n"
					+ "		    "+agrupFecha+" AS periodo,\r\n"
	        +" " +sqlOp+" AS total_dia\r\n"
					+ "		  "+joinTotal+"\r\n"
					+ whereTotal 
					+ "		    AND "+localFieldDiario+" >= now() - INTERVAL '2 years'\r\n"
					+ "		  GROUP BY DATE("+localFieldDiario+"), "+agrupFecha+"\r\n"
					+ "		),\r\n"
					+ "	  acumulados AS (\r\n"
					+ "		  SELECT\r\n"
					+ "		    t.fecha AS fecha, t.periodo as periodo, \r\n"
					+ "		    SUM(t.total_dia) OVER (PARTITION BY t.periodo ORDER BY t.fecha ROWS UNBOUNDED PRECEDING) AS total_acum\r\n"
					+ "		  FROM total_diario t\r\n"
					+ "		),\r\n"
					+ "	  base AS (\r\n"
					+ "  SELECT\r\n"
					+ "    fecha,\r\n"
					+ "    periodo,\r\n"
					+ "    ROUND(NULLIF(total_acum, 0), 2) AS valor\r\n"
					+ "  FROM acumulados\r\n"
					+ ")";
		}
		outputSql=sql;
		outputSqlHistorico=sqlH;
		outputSqlDetalle=sqlg;
		outputField="SUM_TUR_PNR_BOLETO_TARIFA";
		outputClass=GuiBookings.class.getName();
		outputTitle="BOOKING "+rutaDef;
		
		
		if (isMSTarifa||isMSCantidad||isPorcentajeSobreElTotal || isPorcentajeSobreExcluidos || 
				isPorcentajeSobreGDSIncluidos || isPorcentajeSoloIdaSobreElTotal) {
			if (isPorcentajeSobreElTotal)
				outputSqlAux1 = zTotalize2;
			else if (isPorcentajeSobreExcluidos)
				outputSqlAux1 = zTotalize3;
			else if (isPorcentajeSobreGDSIncluidos)
				outputSqlAux1 = zTotalize4;
			else if (isMSTarifa || isMSCantidad)
				outputSqlAux1 = zTotalize;

			outputSqlAux2 =sqlp;
			outputDescrSqlAux1 =(tipo==META?"Meta":"Base")+" total ventas";
			outputDescrSqlAux2 =(tipo==META?"Meta":"Base")+" total condición";
			
		}
		if (outputTitle.length()>400) 
			outputTitle = outputTitle.substring(0,390)+"...";
		
	}
	
	public void autogenerarReservas() throws Exception {
		String rutaDef = "";

	
		
		String sqlFromReserva = " from  TUR_PNR_RESERVA   ";
		
		String sqlWhereR="";
		String sqlWhereReserva="";
		String sqlWhere1Reserva="";
		String sqlWhereFechaReserva="";
		String sqlWhereAerolineasReserva="";
		String sqlWhereClasesReserva="";
		String sqlWhereClasesExcluidasReserva="";
		String sqlWhereClasesExcluidasInvReserva="";
		
		if (hasOrigenContinente||hasOrigenZona||(isViceversa&&(hasDestinoContinente||hasDestinoZona))) {
			sqlFromReserva+=" JOIN TUR_AIRPORT ae_origen ON ae_origen.code=TUR_PNR_RESERVA.aeropuerto_origen  ";
			if (hasOrigenContinente||hasOrigenZona||(isViceversa&&(hasDestinoContinente||hasDestinoZona))) {
				sqlFromReserva+= " JOIN reg_pais airorpais ON ae_origen.country=airorpais.pais ";
			}
		}
		if (hasDestinoContinente||hasDestinoZona||(isViceversa&&(hasOrigenContinente||hasOrigenZona))) {
			sqlFromReserva+=" JOIN TUR_AIRPORT ae_destino ON ae_destino.code=TUR_PNR_RESERVA.aeropuerto_destino  ";
			if (hasDestinoContinente||hasDestinoZona||(isViceversa&&(hasOrigenContinente||hasOrigenZona))) {
				sqlFromReserva+= " JOIN reg_pais airdestpais ON ae_destino.country=airdestpais.pais ";
			}
		}
		sqlWhere1Reserva += " WHERE (TUR_PNR_RESERVA.company= '" + detalle.getCompany() + "')  AND creation_date  > current_date  - interval '::DAYS:: day' ";	
		sqlWhereFechaReserva += getFilterFechaReserva(false);

		if (detalle.hasMultiAerolineas()) {
			sqlWhereAerolineasReserva = " AND TUR_PNR_RESERVA.codigoAEROLINEA in ( " + detalle.getAerolineas() + ") ";
		} else {
			sqlWhereAerolineasReserva = " AND TUR_PNR_RESERVA.codigoAEROLINEA = '" + detalle.getIdAerolinea() + "' ";
		}
		
		if (detalle.hasMultiAerolineasPlaca()) {
			sqlWhereAerolineasReserva += " AND TUR_PNR_RESERVA.codigoAEROLINEA in ( " + detalle.getAerolineasPlaca() + ") ";
		}
	

		if (detalle.getNoAerolineas() != null && !detalle.getNoAerolineas().trim().equals("")) {
			sqlWhere1Reserva += " AND TUR_PNR_RESERVA.codigoAEROLINEA not in ( " + detalle.getNoAerolineas() + ") ";
		}
		fieldAerolinea= "TUR_PNR_BOLETO.codigoAEROLINEA";
		fieldRuta = "TUR_PNR_BOLETO.mini_route";
		fieldVendedor = "TUR_PNR_BOLETO.vendedor";
		fieldCliente = "TUR_PNR_BOLETO.customer_id_reducido";
		fieldClase = "TUR_PNR_BOLETO.clase";
		fieldRutaOrigen = "TUR_PNR_BOLETO.aeropuerto_origen";
		fieldRutaDestino = "TUR_PNR_BOLETO.aeropuerto_destino";
		fieldRutaOrigenGeo = "ae_origen.geo_position";
		fieldRutaDestinoGeo = "ae_destino.geo_position";
		calculedFromDetalle = "TUR_PNR_BOLETO.*";
	
		if (detalle.hasIatas()) {
			sqlWhere1Reserva += " AND TUR_PNR_RESERVA.nro_iata in ( " + detalle.getIatas() + ") ";
		} if (detalle.hasNoIatas()) {
			sqlWhere1Reserva += " AND NOT(TUR_PNR_RESERVA.nro_iata in ( " + detalle.getNoIatas() + ")) ";
		}
	

		String sqlANDR = "";
		String sqlOROrigenR = "";
		String sqlORDestinoR = "";
		String sqlANDViceversaR = "";
		String sqlOROrigenViceversaR = "";
		String sqlORDestinoViceversaR = "";
		if (hasGDSIncluded) {
			sqlWhere1Reserva += " AND TUR_PNR_RESERVA.gds in ( " + getGDSIncluded + ") ";
		} if (hasGDSExcluded) {
			sqlWhere1Reserva += " AND NOT(TUR_PNR_RESERVA.gds in ( " + getGDSExcludes + ")) ";
		}
		if (hasClasesExcluded) {
			String sqlTCR = "";
			StringTokenizer toksTC = new StringTokenizer(getClasesExcludes, ",;");
			while (toksTC.hasMoreTokens()) {
				String s = toksTC.nextToken();
				int pos = s.indexOf(":");
				if (pos!=-1) {
					String a = s.substring(0,pos);
					String b = s.substring(pos+1);
					String aerolinea =a.trim();
					String clase=b.trim();
					if (b.length()>=2) {
						aerolinea=b;
						clase=a;
					}
					if (aerolinea.startsWith("@")) {
						String pais = aerolinea.substring(1);
						sqlTCR += (sqlTCR.equals("") ? "" : " AND ") + " (TUR_PNR_RESERVA.pais_origen <> '" + pais + "'  OR (TUR_PNR_RESERVA.pais_origen = '" + pais + "' AND TUR_PNR_RESERVA.clase <> '" + clase + "')) ";

					} else {
						sqlTCR += (sqlTCR.equals("") ? "" : " AND ") + " (TUR_PNR_RESERVA.codigoAEROLINEA <> '" + aerolinea + "'  OR (TUR_PNR_RESERVA.codigoAEROLINEA = '" + aerolinea + "' AND TUR_PNR_RESERVA.clase <> '" + clase + "')) ";
					}
				} else {
					sqlTCR += (sqlTCR.equals("") ? "" : " AND ") + " (TUR_PNR_RESERVA.clase <> '" + s.trim() + "') ";
				}
				rutaDef += (rutaDef.isEmpty() ? "" : ",") + s.trim().replace("'", "");

			}
			if (!sqlTCR.equals("")) 
				sqlWhereClasesExcluidasReserva += (" AND ") + "("+sqlTCR+")";
		}
		if (hasClasesExcluded) {
			String sqlTCR = "";
			StringTokenizer toksTC = new StringTokenizer(getClasesExcludes, ",;");
			while (toksTC.hasMoreTokens()) {
				String s = toksTC.nextToken();
				int pos = s.indexOf(":");
				if (pos!=-1) {
					String a = s.substring(0,pos);
					String b = s.substring(pos+1);
					String aerolinea =a.trim();
					String clase=b.trim();
					if (b.length()>=2) {
						aerolinea=b;
						clase=a;
					}
					if (aerolinea.startsWith("@")) {
						String pais = aerolinea.substring(1);
						sqlTCR += (sqlTCR.equals("") ? "" : " OR ") + " (TUR_PNR_RESERVA.pais_origen = '" + pais + "'  AND (TUR_PNR_RESERVA.clase = '" + clase + "')) ";
						
					} else {
						sqlTCR += (sqlTCR.equals("") ? "" : " OR ") + " (TUR_PNR_RESERVA.codigoAEROLINEA = '" + aerolinea + "'  AND (TUR_PNR_RESERVA.clase = '" + clase + "')) ";
					}
				} else {
					sqlTCR += (sqlTCR.equals("") ? "" : " OR ") + " (TUR_PNR_RESERVA.clase = '" + s.trim() + "') ";
				}
				rutaDef += (rutaDef.isEmpty() ? "" : ",") + s.trim().replace("'", "");

			}
			if (!sqlTCR.equals(""))
				sqlWhereClasesExcluidasInvReserva += (" AND ") + "("+sqlTCR+")";
		}
		if (hasClasesIncluded) {
			String sqlORR = "";
			
			StringTokenizer toksTC = new StringTokenizer(getClasesIncluded, ",;");
			while (toksTC.hasMoreTokens()) {
				String s = toksTC.nextToken();
				int pos = s.indexOf(":");
				if (pos!=-1) {
					String a = s.substring(0,pos);
					String b = s.substring(pos+1);
					String aerolinea =a.trim();
					String clase=b.trim();
					if (b.length()>=2) {
						aerolinea=b;
						clase=a;
					}
					if (aerolinea.startsWith("@")) {
						String pais = aerolinea.substring(1);
						sqlORR += (sqlORR.equals("") ? "" : " OR ") + " ((TUR_PNR_RESERVA.pais_origen = '" + pais + "'  AND TUR_PNR_RESERVA.clase ='" + clase + "')) ";

					} else {
						sqlORR += (sqlORR.equals("") ? "" : " OR ") + " ((TUR_PNR_RESERVA.codigoAEROLINEA = '" + aerolinea + "'  AND TUR_PNR_RESERVA.clase ='" + clase + "')) ";
					}
				} else {
					sqlORR += (sqlORR.equals("") ? "" : " OR ") + " (TUR_PNR_RESERVA.clase ='" + s.trim() + "') ";
				}
				rutaDef += (rutaDef.isEmpty() ? "" : ",") + s.trim().replace("'", "");

			}
			if (!sqlORR.equals(""))
				sqlWhereClasesReserva += (" AND ") + "("+sqlORR+")";
		}
		if (hasFareBasicExcluded) {
	    String sqlTCR = "";
	    StringTokenizer toksTCR = new StringTokenizer(getFareBasicExcludes, ",;");
	    while (toksTCR.hasMoreTokens()) {
	        String s = toksTCR.nextToken();
	        if (s.matches("\\d+:'\\w+'")) { // Detectar el formato 7:'AB'
	            String[] parts = s.split(":'");
	            int position = Integer.parseInt(parts[0]);
	            String substring = parts[1].replace("'", ""); // Eliminar comillas simples
	            sqlTCR += (sqlTCR.isEmpty() ? "" : " OR ") +
	                      " (SUBSTRING(TUR_PNR_RESERVA.fare_intinerary FROM " + position + " FOR " + substring.length() + ") <> '" + substring + "') ";
	        } else {
	            // Caso genérico, buscar coincidencia parcial
	            sqlTCR += (sqlTCR.isEmpty() ? "" : " OR ") +
	                      " (TUR_PNR_RESERVA.fare_intinerary LIKE '%" + s.trim() + "%') ";
	        }
	        rutaDef += (rutaDef.isEmpty() ? "" : ",") + s.trim().replace("'", "");

	    }
	    if (!sqlTCR.isEmpty())
	        sqlANDR += (sqlANDR.isEmpty() ? "" : " AND ") + "(" + sqlTCR + ")";
	}

	if (hasFareBasicIncluded) {
	    String sqlTCR = "";
	    StringTokenizer toksTC = new StringTokenizer(getFareBasicIncluded, ",;");
	    while (toksTC.hasMoreTokens()) {
	        String s = toksTC.nextToken();
	        if (s.matches("\\d+:'\\w+'")) { // Detectar el formato 7:'AB'
	            String[] parts = s.split(":'");
	            int position = Integer.parseInt(parts[0]);
	            String substring = parts[1].replace("'", ""); // Eliminar comillas simples
	            sqlTCR += (sqlTCR.isEmpty() ? "" : " OR ") +
	                      " (SUBSTRING(TUR_PNR_RESERVA.fare_intinerary FROM " + position + " FOR " + substring.length() + ") = '" + substring + "') ";
	        } else {
	            // Caso genérico, buscar coincidencia parcial
	            sqlTCR += (sqlTCR.isEmpty() ? "" : " OR ") +
	                      " (TUR_PNR_RESERVA.fare_intinerary LIKE '%" + s.trim() + "%') ";
	        }
	        rutaDef += (rutaDef.isEmpty() ? "" : ",") + s.trim().replace("'", "");

	    }
	    if (!sqlTCR.isEmpty())
	        sqlWhereClasesReserva += " AND (" + sqlTCR + ")";
	}

		if (hasRutas) {
			String sqlORR = "";
			StringTokenizer toksTC = new StringTokenizer(getRutas, ",;");
			while (toksTC.hasMoreTokens()) {
				String s = toksTC.nextToken();
				if (s.trim().indexOf("%")!=-1) {
					sqlORR += (sqlORR.equals("") ? "" : " OR ") + " (TUR_PNR_RESERVA.air_intinerary  like '" + s.trim() + "') ";
				} else {
					String sqlAND2R="";
					String searchPais="";
					String searchAero="";
					String searchMetro="";
					boolean anyPais=false;
					boolean anyAero=false;
					boolean anyMetro=false;
					StringTokenizer toksTCI = new StringTokenizer(s, "-");
					while (toksTCI.hasMoreTokens()) {
						String nodo = toksTCI.nextToken();
						if (nodo.length()==2) { //pais
							searchPais += (searchPais.equals("")?"":"-")+nodo.toUpperCase();
							searchAero += (searchAero.equals("")?"":"-")+"___";
							searchMetro+= (searchMetro.equals("")?"":"-")+"___";
							anyPais=true;
						}
						else if (nodo.length()==3) { // aeropuerto
							boolean isMetro = isAreaMetro(nodo.toUpperCase());
							searchPais += (searchPais.equals("")?"":"-")+"__";
							if (isMetro) {
								anyMetro=true;
								searchMetro += (searchMetro.equals("")?"":"-")+nodo.toUpperCase();
								searchAero += (searchAero.equals("")?"":"-")+"___";
							} else {
								anyAero=true;
								searchAero += (searchAero.equals("")?"":"-")+nodo.toUpperCase();
								searchMetro += (searchMetro.equals("")?"":"-")+"___";
							}
						}	else
							continue;
					}
					if (anyAero && anyPais && anyMetro) {
						sqlAND2R += (sqlAND2R.equals("") ? "" : " AND ") + " (TUR_PNR_RESERVA.air_pais_intinerary  like '" + searchPais + "') ";
						sqlAND2R += (sqlAND2R.equals("") ? "" : " AND ") + " (TUR_PNR_RESERVA.air_intinerary  like '" + searchAero + "') ";
						sqlAND2R += (sqlAND2R.equals("") ? "" : " AND ") + " (TUR_PNR_RESERVA.air_gen_intinerary  like '" + searchMetro + "') ";
					}	else if (anyAero && anyPais && !anyMetro) {
						sqlAND2R += (sqlAND2R.equals("") ? "" : " AND ") + " (TUR_PNR_RESERVA.air_pais_intinerary  like '" + searchPais + "') ";
						sqlAND2R += (sqlAND2R.equals("") ? "" : " AND ") + " (TUR_PNR_RESERVA.air_intinerary  like '" + searchAero + "') ";
					}	else if (anyAero && !anyPais && anyMetro) {
						sqlAND2R += (sqlAND2R.equals("") ? "" : " AND ") + " (TUR_PNR_RESERVA.air_intinerary  like '" + searchAero + "') ";
						sqlAND2R += (sqlAND2R.equals("") ? "" : " AND ") + " (TUR_PNR_RESERVA.air_gen_intinerary  like '" + searchMetro + "') ";
					}	else if (!anyAero && anyPais && anyMetro) {
						sqlAND2R += (sqlAND2R.equals("") ? "" : " AND ") + " (TUR_PNR_RESERVA.air_pais_intinerary  like '" + searchPais + "') ";
						sqlAND2R += (sqlAND2R.equals("") ? "" : " AND ") + " (TUR_PNR_RESERVA.air_gen_intinerary  like '" + searchMetro + "') ";
					} else if (anyAero) {
						sqlAND2R += (sqlAND2R.equals("") ? "" : " AND ") + " (TUR_PNR_RESERVA.air_intinerary  = '" + s.trim() + "') ";
					}	else if (anyPais) {
						sqlAND2R += (sqlAND2R.equals("") ? "" : " AND ") + " (TUR_PNR_RESERVA.air_pais_intinerary  = '" + s.trim() + "') ";
					}	else if (anyMetro) {
						sqlAND2R += (sqlAND2R.equals("") ? "" : " AND ") + " (TUR_PNR_RESERVA.air_gen_intinerary  = '" + s.trim() + "') ";
					}
					if (!sqlAND2R.equals(""))
						sqlORR+= (sqlORR.equals("") ? "" : " OR ") + sqlAND2R;
				
				
				}
//				rutaDef+=(rutaDef.equals("")?"":",")+s.trim();
			}
			if (!sqlORR.equals(""))
				sqlANDR += (sqlANDR.equals("") ? "" : " AND ") + "("+sqlORR+")";
		}

		if (hasDomesticInternacional) {
			if (isDomestic) {
				sqlANDR+= (sqlANDR.equals("") ? "" : " AND ") + "(internacional_descr='Domestic')";
			}
			if (isInternational) {
				sqlANDR+= (sqlANDR.equals("") ? "" : " AND ") + "(internacional_descr='International')";
			}
			if (isViceversa) {
				if (isDomestic) {
					sqlANDViceversaR+= (sqlANDViceversaR.equals("") ? "" : " AND ") + "(internacional_descr='Domestic')";
				}
				if (isInternational) {
					sqlANDViceversaR+= (sqlANDViceversaR.equals("") ? "" : " AND ") + "(internacional_descr='International')";	
				}
			}
		}	
		if (hasOrigenAeropuerto) {
			String sqlORR = "";
			String sqlORVicR = "";
			JIterator<String> it = getOrigenAeropuerto.getIterator();
			while (it.hasMoreElements()) {
				String s = it.nextElement();
				sqlORR += (sqlORR.equals("") ? "" : " OR ") + " (TUR_PNR_RESERVA.aeropuerto_origen = '" + s.trim() + "') ";
				if (isViceversa) {
					sqlORVicR += (sqlORVicR.equals("") ? "" : " OR ") + " (TUR_PNR_RESERVA.aeropuerto_destino = '" + s.trim() + "') ";
				}
				rutaDef+=(rutaDef.equals("")?"":",")+s.trim();

			}
			if (!sqlORR.equals(""))
				sqlOROrigenR += (sqlOROrigenR.equals("") ? "" : " OR ")  + "("+sqlORR+")";
			if (!sqlORVicR.equals(""))
				sqlOROrigenViceversaR += (sqlOROrigenViceversaR.equals("") ? "" : " OR ")  + "("+sqlORVicR+")";
		}
		if (hasOrigenContinente) {
			String sqlORR = "";
			String sqlORVicR = "";
			JIterator<String> it = getOrigenContinente.getIterator();
			while (it.hasMoreElements()) {
				String s = it.nextElement();
				sqlORR += (sqlORR.equals("") ? "" : " OR ") + " (airorpais.continente = '" + s.trim() + "') ";
				if (isViceversa) {
					sqlORVicR += (sqlORVicR.equals("") ? "" : " OR ") + " (airdestpais.continente = '" + s.trim() + "') ";
				}
				rutaDef+=(rutaDef.equals("")?"":",")+s.trim();
			}
			if (!sqlORR.equals(""))
				sqlOROrigenR += (sqlOROrigenR.equals("") ? "" : " OR ")  + "("+sqlORR+")";
			if (!sqlORVicR.equals(""))
				sqlOROrigenViceversaR += (sqlOROrigenViceversaR.equals("") ? "" : " OR ")  + "("+sqlORVicR+")";
		}
		if (hasOrigenZona) {
			String sqlORR = "";
			String sqlORVicR = "";
			JIterator<String> it = getOrigenZona.getIterator();
			while (it.hasMoreElements()) {
				String s = it.nextElement();
				sqlORR += (sqlORR.equals("") ? "" : " OR ") + " (airorpais.region = '" + s.trim() + "') ";
				if (isViceversa) {
					sqlORVicR += (sqlORVicR.equals("") ? "" : " OR ") + " (airdestpais.region = '" + s.trim() + "') ";
				}				
				rutaDef+=(rutaDef.equals("")?"":",")+s.trim();
			}
			if (!sqlORR.equals(""))
				sqlOROrigenR += (sqlOROrigenR.equals("") ? "" : " OR ")  + "("+sqlORR+")";
			if (!sqlORVicR.equals(""))
				sqlOROrigenViceversaR += (sqlOROrigenViceversaR.equals("") ? "" : " OR ")  + "("+sqlORVicR+")";
		}
		if (hasOrigenPais) {
			String sqlORR = "";
			String sqlORVicR = "";
			JIterator<String> it = getOrigenPais.getIterator();
			while (it.hasMoreElements()) {
				String s = it.nextElement();
				sqlORR += (sqlORR.equals("") ? "" : " OR ") + " ( TUR_PNR_RESERVA.pais_origen = '" + s.trim() + "') ";
				if (isViceversa) {
					sqlORVicR += (sqlORVicR.equals("") ? "" : " OR ") + " (TUR_PNR_RESERVA.pais_destino = '" + s.trim() + "') ";
				}
				rutaDef+=(rutaDef.equals("")?"":",")+s.trim();
			}
			if (!sqlORR.equals(""))
				sqlOROrigenR += (sqlOROrigenR.equals("") ? "" : " OR ") + "("+sqlORR+")";
			if (!sqlORVicR.equals(""))
				sqlOROrigenViceversaR += (sqlOROrigenViceversaR.equals("") ? "" : " OR ")  + "("+sqlORVicR+")";
		}
		
		if (!sqlOROrigenR.equals(""))
			sqlANDR+= (sqlANDR.equals("") ? "" : " AND ") + (isOrigenNegado?" NOT ":"")+"("+sqlOROrigenR+")";
		if (!sqlOROrigenViceversaR.equals(""))
			sqlANDViceversaR+= (sqlANDViceversaR.equals("") ? "" : " AND ") + (isOrigenNegado?" NOT ":"")+"("+sqlOROrigenViceversaR+")";
		
		if (hasDestinoAeropuerto) {
			String sqlORR = "";
			String sqlORVicR = "";
			JIterator<String> it = getDestinoAeropuerto.getIterator();
			while (it.hasMoreElements()) {
				String s = it.nextElement();
				sqlORR += (sqlORR.equals("") ? "" : " OR ") + " (TUR_PNR_RESERVA.aeropuerto_destino = '" + s.trim() + "') ";
				if (isViceversa) {
					sqlORVicR += (sqlORVicR.equals("") ? "" : " OR ") + " (TUR_PNR_RESERVA.aeropuerto_origen = '" + s.trim() + "') ";
				}
				rutaDef+=(rutaDef.equals("")?"":",")+s.trim();
			}
			if (!sqlORR.equals(""))
				sqlORDestinoR += (sqlORDestinoR.equals("") ? "" : " OR ") + "("+sqlORR+")";
			if (!sqlORVicR.equals(""))
				sqlORDestinoViceversaR += (sqlORDestinoViceversaR.equals("") ? "" : " OR ")  + "("+sqlORVicR+")";
		}
		if (hasDestinoContinente) {
			String sqlORR = "";
			String sqlORVicR = "";
			JIterator<String> it = getDestinoContinente.getIterator();
			while (it.hasMoreElements()) {
				String s = it.nextElement();
				sqlORR += (sqlORR.equals("") ? "" : " OR ") + " (airdestpais.continente = '" + s.trim() + "') ";
				if (isViceversa) {
					sqlORVicR += (sqlORVicR.equals("") ? "" : " OR ") + " (airorpais.continente = '" + s.trim() + "') ";
				}
				rutaDef+=(rutaDef.equals("")?"":",")+s.trim();
			}
			if (!sqlORR.equals(""))
				sqlORDestinoR += (sqlORDestinoR.equals("") ? "" : " OR ")  + "("+sqlORR+")";
			if (!sqlORVicR.equals(""))
				sqlORDestinoViceversaR += (sqlORDestinoViceversaR.equals("") ? "" : " OR ")  + "("+sqlORVicR+")";
		}
		if (hasDestinoZona) {
			String sqlORR = "";
			String sqlORVicR = "";
			JIterator<String> it = getDestinoZona.getIterator();
			while (it.hasMoreElements()) {
				String s = it.nextElement();
				sqlORR += (sqlORR.equals("") ? "" : " OR ") + " (airdestpais.region = '" + s.trim() + "') ";
				if (isViceversa) {
					sqlORVicR += (sqlORVicR.equals("") ? "" : " OR ") + " (airorpais.region = '" + s.trim() + "') ";
				}
				rutaDef+=(rutaDef.equals("")?"":",")+s.trim();
			}
			if (!sqlORR.equals(""))
				sqlORDestinoR += (sqlORDestinoR.equals("") ? "" : " OR ")  + "("+sqlORR+")";
			if (!sqlORVicR.equals(""))
				sqlORDestinoViceversaR += (sqlORDestinoViceversaR.equals("") ? "" : " OR ")  + "("+sqlORVicR+")";
		}
		if (hasDestinoPais) {
			String sqlORR = "";
			String sqlORVicR = "";
			JIterator<String> it = getDestinoPais.getIterator();
			while (it.hasMoreElements()) {
				String s = it.nextElement();
				sqlORR += (sqlORR.equals("") ? "" : " OR ") + " ( TUR_PNR_RESERVA.pais_destino = '" + s.trim() + "') ";
				if (isViceversa) {
					sqlORVicR += (sqlORVicR.equals("") ? "" : " OR ") + " (TUR_PNR_RESERVA.pais_origen = '" + s.trim() + "') ";
					
				}
				rutaDef+=(rutaDef.equals("")?"":",")+s.trim();
			}
			if (!sqlORR.equals(""))
				sqlORDestinoR += (sqlORDestinoR.equals("") ? "" : " OR ") + "("+sqlORR+")";
			if (!sqlORVicR.equals(""))
				sqlORDestinoViceversaR += (sqlORDestinoViceversaR.equals("") ? "" : " OR ")  + "("+sqlORVicR+")";
		}
		if (!sqlORDestinoR.equals(""))
			sqlANDR+= (sqlANDR.equals("") ? "" : " AND ") + (isDestinoNegado?" NOT ":"")+"("+sqlORDestinoR+")";
		if (!sqlORDestinoViceversaR.equals(""))
			sqlANDViceversaR+= (sqlANDViceversaR.equals("") ? "" : " AND ") + (isDestinoNegado?" NOT ":"")+"("+sqlORDestinoViceversaR+")";


		if (!sqlANDViceversaR.equals("") && isRequiredAnd() ) {
			sqlWhereR += " AND ((" + sqlANDR + ") AND (" + sqlANDViceversaR + "))";
		} else if (!sqlANDViceversaR.equals("")) {
			sqlWhereR += " AND ((" + sqlANDR + ") OR (" + sqlANDViceversaR + "))";
		} else if (!sqlANDR.equals("")) {
			sqlWhereR += " AND (" + sqlANDR + ")";
			
		}

		calculedFromReservas = sqlFromReserva;
		calculedWhereReservas = sqlWhere1Reserva + sqlWhereFechaReserva + sqlWhereAerolineasReserva  + sqlWhereClasesReserva  + sqlWhereClasesExcluidasReserva  + sqlWhereReserva + sqlWhereClasesExcluidasInvReserva  +sqlWhereR;

		
		String sqlr = "select     ";
		sqlr += " * ";
		sqlr += calculedFromReservas;
		sqlr += calculedWhereReservas;

		outputSqlReserva=sqlr;
	}
		
	
	public String getReservas() throws Exception {
		String select="";

		select += "Select * ";
		select += "From tur_pnr_reserva ";
		select += calculedWhereReservas;
		return select;
	}	

	public String getEstadisticaMSAerolinea() throws Exception {
		String select="";

		select += "Select '' as agrupador1, "+fieldAerolinea+" as agrupador2, '' as agrupador3, '' as agrupador4, "+calculedFieldMSAerolinea+" as cantidad1, "+calculedField2MSAerolinea+" as cantidad2, 0 as cantidad3, 0 as cantidad4, 0 as cantidad5, 0 as porcentaje, 0 as factor ";
		select += calculedFromMSAerolinea;
		select += calculedWhereMSAerolinea;
		select += calculedGroupMSAerolinea;
		select += " order by "+calculedFieldMSAerolinea+" DESC" ;
		return select;
	}
	public String getEstadisticaMSAerolineaDetalle() throws Exception {
		String select="";
		select += "Select "+calculedFromDetalle;
		select += calculedFromMSAerolinea;
		select += calculedWhereMSAerolinea;
		select += " AND "+fieldAerolinea+"='::agrupado2::' ";
		return select;
	}
	public String getEstadisticaMSRuta() throws Exception {
		String select="";

		select += "Select '' as agrupador1, "+fieldRuta+" as agrupador2, '' as agrupador3, '' as agrupador4, "+calculedFieldMSRuta+" as cantidad1, "+calculedField2MSRuta+" as cantidad2, 0 as cantidad3, 0 as cantidad4, 0 as cantidad5, 0 as porcentaje, 0 as factor ";
		select += calculedFromMSRuta;
		select += calculedWhereMSRuta;
		select += calculedGroupMSRuta;
		select += " order by "+calculedFieldMSRuta+" DESC" ;
		return select;
	}
	public String getEstadisticaMSRutaDetalle() throws Exception {
		String select="";
		select += "Select "+calculedFromDetalle;
		select += calculedFromMSRuta;
		select += calculedWhereMSRuta;
		select += " AND "+fieldRuta+"='::agrupado2::' ";
		return select;
	}
	public String getEstadisticaMSClase() throws Exception {
		String select="";

		select += "Select '' as agrupador1, "+fieldClase+" as agrupador2, '' as agrupador3, '' as agrupador4, "+calculedFieldMSClase+" as cantidad1,  "+calculedField2MSClase+" as cantidad2, 0 as cantidad3, 0 as cantidad4, 0 as cantidad5, 0 as porcentaje, 0 as factor ";
		select += calculedFromMSClase;
		select += calculedWhereMSClase;
		select += calculedGroupMSClase;
		select += " order by "+calculedFieldMSClase+" DESC" ;
		return select;
	}
	public String getEstadisticaMSClaseDetalle() throws Exception {
		String select="";
		select += "Select "+calculedFromDetalle;
		select += calculedFromMSClase;
		select += calculedWhereMSClase;
		select += " AND "+fieldClase+"='::agrupado2::' ";
		return select;
	}

	public String getEstadisticaRutas() throws Exception {
		String select="";

		select += "Select "+fieldRutaOrigen+" as agrupador1, "+fieldRutaDestino+" as agrupador2, max("+fieldRutaOrigenGeo+") as agrupador3, max("+fieldRutaDestinoGeo+") as agrupador4, "+calculedFieldRutas+" as cantidad1,  "+calculedField2Rutas+" as cantidad2, 0 as cantidad3, 0 as cantidad4, 0 as cantidad5, 0 as porcentaje, 0 as factor ";
		select += calculedFromRutas;
		select += calculedWhereRutas;
		select += calculedGroupRutas;
		select += " order by "+calculedFieldRutas+" DESC,"+fieldRutaOrigen+","+fieldRutaDestino;
		return select;
	}

	public String getEstadisticaRutasDetalle() throws Exception {
		String select="";
		select += "Select "+calculedFromDetalle;
		select += calculedFromRutas;
		select += calculedWhereRutas;
		select += " AND "+fieldRutaOrigen+"='::agrupado1::' AND "+fieldRutaDestino+"='::agrupado2::'  ";
		return select;
	}
	public String getEstadisticaDiario() throws Exception {
		String select="";

		select += "Select '' as agrupador1, date("+fieldDiario+") as agrupador2, '' as agrupador3, '' as agrupador4, "+calculedFieldDiario+" as cantidad1, "+calculedField2Diario+" as cantidad2, 0 as cantidad3, 0 as cantidad4, 0 as cantidad5, 0 as porcentaje, 0 as factor ";
		select += calculedFromDiario;
		select += calculedWhereDiario;
		select += calculedGroupDiario;
		select += " order by "+fieldDiario+" ASC" ;
		return select;
	}
	public String getEstadisticaDiarioDetalle() throws Exception {
		String select="";
		select += "Select "+calculedFromDetalle;
		select += calculedFromDiario;
		select += calculedWhereDiario;
		select += " AND "+fieldDiario+"='::agrupado2::' ";
		return select;
	}
	public String getEstadisticaHistorico() throws Exception {
		String select="";

		select += "Select "+fieldHistorico1+" as agrupador1, "+fieldHistorico2+" as agrupador2, '' as agrupador3, '' as agrupador4, "+calculedFieldHistorico+" as cantidad1, "+calculedField2Historico+" as cantidad2, 0 as cantidad3, 0 as cantidad4, 0 as cantidad5, 0 as porcentaje, 0 as factor ";
		select += calculedFromHistorico;
		select += calculedWhereHistorico;
		select += calculedGroupHistorico;
		select += " order by "+fieldHistorico1+" ASC, "+fieldHistorico2+" ASC" ;
		select = select.replace("::FECHA::", "");
		return select;
	}
	public String getEstadisticaHistoricoDetalle() throws Exception {
		String select="";
		select += "Select "+calculedFromDetalle;
		select += calculedFromHistorico;
		select += calculedWhereHistorico;
		select += " AND "+fieldHistorico1+"='::agrupado1::'  AND "+fieldHistorico2+"='::agrupado2::' ";
		select = select.replace("::FECHA::", "");
		return select;
	}

	public String getEstadisticaVendedor() throws Exception {
		String select="";

		select += "Select '' as agrupador1, "+fieldVendedor+" as agrupador2, '' as agrupador3, '' as agrupador4, "+calculedFieldVendedor+" as cantidad1, "+calculedField2Vendedor+" as cantidad2, 0 as cantidad3, 0 as cantidad4, 0 as cantidad5, 0 as porcentaje, 0 as factor ";
		select += calculedFromVendedor;
		select += calculedWhereVendedor;
		select += calculedGroupVendedor;
		select += " order by "+calculedFieldVendedor+" DESC" ;
		return select;
	}
	public String getEstadisticaVendedorDetalle() throws Exception {
		String select="";
		select += "Select "+calculedFromDetalle;
		select += calculedFromVendedor;
		select += calculedWhereVendedor;
		select += " AND "+fieldVendedor+"='::agrupado2::' ";
		return select;
	}

	public String getEstadisticaCliente() throws Exception {
		String select="";

		select += "Select '' as agrupador1, "+fieldCliente+" as agrupador2, '' as agrupador3, '' as agrupador4, "+calculedFieldCliente+" as cantidad1, "+calculedField2Cliente+" as cantidad2, 0 as cantidad3, 0 as cantidad4, 0 as cantidad5, 0 as porcentaje, 0 as factor ";
		select += calculedFromCliente;
		select += calculedWhereCliente;
		select += calculedGroupCliente;
		select += " order by "+calculedFieldCliente+" DESC" ;
		return select;
	}
	public String getEstadisticaClienteDetalle() throws Exception {
		String select="";
		select += "Select "+calculedFromDetalle;
		select += calculedFromCliente;
		select += calculedWhereCliente;
		select += " AND "+fieldCliente+"='::agrupado2::' ";
		return select;
	}
	
	public String getEstadisticaCliente2() throws Exception {
		String select="";

		select += "Select '' as agrupador1, "+fieldCliente+" as agrupador2, '' as agrupador3, '' as agrupador4, "+calculedFieldCliente+" as cantidad1, "+calculedField3Cliente+" as cantidad2, 0 as cantidad3, 0 as cantidad4, 0 as cantidad5, 0 as porcentaje, 0 as factor ";
		select += calculedFromCliente;
		select += calculedWhereCliente;
		select += calculedGroupCliente;
		select += " order by "+calculedFieldCliente+" DESC" ;
		return select;
	}
	public String getEstadisticaClienteDetalle2() throws Exception {
		String select="";
		select += "Select "+calculedFromDetalle;
		select += calculedFromCliente;
		select += calculedWhereCliente;
		select += " AND "+fieldCliente+"='::agrupado2::' ";
		return select;
	}
}
