package pss.tourism.pnr;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;

public class FormPNRTax extends JBaseForm {

    private static final long serialVersionUID = 1446642143959L;

    /**
     * Constructor de la Clase
     */
    public FormPNRTax() throws Exception {
    }

    public GuiPNRTax getWin() {
        return (GuiPNRTax) getBaseWin();
    }

    /**
     * Linkeo los campos con la variables del form
     */
    @Override
    public void InicializarPanel(JWin zWin) throws Exception {
        AddItemEdit(null, CHAR, REQ, "company").setHide(true);
        AddItemEdit(null, UINT, REQ, "interface_id").setHide(true);

        JFormPanelResponsive row = AddItemRow();
        row.AddItemEdit("Codigomoneda", CHAR, REQ, "codigomoneda").setSizeColumns(4);
        row.AddItemEdit("Codigoimpuesto", CHAR, REQ, "codigoimpuesto").setSizeColumns(4);
        row.AddItemEdit("Codigopnr", CHAR, REQ, "codigopnr").setSizeColumns(4);

        row = AddItemRow();
        row.AddItemEdit("Numeroboleto", CHAR, REQ, "numeroboleto").setSizeColumns(4);
        row.AddItemEdit("Secuencia", CHAR, REQ, "secuencia").setSizeColumns(4);
        row.AddItemEdit("Importe", UFLOAT, REQ, "importe").setSizeColumns(4);

        row = AddItemRow();
        row.AddItemCheck("Virtual", OPT, "virtual").setSizeColumns(4);

        AddItemTabPanel().AddItemList(120);
    }
}

