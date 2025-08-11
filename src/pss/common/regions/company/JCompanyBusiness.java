package pss.common.regions.company;

import java.net.URL;
import java.util.Map;

import org.apache.avalon.framework.parameters.Parameters;
import org.apache.cocoon.environment.SourceResolver;

import pss.JPath;
import pss.common.agenda.evento.GuiEvento;
import pss.common.agenda.evento.GuiEventos;
import pss.common.agenda.evento.logicas.EventoLogicaNada;
import pss.common.agenda.evento.tipo.BizTipoEvento;
import pss.common.customList.config.BizCustomListModules;
import pss.common.customList.config.carpetas.GuiCarpeta;
import pss.common.customList.config.carpetas.GuiCarpetas;
import pss.common.customList.config.customlist.BizCustomList;
import pss.common.customList.config.relation.JRelations;
import pss.common.documentos.BizDocumento;
import pss.common.documentos.GuiDocumento;
import pss.common.documentos.docElectronico.BizDocElectronico;
import pss.common.documentos.docElectronico.GuiDocElectronico;
import pss.common.documentos.docEmail.BizDocEmail;
import pss.common.documentos.docEmail.GuiDocEmail;
import pss.common.documentos.docLocal.BizDocLocal;
import pss.common.documentos.docLocal.GuiDocLocal;
import pss.common.documentos.tipos.BizDocFisicoTipo;
import pss.common.event.device.BizQueueMessage;
import pss.common.event.sql.GuiSqlEvent;
import pss.common.layoutWysiwyg.BizPlantilla;
import pss.common.layoutWysiwyg.GuiPlantilla;
import pss.common.layoutWysiwyg.GuiPlantillas;
import pss.common.layoutWysiwyg.IPlantilla;
import pss.common.personalData.BizPersona;
import pss.common.personalData.GuiPersonas;
import pss.common.regions.multilanguage.JLanguage;
import pss.common.security.BizUsuario;
import pss.common.security.GuiCambioPassword;
import pss.common.security.GuiUsuario;
import pss.common.security.GuiUsuarios;
import pss.core.data.BizPssConfig;
import pss.core.services.JExec;
import pss.core.services.records.BizVirtual;
import pss.core.services.records.JRecord;
import pss.core.services.records.JRecords;
import pss.core.tools.JExcepcion;
import pss.core.tools.JTools;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JList;
import pss.core.tools.collections.JMap;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.win.actions.BizAction;
import pss.core.winUI.icons.GuiIconos;
import pss.www.platform.applications.JWebApplicationSession;
import pss.www.platform.content.providers.JWebBusinessFileProvider;

public abstract class JCompanyBusiness {

	String tipoBusiness;
  String outFolder = null;
  
	public void setTipoBusiness(String tipoBusiness) {
		this.tipoBusiness=tipoBusiness;
		getInternalOutFolder();
	}

	public String getTipoBusiness() {
		return tipoBusiness; 
	}
	
	public String getTipoUsuarioDefault() {
		return null; 
	}
	
	
	
	private String getInternalOutFolder()  {
		if (outFolder!=null) return outFolder;
		try {
			outFolder=getOutFolder();
			JWebBusinessFileProvider.setOutFolder(outFolder);
			JWebBusinessFileProvider.setSitemapFolder(getSitemapFolder());
		} catch (Exception e) {
			outFolder=null;
		}
		return outFolder;
	}
	
	public boolean isAcceptComma() {
		return false;
	}

	public String getSitemapFolder() throws Exception {
		return "business_resource";
	}
	public String getOutFolder() throws Exception {
		return JPath.PssPathTempFiles();
	}

	public URL getPathMultilanguage(String lang) {
		return null;
	}
	// Classes

	public abstract GuiNewCompany getNewWinInstance() throws Exception;

	public abstract GuiCompany getWinInstance() throws Exception;

	public abstract BizCompany getInstance() throws Exception;

	public abstract JWin getSpecialNode() throws Exception;

	public String getPreviewFlag() throws Exception {
		return JWins.PREVIEW_MAX;// JWins.PREVIEW_SI;//JWins.PREVIEW_MAX;
	}

	public BizUsuario getUserInstance() throws Exception {
		return new BizUsuario();
	}
	public GuiUsuario getUserWinInstance() throws Exception { 	
		return new GuiUsuario();
	}

	public Class getPersonasClass() throws Exception { 	
		return GuiPersonas.class;
	}

	public Class getEventosClass() throws Exception { 	
		return GuiEventos.class;
	}	  
	public GuiEvento getNewEventos() throws Exception { 	
		return new GuiEvento();
	}	  
	public GuiSqlEvent getSqlEventWinInstance() throws Exception { 	
		return new GuiSqlEvent();
	}
	public GuiCarpeta getCarpeta() throws Exception { 	
		return new GuiCarpeta();
	}
	public GuiCarpetas getCarpetas() throws Exception { 	
		return new GuiCarpetas();
	}
	
	public boolean isSqlEventProcessInService() throws Exception { 	
		return false;
	}

	public boolean isNeedAutoRefresh(String marca) throws Exception {
		if (marca.equals("SYSTEMINFO")) return true;
		return false;
	}
	
	public boolean forceConvertToResponsive() throws Exception {
		return true;
	}	
	// Labels

	public String getLabelArticles() throws Exception {
		return "Artículos";
	}

	public String getLabelArticle() throws Exception {
		return "Artículo";
	}

	public String getLabelSubCliente() throws Exception {
		return "Sub-Cliente";
	}

	public String getLabelSubClientes() throws Exception {
		return "Sub-Clientes";
	}

	public String getLabelRegionOrigen() throws Exception {
		return "Región de Origen";
	}

	public String getLabelRegionOrigenPlural() throws Exception {
		return "Regiones de Origen";
	}

//	public String getLabelRegionOrigenFiltro() throws Exception {
//		return "Región";
//	}

	public long getPageSize() throws Exception {
		return 10;
	}
	public String getLabelReporteTerminal() throws Exception {
		return "POS";
	}


	public String getLabelNodo() throws Exception {
		return "Nodo";
	}


	public String getTitle() throws Exception {
		return BizPssConfig.getPssConfig().getTitle();
	}
	// Campos Requeridos

	// Defaults
	public boolean getDefaultCuentaContado() {
		return false;
	}

	public String getDefaultOperationForReportsCustomers() {
		return "";
	}

	public boolean useCustomForms() throws Exception {
		return true;
	}

	// Products
	public abstract JList<String> getProductTypes() throws Exception;

	// public JList getSaleOperations() throws Exception { return null;}

	// Varios
	public abstract boolean isValidOperation(String zOperation) throws Exception;

	public abstract boolean isValidOperationForCustomers(String zOperation) throws Exception;

	public boolean isValidOperationForSuppliers(String zOperation) throws Exception {
		return false;
	}
	
	public  String getAfterLoginChangeHomePage() throws Exception {
		return null;
	}
	
  public JMap<String, String> getBusinessHomePages() throws Exception {
  	homepages = null;
 	  this.createBusinessHomePages();
  	return homepages; 
  }
  
	public abstract JMap<String, String> getEventCodes() throws Exception;
	
  /*
   * create your own home pages for your application
   * 1.- overwrite this method in your company business class
   * 2.- add your home pages: addPage( class action , human description )
   * class action example: pss.common.security.GuiModuloCommon_1 
   */
  public abstract void createBusinessHomePages() throws Exception;
	
  private JMap<String, String> homepages = null;
	public void addPage(String classaction, String description) throws Exception {
		if ( homepages == null ) homepages=JCollectionFactory.createMap();
		if ( BizUsuario.ifOperacionHabilitada(classaction) ) 
		  homepages.addElement(classaction, description);
	}
	
	public boolean isUseTurnos() {
		return false;
	}

	public boolean isCustomListSimplify() throws Exception{
		return false;
	}
	public boolean hasRolesAplicacion() {
		return true;
	}
	

	public String getUserMensajes() throws Exception {
		return null;
	}
	
	// RJL: cambio lo que devueve este metodo
	// reemplazar el String[] por BizQueueMessage.buildSimpleMessage(String title,String info,String type, boolean permanent,String link);
	public BizQueueMessage getEmergMensajes() throws Exception {
		return null;
	}

	public boolean hasMailIcon() throws Exception {
		return true;
	}

	public String getHelp() throws Exception {
		return null;
	}


  public JMap<String, String> getUserManuals() throws Exception {
  	JMap<String, String> map=JCollectionFactory.createOrderedMap();
//  	map.addElement("Conceptos Basicos","common/manuals/capacitacion_1.mp4");
//  	map.addElement("Mesa de Entrada","common/manuals/mesaEntrada.mp4");
//  	map.addElement("Expedientes","common/manuals/expedientes.mp4");
//  	map.addElement("Legajos","common/manuals/Legajos_1.mp4");
//  	map.addElement("Ej. Cambio Razon Social","common/manuals/expcambiorazonsocial.mp4");
//  	map.addElement("Ej. Alta Sucursal","common/manuals/alta_sucursal.mp4");
  	return map;
  } 

	public abstract JWins getBusinessModules() throws Exception;
	
	public boolean isBusinessModuleInstalled(String zModule) throws Exception{
		BizCompanyBusinessModule module = new BizCompanyBusinessModule();
		module.dontThrowException(true);
		return module.Read( BizUsuario.getUsr().getObjCompany().getCompany(), BizUsuario.getUsr().getObjBusiness().getTipoBusiness()  , zModule);
	}
	
	public void attachRelationMap(BizCustomListModules self,JRelations rels) throws Exception {
		self.attachModule(1, BizCustomListModules.SET_SQL_CLASS, rels);
	}
	
	public boolean findMinFechaVto() throws Exception {
		return false;
	}

	public String getLabelRegionOrigenFiltro() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public Class<?> getShopClass() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
  public GuiUsuarios getCompanyWinUsersClass() throws Exception {
  	return new GuiUsuarios();
  }
  
	public JWins getModuloLayouts() throws Exception {
		return new GuiPlantillas();
	}
	public JWin getModuloLayout() throws Exception {
		return new GuiPlantilla();
	}
	public IPlantilla getModuloBizLayout() throws Exception {
		return new BizPlantilla();
	}
	
	public String getResourcePublicWeb(SourceResolver resolver, Map objectModel, String zRequest, Parameters par) {
		
		
		return ""; 
	}
		
	public String getResourcePublicWeb(SourceResolver resolver, Map objectModel, String zRequest, Map params) {
			
			
			return ""; 
	}

	  protected static JMap<String,String> mapa;
//	  public static String FECHA_INICIAL_EXP = "_TIF";
//	  public static String FECHA_FINAL_EXP = "_TTF";
//	  public static String ID_EXPEDIENTE = "_E";
//	  public static String ID_LEGAJO = "_L";
	  public static String FECHA_INICIAL_MES = "_IMES";
	  public static String FECHA_INICIAL_ANO = "_IANO";
	  public static String FECHA_FINAL_MES = "_FMES";
	  public static String FECHA_FINAL_ANO = "_FANO";
	  public static String FECHA_ACTUAL = "_FACT";
	  
	public JMap<String, String> getCamposClavesParaCustomList() throws Exception {
	  	if (mapa!=null) return mapa;
	  	mapa = JCollectionFactory.createMap();
//	  	mapa.addElement(FECHA_INICIAL_EXP, "Fecha Inicial exp");
//	  	mapa.addElement(FECHA_FINAL_EXP, "Fecha final exp");
	  	mapa.addElement(FECHA_INICIAL_MES, "Fecha inicio mes");
	  	mapa.addElement(FECHA_FINAL_MES, "Fecha fin de mes");
	  	mapa.addElement(FECHA_INICIAL_ANO, "Fecha inicio año");
	  	mapa.addElement(FECHA_FINAL_ANO, "Fecha fin de año");
	   	mapa.addElement(FECHA_ACTUAL, "Fecha Actual");
//	 	  mapa.addElement(ID_EXPEDIENTE, "Id Expediente");
//	  	mapa.addElement(ID_LEGAJO, "Id Legegajo");
	  	return mapa;
  }
  public JMap<String, String> getNivelDatos(String origen) throws Exception {
		JMap<String, String> map = JCollectionFactory.createOrderedMap();
		map.addElement(origen, getOrigenDatos().getElement(origen)); // un solo nivel
  	return map;
  }
	public JMap<String, String> hCacheOrigenDatos = null; 
  public JMap<String, String> getOrigenDatos() throws Exception {
  	if (hCacheOrigenDatos==null) {
  		JMap<String, String> map = JCollectionFactory.createOrderedMap();
//  		if (JTools.isInstalled("pss.sj.tramites.BizTramite")) map.addElement("T", "Expediente");
  		map.addElement("P", "Usuario");
  		map.addElement("L", "Listado");
  		map.addElement("U", "Compania info");
  		map.addElement("D", "Documento");
  		hCacheOrigenDatos=map;
  	}
  	return hCacheOrigenDatos;
  }
  
  public JMap<String, String> hCacheClassOrigenDatos = null; 
  public  JMap<String, String> getClassOrigenDatos() throws Exception {
  	if (hCacheClassOrigenDatos==null) {
  		JMap<String, String> map = JCollectionFactory.createOrderedMap();
//  		if (JTools.isInstalled("pss.sj.tramites.BizTramite")) map.addElement("T", "pss.sj.tramites.BizTramite");
  		map.addElement("L", BizCustomList.class.getCanonicalName());
  		map.addElement("U", BizCompany.class.getCanonicalName());
  		map.addElement("P", BizUsuario.class.getCanonicalName());
  		map.addElement("D", BizDocumento.class.getCanonicalName());
  		hCacheClassOrigenDatos=map;
  	}
  	return hCacheClassOrigenDatos;
  }
  
  public String getEmailDefault() throws Exception {
  	return null;
  }

  public String getNombreCompleto(BizPersona persona) throws Exception {
		if (persona.isPersonaFisica()) {
			if (persona.GetNombre().isEmpty()) 
				return persona.GetApellido();
			return persona.GetApellido()+", "+JTools.capitalizeAll(persona.GetNombre());
		}
		if (persona.isPersonaJuridica()) {
			return persona.GetApellido()+((persona.GetNombre().isEmpty()||persona.GetNombre().equals(persona.GetApellido()))?"":" ("+persona.GetNombre()+")");
		}
		return persona.GetNombre();
  }
  
  protected JMap<String, String> definiciones;
  public static String SPLITWINLIST = "SPWL";
  public static String USECONTEXTMENU = "UCM";
  public static String CONICONOS = "CICO";
  public static String MAXMENUHORIZONTAL = "MXMN";
  public static String getDefiniciones(String def) throws Exception {
  	if (BizUsuario.getUsr()==null)
  		return null;
  	return BizUsuario.getUsr().getObjBusiness().getDefiniciones().getElement(def);
  }
  public static long getDefinicionesNumber(String def) throws Exception {
  	if (BizUsuario.getUsr()==null)
  		return 0;
  	return JTools.getLongFirstNumberEmbedded(BizUsuario.getUsr().getObjBusiness().getDefiniciones().getElement(def));
  }
  
	public boolean isLDAP() {
		return false;
	}
	public boolean useMaximizeTabs() {
		return true;
	}
  public JMap<String, String> getDefiniciones() throws Exception {
  	if (definiciones!=null) return definiciones;
  	definiciones = JCollectionFactory.createMap();
  	definiciones.addElement(SPLITWINLIST, "30%");
  	definiciones.addElement(USECONTEXTMENU, "N");
  	definiciones.addElement(CONICONOS, "N");
  	definiciones.addElement(MAXMENUHORIZONTAL, "10");
  	return definiciones;
  }

  public void loadIcons(GuiIconos iconos) throws Exception {
  }
  public JWin buildCambioClave(String login,String descrip, String  key, String  newkey,String confirmKey2) throws Exception {
  	GuiCambioPassword oCambioPassword = new GuiCambioPassword();
    oCambioPassword.GetcDato().setUser(login);
    if (descrip!=null) oCambioPassword.GetcDato().setDescripcion(descrip);
    if (key!=null) oCambioPassword.GetcDato().setCurrentPassword(key);
    if (newkey!=null) oCambioPassword.GetcDato().setNewPassword(newkey);
    if (confirmKey2!=null) oCambioPassword.GetcDato().setConfirmPassword(confirmKey2);
    return oCambioPassword;
  }
  
  public String getWinListToolbarPosDefault() throws Exception {
	  return null;
  }
  public String getWinListToolbarEmbbededPorDefault() throws Exception {
	  return null;
  }
  public long getCountColumnsForAddAutocomplete() throws Exception {
	  return 1; //-1:never ... x: x columnas o menos agrega una columna vacia que autocompleta
  }
  public String getFormToolbarPosDefault() throws Exception {
	  return null;
  }
  
  
  public String[] createStringsFixed() throws Exception {
		return null;
	}
  
  public String[] createStrings() throws Exception {
		return null;
	}
	public void eventInterfaz(String componente, String mensaje, long percent, long total, boolean cancel, String icon) throws Exception {
		JWebApplicationSession.sendWaitMessage(icon, percent!=-1, true, percent, total, JLanguage.translate(mensaje));
  }
	public void clearEventInterfaz() throws Exception {
		JWebApplicationSession.clearWaitMessage();
	}

  public boolean isErrorApplication(Throwable zException) {
  	return false;
  }
  
  public String getSkinForced() throws Exception {
  	return null;
  }
 public boolean isFilterBarOpenByDefault() {
  	return false;
  }
  public JList<BizAction> getGlobalActions(BizUsuario user) throws Exception {
  	return null;
  }

  public String getCompanyDefault() throws Exception {
  	JExcepcion.SendError("No esta definida la empresa default");
  	return null;
  }
  
  public GuiDocumento documentWinCreate(String type) throws Exception {
		if (type.equals(BizDocFisicoTipo.CORREO)) return new GuiDocEmail();
		if (type.equals(BizDocFisicoTipo.ELECT)) return new GuiDocElectronico();
		if (type.equals(BizDocFisicoTipo.LOCAL)) return new GuiDocLocal();
		return null;
	}
  public BizDocumento documentBizCreate(String type) throws Exception {
  	if (type.equals(BizDocFisicoTipo.CORREO)) return new BizDocEmail();
  	if (type.equals(BizDocFisicoTipo.ELECT)) return new BizDocElectronico();
  	if (type.equals(BizDocFisicoTipo.LOCAL)) return new BizDocLocal();
  	return null;
  }
  public JMap<String,String> documentClasesConocidas(JMap<String,String> cc) {
  	cc.addElement(BizDocFisicoTipo.CORREO, BizDocEmail.class.getCanonicalName());
  	cc.addElement(BizDocFisicoTipo.ELECT, BizDocElectronico.class.getCanonicalName());
  	cc.addElement(BizDocFisicoTipo.LOCAL, BizDocLocal.class.getCanonicalName());
  	return cc;
  }  

  public Class eventoLogicasClasesConocidas(String id) {
	  	if (id.equals(BizTipoEvento.HACER_NADA)) return  EventoLogicaNada.class;
	  	return null;
  }
  
  public JMap<String,String> eventoLogicasClasesConocidasMap(JMap<String,String> cc) {
  	cc.addElement(BizTipoEvento.HACER_NADA, "Sin operacion asociada");
  	return cc;
  }
  
	public BizUsuario execRegisterNewCompany(final String sUsername,final String sCompany,final String sPassword,final String sEmail,final String sPais) throws Exception {
		JExec oExec = new JExec(null,"registerNewCompany") {

			@Override
			public void Do() throws Exception {
				setOutput(registerNewCompany(sUsername, sCompany, sPassword, sEmail, sPais));
			}
		};
		oExec.execute();
		return (BizUsuario)oExec.getOutput();
	}

  public BizUsuario registerNewCompany(String sUsername,String sCompany,String sPassword,String sEmail,String sPais) throws Exception {
  	BizCompany company = getWinInstance().GetcDato();
  	company.Read(this.getCompanyDefault());
  	return company.buildNewCompany( this,sUsername, sCompany, sPassword, sEmail, sPais);
  }
  public void startRegisterNewCompany(String sUsername,String sCompany,String sPassword,String sEmail,String sPais) throws Exception {
  	BizCompany company = getWinInstance().GetcDato();
  	company.Read(this.getCompanyDefault());
  	company.registerNewCompany( this,sUsername, sCompany, sPassword, sEmail, sPais);
  }
  


  public void recoveryUser(String sEmail) throws Exception {
  	
  	
  	BizUsuario user = getUserInstance();
  	user.dontThrowException(true);
  	if (!user.readByMail(sEmail)) {
  		throw new Exception("Lo siento. eMail desconocido");
  	}
  	BizUsuario.SetGlobal(user);
  	user.recoveryPassword();
  	BizUsuario.SetGlobal(null);
  	     // enviar un mail con clave
  }
	
}
