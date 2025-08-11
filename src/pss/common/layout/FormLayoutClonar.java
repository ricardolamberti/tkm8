package pss.common.layout;

import pss.common.regions.company.GuiCompanies;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.forms.JBaseForm;

public class FormLayoutClonar extends JBaseForm {
	
	public FormLayoutClonar(){		
	}

  public GuiLayoutClonar GetWin() {
    return (GuiLayoutClonar) getBaseWin();
  }
  
  @Override
	public void InicializarPanel( JWin zWin ) throws Exception {
  	AddItemEdit( "company", CHAR, REQ, "company");
  	AddItemEdit( "pais", CHAR, REQ, "pais");
  	AddItemEdit( "layout", CHAR, OPT, "layout");
  	AddItemCombo( "Companyclon", CHAR, REQ, "company_clon", new JControlCombo() {
  		public JWins getRecords(boolean one) throws Exception {
  			return new GuiCompanies();
  		}
  	}).SetValorDefault(this.GetWin().GetcDato().getCompany());
  	AddItemEdit( "layoutClon", CHAR, REQ, "layout_clon");
  }

} 
