package pss.core.tools.io;

/**
 * Description:
 * @author Iv�n Rub�n
 */

public class JInvalidTransientFileException extends JTransientFileSystemException {
  public JInvalidTransientFileException( JTransientFile f ) {
    super( "Unrecognizable object " + f.getName() );
  }
}
