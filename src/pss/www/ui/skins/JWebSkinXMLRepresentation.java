/*
 * Created on 24-ago-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.ui.skins;

import org.w3c.dom.NodeList;

import pss.core.data.BizPssConfig;
import pss.www.platform.content.generators.JXMLContent;
import pss.www.ui.processing.JXMLRepresentable;


class JWebSkinXMLRepresentation implements JXMLRepresentable {

  private JWebSkin oSkin;

  public JWebSkinXMLRepresentation(JWebSkin zSkin) {
    this.oSkin = zSkin;
  }

  public void toXML(JXMLContent zContent) throws Exception {
    zContent.startNode("skin");
//    zContent.setAttribute("base_path", this.oSkin.getRelativeBasePath());
    zContent.setAttribute("base_path", "skin/"+oSkin.createGenerator().getSkinPath());
    zContent.setAttribute("url_prefix", BizPssConfig.getPssConfig().getAppURLPrefix());

    
    NodeList oSkinChildrenDOMs = this.oSkin.getRootElement().getChildNodes();
    for (int i = 0; i < oSkinChildrenDOMs.getLength(); i++) {
      zContent.embed(oSkinChildrenDOMs.item(i));
    }
    
    zContent.endNode("skin");
  }

  public void destroy() {
    this.oSkin = null;
  }

}
