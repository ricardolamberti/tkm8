package pss.www.ui;

import pss.core.winUI.responsiveControls.JFormPanelResponsive;

public class JWebDivResponsive extends JWebPanelResponsive implements JWebIconHolder,JWebLabelHolder {

	private String sLabel;


	@Override
	public String getComponentType() {
		return "div_responsive";
	}


	public String getLabel() {
		return sLabel;
	}

	public void setLabel(String sTitle) {
		this.sLabel = sTitle;
	}

	public JWebDivResponsive() {

	}

	public JWebDivResponsive(String zLabel) {
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

	public static JWebPanelResponsive create(JWebViewComposite parent,  JFormPanelResponsive zPanel, String onlyThisControl) throws Exception {
		JWebDivResponsive webPanel=new JWebDivResponsive();
		webPanel.takeAttributesFormControlResponsive(zPanel);
		webPanel.setLabel(zPanel.getTitle());
		webPanel.setWidth(zPanel.getWidth());
		webPanel.setHeight(zPanel.getHeight());
		webPanel.setGutter(zPanel.isGutter());
		webPanel.setZoomtofit(zPanel.getZoomtofit());
		webPanel.setDataparent(zPanel.getDataparent());

		if (parent!=null) 
			parent.addChild( webPanel);
		webPanel.addAllComponentes(zPanel,onlyThisControl);
		return webPanel;
	}
	
	
}