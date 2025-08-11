package pss.www.ui;

import pss.core.winUI.responsiveControls.JFormTextAreaResponsive;
import pss.www.platform.actions.JWebAction;

public class JWebViewTextAreaResponsive extends JWebTextComponent implements JWebActionable {

	@Override
	public String getComponentType() {
		return "text_area_responsive";
	}

	public static JWebViewTextAreaResponsive create(JWebViewComposite parent, JFormTextAreaResponsive zArea) throws Exception {
		JWebViewTextAreaResponsive webText=new JWebViewTextAreaResponsive();
		webText.takeAttributesFormControlResponsive(zArea);
		webText.setResponsive(zArea.isResponsive());
		if (parent!=null) parent.addChild(zArea.getName(), webText);
		
		return webText;
	}
	public boolean isNeedEnter() {
		return true;
	}
	
	protected String getTextForGeneration() throws Exception {
		return this.getText().replaceAll("\r\n","\n");
	}

  public JWebAction getWebAction() throws Exception {
  	if (!this.hasToRefreshForm()) return null;
  	return this.getObjectProvider().getWebSourceAction();
  }


}
