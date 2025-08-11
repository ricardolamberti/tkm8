/*
 * Created on 05-jun-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.platform.content.generators;

import java.io.Serializable;

import org.apache.cocoon.caching.CacheableProcessingComponent;
import org.apache.cocoon.environment.Response;
import org.apache.excalibur.source.SourceValidity;

import pss.www.platform.content.JSesionCacheValidity;
import pss.www.ui.processing.JXMLRepresentable;

public abstract class JXMLComponentGenerator extends JBasicXMLContentGenerator implements JXMLRepresentable, CacheableProcessingComponent {

	public void toXML(JXMLContent zContent) throws Exception {
		((JBasicXMLContentGenerator) zContent.getGenerator()).embed(this);
	}

	public void destroy() {
		super.cleanUp();
	}

	@Override
	protected void setResponseHeaders(Response zResponse) throws Exception {
		/*
		  if (JWebClientConfiguration.getCurrent().acceptsGZip()) {
		  	zResponse.addHeader("Content-Encoding", "gzip"); 
		  	}
		 */
		super.setResponseHeaders(zResponse);

	}

	public Serializable getKey() {
		if (this.getSession()==null) return null;

		return this.getSession().getId();
	}

	public SourceValidity getValidity() {
		return new JSesionCacheValidity();
	}
	
	
}
