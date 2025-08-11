package pss.www.platform.actions.resolvers;

import pss.www.platform.actions.JWebActionFactory;

public class JDoViewAllPanelsActionResolver extends JDoPssActionResolver {

	@Override
	protected String getBaseActionName() {
		return "viewArea";
	}

	@Override
	protected boolean isAjax() {return true;}

  @Override
	protected boolean isBackPageOnDelete() {return true;}
	
  public boolean calculeIsModal() throws Exception {
  	return (!isBack() && isModal()) || (isBack() && getRequest().hasBackToModal());
  }
	protected String getTransformer() throws Exception {
		String sRequestedOutput=JWebActionFactory.getSerializerFor(this.getRequest());
		if (sRequestedOutput.equalsIgnoreCase("mobile")) 
			return "resources/stylesheets/mobile/page.xsl";

		if (calculeIsModal())
			return "resources/stylesheets/html/responsive_ajax.modalviewarea.xsl";
		return "resources/stylesheets/html/responsive_ajax.viewAreaAndTitle.xsl";
	}
 
}

