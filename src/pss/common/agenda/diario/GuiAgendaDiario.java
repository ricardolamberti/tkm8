package  pss.common.agenda.diario;

import java.awt.event.KeyEvent;
import java.util.Date;

import com.ibm.icu.util.Calendar;

import pss.common.agenda.evento.BizEvento;
import pss.common.agenda.evento.GuiEvento;
import pss.common.security.BizUsuario;
import pss.core.services.records.JRecord;
import pss.core.tools.JDateTools;
import pss.core.win.JBaseWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActNew;
import pss.core.win.submits.JActQuery;
import pss.core.win.submits.JActUpdate;
import pss.core.win.submits.JActWins;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.icons.GuiIcon;

public class GuiAgendaDiario extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiAgendaDiario() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizAgendaDiario(); }
  public int GetNroIcono()   throws Exception { return 10058; }
  public String GetTitle()   throws Exception { return "Semana"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormAgendaDiario.class; }
  @Override
  public Class<? extends JBaseForm> getFormFlat() throws Exception {
  	return FormAgendaDiario.class;
  }
  public String  getKeyField() throws Exception { return "fechaid"; }
  public String  getDescripField() { return "fechahora"; }
  public BizAgendaDiario GetcDato() throws Exception { return (BizAgendaDiario) this.getRecord(); }
  public void createActionMap() throws Exception {
		addAction(30,   "Ver", null, 10001, true, true);
		addAction(40,   "Nuevo evento", null, 10001, true, true);
		addAction(1, "Consultar", KeyEvent.VK_ENTER, null, GuiIcon.CONSULTAR_ICON, false, false);
		addAction(50, "Consultar EVENTO", null,10001, false, false);

  }
  public JAct getSubmit(BizAction a) throws Exception {
		if (a.getId()==30) return new JActWins(getAgendaHora(),false);
		if (a.getId()==40) return new JActNew(getNewEvento(),0);
		if (a.getId()==50) return new JActQuery(getEvento((BizEvento)a.getData()));

		return super.getSubmit(a);
	}
	public int GetDobleClick() throws Exception {
		return 30;
	}
	public int GetSimpleClick() throws Exception {
		return 30;
	}
	public boolean isWordWrap(String zColName)  throws Exception {
		return true;
	}

	public int getHeightRow() throws Exception {
		return 40;
	}
	public int getWidthCol(String zColName)  throws Exception {
		if (zColName.equals("hora")) return 70;
		return super.getWidthCol(zColName);
	}
	@Override
	public String getFieldBackground(String zColName) throws Exception {
		return  (GetcDato().getBackgroundColor());
	}
	
	
	@Override
	public boolean canConvertToURL() throws Exception {
			return false;
	}
	public GuiEvento getEvento(BizEvento ev) throws Exception {
		GuiEvento win = BizUsuario.getUsr().getObjBusiness().getNewEventos();
		win.setRecord(ev);
		return win;
	}
	public JWins getAgendaHora() throws Exception {
		JWins a =(JWins) BizUsuario.getUsr().getObjBusiness().getEventosClass().newInstance();
		if (!BizUsuario.getUsr().isAdminUser()) 
			a.getRecords().addFilter("company", BizUsuario.getUsr().getCompany());
		a.getRecords().addFilter("fecha_i", GetcDato().getFechaDesde(),">=",true).setVirtual(true);
		a.getRecords().addFilter("fecha_f", GetcDato().getFechaHasta(),"<=",true).setVirtual(true);
		if (GetcDato().hasIdAgenda()) {
			a.getRecords().addFilter("company", BizUsuario.getUsr().getCompany());
			a.getRecords().addFilter("id_agenda", GetcDato().getIdAgenda());
		}
		return a;
	}
	
	public GuiEvento getNewEvento() throws Exception {
		GuiEvento a = BizUsuario.getUsr().getObjBusiness().getNewEventos();
		a.getRecord().addFilter("company", BizUsuario.getUsr().getCompany());
		a.getRecord().addFilter("id_agenda", GetcDato().getIdAgenda());
		a.getRecord().addFilter("fecha_inicio", GetcDato().getFechaDesde(),true);
		return a;
	}
	
	@Override
	public boolean acceptDrop(String zone) throws Exception {
		return true;
	}
	@Override
	public JAct Drop(JBaseWin zBaseWin) throws Exception {
		// TODO Auto-generated method stub
		if (zBaseWin instanceof GuiEvento) {
			GuiEvento ev = (GuiEvento) zBaseWin;
			
			Date fi = ev.GetcDato().getFechaInicio();
			Date nfi = this.GetcDato().getFechaDesde();
			Date ff = ev.GetcDato().getFechaFin();
			Date fa = ev.GetcDato().getFechaAlarma();
			
			Calendar cfa = Calendar.getInstance();
			cfa.setTime(fa);
			cfa.add(Calendar.MINUTE, JDateTools.getMinutesBetween(fi, nfi));
			Calendar cfi = Calendar.getInstance();
			cfi.setTime(ff);
			cfi.add(Calendar.MINUTE, JDateTools.getMinutesBetween(fi, nfi));
			
			
			ev.GetcDato().setFechaInicio(this.GetcDato().getFechaDesde());
			ev.GetcDato().setFechaFin(cfi.getTime());
			ev.GetcDato().setFechaAlarma(cfa.getTime());
			ev.GetcDato().execProcessUpdate();
			return null;
		}
		return super.Drop(zBaseWin);
	}
 }
