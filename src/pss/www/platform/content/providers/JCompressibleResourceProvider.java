/*
 * Created on 20-jun-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.platform.content.providers;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
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
import pss.core.tools.PssLogger;
import pss.www.platform.applications.JSessionedApplication;
import pss.www.platform.applications.JWebServer;
import pss.www.platform.content.compression.JClientContentCompressionManager;

public class JCompressibleResourceProvider extends ResourceReader {

	/**
	 * Setup the reader. The resource is opened to get an <code>InputStream</code>, the length and the last modification date
	 */
	@Override
	@SuppressWarnings("unchecked")
	public void setup(SourceResolver resolver, Map objectModel, String src, Parameters par) throws ProcessingException, SAXException, IOException {
		try {
			JSessionedApplication oApp=JWebServer.getInstance().getWebApplication(ObjectModelHelper.getContext(objectModel));
			String sPath=oApp.getApplicationContext().getHomePrecededPath("resources/"+JPath.normalizePath(src));
			int iLastDotIndex=sPath.lastIndexOf('.');
			String sManagedFileName;
			if (iLastDotIndex!=-1) {
				String sType=sPath.substring(iLastDotIndex+1);
				sManagedFileName=JClientContentCompressionManager.getInstance().getManagedFilePath(sPath, sType, oApp);
			} else {
				sManagedFileName=sPath;
			}
			this.setResponseHeader(ObjectModelHelper.getResponse(objectModel));
			super.setup(resolver, objectModel, sManagedFileName, par);
		} catch (Exception e) {
			PssLogger.logError(e);
			throw new ProcessingException(e);
		}
	}
	@Override
	public Serializable getKey() {
//		try {
//			if (((String)super.getKey()).startsWith("file:")) {
//				File oFile= new File(((String)super.getKey()).substring(8));
//				return ((String)super.getKey())+"_"+ oFile.lastModified();
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		return super.getKey();
	}
	
	private void setResponseHeader(Response zResp) throws ProcessingException {
		zResp.setHeader("Cache-Control", "must-revalidate");
		zResp.addHeader("Access-Control-Allow-Origin", "*");
		zResp.addHeader("Access-Control-Allow-Headers", "*");
	}
	
	@SuppressWarnings( { "unchecked", "unused" })
	private void setLastModifiedHeader(Response zResp, String src) throws ProcessingException {
		try {
			JSessionedApplication oApp=JWebServer.getInstance().getWebApplication(ObjectModelHelper.getContext(objectModel));
			String sPath=oApp.getApplicationContext().getHomePrecededPath("resources/"+JPath.normalizePath(src));
			File oFile=new File(sPath);
			if (oFile.exists()) {
				Calendar oCal=Calendar.getInstance();
				oCal.add(Calendar.DAY_OF_MONTH, 1);
				zResp.setDateHeader("Expires", oCal.getTimeInMillis());
				zResp.setDateHeader("Last-Modified", oFile.lastModified());
				zResp.setHeader("Cache-Control", "must-revalidate");

				
			}
		} catch (Exception e) {
			throw new ProcessingException(e);
		}
	}

}
