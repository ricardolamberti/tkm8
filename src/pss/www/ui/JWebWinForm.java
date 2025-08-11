package pss.www.ui;

import java.awt.Dimension;

import pss.common.regions.multilanguage.JLanguage;
import pss.common.security.BizUsuario;
import pss.core.tools.PssLogger;
import pss.core.tools.collections.JIterator;
import pss.core.win.JBaseWin;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.controls.JFormControles;
import pss.core.winUI.forms.JBaseForm;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;
import pss.www.platform.actions.JWebAction;
import pss.www.platform.actions.JWebActionFactory;
import pss.www.platform.actions.JWebRequest;
import pss.www.platform.content.generators.JXMLContent;

public class JWebWinForm extends JWebForm implements JWebActionOwnerProvider, IWebWinForm {

	// private JFormControles oControls;
	private JWebViewComposite oRootPanel;
	protected JBaseForm oBaseForm;
	private JWebWinActionBar oActionBar;
	// private JWebSplitPane splitPane;

	// private Throwable throwable;
	private String iRegisteredObjectId = null;
	private BizAction sourceAction;
	
	private boolean embedded = false;
	private boolean autoRefresh = false;
	private int timerAutoRefresh;
	private String marcaAutoRefresh = null;
	private String sToobar = null;
	
	private boolean withHeader = true;

	public boolean isWithHeader() {
		return withHeader;
	}
	public void setWithHeader(boolean withHeader) {
		this.withHeader = withHeader;
	}
	public void setToolbar(String value) throws Exception {
		this.sToobar = value;
	}
  public boolean isUploadData() throws Exception {
  	// ojo, bandera desestabilizadora!!!
  	if (this.isModoConsulta()) return false;
  	return true;
  }

	public boolean isModal() throws Exception {
		return sourceAction.isModal();
	}

	public String getToolbar() throws Exception {
		if (this.getWin().hasToolbarForced())
			return this.getWin().getToolbarForced();
		if (this.sToobar != null)
			return this.sToobar;
		return JBaseWin.TOOLBAR_TOP;
	}

	public boolean isToolbarNone() throws Exception {
		return this.getToolbar().equals(JBaseWin.TOOLBAR_NONE);
	}

	public boolean isToolbarTop() throws Exception {
		return this.getToolbar().equals(JBaseWin.TOOLBAR_TOP);
	}

	public boolean isToolbarLeft() throws Exception {
		return this.getToolbar().equals(JBaseWin.TOOLBAR_LEFT);
	}

	// public JWebSplitPane getSplitPane() {
	// return splitPane;
	// }
	//
	// public void setSplitPane(JWebSplitPane splitPane) {
	// this.splitPane = splitPane;
	// }

	public JWebViewComposite getRootPanel() {
		return this.oRootPanel;
	}

	public void setRootPanel(JWebViewComposite panel) {
		this.oRootPanel = panel;
	}

	public void setSourceAction(BizAction value) {
		this.sourceAction = value;
	}

	public BizAction getSourceAction() {
		return this.sourceAction;
	}

	public boolean isAutoRefresh() {
		return autoRefresh;
	}

	public void setAutoRefresh(boolean autoRefresh) {
		this.autoRefresh = autoRefresh;
	}

	public int getTimerAutoRefresh() {
		return timerAutoRefresh;
	}

	public void setTimerAutoRefresh(int timerAutoRefresh) {
		this.timerAutoRefresh = timerAutoRefresh;
	}

	public String getMarcaAutoRefresh() {
		return marcaAutoRefresh;
	}

	public void setMarcaAutoRefresh(String marcaAutoRefresh) {
		this.marcaAutoRefresh = marcaAutoRefresh;
	}

	public JWebWinForm(JBaseForm zBaseForm) {
		this.oBaseForm = zBaseForm;
		// this.oControls = new JFormControles(zBaseForm);
	}

	public String getAbsoluteName() {
		return this.getName();
	}

	@SuppressWarnings("unchecked")
	public Class getAbsoluteClass() {
		return JWebWinForm.class;
	}

	public JWebViewEditComponent findComponent(String zChildName) throws Exception {
		return this.findComponent(this, zChildName);
	}

	private JWebViewEditComponent findComponent(JWebViewComposite zComposite, String zChildName) throws Exception {
		JWebViewEditComponent oResult = null;
		JWebViewComponent oComp = (JWebViewComponent) zComposite.getChild(zChildName);
		if (oComp != null && oComp.isEditComponent()) {
			oResult = (JWebViewEditComponent) oComp;
		} else {
			JIterator<JWebViewComponent> oChildIt = zComposite.getChildren();
			while (oChildIt.hasMoreElements() && oResult == null) {
				oComp = oChildIt.nextElement();
				if (oComp.isEditComponent() && oComp.getName().equals(zChildName)) {
					oResult = (JWebViewEditComponent) oComp;
				} else if (oComp.isComposite()) {
					oResult = this.findComponent((JWebViewComposite) oComp, zChildName);
				}
			}
		}
		return oResult;
	}


	@Override
	public void add(String zChildName, JWebViewEditComponent zComponent) throws Exception {
		this.addChild(zChildName, zComponent);
	}

	// @Override
	// public boolean isForm() {
	// return true;
	// }

	@Override
	public void destroy() {
		if (this.oBaseForm != null) {
			try {
				this.oBaseForm.cleanUp();
			} catch (Exception e) {
				PssLogger.logDebug(e);
			}
			this.oBaseForm = null;
		}
		super.destroy();
	}

	//
	// ACCESS API
	//
	public JWin getWin() {
		return this.oBaseForm.getBaseWin();
	}

	public JFormControles getControls() throws Exception {
		return this.oBaseForm.getControles();
	}

	//
	// INTERNAL IMPLEMENTATION
	//

	//
	// METHODS TO OVERRIDE IN SUBCLASSES
	//

	public boolean isEditForm() {
		return true;
	}

	//
	// supertypes implementation
	//

	public String getScroll(JXMLContent zContent) throws Exception {
		JWebRequest oRequest = zContent.getGenerator().getRequest();
		if (oRequest.getSession().getHistoryManager().getLastHistory() == null)
			return "";
		return oRequest.getSession().getHistoryManager().findLastScroll(this.sourceAction);
	}

	@Override
	protected void componentPreLayout(JXMLContent zContent) throws Exception {
		// represent the action bar actions for the Win object
		if (!this.hasActionBar()) {
			super.componentPreLayout(zContent);
			return;
		}
		if (!this.oBaseForm.isAlta())
			this.getWin().getRecord().keysToFilters();

		if (this.oBaseForm.isConsulta() || this.oBaseForm.isConsultaActiva()) {
			if (this.oBaseForm.canCancelAction())
				this.attachBackAction();
			this.oActionBar.addActionsFor(this.getWin(), this.getWin().getUniqueId(), null, true, true, false, false);
		} else {
			BizAction action = this.oBaseForm.findActionBySubmit();
			action.setModal(JWebActionFactory.getCurrentRequest().parentIsModal());
			if (this.oBaseForm.canApplyAction()) {
				if (this.oBaseForm.getNameApplyAction()!=null)
					action.setDescrip(this.oBaseForm.getNameApplyAction());
				this.oActionBar.addSubmitAction(action, this.getWin().getUniqueId(), this.getWin(),this.oBaseForm.isSwitcheable());
		}
			if (this.oBaseForm.canCancelAction())
				this.oActionBar.addCancelAction(JWebActionFactory.getCurrentRequest().parentIsModal(),this.oBaseForm.isSwitcheable(),this.oBaseForm.getNameCancelAction());
			this.oActionBar.addActionsFor(this.getWin(), this.getWin().getUniqueId(), null, true, true, true, this.isModal());
		}
		super.componentPreLayout(zContent);
	}

	@Override
	protected void containerToXML(JXMLContent zContent) throws Exception {
		zContent.getGenerator().getSession().getHistoryManager().addHistoryProvider(this.sourceAction);
		zContent.setAttribute("scroll", getScroll(zContent));
		zContent.setAttribute("has_action_bar", this.hasActionBar());
		zContent.setAttribute("obj_provider", getProviderName());
		if (isAutoRefresh()) {
			zContent.setAttribute("autorefresh", true);
			zContent.setAttribute("timer", getTimerAutoRefresh());
			zContent.setAttribute("marca", getMarcaAutoRefresh());
		}
		this.registerObject(zContent);
	}
	
	@Override
	protected void componentToXMLposLayout(JXMLContent zContent) throws Exception {
		super.componentToXMLposLayout(zContent);
		if (iRegisteredObjectId!=null)
			this.iRegisteredObjectId = zContent.addObject(this.iRegisteredObjectId, this.getWin());
	}
	
	public void registerObject(JXMLContent zContent) throws Exception {
		this.iRegisteredObjectId = this.getWin().getUniqueId();
		this.iRegisteredObjectId = zContent.addObject(this.iRegisteredObjectId, this.getWin());
//		this.iRegisteredObjectId = zContent.addObject(this.iRegisteredObjectId, this.getWin());
		zContent.setAttribute("zobject", this.iRegisteredObjectId);
		if (this.getBaseForm().hasSubForms()) {
			JIterator<JBaseForm> iter = this.getBaseForm().getSubForms().getIterator();
			while (iter.hasMoreElements()) {
				JWin win = iter.nextElement().getBaseWin();
				zContent.addObject(win.getUniqueId(), win);
			}
		}
	}

	@Override
	protected void containerToHelpXML(JXMLContent zContent) throws Exception {
		zContent.setAttribute("title", getTitle());
		zContent.setAttribute("copete", getHelp());
		actionsToHelpXML(JLanguage.translate("Acciones de ")+JLanguage.translate( getTitle()),getWin(),zContent);
		zContent.startNode("subtitle");
		zContent.setAttribute("title", JLanguage.translate("Componentes"));
		zContent.endNode("subtitle");
		super.containerToHelpXML(zContent);


	}
	

	private void attachBackAction() throws Exception {
		if (this.sourceAction == null)
			return;
		if (!this.sourceAction.hasBackAction())
			return;
		if (!this.getWin().hasBackAction())
			return;
		if (this.oBaseForm.isEmbedded())
			return;
		if (this.isEmbedded())
			return;
		if (this.isPreview())
			return;
		if (!BizUsuario.retrieveSkinGenerator().attachBackActionToToolbar("form"))
		 return;
		this.oActionBar.addBackAction(getWin().getUniqueId(), getWin().confirmBack(),oBaseForm.getNameCancelAction());
	}

	// super.containerToXML(zContent);
	public String forceTitle;
	public String getForceTitle() {
		return forceTitle;
	}
	public void setForceTitle(String forceTitle) {
		this.forceTitle = forceTitle;
	}
	public String getTitle() {
		if (getForceTitle()!=null) return getForceTitle();
		return this.oBaseForm.getTitle();
	}
	
	public String getTitleRight() {
		return this.oBaseForm.getTitleRight();
	}
	public boolean hasTitleRight() {
		return !(this.oBaseForm.getTitleRight()==null || this.oBaseForm.getTitleRight().equals(""));
	}

	public String getHelp() {
		return this.oBaseForm.getHelp();
	}
	public void setActionBar(JWebWinActionBarForm zActionBar) {
		this.oActionBar = zActionBar;
	}

	public JWebWinActionBar getActionBar() {
		return this.oActionBar;
	}

	public boolean hasActionBar() {
		return this.oActionBar != null;
	}

	//
	// internal implementation
	//

	@Override
	protected void build() throws Exception {
		// this.oBaseForm.doLayout();
//		this.takeAttributesForm(null, this.oBaseForm);
		this.oBaseForm.setSourceAction(this.sourceAction);
		this.oBaseForm.build();
		this.oBaseForm.generate();
		this.oRootPanel = this.skin().buildForm(this);
		this.createControls();
//		this.createRootPanel(comp);
	}
	

	@Override
	public String getName() {
		try {
			if (this.sourceAction == null)
				return "win_form";
			return "winform_" + this.sourceAction.getProviderName();
		} catch (Exception e) {
			return "win_form";
		}
	}


	public boolean hasToolbar() throws Exception {
		if (this.isToolbarNone())
			return false;
		if (this.isPreview() && this.isEmbedded())
			return false;
		return true;
	}

	public boolean isInternalToolbar() throws Exception {
		if (this.isEmbedded())
			return true;
		if (this.isPreview())
			return true;
		return false;
	}

	public JWebViewInternalComposite buildActionBar() throws Exception {
		JWebWinActionBar actionBar = new JWebWinActionBarForm(this, true, this.getToolbar(), this.isEmbedded());
		actionBar.setPreview(isPreview());
		this.oActionBar = actionBar;
		this.oActionBar.setRootPanel(BizUsuario.retrieveSkinGenerator().createActionBarForm(this));
		return this.oActionBar.getRootPanel();
	}

	protected void createControls() throws Exception {
		createControls(this.oBaseForm, -1, null);
	}

	protected void createControls(String onlyThisControl) throws Exception {
		createControls(this.oBaseForm, -1, onlyThisControl);
	}

	public void createControls(JBaseForm f, long idRow, String onlyThisControl) throws Exception {
		JFormControles controls = new JFormControles(this.oBaseForm,null);
		JIterator<JFormControl> iter = f.getControles().getAllItems();
		while (iter.hasMoreElements()) {
			JFormControl formControl = iter.nextElement();
			if (onlyThisControl != null && !(formControl instanceof JFormPanelResponsive)) {
				if (formControl.getFixedProp() == null)
					continue;
				if (!formControl.getFixedProp().GetCampo().equals(onlyThisControl)&&!formControl.getIdControl().equals(onlyThisControl))
					continue;
			}
			if (!this.canCreateControl(formControl))
				continue;
			controls.AddControl(this.createWebControl(formControl, onlyThisControl));
		}
//		 f.setControles(controls);
	}

	protected boolean canCreateControl(JFormControl formControl) throws Exception {
		return true;
	}

	@Override
	public Dimension getMinimumSize() {
		return new Dimension(478 + 161, 300);
	}

	@Override
	public Dimension getMaximumSize() {
		return null;
	}

	@Override
	public String getComponentType() {
		return "win_form";
	}

	public String getRegisteredObjectId() {
		return this.iRegisteredObjectId;
	}

	public String getRegisteredSourceObjectId() {
		return this.iRegisteredObjectId;
	}

//	public String getWinName() throws Exception {
////		return this.getWin()==null?"temporal":this.getWin().getClass().getSimpleName();
////		return getProviderName()!=null?getProviderName():(this.getWin()==null?"temporal":this.getWin().getClass().getSimpleName());
//		return "obj_"+this.getWin().hashCode();
//	}

	public JBaseForm getBaseForm() throws Exception {
		return this.oBaseForm;
	}

	public boolean isEmbedded() {
		if (this.oBaseForm.isEmbedded())
			return true;
		return embedded;
	}

	public void setEmbedded(boolean embedded) {
		this.embedded = embedded;
	}

	private boolean bPreview = false;
	private String sContainerPreview;
	public String getContainerPreview() {
		return sContainerPreview;
	}
	public void setContainerPreview(String sContainerPreview) {
		this.sContainerPreview = sContainerPreview;
	}
	public void setPreview(boolean v) throws Exception {
		this.bPreview = v;
		this.oBaseForm.setPreview(v);
	}

	public boolean isPreview() {
		return this.bPreview;
	}


	public boolean isModoConsulta() throws Exception {
		return this.getBaseForm().isConsulta();
	}
	public boolean isAlta() throws Exception {
		return this.getBaseForm().isAlta();
	}

	public String getFormName() throws Exception {
		return this.getProviderName();
	}

	private String providerName=null;
	public void setProviderName(String v) {
		this.providerName=v;
	}
	public String getProviderName() throws Exception {
		if (this.providerName!=null) return this.providerName;
		if (this.sourceAction == null)
			return null;
		return "form_"+this.sourceAction.getProviderName();
	}

	public JWebAction getWebSourceAction() throws Exception {
		if (this.sourceAction == null)
			return null;
		JWebActionOwnerProvider ownerProvider = getObjectProviderNoException();
		if (ownerProvider!=null)
			PssLogger.logInfo("xxxx");
		return JWebActionFactory.createViewAreaAndTitleAction(this.sourceAction, this, true, this.getWin().getUniqueId(),ownerProvider==null?null:getObjectProvider().getProviderName());
	}

}
