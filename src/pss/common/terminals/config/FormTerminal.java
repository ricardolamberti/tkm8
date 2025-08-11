package pss.common.terminals.config;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;

public class FormTerminal extends JBaseForm {

	public FormTerminal() throws Exception {
  }

  public GuiTerminal GetWin() { return (GuiTerminal) getBaseWin(); }

  @Override
	public void InicializarPanel( JWin zWin ) throws Exception {
    JFormPanelResponsive panel = this.AddItemColumn(6);
  	panel.AddItemEdit( "company", CHAR, REQ, "company" ).hide();
  	panel.AddItemEdit( "terminal_id", UINT, OPT, "terminal_id" ).hide();
  	panel.AddItemEdit( "nodo", CHAR, REQ, "nodo" ).hide();
  	panel.AddItemEdit( "temrinal_pool", UINT, REQ, "terminal_pool" ).hide();
    panel.AddItemCombo( "Tipo Terminal", CHAR, REQ, "terminal_type", new JControlCombo() {
    	@Override
    	public JWins getRecords(boolean zOneRow) throws Exception { 
    		return getTerminalTypes(zOneRow);
    	}
    },4 );
    panel.AddItemEdit( "Connection String", CHAR, REQ, "connection_string",4 );
    panel.AddItemEdit( "Nro Serie", CHAR, OPT, "nro_serie", 4 );
    panel.AddItemTabPanel().AddItemTab(10);
  }
  
  private JWins getTerminalTypes(boolean zOneRow) throws Exception {
  	return JWins.createVirtualWinsFromMap(BizType.getTypes());
  }

}