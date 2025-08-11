package  pss.bsp.consola.referencia;

import java.awt.Dimension;
import java.awt.Rectangle;
import pss.core.winUI.forms.JBaseForm;
import pss.core.ui.components.*;
import pss.core.win.JWin;

public class FormReferencia extends JBaseForm {


private static final long serialVersionUID = 1256842199400L;

  /**
   * Constructor de la Clase
   */
  public FormReferencia() throws Exception {
   }

  public GuiReferencia getWin() { return (GuiReferencia) getBaseWin(); }

  
  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( null, UINT, REQ, "id" ).setHide(true);
    AddItemEdit( "icono", UINT, REQ, "icono" );
    AddItemEdit( "descripcion", CHAR, REQ, "descripcion" );

  } 
} 
