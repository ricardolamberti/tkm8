package  pss.bsp.consolid.model.liquidacion.acumulado.invoice;

import pss.common.regions.divitions.GuiPaisesLista;
import pss.core.win.JControlWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormColumnResponsive;
import pss.core.winUI.responsiveControls.JFormFieldsetResponsive;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;
import pss.core.winUI.responsiveControls.JFormTabPanelResponsive;
import pss.tourism.airports.GuiAirports;
import pss.tourism.carrier.GuiCarriers;

public class FormInvoiceH6 extends JBaseForm {


private static final long serialVersionUID = 1218223408500L;

/**
   * Constructor de la Clase
   */
  public FormInvoiceH6() throws Exception {
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
    row.AddItemDateTime("Fecha Desde", DATE, REQ, "fecha_desde" ).setSizeColumns(4).setReadOnly(true);;
    row.AddItemDateTime("Fecha hasta", DATE, OPT, "fecha_hasta" ).setSizeColumns(4).setReadOnly(true);;
    
    JFormColumnResponsive col = row.AddItemColumn();
    col.setSizeColumns(6);
    col.AddItemHtml("Customer", CHAR, OPT, "direccion_from" ).setSizeColumns(12).setHeight(250);
    col = row.AddItemColumn();
    col.setSizeColumns(6);
    JFormFieldsetResponsive inv= col.AddItemFieldset("Invoice Detail");
    inv.AddItemEdit( "Period", CHAR, OPT, "direccion" ).setSizeColumns(12);
    inv.AddItemDateTime("date", DATE, OPT, "fecha_liq" ).setSizeColumns(12);
    row = AddItemRow();
    
    row.AddItemLine();
    row = AddItemRow();
    row.AddItemEdit( "Ait Ticket Fee", FLOAT, OPT, "total" ).setReadOnly(true);
  
		row = AddItemRow();
		row.AddItemEdit("Bank name", CHAR, OPT, "bank_name").setSizeColumns(4);
		row.AddItemEdit("Name of account holder", CHAR, OPT, "account_name").setSizeColumns(4);
		row.AddItemEdit("Account Number", CHAR, OPT, "account_number").setSizeColumns(4);
		row.AddItemEdit("Clabe Account Number", CHAR, OPT, "clabe_account_number").setSizeColumns(4);
		row.AddItemEdit("Currency", CHAR, OPT, "acccount_currency").setSizeColumns(4);
		row.AddItemHtml("Bank address", CHAR, OPT, "bank_address" ).setSizeColumns(12).setHeight(150);
       
 
  }
	public boolean hideChilds() throws Exception {
		return false;
	}
}  //  @jve:decl-index=0:visual-constraint="80,14"
