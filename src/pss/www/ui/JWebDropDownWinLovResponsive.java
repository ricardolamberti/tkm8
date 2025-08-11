package pss.www.ui;

import pss.common.security.BizUsuario;
import pss.core.tools.JTools;
import pss.core.tools.collections.JIterator;
import pss.core.win.JBaseWin;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.controls.JFormControles;
import pss.core.winUI.controls.JFormLocalForm;
import pss.core.winUI.responsiveControls.JFormDropDownWinLovResponsive;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;
import pss.www.platform.actions.JWebAction;
import pss.www.platform.actions.JWebActionFactory;
import pss.www.platform.actions.JWebServerAction;
import pss.www.platform.actions.requestBundle.JWebActionData;
import pss.www.platform.content.generators.JXMLContent;

public class JWebDropDownWinLovResponsive  extends JWebPanelResponsive implements JWebActionOwnerProvider, JWebEditComponentContainer,JWebActionable {
	private BizAction sourceAction;
	private String title;

	private String iRegisteredObjectId = null;
	private JWin baseWin;
	public JWebWinLOVResponsive winlov;
	public JFormDropDownWinLovResponsive control;

	long sizeRows;
	boolean withNew;
	boolean isOpen;
	String zobject;
	private boolean inactive=false;

	public boolean isInactive() {
		return inactive;
	}

	public void setInactive(boolean inactive) {
		this.inactive = inactive;
	}	
	public boolean hasLabelInfo() {
  	return true;
  }

	public JFormDropDownWinLovResponsive getControl() {
		return control;
	}

	public void setControl(JFormDropDownWinLovResponsive control) {
		this.control = control;
	}
	public String getZObject() {
		return zobject;
	} 

	public void setZObject(String zobject) {
		this.zobject = zobject;
	}

	public boolean getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}

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


	public JWin getBaseWin() {
		return baseWin;
	}

	public void setBaseWin(JWin baseWin) {
		this.baseWin = baseWin;
	}
	@Override
	public String getComponentType() {
		return "dropdownwinlov_responsive"+(winlov.isNoForm()?"_noform":"");
	}
	
	
	public static JWebDropDownWinLovResponsive create(JWebViewComposite parent, JFormDropDownWinLovResponsive control, String onlyThisControl) throws Exception {
		JWebDropDownWinLovResponsive webDropDown=new JWebDropDownWinLovResponsive();
		
		
		webDropDown.winlov = new JWebWinLOVResponsive();
		
		webDropDown.winlov.setFormLov(control);
		webDropDown.winlov.setSearchString(control.getSearchString());
		webDropDown.winlov.setEditable(!control.ifReadOnly());
		webDropDown.winlov.setResponsive(control.isResponsive());
		webDropDown.winlov.setShowLupa(control.isShowLupa());
		webDropDown.winlov.takeAttributesFormControlResponsive(control);

		webDropDown.takeAttributesFormControlResponsive(control);
		webDropDown.setSourceAction(control.getActionNew());
		webDropDown.setBaseWin(control.getBaseWin());
		webDropDown.setTitle(control.getTitle());
		webDropDown.setWithNew(control.isWithNew());
		webDropDown.setSizeRows(control.getSizeRows());
		webDropDown.setIsOpen(control.getIsOpen());
		webDropDown.setZObject(control.getZObject());
		webDropDown.setControl(control);
		
	 if (parent!=null)
    	parent.addChild(webDropDown.getObjectName(), webDropDown);
		
	 if ((control.isEditable()||onlyThisControl!=null) && control.isWithNew()) {
		 webDropDown.addActionComponentes(onlyThisControl);
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
		
		return JWebActionFactory.createViewAreaAndTitleAction(this.sourceAction, this.getObjectProvider(), true,  this.getObjectProvider().getRegisteredObjectId(),null,false);
	}

	

	@Override
	public void destroy() {

		super.destroy();
	}



	

	public void addActionComponentes(String onlyThisControl) throws Exception {
		JFormControles controles = getControl().getControlesAction();
		if (controles == null)
			return;
		BizAction action = getControl().getDropDownForm().findActionBySubmit();
		if (action == null)
			return;
		if (onlyThisControl!=null && (!getControl().getFixedProp().GetCampo().equals(onlyThisControl) || getControl().getSearchString()==null)) {
			setInactive(true);
		}

		JIterator<JFormControl> it = controles.getAllItems();
		JFormControl ctrl;
		while (it.hasMoreElements()) {
			ctrl = it.nextElement();
			if (onlyThisControl != null && !(ctrl instanceof JFormPanelResponsive) && !(ctrl instanceof JFormLocalForm) && !(ctrl instanceof JFormDropDownWinLovResponsive)) {
				if (ctrl.getFixedProp() == null)
					continue;
				if (!ctrl.getFixedProp().GetCampo().equals(onlyThisControl))
					continue;
			}
			this.createWebControl(ctrl, onlyThisControl);
		}

		this.addChild(this.buildActionBar());
		action.setModal(JWebActionFactory.getCurrentRequest().parentIsModal());
		action.setDescrip("Aplicar");
		if (getControl().getDropDownForm().getNameApplyAction() != null)
			action.setDescrip(getControl().getDropDownForm().getNameApplyAction());
		this.getActionBar().addSubmitAction(action, getControl().getDropDownForm().getBaseWin().getUniqueId(), getControl().getDropDownForm().getBaseWin(), getControl().getDropDownForm().isSwitcheable(), false);
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
		super.containerToXML(zContent);
		if (isInactive()) return;
		zContent.startNode("winlov");
		winlov.componentToXML(zContent);
		zContent.endNode("winlov");

		if (getControl().isWithUpdate()&&(!getForm().isModoConsulta())) {
			BizAction action =  getControl().buildWins().getWinRefWithActions().findActionByUniqueId(getControl().getIdActionUpdate());
			if (action!=null) {
				zContent.startNode("action_edit");
				action.setModal(true);
				JWebAction oAction=JWebActionFactory.createViewAreaAndTitleAction(action, this , false, null);
				JWebActionData nav = ((JWebServerAction) oAction).addData("extra_form_data");
				nav.add("embedded", "" + false);

				if (oAction!=null) {
					oAction.toXML(zContent);
				}
				zContent.endNode("action_edit");
				
			}
		}


	}
	

	@Override
	protected void componentToXML(JXMLContent zContent) throws Exception {
			JBaseWin win = getControl().getWinNew();
//			if (!getForm().isModoConsulta())
//				win.setCanConvertToURL(false); // en edicion para que las modificaciones se apliquen no debe reconstruirse
			win.setDropListener(getBaseWin());
			win.setAttachField(getName());
			if (!isInactive()) {
				this.iRegisteredObjectId = zContent.addObject(this.getWinName(), win);
				
				zContent.setAttribute("zobject", this.iRegisteredObjectId );
				zContent.setAttribute("obj_provider", getProviderName());
				zContent.setAttribute("size_rows", getSizeRows());
				zContent.setAttribute("is_open", getIsOpen());
				zContent.setAttribute("required", getControl().ifRequerido());
				zContent.setAttributeNLS("form_objectOwner", this.getObjectProvider().getRegisteredObjectId());
				zContent.setAttributeNLS("form_objprovider", this.getObjectProvider().getProviderName());
				if (winlov.getWinLov().getAction()!=null) zContent.setAttribute("form_objectAction", winlov.getWinLov().getAction().getId());
			}
	
		super.componentToXML(zContent);

	}
	public void setValue(String value) throws Exception {
		winlov.setValueFromDBString(value);
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
 if (!this.hasToRefreshForm()) return null;
  	return this.getObjectProvider().getWebSourceAction();
  }
}
