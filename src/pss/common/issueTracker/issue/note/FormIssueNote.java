package pss.common.issueTracker.issue.note;

import pss.common.issueTracker.issueHandlerUsers.BizIssueHandlerUser;
import pss.common.issueTracker.issueHandlerUsers.GuiIssueHandlerUsers;
import pss.common.security.BizUsuario;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormControlResponsive;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;


public class FormIssueNote extends JBaseForm {

	private static final long serialVersionUID=1L;

	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public FormIssueNote() throws Exception {

	}

	public GuiIssueNote GetWin() {
		return (GuiIssueNote) this.getBaseWin();
	}

	
	// -------------------------------------------------------------------------//
	// Linkeo los campos con la variables del form
	// -------------------------------------------------------------------------//
	@Override
	public void InicializarPanel(JWin zWin) throws Exception {
		SetExitOnOk(true);
		
		JFormControlResponsive oCtrl;
		JFormControlResponsive oCtrl_user;
		
		JFormPanelResponsive panel = this.AddItemColumn(6);
		panel.setFormatFields(JFormPanelResponsive.FIELD_LABEL_HORIZONTAL_LEFT);
		panel.setSizeLabels(3);
		panel.AddItemEdit("company", CHAR, REQ, "company").hide();
		panel.AddItemEdit("Nro. de Issue", ULONG, OPT, "issue_id").SetReadOnly(true);
		
		oCtrl =panel.AddItemArea("Descripción", CHAR, REQ, "description");
		oCtrl.setHeight(200);
		


		oCtrl_user=panel.AddItemEdit("Usuario", CHAR, OPT, "usuario");
		oCtrl_user.SetReadOnly(true);
		oCtrl_user.setVisible(false);
		String req = BizIssueHandlerUser.isLoginedUserAHandlerUser()?REQ:OPT;
		oCtrl= panel.AddItemCombo("Responsable", CHAR, req, "handler_user", new JControlCombo() { @Override
  		public JWins getRecords(boolean zOneRow) throws Exception {
				return getHandlerUsers(zOneRow);
			}
		});
		oCtrl.setVisible(false);
		if (BizIssueHandlerUser.isLoginedUserAHandlerUser()) {
			oCtrl.SetValorDefault(this.getFirsHandleUser());
			oCtrl.setVisible(true);
			oCtrl_user.setVisible(true);
		}
	}
	
	private String getFirsHandleUser() throws Exception {
		GuiIssueHandlerUsers wins = (GuiIssueHandlerUsers)this.getHandlerUsers(false);
		wins.getRecords().toStatic();
		wins.getRecords().firstRecord();
		if (wins.getRecords().nextRecord())
			return  ((BizIssueHandlerUser)wins.getRecords().getRecord()).getUsuario();
		return "";
	}
	
	protected JWins getHandlerUsers(boolean zOneRow) throws Exception {
		GuiIssueHandlerUsers wins=new GuiIssueHandlerUsers();
		wins.getRecords().addFilter("usuario_map_sistema", BizUsuario.getUsr().GetUsuario());		
		if (zOneRow) {
			wins.getRecords().addFilter("usuario", this.GetWin().GetcDato().getHandlerUser());
		}
		return wins;
	}
	
} // @jve:decl-index=0:visual-constraint="10,10"
