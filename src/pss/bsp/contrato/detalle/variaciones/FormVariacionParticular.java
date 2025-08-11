package pss.bsp.contrato.detalle.variaciones;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;

public class FormVariacionParticular extends JBaseForm {

    private static final long serialVersionUID = 1446860154249L;

    public FormVariacionParticular() throws Exception {
    }

    public GuiVariacionParticular getWin() {
        return (GuiVariacionParticular) getBaseWin();
    }

    public void InicializarPanel(JWin zWin) throws Exception {
        AddItemEdit(null, UINT, OPT, "linea").setHide(true);
        AddItemEdit(null, UINT, OPT, "id").setHide(true);
        AddItemEdit(null, UINT, OPT, "id_contrato").setHide(true);
        AddItemEdit(null, UINT, OPT, "linea_contrato").setHide(true);
        AddItemEdit(null, CHAR, OPT, "company").setHide(true);

        JFormPanelResponsive row = AddItemRow();
        row.AddItemEdit("Descripcion", CHAR, REQ, "descripcion").setSizeColumns(6);
        row.AddItemEdit("Variacion", FLOAT, REQ, "variacion").setSizeColumns(6);

        row = AddItemRow();
        row.AddItemDateTime("Fecha desde", DATE, REQ, "fecha_desde").setSizeColumns(6);
        row.AddItemDateTime("Fecha hasta", DATE, REQ, "fecha_hasta").setSizeColumns(6);
    }
}

