package  pss.common.agenda.diario;

import java.util.Date;

import pss.common.agenda.evento.BizEvento;
import pss.common.agenda.evento.GuiEvento;
import pss.common.agenda.evento.tipo.GuiTipoEventos;
import pss.common.agenda.turno.BizTurno;
import pss.common.agenda.turno.GuiTurnos;
import pss.common.regions.company.GuiCompanies;
import pss.common.security.BizUsuario;
import pss.core.services.records.JRecords;
import pss.core.tools.JDateTools;
import pss.core.tools.JTools;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActNew;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.lists.JFormFiltro;
import pss.core.winUI.lists.JWinList;

public class GuiAgendaDiarios  extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiAgendaDiarios() throws Exception {
  }


	@Override
	public JRecords<? extends BizAgendaDiario> ObtenerDatos() throws Exception {
		return new BizAgendaDiarios();
	}
  public int     GetNroIcono() throws Exception  { return 10001; } 
  public String  GetTitle()    throws Exception  { 
		Date fechaPatron = this.getFilterValue("fecharef")==null?new Date():JDateTools.StringToDate(this.getFilterValue("fecharef"));
		String title = JDateTools.DateToString(fechaPatron);

		BizTurno turno = getAgenda();
  	if (turno!=null) { 
  		return title+" de "+turno.getDescripcion();
  	}
  	return title;
  	
  }
  public Class<? extends JWin>  GetClassWin()                   { return GuiAgendaDiario.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  public void createActionMap() throws Exception {
    addActionNew( 1, "Nuevo Evento" );
//		addAction(30,   "Ver mes", null, 10001, true, true);
 }
  
  public boolean hasAgenda() throws Exception {
  	return getFilterValue("id_agenda")!=null;
  }
  public BizTurno getAgenda() throws Exception {
  	if (!hasAgenda()) return null;
  	BizTurno turno = new BizTurno();
  	turno.dontThrowException(true);
  	if (!turno.read(Long.parseLong(getFilterValue("id_agenda")))) return null;
  	return turno;
  }

  
  @Override
  public JAct getSubmit(BizAction a) throws Exception {
		if (a.getId()==1) return new JActNew(this.getNewWin(), 0);
//		if (a.getId()==30) return new JActWinsSelect(getAgendaMes(),this);
  	return super.getSubmit(a);
  }
  @Override
  protected boolean checkActionOnDrop(BizAction a) throws Exception {
  	if (a.getId()==30) return true;
  	return super.checkActionOnDrop(a);
  }
	public JWin getNewWin() throws Exception {
		GuiEvento ev = BizUsuario.getUsr().getObjBusiness().getNewEventos();
//		if (!BizUsuario.getUsr().isAdminUser()) {
			ev.getRecord().addFilter("company", BizUsuario.getUsr().getCompany());
//		}
			this.joinData(ev);

		return ev;
	}
//	public JWins getAgendaMes() throws Exception {
//		JWins a = new GuiAgendaMensuals();
//		a.getRecords().addFilter("fecha",(String)this.getFilters().getElement("Fecha Referencia").get(0)).setDynamic(true);
//		return a;
//	}
  @Override
  public void ConfigurarFiltros(JFormFiltro zFiltros) throws Exception {
   	if (getFilterValue("id_agenda")==null)
   		zFiltros.addComboResponsive("Agenda", "id_agenda", getAgendas());
   	if (BizUsuario.getUsr().IsAdminUser() && getFilterValue("company")==null)
   		zFiltros.addComboResponsive("Company", "company", new GuiCompanies());

   	JFormControl f = zFiltros.addCDateResponsive("Fecha Referencia", "fecharef");
  	f.SetValorDefault(new Date());
  	f.setRefreshForm(true);
  	f = zFiltros.addComboResponsive("", "rangodesde", getHorarios());
  	f.SetValorDefault("08");
  	f = zFiltros.addComboResponsive("", "rangohasta", getHorarios());
  	f.SetValorDefault("18");
  	f = zFiltros.addComboResponsive("Estado", "estado", JWins.createVirtualWinsFromMap(BizEvento.getEstados()));
  	f.SetValorDefault(BizEvento.ACTIVA);
  	f = zFiltros.addComboResponsive("Tipo Evento", "id_tipo_evento", getTipoEventos());

  	super.ConfigurarFiltros(zFiltros);
  }

  @Override
  public boolean isWebPageable() {
  	return false;
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
  static JWins horarios = null;
  private static JWins getHorarios() throws Exception {
  	if (horarios!=null) return horarios;
  	JMap<String, String> hs=JCollectionFactory.createOrderedMap();
  	for (int i=0;i<24;i++)
  		hs.addElement(""+i, JTools.LPad(""+i, 2, "0")+":00");
  	return horarios= JWins.createVirtualWinsFromMap(hs);
  }
  
  /**
   * Configuro las columnas que quiero mostrar en la grilla
   */
  public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
  //	zLista.AddIcono("");
  	zLista.AddColumnaLista("Hora");
  	if (GetVision().equals("SIMPLE"))
  	  	zLista.AddColumnaLista("Texto");
  	else
  		zLista.addColumnWinAction("Eventos", 1).setForceTitle(true);
  	
  }

  @Override
  public void readAll() throws Exception {
  	super.readAll();
  }
  
  
}
