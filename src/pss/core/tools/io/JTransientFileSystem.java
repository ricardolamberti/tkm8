package pss.core.tools.io;

/**
 * Description: A transient file system that works from a directory on the standard FS.
 *
 * Triggers a GC on every call to JTFS.createNewFile(...), with a mechanism to
 * avoid GCs overlapping (separate threads) by implementing JGarbageCollectable.
 *
 * It is a singleton that associates instances to the root directory of the
 * repository it handles.
 *
 * @author Iván Rubín
 */

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import pss.core.tools.JGarbageCollectable;

public class JTransientFileSystem implements JGarbageCollectable {

  private static Map<String, JTransientFileSystem>                   oInstances = new HashMap<String, JTransientFileSystem>();
  private String                       sRootDirectory = "";
  private boolean                      bGarbageCollecting = false;

  // Helpers for handling milliseconds, as we specify expiration dates in ms.
  public static final int     SECOND = 1000;
  public static final int     MINUTE = SECOND * 60;
  public static final int     HOUR = MINUTE * 60;

  private JTransientFileSystem( String zRootDirectory ) {
    sRootDirectory = zRootDirectory;
  }

  public synchronized static JTransientFileSystem getInstance( String zTransientRootDirectory ) {
    JTransientFileSystem oInstance = oInstances.get( zTransientRootDirectory );

    if( oInstance == null ) {
      oInstance = new JTransientFileSystem( zTransientRootDirectory );
      oInstances.put( zTransientRootDirectory, oInstance );
    }

    return oInstance;
  }

  /**
   *  JGarbageCollectable interface
   */
  public synchronized boolean startsGarbageCollection() {
    if( !bGarbageCollecting ) {
      bGarbageCollecting = true;
      return true;
    }

    return false;
  }

  public synchronized void endsGarbageCollection() {
    bGarbageCollecting = false;
  }
  /********/


  /**
    * Answers the root directory from which we're operating
    *
    */
  public String getRootDirectory() {
    return sRootDirectory;
  }

  /**
    * Answers a File object for a newly created file.
    *
    * The file is created in the repository under a directory hierarchy
    * that resembles the package of zResponsible's class + zlValidUntil
    * + the name we provide.
    *
    * zlValidUntil in milliseconds from Epoch.
    *
    */
  public synchronized File createNewFile( Class<? extends Object> zResponsible, long zlValidUntil, String sName ) throws JTransientFileSystemException {
    File   f = null;
    String sFileName;

    if( this.sRootDirectory == null ) {
      throw new JTransientFileSystemException( "There is no root directory specified!" );
    }

    // Triggers a shit collector. He by himself will terminate inmediatly if another
    // instance is collecting the shit for this system
    new JTransientFileSystemGC( this ).start();

    try {
      // Check repository's root
      f = new File( this.sRootDirectory );
      if( !f.exists() ) {
        if( !f.mkdirs() ) {
          throw new JTransientFileSystemException( "Can't create repository " + f.getAbsolutePath() + "!" );
        }
      }

      // Start building the abosolute path for the file we'll try to create
      sFileName = sRootDirectory;

      // Locate the hierarchy for zResponsible's package
      Package oPackage = zResponsible.getPackage();

      // If we've a package, we must operate the hierarchy in the file system
      if( oPackage != null ) {
        // Add the directories for zResponsible's package
        sFileName += "/" + oPackage.getName().replace( '.', '/' );

        // Check directories
        f = new File( sFileName );
        if( !f.exists() ) {
          if( !f.mkdirs() ) {
            throw new JTransientFileSystemException(
              "Can't create " + f.getAbsolutePath() + " directory"
            );
          }
        }
      }

      // Actual file creation. Adds the file name to the path we've been building
      String sClassName = zResponsible.getName().substring(
        zResponsible.getName().lastIndexOf( '.' ) + 1
      );

      sFileName += "/" + zlValidUntil + "." + sClassName + "." + sName;

      f = new File( sFileName );
      if( f.exists() ) {
        throw new JTransientFileSystemException(
          "File " + f.getAbsolutePath() + " already exists! We're not going to override it."
        );
      }

      if( !f.createNewFile() ) {
        throw new JTransientFileSystemException(
          "Can't create " + f.getAbsolutePath()
        );
      }
    } catch( IOException e ) {
      throw new JTransientFileSystemException( "Input/output error", e );
    } catch( SecurityException e ) {
      throw new JTransientFileSystemException( "There are security restrictions for accessing the standard filesystem", e );
    }

    return f;
  }


  // A test for the transient file system
  public static void main( String args[] ) throws JTransientFileSystemException {
    JTransientFileSystem oFS = JTransientFileSystem.getInstance( "c:/temp/transient" );

    new JTransientFileSystemGC( oFS ).start();

    int i;
    for( i = 0; i < 1000; i++ ) {
      oFS.createNewFile( new Integer(1).getClass(), System.currentTimeMillis() + 5*JTransientFileSystem.SECOND, "kk.jpg" );
      try { Thread.sleep( 10 ); } catch( Exception e ) {}
    }
  }
}
