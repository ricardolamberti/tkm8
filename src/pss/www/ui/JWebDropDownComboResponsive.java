package pss.www.ui;

import pss.common.security.BizUsuario;
import pss.core.tools.JTools;
import pss.core.tools.collections.JIterator;
import pss.core.win.JBaseWin;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.controls.JFormControles;
import pss.core.winUI.responsiveControls.JFormDropDownComboResponsive;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;
import pss.www.platform.actions.JWebAction;
import pss.www.platform.actions.JWebActionFactory;
import pss.www.platform.content.generators.JXMLContent;

public class JWebDropDownComboResponsive extends JWebPanelResponsive implements JWebActionOwnerProvider, JWebEditComponentContainer,JWebActionable {
	private BizAction sourceAction;
	private String title;

	private String iRegisteredObjectId = null;
	private JWin baseWin;
	JWebComboResponsive combo;

	long sizeRows;
	boolean withNew;

	public boolean isWithNew() {
		return withNew;
	}

	public void setWithNew(boolean withAlta) {
		this.withNew = withAlta;
	}

	public long getSizeRows() {
		return sizeRows;
	}

	public void setSizeRows(long sizeRows) {
		this.sizeRows = sizeRows;
	}


	public boolean isModal() throws Exception {
		return sourceAction.isModal();
	}
	
  public boolean isUploadData() throws Exception {
  	return true;
  }

  public boolean isCard() throws Exception {
  	return true;
  }
  public boolean hasLabelInfo() {
  	return true;
  }

	public JWin getBaseWin() {
		return baseWin;
	}

	public void setBaseWin(JWin baseWin) {
		this.baseWin = baseWin;
	}
	@Override
	public String getComponentType() {
		return "dropdowncombo_responsive"+(combo.isNoForm()?"_noform":"");
	}
	
	
	public static JWebDropDownComboResponsive create(JWebViewComposite parent, JFormDropDownComboResponsive control) throws Exception {
		JWebDropDownComboResponsive webDropDown=new JWebDropDownComboResponsive();
		
		
		webDropDown.combo = new JWebComboResponsive();
		
		webDropDown.combo.setCombo(control);
		webDropDown.combo.setResponsive(control.isResponsive());
		webDropDown.combo.setShowKey(control.isShowKey());
		webDropDown.combo.commonComboSettings(webDropDown.combo, null, control);
		
		webDropDown.takeAttributesFormControlResponsive(control);
		webDropDown.setSourceAction(control.getActionNew());
		webDropDown.setBaseWin(control.getBaseWin());
		webDropDown.setTitle(control.getTitle());
		webDropDown.setWithNew(control.isWithNew());
		webDropDown.setSizeRows(control.getSizeRows());
		
	 if (parent!=null)
    	parent.addChild(webDropDown.getObjectName(), webDropDown);
		
	 if (control.isEditable() && control.isWithNew()) {
		 webDropDown.addActionComponentes(control,(String)null);
	  }
		return webDropDown;
	}
	


	@Override
	public String getProviderName() throws Exception {
		if (this.sourceAction == null)
			return null;
		String wn = getWinName().replace(".", "_");

		return this.sourceAction.getProviderName()+"_"+wn;
	}
	@Override
	public String getFormName() throws Exception {
		return getProviderName();
	}


	@Override
	public String getRegisteredObjectId() {
		return iRegisteredObjectId;
	}
	public String getWinName() throws Exception {
//		String idProv = JWebActionFactory.getCurrentRequest().getSession().getNameDictionary(JTools.getValidFilename(this.getBaseWin().getClass().getSimpleName()+"_"+this.getBaseWin().getRecord().getKeyListValue()+"_"+this.getBaseWin().getRecord().getRowId()));
//		return "card_"+idProv;

		return "card_"+JTools.getValidFilename(this.getBaseWin().getClass().getSimpleName()+"_"+sourceAction.getIdAction()+getControl().getName()+"_"+this.getBaseWin().getRecord().getRowId());
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

	

	@Override
	public void destroy() {

		super.destroy();
	}
	
	
	

	public void addActionComponentes(JFormDropDownComboResponsive zPanel,String onlyThisControl) throws Exception {
		JFormControles controles =zPanel.getControlesAction();
		 if (controles==null) return;
			BizAction action = zPanel.getDropDownForm().findActionBySubmit();
			if (action==null) return;
		JIterator<JFormControl> it=controles.getAllItems();
		JFormControl ctrl;
		while (it.hasMoreElements()) {
			ctrl=it.nextElement();
			if (onlyThisControl!=null && !(ctrl instanceof JFormPanelResponsive)) {
				if (ctrl.getFixedProp()==null) continue;
				if (!ctrl.getFixedProp().GetCampo().equals(onlyThisControl)) continue;
			}
			this.createWebControl(ctrl,onlyThisControl);
		}
 
		
		this.addChild(this.buildActionBar());
  	action.setModal(JWebActionFactory.getCurrentRequest().parentIsModal());
		action.setDescrip("Aplicar");
		if (zPanel.getDropDownForm().getNameApplyAction()!=null)
			action.setDescrip(zPanel.getDropDownForm().getNameApplyAction());
		this.getActionBar().addSubmitAction(action, zPanel.getDropDownForm().getBaseWin().getUniqueId(), zPanel.getDropDownForm().getBaseWin(),zPanel.getDropDownForm().isSwitcheable(),false);
	}

	
	JWebWinActionBar oActionBar;
	public JWebViewInternalComposite buildActionBar() throws Exception {
		return this.generatedActionBar().getRootPanel();
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

	public JWebWinActionBar generatedActionBar() throws Exception {
		JWebWinActionBar actionBar = new JWebWinActionBarForm(this, true, JBaseWin.TOOLBAR_BOTTOM, true);
		actionBar.setPreview(false);
		this.oActionBar = actionBar;
		this.oActionBar.setForceWithLabels(false);
		this.oActionBar.setRootPanel(BizUsuario.retrieveSkinGenerator().createActionBarForm(this));
		return this.oActionBar;
	}
	
	@Override
	protected void containerToXML(JXMLContent zContent) throws Exception {
		// TODO Auto-generated method stub
		super.containerToXML(zContent);
		zContent.startNode("combo");
		combo.componentToXML(zContent);
		zContent.endNode("combo");

	}

	@Override
	protected void componentToXML(JXMLContent zContent) throws Exception {
			JBaseWin win = null;
			win=this.getSourceAction().getResult();
//			if (!getForm().isModoConsulta())
//				win.setCanConvertToURL(false); // en edicion para que las modificaciones se apliquen no debe reconstruirse
			win.setDropListener(getBaseWin());
			win.setAttachField(getName());
			this.iRegisteredObjectId = zContent.addObject(this.getWinName(), win);
			zContent.setAttribute("zobject", this.iRegisteredObjectId );
			zContent.setAttribute("obj_provider", getProviderName());
			zContent.setAttribute("size_rows", getSizeRows());
			zContent.setAttribute("required", getControl().ifRequerido());
		
		
		super.componentToXML(zContent);

	}
	public void setValue(String value) throws Exception {
		combo.setValueFromDBString(value);
	}

	@Override
	public String getTitle() throws Exception {
		return title;
	}
	

	@Override
	public boolean isModoConsulta() throws Exception {
		return getForm().isModoConsulta();
	}
  public boolean isAlta()  throws Exception {
  	return getForm().isAlta();
  }


	@Override
	public boolean isInLine() {
		return false;
	}

	@Override
	public JWin getWin() {
		return getBaseWin();
	}

  public JWebAction getWebAction() throws Exception {
 // 	if (!this.hasToRefreshForm()) return null;
  	return this.getObjectProvider().getWebSourceAction();
  }
}
