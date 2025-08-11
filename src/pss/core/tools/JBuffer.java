
//Title:        Project Benetton
//Version:      1.0
//Copyright:    Copyright (c) 1998
//Author:       Sgalli
//Company:
//Description:  Manejo de Buffers


package pss.core.tools;

import java.util.Vector;

public class JBuffer {

  //-------------------------------------------------------------------------- //
  // Propiedades publicas y privadas de la clase
  //-------------------------------------------------------------------------- //
  public char     cDelim       ;
  public char     cFS          ;
  public char     cEOL         ;
  public char     cEOB         ;
  public int      iCantValores = 0;
  public boolean  bSoloValores = false;
  private Vector<String>  vCampo = new Vector<String>();
  private Vector<String>  vValor = new Vector<String>();

  //========================================================================== //
  // Metodos de la clase
  //========================================================================== //

  //-------------------------------------------------------------------------- //
  // Constructor
  //-------------------------------------------------------------------------- //
  public JBuffer() {
    iCantValores = 0     ;
  }

  //-------------------------------------------------------------------------- //
  // SetBuffer( String zStr, char zFS )
  // Seteo un Buffer con un string que tiene campos delimitados por 'zDelim'
  //---------------------------------------------------------------------------//
  public boolean SetBuffer( String zStr, char zFS ) throws JExcepcion {
    @SuppressWarnings("unused")
		char cDelim;
    try {
      this.bSoloValores = true;
      return SetBuffer( zStr, ' ', zFS );
    } // try
    catch ( Exception E ) {
      JExcepcion.ProcesarError( E, this.getClass().getName() + ".SetBuffer"  );
      return false;
    } // Catch
  } // SetBuffer


  //-------------------------------------------------------------------------- //
  // SetBuffer( String zStr, char zDelim, char zFS )
  // Seteo un Buffer con un string que tiene campos delimitados por 'zDelim'
  //---------------------------------------------------------------------------//
  public boolean SetBuffer( String zStr, char zDelim, char zFS ) throws JExcepcion {
    int          iLen     ;
    @SuppressWarnings("unused")
		int          iVal     ;
    @SuppressWarnings("unused")
		int          iCam     ;
    int          iIdx     ;
    boolean      bEsCampo ;
    StringBuffer eValor = new StringBuffer( "" );
    StringBuffer eCampo = new StringBuffer( "" );
    try {

      this.cDelim       = zDelim ;
      this.cFS          = zFS    ;
      this.iCantValores = 0      ;

      iLen              = zStr.length();
      iVal              = 1  ;
      iCam              = 1  ;

      //-----------------------------------------------------------
      // Determino si es una lista de valores o una campo=valor
      //-----------------------------------------------------------
      if ( this.bSoloValores )
        bEsCampo = false ;
      else
        bEsCampo = true  ;

      //--------------------------------
      //  Voy llenando el Campo y Valor
      //--------------------------------
      for ( iIdx = 0 ; iIdx < iLen ; iIdx++ ) {

        if ( bEsCampo ) {
           //--------------------------------
           //  Voy llenando el Campo
           // -------------------------------
           if ( zStr.charAt( iIdx ) == zDelim ) {
             this.vCampo.addElement( eCampo.toString().trim() );
             eCampo   = new StringBuffer( "" );
             bEsCampo = false;
           } else {
             eCampo.append( zStr.charAt( iIdx ) );
           }
        } else {
          //--------------------------------
          //  Voy llenando el Valor
          //--------------------------------
          if ( zStr.charAt( iIdx ) == zFS ) {
            this.vValor.addElement( eValor.toString().trim() );
            eValor = new StringBuffer( "" );;
            if ( this.bSoloValores == false ) bEsCampo = true;
          } else {
            eValor.append( zStr.charAt( iIdx ) );
          }
        }

      }

      if ( bEsCampo == false ) {
        if ( zStr.charAt( iLen-1 ) != zFS ) {
            this.vValor.addElement( eValor.toString().trim() );
        }
      }

      iCantValores = this.vValor.size();

      return true;
    } // try
    catch ( Exception E ) {
      JExcepcion.ProcesarError( E, this.getClass().getName() + ".SetBuffer"  );
      return false;
    } // Catch
  } // SetBuffer


  //---------------------------------------------------------------------------//
  // GetValue( int zIndex )
  // Obtengo el valor de un determinado elemento del buffer                    //
  //---------------------------------------------------------------------------//
  public String GetValue( int zIndex ) {
    if ( zIndex <= this.vValor.size() ) {
      return this.vValor.elementAt( zIndex - 1 ).toString();
    } else {
      return "";
    }
  }

  //---------------------------------------------------------------------------//
  // GetField( int zIndex )
  // Obtengo el valor de un determinado elemento del buffer
  //---------------------------------------------------------------------------//
  public String GetField( int zIndex ) {
    if ( zIndex <= this.vCampo.size() ) {
        return this.vCampo.elementAt( zIndex - 1 ).toString();
    } else {
        return "";
    }
  }

  //---------------------------------------------------------------------------//
  // GetValueByField( String zField )
  // Obtengo el valor de un determinado elemento del buffer
  //---------------------------------------------------------------------------//
  public String GetValueByField( String zField ) {
    int iIdx;

    iIdx = this.vCampo.indexOf( zField );
    if ( iIdx >= 0 ) {
      return this.vValor.elementAt( iIdx ).toString();
    }
    return "";
  }

  public int GetSize() {
    return iCantValores;
  }

}
