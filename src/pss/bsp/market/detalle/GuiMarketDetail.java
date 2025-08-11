package  pss.bsp.market.detalle;

import pss.common.security.BizUsuario;
import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.winUI.forms.JBaseForm;

public class GuiMarketDetail extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiMarketDetail() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizMarketDetail(); }
  public int GetNroIcono()   throws Exception { return 10032; }
  public String GetTitle()   throws Exception { return "Mercado detalle"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormMarketDetail.class; }
  public String  getKeyField() throws Exception { return "id"; }
  public String  getDescripField() { return "descripcion"; }
  public BizMarketDetail GetcDato() throws Exception { return (BizMarketDetail) this.getRecord(); }

  @Override
  public boolean OkAction(BizAction a) throws Exception {
  	if (a.getId()==BizAction.REMOVE) return BizUsuario.IsAdminCompanyUser();
  	if (a.getId()==BizAction.UPDATE) return BizUsuario.IsAdminCompanyUser();
  	return super.OkAction(a);
  }

 }
