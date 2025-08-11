/*
 * Created on 06-jun-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.platform.actions.resolvers;

import javax.security.sasl.AuthenticationException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.cocoon.environment.Request;
import org.apache.cocoon.environment.http.HttpEnvironment;

import pss.common.security.BizLoginTrace;
import pss.common.security.BizUsuario;
import pss.common.security.BlankPasswordException;
import pss.common.security.ExpiredPasswordException;
import pss.common.security.WrongPasswordException;
import pss.core.JAplicacion;
import pss.core.data.BizPssConfig;
import pss.core.tools.collections.JMap;
import pss.www.platform.actions.results.JWebActionResult;
import pss.www.platform.applications.JWebApplication;
import pss.www.platform.applications.JWebApplicationSession;
import pss.www.platform.applications.JWebServer;
import pss.www.platform.applications.JwtTokenUtil;

/**
 * 
 * 
 * Created on 06-jun-2003
 * 
 * @author PSS
 */

public abstract class JAbstractLoginResolver extends JFrontDoorActionResolver {

	@Override
	protected String getCharacterEncoding() {
		return "ISO-8859-1";
	}

//	public String calculeServerAddress() throws Exception {
//		String local = BizPssConfig.getPssConfig().getClusteringLocal();
//		if (local.equals("")) return null;
//		String uri = null;
//		BizUsuario user = getSession().getUser();
//		if (user==null) return null;
//		String appServer = user.getApplicationServer();
//		if (appServer==null) return null;
//		if (appServer.equalsIgnoreCase(local)) return null;
//		uri = BizPssConfig.getPssConfig().getClusteringAddress(appServer);
//		if (uri.endsWith("/")) uri=uri.substring(0,uri.length()-1);
//		return uri;
//	}
//	
//	public JWebActionResult goToServer(String uri,String traceid) throws Exception {
//		return this.getRedirector().goExternalLink(uri+"/remotelogin?traceid="+traceid);
//	}
//		
//	
//	public JWebActionResult opensubsession(String session, String urlredirect) throws Throwable {
//		
//		Request oCocoonRequest=this.getServletRequest();
//		HttpSession oldServletSession=oCocoonRequest.getSession(false);
//		JWebApplicationSession oOldSession=this.getApplication().getSessionManager().getSession(oldServletSession,session,null);
//		if (oOldSession==null) {
//			this.logError("The servlet was unable to create a new session!!!");
//			return this.endRequest();
//		}
//		String zUser = oOldSession.getUser().GetUsuario();
//		String zBase = oOldSession.getDatabase().GetDatabaseDefault();
//		HttpSession oServletSession=oCocoonRequest.getSession(true);
//
//  	JWebApplicationSession oNewSession=null;
//		try {
//
//	  	
//	  	oNewSession=new JWebApplicationSession(oOldSession.getIpAddress(), oServletSession, JBasicWebActionResolver.getSessionID(oCocoonRequest,oServletSession), oCocoonRequest.getParameter("subsession"));
//			this.getApplication().getSessionManager().openSubsession(oOldSession,oNewSession, oOldSession.getUser().GetUsuario(),oOldSession.getTraceId()) ;
//			this.setSession(oNewSession);
//			if (oNewSession.getUser().hasNeverExpireSession())
//				oNewSession.setTimeout(JWebApplicationSession.NEVER_EXPIRES);
//			else
//				oNewSession.setTimeout((int)oOldSession.getUser().getExpire());
//			this.logInfo("User '"+zUser+"' logged in");
//			// initialize the UI coordinator from the first request
//			this.getSession().getUICoordinator().initialize(this.getRequest());
//			
//			if (zBase!=null && !zBase.equals("")) {
//				JBDatos.GetBases().SetDatabaseDefault(zBase);
//	//			JBDatos.GetBases().openDatabases();
//			}
//			
//			oNewSession.fillConfig(zUser);
//			String uri  = this.calculeServerAddress();
//			if (uri!=null) return goToServer(uri,oNewSession.getTraceId());
//		
//			JWebTreeSelection oSelection=new JWebTreeSelection("", JCollectionFactory.<String> createList());
//			this.getSession().getUICoordinator().getTreeCoordinator().setSelection(oSelection);
//			// go home !!
//			BizUsuario.SetGlobal(oNewSession.getUser());
//			oNewSession.getUser().setVisionCustomMenu(urlredirect);
//			JWebActionResult oResult=this.getRedirector().goHomePage(urlredirect);
//			oNewSession.setOpen();
//			return oResult;
//		} catch (BlankPasswordException e) {
//			this.logInfo("The password has blanqued for user: "+zUser);
//			return this.getRedirector().goPasswordChangePage(zUser);
//		} catch (ExpiredPasswordException e) {
//			this.logInfo("The password has expired for user: "+zUser);
//			return this.getRedirector().goPasswordChangePage(zUser);
//		} catch (WrongPasswordException e) {
//			this.logInfo(e.getMessage());
//			try { 			 
//				this.getApplication().getSessionManager().closeSessionById(oNewSession.getSessionId(),oCocoonRequest.getParameter("subsession"),"wrong password");			
//			} catch (Exception eee ) {			  this.logInfo("no session with id :"+ e.getMessage());			}			throw e;
//		} finally {
//			if (oNewSession!=null&&oNewSession.isCurrent()) {
//				this.setSessionToNull();
//			}
//		}
//		
//	}	
//	
	public JWebActionResult performLogin(String zUser, String zPassword, String zBase, String loginClass, boolean persistent) throws Throwable {
		return performLogin(zUser, zPassword, zBase, loginClass, persistent, null);
	}

	public JWebActionResult performLogin(String zUser, String zPassword, String zBase, String loginClass, boolean persistent, JMap<String, Object> attributes) throws Throwable {
		if (this.getSession() != null) {
			throw new RuntimeException("A login resolver should never have session until the login completed.");
		}
		this.logInfo("Trying to login '" + zUser + "'...");

		Request oCocoonRequest = this.getServletRequest();
		String ipaddress = oCocoonRequest.getRemoteAddr();
		HttpSession oServletSession = oCocoonRequest.getSession(true);

		if (oServletSession == null) {
			this.logError("The servlet was unable to create a new session!!!");
			return this.endRequest();
		}
		JWebApplicationSession session = new JWebApplicationSession(ipaddress, oServletSession, JBasicWebActionResolver.getSessionID(oCocoonRequest, oServletSession), "");
		session.initialize();
		try {
		
			if (isValidUser(zUser, zPassword, ipaddress, loginClass, persistent)) {
				String token = JwtTokenUtil.generateToken(zUser);
				
				// Configurar la cookie con el token
				HttpServletResponse response = (HttpServletResponse) this.getObjectModel().get(HttpEnvironment.HTTP_RESPONSE_OBJECT);
				Cookie authCookie = new Cookie("Authorization", token);
				authCookie.setHttpOnly(true);
				authCookie.setSecure(true);
				authCookie.setPath("/");
				response.addCookie(authCookie);

				response.getWriter().write("Token set in cookie.");

				oCocoonRequest.setAttribute("session", session);
				JWebApplication app =JWebServer.getInstance().getWebApplication(null);
				app.getStadistics().addInfoSession(session);

			} else {
				throw new AuthenticationException("Invalid credentials");
			}
			JWebActionResult oResult = this.getRedirector().goHomePage(null);
			return oResult;
		} catch (BlankPasswordException e) {
			this.logInfo("The password has blanqued for user: " + zUser);
			return this.getRedirector().goPasswordChangePage(zUser);
		} catch (ExpiredPasswordException e) {
			this.logInfo("The password has expired for user: " + zUser);
			return this.getRedirector().goPasswordChangePage(zUser);
		} catch (WrongPasswordException e) {
			this.logInfo(e.getMessage());
			throw e;
		} finally {
			session.finalize();
		}
	}

	private boolean isValidUser(String zUser, String zPassword, String ipaddress, String loginClass, boolean persistent) throws Exception {
		BizLoginTrace login = new BizLoginTrace();
		if (loginClass == null) {
			loginClass = BizPssConfig.getPssConfig().getLoginTraceClass();
		}
		if (loginClass != null && !loginClass.equals(""))
			login = (BizLoginTrace) Class.forName(loginClass).newInstance();
		login.SetLogin(zUser);
		login.setPersistent(persistent);
		login.SetPassword(zPassword);
		login.setIpAddress(ipaddress);
		login.SetIdApp(JAplicacion.AppFrontEndWeb());
		login.SetTipoApp(JAplicacion.AppTipoWeb());

		login.execProcessInsert();
		
		if (login.isOutAccess() && !BizUsuario.getUsr().canOutAccess())
			throw new Exception("No tiene autorización para loguearse desde fuera de la intranet. Si lo necesita comuniquese con el administrador.");

		BizUsuario.SetGlobal(login.getObjUsuario());
		login.getObjUsuario().createWebProfile();
		return true;
	}

	/**
	 * Utility method to test the given user name and password from the application front door, when there is no current session.<br>
	 * It checks the given user exists and its password is correct, and, if something fails, thows an exception.
	 */

//	private JWebActionResult logIn(JSessionedApplication zApplication, String zUser, String zPassword) throws Exception {
//
//		try {
//			JAplicacion.openSession();
//
//			BizLoginTrace login=new BizLoginTrace();
//			login.SetLogin(zUser);
//			login.SetPassword(zPassword);
//			login.SetIdApp(zApplication.getName());
//			login.SetTipoApp(zApplication.getType());
//			login.execProcessInsert();
//			return null;
//
//		} catch (BlankPasswordException e) {
//			this.logInfo("The password has blanqued for user: "+zUser);
//			return this.getRedirector().goPasswordChangePage(zUser);
//		} catch (ExpiredPasswordException e) {
//			this.logInfo("The password has expired for user: "+zUser);
//			return this.getRedirector().goPasswordChangePage(zUser);
//		} finally {
//			JBDatos.GetBases().closeDatabases();
//			JAplicacion.closeSession();
//		}
//	}

	/**
	 * Utility method to load the Pss modules.
	 */
	/*
	 * private void loadPssActions(JWebApplicationSession zNewSession) throws Exception { this.logInfo("Loading actions for current user..."); new GuiModuloPss().getActionMap(); }
	 */

}
