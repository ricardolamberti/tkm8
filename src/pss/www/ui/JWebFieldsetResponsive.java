package pss.www.ui;

import pss.core.winUI.responsiveControls.JFormFieldsetResponsive;
import pss.www.platform.content.generators.JXMLContent;

public class JWebFieldsetResponsive extends JWebPanelResponsive {
	boolean onlyFieldSet;
//only_fieldset
	public boolean isOnlyFieldSet() {
		return onlyFieldSet;
	}

	public void setOnlyFieldSet(boolean onlyFieldSet) {
		this.onlyFieldSet = onlyFieldSet;
	}
	@Override
	public String getComponentType() {
		return "fieldset_responsive";
	}
	public static JWebFieldsetResponsive create(JWebViewComposite parent,  JFormFieldsetResponsive zPanel, String onlyThisControl) throws Exception {
		JWebFieldsetResponsive webPanel=new JWebFieldsetResponsive();
		webPanel.takeAttributesFormControlResponsive(zPanel);
		webPanel.setLabel(zPanel.getTitle());
		webPanel.setLabelRight(zPanel.getTitleRight());
		webPanel.setWidth(zPanel.getWidth());
		webPanel.setOnlyFieldSet(zPanel.isOnlyFieldSet());
		webPanel.setHeight(zPanel.getHeight());
		webPanel.setDataparent(zPanel.getDataparent());
		if (parent!=null) parent.addChild( webPanel);
		webPanel.addAllComponentes(zPanel,onlyThisControl);
		return webPanel;
	}
	@Override
	protected void containerToXML(JXMLContent zContent) throws Exception {
		if (isOnlyFieldSet()) {
			zContent.setAttribute("only_fieldset", true);
		}
		super.containerToXML(zContent);
	}
}
