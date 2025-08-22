package pss.tourism.pnr;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ibm.icu.util.Calendar;

import pss.JPath;
import pss.bsp.bspBusiness.BizBSPCompany;
import pss.common.customList.config.relation.JRelation;
import pss.common.customList.config.relation.JRelations;
import pss.common.event.mailing.BizMailingPersona;
import pss.common.regions.divitions.BizPaisLista;
import pss.common.security.BizUsuario;
import pss.core.services.JExec;
import pss.core.services.fields.JBoolean;
import pss.core.services.fields.JDate;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.JDateTools;
import pss.core.tools.JPair;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JMap;
import pss.core.tools.collections.JOrderedMap;
import pss.tourism.airports.BizAirport;
import pss.tourism.interfaceGDS.FileProcessor;
import pss.tourism.interfaceGDS.amadeus.AmadeusFileProcessor;
import pss.tourism.interfaceGDS.sabre.SabreFileProcessor;
import pss.tourism.interfaceGDS.travelport.TravelPortFileProcessor;

public class BizPNRReserva  extends JRecord {

  private JLong pSecuenceId = new JLong();
  
	private JString pNroIata = new JString();
	private JString pOfficeId = new JString();
	private JString pCityCode = new JString();
	private JDate pPNRDate = new JDate();
	private JDate pCreationDate = new JDate();
	private JDate pCreationDateAir = new JDate();
	private JString pDirectory = new JString();
	private JDate pFechaProcesamiento = new JDate();
	private JString pTransactionType = new JString();
	private JString pPais = new JString();
	private JString pArchivo = new JString();
	private JString pVersion = new JString();
	private JString pGDS = new JString();
	private JString pOrigen = new JString();
	private JString pCustomerId = new JString();
	private JString pCustomerIdReducido = new JString();
	private JString pTipoPrestacion = new JString();
	private JBoolean pVoid = new JBoolean();
	private JString pRoute = new JString();
	private JString pMiniRoute = new JString();
	JString pOrder = new JString();

	private JBoolean pInternacional = new JBoolean();
	private JString pInternacionalDescr = new JString();

	private JString pCodigopnr = new JString();
	private JString pNombrePasajero = new JString();
	private JLong   pCodigoSegmento = new JLong();

  private JDate pFechaSalida = new JDate();
  
  private JString pPaisOrigen = new JString();
  private JString pPaisDestino = new JString();
  private JString pVendedor = new JString();
  private JString pCompany = new JString();

	private JString pAirIntinerary = new JString();
	private JString pAirGenIntinerary = new JString();
	private JString pAirPaisIntinerary = new JString();
	private JString pCarrierIntinerary = new JString();
	private JString pClassIntinerary = new JString();
	private JString pFechaIntinerary = new JString();
	private JString pFareIntinerary = new JString();

	private JDate pDepartureDate = new JDate();
	private JDate pArriveDate = new JDate();
	private JDate pEndTravelDate = new JDate();
	protected JString pAeropuertoOrigen = new JString();
	protected JString pAeropuertoDestino = new JString();

	private JLong pCantidadSegmentos = new JLong();
	private JLong pCantidadRoundTrip = new JLong();
	private JLong pIdTicket = new JLong();

	protected JString pClase = new JString();
	protected JString pTipoClase = new JString();
	private JString pTipoOperacion = new JString();
	private JString pCodigoaerolineaIntern = new JString();
	private JString pCodigoaerolinea = new JString();

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  public void setSecuenceId(long zValue) throws Exception {    pSecuenceId.setValue(zValue);  }
  public long getSecuenceId() throws Exception {     return pSecuenceId.getValue();  }
  public boolean isNullSecuenceId() throws Exception { return  pSecuenceId.isNull(); } 
  public void setNullToSecuenceId() throws Exception {  pSecuenceId.setNull(); } 
  public void setFechaSalida(Date zValue) throws Exception {    pFechaSalida.setValue(zValue);  }
  public Date getFechaSalida() throws Exception {     return pFechaSalida.getValue();  }
  public boolean isNullFechaSalida() throws Exception { return  pFechaSalida.isNull(); } 
  public void setNullToFechaSalida() throws Exception {  pFechaSalida.setNull(); } 

  public void setPaisDestino(String zValue) throws Exception {    pPaisDestino.setValue(zValue);  }
  public String getPaisDestiono() throws Exception {     return pPaisDestino.getValue();  }
  public void setPaisOrigen(String zValue) throws Exception {    pPaisOrigen.setValue(zValue);  }
  public String getPaisOrigen() throws Exception {     return pPaisOrigen.getValue();  }

  public void setVendedor(String zValue) throws Exception {    pVendedor.setValue(zValue);  }
  public String getVendedor() throws Exception {     return pVendedor.getValue();  }
  public boolean isNullVendedor() throws Exception { return  pVendedor.isNull(); } 
  public void setNullToVendedor() throws Exception {  pVendedor.setNull(); } 
  public void setCompany(String zValue) throws Exception {    pCompany.setValue(zValue);  }
  public String getCompany() throws Exception {     return pCompany.getValue();  }
  public boolean isNullCompany() throws Exception { return  pCompany.isNull(); } 
  public void setNullToCompany() throws Exception {  pCompany.setNull(); } 
	public void setArchivo(String value) throws Exception {this.pArchivo.setValue(value);}
	public void setVersion(String value) throws Exception {this.pVersion.setValue(value);}
	public void setGDS(String value) throws Exception {this.pGDS.setValue(value);}
	public void setOrigen(String value) throws Exception {		this.pOrigen.setValue(value);}
	public void setNroIata(String value) throws Exception {		this.pNroIata.setValue(value);}
	public void setOfficeId(String value) throws Exception {		this.pOfficeId.setValue(value);}
	public void setCityCode(String value) throws Exception {		this.pCityCode.setValue(value);}
	public void setPNRDate(Date value) throws Exception {		this.pPNRDate.setValue(value);}
	public void setCreationDate(Date value) throws Exception {		this.pCreationDate.setValue(value);}
	public void setCreationDateAir(Date value) throws Exception {		this.pCreationDateAir.setValue(value);}
	public void setDirectory(String value) throws Exception {		this.pDirectory.setValue(value);}
	public void setFechaProcesamiento(Date value) throws Exception {		this.pFechaProcesamiento.setValue(value);}
	public void setTransactionType(String value) throws Exception {		this.pTransactionType.setValue(value);}
	public void setPais(String value) throws Exception {		this.pPais.setValue(value);}
	public void setCustomerId(String value) throws Exception {		this.pCustomerId.setValue(value);}
	public void setCustomerIdReducido(String value) throws Exception {
		this.pCustomerIdReducido.setValue(value);
	}

	public void setCustomerIdReducidoFromCustomer(String value) throws Exception {
		String cod = value;
		if (!(cod == null || cod.length() < 10)) {
			cod = value.substring(1, 3) + value.substring(6, 10);
		}
		this.pCustomerIdReducido.setValue(cod);
	}

	public void setTipoPrestacion(String value) throws Exception {		this.pTipoPrestacion.setValue(value);}
	public void setVoid(boolean value) throws Exception {		this.pVoid.setValue(value);}
	public void setCodigoPNR(String value) throws Exception {		this.pCodigopnr.setValue(value);}
  public String getCodigoPNR() throws Exception {     return pCodigopnr.getValue();  }
	public void setIdPax(long value) throws Exception {		this.pCodigoSegmento.setValue(value);}
	public void setNombrePasajero(String zValue) throws Exception {pNombrePasajero.setValue(zValue);	}

	

  /**
   * Constructor de la Clase
   */
  public BizPNRReserva() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "secuence_id", pSecuenceId );
    this.addItem( "archivo", pArchivo );
    this.addItem( "version", pVersion );
    this.addItem( "gds", pGDS );
    this.addItem( "origen", pOrigen );
    this.addItem( "office_id", pOfficeId );
    this.addItem( "nro_iata", pNroIata );
    this.addItem( "city_code", pCityCode );
    this.addItem( "fecha_proc", pFechaProcesamiento );
    this.addItem( "pnr_date", pPNRDate);
    this.addItem( "creation_date", pCreationDate );
    this.addItem( "creation_dateiar", pCreationDateAir );
    this.addItem( "directory", pDirectory );
    this.addItem( "transaction_type", pTransactionType );
    this.addItem( "pais", pPais );
    this.addItem( "codigo_pnr", pCodigopnr );
    this.addItem( "nombre_pasajero", pNombrePasajero );
    this.addItem( "id_pax", pCodigoSegmento );
    this.addItem( "customer_id", pCustomerId );
    this.addItem( "customer_id_reducido", pCustomerIdReducido );
    this.addItem( "tipo_prestacion", pTipoPrestacion );
    this.addItem( "void", pVoid );
    this.addItem( "fecha_salida", pFechaSalida );
    this.addItem( "pais_origen", pPaisOrigen );
    this.addItem( "pais_destino", pPaisDestino );
    this.addItem( "vendedor", pVendedor );
    this.addItem( "company", pCompany );
		this.addItem( "air_intinerary", pAirIntinerary);
		this.addItem( "air_pais_intinerary", pAirPaisIntinerary);
		this.addItem( "air_gen_intinerary", pAirGenIntinerary);
		this.addItem( "carrier_intinerary", pCarrierIntinerary);
		this.addItem( "class_intinerary", pClassIntinerary);
		this.addItem( "fecha_intinerary", pFechaIntinerary);
		this.addItem( "fare_intinerary", pFareIntinerary);
		this.addItem( "departure_date", pDepartureDate);
		this.addItem( "arrive_date", pArriveDate);
		this.addItem( "endtravel_date", pEndTravelDate);
		this.addItem("Internacional", pInternacional);
		this.addItem("Internacional_descr", pInternacionalDescr);
		this.addItem("route", pRoute);
		this.addItem("mini_route", pMiniRoute);
		this.addItem("aeropuerto_origen", pAeropuertoOrigen);
		this.addItem("aeropuerto_destino", pAeropuertoDestino);
		this.addItem("cant_segmentos", pCantidadSegmentos);
		this.addItem("cant_roundtrip", pCantidadRoundTrip);
		this.addItem("clase", pClase);
		this.addItem("tipo_clase", pTipoClase);
		this.addItem("codigo_aerolinea_intern", pCodigoaerolineaIntern);
		this.addItem("tipo_operacion", pTipoOperacion);
		this.addItem("CodigoAerolinea", pCodigoaerolinea);
		this.addItem("id_ticket", pIdTicket);
		this.addItem("order_str", pOrder);

  }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "secuence_id", "Secuence id", false, false, 64 );

    this.addFixedItem( FIELD, "archivo", "Archivo", true, false, 500 );
    this.addFixedItem( FIELD, "version", "Version", true, false, 50 );
    this.addFixedItem( FIELD, "gds", "GDS", true, false, 50 );
    this.addFixedItem( FIELD, "origen", "Origen pnr", true, false, 50 );
    this.addFixedItem( FIELD, "office_id", "Oficina", true, false, 50 );
    this.addFixedItem( FIELD, "nro_iata", "Iata", true, false, 50 );
    this.addFixedItem( FIELD, "city_code", "Ciudad pnr", true, false, 50 );
    this.addFixedItem( FIELD, "fecha_proc", "fecha_proc", true, false, 18 );
    this.addFixedItem( FIELD, "pnr_date", "pnr_date", true, false, 18 );
    this.addFixedItem( FIELD, "creation_dateiar", "Fecha modif pnr", true, false, 18 );
    this.addFixedItem( FIELD, "creation_date", "Fecha emisión", true, false, 18 );
    this.addFixedItem( FIELD, "directory", "directory", true, false, 500 );
    this.addFixedItem( FIELD, "transaction_type", "transaction_type", true, false, 50 );
    this.addFixedItem( FIELD, "nombre_pasajero", "Nombre pasajero", true, false, 250 );
    this.addFixedItem( FIELD, "codigo_pnr", "codigo pnr", true, false, 50 );
    this.addFixedItem( FIELD, "id_pax", "id pax", true, false, 18 );
    this.addFixedItem( FIELD, "customer_id", "Cliente", true, false, 50 );
    this.addFixedItem( FIELD, "customer_id_reducido", "DK", true, false, 50 );
    this.addFixedItem( FIELD, "tipo_prestacion", "Tipo prestacion", true, false, 50 );
    this.addFixedItem( FIELD, "void", "Anulado?", true, false, 1 );
  
    this.addFixedItem( FIELD, "pais_origen", "País Origen", true, false, 50 );
    this.addFixedItem( FIELD, "pais_destino", "País Destino", true, false, 50 );
    this.addFixedItem( FIELD, "vendedor", "Vendedor", true, false, 200 );
    this.addFixedItem( FIELD, "company", "Company", true, false, 15 );

  	this.addFixedItem(FIELD, "Internacional", "Internacional", true, false, 1);
		this.addFixedItem(FIELD, "internacional_descr", "Domestic/International", true, false, 100);
		this.addFixedItem(FIELD, "route", "Ruta", true, false, 300);
		this.addFixedItem(FIELD, "mini_route", "Origen/Destino", true, false, 300);
		this.addFixedItem(FIELD, "codigo_aerolinea_intern", "Primer Aerolínea Internac.", true, false, 2);

		this.addFixedItem(FIELD, "air_intinerary", "Intinerario aereo", true, false, 1000);
		this.addFixedItem(FIELD, "air_pais_intinerary", "Intinerario aereo paises", true, false, 1000);
		this.addFixedItem(FIELD, "air_gen_intinerary", "Intinerario aereo metro", true, false, 1000);
		this.addFixedItem(FIELD, "carrier_intinerary", "Intinerario aerolinea", true, false, 1000);
		this.addFixedItem(FIELD, "class_intinerary", "Intinerario clases", true, false, 1000);
		this.addFixedItem(FIELD, "fecha_intinerary", "Intinerario fechas", true, false, 1000);
		this.addFixedItem(FIELD, "fare_intinerary", "Intinerario Tarifa", true, false, 1000);
		this.addFixedItem(FIELD, "tipo_operacion", "Tipo Operacion", true, false, 300);
		this.addFixedItem(FIELD, "id_ticket", "ticket", true, false, 64);

		this.addFixedItem(FIELD, "departure_date", "Fecha Salida", true, false, 10);
		this.addFixedItem(FIELD, "arrive_date", "Fecha Llegada", true, false, 10);
		this.addFixedItem(FIELD, "endtravel_date", "Fecha Fin Viaje", true, false, 10);
		this.addFixedItem(FIELD, "aeropuerto_origen", "Aeropuerto Origen", true, false, 10);
		this.addFixedItem(FIELD, "aeropuerto_destino", "Aeropuerto Destino", true, false, 10);

		this.addFixedItem(FIELD, "cant_segmentos", "Cantidad bookings", true, false, 18);
		this.addFixedItem(FIELD, "cant_roundtrip", "Cantidad roundtrip", true, false, 18);

		this.addFixedItem(FIELD, "clase", "clase", true, false, 20);
		this.addFixedItem(FIELD, "tipo_clase", "Tipo clase", true, false, 20);
		this.addFixedItem(FIELD, "CodigoAerolinea", "Aerolínea Cod.", true, false, 2);
		this.addFixedItem(VIRTUAL, "order_str", "Orden", true, false, 10);

  }
  /**
   * Returns the table name
   */
  public String GetTable() { return "tur_pnr_reserva"; }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	BizBSPCompany objCompany;

	public BizBSPCompany getObjCompany() throws Exception {
		if (objCompany != null)
			return objCompany;
		BizBSPCompany comp = new BizBSPCompany();
		comp.Read(pCompany.getValue());
		return comp;
	}

	public void setCodigopnr(String zValue) throws Exception {
		pCodigopnr.setValue(zValue);
	}
	public long getId() throws Exception {
		return pSecuenceId.getValue();
	}
	public String getCodigopnr() throws Exception {
		return pCodigopnr.getValue();
	}

	public void setRoute(String value) throws Exception {
		this.pRoute.setValue(value);
		if (value != null && value.length() > 3)
			this.pMiniRoute.setValue(pAeropuertoOrigen.getValue() + "/" + pAeropuertoDestino.getValue());
	}

	public void setMiniRoute(String value) throws Exception {
		this.pMiniRoute.setValue(value);
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

	public String getCustomerId() throws Exception {
		return this.pCustomerId.getValue();
	}

	public boolean isNullCustomerId() throws Exception {
		return this.pCustomerId.isNull();
	}


	public void setAirIntinerary(String value) throws Exception {
		this.pAirIntinerary.setValue(value);
	}

	public String getAirIntinerary() throws Exception {
		return this.pAirIntinerary.getValue();
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
	public String getCarrier() throws Exception {
		return pCodigoaerolinea.getValue();
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
	public void setFareIntinerary(String value) throws Exception {
		this.pFareIntinerary.setValue(value);
	}
	public String getLineaAerea() throws Exception {
		return pCodigoaerolinea.getValue();
	}

	public void setCodigoaerolinea(String zValue) throws Exception {
		pCodigoaerolinea.setValue(zValue);
	}
	public boolean hasTicket() throws Exception {
		return pIdTicket.hasValue();
	}

	public long getTicket() throws Exception {
		return pIdTicket.getValue();
	}
	public void setTicket(long ticket) throws Exception {
		 pIdTicket.setValue(ticket);
	}
	public String getFechaIntinerary() throws Exception {
		return this.pFechaIntinerary.getValue();
	}

	public void setDepartureDate(Date value) throws Exception {
		this.pDepartureDate.setValue(value);
	}
	public void setFirstCarrierInternacional(String zValue) throws Exception {
		pCodigoaerolineaIntern.setValue(zValue);
	}

	public String getFirstCarrierInternacional() throws Exception {
		return pCodigoaerolineaIntern.getValue();
	}
	
	public void setArriveDate(Date value) throws Exception {
		this.pArriveDate.setValue(value);
	}

	public void setEndTravelDate(Date value) throws Exception {
		this.pEndTravelDate.setValue(value);
	}
	
	public String getNombrePasajero() throws Exception {
		return pNombrePasajero.getValue();
	}

	public Date getCreationDate() throws Exception {
		return this.pCreationDate.getValue();
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
	public String getTipoOperacion() throws Exception {
		return pTipoOperacion.getValue();
	}
	public void setTipoOperacion(String zValue) throws Exception {

		pTipoOperacion.setValue(zValue);
	
	}

	public String getOrigen() throws Exception {
		return this.pOrigen.getValue();
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
	public void setClase(String zValue) throws Exception {
		pClase.setValue(zValue);
	}

	public void setTipoClase(String zValue) throws Exception {
		pTipoClase.setValue(zValue);
	}
	private BizAirport aeropuertoOrigen;
	private BizAirport aeropuertoDestino;

	public BizAirport getObjAeropuertoOrigen() throws Exception {
		if (this.aeropuertoOrigen != null)
			return this.aeropuertoOrigen;
		BizAirport a = new BizAirport();
		a.dontThrowException(true);
		if (!a.Read(this.pAeropuertoOrigen.getValue()))
			return null;
		return (this.aeropuertoOrigen = a);
	}
	public BizAirport getObjAeropuertoDestino() throws Exception {
		if (this.aeropuertoDestino != null)
			return this.aeropuertoDestino;
		BizAirport a = new BizAirport();
		a.dontThrowException(true);
		if (!a.Read(this.pAeropuertoDestino.getValue()))
			return null;
		return (this.aeropuertoDestino = a);
	}

  /**
   * Default read() method
   */
  public boolean read( long zSecuenceId ) throws Exception { 
    addFilter( "secuence_id",  zSecuenceId ); 
    return read(); 
  } 
  public boolean readByPNR( String zCompany,String zIata,String zPnr,long zcodigo ) throws Exception { 
    addFilter( "company",  zCompany ); 
    addFilter( "nro_iata",  zIata ); 
    addFilter( "codigo_pnr",  zPnr ); 
    addFilter( "id_pax",  zcodigo ); 
    return read(); 
  } 
  public boolean readByName( String zCompany,String zIata,String zPnr,String zcodigo ) throws Exception { 
    addFilter( "company",  zCompany ); 
    addFilter( "nro_iata",  zIata ); 
    addFilter( "codigo_pnr",  zPnr ); 
    addFilter( "nombre_pasajero",  zcodigo ); 
    return read(); 
  } 
	public void attachRelationMap(JRelations rels) throws Exception {
		rels.setSourceWinsClass("pss.tourism.pnr.GuiPNRReserva");

		JRelation rel;
		rel = rels.addRelationForce(10, "restriccion usuario");
//		rel.addFilter(" (TUR_PNR_RESERVA.company in (COMPANY_CUSTOMLIST)) ");
		if (BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).isDependant()) {
			rel.addFilter(" (TUR_PNR_RESERVA.company in (COMPANY_TICKET) and TUR_PNR_RESERVA.customer_id_reducido='"+BizBSPCompany.getObjBSPCompany( BizUsuario.getUsr().getCompany()).getCodigoCliente()+"')  ");
		} else {
			rel.addFilter(" (TUR_PNR_RESERVA.company in (COMPANY_CUSTOMLIST)) ");
		}
		JRelation r;
		r = rels.addRelationParent(94, "IATA", BizMailingPersona.class, "nro_iata", "codigo");
		r.addJoin("TUR_PNR_RESERVA.nro_iata", "iata.codigo");
		r.addJoin("TUR_PNR_RESERVA.company", "iata.company");
		r.addFilter("iata.tipo='" + BizMailingPersona.IATA + "'");
		r.setAlias("iata");

		rels.hideField("secuencia_id");
		//rels.hideField("company");
		rels.hideField("archivo");
		rels.hideField("origen");
		rels.hideField("fecha_proc");
		rels.hideField("pais");
	}




	public void execReprocesar() throws Exception {
		JExec oExec = new JExec(this, "reprocesar") {

			@Override
			public void Do() throws Exception {
				reprocesar();
			}
		};
		oExec.execute();
	}
	public void reprocesar() throws Exception {
		boolean first = true;

		JRecords<BizPNRFile> files = getArchivos();
		JIterator<BizPNRFile> it = files.getStaticIterator();
		while (it.hasMoreElements()) {
			BizPNRFile file = it.nextElement();
			String origen = JPath.PssPathInputProcessed() + "/" + file.getDirectory() + "/" + file.getArchivo();

	
			processFileInternal(origen, true);

		}

	}
	public void processFileInternal(String filename, boolean reprocess) throws Exception {

		FileProcessor fp = null;
		if (filename.indexOf("SABRE")!=-1)
			fp = new SabreFileProcessor();
		else if (filename.indexOf("AMADEUS")!=-1)
			fp = new AmadeusFileProcessor();
		else if (filename.indexOf("TRAVELPORT")!=-1)
			fp = new TravelPortFileProcessor();
		else
			throw new Exception("GDS desconocido");
		fp.processFileInternal(new File(filename), reprocess);
	}
	
	public JRecords<BizPNRFile> getArchivos() throws Exception {

		JRecords<BizPNRFile> files = new JRecords<BizPNRFile>(BizPNRFile.class);
		files.setStatic(true);
		files.clearStaticItems();

		JRecords<BizPNRFilename> fs = new JRecords<BizPNRFilename>(BizPNRFilename.class);
		fs.addFilter("company", this.getCompany());
		fs.addFilter("pnr", this.getCodigoPNR());
		fs.addOrderBy("arrive_order");
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

	public void processAnalizeSegments(JRecords<BizPNRSegmentoAereo> segmentos) throws Exception {
		new AuxiliarClaseCalculoSegmentos().processAnalizeSegmentsInterno(segmentos);
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
			segmentos = zsegmentos;
			segmentos.Ordenar("codigosegmento");
			calculeMaxSegmento();
			if (airportInicio == null) {
				return;
			}

			armarRuta();
			calculosVarios();
			setRoute(ruta);
			setBookings(cantSegmentos == 0 ? 1 : cantSegmentos);
			setCantidadRoundTrip(/* cantSegmentoIR */roundTrip ? 2 : 1);
			setTipoOperacion(getTipoOperacion());
			setClase(clase);
			setTipoClase(tipoclase);
			setFirstCarrierInternacional(firstCarrier);
			setDepartureDate(fechaSalida);
			if (getDepartureDate() != null && JDateTools.getDaysBetween(getDepartureDate(), getCreationDate()) > 10) {// hay
				// un margen porque encontre boletos confechas de salida unos dias
				// posteriores a la de despegue
				Calendar cal = Calendar.getInstance();
				cal.setTime(getCreationDate());
				cal.add(Calendar.YEAR, -1);
				setCreationDate(cal.getTime());
				cal.setTime(pCreationDateAir.getValue());
				cal.add(Calendar.YEAR, -1);
				setCreationDateAir(cal.getTime());

			}
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
				s.setDestinoPais(s.getObjAeropuertoDestino().getObjCountry() == null ? null : s.getObjAeropuertoDestino().getObjCountry().GetPais());
				s.setOrigenPais(s.getObjAeropuertoOrigen().getObjCountry() == null ? null : s.getObjAeropuertoOrigen().getObjCountry().GetPais());
				s.processUpdateOrInsertWithCheck();
				origen = s.getDespegue();
				destino = s.getArrivo();
				pura &= (aero.equals(s.getCarrier()));
				paisorigen = s.getObjAeropuertoDespegue().getObjCountry() == null ? null : s.getObjAeropuertoDespegue().getObjCountry().GetPais();
				paisdestino = s.getObjAeropuertoArrivo().getObjCountry() == null ? null : s.getObjAeropuertoArrivo().getObjCountry().GetPais();
				areaorigen = s.getObjAeropuertoDespegue().getObjCountry() == null ? null : s.getObjAeropuertoDespegue().getIataArea();
				areadestino = s.getObjAeropuertoArrivo().getObjCountry() == null ? null : s.getObjAeropuertoArrivo().getIataArea();
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

				if (s.getCarrier().equals(getCarrier())) {
					if (clase != null) {
						if (clasePrioridad < s.getPrioridad()) {
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
			if (pInternacionalDescr.isNull() ) {
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


			BizAirport airportOr = getObjAeropuertoOrigen();
			BizPaisLista paisOr = airportOr != null ? airportOr.getObjCountry() : null;
			BizAirport airportDst = getObjAeropuertoDestino();
			BizPaisLista paisDst = airportDst != null ? airportDst.getObjCountry() : null;

//			setInterlineal(!pura);
			// countryCompany = getObjCompany().getCountry();
			// if (paisOr != null && paisDst != null) {
			// if (paisDst.GetPais().equals(countryCompany) &&
			// !paisOr.GetPais().equals(countryCompany)) {
			// setPaisDestino(paisOr.GetPais());
			// setPaisOrigen(paisDst.GetPais());
			// } else {
			if (paisDst!=null)
			setPaisDestino(paisDst.GetPais());
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
			String paisInt = "";
			String genInt = "";
			String fecInt = "";
			String carInt = "";
			String clsInt = "";
			String fareInt = "";
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
				genInt += (genInt.equals("") ? "" : "-") + ( areaMet.equals("")?seg.getDespegue():areaMet );
				paisInt += (paisInt.equals("") ? "" : "-") + seg.getObjAeropuertoDespegue().getCountry();
				if (seg.getFechaDespegue() != null)
					fecInt += (fecInt.equals("") ? "" : "-") + JDateTools.dateToStr(seg.getFechaDespegue(), "ddMMM").toUpperCase();
				carInt += (carInt.equals("") ? "" : "-") + seg.getCarrier();
				clsInt += (clsInt.equals("") ? "" : "-") + seg.getClase();
				fareInt += (fareInt.equals("") ? "" : "-") + seg.getFareBasic();
				
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
			genInt += (genInt.equals("") ? "" : "-") + ( areaMet.equals("")?lastSeg.getDespegue():areaMet );
			paisInt += (paisInt.equals("") ? "" : "-") + lastSeg.getObjAeropuertoArrivo().getCountry();
			if (lastSeg.getFechaDespegue() != null)
				fecInt += (fecInt.equals("") ? "" : "-") + JDateTools.dateToStr(lastSeg.getFechaDespegue(), "ddMMM").toUpperCase();
			carInt += (carInt.equals("") ? "" : "-") + lastSeg.getCarrier();
			clsInt += (clsInt.equals("") ? "" : "-") + lastSeg.getClase();
			fareInt += (fareInt.equals("") ? "" : "-") + lastSeg.getFareBasic();

			setAirIntinerary(airInt);
			setAirPaisIntinerary(paisInt);
			setAirGenIntinerary(genInt);
			setCarrierIntinerary(carInt);
			setClassIntinerary(clsInt);
			setFechaIntinerary(fecInt);
			setFareIntinerary(fareInt);
		}
		public boolean addPoint(JMap<String,String> map, String point) throws Exception {
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
			while (iter.hasMoreElements()) {
				BizPNRSegmentoAereo seg = iter.nextElement();
				boolean isConnection = seg.getConnectionIndicator().equals(BizPNRSegmentoAereo.SEGMENTO_CONNECTION);
				boolean esCorte=(ant != null && !isEqualAirport(objAirportIni,objAirportFin) &&ant.getFechaArrivo() != null && seg.getFechaDespegue() != null && !isConnection);
				if (airportInicio == null) {
					airportInicio = seg.getDespegue();
					objAirportIni = seg.getObjAeropuertoDespegue();
					loop|=addPoint(map,objAirportIni.getIataArea().equals("")?airportInicio:objAirportIni.getIataArea());
					paisorigen = objAirportIni.getObjCountry() == null ? "" : objAirportIni.getObjCountry().GetPais();
					loopP|=addPoint(mapP,paisorigen);
				}
				airportFin = seg.getArrivo();
				objAirportFin = seg.getObjAeropuertoArrivo();
				loop|=addPoint(map,objAirportFin.getIataArea().equals("")?airportFin:objAirportFin.getIataArea());
				paisdestino = objAirportFin.getObjCountry() == null ? "" : objAirportFin.getObjCountry().GetPais();
				loopP|=addPoint(mapP,paisdestino);

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
					if (maxTiempo == 0 || tiempo > maxTiempo) {
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
			boolean isEqualAirport(BizAirport ini,BizAirport fin) throws Exception {
			if (ini.getCode().equals(fin.getCode())) return true;
			if (!ini.getIataArea().equals("") && ini.getIataArea().equals(fin.getIataArea())) return true;
			return false;		
		}

		

	}

	public BizPNRTicket getObjTicket() throws Exception {
		BizPNRTicket tk = new BizPNRTicket();
		tk.dontThrowException(true);
		if (!tk.read(getId())) return null;
		return tk;
	}
	
}
