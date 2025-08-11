package pss.www.ui;

import java.util.ArrayList;
import java.util.List;

import pss.core.win.JWin;
import pss.www.platform.actions.JWebAction;
import pss.www.platform.actions.JWebActionFactory;
import pss.www.platform.actions.JWebServerAction;
import pss.www.platform.actions.requestBundle.JWebActionData;
import pss.www.platform.content.generators.JXMLContent;

public class JWebFilterPaneResponsive extends JWebFormResponsive implements JWebEditComponentContainer, JWebActionOwnerProvider {
	
	private IWebWinGeneric webWinList;
	
	List<JWebButton> buttons;
	
	public List<JWebButton> getButtons() {
		if (buttons==null) buttons = new ArrayList<JWebButton>();
		return buttons;
	}

	public void addButtons(JWebButton buttons) {
		 getButtons().add(buttons);
	}

	public JWebFilterPaneResponsive(IWebWinGeneric w) {
		this.webWinList =w;
	}
  public boolean isUploadData() throws Exception {
  	return false;
  }

  public boolean isCard() throws Exception {
  	return false;
  }
	public String getName() {
		try {
			return "filter_pane_"+webWinList.getProviderName();
		} catch (Exception e) {
			return "filter_pane";
		}
	}
	public boolean isModal() throws Exception {
		return webWinList.getSourceAction().isModal();
	}
  public boolean isInLine() throws Exception {
  	return skin().isFilterInLine();
  }
  public IWebWinGeneric getWebWinList() throws Exception {
  	return webWinList;
  }

	
	public String getFormName() throws Exception {
		return this.getName();
	}
  public boolean isModoConsulta() {
  	return false;
  }
  public boolean isAlta() {
  	return false;
  }

  public String getProviderName() throws Exception {
  	return this.webWinList.getProviderName();
  }
  
  public String getRegisteredObjectId()  throws Exception {
  	return this.webWinList.getRegisteredObjectId();
  }

	public JWin getWin() {
		return this.getForm().getWin();
	}

  public JWebAction getWebSourceAction() throws Exception {
  	JWebServerAction  a = JWebActionFactory.createViewAreaAndTitleAction(webWinList.getSourceAction(), this, true, this.webWinList.getSourceObjectId());
		JWebActionData nav=a.addData("win_list_nav");
		nav.add("with_preview", ""+webWinList.findPreviewFlag());
		nav.add("is_preview", ""+webWinList.isPreview());
		nav.add("embedded", ""+webWinList.isEmbedded());
		nav.add("toolbar", ""+webWinList.getToolbar());
	//	nav.add("from", String.valueOf(0));
		nav.add("paginado", webWinList.getPaginado());
		nav.add("pagina_actual",  String.valueOf(webWinList.getPaginaActual()));
		nav.add("page_size", String.valueOf(webWinList.getPageSize()));
		nav.add("hide_filter_bar", ""+webWinList.isShowFilterBar());
		nav.add("button_search", "true");
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


}
