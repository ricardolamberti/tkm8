/*
 * Created on 11-jun-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.ui;

import pss.www.platform.content.generators.JXMLContent;


/**
 * 
 * 
 * Created on 11-jun-2003
 * @author PSS
 */

public class JWebRootPane extends JWebPanel {

  @Override
	protected void containerToXML(JXMLContent zContent) throws Exception {
  }

  
//  public String getComponentType() {
//    return "panel";
//  }
  


@Override
public String getName() {
    return "win_root_pane";
  }
  
//  public void doLayout() throws Exception {
//  	if (this.getParent().isView()) {
//  		this.setHeight(this.getParent().getSize().height);
//  		this.setWidth(this.getParent().getSize().width);
//  	} 
//  	super.doLayout();
//  }
  
  
  
}
