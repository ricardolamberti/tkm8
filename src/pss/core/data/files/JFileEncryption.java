package pss.core.data.files;

import pss.JPath;
import pss.core.tools.JExcepcion;
import pss.core.tools.JNativeToolConstants;
import pss.core.tools.JNativeTools;
import pss.core.tools.JTools;

public class JFileEncryption {

  public static final int SUCCESSFULL  = 0;
  private int iTimeOut = 0;
  private static final String ENCRYPTFILE = "runEncrypt.bat" ;
  private String fileSource;
  private String[]  sSourcesFileNames;

  public JFileEncryption(){
  }

  public static void main(String[] args) {
    try{
      JFileEncryption oFileEncryption = new JFileEncryption();
      String []oArray = new String[3];
      oArray[0] = "1.txt";
      oArray[1] = "2.txt";
      oArray[2] = "3.txt";
      oFileEncryption.setFileSource("c:/temp/files");
      oFileEncryption.setFilesToEncrypt(oArray);
      oFileEncryption.setTimeOut(60000);
      oFileEncryption.FileProcess();
    }catch (Exception e){}
  }

  public void setFileSource(String fileSource) { this.fileSource = fileSource; }

  public String getFileSource() { return fileSource; }

  public void setFilesToEncrypt(String [] sFilesNames){ sSourcesFileNames = sFilesNames; }

  public void setTimeOut(int timeout) { iTimeOut = timeout; }


  /*************************************************************************************
  * Encripta o desencripta los archivos definidos
  *************************************************************************************/
  public void FileProcess() throws Exception {
    boolean berror = false;
    String sPath = JPath.PssPathBin() + "/";
    if (fileSource.equals("") ) JExcepcion.SendError( "No se encuentran definido el origen o el destino del archivo." );
    for ( int i = 0 ; i < sSourcesFileNames.length ; i++ ) {
      JTools.MoveFile( fileSource + "/" + sSourcesFileNames[i], sPath + sSourcesFileNames[i] );
      berror = beginEncryptFile(sSourcesFileNames[i]);
      JTools.MoveFile( sPath + sSourcesFileNames[i], fileSource + "/" + sSourcesFileNames[i] );
    }
    if (!berror) JExcepcion.SendError( "Uno o mas de los archivos no fueron modificados correctamente." );
  }

  private boolean beginEncryptFile(String sFile) throws Exception {
    String sPathOnly = JPath.PssPathBin();
    String sPath = JPath.PssPathBin() + "/" + ENCRYPTFILE;
    String sCommand = JTools.ReplaceToken( sPath + " " + sPathOnly + " " + sFile, "/", "\\" );
    return ExecuteCommand( sCommand );
  }

  private boolean ExecuteCommand( String zCommand ) throws Exception {
    int i = JNativeTools.systemCall( iTimeOut , zCommand );
    if ( JNativeToolConstants.PROCESS_ERROR_FOUND == i || JNativeToolConstants.WAIT_TIMED_OUT  == i ) { return false; }
    if ( i != SUCCESSFULL ) { return false; }
    return true;
  }
}
