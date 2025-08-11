package pss.bsp.contrato.total;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;

public class FormTotal extends JBaseForm {


private static final long serialVersionUID = 1446860154249L;


  /**
   * Constructor de la Clase
   */
  public FormTotal() throws Exception {
  }

  public GuiTotal getWin() { return (GuiTotal) getBaseWin(); }

  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
        AddItemEdit( null, CHAR, OPT, "id" ).setHide(true);

        JFormPanelResponsive row = AddItemRow();
        row.AddItemEdit( "Grupo", CHAR, OPT, "grupo" ).setReadOnly(true).setSizeColumns(6);
        row.AddItemEdit( "Moneda", CHAR, OPT, "moneda" ).setReadOnly(true).setSizeColumns(6);

        row = AddItemRow();
        row.AddItemEdit( "Base Comisionable", FLOAT, OPT, "base_comisionable" ).setReadOnly(true).setSizeColumns(6);
        row.AddItemEdit( "Ganancia", FLOAT, OPT, "ganancia" ).setReadOnly(true).setSizeColumns(6);
  }

}
