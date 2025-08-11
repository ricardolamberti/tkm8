package pss.core.tools;

import java.io.IOException;

public class JBrowserControl {	
  // The default system browser.
  private static final String WIN_PATH = "rundll32";
  // The flag to display a url.
  private static final String WIN_FLAG = "url.dll,FileProtocolHandler";
  

	/**
   * Display a file in the system browser.  If you want to display a
   * file, you must include the absolute path name.
   *
   * @param url the file's url (the url must start with either "http://" or "file://").
   */
  public static void displayURL(String url)
  {
      String cmd = null;
      try{
          // cmd = 'rundll32 url.dll,FileProtocolHandler http://...'
          cmd = WIN_PATH + " " + WIN_FLAG + " " + url;
          Runtime.getRuntime().exec(cmd);
      }
      catch(IOException x){
          // couldn't exec browser
      	PssLogger.logDebug("No se pudo lanzar abrir la URL especificada: " + cmd);
      	PssLogger.logDebug("Caught: " + x);
      }
  }
  
 
  public static void main(String[] args)
  {
      displayURL("http://10.28.121.75/tmtrack/tmtrack.dll?");
  }
  
}
