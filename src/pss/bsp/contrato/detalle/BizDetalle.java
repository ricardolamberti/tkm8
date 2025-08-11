package pss.bsp.contrato.detalle;

import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

import pss.bsp.bspBusiness.BizBSPCompany;
import pss.bsp.bspBusiness.BizBSPUser;
import pss.bsp.contrato.BizContrato;
import pss.bsp.contrato.JAutogenerador;
import pss.bsp.contrato.detalle.nivel.BizNivel;
import pss.bsp.contrato.detalle.prediccion.IPrediccion;
import pss.bsp.contrato.detalle.prediccion.JNeuronalSVM;
import pss.bsp.contrato.detalle.prorrateo.BizProrrateo;
import pss.bsp.contrato.detalle.variaciones.BizVariacionPaticular;
import pss.bsp.contrato.detalleAvianca.grilla.BizObjetivosGrilla;
import pss.bsp.contrato.detalleAvianca.grilla.BizObjetivosGrillas;
import pss.bsp.contrato.detalleAvianca.grilla.GuiObjetivosGrillas;
import pss.bsp.contrato.detalleCopa.objetivos.BizObjetivosCopa;
import pss.bsp.contrato.detalleCopa.objetivos.BizObjetivosCopas;
import pss.bsp.contrato.detalleCopa.objetivos.GuiObjetivosCopas;
import pss.bsp.contrato.detalleCopaPorRutas.objetivos.BizObjetivosCopaPorRutas;
import pss.bsp.contrato.detalleCopaPorRutas.objetivos.GuiObjetivosCopasPorRutas;
import pss.bsp.contrato.detalleCopaWS.objetivos.BizObjetivosCopaWS;
import pss.bsp.contrato.detalleCopaWS.objetivos.GuiObjetivosCopasWS;
import pss.bsp.contrato.detalleRutas.objetivos.BizObjetivosRuta;
import pss.bsp.contrato.detalleRutas.objetivos.BizObjetivosRutas;
import pss.bsp.contrato.detalleRutas.objetivos.GuiObjetivosRutas;
import pss.bsp.contrato.logica.ILogicaContrato;
import pss.bsp.contrato.logica.JContratoAvianca;
import pss.bsp.contrato.logica.JContratoBackendDobleRuta;
import pss.bsp.contrato.logica.JContratoBackendRuta;
import pss.bsp.contrato.logica.JContratoCopa;
import pss.bsp.contrato.logica.JContratoCopaPorRutas;
import pss.bsp.contrato.logica.JContratoCopaWS;
import pss.bsp.contrato.logica.JContratoLatamFamilia;
import pss.bsp.contrato.logica.JContratoNormal;
import pss.bsp.contrato.logica.JContratoRuta;
import pss.bsp.contrato.logica.JContratoTriple;
import pss.bsp.contrato.logica.JContratoUpfront;
import pss.bsp.contrato.logica.JContratoUpfrontRuta;
import pss.bsp.contrato.logica.JContratoUpfrontRutaSlave;
import pss.bsp.contrato.quevender.BizQueVender;
import pss.bsp.contrato.series.calculado.BizSerieCalculada;
import pss.bsp.contrato.series.calculado.GuiSerieCalculadas;
import pss.bsp.contrato.series.variaciones.BizVariacion;
import pss.bsp.gds.BizInterfazNew;
import pss.bsp.interfaces.copa.detalle.BizCopaDetalle;
import pss.bsp.reembolsos.BizReembolso;
import pss.bsp.reembolsos.GuiReembolsos;
import pss.common.customList.config.relation.JRelation;
import pss.common.customList.config.relation.JRelations;
import pss.common.event.sql.BizSqlEvent;
import pss.common.event.sql.datos.BizSqlEventDato;
import pss.common.event.sql.serie.GuiVirtualSeries;
import pss.common.layoutWysiwyg.BizPlantilla;
import pss.common.regions.company.BizCompany;
import pss.common.regions.multilanguage.JLanguage;
import pss.common.security.BizUsuario;
import pss.core.data.interfaces.connections.JBDatos;
import pss.core.data.interfaces.sentences.JBaseRegistro;
import pss.core.graph.Graph;
import pss.core.graph.analize.Dataset;
import pss.core.graph.implementations.GraphScriptMedioReloj;
import pss.core.graph.implementations.GraphScriptSerieTemporal;
import pss.core.graph.model.ModelGrid;
import pss.core.graph.model.ModelMatrix;
import pss.core.services.JExec;
import pss.core.services.fields.JBoolean;
import pss.core.services.fields.JCurrency;
import pss.core.services.fields.JDate;
import pss.core.services.fields.JDateTime;
import pss.core.services.fields.JFloat;
import pss.core.services.fields.JInteger;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JMultiple;
import pss.core.services.fields.JObjBDs;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.JDateTools;
import pss.core.tools.JTools;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JList;
import pss.core.tools.collections.JMap;
import pss.core.win.JWins;
import pss.core.winUI.lists.JWinList;
import pss.tourism.pnr.BizBooking;
import pss.tourism.pnr.BizPNRReserva;
import pss.tourism.pnr.BizPNRTicket;
import pss.tourism.pnr.GuiPNRTickets;

public class BizDetalle extends JRecord {

	final public static String MENSUAL = "MENSUAL";
	final public static String BIMESTRAL = "BIMESTRAL";
	final public static String TRIMESTRAL = "TRIMESTRAL";
	final public static String CUATRIMESTRAL = "CUATRIMESTRAL";
	final public static String SEMESTRAL = "SEMESTRAL";
	final public static String ANUAL = "ANUAL";

  protected JString pCompany = new JString();
  protected JLong pLinea = new JLong();
  protected JLong pId = new JLong();
  protected JLong pPrioridad = new JLong();

  
  
  protected JLong pValor = new JLong();
  protected JString pEstado = new JString();
  protected JLong pVariable = new JLong();
  protected JLong pVariableGanancia = new JLong();
  protected JLong pVariableAuxiliar = new JLong();
  protected JString pDescripcion = new JString();
  protected JBoolean pAcumulativo = new JBoolean();
  protected JBoolean pPax = new JBoolean();
  protected JBoolean pPaxBase = new JBoolean();
  protected JBoolean pPaxAux = new JBoolean();
  protected JBoolean pViceversa = new JBoolean();
  protected JString pPrimera = new JString();
   protected JString pMS = new JString();
  protected JString pMSBase = new JString();
  protected JBoolean pDolares = new JBoolean();
  protected JBoolean pYQ = new JBoolean();
  protected JBoolean pOrigenNegado = new JBoolean();
  protected JBoolean pDestinoNegado = new JBoolean();
  protected JString pVolado = new JString();
  protected JString pDomestico = new JString();
  protected JString pInterlineal = new JString();
  protected JBoolean pExchange = new JBoolean();
  protected JString pPeriodo = new JString();
  protected JString pDefaultTipoObjetivo = new JString();
  protected JString pDefaultTipoPremio = new JString();
  protected JString pMercados = new JString();
  protected JString pNoMercados = new JString();
  protected JString pRutas = new JString();
  protected JString pNoRutas = new JString();
  protected JString pVuelos = new JString();
  protected JString pNoVuelos = new JString();
  protected JString pNoVuelosBase = new JString();
  protected JString pNoVuelosAux = new JString();
  protected JString pAndOr = new JString();
  protected JString pAndOrBase = new JString();
  protected JString pAndOrAux = new JString();
  protected JLong pModelo = new JLong();
  protected JBoolean pKicker = new JBoolean();
  protected JString pGrupo = new JString();
  protected JString pAutogenerado = new JString();
  protected JBoolean pIdemAnterior = new JBoolean();
  protected JMultiple pMeses = new JMultiple();
  protected JDateTime pUpdateVersion = new JDateTime();
  
  protected JDate pFechaEmisionDesde = new JDate();
  protected JDate pFechaEmisionHasta = new JDate();
  protected JDate pFechaVueloDesde = new JDate();
  protected JDate pFechaVueloHasta = new JDate();
  protected JDate pFechaBlockoutDesde = new JDate();
  protected JDate pFechaBlockoutHasta = new JDate();
  protected JLong pSepFecha = new JLong();

  protected JString pLogicaContrato = new JString();

  protected JString pDescrSqlAux1Meta = new JString() {
  	public void preset() throws Exception {pDescrSqlAux1Meta.setValue(getDescrSqlAux1Meta());}
  };
  protected JString pDescrSqlAux2Meta = new JString() {
  	public void preset() throws Exception {pDescrSqlAux2Meta.setValue(getDescrSqlAux2Meta());}
  };
  protected JFloat pSqlAux1Meta = new JFloat(){
  	public void preset() throws Exception {pSqlAux1Meta.setValue(getSqlAux1Meta());}
  };
  protected JFloat pSqlAux2Meta = new JFloat(){
  	public void preset() throws Exception {pSqlAux2Meta.setValue(getSqlAux2Meta());}
  };
  protected JString pDescrSqlAux1Base = new JString() {
  	public void preset() throws Exception {pDescrSqlAux1Base.setValue(getDescrSqlAux1Base());}
  };
  protected JString pDescrSqlAux2Base = new JString() {
  	public void preset() throws Exception {pDescrSqlAux2Base.setValue(getDescrSqlAux2Base());}
  };
  protected JFloat pSqlAux1Base = new JFloat(){
  	public void preset() throws Exception {pSqlAux1Base.setValue(getSqlAux1Base());}
  };
  protected JFloat pSqlAux2Base = new JFloat(){
  	public void preset() throws Exception {pSqlAux2Base.setValue(getSqlAux2Base());}
  };
  protected JString pImagen1 = new JString() {
  	public void preset() throws Exception {pImagen1.setValue(getImagen1(false));}
  };
  protected JString pImagen2 = new JString() {
  	public void preset() throws Exception {pImagen2.setValue(getImagen2());}
  };
  protected JString pImagen3 = new JString() {
  	public void preset() throws Exception {pImagen3.setValue(getImagen1(true));}
  };
  protected JString pImagen4 = new JString() {
  	public void preset() throws Exception {pImagen4.setValue(getImagenGanancia(true));}
  };
  protected JString pImagen5 = new JString() {
  	public void preset() throws Exception {pImagen5.setValue(getImagenAuxiliar(true));}
  };
  protected JFloat pFirstMeta = new JFloat(){
  	public void preset() throws Exception {pFirstMeta.setValue(getFirstMeta());}
  };
  protected JString pConclusion = new JString();
  protected JString pAnalisis = new JString();
  protected JString pDescrVariable = new JString() {
  	public void preset() throws Exception {pDescrVariable.setValue(getDescrVariable());}
  };
  protected JString pDescrContrato = new JString() {
  	public void preset() throws Exception {pDescrContrato.setValue(getDescrContrato());}
  };
  protected JFloat pValorObjetivo = new JFloat();
  protected JFloat pSigValorObjetivo = new JFloat();
  protected JFloat pValorActual = new JFloat(); // borrar
  protected JFloat pValorTotal = new JFloat(); // borrar
  protected JCurrency pValorFinContrato = new JCurrency();
  protected JCurrency pValorTotalFinContrato = new JCurrency();
  protected JString pNivelAlcanzado = new JString(); // borrar
  protected JCurrency pGanancia = new JCurrency(); // borrar
  protected JString pNivelAlcanzadoEstimada = new JString();
  protected JCurrency pGananciaEstimada = new JCurrency();
  protected JCurrency pGananciaAuxiliar = new JCurrency();
  protected JDate pFechaPrediccion = new JDate();
  
  protected JFloat pFMSGlobal = new JFloat();
  protected JFloat pShareGapGLobal = new JFloat();
  protected JFloat pValorGlobal = new JFloat();
  protected JFloat pPorcUpfront = new JFloat();
  protected JFloat pReembolso = new JFloat();
  protected JFloat pReembolsoAuto = new JFloat();
  protected JFloat pReembolsoAutoShow = new JFloat() {
  	public void preset() throws Exception {
  		if (pRecalculeReembolso.getRawValue()) {
  			calculeReembolsos();
  		}
  		pReembolsoAutoShow.setValue(pReembolsoAuto.getRawValue());
  	};
  };
  protected JBoolean pRecalculeReembolso = new JBoolean();
   protected JFloat pPonderado= new JFloat();
  protected JCurrency pComisionBase = new JCurrency();
  

  protected JFloat pValorTotalContratoCalc = new JFloat() {
  	public void preset() throws Exception {pValorTotalContratoCalc.setValue(getValorTotalContratoCalc());}

  };
  protected JFloat pGananciaEstimadaCalc = new JFloat() {
  	public void preset() throws Exception {pGananciaEstimadaCalc.setValue(getGananciaEstimadaCalc());}

  };
  protected JFloat pNivelAlcanzadoEstimadoCalc= new JFloat() {
  	public void preset() throws Exception {pNivelAlcanzadoEstimadoCalc.setValue(getNivelAlcanzadoEstimadoCalc());}
  	public int getCustomPrecision() throws Exception {return 2;};

  };
  
	private JLong pReservasMeta = new JLong() {
		public void preset() throws Exception {
			pReservasMeta.setValue(getReservasMeta());
		};
	};
	private JLong pReservasBase = new JLong(){
		public void preset() throws Exception {
			pReservasBase.setValue(getReservasBase());
		};
	};
	private JLong pReservasAux = new JLong(){
		public void preset() throws Exception {
			pReservasAux.setValue(getReservasAux());
		};
	};
	private JLong pFMSMin = new JLong();
  private JLong pFMSMax = new JLong();
  private JLong pLimiteMinTkt = new JLong();
  private JString pIdAerolinea = new JString();
  private JString pAerolineas = new JString();
  private JString pAerolineasPlaca = new JString();
  private JString pNoAerolineas = new JString();
  private JString pNoAerolineasPlaca = new JString();
  private JString pIatas = new JString();
  private JString pNoIatas = new JString();
  private JString pGDS = new JString();
  private JString pNoGDS = new JString();
  private JString pGDSBase = new JString();
  private JString pNoGDSBase = new JString();
  private JString pGDSAux = new JString();
  private JString pNoGDSAux = new JString();
  private JString pVersionInterfaz = new JString();
  private JString pIdGeneracion = new JString();
  private JDate pFechaDesdeCalculo = new JDate();
  private JDate pFechaCalculo = new JDate();
  private JString pInfoAerolineas = new JString() {
  	public void preset() throws Exception {
  		pInfoAerolineas.setValue(getInfoAerolineas());
  	};
  };
  

  private JString pTourCodeExcluded = new JString();
  private JString pTourCodeIncluded = new JString();
  private JString pFareBasicExcluded = new JString();
  private JString pFareBasicIncluded = new JString();
  private JString pTipoPasajeroExcluded = new JString();
  private JString pTipoPasajeroIncluded = new JString();
  private JString pClasesExcluded = new JString();
  private JString pClasesIncluded = new JString();
  private JMultiple pOrigenContinente = new JMultiple();
  private JMultiple pOrigenPais = new JMultiple();
  private JMultiple pOrigenZona = new JMultiple();
  private JMultiple pOrigenAeropuerto = new JMultiple();
  private JMultiple pDestinoContinente = new JMultiple();
  private JMultiple pDestinoPais = new JMultiple();
  private JMultiple pDestinoZona = new JMultiple();
  private JMultiple pDestinoAeropuerto = new JMultiple();

  private JString pOrigenConsolidado = new JString() {
  	public void preset() throws Exception { pOrigenConsolidado.setValue(getOrigenConsolidado());};
  };
  private JString pDestinoConsolidado = new JString() {
  	public void preset() throws Exception { pDestinoConsolidado.setValue(getDestinoConsolidado());};
  };
  private JString pMonedaConsolidada = new JString() {
  	public void preset() throws Exception { pMonedaConsolidada.setValue(getMonedaConsolidada());};
  };
  private JString pPremioUpfront = new JString() {
  	public void preset() throws Exception { pPremioUpfront.setValue(getPremioUpfront());};
  };
  protected JBoolean pOrigenNegadoBase = new JBoolean();
  protected JBoolean pDestinoNegadoBase = new JBoolean();
  protected JString pVoladoBase = new JString();
  protected JString pDomesticoBase = new JString();
  protected JString pInterlinealBase = new JString();
  protected JBoolean pExchangeBase = new JBoolean();
  protected JBoolean pViceversaBase = new JBoolean();
  protected JString pPrimeraBase = new JString();
  protected JString pTipoViaje = new JString();
  protected JString pMultidestino = new JString();
  protected JString pStopover= new JString();
  protected JString pCambio = new JString();
  protected JString pVueltaMundo = new JString();
  
  protected JString pMercadosBase = new JString();
  protected JString pNoMercadosBase = new JString();
  protected JString pRutasBase = new JString();
  protected JString pVuelosBase = new JString();
  private JString pFareBasicExcludedBase = new JString();
  private JString pFareBasicIncludedBase = new JString();
  private JString pTourCodeExcludedBase = new JString();
  private JString pTourCodeIncludedBase = new JString();
  private JString pTipoPasajeroExcludedBase = new JString();
  private JString pTipoPasajeroIncludedBase = new JString();
  private JString pClasesExcludedBase = new JString();
  private JString pClasesIncludedBase = new JString();
  private JMultiple pOrigenContinenteBase = new JMultiple();
  private JMultiple pOrigenPaisBase = new JMultiple();
  private JMultiple pOrigenZonaBase = new JMultiple();
  private JMultiple pOrigenAeropuertoBase = new JMultiple();
  private JMultiple pDestinoContinenteBase = new JMultiple();
  private JMultiple pDestinoPaisBase = new JMultiple();
  private JMultiple pDestinoZonaBase = new JMultiple();
  private JMultiple pDestinoAeropuertoBase = new JMultiple();

  protected JString pMSAux = new JString();
  protected JBoolean pOrigenNegadoAux = new JBoolean();
  protected JBoolean pDestinoNegadoAux = new JBoolean();
  protected JString pVoladoAux = new JString();
  protected JBoolean pDomesticoAux = new JBoolean();
  protected JString pInterlinealAux = new JString();
  protected JBoolean pExchangeAux = new JBoolean();
  protected JBoolean pViceversaAux = new JBoolean();
  protected JString pPrimeraAux = new JString();
  protected JString pMercadosAux = new JString();
  protected JString pNoMercadosAux = new JString();
  protected JString pRutasAux = new JString();
  protected JString pVuelosAux = new JString();
  private JString pTourCodeExcludedAux = new JString();
  private JString pTourCodeIncludedAux = new JString();
  private JString pFareBasicExcludedAux = new JString();
  private JString pFareBasicIncludedAux = new JString();
  private JString pTipoPasajeroExcludedAux = new JString();
  private JString pTipoPasajeroIncludedAux = new JString();
  private JString pClasesExcludedAux = new JString();
  private JString pClasesIncludedAux = new JString();
  private JMultiple pOrigenContinenteAux = new JMultiple();
  private JMultiple pOrigenPaisAux = new JMultiple();
  private JMultiple pOrigenZonaAux = new JMultiple();
  private JMultiple pOrigenAeropuertoAux = new JMultiple();
  private JMultiple pDestinoContinenteAux = new JMultiple();
  private JMultiple pDestinoPaisAux = new JMultiple();
  private JMultiple pDestinoZonaAux = new JMultiple();
  private JMultiple pDestinoAeropuertoAux = new JMultiple();
  
  private JString pConsultaReservaBase = new JString();
  private JString pConsultaReservaMeta = new JString();
  private JString pConsultaReservaAux = new JString();

  
  private JString pTipoContrato = new JString() {
   	public void preset() throws Exception {pTipoContrato.setValue(getTipoContrato());}
     	
  };


  protected JFloat pNivelCumplimiento = new JFloat() {
  	public void preset() throws Exception {pNivelCumplimientoEstimado.setValue(getNivelCumplimiento());}
  	public int getCustomPrecision() throws Exception {return 2;};

  };
  protected JFloat pNivelCumplimientoEstimado = new JFloat() {
  	public void preset() throws Exception {pNivelCumplimientoEstimado.setValue(getNivelCumplimiento());}
  	public int getCustomPrecision() throws Exception {return 2;};

  };
  protected JString pDetalleNivelCumplimiento = new JString() {
  	public void preset() throws Exception {pDetalleNivelCumplimiento.setValue(getDetalleNivelCumplimiento());}
  };
  protected JDate pFDesde = new JDate() {
  	public void preset() throws Exception {
  		setValue(getObjContrato().getFechaDesde());
  	};
  };
  protected JInteger pMes = new JInteger() {
  	public void preset() throws Exception {
  		setValue(JDateTools.getMonthNumber(getObjContrato().getFechaDesde()));
  	};
  };
  protected JDate pFHasta  = new JDate() {
  	public void preset() throws Exception {
  		setValue(getObjContrato().getFechaHasta());
  	};
  };
  protected JString pPeriodoDetalle = new JString() {
  	public void preset() throws Exception {
  		pPeriodoDetalle.setValue(getPeriodoDetalle());
  	};
  };
	protected JObjBDs<BizNivel> pNiveles= new JObjBDs<BizNivel>()
	{
		public void preset() throws Exception {
//			if (pNiveles.isRawNull())
				pNiveles.setValue(getObjNiveles());
		}
	};

	protected JObjBDs<BizObjetivosGrilla> pGrillas= new JObjBDs<BizObjetivosGrilla>() {
		public void preset() throws Exception {
			pGrillas.setValue(getObjGrillas());
		}
	};
	protected JObjBDs<BizObjetivosCopa> pCopas= new JObjBDs<BizObjetivosCopa>() {
		public void preset() throws Exception {
			pCopas.setValue(getObjCopas());
		}
	};
	protected JObjBDs<BizObjetivosRuta> pObjetivosRutas= new JObjBDs<BizObjetivosRuta>() {
		public void preset() throws Exception {
			pObjetivosRutas.setValue(getObjObjetivosRutas());
		}
	};
	protected JObjBDs<BizObjetivosCopaPorRutas> pCopasRutas= new JObjBDs<BizObjetivosCopaPorRutas>() {
		public void preset() throws Exception {
			pCopasRutas.setValue(getObjCopaRutas());
		}
	};
	protected JObjBDs<BizObjetivosCopaWS> pCopasRutasWS= new JObjBDs<BizObjetivosCopaWS>() {
		public void preset() throws Exception {
			pCopasRutasWS.setValue(getObjCopaRutasWS());
		}
	};
	protected JString pHtmlData = new JString() {
	  	public void preset() throws Exception {pHtmlData.setValue(getHtmlData());}
	  };
	  
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  public void setLinea(long zValue) throws Exception {    pLinea.setValue(zValue);  }
  public long getLinea() throws Exception {     return pLinea.getValue();  }
  public boolean isNullLinea() throws Exception { return  pLinea.isNull(); } 
  public void setNullToLinea() throws Exception {  pLinea.setNull(); } 
  public void setValor(long zValue) throws Exception {    pValor.setValue(zValue);  }
  public long getValor() throws Exception {   return pValor.getValue();  }
//  public double getValorActual() throws Exception {   return pValorActual.getValue();  }
  public double getValorObjetivo() throws Exception {   return pValorObjetivo.getValue();  }
//  public double getGanancia() throws Exception {   return pGanancia.getValue();  }
  public double getSigValorObjetivo() throws Exception {   return pSigValorObjetivo.getValue();  }
//  public String getNivelAlcanzado() throws Exception {   return pNivelAlcanzado.getValue();  }
  public String getNivelAlcanzado() throws Exception {   return pNivelAlcanzadoEstimada.getValue();  }
  public double getGanancia() throws Exception {   return pGananciaEstimada.getValue();  }
  public double getGananciaAuxiliar() throws Exception {   return pGananciaAuxiliar.getValue();  }
  public double getValorTotal() throws Exception {   return pValorTotalFinContrato.getValue();  }
  public double getValorActual() throws Exception {   return pValorFinContrato.getValue();  }
  public Date getFechaPrediccion() throws Exception {   return pFechaPrediccion.getValue();  }
  public void setFechaPrediccion(Date value)   throws Exception {    pFechaPrediccion.setValue(value);  }
  public void setLogicaContrato(String zValue) throws Exception {    pLogicaContrato.setValue(zValue);  }
  public String getLogicaContrato() throws Exception {     return pLogicaContrato.getValue();  }
  public boolean hasLogicaContrato() throws Exception {     return pLogicaContrato.isNotNull() && !pLogicaContrato.getValue().equals("");  }
  public boolean hasFechaCalculo() throws Exception {   return pFechaCalculo.hasValue() ;  }
  public boolean hasFechaDesdeCalculo() throws Exception {   return pFechaDesdeCalculo.hasValue();  }
  public Date getFechaCalculo() throws Exception {   return pFechaCalculo.getValue();  }
  public void setFechaCalculo(Date value)   throws Exception {    pFechaCalculo.setValue(value);  }
  public Date getFechaDesdeCalculo() throws Exception {   return pFechaDesdeCalculo.getValue();  }
  public void setFechaDesdeCalculo(Date value)   throws Exception {    pFechaDesdeCalculo.setValue(value);  }
  public Date getUpdateVersion() throws Exception {   return pUpdateVersion.getValue();  }
  public void setUpdateVersion(Date value)   throws Exception {    pUpdateVersion.setValue(value);  }

  public double getPonderado() throws Exception {   return pPonderado.getValue();  }
  public double getFMSGlobal() throws Exception {   return pFMSGlobal.getValue();  }
  public double getShareGapGlobal() throws Exception {   return pShareGapGLobal.getValue();  }
  public double getValorGlobal() throws Exception {   return pValorGlobal.getValue();  }
  public double getPorcUpfront() throws Exception {   return pPorcUpfront.getValue();  }
  public double getReembolso() throws Exception {   return pReembolso.getValue();  }
  public double getReembolsoAuto() throws Exception {   return pReembolsoAuto.getValue();  }
  public boolean isRecalculeReembolsoAuto() throws Exception {   return pRecalculeReembolso.getValue();  }
  public void setReembolsoAuto(double value)   throws Exception {    pReembolsoAuto.setValue(value);  }
  public void setRecalculeReembolsoAuto(boolean value)   throws Exception {    pRecalculeReembolso.setValue(value);  }

  public long getSepFecha() throws Exception {   return pSepFecha.isNull()?0: pSepFecha.getValue();  }
  
  public JList<String> getOrigenContinente() throws Exception {   return pOrigenContinente.getValue();  }
  public JList<String> getOrigenPais() throws Exception {   return pOrigenPais.getValue();  }
  public JList<String> getOrigenZona() throws Exception {   return pOrigenZona.getValue();  }
  public JList<String> getOrigenAeropuerto() throws Exception {   return pOrigenAeropuerto.getValue();  }
  public JList<String> getDestinoContinente() throws Exception {   return pDestinoContinente.getValue();  }
  public JList<String> getDestinoPais() throws Exception {   return pDestinoPais.getValue();  }
  public JList<String> getDestinoZona() throws Exception {   return pDestinoZona.getValue();  }
  public JList<String> getDestinoAeropuerto() throws Exception {   return pDestinoAeropuerto.getValue();  }
  public JList<String> getMeses() throws Exception {   return pMeses.getValue();  }
   public String getClasesExcludes() throws Exception {   return pClasesExcluded.getValue();  }
  public String getClasesIncluded() throws Exception {   return pClasesIncluded.getValue();  }
  public String getMercados() throws Exception {   return pMercados.getValue();  }
  public String getNoMercados() throws Exception {   return pNoMercados.getValue();  }
  public String getRutas() throws Exception {   return pRutas.getValue();  }
  public String getNoRutas() throws Exception {   return pNoRutas.getValue();  }
  public String getVuelos() throws Exception {   return pVuelos.getValue();  }
  public String getNoVuelos() throws Exception {   return pNoVuelos.getValue();  }
  public String getNoVuelosBase() throws Exception {   return pNoVuelosBase.getValue();  }
  public String getNoVuelosAux() throws Exception {   return pNoVuelosAux.getValue();  }

  public String geAndOr() throws Exception {   return pAndOr.getValue();  }
  public String getAndOrBase() throws Exception {   return pAndOrBase.getValue();  }
  public String getAndOrAux() throws Exception {   return pAndOrAux.getValue();  }


  public String getTourcodeExcludes() throws Exception {   return pTourCodeExcluded.getValue();  }
  public String getTourcodeIncludes() throws Exception {   return pTourCodeIncluded.getValue();  }
  public boolean hasTourCodeExcluded() throws Exception {   return pTourCodeExcluded.hasValue();  }
  public boolean hasTourCodeIncluded() throws Exception {   return pTourCodeIncluded.hasValue();  }

  public String getTipoPasajeroExcludes() throws Exception {   return pTipoPasajeroExcluded.getValue();  }
  public String getTipoPasajeroIncludes() throws Exception {   return pTipoPasajeroIncluded.getValue();  }
  public boolean hasTipoPasajeroExcluded() throws Exception {   return pTipoPasajeroExcluded.hasValue();  }
  public boolean hasTipoPasajeroIncluded() throws Exception {   return pTipoPasajeroIncluded.hasValue();  }
  
  public boolean hasMercado() throws Exception {   return pMercados.hasValue();  }
  public boolean hasNoMercado() throws Exception {   return pNoMercados.hasValue();  }
  public boolean hasRutas() throws Exception {   return pRutas.hasValue();  }
  public boolean hasNoRutas() throws Exception {   return pNoRutas.hasValue();  }
  public boolean hasVuelos() throws Exception {   return pVuelos.hasValue();  }
  public boolean hasNoVuelos() throws Exception {   return pNoVuelos.hasValue();  }
  public boolean hasNoVuelosBase() throws Exception {   return pNoVuelosBase.hasValue();  }
  public boolean hasNoVuelosAux() throws Exception {   return pNoVuelosAux.hasValue();  }
  public boolean hasAndOr() throws Exception {   return pAndOr.hasValue();  }
  public boolean hasAndOrBase() throws Exception {   return pAndOrBase.hasValue();  }
  public boolean hasAndOrAux() throws Exception {   return pAndOrAux.hasValue();  }
  public boolean hasIatas() throws Exception {   return pIatas.hasValue();  }
  public boolean hasNoIatas() throws Exception {   return pNoIatas.hasValue();  }
  public boolean hasGDS() throws Exception {   return pGDS.hasValue();  }
  public boolean hasNoGDS() throws Exception {   return pNoGDS.hasValue();  }
  public boolean hasGDSBase() throws Exception {   return pGDSBase.hasValue();  }
  public boolean hasNoGDSBase() throws Exception {   return pNoGDSBase.hasValue();  }
  public boolean hasGDSAux() throws Exception {   return pGDSAux.hasValue();  }
  public boolean hasNoGDSAux() throws Exception {   return pNoGDSAux.hasValue();  }
  public boolean hasClasesExcluded() throws Exception {   return pClasesExcluded.hasValue();  }
  public boolean hasClasesIncluded() throws Exception {   return pClasesIncluded.hasValue();  }
  public boolean hasOrigenContinente() throws Exception {   return pOrigenContinente.hasValue();  }
  public boolean hasOrigenPais() throws Exception {   return pOrigenPais.hasValue();  }
  public boolean hasOrigenZona() throws Exception {   return pOrigenZona.hasValue();  }
  public boolean hasOrigenAeropuerto() throws Exception {   return pOrigenAeropuerto.hasValue();  }
  public boolean hasDestinoContinente() throws Exception {   return pDestinoContinente.hasValue();  }
  public boolean hasDestinoPais() throws Exception {   return pDestinoPais.hasValue();  }
  public boolean hasDestinoZona() throws Exception {   return pDestinoZona.hasValue();  }
  public boolean hasDestinoAeropuerto() throws Exception {   return pDestinoAeropuerto.hasValue();  }
  
  public boolean hasMeses() throws Exception {   return pMeses.hasValue();  }
  
  public void setOrigenNegadoBase(boolean zValue) throws Exception {    pOrigenNegadoBase.setValue(zValue);  }
  public boolean isOrigenNegadoBase() throws Exception {     return pOrigenNegadoBase.getValue();  }
  public void setDestinoNegadoBase(boolean zValue) throws Exception {    pDestinoNegadoBase.setValue(zValue);  }
  public boolean isDestinoNegadoBase() throws Exception {     return pDestinoNegadoBase.getValue();  }
  public void setVoladoBase(boolean zValue) throws Exception {    pVoladoBase.setValue(zValue);  }
  public boolean isVoladoBase() throws Exception {     return pVoladoBase.getValue().equals("S");  }
  public boolean isVoladoEmitidoBase() throws Exception {     return pVoladoBase.isNull();  }
  public JList<String> getOrigenContinenteBase() throws Exception {   return pOrigenContinenteBase.getValue();  }
  public JList<String> getOrigenPaisBase() throws Exception {   return pOrigenPaisBase.getValue();  }
  public JList<String> getOrigenZonaBase() throws Exception {   return pOrigenZonaBase.getValue();  }
  public JList<String> getOrigenAeropuertoBase() throws Exception {   return pOrigenAeropuertoBase.getValue();  }
  public JList<String> getDestinoContinenteBase() throws Exception {   return pDestinoContinenteBase.getValue();  }
  public JList<String> getDestinoPaisBase() throws Exception {   return pDestinoPaisBase.getValue();  }
  public JList<String> getDestinoZonaBase() throws Exception {   return pDestinoZonaBase.getValue();  }
  public JList<String> getDestinoAeropuertoBase() throws Exception {   return pDestinoAeropuertoBase.getValue();  }
  public String getClasesExcludesBase() throws Exception {   return pClasesExcludedBase.getValue();  }
  public String getClasesIncludedBase() throws Exception {   return pClasesIncludedBase.getValue();  }
  public String getMercadosBase() throws Exception {   return pMercadosBase.getValue();  }
  public String getNoMercadosBase() throws Exception {   return pNoMercadosBase.getValue();  }
    
  public String getTourcodeExcludesBase() throws Exception {   return pTourCodeExcludedBase.getValue();  }
  public boolean hasTourCodeExcludedBase() throws Exception {   return pTourCodeExcludedBase.hasValue();  }
  public boolean hasTourCodeIncludedBase() throws Exception {   return pTourCodeIncludedBase.hasValue();  }
  public String getTourcodeIncludedBase() throws Exception {   return pTourCodeIncludedBase.getValue();  }

  public String getTipoPasajeroExcludesBase() throws Exception {   return pTipoPasajeroExcludedBase.getValue();  }
  public boolean hasTipoPasajeroExcludedBase() throws Exception {   return pTipoPasajeroExcludedBase.hasValue();  }
  public boolean hasTipoPasajeroIncludedBase() throws Exception {   return pTipoPasajeroIncludedBase.hasValue();  }
  public String getTipoPasajeroIncludedBase() throws Exception {   return pTipoPasajeroIncludedBase.getValue();  }

  public boolean hasRutasBase() throws Exception {   return pRutasBase.hasValue();  }
  public boolean hasVuelosBase() throws Exception {   return pVuelosBase.hasValue();  }
  public String getRutasBase() throws Exception {   return pRutasBase.getValue();  }
  public String getVuelosBase() throws Exception {   return pVuelosBase.getValue();  }
  public boolean hasMercadoBase() throws Exception {   return pMercadosBase.hasValue();  }
  public boolean hasNoMercadoBase() throws Exception {   return pNoMercadosBase.hasValue();  }
  public boolean hasClasesExcludedBase() throws Exception {   return pClasesExcludedBase.hasValue();  }
  public boolean hasClasesIncludedBase() throws Exception {   return pClasesIncludedBase.hasValue();  }
  public boolean hasOrigenContinenteBase() throws Exception {   return pOrigenContinenteBase.hasValue();  }
  public boolean hasOrigenPaisBase() throws Exception {   return pOrigenPaisBase.hasValue();  }
  public boolean hasOrigenZonaBase() throws Exception {   return pOrigenZonaBase.hasValue();  }
  public boolean hasOrigenAeropuertoBase() throws Exception {   return pOrigenAeropuertoBase.hasValue();  }
  public boolean hasDestinoContinenteBase() throws Exception {   return pDestinoContinenteBase.hasValue();  }
  public boolean hasDestinoPaisBase() throws Exception {   return pDestinoPaisBase.hasValue();  }
  public boolean hasDestinoZonaBase() throws Exception {   return pDestinoZonaBase.hasValue();  }
  public boolean hasDestinoAeropuertoBase() throws Exception {   return pDestinoAeropuertoBase.hasValue();  }

  public void setOrigenNegadoAux(boolean zValue) throws Exception {    pOrigenNegadoAux.setValue(zValue);  }
  public boolean isOrigenNegadoAux() throws Exception {     return pOrigenNegadoAux.getValue();  }
  public void setDestinoNegadoAux(boolean zValue) throws Exception {    pDestinoNegadoAux.setValue(zValue);  }
  public boolean isDestinoNegadoAux() throws Exception {     return pDestinoNegadoAux.getValue();  }
  public void setVoladoAux(boolean zValue) throws Exception {    pVoladoAux.setValue(zValue);  }
  public boolean isVoladoAux() throws Exception {     return pVoladoAux.getValue().equals("S");  }
  public boolean isVoladoEmitidoAux() throws Exception {     return pVoladoAux.isNull();  }
  public JList<String> getOrigenContinenteAux() throws Exception {   return pOrigenContinenteAux.getValue();  }
  public JList<String> getOrigenPaisAux() throws Exception {   return pOrigenPaisAux.getValue();  }
  public JList<String> getOrigenZonaAux() throws Exception {   return pOrigenZonaAux.getValue();  }
  public JList<String> getOrigenAeropuertoAux() throws Exception {   return pOrigenAeropuertoAux.getValue();  }
  public JList<String> getDestinoContinenteAux() throws Exception {   return pDestinoContinenteAux.getValue();  }
  public JList<String> getDestinoPaisAux() throws Exception {   return pDestinoPaisAux.getValue();  }
  public JList<String> getDestinoZonaAux() throws Exception {   return pDestinoZonaAux.getValue();  }
  public JList<String> getDestinoAeropuertoAux() throws Exception {   return pDestinoAeropuertoAux.getValue();  }
  public String getClasesExcludesAux() throws Exception {   return pClasesExcludedAux.getValue();  }
  public String getClasesIncludedAux() throws Exception {   return pClasesIncludedAux.getValue();  }
  public String getMercadosAux() throws Exception {   return pMercadosAux.getValue();  }
  public String getNoMercadosAux() throws Exception {   return pNoMercadosAux.getValue();  }
    
  public String getTourcodeExcludesAux() throws Exception {   return pTourCodeExcludedAux.getValue();  }
  public boolean hasTourCodeExcludedAux() throws Exception {   return pTourCodeExcludedAux.hasValue();  }
  public boolean hasTourCodeIncludedAux() throws Exception {   return pTourCodeIncludedAux.hasValue();  }
  public String getTourcodeIncludedAux() throws Exception {   return pTourCodeIncludedAux.getValue();  }

  public String getTipoPasajeroExcludesAux() throws Exception {   return pTipoPasajeroExcludedAux.getValue();  }
  public boolean hasTipoPasajeroExcludedAux() throws Exception {   return pTipoPasajeroExcludedAux.hasValue();  }
  public boolean hasTipoPasajeroIncludedAux() throws Exception {   return pTipoPasajeroIncludedAux.hasValue();  }
  public String getTipoPasajeroIncludedAux() throws Exception {   return pTipoPasajeroIncludedAux.getValue();  }
  public boolean hasRutasAux() throws Exception {   return pRutasAux.hasValue();  }
  public boolean hasVuelosAux() throws Exception {   return pVuelosAux.hasValue();  }
  public String getRutasAux() throws Exception {   return pRutasAux.getValue();  }
  public String getVuelosAux() throws Exception {   return pVuelosAux.getValue();  }

  public boolean hasMercadoAux() throws Exception {   return pMercadosAux.hasValue();  }
  public boolean hasNoMercadoAux() throws Exception {   return pNoMercadosAux.hasValue();  }
  public boolean hasClasesExcludedAux() throws Exception {   return pClasesExcludedAux.hasValue();  }
  public boolean hasClasesIncludedAux() throws Exception {   return pClasesIncludedAux.hasValue();  }
  public boolean hasOrigenContinenteAux() throws Exception {   return pOrigenContinenteAux.hasValue();  }
  public boolean hasOrigenPaisAux() throws Exception {   return pOrigenPaisAux.hasValue();  }
  public boolean hasOrigenZonaAux() throws Exception {   return pOrigenZonaAux.hasValue();  }
  public boolean hasOrigenAeropuertoAux() throws Exception {   return pOrigenAeropuertoAux.hasValue();  }
  public boolean hasDestinoContinenteAux() throws Exception {   return pDestinoContinenteAux.hasValue();  }
  public boolean hasDestinoPaisAux() throws Exception {   return pDestinoPaisAux.hasValue();  }
  public boolean hasDestinoZonaAux() throws Exception {   return pDestinoZonaAux.hasValue();  }
  public boolean hasDestinoAeropuertoAux() throws Exception {   return pDestinoAeropuertoAux.hasValue();  }

  public String getFareBasicExcludesBase() throws Exception {   return pFareBasicExcludedBase.getValue();  }
  public String getFareBasicIncludedBase() throws Exception {   return pFareBasicIncludedBase.getValue();  }
  public String getFareBasicExcludes() throws Exception {   return pFareBasicExcluded.getValue();  }
  public String getFareBasicIncludes() throws Exception {   return pFareBasicIncluded.getValue();  }
  public String getFareBasicExcludesAux() throws Exception {   return pFareBasicExcludedAux.getValue();  }
  public String getFareBasicIncludedAux() throws Exception {   return pFareBasicIncludedAux.getValue();  }

  public boolean hasFareBasicExcluded() throws Exception {   return pFareBasicExcluded.hasValue();  }
  public boolean hasFareBasicIncluded() throws Exception {   return pFareBasicIncluded.hasValue();  }
  public boolean hasFareBasicExcludedBase() throws Exception {   return pFareBasicExcludedBase.hasValue();  }
  public boolean hasFareBasicIncludedBase() throws Exception {   return pFareBasicIncludedBase.hasValue();  }
  public boolean hasFareBasicExcludedAux() throws Exception {   return pFareBasicExcludedAux.hasValue();  }
  public boolean hasFareBasicIncludedAux() throws Exception {   return pFareBasicIncludedAux.hasValue();  }

  public boolean hasConsultaReservaBase() throws Exception {   return pConsultaReservaBase.hasValue();  }
  public boolean hasConsultaReservaMeta() throws Exception {   return pConsultaReservaMeta.hasValue();  }
  public boolean hasConsultaReservaAux() throws Exception {   return pConsultaReservaAux.hasValue();  }
  public String getConsultaReservaBase() throws Exception {   return pConsultaReservaBase.getValue();  }
  public String getConsultaReservaMeta() throws Exception {   return pConsultaReservaMeta.getValue();  }
  public String getConsultaReservaAux() throws Exception {   return pConsultaReservaAux.getValue();  }
  public void setConsultaReservaBase(String zValue) throws Exception { pConsultaReservaBase.setValue(zValue);  }
  public void setConsultaReservaMeta(String zValue) throws Exception { pConsultaReservaMeta.setValue(zValue);  }
  public void setConsultaReservaAux(String zValue) throws Exception { pConsultaReservaAux.setValue(zValue);  }

  
  public long getReservasMeta() throws Exception {  
  	return getListaReservasMeta().selectSupraCount();
  }
  public long getReservasBase() throws Exception {  
  	return getListaReservasBase().selectSupraCount();
  }
  public long getReservasAux() throws Exception {  
  	return getListaReservasAux().selectSupraCount();
  }
  public long getFMSMin() throws Exception {   return pFMSMin.getValue();  }
  public long getFMSMax() throws Exception {   return pFMSMax.getValue();  }
  public long getLimiteMinTkt() throws Exception {   return pLimiteMinTkt.getValue();  }
  public String getRawOrigenContinente() throws Exception { 
  	return pOrigenContinente.toRawString();
  }
  public String getRawOrigenZona() throws Exception { 
  	return pOrigenZona.toRawString();
  }
  public String getRawOrigenPais() throws Exception { 
  	return pOrigenPais.toRawString();
  }
  public String getRawOrigenAeropuerto() throws Exception { 
  	return pOrigenAeropuerto.toRawString();
  }
  public String getRawDestinoContinente() throws Exception { 
  	return pDestinoContinente.toRawString();
  }
  public String getRawDestinoZona() throws Exception { 
  	return pDestinoZona.toRawString();
  }
  public String getRawDestinoPais() throws Exception { 
  	return pDestinoPais.toRawString();
  }
  public String getRawDestinoAeropuerto() throws Exception { 
  	return pDestinoAeropuerto.toRawString();
  }
  public String getRawMeses() throws Exception { 
  	return pMeses.toRawString();
  }
  public String getRawAerolineas() throws Exception {
   	return pAerolineas.getValue();
  }



  public String getRawAerolineasPlaca() throws Exception { 
  	return pAerolineasPlaca.getValue();
  }

  public String getRawIatas() throws Exception { 
  	return pIatas.getValue();
  }
  public String getRawNoIatas() throws Exception { 
  	return pNoIatas.getValue();
  }
  public String getRawGDS() throws Exception { 
  	return pGDS.getValue();
  }
  public String getRawNoGDS() throws Exception { 
  	return pNoGDS.getValue();
  }
  public String getRawGDSBase() throws Exception { 
  	return pGDSBase.getValue();
  }
  public String getRawNoGDSBase() throws Exception { 
  	return pNoGDSBase.getValue();
  }
  public String getRawGDSAux() throws Exception { 
  	return pGDSAux.getValue();
  }
  public String getRawNoGDSAux() throws Exception { 
  	return pNoGDSAux.getValue();
  }
  public String getOrigenConsolidado() throws Exception { 
  	String out="";
  	if (hasOrigenContinente()) 
  		out+= (out.equals("")?"":"/")+getRawOrigenContinente();
  	if (hasOrigenZona()) 
  		out+= (out.equals("")?"":"/")+getRawOrigenZona();
  	if (hasOrigenPais()) 
  		out+= (out.equals("")?"":"/")+getRawOrigenPais();
  	if (hasOrigenAeropuerto()) 
  		out+= (out.equals("")?"":"/")+getRawOrigenAeropuerto();
  	return out;
  }
  public String getMonedaConsolidada() throws Exception { 
  	if (isDolares()) return "USD";
  	return BizCompany.getObjCompany(getCompany()).getCurrency();
  }

	public String getPremioUpfront() throws Exception { 
	 if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isDependant()) {
		 BizProrrateo pr = new BizProrrateo();
		 pr.addFilter("company", getCompany());
		 pr.addFilter("contrato", getId());
		 pr.addFilter("detalle", getLinea());
		 pr.addFixedFilter("where (cliente like '%"+BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getCodigoCliente()+"%') ");
		 pr.dontThrowException(true);
		 if (pr.read()) {
			 return JTools.formatDouble(pr.getComision())+ "%";
		 }
		 pr = new BizProrrateo();
		 pr.addFilter("company", getCompany());
		 pr.addFilter("contrato", getId());
		 pr.addFilter("detalle", getLinea());
		 pr.addFixedFilter("where (cliente='ALL') ");
		 pr.dontThrowException(true);
		 if (pr.read()) {
			 return JTools.formatDouble(pr.getComision())+ "%";
		 }
		 else throw new Exception("Contrato desconocido");
	 }
	 	
		
		
		return JTools.formatDouble(getPorcUpfront())+ "%";
	}
  public String getDestinoConsolidado() throws Exception { 
  	String out="";
  	if (hasDestinoContinente()) 
  		out+= (out.equals("")?"":"/")+getRawDestinoContinente();
  	if (hasDestinoZona()) 
  		out+= (out.equals("")?"":"/")+ getRawDestinoZona();
  	if (hasDestinoPais()) 
  		out+= (out.equals("")?"":"/")+getRawDestinoPais();
  	if (hasDestinoAeropuerto()) 
  		out+= (out.equals("")?"":"/")+getRawDestinoAeropuerto();
  	return out;
  }
  public String getInfoAerolineas() throws Exception { 
  	if (hasMultiAerolineasPlaca()) return getRawAerolineasPlaca();
  	return getIdAerolinea();
  }
  public String getAerolineas() throws Exception {  
    String aer="";
  	StringTokenizer toks = new StringTokenizer( pAerolineas.getValue(),",;");
  	while (toks.hasMoreTokens()) {
  		String s = toks.nextToken();
    	aer+=(aer.equals("")?"":",")+"'"+s+"'";
  	}
  	return aer;  
  }
  public String getNoAerolineasPlaca() throws Exception {  
    String aer="";
  	StringTokenizer toks = new StringTokenizer( pNoAerolineasPlaca.getValue(),",;");
  	while (toks.hasMoreTokens()) {
  		String s = toks.nextToken();
    	aer+=(aer.equals("")?"":",")+"'"+s+"'";
  	}
  	return aer;  
  }
   public String getAerolineasPlaca() throws Exception {  
  	String aer="";
  	StringTokenizer toks = new StringTokenizer( getRawAerolineasPlaca(),",;");
  	while (toks.hasMoreTokens()) {
  		String s = toks.nextToken();
    	aer+=(aer.equals("")?"":",")+"'"+s+"'";
  	}
  	return aer;  
  }
   public boolean hasMultiAerolineas() throws Exception {
  		return (getAerolineas() != null && !getAerolineas().trim().equals(""));
    }
   public boolean hasNoMultiAerolineas() throws Exception {
 		return (getNoAerolineas() != null && !getNoAerolineas().trim().equals(""));

 }
   public boolean hasNoMultiAerolineasPlaca() throws Exception {
 		return (getNoAerolineasPlaca() != null && !getNoAerolineasPlaca().trim().equals(""));

 }
	public boolean hasMultiAerolineasPlaca() throws Exception {
			 return (getAerolineasPlaca() != null && !getAerolineasPlaca().trim().equals(""));
	 }
   public String getRawNoAerolineasPlaca() throws Exception { 
  	 return pNoAerolineasPlaca.getValue();
   }
   public String getRawNoAerolineas() throws Exception { 
  	 return pNoAerolineas.getValue();
   }

   public String getNoAerolineas() throws Exception {  
  	String aer=""; 
  	StringTokenizer toks = new StringTokenizer( pNoAerolineas.getValue(),",;");
  	while (toks.hasMoreTokens()) {
  		String s = toks.nextToken();
    	aer+=(aer.equals("")?"":",")+"'"+s+"'";
  	}
  	return aer;  
  }
 
  public String getIatas() throws Exception {  
  	String aer="";
  	StringTokenizer toks = new StringTokenizer( pIatas.getValue(),",;");
  	while (toks.hasMoreTokens()) {
  		String s = toks.nextToken();
    	aer+=(aer.equals("")?"":",")+"'"+s+"'";
  	}
  	return aer;  
  }
  public String getNoIatas() throws Exception {  
  	String aer="";
  	StringTokenizer toks = new StringTokenizer( pNoIatas.getValue(),",;");
  	while (toks.hasMoreTokens()) {
  		String s = toks.nextToken();
    	aer+=(aer.equals("")?"":",")+"'"+s+"'";
  	}
  	return aer;  
  }
  public String getGDS() throws Exception {  
  	String aer="";
  	StringTokenizer toks = new StringTokenizer( pGDS.getValue(),",;");
  	while (toks.hasMoreTokens()) {
  		String s = toks.nextToken();
    	aer+=(aer.equals("")?"":",")+"'"+s+"'";
  	}
  	return aer;  
  }
  public String getNoGDS() throws Exception {  
  	String aer="";
  	StringTokenizer toks = new StringTokenizer( pNoGDS.getValue(),",;");
  	while (toks.hasMoreTokens()) {
  		String s = toks.nextToken();
    	aer+=(aer.equals("")?"":",")+"'"+s+"'";
  	}
  	return aer;  
  }
  public String getGDSBase() throws Exception {  
  	String aer="";
  	StringTokenizer toks = new StringTokenizer( pGDSBase.getValue(),",;");
  	while (toks.hasMoreTokens()) {
  		String s = toks.nextToken();
    	aer+=(aer.equals("")?"":",")+"'"+s+"'";
  	}
  	return aer;  
  }
  public String getNoGDSBase() throws Exception {  
  	String aer="";
  	StringTokenizer toks = new StringTokenizer( pNoGDSBase.getValue(),",;");
  	while (toks.hasMoreTokens()) {
  		String s = toks.nextToken();
    	aer+=(aer.equals("")?"":",")+"'"+s+"'";
  	}
  	return aer;  
  }
  public String getGDSAux() throws Exception {  
  	String aer="";
  	StringTokenizer toks = new StringTokenizer( pGDS.getValue(),",;");
  	while (toks.hasMoreTokens()) {
  		String s = toks.nextToken();
    	aer+=(aer.equals("")?"":",")+"'"+s+"'";
  	}
  	return aer;  
  }
  public String getNoGDSAux() throws Exception {  
  	String aer="";
  	StringTokenizer toks = new StringTokenizer( pNoGDS.getValue(),",;");
  	while (toks.hasMoreTokens()) {
  		String s = toks.nextToken();
    	aer+=(aer.equals("")?"":",")+"'"+s+"'";
  	}
  	return aer;  
  }

  public String getIdAerolinea() throws Exception {   return pIdAerolinea.getValue();  }
  public String getVersionInterfaz() throws Exception {   return pVersionInterfaz.getValue();  }
  public String getIdGeneracion() throws Exception {   return pIdGeneracion.getValue();  }
  public void setIdAerolinea(String zValue) throws Exception {    pIdAerolinea.setValue(zValue);  }
  public void setAerolineas(String zValue) throws Exception {    pAerolineas.setValue(zValue);  }
  public void setAerolineasPlaca(String zValue) throws Exception {    pAerolineasPlaca.setValue(zValue);  }
  public void setNoAerolineas(String zValue) throws Exception {    pNoAerolineas.setValue(zValue);  }
   public void setNoAerolineasPlaca(String zValue) throws Exception {    pNoAerolineasPlaca.setValue(zValue);  }
  public void setIatas(String zValue) throws Exception {    pIatas.setValue(zValue);  }
  public void setNoIatas(String zValue) throws Exception {    pNoIatas.setValue(zValue);  }
  public void setGDS(String zValue) throws Exception {    pGDS.setValue(zValue);  }
  public void setNoGDS(String zValue) throws Exception {    pNoGDS.setValue(zValue);  }

  public void setGDSAux(String zValue) throws Exception {    pGDSAux.setValue(zValue);  }
  public void setNoGDSAux(String zValue) throws Exception {    pNoGDSAux.setValue(zValue);  }

  public void setGDSBase(String zValue) throws Exception {    pGDSBase.setValue(zValue);  }
  public void setNoGDSBase(String zValue) throws Exception {    pNoGDSBase.setValue(zValue);  }

  public boolean hasInterlineal() throws Exception {   return pInterlineal.isNotNull();  }
  public boolean isInterlineal() throws Exception {   return pInterlineal.isNotNull() && pInterlineal.getValue().equals("S");  }
  public boolean isSoloUna() throws Exception {   return pInterlineal.isNotNull() && !pInterlineal.getValue().equals("S");  }
   public void setInterlineal(boolean zvalue) throws Exception {   pInterlineal.setValue(zvalue);  }
  public boolean hasDomesticInternacional() throws Exception {   return pDomestico.isNotNull();  }
  public boolean isDomestic() throws Exception {   return pDomestico.isNotNull() && pDomestico.getValue().equals("S");  }
  public boolean isInternational() throws Exception {   return pDomestico.isNotNull() && !pDomestico.getValue().equals("S");  }


  public boolean hasTipoViaje() throws Exception {   return pTipoViaje.isNotNull();  }
  public boolean isOpenJaws() throws Exception {   return pTipoViaje.isNotNull() && pTipoViaje.getValue().equals("S");  }
  public boolean isRoundTrip() throws Exception {   return pTipoViaje.isNotNull() && !pTipoViaje.getValue().equals("S");  }
  public void setTipoViaje(boolean zvalue) throws Exception {   pTipoViaje.setValue(zvalue);  }

  public boolean hasMultidestino() throws Exception {   return pMultidestino.isNotNull();  }
  public boolean isMore4Destinos() throws Exception {   return pMultidestino.isNotNull() && pMultidestino.getValue().equals("S");  }
  public boolean isLess4Destinos() throws Exception {   return pMultidestino.isNotNull() && !pMultidestino.getValue().equals("S");  }
  public void setMultidestino(boolean zvalue) throws Exception {   pMultidestino.setValue(zvalue);  }

  public boolean hasStopover() throws Exception {   return pStopover.isNotNull();  }
  public boolean isStopOver() throws Exception {   return pStopover.isNotNull() && pStopover.getValue().equals("S");  }
  public boolean isNoStopOver() throws Exception {   return pStopover.isNotNull() && !pStopover.getValue().equals("S");  }
  public void setStopOver(boolean zvalue) throws Exception {   pStopover.setValue(zvalue);  }

  public boolean hasCambio() throws Exception {   return pCambio.isNotNull();  }
  public boolean isPostDespegue() throws Exception {   return pCambio.isNotNull() && pCambio.getValue().equals("S");  }
  public boolean isPreDespegue() throws Exception {   return pCambio.isNotNull() && !pCambio.getValue().equals("S");  }
  public void setCambio(boolean zvalue) throws Exception {   pCambio.setValue(zvalue);  }

  public boolean hasVueltaMundo() throws Exception {   return pVueltaMundo.isNotNull();  }
  public boolean isVueltaMundo() throws Exception {   return pVueltaMundo.isNotNull() && pVueltaMundo.getValue().equals("S");  }
  public boolean isNoVueltaMundo() throws Exception {   return pVueltaMundo.isNotNull() && !pVueltaMundo.getValue().equals("S");  }
  public void setVueltaMundo(boolean zvalue) throws Exception {   pVueltaMundo.setValue(zvalue);  }


  public boolean isExchange() throws Exception {   return pExchange.isNotNull() && pExchange.getValue();  }
  public void setExchange(boolean zvalue) throws Exception {   pExchange.setValue(zvalue);  }
  public boolean isExchangeBase() throws Exception {   return pExchangeBase.getValue();  }
  public void setExchangeBase(boolean zvalue) throws Exception {   pExchangeBase.setValue(zvalue);  }
  public boolean isExchangeAux() throws Exception {   return pExchangeAux.getValue();  }
  public void setExchangeAux(boolean zvalue) throws Exception {   pExchangeAux.setValue(zvalue);  }

  public boolean hasInterlinealBase() throws Exception {   return pInterlinealBase.isNotNull();  }
  public boolean isInterlinealBase() throws Exception {   return pInterlinealBase.isNotNull() && pInterlinealBase.getValue().equals("S");  }
  public boolean isSoloUnaBase() throws Exception {   return pInterlinealBase.isNotNull() && !pInterlinealBase.getValue().equals("S");  }
  public void setInterlinealBase(boolean zvalue) throws Exception {   pInterlinealBase.setValue(zvalue);  }
  public boolean hasDomesticInternacionalBase() throws Exception {   return pDomesticoBase.isNotNull();  }
  public boolean isDomesticBase() throws Exception {   return pDomesticoBase.isNotNull() && pDomesticoBase.getValue().equals("S");  }
  public boolean isInternationalBase() throws Exception {   return pDomesticoBase.isNotNull() && !pDomesticoBase.getValue().equals("S");  }
  
  public boolean hasInterlinealAux() throws Exception {   return pInterlinealAux.isNotNull();  }
  public boolean isInterlinealAux() throws Exception {   return pInterlinealAux.isNotNull() && pInterlinealAux.getValue().equals("S");  }
  public boolean isSoloUnaAux() throws Exception {   return pInterlinealAux.isNotNull() && !pInterlinealAux.getValue().equals("S");  }
  public void setInterlinealAux(boolean zvalue) throws Exception {   pInterlinealAux.setValue(zvalue);  }
  public boolean hasDomesticInternacionalAux() throws Exception {   return pDomesticoAux.isNotNull();  }
  public boolean isDomesticAux() throws Exception {   return pDomesticoAux.isNotNull() && pDomesticoAux.getValue();  }
  public boolean isInternationalAux() throws Exception {   return pDomesticoAux.isNotNull() && !pDomesticoAux.getValue();  }
  
  public Date getFDesde() throws Exception { return  pFDesde.getValue(); } 
  public void setFDesde(Date value) throws Exception {  pFDesde.setValue(value); } 
  public Date getFHasta() throws Exception { return  hasFechaCalculo()?getFechaCalculo():pFHasta.getValue(); } 
  public void setFHasta(Date value) throws Exception {  pFHasta.setValue(value); } 
  public double getCalculeSigValorObjetivo() throws Exception { 
  	BizNivel nivel = getObjNivelSiguiente();
  	if (nivel==null) return 0;
  	return nivel.getValor(); 
  };   
  public double getValorObjetivoEvento() throws Exception {    
	if (!getObjEvent().isNullValorEmergencia()) return getObjEvent().getValorEmergencia();
  	return getObjEvent().getValorMinimo(); 
  }

  public void calcule(boolean update)  throws Exception {
  }
  
  
	public void buildReports(JAutogenerador autogenerador, int id) throws Exception {
		if (pLinea.isNull()) return;
		BizQueVender extraInfo = new BizQueVender();
		extraInfo.dontThrowException(true);
		extraInfo.read(getCompany(),getId(),getLinea(),id);
		extraInfo.setObjDetalle(this);
		extraInfo.setId(id);
		extraInfo.setDescripcion("Market Share por aerolinea");
		extraInfo.setFieldname1(autogenerador.getOutputField1Description());
		extraInfo.setFieldname2(autogenerador.getOutputField2Description());
		extraInfo.setFieldnameAgrupador1("");
		extraInfo.setFieldnameAgrupador2("Aerolinea");
		extraInfo.setClase(pss.bsp.contrato.quevender.ms.GuiMSs.class.getName());
		extraInfo.setSql(autogenerador.getEstadisticaMSAerolinea());
		extraInfo.setSqlDetalle(autogenerador.getEstadisticaMSAerolineaDetalle());
		extraInfo.setGrafico(1);
		extraInfo.processUpdateOrInsertWithCheck();
		
		BizQueVender extraInfo2 = new BizQueVender();
		extraInfo2.dontThrowException(true);
		extraInfo2.read(getCompany(),getId(),getLinea(),id+1);
		extraInfo2.setObjDetalle(this);
		extraInfo2.setDescripcion("Market Share por ruta");
		extraInfo2.setFieldname1(autogenerador.getOutputField1Description());
		extraInfo2.setFieldname2(autogenerador.getOutputField2Description());
		extraInfo2.setFieldnameAgrupador1("");
		extraInfo2.setFieldnameAgrupador2("Ruta");
		extraInfo2.setClase(pss.bsp.contrato.quevender.ms.GuiMSs.class.getName());
		extraInfo2.setSql(autogenerador.getEstadisticaMSRuta());
		extraInfo2.setSqlDetalle(autogenerador.getEstadisticaMSRutaDetalle());
		extraInfo2.setId(id+1);
		extraInfo2.setGrafico(1);
		extraInfo2.processUpdateOrInsertWithCheck();
		
		BizQueVender extraInfo3 = new BizQueVender();
		extraInfo3.dontThrowException(true);
		extraInfo3.read(getCompany(),getId(),getLinea(),id+2);
		extraInfo3.setObjDetalle(this);
		extraInfo3.setDescripcion("Market Share por clase");
		extraInfo3.setFieldname1(autogenerador.getOutputField1Description());
		extraInfo3.setFieldname2(autogenerador.getOutputField2Description());
		extraInfo3.setFieldnameAgrupador1("");
		extraInfo3.setFieldnameAgrupador2("Clase");
		extraInfo3.setClase(pss.bsp.contrato.quevender.ms.GuiMSs.class.getName());
		extraInfo3.setSql(autogenerador.getEstadisticaMSClase());
		extraInfo3.setSqlDetalle(autogenerador.getEstadisticaMSClaseDetalle());
		extraInfo3.setId(id+2);
		extraInfo3.setGrafico(1);
		extraInfo3.processUpdateOrInsertWithCheck();
		
		BizQueVender extraInfo4 = new BizQueVender();
		extraInfo4.dontThrowException(true);
		extraInfo4.read(getCompany(),getId(),getLinea(),id+3);
		extraInfo4.setObjDetalle(this);
		extraInfo4.setDescripcion("Mapa de rutas");
		extraInfo4.setFieldname1(autogenerador.getOutputField1Description());
		extraInfo4.setFieldname2(autogenerador.getOutputField2Description());
		extraInfo4.setFieldnameAgrupador1("Origen");
		extraInfo4.setFieldnameAgrupador2("Destino");
		extraInfo4.setClase(pss.bsp.contrato.quevender.ms.GuiMSs.class.getName());
		extraInfo4.setSql(autogenerador.getEstadisticaRutas());
		extraInfo4.setSqlDetalle(autogenerador.getEstadisticaRutasDetalle());
		extraInfo4.setId(id+3);
		extraInfo4.setGrafico(2);
		extraInfo4.processUpdateOrInsertWithCheck();
		
		BizQueVender extraInfo5 = new BizQueVender();
		extraInfo5.dontThrowException(true);
		extraInfo5.read(getCompany(),getId(),getLinea(),id+4);
		extraInfo5.setObjDetalle(this);
		extraInfo5.setDescripcion("Evolución diaria");
		extraInfo5.setFieldname1(autogenerador.getOutputField1Description());
		extraInfo5.setFieldname2(autogenerador.getOutputField2Description());
		extraInfo5.setFieldnameAgrupador1("");
		extraInfo5.setFieldnameAgrupador2("día");
		extraInfo5.setClase(pss.bsp.contrato.quevender.ms.GuiMSs.class.getName());
		extraInfo5.setSql(autogenerador.getEstadisticaDiario());
		extraInfo5.setSqlDetalle(autogenerador.getEstadisticaDiarioDetalle());
		extraInfo5.setId(id+4);
		extraInfo5.setGrafico(3);
		extraInfo5.processUpdateOrInsertWithCheck();
		
		BizQueVender extraInfo6 = new BizQueVender();
		extraInfo6.dontThrowException(true);
		extraInfo6.read(getCompany(),getId(),getLinea(),id+5);
		extraInfo6.setObjDetalle(this);
		extraInfo6.setDescripcion("Venta por vendedor en el contrato");
		extraInfo6.setFieldname1(autogenerador.getOutputField1Description());
		extraInfo6.setFieldname2(autogenerador.getOutputField2Description());
		extraInfo6.setFieldnameAgrupador1("");
		extraInfo6.setFieldnameAgrupador2("Vendedor");
		extraInfo6.setClase(pss.bsp.contrato.quevender.ms.GuiMSs.class.getName());
		extraInfo6.setSql(autogenerador.getEstadisticaVendedor());
		extraInfo6.setSqlDetalle(autogenerador.getEstadisticaVendedorDetalle());
		extraInfo6.setId(id+5);
		extraInfo6.setGrafico(1);
		extraInfo6.processUpdateOrInsertWithCheck();
		
		BizQueVender extraInfo7 = new BizQueVender();
		extraInfo7.dontThrowException(true);
		extraInfo7.read(getCompany(),getId(),getLinea(),id+6);
		extraInfo7.setObjDetalle(this);
		extraInfo7.setDescripcion("Venta por DK por segmento");
		extraInfo7.setFieldname1(autogenerador.getOutputField1Description());
		extraInfo7.setFieldname2(autogenerador.getOutputField2Description());
		extraInfo7.setFieldnameAgrupador1("");
		extraInfo7.setFieldnameAgrupador2("Cliente");
		extraInfo7.setClase(pss.bsp.contrato.quevender.ms.GuiMSs.class.getName());
		extraInfo7.setSql(autogenerador.getEstadisticaCliente());
		extraInfo7.setSqlDetalle(autogenerador.getEstadisticaClienteDetalle());
		extraInfo7.setId(id+6);
		extraInfo7.setGrafico(1);
		extraInfo7.processUpdateOrInsertWithCheck();


		BizQueVender extraInfo8 = new BizQueVender();
		extraInfo8.dontThrowException(true);
		extraInfo8.read(getCompany(),getId(),getLinea(),id+7);
		extraInfo8.setObjDetalle(this);
		extraInfo8.setDescripcion("Comparación histórica");
		extraInfo8.setFieldname1(autogenerador.getOutputField1Description());
		extraInfo8.setFieldname2(autogenerador.getOutputField2Description());
		extraInfo8.setFieldnameAgrupador1("año");
		extraInfo8.setFieldnameAgrupador2("Mes-día");
		extraInfo8.setClase(pss.bsp.contrato.quevender.ms.GuiMSs.class.getName());
		extraInfo8.setSql(autogenerador.getEstadisticaHistorico());
		extraInfo8.setSqlDetalle(autogenerador.getEstadisticaHistoricoDetalle());
		extraInfo8.setId(id+7);
		extraInfo8.setGrafico(4);
		extraInfo8.processUpdateOrInsertWithCheck();

		BizQueVender extraInfo9 = new BizQueVender();
		extraInfo9.dontThrowException(true);
		extraInfo9.read(getCompany(),getId(),getLinea(),id+8);
		extraInfo9.setObjDetalle(this);
		extraInfo9.setDescripcion("Venta por DK por boleto");
		extraInfo9.setFieldname1(autogenerador.getOutputField1Description());
		extraInfo9.setFieldname2(autogenerador.getOutputField6Description());
		extraInfo9.setFieldnameAgrupador1("");
		extraInfo9.setFieldnameAgrupador2("Cliente");
		extraInfo9.setClase(pss.bsp.contrato.quevender.ms.GuiMSs.class.getName());
		extraInfo9.setSql(autogenerador.getEstadisticaCliente2());
		extraInfo9.setSqlDetalle(autogenerador.getEstadisticaClienteDetalle2());
		extraInfo9.setId(id+8);
		extraInfo9.setGrafico(1);
		extraInfo9.processUpdateOrInsertWithCheck();

	}
	
//   public double getCalculeValorTotal() throws Exception { 
//  	if (getObjEventGanancia()==null) {
//  		return  pValorActual.getValue();
//  	}
//  	
//  	String company = getCompany();
//		BizInterfazNew news=new BizInterfazNew();
//		news.dontThrowException(true);
//  	Calendar hoy = Calendar.getInstance();
//		if (news.read(company)) {
//			hoy.setTime(news.getLastPnr());
//		}
//		else {
//			hoy.setTime(new Date());
//		}
//  	
//		Calendar desde = Calendar.getInstance();
//		desde.setTime(getObjContrato().getFechaDesde());
//		
//		Calendar hasta = Calendar.getInstance();
//		hasta.setTime(hasFechaCalculo()?getFechaCalculo():getObjContrato().getFechaHasta());
//		
//		if (hoy.before(desde)) //return  0.00;
//			hoy = desde;
//
//		if (hoy.after(hasta))
//			hoy = hasta;
//  	
//	 	BizSqlEventDato dato=  getObjEventGanancia().getValorAnterior(desde,hoy);
//	 	return dato==null?0:dato.getValue();
//     	
//  }
  public double getCalculeValorTotalFinContrato() throws Exception { 
  	if (getObjEventGanancia()==null) {
  		return pValorFinContrato.getValue();
  	}
  	
		Calendar desde = Calendar.getInstance();
		desde.setTime(hasFechaDesdeCalculo()?getFechaDesdeCalculo():getObjContrato().getFechaDesde());
		Calendar hoy = Calendar.getInstance();
		hoy.setTime(hasFechaCalculo()?getFechaCalculo():getObjContrato().getFechaHasta());
  	if (getAcumulativo()) return calculeAcumulado(desde,hoy);
	  if (getObjEvent()!=null && JDateTools.dateEqualOrBefore(getObjEvent().getFechaUpdate(),new Date()) &&
	  		!JDateTools.equalsDate(getObjEvent().getFechaUpdate(),new Date())) {
	  	getObjEvent().processUpdateDatos();
	  }
//		Calendar desde = Calendar.getInstance();
//		desde.setTime(getObjContrato().getFechaDesde());
//		
//		Calendar hasta = Calendar.getInstance();
//		hasta.setTime(hasFechaCalculo()?getFechaCalculo():getObjContrato().getFechaHasta());
//		
	//	if (hoy.before(desde)) return 0.00;
  	if (!getObjEventGanancia().isOK()) {
  		return getObjEventGanancia().getValor(desde,hoy)-getReembolso()-getReembolsoAuto();
  	}
	 	BizSqlEventDato dato=  getObjEventGanancia().getValorAnterior(desde,hoy);
	 	return (dato==null?0:dato.getValue())-getReembolso();

  	
//	 	return getObjEventGanancia().getValor(hoy);
     	
  }
  public double getCalculeValorAuxiliar() throws Exception { 
		Calendar hoy = Calendar.getInstance();
		hoy.setTime(hasFechaCalculo()?getFechaCalculo():getObjContrato().getFechaHasta());
		Calendar desde = Calendar.getInstance();
		desde.setTime(hasFechaDesdeCalculo()?getFechaDesdeCalculo():getObjContrato().getFechaDesde());
  	if (getObjEventAuxiliar()==null) {
  		return pGananciaAuxiliar.getValue();
  	}
  	if (!getObjEventAuxiliar().isOK()) {
   		return getObjEventAuxiliar().getValor(desde,hoy);
   	}
	 	BizSqlEventDato dato=  getObjEventAuxiliar().getValorAnterior(desde,hoy);
	 	return (dato==null?0:dato.getValue());

  	
//	 	return getObjEventGanancia().getValor(hoy);
     	
  }
  
  public String imprimirResumen() throws Exception {
		JRecord[]recs = new JRecord[4];
		recs[0]=BizBSPCompany.getObjBSPCompany(this.getCompany());
		recs[1]=getObjContrato();
		recs[2]=this;
		recs[3]=null;
		
		String tempfile =BizPlantilla.generateListadoTemporario(this.getCompany(),true,recs,"sys_contrato_detalle");
		return tempfile;
	}


	  
  public boolean hasDetalle() throws Exception {
  	return getObjEvent()!=null && !getObjEvent().getConsultaDetalle().equals("");
  }
  public boolean hasDetalleGanancia() throws Exception {
  	return getObjEventGanancia()!=null && !getObjEventGanancia().getConsultaDetalle().equals("");
  }
  public boolean hasDetalleAuxiliar() throws Exception {
  	return getObjEventAuxiliar()!=null && !getObjEventAuxiliar().getConsultaDetalle().equals("");
  }
  public boolean hasDetalleDM() throws Exception {
  	return getObjEvent()!=null && getObjEvent().getObjCustomList()!=null;
  }
  public boolean hasDetalleGananciaDM() throws Exception {
  	return getObjEventGanancia()!=null && getObjEventGanancia().getObjCustomList()!=null;
  }
  public boolean hasDetalleAuxiliarDM() throws Exception {
  	return getObjEventAuxiliar()!=null && getObjEventAuxiliar().getObjCustomList()!=null;
  }
  public JWins getDetalle() throws Exception {
		Calendar hoy = Calendar.getInstance();
		hoy.setTime(new Date());
		Calendar desde = Calendar.getInstance();
		desde.setTime(hasFechaDesdeCalculo()?getFechaDesdeCalculo():getObjContrato().getFechaDesde());
		Calendar hasta = Calendar.getInstance();
		hasta.setTime(hasFechaCalculo()?getFechaCalculo():getObjContrato().getFechaHasta());
		if (hoy.after(hasta)||!isVolado())
			hoy = hasta;
		return getObjEvent().getDetalles(desde,hoy,getObjEvent()!=null?getObjEvent().getCampo():null);
	}

  public String getSQLDetalle() throws Exception {
		Calendar hoy = Calendar.getInstance();
		hoy.setTime(new Date());
		Calendar desde = Calendar.getInstance();
		desde.setTime(hasFechaDesdeCalculo()?getFechaDesdeCalculo():getObjContrato().getFechaDesde());
		Calendar hasta = Calendar.getInstance();
		hasta.setTime(hasFechaCalculo()?getFechaCalculo():getObjContrato().getFechaHasta());
		if (hoy.after(hasta))
			hoy = hasta;
		return getObjEvent().getSQLDetalles(desde,hoy,"");
	}

  public boolean isDataminingRutas() throws Exception {
  	return getLogicaContrato().equals(JContratoUpfrontRuta.class.getName()) ||
  			getLogicaContrato().equals(JContratoBackendRuta.class.getName()) ||
  			getLogicaContrato().equals(JContratoBackendDobleRuta.class.getName());
  }
  public boolean isDatamining() throws Exception {
  	return getLogicaContrato().equals(JContratoNormal.class.getName());
  }
  public boolean isUpfront() throws Exception {
  	return getLogicaContrato().equals(JContratoUpfrontRuta.class.getName()) || getLogicaContrato().equals(JContratoUpfront.class.getName());
  }
  public boolean isDataminingLatamFamilia() throws Exception {
  	return getLogicaContrato().equals(JContratoLatamFamilia.class.getName());
  }
  public boolean isCopa() throws Exception {
  	return getLogicaContrato().equals(JContratoCopa.class.getName());
  }
  public boolean isCopaWS() throws Exception {
  	return getLogicaContrato().equals(JContratoCopaWS.class.getName());
  }
  public boolean isCopaPorRutas() throws Exception {
  	return getLogicaContrato().equals(JContratoCopaPorRutas.class.getName());
  }
 
  public boolean hasTicketInDetalleGanancia(BizPNRTicket ticket) throws Exception {
		Calendar hoy = Calendar.getInstance();
		hoy.setTime(new Date());
		Calendar desde = Calendar.getInstance();
		desde.setTime(getObjContrato().getFechaDesde());
		Calendar hasta = Calendar.getInstance();
		hasta.setTime(getObjContrato().getFechaHasta());

		if (hoy.after(hasta))
			hoy = hasta;
		JWins wins = getObjEventGanancia().getDetalles(desde,hoy,""," and numeroboleto='"+ticket.getNumeroboleto()+"' ");
		wins.readAll();
		wins.toStatic();
		return wins.getRecords().getStaticItems().size()>0;
  }
  public JWins getDetalleGanancia() throws Exception {
		Calendar hoy = Calendar.getInstance();
		hoy.setTime(new Date());
		Calendar desde = Calendar.getInstance();
		desde.setTime(hasFechaDesdeCalculo()?getFechaDesdeCalculo():getObjContrato().getFechaDesde());
		Calendar hasta = Calendar.getInstance();
		hasta.setTime(hasFechaCalculo()?getFechaCalculo():getObjContrato().getFechaHasta());

		if (hoy.after(hasta)||isVoladoBase() )
			hoy = hasta;
		return getObjEventGanancia().getDetalles(desde,hoy,getObjEventGanancia()!=null?getObjEventGanancia().getCampo():null );
	}
  public JWins getDetalleReembolsos() throws Exception {
		Calendar hoy = Calendar.getInstance();
		hoy.setTime(new Date());
		Calendar desde = Calendar.getInstance();
		desde.setTime(hasFechaDesdeCalculo()?getFechaDesdeCalculo():getObjContrato().getFechaDesde());
		Calendar hasta = Calendar.getInstance();
		hasta.setTime(hasFechaCalculo()?getFechaCalculo():getObjContrato().getFechaHasta());

		if (hoy.after(hasta)||isVoladoBase() )
			hoy = hasta;
		GuiReembolsos  reembolsos= new GuiReembolsos();
		reembolsos.getRecords().setStatic(true);
		JWins wins = getDetalleGanancia();
		if (!(wins instanceof GuiPNRTickets)) {
		  	JIterator<BizBooking> it = wins.getRecords().getStaticIterator();
		  	while (it.hasMoreElements()) {
		  		BizBooking pnr =it.nextElement();
		  		BizReembolso objReem = new BizReembolso();
		  		objReem.dontThrowException(true);
		  		if (!objReem.readByBoletoRfnd(pnr.getCompany(),pnr.getNumeroBoleto())) continue;
		  		reembolsos.getRecords().addItem(objReem);
		  	}
		  	
				return reembolsos;
			}
	  	JIterator<BizPNRTicket> it = wins.getRecords().getStaticIterator();
	  	while (it.hasMoreElements()) {
	  		BizPNRTicket pnr =it.nextElement();
	  		BizReembolso objReem = new BizReembolso();
	  		objReem.dontThrowException(true);
	  		if (!objReem.readByBoletoRfnd(pnr.getCompany(),pnr.getNumeroboleto())) continue;
	  		reembolsos.getRecords().addItem(objReem);
	  		
	  	}
			return reembolsos;
  
	   
  
  
  }
  public JWins getDetalleAuxiliar() throws Exception {
		Calendar hoy = Calendar.getInstance();
		hoy.setTime(new Date());
		Calendar desde = Calendar.getInstance();
		desde.setTime(hasFechaDesdeCalculo()?getFechaDesdeCalculo():getObjContrato().getFechaDesde());
		Calendar hasta = Calendar.getInstance();
		hasta.setTime(hasFechaCalculo()?getFechaCalculo():getObjContrato().getFechaHasta());

		if (hoy.after(hasta)||isVoladoAux())
			hoy = hasta;
		return getObjEventAuxiliar().getDetalles(desde,hoy,getObjEventAuxiliar()!=null?getObjEventAuxiliar().getCampo():null);
	}

  
  
  public Date getFechaActual() throws Exception {
  	String company = getCompany();
		BizInterfazNew news=new BizInterfazNew();
		news.dontThrowException(true);
  	Calendar hoy = Calendar.getInstance();
		if (news.read(company)) {
			hoy.setTime(news.getLastPnr());
		}
		else {
			hoy.setTime(new Date());
		}
		Calendar desde = Calendar.getInstance();
		desde.setTime(getObjContrato().getFechaDesde());
		
		Calendar hasta = Calendar.getInstance();
		hasta.setTime(hasFechaCalculo()?getFechaCalculo():getObjContrato().getFechaHasta());
		
		if (hoy.before(desde)) //return  0.00;
			hoy = desde;

		if (hoy.after(hasta))
			hoy = hasta;

		return hoy.getTime();
  }
  
  public double getCalculeValorObjetivo() throws Exception { 
  	throw new Exception("NO IMPLEMENTADO");
  }

  public double getCalculeValorActual() throws Exception { 
  	throw new Exception("NO IMPLEMENTADO");
  }
  public double getCalculeValorAnoAnterior() throws Exception { 
  	return 0;
  }  
  public double getCalculeValorFinContrato() throws Exception { 
  	throw new Exception("NO IMPLEMENTADO");
  }
 
//  public String getCalculeNivelAlcanzado() throws Exception { 
//  	double val = pValorTotal.getValue();
//  	double valor = pValorActual.getValue();
//  	BizNivel nivel = getObjNivelAlcanzado(valor);
//  	if (nivel==null) return "Ninguno";
//  	return nivel.getValorPerc(val, valor);
//  } 
  public double getCalculePorcPorBoleto(double baseComisionable) throws Exception { 
  	double val = baseComisionable;
   	double valor = pValorFinContrato.getValue();
    BizNivel nivel = getObjNivelAlcanzado(valor);
  	if (nivel==null) return 0;
   	return nivel.getValorPercDouble(val, valor);
  }
  public double getCalculeGananciaPorBoleto(double baseComisionable, double porc) throws Exception { 
  	double val = baseComisionable;
  	double valor = pValorFinContrato.getValue();
  	BizNivel nivel = getObjNivelAlcanzado(valor);
  	if (nivel==null) return 0.0;
  	return nivel.getValorPremio(val,valor); 
  }
  public String getCalculeNivelAlcanzado() throws Exception { 
  	double val = pValorTotalFinContrato.getValue();
   	double valor = pValorFinContrato.getValue();
    BizNivel nivel = getObjNivelAlcanzado(valor);
  	if (nivel==null && getObjContrato().isContrato()) return "Ninguno";
  	if (nivel==null) nivel = getFirstNivel();
  	if (nivel==null) return "Ninguno";
  	return nivel.getValorPerc(val, valor);
  }  
  public BizNivel getObjCalculeNivelAlcanzado() throws Exception { 
  	double val = pValorTotalFinContrato.getValue();
   	double valor = pValorFinContrato.getValue();
    BizNivel nivel = getObjNivelAlcanzado(valor);
  	if (nivel==null) return null;
   	return nivel;
  }  
//  public double getCalculeGanancia() throws Exception { 
//  	double val = pValorTotal.getValue();
//  	double valor =  pValorActual.getValue();
//  	BizNivel nivel = getObjNivelAlcanzado(valor);
//  	if (nivel==null) return 0.0;
//  	return nivel.getValorPremio(val,valor); 
//  }
  public double getCalculeGanancia() throws Exception { 
  	double val = pValorTotalFinContrato.getValue();
  	double valor = pValorFinContrato.getValue();
  	BizNivel nivel = getObjNivelAlcanzado(valor);
  	if (nivel==null) return 0.0;
  	return nivel.getValorPremio(val,valor); 
  }
  public double getCalculeAuxiliar() throws Exception { 
  	return getCalculeValorAuxiliar(); 
  }

  public BizNivel getObjNivelAlcanzado(double val) throws Exception {
   	JRecords<BizNivel> n = new JRecords<BizNivel>(BizNivel.class);
  	n.addFilter("company", getCompany());
  	n.addFilter("id", getId());
  	n.addFilter("linea", getLinea());
  	n.addFilter("valor", val).setOperator("<=");
  	n.addOrderBy("valor","DESC");
  	n.setTop(1);
  	JIterator<BizNivel> it = n.getStaticIterator();
  	if (!it.hasMoreElements()) return null;
  	return it.nextElement();
  	
  }
//  public double getNivelCumplimiento() throws Exception {  
//  	if (pValorObjetivo.getValue()==0) return 100;// el objetivo en 0 es un tema, falta pensar un poco mas
//  	double val= (pValorActual.getValue()*100)/pValorObjetivo.getValue();
//  	return val>100?100:val;
//  }
  public double getNivelCumplimiento() throws Exception {  
  	double objetivo=pSigValorObjetivo.getValue();
  	if (objetivo==0) {
  		objetivo=pValorObjetivo.getValue();
  		if (objetivo==0) return 100;// el objetivo en 0 es un tema, falta pensar un poco mas
  	}
  	double val= (pValorFinContrato.getValue()*100)/objetivo;
  	return val>100?100:val;
  }
  
  

	public double getValorTotalContratoCalc() throws Exception {
			return getCalculeGanancia() ;
	}

	public double getGananciaEstimadaCalc() throws Exception {
		return getValorTotalContratoCalc() * (getNivelAlcanzadoEstimadoCalc() / 100) ;
	}

	public double getNivelAlcanzadoEstimadoCalc() throws Exception {  
  	BizProrrateo pror = new BizProrrateo();
  	pror.dontThrowException(true);
  	
  	pror.addFilter("company", BizBSPCompany.getObjBSPCompany(getCompany()).getParentCompany());
  	pror.addFilter("detalle", getLinea());
  	pror.addFilter("cliente", BizBSPCompany.getObjBSPCompany(getCompany()).getCodigoCliente(),"like");
  	if (pror.read()) 
  		return pror.getComision();
  	
  	pror.clearFilters();
	 	pror.addFilter("company", BizBSPCompany.getObjBSPCompany(getCompany()).getParentCompany());
  	pror.addFilter("detalle", getLinea());
  	pror.addFilter("cliente", "ALL");
  	if (pror.read()) 
  		return pror.getComision();
  	  
  	return 0.0;
  }
  public String getTipoContrato() throws Exception {  
  	return getLogicaContrato().indexOf("Upfront")==-1?"Backend":"Upfront";
  }
  public String getDetalleNivelCumplimiento() throws Exception {  
  	return new JFloat(getNivelCumplimiento()).setPrec(2).toFormattedString()+"%";
  }
  public boolean isNullValor() throws Exception { return  pValor.isNull(); } 
  public void setNullToValor() throws Exception {  pValor.setNull(); } 
  public void setVariable(long zValue) throws Exception {    pVariable.setValue(zValue);  }
  public long getVariable() throws Exception {     return pVariable.getValue();  }
  public boolean isNullVariable() throws Exception { return  pVariable.isNull(); } 
  public void setNullToVariable() throws Exception {  pVariable.setNull(); } 
  public void setVariableAuxiliar(long zValue) throws Exception {    pVariableAuxiliar.setValue(zValue);  }
  public long getVariableAuxiliar() throws Exception {     return pVariableAuxiliar.getValue();  }
  public boolean isNullVariableAuxiliar() throws Exception { return  pVariableAuxiliar.isNull(); } 
  public void setNullToVariableAuxiliar() throws Exception {  pVariableAuxiliar.setNull(); } 
  public void setNullToVariableGanancia() throws Exception {  pVariableGanancia.setNull(); } 
  public void setCompany(String zValue) throws Exception {    pCompany.setValue(zValue);  }
  public String getCompany() throws Exception {     return pCompany.getValue();  }
  public boolean isNullCompany() throws Exception { return  pCompany.isNull(); } 
  public void setNullToCompany() throws Exception {  pCompany.setNull(); } 
  public void setDescripcion(String zValue) throws Exception {    pDescripcion.setValue(zValue);  }
  public String getDescripcion() throws Exception {     return pDescripcion.getValue();  }
  public boolean isNullDescripcion() throws Exception { return  pDescripcion.isNull(); } 
  public void setNullToDescripcion() throws Exception {  pDescripcion.setNull(); } 
  public void setId(long zValue) throws Exception {    pId.setValue(zValue);  }
  public long getId() throws Exception {     return pId.getValue();  }
  public boolean isNullId() throws Exception { return  pId.isNull(); } 
  public void setNullToId() throws Exception {  pId.setNull(); } 
  public void setModelo(long zValue) throws Exception {    pModelo.setValue(zValue);  }
  public long getModelo() throws Exception {     return pModelo.getValue();  }
  public void setKicker(boolean zValue) throws Exception {    pKicker.setValue(zValue);  }
  public boolean getKicker() throws Exception {     return pKicker.getValue();  }
  public void setGrupo(String zValue) throws Exception {    pGrupo.setValue(zValue);  }
  public String getGrupo() throws Exception {     return pGrupo.getValue();  }
  public void setAutogenerado(String zValue) throws Exception {    pAutogenerado.setValue(zValue);  }
  public String getAutogenerado() throws Exception {     return pAutogenerado.getValue();  }
  public boolean isAutogenerado() throws Exception {     return pAutogenerado.isNotNull() && !pAutogenerado.getValue().equals("false")&& !pAutogenerado.getValue().equals("N");  }
  public void setIdemAnterior(boolean zValue) throws Exception {    pIdemAnterior.setValue(zValue);  }
  public boolean getIdemAnterior() throws Exception {     return pIdemAnterior.getValue();  }
  public void setDefaultTipoPremio(String zValue) throws Exception {    pDefaultTipoPremio.setValue(zValue);  }
  public void setPrioridad(long zValue) throws Exception {    pPrioridad.setValue(zValue);  }
  public long getPrioridad() throws Exception {     return pPrioridad.getValue();  }
  public boolean isNullPrioridad() throws Exception {   return pPrioridad.isNull();  }
  public String getDefaultTipoPremio() throws Exception {     
  	if (pDefaultTipoPremio.isRawNull()) {
  		BizNivel n = getFirstNivel();
  		if ( n!=null )
  			return n.getTipoPremio();
  	}
  	return pDefaultTipoPremio.getValue();  
  }
  public void setDefaultTipoObjetivo(String zValue) throws Exception {    pDefaultTipoObjetivo.setValue(zValue);  }
  public String getDefaultTipoObjetivo() throws Exception {
  	if (pDefaultTipoObjetivo.isRawNull()) {
  		BizNivel n = getFirstNivel();
  		if ( n!=null )
  			return n.getTipoObjetivo();
  	}
  	return pDefaultTipoObjetivo.getValue();  
  }
  
  public void setVariableGanancia(long zValue) throws Exception {    pVariableGanancia.setValue(zValue);  }
  public long getVariableGanancia() throws Exception {  
  	if (pVariableGanancia.isNull()) return pVariable.getValue();
  	return pVariableGanancia.getValue();  
  }
  public boolean isNullModelo() throws Exception { return  pModelo.isNull(); } 
  public void setNullToModelo() throws Exception {  pModelo.setNull(); } 
  public void setAcumulativo(boolean zValue) throws Exception {    pAcumulativo.setValue(zValue);  }
  public boolean getAcumulativo() throws Exception {     return pAcumulativo.getValue();  }
  public void setPax(boolean zValue) throws Exception {    pPax.setValue(zValue);  }
  public boolean getPax() throws Exception {     return pPax.getValue();  }
  public void setPaxAux(boolean zValue) throws Exception {    pPaxAux.setValue(zValue);  }
  public boolean getPaxAux() throws Exception {     return pPaxAux.getValue();  }
  public void setPaxBase(boolean zValue) throws Exception {    pPaxBase.setValue(zValue);  }
  public boolean getPaxBase() throws Exception {     return pPaxBase.getValue();  }
  public void setViceversa(boolean zValue) throws Exception {    pViceversa.setValue(zValue);  }
  public boolean isViceversa() throws Exception {     return pViceversa.getValue();  }
  public void setViceversaBase(boolean zValue) throws Exception {    pViceversaBase.setValue(zValue);  }
  public boolean isViceversaBase() throws Exception {     return pViceversaBase.getValue();  }
  public void setViceversaAux(boolean zValue) throws Exception {    pViceversaAux.setValue(zValue);  }
  public boolean isViceversaAux() throws Exception {     return pViceversaAux.getValue();  }
  public void setPrimera(boolean zValue) throws Exception {    pPrimera.setValue(zValue);  }
  public boolean hasPrimera() throws Exception {   return pPrimera.isNotNull();  }
  public boolean isPrimera() throws Exception {   return pPrimera.isNotNull() && pPrimera.getValue().equals("S");  }
  public boolean isNoPrimera() throws Exception {   return pPrimera.isNotNull() && !pPrimera.getValue().equals("S");  }
 
  public void setPrimeraBase(boolean zValue) throws Exception {    pPrimeraBase.setValue(zValue);  }
  public boolean hasPrimeraBase() throws Exception {   return pPrimeraBase.isNotNull();  }
  public boolean isPrimeraBase() throws Exception {   return pPrimeraBase.isNotNull() && pPrimeraBase.getValue().equals("S");  }
  public boolean isNoPrimeraBase() throws Exception {   return pPrimeraBase.isNotNull() && !pPrimeraBase.getValue().equals("S");  }
 
  public void setPrimeraAux(boolean zValue) throws Exception {    pPrimeraAux.setValue(zValue);  }
  public boolean hasPrimeraAux() throws Exception {   return pPrimeraAux.isNotNull();  }
  public boolean isPrimeraAux() throws Exception {   return pPrimeraAux.isNotNull() && pPrimeraAux.getValue().equals("S");  }
  public boolean isNoPrimeraAux() throws Exception {   return pPrimeraAux.isNotNull() && !pPrimeraAux.getValue().equals("S");  }
 
  public void setAndOr(boolean zValue) throws Exception {    pAndOr.setValue(zValue);  }
  public boolean isAnd() throws Exception {   return pAndOr.isNotNull() && !pAndOr.getValue().equals("S");  }
  public boolean isOr() throws Exception {   return pAndOr.isNotNull() && pAndOr.getValue().equals("S");  }
 
  public void setAndOrBase(boolean zValue) throws Exception {    pAndOrBase.setValue(zValue);  }
  public boolean isAndBase() throws Exception {   return pAndOrBase.isNotNull() && !pAndOrBase.getValue().equals("S");  }
  public boolean isOrBase() throws Exception {   return pAndOrBase.isNotNull() && pAndOrBase.getValue().equals("S");  }
  
  public void setAndOrAux(boolean zValue) throws Exception {    pAndOrAux.setValue(zValue);  }
  public boolean isAndAux() throws Exception {   return pAndOrAux.isNotNull() && !pAndOrAux.getValue().equals("S");  }
  public boolean isOrAux() throws Exception {   return pAndOrAux.isNotNull() && pAndOrAux.getValue().equals("S");  }

  public void setMS(String zValue) throws Exception {    pMS.setValue(zValue);  }
  public String getMS() throws Exception {    return pMS.getValue();  }
  public void setMSBase(String zValue) throws Exception {    pMSBase.setValue(zValue);  }
  public String getMSBase() throws Exception {    return pMSBase.getValue();  }
  public boolean isPorcentajeSobreElTotalBase() throws Exception {     return pMSBase.getValue().equals("P");  }
  public boolean isPorcentajeSoloIdaSobreElTotalBase() throws Exception {     return pMSBase.getValue().equals("S");  }
  public boolean isPorcentajeSobreExcluidosBase() throws Exception {     return pMSBase.getValue().equals("I");  }
  public boolean isPorcentajeSobreGDSIncluidosBase() throws Exception {     return pMSBase.getValue().equals("G");  }
  public boolean isPaxBase() throws Exception {     return pMSBase.getValue().equals("X");  }
  public boolean isMSTarifaBase() throws Exception {     return pMSBase.getValue().equals("J");  }
  public boolean isMSCantidadBase() throws Exception {     return pMSBase.getValue().equals("C");  }
  public boolean isTarifaBase() throws Exception {     return pMSBase.getValue().equals("N");  }
  public boolean isTarifaBrutaBase() throws Exception {     return pMSBase.getValue().equals("B");  }
  public boolean isTarifaNetaSoloIda() throws Exception {     return pMSBase.getValue().equals("H");  }

  public boolean isPorcentajeSobreElTotal() throws Exception {     return pMS.getValue().equals("P");  }
  public boolean isPorcentajeSoloIdaSobreElTotal() throws Exception {     return pMS.getValue().equals("S");  }
  public boolean isPorcentajeSobreExcluidos() throws Exception {     return pMS.getValue().equals("I");  }
  public boolean isPorcentajeSobreGDSIncluidos() throws Exception {     return pMS.getValue().equals("G");  }
  public boolean isPuntos() throws Exception {     return !pPax.getValue();  }
  public boolean isPax() throws Exception {     return pMS.getValue().equals("X");  }
  public boolean isMSTarifa() throws Exception {     return pMS.getValue().equals("J");  }
  public boolean isMSCantidad() throws Exception {     return pMS.getValue().equals("C");  }
  public boolean isTarifa() throws Exception {     return pMS.getValue().equals("N");  }
  public boolean isTarifaBruta() throws Exception {     return pMS.getValue().equals("B");  }
  public void setMSAux(String zValue) throws Exception {    pMSAux.setValue(zValue);  }
  public String getMSAux() throws Exception {    return pMSAux.getValue();  }
  public boolean isPorcentajeSobreElTotalAux() throws Exception {     return pMSAux.getValue().equals("P");  }
  public boolean isPorcentajeSoloIdaSobreElTotalAux() throws Exception {     return pMSAux.getValue().equals("S");  }
  public boolean isPorcentajeSobreExcluidosAux() throws Exception {     return pMSAux.getValue().equals("I");  }
  public boolean isPorcentajeSobreGDSIncluidosAux() throws Exception {     return pMSAux.getValue().equals("G");  }
   public boolean isMSTarifaAux() throws Exception {     return pMSAux.getValue().equals("J");  }
  public boolean isMSCantidadAux() throws Exception {     return pMSAux.getValue().equals("C");  }
  public boolean isPaxAux() throws Exception {     return pMSAux.getValue().equals("X");  }
  public boolean isTarifaAux() throws Exception {     return pMSAux.getValue().equals("N");  }
  public boolean isTarifaBrutaAux() throws Exception {     return pMSAux.getValue().equals("B");  }
  public void setDolares(boolean zValue) throws Exception {    pDolares.setValue(zValue);  }
  public boolean isDolares() throws Exception {     return pDolares.getValue();  }
  public void setYQ(boolean zValue) throws Exception {    pYQ.setValue(zValue);  }
  public boolean isYQ() throws Exception {     return pYQ.getValue();  }
  public void setOrigenNegado(boolean zValue) throws Exception {    pOrigenNegado.setValue(zValue);  }
  public boolean isOrigenNegado() throws Exception {     return pOrigenNegado.getValue();  }
  public void setDestinoNegado(boolean zValue) throws Exception {    pDestinoNegado.setValue(zValue);  }
  public boolean isDestinoNegado() throws Exception {     return pDestinoNegado.getValue();  }
  public void setVolado(boolean zValue) throws Exception {    pVolado.setValue(zValue);  }
  public boolean isVolado() throws Exception {     return pVolado.getValue().equals("S");  }
  public boolean isVoladoEmitido() throws Exception {     return pVolado.isNull();  }
  public void setPeriodo(String zValue) throws Exception {    pPeriodo.setValue(zValue);  }
  public String getPeriodo() throws Exception {     return pPeriodo.getValue();  }
  public String getPeriodoDetalle() throws Exception {     
  	return JDateTools.formatFechaMediana(pFDesde.getValue())+" al "+JDateTools.formatFechaMediana(pFHasta.getValue());  
  }
  public void setFechaEmisionDesde(Date zValue) throws Exception {    pFechaEmisionDesde.setValue(zValue);  }
  public Date getFechaEmisionDesde() throws Exception {     return pFechaEmisionDesde.getValue();  }
  public boolean isNullFechaEmisionDesde() throws Exception { return  pFechaEmisionDesde.isNull(); } 
  public void setNullToFechaEmisionDesde() throws Exception {  pFechaEmisionDesde.setNull(); } 

  public void setFechaEmisionHasta(Date zValue) throws Exception {    pFechaEmisionHasta.setValue(zValue);  }
  public Date getFechaEmisionHasta() throws Exception {     return pFechaEmisionHasta.getValue();  }
  public boolean isNullFechaEmisionHasta() throws Exception { return  pFechaEmisionHasta.isNull(); } 
  public void setNullToFechaEmisionHasta() throws Exception {  pFechaEmisionHasta.setNull(); } 
  
  public void setFechaVueloDesde(Date zValue) throws Exception {    pFechaVueloDesde.setValue(zValue);  }
  public Date getFechaVueloDesde() throws Exception {     return pFechaVueloDesde.getValue();  }
  public boolean isNullFechaVueloDesde() throws Exception { return  pFechaVueloDesde.isNull(); } 
  public void setNullToFechaVueloDesde() throws Exception {  pFechaVueloDesde.setNull(); } 

  public void setFechaVueloHasta(Date zValue) throws Exception {    pFechaVueloHasta.setValue(zValue);  }
  public Date getFechaVueloHasta() throws Exception {     return pFechaVueloHasta.getValue();  }
  public boolean isNullFechaVueloHasta() throws Exception { return  pFechaVueloHasta.isNull(); } 
  public void setNullToFechaVueloHasta() throws Exception {  pFechaVueloHasta.setNull(); } 

  public void setFechaBlockoutDesde(Date zValue) throws Exception {    pFechaBlockoutDesde.setValue(zValue);  }
  public Date getFechaBlockoutDesde() throws Exception {     return pFechaBlockoutDesde.getValue();  }
  public boolean isNullFechaBlockoutDesde() throws Exception { return  pFechaBlockoutDesde.isNull(); } 
  public void setNullToFechaBlockoutDesde() throws Exception {  pFechaBlockoutDesde.setNull(); } 

  public void setFechaBlockoutHasta(Date zValue) throws Exception {    pFechaBlockoutHasta.setValue(zValue);  }
  public Date getFechaBlockoutHasta() throws Exception {     return pFechaBlockoutHasta.getValue();  }
  public boolean isNullFechaBlockoutHasta() throws Exception { return  pFechaBlockoutHasta.isNull(); } 
  public void setNullToFechaBlockoutHasta() throws Exception {  pFechaBlockoutHasta.setNull(); } 

  public void setSepFecha(long zValue) throws Exception {    pSepFecha.setValue(zValue);  }
  
  
  BizObjetivosGrillas objGrillas;
  public JRecords<BizObjetivosGrilla> getObjGrillas() throws Exception {
  	if (objGrillas!=null) return objGrillas;
	  GuiObjetivosGrillas	c = new GuiObjetivosGrillas();
		c.getRecords().addFilter("company", getCompany());
		c.getRecords().addFilter("contrato", getId());
		c.getRecords().addFilter("detalle", getLinea());
		c.getRecords().readAll();
		return objGrillas=c.getcRecords();
	}
  BizObjetivosCopas objCopas;
  public JRecords<BizObjetivosCopa> getObjCopas() throws Exception {
  	if (objCopas!=null) return objCopas;
	  GuiObjetivosCopas	c = new GuiObjetivosCopas();
		c.getRecords().addFilter("company", getCompany());
		c.getRecords().addFilter("contrato", getId());
		c.getRecords().addFilter("detalle", getLinea());
		c.getRecords().addFilter("mensaje",BizObjetivosCopa.SOLO_CUMPLE);
		
		c.getRecords().readAll();
		return objCopas=c.getcRecords();
	}
  BizObjetivosRutas objRutas;
  public JRecords<BizObjetivosRuta> getObjObjetivosRutas() throws Exception {
  	if (objRutas!=null) return objRutas;
  	GuiObjetivosRutas	c = new GuiObjetivosRutas();
	  c.getRecords().addFilter("company", getCompany());
		c.getRecords().addFilter("contrato", getId());
		c.getRecords().addFilter("detalle", getLinea());
		
		c.getRecords().readAll();
		return objRutas=c.getcRecords();
	}
  JRecords<BizObjetivosCopaPorRutas> objCopaRutas;
  public JRecords<BizObjetivosCopaPorRutas> getObjCopaRutas() throws Exception {
  	if (objCopaRutas!=null) return objCopaRutas;
	  GuiObjetivosCopasPorRutas	c = new GuiObjetivosCopasPorRutas();
		c.getRecords().addFilter("company", getCompany());
		c.getRecords().addFilter("contrato", getId());
		c.getRecords().addFilter("detalle", getLinea());
		c.getRecords().readAll();
		return objCopaRutas=c.getcRecords();
	}
  JRecords<BizObjetivosCopaWS> objCopaRutasWS;
  public JRecords<BizObjetivosCopaWS> getObjCopaRutasWS() throws Exception {
  	if (objCopaRutasWS!=null) return objCopaRutasWS;
	  GuiObjetivosCopasWS	c = new GuiObjetivosCopasWS();
		c.getRecords().addFilter("company", getCompany());
		c.getRecords().addFilter("contrato", getId());
		c.getRecords().addFilter("detalle", getLinea());
		c.getRecords().readAll();
		return objCopaRutasWS=c.getcRecords();
	}
  public BizNivel getFirstNivel() throws Exception {
  	if (getObjNiveles()==null)  return null;
  	JIterator<BizNivel> it = getObjNiveles().getStaticIterator();
  	if (!it.hasMoreElements()) return null;
  	return it.nextElement();
	}
  JRecords<BizNivel> objNiveles;
  public JRecords<BizNivel> getObjNiveles() throws Exception {
  	if (objNiveles!=null) return objNiveles;
	  JRecords<BizNivel> records = new JRecords<BizNivel>(BizNivel.class);
		records.addFilter("company", this.getCompany());
		records.addFilter("id", this.getId());
		records.addFilter("linea", this.getLinea());
		records.addOrderBy("id_nivel","ASC");
		return objNiveles= records;
	}
  public String getHtmlData() throws Exception {
  	String s="";
  	s+="<table width=\"100%\">";
  	s+="<tr>";
  	s+="<td style=\"visibility: visible; height: 15px; white-space: nowrap; text-align: left; color: rgb(0, 0, 0); background: none rgba(0, 0, 0, 0);\"><font size=\"2\">"+JTools.toPrinteableNumber(getSigValorObjetivo(),2)+"</font></td>";
  	s+="<td style=\"visibility: visible; height: 15px; white-space: nowrap; text-align: right; color: rgb(0, 200, 0); background: none rgba(0, 0, 0, 0);\"><font size=\"2\">"+getNivelAlcanzado()+"</font></td>";
  	s+="</tr>";
  	s+="<tr>";
  	s+="<td style=\"visibility: visible; height: 15px; white-space: nowrap; text-align: left; color: rgb(0, 0, 0); background: none rgba(0, 0, 0, 0);\"><font size=\"2\">"+JTools.toPrinteableNumber(getValorActual(),2)+"</font></td>";
  	s+="<td style=\"visibility: visible; height: 15px; white-space: nowrap; text-align: right; color: rgb(0, 0, 200); background: none rgba(0, 0, 0, 0);\"><font size=\"2\">"+JTools.toPrinteableNumber(getGanancia(),2)+"</font></td>";
  	s+="</tr>";
  	s+="</table>";
  	return s;
  }
 
  public String getHtmlTitulo() throws Exception {
  	String s="";
  	s+="<table width=\"100%\">";
  	s+="<tr>";
  	s+="<td style=\"visibility: visible; height: 15px; white-space: nowrap; text-align: left; color: rgb(255, 255, 255); background: none rgba(0, 0, 0, 0);\"><font size=\"2\">Sig.Objetivo</font></td>";
  	s+="<td style=\"visibility: visible; height: 15px; white-space: nowrap; text-align: right; color: rgb(0, 200, 0); background: none rgba(0, 0, 0, 0);\"><font size=\"2\">Nivel</font></td>";
  	s+="</tr>";
  	s+="<tr>";
  	s+="<td style=\"visibility: visible; height: 15px; white-space: nowrap; text-align: left; color: rgb(255, 255, 255); background: none rgba(0, 0, 0, 0);\"><font size=\"2\">Valor</font></td>";
  	s+="<td style=\"visibility: visible; height: 15px; white-space: nowrap; text-align: right; color: rgb(125, 128, 200); background: none rgba(0, 0, 0, 0);\"><font size=\"2\">Ganancia</font></td>";
  	s+="</tr>";
  	s+="</table>";
  	return s;
  }
	public String getImagen1(boolean printerVersion) throws Exception {
		GuiSerieCalculadas w = new GuiSerieCalculadas();
	  Date fdesde = getFDesde();
	  Date fhasta = hasFechaCalculo()?getFechaCalculo():getFHasta();
	  if (fdesde==null) fdesde=getObjContrato().getFechaDesde();
	  if (fhasta==null) fhasta=getObjContrato().getFechaHasta();
  	if (getObjEvent()==null) return null;
  	if (!getObjEvent().isOK()) return "<div class=\"panel panel-default\" style=\"height: 100%;width: 100%; text-align: center;\"><B>Aguardando recalculo del indicador..<br/>Estado actual: "+getObjEvent().getEstado()+"</B></div>";

		String sql="";
		
		sql+=" select * from bsp_serie_calculada ";
		sql+=" where modelo = "+getModelo()+" and variable="+getVariable()+" and company='"+getCompany()+"' ";
		sql+=" and fecha between '"+fdesde+"' and '"+fhasta+"' ";
//		sql+=" select company,max(fecha) as fecha,0 as id,modelo, 0 as mes , dia as dia, 0 as anio, max(valor_ant) as valor_ant, max(valor) as valor, max(valor_estimado) as valor_estimado,max(acumulado) as acumulado, variable from bsp_serie_calculada ";
//		sql+=" where modelo = "+getModelo()+" and variable="+getVariable()+" and company='"+getCompany()+"' ";
//		sql+=" and fecha between '"+fdesde+"' and '"+fhasta+"' ";
// 		sql+=" group by company,modelo,variable, dia ";
		sql+=" order by  fecha ";
		w.getRecords().SetSQL(sql);
		JWinList wl = new JWinList(w);
		w.AddColumnasDefault(wl);
		//Graph gr = new GraphXYLine();
		GraphScriptSerieTemporal gr = new GraphScriptSerieTemporal();
		
		gr.addAtributtes(Graph.GRAPH_ATTR_ROTATENAMES, "1");
		gr.addAtributtes(Graph.GRAPH_ATTR_DECIMALPRECISON, "2");
		gr.addAtributtes(Graph.GRAPH_ATTR_NUMBERPREFIX, "");
		gr.addAtributtes(Graph.GRAPH_ATTR_EXPORTENABLED, "1");
		gr.addAtributtes(Graph.GRAPH_ATTR_XAXISNAME, "Tiempo");
		gr.setWithFocus(!printerVersion);
		gr.addAtributtes(Graph.GRAPH_ATTR_YAXISNAME, getObjEvent().getDescripcion());
		gr.setTitle("Analisis "+getObjEvent().getDescripcion());
//		gr.setImage("html/images/fondochart.png");
		ModelGrid mg=new ModelGrid();
		
		mg.addColumn("dia", ModelGrid.GRAPH_FUNCTION_CATEGORIE);
		mg.addColumn(new Dataset("Datos actuales"),"valor", ModelGrid.GRAPH_FUNCTION_VALUE);
		mg.addColumn(new Dataset("Estimacion"),"valor_estimado", ModelGrid.GRAPH_FUNCTION_VALUE);
		mg.addColumn(new Dataset("año anterior"),"valor_ant", ModelGrid.GRAPH_FUNCTION_VALUE);
		if (getAcumulativo())
			mg.addColumn(new Dataset("Acumulado"),"acumulado", ModelGrid.GRAPH_FUNCTION_VALUE);
//		else
//			mg.addColumn(new Dataset("Acumulado"),"acumulado", ModelGrid.GRAPH_FUNCTION_VALUE);
		gr.setModel(mg);
		gr.clearRefs();
		JRecords<BizNivel> niveles = getObjNiveles();
		JIterator<BizNivel> it = niveles.getStaticIterator();
		while (it.hasMoreElements()) {
			BizNivel nivel = it.nextElement();
			gr.addRef(nivel.getDescripcion(), nivel.getValor(), "#"+nivel.getColor());
		}
//		gr.setMax(getValorObjetivo());
//		gr.setLeyendaMax("Valor Objetivo");
//		gr.setColorMax("#00ff00");
  	wl.addGrafico(gr);
    w.ConfigurarFiltrosLista(wl);
    
		Graph g=wl.getGrafico(1);
		if (g!=null) {
			g.localFill(wl,null,null);
			g.setRefresh(1);
			return g.getImage(615, 552).replace("script:", "");
		}
		return null;
	}
	public String getImagenGanancia(boolean printerVersion) throws Exception {
		GuiVirtualSeries w = new GuiVirtualSeries();
	  String sSql;
	  Date fdesde = getFDesde();
	  Date fhasta = getFHasta();
	  if (fdesde==null) fdesde=JDateTools.getFirstDayOfYear(new Date());
	  if (fhasta==null) fhasta=JDateTools.getDateEndDay(new Date());
	  
	 	sSql ="SELECT '"+getDescripcion()+"' as descripcion,evt_sqleventos_datos.value as valor, fecha  ";
	 	sSql+=" FROM evt_sqleventos_datos where id_evento= "+getVariableGanancia()+ " and ";
		sSql+=" fecha between '"+fdesde+"' and '"+fhasta+"' ";
   	sSql+=" order by fecha ";
	  w.getRecords().SetSQL(sSql);
	  
		JWinList wl = new JWinList(w);

		wl.AddColumnaLista("descripcion");
		wl.AddColumnaLista("valor");
		wl.AddColumnaLista("fecha");

		GraphScriptSerieTemporal gr = new GraphScriptSerieTemporal();
		gr.addAtributtes(Graph.GRAPH_ATTR_ROTATENAMES, "1");
		gr.addAtributtes(Graph.GRAPH_ATTR_DECIMALPRECISON, "2");
		gr.addAtributtes(Graph.GRAPH_ATTR_NUMBERPREFIX, "");
		gr.addAtributtes(Graph.GRAPH_ATTR_EXPORTENABLED, "1");
	  gr.addAtributtes(Graph.GRAPH_ATTR_XAXISNAME, "Tiempo");

		gr.addAtributtes(Graph.GRAPH_ATTR_YAXISNAME, "Variable");

		gr.setTitle(getObjEventGanancia().getDescripcion());
		
		gr.getModel().addColumn("fecha", ModelMatrix.GRAPH_FUNCTION_CATEGORIE);
		gr.getModel().addColumn("descripcion", ModelMatrix.GRAPH_FUNCTION_DATASET);
		gr.getModel().addColumn("valor", ModelMatrix.GRAPH_FUNCTION_VALUE);

  	wl.addGrafico(gr);
    w.ConfigurarFiltrosLista(wl);
    
		Graph g=wl.getGrafico(1);
		if (g!=null) {
			g.localFill(wl,null,null);
			g.setRefresh(1);
			return g.getImage(900, 900).replace("script:", "");
		}
		return null;
	}
	public String getImagenAuxiliar(boolean printerVersion) throws Exception {
		GuiVirtualSeries w = new GuiVirtualSeries();
	  String sSql;
	  Date fdesde = getFDesde();
	  Date fhasta = getFHasta();
	  if (fdesde==null) fdesde=JDateTools.getFirstDayOfYear(new Date());
	  if (fhasta==null) fhasta=JDateTools.getDateEndDay(new Date());
	  
	 	sSql ="SELECT '"+getDescripcion()+"' as descripcion,evt_sqleventos_datos.value as valor, fecha  ";
	 	sSql+=" FROM evt_sqleventos_datos where id_evento= "+getVariableAuxiliar()+ " and ";
		sSql+=" fecha between '"+fdesde+"' and '"+fhasta+"' ";
   	sSql+=" order by fecha ";
	  w.getRecords().SetSQL(sSql);
	  
		JWinList wl = new JWinList(w);

		wl.AddColumnaLista("descripcion");
		wl.AddColumnaLista("valor");
		wl.AddColumnaLista("fecha");

		GraphScriptSerieTemporal gr = new GraphScriptSerieTemporal();
		gr.addAtributtes(Graph.GRAPH_ATTR_ROTATENAMES, "1");
		gr.addAtributtes(Graph.GRAPH_ATTR_DECIMALPRECISON, "2");
		gr.addAtributtes(Graph.GRAPH_ATTR_NUMBERPREFIX, "");
		gr.addAtributtes(Graph.GRAPH_ATTR_EXPORTENABLED, "1");
	  gr.addAtributtes(Graph.GRAPH_ATTR_XAXISNAME, "Tiempo");

		gr.addAtributtes(Graph.GRAPH_ATTR_YAXISNAME, "Variable");

		gr.setTitle(getObjEventAuxiliar().getDescripcion());
		
		gr.getModel().addColumn("fecha", ModelMatrix.GRAPH_FUNCTION_CATEGORIE);
		gr.getModel().addColumn("descripcion", ModelMatrix.GRAPH_FUNCTION_DATASET);
		gr.getModel().addColumn("valor", ModelMatrix.GRAPH_FUNCTION_VALUE);

  	wl.addGrafico(gr);
    w.ConfigurarFiltrosLista(wl);
    
		Graph g=wl.getGrafico(1);
		if (g!=null) {
			g.localFill(wl,null,null);
			g.setRefresh(1);
			return g.getImage(900, 900).replace("script:", "");
		}
		return null;
	}
	public String getImagen2() throws Exception {     
  	if (getObjEvent()==null) return null;
		String company = getCompany();
		BizInterfazNew news=new BizInterfazNew();
		news.dontThrowException(true);
  	Calendar hoy = Calendar.getInstance();
		if (news.read(company)) {
			hoy.setTime(news.getLastPnr());
		}
		else {
			hoy.setTime(new Date());
		}
  	Calendar fd = Calendar.getInstance();
		fd.setTime(getObjContrato().getFechaDesde());
		Calendar fh = Calendar.getInstance();
		fh.setTime(hasFechaCalculo()?getFechaCalculo():getObjContrato().getFechaHasta());
		GraphScriptMedioReloj gr = new GraphScriptMedioReloj();
		gr.setMax((long)getValorObjetivo());
		if (getValorObjetivo()<1) return "";
		gr.setSize(260);
		gr.setIncrementWidth(true);
		 
	
//		if (hoy.after(fd) && hoy.before(fh)) {
		gr.setValor(this.isPax()?(long)getValorActual():((long)getValorObjetivo()<=(long)getValorActual())?(long)getValorObjetivo():(long)getValorActual());
//		} else {
//			gr.setValor(0);
//		}
		
//		gr.setTitle("VENTA DE "+getObjEvent().getDescripcion().toUpperCase()+" A FIN CONTRATO");
		gr.setLeyenda(""+gr.getValor());
		return gr.getImage(300, 300).replace("script:", "");
	}
	
	
  public void processUpdateDatos() throws Exception {
	  if (getObjEvent()!=null && getObjEvent().isOK()) {
			  	getObjEvent().processUpdateDatos();
	  }
	  if (getObjEventGanancia()!=null && getObjEventGanancia().isOK()) {
	  	getObjEventGanancia().processUpdateDatos();
	  }
	  if (getObjEventAuxiliar()!=null && getObjEventAuxiliar().isOK()) {
	  	getObjEventAuxiliar().processUpdateDatos();
	  }
  }
	BizSqlEvent objEvent;
	
	public BizSqlEvent getObjEvent() throws Exception {
		if (objEvent!=null) return objEvent;
		if (pVariable.isNull()) return null;
		BizSqlEvent e = BizUsuario.getUsr().getObjBusiness().getSqlEventWinInstance().GetcDato();
		e.dontThrowException(true);
		if (!e.read(getVariable())) return null;
		return objEvent=e;
	}
	BizSqlEvent objEventGanancia;
	
	public BizSqlEvent getObjEventGanancia() throws Exception {
		if (objEventGanancia!=null) return objEventGanancia;
		if (pVariableGanancia.isNull()) return null;
		BizSqlEvent e = BizUsuario.getUsr().getObjBusiness().getSqlEventWinInstance().GetcDato();
		e.dontThrowException(true);
		if (!e.read(getVariableGanancia())) return null;
		return objEventGanancia=e;
	}
	BizSqlEvent objEventAuxiliar;
	
	public BizSqlEvent getObjEventAuxiliar() throws Exception {
		if (objEventAuxiliar!=null) return objEventAuxiliar;
		if (pVariableAuxiliar.isNull()) return null;
		BizSqlEvent e = BizUsuario.getUsr().getObjBusiness().getSqlEventWinInstance().GetcDato();
		e.dontThrowException(true);
		if (!e.read(getVariableAuxiliar())) return null;
		return objEventAuxiliar=e;
	}
  /**
   * Constructor de la Clase
   */
  public BizDetalle() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "company", pCompany );
    this.addItem( "id", pId );
    this.addItem( "linea", pLinea );
    this.addItem( "valor", pValor );
    this.addItem( "variable", pVariable );
    this.addItem( "variable_aux", pVariableAuxiliar );
    this.addItem( "variable_ganancia", pVariableGanancia );
    this.addItem( "modelo", pModelo );
    this.addItem( "default_tipopremio", pDefaultTipoPremio );
    this.addItem( "default_tipoobjetivo", pDefaultTipoObjetivo );
    this.addItem( "kicker", pKicker );
    this.addItem( "grupo", pGrupo );
    this.addItem( "autogenerado", pAutogenerado );
    this.addItem( "idem_anterior", pIdemAnterior );
    this.addItem( "prioridad", pPrioridad );
    this.addItem( "descripcion", pDescripcion );
    this.addItem( "imagen1", pImagen1 );
    this.addItem( "imagen2", pImagen2 );
    this.addItem( "imagen4", pImagen4 );
    this.addItem( "imagen5", pImagen5 );
    this.addItem( "imagenP", pImagen3 );
    this.addItem( "first_meta", pFirstMeta );
    this.addItem( "fecha_prediccion", pFechaPrediccion );
    this.addItem( "fecha_calculo", pFechaCalculo );
    this.addItem( "update_version", pUpdateVersion );
    this.addItem( "fecha_desde_calculo", pFechaDesdeCalculo );
    this.addItem( "mercados", pMercados );
    this.addItem( "no_mercados", pNoMercados );
    this.addItem( "rutas", pRutas );
    this.addItem( "no_rutas", pNoRutas );
    this.addItem( "vuelos", pVuelos );
    this.addItem( "no_vuelos", pNoVuelos );
    this.addItem( "no_vuelos_base", pNoVuelosBase );
    this.addItem( "no_vuelos_aux", pNoVuelosAux );
    this.addItem( "andor", pAndOr );
    this.addItem( "andor_base", pAndOrBase );
    this.addItem( "andor_aux", pAndOrAux );
   this.addItem( "conclusion", pConclusion );
    this.addItem( "analisis", pAnalisis );
    this.addItem( "descr_variable", pDescrVariable );
    this.addItem( "descr_contrato", pDescrContrato );
    this.addItem( "acumulativo", pAcumulativo );
    this.addItem( "pax", pPax );
    this.addItem( "pax_aux", pPaxAux);
    this.addItem( "pax_base", pPaxBase);
    this.addItem( "viceversa", pViceversa );
    this.addItem( "primera", pPrimera );
     this.addItem( "periodo", pPeriodo );
    this.addItem( "valor_actual", pValorActual ); // borrar
    this.addItem( "valor_total", pValorTotal ); // borrar
    this.addItem( "valor_objetivo", pValorObjetivo );
    this.addItem( "valor_fcontrato", pValorFinContrato );
    this.addItem( "valor_totalcontrato", pValorTotalFinContrato );
    this.addItem( "sig_valor_objetivo", pSigValorObjetivo );
    this.addItem( "nivel_alcanzado", pNivelAlcanzado ); // borrar
    this.addItem( "nivel_alcanzado_estimada", pNivelAlcanzadoEstimada );
    this.addItem( "ganancia", pGanancia ); // borrar
    this.addItem( "tourcode_excluded", pTourCodeExcluded );
    this.addItem( "tourcode_included", pTourCodeIncluded );
    this.addItem( "class_excluded", pClasesExcluded );
    this.addItem( "ganancia_estimada", pGananciaEstimada );
    this.addItem( "ganancia_auxiliar", pGananciaAuxiliar );
    this.addItem( "nivel", pNivelCumplimiento );
    this.addItem( "nivel_estimado", pNivelCumplimientoEstimado);
    this.addItem( "detalle_nivel", pDetalleNivelCumplimiento );
    this.addItem( "fms_global", pFMSGlobal );
    this.addItem( "ponderado", pPonderado );
    this.addItem( "ms", pMS );
    this.addItem( "ms_base", pMSBase );
    this.addItem( "dolares", pDolares );
    this.addItem( "yq",  pYQ );
    this.addItem( "origen_negado", pOrigenNegado );
    this.addItem( "destino_negado", pDestinoNegado );
    this.addItem( "sharegap_global", pShareGapGLobal );
    this.addItem( "valor_global", pValorGlobal );
    this.addItem( "porc_upfront", pPorcUpfront );
    this.addItem( "valor_reembolso", pReembolso );
    this.addItem( "valor_reembolso_auto", pReembolsoAuto );
    this.addItem( "valor_reembolso_auto_show", pReembolsoAutoShow );
    this.addItem( "calcule_reembolso_auto", pRecalculeReembolso );
    this.addItem( "comision_base", pComisionBase );
    this.addItem( "volado", pVolado );
    this.addItem( "interlineal", pInterlineal );
    this.addItem( "fdesde", pFDesde );
    this.addItem( "fhasta", pFHasta );
    this.addItem( "mes", pMes );
    this.addItem( "fms_min", pFMSMin );
    this.addItem( "reservas_meta", pReservasMeta );
    this.addItem( "reservas_base", pReservasBase );
    this.addItem( "reservas_aux", pReservasAux );
    this.addItem( "fms_max", pFMSMax );
    this.addItem( "limite", pLimiteMinTkt );
    this.addItem( "id_aerolinea", pIdAerolinea );
    this.addItem( "aerolineas", pAerolineas );
    this.addItem( "aerolineas_placa", pAerolineasPlaca );
    this.addItem( "no_aerolineas", pNoAerolineas );
    this.addItem( "no_aerolineas_placa", pNoAerolineasPlaca );
    this.addItem( "info_aerolineas", pInfoAerolineas );
    this.addItem( "iatas", pIatas );
    this.addItem( "no_iatas", pNoIatas );
    this.addItem( "gds", pGDS );
    this.addItem( "no_gds", pNoGDS );
    this.addItem( "gds_base", pGDSBase );
    this.addItem( "no_gds_base", pNoGDSBase );
    this.addItem( "gds_aux", pGDSAux );
    this.addItem( "no_gds_aux", pNoGDSAux );
    this.addItem( "niveles", pNiveles );
    this.addItem( "grillas", pGrillas );
    this.addItem( "copas", pCopas );
    this.addItem( "copas_rutas", pCopasRutas );
    this.addItem( "copas_rutas_ws", pCopasRutasWS );
    this.addItem( "objetivos_rutas", pObjetivosRutas );
    this.addItem( "html_data", pHtmlData );
    this.addItem( "logica", pLogicaContrato );
    this.addItem( "periodo_detalle", pPeriodoDetalle );
    this.addItem( "class_included", pClasesIncluded );
    this.addItem( "origen_continente", pOrigenContinente );
    this.addItem( "origen_pais", pOrigenPais );
    this.addItem( "origen_zona", pOrigenZona );
    this.addItem( "origen_aeropuerto", pOrigenAeropuerto );
    this.addItem( "destino_continente", pDestinoContinente );
    this.addItem( "destino_pais", pDestinoPais );
    this.addItem( "destino_zona", pDestinoZona );
    this.addItem( "destino_aeropuerto", pDestinoAeropuerto );
    this.addItem( "fecha_emision_desde", pFechaEmisionDesde );
    this.addItem( "fecha_emision_hasta", pFechaEmisionHasta );
    this.addItem( "fecha_vuelo_desde", pFechaVueloDesde );
    this.addItem( "fecha_vuelo_hasta", pFechaVueloHasta );
    this.addItem( "fecha_blockout_desde", pFechaBlockoutDesde );
    this.addItem( "fecha_blockout_hasta", pFechaBlockoutHasta );
    this.addItem( "meses", pMeses );
     this.addItem( "sep_fecha", pSepFecha );

    this.addItem( "origen_consolidado", pOrigenConsolidado );
    this.addItem( "destino_consolidado", pDestinoConsolidado );
    this.addItem( "moneda_consolidada", pMonedaConsolidada );
    this.addItem( "premio_upfront", pPremioUpfront );
    this.addItem( "viceversa_base", pViceversaBase );
    this.addItem( "primera_base", pPrimeraBase );
    this.addItem( "origen_negado_base", pOrigenNegadoBase );
    this.addItem( "destino_negado_base", pDestinoNegadoBase );
    this.addItem( "volado_base", pVoladoBase );
    this.addItem( "interlineal_base", pInterlinealBase );
    this.addItem( "domestic", pDomestico );
    this.addItem( "domestic_base", pDomesticoBase );

    this.addItem( "mercados_base", pMercadosBase );
    this.addItem( "no_mercados_base", pNoMercadosBase );
    this.addItem( "rutas_base", pRutasBase );
    this.addItem( "vuelos_base", pVuelosBase );
    this.addItem( "tourcode_excluded_base", pTourCodeExcludedBase );
    this.addItem( "tourcode_included_base", pTourCodeIncludedBase );
    this.addItem( "class_excluded_base", pClasesExcludedBase );
    this.addItem( "class_included_base", pClasesIncludedBase );
    this.addItem( "origen_continente_base", pOrigenContinenteBase );
    this.addItem( "origen_pais_base", pOrigenPaisBase );
    this.addItem( "origen_zona_base", pOrigenZonaBase );
    this.addItem( "origen_aeropuerto_base", pOrigenAeropuertoBase );
    this.addItem( "destino_continente_base", pDestinoContinenteBase );
    this.addItem( "destino_pais_base", pDestinoPaisBase );
    this.addItem( "destino_zona_base", pDestinoZonaBase );
    this.addItem( "destino_aeropuerto_base", pDestinoAeropuertoBase );
    
    this.addItem( "tipoviaje", pTipoViaje );
    this.addItem( "multidestino", pMultidestino );
    this.addItem( "stopover", pStopover );
    this.addItem( "cambio", pCambio );
    this.addItem( "vuelta_mundo", pVueltaMundo );

    this.addItem( "origen_negado_aux", pOrigenNegadoBase );
    this.addItem( "destino_negado_aux", pDestinoNegadoBase );
    this.addItem( "volado_aux", pVoladoAux );
    this.addItem( "interlineal_aux", pInterlinealAux );
    this.addItem( "viceversa_aux", pViceversaAux );
    this.addItem( "primera_aux", pPrimeraAux );
    this.addItem( "domestic", pDomestico );
    this.addItem( "domestic_aux", pDomesticoAux );
    this.addItem( "ms_aux", pMSAux );

    this.addItem( "exchange_base", pExchangeBase );
    this.addItem( "exchange", pExchange );
    this.addItem( "exchange_aux", pExchangeAux );

    this.addItem( "mercados_aux", pMercadosAux );
    this.addItem( "no_mercados_aux", pNoMercadosAux );
    this.addItem( "rutas_aux", pRutasAux);
    this.addItem( "vuelos_aux", pVuelosAux );
    this.addItem( "tourcode_excluded_aux", pTourCodeExcludedAux );
    this.addItem( "tourcode_included_aux", pTourCodeIncludedAux );
    this.addItem( "class_excluded_aux", pClasesExcludedAux );
    this.addItem( "tipopasajero_excluded", pTipoPasajeroExcluded );
    this.addItem( "tipopasajero_included", pTipoPasajeroIncluded );
    this.addItem( "tipopasajero_excluded_base", pTipoPasajeroExcludedBase);
    this.addItem( "tipopasajero_included_base", pTipoPasajeroIncludedBase );
    this.addItem( "tipopasajero_excluded_aux", pTipoPasajeroExcludedAux );
    this.addItem( "tipopasajero_included_aux", pTipoPasajeroIncludedAux );
 
    this.addItem( "class_included_aux", pClasesIncludedAux );
    this.addItem( "origen_continente_aux", pOrigenContinenteAux );
    this.addItem( "origen_pais_aux", pOrigenPaisAux );
    this.addItem( "origen_zona_aux", pOrigenZonaAux );
    this.addItem( "origen_aeropuerto_aux", pOrigenAeropuertoAux );
    this.addItem( "destino_continente_aux", pDestinoContinenteAux );
    this.addItem( "destino_pais_aux", pDestinoPaisAux);
    this.addItem( "destino_zona_aux", pDestinoZonaAux );
    this.addItem( "destino_aeropuerto_aux", pDestinoAeropuertoAux );
    this.addItem( "tipo_contrato", pTipoContrato );
    this.addItem( "consulta_reserva", pConsultaReservaMeta );
    this.addItem( "consulta_reserva_base", pConsultaReservaBase );
    this.addItem( "consulta_reserva_aux", pConsultaReservaAux );

    this.addItem( "farebasic_excluded", pFareBasicExcluded );
    this.addItem( "farebasic_included", pFareBasicIncluded );
    this.addItem( "farebasic_excluded_base", pFareBasicExcludedBase );
    this.addItem( "farebasic_included_base", pFareBasicIncludedBase );
    this.addItem( "farebasic_excluded_aux", pFareBasicExcludedAux );
    this.addItem( "farebasic_included_aux", pFareBasicIncludedAux );
    this.addItem( "ganancia_estimada_calc", pGananciaEstimadaCalc );
    this.addItem( "valor_totalcontrato_calc", pValorTotalContratoCalc );
    this.addItem( "nivel_alcanzado_estimado_calc", pNivelAlcanzadoEstimadoCalc );
    
    this.addItem( "descr_sql_aux1_meta", pDescrSqlAux1Meta );
    this.addItem( "descr_sql_aux2_meta", pDescrSqlAux2Meta );
    this.addItem( "sql_aux1_meta", pSqlAux1Meta );
    this.addItem( "sql_aux2_meta", pSqlAux2Meta );
    this.addItem( "descr_sql_aux1_base", pDescrSqlAux1Base );
    this.addItem( "descr_sql_aux2_base", pDescrSqlAux2Base );
    this.addItem( "sql_aux1_base", pSqlAux1Base );
    this.addItem( "sql_aux2_base", pSqlAux2Base );

    
  }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "company", "Company", true, true, 15 );
    this.addFixedItem( KEY, "id", "Id", true, true, 64 );
    this.addFixedItem( KEY, "linea", "Linea", false, false, 32 );
    this.addFixedItem( FIELD, "valor", "Valor", true, false, 18 );
    this.addFixedItem( FIELD, "descripcion", "Descripcion", true, true, 300 );
    this.addFixedItem( FIELD, "variable", "Variable", true, false, 18 );
    this.addFixedItem( FIELD, "variable_aux", "Variable auxiliar", true, false, 18 );
    this.addFixedItem( FIELD, "variable_ganancia", "variable_ganancia", true, false, 18 );
    this.addFixedItem( FIELD, "modelo", "modelo", true, false, 18 );
    this.addFixedItem( FIELD, "default_tipopremio", "Default tipo premio", true, false, 300 );
    this.addFixedItem( FIELD, "default_tipoobjetivo", "Default tipo objetivo", true, false, 300 );
    this.addFixedItem( FIELD, "tourcode_excluded", "Tour code excluidas", true, false, 3000 );
    this.addFixedItem( FIELD, "tourcode_included", "Tour code incluidas", true, false, 3000 );
    this.addFixedItem( FIELD, "class_excluded", "Clases excluidas", true, false, 3000 );
    this.addFixedItem( FIELD, "class_included", "Clases incluidas", true, false, 3000 );
    this.addFixedItem( FIELD, "origen_continente", "Origen continente", true, false, 3000 );
    this.addFixedItem( FIELD, "origen_pais", "Origen pais", true, false, 3000 );
    this.addFixedItem( FIELD, "origen_zona", "Origen zona", true, false, 3000 );
    this.addFixedItem( FIELD, "origen_aeropuerto", "Origen aeropuerto", true, false, 3000 );
    this.addFixedItem( FIELD, "destino_continente", "Destino continente", true, false, 3000 );
    this.addFixedItem( FIELD, "destino_pais", "Destino pais", true, false, 3000 );
    this.addFixedItem( FIELD, "destino_zona", "Destino zona", true, false, 3000 );
    this.addFixedItem( FIELD, "destino_aeropuerto", "Destino aeropuerto", true, false, 3000 );
    this.addFixedItem( FIELD, "mercados", "Mercados", true, false, 3000 );
    this.addFixedItem( FIELD, "no_mercados", "No Mercados", true, false, 3000 );
    this.addFixedItem( FIELD, "vuelos", "Vuelos", true, false, 3000 );
    this.addFixedItem( FIELD, "no_vuelos", "No Vuelos", true, false, 3000 );
    this.addFixedItem( FIELD, "no_vuelos_base", "No Vuelos base", true, false, 3000 );
    this.addFixedItem( FIELD, "no_vuelos_aux", "No Vuelos aux", true, false, 3000 );
    this.addFixedItem( FIELD, "andor", "And OR", true, false, 30 );
    this.addFixedItem( FIELD, "andor_base", "And Or base", true, false, 30 );
    this.addFixedItem( FIELD, "andor_aux", "And Or aux", true, false, 30 );
    this.addFixedItem( FIELD, "rutas", "rutas", true, false, 4000 );
    this.addFixedItem( FIELD, "no_rutas", "rutas excluidad", true, false, 4000 );
    this.addFixedItem( FIELD, "meses", "Meses emision", true, false, 3000 );
    this.addFixedItem( FIELD, "consulta_reserva", "Consulta reserva meta", true, false, 4000 );
    this.addFixedItem( FIELD, "consulta_reserva_base", "Consulta reserva base", true, false, 4000 );
    this.addFixedItem( FIELD, "consulta_reserva_aux", "Consulta reserva aux", true, false, 4000 );

    this.addFixedItem( FIELD, "fecha_emision_desde", "Fecha emision desde", true, false, 18 );
    this.addFixedItem( FIELD, "fecha_emision_hasta", "Fecha emision hasta", true, false, 18 );
    this.addFixedItem( FIELD, "fecha_vuelo_desde", "Fecha vuelo desde", true, false, 18 );
    this.addFixedItem( FIELD, "fecha_vuelo_hasta", "Fecha vuelo hasta", true, false, 18 );
    this.addFixedItem( FIELD, "fecha_blockout_desde", "Fecha blockout desde", true, false, 18 );
    this.addFixedItem( FIELD, "fecha_blockout_hasta", "Fecha blockout hasta", true, false, 18 );
    this.addFixedItem( VIRTUAL, "origen_consolidado", "Origen", true, false, 500 );
    this.addFixedItem( VIRTUAL, "destino_consolidado", "Destino", true, false, 500 );
    this.addFixedItem( VIRTUAL, "moneda_consolidada", "Moneda", true, false, 30 );
    this.addFixedItem( VIRTUAL, "premio_upfront", "Premio", true, false, 30 );
    
    this.addFixedItem( FIELD, "domestic", "Domestico", true, false, 1 );

    this.addFixedItem( FIELD, "kicker", "Extra/Subobjetivo", true, false, 1 );
    this.addFixedItem( FIELD, "grupo", "Grupo", true, false, 100 );
    this.addFixedItem( FIELD, "autogenerado", "Autogenerado", true, false, 50 );
    this.addFixedItem( VIRTUAL, "idem_anterior", "Idem anterior", true, false, 1 );
    this.addFixedItem( FIELD, "pax", "pax", true, false, 1 );
    this.addFixedItem( FIELD, "pax_aux", "pax aux", true, false, 1 );
    this.addFixedItem( FIELD, "pax_base", "pax base", true, false, 1 );
    this.addFixedItem( FIELD, "viceversa", "viceversa", true, false, 1 );
    this.addFixedItem( FIELD, "primera", "primera edición", true, false, 1 );
    this.addFixedItem( FIELD, "acumulativo", "acumulativo", true, false, 1 );
    this.addFixedItem( FIELD, "periodo", "periodo", true, false, 50 );
    this.addFixedItem( FIELD, "prioridad", "prioridad", true, false, 18 );
    this.addFixedItem( FIELD, "fecha_prediccion", "fecha prediccion", true, false, 18 );
    this.addFixedItem( FIELD, "update_version", "fecha version", true, false, 18 );
    this.addFixedItem( VIRTUAL, "fecha_calculo", "fecha calculo", true, false, 18 );
    this.addFixedItem( VIRTUAL, "fecha_desde_calculo", "fecha Desde calculo", true, false, 18 );
    this.addFixedItem( FIELD, "valor_actual", "Valor actual", true, false, 18,2 ); // borrar
    this.addFixedItem( FIELD, "sig_valor_objetivo", "Prox.Objetivo", true, false, 18,2 );
    this.addFixedItem( FIELD, "valor_objetivo", "Valor objetivo", true, false, 18,2 );
    this.addFixedItem( FIELD, "valor_total", "Valor ingreso", true, false, 18,2 ); // borrar
    this.addFixedItem( FIELD, "valor_fcontrato", "Valor alcanzado", true, false, 18,2 );
    this.addFixedItem( FIELD, "valor_totalcontrato", "Base comisionable", true, false, 18,2 );
    this.addFixedItem( FIELD, "nivel_alcanzado", "Nivel alcanzado", true, false, 200 ); // borrar
    this.addFixedItem( FIELD, "ganancia", "Ganancia", true, false, 18,2 ); // borrar
    this.addFixedItem( FIELD, "nivel_alcanzado_estimada", "Nivel/Porc.Comision", true, false, 200 );
    this.addFixedItem( FIELD, "ganancia_estimada", "Ganancia/Comisión", true, false, 18,2 );
    this.addFixedItem( FIELD, "ganancia_auxiliar", "Ganancia Auxiliar", true, false, 18,2 );
    this.addFixedItem( VIRTUAL, "reservas_meta", "Reservas meta", true, false, 18,2 );
    this.addFixedItem( VIRTUAL, "reservas_base", "Reservas base", true, false, 18,2 );
    this.addFixedItem( VIRTUAL, "reservas_aux", "Reservas aux", true, false, 18,2 );
    this.addFixedItem( FIELD, "fms_min", "FMS max", true, false, 18,2 );
    this.addFixedItem( FIELD, "fms_max", "FMS min", true, false, 18,2 );
    this.addFixedItem( FIELD, "limite", "limite", true, false, 18,2 );
    this.addFixedItem( FIELD, "id_aerolinea", "Aerolinea", true, false, 50 );
    this.addFixedItem( FIELD, "aerolineas", "Aerolineas", true, false, 2000 );
    this.addFixedItem( FIELD, "aerolineas_placa", "Aerolineas Placa", true, false, 2000 );
    this.addFixedItem( FIELD, "no_aerolineas", "No Aerolineas", true, false, 200 );
    this.addFixedItem( FIELD, "no_aerolineas_placa", "No Aerolineas placa", true, false, 200 );
    this.addFixedItem( VIRTUAL, "info_aerolineas", "Aerolineas", true, false, 1000 );
    this.addFixedItem( FIELD, "iatas", "Iatas", true, false, 2000 );
    this.addFixedItem( FIELD, "no_iatas", "No Iatas", true, false, 2000 );
    this.addFixedItem( FIELD, "gds", "GDS", true, false, 2000 );
    this.addFixedItem( FIELD, "no_gds", "No GDS", true, false, 2000 );
    this.addFixedItem( FIELD, "gds_base", "GDS Base", true, false, 2000 );
    this.addFixedItem( FIELD, "no_gds_base", "No GDS Base", true, false, 2000 );
    this.addFixedItem( FIELD, "gds_aux", "GDS Aux", true, false, 2000 );
    this.addFixedItem( FIELD, "no_gds_aux", "No GDS Aux", true, false, 2000 );
    this.addFixedItem( FIELD, "fms_global", "FMS global", true, false, 18,2 );
    this.addFixedItem( FIELD, "ponderado", "Ponderado", true, false, 18,2 );
    this.addFixedItem( FIELD, "ms", "Calculo", true, false, 1);
    this.addFixedItem( FIELD, "ms_base", "Calculo Base", true, false, 1);
    this.addFixedItem( FIELD, "dolares", "En dolares", true, false, 1 );
    this.addFixedItem( FIELD, "yq", "con YQ", true, false, 1 );
    this.addFixedItem( FIELD, "origen_negado", "Negado origen", true, false, 1 );
    this.addFixedItem( FIELD, "destino_negado", "Negado destino", true, false, 1 );
    this.addFixedItem( FIELD, "volado", "Volado", true, false, 1 );
    this.addFixedItem( FIELD, "interlineal", "Interlineal", true, false, 1 );
    this.addFixedItem( FIELD, "sharegap_global", "Target Share Gap", true, false, 18,2 );
    this.addFixedItem( FIELD, "valor_global", "Valor referencia global", true, false, 18,2 );
    this.addFixedItem( FIELD, "porc_upfront", "Porc.Upfront", true, false, 18,2 );
    this.addFixedItem( FIELD, "valor_reembolso", "Valor reembolso", true, false, 18,2 );
    this.addFixedItem( FIELD, "valor_reembolso_auto", "Valor reembolso Auto", true, false, 18,2 );
    this.addFixedItem( VIRTUAL, "valor_reembolso_auto_show", "Valor reembolso Auto", true, false, 18,2 );
      this.addFixedItem( FIELD, "calcule_reembolso_auto", "Recalcule reembolso", true, false, 1 );
     this.addFixedItem( FIELD, "comision_base", "Comision Base", true, false, 18, 2 );
    this.addFixedItem( VIRTUAL, "nivel", "Nivel cumplimiento", true, false, 18,2 );
    this.addFixedItem( VIRTUAL, "nivel_estimado", "Nivel cumplimiento estimado", true, false, 18,2 );
    this.addFixedItem( VIRTUAL, "detalle_nivel", "Cumpl.", true, false, 50 );
    this.addFixedItem( VIRTUAL, "imagen1", "imagen1", true, false, 64 );
    this.addFixedItem( VIRTUAL, "imagen2", "imagen2", true, false, 64 );
    this.addFixedItem( VIRTUAL, "imagen4", "imagen4", true, false, 64 );
    this.addFixedItem( VIRTUAL, "imagen5", "imagen5", true, false, 64 );
    this.addFixedItem( VIRTUAL, "imagenP", "imagen printer", true, false, 64 );
    this.addFixedItem( VIRTUAL, "first_meta", "Meta inicial", true, false, 18, 2 );
    this.addFixedItem( FIELD, "conclusion", "Evaluacion", true, false, 500 );
    this.addFixedItem( FIELD, "analisis", "Analisis detallado", true, false, 4000 );
    this.addFixedItem( VIRTUAL, "descr_variable", "Descr.Variable", true, false, 200 );
    this.addFixedItem( VIRTUAL, "descr_contrato", "Descr.Contrato", true, false, 200 );
    this.addFixedItem( VIRTUAL, "mes", "Mes", true, false, 18 );
    this.addFixedItem( VIRTUAL, "fdesde", "Fecha desde", true, false, 18 );
    this.addFixedItem( VIRTUAL, "fhasta", "Fecha hasta", true, false, 18 );
    this.addFixedItem( VIRTUAL, "html_data", getHtmlTitulo(), true, false, 400 );
		this.addFixedItem( RECORDS, "grillas", "Grillas", true, false, 18).setClase(BizObjetivosGrilla.class);
		this.addFixedItem( RECORDS, "copas", "Copas", true, false, 18).setClase(BizObjetivosCopa.class);
		this.addFixedItem( RECORDS, "copas_rutas", "Copas rutas", true, false, 18).setClase(BizObjetivosCopaPorRutas.class);
		this.addFixedItem( RECORDS, "copas_rutas_ws", "Copas rutas ws", true, false, 18).setClase(BizObjetivosCopaWS.class);
		this.addFixedItem( RECORDS, "objetivos_rutas", "Obj rutas", true, false, 18).setClase(BizObjetivosRuta.class);
		this.addFixedItem( RECORDS, "niveles", "Niveles", true, false, 18).setClase(BizNivel.class);
		this.addFixedItem( FIELD, "logica", "Logica contrato", true, false, 4000 );
    this.addFixedItem( VIRTUAL, "periodo_detalle", "Periodo", true, false, 400 );
    this.addFixedItem( VIRTUAL, "tipo_contrato", "Tipo contrato", true, false, 50 );

    this.addFixedItem( FIELD, "tourcode_excluded_base", "Tour code excluidas base", true, false, 3000 );
    this.addFixedItem( FIELD, "tourcode_included_base", "Tour code incluidas base", true, false, 3000 );
    this.addFixedItem( FIELD, "class_excluded_base", "Clases excluidas base", true, false, 3000 );
    this.addFixedItem( FIELD, "class_included_base", "Clases incluidas base", true, false, 3000 );
    this.addFixedItem( FIELD, "origen_continente_base", "Origen continente base", true, false, 3000 );
    this.addFixedItem( FIELD, "origen_pais_base", "Origen pais base", true, false, 3000 );
    this.addFixedItem( FIELD, "origen_zona_base", "Origen zona base", true, false, 3000 );
    this.addFixedItem( FIELD, "origen_aeropuerto_base", "Origen aeropuerto base", true, false, 3000 );
    this.addFixedItem( FIELD, "destino_continente_base", "Destino continentev", true, false, 3000 );
    this.addFixedItem( FIELD, "destino_pais_base", "Destino pais base", true, false, 3000 );
    this.addFixedItem( FIELD, "destino_zona_base", "Destino zona base", true, false, 3000 );
    this.addFixedItem( FIELD, "destino_aeropuerto_base", "Destino aeropuerto base", true, false, 3000 );
    this.addFixedItem( FIELD, "mercados_base", "Mercados base", true, false, 3000 );
    this.addFixedItem( FIELD, "no_mercados_base", "No Mercados base", true, false, 3000 );
    this.addFixedItem( FIELD, "vuelos_base", "Vuelos base", true, false, 3000 );
    this.addFixedItem( FIELD, "rutas_base", "rutas base", true, false, 4000 );
    this.addFixedItem( FIELD, "viceversa_base", "viceversa base", true, false, 1 );
    this.addFixedItem( FIELD, "primera_base", "primera base", true, false, 1 );
    this.addFixedItem( FIELD, "domestic_base", "Domestico base", true, false, 1 );
    this.addFixedItem( FIELD, "volado_base", "Volado base", true, false, 1 );
    this.addFixedItem( FIELD, "interlineal_base", "Interlineal base", true, false, 1 );
    this.addFixedItem( FIELD, "origen_negado_base", "Negado base", true, false, 1 );
    this.addFixedItem( FIELD, "destino_negado_base", "Negado base", true, false, 1 );
    this.addFixedItem( FIELD, "ms_aux", "Calculo aux", true, false, 1);
    this.addFixedItem( FIELD, "sep_fecha", "Sep fecha", true, false, 18);

    this.addFixedItem( FIELD, "tipoviaje", "Tipo viaje", true, false, 1);
    this.addFixedItem( FIELD, "multidestino", "Multidestino", true, false, 1);
    this.addFixedItem( FIELD, "stopover", "Stopover", true, false, 1);
    this.addFixedItem( FIELD, "cambio" , "Cambio", true, false, 1);
    this.addFixedItem( FIELD, "vuelta_mundo" , "Vuelta al mundo", true, false, 1);

    this.addFixedItem( FIELD, "tourcode_excluded_aux", "Tour code excluidas aux", true, false, 3000 );
    this.addFixedItem( FIELD, "tourcode_included_aux", "Tour code incluidas aux", true, false, 3000 );

    this.addFixedItem( FIELD, "tipopasajero_excluded", "tipo pasajero excluidas", true, false, 3000 );
    this.addFixedItem( FIELD, "tipopasajero_included", "tipo pasajero incluidas", true, false, 3000 );
    this.addFixedItem( FIELD, "tipopasajero_excluded_base", "tipo pasajero excluidas base", true, false, 3000 );
    this.addFixedItem( FIELD, "tipopasajero_included_base", "tipo pasajero incluidas base", true, false, 3000 );
    this.addFixedItem( FIELD, "tipopasajero_excluded_aux", "tipo pasajero excluidas aux", true, false, 3000 );
    this.addFixedItem( FIELD, "tipopasajero_included_aux", "tipo pasajero incluidas aux", true, false, 3000 );

    this.addFixedItem( FIELD, "class_excluded_aux", "Clases excluidas aux", true, false, 3000 );
    this.addFixedItem( FIELD, "class_included_aux", "Clases incluidas aux", true, false, 3000 );
    this.addFixedItem( FIELD, "origen_continente_aux", "Origen continente aux", true, false, 3000 );
    this.addFixedItem( FIELD, "origen_pais_aux", "Origen pais aux", true, false, 3000 );
    this.addFixedItem( FIELD, "origen_zona_aux", "Origen zona aux", true, false, 3000 );
    this.addFixedItem( FIELD, "origen_aeropuerto_aux", "Origen aeropuerto aux", true, false, 3000 );
    this.addFixedItem( FIELD, "destino_continente_aux", "Destino continentev", true, false, 3000 );
    this.addFixedItem( FIELD, "destino_pais_aux", "Destino pais aux", true, false, 3000 );
    this.addFixedItem( FIELD, "destino_zona_aux", "Destino zona aux", true, false, 3000 );
    this.addFixedItem( FIELD, "destino_aeropuerto_aux", "Destino aeropuerto aux", true, false, 3000 );
    this.addFixedItem( FIELD, "mercados_aux", "Mercados aux", true, false, 3000 );
    this.addFixedItem( FIELD, "vuelos_aux", "Vuelos aux", true, false, 3000 );
    this.addFixedItem( FIELD, "rutas_aux", "rutas aux", true, false, 4000 );
    this.addFixedItem( FIELD, "no_mercados_aux", "No Mercados aux", true, false, 3000 );
    this.addFixedItem( FIELD, "viceversa_aux", "viceversa aux", true, false, 1 );
    this.addFixedItem( FIELD, "primera_aux", "primera aux", true, false, 1 );
    this.addFixedItem( FIELD, "domestic_aux", "Domestico aux", true, false, 1 );
    this.addFixedItem( FIELD, "volado_aux", "Volado aux", true, false, 1 );
    this.addFixedItem( FIELD, "interlineal_aux", "Interlineal aux", true, false, 1 );
    this.addFixedItem( FIELD, "origen_negado_aux", "Negado aux", true, false, 1 );
    this.addFixedItem( FIELD, "destino_negado_aux", "Negado aux", true, false, 1 );

    this.addFixedItem( FIELD, "exchange", "exchange", true, false, 1 );
    this.addFixedItem( FIELD, "exchange_base", "exchange base", true, false, 1 );
    this.addFixedItem( FIELD, "exchange_aux", "exchange aux", true, false, 1 );

    this.addFixedItem( FIELD, "farebasic_excluded", "Fare Basic excluidas", true, false, 3000 );
    this.addFixedItem( FIELD, "farebasic_included", "Fare Basic incluidas", true, false, 3000 );
    this.addFixedItem( FIELD, "farebasic_excluded_base", "Fare Basic excluidas base", true, false, 3000 );
    this.addFixedItem( FIELD, "farebasic_included_base", "Fare Basic incluidas base", true, false, 3000 );
    this.addFixedItem( FIELD, "farebasic_excluded_aux", "Fare Basic excluidas aux", true, false, 3000 );
    this.addFixedItem( FIELD, "farebasic_included_aux", "Fare Basic incluidas aux", true, false, 3000 );
    this.addFixedItem( VIRTUAL, "ganancia_estimada_calc", "Ganancia estimada", true, false, 18,2 );
    this.addFixedItem( VIRTUAL, "valor_totalcontrato_calc", "Base comisionable estimada", true, false, 18,2 );
    this.addFixedItem( VIRTUAL, "nivel_alcanzado_estimado_calc", "Porcentaje contrato", true, false, 18,2 );

    this.addFixedItem( VIRTUAL, "descr_sql_aux1_meta", "descr_sql_aux1_meta", true, false, 4000 );
    this.addFixedItem( VIRTUAL, "descr_sql_aux2_meta", "descr_sql_aux2_meta", true, false, 4000 );
    this.addFixedItem( VIRTUAL, "sql_aux1_meta", "sql_aux1_meta", true, false, 18,2 );
    this.addFixedItem( VIRTUAL, "sql_aux2_meta", "sql_aux2_meta", true, false, 18,2 );
    this.addFixedItem( VIRTUAL, "descr_sql_aux1_base", "descr_sql_aux1_base", true, false, 4000 );
    this.addFixedItem( VIRTUAL, "descr_sql_aux2_base", "descr_sql_aux2_base", true, false, 4000 );
    this.addFixedItem( VIRTUAL, "sql_aux1_base", "sql_aux1_base", true, false, 18,2 );
    this.addFixedItem( VIRTUAL, "sql_aux2_base", "sql_aux2_base", true, false, 18,2 );
     
  }
  /**
   * Returns the table name
   */
  public String GetTable() { return "bsp_contrato_detalle"; }


  public void attachRelationMap(JRelations rels) throws Exception {
  	rels.setSourceWinsClass(GuiDetalles.class.getName());
  	rels.hideField("id");
  	rels.hideField("linea");
  	rels.hideField("imagen1");
  	rels.hideField("imagen2");
  	rels.hideField("imagen4");
  	rels.hideField("imagen5");
    rels.hideField("imagenP");
  	rels.hideField("html_data");
  	rels.hideField("linea");

  	JRelation r=rels.addRelationParent(20, "Indicador", BizSqlEvent.class, "variable");
  	r.addJoin("bsp_contrato_detalle.variable", "evt_sqleventos.id");
  	
  	
  }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  /**
   * Default read() method
   */
  public boolean read( long zLinea ) throws Exception { 
    addFilter( "linea",  zLinea ); 
    return read(); 
  } 
  
  public boolean readByAutogenerado( String  zCompany,long zContrato, String ruta ) throws Exception { 
    addFilter( "company",  zCompany ); 
    addFilter( "id",  zContrato ); 
    addFilter( "autogenerado",  ruta ); 
    return read(); 
  } 
 
  public void processInsertClon() throws Exception {
  	BizDetalle max = new BizDetalle();
  	pModelo.setValue(max.SelectMaxLong("modelo")+1);
  	super.processInsert();
  	processCopyVariaciones();
    new BizPNRTicket().procCalculeOverDetalle(this,null,null,new Date());
  }
  @Override
  public void processInsert() throws Exception {
  	BizDetalle max = new BizDetalle();
  	pModelo.setValue(max.SelectMaxLong("modelo")+1);
   	setUpdateVersion(new Date());
   	if (isNullPrioridad()) {
    	BizDetalle max2 = new BizDetalle();
     	max2.addFilter("linea",getLinea());
     	setPrioridad(max2.SelectMaxLong("prioridad")+1);		
   	}
    super.processInsert();
    clean();
  	processCopyVariaciones();
   	JRecords<BizNivel> detalles = this.pNiveles.getRawValue();
   	if (detalles!=null)
   		detalles.processProcessTable(this,true);

  // 	this.processPopulate(null,false);
//    new BizPNRTicket().procCalculeOverDetalle(this,null,null,new Date());
    getObjContrato().markForChange();
    
     calculeReembolsos();
  //	calcule();
  }
  @Override
  public void processUpdate() throws Exception {
  	BizDetalle max = new BizDetalle();
    if (isNullModelo()) pModelo.setValue(max.SelectMaxLong("modelo")+1);
  	super.processUpdate();
   	JRecords<BizNivel> detalles = this.pNiveles.getRawValue();
   	if (detalles!=null) {
   		detalles.processProcessTable(this,true);
   	} else {
   		JRecords<BizNivel> niveles = getObjNiveles();
     	JIterator<BizNivel> it = niveles.getStaticIterator();
     	while (it.hasMoreElements()) {
     		BizNivel n = it.nextElement();
     		n.processUpdateSimple();
     	}
  	}
   //	processMercado();
   	setUpdateVersion(new Date());
   	if (isNullPrioridad()) {
    	BizDetalle max2 = new BizDetalle();
     	max2.addFilter("linea",getLinea());
     	setPrioridad(max2.SelectMaxLong("prioridad")+1);
  		
   	}
   	super.update();
    processUpdateAsociados();
    processUpdateAndCalcule();
    //getObjContrato().processRecalcular();
    getObjContrato().markForChange();
    calculeReembolsos();

    //new BizPNRTicket().procCalculeOverDetalle(this,null,null,new Date());
   	// 	processPopulate(null);
  }
  
  public void processUpdateAndCalcule() throws Exception {
   	super.update();
  	clean();
   	calcule(true);
   	super.update();
   }	 
  public void processClonarBaseYAux() throws Exception {
		pPaxBase.setValue(this.pPax.getValue());
		pPaxAux.setValue(this.pPax.getValue());
		pDestinoNegadoBase.setValue(pDestinoNegado.getValue());
		pDestinoNegadoAux.setValue(pDestinoNegado.getValue());
		pOrigenNegadoBase.setValue(pOrigenNegado.getValue());
		pOrigenNegadoAux.setValue(pOrigenNegado.getValue());
		pInterlinealBase.setValue(pInterlineal.getValue());
		pInterlinealAux.setValue(pInterlineal.getValue());
		pExchangeBase.setValue(pExchange.getValue());
		pExchangeAux.setValue(pExchange.getValue());

		pTipoPasajeroExcludedAux.setValue(pTipoPasajeroExcluded.getValue());
		pTipoPasajeroExcludedBase.setValue(pTipoPasajeroExcluded.getValue());
		pTipoPasajeroIncludedAux.setValue(pTipoPasajeroIncluded.getValue());
		pTipoPasajeroIncludedBase.setValue(pTipoPasajeroIncluded.getValue());
		pFareBasicExcludedAux.setValue(pFareBasicExcluded.getValue());
		pFareBasicExcludedBase.setValue(pFareBasicExcluded.getValue());
		pFareBasicIncludedAux.setValue(pFareBasicIncluded.getValue());
		pFareBasicIncludedBase.setValue(pFareBasicIncluded.getValue());
		pDestinoAeropuertoAux.setValue(pDestinoAeropuerto.getValue());
		pDestinoAeropuertoBase.setValue(pDestinoAeropuerto.getValue());
		pOrigenAeropuertoAux.setValue(pOrigenAeropuerto.getValue());
		pOrigenAeropuertoBase.setValue(pOrigenAeropuerto.getValue());
		pDestinoContinenteAux.setValue(pDestinoContinente.getValue());
		pDestinoContinenteBase.setValue(pDestinoContinente.getValue());
		pOrigenContinenteAux.setValue(pOrigenContinente.getValue());
		pOrigenContinenteBase.setValue(pOrigenContinente.getValue());
		pDestinoZonaAux.setValue(pDestinoZona.getValue());
		pDestinoZonaBase.setValue(pDestinoZona.getValue());
		pOrigenZonaAux.setValue(pOrigenZona.getValue());
		pOrigenZonaBase.setValue(pOrigenZona.getValue());
		pDestinoPaisAux.setValue(pDestinoPais.getValue());
		pDestinoPaisBase.setValue(pDestinoPais.getValue());
		pOrigenPaisAux.setValue(pOrigenPais.getValue());
		pOrigenPaisBase.setValue(pOrigenPais.getValue());
		pViceversaBase.setValue(pViceversa.getValue());
		pViceversaAux.setValue(pViceversa.getValue());
		pPrimeraBase.setValue(pPrimera.getValue());
		pPrimeraAux.setValue(pPrimera.getValue());
		pDomesticoAux.setValue(pDomestico.getValue());
		pDomesticoBase.setValue(pDomestico.getValue());
		pVoladoBase.setValue(pVolado.getValue());
		pVoladoAux.setValue(pVolado.getValue());
		pMercadosAux.setValue(pMercados.getValue());
		pMercadosBase.setValue(pMercados.getValue());
		pNoMercadosAux.setValue(pNoMercados.getValue());
		pNoMercadosBase.setValue(pNoMercados.getValue());
		pClasesExcludedAux.setValue(pClasesExcluded.getValue());
		pClasesExcludedBase.setValue(pClasesExcluded.getValue());
		pClasesIncludedAux.setValue(pClasesIncluded.getValue());
		pClasesIncludedBase.setValue(pClasesIncluded.getValue());
		pGDSAux.setValue(pGDS.getValue());
		pGDSBase.setValue(pGDS.getValue());
		pNoGDSAux.setValue(pNoGDS.getValue());
		pNoGDSBase.setValue(pNoGDS.getValue());
		pVuelosAux.setValue(pVuelos.getValue());
		pVuelosBase.setValue(pVuelos.getValue());
		pNoVuelosAux.setValue(pNoVuelos.getValue());
		pNoVuelosBase.setValue(pNoVuelos.getValue());
		pRutasAux.setValue(pRutas.getValue());
		pRutasBase.setValue(pRutas.getValue());
		pAndOrAux.setValue(pAndOr.getValue());
		pAndOrBase.setValue(pAndOr.getValue());
		

}
  
  
  public void processUpdateAsociados() throws Exception {
  	JRecords<BizDetalle> recs = new JRecords<BizDetalle>(BizDetalle.class);
  	recs.addFilter("company", getCompany());
  	if (getVariable()!=0) recs.addFilter("variable", getVariable());
  	recs.addFilter("variable_ganancia", getVariableGanancia());
  	JIterator<BizDetalle> it = recs.getStaticIterator();
  	while (it.hasMoreElements()) {
  		BizDetalle d = it.nextElement();
  		if (d.getId()==this.getId() && d.getLinea()==this.getLinea()) continue;  		
			BizDetalle det=d.getObjLogicaInstance().getBiz();
			det.copyProperties(d);

  		det.setDestinoNegado(this.isDestinoNegado());
  		det.setAerolineas(this.getRawAerolineas());
  		det.setAerolineasPlaca(this.getRawAerolineasPlaca());
  		det.setNoAerolineas(this.getRawNoAerolineas());
  		det.setNoAerolineasPlaca(this.getRawNoAerolineasPlaca());
  		det.setIdAerolinea(this.getIdAerolinea());
  		det.setDolares(this.isDolares());
  		det.setDestinoNegadoBase(this.isDestinoNegadoBase());
  		det.setDestinoNegadoAux(this.isDestinoNegadoAux());
  		det.setIatas(this.getRawIatas());
  		det.setNoIatas(this.getRawNoIatas());
  		det.setGDS(this.getRawGDS());
  		det.setGrupo(this.getGrupo());
  		det.setNoGDS(this.getRawNoGDS());
  		det.setGDSBase(this.getRawGDSBase());
  		det.setNoGDSBase(this.getRawNoGDSBase());
  		det.setGDSAux(this.getRawGDSAux());
  		det.setNoGDSAux(this.getRawNoGDSAux());
  		det.setInterlineal(this.isInterlineal());
  		det.setInterlinealBase(this.isInterlinealBase());
  		det.setExchange(this.isExchange());
  		det.setExchangeBase(this.isExchangeBase());
  		det.setMS(this.getMS());
  		det.setYQ(this.isYQ());
  		det.setOrigenNegado(this.isOrigenNegado());
  		det.setOrigenNegadoBase(this.isOrigenNegadoBase());
  		det.pPax.setValue(this.pPax.getValue());
  		det.pPaxBase.setValue(this.pPaxBase.getValue());
  		det.pPaxAux.setValue(this.pPaxAux.getValue());
  		det.pViceversa.setValue(this.pViceversa.getValue());
  		det.pPrimera.setValue(this.pPrimera.getValue());
  		det.pMeses.setValue(this.pMeses.getValue());
    	det.pVolado.setValue(this.pVolado.getValue());
  		det.pDomestico.setValue(this.isDomestic()?"S":this.isInternational()?"N":null);
  		det.pInterlineal.setValue(this.pInterlineal.getValue());
  		det.pExchange.setValue(this.pExchange.getValue());
  		det.pClasesExcluded.setValue(this.pClasesExcluded.getValue());
  		det.pClasesIncluded.setValue(this.pClasesIncluded.getValue());
  		det.pGDS.setValue(this.pGDS.getValue());
  		det.pNoGDS.setValue(this.pNoGDS.getValue());
  		det.pGDSAux.setValue(this.pGDSAux.getValue());
  		det.pNoGDSAux.setValue(this.pNoGDSAux.getValue());
  		det.pGDSBase.setValue(this.pGDSBase.getValue());
  		det.pNoGDSBase.setValue(this.pNoGDSBase.getValue());
  		det.pTipoPasajeroExcluded.setValue(this.pTipoPasajeroExcluded.getValue());
  		det.pTipoPasajeroIncluded.setValue(this.pTipoPasajeroIncluded.getValue());
   		det.pTipoPasajeroExcludedBase.setValue(this.pTipoPasajeroExcludedBase.getValue());
  		det.pTipoPasajeroIncludedBase.setValue(this.pTipoPasajeroIncludedBase.getValue());
   		det.pTipoPasajeroExcludedAux.setValue(this.pTipoPasajeroExcludedAux.getValue());
  		det.pTipoPasajeroIncludedAux.setValue(this.pTipoPasajeroIncludedAux.getValue());
  		det.pFareBasicExcluded.setValue(this.pFareBasicExcluded.getValue());
  		det.pFareBasicIncluded.setValue(this.pFareBasicIncluded.getValue());
   		det.pFareBasicExcludedBase.setValue(this.pFareBasicExcludedBase.getValue());
  		det.pFareBasicIncludedBase.setValue(this.pFareBasicIncludedBase.getValue());
   		det.pFareBasicExcludedAux.setValue(this.pFareBasicExcludedAux.getValue());
  		det.pFareBasicIncludedAux.setValue(this.pFareBasicIncludedAux.getValue());
  		det.pDestinoAeropuerto.setValue(this.pDestinoAeropuerto.getValue());
  		det.pOrigenAeropuerto.setValue(this.pOrigenAeropuerto.getValue());
  		det.pDestinoZona.setValue(this.pDestinoZona.getValue());
  		det.pOrigenZona.setValue(this.pOrigenZona.getValue());
  		det.pDestinoPais.setValue(this.pDestinoPais.getValue());
  		det.pOrigenPais.setValue(this.pOrigenPais.getValue());
  		det.pDestinoContinente.setValue(this.pDestinoContinente.getValue());
  		det.pOrigenContinente.setValue(this.pOrigenContinente.getValue());
  		det.pMercados.setValue(this.pMercados.getValue());
  		det.pNoMercados.setValue(this.pNoMercados.getValue());
   		det.pViceversaBase.setValue(this.pViceversaBase.getValue());
   		det.pPrimeraBase.setValue(this.pPrimeraBase.getValue());
  		det.pVoladoBase.setValue(this.pVoladoBase.getValue());
  		det.pDomesticoBase.setValue(this.isDomesticBase()?"S":this.isInternationalBase()?"N":null);
  		det.pInterlinealBase.setValue(this.pInterlinealBase.getValue());
  		det.pExchangeBase.setValue(this.pExchangeBase.getValue());
  		det.pClasesExcludedBase.setValue(this.pClasesExcludedBase.getValue());
  		det.pClasesIncludedBase.setValue(this.pClasesIncludedBase.getValue());
  		det.pSepFecha.setValue(this.pSepFecha.getValue());
  		det.pDestinoAeropuertoBase.setValue(this.pDestinoAeropuertoBase.getValue());
  		det.pOrigenAeropuertoBase.setValue(this.pOrigenAeropuertoBase.getValue());
  		det.pDestinoZonaBase.setValue(this.pDestinoZonaBase.getValue());
  		det.pOrigenZonaBase.setValue(this.pOrigenZonaBase.getValue());
  		det.pDestinoPaisBase.setValue(this.pDestinoPaisBase.getValue());
  		det.pOrigenPaisBase.setValue(this.pOrigenPaisBase.getValue());
  		det.pDestinoContinenteBase.setValue(this.pDestinoContinenteBase.getValue());
  		det.pOrigenContinenteBase.setValue(this.pOrigenContinenteBase.getValue());
  		det.pMercadosBase.setValue(this.pMercadosBase.getValue());
  		det.pNoMercadosBase.setValue(this.pNoMercadosBase.getValue());
  		det.pViceversaAux.setValue(this.pViceversaAux.getValue());
  		det.pPrimeraAux.setValue(this.pPrimeraAux.getValue());
  		det.pVoladoAux.setValue(this.pVoladoAux.getValue());
  		det.pDomesticoAux.setValue(this.isDomesticAux()?"S":this.isInternationalAux()?"N":null);
  		det.pInterlinealAux.setValue(this.pInterlinealAux.getValue());
  		det.pExchangeAux.setValue(this.pExchangeAux.getValue());
  		det.pClasesExcludedAux.setValue(this.pClasesExcludedAux.getValue());
  		det.pClasesIncludedAux.setValue(this.pClasesIncludedAux.getValue());
  		det.pDestinoAeropuertoAux.setValue(this.pDestinoAeropuertoAux.getValue());
  		det.pOrigenAeropuertoAux.setValue(this.pOrigenAeropuertoAux.getValue());
  		det.pDestinoZonaAux.setValue(this.pDestinoZonaAux.getValue());
  		det.pOrigenZonaAux.setValue(this.pOrigenZonaAux.getValue());
  		det.pDestinoPaisAux.setValue(this.pDestinoPaisAux.getValue());
  		det.pOrigenPaisAux.setValue(this.pOrigenPaisAux.getValue());
  		det.pDestinoContinenteAux.setValue(this.pDestinoContinenteAux.getValue());
  		det.pOrigenContinenteAux.setValue(this.pOrigenContinenteAux.getValue());
  		det.pMercadosAux.setValue(this.pMercadosAux.getValue());
  		det.pNoMercadosAux.setValue(this.pNoMercadosAux.getValue());
  		det.pMSAux.setValue(this.pMSAux.getValue());
  		det.pMSBase.setValue(this.pMSBase.getValue());
  		det.pVuelos.setValue(this.pVuelos.getValue());
  		det.pVuelosBase.setValue(this.pVuelosBase.getValue());
  		det.pVuelosAux.setValue(this.pVuelosAux.getValue());
  		det.pRutas.setValue(this.pRutas.getValue());
  		det.pNoRutas.setValue(this.pNoRutas.getValue());
  		det.pRutasBase.setValue(this.pRutasBase.getValue());
  		det.pRutasAux.setValue(this.pRutasAux.getValue());
  		det.pTipoViaje.setValue(this.pTipoViaje.getValue());
  		det.pMultidestino.setValue(this.pMultidestino.getValue());
  		det.pStopover.setValue(this.pStopover.getValue());
  		det.pCambio.setValue(this.pCambio.getValue());
  		det.pVueltaMundo.setValue(this.pVueltaMundo.getValue());
//	 		det.pFechaEmisionDesde.setValue(this.pFechaEmisionDesde.getValue());
//  		det.pFechaEmisionHasta.setValue(this.pFechaEmisionHasta.getValue());
//  		det.pFechaVueloDesde.setValue(this.pFechaVueloDesde.getValue());
//  		det.pFechaVueloHasta.setValue(this.pFechaVueloHasta.getValue());
//  		det.pFechaBlockoutDesde.setValue(this.pFechaBlockoutDesde.getValue());
//  		det.pFechaBlockoutHasta.setValue(this.pFechaBlockoutHasta.getValue());
 			
  		
   		det.processUpdateAndCalcule();

    }
  }
  public String getConclusion() throws Exception {
  	Calendar hoy = Calendar.getInstance();
  	hoy.setTime(new Date());
  	Double data=getValorActual();
  	BizNivel nivel=getObjCalculeNivelAlcanzado();
  	String conclusion="";
  	if (nivel!=null) {
	  	conclusion+=JTools.getDoubleNumberEmbedded(nivel.getDescripcion())==0?"Se llego al objetivo":JLanguage.translate("Se llego al "+nivel.getDescripcion()+" de premio");
	 	}
  	if (getObjContrato().getFechaHasta().after(new Date())) {
    	if (data>=getValorObjetivo()) {
      	Calendar f = getFechaCumplimiento();
      	if (f!=null)
      		conclusion+= (conclusion.equals("")?"Objetivo se cumplirá el ":", el siguiente objetivo se cumplirá el ")+JDateTools.DateToString(f.getTime());
    	} else {
      	Calendar f = getFechaCumplimientoDesdeHoy();
      	if (f==null) 
      		conclusion+= JLanguage.translate(conclusion.equals("")?"NO se cumplimentará el objetivo":", el siguiente objetivo NO se cumplirá");
      	else
      		conclusion+= JLanguage.translate(conclusion.equals("")?"Objetivo se cumplirá el ":", el siguiente objetivo se cumplirá el ")+JDateTools.DateToString(f.getTime());
    	}
  	}
  	return conclusion;
  }
  public double getFirstMeta() throws Exception {
  	BizNivel nivel=getFirstNivel();
  	if (nivel==null) return 0f;
  	return nivel.getValor();
  }  
  public String getDescrVariable() throws Exception {
  	if (!isNullDescripcion()) return getDescripcion();
  	return getObjEvent().getDescripcion();
  }  
  public String getDescrSqlAux1Meta() throws Exception {
  	return getObjEvent()==null?"":getObjEvent().getDescrConsultaAux1();
  }   
  public String getDescrSqlAux2Meta() throws Exception {
  	return getObjEvent()==null?"":getObjEvent().getDescrConsultaAux2();
  }  
  public String getDescrSqlAux1Base() throws Exception {
  	return getObjEventGanancia()==null?"":getObjEventGanancia().getDescrConsultaAux1();
  }  
  public String getDescrSqlAux2Base() throws Exception {
   	return getObjEventGanancia()==null?"":getObjEventGanancia().getDescrConsultaAux2();
  }  
  public String getDescrContrato() throws Exception {
  	return getObjContrato().getDescripcion();
  }  
  Double aux1meta;
  Double aux2meta;
  Double aux1base;
  Double aux2base;
  public void clearCache() throws Exception {
  	aux2base=null;
  	aux1base=null;
  	aux2meta=null;
  	aux1meta=null;
  }
  
   public double getSqlAux1Meta() throws Exception {
  	 if (aux1meta!=null) return aux1meta;
		Calendar desde = Calendar.getInstance();
		desde.setTime(hasFechaDesdeCalculo()?getFechaDesdeCalculo():getObjContrato().getFechaDesde());
		Calendar hoy = Calendar.getInstance();
		hoy.setTime(hasFechaCalculo()?getFechaCalculo():getObjContrato().getFechaHasta());
		return aux1meta=getObjEvent().getValorAux1(desde,hoy, "");
  }
  public double getSqlAux2Meta() throws Exception {
 	 if (aux2meta!=null) return aux2meta;
	Calendar desde = Calendar.getInstance();
		desde.setTime(hasFechaDesdeCalculo()?getFechaDesdeCalculo():getObjContrato().getFechaDesde());
		Calendar hoy = Calendar.getInstance();
		hoy.setTime(hasFechaCalculo()?getFechaCalculo():getObjContrato().getFechaHasta());
		return aux2meta=getObjEvent().getValorAux2(desde,hoy, "");
  }
  public double getSqlAux1Base() throws Exception {
 	 if (aux1base!=null) return aux1base;
	Calendar desde = Calendar.getInstance();
		desde.setTime(hasFechaDesdeCalculo()?getFechaDesdeCalculo():getObjContrato().getFechaDesde());
		Calendar hoy = Calendar.getInstance();
		hoy.setTime(hasFechaCalculo()?getFechaCalculo():getObjContrato().getFechaHasta());
		return aux1base=getObjEventGanancia().getValorAux1(desde,hoy, "");
  }
  public double getSqlAux2Base() throws Exception {
  	 if (aux2base!=null) return aux2base;
	Calendar desde = Calendar.getInstance();
		desde.setTime(hasFechaDesdeCalculo()?getFechaDesdeCalculo():getObjContrato().getFechaDesde());
		Calendar hoy = Calendar.getInstance();
		hoy.setTime(hasFechaCalculo()?getFechaCalculo():getObjContrato().getFechaHasta());
		return aux2base=getObjEventGanancia().getValorAux2(desde,hoy, "");
  }
  public Calendar getFechaCumplimiento() throws Exception {
  	Calendar hoy = Calendar.getInstance();
  	hoy.setTime(new Date());
  	
  	JRecords<BizSerieCalculada> serie = new JRecords<BizSerieCalculada>(BizSerieCalculada.class);
   	serie.addFilter("modelo", getModelo());
   	serie.addFilter("variable", getVariable());
   	serie.addFilter("acumulado", getValorObjetivo()).setOperator(">=");
   	if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isDependant()) {
   		serie.addFilter("company", BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getTicketCompany());
   		serie.addFilter("customer_id_reducido", BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getCodigoCliente());
    }
   	else {
   		serie.addFilter("company", getCompany());       		
   	}
	   	
   	serie.addOrderBy("dia","asc");
  	JIterator<BizSerieCalculada> it =serie.getStaticIterator();
  	if (!it.hasMoreElements()) return null;
  	BizSerieCalculada s =it.nextElement();
  	Calendar c = Calendar.getInstance();
  	c.setTime(s.getFecha());
  	return s.getFecha().before(new Date())?null:c;
  }
  public Calendar getFechaCumplimientoDesdeHoy() throws Exception {
  	Calendar hoy = Calendar.getInstance();
  	hoy.setTime(new Date());
  	
  	JRecords<BizSerieCalculada> serie = new JRecords<BizSerieCalculada>(BizSerieCalculada.class);
   	serie.addFilter("modelo", getModelo());
   	serie.addFilter("variable", getVariable());
   	serie.addFilter("acumulado", getValorObjetivo()).setOperator(">=");
   	if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isDependant()) {
   		serie.addFilter("company", BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getTicketCompany());
   		serie.addFilter("customer_id_reducido", BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getCodigoCliente());
    }
   	else {
   		serie.addFilter("company", getCompany());       		
   	}
	   	   	serie.addFilter("dia", hoy.get(Calendar.DAY_OF_YEAR),">=");
  	serie.addOrderBy("dia","asc");
  	JIterator<BizSerieCalculada> it =serie.getStaticIterator();
  	if (!it.hasMoreElements()) return null;
  	BizSerieCalculada s =it.nextElement();
  	Calendar c = Calendar.getInstance();
  	c.set(Calendar.YEAR, (int) s.getAnio());
  	c.set(Calendar.DAY_OF_YEAR,(int) s.getDia());
  	return c;
  }
  public double calculeAcumulado(Calendar fechaDesde,Calendar fecha) throws Exception {
    	JRecords<BizSerieCalculada> serie = new JRecords<BizSerieCalculada>(BizSerieCalculada.class);
     	serie.addFilter("modelo", getModelo());
     	serie.addFilter("variable", getVariable());
     	if (fechaDesde!=null) {
	     	serie.addFilter("anio", fechaDesde.get(Calendar.YEAR), ">=");
	     	serie.addFilter("dia", fechaDesde.get(Calendar.DAY_OF_YEAR), ">=");
	   	}
     	serie.addFilter("anio", fecha.get(Calendar.YEAR), "<=");
     	serie.addFilter("dia", fecha.get(Calendar.DAY_OF_YEAR), "<=");
     	serie.addFilter("valor", "0","<>");
     	if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isDependant()) {
     		serie.addFilter("company", BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getTicketCompany());
     		serie.addFilter("customer_id_reducido", BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getCodigoCliente());
      }
     	else {
     		serie.addFilter("company", getCompany());       		
     	}
 	   	
 		 	serie.addOrderBy("anio","desc");
    	serie.addOrderBy("dia","desc");
    	serie.setPagesize(1);
    	JIterator<BizSerieCalculada> it =serie.getStaticIterator();
    	if (!it.hasMoreElements()) return 0;
    	BizSerieCalculada s =it.nextElement();
    	return s.getAcumulado();
  }
  
  public void processEliminarSerieCalculada() throws Exception {
  	if (isNullModelo()) return;
		JBaseRegistro reg = JBaseRegistro.recordsetFactory();
		reg.Execute("delete from bsp_serie_calculada where modelo=" + getModelo() );
		reg.close();
		


  }
  public void processEliminarSerieCalculada(long variable) throws Exception {
  	if (isNullModelo()) return;
		JBaseRegistro reg = JBaseRegistro.recordsetFactory();
		reg.Execute("delete from bsp_serie_calculada where modelo=" + getModelo() + " and  variable="+variable);
		reg.close();
		


  }
   
  
  @Override
  public void processDelete() throws Exception {
  	JRecords<BizNivel> niveles = new JRecords<BizNivel>(BizNivel.class);
  	niveles.addFilter("id", getId());
  	niveles.addFilter("linea", getLinea());
  	niveles.processDeleteAll();
  	JRecords<BizQueVender> info = new JRecords<BizQueVender>(BizQueVender.class);
  	info.addFilter("company", getCompany());
  	info.addFilter("id_contrato", getId());
  	info.addFilter("id_detalle", getLinea());
  	info.processDeleteAll();
  	processEliminarSerieCalculada();
  	super.processDelete();
  	try {
			if (getObjEventGanancia()!=null && getObjEventGanancia().isInvisible()) {
				getObjEventGanancia().processDelete();
			}
		} catch (Exception e) {
			//puede fallar por estar utilizada por otro
		}
  	try {
			if (getObjEventAuxiliar()!=null && getObjEventAuxiliar().isInvisible()) {
				getObjEventAuxiliar().processDelete();
			}
		} catch (Exception e) {
			//puede fallar por estar utilizada por otro
		}
  	try {
			if (getObjEvent()!=null && getObjEvent().isInvisible()) {
				getObjEvent().processDelete();
			}
		} catch (Exception e) {
			//puede fallar por estar utilizada por otro
		}
		String sql = "UPDATE public.tur_pnr_boleto ";
		sql+=" SET contratos_back = TRIM(BOTH ',' FROM regexp_replace(contratos_back, '(^|,)\\s*"+getLinea()+"\\s*(,|$)', ',', 'g')) ";
		sql+=" WHERE contratos_back LIKE '%"+getLinea()+"%' ";
		
		JBaseRegistro reg;
		reg = JBaseRegistro.recordsetFactory();
		reg.Execute(sql);
		reg.close();
  	
		sql =" UPDATE public.tur_pnr_boleto ";
		sql+=" SET porcentajeover=0, importeover=0, upfront_ref = null, importe_prorr_over=0, porcentaje_prorr_over=0 ";
		sql+=" WHERE upfront_ref = "+getLinea()+" ";
		
		reg = JBaseRegistro.recordsetFactory();
		reg.Execute(sql);
		reg.close();
  }
	public void execProcessPopulate() throws Exception {
		JExec oExec = new JExec(this, "processPopulate") {

			@Override
			public void Do() throws Exception {
		   	processPopulate(null,true);
			}
		};
		oExec.execute();
	}
	public void execProcessCopiar() throws Exception {
		JExec oExec = new JExec(this, "processPopulate") {

			@Override
			public void Do() throws Exception {
				processCopiar();
			}
		};
		oExec.execute();
	}
	public void execProcessCopyVariaciones() throws Exception {
		JExec oExec = new JExec(this, "processCopyVariaciones") {

			@Override
			public void Do() throws Exception {
				processCopyVariaciones();
			}
		};
		oExec.execute();
	}
	 public  void processCopyVariaciones() throws Exception {
		  JRecords<BizVariacion> vars = new JRecords<BizVariacion>(BizVariacion.class);
		  vars.addFixedFilter("(company is null or company='"+getCompany()+"')");
		  JIterator<BizVariacion> it = vars.getStaticIterator();
		  while (it.hasMoreElements()) {
		  	BizVariacion var = it.nextElement();
		  	BizVariacionPaticular part = new BizVariacionPaticular();
		  	part.addFilter("company", getCompany());
		  	part.addFilter("id_contrato", getId());
		  	part.addFilter("linea_contrato", getLinea());
		  	part.addFilter("linea", var.getLinea());
		  	part.dontThrowException(true);
		  	part.read();
		  	part.setCompany(getCompany());
		  	part.setIdContrato(getId());
		  	part.setLineaContrato(getLinea());
		  	part.setLinea(var.getLinea());
		  	part.setVariacion(var.getVariacion());
		  	part.setFechaDesde(var.getFechaDesde());
		  	part.setFechaHasta(var.getFechaHasta());
		  	part.setDescripcion(var.getDescripcion());
		  	part.processUpdateOrInsertWithCheck();
		  	
		  	
		  }
	 }
		public void execAutogenerar() throws Exception {
			JExec oExec = new JExec(this, "execAutogenerar") {

				@Override
				public void Do() throws Exception {
					processAutogenerar();
				}
			};
			oExec.execute();
		}
	  public  void processAutogenerar() throws Exception {
	  	autogenerar();
	  	super.update();
	  	
	  }
	  public  void processMercado() throws Exception {
	  	
	  }
	 
	 public  void processCheckForCalculo() throws Exception {
	   	if (getObjLogicaInstance().needIndicadorObjetivo())
	   		processPopulate(null,false,getVariable());
	   	if (getObjLogicaInstance().needIndicadorObjetivoGanancia())
	   		processPopulate(null,false,getVariableGanancia());

	 }	 
	 public  void processCopiar() throws Exception {
		 BizContrato contrato = getObjContrato();
			Calendar fechaIniNC = Calendar.getInstance();
			Calendar fechaFinNC = Calendar.getInstance();
			fechaIniNC.setTime(contrato.getFechaHasta());
			fechaIniNC.add(Calendar.DAY_OF_MONTH, 1);
			long difInMonths = (long)((float)(contrato.getFechaHasta().getTime() -contrato.getFechaDesde().getTime()) / ((long)29 * 24 * 60 * 60 * 1000));
			if (difInMonths==0) difInMonths=1;
			fechaFinNC.setTime(fechaIniNC.getTime());
			fechaFinNC.add(Calendar.MONTH, (int)difInMonths);
			fechaFinNC.add(Calendar.DAY_OF_MONTH, -1);

			BizContrato na = new BizContrato();
			
			JRecords<BizContrato> candidatos = new JRecords<BizContrato>(BizContrato.class);
			candidatos.addFilter("company", contrato.getCompany());
			candidatos.addFilter("descripcion", contrato.getDescripcion());
			candidatos.addFilter("fecha_desde", fechaIniNC.getTime(), ">=");
			candidatos.addFilter("fecha_desde", fechaFinNC.getTime(), "<");
			candidatos.addOrderBy("fecha_desde", "ASC");
			candidatos.setTop(1);
			JIterator<BizContrato> it = candidatos.getStaticIterator();
			if (!it.hasMoreElements()) return;
			BizContrato next = it.nextElement();
					//if (ampl.isAutogenerado()) continue;
			BizDetalle newDet = null;
			ILogicaContrato l = this.getObjLogicaInstance();
			newDet = l.getBiz();
			newDet.copyProperties(this);
			newDet.setCompany(next.getCompany());
			newDet.setId(next.getId());
			newDet.setNullToLinea();
			
			newDet.setFechaPrediccion(null);
			this.processClon(newDet);
			newDet.autogenerar();

	 }
	 
		public void processPopulate(Date fechaEsti, boolean forzar) throws Exception {
			if (getObjLogicaInstance().needIndicadorObjetivo())
				processPopulate(fechaEsti, forzar, getVariable());
			if (getObjLogicaInstance().needIndicadorObjetivoGanancia())
				processPopulate(fechaEsti, forzar, getVariableGanancia());
		}
	 public  void processPopulate(Date fechaEsti,boolean forzar, long variable) throws Exception {
		  	Date fecha = fechaEsti;
		 processUpdateDatos();
  	if (fecha==null) {
  		BizSqlEventDato datoUltimo = new BizSqlEventDato();
	 		datoUltimo.addFilter("id_evento",variable);
			fecha = datoUltimo.SelectMaxDate("fecha");
  		Calendar hoy = Calendar.getInstance();
  		Calendar ultimo = Calendar.getInstance();
  		hoy.setTime(new Date());
  		if ((fecha==null)) fecha = new Date();
  		ultimo.setTime(fecha);	
  		if (hoy.before(ultimo))
  			fecha=hoy.getTime();
  	}
  	if (!pFechaPrediccion.isNull()) {
    	Calendar expire = Calendar.getInstance();
    	Calendar prediccion = Calendar.getInstance();
    	expire.setTime(new Date());
    	prediccion.setTime(getFechaPrediccion());
    	if (prediccion.get(Calendar.MONTH)!=expire.get(Calendar.MONTH))
    		prediccion=null;
  	}
  	boolean predecir = true;
  	if (!pFechaPrediccion.isNull()) {
    	Calendar expire = Calendar.getInstance();
    	Calendar prediccion = Calendar.getInstance();
    	expire.setTime(new Date());
    	prediccion.setTime(getFechaPrediccion());
    	expire.add(Calendar.DAY_OF_MONTH, -1);
    	predecir = prediccion.before(expire);
  		
  	}
  	boolean calcule = true;
    
  	if (predecir || forzar) {
      	//  	IPrediccion predictor= new JNeuronalStandart();
      	//	IPrediccion predictor= new JNeuronalElman();
      	  	IPrediccion predictor= new JNeuronalSVM();
      	 		predictor.processPopulate(this, fecha,variable);
      	   	
      	  	setFechaPrediccion(new Date());
      	  	calcule=false;
        	}
  	
  	if (calcule) {
  		fecha =getFechaActual();
  		double valor =getCalculeValorActual();
  		if (valor!=0) {
    		BizSerieCalculada serie = new BizSerieCalculada();
    		serie.dontThrowException(true);
	  		if (serie.read(getCompany(), variable, fecha)) {
	  			serie.setValor(valor);
	  			serie.update();
	  		}
  		}
  	}
  	if (forzar)
  		this.calcule(true);
  	update();
  }
  @Override
  public void clean() throws Exception {
  	objContrato=null;
  	objEvent=null;
  	objEventGanancia=null;
  	objEventAuxiliar=null;
  	super.clean();
  }
  public Double getRealData(Calendar fecha,long variable,Calendar finEstimacion) throws Exception {
  	if (fecha!=null && fecha.after(finEstimacion))
  		return new Double(0);
  	double valor = 0;
   	BizSqlEventDato sql = new BizSqlEventDato();
   	sql.dontThrowException(true);
   	if (sql.read(variable, fecha.getTime())) {
   		valor= sql.getValue();
   	} else
   		return null;
  	return valor;
  }
  
  BizContrato objContrato;
  public void setObjContrato(BizContrato c) throws Exception {
  	this.objContrato=c;
  }
  public BizContrato getObjContrato() throws Exception {
  	if (objContrato!=null) return objContrato;
  	BizContrato contrato = new BizContrato();
  	contrato.read(getId());
  	return objContrato=contrato;
  }
  
  public BizNivel getObjNivelSiguiente() throws Exception {
  	double valor = getValorActual();
   	JRecords<BizNivel> n = new JRecords<BizNivel>(BizNivel.class);
  	n.addFilter("company", getCompany());
  	n.addFilter("id", getId());
  	n.addFilter("linea", getLinea());
  	n.addFilter("valor", ""+valor,">");
  	n.addOrderBy("valor","ASC");
  	n.setTop(1);
  	JIterator<BizNivel> it = n.getStaticIterator();
  	if (!it.hasMoreElements()) return null;
  	return it.nextElement();
  } 
  
  public BizNivel getObjNivelFavorito() throws Exception {
  	if (pValor.isNull()||pValor.getValue()==0) return getObjNivelSiguiente();
  	BizNivel n = new BizNivel();
  	n.dontThrowException(true);
  	if (!n.read(pCompany.getValue(),pId.getValue(),pValor.getValue())) return getObjNivelSiguiente();
  	return n;
  }
  public BizNivel getObjNivelMayor() throws Exception {
  	JRecords<BizNivel> n = new JRecords<BizNivel>(BizNivel.class);
  	n.addFilter("company", getCompany());
  	n.addFilter("id", getId());
  	n.addFilter("linea", getLinea());
  	n.addOrderBy("valor","DESC");
  	n.setTop(1);
  	JIterator<BizNivel> it = n.getStaticIterator();
  	if (!it.hasMoreElements()) return null;
  	
  	return it.nextElement();
  }

	
	static JMap<String,String> gMapPeriodos;
  public static JMap<String,String> getPeriodos() throws Exception {
  	if (gMapPeriodos!=null) return gMapPeriodos;
  	gMapPeriodos = JCollectionFactory.createOrderedMap();
  	gMapPeriodos.addElement(MENSUAL, "MENSUAL");
  	gMapPeriodos.addElement(BIMESTRAL, "Bimestral");
  	gMapPeriodos.addElement(TRIMESTRAL, "Trimestral");
  	gMapPeriodos.addElement(CUATRIMESTRAL, "Cuatrimestral");
  	gMapPeriodos.addElement(SEMESTRAL, "Semestral");
  	gMapPeriodos.addElement(ANUAL, "Anual");
  	return gMapPeriodos;
  }
  
//  public  void processUpdateData() throws Exception {
//  	String sql="";
//  	sql+="UPDATE bsp_serie_calculada SET valor=evt_sqleventos_datos.value from evt_sqleventos join evt_sqleventos_datos on evt_sqleventos_datos.id_evento= evt_sqleventos.id  where evt_sqleventos.id = bsp_serie_calculada.variable and evt_sqleventos.company = bsp_serie_calculada.company and evt_sqleventos_datos.fecha = bsp_serie_calculada.fecha and evt_sqleventos_datos.value <> bsp_serie_calculada.valor";
//		JBaseRegistro reg = JBaseRegistro.recordsetFactory();
//		reg.Execute(sql);
//		reg.close();
//  }
  
  static public  void processRePopulate(String company,long idVariable,Date fechaEsti, boolean news) throws Exception {
  	JRecords<BizDetalle> dets = new JRecords<BizDetalle>(BizDetalle.class);
  	dets.addFilter("company", company);
  	dets.addFixedFilter("(variable="+idVariable+" or variable_ganancia="+idVariable+" or variable_aux="+idVariable+")");
  	JIterator<BizDetalle> it = dets.getStaticIterator();
  	while (it.hasMoreElements()) {
  		BizDetalle det = it.nextElement();
  	//	PssLogger.logInfo("Calculando "+det.getId()+" "+det.getDescripcion());
			BizDetalle d=det.getObjLogicaInstance().getBiz();
			d.copyProperties(det);
	//		d.processPopulate(fechaEsti,!news);
  	}
  	
  }
  
  public String getErrorNiveles() throws Exception {
  	String error=null;
  	boolean hayniveles=false;
		JRecords<BizNivel> ampls = new JRecords<BizNivel>(BizNivel.class);
		ampls.addFilter("company", getCompany());
		ampls.addFilter("id", getId());
		ampls.addFilter("linea", getLinea());
		JIterator<BizNivel> it = ampls.getStaticIterator();
		while (it.hasMoreElements()) {
			BizNivel n=it.nextElement();
			hayniveles=true;
			error = n.getError();
			if (error!=null) break;
		}
		if (!hayniveles)
			return "";
		return error;
	}
	public BizDetalle processClon(BizDetalle newDoc) throws Exception {
		newDoc.processInsertClon();
		newDoc.setLinea(newDoc.getIdentity("linea"));
		JRecords<BizNivel> ampls = new JRecords<BizNivel>(BizNivel.class);
		ampls.addFilter("company", getCompany());
		ampls.addFilter("id", getId());
		ampls.addFilter("linea", getLinea());
		JIterator<BizNivel> it = ampls.getStaticIterator();
		while (it.hasMoreElements()) {
			BizNivel ampl = it.nextElement();
			BizNivel na = new BizNivel();
			na.copyProperties(ampl);
			na.setCompany(newDoc.getCompany());
			na.setId(newDoc.getId());
			na.setLinea(newDoc.getLinea());
			na.processInsertClon();
			
		}

		JRecords<BizProrrateo> pros = new JRecords<BizProrrateo>(BizProrrateo.class);
		pros.addFilter("company", getCompany());
		pros.addFilter("contrato", getId());
		pros.addFilter("detalle", getLinea());
		JIterator<BizProrrateo> itp = pros.getStaticIterator();
		while (itp.hasMoreElements()) {
			BizProrrateo ampl = itp.nextElement();
			BizProrrateo na = new BizProrrateo();
			na.copyProperties(ampl);
			na.setCompany(newDoc.getCompany());
			na.setContrato(newDoc.getId());
			na.setIdDetalle(newDoc.getLinea());
			na.processInsertClon();
			
		}

		
		BizDetalle det = this.getObjLogicaInstance().getBiz();
		det.copyProperties(this);
		if (!det.canUseEqualVariables()) {
			det.setNullToVariable();
			det.setNullToVariableAuxiliar();
			det.setNullToVariableGanancia();
			det.autogenerar();
			
		}
   	det.processPopulate(null,false);

		return newDoc;
	}
	public void autogenerar() throws Exception {
	}
	public boolean canUseEqualVariables() throws Exception {
		if (pFechaBlockoutDesde.isNotNull()) return false;
		if (pFechaBlockoutHasta.isNotNull()) return false;
		if (pFechaVueloHasta.isNotNull()) return false;
		if (pFechaVueloDesde.isNotNull()) return false;
		if (pFechaEmisionDesde.isNotNull()) return false;
		if (pFechaEmisionDesde.isNotNull()) return false;
			return true;
	}
  static private JMap<String, String>  objCalculosBase;
  public static JMap<String, String> getCalculosBase() throws Exception {
  	if (objCalculosBase!=null) return objCalculosBase;
  	JMap<String, String> map = JCollectionFactory.createMap();
  	map.addElement("N", "Tarifa Neta" );
  	map.addElement("B", "Tarifa Bruta" );
  	map.addElement("H", "Tarifa neta solo Ida" );
    map.addElement("X", "Pax" );
  	return objCalculosBase=map;
  }
  static private JMap<String, String>  objCalculos;
  public static JMap<String, String> getCalculos() throws Exception {
  	if (objCalculos!=null) return objCalculos;
  	JMap<String, String> map = JCollectionFactory.createMap();
  	map.addElement("J", "MS Tarifa" );
  	map.addElement("C", "MS Cantidad" );
  	map.addElement("P", "Porcentaje sobre total" );
  	map.addElement("S", "Porcentaje solo Ida sobre total" );
  	map.addElement("N", "Tarifa Neta" );
  	map.addElement("B", "Tarifa Bruta" );
  	map.addElement("X", "Pax" );
   	map.addElement("I", "Porcentaje sobre clases/Fam excluidas" );
   	map.addElement("G", "Porcentaje sobre gds incluidos" );
     	return objCalculos=map;
  }
	static JMap<String,String> gMapLogicas;
  public static JMap<String,String> getLogicasContrato() throws Exception {
  	if (gMapLogicas!=null) return gMapLogicas;
  	gMapLogicas = JCollectionFactory.createOrderedMap();
  	gMapLogicas.addElement(JContratoNormal.class.getName(), "Contrato datamining");
  	gMapLogicas.addElement(JContratoCopa.class.getName(), "Contrato Copa por rutas");
  	gMapLogicas.addElement(JContratoCopaWS.class.getName(), "Contrato Copa WS");
  	gMapLogicas.addElement(JContratoCopaPorRutas.class.getName(), "Contrato Copa Por Rutas");
  	gMapLogicas.addElement(JContratoAvianca.class.getName(), "Contrato Avianca por puntos");
  	gMapLogicas.addElement(JContratoTriple.class.getName(), "Contrato datamining triple");
  	gMapLogicas.addElement(JContratoUpfront.class.getName(), "Contrato Upfront");
  	gMapLogicas.addElement(JContratoRuta.class.getName(), "Contrato por rutas");
  	gMapLogicas.addElement(JContratoBackendRuta.class.getName(), "Contrato Backend por rutas");
  	gMapLogicas.addElement(JContratoBackendDobleRuta.class.getName(), "Contrato Backend doble objetivo por rutas");
  	gMapLogicas.addElement(JContratoUpfrontRuta.class.getName(), "Contrato Upfront por rutas");
  	gMapLogicas.addElement(JContratoLatamFamilia.class.getName(), "Contrato Latam familia");
  	
  	return gMapLogicas;
  }
	static JMap<String,String> gMapHtmlLogicas;
  public static JMap<String,String> getLogicasHTMLContrato() throws Exception {
//  	if (gMapHtmlLogicas!=null) return gMapHtmlLogicas;
  	gMapHtmlLogicas = JCollectionFactory.createOrderedMap();
  	gMapHtmlLogicas.addElement(JContratoNormal.class.getName(), JTools.replaceForeignCharsForWeb("<HTML><HEAD><META HTTP-EQUIV=\"CONTENT-TYPE\" CONTENT=\"text/html; charset=windows-1252\"><TITLE></TITLE><META NAME=\"GENERATOR\" CONTENT=\"OpenOffice 4.1.3  (Win32)\"><META NAME=\"AUTHOR\" CONTENT=\"Ricardo Lamberti\"><META NAME=\"CREATED\" CONTENT=\"20170327;20080892\"><META NAME=\"CHANGEDBY\" CONTENT=\"Ricardo Lamberti\"><META NAME=\"CHANGED\" CONTENT=\"20170327;20333215\"></HEAD><BODY LANG=\"es-AR\" DIR=\"LTR\"><P ALIGN=CENTER><SPAN CLASS=\"sd-abs-pos\" STYLE=\"top: 0.61cm; width: 200px\"><IMG SRC=\"pss_icon/contrato.png\" NAME=\"gr&aacute;ficos1\" WIDTH=200 HEIGHT=200 BORDER=0><P></SPAN><P ALIGN=CENTER></P><P ALIGN=CENTER><FONT FACE=\"Malgun Gothic, sans-serif\"><FONT SIZE=4 STYLE=\"font-size: 15pt\">Contrato basado en datamining</FONT></FONT></P><BR><BR><P ALIGN=CENTER><FONT FACE=\"Malgun Gothic, sans-serif\">Descripcion:</FONT></P><BR><BR><P ALIGN=CENTER><FONT FACE=\"Malgun Gothic, sans-serif\">En este tipo de contratos se eligen dos indicadores: un indicador con el que se mide el valor a llegar de una lista de objetivos, y otro indicador que se utiliza como dato de entrada para calcular el premio.</FONT></P><P><BR><BR></P><BR><BR><BR><BR><BR><BR><BR><BR><BR><BR></BODY></HTML>"));
  	gMapHtmlLogicas.addElement(JContratoTriple.class.getName(), JTools.replaceForeignCharsForWeb("<HTML><HEAD><META HTTP-EQUIV=\"CONTENT-TYPE\" CONTENT=\"text/html; charset=windows-1252\"><TITLE></TITLE><META NAME=\"GENERATOR\" CONTENT=\"OpenOffice 4.1.3  (Win32)\"><META NAME=\"AUTHOR\" CONTENT=\"Ricardo Lamberti\"><META NAME=\"CREATED\" CONTENT=\"20170327;20080892\"><META NAME=\"CHANGEDBY\" CONTENT=\"Ricardo Lamberti\"><META NAME=\"CHANGED\" CONTENT=\"20170327;20333215\"></HEAD><BODY LANG=\"es-AR\" DIR=\"LTR\"><P ALIGN=CENTER><SPAN CLASS=\"sd-abs-pos\" STYLE=\"top: 0.61cm; width: 200px\"><IMG SRC=\"pss_icon/contrato.png\" NAME=\"gr&aacute;ficos1\" WIDTH=200 HEIGHT=200 BORDER=0><P></SPAN><P ALIGN=CENTER></P><P ALIGN=CENTER><FONT FACE=\"Malgun Gothic, sans-serif\"><FONT SIZE=4 STYLE=\"font-size: 15pt\">Contrato basado en datamining con tres variables</FONT></FONT></P><BR><BR><P ALIGN=CENTER><FONT FACE=\"Malgun Gothic, sans-serif\">Descripcion:</FONT></P><BR><BR><P ALIGN=CENTER><FONT FACE=\"Malgun Gothic, sans-serif\">En este tipo de contratos se eligen dos indicadores: un indicador con el que se mide el valor a llegar de una lista de objetivos, y otro indicador que se utiliza como dato de entrada para calcular el premio. Hay un tercer indicador que se usa segun el tipo de nivel.</FONT></P><P><BR><BR></P><BR><BR><BR><BR><BR><BR><BR><BR><BR><BR></BODY></HTML>"));
  	gMapHtmlLogicas.addElement(JContratoCopa.class.getName(), JTools.replaceForeignCharsForWeb("<HTML><HEAD><META HTTP-EQUIV=\"CONTENT-TYPE\" CONTENT=\"text/html; charset=windows-1252\"><TITLE></TITLE><META NAME=\"GENERATOR\" CONTENT=\"OpenOffice 4.1.3  (Win32)\"><META NAME=\"AUTHOR\" CONTENT=\"Ricardo Lamberti\"><META NAME=\"CREATED\" CONTENT=\"20170327;20080892\"><META NAME=\"CHANGEDBY\" CONTENT=\"Ricardo Lamberti\"><META NAME=\"CHANGED\" CONTENT=\"20170327;20333215\"></HEAD><BODY LANG=\"es-AR\" DIR=\"LTR\"><P ALIGN=CENTER><SPAN CLASS=\"sd-abs-pos\" STYLE=\"top: 0.61cm; width: 200px\"><IMG SRC=\"pss_icon/contrato.png\" NAME=\"gr&aacute;ficos1\" WIDTH=200 HEIGHT=200 BORDER=0><P></SPAN><P ALIGN=CENTER></P><P ALIGN=CENTER><FONT FACE=\"Malgun Gothic, sans-serif\"><FONT SIZE=4 STYLE=\"font-size: 15pt\">Contrato basado en rutas</FONT></FONT></P><BR><BR><P ALIGN=CENTER><FONT FACE=\"Malgun Gothic, sans-serif\">Descripcion:</FONT></P><BR><BR><P ALIGN=CENTER><FONT FACE=\"Malgun Gothic, sans-serif\">En este tipo de contratos se da un premio relacionado al market share de cada ruta con respecto al FMS de cada ruta. </FONT></P><P>Este tipo de contrato requiere la carga de la interfaz correspondiente otorgada por la aerolínea con todos los FMS para cada ruta.<BR><BR></P><BR><BR><BR><BR><BR><BR><BR><BR><BR><BR></BODY></HTML>"));
  	gMapHtmlLogicas.addElement(JContratoCopaWS.class.getName(), JTools.replaceForeignCharsForWeb("<HTML><HEAD><META HTTP-EQUIV=\"CONTENT-TYPE\" CONTENT=\"text/html; charset=windows-1252\"><TITLE></TITLE><META NAME=\"GENERATOR\" CONTENT=\"OpenOffice 4.1.3  (Win32)\"><META NAME=\"AUTHOR\" CONTENT=\"Ricardo Lamberti\"><META NAME=\"CREATED\" CONTENT=\"20170327;20080892\"><META NAME=\"CHANGEDBY\" CONTENT=\"Ricardo Lamberti\"><META NAME=\"CHANGED\" CONTENT=\"20170327;20333215\"></HEAD><BODY LANG=\"es-AR\" DIR=\"LTR\"><P ALIGN=CENTER><SPAN CLASS=\"sd-abs-pos\" STYLE=\"top: 0.61cm; width: 200px\"><IMG SRC=\"pss_icon/contrato.png\" NAME=\"gr&aacute;ficos1\" WIDTH=200 HEIGHT=200 BORDER=0><P></SPAN><P ALIGN=CENTER></P><P ALIGN=CENTER><FONT FACE=\"Malgun Gothic, sans-serif\"><FONT SIZE=4 STYLE=\"font-size: 15pt\">Contrato basado en rutas</FONT></FONT></P><BR><BR><P ALIGN=CENTER><FONT FACE=\"Malgun Gothic, sans-serif\">Descripcion:</FONT></P><BR><BR><P ALIGN=CENTER><FONT FACE=\"Malgun Gothic, sans-serif\">En este tipo de contratos se da un premio relacionado al market share de cada ruta con respecto al WS de cada ruta. </FONT></P><P>Este tipo de contrato requiere la carga de la interfaz correspondiente otorgada por la aerolínea con todos las ruta.<BR><BR></P><BR><BR><BR><BR><BR><BR><BR><BR><BR><BR></BODY></HTML>"));
  	gMapHtmlLogicas.addElement(JContratoCopaPorRutas.class.getName(), JTools.replaceForeignCharsForWeb("<HTML><HEAD><META HTTP-EQUIV=\"CONTENT-TYPE\" CONTENT=\"text/html; charset=windows-1252\"><TITLE></TITLE><META NAME=\"GENERATOR\" CONTENT=\"OpenOffice 4.1.3  (Win32)\"><META NAME=\"AUTHOR\" CONTENT=\"Ricardo Lamberti\"><META NAME=\"CREATED\" CONTENT=\"20170327;20080892\"><META NAME=\"CHANGEDBY\" CONTENT=\"Ricardo Lamberti\"><META NAME=\"CHANGED\" CONTENT=\"20170327;20333215\"></HEAD><BODY LANG=\"es-AR\" DIR=\"LTR\"><P ALIGN=CENTER><SPAN CLASS=\"sd-abs-pos\" STYLE=\"top: 0.61cm; width: 200px\"><IMG SRC=\"pss_icon/contrato.png\" NAME=\"gr&aacute;ficos1\" WIDTH=200 HEIGHT=200 BORDER=0><P></SPAN><P ALIGN=CENTER></P><P ALIGN=CENTER><FONT FACE=\"Malgun Gothic, sans-serif\"><FONT SIZE=4 STYLE=\"font-size: 15pt\">Contrato basado en rutas</FONT></FONT></P><BR><BR><P ALIGN=CENTER><FONT FACE=\"Malgun Gothic, sans-serif\">Descripcion:</FONT></P><BR><BR><P ALIGN=CENTER><FONT FACE=\"Malgun Gothic, sans-serif\">En este tipo de contratos se da un premio relacionado al market share de cada ruta con respecto al WS de cada ruta. </FONT></P><P>Este tipo de contrato requiere la carga de la interfaz correspondiente otorgada por la aerolínea con todos las ruta.<BR><BR></P><BR><BR><BR><BR><BR><BR><BR><BR><BR><BR></BODY></HTML>"));
  	gMapHtmlLogicas.addElement(JContratoAvianca.class.getName(), JTools.replaceForeignCharsForWeb("<HTML><HEAD><META HTTP-EQUIV=\"CONTENT-TYPE\" CONTENT=\"text/html; charset=windows-1252\"><TITLE></TITLE><META NAME=\"GENERATOR\" CONTENT=\"OpenOffice 4.1.3  (Win32)\"><META NAME=\"AUTHOR\" CONTENT=\"Ricardo Lamberti\"><META NAME=\"CREATED\" CONTENT=\"20170327;20080892\"><META NAME=\"CHANGEDBY\" CONTENT=\"Ricardo Lamberti\"><META NAME=\"CHANGED\" CONTENT=\"20170327;20333215\"></HEAD><BODY LANG=\"es-AR\" DIR=\"LTR\"><P ALIGN=CENTER><SPAN CLASS=\"sd-abs-pos\" STYLE=\"top: 0.61cm; width: 200px\"><IMG SRC=\"pss_icon/contrato.png\" NAME=\"gr&aacute;ficos1\" WIDTH=200 HEIGHT=200 BORDER=0><P></SPAN><P ALIGN=CENTER></P><P ALIGN=CENTER><FONT FACE=\"Malgun Gothic, sans-serif\"><FONT SIZE=4 STYLE=\"font-size: 15pt\">Contrato basado en puntos</FONT></FONT></P><BR><BR><P ALIGN=CENTER><FONT FACE=\"Malgun Gothic, sans-serif\">Descripcion:</FONT></P><BR><BR><P ALIGN=CENTER><FONT FACE=\"Malgun Gothic, sans-serif\">En este tipo de contratos se da un premio relacionado al puntaje obtenido por llegar al market share de cada ruta.</FONT></P><P><BR><BR><BR></P><BR><BR><BR><BR><BR><BR><BR><BR><BR><BR></BODY></HTML>"));
  	gMapHtmlLogicas.addElement(JContratoUpfront.class.getName(), JTools.replaceForeignCharsForWeb("<HTML><HEAD><META HTTP-EQUIV=\"CONTENT-TYPE\" CONTENT=\"text/html; charset=windows-1252\"><TITLE></TITLE><META NAME=\"GENERATOR\" CONTENT=\"OpenOffice 4.1.3  (Win32)\"><META NAME=\"AUTHOR\" CONTENT=\"Ricardo Lamberti\"><META NAME=\"CREATED\" CONTENT=\"20170327;20080892\"><META NAME=\"CHANGEDBY\" CONTENT=\"Ricardo Lamberti\"><META NAME=\"CHANGED\" CONTENT=\"20170327;20333215\"></HEAD><BODY LANG=\"es-AR\" DIR=\"LTR\"><P ALIGN=CENTER><SPAN CLASS=\"sd-abs-pos\" STYLE=\"top: 0.61cm; width: 200px\"><IMG SRC=\"pss_icon/contrato.png\" NAME=\"gr&aacute;ficos1\" WIDTH=200 HEIGHT=200 BORDER=0><P></SPAN><P ALIGN=CENTER></P><P ALIGN=CENTER><FONT FACE=\"Malgun Gothic, sans-serif\"><FONT SIZE=4 STYLE=\"font-size: 15pt\">Contrato upfont</FONT></FONT></P><BR><BR><P ALIGN=CENTER><FONT FACE=\"Malgun Gothic, sans-serif\">Descripcion:</FONT></P><BR><BR><P ALIGN=CENTER><FONT FACE=\"Malgun Gothic, sans-serif\">En este tipo de contratos se da un over de porcentaje inmediato.</FONT></P><P><BR><BR><BR></P><BR><BR><BR><BR><BR><BR><BR><BR><BR><BR></BODY></HTML>"));
  	gMapHtmlLogicas.addElement("NINGUNA", JTools.replaceForeignCharsForWeb( "<HTML><HEAD><META HTTP-EQUIV=\"CONTENT-TYPE\" CONTENT=\"text/html; charset=windows-1252\"><TITLE></TITLE><META NAME=\"GENERATOR\" CONTENT=\"OpenOffice 4.1.3  (Win32)\"><META NAME=\"AUTHOR\" CONTENT=\"Ricardo Lamberti\"><META NAME=\"CREATED\" CONTENT=\"20170327;20080892\"><META NAME=\"CHANGEDBY\" CONTENT=\"Ricardo Lamberti\"><META NAME=\"CHANGED\" CONTENT=\"20170327;20333215\"></HEAD><BODY LANG=\"es-AR\" DIR=\"LTR\"><P ALIGN=CENTER><SPAN CLASS=\"sd-abs-pos\" STYLE=\"top: 0.61cm; width: 200px\"><IMG SRC=\"pss_icon/contrato.png\" NAME=\"gr&aacute;ficos1\" WIDTH=200 HEIGHT=200 BORDER=0><P></SPAN><P ALIGN=CENTER></P><P ALIGN=CENTER><FONT FACE=\"Malgun Gothic, sans-serif\"><FONT SIZE=4 STYLE=\"font-size: 15pt\">Tipo Contrato</FONT></FONT></P><BR><BR><P ALIGN=CENTER><FONT FACE=\"Malgun Gothic, sans-serif\">Seleccione uno:</FONT></P><BR><BR><P ALIGN=CENTER><FONT FACE=\"Malgun Gothic, sans-serif\">Si hace click en cada opci�n podr� visualizar una breve Descripción de cada tipo de contrato.</FONT></P><P><BR><BR></P><BR><BR><BR><BR><BR><BR><BR><BR><BR><BR></BODY></HTML>"));
  	
  	return gMapHtmlLogicas;
  }
  transient ILogicaContrato objLogicaContrato;
  public ILogicaContrato getObjLogicaInstance() throws Exception {
  	if (objLogicaContrato!=null && objLogicaContrato.getClass().getName().equals(getLogicaContrato())) return objLogicaContrato;
  	if (pLogicaContrato.isNull()) pLogicaContrato.setValue(JContratoNormal.class.getName());
  	
  	ILogicaContrato pr = null;
  	if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isDependant() ) {
    	pr = (ILogicaContrato)JContratoUpfrontRutaSlave.class.newInstance();
  	} else
  		pr = (ILogicaContrato)Class.forName(getLogicaContrato()).newInstance();
  	return objLogicaContrato=pr;
  	
  }
  static public ILogicaContrato getLogicaInstance(String logica) throws Exception {
  	if (logica==null || logica.equals(""))
  		logica=JContratoNormal.class.getName();
  	ILogicaContrato pr = (ILogicaContrato)Class.forName(logica).newInstance();
  	return pr;
  	
  }
  public double getFMS(String company,String idAerolinea,String mercado) throws Exception {
  	BizCopaDetalle fmss = new BizCopaDetalle();
  	fmss.dontThrowException(true);
  	if (fmss.read(company, getFDesde(), idAerolinea, mercado))
  		return fmss.getFMS();
  	if (fmss.read(BizBSPCompany.getCompany(company).getParentCompany(), getFDesde(), idAerolinea, mercado))
  		return fmss.getFMS();
  	return 0;
  }
	public BizDetalle getClon() throws Exception {
	  ILogicaContrato logica = getObjLogicaInstance();
	  BizDetalle clon = logica.getBiz();
    clon.copyProperties(this);
    clon.pLinea.setNull();
    clon.pVariableGanancia.setNull();
    clon.pVariableGanancia.setNull();
    clon.pVariable.setNull();
    clon.pDescripcion.setValue(this.getDescripcion()+" (bis)");
    if (!clon.isUpfront())
    	clon.pNiveles.setValue(this.getObjNiveles());
		return clon;
	}
	
	private double getExpireReservas() throws Exception {
		double fx =BizBSPUser.getUsrBSP().getBspConsola().getConfigView().getExpireReservas();
		if (fx==0) return 10;
		return fx;
	}
  public String getSqlCalculed(String zsql) throws Exception {
  	String sql = zsql;
		Calendar fecha = Calendar.getInstance();
		fecha.setTime(getFHasta());
		sql = JTools.replace(sql, "::FECHA::", "");
		sql = JTools.replace(sql, "::DAYS::", ""+getExpireReservas());
		if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isDependant()) 
 			sql = JTools.replace(sql, "::DK::", " AND (customer_id_reducido='"+BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getCodigoCliente()+"') ");
		else
			sql = JTools.replace(sql, "::DK::", "");
		
		if (fecha!=null) {
			sql=JTools.replace(sql, "now()", "'"+JDateTools.DateToString(fecha.getTime())+"'::date ");
			Calendar ayer = Calendar.getInstance();
			ayer.setTime(fecha.getTime());
			ayer.add(Calendar.DAY_OF_MONTH, -1);
			sql=JTools.replace(sql, "'yesterday'",  "'"+JDateTools.DateToString(ayer.getTime())+"'::date ");
			Calendar maniana = Calendar.getInstance();
			maniana.setTime(fecha.getTime());
			maniana.add(Calendar.DAY_OF_MONTH, 1);
			sql=JTools.replace(sql, "'tomorrow'", "'"+JDateTools.DateToString(maniana.getTime())+"'::date ");
		
		}
	 	return sql;
	}
	
	public JRecords<BizPNRReserva> getListaReservasMeta() throws Exception {
		JRecords<BizPNRReserva> res = new JRecords<BizPNRReserva> (BizPNRReserva.class);
		if (!hasConsultaReservaMeta()) 
			res.setStatic(true);
		else {
			res.SetSQL(getSqlCalculed(getConsultaReservaMeta()));
			res.toStatic();
		}
		return res;
	}
	public JRecords<BizPNRReserva> getListaReservasBase() throws Exception {
		JRecords<BizPNRReserva> res = new JRecords<BizPNRReserva> (BizPNRReserva.class);
		if (!hasConsultaReservaBase()) 
			res.setStatic(true);
		else {
			res.SetSQL(getSqlCalculed(getConsultaReservaBase()));
			res.toStatic();
		}
		return res;
	}
	public JRecords<BizPNRReserva> getListaReservasAux() throws Exception {
		JRecords<BizPNRReserva> res = new JRecords<BizPNRReserva> (BizPNRReserva.class);
		if (!hasConsultaReservaAux()) 
			res.setStatic(true);
		else {
			res.SetSQL(getSqlCalculed(getConsultaReservaAux()));
			res.toStatic();
		}
		return res;
	}
  public void calculeReembolsos() throws Exception {
  	if (isUpfront())
  		return;
  	if (!isRecalculeReembolsoAuto())
  		return;
  	double reems=0;

  	JWins wins = getDetalleGanancia();
		if (!(wins instanceof GuiPNRTickets)) {
	  	JIterator<BizBooking> it = wins.getRecords().getStaticIterator();
	  	while (it.hasMoreElements()) {
	  		BizBooking pnr =it.nextElement();
	  		BizReembolso objReem = new BizReembolso();
	  		objReem.dontThrowException(true);
	  		if (!objReem.readByBoletoRfnd(pnr.getCompany(),pnr.getNumeroBoleto())) continue;
	  		reems+=Math.abs(objReem.getTotal());
	  	}
	  	setReembolsoAuto(reems);
	  	setRecalculeReembolsoAuto(false);
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
			return;
		}
  	JIterator<BizPNRTicket> it = wins.getRecords().getStaticIterator();
  	while (it.hasMoreElements()) {
  		BizPNRTicket pnr =it.nextElement();
  		BizReembolso objReem = new BizReembolso();
  		objReem.dontThrowException(true);
  		if (!objReem.readByBoletoRfnd(pnr.getCompany(),pnr.getNumeroboleto())) continue;
  		reems+=Math.abs(objReem.getTotal());
  	}
  	setReembolsoAuto(reems);
  	setRecalculeReembolsoAuto(false);
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
//  public static void execProcessAllUpdateData() throws Exception {
//  	new BizDetalle().execProcessUpdateAllSeries();
//		JRecords<BizDetalle> recs =  new JRecords<BizDetalle>(BizDetalle.class);
//		JIterator<BizDetalle> it = recs.getStaticIterator();
//		while (it.hTasMoreElements()) {
//			BizDetalle det = it.nextElement();
//			det.execProcessUpdateAcumuladosSeries();
//		}
//  }

//  public void execProcessUpdateAcumuladosSeries() throws Exception {
//		JExec oExec = new JExec(this, "processUpdateAcumuladosSeries") {
//
//			@Override
//			public void Do() throws Exception {
//				processUpdateAcumuladosSeries();
//			}
//		};
//		oExec.execute();
//	}
  
//  public void execProcessUpdateAllSeries() throws Exception {
//		JExec oExec = new JExec(this, "processUpdateData") {
//
//			@Override
//			public void Do() throws Exception {
//				processUpdateData();
//			}
//		};
//		oExec.execute();
//	}
  
//  public  void processUpdateAcumuladosSeries() throws Exception {
//		BizSqlEventDato datoUltimo = new BizSqlEventDato();
//		datoUltimo.addFilter("id_evento",getVariable());
//		Date fechaUltimoDato = datoUltimo.SelectMaxDate("fecha");
//
//  	Calendar fatras = Calendar.getInstance();
//  	fatras.setTime(new Date());
//  	fatras.setTime(JDateTools.getFirstDayOfYear(new Date()));
//  	//fatras.add(Calendar.DAY_OF_YEAR, -diasHist);
//  	Calendar fdesde = Calendar.getInstance();
//  	fdesde.setTime(getObjContrato().getFechaDesde());
//  	Calendar fhasta = Calendar.getInstance();
//  	fhasta.setTime(hasFechaCalculo()?getFechaCalculo():getObjContrato().getFechaHasta());
//  	double acumul1 = 0;
//  	Calendar finEstimacion = Calendar.getInstance();
//  	finEstimacion.setTime(fechaUltimoDato);
//  	long antPeriodo=0;
//  	for (int day = 1; fdesde.before(fhasta); day++) {
//			BizSerieCalculada serie = new BizSerieCalculada();
//			serie.dontThrowException(true);
//			if (serie.read(fdesde.getTime())) {
//				if (getAcumulativo())
//					acumul1 += fdesde.after(finEstimacion) ? serie.getValorEstimado() : serie.getValor();
//				else
//					acumul1 = fdesde.after(finEstimacion) ? serie.getValorEstimado() : serie.getValor();
//			}				
//
//			long newPeriodo=0;
//			if (getPeriodo().equals(BIMESTRAL)) newPeriodo = (int)Math.floor((fdesde.get(Calendar.MONTH))/2)+1;
//			if (getPeriodo().equals(TRIMESTRAL)) newPeriodo = (int)Math.floor((fdesde.get(Calendar.MONTH))/3)+1;
//			if (getPeriodo().equals(CUATRIMESTRAL)) newPeriodo = (int)Math.floor((fdesde.get(Calendar.MONTH))/4)+1;
//			if (getPeriodo().equals(SEMESTRAL)) newPeriodo = (int)Math.floor((fdesde.get(Calendar.MONTH))/6)+1;
//			if (getPeriodo().equals(ANUAL)) newPeriodo = 1;
//			if (antPeriodo!=newPeriodo) acumul1=0;
//			antPeriodo=newPeriodo;
//			if (acumul1!=serie.getAcumulado()) {
//				serie.setAcumulado(acumul1);
//				serie.update();
//			}
//
//			fdesde.add(Calendar.DAY_OF_YEAR, 1);
//		}
//  }
}
