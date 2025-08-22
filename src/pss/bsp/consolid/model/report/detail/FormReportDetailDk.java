package pss.bsp.consolid.model.report.detail;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;

public class FormReportDetailDk extends JBaseForm {

	
	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public FormReportDetailDk() throws Exception {

	}

	public GuiReportDetailDk GetWin() {
		return (GuiReportDetailDk) GetBaseWin();
	}
	 public void InicializarPanel( JWin zWin ) throws Exception {
	    AddItemEdit( null, CHAR, OPT, "company" ).setHide(true);
	    AddItemEdit( null, UINT, OPT, "id" ).setHide(true);
	    AddItemEdit( null, CHAR, OPT, "id_report" ).setHide(true);
	    JFormPanelResponsive row = this.AddItemRow();
	    row.AddItemEdit("Tipo", CHAR, OPT, "type").setSizeColumns(4).setReadOnly(true);
	    row.AddItemEdit("Fecha Ticket", CHAR, OPT, "date_of_tick").setSizeColumns(4).setReadOnly(true);
	    row = this.AddItemRow();
	    row.AddItemEdit("CTrip", CHAR, OPT, "ctrip").setSizeColumns(4).setReadOnly(true);
	    row = this.AddItemRow();
	    row.AddItemEdit("Nro Boleto", CHAR, OPT, "tkt_number").setSizeColumns(4).setReadOnly(true);
	    row.AddItemEdit("Pasajero", CHAR, OPT, "passager_name").setSizeColumns(6).setReadOnly(true);
	    row = this.AddItemRow();
	    row.AddItemEdit("Vuelo", CHAR, OPT, "flight_nro").setSizeColumns(4).setReadOnly(true);
	    row.AddItemEdit("O&D", CHAR, OPT, "od").setSizeColumns(4).setReadOnly(true);
	    row = this.AddItemRow();
	    row.AddItemEdit("Monto CTrip", FLOAT, OPT, "ctrip_amount").setSizeColumns(4).setReadOnly(true);
	    row = this.AddItemRow();
	    row.AddItemEdit("Total", FLOAT, OPT, "total").setSizeColumns(4);
	    row = this.AddItemRow();
	    row.AddItemEdit("Fare", FLOAT, OPT, "fare").setSizeColumns(4);
	    row = this.AddItemRow();
	    row.AddItemEdit("Comisión", FLOAT, OPT, "comision").setSizeColumns(4);
	    row = this.AddItemRow();
	    row.AddItemEdit("Fee", FLOAT, OPT, "fee").setSizeColumns(4);
	    row = this.AddItemRow();
	    row.AddItemEdit("Total a Pagar", FLOAT, OPT, "total_pay").setSizeColumns(4);

	 }


	// -------------------------------------------------------------------------//
	// Linkeo los campos con la variables del form
	// -------------------------------------------------------------------------//


} // @jve:decl-index=0:visual-constraint="10,10"
