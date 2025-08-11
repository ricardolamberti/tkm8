package pss.www.platform.content.generators;

import pss.core.win.submits.JAct;
import pss.www.platform.actions.JWebActionFactory;
import pss.www.ui.JWebPage;
import pss.www.ui.JWebView;
import pss.www.ui.JWebViewsConstants;

public class JUpdateServerFieldPageGenerator extends JXMLPageGenerator {

	private JAct submit=null;

	@Override
	protected String getBaseContentName() {
		return "serverfield";
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
		String result = (String)JWebActionFactory.getCurrentRequest().getRegisteredRequestObject( "result");
    this.startNode("message");
		this.setAttribute("ok", result==null?0:(result.equals("OK")?1:0));
		this.setAttribute("response", result);
	 	this.endNode("message");
	}

	}