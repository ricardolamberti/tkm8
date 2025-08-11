package pss.core.tools;

/**
 * Description: Si se le especifica una Hashtable de (String key, String value)
 * por la PRIMER OCURRENCIA de KEY agrega VALUE al stream y elimina la entrada de la
 * tabla.
 *
 * Uso esta clase para agregar algunas cosas +o- al principio de un stream,
 * para algo performante a lo largo del stream es necesario diseñar esto
 * de otra manera.
 */

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Hashtable;

public class JTransBufferedOutputStream extends BufferedOutputStream {
  private Hashtable oTransTable;
  private int[]     oIndexes; // Debería ser parte del valor asociado a la llave,
                              // encapsulado en algan tipo de objeto

  public JTransBufferedOutputStream( OutputStream zoOut ) {
    super( zoOut );
  }

  public JTransBufferedOutputStream( OutputStream zoOut, int ziSize ) {
    super( zoOut, ziSize );
  }

  public void setTransformations( Hashtable t ) {
    this.oTransTable = t;

    if( this.oTransTable != null ) {
      this.oIndexes = new int[ t.size() ];
    }
  }

  @Override
	public void write( byte[] b, int off, int len ) throws IOException {
    if( oTransTable == null ) {
      super.write( b, off, len );
      return;
    }
    int i = off;
    while( i < off+len ) {
      doTrans( b[i] );
      i++;
    }
  }

  @Override
	public void write( int b ) throws IOException {
    if( oTransTable == null ) {
      super.write( b );
    }

    doTrans( (byte)b );
  }

  private void doTrans( byte b ) throws IOException {
    Enumeration e = oTransTable.keys();

    int i=0;
    while( e.hasMoreElements() ) {
      String key   = (String)e.nextElement();
      String value = (String)oTransTable.get( key );

      if( b == (byte)key.charAt( oIndexes[i] ) ) {
        oIndexes[i]++;
        // Matcheó la llave completa?
        if( oIndexes[i] == key.length() ) {
          super.write( b );
          super.write( value.getBytes() );

          oTransTable.remove( key );
          if( oTransTable.size() == 0 ) {
            oTransTable = null;
            return;
          }

          // Elimina de oIndexes el elemento [i] y redimensiona para
          // que sea correlativo a las keys() en oTransTable
          int[] auxIndices = new int[ oIndexes.length-1 ];
          int o;
          int j = 0;
          for( o=0; o < oIndexes.length; o++ ) {
            if( o != i ) {
              auxIndices[j++] = oIndexes[o];
            }
          }

          oIndexes = auxIndices;
          i = 0;
          e = oTransTable.keys();
          continue;
        }
      }

      i++;
    }
  }
}
