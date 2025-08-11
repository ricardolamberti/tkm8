package pss.common.terminals.config;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiGroupTerminal extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiGroupTerminal() throws Exception {
  }


  @Override
	public JRecord ObtenerDato()   throws Exception { return new BizGroupTerminal(); }
  @Override
	public int GetNroIcono()   throws Exception { return 994; }
  @Override
	public String GetTitle()   throws Exception { return "Grupo de Terminales"; }
  @Override
	public Class<? extends JBaseForm> getFormBase() throws Exception { return FormGroupTerminal.class; }
  @Override
	public String  getKeyField() throws Exception { return "terminal_id"; }
  @Override
	public String  getDescripField() { return "terminal_id"; }
  public BizGroupTerminal GetcDato() throws Exception { return (BizGroupTerminal) this.getRecord(); }

  @Override
	public void createActionMap() throws Exception {
    this.addActionDelete ( 3, "Eliminar"  );
  }
  
}
