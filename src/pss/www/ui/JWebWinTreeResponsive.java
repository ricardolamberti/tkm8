package pss.www.ui;

import java.awt.Dimension;
import java.util.Arrays;
import java.util.StringTokenizer;

import pss.common.security.BizUsuario;
import pss.core.graph.Graph;
import pss.core.graph.TrendLines;
import pss.core.graph.analize.Categories;
import pss.core.graph.analize.Dataset;
import pss.core.graph.analize.Value;
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
import pss.core.winUI.lists.JColumnaLista;
import pss.core.winUI.lists.JGrupoColumnaLista;
import pss.core.winUI.lists.JWinList;
import pss.core.winUI.responsiveControls.JFormTreeResponsive;
import pss.www.platform.actions.JWebAction;
import pss.www.platform.actions.JWebActionFactory;
import pss.www.platform.actions.JWebRequest;
import pss.www.platform.actions.JWebServerAction;
import pss.www.platform.actions.requestBundle.JWebActionData;
import pss.www.platform.applications.JHistoryProvider;
import pss.www.platform.content.generators.JXMLContent;
import pss.www.ui.controller.JWebFormControl;

public class JWebWinTreeResponsive  extends JWebWinGenericResponsive implements JAbsolutelyNamedWebViewComponent, JWebControlInterface{

	String expanderTemplate="<span class=\"treetable-expander\"></span>";
	String indentTemplate="<span class=\"treetable-indent\"></span>";
	String expanderExpandedClass="fa fa-angle-down";//"fa fa-folder-open"
	String expanderCollapsedClass="fa fa-angle-right";//"fa fa-folder"
	long treeColumn=0;
	String initStatusClass="treetable-collapsed";
	
	public String getExpanderTemplate() {
		return expanderTemplate;
	}

	public void setExpanderTemplate(String expanderTemplate) {
		this.expanderTemplate = expanderTemplate;
	}

	public String getIndentTemplate() {
		return indentTemplate;
	}

	public void setIndentTemplate(String indentTemplate) {
		this.indentTemplate = indentTemplate;
	}

	public String getExpanderExpandedClass() {
		return expanderExpandedClass;
	}

	public void setExpanderExpandedClass(String expanderExpandedClass) {
		this.expanderExpandedClass = expanderExpandedClass;
	}

	public String getExpanderCollapsedClass() {
		return expanderCollapsedClass;
	}

	public void setExpanderCollapsedClass(String expanderCollapsedClass) {
		this.expanderCollapsedClass = expanderCollapsedClass;
	}

	public String getPresentation() throws Exception {
		return JBaseWin.MODE_TREE;
	};

	public long getTreeColumn() {
		return treeColumn;
	}

	public void setTreeColumn(long treeColumn) {
		this.treeColumn = treeColumn;
	}

	public String getInitStatusClass() {
		return initStatusClass;
	}

	public void setInitStatusClass(String initStatusClass) {
		this.initStatusClass = initStatusClass;
	}

	//
	// CONSTRUCTOR
	//
	public JWebWinTreeResponsive(BizAction action,  boolean embedded) throws Exception {
		this(action, embedded, false);
	}

	public JWebWinTreeResponsive(BizAction action, boolean embedded, boolean formLov) throws Exception {
		this.sourceAction = action;
		this.setFormLov(formLov);
		this.setEmbedded(embedded);
	}

	public JWebViewComposite getRootPanel() {
		return this.oRootPanel;
	}


	public static JWebWinTreeResponsive create(JWebViewComposite parent,  JFormTreeResponsive control) throws Exception {
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
		JWebWinTreeResponsive webList=new JWebWinTreeResponsive(control.getAction(), true);
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
		webList.setLabelLateral(control.getLabel());
		webList.setPreviewFlag(control.getPreviewFlag());
		webList.setDistributionObjects(control.getDistributionObjects());
		webList.setZoomtofit(control.getZoomtofit());
		webList.setMode(control.getMode());
		webList.setExpanderTemplate(control.getExpanderTemplate());
		webList.setIndentTemplate(control.getIndentTemplate());
		webList.setExpanderExpandedClass(control.getExpanderExpandedClass());
		webList.setExpanderCollapsedClass(control.getExpanderCollapsedClass());
		webList.setTreeColumn(control.getTreeColumn());
		webList.setInitStatusClass(control.getInitStatusClass());
		webList.setNeedRefreshAllScreen(control.isNeedRefreshAllScreen());
		webList.ensureIsBuilt();
		webList.setName(pageAction.getIdAction());
		realParent.addChild(webList.getRootPanel().getObjectName(), webList.getRootPanel());
		return webList;

	}
	
	public JWebWinActionBar getActionBar() throws Exception {
		return this.oActionBar;
	}

	@Override
	protected void build() throws Exception {

		if (this.winList == null) {
			this.winList = new JWinList(getWins());
		}
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
		return "tree_responsive";
	}
	JMap<Long,Long> getPaginas() throws Exception {
		JMap<Long,Long> pags= JCollectionFactory.createMap();
		if (paginado==null) return pags;
		long p=1;
		StringTokenizer toks = new StringTokenizer(paginado, ",");
		while (toks.hasMoreTokens()) {
			String tok = toks.nextToken();
			pags.addElement(p, Long.parseLong(tok));
			p++;
		}
		return pags;
	}
	protected int initializePagination() throws Exception {

		JWebRequest req =  JWebActionFactory.getCurrentRequest();
		if (!hasPagination() || JWebActionFactory.isWinListFullExportRequest(req, this)) {
			iPageSize = -1;
			return -1;
		}	

		int iFromRow = -1;
		JWebActionData nav = recoveryNavigationMap();

		if (nav != null) {
			iPageSize = recoveryPageSize(nav);
			iPaginaActual = Integer.parseInt(nav.get("pagina_actual"));
			if (iPaginaActual==-1) iPaginaActual=0;
			paginado = nav.get("paginado");
			JHistoryProvider p = req.getSession().getHistoryManager().getLastHistory().findProvider(sourceAction);
			if (p != null)
				p.setNavigation(nav);
		} else {
			iPageSize = getWins().getWebPageSize();
			if (iPageSize == -1)
				iPageSize =  getDefaultPageSize();
			paginado="0";
			iPaginaActual = 1;
			JHistoryProvider p = req.getSession().getHistoryManager().getLastHistory().findProvider(sourceAction);
			if (p != null)
				p.setNavigation(nav);
		}
		Long pos = getPaginas().getElement(new Long(iPaginaActual));
		if (pos != null)
			iFromRow = pos.intValue();
		else
			iFromRow = 0;

		return iFromRow;
		
	}

	// protected void componentToXML(JXMLContent zContent) throws Exception {
	@Override
	protected void containerToXML(JXMLContent zContent) throws Exception {
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

	// protected void clearUnusedActions() throws Exception {
	// this.oActionBar.clearUnusedActions();
	// }

	@Override
	protected void componentPreLayout(JXMLContent zContent) throws Exception {
		if (!hasWins())
			return;

		this.attachActionBar();

	}

	protected void addProviderHistory(JXMLContent zContent) throws Exception {
		zContent.getGenerator().getSession().getHistoryManager().addHistoryProvider(this.sourceAction);
	}

	boolean goesNext;
	boolean goesBack;
	@Override
	protected void componentToXML(JXMLContent zContent) throws Exception {
		JInternalGenerateXML iXML = new JInternalGenerateXML();
//		PssLogger.logInfo("ini");
//		long ini = System.nanoTime();
		iXML.oWebWinList = this;
		iXML.zContent = zContent;
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
		
		goesNext=iXML.goesNext;
		goesBack=iXML.goesBack;
		iXML.generateNavigationBar();
		iXML.generateActionRows();
		getActionBar().containerToXMLActions(zContent);
//		long fin = System.nanoTime();
		
//		PssLogger.logInfo("Demora "+((fin-ini)/1000000.0)+" ms");
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





//	protected boolean headerToXML(JWins zWins, JXMLContent zContent, JWin oFirstWin, JGrupoColumnaLista[][] oGrupoColumns, JColumnaLista[] oColumns, boolean isWinListFullExportRequest) throws Exception {
//		JGrupoColumnaLista oGCol;
//		JGrupoColumnaLista oldGCol;
//		int span = 0;
//		// generate the header columns
//		boolean conGrupo = !(oGrupoColumns[0][0].getTitulo().equals("SINGRUPO"));
//		zContent.setAttribute("with_group", conGrupo);
//
//		int maxLevel = 0;
//		for (maxLevel = 0; oGrupoColumns[maxLevel][0] != null; maxLevel++)
//			;
//
//		for (int l = maxLevel - 2; l >= 0; l--) {
//			zContent.startNode("level_column");
//			oldGCol = null;
//			span = 0;
//			for (int g = 0; oGrupoColumns[l][g] != null; g++) {
//				oGCol = oGrupoColumns[l][g];
//				if (oldGCol == null)
//					oldGCol = oGCol;
//				if (oldGCol == oGCol) {
//					span++;
//					continue;
//				}
//				zContent.startNode("grupo_column");
//				zContent.setAttribute("span", span);
//				addDrop(zContent, "column_" + oldGCol.getTitulo(), getWins());
//				zContent.addTextNode("title", JLanguage.translate(oldGCol.getTitulo()));
//				zContent.endNode("grupo_column");
//				oldGCol = oGCol;
//				span = 1;
//			}
//			if (oldGCol != null) {
//				zContent.startNode("grupo_column");
//				zContent.setAttribute("span", span);
//				addDrop(zContent, "column_" + oldGCol.getTitulo(), getWins());
//				zContent.addTextNode("title", JLanguage.translate(oldGCol.getTitulo()));
//				zContent.endNode("grupo_column");
//			}
//			oldGCol = null;
//			zContent.endNode("level_column");
//		}
//
//		zContent.startNode("header");
//		zContent.setAttribute("has_subdetail", zWins.hasSubDetail(isWinListFullExportRequest));
//		zContent.setAttribute("datetime", JDateTools.CurrentDateTime());
//		zContent.setAttribute("filters", formFiltros == null ? "" : formFiltros.toString());
//		if (zWins.hasSubDetail(isWinListFullExportRequest))
//			JWebIcon.getSkinIcon(BizUsuario.retrieveSkinGenerator().getExpandAllSubDetail()).toXML(zContent);
////		if (zWins.getRecords().hasCorteControl()) 
////			cortecontrolToHeaderXML(zContent,zWins.getWinRef());
//
//		JColumnaLista oCol;
//		String sColType;
//		boolean bWithIcon = false;
//		for (int i = 0; i < oColumns.length; i++) {
//			oCol = oColumns[i];
//			if (oCol == null)
//				continue;
//			if (isWinListFullExportRequest && oCol.IfIcono() && i == 0)
//				continue;// el icono inicial en la exportacion no va
//			zContent.startNode("column");
//			if (isWinListFullExportRequest && oCol.IfIcono()) {
//				zContent.addTextNode("title", ""); // en la exportacion debe ir
//				// en blanco, para que no se
//				// ponga
//			} else {
//					if (oCol.IfIcono()) {
//						bWithIcon = true;
//						zContent.setAttribute("halignment", "center");
//						addDrop(zContent, "campo_X", getWins());
//						if (isMultipleSelection()) {
//							zContent.addTextNode("hover", "Sel./Desel.Todo"); // no se traduce
//							zContent.addTextNode("title", "-"); // no se traduce
//							JWebAction webAction = JWebActionFactory.createWinListSelectAll(this, this.sourceAction, this.sourceObjectId, oCol.GetCampo(), direccionOrden, sAll.equals("N") ? "S" : "N",(int)totalCountElement, iFromRow, this.iPageSize);
//							webAction.toXML(zContent);
//
//						} else {
//							zContent.addTextNode("title", "x"); // no se traduce
//
//						}
//						sColType = "JICON";
//					} else if (oCol.IfAction()) {
//						sColType = "JLINK";
//					} else if (oFirstWin != null && !oCol.GetCampo().equals("")) {
//							sColType = getWins().getWinRef().getRecord().getObjectType(oCol.GetCampo());
//					} else {
//						sColType = JObject.JSTRING;
//					}
//
//					if (!oCol.hasAlignment()) {
//						oCol.setAlignmentFromType(sColType);
//					}
//					zContent.setAttribute("halignment", this.getColumnAlignment(oCol.getAlignment()));
//					if (oFirstWin != null && oFirstWin.getWidthCol(oCol.GetCampo()) != 0)
//						zContent.setAttribute("width", oFirstWin.getWidthCol(oCol.GetCampo()));
//					else if (isWinListFullExportRequest) {
//						if (oCol.getFixedProp() != null && oCol.getFixedProp().getSize() != 0 && oCol.getFixedProp().getSize() * 3 < 100)
//							zContent.setAttribute("width", oCol.getFixedProp().getSize() * 3);
//					}
//					
//					if (sColType.equals(JObject.JBOOLEAN)) {
//						zContent.setAttribute("image_true", getWins().getWinRef().getImageIfTrue(oCol.GetCampo()));
//						zContent.setAttribute("image_false", getWins().getWinRef().getImageIfFalse(oCol.GetCampo()));
//					}
//					zContent.setAttribute("type", sColType);
//					JWebAction webAction = JWebActionFactory.createWinListOrden(this, this.sourceAction, this.sourceObjectId, oCol.GetCampo(), (columnOrden == null || !columnOrden.equals(oCol.GetCampo())) ? "ASC" : (direccionOrden.equalsIgnoreCase("asc") ? "desc" : "asc"), sAll,(int)totalCountElement, iFromRow, this.iPageSize);
//					addDrop(zContent, "campo_" + oCol.GetCampo(), getWins());
//					webAction.toXML(zContent);
//					zContent.addTextNode("hover", oCol.GetColumnaTitulo()); // no
//					// se
//					// traduce
//					zContent.addTextNode("title", oCol.GetColumnaTitulo()); // no
//					// se
//					// traduce
//			} // acá, sino antes
//			zContent.endNode("column");
//		}
//		zContent.endNode("header");
//		return bWithIcon;
//	}

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
			if (oProp.hasAlignment())
				zContent.setAttribute("halignment", this.getColumnAlignment(oProp.getAlignment()));
			else
				zContent.setAttribute("halignment", this.getColumnAlignment(oCol.getAlignment()));
			zContent.addTextNode("value", oProp.getValue().toRawFormattedString());

			zContent.endNode("column");
		}
		zContent.endNode("footer");
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



	void rowToXML(JXMLContent zContent, JWin zWin, JColumnaLista[] aColumns, boolean zWithIcon, int iRowPos, JWins iOldSelected, String iOldSingleSelect, boolean firstLevel, boolean isWinListFullExportRequest) throws Exception {
		zContent.setAttribute("rowpos", iRowPos);
		if (isWinListFullExportRequest && hasMap()) {
			zContent.setAttribute("mapdir", zWin.getDireccionMapa());
			zContent.setAttribute("mapname", JTools.replace(zWin.getNombreMapa(), "\"", " "));
		}
		// represent the action bar actions for the given Win
		String id=null;
		if (this.hasActionBar() && firstLevel) {
			zWin.getRecord().keysToFilters();

			String forcedPos = this.getProviderName() + "." + this.getWins().getClass().getSimpleName() + "_" + iRowPos;
			id = zContent.addObject(forcedPos, zWin);
			this.rowToXMLActionBar(zContent, zWin, id);
			
			boolean selected = calculeIsSelected(id, iOldSingleSelect, iOldSelected, iRowPos, zWin);
			zContent.setAttribute("selected", selected);
		}
		String title = zWin.getFieldTooltip(null);
		if (title != null)
			zContent.setAttribute("tooltip", title);


		zContent.setAttribute("id_tree", "treetable-"+zWin.GetValorTreeItemClave());
		if (!zWin.GetValorTreeParentClave().equals("0")) zContent.setAttribute("id_tree_parent", "treetable-parent-"+zWin.GetValorTreeParentClave());
//    PssLogger.logInfo("------------------------------------------------------->"+zWin.GetValorTreeItemClave()+" -> "+zWin.GetValorTreeParentClave());
		addDrop(zContent, "data_" + iRowPos, zWin);

		// each data cell represented as a <d> element
		JColumnaLista oCol;
		for (int i = 0; i < aColumns.length; i++) {
			oCol = aColumns[i];
			fieldToXML(zContent, zWin, oCol, i, id, iRowPos);
		}

		if (!isReadOnly()) {
			zContent.startNode("rightclick");
			zContent.setAttribute("obj_provider", this.getProviderName());
			zContent.endNode("rightclick");
			JWebAction actDblClick = this.getActionDefault(zWin);
			if (addActionsBase("dblclick",actDblClick) != null) {
				zContent.startNode("dblclick");
				actDblClick.toXML(zContent);
				zContent.endNode("dblclick");
//				if (zWithIcon) {
//					zContent.startNode("linkOnIcon");
//					actDblClick.toXML(zContent);
//					zContent.endNode("linkOnIcon");
//				}
			}
			JWebAction actDrag = this.getActionDrop(zWin);
			if (actDrag != null) {
				zContent.startNode("drop");
				actDrag.toXML(zContent);
				zContent.endNode("drop");
			}

			if (this.hasPreviewPanel() && getWins().getForceActionPreview() == 0) {
				JWebAction actSmplClick = this.getActionSimpleClick(zWin);
				if (addActionsBase("smplclick",actSmplClick) != null) {
					zContent.startNode("smplclick");
					actSmplClick.toXML(zContent);
					zContent.endNode("smplclick");
				}
			}
		}
	}

	void rowDetailToXML(JXMLContent zContent, JWin zWin, int colspan, int iRowPos, boolean isWinListFullExportRequest) throws Exception {
		JWins details = zWin.getWinsDetail();

		if (details == null)
			return;
		JWinList oWinList = new JWinList(details); // WinList fantasma para
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
		JWebIcon.getSkinIcon(BizUsuario.retrieveSkinGenerator().getMoreSubDetail()).toXML(zContent);

		boolean bWithIcon = headerToXML(details, zContent, oFirstWin, oGrupoColumns, oColumns, isWinListFullExportRequest);

		zContent.startNode("rows");

		JWin oWin = oFirstWin;
		int iRowCursor = 0;

		while (oWin != null) {
			if (oWin.hasToFilterRecordInGrid()){
				
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
			if (getWins().getToolbarForced()!=null)
				nav.add("toolbar", ""+zWin.getToolbarForced());
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
		JWebWinTreeResponsive oWebWinList;
		JMap<Long,String> cortes; 

		JWin oFirstWin = null;
		int iRowCursor = -1;
		int iParentRowCursor = -1;
		boolean supportOffset = false;
		boolean goesNext = false;
		boolean goesBack = false;
		boolean bWithIcon;
		int iToRow;
		int iTreeRow;
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
			zContent.setAttribute("expanderTemplate", getExpanderTemplate());
			zContent.setAttribute("indentTemplate", getIndentTemplate());
			zContent.setAttribute("expanderExpandedClass", getExpanderExpandedClass());
			zContent.setAttribute("expanderCollapsedClass", getExpanderCollapsedClass());
			zContent.setAttribute("initStatusClass", getInitStatusClass());
			zContent.setAttribute("class_table_responsive", getClassTableResponsive());


			return !JWebActionFactory.isOtherObjectExportRequest(zContent.getGenerator().getRequest(), oWebWinList);
		}

		protected boolean isForceRealAll() throws Exception {
			return JWebActionFactory.isWinListFullExportRequest(zContent.getGenerator().getRequest(), oWebWinList);
		}

		protected void readData() throws Exception {
			getWins().setPagesize(getRealPageSize()+10);
			getWins().setOffset(iFromRow);
			getWins().ConfigurarColumnasListaInternal(winList);
			if (winList.isColumnAction((int)getTreeColumn()))
				zContent.setAttribute("treeColumn", getTreeColumn()+1);
			else
				zContent.setAttribute("treeColumn", getTreeColumn());
			zContent.setAttribute("title", getWins().GetTitle());
			if (columnOrden != null && !columnOrden.equals("")) {
				getWins().getRecords().clearOrderBy();
				getWins().addOrderByFromUI(columnOrden, (direccionOrden == null || direccionOrden.equals("")) ? "asc" : direccionOrden);
				getWins().getRecords().getStructure().setTable(getWins().GetBaseDato().GetTable());
				getWins().getRecords().getStructure().setTableTemporal(getWins().getRecords().GetTableTemporal());
			}

			String alerta = getWins().anyAlert(-1);
			if (alerta != null)
				setAlert(alerta); // Warning referidos a los filtros
			if (!getWins().canReadAll(sourceAction) && !this.isForceRealAll())
				return;
			if (isWinListFullExportRequest && hasGraph()) {
				getWins().preGraphicRead();
			}
//			if (getWins().GetVision() != null && getWins().GetVision().equals("PREVIEW"))
//				getWins().setPagesize(10);

			getWins().ReRead();
			calculateTotalCount();
			alerta = getWins().anyAlert(totalCountElement);
			if (alerta != null)
				setAlert(alerta); // warning referidos a los resultados, paso el
			// tamaño para no volver a calcularlo
		}

		protected void readNextData(int desde) throws Exception {
			getWins().setPagesize(getRealPageSize());
			getWins().setOffset(desde);
			if (!getWins().canReadAll(sourceAction) && !this.isForceRealAll())
				return;

			getWins().ReRead();
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
				iRowCursor = 1;
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
			bWithIcon = headerToXML(getWins(), zContent, oFirstWin, oGrupoColumns, oColumns, isWinListFullExportRequest);
		}


		void savePaginas(JMap<Long,Long> pags) throws Exception {
			paginado="";
			JIterator<Long> it = pags.getKeyIterator();
			while (it.hasMoreElements()) {
				Long key = it.nextElement();
				Long value = pags.getElement(key);
				paginado+=((paginado.equals(""))?"":",")+value.toString();
			}
		}
		
		void generateRows() throws Exception {
			zContent.startNode("rows");
      goesBack=false;
      goesNext=false;
			zContent.setAttribute("zobject", winsObjectId);
			JWin oWin = oFirstWin;
			JMap<Long,Long> pags =  getPaginas();
//			JMap<String,String> pendingParents =JCollectionFactory.createMap();
			cortes=JCollectionFactory.createMap();
			iTreeRow=0;
			boolean puntoCorte=false;
			
			if (iPaginaActual>1)
				goesBack = true;

			while (oWin != null) {
				if (isWinListFullExportRequest && hasGraph())
					generateGraphics(oWin);
				
//				puntoCorte = pendingParents.size()==0;//(oWin.GetValorTreeParentClave().equals("0")||oWin.GetValorTreeParentClave().equals("root_"));
				iTreeRow++;
				
				if (iPageSize!=-1 && iTreeRow>=getRealPageSize() && puntoCorte) {
					iSizePageActual = iFromRow+iRowCursor;
					pags.addElement(new Long(iPaginaActual), new Long(iFromRow));
					pags.addElement(new Long(iPaginaActual+1), new Long(iSizePageActual));
					savePaginas(pags);
					goesNext = oWin!=null;
					break;
				}
				puntoCorte = (oWin.GetValorTreeParentClave().equals("0")||oWin.GetValorTreeParentClave().equals("root_"));
//			if (!(oWin.GetValorTreeParentClave().equals("0")||oWin.GetValorTreeParentClave().equals("root_")) && pendingParents.getElement(oWin.GetValorTreeParentClave())==null)
//					pendingParents.addElement(oWin.GetValorTreeParentClave(), oWin.GetValorTreeParentClave());
//				if ( pendingParents.getElement(oWin.GetValorTreeItemClave())!=null)
//					pendingParents.removeElement(oWin.GetValorTreeItemClave());
//			
				
				zContent.startNode("row");
				
				rowToXML(zContent, oWin, oColumns, bWithIcon, iRowCursor, oldSelect, oldSingleSelect, true, isWinListFullExportRequest);
				
				if (getWins().hasSubDetail(isWinListFullExportRequest))
					rowDetailToXML(zContent, oWin, oColumns.length, iRowCursor, isWinListFullExportRequest);
				
				zContent.endNode("row");
				
				iRowCursor++;
				
				if (getWins().nextRecord())
					oWin = getWins().getRecord();
				else
					oWin = null;
			
				if (oWin ==null && supportOffset && iTreeRow<iPageSize ) {
					readNextData(iFromRow+iRowCursor);
					getWins().firstRecord();
					if (getWins().nextRecord()) {
						oWin = getWins().getRecord();
					}

				} else if (oWin==null && supportOffset) {
					iSizePageActual = iFromRow+iRowCursor;
					pags.addElement(new Long(iPaginaActual), new Long(iFromRow));
					pags.addElement(new Long(iPaginaActual+1), new Long(iSizePageActual));
					savePaginas(pags);
					goesNext = true;
         break;
				}
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
				footToXML(zContent, oTotal, oColumns);

		}
		void generateNavigationBar() throws Exception {
			if (isWinListFullExportRequest)
				return;

			navigationBarToXML(zContent);
		}
		void generateActionRows() throws Exception {
			if (isWinListFullExportRequest)
				return;

			generateActionRowsToXML(zContent);
		}
	}


	protected JWebViewComponent attchGoBack(JWebViewComposite panel) throws Exception {
		if (!goesBack) return null;
		JWebAction webAction = JWebActionFactory.createWinListGoBack(this, this.sourceAction, this.sourceObjectId, columnOrden, direccionOrden, sAll,(int)totalCountElement, 0, iPageSize, this.paginado,this.iPaginaActual-1);
		return 	BizUsuario.retrieveSkinGenerator().createBotonPagination(panel,this.getProviderName()+"_prev",webAction,"Anterior",false);
	}

	protected JWebViewComponent attchGoNext(JWebViewComposite panel) throws Exception {
		if (!goesNext) return null;
		JWebAction webAction = JWebActionFactory.createWinListGoNext(this, this.sourceAction, this.sourceObjectId, columnOrden, direccionOrden, sAll,(int)totalCountElement, 0, iPageSize, this.paginado,this.iPaginaActual+1);
		return 	BizUsuario.retrieveSkinGenerator().createBotonPagination(panel,this.getProviderName()+"_next",webAction,"Siguiente",false);
	}

	
	public JWebViewComponent addPagination(JWebViewComposite panel) throws Exception {
		if (!this.hasPagination()) return null;
		JWebViewInternalComposite pagBar= BizUsuario.retrieveSkinGenerator().createPaginationBar(panel,true, this.getProviderName());
		attchGoBack(pagBar);
		attchGoNext(pagBar);
		JWebViewInternalComposite pagBarMini= BizUsuario.retrieveSkinGenerator().createPaginationBar(panel,false, this.getProviderName());
		attchGoBack(pagBarMini);
		attchGoNext(pagBarMini);
		return pagBar;
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

