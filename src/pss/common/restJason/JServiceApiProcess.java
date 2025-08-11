package pss.common.restJason;

import java.lang.annotation.Annotation;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.json.JSONObject;

import com.google.gson.Gson;

import pss.common.restJason.apiReqHistory.BizApiRequestHistory;
import pss.common.restJason.apiRestBase.apiBasicRequest;
import pss.common.restJason.apiRestBase.apiStatus;
import pss.common.restJason.apiRestBase.apiTokenData;
import pss.common.restJason.apiRestBase.apiUserData;
import pss.common.restJason.apiRestBase.apiWebServiceTools;
import pss.common.restJason.apiUser.BizApiUser;
import pss.common.restJason.apiUser.apiUserDetails.BizApiUserDetail;
import pss.common.security.BizUsuario;
import pss.core.JAplicacion;
import pss.core.services.JExec;
import pss.core.services.records.JRecord;
import pss.core.tools.JExcepcion;
import pss.core.tools.PssLogger;
import pss.core.win.JWin;

public class JServiceApiProcess {

	private JServiceApiRequest request = null;
	private String token;
	private String srequest = "";
	private String sCompany;
	private JSon json;
	private UriInfo uri;
	private HttpServletRequest httpReq=null;
	private Response response = null;

	private String status=null;
	private String statusMsg=null;
	private JRecord internal = null;


	public void setCompany(String v) { this.sCompany = v; }
	public String getCompany() { return this.sCompany ; }
	public void setRequest(String v) { this.srequest = v; }
	public void setUri(UriInfo v) { this.uri= v; }
	public void setToken(String v) { this.token= v; }
	public void setHttpReq(HttpServletRequest  v) { this.httpReq= v; }
	public void setInternal(JRecord v) { this.internal= v; }
	public String getToken() { return this.token; }
	public String getRequest() { return this.srequest; }
	public JServiceApiRequest getApiRequest() { return this.request; }
	public UriInfo getUri() { return this.uri; }
	public HttpServletRequest getHttpReq() { return this.httpReq; }
	
	public JServiceApiProcess() {
	}

	public String getApiName() throws Exception {
		return "Api";
	}
	
	public void setRequest(JServiceApiRequest v) { 
		this.request = v; 
		this.setRequest(new Gson().toJson(request));
	}
	
	public JSon getJson() { 
		if (this.json!=null) return json;
		return (this.json=new JSon(new JSONObject(this.getRequest()))); 
	}

	public String getRemoteAddr() throws Exception { 
		if (this.httpReq==null) return "";
		return this.httpReq.getRemoteAddr();
	}

	public Response process(JServiceApi api, UriInfo uriInfo, HttpServletRequest httpReq, String srequest) throws Exception {
		return this.process(api, uriInfo, httpReq, null, srequest);
	}
	
	public Response process(JServiceApi api, UriInfo uriInfo, HttpServletRequest httpReq, JServiceApiRequest request, String srequest) throws Exception {
		try {
			this.startRest(api, uriInfo, httpReq, request, srequest);
			this.execProcess();
			this.finishOk();
			return this.response;
		} catch (Exception e) {
			this.finishError(e);
			return this.response;
		}
	}
	
	private void startRest(JServiceApi api, UriInfo uriInfo, HttpServletRequest httpReq, JServiceApiRequest request, String srequest) throws Exception {
		this.setUri(uriInfo);
		this.setHttpReq(httpReq);
		this.setToken(this.findReqParam("ACCESS_TOKEN"));		
		apiTokenData tk = apiWebServiceTools.parseToken(this.getToken());
		this.setCompany(tk.getCompany());
		this.setRequest(request); // dejar de usar esta modalidad
		this.setRequest(srequest);
		this.openAppAndLogin(api.getClass(), this.getApiName());	
		this.startHistory();
	}
		
	protected void execProcess() throws Exception {
		JExec exec = new JExec(null, null) {
			public void Do() throws Exception {
				response = process();
			};
		};
		exec.execute();
	}
		
	protected Response process() throws Exception {
		return null;
	}
	
//	public Response processQuery(String sToken, UriInfo uri) throws Exception {
//		this.setToken(apiWebServiceTools.parseToken(sToken));		
//		this.setCompany(this.getToken().getCompany());
//		this.setUri(uri);
//		PssLogger.logInfo(this.getRequest());
//		return this.processQuery();
//	}

//	public Response processQuery() throws Exception {
//		return null;
//	}
	
	public String findParam(String param) throws Exception {
		if (this.uri==null) 
			JExcepcion.SendError("Definir la variable UriInfo");
		String v = (String)this.getUri().getPathParameters().get(param).get(0);
		if (v==null) 
			JExcepcion.SendError("Variable no definida: " +param);
		return v;
	}

	public String findReqParam(String param) throws Exception {
		if (this.uri==null) 
			JExcepcion.SendError("Definir la variable UriInfo");
		String v = (String)this.getUri().getQueryParameters().get(param).get(0);
		if (v==null) 
			JExcepcion.SendError("Variable no definida: " +param);
		return v;
	}

	public void processLogin(String sToken, Class classElement) throws Exception {
		
		apiTokenData token = apiWebServiceTools.parseToken(sToken);		
		
		BizUsuario usuario = this.logIn(token.getUserData());
		PssLogger.logDebug("Usuario [" + usuario.GetUsuario() + "] logeado ok");

		this.checkToken(usuario.GetUsuario(), sToken, classElement);
		this.setCompany(usuario.getCompany());
	}
	
	protected BizUsuario logIn(apiUserData user) throws Exception {
		apiWebServiceTools ws = new apiWebServiceTools();
		return ws.logIn(user.getUsuario(), user.getPassword());
	}

	public void openAppAndLogin(Class classElement, String name ) throws Exception {
		this.openApp(name);
		this.processLogin(this.getToken(), classElement);
	}

	
	public void checkCompany(apiBasicRequest request) throws Exception {
		// Si la transaccion recibe el filtro company, la company debe coincidir con el token
		// para que un token válido no consulte sobre otra company.
		String sCompany = request.getFilters().get("company");
		if (sCompany==null) {
			sCompany = this.getCompany();
			request.addFilter("company", sCompany);
		}
		if (!sCompany.equals(this.getCompany()))
			 JExcepcion.SendError("La Agencia habilitada para el token y el filtro, no coinciden");
	}
	
	public void openApp(String name) throws Exception {
		JAplicacion.openSession();
//		JAplicacion.GetApp().setLogFileName(JPath.PssPathLog() + "/json/" + name + ".log");
		JAplicacion.GetApp().openApp(name, JAplicacion.AppService(), true);
		JAplicacion.SetWaitForClose(true);
		Thread.currentThread().setName(name);
	}
	
	public void closeApp() {
		try {
//			JBDatos.GetBases().commit();
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
	
	
//	public Response response(apiBasicResponse res) throws Exception {
//		this.execHistory("OK");
//		this.closeApp();
//    return this.build(res);
//	}

//	public Response response(Response res) throws Exception {
//		this.execHistory("OK");
//		this.closeApp();
//		return res;
//	}



	private String findMessage(Exception e) throws Exception {
		String msg = e.getMessage();
		if (msg==null) msg="Puntero a nulo";
		return msg;
	}
	
//	public Response responseError(Exception e) throws Exception {
//		PssLogger.logError(e);
//		this.execHistory(e);
//		this.closeApp();
//		return makeResponse(apiStatus.STATUS_CODE_ERROR, msg);
//	}
	
	public Response responseOk() throws Exception {
		return responseOk(null);
	}
	public Response responseOk(String msg, JRecord rec) throws Exception {
		this.setInternal(rec);
		return makeOk(msg);
	}
	public Response responseOk(String msg) throws Exception {
		return makeOk(msg);
	}
	public static Response makeOk(String msg) throws Exception {
		return buildOk(apiStatus.makeOk(msg));
	}
	public static Response makeError(String msg) throws Exception {
		return buildError(apiStatus.makeError(msg));
	}
	
//	public static Response makeOk(String msg) throws Exception {
//		return makeResponse(apiStatus.STATUS_CODE_OK, msg);
//	}
//	public static Response makeError(String msg) throws Exception {
//		return makeResponse(apiStatus.STATUS_CODE_ERROR, msg);
//	}
	
//	public Response makeResponse(String status, String msg) throws Exception {
//		apiStatus st = new apiStatus();
//		st.setStatusCode(status);
//		st.setStatusMsg(msg);
//		if (status.equals(apiStatus.STATUS_CODE_ERROR))
//			return makeError(st);
//		if (status.equals(apiStatus.STATUS_CODE_OK))
//			return makeOk(st);
//		JExcepcion.SendError("No contemplado");
//		return null;
//	}


	public static Response buildOk(Object res) throws Exception {
    return Response.ok().entity(res).build();
	}

	public static Response buildError(Object res) throws Exception {
    return Response.serverError().entity(res).build();
	}

	private void startHistory() throws Exception {
		JExec exec = new JExec(null,null) {
			public void Do() throws Exception {
				doHistory();
			}
		};
		exec.execute();
	}
	
	private void finishOk() throws Exception {
		this.status=BizApiRequestHistory.OK;
		this.statusMsg="OK";
		this.execUpateHistory();
		this.closeApp();
	}
	
	public void finishError(Exception e) throws Exception {
		PssLogger.logError(e);
		this.status=BizApiRequestHistory.ERROR;
		this.statusMsg=this.findMessage(e);
		this.response = makeError(statusMsg);
		this.execUpateHistory();
		this.closeApp();
	}
	
	
	private BizApiRequestHistory history=null;
	public void doHistory() throws Exception {
		BizApiRequestHistory hist = new BizApiRequestHistory();
		hist.setOper(BizApiRequestHistory.ENTRANTE);
		hist.setCompany(this.getCompany());
		hist.setRequest(this.getRequest());
		hist.setUser(BizUsuario.getUsr().GetUsuario());
		hist.setUrl(this.getUri().getRequestUri().toString());
		hist.setMethod(this.getApiName());
		hist.setType(this.getHttpReq().getMethod());
		hist.setAddressSource(this.getRemoteAddr());
		hist.setStatus(BizApiRequestHistory.PENDING);
		hist.setStatusMessage("En Proceso...");
		hist.processInsert();
		BizUsuario.getUsr().putObjectMap(BizUsuario.MAP_CURRENT_JSON, hist);
		this.history=hist;
	}

	public void execUpateHistory() throws Exception {
		JExec exec = new JExec(null,null) {
			public void Do() throws Exception {
				updateHistory();
			}
		};
		exec.execute();
	}

	public void updateHistory() throws Exception {
		if (this.history==null) return;
		BizApiRequestHistory rec = new BizApiRequestHistory();
		rec.read(this.history.getId());
		rec.setStatus(this.status);    	
		rec.setStatusMessage(this.getStatusMsg());
		rec.setInternalKey(this.makeInternalKey());
		rec.setResponse(this.getOutputMsg());
		rec.pushResponse();
		rec.update();
	}	
	
	public String makeInternalKey() throws Exception {
		if (this.internal==null) return "";
		return this.internal.getClass().getCanonicalName()+"_"+this.internal.getKeyListValue();
	}
	

	public String getStatusMsg() throws Exception {
		if (this.statusMsg==null) return "";
		return this.statusMsg;
	}
	public String getOutputMsg() throws Exception {
		if (this.response==null) return "";
		return this.response.getEntity().toString();
	}
	
}
