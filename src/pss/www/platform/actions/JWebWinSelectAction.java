
package pss.www.platform.actions;

public class JWebWinSelectAction extends JWebServerAction {
	
	@Override
	public boolean hasFunction() { return true;}
	
	@Override
	public String getFunction() throws Exception {
		return "formLovSelect('"+getProviderName()+"');";
	}


}
