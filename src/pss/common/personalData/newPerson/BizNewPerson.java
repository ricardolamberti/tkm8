package  pss.common.personalData.newPerson;

import java.util.Date;

import pss.common.personalData.BizDomicilio;
import pss.common.personalData.BizPersona;
import pss.common.personalData.BizTelefono;
import pss.common.regions.cities.BizCity;
import pss.common.regions.divitions.BizPais;
import pss.common.security.BizUsuario;
import pss.core.connectivity.messageManager.common.core.JMMRecord;
import pss.core.services.fields.JDate;
import pss.core.services.fields.JGeoPosition;
import pss.core.services.fields.JInteger;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.tools.GeoPosition;
import pss.core.tools.JDateTools;
import pss.core.tools.JTools;

public  class BizNewPerson extends JMMRecord {

	// -------------------------------------------------------------------------//
	// Propiedades de la Clase
	// -------------------------------------------------------------------------//
	JLong pIdPersona=new JLong();
	JLong pIdPersonaMaster=new JLong();
	JString pCompany=new JString();
//	JString pIdSistemaExterno=new JString();
	JString pTipoPersona=new JString();
	JString pNombre=new JString();
	JString pApellido=new JString();
	JString pIdentAdicional=new JString();
	JString pNacionalidad=new JString();
	JString pLocalidad=new JString();
	JString pPais=new JString();
	JString pProvincia=new JString();
	JString pTipoDoc=new JString();
	JString pNroDoc=new JString();
	JString pTipoCui=new JString();
	JString pNroCui=new JString();
	JString pSexo=new JString();
	JString pProfesion=new JString();
	JString pTitulo=new JString();
	JString pMatricula=new JString();
	JString pConyuge=new JString();
	JInteger pNupcias=new JInteger();
	JString pPasaporte=new JString();
	JDate pVtoPasaporte=new JDate();
	JString pCiudadId=new JString();
	JString pLocalidadId=new JString();
	JLong pCity=new JLong();
	JString pComentarios=new JString();
	JDate pFechaNac=new JDate();
	JString pEMail=new JString();
	JString pWeb=new JString();
	JString pObservaciones=new JString();
	JLong pDomSecuencia=new JLong();
	JString pCalle=new JString();
	JString pCalleLetra=new JString();
	JString pNumero=new JString();
	JString pPiso=new JString();
	JString pDepto=new JString();
	JString pCodPostal=new JString();
	JString pOtros=new JString();
	JString pCiudad=new JString();
	JString pContacto=new JString();
	JString pTelComercial=new JString();
	JString pTelParticular=new JString();
	JString pTelCelular=new JString();
	JString pTelFax=new JString();
	JGeoPosition pGeoPosition=new JGeoPosition();
	JString pEstadoCivil=new JString();


	
//	private BizPais oPaisDomicilio;
//	private BizPersona oPersona;
//	private BizPersona oPersonaOld;
//	private BizDomicilio oDomicilio;
//	private BizDomicilio oDomicilioOld;
//	private BizTelefono telComercial;
//	private BizTelefono telComercialOld;
//	private BizTelefono telParticular;
//	private BizTelefono telParticularOld;
//	private BizTelefono telCelular;
//	private BizTelefono telCelularOld;
//	private BizTelefono telFax;
//	private BizTelefono telFaxOld;
	
	JString pNombreCompleto=new JString() {
		public void preset() throws Exception {
			pNombreCompleto.setValue(getNombreCompleto());
		};
	};
//	JString pDomicilio=new JString() {
//		public void preset() throws Exception {
//			pDomicilio.setValue(getObjAddress().getDomicilioCompleto());
//		};
//	};
//	protected JString pCalleNumeroPisoDpto = new JString() {
//		public void preset() throws Exception {
//			pCalleNumeroPisoDpto.setValue(getObjAddress().getCalleNroPisoDpto());
//		};
//	};
//	protected JString pPaisCiudProv = new JString() {
//		public void preset() throws Exception {
//			pPaisCiudProv.setValue(getObjAddress().getCiudProvPais());
//		};
//	};

	public String getNombreCompleto() throws Exception {
		if (this.isPersonaFisica()) {
			if (this.pNombre.isEmpty()) 
				return pApellido.getValue();
			return pApellido.getValue()+", "+JTools.capitalizeAll(pNombre.getValue());
		}
		if (this.isPersonaJuridica())
			return pApellido.getValue();
		return pApellido.getValue();
	}

//	public String getApellido() throws Exception {
//		return pApellido.getValue();
//	}

	public void setFechaInicio(Date zVal) throws Exception {
		this.pFechaNac.setValue(zVal);
	}
	
	public void setTipoPersona(String zVal) throws Exception {
		this.pTipoPersona.setValue(zVal);
	}

	public void setCompany(String value) {
		pCompany.setValue(value);
	}
	
	public void setNroCui(String value) {
		pNroCui.setValue(value);
	}

	public String getNroCui() throws Exception {
		return pNroCui.getValue();
	}

	public void setNombre(String value) {
		pNombre.setValue(value);
	}
	public void setNupcias(int value) {
		pNupcias.setValue(value);
	}
	public int getNupcias() throws Exception {
		return pNupcias.getValue();
	}
	public void setApellido(String value) {
		pApellido.setValue(value);
	}
	
	public void setMatricula(String value) {
		pMatricula.setValue(value);
	}

	public long getIdPersona() throws Exception  { return pIdPersona.getValue(); }
	public void setIdPersona(long p) throws Exception  { pIdPersona.setValue(p); }
	public long getIdPersonaMaster() throws Exception  { return pIdPersonaMaster.getValue(); }
	public String getApellido() throws Exception  { return pApellido.getValue(); }
	public String getNombre() throws Exception  { return pNombre.getValue(); }
	public Date getFechaInicio() throws Exception  { return pFechaNac.getValue(); }

	public void setPais(String value) {
		pPais.setValue(value);
	}
	public void setIdentAdicional(String value) {
		pIdentAdicional.setValue(value);
	}
	public String getIdentAdicional() throws Exception {
		return pIdentAdicional.getValue();
	}
//	public void setObjPerson(BizPersona value) {
//		this.oPersona=value;
//	}
//	public void setObjPersonOld(BizPersona value) {
//		this.oPersonaOld=value;
//	}

//	public void setObjAdress(BizDomicilio value) {
//		this.oDomicilio=value;
//	}
//	public void setObjAdressOld(BizDomicilio value) {
//		this.oDomicilioOld=value;
//	}
//	public void setObjTelComercial(BizTelefono value) {
//		this.telComercial=value;
//	}
//	public void setObjTelComercialOld(BizTelefono value) {
//		this.telComercialOld=value;
//	}
//	public void setObjTelParticular(BizTelefono value) {
//		this.telParticular=value;
//	}
//	public void setObjTelParticularOld(BizTelefono value) {
//		this.telParticularOld=value;
//	}
//	public void setObjTelCelular(BizTelefono value) {
//		this.telCelular=value;
//	}
//	public void setObjTelCelularOld(BizTelefono value) {
//		this.telCelularOld=value;
//	}
//	public void setObjTelFax(BizTelefono value) {
//		this.telFax=value;
//	}
//	public void setObjTelFaxOld(BizTelefono value) {
//		this.telFaxOld=value;
//	}
	
	
	public void setWeb(String web) throws Exception {
		this.pWeb.setValue(web);
	}
	
	public String getWeb() throws Exception {
		return this.pWeb.getValue();
	}
	public void setConyuge(String web) throws Exception {
		this.pConyuge.setValue(web);
	}
	
	public String getConyuge() throws Exception {
		return this.pConyuge.getValue();
	}
	
	public void setObservacion(String value) throws Exception  {
		this.pObservaciones.setValue(value);
	}

	public String getObservacion() throws Exception {
		return this.pObservaciones.getValue();
	}
	// public void setObjPhone(BizTelefono value) {
	// this.oTelefono=value;
	// }
	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public BizNewPerson() throws Exception {
	}

	@Override
	public void createProperties() throws Exception {
		addItem("id_persona", pIdPersona);
		addItem("tipo_persona", pTipoPersona);
		addItem("nombre", pNombre);
		addItem("apellido", pApellido);
		addItem("ident_adicional", pIdentAdicional);
		addItem("nacionalidad", pNacionalidad);
		addItem("tipo_doc", pTipoDoc);
		addItem("nro_doc", pNroDoc);
		addItem("tipo_cui", pTipoCui);
		addItem("nro_cui", pNroCui);
		addItem("fecha_nac", pFechaNac);
		addItem("sexo", pSexo);
		addItem("profesion", pProfesion);
		addItem("titulo", pTitulo);
		addItem("matricula", pMatricula);
		addItem("conyuge", pConyuge);
		addItem("nupcias", pNupcias);
		addItem("company", pCompany);
		addItem("pais", pPais);
		addItem("provincia", pProvincia);
		addItem("ciudad", pCiudad);
		addItem("localidad", pLocalidad);
		addItem("e_mail", pEMail);
		addItem("web", pWeb);
		addItem("observaciones", pObservaciones);
		addItem("dom_secuencia", pDomSecuencia);
		addItem("calle", pCalle);
		addItem("calle_letra", pCalleLetra);
		addItem("numero", pNumero);
		addItem("piso", pPiso);
		addItem("depto", pDepto);
		addItem("city", pCity);
		addItem("cod_postal", pCodPostal);
		addItem("otros", pOtros);
		addItem("ciudad_id", pCiudadId);
		addItem("localidad_id", pLocalidadId);
		addItem("comentarios", pComentarios);
//		addItem("id_sistema_externo", pIdSistemaExterno);
		addItem("pasaporte", pPasaporte);
		addItem("vto_pasaporte", pVtoPasaporte);
		addItem("contacto", pContacto);
		addItem("tel_comercial", pTelComercial);
		addItem("tel_particular", pTelParticular);
		addItem("tel_celular", pTelCelular);
		addItem("tel_fax", pTelFax);
		addItem("geo_position", pGeoPosition);
		addItem("estado_civil", pEstadoCivil);
		
		addItem("description", pNombreCompleto);
//		addItem("domicilio", pDomicilio);
//		addItem("calle_nro_piso_dpto", pCalleNumeroPisoDpto);
//		addItem("pais_ciud_prov", pPaisCiudProv);
//		
	}

	public String getFieldApellidoDescription() throws Exception {
		return "Apellido";
	}

	@Override
	public void createFixedProperties() throws Exception {
		addFixedItem(FIELD, "id_persona", "Id Persona", true, true, 50);
		addFixedItem(FIELD, "company", "Empresa", true, true, 15);
		addFixedItem(FIELD, "tipo_persona", "Tipo Persona", true, true, 2);
		addFixedItem(FIELD, "nombre", "Nombre", true, true, 250);
		addFixedItem(FIELD, "apellido", getFieldApellidoDescription(), true, true, 200);
		addFixedItem(FIELD, "ident_adicional", "Ident. Adicional", true, true, 200);
		addFixedItem(FIELD, "nacionalidad", "Nacionalidad", true, false, 15);
		addFixedItem(FIELD, "tipo_doc", "Tipo Documento", true, false, 3);
		addFixedItem(FIELD, "nro_doc", "Nro Documento", true, false, 20);
		addFixedItem(FIELD, "tipo_cui", "Tipo Cui", true, false, 3);
		addFixedItem(FIELD, "nro_cui", "Nro Cui", true, false, 20);
		addFixedItem(FIELD, "fecha_nac", "Fecha Nacimiento", true, false, 10);
		addFixedItem(FIELD, "sexo", "Sexo", true, true, 1);
		addFixedItem(FIELD, "profesion", "Profesión", true, true, 100);
		addFixedItem(FIELD, "titulo", "Titulo", true, true, 10);
		addFixedItem(FIELD, "matricula", "Matricula", true, true, 20);
		addFixedItem(FIELD, "conyuge", "Conyuge", true, true, 100);
		addFixedItem(FIELD, "nupcias", "Nupcias", true, true, 2);
		addFixedItem(FIELD, "pais", "Pais Domicilio", true, true, 5);
		addFixedItem(FIELD, "provincia", "Provincia", true, false, 5);
		addFixedItem(FIELD, "ciudad", "Ciudad", true, false, 50);
		addFixedItem(FIELD, "localidad", "localidad", true, false, 50);
		addFixedItem(FIELD, "e_mail", "eMail", true, false, 500);
		addFixedItem(FIELD, "web", "Pag.Web", true, false, 150);
		addFixedItem(FIELD, "observaciones", "Observaciones", true, false, 1000);
		addFixedItem(FIELD, "dom_secuencia", "Dom Secuencia", true, false, 18);
		addFixedItem(FIELD, "calle", "Calle", true, false, 50);
		addFixedItem(FIELD, "calle_letra", "O.", true, false, 50);
		addFixedItem(FIELD, "numero", "Número", true, false, 20);
		addFixedItem(FIELD, "piso", "Piso", true, false, 30);
		addFixedItem(FIELD, "depto", "Depto", true, false, 5);
		addFixedItem(FIELD, "city", "Ciudad", true, false, 10);
		addFixedItem(FIELD, "cod_postal", "Cod_postal", true, false, 10);
		addFixedItem(FIELD, "otros", "Otros", true, false, 200);
		addFixedItem(FIELD, "ciudad_id", "Ciudad Id", true, false, 20);
		addFixedItem(FIELD, "localidad_id", "Localidad Id", true, false, 20);
		addFixedItem(FIELD, "comentarios", "Comentarios", true, false, 200);
//		addFixedItem(FIELD, "id_sistema_externo", "Id Sistema Externo", true, false, 20);
		addFixedItem(FIELD, "pasaporte", "Pasaporte", true, false, 20);
		addFixedItem(FIELD, "vto_pasaporte", "Vto. Pasaporte", true, false, 200);
		addFixedItem(FIELD, "contacto", "Contacto", true, false, 50);
		addFixedItem(FIELD, "tel_comercial", "TE Comercial", true, false, 25,0); // poner el format en el business //, this.getFormatTelComercial() );
		addFixedItem(FIELD, "tel_particular", "TE Particular", true, false, 25,0);//,"^(\\\\+?\\\\d{1,3}?([-]\\\\d{0,3})?)?\\\\(([0]?\\\\d{2,4})\\\\)(15[-])?\\\\d{2,4}[-]\\\\d\\\\d\\\\d\\\\d$[+54][-9](9999)[15-]9999-9999");
		addFixedItem(FIELD, "tel_celular", "Celular", true, false, 25,0);//, this.getFormatTelCelular());
		addFixedItem(FIELD, "tel_fax", "Fax", true, false, 25, 0);//,"^(\\\\+?\\\\d{1,3}?([-]\\\\d{0,3})?)?\\\\(([0]?\\\\d{2,4})\\\\)(15[-])?\\\\d{2,4}[-]\\\\d\\\\d\\\\d\\\\d$[+54][-9](9999)[15-]9999-9999");
		addFixedItem(FIELD, "geo_position", "GeoPosition", true, false, 50);
		addFixedItem(FIELD, "estado_civil", "Estado civil", true, false, 20);

		addFixedItem(VIRTUAL, "description", "Denominación", true, false, 300);
//		addFixedItem(VIRTUAL, "domicilio", "Domicilio", true, false, 300);
//		addFixedItem(VIRTUAL, "calle_nro_piso_dpto", "Calle Nro Piso Dpto", true, false, 500);
//		addFixedItem(VIRTUAL, "pais_ciud_prov", "ciud.pais prov", true, false, 500);

	}
	
	@Override
	public void processUpdate() throws Exception {
		this.processInsert();
	}
	
	public boolean hasIdPersona() throws Exception {
		return this.getIdPersona()!=0L;
	}

	@Override
	public void processInsert() throws Exception {
//		oPersona=null;
//		this.cleanUp();

		boolean upd = this.hasIdPersona();
		BizPersona persona = this.makePersona();
		if (upd) persona.processUpdate();
		else persona.processInsert();
		this.pIdPersona.setValue(persona.GetPersona());
			
		
		BizDomicilio address = this.makeDomicilio();
		if (address!=null) {
			if (address.hasSecuencia()) address.processDelete();
			address.processInsert();
			persona.setSecDomicilio(address.getSecuencia());
			persona.update();
		}
		
		persona.getTelefonos().processDeleteAll();
		this.pushTelelfono(this.makeTelParticular());
		this.pushTelelfono(this.makeTelComercial());
		this.pushTelelfono(this.makeTelCelular());
		this.pushTelelfono(this.makeTelFax());
		
//		this.oPersonaOld=oPersona;
	}
	

	private void pushTelelfono(BizTelefono tel) throws Exception {
		if (tel==null) return;
		tel.SetPersona(this.getIdPersona());
		if (tel.GetNumero().isEmpty()) return;
		tel.processUpdateOrInsert();
	}


//	protected void cleanUp() throws Exception {
//		this.oPersona=null;
//		this.oPaisDomicilio=null;
//		this.oDomicilio=null;
//		this.telParticular=null;
//		this.telComercial=null;
//		this.telCelular=null;
//		this.telFax=null;
//	}

//	public void cleanOld() throws Exception {
////		this.oPersonaOld=null;
//		this.oDomicilioOld=null;
//		this.telParticularOld=null;
//		this.telComercialOld=null;
//		this.telCelularOld=null;
//		this.telFaxOld=null;
//	}

//	private BizDomicilio getObjAddress() throws Exception {
//		if (this.oDomicilio!=null) return this.oDomicilio;
//		BizDomicilio record=(this.oDomicilioOld!=null)? this.oDomicilioOld : new BizDomicilio();
//		record.SetCalle(pCalle.getValue());
//		record.SetCalleLetra(pCalleLetra.getValue());
//		record.SetPiso(pPiso.getValue());
//		record.SetNumero(pNumero.getValue());
//		record.SetDpto(pDepto.getValue());
//		record.SetCodPostal(pCodPostal.getValue());
//		record.setOtros(pOtros.getValue());
//		record.SetCiudadId(pCiudadId.getValue());
//		record.SetCiudad(pCiudad.getValue());
//		record.SetLocalidadId(pLocalidadId.getValue());
//		record.SetLocalidad(pLocalidad.getValue());
//		record.setPosition(pGeoPosition.getValue());
//		record.SetCiudad(pCiudad.getValue());
//		record.SetLocalidad(pLocalidad.getValue());
//		record.setCity(pCity.getValue());
//		
//		BizPais dom = ObtenerPaisDomicilio();
//		if ( dom != null )  { 
//		  if (dom.ifUsaTablaCiudad()) record.SetCiudadId(pCiudadId.getValue());
//			if (dom.ifUsaTablaLocalidad()) record.SetLocalidadId(pLocalidadId.getValue());
//		}	
//		record.SetProvincia(pProvincia.getValue());
//		record.SetPais(pPais.getValue());
//		record.setIsLegal(true);
//		record.setIsComercial(true);
//		record.setIsParticular(true);
//		record.setIsListados(true);
//		return (this.oDomicilio=record);
//	}
	
//	public BizPersonaJuridica getObjPersonaJuridica() throws Exception {
//		return (BizPersonaJuridica) this.getObjPerson();
//	}
	
//	public BizPersona getObjPerson() throws Exception {
////		if (oPersona!=null) return this.oPersona;
////		BizPersona record=(oPersonaOld!=null) ? this.oPersonaOld : new BizPersona();
//		BizPersona record=new BizPersona();
//		record.setPersonaMaster(pIdPersonaMaster.getValue());
//		record.setTipoPersona(pTipoPersona.getValue());
//		record.SetNombre(pNombre.getValue());
//		record.SetApellido(pApellido.getValue());
//		record.setIdentAdicional(pIdentAdicional.getValue());
//		record.SetNacionalidad(pNacionalidad.getValue());
//		record.SetTipoDoc(pTipoDoc.getValue());
//		record.SetNroDoc(pNroDoc.getValue());
//		record.setTipoCui(pTipoCui.getValue());
//		record.setNroCui(pNroCui.getValue());
//		record.SetFechaNac(pFechaNac.getValue());
//		record.SetSexo(pSexo.getValue());
//		record.setProfesion(pProfesion.getValue());
//		record.setMatricula(pMatricula.getValue());
//		record.setTitulo(pTitulo.getValue());
//		record.setConyuge(pConyuge.getValue());
//		record.setNupcias(pNupcias.getValue());
//		record.SetContacto(pContacto.getValue());
//		record.SetEmail(pEMail.getValue());
//		record.SetWeb(pWeb.getValue());
//		record.SetObservaciones(pObservaciones.getValue());
//		record.SetPasaporte(pPasaporte.getValue());
//		record.setFechaVtoPasaporte(pVtoPasaporte.getValue());
//		record.SetEstadoCivil(pEstadoCivil.getValue());
//		return record;
////		return (this.oPersona=record);
//	}
	public void loadDefaultsPersona(String persona) throws Exception {
		BizPersona p = new BizPersona();
		p.Read(Long.valueOf(persona));
		this.loadDefaultsPersona(p);
	}
	
	public void loadDefaultsPersona(long persona) throws Exception {
		BizPersona p = new BizPersona();
		p.Read(persona);
		this.loadDefaultsPersonaFull(p);
	}
	
	public void loadDefaultsPersonaFull(BizPersona persona) throws Exception {
		this.loadDefaultsPersona(persona);
  	
//		this.setObjAdressOld(persona.getObjDomicilioMain());
  	this.loadDefaultsAddress(persona.getObjDomicilioMain());
  	
//  	this.setObjTelComercialOld(oPersonaOld.getObjTelComercial());
  	this.loadDefaultsTelComercial(persona.getObjTelComercial());
  	
//  	this.setObjTelParticularOld(oPersonaOld.getObjTelParticular());
		this.loadDefaultsTelParticular(persona.getObjTelComercial());
		
//  	this.setObjTelCelularOld(oPersonaOld.getObjTelCelular());
		this.loadDefaultsTelCelular(persona.getObjTelComercial());
		
//  	this.setObjTelFaxOld(oPersonaOld.getObjTelFax());
//		this.loadDefaultsTelFax();

//		this.setObjPersonaRegistroOld(oPersonaOld.getObjPersonaRegistro());
//		this.loadDefaultsPersonaRegistro();
		
//		if (this.isClone()) this.cleanOld();
	}
	
//	private boolean isClone() throws Exception {
//		if (this.pIdPersona.getValue()==0) return false;
//		if (this.pIdPersonaMaster.getValue()==0) return false;
//		return this.pIdPersona.getValue()==this.pIdPersonaMaster.getValue();
//	}
	
	public boolean isPersonaFisica() throws Exception {
		return this.pTipoPersona.getValue().equals(BizPersona.TIPO_FISICA);
	}
	
	public boolean isPersonaJuridica() throws Exception {
		return this.pTipoPersona.getValue().equals(BizPersona.TIPO_JURIDICA);
	}

	public void loadDefaultsPersona(BizPersona persona) throws Exception {
		if (persona==null) return;
		pIdPersona.setValue(persona.GetPersona());
		pTipoPersona.setValue(persona.getIdTipoPersona());
		pIdPersonaMaster.setValue(persona.getPersonaMaster());
		pNombre.setValue(persona.GetNombre());
		pApellido.setValue(persona.GetApellido());
		pIdentAdicional.setValue(persona.getIdentAdicional());
		pNacionalidad.setValue(persona.GetNacionalidad());
		pTipoDoc.setValue(persona.GetTipoDoc());
		pNroDoc.setValue(persona.GetNroDoc());
		pTipoCui.setValue(persona.getTipoCui());
		pNroCui.setValue(persona.getNroCui());
		pFechaNac.setValue(persona.getFechaNacimiento());
		pSexo.setValue(persona.GetSexo());
		pProfesion.setValue(persona.getProfecion());
		pTitulo.setValue(persona.getTitulo());
		pMatricula.setValue(persona.getMatricula());
		pConyuge.setValue(persona.getConyuge());
		pNupcias.setValue(persona.getNupcias());
		pContacto.setValue(persona.GetContacto());
		pEMail.setValue(persona.GetEmail());
		pPasaporte.setValue(persona.GetPasaporte());
		pObservaciones.setValue(persona.GetObservaciones());
		pVtoPasaporte.setValue(persona.GetFechaVtoPasaporte());
		pEstadoCivil.setValue(persona.GetEstadoCivil());
		pWeb.setValue(persona.GetWeb());
	}
	
	public void loadDefaultsAddress(BizDomicilio domicilio) throws Exception {
		if (domicilio==null) return;
		pDomSecuencia.setValue(domicilio.getSecuencia());
		pCalle.setValue(domicilio.getCalle());
		pCalleLetra.setValue(domicilio.getLetraCalle());
		pPiso.setValue(domicilio.getPiso());
		pNumero.setValue(domicilio.getNumero());
		pDepto.setValue(domicilio.getDepto());
		pCodPostal.setValue(domicilio.getCodPostal());
		pOtros.setValue(domicilio.getOtros());
		pCiudadId.setValue(domicilio.GetCiudadId());
		pCiudad.setValue(domicilio.getCiudad());
		pLocalidadId.setValue(domicilio.GetLocalidadId());
		pLocalidad.setValue(domicilio.getLocalidad());
		pGeoPosition.setValue(domicilio.getGeoPosition(null));
		pProvincia.setValue(domicilio.getProvincia());
		pPais.setValue(domicilio.getPais());
		pCity.setValue(domicilio.getCity());
		pComentarios.setValue(domicilio.getComentarios());
	}

	public String getDomicilioCompleto() throws Exception {
		return BizDomicilio.getDomicilioCompleto(pCalle.getValue(), pPiso.getValue(), pNumero.getValue(), pDepto.getValue(), pOtros.getValue(),pCodPostal.getValue(), pPais.getValue(), pProvincia.getValue(), pCiudad.getValue(), pLocalidad.getValue());
	}
	public String getDomicilioParcial() throws Exception {
		return BizDomicilio.getDomicilioCompleto(pCalle.getValue(), pPiso.getValue(), pNumero.getValue(), pDepto.getValue(), pOtros.getValue(), "", "","","", "");
	}
	public void loadDefaultsTelComercial(BizTelefono tel) throws Exception {
		if (tel==null) return;
		pTelComercial.setValue(tel.GetNumero());
	}
	public void loadDefaultsTelParticular(BizTelefono tel) throws Exception {
		if (tel==null) return;
		pTelParticular.setValue(tel.GetNumero());
	}
	public void loadDefaultsTelCelular(BizTelefono tel) throws Exception {
		if (tel==null) return;
		pTelCelular.setValue(tel.GetNumero());
	}
	public void loadDefaultsTelFax(BizTelefono tel) throws Exception {
		if (tel==null) return;
		pTelFax.setValue(tel.GetNumero());
	}
	
//	protected BizPersona getNewPersonClass() throws Exception {
//		return new BizPersonaFisica();
//	}

//	public BizPais ObtenerPaisDomicilio() throws Exception {
//		oPaisDomicilio=new BizPais();
//		oPaisDomicilio.dontThrowException(true);
//	  if ( oPaisDomicilio.Read(pPais.getValue()) )
//		  return oPaisDomicilio;
//	  else 
//	  	return null;
//	}

	public String getCompany() throws Exception {
		return pCompany.getValue();
	}

	public String getPais() throws Exception {
		return pPais.getValue();
	}

//	private BizTelefono getObjTelComercial() throws Exception {
//		if (this.telComercial!=null) return this.telComercial;
//		if (this.pTelComercial.isEmpty() && this.telComercialOld==null) return null;
//		BizTelefono record=(this.telComercialOld!=null)?this.telComercialOld:new BizTelefono();
//		record.SetTipoTel(BizTelefono.TIPO_COMERCIAL);
//		record.SetNumero(pTelComercial.getValue());
//		return (this.telComercial=record);
//	}
//
//	private BizTelefono getObjTelParticular() throws Exception {
//		if (this.telParticular!=null) return this.telParticular;
//		if (this.pTelParticular.isEmpty() && this.telParticularOld==null) return null;
//		BizTelefono record=(this.telParticularOld!=null)?this.telParticularOld:new BizTelefono();
//		record.SetTipoTel(BizTelefono.TIPO_PARTICULAR);
//		record.SetNumero(pTelParticular.getValue());
//		return (this.telParticular=record);
//	}
//
//	private BizTelefono getObjTelCelular() throws Exception {
//		if (this.telCelular!=null) return this.telCelular;
//		if (this.pTelCelular.isEmpty() && this.telCelularOld==null) return null;
//		BizTelefono record=(this.telCelularOld!=null)?this.telCelularOld:new BizTelefono();
//		record.SetTipoTel(BizTelefono.TIPO_CELULAR);
//		record.SetNumero(pTelCelular.getValue());
//		return (this.telCelular=record);
//	}
//
//	private BizTelefono getObjTelFax() throws Exception {
//		if (this.telFax!=null) return this.telFax;
//		if (this.pTelFax.isEmpty() && this.telFaxOld==null) return null;
//		BizTelefono record=(this.telFaxOld!=null)?this.telFaxOld:new BizTelefono();
//		record.SetTipoTel(BizTelefono.TIPO_FAX);
//		record.SetNumero(pTelFax.getValue());
//		return (this.telFax=record);
//	}


	/**
	 * @return the pIdSistemaExterno
	 */
//	public String getIdSistemaExterno() throws Exception {
//		return pIdSistemaExterno.getValue();
//	}

	/**
	 * @param idSistemaExterno the pIdSistemaExterno to set
	 */
//	public void setIdSistemaExterno(String idSistemaExterno) throws Exception {
//		pIdSistemaExterno.setValue( idSistemaExterno );
//	}

	/**
	 * @return the pNacionalidad
	 */
	public String getNacionalidad() throws Exception {
		return pNacionalidad.getValue();
	}

	/**
	 * @param nacionalidad the pNacionalidad to set
	 */
	public void setNacionalidad(String nacionalidad) throws Exception {
		pNacionalidad.setValue(nacionalidad);
	}

	/**
	 * @return the pLocalidad
	 */
	public String getLocalidad() throws Exception {
		return pLocalidad.getValue();
	}

	public String getComentarios() throws Exception {
		return pComentarios.getValue();
	}

	/**
	 * @param localidad the pLocalidad to set
	 */
	public void setLocalidad(String localidad) throws Exception {
		pLocalidad.setValue(localidad);
	}

	public void setComentarios(String localidad) throws Exception {
		pComentarios.setValue(localidad);
	}

	/**
	 * @return the pProvincia
	 */
	public String getProvincia() throws Exception {
		return pProvincia.getValue();
	}

	/**
	 * @param provincia the pProvincia to set
	 */
	public void setProvincia(String provincia) throws Exception {
		pProvincia.setValue(provincia);
	}

	/**
	 * @return the pTipoDoc
	 */
	public String getTipoDoc() throws Exception {
		return pTipoDoc.getValue();
	}
	public String getTipoCui() throws Exception {
		return pTipoCui.getValue();
	}

	/**
	 * @param tipoDoc the pTipoDoc to set
	 */
	public void setTipoDoc(String tipoDoc) throws Exception {
		pTipoDoc.setValue(tipoDoc);
	}
	public void setTipoCui(String tipoDoc) throws Exception {
		pTipoCui.setValue(tipoDoc);
	}

	/**
	 * @return the pNroDoc
	 */
	public String getNroDoc() throws Exception {
		return pNroDoc.getValue();
	}

	/**
	 * @param nroDoc the pNroDoc to set
	 */
	public void setNroDoc(String nroDoc) throws Exception {
		pNroDoc.setValue(nroDoc);
	}

	/**
	 * @return the pSexo
	 */
	public String getSexo() throws Exception {
		return pSexo.getValue();
	}

	/**
	 * @param sexo the pSexo to set
	 */
	public void setSexo(String sexo) throws Exception {
		pSexo.setValue(sexo);
	}



	/**
	 * @return the pPasaporte
	 */
	public String getPasaporte() throws Exception {
		return pPasaporte.getValue();
	}

	/**
	 * @param pasaporte the pPasaporte to set
	 */
	public void setPasaporte(String pasaporte) throws Exception {
		pPasaporte.setValue( pasaporte );
	}

	public void setIdPersonaMaster(long value) throws Exception {
		pIdPersonaMaster.setValue(value);
	}

	/**
	 * @return the pVtoPasaporte
	 */
	public Date getVtoPasaporte() throws Exception {
		return pVtoPasaporte.getValue();
	}

	/**
	 * @param vtoPasaporte the pVtoPasaporte to set
	 */
	public void setVtoPasaporte(Date vtoPasaporte) throws Exception {
		pVtoPasaporte.setValue(vtoPasaporte);
	}

	/**
	 * @return the pCiudadId
	 */
	public String getCiudadId() throws Exception {
		return pCiudadId.getValue();
	}

	public long getCity() throws Exception {
		return pCity.getValue();
	}

	/**
	 * @param ciudadId the pCiudadId to set
	 */
	public void setCiudadId(String ciudadId) throws Exception {
		pCiudadId.setValue( ciudadId );
	}

	/**
	 * @return the pLocalidadId
	 */
	public String getLocalidadId() throws Exception {
		return pLocalidadId.getValue();
	}

	/**
	 * @param localidadId the pLocalidadId to set
	 */
	public void setLocalidadId(String localidadId) throws Exception {
		pLocalidadId.setValue(localidadId);
	}

	/**
	 * @return the pEMail
	 */
	public String getEMail()  throws Exception {
		return pEMail.getValue();
	}

	/**
	 * @param mail the pEMail to set
	 */
	public void setEMail(String mail) throws Exception {
		pEMail.setValue(mail);
	}
	public String getCalleLetra()  throws Exception {
		return pCalleLetra.getValue();
	}

	/**
	 * @param calle the pCalle to set
	 */
	public void setCalleLetra(String calle) throws Exception {
		pCalleLetra.setValue( calle );
	}

	/**
	 * @return the pCalle
	 */
	public String getCalle()  throws Exception {
		return pCalle.getValue();
	}

	/**
	 * @param calle the pCalle to set
	 */
	public void setCalle(String calle) throws Exception {
		pCalle.setValue( calle );
	}

	
	/**
	 * @return the pNumero
	 */
	public String getNumero()  throws Exception {
		return pNumero.getValue();
	}

	/**
	 * @param numero the pNumero to set
	 */
	public void setNumero(String numero) throws Exception {
		pNumero.setValue( numero );
	}

	/**
	 * @return the pPiso
	 */
	public String getPiso()  throws Exception {
		return pPiso.getValue();
	}

	/**
	 * @param piso the pPiso to set
	 */
	public void setPiso(String piso) throws Exception {
		pPiso.setValue( piso );
	}

	/**
	 * @return the pDepto
	 */
	public String getDepto()  throws Exception {
		return pDepto.getValue();
	}

	/**
	 * @param depto the pDepto to set
	 */
	public void setDepto(String depto) throws Exception {
		pDepto.setValue( depto );
	}
	
	public String getOtros()  throws Exception {
		return pOtros.getValue();
	}

	/**
	 * @param depto the pOtros to set
	 */
	public void setOtros(String depto) throws Exception {
		pOtros.setValue( depto );
	}

	/**
	 * @return the pCodPostal
	 */
	public String getCodPostal()  throws Exception {
		return pCodPostal.getValue();
	}

	/**
	 * @param codPostal the pCodPostal to set
	 */
	public void setCodPostal(String codPostal) throws Exception {
		pCodPostal.setValue(codPostal);
	}

	/**
	 * @return the pCiudad
	 */
	public String getCiudad()  throws Exception {
		return pCiudad.getValue();
	}

	/**
	 * @param ciudad the pCiudad to set
	 */
	public void setCiudad(String ciudad) throws Exception {
		pCiudad.setValue( ciudad );
	}

	/**
	 * @return the pContacto
	 */
	public String getContacto()  throws Exception {
		return pContacto.getValue();
	}

	/**
	 * @param contacto the pContacto to set
	 */
	public void setContacto(String contacto) throws Exception {
		pContacto.setValue( contacto );
	}

	/**
	 * @return the pTelComercial
	 */
	public String getTelComercial()  throws Exception {
		return pTelComercial.getValue();
	}

	/**
	 * @param telComercial the pTelComercial to set
	 */
	public void setTelComercial(String telComercial) throws Exception {
		pTelComercial.setValue(telComercial);
	}

	/**
	 * @return the pTelParticular
	 */
	public String getTelParticular()  throws Exception {
		return pTelParticular.getValue();
	}

	/**
	 * @param telParticular the pTelParticular to set
	 */
	public void setTelParticular(String telParticular) throws Exception {
		pTelParticular.setValue(telParticular);
	}

	/**
	 * @return the pTelCelular
	 */
	public String getTelCelular()  throws Exception {
		return pTelCelular.getValue();
	}

	/**
	 * @param telCelular the pTelCelular to set
	 */
	public void setTelCelular(String telCelular) throws Exception {
		pTelCelular.setValue( telCelular );
	}

	/**
	 * @return the pTelFax
	 */
	public String getTelFax()  throws Exception {
		return pTelFax.getValue()	;
	}

	/**
	 * @param telFax the pTelFax to set
	 */
	public void setTelFax(String telFax) throws Exception {
		pTelFax.setValue( telFax );
	}

	/**
	 * @return the pGeoPosition
	 */
	public GeoPosition getGeoPosition()  throws Exception {
		return pGeoPosition.getValue();
	}

	/**
	 * @param geoPosition the pGeoPosition to set
	 */
	public void setGeoPosition(GeoPosition geoPosition) {
		pGeoPosition.setValue( geoPosition );
	}
	
	public String GetEstadoCivil() throws Exception {
		return pEstadoCivil.getValue();
	}
	
	public void SetEstadoCivil(String zVal) throws Exception {
		pEstadoCivil.setValue(zVal);
	}
	
//	public boolean hasOldPerson() {
//		return this.oPersonaOld!=null;
//	}
//
//	public BizPersona getOldPerson() {
//		return this.oPersonaOld;
//	}

	public String getTipoPersona() throws Exception {
		return this.pTipoPersona.getValue();
	}

	public String getFormatTelComercial() throws Exception {
		return "(^[0]$|^((\\\\+?\\\\d{1,3}?([-]\\\\d{0,3})?)?\\\\(([0]?\\\\d{2,4})\\\\)(15[-])?\\\\d{2,4}[-]\\\\d\\\\d\\\\d\\\\d)$)#[+54][-9](9999)[15-]9999-9999 (0 si no informa)";
	}

	public String getFormatTelCelular() throws Exception {
		return "(\\\\+?\\\\d{1,3}?([-]\\\\d{0,3})?)?\\\\(([0]?\\\\d{2,4})\\\\)(15[-])?\\\\d{2,4}[-]\\\\d\\\\d\\\\d\\\\d#[+54][-9](9999)[15-]9999-9999";
	}
	
	public String getDescrPais() throws Exception {
		if (this.pPais.isEmpty()) return "";
		return BizPais.getDescription(this.pPais.getValue());
	}
	
	public String getDescrProvincia() throws Exception {
		if (this.pProvincia.isEmpty()) return "";
		return BizPais.findPais(this.pPais.getValue()).findProvincia(this.pProvincia.getValue()).GetDescrip();
	}
	
	public String getDescrCity() throws Exception {
		if (this.pCity.getValue()==0L) return "";
		return BizCity.findCity(""+this.pCity.getValue()).getDescripcion();
	}
	public boolean hasFechaNac() throws Exception {
		return this.pFechaNac.isNotNull();
	}	
	public int getEdad() throws Exception {
		if (this.pFechaNac.isNull()) return 0;
		if (this.pFechaNac.getValue().equals(JDateTools.MinDate())) return 0;
		return JDateTools.getYearsBetween(this.getFechaInicio(), BizUsuario.getUsr().todayGMT());
	}

	private boolean queryMode=false;
	public void setQueryMode(boolean value) throws Exception {
		this.queryMode=value;
	}
	public boolean isQueryMode() throws Exception {
		return this.queryMode;
	}

	public BizPersona makePersonaFull() throws Exception {
		BizPersona per = this.makePersona();
		per.setObjAddressMain(this.makeDomicilio());
		per.setObjTelParticular(this.makeTelParticular());
		per.setObjTelComercial(this.makeTelComercial());
		per.setObjTelCelular(this.makeTelCelular());
		return per;
	}
	
	public BizPersona makePersona() throws Exception {
		BizPersona per=new BizPersona();
		per.setPersona(this.pIdPersona.getValue());
		per.setPersonaMaster(pIdPersonaMaster.getValue());
		per.setTipoPersona(pTipoPersona.getValue());
		per.SetNombre(pNombre.getValue());
		per.SetApellido(pApellido.getValue());
		per.setIdentAdicional(pIdentAdicional.getValue());
		per.SetNacionalidad(pNacionalidad.getValue());
		per.SetTipoDoc(pTipoDoc.getValue());
		per.SetNroDoc(pNroDoc.getValue());
		per.setTipoCui(pTipoCui.getValue());
		per.setNroCui(pNroCui.getValue());
		per.SetFechaNac(pFechaNac.getValue());
		per.SetSexo(pSexo.getValue());
		per.setProfesion(pProfesion.getValue());
		per.setMatricula(pMatricula.getValue());
		per.setTitulo(pTitulo.getValue());
		per.setConyuge(pConyuge.getValue());
		per.setNupcias(pNupcias.getValue());
		per.SetContacto(pContacto.getValue());
		per.SetEmail(pEMail.getValue());
		per.SetWeb(pWeb.getValue());
		per.SetObservaciones(pObservaciones.getValue());
		per.SetPasaporte(pPasaporte.getValue());
		per.setFechaVtoPasaporte(pVtoPasaporte.getValue());
		per.SetEstadoCivil(pEstadoCivil.getValue());
		per.validateConstraints();

		return per;
	}
	
	public BizDomicilio makeDomicilio() throws Exception {
		
		BizDomicilio dom = new BizDomicilio();
		dom.SetPersona(this.getIdPersona());
		dom.setSecuencia(pDomSecuencia.getValue());
		dom.SetCalle(pCalle.getValue());
		dom.SetCalleLetra(pCalleLetra.getValue());
		dom.SetPiso(pPiso.getValue());
		dom.SetNumero(pNumero.getValue());
		dom.SetDpto(pDepto.getValue());
		dom.SetCodPostal(pCodPostal.getValue());
		dom.setOtros(pOtros.getValue());
		dom.SetCiudadId(pCiudadId.getValue());
		dom.SetCiudad(pCiudad.getValue());
		dom.SetLocalidadId(pLocalidadId.getValue());
		dom.SetLocalidad(pLocalidad.getValue());
		dom.setPosition(pGeoPosition.getValue());
		dom.SetCiudad(pCiudad.getValue());
		dom.SetLocalidad(pLocalidad.getValue());
		dom.setCity(pCity.getValue());
		dom.setComentarios(pComentarios.getValue());
		
		BizPais pais = BizPais.findPais(this.getPais());
		if ( pais != null )  { 
		  if (pais.ifUsaTablaCiudad()) dom.SetCiudadId(pCiudadId.getValue());
			if (pais.ifUsaTablaLocalidad()) dom.SetLocalidadId(pLocalidadId.getValue());
		}	
		dom.SetProvincia(pProvincia.getValue());
		dom.SetPais(pPais.getValue());
		return dom;
	}
	
	public BizTelefono makeTelParticular() throws Exception {
		BizTelefono t = new BizTelefono();
		t.SetTipoTel(BizTelefono.TIPO_PARTICULAR);
		t.SetNumero(this.getTelParticular());
		return t;
	}

	public BizTelefono makeTelComercial() throws Exception {
		BizTelefono t = new BizTelefono();
		t.SetTipoTel(BizTelefono.TIPO_COMERCIAL);
		t.SetNumero(this.getTelComercial());
		return t;
	}

	public BizTelefono makeTelCelular() throws Exception {
		BizTelefono t = new BizTelefono();
		t.SetTipoTel(BizTelefono.TIPO_CELULAR);
		t.SetNumero(this.getTelCelular());
		return t;
	}
	
	public BizTelefono makeTelFax() throws Exception {
		BizTelefono t = new BizTelefono();
		t.SetTipoTel(BizTelefono.TIPO_FAX);
		t.SetNumero(this.getTelFax());
		return t;
	}

}
