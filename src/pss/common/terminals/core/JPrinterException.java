package pss.common.terminals.core;

import pss.core.tools.JExcepcion;

public class JPrinterException extends JExcepcion {
  public JPrinterException() {
    super( "Error de Impresora" );
  }

  public JPrinterException( String sError ) {
    super( sError );
  }

  public static void SendError(String zMje) throws JPrinterException {
    throw new JPrinterException(zMje);
  } // SendError
}
