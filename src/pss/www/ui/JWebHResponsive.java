package pss.www.ui;

import pss.www.platform.content.generators.JXMLContent;

public class JWebHResponsive extends JWebViewInternalComposite implements JWebIconHolder,JWebLabelHolder {

	private String sLabel;
	private long size;

	@Override
	public String getComponentType() {
		return "h"+size+"_responsive";
	}


	public String getLabel() {
		return sLabel;
	}

	public void setLabel(String sTitle) {
		this.sLabel = sTitle;
	}

	public JWebHResponsive() {

	}

	public JWebHResponsive(int zSize,String zLabel) {
		super();
		size=zSize;
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
		// TODO Auto-generated method stub
		
	}

	
}