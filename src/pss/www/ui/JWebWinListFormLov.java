/*
 * Created on 27-jun-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.ui;

import pss.common.security.BizUsuario;
import pss.core.win.JBaseWin;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.winUI.icons.GuiIcon;
import pss.www.platform.actions.JWebAction;
import pss.www.platform.actions.JWebActionFactory;
import pss.www.platform.actions.JWebServerAction;
import pss.www.platform.content.generators.JXMLContent;

public class JWebWinListFormLov extends JWebWinListResponsive {

	public JWebWinListFormLov(BizAction action) throws Exception {
		super(action, false, true);
		this.setBackBotton(false);
		this.setLineSelect(true);
		this.setToolbar(JBaseWin.TOOLBAR_TOP);
		
	}
	public JWebViewInternalComposite createActionBar() throws Exception {
		this.oActionBar=new JWebWinActionBarFormLov(this, false, JBaseWin.TOOLBAR_TOP, false);
		this.oActionBar.setRootPanel(BizUsuario.retrieveSkinGenerator().createActionBarFormLov(this, false));
		return oActionBar.getRootPanel();
	}

	public void regiterObjects() throws Exception {
		// se agrega el id de Objecto de la accion padre
		
		this.getSourceAction().getObjOwner().setCanConvertToURL(false);
		super.regiterObjects();
		
	}

	private BizAction getToolbarSelectAction() throws Exception {
		BizAction oToolbarRefreshAction=new BizAction();
		oToolbarRefreshAction.pDescr.setValue("Seleccionar");
		oToolbarRefreshAction.pNroIcono.setValue(GuiIcon.APLICAR_ICON);
		oToolbarRefreshAction.SetIdAction("select");
		oToolbarRefreshAction.SetId(JWin.ACTION_DROP);
		return oToolbarRefreshAction;
	}

	protected void addProviderHistory(JXMLContent zContent) throws Exception {
		//zContent.getGenerator().getSession().getHistoryManager().addHistoryProvider(this.sourceAction);
	}	
	@Override
	protected JWebAction getActionDefault(JWin zWin) throws Exception {
		return getActionBar().createWinSelectAction();
	}

	@Override
	protected void rowToXMLActionBar(JXMLContent zContent, JWin zWin, String id) throws Exception {
		zWin.getRecord().keysToFilters();
		zContent.setAttribute("zobject", id);
		BizAction action = this.getToolbarSelectAction();
//		if (!zWin.OkAction(action)) return id;
		JWebServerAction webAction = getActionBar().createWinSelectAction();
		webAction.setIsCancel(true);
		webAction.setIsWin(true);
		getActionBar().registerActionFor(action, null, webAction, true, true);
		zContent.setAttribute("key", zWin.GetValorItemClave());//Record().getProp(zWin.getKeyField()).toString());
		zContent.setAttribute("descr", zWin.getDescripFieldValue());
	}

	@Override
	public void attachWinsActions(JBaseWin zWin, String zBaseWinId, boolean append) throws Exception {
	}
	
	public JWebServerAction attachRefreshAction() throws Exception {
		JWebServerAction webAction = (JWebServerAction)super.attachRefreshAction();
		webAction.setURI("do-FormLovRefreshAction");
		return webAction;
	}
	
//	protected JWebAction attchGoBack(int zFromRow, String column,String dirOrden) throws Exception {
//		JWebServerAction webAction = (JWebServerAction)super.attchGoBack(zFromRow,column,dirOrden);
//		webAction.setURI("do-FormLovRefreshAction");
//		return webAction;
//	}
	
	protected JWebServerAction createDoFilterAction(BizAction a, JWebFilterPanel filterPanel) throws Exception {
		JWebServerAction webAction = (JWebServerAction)JWebActionFactory.createWinListRefreshAction(a, filterPanel, null);
		webAction.setURI("do-FormLovRefreshAction");
		return webAction;
	}


//	protected JWebAction attchGoNext(int zToRow, String column,String dirOrden) throws Exception {
//		JWebServerAction webAction = (JWebServerAction)super.attchGoNext(zToRow,column,dirOrden);
//		webAction.setURI("do-FormLovRefreshAction");
//		return webAction;
//	}

	protected void attachDoFilterButton(JWebFilterPanel filterPanel) throws Exception {
		this.filterBotton=null;
		if (!this.sourceAction.hasFilterAction()) return;
		if (isReadOnly()) return;

		BizAction a=new BizAction();
		a.setDescrip("Buscar");
		a.SetIdAction(this.sourceAction.getIdAction());

		JWebServerAction webAction = JWebActionFactory.createFormLovRefreshAction(a, filterPanel, null);
		webAction.setAjaxContainer(this.getCompletePanelName());


		JWebLink link=new JWebLink();		
		link.setWebAction(webAction);
		link.setCancel(false);
		link.setSkinStereotype("win_list_dofilter");
		link.setLabel("Buscar");
		link.setSubmit(true);
		link.setIcon(JWebIcon.getPssIcon(112));
		filterPanel.addChild("filtrar", link);
		this.filterBotton=link;
		((JWebServerAction) this.filterBotton.getWebAction()).setObjectOwnerID(sourceObjectId);
	}
//	@Override
//	public JWebAction createRefreshAction() throws Exception {
//		return getActionBar().createFormLovRefreshAction();
//	}

}
