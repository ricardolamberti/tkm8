package pss.bsp.contrato.detalleCopaPorRutas.objetivos;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;

public class FormObjetivosCopaPorRutas extends JBaseForm {

  private static final long serialVersionUID = 1446860154249L;

  /**
   * Constructor de la Clase
   */
  public FormObjetivosCopaPorRutas() throws Exception { }

  public GuiObjetivosCopaPorRutas getWin() { return (GuiObjetivosCopaPorRutas) getBaseWin(); }

  /**
   * Linkeo los campos con la variables del form
   */
  @Override
  public void InicializarPanel(JWin zWin) throws Exception {
    AddItemEdit(null, UINT, OPT, "linea").setHide(true);
    AddItemEdit(null, UINT, OPT, "id").setHide(true);
    AddItemEdit(null, UINT, OPT, "id_contrato").setHide(true);
    AddItemEdit(null, UINT, OPT, "linea_contrato").setHide(true);
    AddItemEdit(null, UINT, OPT, "company").setHide(true);

    JFormPanelResponsive row = AddItemRow();
    row.AddItemEdit("Descripcion", CHAR, REQ, "descripcion").setSizeColumns(6);
    row.AddItemEdit("Variacion", FLOAT, REQ, "variacion").setSizeColumns(3);

    row = AddItemRow();
    row.AddItemDateTime("Fecha hasta", DATE, REQ, "fecha_hasta").setSizeColumns(6);
    row.AddItemDateTime("Fecha desde", DATE, REQ, "fecha_desde").setSizeColumns(6);
  }
}

