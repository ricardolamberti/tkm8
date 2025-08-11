package pss.bsp.consolid.model.liquidacion.errors;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormEditLiqError extends JBaseForm {

	private static final long serialVersionUID = 1477827540739L;

	/**
	 * Constructor de la Clase
	 */
	public FormEditLiqError() throws Exception {
	}

	public GuiLiqError getWin() {
		return (GuiLiqError) getBaseWin();
	}

	@Override
	public void InicializarPanel(JWin zBase) throws Exception {
		this.AddItemEdit(null, CHAR, OPT, "linea").setHide(true);
		this.AddItemEdit(null, CHAR, OPT, "company").setHide(true);
		this.AddItemEdit(null, CHAR, OPT, "liquidacion_id").setHide(true);
		this.AddItemCombo("Tipo", CHAR, REQ, "type_error").setSizeColumns(4);
		this.AddItemEdit("Error", CHAR, REQ, "error").setSizeColumns(12);
		this.AddItemEdit(null, CHAR, OPT, "interface_id").setHide(true);
		this.AddItemEdit(null, CHAR, OPT, "dk").setHide(true);
		this.AddItemEdit(null, CHAR, OPT, "tipo_doc").setHide(true);
		this.AddItemEdit(null, CHAR, OPT, "nro_doc").setHide(true);
		this.AddItemEdit(null, CHAR, OPT, "fecha_doc").setHide(true);
		this.AddItemEdit(null, CHAR, OPT, "nro_boleto").setHide(true);
		this.AddItemEdit(null, CHAR, OPT, "prestador").setHide(true);
		this.AddItemEdit(null, CHAR, OPT, "prestador_tkm").setHide(true);
		this.AddItemEdit(null, CHAR, OPT, "ruta").setHide(true);
		this.AddItemEdit(null, CHAR, OPT, "ruta_tkm").setHide(true);
		this.AddItemEdit(null, CHAR, OPT, "pasajero").setHide(true);
		this.AddItemEdit(null, CHAR, OPT, "reserva").setHide(true);
		this.AddItemEdit(null, CHAR, OPT, "iata").setHide(true);
		this.AddItemEdit(null, CHAR, OPT, "iata_tkm").setHide(true);
		this.AddItemEdit(null, CHAR, OPT, "tarifa").setHide(true);
		this.AddItemEdit(null, CHAR, OPT, "tarifa_tkm").setHide(true);
		this.AddItemEdit(null, CHAR, OPT, "iva").setHide(true);
		this.AddItemEdit(null, CHAR, OPT, "iva_tkm").setHide(true);
		this.AddItemEdit(null, CHAR, OPT, "tua").setHide(true);
		this.AddItemEdit(null, CHAR, OPT, "tua_tkm").setHide(true);
		this.AddItemEdit(null, CHAR, OPT, "importe").setHide(true);
		this.AddItemEdit(null, CHAR, OPT, "importe_tkm").setHide(true);
		this.AddItemEdit(null, CHAR, OPT, "saldo").setHide(true);
		this.AddItemEdit(null, CHAR, OPT, "saldo_tkm").setHide(true);
		this.AddItemEdit(null, CHAR, OPT, "tipo_servicio").setHide(true);
		this.AddItemEdit(null, CHAR, OPT, "tipo_servicio_tkm").setHide(true);
		this.AddItemEdit(null, CHAR, OPT, "clase").setHide(true);
		this.AddItemEdit(null, CHAR, OPT, "clase_tkm").setHide(true);
		this.AddItemEdit(null, CHAR, OPT, "clases").setHide(true);
		this.AddItemEdit(null, CHAR, OPT, "clases_tkm").setHide(true);
		this.AddItemEdit(null, CHAR, OPT, "lineas").setHide(true);
		this.AddItemEdit(null, CHAR, OPT, "lineas_tkm").setHide(true);
		this.AddItemEdit(null, CHAR, OPT, "forma_pago").setHide(true);
		this.AddItemEdit(null, CHAR, OPT, "forma_pago_tkm").setHide(true);
		this.AddItemEdit(null, CHAR, OPT, "gds_tkm").setHide(true);
		this.AddItemEdit(null, CHAR, OPT, "emision_tkm").setHide(true);
		this.AddItemEdit(null, CHAR, OPT, "tarjeta_tkm").setHide(true);
		this.AddItemEdit(null, CHAR, OPT, "fecha_salida_tkm").setHide(true);
		this.AddItemEdit(null, CHAR, OPT, "fecha_fin_tkm").setHide(true);
		this.AddItemEdit(null, CHAR, OPT, "origen_tkm").setHide(true);
		this.AddItemEdit(null, CHAR, OPT, "destino_tkm").setHide(true);
		this.AddItemEdit(null, CHAR, OPT, "porc_incentivo").setHide(true);
		this.AddItemEdit(null, CHAR, OPT, "incentivo").setHide(true);
		this.AddItemEdit(null, CHAR, OPT, "tipo").setHide(true);
		this.AddItemEdit(null, CHAR, OPT, "contado").setHide(true);
		this.AddItemEdit(null, CHAR, OPT, "tarjeta").setHide(true);
		this.AddItemEdit(null, CHAR, OPT, "fiscal").setHide(true);
		this.AddItemEdit(null, CHAR, OPT, "nofiscal").setHide(true);
		this.AddItemEdit(null, CHAR, OPT, "noDevengado").setHide(true);
	}
} // @jve:decl-index=0:visual-constraint="14,3"
