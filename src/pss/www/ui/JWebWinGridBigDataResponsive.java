package pss.www.ui;

import java.awt.Dimension;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.StringTokenizer;

import org.jsoup.Jsoup;

import pss.common.security.BizUsuario;
import pss.core.services.fields.JObject;
import pss.core.services.fields.JScript;
import pss.core.services.fields.JString;
import pss.core.tools.JDateTools;
import pss.core.tools.JTools;
import pss.core.tools.PssLogger;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JList;
import pss.core.tools.collections.JMap;
import pss.core.win.JBaseWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.actions.BizActions;
import pss.core.win.submits.JActWins;
import pss.core.win.totalizer.JTotalizer;
import pss.core.winUI.lists.JColumnaLista;
import pss.core.winUI.lists.JWinBigData;
import pss.core.winUI.responsiveControls.JFormCheckResponsive;
import pss.core.winUI.responsiveControls.JFormControlResponsive;
import pss.core.winUI.responsiveControls.JFormTableBigDataResponsive;
import pss.core.winUI.responsiveControls.TableFilterManual;
import pss.www.platform.actions.JWebAction;
import pss.www.platform.actions.JWebActionFactory;
import pss.www.platform.actions.JWebRequest;
import pss.www.platform.actions.JWebServerAction;
import pss.www.platform.actions.requestBundle.JWebActionData;
import pss.www.platform.content.generators.JXMLContent;
import pss.www.ui.controller.JWebFormControl;

public class JWebWinGridBigDataResponsive  extends JWebWinGenericResponsive implements JAbsolutelyNamedWebViewComponent, JWebControlInterface {

	//
	// CONSTRUCTOR
	//
	protected Map<String,Map<Long,String>> searchColumns = null;

	JFormTableBigDataResponsive table;
	public JFormTableBigDataResponsive getTable() {
		return table;
	}
	public void setTable(JFormTableBigDataResponsive table) {
		this.table = table;
	}
	public JWebWinGridBigDataResponsive(BizAction action,  boolean embedded) throws Exception {
		this(action, embedded, false);
	}
	

	public JWebWinGridBigDataResponsive(BizAction action, boolean embedded, boolean formLov) throws Exception {
		this.sourceAction = action;
		this.setFormLov(formLov);
		this.setEmbedded(embedded);


	}
	
	public void addInfoManualFilterByCol(JXMLContent zContent) throws Exception {
		if (getTable()==null) return;
		JMap<String, TableFilterManual> tables = getTable().getManualFilter();
		if (tables==null) return ;
		JIterator<String> it = tables.getKeyIterator();
		while (it.hasMoreElements()) {
			String key = it.nextElement();
			TableFilterManual manual = tables.getElement(key);
			zContent.startNode("manualFilter");
			zContent.setAttribute("field", manual.getField());
			zContent.setAttribute("oper", manual.getOperacion());
			zContent.setAttribute("control", manual.getControl());
			for (String column:manual.getColumns()) {
				zContent.startNode("column");
				zContent.setAttribute("name",buildColumnName(column));
				zContent.endNode("column");
			}
			zContent.endNode("manualFilter");
			
		}
	}
	public void addUpdateControls(JXMLContent zContent) throws Exception {
		if (getTable()==null) return;
		JMap<String, String> tables = getTable().getUpdateControls();
		if (tables==null) return ;
		JIterator<String> it = tables.getKeyIterator();
		while (it.hasMoreElements()) {
			String key = it.nextElement();
			String value = tables.getElement(key);
			zContent.startNode("updateControl");
			zContent.setAttribute("field", key);
			zContent.setAttribute("data", value);
			zContent.endNode("updateControl");
			
		}
	}
	public boolean hasPagination() throws Exception {
		return false; // datatable provee la paginacion
	}
	
	public JWebViewComposite getRootPanel() {
		return this.oRootPanel;
	}
	@Override
	public String getRegisteredObjectId() throws Exception{
		return getProviderName();
	}
	@Override
	public void regiterObjects() throws Exception {
		// se agrega el id de Objecto de la accion padre
		if (this.bObjectRegistred) return;
		winsObjectId = JWebActionFactory.registerObject(this.getWins(), getRegisteredObjectId());
		if (sourceAction!=null && sourceAction.hasOwner()) { 
				sourceObjectId = JWebActionFactory.registerObject(this.sourceAction.getObjOwner());
		} 
	
	
		this.bObjectRegistred=true;
	}
	public static JWebWinGridBigDataResponsive create(JWebViewComposite parent, JFormTableBigDataResponsive control) throws Exception {
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
		JWebWinGridBigDataResponsive webList = new JWebWinGridBigDataResponsive(control.getAction(), true);
		webList.takeAttributesFormControlResponsive(control);
		webList.setParentProvider(parent.findJWebWinForm());
		webList.setNotebookParent(pageAction.getIdAction());
		webList.setWinList(control.GetLista());
		webList.setUseContextMenu(control.getUseContextMenu());
		webList.setMinimusRows(control.getMinimusRows());
		webList.setExtraRows(control.getExtraRows());
		
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
		webList.setShowFooterBar(control.isShowFooterBar());
	
		webList.setEmbedded(true);
		webList.ensureIsBuilt();
		webList.setName(pageAction.getIdAction());
		webList.setTable(control);
		realParent.addChild(webList.getRootPanel().getName(), webList.getRootPanel());
		return webList;

	}
	long findIdxColumn(JColumnaLista[] columns,String name) throws Exception {
		JColumnaLista oCol=null;
		for (int i = 0; i < columns.length; i++) {
			oCol = columns[i];
			if (oCol == null)
				continue;
			if (oCol.GetCampo().equalsIgnoreCase(name))
				return i;
			if (buildColumnName(oCol.GetCampo()).equalsIgnoreCase(name))
				return i;
		}
		return -1;
	}
	
	boolean checkOneManualFilter(JObject obj,JWin win,JColumnaLista[] columns,Map<Long,String>search,String sPropFormatted,String oper,String value, String field) throws Exception {
		boolean find = true;
		if (oper.equals("win")) {
			return win.checkManualFilter(field,sPropFormatted,value);
		}
		if (obj.isBoolean()) {
			if (oper.equals("auto")) oper="";
		  if (oper.equals("=")) {
			  find &= sPropFormatted.toLowerCase().equalsIgnoreCase(value.toLowerCase());
		  } else if (oper.equals("!=")) {
			  find &= !sPropFormatted.toLowerCase().equalsIgnoreCase(value.toLowerCase());
		  }else if (oper.equalsIgnoreCase("null")) {
		  	if (value.equals("true"))
		  		find &= sPropFormatted.equals("");
		  }else if (oper.equalsIgnoreCase("notnull")) {
		  	if (value.equals("true"))
		  		find &= !sPropFormatted.equals("");
		  }
		} else if (obj.isLong()||obj.isInteger()||obj.isFloat()||obj.isCurrency()) {
			if (oper.equals("auto")) oper="=";
			double valueA=JTools.getDoubleNumberEmbedded(value);
			double valueB=JTools.getDoubleNumberEmbedded(sPropFormatted);
			if (oper.equals("=")) {
			  find &= valueA==valueB;
		  } else if (oper.equals("!=")) {
			  find &= valueA!=valueB;
		  } else if (oper.equals("<")) {
			  find &= valueA<valueB;
		  } else if (oper.equals("<=")) {
			  find &= valueA<=valueB;
		  } else if (oper.equals(">")) {
			  find &= valueA>valueB;
		  } else if (oper.equals(">=")) {
			  find &= valueA>=valueB;
		  }else if (oper.equalsIgnoreCase("null")) {
		  	if (value.equals("true"))
		  		find &= valueB==0;
		  }else if (oper.equalsIgnoreCase("notnull")) {
		  	if (value.equals("true"))
		  		find &= valueB!=0;
		  }else  {
		  	if (value.equals("true"))
		  		find &= sPropFormatted.toLowerCase().equals(oper.toLowerCase());
		  }
		} else {
			if (oper.equals("auto")) oper="ilike";
		  if (oper.equals("ilike")) {
			  find &= sPropFormatted.toLowerCase().indexOf(value.toLowerCase())!=-1;
		  } else if (oper.equals("=")) {
			  find &= sPropFormatted.equals(value);
		  }else if (oper.equals("!=")) {
			  find &= !sPropFormatted.equals(value);
		  }else  {
		  	if (value.equals("true"))
		  		find &= sPropFormatted.toLowerCase().equals(oper.toLowerCase());
		  }
		  
		}  
		return find;
	}
	boolean checkManualFilter(JWin win,JColumnaLista[] columns,Map<String,Map<Long,String>>search) throws Exception {
		boolean find=true;

		if (search==null) return true;
		if (search.size()==0) return true;
		Iterator<String> it = search.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			Map<Long,String> value = search.get(key);
		  find&=checkOrManualFilter(win,columns,value);
			if (find==false) break;//lazy
		}
		return find;
	}
	
	boolean checkOrManualFilter(JWin win,JColumnaLista[] columns,Map<Long,String>search) throws Exception {
		boolean find=false;
		if (search==null) return true;
		if (search.size()==0) return true;
		Iterator<Long> it = search.keySet().iterator();
		while (it.hasNext()) {
				long key = it.next();
				String value = search.get(key);
				if (value==null || value.equals("")) continue;
				JColumnaLista col = columns[(int)key];
				String f = col.GetCampo();
				if (f==null) continue;
				StringTokenizer tokenizerOr = new StringTokenizer(value,";");
				boolean findOutOr=false;
				while (tokenizerOr.hasMoreTokens()) {
					String valueOr = tokenizerOr.nextToken();
				  JObject obj=win.getRecord().getPropDeep(f,false);
				  if (obj==null) continue;
					String oper = "auto";
					int pos =valueOr.indexOf("|");
					if (pos!=-1) {
						oper=valueOr.substring(0,pos);
						value=valueOr.substring(pos+1);
					}
					if (value.length()==0) continue;
				  String sPropFormatted = obj.toString();
					Boolean result = win.checkStaticFiltersInQuickSearchColumn(col.GetCampo(),sPropFormatted,oper,value);
					if (result!=null) {
						find &= result;
						continue;
					}
					
					boolean findOr=false;
					StringTokenizer toks = new StringTokenizer(value,",");
					while (toks.hasMoreTokens()) {
						String orVal = toks.nextToken();
						findOr|=checkOneManualFilter( obj, win,columns,search, sPropFormatted, oper, orVal, f);
						if (findOr==true) break;//lazy
							
					}
					findOutOr |=findOr;
					if (findOutOr==true) break;//lazy
				}
			  find |=findOutOr;
				if (find==true) break;//lazy
		}
		return find;
	}
	public String getPresentation() throws Exception {
		return JBaseWin.MODE_BIGDATA;
	};

	@Override
	protected void build() throws Exception {

		if (this.winList == null) {
			this.winList = new JWinBigData(getWins());
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
		return "win_list_bigdata";
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
	

	protected void addProviderHistory(JXMLContent zContent) throws Exception {
		zContent.getGenerator().getSession().getHistoryManager().addHistoryProvider(this.sourceAction);
	}

	
	protected int initializePagination() throws Exception {
		JWebRequest req = JWebActionFactory.getCurrentRequest();
		if (JWebActionFactory.isWinListFullExportRequest(req, this)&&isHtmlRequest) {
			this.iPageSize = -1;
				return -1;
		}	
		this.iFromRow = (int) JTools.getLongNumberEmbedded(req.getArgument("start"));
		this.iPageSize = (int) JTools.getLongNumberEmbedded(req.getArgument("length"));
		return iFromRow;
	}

	@Override
	protected void componentToXML(JXMLContent zContent) throws Exception {
		JInternalGenerateXML iXML = new JInternalGenerateXML();
		iXML.oWebWinList = this;
		iXML.zContent = zContent;
		iXML.jsonRequest = (createWinListExportAllToJson(getSourceAction(), sourceObjectId, JBaseWin.MODE_BIGDATA));
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

	protected boolean headerToXML(JWins zWins, JXMLContent zContent, JColumnaLista[] oColumns, boolean isWinListFullExportRequest) throws Exception {
			int span = 0;
		// generate the header columns
		zContent.setAttribute("with_group", false); 

		zContent.startNode("header");
		zContent.setAttribute("has_subdetail", zWins.hasSubDetail(isWinListFullExportRequest));
		zContent.setAttribute("datetime", JDateTools.CurrentDateTime());
		zContent.setAttribute("filters", formFiltros == null ? "" : formFiltros.toString());
		if (zWins.hasSubDetail(isWinListFullExportRequest))
			JWebIcon.getSkinIcon(BizUsuario.retrieveSkinGenerator().getExpandAllSubDetail()).toXML(zContent);
		JColumnaLista oCol;
		String sColType;
		boolean bWithIcon = false;
		addInfoManualFilterByCol(zContent);
		addUpdateControls(zContent);
		for (int i = 0; i < oColumns.length; i++) {
			oCol = oColumns[i];
			if (oCol == null)
				continue;
//			if (oCol.IfIcono() && i == 0)
//				continue;// el icono inicial en la exportacion no va
			zContent.startNode("column");
			zContent.setAttribute("col_name", buildColumnName(oCol.GetCampo()));
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

					if (oCol.getControl()!=null) {
						JFormControlResponsive control = (JFormControlResponsive)oCol.getControl();
						if (control.getScript()!=null) {
							zContent.setAttribute("control","JSCRIPT");
							generateScript(zContent,control.getScript());
						} else if (oCol.getControl().isEditable()) {
							if (control instanceof JFormCheckResponsive) {
								zContent.setAttribute("control","JCHECK");
								if (control.isEnteristab())
									zContent.setAttribute("class_component", "enteristab");
							} else {
								zContent.setAttribute("control","JINPUT");
								if (control.isEnteristab())
									zContent.setAttribute("class_component", "enteristab");
						  }
						}
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
					zContent.setAttribute("visible", oCol.isVisible()); 
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
	protected void  generateScript(JXMLContent zContent, JScript script) throws Exception {
		String onCalculate="";
		if (script.isPure()) {
			zContent.setAttribute("onCalculate", script.getScript());
			return;
		}
		LinkedHashMap<String, String> map= new LinkedHashMap<String, String>();
		if (script.getBind()==null) {
			zContent.setAttribute("onCalculate", script.getScript());
			return;
		}
		JIterator<String> i = script.getBind().getKeyIterator();
		while (i.hasMoreElements()) {
			String key = i.nextElement();
			String value = (String)script.getBind().getElement(key);
			map.put(key, "data['"+value+"']");
		}

		zContent.setAttribute("onCalculate", script.getFormulaInContext(map));
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
				zContent.setAttribute("col_name", buildColumnName(sColName) );
				zContent.setAttribute("halignment", "center");
				zContent.addTextNode("value", "");
				zContent.endNode("column");
				continue;
			}
			zContent.startNode("column");
			zContent.setAttribute("col_name", buildColumnName(sColName));
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
			String sPropFormatted = oProp.toRawString();
			if (sPropFormatted == null || oProp.isRawNull()) {
				sPropFormatted = "";
			}

			zContent.startNode("d");
			zContent.setAttribute("col_name", buildColumnName(sColName));

			zContent.setAttribute("marked", zWin.isMarked(sColName));
			if (zWin.isWordWrap(sColName))
				zContent.setAttribute("word_wrap", zWin.isWordWrap(sColName));
			zContent.setAttribute("height", zWin.getHeightRow());
			zContent.setAttribute("axis", idCol);
			zContent.setAttribute("visible", oCol.isVisible());


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
				zContent.setNodeText( JTools.encodeString(sPropFormatted));
			zContent.endNode("d");
		} catch (Exception e) {
			PssLogger.logError(e);
			zContent.startNode("d");
			zContent.setAttribute("col_name", buildColumnName(sColName));
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
			String forcedPos = this.getProviderName() + "_" + this.getWins().getClass().getSimpleName() + "_" + iRowPos;
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
			nav.add("minimusRows", ""+getMinumusRows());
			nav.add("extraRows", ""+getExtraRows());
			nav.add("toolbar", "" + this.getToolbar());
			
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
		nav.add("minimusRows", ""+getMinumusRows());
		nav.add("extraRows", ""+getExtraRows());
		nav.add("toolbar", "" + this.getToolbar());
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
		if (winList.getPageType().equals(JWinBigData.PAGETYPE_LANDSCAPE)) {
			return "11.693055555555556in";
		} else {
			return "8.268055555555555in";
		}
	}

	public String getHeigthPage() {
		if (winList.getPageType().equals(JWinBigData.PAGETYPE_LANDSCAPE)) {
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
		JWebWinGridBigDataResponsive oWebWinList;
		JWebAction jsonRequest;
		int errors=0;
		int filtrados = 0;
		int totalRecords = 0;

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

		boolean startDeclarations() throws Exception {
			if (!hasWins())
				return false;
			oRequest = zContent.getGenerator().getRequest();
			isWinListFullExportRequest = JWebActionFactory.isWinListFullExportRequest(oRequest, oWebWinList);
			isHtmlRequest = !JWebActionFactory.isBigData(oRequest);

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
		private void addFilterQuickSearchByColumn(JColumnaLista[] columns,Map<Long,String>search) throws Exception {
			if (search==null) return;
			if (search.size()==0) return;
			Iterator<Long> it = search.keySet().iterator();
			while (it.hasNext()) {
				long key = it.next();
				String value = search.get(key);
				String oper = "auto";
				int pos =value.indexOf("|");
				if (pos!=-1) {
					oper=value.substring(0,pos);
					value=value.substring(pos+1);
				}
				if (value.length()==0) continue;

				JColumnaLista col = columns[(int)key];
				String f = col.GetCampo();
				if (f==null) continue;
				if (!getWins().getWinRef().getRecord().getFixedProp(f).isTableBased()) continue;
				getWins().getRecords().clearDynamicFilters();
				if (!getWins().assignFilters(f, value)) {
					if (getWins().getWinRef().getRecord().getProp(f).isString())
						getWins().getRecords().addFilter(f, value, oper.equals("auto")?"ilike":oper).setDynamic(true);
					else if (JTools.isNumber(value)) {
						getWins().getRecords().addFilter(f, value, oper.equals("auto")?"=":oper).setDynamic(true);
					}
				}			
			}
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
		void setHideRows(long hiderows) throws Exception {
//			if (!getTable().getTableControl().hasHideRows()) return;
//			JFormControl control = getTable().getTableControl().getBaseForm().getControles().findControl(getTable().getTableControl().getHideRows());
//			if (control==null) return;
//			setPosFunction("updateField('dgf_form_"+this.sourceAction.getProviderName()+"_fd-"+control.getName()+"',"+hiderows+")");
		}
		private boolean checkFilters(JWin win) throws Exception {
			if (!checkFilterQuickSearch(search,win)) return false;
			return checkManualFilter(win, determineColumns(), searchColumns);
		}

		private boolean checkFilterQuickSearch(String value, JWin win) throws Exception {
			if (value == null || value.trim().equals(""))
				return true;
			boolean check = false;
			StringTokenizer t = new StringTokenizer(getWins().getWinRef().getSearchFields(false), ";");
			while (t.hasMoreTokens()) {
				String f = t.nextToken();
		
				check |= win.checkStaticFiltersInQuickSearch(f, value);
			}
			return check;


		}

		protected void readData() throws Exception {
			analizSearchRequest();
			addFilterQuickSearch(search);
//			addFilterQuickSearchByColumn(determineColumns(),searchColumns);
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
			getWinList().ConfigurarColumnasLista();
		}

		protected void analizSearchRequest() throws Exception {
			JWebRequest req = JWebActionFactory.getCurrentRequest();
			
			searchColumns = new HashMap<String,Map<Long,String>>();
			search = req.getArgument("search[value]");
			String nameColumn=null;
			long idNameColumn=0;
			JIterator<String> it = req.getAllArgumentNames();
			while (it.hasMoreElements()) {
				String arg = it.nextElement();
				if (arg.indexOf("[name]")!=-1 && arg.startsWith("columns[")) {
					idNameColumn=JTools.getLongFirstNumberEmbedded(arg);
					nameColumn=req.getArgument(arg);
				}
				if (arg.equalsIgnoreCase("[search][value]")) continue;
				if (arg.indexOf("[search][value]")==-1) continue;
				if (arg.equalsIgnoreCase("[search][value]")) continue;
				String val = req.getArgument(arg);
				if (val.equals("")) continue;
				long column = JTools.getLongFirstNumberEmbedded(arg);
				if (column!=idNameColumn) continue;
				long findColumn = findIdxColumn(determineColumns(), nameColumn);
				if (findColumn==-1) continue;
				String filter = val.substring(0,val.indexOf("|"));
				String value = val.substring(val.indexOf("|")+1);
				Map<Long,String> searchs = searchColumns.get(filter);
				if (searchs==null) {
					searchs= new HashMap<Long,String>();
					searchColumns.put(filter,searchs);
				}
				searchs.put(findColumn, value);
			}
		}
		
		JColumnaLista[] determineColumns() throws Exception    {
			if (oColumns!=null) return oColumns;
			getWinList().ConfigurarColumnasLista();
			JList<JColumnaLista> lista = getWinList().GetColumnasLista();
			JColumnaLista[] columns = new JColumnaLista[lista.size()];
			JIterator<JColumnaLista> oColsIt = lista.getIterator();
			int counter = 0;
			while (oColsIt.hasMoreElements()) {
				JColumnaLista col = oColsIt.nextElement();
//				if (!col.isVisible()) continue;
				columns[counter] = col;
				counter++;
			}
			return oColumns=columns;
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
			supportOffset =getWins().isDatabaseSupportsOffset();
			getWins().startShowInGridBigData();
			if (iFromRow != -1) {
				boolean hasRow = true;
				while (hasRow && iRowCursor < iFromRow) {
					hasRow = getWins().nextRecord();
					if (hasRow && !supportOffset) {
						oFirstWin = getWins().getRecord();
						oFirstWin.showInGridBigData();
						if (!checkFilters(oFirstWin)) {
							boolean iserror =getWins().processShowInGridBigData(oFirstWin);
							oFirstWin=null;
							errors+=iserror?1:0;
							totalRecords++;
							filtrados++;
							continue;
						}
						getWins().processShowInGridBigDataFilter(oFirstWin);

					}
					iRowCursor++;
					if (iRowCursor >= iFromRow)
						break;
					totalRecords++;

				}
				if (oFirstWin==null && hasRow) {
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
			bWithIcon = headerToXML(getWins(), zContent, oColumns, isWinListFullExportRequest);
		}

		void generateRows() throws Exception {
			zContent.startNode("rows");

			zContent.setAttribute("zobject", winsObjectId);
			zContent.setAttribute("corte_control", hasCorteControl());
			
			JWin oWin = oFirstWin;
			long nroRecords = iFromRow>0?iFromRow:0;
			if (iPageSize != -1)
				iToRow = iFromRow + getRealPageSize() - 1;
			else
				iToRow = -1;
 
			if (iFromRow != -1 && iFromRow > 0)
				goesBack = true;
			
			while (oWin != null) {
				oWin.showInGridBigData();
				errors +=getWins().processShowInGridBigData(oWin)?1:0;
				totalRecords++;
				if (checkFilters(oWin)) {
					getWins().processShowInGridBigDataFilter(oWin);
					zContent.startNode("row");
					nroRecords++;
					rowToXML(zContent, oWin, oColumns, bWithIcon, iRowCursor++, oldSelect, oldSingleSelect, true, isWinListFullExportRequest);
					zContent.endNode("row");
				} else {
					filtrados++;
				}
				if (iToRow!=-1 && nroRecords>iToRow)
					break;
				if (getWins().nextRecord())
					oWin = getWins().getRecord();
				else
					oWin = null;
			}
			
			generateRestRows();
			
		//	PssLogger.logInfo("Sending rows from: "+iFromRow+" to: "+nroRecords);
			if (oWin==null)
				generateExtraRows(nroRecords);
			long total =totalCountElement == -1 ? totalRecords : totalCountElement;
			Map<String,String> customInfo = getWins().finishShowInGridBigData(total, filtrados);

			zContent.endNode("rows");
			zContent.startNode("info");
			zContent.setAttribute("total_records", totalRecords);
			zContent.setAttribute("visible_records", totalRecords-filtrados);
			zContent.setAttribute("hide_records", filtrados);
			zContent.setAttribute("error_records", errors);
			if (customInfo!=null) {
				Iterator<String> it = customInfo.keySet().iterator();
				while (it.hasNext()) {
					String key = it.next();
					String value = customInfo.get(key);
					zContent.startNode("customInfo");
					zContent.setAttribute("name", key);
					zContent.setAttribute("value", value);
					zContent.endNode("customInfo");
				}
			}
			zContent.endNode("info");

		}
		public int getRealPageSize() throws Exception {
			return iPageSize==-1?-1:iPageSize+(isShowFilterBar()?0:5);
		}

		void generateRestRows() throws Exception {
			JWin oWin = null;
			while (getWins().nextRecord()) {
				oWin=getWins().getRecord();
				oWin.showInGridBigData();
				errors +=getWins().processShowInGridBigData(oWin)?1:0;
				totalRecords++;
				if (!checkFilters(oWin)) 
					filtrados++;
				else
					getWins().processShowInGridBigDataFilter(oWin);

			}
			
		}
		void generateExtraRows(long nroRecords) throws Exception {
			JWin oWin = getWins().getWinRef();
			long lastPos=nroRecords;
			for (long j =0;j<getExtraRows()||lastPos<getMinumusRows();j++) {
					zContent.startNode("row");
					zContent.setAttribute("rowclass", BizUsuario.retrieveSkinGenerator().getRowClass());
					zContent.setAttribute("rowpos", lastPos);
					oWin.showInGridBigData();
					
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
