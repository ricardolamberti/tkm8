package  pss.common.agenda.turno;

import java.util.Calendar;
import java.util.Date;

import pss.common.agenda.evento.BizEvento;
import pss.common.agenda.evento.tipo.GuiTipoEventos;
import pss.common.agenda.feriado.BizFeriado;
import pss.common.agenda.turno.ausencias.BizTurnoAusencia;
import pss.common.agenda.turno.laborables.BizTurnoLaborable;
import pss.common.customList.config.relation.JRelation;
import pss.common.customList.config.relation.JRelations;
import pss.common.security.BizUsuario;
import pss.core.services.JExec;
import pss.core.services.fields.JBoolean;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.JPair;
import pss.core.tools.collections.JIterator;
import pss.core.win.JWins;

public class BizTurno extends JRecord {

  private JString pCompany = new JString();
  private JLong pAgenda = new JLong();
  private JLong pTiempoTurnos = new JLong();
  private JLong pTiempoSeparacionTurnos = new JLong();
  private JBoolean pUsaHito = new JBoolean();
  private JBoolean pUsaAlarma = new JBoolean();
  private JBoolean pUsaParticipantes = new JBoolean();
  private JLong pTipoEventoDefault = new JLong();
  private JLong pOwner = new JLong();
  private JString pDescripcion = new JString();
  private JString pEstadoInicial = new JString();
  private JString pTipo = new JString();
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getter & Setters methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  public void setCompany(String zValue) throws Exception {    pCompany.setValue(zValue);  }
  public String getCompany() throws Exception {     return pCompany.getValue();  }
  public boolean isNullCompany() throws Exception { return  pCompany.isNull(); } 
  public void setNullToCompany() throws Exception {  pCompany.setNull(); } 

  public void setOwner(long zValue) throws Exception {    pOwner.setValue(zValue);  }
  public long getOwner() throws Exception {     return pOwner.getValue();  }
  public boolean isNullOwner() throws Exception { return  pOwner.isNull(); } 
  public void setNullToOwner() throws Exception {  pOwner.setNull(); } 

 
  
  public void setDescripcion(String zValue) throws Exception {    pDescripcion.setValue(zValue);  }
  public String getDescripcion() throws Exception {     return pDescripcion.getValue();  }

  public void setTiempoTurno(long zValue) throws Exception {    pTiempoTurnos.setValue(zValue);  }
  public long getTiempoTurno() throws Exception {     return pTiempoTurnos.getValue();  }
  public void setSepTiempoTurno(long zValue) throws Exception {    pTiempoSeparacionTurnos.setValue(zValue);  }
  public long getSepTiempoTurno() throws Exception {     return pTiempoSeparacionTurnos.getValue();  }

  public void setIdAgenda(long zValue) throws Exception {    pAgenda.setValue(zValue);  }
  public long getIdAgenda() throws Exception {     return pAgenda.getValue();  }

  public void setUsaHito(boolean zValue) throws Exception {    pUsaHito.setValue(zValue);  }
  public boolean isUsaHito() throws Exception {     return pUsaHito.getValue();  }
  public void setUsaAlarma(boolean zValue) throws Exception {    pUsaAlarma.setValue(zValue);  }
  public boolean isUsaAlarma() throws Exception {     return pUsaAlarma.getValue();  }
  public void setUsaParticipantes(boolean zValue) throws Exception {    pUsaParticipantes.setValue(zValue);  }
  public boolean isUsaParticipantes() throws Exception {     return pUsaParticipantes.getValue();  }

  public void setEstadoInicial(String zValue) throws Exception {    pEstadoInicial.setValue(zValue);  }
  public String getEstadoIncial() throws Exception {     return pEstadoInicial.getValue();  }

  public void setTipoEventoDefault(long zValue) throws Exception {    pTipoEventoDefault.setValue(zValue);  }
  public long getTipoEventoDefault() throws Exception {     return pTipoEventoDefault.getValue();  }

  /**
   * Constructor de la Clase
   */
  public BizTurno() throws Exception {
  }


  public void createProperties() throws Exception {
    this.addItem( "company", pCompany );
    this.addItem( "id_agenda", pAgenda );
    this.addItem( "tiempo_turnos", pTiempoTurnos );
    this.addItem( "tiempo_separacion_turnos", pTiempoSeparacionTurnos );
    this.addItem( "descripcion", pDescripcion );
    this.addItem( "use_hito", pUsaHito );
    this.addItem( "use_alarma", pUsaAlarma );
    this.addItem( "use_participantes", pUsaParticipantes );
    this.addItem( "id_tipo_evento_default", pTipoEventoDefault );
    this.addItem( "estado_inicial", pEstadoInicial );
    this.addItem( "tipo", pTipo );
    this.addItem( "owner", pOwner );
  }
  /**
   * Adds the fixed object properties
   */
  public void createFixedProperties() throws Exception {
    this.addFixedItem( KEY, "company", "Company", true, true, 15 );
    this.addFixedItem( KEY, "id_agenda", "id agenda", false, false, 64 );
    this.addFixedItem( FIELD, "tiempo_turnos", "Tiempo turnos", true, false, 18 );
    this.addFixedItem( FIELD, "tiempo_separacion_turnos", "Tiempo separacion turnos", true, false, 18 );
    this.addFixedItem( FIELD, "descripcion", "Descripcion", true, true, 250 );
    this.addFixedItem( FIELD, "use_hito", "usa hito?", true, false, 1 );
    this.addFixedItem( FIELD, "use_alarma", "usa alarma?", true, false, 1 );
    this.addFixedItem( FIELD, "use_participantes", "usa participantes?", true, false, 1 );
    this.addFixedItem( FIELD, "id_tipo_evento_default", "tipo evento default", true, false, 64 );
    this.addFixedItem( FIELD, "estado_inicial", "Estado incial", true, false, 64 );
    this.addFixedItem( FIELD, "owner", "Dueño", true, false, 64 );
    this.addFixedItem( VIRTUAL, "tipo", "tipo", true, false, 10);
    }
  
  @Override
  public void createControlProperties() throws Exception {
  	this.addControlsProperty("id_tipo_evento_default", createControlCombo(GuiTipoEventos.class,"id_tipoevento", new JPair<String, String>("company","company") ));
   	this.addControlsProperty("estado_inicial", createControlCombo(JWins.createVirtualWinsFromMap(BizEvento.getEstados()),null, null) );
   	super.createControlProperties();
  }
  
  /**
   * Returns the table name
   */
  public String GetTable() { return "agd_manejturnos"; }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functionality methods
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  

  /**
   * Default read() method
   */
  public boolean read( long agenda) throws Exception { 
    addFilter( "id_agenda",  agenda ); 
    return read(); 
  } 
  
  public static BizTurno readTurnoMasDisponible( String zCompany ) throws Exception { 

    	return readTurnoMasDisponibleEntreTodosLosUsuarios(zCompany);

  } 
  
 
  public static BizTurno readTurnoMasDisponibleEntreTodosLosUsuarios( String zCompany ) throws Exception { 
    JRecords<BizTurno> turnos = new JRecords<BizTurno>(BizTurno.class);
    turnos.addFilter("company", zCompany);
    JIterator<BizTurno> it = turnos.getStaticIterator();
    Date masReciente=null;
    BizTurno usu=null;
    while (it.hasMoreElements()) {
    	BizTurno turno = it.nextElement();
    	Date fecha = turno.calculeNextCita();
    	if (fecha!=null && (masReciente==null || fecha.before(masReciente))) {
    		masReciente=fecha;
    		usu=turno;
    	}
    }
    return usu;
  } 
  @Override
  public void processInsert() throws Exception {
  	if (pCompany.isNull()) pCompany.setValue(BizUsuario.getUsr().getCompany());
  	super.processInsert();
  }
  
  public BizTurnoLaborable getLaborable(Date fecha) throws Exception {
  	Calendar c = Calendar.getInstance();
  	c.setTime(fecha);
  	
  	JRecords<BizTurnoLaborable> labs = new JRecords<BizTurnoLaborable>(BizTurnoLaborable.class);
  	labs.addFilter("id_agenda",getIdAgenda());
  	labs.addFilter("company",getCompany());
  	labs.toStatic();
  	labs.firstRecord();
  	while (labs.nextRecord()) {
  		BizTurnoLaborable l = labs.getRecord();
  		if (l.getDia()!=c.get(Calendar.DAY_OF_WEEK)) continue;
  		Calendar ld = Calendar.getInstance();
  		ld.setTime(fecha);
  		ld.set(Calendar.HOUR_OF_DAY, Integer.parseInt(l.getHoraDesde().substring(0,2)));
  		ld.set(Calendar.MINUTE, Integer.parseInt(l.getHoraDesde().substring(3,5)));
  		ld.set(Calendar.SECOND, Integer.parseInt(l.getHoraDesde().substring(6)));

  		Calendar lh = Calendar.getInstance();
  		lh.setTime(fecha);
  		lh.set(Calendar.HOUR_OF_DAY, Integer.parseInt(l.getHoraHasta().substring(0,2)));
  		lh.set(Calendar.MINUTE, Integer.parseInt(l.getHoraHasta().substring(3,5)));
  		lh.set(Calendar.SECOND, Integer.parseInt(l.getHoraHasta().substring(6)));

  		if (!(c.after(ld) && c.before(lh))) continue;
  		return l;
  	}
  	return null;
  }
  
  public Date getNextTurno(Date fecha, boolean force) throws Exception {
  	Calendar fechaCandidata = Calendar.getInstance();
  	fechaCandidata.setTime(fecha);
  	if (force) {
  		Calendar nextDisponible = checkDisponibilidad(fechaCandidata);
			if (nextDisponible==null)	return fechaCandidata.getTime();
			
  	}
  	boolean first=true;
    int fallas=0;
  	while (true) { //recorro dias
  		if (fallas>100) return null;
  		int diaSemana = fechaCandidata.get(Calendar.DAY_OF_WEEK);
    	JRecords<BizTurnoLaborable> labs = new JRecords<BizTurnoLaborable>(BizTurnoLaborable.class);
    	labs.addFilter("id_agenda",getIdAgenda());
    	labs.addFilter("company",getCompany());
    	labs.addFilter("dia",diaSemana);
    	labs.addOrderBy("hora_desde","asc");
    	labs.toStatic();
    	labs.firstRecord();
  		while (labs.nextRecord()) {
  			BizTurnoLaborable l = labs.getRecord();
  			
    		Calendar ld = Calendar.getInstance();
    		ld.setTime(fechaCandidata.getTime());
    		ld.set(Calendar.HOUR_OF_DAY, Integer.parseInt(l.getHoraDesde().substring(0,2)));
    		ld.set(Calendar.MINUTE, Integer.parseInt(l.getHoraDesde().substring(3,5)));
    		ld.set(Calendar.SECOND, Integer.parseInt(l.getHoraDesde().substring(6)));

    		Calendar lh = Calendar.getInstance();
    		lh.setTime(fechaCandidata.getTime());
    		lh.set(Calendar.HOUR_OF_DAY, Integer.parseInt(l.getHoraHasta().substring(0,2)));
    		lh.set(Calendar.MINUTE, Integer.parseInt(l.getHoraHasta().substring(3,5)));
    		lh.set(Calendar.SECOND, Integer.parseInt(l.getHoraHasta().substring(6)));

    		if (first && fechaCandidata.after(lh)) continue;
    		
    		Calendar fechaTurno = Calendar.getInstance();
    		fechaTurno.setTime(fechaCandidata.getTime());
   			fechaTurno.set(Calendar.HOUR_OF_DAY, ld.get(Calendar.HOUR_OF_DAY));
   			fechaTurno.set(Calendar.MINUTE, ld.get(Calendar.MINUTE));
   			fechaTurno.set(Calendar.SECOND, ld.get(Calendar.SECOND));
    		while (true) { // recorro turnos
    			Calendar nextDisponible = checkDisponibilidad(fechaTurno);
    			if (nextDisponible==null)	return fechaTurno.getTime();
    			
    			if (nextDisponible.equals(fechaTurno.getTime())) {
      			fechaTurno.add(Calendar.HOUR, (int)l.getDuracion());
      			fechaTurno.add(Calendar.MINUTE,(int)l.getSeparacion());
    			} else {
    				fechaTurno = nextDisponible ;
      			fechaTurno.add(Calendar.MINUTE,(int)l.getSeparacion());
    			}
    			
    			if (fechaTurno.after(lh)) break; // se acabo el ciclo
    		}
    		
  		}
  		fallas++;
  		fechaCandidata.add(Calendar.DAY_OF_YEAR, 1);
  		first=false;
  	}
   }  	
  
  	private Calendar checkDisponibilidad(Calendar zFecha) throws Exception {
 			Calendar out = Calendar.getInstance();
		  out.setTime(zFecha.getTime());
		  
		  Date finFeriado =BizFeriado.isFeriado(zFecha.getTime(),getCompany());
  		if (finFeriado!=null) {
  			out.setTime(finFeriado);
  			return out;
  		}
  	  		
  		BizTurnoAusencia auss = new BizTurnoAusencia();
  		auss.addFilter("company", getCompany());
  		auss.addFilter("id_agenda", getIdAgenda());
  		auss.addFilter("fecha_desde", zFecha.getTime(), true).setOperator("<=");
  		auss.addFilter("fecha_hasta", zFecha.getTime(), true).setOperator(">");
  		auss.dontThrowException(true);
  		if (auss.read()) {
  			out.setTime(auss.getHoraHasta());
  			return out;
   		}
  		
  		BizEvento evento = new BizEvento();
  		evento.addFilter("company", getCompany());
  		evento.addFilter("estado", BizEvento.ACTIVA);
  		evento.addFilter("fecha_inicio", zFecha.getTime(), true).setOperator("<=");
  		evento.addFilter("fecha_fin", zFecha.getTime(), true).setOperator(">");
//  		evento.addJoin("agd_evento_participantes");
//  		evento.addFixedFilter("where agd_evento_participantes.id_evento=agd_evento.id_evento and agd_evento_participantes.id_persona='"+getObjUsuario().getIdPersona()+"' ");
  		evento.dontThrowException(true);
  		if (evento.read()) {
  			out.setTime(evento.getFechaFin());
  			return out;
   		}
  		
  		return null;
  	}
  	  
  public  Date calculeNextCita() throws Exception {
  	// en verdad hay que buscar el siguiente turno para este usuario
 	
  	Calendar c = Calendar.getInstance();
  	c.setTime(new Date());

  	return getNextTurno(c.getTime(),false); 
  }

  
  public JRecords<BizTurnoLaborable> getTurnosLaborables() throws Exception {
    	JRecords<BizTurnoLaborable> labs = new JRecords<BizTurnoLaborable>(BizTurnoLaborable.class);
    	labs.addFilter("id_agenda",getIdAgenda());
    	labs.addFilter("company",getCompany());
    	labs.toStatic();
    	return labs;
   }
  
  public void execClonar(final String company) throws Exception {
		JExec exec = new JExec(null, null) {
			public void Do() throws Exception {
				clonarClonar(company);
			}
		};
		exec.execute();
	}
  
	public void clonarClonar(String company) throws Exception {
		BizTurno turno = new BizTurno();
		turno.setCompany(company);
		turno.copyCommonsNotKeysProperties(this, true);
		turno.processInsert();
		
  	JRecords<BizTurnoLaborable> laborables = new JRecords<BizTurnoLaborable>(BizTurnoLaborable.class);
  	laborables.addFilter("id_agenda",getIdAgenda());
  	laborables.addFilter("company",getCompany());
		JIterator<BizTurnoLaborable> it = laborables.getStaticIterator();
		while (it.hasMoreElements()) {
			BizTurnoLaborable laboral = it.nextElement();
			BizTurnoLaborable newLaboral = new BizTurnoLaborable();
			newLaboral.copyProperties(laboral);
			newLaboral.setIdAgenda(turno.getIdAgenda());
			newLaboral.processInsert();
		}
	}
	public void attachRelationMap(JRelations rels) throws Exception {
		rels.setSourceWinsClass(GuiTurno.class.getCanonicalName());

		JRelation rel;
		rel = rels.addRelationForce(10, "restriccion usuario");
		rel.addFilter(" (agd_manejturnos.company= 'COMPANY_CUSTOMLIST') ");


		rels.hideField("company");
		rels.hideField("id_*");
		rels.hideField("use_*");
		rels.hideField("owner*");
	}
  
}
