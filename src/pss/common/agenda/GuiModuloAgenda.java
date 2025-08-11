package pss.common.agenda;

import pss.common.agenda.diario.GuiAgendaDiarios;
import pss.common.agenda.evento.GuiEventos;
import pss.common.agenda.evento.tipo.GuiTipoEventos;
import pss.common.agenda.feriado.GuiFeriados;
import pss.common.agenda.mensual.GuiAgendaMensuals;
import pss.common.agenda.rol.GuiEventoRoles;
import pss.common.agenda.turno.GuiTurnos;
import pss.common.security.BizUsuario;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.modules.GuiModulo;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActWins;

public class GuiModuloAgenda extends GuiModulo {

	public GuiModuloAgenda() throws Exception {
		super();
		SetModuleName("Agenda");
		SetNroIcono(10001);
	}

	@Override
	public BizAction createDynamicAction() throws Exception {
		return this.addAction(1, "Agenda", null, 10001, true, true, true,	"Group");
	}

	
	 @Override
		public void createActionMap() throws Exception {
	  	this.addAction( 10, "Tipo Eventos" , null , 157 , true, true, true, "Group");
	  	this.addAction( 15, "Eventos" , null , 10 , true, true, true, "Group");
	  	this.addAction( 20, "Roles" , null , 10 , true, true, true, "Group");
	  	this.addAction( 40, "Feriados" , null , 10 , true, true, true, "Group");
	  	this.addAction( 50, "Manejador Turnos" , null , 10 , true, true, true, "Group");
	  	this.addAction( 60, "Agenda Mensual" , null , 10 , true, true, true, "Group");
	  	this.addAction( 70, "Agenda Diaria" , null , 10 , true, true, true, "Group");
	  	this.loadDynamicActions(null);
	  }

		public JAct getSubmitFor(BizAction a) throws Exception {
			if ( a.getId()==10 ) return new JActWins(getTipoEventos());
			if ( a.getId()==15 ) return new JActWins(getEventos());
			if ( a.getId()==20 ) return new JActWins(getRoles());
			if ( a.getId()==40 ) return new JActWins(getFeriados());
			if ( a.getId()==50 ) return new JActWins(getManejTurnos());
			if ( a.getId()==60 ) return new JActWins(getAgendaMensual(),false,false);
			if ( a.getId()==70 ) return new JActWins(getAgendaDiaria());
			return null;
	  }
	  
		public JWins getAgendaDiaria() throws Exception {
			GuiAgendaDiarios wins= new GuiAgendaDiarios();
			if (!BizUsuario.getUsr().isAdminUser())
				wins.getRecords().addFilter("company", BizUsuario.getUsr().getCompany());
			wins.SetVision("SIMPLE");
			return wins;
		}	
		public JWins getAgendaMensual() throws Exception {
			GuiAgendaMensuals wins= new GuiAgendaMensuals();
			if (!BizUsuario.getUsr().isAdminUser())
				wins.getRecords().addFilter("company", BizUsuario.getUsr().getCompany());
			return wins;
		}	

		public JWins getTipoEventos() throws Exception {
			GuiTipoEventos g = new GuiTipoEventos();
			if (!BizUsuario.getUsr().isAdminUser()) 
				g.getRecords().addFilter("company", BizUsuario.getUsr().getCompany());
	    return g;
	  }
		
		public JWins getEventos() throws Exception {
			GuiEventos g = (GuiEventos) BizUsuario.getUsr().getObjBusiness().getEventosClass().newInstance();
;
			if (!BizUsuario.getUsr().isAdminUser()) 
				g.getRecords().addFilter("company", BizUsuario.getUsr().getCompany());
	    return g;
	  }
		
		public JWins getRoles() throws Exception {
			GuiEventoRoles g = new GuiEventoRoles();
			if (!BizUsuario.getUsr().isAdminUser()) 
				g.getRecords().addFilter("company", BizUsuario.getUsr().getCompany());
	    return g;
	  }	
		public JWins getFeriados() throws Exception {
			GuiFeriados g = new GuiFeriados();
			if (!BizUsuario.getUsr().isAdminUser()) 
				g.getRecords().addFilter("company", BizUsuario.getUsr().getCompany());
	    return g;
	  }	
		public JWins getManejTurnos() throws Exception {
			GuiTurnos g = new GuiTurnos();
			if (!BizUsuario.getUsr().isAdminUser()) 
				g.getRecords().addFilter("company", BizUsuario.getUsr().getCompany());
	    return g;
	  }
}
