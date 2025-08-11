package pss.www.platform.actions.resolvers;

import pss.www.platform.actions.results.JWebActionResult;


public class JDoSign   extends JBasicWebActionResolver 
{
	@Override
	protected boolean isAjax() {
		return true;
	}

	


	@Override
	protected JWebActionResult perform() throws Throwable {
		this.addObjectToResult("signer", "signer");
		this.addObjectToResult("browser", this.getBrowser());
		return super.perform();
	}
	
	
	public String getBrowser() throws Exception {
		String browser =  getRequest().getHeader("user-agent");
		if (browser.indexOf("MSIE")!=-1) return "Explorer";
		if (browser.indexOf("Mozilla")!=-1) return "Mozilla";
		return "Other";
	}
	
		@Override
		protected String getBaseActionName() {
			return "signer";
		}
}