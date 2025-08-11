package pss.tourism.pnr;

import pss.common.regions.currency.GuiMonedas;
import pss.common.security.BizUsuario;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormFieldsetResponsive;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;
import pss.tourism.carrier.GuiCarriers;

public class FormPNRReserva  extends JBaseForm {
	private static final long serialVersionUID=1L;

	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public FormPNRReserva() throws Exception {
		try {
			jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public GuiPNRReserva getWin() {
		return (GuiPNRReserva) getBaseWin();
	}


	// -------------------------------------------------------------------------//
	// Inicializacion Grafica
	// -------------------------------------------------------------------------//
	protected void jbInit() throws Exception {
	}
	

	// -------------------------------------------------------------------------//
	// Linkeo los campos con la variables del form
	// -------------------------------------------------------------------------//
	@Override
	public void InicializarPanel(JWin zWin) throws Exception {
		JFormControl c;
		AddItemEdit(null, ULONG, OPT, "secuence_id").setHide(true);
		AddItemEdit("Companía", CHAR, OPT, "company").setHide(!BizUsuario.getUsr().isAdminUser());
//		AddItemEdit(null, CHAR, OPT, "pais").setHide(true);

		JFormFieldsetResponsive fieldset1 = AddItemFieldset("Boleto");
		fieldset1.setSizeColumns(12);
		JFormPanelResponsive fieldsetInfo = fieldset1.AddItemPanel("");
		fieldsetInfo.setSizeColumns(10);
		
		fieldsetInfo.AddItemEdit( "PNR", CHAR, OPT, "codigo_pnr" ).setSizeColumns(3);
		fieldsetInfo.AddItemEdit( "Pasajero", CHAR, REQ, "nombre_pasajero").setSizeColumns(9);
		fieldsetInfo.AddItemDateTime("Fecha emisión", DATE, REQ, "creation_date").setSizeColumns(6);
		fieldsetInfo.AddItemDateTime("Fecha Salida", DATE, REQ, "departure_date").setSizeColumns(6);
		fieldsetInfo.AddItemEdit( "Ruta", CHAR, OPT, "route" ).setSizeColumns(3);
	
		fieldsetInfo.AddItemCombo("Línea", CHAR, REQ, "CodigoAerolinea", new JControlCombo() {
			public JWins getRecords(boolean one) throws Exception {
				return getCarriers(one);
			}
		}).setSizeColumns(6);
		

		fieldsetInfo.AddItemEdit( "Intinerario aeropuertos", CHAR, OPT, "air_intinerary" ).setSizeColumns(12);
		fieldsetInfo.AddItemEdit( "Intinerario aerolineas", CHAR, OPT, "carrier_intinerary" ).setSizeColumns(12);
		fieldsetInfo.AddItemEdit( "Intinerario fechas", CHAR, OPT, "fecha_intinerary" ).setSizeColumns(12);
		fieldsetInfo.AddItemEdit( "Intinerario clases", CHAR, OPT, "class_intinerary" ).setSizeColumns(12);
		fieldsetInfo.AddItemEdit( "Intinerario tarifas", CHAR, OPT, "fare_intinerary" ).setSizeColumns(12);
			

		fieldsetInfo.AddItemEdit( "Vendedor", CHAR, OPT, "vendedor" ).setSizeColumns(12);
		

	  JFormPanelResponsive row = AddItemTabPanel();
		row.AddItemList( 30);		

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

