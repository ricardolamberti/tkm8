package pss.www.ui;

import pss.core.winUI.responsiveControls.JFormFieldsetExpandedResponsive;
import pss.www.platform.content.generators.JXMLContent;

public class JWebFieldsetExpandedResponsive extends JWebFieldsetResponsive {

	boolean nSstartCollapse;

	public boolean isOnStartCollapse() {
		return nSstartCollapse;
	}

	public void setOnStartCollapse(boolean startCollapse) {
		this.nSstartCollapse = startCollapse;
	}
	
	@Override
	public String getComponentType() {
		return "fieldsetexpanded_responsive";
	}
	public static JWebFieldsetExpandedResponsive create(JWebViewComposite parent,  JFormFieldsetExpandedResponsive zPanel, String onlyThisControl) throws Exception {
		JWebFieldsetExpandedResponsive webPanel=new JWebFieldsetExpandedResponsive();
		webPanel.takeAttributesFormControlResponsive(zPanel);
		webPanel.setLabel(zPanel.getTitle());
		webPanel.setLabelRight(zPanel.getTitleRight());
		webPanel.setWidth(zPanel.getWidth());
		webPanel.setOnlyFieldSet(zPanel.isOnlyFieldSet());
		webPanel.setHeight(zPanel.getHeight());
		webPanel.setDataparent(zPanel.getDataparent());
		webPanel.setOnStartCollapse(zPanel.isOnStartCollapse());
		if (parent!=null) parent.addChild( webPanel);
		webPanel.addAllComponentes(zPanel,onlyThisControl);
		return webPanel;
	}
	
	@Override
	protected void containerToXML(JXMLContent zContent) throws Exception {
		if (isOnStartCollapse()) {
			zContent.setAttribute("onstart-collapse", true);
		}
		super.containerToXML(zContent);
	}
}
