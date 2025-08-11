/*
 * Created on 28-jul-2003 by PSS
 *
 * Copyright PuntoSur Soluciones 2003
 */

package pss.www.platform.content.compression;

import java.io.File;

import pss.JPath;
import pss.core.tools.JExcepcion;
import pss.core.tools.JTools;
import pss.core.tools.PssLogger;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JMap;
import pss.www.platform.applications.JSessionedApplication;

public class JClientContentCompressionManager {

	private static JClientContentCompressionManager INSTANCE=new JClientContentCompressionManager();

	private JMap<String, Class<? extends JClientContentCompressor>> oCompressors;

	JClientContentCompressionManager() {
		this.oCompressors=JCollectionFactory.createMap();
//		this.oCompressors.addElement("js", JJSCompressor.class);
		this.oCompressors.addElement("css", JCSSCompressor.class);
		this.oCompressors.addElement("xsl", JXSLCompressor.class);
	}

	//
	// public api
	//

	public static JClientContentCompressionManager getInstance() {
		return INSTANCE;
	}

	@SuppressWarnings("unchecked")
	public String getManagedFilePath(String zFilePath, String zType, JSessionedApplication zApp) throws Exception {
		Class oCompressorClass=this.oCompressors.getElement(zType);
		if (oCompressorClass==null) {
			return zFilePath;
		}
		File oRawFile=new File(JPath.normalizePath(zFilePath));
		String sManagedFileName=this.getManagedFileName(zFilePath,oRawFile, zApp);
		File oCompressedFile=new File(sManagedFileName);
		if (!oCompressedFile.exists()) {
			JClientContentCompressor oCompressor=(JClientContentCompressor) oCompressorClass.newInstance();
			oCompressor.compress(zFilePath, sManagedFileName);
		}
		return sManagedFileName;
	}

	@SuppressWarnings("unchecked")
	public void compress(String zFilePath, String zType, JSessionedApplication zApp) throws Exception {
		// compress...
		Class oCompressorClass=this.oCompressors.getElement(zType);
		if (oCompressorClass==null) {
			return;
		}
		File oRawFile=new File(zFilePath);
		String sManagedFileName=this.getManagedFileName(zFilePath,oRawFile, zApp);
		File oCompressedFile=new File(sManagedFileName);
		if (!oCompressedFile.exists()) {
			JClientContentCompressor oCompressor=(JClientContentCompressor) oCompressorClass.newInstance();
			oCompressor.compress(zFilePath, sManagedFileName);
		} else {
			return;
		}
		// then rename the original to backup
		String sBackupFileName=this.getBackupFileName(zFilePath,oRawFile, zApp);
		File oOriginalBackupFile=new File(sBackupFileName);
		if (oRawFile.exists()) {
			oOriginalBackupFile.delete();
			if (!oRawFile.renameTo(oOriginalBackupFile)) {
				JExcepcion.SendError("Could not backup file: "+zFilePath);
			}
		} else {
			JExcepcion.SendError("File does not exist: "+zFilePath);
		}
		// copy the compressed to original
		JTools.copyFile(sManagedFileName, zFilePath);
		// rename the compressed to the new last modification time
		String sNewManagedFileName=this.getManagedFileName(zFilePath,oRawFile, zApp);
		File oNewCompressedFile=new File(sNewManagedFileName);
		if (oNewCompressedFile.exists()) {
			oNewCompressedFile.delete();
		}
		if (!oCompressedFile.renameTo(oNewCompressedFile)) {
			JExcepcion.SendError("Could not update compressed file to new time: "+sNewManagedFileName);
		}
	}

	//
	// internal implementation
	//

	private String getManagedFileName(String originalFilename, File zOriginalFile, JSessionedApplication zApp) throws Exception {
//		if (!zOriginalFile.exists()) {
//			JExcepcion.SendError("File not found: "+zOriginalFile);
//		}
		String sRelativePath=JPath.normalizePath(zApp.getApplicationContext().getHomeRelativePath(originalFilename));
		String sAbsoluteHistoryFilePath=this.getWorkingDirectory(zApp)+"/history/"+sRelativePath;
		int iLastSepIndex=sAbsoluteHistoryFilePath.lastIndexOf('/');
		String sAbsoluteHistoryDir;
		if (iLastSepIndex!=-1) {
			sAbsoluteHistoryDir=sAbsoluteHistoryFilePath.substring(0, iLastSepIndex);
			File oDir=new File(sAbsoluteHistoryDir);
			if (!oDir.exists()) {
				if (!oDir.mkdirs()) {
					//JExcepcion.SendError("Can't create working history directory "+oDir.getAbsolutePath()+"!");
					PssLogger.logError("Can't create working history directory "+oDir.getAbsolutePath()+"!");
				}
			}
		}
		return sAbsoluteHistoryFilePath+"_"+String.valueOf(zOriginalFile.lastModified());
	}

	private String getBackupFileName(String originalFileName,File zOriginalFile, JSessionedApplication zApp) throws Exception {
//		if (!zOriginalFile.exists()) {
//			JExcepcion.SendError("File not found: "+zOriginalFile);
//		}
		String sRelativePath=JPath.normalizePath(zApp.getApplicationContext().getHomeRelativePath(originalFileName));
		String sAbsoluteBackupFilePath=this.getWorkingDirectory(zApp)+"/backup/"+sRelativePath;
		int iLastSepIndex=sAbsoluteBackupFilePath.lastIndexOf('/');
		String sAbsoluteBackupDir;
		if (iLastSepIndex!=-1) {
			sAbsoluteBackupDir=sAbsoluteBackupFilePath.substring(0, iLastSepIndex);
			File oDir=new File(sAbsoluteBackupDir);
			if (!oDir.exists()) {
				if (!oDir.mkdirs()) {
					JExcepcion.SendError("Can't create working backup directory "+oDir.getAbsolutePath()+"!");
				}
			}
		}
		return sAbsoluteBackupFilePath;
	}

	private String getWorkingDirectory(JSessionedApplication zApp) throws Exception {
		String sDir=JPath.normalizePath(zApp.getApplicationContext().getWorkingDirectory());
		File oDir=new File(sDir);
		if (!oDir.exists()) {
			if (!oDir.mkdirs()) {
				JExcepcion.SendError("Can't create working directory "+oDir.getAbsolutePath()+"!");
			}
		}
		return sDir;
	}

}
