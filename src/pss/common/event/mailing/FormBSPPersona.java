package pss.common.event.mailing;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormBSPPersona extends JBaseForm {

  public FormBSPPersona() throws Exception {
  }

  public GuiBSPPersona getWin() { return (GuiBSPPersona) getBaseWin(); }

  public void InicializarPanel( JWin zWin ) throws Exception {
		AddItemEdit( "Id", CHAR, OPT, "id" );
		AddItemEdit( "Tipo", CHAR, OPT, "tipo" );
		AddItemEdit( "codigo", CHAR, REQ, "codigo" );
    AddItemEdit( "descripcion", CHAR, REQ, "descripcion" );
    AddItemEdit( "mail", CHAR, OPT, "mail" );

  }

} 
