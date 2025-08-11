/*
 * Created on 24-sep-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.ui;

import java.awt.Dimension;

import pss.common.security.BizUsuario;
import pss.core.tools.JPair;
import pss.core.tools.JTools;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JList;
import pss.core.tools.collections.JOrderedMap;
import pss.core.win.JBaseWin;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.www.platform.actions.JWebAction;
import pss.www.platform.actions.JWebActionFactory;
import pss.www.platform.actions.JWebServerAction;
import pss.www.platform.applications.JHistory;
import pss.www.platform.content.generators.JXMLContent;

public class JWebWinActionBar extends JWebViewInternalComposite {

	private JOrderedMap<String, JPair<Boolean,JList<String>>> oActionIdToAllowedWins;
	private JOrderedMap<String, JPair<Boolean,JList<String>>> oActionIdToNotAllowedWins;
	private JList<String> oMultipleAllowedActions;
	protected JWebActionOwnerProvider oObjectProvider;
	private boolean forForm;
	private String sToolbarPos="top";
	private boolean isFalseInternalToolbar=true;

	private boolean bEmbedded;
	private String scroll="auto";
	private boolean bImportant;
	private boolean bPreview;
	private Boolean forceWithLabels=null;


	//	private String  sExtraInfo="";
	JWebViewInternalComposite oRootPanel;
	public JWebViewInternalComposite getRootPanel() {
		return oRootPanel;
	}

	public void setRootPanel(JWebViewInternalComposite oRootPanel) {
		this.oRootPanel = oRootPanel;
	}
	public Boolean getForceWithLabels() {
		return forceWithLabels;
	}

	public void setForceWithLabels(Boolean forceWithLabels) {
		this.forceWithLabels = forceWithLabels;
	}
	@Override
	public Dimension getDefaultSize() throws Exception {
		return new Dimension(1, 1);
	}
	public String getToolbarPos() throws Exception {
		return sToolbarPos;
	}
	public boolean isToolbarTop() {
		return this.sToolbarPos.equals(JBaseWin.TOOLBAR_TOP);
	}
	public boolean isToolbarLeft() {
		return this.sToolbarPos.equals(JBaseWin.TOOLBAR_LEFT);
	}
	public boolean isToolbarNone() {
		return this.sToolbarPos.equals(JBaseWin.TOOLBAR_NONE);
	}
	public boolean isToolbarIn() {
		return this.sToolbarPos.equals(JBaseWin.TOOLBAR_IN);
	}
	public boolean isEmbedded() {
		return this.bEmbedded;
	}
	public boolean isImportant() {
		return this.bImportant;
	}
	public void setScroll(String value) {
		this.scroll=value;
	}
	
	public boolean isPreview() {
		return bPreview;
	}
	public void setPreview(boolean bPreview) {
		this.bPreview = bPreview;
	}
	public boolean isForm() throws Exception {
		return false;
	}
	public boolean isWinlist() throws Exception {
		return false;
	}
	public boolean isFormLov() throws Exception {
		return false;
	}
	public boolean isFalseInternalToolbar() {
		return isFalseInternalToolbar;
	}

	public void setFalseInternalToolbar(boolean isFalseInternalToolbar) {
		this.isFalseInternalToolbar = isFalseInternalToolbar;
	}

	//
	// constructor
	//
	public JWebWinActionBar(JWebActionOwnerProvider zObjectProvider, boolean zForForm, String toolbar, boolean bEmbedded) {
		this.oObjectProvider = zObjectProvider;
		this.forForm = zForForm;
		this.sToolbarPos = toolbar;
		this.bEmbedded= bEmbedded;

	}

	public String getName() {
		try {
			if (this.oObjectProvider==null) return "";
			if (isToolbarIn() && isFalseInternalToolbar()) // rjl test
				return "actionbarinternal_"+this.oObjectProvider.getProviderName();
			return "actionbar_"+this.oObjectProvider.getProviderName();
		} catch (Exception e){
			return "";
		}
			
	}
	public JWebActionOwnerProvider getObjectProvider() {
		return oObjectProvider;
	}
	public boolean isModalParent(BizAction action) throws Exception { //RJL. mejorar, necesito saber si tiene que volver a un modal o a una no modal.
		JHistory actThis = 	JWebActionFactory.getCurrentRequest().getSession().getHistoryManager().getLastHistory();
		if (action.isImprimir()) return false;
		if (/*actThis.getMainSumbit().isExitOnOk() ||*/action.isDropAction()/* || (actThis.getMainSumbit().isOnlySubmit() && actThis.getMainSumbit().isExitOnOk())*/) {
//			return JWebActionFactory.getCurrentRequest().hasBackToModal();
			if (JWebActionFactory.getCurrentRequest().getSession().getHistoryManager().getLastLastHistory()==null)
				return false;
			return JWebActionFactory.getCurrentRequest().getSession().getHistoryManager().getLastLastHistory().getFirstAction().isModal();
		}
		return action.isModal() || actThis.getFirstAction().isModal();
	}
	public boolean isModal() throws Exception { //RJL. mejorar, necesito saber si tiene que volver a un modal o a una no modal.
		if (JWebActionFactory.getCurrentRequest().getSession().getHistoryManager().getLastHistory()==null)
			return false;
		return JWebActionFactory.getCurrentRequest().getSession().getHistoryManager().getLastHistory().isJSModal();
	}
	//
	// public API
  //
	public void addActionsFor(JBaseWin baseWin, String zBaseWinId, JWebAction parent, boolean append, boolean bForm, boolean inAlta, boolean isModal) throws Exception {
		addActionsFor(baseWin, zBaseWinId, parent, append, bForm, inAlta, isModal, null);
	}
	
	public void addActionsFor(JBaseWin baseWin, String zBaseWinId, JWebAction parent, boolean append, boolean bForm, boolean inAlta, boolean isModal, String objContext) throws Exception {
		JIterator<BizAction> iter = baseWin.getActionMap().getStaticIterator();
		boolean register = true;
		while (iter.hasMoreElements()) {
			BizAction action = iter.nextElement();
			if (isModal && !action.isImprimir()) action.setModal(isModal);
			if (JWebActionFactory.isBackOnPrint()) action.setBackOnPrint(true);
			register=true;
			if (getForm()!=null&&!getForm().isModoConsulta())
				action.setUploadData(true);
			if (!action.ifToolBar()) register=false;
			else if (baseWin.isInMobile() && !action.isInMobile()) register=false;
			else if (!baseWin.isInMobile() && action.isOnlyMobile()) register=false;
			else if (!bForm && action.isOnlyInForm()) register=false;
			else if (isForm() && action.isNotInForm()) register=false;
			else if (!action.isOkAction()) register=false;
			else if (!action.isOkSecurity()) register=false;
			else if (inAlta && !action.isInAlta()) register=false;
			else if (!action.isAccessToDetail() && !action.ifToolBar()) register=false;
			JWebServerAction webAction = this.createWebAction(zBaseWinId, action, false, baseWin, objContext);
			if (webAction==null) register=false;
			else {
				webAction.setActionParent(parent);
				webAction.setIsWin(baseWin.isWin());
				webAction.setIsForm(bForm);
			}
			if (register)
				this.registerActionFor(action, zBaseWinId, webAction, baseWin.isWin(), append);
			else
				this.registerNotActionFor(action, zBaseWinId, baseWin.isWin());
		}
//		if (baseWin.getNoActionMapToolbar()!=null) {
//			iter = baseWin.getNoActionMapToolbar().getStaticIterator();
//			while (iter.hasMoreElements()) {
//				BizAction action = iter.nextElement();
//				this.registerNotActionFor(action, zBaseWinId, baseWin.isWin());
//			}
//		}
	}

	public void addSubmitAction(BizAction zAction, String zBaseWinId, JWin win, boolean switcheable) throws Exception {
		addSubmitAction(zAction, zBaseWinId, win, switcheable,true);
	}

	public void addSubmitAction(BizAction zAction, String zBaseWinId, JWin win, boolean switcheable, boolean submitAfterBack) throws Exception {
		JWebServerAction webAction = this.createWebAction(null, zAction, true, win);
		webAction.setForceSubmit(true);
		webAction.setUploadata(true);
		webAction.setSubmitAfterBack(submitAfterBack);
		webAction.setSwitcheable(switcheable);
		this.registerActionFor(zAction, zBaseWinId, webAction, true, true);
	}

	public JWebServerAction addCancelAction(boolean isModal, boolean switcheable, String descr) throws Exception { // switchable funcion para emilio, que cambia el cancel por close si hubo cambios en los campos
		BizAction action = this.createActionCancel();
		if (descr!=null)
			action.setDescrip(descr);
		JWebServerAction webAction = this.createWebCancelAction();
		if (isModal)
			webAction.setAjaxContainer("view_area_and_title");
		webAction.setIsCancel(true);
		webAction.setCancelable(false);
		webAction.setSwitcheable(switcheable);
		this.registerActionFor(action, "-1", webAction, true, true);
		return webAction;
	}

	public JWebServerAction addBackAction(String id,String confirmBack, String label) throws Exception {
		BizAction action = this.createActionBack();
		if (label!=null)
			action.setDescrip(label);
		JWebServerAction webAction = this.createWebCancelAction();
		if (confirmBack!=null) {
			action.setConfirmMessageDescription(confirmBack);
			action.setConfirmMessage(true);
			webAction.setConfirmMessageDescription(confirmBack);
		}
		webAction.setIsCancel(true);
		webAction.setCancelable(false);
		this.registerActionFor(action, id, webAction, false, true);
		return webAction;
	}

	public BizAction createActionCancel() throws Exception {
		return BizUsuario.retrieveSkinGenerator().createActionCancel(this.oObjectProvider);
	}

	public BizAction createActionBack() throws Exception {
		return BizUsuario.retrieveSkinGenerator().createActionBack(this.oObjectProvider);
	}

	//
	// superclass overriding
	//

	protected void containerToXML(JXMLContent zContent) throws Exception {
	}	
	protected void containerToXMLActions(JXMLContent zContent) throws Exception {
		JIterator<String> oActionIdIt = this.getActionIdToAllowedWins().getKeyIterator();
		zContent.setAttribute("name", getName());
		zContent.setAttribute("provider", oObjectProvider.getProviderName());
		String sActionId;
		JPair<Boolean,JList<String>> pair;
		JList<String> oAllowedWinIds;
		while (oActionIdIt.hasMoreElements()) {
			sActionId = oActionIdIt.nextElement();
			pair = this.getActionIdToAllowedWins().getElement(sActionId);
			oAllowedWinIds = pair.secondObject;
			zContent.startNode("action");
			zContent.setAttribute("id", sActionId);
			zContent.setAttribute("win", pair.fisrtObject);
			zContent.setAttribute("multiple", getMultipleAllowedActions().containsElement(sActionId));
			JIterator<String> oAllowedWinsIt = oAllowedWinIds.getIterator();
			while (oAllowedWinsIt.hasMoreElements()) {
				zContent.startNode("allowed_win");
				zContent.setAttribute("id", oAllowedWinsIt.nextElement());
				zContent.endNode("allowed_win");
			}
			zContent.endNode("action");
		}
	}

	@Override
	public String getComponentType() {
		if (isToolbarIn() && isFalseInternalToolbar())// rjl test
			return "win_action_bar_internal";
		return "win_action_bar";
	}
	
	public boolean hasScroll() throws Exception {
		if (this.scroll.equals("none"))
			return false;
		return true;
	}

	public String getScroll() throws Exception {
		return this.scroll;
	}


	@Override
	public void destroy() {
		if (this.oActionIdToAllowedWins != null) {
			this.oActionIdToAllowedWins.removeAllElements();
			this.oActionIdToAllowedWins = null;
		}
		if (this.oActionIdToNotAllowedWins != null) {
			this.oActionIdToNotAllowedWins.removeAllElements();
			this.oActionIdToNotAllowedWins = null;
		}
		this.oObjectProvider = null;
		super.destroy();
	}

	protected JOrderedMap<String,  JPair<Boolean,JList<String>>> getActionIdToAllowedWins() throws Exception {
		if (this.oActionIdToAllowedWins == null) this.oActionIdToAllowedWins = JCollectionFactory.createOrderedMap();
		return this.oActionIdToAllowedWins;
	}

	protected JOrderedMap<String, JPair<Boolean,JList<String>>> getActionIdToNotAllowedWins() throws Exception {
		if (this.oActionIdToNotAllowedWins == null) this.oActionIdToNotAllowedWins = JCollectionFactory.createOrderedMap();
		return this.oActionIdToNotAllowedWins;
	}
	protected JList<String> getMultipleAllowedActions() throws Exception {
		if (this.oMultipleAllowedActions == null) this.oMultipleAllowedActions = JCollectionFactory.createList();
		return this.oMultipleAllowedActions;
	}


	protected boolean hasInMap(String actionId) throws Exception {
		return this.getActionIdToAllowedWins().containsKey(actionId);
	}

	protected JList<String> getListMap(String actionId) throws Exception {
		return (JList<String>)this.getActionIdToAllowedWins().getElement(actionId).secondObject;
	}

	protected boolean hasInNoMap(String actionId) throws Exception {
		return this.getActionIdToNotAllowedWins().containsKey(actionId);
	}
	protected JList<String> getListNoMap(String actionId) throws Exception {
		return  (JList<String>)this.getActionIdToNotAllowedWins().getElement(actionId).secondObject;
	}

	//
	// internal implementation
	//
	public void registerActionFor(BizAction zAction, String zBaseWinId, JWebAction webAction, boolean isWin, boolean append) throws Exception {
		if (webAction == null) return;
		String sActionId = JTools.replace(this.getName()+"_"+zAction.getIdAction(), ".", "_");

		if (this.hasInMap(sActionId)) {
			this.getListMap(sActionId).addElement(zBaseWinId);
			return;
		}
		if (!append) return;

		webAction.setIsWinList(this.isWinlist());
		webAction.setIsForm(this.isForm());
		webAction.setIsFormLov(this.isFormLov());
		
		JList<String> oAllowedWins = JCollectionFactory.createList();
		oAllowedWins.addElement(zBaseWinId);
		this.getActionIdToAllowedWins().addElement(sActionId, new JPair(isWin, oAllowedWins));
		
		if (this.skin().actionsInMore(this,zAction, webAction)) {
			this.createBotonLinkMore(zAction, webAction);
		}
		else {
			this.createBotonLink(zAction, webAction);
		}
	}

	
	public void registerNotActionFor(BizAction zAction, String zBaseWinId, boolean isWin) throws Exception {
		String sActionId = JTools.replace(this.getName()+"_"+zAction.getIdAction(), ".", "_");

		if (this.hasInNoMap(sActionId)) {
			this.getListNoMap(sActionId).addElement(zBaseWinId);
			return;
		}
		
		JList<String> oNotAllowedWins = JCollectionFactory.createList();
		oNotAllowedWins.addElement(zBaseWinId);
		this.getActionIdToNotAllowedWins().addElement(sActionId, new JPair(isWin,oNotAllowedWins));
	}
	
	JWebViewComposite moreButton;	
	protected void createBotonLinkMore(BizAction zAction, JWebAction webAction) throws Exception {
		if (this.getParent()==null) return;
		if (moreButton==null) {
			moreButton = BizUsuario.retrieveSkinGenerator().createBotonMore(this,isWinlist(),isForm(),isFormLov());
		}
		if (moreButton==null) {
			return;
		}		
		String sActionId = zAction.getIdAction();
		sActionId=JTools.replace(this.getName()+"_"+sActionId, ".", "_");
		webAction.setIsWinList(this.isWinlist());
		webAction.setIsForm(this.isForm());
		webAction.setIsFormLov(this.isFormLov());
		webAction.setNuevaVentana(zAction.isNuevaVentana());

		BizUsuario.retrieveSkinGenerator().createBotonInMore(this, moreButton, sActionId, zAction, webAction);

		if (zAction.isMulti()) 
			getMultipleAllowedActions().addElement(sActionId);

	}
	
	protected void createBotonLink(BizAction zAction, JWebAction webAction) throws Exception {
		String sActionId = zAction.getIdAction();
		if (zAction.isBreak()) {
			this.getRootPanel().addChild(new JWebFlowBreak());
			return;
		}

		webAction.setIsWinList(this.isWinlist());
		webAction.setIsForm(this.isForm());
		webAction.setIsFormLov(this.isFormLov());
		webAction.setNuevaVentana(zAction.isNuevaVentana());
		
		sActionId=JTools.replace(this.getName()+"_"+sActionId, ".", "_");
		BizUsuario.retrieveSkinGenerator().createBoton(this,sActionId,zAction,webAction);

		if (zAction.isMulti()) getMultipleAllowedActions().addElement(sActionId);

	}


	public boolean isUpperCase() throws Exception {
		return BizUsuario.retrieveSkinGenerator().isActionUpperCase(this.getToolbarPos(), this.isEmbedded());
	}

	public boolean isWithIcons() throws Exception {
		return BizUsuario.retrieveSkinGenerator().isActionWithIcons(this.getToolbarPos(), this.isEmbedded());
	}

	public boolean isWithLabels() throws Exception {
		if (forceWithLabels!=null) return forceWithLabels;
		return BizUsuario.retrieveSkinGenerator().isActionWithLabels(this.getToolbarPos(), this.isEmbedded());
	}

	// protected JWebAction createWinsAction(String zWinId, BizAction zAction)
	// throws Exception {
	// return JWebActionFactory.createWebAction(zAction, this.oObjectProvider,
	// false, zWinId);
	// }

	// protected JWebAction createWinAction(BizAction zAction, boolean bSubmit)
	// throws Exception {
	// if (zAction.getId()==JWin.ACTION_DELETE&&!this.forForm) return
	// createWinListDeleteAction(zAction);
	// return JWebActionFactory.createWinAction(zAction, this.oObjectProvider,
	// bSubmit);
	// }
	protected JWebServerAction createWebAction(String zWinId, BizAction action, boolean bSubmit, JBaseWin bwin) throws Exception {
		return createWebAction(zWinId, action, bSubmit, bwin,null);
	}
	protected JWebServerAction createWebAction(String zWinId, BizAction action, boolean bSubmit, JBaseWin bwin, String objContext) throws Exception {
//		if (bwin instanceof JWin) {
//			if (zAction.getId() == JWin.ACTION_DELETE && !this.forForm) return createWinListDeleteAction(zAction);
//			if (zAction.getId() == JWin.ACTION_QUERY && this.forForm) return null;
//		} 
		if (!bwin.checkActionComponentType(action, this.forForm)) return null;
		return JWebActionFactory.createViewAreaAndTitleAction(action, this.oObjectProvider, bSubmit, zWinId, null, isModalParent(action),objContext);
	}

	
	// go back action
	public JWebServerAction createWebCancelAction() throws Exception {
		return JWebActionFactory.createWinGoBack();
	}

	// public JWebAction createWebRefreshAction() throws Exception {
	// return JWebActionFactory.createWinListRefresh(this.oObjectProvider);
	// }

	public JWebServerAction createWinSelectAction() throws Exception {
		return JWebActionFactory.createWinSelect(this.oObjectProvider);
	}

//	protected JWebAction createWinListDeleteAction(BizAction zAction) throws Exception {
//		return JWebActionFactory.createWinListDelete(zAction, this.oObjectProvider);
//	}
//	private JWebAction createWinListEditAction(BizAction zAction) throws Exception {
//		return JWebActionFactory.createWinListEdit(zAction, this.oObjectProvider);
//	}
	/*
	 * private JWebAction createWinListReloadAction(int zWinId,BizAction zAction)
	 * throws Exception { return
	 * JWebActionFactory.createWinListReload(zWinId,zAction,
	 * this.oObjectProvider); }
	 */
	

}
