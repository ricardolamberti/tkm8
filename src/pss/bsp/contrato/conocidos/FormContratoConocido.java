package pss.bsp.contrato.conocidos;

import pss.bsp.contrato.detalle.BizDetalle;
import pss.bsp.contrato.detalle.nivel.BizNivel;
import pss.bsp.contrato.logica.JContratoNormal;
import pss.bsp.event.GuiBSPSqlEvents;
import pss.common.event.sql.GuiSqlEvents;
import pss.common.regions.company.GuiCompanies;
import pss.common.regions.divitions.GuiPaisesLista;
import pss.core.win.JControlWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.icons.GuiIconos;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;
import pss.tourism.carrier.GuiCarriers;

public class FormContratoConocido extends JBaseForm {

    private static final long serialVersionUID = 1477827540739L;

    public FormContratoConocido() throws Exception {}

    public GuiContratoConocido getWin() { return (GuiContratoConocido) getBaseWin(); }

    @Override
    public void InicializarPanel(JWin zWin) throws Exception {
        JFormPanelResponsive row = AddItemRow();
        row.AddItemEdit(null, UINT, OPT, "id").setHide(true);
        row.AddItemEdit("Descripcion", CHAR, REQ, "descripcion").setSizeColumns(12);

        row = AddItemRow();
        row.AddItemCombo("Compañia", CHAR, REQ, "company", new GuiCompanies()).setSizeColumns(6).setRefreshForm(true);
        row.AddItemCombo("País", CHAR, REQ, "pais", new GuiPaisesLista()).setSizeColumns(6);

        row = AddItemRow();
        row.AddItemCombo("Aerolineas", CHAR, REQ, "aerolineas", new JControlWin() {
            @Override
            public JWins getRecords(boolean bOneRow) throws Exception {
                return getAerolineas(bOneRow);
            }
        }).setSizeColumns(6);
        row.AddItemCombo("Período", CHAR, REQ, "periodo",
                JWins.createVirtualWinsFromMap(BizContratoConocido.getTipoPeriodos())).setSizeColumns(6);

        row = AddItemRow();
        row.AddItemCombo("Contrato", CHAR, REQ, "tipo_contrato", new JControlCombo() {
            @Override
            public JWins getRecords(boolean bOneRow) throws Exception {
                return getTiposContratos(bOneRow);
            }
        }).setSizeColumns(6).setRefreshForm(true).SetValorDefault(JContratoNormal.class.getName());
        row.AddItemWinLov("Icono", UINT, REQ, "icono", new JControlWin() {
            @Override
            public JWins getRecords() throws Exception {
                return new GuiIconos();
            }
        }).setSizeColumns(6);

        row = AddItemRow();
        row.AddItemWinLov("Indicador", UINT, OPT, "id_variable", new JControlWin() {
            @Override
            public JWins getRecords(boolean bOneRow) throws Exception {
                return getVariable(bOneRow);
            }
        }).setSizeColumns(4);
        row.AddItemWinLov("Indicador ganancia", UINT, OPT, "id_variableganancia", new JControlWin() {
            @Override
            public JWins getRecords(boolean bOneRow) throws Exception {
                return getVariableGanancia(bOneRow);
            }
        }).setSizeColumns(4);
        row.AddItemWinLov("Indicador auxiliar", UINT, OPT, "id_variableaux", new JControlWin() {
            @Override
            public JWins getRecords(boolean bOneRow) throws Exception {
                return getVariableAuxiliar(bOneRow);
            }
        }).setSizeColumns(4);

        row = AddItemRow();
        row.AddItemCombo("Tipo premio", CHAR, REQ, "tipo_premio",
                JWins.createVirtualWinsFromMap(BizNivel.getTiposPremios())).setSizeColumns(6);
        row.AddItemCombo("Tipo objetivo", CHAR, REQ, "tipo_objetivo",
                JWins.createVirtualWinsFromMap(BizNivel.getTiposObjetivos())).setSizeColumns(6);

        row = AddItemRow();
        row.AddItemCheck("Objetivo extra", OPT, "objetivo_extra").setSizeColumns(6);

        JFormPanelResponsive rowDet = AddItemRow();
        JFormControl c = rowDet.AddItemHtml("detalle", CHAR, OPT, "detalle");
        c.setKeepHeight(true);
        c.setKeepWidth(true);
    }

    JWins getTiposContratos(boolean one) throws Exception {
        return JWins.createVirtualWinsFromMap(BizDetalle.getLogicasContrato());
    }

    JWins getVariable(boolean one) throws Exception {
        GuiSqlEvents events = new GuiBSPSqlEvents();
        if (one)
            events.getRecords().addFilter("id", getWin().GetcDato().getIdvariable());
        else
            events.getRecords().addFilter("company", getWin().GetcDato().getCompania());
        return events;
    }

    JWins getVariableGanancia(boolean one) throws Exception {
        GuiSqlEvents events = new GuiBSPSqlEvents();
        if (one)
            events.getRecords().addFilter("id", getWin().GetcDato().getIdvariableganancia());
        else
            events.getRecords().addFilter("company", getWin().GetcDato().getCompania());
        return events;
    }

    JWins getVariableAuxiliar(boolean one) throws Exception {
        GuiSqlEvents events = new GuiBSPSqlEvents();
        if (one)
            events.getRecords().addFilter("id", getWin().GetcDato().getIdvariableAux());
        else
            events.getRecords().addFilter("company", getWin().GetcDato().getCompania());
        return events;
    }

    JWins getAerolineas(boolean one) throws Exception {
        GuiCarriers events = new GuiCarriers();
        if (one)
            events.getRecords().addFilter("carrier", getWin().GetcDato().getAerolineas());
        return events;
    }
}
