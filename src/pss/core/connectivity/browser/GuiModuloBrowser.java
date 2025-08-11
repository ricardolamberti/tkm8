package pss.core.connectivity.browser;

import pss.JPath;
import pss.core.tools.JOSCommand;
import pss.core.win.actions.BizAction;
import pss.core.win.modules.GuiModulo;
import pss.core.win.submits.JAct;

public class GuiModuloBrowser extends GuiModulo {

  public GuiModuloBrowser() throws Exception {
    super();
    SetModuleName( "Browser" );
    SetNroIcono  ( 910 );
  }

  @Override
	public BizAction createDynamicAction() throws Exception {
    return addAction(1, "Browser", new JAct() { 
    	@Override
			public void Do() throws Exception { ejecutarBrowser(); }} , 910 , true, false);
  }


  @Override
	public void createActionMap() throws Exception {
  }

  private void ejecutarBrowser() throws Exception {
    JOSCommand command = new JOSCommand(JPath.PssPathBin() + "/PssBrowser.exe");
    command.executeWaitingForEver();
  }

}



