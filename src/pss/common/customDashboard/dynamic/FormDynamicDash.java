package  pss.common.customDashboard.dynamic;

import pss.common.customDashboard.DashBoardInfo;
import pss.common.customDashboard.config.BizDashBoardConfig;
import pss.core.tools.collections.JIterator;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormCardResponsive;
import pss.core.winUI.responsiveControls.JFormColumnResponsive;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;

public class FormDynamicDash extends JBaseForm {
  /**
   * Constructor de la Clase
   */
  public FormDynamicDash() throws Exception {
  }

  public GuiDynamicDash getWin() { return (GuiDynamicDash) getBaseWin(); }

  public void InicializarPanel( JWin zWin ) throws Exception {
		if (this.getWin().isInModule()) return;
		
		JFormPanelResponsive row = (JFormPanelResponsive) this.AddItemRow().setGutter(true);
		JFormColumnResponsive col = (JFormColumnResponsive) row.AddItemColumn().setSizeColumns(12);
	
		JIterator<BizDashBoardConfig> iter = this.getWin().GetcDato().getConfigs().getStaticIterator();
		while (iter.hasMoreElements()) {
			BizDashBoardConfig cfg = iter.nextElement();

			DashBoardInfo info = cfg.findInfo();
			if (cfg.getExcluded()) continue;
			
//			GuiDashBoard db = info.getObjModule().getWinDashboard();
//			db.GetcDato().setObjConfig(cfg);
			
//			String action = "FORM-"+cfg.getId()+"-"+info.getAction();
  		JFormCardResponsive c = col.AddCardPanel(info.getDescrip(), "FORM-"+cfg.getId());
  		c.setSizeColumns(cfg.getDashSize());
  		c.setDiferido(true);
  		
//			String action = "DETAIL-"+cfg.getId()+"-"+dbd.getAction();
//			String field = dbd.getModuleId()+"-"+dbd.getField();
//  		this.getWin().getRecord().addItem(field, db.getRecord().getProp(dbd.getField()));
//			if (dbd.isImage()) {
//				JFormImageCardResponsive img = col.AddImageCard( dbd.getDescrip(), field, dbd.getActionDescrip(), action);
//				img.setSizeColumns(cfg.getDashSize());
//				img.setSource(JPssImage.SOURCE_PSSDATA);
//				img.SetReadOnly(true);
//				img.setResponsiveClass(dbd.getLevel());
//				img.setIdControl(field+cfg.getDashOrder());
//			} else {
//				JFormInfoCardResponsive card = col.AddInfoCard(dbd.getDescrip(), JBaseForm.CHAR, field, JWebIcon.getResponsiveIcon(dbd.getIcon()), dbd.getActionDescrip(), action);
//				card.setResponsiveClass(dbd.getLevel());
//				card.setSizeColumns(cfg.getDashSize());
//			}
				
		}
	}
  		
  	
}  //  @jve:decl-index=0:visual-constraint="10,10" 
