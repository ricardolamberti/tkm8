package pss.bsp.consolidador.diferencia;

import pss.bsp.consolidador.consolidacion.detalle.BizConsolidacion;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;

public class FormDiferencia extends JBaseForm {

    private static final long serialVersionUID = 1247604035440L;

    public FormDiferencia() throws Exception {
    }

    public GuiDiferencia getWin() {
        return (GuiDiferencia) getBaseWin();
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
        row.AddItemCombo("Status", CHAR, OPT, "status",
                JWins.CreateVirtualWins(BizConsolidacion.ObtenerResultadosConsolidacion()))
                .setSizeColumns(4).setReadOnly(true);

        row = AddItemRow();
        row.AddItemEdit("Boleto", CHAR, OPT, "boleto").setSizeColumns(4).setReadOnly(true);
        row.AddItemEdit("Operacion", CHAR, OPT, "operacion").setSizeColumns(4).setReadOnly(true);
        row.AddItemEdit("Tipo ruta", CHAR, OPT, "tipo_ruta").setSizeColumns(4).setReadOnly(true);

        row = AddItemRow();
        row.AddItemEdit("Aerolinea", CHAR, OPT, "id_aerolinea").setSizeColumns(4).setReadOnly(true);
        row.AddItemEdit("Tarifa BSP", UFLOAT, OPT, "tarifa_bsp").setSizeColumns(4).setReadOnly(true);
        row.AddItemEdit("Contado BSP", UFLOAT, OPT, "contado_bsp").setSizeColumns(4).setReadOnly(true);

        row = AddItemRow();
        row.AddItemEdit("Credito BSP", UFLOAT, OPT, "credito_bsp").setSizeColumns(4).setReadOnly(true);
        row.AddItemEdit("Comision BSP", UFLOAT, OPT, "comision_bsp").setSizeColumns(4).setReadOnly(true);
        row.AddItemEdit("Impuesto BSP", UFLOAT, OPT, "impuesto_bsp").setSizeColumns(4).setReadOnly(true);

        row = AddItemRow();
        row.AddItemEdit("Tarifa BO", UFLOAT, OPT, "tarifa_bo").setSizeColumns(4).setReadOnly(true);
        row.AddItemEdit("Contado BO", UFLOAT, OPT, "contado_bo").setSizeColumns(4).setReadOnly(true);
        row.AddItemEdit("Credito BO", UFLOAT, OPT, "credito_bo").setSizeColumns(4).setReadOnly(true);

        row = AddItemRow();
        row.AddItemEdit("Comision BO", UFLOAT, OPT, "comision_bo").setSizeColumns(4).setReadOnly(true);
        row.AddItemEdit("Impuesto BO", UFLOAT, OPT, "impuesto_bo").setSizeColumns(4).setReadOnly(true);
        row.AddItemEdit("Tarifa Dif", UFLOAT, OPT, "tarifa_dif").setSizeColumns(4).setReadOnly(true);

        row = AddItemRow();
        row.AddItemEdit("Contado Dif", UFLOAT, OPT, "contado_dif").setSizeColumns(4).setReadOnly(true);
        row.AddItemEdit("Credito Dif", UFLOAT, OPT, "credito_dif").setSizeColumns(4).setReadOnly(true);
        row.AddItemEdit("Comision Dif", UFLOAT, OPT, "comision_dif").setSizeColumns(4).setReadOnly(true);

        row = AddItemRow();
        row.AddItemEdit("Impuesto Dif", UFLOAT, OPT, "impuesto_dif").setSizeColumns(4).setReadOnly(true);
        row.AddItemArea("Observacion", CHAR, OPT, "observacion").setSizeColumns(8);
    }
}

