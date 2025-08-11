package pss.bsp.contrato.detalle.prorrateo.header;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormTabPanelResponsive;

public class FormHeaderProrrateo extends JBaseForm {


private static final long serialVersionUID = 1446860278358L;

  /**
   * Propiedades de la Clase
   */


/**
   * Constructor de la Clase
   */
  public FormHeaderProrrateo() throws Exception {
  }

  public GuiHeaderProrrateo getWin() { return (GuiHeaderProrrateo) getBaseWin(); }

  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
        AddItemEdit( null, CHAR, OPT, "company" ).setHide(true);
        AddItemEdit( null, CHAR, OPT, "periodo" ).setHide(true);

        JFormTabPanelResponsive tabs = AddItemTabPanel();
        tabs.AddItemList(120);
  }

}  //  @jve:decl-index=0:visual-constraint="7,4"
