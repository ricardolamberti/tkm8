package pss.common.terminals.config.auxPrinter;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiPrinter extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiPrinter() throws Exception {
  }


  @Override
	public JRecord ObtenerDato()   throws Exception { return new BizPrinter(); }
  @Override
	public int GetNroIcono()   throws Exception { return 555; }
  @Override
	public String GetTitle()   throws Exception { return "Impresora"; }
  @Override
	public Class<? extends JBaseForm> getFormBase() throws Exception { return FormPrinter.class; }
  @Override
	public String  getKeyField() throws Exception { return "terminal_id"; }
  @Override
	public String  getDescripField() { return "terminal_id"; }
  public BizPrinter GetcDato() throws Exception { return (BizPrinter) this.getRecord(); }

 }
