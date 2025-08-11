package pss.common.terminals.config;

import pss.core.services.records.JRecords;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;

public class FormTerminalDriver extends JBaseForm {

  public FormTerminalDriver() throws Exception {
  }

  public GuiTerminalDriver GetWin() { return (GuiTerminalDriver) getBaseWin(); }

  @Override
	public void InicializarPanel( JWin zWin ) throws Exception {
  	JFormPanelResponsive panel = this.AddItemColumn(6);
    panel.AddItemEdit( "company", CHAR, REQ, "company" ).hide();
    panel.AddItemEdit( "nodo", CHAR, REQ, "nodo" ).hide();
    panel.AddItemEdit( "terminal_id", UINT, REQ, "terminal_id" ).hide();
    panel.AddItemCombo( "Driver", CHAR, REQ, "driver_type", new JControlCombo() {
    	@Override
    	public JWins getRecords(boolean zOneRow) throws Exception { 
    		return getTerminalDrivers(zOneRow);
    	}
    });
  }
  
  private JWins getTerminalDrivers(boolean zOneRow) throws Exception {
  	return JWins.CreateVirtualWins(JRecords.createVirtualFormMap(GetWin().GetcDato().getObjTerminal().getTerminal().getAllDrivers()));
  }
  
} 