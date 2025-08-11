package pss.www.platform.applications;

import java.net.MalformedURLException;
import java.net.URL;

import pss.JPath;
import pss.JPss;


public class JWebServerContext extends JAbstractApplicationContext {

  JWebServerContext(JWebServer zApplication) {
    super(zApplication);
  }

  @Override
	protected String resolveHomePath() throws Exception {
    return JPath.PssPathBin();
  }
  @Override
	public URL getResource(String zResourceURL) throws MalformedURLException {
    return JPss.class.getResource( zResourceURL );
  }

  @Override
	public String getWorkingDirectory() {
    return this.getHomePrecededPath("work");
  }
  
  public String getWebappRelativePath(String zAbsolutePath) {
    return this.getHomeRelativePath(zAbsolutePath);
  }  

}
