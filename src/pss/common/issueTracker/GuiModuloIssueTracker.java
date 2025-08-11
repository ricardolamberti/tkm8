package pss.common.issueTracker;

import pss.common.issueTracker.issue.GuiIssues;
import pss.common.issueTracker.issueHandlerUsers.BizIssueHandlerUser;
import pss.common.issueTracker.issueHandlerUsers.GuiIssueHandlerUsers;
import pss.common.issueTracker.reportes.GuiIssueSummaries;
import pss.common.issueTracker.setting.GuiIssueTrackerSettings;
import pss.common.security.BizUsuario;
import pss.core.win.JBaseWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.modules.GuiModulo;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActWins;

public class GuiModuloIssueTracker extends GuiModulo {

  public GuiModuloIssueTracker() throws Exception {
    super();
    SetModuleName( "Incidencias" );
    SetNroIcono  ( 713 );
  }

  @Override
	public BizAction createDynamicAction() throws Exception {
    return addAction( 1, "Incidencias", new JAct() {@Override
		public JBaseWin GetBWin() throws Exception {return GetThis();}} , 713 , true, true, true, "Group");
  }

  @Override
	public void createActionMap() throws Exception {
  	this.addAction( 5, "Configuración" , null , 1 , true, true, true, "Group");
  	//this.addAction( 10, "Agregar Issue" , null , 1 , true, true, true, "Group");
  	this.addAction( 20, "Incidencias" , null , 1 , true, true, true, "Group");
  	this.addAction( 30, "Usuarios" , null , 1 , true, true, true, "Group");
  	this.addAction(40, "Estadísticas", null, 10012, true, false).setFilterAction(true);
  }

	public JAct getSubmitFor(BizAction a) throws Exception {
		if ( a.getId()==5 ) return new JActWins(getIssueTrackerSetting());
		//if ( a.getId()==10 ) return new JActNew(getNewMantisIssue(),0);
		if ( a.getId()==20 ) return new JActWins(getIssues());
		if ( a.getId()==30 ) return new JActWins(getUsuarios());
		if (a.getId()==40) return new JActWins(this.getEstadisticas());
  	return null;
  }
  

  @Override
	public boolean isModuleComponent() { return false; }

  public JWins getIssueTrackerSetting() throws Exception {
  	GuiIssueTrackerSettings g = new GuiIssueTrackerSettings();
    return g;
  }
  
  
  public JWins getIssues() throws Exception {
  	GuiIssues g = new GuiIssues();
  	if (!BizIssueHandlerUser.isLoginedUserAHandlerUser()) {
  		String sCompany = BizUsuario.getUsr().getCompany();
    	g.getRecords().addFilter("company", sCompany);
  	}
    g.setPreviewFlag(JWins.PREVIEW_NO);
    return g;
  }

  public JWins getUsuarios() throws Exception {
  	GuiIssueHandlerUsers g = new GuiIssueHandlerUsers();
    g.setPreviewFlag(JWins.PREVIEW_NO);
    return g;
  }
  
	private GuiIssueSummaries getEstadisticas() throws Exception {
		GuiIssueSummaries wins=new GuiIssueSummaries();
		wins.setPreviewFlag(JWins.PREVIEW_NO);
		wins.setRefreshOnlyOnUserRequest(true);
		return wins;
	}


  
}




