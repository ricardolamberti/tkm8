package pss.bsp.contrato.evaluate.detail;

import pss.bsp.contrato.detalle.BizDetalle;
import pss.bsp.contrato.detalle.GuiDetalle;
import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActUpdate;
import pss.core.winUI.forms.JBaseForm;

public class GuiEvaluateDetail extends JWin {



  /**
   * Constructor de la Clase
   */
  public GuiEvaluateDetail() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizEvaluateDetail(); }
  public int GetNroIcono()   throws Exception { return 10003; }
  public String GetTitle()   throws Exception { return "Checkeo"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception { return FormEvaluateDetail.class; }
  public String  getKeyField() throws Exception { return "id"; }
  public String  getDescripField() { return "descripcion"; }
  public BizEvaluateDetail GetcDato() throws Exception { return (BizEvaluateDetail) this.getRecord(); }

  public void createActionMap() throws Exception {

  }
  @Override
  public String getFieldForeground(String zColName) throws Exception {
		if (GetcDato().getOk().equals("OK")) return "146f14";
		if (GetcDato().getOk().equals("NO")) return "8e051e";
		if (GetcDato().getOk().equals("NO APLICA")) return "7e7b7c";
		return super.getFieldBackground(zColName);
  }
  
  
 }
