package pss.bsp.consolidador.over.overAcum;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;

public class FormOverAcum extends JBaseForm {

    private static final long serialVersionUID = 1247630958026L;

    public FormOverAcum() throws Exception {
    }

    public GuiOverAcum getWin() {
        return (GuiOverAcum) getBaseWin();
    }

    /**
     * Linkeo los campos con la variables del form
     */
    public void InicializarPanel(JWin zWin) throws Exception {
        JFormPanelResponsive row = AddItemRow();
        row.AddItemEdit("Company", CHAR, REQ, "company").setSizeColumns(4);
        row.AddItemEdit("Id BSP", CHAR, REQ, "idpdf").setSizeColumns(4);
        row.AddItemEdit("Id aerolinea", CHAR, REQ, "id_aerolinea").setSizeColumns(4);

        row = AddItemRow();
        row.AddItemEdit("Count boletos", UFLOAT, REQ, "count_boletos").setSizeColumns(4);
        row.AddItemEdit("Sum over ped", UFLOAT, REQ, "sum_over_ped").setSizeColumns(4);
        row.AddItemEdit("Sum over rec", UFLOAT, REQ, "sum_over_rec").setSizeColumns(4);

        row = AddItemRow();
        row.AddItemEdit("Sum dif", UFLOAT, REQ, "sum_dif").setSizeColumns(4);
    }
}

