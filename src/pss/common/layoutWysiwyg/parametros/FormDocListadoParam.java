package  pss.common.layoutWysiwyg.parametros;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormDocListadoParam extends JBaseForm {


private static final long serialVersionUID = 1218223408500L;


/**
   * Constructor de la Clase
   */
  public FormDocListadoParam() throws Exception {
  }

  public GuiDocListadoParam GetWin() { return (GuiDocListadoParam) getBaseWin(); }


  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( null, LONG, OPT, "id_doc" ).setHide(true);
    AddItemEdit( null, CHAR, OPT, "company" ).setHide(true);
    AddItemEdit( null, LONG, OPT, "list_id" ).setHide(true);
    AddItemEdit( null, LONG, OPT, "orden" ).setHide(true);


    AddItemEdit( "Filtro", CHAR, OPT, "descripcion" ).SetReadOnly(true);
    AddItemEdit( "Valor", CHAR, OPT, "value" );

  } 
}  //  @jve:decl-index=0:visual-constraint="10,10" 
