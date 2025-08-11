package pss.bsp.contrato.detalleCopa.objetivos;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;

public class FormObjetivosCopa extends JBaseForm {

    private static final long serialVersionUID = 1446860154249L;

    public FormObjetivosCopa() throws Exception {
    }

    public GuiObjetivosCopa getWin() { return (GuiObjetivosCopa) getBaseWin(); }

    @Override
    public void InicializarPanel(JWin zWin) throws Exception {
        AddItemEdit(null, CHAR, REQ, "company").setHide(true);
        AddItemEdit(null, UINT, OPT, "linea").setHide(true);
        AddItemEdit(null, UINT, OPT, "id").setHide(true);
        AddItemEdit(null, UINT, OPT, "id_contrato").setHide(true);
        AddItemEdit(null, UINT, OPT, "linea_contrato").setHide(true);

        JFormPanelResponsive row = AddItemRow();
        row.AddItemEdit("Descripcion", CHAR, REQ, "descripcion").setSizeColumns(6);
        row.AddItemEdit("Variacion", UFLOAT, REQ, "variacion").setSizeColumns(6);

        row = AddItemRow();
        row.AddItemDate("Fecha desde", DATE, REQ, "fecha_desde").setSizeColumns(6);
        row.AddItemDate("Fecha hasta", DATE, REQ, "fecha_hasta").setSizeColumns(6);
    }
}
