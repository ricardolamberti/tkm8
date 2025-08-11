package  pss.common.personalData;

import java.util.Date;

import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.GregorianCalendar;

import pss.common.components.JSetupParameters;
import pss.common.customList.config.relation.JRelation;
import pss.common.customList.config.relation.JRelations;
import pss.common.dbManagement.synchro.JDBClassIndex;
import pss.common.personalData.tipoCui.BizTipoCui;
import pss.common.security.BizUsuario;
import pss.core.services.JExec;
import pss.core.services.fields.JBoolean;
import pss.core.services.fields.JDate;
import pss.core.services.fields.JInteger;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JObjBD;
import pss.core.services.fields.JObject;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.JDateTools;
import pss.core.tools.JExcepcion;
import pss.core.tools.JTools;
import pss.core.tools.collections.JIterator;

public class BizPersona extends JRecord {

	public static final String TIPO_ADM="A";
	public static final String TIPO_FISICA="F";
	public static final String TIPO_JURIDICA="J";
	
//	public static final String TIPO_CUIT="CT";
	public static final String SEXO_MASCULINO="M";
	public static final String SEXO_FEMENINO="F";
	// -------------------------------------------------------------------------//
	// Propiedades de la Clase
	// -------------------------------------------------------------------------//
	private JLong pPersona=new JLong();
	private JString pCompany=new JString();
	private JLong pPersonaMaster=new JLong();
	private JString pTipoPersona=new JString();
	protected JString pNombre=new JString();
	protected JString pApellido=new JString();
	protected JString pApellido2=new JString();
	private JString pApellidoMaterno=new JString();
	private JString pIdentAdicional=new JString();
	private JString pDescription=new JString();
	private JString pBusqueda=new JString();
	private JString pTipoDoc=new JString();
	private JString pNroDoc=new JString();
	private JString pTipoCui=new JString();
	private JString pNroCui=new JString();
	private JDate pFechaNacimiento=new JDate();
	private JString pEmail=new JString();
	private JString pIdRuapp=new JString();
	private JString pConyuge=new JString();
	private JInteger pNupcias=new JInteger();
	private JString pContacto=new JString();
	private JString pProfecion=new JString();
	private JString pTitulo=new JString();
	private JString pMatricula=new JString();
	private JString pWeb=new JString();
	private JString pPasaporte=new JString();
	private JDate pVtoPasaporte=new JDate();
	private JString pSexo=new JString();
	private JString pOrigen=new JString();
	private JString pInscripta=new JString();
	private JBoolean pAEliminar=new JBoolean();
	private JString pDescrTipoDoc=new JString() {
		public void preset() throws Exception {
			pDescrTipoDoc.setValue(GetDescrTipoDoc());
		}
	};
	private JString pDescrTipoCuit=new JString() {
		public void preset() throws Exception {
			pDescrTipoCuit.setValue(GetDescrTipoCuit());
		}
	};
	private JString pNombreCompleto=new JString() {
		public void preset() throws Exception {
			pNombreCompleto.setValue(getNombreCompleto());
		}
	};
	private JString pNacionalidad=new JString();
	private JString pEstadoCivil=new JString();
	private JString pDescrEstadoCivil=new JString() {
		public void preset() throws Exception {
			pDescrEstadoCivil.setValue(GetDescrEstadoCivil());
		}
	};
	private JLong pSecDomicilio = new JLong();
//  private JObjBD pDomicilio = new JObjBD() {
//  	public void preset() throws Exception {
//  		if (pDomicilio.isRawNull())
//  			pDomicilio.setValue(fillObjDomicilioForce());
//  	};
//  };
//  private JObjBD pTelefonoParticular = new JObjBD() {
//  	public void preset() throws Exception {
//  		if (pTelefonoParticular.isRawNull())
//  			pTelefonoParticular.setValue(fillTelParticularForce());
//  	};
//  };
//  private JObjBD pTelefonoCelular = new JObjBD() {
//  	public void preset() throws Exception {
//  		if (pTelefonoCelular.isRawNull())
//  			pTelefonoCelular.setValue(fillTelCelularForce());
//  	};
//  };
//  private JObjBD pTelefonoComercial = new JObjBD() {
//  	public void preset() throws Exception {
//  		if (pTelefonoComercial.isRawNull())
//  			pTelefonoComercial.setValue(fillTelComercialForce());
//  	};
//  };
	private JLong pCantidadHijos=new JLong();
	private JString pObservaciones=new JString();
	private JInteger pOcur= new JInteger();
	private BizDomicilio adressMain=null;
	private BizTipoDoc oTipoDocumento=null;
	private BizTelefono telParticular=null;
	private BizTelefono telComercial=null;
	private BizTelefono telCelular=null;
	private BizTelefono telFax=null;
	
	public long GetPersona() throws Exception {
		return pPersona.getValue();
	}


	public long getPersonaMaster() throws Exception {
		return pPersonaMaster.getValue();
	}

	public String GetNombre() throws Exception {
		return pNombre.getValue();
	}

	public String GetApellido() throws Exception {
		return pApellido.getValue();
	}
	public String GetApellido2() throws Exception {
		return pApellido2.getValue();
	}
	public String GetApellidoMaterno() throws Exception {
		return pApellidoMaterno.getValue();
	}
		
	public String getIdentAdicional() throws Exception {
		return pIdentAdicional.getValue();
	}

	public String GetContacto() throws Exception {
		return pContacto.getValue();
	}

	public String getProfecion() throws Exception {
		return pProfecion.getValue();
	}

	public String getTitulo() throws Exception {
		return pTitulo.getValue();
	}

	public String getMatricula() throws Exception {
		return pMatricula.getValue();
	}

	public String GetWeb() throws Exception {
		return pWeb.getValue();
	}

	public String GetPasaporte() throws Exception {
		return pPasaporte.getValue();
	}

	public Date GetFechaVtoPasaporte() throws Exception {
		return pVtoPasaporte.getValue();
	}

	public String GetSexo() throws Exception {
		return pSexo.getValue();
	}

	public void clearClave() throws Exception {
		this.pPersona.setNull();
	}

	public boolean hasTipoDoc() throws Exception {
		return !pTipoDoc.getValue().equals("");
	}

	public String GetTipoDoc() throws Exception {
		return pTipoDoc.getValue();
	}

	public String GetNroDoc() throws Exception {
		return pNroDoc.getValue();
	}

	public String getTipoCui() throws Exception {
		return pTipoCui.getValue();
	}

	public String getNroCui() throws Exception {
		return pNroCui.getValue();
	}

	public void setSecDomicilio(long zValue) {
		pSecDomicilio.setValue(zValue);
	}

	public long getSecDomicilio() throws Exception {
		return pSecDomicilio.getValue();
	}

	public String getNroCuiFormateado() throws Exception {
		if (getNroCui().length()<11) return getNroCui();
		if (getNroCui().indexOf("-")!=-1) return getNroCui();
		return getNroCui().substring(0,2)+"-"+getNroCui().substring(2,2+8)+"-"+getNroCui().substring(10);
	}

	public String GetDescrTipoDoc() throws Exception {
		if (pTipoDoc.isNull()) return "";
		BizTipoDoc oTipoDoc=new BizTipoDoc();
		oTipoDoc.dontThrowException(true);
		if (!oTipoDoc.Read(pNacionalidad.getValue(), this.pTipoDoc.getValue())) return "";
		return oTipoDoc.pDescripcion.getValue();
	}
	public String GetDescrTipoCuit() throws Exception {
		if (pTipoCui.isNull()) return "";
		BizTipoCui oTipoDoc=new BizTipoCui();
		oTipoDoc.dontThrowException(true);
		if (!oTipoDoc.Read(pNacionalidad.getValue(), this.pTipoCui.getValue())) return "";
		return oTipoDoc.getDescripcion();
	}
	public String GetDescrEstadoCivil() throws Exception {
		if (pEstadoCivil.isNull()) return "";
		BizEstadoCivil oEstCiv=new BizEstadoCivil();
		oEstCiv.dontThrowException(true);
		if (!oEstCiv.Read(this.pEstadoCivil.getValue())) return "";
		return oEstCiv.getDescripcion();
	}



	public String GetEmail() throws Exception {
		return pEmail.getValue();
	}
	public boolean hasMail() throws Exception {
		return pEmail.isNotNull();
	}

	public String GetNacionalidad() throws Exception {
		return pNacionalidad.getValue();
	}

	public String GetObservaciones() throws Exception {
		return pObservaciones.getValue();
	}

	public String GetEstadoCivil() throws Exception {
		return pEstadoCivil.getValue();
	}
	
	public String getConyuge() throws Exception {
		return pConyuge.getValue();
	}

	public int getNupcias() throws Exception {
		return pNupcias.getValue();
	}

	public long GetCantidadHijos() throws Exception {
		return pCantidadHijos.getValue();
	}
	public long GetNupcias() throws Exception {
		return pNupcias.getValue();
	}
	public boolean GetMayorMenor() throws Exception {
		if (hasFechaNacimiento()) {
			Calendar cal = new GregorianCalendar();
			cal.setTime(getFechaNacimiento());
			int anios = Math.abs(cal.fieldDifference(new Date(), Calendar.YEAR));
			return anios>=18;
		}
		return true;
	}


	public void setPersona(long zValue) {
		pPersona.setValue(zValue);
	}

	public void setPersonaMaster(long zValue) {
		pPersonaMaster.setValue(zValue);
	}

	public void SetNombre(String zValue) {
		pNombre.setValue(zValue);
	}
	public void setCompany(String zValue) {
		pCompany.setValue(zValue);
	}

	public void SetApellido(String zValue) {
		pApellido.setValue(zValue);
	}
	public void SetApellido2(String zValue) {
		pApellido2.setValue(zValue);
	}

	public void setIdentAdicional(String zValue) {
		pIdentAdicional.setValue(zValue);
	}

	public void setDescription(String zValue) {
		pDescription.setValue(zValue);
	}

	public void SetTipoDoc(String zValue) {
		pTipoDoc.setValue(zValue);
	}

	public void setTipoCui(String zValue) {
		pTipoCui.setValue(zValue);
	}

	public void SetWeb(String zValue) {
		pWeb.setValue(zValue);
	}

	public void SetContacto(String zValue) {
		pContacto.setValue(zValue);
	}

	public void setProfesion(String zValue) {
		pProfecion.setValue(zValue);
	}

	public void setTitulo(String zValue) {
		pTitulo.setValue(zValue);
	}

	public void setConyuge(String zValue) {
		pConyuge.setValue(zValue);
	}

	public void setNupcias(int zValue) {
		pNupcias.setValue(zValue);
	}

	public void setMatricula(String zValue) {
		pMatricula.setValue(zValue);
	}

	public void SetPasaporte(String zValue) {
		pPasaporte.setValue(zValue);
	}

	public void setFechaVtoPasaporte(Date zValue) {
		pVtoPasaporte.setValue(zValue);
	}

	public void SetSexo(String zValue) {
		pSexo.setValue(zValue);
	}

	public void SetNroDoc(String zValue) {
		pNroDoc.setValue(zValue);
	}

	public void setNroCui(String zValue) {
		pNroCui.setValue(zValue);
	}

	public void SetFechaNac(Date zValue) throws Exception {
		pFechaNacimiento.setValue(zValue);
	}

	public void SetEmail(String zValue) {
		pEmail.setValue(zValue);
	}

	public void SetNacionalidad(String zValue) {
		pNacionalidad.setValue(zValue);
	}

	public void setObjAddressMain(BizDomicilio zValue) {
		adressMain=zValue;
	}

	public void setObjTelParticular(BizTelefono zValue) {
		telParticular=zValue;
	}

	public void setObjTelComercial(BizTelefono zValue) {
		telComercial=zValue;
	}

	public void setObjTelCelular(BizTelefono zValue) {
		telCelular=zValue;
	}

	public void setObjTelFax(BizTelefono zValue) {
		telFax=zValue;
	}


	public void SetObservaciones(String zVal) throws Exception {
		pObservaciones.setValue(zVal);
	}

	public void SetEstadoCivil(String zVal) throws Exception {
		pEstadoCivil.setValue(zVal);
	}
	
	public void setOcur(int zValue) {
		pOcur.setValue(zValue);
	}

	public boolean hasAnyOcur() throws Exception  {
		return this.pOcur.getValue()>0;
	}

	public int getOcur() throws Exception {
		return pOcur.getValue();
	}

	public void SetCantidadHijos(long zVal) throws Exception {
		pCantidadHijos.setValue(zVal);
	}
	
	public void setTipoPersona(String zVal) throws Exception {
		pTipoPersona.setValue(zVal);
	}

	public void SetOrigen(String zVal) throws Exception {
		pOrigen.setValue(zVal);
	}

	public String GetOrigen() throws Exception {
		return pOrigen.getValue();
	}
	public void SetInscripta(String zVal) throws Exception {
		pInscripta.setValue(zVal);
	}

	public String GetInscripta() throws Exception {
		return pInscripta.getValue();
	}
	
	public void SetApellidoMaterno(String zVal) throws Exception {
		pApellidoMaterno.setValue(zVal);
	}


//	public static boolean sprocessDeleteFlag=true;
//	private static final String aNodosHijos[]= { "Pss.efleet.autorizador.Contrato.BizYERCliente" };
	private JRecords<BizDomicilio> domicilios;
	private JRecords<BizTelefono> telefonos;

	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public BizPersona() throws Exception {
	}

	@Override
	public void createProperties() throws Exception {
		addItem("persona", pPersona);
		addItem("persona_master", pPersonaMaster);
		addItem("company", pCompany);
		addItem("tipo_persona", pTipoPersona);
		addItem("nombre", pNombre);
		addItem("apellido", pApellido);
		addItem("apellido2", pApellido2);
		addItem("apellido_materno", pApellidoMaterno);
		addItem("ident_adicional", pIdentAdicional);
		addItem("description", pDescription);
		addItem("tipo_doc", pTipoDoc);
		addItem("nro_doc", pNroDoc);
		addItem("tipo_cui", pTipoCui);
		addItem("nro_cui", pNroCui);
		addItem("titulo", pTitulo);
		addItem("fecha_nacimiento", pFechaNacimiento);
		addItem("e_mail", pEmail);
		addItem("estado_civil", pEstadoCivil);
		addItem("cantidad_hijos", pCantidadHijos);
		addItem("observaciones", pObservaciones);
		addItem("nacionalidad", pNacionalidad);
		addItem("descr_tipo_doc", pDescrTipoDoc);
		addItem("descr_tipo_cuit", pDescrTipoCuit);
		addItem("descr_estado_civil", pDescrEstadoCivil);
		addItem("nombre_completo", pNombreCompleto);
		addItem("web", pWeb);
		addItem("pasaporte", pPasaporte);
		addItem("vto_pasaporte", pVtoPasaporte);
		addItem("sexo", pSexo);
		addItem("nupcias", pNupcias);
		addItem("busqueda", pBusqueda);
		addItem("id_ruapp", pIdRuapp);
		addItem("contacto", pContacto);
		addItem("profecion", pProfecion);
		addItem("matricula", pMatricula);
		addItem("conyuge", pConyuge);
		addItem("nupcias", pNupcias);
		addItem("ocur", pOcur);	
		addItem("origen", pOrigen);	
		addItem("inscripta", pInscripta);	
		addItem("a_eliminar", pAEliminar);	
		addItem("sec_domicilio", pSecDomicilio);	
		
//	  this.addItem( "domicilio", pDomicilio );
//	  this.addItem( "tel_particular", pTelefonoParticular );
//	  this.addItem( "tel_comercial", pTelefonoComercial );
//	  this.addItem( "tel_celular", pTelefonoCelular );
	}

	
	@Override
	public void createFixedProperties() throws Exception {
		addFixedItem(KEY,   "persona", "Persona", false, false, 18);
		addFixedItem(FIELD, "persona_master", "Persona Master", true, false, 18);
		addFixedItem(FIELD, "company", "Empresa", true, false, 15);
		addFixedItem(FIELD, "tipo_persona", "Tipo", true, false, 1);
		addFixedItem(FIELD, "nombre", "Nombre", true, false, 250);
		addFixedItem(FIELD, "apellido", getFieldApellidoDescription(), true, true, 250, 0, JObject.JUPPERCASE);
		addFixedItem(FIELD, "apellido2", getFieldApellidoDescription(), true, false, 250);
		addFixedItem(FIELD, "apellido_materno", getFieldApellidoDescription()+ " materno", true, false, 100);
		addFixedItem(FIELD, "ident_adicional", "Ident Adicional", true, false, 200);
		addFixedItem(FIELD, "description", "Descripción", true, true, 500);
		addFixedItem(FIELD, "tipo_doc", "Tipo Documento", true, false, 5);
		addFixedItem(FIELD, "nro_doc", "Nro. Documento", true, false, 20);
		addFixedItem(FIELD, "tipo_cui", "Tipo Cui", true, false, 5);
		addFixedItem(FIELD, "nro_cui", "Nro. Cui", true, false, 20);
		addFixedItem(FIELD, "fecha_nacimiento", getFieldFechaNacimiento(), true, false, 10);
		addFixedItem(FIELD, "e_mail", "Email", true, false, 50);
		addFixedItem(FIELD, "nacionalidad", "Nacionalidad", true, false, 100);
		addFixedItem(FIELD, "estado_civil", "Estado civil", true, false, 20);
		addFixedItem(FIELD, "cantidad_hijos", "Cantidad hijos", true, false, 2);
		addFixedItem(FIELD, "observaciones", "Observaciones", true, false, 1000);
		addFixedItem(FIELD, "titulo", "Titulo", true, false, 200);
		addFixedItem(VIRTUAL, "descr_tipo_doc", "Desc. Tipo Documento", true, true, 5);
		addFixedItem(VIRTUAL, "descr_estado_civil", "Desc. Estado Civil", true, true, 50);
		addFixedItem(VIRTUAL, "nombre_completo", "Nombre Completo", true, true, 300);
		addFixedItem(FIELD, "web", "web", true, false, 200);
		addFixedItem(FIELD, "pasaporte", "Pasaporte", true, false, 20);
		addFixedItem(FIELD, "vto_pasaporte", "Vto. Pasaporte", true, false, 200);
		addFixedItem(FIELD, "sexo", "Sexo", true, false, 1);
		addFixedItem(FIELD, "id_ruapp", "Id Ruapp", true, false, 20);
		addFixedItem(FIELD, "contacto", "Contacto", true, false, 50);
		addFixedItem(FIELD, "profecion", "Profesion", true, false, 100);
		addFixedItem(FIELD, "matricula", "Matricula", true, false, 20);
		addFixedItem(FIELD, "conyuge", "Conyuge", true, false, 250);
		addFixedItem(FIELD, "nupcias", "Nupcias", true, false, 2);
		addFixedItem(FIELD, "busqueda", "Busqueda", true, false, 1000);

		addFixedItem(FIELD, "ocur", "Ocur", true, false, 5);
		addFixedItem(FIELD, "origen", "Origen", true, false, 150);
		addFixedItem(FIELD, "inscripta", "Inscripta", true, false, 150);
		addFixedItem(FIELD, "a_eliminar", "A Eliminar", true, false, 1);
		addFixedItem(FIELD, "sec_domicilio", "Sec Domicilio", true, false, 5);

		addFixedItem(VIRTUAL, "descr_tipo_cuit", "Desc. Tipo Documento CUIT", true, true, 5);
		addFixedItem(VIRTUAL, "descr_tipo_doc", "Desc. Tipo Documento", true, true, 5);
		addFixedItem(VIRTUAL, "descr_estado_civil", "Desc. Estado Civil", true, true, 50);
		addFixedItem(VIRTUAL, "nombre_completo", "Nombre Completo", true, true, 300);

//		this.addFixedItem( RECORD, "domicilio", "Domicilio", true, false, 15 ).setClase(BizDomicilio.class);
//		this.addFixedItem( RECORD, "tel_particular", "Telefono Particular", true, false, 15 ).setClase(BizTelefono.class);
//		this.addFixedItem( RECORD, "tel_comercial", "Telefono Comercial", true, false, 15 ).setClase(BizTelefono.class);
//		this.addFixedItem( RECORD, "tel_celular", "Telefono Celular", true, false, 15 ).setClase(BizTelefono.class);

	}

	/**
	 * Create the indexed fields
	 */
	public void createIndexes() throws Exception {
		JDBClassIndex indexIX1 = this.addIndex("PER_PERSONA_IX1");
		indexIX1.addField("NRO_DOC");
}

	@Override
	public String GetTable() {
		return "per_persona";
	}

//	@Override
//	public void setupConfig(JSetupParameters zParams) throws Exception {
//		zParams.setExportData(zParams.isLevelBusiness());
//	}

	@Override
	public void processInsert() throws Exception {
		if (this.pTipoPersona.isNull()) this.pTipoPersona.setValue(this.getTipoPersona());
		if (this.pOcur.isNull()) this.pOcur.setValue(1);
//		this.validateConstraints();
//		if (this.wasRead()) 
//			this.update();
//		else {
			this.insert();
			this.pPersona.setValue(this.getIdentity("persona"));
//		}

	}

	public boolean Read(long zPersona) throws Exception {
		addFilter("persona", zPersona);
		return read();
	}

//	private void ObtenerDescrTipoDoc() throws Exception {
//		if (pTipoDoc.isNull()) return;
//		BizTipoDoc oTipoDoc=new BizTipoDoc();
//		oTipoDoc.Read(pNacionalidad.getValue(), this.pTipoDoc.getValue());
//		pDescrTipoDoc.setValue(oTipoDoc.pDescripcion.getValue());
//	}

//	private void ObtenerDescrEstadoCivil() throws Exception {
//		if (pEstadoCivil.isNull()) return;
//		BizEstadoCivil oEstCiv=new BizEstadoCivil();
//		oEstCiv.Read(this.pEstadoCivil.getValue());
//		pDescrEstadoCivil.setValue(oEstCiv.getDescripcion());
//	}

	// public BizDomicilio getObjLegalAddress() throws Exception {
	// return getObjLegalAddress(false);
	// }

	public String getTelComercial() throws Exception {
		BizTelefono oTelefono=getObjTelComercial();
		if (oTelefono==null) return "";
		return oTelefono.getDescription();
	}

	public String getTelParticular() throws Exception {
		BizTelefono oTelefono=getObjTelParticular();
		if (oTelefono==null) return "";
		return oTelefono.getDescription();
	}

	public String getTelCelular() throws Exception {
		BizTelefono oTelefono=getObjTelCelular();
		if (oTelefono==null) return "";
		return oTelefono.getDescription();
	}
	
	public void addDomicilio(BizDomicilio d) throws Exception {
		this.getObjDomicilios().addItem(d);
	}
	
//	public boolean hasDomicilioLegal() throws Exception {
//		JIterator<BizDomicilio> iter=this.getObjDomicilios().getStaticIterator();
//		while (iter.hasMoreElements()) {
//			BizDomicilio d=iter.nextElement();
//			if (d.isLegal()) return true;
//		}
//		return false;
//	}
//	public boolean hasDomicilioComercial() throws Exception {
//		JIterator<BizDomicilio> iter=this.getObjDomicilios().getStaticIterator();
//		while (iter.hasMoreElements()) {
//			BizDomicilio d=iter.nextElement();
//			if (d.isComercial()) return true;
//		}
//		return false;
//	}
//	public boolean hasDomicilioParticular() throws Exception {
//		JIterator<BizDomicilio> iter=this.getObjDomicilios().getStaticIterator();
//		while (iter.hasMoreElements()) {
//			BizDomicilio d=iter.nextElement();
//			if (d.isParticular()) return true;
//		}
//		return false;
//	}
//	public BizDomicilio getObjDomicilioLegalNoExec() throws Exception {
//		JIterator<BizDomicilio> iter=this.getObjDomicilios().getStaticIterator();
//		while (iter.hasMoreElements()) {
//			BizDomicilio d=iter.nextElement();
//			if (d.isLegal()) return d;
//		}
//		return null;
//	}
	
//	public BizDomicilio getObjDomicilioLegalNoExcep() throws Exception {
//		JIterator<BizDomicilio> iter=this.getObjDomicilios().getStaticIterator();
//		while (iter.hasMoreElements()) {
//			BizDomicilio d=iter.nextElement();
//			if (d.isLegal()) return d;
//		}
//		return null;
//	}
	
//	public BizDomicilio getObjDomicilioLegal() throws Exception {
//		JIterator<BizDomicilio> iter=this.getObjDomicilios().getStaticIterator();
//		while (iter.hasMoreElements()) {
//			BizDomicilio d=iter.nextElement();
//			if (d.isLegal()) return d;
//		}
//		JExcepcion.SendError("No existe domicilio Legal");
//		return null;
//	}
	
//	public BizDomicilio getObjDomicilioParticular() throws Exception {
//		JIterator<BizDomicilio> iter=this.getObjDomicilios().getStaticIterator();
//		while (iter.hasMoreElements()) {
//			BizDomicilio d=iter.nextElement();
//			if (d.isLegal()) return d;
//		}
//		JExcepcion.SendError("No existe domicilio Particular");
//		return null;
//	}
	
	public String getDomicilioCompleto() throws Exception {
		return this.getObjDomicilioMain().getDomicilioCompleto();
	}

	public BizDomicilio getObjDomicilioMain() throws Exception {
		if (this.adressMain!=null) return this.adressMain;
		return (this.adressMain=this.getDomicilioMain());
	}
	
	public BizDomicilio getDomicilioMain() throws Exception {
		if (!this.hasSecDomicilio()) return null;
		BizDomicilio dom = new BizDomicilio();
		dom.Read(this.GetPersona(), this.getSecDomicilio());
		return dom;
	}
	
	public boolean hasSecDomicilio() throws Exception {
		return this.pSecDomicilio.getValue()!=0L;
	}
//	JIterator<BizDomicilio> iter=this.getObjDomicilios().getStaticIterator();
//	while (iter.hasMoreElements()) {
//		BizDomicilio d=iter.nextElement();
//		if (d.isLegal()) return d;
//	}
//	JExcepcion.SendError("No existe domicilio Legal");
//	return null;
//}
	
//	public BizDomicilio getObjDomicilioComercialNoExcep() throws Exception {
//		JIterator<BizDomicilio> iter=this.getObjDomicilios().getStaticIterator();
//		while (iter.hasMoreElements()) {
//			BizDomicilio d=(BizDomicilio) iter.nextElement();
//			if (d.isComercial()) return d;
//		}
//		//JExcepcion.SendError("No existe domicilio Comercial");
//		return null;
//	}

//	public BizDomicilio getObjDomicilioComercial() throws Exception {
//		JIterator<BizDomicilio> iter=this.getObjDomicilios().getStaticIterator();
//		while (iter.hasMoreElements()) {
//			BizDomicilio d=(BizDomicilio) iter.nextElement();
//			if (d.isComercial()) return d;
//		}
//		JExcepcion.SendError("No existe domicilio Comercial");
//		return null;
//	}

//	public BizDomicilio getObjDomicilioListados() throws Exception {
//		JIterator<BizDomicilio> iter=this.getObjDomicilios().getStaticIterator();
//		while (iter.hasMoreElements()) {
//			BizDomicilio d=(BizDomicilio) iter.nextElement();
//			if (d.isListados()) return d;
//		}
////		JExcepcion.SendError("No existe domicilio para Listados");
//		return null;
//	}

	public BizTipoDoc getObjTipoDocumento() throws Exception {
		if (oTipoDocumento!=null) return oTipoDocumento;
		oTipoDocumento=new BizTipoDoc();
		oTipoDocumento.Read(pNacionalidad.getValue(), GetTipoDoc());
		return oTipoDocumento;
	}
	
	public boolean isPersonaFisica() throws Exception {
		return this.pTipoPersona.getValue().equals(BizPersona.TIPO_FISICA);
	}

	public boolean isPersonaJuridica() throws Exception {
		return this.pTipoPersona.getValue().equals(BizPersona.TIPO_JURIDICA);
	}
	
	public boolean isPersonaAdm() throws Exception {
		return this.pTipoPersona.getValue().equals(BizPersona.TIPO_ADM);
	}
	
	public boolean hasTipoPersona() throws Exception {
		return this.pTipoPersona.hasValue();
	}

	public String getDescription() throws Exception {
		return this.pDescription.getValue();
	}

	
	public String getNombreCompleto() throws Exception {
		return BizUsuario.getUsr().getObjBusiness().getNombreCompleto(this);
	}

	public String getNombreFiscal() throws Exception {
		if (this.isPersonaFisica())
			return pApellido.getValue()+", "+JTools.capitalizeAll(pNombre.getValue());
		if (this.isPersonaJuridica())
			return pApellido.getValue();
		return pApellido.getValue();
	}

//	public BizDomicilio getObjDomicilioForce() throws Exception {
//		return (BizDomicilio) pDomicilio.getValue();
//	}
//	public BizDomicilio fillObjDomicilioForce() throws Exception {
//		JIterator<BizDomicilio> iter=this.getObjDomicilios().getStaticIterator();
//		while (iter.hasMoreElements()) {
//			BizDomicilio d=(BizDomicilio) iter.nextElement();
//			if (d.isLegal()) return d;
//		}
//		BizDomicilio objDomicilio = new BizDomicilio();
//		objDomicilio.setIsLegal(true);
//		objDomicilio.SetPais(BizUsuario.getUsr().getBirthCountryId());
//		return objDomicilio;
//	}
//	public BizTelefono getObjTelCelularForce() throws Exception {
//		return (BizTelefono) pTelefonoCelular.getValue();
//	}
//	public BizTelefono getObjTelComercialForce() throws Exception {
//		return (BizTelefono) pTelefonoCelular.getValue();
//	}
//	public BizTelefono getObjTelParticularForce() throws Exception {
//		return (BizTelefono) pTelefonoCelular.getValue();
//	}
//	public BizTelefono fillTelCelularForce() throws Exception {
//		BizTelefono tel = getObjTelCelular();
//		if (tel==null) {
//			tel = new BizTelefono();
//			tel.SetTipoTel( BizTelefono.TIPO_CELULAR);
//		}
//		setObjTelCelular(tel);
//		return tel;
//	}
//	public BizTelefono fillTelComercialForce() throws Exception {
//		BizTelefono tel = getObjTelComercial();
//		if (tel==null) {
//			tel = new BizTelefono();
//			tel.SetTipoTel( BizTelefono.TIPO_COMERCIAL);
//		}
//		setObjTelComercial(tel);
//		return tel;
//	}
//	public BizTelefono fillTelParticularForce() throws Exception {
//		BizTelefono tel =getObjTelParticular();
//		if (tel==null) {
//			tel = new BizTelefono();
//			tel.SetTipoTel( BizTelefono.TIPO_PARTICULAR);
//		}
//		setObjTelParticular(tel);
//		return tel;
//	}
//	private void processDeleteHijos() throws Exception {
//		BizPersona.sprocessDeleteFlag=false;
//		for(int i=0; i<aNodosHijos.length; i++)
//			this.processDeleteHijo(i);
//		BizPersona.sprocessDeleteFlag=true;
//	}
//
//	private void processDeleteHijo(int zHijo) throws Exception {
//		JRecord oNodoHijo=null;
//		try {
//			oNodoHijo=(JRecord) Class.forName(aNodosHijos[zHijo]).newInstance();
//		} catch (Exception e) {
//			return;
//		}
//		if (oNodoHijo.getClass()!=this.getClass()) {
//			oNodoHijo.addFilter("persona", pPersona.getValue());
//			oNodoHijo.SetNoExcSelect(true);
//			oNodoHijo.Read();
//			try {
//				oNodoHijo.processDelete();
//			} catch (Exception e) {
//				BizPersona.sprocessDeleteFlag=true;
//				JExcepcion.SendError(e.getMessage());
//			}
//		}
//		;
//	}

	@Override
	public void processDelete() throws Exception {
//		if (BizPersona.sprocessDeleteFlag) {
//			processDeleteHijos();
//		}
//		update();
		if (pOcur.getValue()>1) {
			this.pOcur.setValue(pOcur.getValue()-1);
			this.update();
			return;
		}
		JRecords<BizDomicilio> doms = this.getObjDomicilios();
		doms.setViolateIntegrity(true);
		doms.processDeleteAll();
		this.getObjTelefonos().processDeleteAll();
		super.processDelete();
		this.pOcur.setValue(0);
	}

	@Override
	public void validateConstraints() throws Exception {
		this.validateBirth();
		this.validateDocumentation();
		if(this.pCompany.isNull()) this.pCompany.setValue(BizUsuario.getUsr().getCompany());
		pDescription.setValue(this.getNombreCompleto());
		this.generateBusqueda();
	}
	public void generateBusqueda() throws Exception {
		pBusqueda.setValue(JTools.generateBusqueda(this.GetApellido()+" "+this.GetNombre()+" "+getIdentAdicional()+" "+GetNroDoc()+" "+getNroCui(), false));
	}
	public void validateBirth() throws Exception {
		if (pFechaNacimiento.isNotNull()&&pFechaNacimiento.getValue().after(JDateTools.StringToDate(JDateTools.CurrentDate("yyyy.MM.dd"), "yyyy.MM.dd"))) {
			JExcepcion.SendError("La "+getFieldFechaNacimiento()+" es posterior a la actual");
		}
	}

	public void validateDocumentation() throws Exception {
//		if (JRecords.existsComplete("Pss.eClub.Client.BizClient", "persona", GetPersona())) if (pTipoDoc.isEmpty()) 
//			JExcepcion.SendError("Campo Requerido: Tipo Documento");
//		if (pNacionalidad.isEmpty()) {
//			if (!pTipoDoc.isEmpty()) JExcepcion.SendError("Debe seleccionar una Nacionalidad");
//			if (!pNroDoc.isEmpty()) JExcepcion.SendError("Debe seleccionar una Nacionalidad y Tipo de Documento");
//			return; // Nacionalidad, TipoDoc y NroDoc vacios. OK.
//		}
//		BizPais.doesExist(true, pNacionalidad.getValue());
		// Hasta aca existe Nacionalidad
//		if (!pNroDoc.isEmpty() && pTipoDoc.isEmpty())
//			JExcepcion.SendError("Debe ingresar un Tipo de documento");

//		// valida que concuerde el tipo de doc con el pais
//		JRecords<BizTipoDoc> oBDs=new JRecords<BizTipoDoc>(BizTipoDoc.class);
//		oBDs.addFilter("tipo_doc", pTipoDoc.getValue());
//		oBDs.addFilter("pais", pNacionalidad.getValue());
//		oBDs.readAll();
//		oBDs.firstRecord();
//		if (!oBDs.nextRecord()) {
//			JExcepcion.SendError("Debe ingresar un tipo de documento del pais");
//		}
		return; // Todo OK.
	}

	public JRecords<BizDomicilio> getObjDomicilios() throws Exception {
		if (this.domicilios!=null) return this.domicilios;
		JRecords<BizDomicilio> records=new JRecords<BizDomicilio>(BizDomicilio.class);
		records.addFilter("persona", pPersona.getValue());
		records.setParent(this);
		records.readAll();
		records.toStatic();
		return (this.domicilios=records);
	}

	public JRecords<BizTelefono> getObjTelefonos() throws Exception {
		if (this.telefonos!=null) return this.telefonos;
		return (this.telefonos=this.getTelefonos());
	}
	public JRecords<BizTelefono> getTelefonos() throws Exception {
		JRecords<BizTelefono> oJBDs=new JRecords<BizTelefono>(BizTelefono.class);
		oJBDs.addFilter("persona", pPersona.getValue());
		oJBDs.readAll();
		return oJBDs;
	}

	public static String joinDomicilio(String zPersona, String zDomicilio) {
		String sWhere="";
		sWhere+=" and "+zDomicilio+".persona = "+zPersona+".persona ";
		sWhere+=" and "+zDomicilio+".tipo_domicilio = "+zPersona+".domicilio_principal ";
		return sWhere;
	}

	public static String joinDocumento(String zPersona, String zDocumento) {
		String sWhere="";
		sWhere+=" and "+zDocumento+".tipo_doc = "+zPersona+".tipo_doc ";
		sWhere+=" and "+zDocumento+".pais = "+zPersona+".nacionalidad ";
		return sWhere;
	}

	protected String getFieldApellidoDescription() {
		return "Apellido";
	}

	protected String getFieldFechaNacimiento() {
		return "Fecha de Nacimiento";
	}
	public BizTelefono getObjTelefono() throws Exception {
		BizTelefono t = getObjTelCelular();
		if (t!=null) return t;
		t = getObjTelParticular();
		if (t!=null) return t;
		t = getObjTelComercial();
		if (t!=null) return t;
		t = getObjTelFax();
		if (t!=null) return t;
		return null;
	}
	
	public BizTelefono getObjTelComercial() throws Exception {
		if (telComercial!=null) return telComercial;
		BizTelefono record=new BizTelefono();
		record.dontThrowException(true);
		if (!record.Read(pPersona.getValue(), BizTelefono.TIPO_COMERCIAL)) {
			return null;
		}
		return (this.telComercial=record);
	}

	public BizTelefono getObjTelParticular() throws Exception {
		if (telParticular!=null) return telParticular;
		BizTelefono record=new BizTelefono();
		record.dontThrowException(true);
		if (!record.Read(pPersona.getValue(), BizTelefono.TIPO_PARTICULAR)) {
			return null;
		}
		return (this.telParticular=record);
	}

	public BizTelefono getObjTelCelular() throws Exception {
		if (telCelular!=null) return telCelular;
		BizTelefono record=new BizTelefono();
		record.dontThrowException(true);
		if (!record.Read(pPersona.getValue(), BizTelefono.TIPO_CELULAR)) {
			return null;
		}
		return (this.telCelular=record);
	}

	public BizTelefono getObjTelFax() throws Exception {
		if (telFax!=null) return telFax;
		BizTelefono record=new BizTelefono();
		record.dontThrowException(true);
		if (!record.Read(pPersona.getValue(), BizTelefono.TIPO_FAX)) {
			return null;
		}
		return (this.telFax=record);
	}

	public boolean hasFechaNacimiento() throws Exception {
		return pFechaNacimiento.isNotNull();
	}

	public Date getFechaNacimiento() throws Exception {
		return pFechaNacimiento.getValue();
	}

	public String getIdRuapp() throws Exception {
		return pIdRuapp.getValue();
	}

	public void setIdRuapp(String idSistemaExterno) {
		pIdRuapp.setValue(idSistemaExterno);
	}

	@Override
	public void execProcessInsert() throws Exception {
		// do nothing
	}

	public void execProcessDesMarcarAEliminar() throws Exception {
		JExec exec = new JExec() {
			public void Do() throws Exception {
				processDesMarcarAEliminar();
			}
		};
		exec.execute();
	}
	
	public void processDesMarcarAEliminar() throws Exception {
		BizPersona p = (BizPersona) this.getPreInstance();
		p.pAEliminar.setValue(false);
		p.update();
	}

	public void execProcessMarcarAEliminar() throws Exception {
		JExec exec = new JExec() {
			public void Do() throws Exception {
				processMarcarAEliminar();
			}
		};
		exec.execute();
	}
	
	public void processMarcarAEliminar() throws Exception {
		BizPersona p = (BizPersona) this.getPreInstance();
		p.pAEliminar.setValue(true);
		p.update();
	}
	
	public String getTipoPersona() throws Exception {
		return BizPersona.TIPO_FISICA;
	}
	public String getIdTipoPersona() throws Exception {
		return pTipoPersona.getValue();
	}
	public static String getDescrTipoPersona(String tipo) throws Exception {
		if (tipo.equals(BizPersona.TIPO_FISICA)) return "Persona Física";
		if (tipo.equals(BizPersona.TIPO_JURIDICA)) return "Persona Jurídica";
		return "";
	}

	public void processIncrementOcur() throws Exception {
		BizPersona per = (BizPersona)this.getPreInstance();
		per.setOcur(per.getOcur()+1);
		per.update();
		this.setOcur(per.getOcur());
	}
	
	public void processUpdateOcur(BizPersona personaNew) throws Exception {
		if (this.GetPersona()==personaNew.GetPersona()) return;
		this.processDelete();
		personaNew.processIncrementOcur();
	}
	public void execProcessReindex() throws Exception {
		JExec oExec = new JExec(this, "processReindex") {

			@Override
			public void Do() throws Exception {
				processReindex();
			}
		};
		oExec.execute();
	}
	
	public void processReindex() throws Exception {
		JRecords<BizPersona> personas = new JRecords<BizPersona>(BizPersona.class);
		personas.readAll();
		while (personas.nextRecord()) {
			BizPersona per = personas.getRecord();
			per.generateBusqueda();
			per.setDescription(per.getNombreCompleto());
			per.update();
		}
	}
	
	public boolean isMaster() throws Exception {
		return this.pPersonaMaster.getValue()==0L;
	}
	
	public boolean isSlave() throws Exception {
		return !this.isMaster();
	}

	public boolean isAEliminar() throws Exception {
		return this.pAEliminar.getValue();
	}

	public long findPersonaMaster() throws Exception {
		return (this.isMaster()) ? this.GetPersona() : this.getPersonaMaster();
	}

	public void execUnificar(final JRecords pers) throws Exception {
		JExec exec = new JExec(null,null) {
			public void Do() throws Exception {
				processUnificar(pers);
			}
		};
		exec.execute();
	}
	
	public void processUnificar(JRecords pers) throws Exception {
		if (pers.sizeStaticElements()<=1) 
			JExcepcion.SendError("Funcion habilitada unicamente para unificar dos ó más personas");

		int index_master = this.getIndexOfMasterPersona(pers);
		BizPersona master=(BizPersona)pers.getStaticElement(index_master);
		
		JIterator iter = pers.getStaticIterator();
		while (iter.hasMoreElements()) {
			BizPersona dup = (BizPersona)iter.nextElement();
			if (dup.isAEliminar()) {  
				this.processUnificar(dup, master);
			}
		}
	}
	
	public int getIndexOfMasterPersona(JRecords pers) throws Exception {
		JIterator iter = pers.getStaticIterator();
		int master_qty = 0;
		int index = 0;
		int ret_index = -1;
		while (iter.hasMoreElements()) {
			BizPersona p = (BizPersona)iter.nextElement();
			index++;
			if (p.isAEliminar()) continue ;
			master_qty++;
			ret_index = index-1;
		}
		if (master_qty==0)  JExcepcion.SendError("Debe haber una persona que no sea a eliminar. Es la persona maestra ");
		if (master_qty>1)  JExcepcion.SendError("No puede haber más de una persona maestra ");
		return ret_index ;
	}

	public void processUnificar(BizPersona dup, BizPersona master) throws Exception {
		// cambio la clave en las personas slaves
		JRecords recs = new JRecords(BizPersona.class);
		recs.addField("persona_master", master.GetPersona());
		recs.addFilter("persona_master", dup.GetPersona());
		recs.update();
		dup.processDelete();
	}
	
	public String generateCompleteMail() throws Exception {
		// si tiene un solo mail le agrego el alias
		if (this.GetEmail().isEmpty()) return "";
		if (this.GetEmail().replace(';', ',').indexOf(',')==-1)
			return this.getDescription()+" <"+this.GetEmail()+">";
		return this.GetEmail();
	}
	
	public void attachRelationMap(JRelations rels) throws Exception {
		rels.setSourceWinsClass(GuiPersona.class.getCanonicalName());
		JRelation rel;
		rel = rels.addRelationForce(10, "restriccion usuario");
		rel.addFilter(" (per_persona.company= 'COMPANY_CUSTOMLIST') ");
		rels.hideField("persona");
		rels.hideField("persona_master");
		rels.hideField("tipo_persona");
		rels.hideField("apellido_materno");
		rels.hideField("company");
		rels.hideField("ident_adicional");
	  rels.hideField("apellido2");
		rels.hideField("tipo_cui");
		rels.hideField("nro_cui");
		rels.hideField("cantidad_hijos");
		rels.hideField("web");
		rels.hideField("pasaporte");
		rels.hideField("vto_pasaporte");
		rels.hideField("nupcias");
		rels.hideField("busqueda");
		rels.hideField("id_ruapp");
		rels.hideField("profecion");
		rels.hideField("conyuge");
		rels.hideField("matricula");
		rels.hideField("ocur");
		rels.hideField("origen");
		rels.hideField("inscripta");
		rels.hideField("a_eliminar");
		rels.hideField("secuencia*");
		rels.hideField("persona*");
		
		rels.hideField("domicilio.calle*");
		rels.hideField("domicilio.numero");
		rels.hideField("domicilio.piso");
		rels.hideField("domicilio.depto");
		rels.hideField("domicilio.cod_*");
		rels.hideField("domicilio.descr_prov*");
		rels.hideField("domicilio.descr_pais");
		rels.hideField("domicilio.descr_ciudad");
		rels.hideField("domicilio.localidad*");
		rels.hideField("domicilio.provincia");
		rels.hideField("domicilio.ciudad*");
		rels.hideField("domicilio.secuencia");
		rels.hideField("domicilio.persona");
		rels.hideField("domicilio.city");
		rels.hideField("domicilio.is*");
		rels.hideField("domicilio.otros");
		rels.hideField("domicilio.pais");
		rels.hideField("domicilio.geo_position");
				
		rels.hideField("tel_particular");
		rels.hideField("tel_particular.secuencia");
		rels.hideField("tel_particular.persona");
		rels.hideField("tel_particular.tipo_*");
		rels.hideField("tel_particular.descr_tipo_tel");
		rels.hideField("tel_particular.observacion*");
		rels.hideField("tel_comercial");
		rels.hideField("tel_comercial.secuencia");
		rels.hideField("tel_comercial.persona");
		rels.hideField("tel_comercial.tipo_*");
		rels.hideField("tel_comercial.descr_tipo_tel");
		rels.hideField("tel_comercial.observacion*");
		rels.hideField("tel_celular");
		rels.hideField("tel_celular.secuencia");
		rels.hideField("tel_celular.persona");
		rels.hideField("tel_celular.tipo_*");
		rels.hideField("tel_celular.descr_tipo_tel");
		rels.hideField("tel_celular.observacion*");


	  }
} 
