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

import pss.core.winUI.lists.JPlantilla;
import pss.www.ui.controller.JWebClientConfiguration;

public class JLayoutPageGenerator extends JXMLComponentGenerator {

	@Override
	protected String getBaseContentName() {
		return "layout.menu";
	}

	@Override
	@SuppressWarnings("unchecked")
	public void setup(SourceResolver resolver, Map objectModel, String src, Parameters par) throws ProcessingException, SAXException, IOException {
		super.setup(resolver, objectModel, src, par);
		getRequest().setAjax(true);
	}

	@Override
	protected void doGenerate() throws Exception {

		JPlantilla p = (JPlantilla) objectModel.get("plantilla");
		
		String out="newmapa = "+(p==null?"[]": p.serialize());
		this.startNode("code");
		this.addTextNode("source", out);
		this.endNode("code");
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
		return (String) objectModel.get("key")+"_"+(String) objectModel.get("source");
	}
}
