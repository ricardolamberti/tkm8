package  pss.bsp.pdf.bol.impuesto;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;

public class FormBolImpuesto extends JBaseForm {

  private static final long serialVersionUID = 1245351577697L;

  public FormBolImpuesto() throws Exception {}

  public GuiBolImpuesto getWin() { return (GuiBolImpuesto) getBaseWin(); }

  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( null, CHAR, REQ, "company" ).setHide(true);
    AddItemEdit( null, CHAR, REQ, "owner" ).setHide(true);
    AddItemEdit( null, CHAR, REQ, "idPDF" ).setHide(true);
    AddItemEdit( null, UINT, REQ, "linea" ).setHide(true);

    JFormPanelResponsive row = AddItemRow();
    row.AddItemEdit( "Iso", CHAR, REQ, "iso" ).setSizeColumns(4);
    row.AddItemEdit( "Contado", UFLOAT, REQ, "contado" ).setSizeColumns(4);
    row.AddItemEdit( "Tarjeta", UFLOAT, REQ, "tarjeta" ).setSizeColumns(4);
  }
}
