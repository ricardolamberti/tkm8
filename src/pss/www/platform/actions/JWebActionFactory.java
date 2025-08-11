/*
 * Created on 24-jun-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.platform.actions;

import java.io.Serializable;

import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.net.URLCodec;

import pss.common.mail.GuiModuloMailing;
import pss.common.security.BizUsuario;
import pss.common.security.GuiModuloSeguridad;
import pss.core.tools.JDateTools;
import pss.core.tools.JExcepcion;
import pss.core.tools.JExceptionRunAction;
import pss.core.tools.JTools;
import pss.core.tools.PssLogger;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JStringTokenizer;
import pss.core.win.JBaseWin;
import pss.core.win.actions.BizAction;
import pss.www.GuiModuloWebUserProfile;
import pss.www.platform.actions.requestBundle.JWebActionData;
import pss.www.platform.actions.requestBundle.JWebActionDataField;
import pss.www.platform.applications.JHistory;
import pss.www.platform.applications.JWebApplicationSession;
import pss.www.ui.IWebWinGeneric;
import pss.www.ui.JWebActionOwnerProvider;

public abstract class JWebActionFactory {

	public static final String ACTION_DATA_PREFIX="dg_";
	private static final String ACTION_DATA_FIELD_PREFIX="dgf_";

	private static final String SRC_ACTION_DATA_PREFIX="src_dg_";
	private static final String SRC_ACTION_DATA_FIELD_PREFIX="src_dgf_";

	private static final String ENCRYPTED_DATA_SUFFIX="_encrypted";

	static InheritableThreadLocal<JWebRequest> CURRENT_REQUEST=new InheritableThreadLocal<JWebRequest>();

	private static URLCodec oURLCodec=new URLCodec();

	public static JWebRequest getCurrentRequest() {
		return CURRENT_REQUEST.get();
	}
	
	public static boolean isMobile() {
		try {
			if (getCurrentRequest()==null) return false;
			return getCurrentRequest().isMobile();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return false;
		}
	}
	public static String registerObject(JBaseWin zBaseWin) throws Exception {
		return JWebActionFactory.getCurrentRequest().registerObject(zBaseWin);
	}
	public static String registerObject(JBaseWin zBaseWin,String id) throws Exception {
		return JWebActionFactory.getCurrentRequest().registerObject(id,zBaseWin);
	}
	public static String registerObject(Serializable serializable) throws Exception {
		return JWebActionFactory.getCurrentRequest().registerObjectObj(serializable);
	}	//
	//
	// WEB ACTION PROVIDING METHODS
	//
	//
	public static boolean isBackOnPrint() throws Exception {
		if ( JWebActionFactory.getCurrentRequest()==null) return false;
		if ( JWebActionFactory.getCurrentRequest().getArgument("back_on_print")==null) return false;
		return JWebActionFactory.getCurrentRequest().getArgument("back_on_print").equals("true");
	}
	public static JWebAction createDoLogout() throws Exception {
//		JWebAction oAction=JWebActionFactory.create(BizUsuario.getCurrentLoginPage(), false);
		JWebAction oAction=JWebActionFactory.create("closed", false);
		oAction.setDescription("Cerrar sesión");
		return oAction; 
	}
	public static JWebAction createDoLogoutClosed() throws Exception {
		JWebAction oAction=JWebActionFactory.create("closed_subsession", false);
		oAction.setDescription("Cerrar sesión");
		return oAction; 
	}
	
	public static JWebAction createDoPasswordChangeLex() {
		JWebAction oAction=JWebActionFactory.create("do-passwordchangelex", true);
		oAction.setDescription("Cambiar contraseña e ingresar a la aplicación");
		return oAction;
	}
	public static JWebAction createRegistrar() {
		JWebAction oAction=JWebActionFactory.create("do-registrar", true);
		oAction.setDescription("Ingresar a la aplicación");
		return oAction;
	}

	public static JWebAction createNewSession(JWebApplicationSession secc) throws Exception {
		JWebServerAction oAction=JWebActionFactory.create("subsession", false);
		oAction.setDescription("Nueva sesion"); 
		oAction.setOpenInNewSession(true);
		JWebActionData oData=oAction.addData("newSession");
	//	oData.add("session", secc.getSessionId());
		return oAction;
	}
	
	public static JWebAction createDoForget() {
		JWebAction oAction=JWebActionFactory.create("do-forget", true);
		oAction.setDescription("Olvidar contraseña");
		return oAction;
	}
	public static JWebAction createNewSession(JWebApplicationSession secc,String action) throws Exception {
		JWebServerAction oAction=JWebActionFactory.create("subsession", false);
		oAction.setDescription("Nueva sesion"); 
		oAction.setOpenInNewSession(true);
		oAction.setMaximize(true);
		JWebActionData oData=oAction.addData("newSession");
	//	oData.add("session", secc.getSessionId());
		oData.add("urlredirect", action);
		return oAction;
	}
	
	public static JWebAction createSelectColumns() throws Exception {

//		if (BizUsuario.IsAdminCompanyUser()) 
			return new JWebExcludeColumnsAction();
//		else 
//			return null;
	}

	
	public static JWebAction createDoAutoLogin() {
		JWebAction oAction=JWebActionFactory.create("autologin", true);
		oAction.setDescription("Ingresar anónimo a la aplicación");
		return oAction;
	}

	public static JWebAction createDoLoginByIni() {
		JWebServerAction oAction=JWebActionFactory.create("do-login-ini", true);
		oAction.setUploadata(true);
		oAction.setDescription("Ingresar a la aplicación");
		return oAction;
	}

	public static JWebAction createDoLogin() {
		JWebServerAction oAction=JWebActionFactory.create("do-login", true);
		oAction.setUploadata(true);
		oAction.setDescription("Ingresar a la aplicación");
		return oAction;
	}

	public static JWebAction createDoLoginEmbbeded() {
		JWebServerAction oAction=JWebActionFactory.create("do-login", true);
		oAction.setOpenInNewWindow(true);
		oAction.setUploadata(true);
		oAction.setDescription("Ingresar a la aplicación");
		return oAction;
	}
	
	public static JWebAction createDoRegister() {
		JWebServerAction oAction=JWebActionFactory.create("do-register", true);
		oAction.setUploadata(true);
		oAction.setDescription("Registrarse a la aplicación");
		return oAction;
	}

	public static JWebAction createRegister() {
		JWebServerAction oAction=JWebActionFactory.create("register", false);
		oAction.setUploadata(true);
		oAction.setDescription("Registrarse a la aplicación");
		return oAction;
	}

	public static JWebAction createRecovery() {
		JWebServerAction oAction=JWebActionFactory.create("recovery", false);
		oAction.setUploadata(true);
		oAction.setDescription("Recuperar clave");
		return oAction;
	}
	public static JWebAction createDoRecovery() {
		JWebServerAction oAction=JWebActionFactory.create("do-recovery", true);
		oAction.setUploadata(true);
		oAction.setDescription("Recuperar clave");
		return oAction;
	}
	public static JWebAction createDoPasswordChange() {
		JWebServerAction oAction=JWebActionFactory.create("do-passwordchange", true);
		oAction.setUploadata(true);
		oAction.setDescription("Cambiar contraseña e ingresar a la aplicación");
		return oAction;
	}

	public static JWebAction createMail() throws Exception {
		JWebAction oAction=JWebActionFactory.createViewAreaAntTitleAction(GuiModuloMailing.class.getName()+"_10");
		oAction.setDescription("Correo");
		return oAction;
	}
	public static JWebAction createHideMail() throws Exception {
		JWebAction oAction=JWebActionFactory.createViewAreaAntTitleAction(GuiModuloMailing.class.getName()+"_11");
		oAction.setDescription("Ocultar Correo");
		return oAction;
	}
	
	public static JWebAction createPreferences() throws Exception {
		JWebAction oAction=JWebActionFactory.createViewAreaAntTitleAction(GuiModuloWebUserProfile.class.getName()+"_10");
		oAction.setDescription("Preferencias Usuario");
		return oAction;
	}

	public static JWebAction createInfo() throws Exception {
		JWebAction oAction=JWebActionFactory.createViewAreaAntTitleAction(GuiModuloWebUserProfile.class.getName()+"_30");
		oAction.setDescription("Info sistema");
		return oAction;
	}

	public static JWebAction createPasswordChange() throws Exception {
		JWebAction oAction=JWebActionFactory.createViewAreaAntTitleAction(GuiModuloSeguridad.class.getName()+"_2");
		oAction.setDescription("Cambio Clave");
		return oAction;
	}

	public static JWebAction createSecurity() throws Exception {
		if (BizUsuario.IsAdminCompanyUser()) 
			return new JWebSecurityAction();
		else 
			return null;
	}
		
	private static JWebServerAction createWinListNavigate(IWebWinGeneric zList, BizAction action, String column, String dirOrden,  String all,String zWinId, int size, int zFrom, int zPageSize, boolean showFilter, String zDescription, String paginado,long paginaActual, boolean isSearch) throws Exception {
		if (zList.isInForm()) {
			JWebServerAction webAction=new JWebPartialRefreshFormAction();
			JWebActionData nav = webAction.getNavigationData();
			nav.add("embedded", ""+true);
			nav.add("is_preview", ""+true);
			JWebActionData oData = webAction.addData("presentation");
			oData.add("mode", zList.getPresentation());

			oData=webAction.getNavigationData();
			oData.add("name", zList.getWins().GetTitle());
			oData.add("from", String.valueOf(zFrom));
			oData.add("paginado", paginado);
			oData.add("pagina_actual",  String.valueOf(paginaActual));

			oData.add("orden", column);
			oData.add("all", all);	
		  oData.add("dir_orden", dirOrden);
			oData.add("size", String.valueOf(size));
			oData.add("page_size", String.valueOf(zPageSize));
			oData.add("hide_filter_bar", ""+showFilter);
			oData.add("hide_act_bar", ""+false);
			oData.add("with_preview", ""+zList.findPreviewFlag());
			oData.add("is_preview", ""+zList.isPreview());
			oData.add("embedded", ""+zList.isEmbedded());
			oData.add("toolbar", ""+zList.getToolbar());
			if (isSearch) oData.add("button_search", "true");
			return webAction.prepareWebAction(action, zList.getParentProvider(), zWinId);

		}
		
		JWebServerAction webAction=JWebActionFactory.createWinListRefreshAction(action, zList, zWinId);
		webAction.setAjaxContainer(zList.getCompletePanelName());
		webAction.setDescription(zDescription+" {"+String.valueOf(zPageSize)+"}");
//		JWebActionData oData=webAction.addData("win_list_nav_"+zList.getProviderName());
		JWebActionData oData = webAction.addData("presentation");
		oData.add("mode", zList.getPresentation());

		oData=webAction.getNavigationData();
		oData.add("name", zList.getWins().GetTitle());
		oData.add("from", String.valueOf(zFrom));
		oData.add("paginado", paginado);
		oData.add("pagina_actual",  String.valueOf(paginaActual));

		oData.add("orden", column);
		oData.add("all", all);	
	  oData.add("dir_orden", dirOrden);
		oData.add("size", String.valueOf(size));
		oData.add("page_size", String.valueOf(zPageSize));
		oData.add("hide_filter_bar", ""+showFilter);
		oData.add("hide_act_bar", ""+false);
		oData.add("with_preview", ""+zList.findPreviewFlag());
		oData.add("is_preview", ""+zList.isPreview());
		oData.add("embedded", ""+zList.isEmbedded());
		oData.add("toolbar", ""+zList.getToolbar());
		if (isSearch) oData.add("button_search", "true");
		return webAction;
	}
	
	public static JWebAction createWinListWithPreview(IWebWinGeneric zList, BizAction action, String zWinId,String orden,String dirOrden, String all,int size, int zFrom, int zPageSize, String paginado,long paginaActual) throws Exception {
		JWebServerAction webAction=new JWebWinListExpandAction();
		webAction.prepareWebAction(action, zList, zWinId);
//		webAction.setAjaxContainer(zList.getPreviewCanvasPanelName());
		webAction.setAjaxContainer(zList.getCompletePanelName());
		webAction.setDescription(zList.isPreviewFlagSi()?"Expandir":"Ver Preview");
		JWebActionData oData = webAction.addData("presentation");
		oData.add("mode", zList.getPresentation());
	  oData=webAction.addData("win_list_nav");
		oData.add("name", zList.getWins().GetTitle());
		oData.add("from", String.valueOf(zFrom));
		oData.add("paginado", paginado);
		oData.add("pagina_actual",  String.valueOf(paginaActual));
		oData.add("orden", orden);
		oData.add("all", all);
	  oData.add("dir_orden", dirOrden);
		oData.add("size", String.valueOf(size));
		oData.add("page_size", String.valueOf(zPageSize));
		oData.add("is_preview", ""+zList.isPreview());
		oData.add("hide_filter_bar", ""+zList.isShowFilterBar());
		oData.add("hide_act_bar", ""+false);
		oData.add("with_preview", ""+zList.invertPreviewFlag());
		oData.add("embedded", ""+zList.isEmbedded());
		oData.add("toolbar", ""+zList.getToolbar());
		return webAction;
	}
	
	

	public static JWebAction createWinListHideShowFilter(IWebWinGeneric zList, BizAction action, String zWinId,String orden,String dirOrden, String all,int size, int zFrom, int zPageSize, String paginado,int paginaActual) throws Exception {
		return createWinListNavigate(zList, action , orden, dirOrden, all, zWinId, size, zFrom, zPageSize, !zList.isShowFilterBar(), "Ver/Ocultar filtros", paginado, paginaActual, false);
	}

	public static JWebAction createWinListGoNext(IWebWinGeneric zList, BizAction action, String zWinId,String orden,String dirOrden, String all,int size, int zFrom, int zPageSize, String paginado,int paginaActual) throws Exception {
		return createWinListNavigate(zList, action , orden, dirOrden, all, zWinId, size, zFrom, zPageSize, zList.isShowFilterBar(),  "Siguientes", paginado, paginaActual, true);
	}

	public static JWebAction createWinListGoBack(IWebWinGeneric zList, BizAction action, String zWinId,String orden,String dirOrden,String all,int size, int zFrom, int zPageSize, String paginado,int paginaActual) throws Exception {
		return createWinListNavigate(zList, action , orden, dirOrden, all, zWinId, size, zFrom, zPageSize, zList.isShowFilterBar(), "Anteriores", paginado, paginaActual, true);
	}
	public static JWebAction createWinListOrden(IWebWinGeneric zList, BizAction action, String zWinId, String orden, String dirOrden,String all,int size,  int zFrom, int zPageSize, String paginado,int paginaActual) throws Exception {
		return createWinListNavigate(zList, action , orden,dirOrden, all, zWinId, size, zFrom, zPageSize, zList.isShowFilterBar(), "Orden", paginado, paginaActual, true);
	}
	public static JWebAction createWinListSelectAll(IWebWinGeneric zList, BizAction action, String zWinId, String orden, String dirOrden,String all,int size,  int zFrom, int zPageSize, String paginado,int paginaActual) throws Exception {
		return createWinListNavigate(zList, action , orden,dirOrden, all,zWinId, size, zFrom, zPageSize, zList.isShowFilterBar(), "Sel.Todo", paginado, paginaActual, true);
	}

	public static JWebAction createWinListExportAllToMap(BizAction sourceAction, JWebActionOwnerProvider provider, String idObject) throws Exception {
		BizAction a = new BizAction();
		a.setDescrip("Exportar a Mapa");
		a.SetIdAction(sourceAction.getIdAction());
		a.setNuevaVentana(true);
		a.setNuevaVentana(true);
		JWebServerAction webaction=new JWebPssAction();
		JWebAction wa= webaction.prepareWebAction(a, provider, idObject);
		JWebActionData oData=wa.addData("export");
		oData.add("serializer", "map");
		oData.add("object", "win_list_"+provider.getProviderName());
		oData.add("range", "all");
		oData.add("preserve", "Y");
		oData.add("history", "N");
	  return wa;
	}

	public static JWebAction createWinListExportAllToGraph(BizAction sourceAction, JWebActionOwnerProvider provider, String idObject) throws Exception {
		BizAction a = new BizAction();
		a.setDescrip("Exportar a Gráfico");
		a.SetIdAction(sourceAction.getIdAction());
		a.setNuevaVentana(true);
		JWebServerAction webaction=new JWebPssAction();
		JWebAction wa= webaction.prepareWebAction(a, provider, idObject);
		JWebActionData oData=wa.addData("export");
		oData.add("serializer", "graph");
		oData.add("object", "win_list_"+provider.getProviderName());
		oData.add("range", "all");
		oData.add("preserve", "Y");
		oData.add("history", "N");
	  return wa;
	}

	public static JWebAction createWinListExportAllToExcel(BizAction sourceAction, JWebActionOwnerProvider provider, String idObject,String presentation) throws Exception {
		BizAction a = new BizAction();
		a.setDescrip("Exportar a Excel®");
		a.SetIdAction(sourceAction.getIdAction());
		a.setNuevaVentana(true);
		JWebServerAction webaction=new JWebReportAction();
		JWebAction wa= webaction.prepareWebAction(a, provider, idObject);
		JWebActionData oData=wa.addData("presentation");
		oData.add("mode", presentation);
		
		oData=wa.addData("export");
		oData.add("serializer", "excel");
		oData.add("title", JTools.getValidFilename(provider.getTitle()+"_"+JDateTools.CurrentDate("ddMMyyyyHHmmss"))+".xls");
		oData.add("object", "win_list_"+provider.getProviderName());
		oData.add("range", "all");
		oData.add("preserve", "Y");
		oData.add("history", "N");
		return wa;
	}

	public static JWebAction createWinListExportAllToDownload(BizAction sourceAction, JWebActionOwnerProvider provider, String idObject,String presentation, String type) throws Exception {
		
		BizAction a = new BizAction();
		a.setDescrip("Exportar");
		a.SetIdAction(sourceAction.getIdAction());
		a.setPostFunction("waitingDialog.show();");
		JWebServerAction webaction=new JWebReportFormAction(false);
		JWebAction wa= webaction.prepareWebAction(a, provider, idObject);
		JWebActionData oData=wa.addData("presentation");
		oData.add("mode", presentation);
		
		oData=wa.addData("export");
		oData.add("serializer", "html");
		oData.add("type", type);
		oData.add("title", JTools.getValidFilename(provider.getTitle()+"_"+JDateTools.CurrentDate("ddMMyyyyHHmmss")));
		oData.add("object", "win_list_"+provider.getProviderName());
		oData.add("range", "all");
		oData.add("preserve", "Y");
		oData.add("history", "Y");
		return wa;
	}


	public static JWebAction createWinListExportAllToCSV(BizAction sourceAction, JWebActionOwnerProvider provider, String idObject,String presentation) throws Exception {
		BizAction a = new BizAction();
		a.setDescrip("Exportar CSV");
		a.SetIdAction(sourceAction.getIdAction());
		a.setNuevaVentana(true);
		JWebServerAction webaction=new JWebPssAction();
		JWebAction wa= webaction.prepareWebAction(a, provider, idObject);
		JWebActionData oData=wa.addData("presentation");
		oData.add("mode", presentation);
		
		oData=wa.addData("export");
		oData.add("serializer", "csv");
		oData.add("title", JTools.getValidFilename(provider.getTitle()+"_"+JDateTools.CurrentDate("ddMMyyyyHHmmss"))+".csv");
		
		oData.add("object", "win_list_"+provider.getProviderName());
		oData.add("range", "all");
		oData.add("preserve", "Y");
		oData.add("history", "N");
		return wa;
	}

	public static JWebAction createWinListExportAllToReport(BizAction sourceAction, JWebActionOwnerProvider provider, String idObject,String presentation) throws Exception {
		BizAction a = new BizAction();
		a.setDescrip("Exportar a Reporte");
		a.SetIdAction(sourceAction.getIdAction());
		a.setNuevaVentana(true);
		JWebServerAction webaction=new JWebPssAction();//JWebReportAction();
		JWebAction wa= webaction.prepareWebAction(a, provider, idObject);

		JWebActionData oData=wa.addData("presentation");
		oData.add("mode", presentation);
		
		oData=wa.addData("export");
		oData.add("serializer", "report");
		oData.add("object", "win_list_"+provider.getProviderName());
		oData.add("range", "all");
		oData.add("preserve", "Y");
		oData.add("history", "N");
		return wa;
	}
	
	public static JWebAction createWinListExportAllToReport(BizAction sourceAction, JWebActionOwnerProvider provider, String idObject) throws Exception {
		BizAction a = new BizAction();
		a.setDescrip("Exportar a Reporte");
		a.SetIdAction(sourceAction.getIdAction());
		a.setNuevaVentana(true);
		JWebReportAction webaction=new JWebReportAction();
		JWebAction wa= webaction.prepareWebAction(a, provider, idObject);
	

		return wa;
	}
	public static boolean isBigData(JWebRequest zRequest) throws Exception {
		String oData=zRequest.getArgument("draw");
		if (oData==null||oData.equals("")) {
			return false;
		}
		return true;
	}
	public static boolean isJSON(JWebRequest zRequest) throws Exception {
		String oData=zRequest.getArgument("draw");
		if (oData==null||oData.equals("")) {
			return false;
		}
		return true;
	}
	public static boolean isWinListFullExportRequest(JWebRequest zRequest, IWebWinGeneric zList) throws Exception {
		JWebActionData oData=zRequest.getData("export");
		if (oData==null||oData.isNull()) {
			return false;
		}
		String sObject=oData.get("object");
		if (sObject==null||(!sObject.equals("win_list_"+zList.getName())&&!sObject.equals("win_list_x"))) {
			return false;
		}
		String sRange=oData.get("range");
		if (sRange!=null&&sRange.equalsIgnoreCase("all")) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean isPreserveObjectRegistered(JWebRequest zRequest) throws Exception {
		if (!zRequest.isAjax())
			return true; // bug con el applet de firma, me generaba una llamada extra al servlety me borraba los objetos registrados. Como no pude encontrar donde hace la llamada, agregue esta linea para que no me borre los objetos y destruya el form
		JWebActionData oData=zRequest.getData("export");
		if (oData==null||oData.isNull()) return false;
		String sPreserve=oData.get("preserve");
		if (sPreserve!=null&&sPreserve.equalsIgnoreCase("Y")) return true;
		return false;
	}
	public static boolean isHistoryObjectRegistered(JWebRequest zRequest) throws Exception {
		JWebActionData oData=zRequest.getData("export");
		if (oData==null||oData.isNull()) return true;
		String sHistory=oData.get("history");
		if (sHistory!=null&&sHistory.equalsIgnoreCase("Y")) return true;
		return false;
	}
				
	public static boolean isOtherObjectExportRequest(JWebRequest zRequest, IWebWinGeneric zList) throws Exception {
		JWebActionData oData=zRequest.getData("export");
		if (oData==null||oData.isNull()) {
			return false;
		}
		
		//si ya estaba, era porque estaba paginado, si es all seguro quiero mas de lo que hay
		String sRange=oData.get("range");
		if (sRange!=null&&sRange.equalsIgnoreCase("all")) 
			return false;
			
		String sObject=oData.get("object");
		if (sObject!=null&&sObject.trim().length()>0) {
			if (!sObject.equalsIgnoreCase("win_list_"+zList.getName())) {
				return true;
			}
		}
		return false;
	}

	public static boolean isWinListNavigationRequest(JWebRequest zRequest, IWebWinGeneric zList) throws Exception {
		// JWebActionData oSrcReq = zRequest.getData("src_request");
		// if (oSrcReq.isNull()) {
		// return false;
		// }
		// String sSrcURI = oSrcReq.get("uri");
		// if (sSrcURI==null || !sSrcURI.endsWith("do-refreshwinlist")) {
		// return false;
		// }
		if (zRequest.getData("win_list_nav_"+zList.getName()).isNull()) {
			return false;
		}
		return true;
	}

	public static String getSerializerFor(JWebRequest zRequest) throws Exception {
		JWebActionData oData=zRequest.getData("export");
		String sRequestedOutput=null;
		if (oData!=null&&!oData.isNull()) {
			sRequestedOutput=oData.get("serializer");
		}
		if (sRequestedOutput==null||sRequestedOutput.trim().length()<1) {
			if (zRequest.isMobile()||zRequest.getURI().indexOf("mobile")!=-1)
				sRequestedOutput="mobile";
			else
				sRequestedOutput="html";
		}
		return sRequestedOutput;
	}
	
	public static String getTitleFor(JWebRequest zRequest) throws Exception {
		JWebActionData oData=zRequest.getData("export");
		String sRequestedOutput=null;
		if (oData!=null&&!oData.isNull()) {
			sRequestedOutput=oData.get("title");
		}
		if (sRequestedOutput==null||sRequestedOutput.trim().length()<1) {
			sRequestedOutput=null;
		}
		return sRequestedOutput;
	}

	public static JWebServerAction createRefresh() throws Exception {
		return new JWebRefreshAction();
	}

	public static JWebAction createGoBack() throws Exception {
		return new JWebBackAction();
	}

	public static JWebAction createGoBackToQuery() throws Exception {
		return new JWebBackToQueryAction();

	}

	
	public static JWebAction createHelp() throws Exception {
		JWebAction oAction=JWebActionFactory.createViewAreaAntTitleAction("pss.common.help.GuiModuloHelp_10");
		oAction.setDescription("Ayuda");
		return oAction;
	}

	public static JWebAction createActiveHelp() throws Exception {
		JWebAction oAction=JWebActionFactory.createViewAreaAntTitleAction("pss.common.help.GuiModuloHelp_16");
		oAction.setDescription("Ayuda");
		return oAction;
	}

	public static JWebAction createViewHelpVideos() throws Exception {
		JWebAction oAction=JWebActionFactory.createViewAreaAntTitleAction("pss.common.help.GuiModuloHelp_30");
		oAction.setDescription("Ayuda");
		return oAction;
	}

	public static JWebAction createGoBackSubmitting() throws Exception {
		JWebAction oAction=JWebActionFactory.create("closed", true);
		oAction.setDescription("Atrás");
		return oAction;
	}

	public static JWebAction createGoHome(JWebApplicationSession zSession) throws Exception {
		// String sActionId =
		// zSession.getUserProfile().getHomePageAction().GetIdAccion();
		// String sActionId = zSession.getUserProfile().getHomePageAction();
		// int iSepIndex = sActionId.indexOf('_');
		JWebAction oAction=JWebActionFactory.createGoHome(zSession.getHomePageAction());
		oAction.setDescription("Página principal");
		return oAction;
	}

	public static JWebAction createRedirect(String zURI, boolean zIsSubmit, JWebRequest zSourceRequest) throws Exception {
		JWebServerAction oAction=create(zURI, zIsSubmit);
		fillSourceActionDataFor(zSourceRequest, oAction);
		return oAction;
	}

	public static JWebAction createRedirectByAction(String zIdAction, boolean zIsSubmit, JWebRequest zSourceRequest) throws Exception {
		JWebServerAction oAction=(JWebServerAction) createPssAction(zIdAction, zIsSubmit);
		fillSourceActionDataFor(zSourceRequest, oAction);
		return oAction;
	}

	public static JWebServerAction create(String zURI, boolean zIsSubmit) {
		return new JWebServerAction(zURI, zIsSubmit);
	}

	public static JWebAction createPssAction(String zIdAction, boolean zIsSubmit) throws Exception {
		if (!canPerform(zIdAction)) {
			return new JWebDisabledAction();
		} else {
//			JWebServerAction oServerAction=create("do-pssaction", zIsSubmit);//RJL
			JWebServerAction oServerAction=create("do-respaction", zIsSubmit);
			JWebActionData oData=addDataToAction(oServerAction, "action");
			oData.add("act", zIdAction);
			oData.setReadOnly(true);
			oServerAction.setDescription(getPssAction(zIdAction).GetDescr());
			oServerAction.setKey(getPssAction(zIdAction).GetKeyAction());
			return oServerAction;
		}
	}

//	public static JWebAction createViewAreaAction(BizAction zAction) throws Exception {
//		JWebServerAction oServerAction=new JWebViewAreaAction(false);
//		JWebActionData oData=addDataToAction(oServerAction, "act_owner");
//		oData.add("cls", zAction.getObjOwner().getClass().getName());
//		oData=addDataToAction(oServerAction, "action");
//		oData.add("act", zAction.getIdAction());
//		oData.setReadOnly(true);
//		return oServerAction;
////	}
//	public static JWebAction createMinimize(JWebSplitPane split,int estado) throws Exception {
//		JWebServerAction oServerAction=new JWebRefreshAction();
//		JWebActionData oData=addDataToAction(oServerAction, split.getName());
//		oData.add("estado", ""+estado);
//		return oServerAction;
//	}

	
	public static JWebAction createViewAreaAction(String zActionId) throws Exception {
		int iSepIndex=zActionId.indexOf('_');
		String sPssWinClass=zActionId.substring(0, iSepIndex);
		JWebServerAction oServerAction=new JWebViewAreaAction(false);
		JWebActionData oData=addDataToAction(oServerAction, "act_owner");
		oData.add("cls", sPssWinClass);
		oData=addDataToAction(oServerAction, "action");
		oData.add("act", zActionId);
		oData.setReadOnly(true);
		return oServerAction;
	}

	public static JWebAction createViewAreaAntTitleAction(String zActionId) throws Exception {
		int iSepIndex=zActionId.indexOf('_');
		String sPssWinClass=zActionId.substring(0, iSepIndex);
		JWebServerAction oServerAction=new JWebViewAllPanelsAction(false);
		JWebActionData oData=addDataToAction(oServerAction, "act_owner");
		oData.add("cls", sPssWinClass);
		oData=addDataToAction(oServerAction, "action");
		oData.add("act", zActionId);
		oData.setReadOnly(true);
		return oServerAction;
	}

	public static JWebAction createGoHome(String zActionId) throws Exception {
		int iSepIndex=zActionId.indexOf('_');
		if (iSepIndex==-1) {
		 	BizAction bAction = JWebActionFactory.getPssAction(zActionId);
		 	if (bAction!=null) {
				String sPssWinClass=bAction.GetOwner();
				JWebServerAction oServerAction=new JWebViewAllPanelsAction(false);
				JWebActionData oData=addDataToAction(oServerAction, "act_owner");
				oData.add("cls", sPssWinClass);
				oData=addDataToAction(oServerAction, "action");
				oData.add("act", bAction.getIdAction());
				oData.setReadOnly(true);
				return oServerAction;
		 	}
		}
		String sPssWinClass=zActionId.substring(0, iSepIndex);
		// String sActionNumber = zActionId.substring(iSepIndex+1);
		JWebServerAction oServerAction=new JWebViewAllPanelsAction(false);
		JWebActionData oData=addDataToAction(oServerAction, "act_owner");
		oData.add("cls", sPssWinClass);
		oData=addDataToAction(oServerAction, "action");
		oData.add("act", zActionId);
		oData.setReadOnly(true);
		return oServerAction;
	}
	
	public static JWebAction createHistoryAction(JHistory hist) throws Exception {
		JWebServerAction oServerAction=new JWebGotoHistoryAction();
		oServerAction.setDescription(hist.getFirstProvider().getAction().getResult().GetTitle());
		JWebActionData oData=addDataToAction(oServerAction, "act_owner");
		oData=addDataToAction(oServerAction, "action");
		oData.add("hist", hist.getRef());
		oData.setReadOnly(true);
		
		return oServerAction;
	}
	
	public static JWebServerAction createRedirectExceptionAction( JWebRequest oRequest,JExceptionRunAction ex) throws Exception {
		BizAction action = ex.getBizAction();
		action.setModal(true);
		return fillRedirectionData(oRequest, action, ex.getWin());
		}
	
	public static JWebServerAction createDropAction( JWebActionOwnerProvider zObjectProvider, String zone, String zWinId) throws Exception {
		JWebServerAction s=new JWebDropAction(false);
		return s.prepareWebAction(zone, zObjectProvider, zWinId);
	}
	public static JWebServerAction createViewAreaAndTitleAction(BizAction zAction, JWebActionOwnerProvider zObjectProvider, boolean bSubmit, String zWinId) throws Exception {
		return createViewAreaAndTitleAction(zAction, zObjectProvider, bSubmit, zWinId, null, false);
	}
	public static JWebServerAction createViewAreaAndTitleAction(BizAction zAction, JWebActionOwnerProvider zObjectProvider, boolean bSubmit, String zWinId, String row) throws Exception {
		return createViewAreaAndTitleAction(zAction, zObjectProvider, bSubmit, zWinId, row, false);
	}

	public static JWebServerAction createViewAreaAndTitleAction(BizAction zAction, JWebActionOwnerProvider zObjectProvider, boolean bSubmit, String zWinId, String row, boolean forceModal) throws Exception {
		return createViewAreaAndTitleAction(zAction, zObjectProvider, bSubmit, zWinId, row, forceModal, null);
	}
	public static JWebServerAction createViewAreaAndTitleAction(BizAction zAction, JWebActionOwnerProvider zObjectProvider, boolean bSubmit, String zWinId, String row, boolean forceModal, String objcontext) throws Exception {
		if (!zAction.isBackNoModal() && (zAction.isModal() || forceModal)) {
			zAction.setModal(true);
//			return createModalViewAreaAction(zAction,zObjectProvider,bSubmit,zWinId);
		}
		JWebServerAction s=new JWebViewAllPanelsAction(bSubmit);
		return s.prepareWebAction(zAction, zObjectProvider, zWinId, row,objcontext);
	}
	public static JWebServerAction createViewOptionsAction(BizAction zAction, JWebActionOwnerProvider zObjectProvider, boolean bSubmit, String zWinId, boolean forceModal) throws Exception {
		JWebServerAction s = new JWebViewAllPanelsAction(bSubmit);
		if (isBackOnPrint()) zAction.setBackOnPrint(true);
		if (zAction.isModal() || forceModal) {
			zAction.setModal(true);
//			s = createModalViewAreaAction(zAction, zObjectProvider, bSubmit, zWinId);
		}
//			else {
			s.prepareWebAction(zAction, zObjectProvider, zWinId);
//		}
		JWebActionData oData = s.addData("export");
		oData.add("preserve", "Y");
		oData.add("history", "Y");
	
		return s;
	}

	public static JWebServerAction createViewAreaAction(BizAction zAction, JWebActionOwnerProvider zObjectProvider, boolean bSubmit, String zWinId) throws Exception {
		JWebServerAction s=new JWebViewAreaAction(bSubmit);
		return s.prepareWebAction(zAction, zObjectProvider, zWinId);
	}

	public static JWebServerAction createModalViewAreaAction(BizAction zAction, JWebActionOwnerProvider zObjectProvider, boolean bSubmit, String zWinId) throws Exception {
		JWebServerAction s=new JWebModalViewAreaAction(bSubmit);
		return s.prepareWebAction(zAction, zObjectProvider, zWinId);
	}

	public static JWebServerAction createListExpandAreaAction(BizAction zAction, JWebActionOwnerProvider zObjectProvider, String zWinId,String ajaxContainer,String row) throws Exception {
		JWebServerAction s=new JWebListExpandAreaResponsiveAction(ajaxContainer);
		return s.prepareWebAction(zAction, zObjectProvider, zWinId,row);
	}

	
	public static JWebServerAction createPreviewAreaAction(BizAction zAction, JWebActionOwnerProvider zObjectProvider, String zWinId) throws Exception {
		JWebServerAction s=new JWebPreviewAreaResponsiveAction();
		zAction.setModal(false);
		return s.prepareWebAction(zAction, zObjectProvider, zWinId);
	}

	public static JWebServerAction createPreviewShowAreaAreaAction(BizAction zAction, JWebActionOwnerProvider zObjectProvider, String zWinId) throws Exception {
		String actionId=zAction.getIdAction();
		JWebServerAction s=new JWebPreviewAreaResponsiveAction();
		int iSepIndex=actionId.indexOf('_');
		String sPssWinClass=actionId.substring(0, iSepIndex);
		JWebActionData oData=addDataToAction(s, "act_owner");
		oData.add("cls", sPssWinClass);
		oData=addDataToAction(s, "action");
		oData.add("act", actionId);
		oData.setReadOnly(true);
		s.setResolveString(asURLString(oData));
		return s.prepareWebAction(zAction, zObjectProvider, zWinId);
	}
	public static JWebServerAction createFormLovRefreshAction(BizAction zAction, JWebActionOwnerProvider zObjectProvider, String zWinId) throws Exception {
//		JWebServerAction oServerAction=new JWebFormLovRefreshAction(true);
//		return JWebActionFactory.create(zAction, zObjectProvider, oServerAction, zWinId);
		JWebServerAction s=new JWebFormLovRefreshAction();
		return s.prepareWebAction(zAction, zObjectProvider, zWinId);
	}
//	
//	public static JWebAction createTabRefreshAction(BizAction zAction, JWebActionOwnerProvider zObjectProvider, String zWinId) throws Exception {
//		JWebServerAction oServerAction=new JWebTabAction();
//		return JWebActionFactory.create(zAction, zObjectProvider, oServerAction, zWinId);
//	} 

	
	public static JWebServerAction createWinListRefreshPreviewAction(BizAction zAction, JWebActionOwnerProvider zObjectProvider, String zWinId) throws Exception {
		JWebServerAction s=new JWebWinListExpandAction();
		return s.prepareWebAction(zAction, zObjectProvider, zWinId);
	} 
	public static JWebServerAction createWinListRefreshAction(BizAction zAction, JWebActionOwnerProvider zObjectProvider, String zWinId) throws Exception {
		JWebServerAction s=new JWebWinListRefreshAction();
	  zAction.setModal(false);
		return s.prepareWebAction(zAction, zObjectProvider, zWinId);
	} 
//	public static JWebAction createPartialRefreshFormAction(BizAction zAction, JWebActionOwnerProvider zObjectProvider, String zWinId,String provider,String ajaxContainer,String param,String value) throws Exception {
//		JWebServerAction s= JWebActionFactory.createPreviewAreaAction(zAction, zObjectProvider, null);
//		JWebActionData nav = s.getNavigationData();
//		nav.add("embedded", ""+true);
//		nav.add("is_preview", ""+true);
//		JWebActionData oData=addDataToAction(s, "new_status");
//		oData.add("value", value);
//		oData.add("param", param);
//		oData.add("provider", provider);
//		oData.setReadOnly(true);
//		return s.prepareWebAction(zAction, zObjectProvider, zWinId);
//	} 

	public static JWebServerAction createPartialRefreshFormAction(BizAction zAction, JWebActionOwnerProvider zObjectProvider, String zWinId,String provider,String param,String value) throws Exception {
		JWebServerAction s=new JWebPartialRefreshFormAction();
		JWebActionData oData=addDataToAction(s, "new_status");
		oData.add("value", value);
		oData.add("param", param);
		oData.add("provider", provider);
		oData.setReadOnly(true);
		return s.prepareWebAction(zAction, zObjectProvider, zWinId);
	} 
	
	public static JWebServerAction createPartialRefreshPreviewFormAction(BizAction zAction, JWebActionOwnerProvider zObjectProvider, String zWinId,String container,String provider,String param,String value) throws Exception {
		JWebServerAction s=new JWebPartialRefreshPreviewFormAction();
		JWebActionData oData=addDataToAction(s, "new_status");
		oData.add("value", value);
		oData.add("param", param);
		oData.add("provider", provider);
		oData.setReadOnly(true);
		s.setAjaxContainer(container);
		return s.prepareWebAction(zAction, zObjectProvider, zWinId);
	} 

	public static JWebAction createBuildGraphAction(BizAction zAction, JWebActionOwnerProvider zObjectProvider,String container,String graph,String idGraph) throws Exception {
		JWebServerAction s=new JWebBuildGraphAction();
		s.setAjaxContainer(container);
//		s.setCancelable(false);
		JWebActionData oData=addDataToAction(s, "graficos");
		oData.add("graph", graph);
		oData.add("id_graph", idGraph);
	
		oData.setReadOnly(true);
		return s.prepareWebAction(zAction, zObjectProvider, null);
	} 

	//	public static JWebAction createFormLovRefreshAction(BizAction zAction, JWebActionOwnerProvider zObjectProvider, String zWinId) throws Exception {
//		JWebServerAction oServerAction=new JWebFormLovRefreshAction(true);
//		return JWebActionFactory.create(zAction, zObjectProvider, oServerAction, zWinId);
//	}
	

	
	public static JWebTabAction createTabAction(BizAction zAction, JWebActionOwnerProvider zObjectProvider, boolean isModoAlta) throws Exception {
		if (isModoAlta)
			return new JWebDisabledTabAction();
//		{
//			if (!canPerform(zAction.getIdAction())) {
//				return new JWebDisabledAction();
//			} else {
//				return new JWebDisabledTabAction();
//			}
//		} else {
			JWebTabAction s=new JWebTabAction();
			s.prepareWebAction(zAction, zObjectProvider, null);
			return s;
//		}
	} 

//	public static JWebAction createTabRefreshAction(BizAction zAction, JWebActionOwnerProvider zObjectProvider, boolean isModoAlta) throws Exception {
//		JWebServerAction oServerAction=new JWebTabAction();
//		oServerAction.setURI("do-tabRefresh");
//		return JWebActionFactory.create(zAction, zObjectProvider, oServerAction, null);
//	} 

	
	public static JWebAction createMaximizeAction(BizAction zAction, JWebActionOwnerProvider zObjectProvider, boolean isModoAlta,String title) throws Exception {
		if (isModoAlta) {
			if (!canPerform(zAction.getIdAction())) {
				return new JWebDisabledAction();
			} else {
				return new JWebDisabledTabAction();
			}
		} else {
			JWebServerAction oServerAction=new JWebViewAllPanelsAction(false);
			oServerAction.setDescription(title);
			oServerAction.setOwnerProvider(zObjectProvider);
			oServerAction.setIdAction(idActionToURL(zAction.getIdAction()));
			oServerAction.setCancelable(false);
			oServerAction.setUploadata(false);
			return oServerAction;
		}
	}
	
//	public static JWebAction createWinListRefresh(JWebActionOwnerProvider zObjectProvider) throws Exception {
//		JWebServerAction oServerAction=new JWebWinListRefreshAction();
//		oServerAction.setDescription("Refresh");
//		oServerAction.setOwnerProvider(zObjectProvider);
//		return oServerAction;
//	}




	public static JWebServerAction createWinSelect(JWebActionOwnerProvider zObjectProvider) throws Exception {
		JWebServerAction oAction=new JWebWinSelectAction();
		oAction.setOwnerProvider(zObjectProvider);
		return oAction;
	}

	public static JWebServerAction createWinGoBack() throws Exception {
		JWebServerAction oAction=new JWebBackAction();
		// oAction.setOwnerProvider(zObjectProvider);
		return oAction;
	}


	/*
	 * public static JWebAction create(Class zPssWinClass, int zActionNumber, boolean zIsSubmit) throws Exception { return create(zPssWinClass.getName(), zActionNumber, zIsSubmit); }
	 */

//	public static boolean canPerform(String zPssWinClass, int zActionNumber) throws Exception {
//		return BizUsuario.ifOperacionHabilitada(zPssWinClass+"_"+String.valueOf(zActionNumber));
//	}

	public static boolean canPerform(String zFullActionId) throws Exception {
		return BizUsuario.ifOperacionHabilitada(zFullActionId);
	}

	public static String getErrorMessageTitle(JWebRequest zRequest) {
		/*
		 * try { if (zRequest != null) { JWebAction oAction = JWebActions.getSourceActionForPageOf(zRequest, false); JWebActionData oData = oAction.getData("pss_act"); if (oData != null && !oData.isNull()) { String sClass = oData.get("cls"); String sId = oData.get("act"); BizAction oPssAction = JWebActions.getPssAction(sClass, Integer.parseInt(sId)); if (oPssAction!=null) { return oPssAction.GetDescr(); } } } } catch (Exception e) { }
		 */
		if (!showError(zRequest))
			return "NOSHOW";
		
		return null;
	}
	
	private static boolean showError(JWebRequest zRequest) {
		if (zRequest.getURI().indexOf("do-PreviewAreaAction")!=-1) return false;
		return true;
	}


	/**
	 * Creates and answers an action capable of producing the same request as the given one.<br>
	 * The action includes the URI, parameters and all the needed action data, to exactly reproduce the given request.
	 * 
	 * @param zRequest
	 *          the request the action will be created from
	 * @return the server action created
	 */
	public static JWebServerAction getSourceActionFor(JWebRequest zRequest) throws Exception {
		JWebServerAction oAction=new JWebServerAction();
		oAction.setURI(zRequest.getURI());
		oAction.setSubmit("true".equalsIgnoreCase(zRequest.getArgument("sbmt")));
		if (zRequest.getURI().indexOf("modaldo-exceptionaction")!=-1) oAction.setOpenInModal(true);
		fillSourceActionDataFor(zRequest, oAction);
		return oAction;
	}
	public static JWebServerAction getSourceActionForHistory(JWebRequest zRequest) throws Exception {
		BizAction action = zRequest.getSession().getHistoryManager().getLastSubmit().getActionSource();
		if (zRequest.getURI().indexOf("modaldo-exceptionaction")!=-1) action.setModal(true);
		return JWebActionFactory.createViewAreaAndTitleAction( action,(JWebActionOwnerProvider)null,false,(String)null);
	}
	/**
	 * Creates and answers an action capable of generating the page the given request came from.<br>
	 * The action includes the URI, parameters and all the needed action data, to exactly reproduce the given request's holder page.
	 * 
	 * @param zRequest
	 *          the request the action will be created from
	 * @return the server action created
	 */

	public static JWebServerAction getSourceActionForPageOf(JWebRequest zRequest, boolean zIncludeRequestData) throws Exception {
		JWebServerAction oAction=new JWebServerAction();
		oAction.setURI(zRequest.getArgument("src_uri"));
		oAction.setSubmit("true".equalsIgnoreCase(zRequest.getArgument("src_sbmt")));
		JIterator<String> oRawArgsIt=zRequest.getAllArgumentNames();
		String sArgName;
		while (oRawArgsIt.hasMoreElements()) {
			sArgName=oRawArgsIt.nextElement();
			if (sArgName.startsWith(SRC_ACTION_DATA_PREFIX)) {
				parseActionData(oAction.addData(sArgName.substring(SRC_ACTION_DATA_PREFIX.length())), zRequest.getArgument(sArgName));
			} else if (sArgName.startsWith(SRC_ACTION_DATA_FIELD_PREFIX)) {
				parseActionDataField(oAction, sArgName.substring(SRC_ACTION_DATA_FIELD_PREFIX.length()), zRequest.getArgument(sArgName));
			} else if (sArgName.startsWith("src_")&&!sArgName.equals("src_sbmt")&&!sArgName.equals("src_uri")) {
				throw new RuntimeException("Unrecognized page source request argument name: "+sArgName);
			}
		}
		// if it is needed to include the request data, set it into the action;
		// this is
		// done separatedly from the rest of the data to ovverride the original
		// data
		// with the new request data when it is asked to do so
		if (zIncludeRequestData) {
			fillSourceActionDataFor(zRequest, oAction);
		}
		//
		return oAction;
	}
	

	public static String idActionToURL(String actionId,String row) throws Exception {
		JWebActionData oData=new JWebActionData("");
		oData.add("act", actionId);
		if (row!=null) 
			oData.add("row", row);
		return fieldsAsURLString(oData, false);
	}

	public static String idActionToURL(String actionId) throws Exception {
		return idActionToURL(actionId,null);
	}


	public static String asURLString(JWebActionData zData) {
		StringBuffer sActionData=new StringBuffer(100);
		try {
			sActionData.append( oURLCodec.encode("dg_"+zData.getId())).append('=');
		} catch (EncoderException e) {
			sActionData.append( "dg_"+zData.getId()).append('=');
		}
		sActionData.append(fieldsAsURLString(zData, true));
		return sActionData.toString();
	}

	public static String fieldsAsURLString(JWebActionData zData, boolean encode) {
		StringBuffer sActionData=new StringBuffer(100);
		JIterator<JWebActionDataField> oArgIt=zData.getFieldIterator();
		JWebActionDataField oArg;
		while (oArgIt.hasMoreElements()) {
			oArg=oArgIt.nextElement();
			if (!oArg.isEncrypted()) { // encrypted data must be always asked
				// again to the user
				try {
					sActionData.append(encode ? oURLCodec.encode(oArg.getName()): oArg.getName());
					sActionData.append(encode ? oURLCodec.encode("___op"+String.valueOf(JWebActionDataField.getOperatorCode(oArg.getOperator()))):"___op"+String.valueOf(JWebActionDataField.getOperatorCode(oArg.getOperator())));
					sActionData.append(encode ? oURLCodec.encode("=") : "=");
					sActionData.append(encode ? oURLCodec.encode(oArg.getEncodedValue()) : oArg.getValue());
					sActionData.append(encode ? oURLCodec.encode(",") : ",");
				} catch (EncoderException e) {
					PssLogger.logDebug(e);
				}
			}
		}
		return sActionData.toString();
	}

	//
	//
	// PUBLIC UTILITY METHODS
	//
	//

	/**
	 * Finds the asked Pss action and returns it.
	 * 
	 * @return the requested action or <code>null</code> if the action does not exist or the current user has no permission to perform it
	 */
	// public static BizAction getPssAction(String zActionId) throws Exception {
	// int iSepIndex = zActionId.indexOf('_');
	// String sPssWinClass = zActionId.substring(0, iSepIndex);
	// return getPssAction(sPssWinClass, zActionId);
	// }
	public static BizAction getPssAction(String zActionId) throws Exception {
		if (!canPerform(zActionId)) {
			 JExcepcion.SendError("Operacion no Habilitada");
		}
		BizAction action=BizUsuario.getUsr().getModuloPss().findModuleAction(zActionId); //HGK, saque el barrido de carpetas q no tenia sentido
//		BizAction action=GuiModuloPss.getModuloPss().findActionByUniqueId(zActionId);
//		BizAction action=BizUsuario.getUsr().getModuloPss().findActionByUniqueId(zActionId);
		// RJL, no se porque es esto,  pero rompe algunas solapas swap
//		if (action!=null)
//			action.clearSubmit();
//		else
//			action = null;
		return action;
		// JBaseWin baseWin = (JBaseWin)Class.forName(zClass).newInstance();
		// baseWin.generateFullActionMap();
		// return baseWin.findActionByUniqueId(zActionId);
		// checkAllPssActionsLoaded();
		// return GuiModuloPss.getInstance().BuscarAccion(zClass + "_" +
		// String.valueOf(zActionNumber));
	}

	/**
	 * Parses the data dor the given group, which cames in the given String cotained into an URL.
	 */
	public static void parseActionData(JWebActionData zGroup, String zDataFieldString) {
		parseActionData(zGroup, zDataFieldString, false);
	}

	public static void parseActionData(JWebActionData zGroup, String zDataFieldString, boolean bIncludeEncryptedData) {
		if (zDataFieldString==null||zDataFieldString.length()<1) {
			return;
		}
		JStringTokenizer oTokens=JCollectionFactory.createStringTokenizer(zDataFieldString, ',');
		String sToken;
		int zAssignmentIndex, zOpPrefixIndex;
		String sNameAndOp, sName, sOp, sValue;
		while (oTokens.hasMoreTokens()) {
			sToken=oTokens.nextToken();
			zAssignmentIndex=sToken.indexOf('=');
			if (zAssignmentIndex>0) {
				sNameAndOp=sToken.substring(0, zAssignmentIndex).trim();
				sValue=sToken.substring(zAssignmentIndex+1).trim();
				zOpPrefixIndex=sNameAndOp.lastIndexOf("___op");	
				if (zOpPrefixIndex>0) {
					sName=sNameAndOp.substring(0, zOpPrefixIndex).trim();
					sOp=JWebActionDataField.getOperatorFromCode(Integer.parseInt(sNameAndOp.substring(zOpPrefixIndex+5)));
				} else {
					sName=sNameAndOp;
					sOp="=";
				}
				addDataField(zGroup, sName, sOp, sValue, bIncludeEncryptedData);
			}
		}
	}

	private static void addDataField(JWebActionData zData, String zFieldName, String zOperator, String zValue) {
		addDataField(zData, zFieldName, zOperator, zValue, false);
	}

	private static void addDataField(JWebActionData zData, String zFieldName, String zOperator, String zValue, boolean zIncludeEncryptedData) {
		if (zOperator==null) {
			zOperator="=";
		}
		if (zFieldName.endsWith(ENCRYPTED_DATA_SUFFIX)) {
			if (zIncludeEncryptedData) {
				zData.addEncrypted(zFieldName.substring(0, zFieldName.indexOf(ENCRYPTED_DATA_SUFFIX)), zOperator, zValue);
			}
		} else {
			zData.add(zFieldName, zOperator, zValue);
		}
	}

	public static JWebActionData parseActionDataFields(JWebRequest zRequest, String zDataId) throws Exception {
		return parseActionDataFields(zRequest, zDataId, false);
	}
	
	private static int checkArgument(String arg, String prefix) throws Exception {
		if (arg.startsWith(prefix)) return prefix.length();
//		String arg2 = arg.lastIndexOf("__l")==-1?arg:arg.substring(0,arg.lastIndexOf("__l"));
//		String prefix2= prefix.lastIndexOf("_fd")==-1?prefix:prefix.substring(0,prefix.lastIndexOf("_fd"));
//		if (arg2.startsWith(prefix2)) 
//			return arg.substring(0,arg.indexOf("_fd-")+4).length();
//		
		return -1;
	}

	public static JWebActionData parseActionDataFields(JWebRequest zRequest, String zDataId, boolean bIncludeEncryptedData) throws Exception {
		JWebActionData oResult=null;
		String sPrefix=ACTION_DATA_FIELD_PREFIX+zDataId+"-";
		JIterator<String> oRawArgsIt=zRequest.getAllArgumentNames();
		String sArgName;
		int lenght=0;
		while (oRawArgsIt.hasMoreElements()) {
			sArgName=oRawArgsIt.nextElement();
			lenght=checkArgument(sArgName,sPrefix);
			if (lenght!=-1) {
				if (oResult==null) {
					oResult=new JWebActionData(zDataId);
				}
				addDataField(oResult, sArgName.substring(lenght), null, zRequest.getArgument(sArgName), bIncludeEncryptedData);
			}
		}
		if (oResult!=null) {
			oResult.setReadOnly(true);
		}
		return oResult;
	}

	//
	//
	// INTERNAL UTILITY METHODS
	//
	//

	/*
	 * private synchronized static void checkAllPssActionsLoaded() throws Exception { if (oModuloPss != null) return; // load all actions, without regarding which user is logged in; // this instance of Pss module is just for action providing; // security is checked though the #canPerform(...) method BizUsuario oGlobal = BizUsuario.GetGlobal(); BizUsuario oAdmin = new BizUsuario(); oAdmin.SetNoExcSelect(true); if (!oAdmin.Read(BizUsuario.GetAdminUsername())) { JExcepcion.SendError("No hay usuario administrador (ADMIN) en la base de datos."); } BizUsuario.SetGlobal(oAdmin); try { oModuloPss = new GuiModuloPss(); oModuloPss.SetAcciones(new GuiActions()); oModuloPss.OnGenerarAcciones(); } catch (Exception e) { throw e; } finally { BizUsuario.SetGlobal(oGlobal); } }
	 */

	static void fillSourceActionDataFor(JWebRequest zRequest, JWebServerAction zAction) throws Exception {
		JIterator<String> oRawArgsIt=zRequest.getAllArgumentNames();
		String sArgName;
		while (oRawArgsIt.hasMoreElements()) {
			sArgName=oRawArgsIt.nextElement();
			if (sArgName.equals("subsession")) {
				addDataField(addDataToAction(zAction, "subsession"), "subsession", "=", zRequest.getArgument(sArgName));
			} else if (sArgName.equals("is_mobile")) {
				zAction.addRedirectorParam("is_mobile="+ zRequest.getArgument(sArgName));
			} else if (sArgName.startsWith(ACTION_DATA_PREFIX)) {
				parseActionData(addDataToAction(zAction, sArgName.substring(ACTION_DATA_PREFIX.length())), zRequest.getArgument(sArgName));
			} else if (sArgName.startsWith(ACTION_DATA_FIELD_PREFIX)) {
				parseActionDataField(zAction, sArgName.substring(ACTION_DATA_FIELD_PREFIX.length()), zRequest.getArgument(sArgName));
			} else if (!sArgName.startsWith("src_")&&!sArgName.equals("sbmt")) {
				if (sArgName.equals("traceid")) continue;
				if (sArgName.equals("ajaxContainer")) continue;
				if (sArgName.equals("table_rows")) continue;
				if (sArgName.equals("dictionary")) continue;
				if (sArgName.equals("id")) continue;
				if (sArgName.equals("uploaded_file")) continue;
				if (sArgName.equals("lastupdate")) continue;
				if (sArgName.equals("marca")) continue;
				if (sArgName.equals("draw")) continue;
				if (sArgName.startsWith("columns")) continue;
				if (sArgName.startsWith("order")) continue;
				if (sArgName.startsWith("start")) continue;
				if (sArgName.startsWith("length")) continue;
				if (sArgName.startsWith("search")) continue;
				if (sArgName.startsWith("_")) continue;
	//			throw new RuntimeException("Unrecognized request argument name: "+sArgName);
			}
		}
	}
	static JWebServerAction fillRedirectionData(JWebRequest zRequest, BizAction action, JBaseWin win) throws Exception {
		JWebServerAction server=new JWebModalExceptionAction(false);		
		server.prepareWebAction(action, null, null);
		server.setOpenInModal(action.isModal());
		String s="";
		JIterator<String> oRawArgsIt=zRequest.getAllArgumentNames();
		String sArgName;
		while (oRawArgsIt.hasMoreElements()) {
			sArgName=oRawArgsIt.nextElement();
			if (sArgName.indexOf("dictionary")!=-1 ||
					sArgName.indexOf("back_on_print")!=-1 ||
					sArgName.indexOf("request")!=-1) {
				s+="&"+sArgName+"="+zRequest.getArgument(sArgName);
			}
		}
		String id = "rdx_" +  getCurrentRequest().getRegisteredObjectsOld().size();
		
		getCurrentRequest().registerObject(id, win);
		s+="&dg_object_owner="+id;
		s+="&dg_object_action="+id;
		
		oRawArgsIt=zRequest.getAllArgumentNames();
		while (oRawArgsIt.hasMoreElements()) {
			sArgName=oRawArgsIt.nextElement();
			if (sArgName.indexOf("object_owner")!=-1) {

				continue;
			} else if (sArgName.indexOf("object_action")!=-1) {
				continue;
			} else if (sArgName.indexOf("table_provider")!=-1) {
				continue;
			} else if (sArgName.indexOf("action")!=-1) {
				JWebActionData oData=addDataToAction( server, "action");
				oData.add("act",  action.getIdAction());
			}	else if (sArgName.equals("subsession")) {
				addDataField(addDataToAction( server, "subsession"), "subsession", "=", zRequest.getArgument(sArgName));
			} else if (sArgName.equals("is_mobile")) {
				s+="&is_mobile="+zRequest.getArgument(sArgName);
			} else if (sArgName.startsWith(ACTION_DATA_PREFIX)) {
				parseActionData(addDataToAction( server, sArgName.substring(ACTION_DATA_PREFIX.length())), zRequest.getArgument(sArgName));
			} else if (sArgName.startsWith(ACTION_DATA_FIELD_PREFIX)) {
				parseActionDataField( server, sArgName.substring(ACTION_DATA_FIELD_PREFIX.length()), zRequest.getArgument(sArgName));
			} else if (!sArgName.startsWith("src_")&&!sArgName.equals("sbmt")) {
				if (sArgName.equals("traceid")) continue;
				if (sArgName.equals("ajaxContainer")) continue;
				if (sArgName.equals("table_rows")) continue;
				if (sArgName.equals("dictionary")) continue;
				if (sArgName.equals("id")) continue;
				if (sArgName.equals("uploaded_file")) continue;
				if (sArgName.equals("lastupdate")) continue;
				if (sArgName.equals("marca")) continue;
				if (sArgName.equals("draw")) continue;
				if (sArgName.startsWith("columns")) continue;
				if (sArgName.startsWith("order")) continue;
				if (sArgName.startsWith("start")) continue;
				if (sArgName.startsWith("length")) continue;
				if (sArgName.startsWith("search")) continue;
				if (sArgName.startsWith("_")) continue;
	//			throw new RuntimeException("Unrecognized request argument name: "+sArgName);
			}
		}
		server.setRedirectorParam(s);
		return server;
	}
	private static boolean isAlwaysReplaceableData(String zDataId) {
		return zDataId.equals("tree_selection")||zDataId.equals("client_conf");
	}

	private static JWebActionData addDataToAction(JWebServerAction zAction, String zDataId) {
		if (isAlwaysReplaceableData(zDataId)) {
			return zAction.addReplaceData(zDataId);
		} else {
			return zAction.addData(zDataId);
		}
	}

	private static void parseActionDataField(JWebServerAction zAction, String zFullFieldName, String zFieldValue) {
		if (zFieldValue.length()>2000) // limite al tamaño, no entiendo bien para que se manda esto, no parece afectar al funcionamiento. SI es muy grande explota la pila
			return;
		int iSepIndex=zFullFieldName.lastIndexOf('-');
		if (iSepIndex==-1) {
			throw new RuntimeException("Invalid data field name: "+zFullFieldName);
		}
		String sDataId=zFullFieldName.substring(0, iSepIndex);
		JWebActionData oData=zAction.getData(sDataId);
		if (oData==null) {
			oData=addDataToAction(zAction, sDataId);
		}
		addDataField(oData, zFullFieldName.substring(iSepIndex+1), null, zFieldValue);
	}

	public static JWebAction createWinListDelete(BizAction zAction, JWebActionOwnerProvider zObjectProvider) throws Exception {
		JWebServerAction s=new JWebWinListDeleteAction();
		return s.prepareWebAction(zAction, zObjectProvider, null);
	}
	public static JWebAction createWinListEdit(BizAction zAction, JWebActionOwnerProvider zObjectProvider) throws Exception {
		JWebServerAction s=new JWebWinListEditAction();
		return s.prepareWebAction(zAction, zObjectProvider, null);
	}
//	public static JWebAction createWinListReload(int zWinid, BizAction zAction, JWebActionOwnerProvider zObjectProvider) throws Exception {
//
//		JWebServerAction oServerAction=new JWebWinListReloadAction();
//		return JWebActionFactory.create(zAction, zObjectProvider, oServerAction, null);
//	}
	/*
	 * public static JWebAction createReport(BizAction zAction, JWebActionOwnerProvider zObjectProvider) throws Exception { JWebServerAction oServerAction = new JWebReportAction(); return JWebActionFactory.create(zAction, zObjectProvider, oServerAction, null); }
	 */

}
