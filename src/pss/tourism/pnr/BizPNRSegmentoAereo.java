package pss.tourism.pnr;

import java.util.Date;

import pss.bsp.clase.BizClase;
import pss.bsp.clase.detalle.BizClaseDetail;
import pss.bsp.config.carrierGroups.detalle.BizCarrierGroupDetail;
import pss.bsp.consola.config.BizBSPConfig;
import pss.bsp.familia.BizFamilia;
import pss.bsp.familia.detalle.BizFamiliaDetail;
import pss.bsp.regions.detalle.BizRegionDetail;
import pss.common.customList.config.relation.JRelation;
import pss.common.customList.config.relation.JRelations;
import pss.core.services.fields.JBoolean;
import pss.core.services.fields.JCurrency;
import pss.core.services.fields.JDate;
import pss.core.services.fields.JGeoPosition;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.tools.GeoPosition;
import pss.core.tools.JExcepcion;
import pss.core.tools.JPair;
import pss.core.tools.JTools;
import pss.tourism.airports.BizAirport;
import pss.tourism.carrier.BizCarrier;

public class BizPNRSegmentoAereo extends JRecord {
  
  public final static String SEGMENTO_IDA = "IDA";
  public final static String SEGMENTO_DESTINO = "FIN";
  public final static String SEGMENTO_RETORNO = "RET";
  
  public final static String SEGMENTO_START = "S";
  public final static String SEGMENTO_CONNECTION = "X";
  public final static String SEGMENTO_STOP = "O";
   
	protected JString pCodigoPNR = new JString();
	protected JString pCodigoSegmento = new JString();
	protected JLong pLongCodigoSegmento = new JLong() {
		public void preset() throws Exception {
			pLongCodigoSegmento.setValue(JTools.getLongFirstNumberEmbedded(pCodigoSegmento.getValue()));
		};
	};
	protected JString pDespegue = new JString();
	protected JString pArrivo = new JString();
	protected JString pOrigen = new JString();
	protected JString pDestino = new JString();
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
	protected JString pHoraArrivo = new JString();
	protected JString pEstado = new JString();
	protected JString pTipoEquipo = new JString();
	protected JLong pId=new JLong();
	protected JString pCompany=new JString();
  protected JString pSegmentoInicial = new JString();
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
  protected JBoolean pEmitido = new JBoolean();
	protected JLong pIdFare=new JLong();
	protected JString pFareBasic=new JString();
	protected JString pFareBasicExtended=new JString();
	protected JString pFamiliaTarifaria=new JString();
	protected JString pTipoFamiliaTarifaria=new JString();
	protected JString pMoneda=new JString();
	protected JString pMonedaBase=new JString();
	protected JString pLimitePeso=new JString();
//  protected JString pPais = new JString();
//  protected JString pCiudadOrigen = new JString();
//  protected JString pContinenteOrigen = new JString();
//  protected JString pRegionOrigen = new JString();
  protected JString pPaisDestino = new JString();
//  protected JString pCiudadDestino = new JString();
//  protected JString pContinenteDestino = new JString();
//  protected JString pRegionDestino = new JString();

//  protected JObjBD pObjArrivo = new JObjBD() {
//  	public void preset() throws Exception {
//  		pObjArrivo.setValue(getObjAeropuertoArribo());
//  	};
//  };
//  protected JObjBD pObjDespegue = new JObjBD() {
//  	public void preset() throws Exception {
//  		pObjDespegue.setValue(getObjAeropuertoDespegue());
//  	};
//  };
	protected JString pCarrierOp = new JString();

  protected JString pTipoSegmento = new JString();
//  protected JGeoPosition pGeoArrivo = new JGeoPosition();
//  protected JGeoPosition pGeoDespegue = new JGeoPosition();
  protected JGeoPosition pGeopArrivo = new JGeoPosition();
  protected JGeoPosition pGeopDespegue = new JGeoPosition();
	protected JString pDescrDespegue = new JString(){
		public void preset() throws Exception {
			pDescrDespegue.setValue(getObjAeropuertoDespegue()==null?"":getObjAeropuertoDespegue().getDescription());
		};
	};
	protected JString pDescrArrivo = new JString(){
		public void preset() throws Exception {
			pDescrArrivo.setValue(getObjAeropuertoArrivo()==null?"":getObjAeropuertoArrivo().getDescription());
		};
	};
	protected JString pDescrCarrier = new JString(){
		public void preset() throws Exception {
			pDescrCarrier.setValue(getObjCarrier()==null?"":getObjCarrier().getDescription());
		};
	};

	public void setCompany(String zValue)  {
		pCompany.setValue(zValue);
	}

	public String getCompany() throws Exception {
		return pCompany.getValue();
	}



	public long getId() throws Exception {
		return pId.getValue();
	}
	
	public void setId(long val){
		pId.setValue(val);
	}


	protected JString pCodigoComida = new JString();
	protected JString pCodigoEntreten = new JString();
	protected JString pDuracion = new JString();

	public void setCodigoComida(String value) { pCodigoComida.setValue(value);}
	public void setCodigoEntreten(String value) { pCodigoEntreten.setValue(value);}
	public void setDuracion(String value) { pDuracion.setValue(value);}
	
	public void setCodigoPNR(String value) { pCodigoPNR.setValue(value);}
	public void setCodigoSegmento(String value) { pCodigoSegmento.setValue(value);}
	public void setDespegue(String value) { pDespegue.setValue(value);}
	public void setGeoDespegue(GeoPosition value) { pGeopDespegue.setValue(value);}
	public void setArrivo(String value) { pArrivo.setValue(value);}
	public void setOrigen(String value) { pOrigen.setValue(value);}
	public void setDestino(String value) { pDestino.setValue(value);}
	public void setOrigenPais(String value) { pOrigenPais.setValue(value);}
	public void setDestinoPais(String value) { pDestinoPais.setValue(value);}
	public void setGeoArrivo(GeoPosition value) { pGeopArrivo.setValue(value);}	
	public void setCarrier(String value) { pCarrier.setValue(value);}
	public void setCarrierPlaca(String value) { pCarrierPlaca.setValue(value);}
	public void setCarrierOp(String value) { pCarrierOp.setValue(value);}
	public void setClase(String value) { pClase.setValue(value);}
	public void setTipoClase(String value) { pTipoClase.setValue(value);}
	public void setPrioridad(long value) { pPrioridad.setValue(value);}
	public void setFechaDespegue(Date value) { pFechaDespegue.setValue(value);}
	public void setNumeroVuelo(String value) { pNumeroVuelo.setValue(value);}
	public void setHoraDespegue(String value) { pHoraDespegue.setValue(value);}
	public void setFechaArrivo(Date value) { pFechaArrivo.setValue(value);}
	public void setHoraArrivo(String value) { pHoraArrivo.setValue(value);}
	public void setEstado(String value) { pEstado.setValue(value);}
	public void setTipoEquipo(String value) { pTipoEquipo.setValue(value);}
	public void setConnectionIndicator(String value) { pSegmentoInicial.setValue(value);}
	public void setMonto(double value) { pMonto.setValue(value);}
	public void setMontoOriginal(double value) { pMontoOriginal.setValue(value);}
	public void setEmitido(boolean value) { pEmitido.setValue(value);}
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
	public String getClase() throws Exception {return pClase.getValue(); }	
	public String getFareBasic() throws Exception {return pFareBasic.getValue(); }	
	public String getFareBasicExt() throws Exception {return pFareBasicExtended.getValue(); }	
	public String getTipoClase() throws Exception {return pTipoClase.getValue(); }	
	public long getPrioridad() throws Exception {return pPrioridad.getValue(); }	
	
  public BizPNRSegmentoAereo() throws Exception {
		super();
	}
  
  public String getFamiliaTarifaria() throws Exception {
  	return pFamiliaTarifaria.getValue();
  }
  
  public String getCodigoPNR() throws Exception {return pCodigoPNR.getValue();}
//  public void setPais(String zValue) throws Exception {
//		pPais.setValue(zValue);
//	}
  
	public void setPaisDestino(String zValue) throws Exception {
		pPaisDestino.setValue(zValue);
	}
	public String getDestinoPais() throws Exception {
		return pDestinoPais.getValue();
	}
	public String getOrigenPais() throws Exception {
		return pOrigenPais.getValue();
	}
//	public void setCiudadOrigen(String zValue) throws Exception {
//		pCiudadOrigen.setValue(zValue);
//	}
//	public void setCiudadDestino(String zValue) throws Exception {
//		pCiudadDestino.setValue(zValue);
//	}
//	public void setRegionOrigen(String zValue) throws Exception {
//		pRegionOrigen.setValue(zValue);
//	}
//	public void setRegionDestino(String zValue) throws Exception {
//		pRegionDestino.setValue(zValue);
//	}
//	public void setContinenteOrigen(String zValue) throws Exception {
//		pContinenteOrigen.setValue(zValue);
//	}
//	public void setContinenteDestino(String zValue) throws Exception {
//		pContinenteDestino.setValue(zValue);
//	}
  public int getCodigoSegmento() throws Exception {return Integer.parseInt(pCodigoSegmento.getValue());}
  public String getDespegue() throws Exception {
  	BizAirport.checkAirportExists(pDespegue.getValue());
  	return pDespegue.getValue();
  }
  public String getArrivo() throws Exception {
  	BizAirport.checkAirportExists(pArrivo.getValue());
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
  public GeoPosition getGeoDespegue() throws Exception {
  	return getObjAeropuertoDespegue()==null?null:getObjAeropuertoDespegue().getGeoPosicion();
  }
  public String getRuta() throws Exception {
  	return pRuta.getValue();
  }
  public void setRuta(String r) throws Exception {
  	pRuta.setValue(r);
  }
  /*
  public String getClase() throws Exception {
  	BizProductClass oClass = new BizProductClass();
  	oClass.SetNoExcSelect(true);
  	if (!oClass.Read(BizUsuario.getUsr().getCompany(), BizArticulo.ART_AIR, "1", pClase.getValue())) {
  		oClass = new BizProductClass();
  		oClass.setCompany(BizUsuario.getUsr().getCompany());
  		oClass.setProductType(BizArticulo.ART_AIR);
  		oClass.setClassGroup(BizProductClass.CLASS_GROUP_MAIN);
  		oClass.setCode(pClase.getValue());
  		oClass.setDescription(pClase.getValue()+" -> Sin Descripción");
  		oClass.execProcessInsert();
  	}
  	return pClase.getValue();
  }*/
  
  
  public Date getFechaDespegue() throws Exception {return pFechaDespegue.getValue();}
  public String getNumeroVuelo() throws Exception {return pNumeroVuelo.getValue();}
  public String getHoraDespegue() throws Exception {return pHoraDespegue.getValue();}
  public Date getFechaArrivo() throws Exception {return pFechaArrivo.getValue();}
  public String getHoraArrivo() throws Exception {return pHoraArrivo.getValue();}
  public String getEstado() throws Exception {return pEstado.getValue();}
  public String getTipoEquipo() throws Exception {return pTipoEquipo.getValue();}

  @Override
	public void createProperties() throws Exception {
		addItem("interface_id", pId);
		addItem("CodigoPNR"           , pCodigoPNR );
		addItem("CodigoSegmento"        , pCodigoSegmento);
		addItem("LongCodigoSegmento"        , pLongCodigoSegmento);
  	addItem("Despegue"        , pDespegue);
  	addItem("Arrivo"        , pArrivo);
  	addItem("Origen"        , pOrigen);
  	addItem("Destino"        , pDestino);
  	addItem("Origen_pais"        , pOrigenPais);
  	addItem("Destino_pais"        , pDestinoPais);
   	addItem("ruta"        , pRuta);
    addItem("descr_despegue"        , pDescrDespegue);
  	addItem("descr_arrivo"        , pDescrArrivo);
  	addItem("Carrier"        , pCarrier);
  	addItem("descr_carrier"        , pDescrCarrier);
  	addItem("carrier_op"        , pCarrierOp);
  	addItem("carrier_placa"        , pCarrierPlaca);
  	addItem("Clase"        , pClase);
  	addItem("tipo_clase"        , pTipoClase);
  	addItem("prioridad_clase"        , pPrioridad);
  	addItem("FechaDespegue"        , pFechaDespegue);
  	addItem("NumeroVuelo"        , pNumeroVuelo);
  	addItem("HoraDespegue"        , pHoraDespegue);
  	addItem("FechaArrivo"        , pFechaArrivo);
  	addItem("HoraArrivo"        , pHoraArrivo);
  	addItem("Estado"        , pEstado);
  	addItem("TipoEquipo"        , pTipoEquipo);
  	addItem("CodigoComida"        , pCodigoComida);
  	addItem("codigoentretenimiento"        , pCodigoEntreten);
  	addItem("duracionvuelo"        , pDuracion);
		addItem("company", pCompany);
		addItem("segmento_ini", pSegmentoInicial);
		addItem("tipo_segmento", pTipoSegmento);
		addItem("monto", pMonto);
		addItem("monto_orig", pMontoOriginal);
		addItem("emitido", pEmitido);
		addItem("id_fare", pIdFare);
		addItem("fare_basic", pFareBasic);
		addItem("fare_basic_ext", pFareBasicExtended);
		addItem("fare_family", pFamiliaTarifaria);
		addItem("group_fare_family", pTipoFamiliaTarifaria);
		addItem("weight", pLimitePeso);
		addItem("currency", pMoneda);
		addItem("currency_base", pMonedaBase);
//		addItem("geo_arrivo", pGeoArrivo);
//		addItem("geo_despegue", pGeoDespegue);
		addItem("geop_arrivo", pGeopArrivo);
		addItem("geop_despegue", pGeopDespegue);
//    this.addItem( "pais", pPais);    
//    this.addItem("ciudad_origen", pCiudadOrigen);
//    this.addItem("continente_origen", pContinenteOrigen);    
//    this.addItem("region_origen", pRegionOrigen);    
    this.addItem("pais_destino", pPaisDestino);    
//    this.addItem("ciudad_destino", pCiudadDestino);
//    this.addItem("continente_destino", pContinenteDestino);    
//    this.addItem("region_destino", pRegionDestino);    



  }
  @Override
	public void createFixedProperties() throws Exception {
		addFixedItem(KEY, "interface_id", "Id Interfaz", true, false, 18);
		addFixedItem(KEY, "CodigoSegmento", "Código Segmento", true, true, 3);
		addFixedItem(VIRTUAL, "LongCodigoSegmento", "Código Segmento", true, false, 3);

  	addFixedItem(FIELD, "company", "empresa", true, false, 50);
  	addFixedItem(FIELD, "CodigoPNR", "PNR", true, true, 15);
  	
  	addFixedItem(FIELD, "Carrier", "Código Aerolinea", true, false, 50);
		addFixedItem(VIRTUAL, "descr_carrier", "Aerolinea", true, false, 250);
  	addFixedItem(FIELD, "carrier_op", "Código Aerolinea Operadora", true, false, 50);
  	addFixedItem(FIELD, "Carrier_placa", "Código Aerolinea placa", true, false, 50);
		addFixedItem(FIELD, "Clase", "Clase", true, false, 50);
  	addFixedItem(FIELD, "tipo_clase", "Clase Grupo", true, false, 50);
  	addFixedItem(FIELD, "prioridad_clase", "Prioridad", true, false, 18);
  	addFixedItem(FIELD, "NumeroVuelo", "Numero Vuelo", true, false, 50);
  	addFixedItem(FIELD, "TipoEquipo", "Tipo Avión", true, false, 50);

  	addFixedItem(FIELD, "CodigoComida", "Código Comida", true, false, 100);
  	addFixedItem(FIELD, "codigoentretenimiento", "Código Entretenimiento", true, false, 100);
  	addFixedItem(FIELD, "duracionvuelo", "Duracion Vuelo", true, false, 100);
  	
  	addFixedItem(FIELD, "Estado", "Estado", true, false, 50);
		
  	addFixedItem(FIELD, "tipo_segmento", "Tipo Segmento", true, false, 10);
		addFixedItem(FIELD, "segmento_ini", "Ind.Conexion", true, false, 1);
  	addFixedItem(FIELD, "monto", "Importe", true, false, 18,2);
  	addFixedItem(FIELD, "monto_orig", "Imp.Original", true, false, 18,2);
  	addFixedItem(FIELD, "emitido", "Emitido", true, false, 1);
  	addFixedItem(FIELD, "id_fare", "Id tarifario", true, false, 18);
  	addFixedItem(FIELD, "fare_basic", "Tarif.base", true, false, 18);
  	addFixedItem(FIELD, "fare_basic_ext", "Tarif.ext.", true, false, 18);
  	addFixedItem(FIELD, "fare_family", "Tarif.familia", true, false, 18);
  	addFixedItem(FIELD, "group_fare_family", "Grupo Tarif.familia", true, false, 18);
  	addFixedItem(FIELD, "weight", "Peso maximo", true, false, 18);
  	addFixedItem(FIELD, "currency", "Moneda", true, false, 18);
  	addFixedItem(FIELD, "currency_base", "Moneda base", true, false, 18);


		addFixedItem(FIELD, "Despegue", "Aer. Origen Código", true, false, 50);
		addFixedItem(FIELD, "ruta", "Ruta", true, false, 50);
		addFixedItem(VIRTUAL, "descr_despegue", "Aer. Origen", true, false, 250);
  	addFixedItem(FIELD, "FechaDespegue", "Fecha Despegue", true, false, 50);
  	addFixedItem(FIELD, "HoraDespegue", "Hora Despegue", true, false, 50);
//		this.addFixedItem( FIELD, "ciudad_origen", "Ciudad Origen", true, false, 100);
//    this.addFixedItem( FIELD, "pais", "Pais Origen", true, true, 200 );    
//		this.addFixedItem( FIELD, "region_origen", "Region Origen", true, false, 100);
//		this.addFixedItem( FIELD, "continente_origen", "Continente Origen", true, false, 100);
		this.addFixedItem( FIELD, "geop_despegue", "Geo Origen", true, false, 50);
//
  	addFixedItem(FIELD, "Arrivo", "Aer.Destino Código", true, false, 50);
  	addFixedItem(VIRTUAL, "descr_arrivo", "Aer. Destino", true, false, 250);
  	addFixedItem(FIELD, "Origen", "Origen tramo", true, false, 50);
  	addFixedItem(FIELD, "Destino", "Destino tramo", true, false, 50);
  	addFixedItem(FIELD, "Origen_pais", "Origen tramo pais", true, false, 50);
  	addFixedItem(FIELD, "Destino_pais", "Destino tramo pais", true, false, 50);
  	addFixedItem(FIELD, "FechaArrivo", "Fecha Arrivo", true, false, 50);
  	addFixedItem(FIELD, "HoraArrivo", "Hora Arrivo", true, false, 50);
//		this.addFixedItem( FIELD, "ciudad_destino", "Ciudad Destino", true, false, 100);
		this.addFixedItem( FIELD, "pais_destino", "Pais Destino", true, false, 100);
//		this.addFixedItem( FIELD, "region_destino", "Region Destino", true, false, 100);
//		this.addFixedItem( FIELD, "continente_destino", "Continente Destino", true, false, 100);
		this.addFixedItem( FIELD, "geop_arrivo", "Geo Destino", true, false, 50);
//		addFixedItem(RECORD, "obj_despegue", "Aeropuerto despegue", true, false, 50).setClase(BizAirport.class);
//		addFixedItem(RECORD, "obj_arrivo", "Aeropuerto arrivo", true, false, 50).setClase(BizAirport.class);
  }
  @Override
	public String GetTable() {
    return "TUR_PNR_SEGMENTOAEREO";
  }
  
  @Override
  protected boolean loadForeignFields() throws Exception {
  	return true;
  }
  
//  @Override
//  public String GetTableTemporal() throws Exception {
//		String s = "SELECT air2.geo_position as geo_despegue,TUR_AIRPORT.geo_position as geo_arrivo,company, interface_id, fechadespegue, numerovuelo, segmento_ini,  tipoequipo, clase, codigocomida, codigopnr, estado, fechaarrivo,  codigosegmento, carrier, arrivo, duracionvuelo, despegue, horadespegue,  tipo_segmento, horaarrivo, codigoentretenimiento  FROM tur_pnr_segmentoaereo LEFT JOIN TUR_AIRPORT ON TUR_AIRPORT.code = arrivo LEFT JOIN TUR_AIRPORT air2 ON air2.code = despegue";
//		return "("+s+") as "+GetTable() ;
////  	return null;
//  }
 
  public String getCarrier() throws Exception {return pCarrier.getValue();}
  public String getCarrierPlaca() throws Exception {return pCarrierPlaca.getValue();}

  public boolean Read( String zCompany, String zCodigopnr ) throws Exception { 
    addFilter( "company",  zCompany); 
    addFilter( "CodigoPNR",  zCodigopnr ); 
    return read(); 
  } 
	public void attachRelationMap(JRelations rels) throws Exception {
  	rels.setSourceWinsClass("pss.tourism.pnr.GuiPNRSegmentoAereos");
//  	rels.addGeoPostions("despegue", new JPair(new JString(""), new JString("geop_despegue")));
//  	rels.addGeoPostions("arrivo", new JPair(new JString(""), new JString("geop_arrivo")));
//  	rels.addGeoPostions("*", new JPair(new JString(""), new JString("geop_despegue")));
  	JRelation r;
  	r=rels.addRelationParent(21, "Aerolinea", BizCarrier.class, "Carrier","carrier");
//  	r.setTypeJoin(JRelations.JOIN_LEFT);
  	r.addJoin("TUR_PNR_SEGMENTOAEREO.Carrier","carrier.carrier");
  	r.setAlias("carrier");

  	r=rels.addRelationParent(51, "Aeropuerto Origen", BizAirport.class, "despegue","code");
  	r.addJoin("TUR_PNR_SEGMENTOAEREO.despegue", "ae_despegue.code");
//  	r.setTypeJoin(JRelations.JOIN_LEFT);
  	r.setAlias("ae_despegue");
  	
  	r=rels.addRelationParent(52, "Aeropuerto Destino", BizAirport.class, "arrivo","code");
  	r.addJoin("TUR_PNR_SEGMENTOAEREO.arrivo", "ae_arrivo.code");
//  	r.setTypeJoin(JRelations.JOIN_LEFT);
  	r.setAlias("ae_arrivo");

//  	r=rels.addRelationParent(60, "Clase", BizClase.class, "tipo_clase","id");
//  	r.addJoin("company","company");
//  	r.addJoin("id","clase");
  	
  	r=rels.addRelationChild(70, "Grupo Carriers", BizCarrierGroupDetail.class);
  	r.addJoin("TUR_PNR_SEGMENTOAEREO.company", "cg_detail.company");
  	r.addJoin("TUR_PNR_SEGMENTOAEREO.carrier", "cg_detail.carrier");
  	r.setAlias("cg_detail");

//  	r=rels.addRelationChild(80, "Grupo Aeropuertos", BizAirportGroupDetail.class);
//  	r.addJoin("segment.company", "ag_detail.company");
//  	r.addJoin("segment.arrivo", "ag_detail.airport");
//  	r.setAlias("ag_detail");

  	rels.addGeoPostions("despegue", new JPair(new JLong(51),new JString("geo_position")));
  	rels.addGeoPostions("arrivo", new JPair(new JLong(52),new JString("geo_position")));
  	rels.addGeoPostions("*", new JPair(new JLong(52), new JString("geo_position")));

//  	r=rels.addRelationParent(33, "Continente Origen", BizPaisLista.getContinentes(), "continente_origen");
//  	r=rels.addRelationParent(34, "Region Origen", BizPaisLista.getRegiones(), "region_origen");
//  	r=rels.addRelationParent(35, "Continente Destino", BizPaisLista.getContinentes(), "continente_destino");
//  	r=rels.addRelationParent(36, "Region Destino", BizPaisLista.getRegiones(), "region_destino");
//  	r=rels.addRelationParent(37, "Pais Origen", BizPaisLista.class, "pais");
//  	r.addJoin("segment.pais", "pais_despegue.pais");
//  	r.setTypeJoin(JRelations.JOIN_LEFT);
//  	r.setAlias("pais_despegue");

//  	r=rels.addRelationParent(38, "Pais Destino", BizPaisLista.class, "pais_destino");
//  	r.addJoin("segment.pais_destino", "pais_arribo.pais");
//  	r.setTypeJoin(JRelations.JOIN_LEFT);
//  	r.setAlias("pais_arribo");

  	rels.hideField("interface_id");
  	//rels.hideField("company");
  	rels.hideField("CodigoPNR");
//  	rels.hideField("CodigoSegmento");
//  	rels.hideField("segmento_ini");
//  	rels.hideField("tipo_segmento");
  	rels.hideField("Estado");
  	rels.hideField("LongCodigoSegmento");

  	
//  	rels.addFieldGroup("*","pais", "*", "Origen");
//  	rels.addFieldGroup("*","ciudad_origen", "*", "Origen");
//  	rels.addFieldGroup("*","continente_origen", "*", "Origen");
//  	rels.addFieldGroup("*","region_origen", "*", "Origen");
//  	rels.addFieldGroup("*","geop_despegue", "*", "Origen");
//
//  	rels.addFieldGroup("*","ciudad_destino", "*", "Destino");
//  	rels.addFieldGroup("*","region_destino", "*", "Destino");
//  	rels.addFieldGroup("*","pais_destino", "*", "Destino");
//  	rels.addFieldGroup("*","continente_destino", "*", "Destino");
//  	rels.addFieldGroup("*","geop_arrivo", "*", "Destino");
  	
  	r=rels.addRelationChild(54, "Mis Regiones segmento destino", BizRegionDetail.class);
  	r.addJoin("TUR_PNR_SEGMENTOAEREO.pais_destino", "BSP_REGION_DETAIL.pais");
  	r.addJoin("TUR_PNR_SEGMENTOAEREO.company", "BSP_REGION_DETAIL.company");
  	r.setTypeJoin(JRelations.JOIN_LEFT);
  	
  	


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
//		if (p==null && !this.getDespegue().equals("")) 
//			p=PNRCache.createTemporaryAirport(this.getDespegue());
		return objAeropuertoDespegue=p;
	}
	
	BizAirport objAeropuertoArribo;
	public BizAirport getObjAeropuertoArrivo() throws Exception {
//		if (pArrivo.isNull()) return null;
		if (objAeropuertoArribo!=null) return objAeropuertoArribo;
		BizAirport p = PNRCache.getAirportCache().getElement(this.getArrivo());
//		if (p==null && !this.getDespegue().equals("")) 
//			p=PNRCache.createTemporaryAirport(this.getArrivo());
		
		return objAeropuertoArribo=p;
	}
	
	
	BizAirport objAeropuertoOrigen;
	public BizAirport getObjAeropuertoOrigen() throws Exception {
//		if (pArrivo.isNull()) return null;
		if (objAeropuertoOrigen!=null) return objAeropuertoOrigen;
		BizAirport p = PNRCache.getAirportCache().getElement(this.getOrigen());
		if (p==null&& !this.getOrigen().equals(""))
			p=PNRCache.createTemporaryAirport(this.getOrigen());
		return objAeropuertoOrigen=p;
	}

	BizAirport objAeropuertoDestino;
	public BizAirport getObjAeropuertoDestino() throws Exception {
//		if (pArrivo.isNull()) return null;
		if (objAeropuertoDestino!=null) return objAeropuertoDestino;
		BizAirport p = PNRCache.getAirportCache().getElement(this.getDestino());
		if (p==null && !this.getDestino().equals("")) {
			p=PNRCache.createTemporaryAirport(this.getDestino());
		}
		return objAeropuertoDestino=p;
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
		if (objCarrier!=null) return objCarrier;
		BizCarrier p = PNRCache.getCarrierCache().getElement(this.getCarrier());
		if (p==null && !this.getCarrier().isEmpty())
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
		return "Segmentos";
	}
	
	public void processCalculate() throws Exception {
		BizClaseDetail cl =  PNRCache.getTipoClaseCache(getCompany(), this.getCarrierPlaca(), getClase(), !getDestinoPais().equals(getOrigenPais()));
		if (cl==null) {
			setTipoClase(BizClase.getTipoClaseDefault( getClase()));
			setPrioridad(BizClase.getPrioridadClaseDefault( getClase()));
		} else {
			setTipoClase(cl.getObjClase().getDescripcion());
			setPrioridad(cl.getPrioridad());
		}
		BizFamiliaDetail fm = PNRCache.getTipoFamiliaCache(getCompany(), getCarrier(), pFamiliaTarifaria.getValue());
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
		BizAirport a =PNRCache.getAirportCache().getElement(code);
		if (a==null) return null;
		return a.getGeoPosicion();
	}

}
