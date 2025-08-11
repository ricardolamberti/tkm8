package pss.core.winUI.lists;
import pss.common.regions.multilanguage.JLanguage;
import pss.common.security.BizUsuario;
import pss.core.graph.Graph;
import pss.core.services.fields.JHtml;
import pss.core.services.fields.JObject;
import pss.core.services.records.JProperty;
import pss.core.tools.PssLogger;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JList;
import pss.core.tools.collections.JMap;
import pss.core.win.IControl;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.totalizer.JTotalizer;
import pss.core.win.totalizer.JTotalizer.Properties;
import pss.core.winUI.forms.JBaseForm;
//import pss.core.winUI.menu.JWinMenuGenerator;

/**
 * Representa una lista de {@link JWin} con sus columnas y acciones de
 * presentación. Provee utilidades para manejar totales, filtros y la
 * configuración visual de las listas mostradas al usuario.
 */
public class JWinList {
	public static final String PAGETYPE_LANDSCAPE = "PAGETYPE_LANDSCAPE";
	public static final String PAGETYPE_NORMAL = "PAGETYPE_NORMAL";


	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// INSTANCE VARIABLES
	//
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//	JList<JAct> aSelectListeners;
//	JAct oCloseListener;
//	private String sSearchString="";
//	private JList<String> aFiltrosCampos=JCollectionFactory.createList(1);
//	private JList<String> aFiltrosValores=JCollectionFactory.createList(1);
//	private JPssEdit oSearchStringToolTip;
//	private int rowEdited=-1;
	// components
//	private JPssWinsTable oTable;
//	private JBotonBar oBotonWin;
//	private JFormFiltro filtros;
//	private WinBasedCellEditor oWinBasedCellEditor=null;
//	boolean pbOrdenar=false;
//	boolean pbFiltrar=false;
//	boolean pbCerrar=false;
//	boolean pbSeleccionar=false;
	protected JWins lastReadWins=null;
	protected JWin oWinRef=null;
	protected IControl oControlWins;
//	protected String sColumnaBusqueda=null;
	protected JBaseForm baseForm;
	// private JList rIniFilters;

	protected JList<JGrupoColumnaLista> aGrupoColumnasLista;
	protected JList<JColumnaLista> aColumnasLista;
	protected JMap<String, String> aFiltrosLista=JCollectionFactory.createMap();
	protected JList<Graph> aGraficosLista=null;
	
	protected Boolean bAllowExportToExcel = null;
	protected Boolean bAllowExportToCSV = null;
	protected Boolean bAllowExportToReport = null;

	public boolean isAllowExportToExcel() {
		return bAllowExportToExcel;
	}

	public JWinList setAllowExportToExcel(boolean bAllowExportToExcel) {
		this.bAllowExportToExcel = bAllowExportToExcel;
		return this;
	}

	public boolean hasAllowExportToCSV() {
		return bAllowExportToCSV!=null;
	}
	public boolean hasAllowExportToExcel() {
		return bAllowExportToExcel!=null;
	}
	public boolean hasAllowExportToReport() {
		return bAllowExportToReport!=null;
	}
	public boolean isAllowExportToCSV() {
		return bAllowExportToCSV;
	}

	public JWinList setAllowExportToCSV(boolean bAllowExportToCSV) {
		this.bAllowExportToCSV = bAllowExportToCSV;
		return this;

	}

	public boolean isAllowExportToReport() {
		return bAllowExportToReport;
	}

	public JWinList setAllowExportToReport(boolean bAllowExportToReport) {
		this.bAllowExportToReport = bAllowExportToReport;
		return this;
	}
	
	private boolean forExport;
	public void setForExport(boolean v) {
		this.forExport = v;
	}

	public boolean isForExport() {
		return this.forExport;
	}


	// Botones fijos de la lista
//	private JBotonBar oBotonesLista;
//	private BizAction oToolbarOrderAction;
//	private BizAction oToolbarSelectAction;
//	private BizAction oToolbarFilterAction;
//	private BizAction oToolbarRefreshAction;
//	private BizAction oToolbarSecurityAction;
//	private BizAction oToolbarCloseAction;
//	private BizAction oToolbarSaveAction;
	
	// configurable behaviour
	private boolean bFiltrosBar=true;


	private String sToolbar=null;//JBaseWin.TOOLBAR_LEFT;
	private boolean bMultipleSelection=true;

//	private boolean bKeyListenerAllowed=true;
//	private boolean bMouseListenerAllowed=true;
//	private boolean bPopupAllowed=true;
//	private boolean bFocusAllowed=true;
//	private boolean bDragAllowed=true;
	private boolean bEmbedded;
	private boolean bEnabled=true;
//	private boolean bColumnSearchLocked;
	private boolean bRefreshOnlyOnUserRequest;

	private String typePage = PAGETYPE_LANDSCAPE;
	private JTotalizer totalizer;
	private boolean bTotalizer;

		// flags
	private boolean listCreated;

	public boolean isCreated() {
		return listCreated;
	}

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// CONSTRUCTORS
	//
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// public JWinList(JWin zActionOwner, int zAction) throws Exception {
	// this.oActionOwner = zActionOwner;
	// this.action = zAction;
	// }
	
	public JWinList(JWins zWins) throws Exception {
		this(zWins, zWins==null?null:zWins.createControlWin());
	}	

	public JWinList(JWins wins, IControl zControlWins) throws Exception {
		this.lastReadWins=wins;
		this.oControlWins = zControlWins;
	}
	public boolean canMapear() throws Exception{
		return getWins().canMapear();
	}
	
	public boolean hasGraficos() throws Exception {
		return getWins().hasGrafico(this);

	}
	
	public void setWins(JWins wins) throws Exception {
		this.lastReadWins=wins;
		
	}
	public JList<Graph> getGraficosLista() throws Exception {
		if (aGraficosLista==null) {
			aGraficosLista=JCollectionFactory.createList();
			try {
				getWins().ConfigurarGraficos(this);
			} catch (Exception ignore) {}
		}
		return aGraficosLista;
	}
	
	public Graph addGrafico(Graph g) throws Exception {
		getGraficosLista().addElement(g);
		return g;
	}
	public Graph getGrafico(int pos) throws Exception {
		JList<Graph> lista = getGraficosLista();
		if (lista.size()<=pos-1) return null;
	  return lista.getElementAt(pos-1);
	}
	
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// METHODS
	//
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// getters and setters
	//
	public void setRefreshOnlyOnUserRequest(boolean bOnlyOnUserRequest) {
		this.bRefreshOnlyOnUserRequest=bOnlyOnUserRequest;
	}

	public boolean isRefreshOnlyOnUserRequest() {
		return this.bRefreshOnlyOnUserRequest;
	}

	public void setToolbar(String zValue) {
		this.sToolbar=zValue;
	}

	public void setMultipleSelection(boolean isMultipleSelection) {
		this.bMultipleSelection=isMultipleSelection;
	}

	public boolean isMultipleSelection() throws Exception {
		return this.bMultipleSelection;// || this.lastReadWins().isMultiSelection();
	}

	public void SetFiltrosBar(boolean zValue) {
		this.bFiltrosBar=zValue;
	}

	public boolean isFilterBar() {
		return this.bFiltrosBar;
	}

	public void setEnabled(boolean zValue) throws Exception {
		if (zValue==this.bEnabled) {
			return;
		}

		this.bEnabled=zValue;
//		this.doEnable();
//		if (this.listCreated) {
//			this.refreshNow(false, false, false);
//		}
	}

	public boolean isEnabled() {
		return this.bEnabled;
	}

	public JMap<String, String> GetFiltrosLista() {
		return this.aFiltrosLista;
	}

	public void setEmbedded(boolean zEmbedded) {
		this.bEmbedded=zEmbedded;
	}

	public JBaseForm getBaseForm() {
		return baseForm;
	}

	public void setBaseForm(JBaseForm baseForm) {
		this.baseForm = baseForm;
	}

	public boolean isEmbedded() {
		return this.bEmbedded;
	}

//	public void SetCloseListener(JAct zValue) {
//		this.oCloseListener=zValue;
//	}

	/**
	 * Sets the substring seach value
	 */
//	public void setSubstringSearch(String zValue) {
//		this.sSearchString=zValue;
//	}

//	public void AddSelectListener(JAct zValue) {
//		if (this.aSelectListeners==null) {
//			this.aSelectListeners=JCollectionFactory.createList();
//		}
//		this.aSelectListeners.addElement(zValue);
//	}
//
//	private boolean hasSelectionListener() {
//		return this.aSelectListeners!=null;
//	}
//
//	/**
//	 * Removes all the selection listeners
//	 */
//	public void removeAllSelectionListeners() {
//		this.aSelectListeners.removeAllElements();
//	}

//	public boolean isKeyListenerAllowed() {
//		return this.bKeyListenerAllowed;
//	}
//
//	public boolean isMouseListenerAllowed() {
//		return this.bMouseListenerAllowed;
//	}
//
//	public boolean isPopupAllowed() {
//		return this.bPopupAllowed;
//	}
//
//	public boolean isFocusAllowed() {
//		return this.bFocusAllowed;
//	}
//
//	public boolean isDragAllowed() {
//		return this.bDragAllowed;
//	}

//	public boolean isToolbar() throws Exception {
//		return !this.sToolbar.equals(JBaseWin.TOOLBAR_NONE);
//	}

	public String getToolbar() throws Exception {
		return this.sToolbar;
	}
//
//	public void setKeyListenerAllowed(boolean zValue) {
//		this.bKeyListenerAllowed=zValue;
//	}
//
//	public void setMouseListenerAllowed(boolean zValue) {
//		this.bMouseListenerAllowed=zValue;
//	}
//
//	public void setPopupAllowed(boolean zValue) {
//		this.bPopupAllowed=zValue;
//	}
//
//	public void setFocusAllowed(boolean zValue) {
//		this.bFocusAllowed=zValue;
//	}
//
//	public void setDragAllowed(boolean zValue) {
//		this.bDragAllowed=zValue;
//	}

	//
	// initialization
	//

	public JWins lastReadWins() throws Exception {
		return lastReadWins;
	}

	/*
	 * public RStructure getInitStructure() { return this.rBaseInit; }
	 */
	// public void SetObjWins(JWins zWins) throws Exception {
	// ObjWins = zWins;
	// this.rBaseInit = null;
	// this.rBaseInit = ObjWins.getRecords().cloneStructure();
	// }
	// }
	public JWin getWinRef() throws Exception {
		if (oWinRef==null) oWinRef=getWins().getWinRef();
		return oWinRef;
	}

//	public void SetColumnaBusqueda(String zCampo) throws Exception {
//		sColumnaBusqueda=zCampo;
//	}
//
//	public String getColumnaBusqueda() throws Exception {
//		return sColumnaBusqueda;
//	}
//
//	public boolean isSetColumnaBusqueda() throws Exception {
//		return sColumnaBusqueda!=null;
//	}

	// --------------------------------------------------------------------------
	// //
	// Manejo de la columna lista
	// --------------------------------------------------------------------------
	// //
	public void RemoveAllColumnaLista(JColumnaLista zCol) {
		aColumnasLista=JCollectionFactory.createList();
	}

	public JColumnaLista AddColumnaLista(JColumnaLista zCol) {
		if (aColumnasLista==null) aColumnasLista=JCollectionFactory.createList();
		aColumnasLista.addElement(zCol);
		return zCol;
	}
	public JGrupoColumnaLista AddGrupoColumnaLista(JGrupoColumnaLista zCol) {
		if (aGrupoColumnasLista==null) aGrupoColumnasLista=JCollectionFactory.createList();
		aGrupoColumnasLista.addElement(zCol);
		return zCol;
	}
	public JGrupoColumnaLista AddGrupoColumnaLista(String zGrupo) {
		if (aGrupoColumnasLista==null) aGrupoColumnasLista=JCollectionFactory.createList();
		JGrupoColumnaLista grupo=new JGrupoColumnaLista(zGrupo);
		aGrupoColumnasLista.addElement(grupo);
		return grupo;
	}

	
	private boolean isFieldExcludedForCompany(String field)  {
		try {
			String company   = BizUsuario.getUsr().getCompany();
			String classname = this.getWins().getClass().getName();

			if ( BizListExcludeCol.findCol(company, classname,  this.getWins().GetVision(), field) )
				return true;
			
		} catch (Exception ee) {
		}
		return false;
	}

	public void removeGroupColumn(String zTitulo) throws Exception {

		
		JColumnaLista oColumnToRemove=null;
		do  {
			JIterator<JColumnaLista>oColsIt=this.aColumnasLista.getIterator();
			while (oColsIt.hasMoreElements()&&oColumnToRemove==null) {
				JColumnaLista oColumna= oColsIt.nextElement();
				if (oColumna.getGrupo()!=null&&oColumna.getGrupo().getTitulo().equalsIgnoreCase(zTitulo)) {
					oColumnToRemove=oColumna;
				}
			}
			if (oColumnToRemove!=null) {
				this.aColumnasLista.removeElement(oColumnToRemove);
			}
		} while (oColumnToRemove!=null);
		
		JGrupoColumnaLista oGColumnToRemove=null;
		JIterator<JGrupoColumnaLista>oColsIt=this.aGrupoColumnasLista.getIterator();
		while (oColsIt.hasMoreElements()&&oGColumnToRemove==null) {
			JGrupoColumnaLista oColumna=oColsIt.nextElement();
			if (oColumna.getTitulo().equalsIgnoreCase(zTitulo)) {
				oGColumnToRemove=oColumna;
			}
		}
		if (oGColumnToRemove!=null) {
			this.aGrupoColumnasLista.removeElement(oGColumnToRemove);
		}
	}

	
	public void removeColumn(String zFieldName) throws Exception {
		JColumnaLista oColumnToRemove=null;
		JIterator<JColumnaLista>oColsIt=this.aColumnasLista.getIterator();
		while (oColsIt.hasMoreElements()&&oColumnToRemove==null) {
			JColumnaLista oColumna= oColsIt.nextElement();
			if (oColumna.GetCampo()!=null&&oColumna.GetCampo().equalsIgnoreCase(zFieldName)) {
				oColumnToRemove=oColumna;
			}
		}
		if (oColumnToRemove!=null) {
			this.aColumnasLista.removeElement(oColumnToRemove);
		}
	}

	public JColumnaLista getColumn(String zFieldName) {
		JIterator<JColumnaLista> oColsIt=this.aColumnasLista.getIterator();
		for(int i=0; oColsIt.hasMoreElements(); i++) {
			JColumnaLista oColumna=oColsIt.nextElement();
			if (oColumna.GetCampo()!=null&&oColumna.GetCampo().equalsIgnoreCase(zFieldName)) {
				return oColumna;
			}
		}
		return null;
	}
	public boolean hasColumn(String name) {
		return this.getColumnIndex(name)!=-1;
	}
		
	public int getColumnIndex(String zFieldName) {
		JIterator<JColumnaLista> oColsIt=this.aColumnasLista.getIterator();
		for(int i=0; oColsIt.hasMoreElements(); i++) {
			JColumnaLista oColumna=oColsIt.nextElement();
			if (oColumna.GetCampo()!=null&&oColumna.GetCampo().equalsIgnoreCase(zFieldName)) {
				return i;
			}
		}
		return -1;
	}
	public String getColumnPos(long pos) {
		JIterator<JColumnaLista> oColsIt=this.aColumnasLista.getIterator();
		for(int i=0; oColsIt.hasMoreElements(); i++) {
			JColumnaLista oColumna=oColsIt.nextElement();
			if (i==pos) {
				return oColumna.GetCampo();
			}
		}
		return "";
	}
	/*
	 * public JColumnaLista AddColumnaLista( JPropiedad zProp ) { return AddColumnaLista( zProp.GetDato().GetObjectClass(), null, zProp.GetCampo() ); }
	 */
	
	public JColumnaLista AddColumnaLista(String zCampo) throws Exception {
		return this.AddColumnaLista(null, zCampo);
	}


	public JColumnaLista AddColumnaLista(Class zClase, JProperty zFixedProp) throws Exception {
		return AddColumnaLista(zClase, "", zFixedProp);
	}

	public JColumnaLista AddColumnaLista(String zTitulo, String zCampo) throws Exception {
		if (isFieldExcludedForCompany(zCampo))
			return null;
		if (isFieldExcludedForThisUser(zCampo))
			return null;
		Class<?> clase=getWinRef().getRecord().getPropType(zCampo);
		JProperty prop=getWinRef().getRecord().getFixedPropDeep(zCampo);
		return this.AddColumnaLista(clase, zTitulo, prop);
	}
	private boolean isFieldExcludedForThisUser(String zCampo) {
		try {
			if (BizListExcludeCol.hasToShowAdminColumn(BizUsuario.getUsr().getCompany(), this.getWins().getClass().getName(), zCampo))
				return true;
		} catch (Exception ee) {
		}
		return false;
	}

	public boolean isColumnAction(int pos) {
		JColumnaLista l = this.aColumnasLista.getElementAt(pos);
		if(l==null) return false;
		return l.getAction()!=null;
	}
	public boolean isEmpty() {
		return aColumnasLista==null || aColumnasLista.isEmpty();
	}
	
	public JColumnaLista AddColumnaAction(int icono, String zTitulo, String actions, boolean agrupado) throws Exception {
		JColumnaLista oColumna=new JColumnaLista(BizAction.class);
		oColumna.setAction(actions);
		oColumna.setNroIcono(icono);
		oColumna.setAgrupado(agrupado);
//		if (zTitulo==null) zTitulo="Acc.";
		oColumna.SetTitulo(JLanguage.translate(zTitulo));
		return this.AddColumnaLista(oColumna);
	}
	
	public JColumnaLista AddColumnaLista(Class zClase, String zTitulo, JProperty zFixedProp) throws Exception {
		JColumnaLista oColumna=new JColumnaLista(zClase);
		oColumna.setFixedProp(zFixedProp);
		if (zTitulo==null) zTitulo=zFixedProp.GetDescripcion();
		oColumna.SetTitulo(JLanguage.translate(zTitulo));
		if (zFixedProp!=null)
			oColumna.setSortable(!zFixedProp.isVirtual());
		return this.AddColumnaLista(oColumna);
	}
	public JColumnaLista AddColumnaAutocomplete() throws Exception {
		JColumnaLista oColumna=new JColumnaLista(Void.class);
		return this.AddColumnaLista(oColumna);
	}


	public void setColumnTranslated(String zCampo, boolean translated) {
		int i=this.getColumnIndex(zCampo);
		if (i==-1) return;
		(this.aColumnasLista.getElementAt(i)).setTranslated(translated);
	}

	public void setAllColumnsTranslated(boolean translated) {
		JIterator<JColumnaLista> oColsIt=this.aColumnasLista.getIterator();
		while (oColsIt.hasMoreElements()) {
			JColumnaLista oColumna=oColsIt.nextElement();
			oColumna.setTranslated(translated);
		}
	}

	public JColumnaLista AddIcono(String zIcono) throws Exception {
		// JColumnaLista oColumna = new JColumnaLista(ImageIcon.class);
//		return AddColumnaAction(null, "all", true);
		return AddColumnaLista(null,"", null);
	}
	public JColumnaLista AddIcono(String zIcono, String zTitulo) throws Exception {
		return AddColumnaLista(null, zTitulo, null);
	}
	

	public void ClearColumnas() {
		aColumnasLista.removeAllElements();
	}

	// --------------------------------------------------------------------------
	// //
	// Manejo de los filtros lista
	// --------------------------------------------------------------------------
	// //
	public void AddFiltrosLista(String zCampo) {
		aFiltrosLista.addElement(zCampo, zCampo);
	}

	public void ClearFiltrosLista() {
		aFiltrosLista.removeAllElements();
	}

	/*
	 * public JColumnaLista CrearColumna(JPropertyDescription zProp) throws Exception { return this.createColum(zProp); }
	 * 
	 * private JColumnaLista createColum(JPropertyDescription zProp) throws Exception { JBD oBD = oWinRef.GetDato(); JColumnaLista oCol = new JColumnaLista(oBD.getProp(zProp.GetCampo()).GetObjectClass()); oCol.SetCampo(zProp.GetCampo()); oCol.SetTitulo(zProp.GetDescripcion()); return oCol; }
	 */
//	public void setColumnAlignment(String zFieldName, int zAlignment) {
//		JIterator<JColumnaLista> oColsIt=this.aColumnasLista.getIterator();
//		while (oColsIt.hasMoreElements()) {
//			JColumnaLista oColumna=oColsIt.nextElement();
//			if (oColumna.GetCampo()!=null&&oColumna.GetCampo().equalsIgnoreCase(zFieldName)) {
//				oColumna.setAlignment(zAlignment);
//				return;
//			}
//		}
//	}

//	public void SetSortAction(boolean zSort) {
//		pbOrdenar=zSort;
//	}
//
//	public void SetFilterAction(boolean zFilter) {
//		pbFiltrar=zFilter;
//	}
//
//	public void SetSelectAction(boolean zValue) {
//		pbSeleccionar=zValue;
//	}
//
//	public boolean hasSelectAction() {
//		return pbSeleccionar;
//	}
//
//	public void SetCerrarAction(boolean zValue) {
//		pbCerrar=zValue;
//	}
	
	public JList<JColumnaLista> GetColumnasLista() throws Exception  {
		return aColumnasLista;
	}
	public JList<JGrupoColumnaLista> GetGrupoColumnasLista(){

		return aGrupoColumnasLista;
	}
//	public void SetControlWins(JControlWin zControlWins) {
//		this.oControlWins=zControlWins;
//	}

	public void clearLastWins() throws Exception {
		this.lastReadWins=null;
	}

	public void clearWins() throws Exception {
		this.lastReadWins=null;
	}

	public JWins getWins() throws Exception {
		if (this.lastReadWins!=null) return this.lastReadWins;
		this.lastReadWins=this.oControlWins.getRecords(false);
		if (this.lastReadWins==null) return null;
		return this.lastReadWins;
	}
	
//	private void reRead() throws Exception {
//		JWins wins = this.getWins();
//		if (wins==null) return;
//	
//		wins.getRecords().clearDynamicFilters();
//		
//		// Filtros de la Toolbar
//		if (bFiltrosBar) wins.asignFiltersFromFilterBar(oBarFiltros);
//
//		// Filtros Interactivos
//		this.SetearFiltrosQuery();
//		// order by
////		this.applyOrderByColumns(wins);
//
//		wins.getRecords().SetIgnoreLocks(true);
//		wins.ReRead();
//		wins.firstRecord();
//	}

	// private JWins generateNewWins() throws Exception {
	// if (this.oControlWins!=null)
	// return oControlWins.GetWins(this);
	// else
	// return this.createWinsFromAction();
	// }

	// private JWins createWinsFromAction() throws Exception {
	// return oActionOwner.findAction(this.action).GetAction().getWinsResult();
	// }
	//  
	// private void preserveInitFilters() throws Exception {
	// this.rIniFilters = JCollectionFactory.createList();
	// if (!this.getWins().getRecords().getStructure().hasFilters()) return;
	// JIterator iter = this.getWins().getRecords().getFilters().getIterator();
	// while (iter.hasMoreElements()) {
	// this.rIniFilters.addElement(iter.nextElement());
	// }
	// }

	/*
	 * public JList getIniFilters() { return this.rIniFilters; }
	 * 
	 * 
	 * public void asignInitFilters() throws Exception { this.getWins().getRecords().clearBasicFilters(); if (this.rIniFilters==null) return; JIterator iter = this.rIniFilters.getIterator(); while (iter.hasMoreElements()) { RFilter filter = (RFilter) iter.nextElement(); this.getWins().getRecords().setFilter(filter); } }
	 */

	private boolean hasToReRead(boolean userRefresh) throws Exception {
		if (userRefresh) return true;
		if (this.isRefreshOnlyOnUserRequest()) return false;
		if (this.lastReadWins().getRecords().isStatic()) return false;
		return true;
	}

	public void generate(JBaseForm form) throws Exception {
		baseForm=form;
		// create the root panel
//		this.oRootPanel=new JPssPanel();
		// refresh it when visible, if this WinList is embedded
	}
	
	public boolean hasControlWins() throws Exception {
		return this.oControlWins!=null;
	}
	public void ConfigurarFiltrosLista() throws Exception {
		this.getWins().ConfigurarFiltrosLista(this);
	}
	public void ConfigurarColumnasLista() throws Exception {
		this.getWins().ConfigurarColumnasListaInternal(this);
	}
	public boolean createListIfNotCreatedNoForm() throws Exception {
		if (this.listCreated) return false;
		try {
			this.aColumnasLista=null;
//			JRefresh.addListener(this);
			ConfigurarFiltrosLista();
			ConfigurarColumnasLista();
			this.listCreated=true;
		} catch (Throwable ex) {
			PssLogger.logError("No se pudo crear la lista");
			PssLogger.logDebug(ex);
			if (ex instanceof Exception) {
				throw (Exception) ex;
			}
		}
		return true;
	}	

	public boolean isGenericEditable() {
		try {
			return this.getWins().isEditable();
		} catch (Exception e) {
			return false;
		}
	}
//
//	public boolean isWinModifEditable() {
//		try {
//			return this.isGenericEditable()&&this.getSelectedWin().ifHabilitado(2); // accion modificar... medio hardcoded
//		} catch (Exception e) {
//			return false;
//		}
//	}

	public boolean isWinAltaEditable() {
		try {
			return this.isGenericEditable()&&this.getWins().isEditableAlta()&&this.getWins().ifHabilitado(1); // accion modificar... medio hardcoded
		} catch (Exception e) {
			return false;
		}
	}

	//
	// user interaction handling
	//

	//
	// sort behavior
	//
//	public void OrdenarLista() throws Exception {
//		getWins().getRecords().clearOrderBy();
//
//		JWin oOrd=new JWin();
//		FormOrdenLista oOrden=(FormOrdenLista) oOrd.createForm(FormOrdenLista.class, JFormControl.MODO_ALTA, null, oOrd.getActionSubmitNew(),false);
//
//		oOrden.InitOrdenLista(this, getWins());
//		oOrden.SetExitOnOk(true);
//		oOrden.SetConfirmarAplicar(false);
//		oOrden.setModal(true);
//		oOrden.Show();
//
//	}

	/**
	 * Unlocks the search column to default
	 */
//	public void unlockColumnSearch() throws Exception {
//		this.bColumnSearchLocked=false;
//	}
//
//	/**
//	 * Locks the search column to default
//	 */
//	public void lockColumnSearch() throws Exception {
//		this.bColumnSearchLocked=true;
//	}
//
//	/**
//	 * Asks if the column search is locked
//	 */
//	private boolean isColumnSearchLocked() {
//		return this.bColumnSearchLocked;
//	}

	//
	// filtering behavior
	//
//	public void FiltrarLista() throws Exception {
//		JWin oFil=new JWin();
//		FormFiltrosLista oFiltros=(FormFiltrosLista) oFil.createForm(FormFiltrosLista.class, JFormControl.MODO_ALTA, null, oFil.getActionSubmitNew(), false);
//
//		aFiltrosCampos.removeAllElements();
//		aFiltrosValores.removeAllElements();
//		oFiltros.InitFiltrosLista(this, getWins(), aFiltrosCampos, aFiltrosValores);
//
//		oFiltros.SetExitOnOk(true);
//		oFiltros.SetConfirmarAplicar(false);
//		oFiltros.setModal(true);
//		oFiltros.Show();
//	}

//	private void SetearFiltrosQuery() throws Exception {
//		Iterator<String> camposIt=aFiltrosCampos.iterator();
//		Iterator<String> valoresIt=aFiltrosValores.iterator();
//		while (camposIt.hasNext()) {
//			String sCampo=camposIt.next();
//			String sVal=valoresIt.next();
//			RFilter filter=getWins().getRecords().addFilter(sCampo, sVal, "like");
//			filter.setDynamic(true);
//		}
//		aFiltrosCampos.removeAllElements();
//		aFiltrosValores.removeAllElements();
//		if (sColumnaBusqueda==null) return;
//		if (sSearchString.equals("")) return;
//		String sLowerInstruction=JBDatos.GetBases().getPrivateCurrentDatabase().getLowerCaseInstruction();
//		String sOperator=(this.getWins().getRecords().getFixedFilters()==null||this.getWins().getRecords().getFixedFilters().isEmpty()) ? " where " : " and ";
//		RFixedFilter filter=this.getWins().getRecords().addFixedFilter(sOperator+" "+sLowerInstruction+"("+getColumnaTabla()+"."+sColumnaBusqueda+") like "+"'"+sSearchString.toLowerCase()+"%'");
//		filter.setDynamic(true);
//		// GetWins().GetDatos().SetFiltros(getColumnaTabla(), sColumnaBusqueda, " like ", sSearchString+"%", JObject.JSTRING, "AND", "" );
//	}

//	public String getColumnaTabla() throws Exception {
//		String sTabla=getWinRef().getRecord().getFixedProp(sColumnaBusqueda).GetTabla();
//		if (sTabla.equals("")) sTabla=getWinRef().getRecord().GetTable();
//		return sTabla;
//	}

//	public JFormFiltro GetFormFiltro() throws Exception {
//		return this.oBarFiltros;
//	}


	/* por ahora solo tienen utilidad en la salida a pdf de la web */
	public void setPageType(String type) {
		typePage = type;
	}
	
	public String getPageType() {
		return typePage;
	}
	

	public boolean isTotalizer() {
		return bTotalizer;
	}

	public boolean isTotalizable() {
		try {
			return getTotalizer()!=null;
		} catch (Exception e) {
			return false;
		}
	}


	public JTotalizer getTotalizer() throws Exception {
		if (totalizer==null) getWins().createTotalizer(this);
		return totalizer;
	}

	public Properties addTotalizer(String column, String operacion, int align) throws Exception {
		return addTotalizer(column, 2, operacion, align);
	}

	public Properties addTotalizer(String column, int decimals, String operacion) throws Exception {
		return addTotalizer(column, decimals, operacion, -1);
	}

	public Properties addTotalizer(String column, int decimals, String operacion, int align) throws Exception {
		return createObjTotalizer().addTotalizer(column, operacion, align, decimals);
	}
	public Properties addTotalizer(String column, JObject v, int align) throws Exception {
		return createObjTotalizer().addTotalizer(column, v, align);
	}

	public Properties addTotalizer(String column, String operacion) throws Exception {
		return addTotalizer(column, 2, operacion, -1);
	}

	public Properties addTotalizerText(String column, String zText, int align) throws Exception {
		return createObjTotalizer().addTotalizerText(column, zText, align);
	}

	public void addTotalizerText(String column, String zText) throws Exception {
		zText = JLanguage.translate(zText);
		createObjTotalizer().addTotalizerText(column, zText, -1);
	}

	private JTotalizer createObjTotalizer() throws Exception {
		if (this.totalizer!=null) return this.totalizer;
		this.totalizer=new JTotalizer(this);
		return this.totalizer;
	}
	
	public boolean hasAnyTotalizer() throws Exception {
		if (totalizer==null) return false;
		return this.createObjTotalizer().hasAny();
	}

	public JColumnaLista addColumnWinAction(String title, int action) throws Exception {
		JColumnaLista col=new JColumnaLista(JHtml.class);
		col.setFixedProp(null);
		col.SetTitulo(title);
		col.setIdAction(action);
		this.AddColumnaLista(col);
		this.setListFlat(true);
		return col;
	}

	private boolean listFlat=false;
	public void setListFlat(boolean v) {
		this.listFlat=v;
	}
	public boolean isListFlat() {
		return this.listFlat;
	}

	private boolean hideActions=false;
	public void setHideActions(boolean v) {
		this.hideActions=v;
	}
	public boolean isHideActions() {
		return this.hideActions;
	}

	private boolean withOrderBy=true;
	public void setWithOrderBy(boolean v) {
		this.withOrderBy=v;
	}
	public boolean isWithOrderBy() {
		return this.withOrderBy;
	}

	private boolean withHeader=true;
	public void setWithHeader(boolean v) {
		this.withHeader=v;
	}
	public boolean isWithHeader() {
		return this.withHeader;
	}
	
	private boolean withFooter=false;
	public void setWithFooter(boolean v) {
		this.withFooter=v;
	}
	public boolean isWithFooter() {
		return this.withFooter;
	}
	
	private boolean needAutocomplete=true;
	public void setNeedAutocomplete(boolean v) {
		this.needAutocomplete=v;
	}
	public boolean needAutocomplete(JWins wins) throws Exception {
		if (!this.needAutocomplete) return false;
		if (!wins.needAutocomplete(this)) return false; //legacy, usar siempre el flag de JWinList en lugar de ssobreesscribir el wins HGK 
		return this.findAutocomplete();
	}
	
	private boolean findAutocomplete() throws Exception {
		long minCol = BizUsuario.getUsr()==null?-1:BizUsuario.getUsr().getObjBusiness().getCountColumnsForAddAutocomplete();
		if (minCol==-1) return false;
		if (this.GetColumnasLista()==null) return false;
		if (this.GetColumnasLista().size()==1) return false;
		return minCol>=this.GetColumnasLista().size();
	}

}