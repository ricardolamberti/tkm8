package pss.www.platform.transformer;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.Map;

import org.apache.avalon.framework.parameters.Parameters;
import org.apache.cocoon.ProcessingException;
import org.apache.cocoon.environment.ObjectModelHelper;
import org.apache.cocoon.environment.Request;
import org.apache.cocoon.environment.SourceResolver;
import org.apache.cocoon.transformation.TraxTransformer;
import org.xml.sax.SAXException;

import pss.core.tools.JTools;
import pss.www.platform.actions.JWebActionFactory;
import pss.www.platform.applications.JWebApplication;
import pss.www.platform.applications.JWebApplicationContext;
import pss.www.platform.applications.JWebApplicationSession;
import pss.www.platform.applications.JWebServer;
import pss.www.platform.users.history.BizUserHistory;

public class JWebTransformer extends TraxTransformer {
	
	JWebApplication oApplication;
	JWebApplicationContext oContext;
//	JWebApplicationSession oSession;
	 private WeakReference<JWebApplicationSession> weakSession;
	  
	  public JWebApplicationSession getSession()  {
	  	if (weakSession==null) return null;
	  	JWebApplicationSession session = weakSession.get() ; 
	  	return session;
	  }

	  void setSession(JWebApplicationSession s ) {
	  	weakSession = new WeakReference<JWebApplicationSession>(s);
	  }
	  
@Override
public void endDocument() throws SAXException {
	// TODO Auto-generated method stub
	super.endDocument();
		try {
			endTransformer();
		} catch (Exception e) {
		}
	}

@Override
	public void setup(SourceResolver resolver, Map objectModel, String source, Parameters parameters) throws ProcessingException, SAXException, IOException {
		super.setup(resolver, objectModel, source, parameters);

		try {
			Thread.currentThread().setName(this.getClass().getSimpleName());
			Request oServletReq=ObjectModelHelper.getRequest(objectModel);
			this.oApplication=JWebServer.getInstance().getWebApplication(ObjectModelHelper.getContext(objectModel));
			this.oContext=(JWebApplicationContext) oApplication.getApplicationContext();
//			this.setSession(oApplication.getWebApplicationSession(ObjectModelHelper.getRequest(objectModel)));
			JWebActionFactory.getCurrentRequest().setRequestid(parameters.getParameter("requestid"));
		} catch (Throwable e) {
//			this.logError(e, "Error on transformer  of []");
//			throw e;
		}
}	
		private void endTransformer() throws Exception {
			if (this.getSession()==null) return;
			BizUserHistory last = this.getSession().getStadistic(JTools.getLongNumberEmbedded(JWebActionFactory.getCurrentRequest().getRequestid()));
			if (last==null) return;
			last.setTimeTransformer(last.getDelta());
		}

	


}
