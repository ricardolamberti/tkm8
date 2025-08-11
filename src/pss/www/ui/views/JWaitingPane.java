package pss.www.ui.views;

import pss.www.platform.content.generators.JXMLContent;
import pss.www.ui.JWebLabel;
import pss.www.ui.skins.JWebSkin;


public class JWaitingPane extends JWebLabel {

 
  public JWaitingPane() {
  	this.setName(getComponentType());
  }

  @Override
	public void destroy() {
    super.destroy();
  }


  protected void containerToXML(JXMLContent zContent) throws Exception {
  }

  @Override
	public String getLabel() {
    return "Por favor, espere...";
  }

  @Override
	public String getComponentType() {
    return "waiting_pane";
  }


}
