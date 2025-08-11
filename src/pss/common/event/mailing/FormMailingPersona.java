package  pss.common.event.mailing;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormMailingPersona extends JBaseForm {

  public FormMailingPersona() throws Exception {
  }

  public GuiMailingPersona getWin() { return (GuiMailingPersona) getBaseWin(); }

  public void InicializarPanel( JWin zWin ) throws Exception {
  		AddItemEdit( "Id", CHAR, OPT, "id" );
  		AddItemEdit( "Tipo", CHAR, OPT, "tipo" );
  		AddItemEdit( "codigo", CHAR, REQ, "codigo" );
    AddItemEdit( "descrpcion", CHAR, REQ, "descripcion" );
    AddItemEdit( "mail", CHAR, OPT, "mail" );
    AddItemEdit( "numero", CHAR, OPT, "numero" );

  }

}
