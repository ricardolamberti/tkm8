package pss.bsp.contrato.rutas.ms;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormMS extends JBaseForm {

    private static final long serialVersionUID = 1446860154249L;

    public FormMS() throws Exception {}

    public GuiMS getWin() { return (GuiMS) getBaseWin(); }

    @Override
    public void InicializarPanel(JWin zWin) throws Exception {
        AddItemEdit(null, CHAR, OPT, "company").setHide(true);
        AddItemEdit(null, CHAR, OPT, "id").setHide(true);
        AddItemEdit(null, CHAR, OPT, "linea").setHide(true);

        AddItemImage("", "image1");
    }
}
