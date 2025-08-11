package pss.www.ui.skins.skin_mobile;

import pss.JPath;
import pss.common.security.BizUsuario;
import pss.core.win.actions.BizAction;
import pss.core.winUI.icons.GuiIconos;
import pss.www.platform.actions.JWebAction;
import pss.www.platform.actions.JWebActionFactory;
import pss.www.ui.JWebDivResponsive;
import pss.www.ui.JWebFilterPaneResponsive;
import pss.www.ui.JWebHResponsive;
import pss.www.ui.JWebIcon;
import pss.www.ui.JWebLabelResponsive;
import pss.www.ui.JWebNavigationBarGroup;
import pss.www.ui.JWebView;
import pss.www.ui.JWebViewComposite;
import pss.www.ui.JWebViewInternalComposite;
import pss.www.ui.JWebViewNavigationBar;
import pss.www.ui.JWebViewZone;
import pss.www.ui.JWebViewZoneRow;
import pss.www.ui.JWebWinActionBar;
import pss.www.ui.JWebWinActionBarSwapList;
import pss.www.ui.JWebWinForm;
import pss.www.ui.JWebWinGenericResponsive;
import pss.www.ui.skins.ILayoutGenerator;
import pss.www.ui.skins.JBasicLayoutGenerator;

public class JLayoutGenerator  extends JBasicLayoutGenerator implements ILayoutGenerator {

	private static final String SKIN_PATH = "skins/sbadmin";

	public String getSkinPath() {
		return SKIN_PATH;
	}
	public void configuereBars(JWebViewNavigationBar bar, String menu)  throws Exception{
		if (menu.equals("TOP")) bar.setNavBarBlack(true);
	}

	public void fillNavBar(JWebView parent) throws Exception {
		parent.addUserPreferences(null, JWebIcon.getResponsiveIcon("fa fa-fw fa-address-card"),getMessage("Preferencias", null));
		parent.addChangePassword(null, JWebIcon.getResponsiveIcon("fa fa-fw fa-lock"),getMessage("Cambiar clave", null));
		if (BizUsuario.IsAdminUser())
			parent.addInfoSystem(null,JWebIcon.getResponsiveIcon("fa fa-fw fa-server"),null);
		parent.addCloseSession(null,JWebIcon.getResponsiveIcon("fas fa-sign-out-alt"),null);
		return;
	}

	public JWebFilterPaneResponsive buildFormFilter(JWebWinGenericResponsive parent) throws Exception {
		JWebFilterPaneResponsive filterPanel = new JWebFilterPaneResponsive(parent);
		parent.addButtonModeFiltersInFormFilter(filterPanel);
		return filterPanel;
	}

	public void buildFooterExtraBar1(JWebWinGenericResponsive parent, JWebViewInternalComposite oNavigationBar) throws Exception {
		parent.addButtonModePreview(oNavigationBar);
		parent.addButtonModeFilters(oNavigationBar);
		parent.addSelectSizePage(oNavigationBar);
	}
	public boolean hasHistory() {
			return  false;
	}
	public JWebViewComposite buildForm(JWebWinForm parent) throws Exception {
		JWebViewZone zone = new JWebViewZone();
		JWebDivResponsive panel = new JWebDivResponsive();
		JWebLabelResponsive titleRight = parent.hasTitleRight()?new JWebLabelResponsive():null;
		JWebHResponsive title = new JWebHResponsive(3,parent.getTitle());
//		title.setClassResponsive("pss-title");
		JWebDivResponsive panelHeading = new JWebDivResponsive();
		JWebDivResponsive panelBody = new JWebDivResponsive();
		JWebDivResponsive panelFooter = new JWebDivResponsive();
		JWebViewZoneRow formRowPanel = new JWebViewZoneRow();
		JWebViewZone formPanel = new JWebViewZone();
		
		if (withIcono() ) {
			JWebIcon oIcon = JWebIcon.getPssIcon(GuiIconos.RESPONSIVE, parent.getWin().GetNroIcono());
			title.setIcon(oIcon);
		}
		
		zone.setClassResponsive("col-lg-12");
		if (parent.isEmbedded())  {
			panel.setClassResponsive("panel panel-ghost");
		} 
		
		panelHeading.setClassResponsive("panel-heading clearfix");
		panelBody.setClassResponsive("panel-body");
		if (parent.isToolbarLeft())
			panelBody.setClassResponsive(panelBody.getClassResponsive() + " with-sidebar");

		formRowPanel.setClassResponsive("row");
		formPanel.setClassResponsive("col-lg-12");
		parent.setRole("form");
	
		formPanel.add("form", parent);
		formRowPanel.add("col", formPanel);
		if (parent.isWithHeader()) 
			panelBody.add("header", panelHeading);
		panelBody.add("row", formRowPanel);
		if (parent.isToolbarLeft())
			panel.add("actionbar",parent.buildActionBar());
		panel.add("body", panelBody);
//		if (!parent.isEmbedded()) {
//			panelFooter.setClassResponsive("panel-footer");
//			panel.add("footer", panelFooter);
//		}
		zone.add("panel", panel);
		title.setClassResponsive("one-line-right");

		panelHeading.add("title",title);
		if (parent.isToolbarTop())
			title.add("actionbar",parent.buildActionBar());

		if (titleRight!=null) {
			titleRight.setClassResponsive("pull-right title_right");
			titleRight.setLabel(parent.getTitleRight());
			title.add("title_right",titleRight);
		}
		JWebViewZoneRow root = new JWebViewZoneRow();
		root.setName(parent.getTitle());
		root.setClassResponsive("row");
		root.addChild("row", zone);

		return root;
	}
	
	@Override
	public JWebViewComposite createBotonMore(JWebWinActionBar parent, boolean isWinList, boolean isForm, boolean isFormLov) throws Exception {
		if (parent instanceof JWebWinActionBarSwapList) {
			return null;
		}
		return super.createBotonMore(parent, isWinList, isForm, isFormLov);
	}

	public boolean attachBackActionToToolbar(String zone) {
			return false;
	}
	public JWebViewComposite createFooterBar(JWebWinGenericResponsive parent,JWebViewInternalComposite infoBar,JWebViewInternalComposite navBar,JWebViewInternalComposite exportBar,JWebViewInternalComposite extraBar1,JWebViewInternalComposite extraBar2) throws Exception {
		JWebViewZoneRow row = new JWebViewZoneRow();
		row.add("nav_zone", navBar);
		return row;
	}
	public JWebViewInternalComposite createNavigationBar(JWebViewComposite parent, String providerName) throws Exception {
		JWebDivResponsive navigationBar = new JWebDivResponsive();
		navigationBar.setName(providerName+"_navigation_bar");
		navigationBar.setClassResponsive("col-sm-12 pagination-bar");
		return navigationBar;
	}
	
	public boolean actionsInMore(JWebWinActionBar parent,BizAction zAction, JWebAction webAction) throws Exception {
		if (parent.isPreview()) 
			return true;
		if (!zAction.useDefaultToolBarMore())
			return zAction.ifToolBarMore() ;

  	if (webAction.isCancel()) return false;
		if (webAction.isForceSubmit()) return false;

		if (webAction.isForm()) {
			if (!parent.isToolbarLeft())
				return true;
			return false;
		}
		if (!webAction.isWin()) {
  		if (webAction.isWinListRefresh())
				return true;
			return false;
		}
		if (zAction.getId()==BizAction.DROP) return false;
		if (webAction.isFormLov()) return false;
		if (parent.isToolbarLeft()) return false;
		return zAction.ifToolBarMore() ;
	}
	
}
