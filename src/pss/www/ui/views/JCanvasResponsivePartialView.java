package pss.www.ui.views;

import pss.common.security.BizUsuario;
import pss.www.platform.actions.JWebActionFactory;
import pss.www.platform.content.generators.JXMLContent;
import pss.www.ui.JWebDivResponsive;
import pss.www.ui.JWebView;
import pss.www.ui.JWebViewInternalComposite;
import pss.www.ui.processing.JWebCanvas;

public class JCanvasResponsivePartialView extends JWebView {

	private JWebViewInternalComposite oCanvas;

	@Override
	protected void containerToXML(JXMLContent zContent) throws Exception {
		zContent.setAttribute("scroll", getScroll(zContent) );
		if (isActiveHelp()) 
			zContent.setAttribute("help", BizUsuario.getUsr().getObjCompany().getObjBusiness().getHelp());//+"#"+getHelp());
		String moldalmustback = (String)JWebActionFactory.getCurrentRequest().getRegisteredRequestObject("modalmustback");
		if (moldalmustback!=null)
			zContent.setAttribute("modalmustback", 	moldalmustback);
		Boolean clearChangeInputs = (Boolean)JWebActionFactory.getCurrentRequest().getRegisteredRequestObject("clearChangeInputs");
		if (clearChangeInputs!=null)
			zContent.setAttribute("clearChangeInputs", 	clearChangeInputs.booleanValue());
		if (BizUsuario.retrieveSkinGenerator().hasHistory() ) {
			if (hasMoreOneHistory()) {
				this.generateHistoryBarActions();
				if (this.hasHistoryBar())
					this.oHistoryBar.getRootpanel().toXML(zContent);
			}
		}

	}

	public JCanvasResponsivePartialView(JWebCanvas zCanvas) {
		this.oCanvas = (JWebViewInternalComposite) zCanvas;
	}

	@Override
	protected void build() throws Exception {
		JWebDivResponsive workArea = new JWebDivResponsive();
		workArea.setClassResponsive("workarea");
		workArea.add(oCanvas.getName(), this.oCanvas);
		this.add("workarea", workArea);
	}
}
