package pss.tourism.pnr;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import pss.common.regions.currency.BizMoneda;
import pss.common.regions.currency.GuiMonedas;
import pss.common.security.BizUsuario;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;
import pss.core.ui.components.JPssCalendarEdit;
import pss.core.ui.components.JPssEdit;
import pss.core.ui.components.JPssImage;
import pss.core.ui.components.JPssLabel;
import pss.core.ui.components.JPssLabelFormLov;
import pss.core.ui.components.JPssPanel;
import pss.core.win.JControlWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.controls.JFormImage;
import pss.core.winUI.controls.JFormLista;
import pss.core.winUI.controls.JFormSwingRadio;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormFieldsetResponsive;
import pss.core.winUI.responsiveControls.JFormImageResponsive;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;
import pss.tourism.carrier.GuiCarriers;

public class FormPNRTicket extends JBaseForm {
	private static final long serialVersionUID=1L;

	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public FormPNRTicket() throws Exception {
		try {
			jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public GuiPNRTicket getWin() {
		return (GuiPNRTicket) getBaseWin();
	}


	// -------------------------------------------------------------------------//
	// Inicializacion Grafica
	// -------------------------------------------------------------------------//
	protected void jbInit() throws Exception {
	}
	
	public JMap<String, String> getOptions() {
		JMap<String, String> map = JCollectionFactory.createMap();
		map.addElement("Adulto", BizPNRTicket.PAX_TYPE_ADULT);
		map.addElement("Menor", BizPNRTicket.PAX_TYPE_CHILD);
		map.addElement("Infante", BizPNRTicket.PAX_TYPE_INFANT);
		return map;
	}
	// -------------------------------------------------------------------------//
	// Linkeo los campos con la variables del form
	// -------------------------------------------------------------------------//
	@Override
	public void InicializarPanel(JWin zWin) throws Exception {
		JFormControl c;
		AddItemEdit(null, ULONG, OPT, "interface_id").setHide(true);
		AddItemEdit("Companía", CHAR, OPT, "company").setHide(!BizUsuario.getUsr().isAdminUser());
		AddItemEdit(null, CHAR, OPT, "pais").setHide(true);

		JFormFieldsetResponsive fieldset1 = AddItemFieldset("Boleto");
		fieldset1.setSizeColumns(12);
		JFormPanelResponsive fieldsetInfo = fieldset1.AddItemPanel("");
		fieldsetInfo.setSizeColumns(10);
		
		fieldsetInfo.AddItemEdit( "Boleto", CHAR, OPT, "NumeroBoleto" ).setSizeColumns(2);
		fieldsetInfo.AddItemEdit( "Pasajero", CHAR, REQ, "nombre_pasajero").setSizeColumns(10);
		fieldsetInfo.AddItemDateTime("Fecha emisión", DATE, REQ, "creation_date").setSizeColumns(6);
		fieldsetInfo.AddItemDateTime("Fecha Salida", DATE, REQ, "departure_date").setSizeColumns(6);
		fieldsetInfo.AddItemEdit( "Ident.Fiscal", CHAR, OPT, "ident_fiscal").setSizeColumns(12);
		fieldsetInfo.AddItemEdit( "PNR", CHAR, OPT, "CodigoPNR" ).setSizeColumns(3);
		fieldsetInfo.AddItemEdit( "Ruta", CHAR, OPT, "route" ).setSizeColumns(3);
		fieldsetInfo.AddItemCheck("Anulado", OPT, "void").setSizeColumns(2);
		fieldsetInfo.AddItemCheck("Devuelto", OPT, "refund").setSizeColumns(2);
		fieldsetInfo.AddItemEdit( "Tipo Pasajero", CHAR, OPT, "tipo_pasajero2").setSizeColumns(2);
		
		fieldsetInfo.AddItemEdit( "GDS", CHAR, OPT, "gds" ).setSizeColumns(3);
			
		fieldsetInfo.AddItemCombo("Línea", CHAR, REQ, "CodigoAerolinea", new JControlCombo() {
			public JWins getRecords(boolean one) throws Exception {
				return getCarriers(one);
			}
		}).setSizeColumns(4);
		fieldsetInfo.AddItemCombo("Moneda", CHAR, REQ, "codigo_moneda", new JControlCombo() {
			public JWins getRecords(boolean zOneRow) throws Exception {
				return getMonedas(zOneRow, getWin().GetcDato().getCodigoMoneda());
			}
		}).setSizeColumns(4).SetValorDefault(BizMoneda.getMonedaLocal());


		
		
		JFormFieldsetResponsive fieldsetRem = fieldset1.AddItemFieldset("Estado");
		fieldsetRem.setSizeColumns(2);
		fieldsetRem.AddItemCheck( "Revisado?",  OPT, "reemitted" );
		fieldsetRem.AddItemCheck( "Exchanged?",  OPT, "exchanged" );
		fieldsetRem.AddItemCheck( "Open?",  OPT, "open" );
		fieldsetRem.AddItemEdit( "Nro.boleto original", CHAR, OPT, "boleto_original" );

		
		JFormFieldsetResponsive fieldsetTarifaFactura = AddItemFieldset("Tarifa factura");
		fieldsetTarifaFactura.setSizeColumns(8);
		JFormFieldsetResponsive fieldsetTarifaAcum = AddItemFieldset("Tarifa Acumulado + revisiones");
		fieldsetTarifaAcum.setSizeColumns(4);
		if (!getWin().isInMobile()) {
		  JFormImageResponsive i=AddItemImage( "", "imagen1" );
		  i.setSource(JPssImage.SOURCE_SCRIPT);
		  i.setSizeColumns(4);
			
		}
		
		JFormPanelResponsive fieldsetInfoC = fieldsetTarifaFactura.AddItemPanel("");
		fieldsetInfoC.setSizeColumns(6);
		JFormFieldsetResponsive fieldsetC = fieldsetTarifaFactura.AddItemFieldset("Comisión");

		fieldsetC.setSizeColumns(6);
		fieldsetC.AddItemEdit("%", UFLOAT, OPT, "comision_perc").setSizeColumns(6).SetValorDefault(0d);
		fieldsetC.AddItemEdit("Monto", UFLOAT, REQ, "comision_factura").setSizeColumns(6).SetValorDefault(0d);
		fieldsetC.AddItemEdit("% Over", UFLOAT, OPT, "porcentajeover").setSizeColumns(6).SetValorDefault(0d);
		fieldsetC.AddItemEdit("Over", UFLOAT, REQ, "importeover").setSizeColumns(6).SetValorDefault(0d);
		fieldsetC.AddItemEdit("Oversobre", REQ, "oversobre").setSizeColumns(6).SetValorDefault(true);


		
		fieldsetTarifaAcum.AddItemEdit("Tarifa Base", FLOAT, OPT, "tarifa_base").setSizeColumns(6);
		fieldsetTarifaAcum.AddItemEdit("Tarifa", UFLOAT, REQ, "tarifa").setSizeColumns(6);
		fieldsetTarifaAcum.AddItemEdit("Neto", FLOAT, REQ, "neto").setSizeColumns(6);
		fieldsetTarifaAcum.AddItemEdit("Impuestos", FLOAT, OPT, "impuestos_total").setSizeColumns(6);
		fieldsetTarifaAcum.AddItemEdit("Total", FLOAT, OPT, "tarifa_base_contax").setSizeColumns(6);

		fieldsetInfoC.AddItemEdit("Neto", FLOAT, OPT, "neto_factura");
		fieldsetInfoC.AddItemEdit("Tarifa", UFLOAT, OPT, "tarifa_factura");
		fieldsetInfoC.AddItemEdit("Impuestos", FLOAT, OPT, "impuestos_total_factura");
		fieldsetInfoC.AddItemEdit("Total", FLOAT, OPT, "tarifa_factura_total");

		JFormFieldsetResponsive fieldsetTarj = fieldsetTarifaFactura.AddItemFieldset("Tarjeta");
		fieldsetTarj.setSizeColumns(12);
		fieldsetTarj.AddItemEdit( "Monto Tarjeta", UFLOAT, OPT, "monto_tarjeta" ).setSizeColumns(4);
		fieldsetTarj.AddItemEdit( "Nro.Tarjeta", CHAR, OPT, "numero_tarjeta_mask" ).setSizeColumns(8);




	
	  JFormPanelResponsive row = AddItemTabPanel();
		row.AddItemList( 90);
		row.AddItemList( 80);
		row.AddItemList( 110);
		row.AddItemList( 30);		
		row.AddItemList( 95);
		row.AddItemList( 120);
		row.AddItemListOnDemand( 150);
		row.AddItemListOnDemand( 170);

	}
	
	

	
	protected JWins getCarriers(boolean one) throws Exception {
		GuiCarriers wins= new GuiCarriers();
		if (one) {
			wins.getRecords().addFilter("carrier", this.getWin().GetcDato().getCarrier());
		} 
		return wins;
	}
	protected JWins getMonedas(boolean zOneRow, String zMoneda) throws Exception {
		GuiMonedas wins=new GuiMonedas();
		if (zOneRow) {
			wins.getRecords().addFilter("codigo", zMoneda);
		} 
		return wins;
	}

}  //  @jve:decl-index=0:visual-constraint="15,3"
