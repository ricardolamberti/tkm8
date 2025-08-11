package  pss.bsp.bo.gen.detalle;

import java.awt.Dimension;
import java.awt.Rectangle;

import pss.bsp.bo.gen.cabecera.BizGenCabecera;
import pss.core.ui.components.JPssEdit;
import pss.core.ui.components.JPssLabel;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormGenDetalle extends JBaseForm {


private static final long serialVersionUID = 1245698750438L;

  /**
   * Constructor de la Clase
   */
  public FormGenDetalle() throws Exception {
  }

  public GuiGenDetalle getWin() { return (GuiGenDetalle) getBaseWin(); }

 
  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( "company", CHAR, REQ, "company" ).setHide(true);
    AddItemEdit( "owner", CHAR, REQ, "owner" ).setHide(true);
    AddItemEdit( "idInterfaz", UINT, REQ, "idInterfaz" ).setHide(true);
    AddItemEdit( "linea", UINT, REQ, "linea" ).setHide(true);
    BizGenCabecera cabecera = getWin().GetcDato().getHeader();
    AddItemEdit( cabecera.getTitulo(1), CHAR, REQ, "d1" );
    AddItemEdit( cabecera.getTitulo(2), CHAR, REQ, "d2" );
    AddItemEdit( cabecera.getTitulo(3), CHAR, REQ, "d3" );
    AddItemEdit( cabecera.getTitulo(4), CHAR, REQ, "d4" );
    AddItemEdit( cabecera.getTitulo(5), CHAR, REQ, "d5" );
    AddItemEdit( cabecera.getTitulo(6), CHAR, REQ, "d6" );
    AddItemEdit( cabecera.getTitulo(7), CHAR, REQ, "d7" );
    AddItemEdit( cabecera.getTitulo(8), CHAR, REQ, "d8" );
    AddItemEdit( cabecera.getTitulo(9), CHAR, REQ, "d9" );
    AddItemEdit( cabecera.getTitulo(10), CHAR, REQ, "d10" );
    AddItemEdit( cabecera.getTitulo(11), CHAR, REQ, "d11" );
    AddItemEdit( cabecera.getTitulo(12), CHAR, REQ, "d12" );
    AddItemEdit( cabecera.getTitulo(13), CHAR, REQ, "d13" );
    AddItemEdit( cabecera.getTitulo(14), CHAR, REQ, "d14" );
    AddItemEdit( cabecera.getTitulo(15), CHAR, REQ, "d15" );
    AddItemEdit( cabecera.getTitulo(16), CHAR, REQ, "d16" );
    AddItemEdit( cabecera.getTitulo(17), CHAR, REQ, "d17" );
    AddItemEdit( cabecera.getTitulo(18), CHAR, REQ, "d18" );
    AddItemEdit( cabecera.getTitulo(19), CHAR, REQ, "d19" );
    AddItemEdit( cabecera.getTitulo(20), CHAR, REQ, "d20" );
    
  

  } 
}  //  @jve:decl-index=0:visual-constraint="10,10" 
