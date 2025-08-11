package  pss.common.agenda.evento.tipo;

import pss.common.regions.company.GuiCompanies;
import pss.common.security.BizUsuario;
import pss.core.win.JWin;
import pss.core.win.JWins;
import pss.core.winUI.forms.JBaseForm;

public class FormTipoEvento extends JBaseForm {


private static final long serialVersionUID = 1352315031772L;

  /**
   * Propiedades de la Clase
   */



  /**
   * Constructor de la Clase
   */
  public FormTipoEvento() throws Exception {
  }

  public GuiTipoEvento getWin() { return (GuiTipoEvento) getBaseWin(); }


  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemCombo( "Compañia", CHAR, REQ, "company" , new GuiCompanies()).setHide(!BizUsuario.getUsr().isAdminUser()).SetValorDefault(BizUsuario.getUsr().getCompany());
    AddItemEdit( "Id", CHAR, REQ, "id_tipoevento" ).setHide(true);
    AddItemEdit( "Descripción", CHAR, REQ, "descripcion" );
    AddItemCombo( "Logica", CHAR, REQ, "logica" , JWins.createVirtualWinsFromMap(BizTipoEvento.getLogicas()));
    AddItemColor( "Color", COLOUR, REQ, "color" );

  } 
}  //  @jve:decl-index=0:visual-constraint="10,10" 
