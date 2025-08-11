package pss.common.restJason;

import java.lang.annotation.Annotation;

import pss.common.restJason.apiRestBase.apiStatus;
import pss.common.restJason.apiRestBase.apiTokenData;
import pss.common.restJason.apiRestBase.apiUserData;
import pss.common.restJason.apiRestBase.apiWebServiceTools;
import pss.common.restJason.apiUser.BizApiUser;
import pss.common.restJason.apiUser.apiUserDetails.BizApiUserDetail;
import pss.common.security.BizUsuario;
import pss.core.JAplicacion;
import pss.core.data.interfaces.connections.JBDatos;
import pss.core.tools.JExcepcion;
import pss.core.tools.PssException;
import pss.core.tools.PssLogger;

public class JServiceApiResolver {
	
	
	
	public apiStatus processLogin(String sToken,Class classElement) throws Exception {
		
		apiTokenData token = apiWebServiceTools.parseToken(sToken);		
		
		BizUsuario usuario = null;
		apiStatus response = new apiStatus();
		try {

			usuario = logIn(token.getUserData());
			if (usuario.getIdPersona() > 0) {
				PssLogger.logDebug("Usuario [" + usuario.getObjPersona().getNombreCompleto() + "] logeado ok");
			} else {
				PssLogger.logDebug("Usuario [" + usuario.GetUsuario() + "] logeado ok");
			}

			this.checkToken(usuario.GetUsuario(), sToken, classElement);

			
			response.setStatus(apiStatus.STATUS_CODE_OK,"OK");
			response.setCompany(usuario.getCompany());
			return response;

		} catch (Exception eee) {
			// Si el usuario es null es porque se dio una excepcion antes de loguearlo, y si tengo configurado tengo que loguear
			// el msg de request. Si el usuario no es null se deberia haber logueado el msg de request luego del login.
			if (usuario == null) {

				PssLogger.logDebug("Request Msg: [" + token.getUserData().getUsuario() + "]");
			}
			PssLogger.logError(eee);
			try {
				JBDatos.GetBases().rollback();
			} catch (Exception e2) {
				throw eee;	
			}
			if (eee instanceof PssException) {
				// Error controlado
				PssException pse = (PssException) eee;
				response.setStatus(apiStatus.STATUS_CODE_ERROR, "PssException Error Code [" + pse.getCode() + "] - Msg [" + pse.getDescription() + "]");
			} else {
				response.setStatus(apiStatus.STATUS_CODE_ERROR, eee.getMessage());
			}

		}

		return response;
	}
	
	protected BizUsuario logIn(apiUserData zWSUser) throws Exception {
		apiWebServiceTools ws = new apiWebServiceTools();

		if (zWSUser == null) {
			return ws.logIn("consulta", "consulta");
		}

		return ws.logIn(zWSUser.getUsuario(), zWSUser.getPassword());
	}

	public apiStatus openAppAndLogin(String sToken,Class classElement ) throws Exception {
		this.openApp();
		apiStatus resp;
		resp = this.processLogin(sToken,classElement);
		if (!resp.getStatus_code().equals(apiStatus.STATUS_CODE_OK)) {
			JExcepcion.SendError(resp.getStatus_msg());
		}
		return resp;
	}
	

	
	public void openApp() throws Exception {
		this.openApp("apiJson","Service"); 
	}
	
	public void openApp(String AppId, String command) throws Exception {
		JAplicacion.openSession();
		JAplicacion.GetApp().openApp(AppId + "." + command, JAplicacion.AppService(), true);
		JAplicacion.SetWaitForClose(true);
		JBDatos.GetBases().beginTransaction();
	}
	
	public void closeApp() {
		try {
			JBDatos.GetBases().commit();
			JAplicacion.GetApp().closeApp();
			JAplicacion.closeSession();
		} catch (Exception e3) {
		}
	}
	
	
	private void checkToken(String usuario, String token,Class classElement) throws Exception {
		BizApiUser.checkToken(usuario, token);
		Class anotationType = javax.ws.rs.Path.class;
		Annotation[] anotta = classElement.getAnnotationsByType(anotationType);
		if (anotta.length<=0) 
			JExcepcion.SendError( "No existe elemento raíz en el Path de la clase servidora de API-Json" );
		
		String service = ((javax.ws.rs.Path)anotta[0]).value();
		String sclass = classElement.getCanonicalName();
		BizApiUserDetail.checkTokenService(usuario, sclass + service);
		
	}
	
	
}
