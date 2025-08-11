package pss.bsp.consolidador.consolidacionOracle;

import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;

public class FormConsolidacionOracle extends JBaseForm {

    private static final long serialVersionUID = 1246906123540L;

    public FormConsolidacionOracle() throws Exception {
    }

    public GuiConsolidacionOracle getWin() {
        return (GuiConsolidacionOracle) getBaseWin();
    }

    /**
     * Linkeo los campos con la variables del form
     */
    public void InicializarPanel(JWin zWin) throws Exception {
        AddItemEdit(null, CHAR, REQ, "company").setHide(true);
        AddItemEdit(null, CHAR, REQ, "owner").setHide(true);
        AddItemEdit(null, UINT, OPT, "linea").setHide(true);

        JFormPanelResponsive row = AddItemRow();
        row.AddItemEdit("Id BSP", CHAR, REQ, "idpdf").setSizeColumns(4);
        row.AddItemCombo("Status", CHAR, OPT, "status",
                JWins.CreateVirtualWins(BizConsolidacionOracle.ObtenerResultadosConsolidacion()))
                .setSizeColumns(4).setReadOnly(true);
        row.AddItemEdit("Boletos", CHAR, OPT, "d1").setSizeColumns(4).setReadOnly(true);

        row = AddItemRow();
        row.AddItemEdit("Fecha", CHAR, OPT, "d2").setSizeColumns(4).setReadOnly(true);
        row.AddItemEdit("Operacion", CHAR, OPT, "d3").setSizeColumns(4).setReadOnly(true);
        row.AddItemEdit("Id Aerolinea", CHAR, OPT, "d4").setSizeColumns(4).setReadOnly(true);

        row = AddItemRow();
        row.AddItemEdit("Aerolinea", CHAR, OPT, "d5").setSizeColumns(4).setReadOnly(true);
        row.AddItemEdit("Tarifa", CHAR, OPT, "d6").setSizeColumns(4).setReadOnly(true);
        row.AddItemEdit("Contado", CHAR, OPT, "d7").setSizeColumns(4).setReadOnly(true);

        row = AddItemRow();
        row.AddItemEdit("Tarjeta", CHAR, OPT, "d8").setSizeColumns(4).setReadOnly(true);
        row.AddItemEdit("Tipo Ruta", CHAR, OPT, "d9").setSizeColumns(4).setReadOnly(true);
        row.AddItemEdit("Base Imponible", CHAR, OPT, "d10").setSizeColumns(4).setReadOnly(true);

        row = AddItemRow();
        row.AddItemEdit("Comision", CHAR, OPT, "d11").setSizeColumns(4).setReadOnly(true);
        row.AddItemEdit("Comision Over", CHAR, OPT, "d12").setSizeColumns(4).setReadOnly(true);
        row.AddItemEdit("Comision Porcentaje", CHAR, OPT, "d13").setSizeColumns(4).setReadOnly(true);

        row = AddItemRow();
        row.AddItemEdit("Impuesto Comision", CHAR, OPT, "d14").setSizeColumns(4).setReadOnly(true);
        row.AddItemEdit("Impuesto 1", CHAR, OPT, "d15").setSizeColumns(4).setReadOnly(true);
        row.AddItemEdit("Impuesto 2", CHAR, OPT, "d16").setSizeColumns(4).setReadOnly(true);

        row = AddItemRow();
        row.AddItemEdit("Total", CHAR, OPT, "d17").setSizeColumns(4).setReadOnly(true);
        row.AddItemEdit("Tipo Tarjeta", CHAR, OPT, "d18").setSizeColumns(4).setReadOnly(true);
        row.AddItemEdit("Nro.Tarjeta", CHAR, OPT, "d19").setSizeColumns(4).setReadOnly(true);

        row = AddItemRow();
        row.AddItemEdit("Observacion BSP", CHAR, OPT, "d20").setSizeColumns(4).setReadOnly(true);
        row.AddItemArea("Observaciones", CHAR, OPT, "observaciones").setSizeColumns(8);
    }
}

