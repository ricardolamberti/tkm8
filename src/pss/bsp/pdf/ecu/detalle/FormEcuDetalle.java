package  pss.bsp.pdf.ecu.detalle;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;
import pss.core.winUI.responsiveControls.JFormTabPanelResponsive;

public class FormEcuDetalle extends JBaseForm {

  private static final long serialVersionUID = 1245253775814L;

  public FormEcuDetalle() throws Exception {}

  public GuiEcuDetalle getWin() { return (GuiEcuDetalle) getBaseWin(); }

  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( null, CHAR, REQ, "company" ).setHide(true);
    AddItemEdit( null, CHAR, REQ, "owner" ).setHide(true);
    AddItemEdit( null, CHAR, REQ, "idPDF" ).setHide(true);
    AddItemEdit( null, UINT, REQ, "linea" ).setHide(true);

    JFormPanelResponsive row = AddItemRow();
    row.AddItemEdit( "Tipo ruta", CHAR, REQ, "tipo_ruta" ).setSizeColumns(4);
    row.AddItemEdit( "Aerolinea", CHAR, REQ, "aerolinea" ).setSizeColumns(4);
    row.AddItemEdit( "Operacion", CHAR, REQ, "operacion" ).setSizeColumns(4);

    row = AddItemRow();
    row.AddItemEdit( "Boleto", CHAR, REQ, "boleto" ).setSizeColumns(4);
    row.AddItemEdit( "Fecha", UINT, REQ, "fecha" ).setSizeColumns(4);
    row.AddItemEdit( "Tarifa", UFLOAT, REQ, "tarifa" ).setSizeColumns(4);

    row = AddItemRow();
    row.AddItemEdit( "Contado", UFLOAT, REQ, "contado" ).setSizeColumns(4);
    row.AddItemEdit( "Tarjeta", UFLOAT, REQ, "tarjeta" ).setSizeColumns(4);
    row.AddItemEdit( "Base imponible", UFLOAT, REQ, "base_imponible" ).setSizeColumns(4);

    row = AddItemRow();
    row.AddItemEdit( "Impuesto1", UFLOAT, REQ, "impuesto1" ).setSizeColumns(4);
    row.AddItemEdit( "Impuesto2", UFLOAT, REQ, "impuesto2" ).setSizeColumns(4);
    row.AddItemEdit( "Comision", UFLOAT, REQ, "comision" ).setSizeColumns(4);

    row = AddItemRow();
    row.AddItemEdit( "Imp sobre com", UFLOAT, REQ, "imp_sobre_com" ).setSizeColumns(4);
    row.AddItemEdit( "Comision over", UFLOAT, REQ, "comision_over" ).setSizeColumns(4);
    row.AddItemEdit( "Observaciones", CHAR, REQ, "observaciones" ).setSizeColumns(4);

    row = AddItemRow();
    row.AddItemEdit( "Numero tarjeta", CHAR, REQ, "numero_tarjeta" ).setSizeColumns(6);
    row.AddItemEdit( "Tipo tarjeta", CHAR, REQ, "tipo_tarjeta" ).setSizeColumns(6);

    JFormTabPanelResponsive tabs = AddItemTabPanel();
    tabs.AddItemList(10);
  }
}
