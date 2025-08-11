package  pss.common.regions.company;

import pss.common.regions.currency.GuiMonedaPaises;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;


public class FormCompanyCountry extends JBaseForm {
  
  public FormCompanyCountry() throws Exception {
  }

  private GuiCompanyCountry GetWin() {
    return (GuiCompanyCountry) getBaseWin();
  }

 @Override
	public void InicializarPanel(JWin zWin) throws Exception {
	  JFormPanelResponsive col= this.AddItemColumn(2);
    col.AddItemEdit("Company", CHAR, REQ, "company").hide();
    col.AddItemEdit("Pais", CHAR, REQ, "pais").setReadOnly(true);
    col.AddItemCombo("Moneda Local", CHAR, OPT, "local_currency", new JControlCombo() {
    	public JWins getRecords(boolean one) throws Exception {
    		return getMonedas(one);
    	}
    });
  }

  public JWins getMonedas(boolean one) throws Exception {
    GuiMonedaPaises wins = new GuiMonedaPaises();
    if (one) {
    	wins.getRecords().addFilter("company", GetWin().GetcDato().getCompany());
    	wins.getRecords().addFilter("pais", GetWin().GetcDato().getCountry());
    	wins.getRecords().addFilter("moneda", GetWin().GetcDato().getLocalCurrency());
    } else {
    	wins.getRecords().addFilter("company", this.valueControl("company"));
    	wins.getRecords().addFilter("pais", this.valueControl("pais"));
    }
    return wins;
  }
	


}