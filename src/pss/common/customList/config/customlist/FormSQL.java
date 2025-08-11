package  pss.common.customList.config.customlist;

import pss.common.customList.config.field.campo.GuiCampo;
import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormSQL extends JBaseForm {


private static final long serialVersionUID = 1226426905817L;


	/**
   * Constructor de la Clase
   */
  public FormSQL() throws Exception {
 }

  public GuiCampo getWin() { return (GuiCampo) getBaseWin(); }

  /**
   * Inicializacion Grafica
   */
 
  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( null, CHAR, REQ, "company" ).setHide(true);
    AddItemEdit( null, UINT, REQ, "list_id" ).setHide(true);
    AddItemArea( "Sql", CHAR, OPT, "sql");
  }
  

	
}  //  @jve:decl-index=0:visual-constraint="10,10" 
