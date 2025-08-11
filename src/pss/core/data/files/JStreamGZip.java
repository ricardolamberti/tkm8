package pss.core.data.files;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import pss.core.tools.JTools;
import pss.core.tools.PssLogger;


public class JStreamGZip extends JBaseFile {

	//-------------------------------------------------------------------------- //
	// Propiedades publicas y privadas de la clase
	//-------------------------------------------------------------------------- //
	private FileOutputStream oWFile; // Handle del Archivo para write
	private InputStream oRFile; // Handle del Archivo para write

	//-------------------------------------------------------------------------- //
	// Constructor
	//-------------------------------------------------------------------------- //
	public JStreamGZip() {
		super();
		bLineMode = false;
	}

	//-------------------------------------------------------------------------- //
	// Creo un nuevo archivo de texto
	//-------------------------------------------------------------------------- //
	@Override
	public void CreateNewFile(String zFile) throws Exception {
		oWFile = new FileOutputStream(zFile);
		pOutput = new GZIPOutputStream(oWFile);
	} // CreateNewFile

	//-------------------------------------------------------------------------- //
	// Open de un archivo
	//-------------------------------------------------------------------------- //
	@Override
	public void Open(String zFile) throws Exception {
		oRFile = new FileInputStream(zFile);
		oRFile.mark(0);
		pInput = new GZIPInputStream(oRFile);
		pInput.mark(0);
		pInput.reset();
	} // Open
	@Override
	public void Open(InputStream zFile) throws Exception {
		oRFile = zFile ;
		oRFile.mark(0);
		pInput = new GZIPInputStream(oRFile);
		pInput.mark(0);
		pInput.reset();
	} // Open


	public static boolean zipFileForDirectory(String zDirectorySrc, String zDirectoryDest,String zDirectoryMoveFile,String zFile, boolean bSaveAllPath, String zExtension) throws Exception {
	  //zipea del directorio origen al destino con el nombre del file
	  try {
	  // Reference to the file we will be adding to the zipfile
	  BufferedInputStream origin = null;
	  int BUFFER_SIZE = 8192;
	
	  File DirSrc = new File(zDirectorySrc);
	  File aFile[] = DirSrc.listFiles() ;
	  boolean bAnyFile = false;
	  for ( int i=0; i < aFile.length ; i++ ) {
	    // Get a BufferedInputStream that we can use to read the source file
	    String filename = aFile[i].getName();
	    if (filename.endsWith(zExtension)) {
	      bAnyFile =true;
	      break;
	    }
	  }
	  if (!bAnyFile) {
	    PssLogger.logDebug( "No hay datos para exportar ");
	    return false;
	  }
	  // Reference to our zip file
	  FileOutputStream dest = new FileOutputStream(zDirectoryDest +"/"+zFile );
	  ZipOutputStream out = new ZipOutputStream( new BufferedOutputStream( dest ) );
	
	  // Create a byte[] buffer that we will read data from the source
	  // files into and then transfer it to the zip file
	  byte[] data = new byte[ BUFFER_SIZE ];
	
	  for ( int i=0; i < aFile.length ; i++ ) {
	    // Get a BufferedInputStream that we can use to read the source file
	    String filename = aFile[i].getName();
	    if (!filename.endsWith(zExtension)) continue;
	    PssLogger.logDebug( "Adding: " + filename );
	    FileInputStream fi = new FileInputStream(zDirectorySrc +"/"+ filename );
	    origin = new BufferedInputStream( fi, BUFFER_SIZE );
	    // Setup the entry in the zip file
	    ZipEntry entry = new ZipEntry( bSaveAllPath ? zDirectorySrc + "/" + filename : filename );
	    out.putNextEntry( entry );
	    // Read data from the source file and write it out to the zip file
	    int count;
	    while( ( count = origin.read(data, 0, BUFFER_SIZE ) ) != -1 )    {
	      out.write(data, 0, count);
	    }
	    // Close the source file
	    origin.close();
	    PssLogger.logDebug( "Mover archivo: " + filename );
	    JTools.MoveFile(zDirectorySrc +"/"+ filename, zDirectoryMoveFile +"/"+ filename);
	  }
	  // Close the zip file
	  out.close();
	  return true;
	}
	catch( Exception e ) {
	  PssLogger.logDebug("Error: " + e.getMessage() );
	  throw e;
	}
	}

	/*****************************************************************************
	 * Des-zipea un archivo a un direcotrio establecido
	 * @param zArchivo -Archivo origen
	 * @param zDirectorySrc - Directorio de salida
	 * @param zDirectoryDest - Directorio de salida
	 * @throws JException por errores en el proceso
	 ****************************************************************************/
	public static void unzipFileForDirectory(String zFile, String zDirectorySrc, String zDirectoryDest) throws Exception {
	  try {
	    // Create a ZipInputStream to read the zip file
	    BufferedOutputStream dest = null;
	    FileInputStream fis = new FileInputStream( (zDirectorySrc!=null?zDirectorySrc +"/":"")+  zFile );
	    ZipInputStream zis = new ZipInputStream( new BufferedInputStream( fis ) );  //fuente comprimido
	
	    // Loop over all of the entries in the zip file
	    int count;
	    int BUFFER_SIZE = 8192;
	    byte data[] = new byte[BUFFER_SIZE]; //8k
	    ZipEntry entry;
	    while( ( entry = zis.getNextEntry() ) != null )
	    {
	      if( !entry.isDirectory() )
	      {
	        String entryName = entry.getName();
	        String destFN =zDirectoryDest+"/"+ entry.getName();
	        String destDir = zDirectoryDest+"/"+ ( entryName.lastIndexOf("/") ==-1? "": entryName.substring(0,  entryName.lastIndexOf("/")));
	
	        JTools.MakeDirectory(destDir);
	
	        // Write the file to the file system
	        FileOutputStream fos = new FileOutputStream( destFN );
	        dest = new BufferedOutputStream( fos, BUFFER_SIZE );
	        while( (count = zis.read( data, 0, BUFFER_SIZE ) ) != -1 )
	        {
	          dest.write( data, 0, count );
	        }
	        dest.flush();
	        dest.close();
	      }
	    }
	    zis.close();
	  }
	  catch( Exception e )
	  {
	    e.printStackTrace();
	  }
	
	}

}
