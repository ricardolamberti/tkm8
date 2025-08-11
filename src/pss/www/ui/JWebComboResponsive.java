package pss.www.ui;

import java.awt.Dimension;

import pss.core.services.fields.JObject;
import pss.core.services.records.JProperty;
import pss.core.win.JWin;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.responsiveControls.JFormComboResponsive;
import pss.www.platform.actions.JWebAction;
import pss.www.platform.content.generators.JXMLContent;
import pss.www.ui.controller.JWebFormControl;

public class JWebComboResponsive extends JWebAbstractValueChooser implements JWebActionable {

	private String sOnChangeEvent;
	private boolean listBox;
	private JWebAction action;
	private String provider;
  private boolean neverUseCache;
  JFormComboResponsive oCombo;

	public JFormComboResponsive getCombo() {
		return oCombo;
	}

	public void setCombo(JFormComboResponsive oCombo) {
		this.oCombo = oCombo;
	}

	public boolean isNeverUseCache() {
		return neverUseCache;
	}

	public void setNeverUseCache(boolean neverUseCache) {
		this.neverUseCache = neverUseCache;
	}	
	public boolean isShowKey() {
		return showKey;
	}

	public void setShowKey(boolean showKey) {
		this.showKey = showKey;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public JWebAction getAction() {
		return action;
	}

	public void setAction(JWebAction action) {
		this.action = action;
	}

	public boolean isListBox() {
		return listBox;
	}

	public void setListBox(boolean listBox) {
		this.listBox = listBox;
	}

	public JWebComboResponsive() {
		sOnChangeEvent="";
		bConvertForeignCharsForWeb = false;
	}

	@Override
	public String getComponentType() {
		return "combobox_responsive"+(isNoForm()?"_noform":"");
	}

	@Override
	protected Dimension getDefaultSize() {
		return new Dimension(150, 24);
	}

	public void setOnChangeEvent(String zOnChangeEvent) {
		sOnChangeEvent=zOnChangeEvent;
	}

	public long getSizeRows() {
		return  isListBox()?getSize().height/14:1;
	}
	@Override
	protected void widgetToXML(JXMLContent zContent) throws Exception {
		zContent.setAttribute("onchange_event", sOnChangeEvent);
		zContent.setAttribute("size_rows",getSizeRows());
		zContent.setAttribute("show_key", isShowKey());
		if (provider!=null)
			zContent.setAttribute("form_name", getProvider());

		super.widgetToXML(zContent);
	}
	
	public void setController(JObject<?> zDataType, String zLabel, boolean zRequired, int zMaxLength, String zInputAttrs, JControlCombo zControlCombo) throws Exception {
		JWebFormControl oControl=new JWebFormControl();
		oControl.setComponent(this);
		setLabelLateral(zLabel);
		JProperty oPropDesc=new JProperty("", this.getName(), zLabel, zDataType, null, false, zRequired, zMaxLength, 0, zInputAttrs, "");
		oControl.setFixedProp(oPropDesc);
		oControl.SetRequerido(zRequired ? "REQ" : "OPT");
		oControl.SetDisplayName(zLabel);
		this.setController(oControl);
		JWebValueChooserModel zModel=new JControlComboValueChooserModel(getController(), zControlCombo);
		setModel(zModel);
	}

	
	public static JWebComboResponsive create(JWebViewComposite parent, JFormComboResponsive control) throws Exception {
		JWebComboResponsive webCombo=new JWebComboResponsive();
		webCombo.setCombo(control);
		webCombo.setResponsive(control.isResponsive());
		webCombo.commonComboSettings(webCombo, parent, control);
		return webCombo;
	}

	public JWebComboResponsive commonComboSettings(JWebComboResponsive webCombo, JWebViewComposite parent, JFormComboResponsive winControl) throws Exception {
		JControlComboValueChooserModel zModel=new JControlComboValueChooserModel(winControl, winControl.getControlCombo());
		zModel.setPermitirTodo(winControl.isPermitirTodo());
		webCombo.setModel(zModel);
		webCombo.setListBox(false);
		webCombo.setModoConsulta(parent==null?false:parent.isModoConsulta());
		webCombo.setShowKey(winControl.isShowKey());
		webCombo.setNeverUseCache(winControl.isNeverUseCache());
		webCombo.takeAttributesFormControlResponsive(winControl);
		webCombo.setEditable(!winControl.ifReadOnly());
		webCombo.setModoConsulta(parent==null?false:parent.isModoConsulta());
		if (winControl.getFixedProp().isRecord())
			webCombo.setUseCache(true);
		if (parent!=null) 
			parent.addChild(winControl.getName(), webCombo);
		return webCombo;
	}
  public void setValue(JWin zVal) throws Exception {
  //	getCombo().setValue(zVal);
  	super.setValue(zVal);
  }
  

	@Override
	protected String getState() throws Exception {
		return null;//(getForm().isModoConsulta()||!isEditable()) ? null : "edit";
	}
	
  public JWebAction getWebAction() throws Exception {
  	if (!this.hasToRefreshForm()) return null;
  	if (action!=null) return action;
  	return this.getObjectProvider().getWebSourceAction();
  }
  
  public JWin getWinElegido(String zClave) throws Exception {
  	return (JWin) getModel().getValues(false).getElement(zClave);
  	
  }
  public JWin[] getWinElegidos(String zClave) throws Exception {
  	return (JWin[]) getModel().getValues(false).getElements();
  	
  }
  


}
