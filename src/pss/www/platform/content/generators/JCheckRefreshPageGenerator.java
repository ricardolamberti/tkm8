package pss.www.platform.content.generators;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

import org.apache.avalon.framework.parameters.Parameters;
import org.apache.cocoon.ProcessingException;
import org.apache.cocoon.environment.Response;
import org.apache.cocoon.environment.SourceResolver;
import org.xml.sax.SAXException;

import pss.common.security.BizUsuario;
import pss.www.ui.controller.JWebClientConfiguration;

public class JCheckRefreshPageGenerator   extends JXMLComponentGenerator {

	@Override
	protected String getBaseContentName() {
		return "check-refresh";
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
		if (BizUsuario.getUsr().getObjBusiness().isNeedAutoRefresh(getRequest().getArgument("marca")))
			this.setAttribute("type", "REFRESH");
		else
			this.setAttribute("type", "NOTHING");
		this.addTextNode("text", "");
		this.endNode("message");
	}

	@Override
	protected void setResponseHeaders(Response zResponse) throws Exception {
			if (JWebClientConfiguration.getCurrent().acceptsGZip()) {
				zResponse.addHeader("Content-Encoding", "gzip");
			}
			super.setResponseHeaders(zResponse);
	}

	public Serializable getKey() {
		return null;
	}

	

}
