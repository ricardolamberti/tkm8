package pss.bsp.consolidador.iva;

import pss.bsp.consolidador.consolidacion.detalle.BizConsolidacion;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;

public class FormImpositivo extends JBaseForm {

    private static final long serialVersionUID = 1247604198764L;

    public FormImpositivo() throws Exception {
    }

    public GuiImpositivo getWin() {
        return (GuiImpositivo) getBaseWin();
    }

    /**
     * Linkeo los campos con la variables del form
     */
    public void InicializarPanel(JWin zWin) throws Exception {
        AddItemEdit(null, CHAR, REQ, "company").setHide(true);
        AddItemEdit(null, CHAR, REQ, "owner").setHide(true);

        JFormPanelResponsive row = AddItemRow();
        row.AddItemEdit("Id BSP", CHAR, REQ, "idpdf").setSizeColumns(4).setReadOnly(true);
        row.AddItemEdit("Linea", UINT, REQ, "linea").setSizeColumns(4).setReadOnly(true);
        row.AddItemCombo("Status", CHAR, REQ, "status",
                JWins.CreateVirtualWins(BizConsolidacion.ObtenerResultadosConsolidacion()))
                .setSizeColumns(4).setReadOnly(true);

        row = AddItemRow();
        row.AddItemEdit("Boleto", CHAR, OPT, "boleto").setSizeColumns(4).setReadOnly(true);
        row.AddItemEdit("Operacion", CHAR, OPT, "operacion").setSizeColumns(4).setReadOnly(true);
        row.AddItemEdit("Tipo ruta", CHAR, OPT, "tipo_ruta").setSizeColumns(4).setReadOnly(true);

        row = AddItemRow();
        row.AddItemEdit("Aerolinea", CHAR, OPT, "id_aerolinea").setSizeColumns(4).setReadOnly(true);
        row.AddItemEdit("Tarifa BSP", UFLOAT, OPT, "tarifa_bsp").setSizeColumns(4).setReadOnly(true);
        row.AddItemEdit("Iva BSP", UFLOAT, OPT, "iva_bsp").setSizeColumns(4).setReadOnly(true);

        row = AddItemRow();
        row.AddItemEdit("Iva Comision BSP", UFLOAT, OPT, "ivacomision_bsp").setSizeColumns(4).setReadOnly(true);
        row.AddItemEdit("Comision BSP", UFLOAT, OPT, "comision_bsp").setSizeColumns(4).setReadOnly(true);
        row.AddItemEdit("Tarifa BO", UFLOAT, OPT, "tarifa_bo").setSizeColumns(4).setReadOnly(true);

        row = AddItemRow();
        row.AddItemEdit("Iva BO", UFLOAT, OPT, "iva_bo").setSizeColumns(4).setReadOnly(true);
        row.AddItemEdit("Iva Comision BO", UFLOAT, OPT, "ivacomision_bo").setSizeColumns(4).setReadOnly(true);
        row.AddItemEdit("Comision BO", UFLOAT, OPT, "comision_bo").setSizeColumns(4).setReadOnly(true);

        row = AddItemRow();
        row.AddItemEdit("Tarifa Dif", UFLOAT, OPT, "tarifa_dif").setSizeColumns(4).setReadOnly(true);
        row.AddItemEdit("Iva Dif", UFLOAT, OPT, "iva_dif").setSizeColumns(4).setReadOnly(true);
        row.AddItemEdit("Iva Comision Dif", UFLOAT, OPT, "ivacomision_dif").setSizeColumns(4).setReadOnly(true);

        row = AddItemRow();
        row.AddItemEdit("Comision Dif", UFLOAT, OPT, "comision_dif").setSizeColumns(4).setReadOnly(true);
        row.AddItemArea("Observacion", CHAR, OPT, "observacion").setSizeColumns(8);
    }
}

