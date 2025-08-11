package pss.www.ui;

import java.awt.Dimension;

import pss.core.services.fields.JObject;
import pss.core.winUI.controls.JControlCombo;
import pss.core.winUI.responsiveControls.JFormMultipleResponsive;
import pss.www.platform.actions.JWebAction;
import pss.www.platform.content.generators.JXMLContent;
import pss.www.ui.views.JControlListValueChooserModel;

public class JWebMultipleResponsive  extends JWebAbstractValueChooser implements JWebActionable {

	private String sOnChangeEvent;
  private boolean onlyShowSelected=true;

  public boolean isOnlyShowSelected() {
		return onlyShowSelected;
	}

  private String extraClassImage;

  public String getExtraClassImage() {
		return extraClassImage;
	}


	public void setExtraClassImage(String extraClassImage) {
		this.extraClassImage = extraClassImage;
	}

	public void setOnlyShowSelected(boolean onlyShowSelected) {
		this.onlyShowSelected = onlyShowSelected;
	}
	public JWebMultipleResponsive() {
		sOnChangeEvent="";
		bConvertForeignCharsForWeb = false;
	}
	

	@Override
	public String getComponentType() {
		return "multiple_list_responsive";
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
		zContent.setAttribute("onlyshowselected",isOnlyShowSelected());
		super.widgetToXML(zContent);
	}

	public void setController(JObject<?> zDataType, JWebLabel zLabel, boolean zRequired, int zMaxLength, String zInputAttrs, JControlCombo zControlCombo) throws Exception {
		super.setController(zDataType, zLabel,  zRequired,  zMaxLength,  zInputAttrs);
		JWebValueChooserModel zModel=new JControlListValueChooserModel(getController(), zControlCombo);
		setModel(zModel);
	}
	
	public static JWebMultipleResponsive create(JWebViewComposite parent, JFormMultipleResponsive control) throws Exception {
		JWebMultipleResponsive webCombo=new JWebMultipleResponsive();
		webCombo.takeAttributesFormControlResponsive(control);
		webCombo.setOnlyShowSelected(control.isOnlyShowSelected());
		webCombo.setResponsive(control.isResponsive());
		webCombo.setExtraClassImage(control.getExtraClassImage());
		webCombo.commonComboSettings(webCombo, parent,  control);
		return webCombo;
	}

	public JWebMultipleResponsive commonComboSettings(JWebMultipleResponsive webCombo, JWebViewComposite parent, JFormMultipleResponsive winControl) throws Exception {
		JWebValueChooserModel zModel=new JControlListValueChooserModel(null, winControl.getControlCombo());
		webCombo.setModel(zModel);
		zModel.setExtraClassImage(getExtraClassImage());
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
