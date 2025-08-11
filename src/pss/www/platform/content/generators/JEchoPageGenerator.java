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

import pss.common.security.BizUsuario;
import pss.www.ui.controller.JWebClientConfiguration;

public class JEchoPageGenerator  extends JXMLComponentGenerator {

	@Override
	protected String getBaseContentName() {
		return "echo";
	}

//	protected void attachToRunningThread() throws Exception {
//		this.oRequest.attachToRunningThreadWhitinBase(this.getSession());
//	}
	@Override
	@SuppressWarnings("unchecked")
	public void setup(SourceResolver resolver, Map objectModel, String src, Parameters par) throws ProcessingException, SAXException, IOException {
		super.setup(resolver, objectModel, src, par);
		getRequest().setAjax(true);
	}

	@Override
	protected void doGenerate() throws Exception {
  
		if (getRequest().getSession().isOpen()) {
	 		this.startNode("wait");
			this.addTextNode("source", "ok");
			this.endNode("wait");
			
		} else {
			this.startNode("message");
			this.setAttribute("type", "SessionTimeOut");
			this.setAttribute("loginpage", BizUsuario.getCurrentLoginPage());
			this.addTextNode("text",BizUsuario.getMessage("La sesion ha expirado, debe reingresar su usuario y clave ", null));
			this.endNode("message");			
		}
		
	
	}  	 

	@Override
	protected void setResponseHeaders(Response zResponse) throws Exception {
			if (JWebClientConfiguration.getCurrent().acceptsGZip()) {
				zResponse.addHeader("Content-Encoding", "gzip");
			}
	}
	
	static long count =(new Random()).nextLong();

	public Serializable getKey() {
		return (String) ""+count++;
	}
}
