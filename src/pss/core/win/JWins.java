package pss.core.win;

import java.awt.event.KeyEvent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;

import pss.JPath;
import pss.common.security.BizUsuario;
import pss.core.data.BizPssConfig;
import pss.core.data.interfaces.connections.JBDatos;
import pss.core.data.interfaces.structure.RFilter;
import pss.core.reports.GuiReporteBase;
import pss.core.services.fields.JIntervalDate;
import pss.core.services.fields.JIntervalDateTime;
import pss.core.services.fields.JObject;
import pss.core.services.fields.JScript;
import pss.core.services.records.BizVirtual;
import pss.core.services.records.JBaseRecord;
import pss.core.services.records.JFilterMap;
import pss.core.services.records.JProperty;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.JDateTools;
import pss.core.tools.JExcepcion;
import pss.core.tools.JTools;
import pss.core.tools.PssLogger;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JList;
import pss.core.tools.collections.JMap;
import pss.core.tools.collections.JOrderedMap;
import pss.core.win.actions.BizAction;
import pss.core.win.actions.BizActions;
import pss.core.win.security.BizWinPropiedad;
import pss.core.win.security.GuiWinPropiedad;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActFieldSwapWins;
import pss.core.win.submits.JActFileGenerate;
import pss.core.win.submits.JActNew;
import pss.core.win.submits.JActQuery;
import pss.core.win.submits.JActReport;
import pss.core.win.tools.orders.GuiWinsColumns;

import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.icons.GuiIcon;
import pss.core.winUI.lists.BizListExcludeCol;
import pss.core.winUI.lists.JFormFiltro;
import pss.core.winUI.lists.JWinList;
import pss.core.winUI.lists.JWinMatrix;
import pss.core.winUI.responsiveControls.JFormRadioResponsive;
import pss.core.winUI.responsiveControls.JFormTwoPropertiesResponsive;
import pss.www.platform.actions.requestBundle.JWebActionData;
import pss.www.platform.actions.requestBundle.JWebActionDataField;
import pss.www.platform.applications.JHistory;
import pss.www.platform.applications.JHistoryProvider;
import pss.www.ui.skins.JWebSkin;

/**
 * Colección de ventanas {@link JWin}. Gestiona listas de ventanas, filtros y
 * operaciones comunes sobre conjuntos de registros asociados. Las clases que
 * representan listados o grillas deben extender esta clase.
 *
 * @param <TWin>
 *            tipo concreto de ventana gestionada.
 */
public abstract class JWins<TWin extends JWin> extends JBaseWin {

	protected Class<? extends JWin> oWClass;
	protected JList<JWin> aWins;
	protected transient JWin winRef;

	protected boolean bEstatico = false;
	protected boolean bForceRefreshUI = false;
	protected boolean bShowFilters = true;
	protected boolean bPaginable = true;
	private Boolean bUseSimpleOrden = null;
	protected transient JOrderedMap<String, ArrayList<Object>> oFiltrosKeyDesc = null;

	protected int pagesize = -1;
	protected int offset = -1;

	protected boolean dontUseFilterDefault = false;
	Serializable extraFilter;
	protected Boolean bAllowExportToExcel = null;
	protected Boolean bAllowExportToCSV = null;
	protected Boolean bAllowExportToReport = null;

	public Boolean isAllowExportToExcel() {
		return bAllowExportToExcel;
	}
	
	public Boolean useSimpleOrden() {
		return bUseSimpleOrden;
	}
	public void setSimpleOrden(Boolean zValue) {
		bUseSimpleOrden =zValue;
	}

	
	

	public Boolean isAllowExportToCSV() {
		return bAllowExportToCSV;
	}
	public Boolean isAllowExportToReport() {
		return bAllowExportToReport;
	}

	public boolean hasAllowExportToCSV() {
		return isAllowExportToCSV()!=null;
	}
	public boolean hasAllowExportToExcel() {
		return isAllowExportToExcel()!=null;
	}
	public boolean hasAllowExportToReport() {
		return isAllowExportToReport()!=null;
	}
	public JWins setAllowExportToExcel(boolean bAllowExportToExcel) {
		this.bAllowExportToExcel = bAllowExportToExcel;
		return this;
	}

	public JWins setAllowExportToCSV(boolean bAllowExportToCSV) {
		this.bAllowExportToCSV = bAllowExportToCSV;
		return this;

	}
	public JWins setAllowExportToReport(boolean bAllowExportToReport) {
		this.bAllowExportToReport = bAllowExportToReport;
		return this;
	}

	public Serializable getExtraFilter() {
		return extraFilter;
	}
	public boolean hasExtraFilter() {
		return extraFilter!=null;
	}

	public void setExtraFilter(Serializable extraFilterVO) {
		this.extraFilter = extraFilterVO;
	}

	public String getModeView() throws Exception {
		if (modeView != null)
			return modeView;
		return JBaseWin.MODE_LIST;
	}

	public void setDontUseFilterDefault(boolean dontUseFilterDefault) {
		this.dontUseFilterDefault = dontUseFilterDefault;
	}

	public boolean dontUseFilterDefault() throws Exception {
		return dontUseFilterDefault;
	}

	private transient JWinList collist = null;

	public JWinList getColWinList() {
		return collist;
	}

	public boolean acceptPreferences() throws Exception {
		return true;
	}

	public JWins buildDefault() throws Exception {
		return this;
	}

	public boolean matrixDisplayTotales() throws Exception {
		return true;
	}

	public long getMaxToOthers() throws Exception {
		return 0;
	}

	public boolean matrixDisplaySubTotales() throws Exception {
		return false;
	}

	public static final String VISION_SIN_VALORES = "SinValor";

	public String getSelectedCell() throws Exception {
		int l = Integer.parseInt(selectedCell);
		collist = new JWinList(this); // WinList fantasma para poder obtener las
										// columnas de details
		this.ConfigurarColumnasListaInternal(collist);

		return collist.getColumnPos(l);
	}

	public JRecords getRecords() throws Exception {
		return (JRecords) this.GetBaseDato();
	}

	public void setRecords(JRecords zDatos) throws Exception {
		this.SetBaseDato(zDatos);
	}

	public Class<? extends JWin> GetClassWin() throws Exception {
		return oWClass;
	}

	public boolean canMapear() throws Exception {
		return false;
	}
	public void cleanWinRef() throws Exception {
		this.winRef = null;
	}
	public JWin getWinRef() throws Exception {
		if (this.winRef != null)
			return this.winRef;
		this.winRef = this.createJoinWin();
//		this.winRef.SetVision(GetVision());
//		this.winRef.setModeWinLov(this.isModeWinLov());
//		this.winRef.setDropListener(this.getDropListener());
//		this.winRef.setParent(this);
		this.winRef.setVerifyActions(false);
		return this.winRef;
	}

	public JWins GetThis() { 
		return this;
	}

	public void SetClassWin(Class<? extends JWin> zClass) {
		oWClass = zClass;
	}

	@Override
	public JBaseWin SetVision(String zValue) throws Exception {
		super.SetVision(zValue);
		GetBaseDato().SetVision(zValue);
		return this;
	}

	private Object oObjetoAPropagar;

	private boolean bRefreshOnlyOnUserRequest;
	private boolean bRefreshOnlyOnUserRequestStrict;

	public Object getObjetoAPropagar() {
		return this.oObjetoAPropagar;
	}

	public void setObjetoAPropagar(Object zValue) {
		this.oObjetoAPropagar = zValue;
	}

	public void setRefreshOnlyOnUserRequest(boolean bOnlyOnUserRequest) {
		this.bRefreshOnlyOnUserRequest = bOnlyOnUserRequest;
	}

	public boolean isRefreshOnlyOnUserRequest() {
		return this.bRefreshOnlyOnUserRequest;
	}
	public void setRefreshOnlyOnUserRequestStrict(boolean bOnlyOnUserRequest) {
		this.bRefreshOnlyOnUserRequestStrict = bOnlyOnUserRequest;
	}

	public boolean isRefreshOnlyOnUserRequestStrict() {
		return this.bRefreshOnlyOnUserRequestStrict;
	}

	@Override
	public JBaseRecord ObtenerBaseDato() throws Exception {
		return ObtenerDatos();
	}

	public JRecords<? extends JRecord> ObtenerDatos() throws Exception {
		JWin win = this.GetClassWin().newInstance();
		JRecords<JRecord> oBDs = new JRecords<JRecord>(win.getRecord());
		// oBDs.SetTabla(oWin.getRecord().getStructure().getTable());
		// oBDs.setBasedClass(oWin.getRecord()os.getClass());
		return oBDs;
	}

	@Override
	public void createGenericActionMap() throws Exception {
		this.addActionOrder(99998, "Ordenar");
		super.createGenericActionMap();
	}
	
	public BizAction addActionOrder(int zId, String zDesc) throws Exception {
		BizAction action=this.addAction(zId, zDesc, KeyEvent.VK_ENTER, null,206, false, false);
		action.setHelp("Ordenar las columnas de "+GetTitle());
		action.setModal(true);
		action.setBackAction(true);
		return action;
	}


	public JWins addFilterAdHoc(String filtro, String valor, String operator) throws Exception {
		this.getRecords().addFilter(filtro, valor, operator);
		return this;
	}

	public JWins addFilterAdHoc(String filtro, String valor) throws Exception {
		this.getRecords().addFilter(filtro, valor);
		return this;
	}

	public JWins addOrderAdHoc(String campo, String orden) throws Exception {
		this.getRecords().addOrderBy(campo, orden);
		return this;
	}

	public JWins addVisionAdHoc(String vision) throws Exception {
		this.SetVision(vision);
		this.getRecords().SetVision(vision);
		return this;
	}

	public JAct DropControl(JAct zAct) throws Exception {
		setDropControl(zAct);
		return super.DropControl(zAct);
	}

	public void setDropControl(JAct zAct) throws Exception {
		JBaseWin zBaseWin = zAct.getResult();
		if (zBaseWin.isWin())
			this.getDropControlIdListener().getActionSource().addFilterMap(getDropControlIdListener().getFieldChanged(), ((JWin) zBaseWin));
		else {
			this.getDropControlIdListener().getActionSource().addFilterMap(getDropControlIdListener().getFieldChanged(), ((JWins) zBaseWin) );
		}
	}

	// --------------------------------------------------------------------------
	// //
	// Constructor
	// --------------------------------------------------------------------------
	// //
	public JWins() throws Exception {
		super();
		aWins = JCollectionFactory.createList();
	}

	// public JBaseForm FormEnterQuery() throws Exception {
	// JWin oWin;
	// oWin=this.createJoinWin(null);
	// return oWin.FormEnterQuery(true);
	// }
	public BizAction addActionNew(int zId, String zDesc, int key) throws Exception {
		return this.addAction(zId, zDesc, key, null, GuiIcon.MAS_ICON, true, true);
	}

	public BizAction addActionNew(int zId, String zDesc) throws Exception {
		return this.addAction(zId, zDesc, KeyEvent.VK_INSERT, null, GuiIcon.MAS_ICON, true, true).setHelp("Permite abrir el formulario de creación de "+GetTitle());
	}
	public BizAction addActionNew(String zId, String zDesc) throws Exception {
		return this.addAction(zId, zDesc, KeyEvent.VK_INSERT, null, GuiIcon.MAS_ICON, true, true).setHelp("Permite abrir el formulario de creación de "+GetTitle());
	}

	public BizAction addActionReport(int zId, String zDesc) throws Exception {
		BizAction a = this.addAction(zId, zDesc, KeyEvent.VK_F12, null, 5, true, true);
		a.setNuevaVentana(true);
		return a;
	}

	public BizAction addActionExcel(int zId, String zDesc) throws Exception {
		BizAction a = this.addAction(zId, zDesc, KeyEvent.VK_9, null, 5, true, true);
		a.setNuevaVentana(true);
		return a;
	}

	@Override
	public JAct getSubmit(BizAction a) throws Exception {
		if (a.getId() == 1)
			return new JActNew(this.createJoinWin(), 0);
		if (a.getId() == 2)
			return new JActReport(this.getReport(), 5);
		if (a.getId() == 99998) {
			GuiWinsColumns orders = this.getOrders();
			return new JActFieldSwapWins( orders.getColumnsSelect(), orders.fillColumnsSource(a),  orders,"field","field");
		}
		if (a.getId() == 99999)
			return getExcel(a);
		return this.getSubmitFor(a);
	}

	private JAct getExcel(BizAction a) throws Exception {

		return new JActFileGenerate(this, a.getId()) {

			public String getPath() throws Exception {
				return JPath.PssPathTempFiles();
				// return fileName;
			}

			public String generate() throws Exception {
				return toExcel();
			}
		};

	}

	public GuiWinsColumns getOrders() throws Exception {
		 return GuiWinsColumns.getOrders(this);
	}
	public JAct getCoreSubmit(BizAction a) throws Exception {
		if (a.getId() == 99998) 
			return new JActFieldSwapWins( getOrders().getColumnsSelect(), getOrders().fillColumnsSource(a),  getOrders(),"field","field");
		return null;
	}
	

	// public void SetAccionEnterQuery(int zId, String zDesc) throws Exception {
	// addAction(zId, zDesc, KeyEvent.VK_F11, new JAct() {
	//
	// @Override
	// public void Do() throws Exception {
	// FormEnterQuery();
	// }
	// }, 158, true, true);
	// }

	/**
	 * Creates a form to show data THIS METHOD DOESNT SHOWS THE FORM
	 */
	// public JListForm CrearFormListaWithOutShow() throws Exception {
	// JListForm oForm = new JListForm(this);
	// //oForm.GetLista().SetFiltrosBar(bFilterBar);
	// oForm.GetLista().setRefreshOnlyOnUserRequest(this.isRefreshOnlyOnUserRequest
	// ());
	// return oForm;
	// }
	public void ReRead() throws Exception {
		ClearWinItems();
		readAll();
	}

	public boolean ifEstatico() {
		return bEstatico;
	}

	public boolean isDatabaseSupportsOffset() throws Exception {
		return this.getRecords().isDatabaseSupportsOffset();
	}

	public void readAll() throws Exception {
		getRecords().setPagesize(this.pagesize);
		getRecords().setOffset(this.offset);
		getRecords().readAll();
	}

	public JIterator<JWin> getStaticIterator() throws Exception {
		this.toStatic();
		return aWins.getIterator();
	}

	public void firstRecord() throws Exception {
		getRecords().firstRecord();
	}

	public boolean nextRecord() throws Exception {
		return getRecords().nextRecord();
	}

	public JWin getRecord() throws Exception {
		if (this.ifEstatico())
			return aWins.getElementAt(getRecords().GetCurrRow());
		return this.createJoinWin(getRecords().getRecord());
	}

	public JWins toStatic() throws Exception {
		if (this.ifEstatico())
			return this;
		getRecords().toStatic();
		this.firstRecord();
		while (this.nextRecord()) {

			this.aWins.addElement(this.getRecord());
		}
		bEstatico = true;
		return this;
	}

	public void endStatic() throws Exception {
		getRecords().endStatic();
		this.clearItems();
		bEstatico = false;
	}

	public void clearItems() throws Exception {
		if (ifEstatico()) {
			ClearStaticItems();
		} else {
			aWins.removeAllElements();
			bEstatico=false;
		}
		//	this.clearBaseDato(); // esto que hacer aca? rompe el combo del  formlov, 
		//tambien rompe el endSatic ya que se pieden todos los filtros HGK
	}

	/*
	 * public boolean ReadNextBlock(int zPageSize) throws Exception { return
	 * ReadAllDatos(true, zPageSize); }
	 */

	// --------------------------------------------------------------------------
	// //
	// Devuelvo la descripcion de un item para el arbol
	// --------------------------------------------------------------------------
	// //
	@Override
	public String getDescripField() {
		try {
			return GetTitle();
		} catch (Exception e) {
			return "ERROR";
		}
	}

	// --------------------------------------------------------------------------
	// //
	// Devuelvo el nombre de la pantalla
	// --------------------------------------------------------------------------
	// //
	@Override
	public String toString() {
		try {
			return this.getDescripFieldValue();
		} catch (Exception e) {
			return "Error";
		}
	}

	public void ConfigurarFiltros(JFormFiltro zFiltros) throws Exception {
	}

	public void ConfigurarGraficos(JWinList oWinlist) throws Exception {
	}

	public void createTotalizer(JWinList oWinlist) throws Exception {
	}

	public void preGraphicRead() throws Exception {
	}

	public boolean hasGrafico(JWinList oWinlist) throws Exception {
		if (oWinlist.getGraficosLista() == null)
			ConfigurarGraficos(oWinlist);
		if (oWinlist.getGraficosLista() == null)
			return false;
		return oWinlist.getGraficosLista().size() > 0;
	}

	// public void assignFiltersFromFilterList(JList<RFilter> list) throws
	// Exception {
	// JRecord rec = this.createWin().getRecord();
	// JIterator<RFilter> iter = list.getIterator();
	// while (iter.hasMoreElements()) {
	// RFilter filter = iter.nextElement();
	// JObject obj = rec.getProp(filter.getField());
	// if (obj==null) continue;
	// obj.setValueFormWeb(filter.getValue());
	// filter.setValue(obj.toString());
	// filter.setType(obj.getObjectType());
	// this.getRecords().getStructure().addFilter(filter);
	// }
	// }

	// public void assignFiltersFromFilterMap(JMap<String, String> map) throws
	// Exception {
	// JFormFiltro filterBar = new JFormFiltro(this);
	// filterBar.initialize();
	// JIterator<String> iter = map.getKeyIterator();
	// while (iter.hasMoreElements()) {
	// String key = iter.nextElement();
	// JFormControl c = filterBar.findControl(key);
	// if (c==null) continue;
	// c.setValue(map.getElement(key));
	// }
	// this.asignFiltersFromFilterBar(filterBar);
	// }
	public void asignFiltersFromFilterMap(JFilterMap filterMap) throws Exception {
		if (filterMap == null)
			return;
		JIterator<String> iter = filterMap.getMap().getKeyIterator();
		while (iter.hasMoreElements()) {
			String f = iter.nextElement();
			Serializable v = filterMap.getMap().getElement(f);
			if (v == null || (v instanceof String && ((String)v).isEmpty()))
				continue;
			boolean added = false;
			JObject obj = this.getWinRef().getRecord().getProp(f, false);
			if (obj!=null && (obj.isDate()||obj.isIntervalDate()||obj.isIntervalDateTime() ) &&  (v instanceof String && !((String)v).isEmpty())) {
				String val = (String)v;
				if (val.indexOf(" - ")!=-1) {
					JIntervalDate i = new JIntervalDate();
					i.setValueFormUI((String)v);
					this.getRecords().addFilter(f,i).setDynamic(true);
					added=true;
				}
			} 
			if (!added)
			 this.getRecords().addFilter(f, v).setDynamic(true);
		}
	}
	
	boolean useForReport = false;

	public boolean isUseForReport() {
		return useForReport;
	}



	public void setUseForReport(boolean useForReport) {
		this.useForReport = useForReport;
	}

	public void asignFiltersFromFilterBarSwap(JFormFiltro filterBar) throws Exception {
		if (filterBar == null)
			return;
		JIterator<JFormControl> iter = filterBar.GetControles().GetItems();
		while (iter.hasMoreElements()) {
			JFormControl control = iter.nextElement();
			if (!control.hasToRefreshForm()) continue;
			this.asignFilterByControl(control);
		}
	}

	public void asignFiltersFromFilterBar(JFormFiltro filterBar) throws Exception {
		if (filterBar == null)
			return;
		JIterator<JFormControl> iter = filterBar.GetControles().GetItems();
		while (iter.hasMoreElements()) {
			JFormControl control = iter.nextElement();
			this.asignFilterByControl(control);
			if (isUseForReport())
				this.AddValueToWinsMapFilter(control);// este es un metodo muy costoso para obtener las descripciones de los fitlros para reportes 
		}
	}
	public void checkControls(JFormFiltro form,String filterChange) throws Exception {
	}
	// return if loaded filters.
	public boolean assignFilters(String field, String value) throws Exception {
		return false;
	}
	protected void asignFilterByControlMultiField(JFormControl control) throws Exception {
		String value = control.getValue();
		if (value=="") return;
		// calcula ultimo campo
		StringTokenizer it = new StringTokenizer(control.getMultiField(),",;");
		String last=null;
		while (it.hasMoreTokens()) {
			String field =it.nextToken();
			JProperty prop = getWinRef().getRecord().getFixedProp(field);
			if (prop.isAutonumerico()) continue;
			if (prop.isVirtual()) continue;
			if (prop.isRecord()) continue;
			if (prop.isRecords()) continue;
			if (prop.isArray()) continue;
			if (!prop.isTableBased()) continue;
			JObject obj = getWinRef().getRecord().getProp(field);
			if (obj.isBoolean()) continue;
			if (obj.isInteger()||obj.isLong()) {
				if (!JTools.isIntegerNumber(value)) continue;
				last=field;
				continue;			
			}
			if (obj.isFloat()||obj.isCurrency()) {
				if (!JTools.isNumber(value, true)) continue;
				last=field;
				continue;			
			}
			if (obj.isDate()) {
				if (!JDateTools.CheckDatetime(value,false)) continue;
				last=field;
				continue;			
			}
			if (obj.isDateTime()) {
				if (!JDateTools.CheckDatetime(value,false)) continue;
				last=field;
				continue;			
			}
			if (obj.isString()) {
				last=field;
				continue;			
			}
		}

		it = new StringTokenizer(control.getMultiField(),",;");
		boolean first=true;
		while (it.hasMoreTokens()) {
			String field =it.nextToken();
			JProperty prop = getWinRef().getRecord().getFixedProp(field);
			if (prop.isAutonumerico()) continue;
			if (prop.isVirtual()) continue;
			if (prop.isRecord()) continue;
			if (prop.isRecords()) continue;
			if (prop.isArray()) continue;
			if (!prop.isTableBased()) continue;
			JObject obj = getWinRef().getRecord().getProp(field);
			if (obj.isBoolean()) continue;
			if (obj.isInteger()||obj.isLong()) {
				if (!JTools.isIntegerNumber(value)) continue;
				RFilter filter = this.getRecords().addFilter(field, JTools.getLongNumberEmbedded(value),JObject.JLONG, "=", first?"AND":"OR", first?"(":(last.equals(field)?")":""));
				first=false;
				continue;			
			}
			if (obj.isFloat()||obj.isCurrency()) {
				if (!JTools.isNumber(value, true)) continue;
				RFilter filter = this.getRecords().addFilter(field, JTools.getDoubleNumberEmbedded(value),JObject.JFLOAT, "=", first?"AND":"OR", first?"(":(last.equals(field)?")":""));
				first=false;
				continue;			
			}
			if (obj.isDate()) {
				if (!JDateTools.CheckDatetime(value,false)) continue;
				RFilter filter = this.getRecords().addFilter(field, value, JObject.JDATE, "=", first?"AND":"OR", first?"(":(last.equals(field)?")":""));
				first=false;
				continue;			
			}
			if (obj.isDateTime()) {
				if (!JDateTools.CheckDatetime(value,false)) continue;
				RFilter filter = this.getRecords().addFilter(field,value, JObject.JDATETIME, "=", first?"AND":"OR", first?"(":(last.equals(field)?")":""));
				first=false;
				continue;			
			}
			if (obj.isString()) {
				RFilter filter = this.getRecords().addFilter(field,value, JObject.JSTRING, "ilike", first?"AND":"OR", first?"(":(last.equals(field)?")":""));
				first=false;
			}
		}
		
	}
	protected void asignFilterByControl(JFormControl control) throws Exception {
		if (control.hasMultiField()) {
			asignFilterByControlMultiField(control);
			return;
		}
		if (control.getFixedProp() == null)
			return;
		if (control.isDisplayOnly())
			return;
		RFilter staticFilter = this.getRecords().getStructure().getFiltro(control.getFixedProp().GetCampo(), control.getOperator());
		if (staticFilter != null && !staticFilter.isDynamic() && getRecords().hidePreasignedFilters())
			return;
		boolean isObject=control.getProp()==null?false:control.getProp().isRecord();
		boolean hasValue=control.hasValue();
		String valorStr = control.getValue();
		JWin valorObj = null;
		if (isObject) {
			JWin obj = control.GetWinSelect();
			hasValue=obj!=null;
			if (hasValue) {
				staticFilter=null;
				valorObj=obj;
			}
		}
		if (!hasValue  && (getRecords().hidePreasignedFilters()||staticFilter == null)) {
			this.getRecords().clearFilter(control.getFixedProp().GetCampo(), control.getOperator());
			return;
		}

		if (!isObject && valorStr.equals(JFormRadioResponsive.NO_FILTER)) return;
		
		String field = control.getFixedProp().GetCampo();

		String operator = "=";
		if (control.getOperator() != null)
			operator = control.getOperator();
		boolean used=false;
		if (staticFilter != null) { //emilio manda filtros locos
			if (!(staticFilter.getObjValue() instanceof JRecord)) return;
			RFilter filter = this.getRecords().addFilter(field, (JRecord)staticFilter.getObjValue(), operator);
			filter.setVirtual(control.getFixedProp().isVirtual());
			filter.setDynamic(true);
			filter.setUserdefined(!control.isValueDefault());
			used=true;
			
		} else if (control.getProp().isIntervalDateTime()||control.getProp().isIntervalDate()) {
			if (!valorStr.equals("")) {
				control.setValue(valorStr);
				RFilter filter = this.getRecords().addFilter(field, control.getProp());
				filter.setVirtual(control.getFixedProp().isVirtual());
				filter.setDynamic(true);
				filter.setUserdefined(!control.isValueDefault());
			}
			used=true;
		} else if (control.useTwoFields()) {
			if (!valorStr.equals("")) {
				JFormTwoPropertiesResponsive controlTwo = (JFormTwoPropertiesResponsive) control;
				if (!controlTwo.getPropFrom().toString().equals("")) {
					RFilter filterFrom = this.getRecords().addFilter(controlTwo.getFieldPropFrom(), controlTwo.getPropFrom().toString(),">=");
					filterFrom.setVirtual(control.getFixedProp().isVirtual());
					filterFrom.setDynamic(true);
					filterFrom.setUserdefined(!control.isValueDefault());
				}
				if (!controlTwo.getPropTo().toString().equals("")) {
					RFilter filterTo = this.getRecords().addFilter(controlTwo.getFieldPropTo(), controlTwo.getPropTo().toString(),"<=");
					filterTo.setVirtual(control.getFixedProp().isVirtual());
					filterTo.setUserdefined(!control.isValueDefault());
					filterTo.setDynamic(true);
				}
			}
			used=true;
		} else if (control.getProp().isRecord()) {
			JWin win = valorObj;
			RFilter filter = this.getRecords().addFilter(field, win.getRecord(), operator);
			filter.setVirtual(control.getFixedProp().isVirtual());
			filter.setDynamic(true);
			filter.setUserdefined(!control.isValueDefault());
			used=true;
		} if (control.getProp().isDate()) {
			if (valorStr.indexOf(" - ")!=-1) {
				JIntervalDate intervalValue= new JIntervalDate();
				intervalValue.setValue(valorStr);
//				control.setValue(intervalValue);
				RFilter filter = this.getRecords().addFilter(field, intervalValue);
				filter.setVirtual(control.getFixedProp().isVirtual());
				filter.setDynamic(true);
				filter.setUserdefined(!control.isValueDefault());
				used=true;
			}
		}  else if (control.getProp().isDateTime()) {
			if (valorStr.indexOf(" - ")!=-1) {
				JIntervalDateTime intervalValue= new JIntervalDateTime();
				intervalValue.setValue(valorStr);
	//			control.setValue(intervalValue);
				RFilter filter = this.getRecords().addFilter(field, intervalValue);
				filter.setVirtual(control.getFixedProp().isVirtual());
				filter.setUserdefined(!control.isValueDefault());
				filter.setDynamic(true);
				used=true;
			}
		}
		if (!used) {
			RFilter filter = this.getRecords().addFilter(field, valorStr, operator);
			filter.setVirtual(control.getFixedProp().isVirtual());
			filter.setUserdefined(!control.isValueDefault());
			filter.setDynamic(true);
		}
	}
	public void AddColumnasDefault(JWinList zLista) throws Exception {
		JWin oWinAux = this.getWinRef();
		if (zLista instanceof JWinList) {
			zLista.AddIcono("");
		}

		JIterator<JProperty> iter = oWinAux.getRecord().getFixedProperties().getValueIterator();
		// Iterator oIt = aPropOrden.iterator();
		while (iter.hasMoreElements()) {
			JProperty oProp = iter.nextElement();
			if (!oProp.ifConverString())
				continue;
			zLista.AddColumnaLista(oProp.GetDescripcion(), oProp.GetCampo());
		}
	}
	public boolean needAutocomplete(JWinList zLista) throws Exception {
		long minCol = BizUsuario.getUsr()==null?-1:BizUsuario.getUsr().getObjBusiness().getCountColumnsForAddAutocomplete();
		if (minCol==-1) return false;
		if (zLista.GetColumnasLista()==null) return false;
		if (zLista.GetColumnasLista().size()==1) return false;
		if (zLista.isListFlat()) return false;
		return minCol>=zLista.GetColumnasLista().size();
	}

	public void AddFiltrosDefault(JWinList zLista) throws Exception {
		// zLista.ClearFiltrosLista();

		JWin oWinAux = this.getWinRef();
		// JHash oEnum = oWinAux.GetDato().GetPropiedades();
		JIterator<JProperty> iter = oWinAux.getRecord().getFixedProperties().getValueIterator();
		// Iterator oIt = aPropOrden.iterator();
		while (iter.hasMoreElements()) {
			JProperty oProp = iter.nextElement();
			if (oProp.isKey())
				zLista.AddFiltrosLista(oProp.GetCampo());
		}
	}

	public void configureColumnsByTable(JWinList zLista) throws Exception {
		String company = BizUsuario.getUsr().getCompany();
		String classname = this.getClass().getName();
		if (BizListExcludeCol.generateCustomColumnList(company, classname, this.GetVision(), zLista) == false)
			this.ConfigurarColumnasLista(zLista);

	}

	protected boolean bForceHideActions = false;
	
	public boolean isForceHideActions() {
		return bForceHideActions;
	}

	public void setForceHideActions(boolean bForceHideActions) {
		this.bForceHideActions = bForceHideActions;
	}
	protected Boolean bWithinHeader = null;
	
	public Boolean isWithHeader() {
		return bWithinHeader;
	}

	public void setWithHeader(boolean bForceHideActions) {
		this.bWithinHeader = bForceHideActions;
	}
	protected Boolean bWithinFooter = null;
	
	public Boolean isWithFooter() {
		return bWithinFooter;
	}

	public void setWithFooter(boolean bForceHideActions) {
		this.bWithinFooter = bForceHideActions;
	}
	protected void configureList(JWinList list) throws Exception {
		list.setHideActions(this.isForceHideActions());
		if (isWithHeader()!=null)
			list.setWithHeader(this.isWithHeader().booleanValue());
		if (isWithHeader()!=null)
			list.setWithFooter(this.isWithFooter().booleanValue());
	}		
	public void ConfigurarColumnasListaInternal(JWinList zLista) throws Exception {
		JWebSkin skin = BizUsuario.retrieveSkin();
		this.configureList(zLista);
		if (!zLista.isHideActions())
			if (skin!=null) skin.createGenerator().buildPreConfigurarColumnaLista(this, zLista);
		if (BizPssConfig.getPssConfig().useCustomColumns())
			configureColumnsByTable(zLista);
		else
			ConfigurarColumnasLista(zLista);
		if (!zLista.isHideActions())
			if (skin!=null) skin.createGenerator().buildPosConfigurarColumnaLista(this, zLista);
		if (zLista.needAutocomplete(this))
			zLista.AddColumnaAutocomplete();

		if (zLista.isEmpty())
			zLista.AddIcono("");
//			throw new Exception("Not has column defined in "+JLanguage.translate(GetTitle()));
	}

	public void ConfigurarColumnasLista(JWinList zLista) throws Exception {
		AddColumnasDefault(zLista);
	}

	public void ConfigurarMatrix(JWinMatrix zLista) throws Exception {
		// AddColumnasDefault(zLista);
	}

	public void ConfigurarFiltrosLista(JWinList zLista) throws Exception {
		AddFiltrosDefault(zLista);
	}

	protected JWin createNewWin() throws Exception {
		return this.createWin();
	}

	protected JWin createNewJoinWin() throws Exception {
		JWin oWin = this.createNewWin();
		this.joinData(oWin);
		return oWin;
	}

	public JWin createJoinWin() throws Exception {
		return createJoinWin(null);
	}

	public JWin createJoinWin(JRecord zBD) throws Exception {
		JWin oWin = createWin(zBD);
		this.joinData(oWin);
		return oWin;
	}

	public JWin createWin(JRecord zBD) throws Exception {
		JWin win = this.createWin();
		if (zBD != null)
			win.setRecord(zBD);
		return win;
	}

	public JWin createWin() throws Exception {
			return GetClassWin().newInstance();
			
	}

	protected void joinWin(JWin win) throws Exception {
		win.SetVision(this.GetVision());
		win.setParent(this);
		win.setObjetoPropagado(this.getObjetoAPropagar());
		win.setDropListener(this.getDropListener());
		win.setModeWinLov(isModeWinLov());

	}

	protected void joinData(JWin win) throws Exception {
		this.joinWin(win);
		this.getRecords().joinData(win.getRecord());
//		win.SetEnlazado(oEnlazado); // deprecado
		return;
	}

	@SuppressWarnings("unchecked")
	public JWin addRecord(JWin zWin) throws Exception {
		aWins.addElement(zWin);
		getRecords().addItem(zWin.getRecord());
		return zWin;
	}

	@SuppressWarnings("unchecked")
	public JWin AddItem(JWin zWin, int pos) throws Exception {
		aWins.addElementAt(pos, zWin);
		getRecords().addItem(zWin.getRecord(), pos);
		return zWin;
	}

	public void AppendDatos(JWins zWins) throws Exception {
		boolean first=true;
		JIterator<JWin> it = zWins.getStaticIterator();
		while (it.hasMoreElements()) {
			JWin win = it.nextElement();
			if (first) {
				this.getRecords().convertToHash(win.getKeyField());
				first=false;
			}
			if (this.getRecords().findInHash(win.getRecord().getPropAsString(win.getKeyField()))!=null) continue;
			this.addRecord(win);
		}
	}

	public JWins toStatic(JRecords records) throws Exception {
		this.getRecords().setStatic(true);
		if (records == null)
			return this;
		records.toStatic();
		aWins.removeAllElements();
		JIterator iter = records.getStaticIterator();
		while (iter.hasMoreElements()) {
			JRecord record = (JRecord) iter.nextElement();
			this.addRecord(createJoinWin(record));
		}
		this.setRecords(records);
		this.SetEstatico(true);
		return this;
	}

	public void OnPostReadDato(JWin zWin) throws Exception {
	}

	public static JWins createVirtualWinsFromMap(JMap<String, String> map) throws Exception {
		return createVirtualWinsFromMap(map, 157);
	}

	public static JWins createVirtualWinsFromMap(JMap<String, String> map, int zIcono) throws Exception {
		return CreateVirtualWins(JRecords.createVirtualFormMap(map, zIcono));
	}
	// public static JWins createVirtualWinsFromList(JList<String> list) throws
	// Exception {
	// return CreateVirtualWins(JRecords.createVirtualFormList(list));
	// }

	public static JWins CreateVirtualWins(JRecords<BizVirtual> records) throws Exception {
		GuiVirtuals v = new GuiVirtuals();
		v.toStatic(records);
		return v;
	}

	@SuppressWarnings("unchecked")
	public JRecords<JRecord> PasarADatos() throws Exception {
		JIterator<JWin> iter = this.aWins.getIterator();
		while (iter.hasMoreElements()) {
			JWin oWin = iter.nextElement();
			this.getRecords().addItem(oWin.getRecord());
		}
		return this.getRecords();
	}

//	@SuppressWarnings("unchecked")
//	@Override
//	public boolean ifAcceptRefresh(JWinEvent e, boolean zGlobal) throws Exception {
//		if (!(e.GetArgs() instanceof JWin))
//			return false;
//		JWin oWin = (JWin) e.GetArgs();
//		if (e.ifRefreshAltaOk() || zGlobal) {
//			if (this.getRecords().getBasedClass() == null)
//				return false;
//			if (!this.getRecords().getBasedClass().isAssignableFrom(oWin.getRecord().getClass()))
//				return false;
//			JRecord oDato = oWin.getRecord();
//			oDato.clearFilters();
//			oDato.getStructure().copyFiltersFrom(this.getRecords().getStructure());
//			oDato.keysToFilters();
//			oDato.SetNoExcSelect(true);
//			boolean bRead = oDato.Read();
//			oDato.clearFilters();
//			if (!bRead)
//				return false;
//
//			JWin newWin = this.createJoinWin(oDato);
//			e.SetArgs(newWin);
//			return true;
//		}
//		return false;
//	}
//
//	public boolean ifAcceptRefreshAll(JWinEvent e) throws Exception {
//		return false;
//	}

	public void OnReadAllDatos() throws Exception {
	}

	public void ClearStaticItems() throws Exception {
		getRecords().clearStaticItems();
		aWins.removeAllElements();
	}

	public void ClearWinItems() throws Exception {
		if (ifEstatico())
			return;
		aWins.removeAllElements();
	}

	public void SetEstatico(boolean zValue) throws Exception {
		bEstatico = zValue;
		getRecords().setStatic(zValue);
	}

	public void PasarAWins(JList zList) throws Exception {
		Iterator oIter = zList.iterator();
		while (oIter.hasNext()) {
			JRecord oBD = (JRecord) oIter.next();
			this.addRecord(this.createJoinWin(oBD));
		}
		this.getRecords().setStatic(true);
	}

	public JWin getStaticElementAt(int i) throws Exception {
		if (!ifEstatico())
			JExcepcion.SendError("No soportado");
		return aWins.getElementAt(i);
	}

	@SuppressWarnings("unchecked")
	public void removeWin(JWin zWin) throws Exception {
		aWins.removeElement(zWin);
		getRecords().removeStaticItem(zWin.getRecord());
	}

	@SuppressWarnings("unchecked")
	public void replaceWin(JWin zOldWin, JWin zNewWin) throws Exception {
		int position;

		position = getRecords().getStaticItems().indexOf(zOldWin.getRecord());
		System.out.print("position1: " + position + "\n");

		if (position == -1)
			JExcepcion.SendError("No se encontro el Elemento en BDs");

		AddItem(zNewWin, position);
		removeWin(zOldWin);
	}

	@SuppressWarnings("unchecked")
	public void removeBD(JRecord zBD) throws Exception {
		int idx = getRecords().getStaticItems().indexOf(zBD);
		if (idx == -1)
			JExcepcion.SendError("No se ha encontrado el dato para Eliminar");
		aWins.removeElementAt(idx);
		getRecords().getStaticItems().removeElementAt(idx);
	}

	public JAct getActionSecurityFull() throws Exception {
		return new JActQuery(this.getObjWinProperty());
	}

	public JWin getObjWinProperty() throws Exception {
		JWin wp = new GuiWinPropiedad();
		BizWinPropiedad p = (BizWinPropiedad) wp.getRecord();

		JWins wins = (JWins)this.getActionOwnerClass().newInstance();

		p.setObjName(wins.GetTitle());
		p.setClase(wins.getClass().getName());
		p.setNroIcono(this.GetNroIcono());

		BizActions allActions = new BizActions();
		allActions.appendRecords(this.getActionMap());
		JWin win = (JWin)wins.GetClassWin().newInstance();
		BizActions actions = win.getActionMap();
		if (actions == null)
			return wp;
		JIterator<BizAction> iter = actions.getStaticIterator();
		while (iter.hasMoreElements()) {
			BizAction a = iter.nextElement();
			a.setOwner(p.getClase());
			allActions.addItem(a);
		}
		p.SetObjAcciones(allActions);
		return wp;
	}

	// public void showListForm(JControlWin controlWin) throws Exception {
	// JListForm oForm=new JListForm(this, controlWin);
	// oForm.GetLista().setRefreshOnlyOnUserRequest(this.isRefreshOnlyOnUserRequest());
	// oForm.Mostrar();
	// }

	public JControlWin createControlWin() throws Exception {
		return new JControlWin() {

			@Override
			public JWins getRecords() throws Exception {
				JWins wins= GetThis().createClone();
				wins.getRecords().clearFilters();
				return wins;
			}
		};
	}

	public long SelectCount() throws Exception {
		return getRecords().selectCount();
	}

	/*
	 * private JList getActiveLists() { if (this.aActiveLists == null) {
	 * this.aActiveLists = JCollectionFactory.createList(); } return
	 * this.aActiveLists; }
	 * 
	 * private boolean hasActiveLists() { return this.aActiveLists != null &&
	 * !this.aActiveLists.isEmpty(); }
	 * 
	 * public boolean hasActiveList(JWinList zList) { return this.aActiveLists
	 * != null && this.aActiveLists.containsElement(zList); }
	 * 
	 * public void addActiveList(JWinList zList) { if
	 * (this.hasActiveList(zList)) return;
	 * this.getActiveLists().addElement(zList); }
	 * 
	 * public void removeActiveList(JWinList zList) {
	 * this.getActiveLists().removeElement(zList); }
	 */
	@Override
	public void SetTitle(String zValue) throws Exception {
		super.SetTitle(zValue);
		/*
		 * if (!this.hasActiveLists()) return;
		 * 
		 * Iterator oListsIt = this.getActiveLists().iterator(); while
		 * (oListsIt.hasNext()) { ((JWinList)
		 * oListsIt.next()).updateRootTitle(); }
		 */}

	public void setWebPageable(boolean value) {
		bPaginable = value;
	}

	public boolean isWebPageable() {
		return bPaginable;
	}

	public boolean ifRefreshToolbarOnInsert() {
		return false;
	}

	public boolean ifRefreshToolbarOnDelete() {
		return false;
	}

	public boolean isEditable() throws Exception {
		return false;
	}

	public boolean isEditableAlta() throws Exception {
		return false;
	}

	public void setForceRefreshUI(boolean value) throws Exception {
		this.bForceRefreshUI = value;
	}

	public void setShowFilters(boolean value) throws Exception {
		this.bShowFilters = value;
	}

	private boolean bShowWinToolbar = true;

	public void setShowWinToolbar(boolean value) throws Exception {
		this.bShowWinToolbar = value;
	}

	public boolean isShowWinToolBar() throws Exception {
		return this.bShowWinToolbar;
	}

	public boolean isShowFilters() throws Exception {
		return this.bShowFilters;
	}

	public boolean isForceRefreshUI() throws Exception {
		return this.bForceRefreshUI;
	}

	// public void setMultiSelection(boolean value) {
	// this.bMultiSelection = value;
	// }
	// public boolean isMultiSelection() {
	// return this.bMultiSelection;
	// }
//	public GuiActions getActionMapMulti() throws Exception {
//		JWin winRef = this.getWinRefWithActions();
//		// genero un vector con todas las acciones posibles
////		winRef.generateFullActionMap();
//		BizActions fullActions = winRef.getActionMap();
//		// Se filtran las acciones que no son multiples
//		GuiActions multiActions = new GuiActions();
//		multiActions.SetEstatico(true);
//		JIterator iter = fullActions.getStaticIterator();
//		while (iter.hasMoreElements()) {
//			BizAction action = (BizAction) iter.nextElement();
//			if (!action.isMulti())
//				continue;
//			action.setObjOwner(this);
//			GuiAction wAction = new GuiAction();
//			wAction.setRecord(action);
//			multiActions.addRecord(wAction);
//		}
//		if (!multiActions.getRecords().ifRecordFound())
//			return multiActions;
////		boolean estatico = this.getRecords().isStatic();
//		// se barren los registros por cada accion
//		this.firstRecord();
//		while (this.nextRecord()) {
//			JWin win=this.getRecord();
//			JIterator<JWin> actionIter=multiActions.getStaticIterator();
//			while (actionIter.hasMoreElements()) {
//				GuiAction action = (GuiAction) actionIter.nextElement();
//				if (win.findAction(action.GetcDato().getId()) == null) {
//					actionIter.remove();
//					multiActions.removeWin(action);
//				}
//			}
//		}
////		this.SetEstatico(estatico);
//		return multiActions;
//	}

	public JWins createClone() throws Exception {
		JWins clone = getClass().newInstance();
		clone.setExtraFilter(this.getExtraFilter());
		clone.setDropListener(this.getDropListener());
		clone.SetVision(this.GetVision());
		clone.getRecords().SetSQL(this.getRecords().getStructure().getSQL());
		clone.getRecords().setParent(this.getRecords().getParent());
		clone.getRecords().setStructure(this.getRecords().getStructure().cloneStructure());
		clone.getRecords().setStatic(this.getRecords().isStatic());
		return clone;
	}

	public void forzeRefresh() throws Exception {
		((JRecords) this.GetBaseDato()).notifyUpdateAllOK();
		JBDatos.notifyCommit();
	}

	public boolean isGeoPositionable() throws Exception {
		try {
			JWin oWin = this.GetClassWin().newInstance();
			return oWin.isGeoPositionable();
		} catch (InstantiationException e) {
			PssLogger.logError(e);
		} catch (IllegalAccessException e) {
			PssLogger.logError(e);
		}
		return false;
	}

	// public boolean isFilterBarAssigned() {
	// return filterBarAssigned;
	// }
	//
	// public void clearFilterBarAssigned() {
	// filterBarAssigned = false;
	// }

	public JWin getReport() throws Exception {
		GuiReporteBase oGuiReport = new GuiReporteBase();
		oGuiReport.GetcDato().setWins(this);
		return oGuiReport;
	}

	public void AddValueToWinsMapFilter(JFormControl control) throws Exception {
		if (this.oFiltrosKeyDesc == null)
			this.oFiltrosKeyDesc = JCollectionFactory.createOrderedMap();

		ArrayList<Object> a = new ArrayList<Object>();
		a.add(control.getValueDescription());
		a.add(control.isVisible());
		this.oFiltrosKeyDesc.addElement(control.GetDisplayName(), a);
	}

	public JOrderedMap<String, ArrayList<Object>> getFilters() throws Exception {
		return this.oFiltrosKeyDesc;
	}

	/**
	 * @return the pagesize
	 */
	public int getPagesize() {
		return pagesize;
	}

	/**
	 * @param pagesize
	 *            the pagesize to set
	 */
	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}

	/**
	 * @return the offset
	 */
	public int getOffset() {
		return offset;
	}

	/**
	 * @param offset
	 *            the offset to set
	 */
	public void setOffset(int offset) {
		this.offset = offset;
	}

	public boolean isEOF() throws Exception {
		return this.getRecords().isEOF();
	}

	public boolean canReadAll(BizAction a) throws Exception {
		// esta funcion se puede sobrescribir para por ejemplo para verificar
		// que haya
		// filtros suficientes
		if (this.isRefreshOnlyOnUserRequest() )
			if (!checkRefreshOnlyOnUserRequest(a))
				return false;
		if (this.isRefreshOnlyOnUserRequestStrict()) {
			if (!checkRefreshOnlyOnUserRequestStrict(a))
				return false;
		}  
		return true;
	}
	
	public boolean checkRefreshOnlyOnUserRequestStrict(BizAction a) throws Exception {
		if (!hasDynamicFilters(false) && !a.isExport()) {
				if (a.isSubmitedByUser())
					setAlert(BizUsuario.getUsr().getMessage("Por favor, refine la busqueda seleccionando algún filtro",null));
				return false;
		}
		return true;
	}
	
	public boolean checkRefreshOnlyOnUserRequest(BizAction a) throws Exception {
		if (a.isExport()) return true;
		return (a.isSubmitedByUser());
	}
	
	
	public boolean hasDynamicFilters(boolean withDefault) throws Exception {
		return this.getRecords().hasDynamicFilters(withDefault);
	}



	public long selectSupraCount() throws Exception {
		return this.getRecords().selectSupraCount();
	}

	protected boolean checkAction(BizAction a) throws Exception {
		if (!super.checkAction(a))
			return false;
		if (this.hasDropListener())
			return this.checkActionOnDrop(a);
		return true;
	}

	protected boolean checkActionOnDrop(BizAction a) throws Exception {
		if (a.getId()==99998) return true;
		return isModeWinLov();
	}

	public boolean hasSubDetail() throws Exception {
		return false;
	}

	
	public JWins getSubDetails(JWin win, boolean zExporting) throws Exception {
		return win.getWinsDetail(); // esto busca la acion detail, no usar mas y sobreescribir esta funcion  getSubDetails()
	}
	
	public boolean hasSubDetail(boolean zExporting) throws Exception {
		return hasSubDetail();
	}

	// @Override
	// public void generateFullActionMap() throws Exception {
	// super.generateFullActionMap();
	// JIterator<BizAction> iter = this.getActionMap().getStaticIterator();
	// while (iter.hasMoreElements()) {
	// BizAction a = iter.nextElement();
	// if (!this.okWinAction(a))
	// iter.remove();
	// }
	// // se le agregan las acciones dinamicas
	// // this.createDynamicAction();
	// }
	//
	// protected boolean okWinAction(BizAction a) throws Exception {
	// return true;
	// }
	/*
	 * public JTotalizer getTotalizer() { return totalizer; }
	 */

	public JWin getWinRefWithActions() throws Exception {
		JWin win = this.getWinRef();
		win.SetVision(this.GetVision());
		win.verifyActionWinRef();
		return win;
	}

	public String getFirstValorItemClave() throws Exception {
		this.toStatic();
		if (this.getRecords().sizeStaticElements() == 0)
			return null;
		JWin win = this.getStaticElementAt(0);
		return win.GetValorItemClave();
	}

	public boolean hasNavigationBar() throws Exception {
		return true;
	}

	public String getFilterValue(String filter) throws Exception {
		return this.getRecords().getFilterValue(filter);
	}

	public void assignDropListener(JBaseWin listener) throws Exception {
		cleanWinRef();
		this.setDropControlIdListener(listener==null?null:listener.getDropControlIdListener());
		this.setDropListener(listener);
		if (!this.getRecords().isStatic())
			return;
		JIterator<JWin> iter = this.getStaticIterator();
		while (iter.hasMoreElements()) {
			JWin w = iter.nextElement();
			w.assignDropListener(listener);
		}
	}

	public boolean isDependentList() throws Exception {
		return false;
	}

	public JWin getFirstRecord() throws Exception {
		getRecords().firstRecord();
		return this.getStaticElementAt(0);
	}
	// private boolean bForceToolbaTop=false;
	// public boolean isForceToolbarTop() throws Exception {
	// return this.bForceToolbaTop;
	// }
	// public void setForceToolbarTop(boolean value) throws Exception {
	// this.bForceToolbaTop=value;
	// }

	// S con preview N Sin preview, M con preview Maximizado
	public static String PREVIEW_SI = "S";
	public static String PREVIEW_NO = "N";
	public static String PREVIEW_MAX = "M";

	private String sPreviewFlag = null;

	public String getPreviewFlag() throws Exception {
		if (sPreviewFlag == null) {
			if (BizUsuario.getUsr() == null)
				return PREVIEW_SI;
			return BizUsuario.getUsr().getObjBusiness().getPreviewFlag();
		}
		return this.sPreviewFlag;
	}

	// public boolean isWithPreview(boolean embedded,boolean formLov) throws
	// Exception {
	// if (formLov) return false;
	// return this.bWithPreview==0?!embedded:this.bWithPreview==1;
	// }
	public void setPreviewFlag(String value) throws Exception {
		// this.bWithPreview=value?1:2;
		this.sPreviewFlag = value;
	}

	private Boolean bForceSelected;

	public Boolean isForceSelected(boolean embedded, boolean formLov, boolean isWinsSelect) throws Exception {
		if (formLov)
			return false;
		return null;
	}

	private long lForceActionPreview;

	public void setForceActionPreview(long value) throws Exception {
		this.lForceActionPreview = value;
	}

	public long getForceActionPreview() throws Exception {
		return lForceActionPreview;
	}

	public void setForceSelected(boolean value) throws Exception {
		this.bForceSelected = value;
	}

	public int getOrdenWinsToolsActions() throws Exception {
		return 1;
	}

	public int getOrdenWinsActions() throws Exception {
		return 2;
	}

	public int getOrdenWinActions() throws Exception {
		return 3;
	}

	public void addOrderByFromUI(String columnOrden, String dirOrden) throws Exception {
		addOrderByFromUI(columnOrden, dirOrden, true);
	}

	public boolean addOrderByFromUI(String columnOrden, String dirOrden, boolean exception) throws Exception {
		JProperty prop = this.getRecords().getRecordRef().getFixedProp(this.getRecords().getRecordRef().getClass(), columnOrden, false);

		if (prop == null || prop.isVirtual()) {
			if (exception)
				throw new Exception("No se pudo ordenar por: " + columnOrden);
			else
				return false;
		}
		getRecords().addOrderBy(columnOrden, dirOrden);
		return true;
	}

	public boolean contains(JRecord r) throws Exception {
		if (ifEstatico()) {
			JIterator<JWin> it = aWins.getIterator();
			while (it.hasMoreElements()) {
				JWin win = it.nextElement();
				if (win.getRecord().isEqual(r))
					return true;
			}
			return false;
		} else {
			JWin win = getWinRef();
			win.getRecord().keysToFilters(r);
			return win.getRecord().exists();
		}
	}

	public boolean extract(JRecord r) throws Exception {
		if (ifEstatico()) {
			JIterator<JWin> it = aWins.getIterator();
			while (it.hasMoreElements()) {
				JWin win = it.nextElement();
				if (win.getRecord().isEqual(r)) {
					it.remove();
					return true;
				}
			}
			return false;
		} else {
			throw new Exception("Solo para estaticos");
		}
	}

	public JList<JWin> getWinsList() throws Exception {
		return aWins;
	}

	String previewSplitPos = null;

	public void setPreviewSplitPos(String p) throws Exception {
		previewSplitPos = p;
	}
	public void setPreviewSplitPos(int p) throws Exception {
		previewSplitPos = p+"px";
	}

	public String getPreviewSplitPos() throws Exception {
		return previewSplitPos;
	}

	public int getWebPageSize() {
		return -1;
	}

	public boolean hasChecked(String fld) throws Exception {
		return this.getRecords().hasChecked(fld);
	}

	public boolean hasAnyRecord() throws Exception {
		return this.getRecords().ifRecordFound();
	}

	public boolean isWin() {
		return false;
	}
	
  public JAct getActExport(final String type) throws Exception {
    return new JActFileGenerate(this) {
  		public String generate() throws Exception {
  			return toExcel();
  		}
  	};
  	
  }

	public void Ordenar(String criteria) throws Exception {
		this.getRecords().pushCriteria(criteria);
		aWins.sort();
		this.getRecords().clearStaticItems();
		this.PasarADatos();
	}


	public String toExcel() throws Exception {
		JWinsExcel excel = new JWinsExcel();
		excel.setWins(this);
		return excel.toExcel();
	}

	public boolean isExportToDownloadForm() throws Exception {
		return false;
	}	
	public void ConfigurarColumnasJSonApiService(JWinList zLista) throws Exception {
		this.ConfigurarColumnasLista(zLista);
	}

	public JWins appendWins(JWins<TWin> wins) throws Exception {
		if (wins == null)
			return this;
		wins.firstRecord();
		while (wins.nextRecord())
			this.addRecord(wins.getRecord());
		return this;
	}

	public JScript getScript() throws Exception {
		return null;
	}
	
	public JWins empty() throws Exception {
		this.getRecords().setStatic(true);
		this.getRecords().clearStaticItems();
		return this;
	}
	
	public boolean isNeverSelected() throws Exception {
		return false;
	}
	
	

	
}
