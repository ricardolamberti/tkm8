package pss.common.personalData.query;

import pss.common.personalData.BizDomicilio;
import pss.common.personalData.BizPersona;
import pss.common.personalData.BizTelefono;
import pss.common.personalData.newPerson.BizNewPerson;
import pss.common.regions.divitions.BizPais;
import pss.common.regions.divitions.BizProvincia;
import pss.core.services.fields.JDate;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;

public class BizQueryPersona extends JRecord {

	private JLong pPerIdPersona=new JLong();
	private JString pPerDescription=new JString();
	private JString pPerNombre=new JString();
	private JString pPerApellido=new JString();
	private JString pPerTipoPersona=new JString();
	private JString pPerTipoDoc=new JString();
	private JString pPerNroDoc=new JString();
	private JString pPerIdentAdicional=new JString();
	private JString pPerPasaporte=new JString();
	private JDate pPerVtoPasaporte=new JDate();
	private JString pPerDescrDoc=new JString() {
		public void preset() throws Exception {
			setValue(pPerTipoDoc.getValue()+"-"+pPerNroDoc.getValue());
		}
	};
	private JString pPerSexo=new JString();
	private JString pPerNacionalidad=new JString();
	private JDate pPerFechaNacimiento=new JDate();
	private JString pPerEMail=new JString();
	private JString pPerContacto=new JString();
	private JString pPerProfesion=new JString();
	private JString pPerWeb=new JString();
	private JString pPerObservaciones=new JString();
	private JString pPerEstadoCivil=new JString();
	private JString pPerBusqueda=new JString();


	private JString pDescrProv=new JString() {
		public void preset() throws Exception {
			setValue(getDescrProv());
		}
	};

	private JString pTelParticular=new JString();
	private JString pTelComercial=new JString();
	private JString pTelCelular=new JString();


	private JString pDomicilioFull=new JString() {
		public void preset() throws Exception {
			setValue(getPerDomicilioFull());
		}
	};
	private JString pDomCalle=new JString();
	private JString pDomNumero=new JString();
	private JString pDomProvincia=new JString();
	private JString pDomCiudad=new JString();
	private JString pDomLocalidad=new JString();
	private JString pDomDepto=new JString();
	private JString pDomPiso=new JString();
	private JString pDomPais=new JString();
	private JString pDomCodPostal=new JString();
	private JString pDomOtros=new JString();
	private JLong pDomCity=new JLong();

	
	public String getPerMail() throws Exception {
		return pPerEMail.getValue();
	}
	public boolean hasPerMail() throws Exception {
		return pPerEMail.isNotNull();
	}

	public String getPerDescription() throws Exception {
		return pPerDescription.getValue();
	}

	public String getPerApellido() throws Exception {
		return pPerApellido.getValue();
	}

	public String getPerNombre() throws Exception {
		return pPerNombre.getValue();
	}

	public long getPerIdPersona() throws Exception {
		return pPerIdPersona.getValue();
	}

	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public BizQueryPersona() throws Exception {
	}

	
	@Override
	public void createProperties() throws Exception {
		addItem("per_id_persona", pPerIdPersona);
		addItem("per_description", pPerDescription);
		addItem("per_nombre", pPerNombre);
		addItem("per_apellido", pPerApellido);
		addItem("per_tipo_persona", pPerTipoPersona);
		addItem("per_tipo_doc", pPerTipoDoc);
		addItem("per_nro_doc", pPerNroDoc);
		addItem("per_pasaporte", pPerPasaporte);
		addItem("per_vto_pasaporte", pPerVtoPasaporte);	
		addItem("per_descr_doc", pPerDescrDoc);
		addItem("per_sexo", pPerSexo);
		addItem("per_estado_civil", pPerEstadoCivil);
		addItem("per_nacionalidad", pPerNacionalidad);
		addItem("per_fecha_nacimiento", pPerFechaNacimiento);
		addItem("per_ident_adicional", pPerIdentAdicional);
		addItem("per_email", pPerEMail);
		addItem("per_web", pPerWeb);
		addItem("per_contacto", pPerContacto);
		addItem("per_profesion", pPerProfesion);
		addItem("per_observaciones", pPerObservaciones);
		addItem("per_busqueda", pPerBusqueda);
		addItem("dom_full", pDomicilioFull);
		addItem("dom_calle", pDomCalle);
		addItem("dom_piso", pDomPiso);
		addItem("dom_depto", pDomDepto);
		addItem("dom_numero", pDomNumero);
		addItem("dom_pais", pDomPais);
		addItem("dom_provincia", pDomProvincia);
		addItem("dom_ciudad", pDomCiudad);
		addItem("dom_localidad", pDomLocalidad);
		addItem("dom_city", pDomCity);
		addItem("dom_otros", pDomOtros);
		addItem("dom_cod_postal", pDomCodPostal);
		addItem("tel_particular", pTelParticular);
		addItem("tel_comercial", pTelComercial);
		addItem("tel_celular", pTelCelular);

		addItem("descr_prov", pDescrProv);
	}

	@Override
	public void createFixedProperties() throws Exception {
		addFixedItem(FIELD, "per_id_persona", "Id Persona", false, false, 18);
		addFixedItem(FIELD, "per_description", "Descripción", false, false, 50);
		addFixedItem(FIELD, "per_nombre", "Nombre", false, false, 50);
		addFixedItem(FIELD, "per_apellido", "Apellido", false, false, 50);
		addFixedItem(FIELD, "per_tipo_persona", "Tipo Persona", false, false, 50);
		addFixedItem(FIELD, "per_tipo_doc", "Tipo Doc", false, false, 50);
		addFixedItem(VIRTUAL, "per_descr_doc", "Tipo Doc", false, false, 1);
		addFixedItem(FIELD, "per_nro_doc", "Nro Doc", false, false, 50);
		addFixedItem(FIELD, "per_pasaporte", "PAsaporte", false, false, 50);
		addFixedItem(FIELD, "per_vto_pasaporte", "Vto PAsaporte", false, false, 10);
		addFixedItem(FIELD, "per_ident_adicional", "Ident Adic", false, false, 50);
		addFixedItem(FIELD, "per_sexo", "Sexo", false, false, 1);
		addFixedItem(FIELD, "per_estado_civil", "Estado Civil", false, false, 1);
		addFixedItem(FIELD, "per_nacionalidad", "Nacionalidad", false, false, 10);
		addFixedItem(FIELD, "per_fecha_nacimiento", "Fecha Nacimiento", false, false, 10);
		addFixedItem(FIELD, "per_email", "eMail", false, false, 50);
		addFixedItem(FIELD, "per_web", "Web", false, false, 50);
		addFixedItem(FIELD, "per_contacto", "Contacto", false, false, 50);
		addFixedItem(FIELD, "per_profesion", "Profesión", false, false, 50);
		addFixedItem(FIELD, "per_observaciones", "Observaciones", false, false, 200);
		addFixedItem(FIELD, "per_busqueda", "Per Busqueda", false, false, 200);
		addFixedItem(FIELD, "dom_calle", "Calle", false, false, 50);
		addFixedItem(FIELD, "dom_piso", "Piso", false, false, 50);
		addFixedItem(FIELD, "dom_numero", "Número", false, false, 50);
		addFixedItem(FIELD, "dom_depto", "Depto", false, false, 50);
		addFixedItem(FIELD, "dom_pais", "Pais", false, false, 50);
		addFixedItem(FIELD, "dom_provincia", "Provincia", false, false, 50);
		addFixedItem(FIELD, "dom_ciudad", "Ciudad", false, false, 50);
		addFixedItem(FIELD, "dom_localidad", "Localidad", false, false, 50);
		addFixedItem(FIELD, "dom_city", "City", false, false, 50);
		addFixedItem(FIELD, "dom_cod_postal", "Cod Postal", false, false, 50);
		addFixedItem(FIELD, "dom_otros", "OTros", false, false, 50);
		
		addFixedItem(FIELD, "tel_particular", "Tel Particular", false, false, 20);
		addFixedItem(FIELD, "tel_comercial", "Tel Comercial", false, false, 20);
		addFixedItem(FIELD, "tel_celular", "Tel Celular", false, false, 20);
		addFixedItem(VIRTUAL, "dom_full", "Domicilio", true, false, 100);
		addFixedItem(VIRTUAL, "descr_prov", "Provincia", false, true, 100);
	}

	
	protected String getJoinPersona() throws Exception {
		return " join per_persona p on p.persona = c.id_persona ";
	}
	protected String getJoinDomicilio() throws Exception {
		 return " left outer join per_domicilio d on d.persona = c.id_persona and d.secuencia=p.sec_domicilio ";
	}
	protected String getJoinTel1() throws Exception {
		return  " left outer join per_telefono t1 on t1.persona = c.id_persona and t1.tipo_tel='"+BizTelefono.TIPO_PARTICULAR+"' ";
	}
	protected String getJoinTel2() throws Exception {
		return  " left outer join per_telefono t2 on t2.persona = c.id_persona and t2.tipo_tel='"+BizTelefono.TIPO_COMERCIAL+"' ";
	}
	protected String getJoinTel3() throws Exception {
		return  " left outer join per_telefono t3 on t3.persona = c.id_persona and t3.tipo_tel='"+BizTelefono.TIPO_CELULAR+"' ";
	}
		
	protected String getFieldsPer() throws Exception {
		return ", p.persona per_id_persona, p.description per_description, p.nombre per_nombre, p.apellido per_apellido,"
				+ "p.tipo_persona per_tipo_persona, p.tipo_doc per_tipo_doc, p.nro_doc per_nro_doc,"
				+ "p.pasaporte per_pasaporte, p.vto_pasaporte per_vto_pasaporte, p.nacionalidad per_nacionalidad,"
				+ "p.ident_adicional per_ident_adicional, p.sexo per_sexo, p.fecha_nacimiento per_fecha_nacimiento,"
				+ "p.e_mail per_email, p.contacto per_contacto, p.profecion per_profesion, p.estado_civil per_estado_civil, p.web per_web, "
				+ "p.observaciones per_observaciones, p.busqueda per_busqueda";
	}

	protected String getFieldsDom() throws Exception {
		return ", d.calle dom_calle, d.piso dom_piso, d.numero dom_numero, d.depto dom_depto,"
				+ "d.pais dom_pais, d.provincia dom_provincia, d.ciudad dom_ciudad,"
				+ "d.localidad dom_localidad, d.otros dom_otros, d.city dom_city, d.cod_postal dom_cod_postal ";
	}
	
	protected String getFieldsTelefono() throws Exception {
		return ", t1.numero tel_particular, t2.numero tel_comercial, t3.numero tel_celular";
	}
		
	public String getPerDomicilioFull() throws Exception {
		return BizDomicilio.getDomicilioCompleto(pDomCalle.getValue(), pDomPiso.getValue(), pDomNumero.getValue(), pDomDepto.getValue(), "", "", pDomPais.getValue(), pDomProvincia.getValue(), pDomCiudad.getValue(), pDomLocalidad.getValue());
	}
	
	public String getPerNombreFiscal() throws Exception {
		if (this.isPersonaFisica())
			return pPerNombre.getValue()+" "+pPerApellido.getValue();
		if (this.isPersonaJuridica())
			return pPerApellido.getValue();
		return pPerApellido.getValue();
	}

	public boolean isPersonaFisica() throws Exception {
		return this.pPerTipoPersona.getValue().equals(BizPersona.TIPO_FISICA);
	}

	public boolean isPersonaJuridica() throws Exception {
		return this.pPerTipoPersona.getValue().equals(BizPersona.TIPO_JURIDICA);
	}


  public String getDescrProv() throws Exception {
  	BizPais p = BizPais.findPais(pDomPais.getValue());
  	if (p==null) return "";
  	if (pDomProvincia.isEmpty()) return "";
  	BizProvincia pr = p.findProvincia(pDomProvincia.getValue());
  	if (pr==null) return "";
  	return pr.GetDescrip();
  }


	public BizPersona makePerson() throws Exception {
		// optimizacion de la construccion de la persona con los datos del join
		BizPersona p=new BizPersona();
		p.setDatosLeidos(true);
		p.setPersona(this.pPerIdPersona.getValue());
		p.setTipoPersona(this.pPerTipoPersona.getValue());
		p.setDescription(this.pPerDescription.getValue());
		p.SetApellido(this.pPerApellido.getValue());
		p.SetNombre(this.pPerNombre.getValue());
		p.SetNacionalidad(this.pPerNacionalidad.getValue());
		p.SetFechaNac(this.pPerFechaNacimiento.getValue());
		p.SetTipoDoc(this.pPerTipoDoc.getValue());
		p.SetNroDoc(this.pPerNroDoc.getValue());
		p.SetSexo(this.pPerSexo.getValue());
		p.SetContacto(this.pPerContacto.getValue());
		p.SetEmail(this.pPerEMail.getValue());
		p.setIdentAdicional(this.pPerIdentAdicional.getValue());
		p.setProfesion(this.pPerProfesion.getValue());
		p.SetPasaporte(this.pPerPasaporte.getValue());
		p.setFechaVtoPasaporte(this.pPerVtoPasaporte.getValue());
		p.SetObservaciones(this.pPerObservaciones.getValue());
		p.SetWeb(this.pPerWeb.getValue());
		p.SetEstadoCivil(this.pPerEstadoCivil.getValue());
		p.setObjAddressMain(this.makeDomicilio());
		p.setObjTelParticular(this.makeTelParticular());
		p.setObjTelComercial(this.makeTelComercial());
		p.setObjTelCelular(this.makeTelCelular());
		return p;
	}		
	
	public BizDomicilio makeDomicilio() throws Exception {
		BizDomicilio dom = new BizDomicilio();
		dom.setDatosLeidos(true);
		dom.SetCalle(pDomCalle.getValue());
		dom.SetPiso(pDomPiso.getValue());
		dom.SetNumero(pDomNumero.getValue());
		dom.SetDpto(pDomDepto.getValue());
		dom.SetCodPostal(pDomCodPostal.getValue());
		dom.setOtros(pDomOtros.getValue());
		dom.SetCiudad(pDomCiudad.getValue());
		dom.SetLocalidad(pDomLocalidad.getValue());
		dom.SetProvincia(pDomProvincia.getValue());
		dom.SetPais(pDomPais.getValue());
		dom.setCity(pDomCity.getValue());
		return dom;
	}
	
	public BizTelefono makeTelParticular() throws Exception {
		BizTelefono tel = new BizTelefono();
		tel.setDatosLeidos(true);
		tel.SetNumero(pTelParticular.getValue());
		return tel;
	}

	public BizTelefono makeTelComercial() throws Exception {
		BizTelefono tel = new BizTelefono();
		tel.setDatosLeidos(true);
		tel.SetNumero(pTelComercial.getValue());
		return tel;
	}

	public BizTelefono makeTelCelular() throws Exception {
		BizTelefono tel = new BizTelefono();
		tel.setDatosLeidos(true);
		tel.SetNumero(pTelCelular.getValue());
		return tel;
	}

	public BizNewPerson createNewPerson() throws Exception {
		return new BizNewPerson();
	}
	
	public BizNewPerson getNewQueryPerson() throws Exception {
		return null;
	}

	public boolean hasData() throws Exception {
		return true;
	}

}
