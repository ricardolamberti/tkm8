package pss.core.tools.io;

/**
 * Description: An extension of File with a method that checks expiration date,
 * extracting it from the filename. (Where it is intended to be, if created by
 * JTransientFileSystem.createNewFile.)
 *
 * @author Iván Rubín
 */

import java.io.File;

import pss.core.tools.JExpirable;

public class JTransientFile extends File implements JExpirable {

  public JTransientFile( String path ) {
    super( path );
  }

  /**
    * Answers true if the file represented by this object has expired
    * The expiration date is stored as a part of the filename, check
    * JTransientFileSystem.createNewFile(...)
    *
    * @throws JInvalidTransientFileException if we can't recognize this as a
    * transient file.
    *
    */
  public boolean hasExpired() throws JInvalidTransientFileException {
    String    parse = this.getName();
    int       firstPt = parse.indexOf( '.' );

    if( firstPt == -1 ) throw new JInvalidTransientFileException( this );

    String    sValidUntil = parse.substring( 0, firstPt );
    long      lValidUntil = 0;
    try {
      lValidUntil = Long.parseLong( sValidUntil );
    } catch( NumberFormatException e ) {
      throw new JInvalidTransientFileException( this );
    }

    // Has expired?
    if( System.currentTimeMillis() >= lValidUntil ) return true;

    return false;
  }

}
