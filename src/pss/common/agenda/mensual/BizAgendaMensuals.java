package  pss.common.agenda.mensual;

import java.util.Calendar;
import java.util.Date;

import pss.common.agenda.evento.BizEvento;
import pss.common.agenda.feriado.BizFeriado;
import pss.common.security.BizUsuario;
import pss.core.data.interfaces.structure.RFilter;
import pss.core.services.records.JRecords;
import pss.core.tools.JDateTools;
import pss.core.tools.collections.JIterator;
import pss.core.win.JWins;

public class BizAgendaMensuals extends JRecords<BizAgendaMensual> {
	
	public Class<BizAgendaMensual> getBasedClass() {
		return BizAgendaMensual.class;
	}
	
	protected boolean loadForeignFields() {
		return true;
	}	
	
	String strFilters;
	public String getDescrFiltros() throws Exception {
		return strFilters;
	}
	
@Override
public boolean readAll() throws Exception {
	String descr = "";
	JRecords<BizEvento> evs =((JWins)( BizUsuario.getUsr().getObjBusiness().getEventosClass().newInstance())).getRecords();
	Date fechaPatron = JDateTools.StringToDate(getFilterValue("fecha"));
	if (fechaPatron==null) fechaPatron = new Date(); 

	descr += (descr.equals("")?"":" - ")+JDateTools.getMonthDescr(fechaPatron);
	if (getFilterValue("estado")!=null) descr += (descr.equals("")?"":" - ")+"Estado: "+getFilterValue("estado");
	if (getFilterValue("id_tipo_evento")!=null) descr += (descr.equals("")?"":" - ")+"Tipo evento: "+getFilterValue("id_tipo_evento");
	strFilters=descr;

	String sPersona = getFilterValue("id_persona");
	if (sPersona!=null && !sPersona.equals("")) {
		evs.addJoin("agd_evento_participantes");
		evs.addFixedFilter("where agd_evento_participantes.id_evento=agd_evento.id_evento and agd_evento_participantes.id_persona="+sPersona);
	}
	

	RFilter tipoEvento = getFilter("id_tipo_evento");
	if (tipoEvento!=null && tipoEvento.hasValue()) {
		tipoEvento.setVirtual(true);
		evs.addFilter("id_tipo_evento", tipoEvento.getValue());
	}
	
	RFilter company = getFilter("company");
	if (company!=null && company.hasValue()) {
		company.setVirtual(true);
		evs.addFilter("company", company.getValue());
	}

	RFilter agenda = getFilter("id_agenda");
	if (agenda!=null && agenda.hasValue()) {
		agenda.setVirtual(true);
		evs.addFilter("id_agenda", agenda.getValue());
	}

	
	evs.addOrderBy("fecha_inicio","ASC");
	
	Date start = JDateTools.getFirstDayOfMonth(fechaPatron);
	Calendar cs = Calendar.getInstance();
	cs.setTime(start);

	Date end = JDateTools.getLastDayOfMonth(fechaPatron);
	Calendar ce = Calendar.getInstance();
	ce.setTime(end);
  int firstWeek = cs.get(Calendar.WEEK_OF_YEAR);
	int lastWeek = ce.get(Calendar.WEEK_OF_YEAR);
	
	if (firstWeek>=lastWeek) {
		// ce.getWeeksInWeekYear() remplazaria al 52, pero no funciona en java 1.6 y me genera algunos problema
		lastWeek=lastWeek+52+((ce.get(Calendar.DAY_OF_WEEK)>=4)?0:1);
	}
	Date firstFecha=null;
	Calendar lastFecha=null;
	setStatic(true);
	this.getStaticItems().removeAllElements();
  int w=0;
 	for (w=firstWeek;w<=lastWeek;w++) {
		BizAgendaMensual semana = new BizAgendaMensual();
		Calendar wI = Calendar.getInstance();
	  wI.clear();
	  wI.set(Calendar.WEEK_OF_YEAR, w);
	  wI.set(Calendar.YEAR, cs.get(Calendar.YEAR));
	  if (firstFecha==null)
	  	firstFecha=wI.getTime();
	  lastFecha=wI;
	  semana.setFecha(wI.getTime());
	  semana.setFechaPatron(fechaPatron);
	  semana.getStructure().copyFiltersFrom(this.getStructure());
	  this.getStaticItems().addElement(semana);
	}
  lastFecha.add(Calendar.DAY_OF_MONTH,6);
 	
	evs.addFilter("fecha_fin", JDateTools.getDateStartDay( firstFecha),">=",true);
	evs.addFilter("fecha_inicio",JDateTools.getDateEndDay(lastFecha.getTime()),"<=",true);
	RFilter estado = getFilter("estado");
	if (estado!=null && estado.hasValue()) {
		estado.setVirtual(true);
		evs.addFilter("estado", estado.getValue());
	}
		
	JIterator<BizEvento> it = evs.getStaticIterator();
  while (it.hasMoreElements()) {
  	BizEvento ev=it.nextElement();
		Calendar cI = Calendar.getInstance();
		cI.setTime(ev.getFechaInicio());
		Calendar cF = Calendar.getInstance();
		cF.setTime(ev.getFechaFin());
	
	  this.firstRecord();
	  while (this.nextRecord()) {
	  	BizAgendaMensual semana = this.getRecord();
			Calendar d = Calendar.getInstance();			
			d.setTime(JDateTools.getDateStartDay(semana.getFecha()));
			for (int dia=0;dia<7;dia++) {
				Calendar dI = Calendar.getInstance();			
				dI.setTime(JDateTools.getDateStartDay(semana.getFecha()));
			  dI.add(Calendar.DAY_OF_MONTH, dia);
				Calendar dF = Calendar.getInstance();
				dF.setTime(JDateTools.getDateEndDay(dI.getTime()));
				
				Date ff = ev.getFechaFin().getTime() > dF.getTime().getTime()?dF.getTime():ev.getFechaFin();
				Date fi = ev.getFechaInicio().getTime()>dI.getTime().getTime()?ev.getFechaInicio():dI.getTime();
				float ocupacionDia= Math.abs(ff.getTime() - fi.getTime());
				float porcOcupacion = (ocupacionDia*100)/(1000*60*60*8);
			
				if (cI.equals(dI) || (cI.after(dI) && cI.before(dF))) {
					if (ev.isHito()) {
						semana.addNota(dI.get(Calendar.DAY_OF_WEEK),"(" + ev.getHoraInicio() + ") (hito) " + ev.getTitulo(), 0);
						semana.getEventos(dI.get(Calendar.DAY_OF_WEEK)).addItem(ev);
					} else {
						semana.addNota(dI.get(Calendar.DAY_OF_WEEK),"(" + ev.getHoraInicio() + "-" + ev.getHoraFin() + ") " + ev.getTitulo(), porcOcupacion);
						semana.getEventos(dI.get(Calendar.DAY_OF_WEEK)).addItem(ev);
					}
				} else {
					if (!ev.isHito() && ((cF.equals(dI) || (cF.after(dI) && cF.before(dF))))) {
						semana.addNota(dI.get(Calendar.DAY_OF_WEEK),"(" + ev.getHoraFin() + ") Fin de " + ev.getTitulo(), porcOcupacion);
						semana.getEventos(dI.get(Calendar.DAY_OF_WEEK)).addItem(ev);
					}
					else if (!ev.isHito() && dF.after(cI) && dI.before(cF)) {
						semana.addNota(dI.get(Calendar.DAY_OF_WEEK),"", porcOcupacion);
						semana.getEventos(dI.get(Calendar.DAY_OF_WEEK)).addItem(ev);
					}
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

	  this.firstRecord();
	  while (this.nextRecord()) {
	  	BizAgendaMensual semana = this.getRecord();
			Calendar d = Calendar.getInstance();			
			d.setTime(JDateTools.getDateStartDay(semana.getFecha()));
			for (int dia=0;dia<7;dia++) {
				Calendar dI = Calendar.getInstance();			
				dI.setTime(JDateTools.getDateStartDay(semana.getFecha()));
			  dI.add(Calendar.DAY_OF_MONTH, dia);
				Calendar dF = Calendar.getInstance();
				dF.setTime(JDateTools.getDateEndDay(dI.getTime()));
				
				Date ff = fr.getFechaFin().getTime() > dF.getTime().getTime()?dF.getTime():fr.getFechaFin();
				Date fi = fr.getFechaInicio().getTime()>dI.getTime().getTime()?fr.getFechaInicio():dI.getTime();
//				float ocupacionDia= Math.abs(ff.getTime() - fi.getTime());
				float porcOcupacion = 100;
			
				if (cI.equals(dI) || (cI.after(dI) && cI.before(dF))) {
						semana.addNota(dI.get(Calendar.DAY_OF_WEEK),fr.getDescripcion(), porcOcupacion);
				} else {
					if (((cF.equals(dI) || (cF.after(dI) && cF.before(dF))))) {
				//		semana.addNota(dI.get(Calendar.DAY_OF_WEEK),"(" + fr.getHoraFin() + ") Fin de " + fr.getDescripcion(), porcOcupacion);
					}
					else if (dF.after(cI) && dI.before(cF)) {
						semana.addNota(dI.get(Calendar.DAY_OF_WEEK),"", porcOcupacion);
					}
				}
			}
	  }
  }
	
	this.firstRecord();
	return true;
}
}
