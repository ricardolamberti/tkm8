package pss.www.ui;

import pss.core.winUI.responsiveControls.JFormLabelDataResponsive;
import pss.www.platform.content.generators.JXMLContent;

public class JWebLabelDataResponsive extends JWebTextField {

	private boolean estatico;

	public boolean isEstatico() {
		return estatico;
	}

	public void setEstatico(boolean estatico) {
		this.estatico = estatico;
	}
	@Override
	public String getComponentType() {
		return "text_label_responsive";
	}

	public static JWebLabelDataResponsive create(JWebViewComposite parent, JFormLabelDataResponsive zEdit) throws Exception {
		JWebLabelDataResponsive webText=new JWebLabelDataResponsive();
		webText.takeAttributesFormControlResponsive(zEdit);
		webText.setResponsive(zEdit.isResponsive());
		webText.setEstatico(zEdit.isEstatico());
		if (parent!=null) parent.addChild(zEdit.getName(), webText);
		webText.oEdit=zEdit;

		return webText;
	}
	
	@Override
	protected void componentToXML(JXMLContent zContent) throws Exception {
	
		super.componentToXML(zContent);
	}


}
