/*
 * Created on 20-jun-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

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

public class JPssIconProvider extends ResourceReader {

	/**
	 * Setup the reader. The resource is opened to get an <code>InputStream</code>, the length and the last modification date
	 */
	@Override
	@SuppressWarnings("unchecked")
	public void setup(SourceResolver resolver, Map objectModel, String zIconFileName, Parameters par) throws ProcessingException, SAXException, IOException {
		// String sPssRelativePath = null;
		// try {
		// resolve the application, context and session for the request
		// JWebApplication oApplication =
		// JWebServer.getInstance().getWebApplication(ObjectModelHelper.getContext(objectModel));
		// JWebApplicationContext oContext = (JWebApplicationContext)
		// oApplication.getApplicationContext();
		// JWebApplicationSession oSession =
		// oApplication.getWebApplicationSession(ObjectModelHelper.getRequest(objectModel));
		// sPssRelativePath =
		// oContext.getSkinProvider().getSkinFor(oSession).getRelativePathForIcon(zIconFileName);
		// } catch (Exception e) {}
		// if (sPssRelativePath==null) {
		String sPssRelativePath=JPath.PssPathResource()+"/core/ui/Images/"+zIconFileName;
		// sPssRelativePath =JTools.PssPathResource()+"/core/ui/images/" +
		// zIconFileName;
		// sPssRelativePath
		// ="E:/desarrollo/JavaWorkspace/Tm4/webcontent/WEB-INF/classes/pss/core/ui/images/"
		// + zIconFileName;
		// }
//		this.setLastModifiedHeader(ObjectModelHelper.getResponse(objectModel),
		// sPssRelativePath);

		this.setResponseHeader(ObjectModelHelper.getResponse(objectModel));
		
		super.setup(resolver, objectModel, sPssRelativePath, par);
	}
	
	private void setResponseHeader(Response zResp) throws ProcessingException {
		zResp.addHeader("Access-Control-Allow-Origin", "*");
		zResp.addHeader("Access-Control-Allow-Headers", "*");
	}
	@SuppressWarnings( { "unchecked", "unused" })
	private void setLastModifiedHeader(Response zResp, String zIconPssRelativePath) throws ProcessingException {
		try {
			String sPath=JPath.PssPathResource()+"/core/ui/Images/"+zIconPssRelativePath;
			File oFile=new File(sPath);
			if (oFile.exists()) {
				Calendar oCal=Calendar.getInstance();
				oCal.add(Calendar.MONTH, 11);
				zResp.setDateHeader("Expires", oCal.getTimeInMillis());
				zResp.setDateHeader("Last-Modified", oFile.lastModified());
				zResp.setHeader("Cache-Control", "must-revalidate");
			}
		} catch (Exception e) {
			throw new ProcessingException(e);
		}
	}

}
