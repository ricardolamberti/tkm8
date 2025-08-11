package pss.www.platform.actions.resolvers;

import java.util.Map;

import pss.common.security.BizUsuario;
import pss.core.services.records.JFilterMap;
import pss.core.tools.JTools;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;
import pss.core.win.JBaseWin;
import pss.core.win.actions.BizAction;
import pss.core.win.submits.JAct;
import pss.www.platform.actions.JWebActionFactory;
import pss.www.platform.actions.JWinPackager;
import pss.www.platform.actions.results.JWebActionResult;

public class JDoInternalRequestResolver extends JDoPssActionResolver {

  @Override
	protected String getBaseActionName() {
    return "do.internalrequest";
  }

	@Override
	protected boolean isAjax() {return false;}
  @Override
	protected boolean isResetRegisteredObjects() {return false;}
  @Override
	protected boolean isActionHistoryAffected() throws Exception {return false;} 
//  @Override
//	protected boolean isRefreshHistory() throws Exception {
//		return true;
//	}

  @Override
	protected boolean isNewRecordEnabled() {return false;}
  
	protected boolean isFirstAccess() throws Exception {
		return false;
	}

	 static class JRequest {
		  JBaseWin win;
		  JAct act;
			int action;
			String usuario;
 			public JRequest(JBaseWin w,int a,String usr) throws Exception {
				this.win=w;
				this.action=a;
				this.usuario=usr;
			}
 			
 			public String serialize() throws Exception {
        return JWebActionFactory.ACTION_DATA_PREFIX+"object_owner="+new JWinPackager(null).baseWinToJSON(win)+"&"+JWebActionFactory.ACTION_DATA_PREFIX+"act="+action+"&"+JWebActionFactory.ACTION_DATA_PREFIX+"usr="+JTools.encodeURL(usuario);
 			}
 			

		};

	public JRequest unserializeRequest() throws Exception {
		return new JRequest(null, Integer.parseInt(getRequest().getArgument(JWebActionFactory.ACTION_DATA_PREFIX+"act")), getRequest().getArgument(JWebActionFactory.ACTION_DATA_PREFIX+"usr"));
 	}

	static JMap<String, JRequest> internalRequest;
  public static JMap<String, JRequest> getInternaRequest() throws Exception {
  	if (internalRequest!=null) return internalRequest;
  	internalRequest=JCollectionFactory.createMap();
  	return internalRequest;
  }
  public static void removeInternaRequest(String id) throws Exception {
  	getInternaRequest().removeElement(id);
  }
  public static String addInternaRequest(String id, JBaseWin w,int a,String u) throws Exception {
  	JRequest req= new JRequest(w, a, u);
  	String extraArguments = req.serialize().toString();
  	getInternaRequest().addElement(id,req);
  	return extraArguments;
  }
  public static void getInternaRequestSetAct(String id,JAct act) throws Exception {
  	getInternaRequest().getElement(id).act=act;
  }
  public static JRequest getInternaRequest(JDoInternalRequestResolver resolver,String id) throws Exception {
  	JRequest req = getInternaRequest().getElement(id);
  	if (req==null && resolver!=null) {
  		req = resolver.unserializeRequest();
  		getInternaRequest().addElement(id,req);
  	}
  	return req;
  }
  public JBaseWin getInternaRequestBaseWin(String id) throws Exception {
  	JBaseWin win = getInternaRequest(this,id).win;
  	if (win==null) {
//			getRequest().addDataBundle("act_owner", getRequest().getArgument("dg_object_owner"));
//			JWebActionData bundle=loadWinBundle();
//			if (bundle==null) return null;
			win= winFactory.getBaseWinFromBundle(getRequest().getArgument(JWebActionFactory.ACTION_DATA_PREFIX+"object_owner"));
		}

  	return win;
  }
  public static int getInternaRequestAction(JDoInternalRequestResolver resolver,String id) throws Exception {
  	return getInternaRequest(resolver,id).action;
  }
  public static JAct getInternaRequestAct(JDoInternalRequestResolver resolver,String id) throws Exception {
  	return getInternaRequest(resolver,id).act;
  }
  public static BizUsuario getInternaRequestUsuario(JDoInternalRequestResolver resolver,String id) throws Exception {
  	BizUsuario usr = new BizUsuario();
  	usr.unSerialize(BizUsuario.readCertificado(getInternaRequest(resolver,id).usuario));
  	return usr;
  }
	protected void assignTarget(JAct action) throws Exception {
	}
	JBaseWin actionOwner;
	BizAction action;
	BizUsuario usuario;

	
	protected void takeApplicationData(Map<String, Object> zObjectModel) throws Throwable {
		// resolve the application, context and session for the request
		super.takeApplicationData(zObjectModel);
		String id= getRequest().getArgument("id");
		usuario=getInternaRequestUsuario(this,id);
//		JApplicationSessionManager sessionManager =  JWebServer.getInstance().getWebApplication(null).getSessionManager();
//		JWebApplicationSession session=sessionManager.hasAnySessionWithUser(usuario.GetUsuario());
//		if (session==null) {
//			Request oCocoonRequest=this.getServletRequest();
//			String ipaddress = oCocoonRequest.getRemoteAddr();
//			HttpSession oServletSession=oCocoonRequest.getSession(true); //OJO
//
//			session=new JWebApplicationSession(oCocoonRequest.getRemoteAddr(), oServletSession, JBasicWebActionResolver.getSessionID(oCocoonRequest,oServletSession), oCocoonRequest.getParameter("subsession"));
//			this.getApplication().getSessionManager().openSession(session, usuario.GetUsuario(), usuario.getPasswordDecrypt(),  ipaddress, null, false,null) ;
//			this.setOnlySession(session);
//			this.getSession().getUICoordinator().initialize(this.getRequest());
//			session.setOpen();
//			
//		}
//		else
//			setOnlySession(session);
	}

	@Override
	protected JWebActionResult perform() throws Throwable {
		String id= getRequest().getArgument("id");
		actionOwner=getInternaRequestBaseWin(id);
		action=actionOwner.getActionMap().findAction(JDoInternalRequestResolver.getInternaRequestAction(this,id));

		JFilterMap map = this.createFilterMap().getElement(action.getProviderName());
		if (map==null) map = this.createFilterMap().getElement("filter_pane");
		action.setObjFilterMap(map);
		action.setFirstAccess(false);
		JDoInternalRequestResolver.getInternaRequestSetAct(id,getSubmit(actionOwner, action));
		JWebActionResult nextStep=this.performAction(actionOwner, action);
		
		if (nextStep!=null) 
			return nextStep;
		
		return this.goOn();
	}
	
	public boolean isPreview() {
		return true;
	}

	protected String getTransformer() throws Exception {
		String sRequestedOutput=JWebActionFactory.getSerializerFor(this.getRequest());
		if (sRequestedOutput.equalsIgnoreCase("htmlfull")) return "resources/stylesheets/html/page.printer.xsl";
		if (sRequestedOutput.equalsIgnoreCase("html")) return "resources/stylesheets/html/page.simple.xsl";
		if (sRequestedOutput.equalsIgnoreCase("excel")) return "resources/stylesheets/export/page.xls.filter.xsl";
		if (sRequestedOutput.equalsIgnoreCase("csv")) return "resources/stylesheets/export/page.csv.filter.xsl";
		if (sRequestedOutput.equalsIgnoreCase("graph")) return "resources/stylesheets/export/page.img.filter.xsl";
		if (sRequestedOutput.equalsIgnoreCase("report")) return "resources/stylesheets/export/page.report.filter.xsl";
		if (sRequestedOutput.equalsIgnoreCase("map")) return "resources/stylesheets/export/page.map.filter.xsl";
		throw new RuntimeException("Invalid transformer: "+sRequestedOutput);
	}

}
