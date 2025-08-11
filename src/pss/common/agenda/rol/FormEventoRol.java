package  pss.common.agenda.rol;

import pss.common.security.BizUsuario;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormEventoRol extends JBaseForm {

  public FormEventoRol() throws Exception {
  }

  public GuiEventoRol getWin() { return (GuiEventoRol) getBaseWin(); }
  public void InicializarPanel( JWin zWin ) throws Exception {

    AddItemEdit( "id_rol", UINT, OPT, "id_rol" );
    AddItemEdit( "descripcion", CHAR, REQ, "descripcion" );
    AddItemEdit( "company", CHAR, OPT, "company" ).SetValorDefault(BizUsuario.getUsr().getCompany());

  } 
} 
