package  pss.common.customList.config.field.stadistic;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormStadistic extends JBaseForm {


private static final long serialVersionUID = 1226426905817L;


   /* Constructor de la Clase
   */
  public FormStadistic() throws Exception {
    try { jbInit(); }
    catch (Exception e) { e.printStackTrace(); } 
  }

  public GuiSatdistic getWin() { return (GuiSatdistic) getBaseWin(); }

  /**
   * Inicializacion Grafica
   */
  protected void jbInit() throws Exception {

    
  }
  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
   
  }
}  //  @jve:decl-index=0:visual-constraint="-1,16"
