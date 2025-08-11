
//Title:        Project Benetton
//Version:      1.0
//Copyright:    Copyright (c) 1998
//Author:       Sgalli
//Company:
//Description:  JArray ( Manejo de Arrays )

package pss.core.ui.components;

import javax.swing.JPasswordField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;

import pss.core.services.fields.JObject;


public class JPssPasswordEdit extends JPasswordField {

  //-------------------------------------------------------------------------- //
  // Propiedades privadas de la clase
  //-------------------------------------------------------------------------- //
//  private JPssEditBase      pPssBase   = new JPssEditBase() ;
  private String  pEditType              = JObject.JSTRING     ;
  private String  pEditAtributo          = null                ;
  private int     pEditSize              = Integer.MAX_VALUE   ;
  private int     pEditPrecision         = 0                   ;

  //-------------------------------------------------------------------------- //
  // Manejo publico de las Propiedades privadas de la clase
  //-------------------------------------------------------------------------- //
  public void SetType     ( String zVal ) { pEditType      = zVal;  }
  public void SetAtributo ( String zVal ) { pEditAtributo  = zVal;  }
  public void SetSize     ( int    zVal ) { pEditSize      = zVal;  }
  public void SetPrecision( int    zVal ) { pEditPrecision = zVal;  }

  public String GetType()      { return pEditType;      }
  public String GetAtributo()  { return pEditAtributo;  }
  public int    GetSize()      { return pEditSize;      }
  public int    GetPrecision() { return pEditPrecision; }

  //-------------------------------------------------------------------------- //
  // Constructores
  //-------------------------------------------------------------------------- //
  public JPssPasswordEdit() {
    super();
    PssEditInit( JObject.JSTRING, Integer.MAX_VALUE, 0, null );
  }

  public JPssPasswordEdit( String zType ) {
    super();
    PssEditInit( zType, Integer.MAX_VALUE, 0, null );
  }

  public JPssPasswordEdit( int zMaxSize ) {
    super();
    PssEditInit( JObject.JSTRING, zMaxSize, 0, null );
  }

  public JPssPasswordEdit( String zType, int zMaxSize ) {
    super();
    PssEditInit( zType, zMaxSize, 0, null );
  }

  public JPssPasswordEdit( String zType, int zMaxSize, String zAtributo ) {
    super();
    PssEditInit( zType, zMaxSize, 0, zAtributo );
  }

  public JPssPasswordEdit( String zType, int zMaxSize, int zPres, String zAtributo ) {
    super();
    PssEditInit( zType, zMaxSize, zPres, zAtributo );
  }

  private void PssEditInit( String zType, int zMaxSize, int zPrecision, String zAtributo ) {
    pEditType      = zType      ;
    pEditSize      = zMaxSize   ;
    pEditAtributo  = zAtributo  ;
    pEditPrecision = zPrecision ;
		JPssEditBase.SetBaseType     ( zType      );
		JPssEditBase.SetBaseSize     ( zMaxSize   );
		JPssEditBase.SetBasePrecision( zPrecision );
		JPssEditBase.SetBaseAtributo ( zAtributo  );
  }

  //-------------------------------------------------------------------------- //
  // Metodos de la Clase
  //-------------------------------------------------------------------------- //
//  protected Document createDefaultModel() {
//    return pPssBase = new PasswordField();
//  }

  class PasswordField extends JPssEditBase {

    @Override
		public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
      SetBaseType    ( pEditType     );
      SetBaseSize    ( pEditSize     );
      SetBaseAtributo( pEditAtributo );
      SetBasePrecision ( pEditPrecision );
      super.insertString( offs, str, a );
    }

  }

}

