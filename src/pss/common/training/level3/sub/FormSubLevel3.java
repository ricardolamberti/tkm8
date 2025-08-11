package pss.common.training.level3.sub;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormSubLevel3 extends JBaseForm {


private static final long serialVersionUID = 1218486819468L;


/**
   * Constructor de la Clase
   */
  public FormSubLevel3() throws Exception {
  }

  public GuiSubLevel3 GetWin() { return (GuiSubLevel3) getBaseWin(); }

  /**
   * Inicializacion Grafica
   */

  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( "id", LONG, OPT, "id" ).setVisible(false);
    AddItemEdit( "sub id", LONG, OPT, "subid" ).setVisible(false);
    AddItemEdit( "descripcion", CHAR, REQ, "descripcion" );

  }

}  //  @jve:decl-index=0:visual-constraint="-382,-30"
