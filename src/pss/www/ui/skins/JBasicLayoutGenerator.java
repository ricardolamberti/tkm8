package pss.www.ui.skins;

import pss.JPath;
import pss.common.regions.company.JCompanyBusiness;
import pss.common.regions.multilanguage.JLanguage;
import pss.common.security.BizUsuario;
import pss.core.data.BizPssConfig;
import pss.core.services.fields.JString;
import pss.core.tools.JTools;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JMap;
import pss.core.win.JBaseWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.actions.BizActions;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.icons.GuiIcon;
import pss.core.winUI.icons.GuiIconos;
import pss.core.winUI.lists.JColumnaLista;
import pss.core.winUI.lists.JWinList;
import pss.core.winUI.responsiveControls.JFormCheckResponsive;
import pss.core.winUI.responsiveControls.JFormControlResponsive;
import pss.core.winUI.responsiveControls.JFormWinLOVResponsive;
import pss.www.platform.actions.JWebAction;
import pss.www.platform.actions.JWebActionFactory;
import pss.www.platform.applications.JWebApplicationSession;
import pss.www.ui.JWebActionOwnerProvider;
import pss.www.ui.JWebCheckResponsive;
import pss.www.ui.JWebCollapseResponsive;
import pss.www.ui.JWebComboResponsive;
import pss.www.ui.JWebDivResponsive;
import pss.www.ui.JWebDropDownComboResponsive;
import pss.www.ui.JWebDropDownWinLovResponsive;
import pss.www.ui.JWebEditComponentContainer;
import pss.www.ui.JWebElementList;
import pss.www.ui.JWebFilterPaneResponsive;
import pss.www.ui.JWebFormFormResponsive;
import pss.www.ui.JWebHResponsive;
import pss.www.ui.JWebHelp;
import pss.www.ui.JWebIcon;
import pss.www.ui.JWebLabel;
import pss.www.ui.JWebLabelResponsive;
import pss.www.ui.JWebLink;
import pss.www.ui.JWebLocalFormResponsive;
import pss.www.ui.JWebNavigationBarGroup;
import pss.www.ui.JWebNavigationBarSidebar;
import pss.www.ui.JWebNavigationBarTop;
import pss.www.ui.JWebPanelResponsive;
import pss.www.ui.JWebRowGridExpandResponsive;
import pss.www.ui.JWebSplit;
import pss.www.ui.JWebTableExpandResponsive;
import pss.www.ui.JWebTableResponsive;
import pss.www.ui.JWebUnsortedList;
import pss.www.ui.JWebView;
import pss.www.ui.JWebViewComponent;
import pss.www.ui.JWebViewComposite;
import pss.www.ui.JWebViewDropDownButton;
import pss.www.ui.JWebViewHistoryBar;
import pss.www.ui.JWebViewInternalComposite;
import pss.www.ui.JWebViewNavigationBar;
import pss.www.ui.JWebViewNavigationResponsive;
import pss.www.ui.JWebViewZone;
import pss.www.ui.JWebViewZoneRow;
import pss.www.ui.JWebWinActionBar;
import pss.www.ui.JWebWinActionBarSwapList;
import pss.www.ui.JWebWinForm;
import pss.www.ui.JWebWinGenericResponsive;
import pss.www.ui.JWebWinListFormLov;
import pss.www.ui.JWebWinOptionsResponsive;
import pss.www.ui.processing.JXMLRepresentable;


public abstract class JBasicLayoutGenerator  implements ILayoutGenerator {
	private static final int INDOORS_APPLICATION_BAR_HEIGHT=20;
	private static final int INDOORS_VIEW_TITLE_HEIGHT=23;
	private static final int INDOORS_VIEW_SEP_TITLE_HEIGHT=0;
	private static final int INDOORS_INFO_BAR_HEIGHT=30;
	private static final int INDOORS_TREE_AREA_WIDTH=181;
	private static final int INDOORS_NAVIGATION_WIDHT=475;
	private static final int INDOORS_NAVIGATION_HEIGHT=22;
	private static final int INDOORS_HISTORY_WIDHT=675;
	private static final int INDOORS_HISTORY_HEIGHT=25;
	private static final int INDOORS_VIEWAREA_TOP=INDOORS_VIEW_TITLE_HEIGHT;
	private static final int INDOORS_WEBWINFORM_BORDER=0;
	private static final int INDOORS_TREEAREA_TOP=105;
	private static final int INDOORS_SPLIT_POINT=110;
	private static final int INDOORS_SPLIT_POINT_EMBEDDED=80;
	private static final int INDOORS_POSY_TITLE= INDOORS_VIEW_SEP_TITLE_HEIGHT+INDOORS_NAVIGATION_HEIGHT;
	private static final int INDOORS_VIEW_TITLE_WIDTH= 478;
		
	private static final int FRONTDOOR_APPLICATION_BAR_HEIGHT=64;
	private static final int FRONTDOOR_INFO_BAR_HEIGHT=28;
	private static final int FRONTDOOR_VIEW_TITLE_HEIGHT=23;
	private static final int FRONTDOOR_TREEAREA_WIDTH=0;
	private static final int FRONTDOOR_TREEAREA_TOP=103;
	private static final int FRONTDOOR_BOTTOM_MARGIN_CANVAS=30;
	private static final int FRONTDOOR_RIGHT_MARGIN_CANVAS=0;

	
	public String getExpandAllSubDetail() {
		return "images/tree/ftv2folderopen.gif";
	}
	public String getMoreSubDetail() {
		return "images/tree/ftv2folderopen.gif";
	}

	public long getSizeIconTitle() {
		return GuiIconos.SIZE_DEFA;
	}
	public long getSizeIconNavigationBar(){
		return GuiIconos.SIZE_DEFA;
	}
	public long getSizeIconHistoryBar(){
		return GuiIconos.SIZE_DEFA;
	}
	public long getSizeIconActionBar(boolean bToolbarTop,boolean isWin, boolean bEmbbeded) {
		return GuiIconos.SIZE_DEFA;
	}
	public long getSizeMenu() {
		return GuiIconos.SIZE_DEFA;
	}
	public long getSizeIconNotebook() {
		return GuiIconos.SIZE_DEFA;
	}
	public int getGapWinActionBarTopH() {return 0;}
	public int getGapWinActionBarTopV(){return 0;}
	public int getGapWinActionBarLeftH(){return 0;}
	public int getGapWinActionBarLeftV(){return 0;}

	public long getSizeWindowFormLovHeight() {
		return 405;
	}
	public long getSizeWindowFormLovWidth() {
		return 905;
	}
	public int getMargenCanvasHeight(){
		return 0;
	}
	public int getMargenCanvasWidth(){
		return 0;
	}
	public boolean hasHistory() {
		return true;
	}
	public boolean attachBackActionToToolbar(String zone) {
		if (zone.equals("history_bar")) return true;
		if (zone.equals("swap")) return true;
		return true;
	}

	public int getRightMarginCanvas() throws Exception {
		return 0;
	}

	public boolean isActionWithIcons(String pos, boolean embedded) {
		return true;
	}

	public boolean isActionUpperCase(String pos, boolean embedded) {
		return false;
	}
	
	public boolean isActionWithLabels(String pos, boolean embedded) {
		return true;
	}

	public void customizeComponents(JFormControl control) {
	}
	
	public boolean useSimpleOrden(JWebWinGenericResponsive parent) throws Exception {
		return !parent.isFlat();
	}



	public void eventInterfaz(String componente, String mensaje, long percent, long total, boolean cancel, String icon) throws Exception {
		JWebApplicationSession.sendWaitMessage(icon, percent!=-1, true, percent, total, JLanguage.translate(mensaje));
	}
	public void clearEventInterfaz() throws Exception {
		JWebApplicationSession.clearWaitMessage();
	}


	public int getBorderWebWinForm() {
		return INDOORS_WEBWINFORM_BORDER;
	}

	private boolean withTree() throws Exception {
		return BizPssConfig.getPssConfig().withTree();
	}
	

	public void setPositionHelp(JWebHelp help) throws Exception {
		if (withTree()) {
			help.setLocation(INDOORS_VIEW_TITLE_WIDTH+5,INDOORS_APPLICATION_BAR_HEIGHT+2);
			help.setSize(17, 16);
			help.setSkinStereotype("help");
		} else {
			help.setLocation( INDOORS_VIEW_TITLE_WIDTH+5,INDOORS_APPLICATION_BAR_HEIGHT+2);
			help.setSize(17, 16);
			help.setSkinStereotype("help_embedded");
		}
		help.setPosition("absolute");
	}



	public int getRelativeNavigationBar() throws Exception {
		if (withTree())
			return 0;
		return INDOORS_TREE_AREA_WIDTH;
	}
	public int getSplitPoint() {
		return INDOORS_SPLIT_POINT;
	}
	public int getSplitPointEmbedded() {
		return INDOORS_SPLIT_POINT_EMBEDDED;
	}

	public int getTopMarginCanvas() throws Exception {
		return INDOORS_VIEW_SEP_TITLE_HEIGHT+INDOORS_APPLICATION_BAR_HEIGHT+INDOORS_NAVIGATION_HEIGHT+INDOORS_VIEW_TITLE_HEIGHT;
	}

	public int getBottomMarginCanvas() throws Exception {
		return FRONTDOOR_BOTTOM_MARGIN_CANVAS;
	}


	public int getLeftMarginCanvas() throws Exception {
		if (withTree())
			return INDOORS_TREE_AREA_WIDTH;
		return 0;
	}




	public int getNotebooksOffset() {
		return 2;
	}

	public boolean needOldCss() throws Exception {
		return false;
	}
	

	public String getClassField(JWebViewComponent componente,JWebEditComponentContainer form,String force)  throws Exception {
		if (force!=null && !force.equals("")) return force;
		if (!componente.isLabelHorizontal()) return force;
		return getClassFieldHorizontal(componente, form);
	}
	
	public String getClassLabel(JWebViewComponent componente,JWebEditComponentContainer form,String force)  throws Exception {
		if (!componente.isLabelHorizontal()) return force;
		String claseh = this.getClassLabelHorizontal(componente, form);
		return claseh + " " + ((force==null)?"":force); 
	}
		
	public String getColumnClass(JFormControlResponsive control,String strColumns) {
		String cls="";
		long columns = JTools.getLongFirstNumberEmbedded(strColumns);
		if (columns>=12)
			cls+="col-sm-12 col-xs-12";
		else if (columns>=11)
			cls+="col-xl-"+columns+" col-lg-11 col-md-10 col-sm-12 col-xs-12";
		else if (columns>=10)
			cls+="col-xl-"+columns+" col-lg-10 col-md-9 col-sm-12 col-xs-12";
		else if (columns>=9)
			cls+="col-xl-"+columns+" col-lg-9 col-md-9 col-sm-12 col-xs-12";
		else if (columns>=8)
			cls+="col-xl-"+columns+" col-lg-8 col-md-8 col-sm-12 col-xs-12";
		else if (columns>=7)
			cls+="col-xl-"+columns+" col-lg-7 col-md-6 col-sm-12 col-xs-12";
		else if (columns>=6)
			cls+="col-xl-"+columns+" col-lg-6 col-md-6 col-sm-12 col-xs-12";
		else if (columns>=5)
			cls+="col-xl-"+columns+" col-lg-5 col-md-6 col-sm-12 col-xs-12";
		else if (columns>=4)
			cls+="col-xl-"+columns+" col-lg-4 col-md-4 col-sm-12 col-xs-12";
		else if (columns>=3)
			cls+="col-xl-"+columns+" col-lg-3 col-md-3 col-sm-12 col-xs-12";
		else if (columns>=2)
			cls+="col-xl-"+columns+" col-lg-2 col-md-3 col-sm-12 col-xs-12";
		else if (columns>=2)
			cls+="col-xl-"+columns+" col-lg-2 col-md-2 col-sm-12 col-xs-12";
		else if (columns>=1)
			cls+="col-xl-"+columns+" col-lg-1 col-md-2 col-sm-12 col-xs-12";
		else 
			cls+="col-xl-"+columns;
		return cls;
	}	
	public String getClassLinkLabel(JWebViewComponent componente,JWebEditComponentContainer form,String force)  throws Exception {
		if (force!=null && !force.equals("")) return force;
		if (componente instanceof JWebCheckResponsive) return force;

//		if (form!=null && form.isInLine())
//			return "sr-only";
//		else 
		return force;
	}
	
	public String getClassFieldHorizontal(JWebViewComponent componente,JWebEditComponentContainer form)  throws Exception {
		if (form!=null && form.isInLine()) return null;
		if (!componente.hasLabelInfo()) return "";
		if (!componente.hasLabel()) return null;
		return "col-xs-"+(12-componente.getSizeLabels());
	}
	
	public String getClassLabelHorizontal(JWebViewComponent componente,JWebEditComponentContainer form)  throws Exception {
		if (form!=null && form.isInLine()) return "";
		if (!componente.hasLabelInfo()) return "";
		return "col-xs-"+componente.getSizeLabels()+ (componente.isLabelRight()? " text-right":componente.isLabelCenter()?" text-center":" text-left");
	}


	public String[] getColorGraph() {
		String[] colorMap= { "#a6cee3","#1f78b4","#b2df8a","#33a02c","#fb9a99","#e31a1c","#fdbf6f","#ff7f00","#cab2d6","#6a3d9a","#ffff99","#b15928","#8dd3c7","#ffffb3","#bebada","#fb8072","#80b1d3","#fdb462","#b3de69","#fccde5","#d9d9d9","#bc80bd","#ccebc5","#ffed6f","#1b9e77","#d95f02","#7570b3","#e7298a","#66a61e","#e6ab02","#a6761d","#666666","#fbb4ae","#b3cde3","#ccebc5","#decbe4","#fed9a6","#ffffcc","#e5d8bd","#fddaec","#f2f2f2" };
		return colorMap;
	}

	public JWebViewComponent getUserMessage(JWebViewComposite parent,String message) throws Exception {
		JWebLabel oLabel=new JWebLabel();
		oLabel.setParent(parent);
		oLabel.setLabel(message);
		return oLabel;
	}
	
	public String menuPosPrincipal()  throws Exception {
		return "TOP";
//		return "SIDEBAR";
  }
	public JWebViewNavigationBar getNavBar(JWebViewComposite parent) throws Exception {
		JWebViewNavigationResponsive n=new JWebViewNavigationResponsive();
		configuereBars(n,  menuPosPrincipal());
		n.buildNav("NAVBAR");
		n.setParent(parent);
		return n;
	}

	public String findLogoMenu() throws Exception {
		String logo = BizUsuario.getUsr().getObjCompany().getLogo();
		if (logo==null) return null;
		return JPath.PssPathLogos()+BizUsuario.getUsr().getObjCompany().getLogo();
	}
	
	public void buildHeaderMenu(JWebView parent,JWebViewNavigationBar menu) throws Exception {
		if (menu==null || !menu.isNavBarRight()) return;
		String logo = this.findLogoMenu();
		parent.addLogoMenu(JWebIcon.getPssDataResource(logo), "width:100%;");
	}
	public void buildOption(JWebView parent,JWebViewNavigationBar menu) throws Exception {
		if (menu==null || !menu.isNavBarRight()) return;
		String logo = BizUsuario.getUsr().getObjCompany().getLogo();
		String logoPath=logo==null?null:JPath.PssPathLogos()+BizUsuario.getUsr().getObjCompany().getLogo();
		parent.addLogoMenu(JWebIcon.getPssDataResource(logoPath), "width:100%;");
	}

	public JWebViewNavigationBar generateMenu(JWebView parent,JWebViewNavigationBar menu,BizActions actions) throws Exception {
		if ( menu==null || actions == null ) return null;
		JIterator<BizAction> iter=actions.getStaticIterator();
		while (iter.hasMoreElements()) {
			BizAction action=iter.nextElement();
			if (!action.ifMenu()) continue;
			JWebIcon icon = this.findMenuIcon("menu", action);
			if (action.hasSubActions()) {
				JWebNavigationBarGroup group= addNavigationGroup(parent,null, icon,action.GetDescr(),1);
				generateSubMenu(parent,group,action.GetSubAcciones(),1);
			}
			else { 
				if (action.isNuevaSession()) {
					addNavigationAction(parent,null,JWebActionFactory.createNewSession(parent.getUICoordinator().getSession(),action.getIdAction()), icon,action.GetDescr(),action.isNuevaVentana(), action.hasConfirmMessage()? action.getConfirmMessageDescription():null);
				} else {
					addNavigationAction(parent,null,JWebActionFactory.idActionToURL(action.getIdAction()), icon,action.GetDescr(),action.isNuevaVentana(), action.hasConfirmMessage()? action.getConfirmMessageDescription():null);
				}
			}
		}
		return menu;
	}
	
	protected JWebIcon findMenuIcon(String tipo, BizAction action) throws Exception {
		JWebIcon icon = JWebIcon.getPssIcon(GuiIconos.RESPONSIVE, action.GetNroIcono());
//		icon.setURL(icon.getURL()+" pss-"+tipo+"-icon");
		return icon;
	}
	
	public void generateSubMenu(JWebView parent,JWebNavigationBarGroup group,BizActions actions,long level) throws Exception {
		if ( actions == null ) return;
		
		JIterator<BizAction> iter=actions.getStaticIterator();
		while (iter.hasMoreElements()) {
			BizAction action=iter.nextElement();
			if (!action.ifMenu()) continue;
			if (action.hasSubActions()) { 
				JWebIcon icon = this.findMenuIcon("submenu", action);
				JWebNavigationBarGroup sgroup =  this.addNavigationGroup(parent,group, icon,action.GetDescr(),level+1);
				this.generateSubMenu(parent,sgroup,action.GetSubAcciones(),level+1);
			}	else {
				JWebIcon icon = this.findMenuIcon("menuitem", action);
				if (action.isNuevaSession()) {
					this.addNavigationAction(parent,group,JWebActionFactory.createNewSession(parent.getUICoordinator().getSession(),action.getIdAction()) ,icon,action.GetDescr(),action.isNuevaVentana(), action.hasConfirmMessage()?action.getConfirmMessageDescription():null);
				} else {
					this.addNavigationAction(parent,group,JWebActionFactory.idActionToURL(action.getIdAction()), icon,action.GetDescr(),action.isNuevaVentana(), action.hasConfirmMessage()?action.getConfirmMessageDescription():null);
				}
			}
		}
	}
	
	public JWebNavigationBarGroup addNavigationGroup(JWebView parent,JWebNavigationBarGroup group, JWebIcon zIcon,String name, long level) throws Exception {
		return parent.addNavigationGroup(group,zIcon,name,level);
	}
	public void addNavigationAction(JWebView parent,JWebNavigationBarGroup group, String zAction, JWebIcon zIcon, String label, boolean newwindow,String confirmation)	throws Exception {
		parent.addNavigationAction(group,zAction,zIcon,label, newwindow,confirmation);
	}
	public void addNavigationAction(JWebView parent,JWebNavigationBarGroup group, JWebAction zAction, JWebIcon zIcon, String label, boolean newwindow,String confirmation)	throws Exception {
		parent.addNavigationAction(group,zAction,zIcon,label, newwindow, confirmation);
	}
	public void addNavigationAction(JWebView parent,JWebNavigationBarGroup group, String zAction, JWebIcon zIcon, String label, boolean newwindow)	throws Exception {
		parent.addNavigationAction(group,zAction,zIcon,label, newwindow);
	}
	public void addNavigationAction(JWebView parent,JWebNavigationBarGroup group, JWebAction zAction, JWebIcon zIcon, String label, boolean newwindow)	throws Exception {
		parent.addNavigationAction(group,zAction,zIcon,label, newwindow);
	}
// header a toda la pantalla
	public JWebViewInternalComposite getJumbotron(JWebView parent) throws Exception {
//		if (BizUsuario.getUsr()==null) return null;
//		String logo = BizUsuario.getUsr().getObjCompany().getLogo();
//		String logoPath=logo==null?null:"logos/"+BizUsuario.getUsr().getObjCompany().getLogo();
//		JWebJumbotronResponsive div = new JWebJumbotronResponsive();
//		JWebDivResponsive container = new JWebDivResponsive();
//		JWebHResponsive h = new JWebHResponsive(1,"");
//		container.setClassResponsive("container");
//		JWebLabelResponsive label = new JWebLabelResponsive();
//		label.setLabel(BizUsuario.getUsr().getObjBusiness().getTitle());
//		label.setIcon(JWebIcon.getPssIcon(logoPath));
//		label.setTextPosition(JWebViewsConstants.TEXT_POSITION_LEFT);
//		div.setClassResponsive("jumbotron");
//		div.addChild(container);
//		container.addChild(h);
//		h.addChild(label);
//		return div;
		return null;
	}
	
	protected void fillHeader(JWebView parent) throws Exception {
		String logo = BizUsuario.getUsr().getObjCompany().getLogo();
		String logoPath=logo==null?null:JPath.PssPathLogos()+BizUsuario.getUsr().getObjCompany().getLogo();
		if (menuPosPrincipal().equals("SIDEBAR")) 
			parent.addNavigationTitle(BizUsuario.getUsr().getObjBusiness().getTitle(),null,null);
		else if (menuPosPrincipal().equals("TOP"))
			parent.addNavigationTitle(BizUsuario.getUsr().getObjBusiness().getTitle(),JWebIcon.getPssDataResource(logoPath),"height:100%;max-height:50px;");
		else 
			parent.addNavigationTitle(BizUsuario.getUsr().getObjBusiness().getTitle(),JWebIcon.getPssDataResource(logoPath),"height:100%;max-height:50px;");
	}
	public void configureJFormFiltro(JFormControl control) throws Exception {
		if (control instanceof JFormWinLOVResponsive) {
		 ( (JFormWinLOVResponsive) control).setMaxWidth(300);
		}
//		if (control instanceof JWinLOV) {
//			 ( (JWinLOV) control).setPreferredWidth(300);
//		}
//		if (control instanceof JFormLOV) {
//			 ( (JFormLOV) control).setPreferredWidth(300);
//		}
 }
	public void configureJFormItem(JFormControl control) throws Exception {
//		if (control instanceof JFormWinLOVResponsive) {
//		 ( (JFormWinLOVResponsive) control).setMaxWidth(300);
//		}
 }
	public void fillNavBar(JWebView parent) throws Exception {
		this.fillHeader(parent);
		
  	parent.addGoBack(null, JWebIcon.getResponsiveIcon("fa fa-fw fa-arrow-left"), null);
//		parent.addGoHelp(null, JWebIcon.getResponsiveIcon("fa fa-fw fa-question-circle"), null);
		parent.addSecurity(null, JWebIcon.getResponsiveIcon("fa fa-fw fa-key"), null);
		parent.addGoHome(null, JWebIcon.getResponsiveIcon("fa fa-fw fa-home"), null);
		parent.addNewSession(null, JWebIcon.getResponsiveIcon("fa fa-fw fa-copy"), null);
		parent.addMailFolder(null, JWebIcon.getResponsiveIcon("fa fa-fw fa-envelope"),getMessage("Mensajeria", null),1);
		parent.addAlertFolder(null, JWebIcon.getResponsiveIcon("fa fa-fw fa-bell"),getMessage("Alertas", null),1);
		JWebNavigationBarGroup g3 = parent.addUserFolder(null, JWebIcon.getResponsiveIcon("fa fa-fw fa-user"),getMessage("Config", null),1);
		if (g3!=null) {
			parent.addSeparator(g3);
			parent.addUserPreferences(g3, JWebIcon.getResponsiveIcon("fa fa-fw fa-address-card"),getMessage("Preferencias", null));
//			parent.addUserListExcludeCols(g3, JWebIcon.getResponsiveIcon("fa fa-fw fa-columns"),"Preferencias Vista");
			parent.addChangePassword(g3, JWebIcon.getResponsiveIcon("fa fa-fw fa-lock"),getMessage("Cambiar clave", null));
			parent.addSeparator(g3);
			if (BizUsuario.IsAdminUser())
				parent.addInfoSystem(g3,JWebIcon.getResponsiveIcon("fa fa-fw fa-server"),null);
			parent.addCloseSession(g3,JWebIcon.getResponsiveIcon("fas fa-sign-out-alt"),null);
		}
		parent.addRefresh(null, JWebIcon.getResponsiveIcon("fa fa-fw fa-sync-alt"),null);
		parent.addActiveHelp(null,null,getMessage("Ayuda", null));
		
//		parent.addLogo(JWebIcon.getPssDataResource(logoPath),"height:25px;");

	}
	public void configuereBars(JWebViewNavigationBar bar, String menu)  throws Exception{
//		if (menu.equals("TOP")) bar.setNavBarBlack(true);
	}
	public JWebViewNavigationBar getMenuBar(JWebViewComposite parent)  throws Exception{
		if (menuPosPrincipal().equals("TOP")) {
			JWebNavigationBarTop n=new JWebNavigationBarTop();
			//n.setNavBarInverse(true);
			n.setNavBarRight(false);
			configuereBars(n,menuPosPrincipal());
			n.buildNav("PRINCIPAL");
			return n;
			
		} else if (menuPosPrincipal().equals("SIDEBAR")) {
			JWebNavigationBarSidebar n=new JWebNavigationBarSidebar();
			configuereBars(n,menuPosPrincipal());
			n.setRoleMobile("SIDEBAR");
			n.setParent(parent);
			return n;
			
		} else {
			JWebNavigationBarTop n=new JWebNavigationBarTop();
			n.setNavBarRight(false);
			configuereBars(n,menuPosPrincipal());
			n.buildNav("PRINCIPAL");
			return n;
		}
		
	}
	
	public String sourceColors() throws Exception {
		return "skins/sbadmin/css/default.css";
	}
	public String source() throws Exception {
		return "skins/sbadmin/css/sb-admin-2.css";
	}


	public JWebViewHistoryBar getHistoryBar(JWebViewComposite parent) throws Exception {
		JWebDivResponsive d=new JWebDivResponsive();
		JWebViewHistoryBar n=new JWebViewHistoryBar();
		n.setRootpanel(d);
		n.setRoleMobile("HISTORYBAR");
		n.setClassResponsive("breadcrumb");
		n.setName("history_bar");
		d.setParent(parent);
		d.setClassResponsive("breadcrumb_panel");
		d.addChild("history_bar",n);
		return n;
	}
	
	public JWebViewComponent getNavigationAction(JWebViewComposite parent,JWebAction zAction, JWebIcon zIcon) throws Exception {
		JWebLink oLink=new JWebLink();
		oLink.setSkinStereotype("navigation_bar_link");
		oLink.setWebAction(zAction);
		oLink.setParent(parent);
		oLink.setIcon(zIcon);
		return oLink;
	}
	
	public JWebViewComponent getHistoryAction(JWebViewComposite parent,JWebAction zAction, JWebIcon zIcon) throws Exception {
		JWebLink oLink=new JWebLink();
		oLink.setClassResponsive("breadcrumb-item");
		oLink.setWebAction(zAction);
		oLink.setParent(parent);
		//oLink.setIcon(zIcon);
		oLink.setLabel(zAction.getDescription());
		parent.addChild(oLink);
		return oLink;
	}
	
	public JWebViewComponent createBackButton(JWebViewComposite parent,JWebAction zAction) throws Exception {
		JWebLink oLink=new JWebLink();
		oLink.setClassResponsive("btn btn-outline btn-primary btn-xs breadcrumb-button");
		oLink.setWebAction(zAction);
		oLink.setParent(parent);
		oLink.setIcon(JWebIcon.getResponsiveIcon("fa fa-chevron-circle-left"));
		oLink.setLabel(zAction.getDescription());
		parent.addChild("back_action",oLink);
		return oLink;
		
	}


	public JWebLink getFindButton(JWebViewComposite parent,JWebAction webAction) throws Exception {
		JWebLink link = new JWebLink();
		link.setWebAction(webAction);
		link.setClassResponsive("btn btn-primary btn-sm");
		link.setCancel(false);
		link.setSkinStereotype("win_list_dofilter");
		link.setLabel("Buscar");
		link.setSubmit(true);
		link.setIcon(JWebIcon.getPssIcon(GuiIconos.RESPONSIVE, 112));
		return link;
	}
	
	public void addAtributesToFilterControls(JWebViewInternalComposite parent,JFormControlResponsive component) throws Exception {
		
	}
	public JIterator<JFormControl> filtersResorted(JIterator<JFormControl> iter)  throws Exception {
		return iter;
	}
	
	public String getWidthAutocompleteColumn(JColumnaLista[] columns) throws Exception {
		return "100%";
	}
	public void addAttributesToFilterForm(JWebViewInternalComposite parent) throws Exception {

	}
	public boolean isFilterInLine() throws Exception {
		return true;
	}

	public JWebViewComponent getHistoryMessage(JWebViewComposite parent,String message) throws Exception {
		JWebLink oLabel=new JWebLink();
		oLabel.setClassResponsive("breadcrumb-item active");
		oLabel.setParent(parent);
		oLabel.setLabel(message);
		parent.addChild(oLabel);
		return oLabel;
	}
	
	public JWebViewComposite buildForm(JWebFormFormResponsive parent) throws Exception {
		parent.setClassResponsive("col-sm-12 panel panel-primary");

		return parent;
	}
	public JWebViewComposite buildLocalForm(JWebLocalFormResponsive parent) throws Exception {
		parent.setClassResponsive("col-sm-12 panel panel-primary");

		return parent;
	}
	
	public String getPanelDefault() throws Exception {
		return "panel-default";
	}

	protected boolean withIcono() throws Exception {
		return JCompanyBusiness.getDefiniciones(JCompanyBusiness.CONICONOS).equals("S");
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
		if (parent.isEmbedded()) 
			panel.setClassResponsive("panel panel-ghost");
		else
			panel.setClassResponsive("panel "+getPanelDefault() );
		panelHeading.setClassResponsive("panel-heading clearfix");
		panelBody.setClassResponsive("panel-body");
		if (parent.isToolbarLeft())
			panelBody.setClassResponsive(panelBody.getClassResponsive() + " with-sidebar");

		formRowPanel.setClassResponsive("row");
		formPanel.setClassResponsive("col-lg-12");
		parent.setRole("form");
	
		formPanel.add("form", parent);
		formRowPanel.add("col", formPanel);
		panelBody.add("row", formRowPanel);
		if (parent.isToolbarLeft())
			panel.add("actionbar",parent.buildActionBar());
		if (parent.isWithHeader()) 
			panel.add("header", panelHeading);
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
	public JWebPanelResponsive buildGridtable(JWebTableExpandResponsive table) throws Exception {
		if (table.getSizeRow()==null) {
			table.setClassResponsive("panel panel-primary paddingtop inline_component");
		}
		else {
			table.setClassResponsive("panel panel-primary paddingtop "+table.getSizeRow());
		}

		if (table.isZoomtofit()) {
			JWebTableResponsive panel = new JWebTableResponsive();
			panel.setZoomtofit(table.getZoomtofit());
			table.add(panel.getObjectName(), panel);
			return panel;
			
		}
		JWebDivResponsive panel = new JWebDivResponsive();
		table.add(panel.getObjectName(), panel);

		return panel;
	}	
	public JWebRowGridExpandResponsive buildGridRow(JWebRowGridExpandResponsive row, String mode) throws Exception {
		if (mode==null)
			row.setClassHeader("panel-primary");
		else
			row.setClassHeader("panel-"+mode);
		
		row.setClassResponsive("panel-body");
		if (row.isToolbarTop()||!row.isEditable()) 
			return row;
		if (row.isToolbarBottom()) {
			JWebDivResponsive panel = new JWebDivResponsive();
			JWebDivResponsive toolbar = new JWebDivResponsive();
			row.addChild(panel);
			row.addChild(toolbar);
			row.setRootPane(panel);
			row.setToolbarRootPane(toolbar);
			return row;
		} 	else if (row.isToolbarLeft()) {
			JWebPanelResponsive panel = new JWebPanelResponsive();
			JWebPanelResponsive toolbar = new JWebPanelResponsive();
			panel.setSizeResponsive("col-sm-8");
			toolbar.setSizeResponsive("col-sm-4 ");
			row.addChild(toolbar);
			row.addChild(panel);
			row.setRootPane(panel);
			row.setToolbarRootPane(toolbar);
			return row;
		}	else if (row.isToolbarRight()) {
			JWebPanelResponsive panel = new JWebPanelResponsive();
			JWebPanelResponsive toolbar = new JWebPanelResponsive();
			panel.setSizeResponsive("col-sm-8");
			toolbar.setSizeResponsive("col-sm-4");
			row.addChild(panel);
			row.addChild(toolbar);
			row.setRootPane(panel);
			row.setToolbarRootPane(toolbar);
			return row;
		}
		return row;
	}	
	
	public void buildPreConfigurarColumnaLista(JWins webwin,JWinList winList) throws Exception {
		winList.AddColumnaAction(87, null, "all", true);
	}
	public void buildPosConfigurarColumnaLista(JWins webwin,JWinList winList) throws Exception {
//		winList.AddColumnaAction(5007, null, "all", true);
	}
	public String getClassActionWinList(JWin win) throws Exception {
		return "btn-ghost-dark btn-ghost-sm ";
	}
	public boolean withCaretActionWinList(JWin win) throws Exception {
		return false;
	}
	
	
	public JWebFilterPaneResponsive buildFormFilter(JWebWinGenericResponsive parent) throws Exception{
		JWebFilterPaneResponsive filterPanel = new JWebFilterPaneResponsive(parent);
		parent.addButtonModeFiltersInFormFilter(filterPanel);
//		if (!parent.useSimpleOrden())
//			parent.addButtonOrderInFormFilter(filterPanel);
		return filterPanel;
	}
	

	public JWebViewInternalComposite buildWinGrid(JWebWinGenericResponsive parent) throws Exception {
		return buildWinList(parent);
	}
	public JWebViewInternalComposite buildWinList(JWebWinGenericResponsive parent) throws Exception {
		JWebViewComposite zoneList = new JWebViewZone();
	//	zoneList.setClassResponsive("col-lg-12");
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
		JWebHResponsive h4 = new JWebHResponsive(3,parent.getTitle());
		h4.setClassResponsive("one-line-right");
		if (parent.hasWins() && withIcono() ) {
			JWebIcon oIcon = JWebIcon.getPssIcon(GuiIconos.RESPONSIVE, parent.getWins().GetNroIcono());
			h4.setIcon(oIcon);
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

		if (!parent.isToolbarNone()) {
			panelToolbar.setClassResponsive("toolbar pull-lg-"+this.getPullBotons());
			if (!parent.isEmbedded()) { 
				panelHeading.addChild("h4",h4); 
		
				if (parent.isToolbarLeft())
					panel.add("actionbar",actionbarRowPanel);
				else if (parent.isToolbarTop()||(parent.isToolbarIn() && parent.isFalseInternalToolbar()))// rjl test
					h4.addChild("toolbar",actionbarRowPanel); 
				else if (parent.isToolbarIn())
					panelToolbar.addChild("toolbar",actionbarRowPanel); 

				if (preferenceBar!=null && !JWebActionFactory.isMobile()) h4.addChild("preference",preferenceBar);

				panel.add("header", panelHeading);
			}	else {
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
			}
		} else {
			panelToolbar.setDisplayType(JFormControl.DISPLAY_NONE);
			panel.addChild(parent.getProviderName()+"_toolbar",panelToolbar);
			panelToolbar.setClassResponsive("hidden");
			panelToolbar.add("actionbar",actionbarRowPanel);
			
		}

		if (this.isFiltersFirst()) {
			this.attachFilters(parent, zoneFilter, panelBody);
			this.attachToolbarIn(parent, panelToolbar, panelBody);
		} else {
			this.attachToolbarIn(parent, panelToolbar, panelBody);
			this.attachFilters(parent, zoneFilter, panelBody);
		}
		
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

	public JWebViewComposite createZoneFilter(JWebWinGenericResponsive parent) throws Exception {
		JWebFilterPaneResponsive filterPane=parent.createFormFiltros();
		if (filterPane==null) return null;
		parent.attachOrderInFormFilter(filterPane);
		parent.attachDoFilterButton(filterPane,filterPane,parent.getPresentation());
		filterPane.setClassResponsive("form-filter panel");
		return filterPane;
	}

	
	public boolean isFiltersFirst() throws Exception {
		return true;
	}

	public void attachFilters(JWebWinGenericResponsive parent, JWebViewComposite zoneFilter, JWebDivResponsive panelBody) throws Exception {
		if (zoneFilter==null) return;
		if (parent.isNothingToShowInFilterBar()) return; 
		panelBody.addChild("filter_pane_"+parent.getProviderName(), zoneFilter);
	}
	
	public void attachToolbarIn(JWebWinGenericResponsive parent, JWebViewComposite panelToolbar, JWebDivResponsive panelBody) throws Exception {
		if (!parent.isToolbarIn()) return;
		panelBody.addChild(parent.getProviderName()+"_toolbar",panelToolbar);
	}
	
	public String getPullBotons() {
		return "right";
	}
	public JWebViewInternalComposite buildOptions(JWebWinOptionsResponsive parent) throws Exception {
		JWebDivResponsive panel = new JWebDivResponsive();
		JWebDivResponsive panelHeading = new JWebDivResponsive();
		JWebDivResponsive panelBody = new JWebDivResponsive();
		JWebDivResponsive panelFooter = new JWebDivResponsive();
		JWebHResponsive title = new JWebHResponsive(3,parent.getQuestion());
		JWebViewInternalComposite actionbarRowPanel = parent.createActionBar();
		JWebFilterPaneResponsive filterPane=parent.createFormFiltros();
		panel.setClassResponsive("panel panel-primary");
		panelHeading.setClassResponsive("panel-heading clearfix");
		panelBody.setClassResponsive("panel-body");
		panel.add(parent.getCompletePanelName()+"_header", panelHeading);
		panel.add(parent.getCompletePanelName()+"_body", panelBody);
		panelHeading.add("title",title);

		JWebViewZoneRow root = new JWebViewZoneRow();
		root.setName(parent.getCompletePanelName()+"_row");
		root.setClassResponsive("row");
		parent.setClassResponsive("col-sm-12 pss-field");
		root.add(parent.getCompletePanelName()+"_panel",panel);

		panelBody.add("list", parent);
		return root;
	}
	public String buildClassOptions(JWebWinOptionsResponsive parent,JWin win) throws Exception {
		return "btn-primary";
	}
	public JWebViewInternalComposite buildSwapList(JWebWinGenericResponsive parent) throws Exception {
		if (parent.isInForm())
			return buildSwapListComponent(parent);
		else
			return buildSwapListInternal(parent);
	}

	public JWebViewInternalComposite buildSwapListInternal(JWebWinGenericResponsive parent) throws Exception {
		JWebDivResponsive panel = new JWebDivResponsive();
		JWebDivResponsive panelHeading = new JWebDivResponsive();
		JWebDivResponsive panelBody = new JWebDivResponsive();
		JWebDivResponsive panelFooter = new JWebDivResponsive();
		JWebHResponsive title = new JWebHResponsive(3,parent.getTitle());
		JWebViewInternalComposite actionbarRowPanel = parent.createActionBar();
		JWebFilterPaneResponsive filterPane=parent.createFormFiltros();
		panel.setClassResponsive("panel panel-primary");
		panelHeading.setClassResponsive("panel-heading clearfix");
		panelBody.setClassResponsive("panel-body");
		panel.add(parent.getCompletePanelName()+"_header", panelHeading);
		panel.add(parent.getCompletePanelName()+"_body", panelBody);
		panelHeading.add("title",title);

		JWebViewZoneRow root = new JWebViewZoneRow();
		root.setName(parent.getCompletePanelName()+"_row");
		root.setClassResponsive("row");
		parent.setClassResponsive("col-sm-12 ");
		root.add(parent.getCompletePanelName(),panel);
		if (filterPane!=null) {
			panelBody.add("filter_pane_"+parent.getProviderName(), filterPane);
			filterPane.setClassResponsive("row no-margin");
		}
		panelBody.add("actionbar",actionbarRowPanel);
		panelBody.add("list", parent);
		return root;
	}
	public JWebViewInternalComposite buildSwapListComponent(JWebWinGenericResponsive parent) throws Exception {
		JWebViewInternalComposite actionbarRowPanel = parent.createActionBar();

//		JWebViewZoneRow panelAction = new JWebViewZoneRow();
//		panelAction.setClassResponsive("row");
//		panelAction.add("actionbar",actionbarRowPanel);
		
		JWebDivResponsive panel = new JWebDivResponsive();
		JWebViewZoneRow root = new JWebViewZoneRow();
		root.setName(parent.getCompletePanelName()+"_row");
		root.setClassResponsive("row");
		root.add(parent.getCompletePanelName(),panel);
		
//		panel.add("actionbar_panel",panelAction);
		panel.add("list", parent);
		return root;
	}

	
	public JWebViewComposite createFooterBar(JWebWinGenericResponsive parent,JWebViewInternalComposite infoBar,JWebViewInternalComposite navBar,JWebViewInternalComposite exportBar,JWebViewInternalComposite extraBar1,JWebViewInternalComposite extraBar2) throws Exception {
		JWebViewZoneRow row = new JWebViewZoneRow();
		JWebViewZoneRow row1 = new JWebViewZoneRow();
		JWebViewZoneRow row2 = new JWebViewZoneRow();
		row1.setClassResponsive("row");
		row1.addChild(infoBar);
		row1.addChild(navBar);
		row1.addChild(exportBar);
		row2.setClassResponsive("row");
		row2.addChild(extraBar1);
		row2.addChild(extraBar2);

		row.add("nav_zone", row1);
		row.add("extra_zone", row2);
		return row;
	}

	public JWebViewInternalComposite createInfoBar(JWebViewComposite parent, String providerName) throws Exception {
		JWebDivResponsive infoBar = new JWebDivResponsive();
		infoBar.setName(providerName+"_infobar");
		infoBar.setClassResponsive("col-sm-3 pull-md-left");
		return infoBar;
	}

	public JWebViewInternalComposite createNavigationBar(JWebViewComposite parent, String providerName) throws Exception {
		JWebDivResponsive navigationBar = new JWebDivResponsive();
		navigationBar.setName(providerName+"_navigation_bar");
		navigationBar.setClassResponsive("col-sm-8 pagination-bar");
		return navigationBar;
	}
	public JWebViewInternalComposite createPreferenceBar(JWebViewComposite parent, String providerName) throws Exception {
		JWebDivResponsive preferenceBar = new JWebDivResponsive();
		preferenceBar.setName(providerName+"_preference_bar");
		preferenceBar.setClassResponsive("pull-right");
		return preferenceBar;
	}
	public JWebViewComponent createBotonPreference(JWebViewComposite parent,String name,JWebAction action) throws Exception {
		JWebLink oToMap = new JWebLink();
		oToMap.setMode(JWebLink.MODE_BUTTON);
		oToMap.setClassResponsive("btn btn-primary btn-circle ");
		oToMap.setIcon(JWebIcon.getResponsiveIcon("fa fa-cog"));
		oToMap.setWebAction(action);
		parent.addChild(name, oToMap);
		return oToMap;
	}
	

	
	public JWebViewInternalComposite createExportBar(JWebViewComposite parent, String providerName) throws Exception {
		JWebDivResponsive exportBar = new JWebDivResponsive();
		exportBar.setName(providerName+"_export_bar");
		exportBar.setClassResponsive("col-sm-1 paddingleft-0 pull-right");
		return exportBar;
	}
	public JWebViewInternalComposite createFooterExtraBar1(JWebViewComposite parent, String providerName) throws Exception {
		JWebDivResponsive navigationBar = new JWebDivResponsive();
		navigationBar.setName(providerName+"_footerExtraBar1");
		navigationBar.setClassResponsive("collapse pull-left btn btn-outline border border-primary");
		return navigationBar;
	}
	public JWebViewInternalComposite createFooterExtraBar2(JWebViewComposite parent, String providerName) throws Exception {
		JWebDivResponsive navigationBar = new JWebDivResponsive();
		navigationBar.setName(providerName+"_footerExtraBar2");
		navigationBar.setClassResponsive("collapse pull-right btn btn-outline border border-primary");
		return navigationBar;
	}

	public JWebViewInternalComposite createPaginationBar(JWebViewComposite parent,boolean mini, String providerName) throws Exception {
		JWebUnsortedList pagination = new JWebUnsortedList();
		pagination.setName(providerName+"_pagination_bar"+(mini?"_mini":""));
		pagination.setClassResponsive("pagination "+(mini?"visible-xs":"hidden-xs"));
		parent.addChild(pagination);
		return pagination;
	}
	
	protected String findActionClass(JWebWinActionBar bar, BizAction action, JWebAction webAction) throws Exception {
		if (webAction.isForceSubmit()) 
			return "btn btn-success btn-sm";
		if (webAction.isCancel()) {
			if (webAction.isForm()) 
				return "btn btn-danger btn-sm";
			if (webAction.isWinList()) 
				return "btn btn-outline btn-link btn-like";
		}
		if (webAction.isWinListRefresh())
			return "btn btn-outline btn-link btn-like";

		if (action.getId()==BizAction.DROP && webAction.isWinList())
			return "btn btn-outline btn-sm btn-success";
			
		return "btn btn-default btn-sm";
	}
	
	protected String findIconPosition(JWebWinActionBar bar, BizAction action, JWebAction webAction) throws Exception {
		return JWebLink.POSICON_RIGHT_NODIV;
	}
	
	protected JWebIcon findActionIcon(JWebWinActionBar bar, BizAction action, JWebAction webAction) throws Exception {
		if (!bar.isWithIcons()) return null;
//		if (webAction.isForceSubmit()) 
//			return JWebIcon.getResponsiveIcon("fa fa-save");
		if (webAction.isWinListRefresh())
			return JWebIcon.getPssIcon(GuiIconos.RESPONSIVE, action.GetNroIcono());
			
		return JWebIcon.getPssIcon(GuiIconos.RESPONSIVE,action.GetNroIcono());
	}

	protected String findActionLabel(JWebWinActionBar bar, BizAction action, JWebAction webAction) throws Exception {
		if (!bar.isWithLabels()) return "";
		String label=action.GetDescr();
		if (webAction.isForceSubmit())
			label="Aplicar";
		
		// por default va con los labels, sobrescribir en el skin
//		if (webAction.isCancel() && webAction.isWinList())
//			label=""; // y eso porque?HGK
//		
//		if (webAction.isWinListRefresh())
//			label=""; 

		if (bar.isUpperCase()) return label.toUpperCase();
		return label;
	}
	protected String findActionTooltip(JWebWinActionBar bar, BizAction action, JWebAction webAction) throws Exception {
		String label=action.GetDescr();
		if (webAction.isForceSubmit())
			label="Aplicar";
		
	

		if (bar.isUpperCase()) return label.toUpperCase();
		return label;
	}
	public void createBoton(JWebWinActionBar bar, String zActionId, BizAction action, JWebAction webAction) throws Exception {

		String clase = this.findActionClass(bar, action, webAction);
		String label = this.findActionLabel(bar, action, webAction);
		String tooltip = this.findActionTooltip(bar, action, webAction);
		JWebIcon icon = this.findActionIcon(bar, action, webAction);
		String iconPosition = this.findIconPosition(bar, action, webAction);

		if (bar.getRootPanel() instanceof JWebNavigationBarSidebar) {
			((JWebNavigationBarSidebar)bar.getRootPanel()).addNavigationAction(null, zActionId, webAction, icon, label, action);
			return;
		}
		
		JWebLink link = new JWebLink();
		link.setClassResponsive(clase);
		link.setIcon(icon);
		link.setToolTip(tooltip);
		if (action.getForeground()!=null)
			link.setForeground(action.getForeground());
		if (action.getBackground()!=null)
			link.setBackground(action.getBackground());
//		if (bar.isWithLabels())
			link.setLabel(label);
		link.setWebAction(webAction);
		link.setCancel(webAction.isCancel());
		link.setMode(JWebLink.MODE_BUTTONGROUP);
		link.setClassPositionIcon(iconPosition);
		link.setAccessKey(webAction.getKey());
		link.setImportant(action.getImportance());
		link.setOpensNewWindow(webAction.isNuevaVentana());
		link.setSubmit(webAction.isForceSubmit());
		link.setSubmitAfterBack(webAction.isSubmitAfterBack());
		
		bar.getRootPanel().add(zActionId, link);
	}
	
	public void createBotonInMore(JWebWinActionBar parent,JWebViewComposite moreButton,String idAction,BizAction zAction, JWebAction webAction) throws Exception {
		String label = parent.isUpperCase()?zAction.GetDescr().toUpperCase():zAction.GetDescr();
		if (parent.getRootPanel() instanceof JWebNavigationBarSidebar) {
			((JWebNavigationBarSidebar)parent.getRootPanel()).addNavigationAction((JWebNavigationBarGroup)moreButton, idAction,webAction,JWebIcon.getPssIcon(GuiIconos.RESPONSIVE,zAction.GetNroIcono()), label, zAction);
			return;
		}
		JWebLink oLink = new JWebLink();
		oLink.setWebAction(webAction);
		oLink.setCancel(webAction.isCancel());
		oLink.setAccessKey(webAction.getKey());
		oLink.setImportant(zAction.getImportance());
		oLink.setOpensNewWindow(webAction.isNuevaVentana());
		if (!parent.isEmbedded()) oLink.setLabel(label);
		if (parent.isEmbedded() && parent.isWithLabels()) oLink.setLabel(label);
		if (parent.isWithIcons()) {
			oLink.setIcon(JWebIcon.getPssIcon(GuiIconos.RESPONSIVE,zAction.GetNroIcono()));
		}
		JWebElementList li = new JWebElementList();
		li.addChild(idAction+"_button", oLink);

		moreButton.addChild(idAction, li);
	}
	
	public JWebViewComposite createBotonMore(JWebWinActionBar parent,boolean isWinList,boolean isForm,boolean isFormLov) throws Exception {
		if (parent.getRootPanel() instanceof JWebNavigationBarSidebar) {
			return ((JWebNavigationBarSidebar)parent.getRootPanel()).addNavigationGroup(null, null, "Acciones", 0);
		}
		JWebViewDropDownButton moreButton = new JWebViewDropDownButton();
		moreButton.setArrow(JWebIcon.getResponsiveIcon( "caret"));
		moreButton.setClassResponsive("btn-group");
		moreButton.setClassButton("btn btn-primary dropdown-toggle btn-sm");
		moreButton.setLabelButton("Acciones");
		parent.getRootPanel().addChild("more", moreButton);
		return moreButton;
	}
	public JWebViewComponent createHideShow(JWebViewComposite parent,String name,boolean state,JWebAction action,String label, String dataon,String dataoff) throws Exception {

		JWebCheckResponsive oHideShow = new JWebCheckResponsive();
		oHideShow.setMode(JFormCheckResponsive.MODE_TOGGLE);
		oHideShow.setDataon(dataon);
		oHideShow.setDataoff(dataoff);
		oHideShow.setLabel(label);
		oHideShow.setDatasize(JFormCheckResponsive.SIZE_MINI);
		oHideShow.setWebAction(action);
		oHideShow.setValue(state);
		oHideShow.setClassResponsive("label_checkbox");//checkbox-inline");
		oHideShow.setNoForm(true);
		parent.addChild(name, oHideShow);
		return oHideShow;
	}

	public JWebViewComponent createToggleInMenu(JWebViewComposite parent,String name,boolean state,JWebAction action,String label, String dataon,String dataoff) throws Exception {

		JWebCheckResponsive oHideShow = new JWebCheckResponsive();
		oHideShow.setMode(JFormCheckResponsive.MODE_TOGGLE);
		oHideShow.setDataon(dataon);
		oHideShow.setDataoff(dataoff);
		oHideShow.setLabel(label);
		oHideShow.setDatasize(JFormCheckResponsive.SIZE_MINI);
		oHideShow.setWebAction(action);
		oHideShow.setValue(state);
		oHideShow.setClassResponsive("label_checkbox");//checkbox-inline");
		oHideShow.setNoForm(true);
		parent.addChild(name, oHideShow);
		return oHideShow;
	}
	
	//align: left o right
	public JWebViewComponent createCollapseButton(JWebViewComposite parent,String name,String collapseZone,String image, String extraClass) throws Exception {
		JWebCollapseResponsive oNavInfo = new JWebCollapseResponsive();
		oNavInfo.setLabel(name);
		oNavInfo.setImage(image);
		oNavInfo.setClassResponsive("btn btn-primary"+(extraClass==null?"":" "+extraClass)); 
		oNavInfo.setsDataTarget(collapseZone);
		parent.addChild(name, oNavInfo);
		return oNavInfo;

	}

	public void buildNavigationBar(JWebWinGenericResponsive parent,JWebViewInternalComposite oNavigationBar) throws Exception {
//		parent.addButtonModeFilters(oNavigationBar);
		parent.addPagination(oNavigationBar);
	}
	public void buildExportBar(JWebWinGenericResponsive parent,JWebViewInternalComposite oNavigationBar) throws Exception {
		if (parent.getWins().isInMobile()) return; // no soportado en mobile aun
		createCollapseButton(oNavigationBar,parent.getProviderName()+"_export",parent.getProviderName()+"_footerExtraBar2","fa fa-cloud-download-alt","pull-right");
	}
	public void buildInfoBar(JWebWinGenericResponsive parent,JWebViewInternalComposite oNavigationBar) throws Exception {
		if (parent.getWins().isInMobile()) return; // no soportado en mobile aun
		createCollapseButton(oNavigationBar,parent.getProviderName()+"_info",parent.getProviderName()+"_footerExtraBar1","fa fa-cog","pull-left");
		parent.addInfoRecord(oNavigationBar);

	}
	
	public void buildHeaderExtraBar1(JWebWinGenericResponsive parent) throws Exception {
		
	}
	
	public void buildFooterExtraBar1(JWebWinGenericResponsive parent,JWebViewInternalComposite oNavigationBar) throws Exception {
//		parent.addInfoRecord(oNavigationBar); // put info into button
		if (parent.getWins().isInMobile()) return; // no soportado en mobile aun
		parent.addButtonModePreview(oNavigationBar);
		if (!parent.hasFilterNeverHide()) parent.addButtonModeFilters(oNavigationBar);
		parent.addSelectSizePage(oNavigationBar);
	}
	
	public void buildFooterExtraBar2(JWebWinGenericResponsive parent,JWebViewInternalComposite oNavigationBar) throws Exception {
		if (parent.getWins().isInMobile()) return; // no soportado en mobile aun
		parent.addButtonExportToMap(oNavigationBar);
		parent.addButtonExportToGraph(oNavigationBar);
		parent.addButtonExportToExcel(oNavigationBar);
		parent.addButtonExportToCSV(oNavigationBar);
		//parent.addButtonExportToReport(oNavigationBar);
	}
	
	public JWebViewComponent buildOrderButtonInFilter(JWebViewComposite parent,JWebAction webAction) throws Exception {
		JWebLink link = new JWebLink();
		link.setWebAction(webAction);
		link.setClassResponsive("btn btn-primary");
		link.setCancel(false);
		link.setLabel("");
		link.setSubmit(false);
		link.setIcon(JWebIcon.getPssIcon(GuiIconos.RESPONSIVE,10061));
		return link;
	}

	
	public JXMLRepresentable buildOrderColumn(JWebViewComposite parent,boolean column,String order,JWebAction webAction) throws Exception {
		return null;
	}
//	public JXMLRepresentable buildOrderColumn(JWebViewComposite parent,boolean column,String order,JWebAction webAction) throws Exception {
//		JWebLink link = new JWebLink();
//		link.setWebAction(webAction);
//		link.setClassResponsive("btn btn-outline btn-link btn-like pull-sm-right");
//		link.setCancel(false);
//		link.setLabel("");
//		link.setSubmit(false);
//		link.setIcon(JWebIcon.getPssIcon(GuiIconos.RESPONSIVE,!column||order==null?15502:order.equals("ASC")?15501:15500));
//		return link;
//	}
	public boolean canClickInAllHeaderForOrder() throws Exception {
		return true;
	}
	
	public JWebViewComponent createInfo(JWebViewComposite parent,String name,JWebAction action) throws Exception {
		if (action!=null) {
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
	public JWebViewComponent createBotonExport(JWebViewComposite parent,String name,JWebAction action) throws Exception {

		JWebLink oToMap = new JWebLink();
		oToMap.setOpensNewWindow(true);
		if (name.indexOf("excel")!=-1) 	oToMap.setIcon(JWebIcon.getResponsiveIcon("fa fa-file-excel fa-1x"));
		else if (name.indexOf("map")!=-1) 	oToMap.setIcon(JWebIcon.getResponsiveIcon("fa fa-globe fa-1x"));
		else if (name.indexOf("csv")!=-1) 	oToMap.setIcon(JWebIcon.getResponsiveIcon("fa fa-file-alt fa-1x"));
		else if (name.indexOf("pdf")!=-1) 	oToMap.setIcon(JWebIcon.getResponsiveIcon("fa fa-file-pdf fa-1x"));
		else if (name.indexOf("graph")!=-1) 	oToMap.setIcon(JWebIcon.getResponsiveIcon("fa fa-chart-area fa-1x"));
		
		if (name.indexOf("excel")!=-1) 	oToMap.setLabel("XLS");
		else if (name.indexOf("map")!=-1) 	oToMap.setLabel("MAP");
		else if (name.indexOf("csv")!=-1) 	oToMap.setLabel("CSV");
		else if (name.indexOf("pdf")!=-1) 	oToMap.setLabel("PDF");
		else if (name.indexOf("graph")!=-1) 	oToMap.setLabel("IMG");
		oToMap.setMode(JWebLink.MODE_BUTTON);
		oToMap.setClassResponsive("pull-md-right");
		oToMap.setWebAction(action);
		parent.addChild(name, oToMap);
		return oToMap;
	}
	public JWebViewComponent createBotonPagination(JWebViewComposite parent,String name,JWebAction action,String label, boolean active) throws Exception {
		JWebElementList el = new JWebElementList();
		JWebLink oButton = new JWebLink();
		oButton.setMode(JWebLink.MODE_BUTTON);
		oButton.setLabel(label);
		oButton.setWebAction(action);
		if (active)
			oButton.setClassResponsive("btn-primary");
			
		el.addChild(oButton);
		parent.addChild(name, el);
		return el;
	}

	public JWebViewComponent createCombo(JWebViewComposite parent,String name,String label, boolean active,JWebAction webAction,String provider,String defa,JControlCombo combo) throws Exception {
		JWebComboResponsive oSelect = new JWebComboResponsive();
		oSelect.setController(new JString(), JLanguage.translate(label), false, 200, null, combo);
		oSelect.setRefreshForm(true);
		oSelect.setAction(webAction);
		oSelect.setNoForm(true);
		oSelect.setValue(defa);
		oSelect.setSizeResponsive("inline_component");
		oSelect.setProvider(provider);
		//oSelect.setClassResponsive("col-sm-1");
		parent.addChild(name, oSelect);
		return oSelect;
	}

	
	public boolean actionsInMore(JWebWinActionBar parent,BizAction zAction, JWebAction webAction) throws Exception {
		if (parent.isPreview()) 
			return true;
		if (parent instanceof JWebWinActionBarSwapList) {
			return false;
		}
		if (!zAction.useDefaultToolBarMore())
			return zAction.ifToolBarMore() ;

  	if (webAction.isCancel()) return false;
		if (webAction.isForceSubmit()) return false;

		if (webAction.isForm()) {
			return false;
		}
		if (!webAction.isWin()) {
  		return false;
		}
		if (zAction.getId()==BizAction.DROP) return false;
		if (webAction.isFormLov()) return false;
		if (parent.isToolbarLeft()) return false;
		return zAction.ifToolBarMore() ;
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
		actionBar.setClassResponsive("btn-group pull-right");
		return actionBar;
	}

	public JWebViewInternalComposite createActionBarWinListLeft(JWebWinGenericResponsive zObjectProvider) throws Exception {
		JWebNavigationBarSidebar actionBar=new JWebNavigationBarSidebar();
		actionBar.setClassResponsive("nav");
		actionBar.setRoleMobile("SIDEBAR");
		actionBar.setClassNavigation("navbar-default sidebar panelsidebar");
		return actionBar;
	}

	public JWebViewInternalComposite createActionBarForm(JWebWinForm zObjectProvider) throws Exception {
		if (zObjectProvider.isToolbarLeft())
			return createActionBarFormLeft(zObjectProvider);
		return createActionBarFormTop(zObjectProvider);
	}
	
	public JWebViewInternalComposite createActionBarForm(JWebDropDownComboResponsive zObjectProvider) throws Exception {
		JWebViewInternalComposite actionBar = zObjectProvider.getActionBar();
		actionBar.setClassResponsive("btn-group pull-right");
		return actionBar;
	}
	public JWebViewInternalComposite createActionBarForm(JWebDropDownWinLovResponsive zObjectProvider) throws Exception {
		JWebViewInternalComposite actionBar = zObjectProvider.getActionBar();
		actionBar.setClassResponsive("btn-group pull-right");
		return actionBar;
	}
	public JWebViewInternalComposite createActionBarForm(JWebRowGridExpandResponsive zObjectProvider) throws Exception {
		JWebViewInternalComposite actionBar=zObjectProvider.getActionBar();
		if (zObjectProvider.isToolbarNone())
			actionBar.setClassResponsive("hidden");
		else if (zObjectProvider.isToolbarTop())
			actionBar.setClassResponsive("btn-group pull-right");
		else if (zObjectProvider.isToolbarRight())
			actionBar.setClassResponsive("btn-group pull-right");
		else if (zObjectProvider.isToolbarLeft())
			;
		else if (zObjectProvider.isToolbarRight())
			;
		else if (zObjectProvider.isToolbarIn())
			actionBar.setClassResponsive("btn-group pull-right");
		return actionBar;
		
	}
	
	

	public JWebViewInternalComposite createActionBarFormTop(JWebWinForm zObjectProvider) throws Exception {
		JWebViewInternalComposite actionBar=zObjectProvider.getActionBar();
		actionBar.setClassResponsive("btn-group pull-right");
		return actionBar;
	}
	public JWebViewInternalComposite createActionBarFormLeft(JWebWinForm zObjectProvider) throws Exception {
		JWebNavigationBarSidebar actionBar=new JWebNavigationBarSidebar();
		actionBar.setClassResponsive("nav");
		actionBar.setRoleMobile("SIDEBAR");
		actionBar.setClassNavigation("navbar-default sidebar panelsidebar");
		return actionBar;
	}
	
	public JWebViewInternalComposite createActionBarFormLov(JWebWinListFormLov zObjectProvider, boolean zForForm) throws Exception {
		JWebWinActionBar actionBar=zObjectProvider.getActionBar();
		actionBar.setClassResponsive("btn-group pull-right");
		return actionBar;
	}

	public String getRowClass()  throws Exception{
		return "table_row";
	}

	public BizAction createActionCancel(JWebActionOwnerProvider zObjectProvider) throws Exception {
		BizAction a = new BizAction();
		a.pDescr.setValue("Cancelar");
		a.pNroIcono.setValue(GuiIcon.CANCELAR_ICON);
		a.SetIdAction("action-cancel");
		return a;
	}
	public BizAction createActionBack(JWebActionOwnerProvider zObjectProvider) throws Exception {
		BizAction a = new BizAction();
		a.pDescr.setValue("Volver");
		a.pNroIcono.setValue(GuiIcon.RETURN_ICON);
		a.SetIdAction((zObjectProvider==null?"":zObjectProvider.getProviderName()+"-")+"action-volver");
		return a;
	}
	public String getMessageConfirmLostData() throws Exception {
		return JLanguage.translate("Se perderán datos, continúa?");
	}
	public String getMessageConfirm(String msgDefa,String extra) throws Exception {
		Object params[]= {extra};
		return JLanguage.translate( msgDefa, params);
	}

	
	public String getToolbarListDefault() throws Exception {
		return JBaseWin.TOOLBAR_TOP;
	}
	public String getToolbarFormDefault() throws Exception {
		return JBaseWin.TOOLBAR_TOP;
	}

  public JMap<String,String> getPaginations(long maxSize) throws Exception {
    JMap<String,String> sizes = JCollectionFactory.createOrderedMap();
    if (maxSize==-1 || maxSize>10) sizes.addElement("10", "10");
    if (maxSize==-1 || maxSize>25) sizes.addElement("25", "25");
    if (maxSize==-1 || maxSize>50) sizes.addElement("50", "50");
    if (maxSize==-1 || maxSize>100) sizes.addElement("100", "100");
    if (maxSize==-1 || maxSize>200) sizes.addElement("200", "200");
    if (maxSize==-1) 
    	sizes.addElement("50000", "Todos");
    else
    	sizes.addElement(""+maxSize, ""+maxSize);
    return sizes;
  }

  
  public String getMessage(String zMsg, Object[] zParams) {
  		if (zMsg.toLowerCase().length()>9 && zMsg.toLowerCase().substring(0, 9).equals("clave inv")) 
  			zMsg = "ACCESO NO HABILITADO. La clave ingresada es incorrecta.";
  		if (zParams==null)
  			return JLanguage.translate(zMsg);
			return JLanguage.translate(zMsg,zParams);
  		
   }
  
	public String findCheckModeDefault() {
		return JFormCheckResponsive.MODE_TOGGLE;
	}

}