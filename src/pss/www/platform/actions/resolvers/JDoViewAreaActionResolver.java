/*
 * Created on 24-jun-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.platform.actions.resolvers;

import pss.www.platform.actions.JWebActionFactory;

public class JDoViewAreaActionResolver extends JDoPssActionResolver {

	@Override
	protected String getBaseActionName() {
		return "viewArea";
	}

	@Override
	protected boolean isAjax() {return true;}

  @Override
	protected boolean isBackPageOnDelete() {return true;}

	
  private boolean isParentModal() throws Exception {
   	return getRequest().hasBackToModal();

  }
  @Override
  public boolean calculeIsModal() throws Exception {
  	return isModal();
  }
	protected String getTransformer() throws Exception {
		String sRequestedOutput=JWebActionFactory.getSerializerFor(this.getRequest());
		if (sRequestedOutput.equalsIgnoreCase("mobile")) 
			return "resources/stylesheets/mobile/page.xsl";
		if (calculeIsModal())
			return "resources/stylesheets/html/responsive_ajax.modalviewarea.xsl";
		return "resources/stylesheets/html/responsive_ajax.viewarea.xsl";
	}
	

	protected String getSerializer() throws Exception {
		String sRequestedOutput=JWebActionFactory.getSerializerFor(this.getRequest());
		if (sRequestedOutput.equalsIgnoreCase("mobile")) return "mobile";
		return "html";
	}

}

