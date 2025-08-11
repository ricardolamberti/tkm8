package pss.www.ui.views;

import pss.common.security.BizUsuario;
import pss.www.platform.actions.JWebActionFactory;
import pss.www.platform.content.generators.JXMLContent;
import pss.www.ui.JWebView;
import pss.www.ui.JWebViewInternalComposite;
import pss.www.ui.processing.JWebCanvas;

public class JCanvasResponsiveEmptyView  extends JWebView {


	  private JWebViewInternalComposite oCanvas;


		@Override
		protected void containerToXML(JXMLContent zContent) throws Exception {
			zContent.setAttribute("scroll", getScroll(zContent) );

			if (isActiveHelp()) 
				zContent.setAttribute("help", BizUsuario.getUsr().getObjCompany().getObjBusiness().getHelp());//+"#"+getHelp());

			zContent.setAttribute("modalmustback", 	(String)JWebActionFactory.getCurrentRequest().getRegisteredRequestObject("modalmustback"));
			Boolean clearChangeInputs = (Boolean)JWebActionFactory.getCurrentRequest().getRegisteredRequestObject("clearChangeInputs");
			if (clearChangeInputs!=null)
				zContent.setAttribute("clearChangeInputs", 	clearChangeInputs.booleanValue());
			

		}  

		public JCanvasResponsiveEmptyView(JWebCanvas zCanvas) {
	    this.oCanvas = (JWebViewInternalComposite) zCanvas;
	  }
	  
	  @Override
		protected void build() throws Exception {
	    this.add(oCanvas.getName(), this.oCanvas);
	  }

	 

	}
