package pss.common.documentos.tipos;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormDocFisicoTipo extends JBaseForm {


private static final long serialVersionUID = 1218223408500L;


  /**
   * Constructor de la Clase
   */
  public FormDocFisicoTipo() throws Exception {
    try { jbInit(); }
    catch (Exception e) { e.printStackTrace(); } 
  }

  public GuiDocFisicoTipo GetWin() { return (GuiDocFisicoTipo) getBaseWin(); }

  /**
   * Inicializacion Grafica
   */
  protected void jbInit() throws Exception {

  }
  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit(  "Id tipo doc", CHAR, REQ, "id_tipo_doc" );
    AddItemEdit( "Descripcion", CHAR, REQ, "descripcion" );
    AddItemCheck( "Transitorio", CHAR, REQ, "transitorio" ,"S", "N");
    AddItemCombo( "Clase", CHAR, REQ, "clase" ,BizDocFisicoTipo.getClases());

  } 
} 
