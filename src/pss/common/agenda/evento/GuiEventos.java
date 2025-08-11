package  pss.common.agenda.evento;

import java.util.Date;

import pss.common.agenda.evento.tipo.GuiTipoEventos;
import pss.common.agenda.turno.GuiTurnos;
import pss.common.personalData.GuiPersonas;
import pss.common.regions.company.GuiCompanies;
import pss.common.security.BizUsuario;
import pss.core.tools.JDateTools;
import pss.core.win.JControlWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.lists.JFormFiltro;
import pss.core.winUI.lists.JWinList;
import pss.core.winUI.responsiveControls.JFormIntervalCDatetimeResponsive;

public class GuiEventos extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiEventos() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 10001; } 
  public String  GetTitle()    throws Exception  { return "Eventos"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiEvento.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  public void createActionMap() throws Exception {
    addActionNew( 1, "Nuevo Evento" );
  }

  @Override
  public void ConfigurarFiltros(JFormFiltro zFiltros) throws Exception {
  	JFormControl f;
  	if (GetVision().equals(BizEvento.VISION_LEGAJO)) return;
  	if (GetVision().equals(BizEvento.VISION_ALARMA)) {
    	JFormIntervalCDatetimeResponsive fi = zFiltros.addIntervalCDateResponsive("Alarma", "fecha_alarma");
  		fi.SetValorDefault(JDateTools.getFirstDayOfMonth(new Date()),JDateTools.getLastDayOfMonth(new Date()));
  	}
  	else {
     	if (getFilterValue("id_agenda")==null)
     		f = zFiltros.addComboResponsive("Agenda", "id_agenda", getAgendas());
     	if (BizUsuario.getUsr().IsAdminUser() && getFilterValue("company")==null)
     		f = zFiltros.addComboResponsive("Company", "company", new GuiCompanies());
     	
     	f = zFiltros.addCDateTimeResponsive("Fecha Desde", "fecha_fin").setWithTime(true);
	  	if (this.getRecords().getFilterValue("fecha_i")==null)
	   		f.SetValorDefault(JDateTools.getFirstDayOfMonth(new Date()));
	   	else {
	  		f.SetValorDefault(this.getRecords().getFilterValue("fecha_i"));
	  		f.setVisible(false);
	   	}		
	  	f.setOperator(">=");
	  	f.setIdControl("fecha_desde");
	  	
	  	f = zFiltros.addCDateTimeResponsive("Fecha Hasta", "fecha_inicio").setWithTime(true);
	   	if (this.getRecords().getFilterValue("fecha_f")==null)
	   	 	f.SetValorDefault(JDateTools.getLastDayOfMonth(new Date()));
	  	else {
	  		f.SetValorDefault(this.getRecords().getFilterValue("fecha_f"));
	  		f.setVisible(false);
	  	}
	  	f.setOperator("<=");
	  	f.setIdControl("fecha_hasta");
  	}
  	f = zFiltros.addComboResponsive("Estado", "estado", JWins.createVirtualWinsFromMap(BizEvento.getEstados())).setRefreshForm(true);;
  	f.SetValorDefault(BizEvento.ACTIVA);
   	f = zFiltros.addComboResponsive("Tipo Evento", "id_tipo_evento", getTipoEventos()).setRefreshForm(true);
    
		f = zFiltros.addWinLovResponsive("Participante", "id_evento", new JControlWin() {
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
@Override
protected void asignFilterByControl(JFormControl control) throws Exception {
	if (control.getIdControl().equals("id_tipo_evento") && !control.hasValue()) {
		return;
	}
	if (control.getIdControl().equals("id_evento") && control.hasValue()) {
		this.getRecords().addJoin("agd_evento_participantes").setDynamic(true);
		this.getRecords().addFixedFilter("where agd_evento_participantes.id_evento=agd_evento.id_evento and agd_evento_participantes.id_persona="+control.getValue()).setDynamic(true);
		return;
	}
	super.asignFilterByControl(control);
}
  /**
   * Configuro las columnas que quiero mostrar en la grilla
   */
  public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
  	zLista.AddIcono("");
  	if (GetVision().equals(BizEvento.VISION_ALARMA)) {
	  	zLista.AddColumnaLista("hito");
	  	zLista.AddColumnaLista("fecha_alarma");
	  	zLista.AddColumnaLista("fecha_inicio_calc");
	  	zLista.AddColumnaLista("fecha_fin");
	  	zLista.AddColumnaLista("titulo");
	  	zLista.AddColumnaLista("estado");
  	}
  	else {
	  	zLista.AddColumnaLista("hito");
	  	zLista.AddColumnaLista("fecha_inicio_calc");
	  	zLista.AddColumnaLista("fecha_fin");
	  	zLista.AddColumnaLista("titulo");
	  	zLista.AddColumnaLista("estado");
	  	zLista.AddColumnaLista("has_alarma");
  	}
  }
  
  

}
