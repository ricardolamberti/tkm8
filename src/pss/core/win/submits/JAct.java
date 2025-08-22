package pss.core.win.submits;

import java.awt.event.ActionEvent;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import pss.core.services.JExec;
import pss.core.services.records.JRecords;
import pss.core.tools.JExcepcion;
import pss.core.tools.JExceptionRunAction;
import pss.core.tools.JMessageInfo;
import pss.core.tools.JTools;
import pss.core.tools.PssLogger;
import pss.core.win.IControl;
import pss.core.win.JBaseWin;
import pss.core.win.JControlWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.winUI.forms.JBaseForm;
import pss.www.platform.actions.JWebActionFactory;
import pss.www.platform.actions.JWebRequest;

/**
 * Base class for all executable actions in the UI framework.
 * <p>
 * A {@code JAct} ties together the originating window, the identifier of the
 * action to perform and the resulting window or form. Subclasses implement the
 * specific behavior (query, modification, navigation, etc.) that should happen
 * when the action is triggered.
 * </p>
 */
public abstract class JAct implements Cloneable, Serializable {

	private static final long serialVersionUID = -2631894790579883735L;
	private String uniqueID = UUID.randomUUID().toString();
  
	public String getUniqueId() {
		return uniqueID.startsWith(JWebRequest.OBJ_PREFIX+"act_") ?uniqueID:JWebRequest.OBJ_PREFIX+"act_"+uniqueID;

	}

	public void setUniqueID(String uniqueID) {
		this.uniqueID = uniqueID;
	}

	private String sName;
	private int actionId;
	private String actionUniqueId;
	private JMessageInfo message;
	private transient JBaseWin result;
	private BizAction actionSource;
	private transient JAct actionNext = null;
	private boolean web = false;
	protected boolean historyAction = true;
	protected boolean historyTarget = false;
	protected boolean bExitOnOk = true;
	protected boolean bForceExitOnOk = false;

	protected boolean bforceModal = true;
	protected boolean bQueryAction = false;

	protected String fieldChanged;
	protected String winLovRowId;
	protected BizAction fieldChangeProvider;
	private boolean clearChangeInputs;
	protected boolean modifiedOnServer = false;
	protected boolean reRead = true;
	private Serializable data; // datos libre para enviar dentro de un submit, esta va al cliente y vuelve
	private Serializable dataLocal; // datos libre para enviar dentro de un submit, pero a diferencia de data, solo
																	// se carga en el Jact para usarlo dentro del submit
	boolean inHistory = false;

	public Serializable getDataLocal() {
		return dataLocal;
	}

	public void setDataLocal(Serializable data) {
		this.dataLocal = data;
	}

	public Serializable getData() {
		return data;
	}

	public boolean refreshOnSubmit() throws Exception {
		return true;
	}

	public void setData(Serializable data) {
		this.data = data;
	}

	public JMessageInfo getMessage() {
		return message;
	}

	public JAct setMessage(JMessageInfo message) {
		this.message = message;
		return this;
	}

	public JAct setMessage(String message) {
		this.message = new JMessageInfo(message);
		return this;
	}

	public boolean isReRead() {
		return reRead;
	}

	public void setReRead(boolean reRead) {
		this.reRead = reRead;
	}

	public boolean isClearChangeInputs() throws Exception {
		return clearChangeInputs;
	}

	public boolean isForceModal() {
		return bforceModal;
	}

	public void setForceModal(boolean bforceModal) {
		this.bforceModal = bforceModal;
	}

	public JAct setClearChangeInputs(boolean clearFieldChanges) {
		this.clearChangeInputs = clearFieldChanges;
		return this;
	}

	public BizAction getFieldChangeProvider() {
		return fieldChangeProvider;
	}

	public void setFieldChangeProvider(BizAction fieldChangeProvider) {
		this.fieldChangeProvider = fieldChangeProvider;
	}

	public JAct setHistoryAction(boolean historyAction) {
		this.historyAction = historyAction;
		return this;
	}

	public JAct setHistoryTarget(boolean v) {
		this.historyTarget = v;
		return this;
	}

	public void setNextAction(JAct value) {
		this.actionNext = value;
	}

	public JAct() {
		super();
	}

	public JAct(JBaseWin zResult) {
		this(zResult, -1);
	}

	public JAct(BizAction value) {
		this.actionSource = value;
	}

	public String getFieldChanged() throws Exception {
		return fieldChanged;
	}

	public String getWinLovRowId() {
		return winLovRowId;
	}

	public void markFieldChanged(BizAction provider, String rowId, String idControl) {
		this.fieldChanged = idControl;
		this.winLovRowId = rowId;
		this.fieldChangeProvider = provider;
	}

	public boolean isCorrectRow(JAct actParent, int rowpos, String provider) throws Exception {
		if (actParent.getWinLovRowId() == null)
			return true;
		return actParent.getWinLovRowId().indexOf("_" + rowpos + "_") != -1 && actParent.getWinLovRowId().indexOf("form_" + provider + "_") != -1;
	}

	public JAct preserveFieldChange(JAct actParent, int rowpos, String provider) throws Exception {
		if (!isCorrectRow(actParent, rowpos, provider))
			return this;
		markFieldChanged(actParent.getFieldChangeProvider(), actParent.getWinLovRowId(), actParent.getFieldChanged());
		return this;
	}

	public JAct(JBaseWin zResult, String zActionId) {
		super();
		this.result = zResult;
		this.actionUniqueId = zActionId;
	}

	public JAct(JBaseWin zResult, int zActionId) {
		super();
		this.result = zResult;
		this.actionId = zActionId;
	}

	public JAct(String zActionName) {
		this.sName = zActionName;
	}

	public boolean hasBackAction() throws Exception {
		if (this.hasActionSource())
			return this.actionSource.hasBackAction();
		return true;// this.hasActionSource();
	}

	public String getName() {
		return this.sName;
	}

	public void setActionUniqueId(String zValue) {
		actionUniqueId = zValue;
	}

	public String getActionUniqueId() {
		if (hasActionUniqueId())
			return actionUniqueId;
		return "" + getActionId();
	}

	public boolean hasActionUniqueId() {
		return actionUniqueId != null;
	}

	public void setActionId(int zValue) {
		actionId = zValue;
	}

	public int getActionId() {
		return actionId;
	}

	public boolean hasActionId() {
		return actionId != -1;
	}

	public String getTitle() throws Exception {
		if (getActionSource() != null) {
			String title = getActionSource().GetDescr();
			if (title != null && !title.equals(""))
				return title;
		}
		if (GetWins() != null) {
			String title = GetWins().GetTitle();
			if (title != null && !title.equals(""))
				return title;

		}
		return getName();
	}

	public void Do(ActionEvent zEvt) throws Exception {
		Do();
	};

	public void Do() throws Exception {
		GetBWin().showQueryForm();
	};

	public JBaseWin GetBWin() throws Exception {
		return null;
	}

	public final JWins GetWins() throws Exception {
		return (JWins) GetBWin();
	};

	public final JWin GetWin() throws Exception {
		return (JWin) GetBWin();
	};

//  public void handleException(Throwable zException) { UITools.MostrarError(zException);}
	public boolean canPerformInBackground() {
		return true;
	}

	private JBaseWin generateResult() throws Exception {
		if (this.result != null)
			return this.result;
		this.result = this.GetBWin();
		if (result == null) {
			// JExcepcion.SendError( "JAct malformado. GetBWin() retorna null." );
			return null;
		}
		return result;
	}

	public final JBaseWin getResult() throws Exception {
		return this.generateResult();
	}

	public final JWins getWinsResult() throws Exception {
		return (JWins) this.getResult();
	}

	public final JWin getWinResult() throws Exception {
		return (JWin) this.getResult();
	}

	public boolean hasResult() throws Exception {
		return this.result != null;
	}

	public boolean isWins() throws Exception {
		return this.result instanceof JWins;
	}

	public boolean isWin() throws Exception {
		return this.result instanceof JWin;
	}

	public final boolean isModifiedOnServer() throws Exception {
		this.getResult();
		return modifiedOnServer;
	}

	public JBaseForm getForm() throws Exception {
		return null;
	}

	public JBaseForm getFormFlat() throws Exception {
		return null;
	}

	public boolean canReReadForm() throws Exception {
		return this.fieldChanged == null && reRead;
	}

	public JBaseForm getFormEmbedded() throws Exception {
		if (this.getWinResult() == null)
			return null;
		this.getWinResult().setEmbedded(true);
		return getForm();
	}

	// produce un retroceso en el history?
	public boolean isBack() {
		return false;
	}

	public IControl createControlWin() throws Exception {
		return new JControlWin() {
			@Override
			public JWins getRecords() throws Exception {
				return getWinsResult();
			}
		};
	}

//  public boolean isReportAction() { return false;}
//  public boolean isDropAction() { return false;}

	public JAct doNextAction() throws Exception {
		if (result != null && result.hasSubmitListener())
			return this.result.getSubmitListener();
		return this.nextAction();
	}

	public JAct nextAction() throws Exception {
		return this.actionNext;
	}

	public JAct nextAction(Exception e) throws Exception {
		if (e instanceof JExceptionRunAction) {
			JExceptionRunAction ex = (JExceptionRunAction) e;
			ex.setWin(getWinResult());
		}
		throw e;

	}

	public JAct doSubmit() throws Exception {
		return doSubmit(false);
	}

	public JAct doSubmit(boolean zWeb) throws Exception {
		try {
			this.web = zWeb;
			this.submit();
			JAct act = this.doNextAction();
			if (act != null && this.getMessage() != null)
				act.setMessage(this.getMessage());
			return act;
		} catch (Exception e) {
			if (JTools.isExceptionLog(e))
				PssLogger.logError(e);
			return this.nextAction(e);
		}
	}

	public boolean backAfterDropControl(JAct act) throws Exception {
		return false;
	}

	public boolean isWeb() {
		return this.web;
	}

	public boolean isExitOnOk() throws Exception {
		return bExitOnOk;
	}

	public boolean forceModal() throws Exception {
		return bExitOnOk;
	}

	public JAct setExitOnOk(boolean bExitOnOk) {
		this.bExitOnOk = bExitOnOk;
		return this;
	}

	public boolean isForceExitOnOk() {
		return bForceExitOnOk;
	}

	public void setForceExitOnOk(boolean bForceExitOnOk) {
		this.bForceExitOnOk = bForceExitOnOk;
	}

	public void submit() throws Exception {
		JExec exec = new JExec(null, null) {
			public void Do() throws Exception {
				execSubmit();
			}
		};
		exec.execute();
	}

	public void execSubmit() throws Exception {
	}

	public void setResult(JBaseWin zValue) {
		this.result = zValue;
	}

	/**
	 * Returns the result window if it was explicitly set without triggering
	 * generation of a default one. This helper is useful when reconstructing
	 * actions from serialized data.
	 */
	public JBaseWin getResultDirect() {
		return this.result;
	}

	public void setActionSource(BizAction value) throws Exception {
		this.actionSource = value;
	}
//  public void setOwner(JBaseWin zValue) {
//  	this.owner=zValue;
//  }
//  public JWin getWinOwner() {
//  	if (this.owner==null) return null;
//  	if (this.owner instanceof JWin) return (JWin) this.owner;
//  	return null;
//  }
//  public JBaseWin getOwner() throws Exception {
//  	return this.owner;
//  }
//  public BizAction getOwnerAction() throws Exception {
//  	return this.owner.getActionById(this.actionId);
//  }

//  public BizAction getActionByIdInOwner() throws Exception {
//  	return this.getOwner().getActionByName(this.ownerActionId);
//  }

	public boolean isAnonimusSubmit() throws Exception {
		return !this.getActionSource().hasOwner();
	}

	public JAct refresh() throws Exception {
		if (this.isAnonimusSubmit())
			return this;
		return this.getActionSource().refreshSubmit();
	}

	public BizAction getActionSource() throws Exception {
		if (this.actionSource == null) {
			this.actionSource = new BizAction();
			this.actionSource.setObjSubmit(this);
			this.actionSource.setObjOwner(this.result);
			// this.actionSource.setModal(true);
			this.actionSource.SetIdAction("anonimus_" + (this.result == null ? this.hashCode() : this.result.hashCode()));
		}
		return this.actionSource;
	}

	public boolean hasActionSource() throws Exception {
		return this.actionSource != null;
	}

	protected JAct getSubmitById() throws Exception {
		BizAction a = this.getWinResult().findActionByUniqueId(this.getActionUniqueId(), false, false); // fuerzo el ok action
		if (a == null)
			JExcepcion.SendError("No Existe acción: " + this.getActionUniqueId());
		a.setModal(getActionSource().isModal());
		return a.getSubmit();
	}

	protected JAct getSubmitByIdActionInOwner() throws Exception {
		if (!this.hasActionSource())
			return this;
		return this.getActionSource().getSubmit();
	}

	public boolean isInHistory() {
		return inHistory;
	}

	public void setInHistory(boolean inHistory) {
		this.inHistory = inHistory;
	}

	public boolean isHistoryAction() throws Exception {
		return this.historyAction;
	}

	public void setQueryAction(boolean bQueryAction) {
		this.bQueryAction = bQueryAction;
	}

	public boolean isQueryAction() throws Exception {
		return bQueryAction;
	}

	public boolean isTargetAction(JAct submit) throws Exception {
		return this.isTargetAction();
	}

	public boolean isTargetAction() throws Exception {
		return this.historyTarget;
	}

	public boolean isMultiProcess() throws Exception {
		return false;
	}

	public boolean isOnlySubmit() {
		return false;
	}

	public boolean hasSelected() {
		return false;
	}

	public boolean backAfterSubmit() throws Exception {
		return false;
	}

	public boolean backAfterSubmit(JAct last) throws Exception {
		return this.backAfterSubmit();
	}

	public JAct getObjSubmitTarget() throws Exception {
		return this;
	}

	protected JAct getObjSubmitBySource() throws Exception {
		BizAction actionSource = this.getActionSource();
		if (actionSource.getObjOwner() != null)
			actionSource.clearSubmit();
		return actionSource.getObjSubmit();
	}

//  public void cleanAction() {}

	public void notifyAction(Object value) throws Exception {
	}

	public JAct getFinalSubmit() throws Exception {
		return this;
	}

	public JRecords<?> getForceRecords() throws Exception {
		JBaseWin bw = this.getResult();
		if (bw instanceof JWins)
			return ((JWins) bw).getRecords();
		JWin win = (JWin) bw;
		return JRecords.createRecords(win.getRecord());
	}

	public boolean hasIdControlListener() throws Exception {
		// if (!this.isWin()) return false;
		return this.getResult().hasDropControlIdListener();
	}

	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public boolean desactiveReread(JAct action) throws Exception {
		return false;
	}

	public boolean forceMarkFieldChange(JAct action) throws Exception {
		return false;
	}

	/**
	 * Serializes this {@link JAct} including all serializable members.
	 * <p>
	 * {@link JBaseWin} instances are registered using
	 * {@link JWebActionFactory#getCurrentRequest()#registerWinObjectObj} to avoid
	 * sending the whole object over the wire. {@link BizAction} members and nested
	 * {@code JAct} instances are serialized recursively. Any other serializable
	 * member is stored through
	 * {@link JWebActionFactory#getCurrentRequest()#registerObjectObj} so the
	 * serialized data is added to the request via {@code addRegisteredObject}.
	 * </p>
	 *
	 * @return a JSON string representing this action.
	 */
//===== helpers de tipo =====
	private static boolean isPlainType(Class<?> t, Object v) {
		if (t.isPrimitive())
			return true;
		if (Number.class.isAssignableFrom(t))
			return true; // Integer, Long, Double, BigDecimal, etc.
		if (t == Boolean.class || t == Character.class)
			return true;
		if (t == String.class || t == java.math.BigDecimal.class || t == java.math.BigInteger.class)
			return true;
		if (t == java.util.UUID.class)
			return true;
		if (t.isEnum())
			return true;
		// también tratamos como “plain” cuando el valor concreto es wrapper/string
		// aunque el field sea Object
		if (v != null) {
			Class<?> c = v.getClass();
			if (Number.class.isAssignableFrom(c) || c == Boolean.class || c == Character.class || c == String.class || c == java.math.BigDecimal.class || c == java.math.BigInteger.class || c == java.util.UUID.class || c.isEnum())
				return true;
		}
		return false;
	}

	private static String plainToString(Object v) {
		if (v == null)
			return null;
		if (v instanceof java.util.UUID)
			return v.toString();
		if (v instanceof java.math.BigDecimal || v instanceof java.math.BigInteger)
			return v.toString();
		if (v instanceof Character)
			return String.valueOf((char) v);
		if (v.getClass().isEnum())
			return ((Enum<?>) v).name(); // para E:
		return String.valueOf(v);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static Object stringToPlain(String s, Class<?> targetType) {
		if (s == null)
			return null;
		if (targetType == String.class)
			return s;
		if (targetType == int.class || targetType == Integer.class)
			return Integer.valueOf(s);
		if (targetType == long.class || targetType == Long.class)
			return Long.valueOf(s);
		if (targetType == boolean.class || targetType == Boolean.class)
			return Boolean.valueOf(s);
		if (targetType == double.class || targetType == Double.class)
			return Double.valueOf(s);
		if (targetType == float.class || targetType == Float.class)
			return Float.valueOf(s);
		if (targetType == short.class || targetType == Short.class)
			return Short.valueOf(s);
		if (targetType == byte.class || targetType == Byte.class)
			return Byte.valueOf(s);
		if (targetType == char.class || targetType == Character.class)
			return s.isEmpty() ? '\0' : s.charAt(0);
		if (targetType == java.math.BigDecimal.class)
			return new java.math.BigDecimal(s);
		if (targetType == java.math.BigInteger.class)
			return new java.math.BigInteger(s);
		if (targetType == java.util.UUID.class)
			return java.util.UUID.fromString(s);
		if (targetType.isEnum())
			return Enum.valueOf((Class<? extends Enum>) targetType, s);
		return s; // fallback seguro
	}

//===== serialize =====
	public String serialize() throws Exception {
		Map<String, String> map = new HashMap<>();
		map.put("class", getClass().getName());

		Class<?> clazz = getClass();
		while (clazz != null && JAct.class.isAssignableFrom(clazz)) {
			for (Field f : clazz.getDeclaredFields()) {
				if (Modifier.isStatic(f.getModifiers()))
					continue;
				f.setAccessible(true);
				Object value = f.get(this);
				if (value == null)
					continue;

				String key = f.getName();
				// 1) valores simples en línea
				if (isPlainType(f.getType(), value)) {
					if (f.getType().isEnum() || (value != null && value.getClass().isEnum())) {
						map.put(key, "E:" + plainToString(value)); // Enum por name()
					} else {
						map.put(key, "V:" + plainToString(value)); // primit./wrapper/string/big*/uuid
					}
					continue;
				}

				// 2) objetos especiales
				if (value instanceof JAct) {
					map.put(key, "A:" + JWebActionFactory.getCurrentRequest().registerActObjectObj((JAct) value));
				} else if (value instanceof BizAction) {
					map.put(key, "B:" + JWebActionFactory.getCurrentRequest().registerActionObjectObj((BizAction) value));
				} else if (value instanceof JBaseWin) {
					String id = JWebActionFactory.getCurrentRequest().registerWinObjectObj((JBaseWin) value);
					if (id != null && !id.isEmpty())
						map.put(key, "W:" + id);
				} else if (value instanceof Serializable) {
					String id = JWebActionFactory.getCurrentRequest().registerObjectObj((Serializable) value);
					if (id != null && !id.isEmpty())
						map.put(key, "S:" + id);
				}
				// si no es Serializable ni caso especial → se omite (o se podría toString)
			}
			clazz = clazz.getSuperclass();
		}
		return JWebActionFactory.getCurrentRequest().serializeRegisterMapJSON(map);
	}

//===== deserialize =====
	public static JAct deserialize(String data) throws Exception {
		Map<String, String> map = JWebActionFactory.getCurrentRequest().deserializeRegisterMapJSON(data);
		String clazzName = map.get("class");
		Class<?> clazz = Class.forName(clazzName);
		JAct act = (JAct) clazz.getDeclaredConstructor().newInstance();
		String uniqueId = map.get("uniqueID").substring(2);
		act.setUniqueID(uniqueId);
		JWebActionFactory.getCurrentRequest().addObjectCreated(uniqueId, act);

		Class<?> scan = clazz;
		while (scan != null && JAct.class.isAssignableFrom(scan)) {
			for (Field f : scan.getDeclaredFields()) {
				if (Modifier.isStatic(f.getModifiers()))
					continue;

				String key = f.getName();
				String stored = map.get(key);
				if (stored == null)
					continue;

				f.setAccessible(true);

				// prefijo + payload
				if (stored.length() >= 2 && stored.charAt(1) == ':') {
					char tag = stored.charAt(0);
					String payload = stored.substring(2);

					switch (tag) {
					case 'V': { // valor simple
						Object v = stringToPlain(payload, f.getType());
						f.set(act, v);
						break;
					}
					case 'E': { // enum
						@SuppressWarnings("unchecked")
						Class<? extends Enum> et = (Class<? extends Enum>) f.getType();
						Object v = Enum.valueOf(et, payload);
						f.set(act, v);
						break;
					}
					case 'A': {
						f.set(act, JWebActionFactory.getCurrentRequest().getRegisterObject(payload));
						break;
					}
					case 'B': {
						f.set(act, JWebActionFactory.getCurrentRequest().getRegisterObject(payload));
						break;
					}
					case 'W': { // objeto registrado (win)
						f.set(act, JWebActionFactory.getCurrentRequest().getRegisterObject(payload));
						break;
					}
					case 'S': { // serializable registrado
						f.set(act, JWebActionFactory.getCurrentRequest().getRegisterObject(payload));
						break;
					}
					default:
						// desconocido → ignorar de forma segura
						break;
					}
				} else {
					// retrocompat: sin prefijo → intentar parsear como “V:”
					Object v = stringToPlain(stored, f.getType());
					try {
						f.set(act, v);
					} catch (Exception ignore) {
					}
				}
			}
			scan = scan.getSuperclass();
		}
		return act;
	}

}
