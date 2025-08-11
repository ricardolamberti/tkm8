package pss.core.data.interfaces.connections;

import pss.core.tools.JExcepcion;

public class JConnectionBroken extends JExcepcion {

  /**
   * @deprecated use method #JConnectionBroken( Throwable cause )
   */
  @Deprecated
	public JConnectionBroken() {
    super("01000", "Se perdi� la conexi�n con la Base de Datos");
  }

  public JConnectionBroken(String zErrorMessage) {
    super("01000", zErrorMessage);
  }


  public JConnectionBroken( Throwable cause ) {
    super( "01000", "Se perdi� la conexi�n con la Base de Datos^", cause );
  }

  public JConnectionBroken( String zMessage, Throwable cause ) {
    super( "01000", zMessage, cause );
  }

}
