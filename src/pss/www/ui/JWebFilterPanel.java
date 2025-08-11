package pss.www.ui;

import java.util.ArrayList;
import java.util.List;

import pss.core.win.JWin;
import pss.www.platform.actions.JWebAction;
import pss.www.platform.actions.JWebActionFactory;
import pss.www.platform.actions.JWebServerAction;
import pss.www.platform.actions.requestBundle.JWebActionData;
import pss.www.platform.content.generators.JXMLContent;

public class JWebFilterPanel extends JWebPanel implements JWebEditComponentContainer, JWebActionOwnerProvider {
	
	private IWebWinGeneric webWinList;
	
	List<JWebButton> buttons;
	
	public List<JWebButton> getButtons() {
		if (buttons==null) buttons = new ArrayList<JWebButton>();
		return buttons;
	}

	public void addButtons(JWebButton buttons) {
		 getButtons().add(buttons);
	}

	public boolean isModal() throws Exception {
		return webWinList.getSourceAction().isModal();
	}
	
	public JWebFilterPanel(IWebWinGeneric w) {
		this.webWinList =w;
	}
  public boolean isUploadData() throws Exception {
  	return false;
  }
  
  public boolean isCard() throws Exception {
  	return false;
  }

	public String getName() {
		return "filter_pane";
	}
	
  public boolean isInLine() {
  	return true;
  }

	
	public String getFormName() throws Exception {
		return this.getName();
	}
  public boolean isAlta() {
  	return false;
  }
  public boolean isModoConsulta() {
  	return false;
  }
	
  
  public String getProviderName() throws Exception {
  	return this.webWinList.getProviderName();
  }
  
  public String getRegisteredObjectId() throws Exception {
  	return this.webWinList.getRegisteredObjectId();
  }

  
  public JWebAction getWebSourceAction() throws Exception {
  	JWebServerAction  a = JWebActionFactory.createViewAreaAndTitleAction(webWinList.getSourceAction(), this, true, this.webWinList.getSourceObjectId());
		JWebActionData nav=a.addData("win_list_nav");
		nav.add("with_preview", ""+webWinList.findPreviewFlag());
		nav.add("is_preview", ""+webWinList.isPreview());
		nav.add("embedded", ""+webWinList.isEmbedded());
		nav.add("toolbar", ""+webWinList.getToolbar());
		return a;

  }
  
  @Override
  protected void componentToXML(JXMLContent content) throws Exception {
  	this.webWinList.regiterObjects();
   	for (JWebButton b:getButtons()) {
   		if (b.getWebAction()==null) continue;//sin permisos
			((JWebServerAction)b.getWebAction()).setObjectOwnerID(this.webWinList.getRegisteredObjectId());
  	}
   	super.componentToXML(content);
 
  }

	@Override
	public String getTitle() throws Exception {
		return this.webWinList.getWins().GetTitle();
	}

	public JWin getWin() {
		return this.getForm().getWin();
	}
}
