package  pss.common.security;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import pss.core.JAplicacion;
import pss.core.data.BizPssConfig;
import pss.core.data.interfaces.connections.JBDatos;
import pss.core.services.fields.JBoolean;
import pss.core.services.fields.JDateTime;
import pss.core.services.fields.JObject;
import pss.core.services.fields.JPassword;
import pss.core.services.fields.JString;
import pss.core.services.records.JRecord;
import pss.core.tools.JDateTools;
import pss.core.tools.JExcepcion;
import pss.core.tools.JTools;
import pss.core.tools.PssLogger;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JList;
import pss.core.tools.collections.JMap;
import pss.core.tools.submit.JSubmit;

public class BizLoginTrace extends JRecord {

	protected static JList<JSubmit> aLogInListener=null;
	protected JString pLoginTrace=new JString();
	protected JString pLogin=new JString();
	protected JPassword pPassword=new JPassword();
	protected JString pIdApp=new JString();
	protected JString pTipoApp=new JString();
	protected JString pBaseDatos=new JString();
	protected JBoolean pValidarUsuario=new JBoolean();
	protected JString ipaddress=new JString();
	protected JString pTraceId = new JString();
	protected JString pOutAccess=new JString();
	protected JString pPersistentKey=new JString();
	protected JBoolean pPersistent = new JBoolean();
	
	protected JMap<String,Object>  attributes;
//	JString pTipoLogin = new JString();

	public JMap<String, Object> getAttributes() {
		return attributes;
	}

	public void setAttributes(JMap<String, Object> attributes) {
		this.attributes = attributes;
	}
	protected  boolean bRegistrarIngreso=true;

	protected  static boolean bLoginFail=true;
	protected  BizUsuario oUsuario=null;

	public static void addLogInListener(JSubmit zValue) {
		if (aLogInListener==null) aLogInListener=JCollectionFactory.createList();
		aLogInListener.addElement(zValue);
	}

	public void SetLogin(String zUser) {
		pLogin.setValue(zUser);
	}

	public void setIpAddress(String ip) throws Exception {
		ipaddress.setValue(ip);
		if (pOutAccess.isNull())
			pOutAccess.setValue(JTools.isOutAccess(ip)?"S":"N");
	}

	public void setOutAccess(String value) throws Exception {
		pOutAccess.setValue(value);
	}

	public void setPersistent(boolean value) throws Exception {
		pPersistent.setValue(value);
	}

	public String getIpAddress() throws Exception {
		return ipaddress.getValue();
	}
	
	public boolean isOutAccess() throws Exception {
		return (pOutAccess.getValue().equals("S")||pOutAccess.getValue().equals("M")) ;
	}

	public void SetPassword(String zPass) {
		pPassword.setValue(zPass);
	}

	public void SetIdApp(String zValue) {
		pIdApp.setValue(zValue);
	}

	public void SetTipoApp(String zValue) {
		pTipoApp.setValue(zValue);
	}

	public void SetValidarUsuario(boolean zValue) {
		pValidarUsuario.setValue(zValue);
	}

	public String getLogin() throws Exception {
		return pLogin.getValue();
	}
	

	public BizUsuario getLastLogedUser() {
		return oUsuario;
	}
	public String getTraceId() throws Exception {
		return pTraceId.getValue();
	}

	public String getPersistentKey() throws Exception {
		return pPersistentKey.getValue();
	}
	public static boolean IfLoginFails() {
		return bLoginFail;
	}

	public void setRegistrarIngreso(boolean b) {
		bRegistrarIngreso=b;
	}

	public BizLoginTrace() throws Exception {
	}

	public void createProperties() throws Exception {
		this.addItem("trace", pLoginTrace);
		this.addItem("login", pLogin);
		this.addItem("password", pPassword);
		this.addItem("ipaddress", ipaddress);
		this.addItem("id_app", pIdApp);
		this.addItem("tipo_app", pTipoApp);
		this.addItem("base_datos", pBaseDatos);
		this.addItem("validar_usuario", pValidarUsuario);
		this.addItem("company", pValidarUsuario);
		this.addItem("traceId", pTraceId);
		this.addItem("out_access", pOutAccess);
		this.addItem("persistent", pPersistent);
		this.addItem("persistent_key", pPersistentKey);
	}

	public void createFixedProperties() throws Exception {
		this.addFixedItem(KEY, "trace", "trace", true, true, 100);
		this.addFixedItem(FIELD, "login", "Usuario", true, true, 15, 0, JObject.JUPPERCASE);
		this.addFixedItem(FIELD, "password", "Clave", true, true, 20);
		this.addFixedItem(FIELD, "ipaddress", "ip", true, true, 20);
		this.addFixedItem(FIELD, "id_app", "Id App", true, true, 100);
		this.addFixedItem(FIELD, "tipo_app", "Tipo App", true, true, 100);
		this.addFixedItem(FIELD, "base_datos", "Base Datos", true, true, 100);
		this.addFixedItem(FIELD, "validar_usuario", "ValidarUsuario", true, true, 100);
		//this.addFixedItem(FIELD, "company", "Empreda", true, true, 15);
		this.addFixedItem(FIELD, "traceId", "traceId", true, true, 50);
		this.addFixedItem(FIELD, "persistent", "persistent", true, false, 1);
		this.addFixedItem(FIELD, "persistent_key", "persistent_key", true, false, 500);
		this.addFixedItem(VIRTUAL, "out_access", "Acceso externo", true, false, 1);
	}

	public String GetTable() {
		return "";
	}

	public void read(String idApp, String tipoApp, String zBaseDatos, String zTraceId,String zuser) throws Exception {
		try {
			if (!JBDatos.isDatabaseOpen()) {
				JAplicacion.GetApp().setForzedDatabase(zBaseDatos);
				JAplicacion.GetApp().openApp(idApp, tipoApp, true);
				JBDatos.GetBases().beginTransaction();
			}
			long trace = Long.parseLong(zTraceId.substring(0,zTraceId.indexOf("_")));
			String data = zTraceId.substring(zTraceId.indexOf("_")+1);
			GregorianCalendar now = new GregorianCalendar();
			GregorianCalendar cal = new GregorianCalendar();
			cal.setTimeInMillis(Long.parseLong(data));
			now.setTime(new Date());
			//if (cal.before(now))  throw new Exception ("La seccion ha expirado");
			
			if (zuser==null){
				BizLogTrace login=new BizLogTrace();
				login.addFilter("NROTRACE", trace);
				login.read();
				
				if (!login.pDatos.equals(data)) throw new Exception ("La seccion ha expirado");
				pLogin.setValue(login.getUsuario());
			}
			else 
				pLogin.setValue(zuser);
			BizUsuario user=this.findUser();
			this.oUsuario=user;
			BizUsuario.SetGlobal(this.oUsuario);
			this.oUsuario=BizUsuario.getUsr();
			this.oUsuario.validateLogin();
			pPassword.setValue(this.oUsuario.getPassword());
			pIdApp.setValue(idApp);
			pTipoApp.setValue(tipoApp);
			JAplicacion.GetApp().resetLogName();

			PssLogger.logInfo("Ingreso Registrado Subsession");
			bLoginFail=false;
		} catch (Exception eT) {
			PssLogger.logError(eT);
			BizLogTrace.register(BizLogTrace.C_LOG_FAILED, eT.getMessage(), pOutAccess.getValue());
			JBDatos.GetBases().commit();
      JBDatos.GetBases().beginTransaction();
			throw eT;
		}
	}
	public void readPerisistent(String idApp, String tipoApp, String zBaseDatos, String zKey) throws Exception {
		try {
			if (!JBDatos.isDatabaseOpen()) {
				JAplicacion.GetApp().setForzedDatabase(zBaseDatos);
				JAplicacion.GetApp().openApp(idApp, tipoApp, true);
				JBDatos.GetBases().beginTransaction();
			}
			Calendar haceUnMes = Calendar.getInstance();
			haceUnMes.setTime(new Date());
			haceUnMes.add(Calendar.MONTH,-1);
			
			String msg = JTools.decryptMessage(zKey);
			String[] parts = msg.split("_");
		
			BizLogTrace login=new BizLogTrace();
			login.addFilter("USUARIO", parts[0]);
			login.addFilter("company", parts[1]);
			login.addFilter("OPERACION", BizLogTrace.C_LOGINPERSISTENT);
			login.addFilter("DATOS", zKey);
			login.addFilter("FECHA",haceUnMes.getTime(),">=");// expire extra ademas del de la cookie
			login.read();

			// buscar un mejor lugar donde poner la depuracion para que no consuma tiempo en el login
			BizLogTrace depure=new BizLogTrace();
			depure.addFilter("OPERACION", BizLogTrace.C_LOGINPERSISTENT);
			depure.addFilter("FECHA",haceUnMes.getTime(),"<");
			depure.deleteMultiple();

			pLogin.setValue(login.getUsuario());

			BizUsuario user=this.findUser();
			this.oUsuario=user;
			BizUsuario.SetGlobal(this.oUsuario);
			this.oUsuario=BizUsuario.getUsr();
			this.oUsuario.validateLogin();
			
			pPassword.setValue(this.oUsuario.getPassword());
			pIdApp.setValue(idApp);
			pTipoApp.setValue(tipoApp);
			
			JAplicacion.GetApp().resetLogName();

			PssLogger.logInfo("Ingreso Registrado Subsession");
			bLoginFail=false;
		} catch (Exception eT) {
			PssLogger.logError(eT);
			BizLogTrace.register(BizLogTrace.C_LOG_FAILED, eT.getMessage(), pOutAccess.getValue());
			JBDatos.GetBases().commit();
      JBDatos.GetBases().beginTransaction();
			throw eT;
		}
	}
	public void processInsert() throws Exception {
//		try {

//			if (!JBDatos.isDatabaseOpen()) {
//				JAplicacion.GetApp().setForzedDatabase(pBaseDatos.getValue());
//				JAplicacion.GetApp().openApp(this.pIdApp.getValue(), this.pTipoApp.getValue(), true);
//				JBDatos.GetBases().beginTransaction();
//			}


			 BizUsuario user=this.findUser();

			if (this.hasToValidatePassword()) {
				user.validatePassword(pPassword.getValue());
			}

			this.checkIn(user);

			this.oUsuario=user;
			BizUsuario.SetGlobal(this.oUsuario);
			this.oUsuario=BizUsuario.getUsr();
			this.oUsuario.validateLogin();

			JAplicacion.GetApp().resetLogName();

			PssLogger.logInfo("Ingreso Registrado");
			bLoginFail=false;

			GregorianCalendar cal = new GregorianCalendar();
			cal.setTime(new Date());
			cal.add(Calendar.SECOND, 30);
			String data = ""+cal.getTimeInMillis();

			if (pPersistent.getValue()) {
				String key = buildPeristentKey();
				BizLogTrace tp = BizLogTrace.register(BizLogTrace.C_LOGINPERSISTENT, data, pOutAccess.getValue(),key);
				if (tp!=null) {
					pTraceId.setValue( tp.getNroTrace()+"_"+data);
					pPersistentKey.setValue(tp.getKey());
				}
			}
			else {
				BizLogTrace t = BizLogTrace.register(BizLogTrace.C_LOGIN, data, pOutAccess.getValue(),null);
				if (t!=null) {
					pTraceId.setValue( t.getNroTrace()+"_"+data);
				}
			}
			

			return;
//		} catch (Exception eT) {
//			PssLogger.logInfo(eT.getMessage());
//			BizLogTrace.register(BizLogTrace.C_LOG_FAILED, eT.getMessage(), pOutAccess.getValue());
//			JBDatos.GetBases().commit();
//			JBDatos.GetBases().beginTransaction();
//			throw eT;
//		}
	}
	public String buildPeristentKey() throws Exception {
	  return (BizUsuario.getCurrentUser()+"_"+BizUsuario.getUsr().getCompany()+"_"+JDateTools.CurrentDate()+"_"+(Math.random()*System.currentTimeMillis())%11777);
	}

	public void checkIn(BizUsuario zUser) throws Exception {
		if (!bRegistrarIngreso) return;
		zUser.pFechaUltimoIngreso.setValue(zUser.todayGMT());
		// zUser.pHoraUltimoIngreso.setValue(JDateTools.CurrentTime("HH:mm:ss"));
		zUser.pIntentosClave.setValue(0);
		zUser.pFechaInhab.setValue((Date) null);
		zUser.pHoraInhab.setValue("");
		if (ipaddress.getValue().equalsIgnoreCase("")) zUser.pLoginSource.setValue(java.net.InetAddress.getLocalHost().getHostName());
		else zUser.pLoginSource.setValue(ipaddress.getValue());
		zUser.updateRecord();
	}

	protected  boolean hasToValidatePassword() throws Exception {
		return pValidarUsuario.getValue()||pValidarUsuario.isNull() ;
	}

	protected BizUsuario findUser() throws Exception {
		BizUsuario user=new BizUsuario();
		user.dontThrowException(true);
		if (user.Read(pLogin.toString().toUpperCase())) 
			return user;
		
		if (this.isLoginAsAdmin())
			return BizUsuario.createAdminUser();
		
		if (this.isAutoLogin()) {
			user=BizUsuario.createUserAutoLogin();
			if (user==null) JExcepcion.SendError(getMessage("Usuario Inválido"));
			return user;
		} 
		
		JExcepcion.SendError(getMessage("Usuario Inválido"));
		return null;
	}

	public BizUsuario getObjUsuario() throws Exception {
		if (oUsuario!=null) return oUsuario;
		BizUsuario usuario=new BizUsuario();
		usuario.Read(pLogin.getValue());
		this.oUsuario=usuario;
		return oUsuario;
	}

	public BizUsuario getObjUsuarioByMail() throws Exception {
		if (oUsuario!=null) return oUsuario;
		BizUsuario usuario=new BizUsuario();
		usuario.dontThrowException(true);
		if (!usuario.readByMail(pLogin.getValue())) return null;
		this.oUsuario=usuario;
		return oUsuario;
	}
	protected  boolean isLoginAsAdmin() throws Exception {
		return pLogin.getValue().equals(BizUsuario.C_ADMIN_USER);
	}

	// public boolean isLoginGuest() throws Exception {
	// return pLogin.getValue().equals(BizUsuario.GUEST_NAME);
	// }

	public boolean isAutoLogin() throws Exception {
		return pLogin.getValue().equals(BizPssConfig.getPssConfig().getAutoLogin());
	}

	/*
	 * private void notifyLogIn(BizUsuario oUsr) throws Exception { if (aLogInListener == null) return; Iterator oIt = aLogInListener.iterator(); while (oIt.hasNext()) { JSubmit oListener = (JSubmit)oIt.next(); oListener.submit(new JEvent(oUsr, 0, "")); } }
	 */
	public static BizLoginTrace createLoginGuest() throws Exception {
		BizLoginTrace login=new BizLoginTrace();
		String	loginClass=BizPssConfig.getPssConfig().getLoginTraceClass();
		if (loginClass!=null && !loginClass.equals("")) 
			login = (BizLoginTrace)Class.forName(loginClass).newInstance();
		login.SetLogin(BizPssConfig.getPssConfig().getAutoLogin());
		login.SetPassword(BizPssConfig.getPssConfig().getAutoLogin());
		login.setPersistent(false);
		BizLogTrace.register(BizLogTrace.C_LOGIN, "Guest Login", login.pOutAccess.getValue());
		return login;
	}
	public String getMessage(String zMsg) throws Exception {
		String msg = 	BizUsuario.retrieveSkinGenerator().getMessage(zMsg, null);
		return msg;
	}
}
