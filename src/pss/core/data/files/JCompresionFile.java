package pss.core.data.files;

import pss.JPath;
import pss.core.tools.JExcepcion;
import pss.core.tools.JNativeToolConstants;
import pss.core.tools.JNativeTools;

public class JCompresionFile {

  public static final int     SUCCESSFULL  = 0;
  private int       iTimeOut;
  private String    Command;
  private String    sDestinyFileName;
  private String    sDestinyPath;
  private String[]  sSourcesFileNames;
  private String    sCompressedFileName;
  private String    sCompresor;


  public JCompresionFile() throws Exception { }

  /*********************************************************
   * Indica el nombre que se le dará al archivo comprimido
   * @param zValue Path Completo del nombre del arhcivo
   *********************************************************/
  public void setDestinyFileName( String zValue ) { sDestinyFileName = zValue;}

  /*************************************************************************************
   * Setea los nombres de los archivos a comprimir.  Deben tener los path completos
   * @param sFilesNames Array de String donde se encuentran los nombres de los archivos
   *************************************************************************************/
  public void setFilesToCompress(String [] sFilesNames) { sSourcesFileNames = sFilesNames; }
  /**************************************************
   * Nombre del archivo que se encuentra comprimido
   * @param zValue Path completo del nombre del File
   ***************************************************/
  public void setCompressedFileName(String zValue) { sCompressedFileName = zValue;}

  /***********************************************
   * Path para los archivos que se descompirmiran
   * @param zValue
   ***********************************************/
  public void setDestinyPath( String zValue ) { sDestinyPath = zValue; }

  public boolean comprimir() throws Exception {
    fillCommand();
    int i = JNativeTools.systemCall(iTimeOut,Command);
    if ( JNativeToolConstants.PROCESS_ERROR_FOUND == i || JNativeToolConstants.WAIT_TIMED_OUT  == i ) { return false; }
    if ( i != SUCCESSFULL ) { return false; }
    return true;
  }

  public boolean descomprimir() throws Exception {
    fillCommand();
    int i = JNativeTools.systemCall(iTimeOut,Command);
    if ( JNativeToolConstants.PROCESS_ERROR_FOUND == i || JNativeToolConstants.WAIT_TIMED_OUT  == i ) { return false; }
    if ( i != SUCCESSFULL ) { return false; }
    return true;
  }

  private void fillCommand() throws Exception {
    sCompresor =  JPath.PssPathBin() + "/7zan.exe";
    if ( sSourcesFileNames != null ) { // comprimir
      if ( sDestinyFileName == null  ) { JExcepcion.SendError("Faltan datos para comprimir"); }

      sCompresor += " a -tzip " + sDestinyFileName;

      for ( int i = 0 ; i < sSourcesFileNames.length ; i++ ) {
        sCompresor += " ";
        sCompresor += sSourcesFileNames[i];
      }
    }
    else { // descomprimir
      if( sCompressedFileName == null ){ JExcepcion.SendError("Faltan datos para comprimir");}
      sCompresor += " e -y ";
      if ( sDestinyPath != null ) {
        sCompresor += " -o" + sDestinyPath;
      }
      sCompresor +=  " " + sCompressedFileName;
    }
    Command = sCompresor;
  }
  public static void main(String[] args) throws Exception {
    JCompresionFile oCompresion = new JCompresionFile();
    String []oArray = new String[5];
    oArray[0] = "c:/utiles/dezipeados/1.exe";
    oArray[1] = "c:/utiles/dezipeados/2.exe";
    oArray[2] = "c:/utiles/dezipeados/3.exe";
    oArray[3] = "c:/utiles/dezipeados/4.exe";
    oArray[4] = "c:/utiles/dezipeados/5.exe";
    oCompresion.setFilesToCompress(oArray);
    oCompresion.setDestinyFileName("c:/utiles/zipeados/facu.zip");
    oCompresion.comprimir();
  }

  public void setTimeOut(int iValue) { iTimeOut = iValue; }
}


