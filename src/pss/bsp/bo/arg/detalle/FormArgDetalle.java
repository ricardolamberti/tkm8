package pss.bsp.bo.arg.detalle;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormArgDetalle extends JBaseForm {

    private static final long serialVersionUID = 1245258187718L;

    public FormArgDetalle() throws Exception {
    }

    public GuiArgDetalle getWin() { return (GuiArgDetalle) getBaseWin(); }

    @Override
    public void InicializarPanel(JWin zWin) throws Exception {
        AddItemRow().AddItemEdit("Company", CHAR, REQ, "company");
        AddItemRow().AddItemEdit("Owner", CHAR, REQ, "owner");
        AddItemRow().AddItemEdit("Idinterfaz", UINT, REQ, "idInterfaz");
        AddItemRow().AddItemEdit("Linea", UINT, REQ, "linea");
        AddItemRow().AddItemEdit("Tipo ruta", CHAR, REQ, "tipo_ruta");
        AddItemRow().AddItemEdit("Aerolinea", CHAR, REQ, "aerolinea");
        AddItemRow().AddItemEdit("Operacion", CHAR, REQ, "operacion");
        AddItemRow().AddItemEdit("Boleto", CHAR, REQ, "boleto");
        AddItemRow().AddItemEdit("Fecha", UINT, REQ, "fecha");
        AddItemRow().AddItemEdit("Tarifa", UFLOAT, REQ, "tarifa");
        AddItemRow().AddItemEdit("Contado", UFLOAT, REQ, "contado");
        AddItemRow().AddItemEdit("Tarjeta", UFLOAT, REQ, "tarjeta");
        AddItemRow().AddItemEdit("Base imponible", UFLOAT, REQ, "base_imponible");
        AddItemRow().AddItemEdit("Impuesto1", UFLOAT, REQ, "impuesto1");
        AddItemRow().AddItemEdit("Impuesto2", UFLOAT, REQ, "impuesto2");
        AddItemRow().AddItemEdit("Comision", UFLOAT, REQ, "comision");
        AddItemRow().AddItemEdit("Imp sobre com", UFLOAT, REQ, "imp_sobre_com");
        AddItemRow().AddItemEdit("Comision over", UFLOAT, REQ, "comision_over");
        AddItemRow().AddItemEdit("Observaciones", CHAR, REQ, "observaciones");
        AddItemRow().AddItemEdit("Numero tarjeta", CHAR, REQ, "numero_tarjeta");
        AddItemRow().AddItemEdit("Tipo tarjeta", CHAR, REQ, "tipo_tarjeta");
    }
}

