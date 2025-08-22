package  pss.bsp.interfaces.dqb.cabecera;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormDQBCabecera extends JBaseForm {


private static final long serialVersionUID = 1245253623694L;



  public FormDQBCabecera() throws Exception {
    }

  public GuiDQBCabecera getWin() { return (GuiDQBCabecera) getBaseWin(); }


  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( null, CHAR, REQ, "company" ).setHide(true);
    AddItemEdit( null, CHAR, REQ, "owner" ).setHide(true);
    AddItemEdit( null, CHAR, REQ, "idPDF" ).setHide(true);
    AddItemEdit( "Descripción", CHAR, REQ, "descripcion" );

  } 
}  //  @jve:decl-index=0:visual-constraint="10,10" 
