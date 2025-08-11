package  pss.common.personalData;

import java.lang.reflect.Method;

import pss.common.components.BizCompInstalados;
import pss.common.components.JSetupParameters;
import pss.common.customList.config.relation.JRelations;
import pss.common.regions.cities.BizCity;
import pss.common.regions.divitions.BizCiudad;
import pss.common.regions.divitions.BizLocalidad;
import pss.common.regions.divitions.BizPais;
import pss.common.regions.divitions.BizProvincia;
import pss.common.security.BizUsuario;
import pss.core.maps.GeoPositionable;
import pss.core.services.JExec;
import pss.core.services.fields.JBoolean;
import pss.core.services.fields.JGeoPosition;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.BizVirtual;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.GeoPosition;
import pss.core.tools.JExcepcion;
import pss.core.tools.JTools;
import pss.core.tools.collections.JIterator;

public class BizDomicilio extends JRecord implements GeoPositionable {
	// -------------------------------------------------------------------------//
	// Propiedades de la Clase
	// -------------------------------------------------------------------------//
	JLong pPersona=new JLong();
	JLong pSecuencia=new JLong();
	JString pCalle=new JString();
	JString pNumero=new JString();
	JString pOtros=new JString();
//	JString pNumero2=new JString();
	JString pLetra=new JString();
	JBoolean pBis=new JBoolean();
	JString pPiso=new JString();
	JString pDepto=new JString();
	JString pCiudadId=new JString();
	JString pLocalidadId=new JString();
	JString pCodPostal=new JString();
	JLong pCity=new JLong();
	JString pCiudad=new JString();
	JString pProvincia=new JString();
	JString pPais=new JString();
	JString pLocalidad=new JString();
	JString pComentarios=new JString();
	JBoolean pIsComercial=new JBoolean();
	JBoolean pIsParticular=new JBoolean();
	JBoolean pIsLegal=new JBoolean();
	JBoolean pIsListados=new JBoolean();
	JGeoPosition pGeoPosition=new JGeoPosition();
	JString pDescrPais=new JString() {
		@Override
		public void preset() throws Exception {
			setearDescrPais();
		}
	};
	JString pDescrProv=new JString() {
		@Override
		public void preset() throws Exception {
			pDescrProv.setValue(getDescrProv());
		}
	};
	JString pDescrCiudad=new JString() {
		@Override
		public void preset() throws Exception {
			pDescrCiudad.setValue(getDomicilioLocalidad());
		}
	};
	// JString pDescrTipoDom=new JString() {
	// @Override
	// public void preset() throws Exception {
	// setearDescrTipoDom();
	// }
	// };
	JString pDomicilio=new JString() {
		public void preset() throws Exception {
			pDomicilio.setValue(getDomicilioCompleto());
		}
	};
//	JString pCalleNroPisoDpto=new JString();
	public static final String TIPO_LEGAL="L";
	public static final String TIPO_PARTICULAR="P";
	public static final String TIPO_COMERCIAL="C";
	private static JRecords<BizVirtual> oTiposDomicilios=null;
	private BizPais pais;
	private BizProvincia provincia; //div1
	private BizCity city;
	private BizCiudad ciudad; //div2
	private BizPersona persona;

	// Getters

	public long GetPersona() throws Exception {
		return pPersona.getValue();
	}

	public long getSecuencia() throws Exception {
		return pSecuencia.getValue();
	}

	public String getLocalidad() throws Exception {
		return pLocalidad.getValue();
	}

	public String getCiudad() throws Exception {
		return pCiudad.getValue();
	}

	public long getCity() throws Exception {
		return pCity.getValue();
	}

	public String getPais() throws Exception {
		return pPais.getValue();
	}

	public String getProvincia() throws Exception {
		return pProvincia.getValue();
	}

	public String GetLocalidadId() throws Exception {
		return pLocalidadId.getValue();
	}

	public String GetCiudadId() throws Exception {
		return pCiudadId.getValue();
	}

	public String getCalle() throws Exception {
		return pCalle.getValue();
	}

	public String getNumero() throws Exception {
		return pNumero.getValue();
	}
//	public String getNumero2() throws Exception {
//		return pNumero2.getValue();
//	}
	public boolean getBisCalle() throws Exception {
		return pBis.getValue();
	}
	public void setBisCalle(boolean v) throws Exception {
		pBis.setValue(v);
	}
	public String getLetraCalle() throws Exception {
		return pLetra.getValue();
	}

	public String getCodPostal() throws Exception {
		return pCodPostal.getValue();
	}
	
	public boolean hasCodPostal() throws Exception {
		return !this.pCodPostal.isEmpty();
	}
	
	public boolean hasSecuencia() throws Exception {
		return this.pSecuencia.getValue()!=0L;
	}

	public String getOtros() throws Exception {
		return pOtros.getValue();
	}

	public String getComentarios() throws Exception {
		return pComentarios.getValue();
	}

	public String getDepto() throws Exception {
		return pDepto.getValue();
	}

	// Setters

	public void SetPersona(long zValue) {
		pPersona.setValue(zValue);
	}

	public void setSecuencia(long zValue) {
		pSecuencia.setValue(zValue);
	}
	public void setIsParticular(boolean value) {
		this.pIsParticular.setValue(value);
	}

	public void setIsComercial(boolean value) {
		this.pIsComercial.setValue(value);
	}

	public void setIsLegal(boolean value) {
		this.pIsLegal.setValue(value);
	}

	public void setIsListados(boolean value) {
		this.pIsListados.setValue(value);
	}

	// public void setTipoDomicilio(String zValue) {
	// pTipoDomicilio.setValue(zValue);
	// }
	public void SetCalle(String zValue) {
		pCalle.setValue(zValue);
	}
	public void SetCalleLetra(String zValue) {
		pLetra.setValue(zValue);
	}

	public void SetNumero(String zValue) {
		pNumero.setValue(zValue);
	}

	public void SetDpto(String zValue) {
		pDepto.setValue(zValue);
	}

	public String getPiso() throws Exception {
		return pPiso.getValue();
	}
	
	public void SetPiso(String zValue) {
		pPiso.setValue(zValue);
	}

	public void SetCodPostal(String zValue) {
		pCodPostal.setValue(zValue);
	}

	public void setOtros(String zValue) {
		pOtros.setValue(zValue);
	}

	public void setComentarios(String zValue) {
		pComentarios.setValue(zValue);
	}

	public void SetPais(String zValue) {
		pPais.setValue(zValue);
	}

	public void SetProvincia(String zValue) {
		pProvincia.setValue(zValue);
	}

	public void SetCiudad(String zValue) {
		pCiudad.setValue(zValue);
	}

	public void SetCiudadId(String zValue) {
		pCiudadId.setValue(zValue);
	}

	public void SetLocalidad(String zValue) {
		pLocalidad.setValue(zValue);
	}

	public void setCity(long zValue) {
		pCity.setValue(zValue);
	}

	public void SetLocalidadId(String zValue) {
		pLocalidadId.setValue(zValue);
	}

//	public void SetCalleNroPisoDpto(String zValue) {
//		pCalleNroPisoDpto.setValue(zValue);
//	}

	public boolean hasProvincia() throws Exception {
		return pProvincia.isNotNull();
	}

	public boolean hasPais() throws Exception {
		return pPais.isNotNull();
	}

	public boolean hasAnyCiudad() throws Exception {
		return this.hasCiudadId() || this.hasCiudad();
	}

	public boolean hasCiudadId() throws Exception {
		return pCiudadId.isNotNull();
	}

	public boolean hasCity() throws Exception {
		return pCity.getValue()!=0;
	}

	public boolean hasCiudad() throws Exception {
		return pCiudadId.isNotNull()||pCiudad.isNotNull();
	}

	public boolean hasAnyLocalidad() throws Exception {
		return this.hasLocalidadId() || this.hasLocalidad();
	}
	
	public boolean hasLocalidad() throws Exception {
		return pLocalidadId.isNotNull()||pLocalidad.isNotNull();
	}
	
	public boolean hasLocalidadId() throws Exception {
		return pLocalidadId.isNotNull();
	}


	public boolean hasDomicilio() throws Exception {
		return !pDomicilio.isEmpty();
	}

	public void clearClave() throws Exception {
		this.pPersona.setNull();
	}

	public GeoPosition getPosition() throws Exception {
		return pGeoPosition.getValue();
	}

	public void setPosition(GeoPosition zValue) {
		pGeoPosition.setValue(zValue);
	}

	public GeoPosition getGeoPosition(String field) throws Exception {
		return pGeoPosition.getValue();
	}
	
	public String getCoordenadasMapa() throws Exception {
		JGeoPosition pMapaPosition = pGeoPosition;
		if (pMapaPosition.isNull() || (pMapaPosition.getValue().getLatitude() == 0 || pMapaPosition.getValue().getLongitude() == 0))
			return "DIR:" + getDomicilioMapa();
		else
			return "GEO:"+pMapaPosition.getValue().getLatitude() + "," + pMapaPosition.getValue().getLongitude();
	}
	public String getDomicilioMapa() throws Exception {
		return BizDomicilio.buildDomicilioCompletoMapa(pCalle.getValue(), pNumero.getValue(), pLetra.getValue(), "", pBis.getValue(), pDescrPais.getValue(), pDescrProv.getValue(), pDescrCiudad.getValue());
	}

	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public BizDomicilio() throws Exception {
	}

	@Override
	public void createProperties() throws Exception {
		addItem("persona", pPersona);
		addItem("secuencia", pSecuencia);
		addItem("calle", pCalle);
		addItem("calle_letra", pLetra);
		addItem("numero", pNumero);
		addItem("piso", pPiso);
		addItem("depto", pDepto);
		addItem("cod_postal", pCodPostal);
		addItem("otros", pOtros);
		addItem("ciudad", pCiudad);
		addItem("ciudad_id", pCiudadId);
		addItem("provincia", pProvincia);
		addItem("city", pCity);
		addItem("pais", pPais);
		addItem("localidad", pLocalidad);
		addItem("localidad_id", pLocalidadId);
		addItem("descr_prov", pDescrProv);
		addItem("descr_pais", pDescrPais);
		addItem("descr_ciudad", pDescrCiudad);
		// addItem("descr_tipo_dom", pDescrTipoDom);
		addItem("descr_domicilio", pDomicilio);
		addItem("geo_position", pGeoPosition);
		addItem("comentarios", pComentarios);
		addItem("is_comercial", pIsComercial);
		addItem("is_particular", pIsParticular);
		addItem("is_legal", pIsLegal);
		addItem("is_listados", pIsListados);
	}

	@Override
	public void createFixedProperties() throws Exception {
		addFixedItem(KEY,   "persona", "Persona", true, true, 10);
		addFixedItem(KEY,   "secuencia", "Secuencia", true, true, 18);
		// addFixedItem(KEY, "tipo_domicilio", "Tipo Domicilio", true, true, 2);
		addFixedItem(FIELD, "calle", "Calle", true, false, 100);
		addFixedItem(FIELD, "calle_letra", "Orientación", true, false, 10);
		addFixedItem(FIELD, "numero", "Número", true, false, 20);
		addFixedItem(FIELD, "piso", "Piso", true, false, 30);
		addFixedItem(FIELD, "depto", "Depto", true, false, 5);
		addFixedItem(FIELD, "cod_postal", "Cód. Postal", true, false, 10);
		addFixedItem(FIELD, "otros", "Otros", true, false, 200);
		addFixedItem(FIELD, "ciudad", "Div 2", true, false, 50);
		addFixedItem(FIELD, "ciudad_id", "Div 2", true, false, 20);
		addFixedItem(FIELD, "provincia", "Div 1", true, false, 15);
		addFixedItem(FIELD, "city", "Ciudad", true, false, 15);
		addFixedItem(FIELD, "pais", "País", true, true, 5);
		addFixedItem(FIELD, "localidad", "Div 3", true, false, 50);
		addFixedItem(FIELD, "localidad_id", "Div 3", true, false, 20);
		addFixedItem(FIELD, "comentarios", "Comentarios", true, false, 200);
		addFixedItem(VIRTUAL, "descr_prov", getDivision(), true, true, 250);
//		addFixedItem(VIRTUAL, "descr_prov", "Descripcion Provincia", true, true, 250);
		addFixedItem(VIRTUAL, "descr_pais", "País", true, true, 250);
		addFixedItem(VIRTUAL, "descr_ciudad", "Ciudad", true, true, 250);
		addFixedItem(VIRTUAL, "descr_domicilio", "Domicilio", true, true, 250);
		addFixedItem(FIELD, "geo_position", "Posicion Global", true, false, 50);
		addFixedItem(FIELD, "is_particular", "Domicilio Particular", true, false, 1);
		addFixedItem(FIELD, "is_comercial", "Domicilio Comercial", true, false, 1);
		addFixedItem(FIELD, "is_legal", "Domicilio Legal", true, false, 1);
		addFixedItem(FIELD, "is_listados", "Domicilio para Listados", true, false, 1);
	}

	private boolean checkUser() throws Exception {
		if ( BizUsuario.getUsr() == null )
			return false;
		if ( BizUsuario.getUsr().getBirthCountryId().equals("") )
			return false;
		return true;
	}
	
//	private String getCity() throws Exception {
//		if ( checkUser() == false )
//			return "Ciudad";
//		return BizUsuario.getUsr().getObjBirthCountry().GetCiudad();
//	}
	
	private String getDivision() throws Exception {
		if ( checkUser() == false )
			return "Provincia";
		return BizUsuario.getUsr().getObjBirthCountry().GetDivision();
	}
	
	private String getCounty() throws Exception {
		if ( checkUser() == false )
			return "Localidad";
		return BizUsuario.getUsr().getObjBirthCountry().GetDivision();
	}
	
	

	@Override
	public String GetTable() {
		return "PER_DOMICILIO";
	}

	@Override
	public void setupConfig(JSetupParameters zParams) throws Exception {
		zParams.setExportData(zParams.isLevelBusiness());
	}

	public boolean Read(long zPersona, long sec) throws Exception {
		addFilter("persona", zPersona);
		addFilter("secuencia", sec);
		return this.read();
	}

	@Override
	public void validateConstraints() throws Exception {
//		BizPaisLista.doesExist(true, pPais.getValue());
//		if (this.hasProvincia()) BizProvincia.doesExist(true, pPais.getValue(), pProvincia.getValue());
//		this.checkFlags();
//		this.uncheckOtherFlags();
		// BizPais oPais = new BizPais();
		// oPais.Read(pPais.getValue());
		// if (oPais.ifUsaTablaCiudad() && pCiudadId.isNotNull()) {
		// BizCiudad oCiudad = new BizCiudad();
		// oCiudad.Read(GetPais(), GetProvincia(),
		// Integer.parseInt(GetCiudadId()));
		// pCiudad.setValue(oCiudad.getDescripcion());
		// } else if (oPais.ifUsaTablaCiudad() && pCiudadId.isNull())
		// pCiudad.setValue("");
		//
		// if (oPais.ifUsaTablaLocalidad() && pLocalidadId.isNotNull()) {
		// BizLocalidad oLocalidad = new BizLocalidad();
		// oLocalidad.Read(GetPais(), GetProvincia(), GetCiudadId(),
		// GetLocalidadId());
		// pLocalidad.setValue(oLocalidad.getLocalidad());
		// } else if (oPais.ifUsaTablaLocalidad() && pLocalidadId.isNull())
		// pLocalidad.setValue("");
//		this.validarComponentesExternos();
	}

	public void processInsert() throws Exception {
		if (!this.hasSecuencia()) {
			BizDomicilio max=new BizDomicilio();
			max.addFilter("persona", pPersona.getValue());
			pSecuencia.setValue(max.SelectMaxLong("secuencia")+1);
		}
		super.processInsert();
	}

	public boolean isParticular() throws Exception {
		return this.pIsParticular.getValue();
	}

	public boolean isComercial() throws Exception {
		return this.pIsComercial.getValue();
	}

	public boolean isLegal() throws Exception {
		return this.pIsLegal.getValue();
	}

	public boolean isListados() throws Exception {
		return this.pIsListados.getValue();
	}

	private void setearDescrPais() throws Exception {
		BizPais oPais=new BizPais();
		oPais.Read(this.pPais.getValue());
		pDescrPais.setValue(oPais.getDescrPaisLista());
	}

	public BizProvincia getObjProvincia() throws Exception {
		if (this.provincia!=null) return this.provincia;
		BizProvincia record=new BizProvincia();
		record.Read(this.pPais.getValue(), this.pProvincia.getValue());
		return (this.provincia=record);
	}

	public BizPais getObjPais() throws Exception {
		if (this.pais!=null) return this.pais;
		return (this.pais=BizPais.findPais(this.pPais.getValue()));
	}

	public BizCity getObjCity() throws Exception {
		if (this.city!=null) return this.city;
		BizCity record=new BizCity();
		record.Read(this.pCity.getValue());
		return (this.city=record);
	}
	
	public boolean hasObjCiudad() throws Exception {
		if (this.ciudad!=null) return true;
		BizCiudad record=new BizCiudad();
		record.dontThrowException(true);
		if (!record.Read(this.pPais.getValue(), this.pProvincia.getValue(), this.pCiudadId.getValue())) return false;
		this.ciudad=record;
		return true;
	}

	public BizCiudad getObjCiudad() throws Exception {
		if (this.ciudad!=null) return this.ciudad;
		BizCiudad record=new BizCiudad();
		record.Read(this.pPais.getValue(), this.pProvincia.getValue(), this.pCiudadId.getValue());
		return (this.ciudad=record);
	}

	private BizLocalidad localidad; //div2
	public BizLocalidad getObjLocalidad() throws Exception {
		if (this.localidad!=null) return this.localidad;
		BizLocalidad record=new BizLocalidad();
		record.Read(this.pPais.getValue(), this.pProvincia.getValue(), this.pCiudadId.getValue(), this.pLocalidadId.getValue());
		return (this.localidad=record);
	}
	
	public String getDescrProv() throws Exception {
		if (!this.hasProvincia()) return "";
		String d= this.getObjProvincia().GetDescrip();
		if (d.indexOf("CAPITAL")==0) return "CABA";
		return d;
	}
	public String getDescrProvAbrev() throws Exception {
		if (!this.hasProvincia()) return "";
		return this.getObjProvincia().GetDescripAbrev();
	}

	public String getDescrPais() throws Exception {
		if (!this.hasPais()) return "";
		return this.getObjPais().getDescrPaisLista();
	}

	public String getDescrCity() throws Exception {
		if (!this.hasCity()) return "";
		return this.getObjCity().getDescripcion();
	}
	public String getDescrCiudad() throws Exception {
		if (!this.hasCiudad()) return "";
		if (!this.hasObjCiudad()) return "Indefinido";
		return this.getObjCiudad().getDescripcion();
	}
	public String getDescrCiudadAbrev() throws Exception {
		if (!this.hasCiudad()) return "";
		if (!this.hasObjCiudad()) return "Indefinido";
		return this.getObjCiudad().getDescrAbrev().equals("")?this.getObjCiudad().getDescripcion():this.getObjCiudad().getDescrAbrev();
	}

	// private void setearDescrTipoDom() throws Exception {
	// pDescrTipoDom.setValue(getTiposDomicilios().findVirtualKey(pTipoDomicilio.getValue()).getDescrip());
	// }
	
	public String getDomicilioCompleto() throws Exception {
		return BizDomicilio.getDomicilioCompleto(pCalle.getValue(), pPiso.getValue(), pNumero.getValue(), pDepto.getValue(), pOtros.getValue(),pCodPostal.getValue(), pPais.getValue(), pProvincia.getValue(), pCiudad.getValue(), pLocalidad.getValue());
	}
	public String getDomicilioParcial() throws Exception {
		return BizDomicilio.getDomicilioCompleto(pCalle.getValue(), pPiso.getValue(), pNumero.getValue(), pDepto.getValue(), pOtros.getValue(),"", "", "", "", "");
	}
	public String getDomicilioParcialConCP() throws Exception {
		return BizDomicilio.getDomicilioCompleto(pCalle.getValue(), pPiso.getValue(), pNumero.getValue(), pDepto.getValue(), pOtros.getValue(),pCodPostal.getValue(), "", "", "", "");
	}
	public String getDomicilioLocalidad() throws Exception {
		String out = "";
		if (hasCity()) out = getDescrCity();
		if (hasCiudad()) out += (!out.equals("")?", ":"")+getDescrCiudad();
		if (hasLocalidad())out += (!out.equals("")?", ":"")+getLocalidad();
		if (pOtros.isNotNull()) out += (!out.equals("")?", ":"")+getOtros();
		return out;
	}
//	public static String getDomicilioCompleto(String calle, String piso, String nro, String dpto, String otros, String cod_postal,String pais, String prov, String ciudad, String loca) throws Exception {
////		if (pCalleNroPisoDpto.isNotNull()) {
////			return pCalleNroPisoDpto.getValue();
////		}
////		String sCalle=calle.trim();
//		String dom = "";
//		if (!calle.equals(""))
//			dom += (!dom.equals("") ? " " : "") + calle;
//		if (!nro.equals(""))
//			dom += (!dom.equals("") ? " " : "") + "Nº " + nro;
//		if (!piso.equals(""))
//			dom += (!dom.equals("") ? " " : "") + piso + ((piso.indexOf("º") == -1 && JTools.isNumberPure(piso)) ? "º" : "");
//		if (!dpto.equals(""))
//			dom += (!dom.equals("") ? " " : "") + "\"" + dpto + "\"";
//		if (!otros.equals(""))
//			dom += (!dom.equals("") ? " " : "") + " " + otros;
//		if (!cod_postal.equals(""))
//			dom += (!dom.equals("") ? " " : "") + " (" + cod_postal + ")";
//		if (ciudad!=null && !ciudad.equals(""))
//			dom+=" "+ciudad;
//		if (loca!=null && !loca.equals(""))
//			dom+=" "+loca;
//		
//		String sprov = "" ;
//		if (pais!="" && prov != "")
//			sprov = BizPais.findPais(pais).findProvincia(prov).GetDescrip();
//		String spais = "" ;
//		if (pais != "")
//			spais = BizPais.findPais(pais).GetDescrip();
//		if (!sprov.equals("") && (sprov.toLowerCase().indexOf("federal") == -1&&sprov.toLowerCase().indexOf("autonoma") == -1))
//			dom += (!dom.equals("") ? " provincia de " : "provincia de ") + sprov;
//		if (!spais.equals("") && !spais.toLowerCase().startsWith("arg"))
//			dom += (!dom.equals("") ? " " : "") + spais;
//
//		
//		return dom;
//	}
	public static String getDomicilioCompleto(String calle, String piso, String nro, String dpto, String otros, String cpostal,String pais, String prov, String ciudad, String loca) throws Exception {
		String d="";
		if (!piso.equals("") && !dpto.equals(""))
			d+=piso+"/"+dpto;
		else
			d+=piso+dpto;
		String dom = calle.trim()+" "+nro.trim()+" "+d.trim();
		if (ciudad!=null && !ciudad.equals(""))
			dom+=" - "+ciudad;
		if (loca!=null && !loca.equals(""))
			dom+=" - "+loca;
		if (prov!=null && !prov.equals(""))
			dom+=" - "+BizPais.findPais(pais).findProvincia(prov).GetDescrip();
		if (cpostal!=null && !cpostal.equals(""))
			dom+=" ("+cpostal+")";
		return dom;
	}
	
	public boolean hasPiso() throws Exception {
		return !this.getPiso().isEmpty();
	}
	
	public boolean hasDepto() throws Exception {
		return !this.getDepto().isEmpty();
	}
	
	public String getDomicilioSuperCompleto() throws Exception {
		String dom = this.getCalle().trim()+" "+this.getNumero().trim();
		if (this.hasPiso())
			dom+=" " + this.getPiso().trim();
		if (this.hasDepto())
			dom+="/"+this.getDepto().trim();
		if (this.hasCity())
			dom+=" - "+this.getDescrCity();
		if (this.hasAnyLocalidad())
			dom+=" - "+this.getDescrLocalidad();
		if (this.hasAnyCiudad())
			dom+=" - "+this.getDescrCiudad();
		if (this.hasProvincia())
			dom+=" - "+BizPais.findPais(this.getPais()).findProvincia(this.getProvincia()).GetDescrip();
		if (this.hasPais())
			dom+=" - "+BizPais.findPais(this.getPais()).GetDescrip();
		if (this.hasCodPostal())
			dom+=" ("+this.getCodPostal()+")";

		return dom;
	}


	public String getCalleNroPisoDpto() throws Exception {
		return buildDomicilioSinCiudad(pCalle.getValue(), pNumero.getValue(), pPiso.getValue(), pDepto.getValue(), pBis.getValue(), pOtros.getValue());
	}

	public String getCiudProvPais() throws Exception {
		return buildLocalidad(pDescrPais.getValue(), pDescrProv.getValue(), pDescrCiudad.getValue(), pLocalidad.getValue());
	}
	public String getDomicilio() throws Exception {
		return BizDomicilio.buildDomicilioCompleto(pCalle.getValue(), pNumero.getValue(), pPiso.getValue(),
				pDepto.getValue(), pBis.getValue(), pOtros.getValue(), pCodPostal.getValue(), 
				pDescrPais.getValue(), pDescrProv.getValue(), pDescrCiudad.getValue(), pLocalidad.getValue());
	}

	static public String buildLocalidad(String pais, String prov, String ciudad, String ciudadLibre) throws Exception {
		String dom = "";
		if (!pais.equals("") && !pais.toLowerCase().startsWith("arg"))
			dom += (!dom.equals("") ? ", " : "") + pais;
		if (!prov.equals("") && ((prov.toLowerCase().indexOf("federal") == -1 && !prov.toLowerCase().equals("caba")) || (ciudad.equals("")&&ciudadLibre.equals(""))))
			dom += (!dom.equals("") ? ", " : "") + prov;
		if (!ciudad.equals(""))
			dom += (!dom.equals("") ? ", " : "") + ciudad;
		if (!ciudadLibre.equals(""))
			dom += (!dom.equals("") ? " " : "") + ciudadLibre;
		return dom;
	}

	static public String buildDomicilioCompleto(String calle, String nro1, String piso, String nro2, boolean bis, String otros, String cod_postal, String pais, String prov, String ciudad, String ciudadLibre) throws Exception {
		String dom = "";
		if (!calle.equals(""))
			dom += (!dom.equals("") ? " " : "") + calle;
		if (bis)
			dom += (!dom.equals("") ? " " : "") + " bis";
		if (!nro1.equals("") && !nro1.equals("0"))
			dom += (!dom.equals("") ? " " : "") + "N:" + nro1;
		if (!piso.equals(""))
			dom += (!dom.equals("") ? " " : "") + piso + ((piso.indexOf("º") == -1 && JTools.isNumberPure(piso)) ? "º" : "");
		if (!nro2.equals(""))
			dom += (!dom.equals("") ? " " : "") + "\"" + nro2 + "\"";
		if (!otros.equals(""))
			dom += (!dom.equals("") ? " " : "") + " " + otros;
		if (!cod_postal.equals(""))
			dom += (!dom.equals("") ? " " : "") + " (" + cod_postal + ")";
		if (!ciudad.equals(""))
			dom += (!dom.equals("") ? " " : "") + ciudad;
		if (!ciudadLibre.equals(""))
			dom += (!dom.equals("") ? " " : "") + ciudadLibre;
		if (!prov.equals("") && (prov.toLowerCase().indexOf("federal") == -1&&prov.toLowerCase().indexOf("autonoma") == -1))
			dom += (!dom.equals("") ? " provincia de " : "provincia de ") + prov;
		if (!pais.equals("") && !pais.toLowerCase().startsWith("arg"))
			dom += (!dom.equals("") ? " " : "") + pais;
		return dom;
	}

	static public String buildDomicilioSinCiudad(String calle, String nro1, String piso, String nro2, boolean bis, String otros) throws Exception {
		String dom = "";
		if (!calle.equals(""))
			dom += (!dom.equals("") ? " " : "") + calle;
		if (bis)
			dom += (!dom.equals("") ? " " : "") + " bis";
		if (!nro1.equals("") && !nro1.equals("0"))
			dom += (!dom.equals("") ? " " : "") + "Nº " + nro1;
		if (!piso.equals(""))
			dom += (!dom.equals("") ? " " : "") + piso + ((piso.indexOf("º") == -1 && JTools.isNumberPure(piso)) ? "º" : "");
		if (!nro2.equals(""))
			dom += (!dom.equals("") ? " " : "") + "\"" + nro2 + "\"";
		if (!otros.equals(""))
			dom += (!dom.equals("") ? " " : "") + " " + otros;
		return dom;
	}

	static public String buildDomicilioCompletoMapa(String calle, String nro1, String letra, String nro2, boolean bis, String pais, String prov, String ciudad) throws Exception {
		String dom = "";
		String numero = JTools.getFirstNumbersEmbedded(nro1);

		if (!calle.equals(""))
			dom += (!dom.equals("") ? " " : "") + calle;
		if (bis)
			dom += (!dom.equals("") ? " " : "") + " bis";
		if (!letra.equals(""))
			dom += (!dom.equals("") ? " " : "") + "(" + letra + ")";
		if (!numero.equals(""))
			dom += (!dom.equals("") ? " " : "") + " " + numero;
		if (!ciudad.equals(""))
			dom += (!dom.equals("") ? "," : "") + ciudad;
		if (!prov.equals("") )
			dom += (!dom.equals("") ? "," : "") + prov;
		if (!pais.equals(""))
			dom += (!dom.equals("") ? "," : "") + pais;
		return JTools.replace(dom, "\"", "");
	}

	// private void setearDescrCiudad() throws Exception {
	// BizCiudad oCiudad = new BizCiudad();
	// oCiudad.getDescripcion();
	// }

	public synchronized static JRecords<BizVirtual> getTiposDomicilios() throws Exception {
		if (oTiposDomicilios!=null) return oTiposDomicilios;
		oTiposDomicilios=JRecords.createVirtualBDs();
		oTiposDomicilios.addItem(JRecord.virtualBD(TIPO_PARTICULAR, "Particular", 1));
		oTiposDomicilios.addItem(JRecord.virtualBD(TIPO_COMERCIAL, "Comercial", 1));
		oTiposDomicilios.addItem(JRecord.virtualBD(TIPO_LEGAL, "Legal", 1));
		return oTiposDomicilios;
	}

	public BizPersona obtenerPersona() throws Exception {
		BizPersona oPersona=new BizPersona();
		oPersona.Read(pPersona.getValue());
		return oPersona;
	}

	public JRecord ObtenerCliente() throws Exception {
		JRecord oCliente=BizCompInstalados.BizCliente();
		oCliente.addFilter("id_persona", pPersona);
		oCliente.dontThrowException(true);
		if (!oCliente.Read()) return null;
		return oCliente;
	}

	public void validarComponentesExternos() throws Exception {
		if (!BizCompInstalados.ifInstallRetailClientes()) return;
		JRecord oCliente=ObtenerCliente();
		if (oCliente==null) return;
		// Definicon de tipos de parametros del metodo
		Class<?>[] paramClass=new Class[1];
		paramClass[0]=BizDomicilio.class;
		// Definicon de valores de parametros del metodo
		Object[] paramArgs=new Object[1];
		paramArgs[0]=this;
		// Obtiene el metodo validarDomicilioParticular de clase JBcliente
		Method oMethod=oCliente.getClass().getMethod("validarDomicilioLegal", paramClass);
		// Ejecuta el Metodo
		oMethod.invoke(oCliente, paramArgs);
	}

	public void setearIdLocalidad() throws Exception {
		BizPais oPais=new BizPais();
		oPais.Read(this.pPais.getValue());
		BizLocalidad oLocalidad=new BizLocalidad();
		oLocalidad.addFilter("pais", getPais());
		oLocalidad.addFilter("provincia", getProvincia());
		oLocalidad.addFilter("ciudad_id", GetCiudadId());
		oLocalidad.addFilter("localidad", getLocalidad());
		oLocalidad.dontThrowException(true);
		if (oLocalidad.Read()) {
			SetLocalidadId(oLocalidad.getLocalidadId());
		} else {
			JExcepcion.SendError("Localidad no encontrada en tabla maestro");
		}
	}

	public void setearIdCiudad() throws Exception {
		BizPais oPais=new BizPais();
		oPais.Read(this.pPais.getValue());
		BizCiudad oCiudad=new BizCiudad();
		oCiudad.addFilter("pais", getPais());
		oCiudad.addFilter("provincia", getProvincia());
		oCiudad.addFilter("ciudad", getCiudad());
		oCiudad.dontThrowException(true);
		if (oCiudad.Read()) {
			SetCiudadId(oCiudad.getCiudad());
		} else {
			JExcepcion.SendError("Ciudad no encontrada en tabla maestro");
		}
	}

	public void setearIdProvincia() throws Exception {
		BizPais oPais=new BizPais();
		oPais.Read(this.pPais.getValue());
		BizProvincia oProvincia=new BizProvincia();
		oProvincia.addFilter("pais", getPais());
		oProvincia.addFilter("provincia", getProvincia());
		oProvincia.dontThrowException(true);
		if (oProvincia.Read()) {
			SetProvincia(oProvincia.GetProvincia());
		} else {
			JExcepcion.SendError("Provincia no encontrada en tabla maestro");
		}
	}

	public boolean ifPaisTablaCiudad() throws Exception {
		BizPais oPais=new BizPais();
		oPais.Read(pPais.getValue());
		return oPais.ifUsaTablaCiudad();
	}

	public boolean ifPaisTablaLocalidad() throws Exception {
		BizPais oPais=new BizPais();
		oPais.Read(pPais.getValue());
		return oPais.ifUsaTablaLocalidad();
	}

	public static String joinProvincia(String zDomicilio, String zProvincia) {
		String sWhere="";
		sWhere+=" and "+zProvincia+".pais = "+zDomicilio+".pais ";
		sWhere+=" and "+zProvincia+".provincia = "+zDomicilio+".provincia ";
		return sWhere;
	}

//	public void processDelete() throws Exception {
//		if (this.wasInvokeBy(BizPersonaJuridica.class)) {
//			super.processDelete();
//			return;
//		} 
//		this.pIsLegal.setValue(false);
//		this.pIsParticular.setValue(false);
//		this.pIsComercial.setValue(false);
//		this.pIsListados.setValue(false);
//		if (!this.isViolateIntegrity())
//			this.checkFlags();
//		super.processDelete();
//	}

	private void uncheckOtherFlags() throws Exception {
		JRecords<BizDomicilio> doms=this.getObjPersona().getObjDomicilios();
		this.uncheckFlag(doms, "is_legal");
	}

	private void checkFlags() throws Exception {
		JRecords<BizDomicilio> doms=this.getObjPersona().getObjDomicilios();
		this.checkFlag(doms, "is_legal");
	}

	private void uncheckFlag(JRecords<BizDomicilio> doms, String campo) throws Exception {
		JBoolean myFlag=(JBoolean) this.getProp(campo);
		if (myFlag.isFalse()) return;
		JIterator<BizDomicilio> iter=doms.getStaticIterator();
		while (iter.hasMoreElements()) {
			BizDomicilio dom=iter.nextElement();
			if (this.equals(dom)) continue;
			JBoolean flag=(JBoolean) dom.getProp(campo);
			if (flag.isFalse()) continue;
			flag.setValue(false);
			dom.update();
		}
	}

	private void checkFlag(JRecords<BizDomicilio> doms, String campo) throws Exception {
		JBoolean myFlag=(JBoolean) this.getProp(campo);
		if (myFlag.isTrue()) return;
		JIterator<BizDomicilio> iter=doms.getStaticIterator();
		while (iter.hasMoreElements()) {
			BizDomicilio dom=iter.nextElement();
			if (this.equals(dom)) continue;
			JBoolean flag=(JBoolean) dom.getProp(campo);
			if (flag.isTrue()) return;
		}
		JExcepcion.SendError("La persona no puede quedar sin : ^"+doms.getRecordRef().getFixedProp(campo).GetDescripcion());
	}

	public boolean equals(BizDomicilio dom) throws Exception {
		if (this.GetPersona()!=dom.GetPersona()) return false;
		if (this.getSecuencia()!=dom.getSecuencia()) return false;
		return true;
	}

	public void setObjPersona(BizPersona value) {
		this.persona=value;
	}

	public BizPersona getObjPersona() throws Exception {
		if (this.persona!=null) return this.persona;
		BizPersona record=new BizPersona();
		record.Read(this.GetPersona());
		return (this.persona=record);
	}
	
	public String getDescrLocalidad() throws Exception {
		if (this.hasLocalidadId())
			return this.getObjLocalidad().getLocalidad();
		return this.getLocalidad();
	}
	public void attachRelationMap(JRelations rels) throws Exception {
		rels.setSourceWinsClass(GuiDomicilio.class.getCanonicalName());
		rels.hideField("secuencia*");
		rels.hideField("persona*");
		rels.hideField("calle_letra*");
		rels.hideField("calle_letra*");
		rels.hideField("*id");
		rels.hideField("localidad");
		rels.hideField("ciudad");
		rels.hideField("city");
		rels.hideField("otros");
		rels.hideField("pais");
		rels.hideField("geo_position");
	}
	public void execProcessMain() throws Exception {
		JExec exec = new JExec(null,null) {
			public void Do() throws Exception {
				processMain();
			}
		};
		exec.execute();
	}	
	public void processMain() throws Exception {
		BizPersona per = new BizPersona();
		per.Read(this.GetPersona());
		per.setSecDomicilio(this.getSecuencia());
		per.update();
	}
	
}
