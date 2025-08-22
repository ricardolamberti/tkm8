package  pss.bsp.consola.dashBoard;

import pss.common.customList.config.customlist.GuiCustomListResult;
import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActQuery;
import pss.core.winUI.forms.JBaseForm;

public class GuiDashBoard extends JWin {


  /**
   * Constructor de la Clase
   */
  public GuiDashBoard() throws Exception {
  }


  public JRecord ObtenerDato()   throws Exception { return new BizDashBoard(); }
  public int GetNroIcono()   throws Exception { return 10057; }
  public String GetTitle()   throws Exception { return "Dash Board"; }
  public Class<? extends JBaseForm> getFormBase() throws Exception {
  	return FormDashBoard.class;
  }
  public BizDashBoard GetcDato() throws Exception { return (BizDashBoard) this.getRecord(); }

  public void createActionMap() throws Exception {
		for (int i=100;i<200;i++) {
		BizAction a = addAction(i, "Datos", null, 10059, false, false);
		a.setFilterAction(true);
		a.setRefreshAction(false);
		}
		for (int i=200;i<300;i++) {
			BizAction a = addAction(i, "View Datos", null, 10059, false, false);
			a.setFilterAction(true);
			a.setRefreshAction(false);
		}
  }

//  public boolean forceCleanHistory() throws Exception {
//		return true;
//	}
  
  @Override
  public boolean OkAction(BizAction a) throws Exception {
  	return super.OkAction(a);
  }
  
	
//  public void addAcciones() throws Exception {
//  	for (int i=100;i<200;i++) {
//  		BizAction a = addAction(i, "Datos", null, 10059, false, false);
//  		a.setFilterAction(true);
//  		a.setRefreshAction(false);
//  	}
//  	for (int i=200;i<300;i++) {
//  		BizAction a = addAction(i, "View Datos", null, 10059, false, false);
//  		a.setFilterAction(true);
//  		a.setRefreshAction(false);
//  	}
//  	
//  }
	
   
	@Override
	public JAct getSubmitFor(BizAction a) throws Exception {

//		for (int i=100;i<200;i++) {
//			if (a.getId()==i) return new JActWins(this.getDynamicWins(i-99));
//  	}
		for (int i=200;i<300;i++) {
			if (a.getId()==i) return new JActQuery(this.getCustomList(a,i-199));
  	}
		return null;
	}


//	public JWins getDynamicWins(int inf) throws Exception {
//		GuiDynamics d = new GuiDynamics();
//		GuiCustomList clist = new GuiCustomList();
//		clist.setRecord(GetcDato().getFavorito(inf));
//		d.setCustomList(clist);
//		return d;
//	}
	public JWin getCustomList(BizAction a,int inf) throws Exception {
		GuiCustomListResult clist = new GuiCustomListResult();
		clist.setRecord(GetcDato().getFavorito(inf));
		return clist.getResult(a,true,true);
	}
 
	
	
}
