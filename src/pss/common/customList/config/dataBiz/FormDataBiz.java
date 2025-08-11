package  pss.common.customList.config.dataBiz;

import pss.core.win.JWin;
import pss.core.winUI.controls.JFormControl;
import pss.core.winUI.forms.JBaseForm;

public class FormDataBiz extends JBaseForm {


private static final long serialVersionUID = 1226426905817L;


	/**
   * Constructor de la Clase
   */
  public FormDataBiz() throws Exception {
  }

  public GuiDataBiz getWin() { return (GuiDataBiz) getBaseWin(); }


  /**
   * Linkeo los campos con la variables del form
   */
  public void InicializarPanel( JWin zWin ) throws Exception {
    AddItemEdit( "Campo", CHAR, OPT, "campo" ).SetReadOnly(true);
    AddItemEdit( "Descripcion", CHAR, OPT, "descripcion" ).SetReadOnly(true);
    JFormControl c = AddItemArea( "Valor", CHAR, OPT, "valor" );
    c.SetReadOnly(true);
    c.setKeepHeight(true);
    c.setKeepWidth(true);
  }
}  //  @jve:decl-index=0:visual-constraint="-1,16"
