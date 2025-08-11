package  pss.common.pki.users;

import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.winUI.forms.JBaseForm;

public class GuiUsuarioFirma extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiUsuarioFirma() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizUsuarioFirma(); }
  public int GetNroIcono()   throws Exception { return 10040; }
  public String GetTitle()   throws Exception { return "Usuario Firma"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormUsuarioFirma.class; }
  public String  getKeyField() throws Exception { return "id"; }
  public String  getDescripField() { return "firma"; }
  public BizUsuarioFirma GetcDato() throws Exception { return (BizUsuarioFirma) this.getRecord(); }

  @Override
  public boolean OkAction(BizAction a) throws Exception {
  	if (a.getId()==BizAction.UPDATE) return false;
   	if (a.getId()==BizAction.QUERY) return false;
    if (a.getId()==BizAction.REMOVE) GetcDato().isVigente();
  	return super.OkAction(a);
  }
 }
