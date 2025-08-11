package pss.bsp.consolidador.over;

import pss.bsp.consolidador.consolidacion.detalle.BizConsolidacion;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;

public class FormOver extends JBaseForm {

    private static final long serialVersionUID = 1247605020960L;

    public FormOver() throws Exception {
    }

    public GuiOver getWin() {
        return (GuiOver) getBaseWin();
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
        row.AddItemEdit("Fecha", UINT, OPT, "fecha").setSizeColumns(4).setReadOnly(true);
        row.AddItemEdit("Aerolinea", CHAR, OPT, "id_aerolinea").setSizeColumns(4).setReadOnly(true);

        row = AddItemRow();
        row.AddItemEdit("Over pedido", UFLOAT, OPT, "over_ped").setSizeColumns(4).setReadOnly(true);
        row.AddItemEdit("Over recibido", UFLOAT, OPT, "over_rec").setSizeColumns(4).setReadOnly(true);
        row.AddItemEdit("Diferencia", UFLOAT, OPT, "over_dif").setSizeColumns(4).setReadOnly(true);

        row = AddItemRow();
        row.AddItemArea("Observaciones", CHAR, OPT, "observaciones").setSizeColumns(12);
    }
}

