/*
 * Created on 05-jun-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.platform.applications;

/**
 * The Pss Web Server Application, which currently uses Tomcat to run.
 *
 * Created on 05-jun-2003
 * @author PSS
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;

import javax.xml.transform.TransformerException;

import org.apache.cocoon.environment.Context;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.net.URLCodec;
import org.apache.xpath.XPathAPI;
import org.apache.xpath.objects.XNodeSet;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import pss.JPath;
import pss.core.tools.JExcepcion;
import pss.core.tools.JOutputStreamNull;
import pss.core.tools.JTools;
import pss.core.tools.PssLogger;
import pss.core.tools.XML.JXMLElementFactory;
import pss.core.tools.XML.JXMLSerializer;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;

public class JWebServer extends JAbstractApplication {

  //
  // the map of web applications, whose id are the name of the directories they
  // are contained in
  //
  private JMap<Context, JWebApplication> oWebAppMap;


  public JWebServer() {
    this.oWebAppMap = JCollectionFactory.createMap();
  }
  
  public static void main(String[] args) throws Exception {
  	try {
  		JWebServer server = new JWebServer();
  		server.startUp(args);
  	} catch ( Throwable e) {
  		System.out.print(e.getMessage());
  	}
  }

  public static JWebServer getInstance() {
    if (INSTANCE==null) {
      // The INSTANCE may be null if the Tomcat Container was started by a
      // parent Web Server -like Microsoft IS- instead of being started by the
      // JPss class' main method
      INSTANCE = new JWebServer();
    }
    return (JWebServer) INSTANCE;
  }


  public synchronized JWebApplication getWebApplication(Context zContext) throws Exception {
    if (zContext==null && this.oWebAppMap.size()!=0) {
    	return  this.oWebAppMap.getElement( this.oWebAppMap.getKeyIterator().nextElement());
    }
  	if (zContext==null) return null;

    JWebApplication oApp=this.oWebAppMap.getElement(zContext);
    if (oApp==null) {
      String sFullPath = JPath.normalizePath(zContext.getRealPath("/"));
  		JPath.setsWorkingDirectory(zContext.getAttribute("javax.servlet.context.tempdir").toString());
      int iLastSepIndex = sFullPath.lastIndexOf('/');
      String sAppName;
      if (iLastSepIndex != -1) {
        sAppName = sFullPath.substring(iLastSepIndex + 1);
      } else {
        sAppName = sFullPath;
      }
      
      String sURLPath = JPath.normalizePath(zContext.getResource("/").getFile());
      iLastSepIndex = sURLPath.lastIndexOf('/');
      String sAppURL;
      if (iLastSepIndex != -1) {
        sAppURL = sURLPath.substring(iLastSepIndex + 1);
      } else {
        sAppURL = sURLPath;
      }
      
      oApp = new JWebApplication(zContext, sAppName);
      PssLogger.logDebug(" Initializing web application: " +  sAppURL );
      //MessageManager.initializeMessageManager();
      oApp.initialize();
      this.oWebAppMap.addElement(zContext, oApp);
    }
    return oApp;
  }




  @Override
	protected void doStartUp(String[] args) throws Throwable {
    System.out.println();
    System.out.println("================================================");
    System.out.println("|   Starting Pss.web(R) application server    |");
    System.out.println("================================================");
    System.out.println(" >> Using Apache Tomcat HTTP implementation");

    if (args.length > 0 && args[0].equalsIgnoreCase("-noout")) {
      System.out.println(" >> Using -noout option. Closing standard output and standard error streams.");
      System.out.println();
      System.out.close();
      System.setOut(new PrintStream(new JOutputStreamNull(), true));
      System.err.close();
      System.setErr(new PrintStream(new JOutputStreamNull(), true));
    } else {
      System.out.println();
    }


    // resolve paths
    String sTomcatHome = JPath.normalizePath(this.deduceTomcatHomePath());
    String sTomcatConfigHomeFromPss = JPath.PssPathData() + "/tomcat.config";
    String sTomcatConfigHome = sTomcatHome + "/conf";
    String sWebappsPath = this.deduceWebappsPath();
    
    //
    //  deploy Tomcat configuration files which are under /PssData folder;
    //  they must be deployed to Tomcat's /conf sub-folder
    //
    this.deployTomcatConfigFiles(sTomcatConfigHomeFromPss, sTomcatConfigHome);
    

    //
    //  update configuration in Tomcat's server.xml file, at runtime
    //
    // load document
    String sServerXMLFile = sTomcatConfigHome + "/server.xml";
    URL fileURL = new URL("file://" + sServerXMLFile);
    Document oDocument = JXMLElementFactory.getInstance().parseURIToDocument(fileURL.getFile());
    // configure tomcat home in server.xml file
    this.setWebappsPath(oDocument, sWebappsPath);
    // configure logs dir in server.xml file
    this.setTomcatLogsPath(oDocument);
    // serialize modified document
    this.serializeXMLFile(sServerXMLFile, oDocument);

    //
    //  update configuration in Cocoon webapps' logkit.xconf file, at runtime
    //
    this.setLogsPathInWebapps(sWebappsPath);

    //
    // start up Tomcat !!!!
    //
    System.setProperty("tomcat.home", sTomcatHome);
    System.setProperty("user.dir", sTomcatHome);
//    Catalina.main(new String[] {"-config", sServerXMLFile, "start"});
  }
  
  @Override
	public String getName() {
    return "Pss.web.server";
  }

  @Override
	public String getType() {
    return "web.server";
  }


  private void setWebappsPath(Document zDocument, String zWebappsPath) throws Throwable {
    XNodeSet oNodeSet = (XNodeSet) XPathAPI.eval(zDocument, "Server/Service[@name='Tomcat-Standalone']/Engine[@name='Standalone']/Host");
    Element oHostNode = (Element) oNodeSet.nodelist().item(0);
    oHostNode.setAttribute("appBase", zWebappsPath);
  }
  private void setTomcatLogsPath(Document zDocument) throws Throwable {
    String sNewLogPath = JPath.normalizePath(JPath.PssPathLog()) + "/tomcat";
    File tomcatLogDir = new File(sNewLogPath);
    if (!tomcatLogDir.exists()) {
      if (!tomcatLogDir.mkdirs()) {
        JExcepcion.SendError("Could not create Tomcat log dir: " + sNewLogPath);
      }
    }
    // generic logger
    XNodeSet oLoggerNodeSet = (XNodeSet) XPathAPI.eval(zDocument, "Server/Service[@name='Tomcat-Standalone']/Engine[@name='Standalone']/Logger");
    Element oLoggerNode = (Element) oLoggerNodeSet.nodelist().item(0);
    oLoggerNode.setAttribute("prefix", "Tomcat.generic.");
    oLoggerNode.setAttribute("suffix", ".log");
    oLoggerNode.setAttribute("directory", sNewLogPath);
    // localhost server logger
    oLoggerNodeSet = (XNodeSet) XPathAPI.eval(zDocument, "Server/Service[@name='Tomcat-Standalone']/Engine[@name='Standalone']/Host/Logger");
    oLoggerNode = (Element) oLoggerNodeSet.nodelist().item(0);
    oLoggerNode.setAttribute("prefix", "Tomcat.localhost.");
    oLoggerNode.setAttribute("suffix", ".log");
    oLoggerNode.setAttribute("directory", sNewLogPath);
  }
  private void setLogsPathInWebapps(String zWebappsPath) throws Exception {
    File[] oFiles = new File(zWebappsPath).listFiles();
    for (int i = 0; i < oFiles.length; i++) {
      if (oFiles[i].isDirectory()) {
        String sLogConfigFile = JPath.normalizePath(oFiles[i].getAbsolutePath()) + "/WEB-INF/logkit.xconf";
        if (new File(sLogConfigFile).exists()) {
          String sWebapp = oFiles[i].getName();
          this.setLogsPathInWebapp(sWebapp, sLogConfigFile);
        }
      }
    }
  }
  private void setLogsPathInWebapp(String zWebapp, String zLogConfigFile) throws Exception {
    String sNewLogPath = JPath.normalizePath(JPath.PssPathLog()) + "/cocoon/" + zWebapp;
    URL fileURL = new URL("file://" + zLogConfigFile);
    Document oDocument = JXMLElementFactory.getInstance().parseURIToDocument(fileURL.getFile());
    // update all nodes defining loggers
    XNodeSet oLoggerNodeSet = (XNodeSet) XPathAPI.eval(oDocument, "logkit/targets/cocoon");
    if (oLoggerNodeSet != null) {
      this.updateDirInTargets(sNewLogPath, oLoggerNodeSet);
    }
    oLoggerNodeSet = (XNodeSet) XPathAPI.eval(oDocument, "logkit/targets/priority-filter");
    if (oLoggerNodeSet != null) {
      this.updateDirInTargets(sNewLogPath, oLoggerNodeSet);
    }
    // serialize modified document
    this.serializeXMLFile(zLogConfigFile, oDocument);
  }
  private void updateDirInTargets(String zNewLogPath, XNodeSet zLogTargetNodeSet) throws TransformerException {
    Element oLogTargetNode;
    Element oLogTargetNodeFilenameChild;
    Node oLogTargetNodeFilenameChildTextNode;
    String sLogTargetFilename;
    String sLogTargetNewFilename;
    NodeList oList = zLogTargetNodeSet.nodelist();
    for (int i = 0; i < oList.getLength(); i++) {
      oLogTargetNode = (Element) oList.item(i);
      NodeList oTempList = oLogTargetNode.getElementsByTagName("filename");
      if (oTempList != null && oTempList.getLength() > 0) {
        oLogTargetNodeFilenameChild = (Element) oTempList.item(0);
        oLogTargetNodeFilenameChildTextNode = oLogTargetNodeFilenameChild.getFirstChild();
        if (oLogTargetNodeFilenameChildTextNode != null) {
          sLogTargetFilename = JPath.normalizePath(oLogTargetNodeFilenameChildTextNode.getNodeValue());
          sLogTargetNewFilename = zNewLogPath + "/" + sLogTargetFilename.substring(sLogTargetFilename.lastIndexOf('/') + 1);
          oLogTargetNodeFilenameChildTextNode.setNodeValue(sLogTargetNewFilename);
        }
      }
    }
  }


  private void serializeXMLFile(String zServerXMLFile, Document zDocument) throws Exception, IOException {
    // serialize to a temp file...
    String sFile = zServerXMLFile;
    String sTempFile = zServerXMLFile + "_ztemp";
    String sBackupFile = zServerXMLFile + "_zbackup";
    JXMLSerializer oSer = new JXMLSerializer();
    FileWriter oFW = new FileWriter(sTempFile); // write to a temp file
    BufferedWriter oWriter = new BufferedWriter(oFW);
    oSer.serialize(oWriter, zDocument);
    oWriter.close();
    oFW.close();
    // ...then rename file
    File oFile = new File(sFile);
    File oTempFile = new File(sTempFile);
    File oBackupFile = new File(sBackupFile);
    if (oBackupFile.exists()) {
      if (!oBackupFile.delete()) {
        this.couldntUpdateTomcatFile();
      }
    }
    if (!oFile.renameTo(oBackupFile)) {
      this.couldntUpdateTomcatFile();
    }
    if (!oTempFile.renameTo(oFile)) {
      oBackupFile.renameTo(oFile);
      this.couldntUpdateTomcatFile();
    }
    if (!oBackupFile.delete()) {
      this.couldntUpdateTomcatFile();
    }
  }

  private void couldntUpdateTomcatFile() throws Exception {
    JExcepcion.SendError("No se pudo actualizar archivo de configuración de Tomcat");
  }

  private String deduceWebappsPath() throws Exception {
    String sWebServerPath = JPath.normalizePath(this.getClass().getResource("").getPath());
    sWebServerPath = sWebServerPath.substring(0, sWebServerPath.lastIndexOf('/'));
    sWebServerPath = (new URLCodec()).decode(sWebServerPath);

    String sWebappsPath = sWebServerPath.substring(0, sWebServerPath.lastIndexOf('/')) + "/webapps";
    File oFile = new File(sWebappsPath);
    if (!oFile.exists() || !oFile.isDirectory()) {
      JExcepcion.SendError("Cannot find webapps directory: " + sWebappsPath);
    }
    return sWebappsPath;
  }

  private String deduceTomcatHomePath() {
    String sWebServerPath = JPath.normalizePath(this.getClass().getResource("").getPath());
    sWebServerPath = sWebServerPath.substring(0, sWebServerPath.lastIndexOf('/'));
    try {
			sWebServerPath = (new URLCodec()).decode(sWebServerPath);
		} catch (DecoderException e) {
			PssLogger.logDebug(e);
		}
    return sWebServerPath.substring(0, sWebServerPath.lastIndexOf('/')) + "/tomcat";
  }


  private void deployTomcatConfigFiles(String zTomcatConfigHomeFromPss, String zTomcatConfigHome) throws Exception {
    File src = new File(zTomcatConfigHomeFromPss);
    if (!src.exists() || !src.isDirectory()) {
      return;
    }
    File[] srcChildren = src.listFiles();
    if (srcChildren==null || srcChildren.length < 1) {
      return;
    }
    File tgt = new File(zTomcatConfigHome);
    tgt.mkdirs();
    if (!tgt.exists() || !tgt.isDirectory()) {
      JExcepcion.SendError("Could not create dir: " + zTomcatConfigHome);
    }
    for (int i = 0; i < srcChildren.length; i++) {
      File child = srcChildren[i];
      String sNameAddin = "/" + child.getName();
      if (child.isDirectory()) {
        this.deployTomcatConfigFiles(zTomcatConfigHomeFromPss + sNameAddin, zTomcatConfigHome + sNameAddin);  
      } else {
        File target = new File(zTomcatConfigHome + sNameAddin);
        File targetBackup = null;
        if (target.exists()) {
          targetBackup = new File(target.getAbsolutePath() + ".backup.temp");
          targetBackup.delete();
          if (!target.renameTo(targetBackup)) {
            JExcepcion.SendError("Could not create temp backup file: " + targetBackup.getAbsolutePath());
          }
        }
        try {
          JTools.copyFile(child.getAbsolutePath(), target.getAbsolutePath());
          if (targetBackup != null && !targetBackup.delete()) {
            PssLogger.logDebug("Could not delete temp backup file: " + targetBackup.getAbsolutePath());
          }
        } catch (Throwable e) {
          PssLogger.logError(e, "Copy failed from " + child.getAbsolutePath() + " to " + target.getAbsolutePath() + "; recovering previous target file from temp backup: " + targetBackup.getAbsolutePath());
          if (target.exists() && !target.delete()) {
            PssLogger.logError("Could not recover file from temp backup: " + child.getAbsolutePath());
          } 
          if (targetBackup != null && !targetBackup.renameTo(target)) {
            PssLogger.logError("Could not recover file from temp backup: " + child.getAbsolutePath());
          }
        }
        
      }
    }
  }



  @Override
	protected JAbstractApplicationContext createApplicationContext() {
    return new JWebServerContext(this);
  }

}
