package  pss.common.event.action.history;

import pss.common.event.action.BizSqlEventAction;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.forms.JBaseForm;

public class FormSqlEventHistory extends JBaseForm {


  public FormSqlEventHistory() throws Exception {
  }

  public GuiSqlEventHistory getWin() { return (GuiSqlEventHistory) getBaseWin(); }

  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( "company", CHAR, OPT, "company" );
    AddItemEdit( "mensajeId", CHAR, OPT, "mensaje" );
    AddItemEdit( "fecha", DATETIME, REQ, "fecha" );
    AddItemEdit( "destinatario", CHAR, REQ, "destinatario" );
    AddItemCombo( "accion", CHAR, REQ, "accion" , JWins.createVirtualWinsFromMap(BizSqlEventAction.getActions()));
    AddItemEdit( "id_action", UINT, REQ, "id_action" );
    AddItemEdit( "id_history", UINT, REQ, "id_history" );
    AddItemEdit( "id_evento", UINT, REQ, "id_evento" );
    JFormControl c=AddItemEdit( "mensaje", CHAR, REQ, "mensaje_contenido" );
    c.setKeepHeight(true);
    c.setKeepWidth(true);
    
  } 
}  
