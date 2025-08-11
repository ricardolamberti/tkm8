package pss.www.ui;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import org.jsoup.Jsoup;

import pss.common.regions.company.JCompanyBusiness;
import pss.common.regions.multilanguage.JLanguage;
import pss.common.security.BizUsuario;
import pss.core.data.BizPssConfig;
import pss.core.graph.Graph;
import pss.core.graph.TrendLines;
import pss.core.graph.analize.AnalizeDataGraph;
import pss.core.graph.analize.Categories;
import pss.core.graph.analize.Dataset;
import pss.core.graph.analize.Value;
import pss.core.services.fields.JObjBDs;
import pss.core.services.fields.JObject;
import pss.core.services.fields.JString;
import pss.core.services.records.JFilterMap;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.JDateTools;
import pss.core.tools.JExcepcion;
import pss.core.tools.JPair;
import pss.core.tools.JTools;
import pss.core.tools.PssLogger;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JList;
import pss.core.tools.collections.JMap;
import pss.core.win.JBaseWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.controls.JFormControles;
import pss.core.winUI.controls.JFormLista;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.icons.GuiIcon;
import pss.core.winUI.icons.GuiIconos;
import pss.core.winUI.lists.JColumnaLista;
import pss.core.winUI.lists.JFormFiltro;
import pss.core.winUI.lists.JGrupoColumnaLista;
import pss.core.winUI.lists.JWinList;
import pss.core.winUI.responsiveControls.JFormControlResponsive;
import pss.www.platform.actions.JWebAction;
import pss.www.platform.actions.JWebActionFactory;
import pss.www.platform.actions.JWebJSonAction;
import pss.www.platform.actions.JWebRequest;
import pss.www.platform.actions.JWebServerAction;
import pss.www.platform.actions.requestBundle.JWebActionData;
import pss.www.platform.applications.JHistoryProvider;
import pss.www.platform.content.generators.JXMLContent;
import pss.www.ui.controller.JWebFormControl;
import pss.www.ui.processing.JXMLRepresentable;

public abstract class JWebWinGenericResponsive extends JWebViewInternalComposite implements JWebActionOwnerProvider, IWebWinGeneric {

	public static final String PAGETYPE_LANDSCAPE = "PAGETYPE_LANDSCAPE";
	public static final String PAGETYPE_NORMAL = "PAGETYPE_NORMAL";
	
	//
	// INSTANCE VARIABLES
	//
	protected JWins oWins;
	protected JIterator iterator;
	protected int iPageSize = -1;
	protected JWebViewInternalComposite oNavigationBar;
	protected JWebViewInternalComposite oInfoBar;
	protected JWebViewInternalComposite oExportBar;
	protected JWebViewInternalComposite oFooterExtraBar1;
	protected JWebViewInternalComposite oFooterExtraBar2;
	public JWebWinActionBar oActionBar;
	protected boolean bAllowExportToExcel = true;
	protected boolean bAllowExportToCSV = true;
	protected boolean bAllowExportToReport = true;
	protected JWebViewInternalComposite oRootPanel;
	protected String winsObjectId = "";
	protected String winsObjectDest = "";
	protected boolean bReadOnly = false;
	protected boolean isInForm = false;
	protected boolean selectedRows = false;
	protected Serializable data;






	boolean needRefreshAllScreen = false;
	protected String title;
	protected String titleDefault;
	protected String distributionObjects;
  protected boolean listResponsive= true;
  protected boolean ordening= true;
  protected boolean lengthChange= true;
  protected boolean searching= true;
	protected boolean headerInFooter = false;
	protected long zoomtofit;

	protected String mode;

	// protected String winObjectId = "";
	protected String sourceObjectId = "";
	protected boolean bOnlyWinListTablePanel = false;
	// protected boolean bShowActionBar = true;
	private boolean bEmbedded = false;
	private boolean bFormLov = false;

	private boolean bPreview = false;
	protected boolean bImportant = false;
	protected boolean bMultipleSelection = false;
	protected boolean bLineSelect = false;
	protected Boolean forceSelected = false;

	protected JFormFiltro formFiltros = null;
	protected JWinList winList = null;

	protected AnalizeDataGraph adg = new AnalizeDataGraph();
	protected String notebookParent = "";
	protected long totalCountElement = -1;
	protected int widthBorder = 0;
	protected int splitPoint;
	protected BizAction sourceAction;
	protected boolean embeded;
	protected String campo;
	protected JWin winCampo;
	protected Class classWinsCampo;
	protected JWebActionOwnerProvider parentProvider;
	protected JWebLink filterBotton = null;
	protected JWebAction actionDefault = null;
	protected boolean bBackBotton = true;
	protected boolean bObjectRegistred = false;
	protected int iFromRow = -1;
	protected int iSizePageActual = -1;
	protected int iPaginaActual = -1;
	protected String paginado = null;
	protected String sAll = "N";
	
	protected String columnOrden = null;
	protected String direccionOrden = null;
	protected List<JPair<String,String>> advanceColumnOrden = null;
	
	protected String search = null;
	// protected int iContainer = 0;
	// int iFromRow = -1;
	protected boolean bShowFilterBar = true;
	protected boolean bShowPaginationBar = true;
	protected boolean bIsModifiedOnServer = false;



	protected boolean allowSortedColumns = true;


	protected Boolean bUseContextMenu = null;
	protected boolean bShowFooterBar = true;

	protected String posFunction=null;
	private long extraRows=10;
	private long minumusRows=10;
	
	private JMap<String,JWebAction> actionsBase;

	
	public JMap<String, JWebAction> getActionsBase() {
		if (actionsBase==null)
			actionsBase = JCollectionFactory.createMap();
		return actionsBase;
	}
	public JWebAction addActionsBase(String event,JWebAction actionBase) throws Exception {
		if (actionBase==null) return null;
		JWebAction act = this.getActionsBase().getElement(event);
		if (act==null) {
		 this.getActionsBase().addElement(event, actionBase);
		 return null;
		}
		return actionBase.getUniqueKey().equals(act.getUniqueKey())?null:actionBase;
	}
	
	public long getMinumusRows() {
		return minumusRows;
	}
	public void setMinimusRows(long minumusRows) {
		this.minumusRows = minumusRows;
	}
	public long getExtraRows() {
		return extraRows;
	}
	public void setExtraRows(long extraRows) {
		this.extraRows = extraRows;
	}
	public String getPosFunction() {
		return posFunction;
	}
	public void setPosFunction(String s) {
		this.posFunction=s;
	}
	public boolean hasPosFunction() {
		return this.posFunction!=null;
	}

	public void setInForm(boolean isInForm) {
		this.isInForm = isInForm;
	}
	public boolean IsModifiedOnServer() {
		return bIsModifiedOnServer;
	}
	public void setModifiedOnServer(boolean bIsModifiedOnServer) {
		this.bIsModifiedOnServer = bIsModifiedOnServer;
	}

	public boolean supportHtml() throws Exception {
		return !getWins().isInMobile();
	}
	public boolean supportSpans() throws Exception {
		return !getWins().isInMobile();
	}
	
	public boolean isShowFooterBar() {
//		if (winList!=null)
//			return bShowFooterBar && winList.isWithFooter();
		return bShowFooterBar;
	}

	public void setShowFooterBar(boolean bShowFooterBar) {
		this.bShowFooterBar = bShowFooterBar;
	}
	public boolean isAllowSortedColumns() {
		return allowSortedColumns;
	}

	public void setAllowSortedColumns(boolean allowSortedColumns) {
		this.allowSortedColumns = allowSortedColumns;
	}
	public boolean isSelectedRows() {
		return selectedRows;
	}
	public void setSelectedRows(boolean selectedRows) {
		this.selectedRows = selectedRows;
	}

	private boolean bNavigationBar=true;
	public void setbNavigationBar(boolean v) {
		this.bNavigationBar = v;
	}
	
	public boolean isNavigationBar() {
		return this.bNavigationBar;
	}


	public Boolean isUseContextMenu() throws Exception {
		if (bUseContextMenu==null) {
			if (BizUsuario.getUsr()!=null)
				if (JCompanyBusiness.getDefiniciones(JCompanyBusiness.USECONTEXTMENU)==null)
					return false;
				return JCompanyBusiness.getDefiniciones(JCompanyBusiness.USECONTEXTMENU).equals("S");
		}
		return bUseContextMenu;
	}

	public void setUseContextMenu(Boolean bUseContextMenu) {
		this.bUseContextMenu = bUseContextMenu;
	}



	protected boolean bNothingToShowInFilterBar = true;
	protected boolean bShowSearchButton = true;
	
	protected boolean isWinListFullExportRequest;
	protected boolean isHtmlRequest;
	protected long minLineas=5;
	protected long maxLineas=-1;

	public boolean isModal() throws Exception { //RJL. mejorar, necesito saber si tiene que volver a un modal o a una no modal.
//		if (getSourceAction().isModal()) 
//			return true;
		if (JWebActionFactory.getCurrentRequest().getSession().getHistoryManager().getLastLastHistory()==null)
			return false;
		return JWebActionFactory.getCurrentRequest().getSession().getHistoryManager().getLastLastHistory().getFirstAction().isModal();
	}
	public long getMinLineas() {
		if (JWebActionFactory.isMobile()) return 1;
		
		return minLineas;
	}

	public String getPreviewSplitPos() throws Exception {
		if (!hasWins()) return null;
		return getWins().getPreviewSplitPos();
	}

	public void setMinLineas(long minLineas) {
		this.minLineas = minLineas;
	}
	
	public void setMaxLineas(long minLineas) {
		this.maxLineas = minLineas;
	}

	public long getMaxLineas() throws Exception {
		if (maxLineas==-1) {
			return getWins().getRecords().getMaxNumberRowsPerPage();
		}
		return maxLineas;
	}
	@Override
	public BizAction getSourceAction() {
		return sourceAction;
	}

	@Override
	public String getSourceObjectId() {
		return this.sourceObjectId;
	}

	@Override
	public String getRegisteredObjectId() throws Exception {
		return winsObjectId;
	}
	
	public JWinList getWinList() {
		return winList;
	}

	public void setWinList(JWinList winList) {
		this.winList = winList;
	}
	protected boolean bForceHidePaginationBar = false;
	
	public boolean isForceHidePaginationBar() {
		return bForceHidePaginationBar;
	}

	public void setForceHidePaginationBar(boolean bForceHidePaginationBar) {
		this.bForceHidePaginationBar = bForceHidePaginationBar;
	}
	@Override
	public void regiterObjects() throws Exception {
		// se agrega el id de Objecto de la accion padre
		if (this.bObjectRegistred) return;
		winsObjectId = JWebActionFactory.registerObject(this.getWins());
		if (sourceAction!=null && sourceAction.hasOwner()) { 
				sourceObjectId = JWebActionFactory.registerObject(this.sourceAction.getObjOwner());
		} 
	
	
		this.bObjectRegistred=true;
	}
protected String sToolbar = null;
protected Boolean sShowFilterBar = true;

	@Override
	public void setToolbar(String value) {
		this.sToolbar = value;
	}
	
	public void setFilterBar(Boolean value) {
		this.sShowFilterBar = value;
	}

	public boolean isNothingToShowInFilterBar() {
		return bNothingToShowInFilterBar;
	}

	public void setNothingToShowInFilterBar(boolean bNothingToShowInFilterBar) {
		this.bNothingToShowInFilterBar = bNothingToShowInFilterBar;
	}

	public boolean isShowSearchButton() {
		return bShowSearchButton;
	}

	public void setShowSearchButton(boolean showSearchButton) {
		this.bShowSearchButton = showSearchButton;
	}

	@Override
	public String getToolbar() throws Exception {
		if (this.hasWins() && this.getWins().hasToolbarForced())
			return this.getWins().getToolbarForced();
		if (this.sToolbar != null)
			return this.sToolbar;
		if (this.winList!=null && this.winList.getToolbar()!=null)
			return this.winList.getToolbar();
		String toolbar = null;
		String toolbarEmbbeded = null;
		if (BizUsuario.getUsr()!=null) {
			toolbar= BizUsuario.getUsr().getObjBusiness().getWinListToolbarPosDefault();
			toolbarEmbbeded= BizUsuario.getUsr().getObjBusiness().getWinListToolbarEmbbededPorDefault();
		}
		toolbarEmbbeded=(toolbarEmbbeded!=null)?toolbarEmbbeded:JBaseWin.TOOLBAR_IN;
		toolbar=(toolbar!=null)?toolbar:JBaseWin.TOOLBAR_LEFT;
		return this.isEmbedded()?toolbarEmbbeded:toolbar;
	}

	public String getPaginado() throws Exception {
		return this.paginado;
	}

	public long getPaginaActual() throws Exception {
		return this.iPaginaActual;
	}

	public boolean isToolbarBottom() throws Exception {
		return this.getToolbar().equals(JBaseWin.TOOLBAR_BOTTOM);
	}

	public boolean isToolbarTop() throws Exception {
		return this.getToolbar().equals(JBaseWin.TOOLBAR_TOP);
	}

	public boolean isToolbarLeft() throws Exception {
		return this.getToolbar().equals(JBaseWin.TOOLBAR_LEFT);
	}
	public boolean isToolbarIn() throws Exception {
		return this.getToolbar().equals(JBaseWin.TOOLBAR_IN);
	}

	public boolean isToolbarRight() throws Exception {
		return this.getToolbar().equals(JBaseWin.TOOLBAR_RIGHT);
	}

	@Override
	public boolean isToolbarNone() throws Exception {
		return this.getToolbar().equals(JBaseWin.TOOLBAR_NONE);
	}

	public static int BUILD_COMPLETE = 1;
	public static int BUILD_EXPAND = 2;
	public static int BUILD_CENTER = 3;
	private int iBuild = 1;

	@Override
	public void setBuild(int value) {
		this.iBuild = value;
	}

	protected boolean isBuildComplete() throws Exception {
		return this.iBuild == BUILD_COMPLETE;
	}

	protected boolean isBuildExpand() throws Exception {
		return this.iBuild == BUILD_EXPAND;
	}

	protected boolean isBuildCenter() throws Exception {
		return this.iBuild == BUILD_CENTER;
	}
	
	public boolean isMultipleSelection() {
		return bMultipleSelection;
	}

	public void setMultipleSelection(boolean multipleSelection) {
		bMultipleSelection = multipleSelection;
	}
	public boolean isZoomtofit() {
		return zoomtofit>0;
	}

	public void setZoomtofit(long zoomtofit) {
		this.zoomtofit = zoomtofit;
	}
	public long getZoomtofit() {
		return this.zoomtofit;
	}
	public boolean isLineSelect() {
		return bLineSelect;
	}

	public void setLineSelect(boolean lineSelect) {
		bLineSelect = lineSelect;
	}

	public void setForceSelected(boolean value) {
		forceSelected = value;
	}

	public boolean isHeaderInFooter() {
		return headerInFooter;
	}

	public void setHeaderInFooter(boolean headerInFooter) {
		this.headerInFooter = headerInFooter;
	}

	@Override
	public void setBackBotton(boolean value) {
		this.bBackBotton = value;
	}

 protected void releaseWins() {
	 oWins=null;
 }
	@Override
	public JWins getWins() throws Exception {
		if (oWins!=null) return oWins;
		if (this.campo!=null) return getWinsFromCampo();
		return getWinsFromAction();
	}		


	private JWins getWinsFromAction() throws Exception {
		if (this.sourceAction != null) {
			this.oWins = sourceAction.getObjSubmit().getWinsResult();
		  bIsModifiedOnServer = sourceAction.getObjSubmit().isModifiedOnServer();
		}
		if (oWins==null) return null;

		JWins oldSelect = JWebActionFactory.getCurrentRequest().getSession().getHistoryManager().findLastMultipleSelect(this.sourceAction);
		boolean selectAny = (oldSelect == null) ? false : oldSelect.getRecords().getStaticItems().size() > 0;

		this.getWins().clearActions();
		if (!selectAny)
			this.forceSelected = this.getWins().isForceSelected(isEmbedded(), isFormLov(), oWins.hasDropListener());

		return this.oWins;

	
	}
	private JWins getWinsFromCampo() throws Exception {
		this.oWins = (JWins) classWinsCampo.newInstance();
		JObjBDs objs = ((JObjBDs)winCampo.getRecord().getProp(this.campo));
		bIsModifiedOnServer = objs.isModifiedOnServer();
		JRecords recs= objs.getRawValue();
		if (recs==null) recs = ((JObjBDs)winCampo.getRecord().getProp(this.campo)).getValue();
		this.oWins.setRecords(recs);
		this.oWins.setParent(winCampo);
		
		JWins oldSelect = JWebActionFactory.getCurrentRequest().getSession().getHistoryManager().findLastMultipleSelect(this.sourceAction);
		boolean selectAny = (oldSelect == null) ? false : oldSelect.getRecords().getStaticItems().size() > 0;

		if (this.oWins != null) {
			this.getWins().clearActions();
			if (!selectAny)
				this.forceSelected = this.getWins().isForceSelected(isEmbedded(), isFormLov(), this.getWins().hasDropListener());
		}
		return oWins;
	}
	
	
	@Override
	public void setParentProvider(JWebActionOwnerProvider p) {
		this.parentProvider = p;
	}

	public JWebActionOwnerProvider getParentProvider() {
		return this.parentProvider;
	}
	@Override
	public boolean hasWins() {
		try {
			return this.getWins() != null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	protected void build() throws Exception {

		oRootPanel = BizUsuario.retrieveSkinGenerator().buildWinList(this);
	
	//	this.createRootPanel(comp);
	}


	public boolean hasNavigationBar() throws Exception {
		return oNavigationBar!=null;
	}

	public boolean isFalseInternalToolbar() {
		return true;
	}


	public JWebViewComposite createFooterBar() throws Exception {
		return BizUsuario.retrieveSkinGenerator().createFooterBar(this,this.oInfoBar,this.oNavigationBar,this.oExportBar,this.oFooterExtraBar1,this.oFooterExtraBar2);
	}

	public JWebViewInternalComposite createExportBar() throws Exception {
		return this.oExportBar=BizUsuario.retrieveSkinGenerator().createExportBar(this, this.getProviderName());
	}
	public JWebViewInternalComposite createFooterExtraBar1() throws Exception {
		return this.oFooterExtraBar1=BizUsuario.retrieveSkinGenerator().createFooterExtraBar1(this, this.getProviderName());
	}
	public JWebViewInternalComposite createFooterExtraBar2() throws Exception {
		return this.oFooterExtraBar2=BizUsuario.retrieveSkinGenerator().createFooterExtraBar2(this, this.getProviderName());
	}
	public JWebViewInternalComposite createInfoBar() throws Exception {
		return this.oInfoBar=BizUsuario.retrieveSkinGenerator().createInfoBar(this, this.getProviderName());
	}

	public JWebViewInternalComposite createNavigation() throws Exception {
		return this.oNavigationBar=BizUsuario.retrieveSkinGenerator().createNavigationBar(this, this.getProviderName());
	}
	
	protected void componentToXMLGraph(JXMLContent zContent) throws Exception {
		zContent.startNode("graph");
		if (adg.getGraphRefresh() != -1)
			zContent.setAttribute("refresh", adg.getGraphRefresh());
		JIterator<Graph> itGr = winList.getGraficosLista().getIterator();
		while (itGr.hasMoreElements()) {
			Graph gr = itGr.nextElement();
			zContent.startNode("graphic");
			zContent.setAttribute("title", gr.getTitle());
			zContent.setAttribute("swf", gr.getSWF());
			zContent.setAttribute("maxvalue", gr.getMaxValue());
			zContent.setAttribute("model", gr.getModel().getName());

			JIterator<String> iatr = gr.getAtributtes().getKeyIterator();
			while (iatr.hasMoreElements()) {
				String key = iatr.nextElement();
				String value = gr.getAtributtes().getElement(key);
				zContent.startNode("attribute");
				zContent.setAttribute("name", key);
				zContent.setAttribute("value", value);
				zContent.endNode("attribute");
			}

			JIterator<Categories> icat = gr.getCategories().getValueIterator();
			while (icat.hasMoreElements()) {
				Categories cat = icat.nextElement();
				zContent.startNode("categories");
				zContent.setAttribute("name", cat.getName());
				zContent.setAttribute("attributes", cat.getAttributesAsString());
				zContent.endNode("categories");
			}

			JIterator<Dataset> ids = gr.getDatasets().getValueIterator();
			while (ids.hasMoreElements()) {
				Dataset ds = ids.nextElement();
				zContent.startNode("dataset");
				zContent.setAttribute("name", ds.getColname());
				zContent.setAttribute("color", ds.getColor());
				zContent.setAttribute("parentYAxis", ds.getParentYAxis());
				zContent.setAttribute("attributes", ds.getAttributesAsString());
				JIterator<Value> iv = ds.getValues().getValueIterator();
				while (iv.hasMoreElements()) {
					Value v = iv.nextElement();
					zContent.startNode("value");
					zContent.setAttribute("value", v.getData().toString());
					zContent.setAttribute("categorie", v.getCategorie());
					zContent.setAttribute("attributes", v.getAttributesAsString());
					zContent.endNode("value");
				}
				zContent.endNode("dataset");
			}

			JIterator<TrendLines> itl = gr.getMapTrendLines().getValueIterator();
			while (itl.hasMoreElements()) {
				TrendLines tl = itl.nextElement();
				zContent.startNode("trendlines");
				zContent.setAttribute("displayvalue", tl.getDisplayValue());
				zContent.setAttribute("startvalue", tl.getStartValue());
				zContent.setAttribute("endvalue", tl.getEndValue());
				zContent.setAttribute("color", tl.getColor());
				zContent.setAttribute("showontop", tl.isShowOnTop() ? "1" : "0");
				zContent.setAttribute("trendzone", tl.isTrendZone() ? "1" : "0");
				zContent.setAttribute("alpha", tl.getAlpha());
				zContent.setAttribute("thickness", tl.getThickness());
				zContent.endNode("trendlines");
			}
			zContent.endNode("graphic");
		}
		zContent.endNode("graph");
	}
	
	protected void componentToHelpXML(JXMLContent zContent) throws Exception {
		zContent.setAttribute("title", getTitle());
		zContent.setAttribute("copete", getHelp());

		super.componentToHelpXML(zContent);
		this.actionsToHelpXML(JLanguage.translate("Acciones de ")+JLanguage.translate(this.getTitle()), this.getWins(), zContent);
		JWin winRef = this.getWins().getWinRefWithActions();
		this.actionsToHelpXML(JLanguage.translate("Acciones de ")+JLanguage.translate(winRef.GetTitle()), winRef, zContent);
	
	}
	

	public JWebViewComposite createNavigationBar() throws Exception {
		if (!isShowPagination()) return null;
		if (!(this.hasWins() && this.getWins().hasNavigationBar())) return null;
		this.oInfoBar = createInfoBar();
		this.oExportBar = createExportBar();
		this.oNavigationBar = createNavigation();
		this.oFooterExtraBar1 = createFooterExtraBar1();
		this.oFooterExtraBar2 = createFooterExtraBar2();
		return createFooterBar();
	}
	public String getTitle() throws Exception {
		if (title!=null&&!title.equals("")) return title;
		if (getWins().hasTitle()) return getWins().GetTitle();
		if (titleDefault!=null&&!titleDefault.equals("")) return titleDefault;
		return "";
	}

	public void setTitle(String title) {
		this.title = title;
	}
	public String getTitleDefault() {
		return titleDefault;
	}
	public void setTitleDefault(String titleDefault) {
		this.titleDefault = titleDefault;
	}

	public String getDistributionObjects() {
		if (mode!=null) {
			if (mode.equals(JFormLista.MODE_NORMAL)) return "<\"pull-left\"lf><\"toolbar pull-right\">rtip";
			if (mode.equals(JFormLista.MODE_DESPOJADO)) return "<\"pull-left\"lf><\"toolbar pull-right\">rtip";
			if (mode.equals(JFormLista.MODE_VERTICAL)) return "<\"pull-left\"lf><\"toolbar pull-right\">rtip";
			if (mode.equals(JFormLista.MODE_CUADRICULA)) return "<\"pull-left\"lf><\"toolbar pull-right\">rtip";
			if (mode.equals(JFormLista.MODE_ONLYTABLE)) return "tip";
		}
		if (distributionObjects==null) return "<\"pull-left\"lf><\"toolbar pull-right\">rtip";/*&lt;\"pull-left\"lf&gt;&lt;\"toolbar pull-right\"&gt;rtip*/
		
		return distributionObjects;
	}

	public void setDistributionObjects(String distributionObjects) {
		this.distributionObjects = distributionObjects;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}


	public boolean isListResponsive() throws Exception {
		if (isPreview()) return true;
		if (!hasPreviewPanel()) return false;
		return listResponsive;
	}

	public void setListResponsive(boolean responsive) {
		this.listResponsive = responsive;
	}
	public boolean isOrdeningControl() {
		if (mode!=null && mode.equals(JFormLista.MODE_DESPOJADO)) return false;	
		if (mode!=null && mode.equals(JFormLista.MODE_VERTICAL)) return false;	
		return ordening;
	}

	public void setOrdeningControl(boolean ordening) {
		this.ordening = ordening;
	}

	public boolean isLengthChangeControl() {
		if (mode!=null && mode.equals(JFormLista.MODE_DESPOJADO)) return false;	
		if (mode!=null && mode.equals(JFormLista.MODE_VERTICAL)) return false;	
		return lengthChange;
	}

	public void setLengthChangeControl(boolean lengthChange) {
		this.lengthChange = lengthChange;
	}

	public boolean isSearchingControl() {
		if (mode!=null && mode.equals(JFormLista.MODE_DESPOJADO)) return false;	
		if (mode!=null && mode.equals(JFormLista.MODE_VERTICAL)) return false;	
		return searching;
	}

	public void setSearchingControl(boolean searching) {
		this.searching = searching;
	}

	
	public String getClassTableResponsive() {
		if (mode!=null) {
			if (mode.equals(JFormLista.MODE_NORMAL)) return "table-responsive";	
			if (mode.equals(JFormLista.MODE_DESPOJADO)) return "table table-condensed";	
			if (mode.equals(JFormLista.MODE_VERTICAL)) return "table table-vertical";	
			if (mode.equals(JFormLista.MODE_CUADRICULA)) return "table table-bordered ";//"table table-striped table-hover dataTable no-footer dtr-inline";	
			if (mode.equals(JFormLista.MODE_CUSTOMLIST)) return "table table-bordered table-customlist";
		}
		if (!hasRawClassResponsive()) return "table  table-bordered";//"table table-striped";
		return "table";
	}

	@Override
	public boolean isFirstAccess() throws Exception {
		if (this.getWins().dontUseFilterDefault())
			return false; 
		if (this.sourceAction==null)
			return false;
		if (this.sourceAction.isExport())
			return false;
		return this.sourceAction.isFirstAccess();
	}

	public boolean hasCorteControl() throws Exception {
		return this.getWins().getRecords().hasCorteControl();
	}
	
	public void recoveryFiltersInHistory() throws Exception {
		JHistoryProvider p = JWebActionFactory.getCurrentRequest().getSession().getHistoryManager().getLastHistory().findProvider(this.sourceAction);
		if (p==null) return;
		JFilterMap map = p.getAction().getFilterMap();
		if (map==null) return;
		sourceAction.setObjFilterMap(map);
		sourceAction.setFirstAccess(false); // no deberia usarse mas
		sourceAction.setSubmitedByUser(p.isNavSubmitedByUser());
	}
	
	public void fillParentInfo(JFormControl c) throws Exception {
		
	}
	public JWebFilterPaneResponsive createFormFiltros() throws Exception {
		if (this.getWins()== null)
			return null;
		if (!getWins().isShowFilters())
			return null;

//		recoveryFiltersInHistory();

		
		this.formFiltros = new JFormFiltro(this.getWins());
		this.formFiltros.initialize();
//		this.formFiltros.convertToResponsive();
		Boolean visible = this.isShowFilterBarInternal();
		if  (visible==null) {
			visible=!hasFilterNeverHide();
			setShowFilterBar(visible);
		}
		JWebFilterPaneResponsive filterPanel = this.skin().buildFormFilter(this);
		filterPanel.addDropManager("filterPanel", getWins());

		this.getWins().getRecords().clearDynamicFilters();

		if (this.isFirstAccess() || !this.sourceAction.hasFilterMap()) {
			// this.sourceAction.clearFilterMap();
			this.formFiltros.asignDefaultData();
		}
		this.formFiltros.applyFilterMap(this.sourceAction, this.isFirstAccess());

		if (this.sourceAction.hasFilterMap())
			getWins().checkControls(this.formFiltros,this.sourceAction.getFilterMap().getFieldChange());
		this.getWins().asignFiltersFromFilterBar(this.formFiltros);

		int i = 0;
		boolean hasFilterVisible =false;
		JFormControles webControls = new JFormControles(null,null);
		JIterator<JFormControl> iter = this.skin().filtersResorted(this.formFiltros.GetControles().GetItems());
		while (iter.hasMoreElements()) {
			JFormControl c = iter.nextElement();
			c.setResponsive(true);
			if (isReadOnly())
				c.SetReadOnly(isReadOnly());
			if (c.isHide())
				continue;
			hasFilterVisible|=true;
		  fillParentInfo(c);
		  this.skin().addAtributesToFilterControls(this,(JFormControlResponsive)c);
			
			JWebFormControl webControl = filterPanel.createWebControl(c,null);
      String zone = "filtro_" + webControl.getIdControl();
//			if (getWins().getObjectDrageable(zone)!=null)
//			  intPanel.setSkinStereotype("win_list_filters_item_drag");
//			else
//			 intPanel.setSkinStereotype("win_list_filters_item");
			c.addDropManager(zone, getWins());
			c.SetControles(webControls); // para que el formcontrol de swing tenga los punteros correctos
			webControls.AddControl(webControl);

		}


		this.formFiltros.setControles(webControls);
		this.formFiltros.disableReadOnlyFilters();
	
//		setShowSearchButton(visible);
		setShowSearchButton(hasFilterVisible);
		setNothingToShowInFilterBar(false);
		if (!visible) {
			boolean modeConFiltrosVisibles =hasFilterNeverHide();
			setNothingToShowInFilterBar(!this.formFiltros.hideFiltersWithinData(modeConFiltrosVisibles));
//			setShowSearchButton(modeConFiltrosVisibles);
	}
		
		if (!this.formFiltros.hasAnyFilterVisible() && visible) {
			setNothingToShowInFilterBar(true);
			return null;
		}

		return filterPanel;
	}
	public boolean hasFilterNeverHide() throws Exception  {
		if (this.formFiltros==null) return false;
		return this.formFiltros.hasFilterNeverHide();
	}
	public JWebViewInternalComposite createPreferenceActionaBar() throws Exception {
		if (!BizPssConfig.getPssConfig().useCustomColumns()) return null;
		if (!this.hasWins()) return null;
		if (!getWins().acceptPreferences()) return null;
		JWebViewInternalComposite bar = BizUsuario.retrieveSkinGenerator().createPreferenceBar(this, this.getProviderName());
		JWebAction action =JWebActionFactory.createSelectColumns();
		if (action!=null)	BizUsuario.retrieveSkinGenerator().createBotonPreference(bar,"Preferencias",action);
		return bar;
	}


	public JWebAction attachRefreshAction() throws Exception {
		BizAction action = this.createActionRefresh();
		action.setModal(this.sourceAction.isModal());

		if (sourceAction == null || (!sourceAction.hasOwner()) || (!sourceAction.hasRefreshAction())) {
			this.oActionBar.registerNotActionFor(action, this.winsObjectId, false);
			return null;
		}


		JWebServerAction webAction = JWebActionFactory.createWinListRefreshAction(this.sourceAction, this, this.sourceObjectId);
		webAction.setAjaxContainer(this.getCompletePanelName());
		webAction.setIsWinListRefresh(true);
		webAction.setDescription(action.GetDescr());
		JWebActionData nav = webAction.getNavigationData();
		nav.add("name", this.getWins().GetTitle());
		nav.add("from", String.valueOf(this.iFromRow));
		nav.add("paginado", this.paginado);
		nav.add("pagina_actual",  String.valueOf(this.iPaginaActual));
		nav.add("page_size", String.valueOf(this.iPageSize));
		nav.add("hide_filter_bar", ""+isShowFilterBar());
		nav.add("with_preview", "" + this.findPreviewFlag());
		nav.add("is_preview", ""+this.isPreview());
		nav.add("embedded", "" + this.isEmbedded());
		nav.add("toolbar", "" + this.getToolbar());
		nav.add("minimusRows", ""+getMinumusRows());
		nav.add("extraRows", ""+getExtraRows());
		nav.add("button_search", "true");


		// webAction.setAjaxContainer(this.findPreviewFlag().equals(JWins.PREVIEW_MAX)?
		// this.getPreviewCanvasPanelName(): this.getCenterPanelName());
//		webAction.setAjaxContainer(this.getCompletePanelName());

		this.oActionBar.registerActionFor(action, this.winsObjectId, webAction, false, true);
		return webAction;
	}

	public BizAction createActionRefresh() throws Exception {
		BizAction a = new BizAction();
		a.pDescr.setValue("Actualizar");
		a.pNroIcono.setValue(GuiIcon.REFRESH_ICON);
		a.SetIdAction(this.getProviderName() + "-winlist-refresh");
		return a;
	}

	String sPreviewFlag = null;

	@Override
	public void setPreviewFlag(String value) {
		this.sPreviewFlag = value;
	}

	@Override
	public String invertPreviewFlag() throws Exception {
		if (this.isPreviewNo())
			return JWins.PREVIEW_NO;
		return this.isPreviewFlagSi() ? JWins.PREVIEW_MAX : JWins.PREVIEW_SI;
	}

	@Override
	public boolean isPreviewNo() throws Exception {
		return this.findPreviewFlag().equals(JWins.PREVIEW_NO);
	}

	@Override
	public boolean isPreviewFlagSi() throws Exception {
		return this.findPreviewFlag().equals(JWins.PREVIEW_SI);
	}

	@Override
	public boolean isPreviewFlagMax() throws Exception {
		return this.findPreviewFlag().equals(JWins.PREVIEW_MAX);
	}

	@Override
	public String findPreviewFlag() throws Exception {
		if (JWebActionFactory.isMobile()) 
			return JWins.PREVIEW_NO;
		if (this.isPreview())
			return JWins.PREVIEW_NO;
		if (this.getWins().isModeWinLov())
			return JWins.PREVIEW_NO;
		if (this.sPreviewFlag != null)
			return sPreviewFlag;
		return this.getWins().getPreviewFlag();
	}

	@Override
	public String getProviderName() throws Exception {
		if (this.sourceAction == null)
			return "provider";
		return this.sourceAction.getProviderName();
	}

	@Override
	public String getName() {
		try {
			return this.getProviderName();
		} catch (Exception e) {
			return "win_list";
		}
	}

	@Override
	public boolean isNeedRefreshAllScreen() {
		return needRefreshAllScreen;
	}

	@Override
	public void setNeedRefreshAllScreen(boolean needRefreshAllScreen) {
		this.needRefreshAllScreen = needRefreshAllScreen;
	}
		
	public void attachDoFilterButton(JWebFilterPaneResponsive filterPanel, JWebViewInternalComposite secccion, String presentation) throws Exception {
		this.filterBotton = null;
		if (!this.sourceAction.hasFilterAction())
			return;
		if (!isShowSearchButton())
			return;

		BizAction a = new BizAction();
		a.setDescrip("Buscar");
		a.SetIdAction(this.sourceAction.getIdAction());
		a.setModal(this.sourceAction.isModal());
		a.setRow(this.sourceAction.getRow());
		JWebServerAction webAction;

//		if (isNeedRefreshAllScreen()) {
//			webAction = JWebActionFactory.k(a, filterPanel, null);
//			webAction.setAjaxContainer(this.getCompletePanelName());
//		} else {
			webAction = JWebActionFactory.createWinListRefreshAction(a, filterPanel, null);
			webAction.setAjaxContainer(this.getCompletePanelName());
//		}
		JWebActionData oData = webAction.addData("presentation");
		oData.add("mode", presentation);


		JWebActionData nav = webAction.getNavigationData();
		nav.add("name", this.getWins().GetTitle());
		nav.add("from", String.valueOf(0));
		nav.add("paginado", this.paginado);
		nav.add("pagina_actual",  String.valueOf(this.iPaginaActual));
		nav.add("page_size", String.valueOf(this.iPageSize));
		nav.add("hide_filter_bar", ""+isShowFilterBar());
		nav.add("with_preview", "" + this.findPreviewFlag());
		nav.add("is_preview", ""+this.isPreview());
		nav.add("embedded", "" + this.isEmbedded());
		nav.add("toolbar", "" + this.getToolbar());
		nav.add("minimusRows", ""+getMinumusRows());
		nav.add("extraRows", ""+getExtraRows());
		nav.add("button_search", "true");

		
		this.filterBotton = BizUsuario.retrieveSkinGenerator().getFindButton(this, webAction);
		((JWebServerAction) this.filterBotton.getWebAction()).setObjectOwnerID(sourceObjectId);

		secccion.addChild(this.filterBotton);
	}

	@Override
	public boolean isReadOnly() {
		return bReadOnly;
	}

	@Override
	public void setReadOnly(boolean readOnly) {
		bReadOnly = readOnly;
	}
	
	public boolean isInForm() throws Exception {
		return isInForm;
	} 

	protected boolean hasToolbar() throws Exception {
		return !this.isToolbarNone() && !this.isReadOnly();
	}

	protected boolean hasActionBar() throws Exception {
		return this.oActionBar != null;
	}
	
	protected boolean hasSelectRows() throws Exception {
		if (hasActionBar()) return true;
		return isSelectedRows(); // implementa eleccio usuario
	}

  public int getPageSize() {
		return iPageSize;
	}
	@Override
	public boolean isShowFilterBar() throws Exception {
		isShowFilterBarInternal();
		return bShowFilterBar;
	}
	public Boolean isShowFilterBarInternal() throws Exception {
		if (!analizeHideShowFilter()) return null;
		return bShowFilterBar;
	}
	public boolean isShowPagination() throws Exception {
		isShowFilterBarInternal();
		return bShowPaginationBar;
	}

	

	@Override
	public void setShowFilterBar(boolean bShowFilterBar) {
		this.bShowFilterBar = bShowFilterBar;
	}
	protected void analizeHideShowPagination() throws Exception {
		if (bForceHidePaginationBar) {
			bShowPaginationBar = false;
			return;
		}
		JWebActionData nav = JWebActionFactory.getCurrentRequest().getSession().getHistoryManager().findLastNavData(getSourceAction());
		bShowPaginationBar = true;
		if (!nav.hasField("hide_pagination_bar"))
			return;
		bShowPaginationBar = nav.get("hide_pagination_bar").equals("true");
	}
	protected boolean analizeHideShowFilter() throws Exception {
		JWebRequest req = JWebActionFactory.getCurrentRequest();
		if (req.hasMultipleObjectOwnerList() && isPreview()) {
			bShowFilterBar = false;
			return true;
		}
		if (isEmbedded()&&isPreview()) {
			bShowFilterBar = false;
			return true;
		}
		JWebActionData nav = JWebActionFactory.getCurrentRequest().getSession().getHistoryManager().findLastNavData(getSourceAction());
		if (sShowFilterBar==null) {
			bShowFilterBar = false;
		} else {
			bShowFilterBar = sShowFilterBar;
		}
		if (!nav.hasField("hide_filter_bar"))
			return (sShowFilterBar==null)?false:true;
		bShowFilterBar = nav.get("hide_filter_bar").equals("true");
		return (sShowFilterBar==null)?false:true;
	}

	@Override
	public boolean hasPreview() throws Exception {
		if (this.isPreview())
			return false;
		return !this.findPreviewFlag().equals(JWins.PREVIEW_NO);
	}

	protected void analizeOrden() throws Exception {
			JHistoryProvider prov = JWebActionFactory.getCurrentRequest().getSession().getHistoryManager().findLastProvider(getSourceAction());
			advanceColumnOrden = prov==null?null:prov.getColumnsOrder();
			if (advanceColumnOrden!=null) return;
		
		JWebActionData oListNavData = JWebActionFactory.getCurrentRequest().getSession().getHistoryManager().findLastNavData(getSourceAction());
		if (oListNavData != null) {
			columnOrden = oListNavData.get("orden");
			direccionOrden = oListNavData.get("dir_orden");
		}
		return;
	}

	protected int analizeSize() throws Exception {
		String size="";
	
		JWebActionData oListNavData = JWebActionFactory.getCurrentRequest().getSession().getHistoryManager().findLastNavData(getSourceAction());
		if (oListNavData != null) {
			size = oListNavData.get("size");
			if (size==null) return -1;
			if (JTools.isNumber(size)) return (int)Integer.parseInt(JTools.getNumberEmbeddedWithDecSigned(size));
		}
		return -1;
	}

	protected void rowToXMLActionBar(JXMLContent zContent, JWin zWin,String id) throws Exception {
//		zWin.generateActionsForToolbar(true, false);
		if (hasActionBar())
			this.attachWinsActions(zWin, id, zWin.forceAppendActions());
		zContent.setAttribute("zobject", id);
	}

	protected JWebAction createWebActionParent() throws Exception {
		if (!this.getWins().isDependentList())
			return null;
		if (this.parentProvider == null)
			return null;
		return parentProvider.getWebSourceAction();
	}

	@Override
	public void attachWinsActions(JBaseWin zWin, String zBaseWinId, boolean append) throws Exception {
		this.oActionBar.addActionsFor(zWin, zBaseWinId, this.createWebActionParent(), append, false, false, isModal());
	}

	protected void attachActions(int orden) throws Exception {
		if (orden == this.getWins().getOrdenWinsToolsActions()) {
			this.attachRefreshAction();
			this.attachBackAction();
		}
		if (orden == this.getWins().getOrdenWinsActions())
			this.attachWinsActions(this.getWins(), this.winsObjectId, true);
		if (orden == this.getWins().getOrdenWinActions() && this.getWins().isShowWinToolBar())
			this.attachWinsActions(this.getWins().getWinRefWithActions(), null, true);

	}

	protected void attachActionBar() throws Exception {
		// represent the action bar actions for the Wins object
		if (!this.hasActionBar())
			return;
		BizUsuario.retrieveSkinGenerator().buildHeaderExtraBar1(this);

		this.attachActions(1);
		this.attachActions(2);
		this.attachActions(3);

	}
	protected String findIcon(JWin win) throws Exception {
		try {
			return win.GetIconFile();
		} catch (Exception e) {
			return "";
		}
	}
	public void iconCellToXML(JXMLContent zContent, JWin zWin, JColumnaLista oCol, int idCol,String idObj) throws Exception {
		if (this.isWinListFullExportRequest) return;
		zContent.startNode("d");
		zContent.setAttribute("type", "JICON");
		zContent.setAttribute("marked", zWin.isMarked("ICONO"));
		if (zWin.isWordWrap("ICONO"))
			zContent.setAttribute("word_wrap", zWin.isWordWrap("ICONO"));
		zContent.setAttribute("height", zWin.getHeightRow());
		zContent.setAttribute("axis", idCol);
		String value = zWin.getFieldForeground("ICONO") != null ? zWin.getFieldForeground("ICONO") : oCol.getColorForeground();
		if (value != null)
			zContent.setAttribute("foreground",  JTools.toHtmlColor(value));
		value = zWin.getFieldBackground("ICONO") != null ? zWin.getFieldBackground("ICONO") : oCol.getColorBackground();
		if (value != null)
			zContent.setAttribute("background",  JTools.toHtmlColor(value));
		value = zWin.getFieldTooltip("ICONO");
		if (value != null)
			zContent.setAttribute("tooltip", value);
		zContent.setAttribute("visible", true);

		value = zWin.getFieldTooltip("ICONO");
		if (value != null)
			zContent.setAttribute("tooltip", value);

		JWebAction actSmplClick = this.getActionById(zWin, oCol.getActionOnClick() == null ? "" + zWin.getDobleClick() : oCol.getActionOnClick(), idObj);
		if (actSmplClick != null) {
			zContent.startNode("actionlink");
			actSmplClick.toXML(zContent);
			JWebIcon.getPssIcon(GuiIconos.RESPONSIVE,zWin.GetNroIcono()).toXML(zContent);						
			zContent.endNode("actionlink");
		} else {
			JWebIcon.getPssIcon(GuiIconos.RESPONSIVE,zWin.GetNroIcono()).toXML(zContent);						
		}
		zContent.endNode("d");
	}

	
	protected int getRowSpan(String zColumn, String zValue) {
		return 0;
	}

	protected boolean shouldSkip(String zColumn, String zValue) {
		return false;
	}
	
	private double castDouble(Object obj) {
		if (obj instanceof Integer) return (double)((Integer) obj);
		if (obj instanceof Long) return (double)((Long) obj);
		return (Double) obj;
	}
		
	
	private String toXlsData(JObject<?> prop) throws Exception {
		try {
			if (prop==null) return "";
			Object obj = prop.asRawObject();
			if (obj==null) return "";
			if (prop.isDate() || prop.isDateTime()) 
				return JDateTools.DateToString(((Date) obj), "yyyy-MM-dd'T'HH:mm:ss");
			if (prop.isFloat() || prop.isCurrency()) 
				return JTools.toPrinteableNumberSinMiles(this.castDouble(obj), prop.getCustomPrecision()); 
			if (prop.isInteger()) 
				return JTools.toPrinteableNumberSinMiles((Integer) obj, 0);
			if (prop.isBoolean()) 
				return prop.toFormattedString();
	
			String sPropFormatted = prop.toRawFormattedString();
			if (sPropFormatted != null && sPropFormatted.indexOf("<") != -1) //horrible!!!! el que hizo esto que lo arregle
				return Jsoup.parse(sPropFormatted).text(); 
			
			return prop.asPrintealbleObject();
		} catch (Exception e) {
			PssLogger.logError(e);
			return "Error";
		}
	}

	private String toXlsData2(JObject<?> oProp) throws Exception {
		if (oProp == null)
			return "";
		if (oProp.isCurrency() || oProp.isFloat())
			return oProp.toRawString().replace(".", ",");
		return oProp.toRawString();
	}
	protected String getColumnAlignment(int zAlignment) {
		switch (zAlignment) {
		case JColumnaLista.ALIGNMENT_LEFT:
			return "left";
		case JColumnaLista.ALIGNMENT_RIGHT:
			return "right";
		default:
		case JColumnaLista.ALIGNMENT_CENTER:
			return "center";
		}
	}
	protected boolean headerToXML(JWins zWins, JXMLContent zContent, JWin firstWin, JGrupoColumnaLista[][] oGrupoColumns, JColumnaLista[] oColumns, boolean isWinListFullExportRequest) throws Exception {
		JGrupoColumnaLista oGCol;
		JGrupoColumnaLista oldGCol;
		int span = 0;
		boolean ordenable=false;
		// generate the header columns
		boolean conGrupo = !(oGrupoColumns[0][0].getTitulo().equals("SINGRUPO"));
		zContent.setAttribute("with_group", conGrupo);
		JWin localFirstWin = firstWin;
		if (firstWin==null)
			localFirstWin=getWins().getWinRef();
			

		int maxLevel = 0;
		for (maxLevel = 0; oGrupoColumns[maxLevel][0] != null; maxLevel++)
			

		for (int l = maxLevel - 2; l >= 0; l--) {
			zContent.startNode("level_column");
			oldGCol = null;
			span = 0;
			for (int g = 0; oGrupoColumns[l][g] != null; g++) {
				oGCol = oGrupoColumns[l][g];
				if (oldGCol == null)
					oldGCol = oGCol;
				if (oldGCol == oGCol) {
					span++;
					continue;
				}
				zContent.startNode("grupo_column");
				zContent.setAttribute("span", span);
				addDrop(zContent, "column_" + oldGCol.getTitulo(), getWins());
				zContent.addTextNode("title", JLanguage.translate(oldGCol.getTitulo()));
				zContent.endNode("grupo_column");
				oldGCol = oGCol;
				span = 1;
			}
			if (oldGCol != null) {
				zContent.startNode("grupo_column");
				zContent.setAttribute("span", span);
				addDrop(zContent, "column_" + oldGCol.getTitulo(), getWins());
				zContent.addTextNode("title", JLanguage.translate(oldGCol.getTitulo()));
				zContent.endNode("grupo_column");
			}
			oldGCol = null;
			zContent.endNode("level_column");
		}

		zContent.startNode("header");
		zContent.setAttribute("has_subdetail", zWins.hasSubDetail(isWinListFullExportRequest));
		zContent.setAttribute("datetime", JDateTools.CurrentDateTime());
		zContent.setAttribute("filters", formFiltros == null ? "" : formFiltros.toString());
		if (zWins.hasSubDetail(isWinListFullExportRequest))
			JWebIcon.getSkinIcon(this.skin().getExpandAllSubDetail()).toXML(zContent);
//		if (zWins.getRecords().hasCorteControl()) 
//			cortecontrolToHeaderXML(zContent,zWins.getWinRef());

		JColumnaLista oCol;
		boolean bWithIcon = false;
		for (int i = 0; i < oColumns.length; i++) {
			oCol = oColumns[i];
			ordenable=false;
			if (oCol == null)	continue;
			if (isWinListFullExportRequest && (oCol.IfIcono() || oCol.IfAction()) && i == 0) continue;// el icono inicial en la exportacion no va
			String sColType=this.findColType(oCol, localFirstWin);
			zContent.startNode("column");
			if (oCol.hasHeaderBackground())
				zContent.setAttribute("background", oCol.getHeaderBackground());

			zContent.setAttribute("is_html", oCol.ifHtml());

			if (isWinListFullExportRequest && (oCol.IfIcono() || oCol.IfAction())) {
				zContent.addTextNode("title", ""); // en la exportacion debe ir en blanco, para que no se ponga
			} else if (oCol.IfIcono()) {
					bWithIcon = true;
					sColType = "JICON";
					zContent.setAttribute("width", 40);
					zContent.setAttribute("type", sColType);
					zContent.setAttribute("halignment", "center");
					zContent.setAttribute("class_table_header", "column-header-icon");
					addDrop(zContent, "campo_X", getWins());
					if (isMultipleSelection()) {
						zContent.addTextNode("hover", "Sel./Desel.Todo"); // no se traduce
						zContent.addTextNode("title", oCol.GetTitulo().equals("")?"-":oCol.GetTitulo()); // no se traduce
						JWebAction webAction = JWebActionFactory.createWinListSelectAll(this, this.sourceAction, this.sourceObjectId, oCol.GetCampo(), direccionOrden, sAll.equals("N") ? "S" : "N", (int) totalCountElement, iFromRow, this.getRealPageSize(),this.paginado,this.iPaginaActual);
						webAction.toXML(zContent);

					} else {
						zContent.addTextNode("title",  oCol.GetTitulo().equals("")?"x":oCol.GetTitulo()); // no se traduce

					}
			} else if (oCol.IfAction()) {
					sColType = "JLINK";
					zContent.setAttribute("type", sColType);
					zContent.setAttribute("class_table_header", "column-header-action");
					if (oCol.isAgrupado()) zContent.setAttribute("width", 40);
					zContent.setAttribute("hover", oCol.GetColumnaTitulo()); // no se traduce
					if (oCol.hasHtmlTitulo()) {
						String val=oCol.GetColumnaTitulo();
						zContent.startNode("html_data");
						zContent.setNodeText(val);
						zContent.endNode("html_data");
					} else
						zContent.addTextNode("title", oCol.GetColumnaTitulo()); // no se traduce
					ordenable=true;
					
			} else if (oCol.hasIdAction()) {
					this.winActionHeaderToXML(zContent, oCol, zWins.getWinRef());
					ordenable=true;
			} else {
			
				 sColType = this.findColType(oCol, localFirstWin);
//
//				if (localFirstWin != null && !oCol.GetCampo().equals("")) 
//					sColType = localFirstWin.getRecord().getObjectType(oCol.GetCampo());
//				else if (oCol.IfBoolean()) 
//					sColType = JObject.JBOOLEAN;
//			  else if (oCol.GetClase().equals(Void.class))
//						sColType = "VOID";
//				else
//						sColType = JObject.JSTRING;
			

				if (!oCol.hasAlignment())	oCol.setAlignmentFromType(sColType);
				zContent.setAttribute("type", sColType);
				zContent.setAttribute("halignment", this.getColumnAlignment(oCol.getAlignment()));
				zContent.setAttribute("class_table_header", "column-header-normal");
				if (sColType.equals(JObject.JBOOLEAN))
					zContent.setAttribute("width", 40);
				else if (sColType.equals("VOID"))
					zContent.setAttribute("width", BizUsuario.retrieveSkinGenerator().getWidthAutocompleteColumn(oColumns));

				else if (localFirstWin != null && localFirstWin.getWidthCol(oCol.GetCampo()) != 0)
					zContent.setAttribute("width", localFirstWin.getWidthCol(oCol.GetCampo())+"px");
				else if (oCol.getWidth()!=null) 
					zContent.setAttribute("width", oCol.getWidth());
				else if (isWinListFullExportRequest) { if (oCol.getFixedProp() != null && oCol.getFixedProp().getSize() != 0 && oCol.getFixedProp().getSize() * 3 < 100)
						zContent.setAttribute("width", oCol.getFixedProp().getSize() * 3);
				} 

				if (sColType.equals(JObject.JBOOLEAN)) {
					zContent.setAttribute("image_true", getWins().getWinRef().getImageIfTrue(oCol.GetCampo()));
					zContent.setAttribute("image_false", getWins().getWinRef().getImageIfFalse(oCol.GetCampo()));
				}
				zContent.setAttribute("hover", oCol.GetColumnaTitulo()); // no se traduce
				if (oCol.hasHtmlTitulo()) {
					String val=oCol.GetColumnaTitulo();
					zContent.startNode("html_data");
					zContent.setNodeText(val);
					zContent.endNode("html_data");
				} else
					zContent.addTextNode("title", oCol.GetColumnaTitulo()); // no se traduce
				ordenable=true;
				addDrop(zContent, "campo_" + oCol.GetCampo(), getWins());
			}	
			
			if (ordenable) addOrder(oCol,zContent);
			
			// acá, sino antes
			zContent.endNode("column");
		}
		zContent.endNode("header");
		return bWithIcon;
	}
	
	public void addOrder(JColumnaLista oCol, JXMLContent zContent) throws Exception {
		if (! oCol.isSortable()) return;
		if (useSimpleOrden()) {
			JWebAction webAction = JWebActionFactory.createWinListOrden(this, this.sourceAction, this.sourceObjectId, oCol.GetCampo(), (columnOrden == null || !columnOrden.equals(oCol.GetCampo())) ? "ASC" : (direccionOrden.equalsIgnoreCase("asc") ? "desc" : "asc"), sAll, (int) totalCountElement, iFromRow, this.iPageSize,this.paginado,this.iPaginaActual);
			JXMLRepresentable botonOrdenar =  BizUsuario.retrieveSkinGenerator().buildOrderColumn(this,columnOrden==null?false:columnOrden.equals(oCol.GetCampo()),direccionOrden,webAction);
			if (BizUsuario.retrieveSkinGenerator().canClickInAllHeaderForOrder())
				webAction.toXML(zContent);
			if (botonOrdenar!=null) {
				zContent.startNode("control");
				botonOrdenar.toXML(zContent);
				zContent.endNode("control");
			}
		} else {
			BizAction action = getWins().findAction(99998);
			if (action==null) return;
			JWebAction webAction = JWebActionFactory.createViewAreaAndTitleAction(action, this, false,  this.winsObjectId, null, true);
			JXMLRepresentable botonOrdenar =  BizUsuario.retrieveSkinGenerator().buildOrderColumn(this,columnOrden==null?false:columnOrden.equals(oCol.GetCampo()),direccionOrden,webAction);
			if (BizUsuario.retrieveSkinGenerator().canClickInAllHeaderForOrder())
				webAction.toXML(zContent);
			if (botonOrdenar!=null) {
				zContent.startNode("control");
				botonOrdenar.toXML(zContent);
				zContent.endNode("control");
			}
		}
		
	}
	
	Boolean isFlat=null;
	public boolean isFlat() throws Exception {
		if (isFlat!=null) return isFlat;
		JWinList wl = new JWinList(getWins());
		getWins().ConfigurarColumnasLista(wl);
		
		JList<JColumnaLista> oCols=wl.GetColumnasLista();
		JIterator<JColumnaLista> it = oCols.getIterator();
		while (it.hasMoreElements()) {
			JColumnaLista col = it.nextElement();
			if (col.hasIdAction()) return isFlat=true;
		}
		return isFlat= false;
	}
	
	public boolean useSimpleOrden() throws Exception {
		if (getWins().useSimpleOrden()!=null) return getWins().useSimpleOrden();
		return BizUsuario.retrieveSkinGenerator().useSimpleOrden(this);
	}
			
	private String findColType(JColumnaLista col, JWin win ) throws Exception {
		// ordene un poco esto pero sigue horrible HGK
		// la idea es que la col tambien tenga el type y no lo busque en el header
		if (col.IfIcono()) return "JICON";
		if (col.IfAction()) return "JLINK";
		if (col.hasIdAction()) return "JWINFORM";
		if (win!=null && !col.GetCampo().equals("") && win.getRecord().getProp(col.GetCampo(), false)!=null) 
				return win.getRecord().getObjectType(col.GetCampo());
		if (col.IfBoolean()) return JObject.JBOOLEAN;
	  if (col.GetClase().equals(Void.class)) return "VOID";
	  return JObject.JSTRING;
	}

	void fieldToXML(JXMLContent zContent, JWin zWin, JColumnaLista oCol, int idCol,String id, int row) throws Exception {
		if (oCol == null)
			return;
		if (oCol.IfIcono()) 
			iconCellToXML(zContent,zWin,oCol,idCol,id);
		else if (oCol.IfAction()) 
			actionCellToXML(zContent,zWin,oCol,idCol,id);
		else if (oCol.hasIdAction()) 
			winActionCellToXML(zContent,zWin,oCol,idCol,id, row);
		else 
			normalCellToXML(zContent, zWin, oCol, idCol,id);
	}

	public void normalCellToXML(JXMLContent zContent, JWin zWin, JColumnaLista oCol, int idCol, String id) throws Exception {
		try {
			String sColName = oCol.GetCampo();
			JObject<?> oProp = sColName.equals("") ? null : zWin.getRecord().getPropDeep(sColName,false);
			if (oProp == null)
				oProp = new JString("");
			oProp.preset();
			Object objProp = oProp.asRawObject();
			String sPropFormatted = oProp.toRawFormattedString();
			if (sPropFormatted == null || oProp.isRawNull()) {
				sPropFormatted = "";
			}
			String sType = this.findColType(oCol, zWin);
			if (!oCol.hasAlignment())	oCol.setAlignmentFromType(sType);

			zContent.startNode("d");
			zContent.setAttribute("type", sType);
			zContent.setAttribute("halignment", this.getColumnAlignment(oCol.getAlignment()));
	
			zContent.setAttribute("marked", zWin.isMarked(sColName));
			if (zWin.isWordWrap(sColName))
				zContent.setAttribute("word_wrap", zWin.isWordWrap(sColName));
			zContent.setAttribute("height", zWin.getHeightRow());
			zContent.setAttribute("axis", idCol);
			if (oProp.isBoolean()) {
				zContent.setAttribute("image_true", zWin.getImageIfTrue(oCol.GetCampo()));
				zContent.setAttribute("image_false", zWin.getImageIfFalse(oCol.GetCampo()));
			}
			String value = zWin.getFieldForeground(sColName) != null ? zWin.getFieldForeground(sColName) : oCol.getColorForeground();
			if (value != null)
				zContent.setAttribute("foreground",  JTools.toHtmlColor(value));
			value = null;
			if (oCol.isMarcaMayores()) {
				if (!oCol.isMarcaTop10()) {
					if (!oCol.getMayoresA().equals("") && JTools.isNumber(oCol.getMayoresA()) && oProp.hasValue()) {
						if (oProp.isFloat() || oProp.isCurrency())
							if (((Double) oProp.asObject()).doubleValue() > Double.parseDouble(oCol.getMayoresA()))
								value = oCol.getColorTopBackground();
						if (oProp.isInteger() || oProp.isLong())
							if (((Long) oProp.asObject()).longValue() > Double.parseDouble(oCol.getMayoresA()))
								value = oCol.getColorTopBackground();
					}
				}
			}
			if (value == null && oCol.isMarcaMenores()) {
				if (!oCol.isMarcaBottom10()) {
					if (!oCol.getMenoresA().equals("") && JTools.isNumber(oCol.getMenoresA()) && oProp.hasValue()) {
						if (oProp.isFloat() || oProp.isCurrency())
							if (((Double) oProp.asObject()).doubleValue() < Double.parseDouble(oCol.getMenoresA()))
								value = oCol.getColorBottomBackground();
						if (oProp.isInteger() || oProp.isLong())
							if (((Long) oProp.asObject()).longValue() < Double.parseDouble(oCol.getMenoresA()))
								value = oCol.getColorBottomBackground();
					}
				}
			}
			if (value == null)
				value = zWin.getFieldBackground(sColName) != null ? zWin.getFieldBackground(sColName) : oCol.getColorBackground();
			if (value != null)
				zContent.setAttribute("background",  JTools.toHtmlColor(value));
			value = zWin.getFieldTooltip(sColName);
			if (value != null)
				zContent.setAttribute("tooltip", value);
			zContent.setAttribute("visible", true);
	
			int iRowSpan = this.getRowSpan(oCol, zWin);
			if (iRowSpan == 0) {
				zContent.setAttribute("skip", true);
			} else if (iRowSpan > 0) {
				zContent.setAttribute("rowspan", iRowSpan);
//				zContent.setAttribute("valignment", "middle");
			}
			int iColSpan = zWin.getFieldColspan(sColName);
			if (iColSpan > 1) {
				zContent.setAttribute("colspan", iRowSpan);
			}
			if (this.shouldSkip(sColName, sPropFormatted)) {
				zContent.setAttribute("skip", true);
			}
			if (!isLineSelect())
				this.addDrop(zContent, "field_"+oCol.GetTitulo(), zWin);
	

			zContent.setAttribute("data", oProp.toRawString());
			zContent.setAttribute("data_xls2", toXlsData(oProp));
			if (objProp != null) {
				if (oProp.isDate() || oProp.isDateTime()) {
					String sResult = JDateTools.DateToString(((Date) objProp), "yyyy-MM-dd'T'HH:mm:ss");
					zContent.setAttribute("data_xls", sResult);
				} else if (oProp.isFloat() || oProp.isCurrency()) {
					zContent.setAttribute("data_xls", JTools.toPrinteableNumberSinMiles(objProp instanceof Integer?(double)((Integer) objProp):objProp instanceof Long?(double)((Long) objProp):((Double) objProp), oProp.getCustomPrecision())); 
				} else if (oProp.isInteger()) {
					zContent.setAttribute("data_xls", JTools.toPrinteableNumberSinMiles(((Integer) objProp), 0));
				} else if (oProp.isBoolean()) {
					zContent.setAttribute("data_xls", oProp.toFormattedString());
				} else if (sPropFormatted != null && sPropFormatted.indexOf("<") != -1) {
					zContent.setAttribute("data_xls", Jsoup.parse(sPropFormatted).text()); 
				} else
					zContent.setAttribute("data_xls", oProp.asPrintealbleObject());
			} else {
				if ( oCol.hasSelector()) {
					zContent.setAttribute("selector", oCol.getSelector());
				}
				zContent.setAttribute("data_xls", "");
			}
			// zContent.setAttribute("data_xls",
			// oProp.toRawString().replace(".", ",")); // ver de nacionalizar
			if (oCol.hasHtml()) {
				zContent.startNode("html_data");
				zContent.setNodeText(oCol.makeHtml(sPropFormatted));
				zContent.endNode("html_data");
			} else if (oProp.hasHtml()) {
				String val=oProp.toHtml();
				if (val.startsWith("html:")) val=val.substring(5);// por compatibilidad
				zContent.startNode("html_data");
				// zContent.setNodeText(sPropFormatted);
				zContent.setNodeText(val);
				zContent.endNode("html_data");
			} else
				zContent.setNodeText(sPropFormatted);

			if (id!=null) {
				JWebAction actSmplClick = this.getActionById(zWin, oCol.getActionOnClick(),id);
				if (actSmplClick != null) {
					zContent.startNode("actionlink");
					actSmplClick.toXML(zContent);
					zContent.endNode("actionlink");
				}
			}

			// zContent.setNodeText(sPropFormatted);
			// zContent.setAttribute("data_xls", oProp.toRawString().replace(".",
			// ",")); // ver de nacionalizar

			// zContent.setNodeText(sPropFormatted);
			zContent.endNode("d");
		} catch (Exception e) {
			PssLogger.logError(e);
			zContent.startNode("d");
			zContent.setAttribute("marked", false);
			zContent.setNodeText(e.getMessage());
			zContent.setAttribute("word_wrap", true);
			zContent.endNode("d");
		}
	}









	public String buildColumnName(String original) throws Exception {
		return JTools.replace(original,".","_");
	}

	public void voidCellToXML(JXMLContent zContent, JWin zWin, JColumnaLista oCol, int idCol, String id) throws Exception {
		try {
			String sColName = oCol.GetCampo();
			JObject<?> oProp = new JString("");
			String sPropFormatted = " ";
			

		
			zContent.startNode("d");
			zContent.setAttribute("col_name", buildColumnName(sColName));
			zContent.setAttribute("marked", zWin.isMarked(sColName));
			zContent.setAttribute("height", zWin.getHeightRow());
			zContent.setAttribute("axis", idCol);
			String value = zWin.getFieldForeground(sColName) != null ? zWin.getFieldForeground(sColName) : oCol.getColorForeground();
			if (value != null)
				zContent.setAttribute("foreground",  JTools.toHtmlColor(value));
			value = null;
			if (value == null)
				value = zWin.getFieldBackground(sColName) != null ? zWin.getFieldBackground(sColName) : oCol.getColorBackground();
			if (value != null)
				zContent.setAttribute("background",  JTools.toHtmlColor(value));
			value = zWin.getFieldTooltip(sColName);
			zContent.setAttribute("visible",true);
			zContent.setAttribute("image_true", "");
			zContent.setAttribute("image_false", "");

			int iRowSpan = this.getRowSpan(oCol, zWin);
			if (iRowSpan > 1) {
				zContent.setAttribute("rowspan", iRowSpan);
			}
			int iColSpan = zWin.getFieldColspan(sColName);
			if (iColSpan > 1) {
				zContent.setAttribute("colspan", iRowSpan);
			}
			if (this.shouldSkip(sColName, sPropFormatted)) {
				zContent.setAttribute("skip", true);
			}

			zContent.setAttribute("data", oProp.toRawString());
			zContent.setAttribute("data_xls2", toXlsData2(oProp));
			zContent.setAttribute("data_xls", "");
			zContent.setNodeText(sPropFormatted);
			zContent.endNode("d");
		} catch (Exception e) {
			PssLogger.logError(e);
			zContent.startNode("d");
			zContent.setAttribute("marked", false);
			zContent.setNodeText(e.getMessage());
			zContent.setAttribute("word_wrap", true);
			zContent.endNode("d");
		}
	}
	
	
	public void actionCellToXML(JXMLContent zContent, JWin zWin, JColumnaLista oCol, int idCol,String idObj) throws Exception {
		zContent.startNode("d");
		zContent.setAttribute("marked", zWin.isMarked(oCol.GetTitulo()));
		zContent.setAttribute("height", zWin.getHeightRow());
		zContent.setAttribute("axis", idCol);
		zContent.setAttribute("type", "JLINK");
		String value = zWin.getFieldForeground(oCol.GetTitulo()) != null ? zWin.getFieldForeground(oCol.GetTitulo()) : oCol.getColorForeground();
		if (value != null)
			zContent.setAttribute("foreground",  JTools.toHtmlColor(value));
		value = zWin.getFieldBackground(oCol.GetTitulo()) != null ? zWin.getFieldBackground(oCol.GetTitulo()) : oCol.getColorBackground();
		if (value != null)
			zContent.setAttribute("background",  JTools.toHtmlColor(value));
		value = zWin.getFieldTooltip(oCol.GetTitulo());
		if (value != null)
			zContent.setAttribute("tooltip", value);
		zContent.setAttribute("visible", true);
		zContent.setAttribute("clase", BizUsuario.retrieveSkinGenerator().getClassActionWinList(zWin) );
		zContent.setAttribute("with_caret",BizUsuario.retrieveSkinGenerator().withCaretActionWinList(zWin));
		zContent.setAttribute("class_responsive", " column-action" );
		
		zContent.setAttribute("agrupado", oCol.isAgrupado());
		zContent.setAttribute("titulo", oCol.GetTitulo());
		if (oCol.getNroIcono()!=0)
			JWebIcon.getPssIcon(GuiIconos.RESPONSIVE,oCol.getNroIcono()).toXML(zContent);						
		if (oCol.getAction().equals("all")) {
			JIterator<BizAction> it = zWin.getActionMap().getStaticIterator();
			while (it.hasMoreElements()) {
				BizAction act = it.nextElement();
				if (!act.ifToolBar()) continue;
				if (!act.isOkSecurity()) continue;
				if (!act.isOkAction()) continue;
				JWebAction actSmplClick = this.getActionById(zWin,act.getIdAction(),idObj);
				if (actSmplClick != null) {
					zContent.startNode("actionclick");
					zContent.setAttribute("descripcion", BizUsuario.getMessage(act.GetDescr(), null));
					actSmplClick.toXML(zContent);
					JWebIcon.getPssIcon(GuiIconos.RESPONSIVE,act.GetNroIcono()).toXML(zContent);						
					zContent.endNode("actionclick");
				}
			}
		} else {
			StringTokenizer toks = new StringTokenizer(oCol.getAction(), ",");
			while (toks.hasMoreTokens()) {
				String sAct= toks.nextToken();
				BizAction action = JTools.isNumber(sAct)?zWin.getActionMap().findAction((int)JTools.getLongFirstNumberEmbedded(sAct)):zWin.getActionMap().findActionByUniqueId(sAct);
				if (action == null) continue;
				JWebAction actSmplClick = this.getActionById(zWin,sAct,idObj);
				if (actSmplClick != null) {
					zContent.startNode("actionclick");
					zContent.setAttribute("descripcion", BizUsuario.getMessage(action.GetDescr(), null));
					actSmplClick.toXML(zContent);
					JWebIcon.getPssIcon(GuiIconos.RESPONSIVE,action.GetNroIcono()).toXML(zContent);						
					zContent.endNode("actionclick");
				}
				
			}
		}
		zContent.endNode("d");
	}
	
	public void winActionHeaderToXML(JXMLContent zContent, JColumnaLista col, JWin win) throws Exception {
		if (!this.winList.isWithHeader()) return;
		if (col.isForceTitle()) {
			zContent.addTextNode("title", col.GetColumnaTitulo());
			return;
		}
		String sColType = "JWINFORM";
		zContent.setAttribute("type", sColType);
		zContent.setAttribute("name", "header_flat");
		zContent.setAttribute("visible", true); 
		BizAction action = win.findActionByUniqueId(""+col.getIdAction(), false, false);
		JBaseForm formFlat=action.findFormFlat(JBaseForm.HEADER);
		JWebWinForm form = new JWebWinForm(formFlat);
		form.setSourceAction(this.sourceAction);
		form.setProviderName(this.getProviderName());
		form.setPreview(this.isPreview());
		form.setEmbedded(this.isEmbedded());
		form.setToolbar(JBaseWin.TOOLBAR_NONE);
		form.ensureIsBuilt();
		JWebViewComponent panel = form.getChildDeep("internal-panel");
		if (panel!=null) panel.toXML(zContent);
	}
	
	public void winActionFooterToXML(JXMLContent zContent, JColumnaLista col, JWin win) throws Exception {
		if (!this.winList.isWithFooter()) return;
		zContent.setAttribute("type", "JWINFORM");
		zContent.setAttribute("visible", true);
		BizAction action = win.findActionByUniqueId(""+col.getIdAction(), false, false);
		JBaseForm formFlat=action.findFormFlat(JBaseForm.FOOTER);
		JWebWinForm form = new JWebWinForm(formFlat);
		form.setSourceAction(this.sourceAction);
		form.setProviderName(this.getProviderName());
		form.setPreview(this.isPreview());
		form.setEmbedded(this.isEmbedded());
		form.setToolbar(JBaseWin.TOOLBAR_NONE);
		form.ensureIsBuilt();
		JWebViewComponent panel = form.getChildDeep("internal-panel");
		if (panel!=null) panel.toXML(zContent);
	}
	
	public void winActionCellToXML(JXMLContent zContent, JWin zWin, JColumnaLista oCol, int idCol, String id, int row) throws Exception {
		try {
			
			BizAction action = zWin.findActionByUniqueId(oCol.getIdAction()+"", false, false);
//			action.setRow(""+row);
			JBaseForm formFlat=action.findFormFlat(JBaseForm.BODY);
//			formFlat.setSourceAction(action);
			formFlat.setIdRow(""+(row)); // lo necesita para hacer el script de expand subdetails
			JWebWinForm form = new JWebWinForm(formFlat);
			form.setProviderName(this.getProviderName());
			form.setSourceAction(action);
			form.setToolbar(JBaseWin.TOOLBAR_NONE);
			form.setPreview(this.isPreview());
			form.ensureIsBuilt();

			zContent.startNode("d");
			zContent.setAttribute("type", "JWINFORM");
			zContent.setAttribute("height", zWin.getHeightRow());
			zContent.setAttribute("axis", idCol);
			zContent.setAttribute("visible", true);
			zContent.setAttribute("name", "cell_flat");
			String sColName = oCol.GetTitulo();
			String value = zWin.getFieldForeground(sColName) != null ? zWin.getFieldForeground(sColName) : oCol.getColorForeground();
			if (value != null) zContent.setAttribute("foreground",  value);
			value = zWin.getFieldBackground(sColName) != null ? zWin.getFieldBackground(sColName) : oCol.getColorBackground();
			if (value != null) zContent.setAttribute("background",  value);
			if (!isLineSelect())
				this.addDrop(zContent, "field_"+oCol.GetTitulo(), zWin);

			form.registerObject(zContent);
			form.getChildDeep("internal-panel").toXML(zContent);
			zContent.endNode("d");

		} catch (Exception e) {
			PssLogger.logError(e);
			zContent.startNode("d");
			zContent.setAttribute("visible", true);
			zContent.setAttribute("marked", false);
			zContent.setNodeText(e.getMessage());
			zContent.setAttribute("word_wrap", true);
			zContent.endNode("d");
		}
	}

	protected JWebAction getActionById(JWin zWin,String idAction,String idObj) throws Exception {
		if (idAction==null) return null;
		BizAction action = JTools.isNumber(idAction)?zWin.getActionMap().findAction((int)JTools.getLongFirstNumberEmbedded(idAction)):zWin.getActionMap().findActionByUniqueId(idAction);
		if (action == null)
			return null;
		if (!action.isOkAll()) return null; //HGK, tiene que controlar seguridad
		if (getForm()!=null&&!getForm().isModoConsulta())
			action.setUploadData(true);
		JWebServerAction wa = null;
		wa = JWebActionFactory.createViewAreaAndTitleAction(action, this, false, idObj);
		return wa;
	}
	
	public boolean selectAny() throws Exception {
		JWins oldSelect = JWebActionFactory.getCurrentRequest().getSession().getHistoryManager().findLastMultipleSelect(this.sourceAction);
		return (oldSelect == null) ? false : oldSelect.getRecords().getStaticItems().size() > 0;
	}

	public long getSelection() throws Exception {
		JWins oldSelect = JWebActionFactory.getCurrentRequest().getSession().getHistoryManager().findLastMultipleSelect(this.sourceAction);
		if (oldSelect==null) return 0;
		return oldSelect.getRecords().getStaticItems().size();
	}
	protected void attachBackAction() throws Exception {
		if (!this.bBackBotton)
			return;
		if (!this.sourceAction.hasBackAction())
			return;
		if (this.isEmbedded())
			return;
		if (this.isPreview())
			return;
		if (!BizUsuario.retrieveSkinGenerator().attachBackActionToToolbar(this.getWins().hasDropListener()?"win_list":"win"))
		 return;
		this.oActionBar.addBackAction(winsObjectId, getWins().confirmBack(),null);
	}

	@Override
	public String getCompletePanelName() throws Exception {
		String s="";
		return "win_list_complete_"+s + this.getProviderName();
	}

	@Override
	public String getCenterPanelName() throws Exception {
		String s="";
		return "center_panel_" +s+ this.getProviderName();
	}

	@Override
	public String getPreviewCanvasPanelName() throws Exception {
		String s="";
		return "preview_canvas_" +s+ this.getProviderName();
	}

	@Override
	public String getPreviewPanelName() throws Exception {
		String s="";
		return "preview_panel_" +s+ this.getProviderName();
	}


	@Override
	public boolean isEmbedded() throws Exception {
		return this.bEmbedded;
	}

	@Override
	public void setEmbedded(boolean v) throws Exception {
		this.bEmbedded = v;
	}

	public boolean isFormLov() {
		return bFormLov;
	}

	public void setFormLov(boolean bFormLov) {
		this.bFormLov = bFormLov;
	}

	@Override
	public boolean isPreview() throws Exception {
		// se fija si estoy en el panel de preview, puede ser lista o estan en una
		// form
		return this.bPreview;
		// if (this.bPreview) return true;
		// JWebWinForm form = (JWebWinForm)this.getForm();
		// if (form==null) return false;
		// return form.isPreview();
	}

	@Override
	public void setPreview(boolean v) throws Exception {
		this.bPreview = v;
	}

	@Override
	public boolean hasPreviewPanel() throws Exception {
		if (!this.hasPreview())
			return false;
		if (this.isPreviewFlagMax())
			return false;
		return true;
	}


	@Override
	public boolean isInternalToolbar() throws Exception {
		if (this.isEmbedded())
			return true;
		if (this.isPreview())
			return true;
		return false;
	}

	public JWebWinActionBar getActionBar() throws Exception {
		return this.oActionBar;
	}

	public JWebViewInternalComposite createActionBar() throws Exception {
		this.oActionBar=new JWebWinActionBarWinList(this, false, this.getToolbar(), this.isEmbedded());
		this.oActionBar.setPreview(false);
		this.oActionBar.setRootPanel(BizUsuario.retrieveSkinGenerator().createActionBarWinList(this));
		this.oActionBar.setFalseInternalToolbar(isFalseInternalToolbar());
		return this.oActionBar.getRootPanel();
	}

	@Override
	public JWebActionData findPagination(JWebActionData nav) throws Exception {
		if (nav == null)
			return null;
		if (nav.isNull())
			return null;
		if (!nav.hasField("from"))
			return null;
		return nav;
	}
	public JWebViewComposite getRootPanel() {
		return this.oRootPanel;
	}
	
	public boolean hasPagination() throws Exception {
		if (isForceHidePaginationBar()) return false;
		if (!isShowFooterBar()) return false;
		if (!this.hasWins()) return false;
		return this.getWins().isWebPageable();
	}
	
	protected JWebActionData recoveryNavigationMap() throws Exception {
		JWebRequest req = JWebActionFactory.getCurrentRequest();
		JWebActionData nav = this.findPagination(req.getData("win_list_nav"));
		if (nav != null) {
			if (!nav.get("name").trim().equals(this.getWins().GetTitle().trim())) 
				nav.add("from", "0");
		} else {
			nav = this.findPagination(req.getSession().getHistoryManager().findLastNavData(this.sourceAction));
			if (nav != null) {
				if (!nav.get("name").equals(this.getWins().GetTitle()))
					nav.add("from", "0");
			}
		}
		return nav;
	}
	protected int getDefaultPageSize() throws Exception {
		return JWebActionFactory.getCurrentRequest().getSession().getUserProfile().getWinListPagination();
	}
	protected int recoveryPageSize(JWebActionData nav) throws Exception {
		int ps = nav!=null&&nav.get("page_size")!=null?Integer.parseInt(nav.get("page_size")): getDefaultPageSize();;
		if (ps==-1) return getDefaultPageSize();
		JWebRequest req =JWebActionFactory.getCurrentRequest();
		JWebActionData extraData = req.getFormData(getProviderName());
		if (extraData==null) return ps;
		String data =  extraData.get("sizepage");
		if (data==null || data.equals("")) return ps;
		ps =  (int)JTools.getDoubleNumberEmbedded(data);
		if (ps==-1||ps==0) return getDefaultPageSize();
		return ps;
	}
	
	protected void savePageSize(JWebActionData nav,int pageSize) throws Exception {
		JWebRequest req = JWebActionFactory.getCurrentRequest();
		if (nav!=null) {
			nav.replace("page_size", ""+pageSize);
			JHistoryProvider p = req.getSession().getHistoryManager().getLastHistory().findProvider(this.sourceAction);
			if (p != null)
				p.setNavigation(nav);
		}
	}	
	protected int initializePagination() throws Exception {

		JWebRequest req = JWebActionFactory.getCurrentRequest();
		if (!hasPagination() || JWebActionFactory.isWinListFullExportRequest(req, this)) {
			this.iPageSize = -1;
			return -1;
		}	

		int iFromRow = -1;
		JWebActionData nav = recoveryNavigationMap();
	
		if (nav != null) {
			String sFrom = nav.get("from");
			String sPageSize = ""+recoveryPageSize(nav);
			if (sFrom != null && sPageSize != null) {
				int from = Integer.parseInt(sFrom);
				int pageSize = Integer.parseInt(sPageSize);
				if (from < 0 || pageSize < 1) {
					throw new RuntimeException("Invalid data pagination range: " + from + "," + (from + pageSize));
				}
				iFromRow = from;
				this.iPageSize = pageSize;
				savePageSize(nav,this.iPageSize);
			}
		} else {
			iFromRow = 0;
			this.iPageSize = this.getWins().getWebPageSize();
			if (iPageSize == -1)
				iPageSize =  getDefaultPageSize();
			savePageSize(nav,this.iPageSize);
		}
		return iFromRow;
		
	}
//si te tira execepcion porque el readAll le hiciste return, recorda
	// reimplmentar canReadAll acorde a si se puede hacer o no el readall
	protected long calculateTotalCount() throws Exception {
		totalCountElement = analizeSize();
		if (totalCountElement>0) return totalCountElement; // previamente calculado
		
		if (this.getWins().getRecords().getTop() != -1)
			return this.getWins().getRecords().getTop();
		
		if (totalCountElement != -1)
			return totalCountElement;
		
		totalCountElement = this.getWins().selectSupraCount();
		return totalCountElement;
	}
	
	protected void navigationBarToXML(JXMLContent zContent) throws Exception {
		if (this.oNavigationBar == null)
			return;


		BizUsuario.retrieveSkinGenerator().buildNavigationBar(this,oNavigationBar);
		BizUsuario.retrieveSkinGenerator().buildExportBar(this,oExportBar);
		BizUsuario.retrieveSkinGenerator().buildInfoBar(this,oInfoBar);
		BizUsuario.retrieveSkinGenerator().buildFooterExtraBar1(this,oFooterExtraBar1);
		BizUsuario.retrieveSkinGenerator().buildFooterExtraBar2(this,oFooterExtraBar2);
		
	}

	void generateActionRowsToXML(JXMLContent zContent) throws Exception {
		zContent.startNode("default_actions");
		JIterator<String> it = getActionsBase().getKeyIterator();
		while (it.hasMoreElements()) {
			String key = it.nextElement();
			JWebAction action =getActionsBase().getElement(key);
			if (action==null) continue;
			zContent.startNode("default_action");
			zContent.setAttribute("name", key);
			action.toXML(zContent);
			zContent.endNode("default_action");
		}
		zContent.endNode("default_actions");
		
		
	}
	
	public JWebAction createWinListExportAllToJson(BizAction sourceAction, String idObject, String presentation) throws Exception {
		BizAction a = new BizAction();
		a.setDescrip("Exportar a JSon®");
		a.SetIdAction(sourceAction.getIdAction());
		a.setRow(sourceAction.getRow());
		JWebServerAction webaction = new JWebJSonAction();
		JWebAction wa = webaction.prepareWebAction(a, this, idObject);
		JWebActionData oData = wa.addData("presentation");
		oData.add("mode", presentation);

		oData = wa.addData("export");
		oData.add("serializer", "json");
		oData.add("title", getTitle() + ".json");
		oData.add("object", "win_list_" + getProviderName());
		oData.add("range", "all");
		oData.add("multiple", ""+isMultipleSelection());
		oData.add("preserve", "Y");
		oData.add("history", "N");

		oData = wa.addData("win_list_nav");
		oData.add("embedded", ""+isEmbedded());
		oData.add("is_preview", ""+isPreview());
		oData.add("minimusRows", ""+getMinumusRows());
		oData.add("extraRows", ""+getExtraRows());
		return wa;
	}



	@Override
	public void addDrop(JXMLContent zContent, String zone, JBaseWin win) throws Exception {
		String idListenerDrop = null;
		JBaseWin winDrag = win.getObjectDrageable(zone);
		if (winDrag != null) {
			zContent.setAttribute("drag", zContent.addObject(winDrag));
			zContent.setAttribute("dragclass", winDrag.getClass().getSimpleName());
		}

		String value = win.getDragForeground(zone) != null ? win.getDragForeground(zone) : null;
		if (value != null)
			zContent.setAttribute("foreground",  JTools.toHtmlColor(value));
		value = win.getDragBackground(zone) != null ? win.getDragBackground(zone) : null;
		if (value != null)
			zContent.setAttribute("background",  JTools.toHtmlColor(value));

		if (win.acceptDrop(zone)) {
			String zoneDrop = win.getZoneDrop(zone);
			JBaseWin listener = win.getListenerForDragAndDropWeb(zone, getWins());

			idListenerDrop = zContent.addObject(listener);

			JWebAction actDrag = JWebActionFactory.createDropAction(this, zoneDrop, idListenerDrop);
			if (actDrag != null) {
				zContent.startNode("drop");
				String accepts = win.getDropClassAccepted();
				if (accepts != null)
					zContent.setAttribute("dropclassaccept", accepts);
				actDrag.toXML(zContent);
				zContent.endNode("drop");
			}
		}
		JBaseWin listener = win.getDblClickObjectDrag(zone, getWins());
		if (listener == null)
			return;
		int action = win.getDblClickDragAction(zone, getWins());
		if (action == -1)
			return;
		BizAction dblClick = listener.getActionMap().findAction(action);
		if (dblClick == null)
			return;
		String idOwn = zContent.addObject(listener);
		JWebAction actDblClick = JWebActionFactory.createViewAreaAndTitleAction(dblClick, this, false, idOwn);
		if (actDblClick != null) {
			zContent.startNode("dblclickDrag");
			actDblClick.toXML(zContent);
			zContent.endNode("dblclickDrag");
		}

	}
	protected void analizeScript() throws Exception {
		if (!this.hasWins()) return;
		if (getWins().getScript()!=null) {
			setPosFunction(getWins().getScript().getScript());
		}
	}

	protected boolean hasMap() throws Exception {
		return false;
	}

	public boolean isAllowExportToMap() throws Exception {
		return this.hasMap();
	}

	public boolean isAllowExportToGraph() throws Exception {
		return this.hasGraph();
	}
	protected boolean hasGraph() throws Exception {
		return false;
	}

	public boolean isAllowExportToExcel() throws Exception {
		if (getWins() != null && getWins().hasAllowExportToExcel())
			if (!getWins().isAllowExportToExcel())
				return false;

		if (getWinList() != null && getWinList().hasAllowExportToExcel())
			if (!getWinList().isAllowExportToExcel())
				return false;

		return this.bAllowExportToExcel;
	}

	public void setAllowExportToExcel(boolean b) {
		this.bAllowExportToExcel = b;
	}

	public boolean isAllowExportToCSV()  throws Exception {
		if (getWins() != null && getWins().hasAllowExportToCSV())
			if (!getWins().isAllowExportToCSV())
				return false;

		if (getWinList() != null && getWinList().hasAllowExportToCSV())
			if (!getWinList().isAllowExportToCSV())
				return false;

		return this.bAllowExportToCSV;
	}

	public boolean isAllowExportToReport() throws Exception {
		if (getWins() != null && getWins().hasAllowExportToReport())
			if (!getWins().isAllowExportToReport())
				return false;

		if (getWinList() != null && getWinList().hasAllowExportToReport())
			if (!getWinList().isAllowExportToReport())
				return false;
		return this.bAllowExportToReport;
	}

	public void setAllowExportToCSV(boolean b) {
		this.bAllowExportToCSV = b;
	}

	public void setAllowExportToReport(boolean b) {
		this.bAllowExportToReport = b;
	}
	
	public int getRealPageSize() throws Exception {
		return iPageSize==-1?-1:iPageSize+(isShowFilterBar()?0:5);
	}
	
	protected JWebViewComponent attchGoBack(JWebViewComposite panel) throws Exception {
		if ( iFromRow <getRealPageSize()) return null;
		JWebAction webAction = JWebActionFactory.createWinListGoBack(this, this.sourceAction, this.sourceObjectId, columnOrden, direccionOrden, sAll,(int)totalCountElement, iFromRow - this.getRealPageSize(), iPageSize,this.paginado,this.iPaginaActual);
		return 	BizUsuario.retrieveSkinGenerator().createBotonPagination(panel,this.getProviderName()+"_prev",webAction,"Anterior",false);
	}
	protected JWebViewComponent attchPage(JWebViewComposite panel,int page) throws Exception {
		boolean active = page==(int)Math.ceil((iFromRow+1)/getRealPageSize())+((iFromRow+1)%getRealPageSize()==0?0:1);
		JWebAction webAction = JWebActionFactory.createWinListGoNext(this, this.sourceAction, this.sourceObjectId, columnOrden, direccionOrden, sAll,(int)totalCountElement, getRealPageSize()*(page-1), iPageSize,this.paginado,this.iPaginaActual);
		return 	BizUsuario.retrieveSkinGenerator().createBotonPagination(panel,this.getProviderName()+"_page"+page,webAction,""+page,active);
	}
	protected JWebViewComponent attchGoNext(JWebViewComposite panel) throws Exception {
		if (totalCountElement>0 && iFromRow+this.getRealPageSize()>=totalCountElement) return null;
			
		JWebAction webAction = JWebActionFactory.createWinListGoNext(this, this.sourceAction, this.sourceObjectId, columnOrden, direccionOrden, sAll,(int)totalCountElement, iFromRow + this.getRealPageSize(), iPageSize,this.paginado,this.iPaginaActual);
		return 	BizUsuario.retrieveSkinGenerator().createBotonPagination(panel,this.getProviderName()+"_next",webAction,"Siguiente",false);
	}
	public JWebViewComponent addSelectSizePage(JWebViewComposite panel) throws Exception {
		if (!getWins().isWebPageable()) return null;
		JWebAction webAction = JWebActionFactory.createWinListGoNext(this, this.sourceAction, this.sourceObjectId, columnOrden, direccionOrden, sAll,(int)totalCountElement, iFromRow, iPageSize,this.paginado,this.iPaginaActual);
		return 	BizUsuario.retrieveSkinGenerator().createCombo(panel,"sizepage","#Lineas",false,webAction,getProviderName(),""+getRealPageSize(),new JControlCombo() {
	  	@Override
	  	public JWins getRecords(boolean oneRow) throws Exception {
	  		return getPaginations();
	  	}
	  });
	}
	
	JWins sizesPage;

  private JWins getPaginations() throws Exception {
  	if (sizesPage!=null) return sizesPage;
   	return sizesPage=JWins.createVirtualWinsFromMap( BizUsuario.retrieveSkinGenerator().getPaginations(getMaxLineas()));
  }

	public JWebViewComponent addPagination(JWebViewComposite panel) throws Exception {
		if (!this.hasPagination()) return null;
		JWebViewInternalComposite pagBar= BizUsuario.retrieveSkinGenerator().createPaginationBar(panel,false, this.getProviderName());
		attchGoBack(pagBar);
		if (totalCountElement>0 && getRealPageSize()>0) {
			int totalPages = (int)Math.ceil(totalCountElement/getRealPageSize())+(totalCountElement%getRealPageSize()==0?0:1);
			int pageActual=(int)Math.ceil((iFromRow+1)/getRealPageSize())+((iFromRow+1)%getRealPageSize()==0?0:1);
			int pageMiddle=totalPages/2;
			int pageIni = pageActual-2;
			int pageFin = pageActual+2;
			if (pageIni<1) {
				pageIni=1;
				pageFin=5;
			}
			if (pageFin>totalPages) {
				pageFin=totalPages;
				pageIni=pageFin-4;
				if (pageIni<1)
					pageIni=1;
			}
			boolean last=true;
			boolean middle=true;
			if (pageIni>1) {
				attchPage(pagBar, 1);
				if (totalPages==1) last=false;
				if (pageMiddle==1) middle=false;
			}
			for (int p=pageIni;p<=pageFin;p++) {
				if (p>pageMiddle && middle && pageMiddle!=0) {
					attchPage(pagBar, pageMiddle);
					middle=false;
				}
				attchPage(pagBar, p);
				if (p==totalPages) last=false;
				if (p==pageMiddle) middle=false;
			}
			if (middle && pageMiddle!=0)
				attchPage(pagBar, pageMiddle);
			if (last)
				attchPage(pagBar, totalPages);
		}
		
		attchGoNext(pagBar);
		JWebViewInternalComposite pagBarMini= BizUsuario.retrieveSkinGenerator().createPaginationBar(panel,true, this.getProviderName());
		attchGoBack(pagBarMini);
		attchGoNext(pagBarMini);
		return pagBar;
	}
	public JWebViewComponent addButtonModeFilters(JWebViewComposite panel) throws Exception {
		return skin().createHideShow(panel,this.getProviderName()+"_hideshow_filter",isShowFilterBar(),JWebActionFactory.createWinListHideShowFilter(this, this.getSourceAction(), this.sourceObjectId, columnOrden, direccionOrden, sAll,(int)totalCountElement, iFromRow, this.iPageSize,this.paginado,this.iPaginaActual),"Filtros","Act","Desact.");
	}
	public JWebViewComponent addButtonModeFiltersInFormFilter(JWebViewComposite panel) throws Exception {
		return skin().createHideShow(panel,this.getProviderName()+"_hideshow_filter",isShowFilterBar(),JWebActionFactory.createWinListHideShowFilter(this, this.getSourceAction(), this.sourceObjectId, columnOrden, direccionOrden, sAll,(int)totalCountElement, iFromRow, this.iPageSize,this.paginado,this.iPaginaActual),"","-","+");
	}

	public JWebViewComponent addButtonOrderInFormFilter(JWebViewComposite panel) throws Exception {
		BizAction action = getWins().findAction(99998);
		if (action==null) return null;
		JWebAction webAction = JWebActionFactory.createViewAreaAndTitleAction(action, this, false,  this.winsObjectId, null, true);
		JWebViewComponent button = this.skin().buildOrderButtonInFilter(this,webAction);
		if (button!=null) {
			panel.addChild(this.getProviderName()+"_order_filter", button);
		}
		return button;
	}
	
	public JWebViewComponent attachOrderInFormFilter(JWebViewComposite panel) throws Exception {
		if (this.useSimpleOrden()) return null;
		if (!this.getWinList().isWithOrderBy()) return null;
		BizAction action = this.getWins().findAction(99998);
		if (action==null) return null;
		JWebAction webAction = JWebActionFactory.createViewAreaAndTitleAction(action, this, false,  this.winsObjectId, null, true);
		JWebViewComponent button = skin().buildOrderButtonInFilter(this,webAction);
		if (button!=null) {
			panel.addChild(this.getProviderName()+"_order_filter_2", button);
		}
		return button;
	}

	public JWebViewComponent addButtonModePreview(JWebViewComposite panel) throws Exception {
		if (!this.hasPreview()) return null;
		return skin().createHideShow(panel,this.getProviderName()+"_hideshow_preview",findPreviewFlag().equals(JWins.PREVIEW_SI),JWebActionFactory.createWinListWithPreview(this, this.getSourceAction(), this.sourceObjectId, columnOrden, direccionOrden, sAll,(int)totalCountElement, iFromRow, this.iPageSize,this.paginado,this.getPaginaActual()),"Vista previa","Act","Desact.");
	
	}
	public JWebViewComponent addInfoRecord(JWebViewComposite panel) throws Exception {
		String info="";
		if (totalCountElement>0) {
			if (((iFromRow+getRealPageSize())<totalCountElement || iFromRow>0) && (iFromRow>0 && getRealPageSize()>0))
				info="Filas "+totalCountElement+" ("+iFromRow+" a "+(iFromRow+getRealPageSize())+")";
			else
				info="Filas "+totalCountElement;
		} else if (totalCountElement>=0 && iFromRow>0 && getRealPageSize()>0) 
			info="("+iFromRow+" a "+(iFromRow+getRealPageSize())+")";
			

		
		// falta info selecion
		String selectInfo = "";
		long sizeSel = 0;
		if (isMultipleSelection() && selectAny()) {
			sizeSel = this.getSelection();
			selectInfo = "(Selecc." + sizeSel + " filas en otras paginas) ";
			info+=selectInfo;
		}
		return 	BizUsuario.retrieveSkinGenerator().createInfo(panel, info, null);
	}

	
	public JWebViewComponent addButtonExportToMap(JWebViewComposite panel) throws Exception {
		if (!this.isAllowExportToMap()) return null; 
		return 	BizUsuario.retrieveSkinGenerator().createBotonExport(panel,this.getProviderName()+"_export_to_map",JWebActionFactory.createWinListExportAllToMap(this.getSourceAction(), this, this.sourceObjectId));
	}
	public JWebViewComponent addButtonExportToGraph(JWebViewComposite panel) throws Exception {
		if (!this.isAllowExportToGraph()) return null; 
		return BizUsuario.retrieveSkinGenerator().createBotonExport(panel,this.getProviderName()+"_export_to_graph",JWebActionFactory.createWinListExportAllToGraph(this.getSourceAction(), this, this.sourceObjectId));
	}
	public JWebViewComponent addButtonExportToExcel(JWebViewComposite panel) throws Exception {
		if (!this.isAllowExportToExcel()) return null;
		if (getWins().isExportToDownloadForm()) 
			return 	BizUsuario.retrieveSkinGenerator().createBotonExport(panel,this.getProviderName()+"_export_to_excel",JWebActionFactory.createWinListExportAllToDownload(this.getSourceAction(), this, this.sourceObjectId,JBaseWin.MODE_LIST,"xlsx"));
		return 	BizUsuario.retrieveSkinGenerator().createBotonExport(panel,this.getProviderName()+"_export_to_excel", JWebActionFactory.createWinListExportAllToExcel(this.getSourceAction(), this, this.sourceObjectId,JBaseWin.MODE_LIST));
	}
	public JWebViewComponent addButtonExportToCSV(JWebViewComposite panel) throws Exception {
		if (!this.isAllowExportToCSV()) return null; 
		if (getWins().isExportToDownloadForm()) 
			return 	BizUsuario.retrieveSkinGenerator().createBotonExport(panel,this.getProviderName()+"_export_to_csv",JWebActionFactory.createWinListExportAllToDownload(this.getSourceAction(), this, this.sourceObjectId,JBaseWin.MODE_LIST,"csv"));
		return 	BizUsuario.retrieveSkinGenerator().createBotonExport(panel,this.getProviderName()+"_export_to_csv",JWebActionFactory.createWinListExportAllToCSV(this.getSourceAction(), this, this.sourceObjectId,JBaseWin.MODE_LIST));
	}
	public JWebViewComponent addButtonExportToReport(JWebViewComposite panel) throws Exception {
		if (!this.isAllowExportToReport()) return null; 
		if (getWins().isExportToDownloadForm()) 
			return 	BizUsuario.retrieveSkinGenerator().createBotonExport(panel,this.getProviderName()+"_export_to_pdf",JWebActionFactory.createWinListExportAllToDownload(this.getSourceAction(), this, this.sourceObjectId,JBaseWin.MODE_LIST,"pdf"));
		return 	BizUsuario.retrieveSkinGenerator().createBotonExport(panel,this.getProviderName()+"_export_to_pdf",JWebActionFactory.createWinListExportAllToReport(this.getSourceAction(), this, this.sourceObjectId,JBaseWin.MODE_LIST));
	}
	
	public boolean calculeIsSelected(String id,String iOldSingleSelect,JWins iOldSelected,long iRowPos,JWin zWin) throws Exception {
		if (iOldSingleSelect==null) return false;
		if (this.getWins().isNeverSelected()) return false;
		boolean selected = iOldSingleSelect.equals("")?false:(iRowPos==Integer.parseInt(iOldSingleSelect));
		if (!selected && this.isMultipleSelection() && iOldSelected != null && !this.isPreview())
			selected = iOldSelected.extract(zWin.getRecord());


		if (forceSelected==null) {
			if (this.isPreviewFlagSi())
				this.forceSelected = true;
			else
				this.forceSelected = false;
		}
		if (this.forceSelected && !this.isPreview() && iRowPos == 0 && iOldSingleSelect.equals("-1"))
			selected=true;	
		return selected;
	}
	protected int getRowSpan(JColumnaLista col, JWin win) throws Exception {
		if (!col.hasSpan())
			return -1;
		JRecords recs = this.getWins().getRecords();
		if (!recs.isStatic())
			JExcepcion.SendError("Span soportado solo para listas estaticas");

		long len = recs.sizeStaticElements();

		// me fijo el de arriba
		int row = recs.GetCurrRow();
		if (row != 0) {
			JRecord pre = recs.getStaticElement(row - 1);
			if (pre.getProp(col.getSpan()).toFormattedString().equals(win.getRecord().getProp(col.getSpan()).toFormattedString()))
				return 0;
		}

		row = recs.GetCurrRow();
		// me fijo el de bajo
		int i = 0;
		while (row + i + 1 < len) {
			JRecord pos = recs.getStaticElement(row + i + 1);
			if (!pos.getProp(col.getSpan()).toFormattedString().equals(win.getRecord().getProp(col.getSpan()).toFormattedString()))
				break;
			i++;
		}
		if (i > 0)
			return i + 1;
		return -1;
	}
	public Serializable getData() {
		return data;
	}
	public void setData(Serializable data) {
		this.data = data;
	}


}
