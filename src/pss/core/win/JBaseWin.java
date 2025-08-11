package pss.core.win;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.URL;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.codec.net.URLCodec;

import pss.JPath;
import pss.common.layout.JWinLayout;
import pss.common.security.BizUsuario;
import pss.core.data.BizPssConfig;
import pss.core.services.records.JBaseRecord;
import pss.core.services.records.JFilterMap;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.JExcepcion;
import pss.core.tools.JTools;
import pss.core.tools.PssLogger;
import pss.core.tools.collections.JIterator;
import pss.core.win.actions.BizAction;
import pss.core.win.actions.BizActions;
import pss.core.win.security.BizWinPropiedad;
import pss.core.win.security.GuiWinPropiedad;
import pss.core.win.submits.JAct;
import pss.core.winUI.icons.BizIcon;
import pss.core.winUI.icons.GuiIcon;
import pss.core.winUI.icons.GuiIconos;
import pss.www.platform.actions.JWebActionFactory;
import pss.www.platform.actions.resolvers.JDoInternalRequestResolver;

public abstract class JBaseWin implements IInMemory,Transferable,Serializable {

	/**
	 * 
	 */
	private int iNroIcono=0;
	private int iNroIconoOpen=0;
	private transient BizActions actionMap=null;
//	private transient BizActions actionMapToolbar=null;
//	private transient BizActions noActionMapToolbar=null;
	private String sTitle="";
	private String sTitleRight="";
	private String sVision="";
	private String sStatusHelp="";
	private String sHelpTag=null;
	private String sHelp=null;
	protected JBaseRecord oBaseDato=null;
	protected String sDescripField="";
	protected String sColor="";
	private String sKeyField;
	private JBaseWin oDropListener=null;
	private JAct dropControlIdListener=null;
	private JAct submitListener=null;
	private JBaseWin parent=null;
	protected boolean embedded=false;
	protected boolean readeable=true;
	protected boolean canConvertToURL=true;
	protected String selectedCell=null;
	private boolean bModeWinLov = false;
	private boolean keepFormOnNew = false;
	protected String modeView=null;
	protected String attachField=null;
	private String uniqueID = UUID.randomUUID().toString();
  
	public static final String MODE_FORM = "F";
	public static final String MODE_SWAP = "S";
	public static final String MODE_LIST = "L";
	public static final String MODE_MATRIX = "M";
	public static final String MODE_JSON = "J";
	public static final String MODE_TREE = "T";
	public static final String MODE_EXPAND = "E";
	public static final String MODE_OPTIONS = "O";
	public static final String MODE_BIGDATA = "B";
	
	public boolean isInMobile() {// inclusion de wwww,... no se me ocurre como evitarlo
		return JWebActionFactory.isMobile();
	}

	public void setUniqueID(String uniqueID) {
		this.uniqueID = uniqueID;
	}

	public String getModeView() throws Exception {
		return modeView;
	}
	public void setModeView(String modeView) {
		this.modeView = modeView;
	}
	public void setKeepFormOnNew(boolean keepFormOnNew) {
		this.keepFormOnNew = keepFormOnNew;
	}
	public boolean isKeepFormOnNew() throws Exception {
		return keepFormOnNew;
	}
	public boolean hasAttachField() {
		return attachField!=null;
	}
	public String getAttachField() {
		return attachField;
	}
	public void setAttachField(String attachField) {
		this.attachField = attachField;
	}
//  private String statusHelp;
  
	public String confirmBack() throws Exception {
		return null;
	}
	public void setSelectedCell(String selectedCell) {
		this.selectedCell = selectedCell;
	}
	public String getSelectedCell() throws Exception {
		return this.selectedCell;
	}
	public JAct getDropControlIdListener() {
		return dropControlIdListener;
	}
	public boolean isCard() {
		if (getDropControlIdListener()==null) return false;
		return getDropControlIdListener().getWinLovRowId().indexOf("_card_")!=-1;
	}
	public JRecord getRecordCard() throws Exception {
		if (getDropControlIdListener()==null) return null;
		return getDropControlIdListener().getActionSource().getObjSubmit().getForm().build().findCard(getDropControlIdListener().getWinLovRowId()).getWinCard().getRecord();
	}
	public boolean hasRowId() {
		if (getDropControlIdListener()==null) return false;
		return getDropControlIdListener().getWinLovRowId()!=null;
	}
	public boolean hasDropControlIdListener() {
		return dropControlIdListener!=null;
	}
	public void setDropControlIdListener(JAct controlIdListener) {
		this.dropControlIdListener = controlIdListener;
	}
	public void clearDropControlIdListener() {
		this.dropControlIdListener = null;
	}
	public String getHelpTag() {
		if (sHelpTag!=null) return sHelpTag;
		
		return this.getClass().getCanonicalName();
	}
	public void setHelpTag(String sHelpTag) {
		this.sHelpTag = sHelpTag;
	}


	/**
	 * @return the embedded
	 */
	public boolean isEmbedded() {
		return embedded;
	}
	public boolean isReadeable() {
		return readeable;
	}
	public void setReadeable(boolean readeable) {
		this.readeable = readeable;
	}

	public boolean isReaded() throws Exception {
		JBaseRecord rec = this.GetBaseDato();
		if (rec  instanceof JRecord) {
			return ((JRecord) rec).wasDbRead();
			
		}
		return false;
	}
	String alert;
	
	public String getAlert() {
		return alert;
	}
	public void setAlert(String alert) {
		this.alert = alert;
	}
	public String anyAlert(long count) throws Exception {
		return alert;
	}


	/**
	 * @param embedded
	 *          the embedded to set
	 */
	public void setEmbedded(boolean embedded) {
		this.embedded=embedded;
	}
	
	public void setCanConvertToURL(boolean value) {
		this.canConvertToURL=value;
	}

	public String getKeyField() throws Exception {
		return sKeyField;
	}
  public String  getSwapKeyField() throws Exception { return null; }
  public String  getSwapDescripField() {  	return getDescripField(); }
 
	public void clearBaseDato() throws Exception {
		oBaseDato=null;
	}

	public void SetBaseDato(JBaseRecord zValue) throws Exception {
		oBaseDato=zValue;
		if (oBaseDato!=null) this.SetVision(oBaseDato.GetVision());
	}

	public boolean canExpandTree() throws Exception {
		return true;
	}

	
	public String getStatusHelp() throws Exception {
		return sStatusHelp;
	}

	public void setStatusHelp(String sStatusHelp) {
		this.sStatusHelp = sStatusHelp;
	}


	/**
	 * @deprecated
	 */
	@Deprecated
	public void SetItemClave(String zItem) {
		sKeyField=zItem;
	}

	public JBaseRecord GetBaseDato() throws Exception {
		if (oBaseDato!=null) return oBaseDato;
		return (this.oBaseDato=this.ObtenerBaseDato());
	}

	public JBaseRecord ObtenerBaseDato() throws Exception {
		return null;
	}

	// --------------------------------------------------------------------------
	// //
	// Constructor
	// --------------------------------------------------------------------------
	// //
	public JBaseWin() {
		super();
	}

	static int it=0;
	public String getColor() throws Exception {
		String Colors[]= BizUsuario.getSkin().createGenerator().getColorGraph();//{ "a6cee3","1f78b4","b2df8a","33a02c","fb9a99","e31a1c","fdbf6f","ff7f00","cab2d6","6a3d9a","ffff99","b15928","8dd3c7","ffffb3","bebada","fb8072","80b1d3","fdb462","b3de69","fccde5","d9d9d9","bc80bd","ccebc5","ffed6f","1b9e77","d95f02","7570b3","e7298a","66a61e","e6ab02","a6761d","666666","fbb4ae","b3cde3","ccebc5","decbe4","fed9a6","ffffcc","e5d8bd","fddaec","f2f2f2" };
		if (sColor==null || sColor.equals("")) sColor=Colors[it++];
		if (it>=Colors.length) it=0;
		return sColor.startsWith("#")?sColor.substring(1):sColor;
	}
	
	public String getDescripField() {
		return sDescripField;
	}

	@Deprecated
	public String getSearchFields(boolean withKey) throws Exception {
		return getSearchFields( withKey,getSearchKeyFields(),getSearchFields());
	}
	public String getSearchKeyFields() throws Exception {
		return this.getKeyField();
	}
	public String getSearchFields() throws Exception {
		return this.getDescripField();
	}

	public String getSearchFields(boolean withKey,String zkey,String zdescrip) throws Exception {
		String search ="";
		String key =zkey;
		String descrip =zdescrip;
		if (key==null) key=this.getSearchKeyFields();
		if (descrip==null) descrip=this.getSearchFields();
		
		search+=(search.equals("")?"":";")+(key==null?"":key);
		search+=(search.equals("")?"":";")+(descrip==null?(key==null?"":key):descrip);
		return search;
	}

	@Deprecated
	public int GetDobleClick() throws Exception {
		return GetDobleClick("",false);
	}
	@Deprecated
	public int GetSimpleClick() throws Exception {
		return GetSimpleClick("",false);
	}
	@Deprecated
	public int GetDobleClick(String zColName, boolean isEje) throws Exception {
		return -1;
	}
	@Deprecated
	public int GetSimpleClick(String zColName, boolean isEje) throws Exception {
		return 1	;
	}
	
	public String getDobleClick() throws Exception {
		String id =getDobleClickByUniqueId();
		if (id!=null)
			return id;
		return ""+ GetDobleClick();
	}
	public String getDobleClickSecondChance() throws Exception {
		String id =getDobleClickSecondChanceByUniqueId();
		if (id!=null)
			return id;
		return ""+ GetDobleClick();
	}
	public String getSimpleClick() throws Exception {
		String id =getSimpleClickByUniqueId();
		if (id!=null)
			return id;
		return""+ GetSimpleClick();
	}
	public String getDobleClick(String zColName, boolean isEje) throws Exception {
		String id =getDobleClickByUniqueId(zColName, isEje);
		if (id!=null)
			return id;
		return ""+ GetDobleClick("",false);
	}
	public String getSimpleClick(String zColName, boolean isEje) throws Exception {
		String id =getSimpleClickByUniqueId(zColName, isEje);
		if (id!=null)
			return id;
		return""+ GetSimpleClick("",false);
	}
	public String getDobleClickByUniqueId() throws Exception {
		return getDobleClickByUniqueId("",false);
	}
	public String getDobleClickSecondChanceByUniqueId() throws Exception {
		return getDobleClickSecondChanceByUniqueId("",false);
	}
	public String getSimpleClickByUniqueId() throws Exception {
		return getSimpleClickByUniqueId("",false);
	}
	public String getDobleClickByUniqueId(String zColName, boolean isEje) throws Exception {
		return null;
	}
	public String getDobleClickSecondChanceByUniqueId(String zColName, boolean isEje) throws Exception {
		return null;
	}
	public String getSimpleClickByUniqueId(String zColName, boolean isEje) throws Exception {
		return null;
	}
	public String GetTitle() throws Exception {
		return sTitle;
	}
	
	public boolean hasTitle() throws Exception {
		return !GetTitle().isEmpty();
	}
	public String getTitleRight() throws Exception {
		return sTitleRight;
	}
	
	public boolean hasTitleRight() throws Exception {
		return !getTitleRight().isEmpty();
	}
	public String getHelp() {
		return sHelp;
	}
	public void setHelp(String sHelp) {
		this.sHelp = sHelp;
	}

	public int GetNroIcono() throws Exception {
		return iNroIcono;
	}

	public int GetNroIconoOpen() throws Exception {
		return iNroIconoOpen;
	}

	public String GetVision() {
		return sVision;
	}
	
	public boolean isVision(String zVision) {
		if (zVision==null && GetVision()==null) return true;
		if (zVision!=null && GetVision()==null) return false;
		if (zVision==null && GetVision()!=null) return false;
 		return GetVision().equals(zVision);
	}

	// public void SetDobleClick ( JAct oAct ) { oDobleClick = oAct ; }
	public void SetNroIcono(int zNroIcono) {
		iNroIcono=zNroIcono;
	}
	public void SetNroIconoOpen(int zNroIcono) {
		iNroIconoOpen=zNroIcono;
	}
	public void setActionMap(BizActions value) {
		this.actionMap=value;
	}
//	public void setActionMapToolbar(BizActions value) {
//		this.actionMapToolbar=value;
//	}
//	public void setNoActionMapToolbar(BizActions value) {
//		this.noActionMapToolbar=value;
//	}
	// public void SetVision ( int zValue ) throws Exception { sVision = new
	// Integer( zValue ).toString(); }

	public JBaseWin SetVision(String zValue) throws Exception {
		sVision=zValue;
		if (this.GetBaseDato()!=null)
			this.GetBaseDato().SetVision(sVision);
		return this;
	}

	/**
	 * @deprecated
	 */
	@Deprecated
	public void SetItemDescrip(String zDesc) {
		sDescripField=zDesc;
	}

	/**
	 * @deprecated
	 */
	@Deprecated
	public void SetTitle(String zValue) throws Exception {
		sTitle=zValue;
	}
	public void setTitle(String zValue) throws Exception {
		sTitle=zValue;
	}
	public void setTitleRight(String zValue) throws Exception {
		sTitleRight=zValue;
	}
	public GuiIcon GetIcon() throws Exception {
		GuiIcon oIcon=GuiIconos.GetGlobal().buscarIcono(this.GetNroIcono());
		return (oIcon);
	}
	public BizActions getRawActionMap() throws Exception {
		return actionMap;
	}
	
//	public boolean isOkToolbar(BizAction a, boolean check, boolean form) throws Exception {
//		if (!a.isAccessToDetail() && !a.ifToolBar()) return false;
//		if (!form && a.isOnlyInForm()) return false;
//		if (!this.verifyAction(a, check, check)) return false;
//		return true;
//	}
	
//	public BizActions getActionMapToolbar(boolean check, boolean form) throws Exception {
//		BizActions actions = new BizActions();
//		actions.setStatic(true);
//		JIterator<BizAction> iter = this.getActionMap().getStaticIterator();
//		while (iter.hasMoreElements()) {
//			BizAction action = iter.nextElement();
//			if (!this.isOkToolbar(action, check, form)) continue;
//			actions.addItem(action);
//		}
//		return actions;
//	}

//	public BizActions getActionMapToolbar() throws Exception {
//		if (actionMapToolbar==null) {
//			if (actionMap==null)	getActionMap();
//			return actionMap;
//		}
//		return actionMapToolbar;
//	}
//	public BizActions getNoActionMapToolbar(boolean check, boolean form) throws Exception {
//		BizActions actions = new BizActions();
//		actions.setStatic(true);
//		JIterator<BizAction> iter = this.getActionMap().getStaticIterator();
//		while (iter.hasMoreElements()) {
//			BizAction action = iter.nextElement();
//			if (!this.isOkToolbar(action, check, form)) continue;
//			actions.addItem(action);
//		}
//		return actions;
//	}
	
	public BizActions getActionMap() throws Exception {
		this.generateActionMap();
		return this.actionMap;
	}

	public String GetIconFile() throws Exception {
		return GetIconFile(GuiIconos.SIZE_DEFA);
	}
	public String GetIconFileOpen() throws Exception {
		return GetIconFileOpen(GuiIconos.SIZE_DEFA);
	}
	public String GetIconFile(long size) throws Exception {
		int icono = 1;
		try {
			icono = this.GetNroIcono(); 
		} catch (Exception ignore) {}
		
		if (GuiIconos.GetGlobal()==null) {
			BizIcon oIcon=new BizIcon();
			oIcon.dontThrowException(true);
			if (!oIcon.Read(icono)) oIcon.Read(0);
			return "pss_icon/"+oIcon.pFile.getValue();
		}
		if (icono==-1) return null;

		GuiIcon oIcon=GuiIconos.GetGlobal().buscarIcono(size,icono);
		if (oIcon==null) return "";

		return "pss_icon/"+oIcon.GetFile();
	}
	public String GetIconFileOpen(long size) throws Exception {
		int icono = 1;
		try {
			icono = this.GetNroIconoOpen(); 
		} catch (Exception ignore) {}
		
		if (GuiIconos.GetGlobal()==null) {
			BizIcon oIcon=new BizIcon();
			oIcon.dontThrowException(true);
			if (!oIcon.Read(icono)) return GetIconFile();
			return "pss_icon/"+oIcon.pFile.getValue();
		}
		if (icono==-1) return "pss_icon/"+GetIconFile();

		GuiIcon oIcon=GuiIconos.GetGlobal().buscarIcono(size,icono);
		if (oIcon==null)return "pss_icon/"+GetIconFile();

		return "pss_icon/"+oIcon.GetFile();
	}
//	public ImageIcon GetIconImage() throws Exception {
//		if (this.GetNroIcono()==-1) {
//			return null;
//		}
//		return GuiIconGalerys.GetGlobal().getIcon(GetIconFile());
//	}

	public String GetIconoFile() throws Exception {
		return GetIconFile();
	}

	public JBaseWin getParent() {
		return parent;
	}

	public JRecord getRecordParent() throws Exception {
		if (parent==null) return null;
		if (!(parent instanceof JWin)) return null;
		return ((JWin)parent).getRecord();
	}

	public JBaseWin getGrandParent() throws Exception {
		if (this.parent==null) return null;
		return this.parent.parent;
	}

	public void setParent(JBaseWin value) {
		this.parent=value;
	}

	public BizAction addAction(int zId, String zDescr, JAct zActionLis, int zIcon, boolean zEnMenu, boolean zEnBar) throws Exception {
		return addAction(zId, getIdAction(zId), null, zDescr, zActionLis, Integer.MIN_VALUE, zIcon, zEnMenu, zEnBar, false, null);
	}
	public BizAction addAction(int zId, String zDescr, JAct zActionLis, int zIcon, boolean zEnMenu, boolean zEnBar, String specialSelector) throws Exception {
		return addAction(zId, getIdAction(zId), null, zDescr, zActionLis, Integer.MIN_VALUE, zIcon, zEnMenu, zEnBar, false, null, true, specialSelector);
	}
	public BizAction addAction(int zId, String zDescr, int ziKeyAction, JAct zActionLis, int zIcon, boolean zEnMenu, boolean zEnBar) throws Exception {
		return addAction(zId, getIdAction(zId), null, zDescr, zActionLis, ziKeyAction, zIcon, zEnMenu, zEnBar, false, null);
	}

	public BizAction addAction(int zId, String zDescr, int ziKeyAction, JAct zActionLis, int zIcon, boolean zEnMenu, boolean zEnBar,boolean zCancelable) throws Exception {
		return addAction(zId, getIdAction(zId), null, zDescr, zActionLis, ziKeyAction, zIcon, zEnMenu, zEnBar, false, null, zCancelable);
	}

	public BizAction addAction(int zId, BizAction zAction, String zDescr, JAct zActionLis, int zIcon, boolean zEnMenu, boolean zEnBar) throws Exception {
		return addAction(zId, getIdAction(zId), zAction, zDescr, zActionLis, Integer.MIN_VALUE, zIcon, zEnMenu, zEnBar, false, null);
	}

	public BizAction addAction(int zId, BizAction zAction, String zDescr, JAct zActionLis, int zIcon, boolean zEnMenu, boolean zEnBar, boolean zEnArbol, String zTipoArbol) throws Exception {
		return addAction(zId, getIdAction(zId), zAction, zDescr, zActionLis, Integer.MIN_VALUE, zIcon, zEnMenu, zEnBar, zEnArbol, zTipoArbol);
	}
	
	public BizAction addAction(int zId, String zDescr, JAct zActionLis, int zIcon, boolean zEnMenu, boolean zEnBar, boolean zEnArbol) throws Exception {
		return addAction(zId, getIdAction(zId), null, zDescr, zActionLis, Integer.MIN_VALUE, zIcon, zEnMenu, zEnBar, zEnArbol, "Group");
	}

	public BizAction addAction(int zId, String zDescr, JAct zActionLis, int zIcon, boolean zEnMenu, boolean zEnBar, boolean zEnArbol, String zTipoArbol) throws Exception {
		return addAction(zId, getIdAction(zId), null, zDescr, zActionLis, Integer.MIN_VALUE, zIcon, zEnMenu, zEnBar, zEnArbol, zTipoArbol);
	}
	public BizAction addAction(String zIdAction, String zDescr, int ziKeyAction, JAct zActionLis, int zIcon, boolean zEnMenu, boolean zEnBar) throws Exception {
		return addAction(-1, zIdAction, null, zDescr, zActionLis, ziKeyAction, zIcon, zEnMenu, zEnBar, false, null);
	}
	public BizAction addAction(String zIdAction, String zDescr, JAct zActionLis, int zIcon, boolean zEnMenu, boolean zEnBar) throws Exception {
		return addAction(-1, zIdAction, null, zDescr, zActionLis, Integer.MIN_VALUE, zIcon, zEnMenu, zEnBar, false, null);
	}
	public BizAction addAction(String zIdAction, String zDescr, JAct zActionLis, int zIcon, boolean zEnMenu, boolean zEnBar, String specialSelector) throws Exception {
		return addAction(-1, zIdAction, null, zDescr, zActionLis, Integer.MIN_VALUE, zIcon, zEnMenu, zEnBar, false, null, true, specialSelector);
	}
	public BizAction addAction(int zId, String zIdAction, BizAction zAction, String zDescr, JAct zActionLis, int ziKeyAction, int zIcon, boolean zEnMenu, boolean zEnBar, boolean zEnArbol, String zTipoArbol) throws Exception {
		return addAction( zId,  zIdAction,  zAction,  zDescr,  zActionLis,  ziKeyAction,  zIcon,  zEnMenu,  zEnBar,  zEnArbol,  zTipoArbol,  true);
	}
	public BizAction addAction(int zId, String zIdAction, BizAction zAction, String zDescr, JAct zActionLis, int ziKeyAction, int zIcon, boolean zEnMenu, boolean zEnBar, boolean zEnArbol, String zTipoArbol, boolean cancelable) throws Exception {
		return addAction( zId,  zIdAction,  zAction,  zDescr,  zActionLis,  ziKeyAction,  zIcon,  zEnMenu,  zEnBar,  zEnArbol,  zTipoArbol,  cancelable, null);
	}
	public BizAction addAction(int zId, String zIdAction, BizAction zAction, String zDescr, JAct zActionLis, int ziKeyAction, int zIcon, boolean zEnMenu, boolean zEnBar, boolean zEnArbol, String zTipoArbol, boolean cancelable, String specialSelector) throws Exception {
		BizAction oAct=new BizAction();
		oAct.pId=zId;
		oAct.setOwner(this.getClass().getName());
		oAct.pAccion.setValue(zIdAction);
		oAct.pDescr.setValue(zDescr);
		oAct.SetTitle(this.getOwnerTitle());
		oAct.pNroIcono.setValue(zIcon);
		oAct.SetAction(zActionLis);
		oAct.SetKeyAction(ziKeyAction);
		oAct.setInMenu(zEnMenu);
		oAct.setInToolbar(zEnBar);
		oAct.sTipoArbol=zTipoArbol;
		oAct.setInAlta(false);
		oAct.setCancelable(cancelable);
		oAct.setSpecialSelector(specialSelector);
		
		BizAction action=null;
		if (zAction==null) action=this.addAction(oAct);
		else action=this.addAction(zAction, oAct);

		oAct.setObjOwner(this);
		return action;
	}

	// public BizAction addAction( GuiAction zAction , BizAction zSubAction )
	// throws Exception {
	// return zAction.AddSubAction((GuiAction)
	// oActionMap.createJoinWin(zSubAction));
	// }

	public BizAction addAction(BizAction action, BizAction subAction) throws Exception {
		return action.addSubAction(subAction);
	}

	public BizAction addAction(BizAction action) throws Exception {
		if (this.actionMap==null) this.actionMap=new BizActions();
		return this.actionMap.addItem(action);
	}
	public void addActions(BizActions actions) throws Exception {
		if (this.actionMap==null) this.actionMap=new BizActions();
		this.actionMap.getStaticItems().addElements(actions.getStaticItems());
	}

	public boolean ifOkAction(int zAct) throws Exception {
		return this.checkAction(this.actionMap.findAction(zAct));
	}

	public String getIdAction(int zId) throws Exception {
		return getActionOwnerClassName()+"_"+zId;
	}

	protected boolean checkAction(BizAction zAct) throws Exception {
		if (zAct==null) return false;
//		boolean bOKAction=false;
//		bOKAction=zAct.VerificarHabilitacion();
//		if (bOKAction&&withOkAction) {
			try {
				return this.OkAction(zAct);
			} catch (Exception e) {
				PssLogger.logError(e);
				return false;
			}
//		}
//		doWithOkAction(zAct, bOKAction);
//		return bOKAction;

	}

	public void doWithOkAction(BizAction zAction, boolean bOk) throws Exception {

	}

	// --------------------------------------------------------------------------
	// //
	// Ok Accion
	// --------------------------------------------------------------------------
	// //
//	public boolean OkAction(int id) throws Exception {
//		BizAction a = this.findAction(id);
//		if (a==null) return false;
//		return this.OkAction(a);
//	}
	public boolean OkAction(BizAction a) throws Exception {
		return true;
	}
	public String whyNotOkAction(BizAction a) throws Exception {
		return null;
	}
	
	public String getDescrAction(BizAction a) throws Exception {
		return a.GetDescr();
	}

	public BizAction findActionByUniqueId(String zActId) throws Exception {
		return this.findActionByUniqueId(zActId, true, true);
	}
	public BizAction findActionByUniqueId(String zActId, boolean security, boolean okAction) throws Exception {
		if (zActId==null) return null;
		BizActions as = this.getActionMap();
		if (as==null) JExcepcion.SendError("action map en nulo");
		BizAction a=as.findActionByUniqueId(zActId);
		if (a==null) return null;
		if (security && !a.isOkSecurity()) return null;
		if (okAction && !a.isOkAction()) return null;
		return a;
	}
//	public JAct getJAct(long zActId) throws Exception {
//		return this.findActionByUniqueId(""+zActId).getObjSubmit();
//	}
//	public JAct getJAct(String zActId) throws Exception {
//		return this.findActionByUniqueId(zActId).getObjSubmit();
//	}
	public BizAction findAction(int zActId) throws Exception {
		if (zActId<0) return null;
		return this.findActionByUniqueId(zActId+"", true, true);
	}
	public BizAction getRawAction(String zActId) throws Exception {
		if ( this.actionMap==null) return null;
		return this.actionMap.findActionByUniqueId(zActId);
	}
	public BizAction getRawAction(int zActId) throws Exception {
		if ( this.actionMap==null) return null;
		return this.actionMap.findAction(zActId);
	}
	public BizAction findQueryAction() throws Exception {
		return this.getActionMap().findAction(this.getQueryAction());
	}
	
	public int getQueryAction() throws Exception {
		return 1; //sobrescribir para encontrar el form
	}

	
	public JWin getObjWinProperty() throws Exception {
		GuiWinPropiedad wp=new GuiWinPropiedad();
		BizWinPropiedad p=(BizWinPropiedad) wp.getRecord();

		JBaseWin w = (JBaseWin)this.getActionOwnerClass().newInstance();
		p.setObjName(w.getOwnerTitle());
		p.setClase(w.getClass().getName());
		p.setNroIcono(this.GetNroIcono());

		p.SetObjAcciones(this.getActionMap());
		return wp;
	}

//	public GuiActions GetAcciones(GuiAction zAction) throws Exception {
//		GuiActions oActions=new GuiActions();
//		oActions.toStatic(zAction.GetcDato().GetSubAcciones());
//		return oActions;
//	}

	public String getDescripFieldValue() throws Exception {
		return this.getDescripField();
	}


	public boolean OnArbol(String zTipo) throws Exception {
		return true;
	}

	public boolean acceptsSecurityAction() {
		return true;
	}

//	public BizAction getActionByName(String action) throws Exception {
//		JIterator<BizAction> iter=this.getActionMap().getStaticItems().getIterator();
//		while (iter.hasMoreElements()) {
//			BizAction oAction=iter.nextElement();
//			if (oAction.getIdAction().equals(action)) return oAction;
//		}
//		return null;
//	}	
//	public BizAction getActionById(int action) throws Exception {
//		JIterator<BizAction> iter=this.getActionMap().getStaticItems().getIterator();
//		while (iter.hasMoreElements()) {
//			BizAction oAction=iter.nextElement();
//			if (oAction.getId()==action) return oAction;
//		}
//		return null;
//	}

	// Drag and Drop
	public static Clipboard localClipboard=new Clipboard("local");

	public static DataFlavor WinFlavor=DataFlavor.stringFlavor;

	public static DataFlavor[] WinFlavors= { WinFlavor };

	public synchronized Object getTransferData(DataFlavor flavor) {
		return this;
	}

	public boolean isDataFlavorSupported(DataFlavor flavor) {
		return true;
	}

	public DataFlavor[] getTransferDataFlavors() {
		return WinFlavors;
	}

	public boolean ifAcceptDrop(JBaseWin zBaseWin) throws Exception {
		return false;
	}

	public JAct Drop(JBaseWin zBaseWin, BizAction actionSource,Serializable data) throws Exception {
		return this.Drop(zBaseWin, actionSource);
	}

	public JAct Drop(JBaseWin zBaseWin, BizAction actionSource) throws Exception {
		return this.Drop(zBaseWin);
	}

	public JAct Drop(JBaseWin zBaseWin, String modifiers) throws Exception {
		return this.Drop(zBaseWin);
	}

	public JAct Drop(JBaseWin zBaseWin) throws Exception {
		return null;
	}

	public JAct DropControl(JAct zAct) throws Exception {
		JAct actSubmit = this.getSubmitListener();
		JAct act = this.getDropControlIdListener();
//		act.setHistoryAction(false);
		String controlId=act.getFieldChanged();
		BizAction controlIdProvider = act.getFieldChangeProvider();
		String rowId =  act.getWinLovRowId();

		JAct actFinal= actSubmit!=null?actSubmit:act;
		if (this.getClass().equals(actFinal.getResult().getClass()))
			actFinal.setResult(this); // ?
		actFinal.markFieldChanged(controlIdProvider, rowId, controlId);
		if (actSubmit!=null)
			actFinal.setData(actSubmit.getData());
//		if (actFinal instanceof JActNew) // HGK esto hace que despues de un winlov con un form en modo alta quede dos veces en el history
//			actFinal.setInHistory(false);
		this.setDropControlIdListener(null);
		this.setSubmitListener(null);
		return actFinal;
	}

	private boolean enProceso=false;
//	private boolean lastSecurity=false;
//	private boolean lastOkAction=false;
	public void generateActionMap() throws Exception {
//		if (lastSecurity!=security && okAction!=lastOkAction)
//			clearActions();
		if (this.enProceso) {
			while (this.enProceso) {Thread.yield();}
			return;
		}
		if (this.actionMap!=null) return;
		try { 
//			lastSecurity=security;
//			lastOkAction=okAction;
			this.enProceso=true;
			this.actionMap=new BizActions();
//			this.noActionMapToolbar=new BizActions();
			this.createGenericActionMap();
			this.createActionMap();
			this.verifyActions(this.actionMap);
		} finally {
			this.enProceso=false;
		}
	}

	
	public void createGenericActionMap() throws Exception {
	}

//	public void createActionMap(boolean okAction) throws Exception {
//		this.createActionMap();
//	}
	public void createActionMap() throws Exception {
	}

//	public void generateFullActionMap() throws Exception {
//		this.generateActionMap(true, false);
//	}

//	public BizActions getActionMapForSecurity() throws Exception {
//		JBaseWin oClone=this.getClass().newInstance();
//		oClone.SetBaseDato(this.GetBaseDato()); 
//		oClone.createActionMap();
//		oClone.verifyActions(oClone.actionMap);
//		return oClone.actionMap;
//	}
	
//	public boolean verifyAction(BizAction action, boolean security, boolean okAction) throws Exception {
//		if (security && !action.VerificarHabilitacion()) return false; // seguridad
//		if (okAction && !this.checkAction(action)) return false; // ok action
//		return true;
//	}
	public void reCheckActions() throws Exception {
		this.verifyActions(this.actionMap);
	}
	
	public boolean verifyActions=true;
	public void setVerifyActions(boolean v) {
		this.verifyActions=v;
	}
	
	public void verifyActions(BizActions actions) throws Exception {
		if (actions==null) return;
		if (!this.verifyActions) return;
		//		noActionMapToolbar=new BizActions();
		long size=actions.sizeStaticElements();
		for(int i=0; i<size; i++) {
			BizAction action=actions.getStaticElement(i);
			if (!action.VerificarHabilitacion()) 
				action.setOkSecurity(action.VerificarHabilitacion()); // seguridad
			if (!this.checkAction(action)) 
				action.setOkAction(false); // ok action

//			if (!this.verifyAction(action, security, okAction)) {
//				actions.removeStaticItem(action);
//				if (noActionMapToolbar!=null) 
//					noActionMapToolbar.addItem(action);
//				i--; size--;
//				continue;
//			}
			if (action.hasSubActions()&&!action.isDinamic()) {
				this.verifyActions(action.GetSubAcciones());
			}
		}
	}

//	private void SecurityActionSubitems(BizActions zActions, boolean security, boolean okAction) throws Exception {
//		long size=zActions.sizeStaticElements();
//		for(int i=0; i<size; i++) {
//			BizAction oAction=zActions.getStaticElement(i);
//			if (!this.OKAction(withOkAction, oAction)) {
//				zActions.removeStaticItem(oAction);
//				i--;
//				size--;
//				continue;
//			}
//			if (oAction.hasSubActions()&&!oAction.isDinamic()) {
//				SecurityActionSubitems(oAction.GetSubAcciones(), withOkAction);
//			}
//		}
//	}

//	public boolean ifAcceptRefresh(JWinEvent e, boolean zGlobal) throws Exception {
//		return false;
//	}

	public boolean ifHabilitado(int zAccion) throws Exception {
		JIterator<BizAction> iter=this.getActionMap().getStaticIterator();
		while (iter.hasMoreElements()) {
			BizAction oAction=iter.nextElement();
			if (oAction.getId()==zAccion) return true;
		}
		return false;
	}

	/**
	 * Serializes the BizActions that this BaseWin object handles and, if bRecursive=true, the subaactions of each element, enclosing them in <item> tags.
	 */
	/*
	 * public void WebGenerarAccionesMenu(JSAXWrapper wrapper, boolean bRecursive, boolean bWeb) throws Exception { BizActions acciones = this.getActionMap(); JInteger oID = new JInteger(0); acciones.firstRecord(); while (acciones.nextRecord()) { WebGenerarAcciones(wrapper, bRecursive, (BizAction) acciones.getRecord(), false, true, bWeb, oID); } }
	 * 
	 * private void WebGenerarAcciones(JSAXWrapper wrapper, boolean bRecursive, BizAction jbaccion, boolean bToolbar, boolean bMenu, boolean bWeb, JInteger zID) throws Exception { boolean validada = true; AttributesImpl attr = new AttributesImpl();
	 * 
	 * if (bToolbar && (!jbaccion.ifToolBar() && !jbaccion.ifArbol())) { validada = false; }
	 * 
	 * if (bMenu && !jbaccion.ifMenu()) { validada = false; } // if( bWeb && !jbaccion.ifToolBar() ) { // validada = false; // }
	 * 
	 * if (validada) { // Si el pedido es recursivo encierra las acciones en elementos // "item" if (bRecursive) { attr.clear(); attr.addAttribute("", "id", "id", "CDATA", zID.toString()); wrapper.startElement("item", attr); zID.setValue(zID.getValue() + 1); }
	 * 
	 * jbaccion.toSAX(wrapper);
	 * 
	 * if (bRecursive) { if (jbaccion.hasSubActions()) { // Subacción jbaccion.GetSubAcciones().firstRecord(); while (jbaccion.GetSubAcciones().nextRecord()) { BizAction subaccion = (BizAction) jbaccion .GetSubAcciones().getRecord(); WebGenerarAcciones(wrapper, bRecursive, subaccion, bToolbar, bMenu, bWeb, zID); } }
	 * 
	 * wrapper.endElement("item"); } } }
	 */

	public boolean ifOkOrigenAction(BizAction zAct, Object zSource) throws Exception {
		return true;
	}

	public void showQueryForm() throws Exception {
	}

	public void clearActions() {
		while (this.enProceso) {Thread.yield();}
		this.actionMap=null;
//		this.actionMapToolbar=null;
//		this.noActionMapToolbar=null;
	}

//	public boolean ifAcceptRefreshOnlyForRefresh(JWinEvent e) throws Exception {
//		return false;
//	}

	public JAct getSubmit(BizAction a) throws Exception {
		return null;
	}

	public JAct getCoreSubmit(BizAction a) throws Exception {
		return null;
	}

	public JAct getSubmitFor(BizAction a) throws Exception {
		return null;
	}

	public void setDropListener(JBaseWin listener) {
		this.oDropListener=listener;
		if (listener!=null && listener.hasDropControlIdListener())
			this.dropControlIdListener=listener.getDropControlIdListener();
//		clearActions();// el tema es que no encuentra el drop
		
	}
	
	public void setSubmitListener(JAct listener) {
		this.submitListener=listener;
	}

	public void assignDropListener(JBaseWin listener) throws Exception {
	}


	public JBaseWin getDropListener() {
		return this.oDropListener;
	}

	public boolean hasDropListener() {
		return this.oDropListener!=null;
	}

	public boolean hasSubmitListener() {
		return this.submitListener!=null;
	}

	public JAct getSubmitListener() {
		return this.submitListener;
	}

	public boolean canConvertToURL() throws Exception {
		return this.canConvertToURL ;
	}

	public String getActionOwnerClassName() {
		return this.getActionOwnerClass().getName();
	}

	public Class getActionOwnerClass() {
		return this.getClass();
	}

	public String getOwnerTitle() throws Exception {
		return this.GetTitle();
	}

	/*
	 * public JBaseWin getExpandedItems(JBaseWin zOrigen) throws Exception { JIterator iter = this.getTreeActions().getIterator(); while ( iter.hasMoreElements() ) { GuiAction oAction = (GuiAction) iter.nextElement(); if ( ! oAction.GetcDato().ifArbolDetail() ) continue; return oAction.GetcDato().GetAction().getResult(); } return null; }
	 * 
	 */
	public String generateHTMLPrint(String company, String name) throws Exception {
		return this.generateHTMLPrint();
	}

	public String generateHTMLPrint() throws Exception {
		JWinLayout l = new JWinLayout(this.createLayoutWinHeader());
		return l.generateLayout();
	}

	public JBaseWin createLayoutWinHeader() throws Exception {
		return this;
	}

	public boolean forceCleanHistory() throws Exception {
		return false;
	}
	public boolean forceCleanIdemHistory() throws Exception {
		return false;
	}
	public boolean isCleanHistory() throws Exception {
		return false;
	}
	public boolean isModeWinLov() {
		return bModeWinLov;
	}
	public void setModeWinLov(boolean bModeWinLov) {
		this.bModeWinLov = bModeWinLov;
	}

	public void preRefresh() throws Exception {
		
	}

	public boolean checkActionComponentType(BizAction action, boolean isForm) throws Exception {
		return true;
	}

	public static final String TOOLBAR_TOP= "top";
	public static final String TOOLBAR_LEFT= "left";
	public static final String TOOLBAR_RIGHT= "right";
	public static final String TOOLBAR_BOTTOM= "bottom";
	public static final String TOOLBAR_IN= "in";
	public static final String TOOLBAR_NONE= "none";

	private String sToolbarForced=null;	
	public void setToolbarForced(String value) throws Exception {
		this.sToolbarForced=value;
	}
	public String getToolbarForced() throws Exception {
		return this.sToolbarForced;
	}
	public boolean hasToolbarForced() throws Exception {
		return this.sToolbarForced!=null;
	}

	int refreshOnSubmit = 0;
	public boolean refreshOnSubmit() throws Exception {
		if (refreshOnSubmit==0) return this.canConvertToURL(); //si esta en la base de datos, si esta en memoria no 
		return refreshOnSubmit==1? true:false;
	}
	
	public void setRefreshOnSubmit(boolean refresh) throws Exception {
		 this.refreshOnSubmit= refresh?1:2;
	}
	
	public JRecords getRecords() throws Exception {
			JBaseRecord br = this.GetBaseDato();
			if (br instanceof JRecords)
				return (JRecords)br;
			else
				return JRecords.createRecords(br);
	}
	// retorna objeto a draguear, null no es drageable
	public JBaseWin getObjectDrageable(String zone) throws Exception {
		return null;
	}

	// Reimplementar si se desea cambia las zonas estandar de JwinList JwinMatrix, el resultado de esta funcion se enviara como vision en el drop
	public String getZoneDrop(String zone) throws Exception {
		return zone;
	}
	
	// se implementa retornando true, en el que recibe el drag
	public boolean acceptDrop(String zone) throws Exception {
		return false;
	}
	// lista de clases con simpleClassname separado por espacios que se aceptan en el drop, null = todas
	public String getDropClassAccepted() throws Exception {
		return null;
	}
	
	// retorna listener al que se envian los objetos que cae en este objeto
	public JBaseWin getListenerForDragAndDropWeb(String zone,JBaseWin all) throws Exception {
		return this;
	}
	// dbl click para zonas que no tienen un win asociado (encabezado, totales, etc)
	public JBaseWin getDblClickObjectDrag(String zone,JBaseWin all) throws Exception {
		return null;
	}
	public int getDblClickDragAction(String zone,JBaseWin all) throws Exception {
		return -1;
	}
   
	public String getDragForeground(String zone) throws Exception {
		return null;
	}
	public String getDragBackground(String zone) throws Exception {
		return null;
	}

	public String getExtraParamsInternalRequest() throws Exception {
		return null;
	}
	public String getHtmlView(int action,String builderArguments,JFilterMap params) throws Exception {
		return getHtmlView(action, builderArguments, params,false,false,true,true);
	}	
	
	public String getHtmlView(int action,String builderArguments,JFilterMap params,boolean convertScriptRefToFile,boolean convertScriptRefToPrefix,boolean convertInjectStyle, boolean convertImageRef) throws Exception {
		String arguments = "";
		String excelArgs = "";
		if (builderArguments.equals("html"))
			arguments="dg_export=serializer=html,object=win_list_x,range=all,preserve=T,history=N&dg_win_list_nav=name_op1=export,with_preview_op1=N,embedded_op1=false,toolbar_op1=none,&";
		else if (builderArguments.equals("htmlfull")) 
			arguments="dg_export=serializer=htmlfull,object=win_list_x,range=all,preserve=T,history=N&dg_win_list_nav=name_op1=export,with_preview_op1=N,embedded_op1=false,toolbar_op1=none,&";
		else if (builderArguments.equals("excel")) {
			arguments="dg_export=serializer=excel,object=win_list_x,range=all,preserve=T,history=N";
			excelArgs ="<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>";
		}
		else if (builderArguments.equals("csv"))
			arguments="dg_export=serializer=csv,object=win_list_x,range=all,preserve=T,history=N";
		else if (builderArguments.equals("map"))
			arguments="dg_export=serializer=map,object=win_list_x,range=all,preserve=T,history=N";
		else if (builderArguments.equals("report"))
			arguments="dg_export=serializer_op1=report,object=win_list_x,range=all,preserve=T,history=N";
		if (builderArguments.equals("htmlfull")) 
			arguments+="&dg_client_conf=pw_op1=1000,ph_op1=2500,sw_op1=1000,sh_op1=2500";
		else
			arguments+="&dg_client_conf=pw_op1=1500,ph_op1=2500,sw_op1=1500,sh_op1=2500";
		if (params!=null) {
			JIterator<String> it = params.getMap().getKeyIterator();
			while (it.hasMoreElements()) {
				String key = it.nextElement();
				Object value = params.getMap().getElement(key);
				if (value instanceof String)
					arguments +="&dgf_filter_pane_fd-"+key+"="+new URLCodec().encode((String)value);
				
			}
		}
		String args = this.getExtraParamsInternalRequest();
		if (args!=null) {
				arguments +=args;
		}
		
		
		String server = BizPssConfig.getPssConfig().getUrlTotal();
		if (server==null)
			server = BizPssConfig.getPssConfig().getAppURLPreview();
		String id =""+(long)Math.ceil((Math.random()*77777));
		String extraArguments = JDoInternalRequestResolver.addInternaRequest(id, this, action,BizUsuario.getUsr().GetCertificado());
		URL url = new URL(server+"/do-InternalRequestResolver?id="+id+(arguments!=null?"&"+arguments:"")+(extraArguments!=null?"&"+extraArguments:""));
		int ptr = 0;
	  String inputLine;
		StringBuffer buffer = new StringBuffer();
		BufferedReader in=null;
    try {
				InputStreamReader isr = new InputStreamReader(url.openStream(), "ISO-8859-1");
				in = new BufferedReader(isr);
				while ((inputLine = in.readLine()) != null) {
					 if (builderArguments.equals("excel")) 
						 inputLine = excelArgs + inputLine;
					buffer.append(inputLine);
					if (builderArguments.equals("csv")) {
						buffer.append("\r\n");
					}
				}
		} finally {
			if (in!=null) in.close();
		}
    String html = buffer.toString(); 
    
    if (convertScriptRefToFile) {
      int oldPos=0;
    	while (true) {
    		String key = "v_"+BizPssConfig.getVersionJS()+"/";
			  int pos1 = buffer.indexOf(key,oldPos+1);
			  if (pos1==-1) break;
			  buffer = buffer.replace(pos1, pos1+key.length(), "");
    	}
    	
  		String pathToResources= "file:///"+JPath.PssPathResourceHtml();

	    oldPos=0;
			while (true) {
			  int pos1 = buffer.indexOf("href=\"",oldPos+1);
			  //buffer.toString().substring(pos1);
			  oldPos=pos1;
			  if (pos1==-1) break;
			  int pos3=buffer.indexOf("html/",oldPos+1);
			  int pos4=buffer.indexOf("skins/sbadmin/css/sb-admin-2.css",oldPos+1);
			  if (pos3!=-1 && pos3-pos1<30) {
			  	buffer = buffer.replace(pos1, pos3, "href=\""+pathToResources+"/");	  	
			  }
			  else if (pos4!=-1 && pos4-pos1<30) {
			  	buffer = buffer.replace(pos1, pos1+6+("skins/sbadmin/css/sb-admin-2.css".length()), "href=\""+pathToResources+"/skins/sbadmin/css/sb-admin-2-novariable.css");	  	
			  }
			  else	buffer = buffer.replace(pos1, pos1+6, "href=\""+pathToResources+"/");
			}   
	    oldPos=0;//skins/sbadmin/css/sb-admin-2.css
			while (true) {
			  int posb = buffer.indexOf("<body");
			  int pos1 = buffer.indexOf("src=\"",oldPos+1);
			  oldPos=pos1;
			  if (pos1>posb) break;
			  if (pos1==-1) break;
			  int pos2=buffer.indexOf("nls.strings",oldPos+1);
			  if (pos2!=-1 && pos2-pos1<30) {
				  int pos3=buffer.indexOf(".js",pos2);
			  	buffer = buffer.replace(pos1, pos3, "src=\""+pathToResources+"/html/nls.strings");	
			  } else 
			  		buffer = buffer.replace(pos1, pos1+5, "src=\""+pathToResources+"/");
			}   
		   return buffer.toString();
    }
    if (convertScriptRefToPrefix) {
  		String pathToResources= BizPssConfig.getPssConfig().getAppURLPrefix();
  		
	    int oldPos=0;
			while (true) {
			  int pos1 = buffer.indexOf("href=\"",oldPos+1);
			  oldPos=pos1;
			  if (pos1==-1) break;
			  if (buffer.substring(pos1).startsWith("href=\"/"+pathToResources+"/")) continue;
			  
			  buffer = buffer.replace(pos1, pos1+6, "href=\"/"+pathToResources+"/");
			}   
	    oldPos=0;
		  int posb = buffer.indexOf("<body");
			while (true) {
			  int pos1 = buffer.indexOf("src=\"",oldPos+1);
			  oldPos=pos1;
			  if (pos1>posb) break;
			  if (pos1==-1) break;
			  if (buffer.substring(pos1).startsWith("src=\"/"+pathToResources+"/")) continue;
	  		buffer = buffer.replace(pos1, pos1+5, "src=\"/"+pathToResources+"/");
			}   
		   return buffer.toString();
    }
		
		
		

   
   /*
		while (true) {
		  int pos1 = buffer.indexOf("<link rel=\"stylesheet\" href=\"skins/");
		  if (pos1==-1) break;
		  	int pos2 = buffer.indexOf(".css",pos1);
			  if (pos2==-1) break;
					String cssFile = server+"/"+buffer.substring(pos1+29,pos2+4);
					URL urlcss = new URL(cssFile);
					InputStream iscss = urlcss.openStream();
					int ptrcss = 0;
					StringBuffer buffercss = new StringBuffer();
					while ((ptrcss = iscss.read()) != -1) {
					    buffercss.append((char)ptrcss);
					}
					String css= buffercss.toString();
					buffer = buffer.replace(pos1, pos2+6, "");
					buffer = new StringBuffer( JTools.inlineStyles(buffer.toString(), css, false) );
		}*/
    if (convertInjectStyle) {
    	String oneCss=null;
  		while (true) {
  		  int pos1 = buffer.indexOf("<link");
  		  int pos2=0;
  			if (pos1 == -1)		break;
  			pos2 = buffer.indexOf(">", pos1);//buffer.substring(pos1)
  			if (pos2 == -1)		break;
  			String link = buffer.substring(pos1, pos2+1);
  			int pos3 = link.indexOf("href=\"");
  			if (pos3 == -1)	break;
  			int pos4 = link.indexOf("\"", pos3 + 7);
  			if (pos4 == -1)	break;
  			String cssFile = server + "/" + link.substring(pos3 + 6, pos4);
	 			buffer = buffer.replace(pos1, pos2+1, "<style type=\"text/css\"></style>");
  			if (cssFile.toLowerCase().indexOf("/bootstrap.min.css")!=-1) {
  				cssFile = JTools.replace(cssFile, "/bootstrap.min.css", "/bootstrap_email.css");
  				if (oneCss==null)
  					oneCss=cssFile;
					continue;
  			}
  			
	 			continue;
  		}
  		//antes convertia todos, pero era lento y solo uno tiene lo que me interesa
  		if (oneCss!=null) {
	 			URL urlcss = new URL(oneCss);
				InputStream iscss = urlcss.openStream();
				int ptrcss = 0;
				StringBuffer buffercss = new StringBuffer();
				while ((ptrcss = iscss.read()) != -1) {
				    buffercss.append((char)ptrcss);
				}
				String css= buffercss.toString();
			  css = JTools.replace(css, "--color", "#337ab7");
			  css = JTools.replace(css, "--color-fondo", "#ffffff");
			  css = JTools.replace(css, "--roweven", "#f1f1f1");
			  css = JTools.replace(css, "--rowevenover", "rgba(207, 206, 223, 0.8)");
			  css = JTools.replace(css, "--rowodd", "#ffffff");
			  css = JTools.replace(css, "--rowoddover", "rgba(207, 206, 223, 0.8)");
			  css = JTools.replace(css, "--rowselect", "rgba(176, 190, 222, 0.6)");
			  css = JTools.replace(css, "--colorbody", "#f8f8f8");
			  css = JTools.replace(css, "--colorpage", "white");
			  css = JTools.replace(css, "--breadcolor", "#495057");

 	 			buffer = new StringBuffer( JTools.inlineStyles(buffer.toString(), css, false));
  		}
//  			URL urlcss = new URL(cssFile);
//  			InputStream iscss = urlcss.openStream();
//  			int ptrcss = 0;
//  			StringBuffer buffercss = new StringBuffer();
//  			while ((ptrcss = iscss.read()) != -1) {
//  				buffercss.append((char) ptrcss);
//  			}
//  			String css = buffercss.toString();
//  			buffer = buffer.replace(pos1, pos2+1, "<style type=\"text/css\">" + css + "</style>");

  		
    }

//	  int pos1 = buffer.indexOf("/resources/styles[");
//	  if (pos1!=-1) {
//	  	int pos2 = buffer.indexOf("].css",pos1);
//		  if (pos2!=-1) {
//				String cssFile = server+buffer.substring(pos1,pos2+5);
//				URL urlcss = new URL(cssFile);
//				InputStream iscss = urlcss.openStream();
//				int ptrcss = 0;
//				StringBuffer buffercss = new StringBuffer();
//				while ((ptrcss = iscss.read()) != -1) {
//				    buffercss.append((char)ptrcss);
//				}
//				String css= buffercss.toString();
//				html = JTools.inlineStyles(html, css, false);
//			}
//	  }
    
    if (convertImageRef)
    	 html=JTools.replace(buffer.toString(), "pss_icon", server+"/pss_icon");
    else html=buffer.toString();
		return html;
		
	}
	
	public boolean isWin() {
		return false;
	}

	public boolean canEjecuteDrop() throws Exception {
		if (this.hasDropControlIdListener()) return false;
		return true;
	}
	public boolean canEjecuteDropControl(JAct act) throws Exception {
		if (!this.hasDropControlIdListener()) return false;
		if (act==null) return true;
		if (this.getDropControlIdListener()==act.getResult().getDropControlIdListener()) return false;
		return true;
		
	}
	// limpia para almacenar en history
	public void cleanUpToLeaveInMemory() throws Exception {
		actionMap=null;
//		actionMapToolbar=null;
//		noActionMapToolbar=null;
	}
	
	public String getHelpFor(BizAction a) throws Exception {
		return null;
	}

	public String getUniqueId() throws Exception {
		return "obj_"+ uniqueID;
	}
	
	// funciones que se disparan en el recorrido, de la generacion de la grilla, se habilitan los siguientes metodos para hacer calculos y no tener que hacer dos recorridas
	public void startShowInGridBigData() throws Exception {
	}
	public void showInGridBigData() throws Exception {
	}
	//devuelve si esta en error, para irlos contando
	public boolean processShowInGridBigData(JWin win) throws Exception {
		return false;
	}
	//permite hacer procesos sobre lo filtrado
	public void processShowInGridBigDataFilter(JWin win) throws Exception {
		
	}
	// devuelve  customInfo que se cargan al json y pueden ser usados como datos para los updateControls
	public Map<String,String> finishShowInGridBigData(long nroRecors, long fitrados) throws Exception {
		return null;
	}


}
