/*
 * Created on 09-jun-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.ui.skins.skin_siti;

import java.awt.Insets;

import pss.JPath;
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
import pss.core.winUI.responsiveControls.JFormPanelResponsive;
import pss.core.winUI.responsiveControls.JFormWinLOVResponsive;
import pss.www.platform.actions.JWebAction;
import pss.www.ui.JWebDivResponsive;
import pss.www.ui.JWebEditComponentContainer;
import pss.www.ui.JWebFilterPaneResponsive;
import pss.www.ui.JWebHResponsive;
import pss.www.ui.JWebIcon;
import pss.www.ui.JWebLink;
import pss.www.ui.JWebNavigationBarGroup;
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

	public String sourceColors() throws Exception {
		return "skins/sbadmin/css/siti.css";
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
		if (tipo.equals("menu"))
			return null;
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
		return JWebLink.POSICON_RIGHT;
	}

//	public void buildHeaderMenu(JWebView parent,JWebViewNavigationBar menu) throws Exception {
//	}
//
//	protected void fillHeader(JWebView parent) throws Exception {
//	}
	public void buildHeaderMenu(JWebView parent, JWebViewNavigationBar menu) throws Exception {
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

	public JWebViewInternalComposite buildWinList(JWebWinGenericResponsive parent) throws Exception {
		parent.setAllowSortedColumns(false);
		parent.setFilterBar(false);
		if (parent.isPreview())
			parent.setToolbar(JWins.TOOLBAR_TOP);
		return super.buildWinList(parent);
	}

	public JWebViewComposite buildForm(JWebWinForm parent) throws Exception {
		JWebViewZone zone = new JWebViewZone();
		JWebDivResponsive panel = new JWebDivResponsive();
		JWebHResponsive title = new JWebHResponsive(3, parent.getTitle());
		title.setIcon(JWebIcon.getPssIcon(GuiIconos.RESPONSIVE, parent.getWin().GetNroIcono()));
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
		if (parent.isToolbarLeft())
			panel.add("actionbar", parent.buildActionBar());
		panel.add("header", panelHeading);
		panel.add("body", panelBody);

		zone.add("panel", panel);

		panelHeading.add("title", title);
		if (parent.isToolbarTop())
			title.add("actionbar", parent.buildActionBar());

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
		}
		super.configureJFormFiltro(control);
	}

	public String getMessageConfirmLostData() throws Exception {
		return null;
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
		parent.addAlertFolder(null, JWebIcon.getResponsiveIcon("fa fa-fw fa-bell"), getMessage("Alertas", null), 1);
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

}
