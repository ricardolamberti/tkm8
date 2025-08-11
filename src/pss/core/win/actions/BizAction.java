package pss.core.win.actions;

import java.io.Serializable;
import java.util.Date;

import pss.common.security.BizOperacion;
import pss.common.security.BizOperacionRol;
import pss.common.security.BizUsuario;
import pss.core.data.interfaces.structure.RFilter;
import pss.core.services.JExec;
import pss.core.services.fields.JBoolean;
import pss.core.services.fields.JInteger;
import pss.core.services.fields.JString;
import pss.core.services.records.JFilterMap;
import pss.core.services.records.JRecord;
import pss.core.tools.JDateTools;
import pss.core.tools.collections.JIterator;
import pss.core.win.JBaseWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActGroupSubmit;
import pss.core.win.submits.JActMultiSubmit;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.icons.GuiIconos;
import pss.www.platform.actions.JWebActionFactory;

/**
 * Represents a configurable action within a window.
 * <p>
 * Each {@code BizAction} defines the behavior executed from the UI, the
 * security restrictions that apply and the visual aspects used when rendering
 * the action. Instances are usually attached to {@link pss.core.win.JBaseWin}
 * components and can in turn own child actions forming a hierarchy.
 * </p>
 */
public class BizAction extends JRecord {

	private JBaseWin owner = null;
	public int pId = 0;
	public String sOwner = null;
	public JString pCompany = new JString();
	public JString pAccion = new JString();
	public JString pDescr = new JString();

	public String background;
	public String foreground;

	public JInteger pNroIcono = new JInteger();
	public String sTipoArbol = null;
	public String sTitle = null;
	public String sHelp = null;
	public String sIconFile = null;
	public String[] sIconFileSize = new String[36];
	public JBoolean pRestringido = new JBoolean() {
		public void preset() throws Exception {
			pRestringido.setValue(!isPermisosHabilitados());
		}
	};
	public int piKeyAction = Integer.MIN_VALUE;
	private boolean bDinamic = false;
	private boolean bMulti = false;
	private boolean bGroup = false;
	private boolean bSwap = false;
	private boolean bReportDetail = false;
	private boolean bConfirmMessage = false;
	private String sConfirmMessageDescription = null;
	private boolean bNuevaVentana = false;
	private boolean bBackOnPrint = false;
	private boolean bRefreshOnPrint = false;
	private boolean bModal = false;
	private boolean bBackNoModal = false;
	private boolean bInMobile = true;
	private boolean bOnlyInMobile = false;
	private boolean bImprimir = false;
	private boolean bNuevaSession = false;
	private boolean bRefreshFatherWindow = false; // refresca la ventana padre, en caso de nueva ventana
	private boolean bCloseFatherWindow = false; // refresca la ventana padre, en caso de nueva ventana

	private boolean bAccessToDetail = false;
	private boolean bBackAction = true;
	private boolean bRefreshAction = true;
	private boolean bFilterAction = true;
	private boolean bFirstAccess = true;
	private boolean bExport = false;
	private boolean bBreak = false;
	private boolean bInAlta = false;
	private String specialSelector = null;
	private Serializable data; // datos libre para enviar dentro de un submit

	public long lImportance = 0;
	private boolean bOkAction = true;
	private boolean bOkSecurity = true;

	public boolean isOkAll() throws Exception {
		return this.isOkAction() && this.isOkSecurity();
	}

	public void setOkAction(boolean v) {
		this.bOkAction = v;
	}

	public boolean isOkAction() {
		return this.bOkAction;
	}

	public void setOkSecurity(boolean v) {
		this.bOkSecurity = v;
	}

	public boolean isOkSecurity() {
		return this.bOkSecurity;
	}

	public long getImportance() {
		return lImportance;
	}

	public boolean isBackNoModal() {
		return bBackNoModal;
	}

	public BizAction setBackNoModal(boolean bBackNoModal) {
		this.bBackNoModal = bBackNoModal;
		return this;
	}

	public boolean isOnlyMobile() {
		return bOnlyInMobile;
	}

	public BizAction setOnlyMobile(boolean bOnlyMobile) {
		this.bOnlyInMobile = bOnlyMobile;
		return this;
	}

	public boolean isInMobile() {
		return bInMobile;
	}

	public BizAction setInMobile(boolean bInMobile) {
		this.bInMobile = bInMobile;
		return this;
	}

	public BizAction setImportance(long lImportance) {
		this.lImportance = lImportance;
		return this;
	}

	public Serializable getData() {
		return data;
	}

	public void setData(Serializable data) {
		this.data = data;
	}

	public boolean isBackOnPrint() {
		return bBackOnPrint;
	}

	public BizAction setBackOnPrint(boolean bBackInPrint) {
		this.bBackOnPrint = bBackInPrint;
		return this;
	}

	public boolean isRefreshOnPrint() {
		return bRefreshOnPrint;
	}

	public BizAction setRefreshOnPrint(boolean refreshOnPrint) {
		this.bRefreshOnPrint = refreshOnPrint;
		return this;
	}

	private transient JFilterMap filterMap = null; // filtros dinamicos que vienen en el request

	private transient BizAction ownerFilterMap = null;
	private transient BizActions subActions = null;

	public byte kNotInForm = 0;
	public byte kOnlyForm = 0;
	public byte kToolBar = 0;
	public byte kToolBarMore = -1;
	public byte kMenu = 0;
	public byte kPanel = 0;
	public byte kWeb = 0;
	protected boolean bCancelable = false;
	protected boolean uploaddata = false;
	protected boolean isSubmitedByUser = false;

	// Esto sólo se utiliza en casos muy puntuales y se inicializa on demand
	// private JList oFiltros = null;
	// public void addFiltro( RFilter zFiltro ) {
	// if( oFiltros == null ) oFiltros = JCollectionFactory.createList();
	// oFiltros.addElement( zFiltro );
	// }
	// /
	public boolean isSubmitedByUser() {
		return isSubmitedByUser;
	}

	public void setSubmitedByUser(boolean isSubmitedByUser) {
		this.isSubmitedByUser = isSubmitedByUser;
	}

	public boolean isUploadData() {
		return uploaddata;
	}

	public BizAction setUploadData(boolean uploaddata) {
		this.uploaddata = uploaddata;
		return this;
	}

	public void setObjFilterMap(BizAction f) {
		filterMap = f.getFilterMap();
		ownerFilterMap = f;
		bFirstAccess = f.isFirstAccess();
	}

	public BizAction getOwnerFilterMap() {
		return ownerFilterMap;
	}

	public BizAction setNuevaVentana(boolean b) {
		bNuevaVentana = b;
		return this;
	}

	public boolean isNuevaVentana() {
		return bNuevaVentana;
	}

	public BizAction setNuevaSession(boolean b) {
		bNuevaSession = b;
		return this;
	}

	public boolean isNuevaSession() {
		return bNuevaSession;
	}

	public BizAction setModal(boolean b) {
		bModal = b;
		return this;
	}

	public boolean isModal() {
		return bModal;
	}

	public boolean isRefreshFatherWindow() {
		return bRefreshFatherWindow;
	}

	public boolean isCloseFatherWindow() {
		return bCloseFatherWindow;
	}

	public BizAction setImprimir(boolean b) {
		bImprimir = b;
		return this;
	}

	public boolean isImprimir() {
		return bImprimir;
	}

	public boolean isCancelable() {
		return bCancelable;
	}

	public BizAction setCancelable(boolean bCancelable) {
		this.bCancelable = bCancelable;
		return this;
	}

	public String getSpecialSelector() {
		return specialSelector;
	}

	public BizAction setSpecialSelector(String specialSelector) {
		this.specialSelector = specialSelector;
		return this;
	}

	public String getHelp() throws Exception {
		return sHelp;
	}

	public BizAction setHelp(String zHelp) {
		this.sHelp = zHelp;
		return this;
	}

	public BizAction setCloseFatherWindow(boolean bCloseFatherWindow) {
		this.bCloseFatherWindow = bCloseFatherWindow;
		return this;
	}

	public BizAction setRefreshFatherWindow(boolean bRefreshFatherWindow) {
		this.bRefreshFatherWindow = bRefreshFatherWindow;
		return this;
	}

	public BizAction setDescrip(String value) {
		pDescr.setValue(value);
		return this;
	}

	String postFunction = null;

	public String getPostFunction() {
		return postFunction;
	}

	public BizAction setPostFunction(String s) {
		this.postFunction = s;
		return this;
	}

	public boolean hasPostFunction() {
		return this.postFunction != null;
	}

	public void setCompany(String value) {
		pCompany.setValue(value);
	}

	public void setBackAction(boolean b) {
		bBackAction = b;
	}

	public BizAction setRefreshAction(boolean b) {
		bRefreshAction = b;
		return this;
	}

	public BizAction setFilterAction(boolean b) {
		bFilterAction = b;
		return this;
	}

	public final static int C_CTRL_MASK = 1000;
	public final static int C_SHIFT_MASK = 2000;
	public final static int C_ALT_MASK = 3000;
	public final static int C_ALTGR_MASK = 4000;

	public final static int QUERY = 1;
	public final static int UPDATE = 2;
	public final static int REMOVE = 3;
	public final static int DROP = 4;
	public final static int REPORT = 5;

	private JAct submit = null;
	private transient JAct oActionAutorizada = null;

	// private JAct oActionListener = null;

	public String GetDescr() throws Exception {
		return pDescr.getValue();
	};

	// public JAct GetActionListener() throws Exception { return oActionListener;
	// };
	public int GetNroIcono() throws Exception {
		return this.pNroIcono.getValue();
	}

	public void SetNroIcono(int zNroIcono) throws Exception {
		this.pNroIcono.setValue(zNroIcono);
	}

	public JBaseWin getResult() throws Exception {
		JAct submit = this.getObjSubmit();

		if (submit == null)
			return null;
		return submit.getResult();
	}

	public void setObjSubmit(JAct value) throws Exception {
		this.submit = value;
		this.submit.setActionSource(this);
	}

	public JAct refreshSubmit() throws Exception {
		this.clearSubmit();
		return this.getObjSubmit();
	}

	public JAct getObjSubmit() throws Exception {
		if (submit != null)
			return submit;
		submit = this.getSubmit();
		return submit;
	}

	public JAct getSubmit() throws Exception {
		JAct submit = null;
		if (this.hasToCreateMultiAction())
			submit = this.createMultiAction(this);
		else if (this.hasToCreateGroupAction())
			submit = this.createGroupAction(this);
		else
			submit = owner.getSubmit(this);

		if (submit != null) {
			submit.setActionSource(this);
			submit.setData(getData());
		}
		return submit;
	};

	public void clearSubmit() throws Exception {
		if (sOwner == null)
			return;
		submit = null;
	}

	public boolean hasSubmit() throws Exception {
		return this.submit != null;
	}

	public void SetAction(JAct zAct) throws Exception {
		submit = zAct;
	}

	public BizActions GetSubAcciones() {
		return this.subActions;
	}

	public int getId() {
		return pId;
	}

	public int GetKeyAction() {
		return piKeyAction;
	}

	public String GetOwner() {
		return sOwner;
	}

	public BizAction setInMenu(boolean zValue) {
		kMenu = (byte) (zValue ? 1 : 0);
		return this;
	}

	public BizAction setInToolbarMore(boolean zValue) {
		kToolBarMore = (byte) (zValue ? 1 : 0);
		return this;
	}

	public BizAction setInToolbar(boolean zValue) {
		kToolBar = (byte) (zValue ? 1 : 0);
		return this;
	}

	public BizAction setOnlyInForm(boolean zValue) {
		kOnlyForm = (byte) (zValue ? 1 : 0);
		return this;
	}

	public BizAction setNotInForm(boolean zValue) {
		kNotInForm = (byte) (zValue ? 1 : 0);
		return this;
	}

	public BizAction setPanel(boolean zValue) {
		kPanel = (byte) (zValue ? 1 : 0);
		return this;
	}

	public void setInWeb(boolean zValue) {
		kWeb = (byte) (zValue ? 1 : 0);
	}

	public BizAction setMulti(boolean zValue) {
		bMulti = zValue;
		return this;
	}

	public BizAction setGroup(boolean zValue) {
		bGroup = zValue;
		bMulti = zValue;
		return this;
	}

	public BizAction setSwap(boolean zValue) {
		bGroup = zValue;
		bMulti = zValue;
		bSwap = zValue;
		return this;
	}

	public void changeOwner(String zValue) {
		sOwner = zValue;
		bDinamic = true;
	}

	public BizAction SetTitle(String zValue) {
		sTitle = zValue;
		return this;
	}

	// public void SetActionListener( JAct zValue ) { oActionListener = zValue;}
	public void SetId(int zId) {
		pId = zId;
	}

	public void SetSubAcciones(BizActions value) {
		this.subActions = value;
	}

	public void SetIdAction(String zValue) {
		pAccion.setValue(zValue);
	}

	public String getIdAction() throws Exception {
		return pAccion.getValue();
	}

	public BizAction SetKeyAction(int zKey) {
		piKeyAction = zKey;
		return this;
	}

	public boolean IfKeyActionDefined() {
		return piKeyAction != Integer.MIN_VALUE;
	}

	public boolean isDinamic() {
		return bDinamic;
	}

	public boolean isBreak() {
		return this.bBreak;
	}

	public boolean isInAlta() {
		return this.bInAlta;
	}

	public boolean isAccessToDetail() {
		return bAccessToDetail;
	}

	public boolean isReportDetail() {
		return bReportDetail;
	}

	public void setOwner(String zValue) {
		sOwner = zValue;
	}

	public BizAction() throws Exception {
	}

	public void setObjOwner(JBaseWin zValue) {
		owner = zValue;
	}

	public JBaseWin getObjOwner() {
		return owner;
	}

	/**
	 * Indicates whether this action requires full object serialization when being
	 * transferred between client and server. Subclasses may override this method to
	 * opt-out of the compact transfer path.
	 *
	 * @return {@code true} if the full action must be serialized, {@code false}
	 *         otherwise.
	 */
	public boolean needsFullSerialization() throws Exception {
		return getIdAction().startsWith("anonimus_");
	}

	public JWin getObjWinOwner() {
		return (JWin) owner;
	}

	public JWins getObjWinsOwner() {
		return (JWins) owner;
	}

	public boolean isWins() {
		return owner instanceof JWins;
	}

	public boolean isWin() {
		return owner instanceof JWin;
	}

	public boolean hasOwner() {
		return owner != null;
	}

	@Override
	public void createProperties() throws Exception {
		this.addItem("accion", pAccion);
		this.addItem("descripcion", pDescr);
		this.addItem("nro_icono", pNroIcono);
		this.addItem("restringido", pRestringido);
	}

	@Override
	public void createFixedProperties() throws Exception {
		this.addFixedItem(KEY, "accion", "Operación", true, true, 20);
		this.addFixedItem(FIELD, "descripcion", "Descripción", true, true, 50);
		this.addFixedItem(FIELD, "nro_icono", "Nro Icono", true, true, 3);
		this.addFixedItem(VIRTUAL, "restringido", "Restringido", true, true, 1);
	}

	@Override
	public String GetTable() {
		return "";
	}

	public boolean isMulti() throws Exception {
		return bMulti;
	}

	public boolean isGroup() throws Exception {
		return bGroup;
	}

	public boolean isSwap() throws Exception {
		return bSwap;
	}

	public void execDeshabilitarPermisos() throws Exception {
		JExec oExec = new JExec(this, "DeshabilitarPermisos") {
			@Override
			public void Do() throws Exception {
				DeshabilitarPermisos();
			}
		};
		oExec.execute();
	}

	public void DeshabilitarPermisos() throws Exception {
		BizOperacion oOpe = new BizOperacion();
		oOpe.Read(this.getCompany(), pAccion.getValue());
		oOpe.processDelete();
		this.notifyUpdateOK();
	}

	public void execHabilitarPermisos() throws Exception {
		JExec oExec = new JExec(this, "HabilitarPermisos") {
			@Override
			public void Do() throws Exception {
				HabilitarPermisos();
			}
		};
		oExec.execute();
	}

	public String getCompany() throws Exception {
		if (pCompany.isNotNull())
			return this.pCompany.getValue();
		return BizUsuario.getUsr().getCompany();
	}

	public String getOperDescrip() throws Exception {
		String d = sTitle + " - " + pDescr.getValue();
		if (this.isPanel())
			return d + " [panel]";
		else
			return d;
	}

	public void HabilitarPermisos() throws Exception {
		BizOperacion oOpe = new BizOperacion();
		oOpe.setCompany(this.getCompany());
		oOpe.SetOperacion(pAccion.getValue());
		oOpe.SetDescrip(this.getOperDescrip()); // sTitle + " - " +
		oOpe.setNroIcono(this.GetNroIcono());// pDescr.GetValor());
		oOpe.processInsert();
		this.notifyUpdateOK();
	}

//	public void FormAutorizar() throws Exception {
//		GuiAutorizar oAutorizar = new GuiAutorizar();
//		oAutorizar.GetcDato().pAccion.setValue(this.pAccion.getValue());
//		oAutorizar.showNewForm();
//		oActionAutorizada.Do();
//	}

	public boolean VerificarHabilitacion() throws Exception {
		BizOperacionRol oOper = BizUsuario.OperacionHabilitada(pAccion.getValue());
		if (oOper == null)
			return false;
		if (!oOper.ifClaveSupervisor())
			return true;
//		oActionAutorizada = submit;
//
//		submit = new JAct() {
//			public void Do() throws Exception {
//				FormAutorizar();
//			}
//		};
		return true;
	}

	public boolean isPermisosHabilitados() throws Exception {
		return BizOperacion.getHash(BizUsuario.getUsr().getCompany()).getElement(pAccion.getValue()) == null;
	}

	public boolean hasSubActions() throws Exception {
		if (this.subActions == null)
			return false;
		return (this.subActions.ifRecordFound());
	}

	public boolean isPanel() throws Exception {
		return kPanel == 1;
	}

	public boolean ifWeb() throws Exception {
		return kWeb == 1;
	}

	public boolean ifMenu() throws Exception {
		return kMenu == 1;
	}

	public boolean ifToolBar() throws Exception {
		return kToolBar == 1;
	}

	public boolean useDefaultToolBarMore() throws Exception {
		return kToolBarMore == -1;
	}

	public boolean ifToolBarMore() throws Exception {
		return kToolBarMore == 1 || kToolBarMore == -1;
	}

	public boolean isOnlyInForm() throws Exception {
		return kOnlyForm == 1;
	}

	public boolean isNotInForm() throws Exception {
		return kNotInForm == 1;
	}

	public boolean isListType() throws Exception {
		if (sTipoArbol == null)
			return false;
		return sTipoArbol.equalsIgnoreCase("Group");
	}

	public boolean isListTypeUnique() throws Exception {
		if (sTipoArbol == null)
			return false;
		return sTipoArbol.equalsIgnoreCase("Detail");
	}

	/*
	 * public String GetIconFile() throws Exception { return
	 * getIcon().pFile.GetValor(); }
	 */
	/*
	 * public BizIcon getIcon() throws Exception { int iNro = GetNroIcono();
	 * 
	 * BizIcon icon = new BizIcon(); icon.SetNoExcSelect( true ); icon.SetFiltros(
	 * "nro_icono", iNro ); if( !icon.Read() ) { icon.ClearFiltros();
	 * icon.SetFiltros( "nro_icono", 0 ); icon.Read(); }
	 * 
	 * return icon; }
	 */
	public BizAction addSubAction(BizAction subAction) throws Exception {
		if (this.subActions == null)
			this.subActions = new BizActions();
		this.subActions.addItem(subAction);
		return subAction;
	}

//	public boolean ProcessKey(KeyEvent e) throws Exception {
//		int iPssKey = this.GetKeyAction();
//		if (JBaseKeys.CompareKeys(e, iPssKey)) {
//			this.submit.Do();
//			e.consume();
//			return true;
//		}
//		return false;
//	}

	@Override
	public String toString() {
		return pAccion.toString() + " [" + pDescr.toString() + "]";
	}

//	public void actionPerformed(ActionEvent e) {
//		JAct submit = null;
//		try {
//			submit = this.getObjSubmit();
//		}
//		catch (Exception ex) {
//			UITools.MostrarError(ex);
//		}
//		if (submit != null) submit.actionPerformed(e);
//	}

	public void setIconFile(String zValue) {
		this.setIconFile(0, zValue);
	}

	public void setIconFile(int size, String zValue) {
		sIconFileSize[size] = zValue;
	}

	public boolean hasIconFile(int size) {
		return sIconFileSize[size] != null;
	}

	public boolean hasIconFile() {
		return hasIconFile(0);
	}

	public String getIconFile(int size) throws Exception {
		if (this.sIconFileSize[size] != null)
			return this.sIconFileSize[size];
		else
			return GuiIconos.GetGlobal().buscarIcono(size, this.GetNroIcono()).GetFile();
	}

	public String getIconFile() throws Exception {
		return getIconFile(0);
//		if (this.sIconFile != null)
//			return this.sIconFile;
//		else return GuiIconos.GetGlobal().buscarIcono(this.GetNroIcono()).GetFile();
	}

	private boolean hasToCreateMultiAction() throws Exception {
		return this.isMulti() && !this.isGroup() && (owner instanceof JWins);
	}

	private JAct createMultiAction(BizAction a) throws Exception {
		JWins wins = (JWins) owner;
		JActMultiSubmit multiSubmit = new JActMultiSubmit(wins);
		JIterator iter = wins.getStaticIterator();
		while (iter.hasMoreElements()) {
			JWin win = (JWin) iter.nextElement();
			JAct action = win.getSubmit(a);
			action.setActionSource(a);
			multiSubmit.addAction(action);
		}
		return multiSubmit;
	}

	private boolean hasToCreateGroupAction() throws Exception {
		return this.isGroup() && (owner instanceof JWins);
	}

	private JAct createGroupAction(BizAction a) throws Exception {
		JWins wins = (JWins) owner;
		JActGroupSubmit groupAct = new JActGroupSubmit(wins);
		if (!wins.getRecords().ifRecordFound()) {
			JWin win = wins.getWinRefWithActions();
			win.setDropListener(wins.getDropListener());
			JAct submit = win.getSubmit(a);

			groupAct.setAct(submit);
			groupAct.setExitOnOk(submit.isExitOnOk());
			return groupAct;
		}
		JWin win = wins.getFirstRecord();
		JAct act = win.getSubmit(a);
		if (!act.isOnlySubmit())
			return act;
		JAct submit = win.getSubmit(a);
		groupAct.setAct(submit);
		groupAct.setExitOnOk(submit.isExitOnOk());
		return groupAct;
//
//		JWins wins = (JWins) owner;
//		JActGroupSubmit groupAct = new JActGroupSubmit(wins);
//		JIterator iter = wins.getStaticIterator();
//		while (iter.hasMoreElements()) {
//			JWin win = (JWin) iter.nextElement();
//			JActSubmit submit = (JActSubmit) win.getSubmit(a);
//			groupAct.setActSubmit(submit);
//			return groupAct;
//		}
//		return null;
	}

	public boolean hasConfirmMessage() {
		return bConfirmMessage;
	}

	public BizAction setConfirmMessage(boolean confirmMessage) {
		bConfirmMessage = confirmMessage;
//		this.setCancelable(false); // lo spongo aca para arreglar todos las acciones bloqueantes
		return this;
	}

	public String getConfirmMessageDescription() {
		return (sConfirmMessageDescription == null) ? "Esta Ud. seguro?" : sConfirmMessageDescription;
	}

	public void setConfirmMessageDescription(String confirmMessageDescription) {
		bConfirmMessage = (confirmMessageDescription != null);
		sConfirmMessageDescription = confirmMessageDescription;
	}

	public BizAction createClone() throws Exception {
		BizAction clone = new BizAction();
		clone.owner = owner;
		clone.pId = pId;
		clone.sOwner = sOwner;
		clone.pAccion.setValue(this.pAccion.getValue());
		clone.pDescr.setValue(this.pDescr.getValue());
		clone.pNroIcono.setValue(this.pNroIcono.getValue());
		clone.sTipoArbol = sTipoArbol;
		clone.sTitle = sTitle;
		clone.sIconFile = sIconFile;
		clone.pRestringido.setValue(pRestringido.getValue());
		clone.piKeyAction = piKeyAction;
		clone.bDinamic = bDinamic;
		clone.bMulti = bMulti;
		clone.bConfirmMessage = bConfirmMessage;
		clone.sConfirmMessageDescription = sConfirmMessageDescription;
		clone.bNuevaVentana = bNuevaVentana;
		clone.bNuevaSession = bNuevaSession;
		clone.bModal = bModal;
		clone.kToolBar = kToolBar;
		clone.kToolBarMore = kToolBarMore;
		clone.kMenu = kMenu;
		clone.kPanel = kPanel;
		clone.kWeb = kWeb;
		if (!this.hasSubActions())
			return clone;
		JIterator iter = this.GetSubAcciones().getStaticIterator();
		while (iter.hasMoreElements()) {
			BizAction subAction = (BizAction) iter.nextElement();
			clone.addSubAction(subAction.createClone());
		}
		return clone;
	}

	public boolean isQueryAction() {
		return this.pId == QUERY;
	}

	public boolean isUpdateAction() {
		return this.pId == UPDATE;
	}

	public boolean isRemoveAction() {
		return this.pId == REMOVE;
	}

	public boolean isDropAction() {
		return this.pId == DROP;
	}

	public boolean isReportAction() {
		return this.pId == REPORT;
	}

	public void addFilterMap(RFilter f) throws Exception {
		this.addFilterMap(f.getField(), f.getValue());
	}

	public void addFilterMap(String filter, Date value) throws Exception {
		this.addFilterMap(filter, JDateTools.DateToString(value));
	}

	public void addFilterMap(String filter, Serializable value) throws Exception {
		if (filter == null)
			return;
		if (this.filterMap == null)
			this.filterMap = new JFilterMap();
		this.filterMap.setFieldChange(filter);
		this.filterMap.addFilterMap(filter, value);
	}

	public void addFilterMap(String filter, String value) throws Exception {
		if (this.filterMap == null)
			this.filterMap = new JFilterMap();
		this.filterMap.addFilterMap(filter, value);
	}

	public String getFilterMapValue(String f, String def) throws Exception {
		if (!this.hasFilterMap())
			return def;
		return (String) this.filterMap.getFilterValue(f, def);
	}

	public Object getFilterMapObjValue(String f, String def) throws Exception {
		if (!this.hasFilterMap())
			return def;
		return this.filterMap.getFilterValue(f, def);
	}

	public boolean hasFilterMapValue(String f) throws Exception {
		if (!this.hasFilterMap())
			return false;
		return !this.getFilterMapValue(f, "").isEmpty();
	}

	public JFilterMap getFilterMap() {
		return this.filterMap;
	}

	public boolean hasFilterMap() {
		return this.filterMap != null;
	}

	public void clearFilterMap() {
		this.filterMap = null;
	}

	public boolean hasBackAction() throws Exception {
		return bBackAction;
	}

	public boolean hasRefreshAction() throws Exception {
		return bRefreshAction;
	}

	public boolean hasFilterAction() throws Exception {
		return bFilterAction;
	}

	public boolean isFirstAccess() {
		return bFirstAccess;
	}

	public void setFirstAccess(boolean value) throws Exception {
		bFirstAccess = value;
	}

	public boolean isExport() throws Exception {
		return bExport;
	}

	public void setExport(boolean value) throws Exception {
		bExport = value;
	}

	public void setBreak(boolean value) throws Exception {
		bBreak = value;
	}

	public void setInAlta(boolean value) throws Exception {
		bInAlta = value;
	}

//	public boolean isFilterMapAssigned() throws Exception {
//		if (!this.hasFilterMap()) return true;
//		return this.getFilterMap().isAssigned();
//	}

	public void setAccessToDetail(boolean accessToDetail) {
		bAccessToDetail = accessToDetail;
	}

	public void setReportDetail(boolean value) {
		bReportDetail = value;
	}

	public boolean waitMoreFilters() throws Exception {
		if (this.isExport())
			return false;
		if (this.isFirstAccess())
			return true;
		return false;
	}

	public void takeDinamycData(BizAction source) throws Exception {
		this.setObjFilterMap(source);
		this.setExport(source.isExport());
	}

	String row;

	public String getRow() {
		return row;
	}

	public int getRowAsInt() {
		return Integer.parseInt(row);
	}

	public long getRowAsLong() {
		return Long.parseLong(row);
	}

	public void setRow(String row) {
		this.row = row;
	}

	public boolean hasRow() {
		if (row == null)
			return false;
		if (row.isEmpty())
			return false;
		return true;
	}

	public String forceProviderName;

	public String getForceProviderName() {
		return forceProviderName;
	}

	public void setForceProviderName(String forceProviderName) {
		this.forceProviderName = forceProviderName;
	}

	public String getProviderName() throws Exception {
		if (forceProviderName != null)
			return forceProviderName;
//		return "prov_"+this.getObjSubmit().getResult().hashCode();
		String action = JWebActionFactory.getCurrentRequest().getNameDictionary(this.getIdAction().replace(".", "_") + ((getData() != null) ? getData().toString() : ""));
		if (JWebActionFactory.getCurrentRequest().getLevel() > 1)
			action += "__l" + JWebActionFactory.getCurrentRequest().getLevel();

		return "p_" + action + (row != null ? "_" + row : "");
	}

	public void setObjFilterMap(JFilterMap f) {
		filterMap = f;
	}

//	public void setSubAction(BizAction value) {
//		this.subAction=value;
//	}
//	
//	public BizAction getSubAction() {
//		return this.subAction;
//	}
//
//	public boolean hasSubAction() {
//		return this.subAction!=null;
//	}

	public boolean isSameAction(BizAction other) throws Exception {
		if (this.getIdAction().isEmpty())
			return false;
		return other.getIdAction().equals(this.getIdAction());
	}

	public JBaseForm findFormFlat(int sector) throws Exception {
		JAct submit = this.getObjSubmit();
		JBaseForm formFlat = submit.getFormFlat();
		formFlat.setMake(sector);
		return formFlat;
	}

	public void setBackground(String v) {
		this.background = v;
	}

	public String getBackground() {
		return this.background;
	}

	public void setForeground(String v) {
		this.foreground = v;
	}

	public String getForeground() {
		return this.foreground;
	}

}