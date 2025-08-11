/*
 * Created on 26-jun-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.platform.applications;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * 
 * 
 * Created on 26-jun-2003
 * @author PSS
 */

public interface JApplicationContext {
  public abstract JApplication getApplication();
  public abstract String getHomePath();
  public abstract String getHomeRelativePath(String zAbsolutePath);
  public abstract String getHomePrecededPath(String zRelativePathToPrepend);
  public abstract URL getResource(String zResourceURL) throws MalformedURLException;
  public abstract String getWebappRelativePath(String zAbsolutePath);
}
