package pss.www.platform.actions.resolvers;

import pss.www.platform.actions.JWebActionFactory;
import pss.www.platform.actions.results.JWebActionResult;

public class JBasicFrontDoorActionResolver extends JFrontDoorActionResolver {

	@Override
	protected String getBaseActionName() {
		return "basic";
	}

	
	@Override
	protected JWebActionResult perform() throws Throwable {
		this.addObjectToResult("transformer", this.getTransformer());
		this.addObjectToResult("serializer", this.getSerializer());
	
		return super.perform();
	}
	protected String getTransformer() throws Exception {
		String sRequestedOutput=JWebActionFactory.getSerializerFor(this.getRequest());
		if (sRequestedOutput.equalsIgnoreCase("mobile")) 
			return "resources/stylesheets/mobile/page.xsl";
		return "resources/stylesheets/html/responsive_page.xsl";
	}
	private String getSerializer() throws Exception {
		String sRequestedOutput=JWebActionFactory.getSerializerFor(this.getRequest());
		if (sRequestedOutput.equalsIgnoreCase("mobile")) return "mobile";
		return "html";
	}
}
