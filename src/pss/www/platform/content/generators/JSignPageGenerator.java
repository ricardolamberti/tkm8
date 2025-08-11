package pss.www.platform.content.generators;

import java.io.Serializable;
import java.util.Random;

import org.apache.cocoon.environment.Response;

import pss.common.security.BizUsuario;
import pss.www.ui.controller.JWebClientConfiguration;


public class JSignPageGenerator extends JXMLComponentGenerator {


	@Override
	protected String getBaseContentName() {
		return "signer";
	}

	static long count =(new Random()).nextLong();

	
	public Serializable getKey() {
		if (this.getSession()==null) return null;

		return this.getSession().getId();
	}

	@Override
	protected void doGenerate() throws Exception {
    this.startNode("message");
		this.setAttribute("type", "signer");
		this.addTextNode("text", BizUsuario.getCurrentUser());
		this.endNode("message");
	}

	@Override
	protected void setResponseHeaders(Response zResponse) throws Exception {
			if (JWebClientConfiguration.getCurrent().acceptsGZip()) {
				zResponse.addHeader("Content-Encoding", "gzip");
			}
			super.setResponseHeaders(zResponse);

	}

}
