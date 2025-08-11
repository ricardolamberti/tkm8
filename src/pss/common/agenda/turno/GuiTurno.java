package  pss.common.agenda.turno;

import pss.common.agenda.diario.GuiAgendaDiarios;
import pss.common.agenda.mensual.GuiAgendaMensuals;
import pss.common.agenda.turno.ausencias.GuiTurnosAusencias;
import pss.common.agenda.turno.laborables.GuiTurnosLaborables;
import pss.common.security.BizUsuario;
import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActWins;
import pss.core.winUI.forms.JBaseForm;

public class GuiTurno extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiTurno() throws Exception {
  } 


  public JRecord ObtenerDato()   throws Exception { return new BizTurno(); }
  public int GetNroIcono()   throws Exception { return 2003; }
  public String GetTitle()   throws Exception { return "Manejador de turno"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormTurno.class; }
  public String  getKeyField() throws Exception { return "id_agenda"; }
  public String  getDescripField() { return "descripcion"; }
  public BizTurno GetcDato() throws Exception { return (BizTurno) this.getRecord(); }
 
  public void createActionMap() throws Exception {
		addAction(10,   "Horario laborable", null, 2003, false, false);
		addAction(20,   "Ausencias programadas", null, 2003, false, false);
		addAction(30,   "Agenda Mensual", null, 2003, true, true);
		addAction(40,   "Agenda Diaria", null, 2003, true, true);
		super.createActionMap();
	}  
  @Override
  public boolean OkAction(BizAction a) throws Exception {
  	if (a.getId()==BizAction.REMOVE) return GetcDato().isNullOwner();
    return super.OkAction(a);
  }
	
 
  @Override
  public int GetDobleClick() throws Exception {
  	return 1;
  }
  @Override
  public int GetSimpleClick() throws Exception {
  	if (GetVision().equals("S"))
    	return 30;
  	return 40;
  }
	
	public JAct getSubmit(BizAction a) throws Exception {
		if (a.getId()==10) return new JActWins(this.getLaborables());
		if (a.getId()==20) return new JActWins(this.getAusencias());
		if ( a.getId()==30 ) return new JActWins(getAgendaMensual(),false,false);
		if ( a.getId()==40 ) return new JActWins(getAgendaDiaria());
		return super.getSubmit(a);
	}
	
	public JWins getAgendaDiaria() throws Exception {
		GuiAgendaDiarios wins= new GuiAgendaDiarios();
		wins.getRecords().addFilter("company", BizUsuario.getUsr().getCompany());
		wins.getRecords().addFilter("id_agenda", GetcDato().getIdAgenda());
		return wins;
	}	
	public JWins getAgendaMensual() throws Exception {
		GuiAgendaMensuals wins= new GuiAgendaMensuals();
		wins.getRecords().addFilter("company", BizUsuario.getUsr().getCompany());
		wins.getRecords().addFilter("id_agenda", GetcDato().getIdAgenda());
		return wins;
	}	
	public JWins getLaborables() throws Exception {
		JWins records=new GuiTurnosLaborables();
		records.getRecords().addFilter("company", GetcDato().getCompany());
		records.getRecords().addFilter("id_agenda", GetcDato().getIdAgenda());
		records.getRecords().addOrderBy("id","ASC");
		records.setParent(this);
		return records;
	}
	public JWins getAusencias() throws Exception {
		JWins records=new GuiTurnosAusencias();
		records.getRecords().addFilter("company", GetcDato().getCompany());
		records.getRecords().addFilter("id_agenda", GetcDato().getIdAgenda());
		records.getRecords().addOrderBy("id","ASC");
		records.setParent(this);
		return records;
	}

 }
