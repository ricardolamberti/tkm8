package  pss.common.event.action.history;

import pss.common.event.action.BizSqlEventAction;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.forms.JBaseForm;

public class FormSqlEventHistorySimple  extends JBaseForm {


  public FormSqlEventHistorySimple() throws Exception {
  }

  public GuiSqlEventHistory getWin() { return (GuiSqlEventHistory) getBaseWin(); }

  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( "company", CHAR, OPT, "company" );
    AddItemEdit( "fecha", DATETIME, REQ, "fecha" );
    AddItemEdit( "destinatario", CHAR, REQ, "destinatario" );
    AddItemCombo( "accion", CHAR, REQ, "accion" , JWins.createVirtualWinsFromMap(BizSqlEventAction.getActions()));
    AddItemEdit( "id_action", UINT, REQ, "id_action" );
    AddItemEdit( "id_history", UINT, REQ, "id_history" );
    AddItemEdit( "id_evento", UINT, REQ, "id_evento" );
//    JFormControl c=AddItem( mensaje, CHAR, REQ, "mensaje" );
//    c.setKeepHeight(true);
//    c.setKeepWidth(true);
    
  } 
}  
