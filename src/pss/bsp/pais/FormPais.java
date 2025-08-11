package pss.bsp.pais;

import java.awt.Dimension;
import java.awt.Rectangle;
import pss.core.winUI.forms.JBaseForm;
import pss.core.ui.components.*;
import pss.core.win.JWin;

public class FormPais extends JBaseForm {


private static final long serialVersionUID = 1245253307166L;


  /**
   * Constructor de la Clase
   */
  public FormPais() throws Exception {
  }

  public GuiPais getWin() { return (GuiPais) getBaseWin(); }

 
  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( "Pais", CHAR, REQ, "pais" );
    AddItemEdit(  "Id parser pdf", CHAR, REQ, "id_parser_pdf" );
    AddItemEdit( "Id parser ho", CHAR, REQ, "id_parser_ho" );

  } 
} 
