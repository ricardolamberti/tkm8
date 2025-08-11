
package pss.www.platform.actions;

public class JWebWinListRefreshAction extends JWebServerAction {

	JWebWinListRefreshAction(String zURI, boolean zIsSubmit) {
    super(zURI, zIsSubmit);
  }

  JWebWinListRefreshAction() {
  	super("do-WinListRefreshAction", true);
  	this.setAjaxContainer("win_list_complete");
  }

//  @Override
//  public String getAjaxContainer() {
//  	//return "win_list_table_panel";
//  	return "win_list_complete";
//  	
//  }
//
//  @Override
//	public String getObjectOwnerID() {
//		return getOwnerProvider().getRegisteredObjectId();
//	}
//
//  @Override
//	public boolean hasObjectOwnerID() {
//		return true;
//	}

  
}
