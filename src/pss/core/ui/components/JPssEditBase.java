
//Title:        Project Benetton
//Version:      1.0
//Copyright:    Copyright (c) 1998
//Author:       Sgalli
//Company:
//Description:  JArray ( Manejo de Arrays )

package pss.core.ui.components;

import java.awt.Toolkit;
import java.awt.event.KeyEvent;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import pss.core.services.fields.JObject;


public class JPssEditBase extends PlainDocument {

  //-------------------------------------------------------------------------- //
  // Propiedades de la clase
  //-------------------------------------------------------------------------- //
  private static String  pType          = JObject.JSTRING   ;
  private static String  pAtributo      = null              ;
//  private static String  pDateFormat    = "dd-MM-yyyy"      ;
  private static int     pSize          = Integer.MAX_VALUE ;
  private static int     pPrecision     = 0                 ;
//  private static boolean pPuntoEscrito  = false             ;
//  private static boolean pMenosEscrito  = false             ;
  private static boolean bExceso        = false             ;
  private String bBeforeText = "";


  //-------------------------------------------------------------------------- //
  // Manejo publico de las Propiedades privadas de la clase
  //-------------------------------------------------------------------------- //
  public static void SetBaseType      ( String zVal ) { pType      = zVal;  }
  public static void SetBaseSize      ( int    zVal ) { pSize      = zVal;  }
  public static void SetBasePrecision ( int    zVal ) { pPrecision = zVal;  }
  public static void SetBaseAtributo  ( String zVal ) { pAtributo  = zVal;  }

  public static String GetBaseType()      { return pType;      }
  public static int    GetBaseSize()      { return pSize;      }
  public static int    GetBasePrecision() { return pPrecision; }
  public static String GetBaseAtributo()  { return pAtributo;  }

  //-------------------------------------------------------------------------- //
  // Constructores
  //-------------------------------------------------------------------------- //
  public JPssEditBase() {
    super();
  }

  //-------------------------------------------------------------------------- //
  // Metodos de la clase
  //-------------------------------------------------------------------------- //
  @Override
	public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {

    // Si recibo null lo ignoro
    if ( str == null )
      return;

    char[]  aChars     = str.toCharArray();
    int     iMas       = 0;
    int     iTextLen   = getLength();
    String  sFinalText = this.getText(0,getLength())+str;

    if (pType.equals(JObject.JHOUR))
      pSize=8;
    if (pType.equals(JObject.JCOLOUR))
      pSize=6;
    if (pType.equals(JObject.JIMAGE))
      pSize=4000;

    String sEnteros ="";
    String sDecimales ="";
    if (pType.equals(JObject.JFLOAT) || pType.equals(JObject.JCURRENCY)){
      // Si la longitud es mayor que la maxima setea retorno
      sEnteros = BuscarParteEntera(sFinalText);
      sDecimales = BuscarParteDecimal(sFinalText);
      bExceso = sFinalText.length() > pSize ||
                sEnteros.length()>pSize-pPrecision || sDecimales.length()>pPrecision;
    }else{
      bExceso = getLength() >= pSize || sFinalText.length() > pSize;
    }
    if (bExceso){
      // La unica excepcion a volver es el float que tiene caracteres
      // adicionales como el '-' o el '.'
      if ( ! pType.equals( JObject.JFLOAT ) && ! pType.equals( JObject.JCURRENCY )) {
        Toolkit.getDefaultToolkit().beep();
        return;
      }

      if ( aChars[0] != '.' && aChars[0] != '-' ) {
        if ( BuscarCaracter( "." ) != -1 )
          iMas ++;
        if ( BuscarCaracter( "-" ) != -1 )
          iMas ++;
        for (int x=1; x<aChars.length;x++)
          if (aChars[x]=='.') iMas++;

        if ( aChars.length+iTextLen-iMas > pSize || sEnteros.length()>pSize-pPrecision || sDecimales.length()>pPrecision) {
          Toolkit.getDefaultToolkit().beep();
          return;
        }
      }
    }//exceso
    // 12/2/2001 GP.
    // Si llega la fecha toda junta y hay que validarla aparte porque la
    // rutina de 1 a 1 no funciona
    if (pType != null &&
        pType.equals( JObject.JHOUR ) &&
        offs == 0 &&
        aChars.length == 6) {
      // Valido la Hora
      int iHora = Integer.valueOf(str.substring(0,2)).intValue();
      if (iHora < 0 || iHora > 23) return;
      // Valido los minutos
      int iMinutos = Integer.valueOf(str.substring(2,4)).intValue();
      if (iMinutos < 0 || iMinutos > 59) return;
      // Valido los Segundos
      int iSegundos = Integer.valueOf(str.substring(4,6)).intValue();
      if (iSegundos < 0 || iSegundos > 59) return;

      super.insertString(offs, str.substring(0,2) + ":" + str.substring(2,4) + ":" + str.substring(4,6), a);
      return;
    }




    char[]  aRta       = new char[ aChars.length ];
    int     iR         = 0;
    int     iLen       = getLength(); // Longitud del Texto total
    int     iOffset    = -1;
    boolean bEscribir = true;

    for (int i = 0; i < aChars.length; i++) {
      // Si es Date
      if (pType != null && pType.equals( JObject.JHOUR ) ) {
        try {
          Integer oInt;
          if(aChars.length >1){
            aRta = aChars;
            iR = aChars.length;
          }else{
            if(aChars[i] != ':')
              oInt = new Integer( String.valueOf( aChars[i] ));
            else
              oInt = new Integer(0);

            char[] aAux = this.getText(0, iLen).toCharArray();
            if (offs < 2) {
              if (iLen > 2 && aAux[2] == ':')  return;
              if (offs == 1 && aAux[0] == ':') return;
              if (offs == 0) {
                if (oInt.intValue() > 2) return;
                if ((iLen > 0) && (oInt.intValue() == 2) && (aAux[0] > '3') && aAux[0]!=':') return;
              } else {
                if (aAux[0] == '2' && oInt.intValue() > 3) return;
              }
            }
            else if (offs == 2) {
              if (aAux[0] == ':' || aAux[1] == ':') return;
            }
            else if (offs > 2 && offs < 5) {
              if (iLen > 5 && aAux[5] == ':') return;
              if (offs == 3) {
                if (oInt.intValue() > 5) return;
              } else {
                if (aAux[3] == ':') return;
              }
            } else if (offs == 5) {
              if (aAux[3] == ':' || aAux[4] == ':') return;
            } else if (offs > 5) {
              if (offs == 6) {
                if (oInt.intValue() > 5) return;
              } else {
                if (aAux[6] == ':') return;
              }
            }

            if ( offs == 1 || offs == 4 ) {
              if (iLen == offs) {
                aRta = new char[ aRta.length+1 ];
                aRta[iR++] = aChars[i];
                aRta[iR++] = ':';
              } else
                aRta[iR++] = aChars[i];

            } else if ( offs == 2 || offs == 5 ) {
              if (iLen == offs) {
                aRta = new char[ aRta.length+1 ];
                aRta[iR++] = ':';
                aRta[iR++] = aChars[i];
              }
              else
                if (aAux[offs] != ':')
                  aRta[iR++] = aChars[i];
            } else {
              aRta[iR++] = aChars[i];
            }
          }
          } catch ( Exception e ) { }
      }
      // Si es String
//			else if ( pType.equals( JObject.JSTRING ) || pType.equals( JObject.JPASSWORD ) ) {
      else if ( (pType.equals( JObject.JSTRING ) || pType.equals( JObject.JPASSWORD )) && (pAtributo==null || !pAtributo.equals(JObject.JLONG))   ) {

        // Verifico si permito Espacios
        if ( pAtributo != null && pAtributo.equals(JObject.JUPPER_NOSPACE) && aChars[i]==' ' ) {
          bEscribir = false;
        } else if ( pAtributo != null && pAtributo.equals(JObject.JUPPER_ONLYLETTERORDIGIT)
                   && !Character.isLetterOrDigit(aChars[i]) ) {
          bEscribir = false;
        } else {
          aRta[iR] = aChars[i];
          // Si es Lower o Upper
          if ( pAtributo != null ) {
            if ( pAtributo.equals( JObject.JUPPERCASE ) || pAtributo.equals( JObject.JUPPER_NOSPACE ) || pAtributo.equals( JObject.JUPPER_ONLYLETTERORDIGIT ))
              aRta[iR] = Character.toUpperCase(aChars[i]);
            else if ( pAtributo.equals( JObject.JLOWERCASE ) )
              aRta[iR] = Character.toLowerCase(aChars[i]);
          }
          iR++;
        }

      }
      // Si entero
      else if (( pType.equals( JObject.JLONG ) || pType.equals( JObject.JINTEGER ) )  || (pAtributo!=null && pAtributo.equals(JObject.JLONG))  ){
        try {
          char c = aChars[i] ;
          if ( c != '-' ) {
//            Integer oInt = new Integer( String.valueOf( aChars[i] ));
          } else {
            if ( offs != 0 ) {
              // No esta permitido
              bEscribir     = false;
            } else {
              if ( pAtributo.equals( JObject.JUNSIGNED ) )
                bEscribir = false;
              else {
                // Busco a ver si el menos sige estando
                iOffset = BuscarCaracter( "-" );
                if ( iOffset >= 0 )
                  bEscribir = false;
              }
            }
          }
          if ( bEscribir )
            aRta[iR++] = aChars[i];
          } catch ( Exception e ) { }
      }
      // Si es float
      else if ( pType.equals( JObject.JFLOAT ) || pType.equals(JObject.JCURRENCY)) {
        try {
          char    c    = aChars[i] ;
//          Integer oInt = null      ;

          if ( c != '.' && c != '-' ) {
//            oInt = new Integer( String.valueOf(c) );
            if ( pPrecision != 0 ) {
              // Busco a ver si el punto sige estando
              iOffset = BuscarCaracter( "." );
              if ( iOffset >= 0 ) {
                if ( offs > iOffset+pPrecision )
                  bEscribir = false;
                else
                  if ( offs > iOffset && iTextLen-iOffset > pPrecision )
                    bEscribir = false;
                else
                  if ( offs < iOffset && iOffset+1 > pSize-pPrecision )
                    bEscribir = false;
              } else {
                // Si no tengo decimales verifico que la parte entera no se exceda
                if ( iTextLen > pSize-pPrecision-1 )
                  bEscribir = false;
              }
            }
          } else
            if ( c == '.' ) {
          if ( pPrecision == 0 )
            bEscribir = false;
          else {
            // Busco a ver si el punto sige estando
            iOffset = BuscarCaracter( "." );
            if ( iOffset >= 0 )
              bEscribir = false ;
            else
              if ( offs <= getLength()-pPrecision-1  )
                bEscribir = false ;
          }
            } else
              // Si tengo un signo menos lo admito solo en la posicion 0
              if ( c == '-' ) {
            if ( offs != 0 ) {
              // No esta permitido
              bEscribir     = false;
            } else {
              if ( pAtributo!=null && pAtributo.equals( JObject.JUNSIGNED ) )
                bEscribir = false;
              else {
                // Busco a ver si el menos sige estando
                iOffset = BuscarCaracter( "-" );
                if ( iOffset >= 0 )
                  bEscribir = false;
              }
            }
              }

              if ( bEscribir )
                aRta[iR++] = c;

              } catch ( Exception e ) { }
      }
      // Si es Date
      else if ( pType.equals( JObject.JDATE ) ) {
        try {
          Integer oInt = new Integer( String.valueOf( aChars[i] ));
          char[] aAux = this.getText(0, iLen).toCharArray();
          /*
          for (int iIndex = 0; iIndex < iLen; iIndex++) {
            if (iIndex == 2 || iIndex == 4)
              if (aAux[iIndex] != '-') JExcepcion.SendError("");
            else
              String.valueOf( aAux[i] );
          } // end for
          */

          // Validacion de dia
          if (offs < 2) {
            if (iLen > 2 && aAux[2] == '-')  return;
            if (offs == 1 && aAux[0] == '-') return;
            if (offs == 0) {
              if (oInt.intValue() > 3) return;
              if ((iLen > 0) && (oInt.intValue() > 2) && (aAux[0] > '1')) return;
            } else {
              if (aAux[0] == '3' && oInt.intValue() > 1) return;
              if (aAux[0] == '0' && oInt.intValue() == 0) return;
            }
          } else if (offs == 2) {
            if (aAux[0] == '-' || aAux[1] == '-') return;
            // Validacion de Mes
          } else if (offs > 2 && offs < 5) {
            if (iLen > 5 && aAux[5] == '-') return;
            if (offs == 3) {
              if (oInt.intValue() > 1) return;
              if ((iLen > 3) && (oInt.intValue() == 1) && (aAux[3] > '2')) return;
            } else {
              if (aAux[3] == '1' && oInt.intValue() > 2)   return;
              if (aAux[3] == '0' && oInt.intValue() == 0)  return;
              if (aAux[3] == '-')                          return;
            }
          } else if (offs == 5) {
            if (aAux[3] == '-' || aAux[4] == '-') return;
          }

          if ( offs == 1 || offs == 4 ) {
            if (iLen == offs) {
              aRta = new char[ aRta.length+1 ];
              aRta[iR++] = aChars[i];
              aRta[iR++] = '-';
            } else
              aRta[iR++] = aChars[i];

          } else if ( offs == 2 || offs == 5 ) {
            if (iLen == offs) {
              aRta = new char[ aRta.length+1 ];
              aRta[iR++] = '-';
              aRta[iR++] = aChars[i];
            }
            else
              if (aAux[offs] != '-')
                aRta[iR++] = aChars[i];
          } else {
            aRta[iR++] = aChars[i];
          }

          } catch ( Exception e ) { }
      } // end if
    } // end for
    if ( iR > 0 )
      super.insertString(offs, new String(aRta), a);
  }

  //-------------------------------------------------------------------------- //
  // Busco un caracter en el texto
  //-------------------------------------------------------------------------- //
  private int BuscarCaracter( String c ) throws BadLocationException  {
    String sText ;
    sText = getText( 0, getLength() );
    return sText.indexOf( c );
  }

  private String BuscarParteEntera(String str) throws BadLocationException {
    String sText ="";
    if (str.length()!=-1){
      if (str.indexOf(".") != -1)
        sText=str.substring(0,str.indexOf("."));
      else
        sText = str;
    }
    return sText;
  }

  private String BuscarParteDecimal(String str) throws BadLocationException {
    String sText ="";
    String sT="";
    if (str.length()!=-1){
      if (str.indexOf(".") != -1){
        sT=str.substring(str.indexOf("."),str.length());
        sText=sT.substring(sT.indexOf(".")+1, sT.length());
      }
    }
    return sText;
  }

  //-------------------------------------------------------------------------- //
  // Metodos del KeyListener
  //-------------------------------------------------------------------------- //
  public void keyReleased(KeyEvent e) {
    if (bBeforeText.equals("")) return;
    try {
      if (this.getText(0,getLength()).indexOf(".")>=0) return;
      setTextCorrection(BuscarParteEntera(bBeforeText));
      } catch(Exception ex) {}
  }
  public void keyTyped   (KeyEvent e) { }


  public void setTextCorrection(String zNewText) { }

  /**
   * Keypressed method
   */
  private boolean isKeyDelete(int iKey) {return iKey==8 || iKey==127;}

//  public void keyPressed (KeyEvent e) {
//    try {
//      bBeforeText = "";
//      if (pType.equals(JObject.JFLOAT) || pType.equals(JObject.JCURRENCY)){
//        if (isKeyDelete(e.getKeyCode()))
//          if (this.getText(0,getLength()).indexOf(".")>=0)
//            bBeforeText = this.getText(0,getLength());
//      }
//
//    } catch( Exception ex ) {
//      if (!e.isConsumed()) {
//        e.consume();
//      }
//      UITools.MostrarError( ex );
//    }
//  }

}
