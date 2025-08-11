package pss.tourism.pnr;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;

public class FormPNRConnectedTicket extends JBaseForm {

    private static final long serialVersionUID = 1446642225174L;

    /**
     * Constructor de la Clase
     */
    public FormPNRConnectedTicket() throws Exception {
    }

    public GuiPNRConnectedTicket getWin() {
        return (GuiPNRConnectedTicket) getBaseWin();
    }

    /**
     * Linkeo los campos con la variables del form
     */
    @Override
    public void InicializarPanel(JWin zWin) throws Exception {
        AddItemEdit(null, CHAR, REQ, "company").setHide(true);

        JFormPanelResponsive row = AddItemRow();
        row.AddItemEdit("Numeroboleto", CHAR, REQ, "numeroboleto").setSizeColumns(6);
        row.AddItemEdit("Numeroboletoconectado", CHAR, REQ, "numeroboletoconectado").setSizeColumns(6);

        AddItemTabPanel().AddItemList(120);
    }
}

