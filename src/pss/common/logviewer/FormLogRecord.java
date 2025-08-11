package  pss.common.logviewer;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormLogRecord extends JBaseForm {

	public FormLogRecord() throws Exception {
  }

  public GuiLog GetWin() { return (GuiLog) getBaseWin(); }

  @Override
	public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemDateTime( "date", DATETIME, OPT, "timestamp" );
    AddItemEdit( "type", CHAR, OPT, "type" );
    AddItemEdit( "thread", CHAR, OPT, "thread" );
    AddItemArea( "Mensaje", CHAR, OPT, "message" );
  }

}


