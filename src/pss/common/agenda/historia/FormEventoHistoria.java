package  pss.common.agenda.historia;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormEventoHistoria extends JBaseForm {


private static final long serialVersionUID = 1352314950136L;

  public FormEventoHistoria() throws Exception {
  }

  public GuiEventoHistoria getWin() { return (GuiEventoHistoria) getBaseWin(); }

  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( "usuario", CHAR, REQ, "usuario" );
    AddItemEdit( "estado", CHAR, REQ, "estado" );
    AddItemEdit( "texto", CHAR, REQ, "texto" );
    AddItemEdit( "id", UINT, REQ, "id" );
    AddItemEdit( "id_evento", UINT, REQ, "id_evento" );
    AddItemEdit( "company", CHAR, REQ, "company" );

  } 
} 
