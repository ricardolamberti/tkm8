package pss.bsp.consolid.model.liquidacion.acumulado;

import pss.bsp.bspBusiness.BizBSPCompany;
import pss.common.security.BizUsuario;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormColumnResponsive;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;
import pss.core.winUI.responsiveControls.JFormTabPanelResponsive;
import pss.www.ui.JWebIcon;

public class FormLiqAcum extends JBaseForm {

	private static final long serialVersionUID = 1477827540739L;

	/**
	 * Constructor de la Clase
	 */
	public FormLiqAcum() throws Exception {
	}

	public GuiLiqAcum getWin() {
		return (GuiLiqAcum) getBaseWin();
	}

	public void InicializarPanel(JWin zWin) throws Exception {
  	JFormPanelResponsive row = AddItemRow();
    row.AddImageCard("Imprimir", JWebIcon.getResponsiveIcon("fa fa-file-pdf fa-3x"), "Emitir en PDF", 20).setSizeColumns(3);
    row.AddImageCard("Exportar", JWebIcon.getResponsiveIcon("fa fa-file-excel fa-3x"), "Emitir en EXCEL", 30).setSizeColumns(3);
		AddItemEdit(null, CHAR, OPT, "company").setHide(true);
		AddItemEdit(null, CHAR, OPT, "liquidacion_id").setHide(true);
		AddItemEdit(null, CHAR, OPT, "pendiente").setHide(true);
		AddItemEdit("DK", CHAR, OPT, "dk").setSizeColumns(2).setReadOnly(true);
		AddItemEdit("Descripción", CHAR, OPT, "descripcion").setSizeColumns(6).setReadOnly(true);
		AddItemEdit("Estado", CHAR, OPT, "estado").setSizeColumns(4).setReadOnly(true);
		
		 row = AddItemRow();
		row.AddItemDateTime("Fecha desde", DATE, REQ, "fecha_desde").setSizeColumns(6);
		row.AddItemDateTime("Fecha hasta", DATE, REQ, "fecha_hasta").setSizeColumns(6);
		row = AddItemRow();
		row.AddItemDateTime("Fecha Liquidación", DATE, OPT, "fecha_liq").setSizeColumns(6).setReadOnly(true);
		row.AddItemEdit("Moneda", FLOAT, OPT, "moneda").setSizeColumns(6).SetValorDefault(BizBSPCompany.getObjBSPCompany(BizUsuario.getUsr().getCompany()).getCurrency()).SetReadOnly(true);
		row = AddItemRow();
		JFormColumnResponsive col1 = row.AddItemColumn();
		col1.setSizeColumns(6);
		JFormColumnResponsive col2 = row.AddItemColumn();
		col2.setSizeColumns(6);
		JFormPanelResponsive zonaResumen = col1.addItemFieldset("Resumen", 12);
		JFormPanelResponsive zonaComisionumen = col2.addItemFieldset("Comisión", 12);

		zonaResumen.AddItemEdit("Doc.Fiscales", FLOAT, OPT, "total_fiscal").setReadOnly(true);
		zonaResumen.AddItemEdit("Doc.No Fiscales", FLOAT, OPT, "total_nofiscal").setReadOnly(true);
		zonaResumen.AddItemEdit("Total facturado en el período", FLOAT, OPT, "total_facturado").setReadOnly(true);
		zonaResumen.AddItemEdit("Total venta tarjeta de crédito", FLOAT, OPT, "total_tarjeta").setReadOnly(true);
		zonaResumen.AddItemEdit("Total venta efectivo", FLOAT, OPT, "total_contado").setReadOnly(true);
		zonaResumen.AddItemEdit("Moneda", CHAR, OPT, "moneda").setReadOnly(true);
		zonaResumen.AddItemDateTime("Total a pagar debe liquidarse en", DATE, OPT, "fecha_comision");

		zonaComisionumen.AddItemEdit("Comisión Neto", FLOAT, OPT, "comision_neto").setReadOnly(true);
		zonaComisionumen.AddItemEdit("Comisión No devengada", FLOAT, OPT, "comision_no_devengada").setReadOnly(true);
		zonaComisionumen.AddItemEdit("Subtotal Comisión", FLOAT, OPT, "comision").setReadOnly(true);
		zonaComisionumen.AddItemEdit("Iva Comisión", FLOAT, OPT, "iva_comision").setReadOnly(true);
		zonaComisionumen.AddItemEdit("Total Factura Comisión", FLOAT, OPT, "total_comision").setReadOnly(true);
		zonaComisionumen.AddItemDateTime("Pago comisión se efectua en", DATE, OPT, "fecha_pago");

		zonaResumen = col2.addItemFieldset("Cuenta", 12);
		zonaComisionumen = col1.addItemFieldset("Factuación", 12);

		zonaResumen.AddItemEdit("Depositar en", CHAR, OPT, "cuenta");
		zonaResumen.AddItemEdit("CBU", CHAR, OPT, "cbu");

		zonaComisionumen.AddItemHtml("", CHAR, OPT, "info").setHeight(150);

		JFormTabPanelResponsive tabs = AddItemTabPanel();
		tabs.AddItemTab(10);
		tabs.AddItemTab(40);

	}
} // @jve:decl-index=0:visual-constraint="14,3"
