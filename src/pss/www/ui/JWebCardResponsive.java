package pss.www.ui;

import pss.core.tools.JTools;
import pss.core.tools.collections.JIterator;
import pss.core.win.JBaseWin;
import pss.core.win.JWin;
import pss.core.win.actions.BizAction;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.controls.JFormControles;
import pss.core.winUI.responsiveControls.JFormCardResponsive;
import pss.core.winUI.responsiveControls.JFormPanelResponsive;
import pss.www.platform.actions.JWebAction;
import pss.www.platform.actions.JWebActionFactory;
import pss.www.platform.actions.JWebServerAction;
import pss.www.platform.actions.requestBundle.JWebActionData;
import pss.www.platform.content.generators.JXMLContent;

public class JWebCardResponsive extends JWebPanelResponsive implements JWebActionOwnerProvider, JWebEditComponentContainer  {

	private String title;

	private String iRegisteredObjectId = null;
	private JWin baseWin;

	private boolean diferido;
	private boolean withActions;
	boolean withHeader = true;
	String zobject;
	
	String field;
	Class classField;

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}
	
	public Class getClassField() {
		return classField;
	}

	public void setClassField(Class classField) {
		this.classField = classField;
	}
	public String getZObject() {
		return zobject;
	}

	public void setZObject(String zobject) {
		this.zobject = zobject;
	}
	public boolean isWithHeader() {
		return withHeader;
	}
	public JFormCardResponsive getControlCard() {
		return (JFormCardResponsive)getControl();
	}

	public void setWithHeader(boolean withHeader) {
		this.withHeader = withHeader;
	}
	public boolean isModal() throws Exception {
		return getSourceAction().isModal();
	}
	
  public boolean isUploadData() throws Exception {
  	return true;
  }

  public boolean isCard() throws Exception {
  	return true;
  }

	public boolean isWithActions() {
		return withActions;
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
		if (this.getSourceAction() == null)
			return null;
		String wn = getWinName().replace(".", "_");

		return this.getSourceAction().getProviderName()+"_"+wn;
	}
	@Override
	public String getFormName() throws Exception {
		return getProviderName();
	}


	@Override
	public String getRegisteredObjectId() {
		return iRegisteredObjectId;
	}
	
	String winName;
	public String getWinName() throws Exception {
		return "card_"+JTools.getValidFilename(this.getBaseWin().getClass().getSimpleName()+"_"+this.getBaseWin().getRecord().getKeyListValue()+getName()+"_"+this.getBaseWin().getRecord().getRowId());
	}
	public BizAction getSourceAction() {
		return getControlCard().getAction();
	}


	public void setTitle(String title) {
		this.title = title;
	}



	@Override
	public JWebAction getWebSourceAction() throws Exception {
		BizAction sourceAction = getSourceAction();
		if (sourceAction == null)
			return null;
		return JWebActionFactory.createViewAreaAndTitleAction(sourceAction, this, true, this.getWinName());
	}


	

	public JWebCardResponsive() {
		super();
	}
	@Override
	public void destroy() {

		super.destroy();
	}
	
	public static JWebCardResponsive create(JWebViewComposite parent,  JFormCardResponsive control,String onlyThisControl) throws Exception {
		JWebCardResponsive table = new JWebCardResponsive();
		table.takeAttributesFormControlResponsive(control);
		table.setBaseWin(control.getBaseWin());
		table.setTitle(control.getTitle());
		table.setWithHeader(control.isWithHeader());
		table.setZObject(control.getZObject());
		table.setField(control.getField());
		table.setClassField(control.getClassField());

		table.setDiferido(control.isDiferido());
		table.setWithActions(control.isWithActions());
		 if (parent!=null)
    	parent.addChild(table.getObjectName(), table);
		
		if(!table.isDiferido())
			 table.addActionComponentes(control,onlyThisControl);
		return table;
	}
	

	public void addActionComponentes(JFormCardResponsive zPanel,String onlyThisControl) throws Exception {
		 JFormControles controles =zPanel.getControlesAction();
		 if (controles==null) return;
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
		
	}

	@Override
	public String getComponentType() {
		return "card_responsive";
	}
	
	


	@Override
	protected void componentToXML(JXMLContent zContent) throws Exception {
		if (isDiferido()) {
			JWin clone = this.getBaseWin().getClass().newInstance();
			clone.setRecord(this.getBaseWin().getRecord());
			clone.SetVision(this.getBaseWin().GetVision());
			clone.setCanConvertToURL(false);
			this.iRegisteredObjectId = "clone_"+this.getWinName();
			iRegisterWin= clone;
			JWebAction actSmplClick = this.getActionDiferido(this.iRegisteredObjectId);
			if (actSmplClick != null) {
				zContent.startNode("diferido");
				actSmplClick.toXML(zContent);
				zContent.endNode("diferido");
			}
		} else {
			if (getSourceAction()==null) {
				super.componentToXML(zContent);
				return;
			}
			JBaseWin win = getSourceAction().getResult();
//			if (!getForm().isModoConsulta())
//				win.setCanConvertToURL(false); // en edicion para que las modificaciones se apliquen no debe reconstruirse
			this.iRegisteredObjectId = this.getWinName();
			iRegisterWin= win;
		}
		if (getField()==null) {
			if (iRegisterWin!=null && iRegisterWin.canConvertToURL())
				zContent.setAttribute("zobject", this.iRegisteredObjectId );
		} else
			getBaseWin().getRecord().getProp(getField()).setUniqueId(iRegisteredObjectId);
		
		super.componentToXML(zContent);
	}
	JBaseWin iRegisterWin;
	@Override
	protected void componentToXMLposLayout(JXMLContent zContent) throws Exception {
			super.componentToXMLposLayout(zContent);
			if (iRegisterWin!=null)
				zContent.addObject(this.iRegisteredObjectId, iRegisterWin);
	}
	
	
	
	public void registerObject(JXMLContent zContent) throws Exception {
		this.iRegisteredObjectId = this.getWin().getUniqueId();
		zContent.setAttribute("zobject", this.iRegisteredObjectId);
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
	//	wa.setUploadata(true);
		JWebActionData nav = wa.getNavigationData();
		nav.add("embedded", "" + true);
		nav.add("is_preview", ""+true);
		nav.add("hide_act_bar", ""+!isWithActions());
		nav.add("title", getTitle());
		nav.add("with_header", ""+isWithHeader());

		return wa;
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

}
