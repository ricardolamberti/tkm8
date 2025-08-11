package pss.tourism.pnr;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;

public class FormPNRAuto extends JBaseForm {

    private static final long serialVersionUID = 1502224695647L;

    /**
     * Constructor de la Clase
     */
    public FormPNRAuto() throws Exception {
    }

    public GuiPNROtro getWin() {
        return (GuiPNROtro) getBaseWin();
    }

    /**
     * Linkeo los campos con la variables del form
     */
    @Override
    public void InicializarPanel(JWin zWin) throws Exception {
        AddItemEdit(null, CHAR, REQ, "company").setHide(true);

        JFormPanelResponsive row = AddItemRow();
        row.AddItemEdit("Secuencia", UINT, REQ, "secuence_id").setSizeColumns(4);
        row.AddItemEdit("Producto", CHAR, REQ, "product_desc").setSizeColumns(4);
        row.AddItemEdit("Control", CHAR, REQ, "control_data").setSizeColumns(4);

        row = AddItemRow();
        row.AddItemEdit("Acción", CHAR, REQ, "action").setSizeColumns(4);
        row.AddItemDateTime("Fecha reserva", DATE, REQ, "fecha_reserva").setSizeColumns(4);
        row.AddItemDateTime("Drop off", DATE, OPT, "drop_off_date").setSizeColumns(4);

        row = AddItemRow();
        row.AddItemEdit("Prod. secundario", CHAR, OPT, "producto_secundario").setSizeColumns(4);
        row.AddItemEdit("Pasajeros", UINT, OPT, "retorno").setSizeColumns(4);
        row.AddItemEdit("Habitaciones", UINT, OPT, "pickup_point").setSizeColumns(4);

        row = AddItemRow();
        row.AddItemEdit("Nro autos", UINT, OPT, "nro_autos").setSizeColumns(4);
        row.AddItemEdit("Car code", CHAR, OPT, "car_code").setSizeColumns(4);
        row.AddItemEdit("Confirmación", CHAR, OPT, "confirmacion").setSizeColumns(4);

        row = AddItemRow();
        row.AddItemEdit("Aeropuerto", CHAR, OPT, "aeropuerto").setSizeColumns(4);
        row.AddItemEdit("Hotel código", CHAR, OPT, "car_type").setSizeColumns(4);
        row.AddItemDateTime("Fecha salida", DATE, OPT, "fecha_salida").setSizeColumns(4);

        row = AddItemRow();
        row.AddItemEdit("Noches", UINT, OPT, "arribo_hora").setSizeColumns(4);
        row.AddItemEdit("Tour", CHAR, OPT, "tasa_garantia").setSizeColumns(4);
        row.AddItemEdit("Hotel", CHAR, OPT, "vehiculo_proveedor").setSizeColumns(4);

        row = AddItemRow();
        row.AddItemEdit("Código prod.", CHAR, OPT, "codigo_producto").setSizeColumns(4);
        row.AddItemEdit("Importe base", UFLOAT, OPT, "importe_base").setSizeColumns(4);
        row.AddItemEdit("Moneda base", CHAR, OPT, "moneda_base").setSizeColumns(4);

        row = AddItemRow();
        row.AddItemEdit("Importe", UFLOAT, OPT, "importe").setSizeColumns(4);
        row.AddItemEdit("Info", CHAR, OPT, "info").setSizeColumns(4);
        row.AddItemEdit("Tasas", UFLOAT, OPT, "tasas").setSizeColumns(4);

        row = AddItemRow();
        row.AddItemEdit("Precio total", UFLOAT, OPT, "precio_total").setSizeColumns(4);
        row.AddItemEdit("Moneda total", CHAR, OPT, "moneda_precio_total").setSizeColumns(4);
        row.AddItemEdit("Domicilio", CHAR, OPT, "domicilio").setSizeColumns(4);

        row = AddItemRow();
        row.AddItemEdit("Teléfono", CHAR, OPT, "telefono").setSizeColumns(4);
        row.AddItemEdit("Fax", CHAR, OPT, "fax").setSizeColumns(4);
        row.AddItemEdit("Código conf.", CHAR, OPT, "codigo_confirmacion").setSizeColumns(4);

        row = AddItemRow();
        row.AddItemEdit("Comisión código", CHAR, OPT, "comision_codigo").setSizeColumns(4);
        row.AddItemEdit("Comisión texto", CHAR, OPT, "comision_texto").setSizeColumns(4);
        row.AddItemEdit("Comisión monto", UFLOAT, OPT, "comision_monto").setSizeColumns(4);

        row = AddItemRow();
        row.AddItemEdit("Tipo tarjeta", CHAR, OPT, "tipo_tarjeta").setSizeColumns(4);
        row.AddItemEdit("Tarjeta", CHAR, OPT, "tarjeta").setSizeColumns(4);
        row.AddItemEdit("Tax 1", CHAR, OPT, "tax_breakdown1").setSizeColumns(4);

        row = AddItemRow();
        row.AddItemEdit("Tax 2", CHAR, OPT, "tax_breakdown2").setSizeColumns(4);
        row.AddItemEdit("Tax 3", CHAR, OPT, "tax_breakdown3").setSizeColumns(4);
        row.AddItemEdit("Tax 4", CHAR, OPT, "tax_breakdown4").setSizeColumns(4);

        row = AddItemRow();
        row.AddItemEdit("Surch1", CHAR, OPT, "surchange1").setSizeColumns(4);
        row.AddItemEdit("Surch2", CHAR, OPT, "surchange2").setSizeColumns(4);
        row.AddItemEdit("Surch3", CHAR, OPT, "surchange3").setSizeColumns(4);

        row = AddItemRow();
        row.AddItemEdit("Surch4", CHAR, OPT, "surchange4").setSizeColumns(4);
        row.AddItemEdit("Desc1", CHAR, OPT, "disclaimer1").setSizeColumns(4);
        row.AddItemEdit("Desc2", CHAR, OPT, "disclaimer2").setSizeColumns(4);

        row = AddItemRow();
        row.AddItemEdit("Upgrade", CHAR, OPT, "upgrade").setSizeColumns(4);
        row.AddItemEdit("Drop off loc.", CHAR, OPT, "dop_off_location").setSizeColumns(4);
        row.AddItemEdit("Drop off chg.", CHAR, OPT, "drop_off_charge").setSizeColumns(4);

        row = AddItemRow();
        row.AddItemEdit("Corp. discount", CHAR, OPT, "corporate_discount").setSizeColumns(4);
        row.AddItemEdit("Tourcode", CHAR, OPT, "tourcode").setSizeColumns(4);
        row.AddItemEdit("Remarks", CHAR, OPT, "remarks").setSizeColumns(4);

        row = AddItemRow();
        row.AddItemEdit("Pasajero freq.", CHAR, OPT, "pasajero_frecuente").setSizeColumns(4);
        row.AddItemEdit("Nombre cliente", CHAR, OPT, "nombre_cliente").setSizeColumns(4);
        row.AddItemEdit("Cupon", CHAR, OPT, "cupon").setSizeColumns(4);

        row = AddItemRow();
        row.AddItemEdit("Booking source", CHAR, OPT, "booking_source").setSizeColumns(4);
        row.AddItemEdit("Precio", UFLOAT, OPT, "importe").setSizeColumns(4);
        row.AddItemEdit("Voucher tipo", CHAR, OPT, "voucher_tipo").setSizeColumns(4);

        row = AddItemRow();
        row.AddItemEdit("Voucher nro", CHAR, OPT, "voucher_numero").setSizeColumns(4);
        row.AddItemEdit("Voucher biling", CHAR, OPT, "voucher_biling").setSizeColumns(4);
        row.AddItemEdit("Voucher formato", CHAR, OPT, "voucher_format").setSizeColumns(4);

        row = AddItemRow();
        row.AddItemEdit("Equip. confirmado", CHAR, OPT, "equipamiento_confirmado").setSizeColumns(4);
        row.AddItemEdit("Garantía info", CHAR, OPT, "nombre_pasajero").setSizeColumns(4);
        row.AddItemEdit("Vendedor", CHAR, OPT, "vendedor").setSizeColumns(4);

        AddItemTabPanel().AddItemList(120);
    }
}

