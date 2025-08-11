package pss.common.terminals.config;

import pss.common.regions.nodes.GuiNodos;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;

public class FormTerminalPool extends JBaseForm {

  public FormTerminalPool() throws Exception {
  }

  public GuiTerminalPool GetWin() { return (GuiTerminalPool) getBaseWin(); }

  @Override
	public void InicializarPanel( JWin zWin ) throws Exception {
  	JFormPanelResponsive panel = this.AddItemColumn(6);
    panel.AddItemEdit( "company", CHAR, REQ, "company" ).hide();
    panel.AddItemEdit( "terminal_pool", UINT, OPT, "terminal_pool" ).hide();
    panel.AddItemCombo( "Sucursal", CHAR, REQ, "nodo", new JControlCombo() {
    	public JWins getRecords(boolean one) throws Exception {
    		return getNodos(one);
    	}
    }, 4);
    panel.AddItemEdit( "Descripción", CHAR, REQ, "description", 4);
    panel.AddItemEdit( "MAC Address", CHAR, REQ, "mac_address", 4);
    panel.AddItemTabPanel().AddItemTab(10);
  }
  
  private JWins getNodos(boolean one) throws Exception {
  	GuiNodos g = new GuiNodos();
  	if (one) {
  		g.getRecords().addFilter("company", this.GetWin().GetcDato().getCompany());
  		g.getRecords().addFilter("nodo", this.GetWin().GetcDato().getNodo());
  	} else {
  		g.getRecords().addFilter("company", this.valueControl("company"));
  	}
  	return g;
  }
  
} 