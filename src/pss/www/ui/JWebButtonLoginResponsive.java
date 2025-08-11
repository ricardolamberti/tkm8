package pss.www.ui;

import pss.www.platform.content.generators.JXMLContent;

public class JWebButtonLoginResponsive extends JWebAbstractActionView   {


	@Override
	public String getComponentType() {
		return "buttonlogin_responsive";
	}
 
	
	

	
	@Override
	protected void componentToXML(JXMLContent zContent) throws Exception {
		zContent.setAttribute("form_name", "login");
		zContent.setAttribute("editable", true);

	
  }
	
	


}
