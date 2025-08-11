package  pss.common.help;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormQuestionDetail extends JBaseForm {

  public FormQuestionDetail() throws Exception {
  }

  public GuiQuestionDetail GetWin() { return (GuiQuestionDetail) getBaseWin(); }

  @Override
	public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( "idQuestion", UINT, REQ, "idQuestion" );
    AddItemEdit( "secuencia", UINT, OPT, "secuencia" );
    AddItemEdit( "page", CHAR, REQ, "page" );
    AddItemEdit( "action", CHAR, REQ, "action" );
    AddItemEdit( "status", CHAR, OPT, "status" );
    AddItemEdit( "step", UINT, REQ, "step" );
    AddItemEdit( "x", UINT, OPT, "x" );
    AddItemEdit( "y", UINT, OPT, "y" );
    AddItemEdit( "z", UINT, OPT, "z" );
    AddItemEdit( "width", UINT, OPT, "width" );
    AddItemEdit( "height", UINT, OPT, "height" );
    AddItemEdit( "typePos", CHAR, OPT, "typePos" );
    AddItemEdit( "id", CHAR, OPT, "id" );
    AddItemEdit( "type", CHAR, OPT, "type" );
    AddItemArea( "Help", CHAR, "REQ", "Help" );

  }

}
