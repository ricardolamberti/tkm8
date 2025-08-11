package pss.core.tools.io;

/**
 * Description:
 * @author Iván Rubín
 */

public class JInvalidTransientFileException extends JTransientFileSystemException {
  public JInvalidTransientFileException( JTransientFile f ) {
    super( "Unrecognizable object " + f.getName() );
  }
}
