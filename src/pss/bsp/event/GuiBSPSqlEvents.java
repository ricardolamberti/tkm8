package pss.bsp.event;

import pss.common.event.sql.GuiSqlEvents;
import pss.common.security.BizUsuario;
import pss.core.services.records.JRecords;
import pss.core.tools.PssLogger;
import pss.core.tools.collections.JIterator;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActSubmit;

public class GuiBSPSqlEvents extends GuiSqlEvents{

	public GuiBSPSqlEvents() throws Exception {
		super();
	}
  public Class<? extends JWin>  GetClassWin()                   { return GuiBSPSqlEvent.class; }

  public void createActionMap() throws Exception {
   	this.addAction(20, "Eliminar inusados", null, 15018, true, true, true, "Group" );
   	super.createActionMap();
	}
  
  @Override
  public boolean OkAction(BizAction a) throws Exception {
  	if (a.getId()==20) return BizUsuario.getUsr().isAdminUser();
  	return super.OkAction(a);
  }

	@Override
	public JAct getSubmitFor(BizAction a) throws Exception {
  	if (a.getId()==20 ) return new JActSubmit(this.getWinRef()) {
  		@Override
  		public void submit() throws Exception {
  			execProcessDeleteInused();
  			
  		}
  	};
		return null;
	}
	public void execProcessDeleteInused() throws Exception {
		JRecords<BizBSPSqlEvent> reg = new JRecords<BizBSPSqlEvent>(BizBSPSqlEvent.class);
		JIterator<BizBSPSqlEvent> it = reg.getStaticIterator();
		int id=0;
		while (it.hasMoreElements()) {
			BizBSPSqlEvent evt = it.nextElement();
			try {
				evt.execProcessDelete();
				id++;
				PssLogger.logInfo("Eliminado "+evt.getDescripcion());
			} catch (Exception e) {
				evt.execProcessInactivar();
				
				
				PssLogger.logInfo("NO eliminado "+id);
				e.printStackTrace();
			}
		}
	}

}
