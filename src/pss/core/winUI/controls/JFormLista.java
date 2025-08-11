package pss.core.winUI.controls;

import pss.common.security.BizUsuario;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;
import pss.core.win.JBaseWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.winUI.lists.JWinList;
import pss.core.winUI.responsiveControls.JFormControlResponsive;
import pss.core.winUI.responsiveControls.JFormTabPanelResponsive;
import pss.core.winUI.responsiveControls.JFormTabResponsive;
import pss.core.winUI.responsiveControls.TableFilterManual;

public class JFormLista  extends JFormTabResponsive {
	public static final String MODE_NORMAL = "MODE_NORMAL";
	public static final String MODE_DESPOJADO = "MODE_DESPOJADO";
	public static final String MODE_VERTICAL = "MODE_VERTICAL";
	public static final String MODE_CUADRICULA = "MODE_CUADRICULA";
	public static final String MODE_ONLYTABLE = "MODE_ONLYTABLE";
	public static final String MODE_CUSTOMLIST = "MODE_CUSTOMLIST";


	// -------------------------------------------------------------------------//
	// Propiedades de la Clase
	// -------------------------------------------------------------------------//
	private JWinList oWinLista;
	private BizAction oAction;
	private JFormTabPanelResponsive oTabPane;
	private JWin oWin;
	private Boolean bFilterBar=null;
	private String sToolBar=null;
	private String mode;
	private String distributionObjects;
	private boolean bListaResponsive=true;
	private String campo;
	protected Boolean bUseContextMenu = null;
	private boolean bNavigationBar=true;

	private JWin winCampo;
	private Class winClassCampo;
	private JMap<String,TableFilterManual> manualFilter;
	private JMap<String,String> updateControls;


	private boolean bListChanged=false;
	private boolean readOnly=false;
  boolean needRefreshAllScreen=false;
	protected boolean bForceHidePaginationBar = false;
	protected boolean allowSortedColumns = true;
	private boolean bShowFooterBar=true;
	private long zoomtofit;
	private long extraRows=10;
	private long minumusRows=10;

	public long getMinimusRows() {
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
	public long getZoomtofit() {
		return zoomtofit;
	}
	
	public JFormLista setZoomtofit(long zoomtofit) {
		this.zoomtofit = zoomtofit;
		return this;
	}

	public boolean isShowFooterBar() {
		return bShowFooterBar;
	}

	public JFormLista setShowFooterBar(boolean bShowFooterBar) {
		this.bShowFooterBar = bShowFooterBar;
		return this;
	}

	public boolean isAllowSortedColumns() {
		return allowSortedColumns;
	}

	public JFormLista setAllowSortedColumns(boolean allowSortedColumns) {
		this.allowSortedColumns = allowSortedColumns;
		return this;
	}

	public boolean isForceHidePaginationBar() {
		return bForceHidePaginationBar;
	}

	public JFormLista setForceHidePaginationBar(boolean bForceHidePaginationBar) {
		this.bForceHidePaginationBar = bForceHidePaginationBar;
		return this;
	}
  public boolean isNeedRefreshAllScreen() {
		return needRefreshAllScreen;
	}

	public void setNeedRefreshAllScreen(boolean needRefreshAllScreen) {
		this.needRefreshAllScreen = needRefreshAllScreen;
	}

	public boolean isReadOnly() {
		return readOnly;
	}

	public JFormControlResponsive setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
		return this;
	}
	
	public boolean isListaResponsive() {
		return bListaResponsive;
	}

	public void setListaResponsive(boolean bListaResponsive) {
		this.bListaResponsive = bListaResponsive;
	}

	public String getDistributionObjects() {
		return distributionObjects;
	}

	public JFormLista setDistributionObjects(String distributionObjects) {
		this.distributionObjects = distributionObjects;
		return this;
	}

	public String getMode() {
		return mode;
	}

	public JFormLista setMode(String mode) {
		this.mode = mode;
		return this;
	}
	public JMap<String, String> getUpdateControls() {
  	if (updateControls==null) updateControls = JCollectionFactory.createMap();
		return updateControls;
	}
	public void setUpdateControl(JMap<String, String> zUpdateControls) {
		this.updateControls = zUpdateControls;
	}
	public void addUpdateControl(String campo,String value) throws Exception {
		getUpdateControls().addElement(campo, value);
	}
//	public boolean isToolBarTop() {
//		return getToolbar().equals(JBaseWin.TOOLBAR_TOP);
//	}
//
//	public boolean isToolBarRight() {
//		return getToolbar().equals(JBaseWin.TOOLBAR_RIGHT);
//	}
//	public boolean isToolBarBottom() {
//		return getToolbar().equals(JBaseWin.TOOLBAR_BOTTOM);
//	}
//	
//	public boolean isToolBarIn() {
//		return getToolbar().equals(JBaseWin.TOOLBAR_IN);
//	}
	public String getToolbar() {
		return sToolBar;
	}

	public void setToolBar(String value) {
		if (this.oWinLista!=null) 
			this.oWinLista.setToolbar(value);
		this.sToolBar = value; // creo este value no sirve para nada HGK
	}

	private String sPreviewFlag=JWins.PREVIEW_MAX;
	public String getPreviewFlag() {
		return sPreviewFlag;
	}

	public void setPreviewFlag(String v) {
		this.sPreviewFlag = v;
	}
	// -------------------------------------------------------------------------//
	// Metodos de acceso a las Propiedades de la Clase
	// -------------------------------------------------------------------------//
	public Boolean isFilterBar() throws Exception {
		if (bFilterBar==null)
			bFilterBar =  BizUsuario.getUsr()==null?false:BizUsuario.getUsr().getObjBusiness().isFilterBarOpenByDefault();
		return bFilterBar;//aca tiene que salir true
	}

	public void setFilterBar(Boolean bFilterBar) {
		this.bFilterBar = bFilterBar;
	}

	public void setNavigationBar(boolean v) {
		this.bNavigationBar = v;
	}

	public boolean hasNavigationBar() {
		return this.bNavigationBar;
	}

	public void setWin(JWin zValue) {
		oWin=zValue;
	}

	public void setTabPane(JFormTabPanelResponsive zValue) {
		oTabPane=zValue;
	}

	public void SetWinLista(JWinList zValue) {
		oWinLista=zValue;
	}

	public void setAction(BizAction zValue) {
		oAction=zValue;
	}

	public JWinList GetLista() {
		return oWinLista;
	}
//
//	public JPanel GetListaPanel() {
//		return oWinLista.getRootPanel();
//	}



	public BizAction getAction() {
		return this.oAction; 
	}
	public JMap<String, TableFilterManual> getManualFilter() {
  	if (manualFilter==null) manualFilter = JCollectionFactory.createMap();
		return manualFilter;
	}
	public void setManualFilter(JMap<String, TableFilterManual> manualFilter) {
		this.manualFilter = manualFilter;
	}
	public void addFilterManual(String campo,String[] col,String op) throws Exception {
  	TableFilterManual t = new TableFilterManual();
  	t.setField(campo);
  	t.setColumns(col);
  	t.setOperacion(op);
  	t.setControl(getControls().getBaseForm().findControl(campo).getClass().getSimpleName());
  	getManualFilter().addElement(campo, t);
	}
	// -------------------------------------------------------------------------//
	// Constructor
	// -------------------------------------------------------------------------//
	public JFormLista() {
	}

	// -------------------------------------------------------------------------//
	// Crear Lista
	// -------------------------------------------------------------------------//

	// -------------------------------------------------------------------------//
	// Blanqueo el campo
	// -------------------------------------------------------------------------//
	@Override
	public void clear() {
	}

	// -------------------------------------------------------------------------//
	// Protejo el campo
	// -------------------------------------------------------------------------//
	@Override
	public void disable() throws Exception {
		oWinLista.setEnabled(true);
	}
	
	public String getCampo() {
		return campo;
	}

	public void setCampo(String campo) {
		this.campo = campo;
	}

	public JWin getWinCampo() {
		return winCampo;
	}

	public void setWinCampo(JWin winCampo) {
		this.winCampo = winCampo;
	}

	public Class getWinClassCampo() {
		return winClassCampo;
	}

	public void setWinClassCampo(Class winClassCampo) {
		this.winClassCampo = winClassCampo;
	}

	// -------------------------------------------------------------------------//
	// Seteo el foco en el campo
	// -------------------------------------------------------------------------//
//	@Override
//	public void SetFocus() {
//	}

	// -------------------------------------------------------------------------//
	// Edito el campo
	// -------------------------------------------------------------------------//
	@Override
	public void edit(String zModo) throws Exception {

		// HGK no se para q esta esto pero me duplica el tab en modo alta asi que lo comento
		// no se que rompo!!!
//		if (zModo==MODO_ALTA) {
			// oWinLista.setEnabled(false);
//			if (oTabPane!=null && oTabPane.hasTabs()) {
//				if (!this.addPanelToTab()) return;
//				this.changeListAttributes();
//	//			oWinLista.RefreshLista();
//				oWinLista.crearRootPanel();
//				return;
//			}
//		}
		oWinLista.setEnabled(true);
		
	}

	private void restoreListAttributes() throws Exception {
		if (!this.bListChanged) return;
		oWinLista.SetFiltrosBar(this.bFilterBar);
		oWinLista.setToolbar(this.getToolbar());
		this.bListChanged=false;
	}

	private void changeListAttributes() throws Exception {
		this.bFilterBar=oWinLista.isFilterBar();
		this.sToolBar=oWinLista.getToolbar();
		oWinLista.SetFiltrosBar(false);
		oWinLista.setToolbar(JBaseWin.TOOLBAR_NONE);
		this.bListChanged=true;
	}
	public Boolean getUseContextMenu() {
		return bUseContextMenu;
	}

	public void setUseContextMenu(Boolean bUseContextMenu) {
		this.bUseContextMenu = bUseContextMenu;
	}
	// -------------------------------------------------------------------------//
	// Obtengo el valor del campo
	// -------------------------------------------------------------------------//
	@Override
	public String getSpecificValue() {
		return null;
	}

	// -------------------------------------------------------------------------//
	// Obtengo el valor del campo
	// -------------------------------------------------------------------------//
	@Override
	public boolean hasValue() {
		return true;
	}

	public BizAction getCheckedAction() throws Exception {
		return oWin.findActionByUniqueId(oAction.getIdAction());
	}

	private boolean addPanelToTab() throws Exception {
		oTabPane.removeTab(oAction.getIdAction());
		BizAction action=this.getCheckedAction();
		if (action==null) return false;
		oTabPane.addTab(oAction.getIdAction(), this);
		return true;
	}

	// -------------------------------------------------------------------------//
	// De la base al control
	// -------------------------------------------------------------------------//
	@Override
	public void BaseToControl(String zModo, boolean userRequest) throws Exception {
//		if (!this.addPanelToTab()) return;
		if (!userRequest) return;
		this.restoreListAttributes();
	}



}
