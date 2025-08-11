package pss.www.ui;

import pss.www.platform.content.generators.JXMLContent;

public class JWebFormGroup extends JWebViewInternalComposite implements JWebLabelHolder {

	private String sLabel;

	@Override
	public String getComponentType() {
		return "form_group";
	}


	public String getLabel() {
		return sLabel;
	}

	public void setLabel(String sTitle) {
		this.sLabel = sTitle;
	}

	public JWebFormGroup() {

	}

	public JWebFormGroup(String zLabel) {
		super();
		this.setLabel(zLabel);
	}



	@Override
	protected void containerToXML(JXMLContent zContent) throws Exception {

	}

}