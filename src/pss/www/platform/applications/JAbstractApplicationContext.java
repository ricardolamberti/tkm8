package pss.www.platform.applications;

import java.net.MalformedURLException;
import java.net.URL;

import pss.JPath;
import pss.core.tools.PssLogger;
import pss.core.tools.io.JTransientFileSystem;

public abstract class JAbstractApplicationContext implements JApplicationContext {

  private String sHomePath;
  private JApplication oApplication;
  
  
  public JAbstractApplicationContext(JApplication zApplication) {
    this.oApplication = zApplication;
  }
  
  public JApplication getApplication() {
    return this.oApplication;
  }


  public String getHomePath() {
    if (this.sHomePath==null) {
      try {
        this.sHomePath = JPath.normalizePath(this.resolveHomePath());   
  	  	
      } catch (Exception e) {
//        PssLogger.logError(this.oApplication.getName(), e, "Could not resolve the application home path");
      	PssLogger.logError(e, "Could not resolve the application home path");
      }
    }
    return this.sHomePath;
  }
  
  public String getHomeRelativePath(String zAbsolutePath) {
    String sNormPath = JPath.normalizePath(zAbsolutePath);
    if (!sNormPath.toUpperCase().startsWith(this.getHomePath().toUpperCase())) {
      throw new RuntimeException("Given absolute path is not a child path of the home path");
    } 
    return JPath.normalizePath(sNormPath.substring(this.getHomePath().length()));
  }

  public String getHomePrecededPath(String zRelativePathToPrepend) {
    String sNormPath = JPath.normalizePath(zRelativePathToPrepend);
    return this.getHomePath() + "/" + sNormPath;
  }
  
  public JTransientFileSystem getTransientFileSystem() {
    return JTransientFileSystem.getInstance(this.getWorkingDirectory() + "/transient");
  }



 
  
  //
  //  METHODS TO IMPLEMENT IN SUBCLASSES
  //
  
  protected abstract String resolveHomePath() throws Exception;
  public abstract String getWorkingDirectory();
  public abstract URL getResource(String zResourceURL) throws MalformedURLException;

}
