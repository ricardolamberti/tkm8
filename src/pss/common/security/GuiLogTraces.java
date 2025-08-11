package  pss.common.security;

import pss.core.tools.JDateTools;
import pss.core.win.JControlWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.lists.JFormFiltro;
import pss.core.winUI.lists.JWinList;
import pss.core.winUI.responsiveControls.JFormIntervalCDatetimeResponsive;

public class GuiLogTraces extends JWins {

	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public GuiLogTraces() throws Exception {
	}

	@Override
	public Class<? extends JWin> GetClassWin() {
		return GuiLogTrace.class;
	}
	

	@Override
	public int GetNroIcono() throws Exception {
		return 10003;
	}

	@Override
	public String GetTitle() throws Exception {
		return "Log Trace";
	}

	@Override
	public void createActionMap() throws Exception {
	}

	public void ConfigurarFiltros(JFormFiltro zFiltros) throws Exception {
		zFiltros.addEditResponsive("Empresa", "company").hide();
		zFiltros.addWinLovResponsive("Usuario", "usuario", new JControlWin() {
		  public JWins getRecords( boolean one) throws Exception { 
				return getUsuarios( one);
			}
		});
		JFormIntervalCDatetimeResponsive d= zFiltros.addIntervalCDateTimeResponsive("Fecha", "fecha");
		d.SetValorDefault(JDateTools.getDateStartDay(BizUsuario.getUsr().todayGMT(false)),JDateTools.getDateEndDay(BizUsuario.getUsr().todayGMT(false)));
		d.setMinWidth(200);
	
	}
	
	private JWins getUsuarios( boolean one) throws Exception {
		//JFormControl c = (JFormControl)source;
		GuiUsuarios u = new GuiUsuarios();
		if (!BizUsuario.getUsr().isAdminUser()) 
			u.getRecords().addFilter("company", this.getFilterValue("company"));
		return u;
	}

	@Override
	public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
		zLista.AddIcono("");
		if ( !this.getRecords().filterHasValue("company"))
		  zLista.AddColumnaLista("company");
		zLista.AddColumnaLista("usuario");
		zLista.AddColumnaLista("nombre");
		zLista.AddColumnaLista("fecha");
		if ( !this.getRecords().filterHasValue("operacion"))
			zLista.AddColumnaLista("operacion");
		zLista.AddColumnaLista("origen_login");
		zLista.AddColumnaLista("out_access_detail");
		zLista.AddColumnaLista("datos");
		getRecords().clearOrderBy();
		getRecords().addOrderBy("nrotrace", "DESC");
		
	}


}
