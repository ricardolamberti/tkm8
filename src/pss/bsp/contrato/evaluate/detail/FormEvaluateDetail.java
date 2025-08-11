package pss.bsp.contrato.evaluate.detail;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;

public class FormEvaluateDetail extends JBaseForm {

  private static final long serialVersionUID = 1477827540739L;

  public FormEvaluateDetail() throws Exception {
  }

  public GuiEvaluateDetail getWin() { return (GuiEvaluateDetail) getBaseWin(); }

  @Override
  public void InicializarPanel(JWin zWin) throws Exception {
    AddItemEdit(null, CHAR, OPT, "id").setHide(true);
    JFormPanelResponsive row = AddItemRow();
    row.AddItemCheck("Ok", OPT, "ok").setSizeColumns(2);
    row.AddItemEdit("Number", UINT, OPT, "number").setSizeColumns(2);
    row.AddItemEdit("Result", CHAR, OPT, "result").setSizeColumns(8);
    AddItemRow().AddItemArea("Detail", CHAR, OPT, "detail").setHeight(80);
  }
}
