package  pss.common.todolist;

import pss.common.security.BizUsuario;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormToDoList extends JBaseForm {


private static final long serialVersionUID = 1352315031772L;

  /**
   * Propiedades de la Clase
   */



  /**
   * Constructor de la Clase
   */
  public FormToDoList() throws Exception {
  }

  public GuiToDoList getWin() { return (GuiToDoList) getBaseWin(); }


  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
  	AddItemEdit(null, CHAR, OPT, "company").setHide(true).SetValorDefault(BizUsuario.getUsr().getCompany());
    AddItemEdit( null, CHAR, OPT, "id_todo" ).setHide(true);
    AddItemEdit( "Orden", CHAR, REQ, "priority" );
    AddItemEdit( "Grupo", CHAR, OPT, "grupo");
    AddItemCombo( "Usuario (vacío = company level)", CHAR, OPT, "id_usuario" );
    AddItemEdit( "Descripción", CHAR, REQ, "description" );
    AddItemEdit( "Accion", CHAR, OPT, "action" );
    AddItemCombo( "Estado", CHAR, OPT, "status").SetValorDefault(BizToDoList.PENDIENTE);

  } 
}  //  @jve:decl-index=0:visual-constraint="10,10" 
