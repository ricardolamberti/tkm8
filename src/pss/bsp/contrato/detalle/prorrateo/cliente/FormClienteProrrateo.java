package pss.bsp.contrato.detalle.prorrateo.cliente;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;
import pss.core.winUI.responsiveControls.JFormTabPanelResponsive;

public class FormClienteProrrateo extends JBaseForm {


private static final long serialVersionUID = 1446860278358L;

  /**
   * Propiedades de la Clase
   */


/**
   * Constructor de la Clase
   */
  public FormClienteProrrateo() throws Exception {
  }

  public GuiClienteProrrateo getWin() { return (GuiClienteProrrateo) getBaseWin(); }

  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
        AddItemEdit( null, CHAR, OPT, "company" ).setHide(true);
        AddItemEdit( null, CHAR, OPT, "periodo" ).setHide(true);

        JFormPanelResponsive row = AddItemRow();
        row.AddItemEdit( "Cliente", CHAR, REQ, "cliente" ).setSizeColumns(6);
        row.AddItemEdit( "Descripción", CHAR, OPT, "descripcion" ).setSizeColumns(6);

        row = AddItemRow();
        row.AddItemEdit( "Número", CHAR, OPT, "numero" ).setSizeColumns(4);
        row.AddItemEdit( "Moneda", CHAR, OPT, "moneda" ).setSizeColumns(4);
        row.AddItemEdit( "Tarifa", FLOAT, OPT, "monto" ).setSizeColumns(4);

        row = AddItemRow();
        row.AddItemEdit( "Comisión", FLOAT, OPT, "comision" ).setSizeColumns(4);

        JFormTabPanelResponsive tabs = AddItemTabPanel();
        tabs.AddItemList(120);
  }

}  //  @jve:decl-index=0:visual-constraint="7,4"
