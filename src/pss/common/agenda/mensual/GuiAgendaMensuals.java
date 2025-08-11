package pss.common.agenda.mensual;

import java.util.Date;

import pss.common.agenda.evento.BizEvento;
import pss.common.agenda.evento.GuiEvento;
import pss.common.agenda.evento.tipo.GuiTipoEventos;
import pss.common.agenda.feriado.GuiFeriados;
import pss.common.agenda.turno.BizTurno;
import pss.common.agenda.turno.GuiTurnos;
import pss.common.personalData.GuiPersonas;
import pss.common.regions.company.GuiCompanies;
import pss.common.security.BizUsuario;
import pss.core.services.records.JRecords;
import pss.core.tools.JDateTools;
import pss.core.win.JControlWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActNew;
import pss.core.win.submits.JActWins;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.lists.JFormFiltro;
import pss.core.winUI.lists.JWinList;

public class GuiAgendaMensuals extends JWins {

	/**
	 * Constructor de la Clase
	 */
	public GuiAgendaMensuals() throws Exception {
	}

	@Override
	public JRecords<? extends BizAgendaMensual> ObtenerDatos() throws Exception {
		return new BizAgendaMensuals();
	}

	public int GetNroIcono() throws Exception {
		return 10001;
	}

	public String GetTitle() throws Exception {
		Date fechaPatron = this.getFilterValue("fecha")==null?new Date():JDateTools.StringToDate(this.getFilterValue("fecha"));
		String title = JDateTools.getMonthDescr(fechaPatron) + " de "+JDateTools.getYearNumber(fechaPatron);
		
		BizTurno turno = getAgenda();
		if (turno != null) {
			return title +" para "+turno.getDescripcion();
		}
		
		return title;

	}

	public boolean hasAgenda() throws Exception {
		return getFilterValue("id_agenda") != null;
	}

	public BizTurno getAgenda() throws Exception {
		if (!hasAgenda())
			return null;
		BizTurno turno = new BizTurno();
		turno.dontThrowException(true);
		if (!turno.read(Long.parseLong(getFilterValue("id_agenda"))))
			return null;
		return turno;
	}

	public Class<? extends JWin> GetClassWin() {
		return GuiAgendaMensual.class;
	}

	/**
	 * Mapeo las acciones con las operaciones
	 */
	public void createActionMap() throws Exception {
		addActionNew(1, "Nuevo Evento");
		addAction(10, "Feriados", null, 2003, true, true);
	}

	@Override
	public JAct getSubmit(BizAction a) throws Exception {
		if (a.getId() == 1)
			return new JActNew(this.getNewWin(), 0);
		if (a.getId() == 10)
			return new JActWins(this.getFeriados());
		return super.getSubmit(a);
	}

	public JWin getNewWin() throws Exception {
		GuiEvento ev =  BizUsuario.getUsr().getObjBusiness().getNewEventos();
//		if (!BizUsuario.getUsr().isAdminUser()) {
		ev.getRecord().addFilter("company", BizUsuario.getUsr().getCompany());
//		}
		this.joinData(ev);

		return ev;
	}

	public JWins getFeriados() throws Exception {
		GuiFeriados g = new GuiFeriados();
		if (!BizUsuario.getUsr().isAdminUser())
			g.getRecords().addFilter("company", BizUsuario.getUsr().getCompany());
		return g;
	}

	@Override
	public void ConfigurarFiltros(JFormFiltro zFiltros) throws Exception {
		if (getFilterValue("id_agenda") == null)
			zFiltros.addComboResponsive("Agenda", "id_agenda", getAgendas());
		if (BizUsuario.getUsr().IsAdminUser() && getFilterValue("company") == null)
			zFiltros.addComboResponsive("Company", "company", new GuiCompanies());

		JFormControl f = zFiltros.addCDateResponsive("Fecha Referencia", "fecha");
		f.SetValorDefault(new Date());
		f.setRefreshForm(true);

		f = zFiltros.addComboResponsive("Estado", "estado", JWins.createVirtualWinsFromMap(BizEvento.getEstados()));
		f.SetValorDefault(BizEvento.ACTIVA);
		f = zFiltros.addComboResponsive("Tipo Evento", "id_tipo_evento", getTipoEventos());
		f = zFiltros.addWinLovResponsive("Participante", "id_persona", new JControlWin() {
			public JWins getRecords(boolean bOneRow) throws Exception {
				return new GuiPersonas();
			};
		});

		super.ConfigurarFiltros(zFiltros);
	}

	public JWins getTipoEventos() throws Exception {
		GuiTipoEventos wins = new GuiTipoEventos();
		if (!BizUsuario.getUsr().isAdminUser())
			wins.getRecords().addFilter("company", BizUsuario.getUsr().getCompany());
		return wins;
	}

	public JWins getAgendas() throws Exception {
		GuiTurnos wins = new GuiTurnos();
		if (!BizUsuario.getUsr().isAdminUser())
			wins.getRecords().addFilter("company", BizUsuario.getUsr().getCompany());
		return wins;
	}

	public boolean isWithPreview(boolean embedded, boolean formLov) throws Exception {
		return false;
	}

	/**
	 * Configuro las columnas que quiero mostrar en la grilla
	 */
	public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
		if (GetVision().equals("SIMPLE")) {
			zLista.AddColumnaLista("lunes_f");
			zLista.AddColumnaLista("martes_f");
			zLista.AddColumnaLista("miercoles_f");
			zLista.AddColumnaLista("jueves_f");
			zLista.AddColumnaLista("viernes_f");
			zLista.AddColumnaLista("sabado_f");
			zLista.AddColumnaLista("domingo_f");
			return;
		}
		zLista.addColumnWinAction("lunes", 90).setForceTitle(true);;
		zLista.addColumnWinAction("martes", 91).setForceTitle(true);
		zLista.addColumnWinAction("miercoles", 92).setForceTitle(true);
		zLista.addColumnWinAction("jueves", 93).setForceTitle(true);
		zLista.addColumnWinAction("viernes", 94).setForceTitle(true);
		zLista.addColumnWinAction("sabado", 95).setForceTitle(true);
		zLista.addColumnWinAction("domingo", 96).setForceTitle(true);

	}

}
