package pss.core.data.files;

import java.io.File;
import java.util.Arrays;

import pss.JPath;
import pss.core.tools.JExcepcion;
import pss.core.tools.PssLogger;
import pss.core.tools.collections.JCollectionFactory;
import pss.core.tools.collections.JIterator;
import pss.core.tools.collections.JList;

public class JFilesProcessor {

	protected String sFilesNames[];
	protected String sDirIn = null;
	protected JList<String> oPriorityFiles;
	protected boolean bProcessDirectories;

	private boolean bPriorityByExtension;
	private boolean bPriorityByName;
	private boolean bProcessZippedFiles;
	private int iTimeToUnzipFiles = 30000;
	private long lTimePoleo = 10000;

	public void setTimePoleo(long zValue) {
		lTimePoleo = zValue;
	}

	public boolean havePriority() {
		return bPriorityByExtension || bPriorityByName;
	}

	public boolean EncFile() throws Exception {
		return false;
	}

	public void desencriptarFile() throws Exception {}

	public String GetZipExtend() {
		return "zip";
	}
	public String GetEncExtend() {
		return "d";
	}

	/********************************************************************************
	 * Loopea hasta que encuentra archivos. Si se seteo que dezipee, lo hace y
	 * devuelve los archivos en el directorio de input, destruyendo el zip.  Tambien
	 * se puede setear que tome determinados archivos antes que otros, mediante el
	 * array de prioridad
	 * @throws Exception
	 *********************************************************************************/
	public void ExecuteFilesProcessor() throws Exception {
		boolean bErrors;
		while (!Thread.interrupted()) {
			Thread.sleep(this.lTimePoleo);
			String sCompletePath;

			if (!isAnyFile()) {
				continue;
			}

			if (EncFile()) {
				desencriptarFile();
				continue;
			}

			if (ZippedFile() && bProcessZippedFiles) {
				descomprimirFiles();
				continue;
			}

			if (havePriority()) {
				ProcessPriorityFiles();
				continue;
			}
			bErrors = false;
			for (int i = 0; i < sFilesNames.length; i++) {
				sCompletePath = sDirIn + "/" + sFilesNames[i];
				try {
					ValidateFile(sCompletePath);
					ProcessFile(sCompletePath);
					OnFileOk();
				} catch (Exception e) {
					PssLogger.logError(e.getMessage());
					bErrors = true;
				}
			}
			if (!bErrors) {
				OnAllFilesOk();
			}
			sFilesNames = null;
		}
	}

	private boolean ProcessPriorityFiles() throws Exception {
		String sCompletePath;
		boolean bErrors = false;
		JIterator<String> oIter = oPriorityFiles.getIterator();
		while (oIter.hasMoreElements()) {
			String sFilePriority = oIter.nextElement();
			String sFileName = FindPriorityInFiles(sFilePriority);
			if (sFileName == null)
				continue;
			sCompletePath = sDirIn + "/" + sFileName;
			try {
				ValidateFile(sCompletePath);
				ProcessFile(sCompletePath);
				OnFileOk();
			} catch (Exception e) {
				PssLogger.logError("Archivo ["+ sCompletePath +"] rechazado. Causa: "+e.getMessage());
				bErrors = true;
			}
		}
		if (!bErrors) {
			OnAllFilesOk();
		}
		sFilesNames = null;
		return true;
	}

	public void ValidateFile(String sCompletePath) throws Exception {}

	private String FindPriorityInFiles(String sFilePriority) throws Exception {
		int i = 0;
		for (i = 0; sFilesNames.length > i; i++) {
			if (bPriorityByExtension) {
				if (sFilesNames[i].endsWith(sFilePriority)) {
					break;
				}
			} else {
				if (sFilesNames[i].startsWith(sFilePriority)) {
					break;
				}
			}
		}
		if (sFilesNames.length <= i) {
			return null;
		}
		return sFilesNames[i];
	}

	protected boolean isAnyFile() throws Exception {
		if (sDirIn == null)
			JExcepcion.SendError("No se especificó directorio entrada.");

		File oFile = new File(sDirIn);

		try {
			// ------------------------------------------------------------------------ //
			// "Debería" devolver una lista con los nombres de los archivos encontrados //
			// ------------------------------------------------------------------------ //
			sFilesNames = oFile.list();
			if (sFilesNames == null) {
				return false;
			}
			if (sFilesNames.length == 0) {
				return false;
			}

			Arrays.sort(sFilesNames);
			oFile = null;
			if (mustProcessDirectories()) {
				return true;
			}
			for (int i = 0; i < sFilesNames.length; i++) {
				File oFileCheck = new File(sDirIn + "/" + sFilesNames[i]);
				if (!oFileCheck.isDirectory()) {
					return true;
				}
			}
			return false;
		} catch (SecurityException se) {
			PssLogger.logError("Archivos no encontrados en: ["+ sDirIn + "]. Causa: "+se.getMessage());
			oFile = null;
			return false;
		}
	}

	public synchronized void ProcessFile(String sFileName) throws Exception {}

	public void setDirIn(String sValue) {
		sDirIn = sValue;
	}

	public boolean MoverArchivo(String sFileName) throws Exception {
		return true;
	}

	public void addPriorityItem(String sItem, int Priority) throws Exception {
		if (oPriorityFiles == null)
			oPriorityFiles = JCollectionFactory.createList();
		oPriorityFiles.addElementAt(Priority, sItem);
	}

	public void setExtentionPriority() {
		bPriorityByExtension = true;
	}
	public void setNamePriority() {
		bPriorityByName = true;
	}
	public void setProcessDirectories() {
		bProcessDirectories = true;
	}

	public boolean mustProcessDirectories() {
		return bProcessDirectories;
	}

	public boolean ZippedFile() {
		for (int i = 0; i < sFilesNames.length; i++) {
			if (sFilesNames[i].toLowerCase().endsWith(GetZipExtend())) {
				return true;
			}
		}
		return false;
	}

	/****************************************
	 * Descompime el archivo y borra el zip
	 * @throws Excepti
	 ****************************************/
	public synchronized void descomprimirFiles() throws Exception {
		JCompresionFile oCompFile = new JCompresionFile();
		oCompFile.setTimeOut(iTimeToUnzipFiles);
		for (int i = 0; i < sFilesNames.length; i++) {
			if (sFilesNames[i].toLowerCase().endsWith(GetZipExtend())) {
				File oFile = new File(sDirIn + "/" + sFilesNames[i]);
				if (oFile.isDirectory()) {
					oFile = null;
					continue;
				}
				oCompFile.setCompressedFileName(sDirIn + "/" + sFilesNames[i]);
				oCompFile.setDestinyPath(JPath.PssPathInput());
				if (!oCompFile.descomprimir()) {
					JExcepcion.SendError("Error al descomprimir archivo^ [" + sFilesNames[i] + "]");
				}
				oFile.delete();
			}
		}
	}

	public void setProcessZippedFile() {
		bProcessZippedFiles = true;
	}

	public void setTimeToUnzip(int iValue) {
		iTimeToUnzipFiles = iValue;
	}

	public void OnFileOk() throws Exception {}

	public void OnAllFilesOk() throws Exception {}
}
