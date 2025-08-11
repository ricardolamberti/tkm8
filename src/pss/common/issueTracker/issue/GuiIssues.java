package pss.common.issueTracker.issue;

import java.util.Date;

import pss.common.issueTracker.issueHandlerUsers.BizIssueHandlerUser;
import pss.common.issueTracker.issueHandlerUsers.GuiIssueHandlerUsers;
import pss.common.issueTracker.setting.BizIssueTrackerSetting;
import pss.common.regions.company.GuiCompanies;
import pss.common.security.BizUsuario;
import pss.core.win.JControlWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.lists.JFormFiltro;
import pss.core.winUI.lists.JWinList;

public class GuiIssues extends JWins {

	public static String VISION_ADMIN = "VISION_ADMIN";
	
	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public GuiIssues() throws Exception {
	}

	@Override
	public Class<? extends JWin> GetClassWin() {
		return GuiIssue.class;
	}

	@Override
	public int GetNroIcono() throws Exception {
		return 89;
	}

	@Override
	public String GetTitle() throws Exception {
		return "Incidencias";
	}
	

	// -------------------------------------------------------------------------//
	// Mapeo las acciones con las operaciones
	// -------------------------------------------------------------------------//
	@Override
	public void createActionMap() throws Exception {
    addActionNew     ( 1, "Nueva Incidencia" );
		//this.addAction(10, "Nueva Incidencia", null, GuiIcon.MAS_ICON, true, true);
	}

	public JAct getSubmitFor(BizAction a) throws Exception {

		return super.getSubmitFor(a);
	}
	
	// -------------------------------------------------------------------------//
	// Configuro las columnas que quiero mostrar en la grilla
	// -------------------------------------------------------------------------//
	@Override
	public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
		zLista.AddIcono("");
		zLista.AddColumnaLista("issue_id");
		zLista.AddColumnaLista("summary").setActionOnClick(1);
		zLista.AddColumnaLista("descr_status");
		zLista.AddColumnaLista("date_submitted");
		zLista.AddColumnaLista("usuario");
		zLista.AddColumnaLista("assigned_to");
		zLista.AddColumnaLista("last_update");
		zLista.AddColumnaLista("has_note");
		zLista.AddColumnaLista("has_attachment");
		if (this.GetVision().equals(VISION_ADMIN)) {
			zLista.AddColumnaLista("total_time_for_resolution");
			zLista.AddColumnaLista("real_time_for_resolution");
			zLista.AddColumnaLista("reopens_qty");
		}
		this.getRecords().addOrderBy("issue_id","desc");
	}

	@Override
	public boolean canConvertToURL() throws Exception {
		return false;
	}
	
	
	 @Override
		public void ConfigurarFiltros(JFormFiltro filters) throws Exception { 
		 JFormControl oControl;
		 filters.addEditResponsive("Empresa", "company");
			oControl=filters.addComboResponsive("Responsable", "assigned_to", new JControlCombo() {
				public JWins getRecords(boolean zOneRow) throws Exception {
					return getHandlerUsers(zOneRow);
				}
			}, true);
			if (!BizIssueHandlerUser.isLoginedUserAHandlerUser()) {
				oControl.setVisible(false);
			}
			filters.addComboResponsive("Estado", "status", new JControlCombo() {
					public JWins getRecords(Object zSource, boolean zOneRow) throws Exception {
						return JWins.createVirtualWinsFromMap(BizIssueTrackerSetting.getAllStatus());
					}
				}, true).setFilterNeverHide(true);
			  
			 Date today = BizUsuario.getUsr().todayGMT(false);
			 
				oControl = filters.addCDateResponsive("Desde", "date_submitted_plain");
//				oControl.SetValorDefault(JDateTools.AddMonths(today, -6));
				oControl.setOperator(">=");
				oControl.setIdControl("fecha_desde");

				oControl = filters.addCDateResponsive("Hasta", "date_submitted_plain");
//				oControl.SetValorDefault(today);
				oControl.setOperator("<=");
				oControl.setIdControl("fecha_hasta");

				filters.addCheckResponsive("Sólo Pendientes", "solo_pendientes").setFilterNeverHide(true);
				filters.addCheckResponsive("Ver Cerrados", "ver_cerrados").SetValorDefault(false);
	  }
	 
	 
	 @Override
		protected void asignFilterByControl(JFormControl control) throws Exception {
			if (control.getIdControl().equals("solo_pendientes")) {
				if (control.getValue().equals("S"))
					this.getRecords().addFixedFilter(" status not in ('"+ BizIssueTrackerSetting.STATUS_RESOLVED + "','" + BizIssueTrackerSetting.STATUS_CLOSED + "')" );
			}
			if (control.getIdControl().equals("ver_cerrados")) {
				if (control.getValue().equals("S"))
					this.getRecords().addFixedFilter(" status in ('"+ BizIssueTrackerSetting.STATUS_CLOSED + "')" );
				else
					this.getRecords().addFixedFilter(" status not in ('"+ BizIssueTrackerSetting.STATUS_CLOSED + "')" );
			}
			super.asignFilterByControl(control);
		}

		private JWins getCompanies(JControlWin c, boolean one) throws Exception {
			GuiCompanies g = new GuiCompanies();
			//g.getRecords().addFilter("company", BizUsuario.getUsr().getCompany());
			return g;
		}
		

		protected JWins getHandlerUsers(boolean zOneRow) throws Exception {
			GuiIssueHandlerUsers wins=new GuiIssueHandlerUsers();
			return wins;
		}


}
