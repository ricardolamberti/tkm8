package pss.www.ui.skins.skin_mirgor;

import java.awt.Insets;

import pss.JPath;
import pss.common.regions.company.JCompanyBusiness;
import pss.common.security.BizUsuario;
import pss.core.tools.JTools;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JList;
import pss.core.win.JBaseWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.icons.GuiIconos;
import pss.core.winUI.lists.JWinList;
import pss.core.winUI.responsiveControls.JFormCheckResponsive;
import pss.core.winUI.responsiveControls.JFormControlResponsive;
import pss.core.winUI.responsiveControls.JFormWinLOVResponsive;
import pss.www.platform.actions.JWebAction;
import pss.www.platform.actions.JWebActionFactory;
import pss.www.ui.JWebDivResponsive;
import pss.www.ui.JWebEditComponentContainer;
import pss.www.ui.JWebFilterPaneResponsive;
import pss.www.ui.JWebHResponsive;
import pss.www.ui.JWebIcon;
import pss.www.ui.JWebLabelResponsive;
import pss.www.ui.JWebLink;
import pss.www.ui.JWebNavigationBarGroup;
import pss.www.ui.JWebPanelResponsive;
import pss.www.ui.JWebSplit;
import pss.www.ui.JWebView;
import pss.www.ui.JWebViewComponent;
import pss.www.ui.JWebViewComposite;
import pss.www.ui.JWebViewDropDownButton;
import pss.www.ui.JWebViewHistoryBar;
import pss.www.ui.JWebViewInternalComposite;
import pss.www.ui.JWebViewNavigationBar;
import pss.www.ui.JWebViewZone;
import pss.www.ui.JWebViewZoneRow;
import pss.www.ui.JWebWinActionBar;
import pss.www.ui.JWebWinActionBarSwapList;
import pss.www.ui.JWebWinForm;
import pss.www.ui.JWebWinGenericResponsive;
import pss.www.ui.JWebWinSwapListResponsive;
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

	public String sourceColors() throws Exception {
		return "skins/sbadmin/css/mirgor.css";
	}

	public String source() throws Exception {
		return "skins/sbadmin/css/sb-admin-2.css";
	}

	public String getToolbarListDefault() throws Exception {
		return JBaseWin.TOOLBAR_LEFT;
	}

	public String getToolbarFormDefault() throws Exception {
		return JBaseWin.TOOLBAR_LEFT;
	}

	public JWebViewComposite createBotonMore(JWebWinActionBar parent, boolean isWinList, boolean isForm,
			boolean isFormLov) throws Exception {
		JWebViewComposite c = super.createBotonMore(parent, isWinList, isForm, isFormLov);
		if (!(c instanceof JWebViewDropDownButton))
			return c;
		JWebViewDropDownButton b = (JWebViewDropDownButton) c;
		b.setImage("fas fa-bars");
		b.setArrow(null);
		return b;
	}

	public void buildPreConfigurarColumnaLista(JWins webwin, JWinList winList) throws Exception {
		winList.AddColumnaAction(87, null, "all", true);
	}

	protected JWebIcon findMenuIcon(String tipo, BizAction action) throws Exception {
		return super.findMenuIcon(tipo, action);
	}

	protected JWebIcon findActionIcon(JWebWinActionBar bar, BizAction action, JWebAction webAction) throws Exception {
		JWebIcon icon = super.findActionIcon(bar, action, webAction);
		if (icon == null)
			return null;
//		if (bar.isToolbarLeft()) icon.setURL(icon.getURL()+" bar-left-icon" );
		return icon;
	}

	protected String findActionClass(JWebWinActionBar bar, BizAction action, JWebAction webAction) throws Exception {
		return "btn btn-sm";
	}

	protected String findIconPosition(JWebWinActionBar bar, BizAction action, JWebAction webAction) throws Exception {
		if (bar.isEmbedded())
			return JWebLink.POSICON_DOWN;
		return JWebLink.POSICON_RIGHT_NODIV;
	}

//	public void buildHeaderMenu(JWebView parent,JWebViewNavigationBar menu) throws Exception {
//	}
//
//	protected void fillHeader(JWebView parent) throws Exception {
//	}
//	public void buildHeaderMenu(JWebView parent, JWebViewNavigationBar menu) throws Exception {
//		super.buildHeaderMenu(parent, menu);
//	}
	
	public String findLogoMenu() throws Exception {
		return JPath.PssPathLogos()+BizUsuario.getUsr().getCompany()+"/logo_mgr2.png";
	}


	protected void fillHeader(JWebView parent) throws Exception {
		String logo = BizUsuario.getUsr().getObjCompany().getLogo();
		String logoPath = (logo == null) ? null : "/files/logos/" + logo;
		parent.addNavigationTitle("", JWebIcon.getPssDataResource(logoPath), "height:100%;max-height:30px;padding:3px;");
	}

	public JWebViewHistoryBar getHistoryBar(JWebViewComposite parent) throws Exception {
		return null;
	}

	public boolean attackBackActionToToolbar(String zone) {
		return true;
	}

	public String getPullBotons() {
		return "left";
	}

	public boolean actionsInMore(JWebWinActionBar parent, BizAction zAction, JWebAction webAction) throws Exception {
		return false;
	}

	public String getExpandAllSubDetail() {
		return "images/tree/expand-sublist.gif";
	}

	public String getMoreSubDetail() {
		return "images/tree/expand-sublist.gif";
	}

	public boolean attachBackActionToToolbar(String zone) {
		return true;
	}

	@Override
	protected boolean withIcono() throws Exception {
		return true;
	}

	public JWebViewInternalComposite createActionBarWinListTop(JWebWinGenericResponsive zObjectProvider) throws Exception {
		JWebWinActionBar actionBar=zObjectProvider.getActionBar();
		if (zObjectProvider instanceof JWebWinSwapListResponsive) {
			actionBar.setClassResponsive("btn-group pull-right");
		} else {
			actionBar.setClassResponsive("btn-group pull-left");
		}
		return actionBar;
	}

//	public JWebViewInternalComposite buildWinList(JWebWinGenericResponsive parent) throws Exception {
//		parent.setAllowSortedColumns(false);
//		parent.setFilterBar(false);
//		parent.setToolbar(JWins.TOOLBAR_TOP);
//		return super.buildWinList(parent);
//	}

	public JWebViewComposite buildForm(JWebWinForm parent) throws Exception {
		JWebViewZone zone = new JWebViewZone();
		JWebDivResponsive panel = new JWebDivResponsive();

		JWebDivResponsive panelHeading = new JWebDivResponsive();
		JWebDivResponsive panelBody = new JWebDivResponsive();
		JWebViewZoneRow formRowPanel = new JWebViewZoneRow();
		JWebViewZone formPanel = new JWebViewZone();
		zone.setClassResponsive("col-lg-12 ");
		if (parent.isEmbedded())
			panel.setClassResponsive("panel panel-ghost");
		else
			panel.setClassResponsive("panel panel-primary");
		panelHeading.setClassResponsive("panel-heading clearfix");
		panelBody.setClassResponsive("panel-body");
		if (parent.isToolbarLeft())
			panelBody.setClassResponsive(panelBody.getClassResponsive() + " with-sidebar");

		formRowPanel.setClassResponsive("row");
		formPanel.setClassResponsive("col-lg-12 ");
		parent.setRole("form");

		formPanel.add("form", parent);
		formRowPanel.add("col", formPanel);
		panelBody.add("row", formRowPanel);
		
//		if (parent.isToolbarLeft())
//			panel.add("actionbar", parent.buildActionBar());
		panel.add("header", panelHeading);
		panel.add("body", panelBody);

		zone.add("panel", panel);

		JWebPanelResponsive titleCol = new JWebPanelResponsive();
		JWebHResponsive title = new JWebHResponsive(3, parent.getTitle());
		title.setIcon(JWebIcon.getPssIcon(GuiIconos.RESPONSIVE, parent.getWin().GetNroIcono()));
		title.setClassResponsive("pull-right");
		titleCol.add("title", title);
		if (!parent.isToolbarNone())
			titleCol.add("actionbar", parent.buildActionBar());

		panelHeading.add("header_title", titleCol);


		JWebViewZoneRow root = new JWebViewZoneRow();
		root.setName(parent.getTitle());
		root.setClassResponsive("row");
		root.addChild("row", zone);

		return root;
	}

	public String getColumnClass(JFormControlResponsive control, String strColumns) {
		String cls = "";
		long columns = JTools.getLongFirstNumberEmbedded(strColumns);
		if (columns >= 12)
			cls += "col-xl-12 col-lg-12 col-md-12 col-sm-12";
		else if (columns >= 11)
			cls += "col-xl-11 col-lg-11 col-md-11 col-sm-12";
		else if (columns >= 10)
			cls += "col-xl-10 col-lg-10 col-md-10 col-sm-12";
		else if (columns >= 9)
			cls += "col-xl-9 col-lg-9 col-md-9 col-sm-12";
		else if (columns >= 8)
			cls += "col-xl-8 col-lg-8 col-md-8 col-sm-12";
		else if (columns >= 7)
			cls += "col-xl-7 col-lg-7 col-md-7 col-sm-12";
		else if (columns >= 6)
			cls += "col-xl-6 col-lg-6 col-md-6 col-sm-12";
		else if (columns >= 5)
			cls += "col-xl-5 col-lg-5 col-md-5 col-sm-12";
		else if (columns >= 4)
			cls += "col-xl-4 col-lg-4 col-md-4 col-sm-12";
		else if (columns >= 3)
			cls += "col-xl-3 col-lg-3 col-md-3 col-sm-12";
		else if (columns >= 2)
			cls += "col-xl-2 col-lg-2 col-md-2 col-sm-12";
		else if (columns >= 1)
			cls += "col-xl-1 col-lg-1 col-md-1 col-sm-12";
		else if (columns >= 0)
			cls += "col-xl-0 col-lg-0 col-md-0 col-sm-0";
		else
			cls += "col-xl-" + columns;
		return cls;
	}

	public String findCheckModeDefault() {
		return JFormCheckResponsive.MODE_SQUARE;
	}

	public boolean isFiltersFirst() throws Exception {
		return false;
	}

	public boolean isFilterInLine() throws Exception {
		return false;
	}

	public void addAtributesToFilterControls(JWebViewInternalComposite parent, JFormControlResponsive component)
			throws Exception {
		component.setSizeColumns(0);
//		component.setFormatFields(JFormPanelResponsive.FIELD_LABEL_HORIZONTAL_RIGHT);
	}

	public String getClassFieldHorizontal(JWebViewComponent componente, JWebEditComponentContainer form)
			throws Exception {
		if (form instanceof JWebFilterPaneResponsive)
			return "";
		return super.getClassFieldHorizontal(componente, form);
	}

	public String getClassLabelHorizontal(JWebViewComponent componente, JWebEditComponentContainer form)
			throws Exception {
		if (form instanceof JWebFilterPaneResponsive)
			return "";
		return super.getClassLabelHorizontal(componente, form);
	}

	public void configureJFormItem(JFormControl control) throws Exception {
		if (control instanceof JFormWinLOVResponsive) {
			((JFormWinLOVResponsive) control).setModal(false);
			((JFormWinLOVResponsive) control).setPlaceHolder("");
		}
	}

	@Override
	public void configureJFormFiltro(JFormControl control) throws Exception {
		if (control instanceof JFormWinLOVResponsive) {
			((JFormWinLOVResponsive) control).setPlaceHolder("");
			((JFormWinLOVResponsive) control).setModal(false);
		}
		super.configureJFormFiltro(control);
	}

	public String getMessageConfirmLostData() throws Exception {
		return null;
	}
	
	public String menuPosPrincipal()  throws Exception {
		return "SIDEBAR";
  }

	public void fillNavBar(JWebView parent) throws Exception {
		this.fillHeader(parent);

		parent.addGoBack(null, JWebIcon.getResponsiveIcon("fa fa-fw fa-arrow-left"), null);
		// parent.addGoHelp(null, JWebIcon.getResponsiveIcon("fa fa-fw
		// fa-question-circle"), null);
		parent.addSecurity(null, JWebIcon.getResponsiveIcon("fa fa-fw fa-key"), null);
		parent.addGoHome(null, JWebIcon.getResponsiveIcon("fa fa-fw fa-home"), null);
		parent.addNewSession(null, JWebIcon.getResponsiveIcon("fa fa-fw fa-copy"), null);
		parent.addMailFolder(null, JWebIcon.getResponsiveIcon("fa fa-fw fa-envelope"), getMessage("Mensajeria", null), 1);
//		parent.addAlertFolder(null, JWebIcon.getResponsiveIcon("fa fa-fw fa-bell"), getMessage("Alertas", null), 1);
		JWebNavigationBarGroup g3 = parent.getNavBar().addNavigationGroup(null,
				JWebIcon.getResponsiveIcon("fa fa-fw fa-user"), BizUsuario.getUsr().GetDescrip(), 0);
		parent.getNavBar().addNavigationActionMensaje(g3, BizUsuario.getUsr().GetUsuario(), null, null, null, null, 0);
		parent.getNavBar().addNavigationActionMensaje(g3, JTools.capitalizeAll(BizUsuario.getUsr().GetDescrip()), null,
				null, null, JWebIcon.findIcon(10074), 0);
		if (!BizUsuario.getUsr().getNodo().isEmpty())
			parent.getNavBar().addNavigationActionMensaje(g3,
					JTools.capitalizeAll(BizUsuario.getUsr().getObjNodo().GetDescrip().toLowerCase()), null, null, null,
					JWebIcon.findIcon(10072), 0);
		parent.addSeparator(g3);
		JList<BizAction> list = BizUsuario.getUsr().getGlobalActions();
		if (list != null) {
			JIterator<BizAction> iter = list.getIterator();
			while (iter.hasMoreElements()) {
				BizAction a = iter.nextElement();
				parent.getNavBar().addNavigationActionMensaje(g3, a.GetDescr(), null, null, null,
						JWebIcon.findIcon(a.GetNroIcono()), 0);
			}
			parent.addSeparator(g3);
		}
		parent.addChangePassword(g3, JWebIcon.getResponsiveIcon("fa fa-fw fa-lock"), getMessage("Cambiar clave", null));
		parent.addSeparator(g3);
		parent.addCloseSession(g3, JWebIcon.getResponsiveIcon("fas fa-sign-out-alt"), null);
		parent.addRefresh(null, JWebIcon.getResponsiveIcon("fa fa-fw fa-sync-alt"), null);
		parent.addActiveHelp(null, JWebIcon.getResponsiveIcon("fas fa-question-circle"), getMessage("Ayuda", null));

//		parent.addLogo(JWebIcon.getPssDataResource(logoPath),"height:25px;");

	}

	public JWebViewInternalComposite createPreferenceBar(JWebViewComposite parent, String providerName) throws Exception {
		return null;
	}

	public boolean canClickInAllHeaderForOrder() throws Exception {
		return false;
	}

	public boolean useSimpleOrden(JWebWinGenericResponsive parent) throws Exception {
		return false;
	}

	public JWebViewComponent buildOrderButtonInFilter(JWebViewComposite parent, JWebAction webAction) throws Exception {
		JWebViewComponent link = super.buildOrderButtonInFilter(parent, webAction);
		link.setHeight(20);
		link.setWidth(26);
		link.setPadding(new Insets(4,0,0,0));
		return link;
	}
	
	public JWebViewInternalComposite createActionBarFormTop(JWebWinForm zObjectProvider) throws Exception {
		JWebViewInternalComposite actionBar=zObjectProvider.getActionBar();
		actionBar.setClassResponsive("btn-group pull-left");
		return actionBar;
	}

	public JWebViewInternalComposite buildWinList(JWebWinGenericResponsive parent) throws Exception {
		parent.setAllowSortedColumns(false);
		parent.setFilterBar(false);
		parent.setToolbar(JWins.TOOLBAR_TOP);

		JWebViewComposite zoneList = new JWebViewZone();
		zoneList.setClassResponsive("col-lg-12");
		zoneList.setLabelLateral(parent.getLabelLateral());
//		JWebViewComposite zonePreview;
		JWebDivResponsive panel = new JWebDivResponsive();
		JWebDivResponsive panelHeading = new JWebDivResponsive();
		JWebDivResponsive panelBody = new JWebDivResponsive();
		JWebDivResponsive panelFooter = new JWebDivResponsive();
	
		JWebDivResponsive filterHeading = new JWebDivResponsive();
		JWebDivResponsive filterBody = new JWebDivResponsive();
		JWebDivResponsive filterFooter = new JWebDivResponsive();
		
		JWebViewZoneRow filterRowHeaderPanel = new JWebViewZoneRow();
		JWebViewZoneRow filterRowPanel = new JWebViewZoneRow();
		JWebViewZoneRow listRowPanel = new JWebViewZoneRow();

		JWebDivResponsive panelToolbar = new JWebDivResponsive();
		JWebDivResponsive panelWithinPreview = new JWebDivResponsive();
		JWebSplit panelWithPreview = new JWebSplit();
		panelWithPreview.setPosSplit(parent.getPreviewSplitPos());
		JWebViewZone listPanel = new JWebViewZone();
		JWebViewZone previewPanel = new JWebViewZone();
		JWebViewZone filters = new JWebViewZone();
		JWebPanelResponsive previewPanelZoom = new JWebPanelResponsive();
//		h4.setClassResponsive("pss-title");

		JWebViewInternalComposite actionbarRowPanel = parent.createActionBar();
		JWebViewComposite navigationRow = parent.createNavigationBar();
		JWebViewComposite preferenceBar = parent.createPreferenceActionaBar();
	
	
	   boolean withFooter=false;
		if (parent.hasNavigationBar()&&parent.isShowFooterBar()) {
			panelFooter.addChild("nav",navigationRow);
			withFooter=true;
		}

		JWebViewComposite zoneFilter=this.createZoneFilter(parent);

		
		if (parent.isEmbedded())
			panel.setClassResponsive("panel panel-primary");
		else
			panel.setClassResponsive("panel panel-primary");

		panelHeading.setClassResponsive("panel-heading clearfix");
		panelBody.setClassResponsive("panel-body");
		if (parent.isToolbarLeft())
			panelBody.setClassResponsive(panelBody.getClassResponsive()+" with-sidebar");

		if (withFooter) {
			panelFooter.setClassResponsive("panel-footer");
			if (parent.isToolbarLeft())
				panelFooter.setClassResponsive(panelFooter.getClassResponsive()+" with-sidebar");
		}
		
		filterHeading.setClassResponsive("panel-heading");
		filterBody.setClassResponsive("panel-body");
		filterRowPanel.setClassResponsive("row");
		listRowPanel.setClassResponsive("row");
		
		listPanel.setClassResponsive("col-sm-12 ");
		panelWithPreview.setClassResponsive("panel-container col-sm-12");
		listPanel.add("list", parent);

		this.attachFilters(parent, zoneFilter, panelBody);

		if (parent.isToolbarNone()) {
			panelToolbar.setDisplayType(JFormControl.DISPLAY_NONE);
			panel.addChild(parent.getProviderName()+"_toolbar",panelToolbar);
			panelToolbar.setClassResponsive("hidden");
			panelToolbar.add("actionbar",actionbarRowPanel);
			panelToolbar.setClassResponsive("toolbar pull-lg-"+this.getPullBotons());
		} else {
			if (parent.isEmbedded()) {
				if (!parent.hasFilterNeverHide() ) {
					JWebViewComponent button = parent.addButtonModeFilters(filterHeading);
					if (button!=null && button.getClassResponsive().indexOf("pull")==-1)
						button.setClassResponsive(button.getClassResponsive()+" pull-lg-right");
					
				}
				if (parent.isToolbarIn()) {
					panelToolbar.setClassResponsive("toolbar pull-lg-"+this.getPullBotons());
					panelToolbar.addChild("toolbar",actionbarRowPanel); 
				} else 
					panel.addChild("actionbar",actionbarRowPanel); 
			} else {
				
				JWebPanelResponsive titleCol = new JWebPanelResponsive();
				JWebHResponsive title = new JWebHResponsive(3,parent.getTitle());
				title.setIcon(JWebIcon.getPssIcon(GuiIconos.RESPONSIVE, parent.getWins().GetNroIcono()));
				title.setClassResponsive("pull-right");
				titleCol.add("title", title);
				titleCol.add("actionbar", actionbarRowPanel);
				
				panelHeading.addChild("header_title", titleCol); 
		
//				if (parent.isToolbarTop())
//					titleCol.addChild("toolbar", actionbarRowPanel); 
//				else if (parent.isToolbarIn())
//					panelToolbar.addChild("toolbar",actionbarRowPanel); 

//				if (preferenceBar!=null && !JWebActionFactory.isMobile()) h4.addChild("preference",preferenceBar);

				panel.add("header", panelHeading);
			}	
		}

		
//		this.attachToolbarIn(parent, panelToolbar, panelBody);
		
		panelBody.add("col", listPanel);
		panelBody.add("row", listRowPanel);
		panel.add("body", panelBody);
		panel.add("footer", panelFooter);
		zoneList.add("panel", panel);


		JWebViewZoneRow root = new JWebViewZoneRow();
		root.setName(parent.getCompletePanelName());
		root.setClassResponsive(parent.getClassResponsive());
		parent.setClassResponsive(null);//ya use su tamaño original en la zone que lo incluye

		if (parent.hasPreviewPanel()) {
			panelWithPreview.setOrientation(JWebSplit.ORIENT_VERTICAL);
			panelWithPreview.setLiteralPosSplit(JCompanyBusiness.getDefiniciones(JCompanyBusiness.SPLITWINLIST));
			panelWithPreview.setPanelA(null,zoneList);
			panelWithPreview.setPanelB(parent.getCompletePanelName()+"_panelb",previewPanel);
			previewPanel.add(parent.getPreviewPanelName(),previewPanelZoom);
			root.addChild(parent.getCompletePanelName()+"_panel",panelWithPreview);
		//	previewPanelZoom.setZoomtofit(1200);
			if (!parent.isEmbedded())
				previewPanel.setAffix(true);
		} else {
			panelWithinPreview.addChild("col_list", zoneList);
			root.addChild(parent.getCompletePanelName()+"_panel",panelWithinPreview);
		}

		return root;
	}
}
