package pss.common.issueTracker.issueHandlerUsers;

import pss.common.regions.company.GuiCompanies;
import pss.common.security.GuiUsuarios;
import pss.core.win.JControlWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.forms.JBaseForm;


public class FormIssueHandlerUser extends JBaseForm {

	
	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public FormIssueHandlerUser() throws Exception {
	}

	public GuiIssueHandlerUser GetWin() {
		return (GuiIssueHandlerUser) this.getBaseWin();
	}

	// -------------------------------------------------------------------------//
	// Linkeo los campos con la variables del form
	// -------------------------------------------------------------------------//
	@Override
	public void InicializarPanel(JWin zWin) throws Exception {
		JFormControl oControl;
		AddItemEdit("user", CHAR, REQ, "usuario");
		AddItemCombo("companyMapUser", CHAR, REQ, "usuario_map_company", new JControlWin() {
			@Override
			public JWins getRecords(boolean zOneRow) throws Exception {
				return getCompanies(zOneRow, GetWin().GetcDato().getUsuarioMapCompany());
			}
		});			
		oControl = AddItemCombo("usersystem", CHAR, REQ, "usuario_map_sistema", new JControlWin() { 
    	@Override
  		public JWins getRecords(boolean zOneRow) throws Exception {
				return getCompanyUsers(zOneRow);
			}
    });			
		oControl.setRefreshForm(true);
   /* AddItem(casilla, CHAR, REQ, "id_casilla", new JControlCombo() { 
    	@Override
  		public JWins getRecords(boolean zOneRow) throws Exception {
				return getCasillas(zOneRow);
			}
    });	*/
		AddItemEdit("email", CHAR, REQ, "email");
  	AddItemCheck("Activo", OPT, "active");
	}
	
	
	protected JWins getCompanies(boolean zOneRow, String zCompany) throws Exception {
		GuiCompanies wins=new GuiCompanies();
		if (zOneRow) {
			wins.getRecords().addFilter("company", zCompany);
		}
		return wins;
	}
	
  protected JWins getCompanyUsers(boolean oneRow) throws Exception {
  	GuiUsuarios wins = new GuiUsuarios();
  	String company = this.findControl("usuario_map_company").getValue();
  	wins.getRecords().addFilter("company", company);
  	
  	if (oneRow) {
  		wins.getRecords().addFilter("usuario", this.GetWin().GetcDato().getUsuarioMapSistema());
  	}
  	return wins;
  }
  
  /*protected JWins getCasillas(boolean oneRow) throws Exception {
  	GuiMailCasillas wins = new GuiMailCasillas();
  	String company = this.GetControles().findControl("usuario_map_company").getValue();
  	wins.getRecords().addFilter("company", company);
  	
  	if (oneRow) {
  		wins.getRecords().addFilter("id", this.GetWin().GetcDato().getIdCasilla());
  	}
  	return wins;
  }*/
  

	
} 
