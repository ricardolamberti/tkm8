package pss.www.ui;

import java.awt.Dimension;
import java.util.Arrays;

import pss.common.security.BizUsuario;
import pss.core.services.fields.JObject;
import pss.core.tools.JExcepcion;
import pss.core.tools.JPair;
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
import pss.core.win.tools.orders.BizWinsColumn;
import pss.core.win.totalizer.JTotalizer;
import pss.core.winUI.controls.JFormLista;
import pss.core.winUI.lists.JColumnaLista;
import pss.core.winUI.lists.JGrupoColumnaLista;
import pss.core.winUI.lists.JWinList;
import pss.www.platform.actions.JWebAction;
import pss.www.platform.actions.JWebActionFactory;
import pss.www.platform.actions.JWebRequest;
import pss.www.platform.actions.JWebServerAction;
import pss.www.platform.actions.requestBundle.JWebActionData;
import pss.www.platform.content.generators.JXMLContent;
import pss.www.ui.controller.JWebFormControl;

public class JWebWinListResponsive extends JWebWinGenericResponsive implements JAbsolutelyNamedWebViewComponent, JWebControlInterface{

	//
	// CONSTRUCTOR
	//
	/*
	 * public JWebWinList(JWins zWins) throws Exception { this.oWins = zWins;
	 * this.ensureIsBuilt(); //JWebWinList(zWins, true); }
	 */
	public JWebWinListResponsive(BizAction action, boolean embedded) throws Exception {
		this(action, embedded, false);
	}
	public JWebWinListResponsive(BizAction action, String zCampo,JWin zWinCampo, Class zClassWins, boolean embedded) throws Exception {
		this(action,zCampo,zWinCampo,zClassWins,embedded,false);
	}
	public JWebWinListResponsive(BizAction action, boolean embedded, boolean formLov) throws Exception {
		this(action,null,null,null,embedded,false);
	}
	public JWebWinListResponsive(BizAction action, String zCampo,JWin zWinCampo, Class zClassWins, boolean embedded, boolean formLov) throws Exception {
		if (zCampo==null)
			initialize(action, embedded, formLov);
		else
			initialize(action, zCampo, zWinCampo, zClassWins, embedded, formLov);
	}
	public void initialize(BizAction action, boolean embedded, boolean formLov) throws Exception {
		this.sourceAction = action;
		this.setFormLov(formLov);
		this.setEmbedded(embedded);
	}
	
@Override
public String getClassResponsive() {
	// TODO Auto-generated method stub
	return super.getClassResponsive();
}

	public void initialize(BizAction action, String zCampo,JWin zWinCampo, Class zClassWins, boolean embedded, boolean formLov) throws Exception {
		this.sourceAction = action;
		this.campo = zCampo;
		this.winCampo = zWinCampo;
		this.classWinsCampo =zClassWins;


		// this.bWithFrame=withFrame;
		this.setEmbedded(embedded);
	}
	public String getPresentation() throws Exception {
		return JBaseWin.MODE_LIST;
	};

	public boolean isFalseInternalToolbar() {
		return false;
	}
	public JWebViewComposite getRootPanel() {
		return this.oRootPanel;
	}
	
	public static JWebWinListResponsive create(JWebViewComposite parent,  JFormLista control) throws Exception {
		BizAction pageAction = control.getAction();

		boolean hideChilds=parent.findJWebWinForm().getBaseForm().hideChilds();
		boolean isInForm=!(parent instanceof JWebTabPanelResponsive);
		JWebViewComposite realParent = parent;
		if (!isInForm) {
			JWebTabPanelResponsive tabs = (JWebTabPanelResponsive) parent;
			JWebTabResponsive tab = new JWebTabResponsive();
			tab.setTitle(pageAction.GetDescr());
			tab.setId(control.getIdControl());
	    if (parent!=null) {
				tab.setPreview(parent.findJWebWinForm().isPreview());
	    	parent.addChild(tab.getObjectName(), tab);
	    }
	    realParent=tab;
	    tabs.verifyTabActive();
	    if (control.isOnDemand() && !tabs.getActive().equals(tab.getId()))
	    	return null;
		} 
		if (hideChilds) return null;
		
		JWebWinListResponsive webList=new JWebWinListResponsive(hideChilds?null:control.getAction(),control.getCampo(),control.getWinCampo(),control.getWinClassCampo(), true);
		webList.takeAttributesFormControlResponsive(control);
		webList.setParentProvider(parent.findJWebWinForm());
		webList.setNotebookParent(pageAction.getIdAction());
		webList.setWinList(control.GetLista());
		webList.setUseContextMenu(control.getUseContextMenu());
		if (webList.campo==null) {
			webList.recoveryFiltersInHistory(); // para que tenga los filtros en las acciones con records estaticos (sin readAll)
			JActWins act = (JActWins) pageAction.getObjSubmit();
			if(act==null) JExcepcion.SendError("No existe la accion:"+pageAction.toString());
			webList.setMultipleSelection(act.isMultiple());
			webList.setLineSelect(act.isLineSelect());
		} else {
			webList.setMultipleSelection(false);
			webList.setLineSelect(true);
		}
		webList.setBackBotton(false);
		webList.setReadOnly(control.isReadOnly());
		webList.setToolbar(control.getToolbar());
		webList.setTitle(control.getTitle()); 
		webList.setEmbedded(true);
		webList.setPreviewFlag(control.getPreviewFlag());
		webList.setFilterBar(control.isFilterBar());
		webList.setDistributionObjects(control.getDistributionObjects());
		webList.setZoomtofit(control.getZoomtofit());
		webList.setListResponsive(control.isListaResponsive());
		webList.setMode(control.getMode());
		webList.setForceHidePaginationBar(control.isForceHidePaginationBar());
		webList.setNeedRefreshAllScreen(control.isNeedRefreshAllScreen());
		webList.setAllowSortedColumns(control.isAllowSortedColumns());
		webList.setInForm(isInForm);
		webList.setShowFooterBar(control.isShowFooterBar());
		webList.setbNavigationBar(control.hasNavigationBar());
		
		webList.ensureIsBuilt();
		webList.setName(pageAction.getIdAction());
		realParent.addChild(webList.getRootPanel().getName(), webList.getRootPanel());
		return webList;

	}
	
	public JWebWinActionBar getActionBar() throws Exception {
		return this.oActionBar;
	}
	
	public boolean hasActionBar() throws Exception {
		return this.oActionBar!=null;
	}

	@Override
	protected void build() throws Exception {

		if (this.winList == null) {
			this.winList = new JWinList(getWins());
//			this.winList.createListIfNotCreatedNoForm();
		}
		iFromRow = initializePagination();
		this.analizeScript();
		this.analizeSelectAll();
		this.analizeOrden();
		this.analizeHideShowFilter();
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
		if (hasWins()) {
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
		return "win_list";
	}

	/*
	 * protected void sizeToXML(JXMLContent zContent) throws Exception { if
	 * (this.oNavigationBar==null) { super.sizeToXML(zContent); } else { Dimension
	 * oSize = this.getSizeLazyly(); if (oSize.width >= 0) {
	 * zContent.setAttribute("width", oSize.width); } if (oSize.height >= 0) {
	 * zContent.setAttribute("height", oSize.height -
	 * this.oNavigationBar.getSize().height); } } }
	 */
	// protected JControlWin createControlWin() throws Exception {
	// return new JControlWin() {
	// public JWins getRecords() throws Exception {
	// return oWins;
	// };
	// };
	// }

	// protected void componentToXML(JXMLContent zContent) throws Exception {
	@Override
	protected void containerToXML(JXMLContent zContent) throws Exception {
	}

	

	// protected void clearUnusedActions() throws Exception {
	// this.oActionBar.clearUnusedActions();
	// }

	@Override
	protected void componentPreLayout(JXMLContent zContent) throws Exception {
		if (!hasWins()) {
			super.componentPreLayout(zContent);;
			return;
		}
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
		winList.setWins(getWins());
		this.addProviderHistory(zContent);
		if (!iXML.startDeclarations())
			return;

		iXML.readData();
		iXML.determineColumns();
		iXML.initializeGraphics();
		iXML.determineFirstRow();

		iXML.generateHeader();
		iXML.generateRows();
		
		iXML.generateFooter();
		iXML.generateNavigationBar();
		iXML.generateActionRows();
		if (hasActionBar())
			getActionBar().containerToXMLActions(zContent);
		// this.clearUnusedActions();
	}

	JGrupoColumnaLista[][] determineGroupoColumnsFromWinList(JWinList zWinList) throws Exception {
		JGrupoColumnaLista[][] columns = new JGrupoColumnaLista[10][zWinList.GetColumnasLista().size() + 1];
		JIterator<JColumnaLista> oColsIt = zWinList.GetColumnasLista().getIterator();
		int counter = 0;
		boolean any = false;
		JGrupoColumnaLista empty = new JGrupoColumnaLista("SINGRUPOCOLUMNA");
		JGrupoColumnaLista muyempty = new JGrupoColumnaLista("SINGRUPO");

		while (oColsIt.hasMoreElements()) {
			JColumnaLista col = oColsIt.nextElement();
			if (col.getGrupo() == null) {
				columns[0][counter++] = empty;
				continue;
			}
			any = true;
			columns[0][counter++] = col.getGrupo();
		}
		columns[0][counter] = null;
		if (!any) {
			columns[0][0] = muyempty;
			return columns;
		}
		Arrays.sort(columns[0], 0, counter);
		return determineGroupoColumnsFromWinList(columns, 0);
	}

	JGrupoColumnaLista[][] determineGroupoColumnsFromWinList(JGrupoColumnaLista[][] columns, int level) throws Exception {
		int counter = 0;
		boolean any = false;
		JGrupoColumnaLista empty = new JGrupoColumnaLista("SINGRUPOCOLUMNA");
		for (int c = 0; columns[level][c] != null; c++) {
			if (columns[level][c].getPadre() != null) {
				columns[level + 1][counter++] = columns[level][c].getPadre();
				any = true;
			} else {
				columns[level + 1][counter++] = empty;

			}
		}
		columns[level + 1][counter] = null;
		if (!any)
			return columns;
		Arrays.sort(columns[level + 1], 0, counter - 1);
		if (level + 1 > 9)
			return columns;

		return determineGroupoColumnsFromWinList(columns, level + 1);
	}

	JColumnaLista[] determineColumnsFromWinList(JWinList zWinList, JGrupoColumnaLista[][] oGrupoColumns, boolean onlyVisible) throws Exception {
		if (oGrupoColumns[0][0].getTitulo().equals("SINGRUPO")) {
			JColumnaLista[] columns = new JColumnaLista[zWinList.GetColumnasLista().size()];
			JIterator<JColumnaLista> oColsIt = zWinList.GetColumnasLista().getIterator();
			int counter = 0;
			while (oColsIt.hasMoreElements()) {
				JColumnaLista col = oColsIt.nextElement();
				if (!col.isVisible() && onlyVisible)
					continue;
				columns[counter] = col;
				counter++;
			}
			return columns;
		}
		JColumnaLista[] columns = new JColumnaLista[zWinList.GetColumnasLista().size()];

		JGrupoColumnaLista oldGCol = null;
		JGrupoColumnaLista oGCol = null;
		int counter = 0;
		for (int g = 0; oGrupoColumns[0][g] != null; g++) {
			oGCol = oGrupoColumns[0][g];
			if (oldGCol != null && oGCol.equals(oldGCol))
				continue;
			oldGCol = oGCol;

			JIterator<JColumnaLista> oColsIt = zWinList.GetColumnasLista().getIterator();
			while (oColsIt.hasMoreElements()) {
				JColumnaLista col = oColsIt.nextElement();
				if (!col.isVisible() && onlyVisible)
					continue;
				if (col.getGrupo() == null && g != 0)
					continue;// lo que no tenga hijo, en el grupo 0
				else if (col.getGrupo() != null && !col.getGrupo().equals(oGCol))
					continue;
				if (columns.length <= counter)
					break;
				columns[counter] = col;
				counter++;
			}
		}
		return columns;
	}



 	void cortecontrolToHeaderXML(JXMLContent zContent, JWin zWin) throws Exception {
		if (!hasCorteControl()) return;

		for (int l=0;l<zWin.getCorteControlCantLevels();l++) {
			zContent.startNode("column");
			zContent.setAttribute("col_name", "l"+(l+1));
			zContent.addTextNode("title",  "l"+(l+1)); 
			zContent.endNode("column");
		}
	}

 	void cortecontrolToXML(JXMLContent zContent, JWin zWin, JMap<Long,String> cortes) throws Exception {
		if (!hasCorteControl()) return;
		for (int l=0;l<zWin.getCorteControlCantLevels();l++) {
		  String marcaLevel = zWin.getCorteControlID(l);
			String marcaAnt=cortes.getElement((long)l);
			if (marcaAnt==null) {
				cortes.addElement((long)l, marcaLevel);
			} else {
				if (marcaAnt.equals(marcaLevel)) {
					zContent.startNode("l");
					zContent.setAttribute("level", l);
					zContent.endNode("l");
				} else {
					cortes.addElement((long)l, marcaLevel);
					zContent.startNode("l");
					zContent.setAttribute("level", l);
					zContent.endNode("l");
					
//					if ()
				
				
				}
			}
		}
	}
 	
	


	protected void footToXML(JXMLContent zContent, JTotalizer zTot, JColumnaLista[] oColumns) throws Exception {
		zContent.startNode("footer");
		if (this.getWins().hasSubDetail(false)) {
			zContent.startNode("column");
			zContent.addTextNode("halignment", "center");
			zContent.addTextNode("value", "");
			zContent.endNode("column");
		}
		JColumnaLista oCol;
		for (int i = 0; i < oColumns.length; i++) {
			oCol = oColumns[i];
			if (oCol==null) continue;
			if (oCol.hasIdAction()) {
				zContent.startNode("column");
				this.winActionFooterToXML(zContent, oCol, this.getWins().getWinRef());
				zContent.endNode("column");
				continue;
			}
			String sColName = oCol.GetCampo();
			JTotalizer.Properties oProp = zTot.getProp(sColName);
			if (oProp == null) {
				zContent.startNode("column");
				zContent.addTextNode("halignment", "center");
				zContent.addTextNode("value", "");
				zContent.endNode("column");
				continue;
			}
			zContent.startNode("column");
			zContent.setAttribute("halignment", this.getColumnAlignment(oProp.hasAlignment()?oProp.getAlignment():oCol.getAlignment()));

			
			if (oCol.GetTipo()!=null && (oCol.GetTipo().equalsIgnoreCase("VIRTUAL") ||oCol.GetTipo().equalsIgnoreCase(JObject.JDATE) ||oCol.GetTipo().equalsIgnoreCase(JObject.JDATETIME)))
				zContent.addTextNode("value", "");
			else {

				if (oProp.getValue().hasHtml()) {
					String val=oProp.getValue().toHtml();
					zContent.startNode("html_data");
					zContent.setNodeText(val);
					zContent.endNode("html_data");
				} 
				zContent.addTextNode("value", oProp.getValue().toRawFormattedString());
			}
			zContent.endNode("column");
		}
		zContent.endNode("footer");
	}

	protected boolean hasGraph() throws Exception {
		return winList.getGraficosLista().size() > 0;
	}
 //
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

	private void rowToXML(JXMLContent zContent, JWin zWin, JColumnaLista[] aColumns, boolean zWithIcon, int iRowPos, JWins iOldSelected, String iOldSingleSelect, boolean firstLevel, boolean isWinListFullExportRequest) throws Exception {
		zContent.setAttribute("rowpos", iRowPos);
		if (isWinListFullExportRequest && hasMap()) {
			zContent.setAttribute("mapdir", zWin.getDireccionMapa());
			zContent.setAttribute("mapname", JTools.replace(zWin.getNombreMapa(), "\"", " "));
		}
		String id=null;
		// represent the action bar actions for the given Win
		if (this.hasActionBar() && firstLevel) {
			zWin.getRecord().keysToFilters();

//			String forcedPos = zWin.getUniqueId();//this.getProviderName() + "." + this.getWins().getClass().getSimpleName() + "_" + iRowPos;
			id = zContent.addObject(zWin.getUniqueId(), zWin);
			this.rowToXMLActionBar(zContent, zWin, id);
			
		  boolean selected = this.calculeIsSelected(id, iOldSingleSelect, iOldSelected, iRowPos, zWin);

			zContent.setAttribute("selected", selected);
		}
		String title = zWin.getFieldTooltip(null);
		if (title != null)
			zContent.setAttribute("tooltip", title);

		if (isLineSelect())
			this.addDrop(zContent, "data_" + iRowPos, zWin);

		// each data cell represented as a <d> element
		JColumnaLista oCol;
		for (int i = 0; i < aColumns.length; i++) {
			oCol = aColumns[i];
			if (isWinListFullExportRequest && (oCol.IfIcono() || oCol.IfAction()) && i == 0) continue;// el icono inicial en la exportacion no va
			this.fieldToXML(zContent, zWin, oCol, i,id, iRowPos);
		}

		this.addRightClick(zContent);
		this.addDoubleClick(zContent, zWin);
		this.addDrag(zContent, zWin);
		this.addSimpleClick(zContent, zWin);
	}
	
	private void addDoubleClick(JXMLContent zContent, JWin win) throws Exception {
		if (this.isReadOnly()) return;
		JWebAction actDblClick = this.getActionDefault(win);
		if (addActionsBase("dblclick",actDblClick)== null) return;
		zContent.startNode("dblclick");
		actDblClick.toXML(zContent);
		zContent.endNode("dblclick");
	}
	
	private void addRightClick(JXMLContent zContent) throws Exception {
		if (this.isReadOnly()) return;
		zContent.startNode("rightclick");
		zContent.setAttribute("obj_provider", this.getProviderName());
		zContent.endNode("rightclick");
	}

	private void addSimpleClick(JXMLContent zContent, JWin win) throws Exception {
		if (this.isReadOnly()) return;
		if (!this.hasPreviewPanel()) return; 
		if(this.getWins().getForceActionPreview()!=0) return;
		JWebAction actSmplClick = this.getActionSimpleClick(win);
		if (addActionsBase("smplclick",actSmplClick)== null) return;
		zContent.startNode("smplclick");
		actSmplClick.toXML(zContent);
		zContent.endNode("smplclick");
	}

	private void addDrag(JXMLContent zContent, JWin win) throws Exception {
		if (this.isReadOnly()) return;
		JWebAction actDrag = this.getActionDrop(win);
		if (actDrag == null) return;
		zContent.startNode("drop");
		actDrag.toXML(zContent);
		zContent.endNode("drop");
	}



	private void rowDetailToXML(JXMLContent zContent, JWins details, int colspan, int iRowPos, boolean isWinListFullExportRequest) throws Exception {
		if (details == null) return;
		
		JWinList oWinList = new JWinList(details); // WinList fantasma para
		details.setForceHideActions(true);
		// poder obtener las
		// columnas de details
		details.ConfigurarColumnasListaInternal(oWinList);
	
		details.readAll();
		if (!details.nextRecord())
			return;
		JWin oFirstWin = details.getRecord();

		JGrupoColumnaLista[][] oGrupoColumns = determineGroupoColumnsFromWinList(oWinList);
		JColumnaLista[] oColumns = determineColumnsFromWinList(oWinList, oGrupoColumns, isWinListFullExportRequest ? false : true);

		zContent.startNode("details");
		zContent.setAttribute("rowpos", iRowPos);
		zContent.setAttribute("colspan", colspan);
		zContent.setAttribute("class_table_responsive", getClassTableResponsive());

		boolean bWithIcon = false;
		if (!oWinList.isWithHeader()) {
			zContent.setAttribute("display", "none"); 
		} else {
			bWithIcon = headerToXML(details, zContent, oFirstWin, oGrupoColumns, oColumns, isWinListFullExportRequest);
			JWebIcon.getSkinIcon(BizUsuario.retrieveSkinGenerator().getMoreSubDetail()).toXML(zContent);
		}
		
		zContent.startNode("rows");

		JWin oWin = oFirstWin;
		int iRowCursor = 0;

		while (oWin != null) {
			if (oWin.hasToFilterRecordInGrid()) {
				zContent.startNode("row");
				rowToXML(zContent, oWin, oColumns, bWithIcon, iRowCursor++, null, "-1", false, false);
				zContent.endNode("row");
			}
			if (!details.nextRecord())
				break;
			oWin = details.getRecord();
		}
		zContent.endNode("rows");
		zContent.endNode("details");

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
		if (action==null||getWins().isModeWinLov())
			action = zWin.findAction(JWin.ACTION_DROP);
		if (action == null)
			action = zWin.findAction(JWin.ACTION_QUERY);
		if (action == null)
			return null;
		// return
		// (this.actionDefault=JWebActionFactory.createViewAreaAction(action,
		// this, false, null));
		return JWebActionFactory.createViewAreaAndTitleAction(action, this, false, null, null, isModal());
	}

	private BizAction findActionPreview(JWin zWin) throws Exception {

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

	protected JWebAction getActionSimpleClick(JWin zWin) throws Exception {
		BizAction action = this.findActionPreview(zWin);
		if (action == null)
			return null;
		JWebServerAction wa = null;
		if (this.hasPreview()) {
			wa = JWebActionFactory.createPreviewAreaAction(action, this, null);
			JWebActionData nav = wa.getNavigationData();
			nav.add("embedded", "" + this.isEmbedded());
			nav.add("is_preview", ""+true);
			nav.add("toolbar", JWins.TOOLBAR_TOP);
			nav.add("container_preview", getPreviewPanelName());

		} else
			wa = JWebActionFactory.createViewAreaAndTitleAction(action, this, false, null);
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
		if (getWins().getToolbarForced()!=null)
			nav.add("toolbar", ""+getWins().getToolbarForced());
		nav.add("container_preview", getPreviewPanelName());
		return wa;
	}

	protected int getRowSpan(String zColumn, String zValue) {
		return 0;
	}

	protected boolean shouldSkip(String zColumn, String zValue) {
		return false;
	}
/*
	protected void navigationBarToXML(JXMLContent zContent, int iRowCursor, boolean goesNext, boolean goesBack, String orden, String dirOrden, int zFromRow, int zToRow) throws Exception {
		if (this.oNavigationBar == null)
			return;
		String selectInfo = "";
		long sizeSel = 0;
		if (isMultipleSelection()) {
			JWins olds = this.getOldSelect(zContent);
			if (olds != null) {
				sizeSel = olds.getWinsList().size();
				selectInfo = "(Selecc." + sizeSel + " filas en otras paginas) ";
			}
		}
		zContent.startNode("select_info");
		zContent.setAttribute("has_more_selection", sizeSel == 0 ? "0" : "1");
		zContent.setAttribute("info_more_selection", selectInfo);
		zContent.endNode("select_info");
		// int ancho = 0;
		JWebLabel oNavInfo = new JWebLabel();
		oNavInfo.setClassResponsive("dataTables_info");
		this.oInfoBar.add("nav_info_lbl", oNavInfo);
		// this.oNavigationBar.resolvePadding();
//		if (goesBack)
//			this.attchGoBack(zFromRow, orden, dirOrden);
//		if (goesNext)
//			this.attchGoNext(zToRow, orden, dirOrden);
//		this.attchSelectPage(getWins().getWebPageSize());

		if (this.hasHideShowFilterOption()) {
			JWebLink oShowFilter = new JWebLink();
			oShowFilter.setOpensNewWindow(true);
			oShowFilter.setWebAction(JWebActionFactory.createWinListHideShowFilter(this, this.getSourceAction(), this.sourceObjectId, orden, dirOrden, sAll, zFromRow, this.iPageSize));
			oShowFilter.setSkinStereotype("win_list_navigation_bar_link");
			this.oNavigationBar.add("show_filter", oShowFilter);
			oShowFilter.setIcon(JWebIcon.getPssIcon(BizUsuario.retrieveSkinGenerator().getSizeIconNavigationBar(), isShowFilterBar() ? 1120 : 1121));
			oShowFilter.setSize(GuiIconos.sizeToDimension(BizUsuario.retrieveSkinGenerator().getSizeIconNavigationBar()));
		}

		if (this.hasPreview()) {
			JWebLink expand = new JWebLink();
			expand.setWebAction(JWebActionFactory.createWinListWithPreview(this, this.getSourceAction(), this.sourceObjectId, orden, dirOrden, sAll, zFromRow, this.iPageSize));
			expand.setSkinStereotype("win_list_navigation_bar_link");
			this.oNavigationBar.add("expand", expand);
			expand.setIcon(JWebIcon.getPssIcon(BizUsuario.retrieveSkinGenerator().getSizeIconNavigationBar(), isPreviewFlagSi() ? 1118 : 1119));
			expand.setSize(GuiIconos.sizeToDimension(BizUsuario.retrieveSkinGenerator().getSizeIconNavigationBar()));
		}
		this.addButtonExportToMap(oNavigationBar);
		this.addButtonExportToGraph(oNavigationBar);
		this.addButtonExportToExcel(oNavigationBar);
		this.addButtonExportToCSV(oNavigationBar);
		this.addButtonExportToReport(oNavigationBar);


		if (this.isAllowExportToMap()) {
			JWebLink oToMap = new JWebLink();
			oToMap.setOpensNewWindow(true);
			oToMap.setWebAction(JWebActionFactory.createWinListExportAllToMap(this.getSourceAction(), this, this.sourceObjectId));
			oToMap.setSkinStereotype("win_list_navigation_bar_link");
			this.oNavigationBar.add("export_to_map", oToMap);
			oToMap.setIcon(JWebIcon.getPssIcon(BizUsuario.retrieveSkinGenerator().getSizeIconNavigationBar(), 14));
			oToMap.setSize(GuiIconos.sizeToDimension(BizUsuario.retrieveSkinGenerator().getSizeIconNavigationBar()));
			// ancho+=oToMap.getSizeLazyly().width;
		}

		if (this.isAllowExportToGraph()) {
			JWebLink oToGraph = new JWebLink();
			oToGraph.setOpensNewWindow(true);
			oToGraph.setWebAction(JWebActionFactory.createWinListExportAllToGraph(this.getSourceAction(), this, this.sourceObjectId));
			oToGraph.setSkinStereotype("win_list_navigation_bar_link");
			this.oNavigationBar.add("export_to_graph", oToGraph);
			oToGraph.setIcon(JWebIcon.getPssIcon(BizUsuario.retrieveSkinGenerator().getSizeIconNavigationBar(), 10029));
			oToGraph.setSize(GuiIconos.sizeToDimension(BizUsuario.retrieveSkinGenerator().getSizeIconNavigationBar()));
			// ancho+=oToGraph.getSizeLazyly().width;
		}
		if (this.isAllowExportToExcel()) {
			JWebLink oToExcel = new JWebLink();
			oToExcel.setOpensNewWindow(true);
			oToExcel.setWebAction(JWebActionFactory.createWinListExportAllToExcel(this.getSourceAction(), this, this.sourceObjectId, JBaseWin.MODE_LIST));
			oToExcel.setSkinStereotype("win_list_navigation_bar_link");
			this.oNavigationBar.add("export_to_excel", oToExcel);
			oToExcel.setIcon(JWebIcon.getPssIcon(BizUsuario.retrieveSkinGenerator().getSizeIconNavigationBar(), 10030));
			oToExcel.setSize(GuiIconos.sizeToDimension(BizUsuario.retrieveSkinGenerator().getSizeIconNavigationBar()));
			// ancho+=oToExcel.getSizeLazyly().width;
		}
		if (this.isAllowExportToCSV()) {
			JWebLink oToCSV = new JWebLink();
			oToCSV.setOpensNewWindow(true);
			oToCSV.setWebAction(JWebActionFactory.createWinListExportAllToCSV(this.getSourceAction(), this, this.sourceObjectId, JBaseWin.MODE_LIST));
			oToCSV.setSkinStereotype("win_list_navigation_bar_link");
			this.oNavigationBar.add("export_to_csv", oToCSV);
			oToCSV.setIcon(JWebIcon.getPssIcon(BizUsuario.retrieveSkinGenerator().getSizeIconNavigationBar(), 10028));
			oToCSV.setSize(GuiIconos.sizeToDimension(BizUsuario.retrieveSkinGenerator().getSizeIconNavigationBar()));
			// ancho+=oToCSV.getSizeLazyly().width;
		}
		if (this.isAllowExportToReport()) {
			JWebLink oToReport = new JWebLink();
			oToReport.setOpensNewWindow(true);
			oToReport.setWebAction(JWebActionFactory.createWinListExportAllToReport(this.getSourceAction(), this, this.sourceObjectId, JBaseWin.MODE_LIST));
			oToReport.setSkinStereotype("win_list_navigation_bar_link");
			this.oNavigationBar.add("export_to_report", oToReport);
			oToReport.setIcon(JWebIcon.getPssIcon(BizUsuario.retrieveSkinGenerator().getSizeIconNavigationBar(), 10000));
			oToReport.setSize(GuiIconos.sizeToDimension(BizUsuario.retrieveSkinGenerator().getSizeIconNavigationBar()));
			// ancho+=oToReport.getSizeLazyly().width;
		}
		

//		if (iRowCursor != -1) {
//			if (goesBack || goesNext) {
//				long count;
//				try {
//					count = calculateTotalCount();
//					if (count == -1)
//						oNavInfo.setLabel(selectInfo + " Mostrando registros del {" + (zFromRow + 1) + "} a {" + (zFromRow + this.iPageSize) + "}");
//					else if (count < (zFromRow + this.iPageSize))
//						oNavInfo.setLabel(selectInfo + "Mostrando registros del {" + (zFromRow + 1) + "} al {" + count + "}");
//					else
//						oNavInfo.setLabel(selectInfo + "Mostrando registros del {" + (zFromRow + 1) + "} a {" + (zFromRow + this.iPageSize) + "} de {" + count + "}");
//				} catch (Exception e) {
//					PssLogger.logError(e);
//					count = 0;
//					oNavInfo.setLabel(selectInfo + "Mostrando registros del {" + (zFromRow + 1) + "} a {" + (zFromRow + this.iPageSize) + "}");
//				}
//
//			} else {
//				oNavInfo.setLabel(selectInfo + "Mostrando todos los registros: {" + iRowCursor + "}");
//			}
//		}
		// oNavInfo.setSize(this.oNavigationBar.getSize().width - iButtonsWidth,
		// iButtonsHeight); oNavInfo.setSize(this.oNavigationBar.getSize().width
		// - (ancho+200), this.oNavigationBar.getSize().height); //
		// this.oNavigationBar.toXML(zContent); }
		// oNavInfo.setSize(this.oNavigationBar.getSize().width - iButtonsWidth,
		// iButtonsHeight);
		// oNavInfo.setSize(this.oNavigationBar.getSize().width - ancho,
		// this.oNavigationBar.getSize().height);
		// this.doLayout();
		// this.oNavigationBar.toXML(zContent);

	}
*/


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

	// public boolean hasPreview() throws Exception {
	// // necesito una maner de saber que estoy siendo generado en el contexto
	// de un preview
	// return sourceAction!=null &&
	// sourceAction.hasOwner()&&sourceAction.hasRefreshAction();
	// }

	public boolean isAllowExportToGraph() throws Exception {
		return this.hasGraph();
	}

//	public boolean isAllowExportToExcel() {
//		return this.bAllowExportToExcel;
//	}

	public void setAllowExportToExcel(boolean b) {
		this.bAllowExportToExcel = b;
	}

//	public boolean isAllowExportToCSV() {
//		return this.bAllowExportToCSV;
//	}

	public boolean isAllowExportToReport() {
		return this.bAllowExportToReport;
	}

	public void setAllowExportToCSV(boolean b) {
		this.bAllowExportToCSV = b;
	}

	public void setAllowExportToReport(boolean b) {
		this.bAllowExportToReport = b;
	}

	// public String getRegisteredObjectId() {
	// return winsObjectId;
	// }
	// public String getProviderName() throws Exception {
	// return this.getName();
	// }
	public JWebAction getWebSourceAction() throws Exception {
		JWebServerAction a = JWebActionFactory.createViewAreaAndTitleAction(this.sourceAction, this, true, null);
		JWebActionData nav = a.addData("win_list_nav");
		nav.add("with_preview", "" + this.findPreviewFlag());
		return a;
	}

	// public String getSourceObjectId() {
	// return this.sourceObjectId;
	// }
	// public String getWinObjectId() {
	// return this.winObjectId;
	// }

	// public boolean isOnlyWinListTablePanel() {
	// return bOnlyWinListTablePanel;
	// }
	//
	// public void setOnlyWinListTablePanel(boolean onlyWinListTablePanel) {
	// bOnlyWinListTablePanel = onlyWinListTablePanel;
	// }

	@Override
	synchronized public void ensureIsBuilt() throws Exception {
		super.ensureIsBuilt();
	}

	// public void attachWinOwnerActions() throws Exception {
	// if (this.sourceAction==null) return;
	// if (!this.getWins().delgateActionsToOwner()) return;
	// this.oActionBar.addActionsFor(this.getWins(), this.sourceObjectId, true);
	// }

	// public JWebAction createRefreshAction() throws Exception {
	// return this.oActionBar.createWebRefreshAction();
	// }

	// public void setToolbarTop(boolean value) {
	// this.bToolbarTop = value;
	// }
	// public void setEmebedded(boolean value) {
	// this.bEmbedded = value;
	// }
	// public void setWithFrame(boolean value) {
	// this.bWithFrame = value;
	// }

	public String getScroll(JXMLContent zContent) throws Exception {
		JWebRequest req = zContent.getGenerator().getRequest();
		return req.getSession().getHistoryManager().findLastScroll(this.sourceAction);
	}

	public JWins getOldSelect(JXMLContent zContent) throws Exception {
		JWebRequest req = zContent.getGenerator().getRequest();
		return req.getSession().getHistoryManager().findLastMultipleSelect(this.sourceAction);
	}

	public String getOldSingleSelect(JXMLContent zContent) throws Exception {
		JWebRequest req = zContent.getGenerator().getRequest();
		return req.getSession().getHistoryManager().findLastSelectItem(this.sourceAction);
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

	@Override
	public boolean hasPreview() throws Exception {
		if (!this.hasWins()) return false;
		boolean hasPreview =super.hasPreview();
		if (!hasPreview) 
			return false;
		return  (findActionPreview(getWins().getWinRefWithActions()))!=null;
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
		JWebWinListResponsive oWebWinList;
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
			zContent.setAttribute("use_context_menu", isUseContextMenu());
			zContent.setAttribute("scroll", getScroll(zContent));
			zContent.setAttribute("pageWidth", getWidthPage());
			zContent.setAttribute("pageHeight", getHeigthPage());
			zContent.setAttribute("class_table_responsive", getClassTableResponsive());
			zContent.setAttribute("class_table_responsive", getClassTableResponsive());
			zContent.setAttribute("modifiedonserver", bIsModifiedOnServer);
			zContent.setAttribute("allowSortedColumns", isAllowSortedColumns());
			if (isZoomtofit()) {
				zContent.setAttribute("zoomtofit", "zoom_"+getName());
				zContent.setAttribute("zoomtofit_width", getZoomtofit());
			}		
			return !JWebActionFactory.isOtherObjectExportRequest(this.request(), oWebWinList);
		}
		
		private JWebRequest request() throws Exception {
			return zContent.getGenerator().getRequest();
		}

		protected boolean isForceRealAll() throws Exception {
			return JWebActionFactory.isWinListFullExportRequest(this.request(), oWebWinList);
		}

		protected void readData() throws Exception {
			getWins().setPagesize(getRealPageSize());
			getWins().setOffset(iFromRow);
	

			if (isGenerateInternalEvents())
				BizUsuario.eventInterfaz(this.getClass().getCanonicalName(), "Consultando la base", 0, getRealPageSize(), false,null);
			
			getWins().ConfigurarColumnasListaInternal(winList);
			zContent.setAttribute("title", getWins().GetTitle());
			applyOrden();

			
			String alerta = getWins().anyAlert(-1);
			if (alerta != null)
				setAlert(alerta); // Warning referidos a los filtros
			if (!getWins().canReadAll(sourceAction) && !this.isForceRealAll()) {
				alerta = getWins().anyAlert(-1);
				if (alerta != null)
					setAlert(alerta); // Warning referidos al read all
				return;
			}
			if (isWinListFullExportRequest && hasGraph()) {
				getWins().preGraphicRead();
			}
			if (getWins().GetVision() != null && getWins().GetVision().equals("PREVIEW"))
				getWins().setPagesize(10);

			getWins().ReRead();
			calculateTotalCount();
			alerta = getWins().anyAlert(totalCountElement);
			if (alerta != null)
				setAlert(alerta); // warning referidos a los resultados, paso el tamaño para no volver a calcularlo
		}

		void applyOrden() throws Exception {
			if (advanceColumnOrden==null) {
				if (columnOrden != null && !columnOrden.equals("")) {
					getWins().getRecords().clearOrderBy();
					getWins().addOrderByFromUI(columnOrden, (direccionOrden == null || direccionOrden.equals("")) ? "asc" : direccionOrden);
				}
			}
			else {
				if (advanceColumnOrden!=null) {
					getWins().getRecords().clearOrderBy();
					for (JPair<String,String> pair:advanceColumnOrden) {
						getWins().addOrderByFromUI(pair.fisrtObject, pair.secondObject);
						
					}
				}
			}
		}

		void determineColumns() throws Exception {
			
			oGrupoColumns = determineGroupoColumnsFromWinList(winList);
			oColumns = determineColumnsFromWinList(winList, oGrupoColumns, isForceRealAll() ? false : true);
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
				boolean hasRow = !getWins().isEOF();
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
			if (getWins().getForceActionPreview() != 0 && oWebWinList.hasPreviewPanel()) {
				JWebAction actSmplClick = getActionShowAction(zContent);
				if (actSmplClick != null) {
					zContent.startNode("showAction");
					actSmplClick.toXML(zContent);
					zContent.endNode("showAction");
				}
			}
			if (!winList.isWithHeader()) {
//				zContent.setAttribute("display", "none"); 
				return ;
			}
			bWithIcon = headerToXML(getWins(), zContent, oFirstWin, oGrupoColumns, oColumns, isWinListFullExportRequest);
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
				boolean showed=false;
				if (!oWin.isHide()) {
					showed=true;		
					if (isWinListFullExportRequest && hasGraph())
						generateGraphics(oWin);
					zContent.startNode("row");
//				cortecontrolToXML(zContent, oWin, cortes);
					rowToXML(zContent, oWin, oColumns, bWithIcon, iRowCursor, oldSelect, oldSingleSelect, true, isWinListFullExportRequest);
					if (isGenerateInternalEvents())
							BizUsuario.eventInterfaz(this.getClass().getCanonicalName(), "Leyendo registro "+iRowCursor, iRowCursor , iPageSize, false,null);
			
					JWins details = getWins().getSubDetails(oWin, isWinListFullExportRequest);
					rowDetailToXML(zContent, details, oColumns.length+1, iRowCursor, isWinListFullExportRequest);
					
					zContent.endNode("row");
				}
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
					if (showed) iRowCursor++;
				} else {
					if (showed) iRowCursor++;
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
			generateExtraRows();

			zContent.endNode("rows");
			if (isGenerateInternalEvents())
				BizUsuario.eventInterfaz(this.getClass().getCanonicalName(), "Generando salida", iPageSize , iPageSize+1, false,null);

		}
		
		void generateExtraRows() throws Exception {
			if (iRowCursor>getMinLineas()) return;
			if (isWinListFullExportRequest) return;
			JWin oWin = getWins().getWinRef();
			long lastPos=iRowCursor;
			while (lastPos<getMinLineas()) {
					zContent.startNode("row");
					zContent.setAttribute("rowpos", lastPos);

					// each data cell represented as a <d> element
					JColumnaLista oCol;
					for (int i = 0; i < oColumns.length; i++) {
						oCol = oColumns[i];
						if (oCol==null) continue;
						voidCellToXML(zContent, oWin, oCol, i,"");
					}
					zContent.endNode("row");
				lastPos++;
			}


		}

		void generateFooter() throws Exception {
			if (hasPosFunction()) {
				zContent.startNode("posfunction");
				zContent.setAttribute("script", getPosFunction());
				zContent.endNode("posfunction");
			}

			if (!isShowFooterBar()) return;
			if (isWinListFullExportRequest && hasGraph()) {
				adg.endAnalize(oColumns, winList.getGraficosLista());
				componentToXMLGraph(zContent);
				return; // para los graficos no me interesa totalizar
			}

			JTotalizer oTotal = winList.getTotalizer();
			if (oTotal!=null || winList.isWithFooter())
				footToXML(zContent, oTotal, oColumns);

		}
		void generateNavigationBar() throws Exception {
			if (isWinListFullExportRequest)
				return;
			if (!isNavigationBar())
				return;

			navigationBarToXML(zContent);
		}
		
		void generateActionRows() throws Exception {
			if (isWinListFullExportRequest)
				return;

			generateActionRowsToXML(zContent);
		}
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
