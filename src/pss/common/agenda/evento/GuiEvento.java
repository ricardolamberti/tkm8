package pss.common.agenda.evento;

import java.util.Date;

import pss.common.agenda.JAgenda;
import pss.common.agenda.historia.GuiEventoHistorias;
import pss.common.agenda.participantes.GuiEventoParticipante;
import pss.common.agenda.participantes.GuiEventosParticipantes;
import pss.common.security.BizUsuario;
import pss.core.services.records.JRecord;
import pss.core.win.JBaseWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActNew;
import pss.core.win.submits.JActSubmit;
import pss.core.win.submits.JActWins;
import pss.core.winUI.forms.JBaseForm;

public class GuiEvento extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiEvento() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizEvento(); }
  public int GetNroIcono()   throws Exception { return 10001; }
  public String GetTitle()   throws Exception { return "Evento"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormEvento.class; }
  public String  getKeyField() throws Exception { return "id_evento"; }
  public String  getDescripField() { return "titulo"; }
  public BizEvento GetcDato() throws Exception { return (BizEvento) this.getRecord(); }

  public void createActionMap() throws Exception {
		addAction(10,   "Participantes", null, 2003, false, false);
		addAction(20,   "Historia", null, 2003, false, false);
		addAction(30,   "Ejecutar", null, 10001, true, true);
		addAction(31,   "Re-Activar", null, 10001, true, true);
		addAction(32,   "Cancelar", null, 7, true, true);
		addAction(40,   "Desactivar Alarma", null, 359, true, true);
		addAction(50,   "Reactivar Alarma", null, 359, true, true);
		addAction(60,   "Agregar Participante", null, 2003, true, true);
		addAction(70,   "Eliminar Def.", null, 2003, true, true).setOnlyInForm(true);
	super.createActionMap();
	}  
  @Override
  public boolean OkAction(BizAction a) throws Exception {
//  	if (a.getId()==30) 
//  		a.setDescrip(GetcDato().getActionDescription());
  	if (a.getId()==60) return GetcDato().canEjecutar();
  	if (a.getId()==30) return GetcDato().canEjecutar();
  	if (a.getId()==31) return !GetcDato().canEjecutar();
  	if (a.getId()==32) return GetcDato().canEjecutar();
  	if (a.getId()==40) return GetcDato().getHasAlarma() && !GetcDato().getReadedAlarma() && GetcDato().getEstado().equals(BizEvento.ACTIVA);
   	if (a.getId()==50) return GetcDato().getHasAlarma() && GetcDato().getReadedAlarma()  && GetcDato().getEstado().equals(BizEvento.ACTIVA);
  	if (a.getId()==70) return GetcDato().getEstado().equals(BizEvento.ELIMINADO);
    return super.OkAction(a);
  }
	
	
	public JAct getSubmit(BizAction a) throws Exception {
		if (a.getId()==10) return new JActWins(this.getParticipantes());
		if (a.getId()==20) return new JActWins(this.getHistoria());
		if (a.getId()==30) return GetcDato().execProcessAction();
		if (a.getId()==31) return new JActSubmit(this) {
			@Override
			public void submit() throws Exception {
				GetcDato().execProcessReActivar();
			}
		};
		if (a.getId()==32) return new JActSubmit(this) {
			@Override
			public void submit() throws Exception {
				GetcDato().execProcessCancelar();
			}
		};
		if (a.getId()==40) return new JActSubmit(this) {
			@Override
			public void submit() throws Exception {
				GetcDato().setReadedAlarma(true);
				GetcDato().execProcessUpdate();
				JAgenda.getAlarmaServer().clearCache(BizUsuario.getUsr().getCompany(),BizUsuario.getUsr().GetUsuario());
			}
		};
		if (a.getId()==50) return new JActSubmit(this) {
			@Override
			public void submit() throws Exception {
				GetcDato().setReadedAlarma(false);
				GetcDato().execProcessUpdate();
				JAgenda.getAlarmaServer().clearCache(BizUsuario.getUsr().getCompany(),BizUsuario.getUsr().GetUsuario());
			}
		};
		if (a.getId()==60) return new JActNew(getNewParticipante(),0);
		if (a.getId()==70) return new JActSubmit(this) {
			public void submit() throws Exception {
				GetcDato().execProcessRealDelete();
			};
		};
		return super.getSubmit(a);
	}
	public GuiEventoParticipante getNewParticipante() throws Exception {
		GuiEventoParticipante p = new GuiEventoParticipante();
		p.getRecord().addFilter("id_evento", GetcDato().getIdevento());
		p.getRecord().addFilter("company", GetcDato().getCompany());
		return p;
	}
	@Override
	public String getFieldForeground(String zColName) throws Exception {
		if (GetcDato().getEstado().equals(BizEvento.ACTIVA) && 
				GetcDato().getFechaFin().before(new Date())) return "ff0000";
		if (GetcDato().getEstado().equals(BizEvento.ACTIVA) &&
				GetcDato().getHasAlarma() &&
				GetcDato().getFechaAlarma().before(new Date())) return "005555";
		return super.getFieldForeground(zColName);
	}
	public JWins getParticipantes() throws Exception {
		JWins records=new GuiEventosParticipantes();
		records.setRecords(GetcDato().getObjParticipantes());
		records.setParent(this);
		return records;
	}
	public JWins getHistoria() throws Exception {
		JWins records=new GuiEventoHistorias();
		records.getRecords().addFilter("company", GetcDato().getCompany());
		records.getRecords().addFilter("id_evento", GetcDato().getIdevento());
		records.getRecords().addOrderBy("fecha","DESC");
		records.setParent(this);
		return records;
	}
	
	public JBaseWin getObjectDrageable(String zone) throws Exception {
		return this;
	}
 }
