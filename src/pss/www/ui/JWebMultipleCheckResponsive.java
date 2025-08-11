package pss.www.ui;

import java.awt.Dimension;

import pss.core.services.fields.JObject;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.responsiveControls.JFormMultipleCheckResponsive;
import pss.www.platform.actions.JWebAction;
import pss.www.platform.content.generators.JXMLContent;
import pss.www.ui.views.JControlListValueChooserModel;

public class JWebMultipleCheckResponsive   extends JWebAbstractValueChooser implements JWebActionable {

	private String sOnChangeEvent;

	public JWebMultipleCheckResponsive() {
		sOnChangeEvent="";
		bConvertForeignCharsForWeb = false;
	}

	@Override
	public String getComponentType() {
		return "multiple_check_responsive";
	}

	@Override
	protected Dimension getDefaultSize() {
		return new Dimension(150, 96);
	}

	public void setOnChangeEvent(String zOnChangeEvent) {
		sOnChangeEvent=zOnChangeEvent;
	}

	@Override
	protected void widgetToXML(JXMLContent zContent) throws Exception {
		zContent.setAttribute("onchange_event", sOnChangeEvent);
		super.widgetToXML(zContent);
	}

	public void setController(JObject<?> zDataType, JWebLabel zLabel, boolean zRequired, int zMaxLength, String zInputAttrs, JControlCombo zControlCombo) throws Exception {
		super.setController(zDataType, zLabel,  zRequired,  zMaxLength,  zInputAttrs);
		JWebValueChooserModel zModel=new JControlListValueChooserModel(getController(), zControlCombo);
		setModel(zModel);
	}
	
	public static JWebMultipleCheckResponsive create(JWebViewComposite parent, JFormMultipleCheckResponsive control) throws Exception {
		JWebMultipleCheckResponsive webCombo=new JWebMultipleCheckResponsive();
		webCombo.takeAttributesFormControlResponsive(control);
		webCombo.setResponsive(control.isResponsive());
		webCombo.commonComboSettings(webCombo, parent,  control);
		return webCombo;
	}

	public JWebMultipleCheckResponsive commonComboSettings(JWebMultipleCheckResponsive webCombo, JWebViewComposite parent, JFormMultipleCheckResponsive winControl) throws Exception {
		JWebValueChooserModel zModel=new JControlListValueChooserModel(null, winControl.getControlCombo());
		webCombo.setModel(zModel);
		webCombo.takeAttributesFormControlResponsive(winControl);
		if (parent!=null) parent.addChild(winControl.getName(), webCombo);
		return webCombo;
	}


	@Override
	protected String getState() throws Exception {
		return (getForm().isModoConsulta()||!isEditable()) ? null : "edit";
	}
	
  public JWebAction getWebAction() throws Exception {
  	if (!this.hasToRefreshForm()) return null;
  	return this.getObjectProvider().getWebSourceAction();
  }

}
