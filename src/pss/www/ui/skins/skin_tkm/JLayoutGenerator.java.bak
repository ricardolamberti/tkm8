/*
 * Created on 09-jun-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.ui.skins.skin_tkm;

import pss.JPath;
import pss.common.security.BizUsuario;
import pss.core.winUI.icons.GuiIconos;
import pss.www.platform.actions.JWebActionFactory;
import pss.www.ui.JWebDivResponsive;
import pss.www.ui.JWebFilterPaneResponsive;
import pss.www.ui.JWebHResponsive;
import pss.www.ui.JWebIcon;
import pss.www.ui.JWebLabelResponsive;
import pss.www.ui.JWebNavigationBarGroup;
import pss.www.ui.JWebNavigationBarSidebar;
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

/**
 * 
 * 
 * Created on 09-jun-2003
 * 
 * @author PSS
 */

public class JLayoutGenerator extends JBasicLayoutGenerator implements ILayoutGenerator {

	private static final String SKIN_PATH = "skins/sbadmin";

	public String getSkinPath() {
		return SKIN_PATH;
	}
	public void configuereBars(JWebViewNavigationBar bar, String menu)  throws Exception{
		if (menu.equals("TOP")) bar.setNavBarBlack(true);
	}

	public void fillNavBar(JWebView parent) throws Exception {
		// super.fillNavBar(parent);
		String logo = BizUsuario.getUsr().getObjCompany().getLogo();
		String logoPath = logo == null ? null : JPath.PssPathLogos() + BizUsuario.getUsr().getObjCompany().getLogo();
		boolean isMobile = JWebActionFactory.isMobile();
		if (isMobile) {
			parent.addUserPreferences(null, JWebIcon.getResponsiveIcon("fa fa-fw fa-address-card"),getMessage("Preferencias", null));
			parent.addChangePassword(null, JWebIcon.getResponsiveIcon("fa fa-fw fa-lock"),getMessage("Cambiar clave", null));
			if (BizUsuario.IsAdminUser())
				parent.addInfoSystem(null,JWebIcon.getResponsiveIcon("fa fa-fw fa-server"),null);
			parent.addCloseSession(null,JWebIcon.getResponsiveIcon("fas fa-sign-out-alt"),null);
			return;
		}
 
		parent.addNavigationTitle(BizUsuario.getUsr().getObjBusiness().getTitle(),logo.equals("")?null: JWebIcon.getPssDataResource(logoPath), "padding-right: 10px;height:50px;");
		parent.addGoBack(null, JWebIcon.getResponsiveIcon("fa fa-fw fa-reply fa-lg"), null);
		//parent.addGoHelp(null, JWebIcon.getResponsiveIcon("fa fa-fw fa-question-circle"), null);
		parent.addSecurity(null, JWebIcon.getResponsiveIcon("fa fa-fw fa-key"), null);
		parent.addGoHome(null, JWebIcon.getResponsiveIcon("fa fa-fw fa-home"), null);
		parent.addNewSession(null, JWebIcon.getResponsiveIcon("fa fa-fw fa-copy"), null);
		//parent.addMailFolder(null, JWebIcon.getResponsiveIcon("fa fa-fw fa-envelope"), "Mensajeria", 1);
		//parent.addAlertFolder(null, JWebIcon.getResponsiveIcon("fa fa-fw fa-bell"), "Alertas", 1);
		JWebNavigationBarGroup g3 = parent.addUserFolder(null, JWebIcon.getResponsiveIcon("fa fa-fw fa-user"), "Config", 1);
		if (g3 != null) {
			parent.addSeparator(g3);
			parent.addUserPreferences(g3, JWebIcon.getResponsiveIcon("fa fa-fw fa-address-card-o"), "Preferencias");
			if (BizUsuario.IsAdminUser())
				parent.addInfoSystem(g3,JWebIcon.getResponsiveIcon("fa fa-fw fa-server"),null);
			// parent.addUserListExcludeCols(g3, JWebIcon.getResponsiveIcon("fa fa-fw
			// fa-columns"),"Preferencias Vista");
			parent.addChangePassword(g3, JWebIcon.getResponsiveIcon("fa fa-fw fa-lock"), "Cambiar clave");
			parent.addSeparator(g3);
			parent.addCloseSession(g3, JWebIcon.getResponsiveIcon("fa fa-fw fa-sign-out"), null);
		}
		//parent.addRefresh(null, JWebIcon.getResponsiveIcon("fa fa-fw fa-sync-alt"), null);

		// parent.addLogo(JWebIcon.getPssDataResource(logoPath),"height:25px;");

	}

	public JWebFilterPaneResponsive buildFormFilter(JWebWinGenericResponsive parent) throws Exception {
		JWebFilterPaneResponsive filterPanel = new JWebFilterPaneResponsive(parent);
		if (JWebActionFactory.isMobile())
			parent.addButtonModeFiltersInFormFilter(filterPanel);
		if (!parent.useSimpleOrden())
			parent.addButtonOrderInFormFilter(filterPanel);
		return filterPanel;
	}

	public void buildFooterExtraBar1(JWebWinGenericResponsive parent, JWebViewInternalComposite oNavigationBar) throws Exception {
		// parent.addInfoRecord(oNavigationBar); // put info into button
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
		if (parent.isEmbedded())
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
//		if (parent instanceof JWebWinActionBarSwapList) {
//			return null;
//		}
		return super.createBotonMore(parent, isWinList, isForm, isFormLov);
	}

}
