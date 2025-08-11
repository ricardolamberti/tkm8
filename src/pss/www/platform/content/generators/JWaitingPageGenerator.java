package pss.www.platform.content.generators;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;
import java.util.Random;

import org.apache.avalon.framework.parameters.Parameters;
import org.apache.cocoon.ProcessingException;
import org.apache.cocoon.environment.Response;
import org.apache.cocoon.environment.SourceResolver;
import org.xml.sax.SAXException;

import pss.www.platform.applications.JWaitMessage;
import pss.www.platform.applications.JWebApplicationSession;
import pss.www.ui.controller.JWebClientConfiguration;

public class JWaitingPageGenerator extends JXMLComponentGenerator {

	@Override
	protected String getBaseContentName() {
		return "waiting";
	}

	protected void attachToRunningThread() throws Exception {
//		this.oRequest.attachToRunningThreadWhitinBase(this.getSession());
	}
	@Override
	@SuppressWarnings("unchecked")
	public void setup(SourceResolver resolver, Map objectModel, String src, Parameters par) throws ProcessingException, SAXException, IOException {
		super.setup(resolver, objectModel, src, par);
		getRequest().setAjax(true);
	}

	@Override
	protected void doGenerate() throws Exception {
    if (getRequest().getArgument("cancel_wait").equals("true")) {
    	JWebApplicationSession.cancelWaitMessage(getRequest().getArgument("wait_request"));
    }
    
    String msg="";
    JWaitMessage msgW = JWebApplicationSession.consumeWaitMessage();
    if (msgW==null) {
  		msg= "(NOMESSAGE)";
    } else {
	    msg += (msgW.getWait_icon()==null)?"":"(ICONO)[I:"+msgW.getWait_icon()+"]";
	    msg += (!msgW.isWait_progress())?"":"(PROGRESS)[P:"+msgW.getWait_percent()+"|T:"+msgW.getWait_total()+"]";
	    msg += (!msgW.isWait_acceptCancel())?"":"(CANCEL)";
	    msg += "{"+msgW.getWait_message()+"}";
	    msg += "[REQ:"+msgW.getWait_idrequest()+"]";
    }
 		this.startNode("wait");
		this.addTextNode("source", msg);
		this.endNode("wait");
		
	
	}

	@Override
	protected void setResponseHeaders(Response zResponse) throws Exception {
			if (JWebClientConfiguration.getCurrent().acceptsGZip()) {
				zResponse.addHeader("Content-Encoding", "gzip");
			}
			super.setResponseHeaders(zResponse);

	}
	
	static long count =(new Random()).nextLong();

	public Serializable getKey() {
		return (String) ""+count++;
	}
}
