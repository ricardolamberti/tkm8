package pss.tourism.pnr;

import pss.core.win.JControlWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;
import pss.tourism.airports.GuiAirports;
import pss.tourism.carrier.GuiCarriers;

public class FormPNRSegmentoAereo extends JBaseForm {

    private static final long serialVersionUID = 1446641998059L;

    /**
     * Constructor de la Clase
     */
    public FormPNRSegmentoAereo() throws Exception {
    }

    public GuiPNRSegmentoAereo getWin() {
        return (GuiPNRSegmentoAereo) getBaseWin();
    }

    /**
     * Linkeo los campos con la variables del form
     */
    @Override
    public void InicializarPanel(JWin zWin) throws Exception {
        AddItemEdit(null, CHAR, REQ, "company").setHide(true);
        AddItemEdit(null, UINT, REQ, "interface_id").setHide(true);

        JFormPanelResponsive row = AddItemRow();
        row.AddItemEdit("Codigo segmento", CHAR, REQ, "codigosegmento").setSizeColumns(4);
        row.AddItemEdit("Estado", CHAR, REQ, "estado").setSizeColumns(4);
        row.AddItemEdit("PNR", CHAR, REQ, "codigopnr").setSizeColumns(4);

        row = AddItemRow();
        row.AddItemEdit("Clase", CHAR, REQ, "clase").setSizeColumns(4);
        row.AddItemEdit("Tipo equipo", CHAR, REQ, "tipoequipo").setSizeColumns(4);
        row.AddItemEdit("Numero vuelo", CHAR, REQ, "numerovuelo").setSizeColumns(4);

        row = AddItemRow();
        row.AddItemEdit("Segmento ini", CHAR, REQ, "segmento_ini").setSizeColumns(4);
        row.AddItemCheck("Emitido", OPT, "emitido").setSizeColumns(4);

        row = AddItemRow();
        row.AddItemWinLov("Despegue", CHAR, REQ, "despegue", new JControlWin() {
            @Override
            public JWins getRecords(boolean bOneRow) throws Exception {
                return getDespegue(bOneRow);
            }
        }).setSizeColumns(6);
        row.AddItemWinLov("Arrivo", CHAR, REQ, "arrivo", new JControlWin() {
            @Override
            public JWins getRecords(boolean bOneRow) throws Exception {
                return getArrivo(bOneRow);
            }
        }).setSizeColumns(6);

        row = AddItemRow();
        row.AddItemEdit("Fecha despegue", CHAR, REQ, "fechadespegue").setSizeColumns(4);
        row.AddItemEdit("Hora despegue", CHAR, REQ, "horadespegue").setSizeColumns(4);
        row.AddItemEdit("Fecha arrivo", CHAR, REQ, "fechaarrivo").setSizeColumns(4);

        row = AddItemRow();
        row.AddItemEdit("Hora arrivo", CHAR, REQ, "horaarrivo").setSizeColumns(4);
        row.AddItemEdit("Duracion", CHAR, REQ, "duracionvuelo").setSizeColumns(4);
        row.AddItemEdit("Codigo comida", CHAR, REQ, "codigocomida").setSizeColumns(4);

        row = AddItemRow();
        row.AddItemWinLov("Carrier", CHAR, REQ, "carrier", new JControlWin() {
            @Override
            public JWins getRecords(boolean bOneRow) throws Exception {
                return getAerolinea(bOneRow);
            }
        }).setSizeColumns(6);
        row.AddItemEdit("Codigo entretenimiento", CHAR, REQ, "codigoentretenimiento").setSizeColumns(6);

        AddItemTabPanel().AddItemList(120);
    }

    public JWins getDespegue(boolean one) throws Exception {
        GuiAirports wins = new GuiAirports();
        if (one) {
            wins.getRecords().addFilter("code", getWin().GetcDato().getDespegue());
        }
        return wins;
    }

    public JWins getArrivo(boolean one) throws Exception {
        GuiAirports wins = new GuiAirports();
        if (one) {
            wins.getRecords().addFilter("code", getWin().GetcDato().getArrivo());
        }
        return wins;
    }

    public JWins getAerolinea(boolean one) throws Exception {
        GuiCarriers wins = new GuiCarriers();
        if (one) {
            wins.getRecords().addFilter("carrier", getWin().GetcDato().getCarrier());
        }
        return wins;
    }
}

