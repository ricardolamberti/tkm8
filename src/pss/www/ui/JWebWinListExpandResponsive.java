package pss.www.ui;

import java.awt.Dimension;

import pss.core.tools.JDateTools;
import pss.core.tools.JTools;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JMap;
import pss.core.win.JBaseWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.actions.BizActions;
import pss.core.win.submits.JActWins;
import pss.core.win.totalizer.JTotalizer;
import pss.core.winUI.controls.JFormLista;
import pss.core.winUI.lists.JColumnaLista;
import pss.core.winUI.lists.JGrupoColumnaLista;
import pss.core.winUI.lists.JWinList;
import pss.core.winUI.responsiveControls.IResizableView;
import pss.www.platform.actions.JWebAction;
import pss.www.platform.actions.JWebActionFactory;
import pss.www.platform.actions.JWebRequest;
import pss.www.platform.actions.JWebServerAction;
import pss.www.platform.actions.requestBundle.JWebActionData;
import pss.www.platform.content.generators.JXMLContent;
import pss.www.ui.controller.JWebFormControl;

public class JWebWinListExpandResponsive extends JWebWinGenericResponsive implements JAbsolutelyNamedWebViewComponent, JWebControlInterface{

	//
	// CONSTRUCTOR
	//
	/*
	 * public JWebWinList(JWins zWins) throws Exception { this.getWins() = zWins;
	 * this.ensureIsBuilt(); //JWebWinList(zWins, true); }
	 */
	public JWebWinListExpandResponsive(BizAction action, boolean embedded) throws Exception {
		this(action, embedded, false);
	}

	public JWebWinListExpandResponsive(BizAction action, boolean embedded, boolean formLov) throws Exception {
		this.sourceAction = action;
		// this.bWithFrame=withFrame;
		this.setEmbedded(embedded);
		this.setFormLov(formLov);


	}
	public String getPresentation() throws Exception {
		return JBaseWin.MODE_EXPAND;
	};


	public JWebViewComposite getRootPanel() {
		return this.oRootPanel;
	}


	public static JWebWinListExpandResponsive create(JWebViewComposite parent,  JFormLista control) throws Exception {
		BizAction pageAction = control.getAction();
	
		JWebViewComposite realParent = parent;
		if (parent instanceof JWebTabPanelResponsive) {
			JWebTabResponsive tab = new JWebTabResponsive();
			tab.setTitle(pageAction.GetDescr());
			tab.setId(control.getIdControl());
	    if (parent!=null) {
				tab.setPreview(parent.findJWebWinForm().isPreview());
	    	parent.addChild(tab.getObjectName(), tab);
	    }
	    realParent=tab;
		}
		JWebWinListExpandResponsive webList=new JWebWinListExpandResponsive(control.getAction(), true);
		webList.takeAttributesFormControlResponsive(control);
		webList.setParentProvider(parent.findJWebWinForm());
		webList.setNotebookParent(pageAction.getIdAction());
		JActWins act = (JActWins) pageAction.getObjSubmit();
		webList.setMultipleSelection(act.isMultiple());
		webList.setBackBotton(false);
		webList.setLineSelect(act.isLineSelect());
		webList.setReadOnly(control.isReadOnly());
		webList.setToolbar(control.getToolbar());
		webList.setTitle(control.getTitle());
		webList.setEmbedded(true);
		webList.setPreviewFlag(JWins.PREVIEW_MAX);
		webList.setDistributionObjects(control.getDistributionObjects());
		webList.setZoomtofit(control.getZoomtofit());
		webList.setListResponsive(control.isListaResponsive());
		webList.setMode(control.getMode());
		webList.setUseContextMenu(control.getUseContextMenu());

		webList.setNeedRefreshAllScreen(control.isNeedRefreshAllScreen());
		webList.ensureIsBuilt();
		webList.setName(pageAction.getIdAction());
		realParent.addChild(webList.getRootPanel().getName(), webList.getRootPanel());
		return webList;

	}
	
	public JWebWinActionBar getActionBar() throws Exception {
		return this.oActionBar;
	}

	@Override
	protected void build() throws Exception {

		if (this.winList == null)
			this.winList = new JWinList(getWins());
		setPreview(false);
		setPreviewFlag(JWins.PREVIEW_MAX);
		if (!hasWins())
			return;

		iFromRow = initializePagination();
		this.analizeSelectAll();
		this.analizeOrden();
		this.analizeHideShowFilter();
		// this.analizePreview();
		this.regiterObjects();

		super.build();
	}

	//
	// SUPERCLASS OVERRIDING
	//
	@Override
	public void validate() throws Exception {

	}

	@Override
	public void destroy() {
		if (this.hasWins()) {
			try {
				this.getWins().getRecords().closeRecord();
				if (this.getWins() instanceof JWebReportWins) {
					((JWebReportWins) this.getWins()).cleanUp();
				}
			} catch (Exception e) {
			}
			releaseWins();
		}
		if (this.oNavigationBar != null) {
			this.oNavigationBar.destroy();
			this.oNavigationBar = null;
		}
		if (this.oActionBar != null) {
			this.oActionBar.destroy();
			this.oActionBar = null;
		}
		super.destroy();
	}

	public String getAbsoluteName() {
		return this.getName();
	}

	@SuppressWarnings("unchecked")
	public Class<?> getAbsoluteClass() {
		return JWebWinListResponsive.class;
	}

	@Override
	public String getComponentType() {
		return "win_expand_responsive";
	}

	

	// protected void componentToXML(JXMLContent zContent) throws Exception {
	@Override
	protected void containerToXML(JXMLContent zContent) throws Exception {
	}


	@Override
	protected void componentPreLayout(JXMLContent zContent) throws Exception {
		this.attachActionBar();

	}

	protected void addProviderHistory(JXMLContent zContent) throws Exception {
		zContent.getGenerator().getSession().getHistoryManager().addHistoryProvider(this.sourceAction);
	}

	@Override
	protected void componentToXML(JXMLContent zContent) throws Exception {
		JInternalGenerateXML iXML = new JInternalGenerateXML();
		iXML.oWebWinList = this;
		iXML.zContent = zContent;
		this.addProviderHistory(zContent);
		if (!iXML.startDeclarations())
			return;

		iXML.readData();
		iXML.initializeGraphics();
		iXML.determineFirstRow();

		iXML.generateHeader();
		iXML.generateRows();
		
		iXML.generateFooter();
		iXML.generateNavigationBar();
		getActionBar().containerToXMLActions(zContent);
		// this.clearUnusedActions();
	}


	protected void headerToXML(JWins zWins, JXMLContent zContent, JWin oFirstWin, boolean isWinListFullExportRequest) throws Exception {


		zContent.startNode("header");
		zContent.setAttribute("has_subdetail", zWins.hasSubDetail(isWinListFullExportRequest));
		zContent.setAttribute("datetime", JDateTools.CurrentDateTime());
		zContent.setAttribute("filters", formFiltros == null ? "" : formFiltros.toString());

		zContent.endNode("header");

	}

	protected void footToXML(JXMLContent zContent) throws Exception {
//		zContent.startNode("footer");
//
//		zContent.endNode("footer");
	}

	protected boolean hasGraph() throws Exception {
		return winList.getGraficosLista().size() > 0;
	}

	protected boolean hasMap() throws Exception {
		return winList.canMapear();
	}

	//
	// INTERNAL IMPLEMENTATION
	//
	protected void analizeSelectAll() throws Exception {
		JWebActionData oListNavData = null;
		JWebRequest oRequest = JWebActionFactory.getCurrentRequest();
		oListNavData = oRequest.getData("win_list_nav");
		if (oListNavData != null) {
			sAll = oListNavData.get("all");
		}

		JWins oldSelect = JWebActionFactory.getCurrentRequest().getSession().getHistoryManager().findLastMultipleSelect(this.sourceAction);
		boolean selectAny = oldSelect == null ? false : oldSelect.getRecords().getStaticItems().size() > 0;
		if (sAll != null) {
			if (sAll.equals("S") && !selectAny) {
				JWins wins = getWins().createClone();
				if (getWins().getRecords().isStatic()) {
					wins.SetEstatico(true);
					JIterator<JWin> it = getWins().getStaticIterator();
					while (it.hasMoreElements()) {
						JWin w = it.nextElement();
						wins.addRecord(w);
					}
				} else {
					wins.getRecords().applyFiltersFromList(getWins().getRecords().getFilters());
					wins.getRecords().applyFixedFiltersFromList(getWins().getRecords().getFixedFilters());
					wins.getRecords().applyJoinsFromList(getWins().getRecords().getJoins());
					wins.getRecords().applyGroupsFromList(getWins().getRecords().getGroupBy());
					wins.toStatic();
				}
				JWebActionFactory.getCurrentRequest().getSession().getHistoryManager().getLastHistory().findProvider(this.sourceAction).setMultipleSelect(wins);
			} else if (sAll.equals("S") && selectAny) {
				JWebActionFactory.getCurrentRequest().getSession().getHistoryManager().getLastHistory().findProvider(this.sourceAction).setMultipleSelect(null);
			}
		}
		sAll = "N";
		return;
	}


	void rowToXML(JXMLContent zContent, JWin zWin, int iRowPos, JWins iOldSelected, String iOldSingleSelect, boolean isWinListFullExportRequest) throws Exception {
		String forcedPos = this.getProviderName() + "_" + this.getWins().getClass().getSimpleName() + "_" + iRowPos;
		zContent.setAttribute("rowpos", iRowPos);
		//zContent.setAttribute("form_name", getProviderName());
		if (isWinListFullExportRequest && hasMap()) {
			zContent.setAttribute("mapdir", zWin.getDireccionMapa());
			zContent.setAttribute("mapname", JTools.replace(zWin.getNombreMapa(), "\"", " "));
		}
		
		// represent the action bar actions for the given Win
		if (this.hasActionBar()) {
			zWin.getRecord().keysToFilters();

			String id = zContent.addObject(forcedPos, zWin);
			this.rowToXMLActionBar(zContent, zWin, id);
			
			boolean selected = calculeIsSelected(id, iOldSingleSelect, iOldSelected, iRowPos, zWin);

			zContent.setAttribute("selected", selected);
		}
		String title = zWin.getFieldTooltip(null);
		if (title != null)
			zContent.setAttribute("tooltip", title);

		if (zWin.getRecord() instanceof IResizableView) {
			zContent.setAttribute("resize", true);
			zContent.setAttribute("cv_posx", ((IResizableView)zWin.getRecord() ).getPosX());
			zContent.setAttribute("cv_posy", ((IResizableView)zWin.getRecord() ).getPosY());
			zContent.setAttribute("cv_width", ((IResizableView)zWin.getRecord() ).getWidth());
			zContent.setAttribute("cv_height", ((IResizableView)zWin.getRecord() ).getHeight());
		}
		addDrop(zContent, "data_" + iRowPos, zWin);
		JWebAction action = this.getAction(zWin,forcedPos,iRowPos);
		if (action != null) {
			zContent.setAttribute("id", forcedPos);
			zContent.setAttribute("class_responsive", "col-sm-4");
	//		zContent.setAttribute("height", 300);
			action.toXML(zContent);
		}


	
		if (!isReadOnly()) {
			zContent.startNode("rightclick");
			zContent.setAttribute("obj_provider", this.getProviderName());
			zContent.endNode("rightclick");
			JWebAction actDblClick = this.getActionDefault(zWin);
			if (actDblClick != null) {
				zContent.startNode("dblclick");
				actDblClick.toXML(zContent);
				zContent.endNode("dblclick");

			}
			JWebAction actDrag = this.getActionDrop(zWin);
			if (actDrag != null) {
				zContent.startNode("drop");
				actDrag.toXML(zContent);
				zContent.endNode("drop");
			}

		}
	}


	protected JWebAction getActionDefault(JWin zWin) throws Exception {
		// if (this.actionDefault!=null) return this.actionDefault;
		BizAction action = null;
		// if (getWins().isEditable()) {
		// action=zWin.findAction(JWin.ACTION_UPDATE);
		// if (action!=null) return
		// JWebActionFactory.createWinListEdit(action,this);
		// }
		action = zWin.findActionByUniqueId(zWin.getDobleClick());
		if (action==null) action = zWin.findActionByUniqueId(zWin.getDobleClickSecondChance());
		if (action == null)
			action = zWin.findAction(JWin.ACTION_DROP);
		if (action == null)
			action = zWin.findAction(JWin.ACTION_QUERY);
		if (action == null)
			return null;
		// return
		// (this.actionDefault=JWebActionFactory.createViewAreaAction(action,
		// this, false, null));
		return JWebActionFactory.createViewAreaAndTitleAction(action, this, false, null);
	}

	private BizAction findAction(JWin zWin) throws Exception {

		BizAction action = null;
		BizActions actions = zWin.getActionMap();
		if (actions == null)
			return null;
		action = actions.findActionByUniqueId(zWin.getSimpleClick());
		if (action == null)
			action = zWin.getActionMap().findAction(JWin.ACTION_QUERY);
		return action;
	}

	protected JWebAction getActionDrop(JWin zWin) throws Exception {
		if (zWin.getActionDrop() == -1)
			return null;
		BizAction action = null;
		action = zWin.findAction(zWin.getActionDrop());
		if (action == null)
			return null;
		return JWebActionFactory.createViewAreaAndTitleAction(action, this, false, null);
	}

	protected JWebAction getAction(JWin zWin,String ajaxContainer,long row) throws Exception {
		BizAction action = this.findAction(zWin);
		if (action == null)
			return null;
		JWebServerAction wa = null;
			wa = JWebActionFactory.createListExpandAreaAction(action, this, ajaxContainer,ajaxContainer,""+row);//al objectid, le pongo el mismo nombre que ajaxcontainer
			JWebActionData nav = wa.getNavigationData();
			nav.add("embedded", "" + this.isEmbedded());
			nav.add("row", "" + row);
			nav.add("is_preview", ""+true);

		return wa;
	}

	protected JWebAction getActionShowAction(JXMLContent zContent) throws Exception {
		BizAction action = getWins().getActionMap().findAction((int) getWins().getForceActionPreview());
		if (action == null)
			return null;
		JWebServerAction wa = null;
		wa = JWebActionFactory.createPreviewShowAreaAreaAction(action, this, zContent.addObject(getWins()));
		JWebActionData nav = wa.getNavigationData();
		nav.add("embedded", "" + this.isEmbedded());
		nav.add("is_preview", ""+true);
		return wa;
	}

	@Override
	public Dimension getDefaultSize() {
		return new Dimension(600, 400);
	}

	public boolean isAllowExportToMap() throws Exception {
		return this.hasMap();
	}

	public boolean hasHideShowFilterOption() throws Exception {
		if (this.formFiltros == null)
			return false;
		if (this.sourceAction == null)
			return false;
		if (!sourceAction.hasOwner())
			return false;
		return sourceAction.hasRefreshAction();
	}



	
	public JWebAction getWebSourceAction() throws Exception {
		JWebServerAction a = JWebActionFactory.createViewAreaAndTitleAction(this.sourceAction, this, true, null);
		JWebActionData nav = a.addData("win_list_nav");
		nav.add("with_preview", "" + this.findPreviewFlag());
		return a;
	}


	@Override
	synchronized public void ensureIsBuilt() throws Exception {
		super.ensureIsBuilt();
	}


	public String getScroll(JXMLContent zContent) throws Exception {
		JWebRequest oRequest = zContent.getGenerator().getRequest();
		if (oRequest.getSession().getHistoryManager().getLastHistory() == null)
			return "";
		return oRequest.getSession().getHistoryManager().findLastScroll(this.sourceAction);
	}

	public JWins getOldSelect(JXMLContent zContent) throws Exception {
		JWebRequest oRequest = zContent.getGenerator().getRequest();
		if (oRequest.getSession().getHistoryManager().getLastHistory() == null)
			return null;
		return oRequest.getSession().getHistoryManager().findLastMultipleSelect(this.sourceAction);
	}

	public String getOldSingleSelect(JXMLContent zContent) throws Exception {
		JWebRequest oRequest = zContent.getGenerator().getRequest();
		if (oRequest.getSession().getHistoryManager().getLastHistory() == null)
			return null;
		return oRequest.getSession().getHistoryManager().findLastSelectItem(this.sourceAction);
	}

	public boolean isMultipleSelection() {
		return bMultipleSelection;
	}

	public void setMultipleSelection(boolean multipleSelection) {
		bMultipleSelection = multipleSelection;
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

	/*
	 * public void setBackAction(boolean value) { this.backAction = value; }
	 */

	protected String getNotebookParent() {
		return notebookParent;
	}

	protected void setNotebookParent(String notebookParent) {
		this.notebookParent = notebookParent;
	}

	public int getWidthBorder() {
		return widthBorder;
	}

	public void setWidthBorder(int widthBorder) {
		this.widthBorder = widthBorder;
	}

	public String getWidthPage() {
		if (winList.getPageType().equals(JWinList.PAGETYPE_LANDSCAPE)) {
			return "11.693055555555556in";
		} else {
			return "8.268055555555555in";
		}
	}

	public String getHeigthPage() {
		if (winList.getPageType().equals(JWinList.PAGETYPE_LANDSCAPE)) {
			return "8.268055555555555in";
		} else {
			return "11.693055555555556in";
		}
	}

	protected String findIcon(JWin win) throws Exception {
		try {
			return win.GetIconFile();
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * Esta clase interna, articula el armado de xml. Deberia contener la logica
	 * del recorrido de los datos, pero lo menos posible de la logica del
	 * JWebWinList
	 *
	 */
	class JInternalGenerateXML {
		JWebRequest oRequest;
		JXMLContent zContent;
		JWebWinListExpandResponsive oWebWinList;
		JMap<Long,String> cortes; 

		JWin oFirstWin = null;
		int iRowCursor = -1;
		boolean supportOffset = false;
		boolean goesNext = false;
		boolean goesBack = false;
		boolean bWithIcon;
		int iToRow;
		String oldSingleSelect;
		JWins oldSelect;
		JColumnaLista[] oColumns;
		JGrupoColumnaLista[][] oGrupoColumns;

		boolean startDeclarations() throws Exception {
			if (!hasWins())
				return false;
			oRequest = zContent.getGenerator().getRequest();
			isWinListFullExportRequest = JWebActionFactory.isWinListFullExportRequest(oRequest, oWebWinList);
			isHtmlRequest = !JWebActionFactory.isJSON(oRequest);
			oldSelect = getOldSelect(zContent);
			oldSingleSelect = getOldSingleSelect(zContent);

			zContent.setAttribute("draw", oRequest.getArgument("draw"));
			zContent.setAttribute("with_preview", hasPreviewPanel());
			zContent.setAttribute("distribution", getDistributionObjects());
			zContent.setAttribute("list_responsive", isListResponsive());
			zContent.setAttribute("ordering", isOrdeningControl());
			zContent.setAttribute("lengthchange", isLengthChangeControl());
			zContent.setAttribute("headerinfooter", isHeaderInFooter());
			zContent.setAttribute("searching", isSearchingControl());
			zContent.setAttribute("has_action_bar", hasActionBar());
			zContent.setAttribute("obj_provider", getProviderName());
			zContent.setAttribute("is_multiple_select", isMultipleSelection());
			zContent.setAttribute("is_line_select", isLineSelect());
			zContent.setAttribute("scroll", getScroll(zContent));
			zContent.setAttribute("pageWidth", getWidthPage());
			zContent.setAttribute("pageHeight", getHeigthPage());
			zContent.setAttribute("class_table_responsive", getClassTableResponsive());
			return !JWebActionFactory.isOtherObjectExportRequest(zContent.getGenerator().getRequest(), oWebWinList);
		}

		protected boolean isForceRealAll() throws Exception {
			return JWebActionFactory.isWinListFullExportRequest(zContent.getGenerator().getRequest(), oWebWinList);
		}

		protected void readData() throws Exception {
			getWins().setPagesize(getRealPageSize());
			getWins().setOffset(iFromRow);
			getWins().ConfigurarColumnasListaInternal(winList);
			zContent.setAttribute("title", getWins().GetTitle());
			if (columnOrden != null && !columnOrden.equals("")) {
				getWins().getRecords().clearOrderBy();
				getWins().addOrderByFromUI(columnOrden, (direccionOrden == null || direccionOrden.equals("")) ? "asc" : direccionOrden);
			}

			String alerta = getWins().anyAlert(-1);
			if (alerta != null)
				setAlert(alerta); // Warning referidos a los filtros
			if (!getWins().canReadAll(sourceAction) && !this.isForceRealAll())
				return;
			if (isWinListFullExportRequest && hasGraph()) {
				getWins().preGraphicRead();
			}
			if (getWins().GetVision() != null && getWins().GetVision().equals("PREVIEW"))
				getWins().setPagesize(10);

			getWins().ReRead();
			calculateTotalCount();
			alerta = getWins().anyAlert(totalCountElement);
			if (alerta != null)
				setAlert(alerta); // warning referidos a los resultados, paso el
			// tamaño para no volver a calcularlo
		}



		void initializeGraphics() throws Exception {
			if (isWinListFullExportRequest && hasGraph())
				adg.startAnalize(oColumns, winList.getGraficosLista());
		}

		void generateGraphics(JWin oWin) throws Exception {
			adg.analizeRow(oColumns, oWin);
		}

		void determineFirstRow() throws Exception {
			// detect first Row
			if (iFromRow != -1) {
				boolean hasRow = true;
				while (hasRow && iRowCursor < iFromRow) {
					hasRow = getWins().nextRecord();
					iRowCursor++;
					if (supportOffset = getWins().isDatabaseSupportsOffset())
						break;
				}
				if (hasRow) {
					oFirstWin = getWins().getRecord();
				}
			} else {
				iRowCursor=1;
				getWins().firstRecord();
				if (getWins().nextRecord()) {
					oFirstWin = getWins().getRecord();
				}
			}
		}


		void generateHeader() throws Exception {
				headerToXML(getWins(), zContent, oFirstWin, isWinListFullExportRequest);
		}

		void generateRows() throws Exception {
			zContent.startNode("rows");

			zContent.setAttribute("zobject", winsObjectId);

			JWin oWin = oFirstWin;
			cortes=JCollectionFactory.createMap();

			if (iPageSize != -1)
				iToRow = iFromRow + getRealPageSize() - 1;
			else
				iToRow = -1;

			if (iFromRow != -1 && iFromRow > 0)
				goesBack = true;

			while (oWin != null) {
				if (isWinListFullExportRequest && hasGraph())
					generateGraphics(oWin);
				zContent.startNode("row");
//				cortecontrolToXML(zContent, oWin, cortes);
				rowToXML(zContent, oWin, iRowCursor, oldSelect, oldSingleSelect, isWinListFullExportRequest);
				zContent.endNode("row");
				if (iToRow != -1) {
					if (iRowCursor < iToRow) {
						if (getWins().nextRecord())
							oWin = getWins().getRecord();
						else
							oWin = null;
					} else {
						oWin = null;
						goesNext = true;// getWins().nextRecord();
					}
					iRowCursor++;
				} else {
					iRowCursor++;
					if (getWins().nextRecord())
						oWin = getWins().getRecord();
					else
						oWin = null;
				}
			}

			if (iPageSize == -1)
				goesNext = false;
			else if (totalCountElement != -1) {
				if (supportOffset) {
					if (iRowCursor + iFromRow < totalCountElement)
						goesNext = true;
				} else if (iRowCursor < totalCountElement)
					goesNext = true;
			} else {
				if (supportOffset)
					if (iRowCursor + iFromRow >= iToRow)
						goesNext = true;
					else if (iRowCursor >= iToRow)
						goesNext = true;

			}
			zContent.endNode("rows");

		}

		void generateFooter() throws Exception {
			if (isWinListFullExportRequest && hasGraph()) {
				adg.endAnalize(oColumns, winList.getGraficosLista());
				componentToXMLGraph(zContent);
				return; // para los graficos no me interesa totalizar
			}

			JTotalizer oTotal = winList.getTotalizer();
			if (oTotal != null)
				footToXML(zContent);

		}
		void generateNavigationBar() throws Exception {
			if (isWinListFullExportRequest)
				return;

			navigationBarToXML(zContent);
		}
	}


	public boolean calculeIsSelected(String id,String iOldSingleSelect,JWins iOldSelected,long iRowPos,JWin zWin) throws Exception {
		boolean selected = id.equals(iOldSingleSelect);
		if (!selected && this.isMultipleSelection() && iOldSelected != null && !this.isPreview())
			selected = iOldSelected.extract(zWin.getRecord());


		this.forceSelected = true;
		if (this.forceSelected && !this.isPreview() && iRowPos == 0 && iOldSingleSelect.equals("-1"))
			selected=true;	
		return selected;
	}
	


	@Override
	public void setEditable(boolean editable) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clear() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getSpecificValue() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDisplayValue() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setValue(String zVal) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setValueFromUIString(String zVal) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setController(JWebFormControl control) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onShow(String modo) throws Exception {
		// TODO Auto-generated method stub
		
	}

}