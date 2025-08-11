package pss.bsp.contrato.detalle.prorrateo.tickets;

import pss.core.win.JControlWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;
import pss.tourism.pnr.GuiPNRTickets;

public class FormTicketProrrateo extends JBaseForm {

private static final long serialVersionUID = 1446860154249L;

  public FormTicketProrrateo() throws Exception {}

  public GuiTicketProrrateo getWin() { return (GuiTicketProrrateo) getBaseWin(); }

  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
        AddItemEdit( null, CHAR, OPT, "company" ).setHide(true);
        AddItemEdit( null, CHAR, OPT, "id" ).setHide(true);
        AddItemEdit( null, CHAR, OPT, "linea" ).setHide(true);
        AddItemEdit( null, CHAR, OPT, "id_contrato" ).setHide(true);

        JFormPanelResponsive row = AddItemRow();
        row.AddItemWinLov( "Ticket", CHAR, REQ, "ticket", new JControlWin() {
                @Override
                public JWins getRecords(boolean bOneRow) throws Exception {
                        return getTicket(bOneRow);
                }
        }).setSizeColumns(6);
        row.AddItemEdit( "Cliente", CHAR, OPT, "customer" ).setSizeColumns(6);

        row = AddItemRow();
        row.AddItemEdit( "Porcentaje", FLOAT, OPT, "porcentaje" ).setSizeColumns(4);
        row.AddItemEdit( "Comisión", FLOAT, OPT, "comision" ).setSizeColumns(4);
        row.AddItemDateTime( "Fecha", DATE, OPT, "fecha" ).setSizeColumns(4);
  }

  public JWins getTicket(boolean one) throws Exception {
        GuiPNRTickets wins = new GuiPNRTickets();
        if (one) {
                wins.getRecords().addFilter("id", getWin().GetcDato().getObjTicket().getId());
        }
        return wins;
  }
}

