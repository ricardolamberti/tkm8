package pss.www.ui;

import java.awt.Dimension;

import pss.common.security.BizUsuario;
import pss.core.services.fields.JObject;
import pss.core.tools.JDateTools;
import pss.core.tools.JTools;
import pss.core.tools.PssLogger;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JMap;
import pss.core.win.JBaseWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.totalizer.JTotalizer;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.controls.JFormRow;
import pss.core.winUI.icons.GuiIcon;
import pss.core.winUI.icons.GuiIconos;
import pss.core.winUI.lists.JColumnaLista;
import pss.core.winUI.lists.JWinGrid;
import pss.core.winUI.lists.JWinList;
import pss.core.winUI.responsiveControls.TableFilterManual;
import pss.www.platform.actions.JWebAction;
import pss.www.platform.actions.JWebActionFactory;
import pss.www.platform.actions.JWebRequest;
import pss.www.platform.content.generators.JXMLContent;
import pss.www.ui.controller.JWebFormControl;

public class JWebWinGridResponsive extends JWebWinGenericResponsive implements JAbsolutelyNamedWebViewComponent {

	protected JWebTableResponsive table = null;

	protected JWinList winGrid = null;
	boolean isWinListFullExportRequest;
	protected boolean selectedRows = false;
	private int lastCol = 0;
	protected boolean uploadOnlyEditable = false;

	public boolean isUploadOnlyEditable() {
		return uploadOnlyEditable;
	}

	public void setUploadOnlyEditable(boolean uploadOnEditable) {
		this.uploadOnlyEditable = uploadOnEditable;
	}

	public boolean isSelectedRows() {
		return selectedRows;
	}

	public void setSelectedRows(boolean selectedRows) {
		this.selectedRows = selectedRows;
	}

	public boolean isShowFilterBar() throws Exception {
		return false;
	}

	public void setWinList(JWinList w) {
		winGrid = w;
	}

	public JWebTableResponsive getTable() {
		return table;
	}

	public void setTable(JWebTableResponsive table) {
		this.table = table;
	}
	//
	// CONSTRUCTOR
	//

	@Override
	public JWins getWins() throws Exception {
		if (oWins != null)
			return oWins;
		this.oWins = this.winGrid.getWins();
		return this.oWins;
	}

	public JWebWinGridResponsive(BizAction action, JWinGrid zWinList, JWebTableResponsive oTable) throws Exception {
		this.sourceAction = action;
		this.winGrid = zWinList;
		this.setTable(oTable);
		this.setWidthBorder(BizUsuario.retrieveSkinGenerator().getBorderWebWinForm());
		this.setMultipleSelection(zWinList.isMultipleSelection());
		this.setSelectedRows(zWinList.isSelectedRows());
		this.setUploadOnlyEditable(zWinList.isUploadOnlyEditable());
		splitPoint = BizUsuario.retrieveSkinGenerator().getSplitPoint();

	}

	public JWebViewInternalComposite getRootPanel() {
		return this.oRootPanel;
	}

	//
	// PUBLIC API
	//

	@Override
	public boolean hasPreviewPanel() throws Exception {
		return false;
	}
	// protected boolean hasActionBar() throws Exception {
	// return this.oActionBar != null && this.isShowActionBar();
	// }

	public String getPresentation() throws Exception {
		return JBaseWin.MODE_LIST;
	};

	@Override
	protected void build() throws Exception {
		if (this.winGrid == null)
			this.winGrid = new JWinGrid(getWins());
		this.regiterObjects();
		oRootPanel = BizUsuario.retrieveSkinGenerator().buildWinGrid(this);
	}

	// public JWebPanel buildList() throws Exception {
	// JWebPanel centerPanel = this.getTableWithFilters();
	// JWebPanel borderPanel = this.getPanelWithBorder(centerPanel);
	// JWebPanel completePanel = this.createCompletePanel(borderPanel);
	// return completePanel;
	// }
	//
	//

	// protected JWebViewComposite buildToolbarLeft() throws Exception {
	// JWebPanel centerPanel = this.getTableWithFilters();
	// JWebPanel borderPanel = this.getPanelWithBorder(centerPanel);
	//
	// JWebSplit oSplit = new JWebSplit();
	// oSplit.setName("split_pane");
	//
	// JWebPanel oPanelA = new JWebPanel();
	// JWebPanel oPanelB = new JWebPanel();
	//
	// oSplit.setPosSplit(splitPoint);
	//
	// JWebBorderLayout oPanelBLayout = new JWebBorderLayout();
	// oPanelB.setSkinStereotype("split_panel");
	// oSplit.setPanelB(oPanelB);
	// oSplit.addChild("splitB", oPanelB);
	//
	//
	// oPanelBLayout.setCenterComponent(borderPanel);
	// oPanelB.setLayout(oPanelBLayout);
	// oPanelB.addChild("win_list_border", borderPanel);
	//
	//
	// JWebBorderLayout oLayoutA = new JWebBorderLayout();
	// oPanelA.setSkinStereotype("split_panel");
	// oPanelA.setLayout(oLayoutA);
	// oSplit.setPanelA(oPanelA);
	// oSplit.addChild("splitA", oPanelA);
	//
	// this.embbedActionBar(oPanelA);
	// return oSplit;
	// }

	//
	// protected JWebPanel buildOnlyTable() throws Exception {
	// JWebPanel panel = this.getWinListTablePanel();
	//// this.oActionBar = this.createActionBar();
	//// panel.addChild(oActionBar.getName(), this.oActionBar); // no layouttear
	// return panel;
	// }

	// protected JWebPanel getTableWithFilters() throws Exception {
	// JWebPanel centerPanel = new JWebPanel();
	// centerPanel.setName("win_list_center_panel");
	// JWebBorderLayout layout = new JWebBorderLayout();
	// centerPanel.setLayout(layout);
	// if (this.getWins()!=null && this.getWins().hasNavigationBar()) {
	// createNavigationBars();
	// }
	// JWebPanel winListTablePanel = this.getWinListTablePanel();
	// centerPanel.addChild("win_list_table_panel", winListTablePanel);
	// layout.setCenterComponent(winListTablePanel);
	//
	// if (this.oNavigationBar!=null) {
	// centerPanel.addChild("win_list_navigation_bar", this.oNavigationBar);
	// layout.setBottomComponent(this.oNavigationBar);
	// }
	//
	// return centerPanel;
	// }

	// protected JWebPanel getPanelWithBorder(JWebPanel panel) throws Exception {
	// JWebPanel borderPanel = new JWebPanel();
	// borderPanel.setName("win_list_border");
	// JWebBorderLayout layout;
	// layout = new JWebBorderLayout(0, 0);
	// borderPanel.setSkinStereotype("win_grid_border_embedded");
	// borderPanel.setLayout(layout);
	// borderPanel.addChild(panel.getName(), panel);
	// layout.setCenterComponent(panel);
	// return borderPanel;
	// }

	// protected JWebPanel createNavigationBars() throws Exception {
	// this.oNavigationBar = new JWebPanel();
	// this.oNavigationBar.setSkinStereotype("win_list_navigation_bar");
	// this.oNavigationBar.setName("win_list_navigation_bar");
	// this.oNavigationBar.setLayout(new JWebFlowLayout(ORIENTATION_HORIZONTAL,
	// HALIGN_RIGHT, VALIGN_MIDDLE, VALIGN_MIDDLE));
	// return this.oNavigationBar;
	//
	// }

	protected void appendNextControl(JFormControl c, JWebPanel panel, JWebPanel flowPanel) throws Exception {
		if (c.getNextControl() == 0)
			return;
		if (c.getNextControl() == JFormControl.NEXT_BREAK) {
			flowPanel.addChild(new JWebFlowBreak());
			return;
		}
		if (c.getNextControl() == JFormControl.NEXT_LINE) {
			flowPanel.addChild(new JWebFlowBreak());
			JWebTitledBorder t = new JWebTitledBorder();
			t.setSize((int) panel.getSize().getWidth(), 2);
			flowPanel.addChild(t);
			flowPanel.addChild(new JWebFlowBreak());
		}
		return;
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

	public Class<?> getAbsoluteClass() {
		return JWebWinListResponsive.class;
	}

	@Override
	public String getComponentType() {
		return "win_grid";
	}

	@Override
	protected void containerToXML(JXMLContent zContent) throws Exception {
	}

	@Override
	protected void componentPreLayout(JXMLContent zContent) throws Exception {
		if (getWins() == null)
			return;

		attachActionBar();

	}

	@Override
	protected void componentToXML(JXMLContent zContent) throws Exception {
		this.zContent = zContent;

		if (!this.startDeclarations())
			return;

		this.readData();
		this.determineColumns(this.winGrid, false);
		this.generateHeader();
		this.generateRows();
		this.generateFooter();
	}

	protected boolean headerToXML(JWins zWins, JXMLContent zContent, JWin oFirstWin, JColumnaLista[] oColumns) throws Exception {
		// generate the header columns

		iDeleted = 0;
		iNews = 0;
		zContent.startNode("header");
		zContent.setAttribute("datetime", JDateTools.CurrentDateTime());
		zContent.setAttribute("filters", "");// formFiltros==null?"":formFiltros.toString()
																					// );
		JColumnaLista oCol;
		String sColType;
		bWithIcon = false;
		bWithIconVisible = false;
		for (int i = 0; i < oColumns.length; i++) {
			oCol = oColumns[i];
			if (oCol == null)
				continue;
			// if (!oCol.isVisible()) continue;
			zContent.startNode("column");
			if (oCol.IfIcono()) {
				bWithIcon = true;
				bWithIconVisible = oCol.isVisible();
				zContent.setAttribute("halignment", "center");
				zContent.setAttribute("visible", bWithIconVisible);
				zContent.addTextNode("title", "-"); // no se traduce
			} else {
				if (oFirstWin != null) {
					sColType = oFirstWin.getRecord().getObjectType(oCol.GetCampo());
				} else {
					sColType = JObject.JSTRING;
				}
				if (!oCol.hasAlignment()) {
					oCol.setAlignmentFromType(sColType);
				}
				zContent.setAttribute("halignment", this.getColumnAlignment(oCol.getAlignment()));
				if (oFirstWin != null && oFirstWin.getWidthCol(oCol.GetCampo()) != 0)
					zContent.setAttribute("width", oFirstWin.getWidthCol(oCol.GetCampo()) + "px");
				else if (oCol.getWidth() != null)
					zContent.setAttribute("width", oCol.getWidth());
				zContent.setAttribute("type", sColType);
				zContent.setAttribute("visible", oCol.isVisible());
				zContent.setAttribute("hover", oCol.GetColumnaTitulo()); // no se
																																	// traduce
				if (oCol.hasHtmlTitulo()) {
					String val = oCol.GetColumnaTitulo();
					zContent.startNode("html_data");
					zContent.setNodeText(val);
					zContent.endNode("html_data");
				} else
					zContent.addTextNode("title", oCol.GetColumnaTitulo()); // no se
																																	// traduce
			}
			zContent.endNode("column");
		}
		zContent.endNode("header");
		return bWithIcon;
	}

	protected void footToXML(JXMLContent zContent, JTotalizer zTot, JColumnaLista[] oColumns) throws Exception {
		zContent.startNode("footer");
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

	//
	// INTERNAL IMPLEMENTATION
	//

	void invisibleToXML(JWebRowResponsive row) throws Exception {
		JColumnaLista oCol;
		zContent.startNode("control");
		zContent.startNode("text_field_responsive");
		zContent.setAttribute("name", "internal_estado_fila");
		zContent.setAttribute("form_name", row.getProvider());
		zContent.setAttribute("skin_stereotype", "text_field");
		zContent.setAttribute("visible", false);
		zContent.setAttribute("intable", true);
		zContent.setAttribute("table_name", getTableName());
		zContent.setAttribute("table_row", row.getRowid());
		zContent.setAttribute("table_column", lastCol++);
		zContent.addTextNode("text", "" + row.getEstado());
		zContent.endNode("text_field_responsive");
		zContent.endNode("control");

		zContent.startNode("control");
		zContent.startNode("checkselect");
		zContent.setAttribute("name", "internal_select_fila");
		zContent.setAttribute("form_name", row.getProvider());
		zContent.setAttribute("skin_stereotype", "text_field");
		zContent.setAttribute("visible", false);
		zContent.setAttribute("intable", true);
		zContent.setAttribute("table_name", getTableName());
		zContent.setAttribute("table_row", row.getRowid());
		zContent.setAttribute("table_column", lastCol++);
		zContent.addTextNode("text", "" + row.isSelected());
		zContent.endNode("checkselect");
		zContent.endNode("control");

		for (int i = 0; i < oColumns.length; i++) {
			oCol = oColumns[i];
			if (oCol.isVisible())
				continue;
			JWebViewComponent comp = null;

			JWebFormControl wcomp = (JWebFormControl) row.getBaseForm().getControles().findControl(oCol.GetCampo());
			if (wcomp == null)
				continue;
			comp = (JWebViewComponent) wcomp.getWebComponent();
			if (comp == null)
				continue;
			if (comp != null) {
				zContent.startNode("control");
				comp.setInTable(getTableName(), row.getRowid(), lastCol++);
				comp.toXML(zContent);
				zContent.endNode("control");
			}
		}
	}

	void setHideRows(long hiderows) throws Exception {
		if (!getTable().getTableControl().hasHideRows())
			return;
		JFormControl control = getTable().getTableControl().getBaseForm().getControles().findControl(getTable().getTableControl().getHideRows());
		if (control == null)
			return;
		setPosFunction("updateField('dgf_form_" + this.sourceAction.getProviderName() + "_fd-" + control.getName() + "'," + hiderows + ")");
	}

	boolean checkManualFilter(JWebRowResponsive row, JWin win) throws Exception {
		boolean find = true;
		JMap<String, TableFilterManual> map = getTable().getTableControl().getManualFilter();
		JIterator<TableFilterManual> filters = map.getValueIterator();
		while (filters.hasMoreElements()) {
			TableFilterManual filter = filters.nextElement();
			JFormControl control = getTable().getTableControl().getBaseForm().getControles().findControl(filter.getField());
			if (control == null)
				continue;
			if (control.getValue().toLowerCase().equals(""))
				continue;
			String search = control.getValue().toLowerCase();
			boolean findRow = false;
			for (String column : filter.getColumns()) {
				JObject obj = win.getRecord().getPropDeep(column, false);
				if (obj == null)
					continue;
				String sPropFormatted = obj.toString();

				findRow |= sPropFormatted.toLowerCase().indexOf(search) != -1;
				if (findRow) {
					break; // lazzy
				}
			}
			find &= findRow;
			if (!find)
				break;// lazzy
		}
		return find;
	}

	boolean fieldToXML(JWebRowResponsive row, int rowpos, JXMLContent zContent, JWin zWin, JColumnaLista oCol, String advice, boolean last, int idCol) throws Exception {
		boolean visibleRow = true;
		if (oCol == null)
			return visibleRow;
		if (oCol.IfIcono())
			return visibleRow;
		try {

			JWebViewComponent comp = null;

			JWebFormControl wcomp = (JWebFormControl) row.getBaseForm().getControles().findControl(oCol.GetCampo());
			if (wcomp == null)
				return visibleRow;
			comp = (JWebViewComponent) wcomp.getWebComponent();
			if (comp == null)
				return visibleRow;
			String sColName = oCol.GetCampo();
			String sPropFormatted = wcomp.getValue();

			visibleRow = true;

			comp.setBackground("inherit");
			comp.setForeground("inherit");
			zContent.startNode("d");
			zContent.setAttribute("marked", zWin.isMarked(sColName));
			if (zWin.isWordWrap(sColName))
				zContent.setAttribute("word_wrap", zWin.isWordWrap(sColName));
			zContent.setAttribute("height", zWin.getHeightRow());
			zContent.setAttribute("axis", idCol);

			String value = zWin.getFieldForeground(sColName) != null ? zWin.getFieldForeground(sColName) : oCol.getColorForeground();
			if (value != null) {
				zContent.setAttribute("foreground", JTools.toHtmlColor(value));
			}
			value = null;
			if (value == null)
				value = zWin.getFieldBackground(sColName) != null ? zWin.getFieldBackground(sColName) : oCol.getColorBackground();
			if (value != null)
				zContent.setAttribute("background", JTools.toHtmlColor(value));
			value = sPropFormatted;
			if (value != null)
				zContent.setAttribute("tooltip", value);
			zContent.setAttribute("visible", true);

			if (firstFocus && (row.getEstado() == JFormRow.NEW || row.getEstado() == JFormRow.LAST)) {
				comp.setForceFocus(true);
				firstFocus = false;
			}

			if (comp != null) {
				if (last) {
					JWebAction actOnBlur = this.getActionOnBlur(row);
					if (actOnBlur != null)
						comp.setOnBlur(actOnBlur);
				}

				zContent.startNode("control");
				comp.setInTable(getTableName(), rowpos, (comp.getControl() != null && !comp.getControl().isEditable()) && ((JWinGrid) winGrid).isUploadOnlyEditable() ? -1 : lastCol++);
				comp.toXML(zContent);
				zContent.endNode("control");

			} else
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
		return visibleRow;
	}

	boolean firstFocus = false;

	boolean rowToXML(JXMLContent zContent, JWebRowResponsive row, JWin zWin, JColumnaLista[] aColumns, boolean zWithIcon, int iRowPos, JWins iOldSelected, String iOldSingleSelect, boolean firstLevel) throws Exception {
		zContent.setAttribute("rowpos", iRowPos);
		// if (this.hasToolbar() && row.getEstado()==JFormRow.NOEDIT) {
		// zWin.getRecord().keysToFilters();
		// String forcedPos = getNotebookParent() + "" +
		// this.getWins().getClass().getSimpleName() + "_" + iRowPos;
		// String id = zContent.addObject(forcedPos, zWin);
		// this.rowToXMLActionBar(zContent, zWin, id);
		//
		// }

		String advice = "";
		boolean ok = true;
		try {
			if (row.getEstado() == JFormRow.UPDATE)
				row.getWin().getRecord().validateRecord();
			else if (row.getEstado() == JFormRow.DELETE)
				advice = "Presione para RECUPERAR REGISTRO BORRADO";
			else if (row.getEstado() == JFormRow.NEW)
				advice = "Presione para GRABAR NUEVO REGISTRO";
			else if (row.getEstado() == JFormRow.LAST)
				advice = "Presione para GRABAR NUEVO REGISTRO";

		} catch (Exception e) {
			advice += "| ERROR: " + e.getMessage();
			ok = false;
		}
		lastCol = 0;

		if (advice != null)
			zContent.setAttribute("tooltip", advice);

		if (row.getEstado() == JFormRow.DELETE) {
			zContent.setAttribute("deleted", true);
			iDeleted++;
		}
		if (row.getEstado() == JFormRow.NEW || row.getEstado() == JFormRow.LAST) {
			// zContent.setAttribute("deleted", true);
			iNews++;
		}

		if (zWithIcon) {
			GuiIcon icon = GuiIconos.GetGlobal().buscarIcono(GuiIconos.RESPONSIVE, row.getEstado() == JFormRow.NOEDIT ? 61 : (row.getEstado() == JFormRow.LAST) ? 11 : row.getEstado() == JFormRow.DELETE ? 17000 : ok ? (zWin.getRecord().detectIsComplete() ? 12 : 0) : 0);
			JWebIcon.getResponsiveIcon(icon.GetFile()).toXML(zContent);
			;

			// String iconFile =
			// this.findIcon(GuiIconos.RESPONSIVE,row.getEstado()==JFormRow.NOEDIT?zWin.GetNroIcono():(row.getEstado()==JFormRow.LAST)?11:row.getEstado()==JFormRow.DELETE?12:ok?61:0);
			// if (iconFile == null) iconFile = "";
			// JWebIcon.getPssIcon(iconFile).toXML(zContent);
		}
		zContent.startNode("control");
		invisibleToXML(row);
		zContent.endNode("control");

		// each data cell represented as a <d> element
		JColumnaLista oCol;
		boolean visibleRow = true;
		long lastField = getLastUtil(zWin,row, aColumns);
		for (int i = 0; i < aColumns.length; i++) {
			oCol = aColumns[i];
			fieldToXML(row, iRowPos, zContent, zWin, oCol, advice, lastField == i, i);
		}
		visibleRow = !zWin.getRecord().detectIsComplete() ? true : checkManualFilter(row, zWin);

		zContent.startNode("row_control");
		zContent.setAttribute("visible", visibleRow);
		zContent.endNode("row_control");

		JWebAction actDblClick = this.getActionDefault(row);
		if (actDblClick != null) {
			zContent.startNode("dblclick");
			actDblClick.toXML(zContent);
			zContent.endNode("dblclick");
			if (zWithIcon) {
				zContent.startNode("linkOnIcon");
				actDblClick.toXML(zContent);
				zContent.endNode("linkOnIcon");
			}
		}

		return visibleRow;
	}

	public long getLastUtil(JWin win,JWebRowResponsive row, JColumnaLista[] aColumns) throws Exception {
		String id = win.getLastFieldInGrid();
	
		int last = 0;
		for (int i = 0; i < aColumns.length; i++) {
			JColumnaLista oCol = aColumns[i];
			if (id!=null && id.equals(oCol.GetCampo())) {
				last = i;
				break;
			}
			if (!oCol.isVisible())
				continue;
			JWebViewComponent comp = null;
			JWebFormControl wcomp = (JWebFormControl) row.getBaseForm().getControles().findControl(oCol.GetCampo());
			if (wcomp == null)
				continue;
			comp = (JWebViewComponent) wcomp.getWebComponent();
			if (comp == null)
				continue;
			if (!comp.isVisible())
				continue;
			if (comp instanceof JWebViewEditComponent) {
				if (!((JWebViewEditComponent) comp).isEditable())
					continue;
			}
			last = i;
		}

		return last;
	}

	JColumnaLista[] determineColumns(JWinList zWinList, boolean onlyVisible) throws Exception {
		if (oColumns != null)
			return oColumns;
		this.winGrid.ConfigurarColumnasLista();
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
		return oColumns = columns;
	}

	protected JWebAction getActionDefault(JWebRowResponsive row) throws Exception {
		if (row.getEstado() == JFormRow.NOEDIT || !bWithIconVisible) {
			BizAction action = row.getWin().findActionByUniqueId(row.getWin().getDobleClick());
			if (action == null)
				action = row.getWin().findActionByUniqueId(row.getWin().getDobleClickSecondChance());
			if (action == null)
				action = row.getWin().findAction(JWin.ACTION_DROP);
			if (action == null)
				action = row.getWin().findAction(JWin.ACTION_QUERY);
			if (action == null)
				return null;
			action.setUploadData(true);
			return JWebActionFactory.createViewAreaAndTitleAction(action, this, false, JWebActionFactory.registerObject(row.getWin()));

		}

		String param = null;
		if (row.getEstado() == JFormRow.LAST)
			param = "" + JFormRow.UPDATE;
		else if (row.getEstado() == JFormRow.NEW || row.getEstado() == JFormRow.UPDATE)
			param = "" + JFormRow.DELETE;
		else if (row.getEstado() == JFormRow.DELETE)
			param = "" + JFormRow.UPDATE;
		return JWebActionFactory.createPartialRefreshFormAction(this.getSourceAction(), this.findJWebWinForm(), null, row.getProvider(), "internal_estado_fila", param);
	}

	protected JWebAction getActionOnBlur(JWebRowResponsive row) throws Exception {
		if (row.getEstado() == JFormRow.NOEDIT) {
			return null;
		}

		if (row.getEstado() != JFormRow.LAST)
			return null;
		String param = null;
		param = "" + JFormRow.UPDATE;
		return JWebActionFactory.createPartialRefreshFormAction(this.getSourceAction(), this.findJWebWinForm(), null, row.getProvider(), "internal_estado_fila", param);
	}

	protected int getRowSpan(String zColumn, String zValue) {
		return 0;
	}

	protected void navigationBarToXML(JXMLContent zContent, int iRowCursor, int eliminados, int nuevos) throws Exception {
		if (this.oNavigationBar == null)
			return;
		JWebLabel oNavInfo = new JWebLabel();
		oNavInfo.setSkinStereotype("win_list_navigation_bar_label");
		this.oNavigationBar.add("nav_info_lbl", oNavInfo);
		this.oNavigationBar.resolvePadding();
		String info = "Registros a agregar-actualizar: " + (iRowCursor - eliminados - nuevos);
		info += (eliminados == 0) ? "" : " ( eliminados: " + eliminados + " )";
		info += (nuevos == 0) ? "" : " PENDIENTES DE CONFIRMAR ALTA: " + nuevos;

		oNavInfo.setLabel(info);
		// oNavInfo.setSize(this.oNavigationBar.getSize().width - 200,
		// this.oNavigationBar.getSize().height);

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

	// public String getRegisteredObjectId() {
	// return winsObjectId;
	// }
	// public String getProviderName() throws Exception {
	// return this.getName();
	// }

	// public String getSourceObjectId() {
	// return this.sourceObjectId;
	// }

	// public String getName() {
	// return "win_grid";
	// }

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

	// public boolean isShowActionBar() {
	// return bShowActionBar;
	// }
	//
	// public void setShowActionBar(boolean showActionBar) {
	// bShowActionBar = showActionBar;
	// }

	public String getScroll(JXMLContent zContent) throws Exception {
		JWebRequest oRequest = zContent.getGenerator().getRequest();
		if (oRequest.getSession().getHistoryManager().getLastHistory() == null)
			return "";
		return oRequest.getSession().getHistoryManager().findLastScroll(this.sourceAction);
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

	protected String findIcon(int icono) throws Exception {
		try {

			GuiIcon oIcon = GuiIconos.GetGlobal().buscarIcono(icono);
			if (oIcon == null)
				return "";

			return oIcon.GetFile();

		} catch (Exception e) {
			return "";
		}
	}

	@Override
	public String getProviderName() throws Exception {
		if (this.sourceAction == null)
			return "provider";
		return this.sourceAction.getProviderName() + "_" + getTable().getCampo();
	}

	/**
	 * Esta clase interna, articula el armado de xml. Deberia contener la logica
	 * del recorrido de los datos, pero lo menos posible de la logica del
	 * JWebWinList
	 *
	 */
	JWebRequest oRequest;
	JXMLContent zContent;

	JWin oFirstWin = null;
	int iRowCursor = -1;
	int iDeleted = 0;
	int iNews = 0;
	boolean supportOffset = false;
	boolean goesNext = false;
	boolean goesBack = false;
	boolean bWithIcon;
	boolean bWithIconVisible;
	int iToRow;
	String oldSingleSelect;
	JWins oldSelect;
	JColumnaLista[] oColumns;

	boolean startDeclarations() throws Exception {
		if (getWins() == null)
			return false;
		oRequest = zContent.getGenerator().getRequest();
		setTableName("dgt_form_" + getProviderName() + "_table");
		zContent.setAttribute("has_action_bar", hasActionBar());
		zContent.setAttribute("obj_provider", getProviderName());
		zContent.setAttribute("is_multiple_select", isMultipleSelection());
		zContent.setAttribute("has_select", hasSelectRows());
		zContent.setAttribute("tablename", getTableName());
		zContent.setAttribute("field", getTable().getCampo());
		zContent.setAttribute("is_line_select", true);
		zContent.setAttribute("editable", winGrid.isEnabled());
		zContent.setAttribute("modifiedonserver", bIsModifiedOnServer);
		zContent.setAttribute("scroll", getScroll(zContent));
		zContent.setAttribute("class_table_responsive", getClassTableResponsive());

		return !JWebActionFactory.isOtherObjectExportRequest(zContent.getGenerator().getRequest(), this);
	}

	protected void readData() throws Exception {
		winGrid.ConfigurarColumnasLista();
		zContent.setAttribute("title", getWins().GetTitle());

		String alerta = getWins().anyAlert(-1);
		if (alerta != null)
			setAlert(alerta); // Warning referidos a los filtros
		this.winGrid.getWins().firstRecord();

	}

	void generateHeader() throws Exception {
		headerToXML(getWins(), zContent, oFirstWin, oColumns);
	}

	void generateRows() throws Exception {
		zContent.startNode("rows");

		zContent.setAttribute("zobject", winsObjectId);
		firstFocus = true;
		long hideRows = 0;
		boolean visibleRow = false;

		iRowCursor = 0;
		JIterator<JWebViewComponent> it = this.getTable().getChildren();
		while (it.hasMoreElements()) {
			JWebViewComponent fco = it.nextElement();
			if (!(fco instanceof JWebRowResponsive))
				continue;
			JWebRowResponsive row = (JWebRowResponsive) fco;
			zContent.startNode("row");
			visibleRow = rowToXML(zContent, row, row.getWin(), oColumns, bWithIcon, iRowCursor++, oldSelect, oldSingleSelect, true);
			if (!visibleRow)
				hideRows++;
			zContent.endNode("row");
		}
		generateExtraRows();
		zContent.endNode("rows");
		setHideRows(hideRows);

	}

	void generateFooter() throws Exception {

		navigationBarToXML(zContent, iRowCursor, iDeleted, iNews);
		if (this.hasPosFunction()) {
			zContent.startNode("posfunction");
			zContent.setAttribute("script", this.getPosFunction());
			zContent.endNode("posfunction");
		}

	}

	void generateExtraRows() throws Exception {
		if (iRowCursor > getMinumusRows())
			return;
		if (isWinListFullExportRequest)
			return;
		JWin oWin = getWins().getWinRef();
		
		long lastPos = iRowCursor;
		while (lastPos < getMinumusRows()) {
			zContent.startNode("row");
			zContent.setAttribute("rowpos", lastPos);
			// each data cell represented as a <d> element
			if (bWithIcon) {
				GuiIcon icon = GuiIconos.GetGlobal().buscarIcono(GuiIconos.RESPONSIVE,61 );
				JWebIcon.getResponsiveIcon(icon.GetFile()).toXML(zContent);
			}
			JColumnaLista oCol;
			for (int i = 0; i < oColumns.length; i++) {
				oCol = oColumns[i];
				fieldToXMLVoid(lastPos, zContent, oWin, oCol, i);
			}
			zContent.endNode("row");
			lastPos++;
		}

	}

	boolean fieldToXMLVoid(long rowpos, JXMLContent zContent, JWin zWin, JColumnaLista oCol, int idCol) throws Exception {
		boolean visibleRow = true;
		if (oCol == null)
			return visibleRow;
		if (oCol.IfIcono())
			return visibleRow;
		try {
			String sColName = oCol.GetCampo();
			String sPropFormatted = "";

			visibleRow = true;

			zContent.startNode("d");
			zContent.setAttribute("marked", zWin.isMarked(sColName));
			if (zWin.isWordWrap(sColName))
				zContent.setAttribute("word_wrap", zWin.isWordWrap(sColName));
			zContent.setAttribute("height", zWin.getHeightRow());
			zContent.setAttribute("axis", idCol);

			String value = zWin.getFieldForeground(sColName) != null ? zWin.getFieldForeground(sColName) : oCol.getColorForeground();
			if (value != null) {
				zContent.setAttribute("foreground", JTools.toHtmlColor(value));
			}
			value = null;
			if (value == null)
				value = zWin.getFieldBackground(sColName) != null ? zWin.getFieldBackground(sColName) : oCol.getColorBackground();
			if (value != null)
				zContent.setAttribute("background", JTools.toHtmlColor(value));
			zContent.setAttribute("visible", true);

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
		return visibleRow;
	}

	// @Override
	// public BizAction getSourceAction() {
	// return sourceAction;
	// }

	@Override
	public JWebAction getWebSourceAction() throws Exception {
		return JWebActionFactory.createViewAreaAndTitleAction(this.sourceAction, this, true, null);
	}

}
