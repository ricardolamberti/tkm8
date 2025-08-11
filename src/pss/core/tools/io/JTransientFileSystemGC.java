package pss.core.tools.io;

import pss.core.tools.JGarbageCollector;

/**
 * Description: shit collector for JTransientFileSystem based on JTransientFile
 * objects expiration date.
 *
 * @author Iván Rubín
 *
 */

import pss.core.tools.PssLogger;

public class JTransientFileSystemGC extends JGarbageCollector {

  public JTransientFileSystemGC( JTransientFileSystem zTFS ) {
    super( zTFS );
  }

  @Override
	protected void collect() {
    try {
      shitCollect( ((JTransientFileSystem)this.getCollectableObject()).getRootDirectory() );
    } catch( SecurityException e ) {
//      PssLogger.logError( "transient.file.system", e, "Couldn't garbage collect" );
    }
  }

  /**
    * Will shit collect from zsRoot recursively,
    * eliminating the expired JTransientFiles
    *
    */
  private void shitCollect( String zsRoot ) throws SecurityException {
    JTransientFile file;
    int            i;
    String[]       oFileList;

    // We are using here JTransientFile for files that we don't even expect
    // to be transient files, but we will never ask hasExpired() for those.
    file = new JTransientFile( zsRoot );

    if( !file.exists() ) return;

    // List directory contents and iterate
    oFileList = file.list();
    if( oFileList == null ) return;

    for( i=0; i < oFileList.length; i++ ) {
      String sFileName = oFileList[i];
      file = new JTransientFile( zsRoot + "/" + sFileName );

      // will check it out and remove it
      if( file.isFile() ) {
        @SuppressWarnings("unused")
				boolean hasExpired = false;

        try {
          if( file.hasExpired() ) {
            try {
              if( !file.delete() ) {
//                PssLogger.logError( "transient.file.system",  "Can't delete " + file.getAbsolutePath() + "!!!" );
              }
            } catch( Exception e ) {
//              PssLogger.logError( "transient.file.system", "Can't delete " + file.getAbsolutePath() + "!!!" );
              // Will continue with the process anyway
            }
          }
        } catch( JInvalidTransientFileException itfe ) {
          // Will not cut off the process despite the finding of invalid objects
          PssLogger.logDebug( itfe );
//          PssLogger.logInfo( "transient.file.system", itfe.getMessage() );
        }

      // will recurse into subdirectories
      } else if( file.isDirectory() ) {

        // Will shit collect recursively
        shitCollect( file.getAbsolutePath() );
      }
    }
  }
}
