package pss.core.tools;

import pss.core.tools.jnidisp.CFunc;
import pss.core.tools.jnidisp.CMalloc;

/**
 * An abstract class with all public static final synchronized member methods.<br>
 * It forms as a wrapper to the native code present in the PssTools.dll file.<br>
 * All functionality present in the dll file is available through this wrapper.<br>
 * All error codes and possibly returned values are encapsulated in the JNativeToolConstants<br>
 * interface.<br>
 * Classes that wish to use this wrapper should also implement the JNativeToolConstants interface.<br>
 * @author Gaston Berrio
 * @see pss.core.Tools.JNativeToolConstants public interface.
 */

public abstract class JNativeTools {

  static final private String PssTools;
  static final private CFunc copyFile;
  static final private CFunc copyFolder;
  static final private CFunc deleteFile;
  static final private CFunc getDriveType;
  static final private CFunc readDriveSize;
  static final private CFunc readMemorySize;
  static final private CFunc setFileAttributes;
  static final private CFunc shutDown;
  static final private CFunc getCPU;
  static final private CFunc getSO;
  static final private CFunc systemCall;
  static final private CFunc isProcessRunning;
  static final private CFunc setSystemDateAndTime;
  static final private CFunc setMyPriority;
  static final private CFunc getEnviromentVariable;

  public static final int HIGH_PRIORITY_CLASS = 0X80;
  public static final int NORMAL_PRIORITY_CLASS = 0X20;
  public static final int IDLE_PRIORITY_CLASS = 0X40;

  /**
   * Static block used to load the desired dll library in memory<br>
   * and obtain each function's address before it is used,<br>
   */
  static {
    PssTools         = "PssTools.dll";
    copyFile          = new CFunc(PssTools, "copyFile");
    copyFolder        = new CFunc(PssTools, "copyFolder");
    deleteFile        = new CFunc(PssTools, "deleteFile");
    getDriveType      = new CFunc(PssTools, "getDriveType");
    readDriveSize     = new CFunc(PssTools, "readDriveSize");
    readMemorySize     = new CFunc(PssTools, "readMemorySize");
    getCPU             = new CFunc(PssTools, "getCPU");
    getSO             = new CFunc(PssTools, "getSO");
    setFileAttributes = new CFunc(PssTools, "setFileAttributes");
    shutDown          = new CFunc(PssTools, "shutDown");
    systemCall        = new CFunc(PssTools, "systemCall");
    isProcessRunning  = new CFunc(PssTools, "isProcessRunning");
    setSystemDateAndTime = new CFunc(PssTools, "setSystemDateAndTime");
    setMyPriority     = new CFunc(PssTools, "SetMyPriority");
    getEnviromentVariable = new CFunc(PssTools, "getEnviromentVariable");
  }

  /**
   *  Set system's date and time.
   */
  public static final synchronized int setSystemDateAndTime(
        int year, int month, int day, int hour, int minute, int second, int millisecond ) {
    return setSystemDateAndTime.callInt(new Object[]{ new Integer(year),
                                                      new Integer(month),
                                                      new Integer(day),
                                                      new Integer(hour),
                                                      new Integer(minute),
                                                      new Integer(second),
                                                      new Integer(millisecond) });
  }

  /**
   * Copies source folder to destiny folder.<br>
   * If destiny folder does not exist, it is created.<br>
   * The source folder (not the entire path) is also created and copied.<br>
   * At the moment, copying folders to or from remote machines is NOT supported.<p>
   *
   * The native C function prototype is:<br>
   * int copyFolder ( const char source[], const char destiny[], BOOL copySubFolders )<br>
   *
   * @param sourceFolderWithFullPath java.lang.String<br>
   * <DL><DD>Must include the entire path, including the hard disk drive:<br>
   * i.e.: "C:\\path\\folder"</DD></DL><p>
   *
   * @param destinyFolderWithFullPath java.lang.String<br>
   * <DL><DD>Must include the entire path, including the hard disk drive:<br>
   * i.e.: "C:\\path\\folder"</DD></DL><p>
   *
   * @param copySubFolders boolean
   * <DL><DD>If false, only the files in the source folder are copied.<br>
   * If true, the complete existing folder tree is created and copied.</DD></DL><p>
   *
   * @return int
   */
  public static final synchronized int copyFolder(String sourceFolderWithFullPath,
                                                  String destinyFolderWithFullPath,
                                                  boolean copySubFolders ) {
    return copyFolder.callInt(new Object[]{ sourceFolderWithFullPath, destinyFolderWithFullPath,
                                            copySubFolders ? new Integer(1) : new Integer(0) });
  }


  /**
   * Copies source file to destiny file.<br>
   * If destiny file exists, it is overwritten.<br>
   * If it doesn't, it is created.<p>
   *
   * The native C function prototype is:<br>
   * int copyFile ( char source[], char destiny[], BOOL overwriteExisting )
   *
   * @param sourceFileNameWithFullPath    java.lang.String<br>
   * <DL><DD>To copy to or from a remote machine, the param must be of the following form:<br>
   * i.e.: "bueconws0081\\path\\file.name"<br>
   * To copy to or from the local machine, the param must be of the following form:<br>
   * i.e.: "C:\\path\\file.name"</DD></DL><p>
   *
   * @param destinyFileNameWithFullPath   java.lang.String<br>
   * <DL><DD>To copy to or from a remote machine, the param must be of the following form:<br>
   * i.e.: "bueconws0081\\path\\file.name"<br>
   * To copy to or from the local machine, the param must be of the following form:<br>
   * i.e.: "C:\\path\\file.name"</DD></DL><p>
   *
   * @return int
   */
  public static final synchronized int copyFile(String sourceFileNameWithFullPath,
                                                String destinyFileNameWithFullPath ) {
    return copyFile.callInt(new Object[]{ sourceFileNameWithFullPath, destinyFileNameWithFullPath });
  }


  /**
   * Deletes the file specified in the in the param fileNameWithFullPath.<p>
   *
   * The native C function prototype is:<br>
   * int deleteFile ( char fileName[] )
   *
   * @param fileNameWithFullPath  java.lang.String<br>
   * <DL><DD>To delete a file on a remote machine, the param must be of the following form:<br>
   * i.e.: "bueconws0081\\path\\file.name"<br>
   * To delete a file on the local machine, the param must be of the following form:<br>
   * i.e.: "C:\\path\\file.name"</DD></DL><p>
   *
   * @return int
   */
  public static final synchronized int deleteFile(String fileNameWithFullPath) {
    return deleteFile.callInt(new Object[]{ fileNameWithFullPath });
  }


  /**
   * Reads the type of drive specified in the drive param.<p>
   *
   * The native C function prototype is:<br>
   * int getDriveType ( char drive[] )
   *
   * @param drive  java.lang.String<br>
   * <DL><DD>To read a drive type on a remote machine, the param must be of the following form:<br>
   * i.e.: "bueconws0081"<br>
   * To read a drive type on the local machine, the param must be of the following form:<br>
   * i.e.: "C:\\"</DD></DL><p>
   *
   * @return int
   */
  public static final synchronized int getDriveType(String drive) {
    return getDriveType.callInt(new Object[]{ drive });
  }


  /**
   * Reads the drive size specified in the drive param.<p>
   *
   * The native C function prototype is:<br>
   * int readDriveSize ( char drive[], char* totalDriveSize, char* availableDriveSize )
   *
   * @param drive  java.lang.String<br>
   * <DL><DD>To read a drive type on a remote machine, the param must be of the following form:<br>
   * i.e.: "bueconws0081"<br>
   * To read a drive type on the local machine, the param must be of the following form:<br>
   * i.e.: "C:\\"</DD></DL><p>
   *
   * @param diskSize_20  char[]<br>
   * <DL><DD>This param must be a char[] of size 20.<br>
   * i.e.: char[20]<br>
   * If the value returned by this method is TRUE (defined in JNativeToolConstants interface),<br>
   * then this param will have the size of the specified drive in bytes.</DD></DL><p>
   *
   * @param freeSpace_20 char[]<br>
   * <DL><DD>This param must be a char[] of size 20.<br>
   * i.e.: char[20]<br>
   * If the value returned by this method is TRUE (defined in JNativeToolConstants interface),<br>
   * then this param will have the available size of the specified drive in bytes.</DD></DL><p>
   *
   * @return int
   */
  public static final synchronized int readDriveSize(String drive, char[] diskSize_20, char[] freeSpace_20) {
    CMalloc cDiskSize  = new CMalloc(21);
    CMalloc cFreeSpace = new CMalloc(21);
    int reply = readDriveSize.callInt(new Object[]{ drive, cDiskSize, cFreeSpace });
    if( reply == JNativeToolConstants.TRUE ) {
      for(int i=0; i<20; i++) { freeSpace_20[i] = diskSize_20[i] = '0'; }
      String temp = cDiskSize.getString(0);
      int amountOfDigits = temp.length();
      temp.getChars(0, amountOfDigits, diskSize_20, 20-amountOfDigits);
      temp = cFreeSpace.getString(0);
      amountOfDigits = temp.length();
      temp.getChars(0, amountOfDigits, freeSpace_20, 20-amountOfDigits);
    }
    cDiskSize.free();
    cFreeSpace.free();
    return reply;
  }

  public static final synchronized int getCPU(char[] model, char[] hertz) {
    CMalloc cModel  = new CMalloc(200);
    CMalloc cHertz = new CMalloc(21);
    int reply = getCPU.callInt(new Object[]{  cModel, cHertz });
    if( reply == JNativeToolConstants.TRUE ) {
      for(int i=0; i<20; i++) { hertz[i] = '0'; }
      for(int i=0; i<200; i++) { model[i] = 0; }
      String temp = cHertz.getString(0);
      int amountOfDigits = temp.length();
      temp.getChars(0, amountOfDigits, hertz, 20-amountOfDigits);
      temp = cModel.getString(0);
      amountOfDigits = temp.length();
      temp.getChars(0, amountOfDigits, model, 0);
    }
    cModel.free();
    cHertz.free();
    return reply;
  }
  public static final synchronized int getSO(char[] so, char[] lenguaje) {
    CMalloc cSO  = new CMalloc(200);
    CMalloc cLang = new CMalloc(200);
    int reply = getSO.callInt(new Object[]{  cSO, cLang });
    if( reply == JNativeToolConstants.TRUE ) {
      for(int i=0; i<200; i++) { so[i] = lenguaje[i] = 0; }
      String temp = cLang.getString(0);
      int amountOfDigits = temp.length();
      temp.getChars(0, amountOfDigits, lenguaje, 0);
      temp = cSO.getString(0);
      amountOfDigits = temp.length();
      temp.getChars(0, amountOfDigits, so, 0);
    }
    cSO.free();
    cLang.free();
    return reply;
  }


  public static final synchronized int readMemorySize( char[] memSize, char[] memSpace, char[] pageSize, char[] pageSpace) {
    CMalloc cMemSize  = new CMalloc(21);
    CMalloc cMemSpace = new CMalloc(21);
    CMalloc cPageSize  = new CMalloc(21);
    CMalloc cPageSpace = new CMalloc(21);
    int reply = readMemorySize.callInt(new Object[]{ cMemSize, cMemSpace, cPageSize, cPageSpace });
    if( reply == JNativeToolConstants.TRUE ) {
      for(int i=0; i<20; i++) { memSpace[i] = memSize[i] = pageSpace[i] = pageSize[i] = '0'; }
      String temp = cMemSize.getString(0);
      int amountOfDigits = temp.length();
      temp.getChars(0, amountOfDigits, memSize, 20-amountOfDigits);

      temp = cMemSpace.getString(0);
      amountOfDigits = temp.length();
      temp.getChars(0, amountOfDigits, memSpace, 20-amountOfDigits);

      temp = cPageSpace.getString(0);
      amountOfDigits = temp.length();
      temp.getChars(0, amountOfDigits, pageSpace, 20-amountOfDigits);

      temp = cPageSize.getString(0);
      amountOfDigits = temp.length();
      temp.getChars(0, amountOfDigits, pageSize, 20-amountOfDigits);
    }
    cMemSize.free();
    cMemSpace.free();
    cPageSize.free();
    cPageSpace.free();
    return reply;
  }


  /**
   * Sets the attributes for the file specified in the fileNameWithFullPath param.<p>
   *
   * The native C function prototype is:<br>
   * int setFileAttributes ( char fileName[], unsigned long fileAttrib )
   *
   * @param fileNameWithFullPath  java.lang.String<br>
   * <DL><DD>To set attributes for a file on a remote machine, the param must be of the following form:<br>
   * i.e.: "bueconws0081\\path\\file.name"<br>
   * To set attributes for a file on the local machine, the param must be of the following form:<br>
   * i.e.: "C:\\path\\file.name"</DD></DL><p>
   *
   * @param fileAttributes        int<br>
   * <DL><DD>This param must be one of the constants defined in the JNativeToolConstants interface.</DD></DL><p>
   *
   * @return int
   */
  public static final synchronized int setFileAttributes(String fileNameWithFullPath, int fileAttributes) {
    return setFileAttributes.callInt(new Object[]{ fileNameWithFullPath, new Integer(fileAttributes) });
  }


  /**
   * Shuts down the system without any type of confirmation from the user.<br>
   * Any information not saved will be lost, since all active applications will be forced to end.<br>
   * Use at your own risk...<p>
   *
   * The native C function prototype is:<br>
   * int shutDown ( )
   *
   * @return int
   */
  public static final synchronized int shutDown() {
    return shutDown.callInt(new Object[]{});
  }

  /******************************************************
   * Excecutes a system Call in the specific TimeOut.
   * @param lTimeOut
   * @param sCommand
   *
   * @return int.
   * -999 if Timed Out
   * -998 if any error ocurrs
   * Program return code, otherwise.
   *******************************************************/
  public static final synchronized int systemCall( int lTimeOut, String sCommand ) {
    return systemCall.callInt(new Object[] { new Integer(lTimeOut), sCommand});
  }


  /**
   * Asks de OS if the process with the given name is currently running or not.
   *
   * @param zProcessName the name of the proces as you see it in the OS task manager
   * @return 0 - if the process is not running <br>
   *         1 - if the process is running
   */
  public static final synchronized int isProcessRunning( String zProcessName ) {
    return isProcessRunning.callInt(new Object[] { zProcessName });
  }

  public static final synchronized void setMyPriority( int iPriorityLevel ) {
    setMyPriority.callInt(new Object[] { new Integer(iPriorityLevel) });
  }

  public static final synchronized String getEnv( String sVariable ) {
    CMalloc cBufferIn = new CMalloc(1024);
    String sValue = "";
    Object oParams[] = new Object[] { sVariable, cBufferIn, new Integer(1024) };
    if( getEnviromentVariable.callInt( oParams ) == 1 ) {
      sValue = cBufferIn.getString(0);
    };
    cBufferIn.free();
    return sValue;
  }

}
