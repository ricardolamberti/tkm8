package  pss.bsp.consolid.model.liquidacion.acumulado.invoice.detail;

import pss.common.regions.divitions.GuiPaisesLista;
import pss.core.win.JControlWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;
import pss.tourism.airports.GuiAirports;
import pss.tourism.carrier.GuiCarriers;

public class FormInvoiceDetail extends JBaseForm {


private static final long serialVersionUID = 1218223408500L;

/**
   * Constructor de la Clase
   */
  public FormInvoiceDetail() throws Exception {
  }

  public GuiInvoiceDetail GetWin() { return (GuiInvoiceDetail) getBaseWin(); }


}  //  @jve:decl-index=0:visual-constraint="80,14"
