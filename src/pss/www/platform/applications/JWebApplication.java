package pss.www.platform.applications;

import java.io.File;

import javax.servlet.http.HttpSession;

import org.apache.cocoon.environment.Context;
import org.apache.cocoon.environment.Request;

import pss.core.data.BizPssConfig;
import pss.www.platform.actions.resolvers.JBasicWebActionResolver;
import pss.www.platform.content.compression.JClientContentCompressionManager;
import pss.www.platform.users.online.JStadistics;

public class JWebApplication extends JAbstractApplication implements JSessionedApplication {

  private Context oContext;
  private String sFullAppName;
  private String sWebAppName;
  private JStadistics stadistics;
  

  protected JWebApplication(Context zContext, String zWebAppName) {
    this.oContext = zContext;
    this.sWebAppName = zWebAppName;
    this.sFullAppName = "Pss.web.app[" + zWebAppName + "]";
  }


  
  
  
  protected void initialize() throws Exception {
    this.compactStylesheets();
    stadistics = new JStadistics();
    stadistics.initialize();
  }

  public String getWebApplicationName() {
    return this.sWebAppName;
  }


  @Override
	public final String getName() {
    return this.sFullAppName;
  }

  @Override
	public String getType() {
    return "web.application";
  }
  
  
  @Override
	protected JAbstractApplicationContext createApplicationContext() {
    return new JWebApplicationContext(this, this.oContext);
  }

  @Override
	protected void doStartUp(String[] args) throws Throwable {
    throw new RuntimeException("Web application should only be started by the Pss Web Server");
  }

//
//  public JWebApplicationSession getWebApplicationSession(Request zRequest) throws Exception {
//  	JWebApplicationSession oSession = null;
//    if( zRequest != null ) {
//    	HttpSession oServletSession = zRequest.getSession(false);
//      if( oServletSession != null) {
//        String sSessionId = JBasicWebActionResolver.getSessionID(zRequest,oServletSession);
//        if( this.getSessionManager().hasSessionWithId(sSessionId,zRequest.getParameter("subsession")) ) {
//          oSession = this.getSessionManager().getSession(zRequest.getSession(),sSessionId,zRequest.getParameter("subsession"));
//        } 
//      }
//    }
//    return oSession;
//  }


  private void compactStylesheets() throws Exception {
    String sPath = this.getApplicationContext().getHomePrecededPath("resources/stylesheets");
    this.compactStylesheetsIn(new File(sPath));
  }
  
  public JStadistics getStadistics() {
		return stadistics;
	}






	private void compactStylesheetsIn(File zDir) throws Exception {
    File[] aChildren = zDir.listFiles();
    File oChild;
    if (aChildren==null) return;
    for (int i = 0; i < aChildren.length; i++) {
      oChild = aChildren[i];
      if (oChild.isDirectory()) {
        this.compactStylesheetsIn(oChild);
      } else if (oChild.getName().toLowerCase().endsWith(".xsl")) {
        this.compactStylesheet(oChild);
      }
    }
  }
  
  private void compactStylesheet(File zStylesheetFile) throws Exception {
    String sOriginalPath = zStylesheetFile.getAbsolutePath();
    int iLastDotIndex = sOriginalPath.lastIndexOf('.');
 //   String sManagedFileName;
    if (iLastDotIndex != -1) {
      String sType = sOriginalPath.substring(iLastDotIndex+1);
      JClientContentCompressionManager.getInstance().compress(sOriginalPath, sType, this);
    }
  }

}
