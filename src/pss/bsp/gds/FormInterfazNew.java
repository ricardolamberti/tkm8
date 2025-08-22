package  pss.bsp.gds;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormInterfazNew extends JBaseForm {


private static final long serialVersionUID = 1448371202115L;


  /**
   * Constructor de la Clase
   */
  public FormInterfazNew() throws Exception {
  }

  public GuiInterfazNew getWin() { return (GuiInterfazNew) getBaseWin(); }

 
  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemDateTime(  "Lastupdate", UINT, REQ, "lastupdate" );
    AddItemEdit( "Company", CHAR, REQ, "company" );

  } 
} 
