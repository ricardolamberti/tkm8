package  pss.common.agenda.evento;

import java.util.Calendar;
import java.util.Date;

import pss.common.agenda.JAgenda;
import pss.common.agenda.evento.logicas.IEventoLogica;
import pss.common.agenda.evento.tipo.BizTipoEvento;
import pss.common.agenda.evento.tipo.GuiTipoEventos;
import pss.common.agenda.historia.BizEventoHistoria;
import pss.common.agenda.participantes.BizEventoParticipante;
import pss.common.agenda.turno.BizTurno;
import pss.common.agenda.turno.GuiTurnos;
import pss.common.mail.JMail;
import pss.common.security.BizUsuario;
import pss.core.services.JExec;
import pss.core.services.fields.JBoolean;
import pss.core.services.fields.JDateTime;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JObjBDs;
import pss.core.services.fields.JScript;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.JDateTools;
import pss.core.tools.JPair;
import pss.core.tools.JTools;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;
import pss.core.win.JWins;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActSubmit;

public class BizEvento extends JRecord {

	public static final String VISION_LEGAJO = "VISION_LEGAJO";
	public static final String VISION_CONSOLA = "VISION_CONSOLA";
	public static final String VISION_ALARMA = "VISION_ALARMA";
	
  private JString pEstado = new JString();
  private JString pCompany = new JString();
  private JString  pTexto = new JString();
  private JString pTitulo = new JString();
  private JString pColor = new JString();
  private JBoolean pHito = new JBoolean();
  private JLong pIdtipo_evento = new JLong();
  private JLong pIdAgenda = new JLong();
  private JBoolean pHasAlarma = new JBoolean();
  private JDateTime pFechaAlarma = new JDateTime();
  private JString pUserAlarma = new JString();
  private JBoolean pReadedAlarma = new JBoolean();
  private JDateTime pFechaFin = new JDateTime();
  private JDateTime pFechaInicio = new JDateTime();
//  {
//  	public JScript getScript() throws Exception {
//  		return new JScript("alert('hola');");
//  	};
//  	
//  	public boolean hasScript() throws Exception { return true;};
//  }; 
  private JString pRazonEnSuspenso = new JString();
  private JLong pIdevento = new JLong();
  private JBoolean pPorSistema = new JBoolean();
  private JBoolean pAlterado = new JBoolean();

  private JString pHoraDesde = new JString() {
  	public void preset() throws Exception {
  		pHoraDesde.setValue(getHoraInicio());
  	};
  };
  private JString pHoraHasta = new JString() {
  	public void preset() throws Exception {
  		pHoraDesde.setValue(getHoraFin());
  	};
  };
  private JString pFechaInicioCalc = new JString() {
	  	public void preset() throws Exception {
	  		pFechaInicioCalc.setValue(getFechaInicioCalc());
	  	};
	  };
	  
		private JObjBDs pParticipantes = new JObjBDs() {
			public void preset() throws Exception {
				setValue(getObjParticipantes());
			}
		};	
		
	
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  public void setEstado(String zValue) throws Exception {    pEstado.setValue(zValue);  }
  public String getEstado() throws Exception {     return pEstado.getValue();  }
  public boolean isNullEstado() throws Exception { return  pEstado.isNull(); } 
  public void setNullToEstado() throws Exception {  pEstado.setNull(); } 
  public void setCompany(String zValue) throws Exception {    pCompany.setValue(zValue);  }
  public String getCompany() throws Exception {     return pCompany.getValue();  }
  public boolean isNullCompany() throws Exception { return  pCompany.isNull(); } 
  public void setNullToCompany() throws Exception {  pCompany.setNull(); } 
  public void setTexto(String zValue) throws Exception {    pTexto.setValue(zValue);  }
  public String getTexto() throws Exception {     return pTexto.getValue();  }
  public boolean isNullTexto() throws Exception { return  pTexto.isNull(); } 
  public void setNullToTexto() throws Exception {  pTexto.setNull(); } 
  public void setTitulo(String zValue) throws Exception {    pTitulo.setValue(zValue);  }
  public String getTitulo() throws Exception {     return pTitulo.getValue();  }
  public String getFechaTitulo() throws Exception {
  	if (isHito())
  		return "(" + getHoraInicio() + ") <hito> " + getTitulo();
  	return "("+getHoraInicio() + "-" + getHoraFin() + ") "+getTitulo();  
  }
  
  public boolean isNullTitulo() throws Exception { return  pTitulo.isNull(); } 
  public void setNullToTitulo() throws Exception {  pTitulo.setNull(); } 
  public void setColor(String zValue) throws Exception {    pColor.setValue(zValue);  }
  public String getColor() throws Exception {     return pColor.getValue();  }
  public boolean isNullColor() throws Exception { return  pColor.isNull(); } 
  public void setNullToColor() throws Exception {  pColor.setNull(); } 
  public void setIdtipoEvento(long zValue) throws Exception {    pIdtipo_evento.setValue(zValue);  }
  public long getIdtipoEvento() throws Exception {     return pIdtipo_evento.getValue();  }
  public boolean isNullIdtipo_evento() throws Exception { return  pIdtipo_evento.isNull(); } 
  public void setNullToIdtipo_evento() throws Exception {  pIdtipo_evento.setNull(); } 

  public void setIdAgenda(long zValue) throws Exception {    pIdAgenda.setValue(zValue);  }
  public long getIdAgenda() throws Exception {     return pIdAgenda.getValue();  }
  public boolean isNullIdAgenda() throws Exception { return  pIdAgenda.isNull(); } 
  public void setNullToIdAgenda() throws Exception {  pIdAgenda.setNull(); } 

  public void setFechaAlarma(Date zValue) throws Exception {    pFechaAlarma.setValue(zValue);  }
  public Date getFechaAlarma() throws Exception {     return pFechaAlarma.getValue();  }
  public boolean isNullFechaAlarma() throws Exception { return  pFechaAlarma.isNull(); } 
  public void setNullToFechaAlarma() throws Exception {  pFechaAlarma.setNull(); } 
  public void setUsuarioAlarma(String zValue) throws Exception {    pUserAlarma.setValue(zValue);  }
  public String getUsuarioAlarma() throws Exception {     return pUserAlarma.getValue();  }
  public void setFechaFin(Date zValue) throws Exception {    pFechaFin.setValue(zValue);  }
  public Date getFechaFin() throws Exception {     return pFechaFin.getValue();  }
  public boolean isNullFechaFin() throws Exception { return  pFechaFin.isNull(); } 
  public void setNullToFechaFin() throws Exception {  pFechaFin.setNull(); } 
  public void setFechaInicio(Date zValue) throws Exception {    pFechaInicio.setValue(zValue);  }
  public Date getFechaInicio() throws Exception {     return pFechaInicio.getValue();  }
  public boolean isNullFechaInicio() throws Exception { return  pFechaInicio.isNull(); } 
  public void setNullToFechaInicio() throws Exception {  pFechaInicio.setNull(); } 
  public void setIdevento(long zValue) throws Exception {    pIdevento.setValue(zValue);  }
  public long getIdevento() throws Exception {     return pIdevento.getValue();  }
  public boolean isNullIdevento() throws Exception { return  pIdevento.isNull(); } 
  public void setNullToIdevento() throws Exception {  pIdevento.setNull(); } 
  public void setRazonEnSuspenso(String zValue) throws Exception {    pRazonEnSuspenso.setValue(zValue);  }
  public String getRazonEnSuspenso() throws Exception {     return pRazonEnSuspenso.getValue();  }

  public void setPorSistema(boolean zValue) throws Exception {    pPorSistema.setValue(zValue);  }
  public boolean getPorSistema() throws Exception {     return pPorSistema.getValue();  }
  public void setAlterado(boolean zValue) throws Exception {    pAlterado.setValue(zValue);  }
  public boolean getAlterado() throws Exception {     return pAlterado.getValue();  }
  public boolean isHito() throws Exception {     return pHito.getValue();  }
  public void setHito(boolean hito) throws Exception {      pHito.setValue(hito);  }
  public void setHasAlarma(boolean zValue) throws Exception {    pHasAlarma.setValue(zValue);  }
  public boolean getHasAlarma() throws Exception {     return pHasAlarma.getValue();  }
  public void setReadedAlarma(boolean zValue) throws Exception {    pReadedAlarma.setValue(zValue);  }
  public boolean getReadedAlarma() throws Exception {     return pReadedAlarma.getValue();  }

  /**
   * Constructor de la Clase
   */
  public BizEvento() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "estado", pEstado );
    this.addItem( "company", pCompany );
    this.addItem( "texto", pTexto );
    this.addItem( "titulo", pTitulo );
    this.addItem( "color", pColor );
    this.addItem( "hito", pHito );
    this.addItem( "id_tipo_evento", pIdtipo_evento );
    this.addItem( "id_agenda", pIdAgenda );
    this.addItem( "has_alarma", pHasAlarma );
    this.addItem( "fecha_alarma", pFechaAlarma );
    this.addItem( "user_alarma", pUserAlarma );
    this.addItem( "readed_alarma", pReadedAlarma );
    this.addItem( "fecha_fin", pFechaFin );
    this.addItem( "fecha_inicio", pFechaInicio );
    this.addItem( "razon_suspenso", pRazonEnSuspenso );
    this.addItem( "id_evento", pIdevento );
    this.addItem( "por_sistema", pPorSistema );
    this.addItem( "alterado", pAlterado );
    this.addItem( "hora_fin", pHoraHasta );
    this.addItem( "hora_inicio", pHoraDesde );
    this.addItem( "fecha_inicio_calc", pFechaInicioCalc );
  }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "id_evento", "Id evento", false, false, 64 );
    this.addFixedItem( KEY, "company", "Company", true, true, 15 );
    this.addFixedItem( FIELD, "texto", "Observaciones", true, false, 4000 );
    this.addFixedItem( FIELD, "hito", "Hito", true, false, 1 );
    this.addFixedItem( FIELD, "estado", "Estado", true, true, 50 );
    this.addFixedItem( FIELD, "titulo", "Titulo", true, true, 4000 );
    this.addFixedItem( FIELD, "color", "Color", true, false, 10 );
    this.addFixedItem( FIELD, "id_tipo_evento", "Id tipo evento", true, false, 64 );
    this.addFixedItem( FIELD, "id_agenda", "Id agenda", true, false, 64 );
    this.addFixedItem( FIELD, "has_alarma", "Alarma", true, false, 1 );
    this.addFixedItem( FIELD, "fecha_alarma", "Fecha alarma", true, false, 18 );
    this.addFixedItem( FIELD, "user_alarma", "Usuario alarma", true, false, 18 );
    this.addFixedItem( FIELD, "readed_alarma", "Leido alarma", true, false, 1 );
    this.addFixedItem( FIELD, "fecha_fin", "Fecha fin", true, false, 18 );
    this.addFixedItem( FIELD, "fecha_inicio", "Fecha inicio", true, true, 18 );
    this.addFixedItem( FIELD, "por_sistema", "Creado Por sistema", true, false, 1 );
    this.addFixedItem( FIELD, "razon_suspenso", "Razon suspenso", true, false, 255 );
    this.addFixedItem( FIELD, "alterado", "Alterado por usuario", true, false, 1 );
    this.addFixedItem( VIRTUAL, "hora_inicio", "Hora desde", true, false, 10 );
    this.addFixedItem( VIRTUAL, "hora_fin", "Hora fin", true, false, 10 );
    this.addFixedItem( VIRTUAL, "fecha_inicio_calc", "Fecha Inicio", true, false, 255 );
  }
  
  
  @Override
  public void createControlProperties() throws Exception {
  	this.addControlsProperty("id_agenda", createControlCombo(GuiTurnos.class,"id_agenda", new JPair<String, String>("company","company") ));
  	this.addControlsProperty("id_tipo_evento", createControlCombo(GuiTipoEventos.class,"id_tipoevento", new JPair<String, String>("company","company") ));
  	this.addControlsProperty("estado", createControlCombo(JWins.createVirtualWinsFromMap(BizEvento.getEstados()),null, null) );
  	super.createControlProperties();
  }
  /**
   * Returns the table name
   */
  public String GetTable() { return "agd_evento"; }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  JRecords<BizEventoParticipante> objParticipantes;
  public JRecords<BizEventoParticipante> getObjParticipantes() throws Exception {
  	if (objParticipantes!=null) return objParticipantes;
  	JRecords<BizEventoParticipante> recs = new JRecords<BizEventoParticipante>(BizEventoParticipante.class);
		recs.setParent(this);
  	if (isNullIdevento()) {
  		recs.setStatic(true);
  		recs.addFilter("company", getCompany());
  		return objParticipantes=recs;
  	}
  	recs.addFilter("id_evento", getIdevento());
		recs.addFilter("company", getCompany());
  	recs.addOrderBy("rol");
  	recs.addOrderBy("id_persona");
		return objParticipantes=recs;
  }

  /**
   * Default read() method
   */
  public boolean read( String zCompany, long zIdevento ) throws Exception { 
    addFilter( "company",  zCompany ); 
    addFilter( "id_evento",  zIdevento ); 
    return read(); 
  } 
  public boolean read(  long zIdevento ) throws Exception { 
    addFilter( "id_evento",  zIdevento ); 
    return read(); 
  } 
  public static final String CANCELADA = "CANCELADA";
  public static final String REALIZADA = "REALIZADA";
  public static final String ACTIVA = "ACTIVA";
  public static final String EN_SUSPENSO = "EN_SUSPENSO";
  public static final String ELIMINADO = "ELIMINADO";
  public static final String EJECUTADO = "EJECUTADO";
   
  static JMap<String,String> estados;
  public static JMap<String,String> getEstados() throws Exception {
  	if (estados!=null) return estados;
  	JMap<String,String> maps = JCollectionFactory.createOrderedMap();
  	maps.addElement(ACTIVA, "Activa");
  	maps.addElement(CANCELADA, "Cancelada");
   	maps.addElement(EJECUTADO, "Ejecutado");
  	maps.addElement(EN_SUSPENSO, "En suspenso");
   	maps.addElement(ELIMINADO, "Eliminado");
   	maps.addElement(REALIZADA, "Realizada");
  	return estados=maps;
  }
  public void execProcessReActivar() throws Exception {
		JExec oExec = new JExec(this, "processReactivar") {

			@Override
			public void Do() throws Exception {
				processReactivar();
			}
		};
		oExec.execute();
	}
  
  public JAct execProcessAction() throws Exception {
			return processAction();

	}
  public String getActionDescription() throws Exception {
		return getLogica().getActionDescription();

}
  
  BizTurno objAgenda;
  public BizTurno getObjAgenda() throws Exception {
  	if (isNullIdAgenda()) return null;
  	if (objAgenda!=null) return objAgenda;
  	BizTurno ev =new BizTurno();
  	ev.dontThrowException(true);
  	if (!ev.read(getIdAgenda())) return null;
  	return objAgenda=ev;
  }
  
  BizTipoEvento objTipoEvento;
  IEventoLogica objLogica;
  
  public BizTipoEvento getObjTipoEvento() throws Exception {
  	if (objTipoEvento!=null) return objTipoEvento;
  	BizTipoEvento ev =new BizTipoEvento();
  	ev.read(getIdtipoEvento());
  	return objTipoEvento=ev;
  }
  public IEventoLogica getLogica() throws Exception  {
  	if (objLogica!=null) return objLogica;
  	objLogica= (IEventoLogica)BizTipoEvento.getLogicasClass(getObjTipoEvento().getLogica()).newInstance();
  	return objLogica;
  }
  public JAct processAction() throws Exception {
  	JAct act = getLogica().process(this);
  	JAct actNext=new JActSubmit(null) {
		  public void submit() throws Exception {
		  	execProcessRealizado();
		  };
  	};
  	if (act==null) return actNext;
 		return act;
  }
  public void execProcessRealizado() throws Exception {
		JExec oExec = new JExec(this, "processRealizado") {

			@Override
			public void Do() throws Exception {
				processRealizado();
			}
		};
		oExec.execute();
	}
  
  public void processRealizado() throws Exception {
  	setEstado(EJECUTADO);
  	setReadedAlarma(true);
  	processUpdate();
		JAgenda.getAlarmaServer().clearCache(BizUsuario.getUsr().getCompany(),BizUsuario.getUsr().GetUsuario());

  }  
  public void execProcessCancelar() throws Exception {
		JExec oExec = new JExec(this, "processCancelar") {

			@Override
			public void Do() throws Exception {
				processCancelar();
			}
		};
		oExec.execute();
	}
  public void processCancelar() throws Exception {
  	setEstado(CANCELADA);
  	setReadedAlarma(true);
  	processUpdate();
		JAgenda.getAlarmaServer().clearCache(BizUsuario.getUsr().getCompany(),BizUsuario.getUsr().GetUsuario());

  }
  public void processReactivar() throws Exception {
  	setEstado(ACTIVA);
  	setReadedAlarma(false);
  	processUpdate();
		JAgenda.getAlarmaServer().clearCache(BizUsuario.getUsr().getCompany(),BizUsuario.getUsr().GetUsuario());
  }
  public boolean canEjecutar() throws Exception {
  	return getEstado().equals(ACTIVA);
  }
  
  String organizador=null;
  
  public String getOrganizador() {
		return organizador;
	}
	public void setOrganizador(String organizador) {
		this.organizador = organizador;
	}
	public BizUsuario getObjOrganizador() throws Exception {
		BizUsuario u = new BizUsuario();
		u.Read(organizador);
		return u;
	}
	@Override
  public void processInsert() throws Exception {
  	if (pCompany.isNull()) setCompany(BizUsuario.getUsr().getCompany());
  	if (pEstado.isNull())	setEstado(EN_SUSPENSO);
  	if (pHasAlarma.isNull())	setHasAlarma(false);
  	if (pAlterado.isNull())	setAlterado(false);
  	if (pReadedAlarma.isNull()) setReadedAlarma(false);
  	if (pUserAlarma.isNull()) setUsuarioAlarma(BizUsuario.getUsr().GetUsuario());
  	if (isHito())		setFechaFin(getFechaInicio());
  	super.processInsert();
  	setIdevento(getIdentity("id_evento"));

  	if (organizador==null && BizUsuario.getUsr()==null)
  		organizador =  BizUsuario.getUsr().GetUsuario();
  	if (organizador!=null/* && !isHito()*/) {
	  	BizEventoParticipante p = new BizEventoParticipante();
	  	p.setCompany(getCompany());
	  	p.setIdevento(getIdevento());
	  	p.setIdpersona(getObjOrganizador().getIdPersona());
	  	p.setEstado(BizEventoParticipante.NO_INVITADO);
	  	p.processInsert();
  	}  	
  	
  	getObjParticipantes().processProcessTable(this, true);;

  	BizEventoHistoria h = new BizEventoHistoria();
		h.setIdevento(getIdevento());
		h.setUsuario(BizUsuario.getUsr().GetUsuario());
		h.setEstado(getEstado());
		h.setCompany(getCompany());
		h.setTexto("Se creó evento ["+getTitulo()+ "] en estado ["+getEstado()+"]" );
		h.processInsert();

		if (getHasAlarma()) JAgenda.getAlarmaServer().clearCache(BizUsuario.getUsr().getCompany(),getUsuarioAlarma());

  }
	
  @Override
  public void processDelete() throws Exception {
  		JRecords<BizEventoParticipante> pp = new JRecords<BizEventoParticipante>(BizEventoParticipante.class);
  		pp.addFilter("company", getCompany());
  		pp.addFilter("id_evento", getIdevento());
  		pp.toStatic();
  		pp.processDeleteAll();

  		JRecords<BizEventoHistoria> ev = new JRecords<BizEventoHistoria>(BizEventoHistoria.class);
  		ev.addFilter("company", getCompany());
  		ev.addFilter("id_evento", getIdevento());
  		ev.toStatic();
  		ev.processDeleteAll();

  		setEstado(ELIMINADO);
			setAlterado(true);
			super.processUpdate();

			BizEventoHistoria h = new BizEventoHistoria();
			h.setIdevento(getIdevento());
			h.setUsuario(BizUsuario.getUsr().GetUsuario());
			h.setCompany(getCompany());
			h.setEstado(getEstado());
			h.setTexto("Se marcó como elimiando" );
			h.processInsert();
			if (getHasAlarma()) JAgenda.getAlarmaServer().clearCache(BizUsuario.getUsr().getCompany(),getUsuarioAlarma());
  }
	public void execProcessRealDelete() throws Exception {
		JExec oExec = new JExec(this, "processRealDelete") {

			@Override
			public void Do() throws Exception {
				processRealDelete();
			}
		};
		oExec.execute();
	}
  public void processRealDelete() throws Exception {
  		JRecords<BizEventoParticipante> pp = new JRecords<BizEventoParticipante>(BizEventoParticipante.class);
  		pp.addFilter("company", getCompany());
  		pp.addFilter("id_evento", getIdevento());
  		pp.toStatic();
  		pp.processDeleteAll();

  		JRecords<BizEventoHistoria> ev = new JRecords<BizEventoHistoria>(BizEventoHistoria.class);
  		ev.addFilter("company", getCompany());
  		ev.addFilter("id_evento", getIdevento());
  		ev.toStatic();
  		ev.processDeleteAll();

			super.processDelete();

			if (getHasAlarma()) JAgenda.getAlarmaServer().clearCache(BizUsuario.getUsr().getCompany(),getUsuarioAlarma());
  }
  @Override
  public void processUpdate() throws Exception {
  	if (isHito())
  		setFechaFin(getFechaInicio());
    
  	JRecord oBD = getPreInstance();
		String oldEstado = oBD.getProp("estado").toString();
		String fecha = oBD.getProp("fecha_inicio").toString();
		String texto = "Se modificó ";
		if (!oldEstado.equals(getEstado())) {
			texto+="estado ["+oldEstado+"] a ["+getEstado()+"] ";
		}
		if (!fecha.equals(getProp("fecha_inicio").toString())) {
			texto+="fecha Inicio["+fecha+"] a ["+getProp("fecha_inicio").toString()+"] ";
		}

  	if (organizador==null && BizUsuario.getUsr()==null)
  		organizador =  BizUsuario.getUsr().GetUsuario();
  	if (organizador!=null/* && !isHito()*/) {
    	BizEventoParticipante pu = new BizEventoParticipante();
    	pu.dontThrowException(true);
    	if (pu.read(getObjOrganizador().getIdPersona(), getIdevento(), getCompany())) {
  	  	pu.setCompany(getCompany());
  	  	pu.setIdevento(getIdevento());
  	  	pu.setIdpersona(getObjOrganizador().getIdPersona());
  	  	pu.setForzeAllRecordsToUpdate(true);
  	  	pu.processUpdate();
    	}
    	else {
    		BizEventoParticipante p = new BizEventoParticipante();
		  	p.setCompany(getCompany());
		  	p.setIdevento(getIdevento());
		  	p.setIdpersona(getObjOrganizador().getIdPersona());
		  	p.setEstado(BizEventoParticipante.NO_INVITADO);
		  	p.processInsert();
    	}
  	}  
		
  	getObjParticipantes().processProcessTable(this, true);;
		BizEventoHistoria h = new BizEventoHistoria();
		h.setIdevento(getIdevento());
		h.setUsuario(BizUsuario.getUsr().GetUsuario());
		h.setCompany(getCompany());
		h.setEstado(getEstado());
		h.setTexto(texto);
		h.processInsert();

		setAlterado(true);
  	super.processUpdate();
		if (getHasAlarma()) JAgenda.getAlarmaServer().clearCache(BizUsuario.getUsr().getCompany(),getUsuarioAlarma());
  }
  
  public String getHoraInicio() throws Exception {
  	Calendar d = Calendar.getInstance();
  	d.setTime(getFechaInicio());
  	return JTools.LPad(""+d.get(Calendar.HOUR_OF_DAY),2,"0")+":"+JTools.LPad(""+d.get(Calendar.MINUTE),2,"0");
  }
  public String getHoraFin() throws Exception {
  	Calendar d = Calendar.getInstance();
  	d.setTime(getFechaFin());
  	return JTools.LPad(""+d.get(Calendar.HOUR_OF_DAY),2,"0")+":"+JTools.LPad(""+d.get(Calendar.MINUTE),2,"0");
  }
  public String getFechaInicioCalc() throws Exception {
	  	String out = JDateTools.DateToString(getFechaInicio());
	  	if (getEstado().equals(EN_SUSPENSO)) out = getRazonEnSuspenso();
	  	return out;

  }
  
	public void execProcessMarkReaded() throws Exception {
		if (this.getReadedAlarma())
			return;
		JExec oExec = new JExec(this, "processUpdate") {

			@Override
			public void Do() throws Exception {
				processMarkReaded();
			}
		};
		oExec.execute();
	}

	public void processMarkReaded() throws Exception {
		this.setReadedAlarma(true);
		this.processUpdate();
		BizEventoHistoria h = new BizEventoHistoria();
		h.setIdevento(getIdevento());
		h.setUsuario(BizUsuario.getUsr().GetUsuario());
		h.setCompany(getCompany());
		h.setEstado(getEstado());
		h.setTexto("Alarma informada");
		h.processInsert();
		JMail.getMailServer().clearCache(getUsuarioAlarma());
	}
}
