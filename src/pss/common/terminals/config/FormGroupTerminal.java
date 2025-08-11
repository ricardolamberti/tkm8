package pss.common.terminals.config;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;

public class FormGroupTerminal extends JBaseForm {

  public FormGroupTerminal() throws Exception {
  }

  public GuiGroupTerminal GetWin() { return (GuiGroupTerminal) getBaseWin(); }

   @Override
	public void InicializarPanel( JWin zWin ) throws Exception {
  	JFormPanelResponsive panel = this.AddItemColumn(6);
    panel.AddItemEdit( "company", CHAR, REQ, "company" ).hide();
    panel.AddItemEdit( "nodo", CHAR, REQ, "nodo" ).hide();
    panel.AddItemEdit( "terminal_group", UINT, REQ, "terminal_group" ).hide();
//    JFormControl control = AddItem( jTerminalPool, UINT, REQ, "terminal_pool", new JControlCombo() {public JWins GetCustomWins(boolean zOneRow) throws Exception { return getTerminalPools(zOneRow);}} );
//    control.addDependencies("terminal_id");
    panel.AddItemCombo( "Terminal", UINT, REQ, "terminal_id", new JControlCombo() {
    	@Override
    	public JWins getRecords(boolean zOneRow) throws Exception { 
    		return getTerminals(zOneRow);
    	}
    }, 6 );
  }

  private JWins getTerminals(boolean zOneRow) throws Exception {
  	GuiTerminals wins = new GuiTerminals();
  	if (zOneRow) {
  		wins.getRecords().addFilter("company", GetWin().GetcDato().getCompany());
  		wins.getRecords().addFilter("nodo", GetWin().GetcDato().getNodo());
//  		wins.getRecords().addFilter("terminal_pool", GetWin().GetcDato().getTerminalPool());
  		wins.getRecords().addFilter("terminal_id", GetWin().GetcDato().getTerminalId());
  	} else {
  		wins.getRecords().addFilter("company", this.findControl("company").getValue());
  		wins.getRecords().addFilter("nodo", this.findControl("nodo").getValue());
  		wins.getRecords().addOrderBy("terminal_pool");
  	}
  	return wins;
  }
  
}