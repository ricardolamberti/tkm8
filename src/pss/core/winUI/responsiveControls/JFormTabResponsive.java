package pss.core.winUI.responsiveControls;

import pss.core.win.JBaseWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.winUI.lists.JWinList;

public class JFormTabResponsive  extends JFormControlResponsive {

	// -------------------------------------------------------------------------//
	// Propiedades de la Clase
	// -------------------------------------------------------------------------//
	private JWinList oWinLista;
	private BizAction oAction;
	private JFormTabPanelResponsive oTabPane;
	private JWin oWin;
	private Boolean bFilterBar=null;
	private boolean bOnDemand=false;


	private String sToolBar=JBaseWin.TOOLBAR_TOP;

	private boolean bListChanged=false;
	private boolean readOnly=false;
  boolean needRefreshAllScreen=false;
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

//	public boolean isToolBarTop() {
//		return getToolbar().equals(JBaseWin.TOOLBAR_TOP);
//	}

//	public boolean isToolBarRight() {
//		return getToolbar().equals(JBaseWin.TOOLBAR_RIGHT);
//	}
//	public boolean isToolBarBottom() {
//		return getToolbar().equals(JBaseWin.TOOLBAR_BOTTOM);
//	}
	public String getToolbar() {
		return sToolBar;
	}

	public void setToolBar(String value) {
		this.sToolBar = value;
	}
	public boolean isOnDemand() {
		return bOnDemand;
	}

	public void setOnDemand(boolean bOnDemand) {
		this.bOnDemand = bOnDemand;
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
		return bFilterBar;
	}

	public JFormTabResponsive setFilterBar(boolean bFilterBar) {
		this.bFilterBar = bFilterBar;
		return this;
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

//	public JPanel GetListaPanel() {
//		return oWinLista.getRootPanel();
//	}
//
//	

	public BizAction getAction() {
		return this.oAction;
	}

	// -------------------------------------------------------------------------//
	// Constructor
	// -------------------------------------------------------------------------//
	public JFormTabResponsive() {
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

		if (zModo==MODO_ALTA) {
			// oWinLista.setEnabled(false);
			if (!oTabPane.hasTabs()) return;
			if (!this.addPanelToTab()) return;
			this.changeListAttributes();
//			oWinLista.RefreshLista();
//			oWinLista.crearRootPanel();
		} else {
			oWinLista.setEnabled(true);
		}
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

	public boolean hasAction() throws Exception {
		return oAction!=null;
	}
	public BizAction getCheckedAction() throws Exception {
		return oWin.findActionByUniqueId(oAction.getIdAction());
	}

	private boolean addPanelToTab() throws Exception {
		BizAction action=this.getCheckedAction();
		if (action==null) return false;

		JFormTabResponsive tab = new JFormTabResponsive();
		tab.SetWinLista(oWinLista);
		tab.setAction(action);
		String id = oTabPane.getForm().buildTabName(action);
		tab.setIdControl(id);
		oTabPane.addTab(id,tab);
		return true;
	}

	// -------------------------------------------------------------------------//
	// De la base al control
	// -------------------------------------------------------------------------//
	@Override
	public void BaseToControl(String zModo, boolean userRequest) throws Exception {
		if (!this.addPanelToTab()) return;
		if (!userRequest) return;
		// this.joinListFiltersWithControls();
		this.restoreListAttributes();
//		oWinLista.RefreshLista(); innecesaria para web, hace que se lea dos veces cuendo esta embebida
	}



}
