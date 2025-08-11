package pss.common.customDashboard.component;

import pss.common.customDashboard.DashBoardInfo;
import pss.common.customDashboard.GuiDashBoard;
import pss.common.customDashboard.config.BizDashBoardConfig;
import pss.core.ui.components.JPssImage;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormImageCardResponsive;
import pss.core.winUI.responsiveControls.JFormInfoCardResponsive;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;
import pss.www.ui.JWebIcon;

public class FormDashComponent extends JBaseForm {

	public FormDashComponent() {
	}
	
	public GuiDashComponent getWin() {
		return (GuiDashComponent) this.getBaseWin();
	}
	
	public BizDashComponent getComp() throws Exception {
		return this.getWin().GetcDato();
	}

	@Override
	public void InicializarPanel(JWin zBase) throws Exception {
		JFormPanelResponsive col = this.AddItemColumn();

		BizDashBoardConfig cf = this.getComp().getConfig();
		DashBoardInfo info = cf.findInfo();

		GuiDashBoard db = info.getObjModule().getWinDashboard();
		db.GetcDato().setObjConfig(this.getComp().getConfig());
//		String action = "DETAIL-"+cfg.getId()+"-"+dbd.getAction();
		String field = info.getField();
		this.getWin().getRecord().addItem(field, db.getRecord().getProp(info.getField()));
		this.getWin().getRecord().addFixedItem(db.getRecord().getFixedProp(info.getField()));

		if (info.isImage()) {
			JFormImageCardResponsive img = col.AddImageCard( info.getDescrip(), field, info.getActionDescrip(), 20);
//			img.setSizeColumns(cf.getDashSize());
			img.setSource(JPssImage.SOURCE_PSSDATA);
			img.SetReadOnly(true);
			img.setResponsiveClass(info.getLevel());
			img.setIdControl(field+cf.getDashOrder());
		} else {
			JFormInfoCardResponsive card = col.AddInfoCard(info.getDescrip(), JBaseForm.CHAR, field, JWebIcon.findIcon(info.getIcon()), info.getActionDescrip(), 20);
			card.setResponsiveClass(info.getLevel());
//			card.setSizeColumns(cf.getDashSize());
		}
	}
}
