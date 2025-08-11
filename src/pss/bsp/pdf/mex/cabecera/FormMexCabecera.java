package pss.bsp.pdf.mex.cabecera;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;

public class FormMexCabecera extends JBaseForm {

  private static final long serialVersionUID = 1245253623694L;

  public FormMexCabecera() throws Exception {}

  public GuiMexCabecera getWin() { return (GuiMexCabecera) getBaseWin(); }

  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( null, CHAR, REQ, "company" ).setHide(true);
    AddItemEdit( null, CHAR, REQ, "owner" ).setHide(true);

    JFormPanelResponsive row = AddItemRow();
    row.AddItemEdit( "ID Liquidacion", CHAR, REQ, "idPDF" ).setSizeColumns(6);
    row.AddItemEdit( "IATA", CHAR, REQ, "IATA" ).setSizeColumns(6);

    row = AddItemRow();
    row.AddItemEdit( "Descripcion", CHAR, REQ, "descripcion" ).setSizeColumns(12);

    row = AddItemRow();
    row.AddItemEdit( "Direccion", CHAR, REQ, "direccion" ).setSizeColumns(6);
    row.AddItemEdit( "Codigo postal", CHAR, REQ, "codigo_postal" ).setSizeColumns(6);

    row = AddItemRow();
    row.AddItemEdit( "Localidad", CHAR, REQ, "localidad" ).setSizeColumns(6);
    row.AddItemEdit( "Clave Fiscal", CHAR, REQ, "CIF" ).setSizeColumns(6);

    row = AddItemRow();
    row.AddItemEdit( "Moneda", CHAR, REQ, "moneda" ).setSizeColumns(6);
  }
}
