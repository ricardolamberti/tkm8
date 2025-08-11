package pss.www.platform.content.generators;

import pss.core.win.JBaseWin;
import pss.www.ui.JWebView;
import pss.www.ui.controller.JWebClientConfiguration;
import pss.www.ui.processing.JWebCanvas;
import pss.www.ui.views.JCanvasResponsiveEmptyView;

public class JListExpandAreaPageGenerator extends JViewAreaAllPanelsPageGenerator {

		
	public String getToolbarPos() throws Exception {
		
		return JBaseWin.TOOLBAR_TOP;
	}

	public boolean isPreview() {
		return true;
	}


	protected void doGenerate() throws Exception {
		JWebClientConfiguration cf = this.getRequest().getUICoordinator().getClientConfiguration();
		super.doGenerate();
	}

	public boolean isWithPreview() throws Exception {
		return false;
	}
	
	@Override
	protected JWebView embedInView(JBaseWin win, JWebCanvas canvas) throws Exception {
		return new JCanvasResponsiveEmptyView(canvas);
	}

}
