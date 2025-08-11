package  pss.bsp.bo.arg.cabecera;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormArgCabecera extends JBaseForm {


private static final long serialVersionUID = 1245254600107L;

  /**
   * Constructor de la Clase
   */
  public FormArgCabecera() throws Exception {
  }

  public GuiArgCabecera getWin() { return (GuiArgCabecera) getBaseWin(); }

  
  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( null, CHAR, OPT, "company" ).setHide(true);
    AddItemEdit( null, CHAR, OPT, "owner" ).setHide(true);
    AddItemEdit( null, UINT, OPT, "idInterfaz" ).setHide(true);
    AddItemDateTime( "Fecha desde", DATE, REQ, "fecha_desde" );
    AddItemDateTime( "Fecha hasta", DATE, REQ, "fecha_hasta" );
    AddItemEdit( "Descripcion", CHAR, REQ, "descripcion" );

  } 
} 
