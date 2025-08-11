package pss.www.ui;

import pss.common.security.BizUsuario;
import pss.core.services.fields.JObject;
import pss.core.services.records.JRecord;
import pss.core.tools.JPair;
import pss.core.tools.JTools;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JMap;
import pss.core.win.JBaseWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JActFieldSwapWins;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.controls.JFormControles;
import pss.core.winUI.lists.JFormFiltro;
import pss.core.winUI.responsiveControls.JFormCheckResponsive;
import pss.core.winUI.responsiveControls.JFormComboResponsive;
import pss.core.winUI.responsiveControls.JFormControlResponsive;
import pss.core.winUI.responsiveControls.JFormSwapListResponsive;
import pss.www.platform.actions.JWebAction;
import pss.www.platform.actions.JWebActionFactory;
import pss.www.platform.content.generators.JXMLContent;
import pss.www.ui.controller.JWebFormControl;

public class JWebWinSwapListResponsive extends JWebWinGenericResponsive implements JAbsolutelyNamedWebViewComponent, JWebControlInterface {

	JWins winsSource;
	JWins winsSelected;
	String fieldKeySource;
	String fieldKeyOptions;
	boolean useCache =false;
	
	public String getFieldKeySource() {
		return fieldKeySource;
	}

	public void setFieldKeySource(String fieldKeySource) {
		this.fieldKeySource = fieldKeySource;
	}

	public String getFieldKeyOptions() {
		return fieldKeyOptions;
	}

	public void setFieldKeyOptions(String fieldKeyOptions) {
		this.fieldKeyOptions = fieldKeyOptions;
	}

	//
	// CONSTRUCTOR
	//
	public JWebWinSwapListResponsive(JActFieldSwapWins action) throws Exception {
		this.sourceAction = action.getActionSource();
		this.winsSource = action.getOptions();
		this.winsSelected = action.getSelecteds();
	}

	public JWebWinSwapListResponsive(BizAction action, String zCampo, JWin zWinCampo, Class zWinsClass, JWins zWinsSource) throws Exception {
		this.sourceAction = action;
		this.campo = zCampo;
		this.winCampo = zWinCampo;
		this.classWinsCampo = zWinsClass;
		this.winsSource = zWinsSource;
	}

	public JWebViewInternalComposite createActionBar() throws Exception {
		this.oActionBar=new JWebWinActionBarSwapList(this, false, this.getToolbar(), this.isEmbedded());
		this.oActionBar.setPreview(false);
		this.oActionBar.setRootPanel(BizUsuario.retrieveSkinGenerator().createActionBarWinList(this));
		return this.oActionBar.getRootPanel();
	}

	public String getPresentation() throws Exception {
		return JBaseWin.MODE_SWAP;
	};

	public String getToolbar() throws Exception {

		return JBaseWin.TOOLBAR_TOP;
	}

	public JWebViewComposite getRootPanel() {
		return this.oRootPanel;
	}

	public static JWebWinSwapListResponsive create(JWebViewComposite parent, JFormSwapListResponsive control) throws Exception {
		BizAction pageAction = control.getAction();

		JWebViewComposite realParent = parent;
		if (parent instanceof JWebTabPanelResponsive) {
			JWebTabResponsive tab = new JWebTabResponsive();
			tab.setTitle(pageAction.GetDescr());
			tab.setId(control.getIdControl());
			if (parent != null) {
				tab.setPreview(parent.findJWebWinForm().isPreview());
				parent.addChild(tab.getObjectName(), tab);
			}
			realParent = tab;
		}
		JWebWinSwapListResponsive webList = new JWebWinSwapListResponsive(control.getAction(), control.getCampo(), control.getWinCampo(), control.getWinClassCampo(), control.getWinsSource());
		webList.takeAttributesFormControlResponsive(control);
		webList.setParentProvider(parent.findJWebWinForm());
		webList.setTitle(control.getTitle());
		webList.setMode(control.getMode());
		webList.setControl(control);
		webList.setFieldKeyOptions(control.getFieldKeyOptions());
		webList.setFieldKeySource(control.getFieldKeySource());
		webList.setInForm(parent.findJWebWinForm()!=null);
		webList.ensureIsBuilt();
		webList.setName(pageAction!=null?pageAction.getIdAction():control.getCampo());
		realParent.addChild(webList.getRootPanel().getName(), webList.getRootPanel());
		return webList;

	}

	@Override
	protected void build() throws Exception {
		this.regiterObjects();
		oRootPanel = BizUsuario.retrieveSkinGenerator().buildSwapList(this);

	}

	//
	// SUPERCLASS OVERRIDING
	//
	@Override
	public void validate() throws Exception {

	}

	@Override
	public void destroy() {
		if (!hasWins()) {
			try {
				this.getWins().getRecords().closeRecord();
				if (this.getWins() instanceof JWebReportWins) {
					((JWebReportWins) this.getWins()).cleanUp();
				}
			} catch (Exception e) {
			}

			releaseWins();
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
		return "swaplist_responsive";
	}

	public void fillParentInfo(JFormControl c) throws Exception {
		c.setConnectControl(this.getName());
		String field = null;
		String descr = null;
		if (c.getFixedProp().isRecord() && !c.hasToRefreshForm()) {
			JRecord rec = (JRecord) c.getFixedProp().getClase().newInstance();
			field = rec.getKeyField();
			descr = rec.getDescripField();
			c.setConnectControlField(c.getFixedProp().GetCampo() + "." + c.getKeyFieldName(rec.getKeyField()));
			c.setConnectControlDatatype(c.GetObjectType());
			c.setConnectControlOperator(c.getOperator());
		} else {
			c.setConnectControlField(c.getFixedProp().GetCampo());
			c.setConnectControlDatatype(c.GetObjectType());
			c.setConnectControlOperator(c.getOperator());
		}
		if (c instanceof JFormComboResponsive) {
			((JFormComboResponsive) c).setNeverUseCache(true);
			// if (field!=null)
			// ((JFormComboResponsive)c).setSearchFields(field, descr);

		}
//		if (c instanceof JFormWinLOVResponsive) {
//			((JFormWinLOVResponsive) c).setNeverUseCache(true);
//			((JFormWinLOVResponsive) c).setUseID(false);
//		}
	}

	public JWebFilterPaneResponsive createFormFiltros() throws Exception {
		if (this.getWins() == null)
			return null;
		if (!getWins().isShowFilters())
			return null;

		recoveryFiltersInHistory();

		this.formFiltros = new JFormFiltro(winsSource);
		this.formFiltros.initialize();
//		this.formFiltros.convertToResponsive();
		Boolean visible = this.isShowFilterBarInternal();
		if (visible == null) {
			visible = !hasFilterNeverHide();
			setShowFilterBar(visible);
		}
		JWebFilterPaneResponsive filterPanel = BizUsuario.retrieveSkinGenerator().buildFormFilter(this);
		filterPanel.addDropManager("filterPanel", getWins());

		this.getWins().getRecords().clearDynamicFilters();

		if (this.isFirstAccess() || (sourceAction != null && !this.sourceAction.hasFilterMap())) {
			// this.sourceAction.clearFilterMap();
			this.formFiltros.asignDefaultData();
		}
		this.formFiltros.applyFilterMap(this.sourceAction, this.isFirstAccess());
		if (sourceAction != null && this.sourceAction.hasFilterMap())
			winsSource.checkControls(this.formFiltros, this.sourceAction.getFilterMap().getFieldChange());
		winsSource.asignFiltersFromFilterBarSwap(this.formFiltros);

		int i = 0;
		JFormControles webControls = new JFormControles(null, null);
		JIterator<JFormControl> iter = BizUsuario.retrieveSkinGenerator().filtersResorted(this.formFiltros.GetControles().GetItems());
		while (iter.hasMoreElements()) {
			JFormControl c = iter.nextElement();
			if (!c.isVisible())
				continue;
			if (isReadOnly())
				c.SetReadOnly(isReadOnly());
			fillParentInfo(c);
			BizUsuario.retrieveSkinGenerator().addAtributesToFilterControls(this, (JFormControlResponsive) c);

			JWebFormControl webControl = filterPanel.createWebControl(c, null);
			String zone = "filtro_" + webControl.getIdControl();
			// if (getWins().getObjectDrageable(zone)!=null)
			// intPanel.setSkinStereotype("win_list_filters_item_drag");
			// else
			// intPanel.setSkinStereotype("win_list_filters_item");
			c.addDropManager(zone, getWins());
			c.SetControles(webControls); // para que el formcontrol de swing tenga los
																		// punteros correctos
			webControls.AddControl(webControl);

		}

		this.formFiltros.setControles(webControls);
		this.formFiltros.disableReadOnlyFilters();

		setShowSearchButton(visible);
		setNothingToShowInFilterBar(false);
		if (!visible) {
			boolean modeConFiltrosVisibles = hasFilterNeverHide();
			setNothingToShowInFilterBar(!this.formFiltros.hideFiltersWithinData(modeConFiltrosVisibles));
			// setShowSearchButton(modeConFiltrosVisibles);
		}

		if (!this.formFiltros.hasAnyFilterVisible() && visible) {
			setNothingToShowInFilterBar(true);
			return null;
		}

		return filterPanel;
	}

	@Override
	public boolean isAllowExportToCSV() throws Exception {
		return false;
	}

	@Override
	public boolean isAllowExportToExcel() throws Exception {
		return false;
	}

	public JWins getSelected() throws Exception {
		return winsSelected;
	}

	private JPair<String, String> buildPair(JXMLContent zContent, JWin win) throws Exception {
		String id = zContent.addObject(win);
		String desc = obtainDescription(win);
		return new JPair<String, String>(id, desc);
	}

	private String obtainKey(JWin win) throws Exception {
		return win.getRecord().getPropAsString(getFieldKeyOptions());
	}

	private String obtainDescription(JWin win) throws Exception {
		return (win.getSwapKeyField() == null ? "" : win.getRecord().getPropAsString(win.getSwapKeyField()) + " - ") + win.getRecord().getPropAsString(win.getSwapDescripField());
	}

	private String obtainValue(JFormControl fc, JWin win, String field, JFormFiltro formFiltros, boolean forceInvisible) throws Exception {
		if (fc.hasToRefreshForm() && !forceInvisible)
			return formFiltros.findControl(field).getValue();
		if (win.getRecord().getPropDeep(field, false) != null)
			return win.getRecord().getPropDeep(field, false).toString();
		return "";
	}

	private void fillMapKeys(JWins options, JMap<String, String> keys, String fieldSource) throws Exception {
		JIterator<JWin> itC = options.getStaticIterator();
		while (itC.hasMoreElements()) {
			JWin win = itC.nextElement();
			JObject obj = win.getRecord().getPropDeep(fieldSource, false);
			if (obj == null)
				continue;
			
			String key = obj.toString();
		
//			if (keys.getElement(key)!=null)  //duplicados
//				key=key+"_xxxclone"+win.hashCode();
//				

			keys.addElement(key, win.GetValorItemClave());
		}
	}

	private JWins buildOptions(String fieldSource) throws Exception {
		JWins options = getWins();
		if (getSelected() != null)
			options = getSelected();

		return options;
	}

	private void fillInfoFillters(JXMLContent zContent, JFormFiltro f, JFormFiltro f2, JFormFiltro formFiltros, JWin win, boolean forceInvisible) throws Exception {
		if (f == null)
			return;
		JIterator<JFormControl> itf = f.GetControles().getAllItems();
		while (itf.hasMoreElements()) {
			JFormControl fc = itf.nextElement();
			String field = fc.getFieldProp();
			String value = ""; 
			JObject obj;
			if (field != null)
				obj = win.getRecord().getPropDeep(field, false);
			else
				obj = fc.getProp(); // compatibilidad

			if (obj == null) {
				if (f2 == null)
					continue;
				value = findFiltro2(f2, fc.getIdControl(), win);
				if (value == null)
					continue;
			}
			if (obj.isRecord() && !fc.hasToRefreshForm()) {
				JRecord rec = (JRecord) win.getRecord().getFixedPropDeep(field).getClase().newInstance();
				field = field + "." + fc.getKeyFieldName(rec.getKeyField());
			}

			value = obtainValue(fc, win, field, formFiltros, forceInvisible);
			if (fc instanceof JFormCheckResponsive && value.equals("")) {
				value = "N";
			}

			zContent.startNode("extradata");
			zContent.setAttribute("extradata", field);
			zContent.setAttribute("extradata_value", value);
			zContent.endNode("extradata");
		}
	}

	// protected void componentToXML(JXMLContent zContent) throws Exception {
	@Override
	protected void containerToXML(JXMLContent zContent) throws Exception {
		JMap<String, String> keys = JCollectionFactory.createMap();
		JMap<String, JPair> items = JCollectionFactory.createMap();
		JMap<String, JWin> itemsSelecteds = JCollectionFactory.createMap();
		JMap<String, String> itemsAdded = JCollectionFactory.createMap();
		zContent.setAttribute("zobject", winsObjectId);
		zContent.setAttribute("zobjectdest", winsObjectDest);
		zContent.setAttribute("obj_provider", getProviderName());
		zContent.setAttribute("form_name", "form_" + getProviderName());

		String fieldSource = (getSelected() != null) ? getFieldKeyOptions() : getFieldKeySource();
		JWins options = buildOptions(fieldSource);

		fillMapKeys(options, keys, fieldSource);

		zContent.startNode("origen");
		JFormFiltro f = new JFormFiltro(winsSource);
		JFormFiltro f2 = winsSource.getClass().equals(options.getClass()) ? null : new JFormFiltro(options);
		winsSource.ConfigurarFiltros(f);
		JWins wins;
		if (useCache) {
			wins = (JWins) zContent.getObject(getProviderName() + "_" + JTools.replace(fieldSource + JTools.getValidFilename(f.swapInfo()), ".", "_") + "_swap");
			if (wins == null) {
				zContent.addObject(getProviderName() + "_" + JTools.replace(fieldSource + JTools.getValidFilename(f.swapInfo()), ".", "_") + "_swap", winsSource);
				wins = winsSource;
			}
		} else {
			wins = winsSource;
			
		}
		String clase = wins.getWinRef().getClass().getName();
		JIterator<JWin> it = wins.getStaticIterator();
		while (it.hasMoreElements()) {
			JWin win = it.nextElement();
			String key = obtainKey(win);
			win.setDropListener(options.getDropListener());
			JPair<String, String> pair = buildPair(zContent, win);
			items.addElement(key, pair);
			if (keys.getElement(key) != null) {
				itemsSelecteds.addElement(key, win);
				continue;
			
			}
			zContent.startNode("items");
			zContent.setAttribute("id", pair.firstObject());
			zContent.setAttributeNLS("description", pair.secondObject());

			fillInfoFillters(zContent, f, null, formFiltros, win, false);
			this.rowToXMLActionBar(zContent, win, pair.firstObject());
			
			zContent.endNode("items");

		}
		zContent.endNode("origen");

		zContent.startNode("destino");
		JIterator<JWin> itC = options.getStaticIterator();
		while (itC.hasMoreElements()) {
			JWin aWin = itC.nextElement();
			JWin win = aWin;
			boolean forceInvisible = false;
			if (!win.getClass().equals(options.GetClassWin())) {
				JWin ghostWin = options.createWin();
				ghostWin.getRecord().getProp(getFieldKeyOptions()).setValueFormUI(win.getRecord().getProp(getFieldKeySource()).toString());
				ghostWin.getRecord().getProp(ghostWin.getSwapDescripField()).setValueFormUI(win.getDescripFieldValue());
				ghostWin.setDropListener(options.getDropListener());
				win = ghostWin;
				forceInvisible = true;
			}
			String key = win.getRecord().getPropDeep(fieldSource).toString();
			if (!win.getClass().getName().equals(clase) && itemsSelecteds.getElement(key) != null) {
				win = itemsSelecteds.getElement(key);
				if (itemsAdded.getElement(key)!=null) {
					JWin dupwin = win.getClass().newInstance();
					dupwin.setRecord(win.getRecord().createDefaultClone());
					win=dupwin;
					key+="_xxxclone"+win.hashCode();
				} else
				  itemsAdded.addElement(key, key);
			}
			win.getRecord().setAttach(aWin.getRecord());
		
			JPair<String, String> pair = items.getElement(key);
			if (pair == null) {
				pair = buildPair(zContent, win);
			}
			zContent.startNode("items");
			zContent.setAttribute("id", (String) pair.firstObject());
			zContent.setAttributeNLS("description", (String) pair.secondObject());

			fillInfoFillters(zContent, f, f2, formFiltros, win, forceInvisible);
			this.rowToXMLActionBar(zContent, win, pair.firstObject());

			zContent.endNode("items");

		}
		zContent.endNode("destino");
		if (hasActionBar())
			getActionBar().containerToXMLActions(zContent);

	}

	public String findFiltro2(JFormFiltro f2, String idControl, JWin win) throws Exception {
		JIterator<JFormControl> itf2 = f2.GetControles().getAllItems();
		while (itf2.hasMoreElements()) {
			JFormControl fc = itf2.nextElement();
			if (!fc.getIdControl().equals(idControl))
				continue;
			String field = fc.getFieldProp();
			String value = "";
			JObject obj = win.getRecord().getPropDeep(field, false);
			if (obj == null)
				break;
			if (obj.isRecord()) {
				JRecord rec = (JRecord) win.getRecord().getFixedPropDeep(field).getClase().newInstance();
				field = field + "." + rec.getKeyField();
			}
			if (win.getRecord().getPropDeep(field, false) != null)
				value = win.getRecord().getPropDeep(field, false).toString();

			return value;
		}
		return null;
	}

	protected void attachBackAction() throws Exception {
		if (!this.bBackBotton)
			return;
		if (sourceAction != null && !this.sourceAction.hasBackAction())
			return;
		if (this.isEmbedded())
			return;
		if (this.isPreview())
			return;
		if (!BizUsuario.retrieveSkinGenerator().attachBackActionToToolbar("swap"))
			return;
		this.oActionBar.addBackAction(winsObjectId, getWins().confirmBack(), null);
	}

	protected void attachActions(int orden) throws Exception {
		if (orden == this.getWins().getOrdenWinsToolsActions()) {
			// this.attachRefreshAction();
			this.attachBackAction();
		}
		if (orden == this.getWins().getOrdenWinsActions())
			this.attachWinsActions(this.getWins(), this.winsObjectDest, true);
		if (orden == this.getWins().getOrdenWinActions() && this.getWins().isShowWinToolBar())
			this.attachWinsActions(this.getWins().getWinRefWithActions(), null, true);

	}
	
	@Override
	public void attachWinsActions(JBaseWin zWin, String zBaseWinId, boolean append) throws Exception {
		this.oActionBar.addActionsFor(zWin, zBaseWinId, this.createWebActionParent(), append, false, false, isModal());
	}

	public JWebWinActionBar getActionBar() throws Exception {
		return this.oActionBar;
	}

	@Override
	protected void componentPreLayout(JXMLContent zContent) throws Exception {
		if (!hasWins())
			return;

		this.attachActionBar();
	}

	protected void addProviderHistory(JXMLContent zContent) throws Exception {
		// zContent.getGenerator().getSession().getHistoryManager().addHistoryProvider(this.sourceAction);
	}

	@Override
	protected void componentToXML(JXMLContent zContent) throws Exception {
		super.componentToXML(zContent);
		getActionBar().containerToXMLActions(zContent);

	}

	@Override
	public JWebAction getWebSourceAction() throws Exception {
		// TODO Auto-generated method stub
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

	@Override
	public void regiterObjects() throws Exception {
		if (this.bObjectRegistred)
			return;

		if (sourceAction != null && sourceAction.hasOwner()) {
			sourceObjectId = JWebActionFactory.registerObject(this.sourceAction.getObjOwner());
		}

		winsObjectDest = JWebActionFactory.registerObject(this.getWins());
		winsObjectId = JWebActionFactory.registerObject(this.winsSource);

		this.bObjectRegistred = true;
	}

}
