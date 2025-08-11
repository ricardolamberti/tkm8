package pss.www.ui;

import pss.www.platform.content.generators.JXMLContent;

public class JWebPanelResizableResponsive extends JWebPanelResponsive {
	@Override
	public String getComponentType() {
		return "panel_resizable_responsive";
	}

	@Override
	protected void componentToXML(JXMLContent zContent) throws Exception {
		zContent.setAttribute("id", this.getName());
		super.componentToXML(zContent);
  }
}
