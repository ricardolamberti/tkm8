package  pss.bsp.consolid.model.liquidacion.acumulado.invoice;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormColumnResponsive;
import pss.core.winUI.responsiveControls.JFormFieldsetResponsive;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;
import pss.core.winUI.responsiveControls.JFormTabPanelResponsive;

public class FormInvoiceH5 extends JBaseForm {


private static final long serialVersionUID = 1218223408500L;

/**
   * Constructor de la Clase
   */
  public FormInvoiceH5() throws Exception {
  }

  public GuiInvoice GetWin() { return (GuiInvoice) getBaseWin(); }

  public void InicializarPanel( JWin zWin ) throws Exception {
  	
    AddItemEdit( null, CHAR, OPT, "company" ).setHide(true);
    AddItemEdit( null, LONG, OPT, "liquidacion_id" ).setHide(true);
    AddItemEdit( null, LONG, OPT, "liq_acum_id" ).setHide(true);
     AddItemEdit( null, CHAR, OPT, "organization" ).setHide(true);
    AddItemEdit( null, LONG, OPT, "id" ).setHide(true);
    AddItemEdit( null, CHAR, OPT, "dk" ).setHide(true);
    AddItemCombo("Formato", CHAR, OPT, "formato" ).setHide(true);
    JFormPanelResponsive row = AddItemRow();
    
    JFormColumnResponsive col = row.AddItemColumn();
    col.setSizeColumns(6);
    col.AddItemHtml("Customer", CHAR, OPT, "direccion_from" ).setSizeColumns(12).setHeight(250);
    col = row.AddItemColumn();
    col.setSizeColumns(6);
    JFormFieldsetResponsive inv= col.AddItemFieldset("Invoice Detail");
    inv.AddItemEdit( "Invoice", CHAR, OPT, "direccion" ).setSizeColumns(12);
    inv.AddItemDateTime("Invoice date", DATE, OPT, "fecha_invoice" ).setSizeColumns(12).setReadOnly(true);;
    inv.AddItemDateTime("Sale period From", DATE, REQ, "fecha_desde" ).setSizeColumns(6).setReadOnly(true);;
    inv.AddItemDateTime("to", DATE, OPT, "fecha_hasta" ).setSizeColumns(6).setReadOnly(true);;
    inv.AddItemDateTime("Due date", DATE, OPT, "fecha_liq" ).setSizeColumns(12);
    row = AddItemRow();
    
    JFormTabPanelResponsive tabs = row.AddItemTabPanel();
    tabs.AddItemTab(60);
    row.AddItemLine();
    row = AddItemRow();
    row.AddItemEdit( "Total report", FLOAT, OPT, "total" ).setReadOnly(true);
    row.AddItemLine();
    row.AddItemEdit( "Deductions", FLOAT, OPT, "pay" ).setReadOnly(true);
    row = AddItemRow();
    row.AddItemEdit( "Total pay", FLOAT, OPT, "billing" ).setReadOnly(true);

		row = AddItemRow();
		row.AddItemEdit("Bank name", CHAR, OPT, "bank_name").setSizeColumns(4);
		row.AddItemEdit("Beneficiary", CHAR, OPT, "account_name").setSizeColumns(4);
		row.AddItemEdit("Clabe Account Number", CHAR, OPT, "clabe_account_number").setSizeColumns(4);
		row.AddItemEdit("Swift", CHAR, OPT, "swift").setSizeColumns(4);
	   
 
  }
	public boolean hideChilds() throws Exception {
		return false;
	}
}  //  @jve:decl-index=0:visual-constraint="80,14"
