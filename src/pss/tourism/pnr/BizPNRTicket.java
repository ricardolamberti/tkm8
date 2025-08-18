package pss.tourism.pnr;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.StringTokenizer;

import pss.JPath;
import pss.bsp.bspBusiness.BizBSPCompany;
import pss.bsp.config.carrierGroups.detalle.BizCarrierGroupDetail;
import pss.bsp.consolid.model.liquidacion.detail.BizLiqDetail;
import pss.bsp.consolidador.IConciliable;
import pss.bsp.contrato.detalle.BizDetalle;
import pss.bsp.contrato.detalle.prorrateo.BizProrrateo;
import pss.bsp.dk.BizClienteDK;
import pss.bsp.market.BizMarket;
import pss.bsp.market.detalle.BizMarketDetail;
import pss.bsp.organization.detalle.BizOrganizationDetail;
import pss.bsp.parseo.IFormato;
import pss.bsp.pdf.mex.cabecera.BizMexCabecera;
import pss.bsp.pdf.mex.detalle.BizMexDetalle;
import pss.bsp.regions.detalle.BizRegionDetail;
import pss.bsp.renderOver.BizRenderOver;
import pss.common.customList.config.relation.JRelation;
import pss.common.customList.config.relation.JRelations;
import pss.common.event.mailing.BizMailingPersona;
import pss.common.regions.company.BizCompany;
import pss.common.regions.currency.BizMoneda;
import pss.common.regions.currency.history.BizMonedaCotizacion;
import pss.common.regions.divitions.BizPaisLista;
import pss.common.security.BizUsuario;
import pss.core.JAplicacion;
import pss.core.data.interfaces.connections.JBDatos;
import pss.core.data.interfaces.sentences.JBaseRegistro;
import pss.core.graph.Graph;
import pss.core.graph.implementations.GraphScriptWorldArc;
import pss.core.graph.model.ModelMatrix;
import pss.core.services.JExec;
import pss.core.services.fields.JBoolean;
import pss.core.services.fields.JCurrency;
import pss.core.services.fields.JDate;
import pss.core.services.fields.JFloat;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JMultiple;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.JDateTools;
import pss.core.tools.JPair;
import pss.core.tools.JTools;
import pss.core.tools.PssLogger;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JList;
import pss.core.tools.collections.JMap;
import pss.core.tools.collections.JOrderedMap;
import pss.core.winUI.lists.JWinList;
import pss.tourism.airports.BizAirport;
import pss.tourism.carrier.BizCarrier;
import pss.tourism.dks.BizDk;
import pss.tourism.interfaceGDS.FileProcessor;
import pss.tourism.interfaceGDS.amadeus.AmadeusFileProcessor;
import pss.tourism.interfaceGDS.galileo.GalileoFileProcessor;
import pss.tourism.interfaceGDS.sabre.SabreFileProcessor;
import pss.tourism.interfaceGDS.travelport.TravelPortFileProcessor;
import pss.tourism.voidManual.BizVoidManual;

public class BizPNRTicket extends JRecord implements IConciliable {

	public static final String TIPO_AEREO = "A";
	public static final String TIPO_BUS = "B";

	public final static String PAX_TYPE_ADULT = "ADT";
	public final static String PAX_TYPE_CHILD = "CHD";
	public final static String PAX_TYPE_INFANT = "INF";
	public final static String PAX_TYPE_SRC = "SRC";
	public final static String PAX_TYPE_ITX = "ITX";
	public final static String PAX_TYPE_NEG = "NEG";
	public final static String PAX_TYPE_CNN = "CNN";
	public final static String PAX_TYPE_C01 = "C01";
	public final static String PAX_TYPE_C02 = "C02";
	public final static String PAX_TYPE_C03 = "C03";
	public final static String PAX_TYPE_C04 = "C04";
	public final static String PAX_TYPE_C05 = "C05";
	public final static String PAX_TYPE_C06 = "C06";
	public final static String PAX_TYPE_C07 = "C07";
	public final static String PAX_TYPE_C08 = "C08";
	public final static String PAX_TYPE_C09 = "C09";
	public final static String PAX_TYPE_C10 = "C10";
	public final static String PAX_TYPE_C11 = "C11";

	public final static String FARETYPE_A = "A";
	public final static String FARETYPE_B = "B";
	public final static String FARETYPE_C = "C";
	public final static String FARETYPE_U = "U";
	public final static String FARETYPE_SPACE = " ";

	private JString pCodigopnr = new JString();
	private JString pNumeroboleto = new JString();
	private JBoolean pInternacional = new JBoolean();
	private JString pInternacionalDescr = new JString();
	private JBoolean pVoid = new JBoolean();
	private JBoolean pRefund = new JBoolean();
	private JBoolean pReemitted = new JBoolean();
	private JBoolean pExchanged = new JBoolean(){
		public void preset() throws Exception {
			pExchanged.setValue(pReemitted.getValue() && pOriginalNumeroboleto.isNull());
		};
	};
	private JBoolean pOpen = new JBoolean() {
		public void preset() throws Exception {
			pOpen.setValue(!pReemitted.getValue());
		};
	};
	private JString pOriginalNumeroboleto = new JString();
	private JLong pRefOriginal = new JLong();
	private JString pTourCode = new JString();
	private JBoolean pIsInterlineal = new JBoolean();
	private JBoolean pIsComplete = new JBoolean();

	private JFloat pHighFare = new JFloat();
	private JFloat pLowFare = new JFloat();
	private JBoolean pCorporativo = new JBoolean();
	private JDate insertDate = new JDate();
	private JString pProposito = new JString();
	private JString pCuenta = new JString();
	private JString pClaseTarifa = new JString();
	private JString pAutorizante = new JString();
	private JString pFareSavings = new JString();
	private JDate pReserva = new JDate();
	private JString pCodigoNegocio = new JString();
	private JString pRegion = new JString();
	private JString pDepartamento = new JString();
	private JString pAutorizCC = new JString();
	private JString pSegCC = new JString();
	private JString pSolicitante = new JString();
	private JString pPrivateFareIndicator = new JString();
	private JString pTipoOperacion = new JString();
	private JBoolean pIsTicket = new JBoolean();
	private JBoolean pEmision = new JBoolean();
	private JBoolean pVueltaAlMundo = new JBoolean();

	private JString pCantidadconectados = new JString();
	private JLong pCantidadSegmentos = new JLong();
	private JLong pCantidadRoundTrip = new JLong();
	private JString pNumeropasajero = new JString();
	private JString pTipoboleto = new JString();
	// private JFloat pVenta = new JFloat();
	// private JFloat pTarifaeconomica = new JFloat();
	// private JFloat pTarifanormal = new JFloat();
	// private JString pAplicacomparativo = new JString();
	// private JString pAplicacentro = new JString();
	// private JString pCentrodecosto = new JString();
	// private JString pNumeroempleado = new JString();
	private JString pCodigoaerolinea = new JString();
	private JString pCodigoaerolineaIntern = new JString();
	private JString pDescripcion = new JString();
	private JString pIt = new JString();
	private JDate pUpdateVersion = new JDate();
	private JLong pUpfrontRef = new JLong();
	private JDate pFechaCalcOver = new JDate();
	private JDate pFechaCalcOverBack = new JDate();
	
	private JString pMonth = new JString() {
		public void preset() throws Exception {
			pMonth.setValue(JDateTools.getMonthDescr(getCreationDate()));
		};
	};
	private JString pYear = new JString() {
		public void preset() throws Exception {
			pYear.setValue(JDateTools.getYearNumber(getCreationDate()));
		};
	};
	private JString pDI = new JString() {
		public void preset() throws Exception {
			pDI.setValue(isInternacional()?"I":"D");
		};
	};
	private JString pOracleNumero = new JString() {
		public void preset() throws Exception {
			pOracleNumero.setValue(getOracleNumero());
		};
	};
	private JString pOracleSerie = new JString() {
		public void preset() throws Exception {
			pOracleSerie.setValue(getOracleSerie());
		};
	};
	
	private JString pBspCodeCarrier = new JString() {
		public void preset() throws Exception {
			pBspCodeCarrier.setValue(getBspCodeCarrier());
		};
	};
	
	private JString pBspIata = new JString() {
		public void preset() throws Exception {
			pBspIata.setValue(getBspIata());
		};
	};
	
	private JString pBspTTL = new JString() {
		public void preset() throws Exception {
			pBspTTL.setValue(getTTL());
		};
	};
	
	private JString pMercadoOrigen = new JString() {
		public void preset() throws Exception {
			pMercadoOrigen.setValue(getMercadoOrigen());
		};
	};
	
	private JString pMercadoDestino = new JString() {
		public void preset() throws Exception {
			pMercadoDestino.setValue(getMercadoDestino());
		};
	};
	private JString pBspCarrier = new JString() {
		public void preset() throws Exception {
			pBspCarrier.setValue(getBspCarrier());
		};
	};
	
	private JString pBspCarrier2 = new JString() {
		public void preset() throws Exception {
			pBspCarrier2.setValue(getBspCarrier2());
		};
	};
	
	private JFloat pBspPorc = new JFloat() {
		public void preset() throws Exception {
			pBspPorc.setValue(getBspPorc());
		};
	};
	
	private JFloat pBspComision = new JFloat() {
		public void preset() throws Exception {
			pBspComision.setValue(getBspComision());
		};
	};
	private JString pBspTipoComprobante = new JString() {
		public void preset() throws Exception {
			pBspTipoComprobante.setValue(getBspTipoComprobante());
		};
	};
	private JFloat pDifBspPorc = new JFloat() {
		public void preset() throws Exception {
			pDifBspPorc.setValue(getComisionPerc()- getBspPorc());
		};
	};
	
	private JFloat pDifBspComision = new JFloat() {
		public void preset() throws Exception {
			pDifBspComision.setValue(getComisionAmount()-getBspComision());
		};
	};
	BizLiqDetail objLiqDetail;
	
	public BizLiqDetail getObjLiqDetail() throws Exception {
		if (objLiqDetail!=null) return objLiqDetail;
		BizLiqDetail liqDetail = new BizLiqDetail();
		liqDetail.dontThrowException(true);
		if (!liqDetail.readByBoleto(getCompany(), getNumeroboleto())) return null;
		
		return objLiqDetail=liqDetail;
	}
	
	public String getOracleNumero() throws Exception {
		BizLiqDetail liqDetail = getObjLiqDetail();
		if (liqDetail==null) return "";
		return liqDetail.getNroDoc();
	}
	public String getOracleSerie() throws Exception {
		BizLiqDetail liqDetail = getObjLiqDetail();
		if (liqDetail==null) return "";
		return liqDetail.getTipoDoc();
	}
	
	BizMexDetalle objMexDetalle;
	
	public BizMexDetalle getObjMexDetail() throws Exception {
		if (objMexDetalle!=null) return objMexDetalle;
		BizMexDetalle liqDetail = new BizMexDetalle();
		liqDetail.dontThrowException(true);
		if (!liqDetail.read(getCompany(), getNumeroboleto())) return null;
		
		return objMexDetalle=liqDetail;
	}
	public String getBspCodeCarrier() throws Exception {
		BizMexDetalle liqDetail = getObjMexDetail();
		if (liqDetail==null) return "";
		return ""+liqDetail.getIdAerolinea();
	}
	public String getBspCarrier() throws Exception {
		String bspCarrier = getBspCodeCarrier();
		if (bspCarrier.equals("")) return "";
		BizCarrier carrier = new BizCarrier();
		carrier.dontThrowException(true);
		if (!carrier.ReadIata(bspCarrier)) return "";
		return carrier.getCarrier();
	}	
	public String getBspIata() throws Exception {
		BizMexDetalle liqDetail = getObjMexDetail();
		if (liqDetail==null) return "";
		BizMexCabecera cab = liqDetail.getObjCabecera();
		if (cab==null) return "";
		
		return cab.getIata();
	}
	public String getTTL() throws Exception {
		BizMexDetalle liqDetail = getObjMexDetail();
		if (liqDetail==null) return "";
		return ""; // que es ttl?
	}
	public String getMercadoOrigen() throws Exception {
		BizAirport origen = getObjAeropuertoOrigen();
		if (origen==null) return "";
		return origen.getObjCountry().GetRegion();
	}
	public String getMercadoDestino() throws Exception {
		BizAirport destino = getObjAeropuertoDestino();
		if (destino==null) return "";
		return destino.getObjCountry().GetRegion();
	}
	public String getBspCarrier2() throws Exception {
			return "";// que es bsp 2
	}
	public double getBspPorc() throws Exception {
		BizMexDetalle liqDetail = getObjMexDetail();
		if (liqDetail==null) return 0.0;
		return liqDetail.getComisionPorc();
	}
	
	public double getBspComision() throws Exception {
		BizMexDetalle liqDetail = getObjMexDetail();
		if (liqDetail==null) return 0.0;
		return liqDetail.getComision();
	}
	public String getBspTipoComprobante() throws Exception {
		BizMexDetalle liqDetail = getObjMexDetail();
		if (liqDetail==null) return "";
		return liqDetail.getOperacion();
	}

	
	private JString pUpfrontDescripcion = new JString() {
		public void preset() throws Exception {
			pUpfrontDescripcion.setValue(getUpfrontDescripcion());
		};
	};

	private JString pEndoso = new JString();
	
	public String getEndoso() throws Exception {
		return pEndoso.getValue();		
	}
	
	public void setEndoso(String endoso) {
		pEndoso.setValue(endoso);
	}
	
	// private JString pBoletocambio = new JString();
	private JCurrency pTarifaReal = new JCurrency() {
		public void preset() throws Exception {
			setSimbolo(pCodigoMoneda.isNotNull());
			setMoneda(pCodigoMoneda.getValue());
		};
	};
	private JCurrency pTarifaUsa = new JCurrency() {
		public void preset() throws Exception {
			setSimbolo(true);
			setMoneda("USD");
		};
	};
	private JCurrency pNetoUsa = new JCurrency() {
		public void preset() throws Exception {
			setSimbolo(true);
			setMoneda("USD");
		};
	};
	private JCurrency pTarifaFacturadaYQUsa = new JCurrency() {
		public void preset() throws Exception {
			setSimbolo(true);
			setMoneda("USD");
		};
	};
	private JCurrency pNetoYQUsa = new JCurrency() {
		public void preset() throws Exception {
			setSimbolo(true);
			setMoneda("USD");
		};
	};
	private JCurrency pBaseYQUsa = new JCurrency() {
		public void preset() throws Exception {
			setSimbolo(true);
			setMoneda("USD");
		};
	};
	
	private JCurrency pTarifaLocal = new JCurrency() {
		public void preset() throws Exception {
			setSimbolo(true);
			setMoneda(pCodigoMonedaLocal.getValue());
		};
	};
	private JCurrency pNetoLocal = new JCurrency() {
		public void preset() throws Exception {
			setSimbolo(true);
			setMoneda(pCodigoMonedaLocal.getValue());
		};
	};
	private JCurrency pTarifaFacturadaYQLocal = new JCurrency() {
		public void preset() throws Exception {
			setSimbolo(true);
			setMoneda(pCodigoMonedaLocal.getValue());
		};
	};
	private JCurrency pNetoYQLocal = new JCurrency() {
		public void preset() throws Exception {
			setSimbolo(true);
			setMoneda(pCodigoMonedaLocal.getValue());
		};
	};
	private JCurrency pBaseYQLocal = new JCurrency() {
		public void preset() throws Exception {
			setSimbolo(true);
			setMoneda(pCodigoMonedaLocal.getValue());
		};
	};
	private JCurrency pTarifaFacturaConImpuestosLocal = new JCurrency() {
		public void preset() throws Exception {
			setSimbolo(true);
			setMoneda(pCodigoMonedaLocal.getValue());
		};
	};
	private JCurrency pNetoFacturaLocal = new JCurrency() {
		public void preset() throws Exception {
			setSimbolo(true);
			setMoneda(pCodigoMonedaLocal.getValue());
		};
	};
	private JCurrency pTarifaFacturaLocal = new JCurrency() {
		public void preset() throws Exception {
			setSimbolo(true);
			setMoneda(pCodigoMonedaLocal.getValue());
		};
	};

	private JCurrency pAhorro = new JCurrency() {
		public void preset() throws Exception {
			setSimbolo(pCodigoMoneda.isNotNull());
			setMoneda(pCodigoMoneda.getValue());
		};
	};
	
	private JFloat pPorcentajeProrrateoOver = new JFloat();
	private JCurrency pImporteProrrateoOver = new JCurrency() {
		public void preset() throws Exception {
			setSimbolo(pCodigoMoneda.isNotNull());
			setMoneda(pCodigoMoneda.getValue());
		};
	};

	private JCurrency pImporteover = new JCurrency() {
		public void preset() throws Exception {
			setSimbolo(pCodigoMoneda.isNotNull());
			setMoneda(pCodigoMoneda.getValue());
		};
	};
	private JCurrency pImporteoverRmk = new JCurrency() {
		public void preset() throws Exception {
			setSimbolo(pCodigoMoneda.isNotNull());
			setMoneda(pCodigoMoneda.getValue());
		};
	};

	private JCurrency pIva = new JCurrency() {
		public void preset() throws Exception {
			setSimbolo(pCodigoMoneda.isNotNull());
			setMoneda(pCodigoMoneda.getValue());
		};
	};
	private JCurrency pYQ = new JCurrency() {
		public void preset() throws Exception {
			setSimbolo(pCodigoMoneda.isNotNull());
			setMoneda(pCodigoMoneda.getValue());
		};
	};
	

	private JCurrency pQ = new JCurrency() {
		public void preset() throws Exception {
			setSimbolo(pCodigoMoneda.isNotNull());
			setMoneda(pCodigoMoneda.getValue());
		};
	};
	private JCurrency pQUsd = new JCurrency() {
		public void preset() throws Exception {
			setSimbolo(true);
			setMoneda("USD");
		};
	};
	private JCurrency pQLocal= new JCurrency() {
		public void preset() throws Exception {
			setSimbolo(true);
			setMoneda(pCodigoMonedaLocal.getValue());
		};
	};
	private JCurrency pImpuestos = new JCurrency() {
		public void preset() throws Exception {
			setSimbolo(pCodigoMoneda.isNotNull());
			setMoneda(pCodigoMoneda.getValue());
		};
	};
	private JCurrency pTotalImpuestos = new JCurrency() {
		public void preset() throws Exception {
			setSimbolo(pCodigoMoneda.isNotNull());
			setMoneda(pCodigoMoneda.getValue());
		};
	};
	private JCurrency pTotalImpuestosFacturaa = new JCurrency() {
		public void preset() throws Exception {
			setSimbolo(pCodigoMoneda.isNotNull());
			setMoneda(pCodigoMoneda.getValue());
		};
	};
	private JLong pDiasViajados = new JLong();
	private JLong pDiasCompras = new JLong();
	private JLong pDiasPreCompras = new JLong();
	 private JCurrency pIvaover = new JCurrency() {
			public void preset() throws Exception {
				setSimbolo(pCodigoMoneda.isNotNull());
				setMoneda(pCodigoMoneda.getValue());
			};
		};
	private JCurrency pImportecedido = new JCurrency() {
		public void preset() throws Exception {
			setSimbolo(pCodigoMoneda.isNotNull());
			setMoneda(pCodigoMoneda.getValue());
		};
	};
	private JFloat pPorcentajeover = new JFloat();
	private JCurrency pNeto = new JCurrency() {
		public void preset() throws Exception {
			setSimbolo(pCodigoMoneda.isNotNull());
			setMoneda(pCodigoMoneda.getValue());
		};
	};
	

	private JBoolean pCalculed = new JBoolean() {
		public void preset() throws Exception {
			if (pCalculed.getRawValue()==false && !GetVision().equals("UPDATE")) {
				calculeOver();
			}
			
			
		};
	};
	
	private JBoolean pOversobre = new JBoolean();
	// private JBoolean pReemision = new JBoolean();
	// private JString pOffline = new JString();
	private JString pNombrePasajero = new JString();
	private JString pIdentFiscal = new JString();
	private JString pTipoPasajero = new JString();
	private JString pTipoPasajero2 = new JString() {
		public void preset() throws Exception {
			pTipoPasajero2.setValue(pTipoPasajero.getValue());
		};
	};

	private JFloat pAditionalFee = new JFloat();
	private JFloat pCostoFee = new JFloat();
	private JString pPagoFee = new JString();
	private JString pOrdenFee = new JString();
	private JString pConsumo = new JString();
	private JFloat pConsumoNum = new JFloat();
	private JFloat pExpense = new JFloat();
	private JFloat pPorcExpense = new JFloat();
	// private JFloat pIvaExpense = new JFloat();
	protected JString pClase = new JString();
	protected JString pTipoClase = new JString();
	protected JString pCompany = new JString();
	private JString pPais = new JString();
	private JString pPaisOrigen = new JString();
	// private JString pCiudadOrigen = new JString();
	// private JString pContinenteOrigen = new JString();
	// private JString pRegionOrigen = new JString();
	private JString pPaisDestino = new JString();
	// private JString pCiudadDestino = new JString();
	// private JString pContinenteDestino = new JString();
	// private JString pRegionDestino = new JString();
	// private JGeoPosition pGeoOrigen = new JGeoPosition();
	// private JGeoPosition pGeoDestino = new JGeoPosition();
	// protected JString pReferencia=new JString();
	// protected JBoolean pDevolucion=new JBoolean();
	// protected JBoolean pAjuste=new JBoolean();
	// private JBoolean pOverCedidoIvaRetenido = new JBoolean();
	private JString pImagen1 = new JString() {
		public void preset() throws Exception {
			pImagen1.setValue(getImagen1());
		}
	};
	// comision
	private JCurrency pComisionAmount = new JCurrency() {
		public void preset() throws Exception {
			setSimbolo(pCodigoMoneda.isNotNull());
			setMoneda(pCodigoMoneda.getValue());
		};
	};
	private JFloat pComisionPerc = new JFloat();


	// price
	private JString pCodigoMoneda = new JString();
	private JString pCodigoMonedaLocal = new JString();
	private JString pCodigoBaseMoneda = new JString();
	private JFloat pTipoCambio = new JFloat();
	// ufff .las monedas estan al reves
	private JCurrency pTarifaBase = new JCurrency() {
		public void preset() throws Exception {
			setSimbolo(pCodigoBaseMoneda.isNotNull());
			setMoneda(pCodigoBaseMoneda.getValue());
		};
	};
	private JCurrency pTarifaBaseConImpuestos = new JCurrency() {
		public void preset() throws Exception {
			setSimbolo(pCodigoMoneda.isNotNull());
			setMoneda(pCodigoMoneda.getValue());
		};
	};
	private JCurrency pTarifaFacturaConImpuestos = new JCurrency() {
		public void preset() throws Exception {
			setSimbolo(pCodigoMoneda.isNotNull());
			setMoneda(pCodigoMoneda.getValue());
		};
	};
	private JCurrency pTarifaFacturaConImpuestosDolares = new JCurrency() {
		public void preset() throws Exception {
			setSimbolo(true);
			setMoneda("USD");
		};
	};
	private JCurrency pTarifaFactura = new JCurrency() {
		public void preset() throws Exception {
			setSimbolo(pCodigoMoneda.isNotNull());
			setMoneda(pCodigoMoneda.getValue());
		};
	};
	private JCurrency pNetoFacturaDolares = new JCurrency() {
		public void preset() throws Exception {
			setSimbolo(true);
			setMoneda("USD");
		};
	};
	private JCurrency pTarifaFacturaDolares = new JCurrency() {
		public void preset() throws Exception {
			setSimbolo(true);
			setMoneda("USD");
		};
	};
	private JCurrency pTarifaBaseFactura = new JCurrency() {
		public void preset() throws Exception {
			setSimbolo(pCodigoBaseMoneda.isNotNull());
			setMoneda(pCodigoBaseMoneda.getValue());
		};
	};
	private JCurrency pIvaFactura = new JCurrency() {
		public void preset() throws Exception {
			setSimbolo(pCodigoMoneda.isNotNull());
			setMoneda(pCodigoMoneda.getValue());
		};
	};
	private JCurrency pNetoFactura = new JCurrency() {
		public void preset() throws Exception {
			setSimbolo(pCodigoMoneda.isNotNull());
			setMoneda(pCodigoMoneda.getValue());
		};
	};
	private JCurrency pImpuestoFactura = new JCurrency() {
		public void preset() throws Exception {
			setSimbolo(pCodigoMoneda.isNotNull());
			setMoneda(pCodigoMoneda.getValue());
		};
	};
	private JCurrency pComisionFactura = new JCurrency() {
		public void preset() throws Exception {
			setSimbolo(pCodigoMoneda.isNotNull());
			setMoneda(pCodigoMoneda.getValue());
		};
	};
	private JCurrency pTarifa = new JCurrency() {
		public void preset() throws Exception {
			setSimbolo(pCodigoMoneda.isNotNull());
			setMoneda(pCodigoMoneda.getValue());
		};
	};
	private JCurrency pTarifaYQ = new JCurrency() {
		public void preset() throws Exception {
			setSimbolo(pCodigoMoneda.isNotNull());
			setMoneda(pCodigoMoneda.getValue());
		};
	};
	private JCurrency pTarifaFacturadaYQ = new JCurrency() {
		public void preset() throws Exception {
			setSimbolo(pCodigoMoneda.isNotNull());
			setMoneda(pCodigoMoneda.getValue());
		};
	};

	// remarks
	private JString pCodigoCliente = new JString();
	private JString pCliente = new JString();
	private JString pEmployee = new JString();
	private JString pEmailID = new JString();
	private JString pFechaAuth = new JString();
	private JString pFechaCreation = new JString();
	private JString pReasonCode = new JString();
	private JString pReasonCodeHotel = new JString();
	private JString pVendedor = new JString();
	private JString pCentroCostos = new JString();
	private JString pBusinessGroup = new JString();
	private JString pAV = new JString();
	private JString pObservacion = new JString();
	private JString pNombreTarjeta = new JString();

	// tarjeta
	private JCurrency pMontoTarjeta = new JCurrency() {
		public void preset() throws Exception {
			setSimbolo(pCodigoMoneda.isNotNull());
			setMoneda(pCodigoMoneda.getValue());
		};
	};
	private JString pNumeroTarjeta = new JString();
	private JString pNumeroTarjetaEnmascarada = new JString() {
		public void preset() throws Exception {
			pNumeroTarjetaEnmascarada.setValue(getNumeroTarjetaEnmascarada());
		};
	};
	private JString pNetRemit = new JString();

	// pnr data
	private JString pNroIata = new JString();
	private JString pOfficeId = new JString();
	private JString pAgenteReserva = new JString();
	private JString pAgenteEmision = new JString();
	private JString pCityCode = new JString();
	private JDate pPNRDate = new JDate();
	private JDate pCreationDate = new JDate();
	private JDate pCreationDateAir = new JDate();
	private JString pPeriodo = new JString();
	private JDate pVoidDate = new JDate();
	private JString pDirectory = new JString();
	private JDate pFechaProcesamiento = new JDate();

	private JString pCustomerId = new JString();
	private JString pCustomerIdReducido = new JString();
	private JString pDKOriginal = new JString();
	private JString pRoute = new JString();
	private JString pMiniRoute = new JString();
	private JString pMarket = new JString();
	private JString pAirIntinerary = new JString();
	private JString pAirGenIntinerary = new JString();
	private JString pAirPaisIntinerary = new JString();
	private JString pCarrierIntinerary = new JString();
	private JString pClassIntinerary = new JString();
	private JString pFechaIntinerary = new JString();
	private JString pFareIntinerary = new JString();
	private JString pNroFlIntinerary = new JString();
	private JString pMarketIntinerary = new JString();
	private JDate pDepartureDate = new JDate();
	private JDate pArriveDate = new JDate();
	private JDate pEndTravelDate = new JDate();
	private JString pTransactionType = new JString();
	private JString pTipoPrestacion = new JString();

	// pnr
	// protected JString pIndicadorVenta = new JString();
	protected JString pArchivo = new JString();
	protected JString pVersion = new JString();
	protected JString pGDS = new JString();
	protected JString pOrigen = new JString();

	protected JString pAeropuertoOrigen = new JString();
	protected JString pAeropuertoDestino = new JString();
	// protected JObjBD pObjAeropuertoOrigen = new JObjBD() {
	// public void preset() throws Exception {
	// setValue(getObjAeropuertoOrigen());
	// };
	// };
	// protected JObjBD pObjAeropuertoDestino = new JObjBD() {
	// public void preset() throws Exception {
	// setValue(getObjAeropuertoDestino());
	// };
	// };

	protected JString pRemarks = new JString();

	private JBoolean pOversobreBack = new JBoolean();
	private JMultiple pContratosBack = new JMultiple();
	private JFloat pComisionPercBack = new JFloat() {
		public void preset() throws Exception {
			calculeOverBackend();// setting in function
		};
	};	
	private JCurrency pComisionOverBack = new JCurrency() {
		public void preset() throws Exception {
			setSimbolo(pCodigoMoneda.isNotNull());
			setMoneda(pCodigoMoneda.getValue());
			calculeOverBackend();// setting in function
		};
	};
	
	private JString pBackendDescripcion = new JString() {
		public void preset() throws Exception {
			calculeOverBackend();// setting in function
		};
	};

	
	public String getBackendDescripcion() throws Exception {
		return pBackendDescripcion.getValue();
	}
	public void setContratoBack(JList<String> zValue) {
		pContratosBack.setValue(zValue);
	}
	public void addContratoBack(String zValue) throws Exception {
		pContratosBack.addValue(zValue);
	}
	public void clearContratoBack() throws Exception {
		pContratosBack.setNull();
	}
	public JList<String> getContratoBack() throws Exception {
		return pContratosBack.getValue();
	}
	
	public void setComisioOverBack(double zValue) {
		pComisionOverBack.setValue(zValue);
	}

	public double getComisionOverBack() throws Exception {
		return pComisionOverBack.getValue();
	}
	
	public void calculeOverBackend() throws Exception {
		if (!pOversobreBack.isFalse()) return;

		JList<String> contratos = getContratoBack();
		JIterator<String> it = contratos.getIterator();
		double porc=0;
		double prem=0;
		String descrip="";
		while (it.hasMoreElements()) {
			String idContrato = it.nextElement();
			BizDetalle det = getObjContrato(Long.parseLong(idContrato));
			if (det==null) continue;
			descrip+=(descrip.equals("")?"":", ")+det.getDescrContrato()+" "+det.getDescripcion();
			double porcentaje = det.getCalculePorcPorBoleto(getTarifaFacturaLocal());
			porc+=porcentaje;
			prem+= det.getCalculeGananciaPorBoleto(getTarifaFacturaLocal(),porcentaje);
		}
		pBackendDescripcion.setValue(descrip);
		pOversobreBack.setValue(true);
		pComisionPercBack.setValue(porc);
		pComisionOverBack.setValue(prem);
	}
	
	public void setComisionPercBack(double zValue) {
		pComisionPercBack.setValue(zValue);
	}

	public double getComisionPercBack() throws Exception {
		return pComisionPercBack.getValue();
	}
	
	public void setOversobreBack(boolean zValue) {
		pOversobreBack.setValue(zValue);
	}

	public boolean getOversobreBack() throws Exception {
		return pOversobreBack.getValue();
	}
	
	public void setCalculed(boolean zValue) {
		pCalculed.setValue(zValue);
	}

	public boolean getCalculed() throws Exception {
		return pCalculed.getValue();
	}
	public void setCompany(String zValue) {
		pCompany.setValue(zValue);
	}

	public String getCompany() throws Exception {
		return pCompany.getValue();
	}

	public String getPais() throws Exception {
		return pPais.getValue();
	}

	BizBSPCompany objCompany;

	public BizBSPCompany getObjCompany() throws Exception {
		if (objCompany != null)
			return objCompany;
		return objCompany=(BizBSPCompany)BizCompany.getCompany(pCompany.getValue());
	}

	public String getPaisOrigen() throws Exception {
	 return pPaisOrigen.getValue();
	}
	public String getPaisDestino() throws Exception {
		 return pPaisDestino.getValue();
		}
	public String getCarrier() throws Exception {
		return pCodigoaerolinea.getValue();
	}

	public String getFirstCarrierInternacional() throws Exception {
		return pCodigoaerolineaIntern.getValue();
	}

	public void setTipoPrestacion(String zValue) throws Exception {
		pTipoPrestacion.setValue(zValue);
	}

	public long getDiasViajados() throws Exception {
		return pDiasViajados.getValue();
	}

	public void setDiasViajados(long zValue) throws Exception {
		pDiasViajados.setValue(zValue);
	}

	public long getDiasCompras() throws Exception {
		return pDiasCompras.getValue();
	}

	public double getFareHigh() throws Exception {
		return pHighFare.getValue();
	}

	public void setFareHigh(double zValue) throws Exception {
		pHighFare.setValue(zValue);
	}

	public double getFareLow() throws Exception {
		return pLowFare.getValue();
	}

	public void setFareLow(double zValue) throws Exception {
		pLowFare.setValue(zValue);
	}

	public boolean getCorporativo() throws Exception {
		return pCorporativo.getValue();
	}

	public void setCorporativo(boolean zValue) throws Exception {
		pCorporativo.setValue(zValue);
	}

	public String getProposito() throws Exception {
		return pProposito.getValue();
	}

	public void setProposito(String zValue) throws Exception {
		pProposito.setValue(zValue);
	}

	public String getCuenta() throws Exception {
		return pCuenta.getValue();
	}

	public void setCuenta(String zValue) throws Exception {
		pCuenta.setValue(zValue);
	}

	public String getClaseTarifa() throws Exception {
		return pClaseTarifa.getValue();
	}

	public void setClaseTarifa(String zValue) throws Exception {
		pClaseTarifa.setValue(zValue);
	}

	public String getAutorizante() throws Exception {
		return pAutorizante.getValue();
	}

	public void setAutorizante(String zValue) throws Exception {
		pAutorizante.setValue(zValue);
	}

	public String getFareSavings() throws Exception {
		return pFareSavings.getValue();
	}

	public void setFareSavings(String zValue) throws Exception {
		pFareSavings.setValue(zValue);
	}

	public String getCodigoNegocio() throws Exception {
		return pCodigoNegocio.getValue();
	}

	public void setCodigoNegocio(String zValue) throws Exception {
		pCodigoNegocio.setValue(zValue);
	}

	public String getRegion() throws Exception {
		return pRegion.getValue();
	}

	public void setRegion(String zValue) throws Exception {
		pRegion.setValue(zValue);
	}

	public String getDepartamento() throws Exception {
		return pDepartamento.getValue();
	}

	public String getVendedor() throws Exception {
		return pVendedor.getValue();
	}

	public void setDepartamento(String zValue) throws Exception {
		pDepartamento.setValue(zValue);
	}

	public void setAutorizationCreditCard(String zValue) throws Exception {
		pAutorizCC.setValue(zValue);
	}

	public void setCodSegCreditCard(String zValue) throws Exception {
		pSegCC.setValue(zValue);
	}

	public String getSolicitante() throws Exception {
		return pSolicitante.getValue();
	}

	public void setSolicitante(String zValue) throws Exception {
		pSolicitante.setValue(zValue);
	}
	
	
	public String getPrivateFareIndicator() throws Exception {
		return pPrivateFareIndicator.getValue();
	}

	public void setPrivateFareIndicator(String zValue) throws Exception {
		pPrivateFareIndicator.setValue(zValue);
	}
	public String getTipoOperacion() throws Exception {
		return pTipoOperacion.getValue();
	}

	public void setTipoOperacion(String zValue) throws Exception {
		// if (zValue.equals(""))
		// zValue=("ETR");
		pTipoOperacion.setValue(zValue);
		if (zValue != null)
			pIsTicket.setValue(zValue.equals("ETR"));
		
		if (pTipoOperacion.equals("EMD")) {
			setRoute("");
			setOversobre(false);
			setIvaover(0);
			setAeropuertoDestino(null);
			setAeropuertoOrigen(null);
			setClase(null);
			pArriveDate.setNull();
			pDepartureDate.setNull();
			pEndTravelDate.setNull();
			pUpfrontRef.setNull();
			pPorcentajeover.setNull();
			pOversobre.setNull();
			pImporteover.setNull();
			pImporteProrrateoOver.setNull();
			pPorcentajeProrrateoOver.setNull();
			pMiniRoute.setNull();
		}
	}

	public Boolean isTicket() throws Exception {
		return pIsTicket.getValue();
	}

	public void setIsTicket(Boolean zValue) throws Exception {
		pIsTicket.setValue(zValue);
	}

	public Boolean isEmision() throws Exception {
		return pEmision.getValue();
	}

	public void setEmision(Boolean zValue) throws Exception {
		pEmision.setValue(zValue);
	}
	public Boolean isVueltaAlMundo() throws Exception {
		return pVueltaAlMundo.getValue();
	}

	public void setVueltaAlMundo(Boolean zValue) throws Exception {
		pVueltaAlMundo.setValue(zValue);
	}
	
	public boolean hasReserva() throws Exception {
		return pReserva.hasValue();
	}

	public Date getReserva() throws Exception {
		return pReserva.getValue();
	}

	public void setReserva(Date zValue) throws Exception {
		pReserva.setValue(zValue);
	}

	public void setDiasCompras(long zValue) throws Exception {
		pDiasCompras.setValue(zValue);
	}

	public void setPreCompras(long zValue) throws Exception {
		pDiasPreCompras.setValue(zValue);
	}

	public void setPais(String zValue) throws Exception {
		pPais.setValue(zValue);
	}

	public void setClase(String zValue) throws Exception {
		pClase.setValue(zValue);
	}

	public void setTipoClase(String zValue) throws Exception {
		pTipoClase.setValue(zValue);
	}

	public void setPaisOrigen(String zValue) throws Exception {
		pPaisOrigen.setValue(zValue);
	}

	public void setPaisDestino(String zValue) throws Exception {
		pPaisDestino.setValue(zValue);
	}

	public String getClase() throws Exception {
		return pClase.getValue();
	}

	public String getTipoClase() throws Exception {
		return pTipoClase.getValue();
	}

	public String getTipoPrestacion() throws Exception {
		return pTipoPrestacion.getValue();
	}

	public void setAeropuertoOrigen(String zValue) throws Exception {
		pAeropuertoOrigen.setValue(zValue);
	}

	public void setAeropuertoDestino(String zValue) throws Exception {
		pAeropuertoDestino.setValue(zValue);
	}

	public String getAeropuertoOrigen() throws Exception {
		return pAeropuertoOrigen.getValue();
	}

	public String getAeropuertoDestino() throws Exception {
		return pAeropuertoDestino.getValue();
	}

	// private BizPNR pnr;
	// private BizPNRPasajero pax;
	private JRecords<BizPNRTax> taxes;
	private BizAirport aeropuertoOrigen;
	private BizAirport aeropuertoDestino;
	private BizCarrier objCarrier;

	protected JLong pId = new JLong();
	private JBoolean pMigrated = new JBoolean();

	public long getId() throws Exception {
		return pId.getValue();
	}

	public void setId(long val) {
		pId.setValue(val);
	}

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Getter & Setters methods
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void setCodigopnr(String zValue) throws Exception {
		pCodigopnr.setValue(zValue);
	}

	public String getCodigopnr() throws Exception {
		return pCodigopnr.getValue();
	}

	public String getCodigoMoneda() throws Exception {
		return pCodigoMoneda.getValue();
	}
	public String getCodigoMonedaLocal() throws Exception {
		return pCodigoMonedaLocal.getValue();
	}

	public String getCodigoBaseMoneda() throws Exception {
		return pCodigoBaseMoneda.getValue();
	}

	public void setNumeroboleto(String zValue) throws Exception {
		pNumeroboleto.setValue(zValue);
	}

	public String getNumeroboleto() throws Exception {
		return pNumeroboleto.getValue();
	}

	public void setOriginalNumeroboleto(String zValue) throws Exception {
		pOriginalNumeroboleto.setValue(zValue);
	}

	public String getOriginalNumeroboleto() throws Exception {
		return pOriginalNumeroboleto.getValue();
	}
	public boolean hasOriginalNumeroboleto() throws Exception {
		return pOriginalNumeroboleto.isNotNull();
	}

	public String getTourCode() throws Exception {
		return pTourCode.getValue();
	}

	public boolean hasTourCode() throws Exception {
		return !(pTourCode.getValue().equals("") || pTourCode.isNull());
	}

	public boolean isInterlineal() throws Exception {
		return pIsInterlineal.getValue();
	}

	public void setInterlineal(boolean pura) throws Exception {
		pIsInterlineal.setValue(pura);
	}

	public boolean isComplete() throws Exception {
		return pIsComplete.getValue();
	}

	public void setComplete(boolean pura) throws Exception {
		pIsComplete.setValue(pura);
	}

	public void setRefOriginal(long zValue) throws Exception {
		pRefOriginal.setValue(zValue);
	}

	public void setTourCode(String zValue) throws Exception {
		pTourCode.setValue(zValue);
	}

	public void setInternacional(boolean zValue) throws Exception {
		pInternacional.setValue(zValue);
	}

	public void setInternacionalDescr(String zValue) throws Exception {
		pInternacionalDescr.setValue(zValue);
	}

	public boolean isInternacional() throws Exception {
		return pInternacional.getValue();
	}

	public void setCantidadconectados(String zValue) throws Exception {
		pCantidadconectados.setValue(zValue);
	}

	public long getCantidadconectados() throws Exception {
		return pCantidadconectados.isNull() ? 0L : Long.parseLong(pCantidadconectados.getValue());
	}

	public void setBookings(long zValue) throws Exception {
		pCantidadSegmentos.setValue(zValue);
	}

	public long getBookings() throws Exception {
		return pCantidadSegmentos.getValue();
	}

	public void setCantidadRoundTrip(long zValue) throws Exception {
		pCantidadRoundTrip.setValue(zValue);
	}

	public long getCantidadRoundTrip() throws Exception {
		return pCantidadRoundTrip.getValue();
	}

	public void setNumeropasajero(String zValue) throws Exception {
		pNumeropasajero.setValue(zValue);
	}

	public void setNombrePasajero(String zValue) throws Exception {
		pNombrePasajero.setValue(zValue);
	}

	public String getNombrePasajero() throws Exception {
		return pNombrePasajero.getValue();
	}
	public void setIdentFiscal(String zValue) throws Exception {
		pIdentFiscal.setValue(zValue);
	}

	public String getIdentFiscal() throws Exception {
		return pIdentFiscal.getValue();
	}

	public void setTipoPasajero(String zValue) throws Exception {
		pTipoPasajero.setValue(zValue);
	}

	public String getTipoPasajero() throws Exception {
		return pTipoPasajero.getValue();
	}

	public String getNumeropasajero() throws Exception {
		return pNumeropasajero.getValue();
	}

	public void setTipoboleto(String zValue) throws Exception {
		pTipoboleto.setValue(zValue);
	}

	public String getTipoboleto() throws Exception {
		return pTipoboleto.getValue();
	}

	public String getNumeroTarjetaEnmascarada() throws Exception {
		String nro = pNumeroTarjeta.getValue();
		if (nro.length() < 12)
			return "";

		String mask = nro;
		if (nro.length() > 8) {
			mask = nro.substring(0, 4);
			for (int j = 0; j < nro.length() - 8; j++)
				mask += "X";
			mask += nro.substring(nro.length() - 4);
		}
		return mask;
	}

	public void setCodigoaerolinea(String zValue) throws Exception {
		pCodigoaerolinea.setValue(zValue);
	}

	public void setFirstCarrierInternacional(String zValue) throws Exception {
		pCodigoaerolineaIntern.setValue(zValue);
	}

	
	public void setDescripcion(String zValue) throws Exception {
		pDescripcion.setValue(zValue);
	}

	public String getDescripcion() throws Exception {
		return pDescripcion.getValue();
	}

	public void setIt(String zValue) throws Exception {
		pIt.setValue(zValue);
	}

	public String getIt() throws Exception {
		return pIt.getValue();
	}

	public void setIva(double zValue) throws Exception {
		pIva.setValue(zValue);
	}

	public void setYQ(double zValue) throws Exception {
		pYQ.setValue(zValue);
	}

	public void setQ(double zValue) throws Exception {
		pQ.setValue(zValue);
	}
	public void setQUsd(double zValue) throws Exception {
		pQUsd.setValue(zValue);
	}
	public void setQLocal(double zValue) throws Exception {
		pQLocal.setValue(zValue);
	}
	
	
	public void setImpuestos(double zValue) throws Exception {
		pImpuestos.setValue(zValue);
	}

	public void setImpuestosTotal(double zValue) throws Exception {
		pTotalImpuestos.setValue(zValue);
	}

	public void setImpuestosTotalFactura(double zValue) throws Exception {
		pTotalImpuestosFacturaa.setValue(zValue);
	}

	

	public void setImporteover(double zValue) throws Exception {
		pImporteover.setValue(zValue);
	}
	public void setImporteoverRmk(String zValue) throws Exception {
		pImporteoverRmk.setValue(zValue);
	}
	public void setTarifaReal(double zValue) throws Exception {
		pTarifaReal.setValue(zValue);
		setAhorro();
	}
	public void setImporteProrrateoOver(double zValue) throws Exception {
		pImporteProrrateoOver.setValue(zValue);
	}
	public void setPorcentajeProrrateoOver(double zValue) throws Exception {
		pPorcentajeProrrateoOver.setValue(zValue);
	}
	public double getPorcentajeProrrateoOver() throws Exception {
		return pPorcentajeProrrateoOver.getValue();
	}
	public double getImporteProrrateoOver( ) throws Exception {
		return pImporteProrrateoOver.getValue();
	}
	public void setTarifaUsa(double zValue) throws Exception {
		pTarifaUsa.setValue(zValue);
	}

	public void setTarifaNetoUsa(double zValue) throws Exception {
		pNetoUsa.setValue(zValue);
	}

	public void setTarifaNetoYQUsa(double zValue) throws Exception {
		pNetoYQUsa.setValue(zValue);
	}

	public void setTarifaFacturadaYQUsa(double zValue) throws Exception {
		pTarifaFacturadaYQUsa.setValue(zValue);
	}

	public void setTarifaBaseYQUsa(double zValue) throws Exception {
		pBaseYQUsa.setValue(zValue);
	}

	public void setIvaover(double zValue) throws Exception {
		pIvaover.setValue(zValue);
	}

	public void setUpfrontRef(long zValue) throws Exception {
		pUpfrontRef.setValue(zValue);
	}
	public Date getFechaCalcOver() throws Exception {
		return pFechaCalcOver.getValue();
	}
	public void setFechaCalcOver(Date zValue) throws Exception {
		pFechaCalcOver.setValue(zValue);
	}
	public Date getFechaCalcOverBack() throws Exception {
		return pFechaCalcOverBack.getValue();
	}
	public void setFechaCalcOverBack(Date zValue) throws Exception {
		pFechaCalcOverBack.setValue(zValue);
	}
	public void setUpdateVersion(Date zValue) throws Exception {
		pUpdateVersion.setValue(zValue);
	}
	public void setImporteCedido(String zValue) throws Exception {
		pImportecedido.setValue(zValue);
	}

	public void setExpense(double zValue) throws Exception {
		pExpense.setValue(zValue);
	}

	public void setPorcExpense(double zValue) throws Exception {
		pPorcExpense.setValue(zValue);
	}

	public double getIva() throws Exception {
		return pIva.getValue();
	}

	public double getYQ() throws Exception {
		return pYQ.getValue();
	}

	public double getQ() throws Exception {
		return pQ.getValue();
	}
	public double getQUsd() throws Exception {
		return pQUsd.getValue();
	}
	public double getQLocal() throws Exception {
		return pQLocal.getValue();
	}

	public double getImpuestos() throws Exception {
		return pImpuestos.getValue();
	}

	public double getImpuestosTotal() throws Exception {
		return pTotalImpuestos.getValue();
	}

	public double getImporteover() throws Exception {
		return pImporteover.getValue();
	}

	public double getImportecedido() throws Exception {
		return pImportecedido.getValue();
	}

	public void setPorcentajeover(double zValue) throws Exception {
		pPorcentajeover.setValue(zValue);
	}

	public double getPorcentajeover() throws Exception {
		return pPorcentajeover.getValue();
	}
	


	public void setNeto(double zValue) throws Exception {
		pNeto.setValue(zValue);
	}

	public double getNeto() throws Exception {
		return pNeto.getValue();
	}

	public void setOversobre(boolean zValue) throws Exception {
		pOversobre.setValue(zValue);
	}

	// public void setOffline(String zValue) throws Exception {
	// pOffline.setValue(zValue); }
	// public double getOffline() throws Exception { return pOffline.getValue();
	// }
	public String getLineaAerea() throws Exception {
		return pCodigoaerolinea.getValue();
	}

	public void setRefund(boolean value) throws Exception {
		pRefund.setValue(value);
	}

	public void setVoid(boolean value) throws Exception {
		if (value)
			PssLogger.logDebug("log point");
		pVoid.setValue(value);
	}

	public void setReemitted(boolean value) throws Exception {
		pReemitted.setValue(value);
	}
	public boolean isNullReemitted() throws Exception {
		return pReemitted.isNull();
	}
	public void setAdicionalFee(String value) throws Exception {
		pAditionalFee.setValue(value);
	}

	public double getAditionalFee() throws Exception {
		return pAditionalFee.getValue();
	}

	public void setOrdenFee(String value) throws Exception {
		pOrdenFee.setValue(value);
	}

	public String getOrdenFee() throws Exception {
		return pOrdenFee.getValue();
	}

	public void setConsumo(String value) throws Exception {
		pConsumo.setValue(value);
	}

	public String getConsumo() throws Exception {
		return pConsumo.getValue();
	}

	public void setConsumoNum(double value) throws Exception {
		pConsumoNum.setValue(value);
	}

	public double getConsumoNum() throws Exception {
		return pConsumoNum.getValue();
	}

	public void setPagoFee(String value) throws Exception {
		pPagoFee.setValue(value);
	}

	public String getPagoFee() throws Exception {
		return pPagoFee.getValue();
	}

	public void setCostoFee(String value) throws Exception {
		pCostoFee.setValue(value);
	}

	public double getCostoFee() throws Exception {
		return pCostoFee.getValue();
	}

	public boolean isReemited() throws Exception {
		return pReemitted.getValue();
	}

	public boolean isVoided() throws Exception {
		return pVoid.getValue();
	}

	public boolean isRefunded() throws Exception {
		return pRefund.getValue();
	}

	public boolean getOversobre() throws Exception {
		return pOversobre.getValue();
	}

	public void setAhorro() throws Exception {
		if (pHighFare.isNotNull() && pLowFare.isNotNull()) {
			pAhorro.setValue(pHighFare.getValue() - pLowFare.getValue());
			return;
		}

		if (pTarifaReal.isNull() || pTarifaReal.getValue() == 0)
			return;
		pAhorro.setValue(pTarifaReal.getValue() - pTarifa.getValue());

	}

	public void setComisionAmount(double zValue) {
		pComisionAmount.setValue(zValue);
	}

	public void setComisionPerc(double zValue) {
		pComisionPerc.setValue(zValue);
	}

	public double getComisionPerc() throws Exception {
		return pComisionPerc.getValue();
	}

	public void setMontoTarjeta(double value) throws Exception {
		this.pMontoTarjeta.setValue(value);
	}

	public double getMontoTarjeta() throws Exception {
		return this.pMontoTarjeta.getValue();
	}

	public void setNetRemit(String value) throws Exception {
		this.pNetRemit.setValue(value);
	}

	public String getNetRemit() throws Exception {
		return this.pNetRemit.getValue();
	}

	public void setNumeroTarjeta(String value) throws Exception {
		this.pNumeroTarjeta.setValue(value);
	}

	public void setNombreTarjeta(String value) throws Exception {
		this.pNombreTarjeta.setValue(value);
	}

	public String getNombreTarjeta() throws Exception {
		return this.pNombreTarjeta.getValue();
	}

	public void setCodigoMoneda(String value) throws Exception {
		this.pCodigoMoneda.setValue(value);
	}
	public void setCodigoMonedaLocal(String value) throws Exception {
		this.pCodigoMonedaLocal.setValue(value);
	}

	public void setCodigoBaseMoneda(String value) throws Exception {
		this.pCodigoBaseMoneda.setValue(value);
	}


	public void setTipoCambio(double value) throws Exception {
		this.pTipoCambio.setValue(value);
	}

	public void setTarifaBase(double value) throws Exception {
		this.pTarifaBase.setValue(value);
	}

	public void setTarifaBaseConImpuestos(double value) throws Exception {
		this.pTarifaBaseConImpuestos.setValue(value);
	}

	public void setTarifa(double value) throws Exception {
		this.pTarifa.setValue(value);
		setAhorro();
	}

	public double getTarifaFactura() throws Exception {
		return this.pTarifaFactura.getValue();
	}

	public void setTarifaLocal(double zValue) throws Exception {
		pTarifaLocal.setValue(zValue);
	}

	public void setTarifaNetoLocal(double zValue) throws Exception {
		pNetoLocal.setValue(zValue);
	}

	public void setTarifaNetoYQLocal(double zValue) throws Exception {
		pNetoYQLocal.setValue(zValue);
	}

	public void setTarifaFacturadaYQLocal(double zValue) throws Exception {
		pTarifaFacturadaYQLocal.setValue(zValue);
	}

	public void setTarifaBaseYQLocal(double zValue) throws Exception {
		pBaseYQLocal.setValue(zValue);
	}

	public double getTarifaLocal() throws Exception {
		return this.pTarifaLocal.getValue();
	}

	public double getTarifaNetoYQLocal() throws Exception {
		return this.pNetoYQLocal.getValue();
	}

	public double getTarifaFacturadaYQLocal() throws Exception {
		return this.pTarifaFacturadaYQLocal.getValue();
	}

	public double getTarifaBaseYQLocal() throws Exception {
		return this.pBaseYQLocal.getValue();
	}

	public double getTarifaNetoLocal() throws Exception {
		return this.pNetoLocal.getValue();
	}

	public double getTarifaUsa() throws Exception {
		return this.pTarifaUsa.getValue();
	}

	public double getTarifaNetoYQUsa() throws Exception {
		return this.pNetoYQUsa.getValue();
	}

	public double getTarifaFacturadaYQUsa() throws Exception {
		return this.pTarifaFacturadaYQUsa.getValue();
	}

	public double getTarifaBaseYQUsa() throws Exception {
		return this.pBaseYQUsa.getValue();
	}

	public double getTarifaNetoUsa() throws Exception {
		return this.pNetoUsa.getValue();
	}

	public void setTarifaFactura(double value) throws Exception {
		this.pTarifaFactura.setValue(value);
	}

	public double getTarifaBaseFactura() throws Exception {
		return this.pTarifaBaseFactura.getValue();
	}

	public void setTarifaBaseFactura(double value) throws Exception {
		this.pTarifaBaseFactura.setValue(value);
	}

	public double getIvaFactura() throws Exception {
		return this.pIvaFactura.getValue();
	}

	public void setIvaFactura(double value) throws Exception {
		this.pIvaFactura.setValue(value);
	}

	public double getNetoFactura() throws Exception {
		return this.pNetoFactura.getValue();
	}

	public void setNetoFactura(double value) throws Exception {
		this.pNetoFactura.setValue(value);
	}

	public double getImpuestoFactura() throws Exception {
		return this.pImpuestoFactura.getValue();
	}

	public void setImpuestoFactura(double value) throws Exception {
		this.pImpuestoFactura.setValue(value);
	}

	public double getTarifaTotalFactura() throws Exception {
		return this.pTarifaFacturaConImpuestos.getValue();
	}

	public void setTarifaTotalFacturaDolares(double value) throws Exception {
		this.pTarifaFacturaConImpuestosDolares.setValue(value);
	}
	public void setTarifaTotalFacturaLocal(double value) throws Exception {
		this.pTarifaFacturaConImpuestosLocal.setValue(value);
	}

	public double getTarifaTotalFacturaDolares() throws Exception {
		return this.pTarifaFacturaConImpuestosDolares.getValue();
	}

	public void setTarifaFacturaDolares(double value) throws Exception {
		this.pTarifaFacturaDolares.setValue(value);
	}

	public double getTarifaFacturaDolares() throws Exception {
		return this.pTarifaFacturaDolares.getValue();
	}

	public void setNetoFacturaDolares(double value) throws Exception {
		this.pNetoFacturaDolares.setValue(value);
	}

	public double getNetoFacturaDolares() throws Exception {
		return this.pNetoFacturaDolares.getValue();
	}

	public void setTarifaFacturaLocal(double value) throws Exception {
		this.pTarifaFacturaLocal.setValue(value);
	}

	public double getTarifaFacturaLocal() throws Exception {
		return this.pTarifaFacturaLocal.getValue();
	}

	public void setNetoFacturaLocal(double value) throws Exception {
		this.pNetoFacturaLocal.setValue(value);
	}

	public double getNetoFacturaLocal() throws Exception {
		return this.pNetoFacturaLocal.getValue();
	}

	public void setTarifaTotalFactura(double value) throws Exception {
		this.pTarifaFacturaConImpuestos.setValue(value);
	}

	public double getComisionFactura() throws Exception {
		return this.pComisionFactura.getValue();
	}

	public void setComisionFactura(double value) throws Exception {
		this.pComisionFactura.setValue(value);
	}

	public double getTarifa() throws Exception {
		return this.pTarifa.getValue();
	}

	public void setTarifaYQ(double value) throws Exception {
		this.pTarifaYQ.setValue(value);
	}

	public double getTarifaYQ() throws Exception {
		return this.pTarifaYQ.getValue();
	}

	public void setTarifaFacturadaYQ(double value) throws Exception {
		this.pTarifaFacturadaYQ.setValue(value);
	}

	public double getTarifaFaturadaYQ() throws Exception {
		return this.pTarifaFacturadaYQ.getValue();
	}

	public double getTarifaBaseConImpuestos() throws Exception {
		return this.pTarifaBaseConImpuestos.getValue();
	}

	public double getTarifaBase() throws Exception {
		return this.pTarifaBase.getValue();
	}

	public double getComisionAmount() throws Exception {
		return this.pComisionAmount.getValue();
	}

	public void setEmpleadoID(String value) throws Exception {
		this.pEmployee.setValue(value);
	}

	public String getEmpleadoID() throws Exception {
		return this.pEmployee.getValue();
	}

	public void setEmailID(String value) throws Exception {
		this.pEmailID.setValue(value);
	}

	public String getEmailID() throws Exception {
		return this.pEmailID.getValue();
	}

	public String getFechaAuthRemark() throws Exception {
		return this.pFechaAuth.getValue();
	}

	public void setFechaAuthRemark(String value) throws Exception {
		this.pFechaAuth.setValue(value);
	}

	public String getFechaCreationRemark() throws Exception {
		return this.pFechaCreation.getValue();
	}

	public void setFechaCreationRemark(String value) throws Exception {
		this.pFechaCreation.setValue(value);
	}

	public String getReasonCodeRemark() throws Exception {
		return this.pReasonCode.getValue();
	}

	public void setReasonCodeRemark(String value) throws Exception {
		this.pReasonCode.setValue(value);
	}

	public String getReasonCodeHotelRemark() throws Exception {
		return this.pReasonCodeHotel.getValue();
	}

	public void setReasonCodeHotelRemark(String value) throws Exception {
		this.pReasonCodeHotel.setValue(value);
	}

	public String getBusinessGroupRemark() throws Exception {
		return this.pBusinessGroup.getValue();
	}

	public void setBusinessGroupRemark(String value) throws Exception {
		this.pBusinessGroup.setValue(value);
	}

	public String getAvRemark() throws Exception {
		return this.pAV.getValue();
	}

	public void setAvRemark(String value) throws Exception {
		this.pAV.setValue(value);
	}

	public void setCliente(String value) throws Exception {
		this.pCliente.setValue(value);
	}

	public String getCliente() throws Exception {
		return this.pCliente.getValue();
	}

	public void setCodigoCliente(String value) throws Exception {
		this.pCodigoCliente.setValue(value);
	}

	public String getCodigoCliente() throws Exception {
		return this.pCodigoCliente.getValue();
	}

	public void setVendedor(String value) throws Exception {
		this.pVendedor.setValue(value);
	}

	public void setCentroCostos(String value) throws Exception {
		this.pCentroCostos.setValue(value);
	}

	public void setObservacion(String value) throws Exception {
		this.pObservacion.setValue(value);
	}

	public void setNroIata(String value) throws Exception {
		this.pNroIata.setValue(value);
	}
	public String getNroIata() throws Exception {
		return this.pNroIata.getValue();
	}
	public void setOfficeId(String value) throws Exception {
		this.pOfficeId.setValue(value);
	}
	public String getOfficeId() throws Exception {
		return this.pOfficeId.getValue();
	}
	public void setAgenteReserva(String value) throws Exception {
		this.pAgenteReserva.setValue(value);
	}

	public void setAgenteEmision(String value) throws Exception {
		this.pAgenteEmision.setValue(value);
	}

	public void setCityCode(String value) throws Exception {
		this.pCityCode.setValue(value);
	}

	public void setCreationDate(Date value) throws Exception {
		this.pCreationDate.setValue(value);
	}
	public void setPeriodo(String value) throws Exception {
		this.pPeriodo.setValue(value);
	}
	public String getPeriodo() throws Exception {
		return this.pPeriodo.getValue();
	}
	public void setVoidDate(Date value) throws Exception {
		this.pVoidDate.setValue(value);
	}
	public void setNullVoidDate() throws Exception {
		this.pVoidDate.setNull();
	}

	public void setInsertDate(Date value) throws Exception {
		this.insertDate.setValue(value);
	}

	public Date getInsertDate() throws Exception {
		return this.insertDate.getValue();
	}

	public boolean isNullCreationDate() throws Exception {
		return this.pCreationDate.isNull();
	}

	public boolean isNullVoidDate() throws Exception {
		return this.pVoidDate.isNull();
	}

	public void setPNRDate(Date value) throws Exception {
		this.pPNRDate.setValue(value);
	}

	public void setCreationDateAir(Date value) throws Exception {
		this.pCreationDateAir.setValue(value);
		this.pPeriodo.setValue(JDateTools.buildPeriod(value));
	}

	public Date getCreationDateAir() throws Exception {
		return this.pCreationDateAir.getValue();
	}
	public void setDirectory(String value) throws Exception {
		this.pDirectory.setValue(value);
	}

	public void setCustomerId(String value) throws Exception {
		this.pCustomerId.setValue(value);
	}

	public String getCustomerId() throws Exception {
		return this.pCustomerId.getValue();
	}

	public boolean isNullCustomerId() throws Exception {
		return this.pCustomerId.isNull();
	}

	public void setCustomerIdReducido(String value) throws Exception {
		this.pDKOriginal.setValue(PNRCache.getDkCachedSinSinonimo(getCompany(), getGDS(), this.pOfficeId.getValue(), this.pAgenteEmision.getValue() ,value));
		this.pCustomerIdReducido.setValue( PNRCache.getDkCached(getCompany(), getGDS(), this.pOfficeId.getValue(), this.pAgenteEmision.getValue() ,value));
	}

	public void setCustomerIdReducidoFromCustomer(String value) throws Exception {
		String cod = value;
		if (!(cod == null || cod.length() < 10)) {
			cod = value.substring(1, 3) + value.substring(6, 10);
		}
		this.setCustomerIdReducido(cod);
	}
	public String getDKOriginal() throws Exception {
		return this.pDKOriginal.getValue();
	}
	public String getCustomerIdReducido() throws Exception {
		return this.pCustomerIdReducido.getValue();
	}

	public boolean hasCustomerIdReducido() throws Exception {
		return !isNullCustomerIdReducido() && !this.pCustomerIdReducido.getValue().equals("");
	}
	public boolean isNullDKOriginal() throws Exception {
		return this.pDKOriginal.isNull();
	}
	public boolean isNullCustomerIdReducido() throws Exception {
		return this.pCustomerIdReducido.isNull();
	}

	public void setRoute(String value) throws Exception {
		this.pRoute.setValue(value);
		if (value != null && value.length() > 3)
			this.pMiniRoute.setValue(pAeropuertoOrigen.getValue() + "/" + pAeropuertoDestino.getValue());
	}
	
	public String getRoute() throws Exception {
		return pRoute.getValue();
	}

	public void setMiniRoute(String value) throws Exception {
		this.pMiniRoute.setValue(value);
	}
	public String getMiniRoute() throws Exception {
		 return this.pMiniRoute.getValue();
	}
	public void setMarket(String value) throws Exception {
		this.pMarket.setValue(value);
	}

	public String getMarket() throws Exception {
		return this.pMarket.getValue();
	}

	public void setAirIntinerary(String value) throws Exception {
		this.pAirIntinerary.setValue(value);
	}

	public String getAirIntinerary() throws Exception {
		return this.pAirIntinerary.getValue();
	}
	public void setFareIntinerary(String value) throws Exception {
		this.pFareIntinerary.setValue(value);
	}

	public String getFareIntinerary() throws Exception {
		return this.pFareIntinerary.getValue();
	}
	public void setNroFlIntinerary(String value) throws Exception {
		this.pNroFlIntinerary.setValue(value);
	}

	public String getNroFlIntinerary() throws Exception {
		return this.pNroFlIntinerary.getValue();
	}
	public void setMarketIntinerary(String value) throws Exception {
		this.pMarketIntinerary.setValue(value);
	}

	public String getMarketIntinerary() throws Exception {
		return this.pMarketIntinerary.getValue();
	}
	public void setAirGenIntinerary(String value) throws Exception {
		this.pAirGenIntinerary.setValue(value);
	}

	public String getAirGenIntinerary() throws Exception {
		return this.pAirGenIntinerary.getValue();
	}

	public void setAirPaisIntinerary(String value) throws Exception {
		this.pAirPaisIntinerary.setValue(value);
	}

	public String getAirPaisIntinerary() throws Exception {
		return this.pAirPaisIntinerary.getValue();
	}
	public void setCarrierIntinerary(String value) throws Exception {
		this.pCarrierIntinerary.setValue(value);
	}

	public String getCarrierIntinerary() throws Exception {
		return this.pCarrierIntinerary.getValue();
	}

	public void setClassIntinerary(String value) throws Exception {
		this.pClassIntinerary.setValue(value);
	}

	public String getClassIntinerary() throws Exception {
		return this.pClassIntinerary.getValue();
	}

	public void setFechaIntinerary(String value) throws Exception {
		this.pFechaIntinerary.setValue(value);
	}

	public String getFechaIntinerary() throws Exception {
		return this.pFechaIntinerary.getValue();
	}

	public void setDepartureDate(Date value) throws Exception {
		this.pDepartureDate.setValue(value);
	}

	public void setArriveDate(Date value) throws Exception {
		this.pArriveDate.setValue(value);
	}

	public void setEndTravelDate(Date value) throws Exception {
		this.pEndTravelDate.setValue(value);
	}

	public void setFechaProcesamiento(Date value) throws Exception {
		this.pFechaProcesamiento.setValue(value);
	}

	public Date getCreationDate() throws Exception {
		return this.pCreationDate.getValue();
	}
	public Date getVoidDate() throws Exception {
		return this.pVoidDate.getValue();
	}
	public Date getPNRDate() throws Exception {
		return this.pPNRDate.getValue();
	}

	public Date getFechaProcesamiento() throws Exception {
		return this.pFechaProcesamiento.getValue();
	}

	public String getDirectory() throws Exception {
		return this.pDirectory.getValue();
	}

	public String getArchivo() throws Exception {
		return this.pArchivo.getValue();
	}

	public Date getDepartureDate() throws Exception {
		return this.pDepartureDate.getValue();
	}

	public Date getArriveDate() throws Exception {
		return this.pArriveDate.getValue();
	}

	public Date getEndTravelDate() throws Exception {
		return this.pEndTravelDate.getValue();
	}

	public void setTransactionType(String value) throws Exception {
		this.pTransactionType.setValue(value);
	}

	// public void setIndicadorVenta(String value) throws Exception {
	// this.pIndicadorVenta.setValue(value);
	// }
	public void setArchivo(String value) throws Exception {
		this.pArchivo.setValue(value);
	}

	public void setVersion(String value) throws Exception {
		this.pVersion.setValue(value);
	}

	public void setGDS(String value) throws Exception {
		this.pGDS.setValue(value);
	}
	
	public String getGDS() throws Exception {
		
		return this.pGDS.getValue();
	}

	public void setOrigen(String value) throws Exception {
		this.pOrigen.setValue(value);
	}

	public void setRemarks(String value) throws Exception {
		this.pRemarks.setValue(value);
	}

	public double getTipoCambio() throws Exception {
		return this.pTipoCambio.getValue();
	}

	public String getOrigen() throws Exception {
		return this.pOrigen.getValue();
	}

	/**
	 * Constructor de la Clase
	 */
	public BizPNRTicket() throws Exception {
		// this.SetTipoArticulo(BizArticulo.ART_PNR);
	}

	public void createProperties() throws Exception {
		this.addItem("interface_id", pId);
		this.addItem("company", pCompany);
		this.addItem("CodigoPNR", pCodigopnr);
		this.addItem("pnr_date", pPNRDate);
		this.addItem("insert_date", insertDate);
		this.addItem("creation_date", pCreationDate);
		this.addItem("creation_date_date", pCreationDateAir);
		this.addItem("void_date", pVoidDate);
		this.addItem("CodigoAerolinea", pCodigoaerolinea);
		this.addItem("tarifa", pTarifa);
		this.addItem("aeropuerto_origen", pAeropuertoOrigen);
		this.addItem("aeropuerto_destino", pAeropuertoDestino);
		this.addItem("NumeroBoleto", pNumeroboleto);
		this.addItem("Internacional", pInternacional);
		this.addItem("Internacional_descr", pInternacionalDescr);
		this.addItem("cant_segmentos", pCantidadSegmentos);
		this.addItem("cant_roundtrip", pCantidadRoundTrip);
		this.addItem("CantidadConectados", pCantidadconectados);
		this.addItem("pre_dias_compra", pDiasPreCompras);
		this.addItem("dias_compra", pDiasCompras);
		this.addItem("dias_viajados", pDiasViajados);
		this.addItem("NumeroPasajero", pNumeropasajero);
		this.addItem("nombre_pasajero", pNombrePasajero);
		this.addItem("ident_fiscal", pIdentFiscal);
		this.addItem("tipo_pasajero", pTipoPasajero);
		this.addItem("tipo_pasajero2", pTipoPasajero2);
		this.addItem("TipoBoleto", pTipoboleto);
		// this.addItem( "Venta", pVenta );
		// this.addItem( "TarifaEconomica", pTarifaeconomica );
		// this.addItem( "TarifaNormal", pTarifanormal );
		// this.addItem( "AplicaComparativo", pAplicacomparativo );
		// this.addItem( "AplicaCentro", pAplicacentro );
		// this.addItem( "NumeroEmpleado", pNumeroempleado );
		this.addItem("codigo_aerolinea_intern", pCodigoaerolineaIntern);
		this.addItem("descripcion", pDescripcion);
		this.addItem("it", pIt);
		this.addItem("imagen1", pImagen1);
		// this.addItem( "boletocambio", pBoletocambio );
		this.addItem("importeover", pImporteover);
		this.addItem("importeover_rmk", pImporteoverRmk);
		this.addItem("tarifa_real", pTarifaReal);
		this.addItem("tarifa_usa", pTarifaUsa);
		this.addItem("neto_usa", pNetoUsa);
		this.addItem("tarifa_facturada_yq_usa", pTarifaFacturadaYQUsa);
		this.addItem("netoyq_usa", pNetoYQUsa);
		this.addItem("baseyq_usa", pBaseYQUsa);
		
		this.addItem("tarifa_local", pTarifaLocal);
		this.addItem("neto_local", pNetoLocal);
		this.addItem("tarifa_facturada_yq_local", pTarifaFacturadaYQLocal);
		this.addItem("netoyq_local", pNetoYQLocal);
		this.addItem("baseyq_local", pBaseYQLocal);
		this.addItem("tarifa_factura_local", pTarifaFacturaLocal);
		this.addItem("neto_factura_local", pNetoFacturaLocal);
		this.addItem("tarifa_factura_total_local", pTarifaFacturaConImpuestosLocal);

		this.addItem("ahorro", pAhorro);
		this.addItem( "ivaover", pIvaover );
		this.addItem("importecedido", pImportecedido);
		this.addItem("porcentajeover", pPorcentajeover);
		this.addItem("porcentaje_prorr_over", pPorcentajeProrrateoOver);
		this.addItem("importe_prorr_over", pImporteProrrateoOver);
		this.addItem("neto", pNeto);
		this.addItem("oversobre", pOversobre);
		// this.addItem( "off_line", pOffline );
		this.addItem("additional_fee", pAditionalFee);
		this.addItem("costo_fee", pCostoFee);
		this.addItem("orden_fee", pOrdenFee);
		this.addItem("pago_fee", pPagoFee);
		this.addItem("expense", pExpense);
		this.addItem("porc_expense", pPorcExpense);
		this.addItem("consumo", pConsumo);
		this.addItem("consumo_num", pConsumoNum);
		this.addItem("clase", pClase);
		this.addItem("tipo_clase", pTipoClase);
		this.addItem("refund", pRefund);
		this.addItem("void", pVoid);
		this.addItem("reemitted", pReemitted);
		this.addItem("exchanged", pExchanged);
		this.addItem("open", pOpen);
		this.addItem("boleto_original", pOriginalNumeroboleto);
		this.addItem("tour_code", pTourCode);
		this.addItem("ref_original", pRefOriginal);
		this.addItem("order_str", pOrder);
		this.addItem("impuestos", pImpuestos);
		this.addItem("impuestos_total", pTotalImpuestos);
		this.addItem("impuestos_total_factura", pTotalImpuestosFacturaa);
		this.addItem("iva", pIva);
		this.addItem("yq", pYQ);
		
		this.addItem("q", pQ);
		this.addItem("q_usd", pQUsd);
		this.addItem("q_local", pQLocal);

		this.addItem("is_interlineal", pIsInterlineal);
		this.addItem("is_complete", pIsComplete);

		this.addItem("highfare", pHighFare);
		this.addItem("lowfare", pLowFare);
		this.addItem("corporativo", pCorporativo);
		this.addItem("proposito", pProposito);
		this.addItem("cuenta", pCuenta);
		this.addItem("clase_tarifa", pClaseTarifa);
		this.addItem("autorizante", pAutorizante);
		this.addItem("fare_savings", pFareSavings);
		this.addItem("codigo_negocio", pCodigoNegocio);
		this.addItem("region", pRegion);
		this.addItem("departamento", pDepartamento);
		this.addItem("autoriz_cc", pAutorizCC);
		this.addItem("seg_cc", pSegCC);
		this.addItem("solicitante", pSolicitante);
		this.addItem("pivatefareindicator", pPrivateFareIndicator);
		this.addItem("tipo_operacion", pTipoOperacion);
		this.addItem("is_ticket", pIsTicket);
		this.addItem("is_emision", pEmision);
		this.addItem("reserva", pReserva);
		this.addItem("vuelta_mundo", pVueltaAlMundo);
		
		this.addItem("tipo_prestacion", pTipoPrestacion);
		this.addItem("comision_amount", pComisionAmount);
		this.addItem("comision_perc", pComisionPerc);
		this.addItem("codigo_moneda", pCodigoMoneda);
		this.addItem("codigo_base_moneda", pCodigoBaseMoneda);
		this.addItem("codigo_moneda_local", pCodigoMonedaLocal);
		this.addItem("tipo_cambio", pTipoCambio);
		this.addItem("tarifa_base", pTarifaBase);
		this.addItem("tarifa_base_contax", pTarifaBaseConImpuestos);
		this.addItem("tarifa_yq", pTarifaYQ);
		this.addItem("tarifa_facturada_yq", pTarifaFacturadaYQ);

		this.addItem("iva_factura", pIvaFactura);
		this.addItem("neto_factura", pNetoFactura);
		this.addItem("tarifa_base_factura", pTarifaBaseFactura);
		this.addItem("tarifa_factura", pTarifaFactura);
		this.addItem("tarifa_factura_total", pTarifaFacturaConImpuestos);
		this.addItem("tarifa_factura_usa", pTarifaFacturaDolares);
		this.addItem("neto_factura_usa", pNetoFacturaDolares);
		this.addItem("tarifa_factura_total_usa", pTarifaFacturaConImpuestosDolares);
		this.addItem("impuesto_factura", pImpuestoFactura);
		this.addItem("comision_factura", pComisionFactura);

		this.addItem("codigo_cliente", pCodigoCliente);
		this.addItem("cliente", pCliente);
		this.addItem("employee", pEmployee);
		this.addItem("email_id", pEmailID);
		this.addItem("av_rmk", pAV);
		this.addItem("bg_rmk", pBusinessGroup);
		this.addItem("fa_rmk", pFechaAuth);
		this.addItem("fc_rmk", pFechaCreation);
		this.addItem("rc_rmk", pReasonCode);
		this.addItem("rch_rmk", pReasonCodeHotel);
		this.addItem("vendedor", pVendedor);
		this.addItem("centro_costos", pCentroCostos);
		this.addItem("observacion", pObservacion);
		this.addItem("nombre_tarjeta", pNombreTarjeta);
		this.addItem("monto_tarjeta", pMontoTarjeta);
		this.addItem("numero_tarjeta", pNumeroTarjeta);
		this.addItem("numero_tarjeta_mask", pNumeroTarjetaEnmascarada);

		this.addItem("nro_iata", pNroIata);
		this.addItem("office_id", pOfficeId);
		this.addItem("agente_reserva", pAgenteReserva);
		this.addItem("agente_emision", pAgenteEmision);
		this.addItem("city_code", pCityCode);
		this.addItem("directory", pDirectory);
		this.addItem("fecha_proc", pFechaProcesamiento);
		this.addItem("periodo", pPeriodo);
		this.addItem("customer_id", pCustomerId);
		this.addItem("customer_id_reducido", pCustomerIdReducido);
		this.addItem("dk_original", pDKOriginal);
		this.addItem("route", pRoute);
		this.addItem("mini_route", pMiniRoute);
		this.addItem("market", pMarket);
		this.addItem("transaction_type", pTransactionType);
		this.addItem("tipo_prestacion", pTipoPrestacion);
		this.addItem("net_remit", pNetRemit);

		// this.addItem("indicador_venta", pIndicadorVenta);
		this.addItem("archivo", pArchivo);
		this.addItem("gds", pGDS);
		this.addItem("origen", pOrigen);

		this.addItem("pais", pPais);

		this.addItem("departure_date", pDepartureDate);
		this.addItem("arrive_date", pArriveDate);
		this.addItem("endtravel_date", pEndTravelDate);

		this.addItem("air_intinerary", pAirIntinerary);
		this.addItem("fare_intinerary", pFareIntinerary);
		this.addItem("air_pais_intinerary", pAirPaisIntinerary);
		this.addItem("air_gen_intinerary", pAirGenIntinerary);
		this.addItem("carrier_intinerary", pCarrierIntinerary);
		this.addItem("class_intinerary", pClassIntinerary);
		this.addItem("fecha_intinerary", pFechaIntinerary);
		this.addItem("nro_flight_intinerary", pNroFlIntinerary);
		this.addItem("market_intinerary", pMarketIntinerary);
		// this.addItem("ciudad_origen", pCiudadOrigen);
		// this.addItem("ciudad_destino", pCiudadDestino);
		this.addItem("pais_origen", pPaisOrigen);
		this.addItem("pais_destino", pPaisDestino);
		// this.addItem("region_origen", pRegionOrigen);
		// this.addItem("region_destino", pRegionDestino);
		// this.addItem("continente_origen", pContinenteOrigen);
		// this.addItem("continente_destino", pContinenteDestino);
		// this.addItem("geo_origen", pGeoOrigen);
		// this.addItem("geo_destino", pGeoDestino);
		// this.addItem("obj_aeropuerto_origen", pObjAeropuertoOrigen);
		// this.addItem("obj_aeropuerto_destino", pObjAeropuertoDestino);
		this.addItem("remarks", pRemarks);
		this.addItem("migrated", pMigrated);
		this.addItem("endoso", pEndoso);
		this.addItem("upfront_ref", pUpfrontRef);
		this.addItem("fecha_calc_over", pFechaCalcOver);
		this.addItem("update_version_over", pUpdateVersion);
		this.addItem("upfront_descripcion", pUpfrontDescripcion);
		
		this.addItem("fecha_calc_over_back", pFechaCalcOverBack);
		this.addItem("backend_descripcion", pBackendDescripcion);
		this.addItem("calculed", pCalculed);
		this.addItem("over_sobre_back", pOversobreBack);
		this.addItem("contratos_back", pContratosBack);
		this.addItem("comision_perc_back", pComisionPercBack);
		this.addItem("comision_over_back", pComisionOverBack);
	
		
		this.addItem("month", pMonth);
		this.addItem("year", pYear);
		this.addItem("di", pDI);
		this.addItem("oracle_numero", pOracleNumero);
		this.addItem("oracle_serie", pOracleSerie);
		this.addItem("bsp_codecarrier", pBspCodeCarrier);
		this.addItem("bsp_carrier", pBspCarrier);
		this.addItem("bsp_iata", pBspIata);
		this.addItem("ttl", pBspTTL);
		this.addItem("mercado_origen", pMercadoOrigen);
		this.addItem("mercado_destino", pMercadoDestino);
		this.addItem("bsp_carrier2", pBspCarrier2);
		this.addItem("bsp_porc", pBspPorc);
		this.addItem("bsp_comision", pBspComision);
		this.addItem("bsp_tipo_operacion", pBspTipoComprobante);
		this.addItem("dif_porcentajeover", pDifBspPorc);
		this.addItem("dif_importeover", pDifBspComision);
				
		


	}

	JString pOrder = new JString();

	/**
	 * Adds the fixed object properties
	 */
	@Override
	public void createFixedProperties() throws Exception {
		this.addFixedItem(KEY, "interface_id", "Id Interfaz", false, false, 18);
		this.addFixedItem(FIELD, "company", "Empresa", true, false, 15);
		this.addFixedItem(FIELD, "pnr_date", "Fecha PNR", true, false, 10);
		this.addFixedItem(FIELD, "insert_date", "Fecha Insert", true, false, 10);
		this.addFixedItem(FIELD, "creation_date", "Fecha Emisión PNR", true, false, 10);
		this.addFixedItem(FIELD, "creation_date_date", "Fecha Emisión Doc", true, false, 10);
		this.addFixedItem(FIELD, "void_date", "Fecha Anulación", true, false, 10);
		this.addFixedItem(FIELD, "departure_date", "Fecha Salida", true, false, 10);
		this.addFixedItem(FIELD, "arrive_date", "Fecha Llegada", true, false, 10);
		this.addFixedItem(FIELD, "endtravel_date", "Fecha Fin Viaje", true, false, 10);
		this.addFixedItem(FIELD, "CodigoAerolinea", "Aerolínea Cod.", true, true, 2);
		this.addFixedItem(FIELD, "aeropuerto_origen", "Cod.Aeropuerto Origen", true, false, 10);
		this.addFixedItem(FIELD, "aeropuerto_destino", "Cod.Aeropuerto Destino", true, false, 10);
		this.addFixedItem(FIELD, "tarifa", "Tarifa", true, false, 18, 4);
		this.addFixedItem(FIELD, "CodigoPNR", "Codigo PNR", true, true, 10);
		this.addFixedItem(FIELD, "NumeroBoleto", "Nro.Boleto", true, true, 50);

		this.addFixedItem(FIELD, "codigo_aerolinea_intern", "Primer Aerolínea Internac.", true, true, 2);
		this.addFixedItem(FIELD, "Internacional", "Internacional", true, true, 1);
		this.addFixedItem(FIELD, "internacional_descr", "Domestic/International", true, true, 100);
		this.addFixedItem(FIELD, "route", "Ruta", true, false, 300);
		this.addFixedItem(FIELD, "mini_route", "Origen/Destino", true, false, 300);
		this.addFixedItem(FIELD, "market", "Mercados", true, false, 1000);

		this.addFixedItem(FIELD, "pre_dias_compra", "Anticipacion pre-compra", true, true, 18);
		this.addFixedItem(FIELD, "dias_compra", "Anticipacion compra", true, true, 18);
		this.addFixedItem(FIELD, "dias_viajados", "días viajados", true, true, 18);
		this.addFixedItem(FIELD, "cant_segmentos", "Cantidad bookings", true, true, 18);
		this.addFixedItem(FIELD, "cant_roundtrip", "Cantidad roundtrip", true, true, 18);
		this.addFixedItem(FIELD, "CantidadConectados", "Cantidad conectados", true, true, 5);
		this.addFixedItem(FIELD, "NumeroPasajero", "Número Pasajero", true, true, 3);
		this.addFixedItem(FIELD, "tipo_pasajero", "Tipo Pasajero", true, true, 3);
		this.addFixedItem(VIRTUAL, "tipo_pasajero2", "Tipo Pasajero", true, true, 3);
		this.addFixedItem(FIELD, "nombre_pasajero", "Nombre Pasajero", true, false, 150);
		this.addFixedItem(FIELD, "ident_fiscal", "Identificador fiscal", true, false, 50);
		this.addFixedItem(FIELD, "TipoBoleto", "Tipo boleto", true, true, 1);
		this.addFixedItem(FIELD, "migrated", "Migrado", true, false, 1);
		// this.addFixedItem( FIELD, "Venta", "Venta", true, true, 50 );
		// this.addFixedItem( FIELD, "TarifaEconomica", "Tarifa economica",
		// true,
		// true, 18,4 );
		// this.addFixedItem( FIELD, "TarifaNormal", "Tarifa normal", true,
		// true,
		// 18, 4 );
		// this.addFixedItem( FIELD, "AplicaComparativo", "Aplica comparativo",
		// true, true, 1 );
		// this.addFixedItem( FIELD, "AplicaCentro", "Aplica centro", true,
		// true, 1
		// );
		// this.addFixedItem( FIELD, "NumeroEmpleado", "Numero empleado", true,
		// true, 5 );

		this.addFixedItem(FIELD, "highfare", "Tarifa alta", true, false, 18, 2);
		this.addFixedItem(FIELD, "lowfare", "Tarifa baja", true, false, 18, 2);
		this.addFixedItem(FIELD, "corporativo", "Corporativo", true, false, 1);
		this.addFixedItem(FIELD, "proposito", "Proposito del viaje", true, false, 300);
		this.addFixedItem(FIELD, "cuenta", "Cuenta", true, false, 300);
		this.addFixedItem(FIELD, "clase_tarifa", "Clase tarifa", true, false, 300);
		this.addFixedItem(FIELD, "autorizante", "Autorizante", true, false, 300);
		this.addFixedItem(FIELD, "fare_savings", "Codigo ahorro", true, false, 300);
		this.addFixedItem(FIELD, "codigo_negocio", "Codigo negocio", true, false, 300);
		this.addFixedItem(FIELD, "region", "Region", true, false, 300);
		this.addFixedItem(FIELD, "solicitante", "Solicitante", true, false, 300);
		this.addFixedItem(FIELD, "pivatefareindicator", "Tipo tarifa", true, false, 3);
		this.addFixedItem(FIELD, "tipo_operacion", "Tipo Operacion", true, false, 300);
		this.addFixedItem(FIELD, "is_ticket", "Es ticket", true, false, 300);
		this.addFixedItem(FIELD, "is_emision", "Es Emision", true, false, 1);
		this.addFixedItem(FIELD, "departamento", "Departamento", true, false, 300);
		this.addFixedItem(FIELD, "autoriz_cc", "Aut.Tarjeta", true, false, 300);
		this.addFixedItem(FIELD, "seg_cc", "Cod.Seg.Tarj", true, false, 300);
		this.addFixedItem(FIELD, "reserva", "Fecha reserva", true, false, 300);
		this.addFixedItem(FIELD, "vuelta_mundo", "Vuelta al mundo", true, false, 1);

		this.addFixedItem(FIELD, "codigo_moneda", "Moneda", true, false, 3);
		this.addFixedItem(FIELD, "codigo_moneda_local", "Moneda Local", true, false, 3);
		this.addFixedItem(FIELD, "codigo_base_moneda", "Moneda Base", true, false, 3);
		this.addFixedItem(FIELD, "tarifa_base", "Tarifa (Mon.Base)", true, false, 18, 4);
		this.addFixedItem(FIELD, "tarifa_base_contax", "Tarifa e impuestos (Mon.Base)", true, false, 18, 4);
		this.addFixedItem(FIELD, "tarifa_yq", "Tarifa e YQ", true, false, 18, 4);
		this.addFixedItem(FIELD, "neto", "Neto", true, false, 18, 4);

		this.addFixedItem(FIELD, "iva_factura", "Iva facturado", true, false, 18, 4);
		this.addFixedItem(FIELD, "neto_factura", "Neto facturado", true, false, 18, 4);
		this.addFixedItem(FIELD, "tarifa_factura", "Tarifa facturada", true, false, 18, 4);
		this.addFixedItem(FIELD, "tarifa_facturada_yq", "Tarifa e YQ facturada", true, false, 18, 4);
		this.addFixedItem(FIELD, "tarifa_factura_total", "Tarifa e impuestos facturada ", true, false, 18, 4);
		this.addFixedItem(FIELD, "tarifa_base_factura", "Tarifa facturada (Mon.Base)", true, false, 18, 4);
		this.addFixedItem(FIELD, "tarifa_factura_total_usa", "Tarifa e impuestos facturada (USD)", true, false, 18, 4);
		this.addFixedItem(FIELD, "tarifa_factura_usa", "Tarifa facturada (USD)", true, false, 18, 4);
		this.addFixedItem(FIELD, "neto_factura_usa", "Neto facturada (USD)", true, false, 18, 4);
		this.addFixedItem(FIELD, "tarifa_facturada_yq_usa", "Tarifa e YQ facturada (USD)", true, true, 18, 4);
		this.addFixedItem(FIELD, "impuesto_factura", "Impuestos facturada", true, false, 18, 4);
		this.addFixedItem(FIELD, "comision_factura", "Comision facturada", true, false, 18, 4);

		this.addFixedItem(FIELD, "descripcion", "Descripción", true, true, 50);
		this.addFixedItem(FIELD, "it", "It", true, true, 50);
		// this.addFixedItem( FIELD, "boletocambio", "Boleto cambio", true,
		// true, 50
		// );
		this.addFixedItem(FIELD, "importeover", "Over", true, true, 18, 4);
		this.addFixedItem(FIELD, "importeover_rmk", "Over Remark", true, true, 18, 4);
		this.addFixedItem(FIELD, "tarifa_real", "Tarifa original", true, true, 18, 4);
		this.addFixedItem(FIELD, "tarifa_usa", "Tarifa (USD)", true, true, 18, 4);
		
		this.addFixedItem(FIELD, "neto_usa", "Neto (USD)", true, true, 18, 4);
		this.addFixedItem(FIELD, "netoyq_usa", "Neto YQ (USD)", true, true, 18, 4);
		this.addFixedItem(FIELD, "baseyq_usa", "Tarifa YQ (USD)", true, true, 18, 4);

		this.addFixedItem(FIELD, "tarifa_local", "Tarifa (local)", true, true, 18, 4);
		this.addFixedItem(FIELD, "neto_local", "Neto (local)", true, true, 18, 4);
		this.addFixedItem(FIELD, "netoyq_local", "Neto YQ (local)", true, true, 18, 4);
		this.addFixedItem(FIELD, "baseyq_local", "Tarifa YQ (local)", true, true, 18, 4);
		this.addFixedItem(FIELD, "tarifa_factura_total_local", "Tarifa e impuestos facturada (local)", true, false, 18, 4);
		this.addFixedItem(FIELD, "tarifa_factura_local", "Tarifa facturada (local)", true, false, 18, 4);
		this.addFixedItem(FIELD, "neto_factura_local", "Neto facturada (local)", true, false, 18, 4);
		this.addFixedItem(FIELD, "tarifa_facturada_yq_local", "Tarifa e YQ facturtada (local)", true, true, 18, 4);

		
		this.addFixedItem(FIELD, "ahorro", "Ahorro", true, true, 18, 4);
		this.addFixedItem( FIELD, "ivaover", "Iva Over", true, true, 7,2 );
		this.addFixedItem(FIELD, "importecedido", "Over Cedido", true, true, 18, 4);
		this.addFixedItem(FIELD, "iva", "Iva", true, true, 18, 4);
		this.addFixedItem(FIELD, "yq", "YQ", true, true, 18, 4);
		
		this.addFixedItem(FIELD, "q", "Q", true, false, 18, 4);
		this.addFixedItem(FIELD, "q_usd", "Q (USD)", true, false, 18, 4);
		this.addFixedItem(FIELD, "q_local", "Q (local)", true, false, 18, 4);
		
		this.addFixedItem(FIELD, "impuestos", "Impuestos", true, true, 18, 4);
		this.addFixedItem(FIELD, "impuestos_total", "Impuestos total", true, true, 18, 4);
		this.addFixedItem(FIELD, "impuestos_total_factura", "Impuestos total facturada", true, true, 18, 4);
		this.addFixedItem(FIELD, "porcentajeover", "Porcentaje over", true, true, 7, 2);

		this.addFixedItem(FIELD, "porcentaje_prorr_over", "Porc.Prorr.over", true, true, 7, 2);
		this.addFixedItem(FIELD, "importe_prorr_over", "Prorr.Over", true, true, 18, 4);

		this.addFixedItem(FIELD, "oversobre", "Tiene Over", true, true, 1);
		// this.addFixedItem( FIELD, "off_line", "Offline", true, true, 1 );
		this.addFixedItem(FIELD, "additional_fee", "Fee", true, false, 18, 4);
		this.addFixedItem(FIELD, "orden_fee", "Orden solicitada Fee", true, false, 50);
		this.addFixedItem(FIELD, "pago_fee", "Forma Pago Fee", true, false, 50);
		this.addFixedItem(FIELD, "costo_fee", "Costo Fee", true, false, 18, 4);
		this.addFixedItem(FIELD, "void", "Anulado?", true, true, 1);
		this.addFixedItem(FIELD, "refund", "Devuelto?", true, true, 1);
		this.addFixedItem(FIELD, "reemitted", "Revisado?", true, true, 1);
		this.addFixedItem(VIRTUAL, "exchanged", "Exchanged?", true, false, 1);
		this.addFixedItem(VIRTUAL, "open", "Open?", true, false, 1);
		this.addFixedItem(FIELD, "boleto_original", "Numero boleto original", true, true, 50);
		this.addFixedItem(FIELD, "tour_code", "Tour code", true, true, 50);
		this.addFixedItem(FIELD, "ref_original", "referencia boleto original", true, true, 18, 0);
		this.addFixedItem(FIELD, "expense", "Expense", true, false, 18, 4);
		this.addFixedItem(FIELD, "porc_expense", "Porc Expense", true, false, 7, 2);
		this.addFixedItem(FIELD, "consumo", "Consumo Literal", true, false, 50);
		this.addFixedItem(FIELD, "consumo_num", "Consumo", true, false, 18);
		this.addFixedItem(FIELD, "is_interlineal", "Interlineal", true, true, 1);
		this.addFixedItem(FIELD, "is_complete", "Generado por m2", true, true, 1);
		// this.addFixedItem( FIELD, "iva_expense", "Iva Expense", true, false,
		// 7,2
		// );
		this.addFixedItem(FIELD, "tipo_prestacion", "Tipo Prestacion", true, false, 1);
		this.addFixedItem(FIELD, "net_remit", "Net remit", true, false, 500);
		this.addFixedItem(VIRTUAL, "order_str", "Orden", true, false, 10);
		// this.addFixedItem( FIELD, "referencia", "Referencia", true, false,
		// 50);
		// this.addFixedItem( FIELD, "over_cedido_iva_retenido", "Over Cedido
		// Iva
		// Retenido", true, false, 1);
		// this.addFixedItem( FIELD, "devolucion", "DEvolución", true, false,
		// 1);
		// this.addFixedItem( FIELD, "reemision", "Reemisión", true, false, 1);
		// this.addFixedItem( FIELD, "ajuste", "Ajuste", true, false, 1);
		this.addFixedItem(FIELD, "comision_amount", "Comision", true, false, 18, 4);
		this.addFixedItem(FIELD, "comision_perc", "Comision %", true, false, 7, 2);
		this.addFixedItem(FIELD, "tipo_cambio", "Tipo de cambio", true, false, 7, 2);
		this.addFixedItem(FIELD, "cliente", "Cliente(Remark)", true, false, 100);
		this.addFixedItem(FIELD, "codigo_cliente", "Codigo Cliente(Remark)", true, false, 20);
		this.addFixedItem(FIELD, "employee", "Empleado ID(Remark)", true, false, 100);
		this.addFixedItem(FIELD, "email_id", "Email ID(Remark)", true, false, 100);
		this.addFixedItem(FIELD, "av_rmk", "AV(Remark)", true, false, 100);
		this.addFixedItem(FIELD, "bg_rmk", "Business group(Remark)", true, false, 100);
		this.addFixedItem(FIELD, "fa_rmk", "Fecha auth(Remark)", true, false, 100);
		this.addFixedItem(FIELD, "fc_rmk", "Fecha creation(Remark)", true, false, 100);
		this.addFixedItem(FIELD, "rc_rmk", "Reason Code(Remark)", true, false, 100);
		this.addFixedItem(FIELD, "rch_rmk", "Reason code hotel(Remark)", true, false, 100);
		this.addFixedItem(FIELD, "vendedor", "Vendedor", true, false, 20);
		this.addFixedItem(FIELD, "centro_costos", "Centro Costos", true, false, 20);
		this.addFixedItem(FIELD, "observacion", "Observación", true, false, 100);
		this.addFixedItem(FIELD, "nombre_tarjeta", "Tarjeta", true, false, 50);
		this.addFixedItem(FIELD, "monto_tarjeta", "Monto Tarjeta", true, false, 18, 4);
		this.addFixedItem(FIELD, "numero_tarjeta", "Número Tarjeta", true, false, 30).setHide(true);
		this.addFixedItem(VIRTUAL, "numero_tarjeta_mask", "Número Tarjeta", true, false, 30);

		this.addFixedItem(FIELD, "nro_iata", "Número IATA", true, false, 30);
		this.addFixedItem(FIELD, "office_id", "Office ID", true, false, 30);
		this.addFixedItem(FIELD, "agente_reserva", "Agente reserva", true, false, 30);
		this.addFixedItem(FIELD, "agente_emision", "Agente emisión", true, false, 30);
		this.addFixedItem(FIELD, "city_code", "City Code", true, false, 30);
		this.addFixedItem(FIELD, "customer_id", "Cliente", true, false, 18);
		this.addFixedItem(FIELD, "customer_id_reducido", "DK", true, false, 18);
		this.addFixedItem(FIELD, "dk_original", "DK Original", true, false, 18);
		this.addFixedItem(FIELD, "transaction_type", "Tx Type", true, false, 5);
		this.addFixedItem(FIELD, "clase", "clase", true, false, 20);
		this.addFixedItem(FIELD, "tipo_clase", "Tipo clase", true, false, 20);

		// this.addFixedItem( FIELD, "indicador_venta", "Indicador Venta", true,
		// false, 10);
		this.addFixedItem(FIELD, "archivo", "Archivo", true, false, 4000);
		this.addFixedItem(FIELD, "gds", "GDS", true, false, 10);
		this.addFixedItem(FIELD, "origen", "Origen", true, false, 10);
		this.addFixedItem(FIELD, "pais", "Pais", true, true, 10);

		this.addFixedItem(FIELD, "directory", "Directorio", true, false, 400);
		this.addFixedItem(FIELD, "fecha_proc", "Fecha Procesamiento", true, false, 10);
		this.addFixedItem(FIELD, "periodo", "Período", true, false, 10);
		this.addFixedItem(FIELD, "fare_intinerary", "Intinerario fare", true, false, 1000);
		this.addFixedItem(FIELD, "air_intinerary", "Intinerario aereo", true, false, 1000);
		this.addFixedItem(FIELD, "air_pais_intinerary", "Intinerario aereo paises", true, false, 1000);
		this.addFixedItem(FIELD, "air_gen_intinerary", "Intinerario aereo metro", true, false, 1000);
		this.addFixedItem(FIELD, "carrier_intinerary", "Intinerario aerolinea", true, false, 1000);
		this.addFixedItem(FIELD, "class_intinerary", "Intinerario clases", true, false, 1000);
		this.addFixedItem(FIELD, "fecha_intinerary", "Intinerario fechas", true, false, 1000);
		this.addFixedItem(FIELD, "nro_flight_intinerary", "Intinerario vuelos", true, false, 1000);
		this.addFixedItem(FIELD, "market_intinerary", "Intinerario Mercados", true, false, 1000);

		this.addFixedItem(FIELD, "pais_origen", "País Origen", true, true, 200);
		this.addFixedItem(FIELD, "pais_destino", "País Destino", true, false, 100);
		this.addFixedItem(FIELD, "upfront_ref", "Upfront", true, false, 18);
		this.addFixedItem(FIELD, "fecha_calc_over", "Fecha calculo over", true, false, 18);
		this.addFixedItem(VIRTUAL, "upfront_descripcion", "Contrato", true, false, 2000);
		this.addFixedItem(FIELD, "update_version_over", "Fecha version over", true, false, 18);
			
		
		this.addFixedItem(FIELD, "fecha_calc_over_back", "Fecha calculo over Back", true, false, 18);
		this.addFixedItem(VIRTUAL, "backend_descripcion", "Contrato Backend", true, false, 2000);
		this.addFixedItem(VIRTUAL, "over_sobre_back", "Marca cal over", true, false, 1);
		this.addFixedItem(FIELD, "calculed", "Calculado", true, false, 1);
		this.addFixedItem(FIELD, "contratos_back", "Contratos Backend", true, false, 2000);
		this.addFixedItem(VIRTUAL, "comision_perc_back", "Com.Porc.Backend", true, false, 18,2);
		this.addFixedItem(VIRTUAL, "comision_over_back", "Com.Over.Backend", true, false, 18,2);
		
		this.addFixedItem(VIRTUAL, "month", "Mes", true, false, 100);
		this.addFixedItem(VIRTUAL, "year", "Año", true, false, 100);
		this.addFixedItem(VIRTUAL, "di", "DI", true, false, 100);
		this.addFixedItem(VIRTUAL, "oracle_numero", "Nro.Doc.", true, false, 100);
		this.addFixedItem(VIRTUAL, "oracle_serie", "Serie Doc.", true, false, 100);
		this.addFixedItem(VIRTUAL, "bsp_codecarrier", "BSP carrier", true, false, 100);
		this.addFixedItem(VIRTUAL, "bsp_tipo_operacion", "BSP tipo comprobante", true, false, 100);
		this.addFixedItem(VIRTUAL, "bsp_carrier", "BSP code", true, false, 100);
		this.addFixedItem(VIRTUAL, "bsp_iata", "BSP iata", true, false, 100);
		this.addFixedItem(VIRTUAL, "ttl", "ttl", true, false, 100);
		this.addFixedItem(VIRTUAL, "mercado_origen", "Mercado Origen", true, false, 100);
		this.addFixedItem(VIRTUAL, "mercado_destino", "Mercado destino", true, false, 100);
		this.addFixedItem(VIRTUAL, "bsp_carrier2", "BSP carrier 2", true, false, 100);
		this.addFixedItem(VIRTUAL, "bsp_porc", "BSP Com %", true, false, 18,2);
		this.addFixedItem(VIRTUAL, "bsp_comision", "BSP Com", true, false, 18,2);
		this.addFixedItem(VIRTUAL, "dif_porcentajeover", "Dif %", true, false, 18,2);
		this.addFixedItem(VIRTUAL, "dif_importeover", "Dif com", true, false, 18,2);

				

// this.addFixedItem( FIELD, "ciudad_origen", "Ciudad Origen", true,
		// false,
		// 100);
		// this.addFixedItem( FIELD, "ciudad_destino", "Ciudad Destino", true,
		// false, 100);
		// this.addFixedItem( FIELD, "region_origen", "Region Origen", true,
		// false,
		// 100);
		// this.addFixedItem( FIELD, "region_destino", "Region Destino", true,
		// false, 100);
		// this.addFixedItem( FIELD, "continente_origen", "Continente Origen",
		// true,
		// false, 100);
		// this.addFixedItem( FIELD, "continente_destino", "Continente Destino",
		// true, false, 100);
		// this.addFixedItem( FIELD, "geo_origen", "Geo Origen", true, false,
		// 50);
		// this.addFixedItem( FIELD, "geo_destino", "Geo Destino", true, false,
		// 50);

		// JProperty p;
		// p = this.addFixedItem( RECORD, "obj_aeropuerto_origen", "Aeropuerto
		// Origen", true, false, 10);
		// p.setClase(BizAirport.class);
		// p.setDependiente("aeropuerto_origen");
		// p = this.addFixedItem( RECORD, "obj_aeropuerto_destino", "Aeropuerto
		// Destino", true, false, 10);
		// p.setClase(BizAirport.class);
		// p.setDependiente("aeropuerto_destino");
		this.addFixedItem(VIRTUAL, "imagen1", "imagen1", true, false, 64);

		this.addFixedItem(FIELD, "remarks", "Remarks", true, false, 2000);
		this.addFixedItem(FIELD, "endoso", "Endoso", true, false, 2000);
	}

	public String getImagen1() throws Exception {
		GuiPNRSegmentoAereos w = new GuiPNRSegmentoAereos();

		w.setRecords(getSegments());
		JWinList wl = new JWinList(w);
		w.AddColumnasDefault(wl);
		Graph gr = new GraphScriptWorldArc();
		gr.setTitle("Boleto N° " + getNumeroboleto());
		gr.addAtributtes(Graph.GRAPH_ATTR_ROTATENAMES, "1");
		gr.addAtributtes(Graph.GRAPH_ATTR_DECIMALPRECISON, "2");
		gr.addAtributtes(Graph.GRAPH_ATTR_NUMBERPREFIX, "");
		gr.addAtributtes(Graph.GRAPH_ATTR_EXPORTENABLED, "1");
		gr.addAtributtes(Graph.GRAPH_ATTR_XAXISNAME, "Anual");

		gr.addAtributtes(Graph.GRAPH_ATTR_YAXISNAME, getDescripcion());
		gr.setTitle(getDescripcion());
		ModelMatrix mg = new ModelMatrix();

		mg.addColumn("Despegue", "geop_despegue", ModelMatrix.GRAPH_FUNCTION_CATEGORIE);
		mg.addColumn("Arrivo", "geop_arrivo", ModelMatrix.GRAPH_FUNCTION_DATASET);
		mg.addColumn("duracionvuelo", ModelMatrix.GRAPH_FUNCTION_VALUE);
		gr.setModel(mg);

		wl.addGrafico(gr);
		w.ConfigurarFiltrosLista(wl);

		Graph g = wl.getGrafico(1);
		if (g != null) {
			g.localFill(wl, null, null);
			g.setRefresh(1);
			return g.getImage(301, 307).replace("script:", "");
		}
		return null;
	}

	/**
	 * Returns the table name
	 */
	@Override
	public String GetTable() {
		return "TUR_PNR_BOLETO";
	}

	// public boolean isIgnoreForeignFields() {
	// return true;
	// }

	public void attachRelationMap(JRelations rels) throws Exception {
		rels.setSourceWinsClass("pss.tourism.pnr.GuiPNRTickets");

		JRelation rel;
		rel = rels.addRelationForce(10, "restriccion usuario");
	
		if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isDependant()) {
			rel.addFilter(" (TUR_PNR_BOLETO.company in (COMPANY_TICKET) and TUR_PNR_BOLETO.customer_id_reducido='"+BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getCodigoCliente()+"')  ");
		} else {
			rel.addFilter(" (TUR_PNR_BOLETO.company in (COMPANY_CUSTOMLIST)) ");
		}
		JRelation r;
		r = rels.addRelationParent(21, "Aerolinea", BizCarrier.class, "CodigoAerolinea", "carrier");
		// r.setTypeJoin(JRelations.JOIN_LEFT);
		r.addJoin("TUR_PNR_BOLETO.codigoaerolinea", "carrier.carrier");
		r.setAlias("carrier");
		r = rels.addRelationParent(22, "Aerolinea Internacional", BizCarrier.class, "codigo_aerolinea_intern", "carrier");
		// r.setTypeJoin(JRelations.JOIN_LEFT);
		r.addJoin("TUR_PNR_BOLETO.codigo_aerolinea_intern", "firstcarrier.carrier");
		r.setAlias("firstcarrier");

		r = rels.addRelationParent(30, "Aeropuerto Origen", BizAirport.class, "aeropuerto_origen", "code");
		r.addJoin("TUR_PNR_BOLETO.aeropuerto_origen", "ae_origen.code");
		// r.setTypeJoin(JRelations.JOIN_LEFT);
		r.setAlias("ae_origen");

		r = rels.addRelationParent(32, "Aeropuerto Destino", BizAirport.class, "aeropuerto_destino", "code");
		r.addJoin("TUR_PNR_BOLETO.aeropuerto_destino", "ae_destino.code");
		// r.setTypeJoin(JRelations.JOIN_LEFT);
		r.setAlias("ae_destino");

		r = rels.addRelationParent(98, "Cliente", BizMailingPersona.class, "customer_id", "codigo");
		r.addJoin("TUR_PNR_BOLETO.customer_id", "cliente.codigo");
		r.addJoin("TUR_PNR_BOLETO.company", "cliente.company");
		r.addFilter("cliente.tipo='" + BizMailingPersona.CLIENTE + "'");
		// r.setTypeJoin(JRelations.JOIN_LEFT);
		r.setAlias("cliente");

		r = rels.addRelationParent(90, "Cliente Reducido", BizMailingPersona.class, "customer_id_reducido", "codigo");
		r.addJoin("TUR_PNR_BOLETO.customer_id_reducido", "cliente_reducido.codigo");
		r.addJoin("TUR_PNR_BOLETO.company", "cliente_reducido.company");
		r.addFilter("cliente_reducido.tipo='" + BizMailingPersona.CLIENTE_REDUCIDO + "'");
		// r.setTypeJoin(JRelations.JOIN_LEFT);
		r.setAlias("cliente_reducido");

		r = rels.addRelationParent(91, "Vendedor", BizMailingPersona.class, "vendedor", "codigo");
		r.addJoin("TUR_PNR_BOLETO.vendedor", "vendedor.codigo");
		r.addJoin("TUR_PNR_BOLETO.company", "vendedor.company");
		r.addFilter("vendedor.tipo='" + BizMailingPersona.VENDEDOR + "'");
		// r.setTypeJoin(JRelations.JOIN_LEFT);
		r.setAlias("vendedor");
		r = rels.addRelationParent(92, "Sucursal", BizMailingPersona.class, "office_id", "codigo");
		r.addJoin("TUR_PNR_BOLETO.office_id", "sucursal.codigo");
		r.addJoin("TUR_PNR_BOLETO.company", "sucursal.company");
		r.addFilter("sucursal.tipo='" + BizMailingPersona.SUCURSAL + "'");
		// r.setTypeJoin(JRelations.JOIN_LEFT);
		r.setAlias("sucursal");
		r = rels.addRelationParent(96, "Agente emision", BizMailingPersona.class, "agente_emision", "codigo");
		r.addJoin("TUR_PNR_BOLETO.agente_emision", "ag_emsion.codigo");
		r.addJoin("TUR_PNR_BOLETO.company", "ag_emsion.company");
		r.addFilter("ag_emsion.tipo='" + BizMailingPersona.SUCURSAL + "'");
		// r.setTypeJoin(JRelations.JOIN_LEFT);
		r.setAlias("ag_emsion");
		r = rels.addRelationParent(97, "Agente reserva", BizMailingPersona.class, "agente_reserva", "codigo");
		r.addJoin("TUR_PNR_BOLETO.agente_reserva", "ag_reserva.codigo");
		r.addJoin("TUR_PNR_BOLETO.company", "ag_reserva.company");
		r.addFilter("ag_reserva.tipo='" + BizMailingPersona.SUCURSAL + "'");
		// r.setTypeJoin(JRelations.JOIN_LEFT);
		r.setAlias("ag_reserva");
		r = rels.addRelationParent(93, "Centro Costo", BizMailingPersona.class, "centro_costos", "codigo");
		r.addJoin("TUR_PNR_BOLETO.centro_costos", "ccosto.codigo");
		r.addJoin("TUR_PNR_BOLETO.company", "ccosto.company");
		r.addFilter("ccosto.tipo='" + BizMailingPersona.CCOSTO + "'");
		// r.setTypeJoin(JRelations.JOIN_LEFT);
		r.setAlias("ccosto");
		r = rels.addRelationParent(94, "IATA", BizMailingPersona.class, "nro_iata", "codigo");
		r.addJoin("TUR_PNR_BOLETO.nro_iata", "iata.codigo");
		r.addJoin("TUR_PNR_BOLETO.company", "iata.company");
		r.addFilter("iata.tipo='" + BizMailingPersona.IATA + "'");
		// r.setTypeJoin(JRelations.JOIN_LEFT);
		r.setAlias("iata");
		r = rels.addRelationParent(95, "Cliente remark", BizMailingPersona.class, "codigo_cliente", "codigo");
		r.addJoin("TUR_PNR_BOLETO.codigo_cliente", "clientermk.codigo");
		r.addJoin("TUR_PNR_BOLETO.company", "clientermk.company");
		r.addFilter("clientermk.tipo='" + BizMailingPersona.CLIENTE + "'");
		// r.setTypeJoin(JRelations.JOIN_LEFT);
		r.setAlias("clientermk");

		r = rels.addRelationParent(99, "Organization", BizOrganizationDetail.class, "nro_iata", "iata");
		r.addJoin("TUR_PNR_BOLETO.nro_iata", "org.iata");
		r.addJoin("TUR_PNR_BOLETO.company", "org.company");
		r.setAlias("org");
		
		r = rels.addRelationChild(1, "Impuestos", BizPNRTax.class);
		r.addJoin("TUR_PNR_BOLETO.interface_id", "tax.interface_id");
		r.setAlias("tax");
		r = rels.addRelationChild(2, "Segmentos", BizPNRSegmentoAereo.class);
		r.addJoin("TUR_PNR_BOLETO.interface_id", "onesegment.interface_id");
		r.setTypeJoin(JRelations.JOIN_ONE);
		r.setAlias("onesegment");
		r = rels.addRelationChild(4, "Todos los Segmentos", BizPNRSegmentoAereo.class);
		r.addJoin("TUR_PNR_BOLETO.interface_id", "segmentos.interface_id");
		r.setAlias("segmentos");
		// r = rels.addRelationChild(3, "Precios", BizPNRFare.class);
		// r.addJoin("TUR_PNR_BOLETO.interface_id", "precios.interface_id");
		// r.setTypeJoin(JRelations.JOIN_ONE);
		// r.setAlias("precios");

		r = rels.addRelationParent(38, "Mis Regiones boleto", BizRegionDetail.class, "pais_destino", "pais");
		r.addJoin("TUR_PNR_BOLETO.pais_destino", "BSP_REGION_DETAIL.pais");
		r.addJoin("TUR_PNR_BOLETO.company", "BSP_REGION_DETAIL.company");
		// r.setTypeJoin(JRelations.JOIN_LEFT);

		r = rels.addRelationChild(70, "Grupo Carriers", BizCarrierGroupDetail.class);
		r.setTypeJoin(JRelations.JOIN_LEFT);
		r.addJoin("TUR_PNR_BOLETO.company", "cgb_detail.company");
		r.addJoin("TUR_PNR_BOLETO.codigoaerolinea", "cgb_detail.carrier");
		r.setAlias("cgb_detail");

		r = rels.addRelationParent(80, "Pais Origen", BizPaisLista.class, "pais_origen", "pais");
		r.addJoin("TUR_PNR_BOLETO.pais_origen", "ps_origen.pais");
		// r.setTypeJoin(JRelations.JOIN_LEFT);
		r.setAlias("ps_origen");

		r = rels.addRelationParent(81, "Pais Destino", BizPaisLista.class, "pais_destino", "pais");
		r.addJoin("TUR_PNR_BOLETO.pais_destino", "ps_destino.pais");
		// r.setTypeJoin(JRelations.JOIN_LEFT);
		r.setAlias("ps_destino");

		rels.addGeoPostions("aeropuerto_origen", new JPair(new JLong(30), new JString("geo_position")));
		rels.addGeoPostions("aeropuerto_destino", new JPair(new JLong(32), new JString("geo_position")));

		rels.hideField("interface_id");
		//rels.hideField("company");
		rels.hideField("internacional");
		// rels.hideField("codigo_cliente");
		rels.hideField("tipo_pasajero2");
		// rels.hideField("centro_costos");
		rels.hideField("codigo_base_moneda");
		// rels.hideField("additional_fee");
		// rels.hideField("void");
		rels.hideField("expense");
		rels.hideField("porc_expense");
		rels.hideField("importecedido");
		rels.hideField("porcentajeover");
		rels.hideField("oversobre");
		rels.hideField("archivo");
		rels.hideField("origen");
		rels.hideField("it");
		rels.hideField("CantidadConectados");
		rels.hideField("migrated");
		rels.hideField("numero_tarjeta");
		rels.hideField("transaction_type");
		rels.hideField("ref_original");
		rels.hideField("fecha_calc_over");
		rels.hideField("update_version_over");
		rels.hideField("upfront_ref");
		rels.hideField("fecha_calc_over_back");
		rels.hideField("contratos_back");
		
		rels.hideField("month");
		rels.hideField("year");
		rels.hideField("di");
		rels.hideField("oracle_numero");
		rels.hideField("oracle_serie");
		rels.hideField("bsp_codecarrier");
		rels.hideField("bsp_tipo_operacion");
		rels.hideField("bsp_carrier");
		rels.hideField("bsp_iata");
		rels.hideField("ttl");
		rels.hideField("mercado_origen");
		rels.hideField("mercado_destino");
		rels.hideField("bsp_carrier2");
		rels.hideField("bsp_porc");
		rels.hideField("bsp_comision");
		rels.hideField("dif_porcentajeover");
		rels.hideField("dif_importeover");
		rels.hideField("contrato");
		rels.hideField("backend_descripcion");
		rels.hideField("over_sobre_back");
		rels.hideField("upfront_descripcion");
			

		// rels.hideField("tarifa_base");
		// rels.hideField("remarks");
		// rels.hideField("observacion");
		rels.hideField("descripcion");
		rels.hideField("order_str");
		// rels.hideField("customer_id");
		rels.hideField("tipo_prestacion");
		rels.hideField("pais");
		rels.hideField("imagen1");
		rels.hideField("is_complete");
		rels.hideField("impuestos");
		rels.hideField("impuesto_factura");
			// rels.hideField("aeropuerto_origen");
		// rels.hideField("aeropuerto_destino");

		rels.onlyLista("NumeroBoleto");
		rels.onlyLista("archivo");
		rels.onlyLista("imagen1");

		rels.addFilter("void", "=", "N");
		rels.addFilter("reemitted", "=", "N");
		rels.addFilter("refund", "=", "N");
		rels.addFilter("is_interlineal", "=", "S");
		rels.addFilter("is_emision", "=", "S");
		rels.addFilter("is_ticket", "=", "S");

		rels.addFolderGroup("Boleto", null);
		rels.addFolderGroup("Fechas", "Boleto");
		rels.addFolderGroup("Aerolínea", "Boleto");
		rels.addFolderGroup("Aeropuertos", "Boleto");
		rels.addFolderGroup("A.Origen", "Aeropuertos");
		rels.addFolderGroup("A.Destino", "Aeropuertos");
		rels.addFolderGroup("Intinerarios", "Boleto");
		rels.addFolderGroup("Pasajero", "Boleto");
		rels.addFolderGroup("Tarifas", "Boleto");
		rels.addFolderGroup("Tarifa (Moneda PNR)", "Tarifas");
		rels.addFolderGroup("Tarifa (Moneda USD)", "Tarifas");
		rels.addFolderGroup("Tarifa (Moneda local)", "Tarifas");
		rels.addFolderGroup("Tarifa (Moneda Base)", "Tarifas");
		rels.addFolderGroup("Comisiones", "Boleto");
		rels.addFolderGroup("Tarjeta", "Boleto");
		rels.addFolderGroup("Impuestos", "Boleto");
		rels.addFolderGroup("Segmento", null);
		rels.addFolderGroup("Todos los Segmentos", null);
		rels.addFolderGroup("Origen", "Segmento");
		rels.addFolderGroup("Destino", "Segmento");
		rels.addFolderGroup("Seg.Origen", "Todos los Segmentos");
		rels.addFolderGroup("Seg.Destino", "Todos los Segmentos");
		// rels.addFolderGroup("Seg.precios", null);
		// rels.addFolderGroup("Origen Precio", "Seg.precios");
		// rels.addFolderGroup("Destino Precio", "Seg.precios");
		rels.addFolderGroup("Remarks", null);
		rels.addFolderGroup("Vendedor", "Remarks");
		rels.addFolderGroup("Centro de Costo", "Remarks");
		rels.addFolderGroup("Cliente remark", "Remarks");
		rels.addFolderGroup("Sucursal", "Boleto");
		rels.addFolderGroup("IATA", "Boleto");
		rels.addFolderGroup("Cliente", "Boleto");
		rels.addFolderGroup("Cliente(DK)", "Boleto");
		rels.addFolderGroup("Otros Datos", "Boleto");
		rels.addFolderGroup("Agente Emision", "Otros Datos");
		rels.addFolderGroup("Agente Reserva", "Otros Datos");

		String s = "4";// ,"7_10"};

		rels.addFieldGroup(s, "highfare", "*", "Remarks");
		rels.addFieldGroup(s, "lowfare", "*", "Remarks");
		rels.addFieldGroup(s, "corporativo", "*", "Remarks");
		rels.addFieldGroup(s, "proposito", "*", "Remarks");
		rels.addFieldGroup(s, "cuenta", "*", "Remarks");
		rels.addFieldGroup(s, "clase_tarifa", "*", "Remarks");
		rels.addFieldGroup(s, "autorizante", "*", "Remarks");
		rels.addFieldGroup(s, "fare_savings", "*", "Remarks");
		rels.addFieldGroup(s, "codigo_negocio", "*", "Remarks");
		rels.addFieldGroup(s, "region", "*", "Remarks");
		rels.addFieldGroup(s, "departamento", "*", "Remarks");
		rels.addFieldGroup(s, "autoriz_cc", "*", "Remarks");
		rels.addFieldGroup(s, "seg_cc", "*", "Remarks");
		rels.addFieldGroup(s, "solicitante", "*", "Remarks");
		rels.addFieldGroup(s, "reserva", "*", "Remarks");
		rels.addFieldGroup(s, "centro_costos", "*", "Centro de Costo");
		rels.addFieldGroup(s, "additional_fee", "*", "Remarks");
		rels.addFieldGroup(s, "costo_fee", "*", "Remarks");
		rels.addFieldGroup(s, "pago_fee", "*", "Remarks");
		rels.addFieldGroup(s, "orden_fee", "*", "Remarks");
		rels.addFieldGroup(s, "consumo", "*", "Remarks");
		rels.addFieldGroup(s, "consumo_num", "*", "Remarks");
		rels.addFieldGroup(s, "cliente", "*", "Remarks");
		rels.addFieldGroup(s, "employee", "*", "Remarks");
		rels.addFieldGroup(s, "email_id", "*", "Remarks");
		rels.addFieldGroup(s, "av_rmk", "*", "Remarks");
		rels.addFieldGroup(s, "bg_rmk", "*", "Remarks");
		rels.addFieldGroup(s, "fa_rmk", "*", "Remarks");
		rels.addFieldGroup(s, "fc_rmk", "*", "Remarks");
		rels.addFieldGroup(s, "rc_rmk", "*", "Remarks");
		rels.addFieldGroup(s, "rch_rmk", "*", "Remarks");
		rels.addFieldGroup(s, "remarks", "*", "Remarks");
		rels.addFieldGroup(s, "tarifa_real", "*", "Remarks");

		rels.addFieldGroup(s, "void", "*", "Boleto");
		rels.addFieldGroup(s, "clase", "*", "Boleto");
		rels.addFieldGroup(s, "tipo_clase", "*", "Boleto");
		rels.addFieldGroup(s, "refund", "*", "Boleto");
		rels.addFieldGroup(s, "reemitted", "*", "Boleto");
		rels.addFieldGroup(s, "is_complete", "*", "Boleto");
		rels.addFieldGroup(s, "is_interlineal", "*", "Boleto");
		rels.addFieldGroup(s, "is_emision", "*", "Boleto");
		rels.addFieldGroup(s, "is_ticket", "*", "Boleto");
		rels.addFieldGroup(s, "refund", "*", "Boleto");
		rels.addFieldGroup(s, "directory", "*", "Boleto");
		rels.addFieldGroup(s, "internacional_descr", "*", "Boleto");
//		rels.addFieldGroup(s, "ref_original", "*", "Boleto");

		
		rels.addFieldGroup(s, "creation_date_date", "*", "Fechas");
		rels.addFieldGroup(s, "creation_date", "*", "Fechas");
		rels.addFieldGroup(s, "departure_date", "*", "Fechas");
		rels.addFieldGroup(s, "arrive_date", "*", "Fechas");
		rels.addFieldGroup(s, "fecha_proc", "*", "Fechas");
		rels.addFieldGroup(s, "insert_date", "*", "Fechas");
		rels.addFieldGroup(s, "pnr_date", "*", "Fechas");
		rels.addFieldGroup(s, "fecha_proc", "*", "Fechas");
		rels.addFieldGroup(s, "endtravel_date", "*", "Fechas");
		rels.addFieldGroup(s, "void_date", "*", "Fechas");
		rels.addFieldGroup(s, "periodo", "*", "Fechas");
		
		rels.addFieldGroup(s, "fecha_intinerary", "*", "Intinerarios");
		rels.addFieldGroup(s, "air_intinerary", "*", "Intinerarios");
		rels.addFieldGroup(s, "fare_intinerary", "*", "Intinerarios");
		rels.addFieldGroup(s, "air_pais_intinerary", "*", "Intinerarios");
		rels.addFieldGroup(s, "air_gen_intinerary", "*", "Intinerarios");
		rels.addFieldGroup(s, "carrier_intinerary", "*", "Intinerarios");
		rels.addFieldGroup(s, "class_intinerary", "*", "Intinerarios");
		rels.addFieldGroup(s, "nro_flight_intinerary", "*", "Intinerarios");
		rels.addFieldGroup(s, "market_intinerary", "*", "Intinerarios");
		
		rels.addFieldGroup(s, "pais_origen", "*", "Boleto");
		rels.addFieldGroup(s, "pais_destino", "*", "Boleto");
		rels.addFieldGroup(s, "market", "*", "Boleto");
		rels.addFieldGroup(s, "tipo_operacion", "*", "Boleto");
		rels.addFieldGroup(s, "boleto_original", "*", "Otros Datos");
		rels.addFieldGroup(s, "imagen1", "*", "Otros Datos");
		rels.addFieldGroup(s, "company", "*", "Boleto");

		rels.addFieldGroup(s, "NumeroBoleto", "*", "Boleto");
		rels.addFieldGroup(s, "vuelta_mundo", "*", "Boleto");
		rels.addFieldGroup(s, "CodigoPNR", "*", "Boleto");
		rels.addFieldGroup(s, "nro_iata", "*", "IATA");
		rels.addFieldGroup(s, "pre_dias_compra", "*", "Otros Datos");
		rels.addFieldGroup(s, "dias_compra", "*", "Otros Datos");
		rels.addFieldGroup(s, "dias_viajados", "*", "Otros Datos");
		rels.addFieldGroup(s, "route", "*", "Boleto");
		rels.addFieldGroup(s, "mini_route", "*", "Boleto");
		rels.addFieldGroup(s, "vendedor", "*", "Vendedor");
		rels.addFieldGroup(s, "observacion", "*", "Otros Datos");
		rels.addFieldGroup(s, "endoso", "*", "Otros Datos");
		
		rels.addFieldGroup(s, "gds", "*", "Boleto");
		// rels.addFieldGroup(s, "tipo_prestacion", "*", "Boleto");
		rels.addFieldGroup(s, "office_id", "*", "Sucursal");
		rels.addFieldGroup(s, "agente_reserva", "*", "Agente Emision");
		rels.addFieldGroup(s, "agente_emision", "*", "Agente Reserva");
//		rels.addFieldGroup(s, "cliente", "*", "Cliente");
		rels.addFieldGroup(s, "codigo_cliente", "*", "Cliente remark");
		rels.addFieldGroup(s, "city_code", "*", "Otros Datos");
		rels.addFieldGroup(s, "tipo_cambio", "*", "Otros Datos");
		rels.addFieldGroup(s, "customer_id", "*", "Cliente");
		rels.addFieldGroup(s, "customer_id_reducido", "*", "Cliente(DK)");
		rels.addFieldGroup(s, "dk_original", "*", "Cliente(DK)");
		rels.addFieldGroup(s, "TipoBoleto", "*", "Boleto");
		rels.addFieldGroup(s, "codigo_aerolinea_intern", "*", "Boleto");
		rels.addFieldGroup(s, "tour_code", "*", "Boleto");
		rels.addFieldGroup(s, "net_remit", "*", "Boleto");
		rels.addFieldGroup(s, "cant_segmentos", "*", "Boleto");
		rels.addFieldGroup(s, "cant_roundtrip", "*", "Boleto");

		rels.addFieldGroup(s + "_98", "*", "*", "");
		rels.addFieldGroup(s + "_98", "descripcion", "*", "Cliente");
		rels.addFieldGroup(s + "_98", "mail", "*", "Cliente");
		rels.addFieldGroup(s + "_98", "numero", "*", "Cliente");

		rels.addFieldGroup(s + "_90", "*", "*", "");
		rels.addFieldGroup(s + "_90", "descripcion", "*", "Cliente(DK)");
		rels.addFieldGroup(s + "_90", "mail", "*", "Cliente(DK)");
		rels.addFieldGroup(s + "_90", "numero", "*", "Cliente(DK)");

		rels.addFieldGroup(s + "_91", "*", "*", "");
		rels.addFieldGroup(s + "_91", "descripcion", "*", "Vendedor");
		rels.addFieldGroup(s + "_91", "mail", "*", "Vendedor");
		rels.addFieldGroup(s + "_91", "numero", "*", "Vendedor");

		rels.addFieldGroup(s + "_92", "*", "*", "");
		rels.addFieldGroup(s + "_92", "descripcion", "*", "Sucursal");
		rels.addFieldGroup(s + "_92", "mail", "*", "Sucursal");
		rels.addFieldGroup(s + "_92", "numero", "*", "Sucursal");

		rels.addFieldGroup(s + "_93", "*", "*", "");
		rels.addFieldGroup(s + "_93", "descripcion", "*", "Centro de Costo");
		rels.addFieldGroup(s + "_93", "mail", "*", "Centro de Costo");
		rels.addFieldGroup(s + "_93", "numero", "*", "Centro de Costo");

		rels.addFieldGroup(s + "_94", "*", "*", "");
		rels.addFieldGroup(s + "_94", "descripcion", "*", "IATA");
		rels.addFieldGroup(s + "_94", "mail", "*", "IATA");
		rels.addFieldGroup(s + "_94", "numero", "*", "IATA");

		rels.addFieldGroup(s + "_95", "*", "*", "");
		rels.addFieldGroup(s + "_95", "descripcion", "*", "Cliente remark");
		rels.addFieldGroup(s + "_95", "mail", "*", "Cliente remark");
		rels.addFieldGroup(s + "_95", "numero", "*", "Cliente remark");

		rels.addFieldGroup(s + "_96", "*", "*", "");
		rels.addFieldGroup(s + "_96", "descripcion", "*", "Agente Emision");
		rels.addFieldGroup(s + "_96", "mail", "*", "Agente Emision");
		rels.addFieldGroup(s + "_96", "numero", "*", "Agente Emision");

		rels.addFieldGroup(s + "_97", "*", "*", "");
		rels.addFieldGroup(s + "_97", "descripcion", "*", "Agente Reserva");
		rels.addFieldGroup(s + "_97", "mail", "*", "Agente Reserva");
		rels.addFieldGroup(s + "_97", "numero", "*", "Agente Reserva");

		rels.addFieldGroup(s + "_98", "*", "*", "");
		rels.addFieldGroup(s + "_98", "descripcion", "*", "Cliente");
		rels.addFieldGroup(s + "_98", "mail", "*", "Cliente");
		rels.addFieldGroup(s + "_98", "numero", "*", "Cliente");

		rels.addFieldGroup(s + "_99", "*", "*", "");
		rels.addFieldGroup(s + "_99_49", "*", "*", "");
		rels.addFieldGroup(s + "_99_49", "descripcion", "*", "Organization");

		rels.addFieldGroup(s + "_21", "*", "*", "Aerolínea");
		rels.addFieldGroup(s + "_21", "carrier", "*", "");
		rels.addFieldGroup(s + "_22", "*", "*", "");
		rels.addFieldGroup(s + "_70", "*", "*", "");
		rels.addFieldGroup(s + "_70_70", "*", "*", "");
		rels.addFieldGroup(s, "CodigoAerolinea", "*", "Aerolínea");
		rels.addFieldGroup(s + "_70", "id_group", "*", "Aerolínea");
		rels.addFieldGroup(s + "_70_70", "descripcion", "*", "Aerolínea");

		rels.addFieldGroup(s, "codigo_moneda", "*", "Tarifa (Moneda PNR)");
		rels.addFieldGroup(s, "codigo_base_moneda", "*", "Tarifa (Moneda PNR)");
		rels.addFieldGroup(s, "tarifa", "*", "Tarifa (Moneda PNR)");
		rels.addFieldGroup(s, "pivatefareindicator", "*", "Tarifa (Moneda PNR)");
		rels.addFieldGroup(s, "tarifa_yq", "*", "Tarifa (Moneda PNR)");
		rels.addFieldGroup(s, "tarifa_facturada_yq", "*", "Tarifa (Moneda PNR)");
		rels.addFieldGroup(s, "tarifa_usa", "*", "Tarifa (Moneda USD)");
		rels.addFieldGroup(s, "neto_usa", "*", "Tarifa (Moneda USD)");
		rels.addFieldGroup(s, "tarifa_facturada_yq_usa", "*", "Tarifa (Moneda USD)");

		rels.addFieldGroup(s, "tarifa_base_contax", "*", "Tarifa (Moneda Base)");
		rels.addFieldGroup(s, "tarifa_base", "*", "Tarifa (Moneda Base)");

		rels.addFieldGroup(s, "codigo_moneda_local", "*", "Tarifa (Moneda local)");
		rels.addFieldGroup(s, "tarifa_factura_total_local", "*", "Tarifa (Moneda local)");
		rels.addFieldGroup(s, "tarifa_factura_local", "*", "Tarifa (Moneda local)");
		rels.addFieldGroup(s, "neto_factura_local", "*", "Tarifa (Moneda local)");
		rels.addFieldGroup(s, "netoyq_local", "*", "Tarifa (Moneda local)");
		rels.addFieldGroup(s, "baseyq_local", "*", "Tarifa (Moneda local)");
		rels.addFieldGroup(s, "tarifa_local", "*", "Tarifa (Moneda local)");
		rels.addFieldGroup(s, "neto_local", "*", "Tarifa (Moneda local)");
		rels.addFieldGroup(s, "tarifa_facturada_yq_local", "*", "Tarifa (Moneda local)");

		rels.addFieldGroup(s, "neto", "*", "Tarifa (Moneda PNR)");
		rels.addFieldGroup(s, "iva", "*", "Tarifa (Moneda PNR)");
		rels.addFieldGroup(s, "tarifa_factura", "*", "Tarifa (Moneda PNR)");
		rels.addFieldGroup(s, "neto_factura", "*", "Tarifa (Moneda PNR)");
		rels.addFieldGroup(s, "iva_factura", "*", "Tarifa (Moneda PNR)");
		rels.addFieldGroup(s, "tarifa_base_factura", "*", "Tarifa (Moneda PNR)");
		rels.addFieldGroup(s, "tarifa_factura_total", "*", "Tarifa (Moneda PNR)");
		rels.addFieldGroup(s, "tarifa_factura_total_usa", "*", "Tarifa (Moneda USD)");
		rels.addFieldGroup(s, "tarifa_factura_usa", "*", "Tarifa (Moneda USD)");
		rels.addFieldGroup(s, "neto_factura_usa", "*", "Tarifa (Moneda USD)");
		rels.addFieldGroup(s, "netoyq_usa", "*", "Tarifa (Moneda USD)");
		rels.addFieldGroup(s, "baseyq_usa", "*", "Tarifa (Moneda USD)");
		rels.addFieldGroup(s, "ahorro", "*", "Tarifa (Moneda PNR)");

		rels.addFieldGroup(s, "nombre_pasajero", "*", "Pasajero");
		rels.addFieldGroup(s, "ident_fiscal", "*", "Pasajero");
		rels.addFieldGroup(s, "NumeroPasajero", "*", "Pasajero");
		rels.addFieldGroup(s, "tipo_pasajero", "*", "Pasajero");

		rels.addFieldGroup(s, "comision_amount", "*", "Comisiones");
		rels.addFieldGroup(s, "comision_perc", "*", "Comisiones");
		rels.addFieldGroup(s, "importeover", "*", "Comisiones");
		rels.addFieldGroup(s, "importeover_rmk", "*", "Comisiones");
		rels.addFieldGroup(s, "importe_prorr_over", "*", "Comisiones");
		rels.addFieldGroup(s, "comision_factura", "*", "Comisiones");
		rels.addFieldGroup(s, "comision_perc_back", "*", "Comisiones");
		rels.addFieldGroup(s, "comision_over_back", "*", "Comisiones");
		rels.addFieldGroup(s, "ivaover", "*", "Comisiones");
		rels.addFieldGroup(s, "porcentaje_prorr_over", "*", "Comisiones");
		rels.addFieldGroup(s, "importe_prorr_over", "*", "Comisiones");

		rels.addFieldGroup(s + "_30", "*", "*", "A.Origen");
		rels.addFieldGroup(s + "_30", "geo_position", "*", "");
		rels.addFieldGroup(s + "_30_80", "*", "*", "");
		rels.addFieldGroup(s + "_30_60", "*", "*", "");
		rels.addFieldGroup(s + "_30_80_70", "*", "*", "");
		rels.addFieldGroup(s + "_32_80", "*", "*", "");
		rels.addFieldGroup(s + "_32_80_70", "*", "*", "");
		rels.addFieldGroup(s + "_80", "*", "*", "");
		rels.addFieldGroup(s + "_80", "descripcion", "*", "A.Origen");
		rels.addFieldGroup(s + "_80", "continente", "*", "A.Origen");
		rels.addFieldGroup(s + "_80", "region", "*", "A.Origen");
		rels.addFieldGroup(s + "_30_80", "id_group", "*", "A.Origen");
		// rels.addFieldGroup(s + "_30_80_70", "descripcion", "*", "Aeropuerto
		// Origen");
		rels.addFieldGroup(s, "aeropuerto_origen", "*", "A.Origen");

		rels.addFieldGroup(s + "_32", "*", "*", "A.Destino");
		rels.addFieldGroup(s + "_32", "geo_position", "*", "");
		rels.addFieldGroup(s + "_32_60", "*", "*", "");
		rels.addFieldGroup(s + "_32_80", "*", "*", "");
		rels.addFieldGroup(s + "_81", "*", "*", "");
		rels.addFieldGroup(s + "_81", "descripcion", "*", "A.Destino");
		rels.addFieldGroup(s + "_81", "continente", "*", "A.Destino");
		rels.addFieldGroup(s + "_81", "region", "*", "A.Destino");
		rels.addFieldGroup(s + "_32_80", "id_group", "*", "A.Destino");
		// rels.addFieldGroup(s + "_32_80_70", "descripcion", "*", "Aeropuerto
		// Destino");
		rels.addFieldGroup(s, "aeropuerto_destino", "*", "A.Destino");

		rels.addFieldGroup(s, "nombre_tarjeta", "*", "Tarjeta");
		rels.addFieldGroup(s, "monto_tarjeta", "*", "Tarjeta");
		rels.addFieldGroup(s, "numero_tarjeta_mask", "*", "Tarjeta");

		rels.addFieldGroup(s + "_1", "*", "*", "Impuestos");
		rels.addFieldGroup(s, "impuestos_total", "*", "Impuestos");
		rels.addFieldGroup(s, "impuestos_total_factura", "*", "Impuestos");
		rels.addFieldGroup(s, "yq", "*", "Impuestos");
		rels.addFieldGroup(s, "q", "*", "Impuestos");
		rels.addFieldGroup(s, "q_usd", "*", "Impuestos");
		rels.addFieldGroup(s, "q_local", "*", "Impuestos");

		rels.addFieldGroup(s + "_2", "*", "*", "Segmento");
		rels.addFieldGroup(s + "_4", "*", "*", "Todos los Segmentos");

		rels.addFieldGroup(s + "_2", "Despegue", "*", "Origen");
		rels.addFieldGroup(s + "_2", "FechaDespegue", "*", "Origen");
		rels.addFieldGroup(s + "_2", "HoraDespegue", "*", "Origen");
		rels.addFieldGroup(s + "_2_51", "*", "*", "Origen");
		rels.addFieldGroup(s + "_2_51", "geo_position", "*", "");
		rels.addFieldGroup(s + "_2_51_60", "*", "*", "");
		rels.addFieldGroup(s + "_2_51_60", "descripcion", "*", "Origen");
		rels.addFieldGroup(s + "_2_51_60", "continente", "*", "Origen");
		rels.addFieldGroup(s + "_2_51_60", "region", "*", "Origen");

		rels.addFieldGroup(s + "_2_70", "*", "*", "");
		rels.addFieldGroup(s + "_2_21", "*", "*", "");
		rels.addFieldGroup(s + "_2_70_70", "*", "*", "");
		rels.addFieldGroup(s + "_2", "carrier", "*", "Segmento");
		rels.addFieldGroup(s + "_2_70", "id_group", "*", "Segmento");
		rels.addFieldGroup(s + "_2_70_70", "descripcion", "*", "Segmento");

		rels.addFieldGroup(s + "_2_51_80", "*", "*", "");
		rels.addFieldGroup(s + "_2_51_80_70", "*", "*", "");
		rels.addFieldGroup(s + "_2_51_80", "id_group", "*", "Origen");
		rels.addFieldGroup(s + "_2_51_80_70", "descripcion", "*", "Origen");
		rels.addFieldGroup(s + "_2", "Origen", "*", "Origen");
		rels.addFieldGroup(s + "_2", "Origen_pais", "*", "Origen");

		rels.addFieldGroup(s + "_2_52_80", "*", "*", "");
		rels.addFieldGroup(s + "_2_52_80_70", "*", "*", "");
		rels.addFieldGroup(s + "_2_52_80", "id_group", "*", "Destino");
		rels.addFieldGroup(s + "_2_52_80_70", "descripcion", "*", "Destino");
		rels.addFieldGroup(s + "_2", "Destino", "*", "Destino");
		rels.addFieldGroup(s + "_2", "Destino_pais", "*", "Destino");

		rels.addFieldGroup(s + "_2", "Arrivo", "*", "Destino");
		rels.addFieldGroup(s + "_2", "FechaArrivo", "*", "Destino");
		rels.addFieldGroup(s + "_2", "HoraArrivo", "*", "Destino");
		rels.addFieldGroup(s + "_2_52", "*", "*", "Destino");
		rels.addFieldGroup(s + "_2_52", "geo_position", "*", "");
		rels.addFieldGroup(s + "_2_52_60", "*", "*", "");
		rels.addFieldGroup(s + "_2_52_60", "descripcion", "*", "Destino");
		rels.addFieldGroup(s + "_2_52_60", "continente", "*", "Destino");
		rels.addFieldGroup(s + "_2_52_60", "region", "*", "Destino");

		// rels.addFieldGroup(s + "_3", "*", "*", "Seg.precios");
		// rels.addFieldGroup(s + "_3_70", "*", "*", "");
		// rels.addFieldGroup(s + "_3_70_70", "*", "*", "");
		// rels.addFieldGroup(s + "_3_70", "id_group", "*", "Seg.precios");
		// rels.addFieldGroup(s + "_3_70_70", "descripcion", "*", "Seg.precios");

		rels.addFieldGroup(s + "_3_51_60", "*", "*", "");
		rels.addFieldGroup(s + "_3_51_80", "*", "*", "");
		rels.addFieldGroup(s + "_3_51_80_70", "*", "*", "");
		rels.addFieldGroup(s + "_3_52_60", "*", "*", "");
		rels.addFieldGroup(s + "_3_52_80", "*", "*", "");
		rels.addFieldGroup(s + "_3_52_80_70", "*", "*", "");
		rels.addFieldGroup(s + "_3_52", "geo_position", "*", "");

		rels.addFieldGroup(s + "_3", "Despegue", "*", "Origen Precio");
		rels.addFieldGroup(s + "_3", "FechaDespegue", "*", "Origen Precio");
		rels.addFieldGroup(s + "_3", "HoraDespegue", "*", "Origen Precio");
		rels.addFieldGroup(s + "_3_51", "*", "*", "Origen Precio");
		rels.addFieldGroup(s + "_3_51", "geo_position", "*", "");
		rels.addFieldGroup(s + "_3_51_60", "descripcion", "*", "Origen Precio");
		rels.addFieldGroup(s + "_3_51_60", "continente", "*", "Origen Precio");
		rels.addFieldGroup(s + "_3_51_60", "region", "*", "Origen Precio");
		rels.addFieldGroup(s + "_3_51_80", "id_group", "*", "Origen Precio");
		rels.addFieldGroup(s + "_3_51_80_70", "descripcion", "*", "Origen Precio");

		rels.addFieldGroup(s + "_3", "Arrivo", "*", "Destino Precio");
		rels.addFieldGroup(s + "_3", "FechaArrivo", "*", "Destino Precio");
		rels.addFieldGroup(s + "_3", "HoraArrivo", "*", "Destino Precio");
		rels.addFieldGroup(s + "_3", "carrier", "*", "Destino Precio");
		rels.addFieldGroup(s + "_3_52", "*", "*", "Destino Precio");
		rels.addFieldGroup(s + "_3_52_60", "descripcion", "*", "Destino Precio");
		rels.addFieldGroup(s + "_3_52_60", "continente", "*", "Destino Precio");
		rels.addFieldGroup(s + "_3_52_60", "region", "*", "Destino Precio");
		rels.addFieldGroup(s + "_3_52_80", "id_group", "*", "Destino Precio");
		rels.addFieldGroup(s + "_3_52_80_70", "descripcion", "*", "Destino Precio");

		rels.addFieldGroup(s + "_38", "*", "*", "");
		rels.addFieldGroup(s + "_38_49", "*", "*", "");
		rels.addFieldGroup(s + "_38_49", "descripcion", "*", "A.Destino");// region
		rels.addFieldGroup(s + "_2_54_49", "*", "*", "");
		rels.addFieldGroup(s + "_2_54", "*", "*", "");
		rels.addFieldGroup(s + "_2_54_49", "descripcion", "*", "Destino");// region
		rels.addFieldGroup(s + "_3_54_49", "*", "*", "");
		rels.addFieldGroup(s + "_3_54", "*", "*", "");
		rels.addFieldGroup(s + "_3_54_49", "descripcion", "*", "Destino Precio");// region

		rels.addFieldGroup(s + "_4", "Despegue", "*", "Seg.Origen");
		rels.addFieldGroup(s + "_4", "FechaDespegue", "*", "Seg.Origen");
		rels.addFieldGroup(s + "_4", "HoraDespegue", "*", "Seg.Origen");
		rels.addFieldGroup(s + "_4_51", "*", "*", "Seg.Origen");
		rels.addFieldGroup(s + "_4_51", "geo_position", "*", "");
		rels.addFieldGroup(s + "_4_51_60", "*", "*", "");
		rels.addFieldGroup(s + "_4_51_60", "descripcion", "*", "Seg.Origen");
		rels.addFieldGroup(s + "_4_51_60", "continente", "*", "Seg.Origen");
		rels.addFieldGroup(s + "_4_51_60", "region", "*", "Seg.Origen");

		rels.addFieldGroup(s + "_4_70", "*", "*", "");
		rels.addFieldGroup(s + "_4_21", "*", "*", "");
		rels.addFieldGroup(s + "_4_70_70", "*", "*", "");
		rels.addFieldGroup(s + "_4", "carrier", "*", "Todos los Segmentos");
		rels.addFieldGroup(s + "_4_70", "id_group", "*", "Todos los Segmentos");
		rels.addFieldGroup(s + "_4_70_70", "descripcion", "*", "Todos los Segmentos");
		rels.addFieldGroup(s + "_4", "Destino", "*", "Todos los Segmentos");
		rels.addFieldGroup(s + "_4", "Destino_pais", "*", "Todos los Segmentos");
		rels.addFieldGroup(s + "_4", "Origen", "*", "Todos los Segmentos");
		rels.addFieldGroup(s + "_4", "Origen_pais", "*", "Todos los Segmentos");

		rels.addFieldGroup(s + "_4_51_80", "*", "*", "");
		rels.addFieldGroup(s + "_4_51_80_70", "*", "*", "");
		rels.addFieldGroup(s + "_4_51_80", "id_group", "*", "Seg.Origen");
		// rels.addFieldGroup(s + "_4_51_80_70", "descripcion", "*", "Seg.Origen");

		rels.addFieldGroup(s + "_4_52_80", "*", "*", "");
		rels.addFieldGroup(s + "_4_52_80_70", "*", "*", "");
		rels.addFieldGroup(s + "_4_52_80", "id_group", "*", "Seg.Destino");
		// rels.addFieldGroup(s + "_4_52_80_70", "descripcion", "*", "Seg.Destino");

		rels.addFieldGroup(s + "_4", "Arrivo", "*", "Seg.Destino");
		rels.addFieldGroup(s + "_4", "FechaArrivo", "*", "Seg.Destino");
		rels.addFieldGroup(s + "_4", "HoraArrivo", "*", "Seg.Destino");
		rels.addFieldGroup(s + "_4_52", "*", "*", "Seg.Destino");
		rels.addFieldGroup(s + "_4_52", "geo_position", "*", "");
		rels.addFieldGroup(s + "_4_52_60", "*", "*", "");
		rels.addFieldGroup(s + "_4_52_60", "descripcion", "*", "Seg.Destino");
		rels.addFieldGroup(s + "_4_52_60", "continente", "*", "Seg.Destino");
		rels.addFieldGroup(s + "_4_52_60", "region", "*", "Seg.Destino");
		rels.addFieldGroup(s + "_4_54_49", "*", "*", "");
		rels.addFieldGroup(s + "_4_54", "*", "*", "");

		rels.addCamposMailing(BizMailingPersona.CLIENTERMK, "codigo_cliente", BizPNRTicket.class.getName(), "0", "=");
		rels.addCamposMailing(BizMailingPersona.CLIENTE, "customer_id", BizPNRTicket.class.getName(), "0", "=");
		rels.addCamposMailing(BizMailingPersona.SUCURSAL, "office_id", BizPNRTicket.class.getName(), "0", "=");
		rels.addCamposMailing(BizMailingPersona.IATA, "nro_iata", BizPNRTicket.class.getName(), "0", "=");
		rels.addCamposMailing(BizMailingPersona.VENDEDOR, "vendedor", BizPNRTicket.class.getName(), "0", "=");
		rels.addCamposMailing(BizMailingPersona.CCOSTO, "centro_costos", BizPNRTicket.class.getName(), "0", "=");
		rels.addCamposMailing(BizMailingPersona.CLIENTE_REDUCIDO, "customer_id_reducido", BizPNRTicket.class.getName(), "0", "=");

		rels.setChatSpec("Si se vende un boleto de, por ejemplo, $1000 y luego hay un cambio de fecha de viaje y se requiere cobrar $100 extra, se emitirá un boleto de "+
		  " de 'Boleto, Tarifas, Tarifa (Moneda local), Neto facturada (local)' = $1000,'Boleto, Tarifas, Tarifa (Moneda local), Tarifa (local)' = $1000 "
		  + "(anteriores reemisiones mas esta) "
		  + "y 'Boleto, Reemitido?'=true; y otro ticket con 'Boleto, Tarifas, Tarifa (Moneda local), Neto facturada (local)' = $100, "
		  + "'Boleto, Tarifas, Tarifa (Moneda local), Tarifa (local)' = $1100 (anteriores emisiones mas la actual), 'Boleto, Reemitido?' = false."
		  + " Por lo que siempre que se use tarifa, '"
		  + " neto, impuestos sin la palabra facturada, debe incluirse un "
		  + " filtro de 'Boleto, Reemitido?'=false para que no se sumen importes "
		  + " Si se usa 'Boleto, Fechas, Fecha emisión' usar  las tarifas etiquetas con facturadas, si usa otra fecha, usar sin facturada y 'Boleto, Reemitido?'=false  ");
		
	}

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Functionality methods
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Default Read() method
	 */
	public boolean Read(String company, String zCodigopnr, String zNumeroboleto) throws Exception {
		addFilter("company", company);
		if (JTools.getLongNumberEmbedded(zNumeroboleto) < 100) // la aerolinea Y4 da
																														// unos numeros de
																														// boletos raro
																														// donde son todos
																														// repetidos y
																														// menores a 100
			addFilter("CodigoPNR", zCodigopnr);
		addFilter("NumeroBoleto", zNumeroboleto);
		return read();
	}

	public boolean Read(String company, Date pnrCreationDate, String zNumeroboleto) throws Exception {
		addFilter("company", company);
		addFilter("creation_date", pnrCreationDate);
		addFilter("NumeroBoleto", zNumeroboleto);
		return read();
	}
	public boolean read(long id) throws Exception {
		this.clearFilters();
		addFilter("interface_id", id);
		return read();
	}

	public boolean read(long id, String zNumeroboleto) throws Exception {
		this.clearFilters();
		addFilter("interface_id", id);
		addFilter("NumeroBoleto", zNumeroboleto);
		return read();
	}

	public boolean ReadByBoleto(String company, String zNumeroboleto) throws Exception {
		addFilter("company", company);
		addFilter("NumeroBoleto", zNumeroboleto);
		return read();
	}

	 
	 public boolean canViewField(String field) throws Exception {
		 if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isDependant()) {
			 if (field.toLowerCase().indexOf("over")!=-1) return false;
			 if (field.toLowerCase().indexOf("comision")!=-1) return false;
			 if (field.toLowerCase().indexOf("upfront")!=-1) return false;
		 }
		 return true;
	 }
	// public BizArticleAir getObjArticleAir() throws Exception {
	// BizCarrier carrier = new BizCarrier();
	// carrier.SetNoExcSelect(true);
	// if (!carrier.Read(pCodigoaerolinea.getValue()))
	// JExcepcion.SendError("El prestador no es un Carrier conocido:^
	// "+pCodigoaerolinea.getValue());
	// return carrier.getObjArticle(true);
	// }

	// public BizPNRPasajero getObjPax() throws Exception {
	// if (this.pax!=null) return this.pax;
	// BizPNRPasajero pax = new BizPNRPasajero();
	// pax.dontThrowException(true);
	// if (!pax.Read(getCompany(), getCodigopnr(), getNumeropasajero()))
	// return null;
	// return (this.pax=pax);
	// }

	// public BizPNR getObjPNR() throws Exception {
	// if (this.pnr!=null) return this.pnr;
	// BizPNR r = new BizPNR();
	// r.Read(getCompany(),this.getCodigopnr());
	// return (this.pnr=r);
	// }

	public void execReprocesar() throws Exception {
		JExec oExec = new JExec(this, "reprocesar") {

			@Override
			public void Do() throws Exception {
				reprocesar();
			}
		};
		oExec.execute();
	}	
	public void execVoidear() throws Exception {
		JExec oExec = new JExec(this, "voidear") {

			@Override
			public void Do() throws Exception {
				new BizVoidManual().procAnular(getCompany(), getNumeroboleto());
			}
			
		};
		oExec.execute();
	}
	public void execDesvoidear() throws Exception {
		JExec oExec = new JExec(this, "voidear") {

			@Override
			public void Do() throws Exception {
				new BizVoidManual().procDesAnular(getCompany(), getNumeroboleto());
			}
			
		};
		oExec.execute();
	}


	public void execProcessFileInternal(final String filename) throws Exception {
		JExec oExec = new JExec(this, "processFileInternal") {

			@Override
			public void Do() throws Exception {
				processFileInternal(filename, false);
			}
		};
		oExec.execute();
	}

	public void processFileInternal(String filename, boolean reprocess) throws Exception {

		FileProcessor fp = null;
		if (filename.indexOf("SABRE") != -1)
			fp = new SabreFileProcessor();
		else if (filename.indexOf("AMADEUS") != -1)
			fp = new AmadeusFileProcessor();
		else if (filename.toUpperCase().indexOf("COPA") != -1)
			fp = new SabreFileProcessor();
		else if (filename.indexOf("TRAVELPORT") != -1)
			fp = new TravelPortFileProcessor();
		else if (filename.indexOf("GALILEO") != -1)
			fp = new GalileoFileProcessor();
		else
			throw new Exception("GDS desconocido");
		fp.processFileInternal(new File(filename), reprocess);
	}

	// public void reprocesar() throws Exception {
	// boolean first=true;
	//
	// JRecords<BizPNRFile> files = getArchivos();
	// JIterator<BizPNRFile> it = files.getStaticIterator();
	// while (it.hasMoreElements()) {
	// BizPNRFile file = it.nextElement();
	// String origen = JPath.PssPathInputProcessed() + "/" + file.getDirectory()
	// + "/" + file.getArchivo();
	//
	// if (first) { // existe por lo menos un archivo, antes de borrarlo
	// deletePNR(getCodigopnr());
	// first=false;
	// }
	// processFileInternal(origen);
	//
	//
	// }
	//
	// if (first) { // no hay archivos, (ambiente de desarrollo ?)
	// this.processAnalizeSegments(this.getSegments());
	// this.update();
	//
	// }
	// }
	public void reprocesar() throws Exception {
		boolean first = true;
		JRecords<BizPNRFile> files = getArchivos();
		JIterator<BizPNRFile> it = files.getStaticIterator();
	
		
		if (!it.hasMoreElements() && pGDS.getValue().equals("ORACLE")) {
			pss.tourism.interfaceGDS.oracle.TicketTransaction trx = new pss.tourism.interfaceGDS.oracle.TicketTransaction();
		  trx.buildTrx(getCompany(),getNumeroboleto(), null);
			return;
		}

		while (it.hasMoreElements()) {
			BizPNRFile file = it.nextElement();
			String origen = JPath.PssPathInputProcessed() + "/" + file.getDirectory() + "/" + file.getArchivo();

			File f = new File(origen);
			if (!f.exists()) {
				String newOrigen = JTools.replace(origen,"/NDC/","/AMADEUS/");
				f = new File(newOrigen);
				if (!f.exists()) {
					 newOrigen = JTools.replace(origen,"/NDC/","/SABRE/");
					 f = new File(newOrigen);
					 if (f.exists()) {
					  origen = newOrigen;
					 }
				} else {
				  origen = newOrigen;

				}
			}
			
			
			if (first) { // existe por lo menos un archivo, antes de borrarlo
				deletePNR(getCodigopnr());
				first = false;
			}
			processFileInternal(origen, true);
			
		}

		if (first) { // no hay archivos, (ambiente de desarrollo ?)
			this.processAnalizeSegments(this.getSegments());
			this.update();

		}
	}

	public void deletePNR(String pnr) throws Exception {
		JRecords<BizPNRTicket> pnrs = new JRecords<BizPNRTicket>(BizPNRTicket.class);
		pnrs.addFilter("company", this.getCompany());
		pnrs.addFilter("CodigoPNR", pnr);
		JIterator<BizPNRTicket> it = pnrs.getStaticIterator();
		while (it.hasMoreElements()) {
			BizPNRTicket oBD = (BizPNRTicket) it.nextElement();
			oBD.setViolateIntegrity(this.isViolateIntegrity());
			oBD.dontThrowException(true);
			oBD.processDelete();
			// oBD.processDeleteChild();
		}
	}

	protected void deletePNRFronIata(String iata) throws Exception {
		JRecords<BizPNRTicket> pnrs = new JRecords<BizPNRTicket>(BizPNRTicket.class);
		pnrs.addFilter("company", this.getCompany());
		pnrs.addFilter("nro_iata", iata);
		JIterator<BizPNRTicket> it = pnrs.getStaticIterator();
		while (it.hasMoreElements()) {
			BizPNRTicket oBD = (BizPNRTicket) it.nextElement();
			oBD.setViolateIntegrity(this.isViolateIntegrity());
			oBD.dontThrowException(true);
			oBD.processDelete();
			// oBD.processDeleteChild();
		}
	}

	public void execSaveCotizacion() throws Exception {
		JExec oExec = new JExec(this, "saveCotizacion") {

			@Override
			public void Do() throws Exception {
				saveCotizacion();
			}
		};
		oExec.execute();
	}

	public boolean acceptTicket() throws Exception {
		if (getGDS().equalsIgnoreCase("galileo") && getNumeroboleto().trim().equals("")) 
			return false;
		if (getGDS().equalsIgnoreCase("amadeus") && getNumeroboleto().trim().equals("")) 
			return false;
	return true;

	}

	public void processAnalizeSegments(JRecords<BizPNRSegmentoAereo> segmentos) throws Exception {
		new AuxiliarClaseCalculoSegmentos().processAnalizeSegmentsInterno(segmentos);
	}

	public void saveCotizacion() throws Exception {
		String cm = getCompany();
		String ms = getCodigoBaseMoneda();
		String mt = getCodigoMoneda();
		Double cotiz = getTipoCambio();
		Date fecha = getCreationDate();
		// this.getCodigopnr()
		if (ms.equals(mt))
			return;
		if (getTipoOperacion().equals("EMD"))
			return;
		if (isVoided())
			return;
		if (!getOriginalNumeroboleto().equals(""))
			return;
		if (cotiz == 0)
			return;

		if (((mt.equals("ARS") ||mt.equals("MXN") ||mt.equals("CLP")||mt.equals("CRC") )&&ms.equals("USD" ))&&  cotiz <= 1)
			return;
		BizMonedaCotizacion cot = new BizMonedaCotizacion();
		cot.dontThrowException(true);
		if (cot.Read(cm, fecha)) {
			if (Math.abs(cot.getCotizVenta() - cotiz) < 1)
				return;
			cot.setCompany(cm);
			cot.setMonedaSource(ms);
			cot.setMonedaTarget(mt);
			cot.setCotizCompra(cotiz);
			cot.setCotizVenta(cotiz);
			cot.setCotizContab(cotiz);
			cot.setFechaHora(fecha);
			cot.setPais(getObjCompany().getCountry());
			cot.update();
			return;
		}
		cot.setCompany(cm);
		cot.setMonedaSource(ms);
		cot.setMonedaTarget(mt);
		cot.setCotizCompra(cotiz);
		cot.setCotizVenta(cotiz);
		cot.setCotizContab(cotiz);
		cot.setFechaHora(fecha);
		cot.setPais(getObjCompany().getCountry());
		cot.processInsertSimple();
	}

	public JRecords<BizPNRFile> getArchivosOld() throws Exception {
		String directory = getDirectory();
		String filename = getArchivo();

		JRecords<BizPNRFile> files = new JRecords<BizPNRFile>(BizPNRFile.class);
		int pos = filename.lastIndexOf(".");

		files.setStatic(true);
		files.clearStaticItems();

		FilenameFilter textFilter = new FilenameFilter() {
			String patron;

			public FilenameFilter setPatron(String patron) {
				this.patron = patron.toLowerCase();
				return this;
			}

			public boolean accept(File dir, String name) {
				String lowercaseName = name.toLowerCase();
				if (lowercaseName.startsWith(patron)) {
					return true;
				} else {
					return false;
				}
			}
		}.setPatron(filename.substring(0, pos - 2));

		File dir = new File(JPath.PssPathInputProcessed() + "/" + directory);
		File fnames[] = dir.listFiles(textFilter);

		// String[] names = dir.list(textFilter);

		if (fnames == null)
			return files;

		fnames = sortFilesByName(fnames);

		for (int i = 0; i < fnames.length; i++) {

			String fname = fnames[i].getName();
			BizPNRFilename f = new BizPNRFilename();

			BizPNRFilename file = new BizPNRFilename();
			file.dontThrowException(true);
			file.addFilter("company", getCompany());
			file.addFilter("pnr", getCodigopnr());
			file.addFilter("filename", fname);
			file.addFilter("directory", getDirectory());
			if (file.read()) {
				continue;
			}

			f.setPNR(getCodigopnr());
			f.setCompany(getCompany());
			f.setArchivo(fname);
			f.setDirectory(getDirectory());
			f.setDate(new Date());
			f.setInterfaceID(1);
			try {
				f.insert();
			} catch (Exception eee) {
				PssLogger.logDebug(eee);
			}

			f.setDescripcion("Archivo version " + fname);

		}
		return files;
	}

//	public void checkArchivosCompletos() throws Exception {
//
//		if (!pGDS.getValue().equals("SABRE")&&!pGDS.getValue().equals("NDC"))
//			return;
//		String directory = getDirectory().substring(0, getDirectory().length() - 2) + getCodigopnr().substring(0, 2);
//		JRecords<BizPNRFile> files = new JRecords<BizPNRFile>(BizPNRFile.class);
//
//		files.setStatic(true);
//		files.clearStaticItems();
//
//		FilenameFilter textFilter = new FilenameFilter() {
//			String patron;
//
//			public FilenameFilter setPatron(String patron) {
//				this.patron = patron.toLowerCase();
//				return this;
//			}
//
//			public boolean accept(File dir, String name) {
//				String lowercaseName = name.toLowerCase();
//				if (lowercaseName.indexOf(patron) != -1) {
//					return true;
//				} else {
//					return false;
//				}
//			}
//		}.setPatron(getCodigopnr().toLowerCase());
//
//		File dir = new File(JPath.PssPathInputProcessed() + "/" + directory);
//		File fnames[] = dir.listFiles(textFilter);
//
//		// String[] names = dir.list(textFilter);
//
//		if (fnames == null)
//			return;
//
//		fnames = sortFilesByName(fnames);
//
//		for (int i = 0; i < fnames.length; i++) {
//
//			String fname = fnames[i].getName();
//			BizPNRFilename f = new BizPNRFilename();
//
//			BizPNRFilename file = new BizPNRFilename();
//			file.dontThrowException(true);
//			file.addFilter("company", getCompany());
//			file.addFilter("pnr", getCodigopnr());
//			file.addFilter("filename", fname);
//			file.addFilter("directory", directory);
//			if (file.read()) {
//				isBugCTS(fname, file, true);
//				continue;
//			}
//
//			f.setPNR(getCodigopnr());
//			f.setCompany(getCompany());
//			f.setArchivo(fname);
//			f.setDirectory(directory);
//			f.setDate(new Date());
//			isBugCTS(fname, file, false);
//
//			f.setInterfaceID(1);
//			try {
//				f.insert();
//			} catch (Exception eee) {
//				PssLogger.logDebug(eee);
//			}
//
//			f.setDescripcion("Archivo version " + getCodigopnr());
//
//		}
//
//	}
//
//	public static void isBugCTS(String fname, BizPNRFilename file, boolean exists) throws Exception {
//		try {
//			int p1 = fname.lastIndexOf("_");
//			int p2 = fname.lastIndexOf(".PNR");
//			if (p1 == -1 || p2 == -1)
//				return;
//			String d = fname.substring(p1 + 1, p2);
//			Date d1 = JDateTools.StringToDate(d, "ddMMyyyyHHmmss");
//			long orden = Long.parseLong(JDateTools.DateToString(d1, "yyyyMMddHHmmss"));
//			if (file.getOrderProcesamiento() != orden) {
//
//				file.setOrderProcesamiento(orden);
//				file.setDate(d1);
//				if (exists)
//					file.update();
//				;
//			}
//		} catch (Exception e) {
//			file.setOrderProcesamiento(System.nanoTime());
//			file.setDate(new Date());
//		}
//
//	}
		
	public void checkArchivosCompletos() throws Exception {
		if (pGDS.getValue().equals("SABRE") && !pGDS.getValue().equals("NDC")) {
			checkArchivosCompletosSabre();
			return;
		}
		if (pGDS.getValue().equals("AMADEUS")) {
			checkArchivosCompletosAmadeus();
			return;
		}
	}

	void checkArchivosCompletosAmadeus() throws Exception {
		JRecords<BizPNRFilename> file = new JRecords<BizPNRFilename>(BizPNRFilename.class);

		// Aplicar filtros para seleccionar los registros relevantes de la tabla
		file.addFilter("company", getCompany());
		file.addFilter("pnr", getCodigopnr());

		// Obtener todos los registros que coinciden con los filtros
		JIterator<BizPNRFilename> it = file.getStaticIterator();

		while (it.hasMoreElements()) {
			BizPNRFilename dbFile = it.nextElement();
			String directory = dbFile.getDirectory();
			String fname = dbFile.getArchivo();

			// Construir la ruta completa del archivo
			File fileEntry = new File(JPath.PssPathInputProcessed() + "/" + directory + "/" + fname);

			if (fileEntry.exists()) {
				continue;
			} else {
				directory = directory.substring(0, directory.length() - 2) + dbFile.getPNR().substring(3, 5);
				File fileEntry2 = new File(JPath.PssPathInputProcessed() + "/" + directory + "/" + fname);

				if (fileEntry2.exists()) {
					JTools.copyFile(fileEntry2.getAbsolutePath(), fileEntry.getAbsolutePath());
					continue;
				} else {
					directory = directory.substring(0, directory.length() - 2) + "1A";// bug
																																						// correccion
					File fileEntry3 = new File(JPath.PssPathInputProcessed() + "/" + directory + "/" + fname);

					if (fileEntry3.exists()) {
						JTools.copyFile(fileEntry3.getAbsolutePath(), fileEntry.getAbsolutePath());
						continue;
					}
					PssLogger.logDebug("El archivo no existe: " + fileEntry.getAbsolutePath());

				}
			}
		}

	}

  void checkArchivosCompletosSabre() throws Exception {
  	if ( getDirectory().equals("")) return;
    String directory = getDirectory().substring(0, getDirectory().length() - 2) + getCodigopnr().substring(0, 2);
    File dir = new File(JPath.PssPathInputProcessed() + "/" + directory);

    FilenameFilter textFilter = new FilenameFilter() {
        String patron;

        public FilenameFilter setPatron(String patron) {
            this.patron = patron.toLowerCase();
            return this;
        }

        public boolean accept(File dir, String name) {
            return name.toLowerCase().contains(patron);
        }
    }.setPatron(getCodigopnr().toLowerCase());

    File[] fnames = dir.listFiles(textFilter);
    if (fnames == null)
        return;

    fnames = sortFilesByName(fnames);

    for (File fileEntry : fnames) {
        String fname = fileEntry.getName();
        Date extractedDate = extractArrivalDate(fileEntry); // Extraer fecha del archivo

        
        // Formatear la fecha para que sea un Número positivo
        long orderProcesamiento = Long.parseLong(JDateTools.DateToString(extractedDate, "yyyyMMddHHmmss"));

        BizPNRFilename file = new BizPNRFilename();
        file.dontThrowException(true);
        file.addFilter("company", getCompany());
        file.addFilter("pnr", getCodigopnr());
        file.addFilter("filename", fname);
        file.addFilter("directory", directory);

        if (file.read()) {
          	file.setDescripcion("Archivo versión " + fname);
            file.setOrderProcesamiento(orderProcesamiento);
            try {
                if (JBDatos.GetBases().getPrivateCurrentDatabase().isTransactionInProgress())
                    file.update();
                else
                    file.execProcessUpdate();
            } catch (Exception e) {
                PssLogger.logDebug(e);
            }
        } else {
            file.setPNR(getCodigopnr());
            file.setCompany(getCompany());
            file.setArchivo(fname);
            file.setDirectory(directory);
            file.setDate(new Date());
            file.setOrderProcesamiento(orderProcesamiento);

            file.setInterfaceID(1);
            try {
                if (JBDatos.GetBases().getPrivateCurrentDatabase().isTransactionInProgress())
                    file.insert();
                else
                    file.execProcessInsert();
            } catch (Exception e) {
                PssLogger.logDebug(e);
            }
            file.setDescripcion("Archivo versión " + fname);
        }
    }
}


//Extraer fecha de llegada del archivo (primera línea)
	private Date extractArrivalDate(File file) {
		BufferedReader reader =null;
    try  {
    	reader = new BufferedReader(new FileReader(file));
        String firstLine = reader.readLine();
        if (firstLine != null && firstLine.length() >=11) {
            String datePart = firstLine.substring(2, 11); // Extraer 16DEC0145 (ddMMMHHmm)
            String year = extractYearFromFilename(file.getName()); // Extraer el año del nombre del archivo
            if (year != null) {
                String fullDate = datePart + year; // Combinar para formar 16DEC2024
                return JDateTools.StringToDate(JDateTools.convertMonthLiteralToNumberWithinSep(fullDate), "ddMMHHmmyyyy");
            }
        }
    } catch (Exception e) {
        PssLogger.logDebug("Error al leer fecha de llegada: " + e.getMessage());
    } finally {
      try {
				if (reader!=null) reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}  	
		} 
    return null;
}

// Extraer el año desde el nombre del archivo
private String extractYearFromFilename(String filename) {
    try {
        Pattern pattern = Pattern.compile("\\.(\\d{4})\\."); // Busca ".2024."
        Matcher matcher = pattern.matcher(filename);
        if (matcher.find()) {
            return matcher.group(1); // Retorna el año encontrado
        }
    } catch (Exception e) {
        PssLogger.logDebug("Error al extraer el año del archivo: " + e.getMessage());
    }
    return null;
}
	
	public static void main(String[] args) {
		try {
			BizPNRTicket tkt = new BizPNRTicket();
			tkt.migrate();
		} catch (Exception ee) {

		}
	}

	private void migrate() {
		try {
			JAplicacion.openSession();
			JAplicacion.GetApp().openApp("ticket_migration", "process", true);
			// JBDatos.GetBases().beginTransaction();

			JRecords<BizCompany> cpys = new JRecords<BizCompany>(BizCompany.class);
			cpys.addOrderBy("company");
			JIterator<BizCompany> it = cpys.getStaticIterator();

			while (it.hasMoreElements()) {
				BizCompany cpy = it.nextElement();

				while (true) {

					JRecords<BizPNRTicket> tkts = new JRecords<BizPNRTicket>(BizPNRTicket.class);
					tkts.addFilter("company", cpy.getCompany());
					tkts.addFilter("gds", "SABRE");
					tkts.addFixedFilter("migrated is null");
					tkts.addOrderBy("codigopnr");
					tkts.setTop(1000);
					tkts.toStatic();
					JBDatos.GetBases().beginTransaction();

					String pnr = "";
					boolean hasRecords = false;
					while (tkts.nextRecord()) {
						hasRecords = true;
						BizPNRTicket tkt = tkts.getRecord();
						tkt.pMigrated.setValue(true);
						tkt.update();

						if (pnr.equals(tkt.getCodigopnr())) {
							continue;
						}

						pnr = tkt.getCodigopnr();
						tkt.getArchivosOld();

					}

					JBDatos.GetBases().commit();

					if (hasRecords == false)
						break;

				}

			}
			// JBDatos.GetBases().commit();
			JAplicacion.GetApp().closeApp();
			JAplicacion.closeSession();

		} catch (Exception e2) {
			PssLogger.logError(e2);
		}

	}

	public JRecords<BizPNRFile> getArchivos() throws Exception {
		checkArchivosCompletos();

		JRecords<BizPNRFile> files = new JRecords<BizPNRFile>(BizPNRFile.class);
		files.setStatic(true);
		files.clearStaticItems();

		JRecords<BizPNRFilename> fs = new JRecords<BizPNRFilename>(BizPNRFilename.class);
		fs.addFilter("company", this.getCompany());
		fs.addFilter("pnr", this.getCodigopnr());
		// fs.addOrderBy("creation_date","ASC");
		fs.addOrderBy("orden_proc");
		fs.toStatic();

		while (fs.nextRecord()) {
			BizPNRFilename file = fs.getRecord();

			BizPNRFile f = new BizPNRFile();
			f.setPNR(file.getPNR());
			f.setCompany(file.getCompany());
			f.setArchivo(file.getArchivo());
			f.setDirectory(file.getDirectory());
			int pos = file.getArchivo().lastIndexOf(".");

			f.setDescripcion("Archivo version " + file.getArchivo());

			files.getStaticItems().addElement(f);
		}
		return files;
	}

	public File[] sortFilesByName(File[] fList) {
		try {
			Arrays.sort(fList, new Comparator<File>() {
				public int compare(File file1, File file2) {
					return Integer.valueOf(file1.getName().compareTo(file2.getName()));
				}
			});
		} catch (Exception ee) {
			System.out.println(ee.getMessage());
		}
		return fList;
	}

	public File[] sortFilesByLastModDate(File[] fList) {
		try {
			Arrays.sort(fList, new Comparator<File>() {
				public int compare(File file1, File file2) {
					return Long.valueOf(file1.lastModified()).compareTo(file2.lastModified());
				}
			});
		} catch (Exception ee) {
			System.out.println(ee.getMessage());
		}
		return fList;
	}

	public JRecords<BizPNRTax> getObjTaxes() throws Exception {
		if (this.taxes != null)
			return this.taxes;
		return (this.taxes = this.getTaxes());

	}

	public static void processReprocessAllTicket(String company) throws Exception {
		JRecords<BizPNRTicket> tickets = new JRecords<BizPNRTicket>(BizPNRTicket.class);
		tickets.addFilter("company", company);
		tickets.addFilter("creation_date", JDateTools.getFirstDayOfYear(new Date()), ">=");
		tickets.readAll();
		while (tickets.nextRecord()) {
			BizPNRTicket ticket = tickets.getRecord();
	//		if (!ticket.getCarrier().equals("AA")) continue;
			ticket.processAnalizeSegments(ticket.getSegments());
			ticket.update();
		}
	}

	public void processReemitir(boolean save) throws Exception {
		setReemitted(true);
		if (save)
			update();
		JRecords<BizBooking> book = new JRecords<BizBooking>(BizBooking.class);
		book.addFilter("company", getCompany());
		book.addFilter("interface_id", getId());
		JIterator<BizBooking> it = book.getStaticIterator();
		while (it.hasMoreElements()) {
			BizBooking bok = it.nextElement();
			bok.setReemitido(true);
			bok.update();
		}
	}

	public void processAnular(Date date) throws Exception {
		Calendar creacion = Calendar.getInstance();
		Calendar anul = Calendar.getInstance();

		setVoid(true);
		if (date==null) {
			anul.setTime(getCreationDate());
		} else {
			creacion.setTime(getCreationDate());
			anul.setTime(date);
			if (!creacion.equals(anul) && creacion.after(anul))// la anulacion viene sin año, se toma el del boleto pero puede fallar
				anul.add(Calendar.YEAR, 1);
		}
		setVoidDate(anul.getTime());
		
		update();
		JRecords<BizBooking> book = new JRecords<BizBooking>(BizBooking.class);
		book.addFilter("company", getCompany());
		book.addFilter("interface_id", getId());
		book.delete();
	}

	@Override
	public void processDelete() throws Exception {
		JRecords<BizPNRSegmentoAereo> segs = new JRecords<BizPNRSegmentoAereo>(BizPNRSegmentoAereo.class);
		segs.addFilter("company", getCompany());
		segs.addFilter("interface_id", getId());
		segs.delete();
		JRecords<BizPNRTax> taxs = new JRecords<BizPNRTax>(BizPNRTax.class);
		taxs.addFilter("company", getCompany());
		taxs.addFilter("interface_id", getId());
		taxs.delete();
		JRecords<BizPNRFare> fare = new JRecords<BizPNRFare>(BizPNRFare.class);
		fare.addFilter("company", getCompany());
		fare.addFilter("interface_id", getId());
		fare.delete();
		JRecords<BizBooking> book = new JRecords<BizBooking>(BizBooking.class);
		book.addFilter("company", getCompany());
		book.addFilter("interface_id", getId());
		book.delete();
		super.processDelete();
	}

	public void processDeleteChild() throws Exception {
		JRecords<BizPNRSegmentoAereo> segs = new JRecords<BizPNRSegmentoAereo>(BizPNRSegmentoAereo.class);
		segs.addFilter("company", getCompany());
		segs.addFilter("interface_id", getId());
		segs.delete();
		JRecords<BizPNRTax> taxs = new JRecords<BizPNRTax>(BizPNRTax.class);
		taxs.addFilter("company", getCompany());
		taxs.addFilter("interface_id", getId());
		taxs.delete();
		JRecords<BizPNRFare> fare = new JRecords<BizPNRFare>(BizPNRFare.class);
		fare.addFilter("company", getCompany());
		fare.addFilter("interface_id", getId());
		fare.delete();
		JRecords<BizBooking> book = new JRecords<BizBooking>(BizBooking.class);
		book.addFilter("company", getCompany());
		book.addFilter("interface_id", getId());
		book.delete();
	}

	/*
	 * public static void processReprocessAllTicket(String company) throws
	 * Exception { JRecords<BizPNRTicket> tickets = new
	 * JRecords<BizPNRTicket>(BizPNRTicket.class); tickets.addFilter("company",
	 * company); tickets.readAll(); String bookings=""; String origen=""; String
	 * destino=""; String checkpais = null; boolean international=false; while
	 * (tickets.nextRecord()) { BizPNRTicket ticket = tickets.getRecord(); String
	 * clase = null; String tipoclase = null; long clasePrioridad = 0;
	 * JRecords<BizPNRSegmentoAereo> segs = ticket.getSegments(); long
	 * cantSegments=0; segs.readAll(); while (segs.nextRecord()) {
	 * BizPNRSegmentoAereo seg = segs.getRecord(); if (origen.equals(""))
	 * origen=seg.getDespegue(); destino=seg.getArrivo(); seg.processCalculate();
	 * seg.update(); if (!seg.getConnectionIndicator().equals
	 * (BizPNRSegmentoAereo.SEGMENTO_CONNECTION)) { bookings=(bookings.equals("")
	 * ?"":",")+"TKM_"+origen.toUpperCase()+destino.toUpperCase(); cantSegments++;
	 * origen=""; } if (seg.getObjAeropuertoArrivo().getObjCountry()==null)
	 * continue; if (checkpais==null)
	 * checkpais=seg.getObjAeropuertoDespegue().getObjCountry().GetPais(); if
	 * (!checkpais
	 * .equals(seg.getObjAeropuertoArrivo().getObjCountry().GetPais()))
	 * international=true;
	 * 
	 * if (clase != null) { if (clasePrioridad < seg.getPrioridad()) { clase =
	 * seg.getClase(); tipoclase = seg.getTipoClase(); clasePrioridad =
	 * seg.getPrioridad(); } } else { clase = seg.getClase(); tipoclase =
	 * seg.getTipoClase(); clasePrioridad = seg.getPrioridad(); }
	 * 
	 * } fillMercado(ruta, rutaPais,bookings);
	 * 
	 * ticket.setBookings(cantSegments==0?1:cantSegments);
	 * ticket.setInternacional(international);
	 * ticket.setInternacionalDescr(international ? "International" : "Domestic");
	 * ticket.setDiasCompras(JDateTools.getDaysBetween(ticket.getCreationDate(),
	 * ticket.getDepartureDate()));
	 * ticket.setDiasViajados(JDateTools.getDaysBetween
	 * (ticket.getDepartureDate(), ticket.getArriveDate()));
	 * ticket.setClase(clase); ticket.setTipoClase(tipoclase);
	 * 
	 * ticket.update(); } }
	 */

	public JRecords<BizPNRTax> getTaxes() throws Exception {
		JRecords<BizPNRTax> detalles = new JRecords<BizPNRTax>(BizPNRTax.class);
		detalles.addFilter("interface_id", getId());
		detalles.readAll();
		detalles.toStatic();
		return detalles;
	}

	public JRecords<BizPNRSegmentoAereo> getSegments() throws Exception {
		JRecords<BizPNRSegmentoAereo> detalles = new JRecords<BizPNRSegmentoAereo>(BizPNRSegmentoAereo.class);
		detalles.addFilter("interface_id", getId());
		detalles.addOrderBy("CodigoSegmento", "ASC");
		detalles.toStatic();
		return detalles;
	}

	public JRecords<BizPNRFare> getSegmentsFare() throws Exception {
		JRecords<BizPNRFare> detalles = new JRecords<BizPNRFare>(BizPNRFare.class);
		detalles.addFilter("interface_id", getId());
		detalles.addOrderBy("secuencia", "ASC");
		detalles.readAll();
		detalles.toStatic();
		return detalles;
	}

	public String getRecordName() throws Exception {
		return "Boletos";
	}

	public BizAirport findAeropuerto(String code) throws Exception {
		return PNRCache.getAirportCache().getElement(code);
	}

	public BizAirport getObjAeropuertoOrigen() throws Exception {
		if (this.aeropuertoOrigen != null)
			return this.aeropuertoOrigen;
		return this.aeropuertoOrigen = PNRCache.getAirportCache().getElement(this.pAeropuertoOrigen.getValue());
	}

	BizPNRTicket objReemitted;

	public BizPNRTicket getObjReemitted() throws Exception {
		if (pRefOriginal.isNull())
			return null;
		if (this.objReemitted != null)
			return this.objReemitted;
		BizPNRTicket a = new BizPNRTicket();
		a.dontThrowException(true);
		if (!a.read(this.pRefOriginal.getValue(), this.pOriginalNumeroboleto.getValue()))
			return null;
		return (this.objReemitted = a);
	}

	public BizCarrier getObjCarrier() throws Exception {
		if (this.objCarrier != null)
			return this.objCarrier;
		return this.objCarrier= PNRCache.getCarrierCache().getElement(this.pCodigoaerolinea.getValue());

	}

	public BizAirport getObjAeropuertoDestino() throws Exception {
		if (this.aeropuertoDestino != null)
			return this.aeropuertoDestino;
		return this.aeropuertoDestino = PNRCache.getAirportCache().getElement(this.pAeropuertoDestino.getValue());
	}

	public BizMoneda getObjMoneda() throws Exception {
		return PNRCache.getMonedaCache().getElement(this.pCodigoMoneda.getValue());
	}

	public Date getDateValue(String field, boolean check) throws Exception {
		if (field.equals(IConciliable.FECHA))
			return pVoid.getValue() ? null : pRefund.getValue() ? null : pCreationDateAir.getValue();
		return null;
	}

	public Long getLongValue(String field, boolean check) throws Exception {
		if (field.equals(IConciliable.ID_AEROLINEA))
			return getObjCarrier() == null || getObjCarrier().getCodIata().equals("") ? null : Long.parseLong(getObjCarrier().getCodIata());
		return null;
	}

	public Double getDoubleValue(String field, String moneda, boolean check) throws Exception {
		if (moneda!=null&&moneda.equals("USD"))
			return getDoubleValueUSD(field, check);
		return getDoubleValueLocal(field, check);
			
	}
	
	public Double getDoubleValueUSD(String field, boolean check) throws Exception {
		if (field.equals(IConciliable.COMISION))
			return null;
		if (field.equals(IConciliable.COMISION_OVER))
			return null;
		if (field.equals(IConciliable.COMISION_PORC))
			return null;
		if (field.equals(IConciliable.COMISION_INV))
			return null;
		if (field.equals(IConciliable.COMISION_OVER_INV))
			return null;
		if (field.equals(IConciliable.IMP_SOBRE_COMISION_INV))
			return null;
		if (field.equals(IConciliable.TOTAL))
			return pTarifaFacturaConImpuestosDolares.getValue();
		if (field.equals(IConciliable.TOTALAPAGAR))
			return null;
		if (field.equals(IConciliable.BASE_IMPONIBLE))
			return null;
		if (field.equals(IConciliable.CONTADO))
			return null;
		if (field.equals(IConciliable.IMP_SOBRE_COMISION))
			return null;
		if (field.equals(IConciliable.IMPUESTO_1))
			return null;
		if (field.equals(IConciliable.IMPUESTO_2))
			return null;
		if (field.equals(IConciliable.IMPUESTO_ACUM))
			return JTools.rd(pTarifaFacturaConImpuestosDolares.getValue() - pTarifaFacturaDolares.getValue(), 2);
		if (field.equals(IConciliable.TARIFA))
			return pTarifaFacturaDolares.getValue();
		if (field.equals(IConciliable.TARJETA))
			return null;
		if (field.equals(IConciliable.TARJETA_BRUTO))
			return null;
		if (field.equals(IConciliable.CONTADO_BRUTO))
			return null;
		if (field.equals(IConciliable.NETO))
			return null;
		if (field.equals(IConciliable.COMISION_ACUM))
			return null;
		// if (field.equals(IConciliable.COMISION_ACUM_INV)) return null;
		if (field.equals(IConciliable.TARIFA_SIN_COMISION))
			return null;
		return null;
	}	
	public Double getDoubleValueLocal(String field, boolean check) throws Exception {
		if (field.equals(IConciliable.COMISION))
			return null;
		if (field.equals(IConciliable.COMISION_OVER))
			return pImporteover.getValue();
		if (field.equals(IConciliable.COMISION_PORC))
			return pPorcentajeover.getValue();
		if (field.equals(IConciliable.COMISION_INV))
			return null;
		if (field.equals(IConciliable.COMISION_OVER_INV))
			return null;
		if (field.equals(IConciliable.IMP_SOBRE_COMISION_INV))
			return null;
		if (field.equals(IConciliable.TOTAL))
			return null;
		if (field.equals(IConciliable.BASE_IMPONIBLE))
			return pTarifaFactura.getValue();
		if (field.equals(IConciliable.CONTADO))
			return null;
		if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isCNS()) {
			if (field.equals(IConciliable.IMP_SOBRE_COMISION))
				return pIvaover.getValue();
			if (field.equals(IConciliable.TOTALAPAGAR))
				return JTools.rd(pTarifaFacturaConImpuestosLocal.getValue()-pMontoTarjeta.getValue()+pImporteover.getValue()+pIvaover.getValue(),2);
		} else {
			if (field.equals(IConciliable.IMP_SOBRE_COMISION))
				return null;
			if (field.equals(IConciliable.TOTALAPAGAR))
				return null;
			}
		if (field.equals(IConciliable.IMPUESTO_1))
			return null;
		if (field.equals(IConciliable.IMPUESTO_2))
			return null;
		if (field.equals(IConciliable.IMPUESTO_ACUM))
			return JTools.rd(pTotalImpuestosFacturaa.getValue(), 2);
		if (field.equals(IConciliable.TARIFA))
			return pTarifaFactura.getValue();
		if (field.equals(IConciliable.TARJETA))
			return null;
		if (field.equals(IConciliable.TARJETA_BRUTO))
			return pMontoTarjeta.getValue();
		if (field.equals(IConciliable.CONTADO_BRUTO))
			return pTarifaFacturaConImpuestosLocal.getValue()-pMontoTarjeta.getValue();
		if (field.equals(IConciliable.NETO))
			return null;
		if (field.equals(IConciliable.COMISION_ACUM))
			return null;
		// if (field.equals(IConciliable.COMISION_ACUM_INV)) return null;
		if (field.equals(IConciliable.TARIFA_SIN_COMISION))
			return null;
		return null;
	}

	public String getStringValue(String field, boolean check) throws Exception {
		if (field.equals(IConciliable.BOLETOS))
			return pNumeroboleto.getValue();
		if (field.equals(IConciliable.AEROLINEA_BOLETOS))
			return check?null:(getObjCarrier()==null?"":(getObjCarrier().getCodIata().equals("")?"("+getObjCarrier().getCarrier()+")":getObjCarrier().getCodIata())+" ")+pNumeroboleto.getValue();
		if (field.equals(IConciliable.BOLETOS_LARGO))
			return check?null:getObjCarrier()==null?"":(getObjCarrier().getCodIata().equals("")?"("+getObjCarrier().getCarrier()+")":getObjCarrier().getCodIata())+" "+pNumeroboleto.getValue();;
		if (field.equals(IConciliable.AEROLINEA))
			return check?null:getObjCarrier()==null?"":getObjCarrier().getDescription().toUpperCase();
		if (field.equals(IConciliable.ID_AEROLINEA))
			return check?null:getObjCarrier()==null?"":getObjCarrier().getCarrier();
		if (field.equals(IConciliable.NUMERO_TARJETA))
			return check?null: pNumeroTarjeta.getValue();
		if (field.equals(IConciliable.OBSERVACION))
			return null;
		if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isCNS()) {
			if (field.equals(IConciliable.OPERACION))
				return check?(pRefund.getValue()?null:pVoid.getValue()?"CAN": pTipoOperacion.getValue().equals("ETR")?"TKT":"EMD"):pTipoOperacion.getValue();
			if (field.equals(IConciliable.TIPO_RUTA))
				return pInternacional.getValue()?"I":"D";
	
		}else {
			if (field.equals(IConciliable.OPERACION))
				return null;
			if (field.equals(IConciliable.TIPO_RUTA))
				return pInternacional.getValue()?"INTERNACIONAL":"DOMESTICO";
		}

		if (field.equals(IConciliable.TIPO_TARJETA))
			return null;
			// if (field.equals(IConciliable.FECHA)) return
		// pDepartureDate.getValue().toLocaleString();
		// if (field.equals(IConciliable.COMISION)) return new
		// Double(pComisionAmount.getValue()).toString();
		// if (field.equals(IConciliable.COMISION_OVER)) return new
		// Double(pImporteover.getValue()).toString();
		// if (field.equals(IConciliable.COMISION_PORC)) return new Double(
		// pComisionPerc.getValue()).toString();
		// if (field.equals(IConciliable.TOTAL)) return new
		// Double(pTarifa.getValue()).toString();
		// if (field.equals(IConciliable.BASE_IMPONIBLE)) return new
		// Double(pTarifaBase.getValue()).toString();
		// if (field.equals(IConciliable.CONTADO)) return null;
		// if (field.equals(IConciliable.IMP_SOBRE_COMISION)) return null;
		// if (field.equals(IConciliable.IMPUESTO_1)) return null;
		// if (field.equals(IConciliable.IMPUESTO_2)) return null;
		// if (field.equals(IConciliable.IMPUESTO_ACUM)) return null;
		// if (field.equals(IConciliable.TARIFA)) return new
		// Double(pTarifa.getValue()).toString();
		// if (field.equals(IConciliable.TARJETA)) return null;
		// if (field.equals(IConciliable.COMISION_INV)) return null;
		// if (field.equals(IConciliable.COMISION_OVER_INV)) return null;
		// if (field.equals(IConciliable.IMP_SOBRE_COMISION_INV)) return null;
		// if (field.equals(IConciliable.TARJETA_BRUTO)) return null;
		// if (field.equals(IConciliable.CONTADO_BRUTO)) return null;
		// if (field.equals(IConciliable.NETO)) return new
		// Double(pNeto.getValue()).toString();
		// if (field.equals(IConciliable.COMISION_ACUM)) return null;
		// if (field.equals(IConciliable.COMISION_ACUM_INV)) return null;
		// if (field.equals(IConciliable.TARIFA_SIN_COMISION)) return new
		// Double(pTarifaBase.getValue()).toString();
		return null;
	}

	@Override
	public void setFormato(IFormato formato) {

	}

	public void extractPersonas() throws Exception {
		if (pCodigoCliente.isNotNull())
			BizMailingPersona.addPersona(BizMailingPersona.CLIENTERMK, getCompany(), pCodigoCliente.getValue());
		if (pCustomerId.isNotNull())
			BizMailingPersona.addPersona(BizMailingPersona.CLIENTE, getCompany(), pCustomerId.getValue());
		if (pCustomerIdReducido.isNotNull())
			BizMailingPersona.addPersona(BizMailingPersona.CLIENTE_REDUCIDO, getCompany(), pCustomerIdReducido.getValue());
		if (pOfficeId.isNotNull())
			BizMailingPersona.addPersona(BizMailingPersona.SUCURSAL, getCompany(), pOfficeId.getValue());
		if (pCentroCostos.isNotNull())
			BizMailingPersona.addPersona(BizMailingPersona.CCOSTO, getCompany(), pCentroCostos.getValue());
		if (pVendedor.isNotNull())
			BizMailingPersona.addPersona(BizMailingPersona.VENDEDOR, getCompany(), pVendedor.getValue());
		if (pNroIata.isNotNull())
			BizMailingPersona.addPersona(BizMailingPersona.IATA, getCompany(), pNroIata.getValue());
	}

	public void assignFareFrom(BizPNRTicket t) {

	}
	
	public boolean hasUpfront() throws Exception {
		if (pUpfrontRef.isNull()) return false;
		return true;
	}
	public BizDetalle getObjUpfront() throws Exception {
		if (pUpfrontRef.isNull()) return null;
		BizDetalle det = new BizDetalle();
		det.dontThrowException(true);
		if (!det.read(pUpfrontRef.getValue())) return null;
		return det;
	}
	public BizDetalle getObjContrato(long idContrato) throws Exception {
		BizDetalle det = new BizDetalle();
		det.dontThrowException(true);
		if (!det.read(idContrato)) return null;
		return det;
	}
	public String getUpfrontDescripcion() throws Exception {
		if (pUpfrontRef.isNull()) return "";
		BizDetalle det = getObjUpfront();
		if (det==null) return "";
		return det.getDescrContrato() + " "+det.getDescripcion();
	}

	public void checkReserva() throws Exception {
		BizPNRReserva reserva = new BizPNRReserva();
		reserva.dontThrowException(true);
		if (!reserva.readByName(getCompany(), getNroIata(), getCodigopnr(), getNombrePasajero())) return;
		if (!hasReserva())
			setReserva(reserva.getCreationDate());
		reserva.setTicket(this.getId());
		reserva.processUpdate();
	}

	class AuxiliarClaseCalculoSegmentos {
		long maxTiempo = 0;
		String airportInicio = null;
		String airportFin = null;
		BizPNRSegmentoAereo segmentoMaximo = null;
		BizPNRSegmentoAereo ant = null;
		boolean paradaLarga = false;
		long cantSegmentos = 0;
		long cantSegmentoIR = 0;
		boolean international = false;
		boolean roundTrip = false;
		boolean hayInfoConexiones = true;
		String ruta = "";
		String rutaPais = "";
		String antAep = null;
		String antCarrier = null;
		String antAepPais = null;
		String aep = null;
		String aepPais = null;
		String bookings = "";
		String segments = "";
		Date fechaSalida = null;
		JIterator<BizPNRSegmentoAereo> iter;
		boolean yendo = true;
		BizPNRSegmentoAereo lastSeg = null;
		String clase = null;
		String tipoclase = null;
		String origen = "";
		String destino = "";
		String paisorigen = "";
		String paisdestino = "";
		String areaorigen = null;
		String areadestino = null;
		String checkpais = null;
		String firstCarrier = null;
		JRecords<BizPNRSegmentoAereo> segmentos;
		

		public void processAnalizeSegmentsInterno(JRecords<BizPNRSegmentoAereo> zsegmentos) throws Exception {
			checkReserva();
			segmentos = zsegmentos;
			if (segmentos == null) {
				segmentos = new JRecords<BizPNRSegmentoAereo>(BizPNRSegmentoAereo.class);
				segmentos.addFilter("company", getCompany());
				segmentos.addFilter("interface_id", getId());
				segmentos.toStatic();
				segmentos.firstRecord();
			}
			segmentos.Ordenar("LongCodigoSegmento");
			if (pGDS.getValue().equals("GALILEO"))
				calculeMaxSegmentoGalileo();
			else
				calculeMaxSegmento();
			if (airportInicio == null) {
				return;
			}

			setCantidadRoundTrip(/* cantSegmentoIR */roundTrip ? 2 : 1);
			if (pGDS.getValue().equals("GALILEO"))
				armarRutaGalileo();
			else
				armarRuta();
			calculosVarios();
			setRoute(ruta);
			setImpuestosTotal(getImpuestos() + getIva());
			setImpuestosTotalFactura(getImpuestoFactura() + getIvaFactura());

			String market = fillMercado(ruta, rutaPais, bookings, segments, pMiniRoute.getValue());
			setMarket(market);

			setBookings(cantSegmentos == 0 ? 1 : cantSegmentos);
			setCantidadRoundTrip(/* cantSegmentoIR */roundTrip ? 2 : 1);
			setTipoOperacion(getTipoOperacion());
			setClase(clase);
			setTipoClase(tipoclase);
			setFirstCarrierInternacional(firstCarrier);
			setDepartureDate(fechaSalida);
//			if (getDepartureDate() != null && JDateTools.getDaysBetween(getDepartureDate(), getCreationDate()) > 10) {// hay
//				// un margen porque encontre boletos confechas de salida unos dias
//				// posteriores a la de despegue
//				Calendar cal = Calendar.getInstance();
//				cal.setTime(getCreationDate());
//				cal.add(Calendar.YEAR, -1);
//				setCreationDate(cal.getTime());
//				cal.setTime(pCreationDateAir.getValue());
//				cal.add(Calendar.YEAR, -1);
//				setCreationDateAir(cal.getTime());
//
//			}
			setDiasCompras(getDepartureDate() == null ||getCreationDate() == null ? 0 : JDateTools.getDaysBetween(getCreationDate(), getDepartureDate()));
			if (getReserva() != null)
				setPreCompras(getCreationDate() == null ? 0 : JDateTools.getDaysBetween(getReserva(), getCreationDate()));
			setDiasCompras(getDepartureDate() == null ||getCreationDate() == null ? 0 : JDateTools.getDaysBetween(getCreationDate(), getDepartureDate()));
			setDiasViajados(getArriveDate() == null || getDepartureDate() == null ? 0 : JDateTools.getDaysBetween(getDepartureDate(), getArriveDate()));
			setFechaProcesamiento(new Date());
		}
		public BizPNRSegmentoAereo segmentRound(BizPNRSegmentoAereo s) throws Exception {
			JIterator<BizPNRSegmentoAereo> iterS = segmentos.getStaticIterator();
			while (iterS.hasMoreElements()) {
				BizPNRSegmentoAereo s1 = iterS.nextElement();
				if (s.getCodigoSegmento()==s1.getCodigoSegmento()) continue;
				if (!(s.getDespegue().equals(s1.getArrivo()))) continue;
				if (!(s1.getDespegue().equals(s.getArrivo()))) continue;
				return s1;
			}			
			return null;
		}
		public void calculosVarios() throws Exception {
			JOrderedMap<Long, JPair> books = JCollectionFactory.createOrderedMap();
			JOrderedMap<Long, JPair> booksCT = JCollectionFactory.createOrderedMap();
			JOrderedMap<Long, JPair> booksComb = JCollectionFactory.createOrderedMap();
			JOrderedMap<Long, JPair> booksCombA = JCollectionFactory.createOrderedMap();
			JOrderedMap<Long, JPair> booksAreas = JCollectionFactory.createOrderedMap();
			String countryCompany = getObjCompany().getCountry();
			long clasePrioridad = 0;

			long nivel = 0;
			BizPNRSegmentoAereo round = null;
			Calendar fechaDespegue = null;
			Calendar fechaArrivo = null;
			Calendar fechaFinViaje = Calendar.getInstance();
			BizBooking bok = null;
			double montoEstimado = segmentos.getStaticItems().size() == 0 ? 0 : getNeto() / segmentos.getStaticItems().size();
			double montoEstimadoFactura = segmentos.getStaticItems().size() == 0 ? 0 : getNetoFacturaLocal() / segmentos.getStaticItems().size();
			double estimadoSegmento = 0;
			double estimadoSegmentoFactura = 0;
			double montoEstimadoUsd = segmentos.getStaticItems().size() == 0 ? 0 : getTarifaNetoUsa() / segmentos.getStaticItems().size();
			double montoEstimadoUsdFactura = segmentos.getStaticItems().size() == 0 ? 0 : getNetoFacturaDolares() / segmentos.getStaticItems().size();
			double estimadoSegmentoUsd = 0;
			double estimadoSegmentoUsdFactura = 0;
			setAeropuertoOrigen(segmentos.getFirstRecord().getDespegue());
			JIterator<BizPNRSegmentoAereo> iterS = segmentos.getStaticIterator();
			String aero = getCarrier();
			BizPNRSegmentoAereo sAnt=null;
			boolean pura = true;
			while (iterS.hasMoreElements()) {
				BizPNRSegmentoAereo s = iterS.nextElement();
				round = segmentRound(s);
				
				if (s.pFechaDespegue.isNotNull() && s.getHoraDespegue().length() > 3) {
					fechaDespegue = Calendar.getInstance();
					fechaDespegue.setTime(s.getFechaDespegue());
					int pos = s.getHoraDespegue().indexOf(":") == -1 ? 2 : s.getHoraDespegue().indexOf(":");
					int pos2 = s.getHoraDespegue().indexOf(":") == -1 ? 2 : s.getHoraDespegue().indexOf(":") + 1;
					fechaDespegue.set(Calendar.HOUR_OF_DAY, new Integer(s.getHoraDespegue().substring(0, pos)));
					fechaDespegue.set(Calendar.MINUTE, new Integer(s.getHoraDespegue().substring(pos2)));
				} else
					fechaDespegue = null;

				s.processCalculate();
				s.setDestinoPais(s.getObjAeropuertoDestino()==null||s.getObjAeropuertoDestino().getObjCountry() == null ? null : s.getObjAeropuertoDestino().getObjCountry().GetPais());
				s.setOrigenPais(s.getObjAeropuertoOrigen()==null||s.getObjAeropuertoOrigen().getObjCountry() == null ? null : s.getObjAeropuertoOrigen().getObjCountry().GetPais());
				s.processUpdateOrInsertWithCheck();
				origen = s.getDespegue();
				destino = s.getArrivo();
				pura &= (aero.equals(s.getCarrier()));
				paisorigen = s.getObjAeropuertoDespegue()==null||s.getObjAeropuertoDespegue().getObjCountry() == null ? null : s.getObjAeropuertoDespegue().getObjCountry().GetPais();
				paisdestino = s.getObjAeropuertoArrivo()==null||s.getObjAeropuertoArrivo().getObjCountry() == null ? null : s.getObjAeropuertoArrivo().getObjCountry().GetPais();
				areaorigen = s.getObjAeropuertoDespegue()==null||s.getObjAeropuertoDespegue().getObjCountry() == null ? null : s.getObjAeropuertoDespegue().getIataArea();
				areadestino = s.getObjAeropuertoArrivo()==null||s.getObjAeropuertoArrivo().getObjCountry() == null ? null : s.getObjAeropuertoArrivo().getIataArea();
				if (areaorigen == null || areaorigen.equals(""))
					areaorigen = "";
				if (areadestino == null || areadestino.equals(""))
					areadestino = "";
				segments += (segments.equals("") ? "" : ",") + "SEG-" + origen + destino;
				if (!areaorigen.equals(""))
					segments += (segments.equals("") ? "" : ",") + "SEG-" + areaorigen + destino;
				if (!areadestino.equals(""))
					segments += (segments.equals("") ? "" : ",") + "SEG-" + origen + areadestino;
				if (!areaorigen.equals("") && !areadestino.equals(""))
					segments += (segments.equals("") ? "" : ",") + "SEG-" + areaorigen + areadestino;
				if (paisdestino != null) {
					if (checkpais == null)
						checkpais = paisdestino;
					String pais = BizBSPCompany.getObjBSPCompany(getCompany()).getPais();
					String paO = paisorigen;
					String pa = paisdestino;
					if (checkpais.equals("1"))
						checkpais = "AR";
					if (pais.equals("1"))
						pais = "AR";
					if (pa.equals("1"))
						pa = "AR";
					if (paO != null)
						if (paO.equals("1"))
							paO = "AR";

					if (pais != null && !pais.equals(paO))
						international = true;
					if (pais != null && !pais.equals(pa))
						international = true;
					if (pais != null && checkpais != null && !pais.equals(checkpais))
						international = true;
					if (checkpais != null && !checkpais.equals(pa))
						international = true;

					if (firstCarrier == null && international) {
						firstCarrier = s.getCarrier();
					}
					if (fechaSalida == null) {
						fechaSalida = s.getFechaDespegue();
					}

				}
				if (s.isTipoFin()) {
					setAeropuertoDestino(s.getArrivo());
					setArriveDate(s.getFechaArrivo());
				}
				if (!origen.equals(""))
					origen = s.getDespegue();
				boolean isConnection = false;
				if (!pGDS.getValue().equals("GALILEO"))
					isConnection = (antCarrier == null || s.getCarrier().equals(antCarrier)) && s.getConnectionIndicator().equals(BizPNRSegmentoAereo.SEGMENTO_CONNECTION);
				else
					isConnection = sAnt==null?false:sAnt.getConnectionIndicator().equals(BizPNRSegmentoAereo.SEGMENTO_CONNECTION);
				sAnt=s;
				if (isConnection && fechaArrivo != null && fechaDespegue != null) {
					int horas = Math.abs(fechaArrivo.fieldDifference(fechaDespegue.getTime(), Calendar.HOUR_OF_DAY));
					if (horas > 12) {
						isConnection = false;
					}
				}
				antCarrier = s.getCarrier();
				if (!isConnection) {
					if (bok != null) {
						if (bok.pMonto.getValue() == 0) {
							bok.setMoneda(getCodigoMoneda());
							bok.setMonto(estimadoSegmento);
							bok.setMontoRoundTrip(estimadoSegmento * getCantidadRoundTrip());
							bok.setMontoFactura(estimadoSegmentoFactura);
							bok.setMontoRoundTripFactura(estimadoSegmentoFactura * getCantidadRoundTrip());
						}
						if (bok.pMontoOriginal.getValue() == 0) {
							bok.setMonedaBase("USD");
							bok.setMontoOriginal(estimadoSegmentoUsd);
							bok.setMontoUsaRoundTrip(estimadoSegmentoUsd * getCantidadRoundTrip());
							bok.setMontoOriginalFactura(estimadoSegmentoUsdFactura);
							bok.setMontoUsaRoundTripFactura(estimadoSegmentoUsdFactura * getCantidadRoundTrip());
						}
						estimadoSegmento = 0;
						estimadoSegmentoUsd = 0;
						estimadoSegmentoFactura = 0;
						estimadoSegmentoUsdFactura = 0;
						bok.setReemitido(isReemited());
						if (!(isVoided() /*|| isReemited()*/))
							bok.processUpdateOrInsertWithCheck();
						bok = null;
					}
					bok = new BizBooking();
					BizBooking bok2 = new BizBooking();
					bok2.dontThrowException(true);
					if (bok2.Read(getCompany(), getId(), s.getCodigoSegmento()))
						bok = bok2;
					bok.copyCommonsProperties(s, true);
					bok.setCodigoSegmento(s.getCodigoSegmento());
					bok.setCarrierPlaca(getCarrier());
					bok.setNumeroBoleto(getNumeroboleto());
					bok.setNombrePasajero(getNombrePasajero());
					bok.setOrigen(s.getOrigen());
					bok.setDespegue(s.getDespegue());
					bok.setFechaDespegue(s.getFechaDespegue());
					bok.setHoraDespegue(s.getHoraDespegue());
					bok.setOrigenPais(paisorigen);
					bok.setArrivo(s.getArrivo());
					bok.setDestino(s.getDestino());
					bok.setFechaEmision(getCreationDateAir());
					bok.setFechaArrivo(s.getFechaArrivo());
					bok.setHoraArrivo(s.getHoraArrivo());
					bok.setDestinoPais(paisdestino);
					bok.setOrigenDestino(bok.getOrigen() + bok.getDestino());
					bok.setMonto(s.pMonto.getValue());
					bok.setMontoFactura(s.pMonto.getValue());
					
					bok.setRoundTrip(getCantidadRoundTrip());
					bok.setMontoRoundTrip(round==null?bok.pMonto.getValue():bok.pMonto.getValue() + round.pMonto.getValue());
					bok.setMontoRoundTripFactura(round==null?bok.pMontoFactura.getValue():bok.pMontoFactura.getValue() + round.pMonto.getValue());
					if (s.pMonedaBase.getValue().equals("USD")) {
						bok.setMontoOriginal(s.pMontoOriginal.getValue());
						bok.setMontoUsaRoundTrip(round==null?bok.pMontoOriginal.getValue():bok.pMontoOriginal.getValue()+ (!round.pMonedaBase.getValue().equals("USD") ? 0 : round.pMontoOriginal.getValue()));
						bok.setMontoOriginalFactura(s.pMontoOriginal.getValue());
						bok.setMontoUsaRoundTripFactura(round==null?bok.pMontoOriginalFactura.getValue():bok.pMontoOriginalFactura.getValue()+ (!round.pMonedaBase.getValue().equals("USD") ? 0 : round.pMontoOriginal.getValue()));
										
					} else {
						bok.setMontoOriginal(0);
						bok.setMontoUsaRoundTrip(0);
						bok.setMontoOriginalFactura(0);
						bok.setMontoUsaRoundTripFactura(0);
												
					}
				
					estimadoSegmento += montoEstimado;
					estimadoSegmentoUsd += montoEstimadoUsd;
					estimadoSegmentoFactura += montoEstimadoFactura;
					estimadoSegmentoUsdFactura += montoEstimadoUsdFactura;
					String marketSeg = fillMercado(bok.getOrigen() + "(" + bok.getCarrier() + ")" + bok.getArrivo(), "@" + bok.getPaisOrigen() + "(" + bok.getCarrier() + ")" + "@" + bok.getPaisDestino(), "BOK-@" + bok.getPaisOrigen() + bok.getArrivo() + ";BOK-" + bok.getOrigen() + bok.getArrivo() + ";BOK-@" + bok.getPaisOrigen() + "@" + bok.getPaisDestino() + (areadestino.equals("") ? "" : ";BOK-@" + bok.getPaisOrigen() + areadestino) + (areaorigen.equals("") ? "" : ";BOK-" + areaorigen + "@" + bok.getPaisDestino()), "", "");

					bok.setMercado(marketSeg);
					bok.setDK(getCustomerIdReducido());
					bok.setInternational(!(bok.getPaisDestino().equals(bok.getPaisOrigen()) && bok.getPaisOrigen().equals(countryCompany)));
					bok.setInternationalDomestic(bok.getInternational() ? "International" : "Domestic");
					

				} else {
					if (bok != null) {
						bok.setArrivo(s.getArrivo());
						bok.setDestino(s.getDestino());
						bok.setFechaArrivo(s.getFechaArrivo());
						bok.setHoraArrivo(s.getHoraArrivo());
						bok.setDestinoPais(paisdestino);
						bok.setOrigenDestino(bok.getOrigen() + bok.getDestino());
						String marketSeg = fillMercado(bok.getOrigen() + "(" + bok.getCarrier() + ")" + bok.getArrivo(), "@" + bok.getPaisOrigen() + "(" + bok.getCarrier() + ")" + "@" + bok.getPaisDestino(), "BOK-@" + bok.getPaisOrigen() + bok.getArrivo() + ";BOK-" + bok.getOrigen() + bok.getArrivo() + ";BOK-@" + bok.getPaisOrigen() + "@" + bok.getPaisDestino() + (areadestino.equals("") ? "" : ";BOK-@" + bok.getPaisOrigen() + areadestino) + (areaorigen.equals("") ? "" : ";BOK-" + areaorigen + "@" + bok.getPaisDestino()), "", "");
						double monto = bok.pMonto.getValue() + s.pMonto.getValue();
						double montousa = !s.pMonedaBase.getValue().equals("USD") ? 0 : bok.pMontoOriginal.getValue() + s.pMontoOriginal.getValue();
						double montoRound = bok.pMontoRoundTrip.getValue() + s.pMonto.getValue()+ (round==null?0:round.pMonto.getValue());
						double montousaRound = !s.pMonedaBase.getValue().equals("USD") ? 0 :bok.pMontoOriginalRoundTrip.getValue() + s.pMontoOriginal.getValue()+ (round==null?0:round.pMontoOriginal.getValue());
						
						double montoFactura = bok.pMontoFactura.getValue() + s.pMonto.getValue();
						double montousaFactura = !s.pMonedaBase.getValue().equals("USD") ? 0 : bok.pMontoOriginalFactura.getValue() + s.pMontoOriginal.getValue();
						double montoRoundFactura = bok.pMontoRoundTripFactura.getValue() + s.pMonto.getValue()+ (round==null?0:round.pMonto.getValue());
						double montousaRoundFactura = !s.pMonedaBase.getValue().equals("USD") ? 0 :bok.pMontoOriginalRoundTripFactura.getValue() + s.pMontoOriginal.getValue()+ (round==null?0:round.pMontoOriginal.getValue());
						
						bok.setMonto(monto);
						bok.setMontoOriginal(montousa);
						bok.setRoundTrip(getCantidadRoundTrip());
						bok.setMontoRoundTrip(montoRound);
						bok.setMontoUsaRoundTrip(montousaRound);
						bok.setMontoFactura(montoFactura);
						bok.setMontoOriginalFactura(montousaFactura);
						bok.setMontoRoundTripFactura(montoRoundFactura);
						bok.setMontoUsaRoundTripFactura(montousaRoundFactura);
					estimadoSegmento += montoEstimado;
						estimadoSegmentoUsd += montoEstimadoUsd;
						estimadoSegmentoFactura += montoEstimadoFactura;
						estimadoSegmentoUsdFactura += montoEstimadoUsdFactura;
					bok.setMercado(marketSeg);
						bok.setInternational(!(bok.getPaisDestino().equals(bok.getPaisOrigen()) && bok.getPaisOrigen().equals(countryCompany)));
						bok.setInternationalDomestic(bok.getInternational() ? "International" : "Domestic");
					}
				}

				if (!isConnection) {
					nivel++;
					books.addElement(nivel, new JPair(origen, destino));
					booksAreas.addElement(nivel, new JPair(areaorigen, areadestino));
					booksCT.addElement(nivel, new JPair(paisorigen, paisdestino));
					booksComb.addElement(nivel, new JPair(paisorigen, destino));
					booksCombA.addElement(nivel, new JPair(paisorigen, areadestino));
				} else {
					JPair pair = books.getElement(nivel);
					if (pair != null) {
						pair.setSecondObject(destino);
					}
					JPair pairA = booksAreas.getElement(nivel);
					if (pairA != null) {
						pairA.setSecondObject(areadestino);
					}
					JPair pairCT = booksCT.getElement(nivel);
					if (pairCT != null) {
						pairCT.setSecondObject(paisdestino);
					}
					JPair pairComb = booksComb.getElement(nivel);
					if (pairComb != null) {
						pairComb.setSecondObject(destino);
					}
					JPair pairCombA = booksCombA.getElement(nivel);
					if (pairCombA != null) {
						pairCombA.setSecondObject(areadestino);
					}
				}
				if (s.getObjCarrier()!=null && s.getObjCarrier().getObjLogica()!=null) { // la logic de clase depende del booking y no del segmento porpor lo que en algunos casos requiere un post ajuste
					if (s.getObjCarrier().getObjLogica().doCarrierLogica(s.getFareBasic(), bok, s)) {
						s.processCalculate();
						bok.processCalculate();
						s.processUpdate();
						clase = s.getClase();
						tipoclase = s.getTipoClase();
						bok.setClase(clase);
						bok.setTipoClase(tipoclase);
					}
					
				}

				if (s.getCarrier().equals(getCarrier())) {
					if (clase != null) {
						if (clasePrioridad > s.getPrioridad()) {
							clase = s.getClase();
							tipoclase = s.getTipoClase();
							clasePrioridad = s.getPrioridad();
						}
					} else {
						clase = s.getClase();
						tipoclase = s.getTipoClase();
						clasePrioridad = s.getPrioridad();
					}
				} else {
					if (clase == null) {
						clase = s.getClase();
						tipoclase = s.getTipoClase();
						clasePrioridad = 99999;

					} 
				}
				if (s.pFechaArrivo.isNotNull() && s.getHoraArrivo().length() > 3) {
					fechaArrivo = Calendar.getInstance();
					fechaArrivo.setTime(s.getFechaArrivo());
					int pos = s.getHoraArrivo().indexOf(":") == -1 ? 2 : s.getHoraArrivo().indexOf(":");
					int pos2 = s.getHoraArrivo().indexOf(":") == -1 ? 2 : s.getHoraArrivo().indexOf(":") + 1;
					fechaArrivo.set(Calendar.HOUR_OF_DAY, new Integer(s.getHoraArrivo().substring(0, pos)));
					fechaArrivo.set(Calendar.MINUTE, new Integer(s.getHoraArrivo().substring(pos2)));
					fechaFinViaje.setTime(fechaArrivo.getTime());
				} else
					fechaArrivo = null;

			}

			setEndTravelDate(fechaFinViaje.getTime());
			if (pInternacionalDescr.isNull() || pGDS.getValue().equals("TRAVELPORT") || pGDS.getValue().equals("GALILEO") || pGDS.getValue().equals("AMADEUS") || pCodigoaerolinea.getValue().equals("4O") || pCodigoaerolinea.getValue().equals("G3") || pCodigoaerolinea.getValue().equals("JJ")) {
				setInternacional(international);
				setInternacionalDescr(isInternacional() ? "International" : "Domestic");
			}

			JIterator<Long> it = books.getKeyIterator();
			while (it.hasMoreElements()) {
				Long key = it.nextElement();
				JPair p = books.getElement(key);
				JPair a = booksAreas.getElement(key);
				String toadd;
				if (bookings.indexOf("BOK-" + ((String) p.secondObject()).toUpperCase() + ((String) p.firstObject()).toUpperCase()) == -1) {
					bookings += (bookings.equals("") ? "" : ",") + "BOK-" + ((String) p.firstObject()).toUpperCase() + ((String) p.secondObject()).toUpperCase();
					toadd = "BOK-" + ((String) a.firstObject()).toUpperCase() + ((String) p.secondObject()).toUpperCase();
					if (!((String) a.firstObject()).equals("") && bookings.indexOf(toadd) == -1)
						bookings += (bookings.equals("") ? "" : ",") + toadd;
					toadd = "BOK-" + ((String) p.firstObject()).toUpperCase() + ((String) a.secondObject()).toUpperCase();
					if (!((String) a.secondObject()).equals("") && bookings.indexOf(toadd) == -1)
						bookings += (bookings.equals("") ? "" : ",") + toadd;
					toadd = "BOK-" + ((String) a.firstObject()).toUpperCase() + ((String) a.secondObject()).toUpperCase();
					if (!((String) a.firstObject()).equals("") && !((String) a.secondObject()).equals("") && bookings.indexOf(toadd) == -1)
						bookings += (bookings.equals("") ? "" : ",") + toadd;
				} else {
					bookings += (bookings.equals("") ? "" : ",") + "VOK-" + ((String) p.firstObject()).toUpperCase() + ((String) p.secondObject()).toUpperCase();
					toadd = (bookings.equals("") ? "" : ",") + "VOK-" + ((String) a.firstObject()).toUpperCase() + ((String) p.secondObject()).toUpperCase();
					if (!((String) a.firstObject()).equals("") && bookings.indexOf(toadd) == -1)
						bookings += (bookings.equals("") ? "" : ",") + toadd;
					toadd = (bookings.equals("") ? "" : ",") + "VOK-" + ((String) p.firstObject()).toUpperCase() + ((String) a.secondObject()).toUpperCase();
					if (!((String) a.secondObject()).equals("") && bookings.indexOf(toadd) == -1)
						bookings += (bookings.equals("") ? "" : ",") + toadd;
					toadd = (bookings.equals("") ? "" : ",") + "VOK-" + ((String) a.firstObject()).toUpperCase() + ((String) a.secondObject()).toUpperCase();
					if (!((String) a.firstObject()).equals("") && !((String) a.secondObject()).equals("") && bookings.indexOf(toadd) == -1)
						bookings += (bookings.equals("") ? "" : ",") + toadd;
				}
				cantSegmentos++;
			}

			JIterator<JPair> itCT = booksCT.getValueIterator();
			JIterator<JPair> itComb = booksComb.getValueIterator();
			JIterator<JPair> itCombA = booksCombA.getValueIterator();
			while (itCT.hasMoreElements()) {
				JPair p = itCT.nextElement();
				JPair p2 = itComb.nextElement();
				JPair p3 = itCombA.nextElement();
				if (p.firstObject() == null)
					continue;
				if (p.secondObject() == null)
					continue;
				
				if (bookings.indexOf("BOK-@" + ((String) p.secondObject()).toUpperCase() + "@" + ((String) p.firstObject()).toUpperCase()) == -1) {
					bookings += (bookings.equals("") ? "" : ",") + "BOK-@" + ((String) p.firstObject()).toUpperCase() + "@" + ((String) p.secondObject()).toUpperCase();
					bookings += (bookings.equals("") ? "" : ",") + "BOK-@" + ((String) p2.firstObject()).toUpperCase() + ((String) p2.secondObject()).toUpperCase();
					if (!((String) p3.firstObject()).equals("") && !((String) p3.secondObject()).equals(""))
						bookings += (bookings.equals("") ? "" : ",") + "BOK-@" + ((String) p3.firstObject()).toUpperCase() + ((String) p3.secondObject()).toUpperCase();
				} else {
					bookings += (bookings.equals("") ? "" : ",") + "VOK-@" + ((String) p.firstObject()).toUpperCase() + "@" + ((String) p.secondObject()).toUpperCase();
					bookings += (bookings.equals("") ? "" : ",") + "VOK-@" + ((String) p2.firstObject()).toUpperCase() + ((String) p2.secondObject()).toUpperCase();
					if (!((String) p3.firstObject()).equals("") && !((String) p3.secondObject()).equals(""))
						bookings += (bookings.equals("") ? "" : ",") + "VOK-@" + ((String) p3.firstObject()).toUpperCase() + ((String) p3.secondObject()).toUpperCase();
				}
			}

			booksCT.addElement(nivel, new JPair(paisorigen, paisdestino));
			booksComb.addElement(nivel, new JPair(paisorigen, destino));
			booksCombA.addElement(nivel, new JPair(paisorigen, areadestino));

			if (bok != null) {
				if (bok.pMonto.getValue() == 0) {
					bok.setMoneda(getCodigoMoneda());
					bok.setMonto(estimadoSegmento);
					bok.setMontoRoundTrip(estimadoSegmento * (getCantidadRoundTrip()));
					bok.setMontoFactura(estimadoSegmentoFactura);
					bok.setMontoRoundTripFactura(estimadoSegmentoFactura * (getCantidadRoundTrip()));
				}
				if (bok.pMontoOriginal.getValue() == 0) {
					bok.setMonedaBase("USD");
					bok.setMontoOriginal(estimadoSegmentoUsd);
					bok.setMontoUsaRoundTrip(estimadoSegmentoUsd * (getCantidadRoundTrip()));
					bok.setMontoOriginalFactura(estimadoSegmentoUsdFactura);
					bok.setMontoUsaRoundTripFactura(estimadoSegmentoUsdFactura * (getCantidadRoundTrip()));
				}
				bok.setReemitido(isReemited());
				if (!(isVoided()/* || isReemited()*/))
					bok.processUpdateOrInsertWithCheck();
				bok = null;
			}

			BizAirport airportOr = getObjAeropuertoOrigen();
			BizPaisLista paisOr = airportOr != null ? airportOr.getObjCountry() : null;
			BizAirport airportDst = getObjAeropuertoDestino();
			BizPaisLista paisDst = airportDst != null ? airportDst.getObjCountry() : null;

			
			setInterlineal(!pura);
			// countryCompany = getObjCompany().getCountry();
			// if (paisOr != null && paisDst != null) {
			// if (paisDst.GetPais().equals(countryCompany) &&
			// !paisOr.GetPais().equals(countryCompany)) {
			// setPaisDestino(paisOr.GetPais());
			// setPaisOrigen(paisDst.GetPais());
			// } else {
			if (paisDst!=null)
				setPaisDestino(paisDst.GetPais());
			if (paisOr!=null)
				setPaisOrigen(paisOr.GetPais());
			// }
			//
			// }

		}

	
		public void updateConexion(List<BizPNRSegmentoAereo> segmentosConexion, String origen, String destino) throws Exception {
			for (BizPNRSegmentoAereo cnx : segmentosConexion) {
				cnx.setDestino(destino);
				cnx.setOrigen(origen);
			}
		}

		public void armarRuta() throws Exception {
			iter = segmentos.getStaticIterator();
			ruta = "";
			rutaPais = "";
			lastSeg = null;
			Calendar fechaArrivo = null;
			Calendar fechaDespegue = null;
			List<BizPNRSegmentoAereo> segmentosConexion = new ArrayList<BizPNRSegmentoAereo>();
			String origen = null;
			String airInt = "";
			String fareInt = "";
			String paisInt = "";
			String genInt = "";
			String fecInt = "";
			String carInt = "";
			String clsInt = "";
			String vuelosInt = "";
			String marketInt = "";
			String areaMet = "";
			boolean hasDestino = false;
			while (iter.hasMoreElements()) {
				BizPNRSegmentoAereo seg = iter.nextElement();
				if (seg.pFechaDespegue.isNotNull() && seg.getHoraDespegue().length() > 3) {
					fechaDespegue = Calendar.getInstance();
					fechaDespegue.setTime(seg.getFechaDespegue());
					int pos = seg.getHoraDespegue().indexOf(":") == -1 ? 2 : seg.getHoraDespegue().indexOf(":");
					int pos2 = seg.getHoraDespegue().indexOf(":") == -1 ? 2 : seg.getHoraDespegue().indexOf(":") + 1;
					fechaDespegue.set(Calendar.HOUR_OF_DAY, new Integer(seg.getHoraDespegue().substring(0, pos)));
					fechaDespegue.set(Calendar.MINUTE, new Integer(seg.getHoraDespegue().substring(pos2)));
				} else
					fechaDespegue = null;
				areaMet =seg.getObjAeropuertoDespegue().getIataArea();
				airInt += (airInt.equals("") ? "" : "-") + seg.getDespegue();
				fareInt += (fareInt.indexOf(seg.getFareBasicExt())!=-1?"":(fareInt.equals("") ? "" : "-") + seg.getFareBasicExt());
				genInt += (genInt.equals("") ? "" : "-") + ( areaMet.equals("")?seg.getDespegue():areaMet );
				paisInt += (paisInt.equals("") ? "" : "-") + seg.getObjAeropuertoDespegue().getCountry();
				if (seg.getFechaDespegue() != null)
				fecInt += (fecInt.equals("") ? "" : "-") + JDateTools.dateToStr(seg.getFechaDespegue(), "ddMMM").toUpperCase();
				carInt += (carInt.equals("") ? "" : "-") + seg.getCarrier();
				clsInt += (clsInt.equals("") ? "" : "-") + seg.getClase();
				vuelosInt += (vuelosInt.equals("") ? "" : "-") + seg.getNumeroVuelo();
				marketInt += (marketInt.equals("") ? "" : "-") + seg.getObjAeropuertoDespegue().getObjCountry().GetRegion();

				boolean isConnection = (antCarrier == null || seg.getCarrier().equals(antCarrier)) && seg.getConnectionIndicator().equals(BizPNRSegmentoAereo.SEGMENTO_CONNECTION);
				boolean isStop = seg.getConnectionIndicator().equals(BizPNRSegmentoAereo.SEGMENTO_STOP)||(lastSeg!=null&&segmentoMaximo != null && lastSeg.getCodigoSegmento() == segmentoMaximo.getCodigoSegmento());
				if (isConnection && fechaArrivo != null && fechaDespegue != null) {
					int days = Math.abs(fechaArrivo.fieldDifference(fechaDespegue.getTime(), Calendar.DAY_OF_MONTH));
					if (days > 1) {

						isStop = true;
					}
				}

				if (hayInfoConexiones) {
					if (lastSeg != null) {
						if (isStop) {
							updateConexion(segmentosConexion, origen, lastSeg.getArrivo());
							segmentosConexion = new ArrayList<BizPNRSegmentoAereo>();
							origen = seg.getDespegue();
						} else if (!isConnection) {
							if (origen == null)
								origen = seg.getDespegue();
						}

					} else {
						if (origen == null)
							origen = seg.getDespegue();
					}
					segmentosConexion.add(seg);
				} else {
					origen = seg.getDespegue();
					seg.setOrigen(seg.getDespegue());
					seg.setDestino(seg.getArrivo());
				}
				if (hayInfoConexiones && isStop) {
					if (antAep != null)
						ruta += antAep + "|";
					if (antAepPais != null)
						rutaPais += antAepPais + "|";

					if (lastSeg != null && !hasDestino && (segmentoMaximo != null && lastSeg.getCodigoSegmento() >= segmentoMaximo.getCodigoSegmento())) {
						if (roundTrip) {
							lastSeg.setTipoSegmento(BizPNRSegmentoAereo.SEGMENTO_DESTINO);
							hasDestino = true;
							yendo = false;
							antAep = null;
							antCarrier = null;
							antAepPais = null;
						}
					}
				}
				if (seg.pFechaArrivo.isNotNull() && seg.getHoraArrivo().length() > 3) {
					fechaArrivo = Calendar.getInstance();
					fechaArrivo.setTime(seg.getFechaArrivo());
					int pos = seg.getHoraArrivo().indexOf(":") == -1 ? 2 : seg.getHoraArrivo().indexOf(":");
					int pos2 = seg.getHoraArrivo().indexOf(":") == -1 ? 2 : seg.getHoraArrivo().indexOf(":") + 1;
					fechaArrivo.set(Calendar.HOUR_OF_DAY, new Integer(seg.getHoraArrivo().substring(0, pos)));
					fechaArrivo.set(Calendar.MINUTE, new Integer(seg.getHoraArrivo().substring(pos2)));
				} else
					fechaArrivo = null;

				if (antAep != null && antAep.equals(seg.getDespegue()))
					ruta += (isConnection ? antAep.toLowerCase() : antAep) + "(" + seg.getCarrier() + ")";
				else
					ruta += (ruta.equals("") || ruta.endsWith("|") ? "" : "|") + seg.getDespegue() + "(" + seg.getCarrier() + ")";

				if (antAep != null && antAep.equals(seg.getDespegue()))
					rutaPais += (isConnection ? antAepPais.toLowerCase() : antAepPais) + "(" + seg.getCarrier() + ")";
				else
					rutaPais += (rutaPais.equals("") || rutaPais.endsWith("|") ? "" : "|") + "@" + seg.getObjAeropuertoDespegue().getCountry() + "(" + seg.getCarrier() + ")";

				if (!hayInfoConexiones && (segmentoMaximo != null && seg.getCodigoSegmento() == segmentoMaximo.getCodigoSegmento())) {
					ruta += seg.getArrivo() + "|";
					rutaPais += "@" + seg.getObjAeropuertoArrivo().getCountry() + "|";
					if ((segmentoMaximo != null && seg.getCodigoSegmento() == segmentoMaximo.getCodigoSegmento())) {
						seg.setTipoSegmento(BizPNRSegmentoAereo.SEGMENTO_DESTINO);
						hasDestino = true;
						yendo = false;
					}
					antAep = null;
					antCarrier = null;
					antAepPais = null;
					cantSegmentoIR++;
					lastSeg = seg;
					continue;
				}
				seg.setTipoSegmento(yendo ? BizPNRSegmentoAereo.SEGMENTO_IDA : BizPNRSegmentoAereo.SEGMENTO_RETORNO);
				antAep = seg.getArrivo();
				antCarrier = seg.getCarrier();
				antAepPais = "@" + seg.getObjAeropuertoArrivo().getCountry();
				if (yendo)
					cantSegmentoIR++;
				lastSeg = seg;

			}

			if (hayInfoConexiones) {
				updateConexion(segmentosConexion, origen, lastSeg.getArrivo());
			}

			if (antAep != null) {
				ruta += antAep;
				rutaPais += antAepPais;
			}

			if (yendo && !lastSeg.getTipoSegmento().equals(BizPNRSegmentoAereo.SEGMENTO_IDA))
				System.out.println("error");

			if (lastSeg.getTipoSegmento().equals(BizPNRSegmentoAereo.SEGMENTO_IDA))
				lastSeg.setTipoSegmento(BizPNRSegmentoAereo.SEGMENTO_DESTINO);
			areaMet =lastSeg.getObjAeropuertoArrivo().getIataArea();
			airInt += (airInt.equals("") ? "" : "-") + lastSeg.getArrivo();
			fareInt += (fareInt.indexOf(lastSeg.getFareBasicExt())!=-1?"":(fareInt.equals("") ? "" : "-") + lastSeg.getFareBasicExt());
			genInt += (genInt.equals("") ? "" : "-") + ( areaMet.equals("")?lastSeg.getDespegue():areaMet );
			paisInt += (paisInt.equals("") ? "" : "-") + lastSeg.getObjAeropuertoArrivo().getCountry();
			if (lastSeg.getFechaDespegue() != null)
				fecInt += (fecInt.equals("") ? "" : "-") + JDateTools.dateToStr(lastSeg.getFechaDespegue(), "ddMMM").toUpperCase();
			carInt += (carInt.equals("") ? "" : "-") + lastSeg.getCarrier();
			clsInt += (clsInt.equals("") ? "" : "-") + lastSeg.getClase();
			vuelosInt += (vuelosInt.equals("") ? "" : "-") + lastSeg.getNumeroVuelo();
			marketInt += (marketInt.equals("") ? "" : "-") + lastSeg.getObjAeropuertoDespegue().getObjCountry().GetRegion();
			marketInt += (marketInt.equals("") ? "" : "-") + lastSeg.getObjAeropuertoArrivo().getObjCountry().GetRegion();

			setAirIntinerary(airInt);
			setFareIntinerary(fareInt);
			setAirPaisIntinerary(paisInt);
			setAirGenIntinerary(genInt);
			setCarrierIntinerary(carInt);
			setClassIntinerary(clsInt);
			setFechaIntinerary(fecInt);
			setNroFlIntinerary(vuelosInt);
			setMarketIntinerary(marketInt);
		}
		public void armarRutaGalileo() throws Exception {
			iter = segmentos.getStaticIterator();
			ruta = "";
			rutaPais = "";
			lastSeg = null;
			Calendar fechaArrivo = null;
			Calendar fechaDespegue = null;
			List<BizPNRSegmentoAereo> segmentosConexion = new ArrayList<BizPNRSegmentoAereo>();
			String origen = null;
			String airInt = "";
			String fareInt = "";
			String paisInt = "";
			String genInt = "";
			String fecInt = "";
			String carInt = "";
			String clsInt = "";
			String areaMet= "";
			String vuelosInt = "";
			String marketInt = "";
			boolean hasDestino = false;
			boolean wasConnection=false;
			while (iter.hasMoreElements()) {
				BizPNRSegmentoAereo seg = iter.nextElement();
				if (seg.pFechaDespegue.isNotNull() && seg.getHoraDespegue().length() > 3) {
					fechaDespegue = Calendar.getInstance();
					fechaDespegue.setTime(seg.getFechaDespegue());
					int pos = seg.getHoraDespegue().indexOf(":") == -1 ? 2 : seg.getHoraDespegue().indexOf(":");
					int pos2 = seg.getHoraDespegue().indexOf(":") == -1 ? 2 : seg.getHoraDespegue().indexOf(":") + 1;
					fechaDespegue.set(Calendar.HOUR_OF_DAY, new Integer(seg.getHoraDespegue().substring(0, pos)));
					fechaDespegue.set(Calendar.MINUTE, new Integer(seg.getHoraDespegue().substring(pos2)));
				} else
					fechaDespegue = null;
				
				if (seg.pFechaArrivo.isNotNull() && seg.getHoraArrivo().length() > 3) {
					fechaArrivo = Calendar.getInstance();
					fechaArrivo.setTime(seg.getFechaArrivo());
					int pos = seg.getHoraArrivo().indexOf(":") == -1 ? 2 : seg.getHoraArrivo().indexOf(":");
					int pos2 = seg.getHoraArrivo().indexOf(":") == -1 ? 2 : seg.getHoraArrivo().indexOf(":") + 1;
					fechaArrivo.set(Calendar.HOUR_OF_DAY, new Integer(seg.getHoraArrivo().substring(0, pos)));
					fechaArrivo.set(Calendar.MINUTE, new Integer(seg.getHoraArrivo().substring(pos2)));
				} else
					fechaArrivo = null;
				aep = seg.getDespegue();
				if ( seg.getObjAeropuertoDespegue()==null) return;
				if ( seg.getObjAeropuertoArrivo()==null) return;
				aepPais = "@" + seg.getObjAeropuertoDespegue().getCountry();
		
				antAep = seg.getArrivo();
				antCarrier = seg.getCarrier();
				antAepPais = "@" + seg.getObjAeropuertoArrivo().getCountry();
				areaMet =seg.getObjAeropuertoDespegue().getIataArea();

				airInt += (airInt.equals("") ? "" : "-") + seg.getDespegue();
				fareInt += (fareInt.indexOf(seg.getFareBasicExt())!=-1?"":(fareInt.equals("") ? "" : "-") + seg.getFareBasicExt());
				genInt += (genInt.equals("") ? "" : "-") + ( areaMet.equals("")?seg.getDespegue():areaMet );
				paisInt += (paisInt.equals("") ? "" : "-") + seg.getObjAeropuertoDespegue().getCountry();
				if (seg.getFechaDespegue() != null)
					fecInt += (fecInt.equals("") ? "" : "-") + JDateTools.dateToStr(seg.getFechaDespegue(), "ddMMM").toUpperCase();
				carInt += (carInt.equals("") ? "" : "-") + seg.getCarrier();
				clsInt += (clsInt.equals("") ? "" : "-") + seg.getClase();
				vuelosInt += (vuelosInt.equals("") ? "" : "-") + seg.getNumeroVuelo();
				marketInt += (marketInt.equals("") ? "" : "-") + seg.getObjAeropuertoDespegue().getObjCountry().GetRegion();
				seg.setOrigen(seg.getDespegue());
				seg.setDestino(seg.getArrivo());

				boolean isConnection = (antCarrier == null || seg.getCarrier().equals(antCarrier)) && seg.getConnectionIndicator().equals(BizPNRSegmentoAereo.SEGMENTO_CONNECTION);
				boolean isStop = seg.getConnectionIndicator().equals(BizPNRSegmentoAereo.SEGMENTO_STOP);
				boolean isMaximo= (seg!=null&&segmentoMaximo != null && seg.getCodigoSegmento() == segmentoMaximo.getCodigoSegmento());
//				if (isConnection && fechaArrivo != null && fechaDespegue != null) {
//					int days = Math.abs(fechaArrivo.fieldDifference(fechaDespegue.getTime(), Calendar.DAY_OF_MONTH));
//					if (days > 1) {
//
//						isStop = true;
//					}
//				}

					if (segmentosConexion!=null) {
						if (isStop) {
							updateConexion(segmentosConexion, origen, seg.getArrivo());
							origen = null;
							segmentosConexion=null;
						} 

					} else {
						segmentosConexion = new ArrayList<BizPNRSegmentoAereo>();
						segmentosConexion.add(seg);
						origen = seg.getDespegue();
					}

			
				
					if (!hasDestino && isMaximo) {
						if (roundTrip) {
							seg.setTipoSegmento(BizPNRSegmentoAereo.SEGMENTO_DESTINO);
							hasDestino = true;
							yendo = false;
						}
					} else {
						seg.setTipoSegmento(yendo ? BizPNRSegmentoAereo.SEGMENTO_IDA : BizPNRSegmentoAereo.SEGMENTO_RETORNO);
						
					}
				

				ruta += (ruta.equals("") || ruta.endsWith("|") ? "" : "|") + (wasConnection ?  aep.toLowerCase() : aep) + "(" + seg.getCarrier() + ")" +(isConnection ?  antAep.toLowerCase() : antAep); 
				rutaPais += (rutaPais.equals("") || rutaPais.endsWith("|") ? "" : "|") +(wasConnection ?  aepPais.toLowerCase() : aepPais) + "(" + seg.getCarrier() + ")"  + (isConnection ?  antAepPais.toLowerCase() : antAepPais); 

				if (yendo)
					cantSegmentoIR++;
				lastSeg = seg;
				
				wasConnection=isConnection;

			}
			areaMet =lastSeg.getObjAeropuertoArrivo().getIataArea();
			airInt += (airInt.equals("") ? "" : "-") + lastSeg.getArrivo();
			fareInt += (fareInt.indexOf(lastSeg.getFareBasicExt())!=-1?"":(fareInt.equals("") ? "" : "-") + lastSeg.getFareBasicExt());
			genInt += (genInt.equals("") ? "" : "-") + ( areaMet.equals("")?lastSeg.getDespegue():areaMet );
			paisInt += (paisInt.equals("") ? "" : "-") + lastSeg.getObjAeropuertoArrivo().getCountry();
			vuelosInt += (vuelosInt.equals("") ? "" : "-") + lastSeg.getNumeroVuelo();
			marketInt += (marketInt.equals("") ? "" : "-") + lastSeg.getObjAeropuertoDespegue().getObjCountry().GetRegion();
			marketInt += (marketInt.equals("") ? "" : "-") + lastSeg.getObjAeropuertoArrivo().getObjCountry().GetRegion();

//			updateConexion(segmentosConexion, origen, seg.getArrivo());
//			
//			if (antAep != null) {
//				ruta += antAep;
//				rutaPais += antAepPais;
//			}

//			if (yendo && !lastSeg.getTipoSegmento().equals(BizPNRSegmentoAereo.SEGMENTO_IDA))
//				System.out.println("error");
//
			if (!hasDestino)
				lastSeg.setTipoSegmento(BizPNRSegmentoAereo.SEGMENTO_DESTINO);
//			airInt += (airInt.equals("") ? "" : "-") + lastSeg.getArrivo();
//			if (lastSeg.getFechaDespegue() != null)
//				fecInt += (fecInt.equals("") ? "" : "-") + JDateTools.dateToStr(lastSeg.getFechaDespegue(), "ddMMM").toUpperCase();
//			carInt += (carInt.equals("") ? "" : "-") + lastSeg.getCarrier();
//			clsInt += (clsInt.equals("") ? "" : "-") + lastSeg.getClase();

			setAirIntinerary(airInt);
			setFareIntinerary(fareInt);
			setAirGenIntinerary(genInt);
			setAirIntinerary(airInt);
			setCarrierIntinerary(carInt);
			setClassIntinerary(clsInt);
			setFechaIntinerary(fecInt);
			setNroFlIntinerary(vuelosInt);
			setMarketIntinerary(marketInt);
		}
		public boolean addPoint(JMap<String,String> map, String point,String lastValue) throws Exception {
		   if (lastValue!=null &&point.equals(lastValue)) {
	        return false; // No se agrega si es igual al último
	    }
			String p = map.getElement(point);
			if (p==null){
				map.addElement(point, point);
				return false;
			}
			return map.size()>1;
		} 
		public void calculeMaxSegmento() throws Exception {
			String paisorigen = "", paisdestino = "";
			JMap<String,String> map = JCollectionFactory.createMap();
			JMap<String,String> mapP = JCollectionFactory.createMap();
			BizAirport objAirportIni=null;
			BizAirport objAirportFin=null;
			iter = segmentos.getStaticIterator();
			boolean loop=false;
			boolean loopP=false;
			String lastValueP=null;
			while (iter.hasMoreElements()) {
				BizPNRSegmentoAereo seg = iter.nextElement();
				boolean isConnection = seg.getConnectionIndicator().equals(BizPNRSegmentoAereo.SEGMENTO_CONNECTION);
				boolean esCorte=(ant != null && !isEqualAirport(objAirportIni,objAirportFin) &&ant.getFechaArrivo() != null && seg.getFechaDespegue() != null && !isConnection);
				if (airportInicio == null) {
					airportInicio = seg.getDespegue();
					objAirportIni = seg.getObjAeropuertoDespegue();
					loop|=addPoint(map,objAirportIni.getIataArea().equals("")?airportInicio:objAirportIni.getIataArea(),null);
					paisorigen = objAirportIni.getObjCountry() == null ? "" : objAirportIni.getObjCountry().GetPais();
					loopP|=addPoint(mapP,paisorigen,lastValueP);lastValueP=paisorigen;
				}
				airportFin = seg.getArrivo();
				objAirportFin = seg.getObjAeropuertoArrivo();
				loop|=addPoint(map,objAirportFin.getIataArea().equals("")?airportFin:objAirportFin.getIataArea(),null);
				paisdestino = objAirportFin.getObjCountry() == null ? "" : objAirportFin.getObjCountry().GetPais();
				loopP|=addPoint(mapP,paisdestino,lastValueP);lastValueP=paisdestino;

				if (seg.getConnectionIndicator().equals(""))
					hayInfoConexiones = false;

				if (esCorte) {
					Calendar arrivo = Calendar.getInstance();
					arrivo.setTime(ant.getFechaArrivo());
					if (ant.getHoraArrivo().trim().equals(""))
						continue;
					int pos = ant.getHoraArrivo().indexOf(":") == -1 ? 2 : ant.getHoraArrivo().indexOf(":");
					int pos2 = ant.getHoraArrivo().indexOf(":") == -1 ? 2 : ant.getHoraArrivo().indexOf(":") + 1;
					arrivo.set(Calendar.HOUR_OF_DAY, new Integer(ant.getHoraArrivo().substring(0, pos)));
					arrivo.set(Calendar.MINUTE, new Integer(ant.getHoraArrivo().substring(pos2)));
					Calendar despegue = Calendar.getInstance();
					despegue.setTime(seg.getFechaDespegue());
					if (seg.getHoraDespegue().trim().equals(""))
						continue;
					pos = ant.getHoraDespegue().indexOf(":") == -1 ? 2 : ant.getHoraDespegue().indexOf(":");
					pos2 = ant.getHoraDespegue().indexOf(":") == -1 ? 2 : ant.getHoraDespegue().indexOf(":") + 1;
					despegue.set(Calendar.HOUR_OF_DAY, new Integer(seg.getHoraDespegue().substring(0, pos)));
					despegue.set(Calendar.MINUTE, new Integer(seg.getHoraDespegue().substring(pos2)));
					long tiempo = despegue.getTime().getTime() - arrivo.getTime().getTime();
					if (maxTiempo == 0 || tiempo > maxTiempo*1.5) { // a la primer parada hay que ganarle
						maxTiempo = tiempo;

						segmentoMaximo = ant;
						paradaLarga = maxTiempo > 1000 * 60 * 60 * 24;// mas de un dia
					}

				} 
				if (ant==null)
					segmentoMaximo = seg;
				
				if (!isEqualAirport(objAirportIni,objAirportFin))
					ant = seg;

			}
			
			if (loop||loopP)
				roundTrip = true;
			// if (!paisorigen.equals(paisdestino)) {
			// segmentoMaximo=null;
			// paradaLarga=false;
			// }
		}
		public void calculeMaxSegmentoGalileo() throws Exception {
			String paisorigen = "", paisdestino = "";
			JMap<String,String> map = JCollectionFactory.createMap();
			JMap<String,String> mapP = JCollectionFactory.createMap();
			BizAirport objAirportIni=null;
			BizAirport objAirportFin=null;
			iter = segmentos.getStaticIterator();
			String myAirportInicio = null;
			ant=null;
			maxTiempo=0;
			boolean loop=false;
			boolean loopP=false;
			boolean fueCorte=false;;
			String last=null;
			while (iter.hasMoreElements()) {
				BizPNRSegmentoAereo seg = iter.nextElement();
				boolean isConnection = seg.getConnectionIndicator().equals(BizPNRSegmentoAereo.SEGMENTO_CONNECTION);
				boolean esCorte=(ant != null && !isEqualAirport(objAirportIni,objAirportFin) &&ant.getFechaArrivo() != null && seg.getFechaDespegue() != null && !isConnection);
				if (myAirportInicio == null) {
					myAirportInicio = seg.getDespegue();
					airportInicio =myAirportInicio;
					objAirportIni = seg.getObjAeropuertoDespegue();
					if (objAirportIni==null) return;
					loop|=addPoint(map,objAirportIni.getIataArea().equals("")?myAirportInicio:objAirportIni.getIataArea(),null);
					paisorigen = objAirportIni.getObjCountry() == null ? "" : objAirportIni.getObjCountry().GetPais();
					loopP|=addPoint(mapP,paisorigen,last);last=paisorigen;
				}
				airportFin = seg.getArrivo();
				objAirportFin = seg.getObjAeropuertoArrivo();
				if (objAirportFin==null) return;
				loop|=addPoint(map,objAirportFin.getIataArea().equals("")?airportFin:objAirportFin.getIataArea(),null);
				paisdestino = objAirportFin.getObjCountry() == null ? "" : objAirportFin.getObjCountry().GetPais();
				loopP|=addPoint(mapP,paisdestino,last);last=paisdestino;


				if (fueCorte) {
					Calendar arrivo = Calendar.getInstance();
					arrivo.setTime(ant.getFechaArrivo());
					if (ant.getHoraArrivo().trim().equals(""))
						continue;
					int pos = ant.getHoraArrivo().indexOf(":") == -1 ? 2 : ant.getHoraArrivo().indexOf(":");
					int pos2 = ant.getHoraArrivo().indexOf(":") == -1 ? 2 : ant.getHoraArrivo().indexOf(":") + 1;
					arrivo.set(Calendar.HOUR_OF_DAY, new Integer(ant.getHoraArrivo().substring(0, pos)));
					arrivo.set(Calendar.MINUTE, new Integer(ant.getHoraArrivo().substring(pos2)));
					Calendar despegue = Calendar.getInstance();
					despegue.setTime(seg.getFechaDespegue());
					if (seg.getHoraDespegue().trim().equals(""))
						continue;
					pos = ant.getHoraDespegue().indexOf(":") == -1 ? 2 : ant.getHoraDespegue().indexOf(":");
					pos2 = ant.getHoraDespegue().indexOf(":") == -1 ? 2 : ant.getHoraDespegue().indexOf(":") + 1;
					despegue.set(Calendar.HOUR_OF_DAY, new Integer(seg.getHoraDespegue().substring(0, pos)));
					try {
						despegue.set(Calendar.MINUTE, new Integer(seg.getHoraDespegue().substring(pos2)));
					} catch (NumberFormatException e) {
						despegue.set(Calendar.MINUTE, 0);
					} catch (Exception e) {
						e.printStackTrace();
					}
					long tiempo = despegue.getTime().getTime() - arrivo.getTime().getTime();
					if (maxTiempo == 0 || tiempo > maxTiempo) {
						maxTiempo = tiempo;

						segmentoMaximo = ant;
						paradaLarga = maxTiempo > 1000 * 60 * 60 * 24;// mas de un dia
					}

				} 
				fueCorte=esCorte;
				if (ant==null)
					segmentoMaximo = seg;

				if (seg.getConnectionIndicator().equals(BizPNRSegmentoAereo.SEGMENTO_STOP))
					myAirportInicio = null;

				if (!isEqualAirport(objAirportIni,objAirportFin))
					ant = seg;

			}
			if (segmentoMaximo==null)
				segmentoMaximo=ant;
			
			if (loop||loopP)
				roundTrip = true;
			// if (!paisorigen.equals(paisdestino)) {
			// segmentoMaximo=null;
			// paradaLarga=false;
			// }
		}
		boolean isEqualAirport(BizAirport ini,BizAirport fin) throws Exception {
			if (ini.getCode().equals(fin.getCode())) return true;
			if (!ini.getIataArea().equals("") && ini.getIataArea().equals(fin.getIataArea())) return true;
			return false;		
		}
		public boolean fillOneMercadoRuta(String subpatron, String tramoRuta, String tramoRutaPais) throws Exception {
			boolean perteneceMercado = false;
			int pos = subpatron.indexOf("-");
			int pos2 = subpatron.indexOf("/");
			if (pos == -1 && pos2 == -1) { // solo aeropuerto
				if (tramoRuta.indexOf(subpatron) != -1) {
					perteneceMercado = true;
					return perteneceMercado;
				}

			} else { // grupo de aeropuertos
				pos = 0;
				int posAux = 0;
				int posAux2 = 0;
				int nodo = 0;
				boolean cumple = true;
				int despl2 = 0;
				StringTokenizer tokDs = new StringTokenizer(subpatron, "-");
				while (tokDs.hasMoreTokens()) {
					String airport = tokDs.nextToken();
					boolean find = false;
					nodo++;
					int nodo2 = 0;
					if (posAux == -1) {
						cumple = false;
						break;
					}
					if (tramoRuta.length() < pos)
						break;
					pos = posAux;
					despl2 = pos;
					StringTokenizer tokNs = new StringTokenizer(tramoRuta.substring(pos), "()");
					while (tokNs.hasMoreTokens() && !find) {
						String airportNodo = tokNs.nextToken();
						if (airportNodo.length() == 2) {
							despl2 += airportNodo.length() + 2;
							continue;
						}
						nodo2++;

						String airportNodoPais = "";
						if (tramoRutaPais.length() >= despl2 + airportNodo.length())
							airportNodoPais = tramoRutaPais.substring(despl2, despl2 + airportNodo.length());
						// EZE(AA)LHR| EZE(AA)GRU(AA)LHR|
						if ((airport.startsWith("|[") || (airport.startsWith("["))) && (airport.endsWith("]") || airport.endsWith("]|"))) {
							StringTokenizer tokDOps = new StringTokenizer(airport.substring(1, airport.length() - 1), ";[]");
							boolean findOp = false;
							boolean first = true;
							while (tokDOps.hasMoreTokens() && !find) {
								String airportD = tokDOps.nextToken();
								if (airport.endsWith("|"))
									// if (!tokDOps.hasMoreTokens() &&
									// airport.endsWith("|"))
									airportD += "|";
								if (first && airport.startsWith("|"))
									// if (airport.startsWith("|"))
									airportD = "|" + airportD;
								first = false;
								if (airportD.startsWith("@")) {
									posAux2 = airportNodoPais.indexOf(airportD);
									if (posAux2 != -1) {
										find = true;
									}
								} else {
									posAux2 = airportNodo.indexOf(airportD);
									if (posAux2 != -1) {
										findOp = true;
										break;
									}
								}
							}
							if (findOp) {
								find = true;
							}
						} else if (airport.startsWith("*")) {

							posAux2 += 3;
							find = true;
						} else if (airport.startsWith("@")) {
							posAux2 = airportNodoPais.indexOf(airport);
							if (posAux2 != -1) {
								find = true;
							}
						} else {
							posAux2 = airportNodo.indexOf(airport);
							if (posAux2 != -1) {
								find = true;
							}
						}
						if (find)
							posAux = tramoRuta.indexOf(")", despl2 + posAux2) == -1 ? tramoRuta.indexOf("|", despl2 + posAux2) : tramoRuta.indexOf(")", despl2 + posAux2) + 1;

						despl2 += airportNodo.length();
					}
					if (!find)
						cumple = false;
				}

				if (cumple) {
					PssLogger.logDebug("Patron" + subpatron + " match " + tramoRuta + " " + tramoRutaPais);
					perteneceMercado = true;
					return perteneceMercado;
				}
			}
			return perteneceMercado;
		}

		public boolean fillOneMercado(String patron, String ruta, String rutaPais) throws Exception {
			boolean perteneceMercado = false;
			StringTokenizer toks = new StringTokenizer(patron, ",");
			while (toks.hasMoreTokens() && !perteneceMercado) {
				String tok = toks.nextToken();

				int tramo = 0;
				StringTokenizer tokRutas = new StringTokenizer(ruta, "|");
				while (tokRutas.hasMoreTokens() && !perteneceMercado) {
					String tramoRuta = tokRutas.nextToken();
					String tramoRutaPais = null;
					int tramoPais = 0;
					StringTokenizer tokRutaPais = new StringTokenizer(rutaPais, "|");
					while (tokRutaPais.hasMoreTokens()) {
						tramoRutaPais = tokRutaPais.nextToken();
						if (tramo == tramoPais) {
							break;
						}
						tramoPais++;
					}

					tramo++;
					tramoRuta = "|" + tramoRuta + "|";
					tramoRutaPais = "|" + tramoRutaPais + "|";
					perteneceMercado = fillOneMercadoRuta(tok, tramoRuta, tramoRutaPais);
				}

			}
			return perteneceMercado;
		}

		public String fillMercado(String ruta, String rutaPais, String bookings, String segments, String miniroute) throws Exception {

			PssLogger.logDebug(getCodigopnr());
			if (getCodigopnr().equals("UCYXNK"))
				PssLogger.logDebug("debug");
			boolean perteneceMercado = false;
			String market = "";
			JIterator<BizMarket> it = PNRCache.getMercadosCache(getCompany()).getIterator();
			while (it.hasMoreElements()) { // mercados
				BizMarket mkt = it.nextElement();
				JIterator<BizMarketDetail> itD =	PNRCache.getMercadosDetailCache(mkt.getId()).getIterator();
				while (itD.hasMoreElements()) {// detalles de mercado
					BizMarketDetail mktD = itD.nextElement();

					perteneceMercado = fillOneMercado(JTools.replace(mktD.getRuta(), "\n", ""), ruta, rutaPais);
					// ruta

					if (perteneceMercado) {
						market += (market.equals("") ? "" : ",") + mkt.getDescripcion();
						break;
					}
				}

				perteneceMercado = false;

			}
			if (!bookings.equals(""))
				market += (market.equals("") ? "" : ",") + bookings;
			if (!segments.equals(""))
				market += (market.equals("") ? "" : ",") + segments;
			if (!miniroute.equals(""))
				market += (market.equals("") ? "" : ",") + "TKM-" + JTools.replace(miniroute, "/", "");

			return market;
		}

		public boolean detectVueltaAlMundo() throws Exception {
			JMap<String, String> mapContinentes = JCollectionFactory.createMap(); // Almacena continentes únicos
			String primerAeropuerto = null;
			String ultimoAeropuerto = null;

			// Iterar sobre los segmentos
			iter = segmentos.getStaticIterator();
			while (iter.hasMoreElements()) {
				BizPNRSegmentoAereo seg = iter.nextElement();

				// Obtener continentes de origen y destino
				String continenteOrigen = seg.getObjAeropuertoDespegue().getObjCountry().GetContinente();
				String continenteDestino = seg.getObjAeropuertoArrivo().getObjCountry().GetContinente();

				// Agregar al mapa para verificar continentes únicos
				mapContinentes.addElement(continenteOrigen, continenteOrigen);
				mapContinentes.addElement(continenteDestino, continenteDestino);

				// Capturar primer y último aeropuerto
				if (primerAeropuerto == null) {
					primerAeropuerto = seg.getDespegue();
				}
				ultimoAeropuerto = seg.getArrivo();
			}

			// Comprobar si regresó al mismo aeropuerto
			boolean regresoAlInicio = primerAeropuerto != null && primerAeropuerto.equals(ultimoAeropuerto);

			// Comprobar si se cruzaron al menos dos continentes
			boolean cruzoContinentes = mapContinentes.size() > 2;

			// Resultado: vuelta al mundo
			return regresoAlInicio && cruzoContinentes;
		}

	}
	//FARETYPE_A A privada sin acct code
	//FARETYPE_B B privada via cad 35
	//FARETYPE_C C privada c acct code
	//FARETYPE_U U no hay identif pero es tarifa negociada
	//FARETYPE_SPACE Blanco → Tarifa Pública
	
	static JMap<String, String> objTipoTarifas;
	public static JMap<String, String> getTipoTarifas() throws Exception {
		if (objTipoTarifas!=null) return objTipoTarifas;
		JMap<String, String> map = JCollectionFactory.createMap();
		map.addElement("FARETYPE_A", "A privada sin acct code" );
		map.addElement("FARETYPE_B", "B privada via cad 35" );
		map.addElement("FARETYPE_C", "C privada c acct code" );
		map.addElement("FARETYPE_U", "U tarifa negociada" );
		map.addElement("FARETYPE_SPACE", "Tarifa Pública" );
		return objTipoTarifas= map;
	}
	
	public void execCalculeOver(String company,Date fechaDesde, Date fechaHasta) throws Exception {
		JExec oExec = new JExec(null, "procCalculeOver") {

			@Override
			public void Do() throws Exception {
				procCalculeOver(company,fechaDesde,fechaHasta);
			}
		};
		oExec.execute();
	}
	
	public void execCalculeOne(String company) throws Exception {
		JExec oExec = new JExec(null, "procCalculeOver") {

			@Override
			public void Do() throws Exception {
				calculeOver();
			}
		};
		oExec.execute();
	}
	public void execFillDetalle(BizDetalle detalle) throws Exception {
		JExec oExec = new JExec(null, "procCalculeOver") {

			@Override
			public void Do() throws Exception {
				fillDetalle(detalle);
			}
		};
		oExec.execute();
	}
	public void procCalculeOverDetalle( BizDetalle det,Date fechaDesde, Date fechaHasta , Date fechaCalc) throws Exception { 
		String sql = "UPDATE public.tur_pnr_boleto ";
		sql+=" SET upfront_ref = null , importeover = 0, porcentajeover=0, porcentaje_prorr_over=0,importe_prorr_over=0 ";
		sql+=" WHERE upfront_ref = "+det.getLinea() +"  and company='"+det.getCompany()+"'";
		
		JBaseRegistro reg;
		reg = JBaseRegistro.recordsetFactory();
		reg.Execute(sql);
		reg.close();
		
		if (BizRenderOver.hasFullRender(det)) {
			fillDetalle(det.getObjContrato().getFechaDesde(),det.getObjContrato().getFechaHasta(), det,fechaCalc);
			fillOthers(det.getObjContrato().getFechaDesde(),det.getObjContrato().getFechaHasta(), det,fechaCalc);
			
		}else {
			
			fillDetalle(fechaDesde,fechaHasta, det,fechaCalc);
			fillOthers(fechaDesde,fechaHasta, det,fechaCalc);
		}
		BizRenderOver.updateRender(det);	
	}

	public void procCalculeOver( String company,Date fechaDesde, Date fechaHasta ) throws Exception { 
		boolean calculeFechas = fechaDesde==null;
		
		if (calculeFechas) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			cal.add(Calendar.DAY_OF_MONTH, -10);
			fechaDesde=cal.getTime();
			fechaHasta=new Date();
		}
		Date fechaCalc = new Date();
		JRecords<BizDetalle> recs = new JRecords<BizDetalle>(BizDetalle.class);
		recs.addJoin(JRelations.JOIN, "bsp_contrato", "bsp_contrato_detalle.company = bsp_contrato.company and bsp_contrato_detalle.id=bsp_contrato.id");
		recs.addFilter("company", company);
		//recs.addFilter("id_aerolinea", "AM");
		recs.addFilter("bsp_contrato", "tipo_contrato", "C", "=");
		recs.addFilter("bsp_contrato", "fecha_hasta", JDateTools.getDateStartDay(fechaDesde), "JDATE", ">=", "AND", "(");
		recs.addFilter("bsp_contrato", "fecha_desde", JDateTools.getDateEndDay(fechaHasta), "JDATE", "<=", "OR", ")");
		recs.addOrderBy("bsp_contrato","id","asc");
		recs.addOrderBy("prioridad","desc");//como se van pisando el de mas prioridad es el mas chico
		int row = 0;
		JIterator<BizDetalle> it = recs.getStaticIterator();
		while (it.hasMoreElements()) {
			BizDetalle det = it.nextElement();
//			ILogicaContrato logica = det.getObjLogicaInstance();
//		  det = logica.getBiz();
//	    det.copyProperties(det);

			
			BizUsuario.eventInterfaz(this.getClass().getCanonicalName(), "Evaluando; "+det.getDescripcion(), row++, recs.getStaticItems().size(), false, null);
			procCalculeOverDetalle(det,fechaDesde,fechaHasta,fechaCalc);
		}
	}
	public void fillDetalle(BizDetalle detalle) throws Exception {
		fillDetalle(detalle.getObjContrato().getFechaDesde(),detalle.getObjContrato().getFechaHasta(),detalle, new Date());
	}
	public void fillOthers(Date fechaDesde, Date fechaHasta, BizDetalle detalle, Date fechaCalc) throws Exception {
		
		JRecords<BizPNRTicket> tkts = new JRecords<BizPNRTicket>(BizPNRTicket.class);
		
		String sql = "UPDATE public.tur_pnr_boleto ";
		sql+=" SET upfront_ref = null , importeover = null, porcentajeover=null, porcentaje_prorr_over=null,";
		sql+=" importe_prorr_over=null,oversobre='N',fecha_calc_over=null,update_version_over=null ";
		sql+=" WHERE creation_date >= '"+JDateTools.dateToStr(fechaDesde,"yyyy-MM-dd") +"'  and creation_date<='"+JDateTools.dateToStr(fechaHasta,"yyyy-MM-dd")+"'";
		sql+=" and fecha_calc_over<>'"+JDateTools.dateToStr(fechaCalc,"yyyy-MM-dd")+"' and upfront_ref="+detalle.getLinea();
		
		JBaseRegistro reg;
		reg = JBaseRegistro.recordsetFactory();
		reg.Execute(sql);
		reg.close();
		
//		tkts.addFilter("creation_date", fechaDesde,">=");
//		tkts.addFilter("creation_date", fechaHasta,"<=");
//		tkts.addFilter("fecha_calc_over",fechaCalc,"<>");
//		tkts.addFilter("upfront_ref",detalle.getLinea());
//		JIterator<BizPNRTicket> it = tkts.getStaticIterator();
//		while (it.hasMoreElements()) {
//			BizPNRTicket tkt=it.nextElement();
//			tkt.pUpfrontRef.setNull();
//			tkt.pImporteover.setNull();
//			tkt.pPorcentajeover.setNull();
//			tkt.pPorcentajeProrrateoOver.setNull();
//			tkt.pImporteover.setNull();
//			tkt.pOversobre.setValue(false);
//			tkt.pFechaCalcOver.setValue(fechaCalc);
//			tkt.pUpdateVersion.setValue(detalle.getUpdateVersion());
//			tkt.update();
//		}
		
	}
	public void fillDetalle(Date fechaDesde, Date fechaHasta, BizDetalle detalle, Date fechaCalc) throws Exception {
		if (detalle.getObjEventGanancia()==null) return;
		
		if (!(detalle.getDetalleGanancia() instanceof GuiPNRTickets))
			return;
		JOrderedMap<String, Double> acumPremios = JCollectionFactory.createOrderedMap();
		
		String sql = "UPDATE public.tur_pnr_boleto ";
		sql+=" SET contratos_back = TRIM(BOTH ',' FROM regexp_replace(contratos_back, '(^|,)\\s*"+detalle.getLinea()+"\\s*(,|$)', ',', 'g')) ";
		sql+=" WHERE contratos_back LIKE '%"+detalle.getLinea()+"%' ";
		
		JBaseRegistro reg;
		reg = JBaseRegistro.recordsetFactory();
		reg.Execute(sql);
		reg.close();
		
		double comdefa = 0;
		JRecords<BizProrrateo> prs = new JRecords<BizProrrateo>(BizProrrateo.class);
		prs.addFilter("company", detalle.getCompany());
		prs.addFilter("contrato", detalle.getId());
		prs.addFilter("detalle", detalle.getLinea());
	
		Calendar periodo = Calendar.getInstance();
		periodo.setTime(detalle.getFDesde());
		detalle.setFechaDesdeCalculo(fechaDesde);
		detalle.setFechaCalculo(fechaHasta);
		GuiPNRTickets tkts = (GuiPNRTickets) detalle.getDetalleGanancia();
		int id = 0;
		tkts.getRecords().readAll();
		while (tkts.getRecords().nextRecord()) {
			BizPNRTicket tkt = (BizPNRTicket) tkts.getRecords().getRecord();
			tkt.calculeOneOver(detalle, fechaCalc,false, !detalle.isVoladoBase());
			tkt.update();
		}
		tkts.getRecords().closeRecord();
	}
	
  public static void ExecCalculeOver(String company,Date fechaDesde,Date fechaHasta) throws Exception {

  	try {
		 	JRecords<BizPNRTicket> pnrs = new JRecords<BizPNRTicket>(BizPNRTicket.class);
		 	pnrs.addFilter("company", company);
		 	pnrs.addFilter("calculed",false);
		 	pnrs.setTop(1000);
		 	if (fechaDesde!=null)
		 		pnrs.addFilter("creation_date_date",fechaDesde,">=");
		 	if (fechaHasta!=null)
		 		pnrs.addFilter("creation_date_date",fechaHasta,"<=");
		 	
			JIterator<BizPNRTicket> it =pnrs.getStaticIterator();
			while (it.hasMoreElements()) {
				BizPNRTicket pnr = it.nextElement();
				pnr.execCalculeOne(company);
				Thread.yield();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  }
	
	public void calculeOver() throws Exception {
		calculeOverEmision();
		calculeOverDespegue();
		setCalculed(true);
  	if (JBDatos.GetBases().getPrivateCurrentDatabase().isTransactionInProgress())
  		this.update();
  	else {
  		try {
				JBDatos.GetBases().getPrivateCurrentDatabase().beginTransaction();
				this.update();
				JBDatos.GetBases().getPrivateCurrentDatabase().commit();
			} catch (Exception e) {
				JBDatos.GetBases().getPrivateCurrentDatabase().rollback();
				
			}
    		
  	}
	}
	public void calculeOverEmision() throws Exception {
		Date fechaDesde,fechaHasta;
		Date fechaDesdeSalida,fechaHastaSalida;
		fechaDesde = JDateTools.getDateStartDay(this.getCreationDate());
		fechaHasta = JDateTools.getDateEndDay(this.getCreationDate());
		fechaDesdeSalida = JDateTools.getDateStartDay(this.getDepartureDate());
		fechaHastaSalida = JDateTools.getDateEndDay(this.getDepartureDate());

		
		Date fechaCalc = new Date();
		JRecords<BizDetalle> recs = new JRecords<BizDetalle>(BizDetalle.class);
		recs.addJoin(JRelations.JOIN, "bsp_contrato", "bsp_contrato_detalle.company = bsp_contrato.company and bsp_contrato_detalle.id=bsp_contrato.id");
		recs.addFilter("company", this.getCompany());
		recs.addFilter("bsp_contrato", "tipo_contrato", "C", "=");
		recs.addFilter("bsp_contrato", "fecha_hasta", fechaDesde, "JDATE", ">=", "AND", "(");
		recs.addFilter("bsp_contrato", "fecha_desde", fechaHasta, "JDATE", "<=", "OR", ")");
		recs.addFixedFilter("where (id_aerolinea='" + this.getCarrier() + "' or aerolineas_placa like '%" + this.getCarrier() + "%') "+
		" and (((bsp_contrato_detalle.logica like '%DetalleBackend%') and (bsp_contrato_detalle.volado_base = 'N')) "+
		" or (bsp_contrato_detalle.logica like '%Upfront%')) ");
		recs.addOrderBy("bsp_contrato","id","asc");
		recs.addOrderBy( "prioridad", "desc");
		int row = 0;
		Date marca = new Date();
		JIterator<BizDetalle> it = recs.getStaticIterator();
		while (it.hasMoreElements()) {
			BizDetalle det = it.nextElement();
			
			if (!(det.getDetalleGanancia() instanceof GuiPNRTickets))
				continue;
			JOrderedMap<String, Double> acumPremios = JCollectionFactory.createOrderedMap();

			double comdefa = 0;
			JRecords<BizProrrateo> prs = new JRecords<BizProrrateo>(BizProrrateo.class);
			prs.addFilter("company", det.getCompany());
			prs.addFilter("contrato", det.getId());
			prs.addFilter("detalle", det.getLinea());

			Calendar periodo = Calendar.getInstance();
			periodo.setTime(det.getFDesde());
			det.setFechaDesdeCalculo(fechaDesde);
			det.setFechaCalculo(fechaHasta);
			boolean inContrato=det.hasTicketInDetalleGanancia(this);
			if (!inContrato) continue;
			calculeOneOver(det,marca,true,true);
			
			
		}
	}
	public void calculeOverDespegue() throws Exception {
		Date fechaDesde,fechaHasta;
		fechaDesde = JDateTools.getDateStartDay(this.getDepartureDate());
		fechaHasta = JDateTools.getDateEndDay(this.getDepartureDate());

		
		Date fechaCalc = new Date();
		JRecords<BizDetalle> recs = new JRecords<BizDetalle>(BizDetalle.class);
		recs.addJoin(JRelations.JOIN, "bsp_contrato", "bsp_contrato_detalle.company = bsp_contrato.company and bsp_contrato_detalle.id=bsp_contrato.id");
		recs.addFilter("company", this.getCompany());
		recs.addFilter("bsp_contrato", "tipo_contrato", "C", "=");
		recs.addFilter("bsp_contrato", "fecha_hasta", fechaDesde, "JDATE", ">=", "AND", "(");
		recs.addFilter("bsp_contrato", "fecha_desde", fechaHasta, "JDATE", "<=", "OR", ")");
		recs.addFilter("logica", "Backend", "like");
		recs.addFilter("bsp_contrato_detalle", "volado_base", "S", "=");
		recs.addFixedFilter("where (id_aerolinea='" + this.getCarrier() + "' or aerolineas_placa like '%" + this.getCarrier() + "%') ");
		recs.addOrderBy("bsp_contrato","id","asc");
		recs.addOrderBy( "prioridad", "desc");
		
		int row = 0;
		Date marca = new Date();
		JIterator<BizDetalle> it = recs.getStaticIterator();
		while (it.hasMoreElements()) {
			BizDetalle det = it.nextElement();
			// ILogicaContrato logica = det.getObjLogicaInstance();
			// det = logica.getBiz();
			// det.copyProperties(det);

//			if (!det.isUpfront())
//				continue;
			if (!(det.getDetalleGanancia() instanceof GuiPNRTickets))
				continue;
			JOrderedMap<String, Double> acumPremios = JCollectionFactory.createOrderedMap();

			double comdefa = 0;
			JRecords<BizProrrateo> prs = new JRecords<BizProrrateo>(BizProrrateo.class);
			prs.addFilter("company", det.getCompany());
			prs.addFilter("contrato", det.getId());
			prs.addFilter("detalle", det.getLinea());

			Calendar periodo = Calendar.getInstance();
			periodo.setTime(det.getFDesde());
			det.setFechaDesdeCalculo(fechaDesde);
			det.setFechaCalculo(fechaHasta);
			boolean inContrato=det.hasTicketInDetalleGanancia(this);
			if (!inContrato) continue;
			calculeOneOver(det,marca,true,false);
			
			
		}
	}
	public void calculeOneOver(BizDetalle det, Date fechaCalc, boolean clean, boolean emision) throws Exception {
		String cl = getCustomerIdReducido();
		if (det.isUpfront()) {
			double ivaOver = (BizBSPCompany.getConfigView(det.getCompany()).getPorcsIvaOver());
			BizClienteDK pers = null;
			if (getCustomerIdReducido()!=null&& !this.getCustomerIdReducido().equals("")) {
				pers = new BizClienteDK();
				pers.dontThrowException(true);
				if ( pers.ReadByDK(det.getCompany(), this.getCustomerIdReducido())) {
					if (!pers.isNullIva())
						ivaOver = pers.getIva();
				}
			}
			if (pers!=null) {
				
				if (pers.getTipoComision().equals(BizClienteDK.SIN_COMISION)) {
					setOversobre(false);
					setPorcentajeProrrateoOver(0);
					setImporteProrrateoOver(0);
				} else if (cl != null && !cl.isEmpty()) {
					Double porcCl = null;
					BizProrrateo prorr = new BizProrrateo();
					prorr.dontThrowException(true);
					prorr.addFilter("company", getCompany());
					prorr.addFilter("detalle", det.getLinea());
					prorr.addFilter("cliente", cl, "like");
					if (!prorr.read()) {
						prorr = new BizProrrateo();
						prorr.dontThrowException(true);
						prorr.addFilter("company", getCompany());
						prorr.addFilter("detalle", det.getLinea());
						prorr.addFilter("cliente", "ALL", "=");
						if (prorr.read()) {
							porcCl = prorr.getComision();
						}
					} else
						porcCl = prorr.getComision();
	
					if (porcCl != null) {
						setOversobre(true);
						setPorcentajeProrrateoOver(porcCl);
						setImporteProrrateoOver(JTools.rd(((emision?getTarifaFacturaLocal():getTarifa()) * porcCl) / 100.0, 2));
					} else {
						setOversobre(false);
						setPorcentajeProrrateoOver(0);
						setImporteProrrateoOver(0);
	
					}
	
				}
			}
			
			Double porc = det.getPorcUpfront();
			this.setPorcentajeover(porc);
			this.setImporteover(JTools.rd(((emision?getTarifaFacturaLocal():getTarifa()) * porc) / 100.0, 2));
			this.setIvaover(this.getImporteover() * (ivaOver / 100f));
			this.setUpfrontRef(det.getLinea());
			this.setFechaCalcOver(fechaCalc);
			this.setUpdateVersion(det.getUpdateVersion());
		} else {
			if (this.getFechaCalcOverBack()==null || !this.getFechaCalcOverBack().equals(fechaCalc) && clean) 
				this.clearContratoBack();
			if (!this.getContratoBack().containsElement("" + det.getLinea()))
				this.addContratoBack("" + det.getLinea());
			this.setFechaCalcOverBack(fechaCalc);
		}
		this.setCalculed(true);
	}

	@Override
	public String getSituation(IConciliable other, double precision) throws Exception {
		Double a =  other.getDoubleValue(IConciliable.COMISION_OVER, null, false);//bsp
		Double b =  this.getDoubleValue(IConciliable.COMISION_OVER, null, false);//pnr
		if (a==null) return "";
		if (b==null) return "";
		if (Math.abs(Math.abs(a.doubleValue()) - Math.abs(b.doubleValue())) < 0.01 + precision)  return "";
		if ((a.doubleValue()-b.doubleValue())>0) return "Sobrecomision ";//+(a.doubleValue()-b.doubleValue());
		if ((b.doubleValue()-a.doubleValue())>0) return "Diferencia ";//+(a.doubleValue()-b.doubleValue());
		return "";
	}

	@Override
	public String getContrato() throws Exception {
		return getUpfrontDescripcion();
	}
}
