package  pss.common.scheduler;

import pss.core.win.actions.BizAction;
import pss.core.win.modules.GuiModulo;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActQuery;
import pss.core.win.submits.JActWins;

public class GuiModuloScheduler extends GuiModulo {

  public GuiModuloScheduler() throws Exception {
    super();
    SetModuleName( "Scheduler" );
    SetNroIcono  ( 5007 );
  }

  @Override
	public BizAction createDynamicAction() throws Exception {
    return addAction( 1, "Adm.Procesos" , null, 5007 , true, true, true, "Group" );
  }

  @Override
	public void createActionMap() throws Exception {
    this.addAction(3,  "Procesos"  , null, 5007 , true, false, true, "Group" );
  }
  
  @Override
	public JAct getSubmitFor(BizAction a) throws Exception {
  	if ( a.getId()==1 ) return new JActQuery(this);
  	if ( a.getId()==3 ) return new JActWins(new GuiProcesses());
  	return null;
  }


}



