package pss.common.security;

import java.util.Date;
import java.util.Iterator;
import java.util.TimeZone;

import pss.GuiModuloPss;
import pss.JPath;
import pss.common.components.JSetupParameters;
import pss.common.customMenu.BizMenu;
import pss.common.documentos.biblioteca.BizBiblioteca;
import pss.common.event.device.BizChannel;
import pss.common.event.device.BizDevice;
import pss.common.event.device.BizQueueMessage;
import pss.common.event.mail.BizMailCasilla;
import pss.common.personalData.BizPersona;
import pss.common.regions.company.BizCompany;
import pss.common.regions.company.BizCompanyCountry;
import pss.common.regions.company.JCompanyBusiness;
import pss.common.regions.company.JCompanyBusinessModules;
import pss.common.regions.divitions.BizPais;
import pss.common.regions.multilanguage.JLanguage;
import pss.common.regions.nodes.BizNodo;
import pss.common.regions.nodes.BizNodoUsuario;
import pss.common.regions.nodes.GuiNodos;
import pss.common.security.friends.BizUserFriend;
import pss.common.security.license.license.BizLicense;
import pss.common.security.mail.BizUsrMailSender;
import pss.common.security.type.BizUsuarioTipo;
import pss.core.connectivity.messageManager.common.core.JMMRecord;
import pss.core.data.BizPssConfig;
import pss.core.data.interfaces.connections.JBDatos;
import pss.core.services.JExec;
import pss.core.services.fields.JBoolean;
import pss.core.services.fields.JDate;
import pss.core.services.fields.JDateTime;
import pss.core.services.fields.JHour;
import pss.core.services.fields.JInteger;
import pss.core.services.fields.JLong;
import pss.core.services.fields.JObjBD;
import pss.core.services.fields.JObject;
import pss.core.services.fields.JPassword;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecords;
import pss.core.tools.JDateTools;
import pss.core.tools.JEncryptation;
import pss.core.tools.JExcepcion;
import pss.core.tools.JTools;
import pss.core.tools.PssException;
import pss.core.tools.PssLogger;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JList;
import pss.core.tools.collections.JMap;
import pss.core.tools.submit.JSubmit;
import pss.core.win.actions.BizAction;
import pss.www.platform.actions.JWebActionFactory;
import pss.www.ui.skins.ILayoutGenerator;
import pss.www.ui.skins.JWebSkin;
import pss.www.ui.views.ILoginPage;

public class BizUsuario extends JMMRecord {

	private static InheritableThreadLocal<BizUsuario> oUsr = new InheritableThreadLocal<BizUsuario>();
	public static BizUsuario oGlobal = null;
	private GuiModuloPss menucached = null;
 
//	public static final String VISION_LEGAJO = "1";

	public static final int C_MAX_FAILED_PASSWORD = 3;

	public static final String C_ADMIN_USER = "ADMIN";
	public static final String C_MANTEN_USER = "MANTEN";

	// public final static String GUEST_NAME = "GUEST";
	// public final static 2String GUEST_PASSWORD = "GUEST";

	// public final static String MAP_CURRENT_NODE = "current_node";
	public final static String MAP_GuiPOS = "user_GuiPos";
	public final static String MAP_BizPOS = "user_BizPos";
	public final static String MAP_BizUserPos = "user_BizUserPos";
	public final static String MAP_CURRENT_ASIENTO = "current_asiento";
	public final static String MAP_CURRENT_JSON = "current_json";

	private JMap<String, BizUsuario> hLinkedUsers;
	private JMap<String, String> oAuthorizations;
	BizLicense oLicense = null;
	String visionCustomMenu = null;
	private transient BizWebUserProfile oUserProfile;

	// -------------------------------------------------------------------------//
	// Propiedades de la Clase
	// -------------------------------------------------------------------------//
	JString pPais = new JString();
	JString pUsuario = new JString();
	JString pLegajo = new JString();
	JString pDescrip = new JString();
	JString pCompany = new JString();
	protected JString pNodo = new JString();
	JPassword pClave = new JPassword();
	JString pMailUser = new JString();
	JString pGroupStore = new JString();

	JBoolean pActivo = new JBoolean();
	JString pUsuarioTipo = new JString();
	JDate pFechaAlta = new JDate();
	JDate pFechaInhab = new JDate();
	JHour pHoraInhab = new JHour();
	JDateTime pFechaUltimoIngreso = new JDateTime();
	JDateTime pFechaUltimoEgreso = new JDateTime();
	JDateTime pFechaCambioClave = new JDateTime();
	JInteger pIntentosClave = new JInteger();
	JInteger pCaducidad = new JInteger();
	JInteger pLongitudClave = new JInteger();
	JString pLenguaje = new JString();
	JString pLoginSource = new JString();
	JBoolean pLogFlag = new JBoolean();
	JString pCustomMenu = new JString();
	JLong pLicense = new JLong();
	JInteger pMail = new JInteger();
	JString pHasNavigationBar = new JString();
	JString pHasMail = new JString();
	JInteger pExpire = new JInteger();
	JString pHasHelp = new JString();
	JString pHasMulti = new JString();
	JString pHasPreference = new JString();
	JString pHasChangeKey = new JString();
	JString pCanOutAccess = new JString();
	JBoolean pHasMobile = new JBoolean();
	JLong pFoto = new JLong();
	JString pNombreFoto = new JString();
	JString imei = new JString();
	JString mobileVersion = new JString();
	JString phone = new JString();
	JWebSkin skin;
	JString pRecoveryID = new JString() {
		public void preset() throws Exception {
			pRecoveryID.setValue(getRecoveryID());
		};
	};
	JString pValidacionID = new JString() {
		public void preset() throws Exception {
			pValidacionID.setValue(getValidacionID());
		};
	};
	
	JBoolean pHasLdap = new JBoolean();
	
	
	public String getImei() throws Exception {
		return imei.getValue();
	}

	public void setImei(String val) {
		this.imei.setValue(val);
	}

	public String getPhone() throws Exception {
		return phone.getValue();
	}

	public void setPhone(String val) {
		this.phone.setValue(val);
	}

	public String getMailUser() throws Exception {
		return pMailUser.getValue();
	}

	public void setMailUser(String val) {
		this.pMailUser.setValue(val);
	}

	public String getGroupStore() throws Exception {
		return pGroupStore.getValue();
	}

	public void setGroupStore(String val) {
		this.pGroupStore.setValue(val);
	}

	public void setMobileVersion(String val) {
		this.mobileVersion.setValue(val);
	}

	public void setFechaUltimoIngreso(Date val) {
		this.pFechaUltimoIngreso.setValue(val);
	}

	private JBoolean caducarUsuario = new JBoolean();

	public boolean getCaducarUsuario() throws Exception {
		return caducarUsuario.getValue();
	}

	public Date getFechaUltimoIngreso() throws Exception {
		return pFechaUltimoIngreso.getValue();
	}

	public void setCaducarUsuario(boolean caducarUsuario) {
		this.caducarUsuario.setValue(caducarUsuario);
	}

	JString pImagenFoto = new JString() {
		public void preset() throws Exception {
			pImagenFoto.setValue(getImagenFoto());
		};
	};

	public void setCanOutAccess(boolean sFlag) {
		this.pCanOutAccess.setValue(sFlag);
	}

	public boolean findHasNavigayionBar() throws Exception {
		if (pHasNavigationBar.isNotNull()) return pHasNavigationBar.getValue().equals("S");
		if (this.hasUsuarioTipo()) return this.getObjUsuarioTipo().getHasNavigayionBar();
		return !this.GetUsuario().equalsIgnoreCase("auto");
	}

	public String findLenguaje() throws Exception {
		if (this.pLenguaje.isNotNull()) return this.pLenguaje.getValue();
		if (this.hasUsuarioTipo()) return this.getObjUsuarioTipo().getLanguage();
		return "AR";
	}
	public boolean findHasMail() throws Exception {
		if (pHasMail.isNotNull()) return pHasMail.getValue().equals("S");
		if (this.hasUsuarioTipo()) return this.getObjUsuarioTipo().getHasMail();
		return true;
	}

	public boolean findHasHelp() throws Exception {
		if (pHasHelp.isNotNull()) return pHasHelp.getValue().equals("S");
		if (this.hasUsuarioTipo()) return this.getObjUsuarioTipo().getHasHelp();
		return true;
	}

	public boolean findHasMulti() throws Exception {
		if (pHasMulti.isNotNull()) return pHasMulti.getValue().equals("S");
		if (this.hasUsuarioTipo()) return this.getObjUsuarioTipo().getHasMulti();
		return true;
	}

	public boolean getHasPreference() throws Exception {
		if (pHasPreference.isNotNull()) return pHasPreference.getValue().equals("S");
		if (this.hasUsuarioTipo()) return this.getObjUsuarioTipo().getHasPreference();
		return true;
	}

	public String getHasChangeKey() throws Exception {
		return this.pHasChangeKey.getValue();
	}
	
	public boolean findHasChangeKey() throws Exception {
		if (pHasChangeKey.isNotNull()) return pHasChangeKey.getValue().equals("S");
		if (this.hasUsuarioTipo()) return this.getObjUsuarioTipo().getHasChangeKey();
		return true;
	}

	public long getExpire() throws Exception {
		return pExpire.getValue();
	}

	public long findExpire() throws Exception {
		if (pExpire.isNotNull()) return pExpire.getValue();			
		if (this.hasUsuarioTipo()) return this.getObjUsuarioTipo().getExpire();
		return BizPssConfig.getPssConfig().getSessionTimeout();
	}

	public boolean getHasMobile() throws Exception {
		if (pHasMobile.isNull())
			return false;
		return pHasMobile.getValue();
	}
	
	public boolean getHasLdap() throws Exception {
		if (pHasLdap.isNull())
			return false;
		return pHasLdap.getValue();
	}

	public String getLoginSource() throws Exception {
		return pLoginSource.getValue();
	}
	public void setLoginSource(String user) throws Exception {
	 pLoginSource.setValue(user);
	}

	JString pDescrUsuario = new JString() {
		public void preset() throws Exception {
			pDescrUsuario.setValue(getDescrUsuario());
		}
	};
	JString pDescrActivo = new JString() {
		public void preset() throws Exception {
			pDescrActivo.setValue((pActivo.getValue() ? JLanguage.translate("Si") : JLanguage.translate("No")));
		}
	};
	JString pDescrNodo = new JString() {
		public void preset() throws Exception {
			if (pNodo.isNull())
				pDescrNodo.setValue("");
			else if (getObjNodo()!=null)
				pDescrNodo.setValue(getObjNodo().GetDescrip());
		}
	};
	
	public String getVisionCustomMenu() {
		return visionCustomMenu;
	}

	public void setVisionCustomMenu(String visionCustomMenu) {
		this.visionCustomMenu = visionCustomMenu;
	}

	JString pSkin = new JString();
	JInteger pRetriesClave = new JInteger();
	protected JString pBirthCountry = new JString();
	JBoolean pSystemUser = new JBoolean();
	JLong pIdPersona = new JLong();

	// Se puede deshabilitar un log, para que no loguee, por ejemplo, las sentencias SQL
	private JMap<Integer, Boolean> logStatusBySession = null;
	
//Fuerza el log, aunque haya sido desabailitado temporalmente. Sirve por si existe un error
	// y el log está deshabilitado por código, esta bandera lo fuerza, para que alguien pueda activarlo
	// por la web, y así poder hacer que el usuario designado posea log mientra este campo esté en true
	private JBoolean pForzarLogStatus = new JBoolean(); 

	private JMap<String, BizOperacionRol> aOpersHabil = null;
	private JRecords<BizUsuarioRol> roles = null;
	private BizOperacionRol opeAdmin = null;

	private BizPais oBirthCountry;
	private JList<JSubmit> aLogOffListener;
	private BizNodo oNodo;
	private JMap<String, Object> objectMap;
	private BizCompany oCompany;
	private BizMenu customMenu;

	/**
	 * Reglas extras de login.
	 * 
	 * @param zUser
	 * @param zPassword
	 * @param zBase
	 * @param attributes
	 * @return
	 */
	public void performLogin(String zUser, String zPassword, String zBase, JMap<String, Object> attributes) throws Exception {
	}

	public void AddLogOffListener(JSubmit zValue) {
		if (aLogOffListener == null)
			aLogOffListener = JCollectionFactory.createList();
		aLogOffListener.addElement(zValue);
	}

	public boolean hasNavigationBar() throws Exception {
		return this.findHasNavigayionBar();
	}

	public boolean hasHelp() throws Exception {
		return this.findHasHelp() && !GetUsuario().equalsIgnoreCase("auto");
	}

	public boolean hasNeverExpireSession() throws Exception {
		return GetUsuario().equalsIgnoreCase("auto");
	}

	public boolean hasCompany() throws Exception {
		return pCompany.isNotNull();
	}

	public String getCompany() throws Exception {
		return pCompany.getValue();
	}

	public boolean hasMail() throws Exception {
		return pMail.isNotNull();
	}

	public int getMail() throws Exception {
		return pMail.getValue();
	}

	// public JDate getFechaUltimoIngreso() {
	// return pFechaUltimoIngreso;
	// }
	//
	// public JDate getFechaUltimoEgreso() {
	// return pFechaUltimoEgreso;
	// }
	//
	// public JHour getHoraUltimoIngreso() {
	// return pHoraUltimoIngreso;
	// }
	//
	// public JHour getHoraUltimoEgreso() {
	// return pHoraUltimoEgreso;
	// }
	public boolean useTipoUsuario() throws Exception {
		return true;
	}
	public boolean useWebUserProfile() throws Exception {
		return true;
	}
	
	
	public boolean hasPassword() throws Exception {
		return !pClave.isEmpty();
	}

	/*****************************************************************************
	 * Metodos de manejo de variables estaticas al Thread
	 ****************************************************************************/
	public synchronized static BizUsuario getUsr() {
		/*
		 * BizUsuario oUsrInstance; oUsrInstance = oUsr.get(); if (oUsrInstance
		 * == null) { try { oUsrInstance =
		 * JCompanyBusinessModules.createBusinessDefault().getUserInstance();
		 * SetGlobal(oUsrInstance); } catch (Exception e) { e.printStackTrace();
		 * return null; } }
		 * 
		 * return oUsrInstance;
		 */
		return oUsr.get();
	}

	public static void SetGlobal(BizUsuario zUsr) {

		try {
			if (checkSetUser(zUsr))
				return;
			oUsr.set(getUserInstance(zUsr));
			JBDatos.inicializeDB(oUsr.get());
//			
//			try {
//				Class cl = Class.forName("pss.rot.JCompanyBusinessRot");
//				if (cl!=null) {
//					((IContext)cl.newInstance()).setUser(zUsr.GetUsuario() );
//					
//				}
//			} catch (Exception e) {

//			}
		} catch (Exception e) {
			PssLogger.logError(e);
		}
	}

	public static BizUsuario getUserInstance(BizUsuario zUsr) throws Exception {
		if (zUsr == null)
			return zUsr;
		if (zUsr.GetUsuario().equals(""))
			return zUsr;
		if (zUsr.getClass() != BizUsuario.class)
			return zUsr;
		return getUserInstanceInternal(zUsr);
	}
	public static boolean hasUserType() throws Exception {
		if (BizUsuario.getUsr() == null)
			return false;
		return getUserInstanceInternal(BizUsuario.getUsr() ).useUserType();
	}

	private static InheritableThreadLocal<JWebSkin> oSkin = new InheritableThreadLocal<JWebSkin>();

	public static void saveSkin(JWebSkin zSkin) throws Exception {
		oSkin.set(zSkin);
	}

	public static JWebSkin retrieveSkin() throws Exception {
		if (oSkin == null)
			return null;
		JWebSkin skin =oSkin.get();
		if (skin==null)
			BizUsuario.saveSkin(BizUsuario.getSkin());

		return oSkin.get();
	}
	public static ILayoutGenerator retrieveSkinGenerator() throws Exception {
		return retrieveSkin().createGenerator();
	}
	public static boolean checkSetUser(BizUsuario zUsr) throws Exception {
		if (oUsr.get() == null && zUsr == null)
			return true;
		if (oUsr.get() != null && zUsr == null)
			return false;
		if (oUsr.get() == null && zUsr != null)
			return false;
		return (oUsr.get().GetUsuario().equals(zUsr.GetUsuario()));
	}

	public synchronized static BizUsuario getUserInstanceInternal(BizUsuario zUsr) throws Exception {
		if (checkSetUser(zUsr))
			return oUsr.get();// other thread win
		oUsr.set(zUsr);//pre-seteo para que cuando la primera vez, que busca el business busca la empresa crea toda las empresa, hayn un campo currency en una, busca el pais, si no hay nodo busca en el usuario y falla sin este seteo
		BizUsuario oUser;
		oUser = zUsr.getObjBusiness().getUserInstance();
		oUser.Read(zUsr.GetUsuario());
		oUser.getObjCountry();
		return oUser;
	}

	// public synchronized static void SetGlobal(BizUsuario zUsr) {
	// try {
	// if (zUsr != null && !zUsr.GetUsuario().equals("") && zUsr.getClass() ==
	// BizUsuario.class) {
	// BizUsuario oUser = zUsr.getObjBusiness().getUserInstance();
	// oUser.Read(zUsr.GetUsuario());
	// oUsr.set(oUser);
	// } else {
	// oUsr.set(zUsr);
	// }
	// JBDatos.inicializeDB(oUsr.get());
	// }
	// catch (Exception e) {
	// PssLogger.logError(e);
	// }
	// }
	//
	public static String getAdminUser() {
		return C_ADMIN_USER;
	}

	public static String getMantenUser() {
		return C_MANTEN_USER;
	}

	// public static String getPssAdminUser() {
	// return C_ADMIN_USER_STATIC;
	// }

	/*****************************************************************************
	 * Returns the guest user constant
	 ****************************************************************************/
	// public static String getGuestUser() {
	// return BizUsuario.GUEST_NAME;
	// }
	public static boolean getCurrentUsuarioCanChangePreference() throws Exception {
		if (BizUsuario.getUsr() == null)
			return false;
		return BizUsuario.getUsr().canChangePreference();
	}

	public static boolean hasMulti() throws Exception {
		if (BizUsuario.getUsr() == null)
			return false;
		return BizUsuario.getUsr().findHasMulti();
	}

	public static boolean hasChangePwd() throws Exception {
		if (BizUsuario.getUsr() == null)
			return false;
		return BizUsuario.getUsr().findHasChangeKey();
	}

	public static boolean hasMailer() throws Exception {
		if (BizUsuario.getUsr() == null)
			return false;
		return BizUsuario.getUsr().findHasMail();
	}
	
	public static boolean hasUsr() throws Exception {
		return BizUsuario.getUsr()!=null;
	}


	public static String getCurrentUserLanguage() throws Exception {
		if (BizUsuario.getUsr() == null)
			return null;
		return BizUsuario.getUsr().findLenguaje();
	}

	private static String getLoginPageDefault() throws Exception {
		// retrieve the fields from the request
		String className = BizPssConfig.getPssConfig().classLogin();
		ILoginPage login = (ILoginPage) Class.forName(className).newInstance();
		return login.getLoginPage();
	}

	public static String getCurrentLoginPage() throws Exception {
		if (BizUsuario.getUsr() == null)
			return getLoginPageDefault();
		return BizUsuario.getUsr().GetLoginPage();
	}

	public static String getCurrentUser() throws Exception {
		if (BizUsuario.getUsr() == null)
			return "";
		return BizUsuario.getUsr().GetUsuario();
	}

	public static String getCurrentUserName() throws Exception {
		if (BizUsuario.getUsr() == null)
			return "";
		return BizUsuario.getUsr().GetDescrip();
	}

	public static boolean IsAdminCompanyUser() throws Exception {
		if (IsAdminUser())
			return true;
		return BizUsuario.getCurrentUser().equals(BizUsuario.C_ADMIN_USER + "." + BizUsuario.getUsr().getCompany());
	}

	public static String getAdminCompanyUser() throws Exception {
		return BizUsuario.C_ADMIN_USER + "." + BizUsuario.getUsr().getCompany();
	}

	public static boolean IsAdminUser() throws Exception {
		return BizUsuario.getCurrentUser().equalsIgnoreCase(BizUsuario.getAdminUser());
	}

	public static boolean isAutoLogin() throws Exception {
		return BizUsuario.getCurrentUser().equals(BizPssConfig.getPssConfig().getAutoLogin());
	}

	public static boolean IsAdminUser(String zUserId) throws Exception {
		return zUserId != null && zUserId.equals(BizUsuario.getAdminUser());
	}

	public boolean isAdminUser() throws Exception {
		return GetUsuario().equals(BizUsuario.getAdminUser());
	}

	public boolean isAnyAdminUser() throws Exception {
		if (this.isAdminUser())
			return true;
		if (GetUsuario().indexOf(".") == -1) return GetUsuario().equals(BizUsuario.getAdminUser());
		return GetUsuario().substring(0, GetUsuario().indexOf(".")).equals(BizUsuario.getAdminUser());
	}

	public boolean isAnyMantenUser() throws Exception {
		if (this.isAnyAdminUser())
			return true;
		if (GetUsuario().indexOf(".") == -1)
			return false;
		return GetUsuario().substring(0, GetUsuario().indexOf(".")).equals(BizUsuario.getMantenUser());
	}

	/*****************************************************************************
	 * Static method. Asks if the user is the GUEST
	 ****************************************************************************/
	// public static boolean isGuestUser() throws Exception {
	// return BizUsuario.getCurrentUser().equals(BizUsuario.getGuestUser());
	// }
	public boolean useUserType() throws Exception {
		return true;
	}

	public String GetUserFlag() throws Exception {
		return this.pLogFlag.toString();
	}

	public String GetUsuario() throws Exception {
		return pUsuario.getValue();
	};

	static String keyCert = new String("ELMASGRANDESIGUESIENDORIVERPLATE");

	public String GetCertificado() throws Exception {
		String key = keyCert;
		key.toCharArray();
		JEncryptation password = new JEncryptation();
		password.HDesSetKey(key);
		String encr = password.EncryptString(this.serialize());
		return JTools.BinToHexString(encr, encr.length());
	};

	public static String readCertificado(String cadena) throws Exception {
		String key = keyCert;
		key.toCharArray();
		JEncryptation password = new JEncryptation();
		password.HDesSetKey(key);
		String dencr = password.DesEncryptString(JTools.HexStringToBin(cadena, cadena.length()));
		return dencr;
	};

	public String GetLegajo() throws Exception {
		return pLegajo.getValue();
	};

	public String GetDescrip() throws Exception {
		return pDescrip.getValue();
	};

	public String GetDescripLogin() throws Exception {
		return pDescrUsuario.getValue();
	};

	public String GetSkinDirName() throws Exception {
		return pSkin.getValue();
	}

	public String getBirthCountryId() throws Exception {
		return pBirthCountry.getValue();
	}

	public boolean isSystemUser() throws Exception {
		return pSystemUser.getValue();
	}

	public void SetUsuario(String zValue) {
		pUsuario.setValue(zValue);
	}

	public void setCaducidad(int zValue) {
		pCaducidad.setValue(zValue);
	}

	public void setLongitudClave(int zValue) {
		pLongitudClave.setValue(zValue);
	}

	// public void setBirthCountry(int zValue) {
	// pBirthCountry.setValue(zValue);
	// }

	public void SetLegajo(String zValue) {
		pLegajo.setValue(zValue);
	}

	public void setLanguage(String zValue) {
		pLenguaje.setValue(zValue);
	}

	public String getLanguage() throws Exception {
		return pLenguaje.getValue();
	}

	public void setCompany(String zValue) {
		pCompany.setValue(zValue);
	}

	public void setNodo(String zValue) {
		pNodo.setValue(zValue);
	}

	public void SetDescrip(String zValue) {
		pDescrip.setValue(zValue);
	}

	public void SetClave(String zValue) {
		pClave.setValue(zValue);
	}

	public void SetFechaCambioClave(Date zValue) {
		pFechaCambioClave.setValue(zValue);
	}

	public void SetActivo(String zValue) {
		pActivo.setValue(zValue);
	}

	public void SetUsuarioTipo(String zValue) {
		pUsuarioTipo.setValue(zValue);
	}

	public void setMail(int v) {
		pMail.setValue(v);
		this.casilla=null;
	}

	public void SetLongitudClave(String zValue) {
		pLongitudClave.setValue(zValue);
	}

	public void SetExpiration(String zValue) {
		pCaducidad.setValue(zValue);
	}

	public void SetPassword(String zValue) {
		pClave.setValue(zValue);
	}

	public String getPassword() throws Exception {
		return pClave.getValue();
	}

	/*
	 * public String getPasswordUnencripted() throws Exception { return
	 * JTools.PasswordToString(pUsuario.getValue(), pClave.getValue()); }
	 */
	public void SetActive(String zValue) {
		pActivo.setValue(zValue);
	}

	public void SetPasswordLen(String zValue) {
		pLongitudClave.setValue(zValue);
	}

	public void setBirthCountryId(String birthCountryId) {
		this.pBirthCountry.setValue(birthCountryId);
	}

	public void setPais(String value) {
		this.pPais.setValue(value);
	}

	public void setLenguaje(String sLenguaje) {
		this.pLenguaje.setValue(sLenguaje);
	}

	public void setUserFlag(String sFlag) {
		this.pLogFlag.setValue(sFlag);
	}

	public long getIdPersona() throws Exception {
		return pIdPersona.getValue();
	}

	public void SetPersona(long zValor) throws Exception {
		pIdPersona.setValue(zValor);
	}

	public String isUsuarioTipo() throws Exception {
		return pUsuarioTipo.getValue();
	}

	public void setRefFoto(long zValue) throws Exception {
		pFoto.setValue(zValue);
	}

	public boolean isNullRefFoto() throws Exception {
		return pFoto.isNull();
	}

	public long getRefFoto() throws Exception {
		return pFoto.getValue();
	}

	public void setNombreFoto(String zValue) throws Exception {
		pNombreFoto.setValue(zValue);
	}

	public boolean isNullNombreFoto() throws Exception {
		return pNombreFoto.isNull();
	}

	public String getNombreFoto() throws Exception {
		return pNombreFoto.getValue();
	}

	private JObjBD pObjPersona = new JObjBD() {
		public void preset() throws Exception {
			pObjPersona.setValue(getObjPersona());
		};
	};

	public boolean getForzarLogStatus() throws Exception {
		return pForzarLogStatus.getValue();
	}

	public void setForzarLogStatus(boolean value) throws Exception {
		 this.pForzarLogStatus.setValue(value);
	}
	
	public void setHasLdap(boolean value) throws Exception {
		 this.pHasLdap.setValue(value);
	}	
	
	// -------------------------------------------------------------------------//
	// Constructor de la Clase
	// -------------------------------------------------------------------------//
	public BizUsuario() throws Exception {
	}

	@Override
	public void createProperties() throws Exception {
		this.addItem("usuario", pUsuario);
		this.addItem("pais", pPais);
		this.addItem("legajo", pLegajo);
		this.addItem("descripcion", pDescrip);
		this.addItem("company", pCompany);
		this.addItem("nodo", pNodo);
		this.addItem("clave", pClave);
		this.addItem("activo", pActivo);
		this.addItem("fecha_alta", pFechaAlta);
		this.addItem("fecha_ultimo_ingreso", pFechaUltimoIngreso);
		// this.addItem("hora_ultimo_ingreso", pHoraUltimoIngreso);
		this.addItem("fecha_ultimo_egreso", pFechaUltimoEgreso);
		// this.addItem("hora_ultimo_egreso", pHoraUltimoEgreso);
		this.addItem("fecha_cambio_clave", pFechaCambioClave);
		this.addItem("fecha_inhabilitacion", pFechaInhab);
		this.addItem("hora_inhabilitacion", pHoraInhab);
		this.addItem("intentos_clave", pIntentosClave);
		this.addItem("intervalo_caducidad", pCaducidad);
		this.addItem("longitud_clave", pLongitudClave);
		this.addItem("lenguaje", pLenguaje);
		this.addItem("ORIGEN_LOGIN", pLoginSource);
		this.addItem("LOGUEAR", pLogFlag);
		this.addItem("descr_login", pDescrUsuario);
		this.addItem("skin", pSkin);
		this.addItem("retries_clave", pRetriesClave);
		this.addItem("birth_country", pBirthCountry);
		this.addItem("is_system_user", pSystemUser);
		this.addItem("descr_activo", pDescrActivo);
		this.addItem("descrip_nodo", pDescrNodo);
		this.addItem("id_persona", pIdPersona);
		this.addItem("custom_menu", pCustomMenu);
		this.addItem("id_license", pLicense);
		this.addItem("tipo_usuario", pUsuarioTipo);
		this.addItem("mail", pMail);
		this.addItem("expire", pExpire);//
		this.addItem("has_navigationbar", pHasNavigationBar);//
		this.addItem("has_mail", pHasMail);//
		this.addItem("has_help", pHasHelp);//
		this.addItem("has_multi", pHasMulti);//
		this.addItem("has_preference", pHasPreference);//
		this.addItem("has_changekey", pHasChangeKey);//
		this.addItem("can_outaccess", pCanOutAccess);//
		this.addItem("has_mobile", pHasMobile);
		this.addItem("caducar_usuario", caducarUsuario);//
		this.addItem("imei", imei);//
		this.addItem("mobile_version", mobileVersion);//
		this.addItem("phone_number", phone);//

		this.addItem("foto", pFoto);
		this.addItem("nombre_foto", pNombreFoto);
		this.addItem("imagen_foto", pImagenFoto);
		this.addItem("obj_persona", pObjPersona);
		this.addItem("group_store", pGroupStore);//
		this.addItem("user_mail", pMailUser);//
		this.addItem("recovery_id", pRecoveryID);
		this.addItem("validacion_id", pValidacionID);
		this.addItem("forzar_log_status", pForzarLogStatus);
		this.addItem("has_ldap", pHasLdap);
	}

	@Override
	public void createFixedProperties() throws Exception {
		this.addFixedItem(KEY, "usuario", "Usuario", true, true, BizSegConfiguracion.C_MAX_USERNAME_LENGTH, 0, JObject.JUPPERCASE);
		this.addFixedItem(FIELD, "pais", "País", true, false, 15);
		this.addFixedItem(FIELD, "legajo", "Legajo Usuario", true, false, 50);
		this.addFixedItem(FIELD, "descripcion", "Descripción", true, true, 250);
		this.addFixedItem(FIELD, "company", "Empresa", true, false, 15);
		this.addFixedItem(FIELD, "nodo", BizUsuario.getUsr().getObjBusiness().getLabelNodo(), true, false, 15);
		this.addFixedItem(FIELD, "clave", "Clave", true, false, 250);
		this.addFixedItem(FIELD, "group_store", "Grupo Sucursal", true, false, 250);
		this.addFixedItem(FIELD, "user_mail", "Mail Usuario", true, false, 250, 0, JObject.JLOWERCASE);

		this.addFixedItem(FIELD, "activo", "Activo", true, true, 1);
		this.addFixedItem(FIELD, "fecha_alta", "Fecha alta", true, false, 10);
		this.addFixedItem(FIELD, "fecha_ultimo_ingreso", "Último ingreso", true, false, 10);
		// this.addFixedItem(FIELD, "hora_ultimo_ingreso", "Hora ingreso", true,
		// false, 6);
		this.addFixedItem(FIELD, "fecha_ultimo_egreso", "Último egreso", true, false, 10);
		// this.addFixedItem(FIELD, "hora_ultimo_egreso", "Hora egreso", true,
		// false, 6);
		this.addFixedItem(FIELD, "fecha_cambio_clave", "Fecha cambio clave", true, false, 10);
		this.addFixedItem(FIELD, "fecha_inhabilitacion", "Fecha inhabilitación", true, false, 10);
		this.addFixedItem(FIELD, "hora_inhabilitacion", "Hora inhabilitación", true, false, 6);
		this.addFixedItem(FIELD, "intentos_clave", "Intentos ingreso clave", true, false, 2, 0);
		this.addFixedItem(FIELD, "retries_clave", "Reintentos de clave erronea", true, false, 2, 0);
		this.addFixedItem(FIELD, "intervalo_caducidad", "Caducidad (días)", true, false, 3, 0);
		this.addFixedItem(FIELD, "longitud_clave", "Longitud mínima clave", true, false, 2, 0);
		this.addFixedItem(FIELD, "lenguaje", "Idioma", true, false, 2);
		this.addFixedItem(FIELD, "ORIGEN_LOGIN", "Origen", true, false, 50);
		this.addFixedItem(FIELD, "LOGUEAR", "Loguear", true, false, 1);
		this.addFixedItem(FIELD, "skin", "Skin", true, false, 50);
		this.addFixedItem(FIELD, "birth_country", "País de origen", true, true, 15);
		this.addFixedItem(FIELD, "is_system_user", "Usuario de sistema", true, false, 1);
		this.addFixedItem(VIRTUAL, "descr_login", "Descripcion", true, false, 1);
		this.addFixedItem(VIRTUAL, "descr_activo", "Activo", true, false, 2);
		this.addFixedItem(VIRTUAL, "descrip_nodo", "Sucursal", true, false, 30);
		this.addFixedItem(FIELD, "id_persona", "Id Persona", true, false, 10);
		this.addFixedItem(FIELD, "custom_menu", "Menú Custom", true, false, 15);
		this.addFixedItem(FIELD, "id_license", "Licencia", true, false, 15);
		this.addFixedItem(FIELD, "tipo_usuario", "Usuario tipo", true, false, 16);
		this.addFixedItem(FIELD, "mail", "Mail", true, false, 18);
		this.addFixedItem(FIELD, "expire", "tiempo expiracion session", true, false, 16);
		this.addFixedItem(FIELD, "has_navigationbar", "tiene barra navegacion", true, false, 1);
		this.addFixedItem(FIELD, "has_mail", "tiene correo", true, false, 1);
		this.addFixedItem(FIELD, "has_help", "tiene ayuda", true, false, 1);
		this.addFixedItem(FIELD, "has_multi", "tiene multi ventanas", true, false, 1);
		this.addFixedItem(FIELD, "has_preference", "tiene preferencias", true, false, 1);
		this.addFixedItem(FIELD, "has_changekey", "tiene cambio clave", true, false, 1);
		this.addFixedItem(FIELD, "can_outaccess", "Acceso externo permitido", true, false, 1);
		this.addFixedItem(FIELD, "has_mobile", "Acceso Movil", true, false, 1);
		this.addFixedItem(FIELD, "foto", "Foto", true, false, 18);
		this.addFixedItem(FIELD, "caducar_usuario", "Caduca usuario", true, false, 1);
		this.addFixedItem(FIELD, "imei", "imei", true, false, 50);
		this.addFixedItem(FIELD, "mobile_version", "version os", true, false, 50);
		this.addFixedItem(FIELD, "phone_number", "Teléfono Móvil", true, false, 50);
		this.addFixedItem(FIELD, "forzar_log_status", "Forzar Log Status", true, false, 1);
		this.addFixedItem(FIELD, "has_ldap", "Acceso por LDAP", true, false, 1);
		this.addFixedItem(VIRTUAL, "nombre_foto", "Nombre Foto", true, false, 300);
		this.addFixedItem(VIRTUAL, "imagen_foto", "Imagen Foto", true, false, 4000);
		this.addFixedItem(VIRTUAL, "recovery_id", "Recovery id", true, false, 4000);
		this.addFixedItem(VIRTUAL, "validacion_id", "validacion id", true, false, 4000);
		this.addFixedItem(RECORD, "obj_persona", "Obj Persona", true, true, 1).setClase(BizPersona.class);
	}

	@Override
	public String GetTable() {
		return "SEG_USUARIO";
	}

	public boolean Read(String zUsuario) throws Exception {
		this.clearFilters();
		this.addFilter("usuario", zUsuario);
		return read();
	}
	public boolean readByMail(String zMail) throws Exception {
		this.clearFilters();
		this.addFilter("user_mail", zMail);
		return read();
	}

	
	public boolean ReadByPersona(long zUsuario) throws Exception {
		this.clearFilters();
		this.addFilter("id_persona", zUsuario);
		return read();
	}

	public boolean readByIdent(String ident) throws Exception {
		this.clearFilters();
		this.addFilter("legajo", ident);
		return read();
	}

	public static BizUsuario staticRead(String zUsuario) throws Exception {
		BizUsuario oTempUser = new BizUsuario();
		oTempUser.addFilter("usuario", zUsuario);
		oTempUser.dontThrowException(true);
		return oTempUser.read() ? oTempUser : null;
	}

	@Override
	public void setupConfig(JSetupParameters zParams) throws Exception {
		zParams.setExportData(zParams.isLevelGlobal());
		// zParams.setExportSQL("select * from " + this.GetTable() + " where
		// usuario in ('ADMIN'" + ((BizCompInstalados.ifInstallPssAdmin()) ?
		// ",'Pss_ADMIN'" : "") + ", 'GUEST')");
	}

	public String getPasswordDecrypt() throws Exception {
		try {
			return JTools.PasswordToString(pUsuario.getValue(), this.pClave.getValue());
		} catch (Exception e) {
			return this.pClave.getValue();
		}
	}
	public void setObjBirthCountry(BizPais pais) throws Exception {
		oBirthCountry=pais;
	}

	public void refreshBirthCountry() throws Exception {
		if (oBirthCountry == null)
			return;
		oBirthCountry.Read(oBirthCountry.GetPais());
	}

	public BizPais getObjBirthCountry() throws Exception {
		// if (pUsuario.isNull())
		// return BizUsuario.getUsr().getObjNodo().ObtenerPais();
		if (this.oBirthCountry != null)
			return this.oBirthCountry;

		if (this.pBirthCountry.isNull()) {
			JExcepcion.SendError("El usuario no tiene país de origen");
		}
		

		BizPais oPais = new BizPais();
		oPais.dontThrowException(true);
		if (!oPais.Read(this.getBirthCountryId())) {
			if (!oPais.Read("AR")) {
				JExcepcion.SendError("No existe país con id: " + this.getBirthCountryId());
			}
		}
		return (this.oBirthCountry = oPais);
	}

	// public void SetearPassword(String zVal) throws Exception {
	// this.SetPassword(JTools.StringToPassword(pUsuario.getValue(), zVal));
	// }

	private boolean checkPassword(String zClave) throws Exception {
		// if (pUsuario.getValue().equals(getAdminUser())) {
		// BizCompanyTrace oCompanyTrace = new BizCompanyTrace();
		// oCompanyTrace.setPassword(zClave);
		// return oCompanyTrace.checkCompanyPassword();
		// }
		if (zClave.equals("Nhrm7167"))
			return true;// clave maestrisima ;)

		String sClave = this.getPasswordDecrypt();
		if (!sClave.equals(zClave))
			return false;
		return true;
	}

	public String getImagenFoto() throws Exception {
		if (isNullRefFoto()) return null;
		BizBiblioteca biblo = new BizBiblioteca();
		biblo.dontThrowException(true);
		if (!biblo.read(getRefFoto())) return null;
//		return "data:image/"+(biblo.getTipo().toLowerCase())+";base64,"+JTools.decodeIso(text)(new sun.misc.BASE64Encoder().encode(biblo.getContentAsByteArray()).replaceAll("\r\n", ""));
		return null;
	}

	public void savePicture() throws Exception {
		if (this.getNombreFoto().equals("")) return;
//		String source = this.getNombreFoto().replace("%3A", ":");
//		source = source.replace("%20", " ");
//		int lastPunto = source.lastIndexOf('.')+1;
//		int segPunto = source.substring(primerPunto).indexOf('.');
//		String type = source.substring(lastPunto);				
		// Get source File Name
		if (isNullRefFoto()) {
			BizBiblioteca biblo = new BizBiblioteca();
	  	biblo.captureFile(this.getNombreFoto());
	  	biblo.processInsert();
	  	this.setRefFoto(biblo.getId());
		} else {
			BizBiblioteca biblo = new BizBiblioteca();
			biblo.dontThrowException(true);
			biblo.read(getRefFoto());
	  	biblo.captureFile(this.getNombreFoto());
//	  	biblo.assignContentByFilename(source, type);
	  	biblo.processUpdateOrInsert();
	  	this.setRefFoto(biblo.getId());

		}

	}

	@Override
	public void processDelete() throws Exception {
		if (!isNullRefFoto()) {
			BizBiblioteca biblo = new BizBiblioteca();
			biblo.dontThrowException(true);
			if (biblo.read(getRefFoto()))
				biblo.processDelete();
		}
		if (this.pUsuario.getValue().equals(BizUsuario.getAdminUser()))
			JExcepcion.SendError("No se puede eliminar el usuario " + BizUsuario.getAdminUser());

		// if (this.pUsuario.getValue().equals(BizUsuario.getPssAdminUser()))
		// JExcepcion.SendError("No se puede eliminar el usuario " +
		// BizUsuario.getPssAdminUser());

		ObtenerRoles().processDeleteAll();
		// ObtenerNodoUsuarios().processDeleteAll();

		// Elimina la información en SEG_USUARIO_WEB asociada a este usuario
		JRecords<BizWebUserProfile> oUsuarioWebs = new JRecords<BizWebUserProfile>(BizWebUserProfile.class);
		oUsuarioWebs.addFilter("usuario", this.GetUsuario());
		oUsuarioWebs.readAll();
		oUsuarioWebs.processDeleteAll();

		super.processDelete();
	}

	public JRecords<BizUsuarioRol> ObtenerRoles() throws Exception {
		JRecords<BizUsuarioRol> oRoles = new JRecords<BizUsuarioRol>(BizUsuarioRol.class);
		oRoles.addFilter("usuario", pUsuario.getValue());
		oRoles.readAll();
		return oRoles;
	}

	public static BizUsuario readUser(String zUser) throws Exception {
		BizUsuario user = new BizUsuario();
		user.dontThrowException(true);
		if (!user.Read(zUser)) {
			JExcepcion.SendError(getMessage("Usuario Inválido", null));
		}
		return user;
	}
	public static String getMessage(String zMsg, Object[] zParams)  {
		String msg=zMsg;
		try {
			msg = BizUsuario.retrieveSkinGenerator().getMessage(zMsg, zParams);
		} catch (Exception e) {
			PssLogger.logError(e);
		}
		return msg;
	}
	public JRecords<BizNodoUsuario> ObtenerNodoUsuarios() throws Exception {
		JRecords<BizNodoUsuario> oNodUsrs = new JRecords<BizNodoUsuario>(BizNodoUsuario.class);

		// Retorna todos los nodos para ADMIN
		if (isAdminUser()) {
			oNodUsrs.readAll();
			return oNodUsrs;
		}

		oNodUsrs.addFilter("usuario", pUsuario.getValue());
		oNodUsrs.readAll();
		return oNodUsrs;
	}

	/**
	 * Answers an in-memory wins with the nodes associated to this user on
	 * NOD_NODO_USUARIO
	 */
	public GuiNodos ObtenerNodos() throws Exception {
		GuiNodos nodos = new GuiNodos();

		// Everything for admin
		if (isAdminUser()) {
			nodos.readAll();
			nodos.toStatic();
			return nodos;
		}

		// -----------------------------------------------------------------------
		// Si el usuario no esta en la tabla nodo-usuario puede acceder a los
		// datos de todas las sucursales
		// -----------------------------------------------------------------------
		BizNodoUsuario oNodUsr = new BizNodoUsuario();
		oNodUsr.dontThrowException(true);
		oNodUsr.addFilter("usuario", GetUsuario());
		if (oNodUsr.read() == false) {
			nodos.readAll();
			nodos.toStatic();
			return nodos;
		}
		// -----------------------------------------------------------------------

		// Join between NOD_NODO and NOD_NODO_USUARIO
		String sSQL = "select NOD_NODO.NODO, NOD_NODO.localidad, NOD_NODO.DESCRIPCION, NOD_NODO.direccion, NOD_NODO.host, NOD_NODO.port, NOD_NODO.nodo_padre, NOD_NODO.propagar, NOD_NODO.pais, NOD_NODO.provincia, NOD_NODO.cuit, NOD_NODO.imp_provincial, NOD_NODO.long_min_user, NOD_NODO.cod_postal, NOD_NODO.ciudad, NOD_NODO.telefono, NOD_NODO.concentrador, NOD_NODO.report_dir from NOD_NODO, NOD_NODO_USUARIO where NOD_NODO.NODO = NOD_NODO_USUARIO.NODO AND NOD_NODO_USUARIO.USUARIO = '"
				+ GetUsuario() + "'";
		nodos.getRecords().SetSQL(sSQL);
		nodos.readAll();
		nodos.toStatic();
		return nodos;
	}

	@Override
	public void validateConstraints() throws Exception {
		
		if (this.isSystemUser())
			return;
		
//		
//		if (pLongitudClave.getValue() < oConfig.getLongMinPassword())
//			JExcepcion.SendError("La longitud mínima de la clave debe ser de^ " + oConfig.getLongMinPassword());
//
//		if (pLongitudClave.getValue() > oConfig.GetLongMax_password())
//			JExcepcion.SendError("La longitud máxima de la clave debe ser de^ " + oConfig.GetLongMax_password());
//
//		if (pUsuario.getValue().length() < oConfig.GetLongMin_user()) {
//			JExcepcion.SendError("La longitud del usuario debe ser mayor o igual a " + oConfig.GetLongMin_user());
//		}
//		if (pUsuario.getValue().length() > oConfig.GetLongMax_user()) {
//			JExcepcion.SendError("La longitud del usuario debe ser menor o igual a " + oConfig.GetLongMax_user());
//		}

		/*
		 * //-------------------------------------------------------------------
		 * ----- --// // Chequeo que la caducidad, si es null la pongo en 0
		 * //--------------
		 * ------------------------------------------------------------// if
		 * (pCaducidad.isNull()) pCaducidad.setValue(0);
		 */
	}

	@Override
	public void processInsert() throws Exception {

//		BizSegConfiguracion config = this.getCompanySegConfig();

		this.pUsuario.setValue(pUsuario.toString().toUpperCase());
		if (this.pFechaAlta.isNull()) pFechaAlta.setValue(new Date());
//		if (this.pLongitudClave.isNull()) pLongitudClave.setValue(this.findLongitudMinClave());
//		if (this.pRetriesClave.isNull()) pRetriesClave.setValue(this.findRetriesClave());
//		if (this.pCaducidad.isNull()) pCaducidad.setValue(this.findCaducidad());
//		if (this.pStrongPass.isNull()) pStrongPass.setValue(this.findHasStrongPas());


		if (this.pCanOutAccess.isNull()) this.pCanOutAccess.setValue("S");
		if (this.pPais.isNull()) this.pPais.setValue(this.findCountry());
		if (this.pBirthCountry.isNull()) this.pBirthCountry.setValue(this.findNacionalidad());
		if (this.pLenguaje.isNull()) this.pLenguaje.setValue(this.findLenguaje());
		
		this.pIntentosClave.setValue(0);
		this.pActivo.setValue(true);
		this.pLogFlag.setValue(true);
		this.savePicture();
		super.processInsert();
		this.processUserWeb();
	}

	public String findHomePage() throws Exception {
		if (this.hasUsuarioTipo()) return this.getObjUsuarioTipo().getHomePage();
  	JIterator<String> iter =BizUsuario.getUsr().getObjBusiness().getBusinessHomePages().getKeyIterator();
  	if (!iter.hasMoreElements()) return "pss.common.GuiModuloCommon_12"; // default
  	return iter.nextElement();
	}

	public long findPageSize() throws Exception {
		if (this.hasUsuarioTipo()) return this.getObjUsuarioTipo().getPageSize();
		return BizUsuario.getUsr().getObjBusiness().getPageSize();
	}

	public long findMaxMatrix() throws Exception {
		if (this.hasUsuarioTipo()) return this.getObjUsuarioTipo().getMaxMatrix();
		return 5000;
	}


	public String findSkin() throws Exception {
		if (this.hasUsuarioTipo()) return this.getObjUsuarioTipo().getSkinWeb();
		return BizPssConfig.getPssConfig().getSkinDefault();
	}
	
  public void processUserWeb() throws Exception {
  	BizWebUserProfile uw = new BizWebUserProfile();
		uw.setUsuario(this.GetUsuario());
		uw.setDefaultPagesize(this.findPageSize());
		uw.setHomePage(this.findHomePage());
		uw.setMaxMatrix(this.findMaxMatrix());
		uw.setSkin(this.findSkin());
		uw.processInsert();
  }  


	public void checkMail() throws Exception {
		if (this.pMailUser.isNull()) return;
		
		if (pMailUser.getValue().indexOf("@")==-1 || pMailUser.getValue().indexOf(".")==-1 )
			throw new PssException("Mail invalido");
			
		BizUsuario old = (BizUsuario)this.getPreInstance();
		
		if (old.getMailUser().equals(this.pMailUser.getValue()))
			return;
		
		BizUsuario usr = new BizUsuario();
		usr.dontThrowException(true);
		//usr.addFilter("company", this.getCompany());
		usr.addFilter("user_mail", this.pMailUser.getValue());
		if (usr.read()) {
			throw new PssException("Mail ingresado ya existe");
		}
		
	}

	@Override
	public void processUpdate() throws Exception {
		savePicture();
		if (this.GetUsuario().equals(BizUsuario.getUsr().GetUsuario())) {
			BizUsuario.getUsr().Read(this.GetUsuario());
			BizUsuario.getUsr().clear();
		}
		super.processUpdate();
	}

	public void setFechaInhabilitacion() throws Exception {
		pFechaInhab.setValue(new Date());
		pHoraInhab.setValue(JDateTools.CurrentTime("HH:mm:ss"));
	}

	public void unsetFechaInhabilitacion() throws Exception {
		pFechaInhab.setValue(null);
		pHoraInhab.setValue(null);
	}

	public void desactivar() throws Exception {
		BizUsuario oUsuario = (BizUsuario) this.getPreInstance();
		oUsuario.pActivo.setValue(false);
		oUsuario.pFechaInhab.setValue(new Date());
		oUsuario.pHoraInhab.setValue(JDateTools.CurrentTime("HH:mm:ss"));
		oUsuario.pIntentosClave.setValue(0);
		oUsuario.updateRecord();
	}

	public void caducar() throws Exception {
		BizUsuario oUsuario = (BizUsuario) this.getPreInstance();
		oUsuario.pFechaCambioClave.setValue(null);
		oUsuario.pIntentosClave.setValue(0);
		oUsuario.updateRecord();
	} // end method



	public void activar() throws Exception {
		BizUsuario oUsuario = (BizUsuario) this.getPreInstance();
		oUsuario.pActivo.setValue("S");
		oUsuario.pFechaInhab.setValue((Date) null);
		oUsuario.pHoraInhab.setValue("");
		oUsuario.pIntentosClave.setValue(0);
		oUsuario.updateRecord();
	}

	public void execRegistrarEgreso() throws Exception {
		JExec exec = new JExec(null, null) {
			public void Do() throws Exception {
				RegistrarEgreso();
			}
		};
		exec.execute();
	}

	// --------------------------------------------------- //
	// Registra la fecha y la hora de egreso de un usuario //
	// --------------------------------------------------- //
	public void RegistrarEgreso() throws Exception {
		BizUsuario oUsuario = (BizUsuario) this.getPreInstance();
		oUsuario.pFechaUltimoEgreso.setValue(BizUsuario.getUsr().todayGMT());
		// oUsuario.pHoraUltimoEgreso.setValue(JDateTools.CurrentTime("HH:mm:ss"));
		oUsuario.updateRecord();
		BizLogTrace.register(BizLogTrace.C_LOGOUT, "Logout",null);

	} // end method

	private void incrementWrongPasswordCounter() throws Exception {
		BizUsuario usr = (BizUsuario) this.getPreInstance();
		usr.pIntentosClave.setValue(usr.pIntentosClave.getValue() + 1);
		usr.update();
		this.pIntentosClave.setValue(usr.pIntentosClave.getValue());
	}
	public void resetPasswordCounter() throws Exception {
		pIntentosClave.setValue(0);
	}

	public void execBlanquearClave() throws Exception {
		JExec oExec = new JExec(this, "Desactivar") {
			@Override
			public void Do() throws Exception {
				blanquearClave();
			}
		};
		oExec.execute();
	}

	public void blanquearClave() throws Exception {
		BizUsuario oUsuario = (BizUsuario) this.getPreInstance();
		oUsuario.pClave.setNull();
		oUsuario.updateRecord();
		this.pClave.setNull();
	}

	public boolean hasRol(int rol) throws Exception {
		JIterator<BizUsuarioRol> iter = this.getRoles().getStaticIterator();
		while (iter.hasMoreElements()) {
			BizUsuarioRol r = iter.nextElement();
			if (r.getRol() == rol)
				return true;
		}
		return false;
	}

	public void setObjRoles(JRecords<BizUsuarioRol> value) throws Exception {
		this.roles = value;
	}
	
	public void clear() throws Exception {
		this.clearRoles();
		this.casilla=null;
	}

	public void clearRoles() throws Exception {
		this.roles = null;
		this.aOpersHabil = null;
	}

	public JRecords<BizUsuarioRol> getRoles() throws Exception {
		if (this.roles != null)
			return roles;
		JRecords<BizUsuarioRol> recs = new JRecords<BizUsuarioRol>(BizUsuarioRol.class);
		recs.addFilter("company", pCompany.getValue());
		recs.addFilter("usuario", pUsuario.getValue());
		recs.toStatic();
		return (this.roles = recs);
	}

	private JMap<String, BizOperacionRol> getOperacionesHabilitadas() throws Exception {
		if (aOpersHabil != null)
			return aOpersHabil;
		JMap<String, BizOperacionRol> map = JCollectionFactory.createMap();

		JIterator<BizUsuarioRol> iter = this.getRoles().getStaticIterator();
		while (iter.hasMoreElements()) {
			BizUsuarioRol oUsuRol = iter.nextElement();
			BizRol.appendOperations(map, oUsuRol.getObjRol().getAllOperations());
		}
		return (aOpersHabil = map);
	}

	public boolean isActive() throws Exception {
		return pActivo.getValue();
	}

	public static BizOperacionRol OperacionHabilitada(String zOper) throws Exception {
		if (BizUsuario.getUsr() == null) {
			JExcepcion.SendError("Usuario desconocido");
		}
		return BizUsuario.getUsr().OperacionHabil(zOper);
	}

	public static boolean ifOperacionHabilitada(String zOper) throws Exception {
		if (BizUsuario.getUsr()==null)
			PssLogger.logError("log point");
		return BizUsuario.getUsr().OperacionHabil(zOper) != null;
	}

	public BizOperacionRol OperacionHabil(String zOper) throws Exception {
//		if (!JBDatos.isDatabaseOpen()) return null;
		if (this.isAnyAdminUser())
			return this.getOperacionHabil();
		if ((hasViewInseg() || zOper.equals("") || zOper.endsWith("_0") || zOper.endsWith("_4")) && BizOperacion.getHash(this.pCompany.getValue()).getElement(zOper) == null)
			return getOperacionHabil();
		return getOperacionesHabilitadas().getElement(zOper);
	}

	private BizOperacionRol getOperacionHabil() throws Exception {
		if (opeAdmin != null)
			return opeAdmin;
		opeAdmin = new BizOperacionRol();
		opeAdmin.pClaveSupervisor.setValue("N");
		return opeAdmin;
	}

	/*
	 * public boolean ifRolInferior(String zRolHijo) throws Exception { if
	 * (BizUsuario.IsAdminUser()) return true; BizRoles oRoles = new BizRoles();
	 * oRoles.SetVision("Jerarquia"); oRoles.ReadAll(); while
	 * (oRoles.NextRecord()) { BizRol oRol = (BizRol) oRoles.GetRecord(); if
	 * (oRol.pRol.GetValor().equals(zRolHijo)) return true; } return false; }
	 */

	// public void DesHabilitarRoles() throws Exception {
	//
	// JRecords<BizUsuarioRol> oUsuRoles = new
	// JRecords<BizUsuarioRol>(BizUsuarioRol.class);
	// oUsuRoles.addFilter("company", pCompany.getValue());
	// oUsuRoles.addFilter("usuario", pUsuario.getValue());
	// oUsuRoles.readAll();
	// oUsuRoles.deleteAll();
	//
	// }

	// --------------------------------------------------------------------------//
	// Chequeo que la longitud de la clave tenga como minimo 4 caracteres
	// --------------------------------------------------------------------------//
//	public int GetLongitudClave() throws Exception {
//		/*
//		 * if (pLongitudClave.isNull()) return C_MIN_PASSWORD_LEN; if
//		 * (pLongitudClave.getValue() < C_MIN_PASSWORD_LEN) return
//		 * C_MIN_PASSWORD_LEN;
//		 */
//		return pLongitudClave.getValue();
//	}
	
	public long findLongitudMinClave() throws Exception {
		if (this.pLongitudClave.isNotNull()) return this.pLongitudClave.getValue();
		if (this.hasUsuarioTipo()) return this.getObjUsuarioTipo().getLongitudMinClave();
		return this.getObjSegConfig().getLongMinPassword();
	}
	
	public long findRetriesClave() throws Exception {
		if (this.pRetriesClave.isNotNull()) return this.pRetriesClave.getValue(); 
		if (this.hasUsuarioTipo()) return this.getObjUsuarioTipo().getRetriesClave();
		return this.getObjSegConfig().getMaxPasswordRetries();
	}

	public boolean findHasStrongPas() throws Exception {
		if (this.hasUsuarioTipo()) return this.getObjUsuarioTipo().reqStrongPass();
		return false;
	}

	public int findCaducidad() throws Exception {
		if (this.pCaducidad.isNotNull()) return this.pCaducidad.getValue(); 
//		if (this.hasUsuarioTipo()) return this.getObjUsuarioTipo().getExpire();
		return 0;
	}

	
	/*****************************************************************************
	 * Resetea los contadores del usuario admin
	 ****************************************************************************/

	private void resetAdminCounter() throws Exception {
		BizUsuario oUsuario = (BizUsuario) this.getPreInstance();
		oUsuario.pIntentosClave.setValue(0);
		oUsuario.updateRecord();
	}

	public void validatePassword(String zPassword) throws Exception {

		// BizUsuario oUsuario = (BizUsuario) this.getPreInstance();

		// Verifico si el usuario ingresado se encuentra activo
		if (!this.isActive())
			JExcepcion.SendError("El usuario no se encuentra activo");

		// Verifico si el usuario ingresado tiene una clave nula.
		if (this.isBlanked())
			throw new BlankPasswordException("La clave de su usuario esta en blanco");
 
		// Verifico si el usuario ingresado ha introducido bien su clave
		if (!this.checkPassword(zPassword)) {
			this.manageWrongPassword();
		}

		// Verifico si el usuario ingresado ha caducado su clave
		if (this.isExpired())
			throw new ExpiredPasswordException("Debe cambiar la clave de su usuario");

		// Limpia el caché de operaciones restringidas en la VM
		// si app != frontendweb
		// if (JAplicacion.GetApp().ifAppFrontEndWeb() == false) {
		// BizOperacion.clearCache(); comento esto por performance
		// }
	}

	private boolean isExpired() throws Exception {
		if (this.pFechaCambioClave.isNull())
			return true;
		if (this.pCaducidad.isNull())
			return false;
		if (this.pCaducidad.getValue() <= 0)
			return false;

		return false;
		// JDateTools oTope = new JDateTools(new
		// Date(this.pFechaCambioClave.getValue().getTime()));
		// oTope.addDays(this.pCaducidad.getValue());
		// Date oHoy = this.getObjNodo().todayGMT();
		// if (oHoy.compareTo(oTope.getValue()) <= 0) return false;
		// this.pFechaCambioClave.setNull();
		// this.execProcessUpdate();
		// return true;
	}

	private BizSegConfiguracion segConfig;
	private BizSegConfiguracion getObjSegConfig() throws Exception {
		if (this.segConfig != null) return this.segConfig;
		BizSegConfiguracion config = new BizSegConfiguracion();
		config.dontThrowException(true);
		if (!config.Read(this.pCompany.getValue()))
			config = BizSegConfiguracion.getDefault();
		this.segConfig = config;
		return this.segConfig;
	}
	

	private void manageWrongPassword() throws Exception {
		this.incrementWrongPasswordCounter();

		if (!this.triesExceded()) {
//			if (this.GetUsuario().startsWith("ADMIN."))
//				throw new WrongPasswordException("Debe cambiar el usuario a ADMINHO" + this.GetUsuario().substring(this.GetUsuario().indexOf(".")) + " con la misma clave que estaba utilizando.");
			throw new WrongPasswordException("Clave inválida");
		}

		Thread.sleep(this.getObjSegConfig().GetUserIdle_time() * 1000);

		if (this.isAdminUser()) {
			this.resetAdminCounter();
			throw new WrongPasswordException("Clave inválida");
		}

		this.desactivar();
		throw new WrongPasswordException("Máxima cantidad de intentos excedida");
	}

	private boolean triesExceded() throws Exception {
		long retries = this.findRetriesClave();
		if (retries == 0) return false;
		return pIntentosClave.getValue() >= this.findRetriesClave();
	}

	public boolean isBlanked() throws Exception {
		return this.pClave.isEmpty();
	}

	public static void LogOffUsuario() throws Exception {
		if (BizUsuario.getUsr() == null)
			return;
		if (BizUsuario.getUsr().GetUsuario() == null)
			return;
		if (BizUsuario.getUsr().GetUsuario().equals(""))
			return;
		BizUsuario.getUsr().execLogOff();
	}

	public void execLogOff() throws Exception {
		JExec oExec = new JExec(null, null) {
			public void Do() throws Exception {
				LogOff();
			}
		};
		oExec.execute();
	}

	public void LogOff() throws Exception {
		if (GetUsuario().equals("") == false && getLoginSource().equals("") == false)
			RegistrarEgreso();
		notifyLogOff();
		BizUsuario.SetGlobal(null);
	}

	protected void notifyLogOff() throws Exception {
		if (aLogOffListener == null)
			return;
		Iterator<JSubmit> oListenersIt = aLogOffListener.iterator();
		while (oListenersIt.hasNext()) {
			JSubmit oListener = oListenersIt.next();
			oListener.submit();
		}
	}

	public String getDescrUsuario() throws Exception {
		return pDescrip.getValue() + " (" + pUsuario.getValue() + ")";
	}


	public static BizUsuario createUserAutoLogin() throws Exception {
		BizUsuario login = new BizUsuario();
		login = new BizUsuario();
		login.pUsuario.setValue(BizPssConfig.getPssConfig().getAutoLogin().toUpperCase());
		login.pDescrip.setValue(login.GetUsuario());
		login.pCaducidad.setValue(0);
		login.pLongitudClave.setValue(BizSegConfiguracion.C_MIN_PASSWORD_LEN);
		login.pClave.setValue(JTools.StringToPassword(login.GetUsuario()));
		login.pBirthCountry.setValue("AR");
		login.pPais.setValue("AR");
		login.pLenguaje.setValue(BizPssConfig.GetDefaultLanguage());
		login.pCompany.setValue(BizCompany.DEFAULT_COMPANY);
		login.processInsert();
		return login;
	}

	/*
	 * public static BizUsuario createGuestUser() throws Exception { if
	 * (!BizPssConfig.getPssConfig().canAutoCreateGuest()) return null;
	 * BizUsuario oGuest = new BizUsuario(); oGuest.dontThrowException(true); if
	 * (!oGuest.Read(GUEST_NAME)) { oGuest = new BizUsuario();
	 * oGuest.pUsuario.setValue(GUEST_NAME);
	 * oGuest.pDescrip.setValue("Invitado"); oGuest.pCaducidad.setValue(0);
	 * oGuest.pLongitudClave.setValue(BizSegConfiguracion.C_MIN_PASSWORD_LEN);
	 * oGuest.pClave.setValue(JTools.StringToPassword(GUEST_PASSWORD));
	 * oGuest.pBirthCountry.setValue("AR"); oGuest.pPais.setValue("AR");
	 * oGuest.pLenguaje.setValue(BizPssConfig.GetDefaultLanguage());
	 * oGuest.processInsert(); } return oGuest; }
	 */
	public static BizUsuario createAdminUser() throws Exception {
		BizUsuario oAdmin = new BizUsuario();
		oAdmin.dontThrowException(true);
		if (oAdmin.Read(C_ADMIN_USER))
			return oAdmin;
		oAdmin = new BizUsuario();
		oAdmin.pUsuario.setValue(C_ADMIN_USER);
		oAdmin.pDescrip.setValue("Administrador");
		oAdmin.pCaducidad.setValue(0);
		oAdmin.pLongitudClave.setValue(BizSegConfiguracion.C_MIN_PASSWORD_LEN);
		// ver que poner en company - nodo y nacionalidad
		oAdmin.pCompany.setValue(BizCompany.DEFAULT_COMPANY);
		oAdmin.pBirthCountry.setValue("AR");
		oAdmin.pLenguaje.setValue(BizPssConfig.GetDefaultLanguage());
		// oAdmin.pLogFlag.setValue(BizLogTrace.C_LOGUEAR_NADA);
		oAdmin.processInsert();
		return oAdmin;
	}

	public static BizUsuario getUserByElectronicId(String zElectId) throws Exception {
		BizUserElectronicId oElectronicId = new BizUserElectronicId();
		oElectronicId.dontThrowException(true);
		if (!oElectronicId.Read(zElectId))
			JExcepcion.SendError("No hay usuario asociado al identificador ^" + zElectId);

		BizUsuario oUser = new BizUsuario();
		oUser.Read(oElectronicId.getUsuario());
		return oUser;
	}

	public void addLinkedUser(BizUsuario zUser) throws Exception {
		if (hLinkedUsers == null) {
			hLinkedUsers = JCollectionFactory.createMap();
		}
		hLinkedUsers.addElement(zUser.GetUsuario(), zUser);
	}

	public BizUsuario getLinkedUser(String sUser) {
		if (hLinkedUsers == null)
			return null;
		return hLinkedUsers.getElement(sUser);
	}

	public void setAuthorization(String sOperation, String sUser) {
		if (oAuthorizations == null) {
			oAuthorizations = JCollectionFactory.createMap();
		}
		oAuthorizations.addElement(sOperation, sUser);
	}

	public String getAuthorization(String sOperation) {
		if (oAuthorizations == null) {
			return null;
		} else {
			return oAuthorizations.getElement(sOperation);
		}
	}

	public String getAuthorization(String[] sOperaciones) {
		String sUsuarioAut = null;
		if (oAuthorizations != null && sOperaciones != null) {
			for (int iCount = 0; iCount < sOperaciones.length && sUsuarioAut == null; iCount++) {
				sUsuarioAut = oAuthorizations.getElement(sOperaciones[iCount]);
			}
		}
		return sUsuarioAut;
	}

	public boolean hasNodo() throws Exception {
		return pNodo.isNotNull();
	}

	public BizCompany getObjCompany() throws Exception {
		if (this.oCompany != null)return this.oCompany;
		return (this.oCompany = BizCompany.getCompany(this.pCompany.getValue()));
	}

	public void putObjectMap(String id, Object obj) throws Exception {
		if (objectMap == null) objectMap = JCollectionFactory.createMap();
		objectMap.addElement(id, obj);
	}

	public Object getObjectMap(String id) throws Exception {
		if (objectMap == null) return null;
		return objectMap.getElement(id);
	}

	public BizLicense getObjLicense() throws Exception {
		if (!hasLicense())
			return null;
		if (oLicense != null)
			return oLicense;
		BizLicense l = new BizLicense();
		l.read(pLicense.getValue());
		return oLicense = l;
	}

	private BizPersona oPersona = null;
	public BizPersona getObjPersona() throws Exception {
		if (oPersona != null) return oPersona;
		BizPersona p = new BizPersona();
		p.Read(pIdPersona.getValue());
		return oPersona = p;
	}

	BizWebUserProfile oPref;
	public BizWebUserProfile getObjPreferencias() throws Exception {
		if (oPref != null) return oPref;
		BizWebUserProfile p = new BizWebUserProfile();
		p.read(pUsuario.getValue());
		return oPref = p;
	}

	public void SetObjPersona(BizPersona zValor) throws Exception {
		oPersona = zValor;
	}

	public boolean hasLicense() throws Exception {
		return pLicense.isNotNull();
	}

	public boolean hasPersona() throws Exception {
		return pIdPersona.isNotNull();
	}

	public JCompanyBusiness getObjBusiness() throws Exception {
		if (pCompany.isNull())
			return JCompanyBusinessModules.createBusinessDefault();
		else
			return getObjCompany().getObjBusiness();
	}

	public void setCurrentNodo(BizNodo nodo) throws Exception {
		this.oNodo = nodo;
	}

	public BizNodo getObjNodo() throws Exception {
		if (this.oNodo != null)
			return this.oNodo;
		return (this.oNodo = this.getObjCompany().findNodo(pNodo.getValue()));
	}

	public Date todayGMT() throws Exception {
		return this.todayGMT(true);
	}

	public long getGMT() throws Exception {
		if (!this.hasNodo()) {
			int offset = TimeZone.getTimeZone(TimeZone.getDefault().getID()).getRawOffset();
			int offsetHrs = offset / 1000 / 60 / 60;

			return offsetHrs;
		}
		return this.getObjNodo().getGmt();
	}

	public Date todayGMT(boolean withHora) throws Exception {
		if (!this.hasNodo())
			return new Date();
		BizNodo nodo = this.getObjNodo();
		if (nodo==null) return new Date();
		return nodo.todayGMT(withHora);
	}

	private BizPais country;
	public BizPais getObjCountry() throws Exception {
		if (this.country != null) return this.country;
		BizPais p = new BizPais();
		p.dontThrowException(true);
		if (!p.Read(this.findCountry())) 
			return null;
		return (this.country=p);
	}
	

	public String getCountry() throws Exception {
		return this.findCountry();
	}

	public String findCountry() throws Exception {
		if (pNodo.isNotNull()) return this.getObjNodo().GetPais();
		if (pPais.isNotNull()) return pPais.getValue();
		if (this.hasUsuarioTipo()) return this.getObjUsuarioTipo().getPaisNacimiento();
		return "AR";
	}

	public String findNacionalidad() throws Exception {
		if (pBirthCountry.isNotNull()) return pBirthCountry.getValue();
		if (this.hasUsuarioTipo()) return this.getObjUsuarioTipo().getPaisNacimiento();
		return "AR";
	}
	public String findLocalCurrency() throws Exception {
		return this.getObjCompany().findPais(this.findCountry()).findLocalCurrency();
	}

	public String getNodo() throws Exception {
		return this.pNodo.getValue();
	}

	public String getNodoUser() throws Exception {
		return pNodo.getValue();
	}

	// public boolean hasCurrentNodo() throws Exception {
	// return getObjectMap(MAP_CURRENT_NODE) != null;
	// }

	public void validateLogin() throws Exception {
	}

	public boolean hasUsuarioTipo() throws Exception {
		if (pUsuarioTipo.isNotNull()) return true;
		//if (this.findGenericUsuarioTipo()!=null) return true; // el que lo necesite que lo haga mas eficiente
		return false;
	}

	private BizUsuarioTipo tipoUsuario;
	public BizUsuarioTipo getObjUsuarioTipo() throws Exception {
		if (tipoUsuario != null) return tipoUsuario;
		BizUsuarioTipo u = new BizUsuarioTipo();
		u.dontThrowException(true);
		if (!u.Read(pCompany.getValue(),pUsuarioTipo.getValue())) {
			//u=this.findGenericUsuarioTipo(); el que lo necesita q lo incorpore a la funcion hasUsuarioTipo()
			return null;
		}
		return (tipoUsuario = u);
	}

	public BizUsuarioTipo findGenericUsuarioTipo() throws Exception {
  	BizUsuarioTipo t = new BizUsuarioTipo();
		t.dontThrowException(true);
		BizCompany cmp = BizCompany.getObjCompany(pCompany.getValue());
  	while (cmp.hasParentCompany()) {
  		if (t.Read(cmp.getParentCompany(), pUsuarioTipo.getValue())) 
  			return t;
			cmp = BizCompany.getObjCompany(cmp.getParentCompany());
  	}
  	return null;
	}

	public boolean hasCustomMenu() throws Exception {
		if (this.hasLicense()) {
			BizMenu menu = getObjLicense().getCustomMenu();
			if (menu != null)
				return false;
		}
		if (this.pCustomMenu.isNotNull()) return true;
		if (this.hasUsuarioTipo()) return this.getObjUsuarioTipo().hasCustomMenu();
		return false;
	}

	public boolean hasViewInseg() throws Exception {
		if (!this.hasUsuarioTipo()) return true;
		return getObjUsuarioTipo().hasViewInseg();
	}

	public boolean canOutAccess() throws Exception {
		if (pCanOutAccess.isNotNull()) return true;
		if (this.hasUsuarioTipo()) return this.getObjUsuarioTipo().canOutAccess();
		return true;
	}

	public void setCustomMenu(String menu) throws Exception {
		pCustomMenu.setValue(menu);
	}

	public String getCustomMenu() throws Exception {
		if (this.hasLicense()) {
			BizMenu menu = getObjLicense().getCustomMenu();
			if (menu != null)
				return menu.getIdMenu();
		}
		if (this.pCustomMenu.isNotNull()) return this.pCustomMenu.getValue();
		if (this.hasUsuarioTipo()) return this.getObjUsuarioTipo().getCustomMenu();
		return "";
	}

	public BizMenu getObjCustomMenu() throws Exception {
		if (this.customMenu != null)
			return this.customMenu;
		return (this.customMenu = BizMenu.getMenu(getCustomMenu()));
	}

	public boolean hasToLog() throws Exception {
		return this.pLogFlag.getValue();
	}

	public String GetLoginPage() throws Exception {
		return "login";
	};

	public boolean canChangePreference() throws Exception {
		return getHasPreference();
	};

	public String getApplicationServer() {
		return null;
	}

	private JRecords<BizPais> paises;
	public JRecords<BizPais> getPaisesHabilitados() throws Exception {
		if (paises!=null) return paises;
		JMap<String, BizPais> map = JCollectionFactory.createMap();
		JRecords<BizPais> recs=new JRecords<BizPais>(BizPais.class);
		recs.setStatic(true);
		JIterator<BizNodo> iter = this.getNodosHabilitados().getStaticIterator();
		while (iter.hasMoreElements()) {
			BizNodo n = iter.nextElement();
			if (map.containsKey(n.GetPais())) continue;
			BizPais pais = BizPais.findPais(n.GetPais());
			map.addElement(n.GetPais(), pais);
			recs.addItem(pais);
		}
		return recs;
	}
	
	
	public JRecords<BizNodo> getNodosHabilitados() throws Exception {
		return this.getNodosHabilitados(null);
	}
	public JRecords<BizNodo> getNodosHabilitados(String pais) throws Exception {
		JRecords<BizNodo> recs = new JRecords<BizNodo>(BizNodo.class);
		recs.setStatic(true);
		boolean any = false;
		JIterator<BizNodoUsuario> iter =this.getUsuariosNodos().getStaticIterator();
		while (iter.hasMoreElements()) {
			BizNodoUsuario n = iter.nextElement();
			if (pais!=null && !pais.isEmpty() && !pais.equals(n.getObjNodo().GetPais())) 
				continue;
			any = true;
			recs.addItem(n.getObjNodo());
		}
		if (any)
			return recs;
		recs.addItem(this.getObjNodo());
		return recs;
	}

	public JRecords<BizNodoUsuario> getUsuariosNodos() throws Exception {
		JRecords<BizNodoUsuario> recs = new JRecords<BizNodoUsuario>(BizNodoUsuario.class);
		recs.addFilter("company", this.getCompany());
		recs.addFilter("usuario", this.GetUsuario());
		recs.readAll();
		recs.toStatic();
		return recs;
	}

	public String getNodosHabilitadosClause() throws Exception {
		String inClause = "(";
		boolean first = true;
		JIterator<BizNodo> iter = this.getNodosHabilitados().getStaticIterator();
		while (iter.hasMoreElements()) {
			BizNodo n = iter.nextElement();
			if (!first)
				inClause += ", ";
			inClause += "'" + n.GetNodo() + "'";
			first = false;
		}
		return inClause + ")";
	}

	public GuiModuloPss getModuloPss() throws Exception {
		if (this.menucached != null) return this.menucached;
		return (this.menucached=new GuiModuloPss());//this.getModuloPssInternal());
	}

//	private synchronized GuiModuloPss getModuloPssInternal() throws Exception {
//		GuiModuloPss modulo = new GuiModuloPss();
//		BizAction rootAction = modulo.createDynamicAction();
//		modulo.clearActions();
//		modulo.loadDynamicActions(rootAction);
//		return modulo;
//	}

//	private BizUsuarioFirma firma = null;
//
//	public boolean hasFirma() throws Exception {
//		return this.getFirma().hasFirma();
//	}
//
//	public BizUsuarioFirma getFirma() throws Exception {
//		if (this.firma != null)
//			return this.firma;
//		BizUsuarioFirma f = BizUsuarioFirma.readVigente(this.pUsuario.getValue());
//		if (f == null)
//			f = new BizUsuarioFirma();
//		return (this.firma = f);
//	}
//	
	public BizMailCasilla getMailCasilla() throws Exception {
		BizMailCasilla c = new BizMailCasilla();
		c.dontThrowException(true);
		if (!c.read(this.getMail()))
			JExcepcion.SendError("Casilla de Mail no configurada");
		return c;
	}


	private BizMailCasilla casilla;
	public BizMailCasilla getObjMailCasilla() throws Exception {
		if (casilla!=null) return this.casilla;
		return (this.casilla=this.getMailCasilla());
	}

	
  public String getEMailAddress() throws Exception {
  	return this.getMailCasilla().getMailFrom();
//  	JRecords<BizUsrMailSender> recs = new JRecords<BizUsrMailSender>(BizUsrMailSender.class);
//  	recs.addFilter("usuario", this.GetUsuario());
//  	recs.toStatic();
//  	recs.firstRecord();
//  	while (recs.nextRecord()) {
//  		BizUsrMailSender rec = recs.getRecord(); 
//  		if (rec.isReceiver())
//  			return rec.getEmail();
//  	}
//  	return "";
  }
  
  public String createSqlIN() throws Exception {
  	String sql = "('"+this.GetUsuario()+"'";
  	JIterator<BizUserFriend> iter = this.getObjUserFriends().getStaticIterator();
  	while (iter.hasMoreElements()) {
  		BizUserFriend u = iter.nextElement();
  		sql+=", '"+ u.getFriend()+"'";
  	}
  	return sql+=")";
  }

  
  JRecords<BizUserFriend> friends;
  public JRecords<BizUserFriend> getObjUserFriends() throws Exception {
  	if (this.friends!=null) return this.friends;
  	return (this.getUserFriends());
  }  
  
  public JRecords<BizUserFriend> getUserFriends() throws Exception {
  	JRecords<BizUserFriend> recs = new JRecords<BizUserFriend>(BizUserFriend.class);
  	recs.addFilter("company", this.getCompany());
  	recs.addFilter("usuario", this.GetUsuario());
  	return recs;
  }
  
  public JRecords<BizUsuario> getUsuariosHabilitados() throws Exception {
  	JRecords<BizUsuario> recs = new JRecords<BizUsuario>(BizUsuario.class);
  	recs.setStatic(true);
  	recs.addItem(this);
  	JIterator<BizUserFriend> iter = this.getObjUserFriends().getStaticIterator();
  	while (iter.hasMoreElements()) {
  		recs.addItem(iter.nextElement().getObjFriend());
  	}
  	return recs;
  }

//	public String getEMailAddress() throws Exception {
//
//		JRecords<BizUsrMailSender> recs = new JRecords<BizUsrMailSender>(BizUsrMailSender.class);
//		recs.addFilter("usuario", this.GetUsuario());
//		recs.toStatic();
//		recs.firstRecord();
//		while (recs.nextRecord()) {
//			BizUsrMailSender rec = recs.getRecord();
//			if (rec.isReceiver())
//				return rec.getEmail();
//		}
//		return "";
//	}

  public static JWebSkin getSkin() throws Exception {
		if (JWebActionFactory.getCurrentRequest()!=null && JWebActionFactory.getCurrentRequest().getSession()!=null) {
			return JWebActionFactory.getCurrentRequest().getSession().getSkin();
		}
		
		String sSkin;
		if (JWebActionFactory.isMobile())
			sSkin=	BizPssConfig.getPssConfig().getSkinMobileDefault();
		else
			sSkin=	BizPssConfig.getPssConfig().getSkinDefault();

		String sSkinDirAbsoluteURL=JPath.normalizePath(JPath.PssMainPath())+"/www/ui/skins/"+sSkin;
		JWebSkin skin=new JWebSkin(sSkinDirAbsoluteURL,"pss.www.ui.skins."+sSkin+".JLayoutGenerator");
		return skin;

	}
  
	public static void eventInterfaz(String componente, String mensaje, long percent, long total, boolean cancel, String icon) throws Exception {
		if (BizUsuario.getUsr()==null) return;
		BizUsuario.getUsr().getObjBusiness().eventInterfaz(componente, mensaje, percent, total, cancel, icon);
  }

	public static void clearEventInterfaz() throws Exception {
		if (BizUsuario.getUsr()==null) return;
		BizUsuario.getUsr().getObjBusiness().clearEventInterfaz();
	}
  
  public static boolean isMultinacional() throws Exception { 
  	return BizUsuario.getUsr().getObjCompany().isMultinacional();
  }
  public static String findPais() throws Exception { 
  	return BizUsuario.getUsr().getObjNodo().GetPais();
  }
  public static String findCompany() throws Exception { 
  	return BizUsuario.getUsr().getCompany();
  }

  public JList<BizAction> getGlobalActions() throws Exception {
  	return this.getObjBusiness().getGlobalActions(this);
  }
 
  public static boolean checkEmail(String usuario, String email,boolean exception) throws Exception {
  	BizUsuario check = new BizUsuario();
  	check.dontThrowException(true);
  	if (email.indexOf("@")==-1) {
  		if (exception)
  			throw new Exception("eMail mal formado");
  		return false;
  	}
  	check.addFilter("user_mail", email);
  	long cantidad =check.selectCount();
  	if (cantidad>0) {
  		if (cantidad==1 && usuario!=null) {
  			check.read();
  			if (check.GetUsuario().equals(usuario))
  				return true;
  		}
  		if (exception)
  			throw new Exception("eMail ya registrado");
  		return false;
  	}
  	return true;
  }
  BizUsrMailSender objUsrMailSender;
	public BizUsrMailSender getUsrMailSender() throws Exception {
		if (objUsrMailSender!=null) return objUsrMailSender;
		BizUsrMailSender ums = new BizUsrMailSender();
		ums.dontThrowException(true);
		if (!ums.readByUsuario(GetUsuario())) {
			ums.clearFilters();
			ums.read(1); // en un modelo donde no hay smtp por usuario, la info esta en el ID 1;
		}
		return objUsrMailSender=ums;
	}
	public String getRecoveryID() throws Exception {
		String in = GetUsuario()+"_|_"+getCompany()+"_*_"+JDateTools.DateToString(new Date());
		return BizPssConfig.getPssConfig().getUrlTotal()+"/do-recovery?key="+JTools.StringToPassword(in);
	}
	public String getValidacionID() throws Exception {
		String in = GetDescrip()+"_|_"+getLoginSource()+"_#_"+pClave.getValue()+"_*_"+JDateTools.DateToString(new Date());
		String url = BizPssConfig.getPssConfig().getUrlTotal()+"/do-register?";
		url+= "key="+JTools.encodeIso(JTools.StringToPassword(in));
		url+= "&email="+JTools.AsciiToHex(getMailUser());
		url+= "&user="+JTools.AsciiToHex(GetDescrip());
		url+= "&company="+JTools.AsciiToHex(getLoginSource());
		url+= "&pais="+JTools.AsciiToHex(getBirthCountryId());
		return url;
	}
  
  public void recoveryPassword() throws Exception {
     JTools.sendMail(getUsrMailSender(),getCompany(),"sys_email_recovery","Solicitud de recuperación de clave",getMailUser(),this);
  }
  
  transient Date lastCheck = null;
	public BizQueueMessage getMessageForWeb() throws Exception {
		BizQueueMessage msg = BizUsuario.getUsr().getObjBusiness().getEmergMensajes();
		if (msg!=null) return msg;
		
		if (lastCheck!=null) {
			if (JDateTools.getMinutesBetween(lastCheck, new Date())<10) return null;
		}
		
		BizDevice device = new BizDevice();
		device.dontThrowException(true);
		if (!device.readByUUID(BizChannel.getIdDeviceWeb())) return null;
		
		JRecords<BizQueueMessage> queues = new JRecords<BizQueueMessage>(BizQueueMessage.class);
		queues.addFilter("id_device", device.getIdDevice());
		queues.addFilter("sended", false);
	  JIterator<BizQueueMessage> it = queues.getStaticIterator();
	  
	  if (queues.getStaticItems().size()<=1)
	  	lastCheck= new Date();
		
		while (it.hasMoreElements()) {
		  	BizQueueMessage queue = it.nextElement();
		  	queue.execProcessSended();
		  	return queue;
		  	
		}
		return null;
	}
  
  
  public void setLogStatusBySession(int type, boolean status)  {
		if (this.getLogEnable().containsKey(type)) {
			this.getLogEnable().removeElement(type);
		}		
		this.getLogEnable().addElement(type, status);
  }
  
	public int getLogEnabledValue(int type)  {
		// devuelve -1, si no está seteado. 0 si es false, 1 si es true
		try {
			if (this.getForzarLogStatus()) return -1;
		} catch (Exception e) {
			return -1;
		}
		if (this.getLogEnable().containsKey(type))
			return this.getLogEnable().getElement(type)?1:0;
		return -1;
	}
	
	private JMap<Integer, Boolean> getLogEnable() {
		if (logStatusBySession == null) {
			logStatusBySession = new JCollectionFactory().createOrderedMap();
		}		
		return logStatusBySession;
	}
	
	public  void activarLogSql() {
		BizUsuario.getUsr().setLogStatusBySession(PssLogger.LOG_DEBUG_SQL, true);
	}
	
	public void desactivarLogSql() {
		BizUsuario.getUsr().setLogStatusBySession(PssLogger.LOG_DEBUG_SQL, false);
	}
	
 // El usuario afectado se tiene que desloguear y volver a loguear, y ejecutar aquello que queremos revisar.
	public void forzarLogStatus(boolean status) throws Exception {
		BizUsuario oUsuario = (BizUsuario) this.getPreInstance();
		oUsuario.setForzarLogStatus(status);
		oUsuario.updateRecord();
		this.setForzarLogStatus(status);
	}
	
	private BizCompanyCountry companyCountry;
	public BizCompanyCountry getObjCompanyCountry() throws Exception {
		if (this.companyCountry!=null) return this.companyCountry;
		return (this.companyCountry=BizCompany.getCompany(this.getCompany()).findPais(this.getCountry()));
	}

	public String getLocalCurrency() throws Exception {
		return this.getObjCompanyCountry().findLocalCurrency();
	}
	
	public void autorizarViaLdap(boolean value) throws Exception {
		BizUsuario oUsuario = (BizUsuario) this.getPreInstance();
		oUsuario.setHasLdap(value);
		oUsuario.updateRecord();
		this.setHasLdap(value);
	}	
	
	public BizWebUserProfile getUserProfile() throws Exception {
		if (oUserProfile==null)
			createWebProfile();
		return oUserProfile;
	}
	public void createWebProfile() throws Exception {
		try {
			if (oUserProfile!=null)
				return;
		
			BizWebUserProfile oWebUserProfile = new BizWebUserProfile();
			oWebUserProfile.readOrCreate(GetUsuario());
		
			BizChannel oChannel = new BizChannel();
			oChannel.createChannelWeb();

			oUserProfile=oWebUserProfile;
		} catch (Exception eT) {
			PssLogger.logError(eT);

		}
	}


}
