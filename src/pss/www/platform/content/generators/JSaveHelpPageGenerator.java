package pss.www.platform.content.generators;

import java.io.IOException;
import java.util.Map;

import org.apache.avalon.framework.parameters.Parameters;
import org.apache.cocoon.ProcessingException;
import org.apache.cocoon.environment.Response;
import org.apache.cocoon.environment.SourceResolver;
import org.xml.sax.SAXException;

import pss.www.ui.controller.JWebClientConfiguration;

public class JSaveHelpPageGenerator  extends JXMLComponentGenerator {

	@Override
	protected String getBaseContentName() {
		return "save.help";
	}
	@Override
	@SuppressWarnings("unchecked")
	public void setup(SourceResolver resolver, Map objectModel, String src, Parameters par) throws ProcessingException, SAXException, IOException {
		super.setup(resolver, objectModel, src, par);
		getRequest().setAjax(true);
	}

	@Override
	protected void doGenerate() throws Exception {
		this.startNode("message");
		this.setAttribute("type", "SaveHelp");
		this.addTextNode("text", "");
		this.endNode("message");
	}

	@Override
	protected void setResponseHeaders(Response zResponse) throws Exception {
			if (JWebClientConfiguration.getCurrent().acceptsGZip()) {
				zResponse.addHeader("Content-Encoding", "gzip");
			}
	}
}
