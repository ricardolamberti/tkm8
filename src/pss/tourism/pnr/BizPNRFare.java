package pss.tourism.pnr;

import pss.bsp.config.carrierGroups.detalle.BizCarrierGroupDetail;
import pss.bsp.regions.detalle.BizRegionDetail;
import pss.common.customList.config.relation.JRelation;
import pss.common.customList.config.relation.JRelations;
import pss.core.services.fields.JBoolean;
import pss.core.services.fields.JCurrency;
import pss.core.services.fields.JInteger;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.tools.JExcepcion;
import pss.core.tools.JPair;
import pss.tourism.airports.BizAirport;

public class BizPNRFare extends JRecord {
	protected JString pCompany=new JString();
  private JString pCodigopnr = new JString();
	protected JInteger pSecuencia=new JInteger();
  private JString pAirportFrom = new JString();
  private JString pAirportTo = new JString();
  private JString pRuta = new JString();
  private JString pClase = new JString();
  private JString pTipoClase = new JString();
  private JString pTipoPasajero = new JString();
  private JCurrency pImpuesto = new JCurrency() {
  	public void preset() throws Exception {
  		setSimbolo(pMoneda.isNotNull());
  		setMoneda(pMoneda.getValue());
  	};
  }; 
  private JCurrency pImporte = new JCurrency() {
  	public void preset() throws Exception {
  		setSimbolo(pMoneda.isNotNull());
  		setMoneda(pMoneda.getValue());
  	};
  }; 
  private JCurrency pTotalIdaYVuelta = new JCurrency() {
  	public void preset() throws Exception {
  		setSimbolo(pMoneda.isNotNull());
  		setMoneda(pMoneda.getValue());
  	};
  }; 
  private JCurrency pImporteIdaYVuelta = new JCurrency() {
  	public void preset() throws Exception {
  		setSimbolo(pMoneda.isNotNull());
  		setMoneda(pMoneda.getValue());
  	};
  }; 
  private JCurrency pNeto = new JCurrency() {
  	public void preset() throws Exception {
  		setSimbolo(pMoneda.isNotNull());
  		setMoneda(pMoneda.getValue());
  	};
  }; 
  private JCurrency pNetoIdaYVuelta = new JCurrency() {
  	public void preset() throws Exception {
  		setSimbolo(pMoneda.isNotNull());
  		setMoneda(pMoneda.getValue());
  	};
  }; 
  private JString pMonedaBase = new JString();
  private JString pMoneda = new JString();
  private JCurrency pImporteBase = new JCurrency() {
  	public void preset() throws Exception {
  		setSimbolo(pMonedaBase.isNotNull());
  		setMoneda(pMonedaBase.getValue());
  	};
  }; 
  private JCurrency pTotalBase = new JCurrency() {
  	public void preset() throws Exception {
  		setSimbolo(pMonedaBase.isNotNull());
  		setMoneda(pMonedaBase.getValue());
  	};
  }; 
  private JCurrency pNetoBase = new JCurrency() {
  	public void preset() throws Exception {
  		setSimbolo(pMonedaBase.isNotNull());
  		setMoneda(pMonedaBase.getValue());
  	};
  }; 
  private JLong pNroEscala = new JLong();
  private JBoolean pEmitido = new JBoolean();

  protected JString pPaisDestino = new JString();
  protected JString pPaisOrigen = new JString();

	public void setPaisDestino(String zValue) throws Exception {
		pPaisDestino.setValue(zValue);
	}
	public void setPaisOrigen(String zValue) throws Exception {
		pPaisOrigen.setValue(zValue);
	}

	public void setCompany(String zValue)  {
		pCompany.setValue(zValue);
	}

	public String getCompany() throws Exception {
		return pCompany.getValue();
	}



  protected JLong pId=new JLong();

	public long getId() throws Exception {
		return pId.getValue();
	}
	public void setId(long val){
		pId.setValue(val);
	}
	public long getNroEscala() throws Exception {
		return pNroEscala.getValue();
	}
	public void setNroEscala(long val){
		pNroEscala.setValue(val);
	}
	public boolean getEmitido() throws Exception {
		return pEmitido.getValue();
	}
	public void setEmitido(boolean val){
		pEmitido.setValue(val);
	}



//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  public void setCodigopnr(String zValue) throws Exception {    pCodigopnr.setValue(zValue);  }
  public String getCodigopnr() throws Exception {     return pCodigopnr.getValue();  }
  public void setTipoPasajero(String zValue) throws Exception {    pTipoPasajero.setValue(zValue);  }
  public String getTipoPasajero() throws Exception {     return pTipoPasajero.getValue();  }
  public void setImporte(double zValue) throws Exception {    pImporte.setValue(zValue);  }
  public double getImporte() throws Exception {     return pImporte.getValue();  }
  public void setNeto(double zValue) throws Exception {    pNeto.setValue(zValue);  }
  public double getNeto() throws Exception {     return pNeto.getValue();  }
  public void setTotalIdaYVuelta(double zValue) throws Exception {    pTotalIdaYVuelta.setValue(zValue);  }
  public double getTotlaIdaYVuelta() throws Exception {     return pTotalIdaYVuelta.getValue();  }
  public void setImporteIdaYVuelta(double zValue) throws Exception {    pImporteIdaYVuelta.setValue(zValue);  }
  public double getImporteIdaYVuelta() throws Exception {     return pImporteIdaYVuelta.getValue();  }
  public void setNetoIdaYVuelta(double zValue) throws Exception {    pNetoIdaYVuelta.setValue(zValue);  }
  public double getNetoIdaYVuelta() throws Exception {     return pNetoIdaYVuelta.getValue();  }
  public void setMonedaBase(String zValue) throws Exception {    pMonedaBase.setValue(zValue);  }
  public void setMoneda(String zValue) throws Exception {    pMoneda.setValue(zValue);  }
  public void setTotalBase(double zValue) throws Exception {    pTotalBase.setValue(zValue);  }
  public double getTotalBase() throws Exception {     return pTotalBase.getValue();  }
  public void setImporteBase(double zValue) throws Exception {    pImporteBase.setValue(zValue);  }
  public double getImporteBase() throws Exception {     return pImporteBase.getValue();  }
  public void setNetoBase(double zValue) throws Exception {    pNetoBase.setValue(zValue);  }
  public double getNetoBase() throws Exception {     return pNetoBase.getValue();  }
  public void setImpuesto(double zValue) throws Exception {    pImpuesto.setValue(zValue);  }
  public double getImpuesto() throws Exception {     return pImpuesto.getValue();  }
  public void setSecuencia(long zValue) throws Exception {    pSecuencia.setValue(zValue);  }
  public int getSecuencia() throws Exception {     return pSecuencia.getValue();  }
  public void setAirportFrom(String zValue) throws Exception {    pAirportFrom.setValue(zValue);  }
  public String getAirportFrom() throws Exception {     return pAirportFrom.getValue();  }
  public void setAirportTo(String zValue) throws Exception {    pAirportTo.setValue(zValue);  }
  public String getAirportTo() throws Exception {     return pAirportTo.getValue();  }
  public void setRuta(String zValue) throws Exception {    pRuta.setValue(zValue);  }
  public String getRuta() throws Exception {     return pRuta.getValue();  }
  public void setClase(String zValue) throws Exception {    pClase.setValue(zValue);  }
  public String getClase() throws Exception {     return pClase.getValue();  }
  public void setTipoClase(String zValue) throws Exception {    pTipoClase.setValue(zValue);  }
  public String getTipoClase() throws Exception {     return pTipoClase.getValue();  }


  /**
   * Constructor de la Clase
   */
  public BizPNRFare() throws Exception {
    this.addItem( "company", pCompany);
  	this.addItem( "interface_id", pId);
    this.addItem( "codigoPNR", pCodigopnr );
    this.addItem( "secuencia", pSecuencia );
    this.addItem( "airport_from", pAirportFrom );
    this.addItem( "airport_to", pAirportTo );
    this.addItem( "fare", pTipoPasajero );
    this.addItem( "ruta", pRuta );
    this.addItem( "clase", pClase );
    this.addItem( "tipo_clase", pTipoClase );
    this.addItem( "Impuesto", pImpuesto );
    this.addItem( "Importe", pImporte );
    this.addItem( "neto", pNeto );
    this.addItem( "importe_iv", pImporteIdaYVuelta );
    this.addItem( "neto_iv", pNetoIdaYVuelta );
    this.addItem( "total_iv", pTotalIdaYVuelta );
    this.addItem( "moneda_base", pMonedaBase );
    this.addItem( "moneda", pMoneda );
    this.addItem( "importe_base", pImporteBase );
    this.addItem( "neto_base", pNetoBase );
    this.addItem( "total_base", pTotalBase);
    this.addItem( "pais_destino", pPaisDestino);    
    this.addItem( "pais_origen", pPaisOrigen);    
    this.addItem( "nro_escala", pNroEscala);    
    this.addItem( "emitido", pEmitido);    

  }
  /**
   * Adds the fixed object properties
   */
  @Override
	public void createFixedProperties() throws Exception {
  	this.addFixedItem( KEY, "interface_id", "Id Interfaz", true, true, 50);
    this.addFixedItem( KEY, "secuencia", "Secuencia", true, true, 18 );
  	this.addFixedItem( FIELD, "company", "empresa", true, false, 50);
    this.addFixedItem( FIELD, "codigoPNR", "PNR", true, true, 30 );
    this.addFixedItem( FIELD, "ruta", "Ruta", true, true, 1000 );
    this.addFixedItem( FIELD, "clase", "Clase", true, true, 100 );
    this.addFixedItem( FIELD, "tipo_clase", "Tipo Clase", true, true, 100 );
    this.addFixedItem( FIELD, "fare", "Tipo pasajero", true, true, 300 );
    this.addFixedItem( FIELD, "airport_from", "Aeropuerto desde", true, true, 30 );
    this.addFixedItem( FIELD, "airport_to", "Aeropuerto a", true, true, 30 );
    this.addFixedItem( FIELD, "Impuesto", "Cargos", true, true, 18,2 );
    this.addFixedItem( FIELD, "Importe", "Importe", true, true, 18,2 );
    this.addFixedItem( FIELD, "neto", "Neto", true, true, 18,2 );
    this.addFixedItem( FIELD, "importe_iv", "Importe ida y vuelta", true, true, 18,2 );
    this.addFixedItem( FIELD, "neto_iv", "Neto ida y vuelta", true, true, 18,2 );
    this.addFixedItem( FIELD, "total_iv", "Total ida y vuelta", true, true, 18,2 );
    this.addFixedItem( FIELD, "moneda_base", "Moneda base", true, true, 18 );
    this.addFixedItem( FIELD, "moneda", "Moneda", true, true, 18 );
    this.addFixedItem( FIELD, "importe_base", "Importe base", true, true, 18,2 );
    this.addFixedItem( FIELD, "neto_base", "Neto base", true, true, 18,2 );
    this.addFixedItem( FIELD, "total_base", "Total base", true, true, 18,2 );
    this.addFixedItem( FIELD, "nro_escala", "Nro escala", true, false, 18 );
  	this.addFixedItem( FIELD, "pais_destino", "Pais destino regiones ", true, false, 100);
  	this.addFixedItem( FIELD, "pais_origen", "Pais origen regiones", true, false, 100);
    this.addFixedItem( FIELD, "emitido", "Emitido", true, false, 1 );
  }
  /**
   * Returns the table name
   */
  @Override
	public String GetTable() { return "TUR_PNR_FARE"; }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void attachRelationMap(JRelations rels) throws Exception {
  	rels.setSourceWinsClass("pss.tourism.pnr.GuiPNRFares");
  	rels.hideField("interface_id");
  	//rels.hideField("company");
  	rels.hideField("secuencia");  	
		rels.hideField("codigoPNR");
//		JRelation r = rels.addRelationParent(21, "Aerolinea", BizCarrier.class, "Carrier", "carrier");
//		r.addJoin("precios.Carrier", "carrier.carrier");
//		r.setAlias("carrier");

		JRelation r = rels.addRelationParent(51, "Aeropuerto Origen", BizAirport.class, "airport_from", "code");
		r.addJoin("TUR_PNR_FARE.airport_from", "ae_despegue.code");
		r.setAlias("ae_despegue");

		r = rels.addRelationParent(52, "Aeropuerto Destino", BizAirport.class, "airport_to", "code");
		r.addJoin("TUR_PNR_FARE.airport_to", "ae_arrivo.code");
		r.setAlias("ae_arrivo");
		
  	r=rels.addRelationChild(70, "Grupo Carriers", BizCarrierGroupDetail.class);
  	r.addJoin("TUR_PNR_FARE.company", "cg_detail.company");
  	r.addJoin("TUR_PNR_FARE.carrier", "cg_detail.carrier");
  	r.setAlias("cg_detail");


		rels.addGeoPostions("airport_from", new JPair(new JLong(51), new JString("geo_position")));
		rels.addGeoPostions("airport_to", new JPair(new JLong(52), new JString("geo_position")));
		rels.addGeoPostions("*", new JPair(new JLong(52), new JString("geo_position")));

  	r=rels.addRelationChild(54, "Mis Regiones precio destino", BizRegionDetail.class);
  	r.addJoin("TUR_PNR_FARE.pais_destino", "BSP_REGION_DETAIL.pais");
  	r.addJoin("TUR_PNR_FARE.company", "BSP_REGION_DETAIL.company");
  	r.setTypeJoin(JRelations.JOIN_LEFT);
 

	}

  @Override
  public void processInsert() throws Exception {
  //	setPaisDestino(getObjAeropuertoDespegue().getCountry());
  	super.processInsert();
  }
  
  @Override
  public void processUpdate() throws Exception {
  //	setPaisDestino(getObjAeropuertoDespegue().getCountry());
    super.processUpdate();
  }
	public BizAirport getObjAeropuerto(String key) throws Exception {
		BizAirport p = new BizAirport();
		p.dontThrowException(true);
		if (!p.Read(key)) return null;
		return p;
	}
	BizAirport objAeropuertoDespegue;
	public BizAirport getObjAeropuertoDespegue() throws Exception {
//		if (pDespegue.isNull()) return null;
		if (objAeropuertoDespegue!=null) return objAeropuertoDespegue;
		if (this.getAirportFrom().trim().isEmpty()) return null;
		BizAirport p = new BizAirport();
		p.dontThrowException(true);
		if (!p.Read(this.getAirportFrom())) 
			return null;
		return objAeropuertoDespegue=p;
	}
	
	BizAirport objAeropuertoArribo;
	public BizAirport getObjAeropuertoArrivo() throws Exception {
		if (getAirportTo().trim().equals("")) return null;
		if (objAeropuertoArribo!=null) return objAeropuertoArribo;
		BizAirport p = new BizAirport();
		p.dontThrowException(true);
		if (!p.Read(this.getAirportTo()))
			return null;
		return objAeropuertoArribo=p;
	}

  public boolean read( String company, long zinterface_id, long zSecuencia ) throws Exception { 
    clearFilters(); 
    addFilter( "company",  company ); 
    addFilter( "interface_id",  zinterface_id ); 
    addFilter( "secuencia",  zSecuencia); 
    return Read(); 
  } 
 
  public boolean readFare( String company, long zinterface_id, String zFare ,String zRuta ) throws Exception { 
    clearFilters(); 
    addFilter( "company",  company ); 
    addFilter( "interface_id",  zinterface_id ); 
    addFilter( "fare",  zFare); 
    addFilter( "ruta",  zRuta); 
    return Read(); 
  } 
 
	public String getRecordName() throws Exception {
		return "Fare";
	}

}
