package  pss.common.logviewer;

import pss.core.win.actions.BizAction;
import pss.core.win.modules.GuiModulo;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActWins;

public class GuiModuloLogViewer extends GuiModulo {

  public GuiModuloLogViewer() throws Exception {
    super();
    SetModuleName( "Visualización de Logs" );
    SetNroIcono  ( 1083 );
  }

  @Override
	public BizAction createDynamicAction() throws Exception {
   return addAction( 1, "Logs", null,1012,true, false, true, "Group" );
  }

  @Override
	public void createActionMap() throws Exception {
    BizAction a =this.addAction( 200, "View" , null , 716 , true, false, true, "Group");
 }

  @Override
	public JAct getSubmitFor(BizAction a) throws Exception {
  	if ( a.getId()==200) return new JActWins(new GuiLogs());
  	return null;
  }

}



