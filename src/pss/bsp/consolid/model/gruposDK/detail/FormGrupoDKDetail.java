package  pss.bsp.consolid.model.gruposDK.detail;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormGrupoDKDetail extends JBaseForm {


private static final long serialVersionUID = 1218223408500L;

 

/**
   * Constructor de la Clase
   */
  public FormGrupoDKDetail() throws Exception {
   }

  public GuiGrupoDKDetail GetWin() { return (GuiGrupoDKDetail) getBaseWin(); }

 
  public void InicializarPanel( JWin zWin ) throws Exception {
  	
    AddItemEdit( null, CHAR, OPT, "company" ).setHide(true);
    AddItemEdit( null, CHAR, OPT, "id_grupo" ).setHide(true);
    AddItemEdit( null, CHAR, OPT, "id_detail" ).setHide(true);
    AddItemEdit( "DK", CHAR, OPT, "dk" );
    AddItemCheck("Ver todos",OPT,"view_all"); 
  }


 
}  //  @jve:decl-index=0:visual-constraint="80,14"
