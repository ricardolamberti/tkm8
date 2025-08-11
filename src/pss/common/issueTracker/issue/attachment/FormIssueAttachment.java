package pss.common.issueTracker.issue.attachment;

import pss.common.issueTracker.issueHandlerUsers.GuiIssueHandlerUsers;
import pss.common.security.BizUsuario;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.forms.JBaseForm;


public class FormIssueAttachment extends JBaseForm {

	public FormIssueAttachment() throws Exception {
	}

	public GuiIssueAttachment GetWin() {
		return (GuiIssueAttachment) this.getBaseWin();
	}

	@Override
	public void InicializarPanel(JWin zWin) throws Exception {
		AddItemEdit("company", CHAR, REQ, "company").hide();
		AddItemEdit("issueId", ULONG, REQ, "issue_id").SetReadOnly(true);
  	AddItemArea("description", CHAR, REQ, "description");
  	AddItemEdit("user", CHAR, OPT, "usuario").SetReadOnly(true);
    AddItemCombo("responsable", CHAR, OPT, "handler_user", new JControlCombo() { @Override
  		public JWins getRecords(boolean zOneRow) throws Exception {
				return getHandlerUsers(zOneRow);
			}
    });	  	
  	
	}
	
	protected JWins getHandlerUsers(boolean zOneRow) throws Exception {
		GuiIssueHandlerUsers wins=new GuiIssueHandlerUsers();
		wins.getRecords().addFilter("usuario_map_sistema", BizUsuario.getUsr().GetUsuario());		
		if (zOneRow) {
			wins.getRecords().addFilter("usuario", this.GetWin().GetcDato().getHandlerUser());
		}
		return wins;
	}
	
} 
