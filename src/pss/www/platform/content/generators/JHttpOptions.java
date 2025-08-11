package pss.www.platform.content.generators;

import java.io.IOException;

import org.apache.cocoon.ProcessingException;
import org.apache.cocoon.environment.Response;
import org.xml.sax.SAXException;

public class JHttpOptions  extends JBasicXMLContentGenerator {


	@Override
	protected String getBaseContentName() {
		return "httpOptions";
	}

	@Override
	protected void setResponseHeaders(Response zResponse) throws Exception {
		zResponse.addHeader("Access-Control-Allow-Origin", getRequest().getHeader("Origin"));
		zResponse.addHeader("Access-Control-Allow-Headers", "Access-Control-Allow-Credentials, Access-Control-Allow-Methods, Access-Control-Allow-Origin, Access-Control-Allow-Headers, Origin,Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");
		zResponse.addHeader("Access-Control-Allow-Methods", "POST,GET,OPTIONS");
		zResponse.addHeader("Access-Control-Expose-Headers", "*");
		zResponse.addHeader("Access-Control-Allow-Credentials", "true");
		zResponse.addHeader("Accept", "*/*");
	}

	@Override
	protected void doGenerate() throws Exception {
	
	}
	@Override
	public void generate() throws IOException, SAXException, ProcessingException {
	}
}
