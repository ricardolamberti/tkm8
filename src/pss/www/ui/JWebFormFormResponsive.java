package pss.www.ui;

import pss.core.tools.JTools;
import pss.core.tools.collections.JIterator;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.controls.JFormControles;
import pss.core.winUI.controls.JFormForm;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;
import pss.core.winUI.responsiveControls.JFormTabPanelResponsive;
import pss.www.platform.actions.JWebAction;
import pss.www.platform.actions.JWebActionFactory;
import pss.www.platform.actions.JWebServerAction;
import pss.www.platform.actions.requestBundle.JWebActionData;
import pss.www.platform.content.generators.JXMLContent;

public class JWebFormFormResponsive extends JWebPanelResponsive implements JWebActionOwnerProvider  {

	private BizAction sourceAction;
	private String title;

	private String iRegisteredObjectId = null;
	private JWin baseWin;

	private boolean diferido;
	private boolean withActions;
	public boolean isWithActions() {
		return withActions;
	}	
	public boolean isModal() throws Exception {
		return sourceAction.isModal();
	}

	public void setWithActions(boolean withActions) {
		this.withActions = withActions;
	}

	public boolean isDiferido() {
		return diferido;
	}

	public void setDiferido(boolean diferido) {
		this.diferido = diferido;
	}

	public JWin getBaseWin() {
		return baseWin;
	}

	public void setBaseWin(JWin baseWin) {
		this.baseWin = baseWin;
	}

	@Override
	public String getProviderName() throws Exception {
		if (this.sourceAction == null)
			return null;
		String wn = JWebActionFactory.getCurrentRequest().getNameDictionary(getWinName().replace(".", "_"));

		return this.sourceAction.getProviderName()+"_w"+wn;
	}

	@Override
	public String getRegisteredObjectId() {
		return iRegisteredObjectId;
	}
	public String getWinName() throws Exception {
		return JTools.getValidFilename(this.getBaseWin().getClass().getSimpleName()+"_"+this.getBaseWin().getRecord().getKeyListValue()+"_"+this.getBaseWin().getRecord().getRowId());
	}
	public BizAction getSourceAction() {
		return sourceAction;
	}

	public void setSourceAction(BizAction sourceAction) {
		this.sourceAction = sourceAction;
	}
	public void setTitle(String title) {
		this.title = title;
	}



	@Override
	public JWebAction getWebSourceAction() throws Exception {
		if (this.sourceAction == null)
			return null;
		return JWebActionFactory.createViewAreaAndTitleAction(this.sourceAction, this, true, this.getWinName());
	}


	

	public JWebFormFormResponsive() {
		super();
	}
	@Override
	public void destroy() {

		super.destroy();
	}
	private JWebViewComposite oRootPanel;

	public JWebViewComposite getRootPanel() {
		return this.oRootPanel;
	}

	public void setRootPanel(JWebViewComposite panel) {
		this.oRootPanel = panel;
	}
	@Override
	protected void build() throws Exception {
		this.oRootPanel = skin().buildForm(this);
		super.build();
	}
	public static JWebFormFormResponsive create(JWebViewComposite parent,  JFormForm control, String onlyThisControl) throws Exception {
		BizAction pageAction = control.getAction();
		
		JWebViewComposite realParent = parent;
		if (parent instanceof JWebTabPanelResponsive) {
			JWebTabResponsive tab = new JWebTabResponsive();
			tab.setTitle(pageAction.GetDescr());
			tab.setId(control.getIdControl());
	    if (parent!=null) {
				tab.setPreview(parent.findJWebWinForm().isPreview());
	    	parent.addChild(tab.getObjectName(), tab);
	    }
	    realParent=tab;
		}
		
		JWebFormFormResponsive webForm=new JWebFormFormResponsive();
		webForm.takeAttributesFormControlResponsive(control);
		webForm.setSourceAction(control.getAction());
		webForm.setBaseWin(control.getWin());
		webForm.setTitle(control.getTitle());
		webForm.setDiferido(control.isDiferido());
		webForm.setWithActions(control.isWithActions());
		webForm.ensureIsBuilt();
		realParent.addChild(webForm.getRootPanel().getName(), webForm.getRootPanel());

		if(!webForm.isDiferido())
			webForm.addActionComponentes(control, onlyThisControl);
		return webForm;
	}
	
	public void addActionComponentes(JFormForm zPanel, String onlyThisControl) throws Exception {
		JFormControles controles = zPanel.getControlesAction();
		if (controles == null)
			return;
		JIterator<JFormControl> it = controles.getAllItems();
		JFormControl ctrl;
		while (it.hasMoreElements()) {
			ctrl = it.nextElement();
			if (onlyThisControl != null && !(ctrl instanceof JFormPanelResponsive)) {
				if (ctrl.getFixedProp() == null)
					continue;
				if (!ctrl.getFixedProp().GetCampo().equals(onlyThisControl))
					continue;
			}
			if (ctrl instanceof JFormTabPanelResponsive) 
				continue;
			this.createWebControl(ctrl, onlyThisControl);
		}

	}

	@Override
	public String getComponentType() {
		return "formform_responsive";
	}
	
	


	@Override
	protected void componentToXML(JXMLContent zContent) throws Exception {
		this.iRegisteredObjectId = zContent.addObject(this.getWinName(), this.getBaseWin());
		if (isDiferido()) {
			JWebAction actSmplClick = this.getActionDiferido(this.iRegisteredObjectId);
			if (actSmplClick != null) {
				zContent.startNode("diferido");
				actSmplClick.toXML(zContent);
				zContent.endNode("diferido");
			}
		}
		super.componentToXML(zContent);
	}

	@Override
	public String getTitle() throws Exception {
		return title;
	}
	protected JWebAction getActionDiferido(String winId) throws Exception {
		BizAction action = getSourceAction();
		if (action == null)
			return null;
		JWebServerAction wa = null;
		wa = JWebActionFactory.createPreviewAreaAction(action, this, winId);
		JWebActionData nav = wa.getNavigationData();
		nav.add("embedded", "" + true);
		nav.add("is_preview", ""+true);
		nav.add("hide_act_bar", ""+!isWithActions());

		return wa;
	}

}
