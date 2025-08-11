package pss.www.platform.content.generators;

import pss.www.platform.actions.JWebActionFactory;
import pss.www.ui.JWebPage;
import pss.www.ui.JWebView;
import pss.www.ui.JWebViewsConstants;

public class JGetChannelPageGenerator  extends JXMLPageGenerator {


	@Override
	protected String getBaseContentName() {
		return "getchannel";
	}

	@Override
	protected String getPageLayoutStereotype() {
		return JWebViewsConstants.PAGE_LAYOUT_APP_INDOORS;
	}

	@Override
	protected boolean isSessionDependent() {
		return true;
	}
	
	
	@Override
	protected JWebView createView() throws Exception {

		return null;
	}

	@Override
	protected JWebPage createPage() throws Exception {
		JWebPage oPage=new JWebPage();
		oPage.setLayoutStereotype(this.getPageLayoutStereotype());
		return oPage;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();
	}
	
	@Override
	protected void doGenerate() throws Exception {
		String channel = (String)JWebActionFactory.getCurrentRequest().getRegisteredRequestObject( "channel");
		this.startNode("channel");
	 	this.setAttribute("channel", channel);
	 	this.endNode("channel");
	}

}