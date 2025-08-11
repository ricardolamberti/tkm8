package pss.tourism.pnr;

import pss.core.win.JControlWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;
import pss.tourism.airports.GuiAirports;

public class FormPNRFare extends JBaseForm {

    private static final long serialVersionUID = 1446642143959L;

    /**
     * Constructor de la Clase
     */
    public FormPNRFare() throws Exception {
    }

    public GuiPNRFare getWin() {
        return (GuiPNRFare) getBaseWin();
    }

    /**
     * Linkeo los campos con la variables del form
     */
    @Override
    public void InicializarPanel(JWin zWin) throws Exception {
        AddItemEdit(null, CHAR, REQ, "company").setHide(true);
        AddItemEdit(null, UINT, REQ, "interface_id").setHide(true);

        JFormPanelResponsive row = AddItemRow();
        row.AddItemEdit("Impuesto", FLOAT, REQ, "impuesto").setSizeColumns(4);
        row.AddItemEdit("Importe", FLOAT, REQ, "importe").setSizeColumns(4);
        row.AddItemEdit("Secuencia", UINT, REQ, "secuencia").setSizeColumns(4);

        row = AddItemRow();
        row.AddItemEdit("Fare", CHAR, REQ, "fare").setSizeColumns(4);
        row.AddItemEdit("Codigo PNR", CHAR, REQ, "codigopnr").setSizeColumns(4);
        row.AddItemEdit("Ruta", CHAR, OPT, "ruta").setSizeColumns(4);

        row = AddItemRow();
        row.AddItemWinLov("Despegue", CHAR, REQ, "airport_from", new JControlWin() {
            @Override
            public JWins getRecords(boolean bOneRow) throws Exception {
                return getDespegue(bOneRow);
            }
        }).setSizeColumns(6);
        row.AddItemWinLov("Arrivo", CHAR, REQ, "airport_to", new JControlWin() {
            @Override
            public JWins getRecords(boolean bOneRow) throws Exception {
                return getArrivo(bOneRow);
            }
        }).setSizeColumns(6);

        AddItemTabPanel().AddItemList(120);
    }

    public JWins getDespegue(boolean one) throws Exception {
        GuiAirports wins = new GuiAirports();
        if (one) {
            wins.getRecords().addFilter("code", getWin().GetcDato().getAirportFrom());
        }
        return wins;
    }

    public JWins getArrivo(boolean one) throws Exception {
        GuiAirports wins = new GuiAirports();
        if (one) {
            wins.getRecords().addFilter("code", getWin().GetcDato().getAirportTo());
        }
        return wins;
    }
}

