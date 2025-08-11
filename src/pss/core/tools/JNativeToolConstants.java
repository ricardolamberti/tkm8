package pss.core.tools;

/**
 * A public interface encapsulating every constant value required in the JNativeTools class.<br>
 * Declared this way, these constants are seen as public static final vars by each class that<br>
 * implements this interface, meaning that it is not necesary to prefix these vars with<br>
 * the name of the interface.<br>
 * i.e.: instead of using:  "JNativeToolConstants . FILE_ATTRIBUTE_READONLY"<br>
 * just use  "FILE_ATTRIBUTE_READONLY".<br>
 * @author Gaston Berrio
 * @see pss.core.Tools.JNativeTools public class.
 */


public interface JNativeToolConstants {

//////////////////////////////////////////////////////////
//  values returned form the                            //
//  getDriveType() native function                      //
//////////////////////////////////////////////////////////
  int DRIVE_UNKNOWN     = 0;
  int DRIVE_NO_ROOT_DIR = 1;
  int DRIVE_REMOVABLE   = 2;
  int DRIVE_FIXED       = 3;
  int DRIVE_REMOTE      = 4;
  int DRIVE_CDROM       = 5;
  int DRIVE_RAMDISK     = 6;


//////////////////////////////////////////////////////////
//  values returned form the                            //
//  copyFolder() native function                      //
//////////////////////////////////////////////////////////
  int CANCELED_BY_USER      = 1223;
  int ERROR_CREATING_FOLDER = 6666;


//////////////////////////////////////////////////////////
//  values returned if the native function failed       //
//  and GetLastError() was called from with in the dll  //
//////////////////////////////////////////////////////////
  int ERROR_ACCESS_DENIED =  5;
  int ERROR_FILE_EXISTS   = 80;
  int ERROR_NOT_READY     = 21;


//////////////////////////////////////////////////////////
//  constant values used for                            //
//  setFileAttributes() function parameters             //
//////////////////////////////////////////////////////////
  int FILE_ATTRIBUTE_READONLY  = 0x001;
  int FILE_ATTRIBUTE_HIDDEN    = 0x002;
  int FILE_ATTRIBUTE_ARCHIVE   = 0x020;
  int FILE_ATTRIBUTE_NORMAL    = 0x080;
  int FILE_ATTRIBUTE_TEMPORARY = 0x100;


//////////////////////////////////////////////////////////
//  typedef to see if native function calls finished    //
//  correctly. if not TRUE then the returned value      //
//  corresponds to the returned value by GetLastError() //
//////////////////////////////////////////////////////////
  int TRUE = 1;

//  -999		is returned when Timed Out.
//  -998		is returned when an error of Process Management's function is detected
  //////////////////////////////////////////////////////////
//  constant values used for                              //
//  systemCall () function parameters                     //
////////////////////////////////////////////////////////////
  int WAIT_TIMED_OUT        =     -999;
  int PROCESS_ERROR_FOUND   =     -998;


////////////////////////////////////////////////////////////
//  constant values used for                              //
//  isProcessRunning() function return codes              //
////////////////////////////////////////////////////////////
  int PROCESS_IS_RUNNING                  = 1;
  int PROCESS_IS_NOT_RUNNING              = 0;


}
