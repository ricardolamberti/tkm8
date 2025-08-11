package pss.www.platform.actions.resolvers;

import java.util.Date;

import javax.servlet.http.Cookie;

import org.apache.cocoon.environment.ObjectModelHelper;
import org.apache.cocoon.environment.Request;

import com.ibm.icu.util.Calendar;

import pss.core.tools.JDateTools;
import pss.core.tools.JTools;
import pss.www.platform.actions.results.JWebActionResult;

public class JAuth0LoginActionResolver   extends JBasicWebActionResolver {


	@Override
	protected JWebActionResult perform() throws Throwable {
		Request oServletReq=ObjectModelHelper.getRequest(getObjectModel());
		// get the request
		Cookie persistentKey = (Cookie) oServletReq.getCookieMap().get("JPERSISTENTSESSIONID");
		if (persistentKey==null||persistentKey.getValue().equals("")) return  super.perform();
		try {
			String msg = JTools.decryptMessage(persistentKey.getValue());
			String[] parts = msg.split("_");
			Date d = JDateTools.StringToDate(parts[2],"dd/MM/yyyy");
			Calendar cal = Calendar.getInstance();
			cal.setTime(d);
			cal.add(Calendar.MONTH, 1);
			if (!JDateTools.dateEqualOrAfter(cal.getTime(), new Date())) return  super.perform(); //expiró
		} catch (Exception e) {
			 return  super.perform();
		}
		
		
	//	if (!persistentKey.isHttpOnly()) return  super.perform();// el usuario no la ingreso a mano

		return this.getRedirector().goPersistentLogin();
	}
	
	@Override
	protected String getBaseActionName() {
		return "pre-login";
	}
}
