package pss.www.ui;

import pss.www.platform.content.generators.JXMLContent;

public class JWebFormResponsive extends JWebViewInternalComposite implements JWebIconHolder,JWebLabelHolder {

	private String sLabel;


	@Override
	public String getComponentType() {
		return "form_responsive";
	}


	public String getLabel() {
		return sLabel;
	}

	public void setLabel(String sTitle) {
		this.sLabel = sTitle;
	}

	public JWebFormResponsive() {

	}

	public JWebFormResponsive(String zLabel) {
		super();
		this.setLabel(zLabel);
	}


	private JWebIcon oIcon;

	public JWebIcon getIcon() {
		return this.oIcon;
	}

	public void setIcon(JWebIcon icon) {
		this.oIcon = icon;
	}

  public JWebIcon getMaximizeIcon() {
		return null;
	}
	@Override
	protected void containerToXML(JXMLContent zContent) throws Exception {
	}

	
}