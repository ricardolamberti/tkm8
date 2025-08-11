/*
 * Created on 12-jun-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.ui;

import java.awt.Dimension;

import pss.GuiModuloPss;
import pss.common.security.BizUsuario;
import pss.core.JAplicacion;
import pss.core.data.BizPssConfig;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JList;
import pss.core.tools.collections.JMap;
import pss.core.win.actions.BizActions;
import pss.www.platform.actions.JWebAction;
import pss.www.platform.actions.JWebActionFactory;
import pss.www.platform.actions.JWebRequest;
import pss.www.platform.applications.JHistory;
import pss.www.platform.applications.JWebApplicationSession;
import pss.www.platform.applications.JWebHistoryManager;
import pss.www.platform.content.generators.JXMLContent;
import pss.www.ui.processing.JWebUICoordinator;

public abstract class JWebView extends JWebViewComposite implements JWebTitledComponent, JWebIconHolder {

	private String sTitle;
	private JWebIcon oIcon;
	private JWebUICoordinator oCoordinator;
	protected JWebViewHistoryBar oHistoryBar;
	private JWebRequest request;
	private JWebViewNavigationBar oNavBar;
	private JWebViewNavigationBar oMenu;
	private String sHelp;

	public String getHelp() {
		return sHelp;
	}

	public void setHelp(String sHelp) {
		this.sHelp = sHelp;
	}

	public JWebRequest getRequest() {
		return request;
	}

	public void setRequest(JWebRequest r) {
		this.request = r;
	}
	
	public boolean isActiveHelp() {
		return BizUsuario.getUsr()!=null && getHelp()!=null && JAplicacion.GetApp().isActiveHelp();
	}
	public boolean isHelpAvailable() throws Exception {
		return BizUsuario.getUsr()!=null && BizUsuario.getUsr().getObjCompany().getObjBusiness().getHelp()!=null;
	}

//	private boolean bHistoryAction=true;
	
	
	


	public JWebView() {
	}

	@Override
	public synchronized void ensureIsBuilt() throws Exception {
		if (this.oCoordinator==null) {
			JWebApplicationSession oSession=JWebApplicationSession.getCurrent();
			if (oSession!=null) {
				this.oCoordinator=oSession.getUICoordinator();
			} else {
				throw new RuntimeException("View has no UI coordinator!!");
			}
		}
		super.ensureIsBuilt();
	}

	@Override
	public void destroy() {
		this.sTitle=null;
		this.oIcon=null;
		this.oCoordinator=null;
		this.oNavBar=null;
		this.oHistoryBar=null;
		this.oMenu=null;
		super.destroy();
	}



	@Override
	public final boolean isView() {
		return true;
	}

	@Override
	protected void containerToXML(JXMLContent zContent) throws Exception {
		zContent.setAttribute("scroll", getScroll(zContent) );
		if (isActiveHelp()) 
			zContent.setAttribute("help", getHelp().indexOf("/")==-1?BizUsuario.getUsr().getObjCompany().getObjBusiness().getHelp():getHelp());//+"#"+getHelp():getHelp());
		
		this.generateJumbotron();
		this.generateNavigationBarActions();
		
		if (this.hasNavigationActions()) 
			this.oNavBar.toXML(zContent);

		if (this.getUICoordinator().hasSession() && skin().hasHistory()) {
			if (hasMoreOneHistory()) {
				this.generateHistoryBarActions(); 
				if (this.hasHistoryBar()) 
					this.oHistoryBar.getRootpanel().toXML(zContent);
			}
		}

	}
	protected void containerToHelpXML(JXMLContent zContent) throws Exception {
  }
	public JWebViewNavigationBar getNavBar() throws Exception {
		if (oNavBar!=null) return oNavBar;
		if (BizUsuario.retrieveSkin()==null) return null;
		return oNavBar=	skin().getNavBar(this);
	}

	JWebViewHistoryBar getHistoryBar() throws Exception {
		if (oHistoryBar!=null) return oHistoryBar;
		if (BizUsuario.retrieveSkin()==null) return null;
		return oHistoryBar=skin().getHistoryBar(this);
	}
	public void addUserMessage(String message) throws Exception {
		if (BizUsuario.retrieveSkin()==null) return;
		getNavBar().addChild("navigation_bar_link"+String.valueOf(this.oNavBar.getChildrenCount()),skin().getUserMessage(this,message));
	}	
	public void addNavigationAction(JWebAction zAction, JWebIcon zIcon) throws Exception {
		if (zAction==null) return;
		if (BizUsuario.retrieveSkin()==null) return;
		getNavBar().addChild("navigation_bar_link"+String.valueOf(this.oNavBar.getChildrenCount()), skin().getNavigationAction(this,zAction,zIcon));
	}	
	
	
	JWebViewNavigationBar getMenu() throws Exception {
		if (oMenu!=null) return oMenu;
		JWebViewNavigationBar n= skin().getMenuBar(this);
		return oMenu=n;
	}

	public boolean hasHistoryBar() {
		return this.oHistoryBar!=null;
	}
	public boolean hasNavigationActions() {
		return this.oNavBar!=null&&this.oNavBar.getChildrenCount()>0;
	}

	public boolean hasMenu() {
		return this.oMenu!=null&&this.oMenu.getChildrenCount()>0;
	}

	@Override
	public String getComponentType() {
		return "view";
	}

	@Override
	public void validate() throws Exception {
		super.validate();
		JMap<Class<?>, JList<String>> oTempMap=JCollectionFactory.createMap();
		this.registerAbsoluteNameComponentsIn(this, JCollectionFactory.<Class, JList<String>> createMap());
		JIterator<JList<String>> oListsIt=oTempMap.getValueIterator();
		while (oListsIt.hasMoreElements()) {
			(oListsIt.nextElement()).removeAllElements();
		}
		oTempMap.removeAllElements();
	}
	protected boolean hasMoreOneHistory() throws Exception {
		JWebHistoryManager hisMan = this.getUICoordinator().getSession().getHistoryManager();
		return hisMan.sizeHistory()>1;
	}
	private void registerAbsoluteNameComponentsIn(JWebViewComposite zComposite, JMap<Class, JList<String>> zTempMap) throws Exception {
		JIterator<JWebViewComponent> oChildrenIt=zComposite.getChildren();
		JWebViewComponent oComp;
		while (oChildrenIt.hasMoreElements()) {
			oComp=oChildrenIt.nextElement();
			if (oComp.isComposite()) {
				if (oComp.isAbsolutelyNamed()) {
					this.registerAbsoluteNameComponent((JAbsolutelyNamedWebViewComponent) oComp, zTempMap);
				}
				this.registerAbsoluteNameComponentsIn((JWebViewComposite) oComp, zTempMap);
			}
		}
	}

	private void registerAbsoluteNameComponent(JAbsolutelyNamedWebViewComponent zComponent, JMap<Class, JList<String>> zTempMap) {
		String sCompName=zComponent.getAbsoluteName();
		Class<?> oAbsoluteClass=zComponent.getAbsoluteClass();
		if (sCompName==null||(sCompName=sCompName.trim()).length()<1) {
			throw new RuntimeException("Absolutely named component without name");
		}
		JList<String> oTempList=zTempMap.getElement(oAbsoluteClass);
		sCompName=sCompName.toUpperCase();
		if (oTempList==null) {
			oTempList=JCollectionFactory.createList();
			zTempMap.addElement(oAbsoluteClass, oTempList);
		} else if (oTempList.containsElement(sCompName)) {
			throw new RuntimeException("Duplicate component name (no case sensitive): "+sCompName);
		}
		oTempList.addElement(sCompName);
	}

	@Override
	public String getName() {
		return "view"; // overriden to use the id in generation
	}

	public boolean hasTitle() {
		return this.sTitle!=null;
	}

	public String getTitle() {
		return this.sTitle;
	}

	public void setTitle(String string) {
		this.sTitle=string;
	}

	public JWebIcon getIcon() {
		return this.oIcon;
	}

	public void setIcon(JWebIcon icon) {
		this.oIcon=icon;
	}

  public JWebIcon getMaximizeIcon() {
		return null;
	}
	public JWebUICoordinator getUICoordinator() {
		return this.oCoordinator;
	}

	public boolean hasUICoordinator() {
		return this.oCoordinator!=null;
	}

	public void setUICoordinator(JWebUICoordinator coordinator) {
		this.oCoordinator=coordinator;
	}

	void setUpFrom(JWebRequest zRequest) throws Exception {
		this.init(zRequest);
		this.ensureIsBuilt();
		this.setUp(zRequest);
	}
	
	protected void init(JWebRequest zRequest) throws Exception {
	}


	//
	// METHODS TO OVERRIDE IN SUBCLASSES
	//
	public boolean isAutoRefresh() {
		return false;
	}

	public Dimension getMinimumSize() throws Exception {
		return null;
	}

	public Dimension getMaximumSize() throws Exception {
		return null;
	}

	@Override
	protected void build() throws Exception {
		throw new RuntimeException("Method #build() must be overriden in views");
	}

	protected void setUp(JWebRequest zRequest) throws Exception {
		// do nothing by default
	}

	//
	// METHODS TO OVERRIDE IN SUBCLASSES
	//
	public JWebViewNavigationBar generateMenu() throws Exception {
//		if (BizUsuario.getUsr()!=null && !BizUsuario.getUsr().hasCustomMenu()) {
//			this.addUserMessage(" ");
//			return null;
//		}
		return this.generateMenu(new GuiModuloPss().getUserMenu(0));
	}

	private JWebViewNavigationBar generateMenu(BizActions actions) throws Exception {
		skin().buildHeaderMenu(this,getMenu());
		skin().generateMenu(this,getMenu(),actions);
		return getMenu();
	}
	
	public JWebNavigationBarGroup addNavigationGroup(JWebNavigationBarGroup group, JWebIcon zIcon,String name, long level) throws Exception {
		return getMenu().addNavigationGroup(group, zIcon, name, level);
	}
	public void addNavigationAction(JWebNavigationBarGroup group, String zAction, JWebIcon zIcon, String label, boolean newwindow) throws Exception {
		addNavigationAction(group, zAction,zIcon,label,newwindow,null);
	}
	public void addNavigationAction(JWebNavigationBarGroup group, String zAction, JWebIcon zIcon, String label, boolean newwindow, String confirmation) throws Exception {
		getMenu().addNavigationAction(group, zAction, zIcon, label, newwindow, confirmation);
	}

	public void addNavigationAction(JWebNavigationBarGroup group, JWebAction zAction, JWebIcon zIcon, String label, boolean newwindow) throws Exception {
		getMenu().addNavigationAction(group, zAction, zIcon, label, newwindow);
	}
	
	public void addNavigationAction(JWebNavigationBarGroup group, JWebAction zAction, JWebIcon zIcon, String label, boolean newwindow,String confirmation) throws Exception {
		getMenu().addNavigationAction(group, zAction, zIcon, label, newwindow,confirmation);
	}
	

	protected void generateJumbotron() throws Exception {
		JWebViewInternalComposite jumbo = skin().getJumbotron(this);
		if (jumbo!=null) this.add("jumbotron",jumbo);
	}	
	protected void generateNavigationBarActions() throws Exception {
		if (BizUsuario.getUsr()==null)
			return;
		if (BizUsuario.getUsr()!=null && !BizUsuario.getUsr().hasNavigationBar()) {
			this.addUserMessage(" ");
			return;
		}
	
		this.skin().fillNavBar(this);
		JWebViewNavigationBar menu = generateMenu();
		getNavBar().addNavigationMenu(menu);
	}
	public void addNavigationTitle(String label,JWebIcon icon,String styleImage) throws Exception {
		getNavBar().addNavigationTitle(label,icon,styleImage);
	}
	public void addNavigationTitle(JWebViewComposite panel,JWebIcon icon,String styleImage) throws Exception {
		getNavBar().addNavigationTitle(panel,icon,styleImage);
	}
	public void addLogo(JWebIcon logo,String styleImage) throws Exception {
		if(BizUsuario.getUsr()==null) return;
			if (logo ==null) return;
		getNavBar().addLogo(logo,styleImage);
	}
	public void addLogoMenu(JWebIcon logo,String styleImage) throws Exception {
		if(BizUsuario.getUsr()==null) return;
			if (logo ==null) return;
		getMenu().addLogo(logo,styleImage);
	}
	public void addGoBack(JWebNavigationBarGroup parent,JWebIcon icon,String label) throws Exception {
		getNavBar().addNavigationAction(parent,JWebActionFactory.createGoBackToQuery(), icon, label,false);
	}
	public void addGoHelp(JWebNavigationBarGroup parent,JWebIcon icon,String label) throws Exception {
		getNavBar().addNavigationAction(parent,JWebActionFactory.createHelp(), icon, label,false);
	}
	public void addSecurity(JWebNavigationBarGroup parent,JWebIcon icon,String label) throws Exception {
		if (!(this.hasUICoordinator()&&this.getUICoordinator().hasSession())) return;
		getNavBar().addNavigationAction(parent,JWebActionFactory.createSecurity(), icon, label,false);
	}
	public void addGoHome(JWebNavigationBarGroup parent,JWebIcon icon,String label) throws Exception {
		if (!(this.hasUICoordinator()&&this.getUICoordinator().hasSession())) return;
		getNavBar().addNavigationAction(parent,JWebActionFactory.createGoHome(this.getUICoordinator().getSession()), icon, label,false);
	}
	
	public void addNewSession(JWebNavigationBarGroup parent,JWebIcon icon,String label) throws Exception {
		if (BizUsuario.hasMulti() && !this.getUICoordinator().getSession().isSubsession())
			getNavBar().addNavigationAction(parent,JWebActionFactory.createNewSession(this.getUICoordinator().getSession()), icon, label,false);
	}


	public JWebNavigationBarGroup addMailFolder(JWebNavigationBarGroup parent, JWebIcon icon, String label, int level) throws Exception {
		if (!BizUsuario.getUsr().getObjBusiness().hasMailIcon()) return null;
		if (!BizUsuario.getUsr().findHasMail())	return null;
		JWebNavigationBarGroup g1 = getNavBar().addNavigationGroup(null, icon, label, level);
		
//		if (JMail.getMailServer().hasUrgentMail())
//			getNavBar().addNavigationActionMensaje(g1, JMail.getMailServer().getUrgentTitle(), JDateTools.DateToString(JMail.getMailServer().getUrgentDate()), JMail.getMailServer().getUrgentMessage(), null, null, 0);
//		JIterator<BizMail> it = JMail.getMailServer().getIteratorMail();
//		while (it.hasMoreElements()) {
//			BizMail mail = it.nextElement();
//			if (mail.getUrgentTitle().equals(JMail.getMailServer().getUrgentTitle()) && mail.getUrgentDate().equals(JMail.getMailServer().getUrgentDate()))
//				continue;
//			getNavBar().addNavigationActionMensaje(g1, mail.getObjMessage().getTitle(), JDateTools.DateToString(mail.getObjMessage().getDateCreation()), mail.getObjMessage().getMessageStrip(), null, null, 0);
//		}

		getNavBar().addNavigationActionMensaje(g1, "", "", "Ver Correo", JWebActionFactory.createMail(), null, 1);
		return g1;
	}
	public JWebNavigationBarGroup addAlertFolder(JWebNavigationBarGroup parent, JWebIcon icon, String label, int level) throws Exception {
		if (BizUsuario.getUsr()==null) return null;
		if (!BizUsuario.getUsr().getObjBusiness().hasMailIcon()) return null;
		JWebNavigationBarGroup g2 = getNavBar().addNavigationGroup(null,  icon,label,level);
//		if (JMail.getMailServer().hasMail()) {
//			getNavBar().addNavigationActionMensaje(g2,"","","Tiene "+JMail.getMailServer().getCantMail()+" mensajes sin leer", null, null,0);
//		}
//		if (JAgenda.getAlarmaServer().hasAlarma()) {
//			getNavBar().addNavigationActionMensaje(g2,"","",JAgenda.getAlarmaServer().getMessage(), null, null,0);
//		}
//		
//		if (BizUsuario.getUsr()!=null) {
//			String msg = BizUsuario.getUsr().getObjCompany().getObjBusiness().getUserMensajes();
//			if (msg!=null)
//				getNavBar().addNavigationActionMensaje(g2,"","",msg, null, null,0);
//				
//		}
		return g2;
	}
	
	
	
	public void addSeparator(JWebNavigationBarGroup parent) throws Exception {
	 	getNavBar().addNavigationSeparator(parent);
	}
	public JWebNavigationBarGroup addUserFolder(JWebNavigationBarGroup parent, JWebIcon icon, String label, int level) throws Exception {
		JWebNavigationBarGroup g3 = getNavBar().addNavigationGroup(null, icon, label,level);
	 	getNavBar().addNavigationActionMensaje(g3,BizUsuario.getUsr().GetUsuario(),BizUsuario.getUsr().GetDescrip(),BizPssConfig.getPssConfig().getVersion(), null, null,0);
	 	return g3;
	}
	

	public void addUserPreferences(JWebNavigationBarGroup parent,JWebIcon icon,String label) throws Exception {
		if (!BizUsuario.getCurrentUsuarioCanChangePreference()) return;
		getNavBar().addNavigationAction(parent,JWebActionFactory.createPreferences(), icon,label,false);
	}
	public void addChangePassword(JWebNavigationBarGroup parent,JWebIcon icon,String label) throws Exception {
		if (!BizUsuario.hasChangePwd()) return;
			getNavBar().addNavigationAction(parent,JWebActionFactory.createPasswordChange(), icon,label,false);
	}
	public void addCloseSession(JWebNavigationBarGroup parent,JWebIcon icon,String label) throws Exception {
		if (this.getUICoordinator().getSession().isSubsession())
			getNavBar().addNavigationAction(parent,JWebActionFactory.createDoLogoutClosed(), icon,label==null?BizUsuario.getMessage("Cerrar ventana", null):label ,false);
		else
			getNavBar().addNavigationAction(parent,JWebActionFactory.createDoLogout(), icon,label==null?BizUsuario.getMessage("Cerrar sesión", null ):label,false);
	}		
	public void addInfoSystem(JWebNavigationBarGroup parent,JWebIcon icon,String label) throws Exception {
			getNavBar().addNavigationAction(parent,JWebActionFactory.createInfo(), icon,label==null?BizUsuario.getMessage("Sistema info", null ):label,false);
	}		
	public void addRefresh(JWebNavigationBarGroup parent,JWebIcon icon,String label) throws Exception {
		getNavBar().addNavigationAction(parent,JWebActionFactory.createRefresh(), icon, label,false);
	}
	
	public void addActiveHelp(JWebNavigationBarGroup parent,JWebIcon icon,String label) throws Exception {
		if (isHelpAvailable()) {
			//getNavBar().addNavigationToggle(g1,JWebActionFactory.createActiveHelp(),"active_help" ,JAplicacion.GetApp().isActiveHelp(),label,label,null);		

			JWebNavigationBarGroup g1 = getNavBar().addNavigationGroup(null, icon, label, 0);
			//String sAyuda = JAplicacion.GetApp().isActiveHelp()?"Activar Ayuda": "Desactivar Ayuda";
			getNavBar().addNavigationActionMensaje(g1, "", "", "Activar/Desactivar", JWebActionFactory.createActiveHelp(), null, 1);
			//getNavBar().addNavigationToggle(g1,JWebActionFactory.createActiveHelp(),"active_help" ,JAplicacion.GetApp().isActiveHelp(),"NO","SI",null);
			getNavBar().addNavigationActionMensaje(g1, "", "", "Ver Videos", JWebActionFactory.createViewHelpVideos(), null, 1);
		}
	}
	
	protected void generateHistoryBarActions() throws Exception {
		if (this.getHistoryBar()==null) return;
		if (!this.getUICoordinator().hasSession()) return;
		
		JWebHistoryManager hisMan = this.getUICoordinator().getSession().getHistoryManager();
		JList<JHistory> list = hisMan.getActionHistory();
		JIterator<JHistory> it = list.getIterator();
		while (it.hasMoreElements()) {
			JHistory hist = it.nextElement();
			if (hist.equals(hisMan.getLastHistory())) {
				getHistoryBar().addHistoryMessage(hist.getFirstProvider().getAction().getResult().GetTitle());
				if (BizUsuario.retrieveSkinGenerator().attachBackActionToToolbar("history_bar"))
					getHistoryBar().addBackAction(hist.getFirstAction().isWins()?hist.getFirstAction().getObjWinsOwner().confirmBack():null);
			} else {
				getHistoryBar().addHistoryAction(JWebActionFactory.createHistoryAction(hist), JWebIcon.getSkinIcon("images/go.back.gif"));
			}
		}

	}
	public String getScroll(JXMLContent zContent) throws Exception {
		JWebRequest oRequest = zContent.getGenerator().getRequest();
		try {
			if (oRequest.getSession() == null)
				return "";		
			if (oRequest.getSession().getHistoryManager() == null)
				return "";
			if (oRequest.getSession().getHistoryManager().getLastHistory() == null)
				return "";
			return oRequest.getSession().getHistoryManager().findLastScroll(null);
		} catch (Exception e) {
			return "";
		}
	}


}
