package pss.www.ui;

import pss.www.platform.content.generators.JXMLContent;

public class JWebUnsortedList  extends JWebViewInternalComposite {

  @Override
	protected void containerToXML(JXMLContent zContent) throws Exception {
  }

  @Override
	public String getComponentType() {
    return "ul";
  }

}
