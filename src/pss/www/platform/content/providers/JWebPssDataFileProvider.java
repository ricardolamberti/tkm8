	package pss.www.platform.content.providers;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Map;

import org.apache.avalon.framework.parameters.Parameters;
import org.apache.cocoon.ProcessingException;
import org.apache.cocoon.environment.ObjectModelHelper;
import org.apache.cocoon.environment.Response;
import org.apache.cocoon.environment.SourceResolver;
import org.apache.cocoon.reading.ResourceReader;
import org.xml.sax.SAXException;

import pss.JPath;


public class JWebPssDataFileProvider extends ResourceReader {

	/**
	 * Setup the reader. The resource is opened to get an <code>InputStream</code>, the length and the last modification date
	 */
	@Override
	@SuppressWarnings("unchecked")
	public void setup(SourceResolver resolver, Map objectModel, String src, Parameters par) throws ProcessingException, SAXException, IOException {
	 try {
		 String sRelativeSrc=JPath.normalizePath(src); 
		 this.setLastModifiedHeader(ObjectModelHelper.getResponse(objectModel), sRelativeSrc);
		 super.setup(resolver, objectModel, JPath.PssPathData()+"/"+sRelativeSrc, par);
		} catch (Exception e) {
		}
	}

	private void setLastModifiedHeader(Response zResp, String zRelativeSrc) throws ProcessingException {
		try {
			String sPath=JPath.PssPathData()+"/"+zRelativeSrc;
			File oFile=new File(sPath);
			if (oFile.exists()) {
				Calendar oCal=Calendar.getInstance();
				oCal.add(Calendar.MONTH, 11);
				zResp.setDateHeader("Expires", oCal.getTimeInMillis());
				zResp.setDateHeader("Last-Modified", oFile.lastModified());
				zResp.setHeader("Cache-Control", "must-revalidate");
				zResp.addHeader("Access-Control-Allow-Origin", "*");
				zResp.addHeader("Access-Control-Allow-Headers", "*");
			}
		} catch (Exception e) {
		//	throw new ProcessingException(e);
		}
	}

}
