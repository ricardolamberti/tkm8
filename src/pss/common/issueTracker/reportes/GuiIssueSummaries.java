package pss.common.issueTracker.reportes;

import java.util.Date;

import pss.common.issueTracker.setting.BizIssueTrackerSetting;
import pss.common.regions.company.GuiCompanies;
import pss.common.security.BizUsuario;
import pss.core.services.fields.JDate;
import pss.core.services.records.JRecords;
import pss.core.tools.JDateTools;
import pss.core.win.JControlWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.lists.JFormFiltro;
import pss.core.winUI.lists.JWinList;

public class GuiIssueSummaries extends JWins {

	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public GuiIssueSummaries() throws Exception {
	}

	@Override
	public boolean canExpandTree() {
		return false;
	}

	@Override
	public int GetNroIcono() {
		return 720;
	}

	@Override
	public Class<? extends JWin> GetClassWin() {
		return GuiIssueSummary.class;
	}

	@Override
	public String GetTitle() {
		return "Ventas";
	}

	@Override
	public JRecords<BizIssueSummary> ObtenerDatos() {
		return new BizIssueSummaries();
	}
	
	public BizIssueSummaries getcRecords() throws Exception {
		return (BizIssueSummaries) getRecords();
	}
	

	@Override
	public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
		zLista.AddIcono("");
		zLista.AddColumnaLista("descr_fecha");
		if (this.hasChecked("agrupa_company"))
			zLista.AddColumnaLista("company");
		if (this.hasChecked("agrupa_usuario"))
			zLista.AddColumnaLista("descr_usuario");
		if (this.hasChecked("agrupa_status"))
			zLista.AddColumnaLista("descr_status");
		zLista.AddColumnaLista("#", "cantidad");
	}
	

	@Override
	public void ConfigurarFiltros(JFormFiltro zFiltros) throws Exception {
		JFormControl oControl;

		zFiltros.addWinLovResponsive("Empresa", "company", new JControlWin() {
		  public JWins getRecords(boolean one) throws Exception {
				return getCompanies(this, one);
			}
		});

		Date today = BizUsuario.getUsr().todayGMT(false);
		oControl=zFiltros.addCDateResponsive("Desde", "fechadesde");
		oControl.SetValorDefault(JDateTools.AddMonths(today, -6));
		zFiltros.addCDateResponsive("Hasta", "fechahasta").SetValorDefault(JDate.today());
		
		/*zFiltros.NuevoCombo("Usuario", "usuario", new JControlCombo() {
			public JWins getRecords(Object zSource, boolean zOneRow) throws Exception {
				return obtenerUsers(zSource);
			}
		}, true);*/
		
		oControl=zFiltros.addComboResponsive("Estado", "status", new JControlCombo() {
			public JWins getRecords(Object zSource, boolean zOneRow) throws Exception {
				return JWins.createVirtualWinsFromMap(BizIssueTrackerSetting.getAllStatus());
			}
		}, true);
		
	
		zFiltros.addComboResponsive("x Periodo", "periodo", new JControlCombo() { 
			@Override
			public JWins getRecords(boolean one) throws Exception {
				return JWins.createVirtualWinsFromMap(BizIssueSummary.getPeriodos());
			}
		}, true);
		zFiltros.addCheckResponsive("x Agencia", "agrupa_company").SetValorDefault(true);
		zFiltros.addCheckResponsive("x Usuario", "agrupa_usuario");
		zFiltros.addCheckResponsive("x Estado", "agrupa_status");

	}



	/*private JWins obtenerUsers(Object zSource) throws Exception {
		JFormControl oCombo = (JFormControl) zSource;
		JWins oWins = new GuiUsuarios();
		String sCompany = oCombo.getControls().findControl("company").getValue();
		if (sCompany.equals(""))
			sCompany = "DEFAULT";
		oWins.getRecords().addFilter("company", sCompany);
		oWins.getRecords().addFilter("activo", true);
		return oWins;
	}*/


	@Override
	public boolean canConvertToURL() throws Exception {
		return false;
	}
	
	private JWins getCompanies(JControlWin c, boolean one) throws Exception {
		GuiCompanies g = new GuiCompanies();
		//g.getRecords().addFilter("company", BizUsuario.getUsr().getCompany());
		return g;
	}
	

}
