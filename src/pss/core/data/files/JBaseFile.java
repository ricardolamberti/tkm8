package pss.core.data.files;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

import pss.core.tools.PssLogger;

public class JBaseFile {

  public final static int FILE_NORMAL = 1; //
  public final static int FILE_GZIP = 2; //
  public final static int FILE_CRYPT = 3; //

  private static final String ENTER = String.valueOf((char)13) + String.valueOf((char)10);

  String sFileName; // Nombre del archivo
  int iPos; // posicion de lectura
  boolean bAutoFlush; // flush cada vez que escribo una linea
  boolean bAutoPos; // autoposiciono el file
  boolean bAppend; // append del file existente
  boolean bLineMode; // Modo linea?
  int pFileType; // Tipo de Archivo
  protected boolean bIsOpen = false;

  protected InputStream pInput = null;
  protected OutputStream pOutput = null;

  public void SetLineModeOn() {
    bLineMode = true;
  }
  public void SetLineModeOff() {
    bLineMode = false;
  }
  public void SetAppend(boolean zValue) {
    bAppend = zValue;
  }

  public void SetAutoFlushOn() {
    bAutoFlush = true;
  }
  public void SetAutoFlushOff() {
    bAutoFlush = false;
  }
  public String GetFileName() {
    return sFileName;
  }

  //-------------------------------------------------------------------------- //
  // Constructor
  //-------------------------------------------------------------------------- //
  public JBaseFile() {
    sFileName = "";
    iPos = 0;
    bAutoFlush = false;
    bAutoPos = false;
    bAppend = false;
    bLineMode = true;
    pFileType = FILE_NORMAL;
  }

  //-------------------------------------------------------------------------- //
  // Creo un nuevo archivo de texto
  //-------------------------------------------------------------------------- //
  public void CreateNewFile(String zFile) throws Exception {} // CreateNewFile

  public boolean isFileOpen() {
    return bIsOpen;
  }
  //-------------------------------------------------------------------------- //
  // Open de un archivo
  //-------------------------------------------------------------------------- //
  public void Open(InputStream zFile) throws Exception {} // Open
  public void Open(String zFile) throws Exception {} // Open

  //-------------------------------------------------------------------------- //
  // Read de un archivo
  //-------------------------------------------------------------------------- //
  public String Read() throws Exception {
    bLineMode = true;
    return Read(80);
  } // Read

  //-------------------------------------------------------------------------- //
  // Read de un archivo
  // Recibo como parametros la cantidad de bytes a leer
  //-------------------------------------------------------------------------- //
  public String Read(int zBytes) throws Exception {
    String sData = null;
    byte cBuf[];

    // sData = null;
    if (bLineMode) {
      sData = ReadLine(pInput);
    } else {
      cBuf = new byte[zBytes];
      if (pInput.read(cBuf, 0, zBytes) != -1)
        sData = new String(cBuf);
    }

    return sData;
  } // Read

  //-------------------------------------------------------------------------- //
  // Leo una linea
  //-------------------------------------------------------------------------- //
  public String ReadLine(InputStream zIn) throws Exception {
    String sData;
    byte cBuf[];
    int iBytes = 80;

    cBuf = new byte[iBytes];
    if (zIn.read(cBuf, 0, iBytes) != -1)
      sData = new String(cBuf);
    else
      sData = "";

    return sData;
  }

  //-------------------------------------------------------------------------- //
  // Write de un archivo
  // Escribo un string en un archivo
  //-------------------------------------------------------------------------- //
  public void Write(String zData) throws Exception {
	  this.Write(zData.getBytes());
	  /*
    byte[] aBytes = zData.getBytes();
    pOutput.write(aBytes);

    if (bLineMode)
      NewLine(pOutput);
    if (bAutoFlush)
      Flush();
      */
  } // Write

  public void Write(byte[] zData) throws Exception {
	    pOutput.write(zData);

	    if (bLineMode)
	      NewLine(pOutput);
	    if (bAutoFlush)
	      Flush();
	  } // Write
  
  
  public void WriteLn(String zData) throws Exception {
    this.Write( zData + ENTER );
  } // Write

  public void WriteLn(byte[] zData) throws Exception {
	    this.Write( zData );
	    this.Write( ENTER );
	  } // Write
  
  //-------------------------------------------------------------------------- //
  // NewLine en una linea
  //-------------------------------------------------------------------------- //
  public void NewLine(OutputStream zOut) throws Exception {} // NewLine

  //-------------------------------------------------------------------------- //
  // Seek de un archivo
  // Avanzo la cantidad de zBytes el archivo
  //-------------------------------------------------------------------------- //
  public long Seek(long zBytes) throws Exception {
    return pInput.skip(zBytes);
  } // Read

  //-------------------------------------------------------------------------- //
  // Flush de un archivo
  //-------------------------------------------------------------------------- //
  public void Flush() throws Exception {
    pOutput.flush();
  } // Flush

  //-------------------------------------------------------------------------- //
  // Close de un archivo
  //-------------------------------------------------------------------------- //
  public void Close() throws Exception {
    if (pOutput != null)
      pOutput.close();
    if (pInput != null)
      pInput.close();
  } // Close

  public static void Delete(String zFile) throws Exception {
    File oFile = new File(zFile);
    if (!oFile.exists()) {
      return;
    }
    if (!oFile.delete()) {
      PssLogger.logError("No se pudo borrar el archivo " + zFile);
    }
  }

  public void Delete() throws Exception {
    File oFile = new File(sFileName);
    oFile.delete();
  }

  /**************************************************************************
   * Informa el nombre del archivo (sin el path)
   * @author CJG
   * @return Nombre del archivo (sin el path)
   *************************************************************************/
  public String getName() throws Exception {
    return (new File(sFileName)).getName();
  }

  /**************************************************************************
   * Informa el tamaño del archivo en bytes
   * @author CJG
   * @return Tamaño del archivo
   *************************************************************************/
  public long getLength() throws Exception {
    return (new File(sFileName)).length();
  }

}
