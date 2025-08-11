package  pss.bsp.bo.gen.cabecera;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormGenCabecera extends JBaseForm {


private static final long serialVersionUID = 1245698643769L;

  /**
   * Constructor de la Clase
   */
  public FormGenCabecera() throws Exception {
  }

  public GuiGenCabecera getWin() { return (GuiGenCabecera) getBaseWin(); }
 
  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( null, CHAR, REQ, "company" ).setHide(true);
    AddItemEdit( null, CHAR, REQ, "owner" ).setHide(true);
    AddItemEdit( null, UINT, REQ, "idInterfaz" ).setHide(true);
    AddItemEdit( null, CHAR, REQ, "id_formato" ).setHide(true);

  } 
} 
