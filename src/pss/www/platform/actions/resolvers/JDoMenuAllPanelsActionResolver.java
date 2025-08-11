package pss.www.platform.actions.resolvers;

import pss.core.win.JBaseWin;
import pss.core.win.actions.BizAction;
import pss.www.platform.actions.JWebActionFactory;
import pss.www.platform.actions.results.JWebActionResult;

public class JDoMenuAllPanelsActionResolver extends JDoPssActionResolver {

	@Override
	protected String getBaseActionName() {
		return "viewArea";
	}

	@Override
	protected boolean isAjax() {return true;}

  @Override
	protected boolean isBackPageOnDelete() {return true;}
	
	protected boolean cleanPreviousHistory() throws Exception {
		return true;
	}
	boolean isModal=false;
	protected JWebActionResult performAction(JBaseWin actionOwner, BizAction zAction) throws Throwable {
		if (zAction!=null)
			isModal=zAction.isModal();
		return super.performAction(actionOwner,zAction);
	}
  public boolean calculeIsModal() throws Exception {
  	return (isModal);
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
