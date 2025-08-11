package pss.bsp.pdf.col.cabecera;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;

public class FormColCabecera extends JBaseForm {

  private static final long serialVersionUID = 1245253623694L;

  public FormColCabecera() throws Exception {
  }

  public GuiColCabecera getWin() {
    return (GuiColCabecera) getBaseWin();
  }

  public void InicializarPanel(JWin zWin) throws Exception {
    AddItemEdit(null, CHAR, REQ, "company").setHide(true);
    AddItemEdit(null, CHAR, REQ, "owner").setHide(true);

    JFormPanelResponsive row = AddItemRow();
    row.AddItemEdit("ID Liquidacion", CHAR, REQ, "idPDF").setSizeColumns(6);
    row.AddItemEdit("Descripcion", CHAR, REQ, "descripcion").setSizeColumns(6);

    row = AddItemRow();
    row.AddItemEdit("Direccion", CHAR, REQ, "direccion").setSizeColumns(6);
    row.AddItemEdit("Codigo postal", CHAR, REQ, "codigo_postal").setSizeColumns(6);

    row = AddItemRow();
    row.AddItemEdit("IATA", CHAR, REQ, "IATA").setSizeColumns(6);
    row.AddItemEdit("Clave Fiscal", CHAR, REQ, "CIF").setSizeColumns(6);

    row = AddItemRow();
    row.AddItemEdit("Localidad", CHAR, REQ, "localidad").setSizeColumns(6);
    row.AddItemEdit("Moneda", CHAR, REQ, "moneda").setSizeColumns(6);
  }
}
