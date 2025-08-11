
package pss.www.platform.actions;

public class JWebWinListExpandAction extends JWebServerAction {

	JWebWinListExpandAction(String zURI, boolean zIsSubmit) {
    super(zURI, zIsSubmit);
  }

  JWebWinListExpandAction() {
  	super("do-WinListExpandAction", true);
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
