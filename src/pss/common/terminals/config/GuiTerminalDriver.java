package pss.common.terminals.config;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiTerminalDriver extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiTerminalDriver() throws Exception {
  }


  @Override
	public JRecord ObtenerDato()   throws Exception { return new BizTerminalDriver(); }
  @Override
	public int GetNroIcono()   throws Exception { return 58; }
  @Override
	public String GetTitle()   throws Exception { return "Driver"; }
  @Override
	public Class<? extends JBaseForm> getFormBase() throws Exception { return FormTerminalDriver.class; }
  @Override
	public String  getKeyField() throws Exception { return "terminal_id"; }
  @Override
	public String  getDescripField() { return "terminal_id"; }
  public BizTerminalDriver GetcDato() throws Exception { return (BizTerminalDriver) this.getRecord(); }

  @Override
	public void createActionMap() throws Exception {
//    this.addActionQuery( 1, "Consultar" );
//    this.addActionUpdate( 2, "Modificar" );
    this.addActionDelete ( 3, "Eliminar"  );
  }
  
}
