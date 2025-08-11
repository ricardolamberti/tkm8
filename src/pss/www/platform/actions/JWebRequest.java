/*
 * Created on 25-jun-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.platform.actions;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import org.apache.cocoon.environment.Request;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import pss.core.services.records.JBaseRecord;
import pss.core.tools.JTools;
import pss.core.tools.PssLogger;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JMap;
import pss.core.win.JBaseWin;
import pss.core.win.submits.JAct;
import pss.www.platform.cache.CacheProvider;
import pss.www.platform.cache.DistCache;
import pss.www.platform.actions.requestBundle.JWebActionData;
import pss.www.platform.actions.requestBundle.JWebActionDataBundle;
import pss.www.platform.applications.JWebApplication;
import pss.www.platform.applications.JWebApplicationSession;
import pss.www.platform.applications.JWebHistoryManager;
import pss.www.platform.applications.JWebHistoryManager.JLocalHistoryManager;
import pss.www.platform.applications.JWebServer;
import pss.www.ui.controller.JFrontDoorUICoordinator;
import pss.www.ui.processing.JWebActionRequestProcessor;
import pss.www.ui.processing.JWebUICoordinator;

/**
 * Wrapper around the servlet {@link Request} that provides convenient access to
 * request parameters, session data and registered application objects.
 * <p>
 * A {@code JWebRequest} is created by the {@link JWebActionRequestProcessor}
 * and remains associated with the processing thread through
 * {@link JWebActionFactory#CURRENT_REQUEST}. It also exposes a
 * {@link JWebRequestPackage} that captures the state of registered objects and
 * history information for serialization.
 * </p>
 */
public class JWebRequest {

	private Request oServletRequest;
	private JWebActionRequestProcessor oProcessor;
	private JWebAction oResult;
	private Map<String, Object> oObjectMap;
	private JMap<String, Object> oRegisteredRequest;

	private Map<String, String> oNameDictionary;
	private Map<String, String> oRegisteredObjectsNew;
	private Map<String, String> oRegisteredObjectsOld;
	private JWebHistoryManager oHistoryManager;

	// Cache for large or server-only objects
	private static final String CACHE_PREFIX = "cache:";
	private static final long CACHE_EXPIRE_SECONDS = Long.getLong("jwebrequest.cache.expireMinutes", 10L) * 60L;

	/**
	 * Lightweight snapshot of the request's registered objects and history manager.
	 * It is primarily used to serialize the server-side state back to the client
	 * when needed.
	 */
	public class JWebRequestPackage {
		private Map<String, String> localRegisteredObject;
		private JLocalHistoryManager localHistoryManager;

//		@Override
//	  public String toString() {
//	    try {
//				return  getRegisterObjectsSerialized();
//			} catch (Exception e) {
//				e.printStackTrace();
//				return "error";
//			}
//	  }

		public String getRegisterObjectsSerialized() throws Exception {
			return buildOutgoingDictionary(readHoldsFromRequest());
		}
	}

	JWebRequestPackage pack = new JWebRequestPackage();

	public JWebRequestPackage getPack() {
		return pack;
	}

	// global data to process the request
	private JWebApplicationSession oSession;
	private JWebUICoordinator oUICoordinator;
	private boolean isAjax = false;
	private String language = null;
	private String requestid;
	private long idRequestJS;

	// the data which came from the request
	private JWebActionDataBundle oDataBundle;

	public JWebRequest(Request zServletRequest, JWebActionRequestProcessor zProcessor) {
		language = zServletRequest.getLocale().getLanguage();
		this.oServletRequest = zServletRequest;
		this.oProcessor = zProcessor;
		JWebActionFactory.CURRENT_REQUEST.set(this);
		try {
			attachToRunningThread();
		} catch (Exception e) {
			PssLogger.logError(e);
		}
	}

	public void setEncoding(String encoding) throws Exception {
		try {
			this.oServletRequest.setCharacterEncoding(encoding);
		} catch (UnsupportedEncodingException e) {
			PssLogger.logError(e, "Error while tyring to establish encoding: " + encoding);
		}
	}

	//
	// a typical request API
	//

	@SuppressWarnings("unchecked")
	public JIterator<String> getAllArgumentNames() {
		return JCollectionFactory.<String>createIterator(this.oServletRequest.getParameterNames());
	}

	public String getURI() {
		return this.oServletRequest.getRequestURI();
	}

	public String getURIok() {
		return this.getURI().substring(1, this.getURI().indexOf("/", 1));
	}

	public Object get(String zArgument) throws Exception {
		return this.oServletRequest.get(zArgument);
	}

	public String getArgument(String zArgument) throws Exception {
		return this.oServletRequest.getParameter(zArgument);
	}

	public String getHeader(String zHeaderName) throws Exception {
		return this.oServletRequest.getHeader(zHeaderName);
	}

	public Request getServletRequest() {
		return this.oServletRequest;
	}

	public JWebActionRequestProcessor getProcessor() {
		return this.oProcessor;
	}

	public void setResult(JWebAction zResultAction) {
		this.oResult = zResultAction;
	}

	public JWebAction getResult() {
		return this.oResult;
	}

	public void setObjectMap(Map<String, Object> zMap) {
		this.oObjectMap = zMap;
	}

	public void addModelObject(String zKey, Object zObject) {
		this.oObjectMap.put(zKey, zObject);
	}

	public Object getModelObject(String zKey) {
		return this.oObjectMap.get(zKey);
	}

	public String getRequestid() {
		return requestid;
	}

	public void setRequestid(String requestid) {
		this.requestid = requestid;
	}

//	public boolean parentIsModal() throws Exception {
//		return getSession().getHistoryManager().getLastLastHistory().isJSModal();
//	}
	public boolean parentIsModal() throws Exception {

		JAct thisAct = getHistoryManager().getLastSubmit();
		if (thisAct.isExitOnOk()) {
			return hasBackToModal();
//  		JHistory last = getSession().getHistoryManager().getLastLastHistory();
//  		if (last==null) return false;
//  		return last.getMainSumbit().getActionSource().isModal();
		} else {
			return thisAct.getActionSource().isModal();
		}
	}

	public long getLevel() throws Exception {
		return getHistoryManager().sizeHistory();
	}

	private JWebActionDataBundle getDataBundle() {
		if (this.oDataBundle == null) {
			this.oDataBundle = new JWebActionDataBundle();
		}
		return this.oDataBundle;
	}

	public long getIdRequestJS() {
		return idRequestJS;
	}

	public void setIdRequestJS(long version) {
		this.idRequestJS = version;
	}

	public JWebActionData getFormData(String zFormId) throws Exception {
		return this.getData((zFormId + "_fd"));
	}

	public JMap<String, JWebActionData> getAllFormData(String zFormId) throws Exception {
		JIterator<String> oRawArgsIt = this.getAllArgumentNames();
		JMap<String, JWebActionData> map = JCollectionFactory.createMap();
		String procesados = "";
		String sArgName;
		while (oRawArgsIt.hasMoreElements()) {
			sArgName = oRawArgsIt.nextElement();
			if (sArgName.indexOf(zFormId) == -1)
				continue;
			int pos1 = sArgName.indexOf(zFormId);
			int pos2 = sArgName.indexOf("_fd");
			int pos3 = sArgName.indexOf("provider");
			if (pos1 == -1)
				continue;
			if (pos2 == -1)
				continue;
			boolean withProvider = pos3 != -1 && pos3 < pos2;
			String name = (sArgName.substring(withProvider ? pos3 : pos1, pos2));
			String search = (withProvider ? (sArgName.substring(pos1, pos2)) : name) + "_fd";
			if (map.getElement(name) != null)
				continue;
			JWebActionData data = this.getData(search);
			map.addElement(name, data);
		}
		return map;
	}

	public void addDataBundle(String zDataId, String zData) throws Exception {
		JWebActionData oData = null;
		oData = this.getDataBundle().addData(zDataId);
		JWebActionFactory.parseActionData(oData, zData, true);
	}

	public JWebActionData getData(String zDataId) throws Exception {
		JWebActionData oData = null;
		// first, look up into the bundle
		if (this.oDataBundle != null) {
			oData = this.getDataBundle().getData(zDataId);
			if (oData != null) {
				return oData;
			}
		}
		// then, look up in the URL for an argument indicating the requested
		// data
		String sDataURL = this.getArgument("dg_" + zDataId);
		if (sDataURL != null && sDataURL.trim().length() > 0) {
			oData = this.getDataBundle().addData(zDataId);
			JWebActionFactory.parseActionData(oData, sDataURL, true);
		}
		// finally, look up in many data field arguments in the URL
		else {
			oData = JWebActionFactory.parseActionDataFields(this, zDataId, true);
			if (oData != null) {
				this.getDataBundle().addData(oData);
			}
		}
		if (oData == null) {
			oData = JWebActionData.NULL;
		}
		//
		return oData;
	}

	@Override
	public String toString() {
		StringBuffer oResult = new StringBuffer();
		oResult.append("URI: ").append(this.oServletRequest.getRequestURI()).append("\narguments:");
		try {
			JIterator<String> oAllArgsIt = this.getAllArgumentNames();
			String sArg;
			while (oAllArgsIt.hasMoreElements()) {
				sArg = oAllArgsIt.nextElement();
				oResult.append("\n  ").append(sArg).append(" = ").append(this.getArgument(sArg));
			}
		} catch (Exception e) {
		}
		return oResult.toString();
	}

	//
	// request-based global data management
	//
//	static int conex=0;

	public synchronized void attachToRunningThread() throws Exception {
//		PssLogger.logInfo("---------------------------------------------------------------------> conexiones open: "+conex);
//		 JWebActionFactory.CURRENT_REQUEST.set(this);
		if (getSession() != null)
			getSession().attachGlobalsToCurrentRequest();
//		conex++;
//		this.setSession(zSession);
	}

	public synchronized void attachToRunningThreadWhitinBase() throws Exception {
//		PssLogger.logInfo("---------------------------------------------------------------------> conexiones open: "+conex);
//		JWebActionFactory.CURRENT_REQUEST.set(this);
		if (getSession() != null)
			getSession().attachGlobalsToCurrentRequestWithinBase();
//		conex++;
//		this.setSession(zSession);
	}

//
	public synchronized void detachFromRunningThread() throws Exception {
//		this.setSession(null);
		JWebApplicationSession.detachGlobals();
		JWebActionFactory.CURRENT_REQUEST.set(null);
//		conex--;
//		PssLogger.logInfo("---------------------------------------------------------------------> conexiones close: "+conex);

	}

	public JWebApplicationSession getSession() {
		if (oSession == null) {
			oSession = (JWebApplicationSession) this.oServletRequest.getAttribute("session");
		}
		return this.oSession;
	}

	public boolean hasSession() {
		return this.oSession != null;
	}

	public JWebUICoordinator getUICoordinator() throws Exception {
		if (this.oUICoordinator == null) {
			if (this.getSession() == null) {
				this.oUICoordinator = new JFrontDoorUICoordinator(); // a new
				// volatile
				// coordinator
				this.oUICoordinator.initialize(this);
				this.oUICoordinator.refresh(this);
			} else {
				this.oUICoordinator = this.getSession().getUICoordinator();
				this.oUICoordinator.refresh(this);
			}
		}
		return this.oUICoordinator;
	}

//	public void setSession(JWebApplicationSession zSession) throws Exception {
//
//		if (this.oSession!=null && zSession!=null)
//			throw new RuntimeException("Request has already a session set");
//
////		if (zSession!=null) {
////			zSession.attachToCurrentRequest();
////		}
//		this.oSession=zSession;
//		this.oUICoordinator=null; // to update it
//	}
	public boolean hasSelectedCell() throws Exception {
		return !isArgumentEmpty(getArgument(JWebActionFactory.ACTION_DATA_PREFIX + "cell_select"));
	}

	public String getSelectedCell() throws Exception {
		return getArgument(JWebActionFactory.ACTION_DATA_PREFIX + "cell_select");
	}

	public String getNoNeedLoad() throws Exception {
		return getArgument(JWebActionFactory.ACTION_DATA_PREFIX + "no_needload");
	}

	public boolean hasMultipleObjectOwnerList() throws Exception {
		String hasMultiList = getArgument(JWebActionFactory.ACTION_DATA_PREFIX + "is_multiple_owner");
		if (this.isArgumentEmpty(hasMultiList))
			return false;
		return hasMultiList.equals("true");
	}

	public boolean hasBackToModal() throws Exception {
		String hasBackModal = getArgument(JWebActionFactory.ACTION_DATA_PREFIX + "back_modal");
		if (this.isArgumentEmpty(hasBackModal))
			return false;
		return hasBackModal.equals("true");
	}

	public String getMultipleObjectOwnerList() throws Exception {
		return getArgument(JWebActionFactory.ACTION_DATA_PREFIX + "multiple_owner_list");
	}

	public String getMultipleObjectOwnerListDest() throws Exception {
		return getArgument(JWebActionFactory.ACTION_DATA_PREFIX + "multiple_owner_list_dest");
	}

	public boolean hasPssObjectOwner() throws Exception {
		return !isArgumentEmpty(getArgument(JWebActionFactory.ACTION_DATA_PREFIX + "object_owner"));
	}

	public boolean hasPssObjectOwnerDest() throws Exception {
		return !isArgumentEmpty(getArgument(JWebActionFactory.ACTION_DATA_PREFIX + "object_owner_dest"));
	}

	public boolean hasPssObjectOwnerContext() throws Exception {
		return !isArgumentEmpty(getArgument(JWebActionFactory.ACTION_DATA_PREFIX + "object_context"));
	}

	public boolean hasPssClearSelect() throws Exception {
		return !isArgumentEmpty(getArgument(JWebActionFactory.ACTION_DATA_PREFIX + "clear_select"));
	}

	public boolean hasPssObjectSelect() throws Exception {
		return !isArgumentEmpty(getArgument(JWebActionFactory.ACTION_DATA_PREFIX + "object_select"));
	}

	public boolean hasPssIdWin() throws Exception {
		return !isArgumentEmpty(getArgument(JWebActionFactory.ACTION_DATA_PREFIX + "id_win"));
	}

	public String getRowSelected() throws Exception {
		String pssObjectOwner = getArgument(JWebActionFactory.ACTION_DATA_PREFIX + "row_select");
		return pssObjectOwner;
	}

	public String getPssObjectOwner() throws Exception {
		String pssObjectOwner = getArgument(JWebActionFactory.ACTION_DATA_PREFIX + "object_owner");
		return pssObjectOwner;
	}

	public String getPssObjectOwnerDest() throws Exception {
		String pssObjectOwner = getArgument(JWebActionFactory.ACTION_DATA_PREFIX + "object_owner_dest");
		return pssObjectOwner;
	}

	public String getPssObjectOwnerContext() throws Exception {
		String pssObjectOwner = getArgument(JWebActionFactory.ACTION_DATA_PREFIX + "object_owner_context");
		if (pssObjectOwner == null || pssObjectOwner.equals(""))
			return null;
		return pssObjectOwner;
	}

	public String getPssObjectSelect() throws Exception {
		String pssObjectOwner = getArgument(JWebActionFactory.ACTION_DATA_PREFIX + "object_select");
		return pssObjectOwner;
	}

	public String getPssIdDictionary() throws Exception {
		String pssDict = getArgument(JWebActionFactory.ACTION_DATA_PREFIX + "dictionary");
		return pssDict;
	}

	public boolean getClearSelect() throws Exception {
		boolean pssObjectOwner = getArgument(JWebActionFactory.ACTION_DATA_PREFIX + "clear_select").equals("1");
		return pssObjectOwner;
	}

//	public String getIdAction() throws Exception {
//		String a=getArgument(JWebActionFactory.ACTION_DATA_PREFIX+"action");
//		return a;
//	}
	public String getStadistics() throws Exception {
		String std = getArgument(JWebActionFactory.ACTION_DATA_PREFIX + "stadistics");
		if (std == null)
			return "";
		return std;
	}

	public String getAjaxContainer() throws Exception {
		String Scroller = getArgument(JWebActionFactory.ACTION_DATA_PREFIX + "ajaxcontainer");
		if (Scroller == null)
			return "";
		return Scroller;
	}

	public String getScroller() throws Exception {
		String Scroller = getArgument(JWebActionFactory.ACTION_DATA_PREFIX + "scroller");
		if (Scroller == null)
			return "";
		return Scroller;
	}

	public boolean isMobile() {
		try {
			String mobile = getArgument("is_mobile");
			if (mobile == null) {
				if (getURI().indexOf("mobile-do") != -1)
					return true;
				return false;
			}
			return mobile.equals("Y");
		} catch (Exception e) {
			return false;
		}
	}

	public String getMobileUUID() throws Exception {
		return getArgument("mobile_uuid");
	}

	public String getMobileType() throws Exception {
		return getArgument("mobile_type");
	}

	public boolean inModal() throws Exception {
		String modal = getArgument(JWebActionFactory.ACTION_DATA_PREFIX + "is_modal");
		if (modal == null)
			return false;
		return modal.equals("Y");
	}

	public String getSourceControId() throws Exception {
		String sourceControl = getArgument(JWebActionFactory.ACTION_DATA_PREFIX + "source_control_id");
		if (sourceControl == null)
			return "";
		int pos = sourceControl.lastIndexOf("fd-");
		if (pos == -1)
			return "";
		return sourceControl.substring(pos + 3);
	}

	// public String getPssIdAction() throws Exception {
	// String pssIdAction =
	// getArgument(JWebActionFactory.ACTION_DATA_PREFIX+"id_action");
	// return pssIdAction;
	// }
	// public String getPssActionOwner() throws Exception {
	// String pssIdAction =
	// getArgument(JWebActionFactory.ACTION_DATA_PREFIX+"act_owner");
	// return pssIdAction;
	// }

	public String getTableProvider() throws Exception {
		return getArgument(JWebActionFactory.ACTION_DATA_PREFIX + "table_provider");
	}

	public boolean isParcial() throws Exception {
		return getArgument(JWebActionFactory.ACTION_DATA_PREFIX + "partial_info") != null;
	}

	public boolean hasTableProvider() throws Exception {
		return !isArgumentEmpty(getTableProvider());
	}

	public boolean isArgumentEmpty(String zRequestArgument) {
		if (zRequestArgument == null)
			return true;
		if (zRequestArgument.toUpperCase().equals("NULL"))
			return true;
		if (zRequestArgument.toUpperCase().equals("UNDEFINED"))
			return true;
		if (zRequestArgument.trim().length() == 0)
			return true;
		return false;
	}

	public boolean hasContainerDimension() throws Exception {
		String height = getArgument(JWebActionFactory.ACTION_DATA_PREFIX + "container_height");
		return !isArgumentEmpty(height);
	}

	public int parseMeasureUnit(String value) throws Exception {
		String temp = value.replaceAll("px", "");
		return Integer.parseInt(temp);
	}

	public int getContainerHeight() throws Exception {
		String height = getArgument(JWebActionFactory.ACTION_DATA_PREFIX + "container_height");
		if (height == null)
			return 0;
		if (height.equals("NaN"))
			return 0;
		return parseMeasureUnit(height);
	}

	public int getContainerWidth() throws Exception {
		String width = getArgument(JWebActionFactory.ACTION_DATA_PREFIX + "container_width");
		if (width == null)
			return 0;
		if (width.equals("NaN"))
			return 0;
		return parseMeasureUnit(width);
	}

	public String getComboId() throws Exception {
		return getArgument(JWebActionFactory.ACTION_DATA_PREFIX + "combo_id");
	}

	public String getComboFormLovId() throws Exception {
		return getArgument(JWebActionFactory.ACTION_DATA_PREFIX + "comboFormLov_id");
	}

	public String getIdWin() throws Exception {
		return getArgument(JWebActionFactory.ACTION_DATA_PREFIX + "id_win");
	}

//	public boolean isHistoryAction() throws Exception {
//		if (hasSession()) {
//			if (getSession().getHistoryManager().getLastAction()!=null) {
//				return getSession().getHistoryManager().getLastAction().isHistoryAction();
//			}
//		}
//		return false;
//	}

	public boolean isAjax() {
		return isAjax;
	}

	public void setAjax(boolean isAjax) {
		this.isAjax = isAjax;
	}

	public String getBrowserLanguage() {
		return language;
	}

	public JMap<String, JWebActionData> getFilters() throws Exception {
		return this.getAllFormData("filter_pane");
	}
//	private void cleanUpGlobalData() throws Exception {
//		BizUsuario.SetGlobal(null);
//		JAplicacion.SetApp(null);
//		JBDatos.SetGlobal(null);
//	}

	public boolean hasNavigation() throws Exception {
		return !this.getData("win_list_nav").isNull();
	}

	public JWebActionData getNavigation() throws Exception {
		return this.getData("win_list_nav");
	}

	public Map<String, String> getRegisteredObjectsOld() {
		if (oRegisteredObjectsOld == null) {
			oRegisteredObjectsOld = (Map<String, String>) this.oServletRequest.getAttribute("registeredObjectsOld");
			if (oRegisteredObjectsOld == null) {
				oRegisteredObjectsOld = new HashMap<String, String>();
				this.oServletRequest.setAttribute("registeredObjectsOld", oRegisteredObjectsOld);
			}

		}
		return oRegisteredObjectsOld;
	}

	public Map<String, String> getRegisteredObjectsNew() {
		if (oRegisteredObjectsNew == null) {
			oRegisteredObjectsNew = (Map<String, String>) this.oServletRequest.getAttribute("registeredObjectsNew");
			if (oRegisteredObjectsNew == null) {
				oRegisteredObjectsNew = new HashMap<String, String>();
				this.oServletRequest.setAttribute("registeredObjectsNew", oRegisteredObjectsNew);
			}

		}
		return oRegisteredObjectsNew;
	}

	private String reuseIfPresent(String key) {
		String oldVal = getRegisteredObjectsOld().get(key);
		if (oldVal != null) {
			getRegisteredObjectsNew().put(key, oldVal);
			return key;
		}
		return null;
	}

	private static String sha256(byte[] data) throws Exception {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		byte[] digest = md.digest(data);
		StringBuilder sb = new StringBuilder();
		for (byte b : digest) {
			sb.append(String.format("%02x", b));
		}
		return sb.toString();
	}

	private Object fetchFromCache(String handle) {
		try {
			byte[] data = CacheProvider.get().getBytes(handle);
			return deserializeObjectFromBytes(data);
		} catch (Exception e) {
			return null;
		}
	}

	public Object getRegisteredRequestObject(String key) {
		return this.getRegisteredRequest().getElement(key);
	}

	public Object setRegisteredRequestObject(String key, Object obj) {
		return this.getRegisteredRequest().addElement(key, obj);
	}

	public JMap<String, Object> getRegisteredRequest() {
		if (oRegisteredRequest == null) {
			oRegisteredRequest = (JMap<String, Object>) this.oServletRequest.getAttribute("registerRequest");
			if (oRegisteredRequest == null) {
				oRegisteredRequest = JCollectionFactory.createMap();
				this.oServletRequest.setAttribute("registerRequest", oRegisteredRequest);
			}

		}
		return oRegisteredRequest;

	}

	private boolean isLargeObject(Object obj) throws Exception {
//		if (obj instanceof JBaseWin) {
//			return ((JBaseWin) obj).canConvertToURL();
//		}
		// Placeholder for real implementation or marker interface check
		return true;
	}

	public static void purgeExpiredCache() {
		// No-op; local cache manages its own eviction
	}

	public synchronized String registerObjectObj(Serializable zObject) throws Exception {
		return registerObjectObj(zObject, false);
	}

	public synchronized String registerObjectTemp(Serializable zObject) throws Exception {
		return registerObjectObj(zObject, true);
	}

	public synchronized String registerObjectObj(Serializable zObject, boolean temp) throws Exception {
		if (temp && zObject instanceof JBaseWin)
			return registerWinObjectObj((JBaseWin) zObject);
		if (isLargeObject(zObject)) {
			String id = "obj_c_" + UUID.randomUUID().toString();
			CacheProvider.get().putBytes(id, serializeObjectToBytes(zObject), CACHE_EXPIRE_SECONDS);
			getRegisteredObjectsNew().put(id, CACHE_PREFIX + id);
			return id;
		}
		String payload = serializeObject(zObject);
		String id = "obj_p_" + sha256(JTools.stringToByteArray(payload));
		if (reuseIfPresent(id) != null)
			return id;
		this.getRegisteredObjectsNew().put(id, payload);
		return id;
	}

	Map<String, String> objectsCreated = new HashMap<String, String>();

	public synchronized String registerWinObjectObj(JBaseWin zObject) throws Exception {
		if (objectsCreated.containsKey(zObject.getUniqueId()))
			return objectsCreated.get(zObject.getUniqueId());
		if (isLargeObject(zObject)) {
			String id = zObject.getUniqueId() != null ? zObject.getUniqueId() : UUID.randomUUID().toString();
			CacheProvider.get().putBytes(id, serializeObjectToBytes(zObject), CACHE_EXPIRE_SECONDS);
			String out = CACHE_PREFIX + id;
			getRegisteredObjectsNew().put(id, out);
			objectsCreated.put(zObject.getUniqueId(), out);
			return out;
		}
		String packed = new JWinPackager(null).baseWinToJSON(zObject);
		String out = "obj_t_" + packed;
		objectsCreated.put(zObject.getUniqueId(), out);
		return out;
	}

	public synchronized String registerRecObjectObj(JBaseRecord zObject) throws Exception {
		if (zObject == null)
			return null;
		String key = zObject.getUniqueId();
		if (reuseIfPresent(key) != null)
			return key;
		if (isLargeObject(zObject)) {
			CacheProvider.get().putBytes(key, serializeObjectToBytes(zObject), CACHE_EXPIRE_SECONDS);
			getRegisteredObjectsNew().put(key, CACHE_PREFIX + key);
			return key;
		}
		String packed = new JWinPackager(null).baseRecToJSON(zObject);
		String payload = "obj_rec_" + packed;
		getRegisteredObjectsNew().put(key, payload);
		return key;
	}

	public Serializable getRegisterObject(String key) {
		String obj = getRegisteredObjectsOld().get(key);
		if (obj == null) {
			PssLogger.logError("Falta objeto");
			return null;
		}
		try {
			if (obj.startsWith(CACHE_PREFIX)) {
				byte[] data = CacheProvider.get().getBytes(obj.substring(CACHE_PREFIX.length()));
				return (Serializable) deserializeObjectFromBytes(data);
			}
			if (obj.startsWith("obj_t:")) {
				return (Serializable) fetchFromCache(obj.substring(6));
			}
			if (obj.startsWith("obj_rec:")) {
				return (Serializable) fetchFromCache(obj.substring(8));
			}
			if (obj.startsWith("obj_t_")) {
				String payload = obj.substring(6);
				byte[] raw = JWinPackager.inflate(Base64.getDecoder().decode(payload));
				String json = JTools.byteVectorToString(raw);
				return new JWinPackager(new JWebWinFactory(null)).jsonToBaseWin(json);
			}
			if (obj.startsWith("obj_rec_")) {
				String payload = obj.substring(8);
				byte[] raw = JWinPackager.inflate(Base64.getDecoder().decode(payload));
				String json = JTools.byteVectorToString(raw);
				return new JWinPackager(new JWebWinFactory(null)).jsonToBaseRec(json);
			}
			return deserializeObject(obj);
		} catch (Exception e) {
			PssLogger.logError(e);
			return null;
		}
	}

//	public synchronized String registerObject(JBaseWin zBaseWin) throws Exception {
//		if (zBaseWin == null)
//			return null;
//		String key = zBaseWin.getUniqueId();
//		if (reuseIfPresent(key) != null)
//			return key;
//		String payload = baseWinToSession(zBaseWin);
//		getRegisteredObjectsNew().put(key, payload);
//		return key;
//	}
//	public synchronized String registerObject(String pos, JBaseWin zBaseWin) throws Exception {
//		if (zBaseWin == null)
//			return null;
//		if (reuseIfPresent(pos) != null)
//			return pos;
//		this.getRegisteredObjectsNew().put(pos, baseWinToSession(zBaseWin));
//		return pos;
//	}
	public synchronized String registerObject(JBaseWin zBaseWin) throws Exception {
		if (zBaseWin == null)
			return null;
		return registerWinObjectObj(zBaseWin);
	}

	public synchronized String registerObject(String pos, JBaseWin zBaseWin) throws Exception {
		if (zBaseWin == null)
			return null;
		if (reuseIfPresent(pos) != null)
			return pos;
		this.getRegisteredObjectsNew().put(pos, registerWinObjectObj(zBaseWin));
		return pos;
	}

	public static class ReconcileResult {
		public java.util.List<String> keep = new java.util.ArrayList<>();
		public java.util.List<String> evict = new java.util.ArrayList<>();
	}

	public ReconcileResult reconcileDictionaries(java.util.List<String> holds) {
		ReconcileResult rr = new ReconcileResult();
		Map<String, String> oldMap = getRegisteredObjectsOld();
		Map<String, String> newMap = getRegisteredObjectsNew();
		rr.keep.addAll(newMap.keySet());
		for (String k : oldMap.keySet()) {
			if (!newMap.containsKey(k)) {
				if (holds == null || !holds.contains(k)) {
					rr.evict.add(k);
				}
			}
		}
		return rr;
	}

	public String buildOutgoingDictionary(java.util.List<String> holds) throws Exception {
		ReconcileResult rr = reconcileDictionaries(holds);
		for (String k : rr.evict) {
			String val = getRegisteredObjectsOld().get(k);
			if (val != null) {
				if (val.startsWith(CACHE_PREFIX)) {
					CacheProvider.get().delete(val.substring(CACHE_PREFIX.length()));
				} else if (val.startsWith("obj_t:")) {
					invalidateHandle(val.substring(6));
				} else if (val.startsWith("obj_rec:")) {
					invalidateHandle(val.substring(8));
				}
			}
		}
		pack.localHistoryManager = getHistoryManager().serializeHistoryManager();
		pack.localRegisteredObject = getRegisteredObjectsNew();
		return serializeRegisterJSON(pack);
	}

	private void invalidateHandle(String handle) {
		try {
			CacheProvider.get().delete(handle);
		} catch (Exception e) {
		}
	}

	@SuppressWarnings("unchecked")
	private java.util.List<String> readHoldsFromRequest() throws Exception {
		String payload = getArgument("dg_holds");
		if (payload == null || payload.isEmpty())
			return null;
		com.google.gson.Gson gson = new com.google.gson.Gson();
		return gson.fromJson(payload, java.util.List.class);
	}

	static long idDictionaryGenerator = 0;

	public Map<String, String> getNameDictionary() {
		if (oNameDictionary == null) {
			oNameDictionary = (Map<String, String>) this.oServletRequest.getAttribute("nameDictionary");
			if (oNameDictionary == null) {
				oNameDictionary = new HashMap<String, String>();
				this.oServletRequest.setAttribute("nameDictionary", oNameDictionary);
			}
		}
		return oNameDictionary;

	}

	public JWebHistoryManager getHistoryManager() {
		if (oHistoryManager == null) {
			oHistoryManager = (JWebHistoryManager) this.oServletRequest.getAttribute("historyManager");
			if (oHistoryManager == null) {
				oHistoryManager = new JWebHistoryManager(JWebActionFactory.getCurrentRequest().getSession());
				this.oServletRequest.setAttribute("historyManager", oHistoryManager);
			}
		}
		return oHistoryManager;

	}

	static long id = 0;

	public String getNameDictionary(String s) {
//		if (s.indexOf("anonimus_") != -1)
		return s;
//		String k = getNameDictionary().get(s);
//		if (k != null)
//			return k;
//		String key = "k" + (id++) + "";
//		getNameDictionary().put(s, key);
//		return key;
	}

	public String getNameDictionary(long id) {
		String key = "k" + id;
		Iterator<String> it = getNameDictionary().keySet().iterator();
		while (it.hasNext()) {
			String tkey = it.next();
			String tval = getNameDictionary().get(tkey);
			if (!tval.equals(key))
				continue;
			return tkey;
		}

		return null;
	}

	boolean restored = false;

	public void winRegisteredObjects(JWebWinFactory factory, boolean preserve, boolean resetObjects) {
		try {
			if (restored)
				return;
			restored = true;
			getRegisteredObjectsNew().clear();
			String dictionary = getPssIdDictionary();
			if (dictionary == null || dictionary.isEmpty())
				return;
			pack = (JWebRequestPackage) deserializeRegisterJSON(dictionary);
			oRegisteredObjectsOld = pack.localRegisteredObject;
			if (getHistoryManager().sizeHistory() == 0) {
				getHistoryManager().deserializeHistoryManager(pack.localHistoryManager);
				factory.fillHistory();
			}
			if (preserve)
				getRegisteredObjectsNew().putAll(pack.localRegisteredObject);
			JWebApplication app = JWebServer.getInstance().getWebApplication(null);
			app.getStadistics().addInfoSession(getSession());
		} catch (Exception e) {
			PssLogger.logError(e);
		}

	}

	public String serializeRegisterMapJSON(Map<String, String> pack) {
		Gson gson = new Gson();
		String serializedMap = gson.toJson(pack);
		return Base64.getEncoder().encodeToString(JTools.stringToByteArray(serializedMap));
	}

	public Map<String, String> deserializeRegisterMapJSON(String serializedDictionary) {
		Map<String, String> map = new TreeMap<String, String>();
		Gson gson = new Gson();
		Type type = new TypeToken<Map<String, String>>() {
		}.getType();
		map = gson.fromJson(JTools.byteVectorToString(Base64.getDecoder().decode(serializedDictionary)), type);
		return map;
	}

	public String serializeRegisterJSON(JWebRequestPackage pack) {
		Gson gson = new Gson();
		String serializedMap = gson.toJson(pack);
		return Base64.getEncoder().encodeToString(JTools.stringToByteArray(serializedMap));
	}

	public JWebRequestPackage deserializeRegisterJSON(String serializedDictionary) {
		JWebRequestPackage map = new JWebRequestPackage();
		Gson gson = new Gson();
		Type type = new TypeToken<JWebRequestPackage>() {
		}.getType();
		map = gson.fromJson(JTools.byteVectorToString(Base64.getDecoder().decode(serializedDictionary)), type);
		return map;
	}

	public static String serializeObject(Serializable obj) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(obj);
		oos.close();
		return Base64.getEncoder().encodeToString(baos.toByteArray());
	}

	public static byte[] serializeObjectToBytes(Serializable obj) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(obj);
		oos.close();
		return baos.toByteArray();
	}

	public static Serializable deserializeObject(String serializedObj) {
		try {
			if (serializedObj == null)
				return null;
			byte[] data = Base64.getDecoder().decode(serializedObj);
			ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
			Serializable obj = (Serializable) ois.readObject();
			ois.close();
			return obj;
		} catch (ClassNotFoundException e) {
			PssLogger.logError(e);
			e.printStackTrace();
		} catch (IOException e) {
			PssLogger.logError(e);
		}
		return null;
	}

	public static Serializable deserializeObjectFromBytes(byte[] data) {
		if (data == null)
			return null;
		try {
			ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
			Serializable obj = (Serializable) ois.readObject();
			ois.close();
			return obj;
		} catch (Exception e) {
			PssLogger.logError(e);
			return null;
		}
	}

	public static String baseWinToSession(JBaseWin zOwner) throws Exception {
//	  if (!zOwner.canConvertToURL()) return serializeObject(zOwner);
//		if (zOwner.hasDropListener()) return serializeObject(zOwner);
//		if (zOwner.hasSubmitListener()) return serializeObject(zOwner);
//		if (zOwner.isModeWinLov()) return serializeObject(zOwner);
//		if (!zOwner.isReaded()) return serializeObject(zOwner);

		String packed = new JWinPackager(null).baseWinToJSON(zOwner);
		return "obj_t_" + packed;
	}

}
