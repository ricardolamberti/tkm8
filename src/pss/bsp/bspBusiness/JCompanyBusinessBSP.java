package pss.bsp.bspBusiness;

import java.util.Date;
import java.util.Map;

import org.apache.cocoon.environment.SourceResolver;

import pss.JPath;
import pss.bsp.GuiModuloBSP;
import pss.bsp.carpeta.GuiBSPCarpeta;
import pss.bsp.carpeta.GuiBSPCarpetas;
import pss.bsp.contrato.BizContrato;
import pss.bsp.contrato.detalle.BizDetalle;
import pss.bsp.contrato.detalle.prorrateo.header.BizHeaderProrrateo;
import pss.bsp.event.GuiBSPSqlEvent;
import pss.bsp.persona.GuiPersonaBSPs;
import pss.common.agenda.evento.GuiEventos;
import pss.common.customList.config.BizCustomListModules;
import pss.common.customList.config.carpetas.GuiCarpeta;
import pss.common.customList.config.carpetas.GuiCarpetas;
import pss.common.customList.config.customlist.BizCustomList;
import pss.common.customList.config.relation.JRelations;
import pss.common.event.action.history.BizSqlEventHistory;
import pss.common.event.device.BizQueueMessage;
import pss.common.event.sql.BizSqlEvent;
import pss.common.event.sql.GuiSqlEvent;
import pss.common.event2.telegram.BizTelegramUserChannel;
import pss.common.regions.company.BizCompany;
import pss.common.regions.company.GuiCompany;
import pss.common.regions.company.GuiNewCompany;
import pss.common.regions.company.JCompanyBusiness;
import pss.common.scheduler.BizScheduler;
import pss.common.security.BizUsuario;
import pss.common.security.GuiUsuario;
import pss.core.tools.PssLogger;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JList;
import pss.core.tools.collections.JMap;
import pss.core.win.JBaseWin;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.www.platform.actions.JWebActionFactory;

public class JCompanyBusinessBSP extends JCompanyBusiness {

	public static final String FORMAT_DATE = "yyyyMMdd";
	public static final String FORMAT_DATETIME = "yyyyMMddHHmm";
	// Classes

	public BizCompany getInstance() throws Exception {
		return new BizBSPCompany();
	}

	@Override
	public JWin getSpecialNode() throws Exception {
		return new GuiBSPCompany();
	}

	@Override
	public BizUsuario getUserInstance() throws Exception {
		return new BizBSPUser();
	}

	public GuiUsuario getUserWinInstance() throws Exception {
		return new GuiBSPUsuario();
	}

	public Class getPersonasClass() throws Exception {
		return GuiPersonaBSPs.class;
	}

	public Class getEventosClass() throws Exception {
		return GuiEventos.class;
	}
	// Labels

	@Override
	public String getLabelArticles() throws Exception {
		return "Boletos";
	}

	@Override
	public String getLabelArticle() throws Exception {
		return "Boleto";
	}

	@Override
	public String getLabelSubCliente() throws Exception {
		return "Cliente";
	}

	@Override
	public String getLabelSubClientes() throws Exception {
		return "Clientes";
	}

	@Override
	public String getLabelRegionOrigen() throws Exception {
		return "Region";
	}

	@Override
	public String getLabelRegionOrigenPlural() throws Exception {
		return "Regiones";
	}

	@Override
	public String getLabelRegionOrigenFiltro() throws Exception {
		return "Region";
	}

	@Override
	public String getLabelReporteTerminal() throws Exception {
		return "Terminal";
	}

	@Override
	public String getLabelNodo() throws Exception {
		return "Nodo";
	}

	@Override
	public GuiSqlEvent getSqlEventWinInstance() throws Exception {
		return new GuiBSPSqlEvent();
	}

	public GuiCarpeta getCarpeta() throws Exception {
		return new GuiBSPCarpeta();
	}

	public GuiCarpetas getCarpetas() throws Exception {
		return new GuiBSPCarpetas();
	}

	public boolean isSqlEventProcessInService() throws Exception {
		return true;
	}

	public String getHelp() throws Exception {
		return "do-help";
	}

	// Defaults
	@Override
	public boolean getDefaultCuentaContado() {
		return true;
	}

	@Override
	public String getDefaultOperationForReportsCustomers() {
		return "";
	}

	@Override
	public JList<String> getProductTypes() throws Exception {
		return null;
	}

	@Override
	public boolean isValidOperation(String operation) throws Exception {
		return false;
	}

	@Override
	public boolean isValidOperationForCustomers(String operation) throws Exception {
		return false;
	}

	@Override
	public void createBusinessHomePages() throws Exception {
		addPage("pss.bsp.GuiModuloBSP_10", "Consola BSP");
	}

	@Override
	public GuiNewCompany getNewWinInstance() throws Exception {
		return new GuiNewBSPCompany();
	}

	@Override
	public GuiCompany getWinInstance() throws Exception {
		return new GuiBSPCompany();
	}

	public void attachRelationMap(BizCustomListModules self, JRelations rels) throws Exception {
		self.attachModule(1, BizCustomListModules.SET_BSP_CLASS, rels);
	}

	public JMap<String, String> getEventCodes() throws Exception {
		return null;
	}

	@Override
	public JWins getBusinessModules() throws Exception {
		return null;
	}

	static String oldStatus;

	@Override
	public boolean isNeedAutoRefresh(String marca) throws Exception {
		if (marca.equals("BSPSERVICE")) {
			String newStatus = BizScheduler.readStatus();
			boolean change = !newStatus.equals(oldStatus);
			oldStatus = newStatus;
			return change;
		}
		// RJL esto hay q repensarlo
//		if ((marca.equals("SQLEVENT") || marca.equals("DASHBOARD")) && BizUsuario.getUsr() != null) {
//			BizInterfazNew news = new BizInterfazNew();
//			news.dontThrowException(true);
//			if (news.read(BizUsuario.getUsr().getCompany())) {
//				try {
//					JApplicationSessionManager sessionManager = JWebServer.getInstance().getWebApplication(null).getSessionManager();
//					JSessionController sessions[] = (JSessionController[]) sessionManager.getSessions().values().toArray();
//					for (int i = 0; i < sessions.length; i++) {
//						JSessionController ss = sessions[i];
//						if (!ss.getSession().getUser().GetUsuario().equals(BizUsuario.getUsr().GetUsuario()))
//							continue;
//						BizOnlineUser user = new BizOnlineUser();
//						user.fill(null, ss.getSession());
//						BizUserHistory hist = user.getLastHistory();
//						Date fechaControl = news.getLastupdate();// DASHBOARD
//						if (marca.equals("SQLEVENT"))
//							fechaControl = news.getLastSqlEvent();
//
//						return (hist.getFechaHora().before(fechaControl));
//					}
//				} catch (Exception e) {
//					return false;
//				}
//			}
//		}
		return false;
	}

	@Override
	public String getResourcePublicWeb(SourceResolver resolver, Map objectModel, String zRequest, org.apache.avalon.framework.parameters.Parameters par) {
		String sPssRelativePath = "";

		try {
			if (zRequest.startsWith("informe"))
				sPssRelativePath = JPath.PssPathTempFiles() + "/" + resolveInforme(zRequest);

		} catch (Exception e) {
			PssLogger.logError(e);
		}

		return sPssRelativePath;
	}

	public String resolveInforme(String zRequest) throws Exception {
		BizSqlEventHistory history = new BizSqlEventHistory();
		history.dontThrowException(true);

		if (!history.readByCode(zRequest))
			return null;
		return history.getFileElectronico();
	}

	public boolean isCustomListSimplify() throws Exception {
		if (BizUsuario.getUsr() == null)
			return true;
		return !BizUsuario.getUsr().isAnyAdminUser();
	}



	public JMap<String, String> getNivelDatos(String origen) throws Exception {
		JMap<String, String> map = JCollectionFactory.createOrderedMap();
		if (origen.equals("D")) {
			map.addElement("D", getOrigenDatos().getElement("D")); // un solo
																	// nivel
			map.addElement("C", getOrigenDatos().getElement("C")); // un solo
																	// nivel
			map.addElement("U", getOrigenDatos().getElement("U")); // un solo
																	// nivel
		} else {
			map.addElement(origen, getOrigenDatos().getElement(origen)); // un
																			// solo
																			// nivel
			map.addElement("U", getOrigenDatos().getElement("U")); // un solo
																	// nivel
		}
		return map;
	}

	public JMap<String, String> getOrigenDatos() throws Exception {
		if (hCacheOrigenDatos == null) {
			JMap<String, String> map = JCollectionFactory.createOrderedMap();
			map.addElement("I", "Aviso");
			map.addElement("C", "Contrato");
			map.addElement("L", "Listado");
			map.addElement("U", "Compania info");
			map.addElement("E", "Indicador");
			map.addElement("D", "Contrato Detalle");
			map.addElement("W", "Contrato Wizard");
			map.addElement("P", "Prorrateo");

			hCacheOrigenDatos = map;
		}
		return hCacheOrigenDatos;
	}

	public JMap<String, String> getCamposClavesParaCustomList() throws Exception {
		if (mapa != null)
			return mapa;
		mapa = JCollectionFactory.createMap();
		mapa.addElement(FECHA_INICIAL_MES, "Fecha inicio mes");
		mapa.addElement(FECHA_FINAL_MES, "Fecha fin de mes");
		mapa.addElement(FECHA_INICIAL_ANO, "Fecha inicio año");
		mapa.addElement(FECHA_FINAL_ANO, "Fecha fin de año");
		mapa.addElement(FECHA_ACTUAL, "Fecha actual");
		return mapa;
	}

	public JMap<String, String> getClassOrigenDatos() throws Exception {
		if (hCacheClassOrigenDatos == null) {
			JMap<String, String> map = JCollectionFactory.createOrderedMap();
			map.addElement("I", BizSqlEventHistory.class.getCanonicalName());
			map.addElement("C", BizContrato.class.getCanonicalName());
			map.addElement("L", BizCustomList.class.getCanonicalName());
			map.addElement("U", BizBSPCompany.class.getCanonicalName());
			map.addElement("E", BizSqlEvent.class.getCanonicalName());
			map.addElement("D", BizDetalle.class.getCanonicalName());
			// map.addElement("W", BizWizardContrato.class.getCanonicalName());
			map.addElement("P", BizHeaderProrrateo.class.getCanonicalName());

			hCacheClassOrigenDatos = map;
		}
		return hCacheClassOrigenDatos;
	}

	public String getWinListToolbarPosDefault() throws Exception {
		return JBaseWin.TOOLBAR_IN;
	}

	public String getFormToolbarPosDefault() throws Exception {
		return JBaseWin.TOOLBAR_TOP;
	}

	public String getEmailDefault() throws Exception {
		if (BizBSPUser.getUsrBSP().getBspConsola() != null)
			return BizBSPUser.getUsrBSP().getBspConsola().getConfigView().getEmail();
		return GuiModuloBSP.getBSPConsolaUsuario().GetcDato().getConfigView().getEmail();
	}

	public boolean isFilterBarOpenByDefault() {
		return !JWebActionFactory.isMobile();
	}
	
	public BizQueueMessage getEmergMensajes() throws Exception {
		
		if (hasToConfirmTelegram()) {
			return BizQueueMessage.buildSimpleMessage("Aviso ", "Se vinculo su cuenta con telegram",BizQueueMessage.TYPE_SUCCESS,false, null);
		}

		return null;
	}

	private boolean hasToConfirmTelegram() throws Exception {
		BizTelegramUserChannel t = new BizTelegramUserChannel();
		t.dontThrowException(true);
		t.addFilter("userid", BizUsuario.getUsr().GetUsuario());
		t.addFilter("confirmed", "N");
		if (t.read()) {
			t.setDateConfirmed(new Date());
			t.setConfirmed(true);
			t.execProcessUpdate();
			return true;
		}
		return false;
	}


}
