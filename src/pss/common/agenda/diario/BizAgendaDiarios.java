package  pss.common.agenda.diario;

import java.util.Calendar;
import java.util.Date;

import pss.common.agenda.evento.BizEvento;
import pss.common.agenda.feriado.BizFeriado;
import pss.common.agenda.turno.BizTurno;
import pss.common.security.BizUsuario;
import pss.core.data.interfaces.structure.RFilter;
import pss.core.services.records.JRecords;
import pss.core.tools.JDateTools;
import pss.core.tools.collections.JIterator;
import pss.core.win.JWins;

public class BizAgendaDiarios extends JRecords<BizAgendaDiario> {
	
	public Class<BizAgendaDiario> getBasedClass() {
		return BizAgendaDiario.class;
	}
	
	protected boolean loadForeignFields() {
		return true;
	}	

	
@Override
public boolean readAll() throws Exception {
	String rd = getFilterValue("rangodesde");
	String rh = getFilterValue("rangohasta");
	long rangod = 0;
	long rangoh = 23;
	String id = getFilterValue("fechaid");
	Date fechaId = null;
	JRecords<BizEvento> evs =((JWins)( BizUsuario.getUsr().getObjBusiness().getEventosClass().newInstance())).getRecords();
	Date fechaPatron = JDateTools.StringToDate(getFilterValue("fecharef"));
  if (id!=null) {
  	fechaId=JDateTools.StringToDateTime(id); 
  	fechaPatron = fechaId;
  }
  if (rd!=null) rangod=Long.parseLong(rd);
  if (rh!=null) rangoh=Long.parseLong(rh);
  if (fechaPatron==null)
  	fechaPatron=new Date();
  
	evs.addFilter("fecha_fin", JDateTools.getDateStartDay(fechaPatron),">=",true);
	evs.addFilter("fecha_inicio", JDateTools.getDateEndDay(fechaPatron),"<=",true);
	String sPersona = getFilterValue("id_persona");
	if (sPersona!=null && !sPersona.equals("")) {
		evs.addJoin("agd_evento_participantes");
		evs.addFixedFilter("where agd_evento_participantes.id_evento=agd_evento.id_evento and agd_evento_participantes.id_persona="+sPersona);
	}
	RFilter estado = getFilter("estado");
	if (estado!=null && estado.hasValue()) {
		estado.setVirtual(true);
		evs.addFilter("estado", estado.getValue());
	}
	RFilter tipoEvento = getFilter("id_tipo_evento");
	if (tipoEvento!=null && tipoEvento.hasValue()) {
		tipoEvento.setVirtual(true);
		evs.addFilter("id_tipo_evento", tipoEvento.getValue());
	}
	evs.addOrderBy("fecha_inicio","ASC");

	RFilter company = getFilter("company");
	if (company!=null && company.hasValue()) {
		company.setVirtual(true);
		evs.addFilter("company", company.getValue());
	}

	long paso = 60;
	RFilter agenda = getFilter("id_agenda");
	if (agenda!=null && agenda.hasValue()) {
		agenda.setVirtual(true);
		evs.addFilter("id_agenda", agenda.getValue());
		
		BizTurno turno = new BizTurno();
		turno.dontThrowException(true);
		if (turno.read(Long.parseLong(agenda.getValue()))) {
			paso = turno.getTiempoTurno()+turno.getSepTiempoTurno();
			
		}
	}
	if (paso==0)
		paso=60;

	long minutosIniciales = 0;
	long minutosFinales = 24*60;
	setStatic(true);
	this.getStaticItems().removeAllElements();
	
  long h=0;
 	for (h=minutosIniciales;h<=minutosFinales;h+=paso) {
		BizAgendaDiario diario = new BizAgendaDiario();
		Calendar hI = Calendar.getInstance();
	  Calendar hF = Calendar.getInstance();
	  hI.setTime(fechaPatron);
	  hI.set(Calendar.HOUR_OF_DAY, (int)h/60);
	  hI.set(Calendar.MINUTE, (int)( h - (((int)h/60))*60));
	  hI.set(Calendar.SECOND,0);
	  hF.setTime(fechaPatron);
	  hF.set(Calendar.HOUR_OF_DAY, (int)(h+paso-1)/60);
	  hF.set(Calendar.MINUTE, (int)( (h+paso-1) - (((int)(h+paso-1)/60))*60));
	  hF.set(Calendar.SECOND,59);
		diario.setFechaDesde(hI.getTime());
		diario.setFechaHasta(hF.getTime());
		diario.setId(hI.getTime());
		if ((fechaId==null && hI.get(Calendar.HOUR_OF_DAY)>=rangod &&  hI.get(Calendar.HOUR_OF_DAY)<=rangoh))
		   this.getStaticItems().addElement(diario);
		else if (fechaId!=null && ((fechaId.equals(hI.getTime()) || fechaId.after(hI.getTime())) && fechaId.before(hF.getTime())))
		   this.getStaticItems().addElement(diario);
	}
		
	JIterator<BizEvento> it = evs.getStaticIterator();
  while (it.hasMoreElements()) {
  	BizEvento ev=it.nextElement();
		Calendar cI = Calendar.getInstance();
		cI.setTime(ev.getFechaInicio());
		Calendar cF = Calendar.getInstance();
		cF.setTime(ev.getFechaFin());
		
		if (ev.isHito() ) {
			cI.set(Calendar.HOUR_OF_DAY, (int) rangod);
			cF.set(Calendar.HOUR_OF_DAY, (int) rangod);
		}
		
	  this.firstRecord();
	  while (this.nextRecord()) {
	  	BizAgendaDiario diario = this.getRecord();

	  	Calendar dI = Calendar.getInstance();			dI.setTime(diario.getFechaDesde());
			Calendar dF = Calendar.getInstance();			dF.setTime(diario.getFechaHasta());
	
			
			Date ff = cF.getTime().getTime()>diario.getFechaHasta().getTime()?diario.getFechaHasta():cF.getTime();
			Date fi = cI.getTime().getTime()>diario.getFechaDesde().getTime()?cI.getTime():diario.getFechaDesde();
			float ocupacionDia= Math.abs(ff.getTime() - fi.getTime());
			float porcOcupacion = (ocupacionDia*100)/(1000*60*60*1);
				if (cI.equals(dI) || (cI.after(dI) && cI.before(dF))) {
					if (ev.isHito()) {
						diario.addNota("(" + ev.getHoraInicio() + ") <hito> " + ev.getTitulo(), 0);
					diario.getEventos().addItem(ev);
					} else {
						diario.addNota("(" + ev.getHoraInicio() + "-" + ev.getHoraFin() + ") " + ev.getTitulo(), porcOcupacion);
						diario.getEventos().addItem(ev);
					}
				} else {
					if (!ev.isHito() && ((cF.equals(dI) || (cF.after(dI) && cF.before(dF))))) {
						diario.addNota("(" + ev.getHoraFin() + ") Fin de " + ev.getTitulo(), porcOcupacion);
						diario.getEventos().addItem(ev);
					}
					else if (!ev.isHito() && dF.after(cI) && dI.before(cF)) {
						diario.addNota("", porcOcupacion);
						diario.getEventos().addItem(ev);
					}
				}
				
	  }
  }
  
  Calendar cFechaPadron = Calendar.getInstance();
  cFechaPadron.setTime(fechaPatron);
	JRecords<BizFeriado> frds = new JRecords<BizFeriado> (BizFeriado.class);
	evs.addFilter("fecha_fin", JDateTools.getFirstDayOfMonth(fechaPatron),">=",true);
	evs.addFilter("fecha_inicio", JDateTools.getLastDayOfMonth(fechaPatron),"<=",true);
	JIterator<BizFeriado> itf = frds.getStaticIterator();
  while (itf.hasMoreElements()) {
  	BizFeriado fr=itf.nextElement();
		Calendar cI = Calendar.getInstance();
		cI.setTime(fr.getFechaInicio());
		Calendar cF = Calendar.getInstance();
		cF.setTime(fr.getFechaFin());
		
		if (fr.getAnualizar()) {
			cI.set(Calendar.YEAR, cFechaPadron.get(Calendar.YEAR));
			cF.set(Calendar.YEAR, cFechaPadron.get(Calendar.YEAR));
		}
		if (cI.get(Calendar.HOUR_OF_DAY)<rangod) {
			cI.set(Calendar.HOUR_OF_DAY, (int) rangod);
			if (cF.before(cI)) 
				cF=cI;
		}
			
	  this.firstRecord();
	  while (this.nextRecord()) {
	  	BizAgendaDiario diario = this.getRecord();
			Calendar dI = Calendar.getInstance();			dI.setTime(diario.getFechaDesde());
			Calendar dF = Calendar.getInstance();			dF.setTime(diario.getFechaHasta());

			Date ff = fr.getFechaFin().getTime()>diario.getFechaHasta().getTime()?diario.getFechaHasta():fr.getFechaFin();
			Date fi = fr.getFechaInicio().getTime()>diario.getFechaDesde().getTime()?fr.getFechaInicio():diario.getFechaDesde();
//			float ocupacionDia= Math.abs(ff.getTime() - fi.getTime());
			float porcOcupacion = 100;
			
				if (cI.equals(dI) || (cI.after(dI) && cI.before(dF))) {
						diario.addNota( fr.getDescripcion(), porcOcupacion);
				} else {
					if ( ((cF.equals(dI) || (cF.after(dI) && cF.before(dF))))) {
//						diario.addNota("(" + ev.getHoraFin() + ") Fin de " + ev.getTitulo(), porcOcupacion);
					}
					else if (dF.after(cI) && dI.before(cF)) {
						diario.addNota("", porcOcupacion);
					}
				}
			}
	  }
 
	
	this.setStatic(true);
	this.firstRecord();
	return true;
}
}