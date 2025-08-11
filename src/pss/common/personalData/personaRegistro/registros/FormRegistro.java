package  pss.common.personalData.personaRegistro.registros;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormRegistro extends JBaseForm {


private static final long serialVersionUID = 1218223408500L;

/**
   * Constructor de la Clase
   */
  public FormRegistro() throws Exception {
  }

  public GuiRegistro GetWin() { return (GuiRegistro) getBaseWin(); }


  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( null, CHAR, OPT, "id" ).setHide(true);
    AddItemEdit( "Descripcion", CHAR, REQ, "descripcion" );
  //  AddItem( tipo, CHAR, OPT, "id_tipo_sociedad", new GuiTiposSociedad() );
   } 
}  //  @jve:decl-index=0:visual-constraint="10,10" 
