package pss;

import java.io.File;
import java.net.URLDecoder;

import pss.core.data.BizPssConfig;



public class JPath {
	

public static String sPssPath = null;
  private static String sPssMainPath = null;
  
  private static String sPssDataPath = null;
  
  private static boolean checkDataPath = false;
  
  private static String inputDir = null;

	public static String PssPath() {
	  try {
	    if (sPssPath != null) return sPssPath;
	
	    String sJPssClassFileName = "JPath.class";
	
	    String sJPssClassFilePath =  JPath.class.getResource(sJPssClassFileName).getFile();
	    if (sJPssClassFilePath.indexOf("!")!=-1) { // is jar
	    	sPssPath =  getsWorkingDirectory();
	    } else {
	    	sPssPath = sJPssClassFilePath.substring(0, sJPssClassFilePath.lastIndexOf(sJPssClassFileName));
	    }
      int idx = sPssPath.substring(0, sPssPath.length() - 1).lastIndexOf("/");
    	
      if (idx!=-1) {
		    if (System.getProperty("os.name").startsWith("Windows"))
		      sPssPath = sPssPath.substring(1, idx);
		    else
		      sPssPath = sPssPath.substring(0, idx); // en POSIX los paths empiezan con barra
	      }
      return sPssPath;
	  } catch (Exception e) {
	    return null;
	  }
	}
	public static boolean isJar() {
			return PssMainPath().indexOf("!")!=-1;
	}

	public static String PssMainPath() {
	  if (sPssMainPath != null) return sPssMainPath;
//    if (System.getProperty("os.name").startsWith("Windows"))
//    	sPssMainPath = JPath.class.getResource("").getFile().indexOf("!")!=-1?JPath.class.getResource("").getFile(): JPath.class.getResource("").getFile().substring(1);
//    else
//    	sPssMainPath = JPath.class.getResource("").getFile();
 // 	JDebugPrint.logDebug("sPssMainPath: "+sPssMainPath );
	  String path  = JPath.class.getResource("").toString();
	 // if (path.endsWith("/") || path.endsWith("\\") ) path=path.substring(0,path.length()-1);
	  return sPssMainPath = path;
	}

	  public static String sWorkingDirectory = null;
	  
		public static String getsWorkingDirectory() {
			return sWorkingDirectory;
		}

		public static void setsWorkingDirectory(String sWorkingDirectory) {
			JPath.sWorkingDirectory = sWorkingDirectory;
		}
		public static String getJar() {
			if (!isJar()) return null;
			String name = PssMainPath();
			if (name.startsWith("jar:")) name = name.substring(4);
			if (name.startsWith("file:")) name = name.substring(5);
			name=name.substring(1, name.indexOf("!"));
			return name;
		}
		public static String PssPathResourceHtml() {
			String path = PssMainPath()+"../../../resources";
			if (path.startsWith("jar:")) path = path.substring(4);
			if (path.startsWith("file:")) path = path.substring(6);
			return path;
			
			
		}		
	public static String PssPathResource() {
	//    return PssMainPath()+"../../../resources/Pss";
			String p = PssMainPath();
			if (p.endsWith("/")) p = p.substring(0,p.length()-1);
	    return p;
	  }


	public static String PssPathJAR() throws Exception {
	  
		String path = JPath.class.getResource("").getPath();
		if (path.startsWith("jar:")) path = path.substring(4);
		if (path.startsWith("file:")) path = path.substring(5);
		path = URLDecoder.decode(path.substring(0, path.lastIndexOf("/lib")+4));
		return path;
	}


	// PATHs abajo de Pss
	public static String PssImages() throws Exception {
	  return PssMainPath() + "core/ui/Images";
	}
	
	
	public static String PssPathBin() throws Exception {
	  String path= PssMainPath() + "../../bin";
		if (path.startsWith("file:/")) path = path.substring(6);
		return path;
	
	}

	public static String PssPathGraph() throws Exception {
	  return PssMainPath() + "lib";
	}


	public static String PssPathInstallClient() throws Exception {
	  return PssMainPath() + "core/SetupBD/Tables";
	}


	public static String PssPathIntallServer() throws Exception {
	  return PssPath() + "/Versions";
	}


	// PATHs abajo de PssData
	public static String PssPathData() throws Exception {		
		if ( checkDataPath == false ) {			
				File f = new File(PssPath() + "/pssData");			
			if ( f.exists() ) 				
				JPath.sPssDataPath = PssPath() + "/pssData";			
			else {			
				f = new File(PssPath() + "/../pssData");
				if ( f.exists() ) 				
					JPath.sPssDataPath = PssPath() + "/../pssData";			
				else {				
					f = new File(PssPath() + "/../../pssData");
					if ( f.exists() ) 				
						JPath.sPssDataPath = PssPath() + "/../../pssData";			
					else {				
						f = new File(PssPath() + "/../../../pssData");
						if ( f.exists() ) 				
							JPath.sPssDataPath = PssPath() + "/../../../pssData";			
						else {				
							f = new File(PssPath() + "/../../../../pssData");
							if ( f.exists() ) 				
								JPath.sPssDataPath = PssPath() + "/../../../../pssData";			
							else {				
								// bin\WEB-INF\classes				
								JPath.sPssDataPath = PssPath() + "/../../../../../pssData";	
							}
						}		
					}		
				}		

			}
			checkDataPath = true;			

		}	  
		return JPath.sPssDataPath;	
	}

	public static String PssPathDataFiles() throws Exception {
		return JPath.PssPathData() + "/files";
	}
	
	public static String PssPathDataBiblo() throws Exception {
		return JPath.PssPathData() + "/biblo";
	}
	
	public static String PssPathOutput() throws Exception {
	  return JPath.PssPathData() + "/output";
	}
	public static String PssPathMarks() throws Exception {
	  return JPath.PssPathData() + "/marks";
	}


	public static String PssPathOutputBackup() throws Exception {
	  return PssPathData() + "/output/backup";
	}

	public static void setPssPathInput(String dir) {
		inputDir=dir;
	}

	public static String PssPathInput() throws Exception {
		if (inputDir!=null) return inputDir;
		String inputfile = BizPssConfig.getPssConfig().getInputFiles();
		return inputfile.equals("")?PssPathData() + "/input":inputfile;
	}
	public static String PssPathLogos() throws Exception {
	  return "files/logos/";
	}

	public static String PssPathReport() throws Exception {
	  return PssPathData() + "/report";
	}
	public static String PssPathFondos() throws Exception {
	  return PssPathData() + "/fondos";
	}
	public static String PssPathSprites() throws Exception {
	  return PssPathData() + "/sprites";
	}

	public static String PssPathInputOriginal() throws Exception {
	  return PssPathInput() + "/original";
	}


	public static String PssPathInputPending() throws Exception {
	  return PssPathInput() + "/pending";
	}


	public static String PssPathInputProcessed() throws Exception {
	  return PssPathInput() + "/processed";
	}


	public static String PssPathInputError() throws Exception {
	  return PssPathInput() + "/errors";
	}


	public static String PssPathInputZIP() throws Exception {
	  return PssPathInput() + "/zip";
	}

	static String tempFiles;
	public static String PssPathTempFiles() throws Exception {
		if (tempFiles!=null) return tempFiles;
		String tf = BizPssConfig.getPssConfig().getTempFiles();
		if (!tf.equals("")) return tempFiles=tf;
		return tempFiles=PssPathData() + "/tempFiles";
	}

	static String ioFilesLocal;
	public static String PssPathImportExportFilesLocal() throws Exception {
		if (ioFilesLocal!=null) return ioFilesLocal;
		String tf = BizPssConfig.getPssConfig().getImportExportFilesLocal();
		if (!tf.equals("")) return ioFilesLocal=tf;
		return ioFilesLocal=PssPathData() + "/tempFiles";
	}
	static String ioFilesRemote;
	public static String PssPathImportExportFilesRemote() throws Exception {
		if (ioFilesRemote!=null) return ioFilesRemote;
		String tf = BizPssConfig.getPssConfig().getImportExportFilesRemote();
		if (!tf.equals("")) return ioFilesRemote=tf;
		return ioFilesRemote=PssPathData() + "/tempFiles";
	}
	public static Boolean PssImportExportCopy() throws Exception {
		return BizPssConfig.getPssConfig().getImportExportCopy();
	}
	public static String PssPathOutputXMLs() throws Exception {
	  return PssPathOutput() + "/xmls";
	}


	public static String PssPathLog() throws Exception {
	  return PssPathData() + "/log";
	}


	public static String PssPathBackup() throws Exception {
	  return PssPathData() + "/backup";
	}
	// PATHs abajo de Pss
	public static String pssFonts() throws Exception {
	  return PssMainPath() + "core/ui/fonts";
	}

	/**
	 * Normalizes the given path applying the following rules: <li>replaces all
	 * '\' char by '/' <li>trims begin '/' <li>trims end '/'
	 * @param zPath the path to be normalized
	 * @return the normalized path
	 */
	public static String normalizePath(String zPath) {
	 if (zPath.startsWith("file:///"))
		 zPath = zPath.substring(8);
	 
		String sNormPath = zPath.replace('\\', '/');
	  while (sNormPath.startsWith("/") && (!sNormPath.startsWith("//")) && !sNormPath.startsWith("/home") && !sNormPath.startsWith("/Users")) {
	    	sNormPath = sNormPath.substring(1);
	  }
	  while (sNormPath.endsWith("/")) {
	    sNormPath = sNormPath.substring(0, sNormPath.length() - 1);
	  }
//  	JDebugPrint.logDebug("zPath: "+zPath  + "-> sNormPath: "+sNormPath );
	  return sNormPath;
	}

}
