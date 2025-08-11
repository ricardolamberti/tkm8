package pss.tourism.airports;


import pss.bsp.config.airportGroups.detalle.BizAirportGroupDetail;
import pss.common.components.JSetupParameters;
import pss.common.customList.config.relation.JRelation;
import pss.common.customList.config.relation.JRelations;
import pss.common.regions.divitions.BizPaisLista;
import pss.core.services.fields.JGeoPosition;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.tools.GeoPosition;
import pss.tourism.pnr.PNRCache;

public class BizAirport extends JRecord {

	// -------------------------------------------------------------------------//
	// Propiedades de la Clase
	// -------------------------------------------------------------------------//
	private JString pCode=new JString();
	private JString pDescription=new JString();
	private JString pCountry=new JString();
	private JString pCity=new JString();
	private JString pIataArea=new JString();
	private JGeoPosition pGeoPosition=new JGeoPosition();
	JString pDescrCountry=new JString() {

		@Override
		public void preset() throws Exception {
			pDescrCountry.setValue((getObjCountry()==null) ? "sin definir" : getObjCountry().GetDescrip());
		}
	};
	// JString pBusqueda=new JString();
	// JScript pCalculado;

	private BizPaisLista oCountry;

	public String getCode() throws Exception {
		return pCode.getValue();
	}

	public String getDescription() throws Exception {
		return pDescription.getValue();
	}
	public String getIataArea() throws Exception {
		return pIataArea.getValue();
	}
	public void setIataArea(String value) throws Exception {
		pIataArea.setValue(value);
	}

	public void setCode(String value) throws Exception {
		pCode.setValue(value);
	}

	public void setDescription(String value) throws Exception {
		pDescription.setValue(value);
	}

	public void setCountry(String zValor) {
		this.pCountry.setValue(zValor);
	}

	public String getCountry() throws Exception {
		return this.pCountry.getValue();
	}

	public String getCity() throws Exception {
		return pCity.getValue();
	}

	public GeoPosition getGeoPosicion() throws Exception {
		return pGeoPosition.getValue();
	}

	public void setCity(String value) throws Exception {
		pCity.setValue(value);
	}

	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public BizAirport() throws Exception {
	}

	@Override
	public void createProperties() throws Exception {
		addItem("code", pCode);
		addItem("description", pDescription);
		addItem("country", pCountry);
		addItem("city", pCity);
		addItem("iata_area", pIataArea);
		addItem("geo_position", pGeoPosition);
		addItem("descr_country", pDescrCountry);
		// addItem("busqueda", pBusqueda);
		/*
		 * assignCatalog("tur_airport");
		 * 
		 * SimpleBindings bind=new SimpleBindings(); bind.put("code", "code"); bind.put("description", "description");
		 * 
		 * pCalculado=new JScript(bind, "code.getValue().concat(description.getValue())"); addItem("calculado", pCalculado);
		 */
	}

	@Override
	public void createFixedProperties() throws Exception {

		addFixedItem(KEY, "code", "Código", true, true, 15);
		addFixedItem(FIELD, "description", "Descr.Aeropuerto", true, true, 100);
		addFixedItem(FIELD, "country", "País", true, false, 2);
		addFixedItem(FIELD, "city", "Ciudad", true, false, 50);
		addFixedItem(FIELD, "iata_area", "Iata Metropolitan Area", true, false, 50);
		addFixedItem(FIELD, "geo_position", "Geoposicion", true, false, 50);
		// this.addFixedItem(FOREIGN, "descr_country", "País", false, false, 15, 0, "", "", "reg_pais");
		addFixedItem(VIRTUAL, "descr_country", "País", true, true, 15);
		// addFixedItem(VIRTUAL, "busqueda", "Busqueda", true, true, 150);

		// addFixedItem(VIRTUAL, "calculado", "Calculado", true, false, 50);

	}

  public void attachRelationMap(JRelations rels) throws Exception {
  	rels.setSourceWinsClass(GuiAirports.class.getName());
  	rels.hideField("descr_country");
  	rels.hideField("code");
		JRelation r=rels.addRelationParent(60, "Pais", BizPaisLista.class, "country","pais");
		r.addJoin("TUR_AIRPORT.country", "airpais.pais");
		//r.setTypeJoin(JRelations.JOIN_LEFT);
		r.setAlias("airpais");
  	r=rels.addRelationChild(80, "Grupo Aeropuertos", BizAirportGroupDetail.class);
		r.addJoin("TUR_PNR_BOLETO.company", "airgrupo.company");
		r.addJoin("TUR_AIRPORT.code", "airgrupo.airport");
  	r.setAlias("airgrupo");


  }

	@Override
	public String GetTable() {
		return "TUR_AIRPORT";
	}

	@Override
	protected void setupConfig(JSetupParameters zParams) throws Exception {
		zParams.setTruncateData(zParams.isLevelCountry());
		zParams.setExportData(zParams.isLevelCountry());
	}

	public boolean Read(String zCode) throws Exception {
		addFilter("code", zCode);
		return this.read();
	}

	@Override
	public void processDelete() throws Exception {
		super.processDelete();
	}

	public static void checkAirportExists(String value) throws Exception {
		BizAirport airport=PNRCache.getAirportCache().getElement(value);
		if (airport==null && !value.isEmpty()) {
			PNRCache.createTemporaryAirport(value);
		}
	}

	static public String getCiudad(String id) throws Exception {
		if (id.trim().equals("")) return null;
		BizAirport record=PNRCache.getAirportCache().getElement(id);
			if (record==null) return null;
		return record.getCity();
	}

	public BizPaisLista getObjCountry() throws Exception {
		if (this.oCountry!=null) return this.oCountry;
		return (this.oCountry=PNRCache.getPaisListaCache().getElement(pCountry.getValue()));
	}

	@Override
	public String getRecordName() throws Exception {
		return "Aeropuerto";
	}

	public String getDescripField() throws Exception {
		return "description";
	}
	

}
