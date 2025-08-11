package pss;

import pss.common.customMenu.GuiMenu;
import pss.common.security.BizUsuario;
import pss.core.tools.JExcepcion;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;
import pss.core.win.JBaseWin;
import pss.core.win.actions.BizAction;
import pss.core.win.actions.BizActions;
import pss.core.win.modules.GuiModulo;
import pss.core.win.submits.JAct;
import pss.core.win.submits.JActQuery;

//

public class GuiModuloPss extends GuiModulo {

//	private static GuiModuloPss cached=null;

	public GuiModuloPss() throws Exception {
		SetModuleName("Pss");
	}

//	public static synchronized GuiModuloPss getModuloPss() throws Exception {
//		if (cached==null) {
//			GuiModuloPss modulo=new GuiModuloPss();
//			BizAction rootAction=modulo.createDynamicAction();
//			modulo.clearActions();
//			modulo.loadDynamicActions(rootAction);
//			cached=modulo;
//		}
//		return cached;
//	}

	@Override
	public int GetNroIcono() throws Exception {
		return 903;
	}

	@Override
	public String GetTitle() throws Exception {
		return JPssVersion.getPssTitle();
	}

	@Override
	public String toString() {
		return JPssVersion.getPssTitle();
	}

	// public void createActionMap() throws Exception {
	// this.setActionMap(getActionsCached());
	// }

	/*
	 * public void appendSubActions(BizAction target, BizAction source) throws Exception { if (!source.ifTieneSubMenus()) return; JIterator iter = source.GetSubAcciones().getStaticIterator(); while ( iter.hasMoreElements() ) { BizAction oAction = (BizAction) iter.nextElement(); target.addSubAction(oAction); // this.appendSubActions(oAction); } }
	 */

	@Override
	public BizAction createDynamicAction() throws Exception {
		return addAction(1, "General", null, 1111, true, true, true, "Group");
	}

	@Override
	public void createActionMap() throws Exception {
//		if (!JAplicacion.GetApp().ifAppFrontEndWeb()) {
//			BizAction oAction=addAction(1, "General", null, 1111, true, true);
//			addAction(2, oAction, "Explorador", null, GuiIcon.ARBOL_ICON, true, true);
////			addAction(9, oAction, "Salir", null, GuiIcon.SALIR_ICON, true, true);
//		}
		this.loadDynamicActions(null);
	}

	@Override
	public JAct getSubmitFor(BizAction a) throws Exception {
		if (a.getId()==1) return new JActQuery(this);
//		if (a.getId()==2) return new JActSubmit(this, 2) {
//
//			@Override
//			public void submit() throws Exception {
//				JWinTree.explorer(GetThis());
//			}
//		};
		return null;
	}


//	public JBotonBar ArmarBotonBar(Object zOrigen, JBotonBar zBar) throws Exception {
//		JRecords<BizToolbarShortcut> oShortcuts=getShortcutsForUser();
//		oShortcuts.toStatic();
//		oShortcuts.closeRecord();
//		oShortcuts.firstRecord();
//		while (oShortcuts.nextRecord()) {
//			BizToolbarShortcut oShortcut=oShortcuts.getRecord();
//			BizAction oAction=getActionForShortcut(oShortcut);
//			if (oAction!=null&&BizUsuario.ifOperacionHabilitada(oAction.getIdAction())) {
//				zBar.AddItem(oAction);
//			}
//		}
//		return zBar;
//	}

	public BizActions getUserMenu(int size) throws Exception {
		if (!BizUsuario.getUsr().hasCustomMenu()) return this.getActionMap();

		GuiMenu menu=new GuiMenu();
		menu.setRecord(BizUsuario.getUsr().getObjCustomMenu());
		return menu.generateActionMenu(true, size).GetSubAcciones();
	}
	
	private JMap<String, BizAction> actionMap;
	public BizAction findModuleAction(String sAction) throws Exception {
		//HGK con esta funcion elimino la barrida por las carpetas iniciales. y cachea la accion, todo el cache es por usuario
		// habria que eliminar el loadDinamicAction en los modulos cuando el usuario tenga menuCustom
		if (this.actionMap==null) this.actionMap=JCollectionFactory.createMap();
		BizAction a = actionMap.getElement(sAction);
		if (a!=null) return a;
		int idx = sAction.indexOf("_");
		JBaseWin w = (JBaseWin) Class.forName(sAction.substring(0, idx)).newInstance();
		a=w.findActionByUniqueId(sAction); 
		if (a==null) JExcepcion.SendError("Acción Inexistente ("+sAction+")");
		this.actionMap.addElement(sAction, a);
		return a;
	}

	/*
	 * public void WebGenerarAccionesMenu( JSAXWrapper wrapper, boolean bRecursive, boolean bWeb ) throws Exception { BizMenuGroup oUserMenu = new BizMenuGroup(); oUserMenu.SetNoExcSelect(true); if (oUserMenu.Read(BizUsuario.getCurrentUser())) { String sMenuId = oUserMenu.getMenuId(); if (sMenuId==null || sMenuId.trim().length() < 1) { super.WebGenerarAccionesMenu(wrapper, bRecursive, bWeb); } else { JInteger oWebInternalIDHolder = new JInteger(0); this.generateMenu(wrapper, this.createCustomMenuList(sMenuId), oWebInternalIDHolder); } return; } oUserMenu.clearFilters(); if (oUserMenu.Read("ALL_USERS")) { JInteger oWebInternalIDHolder = new JInteger(0); this.generateMenu(wrapper, this.createCustomMenuList(oUserMenu.getMenuId()), oWebInternalIDHolder); return; } super.WebGenerarAccionesMenu(wrapper, bRecursive, bWeb); }
	 * 
	 * private void generateMenu(JSAXWrapper zWrapper, JList zMenuList, JInteger zWebInternalIDHolder) throws Exception { JIterator oMenuIt = zMenuList.getIterator(); Object[] oItemDef; while (oMenuIt.hasMoreElements()) { oItemDef = (Object[]) oMenuIt.nextElement(); if (oItemDef[1] instanceof BizAction) { zWebInternalIDHolder.setValue(zWebInternalIDHolder.getValue()+1); this.generateActionXML((BizMenuItem)oItemDef[0], (BizAction)oItemDef[1], zWrapper, zWebInternalIDHolder); } else { zWebInternalIDHolder.setValue(zWebInternalIDHolder.getValue()+1); this.generateSubMenuXML((BizMenuItem)oItemDef[0], (JList)oItemDef[1], zWrapper, zWebInternalIDHolder); } } }
	 * 
	 * private void generateActionXML(BizMenuItem zItem, BizAction zAction, JSAXWrapper zWrapper, JInteger zWebInternalIDHolder) throws Exception { AttributesImpl attr = new AttributesImpl();
	 *  // item node attr.addAttribute( "", "id", "id", "CDATA", zWebInternalIDHolder.toString() ); attr.addAttribute( "", "is_action", "is_action", "CDATA", "true" ); zWrapper.startElement("item", attr); // accion node zAction.toSAX(zWrapper);
	 * 
	 * zWrapper.endElement("item"); } private void generateSubMenuXML(BizMenuItem zItem, JList zItemList, JSAXWrapper zWrapper, JInteger zWebInternalIDHolder) throws Exception { AttributesImpl attr = new AttributesImpl(); // item node attr.addAttribute( "", "id", "id", "CDATA", zWebInternalIDHolder.toString() ); zWrapper.startElement("item", attr); // accion node attr.clear(); attr.addAttribute( "", "label", "label", "CDATA", JLanguage.translate(zItem.getDescription()) ); zWrapper.startElement( "accion", attr ); attr.addAttribute("", "file", "file", "CDATA", zItem.getIconFile()); zWrapper.startElement("icon", attr); zWrapper.endElement("icon"); zWrapper.endElement( "accion" );
	 * 
	 * this.generateMenu(zWrapper, zItemList, zWebInternalIDHolder);
	 * 
	 * zWrapper.endElement("item"); }
	 * 
	 */

//	private JRecords<BizToolbarShortcut> getShortcutsForUser() throws Exception {
//		JRecords<BizToolbarShortcut> oBDs=new JRecords<BizToolbarShortcut>(BizToolbarShortcut.class);
//		oBDs.addFilter("USUARIO", BizUsuario.getUsr().GetUsuario());
//		oBDs.addOrderBy("POSICION");
//		oBDs.readAll();
//		return oBDs;
//	}
//
//	//
//	// CUSTOM MENU BAR AND TOOLBAR GENERATION UTILITIES
//	//
//
//	private BizAction getActionForShortcut(BizToolbarShortcut zShortcut) throws Exception {
//		return getActionMap().findActionByUniqueId(zShortcut.getAction());
//	}
//
//	private BizAction getActionForMenuItem(BizMenuItem zMenuItem) throws Exception {
//		return getActionMap().findActionByUniqueId(zMenuItem.getActionId());
//	}

	/*
	 * private JList createCustomMenuList(String zMenuId) throws Exception { BizMenu oMenu = new BizMenu(); oMenu.Read(zMenuId); JDebugPrint.logDebug("Cargando meN° personalizado: " + oMenu.getDescription());
	 * 
	 * JList oCollectedItems = JCollectionFactory.createList(); this.collectMenuItems(zMenuId, oCollectedItems); return oCollectedItems; }
	 * 
	 * private void collectMenuItems(String zMenuId, JList zListToAddTo) throws Exception { JRecords oItems = new JRecords(BizMenuItem.class); oItems.addFilter("id_menu", zMenuId); oItems.readAll(); BizMenuItem oItem; while (oItems.nextRecord()) { oItem = (BizMenuItem) oItems.getRecord(); // if (oItem.isActionItem()) { this.addAction(oItem, zListToAddTo); // } else if (oItem.isChildMenuItem()) { this.addSubMenu(oItem, zListToAddTo); } } }
	 * 
	 * private void addSubMenu(BizMenuItem zSubMenuItem, JList zParentListToAddTo) throws Exception { JList oCollectedItems = JCollectionFactory.createList(); this.collectMenuItems(zSubMenuItem.getChildMenuId(), oCollectedItems); if (!oCollectedItems.isEmpty()) { zParentListToAddTo.addElement(new Object[] {zSubMenuItem, oCollectedItems}); } }
	 * 
	 * private void addAction(BizMenuItem zActionItem, JList zListToAddTo) throws Exception { BizAction oAction = this.getActionForMenuItem(zActionItem); if (oAction != null && BizUsuario.ifOperacionHabilitada(zActionItem.getActionId())) { zListToAddTo.addElement(new Object[] {zActionItem, oAction}); } }
	 * 
	 */
	
  public static String getCompanyDescription() throws Exception {
  	if ( BizUsuario.getUsr().getObjCompany() == null ) return "Empresa"; 
  	return "Empresa: "+ BizUsuario.getUsr().getObjCompany().getDescription();
	}
	
	
}
