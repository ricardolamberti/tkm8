package pss.core.winUI.forms;

import java.awt.Dimension;

import javax.script.SimpleBindings;

//import java.awt.Component;

import pss.common.customForm.BizCustomForm;
import pss.common.customForm.BizCustomFormField;
import pss.common.security.BizUsuario;
import pss.core.data.interfaces.structure.RFilter;
import pss.core.graph.Graph;
import pss.core.reports.JBDReportes;
import pss.core.services.fields.JObjBDs;
import pss.core.services.fields.JObject;
import pss.core.services.fields.JScript;
import pss.core.services.records.JProperty;
import pss.core.services.records.JRecords;
import pss.core.tools.JExcepcion;
import pss.core.tools.JTools;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JList;
import pss.core.tools.collections.JMap;
import pss.core.ui.graphics.JImageIcon;
import pss.core.win.IControl;
import pss.core.win.JBaseWin;
import pss.core.win.JControlWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActChildForm;
import pss.core.win.submits.JActFieldWins;
import pss.core.win.submits.JActWins;
import pss.core.winUI.controls.IFormTable;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.controls.JControlTree;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.controls.JFormControles;
import pss.core.winUI.controls.JFormLista;
import pss.core.winUI.controls.JFormRow;
import pss.core.winUI.icons.GuiIconos;
import pss.core.winUI.lists.JPlantilla;
import pss.core.winUI.responsiveControls.JFormButtonResponsive;
import pss.core.winUI.responsiveControls.JFormCDatetimeResponsive;
import pss.core.winUI.responsiveControls.JFormCardResponsive;
import pss.core.winUI.responsiveControls.JFormCheckResponsive;
import pss.core.winUI.responsiveControls.JFormColorPickerResponsive;
import pss.core.winUI.responsiveControls.JFormColumnResponsive;
import pss.core.winUI.responsiveControls.JFormComboResponsive;
import pss.core.winUI.responsiveControls.JFormControlResponsive;
import pss.core.winUI.responsiveControls.JFormDivResponsive;
import pss.core.winUI.responsiveControls.JFormDropDownButtonResponsive;
import pss.core.winUI.responsiveControls.JFormDropDownComboResponsive;
import pss.core.winUI.responsiveControls.JFormDropDownWinLovResponsive;
import pss.core.winUI.responsiveControls.JFormEditResponsive;
import pss.core.winUI.responsiveControls.JFormFieldsetExpandedResponsive;
import pss.core.winUI.responsiveControls.JFormFieldsetResponsive;
import pss.core.winUI.responsiveControls.JFormFileResponsive;
import pss.core.winUI.responsiveControls.JFormHtmlTextAreaResponsive;
import pss.core.winUI.responsiveControls.JFormImageCardResponsive;
import pss.core.winUI.responsiveControls.JFormImageResponsive;
import pss.core.winUI.responsiveControls.JFormInfoCardResponsive;
import pss.core.winUI.responsiveControls.JFormIntervalCDatetimeResponsive;
import pss.core.winUI.responsiveControls.JFormLabelDataResponsive;
import pss.core.winUI.responsiveControls.JFormLabelResponsive;
import pss.core.winUI.responsiveControls.JFormListBoxResponsive;
import pss.core.winUI.responsiveControls.JFormListExpandResponsive;
import pss.core.winUI.responsiveControls.JFormMapResponsive;
import pss.core.winUI.responsiveControls.JFormMatrixResponsive;
import pss.core.winUI.responsiveControls.JFormMultipleCheckResponsive;
import pss.core.winUI.responsiveControls.JFormMultipleResponsive;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;
import pss.core.winUI.responsiveControls.JFormPasswordResponsive;
import pss.core.winUI.responsiveControls.JFormRadioResponsive;
import pss.core.winUI.responsiveControls.JFormRowGridExpandResponsive;
import pss.core.winUI.responsiveControls.JFormRowGridResponsive;
import pss.core.winUI.responsiveControls.JFormTabPanelResponsive;
import pss.core.winUI.responsiveControls.JFormTableResponsive;
import pss.core.winUI.responsiveControls.JFormTextAreaResponsive;
import pss.core.winUI.responsiveControls.JFormTreeComponentResponsive;
import pss.core.winUI.responsiveControls.JFormWinLOVResponsive;
import pss.www.platform.actions.JWebActionFactory;
import pss.www.ui.JWebIcon;

/**
 * Clase base para la construcción de formularios de la interfaz. Define modos
 * de operación, controles y utilidades comunes que son reutilizados por los
 * formularios específicos de cada ventana.
 */
public class JBaseForm  {

	public final static String MODO_CONSULTA = "C";

	public final static String MODO_ALTA = "A";

	public final static String MODO_MODIFICACION = "M";

	public final static String MODO_CONSULTA_ACTIVA = "T";

	public final static String MODO_ELIMINAR = "E";

	public final static String MODO_ENTERQUERY = "Y";

	public final static String MODO_EXECUTEQUERY = "Q";

	public final static int FORM_STATUS_APPLIED_OK = 1;

	public final static int FORM_STATUS_APPLIED_FAILED = 2;

	public final static int FORM_STATUS_CANCELLED = 0;

	public final static int FORM_STATUS_DISCARDED = -1;

	public final static int EXIT_AUTOM = 0;

	public final static int EXIT_YES = 1;

	public final static int EXIT_NO = 2;

	public final static String REQ = "REQ";

	public final static String OPT = "OPT";

	public final static String CHAR = "CHAR";

	public final static String DATE = "DATE";

	public final static String DATETIME = "DATETIME";

	public final static String HOUR = "HOUR";

	public final static String COLOUR = "COLOUR";

	public final static String IMAGE = "IMAGE";

	public final static String UINT = "UINT";

	public final static String UFLOAT = "UFLOAT";

	public final static String ULONG = "ULONG";

	public final static String INT = "INT";

	public final static String FLOAT = "FLOAT";

	public final static String LONG = "LONG";

	public final static String CHECK = "S";

	public final static String UNCHECK = "N";


	// -------------------------------------------------------------------------//
	// Propiedades de la Clase
	// -------------------------------------------------------------------------//
	private JFormControles oControles = null;

	// private JFormControles oConInit = null;

	private String sModo;

	// private boolean bModoConsulta;

	private boolean bIdle;

	// private String sFieldChanged=null;

	protected JWin oWin;

	// private JWin oWinInit;

	private JAct submitAction;
	private JAct ownerAction;

	private boolean bSwitcheable = true;

	private int exitOnApply = EXIT_AUTOM;

	private boolean bExitOnError = false;

	private boolean bVisibleMode = true;

	protected String sTituloOnOk = "Operación Exitosa";
	protected String sTextoConfir = "¿Está Ud. Seguro?";

	protected String sTituloConfir = "Confirmación";

	int status = 0;

	int curr_win = -1;

	private Dimension size;

	private boolean modal = false;

//	private JPssPanel oRootPanel;

	private boolean bEmbedded;
	private boolean bPartialRefreshFormAction;

	private String title = "?";
	private String titleRight = "?";
	private String help;

	private boolean reRead = true;

	private boolean modeInLine = false;

	private boolean autoRefresh = false;
	private int timerAutoRefresh;
	private String marcaAutoRefresh = null;
	private boolean detectChanges=true;

	public boolean isDetectChanges() {
		return detectChanges;
	}

	public void setDetectChanges(boolean detectChanges) {
		this.detectChanges = detectChanges;
	}
	public void setAutoRefresh(boolean autoRefresh, int timerAutoRefresh, String marcaAutoRefresh) {
		this.autoRefresh = autoRefresh;
		this.timerAutoRefresh = timerAutoRefresh;
		this.marcaAutoRefresh = marcaAutoRefresh;
	}

	public boolean isAutoRefresh() {
		return autoRefresh;
	}

	public int getTimerAutoRefresh() {
		return timerAutoRefresh;
	}

	public String getMarcaAutoRefresh() {
		return marcaAutoRefresh;
	}
	// private boolean backAction=true;

	public boolean isModeInLine() {
		return modeInLine;
	}
	
	


	public Dimension getSize() {
		return size;
	}

	public void setSize(Dimension size) {
		this.size = size;
	}

	public boolean isWithHeader() {
		return true;
	}
	public void setModeInLine(boolean modeInLine) {
		this.modeInLine = modeInLine;
	}

	private String idRow = "";

	public String getIdRow() {
		return idRow;
	}

	public void setIdRow(String idRow) {
		this.idRow = idRow;
	}

	public JFormControles getControles() throws Exception {
		if (oControles == null)
			oControles = new JFormControles(this, null);
		return oControles;
	}

	public JFormControles getControles(String field) throws Exception {
		if (oControles == null)
			oControles = new JFormControles(this, null);
		return oControles;
	}

	public void setControles(JFormControles zValue) {
		oControles = zValue;
	}

	public boolean isFullSize() throws Exception {
		return true;
	}

	public boolean hideTabArea() throws Exception {
		return false;
	}

	public boolean isSwitcheable() {
		return bSwitcheable;
	}

	public void setSwitcheable(boolean bSwitchable) {
		this.bSwitcheable = bSwitchable;
	}

	public boolean isPartialRefreshFormAction() {
		return bPartialRefreshFormAction;
	}

	public void setPartialRefreshFormAction(boolean bPartialRefreshFormAction) {
		this.bPartialRefreshFormAction = bPartialRefreshFormAction;
	}

	// public boolean hideToolbar() throws Exception {
	// return false;
	// }

	// public void setFieldChanged(String value) {
	// this.sFieldChanged=value;
	// }

	public void clearControl(String control) throws Exception {
		JFormControl c = this.findControl(control);
		if (c==null) return;
		c.clear();
	}

	public boolean hasControl(String value) throws Exception {
		return this.findControl(value)!=null;
	}

	public JFormControl findControl(String value) throws Exception {
		return this.getControles().findControl(value);
	}

	public JFormControlResponsive findControlResponsive(String value) throws Exception {
		return (JFormControlResponsive)this.getControles().findControl(value);
	}

	public JFormPanelResponsive findPanel(String value) throws Exception {
		return this.getControles().findControl(value).getControls().getParent();
	}

	public JFormCardResponsive findCard(String value) throws Exception {
		return this.getControles().findCard(value);
	}

	public String valueControl(String value) throws Exception {
		return this.findControl(value).getValue();
	}

	public String GetModo() {
		return sModo;
	}

	public void setOrganizeColumns(long columns) throws Exception {
		getInternalPanel().setOrganizeColumns(columns);
	}

	String defaultFormatField = JFormPanelResponsive.FIELD_LABEL_VERTICAL;

	public void setFormatFields(String foramtFields) throws Exception {
		defaultFormatField = foramtFields;
	}

	public String getFormatFields() {
		return defaultFormatField;
	}

	public void setLabelLeft() throws Exception {
		this.setFormatFields(JFormPanelResponsive.FIELD_LABEL_HORIZONTAL_LEFT);
	}

	public void setLabelRight() throws Exception {
		this.setFormatFields(JFormPanelResponsive.FIELD_LABEL_HORIZONTAL_RIGHT);
	}
	// public boolean ifModoConsulta() {
	// return bModoConsulta;
	// }

	@Deprecated
	// use getBaseWin()
	public JWin GetBaseWin() {
		return this.getBaseWin();
	}

	public JWin getBaseWin() {
		return oWin;
	}

	// public String GetExitError() {
	// return sExitError;
	// }
	// public String GetCurrentError() {
	// return sCurrentError;
	// }
	public boolean GetExitOnError() {
		return bExitOnError;
	}

	public String GetTextoConfirmacion() {
		return sTextoConfir;
	}

	public String GetTituloConfirmacion() {
		return sTituloConfir;
	}

	public boolean ifOK() {
		return status == FORM_STATUS_APPLIED_OK;
	}

	public boolean ifCancel() {
		return status == FORM_STATUS_CANCELLED || status == FORM_STATUS_DISCARDED;
	}

	public boolean ifDiscarded() {
		return status == FORM_STATUS_DISCARDED;
	}

	public JAct getSubmitAction() {
		return submitAction;
	}


	public void setVisibleMode(boolean zValue) {
		bVisibleMode = zValue;
	}

	public void SetModo(String zModo) {
		sModo = zModo;
	}

	public void SetWin(JWin zBase) {
		oWin = zBase;
	}

	public void setSubmitAction(JAct zValue) {
		submitAction = zValue;
	}

	public void setOwnerAction(JAct zValue) {
		ownerAction = zValue;
	}

	public JAct getOwnerAction() {
		return ownerAction;
	}

	public void SetExitOnOk(boolean value) {
		this.exitOnApply = value ? EXIT_YES : EXIT_NO;
	}

	public void SetTituloOnOk(String zValue) {
		sTituloOnOk = zValue;
	}

	public void SetExitOnError() {
		bExitOnError = true;
	}

	// public void SetExitError(String zValue) {
	// sExitError = zValue;
	// }
	// public void setExitButton(boolean zValue) {
	// bExitButton = zValue;
	// }
	//
	// public void setRefreshButton(boolean zValue) {
	// bRefreshButton = zValue;
	// }

	public void SetTituloConfirmacion(String zValue) {
		sTituloConfir = zValue;
	}

	public void SetTextoConfirmacion(String zValue) {
		sTextoConfir = zValue;
	}

	// -------------------------------------------------------------------------//
	// Constructor
	// -------------------------------------------------------------------------//
	public JBaseForm() {
	}

	// -------------------------------------------------------------------------//
	// Seteo el Win Base
	// -------------------------------------------------------------------------//
	public void InicializarPanel(JWin zBase) throws Exception {
		autoBuildForm(getInternalPanel(), zBase);
		if (isAlta())
			return;
		autoBuildTabs(getInternalPanel(), zBase);
	}

	public void InicializarPanelHeader(JWin zBase) throws Exception {
	}
 
	public void InicializarPanelFooter(JWin zBase) throws Exception {
	}

	public void autoBuildTabs(JFormPanelResponsive panel, JWin zBase) throws Exception {
		JIterator<BizAction> itac = zBase.getActionMap().getStaticIterator();
		while (itac.hasMoreElements()) {
			BizAction action = itac.nextElement();
			if (action.ifMenu())
				continue;
			if (action.ifToolBar())
				continue;
			JAct act = ((JWin) zBase).getSubmit(action);
			if (act instanceof JActFieldWins) {
				if (isConsulta())
					getInternalTabPanel().AddItemList(action.getIdAction()).setToolBar(JBaseWin.TOOLBAR_NONE);
				else
					getInternalTabPanel().AddItemList(action.getIdAction()).setToolBar(JBaseWin.TOOLBAR_TOP);
			} else if (act instanceof JActWins) {
				getInternalTabPanel().AddItemList(action.getIdAction());
			} else if (act instanceof JActChildForm)
				getInternalTabPanel().AddItemForm(action.getIdAction());

		}
	}

	public void autoBuildForm(JFormPanelResponsive panel, JWin zBase) throws Exception {

		JIterator<JProperty> it = zBase.getRecord().getFixedProperties().getValueIterator();
		while (it.hasMoreElements()) {
			JProperty prop = it.nextElement();
			JObject obj = ((JWin) zBase).getRecord().getProp(prop.GetCampo());
			// JProperty fixedprop = ((JWin)
			// zBase).getRecord().getFixedProp(prop.GetCampo());
			JFormControl c = null;
			if (prop.isAutonumerico()) {
				c = panel.AddItemEdit(prop.GetDescripcion(), LONG, OPT, prop.GetCampo()).setHide(true);
			} else if (prop.isKey()) {
				c = panel.AddItemEdit(prop.GetDescripcion(), CHAR, OPT, prop.GetCampo()).setHide(true);
			} else if (prop.isHide()) {
				c = panel.AddItemEdit(prop.GetDescripcion(), CHAR, OPT, prop.GetCampo()).setHide(true);
			} else if (prop.isRecords()) {
				continue;
			} else if (prop.isRecord()) {
				if (prop.hasControl(((JWin) zBase).getRecord())) {
					if (prop.getControl(((JWin) zBase).getRecord()) instanceof JControlCombo) {
						c = panel.AddItemCombo(prop.GetDescripcion(), CHAR, prop.isRequire() ? REQ : OPT, prop.GetCampo());
					} else if (prop.getControl(null) instanceof JControlTree) {
						c = panel.AddItemTree(prop.GetDescripcion(), CHAR, prop.isRequire() ? REQ : OPT, prop.GetCampo());
					} else {
						c = panel.AddItemWinLov(prop.GetDescripcion(), CHAR, prop.isRequire() ? REQ : OPT, prop.GetCampo());

					}
				}
				continue;
			} else if (prop.isVirtual()) {
				continue;
			} else if (obj.isDate()) {
				c = panel.AddItemDateTime(prop.GetDescripcion(), DATE, prop.isRequire() ? REQ : OPT, prop.GetCampo(),6);
			} else if (obj.isIntervalDate()) {
				c = panel.AddItemIntervalDateTime(prop.GetDescripcion(), DATE, prop.isRequire() ? REQ : OPT, prop.GetCampo()).setSizeColumns(6);
			} else if (obj.isIntervalDateTime()) {
				c = panel.AddItemIntervalDateTime(prop.GetDescripcion(), DATETIME, prop.isRequire() ? REQ : OPT, prop.GetCampo()).setSizeColumns(6);
			} else if (obj.isDateTime()) {
				c = panel.AddItemDateTime(prop.GetDescripcion(), DATETIME, prop.isRequire() ? REQ : OPT, prop.GetCampo()).setSizeColumns(6);
			} else if (obj.isHour()) {
				c = panel.AddItemDateTime(prop.GetDescripcion(), HOUR, prop.isRequire() ? REQ : OPT, prop.GetCampo()).setSizeColumns(3);
			} else {
				if (prop.hasControl(((JWin) zBase).getRecord())) {
					if (prop.getControl(((JWin) zBase).getRecord()) instanceof JControlCombo) {
						c = panel.AddItemCombo(prop.GetDescripcion(), CHAR, prop.isRequire() ? REQ : OPT, prop.GetCampo()).setSizeColumns(6);
					} else if (prop.getControl(((JWin) zBase).getRecord()) instanceof JControlTree) {
						c = panel.AddItemTree(prop.GetDescripcion(), CHAR, prop.isRequire() ? REQ : OPT, prop.GetCampo()).setSizeColumns(6);
					} else {
						JFormWinLOVResponsive wl = panel.AddItemWinLov(prop.GetDescripcion(), CHAR, prop.isRequire() ? REQ : OPT, prop.GetCampo());
						if (obj.isMultiple())
							wl.setMultiple(true);
						wl.setSizeColumns(6);

						c = wl;
					}
				} else if (obj.isFloat())
					c = panel.AddItemEdit(prop.GetDescripcion(), FLOAT, prop.isRequire() ? REQ : OPT, prop.GetCampo()).setSizeColumns(2);
				else if (obj.isLong())
					c = panel.AddItemEdit(prop.GetDescripcion(), LONG, prop.isRequire() ? REQ : OPT, prop.GetCampo()).setSizeColumns(2);
				else if (obj.isBoolean())
					c = panel.AddItemCheck(prop.GetDescripcion(), CHAR, prop.GetCampo());
				else if (obj.isCurrency())
					c = panel.AddItemEdit(prop.GetDescripcion(), FLOAT, prop.isRequire() ? REQ : OPT, prop.GetCampo()).setSizeColumns(2);
				else if (obj.isGeoPosition())
					c = panel.AddItemMap(prop.GetDescripcion(), CHAR, prop.isRequire() ? REQ : OPT, prop.GetCampo());
				else if (obj.isInteger())
					c = panel.AddItemEdit(prop.GetDescripcion(), INT, prop.isRequire() ? REQ : OPT, prop.GetCampo()).setSizeColumns(2);
				else if (obj.isPassword())
					c = panel.AddItemPassword(prop.GetDescripcion(), CHAR, prop.isRequire() ? REQ : OPT, prop.GetCampo());
				else
					c = panel.AddItemEdit(prop.GetDescripcion(), CHAR, prop.isRequire() ? REQ : OPT, prop.GetCampo());
			}

			if (c != null) {
				if (prop.isHide())
					c.setVisible(false);
				if (prop.isOnlyDisableUpdate()) {
					if (this.isAlta())
						c.setVisible(false);
					c.SetReadOnly(true);
				}
			}
		}

	}

	public void InicializarFixedPanel(JWin zBase) throws Exception {
	}

	public void InternalInicializarPanel(JWin zBase) throws Exception {
		if (this.isMakeHeader()) {
			this.InicializarPanelHeader(zBase);
			return;
		}
		if (this.isMakeFooter()) {
			this.InicializarPanelFooter(zBase);
			return;
		}
		this.InicializarPanel(zBase);
		InicializarFixedPanel(zBase);
//		if (forceConvertToResponsive())
//			convertToResponsive();
	}

//	public boolean forceConvertToResponsive() throws Exception {
//		if (BizUsuario.getUsr() == null)
//			return false;
//		if (BizUsuario.getUsr().getObjBusiness() == null)
//			return false;
//		return BizUsuario.getUsr().getObjBusiness().forceConvertToResponsive();
//	}


//	public synchronized void convertToResponsive() throws Exception {
//		getControles().setLabelsLoaded(true);
//		TreeMap<String, JFormControl> newCtrls = new TreeMap<String, JFormControl>();
//		JIterator<JFormControl> oControlsIt = getControles().GetItems();
//		int orden = 0;
//		while (oControlsIt.hasMoreElements()) {
//			JFormControl oFormControl = (JFormControl) oControlsIt.nextElement();
//			if (oFormControl instanceof JFormPanelResponsive)
//				((JFormPanelResponsive) oFormControl).convertToResponsive();
//			if (oFormControl.isResponsive())
//				continue;
//			if (!componentIsChild(oFormControl.getComponent()))
//				continue;
//			JFormControlResponsive newComp = oFormControl.getResponsiveVersion();
//			if (newComp == null) {
//				PssLogger.logError("No pude convertir: " + oFormControl.getClass().getCanonicalName());
//				continue;
//			}
//			oControlsIt.remove();
//			newCtrls.put(makeKey(oFormControl, orden++), newComp);
//		}
//		for (JFormControl ctrl : newCtrls.values()) {
//			getControles().AddControl(ctrl);
//		}
//	}

	// private boolean isWebApplication() throws Exception {
	// return JAplicacion.GetApp().ifAppFrontEndWeb();
	// }

	public boolean isOpenMaximized() {
		return false;
	}

	public void initializeBuild() {
		sTituloOnOk = "Operación Exitosa";
		sTextoConfir = "¿Está Ud. Seguro?";
		sTituloConfir = "Confirmación";
	}

	// -------------------------------------------------------------------------//
	// Seteo el Win Base
	// -------------------------------------------------------------------------//
	public JBaseForm build() throws Exception {

		initializeBuild();

		if (this.isAlta())
			this.oWin.getRecord().forceFilterToData();

		if (this.hasToRead()) {
			this.ReRead();
		}
		oWin.clearActions();
		this.InternalInicializarPanel(oWin);
		this.internalBuild();
		this.verifyCustomForms();
		// zBase.clearActions(); // HGK
		this.assignScriptContext();

		this.setTitle(oWin.GetTitle());
		this.setTitleRight(oWin.getTitleRight());
		return this;
	}

	private void assignScriptContext() throws Exception {
		JIterator<JFormControl> iter = this.getControles().GetItems();
		while (iter.hasMoreElements()) {
			JFormControl c = iter.nextElement();
			if (!c.hasFixedProp())
				continue;
			JObject<?> prop = oWin.getRecord().getProp(c.getIdControl(), false);
			if (prop == null)
				continue;
			if (!prop.hasScript())
				continue;
			// JScript script = prop.getObjScript();
			JScript script = prop.getObjScript();
			if (script == null)
				continue;
			this.assingContextToScript(script);
			// c.SetReadOnly(!script.isCalculeOthersFields());
		}
	}

	private void internalBuild() throws Exception {
		JIterator<JFormControl> iter = this.getControles().GetItems();
		while (iter.hasMoreElements()) {
			JFormControl c = iter.nextElement();
			c.internalBuild();
		}
	}

	private void assingContextToScript(JScript script) throws Exception {
		SimpleBindings bind = new SimpleBindings();
		JMap<String, String> originalBind = script.getBind();
		JIterator<String> i = originalBind.getKeyIterator();
		while (i.hasMoreElements()) {
			String key = i.nextElement();
			String value = originalBind.getElement(key);
			JFormControl oControl = getControles().findControl(value);
			if (oControl != null) {
				bind.put(key, oControl);
			}
		}
		// script.setContext(bind);
	}

	public boolean hasToRead() throws Exception {
		return (this.isConsulta()  || this.isModificar() || isConsultaActiva()) && reRead;
	}

	/**
	 * @deprecated usar #setTitle()
	 */
	@Deprecated
	public void SetTitulo(String zTitulo) {
		this.setTitle(zTitulo);
	}

	public void setTitle(String zTitle) {
		this.title = zTitle;
		// this.updateRootTitle();
	}

	public String getTitle() {
		return this.title;
	}
	public void setTitleRight(String zTitle) {
		this.titleRight = zTitle;
		// this.updateRootTitle();
	}

	public String getTitleRight() {
		return this.titleRight;
	}

	public String getHelp() {
		if (help == null)
			return getBaseWin().getHelp();
		return help;
	}

	public void setHelp(String help) {
		this.help = help;
	}

	public void cleanUp() throws Exception {
		// JRefresh.removeListener(this);
//		if (oWin != null)
//			oWin.RemoveFormActivo(this);
		getControles().RemoveAll();
		// oFormEventListener.Do(new ActionEvent(this, 3, "Close"));
		// this.oBarForm = null;
		this.oControles = null;
		// this.oBarWin = null;
		// this.oFormEventListener = null;
		this.oWin = null;
		// this.oWins = null;
		OnClose();
	}

	public boolean wasClean() throws Exception {
		return this.oWin == null;
	}

	// -------------------------------------------------------------------------//
	// OnPostDisplayAlta
	// -------------------------------------------------------------------------//
	// public void OnPostMostrarModificar() throws Exception {
	// }

	public void OnPostRefresh() throws Exception {
	}

	public void OnClose() throws Exception {
	}

	public void notifyActionOwner() throws Exception {
		if (this.ownerAction == null)
			return;
		this.ownerAction.notifyAction(this);
	}

	public void checkControls(String sControlId) throws Exception {
	}


	public JFormHtmlTextAreaResponsive AddItemHtml(String zLabel, String zTipoDato, String zRequer, String sCampo) throws Exception {
		return AddItemHtml(zLabel, zTipoDato, zRequer, sCampo, false);
	}

	public JFormHtmlTextAreaResponsive AddItemHtml(String zLabel, String zTipoDato, String zRequer, String sCampo, boolean isWeb) throws Exception {
		return AddItemHtml(zLabel, zTipoDato, zRequer, sCampo, isWeb, null, null, 650, 40, 0, 0, 0, null, true);
	}

	public JFormHtmlTextAreaResponsive AddItemHtml(String zLabel, String zTipoDato, String zRequer, String sCampo, boolean isWeb, long anchoPagina, long margenIzq) throws Exception {
		return AddItemHtml(zLabel, zTipoDato, zRequer, sCampo, isWeb, null, null, anchoPagina, margenIzq, 0, 0, 0, null, true);
	}

	public JFormHtmlTextAreaResponsive AddItemHtml(String zLabel, String zTipoDato, String zRequer, String sCampo, boolean isWeb, long anchoPagina, long margenIzq, long margenImgSup, long margenImgLeft, long margenImgSize, String fondo) throws Exception {
		return AddItemHtml(zLabel, zTipoDato, zRequer, sCampo, isWeb, null, null, anchoPagina, margenIzq, margenImgSup, margenImgLeft, margenImgSize, fondo, true);
	}

	public JFormHtmlTextAreaResponsive AddItemHtml(String zLabel, String zTipoDato, String zRequer, String sCampo, boolean isWeb, JPlantilla p, String mapaSource, long anchoPagina, long margenIzq, long margenImgSup, long margenImgLeft, long margenImgSize, String fondo, boolean formulario) throws Exception {

		return getInternalPanel().AddItemHtml(zLabel, zTipoDato, zRequer, sCampo, isWeb, anchoPagina, margenIzq, margenImgSup, margenImgLeft, margenImgSize, fondo);
	}





	public JFormControl AddItem(JFormControl control) throws Exception {

		getInternalPanel().getControles().AddControl(control);
		return control;
	}

	JFormTabPanelResponsive tabPanel;
	JFormPanelResponsive panel;

	public JFormTabPanelResponsive getInternalTabPanel() throws Exception {
		if (tabPanel == null)
			tabPanel = getInternalPanel().AddItemTabPanel();
		return tabPanel;
	}

	public JFormPanelResponsive getInternalPanel() throws Exception {
		if (panel == null)
			panel = this.AddDivPanel();
		panel.setId("internal-panel");
		// panel.setResponsiveClass("well-white");
//		int h = getHeight();
//		JTabbedPane tab = findTabPanel();
//		if (tab != null)
//			h -= tab.getHeight();
//		if (h != 0)
//			panel.setHeight(h);
//
//		if (!forceConvertToResponsive())
//			panel.setZoomtofit(getWidth());
		return panel;
	}


//	public JFormLista AddItemListJson(JTabbedPane zTabPanel, int zId) throws Exception {
//		JFormLista l = getInternalTabPanel(zTabPanel).AddItemListJSon(zId);
//		return l;
//	}


	public String buildTabName(String id) throws Exception {
		String s = "local_" + JTools.getValidFilename(id);
		if (JWebActionFactory.getCurrentRequest().getLevel() > 1)
			s += "__l" + JWebActionFactory.getCurrentRequest().getLevel();
		return s;
	}

	public String buildTabName(BizAction zAction) throws Exception {
		String s = "";

		if (getOwnerAction() != null && this.getOwnerAction().getActionSource().getRow() != null)
			s += this.getOwnerAction().getActionSource().getRow() + "_";
		s += zAction.getProviderName();
		// s+= "__l"+JWebActionFactory.getCurrentRequest().getLevel();
		return s;
	}

	public String getProviderName() throws Exception {
		if (getOwnerAction() == null)
			return null;
		if (getOwnerAction().getActionSource() == null)
			return null;
		return "form_" + this.getOwnerAction().getActionSource().getProviderName();
	}



	public IControl createControlTree(final BizAction action) throws Exception {
		return new JControlTree() {
			public JWins getRecords(boolean one) throws Exception {
				return action.getSubmit().getWinsResult();
			}
		};
	}

	public IControl createControlWin(final BizAction action) throws Exception {
		return new JControlWin() {
			public JWins getRecords() throws Exception {
				return action.getSubmit().getWinsResult();
			}
		};
	}

	public IControl createControlWin(final String campo, final Class clase) throws Exception {
		return new JControlWin() {
			public JWins getRecords() throws Exception {
				JWins w = (JWins) Class.forName(clase.getName()).newInstance();
				w.setRecords(((JObjBDs) getBaseWin().getRecord().getProp(campo)).getValue());
				w.setParent(getBaseWin());
				return w;
			}
		};
	}

	// -------------------------------------------------------------------------//
	// Proceso un operacion
	// -------------------------------------------------------------------------//
	public void Do(String zModo) throws Exception {
		sModo = zModo;
		bIdle = false;
		if (isConsulta())
			Detalle();
		else if (isModificar()) // Es necesario agregar un modo refresh? para que
														// abm mas complejos vayan al raw cuando se hace un
														// RefreshForm
			Modificar();
		else if (isConsultaActiva()) // Es necesario agregar un modo refresh? para
																	// que abm mas complejos vayan al raw cuando
																	// se hace un RefreshForm
			ConsultaActiva();
		else if (isAlta())
			Alta();
		// else if (isEnterQuery())
		// EnterQuery();
		bIdle = true;
	}

	public boolean isIdle() {
		return bIdle;
	}

	protected void setIdle(boolean zIdle) {
		bIdle = zIdle;
	}

	// -------------------------------------------------------------------------//
	// Refresh
	// -------------------------------------------------------------------------//
	public void Refresh(boolean userRequest) throws Exception {
		// if (this.isEmbedded()) {
		// this.refreshWhenVisible(userRequest);
		// } else {
		this.RefreshNow(userRequest);
		// }
	}

	public void RefreshNow(boolean userRequest) throws Exception {
		bIdle = false;
		// if (reRead) // ya se relee en el buid
		// this.ReRead();
		OnShow();
		OnRefresh();
		getControles().BaseToControl(GetModo(), userRequest);
		getControles().disableAll();
		// this.RestaurarBarra();
		this.checkControls();
		this.notifyActionOwner();
		OnPostRefresh();
		OnPostShow();
		bIdle = true;
	}

	public void ReRead() throws Exception {
		ReRead(oWin);
		OnPostRead();
	}

	protected void ReRead(JWin oWin) throws Exception {
		if (oWin == null)
			return;
		if (oWin.getRecord().isStatic() || !hasTableAsoc()) {
			oWin.getRecord().refreshStaticData();
			return;
		}
		oWin.getRecord().dontThrowException(true);
		if (hasTableAsoc()) {
			oWin.getRecord().clearFilters();
			oWin.getRecord().keysToFilters();
			oWin.getRecord().read();
		}
	}
	
	private boolean hasTableAsoc() throws Exception {
		return oWin.getRecord().getStructure().getTable() != null && !oWin.getRecord().getStructure().getTable().equals("");
	}

	public JFormTreeComponentResponsive AddItemTree(String label, String zTipoDato, String zRequer, String sField, int icono, JControlTree sCampo) throws Exception {
		return getInternalPanel().AddItemTree(label, zTipoDato, zRequer, sField, icono, sCampo);
	}

	public JFormTreeComponentResponsive AddItemTree(String label, String zTipoDato, String zRequer, String sField) throws Exception {
		return getInternalPanel().AddItemTree(label, zTipoDato, zRequer, sField);
	}

	public JFormTreeComponentResponsive AddItemTree(String label, String zTipoDato, String zRequer, String sField, int icono) throws Exception {
		return getInternalPanel().AddItemTree(label, zTipoDato, zRequer, sField, icono);
	}

	public JFormCDatetimeResponsive AddItemDateTime(String label, String zTipoDato, String zRequer, String zCampo, int size) throws Exception {
		JFormCDatetimeResponsive c = this.AddItemDateTime(label, zTipoDato, zRequer, zCampo);
		c.setSizeColumns(size);
		return c;
	}

	public JFormCDatetimeResponsive AddItemDateTime(String label, String zTipoDato, String zRequer, String zCampo) throws Exception {
		return getInternalPanel().AddItemDateTime(label, zTipoDato, zRequer, zCampo);
	}

	public JFormIntervalCDatetimeResponsive AddItemIntervalDateTime(String label, String zTipoDato, String zRequer, String zCampo) throws Exception {
		return getInternalPanel().AddItemIntervalDateTime(label, zTipoDato, zRequer, zCampo);
	}

	public JFormButtonResponsive AddItemButton(String label, int zActionSource, boolean submit) throws Exception {
		return getInternalPanel().AddItemButton(label, zActionSource, submit);
	}

	public JFormButtonResponsive AddItemButtonBack(String label) throws Exception {
		return getInternalPanel().AddItemButtonBack(label);
	}

	public JFormDropDownButtonResponsive AddItemDropDownButton(String button) throws Exception {
		return getInternalPanel().AddItemDropDownButton(null, button, null);
	}

	public JFormDropDownButtonResponsive AddItemDropDownButton(String button, JWebIcon icon) throws Exception {
		return getInternalPanel().AddItemDropDownButton(null, button, icon);
	}

	public JFormDropDownButtonResponsive AddItemDropDownButton(String label, String button, JWebIcon icon) throws Exception {
		return getInternalPanel().AddItemDropDownButton(label, button, icon);
	}

	public JFormCheckResponsive AddItemCheck(String label, String zRequer, String sCampo, long size) throws Exception {
		JFormCheckResponsive c = this.AddItemCheck(label, zRequer, sCampo);
		c.setSizeColumns(size);
		return c;
	}

	public JFormCheckResponsive AddItemCheck(String label, String zTipoDato, String zRequer, String sCampo, String zValorCheck, String zValorUnCheck) throws Exception {
		return getInternalPanel().AddItemCheck(label, zTipoDato, zRequer, sCampo, zValorCheck, zValorUnCheck);
	}

	public JFormCheckResponsive AddItemCheck(String label, String zRequer, String sCampo) throws Exception {
		return getInternalPanel().AddItemCheck(label, CHAR, zRequer, sCampo, "S", "N");
	}

	public JFormRadioResponsive AddItemThreeCheck(String label, String zTipoDato, String zRequer, String sCampo) throws Exception {
		return getInternalPanel().AddItemThreeCheck(label, zTipoDato, zRequer, sCampo);
	}

	public JFormRadioResponsive AddItemThreeCheck(String label, String zTipoDato, String zRequer, String sCampo, String trueValue, String nullValue, String falseValue, String trueDescr, String nullDescr, String falseDescr) throws Exception {
		return getInternalPanel().AddItemThreeCheck(label, zTipoDato, zRequer, sCampo, trueValue, nullValue, falseValue, trueDescr, nullDescr, falseDescr);
	}

	public JFormColorPickerResponsive AddItemColor(String zLabel, String zTipoDato, String zRequer) throws Exception {
		return getInternalPanel().AddItemColor(zLabel, zTipoDato, zRequer, null);
	}

	public JFormColorPickerResponsive AddItemColor(String zLabel, String zTipoDato, String zRequer, String sCampo) throws Exception {
		return getInternalPanel().AddItemColor(zLabel, zTipoDato, zRequer, sCampo);
	}

	public JFormComboResponsive AddItemCombo(String zLabel, String zTipoDato, String zRequer, String sCampo, JWins zWins) throws Exception {
		return getInternalPanel().AddItemCombo(zLabel, zTipoDato, zRequer, sCampo, zWins);
	}

	public JFormComboResponsive AddItemCombo(String zLabel, String zTipoDato, String zRequer, String sCampo, IControl zCC, long size) throws Exception {
		JFormComboResponsive c = this.getInternalPanel().AddItemCombo(zLabel, zTipoDato, zRequer, sCampo, zCC);
		c.setSizeColumns(size);
		return c;
	}

	public JFormComboResponsive AddItemCombo(String zLabel, String zTipoDato, String zRequer, String sCampo, IControl zCC) throws Exception {
		return getInternalPanel().AddItemCombo(zLabel, zTipoDato, zRequer, sCampo, zCC);
	}

	public JFormComboResponsive AddItemCombo(String zLabel, String zTipoDato, String zRequer, String sCampo) throws Exception {
		return getInternalPanel().AddItemCombo(zLabel, zTipoDato, zRequer, sCampo);
	}

	public JFormDropDownComboResponsive AddItemDropDownCombo(String zLabel, String zTipoDato, String zRequer, String sCampo, JWins zWins) throws Exception {
		return getInternalPanel().AddItemDropDownCombo(zLabel, zTipoDato, zRequer, sCampo, zWins);
	}

	public JFormDropDownComboResponsive AddItemDropDownCombo(String zLabel, String zTipoDato, String zRequer, String sCampo, IControl zCC, long size) throws Exception {
		JFormDropDownComboResponsive c = this.getInternalPanel().AddItemDropDownCombo(zLabel, zTipoDato, zRequer, sCampo, zCC);
		c.setSizeColumns(size);
		return c;
	}

	public JFormDropDownComboResponsive AddItemDropDownCombo(String zLabel, String zTipoDato, String zRequer, String sCampo, IControl zCC) throws Exception {
		return getInternalPanel().AddItemDropDownCombo(zLabel, zTipoDato, zRequer, sCampo, zCC);
	}

	public JFormDropDownComboResponsive AddItemDropDownCombo(String zLabel, String zTipoDato, String zRequer, String sCampo) throws Exception {
		return getInternalPanel().addItemDropDownCombo(zLabel, zTipoDato, zRequer, sCampo);
	}

	
	public JFormEditResponsive AddItemEdit(String zLabel, String zTipoDato, String zRequer) throws Exception {
		return getInternalPanel().AddItemEdit(zLabel, zTipoDato, zRequer, null);
	}

	public JFormEditResponsive AddItemEdit(String zLabel, String zTipoDato, String zRequer, String sCampo, int size) throws Exception {
		JFormEditResponsive c = getInternalPanel().AddItemEdit(zLabel, zTipoDato, zRequer, sCampo);
		c.setSizeColumns(size);
		return c;
	}
	public JFormEditResponsive AddItemEdit(String zEdit, String zTipoDato, String zRequer, String sCampo,  JScript script) throws Exception {
		JFormEditResponsive e = getInternalPanel().AddItemEdit(zEdit, zTipoDato, zRequer, sCampo, script);
		return e;
	}

	public JFormEditResponsive AddItemEdit(String zLabel, String zTipoDato, String zRequer, String sCampo) throws Exception {
		return getInternalPanel().AddItemEdit(zLabel, zTipoDato, zRequer, sCampo);
	}

	public JFormPasswordResponsive AddItemPassword(String zLabel, String zTipoDato, String zRequer, String sCampo) throws Exception {
		return getInternalPanel().AddItemPassword(zLabel, zTipoDato, zRequer, sCampo);
	}

	public JFormLabelDataResponsive AddItemLabelData(String label, String zTipoDato, String sCampo) throws Exception {
		return getInternalPanel().AddItemLabelData(label, zTipoDato, sCampo);
	}

	public JFormLabelDataResponsive AddItemLabelData(String zTipoDato, String sCampo) throws Exception {
		return getInternalPanel().AddItemLabelData(zTipoDato, sCampo);
	}

	public JFormFileResponsive AddItemFile(String label, String zTipoDato, String zRequer, String sCampo) throws Exception {
		return getInternalPanel().AddItemFile(label, zTipoDato, zRequer, sCampo);
	}

	public JFormImageResponsive AddItemImage(String zLabel, int action, Graph graph) throws Exception {
		return getInternalPanel().AddItemImage(zLabel, action, -1, graph, "", false, JImageIcon.PAINT_SCALED);
	}

	public JFormImageResponsive AddItemImage(String zLabel, int action, int graph) throws Exception {
		return getInternalPanel().AddItemImage(zLabel, action, -1, graph, "", false, JImageIcon.PAINT_SCALED);
	}

	public JFormImageResponsive AddItemImage(String zLabel, int actionSource, int actionLink, int graph) throws Exception {
		return getInternalPanel().AddItemImage(zLabel, actionSource, actionLink, graph, "", false, JImageIcon.PAINT_SCALED);
	}

	public JFormImageResponsive AddItemImage(String zLabel, String sCampo) throws Exception {
		return getInternalPanel().AddItemImage(zLabel, -1, -1, 0, sCampo, false, JImageIcon.PAINT_SCALED);
	}

	public JFormImageResponsive AddItemImage(String zLabel, String sCampo, int zActionLink) throws Exception {
		return getInternalPanel().AddItemImage(zLabel, -1, zActionLink, 0, sCampo, false, JImageIcon.PAINT_SCALED);
	}

	public JFormImageResponsive AddItemImage(String zLabel, String sCampo, boolean expand, int type) throws Exception {
		return getInternalPanel().AddItemImage(zLabel, -1, -1, 0, sCampo, false, JImageIcon.PAINT_SCALED);
	}

	public JFormImageResponsive AddItemImage(String zLabel, int zActionSource, int zActionLink, Graph graph, String sCampo, boolean expand, int type) throws Exception {
		return getInternalPanel().AddItemImage(zLabel, zActionSource, zActionLink, graph, sCampo, expand, type);
	}

	public JFormImageResponsive AddItemImage(String zLabel, int zActionSource, int zActionLink, int graph, String sCampo, boolean expand, int type) throws Exception {
		return getInternalPanel().AddItemImage(zLabel, zActionSource, zActionLink, graph, sCampo, expand, type);
	}

	public JFormLabelResponsive AddItemLabel(String zLabel) throws Exception {
		return getInternalPanel().AddItemLabel(zLabel);
	}

	public JFormListBoxResponsive AddItemListBox(String zLabel, String zTipoDato, String zRequerido, String zCampo, JControlCombo zWins) throws Exception {
		return getInternalPanel().AddItemListBox(zLabel, zTipoDato, zRequerido, zCampo, zWins);
	}

	public JFormMultipleCheckResponsive AddItemMultipleCheck(String zLabel, String zTipoDato, String zRequerido, String zCampo, JControlCombo zWins) throws Exception {
		return getInternalPanel().AddItemMultipleCheck(zLabel, zTipoDato, zRequerido, zCampo, zWins);
	}

	public JFormMultipleCheckResponsive AddItemMultipleCheck(String zLabel, String zRequerido, String zCampo, JWins zWins) throws Exception {
		return getInternalPanel().AddItemMultipleCheck(zLabel, zRequerido, zCampo, zWins);
	}

	public JFormMultipleResponsive AddItemMultiple(String zLabel, String zTipoDato, String zRequerido, String zCampo, JControlCombo zWins) throws Exception {
		return getInternalPanel().AddItemMultiple(zLabel, zTipoDato, zRequerido, zCampo, zWins);
	}

	public JFormMultipleResponsive AddItemMultiple(String zLabel, String zRequerido, String zCampo, JWins zWins) throws Exception {
		return getInternalPanel().AddItemMultiple(zLabel, zRequerido, zCampo, zWins);
	}

	public JFormMapResponsive AddItemMap(String zMap, String zTipoDato, String zRequer, String sCampo) throws Exception {
		return getInternalPanel().AddItemMap(zMap, zTipoDato, zRequer, sCampo);
	}

	public JFormMapResponsive AddItemMap(String zMap, String field, int action, String modo) throws Exception {
		return getInternalPanel().AddItemMap(zMap, field, action, modo);
	}

	public JFormMapResponsive AddItemMap(String zMap, int action, String modo) throws Exception {
		return getInternalPanel().AddItemMap(zMap, action, modo);
	}

	public JFormMapResponsive AddItemMap(String zMap, String zTipoDato, String zRequer, String sField, JWins sCampo, String modo) throws Exception {
		return getInternalPanel().AddItemMap(zMap, zTipoDato, zRequer, sField, sCampo, modo);
	}

	public JFormTextAreaResponsive AddItem(String zArea, String zTipoDato, String zRequer, String sCampo) throws Exception {
		JFormTextAreaResponsive oArea;

		oArea = new JFormTextAreaResponsive();
		oArea.setLabel(zArea);
		oArea.setProp(oWin.getRecord().getPropDeep(sCampo));
		oArea.setFixedProp(oWin.getRecord().getFixedPropDeep(sCampo));
		oArea.SetRequerido(zRequer);
		oArea.SetTipoDato(zTipoDato);

		getInternalPanel().getControles().AddControl(oArea);
		return oArea;
	}

	public JFormTextAreaResponsive AddItemArea(String zArea, String zTipoDato, String zRequer, String sCampo) throws Exception {
		return getInternalPanel().AddItemArea(zArea, zTipoDato, zRequer, sCampo, false);
	}

	public JFormTextAreaResponsive AddItemArea(String zArea, String zTipoDato, String zRequer, String sCampo, boolean isWeb) throws Exception {
		return getInternalPanel().AddItemArea(zArea, zTipoDato, zRequer, sCampo, isWeb, null, null, 650, 40, 0, 0, 0, null, true);
	}

	public JFormTextAreaResponsive AddItemArea(String zArea, String zTipoDato, String zRequer, String sCampo, boolean isWeb, long anchoPagina, long margenIzq) throws Exception {
		return getInternalPanel().AddItemArea(zArea, zTipoDato, zRequer, sCampo, isWeb, null, null, anchoPagina, margenIzq, 0, 0, 0, null, true);
	}

	public JFormTextAreaResponsive AddItemArea(String zArea, String zTipoDato, String zRequer, String sCampo, boolean isWeb, long anchoPagina, long margenIzq, long margenImgSup, long margenImgLeft, long margenImgSize, String fondo) throws Exception {
		return getInternalPanel().AddItemArea(zArea, zTipoDato, zRequer, sCampo, isWeb, null, null, anchoPagina, margenIzq, margenImgSup, margenImgLeft, margenImgSize, fondo, true);
	}

	public JFormTextAreaResponsive AddItemArea(String zArea, String zTipoDato, String zRequer, String sCampo, boolean isWeb, JPlantilla p, String mapaSource, long anchoPagina, long margenIzq, long margenImgSup, long margenImgLeft, long margenImgSize, String fondo, boolean formulario) throws Exception {
		return getInternalPanel().AddItemArea(zArea, zTipoDato, zRequer, sCampo, isWeb, p, mapaSource, anchoPagina, margenIzq, margenImgSup, margenImgLeft, margenImgSize, fondo, formulario);
	}

	// -------------------------------------------------------------------------//
	// Agrego un Item RadioGroup
	// -------------------------------------------------------------------------//
	public JFormRadioResponsive AddItemRadio(String zGroup, String zTipoDato, String zRequer, String sCampo, JMap<String, String> options) throws Exception {
		return getInternalPanel().AddItemRadio(zGroup, zTipoDato, zRequer, sCampo, options);
	}

	public JFormWinLOVResponsive AddItemWinLov(String zLabel, String zTipoDato, String zRequer, String sCampo, JWins zWins, int action) throws Exception {
		return getInternalPanel().AddItemWinLov(zLabel, zTipoDato, zRequer, sCampo, zWins.createControlWin(), false, false, action);
	}

	public JFormWinLOVResponsive AddItemWinLov(String zLabel, String zTipoDato, String zRequer, String sCampo, IControl zControlWins, int action) throws Exception {
		return getInternalPanel().AddItemWinLov(zLabel, zTipoDato, zRequer, sCampo, zControlWins, false, false, action);
	}

	public JFormWinLOVResponsive AddItemWinLov(String zLabel, String zTipoDato, String zRequer, String sCampo, JWins zWins) throws Exception {
		return getInternalPanel().AddItemWinLov(zLabel, zTipoDato, zRequer, sCampo, zWins.createControlWin());
	}

	public JFormWinLOVResponsive AddItemWinLov(String zLabel, String zTipoDato, String zRequer, String sCampo, IControl zControlWins) throws Exception {
		return getInternalPanel().AddItemWinLov(zLabel, zTipoDato, zRequer, sCampo, zControlWins, false, false, -1);
	}

	public JFormWinLOVResponsive AddItemWinLov(String zLabel, String zTipoDato, String zRequer, String sCampo, int action) throws Exception {
		return getInternalPanel().AddItemWinLov(zLabel, zTipoDato, zRequer, sCampo, null, false, false, action);
	}

	public JFormWinLOVResponsive AddItemWinLov(String zLabel, String zTipoDato, String zRequer, String sCampo, boolean zIsMultiple, boolean zShowsInMultipleLines, int action) throws Exception {
		return getInternalPanel().AddItemWinLov(zLabel, zTipoDato, zRequer, sCampo, null, zIsMultiple, zShowsInMultipleLines, action);
	}

	public JFormWinLOVResponsive AddItemWinLov(String zLabel, String zTipoDato, String zRequer, String sCampo) throws Exception {
		return getInternalPanel().AddItemWinLov(zLabel, zTipoDato, zRequer, sCampo, null, false, false, -1);
	}

	public JFormWinLOVResponsive AddItemWinLov(String zLabel, String zTipoDato, String zRequer, String sCampo, IControl zControlWins, boolean zIsMultiple, boolean zShowsInMultipleLines, int action) throws Exception {
		return getInternalPanel().AddItemWinLov(zLabel, zTipoDato, zRequer, sCampo, zControlWins, zIsMultiple, zShowsInMultipleLines, action);
	}

	public JFormDropDownWinLovResponsive AddItemDropDownWinLov(String zLabel, String zTipoDato, String zRequer, String sCampo, JWins zWins, int action) throws Exception {
		return getInternalPanel().addItemDropDownWinLov(zLabel, zTipoDato, zRequer, sCampo, zWins.createControlWin(), false, false, action);
	}

	public JFormDropDownWinLovResponsive AddItemDropDownWinLov(String zLabel, String zTipoDato, String zRequer, String sCampo, IControl zControlWins, int action) throws Exception {
		return getInternalPanel().addItemDropDownWinLov(zLabel, zTipoDato, zRequer, sCampo, zControlWins, false, false, action);
	}

	public JFormDropDownWinLovResponsive AddItemDropDownWinLov(String zLabel, String zTipoDato, String zRequer, String sCampo, JWins zWins) throws Exception {
		return getInternalPanel().addItemDropDownWinLov(zLabel, zTipoDato, zRequer, sCampo, zWins.createControlWin());
	}

	public JFormDropDownWinLovResponsive AddItemDropDownWinLov(String zLabel, String zTipoDato, String zRequer, String sCampo, IControl zControlWins) throws Exception {
		return getInternalPanel().addItemDropDownWinLov(zLabel, zTipoDato, zRequer, sCampo, zControlWins, false, false, -1);
	}

	public JFormDropDownWinLovResponsive AddItemDropDownWinLov(String zLabel, String zTipoDato, String zRequer, String sCampo, int action) throws Exception {
		return getInternalPanel().addItemDropDownWinLov(zLabel, zTipoDato, zRequer, sCampo, null, false, false, action);
	}

	public JFormDropDownWinLovResponsive AddItemDropDownWinLov(String zLabel, String zTipoDato, String zRequer, String sCampo, boolean zIsMultiple, boolean zShowsInMultipleLines, int action) throws Exception {
		return getInternalPanel().addItemDropDownWinLov(zLabel, zTipoDato, zRequer, sCampo, null, zIsMultiple, zShowsInMultipleLines, action);
	}

	public JFormDropDownWinLovResponsive AddItemDropDownWinLov(String zLabel, String zTipoDato, String zRequer, String sCampo) throws Exception {
		return getInternalPanel().addItemDropDownWinLov(zLabel, zTipoDato, zRequer, sCampo, null, false, false, -1);
	}

	public JFormDropDownWinLovResponsive AddItemDropDownWinLov(String zLabel, String zTipoDato, String zRequer, String sCampo, IControl zControlWins, boolean zIsMultiple, boolean zShowsInMultipleLines, int action) throws Exception {
		return getInternalPanel().addItemDropDownWinLov(zLabel, zTipoDato, zRequer, sCampo, zControlWins, zIsMultiple, zShowsInMultipleLines, action);
	}

	public JFormTableResponsive AddItemTable(String zTable, String zCampo, Class jwins) throws Exception {
		return getInternalPanel().AddItemTable(zTable, zCampo, jwins, 2, -1, null);
	}

	public JFormTableResponsive AddItemTable(String zTable, String zCampo, Class jwins, JRecords valorDefault) throws Exception {
		return getInternalPanel().AddItemTable(zTable, zCampo, jwins, 2, -1, valorDefault);
	}

	public JFormTableResponsive AddItemTable(String zTable, String zCampo, Class jwins, int updateaction) throws Exception {
		return getInternalPanel().AddItemTable(zTable, zCampo, jwins, updateaction, -1);
	}

	public JFormTableResponsive AddItemTable(String zTable, String zCampo, Class jwins, int updateaction, JRecords valorDefault) throws Exception {
		return getInternalPanel().AddItemTable(zTable, zCampo, jwins, updateaction, -1);
	}

	public JFormTableResponsive AddItemTable(String zTable, String zCampo, Class jwins, int updateaction, int newaction) throws Exception {
		return getInternalPanel().AddItemTable(zTable, zCampo, jwins, updateaction, newaction, null);
	}

	public JFormTableResponsive AddItemTable(String zTable, String zCampo, Class jwins, int updateaction, int newaction, JRecords valorDefault) throws Exception {
		return getInternalPanel().AddItemTable(zTable, zCampo, jwins, updateaction, newaction, valorDefault);
	}

	public JFormTabPanelResponsive AddItemTabPanel() throws Exception {
		JFormTabPanelResponsive oPanel = new JFormTabPanelResponsive();
		oPanel.setForm(this);
		oPanel.setWin(oWin);
		oPanel.setFormatFields(this.getFormatFields());
		getControles().AddControl(oPanel);
		return oPanel;
	}

	public JFormCardResponsive AddCardPanel(String title, String zId) throws Exception {
		BizAction oAction = getBaseWin().findActionByUniqueId(zId);
		return getInternalPanel().AddCardPanel(oAction, title);
	}

	public JFormCardResponsive AddCardPanel(String title, int zId) throws Exception {
		BizAction oAction = getBaseWin().findAction(zId);
		return getInternalPanel().AddCardPanel(oAction, title);
	}

	public JFormCardResponsive AddCardPanel(String zId) throws Exception {
		BizAction oAction = getBaseWin().findActionByUniqueId(zId);
		String tit = oAction != null ? this.getBaseWin().getDescrAction(oAction) : null;
		return getInternalPanel().AddCardPanel(oAction, tit);
	}

	public JFormCardResponsive AddCardPanel(int zId) throws Exception {
		BizAction oAction = getBaseWin().findAction(zId);
		String tit = oAction != null ? this.getBaseWin().getDescrAction(oAction) : null;
		return getInternalPanel().AddCardPanel(oAction, tit);
	}

	public JFormCardResponsive AddCardPanel(BizAction action, String title) throws Exception {
		return getInternalPanel().AddCardPanel(action, title);
	}

	public JFormInfoCardResponsive AddInfoCard(String zLabel, String zTipoDato, String sCampo, JWebIcon image, String labelLink, int zaction) throws Exception {
		BizAction action = getBaseWin().findAction(zaction);
		return getInternalPanel().AddInfoCard(zLabel, zTipoDato, sCampo, image, labelLink, action, null);
	}
	public JFormInfoCardResponsive AddInfoCard(String zLabel, String zTipoDato, String sCampo, JWebIcon image, String labelLink, int zaction, boolean submit) throws Exception {
		BizAction action = getBaseWin().findAction(zaction);
		return getInternalPanel().AddInfoCard(zLabel, zTipoDato, sCampo, image, labelLink, action, null,submit);
	}

	public JFormInfoCardResponsive AddInfoCard(String zLabel, String zTipoDato, String sCampo, int icon, String labelLink, String zaction) throws Exception {
		return this.AddInfoCard(zLabel, zTipoDato, sCampo, JWebIcon.getPssIcon(GuiIconos.RESPONSIVE, icon), labelLink, zaction);
	}

	public JFormInfoCardResponsive AddInfoCard(String zLabel, String zTipoDato, String sCampo, JWebIcon image, String labelLink, String zaction) throws Exception {
		BizAction action = getBaseWin().findActionByUniqueId(zaction);
		return getInternalPanel().AddInfoCard(zLabel, zTipoDato, sCampo, image, labelLink, action, null);
	}

	public JFormInfoCardResponsive AddInfoCard(String zLabel, String zTipoDato, String sCampo, JWebIcon image, String labelLink, BizAction action) throws Exception {
		return getInternalPanel().AddInfoCard(zLabel, zTipoDato, sCampo, image, labelLink, action, null);
	}

	public JFormImageCardResponsive AddImageCard(String zLabel, JWebIcon image, String labelLink, int zaction) throws Exception {
		BizAction action = getBaseWin().findAction(zaction);
		return getInternalPanel().AddImageCard(zLabel, image, labelLink, action);
	}

	public JFormImageCardResponsive AddImageCard(String zLabel, JWebIcon image, String labelLink, String zaction) throws Exception {
		BizAction action = getBaseWin().findActionByUniqueId(zaction);
		return getInternalPanel().AddImageCard(zLabel, image, labelLink, action);
	}

	public JFormImageCardResponsive AddImageCard(String zLabel, JWebIcon image, String labelLink, BizAction action) throws Exception {
		return getInternalPanel().AddImageCard(zLabel, image, labelLink, action);
	}

	public JFormImageCardResponsive AddImageCard(String zLabel, String labelLink, int zaction, int image) throws Exception {
		BizAction action = getBaseWin().findAction(zaction);
		return getInternalPanel().AddImageCard(zLabel, labelLink, action, image);
	}

	public JFormImageCardResponsive AddImageCard(String zLabel, String labelLink, String zaction, int image) throws Exception {
		BizAction action = getBaseWin().findActionByUniqueId(zaction);
		return getInternalPanel().AddImageCard(zLabel, labelLink, action, image);
	}

	public JFormImageCardResponsive AddImageCard(String zLabel, String labelLink, BizAction action, int image) throws Exception {
		return getInternalPanel().AddImageCard(zLabel, labelLink, action, image);
	}

	public JFormPanelResponsive AddDivPanel() throws Exception {
		JFormDivResponsive oPanel = new JFormDivResponsive();
		oPanel.setForm(this);
		oPanel.setWin(oWin);
		oPanel.setFormatFields(this.getFormatFields());
		getControles().AddControl(oPanel);
		return oPanel;
	}

	public JFormPanelResponsive AddItemRow(int size) throws Exception {
		return (JFormPanelResponsive) getInternalPanel().AddItemRow().setSizeColumns(size);
	}

	public JFormPanelResponsive AddItemRow() throws Exception {
		return getInternalPanel().AddItemRow();
	}

	public JFormPanelResponsive AddItemPanel(String zTitle) throws Exception {
		return getInternalPanel().AddItemPanel(zTitle);
	}

	public JFormFieldsetResponsive AddItemFieldset(String zTitle, long size) throws Exception {
		JFormFieldsetResponsive p = getInternalPanel().AddItemFieldset(zTitle);
		p.setSizeColumns(size);
		return p;
	}

	public JFormFieldsetResponsive AddItemFieldset(String zTitle) throws Exception {
		return getInternalPanel().AddItemFieldset(zTitle);
	}
	public JFormFieldsetExpandedResponsive AddItemFieldsetExpanded(String zTitle) throws Exception {
		return getInternalPanel().AddItemFieldsetExpanded(zTitle);
	}
	public JFormColumnResponsive AddItemColumn(long size) throws Exception {
		JFormColumnResponsive col = this.getInternalPanel().AddItemColumn();
		col.setSizeColumns(size);
		return col;
		// return this.getInternalPanel().AddItemColumn("col-sm-"+size + "
		// pss-field");
	}
	
	JList<JBaseForm> subforms;
	public boolean hasSubForms() {
		return this.subforms!=null;
	}
	public JList<JBaseForm> getSubForms() {
		return this.subforms;
	}
	public void AddSubFormFlat(JFormPanelResponsive panel, JWin win, int act) throws Exception {
		if (subforms==null) this.subforms=JCollectionFactory.createList();
  	JBaseForm subform = win.findAction(act).getObjSubmit().getFormFlat();
		this.subforms.addElement(subform);
		panel.AddSubForm(win, subform);
	}

	public JFormColumnResponsive AddItemColumn(String classResponsive) throws Exception {
		return getInternalPanel().AddItemColumn(classResponsive);
	}

	public JFormColumnResponsive AddItemColumn() throws Exception {
		return getInternalPanel().AddItemColumn(null);
	}

	public JFormPanelResponsive addPanelHalf() throws Exception {
		return this.getInternalPanel().addPanelHalf(this.isPreview());
	}

	public JFormTabPanelResponsive addTabHalf() throws Exception {
		return this.getInternalPanel().addTabHalf(this.isPreview());
	}

	public JFormLista AddItemTab(int zId) throws Exception {
		return getInternalPanel().AddItemTab(zId, null);
	}

	// Additem de un JWins a un JTabbedPane a través de una acción
	public JFormLista AddItemTab(int zId, String title) throws Exception {
		BizAction oAction = getBaseWin().findAction(zId);
		if (title == null && oAction != null)
			title = this.getBaseWin().getDescrAction(oAction);
		return getInternalPanel().AddItemList(oAction, title);
	}

	public JFormMatrixResponsive AddItemMatrix(int zId) throws Exception {
		return getInternalPanel().AddItemMatrix(this.getBaseWin().findAction(zId), null);
	}

	public JFormMatrixResponsive AddItemMatrix(int zId, String titulo) throws Exception {
		return getInternalPanel().AddItemMatrix(this.getBaseWin().findAction(zId), titulo);
	}

	public JFormMatrixResponsive AddItemMatrix(String zId) throws Exception {
		return getInternalPanel().AddItemMatrix(this.getBaseWin().findActionByUniqueId(zId));
	}

	public JFormMatrixResponsive AddItemMatrix(BizAction action) throws Exception {
		String title = this.getBaseWin().getDescrAction(action);
		return getInternalPanel().AddItemMatrix(action, title);
	}

	public JFormListExpandResponsive AddItemListExpand(int zId) throws Exception {
		BizAction oAction = getBaseWin().findAction(zId);
		String tit = oAction != null ? this.getBaseWin().getDescrAction(oAction) : null;
		return getInternalPanel().AddItemListExpand(oAction, tit);
	}

	public JFormLista AddItemListJSon(int zId) throws Exception {
		BizAction oAction = getBaseWin().findAction(zId);
		String tit = oAction != null ? this.getBaseWin().getDescrAction(oAction) : null;
		return getInternalPanel().AddItemListJSon(oAction, tit);
	}

	public JFormLista AddItemListJSon(String zId) throws Exception {
		BizAction oAction = getBaseWin().findActionByUniqueId(zId);
		String tit = oAction != null ? this.getBaseWin().getDescrAction(oAction) : null;
		return getInternalPanel().AddItemListJSon(oAction, tit);
	}

	public JFormLista AddItemListJSon(BizAction zAction, String title) throws Exception {
		return getInternalPanel().AddItemListJSon(zAction, title);
	}

	public JFormListExpandResponsive AddItemListExpand(BizAction zAction, String title) throws Exception {
		return getInternalPanel().AddItemListExpand(zAction, title);
	}

	public JFormLista AddItemList(int zId) throws Exception {
		BizAction oAction = getBaseWin().findAction(zId);
		String tit = oAction != null ? this.getBaseWin().getDescrAction(oAction) : null;
		return getInternalPanel().AddItemList(oAction, tit);
	}

	public JFormLista AddItemList(String title, String zCampo, Class jwins) throws Exception {
		return getInternalPanel().AddItemList(title, zCampo, jwins, -1, -1, null);
	}

	public JFormLista AddItemList(String zId) throws Exception {
		BizAction oAction = getBaseWin().findActionByUniqueId(zId);
		String tit = oAction != null ? this.getBaseWin().getDescrAction(oAction) : null;
		return getInternalPanel().AddItemList(oAction, tit);
	}

	public JFormLista AddItemList(BizAction zAction, String title) throws Exception {
		return getInternalPanel().AddItemList(zAction, title);
	}

	public JFormMatrixResponsive AddItemMatrix(BizAction zAction, String title) throws Exception {
		return getInternalPanel().AddItemMatrix(zAction, title);
	}

	// -------------------------------------------------------------------------//
	// Detalle
	// -------------------------------------------------------------------------//
	public void Detalle() throws Exception {
		// this.checkReferencedObjectsExist();
		sModo = MODO_CONSULTA;
		// bModoConsulta = true;

		Refresh(true);
		getControles().disableAll();

	}

	// private void checkReferencedObjectsExist() throws Exception {
	// JRecord oBD;
	// if (this.oWin == null || (oBD = this.oWin.getRecord()) == null ||
	// oBD.getProperties() == null) {
	// return;
	// }
	//
	// String sPropName = null;
	// JObject oProp = null;
	// try {
	// // JIterator oPropNamesIt = oBD.GetPropiedades().getKeyIterator();
	// JIterator oPropNamesIt = GetControles().GetItems();
	// while (oPropNamesIt.hasMoreElements()) {
	// JFormControl oControl = (JFormControl) oPropNamesIt.nextElement();
	// oProp = oControl.getProp();
	// if (oProp != null && !oControl.getFixedProp().isVirtual() &&
	// !oControl.getFixedProp().isRecords()) {
	// oProp.asObject();
	// }
	// }
	// } catch (JNoRowFoundException e) {
	// String sPropDescription = oBD.getFixedProp(sPropName).GetDescripcion();
	// JExcepcion.SendError("El campo '" + sPropDescription + "' referencia a un
	// registro que no existe en la tabla " + e.getTableName());
	// }
	// }
	public void ConsultaActiva() throws Exception {
			OnShow();
		OnModificar();

		this.refreshDataActiveMode();
		getControles().BaseToControl(GetModo(), true);

		getControles().EditarCamposIngresables(JFormControl.MODO_MODIFICACION, false);
		getControles().protectDataWithFilters(oWin.getRecord());

		notifyActionOwner();
		checkControls();
		refreshDataNewModePostCheckControls();

		this.OnPostShow();
		ConsultaActivaChilds();

	}

	public void ConsultaActivaChilds() throws Exception {
		JIterator<JFormControl> oIterator = getControles().getRecursiveItems();
		while (oIterator.hasMoreElements()) {
			JFormControl oCon = oIterator.nextElement();
			if (!(oCon instanceof JFormRow) && !(oCon instanceof JFormRowGridResponsive) && !(oCon instanceof JFormRowGridExpandResponsive) && !(oCon instanceof JFormPanelResponsive))
				continue;
			if (oCon instanceof JFormRow)
				((JFormRow) oCon).GetForm().ConsultaActiva();
			else if (oCon instanceof JFormRowGridResponsive)
				((JFormRowGridResponsive) oCon).GetForm().ConsultaActiva();
			else if (oCon instanceof JFormRowGridExpandResponsive)
				((JFormRowGridExpandResponsive) oCon).GetForm().ConsultaActiva();
			else if (oCon instanceof JFormPanelResponsive)
				((JFormPanelResponsive) oCon).getForm().ConsultaActiva();
		}
	}

	// -------------------------------------------------------------------------//
	// Modificar
	// -------------------------------------------------------------------------//
	public void Modificar() throws Exception {
		// this.checkReferencedObjectsExist();
		sModo = JFormControl.MODO_MODIFICACION;
		OnShow();
		OnModificar();

		getControles().BaseToControl(GetModo(), true);

		getControles().EditarCamposIngresables(JFormControl.MODO_MODIFICACION, false);
		// getControles().ProtegerDatosEnlazados(oWin.GetEnlazado());
		getControles().protectDataWithFilters(oWin.getRecord());

		// this.makeOkCancelToolbar();

		this.notifyActionOwner();
		this.checkControls();

		this.OnPostShow();
		ModificarChilds();

	}

	public void ModificarChilds() throws Exception {
		JIterator<JFormControl> oIterator = getControles().getRecursiveItems();
		while (oIterator.hasMoreElements()) {
			JFormControl oCon = oIterator.nextElement();
			if ((oCon instanceof JFormRow))
				((JFormRow) oCon).GetForm().Modificar();
			if ((oCon instanceof JFormRowGridResponsive))
				((JFormRowGridResponsive) oCon).GetForm().Modificar();
		}
	}

	public void Alta() throws Exception {
		sModo = JFormControl.MODO_ALTA;
		OnShow();
		OnAlta();

		this.refreshDataNewMode(); //
		// this.makeOkCancelToolbar();

		this.notifyActionOwner();
		this.checkControls();
		this.refreshDataNewModePostCheckControls();//por si setean defaults en el checkcontrols
		// OnPostAlta();
		OnPostShow();
		AltaChilds();
		getControles().ControlToBaseWithinCheck();// actualizo el jrecords para que
																							// los default sean conocidos para
																							// filtros de componentes basados
																							// en acciones!

	}

	public void checkControls() throws Exception {
	}

	public void AltaChilds() throws Exception {
		JIterator<JFormControl> oIterator = getControles().getRecursiveItems();
		while (oIterator.hasMoreElements()) {
			JFormControl oCon = oIterator.nextElement();
			if ((oCon instanceof JFormRow))
				((JFormRow) oCon).GetForm().Modificar();
			if ((oCon instanceof JFormRowGridResponsive))
				((JFormRowGridResponsive) oCon).GetForm().Modificar();
		}
	}

	public void refreshDataNewMode() throws Exception {
		this.disablePreasignedData();
	  if (!isPartialRefreshFormAction()) {	
	  	getControles().recordDataToDefault();
	  	getControles().asignDefaultData();
	  } else 
	  	getControles().BaseToControl(MODO_ALTA, true);
	  	
		
		getControles().EditarCamposIngresables(JFormControl.MODO_ALTA, isPartialRefreshFormAction());
	}

	protected void refreshDataNewModePostCheckControls() throws Exception {
		if (!isPartialRefreshFormAction()) getControles().asignDefaultData();
	}
	protected void refreshDataActiveMode() throws Exception {
		this.disablePreasignedData();//getBaseWin().getRecord()
		this.getControles().recordDataToDefault();
		if (!this.isPartialRefreshFormAction())	
		  	getControles().asignDefaultData();
		this.getControles().EditarCamposIngresables(JFormControl.MODO_MODIFICACION, isPartialRefreshFormAction());
	}

	private void disablePreasignedData() throws Exception {
		if (!this.oWin.getRecord().getStructure().hasFilters())
			return;
		JIterator iter = this.oWin.getRecord().getFilters().getIterator();
		while (iter.hasMoreElements()) {
			RFilter filter = (RFilter) iter.nextElement();
			JFormControl control = this.getControles().findControl(filter.getField());
			if (control == null)
				JExcepcion.SendError("Debe especificar el campo en el form: ^" + filter.getField());
			control.SetPreasignado(true);
			control.BaseToControl(MODO_CONSULTA, true);
			control.disable();
		}
	}

	public void disableAll() throws Exception {
		JIterator<JFormControl> iter = this.getControles().getAllItems();
		while (iter.hasMoreElements()) {
			JFormControl f = iter.nextElement();
			f.disable();
		}
	}

	public void ModoAplicar() throws Exception {
	}


	public void OnFormAplicarOk() throws Exception {
	}

	public void OnFormCancelar() throws Exception {
	}

	public void OnRefresh() throws Exception {
	}

	public void OnModificar() throws Exception {
	}

	public void OnAlta() throws Exception {
	}

	public void OnShow() throws Exception {
	}

	// public void OnPostAlta() throws Exception {
	// }
	public void OnPostShow() throws Exception {
	}

	protected void onCreate() throws Exception {
	}

	protected boolean isParentDependent() {
		return false;
	}



	// public void ShowModal() throws Exception {
	// this.setModal(true);
	// Show();
	// if (this.ifCancel())
	// throw new CanceledByUserException();
	// }
	public void generate() throws Exception {
		// bExitButton = false;
		onCreate();
		Do(sModo);
	}

	public boolean PreProcesar() throws Exception {
		return true;
	};

	public boolean PreAplicar() throws Exception {
		return true;
	};

	public void OnPostRead() throws Exception {
	};

	public boolean isConsulta() throws Exception {
		return sModo.equals(MODO_CONSULTA);
	}

	public boolean isModificar() throws Exception {
		return sModo.equals(MODO_MODIFICACION);
	}

	public boolean isConsultaActiva() throws Exception {
		return sModo.equals(MODO_CONSULTA_ACTIVA);
	}

	public boolean isAlta() throws Exception {
		return sModo.equals(MODO_ALTA);
	}

	public boolean isEnterQuery() throws Exception {
		return sModo.equals(MODO_ENTERQUERY);
	}


	public boolean isModal() {
		return modal;
	}

	public void setModal(boolean modal) {
		this.modal = modal;
	}

	// Responsable de mostrar/ocultar controles y/o establecer valores default
	// sobre la base de la composición actual de los controles del formulario.
	protected void reviewControls() throws Exception {
	}

	public void SetReportMode() throws Exception {
		if (this.oWin != null && this.oWin.getRecord() instanceof JBDReportes) {
			((JBDReportes) this.oWin.getRecord()).setReportTitle(this.oWin.GetTitle());
		}

		SetExitOnOk(false);
		// SetLimpiarOnOk(false);
	}

	public BizAction findActionBySubmit() throws Exception {
		return this.submitAction.getWinResult().findActionByUniqueId(this.submitAction.getActionUniqueId(),false,false);
	}

	public boolean hasToExitOnOk() throws Exception {
		if (this.exitOnApply == EXIT_AUTOM)
			return !this.hasListEmbedded();
		return (this.exitOnApply == EXIT_YES);
	}

	public boolean hasListEmbedded() throws Exception {
		JIterator<JFormControl> iter = this.getControles().getRecursiveItemsWithParents();
		while (iter.hasMoreElements()) {
			JFormControl control = iter.nextElement();
			if (control instanceof JFormLista)
				return true;
			if (control instanceof IFormTable)
				return true;
		}
		return false;
	}

//	public JFormLabel findLabel(JLabel label) throws Exception {
//		return (JFormLabel) this.getControles().findLabel(label);
//	}

//	public JFormControl findControl(Component comp) throws Exception {
//		return this.getControles().findControl("comp" + comp.hashCode());
//	}

//	public void addValueListener(PropertyChangeListener listener) {
//		this.changeNotifier.addPropertyChangeListener("value", listener);
//	}
//
//	public void removeValueListener(PropertyChangeListener listener) {
//		this.changeNotifier.removePropertyChangeListener("value", listener);
//	}

	public boolean isEmbedded() {
		return bEmbedded || (this.oWin != null && this.oWin.isEmbedded());
	}

	public void setEmbedded(boolean embedded) {
		bEmbedded = embedded;
	}

	public void setReRead(boolean reRead) {
		this.reRead = reRead;
	}

	public boolean hideChilds() throws Exception {
		return this.isAlta();
	}

	public boolean canApplyAction() throws Exception {
		return true;
	}

	public boolean canCancelAction() throws Exception {
		return true;
	}

	public String getNameApplyAction() throws Exception {
		return null;
	}

	public String getNameCancelAction() throws Exception {
		return null;
	}

	public boolean isFixWidth() throws Exception {
		return true;
	}

	public boolean isFixHight() throws Exception {
		return true;
	}

	private boolean bPreview = false;

	public void setPreview(boolean v) throws Exception {
		this.bPreview = v;
	}

	public boolean isPreview() {
		return this.bPreview;
	}

	public void verifyCustomForms() throws Exception {
		if (!BizUsuario.getUsr().getObjBusiness().useCustomForms())
			return;
		BizCustomForm form = BizCustomForm.findCustomForm(BizUsuario.getUsr().getCompany(), this.getClass().getName());
		if (form == null)
			return;
		JIterator<BizCustomFormField> iter = form.getObjCampos().getStaticIterator();
		while (iter.hasMoreElements()) {
			BizCustomFormField field = iter.nextElement();
			JFormControl c = this.findControl(field.getCampo());
			if (c==null) continue;
			c.SetRequerido(field.isRequerido() ? REQ : OPT);
			c.SetReadOnly(field.isReadOnly());
		}

	}

	public String getMessage(String zMsg) throws Exception {
		String msg = BizUsuario.retrieveSkinGenerator().getMessage(zMsg, null);
		return msg;
	}

	private BizAction sourceAction;

	public void setSourceAction(BizAction a) {
		this.sourceAction = a;
	}

	public BizAction getSourceAction() {
		return this.sourceAction;
	}

	public boolean hasSourceAction() {
		return this.sourceAction != null;
	}

	public static int HEADER = 1;
	public static int BODY = 2;
	public static int FOOTER = 3;

	private int make = BODY;

	public void setMake(int v) {
		this.make = v;
	}

	public boolean isMakeHeader() {
		return this.make == HEADER;
	}

	public boolean isMakeBody() {
		return this.make == BODY;
	}

	public boolean isMakeFooter() {
		return this.make == FOOTER;
	}
	protected JScript makeScriptExpand(int nroIcono) throws Exception {
		return this.makeScriptExpand("row_"+this.getIdRow(), "table-row", nroIcono);
	}
	protected JScript makeScriptExpand(String display) throws Exception {
		return this.makeScriptExpand("row_"+this.getIdRow(), display, 0);
	}
	protected JScript makeScriptExpand(String rowId, String display, int nroIcono) throws Exception {
		JScript sc = new JScript();
		sc.setScript("hide_show_subdetail('" + rowId + "', '"+display+"')");
		if (nroIcono!=0)
			sc.setScript(sc.getScript()+"; hide_show_icon(this, '"+JWebIcon.findIcon(nroIcono).getURL()+"')");
		sc.setPure(true);
		return sc;
	}

	String name;
	public void setName(String v) {
		this.name=v;
	}
	public String getName() {
		if (name==null) return this.getClass().getName();
		return this.name;
	}
}
