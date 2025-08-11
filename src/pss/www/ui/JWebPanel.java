/*
 * Created on 11-jun-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.ui;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import pss.www.platform.content.generators.JXMLContent;


/**
 * 
 * 
 * Created on 11-jun-2003
 * @author PSS
 */

public class JWebPanel extends JWebViewInternalComposite {
  
 	
	 @Override
		protected void containerToXML(JXMLContent zContent) throws Exception {
	  }

	  @Override
		public String getComponentType() {
	    return "panel";
	  }
		public static JWebViewComposite create(JWebViewComposite parent, JPanel panel, boolean responsive) throws Exception {
			return create(parent,panel,"panel", responsive);
		}
		public static JWebViewComposite create(JWebViewComposite parent, JPanel panel,String name, boolean responsive) throws Exception {
			JWebViewComposite p;
			if (panel.getBorder()!=null) {
				p = new JWebTitledBorder();
		    if (panel.getBorder() instanceof TitledBorder)
		    	((JWebTitledBorder)p).setLabel(((TitledBorder)panel.getBorder()).getTitle());

			} else {
				p = new JWebPanel();
			}
			p.takeAttributesForm(parent,panel);
			p.setResponsive(responsive);
//			p.setBackground(panel.getBackground());
 		//	p.setBackgroundImage(((JPssPanel)panel).getBackgroundImageFile());
			if (parent!=null) { 
				parent.addChild(name+"-"+panel.hashCode(), p);
			}
			
			
			return p;
		}
}
