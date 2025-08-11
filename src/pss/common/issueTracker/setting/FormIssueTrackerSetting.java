package pss.common.issueTracker.setting;

import pss.common.event.mail.GuiMailCasillas;
import pss.common.issueTracker.issueHandlerUsers.GuiIssueHandlerUsers;
import pss.common.regions.company.GuiCompanies;
import pss.common.security.BizUsuario;
import pss.core.win.JControlWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.forms.JBaseForm;


public class FormIssueTrackerSetting extends JBaseForm {

	public FormIssueTrackerSetting() throws Exception {
	}

	public GuiIssueTrackerSetting GetWin() {
		return (GuiIssueTrackerSetting) this.getBaseWin();
	}

	@Override
	public void InicializarPanel(JWin zWin) throws Exception {
		AddItemCombo("company", CHAR, REQ, "company", new JControlWin() {
			@Override
			public JWins getRecords(boolean zOneRow) throws Exception {
				return getCompanies(zOneRow, GetWin().GetcDato().getCompany());
			}
		});	
    AddItemCombo("handler_user", CHAR, REQ, "handler_user", new JControlCombo() { @Override
  		public JWins getRecords(boolean zOneRow) throws Exception {
				return getHandlerUsers(zOneRow);
			}
    });	
    AddItemCombo("casilla", CHAR, OPT, "id_casilla", new JControlCombo() { 
    	@Override
  		public JWins getRecords(boolean zOneRow) throws Exception {
				return getCasillas(zOneRow);
			}
    });
    AddItemCheck("default_setting", OPT, "default_setting");
	}
	
	protected JWins getCompanies(boolean zOneRow, String zCompany) throws Exception {
		GuiCompanies wins=new GuiCompanies();
		if (zOneRow) {
			wins.getRecords().addFilter("company", zCompany);
		}
		return wins;
	}

	protected JWins getHandlerUsers(boolean zOneRow) throws Exception {
		GuiIssueHandlerUsers wins=new GuiIssueHandlerUsers();
		if (zOneRow) {
			wins.getRecords().addFilter("usuario", this.GetWin().GetcDato().getHandlerUser());
		}
		return wins;
	}
	
  protected JWins getCasillas(boolean oneRow) throws Exception {
  	GuiMailCasillas wins = new GuiMailCasillas();
  	wins.getRecords().addFilter("company", BizUsuario.getUsr().getCompany());
  	if (oneRow) {
  		wins.getRecords().addFilter("id", this.GetWin().GetcDato().getIdCasilla());
  	}
  	return wins;
  }


} // @jve:decl-index=0:visual-constraint="10,10"
