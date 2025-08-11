package  pss.common.diccionario;

import pss.common.security.BizUsuario;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormDiccionario extends JBaseForm {


private static final long serialVersionUID = 1352315031772L;

  /**
   * Propiedades de la Clase
   */



  /**
   * Constructor de la Clase
   */
  public FormDiccionario() throws Exception {
  }

  public GuiDiccionario getWin() { return (GuiDiccionario) getBaseWin(); }


  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
  	AddItemEdit( "Compañia", CHAR, OPT, "company").setHide(true).SetValorDefault(BizUsuario.getUsr().getCompany());
    AddItemEdit("grupo", CHAR, OPT, "grupo").setHide(true);
    AddItemEdit( "Id", CHAR, OPT, "id_dicc" ).setHide(true);
    AddItemEdit( "Nuevo", CHAR, REQ, "descripcion" );
    

  } 
}  //  @jve:decl-index=0:visual-constraint="10,10" 
