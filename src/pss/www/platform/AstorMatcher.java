package pss.www.platform;

import java.util.Map;

import org.apache.avalon.framework.parameters.Parameters;
import org.apache.cocoon.environment.ObjectModelHelper;
import org.apache.cocoon.environment.Request;
import org.apache.cocoon.matching.WildcardURIMatcher;

public class AstorMatcher extends WildcardURIMatcher {
@Override
protected String getMatchString(Map objectModel, Parameters parameters) {
	Request oServletReq=ObjectModelHelper.getRequest(objectModel);
	if (oServletReq!=null ) {
		if (oServletReq.getMethod().equals("OPTIONS"))
			return "http_options_request";
	}
	
	return super.getMatchString(objectModel, parameters);
}
}
