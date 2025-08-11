package pss.www.platform.content.generators;

import pss.www.ui.JWebViewsConstants;
import pss.www.ui.views.JHelpView;

public class JHelpPageGenerator extends JXMLPageGenerator {


	@Override
	protected String getPageLayoutStereotype() throws Exception {
		return JWebViewsConstants.PAGE_HELP;

	}

	@SuppressWarnings("unchecked")
	@Override
	protected Class getViewClass() {
		return JHelpView.class;
	}

	@Override
	protected String getBaseContentName() {
		return "help";
	}
	
	
	@Override
	protected void postEndDocument() throws Exception {
	}



}
