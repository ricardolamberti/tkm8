package pss.common.event.manager;

import java.util.Date;
import java.util.StringTokenizer;

import pss.common.regions.nodes.GuiNodos;
import pss.common.security.BizUsuario;
import pss.core.tools.JDateTools;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.lists.JFormFiltro;
import pss.core.winUI.lists.JWinList;
import pss.core.winUI.responsiveControls.JFormCDatetimeResponsive;

public class GuiEvents extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiEvents() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 5074; } 
  public String  GetTitle()    throws Exception  { return "Eventos"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiEvent.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  public void createActionMap() throws Exception {
   // addActionNew( 1, "Nuevo Registro" );
  }



  /**
   * Configuro las columnas que quiero mostrar en la grilla
   */
  public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
		zLista.AddIcono("");
	  zLista.AddColumnaLista("id_event");
	  
		BizUsuario usr = BizUsuario.getUsr();
  	if (  usr.getCompany().equalsIgnoreCase("DEFAULT") == true )
		  zLista.AddColumnaLista("descr_company");
	  zLista.AddColumnaLista("event_datetime");
	  zLista.AddColumnaLista("descr_node");
	  zLista.AddColumnaLista("module");
	  zLista.AddColumnaLista("event_code");
	  zLista.AddColumnaLista("descr_event_code");
	  zLista.AddColumnaLista("descr_action");
	  zLista.AddColumnaLista("info");
	  zLista.AddColumnaLista("processed");
  }
  
  
  public void ConfigurarFiltros(JFormFiltro zFiltros) throws Exception {

    zFiltros.addEditResponsive("Company", "company");
		zFiltros.addComboResponsive( "Sucursal" , "node", new JControlCombo() {
			public JWins getRecords(boolean one) throws Exception {
				return getNodes();	
			};
		}
		, true );
		

		JFormCDatetimeResponsive d1 = zFiltros.addCDateResponsive("F.Desde", "event_datetime");
    d1.SetValorDefault(JDateTools.DateTimeToDate(new Date(), "00:00:00"));
    d1.setOperator(">=");
    d1.setIdControl("fecha_desde");
    
    d1 = zFiltros.addCDateResponsive("F.Hasta", "event_datetime");
    d1.SetValorDefault(JDateTools.DateTimeToDate(new Date(), "23:59:59"));
    d1.setOperator("<=");
    d1.setIdControl("fecha_hasta");
    
		
//    zFiltros.NuevoCombo( "Tipo Evento" , "event_type", getEventTypes(), true );
    zFiltros.addEditResponsive( "Evento" , "event_code" );
    
  	
	}

//	protected JWins getModules() throws Exception {
//		return JWins.createVirtualWinsFromMap(BizEventCode.getInstalledModules());
//	}
  
//	private JWins getEventTypes() throws Exception {
//		return JWins.createVirtualWinsFromMap(BizEventCode.getModuleEvents());
//	}

	protected void asignFilterByControl(JFormControl control) throws Exception {
		if (control.getIdControl().equals("event_type")) {
			if (!control.hasValue()) {
				this.getRecords().clearFilter("module");
				this.getRecords().clearFilter("event_code");
			} else {
				StringTokenizer tok = new StringTokenizer(control.getValue(),"|");
				String module = tok.nextToken();
				String code   = tok.nextToken();
				this.getRecords().addFilter("module", module);
				this.getRecords().addFilter("event_code", code);
			}
			return;
		}

		super.asignFilterByControl(control);
	}

	public JWins getNodes() throws Exception {
		GuiNodos s = new GuiNodos();
	  s.getRecords().addFilter("company", this.getRecords().getFilterValue("company"));
		s.getRecords().addOrderBy("pais");
		s.getRecords().addOrderBy("company");
		s.getRecords().addOrderBy("codigo_ident");
		return s;
	}
	

}
