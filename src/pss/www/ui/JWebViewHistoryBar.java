package pss.www.ui;

import pss.core.win.actions.BizAction;
import pss.www.platform.actions.JWebAction;
import pss.www.platform.actions.JWebActionFactory;
import pss.www.platform.actions.JWebServerAction;
import pss.www.platform.content.generators.JXMLContent;

public class JWebViewHistoryBar extends JWebViewInternalComposite {

  JWebDivResponsive rootpanel;
  long id=0;

	public String getUniqueId() {
		return "history_bar_link"+String.valueOf(id++);
	}

	public JWebDivResponsive getRootpanel() {
		return rootpanel;
	}

	public void setRootpanel(JWebDivResponsive rootpanel) {
		this.rootpanel = rootpanel;
	}

@Override
	protected void containerToXML(JXMLContent zContent) throws Exception {
  }

  @Override
	public String getComponentType() {
    return "history_bar";
  }

	public void addHistoryAction(JWebAction zAction, JWebIcon zIcon) throws Exception {
		if (zAction==null) return;
		this.skin().getHistoryAction(this,zAction,zIcon);
	}
	
	public void addHistoryMessage(String message) throws Exception {
		this.skin().getHistoryMessage(this,message);
	}
	
	public JWebServerAction addBackAction(String confirmBack) throws Exception {
		BizAction action = this.createActionBack();
		JWebServerAction webAction = this.createWebCancelAction();
		if (confirmBack!=null) {
			action.setConfirmMessageDescription(confirmBack);
			action.setConfirmMessage(true);
			webAction.setConfirmMessageDescription(confirmBack);
		}
		this.skin().createBackButton(this,webAction);
		return webAction;
	}

	public BizAction createActionBack() throws Exception {
		return this.skin().createActionBack(null);
	}
	// go back action
	public JWebServerAction createWebCancelAction() throws Exception {
		return JWebActionFactory.createWinGoBack();
	}

}
