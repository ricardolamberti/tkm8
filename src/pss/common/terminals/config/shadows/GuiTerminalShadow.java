package pss.common.terminals.config.shadows;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiTerminalShadow extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiTerminalShadow() throws Exception {
  }


  @Override
	public JRecord ObtenerDato()   throws Exception { return new BizTerminalShadow(); }
  @Override
	public int GetNroIcono()   throws Exception { return 555; }
  @Override
	public String GetTitle()   throws Exception { return "Shadows"; }
  @Override
	public Class<? extends JBaseForm> getFormBase() throws Exception { return null; }

  public BizTerminalShadow GetcDato() throws Exception { return (BizTerminalShadow) this.getRecord(); }

 }
