package pss.bsp.contrato.detalleLatamFamilia.calculo;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;

public class FormFamiliaLatamDetail extends JBaseForm {

    private static final long serialVersionUID = 1245253307166L;

    public FormFamiliaLatamDetail() throws Exception {
    }

    public GuiFamiliaLatamDetail getWin() { return (GuiFamiliaLatamDetail) getBaseWin(); }

    @Override
    public void InicializarPanel(JWin zWin) throws Exception {
        AddItemEdit(null, LONG, OPT, "id").setHide(true);
        AddItemEdit(null, CHAR, OPT, "company").setHide(true);
        AddItemEdit(null, LONG, OPT, "id_detalle").setHide(true);
        AddItemEdit(null, LONG, OPT, "id_contrato").setHide(true);

        JFormPanelResponsive row = AddItemRow();
        row.AddItemEdit("Familia", CHAR, REQ, "familia")
           .setToolTip("clases separadas por ,")
           .setSizeColumns(8);

        row = AddItemRow();
        row.AddItemEdit("Peso", FLOAT, REQ, "peso").setSizeColumns(4);
    }
}
