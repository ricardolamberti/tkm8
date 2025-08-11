package pss.common.customDashboard.component;

import pss.common.customDashboard.DashBoardInfo;
import pss.common.customDashboard.GuiDashBoard;
import pss.common.customDashboard.config.BizDashBoardConfig;
import pss.common.customDashboard.config.GuiDashBoardConfig;
import pss.core.services.records.JRecord;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActQuery;
import pss.core.winUI.forms.JBaseForm;

public class GuiDashComponent extends JWin {
	
	@Override
	public Class<? extends JBaseForm> getFormBase() throws Exception {
		return FormDashComponent.class;
	}
  public JRecord ObtenerDato()   throws Exception { return new BizDashComponent(); }
  public String GetTitle()   throws Exception { return this.GetcDato().getConfig().findInfo().getDescrip(); }
  public BizDashComponent GetcDato() throws Exception { return (BizDashComponent) this.getRecord(); }

  @Override
  public int GetNroIcono() throws Exception {
  	return this.GetcDato().getConfig().findInfo().getIcon();
  }

	public GuiDashComponent() {
	}
	
	@Override
	public void createActionMap() throws Exception {
		this.addAction(10, "Card", null, 1, true, true);
		this.addAction(20, "Detalle", null, 1, true, true);
	}

	@Override
	public JAct getSubmitFor(BizAction a) throws Exception {
		if (a.getId()==10) return this.getDashAction();
		if (a.getId()==20) return this.getDetailAction();
		return null;
	}
	
  private JAct getDashAction() throws Exception {
  	GuiDashBoardConfig cf = new GuiDashBoardConfig();
  	cf.setRecord(this.GetcDato().getConfig());
  	return new JActQuery(cf);
  }
  
  private JAct getDetailAction() throws Exception {
  	BizDashBoardConfig cf = this.GetcDato().getConfig();
  	DashBoardInfo info = cf.findInfo();
  	GuiDashBoard dash = info.getObjModule().getWinDashboard();
  	dash.GetcDato().setObjConfig(cf);
  	JAct act = dash.findAction(info.getAction()).getSubmit();
  	if (act.getResult() instanceof JWins) {
  		act.getWinsResult().setShowFilters(false);
  		act.getWinsResult().setPreviewFlag(JWins.PREVIEW_NO);
  	}
  	return act;
  }

}
