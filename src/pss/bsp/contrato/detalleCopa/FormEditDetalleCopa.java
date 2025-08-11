package pss.bsp.contrato.detalleCopa;

import pss.bsp.contrato.detalle.nivel.GuiNiveles;
import pss.common.security.BizUsuario;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.responsiveControls.JFormCheckResponsive;
import pss.core.winUI.responsiveControls.JFormComboResponsive;
import pss.core.winUI.responsiveControls.JFormTableResponsive;
import pss.core.winUI.forms.JBaseForm;
import pss.tourism.carrier.GuiCarriers;

public class FormEditDetalleCopa extends JBaseForm {

    private static final long serialVersionUID = 1446860278358L;

    public FormEditDetalleCopa() throws Exception {}

    public GuiDetalleCopa getWin() { return (GuiDetalleCopa) getBaseWin(); }

    @Override
    public void InicializarPanel(JWin zWin) throws Exception {
        AddItemEdit(null, CHAR, REQ, "company").SetValorDefault(BizUsuario.getUsr().getCompany()).setHide(true);
        AddItemEdit(null, UINT, OPT, "id").setHide(true);
        AddItemEdit(null, UINT, OPT, "linea").setHide(true);

        JFormComboResponsive c = AddItemCombo("Aerolinea", CHAR, REQ, "id_aerolinea", new JControlCombo() {
            @Override
            public JWins getRecords(boolean one) throws Exception {
                return getAerolineas(one);
            }
        });
        c.setSizeColumns(6);
        c.setPlaceHolder("Aerolinea del contrato");

        JFormCheckResponsive ch = AddItemCheck("Objetivo extra", OPT, "kicker");
        ch.setSizeColumns(6);

        AddItemEdit("FMS max", UFLOAT, OPT, "fms_max").setSizeColumns(4);
        AddItemEdit("FMS min", UFLOAT, OPT, "fms_min").setSizeColumns(4);
        AddItemEdit("Limite min", UFLOAT, OPT, "limite").setSizeColumns(4);

        AddItemEdit("Descripcion", CHAR, OPT, "descripcion");

        AddItemEdit("Tour code excluidos", CHAR, OPT, "tourcode_excluded").setSizeColumns(4);
        AddItemEdit("Clases excluidas", CHAR, OPT, "class_excluded").setSizeColumns(4);
        AddItemEdit("Mercados", CHAR, OPT, "mercados")
            .setPlaceHolder("Los mercados se dividen con coma, Ej: EZEMIA,@ARNYC,@AR@US")
            .setSizeColumns(4);

        AddItemEdit("Evaluación al fin del contrato", CHAR, OPT, "conclusion").SetReadOnly(true);

        JFormTableResponsive t = AddItemTable("Niveles", "niveles", GuiNiveles.class);
        t.setKeepHeight(true);
        t.setKeepWidth(true);
    }

    private JWins getAerolineas(boolean one) throws Exception {
        GuiCarriers g = new GuiCarriers();
        if (one) {
            g.getRecords().addFilter("carrier", getWin().GetcDato().getIdAerolinea());
        }
        g.addOrderAdHoc("description", "ASC");
        return g;
    }

    @Override
    public void OnPostShow() throws Exception {
        checkControls("");
        super.OnPostShow();
    }

    @Override
    public void checkControls(String sControlId) throws Exception {
        super.checkControls(sControlId);
    }
}
