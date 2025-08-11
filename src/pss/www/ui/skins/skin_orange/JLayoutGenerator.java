/*
 * Created on 09-jun-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.ui.skins.skin_orange;

import pss.JPath;
import pss.common.regions.company.JCompanyBusiness;
import pss.common.regions.multilanguage.JLanguage;
import pss.common.security.BizUsuario;
import pss.core.services.fields.JString;
import pss.core.tools.JDateTools;
import pss.core.tools.JTools;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JList;
import pss.core.tools.collections.JMap;
import pss.core.win.actions.BizAction;
import pss.core.win.actions.BizActions;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.icons.GuiIcon;
import pss.core.winUI.icons.GuiIconos;
import pss.core.winUI.lists.JColumnaLista;
import pss.core.winUI.responsiveControls.JFormCheckResponsive;
import pss.core.winUI.responsiveControls.JFormControlResponsive;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;
import pss.core.winUI.responsiveControls.JFormRadioResponsive;
import pss.core.winUI.responsiveControls.JFormWinLOVResponsive;
import pss.www.platform.actions.JWebAction;
import pss.www.platform.actions.JWebActionFactory;
import pss.www.ui.JWebActionOwnerProvider;
import pss.www.ui.JWebCheckResponsive;
import pss.www.ui.JWebComboResponsive;
import pss.www.ui.JWebContainer;
import pss.www.ui.JWebDivResponsive;
import pss.www.ui.JWebElementList;
import pss.www.ui.JWebFilterPaneResponsive;
import pss.www.ui.JWebFlowBreak;
import pss.www.ui.JWebFormFormResponsive;
import pss.www.ui.JWebHResponsive;
import pss.www.ui.JWebIcon;
import pss.www.ui.JWebImageResponsive;
import pss.www.ui.JWebLabelResponsive;
import pss.www.ui.JWebLink;
import pss.www.ui.JWebLocalFormResponsive;
import pss.www.ui.JWebNavigationBarGroup;
import pss.www.ui.JWebNavigationBarSidebar;
import pss.www.ui.JWebPanelResponsive;
import pss.www.ui.JWebRowGridExpandResponsive;
import pss.www.ui.JWebSplit;
import pss.www.ui.JWebView;
import pss.www.ui.JWebViewComponent;
import pss.www.ui.JWebViewComposite;
import pss.www.ui.JWebViewDropDownButton;
import pss.www.ui.JWebViewInternalComposite;
import pss.www.ui.JWebViewNavigationBar;
import pss.www.ui.JWebViewZone;
import pss.www.ui.JWebViewZoneRow;
import pss.www.ui.JWebWinActionBar;
import pss.www.ui.JWebWinForm;
import pss.www.ui.JWebWinGenericResponsive;
import pss.www.ui.JWebWinGridBigDataResponsive;
import pss.www.ui.JWebWinListFormLov;
import pss.www.ui.processing.JXMLRepresentable;
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

	public String menuPosPrincipal() throws Exception {
		return BizUsuario.getUsr().getVisionCustomMenu()==null?"NONE":"TOP";
	}

	public String[] getColorGraph() {
		String[] colorMap= { "#ded584","#9c9762","#9e984f","#9e973c","#c7bc2b","#f1e119","#d8d4a7","#847e41","#635b0d","#a29406","#797761","#e8e6c9","#f9e906","#ffffb3","#bebada","#fb8072","#80b1d3","#fdb462","#b3de69","#fccde5","#d9d9d9","#bc80bd","#ccebc5","#ffed6f","#1b9e77","#d95f02","#7570b3","#e7298a","#66a61e","#e6ab02","#a6761d","#666666","#fbb4ae","#b3cde3","#ccebc5","#decbe4","#fed9a6","#ffffcc","#e5d8bd","#fddaec","#f2f2f2" };
		return colorMap;
	}
	public JXMLRepresentable buildOrderColumn(JWebViewComposite parent,boolean column,String order,JWebAction webAction) throws Exception {
		if (!column||order==null) return null;
		JWebLink link = new JWebLink();
		link.setWebAction(webAction);
		link.setClassResponsive("btn btn-outline btn-link btn-like pull-sm-right");
		link.setCancel(false);
		link.setLabel("");
		link.setSubmit(false);
		link.setIcon(JWebIcon.getPssIcon(GuiIconos.RESPONSIVE,!column||order==null?15502:order.equalsIgnoreCase("ASC")?15501:15500));
		return link;
	}


	public String sourceColors() throws Exception {
		return "skins/sbadmin/css/orange.css";
	}
	public String source() throws Exception {
		return "skins/sbadmin/css/sb-admin-2.css";
	}
	public void configureJFormFiltro(JFormControl control) throws Exception {
		if (control instanceof JFormWinLOVResponsive) {
			((JFormWinLOVResponsive)control).setMultiple(false);
		}
	}
	public void configureJFormItem(JFormControl control) throws Exception {
	}
	public void fillNavBar(JWebView parent) throws Exception {
		this.fillHeader(parent);
 
//  	parent.addGoBack(null, JWebIcon.getResponsiveIcon("fa fa-fw fa-arrow-left"), null);
//		parent.addGoHelp(null, JWebIcon.getResponsiveIcon("fa fa-fw fa-question-circle"), null);
//		parent.addSecurity(null, JWebIcon.getResponsiveIcon("fa fa-fw fa-key"), null);
		parent.addGoHome(null, JWebIcon.getResponsiveIcon("fa fa-fw fa-home"), null);
		parent.addNewSession(null, JWebIcon.getResponsiveIcon("fa fa-fw fa-copy"), null);
		parent.addMailFolder(null, JWebIcon.getResponsiveIcon("fa fa-fw fa-envelope"),getMessage("Mensajeria", null),1);
		parent.addAlertFolder(null, JWebIcon.getResponsiveIcon("fa fa-fw fa-bell"),getMessage("Alertas", null),1);
		JWebNavigationBarGroup g3 = parent.addUserFolder(null, JWebIcon.getResponsiveIcon("fa fa-fw fa-user"),getMessage("Config", null),1);
		if (g3!=null) {
			parent.addSeparator(g3);
		//	parent.addUserPreferences(g3, JWebIcon.getResponsiveIcon("fa fa-fw fa-address-card-o"),getMessage("Preferencias"));
//			parent.addUserListExcludeCols(g3, JWebIcon.getResponsiveIcon("fa fa-fw fa-columns"),"Preferencias Vista");
			if ( BizUsuario.getUsr().getObjCompany().getObjBusiness().isLDAP()==false ) {
				parent.addChangePassword(g3, JWebIcon.getResponsiveIcon("fa fa-fw fa-lock"),getMessage("Cambiar clave", null));
				parent.addSeparator(g3);
			}
			if (BizUsuario.IsAdminUser())
				parent.addInfoSystem(g3,JWebIcon.getResponsiveIcon("fa fa-fw fa-server"),getMessage("Sistema info", null));
			parent.addCloseSession(g3,JWebIcon.getResponsiveIcon("fa fa-fw fa-sign-out"),null);
		}
		parent.addRefresh(null, JWebIcon.getResponsiveIcon("fa fa-fw fa-sync-alt"),null);
		parent.addActiveHelp(null,null,getMessage("Ayuda", null));
		
//		parent.addLogo(JWebIcon.getPssDataResource(logoPath),"height:25px;");

	}
	protected void fillHeader(JWebView parent) throws Exception {
		String logo = BizUsuario.getUsr().getObjCompany().getLogo();
		String logoPath=logo==null?null:JPath.PssPathLogos()+BizUsuario.getUsr().getObjCompany().getLogo();
		JWebDivResponsive panel2 =null;
		boolean isLevel1 = parent.getTitle()!=null&& parent.getTitle().equals("form.title.console");//trucho
		if (isLevel1) {
			panel2 =  new JWebDivResponsive();
			panel2.setClassResponsive("");
			JWebDivResponsive divImagen1 = new JWebDivResponsive();
			divImagen1.setClassResponsive("panel panel-default col-xl-2 col-lg-2 col-md-3 col-sm-3  col-xs-12");
			JWebDivResponsive diveBlanco = new JWebDivResponsive();
			diveBlanco.setClassResponsive("panel panel-default col-xl-9 col-lg-10 col-md-9 col-sm-9  col-xs-12 ");
			JWebDivResponsive diveTexto = new JWebDivResponsive();
			diveTexto.setClassResponsive("col-lg-10 col-md-8 col-sm-10 col-xs-12 info-top text-large");
			JWebDivResponsive divImagen = new JWebDivResponsive();
			divImagen.setClassResponsive("col-lg-2 col-md-4 col-sm-2 col-xs-12");
			JWebLabelResponsive label1 = new JWebLabelResponsive();
			label1.setLabel("Welcome, "+BizUsuario.getUsr().getDescrUsuario());
			label1.setClassResponsive("col-lg-6 col-md-8 col-sm-8 col-xs-12 label");
			JWebLabelResponsive label2 = new JWebLabelResponsive();
			label2.setLabel("Last user login "+JDateTools.DateToString(JWebActionFactory.getCurrentRequest().getSession().getLoginTime()));
			label2.setClassResponsive("col-lg-6 col-md-8 col-sm-8 col-xs-12 label");
			JWebLabelResponsive label3 = new JWebLabelResponsive();
			label3.setLabel("Current host "+JTools.GetHostName());
			label3.setClassResponsive("col-lg-6 col-md-8 col-sm-8 col-xs-12 label");
			JWebLabelResponsive label4 = new JWebLabelResponsive();
			label4.setLabel("Product registered by RetailExperts");
			label4.setClassResponsive("col-lg-6 col-md-8 col-sm-8 col-xs-12 label");
			
			JWebImageResponsive imageRetail = new JWebImageResponsive();
			imageRetail.setIcon(JWebIcon.getPssDataResource(JPath.PssPathLogos()+"logoretail.png"));
			imageRetail.setImageClass("center-block");
			imageRetail.setStyleIcon("max-height:100px;");
			imageRetail.setSinDIV(true);
			imageRetail.setWidthResponsive(0);
			
			JWebImageResponsive imageLogo = new JWebImageResponsive();
			imageLogo.setName("title_icon");
			imageLogo.setImageClass("center-block");
			imageLogo.setIcon(JWebIcon.getPssDataResource(logoPath));
			imageLogo.setStyleIcon("max-height:80px;");
			imageLogo.setSinDIV(true);
			imageLogo.setWidthResponsive(0);
			
			divImagen1.addChild(imageLogo);
			
			panel2.addChild(divImagen1);
			panel2.addChild(diveBlanco);
			diveBlanco.addChild(diveTexto);
			diveBlanco.addChild(divImagen);
			diveTexto.addChild(label1);
			diveTexto.addChild(label2);
			diveTexto.addChild(label3);
			diveTexto.addChild(label4);
			divImagen.addChild(imageRetail);
			
			JWebContainer header = new JWebContainer();
			header.setName("navheader");
			header.setClassResponsive("col-xl-10 col-lg-9 col-md-8 col-sm-12 col-xs-12 pull-left");
			header.addChild(panel2);
			parent.getNavBar().addChild(header);
		} else {
			if (menuPosPrincipal().equals("SIDEBAR")) 
				parent.addNavigationTitle("",null,null);
			else if (menuPosPrincipal().equals("TOP"))
				parent.addNavigationTitle("",JWebIcon.getPssDataResource(logoPath),"max-height:50px;");
			else 
				parent.addNavigationTitle("",JWebIcon.getPssDataResource(logoPath),"max-height:50px;");
						
		}


	}
//	public String getClassField(JWebViewComponent componente,JWebEditComponentContainer form,String force)  throws Exception {
//		if (force!=null) 
//			return force;
//		if (form==null) return null;
//		if (form instanceof JWebFilterPaneResponsive) return null;
//		if (form instanceof JWebWinForm && ((JWebWinForm)form).isEmbedded()) return null;
//		if (componente instanceof JWebViewComposite) return null;
//		return "col-sm-10";
//	}
//	public String getClassLabel(JWebViewComponent componente,JWebEditComponentContainer form,String force)  throws Exception {
//		if (force!=null) 
//			return force;
//		if (form==null) return null;
//		if (form instanceof JWebFilterPaneResponsive) return null;
//		if (form.isInLine()) return null;
//		if (form instanceof JWebWinForm && ((JWebWinForm)form).isEmbedded()) return null;
//		if (componente instanceof JWebViewComposite) return null;
//		return "col-sm-2 text-right";
//	}

	public void buildHeaderExtraBar1(JWebWinGenericResponsive parent) throws Exception {
		if (!parent.isAllowExportToCSV() && !parent.isAllowExportToExcel()) return;
		JWebViewDropDownButton globalMoreButton = new JWebViewDropDownButton();
		globalMoreButton.setArrow(JWebIcon.getResponsiveIcon( "caret"));
		globalMoreButton.setClassResponsive("btn-group pull-right");
		globalMoreButton.setClassButton("btn btn-default btn-sm dropdown-toggle ");
		globalMoreButton.setImage("fa fa-cog");
		parent.getActionBar().getRootPanel().addChild("more", globalMoreButton);
	
		parent.addButtonExportToMap(globalMoreButton);
		parent.addButtonExportToGraph(globalMoreButton);
		parent.addButtonExportToExcel(globalMoreButton);
		parent.addButtonExportToCSV(globalMoreButton);
	}

	public void buildFooterExtraBar1(JWebWinGenericResponsive parent, JWebViewInternalComposite oNavigationBar) throws Exception {
	}

	public void buildFooterExtraBar2(JWebWinGenericResponsive parent, JWebViewInternalComposite oNavigationBar) throws Exception {
	}

	public void buildExportBar(JWebWinGenericResponsive parent, JWebViewInternalComposite oNavigationBar) throws Exception {
//		JWebDivResponsive navigationBar = new JWebDivResponsive();
//		navigationBar.setName(parent.getProviderName()+"_footerExtraBar2");
//		navigationBar.setClassResponsive("pull-right");
//		parent.addButtonExportToMap(globalMoreButton);
//		parent.addButtonExportToGraph(globalMoreButton);
//		parent.addButtonExportToExcel(globalMoreButton);
//		parent.addButtonExportToCSV(globalMoreButton);
//		oNavigationBar.addChild(navigationBar);
		JWebDivResponsive navigationBar = new JWebDivResponsive();
		navigationBar.setName(parent.getProviderName()+"_footerExtraBar2");
		navigationBar.setClassResponsive("pull-right");
		oNavigationBar.addChild(navigationBar);
		parent.addInfoRecord(navigationBar);
	}
	public void buildInfoBar(JWebWinGenericResponsive parent,JWebViewInternalComposite oNavigationBar) throws Exception {
		JWebDivResponsive navigationBar = new JWebDivResponsive();
		navigationBar.setName(parent.getProviderName()+"_footerExtraBar1");
		navigationBar.setClassResponsive("pull-left");
		parent.addButtonModePreview(navigationBar);
		parent.addButtonModeFilters(navigationBar);
		parent.addSelectSizePage(navigationBar);
		oNavigationBar.addChild(navigationBar);
	}


	public JWebViewComponent createInfo(JWebViewComposite parent, String name, JWebAction action) throws Exception {
		name = name.replaceAll("Filas", JLanguage.translate("form.setup.page.rows"));
		if (action != null) {
			JWebLink oNavInfo = new JWebLink();
			oNavInfo.setOpensNewWindow(true);
			oNavInfo.setLabel(name);
			oNavInfo.setMode(JWebLink.MODE_LINK);
			oNavInfo.setWebAction(action);
			parent.addChild(name, oNavInfo);
			return oNavInfo;
		} else {
			JWebLabelResponsive oNavInfo = new JWebLabelResponsive();
			oNavInfo.setLabel(name);
			oNavInfo.setClassResponsive("tablelabel_info");
			parent.addChild(name, oNavInfo);
			return oNavInfo;
		}
	}

	public JWebViewComposite createFooterBar(JWebWinGenericResponsive parent,JWebViewInternalComposite infoBar,JWebViewInternalComposite navBar,JWebViewInternalComposite exportBar,JWebViewInternalComposite extraBar1,JWebViewInternalComposite extraBar2) throws Exception {
		JWebViewZoneRow row = new JWebViewZoneRow();
		JWebViewZoneRow row1 = new JWebViewZoneRow();
		row1.setClassResponsive("row");
		row1.addChild(infoBar);
		row1.addChild(navBar);
		row1.addChild(exportBar);
		row.add("nav_zone", row1);
		return row;
	}

	public JWebViewInternalComposite createInfoBar(JWebViewComposite parent, String providerName) throws Exception {
		JWebDivResponsive infoBar = new JWebDivResponsive();
		infoBar.setName(providerName+"_infobar");
		infoBar.setClassResponsive("col-lg-4 col-sm-4 col-md-4 col-xs-12 pull-md-left");
		return infoBar;
	}

	public JWebViewInternalComposite createNavigationBar(JWebViewComposite parent, String providerName) throws Exception {
		JWebDivResponsive navigationBar = new JWebDivResponsive();
		navigationBar.setName(providerName+"_navigation_bar");
		navigationBar.setClassResponsive("col-lg-7 col-sm-4 col-md-6 col-xs-12");
		return navigationBar;
	}
	public JWebViewInternalComposite createPreferenceBar(JWebViewComposite parent, String providerName) throws Exception {
		JWebDivResponsive preferenceBar = new JWebDivResponsive();
		preferenceBar.setName(providerName+"_preference_bar");
		preferenceBar.setClassResponsive("col-lg-1 col-sm-4 col-md-2 col-xs-12 pull-right");
		return preferenceBar;
	}
	public JWebViewComponent createBotonPreference(JWebViewComposite parent,String name,JWebAction action) throws Exception {
		JWebLink oToMap = new JWebLink();
		oToMap.setMode(JWebLink.MODE_BUTTON);
		oToMap.setClassResponsive("btn btn-default btn-circle ");
		oToMap.setIcon(JWebIcon.getResponsiveIcon("fa fa-cog"));
		oToMap.setWebAction(action);
		parent.addChild(name, oToMap);
		return oToMap;
	}

	public void addAtributesToFilterControls(JWebViewInternalComposite parent,JFormControlResponsive component) throws Exception {
		component.setSizeColumns(4);
		component.setFormatFields(JFormPanelResponsive.FIELD_LABEL_HORIZONTAL_RIGHT);
	}
	public JIterator<JFormControl> filtersResorted(JIterator<JFormControl> iter)  throws Exception {
		int pos=0;
		int size=0;
		JList<JFormControl> col1=JCollectionFactory.createList();
		JList<JFormControl> col2=JCollectionFactory.createList();
		JList<JFormControl> col3=JCollectionFactory.createList();
		JList<JFormControl> listaorig=JCollectionFactory.createList();
		JList<JFormControl> lista=JCollectionFactory.createList();
		while (iter.hasMoreElements()) {
			JFormControl c = iter.nextElement();
			listaorig.addElement(c);
			size++;
		}
		JIterator<JFormControl> i0= listaorig.getIterator();
		while (i0.hasMoreElements()) {
			JFormControl c = i0.nextElement();
			if (pos<(Math.ceil(size/3.0))) col1.addElement(c);
			else if (pos<(Math.ceil((size/3.0)*2))) col2.addElement(c);
			else col3.addElement(c);
			pos++;
		}
		JFormControl c1=null;
		JFormControl c2=null;
		JFormControl c3=null;
		JIterator<JFormControl> i1= col1.getIterator();
		JIterator<JFormControl> i2= col2.getIterator();
		JIterator<JFormControl> i3= col3.getIterator();
		while (i1.hasMoreElements()) {
			c1 = i1.nextElement();
			if (i2.hasMoreElements()) c2 = i2.nextElement();
			if (i3.hasMoreElements()) c3 = i3.nextElement();
			lista.addElement(c1);
			if (c2!=null) lista.addElement(c2);
			if (c3!=null) lista.addElement(c3);
			c1=c2=c3=null;
		}

		return lista.getIterator();
	}
	public JWebFilterPaneResponsive buildFormFilter(JWebWinGenericResponsive parent) throws Exception{
		JWebFilterPaneResponsive filterPanel = new JWebFilterPaneResponsive(parent);
		return filterPanel;
	}
	
	public void addAttributesToFilterForm(JWebViewInternalComposite parent) throws Exception {
		
	}
	public JWebLink getFindButton(JWebViewComposite parent,JWebAction webAction) throws Exception {
		JWebLink link = new JWebLink();
		link.setWebAction(webAction);
		link.setClassResponsive("btn btn-default btn-sm ");
		link.setCancel(false);
		link.setSkinStereotype("win_list_dofilter");
		link.setLabel("abm.button.search");
		link.setSubmit(true);
		link.setIcon(JWebIcon.getPssIcon(GuiIconos.RESPONSIVE, 15018));
		return link;
	}
	
	public JWebViewInternalComposite buildWinList(JWebWinGenericResponsive parent) throws Exception {
		JWebViewComposite zoneList = new JWebViewZone();
		zoneList.setLabelLateral(parent.getLabelLateral());

//		JWebViewComposite zonePreview;
		JWebViewComposite zoneFilter;
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
		JWebViewZone listPanel = new JWebViewZone();
		JWebViewZone previewPanel = new JWebViewZone();
		JWebViewZone filters = new JWebViewZone();
		JWebPanelResponsive previewPanelZoom = new JWebPanelResponsive();
		JWebHResponsive h4 = new JWebHResponsive(3,parent.getTitle());
		
		JWebFilterPaneResponsive filterPane=parent.createFormFiltros();
		JWebViewInternalComposite actionbarRowPanel = parent.createActionBar();
		JWebViewComposite navigationRow = parent.createNavigationBar();
		JWebViewComposite preferenceBar = parent.createPreferenceActionaBar();

	  boolean withFooter=false;
		if (parent.hasNavigationBar()&&parent.isShowFooterBar()) {
			panelFooter.addChild("nav",navigationRow);
			withFooter=true;
		}

		if (filterPane != null && !parent.isEmbedded()) { // filtro en winlist

			zoneFilter=filterPane;
	
			parent.attachDoFilterButton(filterPane,actionbarRowPanel,parent.getPresentation());
			filterPane.setClassResponsive("row rot-filter");

		} else if (filterPane != null && parent.isEmbedded()){ // filtro en solapa
			if (parent.isShowFilterBar()) { // filtro visible
				zoneFilter=filterPane;
				
				parent.attachDoFilterButton(filterPane,actionbarRowPanel,parent.getPresentation());
				filterPane.setClassResponsive("row rot-filter");

			} else {  // filtro invisible (muestro los filtros cargados, no editable)
				zoneFilter=filterPane;
				
				filterPane.setClassResponsive("row rot-filter");
				
			}
		
		} else {
			JWebDivResponsive empty=new JWebDivResponsive();
			empty.setMinHeightResponsive(25);
			parent.addChild(empty);
			zoneFilter=null;
		}
		if (parent.isInForm())
			panel.setClassResponsive("panel panel-rotinternal");
		else if (parent.isEmbedded())
			panel.setClassResponsive("panel panel-rotinternal panel-border");
		else
			panel.setClassResponsive("panel panel-primary");
		panelHeading.setClassResponsive("panel-heading clearfix");
		if (parent.isToolbarLeft())
			panelBody.setClassResponsive("panel-body with-sidebar");
		else
			panelBody.setClassResponsive("panel-body");
		if (withFooter) {
			if (parent.isEmbedded())
				panelFooter.setClassResponsive("panel panel-rotinternal");
			else
				panelFooter.setClassResponsive("panel-footer");

		}
		filterHeading.setClassResponsive("panel-heading");
		filterBody.setClassResponsive("panel-body");
		filterRowPanel.setClassResponsive("row");
		listRowPanel.setClassResponsive("row");
		
		listPanel.setClassResponsive("col-sm-12");
		panelWithPreview.setClassResponsive("panel-container col-sm-12");
//		panelWithinPreview.setClassResponsive("col-sm-12");
		panelToolbar.setClassResponsive("toolbar col-sm-12");
		panelToolbar.setHeightResponsive(80);

		listPanel.add("list", parent);
		if (!parent.isToolbarNone()) {
			if (!parent.isEmbedded()) {
				panelHeading.addChild("h4",h4); 
				if (parent.isToolbarLeft())
					panel.add("actionbar",actionbarRowPanel);
				else if (parent.isToolbarTop()||parent.isToolbarIn())
					h4.addChild("toolbar",actionbarRowPanel); 

				if (preferenceBar!=null) h4.addChild("preference",preferenceBar);

				panel.add("header", panelHeading);
			//	if (parent.getSourceAction()())
		//			createBackButton(h4, JWebActionFactory.createGoBackToQuery()).setClassResponsive("btn btn-outline btn-default btn-xs breadcrumb-button pull-right");
			}	else {
				JWebViewComponent button = parent.addButtonModeFilters(filterHeading);
				if (button!=null && button.getClassResponsive().indexOf("pull")==-1)
					button.setClassResponsive(button.getClassResponsive()+" pull-sm-right");

				panel.addChild("actionbar",actionbarRowPanel); 
			}
		} else {
			panelToolbar.setVisible(false);
			if (!(parent instanceof JWebWinGridBigDataResponsive )) panel.addChild(parent.getProviderName()+"_toolbar",panelToolbar);
			panelToolbar.add("actionbar",actionbarRowPanel);
			
		}

		if (zoneFilter!=null && !parent.isNothingToShowInFilterBar()) 
			panelBody.addChild("filter_pane_"+parent.getProviderName(), zoneFilter);
		
		if (parent.isToolbarIn())
			panelBody.addChild(parent.getProviderName()+"_toolbar",panelToolbar);
		
		panelBody.add("col", listPanel);
		panelBody.add("row", listRowPanel);
		panel.add("body", panelBody);
		panel.add("footer", panelFooter);
		zoneList.add("panel", panel);


		JWebViewZoneRow root = new JWebViewZoneRow();
		root.setName(parent.getCompletePanelName());
		root.setClassResponsive("row zone_row");

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
	public String getWidthAutocompleteColumn(JColumnaLista[] columns) throws Exception {
		if (columns.length==2) return "50%";
		if (columns.length==3) return "40%";
		if (columns.length==4) return "30%";
		if (columns.length==5) return "20%";
		if (columns.length==6) return "10%";
		return "100%";
	}
	public JWebViewInternalComposite buildWinGrid(JWebWinGenericResponsive parent) throws Exception {
		JWebViewComposite zoneList = new JWebViewZone();
		zoneList.setLabelLateral(parent.getLabelLateral());

//		JWebViewComposite zonePreview;
		JWebDivResponsive panel = new JWebDivResponsive();
		JWebDivResponsive panelBody = new JWebDivResponsive();
		JWebDivResponsive panelWithinPreview = new JWebDivResponsive();

		JWebDivResponsive filterBody = new JWebDivResponsive();
		
		JWebViewZoneRow filterRowPanel = new JWebViewZoneRow();
		JWebViewZoneRow listRowPanel = new JWebViewZoneRow();

		JWebViewZone listPanel = new JWebViewZone();
		JWebHResponsive h4 = new JWebHResponsive(3,parent.getTitle());
		
		filterRowPanel.setClassResponsive("row");
		listRowPanel.setClassResponsive("row");
		
		listPanel.add("list", parent);

		
		panelBody.add("col", listPanel);
		panelBody.add("row", listRowPanel);
		panel.add("body", panelBody);
		zoneList.add("panel", panel);


		JWebViewZoneRow root = new JWebViewZoneRow();
		root.setName(parent.getCompletePanelName());
		root.setClassResponsive("row");
		panelWithinPreview.setClassResponsive("col-sm-12");
		panelWithinPreview.addChild("col_list", zoneList);

		root.addChild(parent.getCompletePanelName()+"_panel",panelWithinPreview);
		
		return root;
	}
	
	public JWebViewComposite buildForm(JWebFormFormResponsive parent) throws Exception {
		parent.setClassResponsive("col-sm-12 panel panel-rotinternalform");

		return parent;
	}
	public JWebViewComposite buildLocalForm(JWebLocalFormResponsive parent) throws Exception {
		parent.setClassResponsive("col-sm-12 panel panel-rotinternalform");

		return parent;
	}

	public JWebViewComposite buildForm(JWebWinForm parent) throws Exception {
		JWebViewZone zone = new JWebViewZone();
		JWebDivResponsive panel = new JWebDivResponsive();
		JWebHResponsive h4 = new JWebHResponsive(3,parent.getTitle());
		JWebDivResponsive panelHeading = new JWebDivResponsive();
		JWebDivResponsive panelBody = new JWebDivResponsive();
		JWebDivResponsive panelFooter = new JWebDivResponsive();
		JWebViewZoneRow formRowPanel = new JWebViewZoneRow();
		JWebViewZone formPanel = new JWebViewZone();
		zone.setClassResponsive("col-lg-12");
		if (parent.isEmbedded()) 
			panel.setClassResponsive("panel panel-rotinternal");
		else
			panel.setClassResponsive("panel panel-primary");
		panelHeading.setClassResponsive("panel-heading clearfix");
		if (parent.isToolbarLeft())
			panelBody.setClassResponsive("panel-body with-sidebar");
		else
			panelBody.setClassResponsive("panel-body");
		formRowPanel.setClassResponsive("row");
		formPanel.setClassResponsive("col-lg-12");
		parent.setRole("form");
	
		formPanel.add("form", parent);
		formRowPanel.add("col", formPanel);
		panelBody.add("row", formRowPanel);
		if (parent.isToolbarLeft())
			panel.add("actionbar",parent.buildActionBar());
		panel.add("header", panelHeading);
		panel.add("body", panelBody);
//		if (!parent.isEmbedded()) {
//			panelFooter.setClassResponsive("panel-footer");
//			panel.add("footer", panelFooter);
//		}
		zone.add("panel", panel);
		
		panelHeading.add("h4",h4);
		if (parent.isToolbarTop())
			h4.add("actionbar",parent.buildActionBar());

		JWebViewZoneRow root = new JWebViewZoneRow();
		root.setName(parent.getTitle());
		root.setClassResponsive("row");
		root.addChild("row", zone);

		return root;
	}
	
	public boolean actionsInMore(JWebWinActionBar parent, BizAction zAction, JWebAction webAction) throws Exception {
		if (parent.isPreview()) 
			return true;
		if (webAction.isForm()) return false;
		if (zAction.getId()==BizAction.DROP) return false;
		if (webAction.isFormLov()) return false;
		if (parent.isToolbarLeft()) return false; 
		if (webAction.isCancel()) return false; 
		if (webAction.isForceSubmit()) return false; 
		if (!webAction.isWin()) return false; 
		return true;
	}
	   
	public JWebViewInternalComposite createActionBarWinList(JWebWinGenericResponsive zObjectProvider) throws Exception {
		if (zObjectProvider.isToolbarLeft())
			return createActionBarWinListLeft(zObjectProvider);
		if (zObjectProvider.isToolbarIn())
			return createActionBarWinListTop(zObjectProvider);
		return createActionBarWinListTop(zObjectProvider);
	}

	public JWebViewInternalComposite createActionBarWinListTop(JWebWinGenericResponsive zObjectProvider) throws Exception {
		JWebWinActionBar actionBar=zObjectProvider.getActionBar();
		actionBar.setClassResponsive(" pull-right toolbarrot");
		return actionBar;
	}

	public JWebViewInternalComposite createActionBarWinListLeft(JWebWinGenericResponsive zObjectProvider) throws Exception {
		JWebNavigationBarSidebar actionBar=new JWebNavigationBarSidebar();
		actionBar.setClassResponsive("nav");
		actionBar.setClassNavigation("navbar-default sidebar panelsidebar");
		return actionBar;
	}

	public JWebViewInternalComposite createActionBarForm(JWebWinForm zObjectProvider) throws Exception {
		if (zObjectProvider.isToolbarLeft())
			return createActionBarFormLeft(zObjectProvider);
		return createActionBarFormTop(zObjectProvider);
	}
	public boolean hasHistory() {
		return false;
	}
	public JWebViewInternalComposite createActionBarForm(JWebRowGridExpandResponsive zObjectProvider) throws Exception {
		JWebViewInternalComposite actionBar=zObjectProvider.getActionBar();
		actionBar.setClassResponsive("col-sm-12 pull-right");
		return actionBar;
	}
	public void createBoton(JWebWinActionBar parent,String zActionId,BizAction zAction, JWebAction webAction) throws Exception {
		String label = parent.isUpperCase()?zAction.GetDescr().toUpperCase():zAction.GetDescr();

		if (label.equals("Confirmar")) {
			label = JLanguage.translate("swaplist.save");
			webAction.setForceSubmit(true);
		}
		if (parent.getRootPanel() instanceof JWebNavigationBarSidebar) {
			if (webAction.isForceSubmit())
				label=parent.isUpperCase()?JLanguage.translate("swaplist.save").toUpperCase():JLanguage.translate("swaplist.save");
			((JWebNavigationBarSidebar)parent.getRootPanel()).addNavigationAction(null,  webAction,JWebIcon.getPssIcon(GuiIconos.RESPONSIVE,zAction.GetNroIcono()), label,  webAction.isNuevaVentana());
			return;
		}
		
		JWebLink oLink = new JWebLink();
		if (zAction.GetDescr().equals("scanner.button.online")) 
			oLink.setClassResponsive("btn btn-success btn-sm refreshonhover pull-sm-right");
		else if (zAction.GetDescr().equals("scanner.button.offline")) 
			oLink.setClassResponsive("btn btn-danger btn-sm refreshonhover pull-sm-right");
		else
			oLink.setClassResponsive("btn btn-default btn-sm pull-sm-right");
		oLink.setWebAction(webAction);
		oLink.setCancel(webAction.isCancel());
		oLink.setMode(JWebLink.MODE_BUTTONGROUP);
		oLink.setAccessKey(webAction.getKey());
		oLink.setImportant(zAction.getImportance());
		oLink.setOpensNewWindow(webAction.isNuevaVentana());
		if (zAction.getId()==BizAction.DROP && webAction.isWinList()) {
			oLink.setClassResponsive("btn btn-outline  btn-sm  btn-success pull-sm-right");
			oLink.setLabel(label);
			oLink.setSubmit(webAction.isForceSubmit());
			if (parent.isWithIcons()) 
				oLink.setIcon(JWebIcon.getPssIcon(GuiIconos.RESPONSIVE,zAction.GetNroIcono()));
		} else if (webAction.isCancel() && webAction.isForm()) {
			oLink.setClassResponsive("btn btn-danger2 btn-sm pull-sm-right"+ (webAction.isSwitcheable()? " modalchanges_switch":""));
			oLink.setLabel(label);
			if (label.toLowerCase().equals("cancelar"))
				oLink.setIcon(JWebIcon.getResponsiveIcon("fa fa-times"));
			else
				oLink.setIcon(JWebIcon.getResponsiveIcon("fa fa-reply"));
		} 
		else if (webAction.isCancel() && webAction.isWinList()) {
		//	oLink.setClassResponsive("btn btn-outline btn-link btn-like");
			oLink.setLabel("abm.button.cancel");
			oLink.setIcon(JWebIcon.getResponsiveIcon("fa fa-reply"));
		} 
		else if (webAction.isForceSubmit()) {
 			oLink.setClassResponsive("btn btn-success btn-sm pull-sm-right"+(webAction.isSwitcheable()? " modalchanges_hide":""));
			oLink.setLabel(parent.isUpperCase()?JLanguage.translate("abm.button.apply").toUpperCase():JLanguage.translate("abm.button.apply"));
			oLink.setSubmit(true);
			if (parent.isWithIcons()) oLink.setIcon(JWebIcon.getResponsiveIcon("fa fa-save"));
		} else if (zAction.getIdAction().endsWith("-winlist-refresh")) {
			oLink.setClassResponsive("btn btn-outline btn-link btn-like pull-sm-right");
			oLink.setLabel("");
			oLink.setIcon(JWebIcon.getPssIcon(GuiIconos.RESPONSIVE,zAction.GetNroIcono()));
		} else {
			if (!parent.isEmbedded()) oLink.setLabel(label);
			if (parent.isEmbedded() && parent.isWithLabels()) oLink.setLabel(label);
			if (parent.isWithIcons()) {
				oLink.setIcon(JWebIcon.getPssIcon(GuiIconos.RESPONSIVE,zAction.GetNroIcono()));
			}
		}
		parent.getRootPanel().add(zActionId, oLink);
	}
	
	public void createBotonInMore(JWebWinActionBar parent,JWebViewComposite moreButton,String idAction,BizAction zAction, JWebAction webAction) throws Exception {
		String label = parent.isUpperCase()?zAction.GetDescr().toUpperCase():zAction.GetDescr();
		if (parent.getRootPanel() instanceof JWebNavigationBarSidebar) {
			((JWebNavigationBarSidebar)parent.getRootPanel()).addNavigationAction((JWebNavigationBarGroup)moreButton, webAction,JWebIcon.getPssIcon(GuiIconos.RESPONSIVE,zAction.GetNroIcono()), label,  webAction.isNuevaVentana());
			return;
		}
		JWebLink oLink = new JWebLink();
		oLink.setWebAction(webAction);
		oLink.setClassResponsive("btn btn-default btn-sm pull-sm-right");
		oLink.setMode(JWebLink.MODE_BUTTONGROUP);
		oLink.setCancel(webAction.isCancel());
		oLink.setAccessKey(webAction.getKey());
		oLink.setImportant(zAction.getImportance());
		oLink.setOpensNewWindow(webAction.isNuevaVentana());
		if (!parent.isEmbedded()) oLink.setLabel(label);
		if (parent.isEmbedded() && parent.isWithLabels()) oLink.setLabel(label);
		if (parent.isWithIcons()) {
			oLink.setIcon(JWebIcon.getPssIcon(GuiIconos.RESPONSIVE,zAction.GetNroIcono()));
		}

		moreButton.addChild(idAction, oLink);
	}
	JWebViewDropDownButton globalMoreButton;
	
	public void customizeComponents(JFormControl control) {
		if (control instanceof JFormCheckResponsive) {
			((JFormCheckResponsive)control).setStyleOn("success");
		} else if (control instanceof JFormRadioResponsive) {
			((JFormRadioResponsive)control).setClassRadio("btncheck-success btn-sm");
		}

	}
	protected String findActionClass(JWebWinActionBar bar, BizAction action, JWebAction webAction) throws Exception {
		if (webAction.isForceSubmit()) 
			return "btn btn-success btn-sm";
		if (webAction.isCancel()) {
			if (webAction.isForm()) 
				return "btn btn-danger2 btn-sm";
			if (webAction.isWinList()) 
				return "btn btn-outline btn-link btn-like";
		}
		if (webAction.isWinListRefresh())
			return "btn btn-outline btn-link btn-like";

		if (action.getId()==BizAction.DROP && webAction.isWinList())
			return "btn btn-outline btn-success";
			
		return "btn btn-default btn-sm";
	}
	
	public JWebViewComposite createBotonMore(JWebWinActionBar parent,boolean isWinList,boolean isForm,boolean isFormLov) throws Exception {
		if (parent.getRootPanel() instanceof JWebNavigationBarSidebar) {
			return ((JWebNavigationBarSidebar)parent.getRootPanel()).addNavigationGroup(null, null, "Acciones", 0);
		}
		JWebDivResponsive div = new JWebDivResponsive(); // parche, para que cuando no haya boton de buscar no se pegue todo
		div.setMinHeightResponsive(28);
		div.setClassResponsive("btn");
		parent.getRootPanel().addChild(div);
		parent.getRootPanel().addChild(new JWebFlowBreak().setWithLine(true).setClassResponsive("minipadding"));
		return parent;
	}

	public JWebViewInternalComposite createActionBarFormTop(JWebWinForm zObjectProvider) throws Exception {
		JWebViewInternalComposite actionBar=zObjectProvider.getActionBar();
		actionBar.setClassResponsive(" pull-right");
		return actionBar;
	}
	public JWebViewInternalComposite createActionBarFormLeft(JWebWinForm zObjectProvider) throws Exception {
		JWebNavigationBarSidebar actionBar=new JWebNavigationBarSidebar();
		actionBar.setClassResponsive("nav");
		actionBar.setClassNavigation("navbar-default sidebar panelsidebar");
		return actionBar;
	}
	
	public JWebViewInternalComposite createActionBarFormLov(JWebWinListFormLov zObjectProvider, boolean zForForm) throws Exception {
		JWebWinActionBar actionBar=zObjectProvider.getActionBar();
		actionBar.setClassResponsive(" pull-right");
		return actionBar;
	}


	public JWebViewInternalComposite createExportBar(JWebViewComposite parent, String providerName) throws Exception {
		JWebDivResponsive exportBar = new JWebDivResponsive();
		exportBar.setName(providerName+"_export_bar");
		exportBar.setClassResponsive("col-sm-1 paddingleft-0 pull-right");
		return exportBar;
	}
	
	public JWebNavigationBarGroup addNavigationGroup(JWebView parent,JWebNavigationBarGroup group, JWebIcon zIcon,String name, long level) throws Exception {
		return parent.addNavigationGroup(group,zIcon,name,level);
	}
	public void addNavigationAction(JWebView parent,JWebNavigationBarGroup group, String zAction, JWebIcon zIcon, String label, boolean newwindow)	throws Exception {
		parent.addNavigationAction(group,zAction,zIcon,label, newwindow);
	}
	public void addNavigationAction(JWebView parent,JWebNavigationBarGroup group, JWebAction zAction, JWebIcon zIcon, String label, boolean newwindow)	throws Exception {
		parent.addNavigationAction(group,zAction,zIcon,label, newwindow);
	}

	public JWebViewNavigationBar generateMenu(JWebView parent,JWebViewNavigationBar menu,BizActions actions) throws Exception {
		if ( menu==null || actions == null ) return null;
		long maxMenu = JCompanyBusiness.getDefinicionesNumber(JCompanyBusiness.MAXMENUHORIZONTAL);
		JIterator<BizAction> iter=actions.getStaticIterator();
		while (iter.hasMoreElements()) {
			BizAction action=iter.nextElement();
			if (!action.ifMenu()) continue;
			if (action.hasSubActions()) {
				if (BizUsuario.getUsr().getVisionCustomMenu()!=null&&action.GetSubAcciones().getStaticItems().size()<maxMenu) {
					generateSubMenu(parent,null,action.GetSubAcciones(),0);
				} else {
					JWebNavigationBarGroup group= addNavigationGroup(parent,null, JWebIcon.getPssIcon(GuiIconos.RESPONSIVE,action.GetNroIcono()),action.GetDescr(),1);
					generateSubMenu(parent,group,action.GetSubAcciones(),1);
				}
			}
			else {
				if (action.isNuevaSession()) {
					addNavigationAction(parent,null,JWebActionFactory.createNewSession(parent.getUICoordinator().getSession(),action.getIdAction()) ,JWebIcon.getPssIcon(GuiIconos.RESPONSIVE,action.GetNroIcono()),action.GetDescr(),action.isNuevaVentana(), action.hasConfirmMessage()?action.getConfirmMessageDescription():null);
				} else {
					addNavigationAction(parent,null,JWebActionFactory.idActionToURL(action.getIdAction()) ,JWebIcon.getPssIcon(GuiIconos.RESPONSIVE,action.GetNroIcono()),action.GetDescr(),action.isNuevaVentana(), action.hasConfirmMessage()?action.getConfirmMessageDescription():null);
					
				}
			}
		}
		return menu;
	}

	
	public void generateSubMenu(JWebView parent,JWebNavigationBarGroup group,BizActions actions,long level) throws Exception {
		if ( actions == null ) return;
		
		JIterator<BizAction> iter=actions.getStaticIterator();
		while (iter.hasMoreElements()) {
			BizAction action=iter.nextElement();
			if (!action.ifMenu()) continue;
			if (action.hasSubActions()) { 
				JWebNavigationBarGroup sgroup =  this.addNavigationGroup(parent,group, JWebIcon.getPssIcon(GuiIconos.RESPONSIVE,action.GetNroIcono()),action.GetDescr(),level+1);
				this.generateSubMenu(parent,sgroup,action.GetSubAcciones(),level+1);
			}
			else if (action.isNuevaSession()) {
				this.addNavigationAction(parent,group,JWebActionFactory.createNewSession(parent.getUICoordinator().getSession(),action.getIdAction()) ,JWebIcon.getPssIcon(GuiIconos.RESPONSIVE,action.GetNroIcono()),action.GetDescr(),action.isNuevaVentana(), action.hasConfirmMessage()?action.getConfirmMessageDescription():null);
			} else {
				this.addNavigationAction(parent,group,JWebActionFactory.idActionToURL(action.getIdAction()) ,JWebIcon.getPssIcon(GuiIconos.RESPONSIVE,action.GetNroIcono()),action.GetDescr(),action.isNuevaVentana(), action.hasConfirmMessage()?action.getConfirmMessageDescription():null);
			}
		}
	}
	
	public boolean isFilterInLine() throws Exception {
		return false;
	}
	
	public JWebViewComponent createBotonExport(JWebViewComposite parent, String name, JWebAction action) throws Exception {

		JWebLink oToMap = new JWebLink();
		oToMap.setOpensNewWindow(true);
		if (name.indexOf("excel") != -1)
			oToMap.setIcon(JWebIcon.getResponsiveIcon("fa fa-file-excel fa-1x"));
		else if (name.indexOf("map") != -1)
			oToMap.setIcon(JWebIcon.getResponsiveIcon("fa fa-globe fa-1x"));
		else if (name.indexOf("csv") != -1)
			oToMap.setIcon(JWebIcon.getResponsiveIcon("fa fa-file-alt fa-1x"));
		else if (name.indexOf("pdf") != -1)
			oToMap.setIcon(JWebIcon.getResponsiveIcon("fa fa-file-pdf fa-1x"));
		else if (name.indexOf("graph") != -1)
			oToMap.setIcon(JWebIcon.getResponsiveIcon("fa fa-chart-area fa-1x"));

		if (name.indexOf("excel") != -1)
			oToMap.setLabel("XLS");
		else if (name.indexOf("map") != -1)
			oToMap.setLabel("MAP");
		else if (name.indexOf("csv") != -1)
			oToMap.setLabel("CSV");
		else if (name.indexOf("pdf") != -1)
			oToMap.setLabel("PDF");
		else if (name.indexOf("graph") != -1)
			oToMap.setLabel("IMG");
		oToMap.setWebAction(action);
		JWebElementList li = new JWebElementList();
		li.addChild(name+"_button", oToMap);

		parent.addChild(name, li);
		return oToMap;
	}

	public JWebViewComponent createBotonPagination(JWebViewComposite parent, String name, JWebAction action, String label, boolean active) throws Exception {
		JWebElementList el = new JWebElementList();
		JWebLink oButton = new JWebLink();
		oButton.setMode(JWebLink.MODE_BUTTON);
		if (label.equalsIgnoreCase("siguiente"))
			oButton.setLabel(JLanguage.translate("form.setup.page.next"));
		else if (label.equalsIgnoreCase("anterior"))
			oButton.setLabel(JLanguage.translate("form.setup.page.prev"));
		else
			oButton.setLabel(label);
		oButton.setWebAction(action);
		if (active)
			oButton.setClassResponsive("btn-default");

		el.addChild(oButton);
		parent.addChild(name, el);
		return el;
	}

	public JWebViewComponent createCombo(JWebViewComposite parent, String name, String label, boolean active, JWebAction webAction, String provider, String defa, JControlCombo combo) throws Exception {
		JWebComboResponsive oSelect = new JWebComboResponsive();
		oSelect.setController(new JString(), JLanguage.translate(label.equals("#Lineas")?"abm.sizepage.label":label), false, 200, null, combo);
		oSelect.setRefreshForm(true);
		oSelect.setAction(webAction);
		oSelect.setNoForm(true);
		oSelect.setValue(defa);
		oSelect.setSizeResponsive("inline_component");
		oSelect.setProvider(provider);
		// oSelect.setClassResponsive("col-sm-1");
		parent.addChild(name, oSelect);
		return oSelect;
	}

	public JWebViewComponent createHideShow(JWebViewComposite parent, String name, boolean state, JWebAction action, String label, String dataon, String dataoff) throws Exception {

		JWebCheckResponsive oHideShow = new JWebCheckResponsive();
		oHideShow.setMode(JFormCheckResponsive.MODE_TOGGLE);
		String on = dataon;
		String off = dataoff;
		String l =label; 
		if (label.equalsIgnoreCase("vista previa")) {
			on = "On";
			off = "Off";
			l = "form.setup.preview";
		} else if (label.equalsIgnoreCase("filtros")) {
			on = "On";
			off = "Off";
			l = "form.setup.filters";
		}
		oHideShow.setDataon(on);
		oHideShow.setDataoff(off);
		oHideShow.setLabel(l);
		oHideShow.setDatasize(JFormCheckResponsive.SIZE_MINI);
		oHideShow.setDataonstyle("success");
		oHideShow.setWebAction(action);
		oHideShow.setValue(state);
		oHideShow.setClassResponsive("label_checkbox");// checkbox-inline");
		oHideShow.setNoForm(true);
		parent.addChild(name, oHideShow);
		return oHideShow;
	}
	public boolean attachBackActionToToolbar(String zone) {
		if (zone.equals("history_bar")) return true;
		if (zone.equals("swap")) return true;
		if (zone.equals("form")) return true;
		if (zone.equals("win_list")) return true;
		return false;
	}
	public BizAction createActionCancel(JWebActionOwnerProvider zObjectProvider) throws Exception {
		BizAction a = new BizAction();
		a.pDescr.setValue("abm.button.cancel");
		a.pNroIcono.setValue(GuiIcon.CANCELAR_ICON);
		a.SetIdAction("action-cancel");
		return a;
	}
	public BizAction createActionBack(JWebActionOwnerProvider zObjectProvider) throws Exception {
		BizAction a = new BizAction();
		a.pDescr.setValue("abm.button.back");
		a.pNroIcono.setValue(GuiIcon.RETURN_ICON);
		a.SetIdAction((zObjectProvider==null?"":zObjectProvider.getProviderName()+"-")+"action-volver");
		return a;
	}
	public String getMessageConfirmLostData() throws Exception {
		return JLanguage.translate("abm.ask.abandon.changes");
	}
	public String getMessageConfirm(String msgDefa,String extra) throws Exception {
		if (msgDefa!=null && !msgDefa.equals("Esta Ud. seguro?")) return JLanguage.translate( msgDefa );
		Object params[]= { JLanguage.translate(extra)};
		return JLanguage.translate( "abm.action.confirmation",params);
	}
  public JMap<String,String> getPaginations(long maxSize) throws Exception {
    JMap<String,String> sizes = JCollectionFactory.createOrderedMap();
    if (maxSize==-1 || maxSize>10) sizes.addElement("10", "10");
    if (maxSize==-1 || maxSize>25) sizes.addElement("30", "30");
    if (maxSize==-1 || maxSize>50) sizes.addElement("50", "50");
    if (maxSize==-1 || maxSize>100) sizes.addElement("100", "100");
    if (maxSize==-1 || maxSize>200) sizes.addElement("200", "200");
    if (maxSize==-1) {
    	sizes.addElement("50000", JLanguage.translate("form.setup.page.all"));
    } else
    	sizes.addElement(""+maxSize, ""+maxSize);
    return sizes;
  }
  
  public String getMessage(String zMsg, Object[] zParams)  {
  		if (zMsg.toLowerCase().length()>9 && zMsg.toLowerCase().substring(0, 9).equals("clave inv")) 
  			zMsg = "form.wrong.password";
  		else if (zMsg.indexOf("Información")!=-1) 
  			zMsg = "form.title.information";
   		else if (zMsg.equalsIgnoreCase("error")) 
  			zMsg = "form.title.error";
  		else if (zMsg.indexOf("Atrás")!=-1) 
  			zMsg = "form.button.back";
   		else if (zMsg.equalsIgnoreCase("ingresar")) 
  			zMsg = "form.login.button.confirm";
   		else if (zMsg.equalsIgnoreCase("Usuario")) 
  			zMsg = "form.login.user";
   		else if (zMsg.indexOf("refine la busqueda")!=-1) 
  			zMsg = "form.use.filters";
   		else if (zMsg.equalsIgnoreCase("Password")) 
  			zMsg = "form.login.pass";
   		else if (zMsg.indexOf("confirm")==0) 
  			zMsg = "form.login.title";
   		else if (zMsg.equalsIgnoreCase("Inicio de sesión")) 
  			zMsg = "form.login.session";
  		else if (zMsg.equalsIgnoreCase("si")) 
  			zMsg = "form.checkbox.yes";
  		else if (zMsg.equalsIgnoreCase("todos")) 
  			zMsg = "form.checkbox.all";
  		else if (zMsg.equalsIgnoreCase("no")) 
  			zMsg = "form.checkbox.no";
   		else if (zMsg.equalsIgnoreCase("Mensajeria")) 
  			zMsg = "navbar.message";
   		else if (zMsg.equalsIgnoreCase("Alertas")) 
  			zMsg = "navbar.alert";
   		else if (zMsg.equalsIgnoreCase("Config")) 
  			zMsg = "navbar.configr";
   		else if (zMsg.equalsIgnoreCase("Preferencias")) 
  			zMsg = "navbar.preference";
   		else if (zMsg.equalsIgnoreCase("Cambiar clave")) 
  			zMsg = "navbar.changepassword";
   		else if (zMsg.equalsIgnoreCase("Cerrar sesión")) 
  			zMsg = "navbar.closesession";
   		else if (zMsg.equalsIgnoreCase("Multiples usuarios, la sesion ha caducado, debe reingresar su usuario y clave")) 
  			zMsg = "navbar.multipleusers";
   		else if (zMsg.equalsIgnoreCase("La sesion ha expirado, debe reingresar su usuario y clave")) 
  			zMsg = "navbar.expiresession";
   		else if (zMsg.equalsIgnoreCase("Ayuda")) 
  			zMsg = "navbar.help";
   		else if (zMsg.equalsIgnoreCase("Usuario pref.")) 
  			zMsg = "pref.user";
   		else if (zMsg.equalsIgnoreCase("Skin")) 
  			zMsg = "pref.skin";
   		else if (zMsg.equalsIgnoreCase("Página Inicial")) 
  			zMsg = "pref.paginitial";
   		else if (zMsg.equalsIgnoreCase("Líneas por pág.")) 
  			zMsg = "pref.sizepage";
   		else if (zMsg.equalsIgnoreCase("Max.Reg.Matriz")) 
  			zMsg = "pref.maxreg";
   		else if (zMsg.equalsIgnoreCase("Usuario Inválido")) 
  			zMsg = "form.login.invaliduser";
   		else if (zMsg.equalsIgnoreCase("Session cerrada")) 
  			zMsg = "navbar.closesession";
   		else if (zMsg.equalsIgnoreCase("Cerrar ventana")) 
  			zMsg = "str.close";
   		else if (zMsg.equalsIgnoreCase("Hoy")) 
  			zMsg = "date.today";
   		else if (zMsg.equalsIgnoreCase("Ayer")) 
  			zMsg = "date.yesterday";
   		else if (zMsg.equalsIgnoreCase("Los últimos 7 días")) 
  			zMsg = "date.lastweek";
   		else if (zMsg.equalsIgnoreCase("Los últimos 30 días")) 
  			zMsg = "date.lastdays";
   		else if (zMsg.equalsIgnoreCase("Este mes")) 
  			zMsg = "date.month";
  		else if (zMsg.equalsIgnoreCase("Fecha inválida")) 
  			zMsg = "msg.invalid.date";
  		else if (zMsg.equalsIgnoreCase("Hora inválida")) 
  			zMsg = "msg.invalid.time";
  		else if (zMsg.equalsIgnoreCase("Anterior mes")) 
  			zMsg = "date.lastmonth";
   		else if (zMsg.equalsIgnoreCase("Este Año")) 
  			zMsg = "date.year";
   		else if (zMsg.equalsIgnoreCase("Enero")) 
  			zMsg = "str.january";
   		else if (zMsg.equalsIgnoreCase("Febrero")) 
  			zMsg = "str.february";
   		else if (zMsg.equalsIgnoreCase("Marzo")) 
  			zMsg = "str.march";
   		else if (zMsg.equalsIgnoreCase("Abril")) 
  			zMsg = "str.april";
   		else if (zMsg.equalsIgnoreCase("Mayo")) 
  			zMsg = "str.may";
   		else if (zMsg.equalsIgnoreCase("Junio")) 
  			zMsg = "str.june";
   		else if (zMsg.equalsIgnoreCase("Julio")) 
  			zMsg = "str.july";
   		else if (zMsg.equalsIgnoreCase("Agosto")) 
  			zMsg = "str.august";
   		else if (zMsg.equalsIgnoreCase("Septiembre")) 
  			zMsg = "str.september";
   		else if (zMsg.equalsIgnoreCase("Octubre")) 
  			zMsg = "str.october";
   		else if (zMsg.equalsIgnoreCase("Noviembre")) 
  			zMsg = "str.november";
   		else if (zMsg.equalsIgnoreCase("Diciembre")) 
  			zMsg = "str.december";
  		else if (zMsg.equalsIgnoreCase("Lunes")) 
  			zMsg = "str.monday";
  		else if (zMsg.equalsIgnoreCase("Martes")) 
  			zMsg = "str.tuesday";
  		else if (zMsg.equalsIgnoreCase("Miercoles")) 
  			zMsg = "str.wednesday";
  		else if (zMsg.equalsIgnoreCase("Jueves")) 
  			zMsg = "str.thursday";
  		else if (zMsg.equalsIgnoreCase("Virnes")) 
  			zMsg = "str.friday";
  		else if (zMsg.equalsIgnoreCase("Sabado")) 
  			zMsg = "str.saturday";
  		else if (zMsg.equalsIgnoreCase("Domingo")) 
  			zMsg = "str.sunday";
  		else if (zMsg.equals("Lu")) 
  			zMsg = "str.monday.mini";
  		else if (zMsg.equals("Ma")) 
  			zMsg = "str.tuesday.mini";
  		else if (zMsg.equals("Mi")) 
  			zMsg = "str.wednesday.mini";
  		else if (zMsg.equals("Ju")) 
  			zMsg = "str.thursday.mini";
  		else if (zMsg.equals("Vi")) 
  			zMsg = "str.friday.mini";
  		else if (zMsg.equals("Sa")) 
  			zMsg = "str.saturday.mini";
  		else if (zMsg.equals("Do")) 
  			zMsg = "str.sunday.mini";
   		else if (zMsg.equalsIgnoreCase("Aplicar")) 
  			zMsg = "swaplist.save";
   		else if (zMsg.equalsIgnoreCase("Cancelar")) 
  			zMsg = "swaplist.cancel";
   		else if (zMsg.equalsIgnoreCase("Desde")) 
  			zMsg = "reports.label.from";
   		else if (zMsg.equalsIgnoreCase("Hasta")) 
  			zMsg = "reports.label.to";
   		else if (zMsg.equalsIgnoreCase("Custom")) 
  			zMsg = "str.custom";
   		else if (zMsg.equalsIgnoreCase("Otros")) 
  			zMsg = "msg.others";
   		else if (zMsg.equalsIgnoreCase("Sistema info")) 
  			zMsg = "msg.system.info";
  		else if (zMsg.equalsIgnoreCase("Error de la aplicación")) 
  			zMsg = "form.title.error";
  		else if (zMsg.equalsIgnoreCase("Cancelado por el usuario")) 
  			zMsg = "form.cancel.usaer";
  		else if (zMsg.equalsIgnoreCase("Confirmar")) 
  			zMsg = "swaplist.save";
  		
  		

  		if (zParams==null)
  			return JLanguage.translate(zMsg);
			return JLanguage.translate(zMsg,zParams);
  	

  }
  
  

}