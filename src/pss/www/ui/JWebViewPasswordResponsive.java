package pss.www.ui;

import pss.core.winUI.responsiveControls.JFormPasswordResponsive;

public class JWebViewPasswordResponsive extends JWebTextComponent {


	  public JWebViewPasswordResponsive() {
	  }
	  
	  
	  @Override
		public String getComponentType() {
	    return "password_field_responsive";
	  }
	  
	  public static JWebViewPasswordResponsive create(JWebViewComposite parent, JFormPasswordResponsive zPassword) throws Exception {
		  JWebViewPasswordResponsive webPass = new JWebViewPasswordResponsive();
	  	webPass.takeAttributesFormControlResponsive(zPassword);
	  	webPass.setResponsive(zPassword.isResponsive());
	    if (parent!=null)
	    	parent.addChild(zPassword.getName(), webPass);
	  	return webPass;
	  }
	  
	}
