package pss.www.ui;

import java.awt.Dimension;

import pss.common.security.BizUsuario;
import pss.core.tools.JDateTools;
import pss.core.tools.collections.JIterator;
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
import pss.core.winUI.lists.JWinGridExpand;
import pss.core.winUI.lists.JWinList;
import pss.www.platform.actions.JWebAction;
import pss.www.platform.actions.JWebActionFactory;
import pss.www.platform.actions.JWebRequest;
import pss.www.platform.content.generators.JXMLContent;

public class JWebWinGridExpandResponsive extends JWebWinGenericResponsive implements JAbsolutelyNamedWebViewComponent {

	
	protected JWebTableExpandResponsive table = null;

	protected JWinList winGrid = null;
	boolean isWinListFullExportRequest;

	public boolean isShowFilterBar() throws Exception {
		return false;
	}
	public void setWinList(JWinList w) {
		 winGrid=w;
	}
	public JWebTableExpandResponsive getTable() {
		return table;
	}
	public void setTable(JWebTableExpandResponsive table) {
		this.table = table;
	}
	//
	// CONSTRUCTOR
	//
	
	@Override
	public JWins getWins() throws Exception {
		if (oWins!=null) return oWins;
		this.oWins = this.winGrid.getWins();
		return this.oWins;
	}
	
	
	public JWebWinGridExpandResponsive( BizAction action, JWinGridExpand zWinList, JWebTableExpandResponsive oTable) throws Exception {
		this.sourceAction=action;
		this.winGrid=zWinList;
		this.setTable(oTable);
		this.setWidthBorder(BizUsuario.retrieveSkinGenerator().getBorderWebWinForm());
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
//	protected boolean hasActionBar() throws Exception {
//		return this.oActionBar != null && this.isShowActionBar();
//	}


	public String getPresentation() throws Exception {
		return JBaseWin.MODE_LIST;
	};

		
	@Override
	protected void build() throws Exception {
		if (this.winGrid == null)	this.winGrid = new JWinGridExpand(getWins());
		this.regiterObjects();

		super.build();
	}

//	public JWebPanel buildList() throws Exception {
//		JWebPanel centerPanel = this.getTableWithFilters();
//		JWebPanel borderPanel = this.getPanelWithBorder(centerPanel);
//		JWebPanel completePanel = this.createCompletePanel(borderPanel);
//		return completePanel;
//	}
//
//	

//	protected JWebViewComposite buildToolbarLeft() throws Exception {
//		JWebPanel centerPanel = this.getTableWithFilters();
//		JWebPanel borderPanel = this.getPanelWithBorder(centerPanel);
//		
//		JWebSplit oSplit = new JWebSplit();
//		oSplit.setName("split_pane");
//
//		JWebPanel oPanelA = new JWebPanel();
//		JWebPanel oPanelB = new JWebPanel();
//
//		oSplit.setPosSplit(splitPoint);
//
//		JWebBorderLayout oPanelBLayout = new JWebBorderLayout();
//		oPanelB.setSkinStereotype("split_panel");
//		oSplit.setPanelB(oPanelB);
//		oSplit.addChild("splitB", oPanelB);
//
//
//		oPanelBLayout.setCenterComponent(borderPanel);
//		oPanelB.setLayout(oPanelBLayout);
//		oPanelB.addChild("win_list_border", borderPanel);
//
//		
//		JWebBorderLayout oLayoutA = new JWebBorderLayout();
//		oPanelA.setSkinStereotype("split_panel");
//		oPanelA.setLayout(oLayoutA);
//		oSplit.setPanelA(oPanelA);
//		oSplit.addChild("splitA", oPanelA);
//		
//		this.embbedActionBar(oPanelA);
//		return oSplit;
//	}
	
//	
//	protected JWebPanel buildOnlyTable() throws Exception {
//		JWebPanel panel = this.getWinListTablePanel();
////		this.oActionBar = this.createActionBar();
////		panel.addChild(oActionBar.getName(), this.oActionBar); // no layouttear
//		return panel;
//	}

		
	
//	protected JWebPanel getTableWithFilters() throws Exception {
//		JWebPanel centerPanel = new JWebPanel();
//		centerPanel.setName("win_list_center_panel");
//		JWebBorderLayout layout = new JWebBorderLayout();
//		centerPanel.setLayout(layout);
//		if (this.oWins!=null && this.oWins.hasNavigationBar()) {
//			createNavigationBars();
//		}
//		JWebPanel winListTablePanel = this.getWinListTablePanel();
//		centerPanel.addChild("win_list_table_panel", winListTablePanel);
//		layout.setCenterComponent(winListTablePanel);
//	
//		if (this.oNavigationBar!=null) {
//			centerPanel.addChild("win_list_navigation_bar", this.oNavigationBar);
//			layout.setBottomComponent(this.oNavigationBar);
//		}		
//
//		return centerPanel;	
//	}
	
//	protected JWebPanel getPanelWithBorder(JWebPanel panel) throws Exception {
//		JWebPanel borderPanel = new JWebPanel();
//		borderPanel.setName("win_list_border");
//		JWebBorderLayout layout;
//		layout = new JWebBorderLayout(0, 0);
//		borderPanel.setSkinStereotype("win_grid_border_embedded");
//		borderPanel.setLayout(layout);
//		borderPanel.addChild(panel.getName(), panel);
//		layout.setCenterComponent(panel);
//		return borderPanel;
//	}


//	protected JWebPanel createNavigationBars() throws Exception {
//		this.oNavigationBar = new JWebPanel();
//		this.oNavigationBar.setSkinStereotype("win_list_navigation_bar");
//		this.oNavigationBar.setName("win_list_navigation_bar");
//		this.oNavigationBar.setLayout(new JWebFlowLayout(ORIENTATION_HORIZONTAL, HALIGN_RIGHT, VALIGN_MIDDLE, VALIGN_MIDDLE));
//		return this.oNavigationBar;
//	
//	}

	protected void appendNextControl(JFormControl c, JWebPanel panel, JWebPanel flowPanel) throws Exception {
		if (c.getNextControl()==0) return;
		if (c.getNextControl()==JFormControl.NEXT_BREAK) {
			flowPanel.addChild(new JWebFlowBreak());
			return;
		}
		if (c.getNextControl()==JFormControl.NEXT_LINE) {
			flowPanel.addChild(new JWebFlowBreak());
			JWebTitledBorder t =new JWebTitledBorder();
			t.setSize((int)panel.getSize().getWidth(), 2);
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
		return "win_grid_expand";
	}



		@Override
	protected void containerToXML(JXMLContent zContent) throws Exception {
	}

		
	
	@Override	
	protected void componentPreLayout(JXMLContent zContent) throws Exception {
		if (getWins()==null) return;
		attachActionBar();


	}
	
	@Override
	protected void componentToXML(JXMLContent zContent) throws Exception {
		this.zContent = zContent;

		if (!this.startDeclarations()) return;		

		this.readData();
		this.generateHeader();
		this.generateRows();
		this.generateFooter();
	}
	


		

	




	protected boolean headerToXML(JWins zWins, JXMLContent zContent, JWin oFirstWin,  JColumnaLista[] oColumns) throws Exception {
		// generate the header columns
			
				iDeleted=0;
				iNews=0;
		zContent.startNode("header");
		zContent.setAttribute("datetime", JDateTools.CurrentDateTime());
    zContent.endNode("header");
		return bWithIcon;
	}

	protected void footToXML(JXMLContent zContent, JTotalizer zTot, JColumnaLista[] oColumns) throws Exception {
//		zContent.startNode("footer");
//
//		zContent.endNode("footer");
	}



	

	//
	// INTERNAL IMPLEMENTATION
	//



	

  boolean firstFocus=false;
	void rowToXML(JXMLContent zContent,JWebRowGridExpandResponsive row, JWin zWin, JColumnaLista[] aColumns, boolean zWithIcon, int iRowPos, JWins iOldSelected, String iOldSingleSelect, boolean firstLevel) throws Exception {
		  zContent.setAttribute("rowpos", iRowPos);

			row.toXML(zContent);
		
			JWebAction actDblClick = this.getActionDefault(row);
			if (actDblClick != null ) {
				zContent.startNode("dblclick");
				actDblClick.toXML(zContent);
				zContent.endNode("dblclick");
				if (zWithIcon) {
					zContent.startNode("linkOnIcon");
					actDblClick.toXML(zContent);
					zContent.endNode("linkOnIcon");
				}
			}
		
	}
	
	


	
	protected JWebAction getActionDefault(JWebRowGridExpandResponsive row) throws Exception {
		if (row.getEstado()==JFormRow.NOEDIT) {
			BizAction action = row.getWin().findActionByUniqueId(row.getWin().getDobleClick());
			if (action==null) action = row.getWin().findActionByUniqueId(row.getWin().getDobleClickSecondChance());
			if (action==null) action=row.getWin().findAction(JWin.ACTION_DROP);
			if (action==null)	action=row.getWin().findAction(JWin.ACTION_QUERY);
			if (action==null) return null;
			return JWebActionFactory.createViewAreaAndTitleAction(action, this, false, null);

		}
		
		String param=null;
		if (row.getEstado()==JFormRow.NEW)
			param=""+JFormRow.UPDATE;
		else if (row.getEstado()==JFormRow.UPDATE)
			param=""+JFormRow.DELETE;
		else if (row.getEstado()==JFormRow.DELETE)
			param=""+JFormRow.UPDATE;
		return JWebActionFactory.createPartialRefreshFormAction(this.getSourceAction(),this.findJWebWinForm(), null,row.getProvider(),"internal_estado_fila",param);
	}
	protected JWebAction getActionOnBlur(JWebRowGridExpandResponsive row) throws Exception {
		if (row.getEstado()==JFormRow.NOEDIT) {
			return null;
		}
		
		if (row.getEstado()!=JFormRow.NEW) return null;
		String param=null;
		param=""+JFormRow.UPDATE;
		return JWebActionFactory.createPartialRefreshFormAction(this.getSourceAction(),this.findJWebWinForm(), null,row.getProvider(),"internal_estado_fila",param);
	}

	protected int getRowSpan(String zColumn, String zValue) {
		return 0;
	}



	protected void navigationBarToXML(JXMLContent zContent, int iRowCursor, int eliminados, int nuevos) throws Exception {
		if (this.oNavigationBar == null) return;			
		JWebLabel oNavInfo = new JWebLabel();			
		oNavInfo.setSkinStereotype("win_list_navigation_bar_label");			
		this.oNavigationBar.add("nav_info_lbl", oNavInfo);			
		this.oNavigationBar.resolvePadding();		
		String info ="Registros a agregar-actualizar: " + ( iRowCursor-eliminados-nuevos);
		info+=(eliminados==0)?"":" ( eliminados: "+eliminados+" )";

		

		oNavInfo.setLabel(info);
	
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


	public String getScroll(JXMLContent zContent) throws Exception {
		JWebRequest oRequest = zContent.getGenerator().getRequest();
		if (oRequest.getSession().getHistoryManager().getLastHistory() == null)
			return "";
		return oRequest.getSession().getHistoryManager().findLastScroll(this.sourceAction);
	}




	/*
	public void setBackAction(boolean value) {
		this.backAction = value;
	}*/

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
				
				GuiIcon oIcon=GuiIconos.GetGlobal().buscarIcono(icono);
				if (oIcon==null) return"";

				return oIcon.GetFile();

		} catch (Exception e) {
			return "";
		}
	}
	
	@Override
	public String getProviderName() throws Exception {
		if (this.sourceAction == null)
			return "provider";
		return this.sourceAction.getProviderName()+"_"+getTable().getCampo();
	}

	/**
	 * Esta clase interna, articula el armado de xml. Deberia contener la logica del recorrido de los datos, pero lo menos posible de la logica del JWebWinList
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
		int iToRow;
		String oldSingleSelect;
		JWins oldSelect;
		JColumnaLista[] oColumns;
		
		boolean startDeclarations() throws Exception {
			if (getWins()==null) return false;
			oRequest = zContent.getGenerator().getRequest();

			zContent.setAttribute("has_action_bar", hasActionBar());
			zContent.setAttribute("obj_provider", getProviderName());
			zContent.setAttribute("form_name", getProviderName());
			zContent.setAttribute("is_multiple_select", false);
			zContent.setAttribute("is_line_select", true);
			zContent.setAttribute("editable", winGrid.isEnabled());
			zContent.setAttribute("scroll", getScroll(zContent));
			zContent.setAttribute("class_table_responsive", getClassTableResponsive());

			return !JWebActionFactory.isOtherObjectExportRequest(zContent.getGenerator().getRequest(), this);
		}

		
		protected void readData() throws Exception {
	    winGrid.ConfigurarColumnasLista();
			zContent.setAttribute("title", getWins().GetTitle());
			
			String alerta = getWins().anyAlert(-1);
			if (alerta!=null) setAlert(alerta); // Warning referidos a los filtros
			this.winGrid.getWins().firstRecord();
			
		}

	
		
		void generateHeader() throws Exception {
			bWithIcon = headerToXML(getWins(),zContent, oFirstWin, oColumns);
		}

		void generateRows() throws Exception {
			zContent.startNode("rows");

			zContent.setAttribute("zobject", winsObjectId);
			firstFocus=true;

			iRowCursor=0;
			JIterator<JWebViewComponent> it = this.getTable().getChildren();
			while (it.hasMoreElements()) {
				JWebViewComponent fco = it.nextElement();
				if (!(fco instanceof JWebRowGridExpandResponsive)) continue;
				JWebRowGridExpandResponsive row = (JWebRowGridExpandResponsive)fco;
				zContent.startNode("row");
				rowToXML(zContent, row, row.getWin(), oColumns, bWithIcon, iRowCursor++, oldSelect, oldSingleSelect, true);
				zContent.endNode("row");
			}
			zContent.endNode("rows");
			
		}
	
		void generateFooter() throws Exception {

				navigationBarToXML(zContent, iRowCursor, iDeleted, iNews);
		}
	
	
	



//	@Override
//	public BizAction getSourceAction() {
//		return sourceAction;
//	}

	@Override
	public JWebAction getWebSourceAction() throws Exception {
  	return JWebActionFactory.createViewAreaAndTitleAction(this.sourceAction, this, true, null);
	}


}
