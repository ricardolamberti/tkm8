package  pss.common.scheduler;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormProcessHost extends JBaseForm {

  public FormProcessHost() throws Exception {
  }


  //-------------------------------------------------------------------------//
  // Linkeo los campos con la variables del form
  //-------------------------------------------------------------------------//
  @Override
	public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( "PROCESO" , CHAR, REQ, "pid"           );
    AddItemEdit( "host"    , CHAR, REQ, "host"          );
    AddItemEdit( "PARAMETERS" , CHAR, OPT, "params"    );
  }


}  //  @jve:decl-index=0:visual-constraint="10,10"
