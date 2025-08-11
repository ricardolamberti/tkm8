package pss.www.platform.content.providers;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Map;

import org.apache.avalon.framework.parameters.Parameters;
import org.apache.cocoon.ProcessingException;
import org.apache.cocoon.environment.ObjectModelHelper;
import org.apache.cocoon.environment.Request;
import org.apache.cocoon.environment.Response;
import org.apache.cocoon.environment.SourceResolver;
import org.apache.cocoon.reading.ResourceReader;
import org.xml.sax.SAXException;

import pss.common.security.BizUsuario;
import pss.core.JAplicacion;
import pss.core.data.BizPssConfig;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;

public class JPssPublicWebProvider extends ResourceReader {

	static JMap<String,String> cache = JCollectionFactory.createMap();
	/**
	 * Setup the reader. The resource is opened to get an <code>InputStream</code>, the length and the last modification date
	 */
	@Override
	@SuppressWarnings("unchecked")
	public void setup(SourceResolver resolver, Map objectModel, String zRequest, Parameters par) throws ProcessingException, SAXException, IOException {

		String sPssRelativePath=cache.getElement(zRequest);
		if (sPssRelativePath==null) {
			try { 
		//		if (zRequest.startsWith("qr")) return ;
				Request req =  ObjectModelHelper.getRequest(objectModel);
				Map params = req.getParameters();
				JAplicacion.openSession();
				JAplicacion.GetApp().openApp("publicweb", JAplicacion.AppService(), true);
				BizUsuario usr = new BizUsuario();
				usr.Read(BizPssConfig.getPssConfig().getPublicWebLogin());
				BizUsuario.SetGlobal(usr);
				sPssRelativePath = usr.getObjCompany().getObjBusiness().getResourcePublicWeb(resolver, objectModel, zRequest, params);
				if (sPssRelativePath==null) {
					ObjectModelHelper.getResponse(objectModel).sendError(400);
				}	else {
					this.setLastModifiedHeader(ObjectModelHelper.getResponse(objectModel), sPssRelativePath);
					cache.addElement(zRequest, sPssRelativePath);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		super.setup(resolver, objectModel, sPssRelativePath, par);
	}
	private void setLastModifiedHeader(Response zResp, String zRelativeSrc) throws ProcessingException {
    try {
      String sPath=zRelativeSrc;
      File oFile=new File(sPath);
      if (oFile.exists()) {
        Calendar oCal=Calendar.getInstance();
        oCal.add(Calendar.DAY_OF_MONTH, 1);
        zResp.setDateHeader("Expires", oCal.getTimeInMillis());
        zResp.setDateHeader("Last-Modified", oCal.getTimeInMillis());
        zResp.setHeader("Cache-Control", "must-revalidate");
				zResp.addHeader("Access-Control-Allow-Origin", "*");
				zResp.addHeader("Access-Control-Allow-Headers", "*");
    }
    } catch (Exception e) {
      throw new ProcessingException(e);
    }
  
	}
}
