package pss.tourism.pnr;

import java.util.Date;

import pss.bsp.bspBusiness.BizBSPCompany;
import pss.bsp.clase.BizClase;
import pss.bsp.clase.detalle.BizClaseDetail;
import pss.bsp.config.carrierGroups.detalle.BizCarrierGroupDetail;
import pss.bsp.familia.detalle.BizFamiliaDetail;
import pss.bsp.regions.detalle.BizRegionDetail;
import pss.common.customList.config.relation.JRelation;
import pss.common.customList.config.relation.JRelations;
import pss.common.security.BizUsuario;
import pss.core.services.fields.JBoolean;
import pss.core.services.fields.JCurrency;
import pss.core.services.fields.JDate;
import pss.core.services.fields.JGeoPosition;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.tools.GeoPosition;
import pss.core.tools.JPair;
import pss.tourism.airports.BizAirport;
import pss.tourism.carrier.BizCarrier;

public class BizBooking  extends JRecord {
  
	protected JLong pId=new JLong();
	protected JLong pBooking=new JLong();
	protected JString pCompany=new JString();
	protected JString pCodigoPNR = new JString();
	protected JString pCustomerIdReducido = new JString();
	
	protected JLong pCodigoSegmento = new JLong();
	protected JString pNumeroBoleto = new JString();
	protected JString pNombrePasajero = new JString();
	protected JString pDespegue = new JString();
	protected JString pArrivo = new JString();
	protected JString pOrigen = new JString();
	protected JString pDestino = new JString();
	protected JString pOrigenDestino = new JString();
	protected JString pOrigenPais = new JString();
	protected JString pDestinoPais = new JString();
	protected JString pRuta = new JString();
	protected JString pCarrier = new JString();
	protected JString pCarrierPlaca = new JString();
	protected JString pClase = new JString();
	protected JString pTipoClase = new JString();
	protected JLong pPrioridad = new JLong();
	protected JDate pFechaDespegue = new JDate();
	protected JString pNumeroVuelo = new JString();
	protected JString pHoraDespegue = new JString();
	protected JDate pFechaArrivo = new JDate();
	protected JDate pFechaEmision = new JDate();
	protected JString pHoraArrivo = new JString();
	protected JString pEstado = new JString();
	protected JString pTipoEquipo = new JString();
  protected JString pSegmentoInicial = new JString();

  protected JBoolean pEmitido = new JBoolean();
  protected JBoolean pReemitido = new JBoolean();
	protected JLong pIdFare=new JLong();
	protected JString pFareBasic=new JString();
	protected JString pFareBasicExtended=new JString();
	protected JString pFamiliaTarifaria=new JString();
	protected JString pTipoFamiliaTarifaria=new JString();
	protected JString pMoneda=new JString();
	protected JString pMonedaBase=new JString();
	protected JString pLimitePeso=new JString();
	protected JString pCarrierOp = new JString();

	
  protected JString pTipoSegmento = new JString();
  protected JGeoPosition pGeopArrivo = new JGeoPosition();
  protected JGeoPosition pGeopDespegue = new JGeoPosition();
	protected JString pDescrDespegue = new JString(){
		public void preset() throws Exception {
			pDescrDespegue.setValue(getObjAeropuertoDespegue().getDescription());
		};
	};
	protected JString pVendedor = new JString(){
		public void preset() throws Exception {
			pVendedor.setValue(getObjTicket().getVendedor());
		};
	};
	protected JString pDescrArrivo = new JString(){
		public void preset() throws Exception {
			pDescrArrivo.setValue(getObjAeropuertoArrivo().getDescription());
		};
	};
	protected JString pDescrCarrier = new JString(){
		public void preset() throws Exception {
			try {
				pDescrCarrier.setValue(getObjCarrier().getDescription());
			} catch (Exception e) {
				pDescrCarrier.setValue("");
			}
		};
	}; 
	protected JCurrency pMonto  =  new JCurrency() {
  	public void preset() throws Exception {
  		setSimbolo(pMoneda.isNotNull());
  		setMoneda(pMoneda.getValue());
   	};
  }; 
  protected JCurrency pMontoOriginal =  new JCurrency() {
  	public void preset() throws Exception {
  		setSimbolo(pMonedaBase.isNotNull());
  		setMoneda(pMonedaBase.getValue());
  	};
  }; 
  protected JCurrency pMontoOriginalRoundTrip =  new JCurrency() {
  	public void preset() throws Exception {
  		setSimbolo(pMonedaBase.isNotNull());
  		setMoneda(pMonedaBase.getValue());
  	};
  }; 
  protected JCurrency pMontoRoundTrip  =  new JCurrency() {
  	public void preset() throws Exception {
  		setSimbolo(pMoneda.isNotNull());
  		setMoneda(pMoneda.getValue());
   	};
  }; 
	private JCurrency pMontoUsaRoundTrip = new JCurrency() {
		public void preset() throws Exception {
			setSimbolo(true);
			setMoneda("USD");
		};
	};
	protected JLong pRoundTrip=new JLong();

	public void setCompany(String zValue)  {
		pCompany.setValue(zValue);
	}

	public String getCompany() throws Exception {
		return pCompany.getValue();
	}

	protected JCurrency pMontoFactura  =  new JCurrency() {
  	public void preset() throws Exception {
  		setSimbolo(pMoneda.isNotNull());
  		setMoneda(pMoneda.getValue());
   	};
  }; 
  protected JCurrency pMontoOriginalFactura =  new JCurrency() {
  	public void preset() throws Exception {
  		setSimbolo(pMonedaBase.isNotNull());
  		setMoneda(pMonedaBase.getValue());
  	};
  }; 
  protected JCurrency pMontoOriginalRoundTripFactura =  new JCurrency() {
  	public void preset() throws Exception {
  		setSimbolo(pMonedaBase.isNotNull());
  		setMoneda(pMonedaBase.getValue());
  	};
  }; 
  protected JCurrency pMontoRoundTripFactura  =  new JCurrency() {
  	public void preset() throws Exception {
  		setSimbolo(pMoneda.isNotNull());
  		setMoneda(pMoneda.getValue());
   	};
  }; 
	private JCurrency pMontoUsaRoundTripFactura = new JCurrency() {
		public void preset() throws Exception {
			setSimbolo(true);
			setMoneda("USD");
		};
	};

	public long getId() throws Exception {
		return pId.getValue();
	}
	
	public void setId(long val){
		pId.setValue(val);
	}


	protected JString pCodigoComida = new JString();
	protected JString pCodigoEntreten = new JString();
	protected JString pDuracion = new JString();
	protected JBoolean pInternational = new JBoolean();
	protected JString pInternationalDomestic = new JString();
	protected JString pMercado = new JString();
	public void setCodigoComida(String value) { pCodigoComida.setValue(value);}
	public void setCodigoEntreten(String value) { pCodigoEntreten.setValue(value);}
	public void setDuracion(String value) { pDuracion.setValue(value);}
	public void setInternational(boolean value) { pInternational.setValue(value);}
	public void setInternationalDomestic(String value) { pInternationalDomestic.setValue(value);}
	public void setMercado(String value) { pMercado.setValue(value);}
	public void setDK(String value) { pCustomerIdReducido.setValue(value);}	
	public void setCodigoPNR(String value) { pCodigoPNR.setValue(value);}
	public void setCodigoSegmento(long value) { pCodigoSegmento.setValue(value);}
	public void setDespegue(String value) { pDespegue.setValue(value);}
	public void setGeoDespegue(GeoPosition value) { pGeopDespegue.setValue(value);}
	public void setArrivo(String value) { pArrivo.setValue(value);}
	public void setOrigen(String value) { pOrigen.setValue(value);}
	public void setDestino(String value) { pDestino.setValue(value);}
	public void setOrigenDestino(String value) { pOrigenDestino.setValue(value);}
	public void setOrigenPais(String value) { pOrigenPais.setValue(value);}
	public void setDestinoPais(String value) { pDestinoPais.setValue(value);}
	public void setGeoArrivo(GeoPosition value) { pGeopArrivo.setValue(value);}	
	public void setCarrierPlaca(String value) { pCarrierPlaca.setValue(value);}
	public void setNombrePasajero(String value) { pNombrePasajero.setValue(value);}
	public void setNumeroBoleto(String value) { pNumeroBoleto.setValue(value);}
	public void setCarrier(String value) { pCarrier.setValue(value);}
	public void setCarrierOp(String value) { pCarrierOp.setValue(value);}
	public void setClase(String value) { pClase.setValue(value);}
	public void setTipoClase(String value) { pTipoClase.setValue(value);}
	public void setPrioridad(long value) { pPrioridad.setValue(value);}
	public void setFechaDespegue(Date value) { pFechaDespegue.setValue(value);}
	public void setNumeroVuelo(String value) { pNumeroVuelo.setValue(value);}
	public void setHoraDespegue(String value) { pHoraDespegue.setValue(value);}
	public void setFechaArrivo(Date value) { pFechaArrivo.setValue(value);}
	public void setFechaEmision(Date value) { pFechaEmision.setValue(value);}
	public void setHoraArrivo(String value) { pHoraArrivo.setValue(value);}
	public void setEstado(String value) { pEstado.setValue(value);}
	public void setTipoEquipo(String value) { pTipoEquipo.setValue(value);}
	public void setConnectionIndicator(String value) { pSegmentoInicial.setValue(value);}
	public void setMonto(double value) { pMonto.setValue(value);}
	public void setMontoOriginal(double value) { pMontoOriginal.setValue(value);}
	public void setMontoUsaRoundTrip(double value) { pMontoUsaRoundTrip.setValue(value);}
	public void setMontoRoundTrip(double value) { pMontoRoundTrip.setValue(value);}
	public void setEmitido(boolean value) { pEmitido.setValue(value);}
	public void setReemitido(boolean value) { pReemitido.setValue(value);}
	public void setIdFare(long value) { pIdFare.setValue(value);}
	public void setFareBasic(String value) { pFareBasic.setValue(value);}
	public void setFareBasicExpanded(String value) { pFareBasicExtended.setValue(value);}
	public void setFamiliaTarifaria(String value) { pFamiliaTarifaria.setValue(value);}
	public void setTipoFamiliaTarifaria(String value) { pTipoFamiliaTarifaria.setValue(value);}
	public void setLimitePeso(String value) { pLimitePeso.setValue(value);}
	public void setMonedaBase(String value) { pMonedaBase.setValue(value);}
	public void setMoneda(String value) { pMoneda.setValue(value);}
	public void setTipoSegmento(String value) { pTipoSegmento.setValue(value);}
	public String getConnectionIndicator() throws Exception {return pSegmentoInicial.getValue(); }
	public String getTipoSegmento() throws Exception {return pTipoSegmento.getValue(); }	
	public String getDuracion() throws Exception {return pDuracion.getValue(); }	
	public String getInternationalDomestic() throws Exception {return pInternationalDomestic.getValue(); }	
	public boolean getInternational() throws Exception {return pInternational.getValue(); }	
	public String getMercado() throws Exception {return pMercado.getValue(); }	
	public String getDK() throws Exception {return pCustomerIdReducido.getValue(); }	

	public void setMontoFactura(double value) { pMontoFactura.setValue(value);}
	public void setMontoOriginalFactura(double value) { pMontoOriginalFactura.setValue(value);}
	public void setMontoUsaRoundTripFactura(double value) { pMontoUsaRoundTripFactura.setValue(value);}
	public void setMontoRoundTripFactura(double value) { pMontoRoundTripFactura.setValue(value);}

	public String getClase() throws Exception {return pClase.getValue(); }	
	public String getFareBasic() throws Exception {return pFareBasic.getValue(); }	
	public String getTipoClase() throws Exception {return pTipoClase.getValue(); }	
	public long getPrioridad() throws Exception {return pPrioridad.getValue(); }	
	public void setRoundTrip(long value) { pRoundTrip.setValue(value);}

  public BizBooking() throws Exception {
		super();
	}
  
  public String getCodigoPNR() throws Exception {return pCodigoPNR.getValue();}

  public long getCodigoSegmento() throws Exception {return pCodigoSegmento.getValue();}
  public String getDespegue() throws Exception {
  	return pDespegue.getValue();
  }
  public String getArrivo() throws Exception {
  	return pArrivo.getValue();
  }
  public GeoPosition getGeoArrivo() throws Exception {
  	return getObjAeropuertoArrivo()==null?null:getObjAeropuertoArrivo().getGeoPosicion();
  }
  public String getOrigen() throws Exception {
  	return pOrigen.getValue();
  }
  public String getDestino() throws Exception {
  	return pDestino.getValue();
  }
  public String getOrigenDestino() throws Exception {
  	return pOrigenDestino.getValue();
  } 
  public GeoPosition getGeoDespegue() throws Exception {
  	return getObjAeropuertoDespegue()==null?null:getObjAeropuertoDespegue().getGeoPosicion();
  }
  public String getRuta() throws Exception {
  	return pRuta.getValue();
  }
  public void setRuta(String r) throws Exception {
  	pRuta.setValue(r);
  }

  public Date getFechaDespegue() throws Exception {return pFechaDespegue.getValue();}
  public String getNumeroVuelo() throws Exception {return pNumeroVuelo.getValue();}
  public String getHoraDespegue() throws Exception {return pHoraDespegue.getValue();}
  public Date getFechaArrivo() throws Exception {return pFechaArrivo.getValue();}
  public Date getFechaEmision() throws Exception {return pFechaEmision.getValue();}
  public String getHoraArrivo() throws Exception {return pHoraArrivo.getValue();}
  public String getEstado() throws Exception {return pEstado.getValue();}
  public String getTipoEquipo() throws Exception {return pTipoEquipo.getValue();}
  public String getPaisDestino() throws Exception {return pDestinoPais.getValue();}
  public String getPaisOrigen() throws Exception {return pOrigenPais.getValue();}

  @Override
	public void createProperties() throws Exception {
		addItem("booking", pBooking);
		addItem("interface_id", pId);
		addItem("CodigoPNR"           , pCodigoPNR );
		addItem("CodigoSegmento"        , pCodigoSegmento);
		addItem("customer_id_reducido"        , pCustomerIdReducido);
  	addItem("numeroboleto"        , pNumeroBoleto);
  	addItem("nombrepasajero"        , pNombrePasajero);
  	addItem("Despegue"        , pDespegue);
  	addItem("Arrivo"        , pArrivo);
  	addItem("Origen"        , pOrigen);
  	addItem("Destino"        , pDestino);
  	addItem("origen_destino"        , pOrigenDestino);
  	addItem("Origen_pais"        , pOrigenPais);
  	addItem("Destino_pais"        , pDestinoPais);
   	addItem("ruta"        , pRuta);
   	addItem("vendedor"        , pVendedor);
    addItem("descr_despegue"        , pDescrDespegue);
  	addItem("descr_arrivo"        , pDescrArrivo);
   	addItem("Carrier_placa"        , pCarrierPlaca);
    addItem("Carrier"        , pCarrier);
  	addItem("descr_carrier"        , pDescrCarrier);
  	addItem("carrier_op"        , pCarrierOp);
  	addItem("Clase"        , pClase);
  	addItem("tipo_clase"        , pTipoClase);
  	addItem("prioridad_clase"        , pPrioridad);
  	addItem("FechaDespegue"        , pFechaDespegue);
  	addItem("NumeroVuelo"        , pNumeroVuelo);
  	addItem("HoraDespegue"        , pHoraDespegue);
  	addItem("fecha_emision"        , pFechaEmision);
  	addItem("FechaArrivo"        , pFechaArrivo);
  	addItem("HoraArrivo"        , pHoraArrivo);
  	addItem("Estado"        , pEstado);
  	addItem("TipoEquipo"        , pTipoEquipo);
  	addItem("CodigoComida"        , pCodigoComida);
  	addItem("codigoentretenimiento"        , pCodigoEntreten);
  	addItem("duracionvuelo"        , pDuracion);
  	addItem("international"        , pInternational);
  	addItem("internationaldomestic"        , pInternationalDomestic);
		addItem("mercado"        , pMercado);
		addItem("company", pCompany);
		addItem("segmento_ini", pSegmentoInicial);
		addItem("tipo_segmento", pTipoSegmento);
		addItem("monto", pMonto);
		addItem("monto_orig", pMontoOriginal);
		addItem("monto_round", pMontoRoundTrip);
		addItem("monto_round_usa", pMontoUsaRoundTrip);
		addItem("monto_factura", pMontoFactura);
		addItem("monto_orig_factura", pMontoOriginalFactura);
		addItem("monto_round_factura", pMontoRoundTripFactura);
		addItem("monto_round_usa_factura", pMontoUsaRoundTripFactura);
		addItem("roundtrip", pRoundTrip);
		addItem("emitido", pEmitido);
		addItem("reemitido", pReemitido);
		addItem("id_fare", pIdFare);
		addItem("fare_basic", pFareBasic);
		addItem("fare_basic_ext", pFareBasicExtended);
		addItem("fare_family", pFamiliaTarifaria);
		addItem("group_fare_family", pTipoFamiliaTarifaria);
		addItem("weight", pLimitePeso);
		addItem("currency", pMoneda);
		addItem("currency_base", pMonedaBase);
		addItem("geop_arrivo", pGeopArrivo);
		addItem("geop_despegue", pGeopDespegue);
    
  }
  @Override
	public void createFixedProperties() throws Exception {
		addFixedItem(KEY, "booking", "Id booking", false, false, 64);
		addFixedItem(FIELD, "interface_id", "Id Interfaz", true, false, 18);
  	addFixedItem(FIELD, "CodigoSegmento", "Código Segmento", true, false, 3);
  	addFixedItem(FIELD, "numeroboleto", "Numero boleto", true, false, 50);
  	addFixedItem(FIELD, "nombrepasajero", "Pasajero", true, false, 300);
  	addFixedItem(FIELD, "customer_id_reducido", "DK", true, false, 50);
  	
  	addFixedItem(FIELD, "company", "empresa", true, false, 50);
  	addFixedItem(FIELD, "CodigoPNR", "PNR", true, false, 15);
  	
  	addFixedItem(FIELD, "Carrier_placa", "Código Aerolinea placa", true, false, 50);
  	addFixedItem(FIELD, "Carrier", "Código Aerolinea", true, false, 50);
		addFixedItem(VIRTUAL, "descr_carrier", "Aerolinea", true, false, 250);
  	addFixedItem(FIELD, "carrier_op", "Código Aerolinea Operadora", true, false, 50);
		addFixedItem(FIELD, "Clase", "Clase", true, false, 50);
  	addFixedItem(FIELD, "tipo_clase", "Clase Grupo", true, false, 50);
  	addFixedItem(FIELD, "prioridad_clase", "Prioridad", true, false, 18);
  	addFixedItem(FIELD, "NumeroVuelo", "Numero Vuelo", true, false, 50);
  	addFixedItem(FIELD, "TipoEquipo", "Tipo Avión", true, false, 50);

  	addFixedItem(FIELD, "CodigoComida", "Código Comida", true, false, 100);
  	addFixedItem(FIELD, "codigoentretenimiento", "Código Entretenimiento", true, false, 100);
  	addFixedItem(FIELD, "duracionvuelo", "Duracion Vuelo", true, false, 100);
  	addFixedItem(FIELD, "international", "Internacional", true, false, 1);
  	addFixedItem(FIELD, "internationaldomestic", "International/Domestic", true, false, 100);
  	addFixedItem(FIELD, "mercado", "Mercado", true, false, 500);
  	
  	addFixedItem(FIELD, "Estado", "Estado", true, false, 50);
		
  	addFixedItem(FIELD, "tipo_segmento", "Tipo Segmento", true, false, 10);
		addFixedItem(FIELD, "segmento_ini", "Ind.Conexion", true, false, 1);
  	addFixedItem(FIELD, "monto", "Booking Importe", true, false, 18,2);
  	addFixedItem(FIELD, "monto_orig", "Booking Imp.Original", true, false, 18,2);
  	addFixedItem(FIELD, "monto_round", "Booking Imp.Round", true, false, 18,2);
  	addFixedItem(FIELD, "monto_round_usa", "Booking Imp.Round dolares", true, false, 18,2);
  	addFixedItem(FIELD, "monto_factura", "Booking Importe Factura", true, false, 18,2);
  	addFixedItem(FIELD, "monto_orig_factura", "Booking Imp.Original Factura", true, false, 18,2);
  	addFixedItem(FIELD, "monto_round_factura", "Booking Imp.Round Factura", true, false, 18,2);
  	addFixedItem(FIELD, "monto_round_usa_factura", "Booking Imp.Round dolares Factura", true, false, 18,2);
  	addFixedItem(FIELD, "roundtrip", "Booking Round trip", true, false, 18);

  	addFixedItem(FIELD, "emitido", "Emitido", true, false, 1);
  	addFixedItem(FIELD, "reemitido", "Revisado", true, false, 1);
  	addFixedItem(FIELD, "id_fare", "Id tarifario", true, false, 18);
  	addFixedItem(FIELD, "fare_basic", "Tarif.base", true, false, 18);
  	addFixedItem(FIELD, "fare_basic_ext", "Tarif.ext.", true, false, 18);
  	addFixedItem(FIELD, "fare_family", "Tarif.familia", true, false, 18);
  	addFixedItem(FIELD, "group_fare_family", "Grupo Tarif.familia", true, false, 18);
  	addFixedItem(FIELD, "weight", "Peso maximo", true, false, 18);
  	addFixedItem(FIELD, "currency", "Moneda", true, false, 18);
  	addFixedItem(FIELD, "currency_base", "Moneda base", true, false, 18);


		addFixedItem(FIELD, "Despegue", "Booking Aer. Origen Código", true, false, 50);
		addFixedItem(FIELD, "ruta", "Booking Ruta", true, false, 50);
		addFixedItem(VIRTUAL, "descr_despegue", "Aer. Origen", true, false, 250);
		addFixedItem(VIRTUAL, "vendedor", "Vendedor", true, false, 250);
  	addFixedItem(FIELD, "FechaDespegue", "Booking Fecha Despegue", true, false, 50);
  	addFixedItem(FIELD, "HoraDespegue", "Hora Despegue", true, false, 50);
		addFixedItem( FIELD, "geop_despegue", "Geo Origen", true, false, 50);
  	addFixedItem(FIELD, "fecha_emision", "Fecha Emision", true, false, 18);

  	addFixedItem(FIELD, "Arrivo", "Booking Aer.Destino Código", true, false, 50);
  	addFixedItem(VIRTUAL, "descr_arrivo", "Booking Aer. Destino", true, false, 250);
  	addFixedItem(FIELD, "Origen", "Booking Origen", true, false, 50);
  	addFixedItem(FIELD, "Destino", "Booking Destino", true, false, 50);
  	addFixedItem(FIELD, "origen_destino", "Origen/Destino", true, false, 50);
  	addFixedItem(FIELD, "Origen_pais", "Booking Origen pais", true, false, 50);
  	addFixedItem(FIELD, "Destino_pais", "Booking Destino pais", true, false, 50);
  	addFixedItem(FIELD, "FechaArrivo", "Fecha Arrivo", true, false, 50);
  	addFixedItem(FIELD, "HoraArrivo", "Hora Arrivo", true, false, 50);
		addFixedItem(FIELD, "geop_arrivo", "Geo Destino", true, false, 50);
  }
  @Override
	public String GetTable() {
    return "TUR_PNR_BOOKING";
  }
  
  @Override
  protected boolean loadForeignFields() throws Exception {
  	return true;
  }
  

 
  public String getCarrier() throws Exception {return pCarrier.getValue();}
  public String getCarrierPlaca() throws Exception {return pCarrierPlaca.getValue();}
  public String getNumeroBoleto() throws Exception {return pNumeroBoleto.getValue();}
  public String getNombrePasajero() throws Exception {return pNombrePasajero.getValue();}

  public boolean Read( String zCompany, long interfaceId,long codseg) throws Exception { 
    addFilter( "company",  zCompany); 
    addFilter( "interface_id",  interfaceId ); 
    addFilter( "CodigoSegmento",  codseg ); 
    return read(); 
  } 
	public void attachRelationMap(JRelations rels) throws Exception {
  	rels.setSourceWinsClass("pss.tourism.pnr.GuiBookings");
  	JRelation r;
		JRelation rel;
		rel = rels.addRelationForce(10, "restriccion usuario");
//		rel.addFilter(" (TUR_PNR_BOOKING.company in (COMPANY_CUSTOMLIST)) ");
		if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isDependant()) {
			rel.addFilter(" (TUR_PNR_BOOKING.company in (COMPANY_TICKET) and TUR_PNR_BOOKING.customer_id_reducido='"+BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getCodigoCliente()+"')  ");
		} else {
			rel.addFilter(" (TUR_PNR_BOOKING.company in (COMPANY_CUSTOMLIST)) ");
		}
  	r=rels.addRelationParent(90, "Boleto", BizPNRTicket.class, "interface_id","interface_id");
  	r.addJoin("TUR_PNR_BOOKING.company", "TUR_PNR_BOLETO.company");
  	r.addJoin("TUR_PNR_BOOKING.interface_id", "TUR_PNR_BOLETO.interface_id");

  	r=rels.addRelationParent(21, "Aerolinea", BizCarrier.class, "Carrier","carrier");
  	r.addJoin("TUR_PNR_BOOKING.Carrier","carrier.carrier");
  	r.setAlias("carrier");

  	r=rels.addRelationParent(51, "Aeropuerto Origen", BizAirport.class, "despegue","code");
  	r.addJoin("TUR_PNR_BOOKING.despegue", "ae_despegue.code");
//  	r.setTypeJoin(JRelations.JOIN_LEFT);
  	r.setAlias("ae_despegue");
  	
  	r=rels.addRelationParent(52, "Aeropuerto Destino", BizAirport.class, "arrivo","code");
  	r.addJoin("TUR_PNR_BOOKING.arrivo", "ae_arrivo.code");
//  	r.setTypeJoin(JRelations.JOIN_LEFT);
  	r.setAlias("ae_arrivo");

  	r=rels.addRelationChild(70, "Grupo Carriers", BizCarrierGroupDetail.class);
  	r.addJoin("TUR_PNR_BOOKING.company", "cg_detail.company");
  	r.addJoin("TUR_PNR_BOOKING.carrier", "cg_detail.carrier");
  	r.setAlias("cg_detail");

  	
  	rels.addGeoPostions("despegue", new JPair(new JLong(51),new JString("geo_position")));
  	rels.addGeoPostions("arrivo", new JPair(new JLong(52),new JString("geo_position")));
  	rels.addGeoPostions("*", new JPair(new JLong(52), new JString("geo_position")));

  	rels.hideField("interface_id");
  	//rels.hideField("company");
  	rels.hideField("CodigoPNR");
  	rels.hideField("Estado");
  	
  	r=rels.addRelationChild(54, "Mis Regiones segmento destino", BizRegionDetail.class);
  	r.addJoin("TUR_PNR_BOOKING.destino_pais", "BSP_REGION_DETAIL.pais");
  	r.addJoin("TUR_PNR_BOOKING.company", "BSP_REGION_DETAIL.company");
  	r.setTypeJoin(JRelations.JOIN_LEFT);
  	
  	
		rels.addFolderGroup("Boleto", null);
		rels.addFolderGroup("Booking", null);
		rels.addFolderGroup("Aerolínea", "Boleto");
		rels.addFolderGroup("Tarifa", "Boleto");
		rels.addFolderGroup("Pasajero", "Boleto");
		rels.addFolderGroup("Comisiones", "Boleto");
		rels.addFolderGroup("Aeropuerto Origen", "Boleto");
		rels.addFolderGroup("Aeropuerto Destino", "Boleto");
		rels.addFolderGroup("Tarjeta", "Boleto");
		rels.addFolderGroup("Impuestos", "Boleto");
		rels.addFolderGroup("Seg.vuelos", "Boleto");
		rels.addFolderGroup("Todos los Seg.vuelos", "Boleto");
		rels.addFolderGroup("Origen", "Seg.vuelos");
		rels.addFolderGroup("Destino", "Seg.vuelos");
		rels.addFolderGroup("Seg.Origen", "Todos los Seg.vuelos");
		rels.addFolderGroup("Seg.Destino", "Todos los Seg.vuelos");
		rels.addFolderGroup("Seg.precios", "Boleto");
		rels.addFolderGroup("Origen Precio", "Seg.precios");
		rels.addFolderGroup("Destino Precio", "Seg.precios");
		rels.addFolderGroup("Remarks", "Boleto");
		rels.addFolderGroup("Vendedor", "Remarks");
		rels.addFolderGroup("C.Costo", "Remarks");
		rels.addFolderGroup("Vendedor", "Remarks");
		rels.addFolderGroup("Sucursal", "Boleto");
		rels.addFolderGroup("IATA", "Boleto");
		rels.addFolderGroup("Cliente", "Boleto");
		rels.addFolderGroup("Cliente remark", "Remarks");
		rels.addFolderGroup("Cliente ag.emision", "Remarks");
		rels.addFolderGroup("Cliente ag.reserva", "Remarks");

		rels.addFieldGroup("14_90", "*", "*", "Boleto");
		rels.addFieldGroup("14", "*", "*", "Booking");

		String s = "14_90";

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
		rels.addFieldGroup(s, "centro_costos", "*", "C.Costo");
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

		rels.addFieldGroup(s, "creation_date", "*", "Boleto");
		rels.addFieldGroup(s, "departure_date", "*", "Boleto");
		rels.addFieldGroup(s, "arrive_date", "*", "Boleto");
		rels.addFieldGroup(s, "fecha_proc", "*", "Boleto");

		rels.addFieldGroup(s, "air_intinerary", "*", "Boleto");
		rels.addFieldGroup(s, "carrier_intinerary", "*", "Boleto");
		rels.addFieldGroup(s, "class_intinerary", "*", "Boleto");
		rels.addFieldGroup(s, "fecha_intinerary", "*", "Boleto");

		rels.addFieldGroup(s, "NumeroBoleto", "*", "Boleto");
		rels.addFieldGroup(s, "CodigoPNR", "*", "Boleto");
		rels.addFieldGroup(s, "nro_iata", "*", "IATA");
		rels.addFieldGroup(s, "pre_dias_compra", "*", "Boleto");
		rels.addFieldGroup(s, "dias_compra", "*", "Boleto");
		rels.addFieldGroup(s, "dias_viajados", "*", "Boleto");
		rels.addFieldGroup(s, "route", "*", "Boleto");
		rels.addFieldGroup(s, "mini_route", "*", "Boleto");
		rels.addFieldGroup(s, "vendedor", "*", "Vendedor");
		rels.addFieldGroup(s, "observacion", "*", "Boleto");
		rels.addFieldGroup(s, "gds", "*", "Boleto");
		rels.addFieldGroup(s, "tipo_prestacion", "*", "Boleto");
		rels.addFieldGroup(s, "office_id", "*", "Sucursal");
		rels.addFieldGroup(s, "cliente", "*", "Cliente");
		rels.addFieldGroup(s, "codigo_cliente", "*", "Cliente remark");
		rels.addFieldGroup(s, "city_code", "*", "Boleto");
		rels.addFieldGroup(s, "tipo_cambio", "*", "Boleto");
		rels.addFieldGroup(s, "customer_id", "*", "Cliente");
		rels.addFieldGroup(s, "TipoBoleto", "*", "Boleto");
		rels.addFieldGroup(s, "codigo_aerolinea_intern", "*", "Boleto");
		rels.addFieldGroup(s, "tour_code", "*", "Boleto");
		rels.addFieldGroup(s, "net_remit", "*", "Boleto");
		rels.addFieldGroup(s, "cant_segmentos", "*", "Boleto");
		rels.addFieldGroup(s, "cant_roundtrip", "*", "Boleto");

		rels.addFieldGroup(s + "_90", "*", "*", "");
		rels.addFieldGroup(s + "_90", "descripcion", "*", "Cliente");
		rels.addFieldGroup(s + "_90", "mail", "*", "Cliente");

		rels.addFieldGroup(s + "_91", "*", "*", "");
		rels.addFieldGroup(s + "_91", "descripcion", "*", "Vendedor");
		rels.addFieldGroup(s + "_91", "mail", "*", "Vendedor");

		rels.addFieldGroup(s + "_92", "*", "*", "");
		rels.addFieldGroup(s + "_92", "descripcion", "*", "Sucursal");
		rels.addFieldGroup(s + "_92", "mail", "*", "Sucursal");

		rels.addFieldGroup(s + "_93", "*", "*", "");
		rels.addFieldGroup(s + "_93", "descripcion", "*", "C.Costo");
		rels.addFieldGroup(s + "_93", "mail", "*", "C.Costo");

		rels.addFieldGroup(s + "_94", "*", "*", "");
		rels.addFieldGroup(s + "_94", "descripcion", "*", "IATA");
		rels.addFieldGroup(s + "_94", "mail", "*", "IATA");

		rels.addFieldGroup(s + "_95", "*", "*", "");
		rels.addFieldGroup(s + "_95", "descripcion", "*", "Cliente remark");
		rels.addFieldGroup(s + "_95", "mail", "*", "Cliente remark");

		rels.addFieldGroup(s + "_96", "*", "*", "");
		rels.addFieldGroup(s + "_96", "descripcion", "*", "Cliente ag.emision");
		rels.addFieldGroup(s + "_96", "mail", "*", "Cliente ag.emision");

		rels.addFieldGroup(s + "_97", "*", "*", "");
		rels.addFieldGroup(s + "_97", "descripcion", "*", "Cliente ag.reserva");
		rels.addFieldGroup(s + "_97", "mail", "*", "Cliente ag.reserva");

		rels.addFieldGroup(s + "_98", "*", "*", "");


		rels.addFieldGroup(s + "_21", "*", "*", "Aerolínea");
		rels.addFieldGroup(s + "_21", "carrier", "*", "");
		rels.addFieldGroup(s + "_22", "*", "*", "");
		rels.addFieldGroup(s + "_70", "*", "*", "");
		rels.addFieldGroup(s + "_70_70", "*", "*", "");
		rels.addFieldGroup(s, "CodigoAerolinea", "*", "Aerolínea");
		rels.addFieldGroup(s + "_70", "id_group", "*", "Aerolínea");
		rels.addFieldGroup(s + "_70_70", "descripcion", "*", "Aerolínea");

		rels.addFieldGroup(s, "codigo_moneda", "*", "Tarifa");
		rels.addFieldGroup(s, "codigo_base_moneda", "*", "Tarifa");
		rels.addFieldGroup(s, "tarifa", "*", "Tarifa");
		rels.addFieldGroup(s, "tarifa_yq", "*", "Tarifa");
		rels.addFieldGroup(s, "tarifa_facturada_yq", "*", "Tarifa");
		rels.addFieldGroup(s, "tarifa_base_contax", "*", "Tarifa");
		rels.addFieldGroup(s, "tarifa_base", "*", "Tarifa");
		rels.addFieldGroup(s, "tarifa_real", "*", "Tarifa");
		rels.addFieldGroup(s, "tarifa_usa", "*", "Tarifa");
		rels.addFieldGroup(s, "neto_usa", "*", "Tarifa");
		rels.addFieldGroup(s, "tarifa_facturada_yq_usa", "*", "Tarifa");
		rels.addFieldGroup(s, "impuestos", "*", "Tarifa");
		rels.addFieldGroup(s, "neto", "*", "Tarifa");
		rels.addFieldGroup(s, "iva", "*", "Tarifa");
		rels.addFieldGroup(s, "tarifa_factura", "*", "Tarifa");
		rels.addFieldGroup(s, "impuesto_factura", "*", "Tarifa");
		rels.addFieldGroup(s, "neto_factura", "*", "Tarifa");
		rels.addFieldGroup(s, "iva_factura", "*", "Tarifa");
		rels.addFieldGroup(s, "tarifa_base_factura", "*", "Tarifa");
		rels.addFieldGroup(s, "tarifa_factura_total", "*", "Tarifa");
		rels.addFieldGroup(s, "tarifa_factura_total_usa", "*", "Tarifa");
		rels.addFieldGroup(s, "tarifa_factura_usa", "*", "Tarifa");
		rels.addFieldGroup(s, "neto_factura_usa", "*", "Tarifa");
		rels.addFieldGroup(s, "netoyq_usa", "*", "Tarifa");
		rels.addFieldGroup(s, "baseyq_usa", "*", "Tarifa");
		rels.addFieldGroup(s, "ahorro", "*", "Tarifa");

		rels.addFieldGroup(s, "nombre_pasajero", "*", "Pasajero");
		rels.addFieldGroup(s, "NumeroPasajero", "*", "Pasajero");
		rels.addFieldGroup(s, "tipo_pasajero", "*", "Pasajero");

		rels.addFieldGroup(s, "comision_amount", "*", "Comisiones");
		rels.addFieldGroup(s, "comision_perc", "*", "Comisiones");
		rels.addFieldGroup(s, "importeover", "*", "Comisiones");
		rels.addFieldGroup(s, "comision_factura", "*", "Comisiones");

		rels.addFieldGroup(s + "_30", "*", "*", "Aeropuerto Origen");
		rels.addFieldGroup(s + "_30", "geo_position", "*", "");
		rels.addFieldGroup(s + "_30_80", "*", "*", "");
		rels.addFieldGroup(s + "_30_80_70", "*", "*", "");
		rels.addFieldGroup(s + "_32_80", "*", "*", "");
		rels.addFieldGroup(s + "_32_80_70", "*", "*", "");
		rels.addFieldGroup(s + "_30_60", "*", "*", "");
		rels.addFieldGroup(s + "_30_60", "descripcion", "*", "Aeropuerto Origen");
		rels.addFieldGroup(s + "_30_60", "continente", "*", "Aeropuerto Origen");
		rels.addFieldGroup(s + "_30_60", "region", "*", "Aeropuerto Origen");
		rels.addFieldGroup(s + "_30_80", "id_group", "*", "Aeropuerto Origen");
//		rels.addFieldGroup(s + "_30_80_70", "descripcion", "*", "Aeropuerto Origen");
		rels.addFieldGroup(s, "aeropuerto_origen", "*", "Aeropuerto Origen");

		rels.addFieldGroup(s + "_32", "*", "*", "Aeropuerto Destino");
		rels.addFieldGroup(s + "_32", "geo_position", "*", "");
		rels.addFieldGroup(s + "_32_60", "*", "*", "");
		rels.addFieldGroup(s + "_32_60", "descripcion", "*", "Aeropuerto Destino");
		rels.addFieldGroup(s + "_32_60", "continente", "*", "Aeropuerto Destino");
		rels.addFieldGroup(s + "_32_60", "region", "*", "Aeropuerto Destino");
		rels.addFieldGroup(s + "_32_80", "id_group", "*", "Aeropuerto Destino");
//		rels.addFieldGroup(s + "_32_80_70", "descripcion", "*", "Aeropuerto Destino");
		rels.addFieldGroup(s, "aeropuerto_destino", "*", "Aeropuerto Destino");

		rels.addFieldGroup(s, "monto_tarjeta", "*", "Tarjeta");
		rels.addFieldGroup(s, "numero_tarjeta_mask", "*", "Tarjeta");

		rels.addFieldGroup(s + "_1", "*", "*", "Impuestos");
		rels.addFieldGroup(s, "impuestos_total", "*", "Impuestos");
		rels.addFieldGroup(s, "impuestos_total_factura", "*", "Impuestos");
		rels.addFieldGroup(s, "yq", "*", "Impuestos");

		rels.addFieldGroup(s + "_2", "*", "*", "Seg.vuelos");
		rels.addFieldGroup(s + "_4", "*", "*", "Todos los Seg.vuelos");

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
		rels.addFieldGroup(s + "_2", "carrier", "*", "Seg.vuelos");
		rels.addFieldGroup(s + "_2_70", "id_group", "*", "Seg.vuelos");
		rels.addFieldGroup(s + "_2_70_70", "descripcion", "*", "Seg.vuelos");

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

		rels.addFieldGroup(s + "_3", "*", "*", "Seg.precios");
		rels.addFieldGroup(s + "_3_70", "*", "*", "");
		rels.addFieldGroup(s + "_3_70_70", "*", "*", "");
		rels.addFieldGroup(s + "_3_70", "id_group", "*", "Seg.precios");
		rels.addFieldGroup(s + "_3_70_70", "descripcion", "*", "Seg.precios");

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
		rels.addFieldGroup(s + "_38_49", "descripcion", "*", "Aeropuerto Destino");// region
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
		rels.addFieldGroup(s + "_4", "carrier", "*", "Todos los Seg.vuelos");
		rels.addFieldGroup(s + "_4_70", "id_group", "*", "Todos los Seg.vuelos");
		rels.addFieldGroup(s + "_4_70_70", "descripcion", "*", "Todos los Seg.vuelos");
		rels.addFieldGroup(s + "_4", "Destino", "*", "Todos los Seg.vuelos");
		rels.addFieldGroup(s + "_4", "Destino_pais", "*", "Todos los Seg.vuelos");
		rels.addFieldGroup(s + "_4", "Origen", "*", "Todos los Seg.vuelos");
		rels.addFieldGroup(s + "_4", "Origen_pais", "*", "Todos los Seg.vuelos");

		rels.addFieldGroup(s + "_4_51_80", "*", "*", "");
		rels.addFieldGroup(s + "_4_51_80_70", "*", "*", "");
		rels.addFieldGroup(s + "_4_51_80", "id_group", "*", "Seg.Origen");
//		rels.addFieldGroup(s + "_4_51_80_70", "descripcion", "*", "Seg.Origen");

		rels.addFieldGroup(s + "_4_52_80", "*", "*", "");
		rels.addFieldGroup(s + "_4_52_80_70", "*", "*", "");
		rels.addFieldGroup(s + "_4_52_80", "id_group", "*", "Seg.Destino");
//		rels.addFieldGroup(s + "_4_52_80_70", "descripcion", "*", "Seg.Destino");

		rels.addFieldGroup(s + "_4", "Arrivo", "*", "Seg.Destino");
		rels.addFieldGroup(s + "_4", "FechaArrivo", "*", "Seg.Destino");
		rels.addFieldGroup(s + "_4", "HoraArrivo", "*", "Seg.Destino");
		rels.addFieldGroup(s + "_4_52", "*", "*", "Seg.Destino");
		rels.addFieldGroup(s + "_4_52", "geo_position", "*", "");
		rels.addFieldGroup(s + "_4_52_60", "*", "*", "");
		rels.addFieldGroup(s + "_4_52_60", "descripcion", "*", "Seg.Destino");
		rels.addFieldGroup(s + "_4_52_60", "continente", "*", "Seg.Destino");
		rels.addFieldGroup(s + "_4_52_60", "region", "*", "Seg.Destino");
  }
	
	public BizAirport getObjAeropuerto(String key) throws Exception {
		BizAirport p = PNRCache.getAirportCache().getElement(key);
		return p;
	}
	BizAirport objAeropuertoDespegue;
	public BizAirport getObjAeropuertoDespegue() throws Exception {
//		if (pDespegue.isNull()) return null;
		if (objAeropuertoDespegue!=null) return objAeropuertoDespegue;
		BizAirport p = PNRCache.getAirportCache().getElement(this.getDespegue());
		if (p==null) 
			p=PNRCache.createTemporaryAirport(this.getDespegue());
		return objAeropuertoDespegue=p;
	}
	
	BizAirport objAeropuertoArribo;
	public BizAirport getObjAeropuertoArrivo() throws Exception {
//		if (pArrivo.isNull()) return null;
		if (objAeropuertoArribo!=null) return objAeropuertoArribo;
		BizAirport p = PNRCache.getAirportCache().getElement(this.getArrivo());
		if (p==null) 
			p=PNRCache.createTemporaryAirport(this.getArrivo());
		return objAeropuertoArribo=p;
	}
	
	BizAirport objAeropuertoOrigen;
	public BizAirport getObjAeropuertoOrigen() throws Exception {
//		if (pArrivo.isNull()) return null;
		if (objAeropuertoOrigen!=null) return objAeropuertoOrigen;
		BizAirport p = PNRCache.getAirportCache().getElement(this.getOrigen());
		if (p==null) 
			p=PNRCache.createTemporaryAirport(this.getOrigen());
		return objAeropuertoOrigen=p;
	}

	BizAirport objAeropuertoDestino;
	public BizAirport getObjAeropuertoDestino() throws Exception {
//		if (pArrivo.isNull()) return null;
		if (objAeropuertoDestino!=null) return objAeropuertoDestino;
		BizAirport p = PNRCache.getAirportCache().getElement(this.getDestino());
		if (p==null) 
			p=PNRCache.createTemporaryAirport(this.getDestino());
		return objAeropuertoDestino=p;
	}

	BizPNRTicket objTicket;
	public BizPNRTicket getObjTicket() throws Exception {
		if (pId.isNull()) return null;
		if (objTicket!=null) return objTicket;
		BizPNRTicket p = new BizPNRTicket();
		p.dontThrowException(true);
		if (!p.read(pId.getValue(),pNumeroBoleto.getValue())) return null;
		return objTicket=p;
	}
	BizPNRFare objFare;
	public BizPNRFare getObjFare() throws Exception {
		if (pIdFare.isNull()) return null;
		if (objFare!=null) return objFare;
		BizPNRFare p = new BizPNRFare();
		p.dontThrowException(true);
		if (!p.read(this.getCompany(),this.getId(),this.pIdFare.getValue())) return null;
		return objFare=p;
	}
	BizCarrier objCarrier;
	public BizCarrier getObjCarrier() throws Exception {
//		if (pArrivo.isNull()) return null;
		if (objCarrier!=null) return objCarrier;
		BizCarrier p = PNRCache.getCarrierCache().getElement(this.getCarrier());
		if (p==null) 
			p=PNRCache.createTemporaryCarrier(this.getCarrier());
		return objCarrier=p;
	}
	public boolean isTipoIda() throws Exception {
		return this.getTipoSegmento().equals(BizPNRSegmentoAereo.SEGMENTO_IDA);
	}
	public boolean isTipoFin() throws Exception {
		return this.getTipoSegmento().equals(BizPNRSegmentoAereo.SEGMENTO_DESTINO);
	}
	public boolean isTipoRetorno() throws Exception {
		return this.getTipoSegmento().equals(BizPNRSegmentoAereo.SEGMENTO_RETORNO);
	}

	public String getRecordName() throws Exception {
		return "Booking";
	}
	
	public void processCalculate() throws Exception {
		BizClaseDetail cl =  PNRCache.getTipoClaseCache(getCompany(), getCarrierPlaca(), getClase(), getInternational());
		if (cl==null) {
			setTipoClase(BizClase.getTipoClaseDefault( getClase()));
			setPrioridad(BizClase.getPrioridadClaseDefault( getClase()));
		} else {
			setTipoClase(cl.getObjClase().getDescripcion());
			setPrioridad(cl.getPrioridad());
		}
		BizFamiliaDetail fm = PNRCache.getTipoFamiliaCache(getCompany(), getCarrierPlaca(), pFamiliaTarifaria.getValue());
		if (fm==null) {
			setTipoFamiliaTarifaria(pFamiliaTarifaria.getValue());
		} else {
			setTipoFamiliaTarifaria(fm.getObjFamilia().getDescripcion());
			setTipoClase(fm.getObjFamilia().getDescripcion());

		}
		setGeoDespegue(this.findGeoAirport(getDespegue()));				
		setGeoArrivo(this.findGeoAirport(getArrivo()));
		if (pCarrierOp.isNull())
			pCarrierOp.setValue(pCarrier.getValue());

	}
	
	private GeoPosition findGeoAirport(String code) throws Exception {
		BizAirport a = PNRCache.getAirportCache().getElement(code);
		if (a==null) return null;
		return a.getGeoPosicion();
	}

}
