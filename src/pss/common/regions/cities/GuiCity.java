package  pss.common.regions.cities;

import pss.common.security.BizUsuario;
import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class GuiCity extends JWin {


  /**
   * Constructor de la Clase
   */
  public GuiCity() throws Exception {
  }


  @Override
	public JRecord ObtenerDato()   throws Exception { return new BizCity(); }
  @Override
	public int GetNroIcono()   throws Exception { return 5107; }
  @Override
	public String GetTitle()   throws Exception { return BizUsuario.getUsr().getObjBusiness().getLabelRegionOrigen(); }
  @Override
	public Class<? extends JBaseForm> getFormBase() throws Exception { return FormCity.class; }
  @Override
	public String  getKeyField() throws Exception { return "city"; }
  @Override
	public String  getDescripField() { return "descripcion"; }
  public BizCity GetcDato() throws Exception { return (BizCity) this.getRecord(); }

 }
