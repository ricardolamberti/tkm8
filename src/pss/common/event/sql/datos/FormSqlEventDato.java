package  pss.common.event.sql.datos;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormSqlEventDato extends JBaseForm {

  public FormSqlEventDato() throws Exception {
  }

  public GuiSqlEventDato getWin() { return (GuiSqlEventDato) getBaseWin(); }


  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( "id", UINT, REQ, "id" );
    AddItemEdit( "value", UFLOAT, REQ, "value" );
    AddItemDateTime( "fecha", DATE, REQ, "fecha" );
    AddItemEdit( "id_evento", UINT, REQ, "id_evento" );

  } 
} 
