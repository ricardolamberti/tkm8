package  pss.common.publicity;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiSpot extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiSpot() throws Exception {
  }


  @Override
	public JRecord ObtenerDato()   throws Exception { return new BizSpot(); }
  @Override
	public int GetNroIcono()   throws Exception { return 754; }
  @Override
	public String GetTitle()   throws Exception { return "Spot publicitario"; }
  @Override
	public Class<? extends JBaseForm> getFormBase() throws Exception { return FormSpot.class; }
  @Override
	public String  getKeyField() throws Exception { return "id"; }
  @Override
	public String  getDescripField() { return "link"; }
  public BizSpot GetcDato() throws Exception { return (BizSpot) this.getRecord(); }

 }
