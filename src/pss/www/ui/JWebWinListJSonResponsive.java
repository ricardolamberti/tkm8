package pss.www.ui;

import java.awt.Dimension;
import java.util.Arrays;
import java.util.Date;
import java.util.StringTokenizer;

import org.jsoup.Jsoup;

import pss.common.regions.multilanguage.JLanguage;
import pss.common.security.BizUsuario;
import pss.core.services.fields.JObject;
import pss.core.services.fields.JString;
import pss.core.tools.JDateTools;
import pss.core.tools.JTools;
import pss.core.tools.PssLogger;
import pss.core.tools.collections.JIterator;
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
import pss.www.platform.actions.JWebAction;
import pss.www.platform.actions.JWebActionFactory;
import pss.www.platform.actions.JWebRequest;
import pss.www.platform.actions.JWebServerAction;
import pss.www.platform.actions.requestBundle.JWebActionData;
import pss.www.platform.content.generators.JXMLContent;
import pss.www.ui.controller.JWebFormControl;

public class JWebWinListJSonResponsive extends JWebWinGenericResponsive implements JAbsolutelyNamedWebViewComponent, JWebControlInterface {

	//
	// CONSTRUCTOR
	//
	/*
	 * public JWebWinList(JWins zWins) throws Exception { this.oWins = zWins;
	 * this.ensureIsBuilt(); //JWebWinList(zWins, true); }
	 */
	public JWebWinListJSonResponsive(BizAction action,  boolean embedded) throws Exception {
		this(action, embedded, false);
	}
	

	public JWebWinListJSonResponsive(BizAction action, boolean embedded, boolean formLov) throws Exception {
		this.sourceAction = action;
		this.setFormLov(formLov);
		this.setEmbedded(embedded);

	}
	public boolean hasPagination() throws Exception {
		return false; // datatable provee la paginacion
	}
	
	public JWebViewComposite getRootPanel() {
		return this.oRootPanel;
	}

	public static JWebWinListJSonResponsive create(JWebViewComposite parent, JFormLista control) throws Exception {
		BizAction pageAction = control.getAction();

		boolean isPreview=false;
		JWebViewComposite realParent=parent;
		if (parent instanceof JWebTabPanelResponsive) {
			JWebTabResponsive tab = new JWebTabResponsive();
			tab.setTitle(pageAction.GetDescr());
			tab.setId(control.getIdControl());
			if (parent != null) {
				tab.setPreview(parent.findJWebWinForm().isPreview());
				parent.addChild(tab.getObjectName(), tab);
			}
			realParent=tab;
			isPreview=tab.isPreview();
		} else {
			isPreview=parent.findJWebWinForm().isPreview();
		}
		JWebWinListJSonResponsive webList = new JWebWinListJSonResponsive(control.getAction(), true);
		webList.takeAttributesFormControlResponsive(control);
		webList.setParentProvider(parent.findJWebWinForm());
		webList.setNotebookParent(pageAction.getIdAction());
		webList.setWinList(control.GetLista());
		webList.setUseContextMenu(control.getUseContextMenu());

		JActWins act = (JActWins) pageAction.getObjSubmit();
		webList.setMultipleSelection(act.isMultiple());
		webList.setBackBotton(false);
		webList.setLineSelect(act.isLineSelect());
		webList.setPreview(isPreview);
		webList.setPreviewFlag(control.getPreviewFlag());
		webList.setTitle(control.getTitle());
		webList.setReadOnly(control.isReadOnly());
		webList.setToolbar(control.getToolbar());
		webList.setMode(control.getMode());
		webList.setDistributionObjects(control.getDistributionObjects());
		webList.setZoomtofit(control.getZoomtofit());
		webList.setListResponsive(control.isListaResponsive());
		webList.setNeedRefreshAllScreen(control.isNeedRefreshAllScreen());
		webList.setForceHidePaginationBar(control.isForceHidePaginationBar());
		
		webList.setEmbedded(true);
		webList.ensureIsBuilt();
		webList.setName(pageAction.getIdAction());
		realParent.addChild(webList.getRootPanel().getName(), webList.getRootPanel());
		return webList;

	}

	public String getPresentation() throws Exception {
		return JBaseWin.MODE_JSON;
	};

	@Override
	protected void build() throws Exception {

		if (this.winList == null) {
			this.winList = new JWinList(getWins());
		}
		this.regiterObjects();
		iFromRow = initializePagination();
		this.analizeOrden();
		this.analizeHideShowFilter();
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
		return "win_list_json";
	}

	@Override
	protected void containerToXML(JXMLContent zContent) throws Exception {
	}

	@Override
	protected void componentPreLayout(JXMLContent zContent) throws Exception {
		if (!hasWins())
			return;
		this.attachActionBar();

	}
	
	protected int initializePagination() throws Exception {
		JWebRequest req = JWebActionFactory.getCurrentRequest();

		this.search = req.getArgument("search[value]");

		if (JWebActionFactory.isWinListFullExportRequest(req, this)&&isHtmlRequest) {
			this.iPageSize = -1;
			return -1;
		}	
		this.iFromRow = (int) JTools.getLongNumberEmbedded(req.getArgument("start"));
		this.iPageSize = (int) JTools.getLongNumberEmbedded(req.getArgument("length"));
		return iFromRow;
	}

	protected void addProviderHistory(JXMLContent zContent) throws Exception {
		zContent.getGenerator().getSession().getHistoryManager().addHistoryProvider(this.sourceAction);
	}



	@Override
	protected void componentToXML(JXMLContent zContent) throws Exception {
		JInternalGenerateXML iXML = new JInternalGenerateXML();
		iXML.oWebWinList = this;
		iXML.zContent = zContent;
		iXML.jsonRequest = (createWinListExportAllToJson(getSourceAction(), sourceObjectId, JBaseWin.MODE_JSON));
		this.addProviderHistory(zContent);
		if (!iXML.startDeclarations())
			return;
		if (isHtmlRequest&&!isWinListFullExportRequest) {
			iXML.ConfigurarColumnasLista();
			iXML.determineColumns();
			iXML.generateJSonRequest();
			iXML.generateHeader();
			iXML.generateNavigationBar();
			return;
		}

		iXML.readData();
		iXML.determineColumns();
		iXML.initializeGraphics();
		iXML.determineFirstRow();

		iXML.generateHeader();
		iXML.generateRows();

		iXML.generateFooter();
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



	protected boolean headerToXML(JWins zWins, JXMLContent zContent, JGrupoColumnaLista[][] oGrupoColumns, JColumnaLista[] oColumns, boolean isWinListFullExportRequest) throws Exception {
		JGrupoColumnaLista oGCol;
		JGrupoColumnaLista oldGCol;
		int span = 0;
		// generate the header columns
		boolean conGrupo = !(oGrupoColumns[0][0].getTitulo().equals("SINGRUPO"));
		zContent.setAttribute("with_group", conGrupo);

		
		int maxLevel = 0;
		for (maxLevel = 0; oGrupoColumns[maxLevel][0] != null; maxLevel++)
			;

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
			JWebIcon.getSkinIcon(BizUsuario.retrieveSkinGenerator().getExpandAllSubDetail()).toXML(zContent);
		JColumnaLista oCol;
		String sColType;
		boolean bWithIcon = false;

		for (int i = 0; i < oColumns.length; i++) {
			oCol = oColumns[i];
			if (oCol == null)
				continue;
			if (oCol.IfIcono() && i == 0)
				continue;// el icono inicial en la exportacion no va
			zContent.startNode("column");
			zContent.setAttribute("col_name", oCol.GetCampo());
			if (isWinListFullExportRequest && oCol.IfIcono()) {
				zContent.addTextNode("title", ""); // en la exportacion debe ir
				// en blanco, para que no se
				// ponga
			} else {
				if (oCol.IfIcono()) {
					bWithIcon = true;
					zContent.setAttribute("halignment", "center");
					addDrop(zContent, "campo_X", getWins());
					if (isMultipleSelection()) {
						zContent.addTextNode("hover", "Sel./Desel.Todo"); // no
						// se
						// traduce
						zContent.addTextNode("title", "-"); // no se traduce
						JWebAction webAction = JWebActionFactory.createWinListSelectAll(this, this.sourceAction, this.sourceObjectId, oCol.GetCampo(), direccionOrden, sAll.equals("N") ? "S" : "N",(int)totalCountElement, iFromRow, this.getRealPageSize(),null,-1);
						webAction.toXML(zContent);

					} else {
						zContent.addTextNode("title", "x"); // no se traduce

					}
				} else {
					if (oCol.IfIcono()) {
						bWithIcon = true;
						zContent.setAttribute("halignment", "center");
						addDrop(zContent, "campo_X", getWins());
						if (isMultipleSelection()) {
							zContent.addTextNode("hover", "Sel./Desel.Todo"); // no se traduce
							zContent.addTextNode("title", "-"); // no se traduce
							JWebAction webAction = JWebActionFactory.createWinListSelectAll(this, this.sourceAction, this.sourceObjectId, oCol.GetCampo(), direccionOrden, sAll.equals("N") ? "S" : "N",(int)totalCountElement, iFromRow, this.iPageSize,null,-1);
							webAction.toXML(zContent);

						} else {
							zContent.addTextNode("title", "x"); // no se traduce

						}
						sColType = JObject.JSTRING;
					} else if (oCol.IfAction()) {
						sColType = "JLINK";
					} else if (getWins().getWinRef() != null && !oCol.GetCampo().equals("")) {
							sColType = getWins().getWinRef().getRecord().getObjectType(oCol.GetCampo());
					} else {
						sColType = JObject.JSTRING;
					}

					if (!oCol.hasAlignment()) {
						oCol.setAlignmentFromType(sColType);
					}
					zContent.setAttribute("halignment", this.getColumnAlignment(oCol.getAlignment()));
					if (getWins().getWinRef() != null && getWins().getWinRef().getWidthCol(oCol.GetCampo()) != 0)
						zContent.setAttribute("width", getWins().getWinRef().getWidthCol(oCol.GetCampo())+"px");
					else if (oCol.getWidth()!=null) 
						zContent.setAttribute("width", oCol.getWidth());
					else  if (isWinListFullExportRequest) {
						if (oCol.getFixedProp() != null && oCol.getFixedProp().getSize() != 0 && oCol.getFixedProp().getSize() * 3 < 100)
							zContent.setAttribute("width", oCol.getFixedProp().getSize() * 3);
					}
					zContent.setAttribute("type", sColType);
					if (sColType.equals(JObject.JBOOLEAN)) {
						zContent.setAttribute("image_true", getWins().getWinRef().getImageIfTrue(oCol.GetCampo()));
						zContent.setAttribute("image_false", getWins().getWinRef().getImageIfFalse(oCol.GetCampo()));
					}
					zContent.setAttribute("hover", oCol.GetColumnaTitulo()); // no
					zContent.addTextNode("title", oCol.GetColumnaTitulo()); // no
					addDrop(zContent, "campo_" + oCol.GetCampo(), getWins());
					// se
					// traduce
				}
			} // acá, sino antes
			zContent.endNode("column");
		}
		zContent.endNode("header");
		return bWithIcon;
	}

	protected void footToXML(JXMLContent zContent, JTotalizer zTot, JColumnaLista[] oColumns) throws Exception {
		zContent.startNode("footer");
		if (this.getWins().hasSubDetail(false)) {
			zContent.startNode("column");
			zContent.setAttribute("halignment", "center");
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
				zContent.setAttribute("col_name", sColName);
				zContent.setAttribute("halignment", "center");
				zContent.addTextNode("value", "");
				zContent.endNode("column");
				continue;
			}
			zContent.startNode("column");
			zContent.setAttribute("col_name", sColName);
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

	protected void analizeOrden() throws Exception {
		JWebRequest oRequest = JWebActionFactory.getCurrentRequest();
		columnOrden = oRequest.getArgument("order[0][column]");
		direccionOrden = oRequest.getArgument("order[0][dir]");
		return;
	}

	//
	// INTERNAL IMPLEMENTATION
	//
	protected void analizeSelectAll(JXMLContent zContent) throws Exception {
		JWebActionData oListNavData = null;
		JWebRequest oRequest = zContent.getGenerator().getRequest();
		oListNavData = oRequest.getData("win_list_nav");
		if (oListNavData != null) {
			sAll = oListNavData.get("all");
		}

		JWins oldSelect = zContent.getGenerator().getRequest().getSession().getHistoryManager().findLastMultipleSelect(this.sourceAction);
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
				zContent.getGenerator().getSession().getHistoryManager().getLastHistory().findProvider(this.sourceAction).setMultipleSelect(wins);
			} else if (sAll.equals("S") && selectAny) {
				zContent.getGenerator().getRequest().getSession().getHistoryManager().getLastHistory().findProvider(this.sourceAction).setMultipleSelect(null);
			}
		}
		sAll = "N";
		return;
	}

	
	void fieldToXML(JXMLContent zContent, JWin zWin, JColumnaLista oCol, int idCol,String idObj, int rowPos) throws Exception {
		if (oCol == null)
			return;
		if (oCol.IfIcono())
			return;
		if (oCol.IfAction()) {
			actionCellToXML(zContent,zWin,oCol,idCol,idObj);
			return;
		}

		String sColName = oCol.GetCampo();
		try {
			JObject<?> oProp = sColName.equals("") ? null : zWin.getRecord().getPropDeep(sColName);
			if (oProp == null)
				oProp = new JString("");
			oProp.preset();
			Object objProp = oProp.asRawObject();
			String sPropFormatted = oProp.toRawFormattedString();
			if (sPropFormatted == null || oProp.isRawNull()) {
				sPropFormatted = "";
			}

			zContent.startNode("d");
			zContent.setAttribute("col_name", sColName);

			zContent.setAttribute("marked", zWin.isMarked(sColName));
			if (zWin.isWordWrap(sColName))
				zContent.setAttribute("word_wrap", zWin.isWordWrap(sColName));
			zContent.setAttribute("height", zWin.getHeightRow());
			zContent.setAttribute("axis", idCol);
			zContent.setAttribute("visible", true);


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
			
			zContent.setAttribute("data", oProp.toRawString());
			zContent.setAttribute("data_xls2", toXlsData(oProp));

			if (objProp != null) {
				if (oProp.isDate() || oProp.isDateTime()) {
					String sResult = JDateTools.DateToString(((Date) objProp), "yyyy-MM-dd'T'HH:mm:ss");
					zContent.setAttribute("data_xls", sResult);
				} else if (oProp.isFloat() || oProp.isCurrency()) {
					zContent.setAttribute("data_xls", JTools.toPrinteableNumberSinMiles(((Double) objProp),oProp.getCustomPrecision())); // no formateado
				} else if ( oProp.isInteger()) {
					zContent.setAttribute("data_xls", JTools.toPrinteableNumberSinMiles(((Integer) objProp),0)); // no formateado
				} else if ( oProp.isBoolean() ) {
					zContent.setAttribute("data_xls",  oProp.toFormattedString() ); 
				} else if ( sPropFormatted != null && sPropFormatted.indexOf("<")!=-1) {
					zContent.setAttribute("data_xls", Jsoup.parse(sPropFormatted).text()); // no formateado
				} else 
					zContent.setAttribute("data_xls", oProp.asPrintealbleObject());
			} else
				zContent.setAttribute("data_xls", "");

			if (sPropFormatted != null && sPropFormatted.indexOf("<") != -1) {
				zContent.startNode("html_data");
				String out=sPropFormatted;
				if (sPropFormatted.startsWith("html:")) 
					out=sPropFormatted.substring(5);
				zContent.setNodeText("*b64*:"+(java.util.Base64.getEncoder().encodeToString(out.getBytes()).replaceAll("\r\n", "")));
				zContent.endNode("html_data");
			} else
				zContent.setNodeText(sPropFormatted);
			zContent.endNode("d");
		} catch (Exception e) {
			PssLogger.logError(e);
			zContent.startNode("d");
			zContent.setAttribute("col_name", sColName);
			zContent.setAttribute("marked", false);
			zContent.setNodeText(e.getMessage());
			zContent.setAttribute("word_wrap", true);
			zContent.endNode("d");
		}
	}

	private String toXlsData(JObject<?> oProp) throws Exception {
		if (oProp == null)
			return "";
		if (oProp.isCurrency() || oProp.isFloat())
			return oProp.toRawString().replace(".", ",");
		return oProp.toRawString();
	}

	void rowToXML(JXMLContent zContent, JWin zWin, JColumnaLista[] aColumns, boolean zWithIcon, int iRowPos, JWins iOldSelected, String iOldSingleSelect, boolean firstLevel, boolean isWinListFullExportRequest) throws Exception {
		zContent.setAttribute("rowpos", iRowPos);
		String id=null;
		if (this.hasActionBar()) {
			zWin.getRecord().keysToFilters();
			String forcedPos = this.getProviderName() + "." + this.getWins().getClass().getSimpleName() + "_" + iRowPos;
			id = zContent.addObject(forcedPos, zWin);
			this.rowToXMLActionBar(zContent, zWin, id);

			boolean selected = calculeIsSelected(id, iOldSingleSelect, iOldSelected, iRowPos, zWin);
			
			if (selected)
				zContent.setAttribute("rowclass", BizUsuario.retrieveSkinGenerator().getRowClass()+" selected");
			else
				zContent.setAttribute("rowclass", BizUsuario.retrieveSkinGenerator().getRowClass());
		}
		String title = zWin.getFieldTooltip(null);
		if (title != null)
			zContent.setAttribute("tooltip", title);

//		zContent.setAttribute("corte_control", zWin.getCorteControl(0));
		addDrop(zContent, "data_" + iRowPos, zWin);

		// zContent.setAttribute("id", zWin.GetHTMLObjectIdentification());
		// each data cell represented as a <d> element
		JColumnaLista oCol;
		for (int i = 0; i < aColumns.length; i++) {
			oCol = aColumns[i];
			fieldToXML(zContent, zWin, oCol, i, id, iRowPos);
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

		boolean bWithIcon = headerToXML(details, zContent, oGrupoColumns, oColumns, isWinListFullExportRequest);

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
		BizActions actions = zWin.getRawActionMap();
		if (actions == null)
			return null;
		action = actions.findActionByUniqueId(zWin.getSimpleClick());
		if (action == null)
			action = zWin.findActionByUniqueId(JWin.ACTION_QUERY+"", false, false);
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
		return wa;
	}

	protected int getRowSpan(String zColumn, String zValue) {
		return 0;
	}

	protected boolean shouldSkip(String zColumn, String zValue) {
		return false;
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

	public String getScroll(JXMLContent zContent) throws Exception {
		JWebRequest oRequest = zContent.getGenerator().getRequest();
		if (oRequest.getSession().getHistoryManager().getLastHistory() == null)
			return "";
		return oRequest.getSession().getHistoryManager().findLastScroll(this.sourceAction);
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
	 */
	class JInternalGenerateXML {
		JWebRequest oRequest;
		JXMLContent zContent;
		JWebWinListJSonResponsive oWebWinList;
		JWebAction jsonRequest;

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

			zContent.setAttribute("draw", oRequest.getArgument("draw"));
			zContent.setAttribute("with_preview", hasPreviewPanel());
			zContent.setAttribute("list_responsive", isListResponsive());
			zContent.setAttribute("ordering", isOrdeningControl());
			zContent.setAttribute("lengthchange", isLengthChangeControl());
			zContent.setAttribute("headerinfooter", isHeaderInFooter());
			zContent.setAttribute("searching", isSearchingControl());
			zContent.setAttribute("distribution", getDistributionObjects());
			zContent.setAttribute("has_action_bar", hasActionBar());
			zContent.setAttribute("obj_provider", getProviderName());
			zContent.setAttribute("is_multiple_select", isMultipleSelection());
			zContent.setAttribute("is_line_select", isLineSelect());
			zContent.setAttribute("scroll", getScroll(zContent));
			zContent.setAttribute("pageWidth", getWidthPage());
			zContent.setAttribute("pageHeight", getHeigthPage());
			zContent.setAttribute("zobject", winsObjectId);
			zContent.setAttribute("class_table_responsive", getClassTableResponsive());

			if (!isReadOnly()) {
				zContent.startNode("rightclick");
				zContent.setAttribute("obj_provider", getProviderName());
				zContent.endNode("rightclick");
				JWebAction actDblClick = getActionDefault(getWins().getWinRef());
				if (actDblClick != null) {
					zContent.startNode("dblclick");
					actDblClick.toXML(zContent);
					zContent.endNode("dblclick");
				}
				JWebAction actDrag = getActionDrop(getWins().getWinRef());
				if (actDrag != null) {
					zContent.startNode("drop");
					actDrag.toXML(zContent);
					zContent.endNode("drop");
				}

				if (hasPreviewPanel() && getWins().getForceActionPreview() == 0) {
					JWebAction actSmplClick = getActionSimpleClick(getWins().getWinRef());
					if (actSmplClick != null) {
						zContent.startNode("smplclick");
						actSmplClick.toXML(zContent);
						zContent.endNode("smplclick");
					}
				}
			}

			return !JWebActionFactory.isOtherObjectExportRequest(zContent.getGenerator().getRequest(), oWebWinList);
		}

		protected boolean isForceRealAll() throws Exception {
			return JWebActionFactory.isWinListFullExportRequest(zContent.getGenerator().getRequest(), oWebWinList);
		}

		private void addFilterQuickSearch(String value) throws Exception {
			if (value == null || value.trim().equals(""))
				return;
			if (getWins().ifEstatico())
				return;
			StringTokenizer t = new StringTokenizer(getWins().getWinRef().getSearchFields(false), ";");
			while (t.hasMoreTokens()) {
				String f = t.nextToken();
				if (!getWins().getWinRef().getRecord().getFixedProp(f).isTableBased())
					continue;
				// deberia ser or RJL
				getWins().getRecords().clearDynamicFilters();
				if (!getWins().assignFilters(f, value)) {
					if (getWins().getWinRef().getRecord().getProp(f).isString())
						getWins().getRecords().addFilter(f, value, "ILIKE").setDynamic(true);
					else if (JTools.isNumber(value)) {
						getWins().getRecords().addFilter(f, value).setDynamic(true);
					}
				}

			}
		}

		private boolean checkFilterQuickSearch(String value, JWin win) throws Exception {
			if (value == null || value.trim().equals(""))
				return true;
			boolean check = false;
			StringTokenizer t = new StringTokenizer(getWins().getWinRef().getSearchFields(false), ";");
			while (t.hasMoreTokens()) {
				String f = t.nextToken();
				// if
				// (getWins().getWinRef().getRecord().getFixedProp(f).isTableBased())
				// continue;

				check |= win.checkStaticFiltersInQuickSearch(f, value);
			}
			return check;
		}

		protected void readData() throws Exception {
			addFilterQuickSearch(search);
			getWins().setPagesize(getRealPageSize());
			getWins().setOffset(iFromRow);
			getWins().ConfigurarColumnasListaInternal(winList);
			zContent.setAttribute("title", getWins().GetTitle());
			if (columnOrden != null && !columnOrden.equals("")) {
				getWins().getRecords().clearOrderBy();
				getWins().addOrderByFromUI(winList.getColumnPos(JTools.getLongNumberEmbedded(columnOrden)+1), (direccionOrden == null || direccionOrden.equals("")) ? "asc" : direccionOrden, false);
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

		protected void ConfigurarColumnasLista() throws Exception {
			getWins().ConfigurarColumnasListaInternal(winList);
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
				getWins().firstRecord();
				if (getWins().nextRecord()) {
					oFirstWin = getWins().getRecord();
				}
			}
		}

		void generateJSonRequest() throws Exception {
			zContent.startNode("json");
			jsonRequest.toXML(zContent);
			zContent.endNode("json");
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
			bWithIcon = headerToXML(getWins(), zContent, oGrupoColumns, oColumns, isWinListFullExportRequest);
		}

		void generateRows() throws Exception {
			zContent.startNode("rows");

			zContent.setAttribute("zobject", winsObjectId);
			zContent.setAttribute("corte_control", hasCorteControl());
			
			JWin oWin = oFirstWin;
			long nroRecords = 0;
			if (iPageSize != -1)
				iToRow = iFromRow + getRealPageSize() - 1;
			else
				iToRow = -1;
 
			if (iFromRow != -1 && iFromRow > 0)
				goesBack = true;
			int filtrados = 0;
			while (oWin != null) {
				if (checkFilterQuickSearch(search, oWin)) {
					zContent.startNode("row");
					nroRecords++;
					rowToXML(zContent, oWin, oColumns, bWithIcon, iRowCursor++, oldSelect, oldSingleSelect, true, isWinListFullExportRequest);
					zContent.endNode("row");
				} else
					filtrados++;
				if (getWins().nextRecord())
					oWin = getWins().getRecord();
				else
					oWin = null;
			}

			zContent.endNode("rows");
			zContent.startNode("info");
			zContent.setAttribute("nro_records", totalCountElement == -1 ? nroRecords : totalCountElement - filtrados);
			zContent.endNode("info");

		}

		void generateFooter() throws Exception {
			JTotalizer oTotal = winList.getTotalizer();
			if (oTotal != null)
				footToXML(zContent, oTotal, oColumns);
		}

		void generateNavigationBar() throws Exception {
			if (isWinListFullExportRequest)
				return;

			navigationBarToXML(zContent);
		}
	}

	public JWebViewComponent addSelectSizePage(JWebViewComposite panel) throws Exception {
		return null;
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