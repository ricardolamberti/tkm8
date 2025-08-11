package  pss.common.event.action.history;

import java.util.Date;

import pss.common.event.action.BizSqlEventAction;
import pss.common.event.action.GuiSqlEventActions;
import pss.common.security.BizUsuario;
import pss.core.services.fields.JObject;
import pss.core.tools.JDateTools;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActWins;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.lists.JFormFiltro;
import pss.core.winUI.lists.JWinList;
import pss.core.winUI.responsiveControls.JFormIntervalCDatetimeResponsive;

public class GuiSqlEventHistories extends JWins {



  /**
   * Constructor de la Clase
   */
  public GuiSqlEventHistories() throws Exception {
  }


  public int     GetNroIcono() throws Exception  { return 749; } 
  public String  GetTitle()    throws Exception  { return "Historico avisos"; }
  public Class<? extends JWin>  GetClassWin()                   { return GuiSqlEventHistory.class; }
  /**
   * Mapeo las acciones con las operaciones
   */
  public void createActionMap() throws Exception {
//    addActionNew( 1, "Nuevo Registro" );
		this.addAction(20, "Programación", null, 10026, true, true, true, "Group" );
  }

	public JAct getSubmitFor(BizAction a) throws Exception {
		if (a.getId()==20) return new JActWins(getProgramacion());
		return super.getSubmitFor(a);
	}

	public JWins getProgramacion() throws Exception {
		GuiSqlEventActions wins = new GuiSqlEventActions();
		wins.getRecords().addFilter("company", BizUsuario.getUsr().getCompany());
		wins.getRecords().addFilter("tipo_periodicidad", BizSqlEventAction.SOLOUNAVEZ, "<>");
		wins.getRecords().addFilter("action", BizSqlEventAction.NOTIF, "<>");
		return wins;
	}
  /**
   * Configuro las columnas que quiero mostrar en la grilla
   */
  public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
    zLista.AddColumnaLista("fecha");
    zLista.AddColumnaLista("destinatario");
    zLista.AddColumnaLista("fundamento");
    zLista.AddColumnaLista("remitido");
    zLista.AddColumnaLista("fecha_envio");
  }

	@Override
	public void ConfigurarFiltros(JFormFiltro zFiltros) throws Exception {
		JFormControl oControl;
//		oControl=zFiltros.NuevoCombo("Usuario", "Vendedor", new JControlCombo() {
//			public JWins getRecords(Object zSource, boolean zOneRow) throws Exception {
//				return obtenerUsers(zSource);
//			}
//		}, true);
//		if (!GuiPOS.isUsingSharedPos()) oControl.SetValorDefault(BizUsuario.getUsr().GetUsuario());
		JFormIntervalCDatetimeResponsive d= zFiltros.addIntervalCDateResponsive("Fecha", "fecha");
		d.SetValorDefault(JDateTools.getFirstDayOfMonth(new Date()),JDateTools.getLastDayOfMonth(new Date()));

//		zFiltros.NuevoCombo("Tipo",  "id_action", JWins.createVirtualWinsFromMap(BizSqlEventAction.getActions()));
		zFiltros.addEditResponsive("Descripción", JObject.JSTRING, "fundamento").setOperator("ilike");
	}

}
