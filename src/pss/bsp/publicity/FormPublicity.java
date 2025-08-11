package  pss.bsp.publicity;

import pss.common.security.BizUsuario;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormPublicity extends JBaseForm {


private static final long serialVersionUID = 1245258187718L;


/**
   * Constructor de la Clase
   */
  public FormPublicity() throws Exception {
  }

  public GuiPublicity getWin() { return (GuiPublicity) getBaseWin(); }

  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( null, CHAR, REQ, "company" ).SetValorDefault(BizUsuario.getUsr().getCompany()).setHide(true);
    AddItemEdit( null, CHAR, REQ, "owner" ).SetValorDefault(BizUsuario.getUsr().GetUsuario()).setHide(true);
    AddItemEdit( null, UINT, REQ, "id_interfaz" ).SetValorDefault(1).setHide(true);
    AddItemEdit( null, UINT, OPT, "linea" ).setHide(true);
    AddItemEdit( "Tipo ruta", CHAR, REQ, "campana" );
    AddItemEdit( "Aerolinea", CHAR, REQ, "segmento" );
    AddItemDateTime( "Fecha", DATE, REQ, "fecha" );

  } 
}  //  @jve:decl-index=0:visual-constraint="10,10" 
