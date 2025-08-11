package pss.common.event.action;

import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.codec.net.URLCodec;

import pss.JPath;
import pss.common.event.action.history.BizSqlEventHistory;
import pss.common.event.mailing.BizMailingPersona;
import pss.common.event.sql.BizSqlEvent;
import pss.common.layoutWysiwyg.BizPlantilla;
import pss.common.mail.mailing.BizMail;
import pss.common.mail.message.BizMessage;
import pss.common.regions.company.BizCompany;
import pss.common.security.mail.BizUsrMailSender;
import pss.core.services.JExec;
import pss.core.services.fields.JBoolean;
import pss.core.services.fields.JDateTime;
import pss.core.services.fields.JHour;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JFilterMap;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.JDateTools;
import pss.core.tools.PssLogger;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JMap;

public class BizSqlEventAction extends JRecord {

	public final static String SOLOUNAVEZ = "SOLOUNAVEZ";
	public final static String LIMITE = "LIMITE";
	public final static String DIARIO = "DIARIO";
	public final static String SEMANAL = "SEMANAL";
	public final static String MENSUAL = "MENSUAL";
	public final static String ANUAL = "ANUAL";

	public final static String EMAIL = "EMAIL";
	public final static String NOTIF = "NOTIF";
	public final static String SMS = "SMS";
	public final static String AVISO = "AVISO";
	public final static String URL = "URL";
	public final static String MAILING = "MAILING";
	public final static String DOWNLOAD = "DOWNLOAD";

	public final static String PANTALLA = "PANTALLA";
	public final static String PDF = "PDF";
	public final static String CSV = "CSV";
	public final static String EXCEL = "EXCEL";

	private JString pCompany = new JString();
  private JString pUsuario = new JString();
  private JString pCorreo = new JString();
  private JString pMailing = new JString();
  private JString pAction = new JString();
  private JString pFormato = new JString();
 private JString pTelefono = new JString();
  private JLong pIdaction = new JLong();
  private JBoolean pAdjunto = new JBoolean();
  private JString pClassEvento = new JString();
  private JString pDescripcion = new JString();
  private JString pIdevento = new JString();
  private JString pParametros = new JString();
  private JString pExtradata = new JString();
  private JLong pIdplantilla = new JLong();
  private JString pTipoPeriodicidad = new JString();
  private JDateTime pFechaUltimoEnvio = new JDateTime();
  private JString pFundamento = new JString();

  private JBoolean pSemanaTodos = new JBoolean();
  private JBoolean pDiasTodos = new JBoolean();
  private JBoolean pMesTodos = new JBoolean();

  //diario
  private JHour pHora= new JHour();
  // semanal
  private JBoolean pLunes = new JBoolean();
  private JBoolean pMartes = new JBoolean();
  private JBoolean pMiercoles = new JBoolean();
  private JBoolean pJueves = new JBoolean();
  private JBoolean pViernes = new JBoolean();
  private JBoolean pSabado = new JBoolean();
  private JBoolean pDomingo = new JBoolean();
  // mensual
  private JBoolean pd1 = new JBoolean();
  private JBoolean pd2 = new JBoolean();
  private JBoolean pd3 = new JBoolean();
  private JBoolean pd4 = new JBoolean();
  private JBoolean pd5 = new JBoolean();
  private JBoolean pd6 = new JBoolean();
  private JBoolean pd7 = new JBoolean();
  private JBoolean pd8 = new JBoolean();
  private JBoolean pd9 = new JBoolean();
  private JBoolean pd10 = new JBoolean();
  private JBoolean pd11 = new JBoolean();
  private JBoolean pd12 = new JBoolean();
  private JBoolean pd13 = new JBoolean();
  private JBoolean pd14 = new JBoolean();
  private JBoolean pd15 = new JBoolean();
  private JBoolean pd16 = new JBoolean();
  private JBoolean pd17 = new JBoolean();
  private JBoolean pd18 = new JBoolean();
  private JBoolean pd19 = new JBoolean();
  private JBoolean pd20 = new JBoolean();
  private JBoolean pd21 = new JBoolean();
  private JBoolean pd22 = new JBoolean();
  private JBoolean pd23 = new JBoolean();
  private JBoolean pd24 = new JBoolean();
  private JBoolean pd25 = new JBoolean();
  private JBoolean pd26 = new JBoolean();
  private JBoolean pd27 = new JBoolean();
  private JBoolean pd28 = new JBoolean();
  private JBoolean pd29 = new JBoolean();
  private JBoolean pd30 = new JBoolean();
  private JBoolean pd31 = new JBoolean();
  // anual 
  private JBoolean pm1 = new JBoolean();
  private JBoolean pm2 = new JBoolean();
  private JBoolean pm3 = new JBoolean();
  private JBoolean pm4 = new JBoolean();
  private JBoolean pm5 = new JBoolean();
  private JBoolean pm6 = new JBoolean();
  private JBoolean pm7 = new JBoolean();
  private JBoolean pm8 = new JBoolean();
  private JBoolean pm9 = new JBoolean();
  private JBoolean pm10 = new JBoolean();
  private JBoolean pm11 = new JBoolean();
  private JBoolean pm12 = new JBoolean();
  
  
  private JString pDescrAction = new JString() {
		public void preset() throws Exception {
			pDescrAction.setValue(getDescripcionAccion());
		};
	};

  private JString pDescrData = new JString() {
		public void preset() throws Exception {
			pDescrData.setValue(getDescripcionData());
		};
	};

  private JString pMensaje = new JString();
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  public void setUsuario(String zValue) throws Exception {    pUsuario.setValue(zValue);  }
  public String getUsuario() throws Exception {     return pUsuario.getValue();  }
  public boolean isNullUsuario() throws Exception { return  pUsuario.isNull(); } 
  public void setNullToUsuario() throws Exception {  pUsuario.setNull(); } 
  public void setTelefono(String zValue) throws Exception {    pTelefono.setValue(zValue);  }
  public String getTelefono() throws Exception {     return pTelefono.getValue();  }
  public void setNullToTelefono() throws Exception {  pTelefono.setNull(); } 

  public void setCorreo(String zValue) throws Exception {    pCorreo.setValue(zValue);  }
  public String getCorreo() throws Exception {    
  	if (pCorreo.getValue().equals("default"))
  		return (BizCompany.getObjCompany(getCompany())).getObjBusiness().getEmailDefault();
  	return pCorreo.getValue();  
  	
  }
  public void setMailing(String zValue) throws Exception {    pMailing.setValue(zValue);  }
  public String getMailing() throws Exception {     return pMailing.getValue();  }
  public boolean isNullCorreo() throws Exception { return  pCorreo.isNull(); } 
  public void setNullToCorreo() throws Exception {  pCorreo.setNull(); } 
  public void setAction(String zValue) throws Exception {    pAction.setValue(zValue);  }
  public String getAction() throws Exception {     return pAction.getValue();  }
  public boolean isNullAction() throws Exception { return  pAction.isNull(); } 
  public void setNullToAction() throws Exception {  pAction.setNull(); } 
  public void setIdaction(long zValue) throws Exception {    pIdaction.setValue(zValue);  }
  public long getIdaction() throws Exception {     return pIdaction.getValue();  }
  public boolean isNullIdaction() throws Exception { return  pIdaction.isNull(); } 
  public void setNullToIdaction() throws Exception {  pIdaction.setNull(); } 
  public void setAdjunto(boolean zValue) throws Exception {    pAdjunto.setValue(zValue);  }
  public boolean isAdjunto() throws Exception {     return pAdjunto.getValue();  }
  public void setClassevento(String zValue) throws Exception {    pClassEvento.setValue(zValue);  }
  public String getClassevento() throws Exception {     return pClassEvento.getValue();  }
  public boolean isNullClassevento() throws Exception { return  pClassEvento.isNull(); } 
  public void setNullToClassevento() throws Exception {  pClassEvento.setNull(); } 
  public void setIdevento(String zValue) throws Exception {    pIdevento.setValue(zValue);  }
  public String getIdevento() throws Exception {     return pIdevento.getValue();  }
  public boolean isNullIdevento() throws Exception { return  pIdevento.isNull(); } 
  public void setNullToIdevento() throws Exception {  pIdevento.setNull(); } 
  public void setParametros(String zValue) throws Exception {    pParametros.setValue(zValue);  }
  public String getParametros() throws Exception {     return pParametros.getValue();  }
  public boolean isNullParametros() throws Exception { return  pParametros.isNull(); } 
  public void setNullToParametros() throws Exception {  pParametros.setNull(); } 
  public void setParamExtraDatos(String zValue) throws Exception {    pExtradata.setValue(zValue);  }
  public String getParamExtraDatos() throws Exception {     return pExtradata.getValue();  }
  public boolean isNullExtraDatos() throws Exception { return  pExtradata.isNull(); } 
  public void setNullToExtraDatos() throws Exception {  pExtradata.setNull(); } 
  public void setCompany(String zValue) throws Exception {    pCompany.setValue(zValue);  }
  public String getCompany() throws Exception {     return pCompany.getValue();  }
  public void setIdplantilla(long zValue) throws Exception {    pIdplantilla.setValue(zValue);  }
  public long getIdplantilla() throws Exception {     return pIdplantilla.getValue();  }
  public void setNullToIdPlantilla() throws Exception {  pIdplantilla.setNull(); } 
  public boolean isNullIdPlantilla() throws Exception { return  pIdplantilla.isNull(); } 

  public void setDescripcion(String zValue) throws Exception {    pDescripcion.setValue(zValue);  }
  public String getDescripcion() throws Exception {     return pDescripcion.getValue();  }

  public void setTipoPeriodicidad(String zValue) throws Exception {    pTipoPeriodicidad.setValue(zValue);  }
  public String getTipoPeriodicidad() throws Exception {     return pTipoPeriodicidad.getValue();  }

  public void setFechaUltimoEnvio(Date zValue) throws Exception {    pFechaUltimoEnvio.setValue(zValue);  }
  public Date getFechaUltimoEnvio() throws Exception {     return pFechaUltimoEnvio.getValue();  }

  public void setUltimoDia(boolean zValue) throws Exception {    pd31.setValue(zValue);  }
  public boolean getUltimoDia() throws Exception {     return pd31.getValue();  }

  public void setViernes(boolean zValue) throws Exception {    pViernes.setValue(zValue);  }
  public boolean getViernes() throws Exception {     return pViernes.getValue();  }
  public void setHora(String zValue) throws Exception {    pHora.setValue(zValue);  }
  public String getHora() throws Exception {     return pHora.getValue();  }

  public void setFundamento(String zValue) throws Exception {    pFundamento.setValue(zValue);  }
  public String getFundamento() throws Exception {     return pFundamento.getValue();  }

  public void setFormato(String zValue) throws Exception {    pFormato.setValue(zValue);  }

  public void setMensaje(String zValue) throws Exception {    pMensaje.setValue(zValue);  }
  public String getMensaje() throws Exception {     return pMensaje.getValue();  }

  /**
   * Constructor de la Clase
   */
  public BizSqlEventAction() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "company", pCompany );
    this.addItem( "class_evento", pClassEvento );
    this.addItem( "id_evento", pIdevento );
    this.addItem( "parametros", pParametros );
    this.addItem( "extradata", pExtradata );
    this.addItem( "id_action", pIdaction );
    this.addItem( "formato", pFormato );
    this.addItem( "usuario", pUsuario );
    this.addItem( "correo", pCorreo );
    this.addItem( "mailing", pMailing );
    this.addItem( "telefono", pTelefono );
    this.addItem( "action", pAction );
    this.addItem( "descripcion", pDescripcion );
    this.addItem( "tipo_periodicidad", pTipoPeriodicidad );
    this.addItem( "adjunto", pAdjunto );
    this.addItem( "fecha_ultimo", pFechaUltimoEnvio );

    this.addItem( "dias_todos", pDiasTodos );
    this.addItem( "semana_todos", pSemanaTodos );
    this.addItem( "mes_todos", pMesTodos );

    this.addItem( "hora", pHora );
    this.addItem( "lunes", pLunes );
    this.addItem( "martes", pMartes );
    this.addItem( "miercoles", pMiercoles );
    this.addItem( "jueves", pJueves );
    this.addItem( "viernes", pViernes );
    this.addItem( "sabado", pSabado );
    this.addItem( "domingo", pDomingo );

    this.addItem( "d1", pd1 );
    this.addItem( "d2", pd2 );
    this.addItem( "d3", pd3 );
    this.addItem( "d4", pd4 );
    this.addItem( "d5", pd5 );
    this.addItem( "d6", pd6 );
    this.addItem( "d7", pd7 );
    this.addItem( "d8", pd8 );
    this.addItem( "d9", pd9 );
    this.addItem( "d10", pd10 );
    this.addItem( "d11", pd11 );
    this.addItem( "d12", pd12 );
    this.addItem( "d13", pd13 );
    this.addItem( "d14", pd14 );
    this.addItem( "d15", pd15 );
    this.addItem( "d16", pd16 );
    this.addItem( "d17", pd17 );
    this.addItem( "d18", pd18 );
    this.addItem( "d19", pd19 );
    this.addItem( "d20", pd20 );
    this.addItem( "d21", pd21 );
    this.addItem( "d22", pd22 );
    this.addItem( "d23", pd23 );
    this.addItem( "d24", pd24 );
    this.addItem( "d25", pd25 );
    this.addItem( "d26", pd26 );
    this.addItem( "d27", pd27 );
    this.addItem( "d28", pd28 );
    this.addItem( "d29", pd29 );
    this.addItem( "d30", pd30 );
    this.addItem( "d31", pd31 );

    this.addItem( "m1", pm1 );
    this.addItem( "m2", pm2 );
    this.addItem( "m3", pm3 );
    this.addItem( "m4", pm4 );
    this.addItem( "m5", pm5 );
    this.addItem( "m6", pm6 );
    this.addItem( "m7", pm7 );
    this.addItem( "m8", pm8 );
    this.addItem( "m9", pm9 );
    this.addItem( "m10", pm10 );
    this.addItem( "m11", pm11 );
    this.addItem( "m12", pm12 );

    this.addItem( "fundamento", pFundamento );
    this.addItem( "mensaje", pMensaje );
    this.addItem( "descr_action", pDescrAction );
    this.addItem( "descr_data", pDescrData );
    this.addItem( "id_plantilla", pIdplantilla );
  }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "company", "Compania", true, true, 50 );
    this.addFixedItem( KEY, "id_action", "Id action", false, false, 64 );
    this.addFixedItem( KEY, "class_evento", "class evento", true, true, 200 );
    this.addFixedItem( KEY, "id_evento", "Id evento", true, true, 64 );
    this.addFixedItem( FIELD, "parametros", "Parametros", true, false, 2000 );
    this.addFixedItem( FIELD, "extradata", "Extra datos", true, false, 100000 );
    this.addFixedItem( FIELD, "formato", "formato", true, false, 50 );
    this.addFixedItem( FIELD, "usuario", "Usuario", true, false, 50 );
    this.addFixedItem( FIELD, "descripcion", "Descripcion", true, false, 250 );
    this.addFixedItem( FIELD, "correo", "Correo", true, false, 250 );
    this.addFixedItem( FIELD, "mailing", "Mailing", true, false, 250 );
    this.addFixedItem( FIELD, "telefono", "Telefono", true, false, 250 );
    this.addFixedItem( FIELD, "action", "Action", true, true, 50 );
    this.addFixedItem( FIELD, "fecha_ultimo", "Fecha ultimo envio", true, false, 50 );
    this.addFixedItem( FIELD, "id_plantilla", "Plantilla", true, false, 18 );
    this.addFixedItem( FIELD, "tipo_periodicidad", "Tipo periodicidad", true, false, 50 );
    this.addFixedItem( FIELD, "adjunto", "Adjunto", true, false, 1 );
    this.addFixedItem( FIELD, "hora", "Hora", true, false, 10 );
    this.addFixedItem( VIRTUAL, "mensaje", "mensaje", true, false, 2000 );
    this.addFixedItem( VIRTUAL, "fundamento", "fundamento", true, false, 2000 );
    this.addFixedItem( VIRTUAL, "dias_todos", "dias todos", true, false, 1 );
    this.addFixedItem( VIRTUAL, "semana_todos", "semana todos", true, false, 1 );
    this.addFixedItem( VIRTUAL, "mes_todos", "mes todos", true, false, 1 );
    this.addFixedItem( FIELD, "lunes", "Lunes", true, false, 1 );
    this.addFixedItem( FIELD, "martes", "Martes", true, false, 1 );
    this.addFixedItem( FIELD, "miercoles", "Miercoles", true, false, 1 );
    this.addFixedItem( FIELD, "jueves", "Jueves", true, false, 1 );
    this.addFixedItem( FIELD, "viernes", "Viernes", true, false, 1 );
    this.addFixedItem( FIELD, "sabado", "Sabado", true, false, 1 );
    this.addFixedItem( FIELD, "domingo", "Domingo", true, false, 1 );
    this.addFixedItem( FIELD, "d1", "Dia 1", true, false, 1 );
    this.addFixedItem( FIELD, "d2", "Dia 2", true, false, 1 );
    this.addFixedItem( FIELD, "d3", "Dia 3", true, false, 1 );
    this.addFixedItem( FIELD, "d4", "Dia 4", true, false, 1 );
    this.addFixedItem( FIELD, "d5", "Dia 5", true, false, 1 );
    this.addFixedItem( FIELD, "d6", "Dia 6", true, false, 1 );
    this.addFixedItem( FIELD, "d7", "Dia 7", true, false, 1 );
    this.addFixedItem( FIELD, "d8", "Dia 8", true, false, 1 );
    this.addFixedItem( FIELD, "d9", "Dia 9", true, false, 1 );
    this.addFixedItem( FIELD, "d10", "Dia 10", true, false, 1 );
    this.addFixedItem( FIELD, "d11", "Dia 11", true, false, 1 );
    this.addFixedItem( FIELD, "d12", "Dia 12", true, false, 1 );
    this.addFixedItem( FIELD, "d13", "Dia 13", true, false, 1 );
    this.addFixedItem( FIELD, "d14", "Dia 14", true, false, 1 );
    this.addFixedItem( FIELD, "d15", "Dia 15", true, false, 1 );
    this.addFixedItem( FIELD, "d16", "Dia 16", true, false, 1 );
    this.addFixedItem( FIELD, "d17", "Dia 17", true, false, 1 );
    this.addFixedItem( FIELD, "d18", "Dia 18", true, false, 1 );
    this.addFixedItem( FIELD, "d19", "Dia 19", true, false, 1 );
    this.addFixedItem( FIELD, "d20", "Dia 20", true, false, 1 );
    this.addFixedItem( FIELD, "d21", "Dia 21", true, false, 1 );
    this.addFixedItem( FIELD, "d22", "Dia 22", true, false, 1 );
    this.addFixedItem( FIELD, "d23", "Dia 23", true, false, 1 );
    this.addFixedItem( FIELD, "d24", "Dia 24", true, false, 1 );
    this.addFixedItem( FIELD, "d25", "Dia 25", true, false, 1 );
    this.addFixedItem( FIELD, "d26", "Dia 26", true, false, 1 );
    this.addFixedItem( FIELD, "d27", "Dia 27", true, false, 1 );
    this.addFixedItem( FIELD, "d28", "Dia 28", true, false, 1 );
    this.addFixedItem( FIELD, "d29", "Dia 29", true, false, 1 );
    this.addFixedItem( FIELD, "d30", "Dia 30", true, false, 1 );
    this.addFixedItem( FIELD, "d31", "Dia 31", true, false, 1 );
    this.addFixedItem( FIELD, "m1", "Enero", true, false, 1 );
    this.addFixedItem( FIELD, "m2", "Febrero", true, false, 1 );
    this.addFixedItem( FIELD, "m3", "Marzo", true, false, 1 );
    this.addFixedItem( FIELD, "m4", "Abril", true, false, 1 );
    this.addFixedItem( FIELD, "m5", "Mayo", true, false, 1 );
    this.addFixedItem( FIELD, "m6", "Junio", true, false, 1 );
    this.addFixedItem( FIELD, "m7", "Julio", true, false, 1 );
    this.addFixedItem( FIELD, "m8", "Agosto", true, false, 1 );
    this.addFixedItem( FIELD, "m9", "Septiembre", true, false, 1 );
    this.addFixedItem( FIELD, "m10", "Octubre", true, false, 1 );
    this.addFixedItem( FIELD, "m11", "Noviembre", true, false, 1 );
    this.addFixedItem( FIELD, "m12", "Diciembre", true, false, 1 );

    
    this.addFixedItem( VIRTUAL, "descr_action", "Accion", true, true, 50 );
    this.addFixedItem( VIRTUAL, "descr_data", "Fuente", true, true, 50 );
  }
  /**
   * Returns the table name
   */
  public String GetTable() { return "evt_sqleventos_action"; }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  public String getDescripcionAccion() throws Exception {     
  	return getActions().getElement(getAction());  
  }

  public String getDescripcionData() throws Exception { 
  	if (getObjSqlEvent()==null) return "";
  	return getObjSqlEvent().getDescripcion();  
  }
  IActionData objEventAct;
  public IActionData getObjSqlEvent() throws Exception {
  	if (objEventAct!=null) return objEventAct;
  	if (pClassEvento.isNull()) pClassEvento.setValue(BizSqlEvent.class.getCanonicalName()); // compatibilidad
  	if (pTipoPeriodicidad.isNull()) pTipoPeriodicidad.setValue(BizSqlEventAction.LIMITE);
  	IActionData data = (IActionData)Class.forName(getClassevento()).newInstance();
  	if (!data.read(getCompany(),getIdevento(), getFilterMap())) return null;
  	
  	return objEventAct=data;
  }
  
  public void clearObjSqlEvent () {
	  objEventAct=null;
  }

	public BizSqlEventHistory execProcessExecute(final JFilterMap a) throws Exception {
		JExec oExec = new JExec(this, "processExecute") {
			@Override
			public void Do() throws Exception {
				setOutput(processExecute(a));
			}
		};
		oExec.execute();
		return (BizSqlEventHistory)oExec.getOutput();
	}
  public BizSqlEventHistory processExecute(JFilterMap a) throws Exception {
  	if (getTipoPeriodicidad().equals(SOLOUNAVEZ) || getTipoPeriodicidad().equals(LIMITE)) {
	  	BizSqlEventAction oldAction = new BizSqlEventAction();
	  	oldAction.addFilter("company", getCompany());
	  	oldAction.addFilter("class_evento", getClassevento());
	  	oldAction.addFilter("id_evento", getIdevento());
	  	oldAction.addFilter("tipo_periodicidad", getTipoPeriodicidad());
	  	oldAction.addFilter("id_plantilla", getIdplantilla());
	  	
		  	oldAction.dontThrowException(true);
		  	if (!oldAction.read()) {
		    	super.processInsert();
		    	setIdaction(getIdentity("id_action"));
		  	} else {
		  		setIdaction(oldAction.getIdaction());
		  		processUpdate();
		  	}
  	} else {
    	super.processInsert();
    	setIdaction(getIdentity("id_action"));
  	}
  	
  	if (getTipoPeriodicidad().equals(SOLOUNAVEZ)) {
  		BizSqlEventHistory hist=getObjSqlEvent().generarAviso(a,this,false);
 			hist.processInsert();
    	return hist;
  	}
  	return null;
  }
//  JFilterMap filters;


  public JFilterMap getFilterMap() throws Exception {
		return JFilterMap.unserialize(getParametros());
	}


//	public void setFilterMap(JFilterMap action) {
//		this.filters = action;
//	}
  public String getPreviewMensaje() throws Exception {
  	if (getAction().equals("")) return "";
		if (isAccionMAILING()) return "<div style=\"align:center\"><B>Mailing no presenta vista previa</B></div>";
//		if (!isAdjunto() && !isAccionURL())return "<div style=\"align:center\"><B>Solo se pre-visualiza el mail con adjunto</B></div>"; //RJL
		BizSqlEventHistory hist=getObjSqlEvent().generarAviso(getFilterMap(),this,true);
  	String msg= hist.getMensaje();
  	if (msg.length()<500000) return msg;
  	msg=msg.substring(0,msg.indexOf(">",500000)+1)+"...<br><b> Mensaje cortado en vista previa</b>";
		return msg;
  }
  @Override
  public void processInsert() throws Exception {
  	processExecute(getFilterMap());
  	
  }


  /**
   * Default read() method
   */
  public boolean read( long zIdaction ) throws Exception { 
    addFilter( "id_action",  zIdaction ); 
    return read(); 
  } 

  public boolean read( String company,String clase, String zIdevento, String tipo ) throws Exception { 
  	addFilter("company", company);
  	addFilter("class_evento", clase);
  	addFilter("id_evento", zIdevento);
  	addFilter("tipo_periodicidad", tipo);
  	return read(); 
  } 

  static JMap<String,String> gActions;
  public static JMap<String,String> getActions() throws Exception {
  	if (gActions!=null) return gActions;
  	gActions=JCollectionFactory.createOrderedMap();
  	gActions.addElement(EMAIL,   "Enviar eMail");
  	gActions.addElement(URL,     "Publicar URL");
  	gActions.addElement(MAILING, "Mailing");
		gActions.addElement(AVISO,   "Aviso dentro del sistema");
		gActions.addElement(NOTIF,   "Notificación del celular");
  	gActions.addElement(DOWNLOAD, "Descarga");
  	return gActions;
  }
  static JMap<String,String> gTipoSalida;
  public static JMap<String,String> getTipoSalidas() throws Exception {
  	if (gTipoSalida!=null) return gTipoSalida;
  	gTipoSalida=JCollectionFactory.createOrderedMap();
  	gTipoSalida.addElement(PANTALLA,   "Como se ve en la pantalla");
  	gTipoSalida.addElement(PDF,   "Reporte");
  	gTipoSalida.addElement(EXCEL,   "Planilla de cálculo");
  	gTipoSalida.addElement(CSV,   "CSV");
  	return gTipoSalida;
  }
  static JMap<String,String> gPeriodicidad;
  public static JMap<String,String> getPeriodicidad() throws Exception {
  	if (gPeriodicidad!=null) return gPeriodicidad;
  	gPeriodicidad=JCollectionFactory.createOrderedMap();
  	gPeriodicidad.addElement(SOLOUNAVEZ, "Solo esta vez");
  	gPeriodicidad.addElement(LIMITE, "Cuando se supere un limite");
  	gPeriodicidad.addElement(DIARIO, "Diario");
  	gPeriodicidad.addElement(SEMANAL, "Semanal");
  	gPeriodicidad.addElement(MENSUAL, "Mensual");
  	gPeriodicidad.addElement(ANUAL, "Anual");
  	return gPeriodicidad;
  }
  static JMap<String,String> gPeriodicidadRestringida;
  public static JMap<String,String> getPeriodicidadRestringida() throws Exception {
  	if (gPeriodicidadRestringida!=null) return gPeriodicidadRestringida;
  	gPeriodicidadRestringida=JCollectionFactory.createOrderedMap();
  	gPeriodicidadRestringida.addElement(LIMITE, "Cuando se supere un limite");
  	gPeriodicidadRestringida.addElement(DIARIO, "Diario");
  	gPeriodicidadRestringida.addElement(SEMANAL, "Semanal");
  	gPeriodicidadRestringida.addElement(MENSUAL, "Mensual");
  	gPeriodicidadRestringida.addElement(ANUAL, "Anual");
  	return gPeriodicidadRestringida;
  }
  
  public boolean isOutPantalla() throws Exception {return pFormato.getValue().equals(PANTALLA);}
  public boolean isOutPDF() throws Exception {return pFormato.getValue().equals(PDF);}
  public boolean isOutEXCEL() throws Exception {return pFormato.getValue().equals(EXCEL);}
  public boolean isOutCSV() throws Exception {return pFormato.getValue().equals(CSV);}
  
  public boolean isAccionURL() throws Exception {return pAction.getValue().equals(URL);}
  public boolean isAccionEMAIL() throws Exception {return pAction.getValue().equals(EMAIL);}
  public boolean isAccionMAILING() throws Exception {return pAction.getValue().equals(MAILING);}
  public boolean isAccionDOWNLOAD() throws Exception {return pAction.getValue().equals(DOWNLOAD);}
  public boolean isAccionAVISO() throws Exception {return pAction.getValue().equals(AVISO);}
  public boolean isAccionNOTIF() throws Exception {return pAction.getValue().equals(NOTIF);}

  public void clean() throws Exception {
  	objPlantilla=null;
  };
  BizPlantilla objPlantilla;
  public BizPlantilla getObjPlantilla() throws Exception {
  	if (objPlantilla!=null) return objPlantilla;
  	
  	if (isAccionURL()) {
    	BizPlantilla p = BizCompany.getObjPlantilla(getCompany(), "sys_qr");
    	if (p==null) return null;
    	return objPlantilla = p;
  	}
  	if (isAdjunto()) {
    	BizPlantilla p = BizCompany.getObjPlantilla(getCompany(), "sys_emailadj");
    	if (p==null) return null;
    	return objPlantilla = p;
  	}
  	if (getIdplantilla()==0) {
    	BizPlantilla p = BizCompany.getObjPlantilla(getCompany(), "sys_email");
    	if (p==null) return null;
    	return objPlantilla = p;
  		
  	}
  	
  	BizPlantilla p = new BizPlantilla();
  	p.dontThrowException(true);
  	if (!p.Read(getIdplantilla())) return null;
  	return objPlantilla = p;
  }
  
  public String getDestinatario() throws Exception {
		String out="";
  	if (isAccionEMAIL()||isAccionURL())
			out=(getCorreo());
		else if (isAccionNOTIF())
			out=(getUsuario());
		else if (isAccionMAILING())
			out=(getMailing());
		else if (isAccionDOWNLOAD())
			out="";
		else out=(getCorreo()+getUsuario()+getTelefono());
  	return out;
  }
  
  public String getArchivoAsociado(JFilterMap a,BizSqlEventHistory hist) throws Exception {
  	if (isAccionURL()) {
  		String file =getObjSqlEvent().getArchivoAsociadoAviso(a,this,hist);
  		if (file==null) return null;
  		hist.generateLinkQR(file);
   		return  file;
   	}
  	if (isAccionDOWNLOAD()) {
   		return getObjSqlEvent().getArchivoAsociadoAviso(a,this,hist);
  	}
  	if (isAdjunto()) {
   		return getObjSqlEvent().getArchivoAsociadoAviso(a,this,hist);
  	}
  	if (isOutEXCEL()) {
   		return getObjSqlEvent().getArchivoAsociadoAviso(a,this,hist);
  	}
  	return null;
  }
  public String getMensajeAviso(JFilterMap a,BizSqlEventHistory hist) throws Exception {
  	if (isAccionDOWNLOAD()) {
   		return URLEncoder.encode("Presione Aplicar y el archivo se descargará a su dispositivo.","ISO-8859-1").replace("+", "%20");
   	}
  	if (isAccionURL()) {
   		return getObjSqlEvent().getCorreoAviso(a,this,hist);
   	}
  	if (isAccionEMAIL()) {
   		return getObjSqlEvent().getCorreoAviso(a,this,hist);
  	}
  	if (isAccionMAILING()) {
   		return getObjSqlEvent().getCorreoAviso(a,this,hist);
   	}
  	if (isAccionAVISO()) {
   		return getObjSqlEvent().getNotificacionAviso(a,this,hist);
  	}
  	if (isAccionNOTIF()) {
   		return getObjSqlEvent().getNotificacionAviso(a,this,hist);
  	}
  	return null;
  }
  public void send(JFilterMap a,BizSqlEventHistory hist) throws Exception {
  	if (isAccionEMAIL()) {
  		sendMail(a,hist);
  	}
  	if (isAccionMAILING()) {
  		sendMultiMail(a,hist);
  	}
  	if (isAccionAVISO()) {
  		sendMessageInterno(a,hist);
  	}
  	if (isAccionURL()) {
  		if (pCorreo.isNotNull())
  			sendMailQR(a,hist);
   	}

  }
 
	private void sendMessageInterno(JFilterMap a,BizSqlEventHistory hist) throws Exception {
		BizMessage msg = new BizMessage();
		long idMsg = msg.SelectMaxLong("id_message") + 1;
		msg.setIdmessage(idMsg);
		msg.setPriority(1);
		msg.setEmergency(true);
		msg.setSender("ADMIN");
		msg.setTitle("AVISO");
		msg.setMessage(this.getObjSqlEvent().getDescripcion()+" "+hist.getFundamento());
		msg.setDateCreation(new Date());
		msg.insert();
		
		BizMail mail = new BizMail();
		mail.setCompany(getCompany());
		mail.setFolder("INBOX");
		mail.setUsuario(getUsuario());
		mail.setIdmessage(msg.getIdmessage());
		mail.setDateCreation(new Date());
		mail.setValidTime(JDateTools.addMinutes(new Date(), 60));
		mail.insert();
		hist.setFechaEnvio(new Date());
		hist.setRemitido(true);
		hist.processUpdate();		
	}
	private void sendMail(JFilterMap a,BizSqlEventHistory hist) throws Exception {
		String titulo = getObjSqlEvent().getDescripcion();
		if (isAdjunto()) {
			String filename = JPath.PssPathTempFiles()+"/"+hist.getFileElectronico(getCompany(),titulo);
			if (filename.endsWith("null")) 
				throw new Exception("Reporte vacio.");
			getObjMailSender().send(hist.getDestinatario(),titulo,hist.getMensaje(),filename);
		} else
			getObjMailSender().send(hist.getDestinatario(),titulo,hist.getMensaje());
		hist.setFechaEnvio(new Date());
		hist.setRemitido(true);
		processUpdate();
		
	}

	private void sendMailQR(JFilterMap a,BizSqlEventHistory hist) throws Exception {
		String titulo = getObjSqlEvent().getDescripcion();
		getObjMailSender().send(hist.getDestinatario(),titulo,hist.getMensaje());
		hist.setFechaEnvio(new Date());
		hist.setRemitido(true);
		processUpdate();
		
	}
	private void sendMultiMail(JFilterMap a,BizSqlEventHistory hist) throws Exception {
		JRecords<BizMailingPersona> personas = new JRecords<BizMailingPersona>(BizMailingPersona.class);
		personas.addFilter("company", getCompany());
		personas.addFilter("tipo", getDestinatario());
		JIterator<BizMailingPersona> it = personas.getStaticIterator();
		while (it.hasMoreElements()) {
			BizMailingPersona persona = it.nextElement();
			if (persona.isNullMail()) continue;
			String mail=persona.getMail();
			String titulo = getObjSqlEvent().getDescripcion();
			String msg;
			if (isAdjunto()) {
				String filename = getObjSqlEvent().getArchivoAsociadoAviso(a,this,hist,persona.getTipo(),persona.getCodigo());
				if (filename!=null) {
					msg = getObjSqlEvent().getCorreoAviso(a,this,hist);
					getObjMailSender().send(mail,titulo,msg,filename);
					
				}
				else {
					setMensaje("No enviado por estar vacío");
				};
				
			} else {
				String msgG =getObjSqlEvent().getCorreoAviso(a,this,hist,persona.getTipo(),persona.getCodigo());
				if (msgG!=null) {
					msg = (new URLCodec()).decode(msgG,"ISO-8859-1");
					getObjMailSender().send(mail,titulo,msg);
				}
				else {
					setMensaje("No enviado por estar vacío");
				};
			}
		}
		hist.setFechaEnvio(new Date());
		hist.setRemitido(true);
		hist.processUpdate();
		
	}
	BizUsrMailSender objMailSender;

  public BizUsrMailSender getObjMailSender() throws Exception {
  	if (objMailSender!=null) return objMailSender;
  	BizUsrMailSender u = new BizUsrMailSender();
  	u.read(3);//RJL hardcore
  	return objMailSender=u;
  }
  public boolean hasRun() throws Exception {
  	if (getTipoPeriodicidad().equals(BizSqlEventAction.SOLOUNAVEZ)) 
  		return false;//getFechaUltimoEnvio()==null;
  	if (getTipoPeriodicidad().equals(BizSqlEventAction.LIMITE)) return false;
  	Calendar fechaUltimoEnvio = null;
  	Calendar fechaProxEnvio=null;
  	Calendar fechaHoy = Calendar.getInstance();

  	fechaHoy.setTime(new Date());
    if (getFechaUltimoEnvio()!=null) {
    	fechaUltimoEnvio = Calendar.getInstance();
    	fechaUltimoEnvio.setTime(getFechaUltimoEnvio());
    	fechaProxEnvio = getNextFecha(fechaUltimoEnvio.getTime());
    } else {
   	  fechaProxEnvio = getNextFecha(JDateTools.getFirstDayOfYear(fechaHoy.getTime()));
    }
  	
  	if (fechaProxEnvio==null) return false;
  	
   	boolean enviar=fechaProxEnvio.before(fechaHoy);

   	System.out.println("Siguiente envio: "+fechaProxEnvio.getTime());
   	if (!enviar) return false;
   	
    if (getFechaUltimoEnvio()==null) return true;
   	
    System.out.println("Ultimo envio: "+getFechaUltimoEnvio());
    
    enviar = fechaUltimoEnvio.before(fechaProxEnvio);
    
    return enviar;
  	
  	//if (!checkFecha(hoy)) return false;

  	// un aviso por dia
//  	Calendar fechaActual = Calendar.getInstance();
//  	fechaActual.setTime(JDateTools.getDateStartDay(hoy));
//  	Calendar anterior = Calendar.getInstance();
//  	if (getFechaUltimoEnvio()!=null) {
//    	anterior.setTime(JDateTools.getDateStartDay(getFechaUltimoEnvio()));
//    	if (anterior.compareTo(fechaActual)>=0) return false;
//  	}
  	
//  	return true;
  }
//////
//  public Calendar getNextFecha(Date d) throws Exception {
//  	Calendar testDay = Calendar.getInstance();
//  	testDay.setTime(d);
//  	for(int i =0;i<60;i++) {
//    	testDay.add(Calendar.MINUTE, 1);
//  		if (checkFecha(testDay.getTime())) {
//  			return testDay;
//  		}
//  	}
//  	return null;
//  }
  public Calendar getNextFecha(Date d) throws Exception {
   	if (getTipoPeriodicidad().equals(BizSqlEventAction.DIARIO)) return getNextHora(d,false);
   	if (getTipoPeriodicidad().equals(BizSqlEventAction.SEMANAL)) return getNextSemana(d);
   	if (getTipoPeriodicidad().equals(BizSqlEventAction.MENSUAL)) return getNextDia(d);
   	if (getTipoPeriodicidad().equals(BizSqlEventAction.ANUAL)) return getNextMes(d);
    return null;
  }
  public Calendar getNextHora(Date d,boolean force) throws Exception {
  	if (pHora.isNull()) {
  		if (!force) return null;
    	Calendar meta = Calendar.getInstance();
    	meta.setTime( JDateTools.getDateStartDay(d));
    	return meta;
  
  	}
  	Date fechaControl = JDateTools.StringToDate(JDateTools.DateToString(d)+" "+getHora());
  	Calendar meta = Calendar.getInstance();
  	meta.setTime(fechaControl);
  	return meta;
  }

  public Calendar getNextSemana(Date d) throws Exception {
  	Calendar meta = Calendar.getInstance();
  	meta.setTime(d);
  	int day = meta.get(Calendar.DAY_OF_WEEK);
  	int bwn = 0;
  	int add=0;
  	if (pLunes.getValue()) bwn = Calendar.MONDAY;
  	if (pMartes.getValue()) bwn = Calendar.TUESDAY;
  	if (pMiercoles.getValue()) bwn = Calendar.WEDNESDAY;
  	if (pJueves.getValue()) bwn = Calendar.THURSDAY;
  	if (pViernes.getValue()) bwn = Calendar.FRIDAY;
  	if (pSabado.getValue()) bwn = Calendar.SATURDAY;
  	if (pDomingo.getValue()) bwn = Calendar.SUNDAY;
  	if (day<bwn) add = bwn-day ;// 1 < 4
  	else add=(7-day)+bwn;
  	meta.add(Calendar.DAY_OF_MONTH, (int)add);
  	return getNextHora(meta.getTime(),true);
  }

  public  Calendar getNextDia(Date ahora) throws Exception {
  	Calendar meta = Calendar.getInstance();
  	meta.setTime(ahora);
  	Calendar metaMes = Calendar.getInstance();
  	metaMes.setTime(JDateTools.getLastDayOfMonth(ahora));
    int last = metaMes.get(Calendar.DAY_OF_MONTH);
  	int day = meta.get(Calendar.DAY_OF_MONTH);
  	int bwn = 0;
  	int add=0;
  	Calendar testDay = Calendar.getInstance();
  	testDay.setTime(ahora);
  	for(int i =0;i<31;i++) {
  
    	if ((pd1.getValue() || pDiasTodos.getValue()) && testDay.get(Calendar.DAY_OF_MONTH)==1) bwn = 1;
    	if ((pd2.getValue() || pDiasTodos.getValue()) && testDay.get(Calendar.DAY_OF_MONTH)==2) bwn = 2;
    	if ((pd3.getValue() || pDiasTodos.getValue()) && testDay.get(Calendar.DAY_OF_MONTH)==3) bwn = 3;
    	if ((pd4.getValue() || pDiasTodos.getValue()) && testDay.get(Calendar.DAY_OF_MONTH)==4) bwn = 4;
    	if ((pd5.getValue() || pDiasTodos.getValue()) && testDay.get(Calendar.DAY_OF_MONTH)==5) bwn = 5;
    	if ((pd6.getValue() || pDiasTodos.getValue()) && testDay.get(Calendar.DAY_OF_MONTH)==6) bwn = 6;
    	if ((pd7.getValue() || pDiasTodos.getValue()) && testDay.get(Calendar.DAY_OF_MONTH)==7) bwn = 7;
    	if ((pd8.getValue() || pDiasTodos.getValue()) && testDay.get(Calendar.DAY_OF_MONTH)==8) bwn = 8;
    	if ((pd9.getValue() || pDiasTodos.getValue()) && testDay.get(Calendar.DAY_OF_MONTH)==9) bwn = 9;
    	if ((pd10.getValue() || pDiasTodos.getValue()) && testDay.get(Calendar.DAY_OF_MONTH)==10) bwn = 10;
    	if ((pd11.getValue() || pDiasTodos.getValue()) && testDay.get(Calendar.DAY_OF_MONTH)==11) bwn = 11;
    	if ((pd12.getValue() || pDiasTodos.getValue()) && testDay.get(Calendar.DAY_OF_MONTH)==12) bwn = 12;
    	if ((pd13.getValue() || pDiasTodos.getValue()) && testDay.get(Calendar.DAY_OF_MONTH)==13) bwn = 13;
    	if ((pd14.getValue() || pDiasTodos.getValue()) && testDay.get(Calendar.DAY_OF_MONTH)==14) bwn = 14;
    	if ((pd15.getValue() || pDiasTodos.getValue()) && testDay.get(Calendar.DAY_OF_MONTH)==15) bwn = 15;
    	if ((pd16.getValue() || pDiasTodos.getValue()) && testDay.get(Calendar.DAY_OF_MONTH)==16) bwn = 16;
    	if ((pd17.getValue() || pDiasTodos.getValue()) && testDay.get(Calendar.DAY_OF_MONTH)==17) bwn = 17;
    	if ((pd18.getValue() || pDiasTodos.getValue()) && testDay.get(Calendar.DAY_OF_MONTH)==18) bwn = 18;
    	if ((pd19.getValue() || pDiasTodos.getValue()) && testDay.get(Calendar.DAY_OF_MONTH)==19) bwn = 19;
    	if ((pd20.getValue() || pDiasTodos.getValue()) && testDay.get(Calendar.DAY_OF_MONTH)==20) bwn = 20;
    	if ((pd21.getValue() || pDiasTodos.getValue()) && testDay.get(Calendar.DAY_OF_MONTH)==21) bwn = 21;
    	if ((pd22.getValue() || pDiasTodos.getValue()) && testDay.get(Calendar.DAY_OF_MONTH)==22) bwn = 22;
    	if ((pd23.getValue() || pDiasTodos.getValue()) && testDay.get(Calendar.DAY_OF_MONTH)==23) bwn = 23;
    	if ((pd24.getValue() || pDiasTodos.getValue()) && testDay.get(Calendar.DAY_OF_MONTH)==24) bwn = 24;
    	if ((pd25.getValue() || pDiasTodos.getValue()) && testDay.get(Calendar.DAY_OF_MONTH)==25) bwn = 25;
    	if ((pd26.getValue() || pDiasTodos.getValue()) && testDay.get(Calendar.DAY_OF_MONTH)==27) bwn = 26;
    	if ((pd27.getValue() || pDiasTodos.getValue()) && testDay.get(Calendar.DAY_OF_MONTH)==16) bwn = 27;
    	if ((pd28.getValue() || pDiasTodos.getValue()) && testDay.get(Calendar.DAY_OF_MONTH)==27) bwn = 28;
    	if ((pd29.getValue() || pDiasTodos.getValue()) && testDay.get(Calendar.DAY_OF_MONTH)==28) bwn = 29;
    	if ((pd30.getValue() || pDiasTodos.getValue()) && testDay.get(Calendar.DAY_OF_MONTH)==29) bwn = 30;
    	if ((pd31.getValue() || pDiasTodos.getValue()) && testDay.get(Calendar.DAY_OF_MONTH)==last) bwn=last;
    	if (bwn!=0)break;
  		testDay.add(Calendar.DAY_OF_MONTH, 1);
  	}
  	
  	if (day<bwn) add = bwn-day ;// 1 < 4
  	else add=(last-day)+bwn;
  	meta.add(Calendar.DAY_OF_MONTH, (int)add);
  	return getNextHora(meta.getTime(),true);
  }
  
  public Calendar getNextMes(Date ahora) throws Exception {
  	Calendar meta = Calendar.getInstance();
  	meta.setTime(ahora);
    int last = 12;
   	int mes = meta.get(Calendar.MONTH);
   	int dia = meta.get(Calendar.DAY_OF_MONTH);
  	int bwn = 0;
   	int add = 0;
   	
   	for(int i=0;i<12;i++) {
   		int mess = ((mes+i))>12?12-((mes+i)):((mes+i));
	  	if ((pm1.getValue() || pMesTodos.getValue()) && mess==Calendar.JANUARY) bwn= Calendar.JANUARY;
	  	if ((pm2.getValue() || pMesTodos.getValue()) && mess==Calendar.FEBRUARY) bwn= Calendar.FEBRUARY ;
	  	if ((pm3.getValue() || pMesTodos.getValue()) && mess==Calendar.MARCH) bwn= Calendar.MARCH ;
	  	if ((pm4.getValue() || pMesTodos.getValue()) && mess==Calendar.APRIL) bwn= Calendar.APRIL ;
	  	if ((pm5.getValue() || pMesTodos.getValue()) && mess==Calendar.MAY) bwn= Calendar.MAY ;
	  	if ((pm6.getValue() || pMesTodos.getValue()) && mess==Calendar.JUNE) bwn= Calendar.JUNE ;
	  	if ((pm7.getValue() || pMesTodos.getValue()) && mess==Calendar.JULY) bwn= Calendar.JULY ;
	  	if ((pm8.getValue() || pMesTodos.getValue()) && mess==Calendar.AUGUST) bwn= Calendar.AUGUST ;
	  	if ((pm9.getValue() || pMesTodos.getValue()) && mess==Calendar.SEPTEMBER) bwn= Calendar.SEPTEMBER ;
	  	if ((pm10.getValue() || pMesTodos.getValue()) && mess==Calendar.OCTOBER) bwn= Calendar.OCTOBER ;
	  	if ((pm11.getValue() || pMesTodos.getValue()) && mess==Calendar.NOVEMBER) bwn= Calendar.NOVEMBER ;
	  	if ((pm12.getValue() || pMesTodos.getValue()) && mess==Calendar.DECEMBER) bwn= Calendar.DECEMBER ;
	  	if (bwn!=0) break;
   	}
  	if (mes<bwn) 
  		add = bwn-mes ;// 1 < 4
  	else 
  		add=(last-mes)+bwn;
  	meta.add(Calendar.MONTH, add==12?0:(int)add);
  	return getNextDia(meta.getTime());
  }

//////
  public boolean checkFecha(Date fecha) throws Exception {
   	if (getTipoPeriodicidad().equals(BizSqlEventAction.DIARIO)) return checkHora(fecha);
   	if (getTipoPeriodicidad().equals(BizSqlEventAction.SEMANAL)) return checkHora(fecha) && checkSemana(fecha);
   	if (getTipoPeriodicidad().equals(BizSqlEventAction.MENSUAL)) return checkHora(fecha) && checkDia(fecha);
   	if (getTipoPeriodicidad().equals(BizSqlEventAction.ANUAL)) return checkHora(fecha) && checkDia(fecha) && checkMes(fecha);
    return false;
  }
  
  public boolean checkHora(Date ahora) throws Exception {
  	if (pHora.isNull()) return true;
  	Date fechaControl = JDateTools.StringToDate(JDateTools.DateToString(new Date())+" "+getHora());
  	Calendar meta = Calendar.getInstance();
  	meta.setTime(fechaControl);
  	if (meta.before(ahora)) return false;
  	return true;
  }

  public boolean checkSemana(Date ahora) throws Exception {
  	Calendar meta = Calendar.getInstance();
  	meta.setTime(ahora);
  	if (meta.get(Calendar.DAY_OF_WEEK)==Calendar.MONDAY && pLunes.getValue()) return true;
  	if (meta.get(Calendar.DAY_OF_WEEK)==Calendar.TUESDAY && pMartes.getValue()) return true;
  	if (meta.get(Calendar.DAY_OF_WEEK)==Calendar.WEDNESDAY && pMiercoles.getValue()) return true;
  	if (meta.get(Calendar.DAY_OF_WEEK)==Calendar.THURSDAY && pJueves.getValue()) return true;
  	if (meta.get(Calendar.DAY_OF_WEEK)==Calendar.FRIDAY && pViernes.getValue()) return true;
  	if (meta.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY && pSabado.getValue()) return true;
  	if (meta.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY && pDomingo.getValue()) return true;
  	return false;
  }

  public boolean checkDia(Date ahora) throws Exception {
  	Calendar meta = Calendar.getInstance();
  	meta.setTime(ahora);
  	Calendar metaMes = Calendar.getInstance();
  	metaMes.setTime(JDateTools.getLastDayOfMonth(ahora));
   
  	if (meta.get(Calendar.DAY_OF_MONTH)==metaMes.get(Calendar.DAY_OF_MONTH) && pd31.getValue()) return true;

  	if (meta.get(Calendar.DAY_OF_MONTH)==1 && pd1.getValue()) return true;
  	if (meta.get(Calendar.DAY_OF_MONTH)==2 && pd2.getValue()) return true;
  	if (meta.get(Calendar.DAY_OF_MONTH)==3 && pd3.getValue()) return true;
  	if (meta.get(Calendar.DAY_OF_MONTH)==4 && pd4.getValue()) return true;
  	if (meta.get(Calendar.DAY_OF_MONTH)==5 && pd5.getValue()) return true;
  	if (meta.get(Calendar.DAY_OF_MONTH)==6 && pd6.getValue()) return true;
  	if (meta.get(Calendar.DAY_OF_MONTH)==7 && pd7.getValue()) return true;
  	if (meta.get(Calendar.DAY_OF_MONTH)==8 && pd8.getValue()) return true;
  	if (meta.get(Calendar.DAY_OF_MONTH)==9 && pd9.getValue()) return true;
  	if (meta.get(Calendar.DAY_OF_MONTH)==10 && pd10.getValue()) return true;
  	if (meta.get(Calendar.DAY_OF_MONTH)==11 && pd11.getValue()) return true;
  	if (meta.get(Calendar.DAY_OF_MONTH)==12 && pd12.getValue()) return true;
  	if (meta.get(Calendar.DAY_OF_MONTH)==13 && pd13.getValue()) return true;
  	if (meta.get(Calendar.DAY_OF_MONTH)==14 && pd14.getValue()) return true;
  	if (meta.get(Calendar.DAY_OF_MONTH)==15 && pd15.getValue()) return true;
  	if (meta.get(Calendar.DAY_OF_MONTH)==16 && pd16.getValue()) return true;
  	if (meta.get(Calendar.DAY_OF_MONTH)==17 && pd17.getValue()) return true;
  	if (meta.get(Calendar.DAY_OF_MONTH)==18 && pd18.getValue()) return true;
  	if (meta.get(Calendar.DAY_OF_MONTH)==19 && pd19.getValue()) return true;
  	if (meta.get(Calendar.DAY_OF_MONTH)==20 && pd20.getValue()) return true;
  	if (meta.get(Calendar.DAY_OF_MONTH)==21 && pd21.getValue()) return true;
  	if (meta.get(Calendar.DAY_OF_MONTH)==22 && pd22.getValue()) return true;
  	if (meta.get(Calendar.DAY_OF_MONTH)==23 && pd23.getValue()) return true;
  	if (meta.get(Calendar.DAY_OF_MONTH)==24 && pd24.getValue()) return true;
  	if (meta.get(Calendar.DAY_OF_MONTH)==25 && pd25.getValue()) return true;
  	if (meta.get(Calendar.DAY_OF_MONTH)==26 && pd26.getValue()) return true;
  	if (meta.get(Calendar.DAY_OF_MONTH)==27 && pd27.getValue()) return true;
  	if (meta.get(Calendar.DAY_OF_MONTH)==28 && pd28.getValue()) return true;
  	if (meta.get(Calendar.DAY_OF_MONTH)==29 && pd29.getValue()) return true;
  	if (meta.get(Calendar.DAY_OF_MONTH)==30 && pd30.getValue()) return true;
  	return false;
  }
  
  public boolean checkMes(Date ahora) throws Exception {
  	Calendar meta = Calendar.getInstance();
  	meta.setTime(ahora);
  	if (meta.get(Calendar.MONTH)==Calendar.JANUARY && pm1.getValue()) return true;
  	if (meta.get(Calendar.MONTH)==Calendar.FEBRUARY && pm2.getValue()) return true;
  	if (meta.get(Calendar.MONTH)==Calendar.MARCH && pm3.getValue()) return true;
  	if (meta.get(Calendar.MONTH)==Calendar.APRIL && pm4.getValue()) return true;
  	if (meta.get(Calendar.MONTH)==Calendar.MAY && pm5.getValue()) return true;
  	if (meta.get(Calendar.MONTH)==Calendar.JUNE && pm6.getValue()) return true;
  	if (meta.get(Calendar.MONTH)==Calendar.JULY && pm7.getValue()) return true;
  	if (meta.get(Calendar.MONTH)==Calendar.AUGUST && pm8.getValue()) return true;
  	if (meta.get(Calendar.MONTH)==Calendar.SEPTEMBER && pm9.getValue()) return true;
  	if (meta.get(Calendar.MONTH)==Calendar.OCTOBER && pm10.getValue()) return true;
  	if (meta.get(Calendar.MONTH)==Calendar.NOVEMBER && pm11.getValue()) return true;
  	if (meta.get(Calendar.MONTH)==Calendar.DECEMBER && pm12.getValue()) return true;
  	return false;
  }

  public static void ExecCheckForEvents() throws Exception {

  	try {
			JRecords<BizSqlEventAction> events = new JRecords<BizSqlEventAction>(BizSqlEventAction.class);
		//	events.addFilter("tipo_periodicidad", BizSqlEventAction.LIMITE);
			events.addFilter("tipo_periodicidad", BizSqlEventAction.SOLOUNAVEZ,"<>");
				JIterator<BizSqlEventAction> it =events.getStaticIterator();
			while (it.hasMoreElements()) {
				BizSqlEventAction sqlEvent = it.nextElement();
				sqlEvent.execProcessCheckEvents();
				Thread.yield();

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  }

  public static void ExecCheckForEvents(String company) throws Exception {

  	try {
//  		if (BizBSPCompany.getObjBSPCompany(company).getObjExtraData().getSuspender()) RJL, pasar a bsp
//  			return;
  		JRecords<BizSqlEventAction> events = new JRecords<BizSqlEventAction>(BizSqlEventAction.class);
			events.addFilter("company", company);
//			events.addFilter("tipo_periodicidad", BizSqlEventAction.LIMITE,"<>");
			events.addFilter("tipo_periodicidad", BizSqlEventAction.SOLOUNAVEZ,"<>");
			JIterator<BizSqlEventAction> it =events.getStaticIterator();
			while (it.hasMoreElements()) {
				BizSqlEventAction sqlEvent = it.nextElement();
				sqlEvent.execProcessCheckEvents();
				Thread.yield();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  }

	public void execProcessCheckEvents() throws Exception {
		JExec oExec = new JExec(this, "processCheckEvents") {

			@Override
			public void Do() throws Exception {
				try {
					processCheckEvents();
				} catch (Exception e) {
					PssLogger.logError(e);
				}
			}
		};
		oExec.execute();
	}

	public void processCheckEvents() throws Exception {
		IActionData actionData;
		try {
			actionData = getObjSqlEvent();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw e;
		}
		if (actionData==null) return;
		String marca=null;
		if (getTipoPeriodicidad().equals(BizSqlEventAction.SOLOUNAVEZ) /*&& getFechaUltimoEnvio()!=null*/) return;
		if (getTipoPeriodicidad().equals(BizSqlEventAction.LIMITE)) { 
			marca = actionData.hasGenerateAviso(this);
			if (marca==null) return;
		}
		else
			if (!hasRun()) return;
		if (getCompany().equals("IUMIRA"))
			PssLogger.logInfo("log point");
		BizSqlEventHistory msg=actionData.generarAviso(null,this,false);
		if (msg==null) return;
		if (msg.getDestinatario().equals("")) return;
		msg.processInsert();
		setFechaUltimoEnvio(new Date());
		processUpdate();
		actionData.mensajeEnviado(marca);
		
		
		this.send(null,msg);

		
	}

}

