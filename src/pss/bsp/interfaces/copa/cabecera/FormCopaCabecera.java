package  pss.bsp.interfaces.copa.cabecera;

import java.awt.Dimension;
import java.awt.Rectangle;
import pss.core.winUI.forms.JBaseForm;
import pss.core.ui.components.*;
import pss.core.win.JWin;

public class FormCopaCabecera extends JBaseForm {


private static final long serialVersionUID = 1245253623694L;

  /**
   * Constructor de la Clase
   */
  public FormCopaCabecera() throws Exception {
   }

  public GuiCopaCabecera getWin() { return (GuiCopaCabecera) getBaseWin(); }

  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( null, CHAR, REQ, "company" ).setVisible(false);
    AddItemEdit( null, CHAR, REQ, "owner" ).setVisible(false);
    AddItemEdit( null, CHAR, REQ, "idPDF" );
    AddItemEdit( "Descripcion", CHAR, REQ, "descripcion" );
    AddItemEdit(  "Aerolinea", CHAR, REQ, "aerolinea" );

  } 
}  //  @jve:decl-index=0:visual-constraint="10,10" 
