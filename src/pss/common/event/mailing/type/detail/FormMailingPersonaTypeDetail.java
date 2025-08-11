package  pss.common.event.mailing.type.detail;

import pss.core.win.JWin;
import pss.core.winUI.forms.JBaseForm;

public class FormMailingPersonaTypeDetail extends JBaseForm {


private static final long serialVersionUID = 1218223408500L;


/**
   * Constructor de la Clase
   */
  public FormMailingPersonaTypeDetail() throws Exception {
   }

  public GuiMailingPersonaTypeDetail GetWin() { return (GuiMailingPersonaTypeDetail) getBaseWin(); }

 
  public void InicializarPanel( JWin zWin ) throws Exception {
  	
    AddItemEdit( null, CHAR, OPT, "company" ).setHide(true);
    AddItemEdit( null, CHAR, OPT, "id_tipo" ).setHide(true);
    AddItemEdit( null, CHAR, OPT, "id_detail" ).setHide(true);
    AddItemEdit( "DK", CHAR, OPT, "dk" );
      
  }


 
}  //  @jve:decl-index=0:visual-constraint="80,14"
