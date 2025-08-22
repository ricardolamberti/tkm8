package  pss.bsp.consolid.model.liquidacion.acumulado.invoice;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;

public class FormInvoice extends JBaseForm {


private static final long serialVersionUID = 1218223408500L;

/**
   * Constructor de la Clase
   */
  public FormInvoice() throws Exception {
  }

  public GuiInvoice GetWin() { return (GuiInvoice) getBaseWin(); }

  public void InicializarPanel( JWin zWin ) throws Exception {
  	
    AddItemEdit( null, CHAR, OPT, "company" ).setHide(true);
    AddItemEdit( null, LONG, OPT, "liquidacion_id" ).setHide(true);
    AddItemEdit( null, LONG, OPT, "liq_acum_id" ).setHide(true);
      AddItemEdit( null, CHAR, OPT, "organization" ).setHide(true);
    AddItemEdit( null, LONG, OPT, "id" ).setHide(true);
    AddItemEdit( null, CHAR, OPT, "dk" ).setHide(true);
    JFormPanelResponsive row = AddItemRow();
    row.AddItemDateTime("Fecha Desde", DATE, REQ, "fecha_desde" ).setSizeColumns(4).setReadOnly(true);;
    row.AddItemDateTime("Fecha hasta", DATE, OPT, "fecha_hasta" ).setSizeColumns(4).setReadOnly(true);;
    row = AddItemRow();
    row.AddItemCombo("Formato", CHAR, OPT, "formato" ).setSizeColumns(4).setReadOnly(true);
    row = AddItemRow();
    row.AddItemHtml("Direccion cliente", CHAR, OPT, "direccion_from" ).setSizeColumns(6).setHeight(150);
    row.AddItemHtml("Direccion ", CHAR, OPT, "direccion" ).setSizeColumns(6).setHeight(150);
    row = AddItemRow();
    row.AddItemEdit( "Moneda", CHAR, OPT, "currency" ).setSizeColumns(4);
    row.AddItemEdit( "Vat número", CHAR, OPT, "vat" ).setSizeColumns(4);
    row.AddItemEdit( "Invoice #", CHAR, OPT, "numero" ).setSizeColumns(4);
 
    row = AddItemRow();
    row.AddItemLine();
    row = AddItemRow();
    row.AddItemEdit( "Revenue of issued Tickets – total", FLOAT, OPT, "total" ).setReadOnly(true);
    row = AddItemRow();
    row.AddItemEdit( "Amount of Tax - total", FLOAT, OPT, "tax" ).setReadOnly(true);
    row = AddItemRow();
    row.AddItemLine();
    row.AddItemEdit( "Total amount of ticket revenue and tax", FLOAT, OPT, "total_and_tax" ).setReadOnly(true);
    row = AddItemRow();
    row.AddItemLine();
    row.AddItemEdit( "Total payment BSP-CC", FLOAT, OPT, "pay" ).setReadOnly(true);
    row = AddItemRow();
    row.AddItemEdit( "Total Amount", FLOAT, OPT, "tot_amount" ).setReadOnly(true);
    row = AddItemRow();
    row.AddItemLine();
    row.AddItemEdit( "Commission and refund commission", FLOAT, OPT, "comision" ).setReadOnly(true);
    row = AddItemRow();
    row.AddItemLine();
    row.AddItemEdit( "Billing amount", FLOAT, OPT, "billing" ).setReadOnly(true);
   
 
  }
}  //  @jve:decl-index=0:visual-constraint="80,14"
