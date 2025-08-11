package pss.core.ui.components;

import java.awt.Component;

import javax.swing.JTextArea;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;

import pss.core.services.fields.JObject;

/**
 * <p>Title: Pss project</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class JPssEditTextArea extends JTextArea {
//  private JPssEditBase      pPssBase   = new JPssEditBase() ;
  private String  pEditType              = JObject.JSTRING     ;
  private String  pEditAtributo          = null                ;
  private int     pEditSize              = Integer.MAX_VALUE   ;
  private int     pEditPrecision         = 0                   ;
  private Component layoutComponent      = null;

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

  public JPssEditTextArea() {
    super();
    PssEditInit( JObject.JSTRING, GetSize(), 0, null );
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

//  protected Document createDefaultModel() {
//    return pPssBase = new ScrollableTextArea();
//  }

  class ScrollableTextArea extends JPssEditBase {

  @Override
	public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
      SetBaseType    ( pEditType     );
      SetBaseSize    ( pEditSize     );
      SetBaseAtributo( pEditAtributo );
      SetBasePrecision ( pEditPrecision );
      super.insertString( offs, str, a );
    }

  }

	public Component getLayoutComponent() {
		return layoutComponent;
	}
	public void setLayoutComponent(Component layoutComponent) {
		this.layoutComponent = layoutComponent;
	}
}
