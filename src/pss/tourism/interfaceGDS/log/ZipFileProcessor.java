package pss.tourism.interfaceGDS.log;

import java.io.File;
import java.io.FilenameFilter;

import pss.JPath;
import pss.core.data.files.JStreamGZip;
import pss.core.tools.JTools;
import pss.core.tools.PssLogger;
import pss.tourism.interfaceGDS.FileProcessor;

public class ZipFileProcessor extends FileProcessor {

	@Override
	public int processFiles() {
	    int proc = 0;
	    try {
	        File[] files = getZipFiles(JPath.PssPathInputOriginal());
	        for (File file : files) {
	            String fname = file.getName();
	            if (fname.startsWith("client"))
	                continue;

	            // Descomprimir archivo ZIP inicial al directorio unzipped
	            String unzipDir = JPath.PssPathInputZIP() + "/unzipped/" + fname.substring(0, fname.lastIndexOf("."));
	            JStreamGZip.unzipFileForDirectoryPlain(file.getAbsolutePath(), null, unzipDir);

	            // Procesar el directorio descomprimido
	            proc += processUnzippedDirectory(new File(unzipDir), fname.substring(0, fname.lastIndexOf(".") + 1));

	            // Eliminar el archivo ZIP original
	            file.delete();
	            proc++;
	        }
	    } catch (Exception e) {
	        PssLogger.logError(e, "Error reading files");
	    }
	    return proc;
	}

	private int processUnzippedDirectory(File dir, String mark) throws Exception {
	    int proc = 0;

	    if (!dir.exists() || !dir.isDirectory()) {
	        return proc;
	    }

	    File[] files = dir.listFiles();
	    if (files == null || files.length == 0) {
	        return proc;
	    }

	    for (File file : files) {
	        if (file.isDirectory()) {
	            // Si es un directorio, procesarlo recursivamente
	            proc += processUnzippedDirectory(file, mark);
	        } else if (file.getName().endsWith(".zip")) {
	            // Si es un archivo ZIP, descomprimirlo en un subdirectorio único
	            String unzipDir = JPath.PssPathInputZIP() + "/unzipped/" + mark + file.getName().substring(0, file.getName().lastIndexOf("."));
	            JStreamGZip.unzipFileForDirectoryPlain(file.getAbsolutePath(), null, unzipDir);

	            // Procesar el contenido descomprimido
	            proc += processUnzippedDirectory(new File(unzipDir), mark);

	            // Eliminar el archivo ZIP procesado
	            file.delete();
	            proc++;
	        } else {
	            // Mover archivo al nivel superior con marca
	            JTools.MoveFilesWithMark(dir.getAbsolutePath(), JPath.PssPathInputPending(), mark);
	            proc++;
	        }
	    }

	    return proc;
	}


	

	private FilenameFilter verfilter = new FilenameFilter() {
		public boolean accept(File dir, String name) {
			String fname = name.toLowerCase();
			return fname.endsWith(".zip");
		}
	};
	
	private File[] getZipFiles(String path) throws Exception {
		File dir = new File(path);
		File files[] = dir.listFiles(verfilter);
		return files;
	}

	
	

}
