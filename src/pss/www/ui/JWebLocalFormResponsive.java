package pss.www.ui;

import pss.common.security.BizUsuario;
import pss.core.tools.JTools;
import pss.core.tools.collections.JIterator;
import pss.core.win.JWin;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.controls.JFormControles;
import pss.core.winUI.controls.JFormLocalForm;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;
import pss.core.winUI.responsiveControls.JFormTabPanelResponsive;
import pss.www.platform.actions.JWebAction;
import pss.www.platform.content.generators.JXMLContent;

public class JWebLocalFormResponsive  extends JWebPanelResponsive implements JWebActionOwnerProvider  {

	private String title;

	private String iRegisteredObjectId = null;
	private JWin baseWin;

	public JWin getBaseWin() {
		return baseWin;
	}

	public void setBaseWin(JWin baseWin) {
		this.baseWin = baseWin;
	}

	@Override
	public String getProviderName() throws Exception {
		return "local_"+JTools.getValidFilename(title);
	}
	public boolean isModal() throws Exception {
		return false;
	}
	@Override
	public String getRegisteredObjectId() {
		return iRegisteredObjectId;
	}
	public String getWinName() throws Exception {
		return JTools.getValidFilename(this.getBaseWin().getClass().getSimpleName()+"_"+this.getBaseWin().getRecord().getKeyListValue()+"_"+this.getBaseWin().getRecord().getRowId());
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public JWebAction getWebSourceAction() throws Exception {
		return null;
	}


	

	public JWebLocalFormResponsive() {
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
		this.oRootPanel = BizUsuario.retrieveSkinGenerator().buildLocalForm(this);
		super.build();
	}
	
	public static JWebLocalFormResponsive create(JWebViewComposite parent,  JFormLocalForm control, String onlyThisControl) throws Exception {
	
		JWebViewComposite realParent = parent;
		if (parent instanceof JWebTabPanelResponsive) {
			JWebTabResponsive tab = new JWebTabResponsive();
			tab.setTitle(control.getTitle());
			tab.setId(control.getIdControl());
	    if (parent!=null) {
				tab.setPreview(parent.findJWebWinForm().isPreview());
	    	parent.addChild(tab.getObjectName(), tab);
	    }
	    realParent=tab;
		}
	
		JWebLocalFormResponsive webForm=new JWebLocalFormResponsive();
		webForm.takeAttributesFormControlResponsive(control);
		webForm.setTitle(control.getTitle());
		webForm.ensureIsBuilt();
		realParent.addChild(webForm.getRootPanel().getName(), webForm.getRootPanel());

		webForm.addActionComponentes(control, onlyThisControl);
		return webForm;
	}

	public void addActionComponentes(JFormLocalForm zPanel, String onlyThisControl) throws Exception {
		JFormControles controles = zPanel.getRootPanel().getControles();
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
		return "localform_responsive";
	}
	
	


	@Override
	protected void componentToXML(JXMLContent zContent) throws Exception {
		super.componentToXML(zContent);
	}

	@Override
	public String getTitle() throws Exception {
		return title;
	}

}
