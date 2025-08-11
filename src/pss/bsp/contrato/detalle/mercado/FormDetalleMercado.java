package pss.bsp.contrato.detalle.mercado;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;

public class FormDetalleMercado extends JBaseForm {

    private static final long serialVersionUID = 1245253775814L;

    public FormDetalleMercado() throws Exception {
    }

    public GuiDetalleMercado getWin() { return (GuiDetalleMercado) getBaseWin(); }

    @Override
    public void InicializarPanel(JWin zWin) throws Exception {
        AddItemEdit(null, CHAR, REQ, "company").setHide(true);
        AddItemEdit(null, CHAR, REQ, "owner").setHide(true);
        AddItemEdit(null, CHAR, REQ, "idPDF").setHide(true);
        AddItemEdit(null, UINT, REQ, "linea").setHide(true);

        JFormPanelResponsive row = AddItemRow();
        row.AddItemEdit("Aerolinea", CHAR, REQ, "id_aerolinea").setSizeColumns(6);
        row.AddItemEdit("Marketing ID", CHAR, REQ, "marketing_id").setSizeColumns(6);

        row = AddItemRow();
        row.AddItemEdit("Origen", CHAR, REQ, "origen").setSizeColumns(6);
        row.AddItemEdit("Destino", CHAR, REQ, "destino").setSizeColumns(6);

        row = AddItemRow();
        row.AddItemEdit("FMS", UFLOAT, REQ, "fms").setSizeColumns(4);
    }
}
