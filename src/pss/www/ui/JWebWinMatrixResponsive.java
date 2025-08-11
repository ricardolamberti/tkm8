package pss.www.ui;

import java.awt.Dimension;
import java.util.Iterator;
import java.util.SortedMap;
import java.util.TreeMap;

import org.jsoup.Jsoup;

import pss.common.customList.config.field.campo.BizCampo;
import pss.common.security.BizUsuario;
import pss.core.services.fields.JBoolean;
import pss.core.services.fields.JCurrency;
import pss.core.services.fields.JDate;
import pss.core.services.fields.JDateTime;
import pss.core.services.fields.JFloat;
import pss.core.services.fields.JInteger;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JObject;
import pss.core.services.fields.JString;
import pss.core.tools.JDateTools;
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
import pss.core.win.submits.JActWins;
import pss.core.winUI.controls.JFormLista;
import pss.core.winUI.lists.JColumnaLista;
import pss.core.winUI.lists.JColumnaMatrix;
import pss.core.winUI.lists.JDataMatrix;
import pss.core.winUI.lists.JEjeMatrix;
import pss.core.winUI.lists.JFilaMatrix;
import pss.core.winUI.lists.JOrdenEjeMatrix;
import pss.core.winUI.lists.JOrdenMatrix;
import pss.core.winUI.lists.JWinList;
import pss.core.winUI.lists.JWinMatrix;
import pss.core.winUI.responsiveControls.JFormMatrixResponsive;
import pss.www.platform.actions.JWebAction;
import pss.www.platform.actions.JWebActionFactory;
import pss.www.platform.actions.JWebRequest;
import pss.www.platform.actions.JWebServerAction;
import pss.www.platform.actions.requestBundle.JWebActionData;
import pss.www.platform.content.generators.JXMLContent;
import pss.www.ui.controller.JWebFormControl;

public class JWebWinMatrixResponsive  extends JWebWinGenericResponsive implements JAbsolutelyNamedWebViewComponent, JWebActionOwnerProvider, JWebControlInterface {

	protected JWinMatrix winMatrix = null;
	public String infoExtra = null;

	//
	// CONSTRUCTOR
	//
	/*
	 * public JWebWinList(JWins zWins) throws Exception { this.getWins() = zWins;
	 * this.ensureIsBuilt(); //JWebWinList(zWins, true); }
	 */
	public JWebWinMatrixResponsive(BizAction action, boolean build) throws Exception {
		this.sourceAction = action;
		setMode(JFormLista.MODE_CUADRICULA);
	}

	public static JWebWinMatrixResponsive create(JWebViewComposite parent,  JFormMatrixResponsive control) throws Exception {
		BizAction pageAction = control.getAction();
		
		JWebViewComposite realParent=parent;
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
		JWebWinMatrixResponsive webList=new JWebWinMatrixResponsive(control.getAction(),true);
		webList.takeAttributesFormControlResponsive(control);
		webList.setParentProvider(parent.findJWebWinForm());
		webList.setNotebookParent(pageAction.getIdAction());
		JActWins act = (JActWins) pageAction.getObjSubmit();
		webList.setMultipleSelection(act.isMultiple());
		webList.setBackBotton(false);
		webList.setEmbedded(true);
		webList.setLineSelect(act.isLineSelect());
		webList.setReadOnly(control.isReadOnly());
		webList.setToolbar(control.getToolbar());
		webList.setTitle(control.getTitle());
		webList.setNeedRefreshAllScreen(control.isNeedRefreshAllScreen());
		webList.setDistributionObjects(control.getDistributionObjects());
		webList.setZoomtofit(control.getZoomtofit());
		webList.setPreviewFlag(control.getPreviewFlag());
		webList.setMode(control.getMode());
		webList.setForceHidePaginationBar(control.isForceHidePaginationBar());
		webList.setUseContextMenu(control.getUseContextMenu());
		webList.setShowFooterBar(control.isShowFooterBar());

		webList.ensureIsBuilt();
		webList.setName(pageAction.getIdAction());
		realParent.addChild(webList.getRootPanel().getName(), webList.getRootPanel());
		return webList;

	}
	public JWebViewComposite getRootPanel() {
		return this.oRootPanel;
	}
	public boolean isShowFooterBar() {
		if (winMatrix!=null)
			return bShowFooterBar && winMatrix.isWithFooter();
		return bShowFooterBar;
	}

	@Override
	protected void build() throws Exception {

		if (this.winMatrix == null)
			this.winMatrix = new JWinMatrix(getWins());
		
		iFromRow = initializePagination();
		analizeScript();
		analizeSelectAll();
		analizeOrden();
		analizeHideShowFilter();
		this.regiterObjects();
		;

		super.build();
	}
	
	public boolean hasPreview() throws Exception {
		return false;
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

	@Override
	protected void containerToXML(JXMLContent zContent) throws Exception {
	}



	@Override
	protected void componentPreLayout(JXMLContent zContent) throws Exception {
		if (!hasWins())
			return;
		attachActionBar();
	}

	protected void addProviderHistory(JXMLContent zContent) throws Exception {
		zContent.getGenerator().getSession().getHistoryManager().addHistoryProvider(this.sourceAction);
	}

	@Override
	protected void componentToXML(JXMLContent zContent) throws Exception {
		this.addProviderHistory(zContent);

		if (!startDeclarations(zContent))
			return;

		if (!readData(zContent)) return ;
		calculateMatrix();
		generateHeader(zContent);
		generateRows(zContent);
		generateFooter(zContent);
		getActionBar().containerToXMLActions(zContent);

	}







	protected boolean hasMap() throws Exception {
		return winMatrix.canMapear();
	}

	protected boolean hasGraph() throws Exception {
		return winMatrix.hasGraficos();
	}
	//
	// INTERNAL IMPLEMENTATION
	//
	protected void analizeSelectAll() throws Exception {
		JWebActionData oListNavData = null;
		JWebRequest oRequest = JWebActionFactory.getCurrentRequest();
		oListNavData = oRequest.getData("win_list_nav_" + this.getName());
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
	
	




	protected String cellToXMLActionBar(JXMLContent zContent, JWin zWin) throws Exception {
		JIterator<JEjeMatrix> it = winMatrix.GetEjesMatrix().getIterator();
		while (it.hasMoreElements()) {
			JEjeMatrix eje = it.nextElement();
			zWin.getRecord().setFilterValue(eje.GetCampo(),eje.getValue(zWin).toString());//.setDynamic(true);
		}
		zWin.getRecord().keysToFilters();
		String id = zContent.addObject(zWin);
		return id;
	}



	int fieldToXML(JXMLContent zContent, JFilaMatrix fila, JColumnaMatrix columna, int iRowPos, int iColPos,int pos, JWins iOldSelected, String iOldSingleSelect) throws Exception {
		boolean startNodeD=false;
		int salto=1;
		String winObjectId="";
		String rowWinObjectId="";
		try {
			
			
			JDataMatrix oData = fila.getData(columna.getClave());
			if (oData!=null ) {
				if (oData.getWin()!=null) {
//					rowWinObjectId = this.rowToXMLActionBar(getNotebookParent() + "" + this.getWins().getClass().getSimpleName() + "_" + iRowPos, zContent, oData.getWin());
					String forcedPos = this.getProviderName() + "." + this.getWins().getClass().getSimpleName() + "_" + iRowPos;
					rowWinObjectId = zContent.addObject(forcedPos, oData.getWin());
					this.rowToXMLActionBar(zContent, oData.getWin(),rowWinObjectId);
					winObjectId = this.cellToXMLActionBar(zContent, oData.getWin());
				
					if (isMultipleSelection()) {
						if (iOldSelected!=null)
							zContent.setAttribute("selected", iOldSelected.extract(oData.getWin().getRecord()));
						else
							zContent.setAttribute("selected", rowWinObjectId.equals(iOldSingleSelect));
					}	else 
						zContent.setAttribute("selected", rowWinObjectId.equals(iOldSingleSelect));
						
				}

			} 
			zContent.startNode("d");
			startNodeD=true;
			zContent.setAttribute("marked", false);
			zContent.setAttribute("axis", pos);
			zContent.setAttribute("halignment", this.getColumnAlignment(columna.getAlignment()));


			if (columna.isEje()) 
				zContent.setAttribute("iseje", true);

			if (oData == null) {
				zContent.setAttribute("visible", true);
				String value = columna.getColorForeground();
				if (value != null) zContent.setAttribute("foreground",  JTools.toHtmlColor(value));
				value = columna.getColorBackground();
				if (value != null) zContent.setAttribute("background",  JTools.toHtmlColor(value));
				addDrop(zContent, (columna.isEje()?"fila_":"data_")+columna.getCampo(),getWins());
				if (fila.isSubtotal())
					zContent.setAttribute("class_responsive", "subtotal");
				zContent.setAttribute("data", "");
				zContent.setAttribute("data_xls", ""); 
				zContent.setNodeText("-");

			} else {
				
				
				zContent.setAttribute("zobjectcell", winObjectId);
				zContent.setAttribute("visible", oData.isVisible());
				zContent.setAttribute("data", oData.getValue());
				String value = oData.getClassResponsive();
				value = columna.isEje()?(value==null?"":value)+" eje ":value;
				if (value != null) zContent.setAttribute("class_responsive", value);
				String sResult=oData.getValue();
				if (sResult!=null && sResult.indexOf("<")!=-1) {
					zContent.setAttribute("data_xls", Jsoup.parse(sResult).text()); // no formateado
				} else	if ( !oData.isMultiple() && ( oData.getType().equals(JFloat.class) || oData.getType().equals(JCurrency.class)) ) {
//					sResult=JRegionalFormatterFactory.getAbsoluteRegionalFormatter().formatNumber(oData.getValorOrdenador(),2);
//					zContent.setAttribute("data_xls", sResult); // ver
					sResult=oData.getValue(); // formateado
					zContent.setAttribute("data_xls", JTools.toPrinteableNumberSinMiles(oData.getValorOrdenador(),2)); // no formateado
				} else 	if (  oData.getType().equals(JDateTime.class)  ) {
					String sResultXls=JDateTools.DateToString(JDateTools.StringToDate(oData.getValue(),"dd-MM-yyyy HH:mm:ss"),"yyyy-MM-dd'T'HH:mm:ss");
					zContent.setAttribute("data_xls", sResultXls); 
					sResult=oData.getValue();
				} else 	if ( oData.getType().equals(JDate.class) ) {
					String sResultXls=JDateTools.DateToString(JDateTools.getDateStartDay(JDateTools.StringToDate(oData.getValue(),"dd-MM-yyyy")),"yyyy-MM-dd'T'HH:mm:ss");
					zContent.setAttribute("data_xls", sResultXls); 
					sResult=oData.getValue();
				} else 	if ( oData.getType().equals(JBoolean.class) ) {
					zContent.setAttribute("image_true", oData.getWin().getImageIfTrue(oData.getColumna()));
					zContent.setAttribute("image_false", oData.getWin().getImageIfFalse(oData.getColumna()));
					sResult=oData.getValue();
					zContent.setAttribute("data_xls",sResult ); 
				}	else { 
					sResult=oData.getValue();
					zContent.setAttribute("data_xls",sResult ); 
				}
				int iRowSpan = oData.getRowspan();
				if (iRowSpan > 1 ) {
				 zContent.setAttribute("rowspan", iRowSpan);
				}
				int iColSpan = oData.getColspan();
				if (iColSpan > 1 ) {
				 salto=iColSpan;
				 zContent.setAttribute("colspan", iColSpan);
				}
				if (oData.getWin()!=null) {
					if (oData.getWin().isWordWrap(columna.getCampo()))	zContent.setAttribute("word_wrap", oData.getWin().isWordWrap(columna.getCampo()));
					zContent.setAttribute("height", oData.getWin().getHeightRow());
	
					if (columna.getCampo()!=null) {
						
					  value = oData.getColorForeground()!=null?oData.getColorForeground():columna.getColorForeground()!=null?columna.getColorForeground():oData.getWin().getFieldForeground(columna.getCampo());
						if (value != null) zContent.setAttribute("foreground", value);
						value = oData.getColorBackground()!=null?oData.getColorBackground():columna.getColorBackground()!=null?columna.getColorBackground():oData.getWin().getFieldBackground(columna.getCampo());
						if (value != null) zContent.setAttribute("background", value);
						value = oData.getWin().getFieldTooltip(columna.getCampo());
						if (value != null) zContent.setAttribute("tooltip", value);
						addDrop(zContent, (columna.isEje()?"fila_":"data_")+columna.getCampo(),oData.getWin());
						
					} else {
						if (columna.isEje()) {
							addDrop(zContent, "fila_"+iRowPos,getWins());
						}
					}
					if (hasActionBar()) {
						JWebAction actDblClick = null;
						if (columna.isEje()) 
							actDblClick =this.getActionFila(zContent,oData.getWin(),columna.getCampo());
						else
							actDblClick =this.getActionDefault(zContent,oData.getWin(),winObjectId,columna.getCampo());
						if (actDblClick != null ) {
							zContent.startNode("dblclick");
							actDblClick.toXML(zContent);
							zContent.endNode("dblclick");
						}
					}
				}else {
					if (columna.isEje()) {
						addDrop(zContent, "total_"+columna.getCampo(),getWins());
					}

				}
				if (sResult!=null && sResult.indexOf("<")!=-1) {
					zContent.startNode("html_data");
					if (!supportHtml()) {
						zContent.setNodeText(JTools.trimHTML(sResult));
					} else {
						zContent.setNodeText(sResult);
					}
					zContent.endNode("html_data");
				} else
					zContent.setNodeText(sResult);

			}
			zContent.endNode("d");
			startNodeD=false;

		} catch (Exception e) {
			if (e.getMessage()!=null && e.getMessage().equals("Transaccion Abortada por el usuario"))
				throw e;
			PssLogger.logError(e);
			if (!startNodeD) zContent.startNode("d");
			zContent.setAttribute("marked", false);
			zContent.setNodeText(e.getMessage());
			zContent.setAttribute("word_wrap", true);
			zContent.endNode("d");
		}
		return supportSpans()?salto:1;
	}

	void rowToXML(JXMLContent zContent, JFilaMatrix fila, int iRowPos, JWins iOldSelected, String iOldSingleSelect) throws Exception {
		zContent.setAttribute("rowpos", iRowPos);
		int col = 1;
		int salto=1;
		int posCol=0;
		JIterator<JColumnaMatrix> itColumna = columnas.getValueIterator();
		while (itColumna.hasMoreElements()) {
			JColumnaMatrix columna = itColumna.nextElement();
			if (--salto==0) salto=fieldToXML(zContent, fila, columna, iRowPos,col,posCol,oldSelect, oldSingleSelect);
			col++;
			posCol+=salto;
		}
		if (!isReadOnly()) {
			zContent.startNode("rightclick");
      zContent.setAttribute("obj_provider", this.getProviderName());
			zContent.endNode("rightclick");
			
		}

	}
	
	protected void footToXML(JXMLContent zContent) throws Exception {
		if (!getWins().matrixDisplayTotales()) return;
		zContent.startNode("footer");
		JIterator<JColumnaMatrix> itColumna = columnas.getValueIterator();
		boolean one=true;
		while (itColumna.hasMoreElements()) {
			JColumnaMatrix columna = itColumna.nextElement();
			if (columna.isEje() && columna.getValor()==0) {
				zContent.startNode("column");
				String value=null;
				addDrop(zContent, "total_"+columna.getCampo(), getWins());
				zContent.setAttribute("halignment", this.getColumnAlignment( JColumnaLista.ALIGNMENT_LEFT));
				zContent.setAttribute("data_xls", one?"Total":""); 
				zContent.addTextNode("value", one?"Total":"");
				zContent.endNode("column");
				one=false;
			} else {
				String valor="";
				String valorXls="";
				zContent.startNode("column");
				String value=null;
				zContent.setAttribute("halignment", this.getColumnAlignment(columna.getAlignment()));
				if (columna.isPorcentaje()){
					double val = columna.getValor();
					valorXls=JTools.toPrinteableNumberSinMiles(val,2);
					valor= new JFloat(val).setPrec(2).toFormattedString();
				}	else if (columna.getFunction()!=null && columna.getFunction().equals(BizCampo.FUNTION_AVR)){
					double val = columna.getCantidad()==0?0:columna.getValor()/columna.getCantidad();
					valorXls=JTools.toPrinteableNumberSinMiles(val,2);
					valor= new JFloat(val).setPrec(2).toFormattedString();

				}	else if (columna.getClase().equals(JFloat.class)) {
					double val =columna.getValor();
					valorXls=JTools.toPrinteableNumberSinMiles(val,2);
					valor= new JFloat(val).setPrec(2).toFormattedString();
				}	else  if (columna.getClase().equals(JCurrency.class)) {
					double val =columna.getValor();
					valorXls=JTools.toPrinteableNumberSinMiles(val,2);
					valor= new JFloat(val).setPrec(2).toFormattedString();
				}	else  if (columna.getClase().equals(JDate.class)||columna.getClase().equals(JDateTime.class)) {
					valorXls=valor="";
				}	else if (columna.getClase().equals(JLong.class) || columna.getClase().equals(JInteger.class)) {
					long val =(long)columna.getValor();
					valorXls=""+val;
					valor=new JLong(val).toFormattedString();

				}
				else if (JTools.isNumber(""+columna.getValor())){
					double val =columna.getValor();
					valorXls=JTools.toPrinteableNumberSinMiles(val,2);
					valor= new JFloat(val).setPrec(2).toFormattedString();

				} else
					valorXls=valor=""+columna.getValor();
				zContent.setAttribute("data_xls",valorXls); 
				addDrop(zContent, "total_"+columna.getCampo(), getWins());
				zContent.addTextNode("value",valor);
				zContent.endNode("column");
			}
		}
		zContent.endNode("footer");
		if (this.hasPosFunction()) {
			zContent.startNode("posfunction");
			zContent.setAttribute("script", this.getPosFunction());
			zContent.endNode("posfunction");
		}
	}


	protected JWebAction getActionDefault(JXMLContent zContent,JWin zWin,String winObjectId, String columna) throws Exception {
		// if (this.actionDefault!=null) return this.actionDefault;
		BizAction action = zWin.findAction(zWin.GetDobleClick(columna,false));
		if (action == null)
			action = zWin.findAction(JWin.ACTION_DROP);
		if (action == null)
			action = zWin.findAction(JWin.ACTION_QUERY);
		if (action == null)
			return null;
		// return (this.actionDefault=JWebActionFactory.createViewAreaAction(action,
		// this, false, null));
		return JWebActionFactory.createViewAreaAndTitleAction(action, this, false, winObjectId);
	}

	protected JWebAction getActionFila(JXMLContent zContent,JWin zWin,String columna) throws Exception {
		// if (this.actionDefault!=null) return this.actionDefault;
		BizAction action = zWin.findAction(zWin.GetDobleClick(columna,true));//16
		if (action == null)
			action = zWin.findAction(JWin.ACTION_DROP);
		if (action == null)
			action = zWin.findAction(JWin.ACTION_QUERY);
		if (action == null)
			return null;
		// return (this.actionDefault=JWebActionFactory.createViewAreaAction(action,
		// this, false, null));
		return JWebActionFactory.createViewAreaAndTitleAction(action, this, false, null);
	}

	protected boolean shouldSkip(String zColumn, String zValue) {
		return false;
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

	@Override
	public Dimension getDefaultSize() {
		return new Dimension(600, 400);
	}

	//	public String getRegisteredObjectId() {
//		return winsObjectId;
//	}

//	public String getProviderName() throws Exception {
//		return this.getName();
//	}

	public JWebAction getWebSourceAction() throws Exception {
		JWebServerAction a = JWebActionFactory.createViewAreaAndTitleAction(this.sourceAction, this, true, null);
		JWebActionData nav = a.addData("win_list_nav");
		nav.add("with_preview", ""+this.findPreviewFlag());
		nav.add("embedded", ""+this.isEmbedded());
		nav.add("is_preview", ""+this.isPreview());
		nav.add("toolbar", ""+this.getToolbar());
		return a;
	}

//	public String getSourceObjectId() {
//		return this.sourceObjectId;
//	}

	// public String getWinObjectId() {
	// return this.winObjectId;
	// }
//	public String getName() {
//		return "win_list";
//	}

	public boolean isOnlyWinListTablePanel() {
		return bOnlyWinListTablePanel;
	}

	public void setOnlyWinListTablePanel(boolean onlyWinListTablePanel) {
		bOnlyWinListTablePanel = onlyWinListTablePanel;
	}

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

//	public boolean isShowActionBar() {
//		return bShowActionBar;
//	}
//
//	public void setShowActionBar(boolean showActionBar) {
//		bShowActionBar = showActionBar;
//	}
//	
//	public boolean isReadOnly() {
//		return bReadOnly;
//	}
//
//	public void setReadOnly(boolean readOnly) {
//		bReadOnly = readOnly;
//	}

//	public void setToolbarTop(boolean value) {
//		this.bToolbarTop = value;
//	}


//	public void setWithFrame(boolean value) {
//		this.bWithFrame = value;
//	}

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

//	public BizAction getSourceAction() {
//		return sourceAction;
//	}

	public String getWidthPage() {
		if (winMatrix.getPageType().equals(JWinList.PAGETYPE_LANDSCAPE)) {
//			return "11.693055555555556in";
			return "50in";
		} else {
			return "8.268055555555555in";
		}
	}

	public String getHeigthPage() {
		if (winMatrix.getPageType().equals(JWinList.PAGETYPE_LANDSCAPE)) {
			return "8.268055555555555in";
			} else {
			return "11.693055555555556in";
		}
	}
  boolean isPreview = false;
	protected String findIcon(JWin win) throws Exception {
		try {
			return win.GetIconFile();
		} catch (Exception e) {
			return "";
		}
	}

	JWebRequest oRequest;
	JMap<String, JFilaMatrix> matrix = null;
	JMap<String, JColumnaMatrix> columnas = null;

	JWin oFirstWin = null;
	int iRowCursor = -1;
	boolean supportOffset = false;
	boolean goesNext = false;
	boolean goesBack = false;
	boolean bWithIcon;
	int iToRow;
	String oldSingleSelect;
	JWins oldSelect;

	boolean startDeclarations(JXMLContent zContent) throws Exception {
		if (!hasWins())
			return false;
		oRequest = zContent.getGenerator().getRequest();
		isWinListFullExportRequest = JWebActionFactory.isWinListFullExportRequest(oRequest, this);
		oldSelect = getOldSelect(zContent);
		oldSingleSelect = getOldSingleSelect(zContent);

		zContent.setAttribute("has_action_bar", hasActionBar());
		zContent.setAttribute("distribution", getDistributionObjects());
		zContent.setAttribute("list_responsive", isListResponsive());
		zContent.setAttribute("ordering", isOrdeningControl());
		zContent.setAttribute("lengthchange", isLengthChangeControl());
		zContent.setAttribute("searching", isSearchingControl());
		zContent.setAttribute("obj_provider", getProviderName());
		zContent.setAttribute("headerinfooter", isHeaderInFooter());
		zContent.setAttribute("is_multiple_select", false);
		zContent.setAttribute("is_line_select", false);
		zContent.setAttribute("scroll", getScroll(zContent));
		zContent.setAttribute("pageWidth", getWidthPage());
		zContent.setAttribute("pageHeight", getHeigthPage());
		zContent.setAttribute("class_table_responsive", getClassTableResponsive());
		if (isZoomtofit()) {
			zContent.setAttribute("zoomtofit", "zoom_"+getName());
			zContent.setAttribute("zoomtofit_width", getZoomtofit());
		}
		return !JWebActionFactory.isOtherObjectExportRequest(zContent.getGenerator().getRequest(), this);
	}

	public String getPresentation() throws Exception {
		return JBaseWin.MODE_MATRIX;
	};

	protected boolean isForceRealAll(JXMLContent zContent) throws Exception {
		return JWebActionFactory.isWinListFullExportRequest(zContent.getGenerator().getRequest(), this);
	}

	protected boolean readData(JXMLContent zContent) throws Exception {
		getWins().setPagesize(getRealPageSize());
		getWins().setOffset(iFromRow);
		getWins().ConfigurarMatrix(winMatrix);
		getWins().ConfigurarColumnasListaInternal(winMatrix);
		zContent.setAttribute("title", getWins().GetTitle());
		if (columnOrden != null && !columnOrden.equals("")) {
			getWins().getRecords().clearOrderBy();
			getWins().addOrderByFromUI(columnOrden, (direccionOrden == null || direccionOrden.equals("")) ? "asc" : direccionOrden);
		}

		String alerta = getWins().anyAlert(-1);
		if (alerta != null)
			setAlert(alerta); // Warning referidos a los filtros
		if (!getWins().canReadAll(sourceAction) && !this.isForceRealAll(zContent))
			return true;
//		if (isWinListFullExportRequest && hasGraph()) {
//			getWins().preGraphicRead();
//		}
		isPreview = getWins().GetVision().equals("PREVIEW");
		if (isPreview) 
			getWins().setPagesize(10);
		else
			getWins().setPagesize(-1);
		try {
			getWins().ReRead();
		} catch (Exception e) {
			infoExtra="Error consulta: ["+e.getMessage()+"]";
			return false;
		}
		// calculateTotalCount();
		// alerta = getWins().anyAlert(totalCountElement);
		// if (alerta!=null) setAlert(alerta); // warning referidos a los
		// resultados, paso el tamaño para no volver a calcularlo
		return true;
	}

	// top 10, pero controlando que los empatados con el ultimo tambien queden
	private void select10(JDataMatrix[] rank,JDataMatrix valor,boolean top) {
		int i;
	
		for (i=0;i<10;i++) {
			if (rank[i]==null) break;
			double v = rank[i].getValorOrdenador();
			if (top) {
				if (v<valor.getValorOrdenador()) break;
			}else {
				if (v>valor.getValorOrdenador()) break;
			}
		}
		if (i==10 && rank[i-1].getValorOrdenador()!=valor.getValorOrdenador()) {
			return;
		}     
	
		    
		  
		JDataMatrix backupRank;
		for (;i<100;i++) {
			backupRank=rank[i];
			rank[i]=valor;
			valor=backupRank;
			if (valor==null) break;
		}
		if (rank[9]!=null && rank[10]!=null) {
			if (rank[9].getValorOrdenador()!=rank[10].getValorOrdenador()) {
				for (i=10;i<100;i++) {
					rank[i]=null;
				}
			}
		}
	}
	SortedMap<String,JColumnaMatrix> calculateMatrixWithingData() throws Exception {
  	SortedMap<String,JColumnaMatrix> map = new TreeMap<String, JColumnaMatrix>();
		columnas = JCollectionFactory.createOrderedMap();
		matrix = JCollectionFactory.createOrderedMap();
		JMap<Long, JOrdenEjeMatrix> gruposEjes = JCollectionFactory.createOrderedMap();
		JMap<Long, String> gruposTitles = JCollectionFactory.createOrderedMap();
		JMap<Long, String> gruposTotalesEjes = JCollectionFactory.createOrderedMap();
		boolean fillGrupoEjes = true;
		if (winMatrix.GetEjesMatrix()==null) return map;
		boolean displayTotales = false;
		JWin win = getWins().getWinRef();
		
		JMap<Long, String> grupos = JCollectionFactory.createOrderedMap();
		long col=1;
		String idColumna = "";
		String idFila = "";
		long level = 0;
		JIterator<JEjeMatrix> it = winMatrix.GetEjesMatrix().getIterator();
		while (it.hasMoreElements()) {
			JEjeMatrix eje = it.nextElement();
			if (eje.isColumna()) {
				grupos.addElement(level,eje.getValue(win).toFormattedString());
				gruposEjes.addElement(level,new JOrdenEjeMatrix(eje));
				if (fillGrupoEjes) {
					gruposTitles.addElement(level, eje.GetColumnaTitulo());
					gruposTotalesEjes.addElement(level, level==0?"TOTALES":"");
				}
				level++;
				idColumna += (idColumna.equals("") ? "" : "_") + eje.getValue(win);
				displayTotales=true;
			}
			if (eje.isFila()) {
				idFila += (idFila.equals("") ? "" : "_") + "Sin datos";

			}
		}
		idFila = buildCampoName(idFila);
//		if (idColumna.equals("")) idColumna = ""+(col++);
		boolean isNew=false;
		JFilaMatrix fila = matrix.getElement(idFila);
		if (fila == null) {
			fila = new JFilaMatrix();
			fila.setClave(idFila);
			matrix.addElement(idFila, fila);
			isNew=true;
		}

		it = winMatrix.GetEjesMatrix().getIterator();
		while (it.hasMoreElements()) {
			JEjeMatrix eje = it.nextElement();
			if (eje.isColumna()) {
				continue;
			}
			String idCol = "!         "+eje.getLevel()+"_"+eje.GetCampo();
			JDataMatrix data = fila.getData(idCol);
			if (data == null) {
				data = new JDataMatrix();
				fila.addData(idCol, data);
			}
			data.setColumna(idCol);
			data.setWin(win);
			data.setType(eje.getValue(win).getClass());
			data.setValue("Sin datos");
			//data.setKey(win.getRecord().getPropAsString(win.getKeyField())+"_"+data.getValue());
			if (isNew) {
				if (eje.getOldFila()!=null && eje.getOldFila().getValue().equals(data.getValue())) {
				  data.setVisible(false);
					eje.getOldFila().setRowspan(eje.getOldFila().getRowspan()+1);
				}	else eje.setOldFila(data);
			}
			data.setColspan(win.getFieldColspan(eje.GetCampo()));

			JColumnaMatrix colMatrix = map.get(idCol);
			if (colMatrix == null) {
				colMatrix = new JColumnaMatrix();
				colMatrix.setClave(idCol);
				colMatrix.setTitulo(eje.GetColumnaTitulo());
				colMatrix.setAlignment(JColumnaLista.ALIGNMENT_LEFT);
				colMatrix.setClase(data.getType());
				colMatrix.setEje(true);
				colMatrix.setGrupos(gruposTitles);
				colMatrix.setGruposEjes(gruposEjes);
				map.put(idCol, colMatrix);
			}
		}

		fillGrupoEjes = false;

		JIterator<JColumnaLista> itc = winMatrix.GetColumnasLista().getIterator();
		while (itc.hasMoreElements()) {
			JColumnaLista dato = itc.nextElement();
			if (!dato.isMatrix()) continue;
			String campo = dato.GetCampo();
			String idCol = (idColumna + (idColumna.equals("") ? "" : "_") +JTools.lpad(""+(col++),"0",3)+"_"+ campo);

			JDataMatrix data = fila.getData(idCol);
			if (data == null) {
				data = new JDataMatrix();
				fila.addData(idCol, data);
			}
			data.setColumna(idCol);
			data.setWin(win);
			if (campo.equals("")) {
				data.setType(JObject.JSTRING);
				data.setValue("");
			} else {
				JObject obj=win.getRecord().getPropDeep(campo,false);
				data.setType(obj==null?JObject.JSTRING:obj.getObjectType());
				data.setValue("");
			}
			data.setColorBackground(dato.getColorBackground());
			data.setColorForeground(dato.getColorForeground());
			data.setColspan(win.getFieldColspan(campo));

			JColumnaMatrix colMatrix = map.get(idCol);
			if (colMatrix == null) {
				colMatrix = new JColumnaMatrix();
				colMatrix.setGrupos(grupos);
				colMatrix.setClave(idCol);
				colMatrix.setTitulo(dato.GetTituloWithFunction());
				colMatrix.setAlignment(dato.getAlignment());
				colMatrix.setClase(data.getType());
				colMatrix.setCampo(dato.GetCampo());
				colMatrix.setColorBackground(dato.getColorBackground());
				colMatrix.setColorForeground(dato.getColorForeground());
				colMatrix.setFunction(dato.getFunctionTotalizadora());
				colMatrix.setPorcentaje(dato.isPorcentaje());
				colMatrix.setEje(false);
				colMatrix.setGrupos(grupos);
				map.put(idCol, colMatrix);
			}
			JObject value = new JString("Sin datos");
			
		} 
		return map;
	}	
	
	void calculateMatrix() throws Exception {
		new WebMatrixCalculo().calcule();
	}
	
	class WebMatrixCalculo {
  	SortedMap<String,JFilaMatrix> mapMatriz = new TreeMap<String, JFilaMatrix>();
  	SortedMap<String,JFilaMatrix> mapFilas = new TreeMap<String, JFilaMatrix>();
  	SortedMap<String,JColumnaMatrix> mapColumnas = new TreeMap<String, JColumnaMatrix>();
		JMap<String, JOrdenEjeMatrix> gruposEjes = JCollectionFactory.createOrderedMap();
		JMap<Long, JOrdenEjeMatrix> localColumnaGruposEjes = JCollectionFactory.createOrderedMap();
		JMap<Long, JOrdenEjeMatrix> localFilaGruposEjes = JCollectionFactory.createOrderedMap();
		JMap<Long, String> gruposTitles = JCollectionFactory.createOrderedMap();
		JMap<Long, String> gruposTotalesEjes = JCollectionFactory.createOrderedMap();
		JMap<Long, String> gruposDiferenciaEjes = JCollectionFactory.createOrderedMap();
		JMap<Long, String> gruposDiferenciaPorcEjes = JCollectionFactory.createOrderedMap();
		boolean fillGrupoEjes = true;
		boolean displayTotales = false;
		boolean withData = false;
		String lastFila;

		JMap<Long, String> grupos=null;
		boolean descartar=false;
		long orden=-1;
		long col=1;
		String idColumna = "";
		String idFila = "";
		String idFilaDec = "";
		long idNumFila=0;
		long level = 0;
		long levelGroupFilas = 0;
		long levelFilas = 0;
//		boolean otros=false;
		boolean isNew=false;
		JFilaMatrix fila;
		String lastCampo="";
		
		void calculePuntoDeCorte(JWin win) throws Exception {
			JIterator<JOrdenMatrix> itO = winMatrix.GetOrdenMatrix().getValueIterator();
			while (itO.hasMoreElements()) {
				JOrdenMatrix ordenM = itO.nextElement();
				if (ordenM.getLimite()<=0) continue;
				
				orden = ordenM.getValueRank(win);
				descartar = (orden>ordenM.getLimite()); 
				if (!isPreview && descartar) break;
				
			}
		}

		JOrdenEjeMatrix getOrdenEjeMatrix(String tipo, long level,String title,JEjeMatrix eje,String clave) throws Exception {
			String key = tipo+JTools.LPad(""+level, 10, "0")+"_"+title+"_"+eje.GetCampo();

			JOrdenEjeMatrix orden =  gruposEjes.getElement(key);
			if (orden==null) {
				orden = new JOrdenEjeMatrix(eje);
				gruposEjes.addElement(key, orden);
				orden.setClave(clave);
			} 

			return orden;
		}
		void fillGrupos(JWin win) throws Exception {
			JEjeMatrix lc= null;
			String idFilaRaiz="";
			JIterator<JEjeMatrix> it = winMatrix.GetEjesMatrix().getIterator();
			while (it.hasMoreElements()) {
				JEjeMatrix eje = it.nextElement();
//				if (eje.getLimite()>0) {
//					orden = eje.getValueRank(win);
//					descartar = (orden>eje.getLimite()); 
//					if (!isPreview &&descartar) break;
//				}
				if (eje.isColumna()) {
					String title = eje.getValue(win).toFormattedString();
					grupos.addElement(level, title);
					if (fillGrupoEjes) {
						gruposTitles.addElement(level, eje.GetColumnaTitulo());
						gruposTotalesEjes.addElement(level, level==0?"TOTALES":"");
					}
					if (eje.getEje()!=null && eje.getEje().getCalculeDiferencia() && level==0) {
						gruposDiferenciaEjes.addElement(level, "DIFERENCIA "+eje.getEje().getObjCampoDiferencia().getDescrCampo());
						if (eje.getEje().getPorcentajeDiferencia())
							gruposDiferenciaPorcEjes.addElement(level, "DIF.PORCENTAJE "+eje.getEje().getObjCampoDiferencia().getDescrCampo());
					}
					level++;
					idColumna += (idColumna.equals("") ? "" : "_") + JTools.LPad(""+eje.getValue(win),60,"0");
					localColumnaGruposEjes.addElement(level, getOrdenEjeMatrix("C",level, idColumna, eje,JTools.LPad(""+eje.getValue(win),60,"0")));
					if (!eje.GetTitulo().equals("Agregue Columna"))//columna falsa agregada para facilitar edicion
						displayTotales=true;
				}
				if (eje.isFila()) {
					String title=eje.getValue(win).toString();
					idFilaRaiz = idFila;
					idFila += (idFila.equals("") ?"" : "_")  + JTools.LPad(""+title,60,"0");
					levelGroupFilas++;
					localFilaGruposEjes.addElement(levelGroupFilas, getOrdenEjeMatrix("F",levelGroupFilas, idFila, eje, JTools.LPad(""+title,60,"0")));
					lc = eje;
				}
				
			}

			idFilaDec=idFila;
			addSubtotal(false);
//			if (lc!=null) {
//				otros = isGroupToOther(lc);
//				if (otros)
//					idFila = idFilaRaiz+(idFilaRaiz.equals("") ?"" : "_") + JTools.LPad("zzzzzzzOtros",60,"0");
//				lastCampo=lc.GetCampo();
//			}
		}
		
//		boolean isGroupToOther(JEjeMatrix eje) throws Exception {
//			if (getWins().getMaxToOthers() <=0) return false;
//			return levelFilas>=getWins().getMaxToOthers() && eje.GetCampo().equals(lastCampo);
//			return false;
//		}
	
		void fillColumnas(JWin win) throws Exception {
			JIterator<JEjeMatrix> it = winMatrix.GetEjesMatrix().getIterator();
			String dataAcum="";
			it = winMatrix.GetEjesMatrix().getIterator();
			while (it.hasMoreElements()) {
				JEjeMatrix eje = it.nextElement();
				if (eje.isColumna()) continue;
				
				String idCol = "!         "+eje.getLevel()+"_"+eje.GetCampo();
				JDataMatrix data = fila.getData(idCol);
				if (data == null) {
					data = new JDataMatrix();
					fila.addData(idCol, data);
				}
				data.setEje(eje);
				data.setColumna(idCol);
				data.setWin(win);
				data.setType(eje.getValue(win).getClass());
//				if (data.getValue()!=null && lastCampo!=null && eje.GetCampo().equals(lastCampo)) {
//					if (!data.getValue().equals("Otros")) {
//							if (!(data.getValue().equals(eje.getValue(win).toFormattedString())))
//							data.setValue("Otros");
//					}
//					if (!data.getValueAcum().endsWith("zzzzzzOtros")) {
//						dataAcum+=(dataAcum.equals("")?"":"_")+"zzzzzzOtros";
//						data.setValueAcum(dataAcum);
//					}
//				} else {
					data.setValue(eje.getValue(win).toFormattedString());
					dataAcum+=(dataAcum.equals("")?"":"_")+data.getValue();
					data.setValueAcum(dataAcum);
//				}
				//data.setKey(win.getRecord().getPropAsString(win.getKeyField())+"_"+data.getValue());
//				if (isNew) {
//					if (eje.getOldFila()!=null && eje.getOldFila().getValueAcum().equals(data.getValueAcum())) {
//					  data.setVisible(false);
//						eje.getOldFila().setRowspan(eje.getOldFila().getRowspan()+1);
//					}	else eje.setOldFila(data);
//				}
				data.setColspan(win.getFieldColspan(eje.GetCampo()));
				JColumnaMatrix colMatrix = mapColumnas.get(idCol);
				if (colMatrix == null) {
					colMatrix = new JColumnaMatrix();
					colMatrix.setClave(idCol);
					colMatrix.setTitulo(eje.GetColumnaTitulo());
					colMatrix.setAlignment(JColumnaLista.ALIGNMENT_LEFT);
					colMatrix.setClase(data.getType());
					colMatrix.setEje(true);
					colMatrix.setCampo(eje.GetCampo());
					colMatrix.setGrupos(gruposTitles);
					mapColumnas.put(idCol, colMatrix);
				}
				if (hasSubTotalizador())
					fillSubTotalHeader(win,idCol,eje.GetCampo());
			}
		}
		void fillFila(JWin win) throws Exception {
			idFila = buildCampoName(idFila);
			
			lastFila=idFila;
//		if (idColumna.equals("")) idColumna = ""+(col++);
			isNew=false;
			fila = mapFilas.get(idFila);
			if (fila == null) {
				fila = new JFilaMatrix();
//				PssLogger.logInfo("FILA: "+idFilaDec);
				fila.setClave(idFila);
				fila.setGruposEjes(localFilaGruposEjes);
				mapFilas.put(idFila, fila);
				levelFilas++;
				isNew=true;
				lastFila=idFila;
			}
		}
		
		boolean isContable(JColumnaLista dato,JObject value) throws Exception {
			return (
					!dato.getFunctionTotalizadora().equals(BizCampo.FUNTION_LIT) &&
					!dato.getFunctionTotalizadora().equals(BizCampo.FUNTION_ANIO) &&
					!dato.getFunctionTotalizadora().equals(BizCampo.FUNTION_ANOMES) &&
					!dato.getFunctionTotalizadora().equals(BizCampo.FUNTION_ANOSEM) &&
					!dato.getFunctionTotalizadora().equals(BizCampo.FUNTION_BIMESTRE) &&
					!dato.getFunctionTotalizadora().equals(BizCampo.FUNTION_TRIMESTRE) &&
					!dato.getFunctionTotalizadora().equals(BizCampo.FUNTION_CUATRIMESTRE) &&
					!dato.getFunctionTotalizadora().equals(BizCampo.FUNTION_SEMESTRE) &&
					!dato.getFunctionTotalizadora().equals(BizCampo.FUNTION_DIA_MES) &&
					!dato.getFunctionTotalizadora().equals(BizCampo.FUNTION_DIA_SEMANA) &&
					!dato.getFunctionTotalizadora().equals(BizCampo.FUNTION_MES) && 
					value != null && !value.toString().equals("") && 
					((dato.getFunctionTotalizadora().equals(BizCampo.FUNTION_COUNT)||
							dato.getFunctionTotalizadora().equals(BizCampo.FUNTION_AVR)||
							dato.isPorcentaje()||
							dato.getFunctionTotalizadora().equals(BizCampo.FUNTION_SUM) 
							|| (value.isCurrency() || value.isFloat() || value.isInteger() || value.isLong() || (value.isString()&&((JString)value).getFloatValue()!=0)))));
		}

		void processAcumuladores(JWin win,JColumnaMatrix colMatrix,JDataMatrix data,JColumnaLista dato,JObject value, double actualValue) throws Exception {
			double thisValue = actualValue;
			if (dato.isPorcentaje()) colMatrix.setValor(colMatrix.getValor() + thisValue);
			else if (dato.getFunctionTotalizadora().equals(BizCampo.FUNTION_MAX)) colMatrix.setValor(colMatrix.getValor()>thisValue?colMatrix.getValor():thisValue);
			else if (dato.getFunctionTotalizadora().equals(BizCampo.FUNTION_MIN)) colMatrix.setValor(colMatrix.getValor()<thisValue?colMatrix.getValor():thisValue);
			else if (dato.getFunctionTotalizadora().equals(BizCampo.FUNTION_SUM)) colMatrix.setValor(colMatrix.getValor() + thisValue);
			else if (dato.getFunctionTotalizadora().equals(BizCampo.FUNTION_COUNT)) colMatrix.setValor(colMatrix.getValor() + thisValue);
			else if (dato.getFunctionTotalizadora().equals(BizCampo.FUNTION_AVR)) colMatrix.setValor(colMatrix.getValor() + thisValue);
			else  colMatrix.setValor(colMatrix.getValor() + thisValue);
			if (dato.isMarcaMayores() && dato.isMarcaTop10()) select10(dato.getTop10(), data, true);
			if (dato.isMarcaMenores() && dato.isMarcaBottom10()) select10(dato.getBottom10(), data, false);
			if (dato.isMarcaMayores() && !dato.isMarcaTop10() && !dato.getMayoresA().equals("") && JTools.isNumber(dato.getMayoresA())) {
				if (Float.parseFloat(value.toString())>=Float.parseFloat(dato.getMayoresA())) data.setColorBackground(dato.getColorTopBackground());
			}
			if (dato.isMarcaMenores() && !dato.isMarcaBottom10() && !dato.getMenoresA().equals("") && JTools.isNumber(dato.getMenoresA())) {
				if (Float.parseFloat(value.toString())<=Float.parseFloat(dato.getMenoresA())) data.setColorBackground(dato.getColorBottomBackground());
			}
			colMatrix.setRankOrder(win,dato, data,actualValue);
			fila.setRankOrder(win,dato, data,actualValue);
		}

		void processAcumuladoresTotales(JColumnaMatrix colMatrixT,JDataMatrix dataTotales,JColumnaMatrix colMatrix,JColumnaLista dato,JObject value) throws Exception {
			if (dato.isPorcentaje()) dataTotales.setAcumulador(dataTotales.getAcumulador()+ dataTotales.getValorOrdenador());
			else if (dato.getFunctionTotalizadora().equals(BizCampo.FUNTION_MAX)) dataTotales.setAcumulador(dataTotales.getAcumulador()>dataTotales.getValorOrdenador()?dataTotales.getAcumulador():dataTotales.getValorOrdenador());
			else if (dato.getFunctionTotalizadora().equals(BizCampo.FUNTION_MIN)) dataTotales.setAcumulador(dataTotales.getAcumulador()<dataTotales.getValorOrdenador()?dataTotales.getAcumulador():dataTotales.getValorOrdenador());
			else if (dato.getFunctionTotalizadora().equals(BizCampo.FUNTION_SUM)) dataTotales.setAcumulador(dataTotales.getAcumulador()+ dataTotales.getValorOrdenador());
			else if (dato.getFunctionTotalizadora().equals(BizCampo.FUNTION_COUNT)) dataTotales.setAcumulador(dataTotales.getAcumulador()+ dataTotales.getValorOrdenador());
			else if (dato.getFunctionTotalizadora().equals(BizCampo.FUNTION_AVR)) dataTotales.setAcumulador(dataTotales.getAcumulador()+ dataTotales.getValorOrdenador());
			else dataTotales.setAcumulador(dataTotales.getAcumulador()+ dataTotales.getValorOrdenador());
			
			dataTotales.setCantidad(dataTotales.getCantidad()+1);

			if (dato.isPorcentaje()) dataTotales.setValue(new JFloat(dataTotales.getAcumulador()).setPrec(2).toFormattedString());
			else if (dato.getFunctionTotalizadora().equals(BizCampo.FUNTION_AVR)) dataTotales.setValue(new JFloat(dataTotales.getAcumulador()/dataTotales.getCantidad()).setPrec(2).toFormattedString());
			else if (value.isFloat()||value.isCurrency()) dataTotales.setValue(new JFloat(dataTotales.getAcumulador()).setPrec(2).toFormattedString());
			else	dataTotales.setValue(new JLong((long)dataTotales.getAcumulador()).toFormattedString());
			colMatrixT.setCantidad(colMatrixT.getCantidad() + 1);
			
			if (dato.isPorcentaje()) colMatrixT.setValor(colMatrixT.getValor() + dataTotales.getValorOrdenador());
			else if (dato.getFunctionTotalizadora().equals(BizCampo.FUNTION_MAX)) colMatrixT.setValor(colMatrixT.getValor()>dataTotales.getValorOrdenador()?colMatrix.getValor():dataTotales.getValorOrdenador());
			else if (dato.getFunctionTotalizadora().equals(BizCampo.FUNTION_MIN)) colMatrixT.setValor(colMatrixT.getValor()<dataTotales.getValorOrdenador()?colMatrix.getValor():dataTotales.getValorOrdenador());
			else if (dato.getFunctionTotalizadora().equals(BizCampo.FUNTION_SUM)) colMatrixT.setValor(colMatrixT.getValor() + dataTotales.getValorOrdenador());
			else if (dato.getFunctionTotalizadora().equals(BizCampo.FUNTION_COUNT)) colMatrixT.setValor(colMatrixT.getValor() + dataTotales.getValorOrdenador());
			else if (dato.getFunctionTotalizadora().equals(BizCampo.FUNTION_AVR)) colMatrixT.setValor(colMatrixT.getValor() + dataTotales.getValorOrdenador());
			else colMatrixT.setValor(colMatrixT.getValor() + dataTotales.getValorOrdenador());
			dataTotales.setValorOrdenador(dataTotales.getAcumulador());
		}



		JDataMatrix getCelda(JFilaMatrix f,JWin win,String idCol,String campo,JColumnaLista dato) throws Exception {
			JDataMatrix data = f.getData(idCol);
			if (data == null) {
				data = new JDataMatrix();
				f.addData(idCol, data);
			}
			JObject obj = win.getRecord().getProp(campo,false,false);
			if (obj==null) return null;
//			data.setEje(eje);
			data.setColumna(idCol);
			data.setWin(win);
			if (campo.equals("")) {
				data.setType(JObject.JSTRING);
			}	else {
				data.setType(obj.getObjectType());
			}			
			data.setColorBackground(dato.getColorBackground());
			data.setColorForeground(dato.getColorForeground());
			data.setColspan(win.getFieldColspan(campo));
			return data;
		}

		JColumnaMatrix getColumna(JWin win,String idCol,String campo,JColumnaLista dato,JDataMatrix data) throws Exception {
			JColumnaMatrix colMatrix = mapColumnas.get(idCol);
			if (colMatrix == null) {
				colMatrix = new JColumnaMatrix();
				colMatrix.setGrupos(grupos);
				colMatrix.setGruposEjes(localColumnaGruposEjes);
				mapColumnas.put(idCol, colMatrix);
			}
			colMatrix.setClave(idCol);
			colMatrix.setTitulo(dato.GetTituloWithFunction());
			colMatrix.setAlignment(dato.getAlignment());
			colMatrix.setClase(data.getType());
			colMatrix.setCampo(dato.GetCampo());
			colMatrix.setColorBackground(dato.getColorBackground());
			colMatrix.setColorForeground(dato.getColorForeground());
			colMatrix.setFunction(dato.getFunctionTotalizadora());
			colMatrix.setPorcentaje(dato.isPorcentaje());
			colMatrix.setEje(false);
			colMatrix.setGrupos(grupos);
			colMatrix.setGruposEjes(localColumnaGruposEjes);
			return colMatrix;
		}
		JDataMatrix getDiferencia(String idColT) throws Exception {
			JDataMatrix dataTotales = fila.getData(idColT);
			if (dataTotales == null) {
				dataTotales = new JDataMatrix();
				fila.addData(idColT, dataTotales);
			}
			// ver la funcion...
			dataTotales.setType(JObject.JFLOAT);
			return dataTotales;
		}
		JColumnaMatrix getDiferenciaColumna(JMap grupo,String titulo,String idColT,JColumnaLista dato,JDataMatrix dataTotales) throws Exception {
			JColumnaMatrix colMatrixT = mapColumnas.get(idColT);
			if (colMatrixT == null) {
				colMatrixT = new JColumnaMatrix();
				colMatrixT.setClave(idColT);
				colMatrixT.setTitulo(titulo);
				colMatrixT.setAlignment(JColumnaLista.ALIGNMENT_RIGHT);
				colMatrixT.setClase(dataTotales.getType());
				colMatrixT.setFunction("");
				colMatrixT.setPorcentaje(false);
				colMatrixT.setEje(false);
				colMatrixT.setXdif(true);
				colMatrixT.setGrupos(grupo);
				colMatrixT.setGruposEjes(localColumnaGruposEjes);
				mapColumnas.put(idColT, colMatrixT);					

			} 
			return colMatrixT;
		}
		JDataMatrix getTotalizador(JWin win,String idColT,String campo,JColumnaLista dato,JDataMatrix data) throws Exception {
			JDataMatrix dataTotales = fila.getData(idColT);
			if (dataTotales == null) {
				dataTotales = new JDataMatrix();
				fila.addData(idColT, dataTotales);
			}
			dataTotales.setColumna(idColT);
			// ver la funcion...
			dataTotales.setType(data.getType());
			return dataTotales;
		}
		JColumnaMatrix getTotalizadorColumna(JWin win,String idColT,String campo,JColumnaLista dato,JDataMatrix dataTotales) throws Exception {
			JColumnaMatrix colMatrixT = mapColumnas.get(idColT);
			if (colMatrixT == null) {
				colMatrixT = new JColumnaMatrix();
				colMatrixT.setClave(idColT);
				colMatrixT.setTitulo(dato.GetTituloWithFunction());
				colMatrixT.setAlignment(JColumnaLista.ALIGNMENT_RIGHT);
				colMatrixT.setClase(dataTotales.getType());
				colMatrixT.setFunction(dato.getFunctionTotalizadora());
				colMatrixT.setPorcentaje(dato.isPorcentaje());
				colMatrixT.setEje(true);
				colMatrixT.setGrupos(gruposTotalesEjes);
				mapColumnas.put(idColT, colMatrixT);				
	
			} 
			return colMatrixT;
		}
		boolean hasTotalizador(JColumnaLista infoColumna) throws Exception {
			if (!getWins().matrixDisplayTotales()) return false;
			if (!displayTotales) return false;
			if (infoColumna!=null && !Number.class.isAssignableFrom(infoColumna.GetClase())) return false;

			return true;

		}
		boolean hasSubTotalizador() throws Exception {
			if (!getWins().matrixDisplaySubTotales()) return false;
			if (!getWins().matrixDisplayTotales()) return false;
			return true;

		}
		void fillCeldas(JWin win) throws Exception {
			fillGrupoEjes = false;
			// llena los campos
			JIterator<JColumnaLista> itc = winMatrix.GetColumnasLista().getIterator();
			while (itc.hasMoreElements()) {
				JColumnaLista infoColumna = itc.nextElement();
				if (!infoColumna.isMatrix()) continue;
				
				String campo = infoColumna.GetCampo();
				String idCol = (idColumna + (idColumna.equals("") ? "" : "_") +JTools.lpad(""+(col++),"0",3)+"_"+ campo);


				JDataMatrix data = getCelda(fila,win,idCol,campo,infoColumna);
				if (data==null) continue;
				JColumnaMatrix colMatrix = getColumna(win, idCol, campo, infoColumna,data);
		
				JObject value;
				if (campo.equals("")) value = new JString("");
				else value = win.getRecord().getPropRaw(campo);
				
				if (campo.equals("")) {
					data.setValue("");
				}	else {
					if (isContable(infoColumna, value)) {
						double valorContable=((value.isString()?((JString)value).getFloatValue():Float.parseFloat(value.toString())));
						data.setType(win.getRecord().getPropRaw(campo).getObjectType());
						data.setValorOrdenador(data.getValorOrdenador()+valorContable);
						colMatrix.setCantidad(colMatrix.getCantidad() + 1);
						int prec = win.getRecord().getPropRaw(campo).getCustomPrecision();
						String valor=win.getRecord().getPropRaw(campo).toFormattedString();
						if (valor.indexOf("<")!=-1)
							data.setValue(win.getRecord().getPropRaw(campo).toFormattedString());
						else
							data.setValue(JTools.toPrinteableNumber(data.getValorOrdenador(),prec<0?2:prec));
						processAcumuladores(win,colMatrix,data,infoColumna, value,valorContable);
					} else {
						data.setValue(win.getRecord().getPropRaw(campo).toFormattedString());
						colMatrix.setRankOrder(win,infoColumna, data,value.toString());
						fila.setRankOrder(win,infoColumna, data,value.toString());
				}
				}
				
				JIterator<JEjeMatrix> it = winMatrix.GetEjesMatrix().getIterator();
				while (it.hasMoreElements()) {
					JEjeMatrix eje = it.nextElement();
					if (eje.getEje()==null) continue;
					if (!eje.getEje().getCalculeDiferencia()) continue;
					if (!campo.equals(eje.getEje().getObjCampoDiferencia().getNameField())) continue;

					JObject dif = win.getRecord().getProp("xdiffval_"+eje.getEje().getNameField(),false);

					if (dif!=null&&dif.getObjectValue()!=null) {
						JObject difTitle = win.getRecord().getPropRaw("xdiffcol_"+eje.getEje().getNameField());
						String newTitulo =difTitle.toString();
						String idColD = "zzzzzzzzzzzzzz_DIFERENCIA_" + newTitulo;
						JDataMatrix dataDif = getDiferencia( idColD );
						JColumnaMatrix colMatrixD =getDiferenciaColumna(gruposDiferenciaEjes,newTitulo,idColD, infoColumna, dataDif);
						
						if (isContable(infoColumna, dif)) {
							dataDif.setValorOrdenador(dif.isString()?((JString)dif).getFloatValue():Float.parseFloat(dif.toString()));
							processAcumuladoresTotales(colMatrixD, dataDif, colMatrixD, infoColumna, dif);
						}

					}

					if (eje.getEje().getPorcentajeDiferencia()) {
						JObject porcdif = win.getRecord().getProp("xdiffprc_"+eje.getEje().getNameField(),false);

						if (porcdif!=null&&porcdif.getObjectValue()!=null) {
							JObject difTitle = win.getRecord().getPropRaw("xdiffcol_"+eje.getEje().getNameField());
							String newTitulo = difTitle.toString();
							String idColD = "zzzzzzzzzzzzzz_DIFEPORCE_" + newTitulo;
							JDataMatrix dataDif = getDiferencia( idColD );
							JColumnaMatrix colMatrixD =getDiferenciaColumna(gruposDiferenciaPorcEjes,newTitulo,idColD, infoColumna, dataDif);
							
							if (isContable(infoColumna, porcdif)) {
								dataDif.setValorOrdenador(porcdif.isString()?((JString)porcdif).getFloatValue():Float.parseFloat(porcdif.toString()));
								processAcumuladoresTotales(colMatrixD, dataDif, colMatrixD, infoColumna, porcdif);
							}

						}
						
					}


				}
				
				if (hasSubTotalizador())
					fillSubTotalCeldas(win, idCol, campo, infoColumna, data, value);
				
				if (!hasTotalizador(infoColumna)) continue;

				String idColT = "zzzzzzzzzzzzzz_TOTALES_" + campo;
				JDataMatrix dataTotales = getTotalizador(win, idColT, campo, infoColumna, data);
				JColumnaMatrix colMatrixT =getTotalizadorColumna(win, idColT, campo, infoColumna, dataTotales);
				
				if (isContable(infoColumna, value)) {
					dataTotales.setValorOrdenador(value.isString()?((JString)value).getFloatValue():Float.parseFloat(value.toString()));
					processAcumuladoresTotales(colMatrixT, dataTotales, colMatrixT, infoColumna, value);
				}
			} 

		}

		void fillSubTotalHeader(JWin win,String idCol,String campo) throws Exception {
			String idFilaST="";
			JFilaMatrix st = null;
			JIterator<JEjeMatrix> it = winMatrix.GetEjesMatrix().getIterator();
			while (it.hasMoreElements()) {
				JEjeMatrix eje = it.nextElement();
				String idColumn = "!         "+eje.getLevel()+"_"+eje.GetCampo();
				if (eje.isFila()) {
					if (!idFilaST.equals("")) {
						st = fillFilaSubtotal(win,idColumn,eje.GetCampo(),idFilaST,idFilaST+"_zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzsubtotal");
						
					}
					idFilaST += (idFilaST.equals("") ?"" : "_") + JTools.LPad(""+eje.getValue(win),60,"0");
				} 
				
			}
			
		}

		void fillSubTotalHeaderComplete(JWin win,String campo,JFilaMatrix st ) throws Exception {
			boolean vacio =false;
			String dataAcum="";
			JIterator<JEjeMatrix> it = winMatrix.GetEjesMatrix().getIterator();
			while (it.hasMoreElements()) {
				JEjeMatrix eje = it.nextElement();

				if (eje.isFila()) {
					String idCol = "!         "+eje.getLevel()+"_"+eje.GetCampo();
					if (eje.GetCampo().equals(campo)) {
					  JDataMatrix dataTotales = st.getData(idCol);
						if (dataTotales == null) {
							dataTotales = new JDataMatrix();
							st.addData(idCol, dataTotales);
						}
						dataTotales.setColumna(idCol);
						dataTotales.setType(JObject.JSTRING);
						dataTotales.setValue("Sub-total");
						dataAcum+=(dataAcum.equals("")?"":"_")+dataTotales.getValue();
						dataTotales.setValueAcum(dataAcum);
						dataTotales.setClassResponsive("subtotal");
						vacio=true;
						
					} else {
						JDataMatrix dataTotales = st.getData(idCol);
						if (dataTotales == null) {
							dataTotales = new JDataMatrix();
							st.addData(idCol, dataTotales);
						}
						dataTotales.setColumna(idCol);
						dataTotales.setType(JObject.JSTRING);
						dataTotales.setClassResponsive("subtotal");
						if (vacio) {
							dataTotales.setValue("");
							dataAcum+=(dataAcum.equals("")?"":"_")+dataTotales.getValue();
							dataTotales.setValueAcum(dataAcum);
							
						} else {
							dataTotales.setValue(eje.getValue(win).toFormattedString());
							dataAcum+=(dataAcum.equals("")?"":"_")+dataTotales.getValue();
							dataTotales.setValueAcum(dataAcum);
							
						}
//						eje.setOldFila(dataTotales);
					}
				} 
				
			}
			
		}
		void insertSubTotalHeaderComplete(String idSt,JFilaMatrix st ) throws Exception {
//			PssLogger.logInfo("FILA: "+idSt);
			String idFl = buildCampoName(idSt);
			
			JIterator<JEjeMatrix> it = winMatrix.GetEjesMatrix().getIterator();
			while (it.hasMoreElements()) {
				JEjeMatrix eje = it.nextElement();
				String idCol = "!         "+eje.getLevel()+"_"+eje.GetCampo();
			  JDataMatrix dataTotales = st.getData(idCol);
			  if (dataTotales==null) continue;
//				if (eje.getOldFila()!=null && eje.getOldFila().getValueAcum().equals(dataTotales.getValueAcum())) {
//					dataTotales.setVisible(false);
//					eje.getOldFila().setRowspan(eje.getOldFila().getRowspan()+1);
//				}	else eje.setOldFila(dataTotales);
	  	}
			mapFilas.put(idFl,st);
			
		}

		void fillSubTotalCeldas(JWin win,String idCol,String campo,JColumnaLista dato,JDataMatrix data,JObject value) throws Exception {
			String idFilaST="";
			JFilaMatrix st = null;
			JIterator<JEjeMatrix> it = winMatrix.GetEjesMatrix().getIterator();
			while (it.hasMoreElements()) {
				JEjeMatrix eje = it.nextElement();
				if (eje.isFila()) {
					if (!idFilaST.equals("")) {
					  st = fillFilaSubtotal(win,idCol,campo,idFilaST,idFilaST+"_zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzsubtotal");
						
						JDataMatrix dataTotales = st.getData(idCol);
						if (dataTotales == null) {
							dataTotales = new JDataMatrix();
							st.addData(idCol, dataTotales);
						}
						dataTotales.setColumna(idCol);
						dataTotales.setType(data.getType());
						dataTotales.setClassResponsive("subtotal");
						if (isContable(dato, value)) {
							dataTotales.setValorOrdenador( dataTotales.getValorOrdenador()+(value.isString()?((JString)value).getFloatValue():Float.parseFloat(value.toString())));
							dataTotales.setValue(JTools.formatDouble(dataTotales.getValorOrdenador()));


						} 
						
					}
					idFilaST += (idFilaST.equals("") ?"" : "_") + JTools.LPad(""+eje.getValue(win),60,"0");
				} 
				
			}

		}
	
		void addSubtotal(boolean end) throws Exception {
	
			
			JMap<String, JFilaMatrix> matrixNew =  JCollectionFactory.createOrderedMap();
			matrixNew.MapToJMap(mapMatriz);
			JIterator<String> it = matrixNew.getKeyIterator();
			while(it.hasMoreElements()) {
				String key = it.nextElement();
				JFilaMatrix m = matrixNew.getElement(key);
			  if (end || !idFilaDec.startsWith(m.getRaiz())) {
			  	levelFilas=0;
			  	insertSubTotalHeaderComplete(key, m);
			  	mapMatriz.remove(key);
			  }
			}

		}

		JMap<Long,JOrdenEjeMatrix> buildGruposEjesForSubtotal(JMap<Long,JOrdenEjeMatrix> parentEjes,String campo) throws Exception {
			JMap<Long,JOrdenEjeMatrix> newEjes = JCollectionFactory.createMap();
			JIterator<Long> it=parentEjes.getKeyIterator();
			while (it.hasMoreElements()) {
				Long key = it.nextElement();
				JOrdenEjeMatrix eje = parentEjes.getElement(key);
				
				if (eje.getEje().GetCampo().equals(campo))
					break;
				
				newEjes.addElement(key, eje);
			}
			return newEjes;
		}		
		
		JFilaMatrix fillFilaSubtotal(JWin win,String idCol,String campo,String raiz, String id) throws Exception {
//			addSubtotal(false);

			JFilaMatrix filaST = mapMatriz.get(id);
			if (filaST == null) {
				filaST = new JFilaMatrix();
				filaST.setClave(id);
				filaST.setRaiz(raiz);
				filaST.setGruposEjes(buildGruposEjesForSubtotal(localFilaGruposEjes,campo));
				filaST.setSubtotal(true);

				mapMatriz.put(id, filaST);
				fillSubTotalHeaderComplete(win,campo,filaST);
			}
			return filaST;
		}

		
		boolean puntoDeCorte() throws Exception {
			return !isPreview && descartar;
		}
		
		void clearVarContextRegister() throws Exception {
			withData=true;
			grupos = JCollectionFactory.createOrderedMap();
			localColumnaGruposEjes = JCollectionFactory.createOrderedMap();
			localFilaGruposEjes = JCollectionFactory.createOrderedMap();
			col=1;
			idColumna = "";
			idFila = "";
			level = 0;
			levelGroupFilas = 0;
			descartar=false;
			orden=-1;
			lastFila="";
		}
		void procesarRegistro(JWin win ) throws Exception {
			clearVarContextRegister();

			calculePuntoDeCorte(win);
			fillGrupos(win);

			if (puntoDeCorte()) return;
			
			fillFila(win);
			fillColumnas(win);
			fillCeldas(win);
			
		}
		void marcarDestacados() throws Exception {
			JIterator<JColumnaLista> itc = winMatrix.GetColumnasLista().getIterator();
			while (itc.hasMoreElements()) {
				JColumnaLista dato = itc.nextElement();
				if (!dato.isMatrix()) continue;
				if (dato.isMarcaMayores() && dato.isMarcaTop10())
					for (int j =0;j<100;j++) {
			 		if (dato.getTop10()[j]==null) break;
			 		dato.getTop10()[j].setColorBackground(dato.getColorTopBackground());
			 	}
			 	if (dato.isMarcaMenores() && dato.isMarcaBottom10())
			 	for (int j =0;j<100;j++) {
			 		if (dato.getBottom10()[j]==null) break;
			 		dato.getBottom10()[j].setColorBackground(dato.getColorBottomBackground());
			 	}
			}
		}
		
		void calcule() throws Exception {
			columnas = JCollectionFactory.createOrderedMap();
			matrix = JCollectionFactory.createOrderedMap();
			if (winMatrix.GetEjesMatrix()==null) return;
			long limiteMax = BizUsuario.getUsr().getObjPreferencias().getMaxMatrix();
			int limite = 0;
			infoExtra=null;
			while (getWins().nextRecord()) {
				JWin win = getWins().getRecord();
				procesarRegistro(win);
				if (isGenerateInternalEvents())
					BizUsuario.eventInterfaz(this.getClass().getCanonicalName(), "Leyendo registro "+limite, limite , limiteMax,  getSourceAction().isCancelable(), null);
				if (limiteMax>0&&limite>limiteMax) {
					infoExtra="ADVERTENCIA! Límite máximo de ["+limiteMax+"] reg. alcanzado, agregue un filtro para afinar la busqueda";
					break;
				}
				limite++;
			}
			addSubtotal(true);
			if (isGenerateInternalEvents())
				BizUsuario.eventInterfaz(this.getClass().getCanonicalName(), "Construyendo grilla", limite , limite+1,  getSourceAction().isCancelable(), null);

			if (infoExtra==null)
				infoExtra="Total registros procesados: "+limite;
			
			if (!withData) 
				mapColumnas = calculateMatrixWithingData();
			else {
				mapColumnas = reordenarColumnas(mapColumnas);
				mapFilas = reordenarFilas(mapFilas);
				mapFilas = reordenarLimites(mapFilas);
				mapFilas = reordenarSpan(mapFilas);
			}
			matrix.MapToJMap(mapFilas);
			columnas.MapToJMap(mapColumnas);
		 	marcarDestacados();
		}

	}
	
	SortedMap<String,JColumnaMatrix> reordenarColumnas(SortedMap<String,JColumnaMatrix> oneMap) throws Exception {
		SortedMap<String,JColumnaMatrix> newMap = new TreeMap<String,JColumnaMatrix>();
		Iterator<String> it = oneMap.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			JColumnaMatrix col = oneMap.get(key);
			String order=col.rebuildRankOrder();
			newMap.put(order==null||order.equals("")?col.getClave():order, col);
		}
		return newMap;
	}
	SortedMap<String,JFilaMatrix> reordenarFilas(SortedMap<String,JFilaMatrix> oneMap) throws Exception {
		SortedMap<String,JFilaMatrix> newMap = new TreeMap<String,JFilaMatrix>();
		Iterator<String> it = oneMap.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			JFilaMatrix fila = oneMap.get(key);
			String order=fila.rebuildRankOrder();
			newMap.put(order==null||order.equals("")?fila.getClave():order, fila);
		}
		return newMap;
	}
	SortedMap<String,JFilaMatrix> reordenarLimites(SortedMap<String,JFilaMatrix> oneMap) throws Exception {
		SortedMap<String,JFilaMatrix> newMap = new TreeMap<String,JFilaMatrix>();
		Iterator<String> it = oneMap.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			JFilaMatrix fila = oneMap.get(key);
			JList<JFilaMatrix> newFilas=fila.limitar(winMatrix);
			JIterator<JFilaMatrix> itf= newFilas.getIterator();
			while (itf.hasMoreElements()) {
				JFilaMatrix newFila = itf.nextElement();
				newMap.put(newFila.getRankOrder(),newFila);
			}
		}
		return newMap;
	}
	SortedMap<String,JFilaMatrix>  reordenarSpan(SortedMap<String,JFilaMatrix> oneMap) throws Exception {
		Iterator<String> it = oneMap.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			JFilaMatrix fila = oneMap.get(key);
			fila.rebuildSpan();
		}
		return oneMap;
	}
	String buildCampoName(String name) throws Exception {
		return /*JTools.AsciiToHex*/(name);
	}

	String[][] determineGrupoColumnsFromWinMatrix() throws Exception {
		String[][] grupos = new String[10][columnas.size() + 1];
		JIterator<JColumnaMatrix> oColsIt = columnas.getValueIterator();
		int counter = 0;
		boolean any = false;
		String empty = "SINGRUPOCOLUMNA";
		String muyempty = "SINGRUPO";

		while (oColsIt.hasMoreElements()) {
			JColumnaMatrix col = oColsIt.nextElement();
			for (int level = 0; level < 10; level++) {
				String grupo = col.getGrupos().getElement((long) level);
				if (grupo == null) {
					grupos[level][counter] = null;
					break;
				}
				grupos[level][counter] = grupo;
				any = true;
			}
			counter++;
		}
		grupos[0][counter] = null;
		if (!any) {
			grupos[0][0] = muyempty;
			return grupos;
		}
		return grupos;
	}

	void generateHeader(JXMLContent zContent) throws Exception {
		int span = 0;
		// generate the header columns
		String[][] oGrupoColumns = determineGrupoColumnsFromWinMatrix();

		boolean conGrupo = !(oGrupoColumns[0][0].equals("SINGRUPO"));
		zContent.setAttribute("with_group", conGrupo);
		String oldGCol = "";
		String oGCol = "";
		int maxLevel = 0;
		for (maxLevel = 0; oGrupoColumns[maxLevel][0] != null; maxLevel++)
			;

		for (int l = 0; l < maxLevel; l++) {
			zContent.startNode("level_column");
			oldGCol = null;
			span = 0;
			for (int g = 0; oGrupoColumns[l].length>g && oGrupoColumns[l][g] != null; g++) {
				oGCol = oGrupoColumns[l][g];
				if (oldGCol == null)
					oldGCol = oGCol;
				if (oldGCol.equals(oGCol)) {
					span++;
					continue;
				}
				zContent.startNode("grupo_column");
				zContent.setAttribute("span", span);
				addDrop(zContent, "column_"+oldGCol,getWins());

				if (!supportHtml()) {
					zContent.setNodeText(JTools.trimHTML(oldGCol));
				} else {
					
					if (oldGCol.indexOf("<")==-1)
						zContent.addTextNode("title", oldGCol);
					else {
						if (!supportHtml()) 
							zContent.addTextNode("html_data", JTools.trimHTML(oldGCol));
						else
							zContent.addTextNode("html_data", oldGCol);
					}
				}
				zContent.endNode("grupo_column");
				oldGCol = oGCol;
				span = 1;
			}
			if (oldGCol != null) {
				zContent.startNode("grupo_column");
				zContent.setAttribute("span", span);
				addDrop(zContent, "column_"+oldGCol,getWins());

				if (oldGCol.indexOf("<")==-1)
					zContent.addTextNode("title", oldGCol);
				else {
					if (!supportHtml()) 
						zContent.addTextNode("html_data", JTools.trimHTML(oldGCol));
					else
						zContent.addTextNode("html_data", oldGCol);
				}
				zContent.endNode("grupo_column");
			}
			oldGCol = null;
			zContent.endNode("level_column");
		}

		zContent.startNode("header");
		zContent.setAttribute("has_subdetail", false);
		zContent.setAttribute("datetime", JDateTools.CurrentDateTime());
		zContent.setAttribute("filters", formFiltros == null ? "" : formFiltros.toString());
		String sColType;
		JIterator<String> itkeys = columnas.getKeyIterator();
		while (itkeys.hasMoreElements()) {
			String key = itkeys.nextElement();
			JColumnaMatrix oCol = columnas.getElement(key);
			if (oCol == null)
				continue;
			zContent.startNode("column");
			sColType = oCol.getClase().getSimpleName().toUpperCase();
			if (oCol.getAlignment()==0) {
				oCol.setAlignmentFromType(sColType);
			}
			zContent.setAttribute("halignment", this.getColumnAlignment(oCol.getAlignment()));
			zContent.setAttribute("type", sColType.toUpperCase());

			addDrop(zContent, (oCol.isEje()?"fila_"+oCol.getTitulo():"campo_"+oCol.getCampo()), getWins());
			sColType = oCol.getClase().getSimpleName();
			JWebAction webAction = JWebActionFactory.createWinListOrden(this, this.sourceAction, this.sourceObjectId, oCol.getClave(), (columnOrden == null || !columnOrden.equals(oCol.getClave())) ? "ASC" : (direccionOrden.equalsIgnoreCase("asc") ? "desc" : "asc"), sAll,(int)totalCountElement, iFromRow, this.getRealPageSize(),null,-1);
			webAction.toXML(zContent);
			zContent.addTextNode("hover", oCol.getTitulo());
			if (oCol.getTitulo().indexOf("<")==-1)
				zContent.addTextNode("title", oCol.getTitulo());
			else {
				if (!supportHtml()) 
					zContent.addTextNode("html_data", JTools.trimHTML( oCol.getTitulo()));
				else
					zContent.addTextNode("html_data",  oCol.getTitulo());
			}
	
			zContent.endNode("column");
		}
		zContent.endNode("header");
	}

	void generateRows(JXMLContent zContent) throws Exception {
		zContent.startNode("rows");

		zContent.setAttribute("zobject", winsObjectId);
		int pos = 1;
		JIterator<JFilaMatrix> itFilas = matrix.getValueIterator();
		while (itFilas.hasMoreElements()) {

			JFilaMatrix fila = itFilas.nextElement();
			zContent.startNode("row");
			rowToXML(zContent, fila, pos++ , oldSelect,  oldSingleSelect);
			zContent.endNode("row");
			if (isGenerateInternalEvents())
				BizUsuario.eventInterfaz(this.getClass().getCanonicalName(), "Procesando registro ", pos , matrix.size(),  getSourceAction().isCancelable(), null);

		}
		zContent.endNode("rows");
		if (isGenerateInternalEvents())
			BizUsuario.eventInterfaz(this.getClass().getCanonicalName(), "Presentando datos ", matrix.size()-1 , matrix.size(),  getSourceAction().isCancelable(), null);

	}

	void generateFooter(JXMLContent zContent) throws Exception {
		footToXML(zContent);
		if (isWinListFullExportRequest) return;

		navigationBarToXML(zContent);

	}


	protected JWebAction createDoFilterAction(BizAction a, JWebFilterPanel filterPanel) throws Exception {
		return JWebActionFactory.createRefresh();
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
	protected JWebViewComponent attchGoBack(JWebViewComposite panel) throws Exception {
		 return null;
		}
		protected JWebViewComponent attchPage(JWebViewComposite panel,int page) throws Exception {
			 return null;
		}
		protected JWebViewComponent attchGoNext(JWebViewComposite panel) throws Exception {
			 return null;
		}
		public JWebViewComponent addSelectSizePage(JWebViewComposite panel) throws Exception {
			 return null;
		}

	public JWebViewComponent addButtonExportToExcel(JWebViewComposite panel) throws Exception {
		if (this.isAllowExportToExcel()) return null;
		return	BizUsuario.retrieveSkinGenerator().createBotonExport(panel,"export_to_excel",JWebActionFactory.createWinListExportAllToExcel(this.getSourceAction(), this, this.sourceObjectId,JBaseWin.MODE_MATRIX));
	}
	public JWebViewComponent addButtonExportToCSV(JWebViewComposite panel) throws Exception {
		if (this.isAllowExportToCSV())  return null;
		return	BizUsuario.retrieveSkinGenerator().createBotonExport(panel,"export_to_csv",JWebActionFactory.createWinListExportAllToCSV(this.getSourceAction(), this, this.sourceObjectId,JBaseWin.MODE_MATRIX));
	}
	public JWebViewComponent addButtonExportToReport(JWebViewComposite panel) throws Exception {
		if (this.isAllowExportToMap())  return null;
		return	BizUsuario.retrieveSkinGenerator().createBotonExport(panel,"export_to_pdf",JWebActionFactory.createWinListExportAllToReport(this.getSourceAction(), this, this.sourceObjectId,JBaseWin.MODE_MATRIX));
	}
	
}
