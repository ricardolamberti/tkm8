package  pss.bsp.pdf;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;
import pss.core.winUI.responsiveControls.JFormTabPanelResponsive;

public class FormPDF extends JBaseForm {


private static final long serialVersionUID = 1245253394589L;

  /**
   * Constructor de la Clase
   */
  public FormPDF() throws Exception {
  }

  public GuiPDF getWin() { return (GuiPDF) getBaseWin(); }

  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( null, CHAR, REQ, "company" ).setHide(true);
    AddItemEdit( null, CHAR, REQ, "owner" ).setHide(true);

    JFormPanelResponsive row = AddItemRow();
    row.AddItemEdit( "Id.Liquidacion", CHAR, REQ, "idPDF" ).setSizeColumns(6);
    row.AddItemEdit( "Estado", CHAR, REQ, "estado" ).setSizeColumns(6);

    row = AddItemRow();
    row.AddItemEdit( "Descripcion", CHAR, REQ, "descripcion" ).setSizeColumns(12);

    row = AddItemRow();
    row.AddItemEdit( "Fecha desde", UINT, REQ, "fecha_desde" ).setSizeColumns(6);
    row.AddItemEdit( "Fecha hasta", UINT, REQ, "fecha_hasta" ).setSizeColumns(6);

    JFormTabPanelResponsive tabs = AddItemTabPanel();
    tabs.AddItemList(20);
    tabs.AddItemList(25);
    tabs.AddItemForm(10);
  }
}
