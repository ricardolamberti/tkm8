package pss.www.ui;

import pss.core.winUI.responsiveControls.JFormColorPickerResponsive;

public class JWebColorPickerResponsive extends JWebTextField  implements JWebActionable {

	@Override
	public String getComponentType() {
		return "color_field_responsive";
	}

	public static JWebColorPickerResponsive create(JWebViewComposite parent, JFormColorPickerResponsive zEdit) throws Exception {
		JWebColorPickerResponsive webText=new JWebColorPickerResponsive();
		webText.takeAttributesFormControlResponsive(zEdit);
		webText.setResponsive(zEdit.isResponsive());
	//	webText.setSize(webText.getSize().width, webText.getSize().height);
		if (parent!=null) parent.addChild(zEdit.getName(), webText);
		webText.setPopupIcon(zEdit.hasPopupIcon());

		webText.oEdit=zEdit;
		return webText;
	}
	protected boolean hasPopupIcon() throws Exception {
		return true;
	}
}