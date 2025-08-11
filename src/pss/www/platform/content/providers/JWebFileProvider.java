/*
 * Created on 20-jun-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.platform.content.providers;

import java.io.IOException;
import java.util.Map;

import org.apache.avalon.framework.parameters.Parameters;
import org.apache.cocoon.ProcessingException;
import org.apache.cocoon.environment.ObjectModelHelper;
import org.apache.cocoon.environment.SourceResolver;
import org.apache.cocoon.environment.http.HttpResponse;
import org.apache.cocoon.reading.ResourceReader;
import org.xml.sax.SAXException;

import pss.JPath;

public class JWebFileProvider extends ResourceReader {

	/**
	 * Setup the reader. The resource is opened to get an <code>InputStream</code>, the length and the last modification date
	 */
	@Override
	@SuppressWarnings("unchecked")
	public void setup(SourceResolver resolver, Map objectModel, String src, Parameters par) throws ProcessingException, SAXException, IOException {
		try {

			
			String sFileName=JPath.PssPathTempFiles()+"/"+src;
			HttpResponse resp = (HttpResponse)ObjectModelHelper.getResponse(objectModel);
			resp.setHeader("x-response-url", "file/"+src);
			if (this.mustDownload(sFileName)) {
				resp.setContentType("application/x-download");
				resp.setHeader("Content-Disposition", "attachment; filename=" + src);
				resp.addHeader("Access-Control-Allow-Origin", "*");
				resp.addHeader("Access-Control-Allow-Headers", "*");
			}
			super.setup(resolver, objectModel, sFileName, par);
		} catch (Exception e) {
			throw new ProcessingException(e);
		}
	}
	
	private boolean mustDownload(String file) throws Exception {
		if (file.toLowerCase().startsWith("download__")) return true;
		if (file.toLowerCase().endsWith(".io")) return true;
		if (file.toLowerCase().endsWith(".txt")) return true;
		return false;
		
	}

}
