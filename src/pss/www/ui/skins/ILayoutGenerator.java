package pss.www.ui.skins;

import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JMap;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.actions.BizActions;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.lists.JColumnaLista;
import pss.core.winUI.lists.JWinList;
import pss.core.winUI.responsiveControls.JFormControlResponsive;
import pss.www.platform.actions.JWebAction;
import pss.www.ui.JWebActionOwnerProvider;
import pss.www.ui.JWebDropDownComboResponsive;
import pss.www.ui.JWebDropDownWinLovResponsive;
import pss.www.ui.JWebEditComponentContainer;
import pss.www.ui.JWebFilterPaneResponsive;
import pss.www.ui.JWebFormFormResponsive;
import pss.www.ui.JWebHelp;
import pss.www.ui.JWebIcon;
import pss.www.ui.JWebLink;
import pss.www.ui.JWebLocalFormResponsive;
import pss.www.ui.JWebNavigationBarGroup;
import pss.www.ui.JWebPanelResponsive;
import pss.www.ui.JWebRowGridExpandResponsive;
import pss.www.ui.JWebTableExpandResponsive;
import pss.www.ui.JWebView;
import pss.www.ui.JWebViewComponent;
import pss.www.ui.JWebViewComposite;
import pss.www.ui.JWebViewHistoryBar;
import pss.www.ui.JWebViewInternalComposite;
import pss.www.ui.JWebViewNavigationBar;
import pss.www.ui.JWebWinActionBar;
import pss.www.ui.JWebWinForm;
import pss.www.ui.JWebWinGenericResponsive;
import pss.www.ui.JWebWinListFormLov;
import pss.www.ui.JWebWinOptionsResponsive;
import pss.www.ui.processing.JXMLRepresentable;

public interface ILayoutGenerator {

	public int getLeftMarginCanvas() throws Exception;
	public int getTopMarginCanvas() throws Exception;
	public int getBottomMarginCanvas() throws Exception;
	public int getRightMarginCanvas() throws Exception ;

	public void setPositionHelp(JWebHelp help) throws Exception ;
	public int getSplitPoint();
	public int getSplitPointEmbedded();
	public int getBorderWebWinForm();
	public String getSkinPath();
	
	public String sourceColors() throws Exception;
	public String source() throws Exception;
	public boolean useSimpleOrden(JWebWinGenericResponsive parent) throws Exception;

	public long getSizeIconTitle();
	public long getSizeIconNavigationBar();
	public long getSizeIconNotebook();
	public long getSizeIconActionBar(boolean bToolbarTop,boolean isWin, boolean bEmbbeded);
	public long getSizeMenu();
	
	public void configureJFormFiltro(JFormControl control) throws Exception ;
	public void configureJFormItem(JFormControl control) throws Exception ;

	public int getNotebooksOffset();
	
	public boolean isActionWithIcons(String pos, boolean embedded);
	public boolean isActionUpperCase(String pos, boolean embedded);
	public boolean isActionWithLabels(String pos, boolean embedded);

	public void customizeComponents(JFormControl control);
	
	public boolean needOldCss() throws Exception;
	
	public String getExpandAllSubDetail();
	public String getMoreSubDetail() ;
	
	public int getGapWinActionBarTopH();
	public int getGapWinActionBarTopV();
	public int getGapWinActionBarLeftH();
	public int getGapWinActionBarLeftV();
	public String[] getColorGraph();
	public int getMargenCanvasHeight();
	public int getMargenCanvasWidth();
	public long getSizeWindowFormLovHeight();
	public long getSizeWindowFormLovWidth();
	public boolean hasHistory();
	
	public boolean attachBackActionToToolbar(String zone);
	public String getColumnClass(JFormControlResponsive control,String strColumns);
	public JWebViewComponent getUserMessage(JWebViewComposite parent,String message) throws Exception;
	public JXMLRepresentable buildOrderColumn(JWebViewComposite parent,boolean column,String order,JWebAction webAction) throws Exception;
	public JWebViewComponent buildOrderButtonInFilter(JWebViewComposite parent,JWebAction webAction) throws Exception;
	
	public boolean canClickInAllHeaderForOrder() throws Exception;
	
	public String getClassField(JWebViewComponent component,JWebEditComponentContainer form,String force) throws Exception;
	public String getClassLabel(JWebViewComponent component,JWebEditComponentContainer form,String force) throws Exception;
	public String getClassLinkLabel(JWebViewComponent component,JWebEditComponentContainer form,String force) throws Exception;
	public String getPanelDefault() throws Exception;
	public String menuPosPrincipal()  throws Exception;
	public JWebViewInternalComposite getJumbotron(JWebView parent) throws Exception;
	public JWebViewNavigationBar getNavBar(JWebViewComposite parent) throws Exception;
	public void buildHeaderMenu(JWebView parent,JWebViewNavigationBar menu) throws Exception;
	public JWebViewNavigationBar generateMenu(JWebView parent,JWebViewNavigationBar menu,BizActions actions) throws Exception;
	public JWebNavigationBarGroup addNavigationGroup(JWebView parent,JWebNavigationBarGroup group, JWebIcon zIcon,String name, long level) throws Exception;
	public void addNavigationAction(JWebView parent,JWebNavigationBarGroup group, String zAction, JWebIcon zIcon, String label, boolean newwindow)	throws Exception;
	public void addNavigationAction(JWebView parent,JWebNavigationBarGroup group, String zAction, JWebIcon zIcon, String label, boolean newwindow, String confirmation)	throws Exception;
	public void addNavigationAction(JWebView parent,JWebNavigationBarGroup group, JWebAction zAction, JWebIcon zIcon, String label, boolean newwindow)	throws Exception;
  public void fillNavBar(JWebView parent) throws Exception;
	public JWebViewNavigationBar getMenuBar(JWebViewComposite parent)  throws Exception;
	public JWebViewHistoryBar getHistoryBar(JWebViewComposite parent) throws Exception;
	public boolean isFilterInLine() throws Exception;
	
	public JWebViewComponent getNavigationAction(JWebViewComposite parent,JWebAction zAction, JWebIcon zIcon) throws Exception;
	public JWebViewComponent getHistoryAction(JWebViewComposite parent,JWebAction zAction, JWebIcon zIcon) throws Exception;
	public JWebViewComponent getHistoryMessage(JWebViewComposite parent,String message) throws Exception;
	public JWebViewComponent createBackButton(JWebViewComposite parent,JWebAction zAction) throws Exception;
	public JWebViewComposite buildForm(JWebWinForm parent) throws Exception;
	public JWebViewComposite buildForm(JWebFormFormResponsive parent) throws Exception;
	public JWebViewComposite buildLocalForm(JWebLocalFormResponsive parent) throws Exception;
	public JWebViewInternalComposite buildOptions(JWebWinOptionsResponsive parent) throws Exception;
	public String buildClassOptions(JWebWinOptionsResponsive parent,JWin win) throws Exception;
	public JWebViewInternalComposite buildSwapList(JWebWinGenericResponsive parent) throws Exception;
	public JWebViewInternalComposite buildWinList(JWebWinGenericResponsive parent) throws Exception;
	public JWebViewInternalComposite buildWinGrid(JWebWinGenericResponsive parent) throws Exception;
	public void buildPreConfigurarColumnaLista(JWins webwin,JWinList winList) throws Exception;
	public void buildPosConfigurarColumnaLista(JWins webwin,JWinList winList) throws Exception;
	public JWebLink getFindButton(JWebViewComposite parent,JWebAction webAction) throws Exception;
	public String getClassActionWinList(JWin win) throws Exception;
	public boolean withCaretActionWinList(JWin win) throws Exception;
	public JWebPanelResponsive buildGridtable(JWebTableExpandResponsive table) throws Exception;
	public JWebRowGridExpandResponsive buildGridRow(JWebRowGridExpandResponsive row, String mode) throws Exception;
	public String getWidthAutocompleteColumn(JColumnaLista[] columns) throws Exception;
	public JWebViewComposite createFooterBar(JWebWinGenericResponsive parent,JWebViewInternalComposite infoBar,JWebViewInternalComposite navBar,JWebViewInternalComposite exortBar,JWebViewInternalComposite extraBar1,JWebViewInternalComposite extraBar2) throws Exception;
	public JWebViewInternalComposite createInfoBar(JWebViewComposite parent, String providerName) throws Exception;
	public JWebViewInternalComposite createExportBar(JWebViewComposite parent, String providerName) throws Exception;
	public JWebViewInternalComposite createFooterExtraBar1(JWebViewComposite parent, String providerName) throws Exception;
	public JWebViewInternalComposite createFooterExtraBar2(JWebViewComposite parent, String providerName) throws Exception;
	public JWebViewInternalComposite createNavigationBar(JWebViewComposite parent, String providerName) throws Exception;
	public JWebViewInternalComposite createPaginationBar(JWebViewComposite parent, boolean mini, String providerName) throws Exception;
	public void createBoton(JWebWinActionBar parent,String actionId,BizAction zAction, JWebAction zWebAction) throws Exception;
	public void createBotonInMore(JWebWinActionBar parent,JWebViewComposite moreButton,String idAction,BizAction zAction, JWebAction zWebAction) throws Exception;
	public JWebFilterPaneResponsive buildFormFilter(JWebWinGenericResponsive parent) throws Exception;
	public JWebViewInternalComposite createPreferenceBar(JWebViewComposite parent, String providerName) throws Exception;
	public JWebViewComponent createBotonPreference(JWebViewComposite parent,String name,JWebAction action) throws Exception;

	public JWebViewComposite createBotonMore(JWebWinActionBar parent,boolean isWinList,boolean isForm,boolean isFormLov) throws Exception;	
	public boolean actionsInMore(JWebWinActionBar parent,BizAction zAction, JWebAction zWebAction) throws Exception;
	public JWebViewInternalComposite createActionBarWinList(JWebWinGenericResponsive zObjectProvider) throws Exception;
	public JWebViewInternalComposite createActionBarForm(JWebWinForm zObjectProvider) throws Exception;
	public JWebViewInternalComposite createActionBarForm(JWebDropDownComboResponsive zObjectProvider) throws Exception;
	public JWebViewInternalComposite createActionBarForm(JWebDropDownWinLovResponsive zObjectProvider) throws Exception;
	public JWebViewInternalComposite createActionBarForm(JWebRowGridExpandResponsive zObjectProvider) throws Exception;
	public JWebViewInternalComposite createActionBarFormLov(JWebWinListFormLov zObjectProvider, boolean zForForm) throws Exception;
	public JWebViewComponent createCombo(JWebViewComposite parent,String name,String label, boolean active,JWebAction webAction,String provider,String defa,JControlCombo combo) throws Exception;
	
	public void addAtributesToFilterControls(JWebViewInternalComposite parent,JFormControlResponsive component) throws Exception;
	public JIterator<JFormControl> filtersResorted(JIterator<JFormControl> iter) throws Exception;
	
	public String getRowClass()  throws Exception;
	public JWebViewComponent createHideShow(JWebViewComposite parent,String name,boolean state,JWebAction action, String label, String dataon,String dataoff) throws Exception;
	public JWebViewComponent createBotonPagination(JWebViewComposite parent,String name,JWebAction action,String label,boolean active) throws Exception;
	public JWebViewComponent createBotonExport(JWebViewComposite parent,String name,JWebAction action) throws Exception;
	public JWebViewComponent createToggleInMenu(JWebViewComposite parent,String name,boolean state,JWebAction action,String label, String dataon,String dataoff) throws Exception;
	public JWebViewComponent createInfo(JWebViewComposite parent,String name,JWebAction action) throws Exception;

	public void buildHeaderExtraBar1(JWebWinGenericResponsive parent) throws Exception;
	public void buildNavigationBar(JWebWinGenericResponsive parent,JWebViewInternalComposite oNavigationBar) throws Exception;
	public void buildExportBar(JWebWinGenericResponsive parent,JWebViewInternalComposite oExportBar) throws Exception;
	public void buildInfoBar(JWebWinGenericResponsive parent,JWebViewInternalComposite oInfoBar) throws Exception;
	public void buildFooterExtraBar1(JWebWinGenericResponsive parent,JWebViewInternalComposite oExtraBar) throws Exception;
	public void buildFooterExtraBar2(JWebWinGenericResponsive parent,JWebViewInternalComposite oExtraBar) throws Exception;

	public BizAction createActionCancel(JWebActionOwnerProvider zObjectProvider) throws Exception;
	public BizAction createActionBack(JWebActionOwnerProvider zObjectProvider) throws Exception;
	public String getMessageConfirmLostData() throws Exception;
	public String getMessageConfirm(String msgDefa,String extra) throws Exception;
	public String getToolbarListDefault() throws Exception;
	public String getToolbarFormDefault() throws Exception;

  public JMap<String,String> getPaginations(long maxSize) throws Exception;
  public String getMessage(String zMsg, Object[] zParams);
  public String findCheckModeDefault() throws Exception;

}