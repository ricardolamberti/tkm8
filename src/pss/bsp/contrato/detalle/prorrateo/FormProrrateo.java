package pss.bsp.contrato.detalle.prorrateo;

import pss.bsp.dk.GuiClienteDKs;
import pss.core.win.JControlWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;

public class FormProrrateo extends JBaseForm {


private static final long serialVersionUID = 1446860278358L;

  /**
   * Propiedades de la Clase
   */


/**
   * Constructor de la Clase
   */
  public FormProrrateo() throws Exception {
  }

  public GuiProrrateo getWin() { return (GuiProrrateo) getBaseWin(); }

  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
        AddItemEdit( null, CHAR, OPT, "id_prorrateo" ).setHide(true);
        AddItemEdit( null, CHAR, OPT, "company" ).setHide(true);
        AddItemEdit( null, CHAR, OPT, "contrato" ).setHide(true);
        AddItemEdit( null, CHAR, OPT, "detalle" ).setHide(true);

        JFormPanelResponsive row = AddItemRow();
        row.AddItemWinLov( "Cliente", CHAR, REQ, "cliente", new JControlWin() {
                @Override
                public JWins getRecords(boolean bOneRow) throws Exception {
                        return getClientes(bOneRow);
                }
        }).setSizeColumns(8);
        row.AddItemEdit( "Comision", FLOAT, REQ, "valor" ).setSizeColumns(4);
  }

  public JWins getClientes(boolean one) throws Exception {
        GuiClienteDKs wins = new GuiClienteDKs();
        wins.getRecords().addFilter("company", getWin().GetcDato().getCompany());
        wins.getRecords().addOrderBy("dk");
        return wins;
  }

}  //  @jve:decl-index=0:visual-constraint="7,4"
