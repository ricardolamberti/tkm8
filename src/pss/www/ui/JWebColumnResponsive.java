package pss.www.ui;

import pss.core.winUI.responsiveControls.JFormColumnResponsive;

public class JWebColumnResponsive extends JWebPanelResponsive {

	@Override
	public String getComponentType() {
		return "column_responsive";
	}


	public static JWebColumnResponsive create(JWebViewComposite parent,  JFormColumnResponsive zPanel,String onlyThisControl) throws Exception {
		JWebColumnResponsive webPanel=new JWebColumnResponsive();
		webPanel.takeAttributesFormControlResponsive(zPanel);
		webPanel.setLabel(zPanel.getTitle());
		webPanel.setWidth(zPanel.getWidth());
		webPanel.setHeight(zPanel.getHeight());
		webPanel.setGutter(zPanel.isGutter());
		webPanel.setZoomtofit(zPanel.getZoomtofit());
		webPanel.setToBottom(zPanel.isToBottom());
		if (parent!=null) parent.addChild( webPanel);
		webPanel.addAllComponentes(zPanel,onlyThisControl);
		return webPanel;
	}
}
