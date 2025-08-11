package  pss.common.scheduler;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormProcess extends JBaseForm {

	public FormProcess() throws Exception {
  }


  public GuiProcess GetWin() throws Exception {
    return (GuiProcess) getBaseWin();
  }

  @Override
	public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( "descripcion", CHAR, REQ, "description" );
    AddItemEdit( "Comando", CHAR, REQ, "classname" );
    AddItemTabPanel().AddItemTab(10 );
  }





}  //  @jve:decl-index=0:visual-constraint="10,10"
