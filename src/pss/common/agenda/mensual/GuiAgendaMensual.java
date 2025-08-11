package  pss.common.agenda.mensual;

import java.util.Calendar;
import java.util.Date;

import pss.common.agenda.diario.GuiAgendaDiario;
import pss.common.agenda.diario.GuiAgendaDiarios;
import pss.common.agenda.evento.FormEvento;
import pss.common.agenda.evento.GuiEvento;
import pss.core.services.records.JRecord;
import pss.core.tools.JDateTools;
import pss.core.tools.JTools;
import pss.core.win.JBaseWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActQuery;
import pss.core.win.submits.JActWins;
import pss.core.winUI.forms.JBaseForm;

public class GuiAgendaMensual extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiAgendaMensual() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizAgendaMensual(); }
  public int GetNroIcono()   throws Exception { return 10058; }
  public String GetTitle()   throws Exception { return "Semana"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormEvento.class; }
  public String  getKeyField() throws Exception { return "id"; }
  public String  getDescripField() { return "id"; }
  public BizAgendaMensual GetcDato() throws Exception { return (BizAgendaMensual) this.getRecord(); }
  public void createActionMap() throws Exception {
		addAction(30,   "Ver", null, 10001, true, true);
		addAction(90,   "lunes", null, 10001, false, false);
		addAction(91,   "martes", null, 10001, false, false);
		addAction(92,   "miercoles", null, 10001, false, false);
		addAction(93,   "jueves", null, 10001, false, false);
		addAction(94,   "viernes", null, 10001, false, false);
		addAction(95,   "sabado", null, 10001, false, false);
		addAction(96,   "domingo", null, 10001, false, false);
  }
  public JAct getSubmit(BizAction a) throws Exception {
		if (a.getId()==30) return new JActWins(getAgendaDiaria(),false);
		if (a.getId()==90) return new JActQuery(getDia(2));
		if (a.getId()==91) return new JActQuery(getDia(3));
		if (a.getId()==92) return new JActQuery(getDia(4));
		if (a.getId()==93) return new JActQuery(getDia(5));
		if (a.getId()==94) return new JActQuery(getDia(6));
		if (a.getId()==95) return new JActQuery(getDia(7));
		if (a.getId()==96) return new JActQuery(getDia(1));

		return super.getSubmit(a);
	}
	public boolean isWordWrap(String zColName)  throws Exception {
		return true;
	}
	public boolean isMarked(String zColName) throws Exception {
		if (GetcDato().getFecha()==null) return false;
		Calendar fechaPatron = Calendar.getInstance();
		fechaPatron.setTime(JDateTools.getDateStartDay(GetcDato().getFechaPatron()));
		Calendar fecha = Calendar.getInstance();
		fecha.setTime(GetcDato().getFechaColumna(zColName));
	
		return fechaPatron.equals(fecha);
	}
	public int GetDobleClick() throws Exception {
		return 30;
	}
	

	public int getHeightRow() throws Exception {
		return 90;
	}
	public int getWidthCol(String zColName)  throws Exception {
		return 120;
	}
	@Override
	public String getFieldBackground(String zColName) throws Exception {
		return  (GetcDato().getBackgroundColor(zColName));
	}
	@Override
	public String getFieldForeground(String zColName) throws Exception {
		return  (GetcDato().getForegroundColor(zColName));
	}
	public JWins getAgendaDiaria() throws Exception {
		JWins a = new GuiAgendaDiarios();
		a.getRecords().addFilter("fecharef", GetcDato().getFechaFromColumna(this.getSelectedCell()));
		if (GetcDato().getIdPersona()!=0) a.getRecords().addFilter("id_persona", GetcDato().getIdPersona());
		return a;
	}
	@Override
	public boolean canConvertToURL() throws Exception {
			return false;
	}
	
	public JWin getDia(int day) throws Exception {
		Date fechaInicioSemana = GetcDato().getFecha();
		if (fechaInicioSemana==null) return new GuiAgendaDiario();
		Calendar cFechaDia = Calendar.getInstance();
		cFechaDia.setTime(fechaInicioSemana);
		int realDay = day==1?6:day-2;
		cFechaDia.add(Calendar.DAY_OF_MONTH, realDay);
		
		
		GuiAgendaDiario dia = new GuiAgendaDiario();
		dia.GetcDato().setFechaDesde(JDateTools.getDateStartDay(cFechaDia.getTime()));
		dia.GetcDato().setFechaHasta(JDateTools.getDateEndDay(cFechaDia.getTime()));
		dia.GetcDato().setEventos(GetcDato().getEventos(day));
		dia.GetcDato().setIdAgenda(GetcDato().getIdAgenda());
		dia.GetcDato().setCompany(GetcDato().getCompany());
		dia.GetcDato().setTexto("Día "+cFechaDia.get(Calendar.DAY_OF_MONTH)+" ("+JTools.forceRd(GetcDato().getOcupacion(realDay), 2)+"%):");
		dia.GetcDato().setPorcentaje(GetcDato().getOcupacion(day));
		
		dia.SetVision("MENSUAL");
		
		return dia;
	}
	
	
	@Override
	public boolean acceptDrop(String zone) throws Exception {
		return true;
	}
	@Override
	public String getZoneDrop(String zone) throws Exception {
		if (zone.startsWith("field_"))
			return zone.substring(zone.indexOf("_")+1);
		return super.getZoneDrop(zone);
	}
	

	@Override
	public JAct Drop(JBaseWin zBaseWin) throws Exception {
		if (zBaseWin instanceof GuiEvento) {
			GuiEvento ev = (GuiEvento) zBaseWin;
			
			Date fi = ev.GetcDato().getFechaInicio();
			Date nfi = this.GetcDato().getFechaColumna(ev.GetVision());
			Date ff = ev.GetcDato().getFechaFin();
			Date fa = ev.GetcDato().getFechaAlarma();
			Calendar cfi = Calendar.getInstance();
			cfi.setTime(fi);

			Calendar cnfi = Calendar.getInstance();
			cnfi.setTime(nfi);
			cnfi.set(Calendar.HOUR_OF_DAY, cfi.get(Calendar.HOUR_OF_DAY));
			cnfi.set(Calendar.MINUTE, cfi.get(Calendar.MINUTE));
			cnfi.set(Calendar.SECOND, cfi.get(Calendar.SECOND));
			
			int minutesDif = JDateTools.getMinutesBetween(fi, cnfi.getTime());
			Calendar cfa = Calendar.getInstance();
			cfa.setTime(fa);
			cfa.add(Calendar.MINUTE, minutesDif );

			Calendar cff = Calendar.getInstance();
			cff.setTime(ff);
			cff.add(Calendar.MINUTE, minutesDif);
			
			
			ev.GetcDato().setFechaInicio(cnfi.getTime());
			ev.GetcDato().setFechaFin(cff.getTime());
			ev.GetcDato().setFechaAlarma(cfa.getTime());
			ev.GetcDato().execProcessUpdate();
			return null;
		}

		return super.Drop(zBaseWin); 
	}
 }
