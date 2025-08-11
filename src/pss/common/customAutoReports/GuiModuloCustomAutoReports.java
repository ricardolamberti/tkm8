package pss.common.customAutoReports;

import pss.common.customAutoReports.config.GuiReportLists;
import pss.common.security.BizUsuario;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.modules.GuiModulo;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActWins;


public class GuiModuloCustomAutoReports extends GuiModulo {

  public GuiModuloCustomAutoReports() throws Exception {
    super();
    SetModuleName( "Reportes Automáticos" );
    SetNroIcono  ( 5 );
  }

  @Override
	public BizAction createDynamicAction() throws Exception {
    return this.addAction( 1, "Reportes Automáticos", null , 5 , true, false, true, "Group");
  }

  @Override
	public void createActionMap() throws Exception {
    BizAction a =this.addAction( 25, "Reportes" , null , 5 , true, false, true, "Group");
    a.setBackAction(false);
    a.setRefreshAction(false);
  }
  
	@Override
	public JAct getSubmitFor(BizAction a) throws Exception {
		if (a.getId()==25) return new JActWins(this.getListas());
		return super.getSubmitFor(a);
	}
	
  public JWins getListas() throws Exception {
  	GuiReportLists wins = new GuiReportLists();
  	wins.getRecords().addFilter("company", BizUsuario.getUsr().getCompany());
  	return wins;
  }

}

